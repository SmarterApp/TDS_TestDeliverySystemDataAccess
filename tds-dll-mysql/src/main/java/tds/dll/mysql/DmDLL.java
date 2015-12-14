/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.mysql;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import TDS.Shared.Exceptions.ReturnStatusException;

import tds.dll.api.ICommonDLL;
import tds.dll.api.IDmDLL;
import AIR.Common.DB.AbstractDLL;
import AIR.Common.DB.DataBaseTable;
import AIR.Common.DB.SQLConnection;
import AIR.Common.DB.SQL_TYPE_To_JAVA_TYPE;
import AIR.Common.DB.SqlParametersMaps;
import AIR.Common.DB.results.DbResultRecord;
import AIR.Common.DB.results.SingleDataResultSet;
import AIR.Common.Sql.AbstractDateUtilDll;


public class DmDLL extends AbstractDLL implements IDmDLL
{
  @Autowired
  private ICommonDLL          _commonDll = null;

  @Autowired
  private AbstractDateUtilDll _dateUtil     = null;
  
  private static Logger             _logger               = LoggerFactory.getLogger (DmDLL.class);
  
  public void _DailyMaintenance_SP(SQLConnection connection) throws ReturnStatusException {
    
    try {
      _DM_ExpireOpportunities_SP (connection);
      
    } catch (ReturnStatusException re) {
      
       StackTraceElement[] stackTrace = re.getStackTrace ();
       String proc = String.format( "ExpireOpportunities: %s ", stackTrace[0].toString ()); 
      _commonDll._RecordSystemError_SP (connection , proc, re.getMessage ());
      _logger.error (re.getMessage ());
      //if (@proc is null) set @proc = 'ExpireOpoportunities';
    }
    
  }
  
  public void _DM_ExpireOpportunities_SP (SQLConnection connection) throws ReturnStatusException {
    Date today = _dateUtil.getDateWRetStatus (connection);
    
    DataBaseTable completeTbl = getDataBaseTable ("complete").addColumn ("oppkey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER).
        addColumn ("report", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (completeTbl);
    
    //insert into @complete (oppkey, report)
    //select _Key, dbo.IsClientReporting(O.clientname) from TestOpportunity O, TDSCONFIGS_Client_TestProperties P
    //where O.dateCompleted is null and O.status not in ('completed', 'submitted', 'scored', 'expired', 'reported', 'invalidated')
    //    and dateadd(hour, 24 * dbo.ClientTestExpiresIn(_efk_TestID, O.clientname), expireFrom) <= @today
    //    and P.clientname = O.clientname and P.testID = O._efk_TestID and P.forceComplete = 1
    //    and dbo.IsComplete(_Key) = 1 AND O.dateDeleted IS null;
   
    final String cmd1 = "insert into ${completeTbl} (oppkey, report) "
      + " select _Key, isclientreporting(O.clientname) from testopportunity O, ${ConfigDB}.client_testproperties P "
      + "   where O.dateCompleted is null "
      + "   and O.status not in ('completed', 'submitted', 'scored', 'expired', 'reported', 'invalidated')"
      + "  and (expireFrom + INTERVAL (24 * clienttestexpiresin(_efk_TestID, O.clientname)) HOUR) <= ${today} "
      + "  and P.clientname = O.clientname and P.testID = O._efk_TestID and P.forceComplete = 1 "
      + "  and iscomplete(_Key) = b'1' AND O.dateDeleted IS null";
    String fixedCmd1 = fixDataBaseNames (cmd1);
    Map<String, String> unquotedParms1 = new HashMap<String, String> ();
    unquotedParms1.put ("completeTbl", completeTbl.getTableName ());
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("today", today);
    
    int insertedCnt = executeStatement (connection, fixDataBaseNames (fixedCmd1, unquotedParms1), parms1, false).getUpdateCount ();
    
    final String cmd2 = "insert into ${ArchiveDB}.opportunityaudit (_fk_TestOpportunity, AccessType, actor, dateaccessed, hostname, dbname) "
      + " select oppkey, 'ForceComplete', 'SYSTEM', now(3), ${localhost}, ${dbname} from ${completeTbl}";
    String fixedCmd2 = fixDataBaseNames (cmd2);
    Map<String, String> unquotedParms2 = unquotedParms1;
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("localhost", getLocalhostName ()).
        put ("dbname", getTdsSettings ().getTDSSessionDBName ());
    insertedCnt = executeStatement (connection, fixDataBaseNames (fixedCmd2, unquotedParms2), parms2, false).getUpdateCount ();
    
    final String cmd3 = " update testopportunity T, ${completeTbl} C set status = 'completed', "
        + " dateForceCompleted = ${today}, dateCompleted = _testopplastactivity(T._Key) "
        + "  where T._Key = C.oppkey";
    Map<String, String> unquotedParms3 = unquotedParms1;
    SqlParametersMaps parms3 = parms1;
    int updatedCnt = executeStatement (connection, fixDataBaseNames (cmd3, unquotedParms3), parms3, false).getUpdateCount ();
    
    final String cmd4 = "update testopportunity set prevstatus = status, status = 'expired',"
        + " dateExpired = ${today} "
        + " where dateCompleted is null "
        + "  and status not in ('completed', 'submitted', 'scored', 'expired', 'reported', 'invalidated') "
        + "  and (expireFrom + INTERVAL (24 * clienttestexpiresin(_efk_TestID, clientname)) HOUR) <= ${today} "
        + "  and dateDeleted is null";
    SqlParametersMaps parms4 = parms1;
    updatedCnt = executeStatement (connection, cmd4, parms4, false).getUpdateCount ();
    
    
    //Elena: optimized part. Recs with report=1 are pulled to Java side
    // and SubmitQAReport is issued for each rec
    submitAllQAReports (connection, completeTbl, "submitted");

    //done with current content of 'complete'tmp table. truncate it.
    final String cmd6 = "delete from ${completeTbl}";
    Map<String, String> unquotedParms6 = unquotedParms1;
    int deletedCnt = executeStatement (connection, fixDataBaseNames (cmd6, unquotedParms6), null, false).getUpdateCount ();
       
    final String cmd7 = " insert into ${completeTbl} (oppkey, report) "
      + " select _Key, isclientreporting(clientname) from testopportunity "
      + "  where status = 'expired' and dateExpired = ${today}";
    Map<String, String> unquotedParms7 = unquotedParms1;
    SqlParametersMaps parms7 = parms1;
    insertedCnt = executeStatement (connection, fixDataBaseNames (cmd7, unquotedParms7), parms7, false).getUpdateCount ();
    
    final String cmd8 = "insert into ${ArchiveDB}.opportunityaudit (_fk_TestOpportunity, AccessType, actor, dateaccessed, hostname, dbname) "
        + " select oppkey, 'Expired', 'SYSTEM', now(3), ${localhost}, ${dbname} from ${completeTbl}";
    String fixedCmd8 = fixDataBaseNames (cmd8);
    Map<String, String> unquotedParms8 = unquotedParms1;
    SqlParametersMaps parms8 = parms2;
    insertedCnt = executeStatement (connection, fixDataBaseNames (fixedCmd8, unquotedParms8), parms8, false).getUpdateCount ();

    submitAllQAReports (connection, completeTbl, "expired");
    
    //-- Reset all tests that have not been started. 
    //-- These are test opps that were opened and either never approved or not started by the student.
    final String cmd9 = "update testopportunity set datedeleted = ${today} "
        + " where datestarted is null and maxitems = 0 "
        + "  and dateinvalidated is null and dateexpired is null "
        + "  and dateExpiredReported is null and dateDeleted is null";
    SqlParametersMaps parms9 = parms1;
    updatedCnt = executeStatement (connection, cmd9, parms9, false).getUpdateCount ();
    
    _commonDll._LogDBLatency_SP (connection, "_DM_ExpireOpportunities", today, 0L, false, null, null);
  }
  
  private void submitAllQAReports (SQLConnection connection, DataBaseTable completeTbl,  String status) throws ReturnStatusException {
    
    Boolean submitted = ("submitted".equalsIgnoreCase (status) ? true :false);
    
    final String cmd5 = "select oppkey from ${completeTbl} where report = 1";
    Map<String, String> unquotedParms5 = new HashMap<>();
    unquotedParms5.put ("completeTbl", completeTbl.getTableName ());
    
    SingleDataResultSet rs5 = executeStatement (connection, fixDataBaseNames (cmd5, unquotedParms5), null, false).getResultSets ().next ();
    Iterator<DbResultRecord> iter = rs5.getRecords ();
    while (iter.hasNext ()) {
      DbResultRecord rcd5 = iter.next ();
      UUID oppkey = rcd5.<UUID> get ("oppkey");
      
      if (submitted)
         _commonDll._OnStatus_Completed_SP (connection, oppkey);
      
      _commonDll.SubmitQAReport_SP (connection, oppkey, status);     
    }
    
  }
  private String getLocalhostName () {
    String localhostname = null;
    try {
      localhostname = InetAddress.getLocalHost ().getHostName ();
    } catch (UnknownHostException e) {

    }
    return localhostname;
  }
}
