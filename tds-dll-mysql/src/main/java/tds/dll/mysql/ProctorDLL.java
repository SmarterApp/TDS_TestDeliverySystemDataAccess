/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import tds.dll.api.ICommonDLL;
import tds.dll.api.IProctorDLL;
import tds.dll.api.IRtsDLL;
import AIR.Common.DB.AbstractDLL;
import AIR.Common.DB.DataBaseTable;
import AIR.Common.DB.DbComparator;
import AIR.Common.DB.SQLConnection;
import AIR.Common.DB.SQL_TYPE_To_JAVA_TYPE;
import AIR.Common.DB.SqlParametersMaps;
import AIR.Common.DB.results.DbResultRecord;
import AIR.Common.DB.results.MultiDataResultSet;
import AIR.Common.DB.results.SingleDataResultSet;
import AIR.Common.Helpers.CaseInsensitiveMap;
import AIR.Common.Helpers._Ref;
import AIR.Common.Sql.AbstractDateUtilDll;
import TDS.Shared.Exceptions.ReturnStatusException;

public class ProctorDLL extends AbstractDLL implements IProctorDLL
{
  private static Logger       _logger    = LoggerFactory.getLogger (ProctorDLL.class);

  @Autowired
  private AbstractDateUtilDll _dateUtil  = null;

  @Autowired
  private IRtsDLL             _rtsDll    = null;

  @Autowired
  private ICommonDLL          _commonDll = null;

  /**
   * @param connection
   * @param clientName
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   */
  public MultiDataResultSet IB_GetSegments_SP (SQLConnection connection, String clientName, Integer sessionType) throws ReturnStatusException {

    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();
    DataBaseTable testsTable = getDataBaseTable ("tests").addColumn ("_key", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).addColumn ("ID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150);
    connection.createTemporaryTable (testsTable);
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("testsTblName", testsTable.getTableName ());

    final String SQL_INSERT = "insert into ${testsTblName} (_key, ID) select testkey, S.TestID from ${ConfigDB}.client_testmode, ${ItemBankDB}.tblsetofadminsubjects S"
        + " where clientname = ${clientname} and testkey = S._Key and (sessionType = -1 or sessionType = ${sessionType});";
    String finalQuery = fixDataBaseNames (SQL_INSERT);
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("clientname", clientName).put ("sessionType", sessionType);
    int insertedCnt = executeStatement (connection, fixDataBaseNames (finalQuery, unquotedParms), parms1, false).getUpdateCount ();

    final String SQL_QUERY1 = "select _key as TestKey, parentTest as TestID, segmentID, segmentPosition, Label as SegmentLabel, IsPermeable, entryApproval, exitApproval,"
        + " itemReview from ${ConfigDB}.client_segmentproperties SEG, ${testsTblName} T where SEG.clientname = ${clientname} and SEG.parentTest = T.ID order by _key, segmentPosition;";
    finalQuery = fixDataBaseNames (SQL_QUERY1);
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("clientname", clientName);
    SingleDataResultSet rs1 = executeStatement (connection, fixDataBaseNames (finalQuery, unquotedParms), parms2, false).getResultSets ().next ();
    resultsets.add (rs1);

    final String SQL_QUERY2 = "SELECT _key as TestKey, parentTest as TestID, segmentID, segmentPosition, Type as AccType, Value as AccValue, Code as AccCode, IsDefault,"
        + " AllowCombine, AllowChange, TType.IsSelectable, IsVisible, studentControl, 1 as IsFunctionalN FROM ${ConfigDB}.client_testtooltype TType, "
        + " ${ConfigDB}.client_testtool TT, ${testsTblName} TEST, ${ConfigDB}.client_segmentproperties SEG where SEG.clientname = ${clientname} and SEG.parentTest = TEST.ID"
        + " and TType.ContextType = ${SEGMENT} and TType.Context = segmentID and TType.ClientName = ${clientname} and TT.ContextType = ${SEGMENT} and TT.Context = segmentID "
        + " and TT.ClientName = ${clientname} and TT.Type = TType.Toolname order by _Key, segmentPosition, Type, Code;";

    finalQuery = fixDataBaseNames (SQL_QUERY2);
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("clientname", clientName).put ("SEGMENT", "SEGMENT");
    SingleDataResultSet rs2 = executeStatement (connection, fixDataBaseNames (finalQuery, unquotedParms), parms3, false).getResultSets ().next ();
    rs2.addColumn ("IsFunctional", SQL_TYPE_To_JAVA_TYPE.BIT);
    Iterator<DbResultRecord> records = rs2.getRecords ();
    while (records.hasNext ()) {
      DbResultRecord record = records.next ();
      record.addColumnValue ("IsFunctional", (record.<Long> get ("IsFunctionalN") == 1 ? true : false));
    }
    resultsets.add (rs2);

    return new MultiDataResultSet (resultsets);
  }

  /**
   * @param connection
   * @param clientName
   * @param context
   * @return
   * @throws ReturnStatusException
   */
  public MultiDataResultSet IB_GlobalAccommodations_SP (SQLConnection connection, String clientName, String context) throws ReturnStatusException {

    List<SingleDataResultSet> resultsSets = new ArrayList<SingleDataResultSet> ();
    final String SQL_QUERY1 = "select TType.ContextType, TType.Context, Type as AccType, Value as AccValue, Code as AccCode, IsDefault, AllowCombine, AllowChange, " +
        " TType.IsSelectable, IsVisible, studentControl, 1 as IsFunctionalN, DependsOnToolType from ${ConfigDB}.client_testtooltype TType, ${ConfigDB}.client_testtool TT" +
        " where TType.ContextType = ${FAMILY} and TType.clientname = ${clientName} and TT.context = ${context} and TType.Context = TT.context and TT.clientname = ${clientName} " +
        " and TT.ContextType = ${FAMILY} and TT.Type = TType.ToolName";
    String query1 = fixDataBaseNames (SQL_QUERY1);
    SqlParametersMaps parameters1 = new SqlParametersMaps ().put ("clientName", clientName).put ("context", context).put ("FAMILY", "FAMILY");
    SingleDataResultSet result1 = executeStatement (connection, query1, parameters1, false).getResultSets ().next ();
    result1.addColumn ("IsFunctional", SQL_TYPE_To_JAVA_TYPE.BIT);
    Iterator<DbResultRecord> records = result1.getRecords ();
    while (records.hasNext ()) {
      DbResultRecord record = records.next ();
      record.addColumnValue ("IsFunctional", (record.<Long> get ("IsFunctionalN") == 1 ? true : false));
    }
    resultsSets.add (result1);

    final String SQL_QUERY2 = "select clientname, ContextType, Context, IfType, IfValue, ThenType, ThenValue, IsDefault " +
        "from ${ConfigDB}.client_tooldependencies where clientname = ${clientName} and ContextType = ${FAMILY}";
    String query2 = fixDataBaseNames (SQL_QUERY2);
    SqlParametersMaps parameters2 = new SqlParametersMaps ().put ("clientName", clientName).put ("FAMILY", "FAMILY");
    SingleDataResultSet result2 = executeStatement (connection, query2, parameters2, true).getResultSets ().next ();
    resultsSets.add (result2);

    MultiDataResultSet results = new MultiDataResultSet (resultsSets);

    return results;
  }

  /**
   * @param connection
   * @param clientName
   * @param application
   * @param userId
   * @param clientIp
   * @param proxyIp
   * @param userAgent
   * @throws ReturnStatusException
   */
  public void _RecordSystemClient_SP (SQLConnection connection, String clientName, String application, String userId, String clientIP, String proxyIP, String userAgent) throws ReturnStatusException {

    // String sessionDB = getAppSettings ().get ("TDSSessionDBName");
    String sessionDB = getTdsSettings ().getTDSSessionDBName ();
    final String SQL_INSERT = "insert into ${ArchiveDB}.systemclient (clientname, application, UserID, ClientIP, ProxyIP, UserAgent, daterecorded, dbname)"
        + " values (${clientname}, ${application}, ${UserID}, ${ClientIP}, ${ProxyIP}, ${UserAgent}, now(3), ${dbname});";
    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("clientname", clientName).put ("application", application).put ("UserID", userId).put ("ClientIP", clientIP)
        .put ("ProxyIP", proxyIP).put ("UserAgent", userAgent).put ("dbname", sessionDB);
    int insertedCnt = executeStatement (connection, fixDataBaseNames (SQL_INSERT), parameters, false).getUpdateCount ();
  }

  /**
   * This function returns 'bit' in the studio. so, in our java side, we need to
   * return 'boolean' but in this case its not possible to return 'boolean'
   * value because column 'IsOn' from TDSCONFIGS_Client_SystemFlags table is
   * Integer type and its not possible to convert 'int' to 'boolean' on our java
   * side. So, declared return type as 'int'.
   * 
   * @param connection
   * @param clientName
   * @return
   * @throws ReturnStatusException
   */
  public int _SuppressScores_FN (SQLConnection connection, String clientName) throws ReturnStatusException {

    Integer allow = null;
    final String SQL_QUERY = "select IsOn as allow from ${ConfigDB}.client_systemflags F, externs E where E.ClientName= ${ClientName} and F.clientname = ${clientname}"
        + " and E.IsPracticeTest = F.IsPracticeTest and AuditOBject = ${suppressScores}; ";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("clientname", clientName).put ("suppressScores", "suppressScores");
    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY), parms, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      allow = record.<Integer> get ("allow");
    }
    if (allow == null || allow == 0) {
      return 0;
    } else
      return 1;
  }

  /**
   * @param connection
   * @param clientName
   * @param testeeId
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet GetTesteeAttributes_SP (SQLConnection connection, String clientName, String testeeId) throws ReturnStatusException {

    String attname = null;
    String attType = null;
    String RTSName = null;
    String err = null;
    _Ref<Long> testee = new _Ref<> ();
    _Ref<String> attval = new _Ref<> ();
    _Ref<Long> entityKey = new _Ref<> ();
    _Ref<String> entityID = new _Ref<> ();
    _Ref<String> entityName = new _Ref<> ();
    SingleDataResultSet result = null;

    DataBaseTable attributesTable = getDataBaseTable ("attributes").addColumn ("TDS_ID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("type", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100)
        .addColumn ("atLogin", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("rtsName", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("label", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50)
        .addColumn ("value", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200).addColumn ("sortOrder", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("entityKey", SQL_TYPE_To_JAVA_TYPE.BIGINT)
        .addColumn ("entityID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("showOnProctor", SQL_TYPE_To_JAVA_TYPE.BIT);
    connection.createTemporaryTable (attributesTable);
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("attributesTblName", attributesTable.getTableName ());

    final String SQL_INSERT1 = "insert into ${attributesTblName} (TDS_ID, type, rtsName, label, sortOrder, atLogin, showOnProctor) "
        + " select TDS_ID, type, RTSName, label, sortOrder, atlogin, showOnProctor  from ${ConfigDB}.client_testeeattribute where clientname = ${clientname};";
    String finalQuery = fixDataBaseNames (SQL_INSERT1);
    SqlParametersMaps parms = new SqlParametersMaps ().put ("clientname", clientName);
    int insertedCnt = executeStatement (connection, fixDataBaseNames (finalQuery, unquotedParms), parms, false).getUpdateCount ();
    // System.err.println (insertedCnt); // for testing

    _rtsDll._GetRTSEntity_SP (connection, clientName, testeeId, "STUDENT", testee);
    if (testee.get () == null) {
      final String SQL_QUERY1 = "select * from ${attributesTblName};";
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY1, unquotedParms), null, false).getResultSets ().next ();
      return result;
    }
    final String SQL_INSERT2 = "insert into ${attributesTblName} ( TDS_ID, type, value) values (${--RTS KEY--}, ${ENTITYKEY}, ltrim(convert(${testee}, CHAR)));";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("testee", testee.get ()).put ("--RTS KEY--", "--RTS KEY--").put ("ENTITYKEY", "ENTITYKEY");
    insertedCnt = executeStatement (connection, fixDataBaseNames (SQL_INSERT2, unquotedParms), parms1, false).getUpdateCount ();
    // System.err.println (insertedCnt); // for testing

    final String SQL_QUERY2 = "select TDS_ID from ${attributesTblName} where value is null limit 1";
    while (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY2, unquotedParms), null, false))) {
      final String SQL_QUERY3 = "select TDS_ID as attname, Type as attType, rtsName as RTSName from ${attributesTblName} where value is  null limit 1;";
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY3, unquotedParms), null, false).getResultSets ().next ();
      DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        attname = record.<String> get ("attname");
        attType = record.<String> get ("attType");
        RTSName = record.<String> get ("RTSName");
      }
      if (DbComparator.isEqual ("attribute", attType)) {
        attval.set (null);
        _rtsDll._GetRTSAttribute_SP (connection, clientName, testee.get (), RTSName, attval);

        final String SQL_UPDATE1 = "update ${attributesTblName} set value = case when ${attval} is null then ${NA} else ${attval} end where TDS_ID = ${attname};";
        SqlParametersMaps parms2 = new SqlParametersMaps ().put ("attval", attval.get ()).put ("NA", "NA").put ("attname", attname);
        int updateCnt = executeStatement (connection, fixDataBaseNames (SQL_UPDATE1, unquotedParms), parms2, false).getUpdateCount ();
        // System.err.println (updateCnt); // for testing

      } else if (DbComparator.isEqual ("relationship", attType)) {
        entityKey.set (null);
        entityID.set (null);
        entityName.set (null);
        _rtsDll._GetRTSRelationship_SP (connection, clientName, testee.get (), RTSName, entityKey, entityID, entityName);

        final String SQL_UPDATE2 = "update ${attributesTblName} set value = case when ${entityKey} is null then ${NA} else ${entityName} end, entityKey = ${entityKey}, "
            + " entityID = ${entityID} where TDS_ID = ${attname};";
        SqlParametersMaps parms3 = new SqlParametersMaps ().put ("entityKey", entityKey.get ()).put ("NA", "NA").put ("entityName", entityName.get ()).put ("entityID", entityID.get ())
            .put ("attname", attname);
        int updateCnt = executeStatement (connection, fixDataBaseNames (SQL_UPDATE2, unquotedParms), parms3, false).getUpdateCount ();
        // System.err.println (updateCnt); // for testing
      } else {
        err = String.format ("Unknown attribute type: %s", attType);

        final String SQL_DELETE = "delete from ${attributesTblName} where TDS_ID = ${attname};";
        SqlParametersMaps parms4 = new SqlParametersMaps ().put ("attname", attname);
        int deletedCnt = executeStatement (connection, fixDataBaseNames (SQL_DELETE, unquotedParms), parms4, false).getUpdateCount ();
        // System.err.println (deletedCnt); // for testing
        _commonDll._LogDBError_SP (connection, "GetTesteeAttributes", err, null, null, null, null, clientName, null);
      }
    }
    final String SQL_QUERY4 = "select * from ${attributesTblName} order by type, sortOrder;";
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY4, unquotedParms), null, false).getResultSets ().next ();
    connection.dropTemporaryTable (attributesTable);

    return result;
  }

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @param opportunityKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_ApproveOpportunity_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, UUID opportunityKey) throws ReturnStatusException {

    UUID oppsession = null;
    String teststatus = null;
    Integer numitems = null;
    String accessDenied = null;
    _Ref<String> error = new _Ref<> ();
    String clientName = null;
    Date now = _dateUtil.getDateWRetStatus (connection);

    accessDenied = _commonDll.ValidateProctorSession_FN (connection, proctorKey, sessionKey, browserKey);
    if (accessDenied != null) {
      return _commonDll._ReturnError_SP (connection, clientName, "P_ApproveOpportunity", accessDenied, null, opportunityKey, "ValidateProctorSession");
    }
    final String SQL_QUERY1 = "SELECT _fk_Session as oppsession, status as teststatus, maxitems as numitems, clientname from testopportunity  where _Key = ${opportunitykey} ;";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("opportunitykey", opportunityKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, true).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      oppsession = record.<UUID> get ("oppsession");
      teststatus = record.<String> get ("teststatus");
      numitems = record.<Integer> get ("numitems");
      clientName = record.<String> get ("clientname");
    }
    if (DbComparator.notEqual (accessDenied, null)) {
      error.set (accessDenied);
    }
    if (teststatus == null) {
      error.set ("The test opportunity does not exist");
    }
    if (DbComparator.notEqual ("pending", teststatus) && DbComparator.notEqual ("suspended", teststatus) && DbComparator.notEqual ("segmentEntry", teststatus)
        && DbComparator.notEqual ("segmentExit", teststatus)) {
      error.set ("The test opportunity is not pending approval");
    }
    if (sessionKey != null && oppsession != null && DbComparator.notEqual (sessionKey, oppsession)) {
      error.set ("The test opportunity is not enrolled in this session");
    }
    if (error.get () != null) {
      _commonDll._LogDBError_SP (connection, "P_ApproveOpportunity", error.get (), proctorKey, null, null, sessionKey, null, null);
      _commonDll._LogDBLatency_SP (connection, "P_ApproveOpportunity", now, proctorKey, true, 0, null, sessionKey, null, null);
      return _commonDll._ReturnError_SP (connection, clientName, "P_ApproveOpportunity", error.get (), null, opportunityKey, null);
    }
    // TODO Elena:test
    // Date st = new Date();
    result = _commonDll.SetOpportunityStatus_SP (connection, opportunityKey, "approved", false, sessionKey.toString ());
    // Date end = new Date();
    // long diff = end.getTime () - st.getTime ();
    // System.out.println (String.format
    // ("SetOPportunityStatus latency: %d millisec, status: %s", diff,
    // "approved"));
    _commonDll._LogDBLatency_SP (connection, "P_ApproveOpportunity", now, proctorKey, true, 0, null, sessionKey, null, null);
    return result;
  }

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @param opportunityKey
   * @param segment
   * @param segmentAccoms
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_ApproveAccommodations_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, UUID opportunityKey, Integer segment, String segmentAccoms)
      throws ReturnStatusException {

    UUID oppsession = null;
    String teststatus = null;
    Integer numitems = null;
    String accessDenied = null;
    _Ref<String> error = new _Ref<> ();
    String clientName = null;
    Date now = _dateUtil.getDateWRetStatus (connection);

    accessDenied = _commonDll.ValidateProctorSession_FN (connection, proctorKey, sessionKey, browserKey);
    if (accessDenied != null) {
      return _commonDll._ReturnError_SP (connection, clientName, "P_ApproveAccommodations", accessDenied, null, opportunityKey, "ValidateProctorSession");
    }
    final String SQL_QUERY1 = "SELECT _fk_Session as oppsession, status as teststatus, maxitems as numitems, clientname from testopportunity O where O._Key = ${opportunitykey} ;";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("opportunitykey", opportunityKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, true).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      oppsession = record.<UUID> get ("oppsession");
      teststatus = record.<String> get ("teststatus");
      numitems = record.<Integer> get ("numitems");
      clientName = record.<String> get ("clientname");
    }
    if (DbComparator.notEqual (accessDenied, null)) {
      error.set (accessDenied);
    }
    if (teststatus == null) {
      error.set ("The test opportunity does not exist");
    }
    if (DbComparator.notEqual ("pending", teststatus) && DbComparator.notEqual ("suspended", teststatus) && DbComparator.notEqual ("segmentEntry", teststatus)
        && DbComparator.notEqual ("segmentExit", teststatus)) {
      error.set ("The test opportunity is not pending approval");
    }
    if (sessionKey != null && oppsession != null && DbComparator.notEqual (sessionKey, oppsession)) {
      error.set ("The test opportunity is not enrolled in this session");
    }
    if (error.get () != null) {
      _commonDll._LogDBError_SP (connection, "P_ApproveAccommodations", error.get (), proctorKey, null, null, opportunityKey, null, sessionKey);
      _commonDll._LogDBLatency_SP (connection, "P_ApproveAccommodations", now, proctorKey, true, 0, null, sessionKey, null, null);
      return _commonDll._ReturnError_SP (connection, clientName, "P_ApproveAccommodations", error.get (), null, opportunityKey, null);
    }
    try {
      _commonDll._UpdateOpportunityAccommodations_SP (connection, opportunityKey, segment, segmentAccoms, numitems, true, false, error, 1);
      if (error.get () != null) {
        // we are having trouble with deadlocks on _Update so try one more time
        error.set (String.format ("Accommodations update failed. Making second attempt. %s", error.get ()));
        _commonDll._LogDBError_SP (connection, "P_ApproveAccommodations", error.get (), proctorKey, null, null, opportunityKey, null, sessionKey);
        error.set (null);
        _commonDll._UpdateOpportunityAccommodations_SP (connection, opportunityKey, segment, segmentAccoms, numitems, true, false, error, 1);
        if (error.get () != null) {
          _commonDll._LogDBError_SP (connection, "P_ApproveAccommodations", error.get (), proctorKey, null, null, opportunityKey, null, sessionKey);
          _commonDll._LogDBLatency_SP (connection, "P_ApproveAccommodations", now, proctorKey, true, 0, null, sessionKey, null, null);
          return _commonDll._ReturnError_SP (connection, clientName, "P_ApproveAccommodations", error.get (), null, opportunityKey, null);
        }
      }
    } catch (Exception e) {
      String msg = e.getMessage ();
      _commonDll._LogDBError_SP (connection, "P_ApproveAccommodations", msg, proctorKey, null, null, opportunityKey, null, sessionKey);
      _commonDll._LogDBLatency_SP (connection, "P_ApproveAccommodations", now, proctorKey, true, 0, null, sessionKey, null, null);
      return _commonDll._ReturnError_SP (connection, clientName, "P_ApproveAccommodations", "Accommodations update failed", null, opportunityKey, null);
    }
    _commonDll._LogDBLatency_SP (connection, "P_ApproveAccommodations", now, proctorKey, true, 0, null, sessionKey, null, null);
    return null;
  }

  /**
   * @param connection
   * @param clientName
   * @param browserKey
   * @param sessionName
   * @param proctorKey
   * @param procId
   * @param procName
   * @param dateBegin
   * @param dateEnd
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_CreateSession_SP (SQLConnection connection, String clientName, UUID browserKey, String sessionName, Long proctorKey, String procId, String procName, Date dateBegin,
      Date dateEnd, Integer sessionType) throws ReturnStatusException {

    SingleDataResultSet result = null;
    UUID sessionKey = null;
    _Ref<String> sessionId = new _Ref<> ();
    String environment = null;
    String prefix = null;
    String status = "closed";
    String errMsg = null;
    Date now = _dateUtil.getDateWRetStatus (connection);
    Integer audit = null;
    audit = _commonDll.AuditSessions_FN (connection, clientName);

    final String SQL_QUERY1 = "select environment from _externs where clientname = ${clientname};";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("clientname", clientName);
    result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ()
        .next () : null);
    if (record != null) {
      environment = record.<String> get ("environment");
    }
    if (environment == null) {
      errMsg = String.format ("Unknown client: %s", clientName);
      return _commonDll._ReturnError_SP (connection, clientName, "P_CreateSession", errMsg);
    }
    final String SQL_QUERY2 = "select _Key from session S where clientname = ${clientname} and _efk_Proctor = ${proctorKey} and ${now} between S.DateBegin and S.DateEnd and sessiontype = ${sessiontype} limit 1 ";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("clientname", clientName).put ("proctorKey", proctorKey).put ("now", now).put ("sessiontype", sessionType);
    if (exists (executeStatement (connection, SQL_QUERY2, parms2, true))) {
      return _commonDll._ReturnError_SP (connection, clientName, "P_CreateSession", "There already is an active session for this user.");
    }
    prefix = _commonDll._CoreSessName_FN (connection, clientName, procName);
    sessionKey = UUID.randomUUID ();
    if (dateBegin == null) {
      dateBegin = now;
    }
    if (dateEnd == null) {
      dateEnd = adjustDateHours (dateBegin, 8);
    } else if (DbComparator.lessOrEqual (dateEnd, dateBegin)) {
      dateEnd = adjustDateHours (dateBegin, 8);
    }
    if ((now.equals (dateBegin) || now.after (dateBegin)) && (now.equals (dateEnd) || now.before (dateEnd))) {
      status = "open";
    }
    _commonDll._CreateClientSessionID_SP (connection, clientName, prefix, sessionId);
    if (sessionId.get () == null) {
      return _commonDll._ReturnError_SP (connection, clientName, "P_CreateSession", "Failed to insert new session into database");
    }
    try {
      final String SQL_INSERT = "insert into session (_Key, Name, _efk_Proctor, ProctorID, ProctorName, status, DateBegin, DateEnd, SessionID, _fk_browser, clientname, environment, dateVisited, sessiontype, datecreated, serveraddress) "
          + " values (${sessionKey}, ${sessionName}, ${proctorKey}, ${procID}, ${procName}, ${status}, ${dateBegin}, ${dateEnd}, ${sessionID}, ${browserKey}, ${clientname}, ${environment}, ${now}, ${sessiontype}, now(3), ${hostname});";
      SqlParametersMaps parms3 = new SqlParametersMaps ();
      parms3.put ("sessionKey", sessionKey);
      parms3.put ("sessionName", sessionName);
      parms3.put ("proctorKey", proctorKey);
      parms3.put ("procID", procId);
      parms3.put ("procName", procName);
      parms3.put ("status", status);
      parms3.put ("dateBegin", dateBegin);
      parms3.put ("dateEnd", dateEnd);
      parms3.put ("sessionID", sessionId.toString ());
      parms3.put ("browserKey", browserKey);
      parms3.put ("clientname", clientName);
      parms3.put ("environment", environment);
      parms3.put ("now", now);
      parms3.put ("sessiontype", sessionType);
      parms3.put ("hostname", _commonDll.getLocalhostName ());
      int insertedCnt = executeStatement (connection, SQL_INSERT, parms3, false).getUpdateCount ();

    } catch (Exception e) {
      String err = e.getMessage ();
      _commonDll._LogDBError_SP (connection, "P_CreateSession", err, null, null, null, null, clientName, null);
      _commonDll._LogDBLatency_SP (connection, "P_CreateSession", now, proctorKey, true, null, null, null, clientName, null);
      return _commonDll._ReturnError_SP (connection, clientName, "P_CreateSession", "Failed to insert new session into database");
    }
    String localhostname = _commonDll.getLocalhostName ();

    String sessionDB = getTdsSettings ().getTDSSessionDBName ();
    // String sessionDB = getAppSettings ().get ("TDSSessionDBName");
    if (DbComparator.notEqual (audit, 0)) {
      final String SQL_INSERT1 = "insert into ${ArchiveDB}.sessionaudit (_fk_session, DateAccessed, AccessType, hostname, browserKey, dbname) values (${sessionKey}, ${now}, ${status}, ${hostname}, ${browserKey}, ${dbname});";
      SqlParametersMaps parms4 = new SqlParametersMaps ().put ("sessionKey", sessionKey).put ("now", now).put ("status", status).put ("hostname", localhostname).put ("browserKey", browserKey)
          .put ("dbname", sessionDB);
      int insertedCnt = executeStatement (connection, fixDataBaseNames (SQL_INSERT1), parms4, false).getUpdateCount ();
    }
    List<CaseInsensitiveMap<Object>> resultlist = new ArrayList<CaseInsensitiveMap<Object>> ();
    CaseInsensitiveMap<Object> rcrd = new CaseInsensitiveMap<Object> ();
    rcrd.put ("sessionKey", sessionKey);
    rcrd.put ("sessionID", sessionId.get ());
    rcrd.put ("Name", sessionName);
    rcrd.put ("sessionStatus", status);
    resultlist.add (rcrd);

    result = new SingleDataResultSet ();
    result.addColumn ("sessionKey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER);
    result.addColumn ("sessionID", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    result.addColumn ("Name", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    result.addColumn ("sessionStatus", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    result.addRecords (resultlist);

    _commonDll._LogDBLatency_SP (connection, "P_CreateSession", now, proctorKey, true, null, null, sessionKey, clientName, null);
    return result;
  }

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetActiveCount_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey) throws ReturnStatusException {

    Date today = _dateUtil.getDateWRetStatus (connection);
    List<Date> midnights = getMidnightsWRetStatus (connection);
    Date midnightAM = midnights.get (0);
    Date midnightPM = midnights.get (1);

    String accessDenied = null;
    String client = null;
    accessDenied = _commonDll.ValidateProctorSession_FN (connection, proctorKey, sessionKey, browserKey);
    client = getClientNameBySessionKey (connection, sessionKey);
    if (accessDenied != null) {
      _commonDll._LogDBError_SP (connection, "P_GetActiveCount", accessDenied, proctorKey, null, null, sessionKey);
      _commonDll._LogDBLatency_SP (connection, "P_GetActiveCount", today, proctorKey, true, null, sessionKey);
      return _commonDll._ReturnError_SP (connection, client, "P_GetActiveCount", accessDenied, null, null, "ValidateProctorSession");
    }
    String statusStr = _commonDll.GetStatusCodes_FN (connection, "opportunity", "inuse");

    final String SQL_QUERY = "select bigtoint(count(*)) as active from testopportunity_readonly O where _fk_session = ${sessionKey} and O.DateChanged > ${midnightAM} "
        + " and O.DateChanged < ${midnightPM} and O.status in (${statusStr});";
    Map<String, String> unquotedparms = new HashMap<String, String> ();
    unquotedparms.put ("statusStr", statusStr);
    SqlParametersMaps parms = new SqlParametersMaps ().put ("sessionKey", sessionKey).put ("midnightAM", midnightAM).put ("midnightPM", midnightPM);
    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY, unquotedparms), parms, true).getResultSets ().next ();

    _commonDll._LogDBLatency_SP (connection, "P_GetActiveCount", today, proctorKey, true, null, null, sessionKey, null, null);
    return result;
  }

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetCurrentSessionTestees_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey) throws ReturnStatusException {
    Date now = _dateUtil.getDateWRetStatus (connection);
    List<Date> midnights = getMidnightsWRetStatus (connection);
    Date midnightAM = midnights.get (0);
    Date midnightPM = midnights.get (1);

    String accessDenied = null;
    String client = null;
    Integer suppressScores = null;
    client = getClientNameBySessionKey (connection, sessionKey);
    accessDenied = _commonDll.ValidateProctorSession_FN (connection, proctorKey, sessionKey, browserKey);

    if (accessDenied != null) {
      _commonDll._LogDBError_SP (connection, "P_GetCurrentSessionTestees", accessDenied, proctorKey, null, null, sessionKey);
      _commonDll._LogDBLatency_SP (connection, "P_GetCurrentSessionTestees", now, proctorKey, true, null, sessionKey);
      return _commonDll._ReturnError_SP (connection, client, "P_GetCurrentSessionTestees", accessDenied, null, null, "ValidateProctorSession");
    }
    suppressScores = _SuppressScores_FN (connection, client);

    final String SQL_QUERY = "select tOpp._efk_AdminSubject, tOpp._fk_TestOpportunity as opportunityKey, tOpp._efk_Testee, tOpp._efk_TestID, tOpp.Opportunity,"
        + " tOpp.TesteeName, TesteeID, tOpp.Status, tOpp.DateCompleted, tOpp._fk_Session, tOpp.SessID as SessionID, '' as sessionName,  maxitems as ItemCount,"
        + " case when tOpp.status = ${paused} and datePaused is not null "
        + "   then timestampdiff(MINUTE, datePaused, ${now}) "
        + "   else cast(null as CHAR) end as pauseMinutes,"
        + " numResponses as ResponseCount, (select count(*) from testopprequest REQ where REQ._fk_TestOpportunity = tOpp._fk_TestOpportunity and REQ._fk_Session = ${sessionKey}"
        + " and DateFulfilled is null and DateSubmitted > ${midnightAM} and DateSubmitted < ${midnightPM}) as RequestCountN, (select value as score from testopportunityscores S, "
        + " ${ConfigDB}.client_testscorefeatures F  where F.ClientName = ${clientname} and ReportToProctor = 1 and S._fk_TestOpportunity = tOpp._fk_TestOpportunity and S.IsOfficial = 1 "
        + " and S.MeasureOf = F.MeasureOf and S.MeasureLabel = F.MeasureLabel limit 1) as Score, AccommodationString as Accommodations, tOpp.customAccommodations, tOpp.mode "
        + " from testopportunity_readonly tOpp where _fk_Session = ${sessionKey} and tOpp.DateChanged > ${midnightAM} and tOpp.DateChanged < ${midnightPM}  and tOpp.status not in ('pending', 'suspended', 'denied');";

    SqlParametersMaps parms = new SqlParametersMaps ().put ("paused", "paused").put ("clientname", client).put ("now", now).put ("sessionKey", sessionKey).put ("midnightPM", midnightPM)
        .put ("midnightAM", midnightAM);
    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY), parms, true).getResultSets ().next ();
    result.addColumn ("RequestCount", SQL_TYPE_To_JAVA_TYPE.INT);
    Iterator<DbResultRecord> records = result.getRecords ();
    while (records.hasNext ()) {
      DbResultRecord record = records.next ();
      record.addColumnValue ("RequestCount", (record.<Long> get ("RequestCountN") == null ? null : record.<Long> get ("RequestCountN").intValue ()));
    }
    _commonDll._LogDBLatency_SP (connection, "P_GetCurrentSessionTestees", now, proctorKey, true, null, null, sessionKey, null, null);
    return result;
  }

  /**
   * @param connection
   * @param clientName
   * @param externalId
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetRTSTestee_SP (SQLConnection connection, String clientName, String externalId) throws ReturnStatusException {

    SingleDataResultSet result = GetTesteeAttributes_SP (connection, clientName, externalId);
    return result;
  }

  public SingleDataResultSet P_GetCurrentSessions_SP (SQLConnection connection, String clientName, Long proctorKey) throws ReturnStatusException {
    return P_GetCurrentSessions_SP (connection, clientName, proctorKey, 0);
  }

  /**
   * @param connection
   * @param clientName
   * @param proctorKey
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetCurrentSessions_SP (SQLConnection connection, String clientName, Long proctorKey, int sessionType) throws ReturnStatusException {
    // long st1 = System.currentTimeMillis();
    Date today = _dateUtil.getDateWRetStatus (connection);
    List<Date> midnights = getMidnightsWRetStatus (connection);
    Date midnightAM = midnights.get (0);
    Date midnightPM = midnights.get (1);
    SingleDataResultSet result = null;

    final String SQL_QUERY1 = "select _efk_Proctor from session S, timelimits T where T.clientname = ${clientname} and _efk_Proctor = ${proctorKey} and sessionType = ${sessionType}"
        + " and status <> ${closed} and dateend > ${today}  and S.clientname = T.clientname and TACheckinTime is not null and TACheckinTime > 0"
        + " and (DateVisited + INTERVAL TACheckInTime MINUTE) < ${today} limit 1";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("closed", "closed").put ("clientname", clientName).put ("today", today).put ("sessionType", sessionType).put ("proctorKey", proctorKey);

    while (exists (executeStatement (connection, SQL_QUERY1, parms, false))) {
      long st = System.currentTimeMillis ();
      final String SQL_QUERY2 = "select _Key as _key, _fk_browser as browser from session S, timelimits T where T.clientname = ${clientname} and _efk_Proctor = ${proctorKey}"
          + " and sessionType = ${sessionType} and status <> ${closed} and dateend > ${today} and S.clientname = T.clientname and TACheckinTime is not null and TACheckinTime > 0"
          + " and (DateVisited + INTERVAL TACheckInTime MINUTE) < ${today} order by (DateVisited + INTERVAL TACheckInTime MINUTE) limit 1;";
      SqlParametersMaps parms1 = parms;
      result = executeStatement (connection, SQL_QUERY2, parms1, false).getResultSets ().next ();
      DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      // long diff0 = System.currentTimeMillis() - st;
      // st = System.currentTimeMillis();
      if (record != null) {
        UUID key = record.<UUID> get ("_key");
        UUID browser = record.<UUID> get ("browser");
        if (key != null) {
          _commonDll.P_PauseSession_SP (connection, key, proctorKey, browser, "administratively closed", false);
        }
      }
      // long diff1 = System.currentTimeMillis() - st;
      // TODO Elena test
      // System.out.println (String.format
      // ("Prt1 looping chunk1 latency: %d millisec, chunk2 latency: %d", diff0,
      // diff1));
    }
    // long diff = System.currentTimeMillis() - st1;
    // TODO Elena test
    // System.out.println (String.format ("Prt1 latency: %d millisec", diff));

    // st1 = System.currentTimeMillis();
    final String SQL_QUERY3 = "select S._Key, S.SessionID, S.Name as sessionName, S.status, dateBegin, dateEnd, _fk_browser as browserKey, (SELECT COUNT(*) FROM "
        + " testopportunity_readonly O where O._fk_Session = S._key AND O.DateChanged > ${midnightAM} and O.DateChanged < ${midnightPM} AND O.status IN (${pending}, ${suspended})) as NeedApprovalN"
        + " from session S where clientname = ${clientname} and _efk_Proctor = ${proctorKey} and sessiontype = ${sessiontype} and ${today} >= S.DateBegin and"
        + " S.DateEnd >= ${today} and status = ${open} order by S.name;";
    SqlParametersMaps parms2 = new SqlParametersMaps ().put ("pending", "pending").put ("suspended", "suspended").put ("clientname", clientName).put ("midnightPM", midnightPM)
        .put ("midnightAM", midnightAM).put ("sessionType", sessionType).put ("proctorKey", proctorKey).put ("open", "open").put ("today", today);
    result = executeStatement (connection, SQL_QUERY3, parms2, true).getResultSets ().next ();
    result.addColumn ("NeedApproval", SQL_TYPE_To_JAVA_TYPE.INT);
    Iterator<DbResultRecord> records = result.getRecords ();
    while (records.hasNext ()) {
      DbResultRecord record = records.next ();
      record.addColumnValue ("NeedApproval", (record.<Long> get ("NeedApprovalN") == null ? null : record.<Long> get ("NeedApprovalN").intValue ()));
    }
    // diff = System.currentTimeMillis() - st1;
    _commonDll._LogDBLatency_SP (connection, "P_GetCurrentSessions", today, proctorKey, true, null, null, null, clientName, null);

    // TODO Elena test
    // System.out.println (String.format ("Prt2 latency: %d millisec", diff));
    return result;
  }

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetSessionTests_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey) throws ReturnStatusException {

    Date today = _dateUtil.getDateWRetStatus (connection);
    String accessDenied = null;
    String client = null;

    accessDenied = _commonDll.ValidateProctorSession_FN (connection, proctorKey, sessionKey, browserKey);

    if (accessDenied != null) {
      client = getClientNameBySessionKey (connection, sessionKey);
      _commonDll._LogDBError_SP (connection, "P_GetSessionTests", accessDenied, proctorKey, null, null, sessionKey);
      _commonDll._LogDBLatency_SP (connection, "P_GetSessionTests", today, proctorKey, true, null, sessionKey);
      return _commonDll._ReturnError_SP (connection, client, "P_GetSessionTests", accessDenied, null, null, "ValidateProctorSession");
    }
    final String SQL_QUERY = "SELECT _efk_TestID AS TestID, _efk_AdminSubject as TestKey  FROM sessiontests WHERE _fk_Session = ${sessionKey}";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("sessionKey", sessionKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY, parms, true).getResultSets ().next ();

    _commonDll._LogDBLatency_SP (connection, "P_GetSessionTests", today, proctorKey, true, null, null, sessionKey, null, null);
    return result;
  }

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @return
   * @throws ReturnStatusException
   */
  public MultiDataResultSet P_GetTestsForApproval_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey) throws ReturnStatusException {

    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();
    SingleDataResultSet result = null;
    Date today = _dateUtil.getDateWRetStatus (connection);
    String accessDenied = null;
    String client = null;

    if (proctorKey != null)
      accessDenied = _commonDll.ValidateProctorSession_FN (connection, proctorKey, sessionKey, browserKey);

    if (accessDenied != null) {
      client = getClientNameBySessionKey (connection, sessionKey);
      _commonDll._LogDBError_SP (connection, "P_GetTestsForApproval", accessDenied, proctorKey, null, null, sessionKey);
      _commonDll._LogDBLatency_SP (connection, "P_GetTestsForApproval", today, proctorKey, true, null, sessionKey);
      resultsets.add (_commonDll._ReturnError_SP (connection, client, "P_GetTestsForApproval", accessDenied, null, null, "ValidateProctorSession"));
      return new MultiDataResultSet (resultsets);
    }
    DataBaseTable oppsTable = getDataBaseTable ("opps").addColumn ("opportunityKey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER).addColumn ("_efk_testee", SQL_TYPE_To_JAVA_TYPE.BIGINT)
        .addColumn ("_efk_TestID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200).addColumn ("Opportunity", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("_efk_AdminSubject", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250)
        .addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("testeeID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("testeeName", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100)
        .addColumn ("customAccommodations", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("waitingForSegment", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("mode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50)
        .addColumn ("LEP", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100);
    connection.createTemporaryTable (oppsTable);
    Map<String, String> unquotedparms = new HashMap<String, String> ();
    unquotedparms.put ("oppsTableName", oppsTable.getTableName ());

    DataBaseTable accsTable = getDataBaseTable ("accs").addColumn ("oppKey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER).addColumn ("AccType", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100)
        .addColumn ("AccCode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 1000).addColumn ("AccValue", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).addColumn ("segment", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("isSelectable", SQL_TYPE_To_JAVA_TYPE.BIT);
    connection.createTemporaryTable (accsTable);
    Map<String, String> unquotedparms1 = new HashMap<String, String> ();
    unquotedparms1.put ("accsTableName", accsTable.getTableName ());

    final String SQL_INSERT = "insert into ${oppsTableName} (opportunityKey, _efk_Testee, _efk_TestID, Opportunity, _efk_AdminSubject, status, testeeID, testeeName, customAccommodations, "
        + " waitingForSegment, mode) select  _fk_TestOpportunity, _efk_Testee, _efk_TestID, Opportunity, _efk_AdminSubject, status, testeeID, testeeName, customAccommodations,"
        + " waitingForSegment, mode from testopportunity_readonly O where _fk_Session = ${sessionKey} and O.status in (${pending}, ${suspended}, ${segmentEntry}, ${segmentExit});";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey).put ("pending", "pending").put ("suspended", "suspended").put ("segmentEntry", "segmentEntry")
        .put ("segmentExit", "segmentExit");
    int insertedCnt = executeStatement (connection, fixDataBaseNames (SQL_INSERT, unquotedparms), parms1, false).getUpdateCount ();

    final String SQL_QUERY1 = "select opportunityKey from ${oppsTableName} limit 1";
    if (!exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY1, unquotedparms), null, false))) {
      final String SQL_QUERY2 = "select * from ${oppsTableName}";
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY2, unquotedparms), null, false).getResultSets ().next ();
      resultsets.add (result);

      final String SQL_QUERY3 = "select * from ${accsTableName}, ${oppsTableName}; ";
      Map<String, String> unquotedparms2 = new HashMap<String, String> ();
      unquotedparms2.put ("accsTableName", accsTable.getTableName ());
      unquotedparms2.put ("oppsTableName", oppsTable.getTableName ());
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY3, unquotedparms2), null, false).getResultSets ().next ();
      resultsets.add (result);
      _commonDll._LogDBLatency_SP (connection, "P_GetTestsForApproval", today, proctorKey, true, null, null, sessionKey, null, null);
      connection.dropTemporaryTable (accsTable);
      connection.dropTemporaryTable (oppsTable);
      return new MultiDataResultSet (resultsets);
    }
    // TODO, Need to address the Create Index, when migrating to MY_SQL
    final String SQL_INDEX1 = "create index TMP_OPPS on ${oppsTableName} (opportunityKey);";
    executeStatement (connection, fixDataBaseNames (SQL_INDEX1, unquotedparms), null, false).getUpdateCount ();

    final String SQL_UPDATE1 = "update ${oppsTableName} O, testeeattribute A set O.LEP = A.attributeValue   where A._fk_TestOpportunity = O.opportunityKey and A.TDS_ID = ${LEP}";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("LEP", "LEP");
    int updateCnt = executeStatement (connection, fixDataBaseNames (SQL_UPDATE1, unquotedparms), parms2, false).getUpdateCount ();

    final String SQL_INSERT1 = "insert into ${accsTableName} (oppkey, AccType, AccCode, AccValue, segment, isSelectable) select opportunityKey, AccType, AccCode, AccValue, "
        + " segment, isSelectable from testeeaccommodations A, ${oppsTableName} where opportunityKey = A._fk_TestOpportunity;";
    Map<String, String> unquotedparms3 = new HashMap<String, String> ();
    unquotedparms3.put ("accsTableName", accsTable.getTableName ());
    unquotedparms3.put ("oppsTableName", oppsTable.getTableName ());
    insertedCnt = executeStatement (connection, fixDataBaseNames (SQL_INSERT1, unquotedparms3), null, false).getUpdateCount ();

    // TODO, Need to address the Create Index, when migrating to MY_SQL
    final String SQL_INDEX2 = "create index TMP_OPPS on ${accsTableName} (oppKey);";
    executeStatement (connection, fixDataBaseNames (SQL_INDEX2, unquotedparms1), null, false).getUpdateCount ();

    final String SQL_QUERY4 = "select distinct * from ${oppsTableName} where exists (select oppKey from ${accsTableName} where oppkey = opportunityKey);";
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY4, unquotedparms3), null, false).getResultSets ().next ();
    resultsets.add (result);

    final String SQL_QUERY5 = "select distinct * from ${accsTableName}, ${oppsTableName} where oppkey = opportunityKey and (segment = 0 or  isSelectable = 1);";
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY5, unquotedparms3), null, false).getResultSets ().next ();
    resultsets.add (result);

    _commonDll._LogDBLatency_SP (connection, "P_GetTestsForApproval", today, proctorKey, true, null, null, sessionKey, null, null);
    connection.dropTemporaryTable (accsTable);
    connection.dropTemporaryTable (oppsTable);

    return new MultiDataResultSet (resultsets);
  }

  /**
   * @param connection
   * @param clientName
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetCurrentAlertMessages_SP (SQLConnection connection, String clientName) throws ReturnStatusException {

    SingleDataResultSet result = null;
    Date now = _dateUtil.getDateWRetStatus (connection);
    final String SQL_QUERY = "select _key, title, message, dateCreated, createdUser, dateUpdated, updatedUser, dateStarted, dateEnded, dateCancelled, cancelledUser "
        + " from alertmessages where clientname = ${clientname} and dateStarted <= ${now} and dateEnded > ${now} and dateCancelled is null order by dateStarted desc;";
    SqlParametersMaps parameters = new SqlParametersMaps ().put ("clientname", clientName).put ("now", now);
    result = executeStatement (connection, SQL_QUERY, parameters, true).getResultSets ().next ();

    _commonDll._LogDBLatency_SP (connection, "P_GetCurrentAlertMessages", now, null, true, null, null, null, clientName, null);
    return result;
  }

  /**
   * @param connection
   * @param clientName
   * @param proctorKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetUnAcknowledgedAlertMessages_SP (SQLConnection connection, String clientName, Long proctorKey) throws ReturnStatusException {

    Date today = _dateUtil.getDateWRetStatus (connection);

    final String SQL_QUERY = "select _key, title, message, dateCreated, createdUser, dateUpdated, updatedUser, dateStarted, dateEnded, dateCancelled, cancelledUser "
        + " from alertmessages AM  left outer join setofproctoralertmessages S on AM._key = S._fk_AlertMessages and S._efk_Proctor= ${proctorKey} and "
        + " S.dateChanged < ${today} and S.dateChanged > ${today} where AM.dateStarted <= ${today} and AM.dateEnded > ${today} and AM.dateCancelled is null "
        + " and S._efk_Proctor is null and AM.clientname = ${clientname};";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("proctorKey", proctorKey).put ("clientname", clientName).put ("today", today);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY, parms, true).getResultSets ().next ();

    if (result.getCount () > 0) {
      final String SQL_INSERT = "insert into setofproctoralertmessages (_efk_Proctor, _fk_AlertMessages, dateChanged) select ${proctorKey}, AM._key, ${today} from "
          + " alertmessages AM left outer join setofproctoralertmessages S on AM._key = S._fk_AlertMessages and S._efk_Proctor = ${proctorKey} and S.dateChanged >= ${today} and S.dateChanged <= ${today} "
          + " where AM.dateStarted <= ${today} and AM.dateEnded > ${today} and AM.dateCancelled is null and S._efk_Proctor is null and AM.clientname = ${clientname};";
      SqlParametersMaps parms1 = parms;
      int insertedCnt = executeStatement (connection, SQL_INSERT, parms1, true).getUpdateCount ();
    }
    _commonDll._LogDBLatency_SP (connection, "P_GetUnAcknowledgedAlertMessages", today, proctorKey, true, null, null, null, clientName, null);
    return result;
  }

  /**
   * @param connection
   * @param clientName
   * @param proctorKey
   * @param sessionID
   * @param browserKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_HandOffSession_SP (SQLConnection connection, String clientName, Long proctorKey, String sessionID, UUID browserKey) throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);
    _Ref<UUID> sessionKey = new _Ref<> ();

    String localhostname = _commonDll.getLocalhostName ();

    final String SQL_QUERY1 = "select _Key as sessionKey from session  where clientname = ${clientname} and _efk_Proctor = ${proctorkey} and sessionID = ${sessionID}"
        + " and status = ${open} and ${starttime} between dateBegin and dateEnd;";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("proctorKey", proctorKey).put ("clientname", clientName).put ("starttime", starttime).put ("sessionID", sessionID)
        .put ("open", "open");
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      sessionKey.set (record.<UUID> get ("sessionKey"));
    }
    if (sessionKey.get () == null) {
      String client = getClientNameBySessionKey (connection, sessionKey.get ());
      return _commonDll._ReturnError_SP (connection, client, "P_HandOffSession", "The session does not exist");
    }
    // String sessionDB = getAppSettings ().get ("TDSSessionDBName");
    String sessionDB = getTdsSettings ().getTDSSessionDBName ();
    final String SQL_INSERT = "insert into ${ArchiveDB}.sessionaudit (_fk_session, DateAccessed, AccessType, hostname, browserKey, dbname) values (${sessionKey}, ${starttime}, ${Handoff}, ${localhostname}, ${browserKey}, ${dbname});";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("sessionKey", sessionKey.get ()).put ("starttime", starttime).put ("Handoff", "Handoff").put ("localhostname", localhostname)
        .put ("browserKey", browserKey).put ("dbname", sessionDB);
    int insertedCnt = executeStatement (connection, fixDataBaseNames (SQL_INSERT), parms1, false).getUpdateCount ();

    final String SQL_UPDATE = "update session set _fk_Browser = ${browserkey}, dateChanged = ${starttime}, DateVisited = ${starttime}  where _Key = ${sessionKey};";
    SqlParametersMaps parms2 = new SqlParametersMaps ().put ("sessionKey", sessionKey.get ()).put ("starttime", starttime).put ("browserKey", browserKey);
    int updateCnt = executeStatement (connection, SQL_UPDATE, parms2, false).getUpdateCount ();

    List<CaseInsensitiveMap<Object>> resultlist = new ArrayList<CaseInsensitiveMap<Object>> ();
    CaseInsensitiveMap<Object> rcrd = new CaseInsensitiveMap<Object> ();
    rcrd.put ("sessionKey", sessionKey.get ());
    resultlist.add (rcrd);
    result = new SingleDataResultSet ();
    result.addColumn ("sessionKey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER);
    result.addRecords (resultlist);

    _commonDll._LogDBLatency_SP (connection, "P_HandOffSession", starttime, null, true, null, null, null, clientName, null);
    return result;
  }

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @param testKey
   * @param testId
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_InsertSessionTest_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, String testKey, String testId) throws ReturnStatusException {

    Date today = _dateUtil.getDateWRetStatus (connection);
    String accessDenied = null;
    String client = null;
    SingleDataResultSet result = null;

    accessDenied = _commonDll.ValidateProctorSession_FN (connection, proctorKey, sessionKey, browserKey);

    if (accessDenied != null) {
      client = getClientNameBySessionKey (connection, sessionKey);
      _commonDll._LogDBError_SP (connection, "P_InsertSessionTest", accessDenied, proctorKey, null, null, sessionKey);
      _commonDll._LogDBLatency_SP (connection, "P_InsertSessionTest", today, proctorKey, true, null, sessionKey);
      return _commonDll._ReturnError_SP (connection, client, "P_InsertSessionTest", accessDenied, null, null, "ValidateProctorSession");
    }
    final String SQL_QUERY1 = "select _fk_Session from sessiontests where _fk_Session = ${sessionKey} and _efk_AdminSubject = ${testKey} limit 1";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("sessionKey", sessionKey).put ("testKey", testKey);
    if (exists (executeStatement (connection, SQL_QUERY1, parms1, false))) {
      return _commonDll._ReturnError_SP (connection, client, "P_InsertSessionTest", "SessionTestExists");
    }
    final String SQL_INSERT = "INSERT INTO sessiontests (_efk_AdminSubject, _efk_TestID, _fk_Session) VALUES (${testKey}, ${testID}, ${sessionKey});";
    SqlParametersMaps parms2 = new SqlParametersMaps ().put ("sessionKey", sessionKey).put ("testID", testId).put ("testKey", testKey);
    int insertedCnt = executeStatement (connection, SQL_INSERT, parms2, false).getUpdateCount ();

    result = _commonDll.ReturnStatusReason ("success", null);

    _commonDll._LogDBLatency_SP (connection, "P_InsertSessionTest", today, null, true, null, null, sessionKey, null, null);
    return result;
  }

  public SingleDataResultSet P_PauseAllSessions_SP (SQLConnection connection, String clientName, Long proctorKey, UUID browserKey, int sessionType) throws ReturnStatusException {
    return P_PauseAllSessions_SP (connection, clientName, proctorKey, browserKey, 0, 1, sessionType);
  }

  /**
   * @param connection
   * @param clientName
   * @param proctorKey
   * @param browserKey
   * @param exemptCurrent
   * @param reportClosed
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_PauseAllSessions_SP (SQLConnection connection, String clientName, Long proctorKey, UUID browserKey, int exemptCurrent, int reportClosed, int sessionType)
      throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);
    Integer audit = null;
    Integer oppaudit = null;
    Long cnt = null;
    UUID oppKey = null;
    UUID sessionKey = null;

    String localhostname = _commonDll.getLocalhostName ();
    audit = _commonDll.AuditSessions_FN (connection, clientName);
    oppaudit = _commonDll.AuditOpportunities_FN (connection, clientName);
    String statusStr = _commonDll.GetStatusCodes_FN (connection, "opportunity", "inuse");

    DataBaseTable sessionTable = getDataBaseTable ("sessions").addColumn ("sesskey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER);
    connection.createTemporaryTable (sessionTable);
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("sessionTableName", sessionTable.getTableName ());

    if (DbComparator.notEqual (exemptCurrent, 0)) {
      final String SQL_INSERT1 = "insert into ${sessionTableName} (sesskey) (select _key from session where clientname = ${clientname} and _efk_Proctor = ${proctorKey}"
          + " and _fk_Browser = ${browserKey} and status = ${open} and DateEnd < ${now} and sessiontype = ${sessiontype});";
      SqlParametersMaps parms = new SqlParametersMaps ().put ("proctorKey", proctorKey).put ("clientname", clientName).put ("now", starttime).put ("browserKey", browserKey)
          .put ("open", "open").put ("sessiontype", sessionType);
      int insertedCnt = executeStatement (connection, fixDataBaseNames (SQL_INSERT1, unquotedParms), parms, false).getUpdateCount ();
    } else {
      final String SQL_INSERT2 = "insert into ${sessionTableName} (sesskey) (select _key from session where clientname = ${clientname} and _efk_Proctor = ${proctorKey}"
          + " and _fk_Browser = ${browserKey} and status = ${open} and sessiontype = ${sessiontype});";
      SqlParametersMaps parms1 = new SqlParametersMaps ().put ("proctorKey", proctorKey).put ("clientname", clientName).put ("browserKey", browserKey).put ("open", "open")
          .put ("sessiontype", sessionType);
      int insertedCnt = executeStatement (connection, fixDataBaseNames (SQL_INSERT2, unquotedParms), parms1, false).getUpdateCount ();
    }
    final String SQL_QUERY1 = "select count(*) as cnt from ${sessionTableName};";
    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY1, unquotedParms), null, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      cnt = record.<Long> get ("cnt");
    }
    if (DbComparator.lessThan (cnt, 1)) {
      if (reportClosed == 1) {
        List<CaseInsensitiveMap<Object>> resultlist = new ArrayList<CaseInsensitiveMap<Object>> ();
        CaseInsensitiveMap<Object> rcrd = new CaseInsensitiveMap<Object> ();
        rcrd.put ("status", "closed");
        rcrd.put ("number", cnt);
        rcrd.put ("reason", null);
        resultlist.add (rcrd);

        SingleDataResultSet rs = new SingleDataResultSet ();
        rs.addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
        rs.addColumn ("number", SQL_TYPE_To_JAVA_TYPE.INT);
        rs.addColumn ("reason", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
        rs.addRecords (resultlist);

        _commonDll._LogDBLatency_SP (connection, "P_PauseAllSessions", starttime, proctorKey, true, null, null);
        connection.dropTemporaryTable (sessionTable);
        return rs;
      }
    }
    DataBaseTable oppsTable = getDataBaseTable ("opps").addColumn ("oppkey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER).addColumn ("_fk_Session", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER);
    connection.createTemporaryTable (oppsTable);
    Map<String, String> unquotedParms1 = new HashMap<String, String> ();
    unquotedParms1.put ("oppsTableName", oppsTable.getTableName ());

    final String SQL_INSERT3 = "insert into ${oppsTableName} (oppkey, _fk_Session) select _Key, _fk_session from testopportunity where status in (${statusStr}) "
        + " and _fk_Session in (select sesskey from ${sessionTableName})";
    Map<String, String> unquotedParms2 = new HashMap<String, String> ();
    unquotedParms2.put ("oppsTableName", oppsTable.getTableName ());
    unquotedParms2.put ("sessionTableName", sessionTable.getTableName ());
    unquotedParms2.put ("statusStr", statusStr);
    int insertedCnt = executeStatement (connection, fixDataBaseNames (SQL_INSERT3, unquotedParms2), null, true).getUpdateCount ();

    // String sessionDB = getAppSettings ().get ("TDSSessionDBName");
    String sessionDB = getTdsSettings ().getTDSSessionDBName ();
    if (DbComparator.notEqual (oppaudit, 0)) {
      final String SQL_INSERT4 = "insert into ${ArchiveDB}.opportunityaudit(_fk_TestOpportunity, DateAccessed, AccessType, _fk_Session, Hostname, dbname) "
          + " select oppkey, ${now}, ${paused by session}, _fk_Session, ${localhostname}, ${dbname} from ${oppsTableName} ;";
      final String query = fixDataBaseNames (SQL_INSERT4); // to substitute
                                                           // ${ArchiveDB}
      SqlParametersMaps parms3 = new SqlParametersMaps ().put ("paused by session", "paused by session").put ("now", starttime).put ("localhostname", localhostname).put ("dbname", sessionDB);
      insertedCnt = executeStatement (connection, fixDataBaseNames (query, unquotedParms1), parms3, false).getUpdateCount ();
    }
    // final String SQL_QUERY3 = "select oppkey from ${oppsTableName} limit 1";
    // while (exists (executeStatement (connection, fixDataBaseNames
    // (SQL_QUERY3, unquotedParms1), null, false))) {
    // final String SQL_QUERY4 =
    // "select oppkey as oppkey, _fk_session as sessionKey from ${oppsTableName} limit 1;";
    // result = executeStatement (connection, fixDataBaseNames (SQL_QUERY4,
    // unquotedParms1), null, false).getResultSets ().next ();
    // record = result.getCount () > 0 ? result.getRecords ().next () : null;
    // if (record != null) {
    // oppKey = record.<UUID> get ("oppkey");
    // sessionKey = record.<UUID> get ("sessionKey");
    // }
    // final String SQL_DELETE =
    // "delete from ${oppsTableName} where oppkey = ${oppkey};";
    // SqlParametersMaps parms4 = new SqlParametersMaps ().put ("oppkey",
    // oppKey);
    // int deletedCnt = executeStatement (connection, fixDataBaseNames
    // (SQL_DELETE, unquotedParms1), parms4, false).getUpdateCount ();

    // _commonDll.SetOpportunityStatus_SP (connection, oppKey, "paused", true,
    // sessionKey.toString ());
    // }

    final String SQL_QUERY4 = "select oppkey as oppkey, _fk_session as sessionKey from ${oppsTableName}";
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY4, unquotedParms1), null, false).getResultSets ().next ();
    Iterator<DbResultRecord> records = result.getRecords ();
    while (records.hasNext ()) {
      record = records.next ();
      oppKey = record.<UUID> get ("oppkey");
      sessionKey = record.<UUID> get ("sessionKey");
      // TODO Elena test
      // Date st = new Date();
      _commonDll.SetOpportunityStatus_SP (connection, oppKey, "paused", true, sessionKey.toString ());
      // Date end = new Date();
      // long diff = end.getTime () - st.getTime ();
      // System.out.println (String.format
      // ("SetOPportunityStatus latency: %d millisec, status: %s", diff,
      // "paused"));
    }

    if (DbComparator.notEqual (audit, 0)) {
      final String SQL_INSERT5 = "insert into ${ArchiveDB}.sessionaudit (_fk_Session, DateAccessed, AccessType, hostname, browserKey, dbname)"
          + " select sesskey, ${now}, ${closed}, ${host}, ${browserKey}, ${dbname} from ${sessionTableName};";
      final String query = fixDataBaseNames (SQL_INSERT5); // to substitute
                                                           // ${ArchiveDB}
      SqlParametersMaps parms5 = new SqlParametersMaps ().put ("closed", "closed").put ("now", starttime).put ("host", localhostname).put ("browserKey", browserKey).put ("dbname", sessionDB);
      insertedCnt = executeStatement (connection, fixDataBaseNames (query, unquotedParms), parms5, false).getUpdateCount ();
    }
    final String SQL_UPDATE = "update session S, ${sessionTableName} N set S.status = ${closed}, S.DateChanged = ${now} where S._Key = N.sesskey;";
    SqlParametersMaps parms6 = new SqlParametersMaps ().put ("closed", "closed").put ("now", starttime);
    int updateCnt = executeStatement (connection, fixDataBaseNames (SQL_UPDATE, unquotedParms), parms6, false).getUpdateCount ();

    if (reportClosed == 1) {
      List<CaseInsensitiveMap<Object>> resultlist = new ArrayList<CaseInsensitiveMap<Object>> ();
      CaseInsensitiveMap<Object> rcrd = new CaseInsensitiveMap<Object> ();
      rcrd.put ("status", "closed");
      rcrd.put ("number", cnt);
      rcrd.put ("reason", null);
      resultlist.add (rcrd);

      result = new SingleDataResultSet ();
      result.addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("number", SQL_TYPE_To_JAVA_TYPE.INT);
      result.addColumn ("reason", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addRecords (resultlist);
    }
    connection.dropTemporaryTable (sessionTable);
    connection.dropTemporaryTable (oppsTable);
    _commonDll._LogDBLatency_SP (connection, "P_PauseAllSessions", starttime, proctorKey, true, null, null, null, clientName, null);
    return result;
  }

  public SingleDataResultSet P_LogOutProctor_SP (SQLConnection connection, String clientName, Long proctorKey, UUID browserKey) throws ReturnStatusException {
    return P_LogOutProctor_SP (connection, clientName, proctorKey, browserKey, 0);
  }

  /**
   * @param connection
   * @param clientName
   * @param proctorKey
   * @param browserKey
   * @param sessionType
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_LogOutProctor_SP (SQLConnection connection, String clientName, Long proctorKey, UUID browserKey, int sessionType) throws ReturnStatusException {

    Date now = _dateUtil.getDateWRetStatus (connection);

    SingleDataResultSet result = null;

    result = P_PauseAllSessions_SP (connection, clientName, proctorKey, browserKey, sessionType);

    final String SQL_UPDATE = "update session set DateEnd = ${now}, status = ${closed} where clientname = ${clientname} and _efk_Proctor = ${proctorKey}"
        + " and (DateEnd > ${now} or status = ${open}) and _fk_Browser = ${browserKey};";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("closed", "closed").put ("now", now).put ("clientname", clientName).put ("proctorKey", proctorKey)
        .put ("open", "open").put ("browserKey", browserKey);
    int updateCnt = executeStatement (connection, SQL_UPDATE, parms, false).getUpdateCount ();

    _commonDll._LogDBLatency_SP (connection, "P_LogOutProctor", now, proctorKey, true, null, null, null, clientName, null);
    return result;
  }

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @param opportunityKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_PauseOpportunity_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, UUID opportunityKey) throws ReturnStatusException {

    Date today = _dateUtil.getDateWRetStatus (connection);
    String accessDenied = null;
    String client = null;
    SingleDataResultSet result = null;

    accessDenied = _commonDll.ValidateProctorSession_FN (connection, proctorKey, sessionKey, browserKey);

    if (accessDenied != null) {
      client = getClientNameBySessionKey (connection, sessionKey);
      _commonDll._LogDBError_SP (connection, "P_PauseOpportunity", accessDenied, proctorKey, null, null, sessionKey);
      _commonDll._LogDBLatency_SP (connection, "P_PauseOpportunity", today, proctorKey, true, null, sessionKey);
      return _commonDll._ReturnError_SP (connection, client, "P_PauseOpportunity", accessDenied, null, null, "ValidateProctorSession");
    }
    Date st = new Date ();
    result = _commonDll.SetOpportunityStatus_SP (connection, opportunityKey, "paused", false, sessionKey.toString ());
    Date end = new Date ();
    long diff = end.getTime () - st.getTime ();
    System.out.println (String.format ("SetOPportunityStatus latency: %d millisec, status: %s", diff, "paused"));
    _commonDll._LogDBLatency_SP (connection, "P_PauseOpportunity", today, null, true, null, null, sessionKey, null, null);
    return result;
  }

  public SingleDataResultSet P_ResumeAllSessions_SP (SQLConnection connection, String clientName, Long proctorKey, UUID browserKey) throws ReturnStatusException {
    return P_ResumeAllSessions_SP (connection, clientName, proctorKey, browserKey, 0, 0);
  }

  /**
   * @param connection
   * @param clientName
   * @param proctorKey
   * @param browserKey
   * @param suppressOutput
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_ResumeAllSessions_SP (SQLConnection connection, String clientName, Long proctorKey, UUID browserKey, Integer suppressOutput, int sessionType)
      throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);
    Integer audit = null;

    String localhostname = _commonDll.getLocalhostName ();
    audit = _commonDll.AuditSessions_FN (connection, clientName);
    SingleDataResultSet result = null;

    final String SQL_UPDATE = "update session set status = ${open}, DateChanged = ${now}, DateVisited = ${now}, _fk_Browser = ${browserKey} where clientname = ${clientname} "
        + " and _efk_Proctor = ${proctorKey} and sessiontype = ${sessiontype} and ${now} between DateBegin and DateEnd and status = ${closed};";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("closed", "closed").put ("now", starttime).put ("clientname", clientName).put ("proctorKey", proctorKey)
        .put ("open", "open").put ("browserKey", browserKey).put ("sessiontype", sessionType);
    int updateCnt = executeStatement (connection, SQL_UPDATE, parms, false).getUpdateCount ();

    // String sessionDB = getAppSettings ().get ("TDSSessionDBName");
    String sessionDB = getTdsSettings ().getTDSSessionDBName ();
    if (DbComparator.notEqual (audit, 0)) {
      final String SQL_INSERT = "insert into ${ArchiveDB}.sessionaudit (_fk_Session, DateAccessed, AccessType, hostname, browserKey, dbname) "
          + " select _Key, ${now}, ${open}, ${localhostname}, ${browserKey}, ${dbname} from session where _fk_Browser = ${browserKey};";
      SqlParametersMaps parms1 = new SqlParametersMaps ().put ("now", starttime).put ("open", "open").put ("browserKey", browserKey).put ("localhostname", localhostname).put ("dbname", sessionDB);
      int insertedCnt = executeStatement (connection, fixDataBaseNames (SQL_INSERT), parms1, false).getUpdateCount ();
    }
    if (suppressOutput == 0) {
      final String SQL_QUERY = "select ${open} as status, count(*) as numberN, cast(null as CHAR) as reason from session where _fk_Browser = ${browserKey} ;";
      SqlParametersMaps parms3 = new SqlParametersMaps ().put ("open", "open").put ("browserKey", browserKey);
      result = executeStatement (connection, SQL_QUERY, parms3, false).getResultSets ().next ();
      result.addColumn ("number", SQL_TYPE_To_JAVA_TYPE.INT);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        record.addColumnValue ("number", (record.<Long> get ("numberN") == null ? null : record.<Long> get ("numberN").intValue ()));
      }
    }
    _commonDll._LogDBLatency_SP (connection, "P_ResumeAllSessions", starttime, proctorKey, true, null, null, null, clientName, null);
    return result;
  }

  public SingleDataResultSet P_DenyApproval_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, UUID opportunityKey) throws ReturnStatusException {
    return P_DenyApproval_SP (connection, sessionKey, proctorKey, browserKey, opportunityKey, null);
  }

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @param opportunityKey
   * @param reason
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_DenyApproval_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, UUID opportunityKey, String reason) throws ReturnStatusException {

    UUID oppsession = null;
    String teststatus = null;
    String testKey = null;
    String accessDenied = null;
    _Ref<String> error = new _Ref<> ();
    String clientName = null;
    Date now = _dateUtil.getDateWRetStatus (connection);

    accessDenied = _commonDll.ValidateProctorSession_FN (connection, proctorKey, sessionKey, browserKey);
    if (accessDenied != null) {
      return _commonDll._ReturnError_SP (connection, clientName, "P_DenyApproval", accessDenied, null, opportunityKey, "ValidateProctorSession");
    }
    final String SQL_QUERY1 = "SELECT _efk_AdminSubject as testkey, _fk_Session as oppsession, status as teststatus, clientname from testopportunity  where _Key = ${opportunitykey} ;";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("opportunitykey", opportunityKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, true).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      testKey = record.<String> get ("testkey");
      oppsession = record.<UUID> get ("oppsession");
      teststatus = record.<String> get ("teststatus");
      clientName = record.<String> get ("clientname");
    }
    if (DbComparator.notEqual (accessDenied, null)) {
      error.set (accessDenied);
    } else if (teststatus == null) {
      error.set ("The test opportunity does not exist");
    } else if (DbComparator.isEqual ("started", teststatus)) {
      error.set ("The test opportunity is in progress");
    } else if (DbComparator.notEqual ("pending", teststatus) && DbComparator.notEqual ("suspended", teststatus) && DbComparator.notEqual ("segmentEntry", teststatus)
        && DbComparator.notEqual ("segmentExit", teststatus)) {
      error.set ("The test opportunity is not pending approval");
    } else if (sessionKey != null && oppsession != null && DbComparator.notEqual (sessionKey, oppsession)) {
      error.set ("The test opportunity is not enrolled in this session");
    }
    if (error.get () != null) {
      _commonDll._LogDBError_SP (connection, "P_DenyApproval", error.get (), proctorKey, null, null, sessionKey, null, null);
      _commonDll._LogDBLatency_SP (connection, "P_DenyApproval", now, proctorKey, true, null, null, sessionKey, null, null);
      return _commonDll._ReturnError_SP (connection, clientName, "P_DenyApproval", error.get (), null, opportunityKey, null);
    }
    // TODO Elena test
    // Date st = new Date();
    result = _commonDll.SetOpportunityStatus_SP (connection, opportunityKey, "denied", false, sessionKey.toString ());
    // Date end = new Date();
    // long diff = end.getTime () - st.getTime ();
    // System.out.println (String.format
    // ("SetOPportunityStatus latency: %d millisec, status: %s", diff,
    // "denied"));
    if (reason != null) {
      final String SQL_UPDATE = "update testopportunity set Comment = ${reason} where _Key = ${opportunityKey};";
      SqlParametersMaps parms2 = new SqlParametersMaps ().put ("reason", reason).put ("opportunitykey", opportunityKey);
      int updateCnt = executeStatement (connection, SQL_UPDATE, parms2, false).getUpdateCount ();
    }
    _commonDll._LogDBLatency_SP (connection, "P_DenyApproval", now, proctorKey, true, 0, null, sessionKey, null, null);
    return result;
  }

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetApprovedTesteeRequests_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey) throws ReturnStatusException {

    Date today = _dateUtil.getDateWRetStatus (connection);
    List<Date> midnights = getMidnightsWRetStatus (connection);

    String accessDenied = null;
    String client = null;
    SingleDataResultSet result = null;

    accessDenied = _commonDll.ValidateProctorSession_FN (connection, proctorKey, sessionKey, browserKey);
    client = getClientNameBySessionKey (connection, sessionKey);

    if (accessDenied != null) {
      _commonDll._LogDBError_SP (connection, "P_GetApprovedTesteeRequests", accessDenied, proctorKey, null, null, sessionKey);
      _commonDll._LogDBLatency_SP (connection, "P_GetApprovedTesteeRequests", today, proctorKey, true, null, sessionKey);
      return _commonDll._ReturnError_SP (connection, client, "P_GetApprovedTesteeRequests", accessDenied, null, null, "ValidateProctorSession");
    }
    final String SQL_QUERY = "select _Key, _fk_TestOpportunity, _fk_Session, RequestType, RequestValue, DateSubmitted, DateFulfilled, Denied, ItemPage, ItemPosition,"
        + " RequestDescription from testopprequest where _fk_Session = ${sessionKey} and DateSubmitted > ${midnightAM} and DateSubmitted < ${midnightPM} and DateFulfilled is not null"
        + " and DateDenied is null order by _fk_TestOpportunity, DateSubmitted;";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("midnightAM", midnights.get (0)).put ("midnightPM", midnights.get (1)).put ("sessionKey", sessionKey);
    result = executeStatement (connection, SQL_QUERY, parms, true).getResultSets ().next ();

    _commonDll._LogDBLatency_SP (connection, "P_GetApprovedTesteeRequests", today, proctorKey, true, null, null, sessionKey, null, null);
    return result;
  }

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @param opportunityKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetCurrentTesteeRequests_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, UUID opportunityKey) throws ReturnStatusException {

    Date today = _dateUtil.getDateWRetStatus (connection);
    List<Date> midnights = getMidnightsWRetStatus (connection);

    String accessDenied = null;
    String client = null;
    SingleDataResultSet result = null;

    accessDenied = _commonDll.ValidateProctorSession_FN (connection, proctorKey, sessionKey, browserKey);
    client = getClientNameBySessionKey (connection, sessionKey);

    if (accessDenied != null) {
      _commonDll._LogDBError_SP (connection, "P_GetCurrentTesteeRequests", accessDenied, proctorKey, null, null, sessionKey);
      _commonDll._LogDBLatency_SP (connection, "P_GetCurrentTesteeRequests", today, proctorKey, true, null, sessionKey);
      return _commonDll._ReturnError_SP (connection, client, "P_GetCurrentTesteeRequests", accessDenied, null, null, "ValidateProctorSession");
    }
    final String SQL_QUERY = "select _Key, _fk_Session, RequestType, RequestValue, DateSubmitted, DateFulfilled, Denied, ItemPage, ItemPosition,"
        + " RequestDescription from testopprequest where _fk_TestOpportunity = ${opportunityKey} and _fk_Session = ${sessionKey} and DateSubmitted > ${midnightAM} and DateSubmitted < ${midnightPM}"
        + " and DateFulfilled is null order by DateSubmitted;";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("midnightAM", midnights.get (0)).put ("midnightPM", midnights.get (1)).put ("sessionKey", sessionKey)
        .put ("opportunityKey", opportunityKey);
    result = executeStatement (connection, SQL_QUERY, parms, true).getResultSets ().next ();

    _commonDll._LogDBLatency_SP (connection, "P_GetCurrentTesteeRequests", today, proctorKey, true, null, null, sessionKey, null, null);
    return result;
  }

  /**
   * @param connection
   * @param bankkey
   * @param itemkey
   * @return
   * @throws ReturnStatusException
   */
  public String ITEMBANK_StimulusFile_FN (SQLConnection connection, Long bankkey, Long stimKey) throws ReturnStatusException {
    String path = null;
    String makeStimuluskey = _commonDll.MakeStimulusKey_FN (connection, bankkey, stimKey);
    final String SQL_QUERY = "select concat(C.Homepath, B.HomePath, B.stimuliPath, S.FilePath, S.FileName) as path from ${ItemBankDB}.tblitembank B, ${ItemBankDB}.tblclient C, ${ItemBankDB}.tblstimulus S"
        + " where B._efk_Itembank = ${bankkey} and B._fk_Client = C._Key and S._Key = ${makeStimuluskey}";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("bankkey", bankkey).put ("makeStimuluskey", makeStimuluskey);
    String finalQuery = fixDataBaseNames (SQL_QUERY);
    SingleDataResultSet result = executeStatement (connection, finalQuery, parms, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      path = record.<String> get ("path");
      path = replaceSeparatorChar (path);
    }
    return path;
  }

  private void updateTestOppRequest (SQLConnection connection, UUID requestKey, Date today) throws ReturnStatusException {

    final String SQL_UPDATE = "update TestOppRequest set DateFulfilled = ${today} where _key = ${requestKey};";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("today", today).put ("requestKey", requestKey);
    int updateCnt = executeStatement (connection, SQL_UPDATE, parms, false).getUpdateCount ();
  }

  public SingleDataResultSet P_GetTesteeRequestValues_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, UUID requestKey) throws ReturnStatusException {
    return P_GetTesteeRequestValues_SP (connection, sessionKey, proctorKey, browserKey, requestKey, false);
  }

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @param requestKey
   * @param markFulfilled
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetTesteeRequestValues_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, UUID requestKey, Boolean markFulfilled)
      throws ReturnStatusException {

    Long testee = null;
    String testeeName = null;
    String testeeID = null;
    String test = null;
    Integer opportunity = null;
    String rtype = null;
    String rval = null;
    Integer page = null;
    Integer pos = null;
    String lang = null;
    String rparas = null;
    String rdesc = null;
    UUID testoppkey = null;
    Long itemKey = null;
    Long bankKey = null;
    Long stimKey = null;

    Date today = _dateUtil.getDateWRetStatus (connection);
    String accessDenied = null;
    String client = null;
    SingleDataResultSet result = null;

    accessDenied = _commonDll.ValidateProctorSession_FN (connection, proctorKey, sessionKey, browserKey);
    if (accessDenied != null) {
      client = getClientNameBySessionKey (connection, sessionKey);
      _commonDll._LogDBError_SP (connection, "P_GetTesteeRequestValues", accessDenied, proctorKey, null, null, sessionKey);
      _commonDll._LogDBLatency_SP (connection, "P_GetTesteeRequestValues", today, proctorKey, true, null, sessionKey);
      return _commonDll._ReturnError_SP (connection, client, "P_GetTesteeRequestValues", accessDenied, null, null, "ValidateProctorSession");
    }
    final String SQL_QUERY1 = "select R._fk_TestOpportunity as testoppkey, O._efk_Testee as testee, O.TesteeName as testeeName, O.TesteeID as testeeID, O._efk_TestID as test,"
        + " O.Opportunity as opportunity, R.RequestType as rtype, R.RequestValue as rval, R.ItemPage as page, R.ItemPosition as pos, A.AccCode as lang,"
        + " R.RequestParameters as rparas, R.RequestDescription as rdesc from testopprequest R, testeeaccommodations A, testopportunity_readonly O where R._Key = ${requestKey}"
        + " and R._fk_TestOpportunity = O._fk_TestOpportunity and R._fk_TestOpportunity = A._fk_TestOpportunity and A.AccType = ${Language};";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("Language", "Language").put ("requestKey", requestKey);
    result = executeStatement (connection, SQL_QUERY1, parms, true).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      testoppkey = record.<UUID> get ("testoppkey");
      testee = record.<Long> get ("testee");
      testeeName = record.<String> get ("testeeName");
      testeeID = record.<String> get ("testeeID");
      test = record.<String> get ("test");
      opportunity = record.<Integer> get ("opportunity");
      rtype = record.<String> get ("rtype");
      rval = record.<String> get ("rval");
      page = record.<Integer> get ("page");
      pos = record.<Integer> get ("pos");
      lang = record.<String> get ("lang");
      rparas = record.<String> get ("rparas");
      rdesc = record.<String> get ("rdesc");
    }
    if (testoppkey == null) {
      _commonDll._ReturnError_SP (connection, client, "P_GetTesteeRequestValues", "Request not found", null, testoppkey, "P_GetTesteeRequest");
      _commonDll._LogDBLatency_SP (connection, "P_GetTesteeRequestValues", today, proctorKey, true, null, null, sessionKey, null, null);
      return result;
    }
    if (DbComparator.notEqual ("PRINT", rtype) && DbComparator.notEqual ("PRINTPASSAGE", rtype) && DbComparator.notEqual ("PRINTSTIMULUS", rtype) && DbComparator.notEqual ("PRINTITEM", rtype)) {

      // select @testoppkey as opportunityKey, @testee as _efk_Testee, @testeeID
      // as testeeID, @testeeName as TesteeName, @test as _efk_TestID,
      // @opportunity as Opportunity,
      // @rtype as RequestType, @rval as RequestValue, @page as ItemPage, @pos
      // as ItemPosition, @lang as AccCode, @lang as [Language],
      // @rparas as RequestParameters, @rdesc as RequestDescription;

      List<CaseInsensitiveMap<Object>> resultlist = new ArrayList<CaseInsensitiveMap<Object>> ();
      CaseInsensitiveMap<Object> rcrd = new CaseInsensitiveMap<Object> ();
      rcrd.put ("opportunityKey", testoppkey);
      rcrd.put ("_efk_Testee", testee);
      rcrd.put ("testeeID", testeeID);
      rcrd.put ("TesteeName", testeeName);
      rcrd.put ("_efk_TestID", test);
      rcrd.put ("Opportunity", opportunity);
      rcrd.put ("RequestType", rtype);
      rcrd.put ("RequestValue", rval);
      rcrd.put ("ItemPage", page);
      rcrd.put ("ItemPosition", pos);
      rcrd.put ("AccCode", lang);
      rcrd.put ("Language", lang);
      rcrd.put ("RequestParameters", rparas);
      rcrd.put ("RequestDescription", rdesc);
      resultlist.add (rcrd);

      result = new SingleDataResultSet ();
      result.addColumn ("opportunityKey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER);
      result.addColumn ("_efk_Testee", SQL_TYPE_To_JAVA_TYPE.BIGINT);
      result.addColumn ("testeeID", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("TesteeName", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("_efk_TestID", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("Opportunity", SQL_TYPE_To_JAVA_TYPE.INT);
      result.addColumn ("RequestType", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("RequestValue", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("ItemPage", SQL_TYPE_To_JAVA_TYPE.INT);
      result.addColumn ("ItemPosition", SQL_TYPE_To_JAVA_TYPE.INT);
      result.addColumn ("AccCode", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("Language", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("RequestParameters", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("RequestDescription", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addRecords (resultlist);

      if (markFulfilled == true)
        updateTestOppRequest (connection, requestKey, today);
      _commonDll._LogDBLatency_SP (connection, "P_GetTesteeRequestValues", today, proctorKey, true, null, null, sessionKey, null, null);
      return result;
    }
    // if (@rtype in ('PRINT', 'PRINTPASSAGE', 'PRINTSTIMULUS') and @rval is not
    // null) begin
    // select @testoppkey as opportunityKey, @testee as _efk_Testee, @testeeID
    // as testeeID, @testeeName as TesteeName, @test as _efk_TestID,
    // @opportunity as Opportunity,
    // @rtype as RequestType, @rval as StimulusFile, @lang as AccCode, @lang as
    // [Language],
    // @rparas as RequestParameters, @rdesc as RequestDescription;

    if ((DbComparator.isEqual ("PRINT", rtype) || DbComparator.isEqual ("PRINTPASSAGE", rtype) || DbComparator.isEqual ("PRINTSTIMULUS", rtype)) && DbComparator.notEqual (rval, null)) {

      List<CaseInsensitiveMap<Object>> resultlist = new ArrayList<CaseInsensitiveMap<Object>> ();
      CaseInsensitiveMap<Object> rcrd = new CaseInsensitiveMap<Object> ();
      rcrd.put ("opportunityKey", testoppkey);
      rcrd.put ("_efk_Testee", testee);
      rcrd.put ("testeeID", testeeID);
      rcrd.put ("TesteeName", testeeName);
      rcrd.put ("_efk_TestID", test);
      rcrd.put ("Opportunity", opportunity);
      rcrd.put ("RequestType", rtype);
      rcrd.put ("StimulusFile", rval);
      rcrd.put ("AccCode", lang);
      rcrd.put ("Language", lang);
      rcrd.put ("RequestParameters", rparas);
      rcrd.put ("RequestDescription", rdesc);
      resultlist.add (rcrd);

      result = new SingleDataResultSet ();
      result.addColumn ("opportunityKey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER);
      result.addColumn ("_efk_Testee", SQL_TYPE_To_JAVA_TYPE.BIGINT);
      result.addColumn ("testeeID", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("TesteeName", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("_efk_TestID", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("Opportunity", SQL_TYPE_To_JAVA_TYPE.INT);
      result.addColumn ("RequestType", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("StimulusFile", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("AccCode", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("Language", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("RequestParameters", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("RequestDescription", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addRecords (resultlist);

      if (markFulfilled == true)
        updateTestOppRequest (connection, requestKey, today);
      _commonDll._LogDBLatency_SP (connection, "P_GetTesteeRequestValues", today, proctorKey, true, null, null, sessionKey, null, null);
      return result;
    }
    final String SQL_QUERY2 = "select _Efk_ITSITem as itemKey, _efk_ITSBank as bankKey from testeeresponse where _fk_TestOpportunity = ${testoppkey} and Position = ${pos};";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("testoppkey", testoppkey).put ("pos", pos);
    result = executeStatement (connection, SQL_QUERY2, parms1, true).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      itemKey = record.<Long> get ("itemKey");
      bankKey = record.<Long> get ("bankKey");
    }
    if (DbComparator.isEqual ("PRINTITEM", rtype)) {
      if (rval == null) {
        rval = _commonDll.ITEMBANK_ItemFile_FN (connection, bankKey, itemKey);
      }
      if (rval == null) {
        return _commonDll._ReturnError_SP (connection, client, "P_GetTesteeRequestValues", "Request value not found", null, testoppkey, "P_GetTesteeRequest");
      }
      final String SQL_QUERY3 = "select ${testoppkey} as opportunityKey, ${testee} as _efk_Testee, cast(${testeeID} as CHAR) as testeeID, cast(${testeeName} as CHAR) as TesteeName, cast(${test} as CHAR) as _efk_TestID,"
          + " bigtoint(${opportunity}) as Opportunity, cast(${rtype} as CHAR) as RequestType, cast(${rval} as CHAR) as ItemFile, Response as ItemResponse, cast(${lang} as CHAR) as AccCode, cast(${lang} as CHAR) as Language, "
          + " cast(${rparas} as CHAR) as RequestParameters, cast(${rdesc} as CHAR) as RequestDescription from testeeresponse where _fk_TestOpportunity = ${testoppkey} and Position = ${pos};";
      SqlParametersMaps parms2 = new SqlParametersMaps ().put ("testoppkey", testoppkey).put ("pos", pos).put ("testee", testee).put ("testeeID", testeeID).put ("testeeName", testeeName)
          .put ("test", test).put ("opportunity", opportunity).put ("rtype", rtype).put ("rval", rval).put ("lang", lang).put ("rparas", rparas).put ("rdesc", rdesc);
      result = executeStatement (connection, SQL_QUERY3, parms2, false).getResultSets ().next ();
      if (markFulfilled == true)
        updateTestOppRequest (connection, requestKey, today);
      _commonDll._LogDBLatency_SP (connection, "P_GetTesteeRequestValues", today, proctorKey, true, null, null, sessionKey, null, null);
      return result;
    }

    if (DbComparator.isEqual ("PRINT", rtype) || DbComparator.isEqual ("PRINTPASSAGE", rtype) || DbComparator.isEqual ("PRINTSTIMULUS", rtype)) {

      String makeItemkey = _commonDll.MakeItemKey_FN (connection, bankKey, itemKey);
      final String SQL_QUERY4 = "select _efk_ITSKey as stimKey from ${ItemBankDB}.tblsetofitemstimuli T, ${ItemBankDB}.tblstimulus S where T._fk_Item = ${makeItemkey} and T._fk_Stimulus = S._Key;";
      String finalQuery = fixDataBaseNames (SQL_QUERY4);
      SqlParametersMaps parms3 = new SqlParametersMaps ().put ("makeItemkey", makeItemkey);
      result = executeStatement (connection, finalQuery, parms3, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        stimKey = record.<Long> get ("stimKey");
      }
      if (stimKey == null) {
        return _commonDll._ReturnError_SP (connection, client, "P_GetTesteeRequestValues", "Item stimulus not found", null, testoppkey, "P_GetTesteeRequest");
      }
      rval = ITEMBANK_StimulusFile_FN (connection, bankKey, stimKey);
      if (rval == null) {
        _commonDll._ReturnError_SP (connection, client, "P_GetTesteeRequestValues", "Stimulus file not found", null, testoppkey, "P_GetTesteeRequest");
        _commonDll._LogDBLatency_SP (connection, "P_GetTesteeRequestValues", today, proctorKey, true, null, null, sessionKey, null, null);
        return result;
      }
      // select @testoppkey as opportunityKey, @testee as _efk_Testee, @testeeID
      // as testeeID, @testeeName as TesteeName, @test as _efk_TestID,
      // @opportunity as Opportunity,
      // @rtype as RequestType, @rval as StimulusFile, @lang as AccCode, @lang
      // as [Language],
      // @rparas as RequestParameters, @rdesc as RequestDescription;

      List<CaseInsensitiveMap<Object>> resultlist = new ArrayList<CaseInsensitiveMap<Object>> ();
      CaseInsensitiveMap<Object> rcrd = new CaseInsensitiveMap<Object> ();
      rcrd.put ("opportunityKey", testoppkey);
      rcrd.put ("_efk_Testee", testee);
      rcrd.put ("testeeID", testeeID);
      rcrd.put ("TesteeName", testeeName);
      rcrd.put ("_efk_TestID", test);
      rcrd.put ("Opportunity", opportunity);
      rcrd.put ("RequestType", rtype);
      rcrd.put ("StimulusFile", rval);
      rcrd.put ("AccCode", lang);
      rcrd.put ("Language", lang);
      rcrd.put ("RequestParameters", rparas);
      rcrd.put ("RequestDescription", rdesc);
      resultlist.add (rcrd);

      result = new SingleDataResultSet ();
      result.addColumn ("opportunityKey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER);
      result.addColumn ("_efk_Testee", SQL_TYPE_To_JAVA_TYPE.BIGINT);
      result.addColumn ("testeeID", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("TesteeName", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("_efk_TestID", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("Opportunity", SQL_TYPE_To_JAVA_TYPE.INT);
      result.addColumn ("RequestType", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("StimulusFile", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("AccCode", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("Language", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("RequestParameters", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("RequestDescription", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addRecords (resultlist);

      if (markFulfilled == true)
        updateTestOppRequest (connection, requestKey, today);
      _commonDll._LogDBLatency_SP (connection, "P_GetTesteeRequestValues", today, proctorKey, true, null, null, sessionKey, null, null);
      return result;
    }
    _commonDll._LogDBLatency_SP (connection, "P_GetTesteeRequestValues", today, proctorKey, true, null, null, sessionKey, null, null);
    return result;
  }

  public SingleDataResultSet P_DenyTesteeRequest_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, UUID requestKey) throws ReturnStatusException {
    return P_DenyTesteeRequest_SP (connection, sessionKey, proctorKey, browserKey, requestKey, null);
  }

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @param requestKey
   * @param reason
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_DenyTesteeRequest_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, UUID requestKey, String reason) throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);
    String accessDenied = null;
    String client = null;
    accessDenied = _commonDll.ValidateProctorSession_FN (connection, proctorKey, sessionKey, browserKey);
    if (accessDenied != null) {
      client = getClientNameBySessionKey (connection, sessionKey);
      _commonDll._LogDBError_SP (connection, "P_DenyTesteeRequest", accessDenied, proctorKey, null, null, sessionKey);
      _commonDll._LogDBLatency_SP (connection, "P_DenyTesteeRequest", starttime, proctorKey, true, null, sessionKey);
      return _commonDll._ReturnError_SP (connection, client, "P_DenyTesteeRequest", accessDenied, null, null, "ValidateProctorSession");
    }
    final String SQL_UPDATE = "update testopprequest set Denied = ${reason}, DateFulfilled = ${starttime}, DateDenied = ${starttime} where _Key = ${requestKey}";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("reason", reason).put ("starttime", starttime).put ("requestKey", requestKey);
    int updateCnt = executeStatement (connection, SQL_UPDATE, parms, false).getUpdateCount ();
    _commonDll._LogDBLatency_SP (connection, "P_DenyTesteeRequest", starttime, proctorKey, true, null, null, sessionKey, null, null);

    return (new SingleDataResultSet ());
  }

  /**
   * @param connection
   * @param clientName
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetConfigs_SP (SQLConnection connection, String clientName) throws ReturnStatusException {

    Integer TAInterfaceTimeout = null;
    Integer refreshValue = null;
    Integer refreshValueMultiplier = null;
    Integer training = null;
    String institution = null;
    SingleDataResultSet result = null;

    final String SQL_QUERY1 = "select refreshValue, TAInterfaceTimeout, RefreshValueMultiplier as refreshValueMultiplier from timelimits "
        + " where _efk_TestID is null and clientname = ${clientname};";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("clientname", clientName);
    result = executeStatement (connection, SQL_QUERY1, parms, true).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      refreshValue = record.<Integer> get ("refreshValue");
      TAInterfaceTimeout = record.<Integer> get ("TAInterfaceTimeout");
      refreshValueMultiplier = record.<Integer> get ("refreshValueMultiplier");
    }
    final String SQL_QUERY2 = " select IsOn as training from ${ConfigDB}.client_systemflags where ClientName = ${ClientName} and AuditOBject = ${ProctorTraining};";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("clientname", clientName).put ("ProctorTraining", "ProctorTraining");
    String finalQuery = fixDataBaseNames (SQL_QUERY2);
    result = executeStatement (connection, finalQuery, parms1, true).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      training = record.<Integer> get ("training");
    }
    final String SQL_QUERY3 = "select Description as institution from ${ConfigDB}.client_systemflags S where S.ClientName = ${clientname} and AuditObject = ${MatchTesteeProctorSchool} and IsOn = 1;";
    SqlParametersMaps parms3 = new SqlParametersMaps ().put ("clientname", clientName).put ("MatchTesteeProctorSchool", "MatchTesteeProctorSchool");
    finalQuery = fixDataBaseNames (SQL_QUERY3);
    result = executeStatement (connection, finalQuery, parms3, true).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      institution = record.<String> get ("institution");
    }
    final String SQL_QUERY4 = "select cast(null as CHAR) as AnonymousLogin, ClientName, Environment, ClientStylePath, TimeZoneOffset, bigtoint(${refreshValue}) as refreshValue, "
        + " bigtoint(${TAInterfaceTimeout}) as TAInterfaceTimeout, bigtoint(${training}) as ProctorTraining, cast(${institution} as CHAR) as MatchTesteeProctorSchool, bigtoint(${refreshValueMultiplier}) as refreshValueMultiplier,"
        + " proctorCheckin as checkinURL from externs where clientname = ${clientname};";
    SqlParametersMaps parms4 = new SqlParametersMaps ().put ("clientname", clientName).put ("refreshValue", refreshValue).put ("TAInterfaceTimeout", TAInterfaceTimeout)
        .put ("training", training).put ("institution", institution).put ("refreshValueMultiplier", refreshValueMultiplier);
    result = executeStatement (connection, SQL_QUERY4, parms4, false).getResultSets ().next ();

    return result;
  }

  /**
   * @param connection
   * @param systemID
   * @param client
   * @param language
   * @param contextList
   * @param delimiter
   * @return
   * @throws ReturnStatusException
   */
  // Obsolete
  public SingleDataResultSet TDSCONFIGS_TDS_GetMessages_SP (SQLConnection connection, String systemID, String client, String language, String contextList, Character delimiter)
      throws ReturnStatusException {
    return null;
  }

  public DataBaseTable TDS_GetMessages_SP (SQLConnection connection, String systemID, String client, String language, String contextList, Character delimiter)
      throws ReturnStatusException {
    long startTime = System.currentTimeMillis ();
    Integer keys = null;
    Long numKeys = null;
    String default1 = null;
    Boolean inter = null;
    SingleDataResultSet result = null;
    int insertedCnt = 0;

    // _logger.info (String.format
    // ("TDSCONFIGS_TDS_GetMessages_SP using old version"));
    DataBaseTable msgKeysTable = getDataBaseTable ("msgKeys").addColumn ("mkey", SQL_TYPE_To_JAVA_TYPE.BIGINT);
    connection.createTemporaryTable (msgKeysTable);
    Map<String, String> unquotedParms1 = new HashMap<> ();
    unquotedParms1.put ("msgKeysTableName", msgKeysTable.getTableName ());
    final String SQL_QUERY1 = "select defaultLanguage as default1, internationalize as inter from ${ConfigDB}.client where name = ${client} ;";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("client", client);
    String finalQuery = fixDataBaseNames (SQL_QUERY1);
    result = executeStatement (connection, finalQuery, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      default1 = record.<String> get ("default1");
      inter = record.<Boolean> get ("inter");
    }
    if (DbComparator.isEqual (inter, false)) {
      language = default1;
    }
    DataBaseTable buildTable = _commonDll._BuildTable_FN (connection, "buildTableName", contextList, delimiter.toString ());
    Map<String, String> unquotedParms7 = new HashMap<> ();
    unquotedParms7.put ("buildTableName", buildTable.getTableName ());
    unquotedParms7.put ("msgKeysTableName", msgKeysTable.getTableName ());
    // -- First get the key to all message objects used by this system
    final String SQL_INSERT1 = "insert into ${msgKeysTableName} (mkey)  select _fk_CoreMessageObject from ${ConfigDB}.tds_coremessageuser U, ${ConfigDB}.tds_coremessageobject O, "
        + " ${buildTableName} T where U.systemID = ${systemID} and O._Key = U._fk_CoreMessageObject and O.Context = T.record;";
    SqlParametersMaps parms2 = new SqlParametersMaps ().put ("systemID", systemID);
    finalQuery = fixDataBaseNames (SQL_INSERT1);
    keys = executeStatement (connection, fixDataBaseNames (finalQuery, unquotedParms7), parms2, false).getUpdateCount ();
    DataBaseTable msgsTable = getDataBaseTable ("msgs").addColumn ("msgkey", SQL_TYPE_To_JAVA_TYPE.BIGINT).addColumn ("msgSource", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100)
        .addColumn ("MessageID", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("ContextType", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("Context", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100)
        .addColumn ("AppKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 255).addColumn ("Language", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 30).addColumn ("Grade", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 25)
        .addColumn ("Subject", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("ParaLabels", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 255).addColumn ("Message", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 8000);
    connection.createTemporaryTable (msgsTable);
    Map<String, String> unquotedParms2 = new HashMap<> ();
    unquotedParms2.put ("msgsTableName", msgsTable.getTableName ());
    unquotedParms2.put ("msgKeysTableName", msgKeysTable.getTableName ());

    final String SQL_INDEX1 = "create unique index _IX_MSGS on ${msgsTableName} (msgkey);";
    Map<String, String> unquotedParms3 = new HashMap<> ();
    unquotedParms3.put ("msgsTableName", msgsTable.getTableName ());
    executeStatement (connection, fixDataBaseNames (SQL_INDEX1, unquotedParms3), null, false).getUpdateCount ();
    // -- prefer messages in the requested language from the given client
    final String SQL_INSERT2 = "insert IGNORE into ${msgsTableName} (msgkey, msgSource, MessageID, ContextType, Context, Appkey, ParaLabels, Message, Grade, Subject, Language)"
        + " select K.mkey, ${client}, MessageID, ContextType, Context, AppKey, ParaLabels, T.Message, Grade, Subject, language from ${ConfigDB}.tds_coremessageobject O, "
        + " ${ConfigDB}.client_messagetranslation T, ${msgKeysTableName} K  where T._fk_CoreMessageObject = mkey and O._key = mkey and T.Language = ${language} and T.client = ${client};";
    finalQuery = fixDataBaseNames (SQL_INSERT2);
    SqlParametersMaps parms3 = new SqlParametersMaps ().put ("client", client).put ("language", language);
    insertedCnt = executeStatement (connection, fixDataBaseNames (finalQuery, unquotedParms2), parms3, false).getUpdateCount ();
    if (DbComparator.notEqual ("ENU", language)) {
      final String SQL_INSERT3 = "insert IGNORE into ${msgsTableName} (msgkey, msgSource, MessageID, ContextType, Context, Appkey, ParaLabels, Message, Grade, Subject, Language)"
          + " select K.mkey, ${client}, MessageID, ContextType, Context, AppKey, ParaLabels, T.Message, Grade, Subject, language from ${ConfigDB}.tds_coremessageobject O,"
          + " ${ConfigDB}.client_messagetranslation T, ${msgKeysTableName} K where T._fk_CoreMessageObject = mkey and O._key = mkey and T.Language = ${ENU} "
          + " and T.client = ${client};";
      finalQuery = fixDataBaseNames (SQL_INSERT3);
      SqlParametersMaps parms4 = new SqlParametersMaps ().put ("client", client).put ("ENU", "ENU");
      insertedCnt = executeStatement (connection, fixDataBaseNames (finalQuery, unquotedParms2), parms4, false).getUpdateCount ();
    }

    // -- If this is a practice test 'client' (e.g. Oregon_PT), then fill in
    // like above from the true client (Oregon)
    String clientSuffix = null;
    if (client.length () >= 3) {
      clientSuffix = client.substring (client.length () - 3);
    }
    if (clientSuffix != null && "_PT".equalsIgnoreCase (clientSuffix)) {
      String opclient = client.substring (0, client.length () - 3);

      final String SQL_INSERT4 = "insert IGNORE into ${msgsTableName} (msgkey, msgSource, MessageID, ContextType, Context, Appkey, ParaLabels, Message, Grade, Subject, Language)"
          + " select K.mkey, ${opclient}, MessageID, ContextType, Context, AppKey, ParaLabels, T.Message, Grade, Subject, language from ${ConfigDB}.tds_coremessageobject O,"
          + " ${ConfigDB}.client_messagetranslation T, ${msgKeysTableName} K  where T._fk_CoreMessageObject = mkey and O._key = mkey and T.Language = ${language}"
          + " and T.client = ${opclient};";
      finalQuery = fixDataBaseNames (SQL_INSERT4);
      SqlParametersMaps parms5 = new SqlParametersMaps ().put ("opclient", opclient).put ("language", language);
      insertedCnt = executeStatement (connection, fixDataBaseNames (finalQuery, unquotedParms2), parms5, false).getUpdateCount ();

      if (DbComparator.notEqual ("ENU", language)) {
        final String SQL_INSERT5 = "insert IGNORE into ${msgsTableName} (msgkey, msgSource, MessageID, ContextType, Context, Appkey, ParaLabels, Message, Grade, Subject, Language)"
            + " select K.mkey, ${opclient}, MessageID, ContextType, Context, AppKey, ParaLabels, T.Message, Grade, Subject, language from ${ConfigDB}.tds_coremessageobject O,"
            + " ${ConfigDB}.client_messagetranslation T, ${msgKeysTableName} K  where T._fk_CoreMessageObject = mkey and O._key = mkey and T.Language = ${ENU}"
            + " and T.client = ${opclient};";
        finalQuery = fixDataBaseNames (SQL_INSERT5);
        SqlParametersMaps parms6 = new SqlParametersMaps ().put ("opclient", opclient).put ("ENU", "ENU");
        insertedCnt = executeStatement (connection, fixDataBaseNames (finalQuery, unquotedParms2), parms6, false).getUpdateCount ();
      }
    }
    final String SQL_QUERY2 = "select count(distinct msgkey) as numkeys from ${msgsTableName} where grade = ${--ANY--} and subject = ${--ANY--};";
    SqlParametersMaps parms7 = new SqlParametersMaps ().put ("--ANY--", "--ANY--");
    Map<String, String> unquotedParms4 = unquotedParms3;
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY2, unquotedParms4), parms7, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      numKeys = record.<Long> get ("numkeys");
    }
    if (DbComparator.greaterOrEqual (numKeys, keys)) {
      connection.dropTemporaryTable (msgKeysTable);
      return msgsTable;
    }
    // -- finally fill in remaining messages directly from 'AIR' messages
    // -- First, are there any translations standard for all AIR clients
    final String SQL_INSERT6 = "insert IGNORE into ${msgsTableName} (msgkey, msgSource, MessageID, ContextType, Context, Appkey, ParaLabels, Message, Grade, Subject, Language)"
        + " select K.mkey, ${AIR}, MessageID, ContextType, Context, AppKey, ParaLabels, T.Message, Grade, Subject, language from ${ConfigDB}.tds_coremessageobject O,"
        + " ${ConfigDB}.client_messagetranslation T, ${msgKeysTableName} K  where T._fk_CoreMessageObject = mkey and O._key = mkey and T.Language = ${language}"
        + " and T.client = ${AIR};";
    finalQuery = fixDataBaseNames (SQL_INSERT6);
    SqlParametersMaps parms8 = new SqlParametersMaps ().put ("language", language).put ("AIR", "AIR");
    insertedCnt = executeStatement (connection, fixDataBaseNames (finalQuery, unquotedParms2), parms8, false).getUpdateCount ();
    // -- last resort is the core messages table
    final String SQL_INSERT7 = "insert IGNORE into ${msgsTableName} (msgkey, msgSource, MessageID, ContextType, Context, Appkey, ParaLabels, Message, Grade, Subject, Language) "
        + " select K.mkey, OwnerApp, MessageID, ContextType, Context, AppKey, ParaLabels, O.Message, ${--ANY--}, ${--ANY--}, ${ENU} from ${ConfigDB}.tds_coremessageobject O,"
        + "  ${msgKeysTableName} K where O._key = mkey;";
    finalQuery = fixDataBaseNames (SQL_INSERT7);
    SqlParametersMaps parms9 = new SqlParametersMaps ().put ("--ANY--", "--ANY--").put ("ENU", "ENU");
    insertedCnt = executeStatement (connection, fixDataBaseNames (finalQuery, unquotedParms2), parms9, false).getUpdateCount ();

    connection.dropTemporaryTable (msgKeysTable);
    _logger.info ("<<<<<<<< TDS_GetMessages_SP Old Total Execution time : " + (System.currentTimeMillis () - startTime) + " ms, Thread: " + Thread.currentThread ().getId ());
    return msgsTable;
  }

  protected void populateAppMessages (SQLConnection connection, DataBaseTable msgsTable, String client, String language,
      String systemID, String contextList, Character delimiter) throws ReturnStatusException {
    long startTime = System.currentTimeMillis ();
    int insertedCnt;
    Long theKey = null;
    int end = (contextList.length () > 50 ? 49 : contextList.length ());
    String contextIndex = contextList.substring (0, end);
    try {

      final String cmd1 = "insert into ${ConfigDB}.__appmessagecontexts (clientname, systemID, language, contextList,  contextIndex, delim) "
          + " select ${client}, ${systemID}, ${language}, ${contextList},  ${contextIndex}, ${delimiter} from dual "
          + " where not exists (select * from ${ConfigDB}.__appmessagecontexts where clientname = ${client} and systemID = ${systemID} "
          + "                   and language = ${language} and contextindex = ${contextIndex} and contextList = ${contextList})";

      SqlParametersMaps par1 = new SqlParametersMaps ().put ("client", client).put ("systemid", systemID).put ("language", language).
          put ("contextlist", contextList).put ("contextIndex", contextIndex).put ("delimiter", delimiter.toString ());
      insertedCnt = executeStatement (connection, fixDataBaseNames (cmd1), par1, false).getUpdateCount ();
      if (insertedCnt < 1) {
        _logger.info (String.format ("populateAppMessages: No need to insert into __appmessagecontextsfor %s, %s, %s, %s", client, language, systemID, contextIndex));
        return;
      }
    } catch (ReturnStatusException re) {
      _logger.error (String.format ("populateAppMessages: Failed inserting rec into __appmessagecontexts for %s, %s, %s, %s: %s",
          client, language, systemID, contextIndex, re.getMessage ()), re);
      return;
    }
    final String cmd2 = "select cast(LAST_INSERT_ID() as SIGNED) as theKey";
    SingleDataResultSet result = executeStatement (connection, cmd2, null, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      theKey = record.<Long> get ("theKey");
    }
    // if (theKey == null) {
    // _logger.error (String.format
    // ("Strange, getting null key after successfully inserting __appmessagecontexts for %s, %s, %s, %s",
    // client, language, systemID, contextIndex));
    // return;
    // }
    try {
      final String cmd3 = "insert into ${ConfigDB}.__appmessages (_fk_AppMessageContext, msgkey, msgSource, MessageID,"
          + " ContextType, Context, Appkey, ParaLabels, Message, Grade, Subject, Language)"
          + " select ${theKey}, msgkey, msgSource, MessageID, ContextType, Context, Appkey, ParaLabels, Message, Grade, Subject, Language "
          + " from ${msgsTableName}";
      String finalQuery = fixDataBaseNames (cmd3);
      Map<String, String> unquotedParms3 = new HashMap<> ();
      unquotedParms3.put ("msgsTableName", msgsTable.getTableName ());
      SqlParametersMaps par3 = new SqlParametersMaps ().put ("thekey", theKey);
      insertedCnt = executeStatement (connection, fixDataBaseNames (finalQuery, unquotedParms3), par3, false).getUpdateCount ();
    } catch (ReturnStatusException re) {
      _logger.error (String.format ("populateAppMessages: Failed inserting rec into __appmessages for %s, %s, %s, %s: %s",
          client, language, systemID, contextIndex, re.getMessage ()), re);
      try {
        final String delcmd = "delete from ${ConfigDB}.__appmessagecontexts where "
            + "where clientname = ${client} and systemID = ${systemID} "
            + "and language = ${language} and contextindex = ${contextIndex} and contextList = ${contextList}";
        SqlParametersMaps pardel = new SqlParametersMaps ().put ("client", client).put ("systemid", systemID).put ("language", language).
            put ("contextlist", contextList).put ("contextIndex", contextIndex);
        insertedCnt = executeStatement (connection, fixDataBaseNames (delcmd), pardel, false).getCount ();
      } catch (ReturnStatusException re1) {

        _logger.error (String.format ("populateAppMessages: Problem removing rec from __appmessagecontexts: %s", re1.getMessage ()), re1);
      }
      return;
    }
    try {
      final String cmd4 = "update ${ConfigDB}.__appmessagecontexts set dateGenerated = now(3) where _Key = ${thekey}";
      SqlParametersMaps par4 = new SqlParametersMaps ().put ("thekey", theKey);
      int updatedCnt = executeStatement (connection, fixDataBaseNames (cmd4), par4, false).getUpdateCount ();
      // connection.commit ();
      _logger.info (String.format ("populateAppMessages: Inserted %d recs into _appmessages for %s, %s, %s, %s", insertedCnt, client, language, systemID, contextIndex));
    } catch (ReturnStatusException re) {
      _logger.error (String.format ("populateAppMessages: Failed updating dategenerated in __appmessagecontexts for %s, %s, %s, %s: %s",
          client, language, systemID, contextIndex, re.getMessage ()), re);
      try {
        final String delcmd = "delete from ${ConfigDB}.__appmessagecontexts where "
            + "where clientname = ${client} and systemID = ${systemID} "
            + "and language = ${language} and contextindex = ${contextIndex} and contextList = ${contextList}";
        SqlParametersMaps pardel = new SqlParametersMaps ().put ("client", client).put ("systemid", systemID).put ("language", language).
            put ("contextlist", contextList).put ("contextIndex", contextIndex);
        insertedCnt = executeStatement (connection, fixDataBaseNames (delcmd), pardel, false).getCount ();
      } catch (ReturnStatusException re1) {

        _logger.error (String.format ("Problem removing rec from __appmessagecontexts: %s", re1.getMessage ()), re1);
      }
    }
    _logger.info ("<<<<<<<< populateAllMessages Total Execution time : " + (System.currentTimeMillis () - startTime) + " ms, Thread: " + Thread.currentThread ().getId ());    
  }

  /*
   * public SingleDataResultSet TDSCONFIGS_TDS_GetMessages_SP (SQLConnection
   * connection, String systemID, String client, String language, String
   * contextList, Character delimiter) throws ReturnStatusException { long
   * startTime = System.currentTimeMillis (); Integer keys = null; Long numKeys
   * = null; String default1 = null; Boolean inter = null; SingleDataResultSet
   * result = null; int insertedCnt = 0;
   * 
   * DataBaseTable msgKeysTable = getDataBaseTable ("msgKeys").addColumn
   * ("mkey", SQL_TYPE_To_JAVA_TYPE.BIGINT); connection.createTemporaryTable
   * (msgKeysTable); Map<String, String> unquotedParms1 = new HashMap<> ();
   * unquotedParms1.put ("msgKeysTableName", msgKeysTable.getTableName ());
   * final String SQL_QUERY1 =
   * "select defaultLanguage as default1, internationalize as inter from ${ConfigDB}.client where name = ${client} ;"
   * ; SqlParametersMaps parms1 = new SqlParametersMaps ().put ("client",
   * client); String finalQuery = fixDataBaseNames (SQL_QUERY1); result =
   * executeStatement (connection, finalQuery, parms1, false).getResultSets
   * ().next (); DbResultRecord record = (result.getCount () > 0 ?
   * result.getRecords ().next () : null); if (record != null) { default1 =
   * record.<String> get ("default1"); inter = record.<Boolean> get ("inter"); }
   * if (DbComparator.isEqual (inter, false)) { // if (inter == false) {
   * language = default1; } DataBaseTable buildTable = _commonDll._BuildTable_FN
   * (connection, "buildTableName", contextList, delimiter.toString ());
   * _logger.info
   * ("<<<<<<<< TDSCONFIGS_TDS_GetMessages_SP buildTable Execution time : "
   * +(System.currentTimeMillis ()-startTime) +" ms, Thread: " +
   * Thread.currentThread ().getId ()); Map<String, String> unquotedParms7 = new
   * HashMap<> (); unquotedParms7.put ("buildTableName", buildTable.getTableName
   * ()); unquotedParms7.put ("msgKeysTableName", msgKeysTable.getTableName ());
   * // -- First get the key to all message objects used by this system final
   * String SQL_INSERT1 =
   * "insert into ${msgKeysTableName} (mkey)  select _fk_CoreMessageObject from ${ConfigDB}.tds_coremessageuser U, ${ConfigDB}.tds_coremessageobject O, "
   * +
   * " ${buildTableName} T where U.systemID = ${systemID} and O._Key = U._fk_CoreMessageObject and O.Context = T.record;"
   * ; SqlParametersMaps parms2 = new SqlParametersMaps ().put ("systemID",
   * systemID); finalQuery = fixDataBaseNames (SQL_INSERT1); keys =
   * executeStatement (connection, fixDataBaseNames (finalQuery,
   * unquotedParms7), parms2, false).getUpdateCount (); _logger.info (
   * "<<<<<<<< TDSCONFIGS_TDS_GetMessages_SP inserting into  temp table Execution time : "
   * +(System.currentTimeMillis ()-startTime) +" ms, Thread: " +
   * Thread.currentThread ().getId ()); DataBaseTable msgsTable =
   * getDataBaseTable ("msgs").addColumn ("msgkey",
   * SQL_TYPE_To_JAVA_TYPE.BIGINT).addColumn ("msgSource",
   * SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100) .addColumn ("MessageID",
   * SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("ContextType",
   * SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("Context",
   * SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100) .addColumn ("AppKey",
   * SQL_TYPE_To_JAVA_TYPE.VARCHAR, 255).addColumn ("Language",
   * SQL_TYPE_To_JAVA_TYPE.VARCHAR, 30).addColumn ("Grade",
   * SQL_TYPE_To_JAVA_TYPE.VARCHAR, 25) .addColumn ("Subject",
   * SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("ParaLabels",
   * SQL_TYPE_To_JAVA_TYPE.VARCHAR, 255).addColumn ("Message",
   * SQL_TYPE_To_JAVA_TYPE.VARCHAR, 8000); connection.createTemporaryTable
   * (msgsTable); Map<String, String> unquotedParms2 = new HashMap<> ();
   * unquotedParms2.put ("msgsTableName", msgsTable.getTableName ());
   * unquotedParms2.put ("msgKeysTableName", msgKeysTable.getTableName ()); //
   * TODO Elena: added 'unique', just to test!!! final String SQL_INDEX1 =
   * "create unique index _IX_MSGS on ${msgsTableName} (msgkey);"; Map<String,
   * String> unquotedParms3 = new HashMap<> (); unquotedParms3.put
   * ("msgsTableName", msgsTable.getTableName ()); executeStatement (connection,
   * fixDataBaseNames (SQL_INDEX1, unquotedParms3), null, false).getUpdateCount
   * (); // -- prefer messages in the requested language from the given client
   * final String SQL_INSERT2 =
   * "insert IGNORE into ${msgsTableName} (msgkey, msgSource, MessageID, ContextType, Context, Appkey, ParaLabels, Message, Grade, Subject, Language)"
   * +
   * " select K.mkey, ${client}, MessageID, ContextType, Context, AppKey, ParaLabels, T.Message, Grade, Subject, language from ${ConfigDB}.tds_coremessageobject O, "
   * +
   * " ${ConfigDB}.client_messagetranslation T, ${msgKeysTableName} K  where T._fk_CoreMessageObject = mkey and O._key = mkey and T.Language = ${language} and T.client = ${client};"
   * ; finalQuery = fixDataBaseNames (SQL_INSERT2); SqlParametersMaps parms3 =
   * new SqlParametersMaps ().put ("client", client).put ("language", language);
   * insertedCnt = executeStatement (connection, fixDataBaseNames (finalQuery,
   * unquotedParms2), parms3, false).getUpdateCount (); if
   * (DbComparator.notEqual ("ENU", language)) { final String SQL_INSERT3 =
   * "insert IGNORE into ${msgsTableName} (msgkey, msgSource, MessageID, ContextType, Context, Appkey, ParaLabels, Message, Grade, Subject, Language)"
   * +
   * " select K.mkey, ${client}, MessageID, ContextType, Context, AppKey, ParaLabels, T.Message, Grade, Subject, language from ${ConfigDB}.tds_coremessageobject O,"
   * +
   * " ${ConfigDB}.client_messagetranslation T, ${msgKeysTableName} K where T._fk_CoreMessageObject = mkey and O._key = mkey and T.Language = ${ENU} "
   * + " and T.client = ${client};"; finalQuery = fixDataBaseNames
   * (SQL_INSERT3); SqlParametersMaps parms4 = new SqlParametersMaps ().put
   * ("client", client).put ("ENU", "ENU"); insertedCnt = executeStatement
   * (connection, fixDataBaseNames (finalQuery, unquotedParms2), parms4,
   * false).getUpdateCount (); }
   * 
   * // -- If this is a practice test 'client' (e.g. Oregon_PT), then fill in //
   * like above from the true client (Oregon) String clientSuffix = null; if
   * (client.length () >= 3) { clientSuffix = client.substring (client.length ()
   * - 3); } if (clientSuffix != null && "_PT".equalsIgnoreCase (clientSuffix))
   * { String opclient = client.substring (0, client.length () - 3);
   * 
   * final String SQL_INSERT4 =
   * "insert IGNORE into ${msgsTableName} (msgkey, msgSource, MessageID, ContextType, Context, Appkey, ParaLabels, Message, Grade, Subject, Language)"
   * +
   * " select K.mkey, ${opclient}, MessageID, ContextType, Context, AppKey, ParaLabels, T.Message, Grade, Subject, language from ${ConfigDB}.tds_coremessageobject O,"
   * +
   * " ${ConfigDB}.client_messagetranslation T, ${msgKeysTableName} K  where T._fk_CoreMessageObject = mkey and O._key = mkey and T.Language = ${language}"
   * + " and T.client = ${opclient};"; finalQuery = fixDataBaseNames
   * (SQL_INSERT4); SqlParametersMaps parms5 = new SqlParametersMaps ().put
   * ("opclient", opclient).put ("language", language); insertedCnt =
   * executeStatement (connection, fixDataBaseNames (finalQuery,
   * unquotedParms2), parms5, false).getUpdateCount ();
   * 
   * 
   * if (DbComparator.notEqual ("ENU", language)) { final String SQL_INSERT5 =
   * "insert IGNORE into ${msgsTableName} (msgkey, msgSource, MessageID, ContextType, Context, Appkey, ParaLabels, Message, Grade, Subject, Language)"
   * +
   * " select K.mkey, ${opclient}, MessageID, ContextType, Context, AppKey, ParaLabels, T.Message, Grade, Subject, language from ${ConfigDB}.tds_coremessageobject O,"
   * +
   * " ${ConfigDB}.client_messagetranslation T, ${msgKeysTableName} K  where T._fk_CoreMessageObject = mkey and O._key = mkey and T.Language = ${ENU}"
   * + " and T.client = ${opclient};"; finalQuery = fixDataBaseNames
   * (SQL_INSERT5); SqlParametersMaps parms6 = new SqlParametersMaps ().put
   * ("opclient", opclient).put ("ENU", "ENU"); insertedCnt = executeStatement
   * (connection, fixDataBaseNames (finalQuery, unquotedParms2), parms6,
   * false).getUpdateCount (); } } final String SQL_QUERY2 =
   * "select count(distinct msgkey) as numkeys from ${msgsTableName} where grade = ${--ANY--} and subject = ${--ANY--};"
   * ; SqlParametersMaps parms7 = new SqlParametersMaps ().put ("--ANY--",
   * "--ANY--"); Map<String, String> unquotedParms4 = unquotedParms3; result =
   * executeStatement (connection, fixDataBaseNames (SQL_QUERY2,
   * unquotedParms4), parms7, false).getResultSets ().next (); record =
   * (result.getCount () > 0 ? result.getRecords ().next () : null); if (record
   * != null) { numKeys = record.<Long> get ("numkeys"); } if
   * (DbComparator.greaterOrEqual (numKeys, keys)) { final String SQL_QUERY3 =
   * "select * from ${msgsTableName};"; Map<String, String> unquotedParms5 =
   * unquotedParms3; result = executeStatement (connection, fixDataBaseNames
   * (SQL_QUERY3, unquotedParms5), null, false).getResultSets ().next ();
   * connection.dropTemporaryTable (msgsTable); connection.dropTemporaryTable
   * (msgKeysTable); return result; } // -- finally fill in remaining messages
   * directly from 'AIR' messages // -- First, are there any translations
   * standard for all AIR clients final String SQL_INSERT6 =
   * "insert IGNORE into ${msgsTableName} (msgkey, msgSource, MessageID, ContextType, Context, Appkey, ParaLabels, Message, Grade, Subject, Language)"
   * +
   * " select K.mkey, ${AIR}, MessageID, ContextType, Context, AppKey, ParaLabels, T.Message, Grade, Subject, language from ${ConfigDB}.tds_coremessageobject O,"
   * +
   * " ${ConfigDB}.client_messagetranslation T, ${msgKeysTableName} K  where T._fk_CoreMessageObject = mkey and O._key = mkey and T.Language = ${language}"
   * + " and T.client = ${AIR};"; finalQuery = fixDataBaseNames (SQL_INSERT6);
   * SqlParametersMaps parms8 = new SqlParametersMaps ().put ("language",
   * language).put ("AIR", "AIR"); insertedCnt = executeStatement (connection,
   * fixDataBaseNames (finalQuery, unquotedParms2), parms8,
   * false).getUpdateCount (); _logger.info
   * ("<<<<<<<< TDSCONFIGS_TDS_GetMessages_SP insert 3 Execution time : "
   * +(System.currentTimeMillis ()-startTime) +" ms, Thread: " +
   * Thread.currentThread ().getId ()); // -- last resort is the core messages
   * table final String SQL_INSERT7 =
   * "insert IGNORE into ${msgsTableName} (msgkey, msgSource, MessageID, ContextType, Context, Appkey, ParaLabels, Message, Grade, Subject, Language) "
   * +
   * " select K.mkey, OwnerApp, MessageID, ContextType, Context, AppKey, ParaLabels, O.Message, ${--ANY--}, ${--ANY--}, ${ENU} from ${ConfigDB}.tds_coremessageobject O,"
   * + "  ${msgKeysTableName} K where O._key = mkey;"; finalQuery =
   * fixDataBaseNames (SQL_INSERT7); SqlParametersMaps parms9 = new
   * SqlParametersMaps ().put ("--ANY--", "--ANY--").put ("ENU", "ENU");
   * insertedCnt = executeStatement (connection, fixDataBaseNames (finalQuery,
   * unquotedParms2), parms9, false).getUpdateCount (); _logger.info
   * ("<<<<<<<< TDSCONFIGS_TDS_GetMessages_SP insert 4 Execution time : "
   * +(System.currentTimeMillis ()-startTime) +" ms, Thread: " +
   * Thread.currentThread ().getId ()); final String SQL_QUERY4 =
   * "select * from ${msgsTableName} order by ContextType, Context;";
   * Map<String, String> unquotedParms6 = unquotedParms3; result =
   * executeStatement (connection, fixDataBaseNames (SQL_QUERY4,
   * unquotedParms6), null, false).getResultSets ().next ();
   * connection.dropTemporaryTable (msgsTable); connection.dropTemporaryTable
   * (msgKeysTable); _logger.info
   * ("<<<<<<<< TDSCONFIGS_TDS_GetMessages_SP Total Execution time : "
   * +(System.currentTimeMillis ()-startTime) +" ms, Thread: " +
   * Thread.currentThread ().getId ()); return result; }
   */

  public SingleDataResultSet AppMessagesByContext_SP (SQLConnection connection, String systemID, String client, String language, String contextList) throws ReturnStatusException {
    return AppMessagesByContext_SP (connection, systemID, client, language, contextList, ',');
  }

  /**
   * @param connection
   * @param systemID
   * @param client
   * @param language
   * @param contextList
   * @param delimiter
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet AppMessagesByContext_SP (SQLConnection connection, String systemID, String client, String language, String contextList, Character delimiter)
      throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);
    long startTime = System.currentTimeMillis ();
    SingleDataResultSet result = null;

    int end = (contextList.length () > 50 ? 49 : contextList.length ());
    String contextIndex = contextList.substring (0, end);

    final String cmd1 = "select   M.msgkey, M.msgSource, M.MessageID, M.ContextType, M.Context, M.Appkey, M.ParaLabels, M.Message, "
        + " M.Grade, M.Subject, M.Language"
        + " from ${ConfigDB}.__appmessagecontexts A, ${ConfigDB}.__appmessages M "
        + " where A.clientname = ${client} and A.systemid = ${systemid} and "
        + " A.language = ${language} and A.contextindex = ${contextindex} and "
        + " A.contextlist = ${contextlist} and "
        + " A._key = M._fk_AppMessageContext and A.dateGenerated is not null";
    String finalcmd = fixDataBaseNames (cmd1);
    SqlParametersMaps par1 = new SqlParametersMaps ().put ("client", client).put ("systemid", systemID).
        put ("language", language).put ("contextindex", contextIndex).put ("contextlist", contextList);
    result = executeStatement (connection, finalcmd, par1, false).getResultSets ().next ();
    if (result.getCount () > 0) {
      DbResultRecord rec = result.getRecords ().next ();
      Long key = rec.<Long> get ("msgkey");
      _logger.info ("<<<<<<<< TDS_GetMessages_SP Total Optimized Execution time : " + (System.currentTimeMillis () - startTime) + " ms, Thread: " + Thread.currentThread ().getId ());
      _commonDll._LogDBLatency_SP (connection, "AppMessagesByContext", starttime, key, true, null, null, null, client, null);

      return result;
    }
    DataBaseTable tbl = TDS_GetMessages_SP (connection, systemID, client, language, contextList, delimiter);
    final String cmd = "select * from ${tblName} order by ContextType, Context";
    Map<String, String> unquotedParms = new HashMap<> ();
    unquotedParms.put ("tblName", tbl.getTableName ());
    result = executeStatement (connection, fixDataBaseNames (cmd, unquotedParms), null, false).getResultSets ().next ();

    populateAppMessages (connection, tbl, client, language, systemID, contextList, delimiter);
    connection.dropTemporaryTable (tbl);
    _commonDll._LogDBLatency_SP (connection, "AppMessagesByContext", starttime, null, true, null, null, null, client, null);
    return result;
  }

  public String All_FormatMessage_SP (SQLConnection connection, String client, String language, String application, String contextType, String context, String appkey, _Ref<String> msg)
      throws ReturnStatusException {
    return All_FormatMessage_SP (connection, client, language, application, contextType, context, appkey, msg, null, ',', null, null);
  }

  /**
   * @param connection
   * @param client
   * @param language
   * @param application
   * @param contextType
   * @param context
   * @param appkey
   * @param msg
   * @param argstring
   * @param delim
   * @param subject
   * @param grade
   * @return
   * @throws ReturnStatusException
   */
  public String All_FormatMessage_SP (SQLConnection connection, String client, String language, String application, String contextType, String context, String appkey, _Ref<String> msg,
      String argstring, Character delim, String subject, String grade) throws ReturnStatusException {

    String msgKey = null;
    Integer msgID = null;
    Integer indx = null;
    String arg = null;

    DataBaseTable argsTable = getDataBaseTable ("args").addColumn ("indx", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("arg", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 1000);
    connection.createTemporaryTable (argsTable);
    Map<String, String> unquotedparms = new HashMap<String, String> ();
    unquotedparms.put ("argsTableName", argsTable.getTableName ());

    DataBaseTable buildTable = _commonDll._BuildTable_FN (connection, "buildTableName", argstring, delim.toString ());
    Map<String, String> unquotedparms1 = new HashMap<String, String> ();
    unquotedparms1.put ("argsTableName", argsTable.getTableName ());
    unquotedparms1.put ("BuildTableName", buildTable.getTableName ());
    if (argstring != null) {
      final String SQL_INSERT = "insert into ${argsTableName} (indx, arg) select idx, record from ${buildTableName};";
      SqlParametersMaps parms = (new SqlParametersMaps ()).put ("argstring", argstring).put ("delim", delim.toString ());
      int insertedCnt = executeStatement (connection, fixDataBaseNames (SQL_INSERT, unquotedparms1), parms, false).getUpdateCount ();
    }
    msgKey = _commonDll.TDS_GetMessagekey_FN (connection, client, application, contextType, context, appkey, language, grade, subject);

    if (msgKey == null) {
      msg.set (String.format ("%s[-----]", appkey));
      try {
        final String SQL_INSERT1 = "insert into _missingmessages(application,contextType,context, appkey, message) "
            + " select ${application}, ${contextType}, ${context}, ${appkey}, ${msg} from _missingmessages where not exists (Select * from _missingmessages where "
            + " application = ${application} and contextType = ${contextType} and context = ${context} and appkey = ${appkey} and  message = ${msg});";

        SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("application", application).put ("contextType", contextType).put ("context", context)
            .put ("appkey", appkey).put ("msg", msg.get ());
        int insertedCnt = executeStatement (connection, SQL_INSERT1, parms1, false).getUpdateCount ();

      } catch (Exception e) {
        _logger.error (e.getMessage ());
      }
      return msg.toString ();
    }
    if (StringUtils.isNumeric (msgKey)) {
      final String SQL_QUERY = "select message as msg, messageID as msgID from ${ConfigDB}.tds_coremessageobject where _Key = ${msgkey};";
      SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("msgkey", msgKey);
      String finalQuery = fixDataBaseNames (SQL_QUERY);
      SingleDataResultSet result = executeStatement (connection, finalQuery, parms2, false).getResultSets ().next ();
      DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        msg.set (record.<String> get ("msg"));
        msgID = record.<Integer> get ("msgID");
      }
    } else {
      final String SQL_QUERY1 = "select T.message as msg, messageID as msgID from ${ConfigDB}.tds_coremessageobject O, ${ConfigDB}.client_messagetranslation T" +
          " where T._Key = ${msgkey} and O._Key = T._fk_CoreMessageObject;";
      SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("msgkey", msgKey);
      String finalQuery = fixDataBaseNames (SQL_QUERY1);
      SingleDataResultSet result = executeStatement (connection, finalQuery, parms3, false).getResultSets ().next ();
      DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        msg.set (record.<String> get ("msg"));
        msgID = record.<Integer> get ("msgID");
      }
    }
    final String SQL_QUERY2 = "select indx from ${argsTableName} limit 1";
    while (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY2, unquotedparms), null, false))) {
      final String SQL_QUERY3 = "select indx as indx, arg as arg from ${argsTableName} order by indx limit 1;";
      SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY3, unquotedparms), null, false).getResultSets ().next ();
      DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        indx = record.<Integer> get ("indx");
        arg = record.<String> get ("arg");
      }
      msg.set (StringUtils.replace (msg.get (), String.format ("{%d}", indx - 1), arg));

      final String SQL_DELETE = "delete from ${argsTableName} where indx = ${indx};";
      SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("indx", indx);
      int deletedCnt = executeStatement (connection, fixDataBaseNames (SQL_DELETE, unquotedparms), parms4, false).getUpdateCount ();
    }
    msg.set (String.format ("%s [%d]", msg.get (), msgID));

    connection.dropTemporaryTable (argsTable);
    return msg.get ();
  }

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_SetSessionDateVisited_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey) throws ReturnStatusException {

    Date today = _dateUtil.getDateWRetStatus (connection);
    String accessDenied = null;
    String client = null;
    accessDenied = _commonDll.ValidateProctorSession_FN (connection, proctorKey, sessionKey, browserKey);

    if (accessDenied != null) {
      client = getClientNameBySessionKey (connection, sessionKey);
      _commonDll._LogDBError_SP (connection, "P_SetSessionDateVisited", accessDenied, proctorKey, null, null, sessionKey);
      _commonDll._LogDBLatency_SP (connection, "P_SetSessionDateVisited", today, proctorKey, true, null, sessionKey);
      return _commonDll._ReturnError_SP (connection, client, "P_SetSessionDateVisited", accessDenied, null, null, "ValidateProctorSession");
    }
    final String SQL_UPDATE = "update session set DateVisited = ${today} where _Key = ${sessionKey};";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("sessionKey", sessionKey).put ("today", today);
    int updateCnt = executeStatement (connection, SQL_UPDATE, parms, false).getUpdateCount ();

    _commonDll._LogDBLatency_SP (connection, "P_SetSessionDateVisited", today, proctorKey, true, null, null, sessionKey, null, null);
    return null;
  }

  /**
   * @param connection
   * @param sessionKey
   * @return
   * @throws ReturnStatusException
   */
  protected String getClientNameBySessionKey (SQLConnection connection, UUID sessionKey) throws ReturnStatusException {

    String client = null;
    final String SQL_QUERY = "select clientname as client from session where _key = ${sessionKey};";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("sessionKey", sessionKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY, parms, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      client = record.<String> get ("client");
    }
    return client;
  }

  protected Date adjustDateHours (Date theDate, Integer increment) {
    if (theDate == null || increment == null)
      return null;

    Calendar c = Calendar.getInstance ();
    c.setTime (theDate);
    c.add (Calendar.HOUR, increment);
    return c.getTime ();
  }

  protected List<Date> getMidnightsWRetStatus (SQLConnection connection) throws ReturnStatusException {

    List<Date> midnights = new ArrayList<Date> ();
    int timezoneOffset = 0;
    Date midnightAM = null;
    Date midnightPM = null;
    try {
      _dateUtil.calculateMidnights (connection, timezoneOffset);
      midnightAM = _dateUtil.getMidnightAM ();
      midnightPM = _dateUtil.getMidnightPM ();
      midnights.add (midnightAM);
      midnights.add (midnightPM);
    } catch (SQLException se) {
      throw new ReturnStatusException (se);
    }
    return midnights;
  }

  private String replaceSeparatorChar (String str) {
    return str.replace ('/', java.io.File.separatorChar).replace ('\\', java.io.File.separatorChar);
  }
}
