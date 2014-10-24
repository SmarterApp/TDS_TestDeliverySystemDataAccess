/*******************************************************************************
 * Educational Online Test Delivery System Copyright (c) 2014 American
 * Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0 See accompanying
 * file AIR-License-1_0.txt or at http://www.smarterapp.org/documents/
 * American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.mysql;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import tds.dll.api.ICommonDLL;
import tds.dll.api.IReportingDLL;
import tds.dll.api.IRtsDLL;
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

public class CommonDLL extends AbstractDLL implements ICommonDLL
{
  private static Logger       _logger       = LoggerFactory.getLogger (CommonDLL.class);

  @Autowired
  private AbstractDateUtilDll _dateUtil     = null;

  @Autowired
  private IRtsDLL             _rtsDll       = null;

  @Autowired
  private IReportingDLL       _reportingDll = null;

  @Value ("${dbLockRetryAttemptMax:300}")
  private int                 gLockRetryAttemptMax;

  @Value ("${dbLockRetrySleepInterval:100}")
  private int                 gLockRetrySleepInterval;

  @Value ("${logLatencyInterval:59}")
  private int                 gLogLatencyInterval;

  @Value ("${logLatencyMaxTime:30000}")
  private int                 gLogLatencyMaxTime;

  /**
   * @param connection
   * @param testKey
   * @return
   * @throws ReturnStatusException
   */
  // Original port
  public MultiDataResultSet IB_GetTestAccommodations_SPV1 (SQLConnection connection, String testKey) throws ReturnStatusException {

    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();
    DataBaseTable testKeyAccomsTbl = TestKeyAccommodations_FN (connection, testKey);
    DataBaseTable testKeyAccomsDpndsTbl = TestKeyAccommodationDependencies_FN (connection, testKey);

    final String SQL_QUERY1 = " select * from ${testKeyAccomsTblName};";
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("testKeyAccomsTblName", testKeyAccomsTbl.getTableName ());
    SingleDataResultSet rs1 = executeStatement (connection, fixDataBaseNames (SQL_QUERY1, unquotedParms), null, false).getResultSets ().next ();
    resultsets.add (rs1);
    final String SQL_QUERY2 = " select * from ${testKeyAccomsDpndsTblName};";
    Map<String, String> unquotedParms1 = new HashMap<String, String> ();
    unquotedParms1.put ("testKeyAccomsDpndsTblName", testKeyAccomsDpndsTbl.getTableName ());
    SingleDataResultSet rs2 = executeStatement (connection, fixDataBaseNames (SQL_QUERY2, unquotedParms1), null, false).getResultSets ().next ();
    resultsets.add (rs2);

    return new MultiDataResultSet (resultsets);
  }

  // Optimization attempt
  public MultiDataResultSet IB_GetTestAccommodations_SP (SQLConnection connection, String testKey) throws ReturnStatusException {
    Date today = _dateUtil.getDateWRetStatus (connection);

    SingleDataResultSet rs1 = null;
    SingleDataResultSet rs2 = null;
    Long cachekey = null;

    final String cmd = "select _key as cachekey from ${ConfigDB}.__accommodationcache where "
        + "testkey = ${testkey} and dategenerated is not null and clientname = '--NONE--'";
    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("testkey", testKey);
    SingleDataResultSet rs = executeStatement (connection, fixDataBaseNames (cmd), parameters, false).getResultSets ().next ();
    DbResultRecord rec = (rs.getCount () > 0 ? rs.getRecords ().next () : null);
    if (rec != null) {
      cachekey = rec.<Long> get ("cachekey");
    }

    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();

    if (cachekey != null) {
      final String cmd1 = "select * from ${ConfigDB}.__cachedaccommodations where _fk_AccommodationCache = ${cachekey}";
      SqlParametersMaps parm1 = (new SqlParametersMaps ()).put ("cachekey", cachekey);
      rs1 = executeStatement (connection, fixDataBaseNames (cmd1), parm1, false).getResultSets ().next ();
      resultsets.add (rs1);

      final String cmd2 = "select * from ${ConfigDB}.__cachedaccomdepends where _fk_AccommodationCache = ${cachekey}";
      SqlParametersMaps parm2 = (new SqlParametersMaps ()).put ("cachekey", cachekey);
      rs2 = executeStatement (connection, fixDataBaseNames (cmd2), parm2, false).getResultSets ().next ();
      resultsets.add (rs2);

    } else {

      final String cmd3 = "insert into ${ConfigDB}.__accommodationcache (testkey, clientname, _date) " 
        + " select ${testkey}, '--NONE--', now(3) from dual where not exists "
        + "    (select * from ${ConfigDB}.__accommodationcache where testkey = ${testkey}) ";
      SqlParametersMaps parm3 = (new SqlParametersMaps ()).put ("testkey", testKey); 
      int insertedCnt = executeStatement (connection, fixDataBaseNames (cmd3), parm3, false).getUpdateCount ();
      
      rs1 = TestKeyAccommodationsAsSet (connection, testKey);
      resultsets.add (rs1);

      rs2 = TestKeyAccommodationDependenciesAsSet (connection, testKey);
      resultsets.add (rs2);
    }
    _LogDBLatency_SP (connection, "IB_GetTestAccommodations", today, 0L, true, null, null);

    return new MultiDataResultSet (resultsets);
  }

  /**
   * @param connection
   * @param tblName
   * @param testKey
   * @return
   * @throws ReturnStatusException
   */
  public DataBaseTable TestKeyAccommodations_FN (SQLConnection connection, String testKey) throws ReturnStatusException {

    String codeStr = ITEMBANK_TestLanguages_FN (connection, testKey);

    DataBaseTable testKeyAccomsTable = getDataBaseTable ("testKeyAccoms").addColumn ("Segment", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("DisableOnGuestSession", SQL_TYPE_To_JAVA_TYPE.BIT)
        .addColumn ("ToolTypeSortOrder", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("ToolValueSortOrder", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("TypeMode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 25)
        .addColumn ("ToolMode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 25).addColumn ("AccType", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 255).addColumn ("AccValue", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 255)
        .addColumn ("AccCode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 255).addColumn ("IsDefault", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("AllowCombine", SQL_TYPE_To_JAVA_TYPE.BIT)
        .addColumn ("IsFunctional", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("AllowChange", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("IsSelectable", SQL_TYPE_To_JAVA_TYPE.BIT)
        .addColumn ("IsVisible", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("studentControl", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("ValCount", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("DependsOnToolType", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50);
    connection.createTemporaryTable (testKeyAccomsTable);

    final String SQL_INSERT = "insert into ${tblName} (Segment, DisableOnGuestSession, ToolTypeSortOrder, ToolValueSortOrder, TypeMode, ToolMode, AccType, AccValue, AccCode, IsDefault, AllowCombine, IsFunctional, AllowChange,"
        + "IsSelectable, IsVisible, studentControl, ValCount, DependsOnToolType)"
        + " (SELECT distinct 0 as Segment, TType.DisableOnGuestSession, TType.SortOrder as ToolTypeSortOrder, TT.SortOrder as ToolValueSortOrder, TType.TestMode as TypeMode,"
        + " TT.TestMode as ToolMode, Type as AccType, Value as AccValue, Code as AccCode, IsDefault, AllowCombine, IsFunctional, AllowChange, IsSelectable, IsVisible, studentControl, "
        + " (select count(*) from ${ConfigDB}.client_testtool TOOL where TOOL.ContextType = ${TEST} and TOOL.Context = MODE.testID  and TOOL.clientname = MODE.clientname and TOOL.Type = TT.Type) as ValCount, "
        + " DependsOnToolType FROM ${ConfigDB}.client_testtooltype TType, ${ConfigDB}.client_testtool TT, ${ConfigDB}.client_testmode MODE"
        + " where MODE.testkey = ${testkey} and TType.ContextType = ${TEST} and TType.Context = MODE.testID and TType.ClientName = MODE.clientname "
        + " and TT.ContextType = ${TEST} and TT.Context = MODE.testID and TT.ClientName = MODE.clientname and TT.Type = TType.Toolname and (TT.Type <> ${Language} or TT.Code in (${codeStr})) "
        + " and (TType.TestMode = ${ALL} or TType.TestMode = MODE.mode) and (TT.TestMode = ${ALL} or TT.TestMode = MODE.mode)) "
        + " union all "
        + " (SELECT distinct SegmentPosition ,TType.DisableOnGuestSession, TType.SortOrder , TT.SortOrder, TType.TestMode , TT.TestMode, Type , Value , Code , IsDefault, AllowCombine, IsFunctional, AllowChange,"
        + " IsSelectable, IsVisible, studentControl, (select count(*) from ${ConfigDB}.client_testtool TOOL where TOOL.ContextType = ${TEST} and TOOL.Context = MODE.testID and "
        + " TOOL.clientname = MODE.clientname and TOOL.Type = TT.Type) as ValCount, null FROM ${ConfigDB}.client_testtooltype TType, ${ConfigDB}.client_testtool TT, ${ConfigDB}.client_segmentproperties SEG, "
        + " ${ConfigDB}.client_testmode MODE where parentTest = MODE.testID and MODE.testkey = ${testkey} and SEG.modekey = ${testkey} and TType.ContextType = ${SEGMENT} and TType.Context = segmentID and "
        + " TType.ClientName = MODE.clientname and TT.ContextType = ${SEGMENT} and TT.Context = segmentID and TT.ClientName = MODE.clientname and TT.Type = TType.Toolname and (TType.TestMode = ${ALL} or "
        + " TType.TestMode = MODE.mode) and (TT.TestMode = ${ALL} or TT.TestMode = MODE.mode)) "
        + " union all "
        + " (select distinct 0,TType.DisableOnGuestSession,  TType.SortOrder , TT.SortOrder, TType.TestMode , TT.TestMode, Type, Value, Code, "
        + " IsDefault, AllowCombine, IsFunctional, AllowChange, IsSelectable, IsVisible, studentControl, (select count(*) from ${ConfigDB}.client_testtool TOOL where TOOL.ContextType = ${TEST} and TOOL.Context = ${starParam}"
        + " and TOOL.clientname = MODE.clientname and TOOL.Type = TT.Type) as ValCount, DependsOnToolType FROM  ${ConfigDB}.client_testtooltype TType, ${ConfigDB}.client_testtool TT, ${ConfigDB}.client_testmode MODE"
        + " where MODE.testkey = ${testkey} and TType.ContextType = ${TEST} and TType.Context = ${starParam} and TType.ClientName = MODE.clientname and TT.ContextType = ${TEST} and TT.Context = ${starParam} and TT.ClientName = MODE.clientname"
        + " and TT.Type = TType.Toolname and (TType.TestMode = ${ALL} or TType.TestMode = MODE.mode) and (TT.TestMode = ${ALL} or TT.TestMode = MODE.mode)"
        + " and not exists "
        + " (select * from ${ConfigDB}.client_testtooltype Tool where Tool.ContextType = ${TEST} and Tool.Context = MODE.testID and Tool.Toolname = TType.Toolname and Tool.Clientname = MODE.clientname))";

    // Note that codeStr var is already comma separated list of quoted strings
    String query = fixDataBaseNames (SQL_INSERT);
    Map<String, String> unquotedparms = new HashMap<String, String> ();
    unquotedparms.put ("tblName", testKeyAccomsTable.getTableName ());
    unquotedparms.put ("codeStr", codeStr);

    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("testkey", testKey).put ("TEST", "TEST").put ("ALL", "ALL").put ("SEGMENT", "SEGMENT").put ("starParam", "*")
        .put ("Language", "Language");

    int insertedCnt = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, false).getUpdateCount ();

    return testKeyAccomsTable;
  }

  protected SingleDataResultSet TestKeyAccommodationsAsSet (SQLConnection connection, String testKey) throws ReturnStatusException {

    String codeStr = ITEMBANK_TestLanguages_FN (connection, testKey);

    final String SQL_INSERT =
        " (SELECT distinct bigtoint(0) as Segment, TType.DisableOnGuestSession, TType.SortOrder as ToolTypeSortOrder, TT.SortOrder as ToolValueSortOrder, TType.TestMode as TypeMode,"
            + " TT.TestMode as ToolMode, Type as AccType, Value as AccValue, Code as AccCode, "
            + " IsDefault, AllowCombine, IsFunctional, AllowChange, IsSelectable, IsVisible, studentControl, "
            + " (select count(*) from ${ConfigDB}.client_testtool TOOL where TOOL.ContextType = ${TEST} and TOOL.Context = MODE.testID "
            + "   and TOOL.clientname = MODE.clientname and TOOL.Type = TT.Type) as ValCountL, "
            + " DependsOnToolType FROM ${ConfigDB}.client_testtooltype TType, ${ConfigDB}.client_testtool TT, ${ConfigDB}.client_testmode MODE"
            + " where MODE.testkey = ${testkey} and TType.ContextType = ${TEST} and TType.Context = MODE.testID and TType.ClientName = MODE.clientname "
            + " and TT.ContextType = ${TEST} and TT.Context = MODE.testID and TT.ClientName = MODE.clientname and TT.Type = TType.Toolname and (TT.Type <> ${Language} or TT.Code in (${codeStr})) "
            + " and (TType.TestMode = ${ALL} or TType.TestMode = MODE.mode) and (TT.TestMode = ${ALL} or TT.TestMode = MODE.mode)) "
            + " union all "
            + " (SELECT distinct SegmentPosition as Segment, TType.DisableOnGuestSession, TType.SortOrder as ToolTypeSortOrder, TT.SortOrder as ToolValueSortOrder, TType.TestMode as TypeMode,"
            + " TT.TestMode as ToolMode, Type as AccType, Value as AccValue, Code as AccCode, "
            + " IsDefault, AllowCombine, IsFunctional, AllowChange, IsSelectable, IsVisible, studentControl, "
            + " (select count(*) from ${ConfigDB}.client_testtool TOOL where TOOL.ContextType = ${TEST} and TOOL.Context = MODE.testID and "
            + " TOOL.clientname = MODE.clientname and TOOL.Type = TT.Type) as ValCountL, "
            + " cast(null as CHAR) as DependsOnToolType FROM ${ConfigDB}.client_testtooltype TType, ${ConfigDB}.client_testtool TT, ${ConfigDB}.client_segmentproperties SEG, "
            + " ${ConfigDB}.client_testmode MODE where parentTest = MODE.testID and MODE.testkey = ${testkey} and SEG.modekey = ${testkey} and TType.ContextType = ${SEGMENT} and TType.Context = segmentID and "
            + " TType.ClientName = MODE.clientname and TT.ContextType = ${SEGMENT} and TT.Context = segmentID and TT.ClientName = MODE.clientname and TT.Type = TType.Toolname and (TType.TestMode = ${ALL} or "
            + " TType.TestMode = MODE.mode) and (TT.TestMode = ${ALL} or TT.TestMode = MODE.mode)) "
            + " union all "
            + " (select distinct bigtoint(0) as Segment, TType.DisableOnGuestSession,  TType.SortOrder as ToolTypeSortOrder, TT.SortOrder as ToolValueSortOrder, TType.TestMode as TypeMode, "
            + " TT.TestMode as ToolMode, Type as AccType, Value as AccValue, Code as AccCode, "
            + " IsDefault, AllowCombine, IsFunctional, AllowChange, IsSelectable, IsVisible, studentControl, "
            + " (select count(*) from ${ConfigDB}.client_testtool TOOL where TOOL.ContextType = ${TEST} and TOOL.Context = ${starParam}"
            + " and TOOL.clientname = MODE.clientname and TOOL.Type = TT.Type) as ValCountL, "
            + " DependsOnToolType FROM  ${ConfigDB}.client_testtooltype TType, ${ConfigDB}.client_testtool TT, ${ConfigDB}.client_testmode MODE"
            + " where MODE.testkey = ${testkey} and TType.ContextType = ${TEST} and TType.Context = ${starParam} and TType.ClientName = MODE.clientname and TT.ContextType = ${TEST} and TT.Context = ${starParam} and TT.ClientName = MODE.clientname"
            + " and TT.Type = TType.Toolname and (TType.TestMode = ${ALL} or TType.TestMode = MODE.mode) and (TT.TestMode = ${ALL} or TT.TestMode = MODE.mode)"
            + " and not exists "
            + " (select * from ${ConfigDB}.client_testtooltype Tool where Tool.ContextType = ${TEST} and Tool.Context = MODE.testID and Tool.Toolname = TType.Toolname and Tool.Clientname = MODE.clientname))";

    String query = fixDataBaseNames (SQL_INSERT);

    // Note that codeStr var is already comma separated list of quoted strings
    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("testkey", testKey).put ("TEST", "TEST").put ("ALL", "ALL").put ("SEGMENT", "SEGMENT").put ("starParam", "*")
        .put ("Language", "Language");

    Map<String, String> unquotedparms = new HashMap<String, String> ();
    unquotedparms.put ("codeStr", codeStr);

    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, false).getResultSets ().next ();
    result.addColumn ("valCount", SQL_TYPE_To_JAVA_TYPE.INT);
    Iterator<DbResultRecord> records = result.getRecords ();
    while (records.hasNext ()) {
      DbResultRecord record = records.next ();
      Long cnt = record.<Long> get ("valCountL");
      record.addColumnValue ("valCount", (cnt == null ? null : cnt.intValue ()));
    }
    return result;
  }

  /**
   * @param connection
   * @param testKey
   * @return
   * @throws ReturnStatusException
   */
  public DataBaseTable TestKeyAccommodationDependencies_FN (SQLConnection connection, String testKey) throws ReturnStatusException {

    DataBaseTable testKeyAccomDepdncsTable = getDataBaseTable ("testKeyAccomDepdncs").addColumn ("clientname", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100)
        .addColumn ("TestKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).addColumn ("contextType", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("context", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250)
        .addColumn ("TestMode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 25).addColumn ("ifType", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("ifvalue", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 255)
        .addColumn ("thenType", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("thenValue", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 255).addColumn ("IsDefault", SQL_TYPE_To_JAVA_TYPE.BIT);
    connection.createTemporaryTable (testKeyAccomDepdncsTable);

    final String SQL_INSERT = "insert into ${tblName} (clientname, TestKey, contextType, context, TestMode, ifType, ifvalue, thenType, thenValue, IsDefault) " +
        " (select distinct M.clientname, M.TestKey, ContextType, M.testID as Context, TestMode, IfType, IfValue, ThenType, ThenValue, IsDefault from ${ConfigDB}.client_tooldependencies TD, " +
        " ${ConfigDB}.client_testmode M where M.testkey = ${testkey} and TD.ContextType = ${TEST} and TD.Context = M.TestID and TD.Clientname = M.clientname )" +
        " union all " +
        " (select distinct M.clientname, M.TestKey, ContextType, M.TestID as Context, TestMode, IfType, IfValue, ThenType, ThenValue, IsDefault" +
        " from ${ConfigDB}.client_tooldependencies TD, ${ConfigDB}.client_testmode M where M.Testkey = ${testkey} and TD.clientname = M.clientname and ContextType = ${TEST}" +
        " and Context = ${starParam} and (TD.TestMode = ${ALL} or TD.TestMode = M.mode)" +
        " and not exists " +
        "(select * from ${ConfigDB}.client_tooldependencies TD2 where TD2.ContextType = ${TEST} and TD2.Context = M.TestID and TD.Clientname = M.clientname " +
        "and TD.IfType = TD2.IfType and TD.IfValue = TD2.IfValue and TD.ThenType = TD2.ThenType and TD.ThenValue = TD2.ThenValue))";

    String query = fixDataBaseNames (SQL_INSERT);
    Map<String, String> unquotedparms = new HashMap<String, String> ();
    unquotedparms.put ("tblName", testKeyAccomDepdncsTable.getTableName ());
    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("testkey", testKey).put ("TEST", "TEST").put ("ALL", "ALL").put ("starParam", "*");
    int insertedCnt = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, false).getUpdateCount ();

    return testKeyAccomDepdncsTable;
  }

  public SingleDataResultSet TestKeyAccommodationDependenciesAsSet (SQLConnection connection, String testKey) throws ReturnStatusException {

    final String SQL_INSERT =
        " (select distinct M.clientname, M.TestKey, ContextType, M.testID as Context, TestMode, IfType, IfValue, ThenType, ThenValue, IsDefault " +
            "  from ${ConfigDB}.client_tooldependencies TD, ${ConfigDB}.client_testmode M " +
            "  where M.testkey = ${testkey} and TD.ContextType = ${TEST} and TD.Context = M.TestID and TD.Clientname = M.clientname )" +
            " union all " +
            " (select distinct M.clientname, M.TestKey, ContextType, M.TestID as Context, TestMode, IfType, IfValue, ThenType, ThenValue, IsDefault" +
            " from ${ConfigDB}.client_tooldependencies TD, ${ConfigDB}.client_testmode M " +
            "  where M.Testkey = ${testkey} and TD.clientname = M.clientname and ContextType = ${TEST}" +
            " and Context = ${starParam} and (TD.TestMode = ${ALL} or TD.TestMode = M.mode)" +
            " and not exists " +
            "(select * from ${ConfigDB}.client_tooldependencies TD2 where TD2.ContextType = ${TEST} and TD2.Context = M.TestID and TD.Clientname = M.clientname " +
            "and TD.IfType = TD2.IfType and TD.IfValue = TD2.IfValue and TD.ThenType = TD2.ThenType and TD.ThenValue = TD2.ThenValue))";

    String query = fixDataBaseNames (SQL_INSERT);

    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("testkey", testKey).put ("TEST", "TEST").put ("ALL", "ALL").put ("starParam", "*");
    SingleDataResultSet result = executeStatement (connection, query, parameters, false).getResultSets ().next ();

    return result;
  }

  /**
   * This method differs from SQL function because it returns comma separated,
   * single-quoted list of codes only(but not labels) selected VS SQL function
   * returning a table with code and label as columns It is suitable for the
   * manner in which they method is used and it decreases number of temporary
   * tables created.
   * 
   * @param connection
   * @param testKey
   * @return
   * @throws ReturnStatusException
   */
  public String ITEMBANK_TestLanguages_FN (SQLConnection connection, String testKey) throws ReturnStatusException {

    String codeStr = "";
    Boolean segmented = false;
    String algorithm = null;

    final String SQL_QUERY1 = "select IsSegmented as segmented, selectionalgorithm as algorithm from ${ItemBankDB}.tblsetofadminsubjects where _KEy = ${testkey};";
    String query1 = fixDataBaseNames (SQL_QUERY1);
    SqlParametersMaps parameters1 = (new SqlParametersMaps ()).put ("testkey", testKey);
    SingleDataResultSet result = executeStatement (connection, query1, parameters1, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      segmented = record.<Boolean> get ("segmented");
      algorithm = record.<String> get ("algorithm");
    }
    if (DbComparator.isEqual (segmented, false)) {
      if (DbComparator.isEqual ("fixedform", algorithm)) {
        final String SQL_QUERY2 = " select distinct propvalue as code, propdescription as label from ${ItemBankDB}.tblitemprops P, ${ItemBankDB}.testform F where P._fk_AdminSubject = ${testKey} and propname = ${language}"
            + " and F._fk_AdminSubject = ${testkey} and F.Language = P.propvalue and P.isactive = 1";
        String query2 = fixDataBaseNames (SQL_QUERY2);
        SqlParametersMaps parameters2 = (new SqlParametersMaps ()).put ("testkey", testKey).put ("language", "language");
        result = executeStatement (connection, query2, parameters2, false).getResultSets ().next ();
        Iterator<DbResultRecord> records = result.getRecords ();
        while (records.hasNext ()) {
          record = records.next ();
          String code = record.<String> get ("code");
          if (code != null && code.isEmpty () == false) {
            if (codeStr.isEmpty ())
              codeStr = String.format ("'%s'", code);
            else
              codeStr += String.format (",'%s'", code);
          }
        }
      } else {
        final String SQL_QUERY3 = "select distinct propvalue as code, propdescription as label from  ${ItemBankDB}.tblitemprops P where P._fk_AdminSubject = ${testKey} and propname = ${language} and isactive = 1";
        String query3 = fixDataBaseNames (SQL_QUERY3);
        SqlParametersMaps parameters3 = (new SqlParametersMaps ()).put ("testkey", testKey).put ("Language", "Language");
        result = executeStatement (connection, query3, parameters3, false).getResultSets ().next ();
        Iterator<DbResultRecord> records = result.getRecords ();
        while (records.hasNext ()) {
          record = records.next ();
          String code = record.<String> get ("code");
          if (code != null && code.isEmpty () == false) {
            if (codeStr.isEmpty ())
              codeStr = String.format ("'%s'", code);
            else
              codeStr += String.format (",'%s'", code);
          }
        }
      }
    } else {
      final String SQL_QUERY4 = "select distinct propvalue as code, propdescription as label from ${ItemBankDB}.tblsetofadminitems A, ${ItemBankDB}.tblitemprops P, ${ItemBankDB}.tblsetofadminsubjects S where S.VirtualTest = ${testkey} "
          + "and A._fk_AdminSubject = S._Key and A._fk_AdminSubject = P._fk_AdminSubject and A._fk_Item = P._fk_Item and propname = ${language} and P.isactive = 1";
      String query4 = fixDataBaseNames (SQL_QUERY4);
      SqlParametersMaps parameters4 = (new SqlParametersMaps ()).put ("testkey", testKey).put ("Language", "Language");
      result = executeStatement (connection, query4, parameters4, false).getResultSets ().next ();
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        record = records.next ();
        String code = record.<String> get ("code");
        if (code != null && code.isEmpty () == false) {
          if (codeStr.isEmpty ())
            codeStr = String.format ("'%s'", code);
          else
            codeStr += String.format (",'%s'", code);
        }
      }
    }
    return codeStr;
  }

  /**
   * @param connection
   * @param clientname
   * @param itemKey
   * @return
   * @throws ReturnStatusException
   */
  public String ClientItemFile_FN (SQLConnection connection, String clientName, String itemKey) throws ReturnStatusException {
    String path = null;
    final String SQL_QUERY = "select concat(C.Homepath, B.HomePath, B.ItemPath, I.FilePath, I.FileName) as path from ${ItemBankDB}.tblitembank B, ${ItemBankDB}.tblclient C, ${ItemBankDB}.tblitem I where B._efk_Itembank = I._efk_ItemBank and C.name = ${clientName}"
        + "and B._fk_Client = C._Key and I._Key = ${itemkey}";
    String query = fixDataBaseNames (SQL_QUERY);
    SqlParametersMaps parameters = new SqlParametersMaps ().put ("clientName", clientName).put ("itemKey", itemKey);
    SingleDataResultSet result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      path = record.<String> get ("path");
      path = replaceSeparatorChar (path);
    }
    // path = path.replace ('/', java.io.File.separatorChar).replace ('\\',
    // java.io.File.separatorChar);
    return path;
  }

  /**
   * Below two functions(i.e MakeItemKey_FN, MakeStimulusKey_FN) has same
   * functionality except one i/p parameter in future, we can make these 2
   * functions as a single function to minimize coding.
   * 
   * @param connection
   * @param bankkey
   * @param itemkey
   * @return
   */
  public String MakeItemKey_FN (SQLConnection connection, Long bankKey, Long itemKey) {

    String itemKeyStr = null;
    if (bankKey != null && itemKey != null)
      itemKeyStr = String.format ("%d-%d", bankKey, itemKey);

    return itemKeyStr;
  }

  /**
   * @param connection
   * @param bankkey
   * @param stimkey
   * @return
   */
  public String MakeStimulusKey_FN (SQLConnection connection, Long bankKey, Long stimulusKey) {

    String stimKeyStr = null;
    if (bankKey != null && stimulusKey != null)
      stimKeyStr = String.format ("%d-%d", bankKey, stimulusKey);

    return stimKeyStr;
  }

  public boolean _IsValidStatusTransition_FN (String oldStatus, String newStatus) {
    boolean ok;

    switch (oldStatus) {
    case "pending":
      switch (newStatus) {
      case "initializing":
      case "pending":
      case "denied":
      case "approved":
      case "paused":
      case "expired":
      case "invalidated":
      case "forceCompleted":
        ok = true;
        break;
      default:
        ok = false;
      }
      break;
    case "suspended":
      switch (newStatus) {
      case "suspended":
      case "denied":
      case "approved":
      case "paused":
      case "expired":
      case "invalidated":
      case "forceCompleted":
        ok = true;
        break;
      default:
        ok = false;
      }
      break;
    case "started":
      switch (newStatus) {
      case "started":
      case "paused":
      case "review":
      case "completed":
      case "expired":
      case "invalidated":
      case "segmentEntry":
      case "segmentExit":
      case "forceCompleted":
        ok = true;
        break;
      default:
        ok = false;
      }
      break;
    case "approved":
      switch (newStatus) {
      case "approved":
      case "pending":
      case "started":
      case "paused":
      case "expired":
      case "invalidated":
      case "forceCompleted":
        ok = true;
        break;
      default:
        ok = false;
      }
      break;
    case "review":
      switch (newStatus) {
      case "review":
      case "completed":
      case "paused":
      case "expired":
      case "invalidated":
      case "forceCompleted":
      case "segmentEntry":
        ok = true;
        break;
      default:
        ok = false;
      }
      break;
    case "paused":
      switch (newStatus) {
      case "paused":
      case "pending":
      case "suspended":
      case "expired":
      case "invalidated":
      case "forceCompleted":
        ok = true;
        break;
      default:
        ok = false;
      }
      break;
    case "denied":
      switch (newStatus) {
      case "denied":
      case "pending":
      case "suspended":
      case "paused":
      case "expired":
      case "invalidated":
      case "forceCompleted":
        ok = true;
        break;
      default:
        ok = false;
      }
      break;
    case "completed":
      switch (newStatus) {
      case "completed":
      case "scored":
      case "submitted":
      case "invalidated":
        ok = true;
        break;
      default:
        ok = false;
      }
      break;
    case "scored":
      switch (newStatus) {
      case "rescored":
      case "submitted":
      case "invalidated":
        ok = true;
        break;
      default:
        ok = false;
      }
      break;
    case "submitted":
      switch (newStatus) {
      case "rescored":
      case "reported":
      case "invalidated":
        ok = true;
        break;
      default:
        ok = false;
      }
      break;
    case "reported":
      switch (newStatus) {
      case "rescored":
      case "invalidated":
        ok = true;
        break;
      default:
        ok = false;
      }
      break;
    case "expired":
      switch (newStatus) {
      case "rescored":
      case "invalidated":
        ok = true;
        break;
      default:
        ok = false;
      }
      break;
    case "invalidated":
      switch (newStatus) {
      case "rescored":
      case "invalidated":
        ok = true;
        break;
      default:
        ok = false;
      }
      break;
    case "rescored":
      switch (newStatus) {
      case "scored":
        ok = true;
        break;
      default:
        ok = false;
      }
      break;
    case "segmentEntry":
      switch (newStatus) {
      case "approved":
      case "denied":
      case "expired":
      case "invalidated":
      case "forceCompleted":
        ok = true;
        break;
      default:
        ok = false;
      }
      break;
    case "segmentExit":
      switch (newStatus) {
      case "approved":
      case "denied":
      case "expired":
      case "invalidated":
      case "forceCompleted":
        ok = true;
        break;
      default:
        ok = false;
      }
      break;
    case "forceCompleted":
      switch (newStatus) {
      case "completed":
      case "scored":
        ok = true;
        break;
      default:
        ok = false;
      }
      break;
    case "initializing":
      switch (newStatus) {
      case "initializing":
      case "pending":
      case "denied":
      case "approved":
      case "paused":
      case "expired":
      case "invalidated":
      case "forceCompleted":
        ok = true;
        break;
      default:
        ok = false;
      }
      break;
    default:
      ok = false;
      break;
    }
    return ok;
  }

  public String _CanChangeOppStatus_FN (SQLConnection connection, String oldstatus, String newstatus) {

    if (_IsValidStatusTransition_FN (oldstatus, newstatus) == false)
      return String.format ("Cannot change opportunity from %1$s to %2$s", oldstatus, newstatus);

    return null;
  }

  public Boolean ScoreByTDS_FN (SQLConnection connection, String clientName, String testId) throws ReturnStatusException {
    Boolean sc = false;

    final String SQL_QUERY = "select clientname from ${ConfigDB}.client_testscorefeatures where clientname = ${client} and TestID = ${testID} "
        + " and (ReportToStudent = 1 or ReportToProctor = 1 or ReportToParticipation = 1 or UseForAbility = 1) limit 1";
    SqlParametersMaps parameters = new SqlParametersMaps ();
    parameters.put ("client", clientName);
    parameters.put ("testID", testId);

    if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY), parameters, false))) {
      sc = true;
    } else
      sc = false;

    return sc;
  }

  public String CanScoreOpportunity_FN (SQLConnection connection, UUID oppkey) throws ReturnStatusException {
    Boolean scorable = false;

    final String SQL_QUERY1 = "select _efk_TestID as test, clientname from testopportunity where _key = ${oppkey}";
    SqlParametersMaps parameters1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parameters1, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    String test = null;
    String clientname = null;

    if (record != null) {
      test = record.<String> get ("test");
      clientname = record.<String> get ("clientname");
    }
    Boolean scoreByTds = ScoreByTDS_FN (connection, clientname, test);

    final String SQL_QUERY2 = "select _fk_TestOpportunity from testopportunitysegment  "
        + " where _fk_TestOpportunity = ${oppkey} and IsSatisfied = 0 limit 1";
    SqlParametersMaps parameters2 = parameters1;

    if (exists (executeStatement (connection, SQL_QUERY2, parameters2, false))) {
      return "Blueprint not satisfied";
    }

    Long ok = 0L;
    Date archived = null;
    Date scored = null;

    final String SQL_QUERY3 = "select  1 as ok, items_Archived as archived, datescored as scored from testopportunity "
        + " where _Key = ${oppkey} and datecompleted is not null";

    SqlParametersMaps parameters3 = parameters1;

    result = executeStatement (connection, SQL_QUERY3, parameters3, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      ok = record.<Long> get ("ok");
      archived = record.<Date> get ("archived");
      scored = record.<Date> get ("scored");
    }
    if (ok == null || ok == 0)
      return "Test has not completed";

    if (archived == null) {
      final String SQL_QUERY4 = "select _fk_TestOpportunity from testeeresponse "
          + " where _fk_TestOpportunity = ${oppkey} and (scorestatus in ('ForMachineScoring','WaitingForMachineScore')) limit 1";

      SqlParametersMaps parameters4 = parameters1;

      if (exists (executeStatement (connection, SQL_QUERY4, parameters4, false)) == true)
        return "Items remain to be scored";

      final String SQL_QUERY5 = "select _fk_TestOpportunity from testeeresponse "
          + " where _fk_TestOpportunity = ${oppkey} and score = -1 and IsFieldTest = 0 limit 1";
      SqlParametersMaps parameters5 = parameters1;

      if (exists (executeStatement (connection, SQL_QUERY5, parameters5, false)) == false)
        scorable = true;
    } else {

      final String SQL_QUERY6 = "select  _fk_TestOpportunity from testeeresponsearchive "
          + " where _fk_TestOpportunity = ${oppkey} and scorestatus in ('ForMachineScoring','WaitingForMachineScore') limit 1";
      SqlParametersMaps parameters6 = parameters1;

      if (exists (executeStatement (connection, SQL_QUERY6, parameters6, false)) == true)
        return "Items remain to be scored";

      final String SQL_QUERY7 = "select  _fk_TestOpportunity from testeeresponsearchive "
          + " where _fk_TestOpportunity = ${oppkey} and score = -1 and IsFieldTest = 0 limit 1";
      SqlParametersMaps parameters7 = parameters1;

      if (exists (executeStatement (connection, SQL_QUERY7, parameters7, false)) == false)
        scorable = true;
    }
    if (DbComparator.isEqual (scorable, false) && DbComparator.isEqual (scoreByTds, true))
      return "Unofficial score only";

    if (DbComparator.isEqual (scoreByTds, false))
      return "COMPLETE: Do Not Score";

    return null;
  }

  /**
   * This function uses temp table. However it is only used to build comma
   * separated list of values in this one-column table We will not be using temp
   * table for string manipulation (if possible). Note how we built comma
   * separated list based on records in SingleDataResultSet
   * 
   * @param connection
   * @param oppkey
   * @return String
   * @throws ReturnStatusException
   */
  public String MakeItemGroupString_FN (SQLConnection connection, UUID oppkey) throws ReturnStatusException {
    final String SQL_QUERY = "select distinct groupID from testeeresponse where _fk_TestOpportunity = ${oppkey} and _efk_ITSItem is not null;";
    SqlParametersMaps parms = (new SqlParametersMaps ()).put ("oppkey", oppkey);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY, parms, false).getResultSets ().next ();
    if (result.getCount () == 0)
      return "";

    String itemGroup = null;
    Iterator<DbResultRecord> records = result.getRecords ();
    while (records.hasNext ()) {
      DbResultRecord record = records.next ();

      if (itemGroup == null)
        itemGroup = record.<String> get ("groupID");
      else
        itemGroup += "," + record.<String> get ("groupID");
    }
    return itemGroup;
  }

  public int IsXMLOn_Fn (SQLConnection connection, UUID oppKey) throws ReturnStatusException {

    final String SQL_QUERY1 = "select clientname, environment from testopportunity where _Key = ${oppkey}";
    SqlParametersMaps parameters1 = (new SqlParametersMaps ()).put ("oppkey", oppKey);

    String clientname = null;
    String environment = null;

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parameters1, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      clientname = record.<String> get ("clientname");
      environment = record.<String> get ("environment");
    }
    // we substitute coalesce(IsOn, 0) when we read the record
    final String SQL_QUERY2 = "select IsOn as flag "
        + " from ${ConfigDB}.client_systemflags F, externs E "
        + " where E.ClientName = ${clientname} and F.clientname = ${clientname}"
        + "  and E.IsPracticeTest = F.IsPracticeTest and AuditOBject='oppreport'";
    SqlParametersMaps parameters2 = (new SqlParametersMaps ()).put ("clientname", clientname);

    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY2), parameters2, false).getResultSets ().next ();
    Integer flag = null;
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      flag = record.<Integer> get ("flag");
      if (flag == null)
        flag = 0;
    }
    // TODO Elena: commented check for QABrokerGUID while
    // we are using flat files to store qa reports and
    // thus qabrokerGUID is irrelevant.
    // We may reconsider/reuse this column when we move to another way of
    // queuing reports

    // final String SQL_QUERY3 =
    // "select QABrokerGUID as guid from externs where clientname = ${clientname} and environment = ${env}";
    // SqlParametersMaps parameters3 = (new SqlParametersMaps ()).put
    // ("clientname", clientname).put ("env", environment);
    //
    // result = executeStatement (connection, SQL_QUERY3, parameters3,
    // false).getResultSets ().next ();
    // UUID guid = null;
    // record = result.getCount () > 0 ? result.getRecords ().next () : null;
    // if (record != null) {
    // guid = record.<UUID> get ("guid");
    // }

    if (DbComparator.isEqual (flag, 0)
    // || guid == null
    )
      return 0;
    else
      return 1;
  }

  // original port
  public SingleDataResultSet _GetTesteeAttributes_SPV1 (SQLConnection connection, String clientname, Long testee) throws ReturnStatusException {

    DataBaseTable attributesTable = getDataBaseTable ("gtaAttributes").addColumn ("attname", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).
        addColumn ("rtsname", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("attval", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 8000).
        addColumn ("done", SQL_TYPE_To_JAVA_TYPE.BIT);
    connection.createTemporaryTable (attributesTable);

    final String SQL_INSERT1 = "insert into ${attributes} (attname,  rtsName) select TDS_ID, RTSName "
        + " from ${ConfigDB}.client_testeeattribute where clientname = ${clientname} and type = 'attribute'";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("clientname", clientname);
    Map<String, String> unquotedParms1 = new HashMap<String, String> ();
    unquotedParms1.put ("attributes", attributesTable.getTableName ());

    final String query1 = fixDataBaseNames (SQL_INSERT1);
    int insertedCnt = executeStatement (connection, fixDataBaseNames (query1, unquotedParms1), parms1, false).getUpdateCount ();

    final String SQL_QUERY2 = "select done from ${attributes} where done is null limit 1";
    Map<String, String> unquotedParms2 = unquotedParms1;

    while (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY2, unquotedParms2), null, false))) {

      final String SQL_QUERY3 = "select attname, rtsName from ${attributes} where done is null limit 1";
      Map<String, String> unquotedParms3 = unquotedParms1;

      SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY3, unquotedParms3), null, false).getResultSets ().next ();
      DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      String attname = null;
      String rtsname = null;
      if (record != null) {
        attname = record.<String> get ("attname");
        rtsname = record.<String> get ("rtsname");
      }

      String attval = null;

      if (testee > 0) {
        _Ref<String> attvalRef = new _Ref<String> ();
        _rtsDll._GetRTSAttribute_SP (connection, clientname, testee, rtsname, attvalRef);
        attval = attvalRef.get ();

      } else {
        attval = String.format ("GUEST %s", attname);
      }

      if (attval != null) {
        final String SQL_UPDATE4 = "update ${attributes} set attval = ${attval}, done = 1 where attname = ${attname}";
        SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("attname", attname).put ("attval", attval);
        Map<String, String> unquotedParms4 = unquotedParms1;

        int updateCnt = executeStatement (connection, fixDataBaseNames (SQL_UPDATE4, unquotedParms4), parms4, false).getUpdateCount ();

      } else {
        String err = String.format ("Unknown attribute type:  %s", (attname == null ? "<NULL ATTRIBUTE>" : attname));

        final String SQL_UPDATE5 = "update ${attributes} set done = 1 where attname = ${attname}";
        SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("attname", attname);
        Map<String, String> unquotedParms5 = unquotedParms1;

        int updateCnt = executeStatement (connection, fixDataBaseNames (SQL_UPDATE5, unquotedParms5), parms5, false).getUpdateCount ();
        _LogDBError_SP (connection, "_GetTesteeAttributes", err, testee, null, null, null, clientname, null);
      }
    }
    final String SQL_QUERY6 = "select attname as TDS_ID, attval from ${attributes}";
    Map<String, String> unquotedParms6 = unquotedParms1;

    SingleDataResultSet res = executeStatement (connection, fixDataBaseNames (SQL_QUERY6, unquotedParms6), null, false).getResultSets ().next ();
    connection.dropTemporaryTable (attributesTable);
    return res;
  }

  // Optimization attempt
  public SingleDataResultSet _GetTesteeAttributes_SP (SQLConnection connection, String clientname, Long testee) throws ReturnStatusException {

    // DataBaseTable attributesTable = getDataBaseTable
    // ("gtaAttributes").addColumn ("attname", SQL_TYPE_To_JAVA_TYPE.VARCHAR,
    // 50).
    // addColumn ("rtsname", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn
    // ("attval", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 8000).
    // addColumn ("done", SQL_TYPE_To_JAVA_TYPE.BIT);

    final String SQL_CMD1 = "select TDS_ID, RTSName,  cast(null as CHAR) as attval, cast(null as SIGNED) as done "
        + " from ${ConfigDB}.client_testeeattribute where clientname = ${clientname} and type = 'attribute'";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("clientname", clientname);

    List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();

    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_CMD1), parms1, false).getResultSets ().next ();
    Iterator<DbResultRecord> records = result.getRecords ();
    while (records.hasNext ()) {
      DbResultRecord record = records.next ();
      String attname = record.<String> get ("tds_id");
      String rtsname = record.<String> get ("rtsname");

      CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
      rcd.put ("tds_id", attname);
      rcd.put ("rtsname", rtsname);
      rcd.put ("attval", null);
      rcd.put ("done", null);
      resultList.add (rcd);
    }

    for (CaseInsensitiveMap<Object> rcd : resultList) {

      String attval = null;
      String attname = (String) rcd.get ("tds_id");
      if (testee > 0) {
        _Ref<String> attvalRef = new _Ref<String> ();
        _rtsDll._GetRTSAttribute_SP (connection, clientname, testee, (String) rcd.get ("rtsname"), attvalRef);
        attval = attvalRef.get ();

      } else {
        attval = String.format ("GUEST %s", attname);
      }
      // TODO: (Dan) Presently we are simply returning null for missing
      // attributes.

      // if (attval != null) {
      rcd.put ("attval", attval);
      rcd.put ("done", 1);
      // }
      // else {
      // String err = String.format ("Unknown attribute type:  %s", (attname ==
      // null ? "<NULL ATTRIBUTE>" : attname));
      // _LogDBError_SP (connection, "_GetTesteeAttributes", err, testee, null,
      // null, null, clientname, null);
      // rcd.put ("done", 1);
      // }
    }

    for (CaseInsensitiveMap<Object> rcd : resultList) {
      rcd.remove ("done");
      rcd.remove ("rtsname");
    }

    SingleDataResultSet result1 = new SingleDataResultSet ();
    result1.addColumn ("tds_id", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    result1.addColumn ("attval", SQL_TYPE_To_JAVA_TYPE.VARCHAR);

    result1.addRecords (resultList);

    return result1;
  }

  // original port
  public void _SetTesteeAttributes_SPV1 (SQLConnection connection, String clientname, UUID oppkey, Long testee, String context) throws ReturnStatusException {
    if (testee < 0)
      return;
    Date starttime = _dateUtil.getDateWRetStatus (connection);

    Boolean attsexist = null, relsexist = null;

    DataBaseTable attsTable = getDataBaseTable ("staAtts").addColumn ("tdsId", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("attrval", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 500);

    DataBaseTable relsTable = getDataBaseTable ("staRels").addColumn ("reltype", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).
        addColumn ("entityKey", SQL_TYPE_To_JAVA_TYPE.BIGINT).addColumn ("tdsId", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).
        addColumn ("attrval", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 500);

    final String SQL_QUERY1 = "select _fk_TestOpportunity from testeeattribute where _fk_TestOpportunity = ${oppkey} and context = ${context} limit 1";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey).put ("context", context);

    if (exists (executeStatement (connection, SQL_QUERY1, parms1, false))) {
      attsexist = true;
    } else {
      attsexist = false;
    }

    final String SQL_QUERY2 = "select _fk_TestOpportunity from testeerelationship where _fk_TestOpportunity = ${oppkey} and context = ${context} limit 1";
    SqlParametersMaps parms2 = parms1;

    if (exists (executeStatement (connection, SQL_QUERY2, parms2, false))) {
      relsexist = true;
    } else {
      relsexist = false;
    }
    final String finalClientname = clientname;
    final Long finalTestee = testee;
    executeMethodAndInsertIntoTemporaryTable (connection, new AbstractDataResultExecutor ()
    {

      @Override
      public SingleDataResultSet execute (SQLConnection connection) throws ReturnStatusException {
        SingleDataResultSet resultSet = _GetTesteeAttributes_SPV1 (connection, finalClientname, finalTestee);

        resultSet.resetColumnName (1, "tdsID");
        resultSet.resetColumnName (2, "attrval");
        return resultSet;
      }
    }, attsTable, true);

    String sofar = "First. ";

    executeMethodAndInsertIntoTemporaryTable (connection, new AbstractDataResultExecutor ()
    {

      @Override
      public SingleDataResultSet execute (SQLConnection connection) throws ReturnStatusException {

        SingleDataResultSet resultSet = _rtsDll._GetTesteeRelationships_SP (connection, finalClientname, finalTestee);

        resultSet.resetColumnName (1, "reltype");
        resultSet.resetColumnName (3, "tdsID");
        // resultSet.resetColumnName (2, "entitykey");
        resultSet.resetColumnName (4, "attrval");
        return resultSet;
      }
    }, relsTable, true);

    sofar = "Second. ";

    try {
      boolean preexistingAutoCommitMode = connection.getAutoCommit ();
      connection.setAutoCommit (false);
      if (DbComparator.isEqual (attsexist, true)) {
        final String SQL_DELETE3 = "delete from testeeattribute where _fk_TestOpportunity = ${oppkey} and context = ${context}";
        SqlParametersMaps parms3 = parms1;

        int deletedCnt = executeStatement (connection, SQL_DELETE3, parms3, false).getUpdateCount ();
        sofar = "Third. ";
      }

      final String SQL_INSERT4 = "insert into testeeattribute (_fk_TestOpportunity, context, TDS_ID, attributeValue, _date) "
          + " select ${oppkey}, ${context}, tdsID, attrval, now(3) from ${atts}";
      SqlParametersMaps parms4 = parms1;
      Map<String, String> unquotedParms4 = new HashMap<String, String> ();
      unquotedParms4.put ("atts", attsTable.getTableName ());

      int insertedCnt = executeStatement (connection, fixDataBaseNames (SQL_INSERT4, unquotedParms4), parms4, false).getUpdateCount ();

      sofar = "Fourth. ";

      if (DbComparator.isEqual (relsexist, true)) {
        final String SQL_DELETE5 = "delete from testeerelationship where _fk_TestOpportunity = ${oppkey} and context = ${context}";
        SqlParametersMaps parms5 = parms1;

        int deletedCnt = executeStatement (connection, SQL_DELETE5, parms5, false).getUpdateCount ();
        sofar = "Fifth. ";
      }

      final String SQL_INSERT6 = "insert into testeerelationship (_fk_TestOpportunity, context, relationship, TDS_ID, entitykey, attributeValue, _date)"
          + " select ${oppkey}, ${context}, reltype, tdsID, entitykey, attrval, now(3) from ${rels}";
      SqlParametersMaps parms6 = parms1;
      Map<String, String> unquotedParms6 = new HashMap<String, String> ();
      unquotedParms6.put ("rels", relsTable.getTableName ());

      insertedCnt = executeStatement (connection, fixDataBaseNames (SQL_INSERT6, unquotedParms6), parms6, false).getUpdateCount ();
      sofar = "Sixth. ";

      connection.commit ();
      connection.setAutoCommit (preexistingAutoCommitMode);

    } catch (Exception re) {
      try {
        connection.rollback ();
      } catch (SQLException e) {
        _logger.error (String.format ("Problem rolling back transaction %s", e.getMessage ()));
      }
      String msg = String.format ("%s%s", sofar, re.getMessage ());
      _LogDBError_SP (connection, "_SetTesteeAttributes", msg, testee, null, null, oppkey, clientname, null);
    }
    connection.dropTemporaryTable (attsTable);
    connection.dropTemporaryTable (relsTable);

    _LogDBLatency_SP (connection, "_SetTesteeAttributes", starttime, testee, true, null, oppkey, null, clientname, null);
  }

  // optimization attempt
  public void _SetTesteeAttributes_SP (SQLConnection connection, String clientname, UUID oppkey, Long testee, String context) throws ReturnStatusException {
    if (testee < 0)
      return;
    Date starttime = _dateUtil.getDateWRetStatus (connection);

    Boolean attsexist = null, relsexist = null;

    final String SQL_QUERY1 = "select _fk_TestOpportunity from testeeattribute where _fk_TestOpportunity = ${oppkey} and context = ${context} limit 1";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey).put ("context", context);

    if (exists (executeStatement (connection, SQL_QUERY1, parms1, false))) {
      attsexist = true;
    } else {
      attsexist = false;
    }

    final String SQL_QUERY2 = "select _fk_TestOpportunity from testeerelationship where _fk_TestOpportunity = ${oppkey} and context = ${context} limit 1";
    SqlParametersMaps parms2 = parms1;

    if (exists (executeStatement (connection, SQL_QUERY2, parms2, false))) {
      relsexist = true;
    } else {
      relsexist = false;
    }
    // should use optimized version!
    SingleDataResultSet resultSet1 = _GetTesteeAttributes_SP (connection, clientname, testee);
    resultSet1.addColumn ("_fk_TestOpportunity", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER);
    resultSet1.addColumn ("context", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    resultSet1.addColumn ("_date", SQL_TYPE_To_JAVA_TYPE.DATETIME);

    Iterator<DbResultRecord> records = resultSet1.getRecords ();
    while (records.hasNext ()) {
      DbResultRecord record = records.next ();
      record.addColumnValue ("_fk_TestOpportunity", oppkey);
      record.addColumnValue ("context", context);
      record.addColumnValue ("_date", starttime);
    }
    String sofar = "First. ";

    SingleDataResultSet resultSet2 = _rtsDll._GetTesteeRelationships_SP (connection, clientname, testee);
    resultSet2.addColumn ("_fk_TestOpportunity", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER);
    resultSet2.addColumn ("context", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    resultSet2.addColumn ("_date", SQL_TYPE_To_JAVA_TYPE.DATETIME);
    records = resultSet2.getRecords ();
    while (records.hasNext ()) {
      DbResultRecord record = records.next ();
      record.addColumnValue ("_fk_TestOpportunity", oppkey);
      record.addColumnValue ("context", context);
      record.addColumnValue ("_date", starttime);
    }
    sofar = "Second. ";

    try {
      boolean preexistingAutoCommitMode = connection.getAutoCommit ();
      connection.setAutoCommit (false);
      if (DbComparator.isEqual (attsexist, true)) {
        final String SQL_DELETE3 = "delete from testeeattribute where _fk_TestOpportunity = ${oppkey} and context = ${context}";
        SqlParametersMaps parms3 = parms1;

        int deletedCnt = executeStatement (connection, SQL_DELETE3, parms3, false).getUpdateCount ();
        sofar = "Third. ";
      }

      final String SQL_INSERT4 = "insert into testeeattribute (_fk_TestOpportunity, context, TDS_ID, attributeValue, _date) "
          + " values( ${_fk_TestOpportunity}, ${context}, ${tds_id}, ${attval}, ${_date})";

      int insertedCnt = insertBatch (connection, SQL_INSERT4, resultSet1, null);
      sofar = "Fourth. ";

      if (DbComparator.isEqual (relsexist, true)) {
        final String SQL_DELETE5 = "delete from testeerelationship where _fk_TestOpportunity = ${oppkey} and context = ${context}";
        SqlParametersMaps parms5 = parms1;

        int deletedCnt = executeStatement (connection, SQL_DELETE5, parms5, false).getUpdateCount ();
        sofar = "Fifth. ";
      }

      final String SQL_INSERT6 = "insert into testeerelationship (_fk_TestOpportunity, context, relationship, TDS_ID, entitykey, attributeValue, _date)"
          + " values ( ${_fk_TestOpportunity}, ${context}, ${relationtype}, ${tds_ID}, ${entitykey}, ${attval}, ${_date} )";
      // select relationType, entityKey, attname as TDS_ID, attval

      insertedCnt = insertBatch (connection, SQL_INSERT6, resultSet2, null);
      sofar = "Sixth. ";

      connection.commit ();
      connection.setAutoCommit (preexistingAutoCommitMode);

    } catch (Exception re) {
      try {
        connection.rollback ();
      } catch (SQLException e) {
        _logger.error (String.format ("Problem rolling back transaction %s", e.getMessage ()));
      }
      String msg = String.format ("%s%s", sofar, re.getMessage ());
      _LogDBError_SP (connection, "_SetTesteeAttributes", msg, testee, null, null, oppkey, clientname, null);
    }

    _LogDBLatency_SP (connection, "_SetTesteeAttributes", starttime, testee, true, null, oppkey, null, clientname, null);
  }

  public void _RecordBPSatisfaction_SP (SQLConnection connection, UUID oppkey) throws ReturnStatusException {
    final String SQL_QUERY1 = "select _efk_AdminSubject as testkey, _efk_TestID as testID , _fk_session as session "
        + " from testopportunity where _Key = ${oppkey}";
    final SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);

    String testkey = null;
    String testid = null;
    UUID session = null;
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      testkey = record.<String> get ("testkey");
      testid = record.<String> get ("testid");
      session = record.<UUID> get ("session");
    }
    // create table #items (_key varchar(100), segment varchar(250),
    // contentLevel varchar(200)
    DataBaseTable itemsTable = getDataBaseTable ("rbpsItems").addColumn ("_key", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).
        addColumn ("segment", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).addColumn ("contentLevel", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200);
    connection.createTemporaryTable (itemsTable);

    final String SQL_INSERT2 = "insert into ${itemsTableName} (_key, segment, contentLevel) "
        + "select _efk_ItemKey, _efk_Segment, C.contentLevel from testeeresponse R, testopportunitysegment S, ${ItemBankDB}.aa_itemcl C "
        + "  where R._fk_TestOpportunity = ${oppkey} and S._fk_TestOpportunity = ${oppkey} and S.segmentPosition = R.segment "
        + "  and C._fk_AdminSUbject = S._efk_Segment and C._fk_Item = R._efk_ItemKey and R.isFieldTest = 0";
    SqlParametersMaps parms2 = parms1;
    Map<String, String> tblNames2 = new HashMap<String, String> ();
    tblNames2.put ("itemsTableName", itemsTable.getTableName ());

    final String query2 = fixDataBaseNames (SQL_INSERT2);
    int insertCnt = executeStatement (connection, fixDataBaseNames (query2, tblNames2), parms2, false).getUpdateCount ();

    // Elena: it is ok to try to delete without separate explicit check if there
    // are records to be deleted
    // final String SQL_QUERY3 =
    // "select  _fk_TestOpportunity from testopportunitycontentcounts where _fk_TestOpportunity = ${oppkey} limit 1";
    // SqlParametersMaps parms3 = parms1;

    // if (exists (executeStatement (connection, SQL_QUERY3, parms3, false))) {
    final String SQL_QUERY4 = "delete from testopportunitycontentcounts where _fk_TestOpportunity = ${oppkey}";
    SqlParametersMaps parms4 = parms1;
    MultiDataResultSet sets4 = executeStatement (connection, SQL_QUERY4, parms4, false);
    int deleteCnt4 = sets4.getUpdateCount ();
    // }

    final String SQL_INSERT5 = "insert into testopportunitycontentcounts (_fk_TestOpportunity, _efk_TestID, _efk_AdminSubject, ContentLevel, itemcount, dateentered)"
        + " select ${oppkey}, ${testID}, ${testkey}, contentLevel, count(*), now(3) from ${itemsTableName} I group by contentLevel";
    Map<String, String> tblNames5 = tblNames2;
    SqlParametersMaps parms5 = new SqlParametersMaps ();
    parms5.put ("oppkey", oppkey);
    parms5.put ("testID", testid);
    parms5.put ("testkey", testkey);

    int insertCnt5 = executeStatement (connection, fixDataBaseNames (SQL_INSERT5, tblNames5), parms5, false).getUpdateCount ();

    // final String SQL_QUERY6 =
    // "select  _fk_TestOpportunity from testopportunitysegmentcounts where _fk_TestOpportunity = ${oppkey} limit 1";
    // SqlParametersMaps parms6 = parms1;

    // if (exists (executeStatement (connection, SQL_QUERY6, parms6, false))) {
    final String SQL_QUERY7 = "delete from testopportunitysegmentcounts where _fk_TestOpportunity = ${oppkey}";
    SqlParametersMaps parms7 = parms1;
    MultiDataResultSet sets7 = executeStatement (connection, SQL_QUERY7, parms7, false);
    int deleteCnt7 = sets7.getUpdateCount ();
    // }

    final String SQL_INSERT8 = "insert into testopportunitysegmentcounts (_fk_TestOpportunity, _efk_TestID, _efk_AdminSubject, _efk_Segment, ContentLevel, itemcount, dateentered) "
        + "select ${oppkey} as _fk_TestOpportunity, ${testID} as _efk_TestID, ${testkey} as _efk_AdminSubject, segment as _efk_Segment, contentLevel, count(*) as itemcount, now(3) "
        + " from ${itemsTableName} I group by segment, contentLevel";
    Map<String, String> tblNames8 = tblNames5;
    SqlParametersMaps parms8 = parms5;
    int insertCnt8 = executeStatement (connection, fixDataBaseNames (SQL_INSERT8, tblNames8), parms8, false).getUpdateCount ();

    connection.dropTemporaryTable (itemsTable);
  }

  public void _OnStatus_Completed_SP (SQLConnection connection, UUID oppkey) throws ReturnStatusException {

    Date now = _dateUtil.getDateWRetStatus (connection);
    boolean audit = AuditProc_FN (connection, "_OnStatus_Completed");

    final String SQL_QUERY1 = "Select _efk_Testee as testee, _efk_AdminSubject as testkey, clientname, _efk_TestID as testID from testopportunity where _Key = ${oppkey}";
    final SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);

    SingleDataResultSet result1 = executeStatement (connection, SQL_QUERY1, parms1, true).getResultSets ().next ();
    DbResultRecord record = result1.getCount () > 0 ? result1.getRecords ().next () : null;
    Long testee = null;
    String testkey = null;
    String testId = null;
    String clientname = null;
    if (record != null) {
      testee = record.<Long> get ("testee");
      testkey = record.<String> get ("testkey");
      testId = record.<String> get ("testid");
      clientname = record.<String> get ("clientname");
    }

    String itemgroupString = MakeItemGroupString_FN (connection, oppkey);

    final String SQL_QUERY2 = "update testopportunity set itemgroupString = ${itemgroupString} where _Key = ${oppkey}";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("itemgroupString", itemgroupString).put ("oppkey", oppkey);
    MultiDataResultSet sets = executeStatement (connection, SQL_QUERY2, parms2, false);
    int updateCnt2 = sets.getUpdateCount ();

    final String SQL_QUERY3 = "update testopportunitysegment set IsPermeable = -1 where _fk_TestOpportunity = ${oppkey}";
    SqlParametersMaps parms3 = parms1;
    sets = executeStatement (connection, SQL_QUERY3, parms3, false);
    int updateCnt3 = sets.getUpdateCount ();

    _SetTesteeAttributes_SP (connection, clientname, oppkey, testee, "FINAL");
    _RecordBPSatisfaction_SP (connection, oppkey);

    if (IsXMLOn_Fn (connection, oppkey) == 1 && "COMPLETE: Do Not Score".equalsIgnoreCase (CanScoreOpportunity_FN (connection, oppkey))) {
      SubmitQAReport_SP (connection, oppkey, "submitted");
    }

    final String SQL_QUERY4 = "select  _fk_TestOpportunity from ft_opportunityitem where _fk_TestOpportunity = ${oppkey} limit 1";
    SqlParametersMaps parms4 = parms1;
    if (exists (executeStatement (connection, SQL_QUERY4, parms4, false))) {

      DataBaseTable groupsTable = getDataBaseTable ("oscGroups").addColumn ("gid", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER).
          addColumn ("bid", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 20).addColumn ("seg", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("pos", SQL_TYPE_To_JAVA_TYPE.INT);
      connection.createTemporaryTable (groupsTable);

      final String SQL_QUERY6 = "insert into ${groupsTableName} (gid, bid, seg, pos) "
          + " select R.groupID, I.blockID, R.segment, min(R.position) from testeeresponse R, ft_opportunityitem I "
          + " where R._fk_TestOpportunity = ${oppkey} and I._fk_TestOpportunity = ${oppkey} and R.segment = I.segment and R.groupID = I.groupID and R.IsFieldTest = 1 "
          + " group by R.segment, R.groupID, I.blockID";
      SqlParametersMaps parms6 = parms1;
      Map<String, String> tableNames = new HashMap<String, String> ();
      tableNames.put ("groupsTableName", groupsTable.getTableName ());

      int insertCnt = executeStatement (connection, fixDataBaseNames (SQL_QUERY6, tableNames), parms6, false).getUpdateCount ();

      final String SQL_QUERY5 = "update ft_opportunityitem F, ${groupsTableName} G set F.positionAdministered = G.pos, dateAdministered=${now}  "
          + " where F._fk_TestOpportunity = ${oppkey} and F.segment = G.seg and F.groupID = G.gid";
      SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("now", now).put ("oppkey", oppkey);

      int updateCnt = executeStatement (connection, fixDataBaseNames (SQL_QUERY5, tableNames), parms5, false).getUpdateCount ();

      connection.dropTemporaryTable (groupsTable);
    }
    _LogDBLatency_SP (connection, "_OnStatus_Completed", now, null, true, null, oppkey);
  }

  /**
   * This method may return null if it does not make call to submitqareport
   * 
   * @param connection
   * @param oppKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet _OnStatus_Scored_SP (SQLConnection connection, UUID oppKey) throws ReturnStatusException {

    SingleDataResultSet result = null;

    if (IsXMLOn_Fn (connection, oppKey) == 1) {
      result = SubmitQAReport_SP (connection, oppKey, "submitted");
    }
    return result;
  }

  public void _OnStatus_Paused_SP (SQLConnection connection, UUID oppkey, String prevStatus) throws ReturnStatusException {

    if ("started".equalsIgnoreCase (prevStatus) == false && "review".equalsIgnoreCase (prevStatus) == false)
      return;

    final String SQL_QUERY1 = "select _fk_TestOpportunity from testopportunitysegment "
        + " where _fk_TestOpportunity = ${oppkey} and IsPermeable > -1 and restorePermOn <> 'completed' limit 1";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);

    if (exists (executeStatement (connection, SQL_QUERY1, parms1, false))) {
      final String SQL_UPDATE = "update testopportunitysegment set IsPermeable = -1, restorePermOn = null "
          + " where _fk_TestOpportunity = ${oppkey} and IsPermeable > -1 and restorePermOn in ('segment', 'paused');";
      SqlParametersMaps parms2 = parms1;

      MultiDataResultSet sets = executeStatement (connection, SQL_UPDATE, parms2, false);
      int updateCnt = sets.getUpdateCount ();

      String sessionDB = getTdsSettings ().getTDSSessionDBName ();
      // String sessionDB = ConfigurationManager.getInstance ().getAppSettings
      // ().get ("TDSSessionDBName");
      final String SQL_INSERT3 = "insert into ${ArchiveDB}.opportunityaudit (_fk_TestOpportunity, AccessType, _fk_Session, _fk_Browser, dateaccessed, hostname, dbname) "
          + " select ${oppkey}, 'Restore segment permeability', _fk_Session, _fk_Browser, now(3), ${localhost}, ${dbname} "
          + " from testopportunity where _Key = ${oppkey}";
      SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("oppkey", oppkey).put ("hostname", getLocalhostName ()).put ("dbname", sessionDB);
      int insertCnt = executeStatement (connection, fixDataBaseNames (SQL_INSERT3), parms3, false).getUpdateCount ();
    }
  }

  public SingleDataResultSet SetOpportunityStatus_SP (SQLConnection connection, UUID oppkey, String status) throws ReturnStatusException {

    return SetOpportunityStatus_SP (connection, oppkey, status, false, null, null);

  }

  public SingleDataResultSet SetOpportunityStatus_SP (SQLConnection connection, UUID oppkey, String status, Boolean suppressReport) throws ReturnStatusException {
    return SetOpportunityStatus_SP (connection, oppkey, status, suppressReport, null, null);
  }

  public SingleDataResultSet SetOpportunityStatus_SP (SQLConnection connection, UUID oppkey, String status, Boolean suppressReport, String requestor) throws ReturnStatusException {
    return SetOpportunityStatus_SP (connection, oppkey, status, suppressReport, requestor, null);
  }

  /**
   * This method will return null if suppressReport parameter is set to true
   * 
   * @param connection
   * @param oppkey
   * @param status
   * @param suppressReport
   * @param requestor
   * @param comment
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet SetOpportunityStatus_SP (SQLConnection connection, UUID oppkey, String status, Boolean suppressReport, String requestor, String comment) throws ReturnStatusException {
    SingleDataResultSet rs = null;
    Date now = _dateUtil.getDateWRetStatus (connection);
    String clientname = null;
    String oldstatus = null;
    Date datestarted = null;

    final String SQL_QUERY1 = "select clientname, status as oldstatus, datestarted from testopportunity where _Key  = ${oppkey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      clientname = record.<String> get ("clientname");
      oldstatus = record.<String> get ("oldstatus");
      datestarted = record.<Date> get ("datestarted");
    }

    if (oldstatus == null)
      oldstatus = "UNDEFINED";

    String msg = null;
    msg = _CanChangeOppStatus_FN (connection, oldstatus, status);
    if (msg != null) {
      String dbmsg = String.format ("Bad status transition from %s to %s", oldstatus, status);

      _LogDBError_SP (connection, "SetOppportunityStatus", dbmsg, null, null, null, oppkey);
      if (suppressReport == false) {
        String arg = String.format ("%s,%s", oldstatus, status);

        return _ReturnError_SP (connection, clientname, "SetOPportunityStatus", msg, arg, oppkey, "_CanChangeOppStatus", "failed");
      }
      return null;
    }
    if ("pending".equalsIgnoreCase (status) && datestarted != null) {

      final String SQL_QUERY2 = "select  _fk_TestOpportunity from testeeresponse where _fk_TestOpportunity = ${oppkey} limit 1";
      SqlParametersMaps parms2 = parms1;

      if (exists (executeStatement (connection, SQL_QUERY2, parms2, false)))
        status = "suspended";
    }
    String localhostname = getLocalhostName ();

    final String SQL_UPDATE3 = "update testopportunity set "
        + " PrevStatus = status,  DateChanged = ${now}, "
        + " DateScored      = case ${status} when 'scored' then ${now} else DateScored end, "
        + " DateApproved    = case ${status} when 'approved' then ${now} else DateApproved end, "
        + " DateCompleted   = case ${status} when 'completed' then ${now} else DateCompleted end, "
        + " DateExpired     = case ${status} when 'expired' then ${now} else DateExpired end, "
        + " DateSubmitted   = case ${status} when 'submitted' then ${now} else DateSubmitted end, "
        + " DateReported    = case ${status}  when 'reported' then ${now} else DateReported end, "
        + " dateRescored    = case ${status} when 'rescored' then ${now} else dateRescored end, "
        + " datePaused      = case "
        + "   when ${status} = 'paused' and status in ('started', 'review') then ${now} else datePaused end, "
        + " dateInvalidated = case ${status} when 'invalidated' then ${now} else dateInvalidated end, "
        + " invalidatedBy   = case ${status} when 'invalidated' then ${requestor} else invalidatedBy end, "
        + " XMLHost         = case ${status} when 'submitted' then ${hostname} else XMLHost end, "
        + " waitingForSegment = case  "
        + "   when ${status} in ('approved', 'denied') and status in ('segmentEntry', 'segmentExit') then null "
        + "   else waitingForSegment end, "
        + " comment         = case when ${comment} is not null then ${comment} else comment end, "
        + " status = ${status} "
        + " where _Key = ${oppkey}";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("oppkey", oppkey).put ("status", status);
    parms3.put ("now", now).put ("requestor", requestor).put ("hostname", localhostname).put ("comment", comment);

    int updateCnt = executeStatement (connection, SQL_UPDATE3, parms3, false).getUpdateCount ();

    if ("completed".equalsIgnoreCase (status))
      _OnStatus_Completed_SP (connection, oppkey);

    if ("scored".equalsIgnoreCase (status))
      _OnStatus_Scored_SP (connection, oppkey);

    if ("paused".equalsIgnoreCase (status))
      _OnStatus_Paused_SP (connection, oppkey, oldstatus);

    // String sessionDB = getAppSettings().get ("TDSSessionDBName");
    String sessionDB = getTdsSettings ().getTDSSessionDBName ();
    // String sessionDB = ConfigurationManager.getInstance ().getAppSettings
    // ().get ("TDSSessionDBName");
    final String SQL_INSERT4 = " insert into ${ArchiveDB}.opportunityaudit (_fk_Testopportunity, _fk_Session, AccessType, hostname, _fk_Browser, actor, comment, dateaccessed, dbname)"
        + " select ${oppkey}, _fk_session, ${status}, ${localhostname}, _fk_Browser, ${requestor}, ${comment}, now(3), ${dbname} "
        + "   from testopportunity where _Key = ${oppkey}";
    SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("oppkey", oppkey).put ("status", status);
    parms4.put ("localhostname", localhostname).put ("requestor", requestor).put ("comment", comment).put ("dbname", sessionDB);

    int insertCnt = executeStatement (connection, fixDataBaseNames (SQL_INSERT4), parms4, false).getUpdateCount ();

    if (suppressReport == false) {
      // @status as [status], null as reason, null as [context], null as
      // [argstring], '|' as [delimiter];
      rs = ReturnStatusReason (status, null);
    }

    _LogDBLatency_SP (connection, "SetOpportunityStatus", now, null, true, null, oppkey, null, clientname, null);
    return rs;
  }

  public Integer AuditOpportunities_FN (SQLConnection connection, String clientname) throws ReturnStatusException {

    final String SQL_QUERY1 = "select clientname from _externs where clientname = ${clientname} and environment = 'SIMULATION' limit 1";
    SqlParametersMaps parameters1 = (new SqlParametersMaps ()).put ("clientname", clientname);

    if (exists (executeStatement (connection, SQL_QUERY1, parameters1, false)) == true) {
      return 0;
    }

    Integer flag = selectIsOnByAuditObject (connection, clientname, "opportunities");
    if (flag == null || flag == 0)
      return 0;
    else
      return 1;
  }

  public Integer AuditSessions_FN (SQLConnection connection, String clientname) throws ReturnStatusException {

    Integer flag = selectIsOnByAuditObject (connection, clientname, "sessions");
    if (flag == null || flag == 0)
      return 0;
    else
      return 1;
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
   * This method differs from SQL function because it returns comma separated,
   * single-quoted list of statuses selected VS SQL function returning a table.
   * It is suitable for the manner in which they method is used and it decreases
   * number of temporary tables created.
   * 
   * @param connection
   * @param usage
   * @param stage
   * @return
   * @throws ReturnStatusException
   */
  public String GetStatusCodes_FN (SQLConnection connection, String usage, String stage) throws ReturnStatusException {

    String statusStr = "";
    final String SQL_QUERY = "select status from statuscodes where `usage` = ${usage} and stage = ${stage}";
    SqlParametersMaps parms = (new SqlParametersMaps ()).put ("usage", usage).put ("stage", stage);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY, parms, false).getResultSets ().next ();
    Iterator<DbResultRecord> records = result.getRecords ();
    while (records.hasNext ()) {
      DbResultRecord record = records.next ();
      String aStatus = record.<String> get ("status");
      if (aStatus != null && aStatus.isEmpty () == false) {
        if (statusStr.isEmpty ())
          statusStr = String.format ("'%s'", aStatus);
        else
          statusStr += String.format (",'%s'", aStatus);
      }
    }
    return statusStr;
  }

  public String ValidateProctorSession_FN (SQLConnection connection, Long proctorkey, UUID sessionkey, UUID browserkey) throws ReturnStatusException {

    final String SQL_QUERY1 = "select _key from session where _key = ${sessionkey} and status = 'open' "
        + "and ${now} between datebegin and dateend  limit 1";

    Date now = _dateUtil.getDateWRetStatus (connection);
    SqlParametersMaps parameters1 = (new SqlParametersMaps ()).put ("sessionkey", sessionkey).put ("now", now);

    final String SQL_QUERY2 = "select _key from session where _Key = ${sessionkey} and _efk_Proctor = ${proctorkey} limit 1";
    SqlParametersMaps parameters2 = (new SqlParametersMaps ()).put ("sessionkey", sessionkey).put ("proctorkey", proctorkey);

    final String SQL_QUERY3 = "select _key from session where _Key = ${sessionkey} and _fk_browser = ${browserkey} limit 1";
    SqlParametersMaps parameters3 = new SqlParametersMaps ().put ("sessionkey", sessionkey).put ("browserkey", browserkey);

    if (exists (executeStatement (connection, SQL_QUERY1, parameters1, false)) == false) {
      return "The session is closed.";
    }

    if (exists (executeStatement (connection, SQL_QUERY2, parameters2, false)) == false) {
      return "The session is not owned by this proctor";
    }

    if (exists (executeStatement (connection, SQL_QUERY3, parameters3, false)) == false) {
      return "Unauthorized session access";
    }
    return null;
  }

  public SingleDataResultSet P_PauseSession_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey) throws ReturnStatusException {

    return P_PauseSession_SP (connection, sessionKey, proctorKey, browserKey, "closed", true);
  }

  public SingleDataResultSet P_PauseSession_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, String reason, Boolean report) throws ReturnStatusException {
    String clientname = null;

    Date starttime = _dateUtil.getDateWRetStatus (connection);

    final String SQL_QUERY1 = "select clientname from session where _Key = ${sessionKey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null)
      clientname = record.<String> get ("clientname");

    String accessdenied = ValidateProctorSession_FN (connection, proctorKey, sessionKey, browserKey);
    if (accessdenied != null) {

      _LogDBError_SP (connection, "P_PauseSession", accessdenied, proctorKey, null, null, sessionKey);
      _LogDBLatency_SP (connection, "P_PauseSession", starttime, proctorKey, true, null, sessionKey);

      return _ReturnError_SP (connection, clientname, "P_PauseSession", accessdenied, null, null, "ValidateProctorSession", "failed");
    }

    Integer audit = AuditSessions_FN (connection, clientname);
    String localhostName = getLocalhostName ();
    Date now = _dateUtil.getDateWRetStatus (connection);

    final String SQL_QUERY2 = "select  _Key from session where _Key = ${sessionKey} limit 1";
    SqlParametersMaps parms2 = parms1;
    if (exists (executeStatement (connection, SQL_QUERY2, parms2, false)) == false) {
      String msg = String.format ("No such session: %s", sessionKey.toString ());
      _RecordSystemError_SP (connection, "P_PauseSession", msg);
      return _ReturnError_SP (connection, clientname, "P_PauseSession", "Session does not exist");
    }

    final String SQL_UPDATE3 = "Update session set status = 'closed', datechanged = ${now}, dateend=${now} where _Key = ${sessionKey}";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("now", now).put ("sessionKey", sessionKey);
    int updateCnt = executeStatement (connection, SQL_UPDATE3, parms3, false).getUpdateCount ();

    if (DbComparator.notEqual (audit, 0)) {

      String sessionDB = getTdsSettings ().getTDSSessionDBName ();
      // String sessionDB = ConfigurationManager.getInstance ().getAppSettings
      // ().get ("TDSSessionDBName");
      final String SQL_INSERT4 = "insert into ${ArchiveDB}.sessionaudit (_fk_session, DateAccessed, AccessType, hostname, browserkey, dbname) "
          + " values (${sessionKey}, ${now}, ${reason}, ${host}, ${browserKey}, ${dbname})";
      SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey);
      parms4.put ("now", now).put ("reason", reason).put ("host", localhostName).put ("browserKey", browserKey).put ("dbname", sessionDB);

      executeStatement (connection, fixDataBaseNames (SQL_INSERT4), parms4, false);
    }

    final String statusStr = GetStatusCodes_FN (connection, "Opportunity", "inuse");
    if (AuditOpportunities_FN (connection, clientname) != 0) {

      // String sessionDB = getAppSettings().get ("TDSSessionDBName");
      String sessionDB = getTdsSettings ().getTDSSessionDBName ();
      // String sessionDB = ConfigurationManager.getInstance ().getAppSettings
      // ().get ("TDSSessionDBName");
      final String SQL_INSERT5 = "insert into ${ArchiveDB}.opportunityaudit (_fk_TestOpportunity, DateAccessed, AccessType,_fk_Session, Hostname, _fk_Browser, dbname)"
          + " (select _Key, ${now}, 'paused by session', ${sessionKey}, ${host}, _fk_Browser, ${dbname} from testopportunity "
          + " where _fk_Session = ${sessionKey} and status in (${statusStr}))";
      Map<String, String> unquotedparms = new HashMap<String, String> ();
      unquotedparms.put ("statusStr", statusStr);
      final String query5 = fixDataBaseNames (SQL_INSERT5);
      SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("now", now).put ("sessionKey", sessionKey).
          put ("host", localhostName).put ("dbname", sessionDB);
      int insertCnt5 = executeStatement (connection, fixDataBaseNames (query5, unquotedparms), parms5, false).getUpdateCount ();
    }

    final String SQL_QUERY7 = "select _key from testopportunity where _fk_Session = ${sessionKey} and status in (${statusStr})";

    Map<String, String> unquotedparms = new HashMap<String, String> ();
    unquotedparms.put ("statusStr", statusStr);
    SqlParametersMaps parms7 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey);

    SingleDataResultSet result7 = executeStatement (connection, fixDataBaseNames (SQL_QUERY7, unquotedparms), parms7, false).getResultSets ().next ();
    Iterator<DbResultRecord> records = result7.getRecords ();
    while (records.hasNext ()) {
      record = records.next ();
      UUID key = record.<UUID> get ("_key");
      // TODO: SetOpportunityStatus has 5th parameter as String. Do they really
      // want UUID in its place?
      // TODO Elena test
      // long start = System.currentTimeMillis();
      SetOpportunityStatus_SP (connection, key, "paused", true, sessionKey.toString ());
      // long diff = System.currentTimeMillis() - start;
      // System.out.println (String.format
      // ("SetOPportunityStatus latency: %d millisec, status: %s", diff,
      // "paused"));
    }

    if (DbComparator.isEqual (report, true))
      result = ReturnStatusReason ("closed", null);
    else
      result = null;
    _LogDBLatency_SP (connection, "", starttime, null, true, null, null, sessionKey, clientname, null);
    return result;
  }

  public void _RecordSystemError_SP (SQLConnection connection, String proc, String msg) throws ReturnStatusException {
    _RecordSystemError_SP (connection, proc, msg, null, null, null, null, null, null, null, null, null);
  }

  public void _RecordSystemError_SP (SQLConnection connection, String proc, String msg, Long testee,
      String test, Integer opportunity, String application, String clientIp, UUID applicationContextID,
      String stackTrace, UUID testoppkey, String clientname) throws ReturnStatusException {

    if (application == null)

      application = getTdsSettings ().getAppName ();
    // application = ConfigurationManager.getInstance ().getAppSettings ().get
    // ("AppName");

    if (clientname == null && testoppkey != null) {
      final String SQL_QUERY1 = "select clientname from testopportunity where _Key = ${testoppkey}";
      SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("testoppkey", testoppkey);

      SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, true).getResultSets ().next ();
      DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null)
        clientname = record.<String> get ("clientname");
    }
    // String sessionDB = getAppSettings().get ("TDSSessionDBName");
    String sessionDB = getTdsSettings ().getTDSSessionDBName ();
    // String sessionDB = ConfigurationManager.getInstance ().getAppSettings
    // ().get ("TDSSessionDBName");
    final String SQL_INSERT2 = "insert into ${ArchiveDB}.systemerrors (procname, errorMessage, _efk_Testee, _efk_TestID, Opportunity, application,"
        + " IPAddress, ApplicationContextID, stackTrace, _fk_TestOpportunity, clientname, daterecorded, serverid, dbname)"
        + " values (${proc}, ${msg}, ${testee}, ${test}, ${opportunity}, ${application}, "
        + "         ${clientIP}, ${ApplicationContextID}, ${stackTrace}, ${testoppkey}, ${clientname}, now(3), ${localhost}, ${dbname})";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("proc", proc).put ("msg", msg).put ("testee", testee).put ("test", test).put ("opportunity", opportunity)
        .put ("application", application).put ("localhost", getLocalhostName ()).put ("dbname", sessionDB);
    parms2.put ("clientIP", clientIp).put ("ApplicationContextID", applicationContextID).put ("stackTrace", stackTrace).put ("testoppkey", testoppkey).put ("clientname", clientname);
    int insertCnt = executeStatement (connection, fixDataBaseNames (SQL_INSERT2), parms2, false).getUpdateCount ();
  }

  public SingleDataResultSet _ReturnError_SP (SQLConnection connection, String client, String procname, String appkey) throws ReturnStatusException {
    return _ReturnError_SP (connection, client, procname, appkey, null, null, null, "failed");
  }

  public SingleDataResultSet _ReturnError_SP (SQLConnection connection, String client, String procname, String appkey, String argstring, UUID oppkey, String context) throws ReturnStatusException {
    return _ReturnError_SP (connection, client, procname, appkey, argstring, oppkey, context, "failed");
  }

  /**
   * procname paramter must not be null
   * 
   * @param connection
   * @param client
   * @param procname
   * @param appkey
   * @param argstring
   * @param oppkey
   * @param context
   * @param status
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet _ReturnError_SP (SQLConnection connection, String client, String procname, String appkey, String argstring, UUID oppkey, String context, String status)
      throws ReturnStatusException {

    if (context == null)
      context = procname;

    String language = null;
    String subject = null;
    Long testee = null;
    String clientname = client;
    _Ref<String> gradeRef = new _Ref<String> ();

    if (oppkey != null) {
      final String SQL_QUERY1 = "select acccode as language, subject, _efk_Testee as testee, clientname "
          + " from testopportunity O, testeeaccommodations A where O._key = ${oppkey} and A._fk_TestOpportunity = ${oppkey} and A.acctype = 'Language'";

      SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("oppkey", oppkey);

      SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parameters, false).getResultSets ().next ();
      DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        language = record.<String> get ("language");
        subject = record.<String> get ("subject");
        testee = record.<Long> get ("testee");
        clientname = record.<String> get ("clientname");
      }
      if (DbComparator.greaterThan (testee, 0)) {

        _rtsDll._GetRTSAttribute_SP (connection, client, testee, "EnrlGrdCd", gradeRef);
      }
    }
    if (language == null)
      language = "ENU";

    _Ref<String> errmsg = new _Ref<String> ();
    _FormatMessage_SP (connection, clientname, language, context, appkey, errmsg, argstring, ',', subject, gradeRef.get ());

    // final String SQL_QUERY2 =
    // "select ${status} as status, ${errmsg} as reason, ${context} as context, ${appkey} as appkey";
    List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
    CaseInsensitiveMap<Object> record = new CaseInsensitiveMap<Object> ();
    record.put ("status", status);
    record.put ("reason", errmsg.get ());
    record.put ("context", context);
    record.put ("appkey", appkey);
    resultList.add (record);

    SingleDataResultSet result = new SingleDataResultSet ();
    result.addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    result.addColumn ("reason", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    result.addColumn ("context", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    result.addColumn ("appkey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER);

    result.addRecords (resultList);
    return result;
  }

  public void _FormatMessage_SP (SQLConnection connection, String clientname, String language, String context, String appkey, _Ref<String> errmsg, String argstring) throws ReturnStatusException {
    _FormatMessage_SP (connection, clientname, language, context, appkey, errmsg, argstring, ',', null, null);
  }

  public void _FormatMessage_SP (SQLConnection connection, String clientname, String language, String context, String appkey, _Ref<String> errmsg) throws ReturnStatusException {
    _FormatMessage_SP (connection, clientname, language, context, appkey, errmsg, null, ',', null, null);
  }

  public void _FormatMessage_SP (SQLConnection connection, String clientname, String language, String context, String appkey, _Ref<String> errmsg, String argstring, Character delimiter,
      String subject, String grade) throws ReturnStatusException {

    String[] rows = null;
    String msg = null;
    Integer msgId = null;

    if (argstring != null) {
      if (delimiter == null)
        delimiter = ',';
      rows = _BuildTableAsArray (argstring, delimiter.toString (), -1);
    }
    String msgkey = TDS_GetMessagekey_FN (connection, clientname, "database", "database", context, appkey, language, grade, subject);
    if (msgkey == null) {
      msg = String.format ("%s [-----]", appkey);

      try {
        final String SQL_QUERY1 = "select application from _missingmessages where application ='database' and context = ${context} "
            + " and contextType = 'database' and appkey = ${appkey} and message = ${msg} limit 1";
        SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("context", context).put ("appkey", appkey).put ("msg", msg);
        if (exists (executeStatement (connection, SQL_QUERY1, parms1, false)) == false) {
          final String SQL_INSERT2 = "insert into _missingmessages(application,contextType,context, appkey,message) "
              + " values ('database', 'database', ${context}, ${appkey}, ${msg})";
          SqlParametersMaps parms2 = parms1;
          executeStatement (connection, SQL_INSERT2, parms2, false);
        }
      } catch (ReturnStatusException e) {
        _logger.error (String.format ("Failed inserting rec into _missingmessages: %s", e.getMessage ()));
      }
      errmsg.set (msg);
      return;
    }

    if (NumberUtils.isNumber (msgkey)) {

      final String SQL_QUERY3 = "select message, messageID from ${ConfigDB}.tds_coremessageobject where _Key = ${msgkey}";
      SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("msgkey", msgkey);

      SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY3), parms3, false).getResultSets ().next ();
      DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null) {
        msg = record.<String> get ("message");
        msgId = record.<Integer> get ("messageID");
      }

    } else {
      try {
        UUID msgkeyUuid = UUID.fromString (msgkey);

        final String SQL_QUERY4 = " select T.message, messageID from ${ConfigDB}.tds_coremessageobject O, ${ConfigDB}.client_messagetranslation T "
            + " where T._Key = ${msgkeyuuid} and O._Key = T._fk_CoreMessageObject";
        SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("msgkeyuuid", msgkeyUuid);

        SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY4), parms4, false).getResultSets ().next ();
        DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
        if (record != null) {
          msg = record.<String> get ("message");
          msgId = record.<Integer> get ("messageID");
        }
      } catch (IllegalArgumentException ie) {
        _logger.error (String.format ("MesageKey is not of UUID format: %s", msgkey));
      }
    }
    if (rows != null && msg != null) {
      for (int counter1 = 0; counter1 < rows.length; ++counter1) {
        Object value = rows[counter1];
        if (value != null) {
          Pattern p = Pattern.compile ("\\{" + counter1 + "\\}");
          Matcher m = p.matcher (msg);
          msg = m.replaceAll (value.toString ());
        }
      }
    }
    msg = String.format ("%s [%d]", msg, msgId);
    errmsg.set (msg);
  }

  public String TDS_GetMessagekey_FN (SQLConnection connection, String client, String application, String contextType,
      String context, String appkey, String language, String grade, String subject) throws ReturnStatusException {

    Long msgKey = null;
    final String SQL_QUERY1 = "select _fk_CoreMessageObject as msgKey from  ${ConfigDB}.tds_coremessageuser, ${ConfigDB}.tds_coremessageobject "
        + " where SystemID = ${application} and Context = ${context} and _fk_CoreMessageObject = _Key and contextType = ${contextType} and appkey = ${appkey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("application", application).put ("context", context).put ("contextType", contextType).put ("appkey", appkey);

    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY1), parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null)
      msgKey = record.<Long> get ("msgKey");

    if (msgKey == null)
      return null;

    if (client == null)
      client = "AIR";
    if (language == null)
      language = "ENU";
    if (grade == null)
      grade = "--ANY--";
    if (subject == null)
      subject = "--ANY--";

    String defaultL = null;
    Boolean inter = null;

    final String SQL_QUERY2 = "select defaultLanguage, internationalize from  ${ConfigDB}.client where name = ${client}";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("client", client);

    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY2), parms2, false).getResultSets ().next ();
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      defaultL = record.<String> get ("defaultLanguage");
      inter = record.<Boolean> get ("internationalize");
    }

    if (DbComparator.isEqual (inter, false))
      language = defaultL;

    final String SQL_QUERY3 = "select _Key as altMsg from  ${ConfigDB}.client_messagetranslation "
        + " where _fk_CoreMessageObject = ${msgKey} and (language = ${language} or language = ${defaultL}) and (client = ${client} or client = 'AIR') "
        + "  and (Grade = ${grade} or Grade = '--ANY--') and (Subject = ${subject} or Subject = '--ANY--')";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("msgKey", msgKey).put ("language", language).put ("defaultL", defaultL).
        put ("client", client).put ("grade", grade).put ("subject", subject);

    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY3), parms3, false).getResultSets ().next ();
    // TODO: this is
    record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    UUID altMsg = null;
    if (record != null) {
      altMsg = record.<UUID> get ("altMsg");
    }

    if (altMsg != null)
      return altMsg.toString ();
    else
      return msgKey.toString ();
  }

  public DataBaseTable _BuildTable_FNV1 (SQLConnection connection, String tblName, String theLine, String delimiter)
      throws ReturnStatusException {
    // 3) performance may be impacted if it is not a primary key
    // 4) SQL uses format varchar(max) for the record column, which is at least
    // 2GB; what should we
    // reasonably use in out APIs ?

    DataBaseTable table = getDataBaseTable (tblName).addColumn ("idx", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("record", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 8000);
    connection.createTemporaryTable (table);

    if (theLine != null) {
      final String splits[] = StringUtils.split (theLine, delimiter);
      executeMethodAndInsertIntoTemporaryTable (connection, new AbstractDataResultExecutor ()
      {
        @Override
        public SingleDataResultSet execute (SQLConnection connection) throws ReturnStatusException {

          // String splits[] = StringUtils.splitByWholeSeparator (theLine,
          // delimiter);
          List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
          int idx = 1; // start from 1, because this is how idx
                       // IDENTITY(1,1)column is defined on SQL side
          for (String split : splits)
          {
            CaseInsensitiveMap<Object> record = new CaseInsensitiveMap<Object> ();
            record.put ("record", split);
            record.put ("idx", idx++);
            resultList.add (record);
          }
          SingleDataResultSet rs = new SingleDataResultSet ();
          rs.addColumn ("record", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
          rs.addColumn ("idx", SQL_TYPE_To_JAVA_TYPE.INT);
          rs.addRecords (resultList);

          return rs;
        }
      }, table, false); // true = create this temp table
    }
    return table;
  }

  public DataBaseTable _BuildTable_FN (SQLConnection connection, String tblName, String theLine, String delimiter)
      throws ReturnStatusException {
    // 3) performance may be impacted if it is not a primary key
    // 4) SQL uses format varchar(max) for the record column, which is at least
    // 2GB; what should we
    // reasonably use in out APIs ?

    DataBaseTable table = getDataBaseTable (tblName).addColumn ("idx", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("record", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 8000);
    connection.createTemporaryTable (table);

    if (theLine != null) {
      String splits[] = StringUtils.split (theLine, delimiter);
      List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
      int idx = 1; // start from 1, because this is how idx
                   // IDENTITY(1,1)column is defined on SQL side
      for (String split : splits)
      {
        CaseInsensitiveMap<Object> record = new CaseInsensitiveMap<Object> ();
        record.put ("record", split);
        record.put ("idx", idx++);
        resultList.add (record);
      }
      SingleDataResultSet rs = new SingleDataResultSet ();
      rs.addColumn ("record", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      rs.addColumn ("idx", SQL_TYPE_To_JAVA_TYPE.INT);
      rs.addRecords (resultList);

      // String tmp1 = table.generateInsertColumnsNamesStatement();

      // String tmp2 = table.generateInsertColumnsMapStatement();

      insertBatch (connection, table.generateInsertStatement (), rs, null);
      // insertBatchAsMulti (connection,
      // table.generateInsertColumnsNamesStatement (),
      // table.generateInsertColumnsMapStatement (), rs);
    }
    return table;
  }

  /**
   * This method is an alternative for _BuildTable_FN for cases where we can
   * avoid creating temporary table on database server side and instead can use
   * String array on java side.
   * 
   * @param theLine
   *          a string to be split by delimiter into array of strings
   * @param delimiter
   * @param columnIdx
   *          ; if set to -1, all rows in the array are returned; column index
   *          is zero based.
   * @return
   */
  public String[] _BuildTableAsArray (String theLine, String delimiter, int columnIdx) {
    // columnIdx is zero based!

    if (theLine == null)
      return null;

    String splits[] = StringUtils.split (theLine, delimiter);

    if (columnIdx == -1)
      return splits;
    else if (splits.length > columnIdx) {
      String newArray[] = new String[1];
      newArray[0] = splits[columnIdx];
      return newArray;
    } else
      return null;
  }

  public void _LogDBError_SP (SQLConnection connection, String procname, String msg, Long testee,
      String test, Integer opportunity, UUID testopp) throws ReturnStatusException {

    _LogDBError_SP (connection, procname, msg, testee, test, opportunity, testopp, null, null);
  }

  public void _LogDBError_SP (SQLConnection connection, String procname, String msg, Long testee,
      String test, Integer opportunity, UUID testopp, String clientname, UUID session) throws ReturnStatusException {

    if (clientname == null && testopp != null) {
      final String SQL_QUERY1 = "select clientname from testopportunity where _Key = ${testopp}";
      SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("testopp", testopp);

      SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, true).getResultSets ().next ();
      DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null)
        clientname = record.<String> get ("clientname");
    } else if (clientname == null && session != null) {
      final String SQL_QUERY2 = "select clientname from session  where _Key = ${session}";
      SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("session", session);

      SingleDataResultSet result = executeStatement (connection, SQL_QUERY2, parms2, true).getResultSets ().next ();
      DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
      if (record != null)
        clientname = record.<String> get ("clientname");
    }

    String sessionDB = getTdsSettings ().getTDSSessionDBName ();
    // String sessionDB = ConfigurationManager.getInstance ().getAppSettings
    // ().get ("TDSSessionDBName");
    final String SQL_INSERT3 = "insert into ${ArchiveDB}.systemerrors (procname, errorMessage, _efk_Testee, _efk_TestID, Opportunity, application, _fk_TestOpportunity, "
        + " _fk_session, clientname, daterecorded, serverid, dbname) "
        + " values (${procname}, ${msg}, ${testee}, ${test}, ${opportunity}, 'DATABASE', ${testopp}, ${session}, ${clientname}, now(3), ${localhost}, ${dbname})";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("procname", procname).put ("testee", testee).put ("test", test).put ("opportunity", opportunity).put ("testopp", testopp)
        .put ("session", session).put ("clientname", clientname).put ("msg", msg).put ("localhost", getLocalhostName ()).put ("dbname", sessionDB);

    int insertCnt = executeStatement (connection, fixDataBaseNames (SQL_INSERT3), parms3, false).getUpdateCount ();
  }

  public void _LogDBLatency_SP (SQLConnection connection, String procname, Date starttime) throws ReturnStatusException {

    _LogDBLatency_SP (connection, procname, starttime, null, true, null, null, null, null, null);

  }

  public void _LogDBLatency_SP (SQLConnection connection, String procname, Date starttime, Long userkey, boolean checkaudit, Integer N, UUID testoppkey) throws ReturnStatusException {

    _LogDBLatency_SP (connection, procname, starttime, userkey, checkaudit, N, testoppkey, null, null, null);

  }

  /**
   * Caller must pass non-null procname and starttime. If starttime is null,
   * Runtime exception is thrown.
   * 
   * @param connection
   * @param procname
   * @param starttime
   * @param userkey
   * @param checkaudit
   * @param N
   * @param testoppkey
   * @param sessionkey
   * @param clientname
   * @param comment
   * @throws ReturnStatusException
   */
  public void _LogDBLatency_SP (SQLConnection connection, String procname, Date starttime, Long userkey, boolean checkaudit, Integer N, UUID testoppkey, UUID sessionkey, String clientname,
      String comment) throws ReturnStatusException {
    // if(procname ==null)
    // procname = new Object(){}.getClass().getEnclosingMethod().getName();

    if (checkaudit == false || AuditProc_FN (connection, procname) == true) {
      boolean logDBLatency = false;
      Date now = _dateUtil.getDateWRetStatus (connection);
      long duration = 0;
      duration = now.getTime () - starttime.getTime ();
      if (duration < 0) {
        duration = 0;
      }

      Calendar nowCal = Calendar.getInstance ();
      nowCal.setTime (now);
      int currSeconds = nowCal.get (Calendar.SECOND);
      if (currSeconds % gLogLatencyInterval == 0 || duration > gLogLatencyMaxTime) {
        logDBLatency = true;
      }

      if (!logDBLatency) {
        return;
      }

      if (clientname == null && testoppkey != null) {

        final String SQL_QUERY1 = "select clientname from testopportunity where _Key = ${testoppkey}";
        SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("testoppkey", testoppkey);
        SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, true).getResultSets ().next ();

        DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
        if (record != null)
          clientname = record.<String> get ("clientname");

      } else if (clientname == null && sessionkey != null) {
        final String SQL_QUERY2 = "select clientname from session where _Key = ${sessionkey}";
        SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("sessionkey", sessionkey);
        SingleDataResultSet result = executeStatement (connection, SQL_QUERY2, parms2, true).getResultSets ().next ();

        DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
        if (record != null)
          clientname = record.<String> get ("clientname");
      }

      // String startStr = new SimpleDateFormat
      // (AbstractDateUtilDll.DB_DATETIME_FORMAT_MS_PRECISION).format
      // (starttime);
      // String nowStr = new SimpleDateFormat
      // (AbstractDateUtilDll.DB_DATETIME_FORMAT_MS_PRECISION).format (now);
      // System.err.println ("Starttime: " + startStr);
      // System.err.println ("Now: " + nowStr);
      // System.err.println (String.format ("Duration: %d", duration ));

      Date difftime = new Date (duration);

      // String sessionDB = getAppSettings ().get ("TDSSessionDBName");
      String sessionDB = getTdsSettings ().getTDSSessionDBName ();
      // String sessionDB = ConfigurationManager.getInstance ().getAppSettings
      // ().get ("TDSSessionDBName");
      final String SQL_INSERT = "insert into ${ArchiveDB}._dblatency (userkey, duration, starttime, difftime, procname, N, _fk_TestOpportunity, "
          + "  _fk_session, clientname, comment, host, dbname) "
          + " values (${userkey}, ${duration}, ${starttime}, ${difftime}, ${procname}, ${N}, ${testoppkey}, "
          + "         ${sessionkey}, ${clientname}, ${comment}, ${localhost}, ${dbname})";
      SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("userkey", userkey).put ("duration", duration).put ("starttime", starttime).
          put ("difftime", difftime).put ("procname", procname).put ("N", N).put ("testoppkey", testoppkey).put ("sessionkey", sessionkey).
          put ("clientname", clientname).put ("comment", comment).put ("localhost", getLocalhostName ()).put ("dbname", sessionDB);

      executeStatement (connection, fixDataBaseNames (SQL_INSERT), parms3, false);
    }
  }

  // TODO Oksana will talk to LA about this function; this is just a placeholder
  public boolean AuditProc_FN (SQLConnection connection, String procName) throws ReturnStatusException {
    return true;
  }

  /**
   * @param ReturnErrorArgs
   *          class containing arguments to the underlying _ReturnError_SP
   *          method.
   * 
   * @returns SingleDataResultSet containing one row specifying the error.
   * 
   * @throws ReturnStatusException
   */
  public SingleDataResultSet _ReturnError_SP (ReturnErrorArgs args)
      throws ReturnStatusException {
    return _ReturnError_SP (args.getConnection (), args.getClient (), args.getProcName (), args.getAppKey (),
        args.getArgString (), args.getOppKey (), args.getContext (), args.getStatus ());

  }

  /**
   * @param LogDBLatencyArgs
   *          class containing arguments to the underlying _LogDBLatency_SP
   *          method.
   * 
   * @returns SingleDataResultSet containing one row specifying the error.
   * 
   * @throws ReturnStatusException
   * 
   * @throws SQLException
   */
  public void _LogDBLatency_SP (LogDBLatencyArgs args) throws ReturnStatusException {
    _LogDBLatency_SP (args.getConnection (), args.getProcName (), args.getStartTime (), args.getUserKey (), args.isCheckAudit (), args.getN (), args.getTestOppKey (),
        args.getSessionKey (),
        args.getClientName (), args.getComment ());
  }

  /**
   * @param LogDBErrorArgs
   *          class containing arguments to the underlying _LogDBError_SP
   *          method.
   * 
   * @returns SingleDataResultSet containing one row specifying the error.
   * 
   * @throws ReturnStatusException
   */
  public void _LogDBError_SP (LogDBErrorArgs args) throws ReturnStatusException {
    _LogDBError_SP (args.getConnection (), args.getProcName (), args.getMsg (), args.getTestee (), args.getTest (), args.getOpportunity (), args.getTestOppKey (),
        args.getClientName (),
        args.getSessionKey ());
  }

  /**
   * 
   * @param connection
   * @param oppkey
   * @param file
   *          this method invokes main XML-Reporting method and writes XML in
   *          the hardcoded file
   * @throws ReturnStatusException
   */
  public SingleDataResultSet SubmitQAReport_SP (SQLConnection connection, UUID oppkey, String statusChange) throws ReturnStatusException
  {
    // BufferedWriter writer = null;
    String xmlFile = null;
    String file = null;
    SingleDataResultSet res = null;
    String status = null;

    final String cmd = "select status from testopportunity where _Key = ${oppkey}";
    SqlParametersMaps parms = (new SqlParametersMaps ()).put ("oppkey", oppkey);
    SingleDataResultSet result1 = executeStatement (connection, cmd, parms, false).getResultSets ().next ();
    DbResultRecord record = (result1.getCount () > 0 ? result1.getRecords ().next () : null);
    if (record != null) {
      status = record.<String> get ("status");
    }

    if (IsXMLOn_Fn (connection, oppkey) == 0 ||
        ("submitted".equalsIgnoreCase (status) || "reported".equalsIgnoreCase (status))) {
      return ReturnStatusReason ("success", null);
    }

    Date starttime = _dateUtil.getDateWRetStatus (connection);

    String currentRes = null;
    try {
      currentRes = _reportingDll.XML_GetOppXML_SP (connection, oppkey, false);
    } catch (ReturnStatusException ex) {
      // already logged
      return _ReturnError_SP (connection, null, "SubmitQAReport", ex.getMessage (), null, oppkey, null, "failed");
    }

    try {

      // String userdir = System.getProperty ("user.dir");
      // file = userdir + "\\testlogs\\XML-Reports\\XML-Report";
      // xmlFile = file + oppkey.toString ();
      // xmlFile = xmlFile + ".xml";
      String dir = getTdsSettings ().getTDSReportsRootDirectory ();
      if (dir == null) {
        throw new Exception ("No TDSReportingRootDirectory configured in property file!");
      }
      if (dir.endsWith ("/") || dir.endsWith ("\\"))
        dir = dir.substring (0, dir.length () - 1);
      xmlFile = String.format ("%s/%s.xml", dir, oppkey.toString ());
      // Make sure that separator is specific per the current operating system
      xmlFile = replaceSeparatorChar (xmlFile);

      File logFile = new File (xmlFile);
      if (!logFile.getParentFile ().exists ()) {
        _logger.debug ("Creating directory: " + logFile.getParentFile ());

        boolean result = logFile.getParentFile ().mkdirs ();
        if (result) {
          _logger.debug ("DIR: " + logFile.getParentFile () + "  created");
        }
      }
      try (BufferedWriter writer = new BufferedWriter (new FileWriter (logFile))) {
        writer.write (currentRes);
      }

    } catch (Exception w) {
      String msg = String.format ("Unable to queue up XML report: %s", w.getMessage ());
      _logger.error (msg);
      return _ReturnError_SP (connection, null, "SubmitQAReport", msg, null, oppkey, null, "failed");
    }

    _LogDBLatency_SP (connection, "SubmitQAReport", starttime, null, true, null, oppkey);
    return res; // i.e. null
  }

  public SingleDataResultSet ReturnStatusReason (String status, String reason, String context, UUID oppkey, Integer opportunity) throws ReturnStatusException {

    List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
    CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
    rcd.put ("status", status);
    rcd.put ("reason", reason);
    if (oppkey != null)
      rcd.put ("oppkey", oppkey);
    if (opportunity != null)
      rcd.put ("opportunity", opportunity);
    if (context != null)
      rcd.put ("context", context);

    resultList.add (rcd);
    SingleDataResultSet rs = new SingleDataResultSet ();
    rs.addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs.addColumn ("reason", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs.addColumn ("oppkey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER);
    rs.addColumn ("opportunity", SQL_TYPE_To_JAVA_TYPE.INT);
    rs.addColumn ("context", SQL_TYPE_To_JAVA_TYPE.VARCHAR);

    rs.addRecords (resultList);

    return rs;
  }

  public SingleDataResultSet ReturnStatusReason (String status, String reason) throws ReturnStatusException {

    return ReturnStatusReason (status, reason, null, null, null);

  }

  public MultiDataResultSet _UpdateOpportunityAccommodations_SP (SQLConnection connection, UUID oppKey, int segment, String accoms, int isStarted, Boolean approved, Boolean restoreRTS,
      _Ref<String> error) throws ReturnStatusException {
    return _UpdateOpportunityAccommodations_SP (connection, oppKey, segment, accoms, isStarted, approved, restoreRTS, error, 0);
  }

  // ported by Udaya Kommineni.
  public MultiDataResultSet _UpdateOpportunityAccommodations_SP (SQLConnection connection, UUID oppKey, int segment, String accoms, int isStarted, Boolean approved, Boolean restoreRTS,
      _Ref<String> error, int debug) throws ReturnStatusException {

    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();
    SingleDataResultSet result = null;
    Date starttime = _dateUtil.getDateWRetStatus (connection);
    approved = true;
    restoreRTS = false;
    String clientName = null;
    String testKey = null;
    String testId = null;
    Boolean custom = false;

    final String SQL_QUERY1 = " select clientname, _efk_AdminSubject as testkey,  _efk_TestID as testID, customAccommodations as custom from testopportunity where _Key = ${oppkey};";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppKey);

    result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      clientName = record.<String> get ("clientname");
      testKey = record.<String> get ("testkey");
      testId = record.<String> get ("testID");
      custom = record.<Boolean> get ("custom");
    }

    DataBaseTable splitAccomCodesTbl = _SplitAccomCodes_FN (connection, clientName, testKey, accoms);
    // DataBaseTable clientTestAccomsTbl =
    // _ucommonDll.ClientTestAccommodations_FN (connection, clientName, testId);
    DataBaseTable testKeyAccomsTbl = TestKeyAccommodations_FN (connection, testKey);

    if (DbComparator.notEqual (debug, 0)) {
      final String SQL_QUERY2 = " select ${segment} as segment, ${clientname} as clientname, ${testkey} as testkey, ${accoms} as accoms;";
      SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("segment", segment).put ("clientname", clientName).put ("testkey", testKey).put ("accoms", accoms);
      SingleDataResultSet rs1 = executeStatement (connection, SQL_QUERY2, parms2, false).getResultSets ().next ();
      resultsets.add (rs1);

      final String SQL_QUERY3 = "  select * from ${splitTblName}";
      Map<String, String> unquotedParms1 = new HashMap<String, String> ();
      unquotedParms1.put ("splitTblName", splitAccomCodesTbl.getTableName ());
      SingleDataResultSet rs2 = executeStatement (connection, fixDataBaseNames (SQL_QUERY3, unquotedParms1), null, false).getResultSets ().next ();
      resultsets.add (rs2);

      // final String SQL_QUERY4 = "select * from ${clientTestAccomsTblName}";
      // Map<String, String> unquotedParms2 = new HashMap<String, String> ();
      // unquotedParms2.put ("clientTestAccomsTblName",
      // clientTestAccomsTbl.getTableName ());
      // SingleDataResultSet rs3 = executeStatement (connection,
      // fixDataBaseNames (SQL_QUERY4, unquotedParms2), null,
      // false).getResultSets ().next ();
      // resultsets.add (rs3);

      final String SQL_QUERY5 = "select AccType, AccCode, AccValue, AllowChange, studentControl, IsDefault, IsSelectable, valcount from ${testTblName} C,"
          + " ${splitTblName} S where S.code = C.AccCode  and segment = ${segment};";
      SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("segment", segment);
      Map<String, String> unquotedParms = new HashMap<String, String> ();
      unquotedParms.put ("splitTblName", splitAccomCodesTbl.getTableName ());
      unquotedParms.put ("testTblName", testKeyAccomsTbl.getTableName ());
      SingleDataResultSet rs4 = executeStatement (connection, fixDataBaseNames (SQL_QUERY5, unquotedParms), parms3, false).getResultSets ().next ();
      resultsets.add (rs4);
    }

    final DataBaseTable accomsTable = getDataBaseTable ("accoms").addColumn ("atype", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("acode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100)
        .addColumn ("avalue", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).addColumn ("allow", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("control", SQL_TYPE_To_JAVA_TYPE.BIT)
        .addColumn ("recordUsage", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("isDefault", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("isSelectable", SQL_TYPE_To_JAVA_TYPE.BIT)
        .addColumn ("valCount", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (accomsTable);
    Map<String, String> unquotedParms3 = new HashMap<String, String> ();
    unquotedParms3.put ("accomsTableName", accomsTable.getTableName ());

    final String SQL_INSERT1 = " insert into ${accomsTableName} (atype, acode, avalue, allow, control, isDefault, isSelectable, valcount, recordUsage) "
        + " select distinct AccType, AccCode, AccValue, AllowChange, studentControl, IsDefault, IsSelectable, valcount, "
        + " (select count(*) from ${ConfigDB}.client_toolusage where clientname = ${clientname} "
        + " and testID = ${testID} and tooltype = AccType and (recordUsage = 1 or reportUsage = 1) limit 1) "
        + " from ${testTblName} C, ${splitTblName} S where S.code = C.AccCode and segment = ${segment};";
    SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("clientname", clientName).put ("testID", testId).put ("segment", segment);
    Map<String, String> unquotedParms4 = new HashMap<String, String> ();
    unquotedParms4.put ("accomsTableName", accomsTable.getTableName ());
    unquotedParms4.put ("splitTblName", splitAccomCodesTbl.getTableName ());
    unquotedParms4.put ("testTblName", testKeyAccomsTbl.getTableName ());

    final String query1 = fixDataBaseNames (SQL_INSERT1);
    int insertedCnt = executeStatement (connection, fixDataBaseNames (query1, unquotedParms4), parms4, false).getUpdateCount ();
    // System.err.println (insertedCnt); // for testing

    if (DbComparator.notEqual (debug, 0)) {
      final String SQL_QUERY6 = "select * from ${accomsTableName};";
      SingleDataResultSet rs5 = executeStatement (connection, fixDataBaseNames (SQL_QUERY6, unquotedParms3), null, false).getResultSets ().next ();
      resultsets.add (rs5);
    }

    if (DbComparator.notEqual (isStarted, 0)) {
      final String SQL_DELETE1 = "delete from ${accomsTableName} where allow = 0;";
      int deletedCnt = executeStatement (connection, fixDataBaseNames (SQL_DELETE1, unquotedParms3), null, false).getUpdateCount ();
      // System.err.println (deletedCnt); // for testing
    }

    if (DbComparator.isEqual (restoreRTS, true)) {
      final String SQL_DELETE2 = "delete from ${accomsTableName} where isSelectable = 1;";
      int deletedCnt = executeStatement (connection, fixDataBaseNames (SQL_DELETE2, unquotedParms3), null, false).getUpdateCount ();
      // System.err.println (deletedCnt); // for testing
    }
    final String SQL_QUERY7 = "select  isDefault from  ${accomsTableName} where isDefault = 0 limit 1";
    if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY7, unquotedParms3), null, false))) {
      custom = true;
    }

    try {
      boolean preexistingAutoCommitMode = connection.getAutoCommit ();
      connection.setAutoCommit (false);

      final String SQL_DELETE3 = "delete from testeeaccommodations where _fk_TestOpportunity = ${oppkey} and AccType in (select distinct atype from ${accomsTableName}) and segment = ${segment};";
      SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("oppkey", oppKey).put ("segment", segment);
      int deletedCnt = executeStatement (connection, fixDataBaseNames (SQL_DELETE3, unquotedParms3), parms5, false).getUpdateCount ();
      // System.err.println (deletedCnt); // for testing

      final String SQL_INSERT2 = "insert into testeeaccommodations (_fk_TestOpportunity, AccType, AccCode, AccValue, _date, allowChange, recordUsage, testeeControl, segment, "
          + " valueCount, isApproved, IsSelectable)"
          + " select distinct ${oppkey}, atype, acode, avalue, ${starttime}, allow, recordUsage, control, ${segment}, "
          + " valcount, case valcount when 1 then 1 else ${approved} end, isSelectable from ${accomsTableName};";
      SqlParametersMaps parms6 = (new SqlParametersMaps ()).put ("oppkey", oppKey).put ("starttime", starttime).put ("segment", segment).put ("approved", approved);
      int insertedCnt1 = executeStatement (connection, fixDataBaseNames (SQL_INSERT2, unquotedParms3), parms6, false).getUpdateCount ();
      // System.err.println (insertedCnt1); // for testing

      final String SQL_QUERY8 = "select  atype from ${accomsTableName} where atype = 'Language' limit 1";
      if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY8, unquotedParms3), null, false))) {
        final String SQL_UPDATE1 = " update testopportunity T, ${accomsTableName} set T.Language = avalue, T.customAccommodations = ${custom}  where atype = 'Language' and _Key = ${oppkey}; ";
        SqlParametersMaps parms7 = (new SqlParametersMaps ()).put ("custom", custom).put ("oppkey", oppKey);
        int updateCnt = executeStatement (connection, fixDataBaseNames (SQL_UPDATE1, unquotedParms3), parms7, false).getUpdateCount ();
        // System.err.println (updateCnt); // for testing
      } else {
        final String SQL_UPDATE2 = " update testopportunity set customAccommodations = ${custom} where _Key = ${oppkey};";
        SqlParametersMaps parms8 = (new SqlParametersMaps ()).put ("custom", custom).put ("oppkey", oppKey);
        int updateCnt = executeStatement (connection, SQL_UPDATE2, parms8, false).getUpdateCount ();
        // System.err.println (updateCnt); // for testing
      }

      connection.commit ();
      connection.setAutoCommit (preexistingAutoCommitMode);
    } catch (ReturnStatusException re) {
      try {
        connection.rollback ();
      } catch (SQLException e) {
        _logger.error (String.format ("Problem rolling back transaction: %s", e.getMessage ()));
      }
      error.set (String.format ("Error setting accommodations: %s", re.getMessage ()));
      _LogDBLatency_SP (connection, "_UpdateOpportunityAccommodations", starttime, null, true, null, oppKey, null, null, null);

      connection.dropTemporaryTable (accomsTable);
      connection.dropTemporaryTable (testKeyAccomsTbl);
      connection.dropTemporaryTable (splitAccomCodesTbl);
      return null;

    } catch (SQLException se) {
      throw new ReturnStatusException (se);
    }

    connection.dropTemporaryTable (accomsTable);
    connection.dropTemporaryTable (testKeyAccomsTbl);
    connection.dropTemporaryTable (splitAccomCodesTbl);

    final DataBaseTable depsTable = getDataBaseTable ("deps").addColumn ("atype", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("aval", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50)
        .addColumn ("acode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("del", SQL_TYPE_To_JAVA_TYPE.BIT);
    connection.createTemporaryTable (depsTable);
    Map<String, String> unquotedParms5 = new HashMap<String, String> ();
    unquotedParms5.put ("depsTableName", depsTable.getTableName ());

    final String SQL_INSERT3 = "  insert into ${depsTableName} (atype, aval, acode, del)"
        + " select AccType, AccValue, AccCode, 0 from testeeaccommodations A where _fk_TestOpportunity= ${oppkey}"
        + " and exists"
        + " (select * from ${ConfigDB}.client_tooldependencies D where D.ContextType = 'Test' and D.Context = ${testID} and"
        + " D.clientname = ${clientname} and A.AccType = D.ThenType and A.AccCode = D.ThenValue);";
    SqlParametersMaps parms9 = (new SqlParametersMaps ()).put ("oppkey", oppKey).put ("testID", testId).put ("clientname", clientName);
    final String query3 = fixDataBaseNames (SQL_INSERT3);
    int insertedCnt2 = executeStatement (connection, fixDataBaseNames (query3, unquotedParms5), parms9, false).getUpdateCount ();
    // System.err.println (insertedCnt2); // for testing

    final String SQL_UPDATE3 = " update ${depsTableName} set del = 1"
        + " where not exists (select * from testeeaccommodations B, ${ConfigDB}.client_tooldependencies D  where _fk_TestOpportunity = ${oppkey}"
        + " and D.ContextType = 'Test' and D.Context = ${testID} and D.clientname = ${clientname}"
        + " and D.ThenType = atype and D.ThenValue = acode and B.AccType = D.IfType and B.AccCode = D.IfValue)";
    SqlParametersMaps parms10 = parms9;
    final String query = fixDataBaseNames (SQL_UPDATE3);
    int updateCnt = executeStatement (connection, fixDataBaseNames (query, unquotedParms5), parms10, false).getUpdateCount ();
    // System.err.println (updateCnt); // for testing

    final String SQL_QUERY9 = "select  del from ${depsTableName} where del = 1 limit 1";
    if (exists (executeStatement (connection, fixDataBaseNames (SQL_QUERY9, unquotedParms5), null, false))) {
      final String SQL_DELETE4 = " delete from testeeaccommodations where _fk_Testopportunity = ${oppkey} and exists "
          + " (select * from ${depsTableName} where del = 1 and AccType = atype and AccCode = acode)";
      SqlParametersMaps parms11 = (new SqlParametersMaps ()).put ("oppkey", oppKey);
      int deletedCnt = executeStatement (connection, fixDataBaseNames (SQL_DELETE4, unquotedParms5), parms11, false).getUpdateCount ();
      // System.err.println (deletedCnt); // for testing
    }

    String accomString = P_FormatAccommodations_FN (connection, oppKey);

    final String SQL_UPDATE4 = " update testopportunity_readonly set AccommodationString = ${accomString} where _fk_TestOpportunity = ${oppkey};";
    SqlParametersMaps parms12 = (new SqlParametersMaps ()).put ("accomString", accomString).put ("oppkey", oppKey);
    int updateCnt1 = executeStatement (connection, SQL_UPDATE4, parms12, false).getUpdateCount ();
    // System.err.println (updateCnt1); // for testing

    _LogDBLatency_SP (connection, "_UpdateOpportunityAccommodations", starttime, null, true, null, oppKey, null, null, null);
    connection.dropTemporaryTable (depsTable);

    return new MultiDataResultSet (resultsets);
  }

  /**
   * @param connection
   * @param tblName
   * @param clientName
   * @param testId
   * @return
   * @throws ReturnStatusException
   */
  public DataBaseTable ClientTestAccommodations_FN (SQLConnection connection, String clientName, String testId) throws ReturnStatusException {

    DataBaseTable clientTestAccomsTable = getDataBaseTable ("clientTestAccoms").addColumn ("Segment", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("AccType", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 255)
        .addColumn ("AccValue", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 255).addColumn ("AccCode", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 255).addColumn ("AllowCombine", SQL_TYPE_To_JAVA_TYPE.BIT)
        .addColumn ("IsDefault", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("AllowChange", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("IsSelectable", SQL_TYPE_To_JAVA_TYPE.BIT)
        .addColumn ("IsVisible", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("studentControl", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("ValCount", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("DependsOnToolType", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50);
    connection.createTemporaryTable (clientTestAccomsTable);

    final String SQL_INSERT = "insert into ${tblName} (Segment, AccType, AccValue, AccCode, AllowCombine, IsDefault, AllowChange, IsSelectable, IsVisible, studentControl, ValCount, DependsOnToolType) "
        + "(SELECT 0 as Segment, Type as AccType, Value as AccValue, Code as AccCode, AllowCombine, IsDefault,  AllowChange, IsSelectable, IsVisible, studentControl, "
        + " (select count(*) from ${ConfigDB}.client_testtool TOOL where TOOL.ContextType = ${TEST} and TOOL.Context = ${testID}  and TOOL.clientname = ${clientname} and TOOL.Type = TT.Type) as ValCount, DependsOnToolType"
        + " FROM ${ConfigDB}.client_testtooltype TType, ${ConfigDB}.client_testtool TT where TType.ContextType = ${TEST} and TType.Context = ${testID} and TType.ClientName = ${clientname}"
        + " and TT.ContextType = ${TEST} and TT.Context = ${testID} and TT.ClientName = ${clientname} and TT.Type = TType.Toolname) "
        + " union"
        + " (SELECT SegmentPosition as Segment, Type as AccType, Value as AccValue, Code as AccCode, AllowCombine, IsDefault,  AllowChange, IsSelectable, IsVisible, studentControl,"
        + " (select count(*) from ${ConfigDB}.client_testtool TOOL where TOOL.ContextType = ${TEST} and TOOL.Context = ${testID} and TOOL.clientname = ${clientname} and TOOL.Type = TT.Type) as ValCount, null "
        + " FROM ${ConfigDB}.client_testtooltype TType, ${ConfigDB}.client_testtool TT, ${ConfigDB}.client_segmentproperties where parentTest = ${testID} and TType.ContextType = ${SEGMENT} and TType.Context = segmentID"
        + " and TType.ClientName = ${clientname} and TT.ContextType = ${SEGMENT} and TT.Context = segmentID and TT.ClientName = ${clientname} and TT.Type = TType.Toolname) "
        + " union"
        + " (select 0, Type, Value, Code, AllowCombine, IsDefault, AllowChange, IsSelectable, IsVisible, studentControl, "
        + " (select count(*) from ${ConfigDB}.client_testtool TOOL where TOOL.ContextType = ${TEST} and TOOL.Context = ${starParam} and TOOL.clientname = ${clientname} and TOOL.Type = TT.Type) as ValCount, DependsOnToolType"
        + " FROM  ${ConfigDB}.client_testtooltype TType, ${ConfigDB}.client_testtool TT where TType.ContextType = ${TEST} and TType.Context = ${starParam} and TType.ClientName = ${clientname}"
        + " and TT.ContextType = ${TEST} and TT.Context = ${starParam} and TT.ClientName = ${clientname} and TT.Type = TType.Toolname "
        + " and not exists"
        + " (select * from ${ConfigDB}.client_testtooltype Tool where Tool.ContextType = ${TEST} and Tool.Context = ${testID} and Tool.Toolname = TType.Toolname and Tool.Clientname = ${clientname})) ";

    String query = fixDataBaseNames (SQL_INSERT);
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("tblName", clientTestAccomsTable.getTableName ());
    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("clientname", clientName).put ("testID", testId).put ("TEST", "TEST").put ("SEGMENT", "SEGMENT").put ("starParam", "*");
    int insertedCnt = executeStatement (connection, fixDataBaseNames (query, unquotedParms), parameters, false).getUpdateCount ();
    return clientTestAccomsTable;
  }

  /**
   * After discussing with sai, please note that i changed from "desc" to "asc"
   * in the below query for the o/p format.
   * 
   * @param connection
   * @param oppKey
   * @return
   * @throws ReturnStatusException
   */
  public String P_FormatAccommodations_FN (SQLConnection connection, UUID oppKey) throws ReturnStatusException {

    String result = null;
    String avalue = null;

    final String SQL_QUERY = "select concat(AccType, ': ', AccValue) as avalue from testeeaccommodations where _fk_TestOpportunity = ${oppkey} and segment = 0 order by AccType asc;";
    SqlParametersMaps params = new SqlParametersMaps ().put ("oppkey", oppKey);
    SingleDataResultSet rs = executeStatement (connection, SQL_QUERY, params, false).getResultSets ().next ();
    Iterator<DbResultRecord> records = rs.getRecords ();
    while (records.hasNext ()) {
      DbResultRecord record = records.next ();
      avalue = record.<String> get ("avalue");
      if (result == null) {
        result = avalue;
      } else {
        result = result + " | " + avalue;
      }
    }
    return result;
  }

  /**
   * @param connection
   * @param bankkey
   * @param itemkey
   * @return
   * @throws ReturnStatusException
   */
  public String ITEMBANK_ItemFile_FN (SQLConnection connection, long bankkey, long itemkey) throws ReturnStatusException {
    String path = null;
    String makeItemkey = MakeItemKey_FN (connection, bankkey, itemkey);
    final String SQL_QUERY = "select concat(C.Homepath, B.HomePath, B.ItemPath, I.FilePath, I.FileName) as path from ${ItemBankDB}.tblitembank B, ${ItemBankDB}.tblclient C, ${ItemBankDB}.tblitem I" +
        " where B._efk_Itembank = ${bankkey} and B._fk_Client = C._Key and I._Key = ${makeItemKey}";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("bankkey", bankkey).put ("makeItemkey", makeItemkey);
    String finalQuery = fixDataBaseNames (SQL_QUERY);
    SingleDataResultSet result = executeStatement (connection, finalQuery, parms, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      path = record.<String> get ("path");
      path = replaceSeparatorChar (path);
    }
    return path;

  }

  public DataBaseTable _SplitAccomCodes_FN (SQLConnection connection, String clientname, String testkey, String accoms) throws ReturnStatusException {

    String testId = null, family = null;
    final String SQL_QUERY1 = "select K.testID,  AccommodationFamily as family from  ${ConfigDB}.client_testmode K, ${ConfigDB}.client_testproperties P "
        + " where P.clientname = ${clientname} and K.clientname = ${clientname} and K.testkey = ${testkey} and K.testID = P.testID";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("testkey", testkey);

    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY1), parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      testId = record.<String> get ("testId");
      family = record.<String> get ("family");
    }

    Character codeDelim = '|';
    Character delim = ';';
    Character familyDelim = ':';
    String famLine = null;
    if (family != null)
      famLine = String.format ("%s%s", family, familyDelim);
    // famLine = (family == null ? String.format ("%s", familyDelim) :
    // String.format ("%s%s", family, familyDelim));

    // Just sanity check to avoid exception on checking splits.length
    if (accoms == null)
      accoms = "";
    String[] splits = _BuildTableAsArray (accoms, delim.toString (), -1);
    String cset1 = null;
    for (int i = 0; i < splits.length; i++) {
      String rec = splits[i];
      if (rec.indexOf (':') > -1 && famLine != null && rec.indexOf (famLine) == -1)
        // if (rec.indexOf (':') > -1 && rec.indexOf (famLine) == -1)
        splits[i] = null;
      if (famLine != null && rec.indexOf (famLine) >= 0) {
        // if (rec.indexOf (famLine) >= 0) {
        rec = rec.substring (family.length () + 1);
        splits[i] = rec;
      }
      if (splits[i] != null) {
        if (cset1 == null)
          cset1 = splits[i];
        else
          cset1 = String.format ("%s%s%s", cset1, codeDelim, splits[i]);
      }
    }
    // MA:A402;MA:A501;SS:A208;SS:A204;SS:A307;SS:A402;SS:A104;SS:A302;SS:A212;SS:A213;SS:A107;SS:A308;SS:A501;SS:A103;SS:A401;SS:A105;SS:A303;SS:A101;SS:A404;SC:ENU;RE:ENU-Braille;WR:ENU;SS:TDS_TTS0;MA:TDS_TTS_Item;SC:TDS_TTS0
    String[] split1 = _BuildTableAsArray (cset1, codeDelim.toString (), -1);

    DataBaseTable tbl = getDataBaseTable ("sac").addColumn ("idx", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("code", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50);

    final String[] split1Final = split1;
    executeMethodAndInsertIntoTemporaryTable (connection, new AbstractDataResultExecutor ()
    {
      @Override
      public SingleDataResultSet execute (SQLConnection connection) throws ReturnStatusException {

        List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
        int idx = 1;
        if (split1Final != null) {
          for (String split : split1Final) {
            CaseInsensitiveMap<Object> record = new CaseInsensitiveMap<Object> ();
            record.put ("code", (split.length () > 50 ? split.substring (0, 50) : split));
            record.put ("idx", idx++);
            resultList.add (record);
          }
        }
        SingleDataResultSet rs = new SingleDataResultSet ();
        rs.addColumn ("code", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
        rs.addColumn ("idx", SQL_TYPE_To_JAVA_TYPE.INT);
        rs.addRecords (resultList);

        return rs;
      }
    }, tbl, true);

    return tbl;
  }

  public String getLocalhostName () {
    String localhostname = null;
    try {
      localhostname = InetAddress.getLocalHost ().getHostName ();
    } catch (UnknownHostException e) {

    }
    return localhostname;
  }

  public String getExternsColumnByClientName (SQLConnection connection, String clientName, String columnName) throws ReturnStatusException {
    String columnValue = null;
    final String SQL_QUERY1 = "select ${columnName} from externs where clientname = ${clientname};";

    Map<String, String> unquotedNames = new HashMap<String, String> ();
    unquotedNames.put ("columnName", columnName);

    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("clientname", clientName);
    parameters.put ("clientname", clientName);

    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY1, unquotedNames), parameters, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      columnValue = record.<String> get (columnName);
    }
    return columnValue;
  }

  public Date adjustDate (Date theDate, int increment, int incrementUnit) throws ReturnStatusException {
    if (theDate == null)
      return theDate;
    if (incrementUnit != Calendar.MINUTE && incrementUnit != Calendar.SECOND && incrementUnit != Calendar.HOUR
        && incrementUnit != Calendar.DATE) {
      throw new ReturnStatusException ("Invalid date increment unit, must be CALENDAR.second, minute, hoir or date");
    }
    Calendar c = Calendar.getInstance ();
    c.setTime (theDate);
    c.add (incrementUnit, increment);
    return c.getTime ();
  }

  public Date adjustDateMinutes (Date theDate, Integer increment) {
    if (theDate == null || increment == null)
      return null;

    Calendar c = Calendar.getInstance ();
    c.setTime (theDate);
    c.add (Calendar.MINUTE, increment);
    return c.getTime ();
  }

  /**
   * MySql application locks do not support notion of lockmode We decided to use
   * GET_LOCK/RELEASE_LOCK functionality as sufficient; we use 0 as timeout
   * (second) parameter See
   * http://dev.mysql.com/doc/refman/5.6/en/miscellaneous-
   * functions.html#function_get-lock returns: 1 - success, 0 - timeout, null -
   * error
   * 
   * @param connection
   * @param resourcename
   * @param lockmode
   * @return
   * @throws ReturnStatusException
   */
  public Integer getAppLock (SQLConnection connection, String resourcename, String lockmode) throws ReturnStatusException {
    long startTime = System.currentTimeMillis ();
    Integer applock = null;
    Integer currentSleep = gLockRetrySleepInterval;
    Integer cumulativeSleep = 0;
    for (int cnt = 0; cnt < gLockRetryAttemptMax; cnt++) {
      applock = getAppLockInternal (connection, resourcename, lockmode, cnt);
      if (applock == null || applock != 1) {
        _logger.info (String.format ("Failed getAppLock, attempt %d, cumulative wait %d millisec", cnt + 1, cumulativeSleep));
        try {
          Thread.sleep (currentSleep);
        } catch (InterruptedException e) {
        }
        cumulativeSleep += currentSleep;
        // currentSleep = 2*currentSleep;
      } else
        return applock;
    }
    if (applock == null || applock != 1)
      _logger.error (String.format ("Final failure to getAppLock after max attempts %d, cumulative wait %d millisec, Total Time %d ms", gLockRetryAttemptMax, cumulativeSleep,
          (System.currentTimeMillis () - startTime)));

    return applock;
  }

  private Integer getAppLockInternal (SQLConnection connection, String resourcename, String lockmode, int cnt) throws ReturnStatusException {

    Long applock = null;
    final String SQL_QUERY = "select GET_LOCK (${resourcename}, 0) as lk";

    SqlParametersMaps parms = (new SqlParametersMaps ()).put ("resourcename", resourcename);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY, parms, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null)
      applock = record.<Long> get ("lk");

    Integer rt = (applock == null ? null : applock.intValue ());

    return rt;
  }

  /**
   * MySql application locks do not support notion of lockmode We decided to use
   * GET_LOCK/RELEASE_LOCK functionality as sufficient;
   * http://dev.mysql.com/doc/
   * refman/5.6/en/miscellaneous-functions.html#function_release-lock
   */
  public void releaseAppLock (SQLConnection connection, String resourcename) throws ReturnStatusException {

    final String SQL_QUERY = "select release_lock(${resourcename}) as unlk";

    SqlParametersMaps parms = (new SqlParametersMaps ()).put ("resourcename", resourcename);
    executeStatement (connection, SQL_QUERY, parms, false);
  }

  /**
   * 
   * @param connection
   * @param testkey
   * @param debug
   * @return
   * @throws ReturnStatusException
   *           Common function for ItemSelectionDLL and ReportingDLL
   */
  public String TestKeyClient_FN (SQLConnection connection, String testkey)
      throws ReturnStatusException {
    String clientname = null;
    try {
      SingleDataResultSet result;
      DbResultRecord record;

      String query = "select C.Name as clientname "
          + " from ${ItemBankDB}.tblsetofadminsubjects S, "
          + " ${ItemBankDB}.tblclient C, "
          + " ${ItemBankDB}.tbltestadmin A "
          + " where  A._fk_Client = C._Key and S._Key = ${testkey} "
          + " and S._fk_TestAdmin = A._key";
      SqlParametersMaps parameters = new SqlParametersMaps ().put (
          "testkey", testkey);
      result = executeStatement (connection, fixDataBaseNames (query),
          parameters, true).getResultSets ().next ();
      record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        clientname = record.<String> get ("clientname");
      }
    } catch (Exception e) {
      _logger.error (e.getMessage ());
      throw new ReturnStatusException (e);
    }

    return clientname;
  }

  public String _CoreSessName_FN (SQLConnection connection, String clientName, String procName) throws ReturnStatusException {

    String sessname = null;

    sessname = StringUtils.substringAfterLast (procName, " ");
    if (sessname == null || sessname.length () == 0)
      sessname = procName.substring (0, 3).trim () + "-";
    else if (sessname.length () > 4)
      sessname = sessname.substring (0, 3).trim () + "-";
    else
      sessname += "-";

    return sessname;
  }

  /**
   * @param connection
   * @param clientName
   * @param prefix
   * @param sessionId
   * @throws ReturnStatusException
   */
  public void _CreateClientSessionID_SP (SQLConnection connection, String clientName, String prefix, _Ref<String> sessionId) throws ReturnStatusException {

    sessionId.set (null);
    Integer suffix = null;
    String resourcename = String.format ("createsession %s", clientName);
    // -- indicates no applock obtained
    Integer applock = -1;
    String msg = null;

    // on MySql getAppLock returns 1 if success
    applock = getAppLock (connection, resourcename, "Exclusive");
    if (applock == null || applock != 1) {
      return;
    }
    try {
      boolean preexistingAutoCommitMode = connection.getAutoCommit ();
      connection.setAutoCommit (false);

      final String SQL_QUERY1 = "select clientname from client_sessionid where clientname = ${clientname} and IdPrefix = ${prefix} limit 1";
      SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("clientname", clientName).put ("prefix", prefix);
      if (!exists (executeStatement (connection, SQL_QUERY1, parms1, false))) {
        final String SQL_QUERY2 = "select bigtoint(initialSessionID) as suffix from externs where clientname = ${clientname};";
        SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("clientname", clientName);
        SingleDataResultSet result = executeStatement (connection, SQL_QUERY2, parms2, false).getResultSets ().next ();
        DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
        if (record != null) {
          suffix = record.<Integer> get ("suffix");
        }
        final String SQL_INSERT = "insert into client_sessionid (clientname, IdPrefix, cnt) values (${clientname}, ${prefix}, ${suffix});";
        SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("clientname", clientName).put ("prefix", prefix).put ("suffix", suffix);
        int insertedCnt = executeStatement (connection, SQL_INSERT, parms3, false).getUpdateCount ();
      } else {
        final String SQL_QUERY3 = "select  bigtoint(cnt + 1) as suffix from client_sessionid where clientname = ${clientname} and IdPrefix = ${prefix};";
        SqlParametersMaps parms4 = parms1;
        SingleDataResultSet result = executeStatement (connection, SQL_QUERY3, parms4, false).getResultSets ().next ();
        DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
        if (record != null) {
          suffix = record.<Integer> get ("suffix");
        }
        final String SQL_UPDATE = "update client_sessionid set cnt = ${suffix} where clientname = ${clientname} and IdPrefix = ${prefix};";
        SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("clientname", clientName).put ("prefix", prefix).put ("suffix", suffix);
        int insertedCnt = executeStatement (connection, SQL_UPDATE, parms5, false).getUpdateCount ();
      }
      sessionId.set (String.format ("%s%s", prefix, suffix));
      // releaseAppLock (connection, resourcename);
      connection.commit ();
      connection.setAutoCommit (preexistingAutoCommitMode);
      releaseAppLock (connection, resourcename);
      return;
    } catch (SQLException se) {
      msg = se.getMessage ();
    } catch (ReturnStatusException re) {
      msg = re.getMessage ();
    }
    // this will kick if we caught SQLException or ReturnStatusException
    // need to release app lock and rollback transaction

    try {
      connection.rollback ();
    } catch (SQLException se) {
      _logger.error (String.format ("Failed rollback: %s", se.getMessage ()));
    }
    if (DbComparator.isEqual (applock, 1))
      releaseAppLock (connection, resourcename);

    _LogDBError_SP (connection, "_CreateClientSessionID", msg, null, null, null, null, clientName, null);
  }

  /**
   * Udaya: In the original stored procedure, please note that there is a
   * parameter called "@environment" in the T_GetBrowserWhiteList. This
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

  public SingleDataResultSet T_GetBrowserWhiteList_SP (SQLConnection connection, String clientName, String appName) throws ReturnStatusException {
    return T_GetBrowserWhiteList_SP (connection, clientName, appName, null);
  }

  public SingleDataResultSet T_GetBrowserWhiteList_SP (SQLConnection connection, String clientName, String appName, String context) throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);
    final String SQL_QUERY = "SELECT Context, ContextType, BrowserName, OSName, HW_Arch, BrowserMinVersion, BrowserMaxVersion, Action, Priority, OSMinVersion, OSMaxVersion, MessageKey"
        + " FROM ${ConfigDB}.system_browserwhitelist L, _externs E WHERE L.ClientName= ${clientName} and E.clientname = ${clientName} and L.environment = E.environment AND AppName= ${appName}"
        + " and (${context} IS NULL OR  Context = ${context})";
    String finalQuery = fixDataBaseNames (SQL_QUERY);
    SqlParametersMaps parameters = new SqlParametersMaps ().put ("clientName", clientName).put ("appName", appName).put ("context", context);
    SingleDataResultSet result = executeStatement (connection, finalQuery, parameters, false).getResultSets ().next ();

    _LogDBLatency_SP (connection, "T_GetBrowserWhiteList", starttime, null, true, null, null, null, null, null);
    return result;
  }

  private String replaceSeparatorChar (String str) {
    return str.replace ('/', java.io.File.separatorChar).replace ('\\', java.io.File.separatorChar);
  }
}
