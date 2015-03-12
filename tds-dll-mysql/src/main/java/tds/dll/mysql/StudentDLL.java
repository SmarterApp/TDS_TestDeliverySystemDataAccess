/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.mysql;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import tds.dll.api.ICommonDLL;
import tds.dll.api.IReportingDLL;
import tds.dll.api.IRtsDLL;
import tds.dll.api.IStudentDLL;
import tds.dll.api.LogDBErrorArgs;
import tds.dll.api.LogDBLatencyArgs;
import tds.dll.api.ReturnErrorArgs;
import AIR.Common.DB.AbstractDLL;
import AIR.Common.DB.AbstractDataResultExecutor;
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

public class StudentDLL extends AbstractDLL implements IStudentDLL
{
  private static Logger       _logger       = LoggerFactory.getLogger (StudentDLL.class);

  @Autowired
  private AbstractDateUtilDll _dateUtil     = null;
  @Autowired
  private ICommonDLL          _commonDll    = null;
  @Autowired
  private IRtsDLL             _rtsDll       = null;
  @Autowired
  private IReportingDLL       _reportingDll = null;

  private boolean             _debug        = false;

  /**
   * @param connection
   * @param clientName
   * @param bankKey
   * @param itemKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet IB_GetItemPath_SP (SQLConnection connection, String clientName, long bankKey, long itemKey) throws ReturnStatusException {

    String key = _commonDll.MakeItemKey_FN (connection, bankKey, itemKey);
    String itemPath = _commonDll.ClientItemFile_FN (connection, clientName, key);
    List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
    CaseInsensitiveMap<Object> record = new CaseInsensitiveMap<Object> ();
    record.put ("itemPath", itemPath);
    resultList.add (record);
    SingleDataResultSet result = new SingleDataResultSet ();
    result.addColumn ("itemPath", SQL_TYPE_To_JAVA_TYPE.VARCHAR);

    result.addRecords (resultList);
    return result;
  }

  /**
   * @param connection
   * @param clientName
   * @param bankKey
   * @param stimulusKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet IB_GetStimulusPath_SP (SQLConnection connection, String clientName, long bankKey, long stimulusKey) throws ReturnStatusException {

    String key = _commonDll.MakeStimulusKey_FN (connection, bankKey, stimulusKey);
    String stimulusPath = ClientStimulusFile_FN (connection, clientName, key);
    List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
    CaseInsensitiveMap<Object> record = new CaseInsensitiveMap<Object> ();
    record.put ("stimulusPath", stimulusPath);
    resultList.add (record);
    SingleDataResultSet result = new SingleDataResultSet ();
    result.addColumn ("stimulusPath", SQL_TYPE_To_JAVA_TYPE.VARCHAR);

    result.addRecords (resultList);
    return result;
  }

  /**
   * @param connection
   * @param clientName
   * @param stimulusKey
   * @return
   * @throws ReturnStatusException
   */
  public String ClientStimulusFile_FN (SQLConnection connection, String clientName, String stimulusKey) throws ReturnStatusException {
    String path = null;
    final String SQL_QUERY = "select concat(C.Homepath, B.HomePath, B.stimuliPath, S.FilePath, S.FileName) as path from ${ItemBankDB}.tblitembank B, ${ItemBankDB}.tblclient C, ${ItemBankDB}.tblstimulus S where S._Key = ${stimuluskey} and C.name = ${clientName} "
        + "and B._efk_Itembank = S._efk_Itembank and B._fk_Client = C._Key";
    String query = fixDataBaseNames (SQL_QUERY);
    SqlParametersMaps parameters = new SqlParametersMaps ().put ("clientName", clientName).put ("stimulusKey", stimulusKey);
    SingleDataResultSet result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      path = record.<String> get ("path");
      path = replaceSeparatorChar (path);
    }
    return path;
  }

  /**
   * @param connection
   * @param clientName
   * @param appName
   * @return
   * @throws ReturnStatusException
   */

  public SingleDataResultSet T_GetNetworkDiagnostics_SP (SQLConnection connection, String clientName, String appName) throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);
    final String SQL_QUERY = "SELECT TestLabel, MinDataRateReqd, AveItemSize, ResponseTime FROM ${ConfigDB}.system_networkdiagnostics WHERE ClientName= ${clientName} AND AppName= ${appName}";
    String finalQuery = fixDataBaseNames (SQL_QUERY);
    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("clientName", clientName).put ("appName", appName);
    SingleDataResultSet result = executeStatement (connection, finalQuery, parameters, true).getResultSets ().next ();
    _commonDll._LogDBLatency_SP (connection, "T_GetNetworkDiagnostics", starttime, null, true, null, null, null, null, null);
    return result;

  }

  /**
   * @param connection
   * @param clientname
   * @param OS_ID
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_GetVoicePacks_SP (SQLConnection connection, String clientname, String OS_ID) throws ReturnStatusException {

    final String SQL_QUERY = "select OS_ID, VoicePackName, Priority, LanguageCode from ${ConfigDB}.client_voicepack where ClientName = ${clientname} and (${OS_ID} is null or OS_ID = ${OS_ID})";
    String finalQuery = fixDataBaseNames (SQL_QUERY);
    SqlParametersMaps parameters = new SqlParametersMaps ().put ("clientname", clientname).put ("OS_ID", OS_ID);
    SingleDataResultSet result = executeStatement (connection, finalQuery, parameters, false).getResultSets ().next ();

    return result;

  }

  /**
   * @param connection
   * @param clientname
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_GetVoicePacks_SP (SQLConnection connection, String clientname) throws ReturnStatusException {
    return T_GetVoicePacks_SP (connection, clientname, null);
  }

  /**
   * @param connection
   * @param sessionKey
   * @param oppKey
   * @param itempage
   * @param itemposition
   * @param requestType
   * @param requestValue
   * @param requestParameters
   * @param requestDescription
   * @throws ReturnStatusException
   */
  public void T_SubmitRequest_SP (SQLConnection connection, UUID sessionKey, UUID oppKey, Integer itempage, Integer itemposition, String requestType, String requestValue,
      String requestParameters, String requestDescription) throws ReturnStatusException {

    // This is how valid insert... where not exists... looks for MySql
    // INSERT INTO `table` (value1, value2)
    // SELECT 'stuff for value1', 'stuff for value2' FROM `table`
    // WHERE NOT EXISTS (SELECT * FROM `table`
    // WHERE value1='stuff for value1' AND value2='stuff for value2')
    // LIMIT 1
    final String SQL_INSERT = " insert into testopprequest (_fk_TestOpportunity, _fk_Session, RequestType, RequestValue, ItemPage, ItemPosition, "
        + " RequestParameters, RequestDescription, _key, datesubmitted) "
        + "select * from (select ${oppkey} as _fk_TestOpportunity, ${sessionKey} as _fk_Session, ${requestType} as RequestType, ${requestValue} as RequestValue, ${itempage} as ItemPage, ${itemposition} as ItemPosition, "
        + " ${requestParameters} as RequestParameters, ${requestDescription} as RequestDescription, UNHEX(REPLACE(UUID(),'-','')) as _key, now(3) as datesubmitted limit 1)t "
        + " where not exists"
        + " (select * from testopprequest where _fk_Session = ${sessionKey} and _fk_TestOpportunity = ${oppkey} and RequestType = ${requestType} "
        + "  and RequestValue = ${requestValue} and ItemPage = ${itempage} and ItemPosition = ${itemposition} "
        + "  and DateFulfilled is null) limit 1";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("sessionKey", sessionKey).put ("oppkey", oppKey).put ("itempage", itempage)
        .put ("itemposition", itemposition).put ("requestType", requestType).put ("requestValue", requestValue).put ("requestParameters", requestParameters)
        .put ("requestDescription", requestDescription);
    executeStatement (connection, SQL_INSERT, parms, false).getUpdateCount ();
    // System.err.println (insertedCnt); // for testing
  }

  /**
   * @param connection
   * @param clientname
   * @return
   * @throws ReturnStatusException
   */
  public MultiDataResultSet GetForbiddenApps_SP (SQLConnection connection, String clientname) throws ReturnStatusException {

    List<SingleDataResultSet> resultsSets = new ArrayList<SingleDataResultSet> ();
    final String SQL_QUERY1 = "select AgentOS, OS_ID from client_os";
    SqlParametersMaps parameters1 = new SqlParametersMaps ();
    SingleDataResultSet result1 = executeStatement (connection, SQL_QUERY1, parameters1, false).getResultSets ().next ();
    resultsSets.add (result1);

    final String SQL_QUERY2 = "select OS_ID, ProcessName, ProcessDescription from ${ConfigDB}.client_forbiddenappslist where ClientName = ${clientname}";
    String query2 = fixDataBaseNames (SQL_QUERY2);
    SqlParametersMaps parameters2 = new SqlParametersMaps ().put ("clientname", clientname);
    SingleDataResultSet result2 = executeStatement (connection, query2, parameters2, true).getResultSets ().next ();
    resultsSets.add (result2);

    final String SQL_QUERY3 = "select DistrictName, DistrictID, SchoolName, SchoolID from ${ConfigDB}.client_forbiddenappsexcludeschools where ClientName = ${clientname}";
    String query3 = fixDataBaseNames (SQL_QUERY3);
    SqlParametersMaps parameters3 = new SqlParametersMaps ().put ("clientname", clientname);
    SingleDataResultSet result3 = executeStatement (connection, query3, parameters3, true).getResultSets ().next ();
    resultsSets.add (result3);

    return new MultiDataResultSet (resultsSets);
  }

  /**
   * @param connection
   * @param clientName
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   */
  // commented because its code is inlined in IB_ListTests
  public DataBaseTable ListClientTests_FN (SQLConnection connection, String clientName, Integer sessionType) throws ReturnStatusException {

    final DataBaseTable clientTestsTable = getDataBaseTable ("ClientTests").addColumn ("TestId", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 255).addColumn ("GradeCode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50)
        .addColumn ("Subject", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("LanguageCode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 255).addColumn ("Language", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 255)
        .addColumn ("selectionAlgorithm", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("DisplayName", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 255).addColumn ("SortOrder", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("AccommodationFamily", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("IsSelectable", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("MaxOpportunities", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("MinItems", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("ScoreByTDS", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("validateCompleteness", SQL_TYPE_To_JAVA_TYPE.BIT)
        .addColumn ("MaxItems", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("Prefetch", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("StartAbility", SQL_TYPE_To_JAVA_TYPE.FLOAT)
        .addColumn ("windowStart", SQL_TYPE_To_JAVA_TYPE.DATETIME).addColumn ("windowEnd", SQL_TYPE_To_JAVA_TYPE.DATETIME).addColumn ("FTStartDate", SQL_TYPE_To_JAVA_TYPE.DATETIME)
        .addColumn ("FTEndDate", SQL_TYPE_To_JAVA_TYPE.DATETIME).addColumn ("FTMinItems", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("FTMaxItems", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("IsSegmented", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("_key", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).addColumn ("formSelection", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50);
    connection.createTemporaryTable (clientTestsTable);

    // final String SQL_QUERY =
    // "select distinct P.TestID, P.GradeText as GradeCode, P.SubjectName as Subject, L.code as LanguageCode, L.Value as Language, selectionAlgorithm,"
    // +
    // " P.label as DisplayName, P.SortOrder, P.AccommodationFamily, P.IsSelectable, P.testID, S._Key as validateCompletenessKey, MaxOpportunities, MinItems, MaxItems, Prefetch, StartAbility,"
    // +
    // " case when W.startdate is null then ${now} else (W.startDate + INTERVAL shiftWindowStart DAY ) end as windowStart,"
    // +
    // " case when W.endDate is null then ${now} else ( W.endDate + INTERVAL shiftWindowEnd DAY) end as windowEnd,"
    // +
    // " case when FTStartDate is null then ${now} else (FTStartDate + INTERVAL shiftFTStart DAY ) end as FTStartDate,"
    // +
    // " case when FTEndDate is null then ${now} else (coalesce(FTEndDate, ${now}) + INTERVAL shiftFTEnd DAY ) end as FTEndDate, FTMinItems, FTMaxItems,"
    // +
    // " M.IsSegmented, M.TestKey as _Key, case when requireRTSForm = 1 or requireRTSFormWindow = 1 then 'predetermined' else 'algorithmic' end as formSelection "
    // +
    // "from ${ConfigDB}.client_testproperties P, ${ConfigDB}.client_testmode M, ${ConfigDB}.client_testwindow W, ${ItemBankDB}.tblsetofadminsubjects S,"
    // +
    // " ${ConfigDB}.client_testtool L, _externs E  where W.clientname = ${clientname} and (W.sessionType = -1 or W.sessionType = ${sessionType}) and E.clientname = ${clientname}"
    // +
    // " and ${now} between case when W.startdate is null then ${now}  else (W.startDate + INTERVAL shiftWindowStart DAY ) end and "
    // +
    // "case when W.endDate is null then ${now}  else ( W.endDate + INTERVAL shiftWindowEnd DAY) end and P.clientname = ${clientname}  and P.testID = W.testID"
    // +
    // " and M.clientname = ${clientname} and M.testID = P.testID and (M.sessionType = -1 or M.sessionType =  ${sessionType})"
    // +
    // " and M.testkey = S._Key and L.Clientname =  ${clientname} and L.Type = ${Language} and L.ContextType = ${Test} and L.Context = P.TestID ";
    //
    // String query = fixDataBaseNames (SQL_QUERY);
    // SqlParametersMaps parameters = (new SqlParametersMaps ()).put
    // ("clientname", clientName).put ("sessionType", sessionType).put ("now",
    // now).put ("Language", "Language").put ("TEST", "TEST");
    // SingleDataResultSet result = executeStatement (connection, query,
    // parameters, false).getResultSets ().next ();
    // // declare 2 additional columns in the result SingleDataResultSet.
    // result.addColumn ("ScoreByTDS", SQL_TYPE_To_JAVA_TYPE.BIT);
    // result.addColumn ("validateCompleteness", SQL_TYPE_To_JAVA_TYPE.BIT);
    // Iterator<DbResultRecord> records = result.getRecords ();
    // while (records.hasNext ()) {
    // DbResultRecord record = records.next ();
    // String testId = record.<String> get ("testID");
    // Boolean ScoreByTds = _commonDll.ScoreByTDS_FN (connection, clientName,
    // testId);
    // String key = record.<String> get ("validateCompletenessKey");
    // Boolean ValidateCompleteness = ValidateCompleteness_FN (connection, key);
    // record.addColumnValue ("validateCompleteness", ValidateCompleteness);
    // record.addColumnValue ("ScoreByTDS", ScoreByTds);
    // }
    // if (result.getCount () > 0) {
    // insertBatch (connection, clientTestsTable.generateInsertStatement (),
    // result, null);
    // }
    return clientTestsTable;
  }

  public Boolean ValidateCompleteness_FN (SQLConnection connection, String testKey) throws ReturnStatusException {

    Boolean vc = false;

    final String SQL_QUERY = "select validateCompleteness from ${ConfigDB}.client_testproperties P, ${ConfigDB}.client_testmode M "
        + " where M.testkey = ${testkey} and P.clientname = M.clientname and P.testID = M.testID;";
    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("testkey", testKey);

    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY), parameters, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      vc = record.<Boolean> get ("validateCompleteness");
    }
    return vc;
  }

  /**
   * @param connection
   * @param clientName
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   * @throws SQLException
   */
  // Version which inlines ScoreByTds and ValidateComleteness functions
  public SingleDataResultSet IB_ListTests_SP (SQLConnection connection, String clientName, int sessionType) throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);
    // DataBaseTable clientTestsTable = ListClientTests_FN (connection,
    // clientName, sessionType);
    // Map<String, String> unquotedParms = new HashMap<String, String> ();
    // unquotedParms.put ("clientTestsTableName", clientTestsTable.getTableName
    // ());

    // final String SQL_QUERY1 =
    // "select * from ${clientTestsTableName} order by SortOrder;";
    // SingleDataResultSet result = executeStatement (connection,
    // fixDataBaseNames (SQL_QUERY1, unquotedParms), null, false).getResultSets
    // ().next ();

    // connection.dropTemporaryTable (clientTestsTable);

    final String SQL_QUERY = "select distinct P.TestID, P.GradeText as GradeCode, P.SubjectName as Subject, L.code as LanguageCode, L.Value as Language, selectionAlgorithm," +
        " P.label as DisplayName, P.SortOrder, P.AccommodationFamily, P.IsSelectable, " +
        "  ( case when exists ( select  testid " +
        "          from ${ConfigDB}.client_testscorefeatures " +
        "          where (ReportToStudent = 1 or ReportToProctor = 1 or ReportToParticipation = 1 or UseForAbility = 1) " +
        "                and clientname =  ${clientname} " +
        "                and testid = P.testid) then 1 else 0 end " +
        "  ) as scorebytdsN, " +
        "  cast((   select validateCompleteness " +
        "    from ${ConfigDB}.client_testproperties P, ${ConfigDB}.client_testmode M  " +
        "    where P.testID = M.testID " +
        "          and P.clientname = M.clientname " +
        "          and M.testkey  = S._key " +
        "    limit 1 " +
        " ) as SIGNED) as validatecompletenessN, " +
        "  MaxOpportunities, MinItems, MaxItems, Prefetch, StartAbility," +
        " case when W.startdate is null then ${now} else (W.startDate + INTERVAL shiftWindowStart DAY ) end as windowStart," +
        " case when W.endDate is null then ${now} else ( W.endDate + INTERVAL shiftWindowEnd DAY) end as windowEnd," +
        " case when FTStartDate is null then ${now} else (FTStartDate + INTERVAL shiftFTStart DAY ) end as FTStartDate," +
        " case when FTEndDate is null then ${now} else (coalesce(FTEndDate, ${now}) + INTERVAL shiftFTEnd DAY ) end as FTEndDate, FTMinItems, FTMaxItems," +
        " M.IsSegmented, M.TestKey as _Key, case when requireRTSForm = 1 or requireRTSFormWindow = 1 then 'predetermined' else 'algorithmic' end as formSelection " +
        "from ${ConfigDB}.client_testproperties P, ${ConfigDB}.client_testmode M, ${ConfigDB}.client_testwindow W, ${ItemBankDB}.tblsetofadminsubjects S," +
        " ${ConfigDB}.client_testtool L, _externs E  where W.clientname = ${clientname} and (W.sessionType = -1 or W.sessionType = ${sessionType}) and E.clientname = ${clientname}" +
        " and ${now} between case when W.startdate is null then ${now}  else (W.startDate + INTERVAL shiftWindowStart DAY ) end and " +
        "case when W.endDate is null then ${now}  else ( W.endDate + INTERVAL shiftWindowEnd DAY) end and P.clientname = ${clientname}  and P.testID = W.testID" +
        " and M.clientname = ${clientname} and M.testID = P.testID and (M.sessionType = -1 or M.sessionType =  ${sessionType})" +
        " and M.testkey = S._Key and L.Clientname =  ${clientname} and L.Type = ${Language} and L.ContextType = ${Test} and L.Context = P.TestID " +
        "  order by SortOrder";

    String query = fixDataBaseNames (SQL_QUERY);
    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("clientname", clientName).put ("sessionType", sessionType).put ("now", starttime).put ("Language", "Language").put ("TEST", "TEST");
    SingleDataResultSet result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
    // declare 2 additional columns in the result SingleDataResultSet.
    result.addColumn ("ScoreByTDS", SQL_TYPE_To_JAVA_TYPE.BIT);
    result.addColumn ("validateCompleteness", SQL_TYPE_To_JAVA_TYPE.BIT);
    Iterator<DbResultRecord> records = result.getRecords ();
    while (records.hasNext ()) {
      DbResultRecord record = records.next ();
      // String testId = record.<String> get ("testID");
      // Boolean ScoreByTds = _commonDll.ScoreByTDS_FN (connection, clientName,
      // testId);
      // String key = record.<String> get ("validateCompletenessKey");
      // Boolean ValidateCompleteness = ValidateCompleteness_FN (connection,
      // key);
      // record.addColumnValue ("validateCompleteness", ValidateCompleteness);
      // record.addColumnValue ("ScoreByTDS", ScoreByTds);
      Integer scorebytdsN = record.<Integer> get ("scorebyTdsN");
      Integer validatecompletenessN = record.<Integer> get ("validatecompletenessN");
      if (DbComparator.isEqual (1, scorebytdsN))
        record.addColumnValue ("ScoreByTDS", true);
      else
        record.addColumnValue ("ScoreByTDS", false);
      if (DbComparator.isEqual (1, validatecompletenessN))
        record.addColumnValue ("validateCompleteness", true);
      else
        record.addColumnValue ("validateCompleteness", false);
    }

    _commonDll._LogDBLatency_SP (connection, "IB_ListTests", starttime, null, true, null, null, null, clientName, null);

    return result;
  }

  // Version which uses ScoreByTds and ValidateMigration scalar functions
  // public SingleDataResultSet IB_ListTests_SPV2 (SQLConnection connection,
  // String clientName, int sessionType) throws ReturnStatusException {
  //
  // Date starttime = _dateUtil.getDateWRetStatus (connection);
  //
  // final String SQL_QUERY =
  // "select distinct P.TestID, P.GradeText as GradeCode, P.SubjectName as Subject, L.code as LanguageCode, L.Value as Language, selectionAlgorithm,"
  // +
  // " P.label as DisplayName, P.SortOrder, P.AccommodationFamily, P.IsSelectable, "
  // +
  // " scorebytds(${clientname}, P.TestID) as scorebytds," +
  // " validatecompleteness(S._Key) as validatecompleteness, " +
  // "  MaxOpportunities, MinItems, MaxItems, Prefetch, StartAbility," +
  // " case when W.startdate is null then ${now} else (W.startDate + INTERVAL shiftWindowStart DAY ) end as windowStart,"
  // +
  // " case when W.endDate is null then ${now} else ( W.endDate + INTERVAL shiftWindowEnd DAY) end as windowEnd,"
  // +
  // " case when FTStartDate is null then ${now} else (FTStartDate + INTERVAL shiftFTStart DAY ) end as FTStartDate,"
  // +
  // " case when FTEndDate is null then ${now} else (coalesce(FTEndDate, ${now}) + INTERVAL shiftFTEnd DAY ) end as FTEndDate, FTMinItems, FTMaxItems,"
  // +
  // " M.IsSegmented, M.TestKey as _Key, case when requireRTSForm = 1 or requireRTSFormWindow = 1 then 'predetermined' else 'algorithmic' end as formSelection "
  // +
  // "from ${ConfigDB}.client_testproperties P, ${ConfigDB}.client_testmode M, ${ConfigDB}.client_testwindow W, ${ItemBankDB}.tblsetofadminsubjects S,"
  // +
  // " ${ConfigDB}.client_testtool L, _externs E  where W.clientname = ${clientname} and (W.sessionType = -1 or W.sessionType = ${sessionType}) and E.clientname = ${clientname}"
  // +
  // " and ${now} between case when W.startdate is null then ${now}  else (W.startDate + INTERVAL shiftWindowStart DAY ) end and "
  // +
  // "case when W.endDate is null then ${now}  else ( W.endDate + INTERVAL shiftWindowEnd DAY) end and P.clientname = ${clientname}  and P.testID = W.testID"
  // +
  // " and M.clientname = ${clientname} and M.testID = P.testID and (M.sessionType = -1 or M.sessionType =  ${sessionType})"
  // +
  // " and M.testkey = S._Key and L.Clientname =  ${clientname} and L.Type = ${Language} and L.ContextType = ${Test} and L.Context = P.TestID "
  // +
  // "  order by SortOrder";
  //
  // String query = fixDataBaseNames (SQL_QUERY);
  // SqlParametersMaps parameters = (new SqlParametersMaps ()).put
  // ("clientname", clientName).put ("sessionType", sessionType).put ("now",
  // starttime).put ("Language", "Language").put ("TEST", "TEST");
  // SingleDataResultSet result = executeStatement (connection, query,
  // parameters, false).getResultSets ().next ();
  // // declare 2 additional columns in the result SingleDataResultSet.
  // // result.addColumn ("ScoreByTDS", SQL_TYPE_To_JAVA_TYPE.BIT);
  // // result.addColumn ("validateCompleteness", SQL_TYPE_To_JAVA_TYPE.BIT);
  // // Iterator<DbResultRecord> records = result.getRecords ();
  // // while (records.hasNext ()) {
  // // DbResultRecord record = records.next ();
  //
  // // Integer scorebytdsN = record. <Integer> get("scorebyTdsN");
  // // Integer validatecompletenessN = record.<Integer> get
  // // ("validatecompletenessN");
  // // if (DbComparator.isEqual (1, scorebytdsN))
  // // record.addColumnValue ("ScoreByTDS", true);
  // // else
  // // record.addColumnValue ("ScoreByTDS", false);
  // // if (DbComparator.isEqual (1, validatecompletenessN))
  // // record.addColumnValue ("validateCompleteness", true);
  // // else
  // // record.addColumnValue ("validateCompleteness", false);
  // // }
  //
  // _commonDll._LogDBLatency_SP (connection, "IB_ListTests", starttime, null,
  // true, null, null, null, clientName, null);
  //
  // return result;
  // }

  // Version which calculates all scoreByTds for results set as one database
  // call
  // and all validateComletetess as another call
  // public SingleDataResultSet IB_ListTests_SPV1 (SQLConnection connection,
  // String clientName, int sessionType) throws ReturnStatusException {
  //
  // Date starttime = _dateUtil.getDateWRetStatus (connection);
  // // DataBaseTable clientTestsTable = ListClientTests_FN (connection,
  // // clientName, sessionType);
  // // Map<String, String> unquotedParms = new HashMap<String, String> ();
  // // unquotedParms.put ("clientTestsTableName", clientTestsTable.getTableName
  // // ());
  //
  // // final String SQL_QUERY1 =
  // // "select * from ${clientTestsTableName} order by SortOrder;";
  // // SingleDataResultSet result = executeStatement (connection,
  // // fixDataBaseNames (SQL_QUERY1, unquotedParms), null, false).getResultSets
  // // ().next ();
  //
  // // connection.dropTemporaryTable (clientTestsTable);
  //
  // final String SQL_QUERY =
  // "select distinct P.TestID, P.GradeText as GradeCode, P.SubjectName as Subject, L.code as LanguageCode, L.Value as Language, selectionAlgorithm,"
  // +
  // " P.label as DisplayName, P.SortOrder, P.AccommodationFamily, P.IsSelectable, P.testID, S._Key as validateCompletenessKey, MaxOpportunities, MinItems, MaxItems, Prefetch, StartAbility,"
  // +
  // " case when W.startdate is null then ${now} else (W.startDate + INTERVAL shiftWindowStart DAY ) end as windowStart,"
  // +
  // " case when W.endDate is null then ${now} else ( W.endDate + INTERVAL shiftWindowEnd DAY) end as windowEnd,"
  // +
  // " case when FTStartDate is null then ${now} else (FTStartDate + INTERVAL shiftFTStart DAY ) end as FTStartDate,"
  // +
  // " case when FTEndDate is null then ${now} else (coalesce(FTEndDate, ${now}) + INTERVAL shiftFTEnd DAY ) end as FTEndDate, FTMinItems, FTMaxItems,"
  // +
  // " M.IsSegmented, M.TestKey as _Key, case when requireRTSForm = 1 or requireRTSFormWindow = 1 then 'predetermined' else 'algorithmic' end as formSelection "
  // +
  // "from ${ConfigDB}.client_testproperties P, ${ConfigDB}.client_testmode M, ${ConfigDB}.client_testwindow W, ${ItemBankDB}.tblsetofadminsubjects S,"
  // +
  // " ${ConfigDB}.client_testtool L, _externs E  where W.clientname = ${clientname} and (W.sessionType = -1 or W.sessionType = ${sessionType}) and E.clientname = ${clientname}"
  // +
  // " and ${now} between case when W.startdate is null then ${now}  else (W.startDate + INTERVAL shiftWindowStart DAY ) end and "
  // +
  // "case when W.endDate is null then ${now}  else ( W.endDate + INTERVAL shiftWindowEnd DAY) end and P.clientname = ${clientname}  and P.testID = W.testID"
  // +
  // " and M.clientname = ${clientname} and M.testID = P.testID and (M.sessionType = -1 or M.sessionType =  ${sessionType})"
  // +
  // " and M.testkey = S._Key and L.Clientname =  ${clientname} and L.Type = ${Language} and L.ContextType = ${Test} and L.Context = P.TestID "
  // +
  // "  order by SortOrder";
  //
  // String query = fixDataBaseNames (SQL_QUERY);
  // SqlParametersMaps parameters = (new SqlParametersMaps ()).put
  // ("clientname", clientName).put ("sessionType", sessionType).put ("now",
  // starttime).put ("Language", "Language").put ("TEST", "TEST");
  // SingleDataResultSet result = executeStatement (connection, query,
  // parameters, false).getResultSets ().next ();
  // finishListTests (connection, result, clientName);
  //
  // _commonDll._LogDBLatency_SP (connection, "IB_ListTests", starttime, null,
  // true, null, null, null, clientName, null);
  // return result;
  // }
  //
  // protected void finishListTests (SQLConnection connection,
  // SingleDataResultSet result, String clientname) throws ReturnStatusException
  // {
  // Map<String, Boolean> h1 = new HashMap<> ();
  // Map<String, Boolean> h2 = new HashMap<> ();
  // Iterator<DbResultRecord> records = result.getRecords ();
  // while (records.hasNext ()) {
  // DbResultRecord record = records.next ();
  // if (record.hasColumn ("testID") == false)
  // throw new ReturnStatusException ("Resultset must have testId column");
  //
  // String testId = record.<String> get ("testID");
  // if (h1.containsKey (testId) == false)
  // h1.put (testId, false);
  //
  // if (record.hasColumn ("validateCompletenessKey") == false)
  // throw new ReturnStatusException
  // ("Resultset must have validateCompletenessKey column");
  //
  // String key = record.<String> get ("validateCompletenessKey");
  // if (h2.containsKey (key) == false)
  // h2.put (key, false);
  // }
  //
  // allScoreByTds (connection, clientname, h1);
  // allValidateCompleteness (connection, h2);
  // result.addColumn ("ScoreByTDS", SQL_TYPE_To_JAVA_TYPE.BIT);
  // result.addColumn ("validateCompleteness", SQL_TYPE_To_JAVA_TYPE.BIT);
  //
  // records = result.getRecords ();
  // while (records.hasNext ()) {
  // DbResultRecord record = records.next ();
  //
  // String testId = record.<String> get ("testID");
  // Boolean scoreByTds = h1.get (testId);
  // record.addColumnValue ("ScoreByTDS", scoreByTds);
  //
  // String key = record.<String> get ("validateCompletenessKey");
  // Boolean validateCompleteness = h2.get (key);
  // record.addColumnValue ("validateCompleteness", validateCompleteness);
  // }
  // }

  protected String arrayToQuotedString (String[] rows) {
    if (rows.length == 0) {
      return ("''");
    }
    StringBuilder sb = new StringBuilder ();
    for (String row : rows) {
      sb.append (" '").append (row).append ("',");
    }
    sb.deleteCharAt (sb.length () - 1);
    return sb.toString ();
  }

  protected String keySetToString (Set<String> set) {
    String stringForm = null;
    for (String key : set) {
      if (stringForm == null)
        stringForm = String.format ("'%s'", key);
      else
        stringForm = String.format ("%s, '%s'", stringForm, key);
    }
    return stringForm;
  }

  // protected void allScoreByTds (SQLConnection connection, String clientname,
  // Map<String, Boolean> testidMap) throws ReturnStatusException {
  // String inStr = keySetToString (testidMap.keySet ());
  // final String SQL_QUERY =
  // "select distinct testid from ${ConfigDB}.client_testscorefeatures where clientname = ${client} and TestID in (${inStr}) "
  // +
  // " and (ReportToStudent = 1 or ReportToProctor = 1 or ReportToParticipation = 1 or UseForAbility = 1) ";
  //
  // String query = fixDataBaseNames (SQL_QUERY);// to substitute db name
  //
  // SqlParametersMaps parameters = new SqlParametersMaps ();
  // parameters.put ("client", clientname);
  //
  // Map<String, String> unquotedParms = new HashMap<> ();
  // unquotedParms.put ("inStr", inStr);
  //
  // SingleDataResultSet result = executeStatement (connection, fixDataBaseNames
  // (query, unquotedParms), parameters, false).getResultSets ().next ();
  // Iterator<DbResultRecord> records = result.getRecords ();
  // while (records.hasNext ()) {
  // DbResultRecord record = records.next ();
  // String testId = record.<String> get ("testId");
  // if (testidMap.containsKey (testId))
  // testidMap.put (testId, true);
  // }
  // }
  //
  // protected void allValidateCompleteness (SQLConnection connection,
  // Map<String, Boolean> testidMap) throws ReturnStatusException {
  // String inStr = keySetToString (testidMap.keySet ());
  // final String SQL_QUERY =
  // "select validateCompleteness, M.testkey as testkey from ${ConfigDB}.client_testproperties P, ${ConfigDB}.client_testmode M "
  // +
  // " where M.testkey in (${inStr}) and P.clientname = M.clientname and P.testID = M.testID;";
  // Map<String, String> unquotedParams = new HashMap<String, String> ();
  // unquotedParams.put ("inStr", inStr);
  // String query = fixDataBaseNames (SQL_QUERY);// to substitute db names
  // SingleDataResultSet result = executeStatement (connection, fixDataBaseNames
  // (query, unquotedParams), null, false).getResultSets ().next ();
  // Iterator<DbResultRecord> records = result.getRecords ();
  // while (records.hasNext ()) {
  // DbResultRecord record = records.next ();
  // String testkey = record.<String> get ("testkey");
  // Boolean vc = record.<Boolean> get ("validateCompleteness");
  // if (testidMap.containsKey (testkey))
  // testidMap.put (testkey, vc);
  // }
  // }

  /**
   * Udaya: In the original stored procedure, please note that there is a
   * parameter called "@environment" in the T_GetApplicationSettings.. This
   * parameter has been Deprecated and replaced with value in _externs table. I
   * confirmed with sai on this and i'm not passing this parameter in our java
   * side
   * 
   * @param connection
   * @param clientName
   * @param appName
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_GetApplicationSettings_SP (SQLConnection connection, String clientName, String appName) throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);
    final String SQL_QUERY = "SELECT Property, Type, Value FROM ${ConfigDB}.system_applicationsettings A, _externs E "
        + " WHERE A.ClientName= ${clientName} AND E.clientname = ${clientName} and AppName= ${appName} AND A.Environment= E.environment";
    String finalQuery = fixDataBaseNames (SQL_QUERY);
    SqlParametersMaps parameters = new SqlParametersMaps ().put ("clientName", clientName).put ("appName", appName);
    SingleDataResultSet result = executeStatement (connection, finalQuery, parameters, false).getResultSets ().next ();

    _commonDll._LogDBLatency_SP (connection, "T_GetApplicationSettings", starttime, null, true, null, null, null, null, null);
    return result;

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
        " TType.IsSelectable, IsVisible, studentControl, DependsOnToolType from ${ConfigDB}.client_testtooltype TType, ${ConfigDB}.client_testtool TT" +
        " where TType.ContextType = ${FAMILY} and TType.clientname = ${clientName} and TT.context = ${context} and TType.Context = TT.context and TT.clientname = ${clientName} " +
        " and TT.ContextType = ${FAMILY} and TT.Type = TType.ToolName";
    String query1 = fixDataBaseNames (SQL_QUERY1);
    SqlParametersMaps parameters1 = new SqlParametersMaps ().put ("clientName", clientName).put ("context", context).put ("FAMILY", "FAMILY");
    SingleDataResultSet result1 = executeStatement (connection, query1, parameters1, false).getResultSets ().next ();
    result1.addColumn ("IsFunctional", SQL_TYPE_To_JAVA_TYPE.BIT);
    Iterator<DbResultRecord> records = result1.getRecords ();
    while (records.hasNext ()) {
      DbResultRecord record = records.next ();
      record.addColumnValue ("IsFunctional", true);
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

  public int CompletenessValid_FN (SQLConnection connection, String testkey, String scorestring, Character rowdelim, Character coldelim) throws ReturnStatusException {

    //Just sanity check to avoid exception on checking rows.length
    if (scorestring == null)
      scorestring = "";
    String[] rows = _commonDll._BuildTableAsArray (scorestring, rowdelim.toString (), -1);

    String likeRecord = String.format ("Overall%sAttempted", coldelim);
    // let's eliminate all rows that do not match 'likeRecord' condition
    for (int i = 0; i < rows.length; i++) {
      String row = rows[i];
      if (row.startsWith (likeRecord) == false)
        rows[i] = null;
    }
    // now find the last row that matched 'likeRecord' condition
    for (int i = rows.length - 1; i >= 0; i--) {
      if (rows[i] == null)
        continue;
      // found the matching row while looping from bottom up
      String vals[] = _commonDll._BuildTableAsArray (rows[i], coldelim.toString (), 2);
      if (vals != null) {
        String val = vals[0];
        if ("0".equals (val))
          return 0;
        else if ("1".equals (val))
          return 1;
        else
          return -1;
      }
    }
    // no rows matching likeRecord found
    return -1;
  }

  public Integer T_ValidateCompleteness_SP (SQLConnection connection, UUID oppKey, String scoresString) throws ReturnStatusException {
    return T_ValidateCompleteness_SP (connection, oppKey, scoresString, ';', ':');
  }

  public Integer T_ValidateCompleteness_SP (SQLConnection connection, UUID oppKey, String scoresString, Character rowdelim, Character coldelim) throws ReturnStatusException {
    Integer rs = null;
    String adminSubject = null;

    Date today = _dateUtil.getDateWRetStatus (connection);

    final String SQL_QUERY1 = "select _efk_AdminSubject from testopportunity where _key = ${oppKey}";
    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("oppKey", oppKey);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parameters, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;

    if (record != null) {
      adminSubject = record.<String> get ("_efk_AdminSubject");

      rs = CompletenessValid_FN (connection, adminSubject, scoresString, rowdelim, coldelim);
    }

    _commonDll._LogDBLatency_SP (connection, "T_ValidateCompleteness", today, null, true, null, oppKey);
    return rs;
  }

  /**
   * @param SQLConnection
   *          the database connection
   * @param oppkey
   *          the test opportunity UUID
   * @param session
   *          the session UUID
   * @param browserID
   *          the browser UUID
   * 
   * @return SingleDataResultSet containing the segments for the specified Test
   *         Opportunity.
   * 
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_GetOpportunityComment_SP (SQLConnection connection, UUID oppkey, String context) throws ReturnStatusException {
    return T_GetOpportunityComment_SP (connection, oppkey, context, null);
  }

  public SingleDataResultSet T_GetOpportunityComment_SP (SQLConnection connection, UUID oppkey, String context, Integer itemposition) throws ReturnStatusException {

    final String SQL_QUERY = "select  comment from testeecomment  where _fk_TestOpportunity = ${oppkey} "
        + " and context = ${context} and (${itemposition} is null or itemposition = ${itemposition}) order by _date desc  limit 1";

    SingleDataResultSet result = null;
    SqlParametersMaps parameters = new SqlParametersMaps ();
    parameters.put ("oppkey", oppkey);
    parameters.put ("context", context);
    parameters.put ("itemposition", itemposition);

    result = executeStatement (connection, SQL_QUERY, parameters, true).getResultSets ().next ();
    return result;

  }

  public SingleDataResultSet T_GetOpportunityItems_SP (SQLConnection connection, UUID oppKey) throws ReturnStatusException {
    final String SQL_QUERY = "call t_getopportunityitems (${oppkey})";
    SqlParametersMaps params = new SqlParametersMaps ();
    params.put ("oppkey", oppKey);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY, params, false).getResultSets ().next ();
    return result;
  }
  
  /**
   *     This is the Java version of t_getopportunityitems  stored procedure. This method is not used now. 
   *     It is only needed when we want to migrate the Mysql stored procedure t_getopportunityitems  to java.
   * @param connection
   * @param oppKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_GetOpportunityItems_SP_Java (SQLConnection connection, UUID oppKey) throws ReturnStatusException {
    SingleDataResultSet result = null;

    Date today = _dateUtil.getDateWRetStatus (connection);
    Date archived = null;
    Integer restart = null;

    final String SQL_QUERY1 = "select items_archived as archived,  maxItems,  restart from testopportunity where _Key = ${oppkey}";
    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("oppkey", oppKey);

    result = executeStatement (connection, SQL_QUERY1, parameters, true).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;

    if (record != null) {
      archived = record.<Date> get ("archived");
      restart = record.<Integer> get ("restart");
    }

    if (archived != null) {
      final String SQL_QUERY2 = "select _efk_ITSBank as ItemBank, _efk_ITSItem as Item, position, page, segment, segmentID, "
          + " GroupID, ResponseSequence, IsRequired, DATE_FORMAT (dateGenerated, '%Y-%m-%d %H:%i:%S.%f') as dateCreated, groupItemsRequired, "
          + " Score, Mark, case isInactive  when 1 then -1 else OpportunityRestart end as OpportunityRestart, IsFieldTest, IsSelected, IsValid, Format , "
          + " (case when isInactive = 1 then 0 when OpportunityRestart = ${restart} then 1 else 0 end ) as isVisibleN, "
          + " 0  as readOnlyN from testeeresponsearchive where _fk_TestOpportunity = ${oppkey} and _efk_ITSItem is not null order by position;";

      SqlParametersMaps parameters2 = (new SqlParametersMaps ()).put ("restart", restart).put ("oppkey", oppKey);

      result = executeStatement (connection, SQL_QUERY2, parameters2, false).getResultSets ().next ();
    } else {
      final String SQL_QUERY2 = "select _efk_ITSBank as ItemBank, _efk_ITSItem as Item, position, page, segment, segmentID, "
          + " GroupID, ResponseSequence, IsRequired, DATE_FORMAT (dateGenerated, '%Y-%m-%d %H:%i:%S.%f') as dateCreated, groupItemsRequired, "
          + " Score, Mark, case isInactive  when 1 then -1 else OpportunityRestart end as OpportunityRestart, IsFieldTest, IsSelected, IsValid, Format , "
          + " (case when isInactive = 1 then 0 when OpportunityRestart = ${restart} then 1 else 0 end ) as isVisibleN, "
          + " 0 as readOnlyN, visitcount, dateLastVisited from testeeresponse where _fk_TestOpportunity = ${oppkey} and _efk_ITSItem is not null order by position;";

      SqlParametersMaps parameters2 = (new SqlParametersMaps ()).put ("restart", restart).put ("oppkey", oppKey);

      result = executeStatement (connection, SQL_QUERY2, parameters2, false).getResultSets ().next ();
    }

    result.addColumn ("isVisible", SQL_TYPE_To_JAVA_TYPE.BIT);
    result.addColumn ("readOnly", SQL_TYPE_To_JAVA_TYPE.BIT);
    Iterator<DbResultRecord> records = result.getRecords ();
    while (records.hasNext ()) {
      record = records.next ();
      record.addColumnValue ("isVisible", (record.<Long> get ("isVisibleN") == 1 ? true : false));
      record.addColumnValue ("readOnly", (record.<Long> get ("readOnlyN") == 1 ? true : false));
    }

    _commonDll._LogDBLatency_SP (connection, "T_GetOpportunityItems", today, null, true, null, oppKey);
    return result;
  }

  // orig port
  public MultiDataResultSet IB_GetTestProperties_SPV1 (SQLConnection connection, String testKey) throws ReturnStatusException {

    List<SingleDataResultSet> resultsSets = new ArrayList<SingleDataResultSet> ();

    Date today = _dateUtil.getDateWRetStatus (connection);

    final String SQL_QUERY1 = "select distinct P.TestID, P.GradeText as GradeCode, P.SubjectName as Subject, selectionAlgorithm, P.label as DisplayName, "
        + " P.SortOrder, P.AccommodationFamily, P.IsSelectable, P.clientname, P.testID, MaxOpportunities, MinItems, MaxItems, Prefetch, "
        + " S.IsSegmented, S._Key as _Key, case when requireRTSForm = 1 or requireRTSFormWindow = 1 then 'predetermined' else 'algorithmic' end as formSelection "
        + " from ${ConfigDB}.client_testproperties P, ${ConfigDB}.client_testmode M, ${ItemBankDB}.tblsetofadminsubjects S "
        + " where S._Key = ${testkey} and M.testkey = ${testkey} and P.clientname = M.clientname and M.testID = P.testID order by SortOrder;";

    SqlParametersMaps parameters1 = (new SqlParametersMaps ()).put ("testkey", testKey);

    SingleDataResultSet result1 = executeStatement (connection, fixDataBaseNames (SQL_QUERY1), parameters1, true).getResultSets ().next ();
    // declare 2 additional columns in the result1 SingleDataResultSet.
    result1.addColumn ("ScoreByTDS", SQL_TYPE_To_JAVA_TYPE.BIT);
    result1.addColumn ("validateCompleteness", SQL_TYPE_To_JAVA_TYPE.BIT);

    Iterator<DbResultRecord> records = result1.getRecords ();
    while (records.hasNext ()) {
      DbResultRecord record = records.next ();
      String clientName = record.<String> get ("clientname");
      String testId = record.<String> get ("testID");
      Boolean scoreByTds = _commonDll.ScoreByTDS_FN (connection, clientName, testId);

      String key = record.<String> get ("_Key");
      Boolean validateCompleteness = ValidateCompleteness_FN (connection, key);

      record.addColumnValue ("validateCompleteness", validateCompleteness);
      record.addColumnValue ("ScoreByTDS", scoreByTds);
    }
    resultsSets.add (result1);

    final String SQL_QUERY2 = "select segmentID, segmentPosition, Label as SegmentLabel, IsPermeable, entryApproval, exitApproval, itemReview "
        + " from ${ConfigDB}.client_segmentproperties SEG, ${ConfigDB}.client_testmode M "
        + " where M.testkey = ${testkey} and SEG.clientname = M.clientname and SEG.parentTest = M.TestID  order by segmentPosition;";
    SqlParametersMaps parameters2 = parameters1;
    SingleDataResultSet result2 = executeStatement (connection, fixDataBaseNames (SQL_QUERY2), parameters2, true).getResultSets ().next ();
    resultsSets.add (result2);

    MultiDataResultSet results = new MultiDataResultSet (resultsSets);

    _commonDll._LogDBLatency_SP (connection, "IB_GetTestProperties", today);
    return results;
  }

  // Attempt to optimize; inlined code for ScoreByTds and ValidateCompleteness
  // functions
  public MultiDataResultSet IB_GetTestProperties_SP (SQLConnection connection, String testKey) throws ReturnStatusException {

    List<SingleDataResultSet> resultsSets = new ArrayList<SingleDataResultSet> ();

    Date today = _dateUtil.getDateWRetStatus (connection);

    final String SQL_QUERY1 = "select distinct P.TestID, P.GradeText as GradeCode, P.SubjectName as Subject, selectionAlgorithm, P.label as DisplayName, "
        + " P.SortOrder, P.AccommodationFamily, P.IsSelectable, P.clientname, P.testID, MaxOpportunities, MinItems, MaxItems, Prefetch, "
        + " S.IsSegmented, S._Key as _Key, case when requireRTSForm = 1 or requireRTSFormWindow = 1 then 'predetermined' else 'algorithmic' end as formSelection, "

        + "  ( case when exists ( select  testid "
        + "          from ${ConfigDB}.client_testscorefeatures "
        + "          where (ReportToStudent = 1 or ReportToProctor = 1 or ReportToParticipation = 1 or UseForAbility = 1) "
        + "                and clientname =  P.clientname "
        + "                and testid = P.testid) then 1 else 0 end "
        + "  ) as scorebytdsN, "
        + "  cast((   select validateCompleteness "
        + "    from ${ConfigDB}.client_testproperties P, ${ConfigDB}.client_testmode M  "
        + "    where P.testID = M.testID "
        + "          and P.clientname = M.clientname "
        + "          and M.testkey  = S._key "
        + "    limit 1 "
        + " ) as SIGNED) as validatecompletenessN "

        + " from ${ConfigDB}.client_testproperties P, ${ConfigDB}.client_testmode M, ${ItemBankDB}.tblsetofadminsubjects S "
        + " where S._Key = ${testkey} and M.testkey = ${testkey} and P.clientname = M.clientname and M.testID = P.testID order by SortOrder;";
    SqlParametersMaps parameters1 = (new SqlParametersMaps ()).put ("testkey", testKey);

    SingleDataResultSet result1 = executeStatement (connection, fixDataBaseNames (SQL_QUERY1), parameters1, true).getResultSets ().next ();
    // declare 2 additional columns in the result1 SingleDataResultSet.
    result1.addColumn ("ScoreByTDS", SQL_TYPE_To_JAVA_TYPE.BIT);
    result1.addColumn ("validateCompleteness", SQL_TYPE_To_JAVA_TYPE.BIT);

    Iterator<DbResultRecord> records = result1.getRecords ();
    while (records.hasNext ()) {
      DbResultRecord record = records.next ();

      Integer scorebytdsN = record.<Integer> get ("scorebyTdsN");
      Integer validatecompletenessN = record.<Integer> get ("validatecompletenessN");
      if (DbComparator.isEqual (1, scorebytdsN))
        record.addColumnValue ("ScoreByTDS", true);
      else
        record.addColumnValue ("ScoreByTDS", false);
      if (DbComparator.isEqual (1, validatecompletenessN))
        record.addColumnValue ("validateCompleteness", true);
      else
        record.addColumnValue ("validateCompleteness", false);
    }
    resultsSets.add (result1);

    final String SQL_QUERY2 = "select segmentID, segmentPosition, Label as SegmentLabel, IsPermeable, entryApproval, exitApproval, itemReview "
        + " from ${ConfigDB}.client_segmentproperties SEG, ${ConfigDB}.client_testmode M "
        + " where M.testkey = ${testkey} and SEG.clientname = M.clientname and SEG.parentTest = M.TestID  order by segmentPosition;";
    SqlParametersMaps parameters2 = parameters1;
    SingleDataResultSet result2 = executeStatement (connection, fixDataBaseNames (SQL_QUERY2), parameters2, true).getResultSets ().next ();
    resultsSets.add (result2);

    MultiDataResultSet results = new MultiDataResultSet (resultsSets);

    _commonDll._LogDBLatency_SP (connection, "IB_GetTestProperties", today);
    return results;
  }

  public void _ValidateTesteeAccessProc_SP (SQLConnection connection, UUID testoppkey, UUID session, UUID browserId, Boolean checkSession, _Ref<String> message) throws ReturnStatusException {
    Date now = _dateUtil.getDateWRetStatus (connection);
    if (testoppkey != null && session != null & browserId != null)
      _logger.info ("_ValidateTesteeAccessProc: opp " + testoppkey.toString () + " session " + session.toString () + " browser " + browserId.toString ());
    else
      _logger.info ("_ValidateTesteeAccessProc.");

    final String SQL_QUERY1 = "select  O._Key from _externs E, testopportunity O "
        + " where O._Key = ${testoppkey} and E.clientname = O.clientname and E.environment in ('Development', 'SIMULATION') limit 1";
    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("testoppkey", testoppkey);

    if (exists (executeStatement (connection, SQL_QUERY1, parameters, false))) {
      checkSession = false;
    }
    UUID sessionKey = null;
    UUID browserKey = null;
    final String SQL_QUERY2 = "SELECT _fk_Session as sessionKey,  _fk_browser as browserKey from testopportunity where _Key = ${testoppkey}";
    SqlParametersMaps parameters2 = parameters;

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY2, parameters2, true).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      sessionKey = record.<UUID> get ("sessionKey");
      browserKey = record.<UUID> get ("browserKey");
    }
    if (DbComparator.notEqual (browserKey, browserId)) {
      if (browserId != null && browserKey != null)
        _logger.info ("browser key does not match " + browserId.toString () + " should be " + browserKey.toString ());
      else
        _logger.info ("browser key does not match.");
      message.set ("Access violation: System access denied");
      return;
    }

    if (sessionKey == null || session == null || DbComparator.notEqual (sessionKey, session)) {
      if (sessionKey != null && session != null)
        _logger.info ("session key does not match " + session.toString () + " should be " + sessionKey.toString ());
      else
        _logger.info ("session key does not match.");
      message.set ("The session keys do not match; please consult your test administrator");
      return;
    }

    String sessionStatus = null;
    Date dateBegin = null;
    Date dateEnd = null;
    Date dateVisited = null;
    String clientName = null;
    Long proctor = null;
    UUID sessionBrowser = null;

    if (DbComparator.isEqual (checkSession, true)) {

      final String SQL_QUERY3 = "select status as sessionStatus, DateBegin, DateEnd, dateVisited, clientname, "
          + " _efk_Proctor as proctor,  _fk_Browser as sessionBrowser from session where _Key = ${session}";
      SqlParametersMaps parameters3 = (new SqlParametersMaps ()).put ("session", session);

      result = executeStatement (connection, SQL_QUERY3, parameters3, true).getResultSets ().next ();
      record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        sessionStatus = record.<String> get ("sessionStatus");
        dateBegin = record.<Date> get ("dateBegin");
        dateEnd = record.<Date> get ("dateEnd");
        dateVisited = record.<Date> get ("dateVisited");
        clientName = record.<String> get ("clientName");
        proctor = record.<Long> get ("proctor");
        sessionBrowser = record.<UUID> get ("sessionBrowser");
      }
      dateBegin = _commonDll.adjustDateMinutes (dateBegin, -5);
      if (DbComparator.notEqual (sessionStatus, "open") || DbComparator.lessThan (now, dateBegin) || DbComparator.greaterThan (now, dateEnd)) {
        // if ("open".equalsIgnoreCase (sessionStatus) == false || now.compareTo
        // (dateBegin) < 0 || now.compareTo (dateEnd) > 0) {
        message.set ("The session is not available for testing, please check with your test administrator.");
        return;
      }

      if (proctor == null) {
        // this is a proctorless session so proctor 'check in' is impossible
        return;
      }

      Integer checkin = null;
      final String SQL_QUERY4 = "select TACheckInTime as checkin from timelimits where clientname = ${clientname} and _efk_TestID is null";
      SqlParametersMaps parameters4 = (new SqlParametersMaps ()).put ("clientname", clientName);

      result = executeStatement (connection, SQL_QUERY4, parameters4, true).getResultSets ().next ();
      record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        checkin = record.<Integer> get ("checkin");
      }
      dateVisited = _commonDll.adjustDateMinutes (dateVisited, checkin);
      if (checkin != null && checkin > 0 && DbComparator.greaterThan (now, dateVisited)) {

        // String sessionDB = getAppSettings ().get ("TDSSessionDBName");
        String sessionDB = getTdsSettings ().getTDSSessionDBName ();
        final String SQL_INSERT = "insert into ${ArchiveDB}.sessionaudit (_fk_session, AccessType, browserKey, dateAccessed, hostname, dbname)"
            + " values (${session}, 'TACheckin TIMEOUT', ${sessionBrowser}, ${now}, ${localhost}, ${dbname})";
        SqlParametersMaps parms = (new SqlParametersMaps ()).put ("session", session).put ("sessionBrowser", sessionBrowser).put ("now", now).
            put ("localhost", _commonDll.getLocalhostName ()).put ("dbname", sessionDB);

        MultiDataResultSet sets = executeStatement (connection, fixDataBaseNames (SQL_INSERT), parms, false);
        sets.getUpdateCount ();
        _commonDll.P_PauseSession_SP (connection, session, proctor, sessionBrowser);
        message.set ("The session is not available for testing, please check with your test administrator.");
        return;
      }
    }
  }

  public Integer AuditLatencies_FN (SQLConnection connection, String clientname) throws ReturnStatusException {

    Integer flag = selectIsOnByAuditObject (connection, clientname, "latencies");
    if (flag == null || flag == 0)
      return 0;
    else
      return 1;
  }

  public int T_RecordServerLatency_SP (SQLConnection connection, String operation, UUID oppkey, UUID session,
      UUID browser, Integer serverLatency) throws ReturnStatusException {

    return T_RecordServerLatency_SP (connection, operation, oppkey, session, browser, serverLatency, null, null, null);
  }

  public int T_RecordServerLatency_SP (SQLConnection connection, String operation, UUID oppkey, UUID session,
      UUID browser, Integer serverLatency, Integer dblatency, String pageList, String itemList) throws ReturnStatusException {

    final String SQL_QUERY = "select clientname from testopportunity where _Key = ${oppkey}";
    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("oppkey", oppkey);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY, parameters, false).getResultSets ().next ();

    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    String clientname = null;

    if (record != null) {
      clientname = record.<String> get ("clientname");
    }

    if (AuditLatencies_FN (connection, clientname) == 0)
      return 0;

    String sessionDB = getTdsSettings ().getTDSSessionDBName ();
    // String sessionDB = getAppSettings ().get ("TDSSessionDBName");
    final String SQL_INSERT = "insert into ${ArchiveDB}.serverlatency "
        + " (_fk_TestOpportunity, _fk_session, _fk_browser, Operation,  ServerLatency,  DbLatency, pageList, itemList, clientname, _date, hostname, dbname) "
        + " values ( ${oppkey}, ${session}, ${browser},  ${operation}, ${serverlatency},  ${dblatency}, ${pageList}, ${itemList}, ${clientname}, "
        + "          now(3), ${localhost}, ${dbname} )";
    SqlParametersMaps parms = new SqlParametersMaps ();
    parms.put ("oppkey", oppkey);
    parms.put ("session", session);
    parms.put ("browser", browser);
    parms.put ("operation", operation);
    parms.put ("serverlatency", serverLatency);
    parms.put ("dblatency", dblatency);
    parms.put ("pagelist", pageList);
    parms.put ("itemlist", itemList);
    parms.put ("clientname", clientname).put ("localhost", _commonDll.getLocalhostName ()).put ("dbname", sessionDB);

    MultiDataResultSet sets = executeStatement (connection, fixDataBaseNames (SQL_INSERT), parms, false);
    return sets.getUpdateCount ();

  }

  public void T_RecordClientLatency_SP (SQLConnection connection, UUID oppkey, UUID session, UUID browser, int itempage, int numitems, int visitCount,
      Date createDate, Date loadDate, int loadTime, int requestTime, int visitTime, Integer loadAttempts, Date visitDate, String toolsUsed) throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);

    Long testee = null;
    String clientname = null;
    final String SQL_QUERY1 = "select _efk_Testee as testee, clientname from testopportunity where _Key = ${oppkey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      testee = record.<Long> get ("testee");
      clientname = record.<String> get ("clientname");
    }
    if (AuditLatencies_FN (connection, clientname) == 0)
      return;

    final String SQL_QUERY2 = "select RTSname as rtsfield, TDS_ID as tdsid from ${ConfigDB}.client_testeeattribute where clientname = ${clientname} and isLatencySite = 1";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("clientname", clientname);
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY2), parms2, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;

    String rtsfield = null;
    String tdsid = null;
    if (record != null) {
      rtsfield = record.<String> get ("rtsfield");
      tdsid = record.<String> get ("tdsid");
    }

    String siteId = null, siteName = null;
    Long siteKey = null;
    if (rtsfield != null) {
      _Ref<Long> siteKeyObj = new _Ref<Long> ();
      _Ref<String> siteIdObj = new _Ref<String> ();
      _Ref<String> siteNameObj = new _Ref<String> ();

      _rtsDll._GetRTSRelationship_SP (connection, clientname, testee, rtsfield, siteKeyObj, siteIdObj, siteNameObj);
      siteKey = siteKeyObj.get ();
      siteId = siteIdObj.get ();
      siteName = siteNameObj.get ();
    } else {

      final String SQL_QUERY3 = "select entityKey as siteKey, attributeValue as siteId from testeerelationship "
          + " where TDS_ID = 'SchoolID' and _fk_TestOpportunity = ${oppkey} and context = 'INITIAL'";
      SqlParametersMaps parms3 = parms1;
      result = executeStatement (connection, SQL_QUERY3, parms3, false).getResultSets ().next ();
      record = result.getCount () > 0 ? result.getRecords ().next () : null;

      if (record != null) {
        siteKey = record.<Long> get ("siteKey");
        siteId = record.<String> get ("siteid");
      }
    }

    final String SQL_QUERY4 = "select convert(sum(contentsize), SIGNED) as contentsize,  min(groupID) as groupID from ${ItemBankDB}.tblitem I, testeeresponse R "
        + " where R._fk_TestOpportunity = ${oppkey} and R.page = ${itempage} and R._efk_ItemKey = I._Key";
    SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("oppkey", oppkey).put ("itempage", itempage);

    Integer contentsize = null;
    String groupId = null;
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY4), parms4, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;

    if (record != null) {
      Long tmp = record.<Long> get ("contentsize");
      contentsize = (tmp == null ? null : tmp.intValue ());
      groupId = record.<String> get ("groupid");
    }

    if (visitTime < 0)
      visitTime = 0;
    if (loadTime < 0)
      loadTime = 0;
    if (requestTime < 0)
      requestTime = 0;

    final String SQL_INSERT5 = "insert into clientlatency ( _fk_TestOpportunity, _fk_session, _fk_browser,  Page, NumItems, "
        + " VisitCount, VisitTime, CreateDate, LoadDate, LoadTime, RequestTime, LoadAttempts, "
        + " groupID, contentsize, siteKey, siteID, siteName, clientname, _date) "
        + " values ( ${oppkey}, ${session}, ${browser}, ${itempage}, ${numitems}, "
        + "          ${visitCount}, ${visitTime}, ${createDate}, ${loadDate}, ${loadTime}, ${requestTime}, ${loadAttempts}, "
        + "          ${groupID}, ${contentsize}, ${siteKey}, ${siteID}, ${sitename}, ${clientname}, now(3))";

    SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("oppkey", oppkey).put ("session", session).put ("browser", browser).put ("itempage", itempage).put ("numitems", numitems);
    parms5.put ("visitCount", visitCount).put ("createDate", createDate).put ("visitTime", visitTime).put ("loadDate", loadDate).put ("loadTime", loadTime).put ("requestTime", requestTime)
        .put ("loadAttempts", loadAttempts);
    parms5.put ("groupId", groupId).put ("contentsize", contentsize).put ("siteKey", siteKey).put ("siteName", siteName).put ("siteId", siteId).put ("clientname", clientname);

    executeStatement (connection, SQL_INSERT5, parms5, false).getUpdateCount ();

    if (visitCount > 0) {
      final String SQL_QUERY6 = "update testeeresponse set dateLastVisited = coalesce(${visitDate}, ${now}), visitCount = visitCount + ${visitCount} "
          + " where _fk_TestOpportunity = ${oppkey} and page = ${itempage}";

      SqlParametersMaps parms6 = (new SqlParametersMaps ()).put ("visitDate", visitDate).put ("now", starttime).put ("visitCount", visitCount).put ("oppkey", oppkey).put ("itempage", itempage);

      executeStatement (connection, SQL_QUERY6, parms6, false).getUpdateCount ();
    }
    if (toolsUsed != null) {
      DataBaseTable tmpTbl = _commonDll._BuildTable_FN (connection, "xyz", toolsUsed, ";");

      final String SQL_INSERT8 = "insert into testopptoolsused (_fk_TestOpportunity, itempage, toolCode, toolType, groupID)"
          + " select distinct ${oppkey}, ${itempage}, record , AccType, ${groupID}  from  ${tmpTblName}, testeeaccommodations "
          + "where _fk_TestOpportunity = ${oppkey} and AccCode = record";

      Map<String, String> unquotedParms = new HashMap<String, String> ();
      unquotedParms.put ("tmpTblName", tmpTbl.getTableName ());
      SqlParametersMaps parms7 = (new SqlParametersMaps ()).put ("oppkey", oppkey).put ("itempage", itempage).put ("groupId", groupId);

      executeStatement (connection, fixDataBaseNames (SQL_INSERT8, unquotedParms), parms7, false).getUpdateCount ();

      connection.dropTemporaryTable (tmpTbl);
    }

    _commonDll._LogDBLatency_SP (connection, "T_RecordClientLatency", starttime, null, true, null, oppkey, session, clientname, null);
  }

  public void T_RecordClientLatency_XML_SP (SQLConnection connection, UUID oppkey, UUID session, UUID browser, String latencies) throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);

    Long testee = null;
    String clientname = null;
    final String SQL_QUERY1 = "select _efk_Testee as testee, clientname from testopportunity where _Key = ${oppkey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      testee = record.<Long> get ("testee");
      clientname = record.<String> get ("clientname");
    }
    DataBaseTable tbl = getDataBaseTable ("xyz").addColumn ("itempage", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("numitems", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("numvisits", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("createdate", SQL_TYPE_To_JAVA_TYPE.DATETIME).
        addColumn ("loaddate", SQL_TYPE_To_JAVA_TYPE.DATETIME).addColumn ("loadtime", SQL_TYPE_To_JAVA_TYPE.FLOAT).addColumn ("requesttime", SQL_TYPE_To_JAVA_TYPE.FLOAT).
        addColumn ("visittime", SQL_TYPE_To_JAVA_TYPE.FLOAT).addColumn ("loadattempts", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("visitdate", SQL_TYPE_To_JAVA_TYPE.DATETIME).
        addColumn ("segmentkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).addColumn ("gid", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("contentsize", SQL_TYPE_To_JAVA_TYPE.INT);

    connection.createTemporaryTable (tbl);

    final String latenciesFinal = latencies;
    executeMethodAndInsertIntoTemporaryTable (connection, new AbstractDataResultExecutor ()
    {
      @Override
      public SingleDataResultSet execute (SQLConnection connection) throws ReturnStatusException {

        List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
        try {
          InputStream is = new ByteArrayInputStream (latenciesFinal.getBytes ("UTF-8"));
          DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance ();
          DocumentBuilder loader = factory.newDocumentBuilder ();

          Document doc = loader.parse (is);
          doc.getDocumentElement ().normalize ();

          NodeList nodeList = doc.getElementsByTagName ("latency");
          for (int i = 0; i < nodeList.getLength (); i++) {
            Node currentNode = nodeList.item (i);
            if (currentNode.getNodeType () == Node.ELEMENT_NODE) {
              CaseInsensitiveMap<Object> record = new CaseInsensitiveMap<Object> ();
              NamedNodeMap nodeMap = currentNode.getAttributes ();
              // select x.value('@page', 'int') as page, x.value('@numitems',
              // 'int'), x.value('@visitcount', 'int') , x.value('@createdate',
              // 'datetime')
              // , x.value('@loaddate', 'datetime'), x.value('@loadtime',
              // 'float'), x.value('@requesttime', 'float'),
              // x.value('@visittime', 'float')
              // ,x.value('@loadattempts', 'int'
              record.put ("itempage", nodeMap.getNamedItem ("page").getNodeValue ());
              record.put ("numitems", nodeMap.getNamedItem ("numitems").getNodeValue ());
              record.put ("numvisits", nodeMap.getNamedItem ("visitcount").getNodeValue ());
              if ("".equals (nodeMap.getNamedItem ("createdate").getNodeValue ()))
                record.put ("createdate", null);
              else
                record.put ("createdate", nodeMap.getNamedItem ("createdate").getNodeValue ());
              if ("".equals (nodeMap.getNamedItem ("loaddate").getNodeValue ()))
                record.put ("loaddate", null);
              else
                record.put ("loaddate", nodeMap.getNamedItem ("loaddate").getNodeValue ());
              record.put ("loadtime", nodeMap.getNamedItem ("loadtime").getNodeValue ());
              record.put ("requesttime", nodeMap.getNamedItem ("requesttime").getNodeValue ());
              record.put ("visittime", nodeMap.getNamedItem ("visittime").getNodeValue ());
              record.put ("loadattempts", nodeMap.getNamedItem ("loadattempts").getNodeValue ());
              record.put ("visitdate", null);
              // TODO added these three lines, otherwise framework throws
              // exception, rightfully so while trying to insert recs to tmp tbl
              record.put ("contentsize", 0);
              record.put ("gid", null);
              record.put ("segmentkey", null);

              resultList.add (record);
            }
          }
        } catch (Exception e) {
          // UnsupportedEncodingException, FactoryConfigurationError,
          // ParserConfigurationException, SAXException, IOException,
          // IllegalArgumentException,DOMException
          // TODO throw ReturnStatusexception?
          _logger.error (e.getMessage ());
          throw new ReturnStatusException (e);
        }
        SingleDataResultSet rs = new SingleDataResultSet ();
        rs.addColumn ("itempage", SQL_TYPE_To_JAVA_TYPE.INT);
        rs.addColumn ("numitems", SQL_TYPE_To_JAVA_TYPE.INT);
        rs.addColumn ("numvisits", SQL_TYPE_To_JAVA_TYPE.INT);
        rs.addColumn ("createdate", SQL_TYPE_To_JAVA_TYPE.DATETIME);
        rs.addColumn ("loaddate", SQL_TYPE_To_JAVA_TYPE.DATETIME);
        rs.addColumn ("loadtime", SQL_TYPE_To_JAVA_TYPE.FLOAT);
        rs.addColumn ("requesttime", SQL_TYPE_To_JAVA_TYPE.FLOAT);
        rs.addColumn ("visittime", SQL_TYPE_To_JAVA_TYPE.FLOAT);
        rs.addColumn ("loadattempts", SQL_TYPE_To_JAVA_TYPE.INT);
        rs.addColumn ("visitdate", SQL_TYPE_To_JAVA_TYPE.DATETIME);
        rs.addColumn ("segmentkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
        rs.addColumn ("gid", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
        rs.addColumn ("contentsize", SQL_TYPE_To_JAVA_TYPE.INT);

        rs.addRecords (resultList);
        return rs;
      }
    }, tbl, false);

    final String SQL_QUERY2 = "select RTSname as rtsfield, TDS_ID as tdsid from ${ConfigDB}.client_testeeattribute where clientname = ${clientname} and isLatencySite = 1";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("clientname", clientname);
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY2), parms2, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;

    String rtsfield = null;
    String tdsid = null;
    if (record != null) {
      rtsfield = record.<String> get ("rtsfield");
      tdsid = record.<String> get ("tdsid");
    }

    String siteId = null, siteName = null;
    Long siteKey = null;

    if (rtsfield != null) {
      _Ref<Long> siteKeyObj = new _Ref<Long> ();
      _Ref<String> siteIdObj = new _Ref<String> ();
      _Ref<String> siteNameObj = new _Ref<String> ();

      _rtsDll._GetRTSRelationship_SP (connection, clientname, testee, rtsfield, siteKeyObj, siteIdObj, siteNameObj);
      siteKey = siteKeyObj.get ();
      siteId = siteIdObj.get ();
      siteName = siteNameObj.get ();
    } else {

      final String SQL_QUERY3 = "select entityKey as siteKey, attributeValue as siteId from testeerelationship "
          + " where TDS_ID = 'SchoolID' and _fk_TestOpportunity = ${oppkey} and context = 'INITIAL'";
      SqlParametersMaps parms3 = parms1;
      result = executeStatement (connection, SQL_QUERY3, parms3, false).getResultSets ().next ();
      record = result.getCount () > 0 ? result.getRecords ().next () : null;

      if (record != null) {
        siteKey = record.<Long> get ("siteKey");
        siteId = record.<String> get ("siteid");
      }
    }

    final String SQL_UPDATE4 = "update ${tmpTblName} T, testeeresponse R, testopportunitysegment S set T.GID = groupID, T.segmentkey = _efk_Segment  "
        + " where R._fk_TestOpportunity = ${oppkey} and S._fk_TestOpportunity = ${oppkey} and R.page = itempage and R.segment = S.segmentPosition";
    Map<String, String> unquotedParms4 = new HashMap<String, String> ();
    unquotedParms4.put ("tmpTblName", tbl.getTableName ());

    SqlParametersMaps parms4 = parms1;
    executeStatement (connection, fixDataBaseNames (SQL_UPDATE4, unquotedParms4), parms4, false).getUpdateCount ();

    final String SQL_UPDATE5 = "update ${tmpTblName} set contentsize = (select sum(contentsize) from ${ItemBankDB}.tblitem I, ${ItemBankDB}.tblsetofadminitems A "
        + " where A._fk_AdminSubject = segmentKey and A.groupID = GID and A._fk_Item = I._Key)";
    final String query = fixDataBaseNames (SQL_UPDATE5);
    Map<String, String> unquotedParms5 = unquotedParms4;
    SqlParametersMaps parms5 = parms1;
    executeStatement (connection, fixDataBaseNames (query, unquotedParms5), parms5, false).getUpdateCount ();

    final String SQL_INSERT6 = "insert into clientlatency ( _fk_TestOpportunity, _fk_session, _fk_browser,  page, NumItems, "
        + " VisitCount, VisitTime, CreateDate, LoadDate, LoadTime, RequestTime, LoadAttempts,"
        + " groupID, contentsize, siteKey, siteID, siteName, clientname, _date)"
        + " select ${oppkey}, ${session}, ${browser}, itempage, numitems, numvisits, visittime, createdate, loaddate, loadtime, "
        + " requesttime, loadattempts, GID, contentsize, ${siteKey}, ${siteID}, ${sitename}, ${clientname}, now(3) from ${tmpTblName}";
    Map<String, String> unquotedParms6 = unquotedParms4;
    SqlParametersMaps parms6 = (new SqlParametersMaps ()).put ("oppkey", oppkey).put ("session", session).put ("browser", browser);
    parms6.put ("siteKey", siteKey).put ("siteID", siteId).put ("siteName", siteName).put ("clientname", clientname);

    executeStatement (connection, fixDataBaseNames (SQL_INSERT6, unquotedParms6), parms6, false).getUpdateCount ();

    final String SQL_UPDATE7 = "update testeeresponse T, ${tmpTblName} set T.dateLastVisited = coalesce(visitDate, ${now}), "
        + " T.visitCount = visitCount + numvisits "
        + " where numvisits > 0 and _fk_TestOpportunity = ${oppkey} and page = itempage";
    Map<String, String> unquotedParms7 = unquotedParms4;
    SqlParametersMaps parms7 = (new SqlParametersMaps ()).put ("now", starttime).put ("oppkey", oppkey);

    executeStatement (connection, fixDataBaseNames (SQL_UPDATE7, unquotedParms7), parms7, false).getUpdateCount ();

    connection.dropTemporaryTable (tbl);

    _commonDll._LogDBLatency_SP (connection, "T_RecordClientLatency_XML", starttime, null, true, null, oppkey, session, clientname, null);
  }

  public SingleDataResultSet IB_GetTestGrades_SP (SQLConnection connection, String clientname, String testkey) throws ReturnStatusException {
    return IB_GetTestGrades_SP (connection, clientname, testkey, 0);
  }

  public SingleDataResultSet IB_GetTestGrades_SP (SQLConnection connection, String clientname, String testkey, Integer sessionType) throws ReturnStatusException {

    Date today = _dateUtil.getDateWRetStatus (connection);

    final String SQL_QUERY = "select distinct  M.TestID, grade, RequireEnrollment, M.testkey as _fk_AdminSubject, EnrolledSubject "
        + " from ${ItemBankDB}.tblsetofadminsubjects S, ${ConfigDB}.client_testmode M, ${ConfigDB}.client_testgrades G, ${ConfigDB}.client_testwindow W, ${ConfigDB}.client_testproperties P "
        + " where M.clientname = ${clientname} and (${testkey} is null or M.testkey = ${testkey}) and M.testkey = S._Key and (M.sessionType = -1 or M.sessionType = ${sessionType}) "
        + "    and M.clientname = G.clientname and M.TestID = G.TestID  and W.clientname = ${clientname} and W.TestID = M.testID and P.clientname = ${clientname} and P.TestID = M.testID "
        + "    and P.IsSelectable = 1 and ${today} between W.startDate and W.endDate order by M.TestID, grade";

    SqlParametersMaps parameters = new SqlParametersMaps ();
    parameters.put ("testkey", testkey);
    parameters.put ("clientname", clientname);
    parameters.put ("today", today);
    parameters.put ("sessionType", sessionType);

    return executeStatement (connection, fixDataBaseNames (SQL_QUERY), parameters, false).getResultSets ().next ();

  }
  /**
   *     This is the Java version of T_ValidateAccess stored procedure. This method is not used now. It is only needed when we want to migrate the Mysql stored procedure T_ValidateAccess to java.
   * @param connection
   * @param oppkey
   * @param session
   * @param browserId
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_ValidateAccess_SP_Java (SQLConnection connection, UUID oppkey, UUID session, UUID browserId) throws ReturnStatusException {
    Date starttime = _dateUtil.getDateWRetStatus (connection);
    _Ref<String> message = new _Ref<String> ();
    SingleDataResultSet result = null;

    _ValidateTesteeAccessProc_SP (connection, oppkey, session, browserId, true, message);
    if (message.get () == null) {

      final String SQL_QUERY = "select 'success' as status, cast(null AS CHAR) as reason, status as oppStatus, comment from testopportunity where _Key = ${oppkey}";
      SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("oppkey", oppkey);

      result = executeStatement (connection, SQL_QUERY, parameters, false).getResultSets ().next ();
    } else {

      result = _commonDll._ReturnError_SP (connection, null, "T_ValidateAccess", message.get (), null, oppkey, "_ValidateTesteeAccess", "failed");
    }
    _commonDll._LogDBLatency_SP (connection, "T_ValidateAccess", starttime, null, true, null, null, session, null, null);
    return result;
  }
  
  
  public SingleDataResultSet T_ValidateAccess_SP (SQLConnection connection, UUID oppkey, UUID session, UUID browserId) throws ReturnStatusException {
    final String SQL_QUERY = "call t_validateaccess(${oppkey}, ${session}, ${browserID})";
    SqlParametersMaps params = new SqlParametersMaps ();
    params.put ("oppkey", oppkey);
    params.put ("session", session);
    params.put ("browserID", browserId);
    
    SingleDataResultSet resultSet = executeStatement (connection, SQL_QUERY, params, false).getResultSets ().next ();
    return resultSet;
  }

  public void T_RecordToolsUsed_SP (SQLConnection connection, UUID oppkey, String toolsused) throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);

    DataBaseTable tbl = getDataBaseTable ("xyz").addColumn ("page", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("tooltype", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("toolcode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100);

    final String toolsusedFinal = toolsused;
    executeMethodAndInsertIntoTemporaryTable (connection, new AbstractDataResultExecutor ()
    {
      @Override
      public SingleDataResultSet execute (SQLConnection connection) throws ReturnStatusException {

        List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
        try {
          InputStream is = new ByteArrayInputStream (toolsusedFinal.getBytes ("UTF-8"));
          DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance ();
          DocumentBuilder loader = factory.newDocumentBuilder ();

          Document doc = loader.parse (is);
          doc.getDocumentElement ().normalize ();

          NodeList nodeList = doc.getElementsByTagName ("tool");
          for (int i = 0; i < nodeList.getLength (); i++) {
            Node currentNode = nodeList.item (i);
            if (currentNode.getNodeType () == Node.ELEMENT_NODE) {
              CaseInsensitiveMap<Object> record = new CaseInsensitiveMap<Object> ();
              NamedNodeMap nodeMap = currentNode.getAttributes ();

              record.put ("page", nodeMap.getNamedItem ("page").getNodeValue ());
              record.put ("tooltype", nodeMap.getNamedItem ("tooltype").getNodeValue ());
              record.put ("toolcode", nodeMap.getNamedItem ("toolcode").getNodeValue ());

              resultList.add (record);
            }
          }
        } catch (Exception e) {
          // UnsupportedEncodingException, FactoryConfigurationError,
          // ParserConfigurationException, SAXException, IOException,
          // IllegalArgumentException,DOMException
          // TODO throw ReturnStatusexception?
          _logger.error (e.getMessage ());
          throw new ReturnStatusException (e);
        }
        SingleDataResultSet rs = new SingleDataResultSet ();
        rs.addColumn ("page", SQL_TYPE_To_JAVA_TYPE.INT);
        rs.addColumn ("tooltype", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
        rs.addColumn ("toolcode", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
        rs.addRecords (resultList);
        return rs;
      }
    }, tbl, true);

    final String SQL_INSERT1 = "insert into testopptoolsused (_fk_TestOpportunity, itempage, tooltype, toolcode, groupID) "
        + " select distinct ${oppkey}, X.page,  X.tooltype, X.toolcode, R.groupID from ${tmpTblName} X, testeeresponse R "
        + "  where R._fk_TestOpportunity = ${oppkey} and R.page = X.page";
    Map<String, String> unquotedParms1 = new HashMap<String, String> ();
    unquotedParms1.put ("tmpTblName", tbl.getTableName ());
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);

    executeStatement (connection, fixDataBaseNames (SQL_INSERT1, unquotedParms1), parms1, false).getUpdateCount ();
    connection.dropTemporaryTable (tbl);
    _commonDll._LogDBLatency_SP (connection, "", starttime, null, true, null, oppkey);

  }

  public void T_RecordComment_SP (SQLConnection connection, UUID sessionKey, Long testee, String comment) throws ReturnStatusException {

    T_RecordComment_SP (connection, sessionKey, testee, comment, "TESTITEM", null, null);
  }

  public void T_RecordComment_SP (SQLConnection connection, UUID sessionKey, Long testee, String comment, String context, UUID oppkey) throws ReturnStatusException {

    T_RecordComment_SP (connection, sessionKey, testee, comment, context, oppkey, null);
  }

  public void T_RecordComment_SP (SQLConnection connection, UUID sessionKey, Long testee, String comment, String context, UUID oppkey, Integer itemposition) throws ReturnStatusException {

    String clientname = null;
    final String SQL_QUERY1 = "select clientname from session where _Key = ${sessionkey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("sessionkey", sessionKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();

    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null)
      clientname = record.<String> get ("clientname");

    if (context == null && itemposition != null)
      context = "TESTITEM";

    if (clientname != null) {
      String groupId = null;
      if (oppkey != null && itemposition != null) {
        final String SQL_QUERY2 = "select groupID from testeeresponse where _fk_TestOpportunity = ${oppkey} and position = ${itemposition}";
        SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("oppkey", oppkey).put ("itemposition", itemposition);
        result = executeStatement (connection, SQL_QUERY2, parms2, false).getResultSets ().next ();
        record = (result.getCount () > 0 ? result.getRecords ().next () : null);
        if (record != null) {
          groupId = record.<String> get ("groupId");
        }
      }
      final String SQL_INSERT3 = "insert into testeecomment (_fk_Session, clientname, _efk_Testee, comment, context, _fk_TestOpportunity, itemPosition, groupID, _date) "
          + " values (${sessionKey}, ${clientname}, ${testee}, ${comment}, ${context}, ${oppkey}, ${itemposition}, ${groupID}, now(3))";
      SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("oppkey", oppkey).put ("itemposition", itemposition).put ("sessionKey", sessionKey).put ("clientname", clientname).
          put ("testee", testee).put ("comment", comment).put ("context", context).put ("groupID", groupId);
      executeStatement (connection, SQL_INSERT3, parms3, false);

    }
  }

  /**
   * This method returns null in case of success
   * 
   * @param connection
   * @param oppkey
   * @param itemId
   * @param position
   * @param dateCreated
   * @param session
   * @param browser
   * @return SingleDataResultSet
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_RemoveResponse_SP (SQLConnection connection, UUID oppkey, String itemId, Integer position, String dateCreated, UUID session, UUID browser) throws ReturnStatusException {

    _Ref<String> validatedRef = new _Ref<String> ();
    String clientname = null;
    Integer sessiontype = null;
    String msg = null;

    _ValidateTesteeAccessProc_SP (connection, oppkey, session, browser, true, validatedRef);

    final String SQL_QUERY1 = "select clientname, sessionType from session where _Key = ${session}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      clientname = record.<String> get ("clientname");
      sessiontype = record.<Integer> get ("sessiontype");
    }

    if (validatedRef.get () != null) {
      return _commonDll._ReturnError_SP (connection, clientname, "T_RemoveResponse", validatedRef.get (), null, oppkey, "_validateTesteeAccessProc");
    }

    if (DbComparator.notEqual (sessiontype, 1)) {
      return _commonDll._ReturnError_SP (connection, clientname, "T_RemoveResponse", "Invalid operation for this session type", null, oppkey, null);
    }

    String itemKey = null;
    Date genDate = null;

    final String SQL_QUERY2 = "select _efk_ItemKey, dateGenerated from testeeresponse where _fk_TestOpportunity = ${oppkey} and Position = ${position}";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("oppkey", oppkey).put ("position", position);

    result = executeStatement (connection, SQL_QUERY2, parms2, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      itemKey = record.<String> get ("_efk_ItemKey");
      genDate = record.<Date> get ("dateGenerated");
    }

    // TODO Elena: confirm that java passes dateCreated string in yyyy-MM-dd
    // HH:mm:ss.SSS format, in the same manner as .Net
    Date dtC = null;
    if (dateCreated != null) {
      try {
        dtC = (new SimpleDateFormat (AbstractDateUtilDll.DB_DATETIME_FORMAT_MS_PRECISION)).parse (dateCreated);
      } catch (ParseException pe) {
        _logger.error (String.format ("dateCreated format problem: %s, expected format: %s", pe.getMessage (), AbstractDateUtilDll.DB_DATETIME_FORMAT_MS_PRECISION));
        throw new ReturnStatusException (pe);
      }
    }
    if (itemKey == null || itemKey.equalsIgnoreCase (itemId) == false) {
      msg = String.format ("The item does not exist at this position in this test opportunity: Position=%d; Item=%s", position, itemId);
    } else if (DbComparator.notEqual (dtC, genDate)) {
      msg = String.format ("Item security codes do not match: Position=%d;  Item =%s; Date=%s", position, itemId, dateCreated);
    }
    if (msg != null) {
      _commonDll._LogDBError_SP (connection, "T_RemoveResponse", msg, null, null, null, oppkey, null, session);
      return _commonDll._ReturnError_SP (connection, clientname, "T_RemoveResponse", "Response update not legal", null, oppkey, null);
    }
    final String SQL_UPDATE3 = "UPDATE testeeresponse SET DateSubmitted=null, DateFirstResponse=null, "
        + "Response=null, Mark=0, Score=-1, Hostname=null, numUpdates=0, dateSystemAltered=null, IsInactive=0, "
        + "dateInactivated=null, _fk_AdminEvent=null, IsSelected=0, ResponseSequence=0, ResponseLength=0, "
        + "_fk_Browser=null, IsValid=0, ScoreLatency=0, scorestatus=null, scoringDate=null, scoredDate=null, scoreMark=null,"
        + "scoreRationale=null, scoreAttempts=0, _fk_responseSession=null, dateLastVisited = null, visitCount = 0 "
        + " where _fk_TestOpportunity = ${oppkey} and position = ${position}";
    SqlParametersMaps parms3 = parms2;
    executeStatement (connection, SQL_UPDATE3, parms3, false).getUpdateCount ();

    final String SQL_UPDATE4 = "update testopportunity set numresponses = "
        + "(select count(*) from testeeresponse where _fk_TestOpportunity = ${oppkey} and dateSubmitted is not null) where _key = ${oppkey}";
    SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("oppkey", oppkey);
    executeStatement (connection, SQL_UPDATE4, parms4, false).getUpdateCount ();

    // String sessionDB = getAppSettings ().get ("TDSSessionDBName");
    String sessionDB = getTdsSettings ().getTDSSessionDBName ();
    final String SQL_INSERT5 = "insert into ${ArchiveDB}.opportunityaudit (_fk_TestOpportunity, AccessType, Comment, dateaccessed, hostname, dbname) "
        + " values ( ${oppkey}, 'Remove Response', ${comment}, now(3), ${localhost}, ${dbname})";
    SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("oppkey", oppkey)
        .put ("comment", String.format ("Removed item response at position %d", position)).put ("localhost", _commonDll.getLocalhostName ()).put ("dbname", sessionDB);
    executeStatement (connection, fixDataBaseNames (SQL_INSERT5), parms5, false).getUpdateCount ();

    return null;
  }

  /**
   * Original stored proc has 'mark'parameter as integer, however lark column in
   * testeeresponse table has 'bit'type, we changed 'mark' parameter type to
   * Boolean to clarify its valid values.
   * 
   * @param connection
   * @param oppkey
   * @param session
   * @param browserId
   * @param position
   * @param mark
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_SetItemMark_SP (SQLConnection connection, UUID oppkey, UUID session, UUID browserId, Integer position, Boolean mark) throws ReturnStatusException {

    _Ref<String> validatedRef = new _Ref<String> ();

    _ValidateTesteeAccessProc_SP (connection, oppkey, session, browserId, true, validatedRef);
    if (validatedRef.get () != null) {
      return _commonDll._ReturnError_SP (connection, null, "T_SetItemMark", validatedRef.get (), null, oppkey, "_ValidateTesteeAccess", "denied");
    }

    final String SQL_UPDATE1 = "update testeeresponse set Mark = ${mark} where _fk_TestOpportunity = ${oppkey} and position = ${position} and _efk_ITSItem is not null";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("mark", mark).put ("oppkey", oppkey).put ("position", position);
    executeStatement (connection, SQL_UPDATE1, parms1, false).getUpdateCount ();

    SingleDataResultSet rs = _commonDll.ReturnStatusReason ("success", null);
    return rs;
  }

  /**
   * This method will always return non-null SingleDataResultSet because we call
   * SetOpportunityStatus with suppressReport parameter set to false.
   * 
   * @param connection
   * @param oppkey
   * @param status
   * @param sessionKey
   * @param browserKey
   * @param comment
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_SetOpportunityStatus_SP (SQLConnection connection, UUID oppkey, String status, UUID sessionKey, UUID browserKey, String comment) throws ReturnStatusException {

    _Ref<String> validatedRef = new _Ref<String> ();
    _ValidateTesteeAccessProc_SP (connection, oppkey, sessionKey, browserKey, true, validatedRef);

    if (validatedRef.get () != null) {

      _commonDll._LogDBError_SP (connection, "T_SetOpportunityStatus", validatedRef.get (), null, null, null, oppkey);
      return _commonDll._ReturnError_SP (connection, null, "T_SetOpportunityStatus", validatedRef.get (), null, oppkey, "_ValidateTesteeAccess", "failed");
    }

    SingleDataResultSet result = _commonDll.SetOpportunityStatus_SP (connection, oppkey, status, false, "testee", comment);
    return result;
  }

  /**
   * @param connection
   * @param oppKey
   * @param sessionKey
   * @param browserKey
   * @param segment
   * @param entry
   * @param exit
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_WaitForSegment_SP (SQLConnection connection, UUID oppKey, UUID sessionKey, UUID browserKey, Integer segment, Boolean entry, Boolean exit) throws ReturnStatusException {

    String status = null;
    String action = null;
    String msg = null;
    String argstring = null;
    _Ref<String> error = new _Ref<> ();

    _ValidateTesteeAccessProc_SP (connection, oppKey, sessionKey, browserKey, true, error);
    if (error.get () != null) {
      return _commonDll._ReturnError_SP (connection, null, "T_WaitForSegment", error.get (), null, oppKey, "_ValidateTesteeAccess", "denied");
    }
    final String SQL_QUERY1 = "select status from testopportunity where _Key = ${oppkey};";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("oppkey", oppKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      status = record.<String> get ("status");
    }
    if (DbComparator.isEqual (entry, true))
      action = "segmentEntry";
    else if (DbComparator.isEqual (exit, true))
      action = "segmentExit";
    else
      action = "unknown";

    msg = _commonDll._CanChangeOppStatus_FN (connection, status, action);
    if (DbComparator.notEqual (msg, null)) {
      argstring = String.format ("%s,%s", status, action);
      return _commonDll._ReturnError_SP (connection, null, "T_WaitForSegment", msg, argstring, oppKey, null);
    }
    final String SQL_UPDATE = "update testopportunity set waitingForSegment = ${segment} where _Key = ${oppkey};";
    SqlParametersMaps parms2 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("segment", segment);
    executeStatement (connection, SQL_UPDATE, parms2, false).getUpdateCount ();

    // TODO Elena testing
    // long st = System.currentTimeMillis();
    SingleDataResultSet rs = _commonDll.SetOpportunityStatus_SP (connection, oppKey, action);
    // long diff = System.currentTimeMillis() - st;

    return rs;
  }

  /**
   * This is the Java version of T_updatescoredresponse stored procedure. This method is not used now. It is only needed when we want to migrate the Mysql stored procedure T_updatescoredresponse to java.
   * @param connection
   * @param oppKey
   * @param session
   * @param browserId
   * @param itemId
   * @param page
   * @param position
   * @param dateCreated
   * @param responseSequence
   * @param score
   * @param response
   * @param isSelected
   * @param isValid
   * @param scoreLatency
   * @param scoreStatus
   * @param scoreRationale
   * @throws ReturnStatusException
   */
  // The only difference between T_UpdateScoredResponse and
  // T_UpdateScoredResponse2
  // is an additional scoreDimensions parameter in the latter one.
  public SingleDataResultSet T_UpdateScoredResponse_SP (SQLConnection connection, UUID oppKey, UUID session, UUID browserId, String itemId, Integer page, Integer position, String dateCreated,
      Integer responseSequence, Integer score, String response, Boolean isSelected, Boolean isValid, Integer scoreLatency, String scoreStatus, String scoreRationale) throws ReturnStatusException {

	String scoreDimentions = buildScoreInfoNode(score, "overall", scoreStatus);
	  
    return T_UpdateScoredResponse_common (connection, oppKey, session, browserId, itemId,
        page, position, dateCreated, responseSequence, score, response, isSelected,
        isValid, scoreLatency, scoreStatus, scoreRationale, scoreDimentions, 1);
  }
  
  
  /**
   * 
   * @param connection
   * @param oppKey
   * @param session
   * @param browserId
   * @param itemId
   * @param page
   * @param position
   * @param dateCreated
   * @param responseSequence
   * @param score
   * @param response
   * @param isSelected
   * @param isValid
   * @param scoreLatency
   * @param scoreStatus
   * @param scoreRationale
   * @throws ReturnStatusException
   */
  // This function is created to call mysql Stored procedure T_UpdateScoredResponse.
  public SingleDataResultSet T_UpdateScoredResponse_SP_Mysql (SQLConnection connection, UUID oppKey, UUID session, UUID browserId, String itemId, Integer page, Integer position, String dateCreated,
      Integer responseSequence, Integer score, String response, Boolean isSelected, Boolean isValid, Integer scoreLatency, String scoreStatus, String scoreRationale) throws ReturnStatusException {

    final String SQL_QUERY = "call T_UpdateScoredResponse(${oppkey}, ${session}, ${browserID}, ${itemID},${page}, ${position},${dateCreated}, ${responseSequence},${score}, ${response}, ${isSelected},${isValid},${scoreLatency},${scorestatus},${scoreRationale})";
    SqlParametersMaps params = new SqlParametersMaps ();
    params.put ("oppkey", oppKey);
    params.put ("session", session);
    params.put ("browserID", browserId);
    params.put ("itemID", itemId);
    params.put ("page", page);
    params.put ("position", position);
    params.put ("dateCreated", dateCreated);
    params.put ("responseSequence", responseSequence);
    params.put ("score", score);
    params.put ("response", response);
    params.put ("isSelected", isSelected);
    params.put("isValid",isValid);
    params.put ("scoreLatency", scoreLatency);
    params.put ("scorestatus", scoreStatus);
    params.put ("scoreRationale", scoreRationale);

    Map<String, String> unquotedParms = new HashMap<String, String> ();
//    unquotedParms.put ("rts", rts);
    

    SingleDataResultSet resultSet = executeStatement (connection, fixDataBaseNames (SQL_QUERY, unquotedParms), params, false).getResultSets ().next ();
    return resultSet;
  }
  

  public SingleDataResultSet T_UpdateScoredResponse2_SP (SQLConnection connection, UUID oppKey, UUID session,
      UUID browserId, String itemId,
      Integer page, Integer position, String dateCreated,
      Integer responseSequence, Integer score) throws ReturnStatusException {

    return T_UpdateScoredResponse2_SP (connection, oppKey, session, browserId, itemId,
        page, position, dateCreated,
        responseSequence, score, null, true, false, 0, null, null, null);
  }

  // The only difference with T_UpdateScoredResponse_SP is presense of
  // scoreDimensions
  // parameter used to update TesteeResponse tbl
  public SingleDataResultSet T_UpdateScoredResponse2_SP (SQLConnection connection, UUID oppKey, UUID session,
      UUID browserId, String itemId,
      Integer page, Integer position, String dateCreated,
      Integer responseSequence, Integer score, String response, Boolean isSelected,
      Boolean isValid, Integer scoreLatency, String scoreStatus, String scoreRationale, String scoreDimensions)
      throws ReturnStatusException {
    return T_UpdateScoredResponse_common (connection, oppKey, session, browserId, itemId,
        page, position, dateCreated, responseSequence, score, response, isSelected,
        isValid, scoreLatency, scoreStatus, scoreRationale, scoreDimensions, 2);
  }

  private SingleDataResultSet T_UpdateScoredResponse_common (SQLConnection connection, UUID oppKey, UUID session,
      UUID browserId, String itemId,
      Integer page, Integer position, String dateCreated,
      Integer responseSequence, Integer score, String response, Boolean isSelected,
      Boolean isValid, Integer scoreLatency, String scoreStatus, String scoreRationale,
      String scoreDimensions, int methodVersion)
      throws ReturnStatusException {

    Date now = _dateUtil.getDateWRetStatus (connection);
    Integer opprestart = null;
    String status = null;
    Integer lastSequence = null;
    String itmkey = null;
    Date genDate = null;
    String msg = null;
    Boolean isinactive = null;
    String clientName = null;
    UUID scoremark = null;
    Date scoringDate = null;
    Date scoredDate = null;
    Integer thescore = 0;
    Integer audit = null;
    String environment = null;
    Boolean abortSim = false; // set @abortSim = 0;
    Integer responseCount = null;

    _Ref<String> error = new _Ref<> ();
    _ValidateTesteeAccessProc_SP (connection, oppKey, session, browserId, true, error);
    if (error.get () != null) {
      return _commonDll._ReturnError_SP (connection, clientName, "_ValidateTesteeAccessProc", error.get (), null, oppKey, "_ValidateTesteeAccess", "denied");
    }

    final String SQL_QUERY1 = "select Restart as opprestart, status, clientname from testopportunity where _Key = ${oppkey};";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("oppkey", oppKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      opprestart = record.<Integer> get ("opprestart");
      status = record.<String> get ("status");
      clientName = record.<String> get ("clientname");
    }
    if (DbComparator.notEqual (status, "started") && DbComparator.notEqual (status, "review")) {
      return _commonDll._ReturnError_SP (connection, clientName, "T_UpdateScoredResponse",
          "Your test opportunity has been interrupted. Please check with your Test Administrator to resume your test.",
          null, oppKey, "T_UpdateScoredResponse", "denied");
    }

    final String SQL_QUERY2 = "select environment from _externs where clientname = ${clientname};";
    SqlParametersMaps parms2 = new SqlParametersMaps ().put ("clientname", clientName);
    result = executeStatement (connection, SQL_QUERY2, parms2, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      environment = record.<String> get ("environment");
    }
    if (DbComparator.isEqual (environment, "SIMULATION")) {
      final String SQL_QUERY3 = "select sim_abort as abortSim from session where _Key = ${session};";
      SqlParametersMaps parms3 = new SqlParametersMaps ().put ("session", session);
      result = executeStatement (connection, SQL_QUERY3, parms3, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        abortSim = record.<Boolean> get ("abortSim");
      }
    }
    audit = AuditResponses_FN (connection, clientName);
    final String SQL_QUERY4 = "select responseSequence as lastSequence, _efk_ItemKey as itmkey, dateGenerated as genDate, score as thescore, isInactive as isinactive, " +
        " scoreMark as scoremark, scoringDate, scoredDate from testeeresponse where _fk_TestOpportunity = ${oppkey} and Position = ${position};";
    SqlParametersMaps parms4 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("position", position);
    result = executeStatement (connection, SQL_QUERY4, parms4, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      lastSequence = record.<Integer> get ("lastSequence");
      itmkey = record.<String> get ("itmkey");
      genDate = record.<Date> get ("genDate");
      thescore = record.<Integer> get ("thescore");
      isinactive = record.<Boolean> get ("isinactive");
      scoremark = record.<UUID> get ("scoremark");
      scoringDate = record.<Date> get ("scoringDate");
      scoredDate = record.<Date> get ("scoredDate");
    }

    Date dt = null;
    try {
      if (dateCreated != null) {
        String[] tokens = dateCreated.split ("\\.");
        if (tokens.length == 2 && tokens[1].length () > 3) {
          dateCreated = String.format ("%s.%s", tokens[0], tokens[1].substring (0, 3));
        }
        dt = DateUtils.parseDate (dateCreated,
            new String[] { "yyyy-MM-dd HH:mm:ss.SSS",
                "yyyy-MM-dd HH:mm:ss" });
      }
    } catch (Exception e) {
      _logger.error (String.format ("Unexpected dateCreated format: %s, please update StudentDLL.T_UpdateScoredResponse to parse this format!",
          dateCreated));
    }

    // if (itmkey == null || DbComparator.notEqual (itmkey, itemId)) {
    if (itmkey == null || checkItem (connection, itmkey, itemId) == false) {

      msg = String.format ("The item does not exist at this position in this test opportunity: Position = %d; Item = %s; testeeresponse._efk_ItemKey found is %s", 
          position, itemId, (itmkey == null ? "null" : itmkey));

      // } else if (DbComparator.notEqual (genDate, dt)) {
    } else if (genDate != null && dt != null && genDate.getTime () != dt.getTime ()) {

      msg = String.format ("Item security codes do not match:  Position = %d; Item = %s; Date = %s ", position, itemId, dateCreated);
      // ("Item security codes do not match:  Position = %d; Item = %s; Date = %s ",
      // position, itemId, dateCreated);

    } else if (DbComparator.greaterThan (lastSequence, responseSequence)) {

      msg = String.format ("Responses out of sequence: Position = %d;  Stored sequence = %d;  New sequence = %d", position, lastSequence, responseSequence);
    }

    if (msg != null) {
      _commonDll._LogDBError_SP (connection, "T_UpdateScoredResponse", msg, null, null, null, oppKey, clientName, session);
      return _commonDll._ReturnError_SP (connection, clientName, "T_UpdateScoredResponse", "Response update failed", null, oppKey, null);
    }

    if (response != null && (score == null || DbComparator.lessThan (score, 0))) {
      // new response, score to be determined
      thescore = -1; // not scored
      scoremark = UUID.randomUUID (); // app will need this token to update the
                                      // record with the score for THIS response
                                      // once it is determined asynchronously
      scoringDate = now; // start the scoring clock running
      scoredDate = null; // no score, so the date scored is undetermined
    } else {
      if (response != null && DbComparator.greaterOrEqual (scoreLatency, 0)) {
        scoremark = null; // any previous scoremark is now obsolete
        thescore = score; // use the provided score
        scoringDate = now; // 'instantaneous' time lag
        scoredDate = now;
      }
    }
    if (response == null) {
      final String SQL_UPDATE = "Update testeeresponse  set IsSelected = ${isSelected}, IsValid = ${isValid}, _fk_Browser = ${browserID}, responseSequence = ${responseSequence} " +
          " where _fk_TestOpportunity = ${oppkey} and position = ${position};";
      SqlParametersMaps parms5 = new SqlParametersMaps ();
      parms5.put ("oppkey", oppKey);
      parms5.put ("position", position);
      parms5.put ("isSelected", isSelected);
      parms5.put ("isValid", isValid);
      parms5.put ("browserID", browserId);
      parms5.put ("responseSequence", responseSequence);
      executeStatement (connection, SQL_UPDATE, parms5, false).getUpdateCount ();
    } else {
      String tmp = null;
//      if (methodVersion == 1)
//        tmp = "Update testeeresponse set IsSelected = ${isSelected}, IsValid = ${isValid}, _fk_ResponseSession = ${session}, _fk_Browser = ${browserID}, " +
//            " scoreMark = ${scoremark}, NumUpdates =  NumUpdates + 1, DateSubmitted = ${now}, Response = ${response}, responseSequence = ${responseSequence}, responseLength = length(${response})," +
//            " Score = ${thescore}, DateFirstResponse = COALESCE(DateFirstResponse, ${now}), ScoreLatency = ScoreLatency + ${scoreLatency}, scorestatus = ${scorestatus}, scoringDate = ${scoringDate},"
//            +
//            " scoreRationale = ${scoreRationale}, scoredDate = ${scoredDate} "
//            + " where _fk_TestOpportunity = ${oppkey} and position = ${position} and responseSequence <= ${responseSequence};";
//      else
        tmp = "Update testeeresponse set IsSelected = ${isSelected}, IsValid = ${isValid}, _fk_ResponseSession = ${session}, _fk_Browser = ${browserID}, " +
            " scoreMark = ${scoremark}, NumUpdates =  NumUpdates + 1, DateSubmitted = ${now}, Response = ${response}, responseSequence = ${responseSequence}, responseLength = length(${response})," +
            " Score = ${thescore}, DateFirstResponse = COALESCE(DateFirstResponse, ${now}), ScoreLatency = ScoreLatency + ${scoreLatency}, scorestatus = ${scorestatus}, scoringDate = ${scoringDate},"
            +
            " scoreRationale = ${scoreRationale}, scoredDate = ${scoredDate}, scoreDimensions = ${scoreDimensions} "
            + " where _fk_TestOpportunity = ${oppkey} and position = ${position} and responseSequence <= ${responseSequence};";

      final String SQL_UPDATE1 = tmp;
      SqlParametersMaps parms6 = new SqlParametersMaps ();
      parms6.put ("isSelected", isSelected);
      parms6.put ("isValid", isValid);
      parms6.put ("session", session);
      parms6.put ("browserID", browserId);
      parms6.put ("scoremark", scoremark);
      parms6.put ("now", now);
      parms6.put ("response", response);
      parms6.put ("responseSequence", responseSequence);
      parms6.put ("thescore", thescore);
      parms6.put ("scoreLatency", scoreLatency);
      parms6.put ("scorestatus", scoreStatus);
      parms6.put ("scoringDate", scoringDate);
      parms6.put ("scoreRationale", scoreRationale);
      parms6.put ("scoredDate", scoredDate);
      parms6.put ("oppkey", oppKey);
      parms6.put ("position", position).put ("scoreDimensions", scoreDimensions);
      executeStatement (connection, SQL_UPDATE1, parms6, false).getUpdateCount ();

      if (DbComparator.notEqual (audit, 0)) {
        final String SQL_INSERT = "INSERT INTO  testeeresponseaudit(_fk_TestOpportunity, position, scoremark, sequence, response, sessionKey, browserKey, isSelected, isValid, "
            + " score, scorelatency, scoringDate, scoredDate, _efk_Item, _date) "
            + " select ${oppkey}, ${position},${scoremark}, ${responseSequence}, ${response}, ${session}, ${browserID}, ${isSelected},"
            + " ${isValid}, ${thescore}, ${scoreLatency}, ${scoringDate}, ${scoredDate}, ${itemID}, now(3)";
        SqlParametersMaps parms7 = new SqlParametersMaps ();
        parms7.put ("oppkey", oppKey);
        parms7.put ("position", position);
        parms7.put ("scoremark", scoremark);
        parms7.put ("responseSequence", responseSequence);
        parms7.put ("response", response);
        parms7.put ("session", session);
        parms7.put ("browserID", browserId);
        parms7.put ("isSelected", isSelected);
        parms7.put ("isValid", isValid);
        parms7.put ("thescore", thescore);
        parms7.put ("scoreLatency", scoreLatency);
        parms7.put ("scoringDate", scoringDate);
        parms7.put ("scoredDate", scoredDate);
        parms7.put ("itemID", itemId);
        executeStatement (connection, SQL_INSERT, parms7, false).getUpdateCount ();
      }
    }
    final String SQL_QUERY5 = "select count(*) as responseCount from testeeresponse where _fk_TestOpportunity = ${oppkey} and dateFirstResponse is not null;";
    SqlParametersMaps parms8 = parms1;
    result = executeStatement (connection, SQL_QUERY5, parms8, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      responseCount = record.<Long> get ("responseCount").intValue ();
    }
    final String SQL_UPDATE2 = "update testopportunity set numResponses = ${responseCount} where _Key = ${oppkey};";
    SqlParametersMaps parms9 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("responseCount", responseCount);
    executeStatement (connection, SQL_UPDATE2, parms9, false).getUpdateCount ();

    List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
    CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
    rcd.put ("status", "updated");
    rcd.put ("number", 1);
    rcd.put ("reason", null);
    rcd.put ("scoremark", scoremark);
    rcd.put ("responseCount", responseCount);
    rcd.put ("abortSim", abortSim);
    resultList.add (rcd);

    result = new SingleDataResultSet ();
    result.addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    result.addColumn ("number", SQL_TYPE_To_JAVA_TYPE.INT);
    result.addColumn ("reason", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    result.addColumn ("scoremark", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER);
    result.addColumn ("responseCount", SQL_TYPE_To_JAVA_TYPE.INT);
    result.addColumn ("abortSim", SQL_TYPE_To_JAVA_TYPE.BIT);

    result.addRecords (resultList);

    _commonDll._LogDBLatency_SP (connection, "T_UpdateScoredResponse", now, null, true, position, oppKey, session, clientName, null);

    return result;
  }

  private Boolean checkItem (SQLConnection connection, String itmkey, String itemId) throws ReturnStatusException {
    int i = itemId.indexOf ('-');
    if (i == -1 || i == 0 || i == itemId.length () - 1)
      return false;

    String s_itembank = itemId.substring (0, i);
    String s_item = itemId.substring (i + 1);
    int efk_itembank, efk_item;

    try {
      efk_itembank = Integer.parseInt (s_itembank);
      efk_item = Integer.parseInt (s_item);
    } catch (NumberFormatException ne) {
      return false;
    }
    final String cmd1 = "select _key from ${ItembankDB}.tblitem "
        + " where _key = ${itmkey} and _efk_itembank = ${efk_itembank} and _efk_item = ${efk_item} ";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("itmkey", itmkey).
        put ("efk_itembank", efk_itembank).put ("efk_item", efk_item);
    if (exists (executeStatement (connection, fixDataBaseNames (cmd1), parms1, false))) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * please note that i'm renaming the variables which are in ' '(single quotes)
   * as named parameters (${ }) because in future when we move to MySql
   * database, we are not sure if the syntax will be same as in SQL Server.
   * 
   * @param connection
   * @param clientName
   * @return
   * @throws ReturnStatusException
   */
  public Integer AuditResponses_FN (SQLConnection connection, String clientName) throws ReturnStatusException {
    Integer flag = null;
    final String SQL_QUERY = "select IsOn as flag from  ${ConfigDB}.client_systemflags F, externs E "
        + " where E.ClientName = ${clientname} and F.clientname = ${clientname} "
        + " and E.IsPracticeTest = F.IsPracticeTest and AuditOBject = ${responses}";
    String query = fixDataBaseNames (SQL_QUERY);
    SqlParametersMaps parameters = new SqlParametersMaps ().put ("clientname", clientName).put ("responses", "responses");
    SingleDataResultSet result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      flag = record.<Integer> get ("flag");
    }
    if (DbComparator.isEqual (flag, 0)) {
      return 0;
    } else
      return 1;
  }

  /**
   * @param connection
   * @param testKey
   * @return
   * @throws ReturnStatusException
   */
  public Float GetInitialAbility_FN (SQLConnection connection, String testKey) throws ReturnStatusException {
    Float result = null;
    final String SQL_QUERY = "select StartAbility as result from ${ItemBankDB}.tblsetofadminsubjects where _key = ${testKey}";
    String query = fixDataBaseNames (SQL_QUERY);
    SqlParametersMaps parameters = new SqlParametersMaps ().put ("testKey", testKey);
    SingleDataResultSet res = executeStatement (connection, query, parameters, false).getResultSets ().next ();
    DbResultRecord record = res.getCount () > 0 ? res.getRecords ().next () : null;
    if (record != null) {
      result = record.<Float> get ("result");
    }
    return result;
  }

  /**
   * @param connection
   * @param oppKey
   * @return
   * @throws ReturnStatusException
   */
  public void _GetInitialAbility_SP (SQLConnection connection, UUID oppKey, _Ref<Float> ability) throws ReturnStatusException {

    String testId = null;
    Date maxDate = null;
    String clientName = null;
    String subject = null;
    Long testee = null;
    String test = null;
    Boolean bySubject = false;
    Float slope = null;
    Float intercept = null;

    final String SQL_QUERY1 = "select _efk_Testee as testee, Subject, clientname, _efk_AdminSubject as test ," +
        " _efk_TestID as testID from testopportunity where _Key = ${oppkey};";
    SqlParametersMaps parameters1 = new SqlParametersMaps ().put ("oppkey", oppKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parameters1, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      testee = record.<Long> get ("testee");
      subject = record.<String> get ("subject");
      clientName = record.<String> get ("clientname");
      test = record.<String> get ("test");
      testId = record.<String> get ("testID");
    }
    final String SQL_QUERY2 = "select initialAbilityBySubject as bySubject, abilitySlope as slope, abilityIntercept as intercept from ${ConfigDB}.client_testproperties  where clientname = ${clientname} and TestID = ${testID};";

    String query2 = fixDataBaseNames (SQL_QUERY2);
    SqlParametersMaps parameters2 = new SqlParametersMaps ().put ("clientname", clientName).put ("testID", testId);
    result = executeStatement (connection, query2, parameters2, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      bySubject = record.<Boolean> get ("bySubject");
      slope = record.<Float> get ("slope");
      intercept = record.<Float> get ("intercept");
    }

    if (bySubject == null) {
      bySubject = false;
    }

    final DataBaseTable abilitiesTable = getDataBaseTable ("abilities").addColumn ("oppkey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER).addColumn ("test", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200)
        .addColumn ("opportunity", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("scored", SQL_TYPE_To_JAVA_TYPE.DATETIME).addColumn ("ability", SQL_TYPE_To_JAVA_TYPE.FLOAT);
    connection.createTemporaryTable (abilitiesTable);
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("abilitiesTableName", abilitiesTable.getTableName ());

    final String SQL_QUERY3 = "insert into ${abilitiesTableName} (oppkey, test, opportunity, scored, ability) select OTHEROPP._Key, OTHEROPP._efk_TestID,  OTHEROPP.Opportunity, OTHEROPP.dateScored, "
        + " SCORE.value from testopportunity OTHEROPP, testopportunityscores SCORE"
        + " where clientname = ${clientname}  and OTHEROPP._efk_Testee = ${testee} and OTHEROPP.subject = ${subject} and OTHEROPP.dateDeleted is null"
        + " and OTHEROPP.dateScored is not null and OTHEROPP._Key <> ${oppkey} and OTHEROPP._Key = SCORE._fk_TestOpportunity and SCORE.UseForAbility = 1 and SCORE.value is not null;";
    SqlParametersMaps parameters3 = new SqlParametersMaps ().put ("clientname", clientName).put ("testee", testee).put ("subject", subject).put ("oppkey", oppKey);
    executeStatement (connection, fixDataBaseNames (SQL_QUERY3, unquotedParms), parameters3, false).getUpdateCount ();

    // first, try to find a previous opportunity on this exact same test.
    final String SQL_QUERY4 = "select max(scored) as maxdate from  ${abilitiesTableName} where test = ${test};";
    SqlParametersMaps parameters4 = (new SqlParametersMaps ()).put ("test", test);
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY4, unquotedParms), parameters4, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      maxDate = record.<Date> get ("maxdate");
    }
    if (maxDate != null) {
      final String SQL_QUERY5 = "select  ability from ${abilitiesTableName} where test = ${test} and scored = ${maxdate} limit 1;";
      SqlParametersMaps parameters5 = (new SqlParametersMaps ()).put ("test", test).put ("maxdate", maxDate);
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY5, unquotedParms), parameters5, false).getResultSets ().next ();
      record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        ability.set (record.<Float> get ("ability"));
      }
      connection.dropTemporaryTable (abilitiesTable);
      return;

      // failing that, try for same subject/different test
    } else if (DbComparator.isEqual (bySubject, true)) {
      final String SQL_QUERY6 = "select max(scored) as maxdate from ${abilitiesTableName} where test <> ${test};";
      SqlParametersMaps parameters6 = parameters4;
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY6, unquotedParms), parameters6, false).getResultSets ().next ();
      record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        maxDate = record.<Date> get ("maxdate");
      }
      if (maxDate != null) {
        final String SQL_QUERY7 = "select  ability from ${abilitiesTableName} where test <> ${test} and scored = ${maxdate} limit 1;";
        SqlParametersMaps parameters7 = (new SqlParametersMaps ()).put ("test", test).put ("maxdate", maxDate);
        result = executeStatement (connection, fixDataBaseNames (SQL_QUERY7, unquotedParms), parameters7, false).getResultSets ().next ();
        record = result.getCount () > 0 ? result.getRecords ().next () : null;
        if (record != null) {
          ability.set (record.<Float> get ("ability"));
        }
        connection.dropTemporaryTable (abilitiesTable);
        return;
      }
    }
    // failing that, try to get an initial ability from the previous year
    if (DbComparator.isEqual (bySubject, true)) {
      final String SQL_QUERY8 = "select max(initialAbility) as ability from testeehistory where clientname = ${clientname} and _efk_Testee = ${testee} and Subject = ${subject} and initialAbility is not null;";
      SqlParametersMaps parameters8 = (new SqlParametersMaps ()).put ("clientname", clientName).put ("testee", testee).put ("subject", subject);
      result = executeStatement (connection, SQL_QUERY8, parameters8, false).getResultSets ().next ();
      record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        ability.set (record.<Float> get ("ability"));
      }
      if (ability.get () != null && slope != null && intercept != null) {
        ability.set (ability.get () * slope + intercept);
        connection.dropTemporaryTable (abilitiesTable);
        return;
      }
    }
    connection.dropTemporaryTable (abilitiesTable);
    // else get the default for this test from the itembank
    ability.set (GetInitialAbility_FN (connection, test));
    return;
  }

  // Version which uses batch insert
  public void _CreateResponseSet_SPV1 (SQLConnection connection, UUID testoppKey, Integer maxitems, Integer reset) throws ReturnStatusException {
    Date now = _dateUtil.getDateWRetStatus (connection);

    if (reset == null)
      reset = 0;

    final String SQL_QUERY = "SELECT count(*) as numitems from testeeresponse where _fk_TestOpportunity = ${testoppkey}";
    SqlParametersMaps params = (new SqlParametersMaps ()).put ("testoppkey", testoppKey);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY, params, true).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    Long numitems = null;
    if (record != null) {
      numitems = record.<Long> get ("numitems");
    }
    if (DbComparator.greaterThan (numitems, 0)) {
      if (reset != 0) {
        final String SQL_DELETE = "delete from testeeresponse where _fk_TestOpportunity = ${testoppKey}";
        SqlParametersMaps params1 = params;

        MultiDataResultSet sets = executeStatement (connection, SQL_DELETE, params1, false);
        int numDeleted = sets.getUpdateCount ();
        numitems = 0L;
      } else
        return;
    }

    DataBaseTable tbl = getDataBaseTable ("xxx").addColumn ("position", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (tbl);

    List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();

    for (int idx = 1; idx <= maxitems; idx++)
    {
      CaseInsensitiveMap<Object> dbRec = new CaseInsensitiveMap<Object> ();
      dbRec.put ("position", idx);
      // dbRec.put ("testoppkey", testoppKey); //TODO tmp
      resultList.add (dbRec);
    }
    SingleDataResultSet rs = new SingleDataResultSet ();
    rs.addColumn ("position", SQL_TYPE_To_JAVA_TYPE.INT);
    // rs.addColumn ("testoppkey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER);
    // //TODO temp
    rs.addRecords (resultList);

    final String SQL_INSERT_TEMP = "insert into ${tbl} ( position) values ( ${position})";
    Map<String, String> unqpar = new HashMap<> ();
    unqpar.put ("tbl", tbl.getTableName ());
    insertBatch (connection, fixDataBaseNames (SQL_INSERT_TEMP, unqpar), rs, null);

    // final String SQL_INSERT =
    // "insert into testeeresponse (_fk_TestOpportunity, Position) values (${testoppkey}, ${position})";
    // insertBatch (connection, SQL_INSERT, rs, null);

    final String SQL_INSERT = "insert into testeeresponse (_fk_TestOpportunity, Position) select ${testoppkey}, position from ${tbl}";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("testoppkey", testoppKey);
    executeStatement (connection, fixDataBaseNames (SQL_INSERT, unqpar), parms2, false).getUpdateCount ();

    connection.dropTemporaryTable (tbl);
    _commonDll._LogDBLatency_SP (connection, "_CreateResponseSet", now, null, true, null, testoppKey);
  }

  // Version which uses multi value insert statement
  public void _CreateResponseSet_SP (SQLConnection connection, UUID testoppKey, Integer maxitems, Integer reset) throws ReturnStatusException {
    Date now = _dateUtil.getDateWRetStatus (connection);

    if (reset == null)
      reset = 0;

    final String SQL_QUERY = "SELECT count(*) as numitems from testeeresponse where _fk_TestOpportunity = ${testoppkey}";
    SqlParametersMaps params = (new SqlParametersMaps ()).put ("testoppkey", testoppKey);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY, params, true).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    Long numitems = null;
    if (record != null) {
      numitems = record.<Long> get ("numitems");
    }
    if (DbComparator.greaterThan (numitems, 0)) {
      if (reset != 0) {
        final String SQL_DELETE = "delete from testeeresponse where _fk_TestOpportunity = ${testoppKey}";
        SqlParametersMaps params1 = params;

        MultiDataResultSet sets = executeStatement (connection, SQL_DELETE, params1, false);
        int numDeleted = sets.getUpdateCount ();
        numitems = 0L;
      } else
        return;
    }

    DataBaseTable tbl = getDataBaseTable ("xxx").addColumn ("position", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (tbl);

    StringBuilder sb = new StringBuilder ("insert into ${tbl} (position) values ");
    for (int idx = 1; idx <= maxitems; idx++) {
      sb.append ("(").append (idx).append ("), ");
    }
    sb.setCharAt (sb.length () - 2, ';');
    String query1 = sb.toString ();

    Map<String, String> unqpar = new HashMap<> ();
    unqpar.put ("tbl", tbl.getTableName ());
    executeStatement (connection, fixDataBaseNames (query1, unqpar), null, false).getUpdateCount ();

    final String SQL_INSERT = "insert into testeeresponse (_fk_TestOpportunity, Position) select ${testoppkey}, position from ${tbl}";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("testoppkey", testoppKey);
    executeStatement (connection, fixDataBaseNames (SQL_INSERT, unqpar), parms2, false).getUpdateCount ();

    connection.dropTemporaryTable (tbl);
    _commonDll._LogDBLatency_SP (connection, "_CreateResponseSet", now, null, true, null, testoppKey);
  }

  /**
   * @param connection
   * @param oppKey
   * @param segmentKey
   * @return
   * @throws ReturnStatusException
   */
  // original port
  public String _AA_ItempoolString_FN (SQLConnection connection, UUID oppKey, String segmentKey) throws ReturnStatusException {

    String itemString = null;
    Date starttime = _dateUtil.getDateWRetStatus (connection);
    String testId = null;
    String clientName = null;
    UUID session = null;
    String language = null;
    String itemKey = null;
    Integer count = null;

    final String SQL_QUERY1 = " select _efk_TestID as testID , clientname, _fk_Session as session from testopportunity where _key = ${oppkey};";
    SqlParametersMaps params1 = (new SqlParametersMaps ()).put ("oppkey", oppKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, params1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      testId = record.<String> get ("testID");
      clientName = record.<String> get ("clientname");
      session = record.<UUID> get ("session");
    }
    final String SQL_QUERY2 = " select AccCode as language from testeeaccommodations where _fk_TestOPportunity = ${oppkey} and AccType = ${Language};";
    SqlParametersMaps params2 = (new SqlParametersMaps ()).put ("oppkey", oppKey).put ("Language", "Language");
    result = executeStatement (connection, SQL_QUERY2, params2, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      language = record.<String> get ("language");
    }
    final String SQL_QUERY3 = " select distinct I._fk_ITem as itemKey from ${ItemBankDB}.tblsetofadminitems I, ${ConfigDB}.client_test_itemconstraint C1, " +
        " testeeaccommodations A1, ${ItemBankDB}.tblitemprops P1 where I._fk_AdminSUbject = ${segmentKey} and C1.Clientname = ${clientname} and C1.testID = ${testID} and" +
        " C1.item_in = 1 and A1._fk_TestOpportunity = ${oppkey} and A1.AccType = C1.ToolType and A1.AccCode = C1.ToolValue and P1._fk_AdminSubject = ${segmentKey} " +
        " and P1._fk_Item  = I._fk_Item and P1.Propname = C1.propname and P1.Propvalue = C1.Propvalue and P1.isActive = 1" +
        " and not exists " +
        " (select * from ${ConfigDB}.client_test_itemconstraint C2, testeeaccommodations A2, ${ItemBankDB}.tblitemprops P2 where A2._fk_TestOpportunity = ${oppkey} " +
        " and C2.Clientname = ${clientname} and C2.testID = ${testID} and C2.item_in = 0 and A2.AccType = C2.ToolType and A2.AccCode = C2.ToolValue" +
        " and P2._fk_AdminSubject = ${segmentKey} and P2._fk_Item  = I._fk_Item and P2.Propname = C2.propname and P2.Propvalue = C2.Propvalue and P2.isActive = 1) " +
        " group by I._fk_ITem having count(*) = (select count(*) from ${ConfigDB}.client_test_itemconstraint C1, testeeaccommodations A1 where C1.Clientname = ${clientname}" +
        " and C1.testID = ${testID} and C1.item_in = 1 and A1._fk_TestOpportunity = ${oppkey} and A1.AccType = C1.ToolType and A1.AccCode = C1.ToolValue)";
    String query = fixDataBaseNames (SQL_QUERY3);
    SqlParametersMaps params3 = (new SqlParametersMaps ()).put ("oppkey", oppKey).put ("segmentKey", segmentKey).put ("clientname", clientName).put ("testID", testId);
    result = executeStatement (connection, query, params3, true).getResultSets ().next ();
    Iterator<DbResultRecord> records = result.getRecords ();
    while (records.hasNext ()) {
      record = records.next ();
      if (itemString == null)
        itemString = record.<String> get ("itemKey");
      else
        itemString = String.format ("%s,%s", itemString, record.<String> get ("itemKey"));
    }
    return itemString;
  }

  // attempt to optimize; used by _ComputeSegmentPool
  public SingleDataResultSet _AA_ItempoolString_FNOptimized (SQLConnection connection, UUID oppKey, String segmentKey) throws ReturnStatusException {

    // Date starttime = _dateUtil.getDateWRetStatus (connection);
    String testId = null;
    String clientName = null;
    // UUID session = null;
    // String language = null;

    final String SQL_QUERY1 = " select _efk_TestID as testID , clientname from testopportunity where _key = ${oppkey};";
    SqlParametersMaps params1 = (new SqlParametersMaps ()).put ("oppkey", oppKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, params1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      testId = record.<String> get ("testID");
      clientName = record.<String> get ("clientname");
      // session = record.<UUID> get ("session");
    }
    // final String SQL_QUERY2 =
    // " select AccCode as language from testeeaccommodations where _fk_TestOPportunity = ${oppkey} and AccType = ${Language};";
    // SqlParametersMaps params2 = (new SqlParametersMaps ()).put ("oppkey",
    // oppKey).put ("Language", "Language");
    // result = executeStatement (connection, SQL_QUERY2, params2,
    // false).getResultSets ().next ();
    // record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    // if (record != null) {
    // language = record.<String> get ("language");
    // }
    final String SQL_QUERY3 = " select distinct I._fk_ITem as itemKey from ${ItemBankDB}.tblsetofadminitems I, ${ConfigDB}.client_test_itemconstraint C1, " +
        " testeeaccommodations A1, ${ItemBankDB}.tblitemprops P1 where I._fk_AdminSUbject = ${segmentKey} and C1.Clientname = ${clientname} and C1.testID = ${testID} and" +
        " C1.item_in = 1 and A1._fk_TestOpportunity = ${oppkey} and A1.AccType = C1.ToolType and A1.AccCode = C1.ToolValue and P1._fk_AdminSubject = ${segmentKey} " +
        " and P1._fk_Item  = I._fk_Item and P1.Propname = C1.propname and P1.Propvalue = C1.Propvalue and P1.isActive = 1" +
        " and not exists " +
        " (select * from ${ConfigDB}.client_test_itemconstraint C2, testeeaccommodations A2, ${ItemBankDB}.tblitemprops P2 where A2._fk_TestOpportunity = ${oppkey} " +
        " and C2.Clientname = ${clientname} and C2.testID = ${testID} and C2.item_in = 0 and A2.AccType = C2.ToolType and A2.AccCode = C2.ToolValue" +
        " and P2._fk_AdminSubject = ${segmentKey} and P2._fk_Item  = I._fk_Item and P2.Propname = C2.propname and P2.Propvalue = C2.Propvalue and P2.isActive = 1) " +
        " group by I._fk_ITem having count(*) = (select count(*) from ${ConfigDB}.client_test_itemconstraint C1, testeeaccommodations A1 where C1.Clientname = ${clientname}" +
        " and C1.testID = ${testID} and C1.item_in = 1 and A1._fk_TestOpportunity = ${oppkey} and A1.AccType = C1.ToolType and A1.AccCode = C1.ToolValue)";
    String query = fixDataBaseNames (SQL_QUERY3);
    SqlParametersMaps params3 = (new SqlParametersMaps ()).put ("oppkey", oppKey).put ("segmentKey", segmentKey).put ("clientname", clientName).put ("testID", testId);
    result = executeStatement (connection, query, params3, true).getResultSets ().next ();

    return result;
  }

  /**
   * @param connection
   * @param clientName
   * @param oppKey
   * @param segmentKey
   * @param testId
   * @param fieldTest
   * @return
   * @throws ReturnStatusException
   */
  public DataBaseTable _AA_TestoppItempool_FN (SQLConnection connection, String clientName, UUID oppKey, String segmentKey, String testId, Boolean fieldTest)
      throws ReturnStatusException {

    DataBaseTable testOppItempoolTable = getDataBaseTable ("TestOppItempool").addColumn ("itemkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150).addColumn ("groupKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100)
        .addColumn ("groupID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("blockID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 10).addColumn ("isActive", SQL_TYPE_To_JAVA_TYPE.BIT);
    connection.createTemporaryTable (testOppItempoolTable);

    final String SQL_INSERT = "insert into ${tblName} (itemkey, groupKey ,groupID, blockID, isActive)"
        + " select I._fk_ITem as itemkey, I.groupKey, I.groupID, I.blockID, I.isActive from ${ItemBankDB}.tblsetofadminitems I, ${ConfigDB}.client_test_itemconstraint C1,"
        + " testeeaccommodations A1, ${ItemBankDB}.tblitemprops P1 where I._fk_AdminSUbject = ${segmentKey} and I.isFieldTest = ${fieldTest} and C1.Clientname = ${clientname}"
        + " and C1.testID = ${testID} and C1.item_in = 1  and A1._fk_TestOpportunity = ${oppkey} and A1.AccType = C1.ToolType and A1.AccCode = C1.ToolValue "
        + " and P1._fk_AdminSubject = ${segmentKey} and P1._fk_Item  = I._fk_Item and P1.Propname = C1.propname and P1.Propvalue = C1.Propvalue"
        + " and not exists"
        + " (select * from ${ConfigDB}.client_test_itemconstraint C2, testeeaccommodations A2, ${ItemBankDB}.tblitemprops P2 where A2._fk_TestOpportunity =${oppkey}"
        + " and C2.Clientname = ${clientname} and C2.testID = ${testID} and C2.item_in = 0 and A2.AccType = C2.ToolType and A2.AccCode = C2.ToolValue and P2._fk_AdminSubject = ${segmentKey} "
        + " and P2._fk_Item  = I._fk_Item and P2.Propname = C2.propname and P2.Propvalue = C2.Propvalue)"
        + " group by I._fk_ITem, I.groupKey, I.groupID, I.blockID, I.isActive having count(*) = (select count(*) from ${ConfigDB}.client_test_itemconstraint C1, testeeaccommodations A1 "
        + " where C1.Clientname =  ${clientname} and C1.testID =  ${testID} and C1.item_in = 1 and A1._fk_TestOpportunity = ${oppkey} and A1.AccType = C1.ToolType and A1.AccCode = C1.ToolValue); ";

    String query = fixDataBaseNames (SQL_INSERT);
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("tblName", testOppItempoolTable.getTableName ());
    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("clientname", clientName).put ("oppkey", oppKey).put ("segmentKey", segmentKey).put ("testID", testId)
        .put ("fieldTest", fieldTest);
    executeStatement (connection, fixDataBaseNames (query, unquotedParms), parameters, true).getUpdateCount ();

    return testOppItempoolTable;
  }

  /**
   * @param connection
   * @param oppKey
   * @param segmentKey
   * @param testlen
   * @param poolcount
   * @param poolString
   * @param sessionKey
   * @throws ReturnStatusException
   */
  public MultiDataResultSet _ComputeSegmentPool_SP (SQLConnection connection, UUID oppKey, String segmentKey, _Ref<Integer> testlen, _Ref<Integer> poolcount, _Ref<String> poolString, UUID sessionKey)
      throws ReturnStatusException {
    return _ComputeSegmentPool_SP (connection, oppKey, segmentKey, testlen, poolcount, poolString, false, sessionKey);
  }

  // original port
  public MultiDataResultSet _ComputeSegmentPool_SPV1 (SQLConnection connection, UUID oppKey, String segmentKey, _Ref<Integer> testlen, _Ref<Integer> poolcount, _Ref<String> poolString, Boolean debug,
      UUID sessionKey)
      throws ReturnStatusException {

    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();
    Integer shortfall = null;
    Integer strandCount = null;
    Integer newlen = null;
    SingleDataResultSet result = null;
    DbResultRecord record = null;

    DataBaseTable poolTable = getDataBaseTable ("pool").addColumn ("itemkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("isFT", SQL_TYPE_To_JAVA_TYPE.BIT)
        .addColumn ("isActive", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("strand", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200);
    DataBaseTable bluePrintTable = getDataBaseTable ("bluePrint").addColumn ("strand", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200).addColumn ("minItems", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("maxItems", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("poolcnt", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (poolTable);
    connection.createTemporaryTable (bluePrintTable);

    poolString.set (_AA_ItempoolString_FN (connection, oppKey, segmentKey));
    DataBaseTable buildTable = _commonDll._BuildTable_FN (connection, "_BuildTable_FN", poolString.get (), ",");

    final String SQL_INSERT1 = "insert into ${poolTable} (itemkey) select record from ${temporaryTableName}";
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("poolTable", poolTable.getTableName ());
    unquotedParms.put ("temporaryTableName", buildTable.getTableName ());
    executeStatement (connection, fixDataBaseNames (SQL_INSERT1, unquotedParms), null, false).getUpdateCount ();

    if (sessionKey == null) {
      final String SQL_UPDATE1 = "update ${poolTable} P, ${ItemBankDB}.tblsetofadminitems I set P.isFT = isFieldTest, P.isActive = I.isActive, P.strand = strandname  " +
          " where _fk_AdminSubject = ${segmentKey} and _fk_Item = itemkey;";
      String query = fixDataBaseNames (SQL_UPDATE1);
      Map<String, String> unquotedParms1 = new HashMap<String, String> ();
      unquotedParms1.put ("poolTable", poolTable.getTableName ());
      SqlParametersMaps parms1 = new SqlParametersMaps ().put ("segmentKey", segmentKey);
      executeStatement (connection, fixDataBaseNames (query, unquotedParms1), parms1, false).getUpdateCount ();

      final String SQL_INSERT2 = "insert into ${bluePrintTable} (strand, minItems, maxItems, poolcnt) " +
          " select _fk_Strand, minitems, maxItems, (select count(*) from ${poolTable} where strand = _fk_strand and isFT = 0 and isActive = 1) from ${ItemBankDB}.tbladminstrand" +
          " where _fk_AdminSubject = ${segmentKey} and adaptiveCut is not null;";
      query = fixDataBaseNames (SQL_INSERT2);
      Map<String, String> unquotedParms2 = new HashMap<String, String> ();
      unquotedParms2.put ("poolTable", poolTable.getTableName ());
      unquotedParms2.put ("bluePrintTable", bluePrintTable.getTableName ());
      SqlParametersMaps parms2 = parms1;
      executeStatement (connection, fixDataBaseNames (query, unquotedParms2), parms2, false).getUpdateCount ();

      final String SQL_QUERY1 = "select minitems as testlen from ${ItemBankDB}.tblsetofadminsubjects where _Key = ${segmentKey};";
      query = fixDataBaseNames (SQL_QUERY1);
      SqlParametersMaps parms3 = parms1;
      result = executeStatement (connection, query, parms3, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        testlen.set (record.<Integer> get ("testlen"));
      }
    } else {
      final String SQL_UPDATE2 = "update ${poolTable} P, sim_segmentitem I set P.isFT = isFieldTest, P.isActive = I.isActive, P.strand = I.strand " +
          " where _fk_Session = ${sessionkey} and _efk_Segment = ${segmentKey} and _efk_Item = itemkey;";
      Map<String, String> unquotedParms3 = new HashMap<String, String> ();
      unquotedParms3.put ("poolTable", poolTable.getTableName ());
      SqlParametersMaps parms4 = new SqlParametersMaps ().put ("sessionkey", sessionKey).put ("segmentKey", segmentKey);
      executeStatement (connection, fixDataBaseNames (SQL_UPDATE2, unquotedParms3), parms4, false).getUpdateCount ();

      final String SQL_INSERT3 = "insert into ${bluePrintTable} (strand, minItems, maxItems, poolcnt) " +
          " select contentLevel, minitems, maxItems, (select count(*) from ${poolTable} where strand = contentLevel and isFT = 0 and isActive = 1) " +
          " from sim_segmentcontentlevel where _fk_Session = ${sessionkey} and _efk_Segment = ${segmentKey} and adaptiveCut is not null;";
      Map<String, String> unquotedParms4 = new HashMap<String, String> ();
      unquotedParms4.put ("poolTable", poolTable.getTableName ());
      unquotedParms4.put ("bluePrintTable", bluePrintTable.getTableName ());
      SqlParametersMaps parms5 = parms4;
      executeStatement (connection, fixDataBaseNames (SQL_INSERT3, unquotedParms4), parms5, false).getUpdateCount ();

      final String SQL_QUERY2 = "select minitems as testlen from sim_segment where _fk_Session = ${sessionkey} and _efk_Segment = ${segmentKey};";
      SqlParametersMaps parms6 = parms4;
      result = executeStatement (connection, SQL_QUERY2, parms6, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        testlen.set (record.<Integer> get ("testlen"));
      }
    }
    final String SQL_QUERY3 = "select convert(sum(minitems - poolcnt), SIGNED) as shortfall from ${bluePrintTable} where poolcnt < minitems;";
    Map<String, String> unquotedParms5 = new HashMap<String, String> ();
    unquotedParms5.put ("bluePrintTable", bluePrintTable.getTableName ());
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY3, unquotedParms5), null, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      Long tmp = record.<Long> get ("shortfall");
      shortfall = (tmp == null ? null : tmp.intValue ());
    }
    if (shortfall == null) {
      shortfall = 0;
    }

    final String SQL_QUERY4 = "select convert(sum(poolcnt), SIGNED) as strandcnt from ${bluePrintTable};";
    Map<String, String> unquotedParms6 = unquotedParms5;
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY4, unquotedParms6), null, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      Long tmp = record.<Long> get ("strandcnt");
      strandCount = (tmp == null ? null : tmp.intValue ());
    }
    if (DbComparator.lessThan ((testlen.get () - shortfall), strandCount)) {
      newlen = testlen.get () - shortfall;
    } else {
      newlen = strandCount;
    }
    poolcount.set (strandCount);
    testlen.set (newlen);

    if (DbComparator.isEqual (debug, true)) {
      final String SQL_QUERY5 = "select ${testlen} as testlen, ${shortfall} as shortfall, ${strandcnt} as strandcnt, ${newlen} as newlen, ${poolstring} as poolstring;";
      SqlParametersMaps parms7 = new SqlParametersMaps ();
      parms7.put ("testlen", testlen);
      parms7.put ("shortfall", shortfall);
      parms7.put ("strandcnt", strandCount);
      parms7.put ("newlen", newlen);
      parms7.put ("poolstring", poolString.get ());
      SingleDataResultSet rs1 = executeStatement (connection, SQL_QUERY5, parms7, false).getResultSets ().next ();
      resultsets.add (rs1);

      final String SQL_QUERY6 = "select * from ${bluePrintTable};";
      Map<String, String> unquotedParms7 = unquotedParms5;
      SingleDataResultSet rs2 = executeStatement (connection, fixDataBaseNames (SQL_QUERY6, unquotedParms7), null, false).getResultSets ().next ();
      resultsets.add (rs2);

      final String SQL_QUERY7 = "select * from ${poolTable};";
      Map<String, String> unquotedParms8 = new HashMap<String, String> ();
      unquotedParms8.put ("poolTable", poolTable.getTableName ());
      SingleDataResultSet rs3 = executeStatement (connection, fixDataBaseNames (SQL_QUERY7, unquotedParms8), null, false).getResultSets ().next ();
      resultsets.add (rs3);
    }
    connection.dropTemporaryTable (bluePrintTable);
    connection.dropTemporaryTable (poolTable);
    connection.dropTemporaryTable (buildTable);

    return new MultiDataResultSet (resultsets);
  }

  // Attempt to optimize
  public MultiDataResultSet _ComputeSegmentPool_SP (SQLConnection connection, UUID oppKey, String segmentKey, _Ref<Integer> testlen, _Ref<Integer> poolcount, _Ref<String> poolString, Boolean debug,
      UUID sessionKey)
      throws ReturnStatusException {

    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();
    Integer shortfall = null;
    Integer strandCount = null;
    Integer newlen = null;
    SingleDataResultSet result = null;
    DbResultRecord record = null;

    DataBaseTable poolTable = getDataBaseTable ("pool").addColumn ("itemkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("isFT", SQL_TYPE_To_JAVA_TYPE.BIT)
        .addColumn ("isActive", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("strand", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200);
    DataBaseTable bluePrintTable = getDataBaseTable ("bluePrint").addColumn ("strand", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200).addColumn ("minItems", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("maxItems", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("poolcnt", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (poolTable);
    connection.createTemporaryTable (bluePrintTable);

    SingleDataResultSet rs = _AA_ItempoolString_FNOptimized (connection, oppKey, segmentKey);
    String itemString = null;
    Iterator<DbResultRecord> records = rs.getRecords ();
    while (records.hasNext ()) {
      record = records.next ();
      if (itemString == null)
        itemString = record.<String> get ("itemKey");
      else
        itemString = String.format ("%s,%s", itemString, record.<String> get ("itemKey"));
    }
    poolString.set (itemString);

    final String SQL_INSERT1 = "insert into ${poolTable} (itemkey) values (${itemKey})";
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("poolTable", poolTable.getTableName ());

    insertBatch (connection, fixDataBaseNames (SQL_INSERT1, unquotedParms), rs, null);

    if (sessionKey == null) {
      final String SQL_UPDATE1 = "update ${poolTable} P, ${ItemBankDB}.tblsetofadminitems I set P.isFT = isFieldTest, P.isActive = I.isActive, P.strand = strandname  " +
          " where _fk_AdminSubject = ${segmentKey} and _fk_Item = itemkey;";
      String query = fixDataBaseNames (SQL_UPDATE1);
      Map<String, String> unquotedParms1 = new HashMap<String, String> ();
      unquotedParms1.put ("poolTable", poolTable.getTableName ());
      SqlParametersMaps parms1 = new SqlParametersMaps ().put ("segmentKey", segmentKey);
      executeStatement (connection, fixDataBaseNames (query, unquotedParms1), parms1, false).getUpdateCount ();

      final String SQL_INSERT2 = "insert into ${bluePrintTable} (strand, minItems, maxItems, poolcnt) " +
          " select _fk_Strand, minitems, maxItems, (select count(*) from ${poolTable} where strand = _fk_strand and isFT = 0 and isActive = 1) from ${ItemBankDB}.tbladminstrand" +
          " where _fk_AdminSubject = ${segmentKey} and adaptiveCut is not null;";
      query = fixDataBaseNames (SQL_INSERT2);
      Map<String, String> unquotedParms2 = new HashMap<String, String> ();
      unquotedParms2.put ("poolTable", poolTable.getTableName ());
      unquotedParms2.put ("bluePrintTable", bluePrintTable.getTableName ());
      SqlParametersMaps parms2 = parms1;
      executeStatement (connection, fixDataBaseNames (query, unquotedParms2), parms2, false).getUpdateCount ();

      final String SQL_QUERY1 = "select case when selectionAlgorithm = 'adaptive2' then maxitems else minitems end as testlen from ${ItemBankDB}.tblsetofadminsubjects where _Key = ${segmentKey}";
      // final String SQL_QUERY1 =
      // "select minitems as testlen from ${ItemBankDB}.tblsetofadminsubjects where _Key = ${segmentKey};";
      query = fixDataBaseNames (SQL_QUERY1);
      SqlParametersMaps parms3 = parms1;
      result = executeStatement (connection, query, parms3, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        testlen.set (record.<Integer> get ("testlen"));
      }
    } else {
      final String SQL_UPDATE2 = "update ${poolTable} P, sim_segmentitem I set P.isFT = isFieldTest, P.isActive = I.isActive, P.strand = I.strand " +
          " where _fk_Session = ${sessionkey} and _efk_Segment = ${segmentKey} and _efk_Item = itemkey;";
      Map<String, String> unquotedParms3 = new HashMap<String, String> ();
      unquotedParms3.put ("poolTable", poolTable.getTableName ());
      SqlParametersMaps parms4 = new SqlParametersMaps ().put ("sessionkey", sessionKey).put ("segmentKey", segmentKey);
      executeStatement (connection, fixDataBaseNames (SQL_UPDATE2, unquotedParms3), parms4, false).getUpdateCount ();

      final String SQL_INSERT3 = "insert into ${bluePrintTable} (strand, minItems, maxItems, poolcnt) " +
          " select contentLevel, minitems, maxItems, (select count(*) from ${poolTable} where strand = contentLevel and isFT = 0 and isActive = 1) " +
          " from sim_segmentcontentlevel where _fk_Session = ${sessionkey} and _efk_Segment = ${segmentKey} and adaptiveCut is not null;";
      Map<String, String> unquotedParms4 = new HashMap<String, String> ();
      unquotedParms4.put ("poolTable", poolTable.getTableName ());
      unquotedParms4.put ("bluePrintTable", bluePrintTable.getTableName ());
      SqlParametersMaps parms5 = parms4;
      executeStatement (connection, fixDataBaseNames (SQL_INSERT3, unquotedParms4), parms5, false).getUpdateCount ();

      final String SQL_QUERY2 = "select case when selectionAlgorithm = 'adaptive2' then maxitems else minitems end as testlen from sim_segment where _fk_Session = ${sessionkey} and _efk_Segment = ${segmentKey}";
      // final String SQL_QUERY2 =
      // "select minitems as testlen from sim_segment where _fk_Session = ${sessionkey} and _efk_Segment = ${segmentKey};";
      SqlParametersMaps parms6 = parms4;
      result = executeStatement (connection, SQL_QUERY2, parms6, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        testlen.set (record.<Integer> get ("testlen"));
      }
    }
    final String SQL_QUERY3 = "select convert(sum(minitems - poolcnt), SIGNED) as shortfall from ${bluePrintTable} where poolcnt < minitems;";
    Map<String, String> unquotedParms5 = new HashMap<String, String> ();
    unquotedParms5.put ("bluePrintTable", bluePrintTable.getTableName ());
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY3, unquotedParms5), null, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      Long tmp = record.<Long> get ("shortfall");
      shortfall = (tmp == null ? null : tmp.intValue ());
    }
    if (shortfall == null) {
      shortfall = 0;
    }

    final String SQL_QUERY4 = "select convert(sum(poolcnt), SIGNED) as strandcnt from ${bluePrintTable};";
    Map<String, String> unquotedParms6 = unquotedParms5;
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY4, unquotedParms6), null, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      Long tmp = record.<Long> get ("strandcnt");
      strandCount = (tmp == null ? null : tmp.intValue ());
    }
    if (DbComparator.lessThan ((testlen.get () - shortfall), strandCount)) {
        // test offgrade items: TODO check
      newlen = (testlen.get () - shortfall > 0)? testlen.get () - shortfall: testlen.get ();
    } else {
      newlen = strandCount;
    }
    poolcount.set (strandCount);
    testlen.set (newlen);

    if (DbComparator.isEqual (debug, true)) {
      final String SQL_QUERY5 = "select ${testlen} as testlen, ${shortfall} as shortfall, ${strandcnt} as strandcnt, ${newlen} as newlen, ${poolstring} as poolstring;";
      SqlParametersMaps parms7 = new SqlParametersMaps ();
      parms7.put ("testlen", testlen);
      parms7.put ("shortfall", shortfall);
      parms7.put ("strandcnt", strandCount);
      parms7.put ("newlen", newlen);
      parms7.put ("poolstring", poolString.get ());
      SingleDataResultSet rs1 = executeStatement (connection, SQL_QUERY5, parms7, false).getResultSets ().next ();
      resultsets.add (rs1);

      final String SQL_QUERY6 = "select * from ${bluePrintTable};";
      Map<String, String> unquotedParms7 = unquotedParms5;
      SingleDataResultSet rs2 = executeStatement (connection, fixDataBaseNames (SQL_QUERY6, unquotedParms7), null, false).getResultSets ().next ();
      resultsets.add (rs2);

      final String SQL_QUERY7 = "select * from ${poolTable};";
      Map<String, String> unquotedParms8 = new HashMap<String, String> ();
      unquotedParms8.put ("poolTable", poolTable.getTableName ());
      SingleDataResultSet rs3 = executeStatement (connection, fixDataBaseNames (SQL_QUERY7, unquotedParms8), null, false).getResultSets ().next ();
      resultsets.add (rs3);
    }
    connection.dropTemporaryTable (bluePrintTable);
    connection.dropTemporaryTable (poolTable);

    return new MultiDataResultSet (resultsets);
  }

  /**
   * @param connection
   * @param oppKey
   * @return
   * @throws ReturnStatusException
   */
  public Boolean IsSimulation_FN (SQLConnection connection, UUID oppKey) throws ReturnStatusException {

    Boolean sim = false;
    long cnt = 0L;
    final String SQL_QUERY = "select count(*) as cnt from _externs E, testopportunity O, sim_segment S where O._Key = ${oppkey} and E.clientname = O.clientname and " +
        " E.environment = ${SIMULATION} and S._fk_Session = O._fk_Session and S._efk_AdminSubject = O._efk_AdminSubject ";
    SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppKey).put ("SIMULATION", "SIMULATION");

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY, parameters, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      cnt = record.<Long> get ("cnt");
    }
    if (cnt > 0)
      sim = true;
    return sim;
  }

  /**
   * 
   * @param connection
   * @param testOppKey
   * @param testKey
   * @param segment
   * @param segmentId
   * @param language
   * @param ftCount
   * @param noinsert
   * @throws ReturnStatusException
   */
  public MultiDataResultSet _FT_SelectItemgroups_SP (SQLConnection connection, UUID testOppKey, String testKey, Integer segment, String segmentId, String language, _Ref<Integer> ftCount)
      throws ReturnStatusException {
    return _FT_SelectItemgroups_SP (connection, testOppKey, testKey, segment, segmentId, language, ftCount, 0, 0);
  }

  public MultiDataResultSet _FT_SelectItemgroups_SP (SQLConnection connection, UUID testOppKey, String testKey, Integer segment, String segmentId, String language, _Ref<Integer> ftCount,
      Integer debug, Integer noinsert) throws ReturnStatusException {

    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();
    Date now = _dateUtil.getDateWRetStatus (connection);
    Integer tier = null;
    Integer cnt = null;
    Integer numitems = null;
    String grpkey = null;
    String grp = null;
    String block = null;
    String parentTest = null;
    Long testee = null;
    String clientName = null;
    String subject = null;
    UUID session = null;

    Boolean isSim = IsSimulation_FN (connection, testOppKey);

    // variables for holding test specs
    Integer startPos = null;
    Integer endPos = null;
    Integer numIntervals = null;
    Integer minItems = null;
    Integer maxItems = null;
    Integer intervalSize = null;
    // the interval size is computed for each time through the loop
    // thisIntSize = 1 for item groups of 1 item, = n - 1 for itemgroups of > 1
    // item
    // The -1 is to allow room for the group to flow over its selected start
    // position without encroaching on the next interval
    Integer thisIntSize = null;
    Integer nextPos = null; // start position of the currently selected item
                            // group
    Integer intervalIndex = null; // this starts at startpos + 1 and is
                                  // incremented by
    // @intervalSize * num_items_in_group each time
    // through the loop

    final String SQL_QUERY1 = "select _efk_Testee as testee, clientname, subject, _efk_AdminSubject as parentTest, _fk_Session as session from testopportunity where _Key = ${testoppkey}; ";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("testoppkey", testOppKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      testee = record.<Long> get ("testee");
      clientName = record.<String> get ("clientname");
      subject = record.<String> get ("subject");
      parentTest = record.<String> get ("parentTest");
      session = record.<UUID> get ("session");
    }
    if (DbComparator.isEqual (isSim, false)) {
      final String SQL_QUERY2 = "select FTMinItems as minitems, FTmaxITems as maxItems, FTStartPos as startPos, FTEndPos as endpos " +
          " from ${ItemBankDB}.tblsetofadminsubjects where _Key = ${testkey};";
      SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("testkey", testKey);
      String query = fixDataBaseNames (SQL_QUERY2);
      result = executeStatement (connection, query, parms2, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        minItems = record.<Integer> get ("minitems");
        maxItems = record.<Integer> get ("maxItems");
        startPos = record.<Integer> get ("startPos");
        endPos = record.<Integer> get ("endpos");
      }
    } else {
      final String SQL_QUERY3 = "select FTMinItems as minitems, FTmaxITems as maxItems, FTStartPos as startPos, FTEndPos as endpos " +
          " from sim_segment  where _fk_Session = ${session} and _efk_Segment = ${testkey};";
      SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testKey);
      result = executeStatement (connection, SQL_QUERY3, parms3, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        minItems = record.<Integer> get ("minitems");
        maxItems = record.<Integer> get ("maxItems");
        startPos = record.<Integer> get ("startPos");
        endPos = record.<Integer> get ("endpos");
      }
    }
    // does this test opportunity already have items that have been
    // selected/administered?
    final String SQL_QUERY4 = "select convert(sum(numitems), SIGNED) as ftcount from ft_opportunityitem "
        + " where _fk_TestOpportunity = ${testoppkey} and _efk_FieldTest = ${testkey} and coalesce(deleted, 0) = 0;";
    SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("testoppkey", testOppKey).put ("testkey", testKey);
    result = executeStatement (connection, SQL_QUERY4, parms4, true).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      Long tmp = record.<Long> get ("ftcount");
      ftCount.set (tmp == null ? null : tmp.intValue ());
    }
    if (ftCount.get () == null) {
      ftCount.set (0);
    } else if (DbComparator.greaterOrEqual (ftCount.get (), minItems)) {
      if (DbComparator.greaterThan (debug, 0)) {
        // System.err.println ("Aborted selection due to existing items");
        return new MultiDataResultSet (resultsets);
      }
    }
    numIntervals = maxItems;
    // if there are just enough positions as items, then interval size = 0 which
    // implies that item groups must be placed back to back
    intervalSize = (endPos - startPos) / numIntervals;
    // working interval position index. Not really an interval index, but rather
    // the position at the start of the next interval
    intervalIndex = startPos;

    DataBaseTable itemsTable = getDataBaseTable ("items").addColumn ("grpkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 60).addColumn ("grp", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50)
        .addColumn ("block", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 10).addColumn ("numitems", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("tier", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("admins", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("position", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("intervalStart", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("intervalSize", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("numintervals", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("selected", SQL_TYPE_To_JAVA_TYPE.BIT)
        .addColumn ("used", SQL_TYPE_To_JAVA_TYPE.BIT);
    connection.createTemporaryTable (itemsTable);
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("itemsTableName", itemsTable.getTableName ());

    SingleDataResultSet res = _FT_Prioritize_2012_SP (connection, testOppKey, testKey);
    final String SQL_INSERT_TEMP = "insert into ${itemsTableName} (grpkey, grp, block, numitems, tier, admins) values (${grpkey}, ${groupID}, ${blockID}, ${activeItems}, ${tier}, ${admins})";
    insertBatch (connection, fixDataBaseNames (SQL_INSERT_TEMP, unquotedParms), res, null);

    final String SQL_INDEX = "create index tix_ftitems on ${itemsTableName} (grpkey);";
    Map<String, String> unquotedparms = new HashMap<String, String> ();
    unquotedparms.put ("itemsTableName", itemsTable.getTableName ());
    executeStatement (connection, fixDataBaseNames (SQL_INDEX, unquotedparms), null, false);

    final String SQL_UPDATE1 = "update ${itemsTableName} set  selected = 0, used = 0;";
    unquotedParms.put ("itemsTableName", itemsTable.getTableName ());
    executeStatement (connection, fixDataBaseNames (SQL_UPDATE1, unquotedParms), null, false).getUpdateCount ();
    // System.err.println (updateCnt);

    if (debug == 1) {
      final String SQL_QUERY5 = "select 'original selected items', ${language} as language, * from  ${itemsTableName} order by tier, admins;";
      SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("language", language);
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY5, unquotedParms), parms5, false).getResultSets ().next ();
      resultsets.add (result);
    }
    // remove all itemgroups administered to this student
    final String SQL_DELETE1 = "delete from ${itemsTableName} where grp in (select groupID from ft_opportunityitem F, testopportunity O" +
        " where O._efk_Testee = ${testee} and O.clientname = ${clientname} and O.subject = ${subject} and F._fk_TestOpportunity = O._Key);";
    SqlParametersMaps parms6 = (new SqlParametersMaps ()).put ("testee", testee).put ("clientname", clientName).put ("subject", subject);
    executeStatement (connection, fixDataBaseNames (SQL_DELETE1, unquotedParms), parms6, true).getUpdateCount ();
    // System.err.println (deletedCnt);

    if (DbComparator.greaterOrEqual (debug, 1)) {
      final String SQL_QUERY6 = "select 'filtered candidate items', * from ${itemsTableName} order by tier, admins;";
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY6, unquotedParms), null, false).getResultSets ().next ();
      resultsets.add (result);
    }
    if (DbComparator.notEqual (debug, 0)) {
      final String SQL_QUERY7 = "select 'control variables', ${startpos} as startpos, ${endpos} as endpos, ${minitems} as minitems, ${maxitems} as maxitems," +
          " ${numintervals} as numintervals, ${intervalSize} as intervalSize, ${ftcount} as ftcount;";
      SqlParametersMaps parms7 = new SqlParametersMaps ();
      parms7.put ("startpos", startPos);
      parms7.put ("endpos", endPos);
      parms7.put ("minitems", minItems);
      parms7.put ("maxitems", maxItems);
      parms7.put ("numintervals", numIntervals);
      parms7.put ("intervalSize", intervalSize);
      parms7.put ("ftcount", ftCount);
      result = executeStatement (connection, SQL_QUERY7, parms7, false).getResultSets ().next ();
      resultsets.add (result);
    }

    DataBaseTable cohortsTable = getDataBaseTable ("cohorts").addColumn ("cohortindex", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("ratio", SQL_TYPE_To_JAVA_TYPE.FLOAT)
        .addColumn ("available", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("targetcount", SQL_TYPE_To_JAVA_TYPE.FLOAT).addColumn ("itemcount", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("groupcount", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (cohortsTable);
    Map<String, String> unquotedParms1 = new HashMap<String, String> ();
    unquotedParms1.put ("cohortsTableName", cohortsTable.getTableName ());

    int itemcohort = 0;
    final String SQL_INSERT1 = "insert into ${cohortsTableName} (cohortindex, ratio, targetcount, itemcount) " +
        " select Cohort, ItemRatio, ItemRatio * ${maxitems}, 0 from ${ItemBankDB}.testcohort where _fk_AdminSubject = ${testkey};";
    String query = fixDataBaseNames (SQL_INSERT1);
    SqlParametersMaps parms8 = new SqlParametersMaps ().put ("maxitems", maxItems).put ("testkey", testKey);
    executeStatement (connection, fixDataBaseNames (query, unquotedParms1), parms8, false).getUpdateCount ();
    // System.err.println (insertedCnt);

    final String SQL_QUERY8 = "select  cohortindex from ${cohortsTableName} limit 1";
    Map<String, String> unquotedParms5 = unquotedParms1;
    if (!exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY8, unquotedParms5), null, false))) {
      final String SQL_INSERT2 = "insert into ${cohortsTableName} (cohortindex, ratio, targetcount, itemcount) values (1, 1, ${maxitems}, 0);";
      SqlParametersMaps parms9 = new SqlParametersMaps ().put ("maxitems", maxItems);
      executeStatement (connection, fixDataBaseNames (SQL_INSERT2, unquotedParms1), parms9, false).getUpdateCount ();
    }
    if (DbComparator.notEqual (debug, 0)) {
      final String SQL_QUERY9 = "select 'cohorts', * from ${cohortsTableName} ;";
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY9, unquotedParms1), null, false).getResultSets ().next ();
      resultsets.add (result);
    }

    if (DbComparator.greaterThan ((endPos - startPos), (maxItems + 10))) {
      endPos = endPos - 2;
    }

    final String SQL_QUERY10 = "select  grpkey from ${itemsTableName} where used = 0 limit 1";

    while (DbComparator.lessThan (ftCount.get (), minItems) && exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY10, unquotedParms), null, false))) {
      // reinitialize the working variable in the cohorts table
      final String SQL_UPDATE2 = "update ${cohortsTableName} set groupcount = 0;";
      executeStatement (connection, fixDataBaseNames (SQL_UPDATE2, unquotedParms1), null, false).getUpdateCount ();

      final String SQL_QUERY11 = "select max(tier) as tier from ${itemsTableName} where used = 0;";
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY11, unquotedParms), null, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        tier = record.<Integer> get ("tier");
      }
      final String SQL_QUERY12 = "select min(admins) as cnt from ${itemsTableName} where tier = ${tier} and used = 0;";
      SqlParametersMaps parms10 = new SqlParametersMaps ().put ("tier", tier);
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY12, unquotedParms), parms10, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        cnt = record.<Integer> get ("cnt");
      }

      final String SQL_QUERY13 = "select  grpkey, grp, block, numitems from ${itemsTableName} where tier = ${tier} and admins = ${cnt} and used = 0 limit 1;";
      SqlParametersMaps parms11 = new SqlParametersMaps ().put ("tier", tier).put ("cnt", cnt);
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY13, unquotedParms), parms11, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        grpkey = record.<String> get ("grpkey");
        grp = record.<String> get ("grp");
        block = record.<String> get ("block");
        numitems = record.<Integer> get ("numitems");
      }

      if (debug == 3) {
        final String SQL_QUERY14 = "select 'loop vars', ${tier} as tier, ${cnt} as cnt, ${grpkey} as grpkey, ${numitems} as numitems;";
        SqlParametersMaps parms12 = new SqlParametersMaps ();
        parms12.put ("tier", tier);
        parms12.put ("cnt", cnt);
        parms12.put ("grpkey", grpkey);
        parms12.put ("numitems", numitems);
        result = executeStatement (connection, SQL_QUERY14, parms12, false).getResultSets ().next ();
        resultsets.add (result);
      }
      // remove this item group from further consideration
      final String SQL_UPDATE3 = "update ${itemsTableName} set used = 1 where grpkey = ${grpkey};";
      SqlParametersMaps parms13 = new SqlParametersMaps ().put ("grpkey", grpkey);
      executeStatement (connection, fixDataBaseNames (SQL_UPDATE3, unquotedParms), parms13, false).getUpdateCount ();

      // record the cohort counts in this item group's items. Note that a single
      // item group may have items from different cohorts.
      final String SQL_UPDATE4 = "update ${cohortsTableName} set groupcount = (select count(*) from ${ItemBankDB}.tblsetofadminitems I, ${ItemBankDB}.tblitemprops P " +
          " where I._fk_AdminSubject = ${testkey} and P._fk_AdminSubject = ${testKey} and I.groupkey = ${grpkey} and P.propname = ${Language} and P.propvalue = ${lang} " +
          " and P._fk_Item = I._fk_Item and P._fk_AdminSUbject = ${testkey} and I.testCohort = cohortIndex);";
      query = fixDataBaseNames (SQL_UPDATE4);
      SqlParametersMaps parms14 = new SqlParametersMaps ().put ("testkey", testKey).put ("grpkey", grpkey).put ("Language", "Language").put ("lang", language);
      executeStatement (connection, fixDataBaseNames (query, unquotedParms1), parms14, false).getUpdateCount ();

      final String SQL_QUERY15 = "select  cohortindex from ${cohortsTableName} where groupcount > 0 and itemcount < targetcount limit 1";
      if (!exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY15, unquotedParms1), null, false))) {
        if (debug > 0) {
          _logger.debug ("Continue 2"); // for testing
        }
        continue;
      }
      if (DbComparator.greaterThan (numitems, 0) && DbComparator.lessOrEqual ((ftCount.get () + numitems), maxItems)) {
        if (DbComparator.lessThan (intervalSize, 0)) {
          thisIntSize = 1;
        } else if (DbComparator.isEqual (numitems, 1)) {
          thisIntSize = intervalSize;
        } else {
          thisIntSize = intervalSize * (numitems - 1);
        }
        // set @nextpos = (cast (round(RAND() * 1000, 0) as int) % @thisIntSize)
        // + @intervalIndex;
        // nextDouble() method generates a random double value between 0 and 1
        Random generator = new Random ();
        int i = generator.nextInt (1000);
        nextPos = (i % thisIntSize) + intervalIndex;

        if (debug == 3) {
          final String SQL_QUERY16 = "select 'loop', ${numitems} as numItems, ${nextpos} as thispos, ${intervalIndex} as intStart, ${thisIntSize} as intSize; ";
          SqlParametersMaps parms15 = new SqlParametersMaps ();
          parms15.put ("numitems", numitems);
          parms15.put ("nextpos", nextPos);
          parms15.put ("intervalIndex", intervalIndex);
          parms15.put ("thisIntSize", thisIntSize);
          result = executeStatement (connection, SQL_QUERY16, parms15, false).getResultSets ().next ();
          resultsets.add (result);
        }
        final String SQL_UPDATE5 = "update ${itemsTableName} set selected = 1, position = ${nextpos}, intervalStart = ${intervalIndex}, intervalSize = ${intervalSize}, " +
            " numintervals = ${numitems} where grpkey = ${grpkey};";
        SqlParametersMaps parms16 = new SqlParametersMaps ().put ("nextpos", nextPos).put ("intervalIndex", intervalIndex).put ("intervalSize", intervalSize).put ("numitems", numitems)
            .put ("grpkey", grpkey);
        executeStatement (connection, fixDataBaseNames (SQL_UPDATE5, unquotedParms), parms16, false).getUpdateCount ();

        // indicate that all blocks related to this group are 'used'
        final String SQL_UPDATE6 = " update ${itemsTableName} set used = 1 where grp = ${grp};";
        SqlParametersMaps parms17 = new SqlParametersMaps ().put ("grp", grp);
        executeStatement (connection, fixDataBaseNames (SQL_UPDATE6, unquotedParms), parms17, false).getUpdateCount ();

        // record impact of this itemgroup selection on algorithm variables
        ftCount.set (ftCount.get () + numitems);
        if (DbComparator.isEqual (intervalSize, 0)) {
          intervalIndex = intervalIndex + numitems * 1;
        } else {
          intervalIndex = intervalIndex + numitems * intervalSize;
        }
        // add to the cohort itemcount (NEVER alter the targetcount)
        // groupcount is the number of items in this group in the respective
        // cohort this update looks wrong but it is not.
        // That is because the only cohorts whose groupcount is not zero are
        // those that have items from this group. So for all other cohorts,
        // itemcount = itemcount + 0, because groupcount is universally set to 0
        // at the top of this loop
        final String SQL_UPDATE7 = "update ${cohortsTableName} set itemcount = itemcount + groupcount;";
        executeStatement (connection, fixDataBaseNames (SQL_UPDATE7, unquotedParms1), null, false).getUpdateCount ();
      }
    }
    if (debug == 0) {
      final String SQL_DELETE2 = "delete from ${itemsTableName} where selected = 0;";
      executeStatement (connection, fixDataBaseNames (SQL_DELETE2, unquotedParms), null, false).getUpdateCount ();
    }
    String msg = null;
    if (DbComparator.isEqual (noinsert, 1)) {
      final String SQL_QUERY17 = "select * from ${itemsTableName} where selected = 1;";
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY17, unquotedParms), null, false).getResultSets ().next ();
      resultsets.add (result);
    } else {
      try {
        boolean preexistingAutoCommitMode = connection.getAutoCommit ();
        connection.setAutoCommit (false);

        final String SQL_QUERY18 = "select  _fk_TestOpportunity from ft_opportunityitem where _fk_TestOpportunity = ${testoppkey} and _efk_FieldTest = ${testkey} limit 1";
        SqlParametersMaps parms17 = parms4;
        if (!exists (executeStatement (connection, SQL_QUERY18, parms17, false))) {
          final String SQL_INSERT3 = "insert into ft_opportunityitem (_fk_TestOpportunity, _fk_Session, _efk_FieldTest, _efk_ParentTest, segment, segmentID, language, "
              + " position, groupKey, groupID, blockID, numitems,  intervalStart, intervalSize, numIntervals, dateassigned, deleted, _dategenerated)"
              + " select ${testoppkey}, ${session}, ${testkey}, ${parentTest}, ${segment}, ${segmentID}, ${language}, position, grpkey, grp, block, numitems, intervalStart, "
              + " intervalSize, numIntervals, ${now}, 0, now(3)  from ${itemsTableName} where selected = 1 ";
          SqlParametersMaps parms18 = new SqlParametersMaps ().put ("testoppkey", testOppKey).put ("session", session).put ("testkey", testKey).put ("parentTest", parentTest)
              .put ("segment", segment).put ("segmentID", segmentId).put ("language", language).put ("now", now);
          executeStatement (connection, fixDataBaseNames (SQL_INSERT3, unquotedParms), parms18, false).getUpdateCount ();
        }
        final String SQL_QUERY19 = "select convert(sum(numitems), SIGNED) as ftcount from ft_opportunityitem "
            + " where _fk_TestOpportunity = ${testoppkey} and _efk_FieldTest = ${testkey} and coalesce(deleted, 0) = 0;";
        SqlParametersMaps parms19 = parms4;
        result = executeStatement (connection, SQL_QUERY19, parms19, false).getResultSets ().next ();
        record = (result.getCount () > 0 ? result.getRecords ().next () : null);
        if (record != null) {
          Long tmp = record.<Long> get ("ftcount");
          ftCount.set (tmp == null ? null : tmp.intValue ());
        }
        connection.commit ();
        connection.setAutoCommit (preexistingAutoCommitMode);
      } catch (Exception e) {
        try {
          connection.rollback ();
        } catch (SQLException se) {
          _logger.error (String.format ("Failed rollback: %s", e.getMessage ()));
        }
        msg = e.getMessage ();
        _commonDll._LogDBError_SP (connection, "_FT_SelectItemgroups", msg, testee, testKey, null, testOppKey);
        ftCount.set (0);
        connection.dropTemporaryTable (itemsTable);
        connection.dropTemporaryTable (cohortsTable);
        return new MultiDataResultSet (resultsets);
      }
    }

    try {
      if (DbComparator.isEqual (isSim, true)) {
        // Elena: this insert syntax seems to work
        final String SQL_INSERT4 = "insert into ft_groupsamples (_efk_AdminSubject, groupID, blockID, groupKey, sampleSize, _efk_ParentTest, Clientname, _fk_Session, _date) "
            + " select ${testkey}, grp, block, grpkey, 0, ${parentTest}, ${clientname}, ${session}, now(3) "
            + " from ${itemsTableName} where selected = 1 and not exists "
            + " (select * from ft_groupsamples where _efk_AdminSubject = ${testkey} and groupKey = grpkey "
            + "  and _efk_ParentTest = ${parentTest} and _fk_Session = ${session})";
        Map<String, String> unquotedParms22 = unquotedParms;
        SqlParametersMaps parms21 = new SqlParametersMaps ().put ("testkey", testKey).put ("parentTest", parentTest).put ("clientname", clientName).put ("session", session);
        executeStatement (connection, fixDataBaseNames (SQL_INSERT4, unquotedParms22), parms21, false).getUpdateCount ();

        final String SQL_UPDATE8 = "update ft_groupsamples F, ${itemsTableName} I set F.SampleSize = F.Samplesize + 1 where I.selected = 1 and F._efk_AdminSubject = ${testkey} " +
            " and F.groupkey = I.grpkey and F._fk_Session = ${session};";
        Map<String, String> unquotedParms23 = unquotedParms;
        SqlParametersMaps parms22 = new SqlParametersMaps ().put ("testkey", testKey).put ("session", session);
        executeStatement (connection, fixDataBaseNames (SQL_UPDATE8, unquotedParms23), parms22, false).getUpdateCount ();
      }
    } catch (Exception re) {
      msg = re.getMessage ();
      _commonDll._LogDBError_SP (connection, "_FT_SelectItemgroups", msg, testee, testKey, null, testOppKey);
    }

    if (debug > 0) {
      final String SQL_QUERY21 = "select 'final selection', * from ${itemsTableName} order by  selected desc, position;";
      Map<String, String> unquotedParms25 = unquotedParms;
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY21, unquotedParms25), null, false).getResultSets ().next ();
      resultsets.add (result);

      final String SQL_QUERY22 = "select 'final cohorts', * from  ${cohortsTableName};";
      Map<String, String> unquotedParms26 = unquotedParms1;
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY22, unquotedParms26), null, false).getResultSets ().next ();
      resultsets.add (result);
    }
    _commonDll._LogDBLatency_SP (connection, "_FT_SelectItemgroups", now, testee, true, null, testOppKey, null, clientName, null);
    connection.dropTemporaryTable (itemsTable);
    connection.dropTemporaryTable (cohortsTable);

    return new MultiDataResultSet (resultsets);
  }

  /**
   * @param connection
   * @param clientName
   * @param oppKey
   * @param segmentKey
   * @param testId
   * @param fieldTest
   * @param session
   * @return
   * @throws ReturnStatusException
   */
  public DataBaseTable _AA_SIM_TestoppItempool_FN (SQLConnection connection, String clientName, UUID oppKey, String segmentKey, String testId, Boolean fieldTest, UUID session)
      throws ReturnStatusException {

    DataBaseTable sim_testOppItempoolTable = getDataBaseTable ("sim_testOppItempool").addColumn ("itemkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150)
        .addColumn ("groupKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("groupID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("blockID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 10)
        .addColumn ("IRT_b", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150).addColumn ("isActive", SQL_TYPE_To_JAVA_TYPE.BIT);
    connection.createTemporaryTable (sim_testOppItempoolTable);

    final String SQL_INSERT = "insert into ${tblName} (itemkey, groupKey ,groupID, blockID, IRT_b, isActive)"
        + " select I._fk_ITem as itemkey, I.groupKey, I.groupID, I.blockID, I.IRT_b, SI.isActive "
        + " from ${ItemBankDB}.tblsetofadminitems I, ${ConfigDB}.client_test_itemconstraint C1, testeeaccommodations A1, ${ItemBankDB}.tblitemprops P1, sim_segmentitem SI "
        + "    where I._fk_AdminSUbject = ${segmentKey} and SI._fk_Session = ${session} and SI._efk_Segment = ${segmentKey}"
        + " and SI._efk_Item = I._fk_Item and SI.isActive = 1 and (${fieldTest} is null or SI.isFieldTest = ${fieldTest}) and C1.Clientname = ${clientname} and C1.testID = ${testID}"
        + " and C1.item_in = 1 and A1._fk_TestOpportunity = ${oppkey} and A1.AccType = C1.ToolType and A1.AccCode = C1.ToolValue and P1._fk_AdminSubject = ${segmentKey} "
        + " and P1._fk_Item  = I._fk_Item and P1.Propname = C1.propname and P1.Propvalue = C1.Propvalue "
        + " and not exists"
        + " (select * from ${ConfigDB}.client_test_itemconstraint C2, testeeaccommodations A2, ${ItemBankDB}.tblitemprops P2 where A2._fk_TestOpportunity = ${oppkey} "
        + " and C2.Clientname = ${clientname} and C2.testID = ${testID} and C2.item_in = 0 and A2.AccType = C2.ToolType and A2.AccCode = C2.ToolValue and "
        + " P2._fk_AdminSubject = ${segmentKey} and P2._fk_Item  = I._fk_Item and P2.Propname = C2.propname and P2.Propvalue = C2.Propvalue) "
        + " group by I._fk_ITem, I.groupKey, I.groupID, I.blockID, I.IRT_b, SI.isActive having count(*) = (select count(*) from ${ConfigDB}.client_test_itemconstraint C1,"
        + " testeeaccommodations A1  where C1.Clientname = ${clientname} and C1.testID = ${testID} and C1.item_in = 1 "
        + " and A1._fk_TestOpportunity = ${oppkey} and A1.AccType = C1.ToolType and A1.AccCode = C1.ToolValue)";

    String query = fixDataBaseNames (SQL_INSERT);
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("tblName", sim_testOppItempoolTable.getTableName ());
    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("clientname", clientName).put ("oppkey", oppKey).put ("segmentKey", segmentKey).put ("testID", testId)
        .put ("fieldTest", fieldTest).put ("session", session);
    executeStatement (connection, fixDataBaseNames (query, unquotedParms), parameters, true).getUpdateCount ();

    return sim_testOppItempoolTable;
  }

  /**
   * 
   * @param connection
   * @param oppKey
   * @param testKey
   * @throws ReturnStatusException
   */

  public SingleDataResultSet _FT_Prioritize_2012_SP (SQLConnection connection, UUID oppKey, String testKey) throws ReturnStatusException {
    return _FT_Prioritize_2012_SP (connection, oppKey, testKey, 0);
  }

  public SingleDataResultSet _FT_Prioritize_2012_SP (SQLConnection connection, UUID oppKey, String testKey, Integer debug) throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);
    String clientName = null;
    UUID session = null;
    Long testee = null;
    String testId = null;
    Integer tier = null;
    Integer maxtiers = null;
    Boolean isSim = IsSimulation_FN (connection, oppKey);

    final String SQL_QUERY1 = "select clientname, _fk_Session as session, _efk_Testee as testee, _efk_TestID as testID from testopportunity where _Key = ${oppkey};";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("oppkey", oppKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      clientName = record.<String> get ("clientname");
      session = record.<UUID> get ("session");
      testee = record.<Long> get ("testee");
      testId = record.<String> get ("testID");
    }

    DataBaseTable p_itemsTable = getDataBaseTable ("p_items").addColumn ("grpkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 60).addColumn ("groupID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50)
        .addColumn ("blockID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("activeItems", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("tier", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("admins", SQL_TYPE_To_JAVA_TYPE.INT);

    DataBaseTable groupsTable = getDataBaseTable ("groups").addColumn ("gkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 60).addColumn ("GID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50)
        .addColumn ("BID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("lang", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("active", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (p_itemsTable);
    connection.createTemporaryTable (groupsTable);

    DataBaseTable toipTable = _AA_TestoppItempool_FN (connection, clientName, oppKey, testKey, testId, true);
    DataBaseTable stoipTable = _AA_SIM_TestoppItempool_FN (connection, clientName, oppKey, testKey, testId, true, session);

    if (DbComparator.isEqual (isSim, false)) {
      final String SQL_INSERT1 = "insert into ${groupsTable} (gkey, GID, BID, active) select groupKey, groupID, blockID, count(*) as itemCount " +
          " from ${toipTableName} where isActive = 1 group by groupKey, groupID, blockID;";
      Map<String, String> unquotedParms = new HashMap<String, String> ();
      unquotedParms.put ("groupsTable", groupsTable.getTableName ());
      unquotedParms.put ("toipTableName", toipTable.getTableName ());
      executeStatement (connection, fixDataBaseNames (SQL_INSERT1, unquotedParms), null, false).getUpdateCount ();
    } else {
      final String SQL_INSERT2 = "insert into ${groupsTable} (gkey, GID, BID, active) select groupKey, groupID, blockID, count(*) as itemCount" +
          " from ${stoipTableName} where isActive = 1 group by groupKey, groupID, blockID;";
      Map<String, String> unquotedParms1 = new HashMap<String, String> ();
      unquotedParms1.put ("groupsTable", groupsTable.getTableName ());
      unquotedParms1.put ("stoipTableName", stoipTable.getTableName ());
      executeStatement (connection, fixDataBaseNames (SQL_INSERT2, unquotedParms1), null, false).getUpdateCount ();
    }

    final String SQL_INDEX = "create index _ix_ftgroups on ${groupsTable} (gkey);";
    Map<String, String> unquotedparms = new HashMap<String, String> ();
    unquotedparms.put ("groupsTable", groupsTable.getTableName ());
    executeStatement (connection, fixDataBaseNames (SQL_INDEX, unquotedparms), null, false);

    if (debug > 0) {
      final String SQL_QUERY2 = "select * from ${groupsTable};";
      Map<String, String> unquotedParms2 = new HashMap<String, String> ();
      unquotedParms2.put ("groupsTable", groupsTable.getTableName ());
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY2, unquotedParms2), null, false).getResultSets ().next ();
      return result;
    }
    tier = 1;
    if (DbComparator.isEqual (isSim, false)) {
      final String SQL_INSERT3 = "insert into ${p_itemsTable} (grpkey, groupID, blockID, activeItems, tier, admins)" +
          " select  gkey, GID, BID, active, ${tier}, count(*) as admins  from ft_opportunityitem O, ${groupsTable} G where O._efk_FieldTest = ${testkey} " +
          " and G.active > 0 and O.groupkey = G.gkey and coalesce(O.deleted, 0) = 0  group by gkey, GID, BID, lang, active;";
      Map<String, String> unquotedParms3 = new HashMap<String, String> ();
      unquotedParms3.put ("groupsTable", groupsTable.getTableName ());
      unquotedParms3.put ("p_itemsTable", p_itemsTable.getTableName ());
      SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("tier", tier).put ("testkey", testKey);
      executeStatement (connection, fixDataBaseNames (SQL_INSERT3, unquotedParms3), parms2, true).getUpdateCount ();
    } else {
      final String SQL_INSERT4 = "insert into ${p_itemsTable} (grpkey, groupID, blockID, activeItems, tier, admins)"
          + " select  gkey, GID, BID, active, ${tier}, count(*) as admins from ft_opportunityitem O,  ${groupsTable} G where O._efk_FieldTest = ${testkey} "
          + " and G.active > 0 and O.groupkey = G.gkey and coalesce(O.deleted, 0) = 0 and _fk_Session = ${session} group by gkey, GID, BID, lang, active;";
      Map<String, String> unquotedParms4 = new HashMap<String, String> ();
      unquotedParms4.put ("groupsTable", groupsTable.getTableName ());
      unquotedParms4.put ("p_itemsTable", p_itemsTable.getTableName ());
      SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("tier", tier).put ("testkey", testKey).put ("session", session);
      executeStatement (connection, fixDataBaseNames (SQL_INSERT4, unquotedParms4), parms3, true).getUpdateCount ();
    }

    final String SQL_INDEX1 = "create index _ix_ftitems on ${p_itemsTable} (grpkey);";
    Map<String, String> unquotedparms1 = new HashMap<String, String> ();
    unquotedparms1.put ("p_itemsTable", p_itemsTable.getTableName ());
    executeStatement (connection, fixDataBaseNames (SQL_INDEX1, unquotedparms1), null, false);

    tier = tier + 1;
    // TODO Elena: need to find out more if these two statements are the same
    // final String SQL_INSERT5 =
    // "insert into ${p_itemsTable} (grpkey, groupID, blockID,  activeItems, tier, admins) "
    // +
    // " select gkey, GID, BID,  active, ${tier}, 0 from ${groupsTable}  where not exists (select * from ${p_itemsTable} where grpkey = gkey)";
    final String SQL_INSERT5 = "insert IGNORE into ${p_itemsTable} (grpkey, groupID, blockID,  activeItems, tier, admins) "
        + " select gkey, GID, BID,  active, ${tier}, 0 from ${groupsTable} ";

    Map<String, String> unquotedParms6 = new HashMap<String, String> ();
    unquotedParms6.put ("groupsTable", groupsTable.getTableName ());
    unquotedParms6.put ("p_itemsTable", p_itemsTable.getTableName ());
    SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("tier", tier);
    executeStatement (connection, fixDataBaseNames (SQL_INSERT5, unquotedParms6), parms4, false).getUpdateCount ();

    _commonDll._LogDBLatency_SP (connection, "_FT_Prioritize_2012", starttime, null, true, null, oppKey, null, clientName, null);

    final String SQL_QUERY4 = "select grpkey, groupID, blockID, activeItems, tier, admins from ${p_itemsTable} order by tier, admins, RAND()";
    Map<String, String> unquotedParms7 = unquotedParms6;

    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY4, unquotedParms7), null, false).getResultSets ().next ();

    connection.dropTemporaryTable (groupsTable);
    connection.dropTemporaryTable (p_itemsTable);
    connection.dropTemporaryTable (stoipTable);
    connection.dropTemporaryTable (toipTable);

    return result;
  }

  /**
   * @param connection
   * @param clientname
   * @param testId
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   */

  public DataBaseTable GetTestFormWindows_FN (SQLConnection connection, String clientname, String testId, Integer sessionType) throws ReturnStatusException {
    Date now = _dateUtil.getDateWRetStatus (connection);
    long startTime = System.currentTimeMillis ();
    DataBaseTable tbl = getDataBaseTable ("testformwindows").addColumn ("windowID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("windowMax", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("modeMax", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("startDate", SQL_TYPE_To_JAVA_TYPE.DATETIME).addColumn ("endDate", SQL_TYPE_To_JAVA_TYPE.DATETIME).
        addColumn ("formStart", SQL_TYPE_To_JAVA_TYPE.DATETIME).addColumn ("formEnd", SQL_TYPE_To_JAVA_TYPE.DATETIME).addColumn ("formKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).
        addColumn ("formId", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200).addColumn ("language", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 25).addColumn ("mode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).
        addColumn ("testKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).addColumn ("windowSession", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("modeSession", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (tbl);

    final String SQL_INSERT =
        "insert into ${tblName}(windowId, windowmax, modemax, startDate, endDate, formStart, formEnd,formKey, formId,language,mode, testKey,windowSession,modesession) "
            + "(select  windowID, W.numopps as windowMax, M.maxopps as modeMax "
            + " , case when W.startDate is null then ${now} else (W.startDate + INTERVAL shiftWindowStart DAY) end as startDate "
            + " , case when W.endDate is null then ${now} else (W.endDate + INTERVAL shiftWindowEnd DAY) end  as endDate "
            + " , case when F.startDate is null then ${now} else ( F.startdate + INTERVAL shiftFormStart DAY) end as formStart "
            + " , case when F.enddate is null then ${now} else (F.enddate + INTERVAL shiftFormEnd DAY ) end as formEnd "
            + "  , _efk_TestForm as formKey, FormID, F.Language, M.mode "
            + "  , M.testkey, W.sessionType as windowSession, M.sessionType as modeSession "
            + " from ${ConfigDB}.client_testwindow W, ${ConfigDB}.client_testformproperties F, ${ConfigDB}.client_testmode M, ${ItemBankDB}.tblsetofadminsubjects BANK, _externs E"
            + " where F.clientname = ${clientname} and F.testID = ${testID} "
            + "   and M.testkey = F.testkey and M.testkey = BANK._Key "
            + "   and M.clientname = ${clientname} and M.testID = ${testID} and (M.sessionType = -1 or M.sessionType = ${sessionType}) "
            + "    and E.clientname = ${clientname} "
            + "    and ${now} between case when F.startDate is null then ${now} else (F.startdate + INTERVAL shiftFormStart DAY) end "
            + "                     and case when F.enddate is null then ${now} else (F.enddate + INTERVAL shiftFormEnd DAY) end "
            + "    and ${now} between  case when W.startDate is null then ${now} else (W.startDate + INTERVAL shiftWindowStart DAY) end "
            + "                     and case when W.endDate is null then ${now} else (W.endDate + INTERVAL shiftWindowEnd DAY ) end "
            + "    and W.clientname = ${clientname} and W.testID = ${testID} "
            + "    and (W.sessionType = -1 or W.sessionType = ${sessionType})) "
            + " union "
            + " (select  windowID, W.numopps, M.maxopps "
            + ", case when W.startDate is null then ${now} else  (W.startDate + INTERVAL shiftWindowStart DAY) end  as startDate "
            + ", case when W.endDate is null then ${now} else (W.endDate + INTERVAL shiftWindowEnd DAY) end  as endDate "
            + ", case when F.startDate is null then ${now} else ( F.startdate + INTERVAL shiftFormStart DAY) end as formStart "
            + "        , case when F.enddate is null then ${now} else (F.enddate + INTERVAL shiftFormEnd DAY ) end as formEnd "
            + ", _efk_TestForm as formKey, FormID, F.Language, M.mode, M.testkey as TestKey, W.sessionType , M.sessionType "
            + " from ${ConfigDB}.client_testwindow W, ${ConfigDB}.client_testformproperties F, ${ConfigDB}.client_segmentproperties S "
            + "    , ${ConfigDB}.client_testmode M, ${ItemBankDB}.tblsetofadminsubjects BANK, _externs E "
            + " where S.clientname = ${clientname} and F.clientname = ${clientname} and F.Testkey = BANK._Key and S.parentTest = ${testID} "
            + "    and M.clientname = ${clientname} and M.testID = ${testID} and (M.sessionType = -1 or M.sessionType = ${sessionType}) "
            + "    and S.modekey = M.testkey and S.segmentID = BANK.TestID  and E.clientname = ${clientname} "
            + "    and ${now} between case when F.startDate is null then ${now} else (F.startdate + INTERVAL shiftFormStart DAY) end "
            + "                     and case when F.enddate is null then ${now} else (F.enddate + INTERVAL shiftFormEnd DAY) end "
            + "    and ${now} between  case when W.startDate is null then ${now} else (W.startDate + INTERVAL shiftWindowStart DAY) end "
            + "                     and case when W.endDate is null then ${now} else (W.endDate + INTERVAL shiftWindowEnd DAY ) end "
            + "    and W.clientname = ${clientname} and W.testID = S.parentTest and (W.sessionType = -1 or W.sessionType = ${sessionType}))";

    SqlParametersMaps parms = (new SqlParametersMaps ()).put ("now", now).put ("clientname", clientname).put ("testID", testId).
        put ("sessionType", sessionType);
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("tblName", tbl.getTableName ());

    final String query = fixDataBaseNames (SQL_INSERT);
    executeStatement (connection, fixDataBaseNames (query, unquotedParms), parms, false).getUpdateCount ();
    //_logger.info ("<<<<<<<<< GetTestFormWindows_FN  Total Execution Time : "+((System.currentTimeMillis ()-startTime)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    return tbl;
  }

  public SingleDataResultSet _GetTesteeTestForms_SP (SQLConnection connection, String clientname, String testId, Long testee, Integer sessionType) throws ReturnStatusException {
    return _GetTesteeTestForms_SP (connection, clientname, testId, testee, sessionType, null);
  }

  public SingleDataResultSet _GetTesteeTestForms_SP (SQLConnection connection, String clientname, String testId, Long testee, Integer sessionType,
      String formList) throws ReturnStatusException {
    long startTimeLong = System.currentTimeMillis ();
    Date starttime = _dateUtil.getDateWRetStatus (connection);
    SingleDataResultSet result = null;

    DataBaseTable tbl = GetTestFormWindows_FN (connection, clientname, testId, sessionType);
    //_logger.info ("<<<<<<<<< _GetTesteeTestForms_SP 1 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("tblName", tbl.getTableName ());

    if (testee < 0) { // if 'guest' testees are allowed into the system, then
                      // they qualify for all forms by default since there is no
                      // RTS data for them
      final String SQL_QUERY1 = "select windowID, windowMax, startDate, endDate, formkey, mode, modeMax, testkey from ${tblName}";
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY1, unquotedParms), null, false).getResultSets ().next ();
      connection.dropTemporaryTable (tbl);
      return result;
    }
    //_logger.info ("<<<<<<<<< _GetTesteeTestForms_SP 2 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    String tideId = null, formField = null;
    Boolean requireFormWindow = null, requireForm = null, ifexists = null;

    final String SQL_QUERY2 = "select TIDE_ID as tideId, requireRTSFormWindow as requireFormWindow, RTSFormField as formField,"
        + " requireRTSForm as requireForm, requireRTSformIfExists as ifexists "
        + " from ${ConfigDB}.client_testproperties T, ${ConfigDB}.client_testmode F "
        + " where T.clientname = ${clientname} and T.TestID = ${testID} and F.clientname = ${clientname} and F.testID = ${testID} "
        + " and (sessionType = -1 or sessionTYpe = ${sessionType})";

    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testID", testId).put ("sessionType", sessionType);
    SingleDataResultSet result2 = executeStatement (connection, fixDataBaseNames (SQL_QUERY2), parms2, false).getResultSets ().next ();
    DbResultRecord record2 = (result2.getCount () > 0 ? result2.getRecords ().next () : null);
    if (record2 != null) {
      tideId = record2.<String> get ("tideID");
      formField = record2.<String> get ("formField");
      requireFormWindow = record2.<Boolean> get ("requireFormWindow");
      requireForm = record2.<Boolean> get ("requireForm");
      ifexists = record2.<Boolean> get ("ifexists");

    }
    //_logger.info ("<<<<<<<<< _GetTesteeTestForms_SP 3 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    if (formList != null) {
      if (formList.indexOf (':') > -1)
        requireFormWindow = true;
      else {
        requireForm = true;
        requireFormWindow = false;
      }
    } else if (tideId != null && formField != null) {
      _Ref<String> formListRef = new _Ref<String> ();

      _rtsDll._GetRTSAttribute_SP (connection, clientname, testee, formField, formListRef);
      formList = formListRef.get ();
    }
    //_logger.info ("<<<<<<<<< _GetTesteeTestForms_SP 4 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    // forms table ( WID varchar(100), form varchar(50));
    DataBaseTable formsTbl = getDataBaseTable ("gttfForms").addColumn ("WID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("form", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50);
    connection.createTemporaryTable (formsTbl);
    //_logger.info ("<<<<<<<<< _GetTesteeTestForms_SP 5 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    if (formList != null && tideId != null) {
      Character delim = ';';
      final String[] rows = _commonDll._BuildTableAsArray (formList, delim.toString (), -1);

      executeMethodAndInsertIntoTemporaryTable (connection, new AbstractDataResultExecutor ()
      {
        @Override
        public SingleDataResultSet execute (SQLConnection connection) throws ReturnStatusException {

          List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();

          for (String row : rows)
          {
            CaseInsensitiveMap<Object> record = new CaseInsensitiveMap<Object> ();
            int idx = 0;
            if ((idx = row.indexOf (":")) > -1) {// i.e. found
              String wid = row.substring (0, idx);
              String form = row.substring (idx + 1);
              record.put ("WID", wid);
              record.put ("form", form);
            } else {
              record.put ("form", row);
              record.put ("WID", null);
            }
            resultList.add (record);
          }
          SingleDataResultSet rs = new SingleDataResultSet ();
          rs.addColumn ("WID", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
          rs.addColumn ("form", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
          rs.addRecords (resultList);

          return rs;
        }
      }, formsTbl, false); // temp table already created
    }
    //_logger.info ("<<<<<<<<< _GetTesteeTestForms_SP 6 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    Map<String, String> unquotedParms3 = new HashMap<String, String> ();
    unquotedParms3.put ("formsTblName", formsTbl.getTableName ());
    unquotedParms3.put ("tblName", tbl.getTableName ());

    final String SQL_QUERY5 = "select  form from ${formsTblName}, ${tblName} where form = formkey limit 1";
    Map<String, String> unquotedParms5 = unquotedParms3;
    
    if (DbComparator.isEqual (requireFormWindow, true)) {
      final String SQL_QUERY3 = "select distinct windowID, windowMax, startDate, endDate, formkey, mode, modeMax, testkey "
          + " from ${formsTblName}, ${tblName} where WID = windowID and form = formkey";

      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY3, unquotedParms3), null, false).getResultSets ().next ();
      //_logger.info ("<<<<<<<<< _GetTesteeTestForms_SP 7 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    } else if (DbComparator.isEqual (requireForm, true)) {
      final String SQL_QUERY4 = "select distinct windowID, windowMax, startDate, endDate, formkey, mode, modeMax, testkey "
          + " from  ${formsTblName} join  ${tblName} on form = formkey ";
      Map<String, String> unquotedParms4 = unquotedParms3;

      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY4, unquotedParms4), null, false).getResultSets ().next ();
      //_logger.info ("<<<<<<<<< _GetTesteeTestForms_SP 8 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    } else if (DbComparator.isEqual (ifexists, true) && exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY5, unquotedParms5), null, false)) == true) {
      final String SQL_QUERY6 = "select distinct windowID, windowMax, startDate, endDate, formkey, mode, modeMax, testkey "
          + " from  ${formsTblName} join  ${tblName} on form = formkey ";
      Map<String, String> unquotedParms6 = unquotedParms3;

      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY6, unquotedParms6), null, false).getResultSets ().next ();
      //_logger.info ("<<<<<<<<< _GetTesteeTestForms_SP 9 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    } else {
      final String SQL_QUERY7 = " select windowID, windowMax, startDate, endDate, formkey, mode, modeMax, testkey from  ${tblName}";
      Map<String, String> unquotedParms7 = unquotedParms;

      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY7, unquotedParms7), null, false).getResultSets ().next ();
      //_logger.info ("<<<<<<<<< _GetTesteeTestForms_SP 10 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    }
    //_logger.info ("<<<<<<<<< _GetTesteeTestForms_SP 11 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    connection.dropTemporaryTable (tbl);
    connection.dropTemporaryTable (formsTbl);
    //_logger.info ("<<<<<<<<< _GetTesteeTestForms_SP Total Execution Time : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    _commonDll._LogDBLatency_SP (connection, "_GetTesteeTestForms", starttime, testee, true, null, null);
    return result;
  }

  // This is only for testing latencies of _SelectTestForm_Predetermined_SP
  // parts
  // call to GetRtsAttribute was moved to modified version of
  // _SelectTestForm_Predetermined_SP
  public SingleDataResultSet _GetTesteeTestForms_SPV1 (SQLConnection connection, String clientname, String testId, Long testee, Integer sessionType,
      String formList) throws ReturnStatusException {

    SingleDataResultSet result = null;

    DataBaseTable tbl = GetTestFormWindows_FN (connection, clientname, testId, sessionType);
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("tblName", tbl.getTableName ());

    if (testee < 0) { // if 'guest' testees are allowed into the system, then
                      // they qualify for all forms by default since there is no
                      // RTS data for them
      final String SQL_QUERY1 = "select windowID, windowMax, startDate, endDate, formkey, mode, modeMax, testkey from ${tblName}";
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY1, unquotedParms), null, false).getResultSets ().next ();
      connection.dropTemporaryTable (tbl);
      return result;
    }

    String tideId = null;
    Boolean requireFormWindow = null, requireForm = null, ifexists = null;

    final String SQL_QUERY2 = "select TIDE_ID as tideId, requireRTSFormWindow as requireFormWindow, RTSFormField as formField,"
        + " requireRTSForm as requireForm, requireRTSformIfExists as ifexists "
        + " from ${ConfigDB}.client_testproperties T, ${ConfigDB}.client_testmode F "
        + " where T.clientname = ${clientname} and T.TestID = ${testID} and F.clientname = ${clientname} and F.testID = ${testID} "
        + " and (sessionType = -1 or sessionTYpe = ${sessionType})";

    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testID", testId).put ("sessionType", sessionType);
    SingleDataResultSet result2 = executeStatement (connection, fixDataBaseNames (SQL_QUERY2), parms2, false).getResultSets ().next ();
    DbResultRecord record2 = (result2.getCount () > 0 ? result2.getRecords ().next () : null);
    if (record2 != null) {
      tideId = record2.<String> get ("tideID");
      requireFormWindow = record2.<Boolean> get ("requireFormWindow");
      requireForm = record2.<Boolean> get ("requireForm");
      ifexists = record2.<Boolean> get ("ifexists");

    }

    if (formList != null) {
      if (formList.indexOf (':') > -1)
        requireFormWindow = true;
      else {
        requireForm = true;
        requireFormWindow = false;
      }
    }
    // forms table ( WID varchar(100), form varchar(50));
    DataBaseTable formsTbl = getDataBaseTable ("gttfForms").addColumn ("WID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("form", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50);
    connection.createTemporaryTable (formsTbl);

    if (formList != null && tideId != null) {
      Character delim = ';';
      final String[] rows = _commonDll._BuildTableAsArray (formList, delim.toString (), -1);

      executeMethodAndInsertIntoTemporaryTable (connection, new AbstractDataResultExecutor ()
      {
        @Override
        public SingleDataResultSet execute (SQLConnection connection) throws ReturnStatusException {

          List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();

          for (String row : rows)
          {
            CaseInsensitiveMap<Object> record = new CaseInsensitiveMap<Object> ();
            int idx = 0;
            if ((idx = row.indexOf (":")) > -1) {// i.e. found
              String wid = row.substring (0, idx);
              String form = row.substring (idx + 1);
              record.put ("WID", wid);
              record.put ("form", form);
            } else {
              record.put ("form", row);
              record.put ("WID", null);
            }
            resultList.add (record);
          }
          SingleDataResultSet rs = new SingleDataResultSet ();
          rs.addColumn ("WID", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
          rs.addColumn ("form", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
          rs.addRecords (resultList);

          return rs;
        }
      }, formsTbl, false); // temp table already created
    }

    Map<String, String> unquotedParms3 = new HashMap<String, String> ();
    unquotedParms3.put ("formsTblName", formsTbl.getTableName ());
    unquotedParms3.put ("tblName", tbl.getTableName ());

    final String SQL_QUERY5 = "select  form from ${formsTblName}, ${tblName} where form = formkey limit 1";
    Map<String, String> unquotedParms5 = unquotedParms3;

    if (DbComparator.isEqual (requireFormWindow, true)) {
      final String SQL_QUERY3 = "select distinct windowID, windowMax, startDate, endDate, formkey, mode, modeMax, testkey "
          + " from ${formsTblName}, ${tblName} where WID = windowID and form = formkey";

      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY3, unquotedParms3), null, false).getResultSets ().next ();

    } else if (DbComparator.isEqual (requireForm, true)) {
      final String SQL_QUERY4 = "select distinct windowID, windowMax, startDate, endDate, formkey, mode, modeMax, testkey "
          + " from  ${formsTblName} join  ${tblName} on form = formkey ";
      Map<String, String> unquotedParms4 = unquotedParms3;

      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY4, unquotedParms4), null, false).getResultSets ().next ();

    } else if (DbComparator.isEqual (ifexists, true) && exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY5, unquotedParms5), null, false)) == true) {
      final String SQL_QUERY6 = "select distinct windowID, windowMax, startDate, endDate, formkey, mode, modeMax, testkey "
          + " from  ${formsTblName} join  ${tblName} on form = formkey ";
      Map<String, String> unquotedParms6 = unquotedParms3;

      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY6, unquotedParms6), null, false).getResultSets ().next ();

    } else {
      final String SQL_QUERY7 = " select windowID, windowMax, startDate, endDate, formkey, mode, modeMax, testkey from  ${tblName}";
      Map<String, String> unquotedParms7 = unquotedParms;

      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY7, unquotedParms7), null, false).getResultSets ().next ();
    }
    connection.dropTemporaryTable (tbl);
    connection.dropTemporaryTable (formsTbl);
    return result;
  }

  public void _SelectTestForm_Predetermined_SP (SQLConnection connection, UUID oppkey, String testkey, String lang,
      _Ref<String> formkeyRef, _Ref<String> formIdRef, _Ref<Integer> itemcnt, Integer sessionType) throws ReturnStatusException {

    _SelectTestForm_Predetermined_SP (connection, oppkey, testkey, lang, formkeyRef, formIdRef, itemcnt, sessionType, null);
  }

  // TODO this is a test version, to determine latencies of this method parts
  // Call to GetRtsAttribute was moved here from _GetTesteeTestForms_SPV1
  public void _SelectTestForm_Predetermined_SPV1 (SQLConnection connection, UUID oppkey, String testkey, String lang,
      _Ref<String> formkeyRef, _Ref<String> formIdRef, _Ref<Integer> itemcntRef, Integer sessionType, String formList) throws ReturnStatusException {

    // Date now = _dateUtil.getDateWRetStatus (connection);

    Date start = null, end = null;
    Long diff = 0L;
    start = new Date ();
    String clientname = null;
    UUID session = null;
    Long testee = null;
    String testId = null;

    final String SQL_QUERY1 = "select clientname, _efk_Testee, _efk_TestID, _fk_Session from testopportunity where _key = ${oppkey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      clientname = record.<String> get ("clientname");
      testee = record.<Long> get ("_efk_Testee");
      testId = record.<String> get ("_efk_TestID");
      session = record.<UUID> get ("_fk_Session");
    }

    // no RTS data for guests testees anyway
    if (formList == null && testee >= 0) {
      String tideId = null, formField = null;

      final String SQL_QUERY2 = "select TIDE_ID as tideId,  RTSFormField as formField "
          + " from ${ConfigDB}.client_testproperties T, ${ConfigDB}.client_testmode F "
          + " where T.clientname = ${clientname} and T.TestID = ${testID} and F.clientname = ${clientname} and F.testID = ${testID} "
          + " and (sessionType = -1 or sessionTYpe = ${sessionType})";

      SqlParametersMaps params2 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testID", testId).put ("sessionType", sessionType);
      SingleDataResultSet result2 = executeStatement (connection, fixDataBaseNames (SQL_QUERY2), params2, false).getResultSets ().next ();
      DbResultRecord record2 = (result2.getCount () > 0 ? result2.getRecords ().next () : null);
      if (record2 != null) {
        tideId = record2.<String> get ("tideID");
        formField = record2.<String> get ("formField");
      }
      if (tideId != null && formField != null) {
        _Ref<String> formListRef = new _Ref<String> ();

        // _rtsDll._GetRTSAttribute_SP (connection, clientname, testee,
        // formField, formListRef);
        formListRef.set (null);
        formList = formListRef.get ();
      }
    }
    end = new Date ();
    diff = end.getTime () - start.getTime ();
    System.err.println (String.format ("_SelectTestForm_Predetermined part1 latency: %d millisec", (long) diff));

    start = new Date ();
    String environment = _commonDll.getExternsColumnByClientName (connection, clientname, "environment");

    DataBaseTable assignedTbl = getDataBaseTable ("stfpAssigned").addColumn ("testkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).addColumn ("window", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).
        addColumn ("windowMax", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("mode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("modeMax", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("startdate", SQL_TYPE_To_JAVA_TYPE.DATETIME).addColumn ("enddate", SQL_TYPE_To_JAVA_TYPE.DATETIME).
        addColumn ("frmKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("cnt", SQL_TYPE_To_JAVA_TYPE.INT);
    final Integer sessionTypeFinal = sessionType;
    final String formListFinal = formList;
    final String clientnameFinal = clientname;
    final Long testeeFinal = testee;
    final String testIdFinal = testId;
    executeMethodAndInsertIntoTemporaryTable (connection, new AbstractDataResultExecutor ()
    {
      @Override
      public SingleDataResultSet execute (SQLConnection connection) throws ReturnStatusException {
        SingleDataResultSet resultSet = _GetTesteeTestForms_SPV1 (connection, clientnameFinal, testIdFinal, testeeFinal, sessionTypeFinal, formListFinal);
        // windowID, windowMax, startDate, endDate, formkey, mode, modeMax,
        // testkey

        resultSet.resetColumnName (1, "window");
        resultSet.resetColumnName (5, "frmkey");
        resultSet.addColumn ("cnt", SQL_TYPE_To_JAVA_TYPE.INT);
        Iterator<DbResultRecord> records = resultSet.getRecords ();
        while (records.hasNext ()) {
          DbResultRecord record = records.next ();
          record.addColumnValue ("cnt", 0);
        }

        return resultSet;
      }
    }, assignedTbl, true);

    final String SQL_UPDATE2 = "update ${assignedTblName} set cnt =  (select count(*) from testopportunity O, testopportunitysegment S "
        + " where clientname = ${clientname} and _efk_Testee = ${testee} and S._fk_TestOpportunity = O._key and formkey = frmkey)";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testee", testee);
    Map<String, String> unquotedParms2 = new HashMap<String, String> ();
    unquotedParms2.put ("assignedTblName", assignedTbl.getTableName ());

    executeStatement (connection, fixDataBaseNames (SQL_UPDATE2, unquotedParms2), parms2, false).getUpdateCount ();

    DataBaseTable formsTbl = getDataBaseTable ("stfpForms").addColumn ("_formkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("id", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150).
        addColumn ("itemcnt", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("formcnt", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (formsTbl);

    final String SQL_INSERT3 = "insert into ${formTblName} (_formkey, id, formcnt, itemcnt) select F._Key, F.FormID, 0, "
        + " (select count(*) from ${ItemBankDB}.testformitem I where I._fk_AdminSubject = ${testkey} and I._fk_TestForm = F._Key) "
        + " from ${ItemBankDB}.testform F where F.Language = ${lang} and F._fk_AdminSubject = ${testkey}";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("testkey", testkey).put ("lang", lang);
    Map<String, String> unquotedParms3 = new HashMap<String, String> ();
    unquotedParms3.put ("formTblName", formsTbl.getTableName ());

    final String query3 = fixDataBaseNames (SQL_INSERT3);
    executeStatement (connection, fixDataBaseNames (query3, unquotedParms3), parms3, false).getUpdateCount ();

    final String SQL_UPDATE4 = "update  ${formTblName} set formcnt = (select count(*)  from testopportunity, testopportunitysegment "
        + " where clientname = ${clientname} and _fk_TestOpportunity = _Key and _efk_Segment = ${testkey} and FormKey = _formkey "
        + "      and (${environment} <> 'SIMULATION' or _fk_Session = ${session}));";
    Map<String, String> unquotedParms4 = unquotedParms3;
    SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("testkey", testkey).put ("clientname", clientname).put ("environment", environment).put ("session", session);

    executeStatement (connection, fixDataBaseNames (SQL_UPDATE4, unquotedParms4), parms4, true).getUpdateCount ();

    final String SQL_QUERY5 = "select  ID as formId, itemcnt, _formkey as formkey from ${formTblName} F, ${assignedTblName} A "
        + " where F._formkey = A.frmkey order by A.cnt, formcnt, rand()  limit 1";
    Map<String, String> unquotedParms5 = new HashMap<String, String> ();
    unquotedParms5.put ("formTblName", formsTbl.getTableName ());
    unquotedParms5.put ("assignedTblName", assignedTbl.getTableName ());

    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY5, unquotedParms5), null, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      formkeyRef.set (record.<String> get ("formkey"));
      formIdRef.set (record.<String> get ("formId"));
      itemcntRef.set (record.<Integer> get ("itemcnt"));
    }
    connection.dropTemporaryTable (formsTbl);
    connection.dropTemporaryTable (assignedTbl);
    end = new Date ();
    diff = end.getTime () - start.getTime ();
    System.err.println (String.format ("_SelectTestForm_Predetermined part2 latency: %d millisec", (long) diff));
  }

  // TODO Original port
  public void _SelectTestForm_Predetermined_SP (SQLConnection connection, UUID oppkey, String testkey, String lang,
      _Ref<String> formkeyRef, _Ref<String> formIdRef, _Ref<Integer> itemcntRef, Integer sessionType, String formList) throws ReturnStatusException {

    // Date now = _dateUtil.getDateWRetStatus (connection);

    String clientname = null;
    UUID session = null;
    Long testee = null;
    String testId = null;

    final String SQL_QUERY1 = "select clientname, _efk_Testee, _efk_TestID, _fk_Session from testopportunity where _key = ${oppkey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      clientname = record.<String> get ("clientname");
      testee = record.<Long> get ("_efk_Testee");
      testId = record.<String> get ("_efk_TestID");
      session = record.<UUID> get ("_fk_Session");
    }
    // TODO EF:
    String environment = _commonDll.getExternsColumnByClientName (connection, clientname, "environment");

    DataBaseTable assignedTbl = getDataBaseTable ("stfpAssigned").addColumn ("testkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).addColumn ("window", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).
        addColumn ("windowMax", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("mode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("modeMax", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("startdate", SQL_TYPE_To_JAVA_TYPE.DATETIME).addColumn ("enddate", SQL_TYPE_To_JAVA_TYPE.DATETIME).
        addColumn ("frmKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("cnt", SQL_TYPE_To_JAVA_TYPE.INT);
    final Integer sessionTypeFinal = sessionType;
    final String formListFinal = formList;
    final String clientnameFinal = clientname;
    final Long testeeFinal = testee;
    final String testIdFinal = testId;
    executeMethodAndInsertIntoTemporaryTable (connection, new AbstractDataResultExecutor ()
    {
      @Override
      public SingleDataResultSet execute (SQLConnection connection) throws ReturnStatusException {
        SingleDataResultSet resultSet = _GetTesteeTestForms_SP (connection, clientnameFinal, testIdFinal, testeeFinal, sessionTypeFinal, formListFinal);
        // windowID, windowMax, startDate, endDate, formkey, mode, modeMax,
        // testkey

        resultSet.resetColumnName (1, "window");
        resultSet.resetColumnName (5, "frmkey");
        resultSet.addColumn ("cnt", SQL_TYPE_To_JAVA_TYPE.INT);
        Iterator<DbResultRecord> records = resultSet.getRecords ();
        while (records.hasNext ()) {
          DbResultRecord record = records.next ();
          record.addColumnValue ("cnt", 0);
        }

        return resultSet;
      }
    }, assignedTbl, true);

    final String SQL_UPDATE2 = "update ${assignedTblName} set cnt =  (select count(*) from testopportunity O, testopportunitysegment S "
        + " where clientname = ${clientname} and _efk_Testee = ${testee} and S._fk_TestOpportunity = O._key and formkey = frmkey)";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testee", testee);
    Map<String, String> unquotedParms2 = new HashMap<String, String> ();
    unquotedParms2.put ("assignedTblName", assignedTbl.getTableName ());

    executeStatement (connection, fixDataBaseNames (SQL_UPDATE2, unquotedParms2), parms2, false).getUpdateCount ();

    DataBaseTable formsTbl = getDataBaseTable ("stfpForms").addColumn ("_formkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("id", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150).
        addColumn ("itemcnt", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("formcnt", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (formsTbl);

    final String SQL_INSERT3 = "insert into ${formTblName} (_formkey, id, formcnt, itemcnt) select F._Key, F.FormID, 0, "
        + " (select count(*) from ${ItemBankDB}.testformitem I where I._fk_AdminSubject = ${testkey} and I._fk_TestForm = F._Key) "
        + " from ${ItemBankDB}.testform F where F.Language = ${lang} and F._fk_AdminSubject = ${testkey}";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("testkey", testkey).put ("lang", lang);
    Map<String, String> unquotedParms3 = new HashMap<String, String> ();
    unquotedParms3.put ("formTblName", formsTbl.getTableName ());

    final String query3 = fixDataBaseNames (SQL_INSERT3);
    executeStatement (connection, fixDataBaseNames (query3, unquotedParms3), parms3, false).getUpdateCount ();

    final String SQL_UPDATE4 = "update  ${formTblName} set formcnt = (select count(*)  from testopportunity, testopportunitysegment "
        + " where clientname = ${clientname} and _fk_TestOpportunity = _Key and _efk_Segment = ${testkey} and FormKey = _formkey "
        + "      and (${environment} <> 'SIMULATION' or _fk_Session = ${session}));";
    Map<String, String> unquotedParms4 = unquotedParms3;
    SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("testkey", testkey).put ("clientname", clientname).put ("environment", environment).put ("session", session);

    executeStatement (connection, fixDataBaseNames (SQL_UPDATE4, unquotedParms4), parms4, true).getUpdateCount ();

    final String SQL_QUERY5 = "select  ID as formId, itemcnt, _formkey as formkey from ${formTblName} F, ${assignedTblName} A "
        + " where F._formkey = A.frmkey order by A.cnt, formcnt, rand()  limit 1";
    Map<String, String> unquotedParms5 = new HashMap<String, String> ();
    unquotedParms5.put ("formTblName", formsTbl.getTableName ());
    unquotedParms5.put ("assignedTblName", assignedTbl.getTableName ());

    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY5, unquotedParms5), null, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      formkeyRef.set (record.<String> get ("formkey"));
      formIdRef.set (record.<String> get ("formId"));
      itemcntRef.set (record.<Integer> get ("itemcnt"));
    }
    connection.dropTemporaryTable (formsTbl);
    connection.dropTemporaryTable (assignedTbl);
  }

  public void _SelectTestForm_EqDist_SP (SQLConnection connection, UUID oppkey, String testkey, String lang,
      _Ref<String> formkeyRef, _Ref<String> formIdRef, _Ref<Integer> formlengthRef) throws ReturnStatusException {

    _SelectTestForm_EqDist_SP (connection, oppkey, testkey, lang, formkeyRef, formIdRef, formlengthRef, null);
  }

  /**
   * 
   * @param connection
   * @param oppkey
   * @param testkey
   * @param lang
   * @param formkeyRef
   * @param formIdRef
   * @param formlengthRef
   * @param formCohort
   * @throws ReturnStatusException
   */
  public void _SelectTestForm_EqDist_SP (SQLConnection connection, UUID oppkey, String testkey, String lang,
      _Ref<String> formkeyRef, _Ref<String> formIdRef, _Ref<Integer> formlengthRef, String formCohort) throws ReturnStatusException {

    String clientname = null, parenttest = null;
    Long testee = null;
    UUID session = null;
    final String SQL_QUERY1 = "select clientname, _efk_AdminSubject as parenttest, _efk_Testee as testee, _fk_Session as session from testopportunity where _key = ${oppkey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      clientname = record.<String> get ("clientname");
      parenttest = record.<String> get ("parenttest");
      testee = record.<Long> get ("testee");
      session = record.<UUID> get ("session");
    }
    String environment = _commonDll.getExternsColumnByClientName (connection, clientname, "environment");
    Date now = _dateUtil.getDateWRetStatus (connection);

    DataBaseTable formsTbl = getDataBaseTable ("stfeForms").addColumn ("_formkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("id", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150).
        addColumn ("itemcnt", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("usercnt", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("formcnt", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (formsTbl);

    final String SQL_INSERT2 = "insert into ${formsTblName} (_formkey, id, itemcnt)  select F._Key, F.FormID, "
        + " (select count(*) from ${ItemBankDB}.testformitem I where I._fk_AdminSubject = ${testkey} and I._fk_TestForm = F._Key) "
        + "   from ${ItemBankDB}.testform F, ${ConfigDB}.client_testformproperties P "
        + " where F.Language = ${lang} and F._fk_AdminSubject = ${testkey} and F._Key = P._efk_TestForm and P.clientname = ${clientname} "
        + " and (${formCohort} is null or cohort = ${formCohort}) and ((P.startdate is null or ${now} > P.startdate) and (P.enddate is null or ${now} < P.endDate))";
    Map<String, String> unquotedParms2 = new HashMap<String, String> ();
    unquotedParms2.put ("formsTblName", formsTbl.getTableName ());
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("testkey", testkey).put ("lang", lang).put ("clientname", clientname).
        put ("now", now).put ("formCohort", formCohort);

    final String query2 = fixDataBaseNames (SQL_INSERT2);
    executeStatement (connection, fixDataBaseNames (query2, unquotedParms2), parms2, false).getUpdateCount ();

    final String SQL_QUERY3 = "select count(*)  as formcount from  ${formsTblName}";
    Map<String, String> unquotedParms3 = unquotedParms2;

    Long formcount = null;
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY3, unquotedParms3), null, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      formcount = record.<Long> get ("formcount");
    }

    if (DbComparator.isEqual (formcount, 0)) {
      connection.dropTemporaryTable (formsTbl);
      return;
    }

    if (DbComparator.isEqual (formcount, 1)) {
      final String SQL_QUERY4 = "select  _formkey as formkey, id as formID, itemcnt as formLength from ${formsTblName} limit 1";
      Map<String, String> unquotedParms4 = unquotedParms2;
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY4, unquotedParms4), null, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        formkeyRef.set (record.<String> get ("formkey"));
        formIdRef.set (record.<String> get ("formID"));
        formlengthRef.set (record.<Integer> get ("formLength"));
      }
      connection.dropTemporaryTable (formsTbl);
      return;
    }

    final String SQL_UPDATE5 = "update ${formsTblName} set usercnt =  (select count(*) from testopportunity, testopportunitysegment "
        + " where clientname = ${clientname} and _efk_Testee = ${testee} and _fk_TestOpportunity = _Key and _efk_Segment = ${testkey} and FormKey = _formkey "
        + "       and (${environment} <> 'SIMULATION' or _fk_Session = ${session})), "
        + " formcnt = (select count(*) from testopportunity, testopportunitysegment "
        + " where clientname = ${clientname} and _fk_TestOpportunity = _Key and _efk_Segment = ${testkey} and FormKey = _formkey "
        + "       and (${environment} <> 'SIMULATION' or _fk_Session = ${session}))";

    Map<String, String> unquotedParms5 = unquotedParms2;
    SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("testkey", testkey).put ("environment", environment).
        put ("session", session).put ("testee", testee).put ("clientname", clientname);

    executeStatement (connection, fixDataBaseNames (SQL_UPDATE5, unquotedParms5), parms5, true).getUpdateCount ();

    final String SQL_QUERY6 = "select  _formkey as formkey, id as formID, itemcnt as formLength from ${formsTblName} order by usercnt, formcnt, RAND() limit 1";
    Map<String, String> unquotedParms6 = unquotedParms2;
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY6, unquotedParms6), null, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      formkeyRef.set (record.<String> get ("formkey"));
      formIdRef.set (record.<String> get ("formID"));
      formlengthRef.set (record.<Integer> get ("formLength"));
    }
    connection.dropTemporaryTable (formsTbl);
  }

  public void _SelectTestForm_Driver_SP (SQLConnection connection, UUID oppkey, String testkey, String lang,
      _Ref<String> formkeyRef, _Ref<String> formIdRef, _Ref<Integer> formlengthRef) throws ReturnStatusException {

    _SelectTestForm_Driver_SP (connection, oppkey, testkey, lang, formkeyRef, formIdRef, formlengthRef, null, null);
  }

  public void _SelectTestForm_Driver_SP (SQLConnection connection, UUID oppkey, String testkey, String lang,
      _Ref<String> formkeyRef, _Ref<String> formIdRef, _Ref<Integer> formlengthRef, String formList, String formCohort) throws ReturnStatusException {

    String clientname = null, parentTestId = null, parentTest = null;
    Integer sessionType = null;

    final String SQL_QUERY1 = "select O.clientname,  _efk_TestID as parenttestID, _efk_AdminSubject as parentTest, sessionType "
        + " from testopportunity O, session S where O._Key = ${oppkey} and O._fk_Session = S._Key";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      clientname = record.<String> get ("clientname");
      parentTestId = record.<String> get ("parenttestid");
      parentTest = record.<String> get ("parenttest");
      sessionType = record.<Integer> get ("sessionType");
    }
    Boolean requireRTSForm = null;
    Boolean requireRTSFormWindow = null;
    Boolean ifRts = null;
    int fromRts = 0;

    final String SQL_QUERY2 = "select requireRTSForm,  requireRTSFormWindow, requireRTSFormIfExists as ifRts from ${ConfigDB}.client_testmode where testkey = ${parenttest}";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("parenttest", parentTest);
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY2), parms2, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      requireRTSForm = record.<Boolean> get ("requireRTSForm");
      requireRTSFormWindow = record.<Boolean> get ("requireRTSFormWindow");
      ifRts = record.<Boolean> get ("ifRts");
      if (DbComparator.isEqual (requireRTSForm, true))
        fromRts++;
      if (DbComparator.isEqual (requireRTSFormWindow, true))
        fromRts++;
    }

    formkeyRef.set (null);
    formIdRef.set (null);
    formlengthRef.set (null);

    if (formCohort == null && (fromRts > 0 || formList != null || DbComparator.isEqual (ifRts, true))) {

      _SelectTestForm_Predetermined_SP (connection, oppkey, testkey, lang, formkeyRef, formIdRef, formlengthRef, sessionType, formList);
    } else {

      _SelectTestForm_EqDist_SP (connection, oppkey, testkey, lang, formkeyRef, formIdRef, formlengthRef, formCohort);
    }

    if (DbComparator.isEqual (ifRts, true) && formkeyRef.get () == null) {

      _SelectTestForm_EqDist_SP (connection, oppkey, testkey, lang, formkeyRef, formIdRef, formlengthRef, formCohort);
    }

    if (formkeyRef.get () == null || formIdRef.get () == null || formlengthRef.get () == null)
      _commonDll._LogDBError_SP (connection, "_SelectTestForm_Driver", "Unable to select test form", null, null, null, oppkey, clientname, null);
  }

  /**
   * @param connection
   * @param oppKey
   * @param testKey
   * @param parentKey
   * @param language
   * @return
   * @throws ReturnStatusException
   */
  public Integer FT_IsEligible_FN (SQLConnection connection, UUID oppKey, String testKey, String parentKey, String language) throws ReturnStatusException {

    Date today = _dateUtil.getDateWRetStatus (connection);
    SingleDataResultSet result = null;
    Integer ftLength = null;
    Integer langItemCount = null; // this variable is never used
    Integer parentOkay = null;
    Integer testOkay = null;
    String environment = null;
    String clientName = null;
    String testId = null;
    String parentId = null;
    Long ftItems = null;

    final String SQL_QUERY1 = "select clientname from testopportunity where _Key = ${oppkey} ";
    SqlParametersMaps parameterQuery1 = new SqlParametersMaps ().put ("oppkey", oppKey);

    result = executeStatement (connection, SQL_QUERY1, parameterQuery1, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      clientName = record.<String> get ("clientname");
    }

    final String SQL_QUERY2 = "select environment from externs where clientname = ${clientname}";
    SqlParametersMaps parameterQuery2 = new SqlParametersMaps ().put ("clientname", clientName);

    result = executeStatement (connection, SQL_QUERY2, parameterQuery2, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      environment = record.<String> get ("environment");
    }
    //AK: Executed statement contains: ... and P.propname = 'Language' and P.propvalue = 'Language' ... It means that keys "language" and "Language" are the same. I change "language" --> "lang"
    final String SQL_QUERY3 = "select count(*) as ftitems from ${ItemBankDB}.tblitemprops P, ${ItemBankDB}.tblsetofadminitems I where P._fk_AdminSUbject = ${testkey} " +
        " and P.propname = ${Language} and P.propvalue = ${lang} and I._fk_AdminSubject = ${testkey} and I.IsFieldTest = 1 and P._fk_Item = I._Fk_Item and " +
        " I.isActive = 1 and P.isActive = 1";
    String query3 = fixDataBaseNames (SQL_QUERY3);
    SqlParametersMaps parameterQuery3 = (new SqlParametersMaps ()).put ("lang", language).put ("testkey", testKey).put ("Language", "Language");
    result = executeStatement (connection, query3, parameterQuery3, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      ftItems = record.<Long> get ("ftitems");
    }
    if (DbComparator.isEqual (ftItems, 0)) {
      return 0;
    }

    final String SQL_QUERY4 = "select  testID as testId , FTMinItems as ftlength from ${ItemBankDB}.tblsetofadminsubjects S where S._Key = ${testkey}";
    String query = fixDataBaseNames (SQL_QUERY4);
    SqlParametersMaps parameterQuery4 = new SqlParametersMaps ().put ("testkey", testKey);
    result = executeStatement (connection, query, parameterQuery4, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      testId = record.<String> get ("testId");
      ftLength = record.<Integer> get ("ftlength");
    }
    if (DbComparator.isEqual (ftLength, 0))
      return 0;
    if (DbComparator.isEqual (environment, "SIMULATION"))
      return 1;

    if (DbComparator.notEqual (parentKey, testKey)) {
      final String SQL_QUERY5 = "select testID as parentID from ${ItemBankDB}.tblsetofadminsubjects where _Key = ${parentKey} ";
      String query5 = fixDataBaseNames (SQL_QUERY5);
      SqlParametersMaps parameterQuery5 = new SqlParametersMaps ().put ("parentKey", parentKey);
      result = executeStatement (connection, query5, parameterQuery5, false).getResultSets ().next ();
      record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        parentId = record.<String> get ("parentID");
      }
    } else {
      parentId = testId;
    }

    parentOkay = 0;
    long cnt = 0;
    final String SQL_QUERY6 = "select count(*) as cnt from ${ConfigDB}.client_testproperties where clientname = ${clientname} and testID = ${parentID} " +
        " and (FTStartDate is null or FTSTartDate <= ${today}) and (FTENdDate is null or FTEndDate > ${today}) ";
    String query6 = fixDataBaseNames (SQL_QUERY6);

    SqlParametersMaps parameterQuery6 = new SqlParametersMaps ().put ("clientname", clientName).put ("parentID", parentId).put ("today", today);
    result = executeStatement (connection, query6, parameterQuery6, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      cnt = record.<Long> get ("cnt");
      if (cnt > 0)
        parentOkay = 1;
    }

    if (DbComparator.isEqual (parentKey, testKey) || parentOkay == 0) {
      return parentOkay;
    }
    testOkay = 0;
    cnt = 0;
    final String SQL_QUERY7 = "select count(*) as cnt from ${ConfigDB}.client_segmentproperties where clientname = ${clientname} and segmentID = ${testID}" +
        " and (FTStartDate is null or FTSTartDate <= ${today}) and (FTENdDate is null or FTEndDate > ${today})";
    String query7 = fixDataBaseNames (SQL_QUERY7);
    SqlParametersMaps parameterQuery7 = new SqlParametersMaps ().put ("clientname", clientName).put ("testID", testId).put ("today", today);
    result = executeStatement (connection, query7, parameterQuery7, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      cnt = record.<Long> get ("cnt");
      if (cnt > 0)
        testOkay = 1;
    }
    return testOkay;
  }

  /**
   * @param connection
   * @param oppKey
   * @return
   * @throws ReturnStatusException
   */
  public String GetOpportunityLanguage_FN (SQLConnection connection, UUID oppKey) throws ReturnStatusException {

    String lang = null;
    final String SQL_QUERY = "select AccCode as lang from testeeaccommodations where AccType = ${Language} and _fk_TestOpportunity = ${oppkey} ";
    SqlParametersMaps parameters = new SqlParametersMaps ().put ("Language", "Language").put ("oppkey", oppKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY, parameters, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      lang = record.<String> get ("lang");
    }
    return lang;
  }

  /**
   * @param connection
   * @param oppKey
   * @param error
   * @param formKeyList
   * @throws ReturnStatusException
   */
  public MultiDataResultSet _InitializeTestSegments_SP (SQLConnection connection, UUID oppKey, _Ref<String> error, String formKeyList) throws ReturnStatusException {
    return _InitializeTestSegments_SP (connection, oppKey, error, formKeyList, false);
  }

  public MultiDataResultSet _InitializeTestSegments_SP (SQLConnection connection, UUID oppKey, _Ref<String> error, String formKeyList, Boolean debug) throws ReturnStatusException {

    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();
    Date now = _dateUtil.getDateWRetStatus (connection);

    final String SQL_QUERY1 = "select  _efk_Segment from testopportunitysegment where _fk_TestOpportunity = ${oppkey} and ${debug} = 0 limit 1";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("debug", debug);
    if (exists (executeStatement (connection, SQL_QUERY1, parms1, false))) {
      if (debug == true) {
        // System.err.println ("Segments already exist"); // for testing purpose
      }
      return (new MultiDataResultSet (resultsets));
    }
    String testKey = null;
    String testId = null;
    String segmentId = null;
    String parentKey = null;
    String clientName = null;
    String query = null;
    Boolean isSegmented = null;
    String algorithm = null;
    Integer pos = null;
    Integer opitems = null;
    Integer ftitems = null;
    String language = null;
    String formCohort = null; // for enforcing form consistency across segments
    Boolean isSatisfied = null;
    UUID session = null;
    UUID sessionPoolKey = null;
    Integer segcnt = null;
    Integer segpos = null;
    _Ref<String> formKeyRef = new _Ref<> ();
    _Ref<String> formIdRef = new _Ref<> ();
    _Ref<Integer> formlengthRef = new _Ref<> ();
    _Ref<Integer> newlenRef = new _Ref<> ();
    _Ref<Integer> poolcountRef = new _Ref<> ();
    _Ref<Integer> ftcntRef = new _Ref<> ();
    _Ref<String> itemStringRef = new _Ref<> ();
    Boolean isSimulation = IsSimulation_FN (connection, oppKey);

    // create a temporary table to build segments in. WHen done, insert them en
    // masse into testopportunitysegment table with guard against duplication

    DataBaseTable segmentsTable = getDataBaseTable ("Segments").addColumn ("_fk_TestOpportunity", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER)
        .addColumn ("_efk_Segment", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250)
        .addColumn ("SegmentPosition", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("formKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("FormID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200)
        .addColumn ("algorithm", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("opItemCnt", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("ftItemCnt", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("ftItems", SQL_TYPE_To_JAVA_TYPE.TEXT).addColumn ("IsPermeable", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("restorePermOn", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50)
        .addColumn ("segmentID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("entryApproved", SQL_TYPE_To_JAVA_TYPE.DATETIME).addColumn ("exitApproved", SQL_TYPE_To_JAVA_TYPE.DATETIME)
        .addColumn ("formCohort", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 20).addColumn ("IsSatisfied", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("initialAbility", SQL_TYPE_To_JAVA_TYPE.FLOAT)
        .addColumn ("currentAbility", SQL_TYPE_To_JAVA_TYPE.FLOAT).addColumn ("_date", SQL_TYPE_To_JAVA_TYPE.DATETIME).addColumn ("dateExited", SQL_TYPE_To_JAVA_TYPE.DATETIME)
        .addColumn ("itempool", SQL_TYPE_To_JAVA_TYPE.TEXT).addColumn ("poolcount", SQL_TYPE_To_JAVA_TYPE.INT);

    connection.createTemporaryDiskTable (segmentsTable);
    Map<String, String> unquotedParms = new HashMap<> ();
    unquotedParms.put ("segmentsTableName", segmentsTable.getTableName ());
    error.set (null);
    language = GetOpportunityLanguage_FN (connection, oppKey);

    final String SQL_QUERY2 = "select _fk_Session as session, clientname, _efk_TestID as testID, _efk_AdminSubject as testkey, isSegmented, algorithm from testopportunity where _Key = ${oppkey};";
    SqlParametersMaps parms2 = new SqlParametersMaps ().put ("oppkey", oppKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY2, parms2, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      session = record.<UUID> get ("session");
      clientName = record.<String> get ("clientname");
      testId = record.<String> get ("testID");
      testKey = record.<String> get ("testkey");
      isSegmented = record.<Boolean> get ("isSegmented");
      algorithm = record.<String> get ("algorithm");
    }
    parentKey = testKey;

    if (debug == true) {
      final String SQL_QUERY3 = "select cast(${testkey} as CHAR) as testkey, cast(${language} as CHAR) as lang, cast( ${algorithm} as CHAR) as algorithm;";
      SqlParametersMaps parms3 = new SqlParametersMaps ().put ("testkey", testKey).put ("language", language).put ("isSegmented", isSegmented).put ("algorithm", algorithm);
      SingleDataResultSet rs1 = executeStatement (connection, SQL_QUERY3, parms3, false).getResultSets ().next ();
      rs1.addColumn ("segmented", SQL_TYPE_To_JAVA_TYPE.BIT);
      DbResultRecord rec = rs1.getRecords ().next ();
      rec.addColumnValue ("segmented", isSegmented);
      resultsets.add (rs1);
    }
    try {
      if (DbComparator.isEqual (isSimulation, true)) {
        final String SQL_INSERT1 = "insert into ${segmentsTableName} (_fk_TestOpportunity, _efk_Segment, segmentID, SegmentPosition, algorithm, opItemCnt, IsPermeable, IsSatisfied, _date)" +
            " select ${oppkey}, _efk_Segment, segmentID, segmentPosition, selectionalgorithm, MaxItems, ${IsPermeable}, ${IsSatisfied}, ${_date} from sim_segment SS " +
            " where _fk_Session = ${session} and _efk_AdminSubject = ${testkey}; ";
        SqlParametersMaps parms4 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("session", session).put ("testkey", testKey).put ("IsPermeable", -1).put ("IsSatisfied", false)
            .put ("_date", now);
        executeStatement (connection, fixDataBaseNames (SQL_INSERT1, unquotedParms), parms4, false).getUpdateCount ();

        sessionPoolKey = session;

      } else if (DbComparator.isEqual (isSegmented, true)) {
        final String SQL_INSERT2 = "insert into ${segmentsTableName} (_fk_TestOpportunity, _efk_Segment, segmentID, SegmentPosition, algorithm, opItemCnt, IsPermeable, IsSatisfied, _date)"
            + " select ${oppkey}, _Key, testID, testPosition, selectionAlgorithm, maxItems, ${IsPermeable}, ${IsSatisfied}, ${_date} from ${ItemBankDB}.tblsetofadminsubjects SS where VirtualTest = ${testkey};";
        String finalQuery = fixDataBaseNames (SQL_INSERT2);
        SqlParametersMaps parms5 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("testkey", testKey).put ("IsPermeable", -1).put ("IsSatisfied", false).put ("_date", now);
        executeStatement (connection, fixDataBaseNames (finalQuery, unquotedParms), parms5, false).getUpdateCount ();

      } else { // not segmented, so make the test its own segment
        final String SQL_INSERT3 = "insert into ${segmentsTableName} (_fk_TestOpportunity, _efk_Segment, segmentID, SegmentPosition, algorithm, opItemCnt, IsPermeable, IsSatisfied, _date) " +
            " select ${oppkey}, ${testkey}, TestID, 1, selectionAlgorithm, maxItems, ${IsPermeable}, ${IsSatisfied}, ${_date}  from ${ItemBankDB}.tblsetofadminsubjects SS where _Key = ${testkey}; ";
        String finalQuery = fixDataBaseNames (SQL_INSERT3);
        SqlParametersMaps parms6 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("testkey", testKey).put ("IsPermeable", -1).put ("IsSatisfied", false).put ("_date", now);
        executeStatement (connection, fixDataBaseNames (finalQuery, unquotedParms), parms6, false).getUpdateCount ();
        // System.err.println (insertedCnt);
      }
      if (debug == true) {
        final String SQL_QUERY4 = "select * from ${segmentsTableName};";
        SingleDataResultSet rs2 = executeStatement (connection, fixDataBaseNames (SQL_QUERY4, unquotedParms), null, false).getResultSets ().next ();
        resultsets.add (rs2);
      }
      final String SQL_QUERY5 = "select max(segmentPosition) as segcnt, min(segmentPosition) as segpos from ${segmentsTableName};";
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY5, unquotedParms), null, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        segcnt = record.<Integer> get ("segcnt");
        segpos = record.<Integer> get ("segpos");
      }
      // initialize form selection and field test item selection on each segment
      while (DbComparator.lessOrEqual (segpos, segcnt)) {
        ftcntRef.set (0);
        formKeyRef.set (null);
        formIdRef.set (null);
        formlengthRef.set (null);
        itemStringRef.set ("");
        isSatisfied = false;

        final String SQL_QUERY6 = "select  _fk_TestOpportunity from ${segmentsTableName} where segmentPosition = ${segpos} limit 1";
        SqlParametersMaps parms7 = new SqlParametersMaps ().put ("segpos", segpos);
        if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY6, unquotedParms), parms7, false))) {
          final String SQL_QUERY7 = "select _efk_Segment as testkey, SegmentPosition as pos, algorithm, segmentID, opItemCnt as opitems from ${segmentsTableName} where _fk_TestOpportunity = ${oppkey} and segmentPosition = ${segpos} limit 1;";
          SqlParametersMaps parms8 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("segpos", segpos);
          result = executeStatement (connection, fixDataBaseNames (SQL_QUERY7, unquotedParms), parms8, false).getResultSets ().next ();
          record = (result.getCount () > 0 ? result.getRecords ().next () : null);
          if (record != null) {
            testKey = record.<String> get ("testkey");
            pos = record.<Integer> get ("pos");
            algorithm = record.<String> get ("algorithm");
            segmentId = record.<String> get ("segmentID");
            opitems = record.<Integer> get ("opitems");
          }
        } else {
          segpos += 1;
          continue;
        }

        if (DbComparator.isEqual ("fixedform", algorithm)) {
          _SelectTestForm_Driver_SP (connection, oppKey, testKey, language, formKeyRef, formIdRef, formlengthRef, formKeyList, formCohort);
          if (formKeyRef.get () == null) {
            error.set ("Unable to complete test form selection");
            // don't leave garbage around if we failed to do everything
            final String SQL_DELETE1 = "delete from ${segmentsTableName} where _fk_TestOpportunity = ${oppkey}; ";
            SqlParametersMaps parms9 = new SqlParametersMaps ().put ("oppkey", oppKey);
            executeStatement (connection, fixDataBaseNames (SQL_DELETE1, unquotedParms), parms9, false).getUpdateCount ();
            return (new MultiDataResultSet (resultsets));
          }
          poolcountRef.set (formlengthRef.get ());
          if (formCohort == null) {
            final String SQL_QUERY8 = "select cohort as formCohort from ${ItemBankDB}.testform where _fk_AdminSubject = ${testkey} and _Key = ${formkey};";
            String finalQuery = fixDataBaseNames (SQL_QUERY8);
            SqlParametersMaps parms10 = new SqlParametersMaps ().put ("formkey", formKeyRef.get ()).put ("testkey", testKey);
            result = executeStatement (connection, finalQuery, parms10, false).getResultSets ().next ();
            record = (result.getCount () > 0 ? result.getRecords ().next () : null);
            if (record != null) {
              formCohort = record.<String> get ("formCohort");
            }
          }
        } else {
          _ComputeSegmentPool_SP (connection, oppKey, testKey, newlenRef, poolcountRef, itemStringRef, sessionPoolKey);

          int isEligible = FT_IsEligible_FN (connection, oppKey, testKey, parentKey, language);

          if (DbComparator.isEqual (isEligible, 1) && DbComparator.isEqual (newlenRef.get (), opitems)) {
            _FT_SelectItemgroups_SP (connection, oppKey, testKey, pos, segmentId, language, ftcntRef);
          } else {
            ftcntRef.set (0);
          }
          if (ftcntRef.get () != null && newlenRef.get () != null && DbComparator.isEqual (ftcntRef.get () + newlenRef.get (), 0)) {
            isSatisfied = true;
          }
        }
        final String SQL_UPDATE1 = "update ${segmentsTableName} set itempool = (${itemString}), poolcount = ${poolcount}, opItemCnt = case when ${algorithm} = ${fixedform} " +
            " then ${formLength} else ${newlen} end, formCohort = ${formCohort}, formKey = ${formkey}, formID = ${formID}, ftItemCnt = ${ftcnt}, isSatisfied = ${isSatisfied}" +
            " where _fk_TestOpportunity = ${oppkey} and _efk_Segment = ${testkey} and SegmentPosition = ${pos}; ";
        SqlParametersMaps parms11 = new SqlParametersMaps ();
        parms11.put ("itemString", itemStringRef.get ());
        parms11.put ("poolcount", poolcountRef.get ());
        parms11.put ("algorithm", algorithm);
        parms11.put ("fixedform", "fixedform");
        parms11.put ("formLength", formlengthRef.get ());
        parms11.put ("newlen", newlenRef.get ());
        parms11.put ("formCohort", formCohort);
        parms11.put ("formkey", formKeyRef.get ());
        parms11.put ("formID", formIdRef.get ());
        parms11.put ("ftcnt", ftcntRef.get ());
        parms11.put ("isSatisfied", isSatisfied);
        parms11.put ("oppkey", oppKey);
        parms11.put ("testkey", testKey);
        parms11.put ("pos", pos);
        executeStatement (connection, fixDataBaseNames (SQL_UPDATE1, unquotedParms), parms11, false).getUpdateCount ();
        // System.err.println (updatedCnt);

        segpos += 1;
      }
      if (debug == true) {
        final String SQL_QUERY9 = "SELECT _fk_TestOpportunity, _efk_Segment, SegmentPosition, formKey, FormID, algorithm, opItemCnt, ftItemCnt, " +
            " ftItems, IsPermeable, restorePermOn, segmentID, entryApproved, exitApproved, formCohort, IsSatisfied, initialAbility, currentAbility, " +
            " _date, dateExited, itempool, poolcount  FROM ${segmentsTableName};";
        SqlParametersMaps parms12 = new SqlParametersMaps ();
        SingleDataResultSet rs3 = executeStatement (connection, fixDataBaseNames (SQL_QUERY9, unquotedParms), parms12, false).getResultSets ().next ();
        resultsets.add (rs3);
      }
      final String SQL_QUERY10 = "select  _fk_TestOpportunity from ${segmentsTableName} where _fk_TestOpportunity = ${oppkey} and opItemCnt + ftItemCnt > 0 limit 1";
      SqlParametersMaps parms11 = parms2;
      if (!exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY10, unquotedParms), parms11, false))) {
        // RAISERROR ('No items in pool', 15, 1);
        // TODO Udaya, talk to oksana about the severity of the error
        _logger.error ("No items in pool"); // for testing
        throw new ReturnStatusException ("No items in pool for _InitializeTestSegments");
      }
      if (debug == false) {
        
        final String SQL_INSERT4 = "INSERT INTO testopportunitysegment (_fk_TestOpportunity, _efk_Segment, SegmentPosition, formKey, FormID, algorithm, opItemCnt, "
            + " ftItemCnt, ftItems, IsPermeable, restorePermOn, segmentID, entryApproved, exitApproved, formCohort, IsSatisfied, initialAbility, currentAbility, _date,"
            + " dateExited, itempool, poolcount) "
            + " SELECT _fk_TestOpportunity, _efk_Segment, SegmentPosition, formKey, FormID, algorithm, opItemCnt, ftItemCnt, ftItems,"
            + " IsPermeable, restorePermOn, segmentID, entryApproved, exitApproved, formCohort, IsSatisfied, initialAbility, currentAbility, "
            + " _date, dateExited, itempool, poolcount "
            + " FROM ${segmentsTableName} where not exists (select * from testopportunitysegment where _fk_TestOpportunity = ${Oppkey});";
        SqlParametersMaps parms13 = new SqlParametersMaps ().put ("oppkey", oppKey);
           executeStatement (connection, fixDataBaseNames (SQL_INSERT4, unquotedParms), parms13, false).getUpdateCount ();
          // System.err.println (insertedCnt);
      }
      connection.dropTemporaryTable (segmentsTable);
    } catch (ReturnStatusException re) {
      String msg = null;
      // set @msg = ERROR_PROCEDURE() + ': ' + ERROR_MESSAGE();
      // ERROR_PROCEDURE() returns name of the stored procedure TODO udaya, talk
      // to oksana/elena
      msg = String.format ("_InitializeTestSegments %s", re.getMessage ());
      _commonDll._LogDBError_SP (connection, "_InitializeTestSegments", msg, null, null, null, oppKey, clientName, null);
      if (debug == true) {
        error.set (msg);
      } else {
        error.set ("Segment initialization failed");
      }
    }
    return (new MultiDataResultSet (resultsets));
  }

  /**
   * @param connection
   * @param oppKey
   * @param testLength
   * @param reason
   * @param formKeyList
   * @throws ReturnStatusException
   */
  public void _InitializeOpportunity_SP (SQLConnection connection, UUID oppKey, _Ref<Integer> testLength, _Ref<String> reason, String formKeyList) throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);
    _Ref<Float> abilityRef = new _Ref<> ();
    reason.set (null);

    _InitializeTestSegments_SP (connection, oppKey, reason, formKeyList);
    if (reason.get () != null) {
      _commonDll._LogDBError_SP (connection, "_InitializeOpportunity", reason.get (), null, null, null, oppKey);
      return;
    }
    _GetInitialAbility_SP (connection, oppKey, abilityRef);
    final String SQL_INSERT1 = "insert into testoppabilityestimate (_fk_TestOpportunity, strand, estimate, itemPos, _date) values (${oppkey}, ${OVERALL}, ${ability}, 0, now(3))";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("OVERALL", "OVERALL").put ("ability", abilityRef.get ());
    executeStatement (connection, SQL_INSERT1, parms1, false).getUpdateCount ();

    final String SQL_INSERT2 = "insert into testoppabilityestimate (_fk_TestOpportunity, strand, estimate, itemPos, _date)"
        + " select ${oppkey}, _fk_Strand, ${ability}, 0, now(3) from ${ItemBankDB}.tbladminstrand S, testopportunity O "
        + " where O._key = ${oppkey} and O._efk_AdminSubject = S._fk_AdminSubject and S.startAbility is not null;";
    String finalQuery = fixDataBaseNames (SQL_INSERT2);
    SqlParametersMaps parms2 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("ability", abilityRef.get ());
    executeStatement (connection, finalQuery, parms2, false).getUpdateCount ();

    final String SQL_QUERY1 = "select convert(sum(opItemCnt), SIGNED) + convert(sum(ftItemCnt), SIGNED) as testLength from testopportunitysegment where _fk_TestOpportunity = ${oppkey};";
    SqlParametersMaps parms3 = new SqlParametersMaps ().put ("oppkey", oppKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms3, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      Long tmp = record.<Long> get ("testLength");
      testLength.set (tmp == null ? null : tmp.intValue ());
    }
    // now create the set of records where all item responses will go
    _CreateResponseSet_SP (connection, oppKey, testLength.get (), null);

    final String SQL_UPDATE1 = "update testopportunity set prevStatus = status, status = ${started}, DateStarted = ${today}, expireFrom = ${today}, " +
        " Stage = ${inprogress}, DateChanged = ${today}, maxitems = ${testlength}, waitingForSegment = null where _Key = ${oppkey}; ";
    SqlParametersMaps parms4 = new SqlParametersMaps ().put ("started", "started").put ("today", starttime).put ("inprogress", "inprogress").put ("testlength", testLength.get ())
        .put ("oppkey", oppKey);
    executeStatement (connection, SQL_UPDATE1, parms4, false).getUpdateCount ();

    _commonDll._LogDBLatency_SP (connection, "_InitializeOpportunity", starttime, null, true, null, oppKey);
  }

  /**
   * @param connection
   * @param oppKey
   * @param segment
   * @return
   * @throws ReturnStatusException
   */
  public Boolean _AA_IsSegmentSatisfied_FN (SQLConnection connection, UUID oppKey, Integer segment) throws ReturnStatusException {

    Integer ftcnt = null;
    Integer opcnt = null;
    String segmentKey = null;
    Integer opneed = null;
    Integer ftneed = null;
    String algorithm = null;
    SingleDataResultSet result = null;

    final String SQL_QUERY1 = "  select opItemCnt as opneed, ftITemCnt as ftneed, algorithm, _efk_Segment as segmentKey from testopportunitysegment where _fk_TestOpportunity = ${oppkey} and SegmentPosition = ${segment} ";
    SqlParametersMaps parametersQuery1 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("segment", segment);
    result = executeStatement (connection, SQL_QUERY1, parametersQuery1, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      opneed = record.<Integer> get ("opneed");
      ftneed = record.<Integer> get ("ftneed");
      algorithm = record.<String> get ("algorithm");
      segmentKey = record.<String> get ("segmentKey");
    }
    final String SQL_QUERY2 = "select  count(*) as opcnt from testeeresponse where _fk_TestOpportunity = ${oppkey} and segment = ${segment} and isFieldTest = 0";
    SqlParametersMaps parametersQuery2 = parametersQuery1;
    result = executeStatement (connection, SQL_QUERY2, parametersQuery2, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      opcnt = record.<Long> get ("opcnt").intValue ();
    }
    final String SQL_QUERY3 = "select count(*) as ftcnt from testeeresponse where _fk_TestOpportunity = ${oppkey} and segment = ${segment} and isFieldTest = 1";
    SqlParametersMaps parametersQuery3 = parametersQuery1;
    result = executeStatement (connection, SQL_QUERY3, parametersQuery3, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      ftcnt = record.<Long> get ("ftcnt").intValue ();
    }
    final String SQL_QUERY4 = "select  _fk_TestOpportunity from ft_opportunityitem where _fk_TestOpportunity = ${oppkey} and _efk_FieldTest = ${segmentKey} " +
        " and coalesce(deleted, 0) = 0 and dateAdministered is null limit 1";
    SqlParametersMaps parametersQuery4 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("segmentKey", segmentKey);
    if (DbComparator.containsIgnoreCase (algorithm, "adaptive") && DbComparator.greaterOrEqual (opcnt, opneed)
        // if (DbComparator.isEqual (algorithm, "adaptive") &&
        // DbComparator.greaterOrEqual (opcnt, opneed)
        && (DbComparator.lessOrEqual (ftneed, ftcnt) || !exists (executeStatement (connection, SQL_QUERY4, parametersQuery4, false)))) {
      return true;

    } else if (DbComparator.isEqual (algorithm, "fixedform") && (opcnt != null && ftcnt != null && DbComparator.isEqual (opneed, (opcnt + ftcnt)))) {

      return true;
    }
    return false;
  }

  /**
   * @param connection
   * @param oppKey
   * @throws ReturnStatusException
   */
  public MultiDataResultSet _RemoveUnanswered_SP (SQLConnection connection, UUID oppKey) throws ReturnStatusException {
    return _RemoveUnanswered_SP (connection, oppKey, false);
  }

  public MultiDataResultSet _RemoveUnanswered_SP (SQLConnection connection, UUID oppKey, Boolean debug) throws ReturnStatusException {

    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();
    Integer restart = null;
    Integer segment = null;
    Date starttime = _dateUtil.getDateWRetStatus (connection);

    // MAJOR ASSUMPTION: The student is not allowed to proceed past the first
    // page with unanswered items until all those items have been answered
    final String SQL_QUERY1 = "select restart from testopportunity where _key = ${oppkey};";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("oppkey", oppKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      restart = record.<Integer> get ("restart");
    }

    DataBaseTable itemsTable = getDataBaseTable ("items").addColumn ("page", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("pos", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("groupID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("required", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("viewed", SQL_TYPE_To_JAVA_TYPE.BIT)
        .addColumn ("response", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("groupRequired", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (itemsTable);
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("itemsTableName", itemsTable.getTableName ());

    final String SQL_INSERT1 = "insert into ${itemsTableName} (page, pos, groupID, required, viewed, response, groupRequired) " +
        " select page, position, groupID, isRequired, case when visitCount > 0 then 1 else 0 end, case when dateSubmitted is not null then 1 else 0 end, " +
        " groupItemsRequired from testeeresponse R, testopportunitysegment S where R._fk_TestOpportunity = ${oppkey} and S._fk_TestOpportunity = ${oppkey} and " +
        " S.algorithm like 'adaptive%' and dateGenerated is not null and R.segment = S.segmentPosition " +
        " and exists (select * from testeeresponse R1  where R1._fk_TestOpportunity = ${oppkey} and dateLastVisited is not null and R.groupID = R1.groupID and R1.isFieldTest = 0)" +
        " and exists (select * from testeeresponse R2 where R2._fk_TestOpportunity = ${oppkey} and dateSubmitted is null and R.groupID = R2.groupID and R2.isFieldTest = 0);";
    SqlParametersMaps parms2 = parms1;
    executeStatement (connection, fixDataBaseNames (SQL_INSERT1, unquotedParms), parms2, false).getUpdateCount ();
    // System.err.println (insertedCnt);

    final String SQL_QUERY2 = "select  page from ${itemsTableName} limit 1";
    if (!exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY2, unquotedParms), null, false))) {
      connection.dropTemporaryTable (itemsTable);
      return new MultiDataResultSet (resultsets);
    }

    final String SQL_UPDATE1 = "update ${itemsTableName} set required = 1 where groupRequired = -1;";
    executeStatement (connection, fixDataBaseNames (SQL_UPDATE1, unquotedParms), null, false).getUpdateCount ();

    DataBaseTable groupsTable = getDataBaseTable ("groups").addColumn ("page", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("GID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50)
        .addColumn ("itemsRequired", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("itemCount", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("maxRequired", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("answered", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (groupsTable);
    Map<String, String> unquotedParms1 = new HashMap<String, String> ();
    unquotedParms1.put ("groupsTableName", groupsTable.getTableName ());

    final String SQL_INSERT2 = "insert into ${groupsTableName} (page, GID, itemsRequired , itemCount , maxRequired , answered )" +
        " select page, groupID, sum(required), count(*), max(groupRequired), sum(response) from ${itemsTableName} group by page, groupID;";
    Map<String, String> unquotedParms2 = new HashMap<String, String> ();
    unquotedParms2.put ("groupsTableName", groupsTable.getTableName ());
    unquotedParms2.put ("itemsTableName", itemsTable.getTableName ());
    executeStatement (connection, fixDataBaseNames (SQL_INSERT2, unquotedParms2), null, false).getUpdateCount ();

    final String SQL_UPDATE2 = "update ${groupsTableName} set maxRequired = itemCount where maxRequired > itemCount or maxRequired = -1;";
    executeStatement (connection, fixDataBaseNames (SQL_UPDATE2, unquotedParms1), null, false).getUpdateCount ();

    final String SQL_UPDATE3 = "update ${itemsTableName} I, ${groupsTableName} G set I.required = 1 where I.groupID = G.GID and G.maxrequired = G.itemCount;";
    Map<String, String> unquotedParms3 = unquotedParms2;
    executeStatement (connection, fixDataBaseNames (SQL_UPDATE3, unquotedParms3), null, false).getUpdateCount ();

    Integer firstPage = null;
    Integer lastPage = null;

    DataBaseTable pagesTable = getDataBaseTable ("pages").addColumn ("page", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (pagesTable);
    Map<String, String> unquotedParms4 = new HashMap<String, String> ();
    unquotedParms4.put ("pagesTableName", pagesTable.getTableName ());

    final String SQL_INSERT3 = "insert into ${pagesTableName} (page) (select distinct page from ${itemsTableName} where required > 0 and response = 0) " +
        " union (select page from ${groupsTableName} where answered < itemsRequired)";
    Map<String, String> unquotedParms5 = new HashMap<String, String> ();
    unquotedParms5.put ("groupsTableName", groupsTable.getTableName ());
    unquotedParms5.put ("itemsTableName", itemsTable.getTableName ());
    unquotedParms5.put ("pagesTableName", pagesTable.getTableName ());
    executeStatement (connection, fixDataBaseNames (SQL_INSERT3, unquotedParms5), null, false).getUpdateCount ();

    final String SQL_QUERY3 = "select min(page) as firstpage, max(page) as lastpage from ${pagesTableName};";
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY3, unquotedParms4), null, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      firstPage = record.<Integer> get ("firstpage");
      lastPage = record.<Integer> get ("lastpage");
    }
    if (debug == true) {
      final String SQL_QUERY4 = "select ${firstpage} as firstpage, ${lastpage} as lastpage;";
      SqlParametersMaps parms3 = new SqlParametersMaps ().put ("firstpage", firstPage).put ("lastpage", lastPage);
      result = executeStatement (connection, SQL_QUERY4, parms3, false).getResultSets ().next ();
      resultsets.add (result);

      final String SQL_QUERY5 = "select page from ${pagesTableName} order by page;";
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY5, unquotedParms4), null, false).getResultSets ().next ();
      resultsets.add (result);

      final String SQL_QUERY6 = "select * from ${groupsTableName} order by page";
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY6, unquotedParms1), null, false).getResultSets ().next ();
      resultsets.add (result);

      final String SQL_QUERY7 = "select * from ${itemsTableName} order by pos;";
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY7, unquotedParms), null, false).getResultSets ().next ();
      resultsets.add (result);

      connection.dropTemporaryTable (pagesTable);
      connection.dropTemporaryTable (groupsTable);
      connection.dropTemporaryTable (itemsTable);
      return new MultiDataResultSet (resultsets);
    }

    // treat any items generated after the @lastpage as if they were never
    // selected
    // Elena: _fk_testOpportunity column in testeeitemhistory is string
    // presentation of UUID, so
    // we need convert it
    final String SQL_UPDATE4 = "update testeeitemhistory H, testeeresponse R set H.deleted = 1, H.dateGenerated = null " +
        " where H._fk_TestOpportunity = ${oppkeyStr} " +
        " and R._fk_TestOpportunity = ${oppkey} and R.page > ${lastPage} and H.groupID = R.groupID;";
    SqlParametersMaps parms4 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("lastpage", lastPage).
        put ("oppkeyStr", oppKey.toString ());
    executeStatement (connection, SQL_UPDATE4, parms4, false).getUpdateCount ();

    // treat any items found above as administered but recyclable
    final String SQL_UPDATE5 = "update testeeitemhistory H,testeeresponse R set H.deleted = 1 " +
        " where H._fk_TestOpportunity = ${oppkeyStr} " +
        " and R._fk_TestOpportunity = ${oppkey} and R.page between ${firstpage} and ${lastpage} and R.groupID = H.groupID;";
    SqlParametersMaps parms5 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("firstpage", firstPage).
        put ("lastpage", lastPage).put ("oppkeyStr", oppKey.toString ());
    executeStatement (connection, SQL_UPDATE5, parms5, false).getUpdateCount ();

    final String SQL_UPDATE6 = "UPDATE testeeresponse SET _efk_ITSItem=null, _efk_ITSBank=null, _fk_Session=null, OpportunityRestart=0, Page=null," +
        " Answer=null, ScorePoint=null, Format=null, IsFieldTest=0, DateGenerated=null, DateSubmitted=null, DateFirstResponse=null," +
        " Response=null, Mark=0, Score=-1, Hostname=null, numUpdates=0, dateSystemAltered=null, IsInactive=0, dateInactivated=null," +
        " _fk_AdminEvent=null, GroupID=null, IsSelected=0, IsRequired=0, ResponseSequence=0, ResponseLength=0, _fk_Browser=null, IsValid=0, " +
        " ScoreLatency=0, groupItemsRequired=-1, scorestatus=null, scoringDate=null, scoredDate=null, scoreMark=null, scoreRationale=null, " +
        " scoreAttempts=0, _efk_ItemKey=null, _fk_responseSession=null, segment=0, contentLevel=null, segmentID=null, groupB = null, itemB = null, " +
        " dateLastVisited = null, visitCount = 0 where _fk_TestOpportunity = ${oppkey} and page >= ${firstPage}; ";
    SqlParametersMaps parms6 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("firstpage", firstPage);
    executeStatement (connection, SQL_UPDATE6, parms6, false).getUpdateCount ();

    Boolean isSatisfied = _AA_IsSegmentSatisfied_FN (connection, oppKey, segment);

    // update the segment completion status if necessary
    final String SQL_UPDATE7 = "update testopportunitysegment set IsSatisfied = ${isSatisfied} where _fk_TestOpportunity = ${oppkey};";
    SqlParametersMaps parms7 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("isSatisfied", isSatisfied);
    executeStatement (connection, SQL_UPDATE7, parms7, false).getUpdateCount ();

    final String SQL_UPDATE8 = "update testopportunity set numitems = (select count(*) from testeeresponse where _fk_TestOpportunity = ${oppkey} and dateGenerated is not null), "
        + " numresponses = (select count(*) from testeeresponse where _fk_TestOpportunity = ${oppkey} and dateSubmitted is not null) where _key = ${oppkey};";
    SqlParametersMaps parms8 = parms1;
    executeStatement (connection, SQL_UPDATE8, parms8, false).getUpdateCount ();

    // String sessionDB = getAppSettings ().get ("TDSSessionDBName");
    String sessionDB = getTdsSettings ().getTDSSessionDBName ();
    final String SQL_INSERT4 = "insert into ${ArchiveDB}.opportunityaudit (_fk_TestOpportunity, AccessType, Comment, dateaccessed, hostname, dbname)" +
        " select ${oppkey}, ${RemoveUnanswered}, concat('Removed items >= page ', ${firstpage}), now(3), ${localhost}, ${dbname}";
    String finalQuery = fixDataBaseNames (SQL_INSERT4);
    SqlParametersMaps parms9 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("RemoveUnanswered", "RemoveUnanswered").
        put ("firstpage", firstPage.toString ()).put ("localhost", _commonDll.getLocalhostName ()).put ("dbname", sessionDB);
    executeStatement (connection, finalQuery, parms9, false).getUpdateCount ();

    _commonDll._LogDBLatency_SP (connection, "_RemoveUnanswered", starttime, null, false, null, oppKey);

    return new MultiDataResultSet (resultsets);
  }

  /**
   * @param connection
   * @param oppKey
   * @return
   * @throws ReturnStatusException
   */
  public Date _TestOppLastActivity_FN (SQLConnection connection, UUID oppKey) throws ReturnStatusException {

    Date fromTime = null;
    DataBaseTable fromTimeTable = getDataBaseTable ("fromTimes").addColumn ("lasttime", SQL_TYPE_To_JAVA_TYPE.DATETIME);
    connection.createTemporaryTable (fromTimeTable);
    Map<String, String> unquotedParms = new HashMap<> ();
    unquotedParms.put ("fromTimesTableName", fromTimeTable.getTableName ());

    final String SQL_INSERT1 = "insert into ${fromTimesTableName} (lastTime) select datePaused from testopportunity where _key = ${oppkey};";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("oppkey", oppKey);
    executeStatement (connection, fixDataBaseNames (SQL_INSERT1, unquotedParms), parms1, false).getUpdateCount ();

    final String SQL_INSERT2 = "insert into ${fromTimesTableName} (lasttime) select  max(DateSubmitted) from testeeresponse where _fk_TestOpportunity = ${oppkey} and dateSubmitted is not null;";
    SqlParametersMaps parms2 = parms1;
    executeStatement (connection, fixDataBaseNames (SQL_INSERT2, unquotedParms), parms2, true).getUpdateCount ();

    final String SQL_INSERT3 = "insert into ${fromTimesTableName} (lasttime) select max(DateGenerated) from testeeresponse where _fk_TestOpportunity = ${oppkey} and dateGenerated is not null;";
    SqlParametersMaps parms3 = parms1;
    executeStatement (connection, fixDataBaseNames (SQL_INSERT3, unquotedParms), parms3, true).getUpdateCount ();

    final String SQL_QUERY = "select max(lasttime) as fromtime from ${fromTimesTableName};";
    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY, unquotedParms), null, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      fromTime = record.<Date> get ("fromtime");
    }
    connection.dropTemporaryTable (fromTimeTable);
    return fromTime;
  }

  /**
   * @param connection
   * @param oppKey
   * @param newRestart
   * @param doUpdate
   * @throws ReturnStatusException
   */
  public SingleDataResultSet _UnfinishedResponsePages_SP (SQLConnection connection, UUID oppKey, Integer newRestart, Boolean doUpdate) throws ReturnStatusException {

    SingleDataResultSet result = null;

    DataBaseTable unfinishedTable = getDataBaseTable ("unfinished").addColumn ("page", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("groupRequired", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("numitems", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("validCount", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("requiredItems", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("requiredResponses", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("isVisible", SQL_TYPE_To_JAVA_TYPE.BIT);
    connection.createTemporaryTable (unfinishedTable);
    Map<String, String> unquotedParms = new HashMap<> ();
    unquotedParms.put ("unfinishedTableName", unfinishedTable.getTableName ());

    final String SQL_INSERT1 = "insert into ${unfinishedTableName} (IsVisible, page, groupRequired, numitems, validCount, requiredItems, requiredResponses)"
        + " select 0, page, groupItemsRequired, count(*), sum(convert(IsValid, SIGNED)), sum(convert(IsRequired, SIGNED)), "
        + " sum(case when IsRequired = 1 and IsValid = 1 then 1 else 0 end)"
        + "  from testeeresponse  where _fk_TestOpportunity = ${oppkey} and DateGenerated is not null group by page, groupItemsRequired;";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("oppkey", oppKey);
    executeStatement (connection, fixDataBaseNames (SQL_INSERT1, unquotedParms), parms, true).getUpdateCount ();

    final String SQL_UPDATE1 = "update ${unfinishedTableName} set groupRequired = numitems where groupRequired = -1;";
    executeStatement (connection, fixDataBaseNames (SQL_UPDATE1, unquotedParms), null, false).getUpdateCount ();

    final String SQL_UPDATE2 = "update ${unfinishedTableName} set IsVisible = 1 where requiredResponses < requiredItems or validCount < groupRequired ;";
    executeStatement (connection, fixDataBaseNames (SQL_UPDATE2, unquotedParms), null, false).getUpdateCount ();

    if (doUpdate == true) {
      final String SQL_UPDATE3 = "update testeeresponse set OpportunityRestart = ${newRestart} where _fk_TestOpportunity = ${oppkey} and Page in (select page from ${unfinishedTableName} where IsVisible = 1);";
      SqlParametersMaps parms1 = new SqlParametersMaps ().put ("newRestart", newRestart).put ("oppkey", oppKey);
      executeStatement (connection, fixDataBaseNames (SQL_UPDATE3, unquotedParms), parms1, false).getUpdateCount ();
      return result;
    } else {
      final String SQL_QUERY = "select * from ${unfinishedTableName};";
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY, unquotedParms), null, false).getResultSets ().next ();
    }
    connection.dropTemporaryTable (unfinishedTable);
    return result;
  }

  /**
   * @param connection
   * @param oppKey
   * @param restart
   * @return
   * @throws ReturnStatusException
   */
  public Integer ResumeItemPosition_FN (SQLConnection connection, UUID oppKey, Integer restart) throws ReturnStatusException {

    Integer itemposition = null;
    Integer segment = null;

    final String SQL_QUERY1 = "select min(segmentPosition) as segment from testopportunitysegment where _fk_TestOPportunity = ${oppkey} and ((isPermeable = 1 and restorePermOn is not null) or dateExited is null);";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("oppkey", oppKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      segment = record.<Integer> get ("segment");
    }
    if (segment != null) {
      final String SQL_QUERY2 = "select min(position) as itemposition from testeeresponse where _fk_TestOpportunity = ${oppkey} and segment = ${segment}" +
          " and dateGenerated is not null and isValid = 0 and OpportunityRestart = ${restart}; ";
      SqlParametersMaps parms2 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("segment", segment).put ("restart", restart);
      result = executeStatement (connection, SQL_QUERY2, parms2, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        itemposition = record.<Integer> get ("itemposition");
      }
    }
    if (itemposition == null && segment != null) {
      final String SQL_QUERY3 = "Select max(position) as itemposition from testeeresponse where _fk_TestOpportunity = ${oppkey} and segment = ${segment}" +
          " and dateGenerated is not null and OpportunityRestart = ${restart};";
      SqlParametersMaps parms3 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("segment", segment).put ("restart", restart);
      result = executeStatement (connection, SQL_QUERY3, parms3, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        itemposition = record.<Integer> get ("itemposition");
      }
    }
    if (itemposition == null) {
      final String SQL_QUERY4 = "select min(position) as itemposition from testeeresponse where _fk_TestOpportunity = ${oppkey} and OpportunityRestart = ${restart} and isValid = 0 and dateGenerated is not null;";
      SqlParametersMaps parms4 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("restart", restart);
      result = executeStatement (connection, SQL_QUERY4, parms4, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        itemposition = record.<Integer> get ("itemposition");
      }
    }
    if (itemposition == null) {
      final String SQL_QUERY5 = "select max(position) as itemposition from testeeresponse where _fk_TestOpportunity = ${oppkey} and OpportunityRestart = ${restart} and DateSubmitted is not null;";
      SqlParametersMaps parms5 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("restart", restart);
      result = executeStatement (connection, SQL_QUERY5, parms5, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        itemposition = record.<Integer> get ("itemposition");
      }
    }
    if (itemposition == null) {
      final String SQL_QUERY6 = "select count(*) + 1 as itemposition from testeeresponse  where _fk_TestOpportunity = ${oppkey} and dateGenerated is not null;";
      SqlParametersMaps parms6 = parms1;
      result = executeStatement (connection, SQL_QUERY6, parms6, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        itemposition = record.<Long> get ("itemposition").intValue ();
      }
    }
    return itemposition;
  }

  public SingleDataResultSet T_StartTestOpportunity_SP_Mysql (SQLConnection connection, UUID oppKey, UUID sessionKey, UUID browserId, String formKeyList) throws ReturnStatusException {
    
    final String SQL_QUERY = "call t_starttestopportunity(${oppKey}, ${sessionKey}, ${browserId}, ${formKeyList}, ${debug})";
    SqlParametersMaps params = new SqlParametersMaps ();
    params.put ("oppKey", oppKey);
    params.put ("sessionKey", sessionKey);
    params.put ("browserId", browserId);
    params.put ("formKeyList", formKeyList);
    params.put("debug",0);

    SingleDataResultSet resultSet = executeStatement (connection, SQL_QUERY, params, false).getResultSets ().next ();
    return resultSet;
   }
  
  

  
  /**
   * This method is not being used as t_starttestopportunity is migrated to Mysql stored proc. This is the Java version of the t_starttestopportunity procedure
   * @param connection
   * @param oppKey
   * @param sessionKey
   * @param browserId
   * @param formKeyList
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_StartTestOpportunity_SP (SQLConnection connection, UUID oppKey, UUID sessionKey, UUID browserId, String formKeyList) throws ReturnStatusException {

    Date now = _dateUtil.getDateWRetStatus (connection);
    Integer interfaceTimeout = null;
    Integer requestInterfaceTimeout = null;
    Date datechanged = null;
    Date datestarted = null;
    Integer rcnt = null; // restart count
    String excludeItemTypes = null;
    Date fromTime = null; // max of @fromTimes
    Integer delay = null;
    Integer gpRestarts = null; // number of restarts within grace period
    String status = null;
    Integer sessionType = null;

    _Ref<String> error = new _Ref<> ();
    Integer testLength = null;
    String testKey = null;
    String testId = null;
    String clientName = null;
    String query = null;
    Float ability = null;
    Integer operationalLength = null;
    Boolean removeUnanswered = null;
    String localhostname = _commonDll.getLocalhostName ();

    if (sessionKey != null && browserId != null) {
      _ValidateTesteeAccessProc_SP (connection, oppKey, sessionKey, browserId, true, error);
      if (error.get () != null) {
        return _commonDll._ReturnError_SP (connection, null, "T_StartTestOpportunity", error.get (), null, oppKey, "_ValidateTesteeAccess", "denied");
      }
    }
    final String SQL_QUERY1 = "select status as status, DateStarted as datestarted, DateChanged as datechanged, Restart as rcnt, GracePeriodRestarts as gpRestarts," +
        " maxitems as testlength, _efk_AdminSubject as testkey, clientname, _efk_TestID as testID from testopportunity where _Key = ${oppkey};";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("oppkey", oppKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, true).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      status = record.<String> get ("status");
      datestarted = record.<Date> get ("datestarted");
      datechanged = record.<Date> get ("datechanged");
      rcnt = record.<Integer> get ("rcnt");
      gpRestarts = record.<Integer> get ("gpRestarts");
      testLength = record.<Integer> get ("testlength");
      testKey = record.<String> get ("testkey");
      clientName = record.<String> get ("clientname");
      testId = record.<String> get ("testID");
    }
    Boolean scoreByTds = _commonDll.ScoreByTDS_FN (connection, clientName, testId);

    final String SQL_QUERY2 = "select sessionType from session where _Key = ${sessionKey};";
    SqlParametersMaps parms2 = new SqlParametersMaps ().put ("sessionKey", sessionKey);
    result = executeStatement (connection, SQL_QUERY2, parms2, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      sessionType = record.<Integer> get ("sessionType");
    }
    final String SQL_QUERY3 = "select deleteUnansweredItems as removeUnanswered from ${ConfigDB}.client_testproperties where clientname = ${clientname} and TestID = ${testID};";
    String finalQuery = fixDataBaseNames (SQL_QUERY3);
    SqlParametersMaps parms3 = new SqlParametersMaps ().put ("clientname", clientName).put ("testID", testId);
    result = executeStatement (connection, finalQuery, parms3, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      removeUnanswered = record.<Boolean> get ("removeUnanswered");
    }
    final String SQL_QUERY4 = "select maxItems as operationalLength from ${ItemBankDB}.tblsetofadminsubjects where _Key = ${testkey};";
    finalQuery = fixDataBaseNames (SQL_QUERY4);
    SqlParametersMaps parms4 = new SqlParametersMaps ().put ("testkey", testKey);
    result = executeStatement (connection, finalQuery, parms4, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      operationalLength = record.<Integer> get ("operationalLength");
    }
    final String SQL_QUERY5 = "select OppRestart as delay, interfaceTimeout, requestInterfaceTimeout from timelimits where _efk_TestID = ${testID} and clientname = ${clientname};";
    SqlParametersMaps parms5 = parms3;
    result = executeStatement (connection, SQL_QUERY5, parms5, true).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      delay = record.<Integer> get ("delay");
      interfaceTimeout = record.<Integer> get ("interfaceTimeout");
      requestInterfaceTimeout = record.<Integer> get ("requestInterfaceTimeout");
    }
    if (delay == null) {
      final String SQL_QUERY6 = "select OppRestart as delay, interfaceTimeout, requestInterfaceTimeout from timelimits where _efk_TestID is null and clientname = ${clientname};";
      SqlParametersMaps parms6 = new SqlParametersMaps ().put ("clientname", clientName);
      result = executeStatement (connection, SQL_QUERY6, parms6, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        delay = record.<Integer> get ("delay");
        interfaceTimeout = record.<Integer> get ("interfaceTimeout");
        requestInterfaceTimeout = record.<Integer> get ("requestInterfaceTimeout");
      }
    }
    if (delay == null) {
      delay = 1;
    }

    if (DbComparator.notEqual (status, "approved")) {
      return _commonDll._ReturnError_SP (connection, clientName, "T_StartTestOpportunity", "Test start/restart not approved by test administrator", null, oppKey, "T_StartTestOpportunity",
          "denied");
    }
    _Ref<Float> abilityRef = new _Ref<> ();
    _GetInitialAbility_SP (connection, oppKey, abilityRef);

    ability = abilityRef.get ();
    _Ref<String> reason = new _Ref<> ();
    _Ref<Integer> testLengthRef = new _Ref<> ();

    if (datestarted == null) {
      _InitializeOpportunity_SP (connection, oppKey, testLengthRef, reason, formKeyList);
      if (reason.get () != null) {
        _commonDll._LogDBError_SP (connection, "T_StartTestOpportunity", reason.get (), null, null, null, oppKey, clientName, null);
        return _commonDll._ReturnError_SP (connection, clientName, "T_StartTestOpportunity", reason.get (), null, oppKey, "T_StartTestOpportunity", "failed");
      }
      testLength = testLengthRef.get ();

      // String sessionDB = getAppSettings ().get ("TDSSessionDBName");
      String sessionDB = getTdsSettings ().getTDSSessionDBName ();
      final String SQL_INSERT1 = "insert into ${ArchiveDB}.opportunityaudit (_fk_TestOpportunity, _fk_Session, hostname, AccessType, _fk_Browser, dateaccessed,  dbname) "
          + " values (${oppkey}, ${sessionKey}, ${host}, 'started', ${browserID}, now(3),  ${dbname});";
      finalQuery = fixDataBaseNames (SQL_INSERT1);
      SqlParametersMaps parms7 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("sessionkey", sessionKey).put ("host", localhostname).
          put ("browserID", browserId).put ("localhost", _commonDll.getLocalhostName ()).put ("dbname", sessionDB);
      executeStatement (connection, finalQuery, parms7, false).getUpdateCount ();

      final String SQL_QUERY7 = "select bigtoint(1) as startPosition, ${started} as status, bigtoint(0) as restart, "
          + " bigtoint(${testLength}) as TestLength, cast(null AS CHAR) as reason, "
          + " bigtoint(${interfaceTimeout}) as interfaceTimeout, bigtoint(${delay}) as OppRestart,"
          + " cast(${excludeItemTypes} AS CHAR) as excludeItemTypes, bigtoint(120) as contentloadtimeout, "
          + " bigtoint(${requestInterfaceTimeout}) as requestInterfaceTimeout, "
          + "  prefetch, validateCompleteness, "
          + " (select count(*) from testopportunitysegment where _fk_TestOpportunity = ${oppkey}) as SegmentsN from "
          + " ${ConfigDB}.client_testproperties where clientname = ${clientname} and testID = ${testID};";
      finalQuery = fixDataBaseNames (SQL_QUERY7);
      SqlParametersMaps parms8 = new SqlParametersMaps ().put ("started", "started").put ("clientname", clientName).put ("testID", testId).put ("testLength", testLength).put ("oppkey", oppKey)
          .put ("interfaceTimeout", interfaceTimeout).put ("delay", delay).put ("requestInterfaceTimeout", requestInterfaceTimeout)
          .put ("excludeItemTypes", excludeItemTypes);
      result = executeStatement (connection, finalQuery, parms8, false).getResultSets ().next ();
      // declare additional columns in the result SingleDataResultSet.
      result.addColumn ("ScoreByTDS", SQL_TYPE_To_JAVA_TYPE.BIT);
      result.addColumn ("initialability", SQL_TYPE_To_JAVA_TYPE.FLOAT);
      result.addColumn ("Segments", SQL_TYPE_To_JAVA_TYPE.INT);

      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        record = records.next ();

        record.addColumnValue ("ScoreByTDS", scoreByTds);
        record.addColumnValue ("initialability", ability);
        record.addColumnValue ("Segments", (record.<Long> get ("SegmentsN") == null ? null : record.<Long> get ("SegmentsN").intValue ()));
      }
      _commonDll._LogDBLatency_SP (connection, "T_StartTestOpportunity", now, null, false, null, oppKey, null, clientName, null);
      return result;
    }

    // else this is a restart of an existing opportunity
    fromTime = _TestOppLastActivity_FN (connection, oppKey);
    if (DbComparator.lessThan (minutesDiff (fromTime, now), delay)) {
      gpRestarts = gpRestarts + 1;
    }
    final String SQL_UPDATE1 = "update testopportunity set prevStatus = status,  status = ${started}, Restart = ${rcnt} + 1, DateRestarted = ${now}, DateChanged = ${now},"
        + " GracePeriodRestarts = ${gpRestarts}, maxitems = ${testlength}, waitingForSegment = null where _key = ${oppkey}";
    SqlParametersMaps parms9 = new SqlParametersMaps ().put ("started", "started").put ("rcnt", rcnt).put ("now", now).put ("gpRestarts", gpRestarts).put ("testlength", testLength)
        .put ("oppkey", oppKey);
    executeStatement (connection, SQL_UPDATE1, parms9, false).getUpdateCount ();

    // String sessionDB = getAppSettings ().get ("TDSSessionDBName");
    String sessionDB = getTdsSettings ().getTDSSessionDBName ();
    final String SQL_INSERT2 = "insert into ${ArchiveDB}.opportunityaudit (_fk_TestOpportunity, _fk_Session, hostname, AccessType, _fk_Browser, dateaccessed,  dbname)"
        + " values (${oppkey}, ${sessionKey}, ${host}, concat (${restart}, ${rcnt}), ${browserID}, now(3), ${dbname})";
    finalQuery = fixDataBaseNames (SQL_INSERT2);
    SqlParametersMaps parms10 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("sessionkey", sessionKey).put ("host", localhostname).put ("restart", "restart ")
        .put ("rcnt", String.format ("%d", rcnt + 1)).put ("browserID", browserId).put ("dbname", sessionDB);
    executeStatement (connection, finalQuery, parms10, false).getUpdateCount ();

    if (DbComparator.isEqual (sessionType, 1)) {
      final String SQL_UPDATE2 = "update testeeresponse set OpportunityRestart = ${rcnt} + 1 where _fk_TestOpportunity = ${oppkey};";
      SqlParametersMaps parms11 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("rcnt", rcnt);
      executeStatement (connection, SQL_UPDATE2, parms11, false).getUpdateCount ();

    } else if (DbComparator.lessThan (minutesDiff (fromTime, now), delay)) {
      final String SQL_UPDATE3 = "update testeeresponse set OpportunityRestart = ${rcnt} + 1 where _fk_TestOpportunity = ${oppkey} and OpportunityRestart = ${rcnt};";
      SqlParametersMaps parms12 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("rcnt", rcnt);
      executeStatement (connection, SQL_UPDATE3, parms12, false).getUpdateCount ();

    } else if (DbComparator.isEqual (removeUnanswered, true)) {
      _RemoveUnanswered_SP (connection, oppKey);
    }
    // else only item groups with an unanswered item are available in this
    // login, but all items in those groups are available
    rcnt = rcnt + 1; // CAREFUL! This assignment required to call the procedure
                     // below
    _UnfinishedResponsePages_SP (connection, oppKey, rcnt, true);
    Integer resumeItemPos = ResumeItemPosition_FN (connection, oppKey, rcnt);

    final String SQL_QUERY8 = "select ${started} as status, bigtoint(${rcnt})  as restart, bigtoint(${testLength}) as TestLength, "
        + " cast(null AS CHAR) as reason, bigtoint(${interfaceTimeout}) as interfaceTimeout, bigtoint(${delay}) as OppRestart, "
        + " cast(${excludeItemTypes} AS CHAR) as excludeItemTypes, bigtoint(120) as contentloadtimeout,  "
        + " bigtoint(${requestInterfaceTimeout}) as requestInterfaceTimeout, prefetch, validateCompleteness,"
        + " bigtoint(${resumeItemPos}) as startPosition, (select count(*) from testopportunitysegment where _fk_TestOpportunity = ${oppkey}) as SegmentsN"
        + " from ${ConfigDB}.client_testproperties where clientname = ${clientname} and testID = ${testID};";
    finalQuery = fixDataBaseNames (SQL_QUERY8);
    SqlParametersMaps parms13 = new SqlParametersMaps ().put ("started", "started").put ("rcnt", rcnt).put ("testLength", testLength).put ("interfaceTimeout", interfaceTimeout).put ("delay", delay)
        .put ("excludeItemTypes", excludeItemTypes).put ("requestInterfaceTimeout", requestInterfaceTimeout)
        .put ("resumeItemPos", resumeItemPos).put ("oppkey", oppKey).put ("clientname", clientName).put ("testID", testId);

    result = executeStatement (connection, finalQuery, parms13, false).getResultSets ().next ();
    // declare additional columns in the result SingleDataResultSet.
    result.addColumn ("ScoreByTDS", SQL_TYPE_To_JAVA_TYPE.BIT);
    result.addColumn ("initialability", SQL_TYPE_To_JAVA_TYPE.FLOAT);
    result.addColumn ("Segments", SQL_TYPE_To_JAVA_TYPE.INT);

    Iterator<DbResultRecord> records = result.getRecords ();
    while (records.hasNext ()) {
      record = records.next ();
      record.addColumnValue ("ScoreByTDS", scoreByTds);
      record.addColumnValue ("initialability", ability);
      record.addColumnValue ("Segments", (record.<Long> get ("SegmentsN") == null ? null : record.<Long> get ("SegmentsN").intValue ()));
    }
    _commonDll._LogDBLatency_SP (connection, "T_StartTestOpportunity", now, null, false, null, oppKey, null, clientName, null);
    return result;
  }

  public boolean _AllowAnonymousTestee_FN (SQLConnection connection, String clientname) throws ReturnStatusException {

    final String SQL_QUERY1 = "select  clientname from _externs where clientname = ${clientname} and environment = 'SIMULATION' limit 1";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("clientname", clientname);

    if (exists (executeStatement (connection, SQL_QUERY1, parms1, false))) {
      return true;
    }

    Integer allow = null;
    final String SQL_QUERY2 = "select IsOn as allow from ${ConfigDB}.client_systemflags F, externs E "
        + " where E.clientname = ${clientname} and F.ClientName=${clientname} and (AuditOBject='AnonymousTestee' )";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("clientname", clientname);

    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY2), parms2, true).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      allow = record.<Integer> get ("allow");
    }
    if (DbComparator.isEqual (allow, 1))
      return true;

    return false;
  }

  public void _CanOpenExistingOpportunity_SP (SQLConnection connection, String client, Long testee, String testID, UUID sessionID, Integer maxOpportunities,
      _Ref<Integer> numberRef, _Ref<String> msgRef) throws ReturnStatusException {
    long startTime = System.currentTimeMillis ();
    long startTimeLong= System.currentTimeMillis ();
    Date today = _dateUtil.getDateWRetStatus (connection);

    numberRef.set (0);
    msgRef.set (null);
    long ocnt = 0;
    // First check to see if there are any existing opportunities to open
    final String SQL_QUERY1 = "select count(*) as ocnt from testopportunity where _efk_Testee = ${testee} and _efk_TestID = ${testID} and clientname = ${client} and dateDeleted is null";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("testee", testee).put ("testid", testID).put ("client", client);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      ocnt = record.<Long> get ("ocnt");
    }
    if (ocnt == 0)
      return; // Open Existing exit 1
    
    
    Integer lastopp = null;
    String laststatus = null;
    UUID lastsession = null;
    Date dateChanged = null, dateStarted = null;
    // pick up info on the most recent opportunity
    final String SQL_QUERY2 = "select Opportunity as lastopp, status as laststatus, _fk_Session as lastsession, DateChanged, DateStarted "
        + " from testopportunity where _efk_Testee = ${testee} and _efk_TestID = ${testID} and Opportunity = ${ocnt} and clientname = ${client} and dateDeleted is null";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("testee", testee).put ("testid", testID).put ("client", client).put ("ocnt", ocnt);

    result = executeStatement (connection, SQL_QUERY2, parms2, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      lastopp = record.<Integer> get ("lastopp");
      laststatus = record.<String> get ("laststatus");
      lastsession = record.<UUID> get ("lastsession");
      dateChanged = record.<Date> get ("dateChanged");
      dateStarted = record.<Date> get ("dateStarted");
    }
    // last opportunity terminated normally
    final String SQL_QUERY3 = "select status from statuscodes where `usage` = 'opportunity' and stage = 'closed' and status = ${laststatus}";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("laststatus", laststatus);
    if (exists (executeStatement (connection, SQL_QUERY3, parms3, true))) {
      return; // Open Existing exit 2
    }
    Integer lastsessiontype = null, thissessiontype = null;
    // Only allow resuming a test in a session of the same type as opp was
    // opened in
    final String SQL_QUERY4 = " select  sessionType as lastsessiontype from session where _Key = ${lastsession}";
    SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("lastsession", lastsession);
    result = executeStatement (connection, SQL_QUERY4, parms4, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      lastsessiontype = record.<Integer> get ("lastsessiontype");
    }
    final String SQL_QUERY5 = "select  sessionType as thissessiontype  from session where _Key = ${sessionID}";
    SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("sessionID", sessionID);
    result = executeStatement (connection, SQL_QUERY5, parms5, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      thissessiontype = record.<Integer> get ("thissessiontype");
    }
    if (DbComparator.notEqual (lastsessiontype, thissessiontype)) {
      _commonDll._FormatMessage_SP (connection, client, "ENU", "_CanOpenTestOpportunity",
          "You must continue the test in the same type of session it was started in.", msgRef, null, null, null, null);
      return;
    }
    // final security check;
    // if opportunity is not in use, then we can open it
    final String SQL_QUERY6 = "select status from statuscodes where `usage` = 'opportunity' and stage = 'inactive' and status = ${laststatus}";
    SqlParametersMaps parms6 = (new SqlParametersMaps ()).put ("laststatus", laststatus);
    if (exists (executeStatement (connection, SQL_QUERY6, parms6, true))) {
      numberRef.set (lastopp);
      return; // Open Existing exit 3
    }
    // else check for possible opportunity 'hijacking' of in use opportunity
    // if at least 1 day has elapsed since last activity, or
    // is resuming in same session as previous, or
    // last session is closed
    // then allow resume
    if (sessionID == null)
      return; // Open Existing exit 6

    String lastsessionstatus = null;
    Date lastsessionend = null;
    final String SQL_QUERY7 = "select  status as lastsessionstatus, DateEnd as lastsessionend from session where _Key = ${lastsession}";
    SqlParametersMaps parms7 = (new SqlParametersMaps ()).put ("lastsession", lastsession);
    result = executeStatement (connection, SQL_QUERY7, parms7, true).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      lastsessionstatus = record.<String> get ("lastsessionstatus");
      lastsessionend = record.<Date> get ("lastsessionend");
    }
    if (DbComparator.greaterOrEqual (daysDiff (dateChanged, today), 1) || DbComparator.isEqual (lastsession, sessionID)
        || DbComparator.isEqual ("closed", lastsessionstatus) || DbComparator.greaterThan (today, lastsessionend)) {
      // if (daysDiff (dateChanged, today) >= 1 || (lastsession != null &&
      // sessionID.equals (lastsession))
      // || "closed".equalsIgnoreCase (lastsessionstatus) || (lastsessionend !=
      // null && today.compareTo (lastsessionend) > 0)) {Take
      numberRef.set (lastopp);
      return; // Open Existing exit 4
    }
    numberRef.set (0);
    _commonDll._LogDBLatency_SP (connection, "_CanOpenExistingOpportunity", today, testee, true, null, null, sessionID, null, null);

    _commonDll._FormatMessage_SP (connection, client, "ENU", "_CanOpenTestOpportunity", "Current opportunity is active", msgRef, null, null, null, null);
  }

  public void _CanOpenNewOpportunity_SP (SQLConnection connection, String client, Long testee, String testID, Integer maxOpportunities, Integer delayDays,
      _Ref<Integer> numberRef, _Ref<String> msgRef, UUID session) throws ReturnStatusException {
    long startTime = System.currentTimeMillis ();
    Date today = _dateUtil.getDateWRetStatus (connection);
    numberRef.set (0);
    msgRef.set (null);

    String environment = _commonDll.getExternsColumnByClientName (connection, client, "environment");
    long ocnt = 0;
    // First check to see if there are any existing opportunities to open
    final String SQL_QUERY1 = "select count(*) as ocnt from testopportunity where _efk_Testee = ${testee} and _efk_TestID = ${testID} and clientname = ${client} and dateDeleted is null";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("testee", testee).put ("testid", testID).put ("client", client);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, true).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      ocnt = record.<Long> get ("ocnt");
    }
    if (ocnt == 0) {
      if (DbComparator.lessThan (ocnt, maxOpportunities) || DbComparator.isEqual ("SIMULATION", environment))
        numberRef.set (1);
      else if (DbComparator.notEqual ("SIMULATION", environment))
        _commonDll._FormatMessage_SP (connection, client, "ENU", "_CanOpenTestOpportunity", "No opportunities are available for this test", msgRef, null, null, null, null);
      return;
    }

    Integer lastopp = null;
    final String SQL_QUERY2 = "select max(Opportunity) as lastopp from testopportunity "
        + " where _efk_Testee = ${testee} and _efk_TestID = ${testID} and Opportunity = ${ocnt} and clientname = ${clientname} and dateDeleted is null";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("testee", testee).put ("testid", testID).put ("clientname", client).put ("ocnt", ocnt);
    result = executeStatement (connection, SQL_QUERY2, parms2, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      lastopp = record.<Integer> get ("lastopp");
    }
    // pick up info on the most recent opportunity
    Date dateCompleted = null, dateStarted = null;
    String laststatus = null;
    UUID lastoppkey = null;
    final String SQL_QUERY3 = "select  status as laststatus, DateCompleted, DateStarted, _key as lastoppkey "
        + " from testopportunity where _efk_Testee = ${testee} and _efk_TestID = ${testID} and Opportunity = ${lastopp} and clientname = ${clientname} and dateDeleted is null";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("testee", testee).put ("testid", testID).put ("clientname", client).put ("lastopp", lastopp);

    result = executeStatement (connection, SQL_QUERY3, parms3, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      laststatus = record.<String> get ("laststatus");
      lastoppkey = record.<UUID> get ("lastoppkey");
      dateCompleted = record.<Date> get ("dateCompleted");
      dateStarted = record.<Date> get ("dateStarted");
    }
    final String SQL_QUERY4 = "select status from statuscodes where `usage` = 'opportunity' and stage = 'closed' and status = ${laststatus}";
    SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("laststatus", laststatus);
    if (exists (executeStatement (connection, SQL_QUERY4, parms4, false))) {
      if (DbComparator.isEqual ("SIMULATION", environment)) {
        numberRef.set (lastopp + 1);
        return;
      }

      Integer dDiff = daysDiff (dateCompleted, today);
      if (DbComparator.lessThan (lastopp, maxOpportunities) && (dateCompleted == null || DbComparator.greaterThan (dDiff, delayDays))) {
        numberRef.set (lastopp + 1);
      }

      else if (DbComparator.greaterOrEqual (lastopp, maxOpportunities)) {
        _commonDll._FormatMessage_SP (connection, client, "ENU", "_CanOpenTestOpportunity", "All opportunities have been used for this test", msgRef, null, null, null, null);
      } else {
        String arg = null;

        if (dateCompleted != null) {
          Calendar c = Calendar.getInstance ();
          c.setTime (dateCompleted);
          c.add (Calendar.DATE, delayDays);
          Date dateCompldteddAdjusted = c.getTime ();
          arg = dateCompldteddAdjusted.toString ();
        }
        _commonDll._FormatMessage_SP (connection, client, "ENU", "_CanOpenTestOpportunity", "Your next test opportunity is not yet available.", msgRef, arg, null, null, null);
      }
      return;
    }
    _commonDll._LogDBLatency_SP (connection, "_CanOpenNewOpportunity", today, testee, true, null, null, null, null, null);
    //_logger.info ("<<<<<<<<< _CanOpenNewOpportunity Total Execution Time : "+((System.currentTimeMillis ()-startTime)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
  }

  public SingleDataResultSet _GetTesteeTestModes_SP (SQLConnection connection, String clientname, String testID, Long testee, Integer sessionType) throws ReturnStatusException {

    return _GetTesteeTestModes_SP (connection, clientname, testID, testee, sessionType, null);
  }
  
  public SingleDataResultSet _GetTesteeTestModes_SP (SQLConnection connection, String clientname, String testID, Long testee, Integer sessionType,
      String modeList) throws ReturnStatusException {
    Date starttime = _dateUtil.getDateWRetStatus (connection);
    long startTimeLong = System.currentTimeMillis ();
    SingleDataResultSet result = null;
    DataBaseTable ctwTbl = GetCurrentTestWindows_FN (connection, clientname, testID, sessionType);
    // guest testees have no RTS data. If allowed into the system this far, then
    // provide them all modes
    //_logger.info ("<<<<<<<<< _GetTesteeTestModes_SP 1 Total Execution Time : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    if (testee < 0) {
      final String SQL_QUERY1 = "select windowID, windowMax, startDate, endDate, mode, modeMax, testkey from ${ctwTblName}";
      Map<String, String> unquotedParms = new HashMap<String, String> ();
      unquotedParms.put ("ctwTblName", ctwTbl.getTableName ());
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY1, unquotedParms), null, false).getResultSets ().next ();
      connection.dropTemporaryTable (ctwTbl);
      return result;
    }
    //_logger.info ("<<<<<<<<< _GetTesteeTestModes_SP 2 Total Execution Time : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    // It is an error for @require = 1 and @modeField = null, however, it is
    // also highly unlikely since both fields have default values in TDSCONFIGS
    String tideId = null;
    String modeField = null;
    Boolean requiremode = null;
    Boolean requiremodeWindow = null;
    final String SQL_QUERY2 = "select TIDE_ID as tideID,  requireRTSmodeWindow as requiremodeWindow,  RTSmodeField as modeField, requireRTSmode as requiremode "
        + " from ${ConfigDB}.client_testproperties where clientname = ${clientname} and TestID = ${testID}";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testID", testID);

    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY2), parms2, false).getResultSets ().next ();
    //_logger.info ("<<<<<<<<< _GetTesteeTestModes_SP 3 Total Execution Time : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      tideId = record.<String> get ("tideId");
      modeField = record.<String> get ("modeField");
      requiremode = record.<Boolean> get ("requiremode");
      requiremodeWindow = record.<Boolean> get ("requiremodeWindow");
    }
    // -- this block sets up debugging capabilities by simulating conditions we
    // expect to find in the RTS
    if (modeList != null) {
      if (modeList.indexOf ('&') > -1)
        requiremodeWindow = true;
      else {
        requiremode = true;
        requiremodeWindow = false;
      }

    } else if ((DbComparator.isEqual (requiremodeWindow, true) || DbComparator.isEqual (requiremode, true)) && modeField != null) {
      _Ref<String> modeListRef = new _Ref<String> ();

      _rtsDll._GetRTSAttribute_SP (connection, clientname, testee, modeField, modeListRef);
      modeList = modeListRef.get ();
    }
    //_logger.info ("<<<<<<<<< _GetTesteeTestModes_SP 4 Total Execution Time : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    DataBaseTable modesTbl = getDataBaseTable ("gttmModes").addColumn ("rtsval", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200).addColumn ("WID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100)
        .addColumn ("asgnMode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50);
    connection.createTemporaryTable (modesTbl);
    // TODO Elena: I added tideId check below since it does not have sense to
    // look for like records without it.
    //_logger.info ("<<<<<<<<< _GetTesteeTestModes_SP 5 Total Execution Time : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    if (modeList != null && tideId != null) {
      Character delim = ';';
      final String[] rows = _commonDll._BuildTableAsArray (modeList, delim.toString (), -1);
      final String tideIdFinal = tideId;
      executeMethodAndInsertIntoTemporaryTable (connection, new AbstractDataResultExecutor ()
      {
        @Override
        public SingleDataResultSet execute (SQLConnection connection) throws ReturnStatusException {
          List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
          for (String row : rows)
          {
            // each component is of the form <TIDEID>:<Mode> OR
            // <TIDEID>&<WindowID>:<Mode>
            // First, split the string for the TIDE_ID (which is required)
            // insert into @modes (rtsval, asgnMode)
            // select left(record, charindex(':', record) - 1), right(record,
            // len(record) - charindex(':', record))
            // from @rtsVals where record like @tideID + ':%' or record like
            // @tideID + '&%:%';

            CaseInsensitiveMap<Object> record = new CaseInsensitiveMap<Object> ();
            String likeRec1 = String.format ("%s:", tideIdFinal);
            String likeRec2 = String.format ("%s&", tideIdFinal);
            int idx = 0;
            if ((row.startsWith (likeRec1) || row.startsWith (likeRec2)) && (idx = row.indexOf (":")) > -1) {// i.e.
                                                                                                             // found
              String rtsval = row.substring (0, idx);
              String asgnMode = row.substring (idx + 1);
              record.put ("rtsval", rtsval);
              record.put ("asgnMode", asgnMode);
              // Now, parse the windowID out, if applicable
              int idxWid = rtsval.indexOf ('&');
              if (idxWid > -1) {
                String wid = rtsval.substring (idxWid + 1);
                record.put ("WID", wid);
              }
              resultList.add (record);
            }

          }
          SingleDataResultSet rs = new SingleDataResultSet ();
          rs.addColumn ("rtsval", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
          rs.addColumn ("asgnMode", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
          rs.addColumn ("WID", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
          rs.addRecords (resultList);
          return rs;
        }
      }, modesTbl, false); // temp table already created
    }
    //_logger.info ("<<<<<<<<< _GetTesteeTestModes_SP 6 Total Execution Time : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    Map<String, String> unquotedParms3 = new HashMap<String, String> ();
    unquotedParms3.put ("modesTbl", modesTbl.getTableName ());
    unquotedParms3.put ("ctwTbl", ctwTbl.getTableName ());
    // RTS has modes assigned to specific windows in this first case
    if (DbComparator.isEqual (requiremodeWindow, true)) {
      final String SQL_QUERY3 = "select distinct windowID, windowMax, startDate, endDate,  mode, modeMax, testkey from ${modesTbl}, ${ctwTbl} "
          + " where WID = windowID and mode = asgnMode";

      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY3, unquotedParms3), null, false).getResultSets ().next ();
      connection.dropTemporaryTable (modesTbl);
      connection.dropTemporaryTable (ctwTbl);
      _commonDll._LogDBLatency_SP (connection, "_GetTesteeTestModes", starttime, testee, true, null, null, null, clientname, null);
      //_logger.info ("<<<<<<<<< _GetTesteeTestModes_SP 7 Total Execution Time : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
      return result;

    } else if (DbComparator.isEqual (requiremode, true)) {
      // window is not assigned, just the mode, so just join the modes
      final String SQL_QUERY4 = "select distinct windowID, windowMax, startDate, endDate,  mode, modeMax, testkey "
          + "from ${modesTbl}, ${ctwTbl} where mode = asgnMode";
      Map<String, String> unquotedParms4 = unquotedParms3;
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY4, unquotedParms4), null, false).getResultSets ().next ();
      connection.dropTemporaryTable (modesTbl);
      connection.dropTemporaryTable (ctwTbl);
      _commonDll._LogDBLatency_SP (connection, "_GetTesteeTestModes", starttime, testee, true, null, null, null, clientname, null);
      //_logger.info ("<<<<<<<<< _GetTesteeTestModes_SP 8 Total Execution Time : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
      return result;

    }
    // else there is no specific mode assignment to the testee: return all
    // active windows all modes
    final String SQL_QUERY5 = "select windowID, windowMax, startDate, endDate, mode, modeMax, testkey from ${ctwTbl}";
    Map<String, String> unquotedParms5 = new HashMap<String, String> ();
    unquotedParms5.put ("ctwTbl", ctwTbl.getTableName ());

    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY5, unquotedParms5), null, false).getResultSets ().next ();
    //_logger.info ("<<<<<<<<< _GetTesteeTestModes_SP 9 Total Execution Time : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    connection.dropTemporaryTable (modesTbl);
    connection.dropTemporaryTable (ctwTbl);
    _commonDll._LogDBLatency_SP (connection, "_GetTesteeTestModes", starttime, testee, true, null, null, null, clientname, null);
    //_logger.info ("<<<<<<<<< _GetTesteeTestModes_SP Total Execution Time : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    return result;
  }

  /**
   * @param connection
   * @param clientname
   * @param testID
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   */

  public DataBaseTable GetCurrentTestWindows_FN (SQLConnection connection, String clientname, String testID, Integer sessionType) throws ReturnStatusException {
    long startTime = System.currentTimeMillis ();
    DataBaseTable tbl = getDataBaseTable ("currentTestWindows").addColumn ("windowMax", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("windowID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("startDate", SQL_TYPE_To_JAVA_TYPE.DATETIME).
        addColumn ("endDate", SQL_TYPE_To_JAVA_TYPE.DATETIME).addColumn ("mode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).
        addColumn ("testkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).addColumn ("modeMax", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("windowSession", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("modeSession", SQL_TYPE_To_JAVA_TYPE.INT);

    connection.createTemporaryTable (tbl);
    Date now = _dateUtil.getDateWRetStatus (connection);

    final String SQL_QUERY = "insert into ${tblName} (windowmax, windowid,startdate,enddate,mode,testkey,modemax,windowsession,modesession) "
        + " (select distinct W.numopps as windowMax, W.windowID "
        + ", case when W.startDate is null then ${now} else ( W.startDate + INTERVAL shiftWindowStart DAY) end as startDate "
        + ", case when W.endDate is null then ${now} else ( W.endDate + INTERVAL shiftWindowEnd DAY) end as endDate "
        + ", M.mode, M.testkey, M.maxopps as modeMax"
        + ", W.sessionType as windowSession, M.sessionType as modeSession "
        + " from ${ConfigDB}.client_testwindow W, ${ConfigDB}.client_testmode M, _externs E "
        + " where W.clientname = ${clientname} and W.testID = ${testID} and E.clientname = ${clientname} "
        + "  and ${now} between case when W.startDate is null then ${now} else ( W.startDate + INTERVAL shiftWindowStart DAY)end "
        + "               and case when W.endDate is null then ${now} else ( W.endDate + INTERVAL shiftWindowEnd DAY) end "
        + "  and M.clientname = ${clientname} and M.testID = ${testID} and (M.sessionType = -1 or M.sessionType = ${sessionType}) "
        + " and (W.sessionType = -1 or W.sessionType = ${sessionType}))";

    SqlParametersMaps parms = (new SqlParametersMaps ()).put ("now", now).put ("clientname", clientname).put ("testID", testID).put ("sessionType", sessionType);
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("tblName", tbl.getTableName ());

    final String query = fixDataBaseNames (SQL_QUERY);
    executeStatement (connection, fixDataBaseNames (query, unquotedParms), parms, false).getUpdateCount ();
    //_logger.info ("<<<<<<<<< GetCurrentTestWindows_FN Total Execution Time : "+((System.currentTimeMillis ()-startTime)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    return tbl;
  }

  public SingleDataResultSet _GetTesteeTestWindows_SP (SQLConnection connection, String clientname, String testID, Long testee, Integer sessionType) throws ReturnStatusException {
    return _GetTesteeTestWindows_SP (connection, clientname, testID, testee, sessionType, null, null);
  }

  public SingleDataResultSet _GetTesteeTestWindows_SP (SQLConnection connection, String clientname, String testID, Long testee, Integer sessionType,
      String windowList, String formList) throws ReturnStatusException {
    Date starttime = _dateUtil.getDateWRetStatus (connection);
    long startTimeLong = System.currentTimeMillis ();
    SingleDataResultSet result = null;
    DataBaseTable ctwTbl = GetCurrentTestWindows_FN (connection, clientname, testID, sessionType);
    //_logger.info ("<<<<<<<<< _GetTesteeTestWindows_SP 0 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    // -- for guest testees there is no registration data to be found
    if (testee < 0) {
      final String SQL_QUERY1 = "select windowID, windowMax, startDate, endDate, cast(null AS CHAR) as formkey, mode, modeMax, testkey from ${ctwTblName}";
      Map<String, String> unquotedParms = new HashMap<String, String> ();
      unquotedParms.put ("ctwTblName", ctwTbl.getTableName ());
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY1, unquotedParms), null, false).getResultSets ().next ();
      connection.dropTemporaryTable (ctwTbl);
      //_logger.info ("<<<<<<<<< _GetTesteeTestWindows_SP 1 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
      return result;
    }
    Boolean requireWindow = null;
    String windowField = null, tideId = null;
    final String SQL_QUERY2 = " select RTSWindowField as windowField,  requireRTSWindow as requireWindow,  TIDE_ID  as tideID "
        + " from ${ConfigDB}.client_testproperties where clientname = ${clientname} and TestID = ${testID}";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testid", testID);

    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY2), parms2, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      tideId = record.<String> get ("tideId");
      windowField = record.<String> get ("windowField");
      requireWindow = record.<Boolean> get ("requireWindow");
    }
    //_logger.info ("<<<<<<<<< _GetTesteeTestWindows_SP 2 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    Boolean isFormTest = false;
    final String SQL_QUERY3 = "select  testID from ${ConfigDB}.client_testformproperties where clientname = ${clientname} and testID = ${testID} limit 1";
    SqlParametersMaps parms3 = parms2;
    if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY3), parms3, false)))
      isFormTest = true;
    //_logger.info ("<<<<<<<<< _GetTesteeTestWindows_SP 3 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    // -- form+window assignment takes precedence over window assignment.
    // -- This is a bit complicated. Window assignment is pegged to one or more
    // forms, and we have to match forms up with their start/end dates.
    // -- So in order to qualify for a test window, the student must have a form
    // assignment that is active
    // -- We do NOT check whether the form has been used or not since forms may
    // be administered more than one, depending on the number of opportunities
    // allowed by the window and the test
    if (windowList != null) // -- use for debugging
      requireWindow = true;
    else if (windowField == null) // -- sanity check
      requireWindow = false;
    DataBaseTable windowsTbl = getDataBaseTable ("gttwWindows").addColumn ("WID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("form", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50);
    connection.createTemporaryTable (windowsTbl);
    //_logger.info ("<<<<<<<<< _GetTesteeTestWindows_SP 4 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    // -- independent window selection is used in several different places for
    // form and adaptive tests. Set it up here
    if (DbComparator.isEqual (requireWindow, true)) {
      if (windowList == null) {
        _Ref<String> windowListRef = new _Ref<String> ();

        _rtsDll._GetRTSAttribute_SP (connection, clientname, testee, windowField, windowListRef);
        windowList = windowListRef.get ();
      }
      //_logger.info ("<<<<<<<<< _GetTesteeTestWindows_SP 5 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
      // TODO Elena: I added windowList and tideId checks
      if (windowList != null && tideId != null) {

        Character delim = ';';
        final String[] rows = _commonDll._BuildTableAsArray (windowList, delim.toString (), -1);
        final String tideIdFinal = tideId;

        executeMethodAndInsertIntoTemporaryTable (connection, new AbstractDataResultExecutor ()
        {
          @Override
          public SingleDataResultSet execute (SQLConnection connection) throws ReturnStatusException {
            List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
            for (String row : rows)
            {
              CaseInsensitiveMap<Object> record = new CaseInsensitiveMap<Object> ();
              String likeRec1 = String.format ("%s:", tideIdFinal);

              int idx = 0;
              if (row.startsWith (likeRec1) && (idx = row.indexOf (':')) != -1) {
                String win = row.substring (idx + 1);
                record.put ("win", win);
                resultList.add (record);
              }
            }
            SingleDataResultSet rs = new SingleDataResultSet ();
            rs.addColumn ("win", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
            rs.addRecords (resultList);
            return rs;
          }
        }, windowsTbl, false); // temp table already created
      }
    }
    //_logger.info ("<<<<<<<<< _GetTesteeTestWindows_SP 6 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    DataBaseTable tbl = GetTestFormWindows_FN (connection, clientname, testID, sessionType);
    //_logger.info ("<<<<<<<<< _GetTesteeTestWindows_SP 6.1 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    final String SQL_QUERY4 = "select  windowID from ${tblName} limit 1";
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("tblName", tbl.getTableName ());

    if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY4, unquotedParms), null, false))) {
      // TODO:Elena remove this latency check, it is for testing only!
      // Date start = new Date ();
      //_logger.info ("<<<<<<<<< _GetTesteeTestWindows_SP 6.2 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
      result = _GetTesteeTestForms_SP (connection, clientname, testID, testee, sessionType, formList);
      //_logger.info ("<<<<<<<<< _GetTesteeTestWindows_SP 6.3 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
      // Date end = new Date ();
      // long diff = end.getTime () - start.getTime ();
      // System.err.println (String.format
      // ("_GetTesteeTestForms called from GetTesteeTestWindows latency: %d, recs number: %d",
      // diff, result.getCount ()));

      _commonDll._LogDBLatency_SP (connection, "_GetTesteeTestWindows", starttime, null, true, null, null, null, clientname, null);
      connection.dropTemporaryTable (tbl);
      connection.dropTemporaryTable (windowsTbl);
      connection.dropTemporaryTable (ctwTbl);
      //_logger.info ("<<<<<<<<< _GetTesteeTestWindows_SP 7 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
      return result;
    }
    connection.dropTemporaryTable (tbl);
    //_logger.info ("<<<<<<<<< _GetTesteeTestWindows_SP 8 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    // -- NOT A FIXED FORM TEST. Determine if the WINDOW has been assigned to
    // the student
    // -- test windows are recorded by TIDE_ID (in lieu of testID), which is not
    // necessarily unique.
    if (DbComparator.isEqual (requireWindow, true)) {
      // TODO Elena: why do we need to get windowList here? it is not used.
      if (windowList == null) {
        _Ref<String> windowListRef = new _Ref<String> ();

        _rtsDll._GetRTSAttribute_SP (connection, clientname, testee, windowField, windowListRef);
        windowList = windowListRef.get ();
      }
      final String SQL_QUERY5 = "select distinct windowID, windowMax , startDate, endDate, cast(null AS CHAR) as formkey, mode, modeMax, testkey "
          + " from ${windowsTbl}, ${ctwTbl} where WID = windowID";
      Map<String, String> unquotedParms5 = new HashMap<String, String> ();
      unquotedParms5.put ("windowsTbl", windowsTbl.getTableName ());
      unquotedParms5.put ("ctwTbl", ctwTbl.getTableName ());

      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY5, unquotedParms5), null, false).getResultSets ().next ();
      connection.dropTemporaryTable (windowsTbl);
      connection.dropTemporaryTable (ctwTbl);
      _commonDll._LogDBLatency_SP (connection, "_GetTesteeTestWindows", starttime, null, true, null, null, null, clientname, null);
      //_logger.info ("<<<<<<<<< _GetTesteeTestWindows_SP 9 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
      return result;
    }
    // -- not a fixed form test and no special window conditions specific to the
    // testee. Return all windows currently active on this test
    final String SQL_QUERY6 = "select distinct windowID, windowMax , startDate, endDate, cast(null AS CHAR) as formkey, mode, modeMax, testkey from ${ctwTbl}";
    Map<String, String> unquotedParms6 = new HashMap<String, String> ();
    unquotedParms6.put ("ctwTbl", ctwTbl.getTableName ());

    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY6, unquotedParms6), null, false).getResultSets ().next ();
    connection.dropTemporaryTable (windowsTbl);
    connection.dropTemporaryTable (ctwTbl);
    //_logger.info ("<<<<<<<<< _GetTesteeTestWindows_SP 10 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    _commonDll._LogDBLatency_SP (connection, "_GetTesteeTestWindows", starttime, null, true, null, null, null, clientname, null);
    return result;
  }

  public void _IsOpportunityBlocked_SP (SQLConnection connection, String clientname, Long testee, String testID, Integer maxopps, _Ref<String> reasonBlockedRef) throws ReturnStatusException {
    _IsOpportunityBlocked_SP (connection, clientname, testee, testID, maxopps, reasonBlockedRef, 0);
  }

  
  public void _IsOpportunityBlocked_SP_Mysql (SQLConnection connection, String clientname, Long testee, String testID, Integer maxopps, _Ref<String> reasonBlockedRef, Integer sessionType)
      throws ReturnStatusException {
    
 try {
      
      /*String sessionIdStr = sessionId.toString ().replaceAll ("-", "");
      sessionIdStr = String.format ("0x%s", sessionIdStr);*/
      final String SQL_QUERY = "{call _isopportunityblocked(?,?,?,?,?,?,?)}";
      CallableStatement callableStatement = connection.prepareCall(SQL_QUERY);
      callableStatement.setString (1, clientname);
      callableStatement.setLong (2, testee);
      callableStatement.setString (3, testID);
      callableStatement.setInt (4, maxopps);
      callableStatement.registerOutParameter(5, java.sql.Types.VARCHAR);
      callableStatement.setInt (6, sessionType);
      callableStatement.setInt (7, 0);
       
      callableStatement.executeUpdate();
       
      reasonBlockedRef.set (callableStatement.getString(5));
      
    } catch (SQLException e) {
      throw new ReturnStatusException (e);
    }
    
 
  }
  // Original port
  public void _IsOpportunityBlocked_SP (SQLConnection connection, String clientname, Long testee, String testID, Integer maxopps, _Ref<String> reasonBlockedRef, Integer sessionType)
      throws ReturnStatusException {
    // -- This proc is concerned entirely with the current point in time and not
    // with absolutes
    // -- so it applies to both new and existing test opportunities.
    // -- As such it should NEVER BE USED to determine test eligibility
    // if (sessionType == null)
    // sessionType = 0;
    final long startTimeLong = System.currentTimeMillis ();
    Date starttime = _dateUtil.getDateWRetStatus (connection);
    String subject = null;
    final String SQL_QUERY1 = "select subjectname as subject from ${ConfigDB}.client_testproperties "
        + " where clientname = ${clientname} and testID = ${testID}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testid", testID);

    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY1), parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      subject = record.<String> get ("subject");
    }
    //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 1 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    final String SQL_QUERY2 = "select  clientname from _externs where clientname = ${clientname} and environment = 'SIMULATION' limit 1";
    SqlParametersMaps parameters2 = (new SqlParametersMaps ()).put ("clientname", clientname);

    if (exists (executeStatement (connection, SQL_QUERY2, parameters2, false)) == true) {
      return;
    }
    //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 2 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    // -- prepare for match against RTS value-set
    if (subject != null)
      subject = String.format (";%s;", subject.trim ());
    else
      subject = ";;";
    _Ref<String> testeeSubjectsRef = new _Ref<String> ();
    _rtsDll._GetRTSAttribute_SP (connection, clientname, testee, "BLOCKEDSUBJECT", testeeSubjectsRef);
    String testeeSubjects = testeeSubjectsRef.get ();
    if (testeeSubjects != null && testeeSubjects.length () > 0) {

      testeeSubjects = String.format (";%s;", testeeSubjects.trim ());
      if (testeeSubjects.indexOf (subject) > -1) {

        _commonDll._FormatMessage_SP (connection, clientname, "ENU", "_CanOpenTestOpportunity",
            "This test is administratively blocked. Please check with your test administrator.", reasonBlockedRef);
        return;
      }
    }
    //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 3 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    // -- Check for number of tests administered in the current test window.
    // -- There is a maximum number of opportunities for the test over all
    // windows, but also a maximum available to a student in each window

    DataBaseTable modesTbl = getDataBaseTable ("iobModes").addColumn ("WinID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("numopps", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("winmax", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("winopps", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("modeopps", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("startDate", SQL_TYPE_To_JAVA_TYPE.DATETIME).addColumn ("endDate", SQL_TYPE_To_JAVA_TYPE.DATETIME).
        addColumn ("formkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("modetestkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).
        addColumn ("mode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("modemax", SQL_TYPE_To_JAVA_TYPE.INT);
    // Check for number of tests administered in the current test window.
    // There is a maximum number of opportunities for the test over all windows,
    // but also a maximum available to a student in each window
    //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 4.1 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    // WinID, winmax, startDate, endDate, mode, modemax, modeTestKey to table
    // windowID, windowMax, startDate, endDate, mode, modeMax, testkey fromset
    final SingleDataResultSet testModesResult = _GetTesteeTestModes_SP (connection, clientname, testID, testee, sessionType);
    //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 4.2 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    if (testModesResult.getCount () == 0) {
      reasonBlockedRef.set ("NA");
      return;
    }
    testModesResult.resetColumnName (1, "winid");
    testModesResult.resetColumnName (2, "winmax");
    testModesResult.resetColumnName (7, "modetestkey");

    testModesResult.addColumn ("modeopps", SQL_TYPE_To_JAVA_TYPE.INT);
    testModesResult.addColumn ("winopps", SQL_TYPE_To_JAVA_TYPE.INT);
    testModesResult.addColumn ("numopps", SQL_TYPE_To_JAVA_TYPE.INT);
    testModesResult.addColumn ("formkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    Iterator<DbResultRecord> records = testModesResult.getRecords ();
    while (records.hasNext ()) {
      record = records.next ();
      record.addColumnValue ("winopps", 0);
      record.addColumnValue ("numopps", 0);
      record.addColumnValue ("modeopps", 0);
      record.addColumnValue ("formkey", null);
    }
    connection.createTemporaryTable (modesTbl);
    //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 5 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    insertBatch (connection, modesTbl.generateInsertStatement (), testModesResult, null);
    //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 6 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    final String clientnameFinal = clientname;
    final String testIDFinal = testID;
    final Long testeeFinal = testee;
    final Integer sessionTypeFinal = sessionType;

    final String SQL_QUERY3 = "select  mode from ${modesTblName} limit 1";
    Map<String, String> unquotedMap3 = new HashMap<String, String> ();
    unquotedMap3.put ("modesTblName", modesTbl.getTableName ());

    if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY3, unquotedMap3), null, false)) == false) {
      reasonBlockedRef.set ("NA");
      connection.dropTemporaryTable (modesTbl);
      return;
    }
    //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 7 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    final String SQL_QUERY4 = "select  testID from ${ConfigDB}.client_testprerequisite where clientname = ${clientname} and TestID = ${testID} and isActive = 1 "
        + " and not exists (select * from testopportunity where clientname = ${clientname} and _efk_Testee = ${testee} and _efk_TestID = prereqTestID "
        + "  and dateCompleted is not null and dateDeleted is null)  limit 1";
    SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testid", testID).put ("testee", testee);

    if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY4), parms4, false))) {
      _commonDll._FormatMessage_SP (connection, clientname, "ENU", "_CanOpenTestOpportunity", "Missing prerequisite", reasonBlockedRef);
      return;
    }
    //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 8 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    DataBaseTable windowsTbl = getDataBaseTable ("iobWindows").addColumn ("winsession", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("modesessn", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("WID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).
        addColumn ("numopps", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("winmax", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("winopps", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("modeopps", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("startDate", SQL_TYPE_To_JAVA_TYPE.DATETIME).addColumn ("endDate", SQL_TYPE_To_JAVA_TYPE.DATETIME).
        addColumn ("formkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("testkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).
        addColumn ("mode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("modemax", SQL_TYPE_To_JAVA_TYPE.INT);
    //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 8.001 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    executeMethodAndInsertIntoTemporaryTable (connection, new AbstractDataResultExecutor ()
    {
      @Override
      public SingleDataResultSet execute (SQLConnection connection) throws ReturnStatusException {
        // getting windowID, windowMax , startDate, endDate, formkey, mode,
        // modeMax, testkey from _GetTesteeTestWindows,
        // They will populate WID, winmax, startDate, endDate, formkey, mode,
        // modemax, testkey columns in windows tbl.
        // Still need winsession, modesessn, numopps, winopps, modeopps
        //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 8.1 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
        SingleDataResultSet result = _GetTesteeTestWindows_SP (connection, clientnameFinal, testIDFinal, testeeFinal, sessionTypeFinal);
        //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 8.2 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
        result.resetColumnName (1, "wid");
        result.resetColumnName (2, "winmax");
        
        result.addColumn ("winsession", SQL_TYPE_To_JAVA_TYPE.INT);
        result.addColumn ("modesessn", SQL_TYPE_To_JAVA_TYPE.INT);
        result.addColumn ("numopps", SQL_TYPE_To_JAVA_TYPE.INT);
        result.addColumn ("modeopps", SQL_TYPE_To_JAVA_TYPE.INT);
        result.addColumn ("winopps", SQL_TYPE_To_JAVA_TYPE.INT);
        Iterator<DbResultRecord> records = result.getRecords ();
        while (records.hasNext ()) {
          DbResultRecord record = records.next ();
          record.addColumnValue ("winsession", 0);
          record.addColumnValue ("modesessn", 0);
          record.addColumnValue ("numopps", 0);
          record.addColumnValue ("modeopps", 0);
          record.addColumnValue ("winopps", 0);
        }
        return result;
      }
    }, windowsTbl, true);
    //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 9 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    final String SQL_QUERY5 = "delete from ${windowsTblName} where not exists (select * from ${modesTblName} where WinID = WID and modeTestKey = testkey)";
    Map<String, String> unquoted5 = new HashMap<> ();
    unquoted5.put ("windowsTblName", windowsTbl.getTableName ());
    unquoted5.put ("modesTblName", modesTbl.getTableName ());

    executeStatement (connection, fixDataBaseNames (SQL_QUERY5, unquoted5), null, false).getUpdateCount ();
    //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 10 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    connection.dropTemporaryTable (modesTbl);
    //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 11 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    final String SQL_QUERY6 = "select  WID from ${windowsTblName} where WID is not null limit 1";
    Map<String, String> unquoted6 = new HashMap<> ();
    unquoted6.put ("windowsTblName", windowsTbl.getTableName ());
    if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY6, unquoted6), null, false)) == false) {
      _commonDll._FormatMessage_SP (connection, clientname, "ENU", "_CanOpenTestOpportunity", "There is no active testing window for this student on this test", reasonBlockedRef);
      connection.dropTemporaryTable (windowsTbl);
      _commonDll._LogDBLatency_SP (connection, "_IsOpportunityBlocked", starttime, testee, true, null, null, null, clientname, null);
      return;
    }
    //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 12 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    DataBaseTable ctwTbl = GetCurrentTestWindows_FN (connection, clientname, testID, sessionType);
    final String SQL_QUERY7 = "update ${windowsTblName} W, ${ctwTblName} C set W.winSession = C.WindowSession , W.modeSessn = C.modeSession ";
    Map<String, String> unquoted7 = new HashMap<> ();
    unquoted7.put ("windowsTblName", windowsTbl.getTableName ());
    unquoted7.put ("ctwTblName", ctwTbl.getTableName ());

    executeStatement (connection, fixDataBaseNames (SQL_QUERY7, unquoted7), null, false).getUpdateCount ();
    //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 13 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    String statusCodes = _commonDll.GetStatusCodes_FN (connection, "opportunity", "closed");
    final String SQL_QUERY8 = " update ${windowsTblName} set winopps =  (select count(*) from testopportunity O, session S "
        + " where O.clientname = ${clientname} and _efk_Testee = ${testee} and _efk_TestID = ${testID}  and O._fk_Session  = S._Key and (winSession = -1 or S.SessionType = winSession) "
        + "  and windowID = WID and dateDeleted is null and O.status in (${statusCodes}))";
    Map<String, String> unquoted8 = new HashMap<> ();
    unquoted8.put ("windowsTblName", windowsTbl.getTableName ());
    SqlParametersMaps parms8 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testee", testee).put ("testID", testID).put ("statusCodes", statusCodes);

    executeStatement (connection, fixDataBaseNames (SQL_QUERY8, unquoted8), parms8, false).getUpdateCount ();
    //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 14 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    // -- NOTE: Mode is directly correlated with the testkey (as opposed to
    // testID, which is in a one-to-many correspondence with testKEY)
    final String SQL_QUERY9 = "update ${windowsTblName} set modeopps =  (select count(*) from testopportunity O, session S "
        + " where O.clientname = ${clientname} and _efk_Testee = ${testee} and _efk_AdminSubject = testkey and O._fk_Session  = S._Key and (modeSessn = -1 or S.SessionType = modeSessn) "
        + " and dateDeleted is null  and O.status in (${statusCodes}))";
    Map<String, String> unquoted9 = unquoted8;
    SqlParametersMaps parms9 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testee", testee).put ("statusCodes", statusCodes);

    executeStatement (connection, fixDataBaseNames (SQL_QUERY9, unquoted9), parms9, false).getUpdateCount ();
    //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 15 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    final String SQL_QUERY10 = "select  winopps from ${windowsTblName} where winopps < winmax and modeopps < modeMax  limit 1";
    Map<String, String> unquoted10 = unquoted8;

    if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY10, unquoted10), null, false)) == false) {
      _commonDll._FormatMessage_SP (connection, clientname, "ENU", "_CanOpenTestOpportunity", "No opportunities available in this testing window.", reasonBlockedRef);
      connection.dropTemporaryTable (windowsTbl);
      _commonDll._LogDBLatency_SP (connection, "_IsOpportunityBlocked", starttime, testee, true, null, null, null, clientname, null);

      return;
    }
    //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 16 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    // -- check for all opportunities used for the logical test (testID as
    // opposed to testkey)
    final String SQL_QUERY11 = "update ${windowsTblName} set numopps = (select count(*) from testopportunity "
        + " where clientname = ${clientname} and _efk_Testee = ${testee} and _efk_TestID = ${testID} and dateDeleted is null and status in (${statusCodes}))";
    Map<String, String> unquoted11 = unquoted8;
    SqlParametersMaps parms11 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testee", testee).put ("testID", testID).put ("statusCodes", statusCodes);
    executeStatement (connection, fixDataBaseNames (SQL_QUERY11, unquoted11), parms11, false).getUpdateCount ();
    //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 17 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    final String SQL_QUERY12 = "select  numopps from ${windowsTblName} where numopps < ${maxopps} limit 1";
    Map<String, String> unquoted12 = unquoted8;
    SqlParametersMaps parms12 = (new SqlParametersMaps ()).put ("maxopps", maxopps);
    if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY12, unquoted12), parms12, false)) == false) {
      _commonDll._FormatMessage_SP (connection, clientname, "ENU", "_CanOpenTestOpportunity", "All opportunities have been used for this test", reasonBlockedRef);
      connection.dropTemporaryTable (windowsTbl);
      _commonDll._LogDBLatency_SP (connection, "_IsOpportunityBlocked", starttime, testee, true, null, null, null, clientname, null);
      return;
    }
    //_logger.info ("<<<<<<<<< _IsOpportunityBlocked_SP 18 : "+((System.currentTimeMillis ()-startTimeLong)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    connection.dropTemporaryTable (windowsTbl);
    _commonDll._LogDBLatency_SP (connection, "_IsOpportunityBlocked", starttime, testee, true, null, null, null, clientname, null);

  }

  // TODO attempt to restructure to be able to introduce mysql SP ignorant of
  // RTS functionality
  public void _IsOpportunityBlocked_SPV1 (SQLConnection connection, String clientname, Long testee, String testID, Integer maxopps, _Ref<String> reasonBlockedRef, Integer sessionType)
      throws ReturnStatusException {
    // -- This proc is concerned entirely with the current point in time and not
    // with absolutes
    // -- so it applies to both new and existing test opportunities.
    // -- As such it should NEVER BE USED to determine test eligibility
    DbResultRecord record = null;

    final String SQL_QUERY2 = "select  clientname from _externs where clientname = ${clientname} and environment = 'SIMULATION' limit 1";
    SqlParametersMaps parameters2 = (new SqlParametersMaps ()).put ("clientname", clientname);

    if (exists (executeStatement (connection, SQL_QUERY2, parameters2, false)) == true) {
      return;
    }
    // String subject = null;
    // final String SQL_QUERY1 =
    // "select subjectname as subject from ${ConfigDB}.client_testproperties "
    // + " where clientname = ${clientname} and testID = ${testID}";
    // SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("clientname",
    // clientname).put ("testid", testID);

    // SingleDataResultSet result = executeStatement (connection,
    // fixDataBaseNames (SQL_QUERY1), parms1, false).getResultSets ().next ();
    // DbResultRecord record = (result.getCount () > 0 ? result.getRecords
    // ().next () : null);
    // if (record != null) {
    // subject = record.<String> get ("subject");
    // }

    // if (subject != null)
    // subject = String.format (";%s;", subject.trim ());
    // else
    // subject = ";;";
    // _Ref<String> testeeSubjectsRef = new _Ref<String> ();

    // _rtsDll._GetRTSAttribute_SP (connection, clientname, testee,
    // "BLOCKEDSUBJECT", testeeSubjectsRef);

    // String testeeSubjects = testeeSubjectsRef.get ();
    // if (testeeSubjects != null && testeeSubjects.length () > 0) {

    // testeeSubjects = String.format (";%s;", testeeSubjects.trim ());
    // if (testeeSubjects.indexOf (subject) > -1) {

    // _commonDll._FormatMessage_SP (connection, clientname, "ENU",
    // "_CanOpenTestOpportunity",
    // "This test is administratively blocked. Please check with your test administrator.",
    // reasonBlockedRef);
    // return;
    // }
    // }

    // -- Check for number of tests administered in the current test window.
    // -- There is a maximum number of opportunities for the test over all
    // windows, but also a maximum available to a student in each window

    Date start = null, end = null;
    Long diff = 0L;

    // Check for number of tests administered in the current test window.
    // There is a maximum number of opportunities for the test over all windows,
    // but also a maximum available to a student in each window

    // WinID, winmax, startDate, endDate, mode, modemax, modeTestKey to table
    // windowID, windowMax, startDate, endDate, mode, modeMax, testkey fromset
    start = new Date ();
    final SingleDataResultSet testModesResult = _GetTesteeTestModes_SP (connection, clientname, testID, testee, sessionType);
    end = new Date ();
    diff = end.getTime () - start.getTime ();
    if (testModesResult.getCount () == 0) {
      reasonBlockedRef.set ("NA");
      return;
    }

    start = new Date ();
    DataBaseTable modesTbl = getDataBaseTable ("iobModes").addColumn ("WinID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("numopps", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("winmax", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("winopps", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("modeopps", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("startDate", SQL_TYPE_To_JAVA_TYPE.DATETIME).addColumn ("endDate", SQL_TYPE_To_JAVA_TYPE.DATETIME).
        addColumn ("formkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("modetestkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).
        addColumn ("mode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("modemax", SQL_TYPE_To_JAVA_TYPE.INT);

    testModesResult.resetColumnName (1, "winid");
    testModesResult.resetColumnName (2, "winmax");
    testModesResult.resetColumnName (7, "modetestkey");

    testModesResult.addColumn ("modeopps", SQL_TYPE_To_JAVA_TYPE.INT);
    testModesResult.addColumn ("winopps", SQL_TYPE_To_JAVA_TYPE.INT);
    testModesResult.addColumn ("numopps", SQL_TYPE_To_JAVA_TYPE.INT);
    testModesResult.addColumn ("formkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    Iterator<DbResultRecord> records = testModesResult.getRecords ();
    while (records.hasNext ()) {
      record = records.next ();
      record.addColumnValue ("winopps", 0);
      record.addColumnValue ("numopps", 0);
      record.addColumnValue ("modeopps", 0);
      record.addColumnValue ("formkey", null);
    }
    connection.createTemporaryTable (modesTbl);
    insertBatch (connection, modesTbl.generateInsertStatement (), testModesResult, null);
    end = new Date ();
    diff = end.getTime () - start.getTime ();

    final String clientnameFinal = clientname;
    final String testIDFinal = testID;
    final Long testeeFinal = testee;
    final Integer sessionTypeFinal = sessionType;

    final String SQL_QUERY3 = "select  mode from ${modesTblName} limit 1";
    Map<String, String> unquotedMap3 = new HashMap<String, String> ();
    unquotedMap3.put ("modesTblName", modesTbl.getTableName ());

    if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY3, unquotedMap3), null, false)) == false) {
      reasonBlockedRef.set ("NA");
      connection.dropTemporaryTable (modesTbl);
      return;
    }

    final String SQL_QUERY4 = "select  testID from ${ConfigDB}.client_testprerequisite where clientname = ${clientname} and TestID = ${testID} and isActive = 1 "
        + " and not exists (select * from testopportunity where clientname = ${clientname} and _efk_Testee = ${testee} and _efk_TestID = prereqTestID "
        + "  and dateCompleted is not null and dateDeleted is null)  limit 1";
    SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testid", testID).put ("testee", testee);

    if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY4), parms4, false))) {
      _commonDll._FormatMessage_SP (connection, clientname, "ENU", "_CanOpenTestOpportunity", "Missing prerequisite", reasonBlockedRef);
      return;
    }

    start = new Date ();
    SingleDataResultSet result = _GetTesteeTestWindows_SP (connection, clientnameFinal, testIDFinal, testeeFinal, sessionTypeFinal);
    end = new Date ();
    diff = end.getTime () - start.getTime ();

    start = new Date ();
    DataBaseTable windowsTbl = getDataBaseTable ("iobWindows").addColumn ("winsession", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("modesessn", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("WID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).
        addColumn ("numopps", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("winmax", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("winopps", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("modeopps", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("startDate", SQL_TYPE_To_JAVA_TYPE.DATETIME).addColumn ("endDate", SQL_TYPE_To_JAVA_TYPE.DATETIME).
        addColumn ("formkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("testkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).
        addColumn ("mode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("modemax", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (windowsTbl);

    // getting windowID, windowMax , startDate, endDate, formkey, mode,
    // modeMax, testkey from _GetTesteeTestWindows,
    // They will populate WID, winmax, startDate, endDate, formkey, mode,
    // modemax, testkey columns in windows tbl.
    // Still need winsession, modesessn, numopps, winopps, modeopps
    result.resetColumnName (1, "wid");
    result.resetColumnName (2, "winmax");

    result.addColumn ("winsession", SQL_TYPE_To_JAVA_TYPE.INT);
    result.addColumn ("modesessn", SQL_TYPE_To_JAVA_TYPE.INT);
    result.addColumn ("numopps", SQL_TYPE_To_JAVA_TYPE.INT);
    result.addColumn ("modeopps", SQL_TYPE_To_JAVA_TYPE.INT);
    result.addColumn ("winopps", SQL_TYPE_To_JAVA_TYPE.INT);
    records = result.getRecords ();
    while (records.hasNext ()) {
      record = records.next ();
      record.addColumnValue ("winsession", 0);
      record.addColumnValue ("modesessn", 0);
      record.addColumnValue ("numopps", 0);
      record.addColumnValue ("modeopps", 0);
      record.addColumnValue ("winopps", 0);
    }
    insertBatch (connection, windowsTbl.generateInsertStatement (), result, null);
    end = new Date ();
    diff = end.getTime () - start.getTime ();

    start = new Date ();
    DataBaseTable ctwTbl = GetCurrentTestWindows_FN (connection, clientname, testID, sessionType);
    end = new Date ();
    diff = end.getTime () - start.getTime ();

    start = new Date ();
    final String SQL_QUERY5 = "delete from ${windowsTblName} where not exists (select * from ${modesTblName} where WinID = WID and modeTestKey = testkey)";
    Map<String, String> unquoted5 = new HashMap<> ();
    unquoted5.put ("windowsTblName", windowsTbl.getTableName ());
    unquoted5.put ("modesTblName", modesTbl.getTableName ());

    executeStatement (connection, fixDataBaseNames (SQL_QUERY5, unquoted5), null, false).getUpdateCount ();

    connection.dropTemporaryTable (modesTbl);

    final String SQL_QUERY6 = "select  WID from ${windowsTblName} where WID is not null limit 1";
    Map<String, String> unquoted6 = new HashMap<> ();
    unquoted6.put ("windowsTblName", windowsTbl.getTableName ());
    if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY6, unquoted6), null, false)) == false) {
      _commonDll._FormatMessage_SP (connection, clientname, "ENU", "_CanOpenTestOpportunity", "There is no active testing window for this student on this test", reasonBlockedRef);
      connection.dropTemporaryTable (windowsTbl);
      return;
    }

    final String SQL_QUERY7 = "update ${windowsTblName} W, ${ctwTblName} C set W.winSession = C.WindowSession , W.modeSessn = C.modeSession ";
    Map<String, String> unquoted7 = new HashMap<> ();
    unquoted7.put ("windowsTblName", windowsTbl.getTableName ());
    unquoted7.put ("ctwTblName", ctwTbl.getTableName ());

    executeStatement (connection, fixDataBaseNames (SQL_QUERY7, unquoted7), null, false).getUpdateCount ();

    String statusCodes = _commonDll.GetStatusCodes_FN (connection, "opportunity", "closed");
    final String SQL_QUERY8 = " update ${windowsTblName} set winopps =  (select count(*) from testopportunity O, session S "
        + " where O.clientname = ${clientname} and _efk_Testee = ${testee} and _efk_TestID = ${testID}  and O._fk_Session  = S._Key and (winSession = -1 or S.SessionType = winSession) "
        + "  and windowID = WID and dateDeleted is null and O.status in (${statusCodes}))";
    Map<String, String> unquoted8 = new HashMap<> ();
    unquoted8.put ("windowsTblName", windowsTbl.getTableName ());
    SqlParametersMaps parms8 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testee", testee).put ("testID", testID).put ("statusCodes", statusCodes);

    executeStatement (connection, fixDataBaseNames (SQL_QUERY8, unquoted8), parms8, false).getUpdateCount ();

    // -- NOTE: Mode is directly correlated with the testkey (as opposed to
    // testID, which is in a one-to-many correspondence with testKEY)
    final String SQL_QUERY9 = "update ${windowsTblName} set modeopps =  (select count(*) from testopportunity O, session S "
        + " where O.clientname = ${clientname} and _efk_Testee = ${testee} and _efk_AdminSubject = testkey and O._fk_Session  = S._Key and (modeSessn = -1 or S.SessionType = modeSessn) "
        + " and dateDeleted is null  and O.status in (${statusCodes}))";
    Map<String, String> unquoted9 = unquoted8;
    SqlParametersMaps parms9 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testee", testee).put ("statusCodes", statusCodes);

    executeStatement (connection, fixDataBaseNames (SQL_QUERY9, unquoted9), parms9, false).getUpdateCount ();

    final String SQL_QUERY10 = "select  winopps from ${windowsTblName} where winopps < winmax and modeopps < modeMax  limit 1";
    Map<String, String> unquoted10 = unquoted8;

    if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY10, unquoted10), null, false)) == false) {
      _commonDll._FormatMessage_SP (connection, clientname, "ENU", "_CanOpenTestOpportunity", "No opportunities available in this testing window.", reasonBlockedRef);
      connection.dropTemporaryTable (windowsTbl);
      return;
    }

    // -- check for all opportunities used for the logical test (testID as
    // opposed to testkey)
    final String SQL_QUERY11 = "update ${windowsTblName} set numopps = (select count(*) from testopportunity "
        + " where clientname = ${clientname} and _efk_Testee = ${testee} and _efk_TestID = ${testID} and dateDeleted is null and status in (${statusCodes}))";
    Map<String, String> unquoted11 = unquoted8;
    SqlParametersMaps parms11 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testee", testee).put ("testID", testID).put ("statusCodes", statusCodes);
    executeStatement (connection, fixDataBaseNames (SQL_QUERY11, unquoted11), parms11, false).getUpdateCount ();

    final String SQL_QUERY12 = "select  numopps from ${windowsTblName} where numopps < ${maxopps} limit 1";
    Map<String, String> unquoted12 = unquoted8;
    SqlParametersMaps parms12 = (new SqlParametersMaps ()).put ("maxopps", maxopps);
    if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY12, unquoted12), parms12, false)) == false) {
      _commonDll._FormatMessage_SP (connection, clientname, "ENU", "_CanOpenTestOpportunity", "All opportunities have been used for this test", reasonBlockedRef);
      connection.dropTemporaryTable (windowsTbl);
      return;
    }
    connection.dropTemporaryTable (windowsTbl);
    end = new Date ();
    diff = end.getTime () - start.getTime ();
  }

  public void _CanOpenTestOpportunity_SP_Mysql (SQLConnection connection, String clientname, Long testee, String testkey, UUID sessionId,
      Integer maxOpportunities, _Ref<Boolean> newRef, _Ref<Integer> numberRef, _Ref<String> reasonRef) throws ReturnStatusException {
    try {
      
      String sessionIdStr = sessionId.toString ().replaceAll ("-", "");
      sessionIdStr = String.format ("0x%s", sessionIdStr);
      final String SQL_QUERY = "{call _canopentestopportunity(?,?,?,"+sessionIdStr+",?,?,?,?,?)}";
      CallableStatement callableStatement = connection.prepareCall(SQL_QUERY);
      callableStatement.setString (1, clientname);
      callableStatement.setLong (2, testee);
      callableStatement.setString (3, testkey);
      callableStatement.setInt (4, maxOpportunities);
      callableStatement.registerOutParameter(5, java.sql.Types.BOOLEAN);
      callableStatement.registerOutParameter(6, java.sql.Types.INTEGER);
      callableStatement.registerOutParameter(7, java.sql.Types.VARCHAR);
      callableStatement.setInt (8, 0);
       
      callableStatement.executeUpdate();
       
      newRef.set (callableStatement.getBoolean(5));
      numberRef.set( callableStatement.getInt(6));
      reasonRef.set ( callableStatement.getString (7));
      
    } catch (SQLException e) {
      throw new ReturnStatusException (e);
    }
    
  }
  public void _CanOpenTestOpportunity_SP (SQLConnection connection, String clientname, Long testee, String testkey, UUID sessionId,
      Integer maxOpportunities, _Ref<Boolean> newRef, _Ref<Integer> numberRef, _Ref<String> reasonRef) throws ReturnStatusException {
    Date today = _dateUtil.getDateWRetStatus (connection);
    long startTime = System.currentTimeMillis ();
    newRef.set (false);
    numberRef.set (0);
    reasonRef.set (null);

    String environment = _commonDll.getExternsColumnByClientName (connection, clientname, "environment");
    Integer sessionType = null;
    final String SQL_QUERY1 = "select sessionType from session where _Key = ${sessionID}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("sessionID", sessionId);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      sessionType = record.<Integer> get ("sessionType");
    }
    String testId = null; // -- testkeys are unique
    final String SQL_QUERY2 = "select  TestID from ${ItemBankDB}.tblsetofadminsubjects where _Key = ${testkey}";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("testkey", testkey);
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY2), parms2, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      testId = record.<String> get ("testId");
    }
    // -- If simulation environment uses 'real' students then we want the test
    // to proceed anyway
    // _IsOpportunityBlocked_SP logic is performed by T_GETELIGIBLETESTS, Removing from here as per Jon Cohen's suggestion.
    /*if (DbComparator.notEqual (environment, "SIMULATION") && DbComparator.greaterThan (testee, 0)) {
      // -- only check eligibility on real students
      _IsOpportunityBlocked_SP (connection, clientname, testee, testId, maxOpportunities, reasonRef, sessionType);
    }*/
    if (reasonRef.get () != null) {
      if ("NA".equalsIgnoreCase (reasonRef.get ()))
        numberRef.set (-1); // -- this test is not applicable to the student
      else
        numberRef.set (0);
      return;
    }
    Integer delayDays = null;
    final String SQL_QUERY6 = "select OppDelay  from timelimits where _efk_TestID = ${testID} and clientname = ${clientname}";
    SqlParametersMaps parms6 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testid", testId);
    result = executeStatement (connection, SQL_QUERY6, parms6, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      delayDays = record.<Integer> get ("oppdelay");
    }
    if (delayDays == null) {
      final String SQL_QUERY7 = "select OppDelay from timelimits where _efk_TestID is null and clientname = ${clientname}";
      SqlParametersMaps parms7 = (new SqlParametersMaps ()).put ("clientname", clientname);
      result = executeStatement (connection, SQL_QUERY7, parms7, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        delayDays = record.<Integer> get ("oppdelay");
      }
    }
    // -- first try to open an existing opportunity of the main test
    // -- _CanOpenExistingOpportunity must use the test key, not testID, because
    // this will be resuming an actual physical test, not a logical one
    _CanOpenExistingOpportunity_SP (connection, clientname, testee, testId, sessionId, maxOpportunities, numberRef, reasonRef);
    if (DbComparator.greaterThan (numberRef.get (), 0) || reasonRef.get () != null) {
      return;
    }
    // -- neither success nor a fatal error, so now try to open a new
    // opportunity
    // -- Important note: This function takes into account that an expired
    // opportunity may have to be closed first
    _CanOpenNewOpportunity_SP (connection, clientname, testee, testId, maxOpportunities, delayDays, numberRef, reasonRef, sessionId);
    if (DbComparator.greaterThan (numberRef.get (), 0)) {
      newRef.set (true);
    }
    //_logger.info ("<<<<<<<<< _CanOpenTestOpportunity_SP Total Execution Time : "+((System.currentTimeMillis ()-startTime)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    _commonDll._LogDBLatency_SP (connection, "_CanOpenTestOpportunity", today, testee, true, null, null, null, clientname, null);
  }

  public SingleDataResultSet _InitOpportunityAccommodations_SP (SQLConnection connection, UUID oppkey, String accoms) throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);

    Long testee = null;
    String testId = null, test = null, clientname = null;
    final String SQL_QUERY1 = "select  _efk_TestID as testID, _efk_AdminSubject as test, _efk_Testee as testee, clientname "
        + " from testopportunity where _key = ${oppkey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      testee = record.<Long> get ("testee");
      testId = record.<String> get ("testID");
      test = record.<String> get ("test");
      clientname = record.<String> get ("clientname");
    }
    Integer transactionIsolation = null;
    Boolean preexistingAutoCommitMode = null;
    try {
        transactionIsolation = connection.getTransactionIsolation ();
        preexistingAutoCommitMode = connection.getAutoCommit ();
        connection.setAutoCommit (false);
        connection.setTransactionIsolation (Connection.TRANSACTION_READ_COMMITTED);
        DataBaseTable tbl = _commonDll.TestKeyAccommodations_FN (connection, test);
        final String SQL_QUERY2 = "insert into testeeaccommodations (_fk_TestOpportunity, segment, AccType, AccCode, AccValue, allowChange, "
            + "      testeeControl, isSelectable, IsApproved, valueCount, recordUsage, _date) "
            + " select ${oppkey}, Segment, AccType, AccCode, AccValue, allowChange, studentControl, IsSelectable, case valcount when 1 then 1 else 0 end, "
            + " valCount,  "
            + "     (select count(*) from ${ConfigDB}.client_toolusage "
            + "      where clientname = ${clientname} and testID = ${testID} and tooltype = AccType and (recordUsage = 1 or reportUsage = 1) limit 1), now(3) "
            + " from ${tblName} A "
            + " where IsDefault = 1 and DependsOnToolType is null "
            + " and not exists (select * from testeeaccommodations ACC where ACC._fk_TestOpportunity = ${oppkey} and ACC.AccCode = A.AccCode)";
        SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("oppkey", oppkey).put ("clientname", clientname).put ("testId", testId);
        Map<String, String> unquotedParms2 = new HashMap<> ();
        unquotedParms2.put ("tblName", tbl.getTableName ());
        final String query2 = fixDataBaseNames (SQL_QUERY2);
        executeStatement (connection, fixDataBaseNames (query2, unquotedParms2), parms2, false).getUpdateCount ();
        connection.commit ();
    } catch (ReturnStatusException re) {
      try {
        connection.rollback ();
      } catch (SQLException e) {
        _logger.error (String.format ("Problem rolling back transaction: %s", e.getMessage ()));
      }
      String errmsg = re.getMessage ();
      if (errmsg == null)
        errmsg = "no error message logged";

      _commonDll._LogDBError_SP (connection, "_InitOpportunityAccommodations", errmsg, null, null, null, oppkey);
      throw new ReturnStatusException (re);
      //return null;

    } catch (SQLException se) {
      throw new ReturnStatusException (se);
    } finally {
      try {
        if(preexistingAutoCommitMode!=null) {
          connection.setAutoCommit (preexistingAutoCommitMode);
        }
        if(transactionIsolation!=null) {
          connection.setTransactionIsolation (transactionIsolation);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    if (DbComparator.greaterThan (testee, 0)) {
      if (accoms == null) {

        _Ref<String> attValueRef = new _Ref<> ();

        _rtsDll._GetRTSAttribute_SP (connection, clientname, testee, "--ACCOMMODATIONS--", attValueRef);
        accoms = attValueRef.get ();

        if (accoms == null || accoms.length () < 1) {

          String accomodationsString = _commonDll.P_FormatAccommodations_FN (connection, oppkey);

          final String SQL_QUERY3 = "update testopportunity_readonly set AccommodationString = ${accomsStr} where _fk_TestOpportunity = ${oppkey}";
          SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("accomsStr", accomodationsString).put ("oppkey", oppkey);
          executeStatement (connection, SQL_QUERY3, parms3, false).getUpdateCount ();

          _commonDll._LogDBLatency_SP (connection, "_InitOpportunityAccommodations", starttime, testee, true, null, oppkey, null, clientname, null);
          return null;
        }
        // -- else fall through to _Update proc
      }
      _Ref<String> errorRef = new _Ref<> ();
      _commonDll._UpdateOpportunityAccommodations_SP (connection, oppkey, 0, accoms, 0, false, false, errorRef);
      if (errorRef.get () != null) {
        // -- we are having trouble with deadlocks on _Update, try one more time

        String error = String.format ("Accommodations update failed. Making second attempt.%s", errorRef.get ());
        _commonDll._LogDBError_SP (connection, "_InitOpportunityAccommodations", error, null, null, null, oppkey);

        errorRef.set (null);
        _commonDll._UpdateOpportunityAccommodations_SP (connection, oppkey, 0, accoms, 0, false, false, errorRef);
        if (errorRef.get () != null) {
          _commonDll._LogDBError_SP (connection, "_InitOpportunityAccommodations", errorRef.get (), null, null, null, oppkey);

          return _commonDll._ReturnError_SP (connection, clientname, "_InitOpportunityAccommodations", "Accommodations update failed", null, oppkey, null);
        }
      }
    }
    _commonDll._LogDBLatency_SP (connection, "_InitOpportunityAccommodations", starttime, testee, true, null, oppkey, null, clientname, null);
    return null;
  }

  /**
   * Open an existing test opportunity for testing. Main purpose of this
   * function is to apply business rules. -- Assumptions: -- the given
   * opportunity exists for opening (and has not expired), as verified by
   * _CanOpenTestOpportunity -- the session is open for business, as verified by
   * _CanOpenTestOpportunity
   * 
   * @param connection
   * @param clientname
   * @param testee
   * @param testkey
   * @param opportunity
   * @param sessionId
   * @param browserId
   * @param newstatusRef
   * @param accommodations
   * @param restoreRTS
   * @param testoppkeyRef
   * @throws ReturnStatusException
   */
  public void _OpenExistingOpportunity_SP (SQLConnection connection, String clientname, Long testee, String testkey, Integer opportunity,
      UUID sessionId, UUID browserId,
      _Ref<String> newstatusRef, String accommodations, Boolean restoreRTS, _Ref<UUID> testoppkeyRef) throws ReturnStatusException {

    SingleDataResultSet rs = null;

    Date starttime = _dateUtil.getDateWRetStatus (connection);

    // -- ClienttestOppKey will only return non-deleted testopps
    final String SQL_QUERY1 = "select _Key as testoppkey from testopportunity "
        + " where clientname = ${clientname} and _efk_Testee = ${testee} and _efk_AdminSubject = ${testKey} "
        + "  and opportunity = ${opportunity} and dateDeleted is null";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testee", testee).put ("testkey", testkey).
        put ("opportunity", opportunity);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      testoppkeyRef.set (record.<UUID> get ("testoppkey"));
    }

    Integer audit = _commonDll.AuditOpportunities_FN (connection, clientname);

    Date today = _dateUtil.getDateWRetStatus (connection);

    // -- pick up info on the most recent opportunity
    String laststatus = null;
    final String SQL_QUERY2 = "select status as laststatus from testopportunity where _Key = ${testoppkey}";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("testoppkey", testoppkeyRef.get ());
    result = executeStatement (connection, SQL_QUERY2, parms2, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      laststatus = record.<String> get ("laststatus");
    }

    // -- determine if the test has been started in fact (items administered)
    Long slotcount = null;
    final String SQL_QUERY3 = "select count(*) as slotcount from testeeresponse where _fk_TestOpportunity = ${testoppkey}";
    SqlParametersMaps parms3 = parms2;
    result = executeStatement (connection, SQL_QUERY3, parms3, true).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      slotcount = record.<Long> get ("slotcount");
    }

    if (DbComparator.greaterThan (slotcount, 0))
      // -- indicates an opportunity in which items have been administered
      newstatusRef.set ("suspended");
    else
      // -- indicates a new opportunity for all intents and purposes
      newstatusRef.set ("pending");

    // -- determine if this is an 'abnormal restart' as indicated by an
    // opportunity which appears to be in use
    // -- this is for auditing purposes only
    Integer isAbnormal = null;
    final String SQL_QUERY4 = "select status from statuscodes where `usage` = 'opportunity' and stage = 'inuse' and status = ${laststatus}";
    SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("laststatus", laststatus);
    if (exists (executeStatement (connection, SQL_QUERY4, parms4, true)))
      isAbnormal = 1;
    else
      isAbnormal = 0;

    // -- open the opportunity
    // -- reset the date started if no items administered
    final String SQL_QUERY5 = "update testopportunity set  _fk_browser = ${browserID}, DateChanged = ${today}, "
        + " prevStatus = status, status = ${newstatus}, AbnormalStarts = AbnormalStarts + ${isabnormal}, "
        + "    waitingForSegment = case when insegment is null then 1 else insegment end, "
        + " DateStarted = case when ${slotcount} = 0 then null else DateStarted end  where _Key = ${testoppkey}";
    SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("browserID", browserId).put ("today", today).put ("slotcount", slotcount).
        put ("isabnormal", isAbnormal).put ("testoppkey", testoppkeyRef.get ()).put ("newstatus", newstatusRef.get ());
    executeStatement (connection, SQL_QUERY5, parms5, false).getUpdateCount ();

    // -- there should be accommodations already, but database distress errors
    // have been known to fail here
    // -- so this should self-repair the data
    // -- With segments, we only want to burden the Proctor with setting accoms
    // on restart when there is an actual choice required.
    // -- It also provides an opportunity to correct a mistake.
    Integer isStarted;
    if (DbComparator.isEqual ("pending", newstatusRef.get ()))
      isStarted = 0;
    else
      isStarted = 1;

    final String SQL_QUERY6 = "select  _fk_TestOpportunity from testeeaccommodations where _fk_TestOpportunity = ${testoppkey} limit 1";
    SqlParametersMaps parms6 = (new SqlParametersMaps ()).put ("testoppkey", testoppkeyRef.get ());
    if (exists (executeStatement (connection, SQL_QUERY6, parms6, false)) == false) {

      rs = _InitOpportunityAccommodations_SP (connection, testoppkeyRef.get (), accommodations);

    } else if (accommodations != null && accommodations.length () > 0) {

      _Ref<String> errorRef = new _Ref<> ();
      _commonDll._UpdateOpportunityAccommodations_SP (connection, testoppkeyRef.get (), 0, accommodations, isStarted, false, restoreRTS, errorRef);
    }

    // -- To facilitate 'smart' segment interruption, accommodation records now
    // record their approval status which goes as follows:
    // -- If there is only a single value available for the tool, the
    // accommodation is approved by default
    // -- Upon starting a new test, all tools with more than a single value
    // option are initially not approved
    // -- Upon restarting a test, all tools with more than a single value option
    // are initially not approved, unless their alteration is not allowed

    DataBaseTable ctaTbl = _commonDll.ClientTestAccommodations_FN (connection, clientname, testkey);

    // -- do this in case the valuecount on an accommodation has changed since
    // the test started
    final String SQL_QUERY7 = "update testeeaccommodations T, ${ctaTblName} A set T.valueCount = A.valcount  "
        + " where _fk_TestOpportunity = ${testoppkey} and T.Segment = A.Segment and T.AccCode = A.AccCode";
    SqlParametersMaps parmts7 = (new SqlParametersMaps ()).put ("testoppkey", testoppkeyRef.get ());
    Map<String, String> unquotedParms7 = new HashMap<> ();
    unquotedParms7.put ("ctaTblName", ctaTbl.getTableName ());
    executeStatement (connection, fixDataBaseNames (SQL_QUERY7, unquotedParms7), parmts7, false).getUpdateCount ();
    connection.dropTemporaryTable (ctaTbl);

    final String SQL_QUERY8 = "update testeeaccommodations set IsApproved = 0 "
        + " where _fk_TestOpportunity = ${testoppkey} and valueCount > 1 and isSelectable = 1 and allowChange = 1";
    SqlParametersMaps parms8 = parmts7;
    executeStatement (connection, SQL_QUERY8, parms8, false).getUpdateCount ();

    // -- record the audit trail
    String localost = _commonDll.getLocalhostName ();
    // String sessionDB = getAppSettings ().get ("TDSSessionDBName");
    String sessionDB = getTdsSettings ().getTDSSessionDBName ();
    final String SQL_QUERY9 = "insert into ${ArchiveDB}.opportunityaudit (_fk_TestOpportunity, _fk_Session, hostname, _fk_Browser, AccessType, IsAbnormal, dateaccessed, dbname) "
        + " values (${testoppkey}, ${sessionID}, ${localhost}, ${browserID}, ${newstatus}, ${isabnormal}, now(3), ${dbname})";
    SqlParametersMaps parms9 = (new SqlParametersMaps ()).put ("testoppkey", testoppkeyRef.get ()).put ("sessionId", sessionId).put ("localhost", localost).
        put ("browserID", browserId).put ("newstatus", newstatusRef.get ()).put ("isabnormal", isAbnormal).put ("dbname", sessionDB);
    executeStatement (connection, fixDataBaseNames (SQL_QUERY9), parms9, false).getUpdateCount ();

    _commonDll._LogDBLatency_SP (connection, "_OpenExistingOpportunity", starttime, testee, true, null, testoppkeyRef.get ());
  }


  public String TestSubject_FN (SQLConnection connection, String testkey) throws ReturnStatusException {

    String rslt = null;
    final String SQL_QUERY1 = "select S.Name from  ${ItemBankDB}.tblsubject S,  ${ItemBankDB}.tblsetofadminsubjects A "
        + " where A._key = ${testkey} and S._Key = A._fk_Subject";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("testkey", testkey);
    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY1), parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      rslt = record.<String> get ("name");
    }
    return rslt;
  }

  public void _GetTestParms_SP (SQLConnection connection, String clientname, String testkey,
      _Ref<String> subjectRef, _Ref<String> testIdRef, _Ref<Boolean> segmentedRef, _Ref<String> algorithmRef) throws ReturnStatusException {

    subjectRef.set (TestSubject_FN (connection, testkey));

    final String SQL_QUERY1 = "select TestID, IsSegmented, selectionalgorithm from ${ItemBankDB}.tblsetofadminsubjects where _Key = ${testkey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("testkey", testkey);
    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY1), parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      testIdRef.set (record.<String> get ("testid"));
      segmentedRef.set (record.<Boolean> get ("isSegmented"));
      algorithmRef.set (record.<String> get ("selectionalgorithm"));
    }
  }

  /**
   * Open a new test opportunity for testing. Main purpose of this function is
   * to apply business rules. -- Assumptions: -- the given opportunity does not
   * exist and is within testing limits, as verified by _CanOpenTestOpportunity
   * -- the session is open for business, as verified by _CanOpenTestOpportunity
   * 
   * @param connection
   * @param clientname
   * @param testee
   * @param testkey
   * @param opportunity
   * @param sessionKey
   * @param browserKey
   * @param testeeID
   * @param testeeName
   * @param status
   * @param guestAccommodations
   * @param testoppkeyRef
   * @throws ReturnStatusException
   */
  public SingleDataResultSet _OpenNewOpportunity_SP (SQLConnection connection, String clientname, Long testee, String testkey, Integer opportunity,
      UUID sessionKey, UUID browserKey, String testeeID, String testeeName, String status, String guestAccommodations, _Ref<UUID> testoppkeyRef)
      throws ReturnStatusException {
    if (status == null)
      status = "pending";

    Date starttime = _dateUtil.getDateWRetStatus (connection);

    boolean auditProc = _commonDll.AuditProc_FN (connection, "_OpenNewOpportunity");

    Date today = _dateUtil.getDateWRetStatus (connection);
    String environment = _commonDll.getExternsColumnByClientName (connection, clientname, "environment");

    Integer sessionType = null;
    final String SQL_QUERY1 = " select  sessionType  from session where _Key = ${sessionKey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      sessionType = record.<Integer> get ("sessionType");
    }

    testoppkeyRef.set (UUID.randomUUID ());

    _Ref<Boolean> segmentedRef = new _Ref<> ();
    _Ref<String> algorithmRef = new _Ref<> ();
    _Ref<String> subjectRef = new _Ref<> ();
    _Ref<String> testIdRef = new _Ref<> ();

    _GetTestParms_SP (connection, clientname, testkey, subjectRef, testIdRef, segmentedRef, algorithmRef);

    DataBaseTable windowsTbl = getDataBaseTable ("newOppWindows").addColumn ("WID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).
        addColumn ("maxOpps", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("numOpps", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("startDate", SQL_TYPE_To_JAVA_TYPE.DATETIME).
        addColumn ("endDate", SQL_TYPE_To_JAVA_TYPE.DATETIME).addColumn ("formkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).
        addColumn ("modeOpps", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("testmode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).
        addColumn ("modeMax", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("testkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250);

    final String clientnameFinal = clientname;
    final String testIDFinal = testIdRef.get ();
    final Long testeeFinal = testee;
    final Integer sessionTypeFinal = sessionType;
    executeMethodAndInsertIntoTemporaryTable (connection, new AbstractDataResultExecutor ()
    {
      @Override
      public SingleDataResultSet execute (SQLConnection connection) throws ReturnStatusException {
        // getting windowID, windowMax , startDate, endDate, formkey, mode,
        // modeMax, testkey from _GetTesteeTestWindows,
        // They will populate (WID, maxopps, startDate, endDate, formkey,
        // testmode, modeMax, testkey) columns in windows tbl
        // Still need numopps, and modeopps
        SingleDataResultSet result = _GetTesteeTestWindows_SP (connection, clientnameFinal, testIDFinal, testeeFinal, sessionTypeFinal);
        result.resetColumnName (1, "wid");
        result.resetColumnName (2, "maxopps");
        result.resetColumnName (6, "testmode");

        result.addColumn ("numopps", SQL_TYPE_To_JAVA_TYPE.INT);
        result.addColumn ("modeopps", SQL_TYPE_To_JAVA_TYPE.INT);

        Iterator<DbResultRecord> records = result.getRecords ();
        while (records.hasNext ()) {
          DbResultRecord record = records.next ();
          record.addColumnValue ("numopps", 0);
          record.addColumnValue ("modeopps", 0);
        }
        return result;
      }
    }, windowsTbl, true);

    Date minStartDate = null;
    final String SQL_QUERY2 = "select min(startDate)  as minStartDate from ${windowsTblName}";
    Map<String, String> unquotedParms2 = new HashMap<> ();
    unquotedParms2.put ("windowsTblName", windowsTbl.getTableName ());
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY2, unquotedParms2), null, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      minStartDate = record.<Date> get ("minStartDate");
    }

    String windowId = null;
    String mode = null;
    final String SQL_QUERY3 = "select  WID as windowID, testmode as mode from ${windowsTblName} where testkey = ${testkey} and startDate = ${minStartDate} limit 1";
    Map<String, String> unquotedParms3 = unquotedParms2;
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("testkey", testkey).put ("minStartDate", minStartDate);
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY3, unquotedParms3), parms3, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      windowId = record.<String> get ("windowId");
      mode = record.<String> get ("mode");
    }

    if (windowId == null)
      return _commonDll._ReturnError_SP (connection, clientname, "_OpenNewOpportunity", "There is no active testing window for this student at this time");

    _Ref<Long> newIdRef = new _Ref<> ();
    //TODO Temporary Commented on 10.20.2014 per Larry's suggestion.
//    _CreateClientReportingID_SP (connection, clientname, testoppkeyRef.get (), newIdRef);
//
//    if (newIdRef.get () == null) {
//
//      _commonDll._LogDBError_SP (connection, "_OpenNewOpportunity", "Unable to create a unique reporting ID", testee, testkey, opportunity, null);
//      return _commonDll._ReturnError_SP (connection, clientname, "_OpenNewOpportunity", "Unable to create a unique reporting ID");
//    }

    // -- version is irrespective of deleted status
    Integer version = null;
    final String SQL_QUERY4 = "select max(_version)  as version from testopportunity "
        + " where _efk_Testee = ${testee} and _efk_TestID = ${testID} and Opportunity = ${opportunity} and clientname = ${clientname}";
    SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("testee", testee).put ("opportunity", opportunity).
        put ("clientname", clientname).put ("testid", testIdRef.get ());
    result = executeStatement (connection, SQL_QUERY4, parms4, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      version = record.<Integer> get ("version");
    }

    if (version == null)
      version = 1;
    else
      version++;

    final String SQL_QUERY5 = "insert into testopportunity (_key, _version, clientname, _efk_Testee, _efk_TestID, Opportunity, "
        + "     Status,  Subject, TesteeID, TesteeName, _fk_Browser, DateChanged,  ReportingID,  windowID,"
        + "     mode, isSegmented, algorithm,_efk_AdminSubject, environment, SessID, ProctorName, waitingForSegment, datejoined) "
        + " select ${testoppkey}, ${version}, ${clientname}, ${testee}, ${testID}, ${opportunity},  "
        + "        'paused', ${subject}, ${testeeID}, ${testeeName}, ${browserKey}, ${today}, ${newID}, ${windowID}, "
        + "         ${mode}, ${segmented}, ${algorithm}, ${testkey}, ${environment}, SessionID, ProctorName, 1, now(3)"
        + " from session where _Key = ${sessionKey}";
    SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("testoppkey", testoppkeyRef.get ()).put ("version", version).put ("clientname", clientname).
        put ("testee", testee).put ("testID", testIdRef.get ()).put ("opportunity", opportunity).put ("subject", subjectRef.get ()).
        put ("testeeID", testeeID).put ("testeeName", testeeName).put ("browserKey", browserKey).put ("today", today).put ("newID", newIdRef.get ()).
        put ("windowID", windowId).put ("mode", mode).put ("segmented", segmentedRef.get ()).put ("algorithm", algorithmRef.get ()).
        put ("testKey", testkey).put ("environment", environment).put ("sessionKey", sessionKey);
    executeStatement (connection, SQL_QUERY5, parms5, false).getUpdateCount ();

    String context = "INITIAL";
    _commonDll._SetTesteeAttributes_SP (connection, clientname, testoppkeyRef.get (), testee, context);

    final String SQL_QUERY6 = "select _fk_TestOpportunity from ${ArchiveDB}.systemerrors where procname = '_SetTesteeAttributes' and _fk_TestOpportunity = ${testoppkey} limit 1";
    SqlParametersMaps parms6 = (new SqlParametersMaps ()).put ("testoppkey", testoppkeyRef.get ());
    if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY6), parms6, false))) {
      // -- we have had deadlock problems with this, try again?
      _commonDll._SetTesteeAttributes_SP (connection, clientname, testoppkeyRef.get (), testee, context);
    }

    // -- insert the given accommodations or defaults
    _InitOpportunityAccommodations_SP (connection, testoppkeyRef.get (), guestAccommodations);

    
    Integer transactionIsolation = null;
    try {
      transactionIsolation = connection.getTransactionIsolation ();
      connection.setTransactionIsolation (Connection.TRANSACTION_READ_COMMITTED);

      final String SQL_QUERY7 = "update testopportunity set status = ${status} where _key = ${testoppkey}";
      SqlParametersMaps parms7 = (new SqlParametersMaps ()).put ("testoppkey", testoppkeyRef.get ()).put ("status", status);
      executeStatement (connection, SQL_QUERY7, parms7, false).getUpdateCount ();
 
      
    } catch (SQLException se) {
       throw new ReturnStatusException (se);
    } finally {
      try {
        if (transactionIsolation != null)
          connection.setTransactionIsolation (transactionIsolation);

      } catch (SQLException e) {
        e.printStackTrace ();
      }     
    }
    if (auditProc) {
      // String sessionDB = getAppSettings ().get ("TDSSessionDBName");
      String sessionDB = getTdsSettings ().getTDSSessionDBName ();
      final String SQL_QUERY8 = "insert into ${ArchiveDB}.opportunityaudit (_fk_TestOpportunity, _fk_Session, AccessType, hostname, _fk_Browser, dateaccessed, dbname) "
          + " values (${testoppkey}, ${sessionKey}, ${status}, ${hostname}, ${browserKey}, now(3), ${dbname})";
      String localhost = _commonDll.getLocalhostName ();
      SqlParametersMaps parms8 = (new SqlParametersMaps ()).put ("testoppkey", testoppkeyRef.get ()).put ("status", status).put ("sessionKey", sessionKey).
          put ("hostname", localhost).put ("browserKey", browserKey).put ("dbname", sessionDB);
      executeStatement (connection, fixDataBaseNames (SQL_QUERY8), parms8, false).getUpdateCount ();
    }

    _commonDll._LogDBLatency_SP (connection, "_OpenNewOpportunity", starttime, testee, true, null, testoppkeyRef.get (), null, clientname, null);

    return null;
  }

  public boolean _AllowProctorlessSessions_FN (SQLConnection connection, String clientname) throws ReturnStatusException {

    Integer allow = null;

    final String SQL_QUERY1 = "select IsOn as allow from ${ConfigDB}.client_systemflags where ClientName=${ClientName} and AuditOBject='proctorless'";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("clientname", clientname);
    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY1), parms1, true).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      allow = record.<Integer> get ("allow");
    }
    if (DbComparator.isEqual (allow, 1))
      return true;

    return false;
  }

  public int IsSessionOpen_FN (SQLConnection connection, UUID sessionKey) throws ReturnStatusException {
    Date now = _dateUtil.getDateWRetStatus (connection);

    String clientname = null;
    Long proctorKey = null;
    String sessionId = null;
    final String SQL_QUERY1 = "select clientname, _Efk_Proctor as proctorKey, sessionID  from session  "
        + " where _Key = ${sessionKey} and status in (select status from statuscodes  where `usage` = 'session' and stage = 'open') "
        + " and ( DateBegin + INTERVAL -10 MINUTE) <= ${now} and DateEnd >= ${now}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("sessionkey", sessionKey).put ("now", now);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, true).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      clientname = record.<String> get ("clientname");
      proctorKey = record.<Long> get ("proctorKey");
      sessionId = record.<String> get ("sessionId");
    }
    if (sessionId == null)
      return 0;
    if (proctorKey == null && _AllowProctorlessSessions_FN (connection, clientname) == false)
      return 0;
    return 1;
  }

  public boolean _RestoreRTSAccommodations_FN (SQLConnection connection, String clientname) throws ReturnStatusException {
    Integer allow = null;
    final String SQL_QUERY1 = "select IsOn from ${ConfigDB}.client_systemflags F, externs E "
        + " where E.clientname = ${clientname} and  E.IsPracticeTest = F.IsPracticeTest and F.ClientName=E.Clientname and AuditOBject='RestoreAccommodations' ";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("clientname", clientname);
    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY1), parms1, true).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      allow = record.<Integer> get ("IsOn");
    }
    if (allow == null)
      allow = 0;
    return (allow == 0 ? false : true);

  }

  public SingleDataResultSet T_OpenTestOpportunity_SP_Mysql (SQLConnection connection, Long testee, String testkey, UUID sessionKey, UUID browserKey,
      String guestAccommodations) throws ReturnStatusException {
    
    final String SQL_QUERY = "call T_OpenTestOpportunity(${testee}, ${testkey}, ${sessionKey}, ${browserKey},${guestAccommodations},${debug})";
    SqlParametersMaps params = new SqlParametersMaps ();
    params.put ("testee", testee);
    params.put ("testkey", testkey);
    params.put ("sessionKey", sessionKey);
    params.put ("browserKey", browserKey);
    params.put ("guestAccommodations", guestAccommodations);
    params.put("debug",0);

    SingleDataResultSet resultSet = executeStatement (connection, SQL_QUERY, params, false).getResultSets ().next ();
    return resultSet;
   }

  public SingleDataResultSet T_OpenTestOpportunity_SP (SQLConnection connection, Long testee, String testkey, UUID sessionKey, UUID browserKey) throws ReturnStatusException {

    return T_OpenTestOpportunity_SP (connection, testee, testkey, sessionKey, browserKey, null);
  }

  protected String getTime() {
    java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
    return df.format (new java.util.Date());
  }
  
  // This method is not being used as T_OpenTestOpportunity is migrated to Mysql stored proc. This is the Java version of the t_starttestopportunity procedure
  public SingleDataResultSet T_OpenTestOpportunity_SP (SQLConnection connection, Long testee, String testkey, UUID sessionKey, UUID browserKey,
      String guestAccommodations) throws ReturnStatusException {
    
    Date starttime = _dateUtil.getDateWRetStatus (connection);

    String clientname = null;
    final String SQL_QUERY1 = "select clientname from session where _Key = ${sessionkey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("sessionkey", sessionKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();

    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null)
      clientname = record.<String> get ("clientname");

    String testId = null;
    final String SQL_QUERY2 = "select _efk_TestID from sessiontests where _fk_Session = ${sessionKey} and _efk_AdminSubject = ${testkey}";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("sessionkey", sessionKey).put ("testkey", testkey);
    result = executeStatement (connection, SQL_QUERY2, parms2, false).getResultSets ().next ();

    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null)
      testId = record.<String> get ("_efk_TestID");

    if (testee < 0 && _AllowAnonymousTestee_FN (connection, clientname) == false) {
      return _commonDll._ReturnError_SP (connection, clientname, "T_OpenTestOpportunity", "Guest logins not allowed");
    }

    String testeeId = null, testeeName = null;
    if (testee < 0) {
      testeeId = "GUEST";
      testeeName = "GUEST";
    } else {
      _Ref<String> testeeIdRef = new _Ref<> ();
      _Ref<String> testeeNameRef = new _Ref<> ();
      _rtsDll._GetRTSAttribute_SP (connection, clientname, testee, "ExternalID", testeeIdRef);
      testeeId = testeeIdRef.get ();
      _rtsDll._GetRTSAttribute_SP (connection, clientname, testee, "--ENTITYNAME--", testeeNameRef);
      testeeName = testeeNameRef.get ();
    }
    Integer maxOpportunities = null;
    final String SQL_QUERY3 = "select maxOpportunities from ${ConfigDB}.client_testproperties where clientname = ${clientname} and testID = ${testID}";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testid", testId);
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY3), parms3, false).getResultSets ().next ();

    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null)
      maxOpportunities = record.<Integer> get ("maxOpportunities");

    if (sessionKey != null && IsSessionOpen_FN (connection, sessionKey) == 0) {
      String reason = "The session is not available for testing. Please check with your test administrator.";
      _commonDll._LogDBLatency_SP (connection, "T_OpenTestOpportunity", starttime, testee, true, null, null);
      return _commonDll._ReturnError_SP (connection, clientname, "T_OpenTestOpportunity", reason, null, null, "_CanOpenTestOpportunity");
    }

    _Ref<String> reasonRef = new _Ref<> ();
    _Ref<Boolean> isNewRef = new _Ref<> ();
    _Ref<Integer> oppnumRef = new _Ref<> ();

    _CanOpenTestOpportunity_SP (connection, clientname, testee, testkey, sessionKey, maxOpportunities, isNewRef, oppnumRef, reasonRef);
    String reason = reasonRef.get ();
    Boolean isNew = isNewRef.get ();
    Integer oppnum = oppnumRef.get ();

    if (DbComparator.isEqual (oppnum, 0)) {
      // select 0 as opportunity, 'denied' as [status], @reason as reason,
      // '_CanOpenTestOpportunity' as [context], null as [argstring], null as
      // [delimiter];

      SingleDataResultSet rs = _commonDll.ReturnStatusReason ("denied", reason, "_CanOpenTestOpportunity", null, 0);

      _commonDll._LogDBLatency_SP (connection, "T_OpenTestOpportunity", starttime, testee, true, null, null);
      return rs;
    }
    Long proctor = null;
    String newstatus = null;
    UUID testoppKey = null;
    try {
      final String SQL_QUERY4 = "select _efk_Proctor from session where _Key = ${sessionKey}";
      SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey);
      result = executeStatement (connection, SQL_QUERY4, parms4, false).getResultSets ().next ();

      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null)
        proctor = record.<Long> get ("_efk_Proctor");

      if (DbComparator.isEqual (isNew, true)) {
        // -- automatically approve proctorless session (for practice test)
        if (proctor == null)
          newstatus = "approved";
        else
          newstatus = "pending";

        // -- for operational tests, the student app does not pass along the
        // RTS-based accommodations
        if ((guestAccommodations == null || guestAccommodations.length () == 0) && testee > 0) {

          _Ref<String> guestAccommodationsRef = new _Ref<> ();
          _rtsDll._GetRTSAttribute_SP (connection, clientname, testee, "--ACCOMMODATIONS--", guestAccommodationsRef);
          guestAccommodations = guestAccommodationsRef.get ();
          if (guestAccommodations != null && guestAccommodations.length () == 0)
            guestAccommodations = null;
        }
        _Ref<UUID> testoppKeyRef = new _Ref<> ();
        SingleDataResultSet r = _OpenNewOpportunity_SP (connection, clientname, testee, testkey, oppnum, sessionKey, browserKey, testeeId,
            testeeName, newstatus, guestAccommodations, testoppKeyRef);
        // In caseof success OpenNewOpportunity returns null
        if (r != null) {
          String errStatus = null;
          String errReason = null;
          DbResultRecord rec = r.getRecords ().next ();
          errStatus = rec.<String> get ("status");
          errReason = rec.<String> get ("reason");
          if ("failed".equalsIgnoreCase (errStatus))
            return _commonDll._ReturnError_SP (connection, clientname, "T_OpenTestOpportunity", errReason, null, null, "_OpenNewOpportunity");
        }
        testoppKey = testoppKeyRef.get ();

      } else {

        boolean restoreRts = _RestoreRTSAccommodations_FN (connection, clientname);
        if (restoreRts == true && testee > 0) {

          _Ref<String> guestAccommodationsRef = new _Ref<> ();
          _rtsDll._GetRTSAttribute_SP (connection, clientname, testee, "--ACCOMMODATIONS--", guestAccommodationsRef);
          guestAccommodations = guestAccommodationsRef.get ();
          if (guestAccommodations != null && guestAccommodations.length () == 0)
            guestAccommodations = null;
        }
        _Ref<String> newstatusRef = new _Ref<> ();
        _Ref<UUID> testoppKeyRef = new _Ref<> ();
        _OpenExistingOpportunity_SP (connection, clientname, testee, testkey, oppnum, sessionKey, browserKey,
            newstatusRef, guestAccommodations, restoreRts, testoppKeyRef);
        newstatus = newstatusRef.get ();
        testoppKey = testoppKeyRef.get ();

        if (proctor == null) {
          // -- Note: This is not supposed to happen, but you never know when we
          // will be expected to reopen opportunities for guest logins
          newstatus = "approved";
          // -- automatically approve proctorless session (for practice test)
          final String SQL_QUERY5 = "update testopportunity set status = 'approved' where _Key = ${testoppkey}";
          SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("testoppKey", testoppKey);
          executeStatement (connection, SQL_QUERY5, parms5, false).getUpdateCount ();
        }
      }
    } catch (ReturnStatusException re) {
      // set @error = coalesce(ERROR_PROCEDURE(), 'UNKNOWN PROC') + ': ' +
      // coalesce(ERROR_MESSAGE(), 'UNKNOWN ERROR');
      StackTraceElement[] stackTrace = re.getStackTrace ();
      // String errorProcedure = stackTrace[0].toString();
      String error = String.format ("%s: %s", stackTrace[0].toString (), re.getMessage ());
      _commonDll._LogDBError_SP (connection, "T_OpenTestOpportunity", error, testee, testkey, oppnum, testoppKey, clientname, sessionKey);
      return _commonDll._ReturnError_SP (connection, clientname, "T_OpenTestOpportunity", "Unable to open the test opportunity");
    }

    String sessId = null, proctorName = null, lang = null;
    final String SQL_QUERY6 = "select SessionID, ProctorName from session where _Key = ${sessionKey}";
    SqlParametersMaps parms6 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey);
    result = executeStatement (connection, SQL_QUERY6, parms6, false).getResultSets ().next ();

    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      sessId = record.<String> get ("sessionID");
      proctorName = record.<String> get ("proctorName");
    }

    final String SQL_QUERY7 = " select AccValue from testeeaccommodations  where _fk_TestOpportunity = ${testoppkey} and AccType = 'Language'";
    SqlParametersMaps parms7 = (new SqlParametersMaps ()).put ("testoppKey", testoppKey);
    result = executeStatement (connection, SQL_QUERY7, parms7, false).getResultSets ().next ();

    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      lang = record.<String> get ("accvalue");
    }

    final String SQL_QUERY8 = "update testopportunity set SessID = ${sessID}, Proctorname = ${proctorname}, Language = ${lang}, _fk_session = ${sessionKey} where _Key = ${testoppkey}";
    SqlParametersMaps parms8 = (new SqlParametersMaps ()).put ("testoppKey", testoppKey).put ("sessID", sessId).put ("proctorName", proctorName).
        put ("lang", lang).put ("sessionkey", sessionKey);
    executeStatement (connection, SQL_QUERY8, parms8, false).getUpdateCount ();

    _commonDll._LogDBLatency_SP (connection, "T_OpenTestOpportunity", starttime, testee, true, null, testoppKey);
    // select @oppnum as opportunity, @newstatus as [status], null as reason,
    // @testoppkey as oppkey;
    SingleDataResultSet rs = _commonDll.ReturnStatusReason (newstatus, null, null, testoppKey, oppnum);
    return rs;
  }

  /**
   * 
   * @param connection
   * @param clientName
   * @return
   * @throws ReturnStatusException
   */

  public SingleDataResultSet T_TesteeAttributeMetadata_SP (SQLConnection connection, String clientName) throws ReturnStatusException {
    final String SQL_QUERY = "select TDS_ID, Label, SortOrder, RTSName, Type, atLogin, isLatencySite from ${ConfigDB}.client_testeeattribute where clientname = ${clientName}";
    String finalQuery = fixDataBaseNames (SQL_QUERY);
    SqlParametersMaps parameters = new SqlParametersMaps ().put ("clientName", clientName);
    SingleDataResultSet result = executeStatement (connection, finalQuery, parameters, false).getResultSets ().next ();

    return result;
  }

  /**
   * @param connection
   * @param clientName
   * @return
   * @throws ReturnStatusException
   */

  public Integer _ActiveOpps_FN (SQLConnection connection, String clientName) throws ReturnStatusException {
    Long n = null;
    Date now = _dateUtil.getDateWRetStatus (connection);
    // MySQL datediff gives delta in days and its parameters are dateend folowed
    // by datestart - different than other date functions
    final String SQL_QUERY = "select count(*) as n from testopportunity where (${clientname} = ${ALL} or clientname = ${clientname}) and" +
        " datediff(${now}, dateChanged ) = 0 and status in ('pending', 'suspended', 'approved', 'started', 'review')";

    SqlParametersMaps parameters = new SqlParametersMaps ().put ("clientname", clientName).put ("now", now).put ("ALL", "ALL");
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY, parameters, true).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      n = record.<Long> get ("n");
    }
    return n.intValue ();
  }

  public SingleDataResultSet P_OpenSession_SP (SQLConnection connection, UUID sessionKey) throws ReturnStatusException {
    return P_OpenSession_SP (connection, sessionKey, 12, false, null);
  }

  public SingleDataResultSet P_OpenSession_SP_Mysql (SQLConnection connection, UUID sessionKey, Integer numhours, Boolean suppressReport, UUID browserKey)
      throws ReturnStatusException {
    
    final String SQL_QUERY = "call p_opensession (${sessionKey},${numhours},${suppressReport},${browserKey})";
    SqlParametersMaps params = new SqlParametersMaps ();
    params.put ("sessionKey", sessionKey);
    params.put ("numhours", numhours);
    params.put ("suppressReport", suppressReport);
    params.put ("browserKey", browserKey);

    SingleDataResultSet resultSet = null;
    MultiDataResultSet ms = executeStatement (connection, SQL_QUERY, params, false);
    if(ms.getUpdateCount ()<=0) {
      resultSet = ms.getResultSets ().next ();
    }
    
    return resultSet;
    
  }
  public SingleDataResultSet P_OpenSession_SP (SQLConnection connection, UUID sessionKey, Integer numhours, Boolean suppressReport, UUID browserKey)
      throws ReturnStatusException {

    if (numhours == null)
      numhours = 12;
    if (suppressReport == null)
      suppressReport = false;

    Date now = _dateUtil.getDateWRetStatus (connection);

    Calendar c = Calendar.getInstance ();
    c.setTime (now);
    c.add (Calendar.HOUR, numhours);
    Date enddate = c.getTime ();

    // -- this should only happen when 'virtual proctor session' is created for
    // practice tests
    if (browserKey == null)
      browserKey = UUID.randomUUID ();

    String clientname = null;
    Date createdate = null, sesstart = null, sesend = null;
    final String SQL_QUERY1 = "Select clientname,  DateCreated as createdate, DateEnd as sesend, DateBegin as sesstart from session where _Key = ${sessionKey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      clientname = record.<String> get ("clientname");
      createdate = record.<Date> get ("createdate");
      sesstart = record.<Date> get ("sesstart");
      sesend = record.<Date> get ("sesend");
    }

    if (createdate == null) {
      String msg = String.format ("No such session: %s", sessionKey.toString ());
      _commonDll._RecordSystemError_SP (connection, "P_OpenSession", msg);
      return _commonDll._ReturnError_SP (connection, clientname, "P_OpenSession", "No such session");
    }

    // -- make sure begin/end dates are reliable. Change end date if null or if
    // earlier than new computed enddate.
    if (sesend != null && DbComparator.lessThan (enddate, sesend))
      enddate = sesend;

    // -- change start date if null or later than now
    if (sesstart == null || DbComparator.greaterThan (sesstart, now))
      sesstart = now;

    final String SQL_QUERY2 = "Update session set status = 'open', DateChanged = ${now}, DateVisited = ${now}, DateEnd = ${enddate}, DateBegin = ${sesstart}, "
        + " _fk_browser = ${browserKey} where _Key = ${sessionKey}";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("now", now).put ("enddate", enddate).put ("sesstart", sesstart).
        put ("browserkey", browserKey).put ("sessionkey", sessionKey);

    executeStatement (connection, SQL_QUERY2, parms2, false).getUpdateCount ();

    if (_commonDll.AuditSessions_FN (connection, clientname) == 1) {
      // String sessionDB = getAppSettings ().get ("TDSSessionDBName");
      String sessionDB = getTdsSettings ().getTDSSessionDBName ();
      final String SQL_QUERY3 = " insert into ${ArchiveDB}.sessionaudit (_fk_session, DateAccessed, AccessType, hostname, browserkey, dbname) "
          + " values (${sessionKey}, ${now}, 'open', ${hostname}, ${browserKey},  ${dbname})";
      String localhostname = _commonDll.getLocalhostName ();
      SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("sessionkey", sessionKey).put ("now", now).put ("hostname", localhostname).
          put ("browserkey", browserKey).put ("dbname", sessionDB);
      executeStatement (connection, fixDataBaseNames (SQL_QUERY3), parms3, false).getUpdateCount ();
    }

    if (suppressReport == false)
      result = _commonDll.ReturnStatusReason ("open", null);
    else
      result = null;

    _commonDll._LogDBLatency_SP (connection, "P_OpenSession", now, null, true, null, null, sessionKey, clientname, null);

    return result;
  }

  /**
   * 
   * @param connection
   * @param clientname
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   */
  public DataBaseTable GetActiveTests_FN (SQLConnection connection, String clientname, Integer sessionType) throws ReturnStatusException {
    DataBaseTable tbl = getDataBaseTable ("activeTests").addColumn ("testId", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200).addColumn ("subject", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).
        addColumn ("maxOpportunities", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("label", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 255).
        addColumn ("IsSelectable", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("sortOrder", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("windowMax", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("windowID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).
        addColumn ("startDate", SQL_TYPE_To_JAVA_TYPE.DATETIME).
        addColumn ("endDate", SQL_TYPE_To_JAVA_TYPE.DATETIME).addColumn ("mode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).
        addColumn ("testkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).addColumn ("modeMax", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("windowSession", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("modeSession", SQL_TYPE_To_JAVA_TYPE.INT);

    connection.createTemporaryTable (tbl);
    Date now = _dateUtil.getDateWRetStatus (connection);

    final String SQL_QUERY = "insert into ${tblName} (testid, subject, maxOpportunities, label, isSelectable, sortOrder, windowMax, WindowId, "
        + "  startDate, endDate, mode, testKey, modeMax, windowSession, modeSession) "
        + " select distinct P.testID, P.subjectname as subject, P.maxOpportunities, P.Label, IsSelectable, P.sortOrder, "
        + " W.numopps as windowMax, W.windowID "
        + ", case when W.startDate is null then ${now} else ( W.startDate + INTERVAL shiftWindowStart DAY) end as startDate "
        + ", case when W.endDate is null then ${now} else ( W.endDate + INTERVAL shiftWindowEnd DAY) end as endDate "
        + ", M.mode, M.testkey, M.maxopps as modeMax , W.sessionType as windowSession, M.sessionType as modeSession "
        + " from ${ConfigDB}.client_testwindow W, ${ConfigDB}.client_testmode M, ${ConfigDB}.client_testproperties P "
        + "   , _externs E ,${ItemBankDB}.tblsetofadminsubjects BANK   "
        + " where P.clientname = ${clientname} and E.clientname = ${clientname} and W.clientname = ${clientname} and W.testID = P.TestID "
        + "   and ${now} between  case when W.startDate is null then ${now} else ( W.startDate + INTERVAL shiftWindowStart DAY) end "
        + "                   and case when W.endDate is null then ${now} else (W.endDate + INTERVAL shiftWindowEnd DAY ) end "
        + "   and M.clientname = ${clientname} and M.TestID = P.TestID"
        + "   and (M.sessionType = -1 or M.sessionType = ${sessionType}) and (W.sessionType = -1 or W.sessionType = ${sessionType}) "
        + "   and IsSelectable = 1 and BANK._Key = M.testkey";

    SqlParametersMaps parms = (new SqlParametersMaps ()).put ("now", now).put ("clientname", clientname).put ("sessionType", sessionType);
    Map<String, String> unquotedParms = new HashMap<> ();
    unquotedParms.put ("tblName", tbl.getTableName ());
    final String query = fixDataBaseNames (SQL_QUERY);
    executeStatement (connection, fixDataBaseNames (query, unquotedParms), parms, false).getUpdateCount ();
    return tbl;
  }
  
 /* public void _SetupProctorlessSession_SP (SQLConnection connection, String clientname, _Ref<UUID> sessionKeyRef, _Ref<String> sessionIdRef) throws ReturnStatusException {
   try {
      final String SQL_QUERY = "{call _setupproctorlesssession(?,?,?)}";
      CallableStatement callableStatement = connection.prepareCall(SQL_QUERY);
      callableStatement.setString (1, clientname);
      callableStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
      callableStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
       
      callableStatement.executeUpdate();
      String sessionKeyStr = callableStatement.getString(2);
      sessionKeyRef.set (UUID.fromString (sessionKeyStr));
      sessionIdRef.set ("GUEST Session");
      
    } catch (Exception e) {
      throw new ReturnStatusException (e);
    }
    
  }*/
  
  public void _SetupProctorlessSession_SP (SQLConnection connection, String clientname, _Ref<UUID> sessionKeyRef, _Ref<String> sessionIdRef) throws ReturnStatusException {

    if (_AllowAnonymousTestee_FN (connection, clientname) == false) {
      _commonDll._RecordSystemError_SP (connection, "_SetupProctorlessSession", "Attempt to set up proctorless session forbidden");
      return;
    }
    sessionIdRef.set ("GUEST Session");
    String status = null;
    Date enddate = null;
    Float timeelapsed = null;
    final String SQL_QUERY2 = "select _Key as sessionKey, status, dateend as enddate, cast(TIMESTAMPDIFF(microsecond, datechanged, now(3))/1000000  as char) as timeelapsed from session where clientname = ${clientname} and sessionID = ${sessionID}";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("sessionId", sessionIdRef.get ());
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY2, parms2, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      sessionKeyRef.set (record.<UUID> get ("sessionKey"));
      status = record.<String> get ("status");
      enddate = record.<Date> get ("enddate");
      timeelapsed = Float.parseFloat (record.<String> get ("timeelapsed"));
    }
    
    if(sessionKeyRef.get ()!=null && timeelapsed!=null && timeelapsed <= 60) {
      return;
    }
    
    
    String environ = _commonDll.getExternsColumnByClientName (connection, clientname, "environment");

    DataBaseTable activeTestsTbl = GetActiveTests_FN (connection, clientname, 0);

    DataBaseTable testsTbl = getDataBaseTable ("tests").addColumn ("testkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).addColumn ("testId", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200);
    connection.createTemporaryTable (testsTbl);

    final String SQL_QUERY1 = " insert into ${testsTblName} (testkey, testID) select distinct testkey, testID from ${activeTestsTbl} where isSelectable = 1";
    Map<String, String> unquotedParms1 = new HashMap<> ();
    unquotedParms1.put ("testsTblName", testsTbl.getTableName ());
    unquotedParms1.put ("activeTestsTbl", activeTestsTbl.getTableName ());

    executeStatement (connection, fixDataBaseNames (SQL_QUERY1, unquotedParms1), null, false).getUpdateCount ();
    connection.dropTemporaryTable (activeTestsTbl);

    

    Integer applock = -1;
    String resourcename = String.format ("guestsession %s", clientname);
    boolean sessionKeyWasNull = false;
    try {
      
      if (sessionKeyRef.get () == null) {
        sessionKeyWasNull = true; 
        // MySql app lock returns 1 is success
        applock = _commonDll.getAppLock (connection, resourcename, "Exclusive");

        if (applock == null || applock != 1) {
          _commonDll._RecordSystemError_SP (connection, "_SetupProctorlessSession", "Unable to obtain applock");

          sessionKeyRef.set (null);
          sessionIdRef.set (null);
          connection.dropTemporaryTable (testsTbl);
          return;
        }
        boolean preexistingAutoCommitMode = connection.getAutoCommit ();
        connection.setAutoCommit (false);

        sessionKeyRef.set (UUID.randomUUID ());

        // _fk_Browser is a required field but is not used for proctorless
        // sessions, so we dummy it with the same GUID as session key
        final String SQL_QUERY3 = "insert into session (_key,  _fk_browser, SessionID, name, clientname, environment,  datecreated, serveraddress) "
            + "  select ${sessionKey},  ${sessionKey}, ${sessionID}, 'TDS Session', ${clientname}, ${environ}, "
            + " now(3), ${localhost} from dual "
            + " where not exists (select * from session where clientname = ${clientname} and sessionid = ${sessionID})";
        SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("sessionKey", sessionKeyRef.get ()).put ("sessionId", sessionIdRef.get ()).
            put ("clientname", clientname).put ("environ", environ).put ("localhost", _commonDll.getLocalhostName ());
        executeStatement (connection, SQL_QUERY3, parms3, false).getUpdateCount ();

        applock = -1;
        connection.commit ();
        connection.setAutoCommit (preexistingAutoCommitMode);
        _commonDll.releaseAppLock (connection, resourcename);
      }

    } catch (SQLException se) {
      if (DbComparator.isEqual (applock, 1))
        _commonDll.releaseAppLock (connection, resourcename);
      throw new ReturnStatusException (se);
    } catch (ReturnStatusException re) {

      try {
        connection.rollback ();
      } catch (Exception e) {
      }

      if (DbComparator.isEqual (applock, 1))
        _commonDll.releaseAppLock (connection, resourcename);
      throw re;
    }

    if (sessionKeyWasNull) {
      final String SQL_QUERY5 = "select _Key as sessionKey from session where clientname = ${clientname} and sessionID = ${sessionID}";
      SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("sessionId", sessionIdRef.get ());
      SingleDataResultSet result5 = executeStatement (connection, SQL_QUERY5, parms5, false).getResultSets ().next ();
      DbResultRecord record5 = (result5.getCount () > 0 ? result5.getRecords ().next () : null);
      if (record5 != null) {
        sessionKeyRef.set (record5.<UUID> get ("sessionKey"));
      }
    }
    if (sessionKeyRef.get () == null) {
      throw new ReturnStatusException ("No sessionkey found in sessiontbl for " + clientname);
    }
    final String SQL_QUERY4 = "insert into sessiontests (_fk_Session, _efk_AdminSubject, _efk_TestID) select ${sessionKey}, testkey, testID from ${testsTblName} "
        + " where not exists (select * from sessiontests where _fk_Session = ${sessionKey} and _efk_AdminSubject = testkey)";
    Map<String, String> unquotedParms4 = new HashMap<> ();
    unquotedParms4.put ("testsTblName", testsTbl.getTableName ());
    SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("sessionKey", sessionKeyRef.get ());
    executeStatement (connection, fixDataBaseNames (SQL_QUERY4, unquotedParms4), parms4, false).getUpdateCount ();
    connection.dropTemporaryTable (testsTbl);

    P_OpenSession_SP (connection, sessionKeyRef.get (), 12, true, null);

  }

  public void _T_ValidateTesteeLogin_SP (SQLConnection connection, String clientname, String testeeId, String sessionId,
      _Ref<String> reasonRef, _Ref<Long> testeeKeyRef) throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);

    // START: Get internal key for student with official ID testeeId
    _rtsDll._GetRTSEntity_SP (connection, clientname, testeeId, "STUDENT", testeeKeyRef);
    if (testeeKeyRef.get () == null) {
      reasonRef.set ("No match for student ID");
      return;
    }
    // END: Get internal key for student with official ID testeId

    // START: Block login if "parent exempt"
    final String SQL_QUERY1 = "select RTSFieldName from ${ConfigDB}.client_testtooltype "
        + " where Clientname = ${clientname} and ContextType = 'TEST' and Context = '*' and ToolName = 'Parent Exempt'";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("clientname", clientname);
    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY1), parms1, false).getResultSets ().next ();

    String rtsField = null;
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      rtsField = record.<String> get ("rtsFieldname");
    }
    // -- check for parent exemption from all tests (no point in logging in)
    _Ref<String> rtsValueRef = new _Ref<> ();
    if (rtsField != null) {

      _rtsDll._GetRTSAttribute_SP (connection, clientname, testeeKeyRef.get (), rtsField, rtsValueRef);
      if (DbComparator.isEqual (rtsValueRef.get (), "TDS_ParentExempt1")) {
        reasonRef.set ("parent exempt");

        _commonDll._LogDBLatency_SP (connection, "_T_ValidateTesteeLogin", starttime, testeeKeyRef.get (), true,
            null, null, null, clientname, null);
        return;
      }
    }
    // END: Block login if "parent exempt"

    Integer schoolmatch = null;
    final String SQL_QUERY2 = "select IsOn from ${ConfigDB}.client_systemflags where ClientName=${clientname} and AuditObject = 'MatchTesteeProctorSchool'";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("clientname", clientname);
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY2), parms2, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      schoolmatch = record.<Integer> get ("IsOn");
    }

    if (DbComparator.isEqual (schoolmatch, 1)) {
      if (sessionId == null) {
        // -- this is an internal system error
        _commonDll._RecordSystemError_SP (connection, "T_GetRTSTestee", "Missing session ID");
        reasonRef.set ("Session ID required");
        _commonDll._LogDBLatency_SP (connection, "_T_ValidateTesteeLogin", starttime, testeeKeyRef.get (), true,
            null, null, null, clientname, null);
        return;
      }
      // -- proctor key is the USERKEY in RTS, NOT the Entity key

      Long proctorKey = null;
      final String SQL_QUERY3 = "select _efk_Proctor from session where clientname = ${clientname} and sessionID = ${sessionID} "
          + " and status = 'open' and ${now} between DateBegin and DateEnd";
      SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("sessionID", sessionId).put ("now", starttime);
      result = executeStatement (connection, SQL_QUERY3, parms3, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        proctorKey = record.<Long> get ("_efk_Proctor");
      }
      if (proctorKey == null) {
        reasonRef.set ("The session is not available for testing");
        _commonDll._LogDBLatency_SP (connection, "_T_ValidateTesteeLogin", starttime, testeeKeyRef.get (), true,
            null, null, null, clientname, null);
        return;
      }

      _Ref<String> schoolKeyRef = new _Ref<> ();

      _rtsDll._ValidateInstitutionMatch_SP (connection, clientname, testeeKeyRef.get (), proctorKey, schoolKeyRef);
      if (schoolKeyRef.get () == null) {
        reasonRef.set ("You must test in a session in your own school");
        _commonDll._LogDBLatency_SP (connection, "_T_ValidateTesteeLogin", starttime, testeeKeyRef.get (), true,
            null, null, null, clientname, null);
        return;
      }
    }
    _commonDll._LogDBLatency_SP (connection, "_T_ValidateTesteeLogin", starttime, testeeKeyRef.get (), true, null, null, null, clientname, null);
  }

  public MultiDataResultSet T_Login_SP (SQLConnection connection, String clientname, Map<String, String> keyValues) throws ReturnStatusException {
    return T_Login_SP (connection, clientname, keyValues, null);
  }

  @Override
  public MultiDataResultSet T_Login_SP (SQLConnection connection, String clientname, Map<String, String> keyValues, String sessionId) throws ReturnStatusException {

    List<SingleDataResultSet> resultsSets = new ArrayList<SingleDataResultSet> ();
    Date starttime = _dateUtil.getDateWRetStatus (connection);
    String externalId = null;
    Long entity = null;
    String inval = null, type = null, field = null;

    // START: Accounting: how many open test opportunities are there currently
    // for this client?
    try {

      Integer numOpps = _ActiveOpps_FN (connection, clientname);

      Date lastupdate = null;
      final String SQL_QUERY1 = "select max(_time) as lastupdate from _maxtestopps where clientname = ${clientname}";
      SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("clientname", clientname);
      SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
      DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        lastupdate = record.<Date> get ("lastupdate");
      }

      if (lastupdate == null || DbComparator.greaterOrEqual (minutesDiff (lastupdate, starttime), 10)) {

        final String SQL_QUERY2 = "insert into _maxtestopps (numopps,  _time, clientname) values (${numopps},  ${now}, ${clientname})";
        SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("numopps", numOpps).put ("now", starttime).put ("clientname", clientname);
        executeStatement (connection, SQL_QUERY2, parms2, false).getUpdateCount ();
      }
    } catch (ReturnStatusException re) {
      // TODO: find out:externalId is not set up at this point!
      String error = String.format (" for testee ID %s: %s", externalId, re.getMessage ());
      _commonDll._LogDBError_SP (connection, "T_Login", error, null, null, null, null, clientname, null);
    }
    // END: Accounting: how many open test opportunities are there currently for
    // this client?

    // START: Retrieve a list of fields that this client expects at login

    // declare @vals table (_key varchar(50), inval varchar(200), outval
    // varchar(200), field varchar(100), atttype varchar(50), action
    // varchar(50));
    DataBaseTable valsTbl = getDataBaseTable ("valsTable").addColumn ("_key", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).
        addColumn ("inval", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200).addColumn ("outval", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200).addColumn ("field", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).
        addColumn ("atttype", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("action", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50);
    connection.createTemporaryTable (valsTbl);

    final String SQL_QUERY3 = "insert into ${valsTblName} (_key, field, atttype, action) "
        + " select TDS_ID, RTSName, type, atLogin from ${ConfigDB}.client_testeeattribute "
        + " where clientname = ${clientname} and atLogin is not null";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("clientname", clientname);
    Map<String, String> unquotedParms3 = new HashMap<> ();
    unquotedParms3.put ("valsTblName", valsTbl.getTableName ());
    final String query3 = fixDataBaseNames (SQL_QUERY3);
    executeStatement (connection, fixDataBaseNames (query3, unquotedParms3), parms3, false).getUpdateCount ();
    // END: Retrieve a list of fields that this client expects at login

    // START: Update list of fields with the values supplied from the login form
    // (form values were passed into fn as argument)
    for (Entry<String, String> pair : keyValues.entrySet ()) {
      final String SQL_QUERY4 = "update ${valsTblName} set inval = ${theRight} where _key = ${theLeft}";
      Map<String, String> unquotedParms4 = unquotedParms3;
      SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("theRight", pair.getValue ()).put ("theLeft", pair.getKey ());
      executeStatement (connection, fixDataBaseNames (SQL_QUERY4, unquotedParms4), parms4, false).getUpdateCount ();
    }
    // END: Update list of fields with the values supplied from the login form
    // (form values were passed into fn as argument)

    // START: Retrieve the student ID
    // -- ID is ALWAYS REQUIRED
    final String SQL_QUERY5 = "select  inval, atttype  as type from ${valsTblName} where _key = 'ID'";
    Map<String, String> unquotedParms5 = unquotedParms3;
    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY5, unquotedParms5), parms3, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      inval = record.<String> get ("inval");
      type = record.<String> get ("type");
    }
    if (inval == null) {
      //SB-797
      //resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_Login", "No ID"));
      resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_Login", "No match for student ID"));

      // TODO Elena: check it out: entity is not set up at this point
      _commonDll._LogDBLatency_SP (connection, "T_Login", starttime, entity, true, null, null, null, clientname, null);
      connection.dropTemporaryTable (valsTbl);
      return (new MultiDataResultSet (resultsSets));
    }

    // The value of the student ID provided on the login form.
    externalId = inval;
    // END: Retrieve the student ID

    _Ref<UUID> sessionKeyRef = new _Ref<> ();
    UUID sessionKey = null;

    // START: Open guest session, if requested and permitted by client
    // configuration
    if (DbComparator.isEqual (sessionId, "GUEST Session")) {
      if (_AllowProctorlessSessions_FN (connection, clientname)) {
        _Ref<String> sessionIdRef = new _Ref<> ();
        _SetupProctorlessSession_SP (connection, clientname, sessionKeyRef, sessionIdRef);
        sessionId = sessionIdRef.get ();
        sessionKey = sessionKeyRef.get ();
      } else {
        connection.dropTemporaryTable (valsTbl);
        resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_Login", "You are not allowed to log in without a Test Administrator"));
        return (new MultiDataResultSet (resultsSets));
      }
    }
    // END: Open guest session, if requested and permitted by client
    // configuration

    // START: Handle request for anonymous login for practice test (assumes
    // GUEST session?)
    Long gkey = 0L;
    if (DbComparator.isEqual (externalId, "GUEST") && _AllowAnonymousTestee_FN (connection, clientname)) {

      final String SQL_QUERY6 = "insert into _anonymoustestee (dateCreated, clientname) values (${now}, ${clientname})";
      SqlParametersMaps parms6 = (new SqlParametersMaps ()).put ("now", starttime).put ("clientname", clientname);
      executeStatement (connection, SQL_QUERY6, parms6, false).getUpdateCount ();

      // to get auto_increment _key column, we use
      // SELECT LAST_INSERT_ID() 
      final String SQL_QUERY7 = "select cast(LAST_INSERT_ID() as SIGNED) as maxkey";
      result = executeStatement (connection, SQL_QUERY7, null, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        gkey = record.<Long> get ("maxKey");
        gkey = (gkey == null ? 0 : (-1 * gkey));
      }

      String outValue = String.format ("GUEST %d", gkey);
      final String SQL_QUERY8 = "update ${valsTblName} set outval = ${outValue} where _Key = 'ID'";
      SqlParametersMaps parms8 = (new SqlParametersMaps ()).put ("outValue", outValue);
      Map<String, String> unquotedParms8 = unquotedParms3;
      executeStatement (connection, fixDataBaseNames (SQL_QUERY8, unquotedParms8), parms8, false).getUpdateCount ();

      final String SQL_QUERY9 = "update  ${valsTblName} set outval = 'GUEST' where _Key in ('Lastname', 'Firstname')";
      Map<String, String> unquotedParms9 = unquotedParms3;
      executeStatement (connection, fixDataBaseNames (SQL_QUERY9, unquotedParms9), null, false).getUpdateCount ();

      final String SQL_QUERY10 = "update  ${valsTblName} set outval = concat('GUEST', _Key) where atttype = 'relationship'";
      Map<String, String> unquotedParms10 = unquotedParms3;
      executeStatement (connection, fixDataBaseNames (SQL_QUERY10, unquotedParms10), null, false).getUpdateCount ();

      // select 'success' as status, @gkey as entityKey, null as accommodations;
      List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
      CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
      rcd.put ("status", "success");
      rcd.put ("entityKey", gkey);
      rcd.put ("accommodations", null);
      resultList.add (rcd);

      SingleDataResultSet rs1 = new SingleDataResultSet ();
      rs1.addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      rs1.addColumn ("entityKey", SQL_TYPE_To_JAVA_TYPE.BIGINT);
      rs1.addColumn ("accommodations", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      rs1.addRecords (resultList);

      resultsSets.add (rs1);

      final String SQL_QUERY11 = "select TDS_ID, outval as Value, Label, SortOrder, atLogin from ${valsTblName}, ${ConfigDB}.client_testeeattribute "
          + " where clientname = ${clientname} and _Key = TDS_ID order by SortOrder";
      Map<String, String> unquotedParms11 = unquotedParms3;
      SqlParametersMaps parms11 = (new SqlParametersMaps ()).put ("clientname", clientname);
      final String query11 = fixDataBaseNames (SQL_QUERY11);
      SingleDataResultSet rs2 = executeStatement (connection, fixDataBaseNames (query11, unquotedParms11), parms11, false).getResultSets ().next ();
      resultsSets.add (rs2);

      // TODO Elena: check it out: entity is not set up at this point
      _commonDll._LogDBLatency_SP (connection, "T_Login", starttime, entity, true, null, null, null, clientname, null);
      connection.dropTemporaryTable (valsTbl);

      return (new MultiDataResultSet (resultsSets));
    }
    // END: Handle request for anonymous login for practice test

    // START: Handle request for validated login.
    // -- This 'may' be a real student. Attempt to validate that
    _Ref<String> errorRef = new _Ref<> ();
    _Ref<Long> entityRef = new _Ref<> ();
    _T_ValidateTesteeLogin_SP (connection, clientname, externalId, sessionId, errorRef, entityRef);
    entity = entityRef.get ();
    if (errorRef.get () != null) {

      resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_Login", errorRef.get ()));
      _commonDll._LogDBLatency_SP (connection, "T_Login", starttime, entity, true, null, null, null, clientname, null);
      connection.dropTemporaryTable (valsTbl);

      return (new MultiDataResultSet (resultsSets));
    }

    // -- Student has been validated against the proctor and a valid RTS Key
    // returned. Now check required attributes
    final String SQL_QUERY12 = "update ${valsTblName} set outval = inval where _Key = 'ID'";
    Map<String, String> unquotedParms12 = unquotedParms3;
    executeStatement (connection, fixDataBaseNames (SQL_QUERY12, unquotedParms12), null, false).getUpdateCount ();

    final String SQL_QUERY13 = "select  action from ${valsTblName} where outval is null and action = 'REQUIRE' limit 1";

    final String SQL_QUERY14 = " select  field,  atttype as type from ${valsTblName} where outval is null and action = 'REQUIRE' limit 1";

    final String SQL_QUERY15 = "update ${valsTblName} set outval = ${value}  where field = ${field}";

    LoginHelper loginHelper = new LoginHelper ();
    loginHelper.setClientname (clientname);
    loginHelper.setEntity (entity);
    loginHelper.setTbl (valsTbl);
    loginHelper.setQueryExists (SQL_QUERY13);
    loginHelper.setQueryExec (SQL_QUERY14);
    loginHelper.setQueryUpdate (SQL_QUERY15);

    loginHelper.doIt (connection);

    // -- check for login failure due to bad required input data
    final String SQL_QUERY16 = "select  _key from ${valsTblName} where _key <> 'ID' and action = 'REQUIRE' and (outval is null or inval is null or inval <> outval ) limit 1";

    Map<String, String> unquotedParms16 = unquotedParms3;
    if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY16, unquotedParms16), null, false))) {

      resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_Login", "No match"));
      _commonDll._LogDBLatency_SP (connection, "T_Login", starttime, entity, true, null, null, null, clientname, null);
      connection.dropTemporaryTable (valsTbl);

      return (new MultiDataResultSet (resultsSets));
    }

    // -- get the remaining verify data and data required by student app for
    // practice tests
    final String SQL_QUERY17 = "select  _key from ${valsTblName} where outval is null  limit 1";

    final String SQL_QUERY18 = " select  field,  atttype as type from ${valsTblName} where outval is null  limit 1";

    loginHelper.setQueryExists (SQL_QUERY17);
    loginHelper.setQueryExec (SQL_QUERY18);
    loginHelper.setQueryUpdate (SQL_QUERY15); // this one stays the same

    loginHelper.doIt (connection);

    // select 'success' as status, @entity as entityKey;
    List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
    CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
    rcd.put ("status", "success");
    rcd.put ("entityKey", entity);
    resultList.add (rcd);

    SingleDataResultSet rs1 = new SingleDataResultSet ();
    rs1.addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs1.addColumn ("entityKey", SQL_TYPE_To_JAVA_TYPE.BIGINT);
    rs1.addRecords (resultList);

    resultsSets.add (rs1);

    final String SQL_QUERY19 = "select TDS_ID, outval as Value, Label, SortOrder, atLogin from ${valsTblName}, ${ConfigDB}.client_testeeattribute "
        + " where clientname = ${clientname} and _Key = TDS_ID and atLogin in ('REQUIRE', 'VERIFY') order by SortOrder";
    Map<String, String> unquotedParms19 = unquotedParms3;
    SqlParametersMaps parms19 = (new SqlParametersMaps ()).put ("clientname", clientname);
    final String query19 = fixDataBaseNames (SQL_QUERY19);
    SingleDataResultSet rs2 = executeStatement (connection, fixDataBaseNames (query19, unquotedParms19), parms19, false).getResultSets ().next ();
    resultsSets.add (rs2);

    _commonDll._LogDBLatency_SP (connection, "T_Login", starttime, entity, true, null, null, null, clientname, null);
    connection.dropTemporaryTable (valsTbl);

    return (new MultiDataResultSet (resultsSets));
  }

  /**
   * @param connection
   * @param oppKey
   * @param page
   * @param segment
   * @param segmentId
   * @param groupId
   * @param msg
   * @return
   * @throws ReturnStatusException
   */
  public void _ValidateItemInsert_SP (SQLConnection connection, UUID oppKey, Integer page, Integer segment, String segmentId, String groupId, _Ref<String> msg) throws ReturnStatusException {

    SingleDataResultSet result = null;
    Integer lastPage = null;
    Integer lastSegment = null;
    Integer lastPosition = null;

    if (_debug)
    {
      _logger.debug ("_ValidateItemInsert_SP: oppKey = " + oppKey);
      _logger.debug (" page = " + page + ", segment = " + segment + ", segmentId = " + segmentId + ", groupId = " + groupId);
    }

    /*final String SQL_QUERY1 = "select max(position) as lastPosition from testeeresponse where _fk_TestOpportunity = ${oppkey} and _efk_ITSItem is not null;";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("oppkey", oppKey);
    result = executeStatement (connection, SQL_QUERY1, parms, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      lastPosition = record.<Integer> get ("lastPosition");
    }*/

//    final String SQL_QUERY2 = "select segment as lastSegment, page as lastPage from testeeresponse where _fk_TestOpportunity = ${oppkey} and position = ${lastPosition};";
    final String SQL_QUERY2 = "select segment as lastSegment, page as lastPage, position as lastPosition from testeeresponse where _fk_TestOpportunity = ${oppkey} and position = (select max(position) as lastPosition from testeeresponse where _fk_TestOpportunity = ${oppkey} and _efk_ITSItem is not null);";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("oppkey", oppKey)/*.put ("lastPosition", lastPosition)*/;
    result = executeStatement (connection, SQL_QUERY2, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      lastSegment = record.<Integer> get ("lastSegment");
      lastPage = record.<Integer> get ("lastPage");
      lastPosition = record.<Integer> get ("lastPosition");
    }
    if (_debug)
    {
      _logger.debug ("_ValidateItemInsert_SP: oppKey = " + oppKey);
      _logger.debug ("lastSegment = " + lastSegment + ", lastPosition = " + lastPosition + ", lastpage = " + lastPage);
    }
    if (DbComparator.lessOrEqual (page, lastPage)) {
      msg.set (String.format ("Attempt to overwrite existing page: %d", page));
      return;
    }
    if (lastPage != null && DbComparator.greaterThan (page, lastPage + 1)) {
      msg.set (String.format ("Attempt to write non-contiguous page: %d", page));
      return;
    }
    if (DbComparator.lessThan (segment, lastSegment) || (lastSegment != null && DbComparator.greaterThan (segment, lastSegment + 1))) {
      msg.set (String.format ("Attempt to write non-contiguous segment: %d", segment));
      return;
    }
    final String SQL_QUERY3 = "select  _fk_TestOpportunity from testeeresponse where _fk_TestOpportunity = ${oppkey} and groupID = ${groupID} limit 1";
    SqlParametersMaps parms2 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("groupID", groupId);
    if (exists (executeStatement (connection, SQL_QUERY3, parms2, false))) {
      msg.set (String.format ("Attempt to duplicate existing item group: %s; attempting segment: %s", groupId, segmentId));
      return;
    }
    final String SQL_QUERY4 = "select  _fk_TestOpportunity from testopportunitysegment where _fk_TestOpportunity = ${oppkey} and segmentPosition = ${segment} and segmentID = ${segmentID} limit 1";
    SqlParametersMaps parms3 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("segment", segment).put ("segmentID", segmentId);
    if (!exists (executeStatement (connection, SQL_QUERY4, parms3, false))) {
      msg.set ("Segment ID does not match segment position");
      return;
    }
  }

  /**
   * @param connection
   * @param oppKey
   * @param sessionKey
   * @param browserId
   * @param segment
   * @param segmentId
   * @param page
   * @param groupId
   * @param itemKeys
   * @param delimiter
   * @param groupItemsRequired
   * @param groupB
   * @param debug
   * @param noinsert
   * @return
   * @throws ReturnStatusException
   */
  // Original port
  public MultiDataResultSet T_InsertItems_SPV1 (SQLConnection connection, UUID oppKey, UUID sessionKey, UUID browserId, Integer segment, String segmentId, Integer page, String groupId,
      String itemKeys, Character delimiter, Integer groupItemsRequired, Float groupB, Integer debug, Boolean noinsert) throws ReturnStatusException {

    List<SingleDataResultSet> resultsSets = new ArrayList<SingleDataResultSet> ();
    Date starttime = _dateUtil.getDateWRetStatus (connection);
    String localhostname = _commonDll.getLocalhostName ();
    _Ref<String> error = new _Ref<> ();
    Long testee = null;

    _ValidateTesteeAccessProc_SP (connection, oppKey, sessionKey, browserId, false, error);
    if (error.get () != null) {
      resultsSets.add (_commonDll._ReturnError_SP (connection, null, "T_InsertItems", error.get (), null, oppKey, "_ValidateTesteeAccesss", "denied"));
      return (new MultiDataResultSet (resultsSets));
    }

    Integer count = null;
    Integer opprestart = null;
    String status = null;
    String clientname = null;
    String environment = null;
    String language = null;
    String item = null;
    String testkey = null;
    Integer lastPosition = null;
    String msg = null;
    String segmentKey = null;
    String formKey = null;
    String algorithm = null;
    String argstring = null;

    DataBaseTable itemsTable = getDataBaseTable ("items").addColumn ("p", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("itemkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50);
    connection.createTemporaryTable (itemsTable);
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("itemsTableName", itemsTable.getTableName ());

    if (itemKeys != null) {
      DataBaseTable buildTable = _commonDll._BuildTable_FN (connection, "_BuildTable", itemKeys, delimiter.toString ());
      final String SQL_INSERT1 = "insert into ${itemsTableName} (p, itemkey) select idx, record from ${temporaryTableName};";
      Map<String, String> unquotedParms1 = new HashMap<String, String> ();
      unquotedParms1.put ("itemsTableName", itemsTable.getTableName ());
      unquotedParms1.put ("temporaryTableName", buildTable.getTableName ());
      executeStatement (connection, fixDataBaseNames (SQL_INSERT1, unquotedParms1), null, false).getUpdateCount ();
      // System.err.println (insertedCnt); // for testing
      connection.dropTemporaryTable (buildTable);
    }

    final String SQL_QUERY1 = "select clientname, _efk_AdminSUbject as testkey, Restart as opprestart, status, environment, _efk_Testee as testee  from testopportunity where _key = ${oppkey};";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("oppkey", oppKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      clientname = record.<String> get ("clientname");
      testkey = record.<String> get ("testkey");
      opprestart = record.<Integer> get ("opprestart");
      status = record.<String> get ("status");
      environment = record.<String> get ("environment");
      testee = record.<Long> get ("testee");
    }

    if (DbComparator.notEqual (environment, "production")) {
      // / set @trace = 'TRACING ' + @groupID + ':' + @itemkeys;
      String trace = String.format ("TRACING groupID = %s : itemkeys = %s", groupId, itemKeys);
    }
    if (DbComparator.notEqual (status, "started")) {
      resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_InsertItems",
          "Your test opportunity has been interrupted. Please check with your Test Administrator to resume your test.", null,
          oppKey, "T_InsertItems_2009", "denied"));
      return (new MultiDataResultSet (resultsSets));
    }
    final String SQL_QUERY2 = "select _efk_Segment as segmentKey, formKey, algorithm from testopportunitysegment where _fk_TestOpportunity = ${oppkey} and SegmentPosition = ${segment};";
    SqlParametersMaps parms2 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("segment", segment);
    result = executeStatement (connection, SQL_QUERY2, parms2, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      segmentKey = record.<String> get ("segmentKey");
      formKey = record.<String> get ("formKey");
      algorithm = record.<String> get ("algorithm");
    }

    if (segmentKey == null) {
      argstring = segment.toString ().trim ();
      msg = String.format ("Unknown test segment %s", argstring);
      _commonDll._LogDBError_SP (connection, "T_InsertItems", msg, null, null, null, oppKey, clientname, sessionKey);
      resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_InsertItems", "Unknown test segment", null, oppKey, null));
      return (new MultiDataResultSet (resultsSets));
    }
    _Ref<String> msgRef = new _Ref<> ();
    _ValidateItemInsert_SP (connection, oppKey, page, segment, segmentId, groupId, msgRef);
    if (msgRef.get () != null) {
      _commonDll._LogDBError_SP (connection, "T_InsertItems", msgRef.get (), null, null, null, oppKey, clientname, sessionKey);
      resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_InsertItems", "Database record insertion failed for new test items", null, oppKey, null));
      return (new MultiDataResultSet (resultsSets));
    }
    final String SQL_QUERY3 = "select AccCode as language from testeeaccommodations where _fk_TestOpportunity = ${oppkey} and AccType = ${Language};";
    SqlParametersMaps parms3 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("Language", "Language");
    result = executeStatement (connection, SQL_QUERY3, parms3, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      language = record.<String> get ("language");
    }
    Integer minpos = null;
    Integer maxpos = null;
    Integer insertcnt = null;
    Integer lastSegment = null;
    Integer lastpage = null;
    Integer lastpos = null;

    final String SQL_QUERY4 = "select max(position) as lastPosition from testeeresponse where _fk_TestOpportunity = ${oppkey} and _efk_ITSItem is not null;";
    SqlParametersMaps parms4 = parms1;
    result = executeStatement (connection, SQL_QUERY4, parms4, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      lastPosition = record.<Integer> get ("lastPosition");
    }
    final String SQL_QUERY5 = "select segment as lastSegment, page as lastPage from testeeresponse where _fk_TestOpportunity = ${oppkey} and position = ${lastPosition};";
    SqlParametersMaps parms5 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("lastPosition", lastPosition);
    result = executeStatement (connection, SQL_QUERY5, parms5, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      lastSegment = record.<Integer> get ("lastSegment");
      lastpage = record.<Integer> get ("lastPage");
    }

    // Get item data from the itembank, filtering by the items that were chosen
    // by the selection algorithm (some may have been excluded)
    DataBaseTable insertsTable = getDataBaseTable ("inserts").addColumn ("bankitemkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("relativePosition", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("formPosition", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("Position", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("answer", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 10)
        .addColumn ("b", SQL_TYPE_To_JAVA_TYPE.FLOAT).addColumn ("bankkey", SQL_TYPE_To_JAVA_TYPE.BIGINT).addColumn ("_efk_ITSItem", SQL_TYPE_To_JAVA_TYPE.BIGINT)
        .addColumn ("Scorepoint", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("contentLevel", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200).addColumn ("Format", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50)
        .addColumn ("IsFieldTest", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("IsRequired", SQL_TYPE_To_JAVA_TYPE.BIT);
    connection.createTemporaryTable (insertsTable);
    Map<String, String> unquotedParms2 = new HashMap<String, String> ();
    unquotedParms2.put ("insertsTableName", insertsTable.getTableName ());

    DataBaseTable TestItemgroupDataTable = ITEMBANK_TestItemGroupData_FN (connection, testkey, groupId, language, formKey);
    final String SQL_INSERT2 = "insert into ${insertsTableName} (bankitemkey, relativePosition, bankkey, _efk_ITSItem, b, Scorepoint,  format, isFieldTest, IsRequired, contentLevel, formPosition, answer)"
        + "select bankitemkey, itemposition, bankkey, itemkey, IRT_b, scorepoint, itemType, IsFieldTest, IsRequired, ContentLevel, FormPosition, answerKey from ${TestItemgroupDataTableName} order by itemposition;";
    Map<String, String> unquotedParms3 = new HashMap<String, String> ();
    unquotedParms3.put ("insertsTableName", insertsTable.getTableName ());
    unquotedParms3.put ("TestItemgroupDataTableName", TestItemgroupDataTable.getTableName ());
    executeStatement (connection, fixDataBaseNames (SQL_INSERT2, unquotedParms3), null, false).getUpdateCount ();
    // System.err.println (insertedCnt); // for testing

    if (itemKeys != null) {
      final String SQL_DELETE1 = "delete from  ${insertsTableName} where bankitemkey not in (select itemkey from ${itemsTableName});";
      Map<String, String> unquotedParms4 = new HashMap<String, String> ();
      unquotedParms4.put ("insertsTableName", insertsTable.getTableName ());
      unquotedParms4.put ("itemsTableName", itemsTable.getTableName ());
      executeStatement (connection, fixDataBaseNames (SQL_DELETE1, unquotedParms4), null, false).getUpdateCount ();
      // System.err.println (deletedCnt); // for testing
    }
    final String SQL_QUERY6 = "select  bankitemkey from ${insertsTableName} where formPosition is null limit 1";
    if (DbComparator.isEqual (algorithm, "fixedform") && (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY6, unquotedParms2), null, false)))) {
      // set @msg = 'Item(s) not on form: ' + @groupID + '; items: ' +
      // @itemkeys;
      msg = String.format ("Item(s) not on form: groupID = %s; items: = %s ", groupId, itemKeys);
      _commonDll._LogDBError_SP (connection, "T_InsertItems", msg, null, null, null, oppKey, clientname, sessionKey);
      resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_InsertItems", "Database record insertion failed for new test items", null, oppKey, null));
      return (new MultiDataResultSet (resultsSets));
    }
    final String SQL_QUERY7 = "select  bankitemkey from ${insertsTableName} limit 1";
    if (!exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY7, unquotedParms2), null, false))) {
      // set @msg = 'Item group does not exist: ' + @groupID + '; items: ' +
      // @itemkeys;
      msg = String.format ("Item group does not exist: groupID = %s; items: = %s ", groupId, itemKeys);
      _commonDll._LogDBError_SP (connection, "T_InsertItems", msg, null, null, null, oppKey, clientname, sessionKey);
      resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_InsertItems", "Database record insertion failed for new test items", null, oppKey, null));
      return (new MultiDataResultSet (resultsSets));
    }

    if (DbComparator.isEqual (algorithm, "fixedform")) {
      Integer formStart = null;
      final String SQL_QUERY8 = "select min(formPosition) as formStart from ${insertsTableName};";
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY8, unquotedParms2), null, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        formStart = record.<Integer> get ("formStart");
      }
      final String SQL_UPDATE1 = "update ${insertsTableName} set relativePosition = formPosition - ${formStart};";
      SqlParametersMaps parms6 = new SqlParametersMaps ().put ("formStart", formStart);
      executeStatement (connection, fixDataBaseNames (SQL_UPDATE1, unquotedParms2), parms6, false).getUpdateCount ();
      // System.err.println (updatedCnt); // for testing
    }
    final String SQL_QUERY9 = "select relativePosition from ${insertsTableName} group by relativePosition having count(*) > 1";
    final String SQL_QUERY10 = "select  bankitemkey from ${insertsTableName} where relativePosition is null limit 1";

    if ((exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY9, unquotedParms2), null, false)))
        || (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY10, unquotedParms2), null, false)))) {
      msg = String.format ("Ambiguous item positions in item group %s", groupId);
      _commonDll._LogDBError_SP (connection, "T_InsertItems", msg, null, null, null, oppKey, clientname, sessionKey);
      resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_InsertItems", "Database record insertion failed for new test items"));
      return (new MultiDataResultSet (resultsSets));
    }
    lastpos = lastPosition;
    if (lastpos == null)
      lastpos = 0;

    final String SQL_QUERY11 = "select  bankitemkey from ${insertsTableName} where position is null limit 1";
    while (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY11, unquotedParms2), null, false))) {
      final String SQL_QUERY12 = "select min(relativePosition) as minpos from ${insertsTableName} where position is null;";
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY12, unquotedParms2), null, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        minpos = record.<Integer> get ("minpos");
      }
      final String SQL_UPDATE2 = "update ${insertsTableName} set position = ${lastpos} + 1 where relativePosition = ${minpos};";
      SqlParametersMaps parms7 = new SqlParametersMaps ().put ("lastpos", lastpos).put ("minpos", minpos);
      executeStatement (connection, fixDataBaseNames (SQL_UPDATE2, unquotedParms2), parms7, false).getUpdateCount ();
      // System.err.println (updatedCnt); // for testing

      lastpos += 1;
    }
    final String SQL_QUERY13 = "select count(*) as count from ${insertsTableName};";
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY13, unquotedParms2), null, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      count = record.<Long> get ("count").intValue ();
    }
    if (debug == 1) {
      final String SQL_QUERY14 = "select * from ${insertsTableName} ;";
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY14, unquotedParms2), null, false).getResultSets ().next ();
      resultsSets.add (result);
    }
    final String SQL_QUERY15 = "select  bankitemkey from testeeresponse, ${insertsTableName} where _fk_TestOpportunity = ${oppkey} and _efk_Itemkey = bankitemkey limit 1";
    SqlParametersMaps parms8 = parms1;
    if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY15, unquotedParms2), parms8, false))) {
      msg = String.format ("Attempt to duplicate existing item: %s", itemKeys);
      _commonDll._LogDBError_SP (connection, "T_InsertItems", msg, null, null, null, oppKey, clientname, sessionKey);
      resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_InsertItems", "Database record insertion failed for new test items", null, oppKey, null));
      return (new MultiDataResultSet (resultsSets));
    }
    if (noinsert == true) {
      return (new MultiDataResultSet (resultsSets));
    }
    String errmsg = null;
    Integer itemcnt = null;
    try {
      boolean preexistingAutoCommitMode = connection.getAutoCommit ();
      connection.setAutoCommit (false);

      final String SQL_INSERT3 = "insert into testeeresponse (_fk_TestOpportunity, Position) select ${oppkey}, R.position from ${insertsTableName} R " +
          " where not exists (select * from testeeresponse where _fk_TestOpportunity = ${oppkey} and position = R.position);";
      SqlParametersMaps parms9 = parms1;
      executeStatement (connection, fixDataBaseNames (SQL_INSERT3, unquotedParms2), parms9, false).getUpdateCount ();

      // TODO Elena check this query!
      final String SQL_UPDATE3 = "Update testeeresponse T, ${insertsTableName} R  set T.isRequired = R.IsRequired, T._efk_ITSItem = R._efk_ITSItem, T._efk_ITSBank = R.bankkey, "
          + " T.response = null, T.OpportunityRestart = ${opprestart}, T.Page = ${page}, T.Answer = R.Answer, T.ScorePoint = R.ScorePoint, T.DateGenerated = ${today},"
          + " T._fk_Session = ${session}, T.Format = R.format, T.isFieldTest = R.isFieldTest, T.Hostname = ${hostname}, T.GroupID = ${groupID}, T.groupItemsRequired = ${groupItemsRequired},"
          + " T._efk_Itemkey = R.bankitemkey, T.segment = ${segment}, T.segmentID = ${segmentID}, T.groupB = ${groupB}, T.itemB = b   "
          + " where  _fk_TestOpportunity = ${oppkey} and T.position = R.position and T._efk_ITSItem is null "
          + " and not exists (select * from testeeresponse T where T._fk_TestOpportunity = ${oppkey} and (T.page = ${page} or (T._efk_ITSBank = R.bankkey and T._efk_ITSItem = R._efk_ITSItem)));";
      SqlParametersMaps parms10 = new SqlParametersMaps ();
      parms10.put ("oppkey", oppKey);
      parms10.put ("opprestart", opprestart);
      parms10.put ("page", page);
      parms10.put ("today", starttime);
      parms10.put ("session", sessionKey);
      parms10.put ("hostname", localhostname);
      parms10.put ("groupID", groupId);
      parms10.put ("groupItemsRequired", groupItemsRequired);
      parms10.put ("segment", segment);
      parms10.put ("segmentID", segmentId);
      parms10.put ("groupB", groupB);
      // TODO Elena Testing only till we fix mysql update
      // executeStatement (connection, fixDataBaseNames
      // (SQL_UPDATE3, unquotedParms2), parms10, false).getUpdateCount ();

      // check for successful insertion of ALL and ONLY the items in the group
      // given here
      final String SQL_QUERY16 = "select count(*) as itemcnt from testeeresponse where _fk_TestOpportunity = ${oppkey} and GroupID = ${groupID} and DateGenerated = ${today};";
      SqlParametersMaps parms11 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("groupID", groupId).put ("today", starttime);
      result = executeStatement (connection, SQL_QUERY16, parms11, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        itemcnt = record.<Long> get ("itemcnt").intValue ();
      }
      if (itemcnt != null && count != null && DbComparator.notEqual (itemcnt, count)) {
        connection.rollback ();
        connection.setAutoCommit (preexistingAutoCommitMode);
        errmsg = String.format ("Item insertion failed for group %s", groupId);
        _commonDll._LogDBError_SP (connection, "T_InsertItems", errmsg, null, null, null, oppKey, clientname, sessionKey);
        // TODO Elena Testing only!!!
        // resultsSets.add (_commonDll._ReturnError_SP (connection, clientname,
        // "T_InsertItems",
        // "Database record insertion failed for new test items", null, oppKey,
        // null));
        return (new MultiDataResultSet (resultsSets));
      }
      final String SQL_QUERY17 = "select  bankitemkey from ${insertsTableName} where isFIeldTest = 1 limit 1";
      if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY17, unquotedParms2), null, false))) {
        Integer minFTpos = null;
        final String SQL_QUERY18 = "select min(position) as minFTpos from ${insertsTableName}";
        result = executeStatement (connection, fixDataBaseNames (SQL_QUERY18, unquotedParms2), null, false).getResultSets ().next ();
        record = (result.getCount () > 0 ? result.getRecords ().next () : null);
        if (record != null) {
          minFTpos = record.<Integer> get ("minFTpos");
        }
        final String SQL_UPDATE4 = "update ft_opportunityitem set dateAdministered = ${now}, positionAdministered = ${minFTpos} where _fk_TestOpportunity = ${oppkey} " +
            "and segment = ${segment} and groupID = ${groupID};";
        SqlParametersMaps parms12 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("groupID", groupId).put ("now", starttime).put ("minFTpos", minFTpos)
            .put ("segment", segment);
        executeStatement (connection, SQL_UPDATE4, parms12, false).getUpdateCount ();
      }
      if (_AA_IsSegmentSatisfied_FN (connection, oppKey, segment) == true) {
        final String SQL_UPDATE5 = "update testopportunitysegment set IsSatisfied = 1 where _fk_TestOpportunity = ${oppkey} and segmentPosition = ${segment};";
        SqlParametersMaps parms13 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("segment", segment);
        executeStatement (connection, SQL_UPDATE5, parms13, false).getUpdateCount ();
      }
      connection.commit ();
      connection.setAutoCommit (preexistingAutoCommitMode);
    } catch (Exception e) {
      try {
        connection.rollback ();
      } catch (SQLException se) {
        _logger.error ("Failed rollback transaction");
      }
      errmsg = String.format ("Item insertion failed: %s", e.getMessage ());
      _commonDll._LogDBError_SP (connection, "T_InsertItems", errmsg, null, null, null, oppKey, clientname, sessionKey);

      resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_InsertItems", "Database record insertion failed for new test items", null, oppKey, null));
      return (new MultiDataResultSet (resultsSets));
    }
    try {
      final String SQL_QUERY19 = "select count(*) as itemcnt from testeeresponse where _fk_TestOpportunity = ${oppkey} and dateGenerated is not null;";
      SqlParametersMaps parms14 = parms1;
      result = executeStatement (connection, SQL_QUERY19, parms14, false).getResultSets ().next ();
      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        itemcnt = record.<Long> get ("itemcnt").intValue ();
      }
      final String SQL_UPDATE6 = "update testopportunity set inSegment = ${segment}, numitems = ${itemcnt} where _Key = ${oppkey};";
      SqlParametersMaps parms15 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("segment", segment).put ("itemcnt", itemcnt);
      executeStatement (connection, SQL_UPDATE6, parms15, false).getUpdateCount ();
      // System.err.println (updateCnt); // for testing

    } catch (ReturnStatusException re) {
      errmsg = re.getMessage ();
      _commonDll._LogDBError_SP (connection, "T_InsertItems", errmsg, null, null, null, oppKey, clientname, sessionKey);
    }
    String starttimeStr = new SimpleDateFormat (AbstractDateUtilDll.DB_DATETIME_FORMAT_MS_PRECISION).format (starttime);
    List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
    CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
    rcd.put ("status", "inserted");
    rcd.put ("number", count);
    rcd.put ("reason", null);
    rcd.put ("dateCreated", starttimeStr);
    resultList.add (rcd);

    SingleDataResultSet rs1 = new SingleDataResultSet ();
    rs1.addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs1.addColumn ("number", SQL_TYPE_To_JAVA_TYPE.INT);
    rs1.addColumn ("reason", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs1.addColumn ("dateCreated", SQL_TYPE_To_JAVA_TYPE.VARCHAR);

    rs1.addRecords (resultList);
    resultsSets.add (rs1);

    final String SQL_QUERY21 = "select bankitemkey, bankkey, _efk_ITSItem as itemkey, bigtoint(${page}) as page, position," +
        " format from ${insertsTableName} order by position;";
    SqlParametersMaps parms17 = new SqlParametersMaps ().put ("page", page);
    SingleDataResultSet rs2 = executeStatement (connection, fixDataBaseNames (SQL_QUERY21, unquotedParms2), parms17, false).getResultSets ().next ();
    resultsSets.add (rs2);

    connection.dropTemporaryTable (insertsTable);
    connection.dropTemporaryTable (itemsTable);
    connection.dropTemporaryTable (TestItemgroupDataTable);
    _commonDll._LogDBLatency_SP (connection, "T_InsertItems", starttime, null, true, page, oppKey, sessionKey, clientname, null);

    return (new MultiDataResultSet (resultsSets));
  }

  public MultiDataResultSet T_InsertItems_SP_Mysql (SQLConnection connection, UUID oppKey, UUID sessionKey, UUID browserId, Integer segment, 
      String segmentId, Integer page, String groupId, String itemKeys, Character delimiter, Integer groupItemsRequired, Float groupB,
      Integer debug, Boolean noinsert) throws ReturnStatusException {
    final String SQL_QUERY = "call T_InsertItems(${oppkey}, ${session}, ${browserID}, ${segment},${segmentId}, "
        + "${page},${groupId}, ${itemKeys},${delimiter}, ${groupItemsRequired}, ${groupB},${debug},${noinsert})";
    SqlParametersMaps params = new SqlParametersMaps ();
    params.put ("oppkey", oppKey);
    params.put ("session", sessionKey);
    params.put ("browserID", browserId);
    params.put ("segment", segment);
    params.put ("segmentId", segmentId);
    params.put ("page", page);
    params.put ("groupId", groupId);
    params.put ("itemKeys", itemKeys);
    params.put ("delimiter", String.valueOf (delimiter));
    params.put ("groupItemsRequired", groupItemsRequired);
    params.put ("groupB", groupB);
    params.put ("debug", debug);
    params.put ("noinsert", noinsert);

    MultiDataResultSet resultSet = executeStatement (connection, SQL_QUERY, params, false);
    return resultSet;
    
    
  }
  /**
   * This is the Java version of T_InsertItems stored procedure. This method is not used now. It is only needed when we want to migrate the Mysql stored procedure T_InsertItems  to java.
   */
//Optimization attempt
 public MultiDataResultSet T_InsertItems_SP (SQLConnection connection, UUID oppKey, UUID sessionKey, UUID browserId, Integer segment, String segmentId, Integer page, String groupId,
     String itemKeys, Character delimiter, Integer groupItemsRequired, Float groupB, Integer debug, Boolean noinsert) throws ReturnStatusException {
   
   List<SingleDataResultSet> resultsSets = new ArrayList<SingleDataResultSet> ();
   Date starttime = _dateUtil.getDateWRetStatus (connection);
   String localhostname = _commonDll.getLocalhostName ();
   _Ref<String> error = new _Ref<> ();
   Long testee = null;

   _ValidateTesteeAccessProc_SP (connection, oppKey, sessionKey, browserId, false, error);
   if (error.get () != null) {
     resultsSets.add (_commonDll._ReturnError_SP (connection, null, "T_InsertItems", error.get (), null, oppKey, "_ValidateTesteeAccesss", "denied"));
     return (new MultiDataResultSet (resultsSets));
   }

   Integer count = null;
   Integer opprestart = null;
   String status = null;
   String clientname = null;
   String environment = null;
   String language = null;
   String item = null;
   String testkey = null;
   Integer lastPosition = null;
   String msg = null;
   String segmentKey = null;
   String formKey = null;
   String algorithm = null;
   String argstring = null;

   DataBaseTable itemsTable = getDataBaseTable ("items").addColumn ("p", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("itemkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50);
   connection.createTemporaryTable (itemsTable);
   Map<String, String> unquotedParms = new HashMap<String, String> ();
   unquotedParms.put ("itemsTableName", itemsTable.getTableName ());

   if (itemKeys != null) {
     DataBaseTable buildTable = _commonDll._BuildTable_FN (connection, "_BuildTable", itemKeys, delimiter.toString ());
     final String SQL_INSERT1 = "insert into ${itemsTableName} (p, itemkey) select idx, record from ${temporaryTableName};";
     Map<String, String> unquotedParms1 = new HashMap<String, String> ();
     unquotedParms1.put ("itemsTableName", itemsTable.getTableName ());
     unquotedParms1.put ("temporaryTableName", buildTable.getTableName ());
     executeStatement (connection, fixDataBaseNames (SQL_INSERT1, unquotedParms1), null, false).getUpdateCount ();
     // System.err.println (insertedCnt); // for testing
     connection.dropTemporaryTable (buildTable);
   }
   final String SQL_QUERY1 = "select clientname, _efk_AdminSUbject as testkey, Restart as opprestart, status, environment, _efk_Testee as testee  from testopportunity where _key = ${oppkey};";
   SqlParametersMaps parms1 = new SqlParametersMaps ().put ("oppkey", oppKey);
   SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
   DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
   if (record != null) {
     clientname = record.<String> get ("clientname");
     testkey = record.<String> get ("testkey");
     opprestart = record.<Integer> get ("opprestart");
     status = record.<String> get ("status");
     environment = record.<String> get ("environment");
     testee = record.<Long> get ("testee");
   }

   if (DbComparator.notEqual (environment, "production")) {
     // / set @trace = 'TRACING ' + @groupID + ':' + @itemkeys;
     String trace = String.format ("TRACING groupID = %s : itemkeys = %s", groupId, itemKeys);
   }
   if (DbComparator.notEqual (status, "started")) {
     resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_InsertItems",
         "Your test opportunity has been interrupted. Please check with your Test Administrator to resume your test.", null,
         oppKey, "T_InsertItems_2009", "denied"));
     return (new MultiDataResultSet (resultsSets));
   }
   final String SQL_QUERY2 = "select _efk_Segment as segmentKey, formKey, algorithm from testopportunitysegment where _fk_TestOpportunity = ${oppkey} and SegmentPosition = ${segment};";
   SqlParametersMaps parms2 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("segment", segment);
   result = executeStatement (connection, SQL_QUERY2, parms2, false).getResultSets ().next ();
   record = (result.getCount () > 0 ? result.getRecords ().next () : null);
   if (record != null) {
     segmentKey = record.<String> get ("segmentKey");
     formKey = record.<String> get ("formKey");
     algorithm = record.<String> get ("algorithm");
   }

   if (segmentKey == null) {
     argstring = segment.toString ().trim ();
     msg = String.format ("Unknown test segment %s", argstring);
     _commonDll._LogDBError_SP (connection, "T_InsertItems", msg, null, null, null, oppKey, clientname, sessionKey);
     resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_InsertItems", "Unknown test segment", null, oppKey, null));
     return (new MultiDataResultSet (resultsSets));
   }
   _Ref<String> msgRef = new _Ref<> ();
   _ValidateItemInsert_SP (connection, oppKey, page, segment, segmentId, groupId, msgRef);
   if (msgRef.get () != null) {
     _commonDll._LogDBError_SP (connection, "T_InsertItems", msgRef.get (), null, null, null, oppKey, clientname, sessionKey);
     resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_InsertItems", "Database record insertion failed for new test items: ", msgRef.get (), oppKey, null));
     if (_debug)
     {
       _logger.debug ("_ValidateItemInsert_SP: oppKey = " + oppKey);
       _logger.debug ("Message: " + msgRef.get ());
     }
     return (new MultiDataResultSet (resultsSets));
   }
   final String SQL_QUERY3 = "select AccCode as language from testeeaccommodations where _fk_TestOpportunity = ${oppkey} and AccType = ${Language};";
   SqlParametersMaps parms3 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("Language", "Language");
   result = executeStatement (connection, SQL_QUERY3, parms3, false).getResultSets ().next ();
   record = (result.getCount () > 0 ? result.getRecords ().next () : null);
   if (record != null) {
     language = record.<String> get ("language");
   }
   Integer minpos = null;
   Integer maxpos = null;
   Integer insertcnt = null;
   Integer lastSegment = null;
   Integer lastpage = null;
   Integer lastpos = null;

   /*final String SQL_QUERY4 = "select max(position) as lastPosition from testeeresponse where _fk_TestOpportunity = ${oppkey} and _efk_ITSItem is not null;";
   SqlParametersMaps parms4 = parms1;
   result = executeStatement (connection, SQL_QUERY4, parms4, false).getResultSets ().next ();
   record = (result.getCount () > 0 ? result.getRecords ().next () : null);
   if (record != null) {
     lastPosition = record.<Integer> get ("lastPosition");
   }*/
//   final String SQL_QUERY5 = "select segment as lastSegment, page as lastPage from testeeresponse where _fk_TestOpportunity = ${oppkey} and position = ${lastPosition};";
   final String SQL_QUERY5 = "select segment as lastSegment, page as lastPage, position as lastPosition from testeeresponse where _fk_TestOpportunity = ${oppkey} and position = (select max(position) as lastPosition from testeeresponse where _fk_TestOpportunity = ${oppkey} and _efk_ITSItem is not null);";
   SqlParametersMaps parms5 = new SqlParametersMaps ().put ("oppkey", oppKey)/*.put ("lastPosition", lastPosition)*/;
   result = executeStatement (connection, SQL_QUERY5, parms5, false).getResultSets ().next ();
   record = (result.getCount () > 0 ? result.getRecords ().next () : null);
   if (record != null) {
     lastSegment = record.<Integer> get ("lastSegment");
     lastpage = record.<Integer> get ("lastPage");
     lastPosition = record.<Integer> get ("lastPosition");
   }
   
   // Get item data from the itembank, filtering by the items that were chosen
   // by the selection algorithm (some may have been excluded)
   DataBaseTable insertsTable = getDataBaseTable ("inserts").addColumn ("bankitemkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("relativePosition", SQL_TYPE_To_JAVA_TYPE.INT)
       .addColumn ("formPosition", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("Position", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("answer", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 10)
       .addColumn ("b", SQL_TYPE_To_JAVA_TYPE.FLOAT).addColumn ("bankkey", SQL_TYPE_To_JAVA_TYPE.BIGINT).addColumn ("_efk_ITSItem", SQL_TYPE_To_JAVA_TYPE.BIGINT)
       .addColumn ("Scorepoint", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("contentLevel", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200).addColumn ("Format", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50)
       .addColumn ("IsFieldTest", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("IsRequired", SQL_TYPE_To_JAVA_TYPE.BIT);
   connection.createTemporaryTable (insertsTable);
   // DataBaseTable TestItemgroupDataTable = ITEMBANK_TestItemGroupData_FN
   // (connection, testkey, groupId, language, formKey);
   // final String SQL_INSERT2 =
   // "insert into ${insertsTableName} (bankitemkey, relativePosition, bankkey, _efk_ITSItem, b, Scorepoint,  format, isFieldTest, IsRequired, contentLevel, formPosition, answer)"
   // +
   // "select bankitemkey, itemposition, bankkey, itemkey, IRT_b, scorepoint, itemType, IsFieldTest, IsRequired, ContentLevel, FormPosition, answerKey from ${TestItemgroupDataTableName} order by itemposition;";

   final String SQL_INSERT2 = "insert into ${insertsTableName} (bankitemkey, relativePosition, bankkey, _efk_ITSItem, b, Scorepoint,  format, isFieldTest, IsRequired, contentLevel, formPosition, answer)"
       + " select  A._fk_Item as bankitemkey, ItemPosition, _efk_ItemBank as bankkey, _efk_Item as itemkey,  IRT_b,"
       + " ScorePoint, ItemType, IsFieldTest, IsRequired, _fk_Strand as ContentLevel, "
       + " (select FormPosition from ${ItemBankDB}.testformitem F where F._fk_Item = A._fk_Item and _fk_TestForm = ${formkey} and F._fk_AdminSubject = ${segmentKey}) as FormPosition, "
       + " Answer as answerKey "
       + " from ${ItemBankDB}.tblsetofadminitems A, ${ItemBankDB}.tblitem I, ${ItemBankDB}.tblitemprops P where A._fk_AdminSubject = ${segmentKey} and A.groupID = ${groupID} and A._fk_ITem = I._Key"
       + " and P._fk_AdminSubject = ${segmentKey} and P._fk_Item = A._fk_Item and P.Propname = 'Language' and P.Propvalue = ${language} "
       + " order by itemposition";

   // + " groupKey, GroupID,  P.IsActive, BlockID,     "
   // + "  ContentSize,   "
   // + " strandName, "
   // +
   // " (select concat(C.Homepath, B.HomePath, B.ItemPath, I.FilePath, I.FileName) "
   // +
   // " from ${ItemBankDB}.tblitembank B, ${ItemBankDB}.tblclient C, ${ItemBankDB}.tblitem I"
   // +
   // " where B._efk_Itembank = bankkey and B._fk_Client = C._Key and I._Key = concat(bankkey, '-', itemkey) limit 1) as itemFile "

   Map<String, String> unquotedParms3 = new HashMap<String, String> ();
   unquotedParms3.put ("insertsTableName", insertsTable.getTableName ());

   SqlParametersMaps params = (new SqlParametersMaps ()).put ("formkey", formKey).put ("testkey", testkey).put ("groupid", groupId).put ("language", language).put ("segmentKey", segmentKey);
   String query = fixDataBaseNames (SQL_INSERT2); // to substitute
                                                  // ${ItemBankDB}
   executeStatement (connection, fixDataBaseNames (query, unquotedParms3), params, false).getUpdateCount ();
   if (itemKeys != null) {
     final String SQL_DELETE1 = "delete from  ${insertsTableName} where bankitemkey not in (select itemkey from ${itemsTableName});";
     Map<String, String> unquotedParms4 = new HashMap<String, String> ();
     unquotedParms4.put ("insertsTableName", insertsTable.getTableName ());
     unquotedParms4.put ("itemsTableName", itemsTable.getTableName ());
     executeStatement (connection, fixDataBaseNames (SQL_DELETE1, unquotedParms4), null, false).getUpdateCount ();
     // System.err.println (deletedCnt); // for testing
   }
   final String SQL_QUERY6 = "select  bankitemkey from ${insertsTableName} where formPosition is null limit 1";
   if (DbComparator.isEqual (algorithm, "fixedform") && (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY6, unquotedParms3), null, false)))) {
     // set @msg = 'Item(s) not on form: ' + @groupID + '; items: ' +
     // @itemkeys;
     msg = String.format ("Item(s) not on form: groupID = %s; items: = %s ", groupId, itemKeys);
     _commonDll._LogDBError_SP (connection, "T_InsertItems", msg, null, null, null, oppKey, clientname, sessionKey);
     resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_InsertItems", "Database record insertion failed for new test items", null, oppKey, null));
     return (new MultiDataResultSet (resultsSets));
   }
   final String SQL_QUERY7 = "select  bankitemkey from ${insertsTableName} limit 1";
   if (!exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY7, unquotedParms3), null, false))) {
     // set @msg = 'Item group does not exist: ' + @groupID + '; items: ' +
     // @itemkeys;
     msg = String.format ("Item group does not exist: groupID = %s; items: = %s ", groupId, itemKeys);
     _commonDll._LogDBError_SP (connection, "T_InsertItems", msg, null, null, null, oppKey, clientname, sessionKey);
     resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_InsertItems", "Database record insertion failed for new test items", null, oppKey, null));
     return (new MultiDataResultSet (resultsSets));
   }

   if (DbComparator.isEqual (algorithm, "fixedform")) {
     Integer formStart = null;
     final String SQL_QUERY8 = "select min(formPosition) as formStart from ${insertsTableName};";
     result = executeStatement (connection, fixDataBaseNames (SQL_QUERY8, unquotedParms3), null, false).getResultSets ().next ();
     record = (result.getCount () > 0 ? result.getRecords ().next () : null);
     if (record != null) {
       formStart = record.<Integer> get ("formStart");
     }
     final String SQL_UPDATE1 = "update ${insertsTableName} set relativePosition = formPosition - ${formStart};";
     SqlParametersMaps parms6 = new SqlParametersMaps ().put ("formStart", formStart);
     executeStatement (connection, fixDataBaseNames (SQL_UPDATE1, unquotedParms3), parms6, false).getUpdateCount ();
     // System.err.println (updatedCnt); // for testing
   }
   final String SQL_QUERY9 = "select relativePosition from ${insertsTableName} group by relativePosition having count(*) > 1";
   final String SQL_QUERY10 = "select  bankitemkey from ${insertsTableName} where relativePosition is null limit 1";

   if ((exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY9, unquotedParms3), null, false)))
       || (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY10, unquotedParms3), null, false)))) {
     msg = String.format ("Ambiguous item positions in item group %s", groupId);
     _commonDll._LogDBError_SP (connection, "T_InsertItems", msg, null, null, null, oppKey, clientname, sessionKey);
     resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_InsertItems", "Database record insertion failed for new test items"));
     return (new MultiDataResultSet (resultsSets));
   }
   lastpos = lastPosition;
   if (lastpos == null)
     lastpos = 0;

   final String SQL_QUERY11 = "select  bankitemkey from ${insertsTableName} where position is null limit 1";
   while (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY11, unquotedParms3), null, false))) {
     final String SQL_QUERY12 = "select min(relativePosition) as minpos from ${insertsTableName} where position is null;";
     result = executeStatement (connection, fixDataBaseNames (SQL_QUERY12, unquotedParms3), null, false).getResultSets ().next ();
     record = (result.getCount () > 0 ? result.getRecords ().next () : null);
     if (record != null) {
       minpos = record.<Integer> get ("minpos");
     }
     final String SQL_UPDATE2 = "update ${insertsTableName} set position = ${lastpos} + 1 where relativePosition = ${minpos};";
     SqlParametersMaps parms7 = new SqlParametersMaps ().put ("lastpos", lastpos).put ("minpos", minpos);
     executeStatement (connection, fixDataBaseNames (SQL_UPDATE2, unquotedParms3), parms7, false).getUpdateCount ();
     // System.err.println (updatedCnt); // for testing

     lastpos += 1;
   }
   final String SQL_QUERY13 = "select count(*) as count from ${insertsTableName};";
   result = executeStatement (connection, fixDataBaseNames (SQL_QUERY13, unquotedParms3), null, false).getResultSets ().next ();
   record = (result.getCount () > 0 ? result.getRecords ().next () : null);
   if (record != null) {
     count = record.<Long> get ("count").intValue ();
   }
   if (debug == 1) {
     final String SQL_QUERY14 = "select * from ${insertsTableName} ;";
     result = executeStatement (connection, fixDataBaseNames (SQL_QUERY14, unquotedParms3), null, false).getResultSets ().next ();
     resultsSets.add (result);
   }
   final String SQL_QUERY15 = "select  bankitemkey from testeeresponse, ${insertsTableName} where _fk_TestOpportunity = ${oppkey} and _efk_Itemkey = bankitemkey limit 1";
   SqlParametersMaps parms8 = parms1;
   if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY15, unquotedParms3), parms8, false))) {
     msg = String.format ("Attempt to duplicate existing item: %s", itemKeys);
     _commonDll._LogDBError_SP (connection, "T_InsertItems", msg, null, null, null, oppKey, clientname, sessionKey);
     resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_InsertItems", "Database record insertion failed for new test items", null, oppKey, null));
     return (new MultiDataResultSet (resultsSets));
   }
   if (noinsert == true) {
     return (new MultiDataResultSet (resultsSets));
   }
   String errmsg = null;
   Integer itemcnt = null;
   try {
     boolean preexistingAutoCommitMode = connection.getAutoCommit ();
     connection.setAutoCommit (false);

     final String SQL_INSERT3 = "insert into testeeresponse (_fk_TestOpportunity, Position) select ${oppkey}, R.position from ${insertsTableName} R " +
         " where not exists (select * from testeeresponse where _fk_TestOpportunity = ${oppkey} and position = R.position);";
     SqlParametersMaps parms9 = parms1;
     executeStatement (connection, fixDataBaseNames (SQL_INSERT3, unquotedParms3), parms9, false).getUpdateCount ();
     // TODO Elena: this is temporary solution while Sai further researches
     // mysql capabilities around this and
     // see if it is even possible to this and the next statement as 1
     // statement call
     final String SQL_EXISTS1 = "select page from testeeresponse T,  ${insertsTableName} R where T._fk_TestOpportunity = ${oppkey} and "
         + " (T.page = ${page} or (T._efk_ITSBank = R.bankkey and T._efk_ITSItem = R._efk_ITSItem))";
     SqlParametersMaps prm = (new SqlParametersMaps ()).put ("oppkey", oppKey).put ("page", page);
     if (exists (executeStatement (connection, fixDataBaseNames (SQL_EXISTS1, unquotedParms3), prm, false)) == false) {
       final String SQL_UPDATE3 = "Update testeeresponse T, ${insertsTableName} R  set T.isRequired = R.IsRequired, T._efk_ITSItem = R._efk_ITSItem, T._efk_ITSBank = R.bankkey, "
           + " T.response = null, T.OpportunityRestart = ${opprestart}, T.Page = ${page}, T.Answer = R.Answer, T.ScorePoint = R.ScorePoint, T.DateGenerated = ${today},"
           + " T._fk_Session = ${session}, T.Format = R.format, T.isFieldTest = R.isFieldTest, T.Hostname = ${hostname}, T.GroupID = ${groupID}, T.groupItemsRequired = ${groupItemsRequired},"
           + " T._efk_Itemkey = R.bankitemkey, T.segment = ${segment}, T.segmentID = ${segmentID}, T.groupB = ${groupB}, T.itemB = b   "
           + " where  _fk_TestOpportunity = ${oppkey} and T.position = R.position and T._efk_ITSItem is null ";
       // +
       // " and not exists (select * from testeeresponse T where T._fk_TestOpportunity = ${oppkey} and (T.page = ${page} or (T._efk_ITSBank = R.bankkey and T._efk_ITSItem = R._efk_ITSItem)));";
       SqlParametersMaps parms10 = new SqlParametersMaps ();
       parms10.put ("oppkey", oppKey);
       parms10.put ("opprestart", opprestart);
       parms10.put ("page", page);
       parms10.put ("today", starttime);
       parms10.put ("session", sessionKey);
       parms10.put ("hostname", localhostname);
       parms10.put ("groupID", groupId);
       parms10.put ("groupItemsRequired", groupItemsRequired);
       parms10.put ("segment", segment);
       parms10.put ("segmentID", segmentId);
       parms10.put ("groupB", groupB);

       executeStatement (connection, fixDataBaseNames (SQL_UPDATE3, unquotedParms3), parms10, false).getUpdateCount ();
     }
     // check for successful insertion of ALL and ONLY the items in the group
     // given here
     final String SQL_QUERY16 = "select count(*) as itemcnt from testeeresponse where _fk_TestOpportunity = ${oppkey} and GroupID = ${groupID} and DateGenerated = ${today};";
     SqlParametersMaps parms11 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("groupID", groupId).put ("today", starttime);
     result = executeStatement (connection, SQL_QUERY16, parms11, false).getResultSets ().next ();
     record = (result.getCount () > 0 ? result.getRecords ().next () : null);
     if (record != null) {
       itemcnt = record.<Long> get ("itemcnt").intValue ();
     }
     if (itemcnt != null && count != null && DbComparator.notEqual (itemcnt, count)) {
       connection.rollback ();
       connection.setAutoCommit (preexistingAutoCommitMode);
       errmsg = String.format ("Item insertion failed for group %s", groupId);
       _commonDll._LogDBError_SP (connection, "T_InsertItems", errmsg, null, null, null, oppKey, clientname, sessionKey);
       resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_InsertItems", "Database record insertion failed for new test items", null, oppKey, null));
       return (new MultiDataResultSet (resultsSets));
     }
     final String SQL_QUERY17 = "select  bankitemkey from ${insertsTableName} where isFIeldTest = 1 limit 1";
     if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY17, unquotedParms3), null, false))) {
       Integer minFTpos = null;
       final String SQL_QUERY18 = "select min(position) as minFTpos from ${insertsTableName}";
       result = executeStatement (connection, fixDataBaseNames (SQL_QUERY18, unquotedParms3), null, false).getResultSets ().next ();
       record = (result.getCount () > 0 ? result.getRecords ().next () : null);
       if (record != null) {
         minFTpos = record.<Integer> get ("minFTpos");
       }
       final String SQL_UPDATE4 = "update ft_opportunityitem set dateAdministered = ${now}, positionAdministered = ${minFTpos} where _fk_TestOpportunity = ${oppkey} " +
           "and segment = ${segment} and groupID = ${groupID};";
       SqlParametersMaps parms12 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("groupID", groupId).put ("now", starttime).put ("minFTpos", minFTpos)
           .put ("segment", segment);
       executeStatement (connection, SQL_UPDATE4, parms12, false).getUpdateCount ();
     }
     if (_AA_IsSegmentSatisfied_FN (connection, oppKey, segment) == true) {
       final String SQL_UPDATE5 = "update testopportunitysegment set IsSatisfied = 1 where _fk_TestOpportunity = ${oppkey} and segmentPosition = ${segment};";
       SqlParametersMaps parms13 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("segment", segment);
       executeStatement (connection, SQL_UPDATE5, parms13, false).getUpdateCount ();
     }
     connection.commit ();
     connection.setAutoCommit (preexistingAutoCommitMode);
   } catch (Exception e) {
     try {
       connection.rollback ();
     } catch (SQLException se) {
       _logger.error ("Failed rollback transaction");
     }
     errmsg = String.format ("Item insertion failed: %s", e.getMessage ());
     _commonDll._LogDBError_SP (connection, "T_InsertItems", errmsg, null, null, null, oppKey, clientname, sessionKey);
     resultsSets.add (_commonDll._ReturnError_SP (connection, clientname, "T_InsertItems", "Database record insertion failed for new test items", null, oppKey, null));
     return (new MultiDataResultSet (resultsSets));
   }
   try {
     final String SQL_QUERY19 = "select count(*) as itemcnt from testeeresponse where _fk_TestOpportunity = ${oppkey} and dateGenerated is not null;";
     SqlParametersMaps parms14 = parms1;
     result = executeStatement (connection, SQL_QUERY19, parms14, false).getResultSets ().next ();
     record = (result.getCount () > 0 ? result.getRecords ().next () : null);
     if (record != null) {
       itemcnt = record.<Long> get ("itemcnt").intValue ();
     }
     final String SQL_UPDATE6 = "update testopportunity set inSegment = ${segment}, numitems = ${itemcnt} where _Key = ${oppkey};";
     SqlParametersMaps parms15 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("segment", segment).put ("itemcnt", itemcnt);
     executeStatement (connection, SQL_UPDATE6, parms15, false).getUpdateCount ();
     // System.err.println (updateCnt); // for testing

   } catch (ReturnStatusException re) {
     errmsg = re.getMessage ();
     _commonDll._LogDBError_SP (connection, "T_InsertItems", errmsg, null, null, null, oppKey, clientname, sessionKey);
   }
   String starttimeStr = new SimpleDateFormat (AbstractDateUtilDll.DB_DATETIME_FORMAT_MS_PRECISION).format (starttime);
   List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
   CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
   rcd.put ("status", "inserted");
   rcd.put ("number", count);
   rcd.put ("reason", null);
   rcd.put ("dateCreated", starttimeStr);
   resultList.add (rcd);

   SingleDataResultSet rs1 = new SingleDataResultSet ();
   rs1.addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
   rs1.addColumn ("number", SQL_TYPE_To_JAVA_TYPE.INT);
   rs1.addColumn ("reason", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
   rs1.addColumn ("dateCreated", SQL_TYPE_To_JAVA_TYPE.VARCHAR);

   rs1.addRecords (resultList);
   resultsSets.add (rs1);

   final String SQL_QUERY21 = "select bankitemkey, bankkey, _efk_ITSItem as itemkey, bigtoint(${page}) as page, position," +
       " format from ${insertsTableName} order by position;";
   SqlParametersMaps parms17 = new SqlParametersMaps ().put ("page", page);
   SingleDataResultSet rs2 = executeStatement (connection, fixDataBaseNames (SQL_QUERY21, unquotedParms3), parms17, false).getResultSets ().next ();
   resultsSets.add (rs2);

   connection.dropTemporaryTable (insertsTable);
   connection.dropTemporaryTable (itemsTable);
   _commonDll._LogDBLatency_SP (connection, "T_InsertItems", starttime, null, true, page, oppKey, sessionKey, clientname, null);
   return (new MultiDataResultSet (resultsSets));
 }

  /**
   * @param connection
   * @param testKey
   * @param groupId
   * @param language
   * @param formkey
   * @return
   * @throws ReturnStatusException
   */
  // Original port
  public DataBaseTable ITEMBANK_TestItemGroupData_FN (SQLConnection connection, String testKey, String groupId, String language, String formkey) throws ReturnStatusException {

    DataBaseTable testItemTable = getDataBaseTable ("TestItemGroupData").addColumn ("groupKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("GroupID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100)
        .addColumn ("ItemPosition", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("IsFieldTest", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("IsActive", SQL_TYPE_To_JAVA_TYPE.BIT)
        .addColumn ("BlockID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 10).addColumn ("IsRequired", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("ContentLevel", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150)
        .addColumn ("ItemType", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("answerKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("ScorePoint", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("ContentSize", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("bankkey", SQL_TYPE_To_JAVA_TYPE.BIGINT).addColumn ("itemkey", SQL_TYPE_To_JAVA_TYPE.BIGINT)
        .addColumn ("bankitemkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150).addColumn ("itemFile", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150).addColumn ("IRT_b", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150)
        .addColumn ("FormPosition", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("strandName", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150);
    connection.createTemporaryTable (testItemTable);

    final String SQL_INSERT = "select groupKey, GroupID, ItemPosition, IsFieldTest, P.IsActive, BlockID, IsRequired, _fk_Strand as ContentLevel, ItemType, Answer as answerKey, "
        + " ScorePoint, ContentSize, _efk_ItemBank as bankkey, _efk_Item as itemkey, A._fk_Item as bankitemkey, IRT_b, "
        + " (select FormPosition from ${ItemBankDB}.testformitem F where F._fk_Item = A._fk_Item and _fk_TestForm = ${formkey} and F._fk_AdminSubject = ${testKey}) as FormPosition, "
        + " strandName from ${ItemBankDB}.tblsetofadminitems A, ${ItemBankDB}.tblitem I, ${ItemBankDB}.tblitemprops P where A._fk_AdminSubject = ${testkey} and A.groupID = ${groupID} and A._fk_ITem = I._Key"
        + " and P._fk_AdminSubject = ${testKey} and P._fk_Item = A._fk_Item and P.Propname = 'Language' and P.Propvalue = ${language}";
    String finalQuery = fixDataBaseNames (SQL_INSERT);
    SqlParametersMaps parms = new SqlParametersMaps ().put ("formkey", formkey).put ("testkey", testKey).put ("groupID", groupId).put ("language", language);
    SingleDataResultSet result = executeStatement (connection, finalQuery, parms, false).getResultSets ().next ();
    result.addColumn ("itemFile", SQL_TYPE_To_JAVA_TYPE.VARCHAR);

    Iterator<DbResultRecord> records = result.getRecords ();
    while (records.hasNext ()) {
      DbResultRecord record = records.next ();
      Long bankkey = record.<Long> get ("bankkey");
      Long itemkey = record.<Long> get ("itemkey");
      String itemFile = _commonDll.ITEMBANK_ItemFile_FN (connection, bankkey, itemkey);
      record.addColumnValue ("itemFile", itemFile);
    }
    if (result.getCount () > 0) {
      insertBatch (connection, testItemTable.generateInsertStatement (), result, null);
    }
    return testItemTable;
  }

  // Attempt to optimize
  // TODO need to reconsider because we path building inlined here
  // needs to be adjusted to make sure
  // java.io.File.separatorChar is used as directory separator

  public DataBaseTable ITEMBANK_TestItemGroupData_FNOpt (SQLConnection connection, String testKey, String groupId, String language, String formkey) throws ReturnStatusException {
    // select bankitemkey, itemposition, bankkey, itemkey, IRT_b, scorepoint,
    // itemType, IsFieldTest, IsRequired, ContentLevel, FormPosition, answerKey
    DataBaseTable testItemTable = getDataBaseTable ("TestItemGroupData").addColumn ("groupKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("GroupID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100)
        .addColumn ("ItemPosition", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("IsFieldTest", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("IsActive", SQL_TYPE_To_JAVA_TYPE.BIT)
        .addColumn ("BlockID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 10).addColumn ("IsRequired", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("ContentLevel", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150)
        .addColumn ("ItemType", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("answerKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("ScorePoint", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("ContentSize", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("bankkey", SQL_TYPE_To_JAVA_TYPE.BIGINT).addColumn ("itemkey", SQL_TYPE_To_JAVA_TYPE.BIGINT)
        .addColumn ("bankitemkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150).addColumn ("itemFile", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150).addColumn ("IRT_b", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150)
        .addColumn ("FormPosition", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("strandName", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150);
    connection.createTemporaryTable (testItemTable);

    // select concat(C.Homepath, B.HomePath, B.ItemPath, I.FilePath, I.FileName)
    // as path from ${ItemBankDB}.tblitembank B, ${ItemBankDB}.tblclient C,
    // ${ItemBankDB}.tblitem I" +
    // " where B._efk_Itembank = ${bankkey} and B._fk_Client = C._Key and I._Key
    // = concat(${bankkey}, '-', ${itemkey})
    final String SQL_INSERT = "select groupKey, GroupID, ItemPosition, IsFieldTest, P.IsActive, BlockID, IsRequired, _fk_Strand as ContentLevel, ItemType, Answer as answerKey, "
        + " ScorePoint, ContentSize, _efk_ItemBank as bankkey, _efk_Item as itemkey, A._fk_Item as bankitemkey, IRT_b, "
        + " (select FormPosition from ${ItemBankDB}.testformitem F where F._fk_Item = A._fk_Item and _fk_TestForm = ${formkey} and F._fk_AdminSubject = ${testKey}) as FormPosition, "
        + " strandName, "
        + " (select concat(C.Homepath, B.HomePath, B.ItemPath, I.FilePath, I.FileName) "
        + "   from ${ItemBankDB}.tblitembank B, ${ItemBankDB}.tblclient C, ${ItemBankDB}.tblitem I"
        + "   where B._efk_Itembank = bankkey and B._fk_Client = C._Key and I._Key = concat(bankkey, '-', itemkey) limit 1) as itemFile "
        + " from ${ItemBankDB}.tblsetofadminitems A, ${ItemBankDB}.tblitem I, ${ItemBankDB}.tblitemprops P where A._fk_AdminSubject = ${testkey} and A.groupID = ${groupID} and A._fk_ITem = I._Key"
        + " and P._fk_AdminSubject = ${testKey} and P._fk_Item = A._fk_Item and P.Propname = 'Language' and P.Propvalue = ${language}";
    String finalQuery = fixDataBaseNames (SQL_INSERT);
    SqlParametersMaps parms = new SqlParametersMaps ().put ("formkey", formkey).put ("testkey", testKey).put ("groupID", groupId).put ("language", language);
    SingleDataResultSet result = executeStatement (connection, finalQuery, parms, false).getResultSets ().next ();
    // result.addColumn ("itemFile", SQL_TYPE_To_JAVA_TYPE.VARCHAR);

    // Iterator<DbResultRecord> records = result.getRecords ();
    // while (records.hasNext ()) {
    // DbResultRecord record = records.next ();
    // Long bankkey = record.<Long> get ("bankkey");
    // Long itemkey = record.<Long> get ("itemkey");
    // String itemFile = ITEMBANK_ItemFile_FN (connection, bankkey, itemkey);
    // record.addColumnValue ("itemFile", itemFile);
    // }
    if (result.getCount () > 0) {
      insertBatch (connection, testItemTable.generateInsertStatement (), result, null);
    }
    return testItemTable;
  }

  public String SIM_MakeItemscoreString_FN (SQLConnection connection, UUID oppkey) throws ReturnStatusException {

    return SIM_MakeItemscoreString_FN (connection, oppkey, ';', ',');
  }

  /**
   * -- This function creates an item string suitable for submission to either
   * the adaptive algorithm or to the scoring algorithm -- These strings are
   * stored with all completed, invalidated, and reset test opportunities in the
   * 'itemstring' field. -- Since the text of the response is not part of this
   * computation, there is no concern over the use of special characters in --
   * the delimiter set. -- Special version of MakeItemscoreString that
   * interpolates dimension scores on items ONLY for the purposes of test
   * simulation
   * 
   * @param connection
   * @param oppkey
   * @param rowdelim
   * @param coldelim
   * @return
   * @throws ReturnStatusException
   */
  // TODO * This method need to be addressed when migrating to MySQL:
  // row_number()
  // over(order by...) funcionality
  public String SIM_MakeItemscoreString_FN (SQLConnection connection, UUID oppkey, Character rowdelim, Character coldelim) throws ReturnStatusException {
    String str = null; // this is return value
    String testkey = null;
    final String SQL_QUERY1 = "select _efk_AdminSubject as testkey from testopportunity where _Key = ${oppkey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null)
      testkey = record.<String> get ("testkey");

    DataBaseTable itemsTbl = getDataBaseTable ("items").addColumn ("_eft_Item", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("bankkey", SQL_TYPE_To_JAVA_TYPE.BIGINT).
        addColumn ("itemkey", SQL_TYPE_To_JAVA_TYPE.BIGINT).addColumn ("score", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("points", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("isFieldTest", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("segment", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("groupID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).
        addColumn ("isSelected", SQL_TYPE_To_JAVA_TYPE.BIT);
    connection.createTemporaryTable (itemsTbl);
    final String SQL_QUERY2 = " insert into ${itemsTblName} (_efk_Item, segment, bankkey, itemkey, score, isFieldTest, groupID, isSelected, points)"
        + " select _efk_ItemKey, segment, _efk_ITSBank, _efk_ITSItem, Score, isFieldTest, groupID, isSelected, scorepoint from testeeresponse "
        + " where _fk_TestOpportunity = ${oppkey} and _efk_ITSItem is not null and (IsInactive is null or IsInactive = 0)";
    SqlParametersMaps parms2 = parms1;
    Map<String, String> unquoted2 = new HashMap<> ();
    unquoted2.put ("itemsTblName", itemsTbl.getTableName ());

    int insertedCnt = executeStatement (connection, fixDataBaseNames (SQL_QUERY2, unquoted2), parms2, false).getUpdateCount ();
    if (insertedCnt <= 0) {
      connection.dropTemporaryTable (itemsTbl);
      return "";
    }

    DataBaseTable dimsTbl = getDataBaseTable ("dims").addColumn ("item", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("dim", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 255).
        addColumn ("points", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("score", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (dimsTbl);

    final String SQL_QUERY3 = "insert into ${dimsTblName} (item, dim, points) select _fk_Item, dimension, scorePoints "
        + " from ${ItemBankDB}.itemscoredimension, ${items}, testopportunitysegment "
        + " where _fk_TestOpportunity = ${oppkey} and segment = segmentPosition and _fk_AdminSUbject = _efk_Segment and _fk_Item = _efk_Item and length(dimension) > 0";
    SqlParametersMaps parms3 = parms1;
    Map<String, String> unquoted3 = new HashMap<> ();
    unquoted3.put ("dimsTblName", dimsTbl.getTableName ());
    unquoted3.put ("items", itemsTbl.getTableName ());

    final String query3 = fixDataBaseNames (SQL_QUERY3);
    executeStatement (connection, fixDataBaseNames (query3, unquoted3), parms3, false).getUpdateCount ();

    String itemKey = null, s = null;
    final String SQL_QUERY4 = "select  _efk_Item as itemKey from ${itemsTblName} limit 1";
    Map<String, String> unquoted4 = unquoted2;

    final String SQL_QUERY5 = "select  score from ${dimsTblName} where item = ${itemkey} limit 1";
    Map<String, String> unquoted5 = unquoted3;

    final String SQL_QUERY6 = "select bankkey, itemkey, isSelected, score, isFieldTest from ${itemsTblName} where _efk_Item = ${itemkey}";
    Map<String, String> unquoted6 = unquoted2;

    final String SQL_QUERY7 = " select score from ${itemsTblName} where _efk_Item = ${itemkey}";
    Map<String, String> unquoted7 = unquoted2;

    final String SQL_QUERY8 = "select  convert(sum(points), SIGNED ) as scorepoints from ${dimsTblName} where item = ${itemkey}";
    Map<String, String> unquoted8 = unquoted3;

    final String SQL_QUERY9 = "update ${dimsTblName} set score = convert(${scoreratio} * points, SIGNED) where item = ${itemkey}";
    Map<String, String> unquoted9 = unquoted3;

    final String SQL_QUERY10 = " select  convert(${score} - sum(score), SIGNED) as dif from ${dimsTblName} where item = ${itemkey}";
    Map<String, String> unquoted10 = unquoted3;
    // TODO: Elena check this query with Sai!
    final String SQL_QUERY11 = "update d set d.score = d.score + dif from  ${dimsTblName} as d "
        + " join ( "
        + "   select *, case when row_number() over(order by dim) <= ${dif} then 1 else 0 end as dif from ${dimsTblName}  "
        + "     where item = ${itemkey} and score < points "
        + "      ) as d2 on d.dim = d2.dim and d.item = d2.item";
    Map<String, String> unquoted11 = unquoted3;

    final String SQL_QUERY12 = "select   dim as dimname, score as dimscore from ${dimsTblName} where item = ${itemkey} limit 1";
    Map<String, String> unquoted12 = unquoted3;

    final String SQL_QUERY13 = "delete from ${dimsTblName} where item = ${itemkey} and dim = ${dimname}";
    Map<String, String> unquoted13 = unquoted3;

    final String SQL_QUERY14 = "select bankkey, itemkey, isSelected, isFieldTest from ${itemsTblName} where _efk_Item = ${itemkey}";
    Map<String, String> unquoted14 = unquoted2;

    while (true) {
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY4, unquoted4), null, false).getResultSets ().next ();
      if (result.getCount () <= 0)
        break;
      record = result.getRecords ().next ();
      itemKey = record.<String> get ("itemKey");

      s = null;
      SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("itemkey", itemKey);

      if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY5, unquoted5), parms5, false)) == false) {

        SqlParametersMaps parms6 = (new SqlParametersMaps ()).put ("itemkey", itemKey).put ("coldelim", coldelim.toString ());
        result = executeStatement (connection, fixDataBaseNames (SQL_QUERY6, unquoted6), parms6, false).getResultSets ().next ();
        record = (result.getCount () > 0 ? result.getRecords ().next () : null);
        if (record != null) {
          Long bankkey = record.<Long> get ("bankkey");
          Long itemkey = record.<Long> get ("itemkey");
          Integer score = record.<Integer> get ("score");
          Boolean isSelected = record.<Boolean> get ("isSelected");
          Boolean isFieldsTest = record.<Boolean> get ("isFieldTest");
          // final String SQL_QUERY6 =
          // "select bankkey, itemkey, isSelected, score, isFieldTest from ${itemsTblName} where _efk_Item = ${itemkey}";

          // final String SQL_QUERY6 =
          // "select  ltrim(str(bankkey)) + '-' + ltrim(str(itemkey)) + ${coldelim} "
          // + " + case isSelected when 1 then ltrim(str(score)) else '' end "
          // +
          // " + ${coldelim} + case isFieldTest when 0 then 'OP' else 'FT' end as s"
          // + " from ${itemsTblName} where _efk_Item = ${itemkey}";
          s = String.format ("%d-%d%s%s%s%s",
              bankkey, itemkey, coldelim, ((DbComparator.isEqual (isSelected, true) && score != null) ? (score.toString ()) : ""), coldelim,
              (DbComparator.isEqual (isFieldsTest, false) ? "OP" : "FT"));
        }
      } else {
        // -- must proportionally allocate score to dimensions for scoring
        // engine to 'score' this 'test'
        Integer score = null;
        SqlParametersMaps parms7 = (new SqlParametersMaps ()).put ("itemkey", itemKey);
        result = executeStatement (connection, fixDataBaseNames (SQL_QUERY7, unquoted7), parms7, false).getResultSets ().next ();
        record = (result.getCount () > 0 ? result.getRecords ().next () : null);
        if (record != null)
          score = record.<Integer> get ("score");

        Integer scorepoints = null;
        SqlParametersMaps parms8 = (new SqlParametersMaps ()).put ("itemkey", itemKey);
        result = executeStatement (connection, fixDataBaseNames (SQL_QUERY8, unquoted8), parms8, false).getResultSets ().next ();
        record = (result.getCount () > 0 ? result.getRecords ().next () : null);
        if (record != null) {
          Long tmp = record.<Long> get ("scorepoints");
          scorepoints = (tmp == null ? null : tmp.intValue ());
        }
        Float scoreratio = null;
        if (score != null && scorepoints != null)
          scoreratio = (float) score / scorepoints;

        SqlParametersMaps parms9 = (new SqlParametersMaps ()).put ("itemkey", itemKey).put ("scoreratio", scoreratio);
        executeStatement (connection, fixDataBaseNames (SQL_QUERY9, unquoted9), parms9, false).getUpdateCount ();

        Integer dif = null;
        SqlParametersMaps parms10 = (new SqlParametersMaps ()).put ("itemkey", itemKey).put ("score", score);
        result = executeStatement (connection, fixDataBaseNames (SQL_QUERY10, unquoted10), parms10, false).getResultSets ().next ();
        record = (result.getCount () > 0 ? result.getRecords ().next () : null);
        if (record != null) {
          Long tmp = record.<Long> get ("dif");
          dif = (tmp == null ? null : tmp.intValue ());
        }
        SqlParametersMaps parms11 = (new SqlParametersMaps ()).put ("itemkey", itemKey).put ("dif", dif);
        executeStatement (connection, fixDataBaseNames (SQL_QUERY11, unquoted11), parms11, false).getUpdateCount ();

        while (true) {
          String dimname = null;
          Integer dimscore = null;

          SqlParametersMaps parms12 = (new SqlParametersMaps ()).put ("itemkey", itemKey);
          result = executeStatement (connection, fixDataBaseNames (SQL_QUERY12, unquoted12), parms12, false).getResultSets ().next ();
          if (result.getCount () <= 0)
            break;
          record = result.getRecords ().next ();
          dimname = record.<String> get ("dimname");
          dimscore = record.<Integer> get ("dimscore");

          SqlParametersMaps parms13 = (new SqlParametersMaps ()).put ("itemkey", itemKey).put ("dimname", dimname);
          executeStatement (connection, fixDataBaseNames (SQL_QUERY13, unquoted13), parms13, false).getUpdateCount ();

          String ds = null;
          // SqlParametersMaps parms14 = (new SqlParametersMaps ()).put
          // ("itemkey", itemKey).put ("dimname", dimname).put ("coldelim",
          // coldelim.toString ()).
          // put ("dimscore", dimscore);
          SqlParametersMaps parms14 = (new SqlParametersMaps ()).put ("itemkey", itemKey);
          result = executeStatement (connection, fixDataBaseNames (SQL_QUERY14, unquoted14), parms14, false).getResultSets ().next ();
          record = (result.getCount () > 0 ? result.getRecords ().next () : null);
          if (record != null) {
            Long bankkey = record.<Long> get ("bankkey");
            Long itemkey = record.<Long> get ("itemkey");
            Boolean isSelected = record.<Boolean> get ("isSelected");
            Boolean isFieldsTest = record.<Boolean> get ("isFieldTest");
            // final String SQL_QUERY14 =
            // "select ltrim(str(bankkey)) + '-' + ltrim(str(itemkey)) + ${coldelim}  "
            // +
            // " + case isSelected when 1 then cast(${dimscore} as varchar(2)) else '' end "
            // +
            // " + ${coldelim} + case isFieldTest when 0 then 'OP' else 'FT' end "
            // +
            // " + ${coldelim} + ${dimname} as ds from ${itemsTblName} where _efk_Item = ${itemkey}";
            ds = String.format ("%d-%d%s%s%s%s%s%s",
                bankkey, itemkey, coldelim, ((DbComparator.isEqual (isSelected, true) && dimscore != null) ? (dimscore.toString ()) : ""), coldelim,
                (DbComparator.isEqual (isFieldsTest, false) ? "OP" : "FT"), coldelim, (dimname == null ? "" : dimname));
          }
          if (s == null)
            s = ds;
          else
            s = String.format ("%s%s%s", s, rowdelim.toString (), ds);
        }
      }
      final String SQL_QUERY15 = "delete from ${itemsTblName} where _efk_ITem = ${itemkey}";
      Map<String, String> unquoted15 = unquoted2;
      SqlParametersMaps parms15 = (new SqlParametersMaps ()).put ("itemkey", itemKey);
      executeStatement (connection, fixDataBaseNames (SQL_QUERY15, unquoted15), parms15, false).getUpdateCount ();

      if (s == null)
        continue;
      if (str == null)
        str = s;
      else
        str = String.format ("%s%s%s", str, rowdelim.toString (), s);
    }

    connection.dropTemporaryTable (dimsTbl);
    connection.dropTemporaryTable (itemsTbl);
    return str;
  }

  public String MakeItemscoreString_FN (SQLConnection connection, UUID oppkey) throws ReturnStatusException {

    return MakeItemscoreString_FN (connection, oppkey, ';', ',');
  }

  /**
   * -- This function creates an item string suitable for submission to either
   * the adaptive algorithm or to the scoring algorithm -- These strings are
   * stored with all completed, invalidated, and reset test opportunities in the
   * 'itemstring' field. -- Since the text of the response is not part of this
   * computation, there is no concern over the use of special characters in --
   * the delimiter set. -- Note that items are separated from each other by the
   * CHAR(13), and fields within an item are separated by the @delim character.
   * -- Returns '' score when the item is not selected. This mod is to conform
   * to DE ALT overloading of meaning to item score
   * 
   * @param connection
   * @param oppkey
   * @param rowdelim
   * @param coldelim
   * @return
   * @throws ReturnStatusException
   */
  // Original port
  public String MakeItemscoreString_FNV1 (SQLConnection connection, UUID oppkey, Character rowdelim, Character coldelim) throws ReturnStatusException {
    String str = null; // returned value
    String s = null;
    Integer p = null;

    final String SQL_QUERY1 = "select  O._Key from testopportunity O, _externs E where O._Key = ${oppkey} "
        + " and O.clientname = E.clientname and E.environment = 'SIMULATION' limit 1";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);

    if (exists (executeStatement (connection, SQL_QUERY1, parms1, false))) {
      return SIM_MakeItemscoreString_FN (connection, oppkey, rowdelim, coldelim);
    }

    DataBaseTable tbl = getDataBaseTable ("tbl").addColumn ("pos", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("bankkey", SQL_TYPE_To_JAVA_TYPE.BIGINT).
        addColumn ("itemkey", SQL_TYPE_To_JAVA_TYPE.BIGINT).addColumn ("score", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("isFieldTest", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("isInactive", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("groupID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).
        addColumn ("isSelected", SQL_TYPE_To_JAVA_TYPE.BIT);
    connection.createTemporaryTable (tbl);

    final String SQL_QUERY2 = "insert into ${tblName} (pos, bankkey, itemkey, score, isFieldTest, groupID, isSelected) "
        + " select position, _efk_ITSBank, _efk_ITSItem, Score, isFieldTest, groupID, isSelected "
        + " from testeeresponse where _fk_TestOpportunity = ${oppkey} and _efk_ITSItem is not null and (IsInactive is null or IsInactive = 0)";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("oppkey", oppkey);
    Map<String, String> unquotedParms2 = new HashMap<> ();
    unquotedParms2.put ("tblName", tbl.getTableName ());

    int insertedCnt = executeStatement (connection, fixDataBaseNames (SQL_QUERY2, unquotedParms2), parms2, false).getUpdateCount ();

    if (insertedCnt == 0) {
      connection.dropTemporaryTable (tbl);
      return "";
    }

    p = selectMinP (connection, tbl);

    while (p != null) {
      s = null;
      final String SQL_QUERY4 = "select bankkey, itemkey, isSelected, score, isFieldTest from ${tblName} where pos = ${p}";

      // final String SQL_QUERY4 =
      // "select ltrim(str(bankkey)) + '-' + ltrim(str(itemkey)) + ${coldelim} "
      // + "+ case isSelected when 1 then ltrim(str(score)) else '' end "
      // +
      // "+ ${coldelim} + case isFieldTest when 0 then 'OP' else 'FT' end as s"
      // + " from ${tblName} where pos = ${p}";
      Map<String, String> unquotedParms4 = unquotedParms2;
      SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("coldelim", coldelim.toString ()).put ("p", p);
      SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY4, unquotedParms4), parms4, false).getResultSets ().next ();
      DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        Long bankkey = record.<Long> get ("bankkey");
        Long itemkey = record.<Long> get ("itemkey");
        Integer score = record.<Integer> get ("score");
        Boolean isSelected = record.<Boolean> get ("isSelected");
        Boolean isFieldsTest = record.<Boolean> get ("isFieldTest");

        s = String.format ("%d-%d%s%s%s%s",
            bankkey, itemkey, coldelim, ((DbComparator.isEqual (isSelected, true) && score != null) ? (score.toString ()) : ""), coldelim,
            (DbComparator.isEqual (isFieldsTest, false) ? "OP" : "FT"));
      }

      final String SQL_QUERY5 = "delete from ${tblName} where pos = ${p}";
      Map<String, String> unquotedParms5 = unquotedParms2;
      SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("p", p);
      executeStatement (connection, fixDataBaseNames (SQL_QUERY5, unquotedParms5), parms5, false).getUpdateCount ();

      p = selectMinP (connection, tbl);

      if (s == null)
        continue;
      if (str == null)
        str = s;
      else
        str = String.format ("%s%s%s", str, rowdelim.toString (), s);
    }
    return str;
  }

  // Optimization attempt
  public String MakeItemscoreString_FN (SQLConnection connection, UUID oppkey, Character rowdelim, Character coldelim) throws ReturnStatusException {
    String str = null; // returned value
    String s = null;
    Integer p = null;

    final String SQL_QUERY1 = "select  O._Key from testopportunity O, _externs E where O._Key = ${oppkey} "
        + " and O.clientname = E.clientname and E.environment = 'SIMULATION' limit 1";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);

    if (exists (executeStatement (connection, SQL_QUERY1, parms1, false))) {
      return SIM_MakeItemscoreString_FN (connection, oppkey, rowdelim, coldelim);
    }

    final String SQL_QUERY2 = " select position as pos, _efk_ITSBank as bankkey, _efk_ITSItem as itemkey, Score, isFieldTest, isSelected "
        + " from testeeresponse where _fk_TestOpportunity = ${oppkey} and _efk_ITSItem is not null "
        + " and (IsInactive is null or IsInactive = 0) order by position";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("oppkey", oppkey);
    Map<String, String> unquotedParms2 = new HashMap<> ();

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY2, parms2, false).getResultSets ().next ();

    if (result.getCount () == 0) {
      return "";
    }
    Iterator<DbResultRecord> records = result.getRecords ();
    while (records.hasNext ()) {
      DbResultRecord record = records.next ();
      p = record.<Integer> get ("pos");

      if (p != null) {
        s = null;
        Long bankkey = record.<Long> get ("bankkey");
        Long itemkey = record.<Long> get ("itemkey");
        Integer score = record.<Integer> get ("score");
        Boolean isSelected = record.<Boolean> get ("isSelected");
        Boolean isFieldsTest = record.<Boolean> get ("isFieldTest");
        s = String.format ("%d-%d%s%s%s%s",
            bankkey, itemkey, coldelim, ((DbComparator.isEqual (isSelected, true) && score != null) ? (score.toString ()) : ""), coldelim,
            (DbComparator.isEqual (isFieldsTest, false) ? "OP" : "FT"));
        if (s == null)
          continue;
        if (str == null)
          str = s;
        else
          str = String.format ("%s%s%s", str, rowdelim.toString (), s);
      }
    }
    return str;
  }

  // TODO implement!
  public String MakeItemscoreString_XML_FN (SQLConnection connection, UUID oppkey) throws ReturnStatusException {

    StringBuilder result = new StringBuilder ();
    StringBuilder dimresult = new StringBuilder ();

    final String cmd1 = "SELECT Position as pos, _efk_ItemKey as itemkey,Score,ScorePoint,"
        + " scoreDimensions,Format,IsFieldTest,IsSelected,scorestatus "
        + " from testeeresponse where _fk_testopportunity = ${oppkey} and _efk_itemkey is not null "
        + " AND (IsInactive = 0 OR IsInactive IS NULL)";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();
    Iterator<DbResultRecord> iter1 = rs1.getRecords ();
    while (iter1.hasNext ()) {
      DbResultRecord rcd1 = iter1.next ();

      // (SELECT T3. itemkey AS "@itemkey",T3.IsSelected AS "@isSelected",CASE
      // when T3.isfieldtest = 1 THEN 'FT' ELSE 'OP' END AS "@purpose",
      // T3.FORMAT AS "@itemType",
      // (SELECT Score AS "@scorePoint" ,'overall' AS
      // "@scoreDimension",scorestatus AS "@scoreStatus",
      // (SELECT NULL FROM @tbl T1 WHERE T1.itemkey = T2.itemkey
      // FOR XML PATH ('SubScoreList'), TYPE)
      // FROM @tbl T2 WHERE T2.itemkey = T3.itemkey FOR XML PATH ('ScoreInfo'),
      // TYPE)
      // FROM @tbl T3 WHERE T3.scoreDimension IS NULL
      // FOR XML PATH ('Item')
      // <Item itemkey="200-14001" isSelected="1" purpose="OP" itemType="MI">
      // <ScoreInfo scorePoint="1" scoreDimension="overall"
      // scoreStatus="Scored">
      // <SubScoreList/>
      // </ScoreInfo>
      // </Item>
      String itemkey = rcd1.<String> get ("itemkey");
      Boolean isSelected = rcd1.<Boolean> get ("isSelected");
      Boolean isFieldTest = rcd1.<Boolean> get ("isFieldTest");
      String format = rcd1.<String> get ("format");
      Integer score = rcd1.<Integer> get ("score");
      String scorestatus = rcd1.<String> get ("scorestatus");
      String scoreDimensions = rcd1.<String> get ("scoreDimensions");

      if (scoreDimensions == null) {
        result.append (buildItemNode (itemkey, isSelected, isFieldTest, format));
        result.append (buildScoreInfoNode (score, "overall", scorestatus));
        // SubScoreList is empty
        result.append ("<SubScoreList/>");
        // close ScoreInfo node
        result.append ("</ScoreInfo>");
        // close Item node
        result.append ("</Item>");

      } else {
        dimresult.append (buildItemNode (itemkey, isSelected, isFieldTest, format));
        // scoreDimensions contains an xml-formatted string
        dimresult.append (scoreDimensions);
        dimresult.append ("</Item>");
      }
    }
    String str = String.format ("%s%s",
        (result == null ? "" : result.toString ()),
        (dimresult == null ? "" : dimresult.toString ()));
    return str;
  }

  private String buildItemNode (String itemkey, Boolean isSelected, Boolean isFieldTest, String format) {
    // (SELECT T3. itemkey AS "@itemkey",T3.IsSelected AS "@isSelected",CASE
    // when T3.isfieldtest = 1 THEN 'FT' ELSE 'OP' END AS "@purpose", T3.FORMAT
    // AS "@itemType",
    // Example
    // <Item itemkey="200-14001" isSelected="1" purpose="OP" itemType="MI">
    String tmp = String.format ("<Item itemkey=\"%s\" isSelected=\"%d\" purpose=\"%s\" itemType=\"%s\">",
        itemkey,
        (isSelected == true ? 1 : 0),
        (isFieldTest == true ? "FT" : "OP"),
        format);
    return tmp;
  }

  private String buildScoreInfoNode (Integer score, String scoreDimConstant, String scoreStatus) {
    // (SELECT Score AS "@scorePoint" ,'overall' AS
    // "@scoreDimension",scorestatus AS "@scoreStatus",
    // Example
    // <ScoreInfo scorePoint="1" scoreDimension="overall" scoreStatus="Scored">
    String tmp = String.format ("<ScoreInfo scorePoint=\"%d\" scoreDimension=\"%s\" scoreStatus=\"%s\"><SubScoreList /></ScoreInfo>",
        score, scoreDimConstant, scoreStatus);
    return tmp;
  }

  public SingleDataResultSet T_GetTestforScoring_SP (SQLConnection connection, UUID oppkey) throws ReturnStatusException {

    return T_GetTestforScoring_SP (connection, oppkey, ';', ',');
  }

  public SingleDataResultSet T_GetTestforScoring_SP (SQLConnection connection, UUID oppkey, Character rowdelim, Character coldelim) throws ReturnStatusException {

    Date now = _dateUtil.getDateWRetStatus (connection);
    String reason = _commonDll.CanScoreOpportunity_FN (connection, oppkey);
    if (DbComparator.notEqual (reason, "Unofficial score only")) {

      return _commonDll.ReturnStatusReason ("failed", reason);
    }

    UUID mark = UUID.randomUUID ();
    Date adjustedNow = _commonDll.adjustDate (now, -15, Calendar.SECOND);

    final String SQL_QUERY1 = " update testopportunity set scoringDate = ${now}, scoreMark = ${mark} "
        + " where _key = ${oppkey} and (scoringDate is null or scoringDate < ${adjustedNow})";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("now", now).put ("mark", mark).put ("oppkey", oppkey).put ("adjustedNow", adjustedNow);
    executeStatement (connection, SQL_QUERY1, parms1, false).getUpdateCount ();

    Date scoreDate = null, dateCompleted = null;
    final String SQL_QUERY2 = "select scoringDate as scoreDate, dateCompleted from testopportunity where _Key = ${oppkey} and scoreMark = ${mark}";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("mark", mark).put ("oppkey", oppkey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY2, parms2, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      scoreDate = record.<Date> get ("scoreDate");
      dateCompleted = record.<Date> get ("dateCompleted");
    }
    if (scoreDate == null) {
      _commonDll._ReturnError_SP (connection, null, "_GetTestforScoring", reason, null, oppkey, null, "failed");
    }

    String itemstring = MakeItemscoreString_FN (connection, oppkey, rowdelim, coldelim);

    // String sessionDB = getAppSettings ().get ("TDSSessionDBName");
    String sessionDB = getTdsSettings ().getTDSSessionDBName ();
    final String SQL_QUERY3 = "insert into ${ArchiveDB}.opportunityaudit (_fk_TestOpportunity, AccessType, Comment, dateaccessed, hostname, dbname) "
        + " values (${oppkey}, 'Get Score String', ${itemstring}, now(3), ${localhost}, ${dbname})";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("itemstring", itemstring).put ("oppkey", oppkey).
        put ("localhost", _commonDll.getLocalhostName ()).put ("dbname", sessionDB);
    executeStatement (connection, fixDataBaseNames (SQL_QUERY3), parms3, false).getUpdateCount ();

    String status = (reason == null ? "Official score" : "Unofficial score");
    List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
    CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
    rcd.put ("status", status);
    rcd.put ("reason", null);
    rcd.put ("itemstring", itemstring);
    rcd.put ("rowdelim", rowdelim);
    rcd.put ("coldelim", coldelim);
    rcd.put ("dateCompleted", dateCompleted);

    resultList.add (rcd);

    SingleDataResultSet rs = new SingleDataResultSet ();
    rs.addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs.addColumn ("reason", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs.addColumn ("itemstring", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs.addColumn ("rowdelim", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs.addColumn ("coldelim", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs.addColumn ("dateCompleted", SQL_TYPE_To_JAVA_TYPE.DATETIME);
    rs.addRecords (resultList);

    return rs;
  }

  public SingleDataResultSet T_GetTestforScoring2_SP (SQLConnection connection, UUID oppkey) throws ReturnStatusException {

    return T_GetTestforScoring2_SP (connection, oppkey, ';', ',');
  }

  public SingleDataResultSet T_GetTestforScoring2_SP (SQLConnection connection, UUID oppkey, Character rowdelim) throws ReturnStatusException {

    return T_GetTestforScoring2_SP (connection, oppkey, rowdelim, ',');
  }

  public SingleDataResultSet T_GetTestforScoring2_SP (SQLConnection connection, UUID oppkey, Character rowdelim, Character coldelim) throws ReturnStatusException {

    Date now = _dateUtil.getDateWRetStatus (connection);
    String reason = _commonDll.CanScoreOpportunity_FN (connection, oppkey);
    if (DbComparator.notEqual (reason, "Unofficial score only")) {

      return _commonDll.ReturnStatusReason ("failed", reason);
    }

    UUID mark = UUID.randomUUID ();
    Date adjustedNow = _commonDll.adjustDate (now, -15, Calendar.SECOND);

    final String SQL_QUERY1 = " update testopportunity set scoringDate = ${now}, scoreMark = ${mark} "
        + " where _key = ${oppkey} and (scoringDate is null or scoringDate < ${adjustedNow})";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("now", now).put ("mark", mark).put ("oppkey", oppkey).put ("adjustedNow", adjustedNow);
    executeStatement (connection, SQL_QUERY1, parms1, false).getUpdateCount ();

    Date scoreDate = null, dateCompleted = null;
    final String SQL_QUERY2 = "select scoringDate as scoreDate, dateCompleted from testopportunity where _Key = ${oppkey} and scoreMark = ${mark}";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("mark", mark).put ("oppkey", oppkey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY2, parms2, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      scoreDate = record.<Date> get ("scoreDate");
      dateCompleted = record.<Date> get ("dateCompleted");
    }

    String segmentForms = null;
    final String cmd1 = "select ifnull(FormID,'') as form from testopportunitysegment "
        + " where _fk_TestOpportunity = ${oppkey} order by SegmentPosition";
    SqlParametersMaps parameters1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parameters1, false).getResultSets ().next ();
    Iterator<DbResultRecord> iter1 = rs1.getRecords ();
    while (iter1.hasNext ()) {
      DbResultRecord rcd1 = iter1.next ();
      String form = rcd1.<String> get ("form");
      if (segmentForms == null)
        segmentForms = String.format ("%s", form);
      else
        segmentForms = String.format ("%s-%s", segmentForms, form);
    }

    if (scoreDate == null) {
      _commonDll._ReturnError_SP (connection, null, "_GetTestforScoring", reason, null, oppkey, null, "failed");
    }

    String itemstring = null;
    if (rowdelim == null) {
      itemstring = MakeItemscoreString_XML_FN (connection, oppkey);
      rowdelim = ';';
    } else {
      itemstring = MakeItemscoreString_FN (connection, oppkey, rowdelim, coldelim);
    }
    // String sessionDB = getAppSettings ().get ("TDSSessionDBName");
    String sessionDB = getTdsSettings ().getTDSSessionDBName ();
    final String SQL_QUERY3 = "insert into ${ArchiveDB}.opportunityaudit (_fk_TestOpportunity, AccessType, Comment, dateaccessed, hostname, dbname) "
        + " values (${oppkey}, 'Get Score String', ${itemstring}, now(3), ${localhost}, ${dbname})";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("itemstring", itemstring).put ("oppkey", oppkey).
        put ("localhost", _commonDll.getLocalhostName ()).put ("dbname", sessionDB);
    executeStatement (connection, fixDataBaseNames (SQL_QUERY3), parms3, false).getUpdateCount ();

    String status = (reason == null ? "Official score" : "Unofficial score");
    List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
    CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
    rcd.put ("status", status);
    rcd.put ("reason", null);
    rcd.put ("itemstring", itemstring);
    rcd.put ("rowdelim", rowdelim);
    rcd.put ("coldelim", coldelim);
    rcd.put ("dateCompleted", dateCompleted);
    rcd.put ("segmentforms", segmentForms);
    resultList.add (rcd);

    SingleDataResultSet rs = new SingleDataResultSet ();
    rs.addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs.addColumn ("reason", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs.addColumn ("itemstring", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs.addColumn ("rowdelim", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs.addColumn ("coldelim", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs.addColumn ("dateCompleted", SQL_TYPE_To_JAVA_TYPE.DATETIME);
    rs.addColumn ("segmentforms", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs.addRecords (resultList);

    return rs;
  }

  public SingleDataResultSet T_GetTestforCompleteness_SP (SQLConnection connection, UUID oppkey) throws ReturnStatusException {

    return T_GetTestforCompleteness_SP (connection, oppkey, ';', ',');
  }

  public SingleDataResultSet T_GetTestforCompleteness_SP (SQLConnection connection, UUID oppkey, Character rowdelim, Character coldelim) throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);
    String scoreString = MakeItemscoreString_FN (connection, oppkey, rowdelim, coldelim);

    List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
    CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
    rcd.put ("scoreString", scoreString);
    resultList.add (rcd);

    SingleDataResultSet rs = new SingleDataResultSet ();
    rs.addColumn ("scoreString", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs.addRecords (resultList);

    _commonDll._LogDBLatency_SP (connection, "T_GetTestforCompleteness", starttime, null, true, null, oppkey);
    return rs;
  }

  /***
   *     This is the Java version of t_getsession stored procedure. This method is not used now. It is only needed when we want to migrate the Mysql stored procedure t_getsession  to java.
   * @param connection
   * @param clientname
   * @param sessionId
   * @return
   * @throws ReturnStatusException
   */
  public MultiDataResultSet T_GetSession_SP_Java (SQLConnection connection, String clientname, String sessionId) throws ReturnStatusException {

    List<SingleDataResultSet> resultsSets = new ArrayList<SingleDataResultSet> ();
    Date today =  _dateUtil.getDateWRetStatus (connection);

    UUID sessionKey = null;
    Long proctorKey = null;
    String proctorId = null;
    String proctorName = null;
    String status = null;
    String name = null;
    Date dateCreated = null;
    Date dateBegin = null;
    Date dateEnd = null;
    String msgstatus = null, msgkey = null, msgarg = null;

    _Ref<UUID> sessionKeyRef = new _Ref<> ();
    _Ref<String> sessionIdRef = new _Ref<> ();

    if ("GUEST Session".equalsIgnoreCase (sessionId)) {
      if (_AllowProctorlessSessions_FN (connection, clientname)) {

        _SetupProctorlessSession_SP (connection, clientname, sessionKeyRef, sessionIdRef);
        sessionId = sessionIdRef.get ();
        sessionKey = sessionKeyRef.get ();

      } else {

        SingleDataResultSet rs = _commonDll._ReturnError_SP (connection, clientname, "T_GetSession", "You are not allowed to log in without a Test Administrator");
        resultsSets.add (rs);
        return new MultiDataResultSet (resultsSets);
      }
    }
    // -- get session
    final String SQL_QUERY1 = "SELECT _Key as sessionKey, _efk_Proctor as proctorKey , ProctorID, ProctorName, Status, Name, DateCreated, DateBegin, DateEnd "
        + " from session WHERE (SessionID = ${sessionID}) and clientname = ${clientname}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("sessionId", sessionId).put ("clientname", clientname);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, true).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      sessionKey = record.<UUID> get ("sessionKey");
      proctorKey = record.<Long> get ("proctorKey");
      proctorId = record.<String> get ("proctorId");
      proctorName = record.<String> get ("proctorName");
      status = record.<String> get ("status");
      name = record.<String> get ("name");
      dateCreated = record.<Date> get ("dateCreated");
      dateBegin = record.<Date> get ("dateBegin");
      dateEnd = record.<Date> get ("dateEnd");
    }
    // -- session found?
    if (sessionKey == null) {
      msgstatus = "denied";
      msgkey = "Could not find session, please check with your test administrator.";

    } else if (DbComparator.isEqual (status, "closed")) {
      // -- does the session's state permit joining?
      msgstatus = "closed";
      msgkey = "The testing session is closed, please check with your test administrator.";

    } else if (proctorKey == null && _AllowProctorlessSessions_FN (connection, clientname) == false) {
      msgstatus = "denied";
      msgkey = "The session is not available, please check with your test administrator.";

    } else if (DbComparator.notEqual (status, "open") || dateBegin == null || dateEnd == null) {
      // -- would this rule ever occur?
      msgstatus = "denied";
      msgkey = "The testing session is not in a valid state, please check with your test administrator.";

    } else if (DbComparator.lessThan (_dateUtil.getDateWRetStatus (connection), dateBegin)) {
      msgarg = new SimpleDateFormat ("MM/dd/yyyy").format (dateBegin);
      msgstatus = "denied";
      msgkey = "The testing session starts on {0}. For further assistance, please check with your test administrator.";

    } else if (DbComparator.greaterThan (today, dateEnd)) {
      msgarg = new SimpleDateFormat ("MM/dd/yyyy").format (dateEnd);
      msgstatus = "denied";
      msgkey = "The testing session expired on {0}. For further assistance, please check with your test administrator.";

    }

    if (msgstatus != null) {
      SingleDataResultSet rs = _commonDll._ReturnError_SP (connection, clientname, "T_GetSession", msgkey, msgarg, null, null, msgstatus);
      resultsSets.add (rs);
      return new MultiDataResultSet (resultsSets);
    }

    // TODO: Elena. Stored proc sets reason to empty string rather than null.
    // why?
    SingleDataResultSet rs1 = _commonDll.ReturnStatusReason ("open", "");
    resultsSets.add (rs1);

    List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
    CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
    rcd.put ("sessionKey", sessionKey);
    rcd.put ("proctorKey", proctorKey);
    rcd.put ("proctorId", proctorId);
    rcd.put ("proctorName", proctorName);
    rcd.put ("name", name);
    rcd.put ("dateCreated", dateCreated);
    rcd.put ("dateBegin", dateBegin);
    rcd.put ("dateEnd", dateEnd);

    resultList.add (rcd);

    SingleDataResultSet rs2 = new SingleDataResultSet ();
    rs2.addColumn ("sessionKey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER);
    rs2.addColumn ("proctorKey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER);
    rs2.addColumn ("proctorId", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs2.addColumn ("proctorName", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs2.addColumn ("name", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs2.addColumn ("dateCreated", SQL_TYPE_To_JAVA_TYPE.DATE);
    rs2.addColumn ("dateBegin", SQL_TYPE_To_JAVA_TYPE.DATE);
    rs2.addColumn ("dateEnd", SQL_TYPE_To_JAVA_TYPE.DATE);
    rs2.addRecords (resultList);

    resultsSets.add (rs2);
    _commonDll._LogDBLatency_SP (connection, "T_GetSession", today, 0L, true, null, null, null, clientname, null);

    return new MultiDataResultSet (resultsSets);
  }
  
  /***
   * This method makes call to t_getsession mysql stored procedure.
   */
  public MultiDataResultSet T_GetSession_SP (SQLConnection connection, String clientname, String sessionId) throws ReturnStatusException {

    final String SQL_QUERY = "call t_getsession(${clientname}, ${sessionId})";
    SqlParametersMaps params = new SqlParametersMaps ();
    params.put ("clientname", clientname);
    params.put ("sessionId", sessionId);

    MultiDataResultSet resultSet = executeStatement (connection, SQL_QUERY, params, false);
    return resultSet;
  }

  public SingleDataResultSet T_GetResponseRationales_SP (SQLConnection connection, UUID oppkey, UUID sessionKey, UUID browserKey) throws ReturnStatusException {

    _Ref<String> errorRef = new _Ref<> ();
    _ValidateTesteeAccessProc_SP (connection, oppkey, sessionKey, browserKey, false, errorRef);
    if (errorRef.get () != null)
      return _commonDll._ReturnError_SP (connection, null, "T_GetResponseRationales", errorRef.get (), null, oppkey, null);

    String clientname = null;
    final String SQL_QUERY1 = "select clientname from testopportunity where _key = ${oppkey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      clientname = record.<String> get ("clientname");
    }

    Boolean pt = null;
    final String SQL_QUERY2 = "select isPracticeTest as pt from externs where clientname = ${clientname}";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("clientname", clientname);
    result = executeStatement (connection, SQL_QUERY2, parms2, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      pt = record.<Boolean> get ("pt");
    }

    if (DbComparator.notEqual (pt, true))
      return _commonDll._ReturnError_SP (connection, clientname, "T_GetResponseRationales", "Access allowed only on practice tests");

    final String SQL_QUERY3 = "SELECT Position, Page, Format, Score, ScorePoint, "
        + " CASE WHEN Format = 'MC' THEN Response ELSE Format END as Response, "
        + " CASE WHEN Format = 'MC' THEN scoreRationale ELSE Format END as ScoreRationale  "
        + " FROM testeeresponse  WHERE _fk_TestOpportunity =  ${oppkey} ORDER BY Position";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("oppkey", oppkey);
    result = executeStatement (connection, SQL_QUERY3, parms3, true).getResultSets ().next ();
    return result;
  }

  public SingleDataResultSet T_GetPTSetup_SP (SQLConnection connection, String clientname) throws ReturnStatusException {
    Date today = _dateUtil.getDateWRetStatus (connection);
    _Ref<UUID> sessionKeyRef = new _Ref<> ();
    _Ref<String> sessionIdRef = new _Ref<> ();

    if (_AllowAnonymousTestee_FN (connection, clientname) == false)
      return _commonDll._ReturnError_SP (connection, clientname, "T_GetPTSetup", "This system is not available for guest login");

    if (_AllowProctorlessSessions_FN (connection, clientname))
      _SetupProctorlessSession_SP (connection, clientname, sessionKeyRef, sessionIdRef);

    List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
    CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
    rcd.put ("loginAs", "GUEST");
    rcd.put ("firstname", "GUEST");
    rcd.put ("sessionKey", sessionKeyRef.get ());
    rcd.put ("sessionId", sessionIdRef.get ());
    resultList.add (rcd);

    SingleDataResultSet rs = new SingleDataResultSet ();
    rs.addColumn ("loginAs", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs.addColumn ("firstname", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs.addColumn ("sessionKey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER);
    rs.addColumn ("sessionId", SQL_TYPE_To_JAVA_TYPE.VARCHAR);

    rs.addRecords (resultList);
    _commonDll._LogDBLatency_SP (connection, "_SetTesteeAttributes", today, 0L, true, null, null, null, clientname, null);

    return rs;
  }

  /**
   * -- This procedure mainly used for setting scores for constructed response
   * items by asynchronous item scorer -- -- Updated 10/2009 to required the
   * itemkey and the response sequence to prevent delayed scores for obsolete
   * item responses
   * 
   * -- 3/2011: Updated for _fk_testOpportunity and clientname -- 3/2012: Moves
   * reliance on testeeresponse index to testeeresponsescore table
   * 
   * @param SQLConnection
   *          the database connection
   * @param oppkey
   *          the test opportunity UUID
   * @param itemKey
   *          the item key UUID
   * @param position
   * @param sequence
   * @param score
   * @param scorestatus
   * @param scoreRationale
   * @param scoremark
   * 
   * @throws ReturnStatusException
   * @throws SQLException
   */
  public SingleDataResultSet S_UpdateItemScore_SP (SQLConnection connection, UUID oppkey, Long itemKey, Integer position, Integer sequence, Integer score, String scorestatus,
      String scoreRationale, UUID scoremark, String scoreDimensions)
      throws ReturnStatusException {
    SingleDataResultSet result = null;
    Date now = _dateUtil.getDateWRetStatus (connection);

    String clientname = null;

    final String query1 = "select clientname from testopportunity where _Key = ${oppkey} ";
    SqlParametersMaps params1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);
    SingleDataResultSet result1 = executeStatement (connection, query1, params1, false).getResultSets ().next ();
    DbResultRecord record1 = result1.getCount () > 0 ? result1.getRecords ().next () : null;
    if (record1 != null) {
      clientname = record1.<String> get ("clientname");
    }

    UUID comparemark = null;
    Integer seq = null;
    Integer attempts = null;

    final String query2 = "select scoreMark, responseSEquence, scoreAttempts from testeeresponsescore where _fk_testOpportunity = ${oppkey} and Position = ${position} ";
    SqlParametersMaps params2 = (new SqlParametersMaps ()).put ("oppkey", oppkey);
    params2.put ("position", position);
    SingleDataResultSet result2 = executeStatement (connection, query2, params2, false).getResultSets ().next ();
    DbResultRecord record2 = result2.getCount () > 0 ? result2.getRecords ().next () : null;
    if (record2 != null) {
      comparemark = record2.<UUID> get ("scoreMark");
      seq = record2.<Integer> get ("responseSEquence");
      attempts = record2.<Integer> get ("scoreAttempts");
    }

    if (DbComparator.isEqual (scoremark, comparemark) && DbComparator.isEqual (sequence, seq)) {
      // if (scoremark.equals (comparemark) && sequence.equals (seq)) {
      final String updateQuery1 = "update testeeresponsescore set scorestatus = ${scorestatus}, scoredDate = ${now} "
          + " where _fk_TestOpportunity = ${oppkey} and position = ${position} and responseSequence = ${sequence} ";
      SqlParametersMaps updateParams1 = new SqlParametersMaps ();
      updateParams1.put ("scorestatus", scorestatus);
      updateParams1.put ("now", now);
      updateParams1.put ("oppkey", oppkey);
      updateParams1.put ("position", position);
      updateParams1.put ("sequence", sequence);
      Integer updateCount1 = executeStatement (connection, updateQuery1, updateParams1, false).getUpdateCount ();

      final String updateQuery2 = "Update testeeresponse set Score = ${score}, scorestatus = ${scorestatus}, scoredDate = ${now}, "
          + " scoreLatency = convert (timestampdiff (MICROSECOND, scoringDate, ${now})/1000, SIGNED), scoreRationale = ${scoreRationale}, scoreAttempts = ${attempts}, "
          + " scoredimensions = ${scoredimensions} "
          + " where _fk_TestOpportunity = ${oppkey} and position = ${position} and _efk_ITSITem = ${itemKey} and responseSequence = ${sequence} ";
      SqlParametersMaps updateParams2 = new SqlParametersMaps ();
      updateParams2.put ("score", score);
      updateParams2.put ("scorestatus", scorestatus);
      updateParams2.put ("now", now);
      updateParams2.put ("scoreRationale", scoreRationale);
      updateParams2.put ("attempts", attempts);
      updateParams2.put ("oppkey", oppkey);
      updateParams2.put ("position", position);
      updateParams2.put ("itemKey", itemKey);
      updateParams2.put ("sequence", sequence);
      updateParams2.put ("scoredimensions", scoreDimensions);
      Integer updateCount2 = executeStatement (connection, updateQuery2, updateParams2, false).getUpdateCount ();

      if (AuditResponses_FN (connection, clientname) == 1) {
        final String updateQuery3 = "update  testeeresponseaudit set score = ${score}, scoredDate = ${now},  "
            + " scoreLatency = convert (timestampdiff (MICROSECOND, scoringDate, ${now})/1000, SIGNED) "
            + " where _fk_TestOpportunity = ${oppkey} and position = ${position} and sequence = ${sequence} and scoremark = ${scoremark} ";
        // TODO - The original Transact-SQL stored procedure where clause has
        // "position = position". What's the point of this? I think it's an
        // error.
        // 11.07.2013 Elena: I agree, changed position to position parameter
        SqlParametersMaps updateParams3 = new SqlParametersMaps ();
        updateParams3.put ("score", score);
        updateParams3.put ("now", now);
        updateParams3.put ("oppkey", oppkey);
        updateParams3.put ("sequence", sequence);
        updateParams3.put ("scoremark", scoremark);
        updateParams3.put ("position", position);
        Integer updateCount3 = executeStatement (connection, updateQuery3, updateParams3, false).getUpdateCount ();
      }
      result = _commonDll.ReturnStatusReason ("updated", null);

    } else {
      LogDBErrorArgs ldbeArgs = new LogDBErrorArgs (connection);
      ldbeArgs.setProcName ("S_UpdateItemScore");
      ldbeArgs.setMsg (String.format ("No such item at %d with sequence %d and scoremark %s",
          position, sequence, scoremark.toString ()));
      ldbeArgs.setTestOppKey (oppkey);
      ldbeArgs.setClientName (clientname);
      _commonDll._LogDBError_SP (ldbeArgs);

      ReturnErrorArgs reArgs = new ReturnErrorArgs (connection);
      reArgs.setProcName (ldbeArgs.getProcName ());
      reArgs.setClient (clientname);
      reArgs.setAppKey ("No such item: {0}");
      reArgs.setArgString (position.toString ());
      reArgs.setOppKey (oppkey);
      reArgs.setContext ("UpdateItemScore");
      reArgs.setStatus ("failed");
      result = _commonDll._ReturnError_SP (reArgs);
    }

    LogDBLatencyArgs args = new LogDBLatencyArgs (connection);
    args.setProcName ("S_UpdateItemScore");
    args.setStartTime (now);
    args.setTestOppKey (oppkey);
    args.setClientName (clientname);
    _commonDll._LogDBLatency_SP (args);

    return result;
  }

  // TODO Elena remove this hack after you introduce scoreDimensions parameter
  // to the above method
  public SingleDataResultSet S_UpdateItemScore_SP (SQLConnection connection, UUID oppkey, Long itemKey, Integer position, Integer sequence, Integer score, String scorestatus,
      String scoreRationale, UUID scoremark)
      throws ReturnStatusException {
    return S_UpdateItemScore_SP (connection, oppkey, itemKey, position, sequence, score, scorestatus, scoreRationale, scoremark, null);
  }

  /**
   * Integrity check: Make sure that testee opportunity attributes match those
   * in table testopportunity Unlike GetItems and InsertItems, there is no
   * provision for suppressing the test status.
   * 
   * @note Related to ValidateProctorSession_FN and _ValidateTesteeAccessProc_SP
   * 
   * @param SQLConnection
   *          the database connection
   * @param testopp
   *          the test opportunity UUID
   * @param session
   *          the session UUID
   * @param browserID
   *          the browser UUID
   * 
   * @return String containing status message if error or null if ok.
   * 
   * @throws ReturnStatusException
   */
  public String _ValidateItemsAccess_FN (SQLConnection connection, UUID testopp, UUID session, UUID browserID) throws ReturnStatusException {
    Date now = _dateUtil.getDateWRetStatus (connection);
    UUID sessionKey = null;
    UUID browserKey = null;
    String status = null;

    final String query1 = "SELECT _fk_Session, _fk_browser, status from testopportunity where _Key = ${testopp} "; // With_(NoLock)
    SqlParametersMaps params1 = (new SqlParametersMaps ()).put ("testopp", testopp);
    SingleDataResultSet result1 = executeStatement (connection, query1, params1, true).getResultSets ().next ();
    DbResultRecord record1 = result1.getCount () > 0 ? result1.getRecords ().next () : null;
    if (record1 != null) {
      sessionKey = record1.<UUID> get ("_fk_Session");
      browserKey = record1.<UUID> get ("_fk_browser");
      status = record1.<String> get ("status");
    }

    if ((sessionKey == null) || (session == null) || (!sessionKey.equals (session)))
    {
      return "The session keys do not match; please consult your test administrator";
    }

    String sessionStatus = null;
    Date dateBegin = null;
    Date dateEnd = null;

    final String query2 = "select status, DateBegin, DateEnd from session where _Key = ${session} "; // With_(NoLock)
    SqlParametersMaps params2 = (new SqlParametersMaps ()).put ("session", session);
    SingleDataResultSet result2 = executeStatement (connection, query2, params2, true).getResultSets ().next ();
    DbResultRecord record2 = result2.getCount () > 0 ? result2.getRecords ().next () : null;
    if (record2 != null) {
      sessionStatus = record2.<String> get ("status");
      dateBegin = record2.<Date> get ("DateBegin");
      dateEnd = record2.<Date> get ("DateEnd");
    }

    Date dateBeginAdj = _commonDll.adjustDateMinutes (dateBegin, -5);
    if (DbComparator.notEqual (sessionStatus, "open") || DbComparator.lessThan (now, dateBeginAdj) || DbComparator.greaterThan (now, dateEnd)) {

      return "The session is not available for testing, please check with your test administrator.";
    }

    if (DbComparator.notEqual (browserKey, browserID)) {
      return "Access violation: System access denied";
    }

    if ((!"started".equals (status)) && (!"review".equals (status))) {
      return "The test opportunity has been paused";
    }

    return null;
  }

  /**
   * -- returns a recordset of the opportunity's segments suitable for passing
   * to the C# item selection algorithm -- the structure of the recordset
   * parallels that of the C# class : -- string segmentID, int position, string
   * formKey, string ftItems
   * 
   * returns segmentID, SegmentPosition as position, formKey, formID, algorithm,
   * ftItems, isPermeable, restorePermOn Most of these values are passed to the
   * C# item selection algorithm via the TestSegment struct, one for each record
   * returned by the procedure.
   * 
   * 2 values must be operationalized by the student app: isPermeable and
   * restorePermOn. See the discussion of these above. These parameters need to
   * be available to the client-side code.
   * 
   * It is not expected that the remainder of these values change during a
   * sitting. The student app should not hold these parameters client-side.
   * ftItems is the actual string of item keys and positions that have been
   * pre-allocated to the test. In the case of independent field tests, this
   * constitutes every item on the entire test. Not only is this a burden on
   * network transmissions, but it is also a security risk.
   * 
   * -- restorePermOn is one of -- 'segment' : restore the default when the
   * testee exits the segment (so he/she is allowed one visit into the segment.
   * Behaves most like the natural manner) -- The default will be restored on
   * pause whether or not the testee entered it. -- 'paused' : restore the
   * default when the testee pauses the test. If testee does not visit the
   * segment, it doesn't matter. -- 'completed' : the default is never restored
   * 
   * @note Related to T_RemoveResponse_SP and T_GetOpportunityComment_SP
   * 
   * @param SQLConnection
   *          the database connection
   * @param oppkey
   *          the test opportunity UUID
   * @param session
   *          the session UUID
   * @param browserID
   *          the browser UUID
   * 
   * @return SingleDataResultSet containing the segments for the specified Test
   *         Opportunity.
   * 
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_GetOpportunitySegments_SP (SQLConnection connection, UUID oppkey, UUID session, UUID browser) throws ReturnStatusException {
    if ((session != null) && (browser != null)) {
      String message = _ValidateItemsAccess_FN (connection, oppkey, session, browser);
      if (message != null) {
        ReturnErrorArgs args = new ReturnErrorArgs (connection);
        args.setProcName ("T_GetOpportunitySegments");
        args.setAppKey (message);
        args.setOppKey (oppkey);
        args.setContext ("_ValidateItemsAccess");
        args.setStatus ("failed");
        return _commonDll._ReturnError_SP (args);
      }
    }

    final String query1 = "select _efk_Segment as segmentKey, segmentID, SegmentPosition as position, formKey, formID, algorithm, isPermeable, restorePermOn, ftItems "
        + " from testopportunitysegment where _fk_TestOpportunity = ${oppkey} ";
    SqlParametersMaps params1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);
    return executeStatement (connection, query1, params1, false).getResultSets ().next ();
  }
  
  class EligibleTest {
    private String testKey;
    private String testId;
    private String grade;
    private String subject;
    private String enroll;
    private String mode;
    private UUID session;
    private String windowid;
    private Integer sessionType;
    private Integer maxOpps;
    private Integer windowMax;
    private Integer modeMax;
    private Integer windowSession;
    private Integer modeSession;
    private Long rtsEquiv;
    private Date startDate;
    private Date endDate;
    private Integer opportunity;
    private String displayName;
    private int sortOrder;
    private String status;
    private String reason;
    private String clientName;
    private Integer numRef = 0;
    private Boolean newoppRef = false;
    
    
    
    public Boolean getNewoppRef () {
      return newoppRef;
    }
    public void setNewoppRef (Boolean newoppRef) {
      this.newoppRef = newoppRef;
    }
    public Integer getNumRef () {
      return numRef;
    }
    public void setNumRef (Integer numRef) {
      this.numRef = numRef;
    }
    public String getReason () {
      return reason;
    }
    public void setReason (String reason) {
      this.reason = reason;
    }
    public String getStatus () {
      return status;
    }
    public void setStatus (String status) {
      this.status = status;
    }
    public Integer getOpportunity () {
      return opportunity;
    }
    public String getDisplayName () {
      return displayName;
    }
    public int getSortOrder () {
      return sortOrder;
    }
    public void setOpportunity (Integer opportunity) {
      this.opportunity = opportunity;
    }
    public void setDisplayName (String displayName) {
      this.displayName = displayName;
    }
    public void setSortOrder (int sortOrder) {
      this.sortOrder = sortOrder;
    }
    public String getTestKey () {
      return testKey;
    }
    public String getTestId () {
      return testId;
    }
    public String getGrade () {
      return grade;
    }
    public String getSubject () {
      return subject;
    }
    public String getEnroll () {
      return enroll;
    }
    public String getMode () {
      return mode;
    }
    public UUID getSession () {
      return session;
    }
    public String getWindowid () {
      return windowid;
    }
    public Integer getSessionType () {
      return sessionType;
    }
    public Integer getMaxOpps () {
      return maxOpps;
    }
    public Integer getWindowMax () {
      return windowMax;
    }
    public Integer getModeMax () {
      return modeMax;
    }
    public Integer getWindowSession () {
      return windowSession;
    }
    public Integer getModeSession () {
      return modeSession;
    }
    public Long getRtsEquiv () {
      return rtsEquiv;
    }
    
    public void setRtsEquiv (Long rtsEquiv) {
      this.rtsEquiv = rtsEquiv;
    }
    public Date getStartDate () {
      return startDate;
    }
    public Date getEndDate () {
      return endDate;
    }
    public void setTestKey (String testKey) {
      this.testKey = testKey;
    }
    public void setTestId (String testId) {
      this.testId = testId;
    }
    public void setGrade (String grade) {
      this.grade = grade;
    }
    public void setSubject (String subject) {
      this.subject = subject;
    }
    public void setEnroll (String enroll) {
      this.enroll = enroll;
    }
    public void setMode (String mode) {
      this.mode = mode;
    }
    public void setSession (UUID session) {
      this.session = session;
    }
    public void setWindowid (String windowid) {
      this.windowid = windowid;
    }
    public void setSessionType (Integer sessionType) {
      this.sessionType = sessionType;
    }
    public void setMaxOpps (Integer maxOpps) {
      this.maxOpps = maxOpps;
    }
    public void setWindowMax (Integer windowMax) {
      this.windowMax = windowMax;
    }
    public void setModeMax (Integer modeMax) {
      this.modeMax = modeMax;
    }
    public void setWindowSession (Integer windowSession) {
      this.windowSession = windowSession;
    }
    public void setModeSession (Integer modeSession) {
      this.modeSession = modeSession;
    }
    public void setStartDate (Date startDate) {
      this.startDate = startDate;
    }
    public void setEndDate (Date endDate) {
      this.endDate = endDate;
    }
    
    public String getClientName () {
      return clientName;
    }
    public void setClientName (String clientName) {
      this.clientName = clientName;
    }
    
  }
  
  public SingleDataResultSet  T_GetEligibleTests_SP (SQLConnection connection, Long testee, UUID sessionKey, String grade) throws ReturnStatusException {
    
    SingleDataResultSet returnResultSet = new SingleDataResultSet ();
    Date startTime = _dateUtil.getDateWRetStatus (connection);
    String clientName = null;
    Integer sessionType = null;
    List<EligibleTest> returnTestList = new ArrayList<EligibleTest> ();
    
    //Getting sessiontype and clientName
    final String SQL_QUERY1 = "select sessionType, clientname from session where _Key = ${sessionKey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      sessionType = record.<Integer> get ("sessionType");
      clientName = record.<String> get ("clientname");
    }
    
    //Getting Tests from ART
    Set<String> rtsTests = new HashSet<String> ();
    if(testee>=0) {
      _Ref<String> refRTSTests = new _Ref<String> ();
      _rtsDll._GetRTSAttribute_SP (connection, clientName, testee, "--ELIGIBLETESTS--", refRTSTests);
      
      if (refRTSTests.get() != null){
        String rows[] = refRTSTests.get ().split (";");
        for(String row:rows) {
          rtsTests.add (row.trim ());
        }
      }
    }
    
    
    
    //Get EligibleTests for the currentSession
    result = _GetCurrentTests (connection, sessionKey);
    Iterator<DbResultRecord> recordsIter = result.getRecords ();
    List<EligibleTest> eligibleTests = new ArrayList<EligibleTest> ();
    StringBuilder sbTestIds = new StringBuilder ();    
    while(recordsIter.hasNext ()) {
      record = recordsIter.next ();
      
      //If guest session and testid do not match with the one in the ART than do not show that test
      if(testee>=0 && !rtsTests.contains (record.<String>get ("testkey"))) {
        recordsIter.remove ();
        continue;
      }
      
      //Eliminate those that are not in a compatible grade.
      if (__IsGradeEquiv_FN (record.<String> get ("grade"), grade) == false) {
        recordsIter.remove ();
        continue;
      }
      
      //Creating EligibleTest object
      EligibleTest test = new EligibleTest();
      test.setTestKey (record.<String>get ("testkey"));
      test.setTestId (record.<String>get ("testid"));
      test.setGrade (record.<String>get ("grade"));
      test.setSubject (record.<String>get ("subject"));
      test.setEnroll (record.<String>get ("enroll"));
      test.setMaxOpps(record.<Integer>get ("maxopps"));
      test.setRtsEquiv (record.<Long>get ("rtsequiv"));
      test.setMode (record.<String>get ("mode"));
      test.setSession (record.<UUID>get ("session"));
      test.setWindowMax (record.<Integer>get ("windowmax"));
      test.setStartDate (record.<Date>get ("startdate"));
      test.setEndDate (record.<Date>get ("enddate"));
      test.setWindowid (record.<String>get ("windowid"));
      test.setModeMax (record.<Integer>get ("modemax"));
      test.setWindowSession (record.<Integer>get ("windowsession"));
      test.setModeSession (record.<Integer>get ("modesession"));
      test.setSessionType (sessionType);
      test.setDisplayName (record.<String>get ("displayName"));
      test.setSortOrder (record.<Integer>get ("sortOrder"));
      test.setClientName(clientName);
      eligibleTests.add (test);
      
      //If sessionType is zero and session is null for the test than update the test with Denied status
      if (DbComparator.isEqual (test.getSessionType (), 0) && sessionKey != null &&
          test.getSession () ==null) {
        test.setOpportunity (0);
        test.setStatus ("denied");
        
        _Ref<String> notAvailRef = new _Ref<> ();
        _commonDll._FormatMessage_SP (connection, clientName, "ENU", "_CanOpenTestOpportunity",
            "Test not available for this session.", notAvailRef);
        
        test.setReason (notAvailRef.get ());
        returnTestList.add (test);
      } else {
        //Created a string for semicolon seperated testIds
        sbTestIds.append (test.getTestId ()).append (";");
      }
    } //End while loop - Iterating through all the tests.
    //Remove the last semicolon from the testIds string
    if(sbTestIds.length ()>1) {
      sbTestIds.replace ( sbTestIds.length ()-1, sbTestIds.length (), "");
    }
    
    //Getting All the Opportunity info for all the eligible tests
     SingleDataResultSet resultSet = _GetOpportunityInfo (connection, clientName,sessionKey, testee, sbTestIds.toString ());
     recordsIter = resultSet.getRecords ();
     //Store oppInfo inside Map for matching it with the opp
     Map<String, DbResultRecord> oppInfoMap = new HashMap<String, DbResultRecord> ();
     while(recordsIter.hasNext ()) {
       DbResultRecord record1 = recordsIter.next ();
       oppInfoMap.put (record1.<String>get("_efk_testid"), record1);
     }
     
     // Get Enviornment Details
    String environment = _commonDll.getExternsColumnByClientName (connection, clientName, "environment");
    _Ref<String> reasonBlockedRef = new _Ref<String> ();
    _Ref<String> testeeSubjectsRef = new _Ref<String> ();
    //If It is not SIMULATION enviornment and not a Guest student than check if _IsOpportunityBlocked
    if (DbComparator.notEqual (environment, "SIMULATION") && DbComparator.greaterThan (testee, 0)) {
        _rtsDll._GetRTSAttribute_SP (connection, clientName, testee, "BLOCKEDSUBJECT", testeeSubjectsRef);
    }
        String subject = null;
        for(EligibleTest test:eligibleTests) {
          Date today = _dateUtil.getDateWRetStatus (connection);
          //Get Opportunity details for the testId
          DbResultRecord oppRecord = oppInfoMap.get (test.getTestId ());
          if (DbComparator.notEqual (environment, "SIMULATION") && DbComparator.greaterThan (testee, 0)) {
            subject = test.getSubject ();
            if (subject != null)
              subject = String.format (";%s;", subject.trim ());
            else
              subject = ";;";
            
            //if the subject is explicitly blocked by the rts attribute BLOCKED, block the opportunity 
            String testeeSubjects = testeeSubjectsRef.get ();
            if (testeeSubjects != null && testeeSubjects.length () > 0) {
        
              testeeSubjects = String.format (";%s;", testeeSubjects.trim ());
              if (testeeSubjects.indexOf (subject) > -1) {
        
                _commonDll._FormatMessage_SP (connection, clientName, "ENU", "_CanOpenTestOpportunity",
                    "This test is administratively blocked. Please check with your test administrator.", reasonBlockedRef);
                test.setReason (reasonBlockedRef.get ());
                continue;
              }
            }
            
            //Check if there is an existing opportunity  _CanOpenExistingOpportunity
            if(oppRecord!=null && oppRecord.<String>get("oppstatus").equalsIgnoreCase ("paused")) {
                Integer numOfOpps = oppRecord.<Integer>get("opportunity");
                Integer maxOpps = test.getMaxOpps ();
                String lastStatusClosedStage = oppRecord.<String>get("laststatus_closedStage");
                String lastStatusInactiveStage = oppRecord.<String>get("laststatus_inactiveStage");
                Integer lastSessionType = oppRecord.<Integer>get("lastsessiontype");
                Integer thisSessionType = oppRecord.<Integer>get("thissessiontype");
                String lastSessionStatus = oppRecord.<String>get("lastsessionstatus");
                Date lastSessionEnd = oppRecord.<Date>get("lastsessionend");
                Date dateChanged = oppRecord.<Date>get("datechanged");
                UUID lastSession = oppRecord.<UUID>get("lastsession");
                
                test.setNumRef (0);
                test.setReason (null);
                
                // last opportunity terminated normally
                if (lastStatusClosedStage!=null) {
                  continue; 
                }
                
                if (DbComparator.notEqual (lastSessionType, thisSessionType)) {
                  _commonDll._FormatMessage_SP (connection, clientName, "ENU", "_CanOpenTestOpportunity",
                      "You must continue the test in the same type of session it was started in.", reasonBlockedRef, null, null, null, null);
                  test.setReason (reasonBlockedRef.get ());
                  continue;
                }
                
                // final security check;
                // if opportunity is not in use, then we can open it
                if (lastStatusInactiveStage==null) {
                  test.setNumRef (numOfOpps);
                  continue; 
                }
                // else check for possible opportunity 'hijacking' of in use opportunity
                // if at least 1 day has elapsed since last activity, or
                // is resuming in same session as previous, or
                // last session is closed
                // then allow resume
                if (sessionKey == null)
                  continue; 
                
                //Check if the number of opportunities in the current window is already hit (windowmax)
                if(numOfOpps == maxOpps) { 
                  _commonDll._FormatMessage_SP (connection, clientName, "ENU", "_CanOpenTestOpportunity", "All opportunities have been used for this test", reasonBlockedRef);
                  test.setReason (reasonBlockedRef.get ());
                  continue;
                }
                
                //Check whether the time elapsed since the last opportunity is sufficient
                if (DbComparator.greaterOrEqual (daysDiff (dateChanged, today), 1) || DbComparator.isEqual (lastSession, sessionKey)
                    || DbComparator.isEqual ("closed", lastSessionStatus) || DbComparator.greaterThan (today, lastSessionEnd)) {
                  test.setNumRef (numOfOpps);
                  continue;
                } 
            } 
            // If OpenexistingOpportunity is executed and teststatus is paused than continue with the next test
            if (DbComparator.greaterThan (test.getNumRef (), 0) || test.getReason () != null) {
              continue;
            }
          } //If not Guest or Simulation - IsOpportunity
            //_Can Open New Opportunity 
            test.setNumRef (0);
            test.setReason (null);
            //If no opportunity found than test is eligible for new opportunity
            if(oppRecord ==null) {
              if (DbComparator.lessThan (0, test.getMaxOpps ()) || DbComparator.isEqual ("SIMULATION", environment)) {
                test.setNumRef (1);
              }
              else if (DbComparator.notEqual ("SIMULATION", environment)) {
                _commonDll._FormatMessage_SP (connection, clientName, "ENU", "_CanOpenTestOpportunity", "No opportunities are available for this test", reasonBlockedRef, null, null, null, null);
                test.setReason (reasonBlockedRef.get ());
              }
              if (DbComparator.greaterThan (test.getNumRef (), 0)) {
                test.setNewoppRef (true);
              }
              continue;
            } 
            //If opportunity is found and teststatus is not paused
            else {
                  String lastStatusClosedStage = oppRecord.<String>get("laststatus_closedStage");
                  Integer numOfOpps = oppRecord.<Integer>get("opportunity");
                  Integer delayDays = oppRecord.<Integer>get("delaydays");
                  Date dateCompleted = oppRecord.<Date>get("datecompleted");
                  if (lastStatusClosedStage!=null) {
                    if (DbComparator.isEqual ("SIMULATION", environment)) {
                      test.setNumRef (numOfOpps + 1);
                      if (DbComparator.greaterThan (test.getNumRef (), 0)) {
                        test.setNewoppRef (true);
                      }
                      continue;
                    }
                  }
                  Integer dDiff = daysDiff (dateCompleted, today);
                  if (DbComparator.lessThan (numOfOpps, test.getMaxOpps ()) && (dateCompleted == null || DbComparator.greaterThan (dDiff, delayDays))) {
                    test.setNumRef (numOfOpps + 1);
                  }
                  else if (DbComparator.greaterOrEqual (numOfOpps, test.getMaxOpps ())) {
                    _commonDll._FormatMessage_SP (connection, clientName, "ENU", "_CanOpenTestOpportunity", "All opportunities have been used for this test", reasonBlockedRef, null, null, null, null);
                    test.setReason (reasonBlockedRef.get ());
                  } else {
                    String arg = null;
      
                    if (dateCompleted != null) {
                      Calendar c = Calendar.getInstance ();
                      c.setTime (dateCompleted);
                      c.add (Calendar.DATE, delayDays);
                      Date dateCompldteddAdjusted = c.getTime ();
                      arg = dateCompldteddAdjusted.toString ();
                    }
                  _commonDll._FormatMessage_SP (connection, clientName, "ENU", "_CanOpenTestOpportunity", "Your next test opportunity is not yet available.", reasonBlockedRef, arg, null, null, null);
                  test.setReason (reasonBlockedRef.get ());
                  }
              }
            if (DbComparator.greaterThan (test.getNumRef (), 0)) {
              test.setNewoppRef (true);
            }
        } //End for loop for existingTests
    
    if (DbComparator.isEqual (sessionType, 1)) {
      
      Iterator<EligibleTest> iTests = eligibleTests.iterator ();
      while(iTests.hasNext ()) {
        EligibleTest test = iTests.next ();
        if(test.getStatus ()!=null && test.getStatus().equals ("NA")){
          iTests.remove ();
        }
        final String SQL_QUERY_INSERT1 = "insert into sessiontests (_fk_Session, _efk_AdminSubject, _efk_TestID) "
            + " values(${sessionKey},${testKey},${testId})";
        SqlParametersMaps parms2 = (new SqlParametersMaps ());
        parms2.put ("sessionKey", sessionKey);
        parms2.put ("testKey", test.getTestKey ());
        parms2.put ("testId", test.getTestId ());
  
        executeStatement (connection, SQL_QUERY_INSERT1, parms2, false).getUpdateCount ();
      }
    }
    Iterator<EligibleTest> iTests = eligibleTests.iterator ();
    List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
    while(iTests.hasNext ()) {
      EligibleTest test = iTests.next();
      CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
      rcd.put ("displayName", test.getDisplayName ());
      rcd.put ("testkey", test.getTestKey ());
      rcd.put ("test", test.getTestId ());
      rcd.put ("opportunity", test.getNumRef ());
      rcd.put ("mode", test.getMode ());
      rcd.put ("maxopps", test.getMaxOpps ());
      rcd.put ("subject", test.getSubject ());
      rcd.put ("grade",grade );
      
      String status = null;
      if (DbComparator.isEqual (test.getNumRef (), 0))
        status = "denied";
      else if (DbComparator.isEqual (test.getNumRef (), -1))
        status = "N/A";
      else if (DbComparator.isEqual (test.getNewoppRef (), false))
        status = "suspended";
      else
        status = "pending";
      
      rcd.put ("status", status);
      rcd.put ("reason", test.getReason ());
      resultList.add (rcd);
    }
    returnResultSet.addColumn ("displayName", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    returnResultSet.addColumn ("testkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    returnResultSet.addColumn ("test", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    returnResultSet.addColumn ("opportunity", SQL_TYPE_To_JAVA_TYPE.INT);
    returnResultSet.addColumn ("mode", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    returnResultSet.addColumn ("maxopps", SQL_TYPE_To_JAVA_TYPE.INT);
    returnResultSet.addColumn ("sortOrder", SQL_TYPE_To_JAVA_TYPE.INT);
    returnResultSet.addColumn ("subject", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    returnResultSet.addColumn ("grade", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    returnResultSet.addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    returnResultSet.addColumn ("reason", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    
    returnResultSet.addRecords (resultList);
    
    _commonDll._LogDBLatency_SP (connection, "T_GetEligibleTests", startTime, testee, true, null, null, null, clientName, null);
    
    return returnResultSet;
  }
  
  
  private SingleDataResultSet _GetCurrentTests(SQLConnection connection, UUID sessionKey) throws ReturnStatusException {
    final String SQL_QUERY = "call _getcurrenttests (${sessionKey})";
    SqlParametersMaps params = new SqlParametersMaps ();
    params.put ("sessionKey", sessionKey);

    SingleDataResultSet resultSet = executeStatement (connection, SQL_QUERY, params, false).getResultSets ().next ();
    return resultSet;
  }
  
  private SingleDataResultSet _GetOpportunityInfo(SQLConnection connection, String clientName, UUID sessionId,Long testee, String testList) throws ReturnStatusException {
    final String SQL_QUERY = "call _getopportunityinfo (${clientName}, ${sessionId}, ${testee},${testList})";
    SqlParametersMaps params = new SqlParametersMaps ();
    params.put ("clientName", clientName);
    params.put ("testee", testee);
    params.put ("testList", testList);
    params.put ("sessionId", sessionId);
    
    SingleDataResultSet resultSet = executeStatement (connection, SQL_QUERY, params, false).getResultSets ().next ();
    return resultSet;
  }
  
  
  /**
   * @param SQLConnection
   *          the database connection
   * @param oppkey
   *          the test opportunity UUID
   * @param session
   *          the session UUID
   * @param browserID
   *          the browser UUID
   * 
   * @return SingleDataResultSet containing the segments for the specified Test
   *         Opportunity.
   * 
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_GetOpportunityItemsWithValidation_SP (SQLConnection connection, UUID oppkey, UUID session, UUID browserID) throws ReturnStatusException {
    SingleDataResultSet result = null;
    Date starttime = _dateUtil.getDateWRetStatus (connection);

    _Ref<String> validatedRef = new _Ref<String> ();
    _ValidateTesteeAccessProc_SP (connection, oppkey, session, browserID, true, validatedRef);
    if (validatedRef.get () != null) {
      ReturnErrorArgs args = new ReturnErrorArgs (connection);
      args.setProcName ("T_GetOpportunityItemsWithValidation");
      args.setAppKey (validatedRef.get ());
      args.setOppKey (oppkey);
      args.setContext ("_ValidateTesteeAccess");
      args.setStatus ("InvalidAccess");
      return _commonDll._ReturnError_SP (args);
    }

    final String query1 = "select status from testopportunity where _Key = ${oppkey} ";
    SqlParametersMaps params1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);
    SingleDataResultSet result1 = executeStatement (connection, query1, params1, false).getResultSets ().next ();
    DbResultRecord record1 = result1.getCount () > 0 ? result1.getRecords ().next () : null;
    String status = null;
    if (record1 != null) {
      status = record1.<String> get ("status");
    }

    if ((!"started".equals (status)) && (!"review".equals (status))) {
      ReturnErrorArgs args = new ReturnErrorArgs (connection);
      args.setProcName ("T_GetOpportunityItemsWithValidation");
      args.setAppKey ("Test opportunity is not available for viewing");
      args.setOppKey (oppkey);
      args.setContext ("T_GetOpportunityItemsWithValidation");
      args.setStatus ("InvalidAccess");
      return _commonDll._ReturnError_SP (args);
    }

    result = T_GetOpportunityItems_SP (connection, oppkey);

    LogDBLatencyArgs args = new LogDBLatencyArgs (connection);
    args.setProcName ("T_GetOpportunityItemsWithValidation_SP");
    args.setStartTime (starttime);
    args.setCheckAudit (true);
    args.setTestOppKey (oppkey);
    _commonDll._LogDBLatency_SP (args);

    return result;
  }

  /**
   * -- Description: Determine whether the user can open each of several test
   * opportunities lists in tests -- This procedure DOES NOT validate that a
   * test is appropriate for the testee. Only that he is allowed to open -- an
   * opp under the rules of expiration dates and max tries.
   * 
   * @param connection
   * @param testee
   * @param sessionKey
   * @param grade
   * @return
   * @throws ReturnStatusException
   */
public SingleDataResultSet T_GetEligibleTests_SP_Old (SQLConnection connection, Long testee, UUID sessionKey, String grade) throws ReturnStatusException {
    Date starttime = _dateUtil.getDateWRetStatus (connection);
    long startTime = System.currentTimeMillis ();
    Integer sessionType = null;
    String clientname = null;

    final String SQL_QUERY1 = "select sessionType, clientname from session where _Key = ${sessionKey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      sessionType = record.<Integer> get ("sessionType");
      clientname = record.<String> get ("clientname");
    }

    DataBaseTable eligibleTbl = getDataBaseTable ("eligible").addColumn ("testKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).
        addColumn ("testId", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 350).addColumn ("maxOpps", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("mode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50);

    DataBaseTable resultTbl = getDataBaseTable ("rslt").addColumn ("testKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).
        addColumn ("test", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200).addColumn ("opportunity", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("mode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).
        addColumn ("displayName", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).addColumn ("maxOpps", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("subject", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("grade", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).
        addColumn ("sortOrder", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).
        addColumn ("reason", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 2000);
    connection.createTemporaryTable (resultTbl);
    final String clientnameFinal = clientname;
    final Long testeeFinal = testee;
    final Integer sessionTypeFinal = sessionType;
    final String gradeFinal = grade;
    executeMethodAndInsertIntoTemporaryTable (connection, new AbstractDataResultExecutor ()
    {
      @Override
      public SingleDataResultSet execute (SQLConnection connection) throws ReturnStatusException {

        SingleDataResultSet resultSet = __EligibleTests_SP (connection, clientnameFinal, testeeFinal, sessionTypeFinal, gradeFinal, false);

        return resultSet;
      }
    }, eligibleTbl, true);
    // -- maxopps is for the logical test (TestID) as opposed to the physical
    // (or mode) test (testkey)
    // -- hence, all testkeys of the same testID will have the same value for
    // maxopps
    _Ref<String> notAvailRef = new _Ref<> ();
    _commonDll._FormatMessage_SP (connection, clientname, "ENU", "_CanOpenTestOpportunity",
        "Test not available for this session.", notAvailRef);
    final String SQL_QUERY2 = "select  testkey, testID as testname, maxopps, mode from ${eligibleTblName}  limit 1";
    Map<String, String> unquotedParms2 = new HashMap<> ();
    unquotedParms2.put ("eligibleTblName", eligibleTbl.getTableName ());

    final String SQL_QUERY_DELETE3 = "delete from ${eligibleTblName} where testkey = ${testkey}";
    Map<String, String> unquotedParms3 = unquotedParms2;

    final String SQL_QUERY4 = "select  _fk_Session from sessiontests where _fk_Session = ${sessionKey} and _efk_TestID = ${testname}  limit 1";
    
    final String SQL_QUERY_INSERT5 = "insert into ${resultTblName} (testkey, test, opportunity, displayName, sortOrder, subject, grade, status, reason, mode, maxopps)"
        + " select ${testkey}, ${testname}, 0, label, sortOrder, subjectname, gradeText, 'denied', ${notAvail}, ${mode}, ${maxopps} "
        + " from ${ConfigDB}.client_testproperties where clientname = ${clientname} and testID = ${testname}";
    Map<String, String> unquotedParms5 = new HashMap<> ();
    unquotedParms5.put ("resultTblName", resultTbl.getTableName ());
    final String query5 = fixDataBaseNames (SQL_QUERY_INSERT5);

    final String SQL_QUERY_INSERT6 = "insert into ${resultTblName} (testkey, test, opportunity, mode, maxopps, displayName, sortOrder, "
        + " subject, grade, status, reason) "
        + " select ${testkey}, ${testname}, ${oppnum}, ${mode}, ${maxopps}, label, sortOrder, subjectname, gradeText, ${status}, ${reason} "
        + " from ${ConfigDB}.client_testproperties where clientname = ${clientname} and testID = ${testname}";
    Map<String, String> unquotedParms6 = new HashMap<> ();
    unquotedParms6.put ("resultTblName", resultTbl.getTableName ());
    final String query6 = fixDataBaseNames (SQL_QUERY_INSERT6);

    final String SQL_QUERY7 = "select  P.testID from ${ConfigDB}.client_testprerequisite P "
        + " where P.testID = ${testname} and P.clientname = ${clientname} and P.isActive = 1 limit 1";
    final String query7 = fixDataBaseNames (SQL_QUERY7);
    String testkey = null, testname = null, mode = null;
    Integer maxopps = null;
    _Ref<Boolean> newoppRef = new _Ref<> ();
    _Ref<Integer> oppnumRef = new _Ref<> ();
    _Ref<String> reasonRef = new _Ref<> ();
    while (true) {
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY2, unquotedParms2), null, false).getResultSets ().next ();
      if (result.getCount () == 0)
        break;

      record = result.getRecords ().next ();
      testkey = record.<String> get ("testkey");
      testname = record.<String> get ("testname");
      mode = record.<String> get ("mode");
      maxopps = record.<Integer> get ("maxopps");

      SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("testkey", testkey);
      executeStatement (connection, fixDataBaseNames (SQL_QUERY_DELETE3, unquotedParms3), parms3, false).getUpdateCount ();
      SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey).put ("testname", testname);
      if (DbComparator.isEqual (sessionType, 0) && sessionKey != null &&
          exists (executeStatement (connection, SQL_QUERY4, parms4, false)) == false) {

        SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("testkey", testkey).put ("testname", testname).put ("notAvail", notAvailRef.get ()).
            put ("mode", mode).put ("maxopps", maxopps).put ("clientname", clientname);
        executeStatement (connection, fixDataBaseNames (query5, unquotedParms5), parms5, false).getUpdateCount ();
      } else {
        _CanOpenTestOpportunity_SP (connection, clientname, testee, testkey, sessionKey, maxopps, newoppRef, oppnumRef, reasonRef);
        
        String status = null;
        if (DbComparator.isEqual (oppnumRef.get (), 0))
          status = "denied";
        else if (DbComparator.isEqual (oppnumRef.get (), -1))
          status = "N/A";
        else if (DbComparator.isEqual (newoppRef.get (), false))
          status = "suspended";
        else
          status = "pending";

        SqlParametersMaps parms6 = (new SqlParametersMaps ()).put ("testkey", testkey).put ("testname", testname).put ("oppnum", oppnumRef.get ()).
            put ("mode", mode).put ("maxopps", maxopps).put ("status", status).put ("reason", reasonRef.get ()).put ("clientname", clientname);
        executeStatement (connection, fixDataBaseNames (query6, unquotedParms6), parms6, false).getUpdateCount ();
        SqlParametersMaps parms7 = (new SqlParametersMaps ()).put ("testname", testname).put ("clientname", clientname);
        // -- Some tests have other tests as prerequisites for which the student
        // may not be naturally eligible.
        // -- Add these tests and run them through normal _CanOpen process
        if (DbComparator.notEqual (status, "N/A") && exists (executeStatement (connection, query7, parms7, false))) {
          // TODO: EF per Sai changed original statement to skip 'not
          // exists(...from ${eligibleTblName})...'
          // and substituted it with 'ignore'. Reason: mysql does not allow tmp
          // table to be used twice in
          // insert statement
          final String SQL_QUERY_INSERT8 = "insert ignore into ${eligibleTblName} (testkey, testID, maxopps, mode) "
              + " select M.testkey, M.testID, T.maxopportunities, M.mode "
              + " from ${ConfigDB}.client_testprerequisite P, ${ConfigDB}.client_testmode M, ${ConfigDB}.client_testproperties T "
              + " where P.clientname = ${clientname} and M.ClientName = ${clientname} and T.clientname = ${clientname} "
              + "     and P.testID = ${testname} and P.prereqTestID = M.testID and T.testID = P.prereqTestID "
              + "     and P.isActive = 1 "
              // +
              // "     and not exists (select * from ${eligibleTblName} E2 where E2.testkey = M.testkey) "
              + "     and not exists (select * from ${resultTblName} R where M.testkey = R.testkey)";
          Map<String, String> unquotedParms8 = new HashMap<> ();
          unquotedParms8.put ("eligibleTblName", eligibleTbl.getTableName ());
          unquotedParms8.put ("resultTblName", resultTbl.getTableName ());
          SqlParametersMaps parms8 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testname", testname);

          final String query8 = fixDataBaseNames (SQL_QUERY_INSERT8);
          executeStatement (connection, fixDataBaseNames (query8, unquotedParms8), parms8, false).getUpdateCount ();
        }
      }
    }
    // -- prerequisite tests need to be forced into the session's tests, so just
    // ensure they are all there
    if (DbComparator.isEqual (sessionType, 1)) {

      final String SQL_QUERY_INSERT9 = "insert into sessiontests (_fk_Session, _efk_AdminSubject, _efk_TestID) "
          + " select ${sessionKey}, testkey, test from ${resultTblName} "
          + " where status <> 'NA' and not exists (select * from sessiontests where _fk_Session = ${sessionKey} and _efk_AdminSUbject = testkey and _efk_TestID = test)";
      Map<String, String> unquotedParms9 = new HashMap<> ();
      unquotedParms9.put ("resultTblName", resultTbl.getTableName ());
      SqlParametersMaps parms9 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey);

      executeStatement (connection, fixDataBaseNames (SQL_QUERY_INSERT9, unquotedParms9), parms9, false).getUpdateCount ();
      final String SQL_QUERY_DELETE10 = "delete from ${resultTblName} where status = 'NA'";
      Map<String, String> unquotedParms10 = new HashMap<> ();
      unquotedParms10.put ("resultTblName", resultTbl.getTableName ());

      executeStatement (connection, fixDataBaseNames (SQL_QUERY_DELETE10, unquotedParms10), null, false).getUpdateCount ();

    }
    final String SQL_QUERY11 = "select * from ${resultTblName} order by sortOrder";
    Map<String, String> unquotedParms11 = new HashMap<> ();
    unquotedParms11.put ("resultTblName", resultTbl.getTableName ());

    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY11, unquotedParms11), null, false).getResultSets ().next ();
    connection.dropTemporaryTable (eligibleTbl);
    connection.dropTemporaryTable (resultTbl);
    _commonDll._LogDBLatency_SP (connection, "T_GetEligibleTests", starttime, testee, true, null, null, null, clientname, null);
    //_logger.info ("<<<<<<<<< T_GetEligibleTests_SP Total Execution Time : "+((System.currentTimeMillis ()-startTime)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    return result;
  }

  public SingleDataResultSet S_InsertTestScores_SP (SQLConnection connection, UUID oppkey, String scoreString, Character rowdelim, Character coldelim)
      throws ReturnStatusException {

    _Ref<String> errorRef = new _Ref<> ();
    _InsertTestoppScores_SP (connection, oppkey, scoreString, coldelim, rowdelim, errorRef);

    if (errorRef.get () != null)
      return _commonDll._ReturnError_SP (connection, null, "S_InsertTestoppScores", errorRef.get (), null, oppkey, null);

    // TODO Elena testing
    // long st = System.currentTimeMillis();
    _commonDll.SetOpportunityStatus_SP (connection, oppkey, "scored", true);
    // long diff = System.currentTimeMillis() - st;
    return _commonDll.ReturnStatusReason ("success", null);

  }

  // Original port
  public SingleDataResultSet S_GetScoreItems_SP (SQLConnection connection, String clientname, Integer pendingMinutes, Integer minAttempts, Integer maxAttempts, Integer sessionType)
      throws ReturnStatusException {
    // -- this procedure is called by the item scoring engine to pick up a
    // backlog of items that require scoring
    // -- It is needed to recover in case of a scoring engine failure
    // -- this procedure attempts to guard against race conditions providing
    // duplicate items to different scoring engines
    // -- by marking
    Date now = _dateUtil.getDateWRetStatus (connection);
    Date before = _commonDll.adjustDate (now, -1 * pendingMinutes, Calendar.MINUTE);
    UUID scoremark = UUID.randomUUID ();

    DataBaseTable itemsTbl = getDataBaseTable ("items").addColumn ("oppkey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER).
        addColumn ("testkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).addColumn ("pos", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("attempts", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (itemsTbl);

    final String SQL_INSERT1 = "insert into ${itemsTblName} (oppkey, testkey, pos, attempts) "
        + " select   _fk_TestOpportunity, _efk_AdminSubject, position, scoreAttempts "
        + "  from testeeresponsescore R, testopportunity O, session S "
        + "    where O.clientname = ${clientName} and ScoreStatus = 'WaitingForMachineScore'"
        + "   and (R.scoringDate is null or R.scoringDate < ${before}) and scoreAttempts between ${minAttempts} and ${maxAttempts} "
        + "   and _fk_TestOpportunity = O._Key and _fk_Session = S._Key and S.sessionType = ${sessionType} order by R.scoringDate limit 20";
    Map<String, String> unquotedParms1 = new HashMap<> ();
    unquotedParms1.put ("itemsTblName", itemsTbl.getTableName ());
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("before", before).put ("minAttempts", minAttempts).
        put ("maxAttempts", maxAttempts).put ("sessionType", sessionType);

    int N = executeStatement (connection, fixDataBaseNames (SQL_INSERT1, unquotedParms1), parms1, true).getUpdateCount ();

    // -- mark the ones that have not been grabbed by someone else while we
    // weren't looking
    // -- the update is an atomic action by the DBMS
    final String SQL_UPDATE2 = "update testeeresponsescore T, ${itemsTblName} set T.scoreMark = ${scoremark}, T.scoringDate = ${now}, T.scoreAttempts = scoreAttempts + 1 "
        + "   where _fk_TestOpportunity = oppkey and ScoreStatus ='WaitingForMachineScore' "
        + "  and position = pos and (scoringDate is null or scoringDate < ${before})";
    Map<String, String> unquotedParms2 = unquotedParms1;
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("scoremark", scoremark).put ("now", now).put ("before", before);

    executeStatement (connection, fixDataBaseNames (SQL_UPDATE2, unquotedParms2), parms2, false).getUpdateCount ();

    // -- now return the ones that we succeeded in marking. Their new
    // scoringDate should make them immune to other callers
    final String SQL_QUERY3 = "select distinct oppkey, testkey, R.position, R.ResponseSequence, _efk_ITSBank as BankKey, "
        + " _efk_ITSItem as ItemKey, response, ${scoremark} as scoremark "
        + "  ,AccCode as Language, attempts, R.segmentID, _efk_ItemKey "
        + " from testeeresponse R, testeeaccommodations A, ${itemsTblName} I, testeeresponsescore S "
        + " where R._fk_TestOpportunity = oppkey and R.position = pos and A._fk_TestOpportunity = oppkey "
        + "    and S.scoremark = ${scoremark} and S._fk_TestOpportunity = oppkey and S.position = R.position and S.ResponseSequence = R.ResponseSequence "
        + "    and A.AccType = 'Language'";
    Map<String, String> unquotedParms3 = unquotedParms1;
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("scoremark", scoremark);

    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY3, unquotedParms3), parms3, true).getResultSets ().next ();
    result.addColumn ("itemFile", SQL_TYPE_To_JAVA_TYPE.VARCHAR);

    Iterator<DbResultRecord> records = result.getRecords ();
    while (records.hasNext ()) {
      DbResultRecord record = records.next ();
      String itemKey = record.<String> get ("_efk_ItemKey");

      String itemFile = _commonDll.ClientItemFile_FN (connection, clientname, itemKey);
      record.addColumnValue ("itemFile", itemFile);
    }
    connection.dropTemporaryTable (itemsTbl);
    _commonDll._LogDBLatency_SP (connection, "S_GetScoreItems", now, 0L, true, N, null, null, clientname, null);
    return result;
  }

  // Optimization attempt
  // Need to reconsider
  public SingleDataResultSet S_GetScoreItems_SPOpt (SQLConnection connection, String clientname, Integer pendingMinutes, Integer minAttempts, Integer maxAttempts, Integer sessionType)
      throws ReturnStatusException {
    // -- this procedure is called by the item scoring engine to pick up a
    // backlog of items that require scoring
    // -- It is needed to recover in case of a scoring engine failure
    // -- this procedure attempts to guard against race conditions providing
    // duplicate items to different scoring engines
    // -- by marking
    Date now = _dateUtil.getDateWRetStatus (connection);
    Date before = _commonDll.adjustDate (now, -1 * pendingMinutes, Calendar.MINUTE);
    UUID scoremark = UUID.randomUUID ();

    DataBaseTable itemsTbl = getDataBaseTable ("items").addColumn ("oppkey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER).
        addColumn ("testkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).addColumn ("pos", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("attempts", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (itemsTbl);

    final String SQL_INSERT1 = "insert into ${itemsTblName} (oppkey, testkey, pos, attempts) "
        + " select   _fk_TestOpportunity, _efk_AdminSubject, position, scoreAttempts "
        + "  from testeeresponsescore R, testopportunity O, session S "
        + "    where O.clientname = ${clientName} and ScoreStatus = 'WaitingForMachineScore'"
        + "   and (R.scoringDate is null or R.scoringDate < ${before}) and scoreAttempts between ${minAttempts} and ${maxAttempts} "
        + "   and _fk_TestOpportunity = O._Key and _fk_Session = S._Key and S.sessionType = ${sessionType} order by R.scoringDate limit 20";
    Map<String, String> unquotedParms1 = new HashMap<> ();
    unquotedParms1.put ("itemsTblName", itemsTbl.getTableName ());
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("before", before).put ("minAttempts", minAttempts).
        put ("maxAttempts", maxAttempts).put ("sessionType", sessionType);

    int N = executeStatement (connection, fixDataBaseNames (SQL_INSERT1, unquotedParms1), parms1, true).getUpdateCount ();

    // -- mark the ones that have not been grabbed by someone else while we
    // weren't looking
    // -- the update is an atomic action by the DBMS
    final String SQL_UPDATE2 = "update testeeresponsescore T, ${itemsTblName} set T.scoreMark = ${scoremark}, T.scoringDate = ${now}, T.scoreAttempts = scoreAttempts + 1 "
        + "   where _fk_TestOpportunity = oppkey and ScoreStatus ='WaitingForMachineScore' "
        + "  and position = pos and (scoringDate is null or scoringDate < ${before})";
    Map<String, String> unquotedParms2 = unquotedParms1;
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("scoremark", scoremark).put ("now", now).put ("before", before);

    executeStatement (connection, fixDataBaseNames (SQL_UPDATE2, unquotedParms2), parms2, false).getUpdateCount ();

    // -- now return the ones that we succeeded in marking. Their new
    // scoringDate should make them immune to other callers
    final String SQL_QUERY3 = "select distinct oppkey, testkey, R.position, R.ResponseSequence, _efk_ITSBank as BankKey, "
        + " _efk_ITSItem as ItemKey, response, ${scoremark} as scoremark "
        + "  ,AccCode as Language, attempts, R.segmentID, _efk_ItemKey "
        + ", (select concat(C.Homepath, B.HomePath, B.ItemPath, I.FilePath, I.FileName) "
        + "   from ${ItemBankDB}.tblitembank B, ${ItemBankDB}.tblclient C, ${ItemBankDB}.tblitem I "
        + "   where B._efk_Itembank = I._efk_ItemBank and C.name = ${clientName}"
        + "   and B._fk_Client = C._Key and I._Key = _efk_ItemKey) as itemFile "
        + " from testeeresponse R, testeeaccommodations A, ${itemsTblName} I, testeeresponsescore S "
        + " where R._fk_TestOpportunity = oppkey and R.position = pos and A._fk_TestOpportunity = oppkey "
        + "    and S.scoremark = ${scoremark} and S._fk_TestOpportunity = oppkey and S.position = R.position and S.ResponseSequence = R.ResponseSequence "
        + "    and A.AccType = 'Language'";
    Map<String, String> unquotedParms3 = unquotedParms1;
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("scoremark", scoremark).put ("clientname", clientname);

    String query = fixDataBaseNames (SQL_QUERY3); // to substitute ${ItemBankDB}
    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (query, unquotedParms3), parms3, true).getResultSets ().next ();

    connection.dropTemporaryTable (itemsTbl);
    _commonDll._LogDBLatency_SP (connection, "S_GetScoreItems", now, 0L, true, N, null, null, clientname, null);
    return result;
  }

  public SingleDataResultSet T_IsTestComplete_SP (SQLConnection connection, UUID oppkey) throws ReturnStatusException {

    final String SQL_QUERY = "call t_istestcomplete (${oppkey})";
    SqlParametersMaps params = new SqlParametersMaps ();
    params.put ("oppkey", oppkey);


    SingleDataResultSet resultSet = executeStatement (connection, SQL_QUERY, params, false).getResultSets ().next ();
    return resultSet;

  }
  
  /**
   * This is the Java version of t_istestcomplete stored procedure. This method is not used now. It is only needed when we want to migrate the Mysql stored procedure t_istestcomplete  to java.
   */
  public SingleDataResultSet T_IsTestComplete_SP_Java (SQLConnection connection, UUID oppkey) throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);
    Long incomplete = 0L;
    final String SQL_QUERY1 = "select count(*) as incomplete from testopportunitysegment where _fk_TestOpportunity = ${oppkey} and IsSatisfied = 0";
    SqlParametersMaps parms = (new SqlParametersMaps ()).put ("oppkey", oppkey);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null)
      incomplete = record.<Long> get ("incomplete");

    boolean isComplete;
    if (incomplete > 0)
      isComplete = false;
    else
      isComplete = true;

    List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
    CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
    rcd.put ("IsComplete", isComplete);
    rcd.put ("segmentsIncomplete", (incomplete == null ? null : incomplete.intValue ()));
    resultList.add (rcd);

    SingleDataResultSet rs = new SingleDataResultSet ();
    rs.addColumn ("isComplete", SQL_TYPE_To_JAVA_TYPE.BIT);
    rs.addColumn ("segmentsIncomplete", SQL_TYPE_To_JAVA_TYPE.INT);

    rs.addRecords (resultList);

    _commonDll._LogDBLatency_SP (connection, "T_IsTestComplete", starttime, null, true, null, oppkey);
    return rs;
  }

  /**
   * @param connection
   * @param oppKey
   * @param which
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet _TestReportScores_SP (SQLConnection connection, UUID oppKey, String which) throws ReturnStatusException {

    SingleDataResultSet result = null;
    String testId = null;
    String clientName = null;
    final String SQL_QUERY1 = "select _efk_TestID as testID, clientname from testopportunity where _Key = ${oppkey};";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("oppkey", oppKey);
    result = executeStatement (connection, SQL_QUERY1, parms1, true).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      testId = record.<String> get ("testID");
      clientName = record.<String> get ("clientname");
    }
    if (DbComparator.isEqual (which, "Proctor")) {
      final String SQL_QUERY2 = "select ReportLabel, value, ReportOrder from testopportunityscores S, ${ConfigDB}.client_testscorefeatures F" +
          " where F.ClientName = ${clientname} and ReportToProctor = 1 and S._fk_TestOpportunity = ${oppkey} and S.IsOfficial = 1" +
          " and S.MeasureOf = F.MeasureOf and S.MeasureLabel = F.MeasureLabel and F.TestID = ${testID} order by reportOrder;";
      String finalQuery = fixDataBaseNames (SQL_QUERY2);
      SqlParametersMaps parms2 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("testID", testId).put ("clientname", clientName);
      result = executeStatement (connection, finalQuery, parms2, false).getResultSets ().next ();
    } else if (DbComparator.isEqual (which, "Testee")) {
      final String SQL_QUERY3 = "select ReportLabel, value, ReportOrder from testopportunityscores S, ${ConfigDB}.client_testscorefeatures F" +
          " where F.ClientName = ${clientname} and ReportToStudent = 1 and S._fk_TestOpportunity = ${oppkey} and S.IsOfficial = 1" +
          " and S.MeasureOf = F.MeasureOf and S.MeasureLabel = F.MeasureLabel and F.TestID = ${testID} order by reportOrder;";
      String finalQuery = fixDataBaseNames (SQL_QUERY3);
      SqlParametersMaps parms3 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("testID", testId).put ("clientname", clientName);
      result = executeStatement (connection, finalQuery, parms3, false).getResultSets ().next ();
    } else if (DbComparator.isEqual (which, "Participation")) {
      final String SQL_QUERY4 = "select ReportLabel, value, ReportOrder from testopportunityscores S, ${ConfigDB}.client_testscorefeatures F" +
          " where F.ClientName = ${clientname} and ReportToParticipation = 1 and S._fk_TestOpportunity = ${oppkey} and S.IsOfficial = 1" +
          " and S.MeasureOf = F.MeasureOf and S.MeasureLabel = F.MeasureLabel and F.TestID = ${testID} order by reportOrder;";
      String finalQuery = fixDataBaseNames (SQL_QUERY4);
      SqlParametersMaps parms4 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("testID", testId).put ("clientname", clientName);
      result = executeStatement (connection, finalQuery, parms4, false).getResultSets ().next ();
    }
    return result;
  }

  /**
   * @param connection
   * @param oppKey
   * @return
   * @throws ReturnStatusException
   */
  public MultiDataResultSet T_GetDisplayScores_SP (SQLConnection connection, UUID oppKey) throws ReturnStatusException {

    List<SingleDataResultSet> resultSets = new ArrayList<SingleDataResultSet> ();
    Boolean score = null;
    Boolean showscores = false; // set @showscores = 0;
    String testId = null;
    String client = null;

    final String SQL_QUERY1 = "select _efk_TestID as testID, clientname as client from testopportunity where _Key = ${oppkey};";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("oppkey", oppKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, true).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      testId = record.<String> get ("testID");
      client = record.<String> get ("client");
    }

    long cnt = 0;
    final String SQL_QUERY2 = "select count(*) as cnt from ${ConfigDB}.client_testscorefeatures where clientname = ${client} and testID = ${testID} and ReportToStudent = 1;";
    String finalQuery = fixDataBaseNames (SQL_QUERY2);
    SqlParametersMaps parms2 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("testID", testId).put ("client", client);
    result = executeStatement (connection, finalQuery, parms2, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      cnt = record.<Long> get ("cnt");
      if (cnt > 0)
        showscores = true;
    }

    score = _commonDll.ScoreByTDS_FN (connection, client, testId);
    final String SQL_QUERY3 = "select  _fk_TestOpportunity from testopportunityscores where _fk_TestOpportunity = ${oppkey} limit 1";
    SqlParametersMaps parms3 = parms1;
    if (DbComparator.isEqual (showscores, true) && exists (executeStatement (connection, SQL_QUERY3, parms3, false))) {
      SingleDataResultSet rs1 = _TestReportScores_SP (connection, oppKey, "Testee");
      resultSets.add (rs1);

      List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
      CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
      rcd.put ("scoreByTDS", score);
      rcd.put ("showscores", showscores);
      rcd.put ("scorestatus", "SCORED");
      resultList.add (rcd);

      SingleDataResultSet rs2 = new SingleDataResultSet ();
      rs2.addColumn ("scoreByTDS", SQL_TYPE_To_JAVA_TYPE.BIT);
      rs2.addColumn ("showscores", SQL_TYPE_To_JAVA_TYPE.BIT);
      rs2.addColumn ("scorestatus", SQL_TYPE_To_JAVA_TYPE.VARCHAR);

      rs2.addRecords (resultList);
      resultSets.add (rs2);

    } else {
      List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
      CaseInsensitiveMap<Object> rcrd = new CaseInsensitiveMap<Object> ();
      rcrd.put ("ReportLabel", null);
      rcrd.put ("value", null);
      rcrd.put ("ReportOrder", null);
      resultList.add (rcrd);

      SingleDataResultSet rs3 = new SingleDataResultSet ();
      rs3.addColumn ("ReportLabel", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      // TODO: what data type are value and ReportOrder?
      rs3.addColumn ("value", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      rs3.addColumn ("ReportOrder", SQL_TYPE_To_JAVA_TYPE.VARCHAR);

      rs3.addRecords (resultList);
      resultSets.add (rs3);

      String scoreStatus = _commonDll.CanScoreOpportunity_FN (connection, oppKey);
      List<CaseInsensitiveMap<Object>> resultList1 = new ArrayList<CaseInsensitiveMap<Object>> ();
      CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
      rcd.put ("scoreByTDS", score);
      rcd.put ("showscores", showscores);
      rcd.put ("scorestatus", scoreStatus);
      resultList1.add (rcd);

      SingleDataResultSet rs4 = new SingleDataResultSet ();
      rs4.addColumn ("scoreByTDS", SQL_TYPE_To_JAVA_TYPE.BIT);
      rs4.addColumn ("showscores", SQL_TYPE_To_JAVA_TYPE.BIT);
      rs4.addColumn ("scorestatus", SQL_TYPE_To_JAVA_TYPE.VARCHAR);

      rs4.addRecords (resultList1);
      resultSets.add (rs4);
    }
    return (new MultiDataResultSet (resultSets));
  }

  /**
   * @param connection
   * @param segment
   * @param oppKey
   * @param sessionKey
   * @param browserKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_ExitSegment_SP (SQLConnection connection, Integer segment, UUID oppKey, UUID sessionKey, UUID browserKey) throws ReturnStatusException {

    Date now = _dateUtil.getDateWRetStatus (connection);
    _Ref<String> error = new _Ref<> ();

    _ValidateTesteeAccessProc_SP (connection, oppKey, sessionKey, browserKey, true, error);
    if (error.get () != null) {
      return _commonDll._ReturnError_SP (connection, null, "T_ExitSegment", error.get (), null, oppKey, null);
    }
    String seg = null;
    seg = String.format ("Exit Segment: %d", segment);

    // String sessionDB = getAppSettings ().get ("TDSSessionDBName");
    String sessionDB = getTdsSettings ().getTDSSessionDBName ();
    final String SQL_INSERT = "insert into ${ArchiveDB}.opportunityaudit (_fk_TestOpportunity, AccessType, _fk_Session, _fK_Browser, dateaccessed, hostname, dbname) "
        + " values (${oppkey}, ${seg}, ${sessionKey}, ${browserKey}, now(3), ${localhost}, ${dbname})";
    String finalQuery = fixDataBaseNames (SQL_INSERT);
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("seg", seg).put ("sessionKey", sessionKey).
        put ("browserKey", browserKey).put ("localhost", _commonDll.getLocalhostName ()).put ("dbname", sessionDB);
    executeStatement (connection, finalQuery, parms1, false).getUpdateCount ();

    final String SQL_UPDATE = "update testopportunitysegment set dateExited = ${now} where _fk_TestOpportunity = ${oppkey} and segmentPosition = ${segment};";
    SqlParametersMaps parms2 = new SqlParametersMaps ().put ("oppkey", oppKey).put ("now", now).put ("segment", segment);
    executeStatement (connection, SQL_UPDATE, parms2, false).getUpdateCount ();

    List<CaseInsensitiveMap<Object>> resultlist = new ArrayList<CaseInsensitiveMap<Object>> ();
    CaseInsensitiveMap<Object> rcrd = new CaseInsensitiveMap<Object> ();
    rcrd.put ("status", "success");
    rcrd.put ("reason", null);
    resultlist.add (rcrd);

    SingleDataResultSet result = new SingleDataResultSet ();
    result.addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    result.addColumn ("reason", SQL_TYPE_To_JAVA_TYPE.VARCHAR);

    result.addRecords (resultlist);

    return result;
  }

  /**
   * @param connection
   * @param segment
   * @param oppKey
   * @param sessionKey
   * @param browserKey
   * @param segmentAccoms
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_ApproveAccommodations_SP (SQLConnection connection, Integer segment, UUID oppKey, UUID sessionKey, UUID browserKey, String segmentAccoms) throws ReturnStatusException {

    UUID oppsession = null;
    String teststatus = null;
    Integer numitems = null;
    _Ref<String> accessdenied = new _Ref<> ();
    String error = null;
    Long testee = null;
    Long proctor = null;
    String client = null;

    Date now = _dateUtil.getDateWRetStatus (connection);
    _ValidateTesteeAccessProc_SP (connection, oppKey, sessionKey, browserKey, true, accessdenied);

    final String SQL_QUERY1 = "SELECT _fk_Session as oppsession, status as teststatus, maxitems as numitems, _efk_Testee as testee, " +
        " clientname as client from testopportunity O where O._Key = ${oppkey} ;";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("oppkey", oppKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, true).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      oppsession = record.<UUID> get ("oppsession");
      teststatus = record.<String> get ("teststatus");
      numitems = record.<Integer> get ("numitems");
      testee = record.<Long> get ("testee");
      client = record.<String> get ("client");
    }
    final String SQL_QUERY2 = "select _efk_Proctor as proctor from session where _Key = ${sessionKey};";
    SqlParametersMaps parms2 = new SqlParametersMaps ().put ("sessionKey", sessionKey);
    result = executeStatement (connection, SQL_QUERY2, parms2, true).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      proctor = record.<Long> get ("proctor");
    }
    if (accessdenied.get () != null) {
      error = accessdenied.get ();
    }
    if (teststatus == null) {
      error = "The test opportunity does not exist";
    }
    if (sessionKey != null && oppsession != null && DbComparator.notEqual (sessionKey, oppsession)) {
      error = "The test opportunity is not enrolled in this session";
    }
    if (proctor != null) {
      error = "Student can only self-approve unproctored sessions";
    }
    if (error != null) {
      _commonDll._LogDBError_SP (connection, "T_ApproveAccommodations", error, null, null, null, oppKey);
      _commonDll._LogDBLatency_SP (connection, "T_ApproveAccommodations", now, null, true, null, oppKey);
      return _commonDll._ReturnError_SP (connection, client, "T_ApproveAccommodations", error);
    }
    _Ref<String> errorRef = new _Ref<> ();
    _commonDll._UpdateOpportunityAccommodations_SP (connection, oppKey, segment, segmentAccoms, numitems, true, false, errorRef);
    if (errorRef.get () != null) {
      _commonDll._LogDBError_SP (connection, "T_ApproveAccommodations", error, null, null, null, oppKey);
      _commonDll._LogDBLatency_SP (connection, "T_ApproveAccommodations", now, null, true, null, oppKey);
      return _commonDll._ReturnError_SP (connection, client, "T_ApproveAccommodations", error);
    }
    _commonDll._LogDBLatency_SP (connection, "T_ApproveAccommodations", now, null, true, null, oppKey);

    return null;
  }

  public boolean AuditScores_FN (SQLConnection connection, String clientname) throws ReturnStatusException {

    Integer flag = null;
    final String SQL_QUERY1 = "select IsOn as flag from ${ConfigDB}.client_systemflags F, externs E "
        + " where E.ClientName=${ClientName} and F.clientname = ${clientname} "
        + "  and E.IsPracticeTest = F.IsPracticeTest and AuditOBject='scores'";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("clientname", clientname);

    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY1), parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null)
      flag = record.<Integer> get ("flag");
    if (flag == null || flag == 0)
      return false;
    else
      return true;
  }

  public void _InsertTestoppScores_SP (SQLConnection connection, UUID testoppkey, String scoreString, _Ref<String> msgRef) throws ReturnStatusException {

    _InsertTestoppScores_SP (connection, testoppkey, scoreString, ',', ';', msgRef);
  }

  // Original port
  public void _InsertTestoppScores_SPV1 (SQLConnection connection, UUID testoppkey, String scoreString, Character coldelim, Character rowdelim,
      _Ref<String> msgRef) throws ReturnStatusException {

    String clientname = null, test = null, subject = null;
    // @scores table (strand varchar(max), measure varchar(max), val
    // varchar(100), se float, ability bigint default 0);
    DataBaseTable scoresTbl = getDataBaseTable ("scores").addColumn ("strand", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 2000).
        addColumn ("measure", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 2000).addColumn ("val", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).
        addColumn ("se", SQL_TYPE_To_JAVA_TYPE.FLOAT).addColumn ("ability", SQL_TYPE_To_JAVA_TYPE.BIGINT);
    connection.createTemporaryTable (scoresTbl);

    try {

      final String SQL_QUERY1 = "select clientname, _efk_TestID as test, Subject from testopportunity where _Key  = ${testoppkey}";
      SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("testoppkey", testoppkey);
      SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
      DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        clientname = record.<String> get ("clientname");
        test = record.<String> get ("test");
        subject = record.<String> get ("subject");
      }

      // -- check the string for rowdelim termination. If not, make it so.
      boolean isOfficial;
      final String SQL_QUERY2 = "select  _fk_TestOpportunity from testeeresponse  "
          + " where  _fk_TestOpportunity = ${testoppkey} and _efk_ITSItem is not null and score = -1 and IsFieldTest = 0 limit 1";
      SqlParametersMaps parms2 = parms1;
      if (exists (executeStatement (connection, SQL_QUERY1, parms1, false)))
        isOfficial = false;
      else
        isOfficial = true;

      String[] rows = _commonDll._BuildTableAsArray (scoreString, rowdelim.toString (), -1);
      for (String row : rows) {
        String[] cols = _commonDll._BuildTableAsArray (row, coldelim.toString (), -1);
        String strand = (cols.length >= 1 ? cols[0] : null);
        String measure = (cols.length >= 2 ? cols[1] : null);
        String val = (cols.length >= 3 ? cols[2] : null);
        String seStr = (cols.length >= 4 ? cols[3] : null);
        Float se = null;
        if (seStr != null && NumberUtils.isNumber (seStr))
          se = Float.parseFloat (seStr);
        Long ability = 0L;

        final String SQL_INSERT3 = "insert into ${scoresTblName} (strand, measure, val, se, ability) values (${strand}, ${measure}, ${val}, ${se}, ${ability})";
        SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("strand", strand).put ("measure", measure).put ("val", val).
            put ("se", se).put ("ability", ability);
        Map<String, String> unquotedParms3 = new HashMap<> ();
        unquotedParms3.put ("scoresTblName", scoresTbl.getTableName ());

        executeStatement (connection, fixDataBaseNames (SQL_INSERT3, unquotedParms3), parms3, false).getUpdateCount ();
      }

      final String SQL_QUERY4 = "select  _fk_TestOpportunity from testopportunityscores where _fk_TestOpportunity = ${testoppkey} limit 1";
      SqlParametersMaps parms4 = parms1;

      if (exists (executeStatement (connection, SQL_QUERY4, parms4, false))) {
        if (AuditScores_FN (connection, clientname)) {

          // String sessionDB = getAppSettings ().get ("TDSSessionDBName");
          String sessionDB = getTdsSettings ().getTDSSessionDBName ();
          final String SQL_INSERT5 = "insert into ${ArchiveDB}.testopportunityscores_audit (_fk_TestOpportunity, subject, MeasureOf, MeasureLabel, value, standardError, _date, dbname)"
              + " select ${testoppkey}, subject, MeasureOf, MeasureLabel, value, standardError, now(3), ${dbname} "
              + "  from testopportunityscores where _fk_TestOpportunity = ${testoppkey}";
          SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("testoppkey", testoppkey).put ("dbname", sessionDB);

          executeStatement (connection, fixDataBaseNames (SQL_INSERT5), parms5, false).getUpdateCount ();
        }
        final String SQL_DELETE6 = "delete from testopportunityscores  where _fk_TestOpportunity = ${testoppkey}";
        SqlParametersMaps parms6 = parms1;

        executeStatement (connection, SQL_DELETE6, parms6, false).getUpdateCount ();
      }

      final String SQL_UPDATE7 = "update ${scoresTblName} S, ${ConfigDB}.client_testscorefeatures F set S.ability = F.UseForAbility "
          + " where F.ClientName = ${clientname} and (F.TestID = ${test} or F.TestID = '*') and F.MeasureOf = strand and F.MeasureLabel = measure";
      Map<String, String> unquotedParms7 = new HashMap<> ();
      unquotedParms7.put ("scoresTblName", scoresTbl.getTableName ());
      SqlParametersMaps parms7 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("test", test);

      final String query7 = fixDataBaseNames (SQL_UPDATE7);
      executeStatement (connection, fixDataBaseNames (query7, unquotedParms7), parms7, false).getUpdateCount ();

      final String SQL_INSERT8 = "insert into testopportunityscores "
          + "(_fk_TestOpportunity, subject, UseforAbility, MeasureOf, MeasureLabel, value, standardError, _date, hostname) "
          + " select ${testoppkey}, ${subject}, ability, strand, measure, val, se, now(3), ${localhost} from ${scoresTblName}";
      Map<String, String> unquotedParms8 = unquotedParms7;
      SqlParametersMaps parms8 = (new SqlParametersMaps ()).put ("testoppkey", testoppkey).put ("subject", subject).put ("localhost", _commonDll.getLocalhostName ());

      executeStatement (connection, fixDataBaseNames (SQL_INSERT8, unquotedParms8), parms8, false).getUpdateCount ();
      connection.dropTemporaryTable (scoresTbl);

    } catch (ReturnStatusException re) {

      _commonDll._LogDBError_SP (connection, "_InsertTestoppScores", re.getMessage (), null, null, null, testoppkey);
      msgRef.set (re.getMessage ());
      connection.dropTemporaryTable (scoresTbl);
    }
  }

  // Optimization attempt
  public void _InsertTestoppScores_SP (SQLConnection connection, UUID testoppkey, String scoreString, Character coldelim, Character rowdelim,
      _Ref<String> msgRef) throws ReturnStatusException {

    Date now = _dateUtil.getDateWRetStatus (connection);
    
    String clientname = null, test = null, subject = null;
    // @scores table (strand varchar(max), measure varchar(max), val
    // varchar(100), se float, ability bigint default 0);
    DataBaseTable scoresTbl = getDataBaseTable ("scores").addColumn ("strand", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 2000).
        addColumn ("measure", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 2000).addColumn ("val", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).
        addColumn ("se", SQL_TYPE_To_JAVA_TYPE.FLOAT).addColumn ("ability", SQL_TYPE_To_JAVA_TYPE.BIGINT);
    connection.createTemporaryTable (scoresTbl);

    try {

      final String SQL_QUERY1 = "select clientname, _efk_TestID as test, Subject from testopportunity where _Key  = ${testoppkey}";
      SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("testoppkey", testoppkey);
      SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
      DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        clientname = record.<String> get ("clientname");
        test = record.<String> get ("test");
        subject = record.<String> get ("subject");
      }

      // -- check the string for rowdelim termination. If not, make it so.
      boolean isOfficial;
      final String SQL_QUERY2 = "select  _fk_TestOpportunity from testeeresponse  "
          + " where  _fk_TestOpportunity = ${testoppkey} and _efk_ITSItem is not null and score = -1 and IsFieldTest = 0 limit 1";
      SqlParametersMaps parms2 = parms1;
      if (exists (executeStatement (connection, SQL_QUERY1, parms1, false)))
        isOfficial = false;
      else
        isOfficial = true;

      List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
      // Just sanity check to avoid exceptionon looping thru rows
      if (scoreString == null)
        scoreString = "";
      String[] rows = _commonDll._BuildTableAsArray (scoreString, rowdelim.toString (), -1);
      for (String row : rows) {
        String[] cols = _commonDll._BuildTableAsArray (row, coldelim.toString (), -1);
        String strand = (cols.length >= 1 ? cols[0] : null);
        String measure = (cols.length >= 2 ? cols[1] : null);
        String val = (cols.length >= 3 ? cols[2] : null);
        String seStr = (cols.length >= 4 ? cols[3] : null);
        Float se = null;
        if (seStr != null && NumberUtils.isNumber (seStr))
          se = Float.parseFloat (seStr);
        Long ability = 0L;

        CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
        rcd.put ("strand", strand);
        rcd.put ("measure", measure);
        rcd.put ("val", val);
        rcd.put ("se", se);
        rcd.put ("ability", ability);
        resultList.add (rcd);
      }
      SingleDataResultSet result1 = new SingleDataResultSet ();
      result1.addColumn ("strand", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result1.addColumn ("measure", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result1.addColumn ("val", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result1.addColumn ("se", SQL_TYPE_To_JAVA_TYPE.FLOAT);
      result1.addColumn ("ability", SQL_TYPE_To_JAVA_TYPE.BIGINT);
      result1.addRecords (resultList);

      insertBatch (connection, scoresTbl.generateInsertStatement (), result1, null);

      final String SQL_QUERY4 = "select  _fk_TestOpportunity from testopportunityscores where _fk_TestOpportunity = ${testoppkey} limit 1";
      SqlParametersMaps parms4 = parms1;

      if (exists (executeStatement (connection, SQL_QUERY4, parms4, false))) {
        if (AuditScores_FN (connection, clientname)) {

          // String sessionDB = getAppSettings ().get ("TDSSessionDBName");
          String sessionDB = getTdsSettings ().getTDSSessionDBName ();
          final String SQL_INSERT5 = "insert into ${ArchiveDB}.testopportunityscores_audit (_fk_TestOpportunity, subject, MeasureOf, MeasureLabel, value, standardError, _date, dbname)"
              + " select ${testoppkey}, subject, MeasureOf, MeasureLabel, value, standardError, now(3), ${dbname} "
              + "  from testopportunityscores where _fk_TestOpportunity = ${testoppkey}";
          SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("testoppkey", testoppkey).put ("dbname", sessionDB);

          executeStatement (connection, fixDataBaseNames (SQL_INSERT5), parms5, false).getUpdateCount ();
        }
        final String SQL_DELETE6 = "delete from testopportunityscores  where _fk_TestOpportunity = ${testoppkey}";
        SqlParametersMaps parms6 = parms1;

        executeStatement (connection, SQL_DELETE6, parms6, false).getUpdateCount ();
      }

      final String SQL_UPDATE7 = "update ${scoresTblName} S, ${ConfigDB}.client_testscorefeatures F set S.ability = F.UseForAbility "
          + " where F.ClientName = ${clientname} and (F.TestID = ${test} or F.TestID = '*') and F.MeasureOf = strand and F.MeasureLabel = measure";
      Map<String, String> unquotedParms7 = new HashMap<> ();
      unquotedParms7.put ("scoresTblName", scoresTbl.getTableName ());
      SqlParametersMaps parms7 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("test", test);

      final String query7 = fixDataBaseNames (SQL_UPDATE7);
      executeStatement (connection, fixDataBaseNames (query7, unquotedParms7), parms7, false).getUpdateCount ();

      final String SQL_INSERT8 = "insert into testopportunityscores "
          + "(_fk_TestOpportunity, subject, UseforAbility, MeasureOf, MeasureLabel, value, standardError, _date, hostname) "
          + " select ${testoppkey}, ${subject}, ability, strand, measure, val, se, now(3), ${localhost} from ${scoresTblName}";
      Map<String, String> unquotedParms8 = unquotedParms7;
      SqlParametersMaps parms8 = (new SqlParametersMaps ()).put ("testoppkey", testoppkey).put ("subject", subject).put ("localhost", _commonDll.getLocalhostName ());

      executeStatement (connection, fixDataBaseNames (SQL_INSERT8, unquotedParms8), parms8, false).getUpdateCount ();
      connection.dropTemporaryTable (scoresTbl);

    } catch (ReturnStatusException re) {

      _commonDll._LogDBError_SP (connection, "_InsertTestoppScores", re.getMessage (), null, null, null, testoppkey);
      msgRef.set (re.getMessage ());
      connection.dropTemporaryTable (scoresTbl);
    }
    
    _commonDll._LogDBLatency_SP (connection, "_InsertTestoppScores", now, null, true, null, testoppkey);
  }

  private Integer selectMinP (SQLConnection connection, DataBaseTable tbl) throws ReturnStatusException {

    Integer p = null;
    final String SQL_QUERY = "select  min(pos) as p from ${tblName}";
    Map<String, String> unquotedParms = new HashMap<> ();
    unquotedParms.put ("tblName", tbl.getTableName ());

    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY, unquotedParms), null, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null)
      p = record.<Integer> get ("p");

    return p;
  }

  protected Integer selectIsOnByAuditObject (SQLConnection connection, String clientname, String auditObject) throws ReturnStatusException {
    Integer flag = null;

    final String SQL_QUERY = "select IsOn as flag from ${ConfigDB}.client_systemflags F, externs E "
        + " where E.ClientName=${clientname} and F.clientname = ${clientname} "
        + " and E.IsPracticeTest = F.IsPracticeTest and AuditOBject=${auditobject}";

    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("clientname", clientname).put ("auditobject", auditObject);

    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY), parameters, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      flag = record.<Integer> get ("flag");
    }
    return flag;
  }

  /**
   * Description of GetCurrentTests(connection, clientname, sessiontype)
   * 
   * @param connection
   *          - connection object
   * @param clientname
   *          - clientname(String)
   * @param sessiontype
   *          - sessiontype(int)
   * @return result - SingleDataResultSet
   */
  public DataBaseTable GetCurrentTests_FN (SQLConnection connection, String clientname, int sessionType) throws ReturnStatusException
  {
    Date today = _dateUtil.getDateWRetStatus (connection);
    DataBaseTable tbl = getDataBaseTable ("currenttests").addColumn ("testID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 255).addColumn ("subject", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).
        addColumn ("language", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 500).addColumn ("maxOpportunities", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("Label", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 255).
        addColumn ("grade", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 25).addColumn ("requireEnrollment", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("enrolledSubject", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).
        addColumn ("IsSelectable", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("sortOrder", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("windowMax", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("WindowID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("startDate", SQL_TYPE_To_JAVA_TYPE.DATETIME).addColumn ("endDate", SQL_TYPE_To_JAVA_TYPE.DATETIME).
        addColumn ("mode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("testkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).addColumn ("modeMax", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("windowSession", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("modeSession", SQL_TYPE_To_JAVA_TYPE.INT);

    connection.createTemporaryTable (tbl);

    final String SQL_INSERT = "insert into ${tblName} (testID,subject,language, maxOpportunities, Label, grade, requireEnrollment, enrolledsubject, IsSelectable, sortOrder, windowMax, windowID, startdate,"
        + " endDate,mode,testkey,modeMax,windowSession, modesession)"
        + " select distinct P.testID, P.subjectname as subject, TOOL.Code as language, P.maxOpportunities, P.Label, G.grade, G.requireEnrollment, "
        + " G.enrolledSubject, IsSelectable, P.sortOrder,W.numopps as windowMax, W.windowID, W.startDate, W.endDate, M.mode, M.testkey,     "
        + " M.maxopps as modeMax , W.sessionType as windowSession, M.sessionType as modesession from ${ConfigDB}.client_testwindow W,    "
        + " ${ConfigDB}.client_testmode M, ${ConfigDB}.client_testproperties P, ${ConfigDB}.client_testgrades G,${ConfigDB}.client_testtool TOOL,   "
        + " _externs E, ${ItemBankDB}.tblsetofadminsubjects BANK where P.clientname = ${clientname} and G.clientname = ${clientname} and G.TestID = P.TestID and  "
        + " E.clientname = ${clientname} and W.clientname = ${clientname} and W.testID = P.TestID and ${today}  between  case when W.startDate is null then   "
        + " ${today}  else (W.startDate + INTERVAL shiftWindowStart DAY ) end and case when W.endDate is null then ${today}  else        "
        + " ( W.endDate + INTERVAL shiftWindowEnd DAY) end and M.clientname = ${clientname} and M.TestID = P.TestID and TOOL.Clientname = ${clientname} and "
        + " TOOL.ContextType = 'TEST' and TOOL.Context = P.TestID and TOOL.type = 'Language' and (M.sessionType = -1 or M.sessionType = ${sessionType} and "
        + "(W.sessionType = -1 or W.sessionType = ${sessionType}) and IsSelectable = 1 and BANK._Key = M.testkey  ) ";
    SqlParametersMaps parms = (new SqlParametersMaps ()).put ("clientname", clientname).put ("sessionType", sessionType).put ("today", today);
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("tblName", tbl.getTableName ());

    final String query = fixDataBaseNames (SQL_INSERT);
    executeStatement (connection, fixDataBaseNames (query, unquotedParms), parms, false).getUpdateCount ();
    return tbl;
  }

  /**
   * Description of Isgradeequiv(connection, grade1, grade2) Checks if a strings
   * - Grade1 & Grade2 are numerics
   * 
   * @param connection
   *          - connection object
   * @param grade1
   *          - grade1(String)
   * @param grade2
   *          - grade2(String)
   * @return result - Boolean
   */
  public boolean __IsGradeEquiv_FN (String grade1, String grade2)
  {
    boolean result = false;
    if (NumberUtils.isNumber (grade1.trim ()) && NumberUtils.isNumber (grade2.trim ())) {
      if (Float.parseFloat (grade1.trim ()) == Float.parseFloat (grade2.trim ())) {
        result = true;
      }
    } else if (DbComparator.isEqual (grade1, grade2))
      result = true;
    return result;
  }

  /**
   * Description of __Eligibletests(connection, clientname, testee, sessiontype,
   * grade, debug)
   * 
   * -- This is a 3-stage algorithm which determines the set of tests for which
   * the testee is eligible. -- Stage 1: Challenge Up: ChallengeUp is a
   * pipe-delimited list of Test Identifiers which serve as absolute permission
   * to take a test. No other qualifier is checked. -- Stage 2: Determine the
   * tests for which the student meets grade and enrollment requirements and
   * which are not superseded by any challenge-up test -- Stage 3: Apply any ad
   * hoc RTS attribute requirements (enablers/disablers) which may be given in
   * TDS_Configs.
   * 
   * -- Exceptions for anonymous testees on practice test: -- 1. @testee value <
   * 0 -- 2. @grade must be non-null and match the grade field of one or more
   * tests -- 3. Challengeup is irrelevant since the testee selects his/her own
   * grade level from the application interface -- 4. Any test enrollment
   * requirement will not be applied to anonymous testees. They will be allowed
   * to take these tests as long as the @grade they selected matches
   * 
   * @param connection
   *          - connection object
   * @param clientname
   *          - clientname(String)
   * @param testee
   *          - testee(bigint)
   * @param sessiontype
   *          - sessiontype(int = 0)
   * @param grade
   *          - grade(String)
   * @param debug
   *          - debug(boolean)
   * @return result - Boolean
   */
  // original version
  public SingleDataResultSet __EligibleTests_SPV1 (SQLConnection connection, String clientname, long testee, int sessiontype, String grade, boolean debug) throws ReturnStatusException {
    Date starttime = _dateUtil.getDateWRetStatus (connection);
    _Ref<String> message = new _Ref<String> ();
    LogDBLatencyArgs logargs = new LogDBLatencyArgs (connection);

    DataBaseTable tests = getDataBaseTable ("Tests").addColumn ("testkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).
        addColumn ("testID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200).addColumn ("grade", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 25).
        addColumn ("subject", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("enroll", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).
        addColumn ("maxopps", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("rtsEquiv", SQL_TYPE_To_JAVA_TYPE.BIT).
        addColumn ("mode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50);
    connection.createTemporaryTable (tests);
    DataBaseTable currenttestsTable = GetCurrentTests_FN (connection, clientname, sessiontype);

    final String SQL_INSERT = "insert into ${tblName} (testkey, testID, grade, subject, enroll, maxopps, rtsEquiv, mode)  "
        + " select distinct testkey, TestID, Grade, Subject, EnrolledSubject, MaxOpportunities, 0 , mode "
        + " from ${currenttests} ";
    SqlParametersMaps parms = new SqlParametersMaps ();
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("tblName", tests.getTableName ());
    unquotedParms.put ("currenttests", currenttestsTable.getTableName ());
    executeStatement (connection, fixDataBaseNames (SQL_INSERT, unquotedParms), parms, false).getUpdateCount ();

    if (testee < 0) {
      final String SQL_QUERY1 = "select distinct testkey, testid, maxopps, mode, grade from ${tablename} ";
      Map<String, String> unquotedParms1 = new HashMap<String, String> ();
      unquotedParms1.put ("tablename", tests.getTableName ());
      SingleDataResultSet result1 = executeStatement (connection, fixDataBaseNames (SQL_QUERY1, unquotedParms1), null, false).getResultSets ().next ();
      Iterator<DbResultRecord> records = result1.getRecords ();
      List<DbResultRecord> list_of_rows = new ArrayList<DbResultRecord> ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        if (__IsGradeEquiv_FN (record.<String> get ("grade"), grade) == false) {
          list_of_rows.add (record);
        }
      }
      result1.removeAll (list_of_rows);
      logargs.procName = "__Eligbletests_SP";
      logargs.startTime = starttime;
      logargs.userKey = testee;
      logargs.checkAudit = true;
      logargs.clientName = clientname;
      _commonDll._LogDBLatency_SP (logargs);

      connection.dropTemporaryTable (currenttestsTable);
      connection.dropTemporaryTable (tests);
      return result1;
    }

    _rtsDll._GetRTSAttribute_SP (connection, clientname, testee, "--ELIGIBLETESTS--", message);

    DataBaseTable buildtable = _commonDll._BuildTable_FN (connection, "buildtable", message.get (), ";");
    final String SQL_QUERY4 = "select distinct testkey, testId, maxopps, mode from ${tablename}, ${buildtable} where testid = record";
    Map<String, String> unquotedParms4 = new HashMap<String, String> ();
    unquotedParms4.put ("tablename", tests.getTableName ());

    unquotedParms4.put ("buildtable", buildtable.getTableName ());
    SingleDataResultSet result2 = executeStatement (connection, fixDataBaseNames (SQL_QUERY4, unquotedParms4), null, false).getResultSets ().next ();

    connection.dropTemporaryTable (buildtable);
    connection.dropTemporaryTable (currenttestsTable);
    connection.dropTemporaryTable (tests);

    logargs.procName = "__Eligbletests_SP";
    logargs.startTime = starttime;
    logargs.userKey = testee;
    logargs.checkAudit = true;
    logargs.clientName = clientname;
    _commonDll._LogDBLatency_SP (logargs);

    return result2;
  }

  // attempt to optimize
  public SingleDataResultSet __EligibleTests_SP (SQLConnection connection, String clientname, long testee, int sessiontype, String grade, boolean debug) throws ReturnStatusException {
    long startTime = System.currentTimeMillis ();
    Date starttime = _dateUtil.getDateWRetStatus (connection);
    _Ref<String> message = new _Ref<String> ();
    LogDBLatencyArgs logargs = new LogDBLatencyArgs (connection);
    
    final String SQL_INSERT = "insert into ${tblName} (testID,subject, maxOpps,  grade,  enroll, "
        + " mode, testkey, rtsEquiv)"
        + " select distinct P.testID, P.subjectname as subject,  P.maxOpportunities, G.grade,  "
        + " G.enrolledSubject,  M.mode, M.testkey, 0    "
        + " from ${ConfigDB}.client_testwindow W,    "
        + " ${ConfigDB}.client_testmode M, ${ConfigDB}.client_testproperties P, ${ConfigDB}.client_testgrades G,${ConfigDB}.client_testtool TOOL,   "
        + " _externs E, ${ItemBankDB}.tblsetofadminsubjects BANK where P.clientname = ${clientname} and G.clientname = ${clientname} and G.TestID = P.TestID and  "
        + " E.clientname = ${clientname} and W.clientname = ${clientname} and W.testID = P.TestID and ${today}  between  case when W.startDate is null then   "
        + " ${today}  else (W.startDate + INTERVAL shiftWindowStart DAY ) end and case when W.endDate is null then ${today}  else        "
        + " ( W.endDate + INTERVAL shiftWindowEnd DAY) end and M.clientname = ${clientname} and M.TestID = P.TestID and TOOL.Clientname = ${clientname} and "
        + " TOOL.ContextType = 'TEST' and TOOL.Context = P.TestID and TOOL.type = 'Language' and (M.sessionType = -1 or M.sessionType = ${sessionType}) and "
        + "(W.sessionType = -1 or W.sessionType = ${sessionType}) and IsSelectable = 1 and BANK._Key = M.testkey   ";

    DataBaseTable tests = getDataBaseTable ("Tests").addColumn ("testkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).
        addColumn ("testID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200).addColumn ("grade", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 25).
        addColumn ("subject", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("enroll", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).
        addColumn ("maxopps", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("rtsEquiv", SQL_TYPE_To_JAVA_TYPE.BIT).
        addColumn ("mode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50);
    connection.createTemporaryTable (tests);
    //_logger.info ("<<<<<<<<< __Eligbletests_SP 1 : "+((System.currentTimeMillis ()-startTime)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("tblName", tests.getTableName ());
    SqlParametersMaps parms = (new SqlParametersMaps ()).put ("clientname", clientname).put ("sessionType", sessiontype).put ("today", starttime);

    final String query = fixDataBaseNames (SQL_INSERT);
    executeStatement (connection, fixDataBaseNames (query, unquotedParms), parms, false).getUpdateCount ();
    //_logger.info ("<<<<<<<<< __Eligbletests_SP 2 : "+((System.currentTimeMillis ()-startTime)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    if (testee < 0) {
      final String SQL_QUERY1 = "select distinct testkey, testid, maxopps, mode, grade from ${tablename} ";
      Map<String, String> unquotedParms1 = new HashMap<String, String> ();
      unquotedParms1.put ("tablename", tests.getTableName ());
      SingleDataResultSet result1 = executeStatement (connection, fixDataBaseNames (SQL_QUERY1, unquotedParms1), null, false).getResultSets ().next ();
      Iterator<DbResultRecord> records = result1.getRecords ();
      List<DbResultRecord> list_of_rows = new ArrayList<DbResultRecord> ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        if (__IsGradeEquiv_FN (record.<String> get ("grade"), grade) == false) {
          list_of_rows.add (record);
        }
      }
      result1.removeAll (list_of_rows);
      logargs.procName = "__Eligbletests_SP";
      logargs.startTime = starttime;
      logargs.userKey = testee;
      logargs.checkAudit = true;
      logargs.clientName = clientname;
      _commonDll._LogDBLatency_SP (logargs);

      connection.dropTemporaryTable (tests);
      //_logger.info ("<<<<<<<<< __Eligbletests_SP 3 Total : "+((System.currentTimeMillis ()-startTime)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
      return result1;
    }

    _rtsDll._GetRTSAttribute_SP (connection, clientname, testee, "--ELIGIBLETESTS--", message);
    
    if (message.get() == null)
      message.set ("");
    String rows[] = _commonDll._BuildTableAsArray (message.get (), ";", -1);
    int ii = 0;
    while (ii < rows.length) {
      rows[ii] = rows[ii].trim ();
      ii++;
    }
    String recordStr = arrayToQuotedString (rows);
    // DataBaseTable buildtable = _commonDll._BuildTable_FN (connection,
    // "buildtable", message.get (), ";");
    // final String SQL_QUERY4 =
    // "select distinct testkey, testId, maxopps, mode from ${tablename}, ${buildtable} where testid = record";
    // final String SQL_QUERY4 =
    // "select distinct testkey, testId, maxopps, mode from ${tablename} where testid in (${recordStr})";
    final String SQL_QUERY4 = "select distinct testkey, testId, maxopps, mode from ${tablename} where testkey in (${recordStr})";
    Map<String, String> unquotedParms4 = new HashMap<String, String> ();
    unquotedParms4.put ("tablename", tests.getTableName ());
    unquotedParms4.put ("recordStr", recordStr);

    // unquotedParms4.put ("buildtable", buildtable.getTableName ());
    if (_logger.isDebugEnabled ()) {
      _logger.debug ("BEGIN: Dump of almost-available tests");
      SingleDataResultSet dump = executeStatement (connection, fixDataBaseNames ("select * from ${tablename}", unquotedParms4), null, false).getResultSets ().next ();
      Iterator<DbResultRecord> iter = dump.getRecords ();
      int i = 1;
      while (iter.hasNext ()) {
        DbResultRecord rec = iter.next ();
        StringBuilder b = new StringBuilder ("Row ")
            .append (i)
            .append (": (");
        Iterator<String> iter2 = rec.getColumnNames ();
        while (iter2.hasNext ()) {
          String columnName = iter2.next ();
          b.append (columnName).append (':')
              .append (rec.get (columnName))
              .append ("); (");
        }
        b.append (")");
        _logger.debug (b.toString ());
        i++;
      }
      _logger.debug ("END: Dump of almost-available tests");
    }
    SingleDataResultSet result2 = executeStatement (connection, fixDataBaseNames (SQL_QUERY4, unquotedParms4), null, false).getResultSets ().next ();
    //_logger.info ("<<<<<<<<< __Eligbletests_SP 4 : "+((System.currentTimeMillis ()-startTime)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    logargs.procName = "__Eligbletests_SP";
    logargs.startTime = starttime;
    logargs.userKey = testee;
    logargs.checkAudit = true;
    logargs.clientName = clientname;
    _commonDll._LogDBLatency_SP (logargs);

    connection.dropTemporaryTable (tests);
    //_logger.info ("<<<<<<<<< __Eligbletests_SP Total Execution Time : "+((System.currentTimeMillis ()-startTime)) + " ms & ThreadId : "+Thread.currentThread ().getId ());
    return result2;
  }

  /**
   * Description of ClientItemStimulusPath(connection, clientname, testkey,
   * itemkey)
   * 
   * @param connection
   *          - connection object
   * @param clientname
   *          - clientname(String)
   * @param testkey
   *          - testkey(string)
   * @return itemkey - itemkey(string)
   */
  public String ClientItemStimulusPath_FN (SQLConnection connection, String clientName, String testkey, String itemkey) throws ReturnStatusException {

    String path = null;
    final String SQL_QUERY = "select concat(C.homepath, B.homepath, B.stimulipath, S.FilePath, S.filename) as path"
        + " from ${ItemBankDB}.tblitembank B, ${ItemBankDB}.tblclient C, ${ItemBankDB}.tblstimulus S, ${ItemBankDB}.tblsetofitemstimuli I "
        + " where I._fk_AdminSubject = ${testkey} and I._fk_Item = ${itemkey}  and S._Key = _fk_Stimulus and C.name = ${clientname}"
        + " and B._efk_Itembank = S._efk_Itembank and B._fk_Client = C._Key ";

    String query = fixDataBaseNames (SQL_QUERY);
    SqlParametersMaps parameters = new SqlParametersMaps ().put ("clientName", clientName).put ("testkey", testkey).put ("itemKey", itemkey);

    SingleDataResultSet result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      path = record.<String> get ("path");
      path = replaceSeparatorChar (path);
    }

    return path;
  }
  /**
   * Description of T_GetOpportunityAccommodations_SP (SQLConnection connection,
   * UUID oppkey, UUID session, UUID browserId) This Stored procedure gets the
   * following inputs - connection, oppkey, session and browser ID and returns a
   * Single dataresult set which contains - Segment, AccType, accCode,
   * isApproved and recordUsage
   * 
   * @param connection
   *          - connection object
   * @param oppkey
   *          - oppkey(UUID)
   * @param session
   *          - session(UUID)
   * @return result - MultiDataResultset
   */
  public SingleDataResultSet T_GetOpportunityAccommodations_SP (SQLConnection connection, UUID oppkey, UUID session, UUID browserId)
      throws ReturnStatusException {
    final String SQL_QUERY = "call t_getopportunityaccommodations (${oppkey}, ${session}, ${browserID})";
    SqlParametersMaps params = new SqlParametersMaps ();
    params.put ("oppkey", oppkey);
    params.put ("session", session);
    params.put ("browserID", browserId);

    

    SingleDataResultSet resultSet = executeStatement (connection, SQL_QUERY, params, false).getResultSets ().next ();
    return resultSet;
  }
  
  /**
   * 
   * This is the Java version of t_getopportunityaccommodations stored procedure. This method is not used now. It is only needed when we want 
   * to migrate the Mysql stored procedure t_getopportunityaccommodations to java.
   * 
   * Description of T_GetOpportunityAccommodations_SP (SQLConnection connection,
   * UUID oppkey, UUID session, UUID browserId) This Stored procedure gets the
   * following inputs - connection, oppkey, session and browser ID and returns a
   * Single dataresult set which contains - Segment, AccType, accCode,
   * isApproved and recordUsage
   * 
   * @param connection
   *          - connection object
   * @param oppkey
   *          - oppkey(UUID)
   * @param session
   *          - session(UUID)
   * @return result - MultiDataResultset
   */
  public SingleDataResultSet T_GetOpportunityAccommodations_SP_Java (SQLConnection connection, UUID oppkey, UUID session, UUID browserId)
      throws ReturnStatusException {
    Date starttime = _dateUtil.getDateWRetStatus (connection);
    _Ref<String> message = new _Ref<String> ();
    ReturnErrorArgs args = new ReturnErrorArgs (connection);
    if (session != null && browserId != null)
    {
      _ValidateTesteeAccessProc_SP (connection, oppkey, session, browserId, true, message);
      if (message.get () != null) {
        args.procName = "GetOpportunityAccommodations";
        args.appKey = message.get ();
        args.status = "failed";
        args.context = "_ValidateItemAccess";
        args.oppKey = oppkey;
        return _commonDll._ReturnError_SP (args);
      }
    }

    final String SQL_QUERY = "select segment, accType, accCode, isApproved, recordUsage from testeeaccommodations where _fk_TestOpportunity = ${oppkey} ";
    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("oppkey", oppkey);

    SingleDataResultSet rs = executeStatement (connection, SQL_QUERY, parameters, false).getResultSets ().next ();
    _commonDll._LogDBLatency_SP (connection, "T_GetOpportunityAccommodations", starttime, null, true, null, null, session, null, null);
    return rs;
  }

  public SingleDataResultSet T_GetItemGroup_SP (SQLConnection connection, UUID oppkey, int pageNumber, String groupID, String dateCreated, 
      UUID session, UUID browserId, boolean validateAccess)
      throws ReturnStatusException {
    
    final String SQL_QUERY = "call t_getitemgroup(${oppkey}, ${pageNumber}, ${groupID}, ${dateCreated},${session}, ${browserId},${validateAccess})";
    SqlParametersMaps params = new SqlParametersMaps ();
    params.put ("oppkey", oppkey);
    params.put ("session", session);
    params.put ("browserID", browserId);
    params.put ("pageNumber", pageNumber);
    params.put ("groupID", groupID);
    params.put ("dateCreated", dateCreated);
    params.put ("validateAccess", validateAccess);

    SingleDataResultSet resultSet = executeStatement (connection, SQL_QUERY, params, false).getResultSets ().next ();
    return resultSet;
  }
  /**
   *     This is the Java version of t_getitemgroup stored procedure. This method is not used now. 
   *     It is only needed when we want to migrate the Mysql stored procedure t_getitemgroup to java.
   *     
   * Description of T_GetItemGroup_SP (SQLConnection connection, UUID oppkey,
   * int pageNumber, String groupID, String dateCreated, UUID session, UUID
   * browserId, boolean validateAccess )
   * 
   * @param connection
   *          - connection object
   * @param oppkey
   *          - oppkey(UUID)
   * @param pagenumber
   *          - pagenumber(int)
   * @param groupID
   *          - groupID(String)
   * @param datecreated
   *          - datecreated(String)
   * @param session
   *          - session(UUID)
   * @param browserid
   *          - browserid(UUID)
   * @param validateaccess
   *          - validateaccess(UUID)
   * @return result - MultiDataResultset
   */
  // Original port
  public SingleDataResultSet T_GetItemGroup_SP_Java (SQLConnection connection, UUID oppkey, int pageNumber, String groupID, String dateCreated, UUID session, UUID browserId, boolean validateAccess)
      throws ReturnStatusException {
    Date starttime = _dateUtil.getDateWRetStatus (connection);
    String testkey = null;
    String testID = null;
    String clientname = null;
    String printtypes = null;
    Boolean printtest = null;
    Integer restart = null;
    SingleDataResultSet result = null;
    _Ref<String> message = new _Ref<String> ();
    LogDBLatencyArgs logargs = new LogDBLatencyArgs (connection);
    ReturnErrorArgs errorargs = new ReturnErrorArgs (connection);

    if (validateAccess == true && browserId != null && session != null)
    {
      _ValidateTesteeAccessProc_SP (connection, oppkey, session, browserId, true, message);
      if (message.get () != null) {
        errorargs.procName = "T_GetItemGroup";
        errorargs.appKey = message.get ();
        errorargs.status = "failed";
        errorargs.context = "_ValidateItemAccess";
        errorargs.oppKey = oppkey;
        return _commonDll._ReturnError_SP (errorargs);
      }
    }

    final String SQL_QUERY1 = "SELECT clientname, _efk_TestID, _efk_AdminSubject, restart  FROM  testopportunity WHERE _Key = ${oppkey}";
    SqlParametersMaps parameters1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);
    result = executeStatement (connection, SQL_QUERY1, parameters1, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;

    if (record != null) {
      clientname = record.<String> get ("clientname");
      testID = record.<String> get ("_efk_TestID");
      testkey = record.<String> get ("_efk_AdminSubject");
      restart = record.<Integer> get ("restart");
    }

    final String SQL_QUERY2 = "SELECT concat('|', printItemTypes, '|') as printtypes, Isprintable as printTest  FROM  ${ConfigDB}.client_testproperties WHERE clientname = ${clientname} and testid = ${testID}";
    SqlParametersMaps parameters2 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testid", testID);
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY2), parameters2, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;

    if (record != null) {
      printtypes = record.<String> get ("printtypes");
      printtest = record.<Boolean> get ("printTest");
    }

    // Comment entries in the Stored procedures
    // case inactive-- this value is needed to prevent from administering an
    // inactivated item that is still (or again) in the item pool

    final String SQL_QUERY3 = "SELECT S._efk_Segment as testkey, R._efk_ItemKey as itemkey,_efk_itsbank AS ItemBank, _efk_itsitem  AS Item, "
        + " position, page, score, mark, response, R.isfieldtest, isselected, R.isrequired, format,"
        + " CASE WHEN isinactive = 1 THEN 0 WHEN opportunityrestart = ${restart} THEN 1 ELSE 0 END  AS isVisibleN, 0  AS readOnlyN ,"
        + "R.groupid, DATE_FORMAT (dateGenerated, '%Y-%m-%d %H:%i:%S.%f') AS DateCreated, responsesequence, responselength, isvalid, groupitemsrequired, R.segment, R.segmentid, "
        + "case when ${printtest} = 1 or locate(concat('|', R.format, '|'), ${printtypes}) > 0 or I.isPrintable = 1 then 1 else 0 end as IsPrintable, "
        + "case isInactive when 1 then -1 else OpportunityRestart  end as OpportunityRestart "
        + "  FROM   testeeresponse R, testopportunitysegment S, ${ItemBankDB}.tblsetofadminitems I "
        + " WHERE  R._fk_testopportunity = ${oppkey} AND S._fk_testopportunity = ${oppkey} AND R.segment = S.segmentposition AND page = ${pageNumber}"
        + " AND I._fk_adminsubject = S._efk_segment"
        + " AND I._fk_item = R._efk_itemkey AND ( ${groupID} IS NULL OR R.groupid = ${groupID} ) AND ( ${dateCreated} IS NULL OR ${dateCreated} = dategenerated ) "
        + " ORDER  BY position   ";
    SqlParametersMaps parameters3 = (new SqlParametersMaps ()).put ("oppkey", oppkey).put ("clientname", clientname).put ("restart", restart).put ("dateCreated", dateCreated)
        .put ("printtest", printtest).put ("printtypes", printtypes)
        .put ("pageNumber", pageNumber).put ("groupID", groupID);

    
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY3), parameters3, false).getResultSets ().next ();
    result.addColumn ("itemFile", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    result.addColumn ("stimulusFile", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    result.addColumn ("isVisible", SQL_TYPE_To_JAVA_TYPE.BIT);
    result.addColumn ("readOnly", SQL_TYPE_To_JAVA_TYPE.BIT);

    Iterator<DbResultRecord> records = result.getRecords ();
    while (records.hasNext ()) {
      record = records.next ();
      String itemfile = _commonDll.ClientItemFile_FN (connection, clientname, record.<String> get ("itemkey"));
      record.addColumnValue ("itemFile", itemfile);

      String stimulusfile = ClientItemStimulusPath_FN (connection, clientname, record.<String> get ("testkey"), record.<String> get ("itemkey"));
      record.addColumnValue ("stimulusFile", stimulusfile);

      Long tmp = null;
      try { // TODO: jmambo determine cause of change in data type from Long to
            // Integer
        tmp = record.<Long> get ("isVisibleN");
      } catch (Exception ex) {
        tmp = new Long (record.<Integer> get ("isVisibleN"));
      }
      record.addColumnValue ("isVisible", (DbComparator.isEqual (tmp, 1) ? true : false));

      Long tmp1 = record.<Long> get ("readOnlyN");
      record.addColumnValue ("readOnly", (DbComparator.isEqual (tmp1, 1) ? true : false));
    }

    logargs.procName = "T_GetItemGroup";
    logargs.startTime = starttime;
    logargs.testOppKey = oppkey;
    logargs.checkAudit = true;
    logargs.clientName = clientname;
    _commonDll._LogDBLatency_SP (logargs);

    return result;
  }

  // Optimization attempt
  // Need to reconsider
  public SingleDataResultSet T_GetItemGroup_SPOpt (SQLConnection connection, UUID oppkey, int pageNumber, String groupID, String dateCreated, UUID session, UUID browserId, boolean validateAccess)
      throws ReturnStatusException {
    Date starttime = _dateUtil.getDateWRetStatus (connection);
    String testkey = null;
    String testID = null;
    String clientname = null;
    String printtypes = null;
    Boolean printtest = null;
    Integer restart = null;
    SingleDataResultSet result = null;
    _Ref<String> message = new _Ref<String> ();
    LogDBLatencyArgs logargs = new LogDBLatencyArgs (connection);
    ReturnErrorArgs errorargs = new ReturnErrorArgs (connection);

    if (validateAccess == true && browserId != null && session != null)
    {
      _ValidateTesteeAccessProc_SP (connection, oppkey, session, browserId, true, message);
      if (message.get () != null) {
        errorargs.procName = "T_GetItemGroup";
        errorargs.appKey = message.get ();
        errorargs.status = "failed";
        errorargs.context = "_ValidateItemAccess";
        errorargs.oppKey = oppkey;
        return _commonDll._ReturnError_SP (errorargs);
      }
    }

    final String SQL_QUERY1 = "SELECT clientname, _efk_TestID, _efk_AdminSubject, restart  FROM  testopportunity WHERE _Key = ${oppkey}";
    SqlParametersMaps parameters1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);
    result = executeStatement (connection, SQL_QUERY1, parameters1, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;

    if (record != null) {
      clientname = record.<String> get ("clientname");
      testID = record.<String> get ("_efk_TestID");
      testkey = record.<String> get ("_efk_AdminSubject");
      restart = record.<Integer> get ("restart");
    }

    final String SQL_QUERY2 = "SELECT concat('|', printItemTypes, '|') as printtypes, Isprintable as printTest  FROM  ${ConfigDB}.client_testproperties WHERE clientname = ${clientname} and testid = ${testID}";
    SqlParametersMaps parameters2 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testid", testID);
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY2), parameters2, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;

    if (record != null) {
      printtypes = record.<String> get ("printtypes");
      printtest = record.<Boolean> get ("printTest");
    }

    // Comment entries in the Stored procedures
    // case inactive-- this value is needed to prevent from administering an
    // inactivated item that is still (or again) in the item pool

    final String SQL_QUERY3 = "SELECT S._efk_Segment as testkey, R._efk_ItemKey as itemkey,_efk_itsbank AS ItemBank, _efk_itsitem  AS Item, "
        + " position, page, score, mark, response, R.isfieldtest, isselected, R.isrequired, format,"
        + " CASE WHEN isinactive = 1 THEN 0 WHEN opportunityrestart = ${restart} THEN 1 ELSE 0 END  AS isVisibleN, 0  AS readOnlyN ,"
        + "R.groupid, DATE_FORMAT (dateGenerated, '%Y-%m-%d %H:%i:%S.%f') AS DateCreated, responsesequence, responselength, isvalid, groupitemsrequired, R.segment, R.segmentid, "
        + "case when ${printtest} = 1 or locate(concat('|', R.format, '|'), ${printtypes}) > 0 or I.isPrintable = 1 then 1 else 0 end as IsPrintable, "
        + "case isInactive when 1 then -1 else OpportunityRestart  end as OpportunityRestart "

        + ", (select concat(C.Homepath, B.HomePath, B.ItemPath, I.FilePath, I.FileName) "
        + "   from ${ItemBankDB}.tblitembank B, ${ItemBankDB}.tblclient C, ${ItemBankDB}.tblitem I "
        + "   where B._efk_Itembank = I._efk_ItemBank and C.name = ${clientName}"
        + "   and B._fk_Client = C._Key and I._Key = R._efk_ItemKey) as itemFile "

        + ", (select concat(C.homepath, B.homepath, B.stimulipath, SS.FilePath, SS.filename) "
        + " from ${ItemBankDB}.tblitembank B, ${ItemBankDB}.tblclient C, ${ItemBankDB}.tblstimulus SS, ${ItemBankDB}.tblsetofitemstimuli I "
        + " where I._fk_AdminSubject = S._efk_Segment and I._fk_Item = R._efk_ItemKey  and SS._Key = _fk_Stimulus and C.name = ${clientname}"
        + " and B._efk_Itembank = SS._efk_Itembank and B._fk_Client = C._Key) as stimulusFile "

        + "  FROM   testeeresponse R, testopportunitysegment S, ${ItemBankDB}.tblsetofadminitems I "
        + " WHERE  R._fk_testopportunity = ${oppkey} AND S._fk_testopportunity = ${oppkey} AND R.segment = S.segmentposition AND page = ${pageNumber}"
        + " AND I._fk_adminsubject = S._efk_segment"
        + " AND I._fk_item = R._efk_itemkey AND ( ${groupID} IS NULL OR R.groupid = ${groupID} ) AND ( ${dateCreated} IS NULL OR ${dateCreated} = dategenerated ) "
        + " ORDER  BY position   ";
    SqlParametersMaps parameters3 = (new SqlParametersMaps ()).put ("oppkey", oppkey).put ("clientname", clientname).put ("restart", restart).put ("dateCreated", dateCreated)
        .put ("printtest", printtest).put ("printtypes", printtypes)
        .put ("pageNumber", pageNumber).put ("groupID", groupID);

    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY3), parameters3, false).getResultSets ().next ();
    result.addColumn ("isVisible", SQL_TYPE_To_JAVA_TYPE.BIT);
    result.addColumn ("readOnly", SQL_TYPE_To_JAVA_TYPE.BIT);

    Iterator<DbResultRecord> records = result.getRecords ();
    while (records.hasNext ()) {
      record = records.next ();
      Long tmp = null;
      try { // TODO: jmambo determine cause of change in data type from Long to
            // Integer
        tmp = record.<Long> get ("isVisibleN");
      } catch (Exception ex) {
        tmp = new Long (record.<Integer> get ("isVisibleN"));
      }
      record.addColumnValue ("isVisible", (DbComparator.isEqual (tmp, 1) ? true : false));

      Long tmp1 = record.<Long> get ("readOnlyN");
      record.addColumnValue ("readOnly", (DbComparator.isEqual (tmp1, 1) ? true : false));
    }

    logargs.procName = "T_GetItemGroup";
    logargs.startTime = starttime;
    logargs.testOppKey = oppkey;
    logargs.checkAudit = true;
    logargs.clientName = clientname;
    _commonDll._LogDBLatency_SP (logargs);

    return result;
  }

  public SingleDataResultSet P_GetTestForms_SP (SQLConnection connection, String clientname, String testId, Integer sessionType) throws ReturnStatusException {

    // Date starttime = getDateWRetStatus (connection);

    DataBaseTable tbl = GetTestFormWindows_FN (connection, clientname, testId, sessionType);

    final String SQL_QUERY = "select * from ${tblName}";
    Map<String, String> unquotedParms = new HashMap<> ();
    unquotedParms.put ("tblName", tbl.getTableName ());

    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY, unquotedParms), null, false).getResultSets ().next ();
    connection.dropTemporaryTable (tbl);

    return result;
  }

  @Override
  public int getOpportunityNumber(SQLConnection connection, UUID oppKey) throws ReturnStatusException {
    int opportunityNumber = 0;
    final String SQL_QUERY = "select opportunity from testopportunity where _key = ${key} ";
    SqlParametersMaps params = (new SqlParametersMaps ()).put ("key", oppKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY, params, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      opportunityNumber = record.<Integer> get ("opportunity");
    }
    return opportunityNumber;
  }
  
  protected Long minutesDiff (Date from, Date to) {

    if (from == null || to == null)
      return null;
    return (to.getTime () - from.getTime ()) / 1000 / 60;
  }

  private Integer daysDiff (String fromStr, String toStr) {
    try {
      Date to = new SimpleDateFormat (AbstractDateUtilDll.DB_DATETIME_FORMAT).parse (toStr);
      Date from = new SimpleDateFormat (AbstractDateUtilDll.DB_DATETIME_FORMAT).parse (fromStr);
      return daysDiff (from, to);
    } catch (Exception pe) {
      _logger.error (pe.getMessage ());
    }
    return null;
  }

  protected Integer daysDiff (Date from, Date to) {
    if (from == null || to == null)
      return null;
    Calendar calTo = Calendar.getInstance ();
    calTo.setTime (to);
    int dayOfYearTo = calTo.get (Calendar.DAY_OF_YEAR);
    int yearTo = calTo.get (Calendar.YEAR);

    Calendar calFrom = Calendar.getInstance ();
    calFrom.setTime (from);
    int dayOfYearFrom = calFrom.get (Calendar.DAY_OF_YEAR);
    int yearFrom = calFrom.get (Calendar.YEAR);

    if (yearTo == yearFrom)
      return (dayOfYearTo - dayOfYearFrom);
    // reminder: DB_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    DateFormat df = new SimpleDateFormat (AbstractDateUtilDll.DB_DATETIME_FORMAT);
    String fromStr = df.format (from);
    String toStr = String.format ("%d-12-31 23:59:59", yearFrom);

    Integer diff = daysDiff (fromStr, toStr) + 1;
    for (int year = yearFrom + 1; year < yearTo; year++) {
      fromStr = String.format ("%d-01-01 00:00:00", year);
      toStr = String.format ("%d-12-31 23:59:59", year);
      Integer oneYearDiff = daysDiff (fromStr, toStr);
      if (oneYearDiff == null)
        return null;
      else
        diff += (oneYearDiff + 1);
    }
    fromStr = String.format ("%d-01-01 00:00:00", yearTo);
    toStr = df.format (to);
    Integer lastDiff = daysDiff (fromStr, toStr);
    if (lastDiff == null)
      return null;
    else
      diff += lastDiff;
    return diff;
  }

  // This class is used only to run three specific queries having one unquoted
  // paramerer - temp table name
  class LoginHelper
  {
    private DataBaseTable tbl;
    private String        queryExists;
    private String        queryExec;
    private String        queryUpdate;
    private String        clientname;
    private Long          entity;

    public void setTbl (DataBaseTable tbl) {
      this.tbl = tbl;
    }

    public void setQueryExists (String queryExists) {
      this.queryExists = queryExists;
    }

    public void setQueryExec (String queryExec) {
      this.queryExec = queryExec;
    }

    public void setQueryUpdate (String queryUpdate) {
      this.queryUpdate = queryUpdate;
    }

    public void setClientname (String clientname) {
      this.clientname = clientname;
    }

    public void setEntity (Long entity) {
      this.entity = entity;
    }

    public void doIt (SQLConnection connection) throws ReturnStatusException {

      Map<String, String> unquotedParms = new HashMap<> ();
      unquotedParms.put ("valsTblName", this.tbl.getTableName ());

      _Ref<String> valueRef = new _Ref<> ();
      _Ref<Long> relatedEntityRef = new _Ref<> ();
      _Ref<String> relatedIdRef = new _Ref<> ();

      while (exists (executeStatement (connection, fixDataBaseNames (this.queryExists, unquotedParms), null, false))) {
        String type = null;
        String field = null;
        SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (this.queryExec, unquotedParms), null, false).getResultSets ().next ();
        DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
        if (record != null) {
          field = record.<String> get ("field");
          type = record.<String> get ("type");
        }

        valueRef.set (null);
        relatedEntityRef.set (null);
        relatedIdRef.set (null);
        if (DbComparator.isEqual (type, "attribute"))
          _rtsDll._GetRTSAttribute_SP (connection, clientname, entity, field, valueRef);
        else if (DbComparator.isEqual (type, "relationship"))
          _rtsDll._GetRTSRelationship_SP (connection, clientname, entity, field, relatedEntityRef, relatedIdRef, valueRef);

        String value = (valueRef.get () == null ?  UNKNOWN_ATTRIBUTE_VALUE : valueRef.get ());
        SqlParametersMaps parms = (new SqlParametersMaps ()).put ("value", value).put ("field", field);
        executeStatement (connection, fixDataBaseNames (queryUpdate, unquotedParms), parms, false).getUpdateCount ();
      }
    }
  }

  public SingleDataResultSet T_GetItemScoringConfigs_SP (SQLConnection connection, String clientName) throws ReturnStatusException {
    return T_GetItemScoringConfigs_SP (connection, clientName, "*", "*");
  }

  public SingleDataResultSet T_GetItemScoringConfigs_SP (SQLConnection connection, String clientName, String siteId, String serverName) throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);
    final String cmd = "select C.context, C.itemtype, C.item_in, C.priority, C.serverurl "
        + " from ${ConfigDB}.client_itemscoringconfig C, _externs E "
        + "WHERE C.clientname=${clientName} AND E.clientname = ${clientName} "
        + " AND (C.siteid = ${siteId} OR C.siteid = '*') AND (C.servername = ${serverName} OR C.servername = '*') "
        + " AND C.environment = E.environment  ";
    SqlParametersMaps parms = (new SqlParametersMaps ()).put ("siteId", siteId).put ("serverName", serverName).put ("clientname", clientName);

    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (cmd), parms, false).getResultSets ().next ();
    _commonDll._LogDBLatency_SP (connection, "T_GetItemScoringConfigs", starttime, null, true, null, null, null, null, null);
    return result;
  }

  public SingleDataResultSet T_LoginRequirements (SQLConnection connection, String clientname) throws ReturnStatusException {

    final String cmd1 = "select TDS_ID, Label, SortOrder  from ${ConfigDB}.client_testeeattribute "
        + " where clientname = ${clientName} and atLogin = 'REQUIRE'";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("clientname", clientname);
    SingleDataResultSet rs1 = executeStatement (connection, fixDataBaseNames (cmd1), parms1, false).getResultSets ().next ();

    return rs1;
  }

  public void handleTISReply(SQLConnection connection, UUID oppkey, Boolean success, String errorMessage) throws ReturnStatusException {
    Date starttime = _dateUtil.getDateWRetStatus (connection);
    String err = null;
    try {
      final String cmd1 = "select _key from testopportunity where _key = ${oppkey}";
      SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);
      if (exists (executeStatement (connection, cmd1, parms1, false)) == false) {
        err = String.format ("No test opportunity matches this key: %s", oppkey.toString ());
      }

      if (err == null) {
        if (success)
          QC_AcceptOpportunity_SP (connection, oppkey);
        else {
          final String cmd2 = "insert into ${ArchiveDB}.opportunityaudit (_fk_TestOpportunity, AccessType, Comment, dateaccessed, hostname, dbname) "
              + " values (${oppkey}, 'rejected', ${errorMessage}, now(3), ${localhost}, ${dbname})";
  
          SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("oppkey", oppkey).put ("errorMessage", errorMessage).
              put ("localhost", _commonDll.getLocalhostName ()).put ("dbname", getTdsSettings ().getTDSSessionDBName ());
          executeStatement (connection, fixDataBaseNames (cmd2), parms2, false).getUpdateCount ();
        }
      } else {
        err = String.format ("%s, success %s, %s", err, success, errorMessage);
        _commonDll._LogDBError_SP (connection, "handleTISReply", err, null, null, null, oppkey);
      }
    } catch (ReturnStatusException re) {
        err = re.getMessage ();
        _commonDll._LogDBError_SP (connection, "handleTISReply", err, null, null, null, oppkey);
    }
    _commonDll._LogDBLatency_SP (connection, "handleTISReply", starttime, null, true, null, oppkey, null, null, null);
 
  }
  
  public void QC_AcceptOpportunity_SP (SQLConnection connection, UUID oppkey) throws ReturnStatusException {
    
    String status = null;
    
    final String cmd1 = "select status from testopportunity where _key = ${oppkey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();
    DbResultRecord rec1 = (rs1.getCount () > 0 ?  rs1.getRecords ().next () : null);
    if (rec1 != null) {
      status = rec1.<String> get ("status");
    }
    
    if (DbComparator.isEqual (status, "expired")) {
      final String cmd2 = "update testopportunity set dateExpiredReported=now(3) where _key=${oppkey}";
      executeStatement (connection, cmd2, parms1, false).getUpdateCount ();
     
      final String cmd3 = "insert into ${ArchiveDB}.opportunityaudit "
          + "(_fk_TestOpportunity, _fk_Session, AccessType, hostname, _fk_Browser, dateaccessed, dbname) "
          + " select ${oppkey}, _fk_session, 'expiredReported', ${localhost}, _fk_Browser, now(3), ${dbname}" 
          + " from testopportunity where _Key = ${oppkey}";
      SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("oppkey", oppkey).
          put ("localhost", _commonDll.getLocalhostName ()).put ("dbname", getTdsSettings ().getTDSSessionDBName ());
      executeStatement (connection, fixDataBaseNames (cmd3), parms3, false).getUpdateCount ();
      return;
    }
    
    if (DbComparator.notEqual (status, "submitted")) {
      final String cmd4 = "insert into ${ArchiveDB}.opportunityaudit "
          + "(_fk_TestOpportunity, _fk_Session, AccessType, hostname, _fk_Browser, dateaccessed, dbname) "
          + " select ${oppkey}, _fk_session, 'QA Reported', ${localhost}, _fk_Browser, now(3), ${dbname}" 
          + " from testopportunity where _Key = ${oppkey}";
      SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("oppkey", oppkey).
          put ("localhost", _commonDll.getLocalhostName ()).put ("dbname", getTdsSettings ().getTDSSessionDBName ());
      executeStatement (connection, fixDataBaseNames (cmd4), parms4, false).getUpdateCount (); 
      return;
    }
    
    SingleDataResultSet res5 = _commonDll.SetOpportunityStatus_SP (connection, oppkey, "reported", false );
    if (res5 != null) {
      DbResultRecord rec5 = res5.getRecords ().next ();
      if (rec5 != null) {
        String setStatus = rec5.<String> get ("status");
        if (DbComparator.isEqual (setStatus, "failed")) {
          _commonDll._LogDBError_SP (connection, "QC_AcceptOpportunity", "Unable to record 'QA reported' status", null, null, null, oppkey);
        }
      }
    }
  }
  
  private String replaceSeparatorChar (String str) {
    return str.replace ('/', java.io.File.separatorChar).replace ('\\', java.io.File.separatorChar);
  }

  
}
