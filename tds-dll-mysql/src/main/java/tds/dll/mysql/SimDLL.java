/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.mysql;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import TDS.Shared.Exceptions.ReturnStatusException;

import tds.dll.api.ICommonDLL;
import tds.dll.api.ISimDLL;
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

public class SimDLL extends AbstractDLL implements ISimDLL
{
  private static Logger       _logger    = LoggerFactory.getLogger (SimDLL.class);
  @Autowired
  private ICommonDLL          _commonDll = null;
  @Autowired
  private AbstractDateUtilDll _dateUtil  = null;

  public SingleDataResultSet SIM_GetUserClients_SP (SQLConnection connection, String userId) throws ReturnStatusException {
    SingleDataResultSet rs = null;

    final String cmd1 = "select C.clientname, C.isAdmin from sim_userclient C, _externs E "
        + " where C._fk_simUser = ${userId} and C.clientname = E.clientname and E.environment = 'SIMULATION'";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("userId", userId);

    rs = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();
    return rs;
  }

  public SingleDataResultSet SIM_ValidateUser_SP (SQLConnection connection, String userId) throws ReturnStatusException {
    return SIM_ValidateUser_SP (connection, userId, null);
  }

  public SingleDataResultSet SIM_ValidateUser_SP (SQLConnection connection, String userId, String password) throws ReturnStatusException {
    SingleDataResultSet rs = null;

    final String cmd1 = "select userName, browserKey from sim_user "
        + " where userID =${userId} and (${password} is null or password = ${password})";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("userId", userId).put ("password", password);

    rs = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();
    return rs;
  }

  public SingleDataResultSet SIM_CreateSession_SP (SQLConnection connection, String clientname, String userId, String sessionName) throws ReturnStatusException {
    return SIM_CreateSession_SP (connection, clientname, userId, sessionName, "ENU", null, 0);
  }

  public SingleDataResultSet SIM_CreateSession_SP (SQLConnection connection, String clientname, String userId, String sessionName,
      String language) throws ReturnStatusException {
    return SIM_CreateSession_SP (connection, clientname, userId, sessionName, language, null, 0);
  }

  public SingleDataResultSet SIM_CreateSession_SP (SQLConnection connection, String clientname, String userId, String sessionName,
      String language, String sessionDescription, Integer sessiontype) throws ReturnStatusException {
    Date starttime = _dateUtil.getDateWRetStatus (connection);
    String environment = null;
    final String cmd1 = "select  environment from externs where clientname = ${clientname}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("clientname", clientname);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (rs1.getCount () > 0 ? rs1.getRecords ().next () : null);
    if (record != null) {
      environment = record.<String> get ("environment");
    }
    if (environment == null || "SIMULATION".equals (environment) == false) {
      _commonDll._LogDBError_SP (connection, "SIM_CreateSession", "Unknown client", null, null, null, null, clientname, null);
      return _commonDll._ReturnError_SP (connection, clientname, "SIM_CreateSession", "Unknown client", clientname, null, null);
    }

    String userName = null;
    UUID browserKey = null;
    Long proctorKey = null;
    final String cmd2 = "    select userName, browserKey, userKey as proctorKey "
        + " from sim_user where userID = ${userId}";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("userId", userId);
    SingleDataResultSet rs2 = executeStatement (connection, cmd2, parms2, false).getResultSets ().next ();
    DbResultRecord record2 = (rs2.getCount () > 0 ? rs2.getRecords ().next () : null);
    if (record2 != null) {
      userName = record2.<String> get ("userName");
      browserKey = record2.<UUID> get ("browserKey");
      proctorKey = record2.<Long> get ("proctorKey");
    }
    if (browserKey == null) {
      _commonDll._LogDBError_SP (connection, "SIM_CreateSession", "Unknown user", null, null, null, null);
      return _commonDll._ReturnError_SP (connection, clientname, "SIM_CreateSession", "Unknown user");
    }

    final String cmd3 = "select _fk_simuser from sim_userclient where _fk_simUser = ${userID} and clientname = ${clientname} limit 1";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("userId", userId);
    if (!exists (executeStatement (connection, cmd3, parms3, false))) {
      _commonDll._LogDBError_SP (connection, "SIM_CreateSession", "User is not authorized on this client", null, null, null, null);
      return _commonDll._ReturnError_SP (connection, clientname, "SIM_CreateSession", "User is not authorized on this client");
    }

    String prefix = _commonDll._CoreSessName_FN (connection, clientname, userName);
    UUID sessionKey = UUID.randomUUID ();
    _Ref<String> sessionIdRef = new _Ref<String> ();

    _commonDll._CreateClientSessionID_SP (connection, clientname, prefix, sessionIdRef);
    if (sessionIdRef.get () == null) {
      _commonDll._LogDBError_SP (connection, "SIM_CreateSession", "Session ID creation failed", null, null, null, null, clientname, null);
      return _commonDll._ReturnError_SP (connection, clientname, "SIM_CreateSession", "Failed to insert new session into database");
    }

    try {

      final String cmd4 = "insert into session (_Key, `Name`, _efk_Proctor, ProctorID, ProctorName, `status`, DateBegin, DateEnd, "
          + " SessionID, _fk_browser, clientname, environment, dateVisited, sessiontype, description, sim_language,"
          + " datecreated, serveraddress) "
          + " values (${sessionKey}, ${sessionName}, ${proctorKey}, ${userID}, ${userName}, 'open', now(3), (now(3) + INTERVAL 1 YEAR), "
          + " ${sessionID}, ${browserKey}, ${clientname}, ${environment}, now(3), ${sessiontype}, ${sessionDescription}, ${language}, "
          + " now(3), ${localhost})";
      SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey).put ("sessionName", sessionName).
          put ("proctorKey", proctorKey).put ("userID", userId).put ("userName", userName).put ("sessionID", sessionIdRef.get ()).
          put ("browserKey", browserKey).put ("clientname", clientname).put ("environment", environment).put ("sessiontype", sessiontype).
          put ("sessionDescription", sessionDescription).put ("language", language).
          put ("localhost", _commonDll.getLocalhostName ());
      Integer cntInserted = executeStatement (connection, cmd4, parms4, false).getUpdateCount ();

    } catch (ReturnStatusException re) {
      // -- this is probably a violation of the uniqueness constraint on index
      // IX_SessionID
      String err = re.getMessage ();
      _commonDll._LogDBError_SP (connection, "SIM_CreateSession", err, null, null, null, null, clientname, null);
      return _commonDll._ReturnError_SP (connection, clientname, "SIM_CreateSession", "Failed to insert new session into database");
    }

    Integer tests = null;
    final String cmd5 = " select count(*) as tests from sessiontests where _fk_Session = ${sessionKey}";
    SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey);
    SingleDataResultSet rs5 = executeStatement (connection, cmd5, parms5, false).getResultSets ().next ();
    DbResultRecord record5 = (rs5.getCount () > 0 ? rs5.getRecords ().next () : null);
    if (record5 != null) {
      Long testsL = record5.<Long> get ("tests");
      tests = testsL.intValue ();
    }
    // select 'success' as status, @sessionKey as sessionKey, @sessionID as
    // sessionID, @sessionName as [Name], 'open' as sessionStatus,
    // (select count(*) from SEssionTests where _fk_Session = @sessionKey) as
    // tests;
    List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
    CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
    rcd.put ("status", "success");
    rcd.put ("sessionKey", sessionKey);
    rcd.put ("sessionID", sessionIdRef.get ());
    rcd.put ("sessionName", sessionName);
    rcd.put ("sessionStatus", "open");
    rcd.put ("tests", tests);

    resultList.add (rcd);

    SingleDataResultSet rs6 = new SingleDataResultSet ();
    rs6.addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs6.addColumn ("sessionKey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER);
    rs6.addColumn ("sessionID", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs6.addColumn ("sessionName", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs6.addColumn ("sessionStatus", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs6.addColumn ("tests", SQL_TYPE_To_JAVA_TYPE.INT);
    rs6.addRecords (resultList);

    _commonDll._LogDBLatency_SP (connection, "SIM_CreateSession", starttime, proctorKey, true, null, null, sessionKey, clientname, null);
    return rs6;
  }

  public SingleDataResultSet SIM_GetUserSessions_SP (SQLConnection connection, String userId) throws ReturnStatusException {
    return SIM_GetUserSessions_SP (connection, userId, null);
  }

  public SingleDataResultSet SIM_GetUserSessions_SP (SQLConnection connection, String userId, String clientname) throws ReturnStatusException {
    final String cmd1 = "select _Key, sessionID, status, name, description, dateCreated, clientname, _fk_browser, environment, sim_language as language, sim_proctorDelay"
        + ", sim_status, sim_start, sim_stop, sim_abort "
        + ",sessiontype, bigtoint((select count(*) from sessiontests where _fk_Session = _Key)) as numTests "
        + " from session "
        + " where environment = 'SIMULATION' and ProctorID = ${userID} and (${clientname} is null or clientname = ${clientname})";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("userId", userId);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();
    return rs1;
  }

  public SingleDataResultSet SIM_CopySession2_SP (SQLConnection connection, UUID fromSession, String sessionName) throws ReturnStatusException {
    return SIM_CopySession2_SP (connection, fromSession, sessionName, null, null);
  }

  public SingleDataResultSet SIM_CopySession2_SP (SQLConnection connection, UUID fromSession, String sessionName, String sessionDescription, String toUser) throws ReturnStatusException {
    Date starttime = _dateUtil.getDateWRetStatus (connection);
    Date now = starttime;
    Date dateBegin = null, dateEnd = null;
    String status = "closed";
    String userId = null;
    String userName = null;
    UUID browserKey = null;
    Long proctorKey = null;
    String clientname = null;
    String client = null;
    String language = null;
    Integer sessionType = null;
    String originalId = null;

    final String cmd1 = " select ProctorID as userId,  ProctorName as userName, _efk_Proctor as proctorKey,"
        + "  _fk_Browser as browserKey, clientname, sim_language as language, sessionType,  clientname as client,"
        + " sessionID as originalID "
        + "  from session where _Key = ${fromSession}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("fromsession", fromSession);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();
    DbResultRecord rcd1 = rs1.getCount () > 0 ? rs1.getRecords ().next () : null;
    if (rcd1 != null) {
      userId = rcd1.<String> get ("userId");
      userName = rcd1.<String> get ("userName");
      proctorKey = rcd1.<Long> get ("proctorKey");
      browserKey = rcd1.<UUID> get ("browserKey");
      clientname = rcd1.<String> get ("clientname");
      language = rcd1.<String> get ("language");
      sessionType = rcd1.<Integer> get ("sessionType");
      client = rcd1.<String> get ("client");
      originalId = rcd1.<String> get ("originalId");
    }

    if (toUser != null) {
      final String cmd2 = "select userID,  userName,  userkey as proctorKey, browserkey "
          + " from sim_user where userID = ${toUser}";
      SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("toUser", toUser);
      SingleDataResultSet rs2 = executeStatement (connection, cmd2, parms2, false).getResultSets ().next ();
      DbResultRecord rcd2 = rs2.getCount () > 0 ? rs2.getRecords ().next () : null;
      if (rcd2 != null) {
        userId = rcd2.<String> get ("userId");
        userName = rcd2.<String> get ("userName");
        proctorKey = rcd2.<Long> get ("proctorKey");
        browserKey = rcd2.<UUID> get ("browserKey");
      }
      sessionName = String.format ("Copy of %s", originalId);
    }

    String environment = null;
    final String cmd3 = "select  environment from externs where clientname = ${clientname}";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("clientname", clientname);
    SingleDataResultSet rs3 = executeStatement (connection, cmd3, parms3, false).getResultSets ().next ();
    DbResultRecord rcd3 = (rs3.getCount () > 0 ? rs3.getRecords ().next () : null);
    if (rcd3 != null) {
      environment = rcd3.<String> get ("environment");
    }
    if (environment == null || "SIMULATION".equals (environment) == false) {
      return _commonDll._ReturnError_SP (connection, clientname, "SIM_CopySession2", "Unknown client or wrong environment", clientname, null, null);
    }

    if (browserKey == null) {
      return _commonDll._ReturnError_SP (connection, client, "SIM_CopySession2", "Unknown session");
    }

    final String cmd4 = "select _fk_simuser from sim_userclient where _fk_simUser = ${userID} and clientname = ${clientname}";
    SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("userId", userId);
    if (!exists (executeStatement (connection, cmd4, parms4, false))) {
      return _commonDll._ReturnError_SP (connection, clientname, "SIM_CopySession2", "User is not authorized on this client");
    }

    String prefix = _commonDll._CoreSessName_FN (connection, clientname, userName);
    UUID sessionKey = UUID.randomUUID ();

    if (dateBegin == null)
      dateBegin = now;

    if (dateEnd == null) {
      dateEnd = DateUtils.addHours (dateBegin, 80);
    } else if (DbComparator.lessOrEqual (dateEnd, dateBegin)) {
      dateEnd = DateUtils.addHours (dateBegin, 80);
    }

    if (DbComparator.greaterOrEqual (now, dateBegin) && DbComparator.lessOrEqual (now, dateEnd)) {
      status = "open";
    }

    _Ref<String> sessionIdRef = new _Ref<> ();
    _commonDll._CreateClientSessionID_SP (connection, clientname, prefix, sessionIdRef);
    if (sessionIdRef.get () == null) {
      return _commonDll._ReturnError_SP (connection, client, "SIM_CopySession2", "Failed to create new session ID");
    }

    try {
      final String cmd5 = "insert into session "
          + " (_Key, `Name`, sim_language, _efk_Proctor, ProctorID, ProctorName, "
          + "  `status`, DateBegin, DateEnd, "
          + "   SessionID, _fk_browser, clientname, environment, dateVisited, sessiontype, description,"
          + "   datecreated, serveraddress) "
          + " values (${sessionKey}, ${sessionName}, ${language}, ${proctorKey}, ${userID}, ${userName}, "
          + "         ${status}, ${dateBegin}, ${dateEnd},"
          + "         ${sessionID}, ${browserKey}, ${clientname}, ${environment}, now(3), ${sessiontype}, ${sessionDescription}, "
          + "         now(3), ${localhost})";
      SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey).put ("sessionName", sessionName).
          put ("language", language).put ("proctorKey", proctorKey).put ("userId", userId).put ("userName", userName).
          put ("status", status).put ("dateBegin", dateBegin).put ("dateEnd", dateEnd).put ("sessionId", sessionIdRef.get ()).
          put ("browserKey", browserKey).put ("clientname", clientname).put ("environment", environment).
          put ("sessionType", sessionType).put ("sessionDescription", sessionDescription).
          put ("localhost", _commonDll.getLocalhostName ());

      Integer insertedCnt = executeStatement (connection, cmd5, parms5, false).getUpdateCount ();

    } catch (ReturnStatusException re) {
      String err = re.getMessage ();
      _commonDll._LogDBError_SP (connection, "SIM_CopySession2", err, null, null, null, null, clientname, null);
      return _commonDll._ReturnError_SP (connection, client, "SIM_CopySession", "Failed to insert new session into database");
    }

    final String cmd6 = "INSERT INTO sessiontests(_fk_Session, _efk_AdminSubject, _efk_TestID, iterations, "
        + "   opportunities, meanProficiency, sdProficiency, strandCorrelation, sim_threads, sim_thinkTime, handscoreitemtypes) "
        + " SELECT ${sessionKey}, _efk_AdminSubject, _efk_TestID, iterations, "
        + "        opportunities, meanProficiency, sdProficiency, strandCorrelation, sim_threads, sim_thinkTime, handscoreitemtypes "
        + " FROM sessiontests where _fk_Session = ${fromSession}";
    SqlParametersMaps parms6 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey).put ("fromSession", fromSession);

    Integer insertedCnt = executeStatement (connection, cmd6, parms6, false).getUpdateCount ();

    final String cmd7 = "INSERT INTO sim_segment(_fk_session, _efk_AdminSubject, _efk_Segment, StartAbility, StartInfo, MinItems, MaxItems "
        + "  , FTStartPos, FTEndPos, FTMinItems, FTMaxItems "
        + "  , formSelection, blueprintWeight, cset1size, cset2Random, cset2InitialRandom, loadConfig, updateConfig, itemWeight"
        + "  , abilityOffset, segmentPosition, segmentID, selectionAlgorithm, cset1Order "
        + "  , rcAbilityWeight, abilityWeight, precisionTargetNotMetWeight, precisionTargetMetWeight, precisionTarget, adaptiveCut, tooCloseSEs "
        + "  , terminationMinCount, terminationOverallInfo, terminationRCInfo, terminationTooClose, terminationFlagsAnd) "
        + " SELECT ${sessionKey}, _efk_AdminSubject, _efk_Segment, StartAbility, StartInfo, MinItems, MaxItems"
        + "  , FTStartPos, FTEndPos, FTMinItems, FTMaxItems "
        + "  , formSelection, blueprintWeight, cset1size, cset2Random, cset2InitialRandom, loadConfig, updateConfig, itemWeight "
        + "  , abilityOffset, segmentPosition, segmentID, selectionAlgorithm, cset1Order "
        + "  , rcAbilityWeight, abilityWeight, precisionTargetNotMetWeight, precisionTargetMetWeight, precisionTarget, adaptiveCut, tooCloseSEs "
        + "  , terminationMinCount, terminationOverallInfo, terminationRCInfo, terminationTooClose, terminationFlagsAnd "
        + "FROM sim_segment where _fk_Session = ${fromSession}";
    SqlParametersMaps parms7 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey).put ("fromSession", fromSession);
    insertedCnt = executeStatement (connection, cmd7, parms7, false).getUpdateCount ();

    final String cmd8 = "INSERT INTO sim_segmentcontentlevel( "
        + "   _fk_Session, _efk_Segment, contentLevel, MinItems, MaxItems, AdaptiveCut, StartAbility, StartInfo, Scalar, IsStrictmax, bpweight"
        + "  ,abilityWeight, precisionTargetNotMetWeight, precisionTargetMetWeight, precisionTarget) "
        + "SELECT ${sessionKey}, _efk_Segment, contentLevel, MinItems, MaxItems, AdaptiveCut, StartAbility, StartInfo, Scalar, IsStrictmax, bpweight"
        + "  ,abilityWeight, precisionTargetNotMetWeight, precisionTargetMetWeight, precisionTarget "
        + "FROM sim_segmentcontentlevel where _fk_Session = ${fromSession}";
    SqlParametersMaps parms8 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey).put ("fromSession", fromSession);
    insertedCnt = executeStatement (connection, cmd8, parms8, false).getUpdateCount ();

    final String cmd9 = "INSERT INTO sim_segmentitem("
        + "  _fk_Session, _efk_Segment, _efk_Item, isActive, isRequired, isFieldTest, strand, groupID) "
        + "SELECT ${sessionKey}, _efk_Segment, _efk_Item, isActive, isRequired, isFieldTest, strand, groupID "
        + "FROM sim_segmentitem where _fk_session = ${fromSession}";
    SqlParametersMaps parms9 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey).put ("fromSession", fromSession);
    insertedCnt = executeStatement (connection, cmd9, parms9, false).getUpdateCount ();

    final String cmd10 = "INSERT INTO sim_itemgroup(_fk_Session, _efk_Segment, groupID, maxItems) "
        + "SELECT ${sessionKey}, _efk_Segment, groupID, maxItems "
        + "FROM sim_itemgroup where _fk_Session = ${fromSession}";
    SqlParametersMaps parms10 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey).put ("fromSession", fromSession);
    insertedCnt = executeStatement (connection, cmd10, parms10, false).getUpdateCount ();

    final String cmd11 = "select 'success' as status, ${sessionKey} as sessionKey, ${sessionID} as sessionID, ${sessionName} as `Name`, "
        + " ${status} as sessionStatus, "
        + " bigtoint((select count(*) from sessiontests where _fk_Session = ${sessionKey})) as tests";
    SqlParametersMaps parms11 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey).put ("sessionId", sessionIdRef.get ()).
        put ("sessionName", sessionName).put ("status", status);
    SingleDataResultSet rs11 = executeStatement (connection, cmd11, parms11, false).getResultSets ().next ();

    _commonDll._LogDBLatency_SP (connection, "SIM_CopySession2", starttime, proctorKey, true, null, null, sessionKey, clientname, null);
    return rs11;
  }

  public SingleDataResultSet SIM_GetSessionTests2_SP (SQLConnection connection, UUID sessionKey) throws ReturnStatusException {

    final String cmd1 = "select S._efk_AdminSubject as testkey, S._efk_TestID as testID, iterations, opportunities, meanProficiency, "
        + " sdProficiency, strandCorrelation, S.handScoreItemTypes, "
        + " bigtoint((select count(*) from testopportunity O "
        + "         where O._fk_Session = ${sessionKey} and O._efk_AdminSubject = S._efk_AdminSubject)) as simulations"
        + " from sessiontests S where s._fk_Session = ${sessionKey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();
    return rs1;
  }

  public SingleDataResultSet SIM_GetSessionTests_SP (SQLConnection connection, UUID sessionKey) throws ReturnStatusException {

    final String cmd1 = "select S._efk_AdminSubject as testkey, S._efk_TestID as testID, iterations, opportunities, meanProficiency, "
        + " sdProficiency, strandCorrelation,  "
        + " bigtoint((select count(*) from testopportunity O "
        + "         where O._fk_Session = ${sessionKey} and O._efk_AdminSubject = S._efk_AdminSubject and dateCompleted is not null)) as simulations"
        + " from sessiontests S where s._fk_Session = ${sessionKey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("sessionKey", sessionKey);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();
    return rs1;
  }

  public void SIM_SetSessionAbort_SP (SQLConnection connection, UUID session, Boolean abort) throws ReturnStatusException {
    final String cmd1 = "update session set sim_Abort = ${abort} where _Key = ${session}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("abort", abort);
    executeStatement (connection, cmd1, parms1, false);
  }

  public void SIM_SetSessionDescription_SP (SQLConnection connection, UUID session, String description) throws ReturnStatusException {
    final String cmd1 = "update session set description = ${description} where _Key = ${session}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("description", description);
    executeStatement (connection, cmd1, parms1, false);
  }

  public SingleDataResultSet SIM_AddSessionTest2_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException {
    Integer iterations = 10;
    Integer opportunities = 3;
    Float meanProficiency = (float) 0.0;
    Float sdProficiency = (float) 1.0;
    Float strandCorrelation = (float) 1.0;
    String handScoreItemTypes = null;
    return SIM_AddSessionTest2_SP (connection, session, testkey, iterations, opportunities, meanProficiency,
        sdProficiency, strandCorrelation, handScoreItemTypes);
  }

  public SingleDataResultSet SIM_AddSessionTest2_SP (SQLConnection connection, UUID session, String testkey,
      Integer iterations, Integer opportunities, Float meanProficiency,
      Float sdProficiency, Float strandCorrelation, String handScoreItemTypes) throws ReturnStatusException {

    // TODO decide if/how we want to set different value for this
    // set @numthreads = case when cast (serverproperty('servername') as
    // varchar(100)) like 'WSTES%' then 4 else 20 end;
    Integer numthreads = 4;
    String sessionClient = null;
    String sessionLang = null;
    String environment = null;

    final String cmd1 = "select clientname as sessionClient, sim_language as sessionLang, environment "
        + " from session where _key = ${session}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();
    DbResultRecord record1 = (rs1.getCount () > 0 ? rs1.getRecords ().next () : null);
    if (record1 != null) {
      sessionClient = record1.<String> get ("sessionClient");
      sessionLang = record1.<String> get ("sessionLang");
      environment = record1.<String> get ("environment");
    }
    if (environment == null || "SIMULATION".equals (environment) == false) {
      return _commonDll._ReturnError_SP (connection, sessionClient, "SIM_AddSessionTest2", "Current environment does not support simulation activities");
    }

    String testId = null;
    Boolean segmented = null;
    final String cmd2 = "select S.TestID,  S.isSegmented as segmented "
        + " from ${ItemBankDB}.tblsetofadminsubjects S, ${ConfigDB}.client_testproperties P "
        + " where S._Key = ${testKey} and P.clientname = ${sessionClient} and P.testID = S.TestID";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("testKey", testkey).put ("sessionClient", sessionClient);
    SingleDataResultSet rs2 = executeStatement (connection, fixDataBaseNames (cmd2), parms2, false).getResultSets ().next ();
    DbResultRecord record2 = (rs2.getCount () > 0 ? rs2.getRecords ().next () : null);
    if (record2 != null) {
      testId = record2.<String> get ("testId");
      segmented = record2.<Boolean> get ("segmented");
      environment = record1.<String> get ("environment");
    }

    if (testId == null) {
      return _commonDll._ReturnError_SP (connection, sessionClient, "SIM_AddSessionTest2", "No such test");
    }

    final String cmd3 = "select _fk_session from sessiontests where _fk_Session = ${session} and _efk_AdminSubject = ${testkey} limit 1";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);

    if (!exists (executeStatement (connection, cmd3, parms3, false))) {

      final String cmd4 = "insert into sessiontests (_fk_SEssion, _efk_AdminSUbject, _efk_TestID, iterations, "
          + " opportunities, meanProficiency, sdProficiency, strandCorrelation, sim_threads,"
          + " handscoreitemtypes) "
          + " values (${session}, ${testkey}, ${testID}, ${iterations},"
          + "  ${opportunities}, ${meanProficiency}, ${sdProficiency}, ${strandCorrelation}, ${numthreads},"
          + "  ${handscoreitemtypes})";
      SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey).
          put ("testID", testId).put ("iterations", iterations).put ("opportunities", opportunities).
          put ("meanProficiency", meanProficiency).put ("sdProficiency", sdProficiency).
          put ("strandCorrelation", strandCorrelation).put ("numthreads", numthreads).
          put ("handScoreItemTypes", handScoreItemTypes);
      executeStatement (connection, cmd4, parms4, false);

      int inserted = 0;
      if (DbComparator.isEqual (segmented, true)) {
        final String cmd5 = "INSERT INTO sim_segment(_fk_session, _efk_AdminSubject, _efk_Segment, segmentID,"
            + "   selectionAlgorithm, segmentPosition, StartAbility, StartInfo, MinItems, MaxItems, "
            + "   FTStartPos, FTEndPos, FTMinItems, FTMaxItems, formSelection, blueprintWeight, "
            + "   cset1size, cset1Order, cset2Random, cset2InitialRandom,  loadConfig, updateConfig, itemWeight, abilityOffset, "
            + "   rcAbilityWeight, abilityWeight, precisionTargetNotMetWeight, precisionTargetMetWeight, precisionTarget, adaptiveCut, "
            + "   tooCloseSEs, terminationMinCount, terminationOverallInfo, terminationRCInfo, terminationTooClose, terminationFlagsAnd)"
            + " select ${session}, ${testKey},  _Key, TestID,"
            + "   selectionAlgorithm, TestPosition, StartAbility, StartInfo, MinItems, MaxItems, "
            + "   FTStartPos, FTEndPos, FTMinItems, FTMaxItems, formSelection, blueprintWeight, "
            + "   cset1size, cset1Order, cset2Random, cset2InitialRandom,  loadConfig, updateConfig, itemWeight, abilityOffset, "
            + "   rcAbilityWeight, abilityWeight, precisionTargetNotMetWeight, precisionTargetMetWeight, precisionTarget, adaptiveCut, "
            + "   tooCloseSEs, terminationMinCount, terminationOverallInfo, terminationRCInfo, terminationTooClose, terminationFlagsAnd "
            + " from ${ItemBankDB}.tblsetofadminsubjects where VirtualTest = ${testKey}";

        SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);
        inserted = executeStatement (connection, fixDataBaseNames (cmd5), parms5, false).getUpdateCount ();

      } else {
        final String cmd6 = "INSERT INTO sim_segment(_fk_session, _efk_AdminSubject, _efk_Segment, segmentID,"
            + "   selectionAlgorithm, segmentPosition, StartAbility, StartInfo, MinItems, MaxItems, "
            + "   FTStartPos, FTEndPos, FTMinItems, FTMaxItems, formSelection, blueprintWeight, "
            + "   cset1size, cset1Order, cset2Random, cset2InitialRandom,  loadConfig, updateConfig, itemWeight, abilityOffset,"
            + "   rcAbilityWeight, abilityWeight, precisionTargetNotMetWeight, precisionTargetMetWeight, precisionTarget, adaptiveCut, "
            + "   tooCloseSEs, terminationMinCount, terminationOverallInfo, terminationRCInfo, terminationTooClose, terminationFlagsAnd)"
            + " select ${session}, ${testKey},  _Key, TestID,"
            + "   selectionAlgorithm, 1, StartAbility, StartInfo, MinItems, MaxItems, "
            + "   FTStartPos, FTEndPos, FTMinItems, FTMaxItems, formSelection, blueprintWeight, "
            + "   cset1size, cset1Order, cset2Random, cset2InitialRandom,  loadConfig, updateConfig, itemWeight, abilityOffset, "
            + "   rcAbilityWeight, abilityWeight, precisionTargetNotMetWeight, precisionTargetMetWeight, precisionTarget, adaptiveCut, "
            + "   tooCloseSEs, terminationMinCount, terminationOverallInfo, terminationRCInfo, terminationTooClose, terminationFlagsAnd "
            + " from ${ItemBankDB}.tblsetofadminsubjects where _key = ${testKey}";

        SqlParametersMaps parms6 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);
        inserted = executeStatement (connection, fixDataBaseNames (cmd6), parms6, false).getUpdateCount ();

      }

      final String cmd7 = "INSERT INTO sim_segmentcontentlevel(_fk_Session, _efk_Segment, contentLevel, MinItems, MaxItems,"
          + "     AdaptiveCut, StartAbility, StartInfo, Scalar, IsStrictmax, bpweight,"
          + "     abilityWeight, precisionTargetNotMetWeight, precisionTargetMetWeight, precisionTarget) "
          + " select ${session}, _efk_Segment, _fk_Strand, A.MinItems, A.MaxItems,"
          + "     A.AdaptiveCut, A.StartAbility, A.StartInfo, Scalar, IsStrictmax, bpweight, "
          + "     A.abilityWeight, A.precisionTargetNotMetWeight, A.precisionTargetMetWeight, A.precisionTarget "
          + " from sim_segment S, ${ItemBankDB}.tbladminstrand A "
          + "   where S._fk_Session = ${session} and S._efk_AdminSubject = ${testKey} and A._fk_AdminSubject = S._efk_SEgment";
      SqlParametersMaps parms7 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);
      executeStatement (connection, fixDataBaseNames (cmd7), parms7, false);

      final String cmd8 = "INSERT INTO sim_segmentcontentlevel(_fk_Session, _efk_Segment, contentLevel, MinItems, MaxItems, "
          + "    AdaptiveCut, StartAbility, StartInfo, Scalar, isStrictMax, bpWeight, "
          + "    abilityWeight, precisionTargetNotMetWeight, precisionTargetMetWeight, precisionTarget)"
          + " select ${session}, _efk_Segment, GroupID, A.MinItems, A.MaxItems, "
          + "    null as AdaptiveCut, A.StartAbility, A.StartInfo, null as Scalar, isStrictMax, weight, "
          + "    A.abilityWeight, A.precisionTargetNotMetWeight, A.precisionTargetMetWeight, A.precisionTarget "
          + " from sim_segment S, ${ItemBankDB}.affinitygroup A "
          + "   where S._fk_Session = ${session} and S._efk_AdminSubject = ${testKey} and A._fk_AdminSubject = S._efk_SEgment";
      SqlParametersMaps parms8 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);
      inserted = executeStatement (connection, fixDataBaseNames (cmd8), parms8, false).getUpdateCount ();

      final String cmd9 = "insert into sim_segmentitem (_fk_Session, _efk_Segment, _efk_Item, isActive, isRequired, isFieldTest, strand, groupID) "
          + " select ${session}, _efk_Segment, I._fk_Item, "
          + "         case when I.isActive = P.isActive then I.isActive else 0 end, isRequired, isFieldTest, strandname, groupID "
          + " from sim_segment S, ${ItemBankDB}.tblsetofadminitems I, ${ItemBankDB}.tblitemprops P "
          + "   where S._fk_Session = ${session} and S._efk_AdminSubject = ${testkey} and I._fk_AdminSubject = S._efk_SEgment "
          + "      and P._fk_AdminSubject = I._fk_AdminSubject and P._fk_Item = I._fk_Item"
          + "      and P.propname = 'Language' and P.propvalue = ${sessionLang}";
      SqlParametersMaps parms9 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey).put ("sessionLang", sessionLang);
      inserted = executeStatement (connection, fixDataBaseNames (cmd9), parms9, false).getUpdateCount ();

      final String cmd10 = "insert into sim_itemgroup (_fk_Session, _efk_Segment, groupID, maxItems) "
          + "select ${session}, S._efk_Segment, A.groupID, A.maxItems "
          + " from sim_segment S, ${ItemBankDB}.tbladminstimulus A "
          + "    where S._fk_Session = ${session} and S._efk_AdminSubject = ${testKey} and A._fk_AdminSubject = S._efk_SEgment";
      SqlParametersMaps parms10 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);
      inserted = executeStatement (connection, fixDataBaseNames (cmd10), parms10, false).getUpdateCount ();

      final String cmdItemSelectionParam = " insert into sim_itemselectionparameter (_fk_session, _fk_adminsubject, bpelementid, name, value, label) "
          + "select ${session}, _fk_adminsubject, bpelementid, name, value, label "
          + " from ${ItemBankDB}.tblitemselectionparm "
          + " where _fk_AdminSubject=${testKey} ";
      SqlParametersMaps parmsItemSelectionParam = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);
      inserted = executeStatement (connection, fixDataBaseNames (cmdItemSelectionParam), parmsItemSelectionParam, false).getUpdateCount ();
      SIM_CreateItemSelectionParameters (connection, session);

      return _commonDll.ReturnStatusReason ("success", null);

    } else if (!testOpportunityExists (connection, session, testkey)) {

      final String cmd12 = "update sessiontests set iterations = ${iterations}, opportunities = ${opportunities}, meanProficiency = ${meanProficiency},"
          + "   sdProficiency = ${sdProficiency}, strandCorrelation = ${strandCorrelation},handScoreItemTypes = ${handScoreItemTypes} "
          + " where _fk_Session = ${session} and _efk_AdminSubject = ${testkey}";
      SqlParametersMaps parms12 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey).
          put ("iterations", iterations).put ("opportunities", opportunities).
          put ("meanProficiency", meanProficiency).put ("sdProficiency", sdProficiency).
          put ("strandCorrelation", strandCorrelation).put ("handScoreItemTypes", handScoreItemTypes);
      executeStatement (connection, cmd12, parms12, false);

      return _commonDll.ReturnStatusReason ("success", null);
    }
    return _commonDll._ReturnError_SP (connection, sessionClient, "SIM_AddSessionTest2", "Opportunity data already exist on this test within this session");
  }

  public SingleDataResultSet SIM_AlterSegmentItemgroup_SP (SQLConnection connection, UUID session, String testkey, String segmentKey,
      String groupId, Integer maxItems) throws ReturnStatusException {

    String client = getClientnameFromSession (connection, session);

    if (!testOpportunityExists (connection, session, testkey)) {
      final String cmd3 = "UPDATE sim_itemgroup SET  maxItems = ${maxItems} "
          + "  WHERE _fk_Session = ${session} and _efk_Segment = ${segmentKey} and groupID = ${groupID}";
      SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("session", session).put ("maxItems", maxItems).
          put ("segmentKey", segmentKey).put ("groupId", groupId);
      executeStatement (connection, cmd3, parms3, false);

      return _commonDll.ReturnStatusReason ("success", null);
    }

    return _commonDll._ReturnError_SP (connection, client, "SIM_AlterSegmentItemgroup",
        "Opportunity data already exist on this test within this session");
  }

  public SingleDataResultSet SIM_AlterSegmentStrand2_SP (SQLConnection connection, UUID session,
      String testkey, String segmentKey,
      String strand, Integer minItems, Integer maxItems, Float bpweight,
      Boolean isStrictMax, Float startAbility, Float startInfo) throws ReturnStatusException {

    return SIM_AlterSegmentStrand2_SP (connection, session, testkey, segmentKey,
        strand, minItems, maxItems, bpweight, isStrictMax, startAbility, startInfo,
        null, null, null, null, null, null);
  }

  public SingleDataResultSet SIM_AlterSegmentStrand2_SP (SQLConnection connection, UUID session, String testkey, String segmentKey,
      String strand, Integer minItems, Integer maxItems, Float bpweight, Boolean isStrictMax, Float startAbility, Float startInfo,
      Float adaptiveCut, Float scalar,
      Float abilityWeight,
      Float precisionTargetNotMetWeight,
      Float precisionTargetMetWeight,
      Float precisionTarget) throws ReturnStatusException {

    String client = getClientnameFromSession (connection, session);

    if (!testOpportunityExists (connection, session, testkey)) {
      final String cmd3 = "UPDATE sim_segmentcontentlevel "
          + " SET  MinItems=${minItems}, MaxItems=${maxItems}, bpWeight=${bpWeight}, isStrictMax  = ${isStrictMax}, "
          + " startAbility = ${startAbility}, startInfo = ${startInfo}, adaptiveCut = ${adaptiveCut}, scalar = ${scalar}, "
          + " abilityWeight = ${abilityWeight}, precisionTargetNotMetWeight = ${precisionTargetNotMetWeight}, "
          + " precisionTargetMetWeight = ${precisionTargetMetWeight}, precisionTarget = ${precisionTarget} "
          + "   WHERE _fk_Session = ${session} and _efk_Segment = ${segmentKey} and contentLevel = ${strand} "
          + "   and startAbility is not null ";
      SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("minItems", minItems).put ("maxItems", maxItems).put ("bpweight", bpweight).
          put ("isStrictMax", isStrictMax).put ("startAbility", startAbility).put ("startInfo", startInfo).put ("adaptiveCut", adaptiveCut).
          put ("scalar", scalar).put ("session", session).put ("segmentKey", segmentKey).put ("strand", strand).
          put ("abilityWeight", abilityWeight).put ("precisionTargetNotMetWeight", precisionTargetNotMetWeight).
          put ("precisionTargetMetWeight", precisionTargetMetWeight).put ("precisionTarget", precisionTarget);

      executeStatement (connection, cmd3, parms3, false);

      return _commonDll.ReturnStatusReason ("success", null);
    }
    return _commonDll._ReturnError_SP (connection, client, "SIM_AlterSegmentStrand2",
        "Opportunity data already exist on this test within this session");
  }

  public SingleDataResultSet SIM_AlterSegmentContentLevel_SP (SQLConnection connection, UUID session, String testkey, String segmentKey,
      String contentLevel, Integer minItems, Integer maxItems, Float bpweight, Boolean isStrictMax) throws ReturnStatusException {

    String client = getClientnameFromSession (connection, session);

    if (!testOpportunityExists (connection, session, testkey)) {
      final String cmd3 = "UPDATE sim_segmentcontentlevel "
          + " SET  MinItems=${minItems}, MaxItems=${maxItems}, bpWeight=${bpWeight}, isStrictMax  = ${isStrictMax} "
          + "   WHERE _fk_Session = ${session} and _efk_Segment = ${segmentKey} and contentLevel = ${contentLevel} ";

      SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("minItems", minItems).put ("maxItems", maxItems).put ("bpweight", bpweight).
          put ("isStrictMax", isStrictMax).put ("session", session).put ("segmentKey", segmentKey).put ("contentLevel", contentLevel);
      executeStatement (connection, cmd3, parms3, false);

      return _commonDll.ReturnStatusReason ("success", null);
    }
    return _commonDll._ReturnError_SP (connection, client, "SIM_AlterSegmentContentLevel", "Opportunity data already exist on this test within this session");
  }

  public MultiDataResultSet SIM_GetTestBlueprint2_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException {

    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();

    final String cmd1 = " select * "
        + ", bigtoint((select count(*) from sim_segmentitem I where _fk_session = ${session} and I._efk_Segment = S._efk_Segment and I.isActive = 1 and I.isFieldTest = 0))"
        + " as `OP Active ItemCount` "
        + ", bigtoint((select count(distinct G.groupID) "
        + "    from sim_itemgroup G, sim_segmentitem I "
        + "    where I._fk_session = ${session} and I._efk_Segment = S._efk_Segment and I.isActive = 1 and I.isFieldTest = 0 "
        + "        and G._fk_Session = ${session} and G._efk_Segment = S._efk_Segment and G.groupID = I.groupID)) "
        + " as `OP Active GroupCount` "
        + " from sim_segment S where _fk_Session = ${session} and _efk_AdminSubject = ${testkey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("testKey", testkey);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();

    resultsets.add (rs1);

    final String cmd2 = "select L._efk_Segment, contentLevel as strand, L.MinItems, L.MaxItems, IsStrictMax, bpweight "
        + "   , L.AdaptiveCut, L.StartAbility, L.StartInfo, Scalar "
        + "   , bigtoint((select count(*) from ${ItembankDB}.aa_itemcl C, sim_segmentitem I "
        + "       where C._fk_AdminSubject = S._efk_Segment and C.ContentLevel = L.ContentLevel and I._efk_Segment = S._efk_Segment "
        + "       and I._fk_session = ${session} and I._efk_Item = C._fk_Item and I.isActive = 1 and I.isFieldTest = 0)) "
        + "     as `OP Active ItemCount` "
        + "   , L.abilityWeight, L.precisionTargetNotMetWeight, L.precisionTargetMetWeight, L.precisionTarget "
        + " from sim_segmentcontentlevel L, sim_segment S "
        + "   where S._fk_Session = ${session} and S._efk_AdminSubject = ${testkey} "
        + "     and L._fk_Session = ${session} and L._efk_Segment = S._efk_Segment and L.startAbility is not null"
        + " order by L._efk_Segment, ContentLevel";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("session", session).put ("testKey", testkey);
    SingleDataResultSet rs2 = executeStatement (connection, fixDataBaseNames (cmd2), parms2, true).getResultSets ().next ();

    // TODO Elena: this loop is a candidate for optimization
    // we would need to move udfGetFeatureClass function to MySql side
    rs2.addColumn ("featureclass", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    Iterator<DbResultRecord> iter = rs2.getRecords ();
    while (iter.hasNext ()) {
      DbResultRecord rcd2 = iter.next ();
      String contentLevel = rcd2.<String> get ("strand");
      String _efk_segment = rcd2.<String> get ("_efk_segment");
      String featureClass = udfGetFeatureClass_FN (connection, contentLevel, _efk_segment);
      rcd2.addColumnValue ("featureclass", featureClass);
    }

    resultsets.add (rs2);

    final String cmd3 = "select L._efk_Segment, contentLevel, L.MinItems, L.MaxItems, IsStrictMax, bpweight "
        + "    , bigtoint((select count(*) from ${ItembankDB}.aa_itemcl C, sim_segmentitem I "
        + "       where C._fk_AdminSubject = S._efk_Segment and C.ContentLevel = L.ContentLevel "
        + "          and I._efk_Segment = S._efk_Segment "
        + "          and I._fk_session = ${session} and I._efk_Item = C._fk_Item and I.isActive = 1 and I.isFieldTest = 0)) "
        + "      as `OP Active ItemCount`"
        + " from sim_segmentcontentlevel L, sim_segment S "
        + "   where S._fk_Session = ${session} and S._efk_AdminSubject = ${testkey} "
        + "     and L._fk_Session = ${session} and L._efk_Segment = S._efk_Segment and L.startability is null "
        + " order by L._efk_Segment, ContentLevel";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("session", session).put ("testKey", testkey);
    SingleDataResultSet rs3 = executeStatement (connection, fixDataBaseNames (cmd3), parms3, true).getResultSets ().next ();

    // TODO Elena: this loop is a candidate for optimization
    // we would need to move udfGetFeatureClass function to MySql side
    rs3.addColumn ("featureclass", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    Iterator<DbResultRecord> iter3 = rs3.getRecords ();
    while (iter3.hasNext ()) {
      DbResultRecord rcd3 = iter3.next ();
      String contentLevel = rcd3.<String> get ("contentLevel");
      String _efk_segment = rcd3.<String> get ("_efk_segment");
      String featureClass = udfGetFeatureClass_FN (connection, contentLevel, _efk_segment);
      rcd3.addColumnValue ("featureclass", featureClass);
    }
    resultsets.add (rs3);

    return new MultiDataResultSet (resultsets);
  }

  protected String udfGetFeatureClass_FN (SQLConnection connection, String token, String adminSubject) throws ReturnStatusException {

    String name = null;
    String parent = null;
    Integer treeLevel = null;
    String featureClass = null;

    final String cmd1 = "SELECT Name,  _fk_Parent as parent,  TreeLevel FROM ${ItembankDB}.tblstrand WHERE _key = ${Token}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("token", token);

    SingleDataResultSet rs1 = executeStatement (connection, fixDataBaseNames (cmd1), parms1, false).getResultSets ().next ();
    DbResultRecord rcd1 = rs1.getCount () > 0 ? rs1.getRecords ().next () : null;
    if (rcd1 != null) {
      name = rcd1.<String> get ("name");
      parent = rcd1.<String> get ("parent");
      treeLevel = rcd1.<Integer> get ("treelevel");
    }

    if (name != null) {
      if (parent == null || DbComparator.isEqual (treeLevel, 1))
        featureClass = "Strand";
      else
        featureClass = "ContentLevel";

    } else {
      final String cmd2 = "SELECT _fk_AdminSubject FROM ${ItembankDB}.affinitygroup "
          + " WHERE _fk_AdminSubject = ${AdminSubject} AND GroupID = ${Token} limit 1";
      SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("token", token).put ("adminsubject", adminSubject);
      if (exists (executeStatement (connection, fixDataBaseNames (cmd2), parms2, false))) {
        featureClass = "AffinityGroup";
      }
    }
    return featureClass;
  }

  public MultiDataResultSet SIM_GetTestItems_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException {

    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();
    final String cmd1 = "select S._efk_Segment as segmentKey, I._efk_Item as itemKey, I.isActive, "
        + "      I.isRequired, I.isFieldTest, I.strand, I.groupID "
        + " from sim_segment S, sim_segmentitem I "
        + "    where S._fk_session = ${session} and I._fk_Session = ${session} and S._efk_AdminSubject = ${testkey} "
        + "       and I._efk_Segment = S._efk_Segment "
        + " order by S._efk_Segment, I._efk_Item";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("testKey", testkey);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();

    resultsets.add (rs1);

    final String cmd2 = "select S._efk_Segment as segmentKey, I.groupID, I.maxItems, bigtoint(count(*)) as activeItems "
        + " from sim_segment S, sim_itemgroup I, sim_segmentitem M "
        + "    where S._fk_session = ${session} and I._fk_Session = ${session} and S._efk_AdminSubject = ${testkey} "
        + "      and I._efk_Segment = S._efk_Segment and M._fk_Session = ${session} and M._efk_Segment = S._efk_Segment "
        + "      and M.isActive = 1 and M.groupID = I.groupID "
        + " group by S._efk_Segment, I.groupID, I.maxItems "
        + " order by S._efk_Segment, I.groupID";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("session", session).put ("testKey", testkey);
    SingleDataResultSet rs2 = executeStatement (connection, cmd2, parms2, false).getResultSets ().next ();

    resultsets.add (rs2);

    return new MultiDataResultSet (resultsets);
  }

  public MultiDataResultSet SIM_ReportBPSatisfaction_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException {
    return SIM_ReportBPSatisfaction_SP (connection, session, testkey, false);
  }

  public MultiDataResultSet SIM_ReportBPSatisfaction_SP (SQLConnection connection, UUID session, String testkey,
      Boolean reportBlueprint) throws ReturnStatusException {
    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();

    DataBaseTable oppsTbl = getDataBaseTable ("opps").addColumn ("oppKey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER).
        addColumn ("CL", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200).addColumn ("cnt", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("oppnum", SQL_TYPE_To_JAVA_TYPE.INT);

    connection.createTemporaryTable (oppsTbl);

    // @blueprint table (contentLevel varchar(200) primary key not null,
    // minItems int, maxItems int, isStrict bit, numCL int, bpweight float);

    DataBaseTable blueprintTbl = getDataBaseTable ("blueprint").addColumn ("contentLevel", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200).
        addColumn ("minItems", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("maxItems", SQL_TYPE_To_JAVA_TYPE.INT);

    connection.createTemporaryTable (blueprintTbl);

    final String cmd1 = "insert into ${blueprintTbl} (contentLevel, minItems, maxItems) "
        + " select contentLevel, sum(C.minItems) as minItems, sum(C.maxItems)as maxItems "
        + " from sim_segment S, sim_segmentcontentlevel C "
        + " where S._Fk_Session = ${session} and S._efk_AdminSubject = ${testkey} "
        + "  and C._fk_Session = ${session} and C._efk_Segment = S._efk_Segment "
        + " group by ContentLevel ";

    Map<String, String> unquotedParms1 = new HashMap<String, String> ();
    unquotedParms1.put ("blueprintTbl", blueprintTbl.getTableName ());
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("testKey", testkey);
    Integer insertedCnt = executeStatement (connection, fixDataBaseNames (cmd1, unquotedParms1), parms1, false).getUpdateCount ();

    if (reportBlueprint == true) {
      final String cmd2 = "select segmentID, segmentPosition, minItems, maxItems, FTMinItems, FTMaxITems "
          + " from sim_segment  where _fk_Session = ${session} and _efk_AdminSubject = ${testkey}";
      SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("session", session).put ("testKey", testkey);
      SingleDataResultSet rs2 = executeStatement (connection, cmd2, parms2, true).getResultSets ().next ();
      resultsets.add (rs2);

      final String cmd3 = "select contentLevel, minItems, maxItems from ${blueprintTbl} order by contentLevel";
      SingleDataResultSet rs3 = executeStatement (connection, fixDataBaseNames (cmd3, unquotedParms1), null, false).getResultSets ().next ();
      resultsets.add (rs3);
    }
    final String cmd4 = "select C._fk_TestOpportunity from testopportunitycontentcounts C, testopportunity O "
        + " where O._fk_Session = ${session} and C._fk_TestOpportunity = O._Key and C._efk_AdminSUbject = ${testkey}";
    SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("session", session).put ("testKey", testkey);
    if (!exists (executeStatement (connection, cmd4, parms4, false))) {
      __PatchContentCounts_SP (connection, session, testkey);
    }

    final String cmd5 = "insert into ${oppsTbl} (oppkey, oppnum, CL, cnt) "
        + " select O._Key, O.opportunity, BP.ContentLevel, itemcount - BP.maxITems "
        + " from testopportunity O, testopportunitycontentcounts C, ${blueprintTbl} BP "
        + "   where O._fk_Session = ${session} and O._efk_AdminSubject = ${testkey} "
        + "     and C._fk_TestOpportunity = O._key "
        + "     and BP.contentLevel = C.COntentLEvel and itemcount > BP.maxitems";
    SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("session", session).put ("testKey", testkey);
    Map<String, String> unquotedParms5 = new HashMap<String, String> ();
    unquotedParms5.put ("blueprintTbl", blueprintTbl.getTableName ());
    unquotedParms5.put ("oppsTbl", oppsTbl.getTableName ());

    insertedCnt = executeStatement (connection, fixDataBaseNames (cmd5, unquotedParms5), parms5, true).getUpdateCount ();

    final String cmd6 = "insert into ${oppsTbl} (oppkey, oppnum, CL, cnt) "
        + "   select O._Key, O.opportunity, BP.ContentLevel, 0 - BP.maxITems "
        + " from testopportunity O, ${blueprintTbl} BP "
        + "   where O._fk_Session = ${session} and O._efk_AdminSubject = ${testkey} and 0 > BP.maxitems "
        + "       and not exists "
        + "       (select * from testopportunitycontentcounts C "
        + "        where C._fk_TestOpportunity = O._Key AND C.ContentLevel = BP.contentLevel "
        + "           AND C._efk_AdminSubject = ${testKey})";
    SqlParametersMaps parms6 = (new SqlParametersMaps ()).put ("session", session).put ("testKey", testkey);
    Map<String, String> unquotedParms6 = unquotedParms5;
    insertedCnt = executeStatement (connection, fixDataBaseNames (cmd6, unquotedParms6), parms6, true).getUpdateCount ();

    final String cmd7 = "insert into ${oppsTbl} (oppkey, oppnum, CL, cnt) "
        + " select O._Key, O.opportunity, BP.ContentLevel, itemcount - BP.minITems "
        + "   from testopportunity O , testopportunitycontentcounts C , ${blueprintTbl} BP "
        + "     where O._fk_Session = ${session} and O._efk_AdminSubject = ${testkey} "
        + "      and C._fk_TestOpportunity = O._Key "
        + "      and BP.contentLevel = C.COntentLEvel and itemcount < BP.minitems";
    SqlParametersMaps parms7 = (new SqlParametersMaps ()).put ("session", session).put ("testKey", testkey);
    Map<String, String> unquotedParms7 = unquotedParms5;
    insertedCnt = executeStatement (connection, fixDataBaseNames (cmd7, unquotedParms7), parms7, true).getUpdateCount ();

    final String cmd8 = "insert into ${oppsTbl} (oppkey, oppnum, CL, cnt) "
        + "   select O._Key, O.opportunity, BP.ContentLevel, 0 - BP.minITems "
        + " from testopportunity O, ${blueprintTbl} BP "
        + "   where O._fk_Session = ${session} and O._efk_AdminSubject = ${testkey} and 0 < BP.minitems "
        + "       and not exists "
        + "       (select * from testopportunitycontentcounts C "
        + "        where C._fk_TestOpportunity = O._Key AND C.ContentLevel = BP.contentLevel "
        + "           AND C._efk_AdminSubject = ${testKey})";
    SqlParametersMaps parms8 = (new SqlParametersMaps ()).put ("session", session).put ("testKey", testkey);
    Map<String, String> unquotedParms8 = unquotedParms5;
    insertedCnt = executeStatement (connection, fixDataBaseNames (cmd8, unquotedParms8), parms8, true).getUpdateCount ();

    final String cmd9 = "select oppnum as Opportunity, CL as contentLevel, cnt as `Items Under/Over`,"
        + "   bigtoint(count(*)) as numopps "
        + " from ${oppsTbl} group by oppnum, CL, cnt "
        + "                 order by CL, oppnum, cnt";
    Map<String, String> unquotedParms9 = new HashMap<> ();
    unquotedParms9.put ("oppsTbl", oppsTbl.getTableName ());
    SingleDataResultSet rs9 = executeStatement (connection, fixDataBaseNames (cmd9, unquotedParms9), null, false).getResultSets ().next ();

    resultsets.add (rs9);

    connection.dropTemporaryTable (blueprintTbl);
    connection.dropTemporaryTable (oppsTbl);

    return new MultiDataResultSet (resultsets);
  }

  public void __PatchContentCounts_SP (SQLConnection connection, UUID sessionKey, String testKey) throws ReturnStatusException {
    final String cmd1 = "select _KEy, _fk_Session from testopportunity "
        + " where datecompleted is not null and datedeleted is null "
        + "    and (${sessionkey} is null or _fk_Session = ${sessionkey}) "
        + "    and (${testkey} is null or _efk_AdminSUbject = ${testkey})";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("sessionkey", sessionKey).put ("testKey", testKey);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();

    UUID oppKey = null;
    Iterator<DbResultRecord> records = rs1.getRecords ();
    while (records.hasNext ()) {
      DbResultRecord record = records.next ();
      oppKey = record.<UUID> get ("_key");
      _commonDll._RecordBPSatisfaction_SP (connection, oppKey);
    }
  }

  public void __PatchSegmentCounts_SP (SQLConnection connection, UUID sessionKey, String testKey) throws ReturnStatusException {

    final String cmd1 = "select _KEy, _fk_Session from testopportunity "
        + " where datecompleted is not null and datedeleted is null "
        + "    and (${sessionkey} is null or _fk_Session = ${sessionkey}) "
        + "    and (${testkey} is null or _efk_AdminSUbject = ${testkey})";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("sessionkey", sessionKey).put ("testKey", testKey);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();

    UUID oppKey = null;
    Iterator<DbResultRecord> records = rs1.getRecords ();
    while (records.hasNext ()) {
      DbResultRecord record = records.next ();
      oppKey = record.<UUID> get ("_key");
      _RecordSegmentSatisfaction_SP (connection, oppKey);
    }
  }

  public SingleDataResultSet SIM_AlterSegmentItem_SP (SQLConnection connection, UUID session, String testkey, String segmentKey, String itemKey,
      Boolean isActive, Boolean isRequired) throws ReturnStatusException {

    String client = getClientnameFromSession (connection, session);

    if (!testOpportunityExists (connection, session, testkey)) {
      final String cmd1 = "UPDATE sim_segmentitem "
          + " SET  isActive = ${isActive}, isRequired = ${isRequired} "
          + "   WHERE _fk_Session = ${session} and _efk_Segment = ${segmentKey} and _efk_Item = ${itemKey}";
      SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("isrequired", isRequired).
          put ("segmentkey", segmentKey).put ("itemkey", itemKey).put ("isActive", isActive);
      Integer updatedCnt = executeStatement (connection, cmd1, parms1, false).getUpdateCount ();

      return _commonDll.ReturnStatusReason ("success", null);
    } else {
      return _commonDll._ReturnError_SP (connection, client, "SIM_AlterSegmentItem", "Opportunity data already exist on this test within this session");
    }
  }

  // TODO Elena:this method can be optimized from top to bottom
  // to avoid using temporary table, if needed. (Extensive rewrite)
  public MultiDataResultSet SIM_ValidateBlueprints_SP (SQLConnection connection, UUID session) throws ReturnStatusException {

    DataBaseTable errorsTbl = getDataBaseTable ("errors").addColumn ("severity", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 20).
        addColumn ("test", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("err", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 4096);
    connection.createTemporaryTable (errorsTbl);

    DataBaseTable itemsTbl = getDataBaseTable ("items").addColumn ("test", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).
        addColumn ("obj", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("cnt", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("req", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("mn", SQL_TYPE_To_JAVA_TYPE.FLOAT).
        addColumn ("mx", SQL_TYPE_To_JAVA_TYPE.FLOAT);
    connection.createTemporaryTable (itemsTbl);

    final String cmd1 = "insert into ${itemsTbl} (test) "
        + " select _efk_Segment from sim_segment "
        + "  where _fk_Session = ${session} and (minItems > maxItems or FTMinItems > FTMaxItems)";
    insertIntoItemsTbl (connection, cmd1, itemsTbl, session);

    Integer insertedErr = null;
    if (existsInItemsTbl (connection, itemsTbl)) {
      String errCmd = "insert into ${errorsTbl} (severity, test, err) "
          + " select 'FATAL ERROR', test, 'Test Min > max' from ${itemsTbl}";
      insertedErr = insertIntoErrorsTbl (connection, errCmd, itemsTbl, errorsTbl);
    }

    deleteFromItemsTbl (connection, itemsTbl);

    final String cmd2 = "insert into ${itemsTbl} (test, obj) "
        + " select _efk_Segment, contentLevel from sim_segmentcontentlevel "
        + "   where _fk_Session = ${session} and minItems > maxItems";
    insertIntoItemsTbl (connection, cmd2, itemsTbl, session);

    if (existsInItemsTbl (connection, itemsTbl)) {
      String errCmd = "insert into ${errorsTbl} (severity, test, err) "
          + " select 'FATAL ERROR', test, concat(obj, ': Content level Min > max') from ${itemsTbl}";
      insertedErr = insertIntoErrorsTbl (connection, errCmd, itemsTbl, errorsTbl);
    }

    deleteFromItemsTbl (connection, itemsTbl);
    final String cmd3 = "insert into ${itemsTbl} (test) "
        + " select _efk_Segment from sim_segment "
        + "   where _fk_Session = ${session} and selectionalgorithm like 'adaptive%'"
        + "     and startability not between -10.0 and 10.0";
    insertIntoItemsTbl (connection, cmd3, itemsTbl, session);

    if (existsInItemsTbl (connection, itemsTbl)) {
      String errCmd = "insert into ${errorsTbl} (severity, test, err) "
          + " select 'FATAL ERROR', test, 'Bad start ability on adaptive test' from ${itemsTbl}";
      insertedErr = insertIntoErrorsTbl (connection, errCmd, itemsTbl, errorsTbl);
    }

    deleteFromItemsTbl (connection, itemsTbl);

    final String cmd4 = "insert into ${itemsTbl} (test, obj, cnt, req) "
        + " select T._efk_Segment, null, sum(S.MaxItems), T.MinItems "
        + "  from sim_segment T, sim_segmentcontentlevel S "
        + "    where T._fk_Session = ${session} and S._fk_Session = ${session} "
        + "       and  S._efk_Segment = T._efk_Segment and T.selectionalgorithm like 'adaptive%' "
        // "       and dbo.udfIsStrand(S.contentLevel) = 1 -- excludes non-strands  "
        + "       and EXISTS (SELECT _key FROM ${ItembankDB}.tblstrand WHERE _Key = S.contentLevel AND (_fk_Parent IS NULL OR TreeLevel = 1))"
        + "  group by T._efk_Segment, T.MinItems";
    String query = fixDataBaseNames (cmd4);
    insertIntoItemsTbl (connection, query, itemsTbl, session);

    if (existsCntInItemsTbl (connection, itemsTbl)) {
      final String errCmd = "insert into ${errorsTbl} (severity, test, err) "
          + " select 'FATAL ERROR', test, 'Insufficient strand item requirements (sum of maxes) for test length (minitems)' from ${itemsTbl} where cnt > req";
      insertedErr = insertIntoErrorsTbl (connection, errCmd, itemsTbl, errorsTbl);
    }

    deleteFromItemsTbl (connection, itemsTbl);

    final String cmd5 = "insert into ${itemsTbl} (test, obj, cnt, req) "
        + " select T._efk_Segment, null, sum(S.MinItems), T.MaxItems "
        + "   from sim_segment T, sim_segmentcontentlevel S "
        + "      where  T._fk_Session = ${session} and S._fk_Session = ${session} "
        + "        and  S._efk_Segment = T._efk_Segment and T.selectionalgorithm like 'adaptive%' "
        // and dbo.udfIsStrand(S.contentLevel) = 1 -- excludes non-strands
        + "       and EXISTS (SELECT _key FROM ${ItembankDB}.tblstrand WHERE _Key = S.contentLevel AND (_fk_Parent IS NULL OR TreeLevel = 1))"
        + "  group by T._efk_Segment, T.MaxItems";
    query = fixDataBaseNames (cmd5);
    insertIntoItemsTbl (connection, query, itemsTbl, session);

    if (existsCntInItemsTbl (connection, itemsTbl)) {
      final String errCmd = "insert into ${errorsTbl} (severity, test, err) "
          + " select 'FATAL ERROR', test, 'Strand item requirements (sum of mins) exceed test length (maxitems)' from ${itemsTbl} where cnt > req";
      insertedErr = insertIntoErrorsTbl (connection, errCmd, itemsTbl, errorsTbl);
    }

    deleteFromItemsTbl (connection, itemsTbl);

    final String cmd6 = "insert into ${itemsTbl} (test, obj, cnt, req)"
        + " select SI._efk_Segment, S.contentLevel, count(*), S.MinItems "
        + "  from sim_segmentcontentlevel S, sim_segmentitem SI, sim_segment T, ${ItembankDB}.aa_itemcl I "
        + "    where  SI._fk_Session = ${session} and S._fk_Session = ${session} "
        + "       and  I.ContentLevel = S.ContentLevel and I._fk_AdminSubject = S._efk_Segment "
        + "       and SI.IsFieldTest = 0 and SI.IsActive = 1 and SI._efk_Item = I._fk_Item "
        + "       and SI._efk_Segment = S._efk_Segment "
        + "       and T._fk_Session = ${session} and T._efk_Segment = S._efk_Segment "
        + "       and T.selectionalgorithm like 'adaptive%' "
        // and dbo.udfIsStrand(S.contentLevel) = 1 -- excludes non-strands
        + "       and EXISTS (SELECT _key FROM ${ItembankDB}.tblstrand WHERE _Key = S.contentLevel AND (_fk_Parent IS NULL OR TreeLevel = 1))"
        + "   group by SI._efk_Segment, S.ContentLevel, S.MinItems";
    query = fixDataBaseNames (cmd6);
    insertIntoItemsTbl (connection, query, itemsTbl, session);

    // we split this insert into two separate statements because
    // in MySql temporary table cannot be used twice in the same statement
    final String cmd7 = "select S._efk_Segment as test, S.ContentLevel as obj, bigtoint(0) as cnt, S.MinItems as req"
        + " from sim_segmentcontentlevel S, sim_segment T "
        + "    where S._fk_Session = ${session} and T._fk_Session = ${session} "
        + "       and S._efk_Segment = T._efk_Segment and T.selectionalgorithm like 'adaptive%' "
        // + " and dbo.udfIsStrand(S.contentLevel) = 1 -- excludes non-strands"
        + "       and EXISTS (SELECT _key FROM ${ItembankDB}.tblstrand WHERE _Key = S.contentLevel AND (_fk_Parent IS NULL OR TreeLevel = 1))"
        + "       and  not exists(select * from ${itemsTbl} where  S._efk_Segment = test and obj = S.ContentLevel)";
    SqlParametersMaps parms7 = (new SqlParametersMaps ()).put ("session", session);
    Map<String, String> unquotedItemsTblParms = new HashMap<> ();
    unquotedItemsTblParms.put ("itemsTbl", itemsTbl.getTableName ());

    // substitute ${ItembankDB} var
    query = fixDataBaseNames (cmd7);
    SingleDataResultSet rs7 = executeStatement (connection, fixDataBaseNames (query, unquotedItemsTblParms), parms7, false).getResultSets ().next ();

    final String cmd8 = "insert into ${itemsTbl} (test, obj, cnt, req)"
        + " values ( ${test}, ${obj}, ${cnt}, ${req} )";

    Integer insertedCnt = insertBatch (connection, fixDataBaseNames (cmd8, unquotedItemsTblParms), rs7, null);

    if (existsCntInItemsTbl (connection, itemsTbl)) {
      final String errCmd = "insert into ${errorsTbl} (severity, test, err) "
          + " select 'FATAL ERROR', test , concat('Insufficient operational items for strand: ',  obj) "
          + " from ${itemsTbl} where cnt < req";

      insertedErr = insertIntoErrorsTbl (connection, errCmd, itemsTbl, errorsTbl);
    }

    deleteFromItemsTbl (connection, itemsTbl);

    final String cmd9 = "insert into ${itemsTbl} (test, obj, cnt, req) "
        + " select S._efk_Segment, S.ContentLevel, sum(C.minItems ), S.minItems "
        + " from sim_segmentcontentlevel S, sim_segmentcontentlevel C, sim_segment T "
        + "   where S._fk_Session = ${session} and C._fk_Session = ${session} "
        + "   and S.ContentLevel = C.ContentLevel and S._efk_Segment = C._efk_Segment and C.ContentLevel not like '%|%|%' "
        + "   and T._fk_Session = ${session} and T._efk_Segment = S._efk_Segment and T.selectionalgorithm like 'adaptive%' "
        // and dbo.udfIsStrand(S.contentLevel) = 1 -- excludes non-strands
        + "   and EXISTS (SELECT _key FROM ${ItembankDB}.tblstrand WHERE _Key = S.contentLevel AND (_fk_Parent IS NULL OR TreeLevel = 1))"
        + "  group by S._efk_Segment, S.ContentLevel, S.minItems";
    // substitute ${ItembankDB} var
    query = fixDataBaseNames (cmd9);
    insertIntoItemsTbl (connection, query, itemsTbl, session);

    if (existsCntInItemsTbl (connection, itemsTbl)) {
      String errCmd = "insert into ${errorsTbl} (severity, test, err) "
          + " select 'FATAL ERROR', obj, 'Sum of content level mins exceed parent strand min' "
          + "  from ${itemsTbl} where cnt > req";
      insertedErr = insertIntoErrorsTbl (connection, errCmd, itemsTbl, errorsTbl);
    }

    deleteFromItemsTbl (connection, itemsTbl);

    final String cmd10 = "insert into ${itemsTbl} (test, req, cnt) "
        + " select T._efk_Segment, T.MinItems, count(*) "
        + " from sim_segment T, sim_segmentitem I "
        + "   where  T._fk_Session = ${session} and I._fk_Session = ${session} "
        + "      and I._efk_Segment = T._efk_Segment and IsFieldTest = 0 and IsActive = 1 "
        + "      and T.selectionAlgorithm like 'adaptive%'"
        + "  group by T._efk_Segment, T.MinItems";
    insertIntoItemsTbl (connection, cmd10, itemsTbl, session);

    // we split this insert into two separate statements because
    // in MySql temporary table cannot be used twice in the same statement
    final String cmd11 =
        " select T._efk_Segment as test, T.MinItems as req, 0 as cnt"
            + "  from sim_segment T "
            + "    where _fk_Session = ${session} and  T._efk_Segment not in (select test from ${itemsTbl}) "
            + "      and T.selectionAlgorithm like 'adaptive%'";
    SqlParametersMaps parms11 = parms7;

    SingleDataResultSet rs11 = executeStatement (connection, fixDataBaseNames (cmd11, unquotedItemsTblParms), parms11, false).getResultSets ().next ();

    final String cmd12 = "insert into ${itemsTbl} (test,  cnt, req)"
        + " values ( ${test},  ${cnt}, ${req} )";

    insertedCnt = insertBatch (connection, fixDataBaseNames (cmd12, unquotedItemsTblParms), rs11, null);

    if (existsCntInItemsTbl (connection, itemsTbl)) {
      final String errCmd = "insert into ${errorsTbl} (severity, test, err)"
          + "select 'FATAL ERROR', test, 'Insufficient operational items in pool' "
          + "  from ${itemsTbl} where cnt < req";
      insertedErr = insertIntoErrorsTbl (connection, errCmd, itemsTbl, errorsTbl);
    }

    deleteFromItemsTbl (connection, itemsTbl);

    final String cmd13 = "insert into ${itemsTbl} (test, req, cnt) "
        + " select T._efk_Segment, T.FTMinItems, count(*) "
        + "  from sim_segment T, sim_segmentitem I "
        + "    where  T._fk_Session = ${session} and I._fk_Session = ${session}"
        + "      and  I._efk_Segment = T._efk_Segment and IsFieldTest = 1 and IsActive = 1 "
        + "      and T.FTMinITems > 0 and T.selectionalgorithm like 'adaptive%' "
        + "  group by T._efk_Segment, I._efk_Segment, T.FTMinItems";
    insertIntoItemsTbl (connection, cmd13, itemsTbl, session);

    // we split this insert into two separate statements because
    // in MySql temporary table cannot be used twice in the same statement
    final String cmd14 = " select T._efk_Segment as test, T.MinItems as req, 0 as cnt"
        + " from sim_segment T "
        + "  where _fk_Session = ${session} and  T._efk_Segment not in (select test from ${itemsTbl})"
        + "  and T.FTMinItems > 0 and T.selectionAlgorithm like 'adaptive%'";
    SqlParametersMaps parms14 = parms7;

    SingleDataResultSet rs14 = executeStatement (connection, fixDataBaseNames (cmd14, unquotedItemsTblParms), parms14, false).getResultSets ().next ();

    final String cmd15 = "insert into ${itemsTbl} (test,  cnt, req)"
        + " values ( ${test},  ${cnt}, ${req} )";

    insertedCnt = insertBatch (connection, fixDataBaseNames (cmd15, unquotedItemsTblParms), rs14, null);

    final String cmd16 = "   update ${itemsTbl} set req = 0 where req is null";

    Integer updatedCnt = executeStatement (connection, fixDataBaseNames (cmd16, unquotedItemsTblParms), null, false).getUpdateCount ();

    if (existsCntInItemsTbl (connection, itemsTbl)) {
      final String errCmd = "insert into ${errorsTbl} (severity, test, err) "
          + "select 'WARNING', test, 'Insufficient field test items in pool' "
          + "  from ${itemsTbl} where cnt < req";
      insertedErr = insertIntoErrorsTbl (connection, errCmd, itemsTbl, errorsTbl);
    }

    deleteFromItemsTbl (connection, itemsTbl);

    final String cmd17 = "insert into ${itemsTbl} (test, req, cnt) "
        // -- Interpretation of start,end change Fall 2012 (T.FTEndPos - 1) -
        // (T.FTStartPos + 1) + 1
        + "select T._efk_Segment, T.FTMaxItems, T.FTEndPos - T.FTStartPos + 1"
        + "  from sim_segment T "
        + "  where _fk_session = ${session} and T.FTMaxItems > 0 and T.selectionAlgorithm like 'adaptive%'";
    insertIntoItemsTbl (connection, cmd17, itemsTbl, session);

    if (existsCntInItemsTbl (connection, itemsTbl)) {

      final String errCmd = "insert into ${errorsTbl} (severity, test, err) "
          + "select 'FATAL ERROR', test,  "
          + "concat("
          + "       'Insufficient positions (', "
          + "        cnt, "
          + "       ') to accommodate maximum field test items (', "
          + "        req, "
          + "       '). \"Start AFTER minpos, end BEFORE maxpos\"')"
          + "  from ${itemsTbl} where cnt < req";
      insertedErr = insertIntoErrorsTbl (connection, errCmd, itemsTbl, errorsTbl);
    }

    deleteFromItemsTbl (connection, itemsTbl);

    final String cmd18 = "insert into ${itemsTbl} (test, cnt) "
        + " select T.FormID, count(*) "
        + "  from ${ItembankDB}.testformitem F, sim_segmentitem I, ${ItembankDB}.testform T "
        + "    where F._fk_Item = I._efk_Item and I.IsActive = 0 and I._fk_session = ${session} "
        + "       and F._fk_AdminSubject = I._efk_Segment"
        + "       and T._fk_AdminSubject = I._efk_Segment and T._Key = F._fk_TestForm "
        + " group by T.FormID";
    // substitute ${ItembankDB} var
    query = fixDataBaseNames (cmd18);
    insertIntoItemsTbl (connection, query, itemsTbl, session);

    if (existsCntGrZeroInItemsTbl (connection, itemsTbl)) {
      final String errCmd = "insert into ${errorsTbl} (severity, test, err) "
          + "select 'FATAL ERROR', test, 'Fixed form has inactive items' "
          + "  from ${itemsTbl} where cnt > 0";
      insertedErr = insertIntoErrorsTbl (connection, errCmd, itemsTbl, errorsTbl);
    }

    deleteFromItemsTbl (connection, itemsTbl);

    final String cmd19 = " insert into ${itemsTbl} (test,  cnt, req) "
        + " select T._efk_Segment,  0, 0 "
        + " from sim_segment T "
        + "   where _fk_Session = ${session} and  selectionAlgorithm = 'fixedform' "
        + " and (FTMinItems> 0 or FTMaxItems > 0)";
    insertIntoItemsTbl (connection, cmd19, itemsTbl, session);

    if (existsInItemsTbl (connection, itemsTbl)) {
      String errCmd = "insert into ${errorsTbl} (severity, test, err) "
          + " select 'FATAL ERROR', test, 'Invalid field test specs on fixed form test' from ${itemsTbl}";
      insertedErr = insertIntoErrorsTbl (connection, errCmd, itemsTbl, errorsTbl);
    }

    final String cmd20 = "select test from ${errorsTbl} limit 1";
    Map<String, String> unquotedParms20 = new HashMap<> ();
    unquotedParms20.put ("errorsTbl", errorsTbl.getTableName ());

    List<SingleDataResultSet> resultSets = new ArrayList<SingleDataResultSet> ();

    if (exists (executeStatement (connection, fixDataBaseNames (cmd20, unquotedParms20), null, false))) {
      Long fatals = 0L;
      Long warnings = 0L;

      final String cmd21 = "select count(*) from ${errorsTbl} where severity = 'FATAL ERROR' as fatals";
      Map<String, String> unquotedParms21 = unquotedParms20;

      SingleDataResultSet rs21 = executeStatement (connection, fixDataBaseNames (cmd21, unquotedParms21), null, false).getResultSets ().next ();
      DbResultRecord rcd21 = rs21.getRecords ().next ();
      fatals = rcd21.<Long> get ("fatals");

      final String cmd22 = "select count(*) from ${errorsTbl} where severity = 'WARNINGS' as warnings";
      Map<String, String> unquotedParms22 = unquotedParms20;

      SingleDataResultSet rs22 = executeStatement (connection, fixDataBaseNames (cmd22, unquotedParms22), null, false).getResultSets ().next ();
      DbResultRecord rcd22 = rs22.getRecords ().next ();
      warnings = rcd22.<Long> get ("warnings");

      List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
      CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
      rcd.put ("status", "failed");
      rcd.put ("fatals", fatals.intValue ());
      rcd.put ("warnings", warnings.intValue ());

      SingleDataResultSet rs23 = new SingleDataResultSet ();
      rs23.addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      rs23.addColumn ("fatals", SQL_TYPE_To_JAVA_TYPE.INT);
      rs23.addColumn ("warnings", SQL_TYPE_To_JAVA_TYPE.INT);

      resultList.add (rcd);
      rs23.addRecords (resultList);
      resultSets.add (rs23);

      final String cmd24 = "select severity, test as `Object`, err as `Error` from ${errorsTbl} order by severity, err";
      Map<String, String> unquotedParms24 = unquotedParms20;

      SingleDataResultSet rs24 = executeStatement (connection, fixDataBaseNames (cmd24, unquotedParms24), null, false).getResultSets ().next ();
      resultSets.add (rs24);

    } else {
      List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
      CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
      rcd.put ("status", "success");
      rcd.put ("fatals", 0);
      rcd.put ("warnings", 0);

      SingleDataResultSet rs23 = new SingleDataResultSet ();
      rs23.addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      rs23.addColumn ("fatals", SQL_TYPE_To_JAVA_TYPE.INT);
      rs23.addColumn ("warnings", SQL_TYPE_To_JAVA_TYPE.INT);

      resultList.add (rcd);
      rs23.addRecords (resultList);
      resultSets.add (rs23);
    }

    connection.dropTemporaryTable (itemsTbl);
    connection.dropTemporaryTable (errorsTbl);

    return new MultiDataResultSet (resultSets);
  }

  public SingleDataResultSet SIM_ClearSessionOpportunityData_SP (SQLConnection connection, UUID session) throws ReturnStatusException {
    return SIM_ClearSessionOpportunityData_SP (connection, session, null);
  }

  public SingleDataResultSet SIM_ClearSessionOpportunityData_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException {

    String simstatus = null;
    String client = null;
    final String cmd1 = "  SELECT sim_status as simstatus, clientname as client FROM session "
        + " WHERE _Key = ${session} AND environment = 'SIMULATION'";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();
    DbResultRecord rcd1 = rs1.getCount () > 0 ? rs1.getRecords ().next () : null;
    if (rcd1 != null) {
      simstatus = rcd1.<String> get ("simstatus");
      client = rcd1.<String> get ("client");
    }
    if (DbComparator.isEqual (simstatus, "running")) {
      return _commonDll.ReturnStatusReason ("failed", "Session opportunity data cannot be cleared because simulation is still running.  Please cancel the simulation first.");
    }

    Long tests = null;
    final String cmd2 = "  select  count(*) as tests "
        + " from testopportunity where _fk_Session = ${session} and environment = 'SIMULATION'"
        + " and (${testkey} is null or _efk_AdminSubject = ${testkey})";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);
    SingleDataResultSet rs2 = executeStatement (connection, cmd2, parms2, false).getResultSets ().next ();
    DbResultRecord rcd2 = rs2.getCount () > 0 ? rs2.getRecords ().next () : null;
    if (rcd2 != null) {
      tests = rcd2.<Long> get ("tests");
    }

    if (DbComparator.isEqual (tests, 0)) {
      return _commonDll.ReturnStatusReason ("success", "Zero opportunities meet the requirements");
    }

    final String cmd3 = "update session set sim_abort = 1 where _Key = ${session}";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("session", session);
    Integer updatedCnt = executeStatement (connection, cmd3, parms3, false).getUpdateCount ();

    final String cmd4 = "delete from itemdistribution where _fk_Session = ${session} "
        + " and (${testkey} is null or _efk_AdminSubject = ${testkey})";
    SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);
    Integer deletedCnt = executeStatement (connection, cmd4, parms4, false).getUpdateCount ();

    final String cmd5 = "delete from ft_groupsamples where _fk_Session = ${session} "
        + " and (${testkey} is null or _efk_AdminSubject = ${testkey})";
    SqlParametersMaps parms5 = parms4;
    deletedCnt = executeStatement (connection, cmd5, parms5, false).getUpdateCount ();

    // -- All other TestOpportunity related data is on a cascade-delete foreign
    // key
    // -- deleting one at a time requires vastly smaller set of
    // rollback-potential data in the delete transaction
    // -- Not only is it more likely to succeed, but if it is aborted for some
    // reason, all the data deleted to that point remains deleted
    final String cmd6 = "select _key from testopportunity  "
        + " where  _fk_Session = ${session} and (${testkey} is null or _efk_AdminSubject = ${testkey}) "
        + " and environment = 'SIMULATION'";
    SqlParametersMaps parms6 = parms4;
    SingleDataResultSet rs6 = executeStatement (connection, cmd6, parms6, true).getResultSets ().next ();
    Iterator<DbResultRecord> iter = rs6.getRecords ();
    while (iter.hasNext ()) {
      DbResultRecord rcd6 = iter.next ();
      UUID key = rcd6.<UUID> get ("_key");
      if (key != null) {
        final String cmd11 = "delete from testeeresponse where _fk_testopportunity = ${key} ";
        SqlParametersMaps parms11 = (new SqlParametersMaps ()).put ("key", key);
        deletedCnt = executeStatement (connection, cmd11, parms11, false).getUpdateCount ();

        final String cmd10 = "delete from testopportunity where _Key = ${key}";
        SqlParametersMaps parms10 = (new SqlParametersMaps ()).put ("key", key);
        deletedCnt = executeStatement (connection, cmd10, parms10, false).getUpdateCount ();
      }

    }
    // Elena: replace this statement with the functionality above
    // because was getting this error message on MySql5.6.14
    // This version of MySQL doesn't yet support 'LIMIT & IN/ALL/ANY/SOME
    // subquery'
    // while (exists (executeStatement (connection, cmd6, parms6, true))) {
    //
    // final String cmd7 = "delete from testopportunity where _Key in "
    // + "(select  _Key from testopportunity "
    // +
    // "    where  _fk_Session = ${session} and (${testkey} is null or _efk_AdminSubject = ${testkey})"
    // + "      and environment = 'SIMULATION' limit 1)";
    // SqlParametersMaps parms7 = parms4;
    // deletedCnt = executeStatement (connection, cmd7, parms7,
    // true).getUpdateCount ();
    // }

    final String cmd8 = " update session set sim_abort=0, sim_status = 'deleted', sim_start = null, sim_stop = null where _Key = ${session}";
    SqlParametersMaps parms8 = (new SqlParametersMaps ()).put ("session", session);
    updatedCnt = executeStatement (connection, cmd8, parms8, false).getUpdateCount ();

    String reason = String.format ("%d %s", tests, "opportunities were deleted");
    return _commonDll.ReturnStatusReason ("success", reason);
  }

  public SingleDataResultSet SIM_DeleteTest_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException {

    String status = null;
    SingleDataResultSet rs1 = SIM_ClearSessionOpportunityData_SP (connection, session, testkey);
    DbResultRecord rcd1 = (rs1.getCount () > 0 ? rs1.getRecords ().next () : null);
    if (rcd1 != null)
      status = rcd1.<String> get ("status");

    if (DbComparator.isEqual (status, "success")) {
      final String cmd2 = "delete from sessiontests where _fk_Session = ${session} and _efk_adminsubject = ${testkey}";
      SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);
      Integer deletedCnt = executeStatement (connection, cmd2, parms2, false).getUpdateCount ();
    }
    return rs1;
  }

  public SingleDataResultSet SIM_AlterSessionTest2_SP (SQLConnection connection, UUID session, String testkey,
      Integer iterations, Integer opportunities,
      Float meanProficiency, Float sdProficiency, Float strandCorrelation) throws ReturnStatusException {

    return SIM_AlterSessionTest2_SP (connection, session, testkey, iterations, opportunities,
        meanProficiency, sdProficiency, strandCorrelation, null);
  }

  public SingleDataResultSet SIM_AlterSessionTest2_SP (SQLConnection connection, UUID session, String testkey,
      Integer iterations, Integer opportunities,
      Float meanProficiency, Float sdProficiency, Float strandCorrelation,
      String handScoreItemTypes) throws ReturnStatusException {

    String client = getClientnameFromSession (connection, session);
    if (testOpportunityExists (connection, session, testkey) == false) {

      String status = null;
      SingleDataResultSet rs1 = SIM_ClearSessionOpportunityData_SP (connection, session, testkey);
      DbResultRecord rcd1 = (rs1.getCount () > 0 ? rs1.getRecords ().next () : null);
      if (rcd1 != null) {
        status = rcd1.<String> get ("status");
      }

      if (DbComparator.isEqual (status, "success")) {

        final String cmd1 = "update sessiontests set iterations = ${iterations}, opportunities = ${opportunities}, "
            + " meanProficiency = ${meanProficiency}, sdProficiency = ${sdProficiency}, strandCorrelation = ${strandCorrelation}, "
            + " handScoreItemTypes = ${handScoreItemTypes} "
            + "  where _fk_Session = ${session} and _efk_AdminSubject = ${testkey}";
        SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey).
            put ("iterations", iterations).put ("opportunities", opportunities).put ("meanProficiency", meanProficiency).
            put ("sdProficiency", sdProficiency).put ("strandCorrelation", strandCorrelation).
            put ("handScoreItemTypes", handScoreItemTypes);
        Integer updatedCnt = executeStatement (connection, cmd1, parms1, false).getUpdateCount ();
      }
      return rs1;

    } else {
      return _commonDll._ReturnError_SP (connection, client, "SIM_AlterSessionTest2",
          "Opportunity data already exist on this test within this session");
    }
  }

  public SingleDataResultSet SIM_DeleteSession_SP (SQLConnection connection, UUID session) throws ReturnStatusException {

    String client = null;
    final String cmd1 = "select clientname as client from simp_session where _Key = ${session}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();
    DbResultRecord record1 = (rs1.getCount () > 0 ? rs1.getRecords ().next () : null);
    if (record1 != null) {
      client = record1.<String> get ("client");
    }

    if (client == null) {
      String status = null;
      SingleDataResultSet rs2 = SIM_ClearSessionOpportunityData_SP (connection, session);
      DbResultRecord rcd2 = (rs2.getCount () > 0 ? rs2.getRecords ().next () : null);
      if (rcd2 != null)
        status = rcd2.<String> get ("status");

      if (DbComparator.isEqual (status, "success")) {
        final String cmd3 = "delete from session where _key = ${session}";
        SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("session", session);
        Integer deletedCnt = executeStatement (connection, cmd3, parms3, false).getUpdateCount ();
      }

      return rs2;

    } else {
      return _commonDll.ReturnStatusReason ("failed", "Published session cannot be deleted");
    }
  }

  public SingleDataResultSet SIM_AlterSegment2_SP (SQLConnection connection,
      UUID session, String testkey,
      String segmentKey, Float startAbility, Float startInfo,
      Integer minItems, Integer maxItems, Float bpweight,
      Integer cset1size, Integer cset2InitialRandom,
      Integer cset2Random, Float itemWeight,
      Float abilityOffset) throws ReturnStatusException {

    String selectionAlgorithm = "adaptive2";
    String cset1Order = "ABILITY";
    Integer ftmin = null, ftmax = null, ftstart = null, ftend = null;
    Float rcAbilityWeight = (float) 1.0;
    Float abilityWeight = (float) 1.0;
    Float precisionTargetMetWeight = (float) 1.0;
    Float precisionTargetNotMetWeight = (float) 1.0;
    Float precisionTarget = null;
    Float adaptiveCut = null;
    Float tooCloseSEs = null;
    Boolean terminationOverallInfo = false;
    Boolean terminationRCInfo = false;
    Boolean terminationMinCount = false;
    Boolean terminationTooClose = false;
    Boolean terminationFlagsAnd = false;

    return SIM_AlterSegment2_SP (connection, session, testkey, segmentKey, startAbility, startInfo, minItems, maxItems,
        bpweight, cset1size, cset2InitialRandom, cset2Random, itemWeight, abilityOffset, selectionAlgorithm, cset1Order,
        ftmin, ftmax, ftstart, ftend, rcAbilityWeight, abilityWeight, precisionTargetMetWeight, precisionTargetNotMetWeight,
        precisionTarget, adaptiveCut, tooCloseSEs, terminationOverallInfo, terminationRCInfo,
        terminationMinCount, terminationTooClose, terminationFlagsAnd);
  }

  public SingleDataResultSet SIM_AlterSegment2_SP (SQLConnection connection,
      UUID session, String testkey,
      String segmentKey, Float startAbility, Float startInfo,
      Integer minItems, Integer maxItems, Float bpweight,
      Integer cset1size, Integer cset2InitialRandom,
      Integer cset2Random, Float itemWeight,
      Float abilityOffset,
      String selectionAlgorithm,
      String cset1Order,
      Integer ftmin, Integer ftmax, Integer ftstart, Integer ftend,
      Float rcAbilityWeight,
      Float abilityWeight,
      Float precisionTargetMetWeight,
      Float precisionTargetNotMetWeight,
      Float precisionTarget,
      Float adaptiveCut,
      Float tooCloseSEs,
      Boolean terminationOverallInfo,
      Boolean terminationRCInfo,
      Boolean terminationMinCount,
      Boolean terminationTooClose,
      Boolean terminationFlagsAnd
      ) throws ReturnStatusException {

    String client = getClientnameFromSession (connection, session);

    final String cmd1 = "select _fk_session from sim_segment where _fk_Session = ${session} "
        + " and _efk_AdminSubject = ${testKey} and _efk_Segment = ${segmentKey} limit 1";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey).put ("segmentkey", segmentKey);

    if (exists (executeStatement (connection, cmd1, parms1, false))) {
      final String cmd2 = "select _fk_session from testopportunity where _fk_Session = ${session}"
          + " and _efk_AdminSubject = ${testkey} limit 1";
      SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);
      if (!exists (executeStatement (connection, cmd2, parms2, false))) {
        
        String currentSelectionAlgorithm = null;         
        final String cmd4 = "select selectionalgorithm from sim_segment where _fk_Session = ${session} and _efk_adminsubject= ${testkey} and _efk_segment=${segmentkey}";
        SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey).put ("segmentkey", segmentKey);
        SingleDataResultSet rs = executeStatement (connection, cmd4, parms4, false).getResultSets ().next ();
        DbResultRecord rd = (rs.getCount () > 0 ? rs.getRecords ().next () : null);
        if (rd != null)
          currentSelectionAlgorithm = rd.<String> get ("selectionalgorithm");
        
        final String cmd3 = "UPDATE  sim_segment "
            + " SET StartAbility=${startAbility}, StartInfo=${startInfo}, MinItems=${minItems}, MaxItems=${maxItems} "
            + " , blueprintWeight=${bpWeight}, cset1size=${cset1size}, cset2Random=${cset2Random} "
            + " , cset2InitialRandom=${cset2InitialRandom},  itemWeight=${itemWeight}, abilityOffset=${abilityOffset} "
            + " , selectionAlgorithm = ${selectionAlgorithm} "
            + " , cset1Order = ${cset1Order}"
            + " , ftMinItems = coalesce(${ftmin}, ftMinITems), ftMaxItems = coalesce(${ftmax}, ftMaxItems) "
            + " , ftStartPos = coalesce(${ftstart}, ftStartPos), ftEndPos = coalesce(${ftend}, ftEndPos) "
            + " , abilityWeight = ${abilityWeight}, rcAbilityWeight = ${rcAbilityWeight} "
            + " , precisionTargetMetWeight = ${precisionTargetMetWeight} "
            + " , precisionTargetNotMetWeight = ${precisionTargetNotMetWeight}, precisionTarget =  ${precisionTarget} "
            + " , adaptiveCut = ${adaptiveCut}, tooCloseSEs = ${tooCloseSEs}, terminationOverallInfo = ${terminationOverallInfo}"
            + " , terminationRCInfo = ${terminationRCInfo}, terminationMinCount = ${terminationMinCount} "
            + " , terminationTooClose = ${terminationTooClose}, terminationFlagsAnd = ${terminationFlagsAnd} "
            + " WHERE _fk_Session = ${session} and _efk_AdminSUbject = ${testkey} and _efk_Segment = ${segmentKey}";
        SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey)
            .put ("segmentkey", segmentKey).put ("startability", startAbility).put ("startinfo", startInfo).
            put ("minItems", minItems).put ("maxItems", maxItems).put ("bpweight", bpweight).put ("cset1size", cset1size).
            put ("cset2random", cset2Random).put ("cset2initialrandom", cset2InitialRandom).put ("itemweight", itemWeight).
            put ("abilityoffset", abilityOffset).put ("cset1Order", cset1Order).put ("ftmin", ftmin).put ("ftmax", ftmax).
            put ("ftstart", ftstart).put ("ftend", ftend).put ("selectionAlgorithm", selectionAlgorithm).
            put ("abilityWeight", abilityWeight).put ("rcAbilityWeight", rcAbilityWeight).
            put ("precisionTargetMetWeight", precisionTargetMetWeight).put ("precisionTargetNotMetWeight", precisionTargetNotMetWeight).
            put ("precisionTarget", precisionTarget).put ("adaptiveCut", adaptiveCut).put ("tooCloseSEs", tooCloseSEs).
            put ("terminationOverallInfo", terminationOverallInfo).
            put ("terminationRCInfo", terminationRCInfo).put ("terminationMinCount", terminationMinCount).
            put ("terminationTooClose", terminationTooClose).put ("terminationFlagsAnd", terminationFlagsAnd);

        Integer updatedCnt = executeStatement (connection, cmd3, parms3, false).getUpdateCount ();
        
        if ((currentSelectionAlgorithm != null) && !selectionAlgorithm.equals (currentSelectionAlgorithm))
            SIM_CreateItemSelectionParameters(connection, session); 
        
        return _commonDll.ReturnStatusReason ("success", null);

      } else {

        return _commonDll._ReturnError_SP (connection, client, "SIM_AlterSegment2", "Opportunity data already exist on this test within this session");
      }

    }
    return _commonDll._ReturnError_SP (connection, client, "SIM_AlterSegment2", "The segment does not exist");
  }

  public SingleDataResultSet SIM_ReportOPItemDistribution_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException {
    Float distributionInterval = (float) 0.20;
    return SIM_ReportOPItemDistribution_SP (connection, session, testkey, distributionInterval);
  }

  public SingleDataResultSet SIM_ReportOPItemDistribution_SP (SQLConnection connection, UUID session, String testkey,
      Float distributionInterval) throws ReturnStatusException {

    Long testCount = null;
    final String cmd1 = "select  count(*) as testCount from testopportunity  "
        + " where _fk_Session = ${session} and _efk_AdminSubject = ${testkey} and dateCompleted is not null";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, true).getResultSets ().next ();
    DbResultRecord rcd1 = rs1.getRecords ().next ();
    if (rcd1 != null) {
      testCount = rcd1.<Long> get ("testCount");
    }

    Long poolCount = null;
    final String cmd2 = "select count(distinct _efk_Item) as poolCount "
        + " from sim_segment S, sim_segmentitem I "
        + " where S._fk_Session = ${session} and I._fk_Session = ${session} and S._efk_AdminSUbject = ${testkey} "
        + "   and I._efk_SEgment = S._efk_Segment and I.isFieldTEst = 0 and I.isActive = 1";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);
    SingleDataResultSet rs2 = executeStatement (connection, cmd2, parms2, true).getResultSets ().next ();
    DbResultRecord rcd2 = rs2.getRecords ().next ();
    if (rcd2 != null) {
      poolCount = rcd2.<Long> get ("poolCount");
    }

    DataBaseTable itemsTbl = getDataBaseTable ("items").addColumn ("itemkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("cnt", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (itemsTbl);

    final String cmd3 = "insert into ${itemsTbl} (itemkey, cnt) "
        + " select _efk_ItemKey, count(*) "
        + "  from testopportunity O, testeeresponse R  "
        + "    where O._fk_SEssion = ${session} and O._efk_AdminSUbject = ${testkey} and O.dateCompleted is not null "
        + "      and R._fk_TestOpportunity = O._Key and R.isFieldTest = 0 "
        + " group by _efk_ItemKey";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);
    Map<String, String> unquotedItemsTblParms = new HashMap<String, String> ();
    unquotedItemsTblParms.put ("itemsTbl", itemsTbl.getTableName ());
    Integer insertedCnt = executeStatement (connection, fixDataBaseNames (cmd3, unquotedItemsTblParms), parms3, true).getUpdateCount ();

    // lbPct float, -- the lower bound in percent of items administered w.r.t.
    // test count
    // ubPct float, -- the upper bound in percent of items w.r.t. test count
    // lbCnt int, -- the count of items at the lower bound
    // ubCnt int, -- the count of items at the upper bound
    // itemPct float, -- the percent of items w.r.t. the pool size administered
    // #times in the range
    // itemCnt int -- number of unique items administered # times between lbCnt
    // and ubCnt

    DataBaseTable bucketsTbl = getDataBaseTable ("buckets").
        addColumn ("i", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("lbPct", SQL_TYPE_To_JAVA_TYPE.FLOAT).
        addColumn ("ubPct", SQL_TYPE_To_JAVA_TYPE.FLOAT).
        addColumn ("lbCnt", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("ubCnt", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("itemPct", SQL_TYPE_To_JAVA_TYPE.FLOAT).
        addColumn ("itemCnt", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (bucketsTbl);

    Integer numBuckets = (int) Math.round (1.0 / distributionInterval);
    Float lbPct = (float) 0.0;
    Float ubPct = (float) 0.0;
    Float itemPct = (float) 0.0;

    final String cmd4 = "insert into ${bucketsTbl} (i, lbPCT, ubPCT, lbCNT, ubCnt, itemPct, itemCnt) "
        + " values (0, ${lbPct}, ${ubPct}, 0, 0, ${itemPct}, 0)";
    SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("lbPct", lbPct).put ("ubPct", ubPct).put ("itemPct", itemPct);
    Map<String, String> unquotedBucketsTblParms = new HashMap<String, String> ();
    unquotedBucketsTblParms.put ("bucketsTbl", bucketsTbl.getTableName ());

    insertedCnt = executeStatement (connection, fixDataBaseNames (cmd4, unquotedBucketsTblParms), parms4, false).getUpdateCount ();

    Integer b = 0;
    while (DbComparator.lessThan (b, numBuckets)) {
      lbPct = b * distributionInterval;
      ubPct = (b + 1) * distributionInterval;

      final String cmd5 = "insert into ${bucketsTbl} (i, lbPCT, ubPCT, lbCNT, ubCnt, itemCnt) "
          + " select ${b} + 1, round(${lbPct}, 2), round(${ubPct}, 2), round(${testCount} * ${lbPct}, 0) + 1, "
          + "  ${testCount} * ${ubPct}, 0";

      SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("b", b).put ("lbPct", lbPct).
          put ("ubPct", ubPct).put ("testCount", testCount);
      insertedCnt = executeStatement (connection, fixDataBaseNames (cmd5, unquotedBucketsTblParms), parms5, false).getUpdateCount ();

      b++;
    }

    final String cmd5 = "update ${bucketsTbl} set itemCnt = "
        + "(select count(*) from ${itemsTbl} where cnt between lbCnt and ubCnt)";
    Map<String, String> unquotedParms5 = new HashMap<String, String> ();
    unquotedParms5.put ("itemsTbl", itemsTbl.getTableName ());
    unquotedParms5.put ("bucketsTbl", bucketsTbl.getTableName ());
    Integer updatedCnt = executeStatement (connection, fixDataBaseNames (cmd5, unquotedParms5), null, false).getUpdateCount ();

    final String cmd6 = "update ${bucketsTbl} set itemCnt = ${poolcount} - (select count(*) from ${itemsTbl}) "
        + " where i = 0";
    Map<String, String> unquotedParms6 = unquotedParms5;
    SqlParametersMaps parms6 = (new SqlParametersMaps ()).put ("poolcount", poolCount);
    updatedCnt = executeStatement (connection, fixDataBaseNames (cmd6, unquotedParms6), parms6, false).getUpdateCount ();

    if (DbComparator.greaterThan (poolCount, 0)) {
      final String cmd7 = " update ${bucketsTbl} set itemPCT = round(100*itemCnt / ${poolcount}, 2)";
      SqlParametersMaps parms7 = (new SqlParametersMaps ()).put ("poolcount", poolCount);
      updatedCnt = executeStatement (connection, fixDataBaseNames (cmd7, unquotedBucketsTblParms), parms7, false).getUpdateCount ();
    }

    final String cmd8 = "select concat('[', round(lbPCT * 100), ',', round(ubPCT * 100), ']') as `% Tests`, "
        + " concat('[', lbCnt, ',', ubCnt,  ']') as `# Tests`,"
        + " itemPct as `% Item Pool`, itemCnt as `# Unique Items`, bigtoint(${poolcount}) as PoolSize "
        + " from ${bucketsTbl} order by i";
    SqlParametersMaps parms8 = (new SqlParametersMaps ()).put ("poolcount", poolCount);
    SingleDataResultSet rs8 = executeStatement (connection, fixDataBaseNames (cmd8, unquotedBucketsTblParms), parms8, false).getResultSets ().next ();

    connection.dropTemporaryTable (bucketsTbl);
    connection.dropTemporaryTable (itemsTbl);

    return rs8;
  }

  public MultiDataResultSet SIM_ItemDistribution_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException {

    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();
    final String cmd1 = "select segmentID, groupID, _efk_Item as item, I.isFieldTest, "
        + "(select bigtoint(count(*)) "
        + "    from testopportunity O, testeeresponse R  "
        + "    where O._fk_session = ${session} and R._fk_TestOpportunity = O._Key "
        + "       and R.segmentID = S.segmentID and R._efk_ItemKey = I._Efk_Item) as N"
        + " from sim_segment S, sim_segmentitem I "
        + " where S._fk_Session = ${session} and I._fk_Session = ${session} "
        + "    and S._efk_AdminSubject = ${testkey} and I._efk_Segment = S._efk_Segment "
        + " order by segmentID, isFieldTest , groupID, N";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, true).getResultSets ().next ();

    resultsets.add (rs1);
    return new MultiDataResultSet (resultsets);
  }

  public MultiDataResultSet SIM_ReportItems_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException {
    return SIM_ReportItems_SP (connection, session, testkey, true);
  }

  public MultiDataResultSet SIM_ReportItems_SP (SQLConnection connection, UUID session, String testkey, Boolean debug) throws ReturnStatusException {

    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();

    Date starttime = _dateUtil.getDateWRetStatus (connection);
    Calendar c = Calendar.getInstance ();
    c.setTime (starttime);
    int hourOfTheDay = c.get (Calendar.HOUR_OF_DAY);

    if (debug == false && (hourOfTheDay < 1 || hourOfTheDay > 5)) {
      resultsets.add (_commonDll.ReturnStatusReason ("failed", "Item dumps only available between 1 and 5 am Eastern Time"));
      return new MultiDataResultSet (resultsets);
    }

    final String cmd1 = "select _efk_Testee as Student, Opportunity, _efk_TestID as TestID, "
        + "    Language, Position, R.groupID, _efk_ItemKey as ItemID, Score, IRT_b, S.name as contentLevel "
        + " from testopportunity O, testeeresponse R, sim_segment M "
        + " , ${ItembankDB}.tblsetofadminitems A, ${ItembankDB}.tblstrand S "
        + " where M._fk_Session = ${session} and M._efk_AdminSubject = ${testkey} "
        + "   and O._fk_Session = ${session} and O._efk_AdminSubject = ${testkey} "
        + "   and R._fk_TestOPportunity = O._key and R.segment = M.segmentPosition "
        + "   and O.dateCompleted is not null "
        + "   and A._fk_Item = R._efk_ItemKey and A._fk_AdminSubject = M._efk_Segment and S._key = A._fk_Strand "
        + " order by _efk_Testee, opportunity, Position";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);
    SingleDataResultSet rs1 = executeStatement (connection, fixDataBaseNames (cmd1), parms1, true).getResultSets ().next ();

    _commonDll._LogDBLatency_SP (connection, "SIM_ReportItems", starttime, null, true, null, null, session, null, testkey);
    resultsets.add (rs1);
    return new MultiDataResultSet (resultsets);
  }

  public SingleDataResultSet SIM_ReportRecycled_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException {

    DataBaseTable dupsTbl = getDataBaseTable ("dups").addColumn ("testee", SQL_TYPE_To_JAVA_TYPE.BIGINT).addColumn ("itemkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50);
    connection.createTemporaryTable (dupsTbl);

    final String cmd1 = "insert into ${dupsTbl} (testee, itemkey) "
        + " select distinct O1._efk_Testee, R1._efk_ItemKey "
        + "   from testeeresponse R1 , testeeresponse R2 , testopportunity O1 , testopportunity O2  "
        + "     where O1._fk_session = ${session} and O2._fk_session = ${session}"
        + "     and O1._efk_AdminSubject = ${testkey} and O2._efk_AdminSubject = ${testkey} "
        + "     and O1._efk_Testee = O2._efk_Testee "
        + "     and O1._Key <> O2._Key "
        + "     and R1._fk_TestOpportunity = O1._Key and R2._fk_TestOpportunity = O2._key "
        + "     and R1._efk_ITSItem is not null and R1._efk_ItemKey = R2._efk_ItemKey";
    Map<String, String> unquotedParms1 = new HashMap<String, String> ();
    unquotedParms1.put ("dupsTbl", dupsTbl.getTableName ());
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);
    Integer insertedCnt = executeStatement (connection, fixDataBaseNames (cmd1, unquotedParms1), parms1, true).getUpdateCount ();

    final String cmd2 = "select itemkey as `Recycled Item`, count(distinct testee) as students from ${dupsTbl} "
        + " group by itemkey";
    Map<String, String> unquotedParms2 = unquotedParms1;
    SingleDataResultSet rs2 = executeStatement (connection, fixDataBaseNames (cmd2, unquotedParms2), null, true).getResultSets ().next ();

    connection.dropTemporaryTable (dupsTbl);

    return rs2;
  }

  public void _RecordSegmentSatisfaction_SP (SQLConnection connection, UUID oppkey) throws ReturnStatusException {

    String testKey = null;
    String testId = null;
    UUID session = null;

    final String cmd1 = "select _efk_AdminSubject as testkey, _efk_TestID  as testId, _fk_session as session "
        + " from testopportunity where _Key = ${oppkey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();
    DbResultRecord rcd1 = (rs1.getCount () > 0 ? rs1.getRecords ().next () : null);
    if (rcd1 != null) {
      testKey = rcd1.<String> get ("testKey");
      testId = rcd1.<String> get ("testId");
      session = rcd1.<UUID> get ("session");
    }

    DataBaseTable itemsTbl = getDataBaseTable ("items").addColumn ("_key", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("segment", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).
        addColumn ("contentLevel", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200);
    connection.createTemporaryTable (itemsTbl);

    final String cmd2 = " insert into ${itemsTbl} (_key, segment, contentLevel) "
        + " select _efk_ItemKey, _efk_Segment, C.contentLevel "
        + "   from testeeresponse R, testopportunitysegment S, ${ItembankDB}.aa_itemcl C "
        + "     where R._fk_TestOpportunity = ${oppkey} and S._fk_TestOpportunity = ${oppkey} "
        + "       and S.segmentPosition = R.segment and C._fk_AdminSUbject = S._efk_Segment "
        + "       and C._fk_Item = R._efk_ItemKey and R.isFieldTest = 0";
    SqlParametersMaps parms2 = parms1;
    Map<String, String> unquotedParms2 = new HashMap<String, String> ();
    unquotedParms2.put ("itemsTbl", itemsTbl.getTableName ());

    String query = fixDataBaseNames (cmd2);
    Integer insertedCnt = executeStatement (connection, fixDataBaseNames (query, unquotedParms2), parms2, false).getUpdateCount ();

    // -- record strand and contentlevel counts for blueprint analysis
    final String cmd3 = "delete from testopportunitysegmentcounts where _fk_TestOpportunity = ${oppkey}";
    SqlParametersMaps parms3 = parms1;
    Integer deletedCnt = executeStatement (connection, cmd3, parms3, false).getUpdateCount ();

    final String cmd4 = "insert into testopportunitysegmentcounts "
        + "    (_fk_TestOpportunity, _efk_TestID, _efk_AdminSubject, _efk_Segment, ContentLevel, itemcount, dateentered) "
        + "    select ${oppkey}, ${testID}, ${testkey}, segment, contentLevel, count(*), now(3) "
        + " from ${itemsTbl} I "
        + " group by segment, contentLevel";
    SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("oppkey", oppkey).put ("testid", testId).
        put ("testkey", testKey);
    Map<String, String> unquotedParms4 = unquotedParms2;
    insertedCnt = executeStatement (connection, fixDataBaseNames (cmd4, unquotedParms4), parms4, false).getUpdateCount ();

    connection.dropTemporaryTable (itemsTbl);
  }

  public SingleDataResultSet SIM_ReportSegmentSatisfaction_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException {

    DataBaseTable oppsTbl = getDataBaseTable ("opps").addColumn ("oppkey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER).addColumn ("segment", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).
        addColumn ("CL", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200).addColumn ("cnt", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("oppnum", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (oppsTbl);

    Integer oppCnt = null;
    Integer segCnt = null;
    final String cmd1 = "select count(*) as oppcnt from testopportunity "
        + " where _fk_Session = ${session} and _efk_AdminSUbject = ${testkey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();
    DbResultRecord rcd1 = rs1.getRecords ().next ();
    if (rcd1 != null) {
      Long tmp = rcd1.<Long> get ("oppcnt");
      if (tmp != null)
        oppCnt = tmp.intValue ();
    }

    final String cmd2 = "select count(distinct _fk_TestOpportunity) as segcnt "
        + " from testopportunity O, testopportunitysegmentcounts "
        + " where _fk_Session = ${session} and O._efk_AdminSUbject = ${testkey} and _fk_TestOpportunity = _Key";
    SqlParametersMaps parms2 = parms1;
    SingleDataResultSet rs2 = executeStatement (connection, cmd2, parms2, false).getResultSets ().next ();
    DbResultRecord rcd2 = rs2.getRecords ().next ();
    if (rcd2 != null) {
      Long tmp = rcd2.<Long> get ("segcnt");
      if (tmp != null)
        segCnt = tmp.intValue ();
    }
    if (DbComparator.notEqual (oppCnt, segCnt)) {
      __PatchSegmentCounts_SP (connection, session, testkey);
    }

    final String cmd3 = "insert into ${oppsTbl} (oppkey, oppnum, segment, CL, cnt) "
        + " select O._Key, O.opportunity, C._Efk_Segment, BP.ContentLevel, itemcount - BP.maxITems"
        + "   from testopportunity O  , testopportunitysegmentcounts C , sim_segmentcontentlevel BP "
        + " where O._fk_Session = ${session} and O._efk_AdminSubject = ${testkey} and C._fk_TestOpportunity = O._Key "
        + "    and BP._fk_Session = ${session} and C._efk_Segment = BP._efk_Segment"
        + "    and BP.contentLevel = C.COntentLEvel and itemcount > BP.maxitems";
    SqlParametersMaps parms3 = parms1;
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("oppsTbl", oppsTbl.getTableName ());
    Integer insertedCnt = executeStatement (connection, fixDataBaseNames (cmd3, unquotedParms), parms3, true).getUpdateCount ();

    final String cmd4 = "insert into ${oppsTbl} (oppkey, oppnum, segment, CL, cnt) "
        + " select O._Key, O.opportunity, BP._Efk_Segment, BP.ContentLevel, 0 - BP.maxITems "
        + "   from testopportunity O , sim_segmentcontentlevel BP "
        + "     where O._fk_Session = ${session} and O._efk_AdminSubject = ${testkey} "
        + "      and BP._fk_Session = ${session} and 0 > BP.maxitems "
        + "      and not exists "
        + "       (select _fk_TestOpportunity from testopportunitysegmentcounts C "
        + "        where C._fk_TestOpportunity = O._Key AND C.ContentLevel = BP.contentLevel "
        + "          AND C._efk_Segment = BP._efk_Segment)";
    SqlParametersMaps parms4 = parms1;
    insertedCnt = executeStatement (connection, fixDataBaseNames (cmd4, unquotedParms), parms4, true).getUpdateCount ();

    final String cmd5 = "insert into ${oppsTbl} (oppkey, oppnum, segment, CL, cnt) "
        + "select O._Key, O.opportunity, C._efk_Segment, BP.ContentLevel, itemcount - BP.minITems "
        + " from testopportunity O  , testopportunitysegmentcounts C , SIM_SegmentContentLevel BP "
        + "    where O._fk_Session = ${session} and O._efk_AdminSubject = ${testkey} and C._fk_TestOpportunity = O._Key "
        + "      and BP._fk_Session = ${session} and C._efk_Segment = BP._efk_Segment "
        + "      and BP.contentLevel = C.COntentLEvel and itemcount < BP.minitems";
    SqlParametersMaps parms5 = parms1;
    insertedCnt = executeStatement (connection, fixDataBaseNames (cmd5, unquotedParms), parms5, true).getUpdateCount ();

    final String cmd6 = "insert into ${oppsTbl} (oppkey, oppnum, segment, CL, cnt) "
        + " select O._Key, O.opportunity, BP._Efk_Segment, BP.ContentLevel, 0 - BP.minITems"
        + "   from testopportunity O , sim_segmentcontentlevel BP "
        + "  where O._fk_Session = ${session} and O._efk_AdminSubject = ${testkey} "
        + "  and BP._fk_Session = ${session} and 0 < BP.minitems "
        + "  and not exists "
        + "    (select _fk_TestOpportunity from testopportunitysegmentcounts C "
        + "     where C._fk_TestOpportunity = O._Key AND C.ContentLevel = BP.contentLevel "
        + "     AND C._efk_Segment = BP._efk_Segment)";
    SqlParametersMaps parms6 = parms1;
    insertedCnt = executeStatement (connection, fixDataBaseNames (cmd6, unquotedParms), parms6, true).getUpdateCount ();

    final String cmd7 = "select segmentPosition, segmentID,  oppnum as Opportunity,"
        + " CL as contentLevel, cnt as `Items Under/Over`, bigtoint(count(*)) as numopps "
        + " from ${oppsTbl}, sim_segment "
        + "   where _fk_Session = ${session} and _efk_AdminSUbject = ${testkey} and _efk_Segment = segment "
        + " group by oppnum, segmentPosition, segmentID, CL, cnt "
        + " order by segmentPosition, CL, oppnum, cnt";
    SqlParametersMaps parms7 = parms1;
    SingleDataResultSet rs7 = executeStatement (connection, fixDataBaseNames (cmd7, unquotedParms), parms7, false).getResultSets ().next ();

    connection.dropTemporaryTable (oppsTbl);

    return rs7;
  }

  public MultiDataResultSet SIM_ReportBPSummary_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException {

    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();

    MultiDataResultSet sets1 = SIM_ReportBPSatisfaction_SP (connection, session, testkey);
    Iterator<SingleDataResultSet> iter = sets1.getResultSets ();
    while (iter.hasNext ()) {
      SingleDataResultSet tmp = iter.next ();
      resultsets.add (tmp);
    }
    SingleDataResultSet rs2 = SIM_ReportSegmentSatisfaction_SP (connection, session, testkey);
    resultsets.add (rs2);
    SingleDataResultSet rs3 = SIM_ReportOPItemDistribution_SP (connection, session, testkey);
    resultsets.add (rs3);
    SingleDataResultSet rs4 = SIM_ReportRecycled_SP (connection, session, testkey);
    resultsets.add (rs4);

    return new MultiDataResultSet (resultsets);
  }

  public Float _SIM_ComputeCoverage05_FN (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException {

    Long N = null;
    final String cmd1 = "select count(*) as N "
        + " from testopportunity O  , testopportunityscores S  , testeeattribute A  "
        + " where O._fk_Session = ${session} and O._efk_AdminSubject = ${testkey} "
        + "    and S._fk_TestOpportunity = O._key and S.measureOf = 'OVERALL' and S.measureLabel = 'ThetaScore' "
        + "    and A._fk_TestOpportunity = O._Key and A.TDS_ID = 'OVERALL' and A.context = 'TRUE THETA'";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, true).getResultSets ().next ();
    DbResultRecord rcd1 = rs1.getRecords ().next ();
    if (rcd1 != null) {
      N = rcd1.<Long> get ("N");
    }
    if (DbComparator.lessThan (N, 1))
      return null;

    // Elena:Note that "(S.Value + 0.0)" on varchar column converts it into
    // Float
    // http://blog.nikoroberts.com/post/45834706087/mysql-castconvert-to-float-equivalent
    Long cnt = null;
    final String cmd2 = "select count(*) as cnt"
        + " from testopportunity O , testopportunityscores S  , testeeattribute A  "
        + " where O._fk_Session = ${session} and O._efk_AdminSubject = ${testkey} "
        + "  and S._fk_TestOpportunity = O._key and S.measureOf = 'OVERALL' and S.measureLabel = 'ThetaScore'"
        + "  and A._fk_TestOpportunity = O._Key and A.TDS_ID = 'OVERALL' and A.context = 'TRUE THETA'"
        + " and abs((S.Value + 0.0)  - (A.attributeValue + 0.0)) > (1.96 * S.StandardError)";
    SqlParametersMaps parms2 = parms1;
    SingleDataResultSet rs2 = executeStatement (connection, cmd2, parms2, true).getResultSets ().next ();
    DbResultRecord rcd2 = rs2.getRecords ().next ();
    if (rcd2 != null) {
      cnt = rcd2.<Long> get ("cnt");
    }

    Float coverage = null;
    if (N != null && cnt != null) {
      coverage = (float) N / (float) cnt;
    }
    return coverage;
  }

  // TODO this is a candidate to rewrite without tmp table
  // calling method SIM_ReportSummaryStats_SP will need to change too
  public DataBaseTable _SIM_ComputeBias_FN (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException {

    DataBaseTable resultTbl = getDataBaseTable ("results").addColumn ("bias", SQL_TYPE_To_JAVA_TYPE.FLOAT).
        addColumn ("seBias", SQL_TYPE_To_JAVA_TYPE.FLOAT).addColumn ("biasT", SQL_TYPE_To_JAVA_TYPE.FLOAT);
    connection.createTemporaryTable (resultTbl);

    Long N = null;
    final String cmd1 = "select count(*) as N "
        + " from testopportunity O  , testopportunityscores S  , testeeattribute A  "
        + " where O._fk_Session = ${session} and O._efk_AdminSubject = ${testkey} "
        + "    and S._fk_TestOpportunity = O._key and S.measureOf = 'OVERALL' and S.measureLabel = 'ThetaScore' "
        + "    and A._fk_TestOpportunity = O._Key and A.TDS_ID = 'OVERALL' and A.context = 'TRUE THETA'";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, true).getResultSets ().next ();
    DbResultRecord rcd1 = rs1.getRecords ().next ();
    if (rcd1 != null) {
      N = rcd1.<Long> get ("N");
    }
    if (DbComparator.lessThan (N, 2))
      return resultTbl;

    Float bias = null;
    // @bias = sum(cast(S.value as float) - cast(A.attributeValue as float)) /
    // @N
    final String cmd2 = "select ( sum((S.value+0.0) - (A.attributeValue+0.0)) / ${N}) as bias "
        + " from testopportunity O  , testopportunityscores S  , testeeattribute A  "
        + " where O._fk_Session = ${session} and O._efk_AdminSubject = ${testkey} "
        + "    and S._fk_TestOpportunity = O._key and S.measureOf = 'OVERALL' and S.measureLabel = 'ThetaScore' "
        + "    and A._fk_TestOpportunity = O._Key and A.TDS_ID = 'OVERALL' and A.context = 'TRUE THETA'";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey).put ("N", N);
    SingleDataResultSet rs2 = executeStatement (connection, cmd2, parms2, true).getResultSets ().next ();
    DbResultRecord rcd2 = rs2.getCount () > 0 ? rs2.getRecords ().next () : null;

    if (rcd2 != null) {
      bias = rcd2.<Float> get ("bias");
    }

    Float sumThetaBias = null;
    final String cmd3 = "select (sum( pow((S.value+0.0) - (A.attributeValue+0.0), 2)  - ${bias})) as sumthetabias "
        + " from testopportunity O  , testopportunityscores S  , testeeattribute A  "
        + " where O._fk_Session = ${session} and O._efk_AdminSubject = ${testkey} "
        + "    and S._fk_TestOpportunity = O._key and S.measureOf = 'OVERALL' and S.measureLabel = 'ThetaScore' "
        + "    and A._fk_TestOpportunity = O._Key and A.TDS_ID = 'OVERALL' and A.context = 'TRUE THETA'";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey).
        put ("bias", bias);
    SingleDataResultSet rs3 = executeStatement (connection, cmd3, parms3, true).getResultSets ().next ();
    DbResultRecord rcd3 = rs3.getCount () > 0 ? rs3.getRecords ().next () : null;

    if (rcd3 != null) {
      sumThetaBias = rcd3.<Float> get ("sumthetabias");
    }

    Float sigmaBias = null;
    if (sumThetaBias != null) {
      sigmaBias = (float) Math.sqrt (sumThetaBias / (N - 1));
    }

    Float seBias = null;
    if (sigmaBias != null) {
      seBias = sigmaBias / (float) Math.sqrt (N);
    }

    final String cmd4 = "  insert into ${resultTbl} (bias, seBias, biasT) "
        + " values (${bias}, ${seBias}, ${bias}/${seBias})";
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("resultTbl", resultTbl.getTableName ());
    SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("bias", bias).put ("seBias", seBias);
    executeStatement (connection, fixDataBaseNames (cmd4, unquotedParms), parms4, true);

    return resultTbl;
  }

  public DataBaseTable _SIM_Init2Firstb_Correlation_FN (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException {

    // @correlations table (opportunity int, correlation float, N int, initAvg
    // float, initVar float, firstAvg float, firstVar float, covar float)

    DataBaseTable correlationsTbl = getDataBaseTable ("correlations").addColumn ("opportunity", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("correlation", SQL_TYPE_To_JAVA_TYPE.FLOAT).addColumn ("N", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("initAvg", SQL_TYPE_To_JAVA_TYPE.FLOAT).addColumn ("initVar", SQL_TYPE_To_JAVA_TYPE.FLOAT).
        addColumn ("firstAvg", SQL_TYPE_To_JAVA_TYPE.FLOAT).addColumn ("firstVar", SQL_TYPE_To_JAVA_TYPE.FLOAT).
        addColumn ("covar", SQL_TYPE_To_JAVA_TYPE.FLOAT);
    connection.createTemporaryTable (correlationsTbl);

    DataBaseTable covarsTbl = getDataBaseTable ("covars").addColumn ("opp", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("N", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("initAvg", SQL_TYPE_To_JAVA_TYPE.FLOAT).addColumn ("initVar", SQL_TYPE_To_JAVA_TYPE.FLOAT).
        addColumn ("firstAvg", SQL_TYPE_To_JAVA_TYPE.FLOAT).addColumn ("firstVar", SQL_TYPE_To_JAVA_TYPE.FLOAT).
        addColumn ("covar", SQL_TYPE_To_JAVA_TYPE.FLOAT);
    connection.createTemporaryTable (covarsTbl);

    final String cmd1 = "insert into ${covarsTbl} (opp, N, initAvg, firstAvg) "
        + " select opportunity, count(*), avg(estimate), avg(groupB) "
        + "   from testopportunity O  , testoppabilityestimate A , testeeresponse R  "
        + "   where O._fk_Session = ${session} and O._efk_AdminSUbject = ${testkey} and O.status <> 'paused' "
        + "      and A._fk_TestOpportunity = _Key and A.strand = 'OVERALL' and A.itempos = 0 "
        + "      and R._fk_TestOpportunity = O._Key and R.position = 1 "
        + " group by Opportunity";
    Map<String, String> unquotedParms1 = new HashMap<String, String> ();
    unquotedParms1.put ("covarsTbl", covarsTbl.getTableName ());
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);
    Integer insertedCnt = executeStatement (connection, fixDataBaseNames (cmd1, unquotedParms1), parms1, true).getUpdateCount ();

    Integer N = null;
    Integer opp = 1;
    final String cmd2 = "select bigtoint(max(N)) as N from ${covarsTbl}";
    Map<String, String> unquotedParms2 = unquotedParms1;
    SingleDataResultSet rs2 = executeStatement (connection, fixDataBaseNames (cmd2, unquotedParms2), null, false).getResultSets ().next ();
    DbResultRecord rcd2 = rs2.getCount () > 0 ? rs2.getRecords ().next () : null;
    if (rcd2 != null) {
      N = rcd2.<Integer> get ("N");
    }

    Integer cnt = null;
    Float variance = null;
    Float covar = null;
    while (DbComparator.lessOrEqual (opp, N)) {
      final String cmd3 = "select bigtoint(count(*)) as cnt "
          + " from testopportunity, testoppabilityestimate, ${covarsTbl} I "
          + " where _fk_Session = ${session} and _efk_AdminSubject = ${testkey}"
          + "    and Opportunity = ${opp} and status <> 'paused' "
          + "    and _fk_TestOpportunity = _Key and opp = ${opp} and strand = 'OVERALL' and itempos = 0";
      SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey).put ("opp", opp);
      Map<String, String> unquotedParms3 = unquotedParms1;
      SingleDataResultSet rs3 = executeStatement (connection, fixDataBaseNames (cmd3, unquotedParms3), parms3, true).getResultSets ().next ();
      DbResultRecord rcd3 = rs3.getRecords ().next ();
      if (rcd3 != null) {
        cnt = rcd3.<Integer> get ("cnt");
      }
      if (DbComparator.lessThan (cnt, 2)) {
        opp++;
        continue;
      }

      final String cmd4 = "select sum(pow(estimate - I.initAvg, 2)) / ((count(*) - 1)) as variance "
          + " from testopportunity, testoppabilityestimate, ${covarsTbl} I "
          + "   where _fk_Session = ${session} and _efk_AdminSubject = ${testkey} "
          + "   and Opportunity = ${opp} and status <> 'paused' "
          + "   and _fk_TestOpportunity = _Key and opp = ${opp} and strand = 'OVERALL' and itempos = 0";
      SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey).put ("opp", opp);
      Map<String, String> unquotedParms4 = unquotedParms1;
      SingleDataResultSet rs4 = executeStatement (connection, fixDataBaseNames (cmd4, unquotedParms4), parms4, true).getResultSets ().next ();
      DbResultRecord rcd4 = rs4.getCount () > 0 ? rs4.getRecords ().next () : null;
      if (rcd4 != null) {
        variance = rcd4.<Float> get ("variance");
      }

      final String cmd5 = "update ${covarsTbl} set initVar = ${variance} where Opp = ${opp}";
      Map<String, String> unquotedParms5 = unquotedParms1;
      SqlParametersMaps parms5 = (new SqlParametersMaps ()).put ("variance", variance).put ("opp", opp);
      Integer updatedCnt = executeStatement (connection, fixDataBaseNames (cmd5, unquotedParms5), parms5, false).getUpdateCount ();

      final String cmd6 = "select bigtoint(count(*)) as cnt "
          + " from testopportunity O , testeeresponse R, ${covarsTbl} F "
          + "  where O._fk_session = ${session} and O._efk_AdminSUbject = ${testkey} "
          + "   and O.Opportunity = ${opp} and O.status <> 'paused' "
          + "   and R._fk_TestOpportunity = O._Key and position = 1 and Opp = ${opp}";
      SqlParametersMaps parms6 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey).put ("opp", opp);
      Map<String, String> unquotedParms6 = unquotedParms1;
      SingleDataResultSet rs6 = executeStatement (connection, fixDataBaseNames (cmd6, unquotedParms6), parms6, true).getResultSets ().next ();
      DbResultRecord rcd6 = rs6.getRecords ().next ();
      if (rcd6 != null) {
        cnt = rcd6.<Integer> get ("cnt");
      }
      if (DbComparator.lessThan (cnt, 2)) {
        opp++;
        continue;
      }

      final String cmd7 = "select  sum(pow(groupB - firstAvg, 2)) / ((count(*) - 1)) as variance "
          + " from testopportunity O, testeeresponse R, ${covarsTbl} F "
          + " where O._fk_session = ${session} and O._efk_AdminSUbject = ${testkey} "
          + "    and O.Opportunity = ${opp} and O.status <> 'paused' "
          + "    and R._fk_TestOpportunity = O._Key and position = 1 and Opp = ${opp}";
      SqlParametersMaps parms7 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey).put ("opp", opp);
      Map<String, String> unquotedParms7 = unquotedParms1;
      SingleDataResultSet rs7 = executeStatement (connection, fixDataBaseNames (cmd7, unquotedParms7), parms7, true).getResultSets ().next ();
      DbResultRecord rcd7 = rs7.getCount () > 0 ? rs7.getRecords ().next () : null;
      if (rcd7 != null) {
        variance = rcd7.<Float> get ("variance");
      }

      final String cmd8 = "update ${covarsTbl} set firstVar = ${variance} where Opp = ${opp}";
      Map<String, String> unquotedParms8 = unquotedParms1;
      SqlParametersMaps parms8 = (new SqlParametersMaps ()).put ("variance", variance).put ("opp", opp);
      updatedCnt = executeStatement (connection, fixDataBaseNames (cmd8, unquotedParms8), parms8, false).getUpdateCount ();

      final String cmd9 = "select bigtoint(count(*)) as cnt "
          + " from testopportunity O, testoppabilityestimate E, testeeresponse R, ${covarsTbl} "
          + "   where O._fk_Session = ${session} and _efk_AdminSUbject = ${testkey}  and O.status <> 'paused' "
          + "    and E._fk_TestOpportunity = _Key and E.strand = 'OVERALL' and E.itempos = 0 "
          + "    and R._fk_TestOpportunity = O._Key and R.position = 1 and O.Opportunity = ${opp}";
      SqlParametersMaps parms9 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey).put ("opp", opp);
      Map<String, String> unquotedParms9 = unquotedParms1;
      SingleDataResultSet rs9 = executeStatement (connection, fixDataBaseNames (cmd9, unquotedParms9), parms9, true).getResultSets ().next ();
      DbResultRecord rcd9 = rs9.getRecords ().next ();
      if (rcd9 != null) {
        cnt = rcd9.<Integer> get ("cnt");
      }
      if (DbComparator.lessThan (cnt, 2)) {
        opp++;
        continue;
      }

      final String cmd10 = "select sum( (estimate - initAvg) * (R.groupB - firstAvg)) / ((count(*) - 1)) as covar "
          + " from testopportunity O, testoppabilityestimate E, testeeresponse R, ${covarsTbl} "
          + "  where O._fk_Session = ${session} and _efk_AdminSUbject = ${testkey}  and O.status <> 'paused' "
          + "    and E._fk_TestOpportunity = _Key and E.strand = 'OVERALL' and E.itempos = 0 "
          + "    and R._fk_TestOpportunity = O._Key and R.position = 1 "
          + "    and O.Opportunity = ${opp}";
      SqlParametersMaps parms10 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey).put ("opp", opp);
      Map<String, String> unquotedParms10 = unquotedParms1;
      SingleDataResultSet rs10 = executeStatement (connection, fixDataBaseNames (cmd10, unquotedParms10), parms10, true).getResultSets ().next ();
      DbResultRecord rcd10 = rs10.getCount () > 0 ? rs10.getRecords ().next () : null;
      if (rcd10 != null) {
        covar = rcd10.<Float> get ("covar");
      }

      final String cmd11 = "update ${covarsTbl} set covar = ${covar} where Opp = ${opp}";
      Map<String, String> unquotedParms11 = unquotedParms1;
      SqlParametersMaps parms11 = (new SqlParametersMaps ()).put ("covar", covar).put ("opp", opp);
      updatedCnt = executeStatement (connection, fixDataBaseNames (cmd11, unquotedParms11), parms11, false).getUpdateCount ();

      opp++;
    }
    final String cmd12 = "insert into ${correlationsTbl} "
        + " (opportunity, correlation, N , initAvg , initVar , firstAvg , firstVar , covar ) "
        + " select opp,  covar / sqrt(initVar * firstVar) , N, initAvg, initVar, firstAvg, firstVar, covar "
        + "  from ${covarsTbl} C where covar is not null and initVar * firstVar > 0";
    Map<String, String> unquotedParms12 = new HashMap<> ();
    unquotedParms12.put ("correlationsTbl", correlationsTbl.getTableName ());
    unquotedParms12.put ("covarsTbl", covarsTbl.getTableName ());
    insertedCnt = executeStatement (connection, fixDataBaseNames (cmd12, unquotedParms12), null, false).getUpdateCount ();

    return correlationsTbl;
  }

  public MultiDataResultSet SIM_ReportSummaryStats_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException {

    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();
    try {
      String algorithm = null;
      Boolean segmented = null;
      Integer opItems = null;
      final String cmd1 = "select selectionalgorithm as algorithm,  isSegmented as segmented, minItems as opItems "
          + " from ${ItembankDB}.tblsetofadminsubjects  where _Key = ${testkey}";
      SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("testkey", testkey);
      SingleDataResultSet rs1 = executeStatement (connection, fixDataBaseNames (cmd1), parms1, true).getResultSets ().next ();
      DbResultRecord rcd1 = rs1.getCount () > 0 ? rs1.getRecords ().next () : null;
      if (rcd1 != null) {
        algorithm = rcd1.<String> get ("algorithm");
        segmented = rcd1.<Boolean> get ("segmented");
        opItems = rcd1.<Integer> get ("opItems");
      }
      if (DbComparator.isEqual (algorithm, "fixedform")) {
        resultsets.add (_commonDll.ReturnStatusReason ("failed", "Report not applicable to fixed form tests"));
        return new MultiDataResultSet (resultsets);
      }

      final String cmd2 = "select virtualtest from ${ItembankDB}.tblsetofadminsubjects where VirtualTest = ${testkey}"
          + " and selectionalgorithm like 'adaptive%' and minItems > 0";
      SqlParametersMaps parms2 = parms1;
      if (DbComparator.isEqual (segmented, true) &&
          !exists (executeStatement (connection, fixDataBaseNames (cmd2), parms2, false))) {
        resultsets.add (_commonDll.ReturnStatusReason ("failed", "No segment applicable to this report"));
        return new MultiDataResultSet (resultsets);
      }

      if (DbComparator.lessThan (opItems, 1)) {
        resultsets.add (_commonDll.ReturnStatusReason ("failed", "Report not applicable to independent field tests"));
        return new MultiDataResultSet (resultsets);
      }

      final String cmd3 = "select _fk_session from testopportunity where _fk_Session = ${session} "
          + " and _efk_AdminSubject = ${testkey} and dateCompleted is not null";
      SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("testkey", testkey).put ("session", session);
      if (!exists (executeStatement (connection, cmd3, parms3, false))) {
        resultsets.add (_commonDll.ReturnStatusReason ("failed", "No opportunities have completed"));
        return new MultiDataResultSet (resultsets);
      }

      Float coverage = _SIM_ComputeCoverage05_FN (connection, session, testkey);
      DataBaseTable resultsTbl = _SIM_ComputeBias_FN (connection, session, testkey);

      final String cmd4 = "select B.* from ${resultsTbl} B";
      SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("coverage", coverage);
      Map<String, String> unquotedParms4 = new HashMap<> ();
      unquotedParms4.put ("resultsTbl", resultsTbl.getTableName ());
      SingleDataResultSet rs4 = executeStatement (connection, fixDataBaseNames (cmd4, unquotedParms4), parms4, false).getResultSets ().next ();
      rs4.addColumn ("coverage05", SQL_TYPE_To_JAVA_TYPE.FLOAT);
      Iterator<DbResultRecord> iter = rs4.getRecords ();
      while (iter.hasNext ()) {
        DbResultRecord rcd4 = iter.next ();
        rcd4.addColumnValue ("coverage05", coverage);
      }
      resultsets.add (rs4);
      connection.dropTemporaryTable (resultsTbl);

      // select Opportunity, N, Correlation from
      // dbo._SIM_Init2Firstb_Correlation(@session, @testkey);
      DataBaseTable correlationTbl = _SIM_Init2Firstb_Correlation_FN (connection, session, testkey);

      final String cmd5 = "select Opportunity, N, Correlation from ${correlationTbl}";
      Map<String, String> unquotedParms5 = new HashMap<> ();
      unquotedParms5.put ("correlationTbl", correlationTbl.getTableName ());
      SingleDataResultSet rs5 = executeStatement (connection, fixDataBaseNames (cmd5, unquotedParms5), null, false).getResultSets ().next ();

      resultsets.add (rs5);
      connection.dropTemporaryTable (correlationTbl);

      return new MultiDataResultSet (resultsets);

    } catch (ReturnStatusException re) {

      StackTraceElement[] stackTrace = re.getStackTrace ();
      String error = String.format ("%s: %s", stackTrace[0].toString (), re.getMessage ());

      resultsets.add (_commonDll.ReturnStatusReason ("failed", error));
      return new MultiDataResultSet (resultsets);
    }
  }

  public MultiDataResultSet SIM_ReportScores_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException {

    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();
    final String cmd1 = "select _efk_Testee as student, opportunity, measureOf, measureLabel, value, standardError "
        + " from testopportunity  , testopportunityscores  "
        + "   where _fk_Session = ${session} and _efk_AdminSubject = ${testkey} and _Key = _fk_TestOpportunity "
        + " order by _efk_Testee, opportunity, measureOf, measureLabel";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);

    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, true).getResultSets ().next ();
    resultsets.add (rs1);
    return new MultiDataResultSet (resultsets);

  }

  public MultiDataResultSet SIM_ReportFormDistributions_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException {

    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();

    final String cmd1 = "select segmentID, formID, formkey, bigtoint(count(*)) as numStudents "
        + " from testopportunity,   testopportunitysegment "
        + "   where _fk_Session = ${session} and _efk_AdminSubject = ${testkey} and _fk_TestOpportunity = _key "
        + " group by segmentID, formID, formKey "
        + " order by segmentID, formID";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);

    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();

    resultsets.add (rs1);
    return new MultiDataResultSet (resultsets);
  }

  public MultiDataResultSet SIM_FieldtestDistribution_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException {

    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();
    final String cmd1 = "select segmentID, groupID, _efk_ItemKey as itemKey, bigtoint(count(*)) as sampleSize "
        + " from testeeresponse, testopportunity O "
        + "    where O._fk_Session = ${session} and _fk_TestOpportunity = _Key "
        + "    and O._efk_AdminSubject = ${testkey} and isFieldTest = 1 "
        + " group by segmentID, groupID, _efk_ItemKey "
        + " order by segmentID, groupID, _efk_ItemKey ";

    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);

    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();
    resultsets.add (rs1);
    return new MultiDataResultSet (resultsets);
  }

  public SingleDataResultSet SIM_ChangeSegmentNonReportingCategoryAsReportingCategory_SP (
      SQLConnection connection,
      UUID session,
      String testkey,
      String segmentKey,
      String contentLevel) throws ReturnStatusException {

    String client = getClientnameFromSession (connection, session);
    if (!testOpportunityExists (connection, session, testkey)) {
      // if dbo.udfIsStrand(@contentLevel) = 1
      final String cmd1 = "SELECT _key FROM ${ItembankDB}.tblstrand WHERE _Key = ${contentLevel} AND (_fk_Parent IS NULL OR TreeLevel = 1) limit 1";
      SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("contentLevel", contentLevel);

      if (exists (executeStatement (connection, fixDataBaseNames (cmd1), parms1, false))) {
        return _commonDll._ReturnError_SP (connection, client, "SIM_ChangeSegmentNonReportingCategoryAsReportingCategory", "Strand should not be modified");

      } else {
        final String cmd2 = "UPDATE sim_segmentcontentlevel "
            + " SET  startAbility = 0  "
            + " WHERE _fk_Session = ${session} and _efk_Segment = ${segmentKey} and contentLevel = ${contentLevel}";
        SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("contentLevel", contentLevel).
            put ("session", session).put ("segmentKey", segmentKey);
        Integer updatedCnt = executeStatement (connection, cmd2, parms2, false).getUpdateCount ();
        return _commonDll.ReturnStatusReason ("success", null);
      }
    }

    return _commonDll._ReturnError_SP (connection, client, "SIM_ChangeSegmentNonReportingCategoryAsReportingCategory",
        "Opportunity data already exist on this test within this session");
  }

  public SingleDataResultSet SIM_ChangeSegmentReportingCategoryAsNonReportingCategory_SP (
      SQLConnection connection,
      UUID session,
      String testkey,
      String segmentKey,
      String strand) throws ReturnStatusException {

    String client = getClientnameFromSession (connection, session);
    if (!testOpportunityExists (connection, session, testkey)) {
      // if dbo.udfIsStrand(@contentLevel) = 1
      final String cmd1 = "SELECT _key FROM ${ItembankDB}.tblstrand WHERE _Key = ${strand} AND (_fk_Parent IS NULL OR TreeLevel = 1) limit 1";
      SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("strand", strand);

      if (exists (executeStatement (connection, fixDataBaseNames (cmd1), parms1, false))) {
        return _commonDll._ReturnError_SP (connection, client, "SIM_ChangeSegmentReportingCategoryAsNonReportingCategory",
            "Strand cannot be changed as non-reporting category");

      } else {
        final String cmd2 = "UPDATE sim_segmentcontentlevel "
            + " SET  startAbility = null  "
            + " WHERE _fk_Session = ${session} and _efk_Segment = ${segmentKey} and contentLevel = ${strand}";
        SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("strand", strand).
            put ("session", session).put ("segmentKey", segmentKey);
        Integer updatedCnt = executeStatement (connection, cmd2, parms2, false).getUpdateCount ();
        return _commonDll.ReturnStatusReason ("success", null);
      }
    }

    return _commonDll._ReturnError_SP (connection, client, "SIM_ChangeSegmentReportingCategoryAsNonReportingCategory",
        "Opportunity data already exist on this test within this session");
  }

  public SingleDataResultSet SIM_GetErrors_SP (SQLConnection connection, UUID session) throws ReturnStatusException {
    Date starttime = null;

    final String cmd1 = " select sim_start as starttime from session where _Key = ${session}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();
    DbResultRecord rcd1 = rs1.getCount () > 0 ? rs1.getRecords ().next () : null;
    if (rcd1 != null) {
      starttime = rcd1.<Date> get ("starttime");
    }

    final String cmd2 = "select procname, bigtoint(count(*)) as numErrors "
        + " from ${ArchiveDB}.systemerrors E "
        + "   where _fk_Session = ${session} and dateRecorded >= ${starttime} "
        + "group by procname";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("session", session).put ("starttime", starttime);
    SingleDataResultSet rs2 = executeStatement (connection, fixDataBaseNames (cmd2), parms2, false).getResultSets ().next ();

    return rs2;
  }

  public MultiDataResultSet SIM_ReportOpportunities_SP (SQLConnection connection, UUID session, String testKey) throws ReturnStatusException {

    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();

    DataBaseTable oppsTbl = getDataBaseTable ("opps").addColumn ("oppkey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER).
        addColumn ("student", SQL_TYPE_To_JAVA_TYPE.BIGINT).addColumn ("opportunity", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("initialAbility", SQL_TYPE_To_JAVA_TYPE.FLOAT).
        addColumn ("historyAbility", SQL_TYPE_To_JAVA_TYPE.BIT).addColumn ("firstGroup", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).
        addColumn ("firstB", SQL_TYPE_To_JAVA_TYPE.FLOAT).addColumn ("trueTheta", SQL_TYPE_To_JAVA_TYPE.FLOAT).
        addColumn ("thetaScore", SQL_TYPE_To_JAVA_TYPE.FLOAT).addColumn ("thetaSE", SQL_TYPE_To_JAVA_TYPE.FLOAT).
        addColumn ("scaledScore", SQL_TYPE_To_JAVA_TYPE.FLOAT).addColumn ("scaledSE", SQL_TYPE_To_JAVA_TYPE.FLOAT).
        addColumn ("performanceLevel", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("rawScore", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("itemCount", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("itemCountScored", SQL_TYPE_To_JAVA_TYPE.INT);

    connection.createTemporaryTable (oppsTbl);
    final String cmd1 = "insert into ${oppsTbl} (oppkey, student, opportunity, status, "
        + "     initialAbility, firstGroup, firstB, trueTheta, historyAbility) "
        + " select distinct _Key, _efk_Testee , Opportunity, Status, Estimate , GroupID , GroupB , AttributeValue, 0 "
        + "   from testopportunity O, testeeresponse R , testoppabilityestimate E  , testeeattribute A "
        + "   where O._fk_session = ${session} and O._efk_AdminSubject = ${testkey} and O.status <> 'paused' "
        + "     and R._fk_TestOPportunity = O._Key and E._fk_TestOpportunity = O._Key and A._fk_TestOPportunity = O._Key "
        + "     and R.position = 1 and E.itemPos = 0 and E.strand = 'OVERALL' "
        + "  and A.Context = 'TRUE THETA' and A.TDS_ID = 'OVERALL'";
    SqlParametersMaps parm1 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testKey);
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("oppsTbl", oppsTbl.getTableName ());

    Integer insertedCnt = executeStatement (connection, fixDataBaseNames (cmd1, unquotedParms), parm1, true).getUpdateCount ();

    final String SQL_INDEX = "create index _ix_opps on ${oppsTbl} (oppkey);";
    executeStatement (connection, fixDataBaseNames (SQL_INDEX, unquotedParms), null, false);

    final String cmd2 = " update ${oppsTbl} O, testeeattribute T "
        + " set O.initialAbility = (T.attributeValue+0.0), O.historyAbility = 1 "
        + "    where T._fk_TestOPportunity = O.oppkey and T.TDS_ID = 'INITIALABILITY'";
    Integer updatedCnt = executeStatement (connection, fixDataBaseNames (cmd2, unquotedParms), null, true).getUpdateCount ();

    final String cmd3 = "update ${oppsTbl} O, testopportunityscores T "
        + " set O.thetaScore = (T.value+0.0), thetaSE = standardError "
        + "   where T._fk_TestOPportunity = O.oppkey and T.MeasureOf = 'OVERALL' and T.measureLabel = 'ThetaScore'";
    updatedCnt = executeStatement (connection, fixDataBaseNames (cmd3, unquotedParms), null, true).getUpdateCount ();

    final String cmd4 = "update ${oppsTbl} O, TestOpportunityScores T "
        + " set O.scaledScore = (T.value +0.0), scaledSE = standardError "
        + "    where T._fk_TestOPportunity = O.oppkey and T.MeasureOf = 'OVERALL' and T.measureLabel = 'ScaleScore'";
    updatedCnt = executeStatement (connection, fixDataBaseNames (cmd4, unquotedParms), null, true).getUpdateCount ();

    final String cmd5 = "update ${oppsTbl} O, testopportunityscores T "
        + " set O.performanceLevel = cast(T.value as SIGNED) "
        + "   where _fk_TestOPportunity = oppkey and MeasureOf = 'OVERALL' and measureLabel = 'PerformanceLevel'";
    updatedCnt = executeStatement (connection, fixDataBaseNames (cmd5, unquotedParms), null, true).getUpdateCount ();

    final String cmd6 = "update ${oppsTbl} O,  TestOpportunityScores T "
        + " set O.rawScore = cast(T.value as SIGNED) "
        + "   where _fk_TestOPportunity = oppkey and MeasureOf = 'OVERALL' and measureLabel = 'RawScore'";
    updatedCnt = executeStatement (connection, fixDataBaseNames (cmd6, unquotedParms), null, true).getUpdateCount ();

    final String cmd7 = "update ${oppsTbl} O,  TestOpportunityScores T "
        + " set O.itemCount = cast(T.value as SIGNED) "
        + "   where _fk_TestOPportunity = oppkey and MeasureOf = 'OVERALL' and measureLabel = 'ItemCount'";
    updatedCnt = executeStatement (connection, fixDataBaseNames (cmd7, unquotedParms), null, true).getUpdateCount ();

    final String cmd8 = "update ${oppsTbl} O,  TestOpportunityScores T "
        + " set O.itemCountScored = cast(T.value as SIGNED) "
        + "   where _fk_TestOPportunity = oppkey and MeasureOf = 'OVERALL' and measureLabel = 'ItemCountScored'";
    updatedCnt = executeStatement (connection, fixDataBaseNames (cmd8, unquotedParms), null, true).getUpdateCount ();

    DataBaseTable aggregatesTbl = getDataBaseTable ("aggregates").addColumn ("opkey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER).
        addColumn ("groups", SQL_TYPE_To_JAVA_TYPE.INT).addColumn ("items", SQL_TYPE_To_JAVA_TYPE.INT).
        addColumn ("ssq", SQL_TYPE_To_JAVA_TYPE.FLOAT).addColumn ("meanDif", SQL_TYPE_To_JAVA_TYPE.FLOAT).
        addColumn ("varDif", SQL_TYPE_To_JAVA_TYPE.FLOAT);
    connection.createTemporaryTable (aggregatesTbl);

    final String cmd9 = "insert into ${aggregatesTbl} (opkey, groups, items, ssq, meanDif) "
        + "   select oppkey, count(distinct groupID), count(*), sum(power(itemB, 2)), avg(itemB) "
        + "   from ${oppsTbl}, testeeresponse   where _fk_TestOpportunity = oppkey and isFieldTest = 0 "
        + "group by oppkey";
    Map<String, String> unquotedParms2 = new HashMap<String, String> ();
    unquotedParms2.put ("oppsTbl", oppsTbl.getTableName ());
    unquotedParms2.put ("aggregatesTbl", aggregatesTbl.getTableName ());
    insertedCnt = executeStatement (connection, fixDataBaseNames (cmd9, unquotedParms2), null, true).getUpdateCount ();

    final String SQL_INDEX3 = "create index _ix_aggx on ${aggregatesTbl} (opkey)";
    Map<String, String> unquotedParms3 = new HashMap<String, String> ();
    unquotedParms3.put ("aggregatesTbl", aggregatesTbl.getTableName ());
    executeStatement (connection, fixDataBaseNames (SQL_INDEX3, unquotedParms3), null, false);

    final String cmd10 = "update ${aggregatesTbl} set varDif = (ssq / items)  - power(meanDif, 2)";
    updatedCnt = executeStatement (connection, fixDataBaseNames (cmd10, unquotedParms3), null, false).getUpdateCount ();

    final String cmd11 = "select student, opportunity, status, historyAbility, "
        + "   initialAbility, trueTheta,  thetaScore, thetaSE, scaledScore, "
        + "   scaledSE, performanceLevel, itemCount, itemCountScored, rawScore, "
        + "   firstGroup, firstB,  groups, items, meanDif, varDif "
        + " from ${oppsTbl}, ${aggregatesTbl} where oppkey = opkey "
        + " order by student, opportunity";

    SingleDataResultSet rs11 = executeStatement (connection, fixDataBaseNames (cmd11, unquotedParms2), null, false).getResultSets ().next ();

    connection.dropTemporaryTable (aggregatesTbl);
    connection.dropTemporaryTable (oppsTbl);

    resultsets.add (rs11);
    return new MultiDataResultSet (resultsets);
  }

  public Integer sim_setSimulationRunProps (SQLConnection connection, UUID session, String status) throws ReturnStatusException {

    final String cmd = "UPDATE session SET sim_status = ${status}, sim_start = now(3), sim_stop = now(3) WHERE _Key = ${session}";
    SqlParametersMaps parms = (new SqlParametersMaps ()).put ("status", status).put ("session", session);
    Integer updatedCnt = executeStatement (connection, cmd, parms, false).getUpdateCount ();
    return updatedCnt;
  }

  public Integer sim_setSimulationErrorProps (SQLConnection connection, UUID session, String status) throws ReturnStatusException {

    final String cmd = "UPDATE session SET sim_status = ${status}, sim_stop = now(3) WHERE _Key = ${session}";
    SqlParametersMaps parms = (new SqlParametersMaps ()).put ("status", status).put ("session", session);
    Integer updatedCnt = executeStatement (connection, cmd, parms, false).getUpdateCount ();
    return updatedCnt;
  }

  public String sim_GetSimStatus (SQLConnection connection, UUID session) throws ReturnStatusException {

    String sim_status = null;
    final String cmd = "SELECT sim_status FROM session WHERE _Key = ${session}";
    SqlParametersMaps parms = (new SqlParametersMaps ()).put ("session", session);
    SingleDataResultSet rs = executeStatement (connection, cmd, parms, false).getResultSets ().next ();
    DbResultRecord rcd = rs.getCount () > 0 ? rs.getRecords ().next () : null;
    if (rcd != null) {
      sim_status = rcd.<String> get ("sim_status");
    }
    return sim_status;
  }

  public MultiDataResultSet SIM_GetTestControls2_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException {

    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();

    final String cmd1 = "select coalesce(N.sim_language, 'ENU') as language, "
        + " S.iterations, S.opportunities, S.meanProficiency, S.sdProficiency, "
        + " S.strandCorrelation, S.handScoreItemTypes, "
        + " M.dbname as itembank, sim_threads as threads, sim_thinkTime as thinkTime, "
        + " (select bigtoint(count(*)) from testopportunity O "
        + "    where O._fk_Session = ${sessionKey} and O._efk_AdminSubject = S._efk_AdminSubject "
        + "      and dateCompleted is not null) "
        + "  as simulations "
        + "from session N, sessiontests S, _synonyms M "
        + " where N._Key = ${sessionKey} and s._fk_Session = ${sessionKey} and _efk_AdminSubject = ${testkey} "
        + "  and M.prefix = 'ITEMBANK_'";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("sessionkey", session).put ("testkey", testkey);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();

    resultsets.add (rs1);

    final String cmd2 = "select distinct contentLevel as strand "
        + "from sim_segmentcontentlevel L, sim_segment S "
        + "  where S._fk_Session = ${sessionKey} and L._fk_Session = ${sessionKey} "
        + "  and S._efk_AdminSubject = ${testkey} and S._efk_Segment = L._efk_Segment "
        + "  and L.StartAbility is not null";
    SqlParametersMaps parms2 = parms1;
    SingleDataResultSet rs2 = executeStatement (connection, cmd2, parms2, false).getResultSets ().next ();

    resultsets.add (rs2);

    return new MultiDataResultSet (resultsets);
  }

  public SingleDataResultSet sim_getItemTypes (SQLConnection connection, String adminSubject) throws ReturnStatusException {
    final String cmd1 = "SELECT DISTINCT (ItemType) FROM ${ItembankDB}.tblsetofadminsubjects SAS "
        + " JOIN ${ItembankDB}.tblsetofadminitems SAI ON SAI._fk_AdminSubject = SAS._Key "
        + " JOIN ${ItembankDB}.tblitem IB ON IB._Key = SAI._fk_Item  "
        + "   WHERE IFNULL(SAS.VirtualTest, SAS._Key) = ${adminSubject}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("adminSubject", adminSubject);
    SingleDataResultSet rs = executeStatement (connection, fixDataBaseNames (cmd1), parms1, false).getResultSets ().next ();
    return rs;
  }

  public void SIM_LogError_SP (SQLConnection connection, UUID session, String clientname,
      String procname, String oppkey, String testkey, String msg) throws ReturnStatusException {

    UUID opp = null;
    try {
      opp = UUID.fromString (oppkey);
    } catch (IllegalArgumentException e) {
    }
    // TODO Does simulator has TdsSettings autowired?
    String sessionDB = getTdsSettings ().getTDSSessionDBName ();
    final String cmd1 = "insert into ${ArchiveDB}.systemerrors (application, procname, clientname,"
        + " _fk_Session, _fk_TestOpportunity, errorMessage, datarecorded, dbname) "
        + " values ('Simulator', ${procname}, ${clientname}, ${session}, ${opp}, ${msg}, now(3), ${dbname})";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("procname", procname).put ("clientname", clientname)
        .put ("session", session).put ("opp", opp).put ("msg", msg).put ("dbname", sessionDB);
    int insertedCnt = executeStatement (connection, fixDataBaseNames (cmd1), parms1, false).getUpdateCount ();
  }

  public void SIM_LogSessionError_SP (SQLConnection connection, UUID session, String clientname,
      String procname, String testkey, String msg) throws ReturnStatusException {

    // TODO Does simulator has TdsSettings autowired?
    String sessionDB = getTdsSettings ().getTDSSessionDBName ();
    final String cmd1 = "insert into ${ArchiveDB}.systemerrors (application, procname, clientname,"
        + " _fk_Session,  errorMessage, datarecorded, dbname) "
        + " values ('Simulator', ${procname}, ${clientname}, ${session},  ${msg}, now(3), ${dbname})";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("procname", procname).put ("clientname", clientname)
        .put ("session", session).put ("msg", msg).put ("dbname", sessionDB);
    int insertedCnt = executeStatement (connection, fixDataBaseNames (cmd1), parms1, false).getUpdateCount ();
  }

  private void insertIntoItemsTbl (SQLConnection connection, String cmd, DataBaseTable itemsTbl, UUID session) throws ReturnStatusException {

    SqlParametersMaps parms = (new SqlParametersMaps ()).put ("session", session);
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("itemsTbl", itemsTbl.getTableName ());
    Integer insertedCnt = executeStatement (connection, fixDataBaseNames (cmd, unquotedParms), parms, false).getUpdateCount ();
  }

  private Boolean existsInItemsTbl (SQLConnection connection, DataBaseTable itemsTbl) throws ReturnStatusException {
    final String cmd1 = "select test from ${itemsTbl} limit 1";
    Map<String, String> unquotedParms1 = new HashMap<String, String> ();
    unquotedParms1.put ("itemsTbl", itemsTbl.getTableName ());

    Boolean exists = exists (executeStatement (connection, fixDataBaseNames (cmd1, unquotedParms1), null, false));
    return exists;
  }

  private Boolean existsCntInItemsTbl (SQLConnection connection, DataBaseTable itemsTbl) throws ReturnStatusException {
    final String cmd1 = "select test from ${itemsTbl} where cnt < req limit 1";
    Map<String, String> unquotedParms1 = new HashMap<String, String> ();
    unquotedParms1.put ("itemsTbl", itemsTbl.getTableName ());

    Boolean exists = exists (executeStatement (connection, fixDataBaseNames (cmd1, unquotedParms1), null, false));
    return exists;
  }

  private Boolean existsCntGrZeroInItemsTbl (SQLConnection connection, DataBaseTable itemsTbl) throws ReturnStatusException {
    final String cmd1 = "select test from ${itemsTbl} where cnt > 0 limit 1";
    Map<String, String> unquotedParms1 = new HashMap<String, String> ();
    unquotedParms1.put ("itemsTbl", itemsTbl.getTableName ());

    Boolean exists = exists (executeStatement (connection, fixDataBaseNames (cmd1, unquotedParms1), null, false));
    return exists;
  }

  private Integer insertIntoErrorsTbl (SQLConnection connection, String cmd, DataBaseTable errorsTbl, DataBaseTable itemsTbl) throws ReturnStatusException {

    final String cmd1 = "insert into ${errorsTbl} (severity, test, err) "
        + " select ${severity}, test, ${err} from ${itemsTbl}";

    Map<String, String> unquotedParms1 = new HashMap<String, String> ();
    unquotedParms1.put ("itemsTbl", itemsTbl.getTableName ());
    unquotedParms1.put ("errorsTbl", errorsTbl.getTableName ());

    Integer cnt = executeStatement (connection, fixDataBaseNames (cmd1, unquotedParms1), null, false).getUpdateCount ();
    return cnt;
  }

  private void deleteFromItemsTbl (SQLConnection connection, DataBaseTable itemsTbl) throws ReturnStatusException {

    final String cmd1 = "delete from ${itemsTbl}";
    Map<String, String> unquotedParms1 = new HashMap<String, String> ();
    unquotedParms1.put ("itemsTbl", itemsTbl.getTableName ());
    executeStatement (connection, fixDataBaseNames (cmd1, unquotedParms1), null, false);
  }

  public SingleDataResultSet SIM_OpenSession_SP (SQLConnection connection, UUID sessionkey) throws ReturnStatusException {

    Date startDate = _dateUtil.getDateWRetStatus (connection);
    Calendar cal = Calendar.getInstance ();
    cal.setTime (startDate);
    cal.add (Calendar.MONTH, 1);
    Date endDate = cal.getTime ();
    Timestamp startTime = new Timestamp (startDate.getTime ());
    Timestamp endTime = new Timestamp (endDate.getTime ());

    final String cmd1 = "   update session set status = 'open', dateend = ${endDate}, "
        + " datevisited = ${startDate}, datechanged = ${startDate} "
        + "   where _key = ${sessionKey} and environment = 'SIMULATION'";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("sessionkey", sessionkey).put ("startDate", startTime).put ("endDate", endTime);
    int updatedCnt = executeStatement (connection, cmd1, parms1, false).getUpdateCount ();

    final String cmd2 = "  select dbname as itembank, clientname, sessionID, _efk_Proctor as proctorKey, "
        + " _fk_Browser as browserKey, sim_language as language, sim_proctorDelay as proctorDelay "
        + " from session, _synonyms "
        + " where _key = ${sessionKey} and environment = 'SIMULATION' and prefix = 'ITEMBANK_'";
    SqlParametersMaps parms2 = parms1;
    SingleDataResultSet rs = executeStatement (connection, cmd2, parms2, false).getResultSets ().next ();
    return rs;
  }

  public void SIM_EndSimulation (SQLConnection connection, UUID session, String status) throws ReturnStatusException {

    final String cmd1 = " update session set sim_status = ${status}, sim_stop = now(3) where _Key = ${session}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("status", status);
    int updatedCnt = executeStatement (connection, cmd1, parms1, false).getUpdateCount ();
  }

  public void SIM_CleanupSessionTest (SQLConnection connection, UUID session, String testKey) throws ReturnStatusException {
    DataBaseTable oppsTbl = getDataBaseTable ("opps").addColumn ("oppkey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER).
        addColumn ("oppnum", SQL_TYPE_To_JAVA_TYPE.INT);
    connection.createTemporaryTable (oppsTbl);

    final String SQL_INDEX = "create index opp_idx on ${oppsTbl} (oppkey)";
    Map<String, String> unquotedparms = new HashMap<String, String> ();
    unquotedparms.put ("oppsTbl", oppsTbl.getTableName ());
    executeStatement (connection, fixDataBaseNames (SQL_INDEX, unquotedparms), null, false);

    final String cmd1 = "insert  IGNORE into ${oppsTbl} (oppkey, oppnum) "
        + " select _Key, opportunity from testopportunity "
        + " where _fk_Session = ${session} and _efk_AdminSUbject = ${testkey} and datecompleted is null";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testKey);
    int insertedCnt = executeStatement (connection, fixDataBaseNames (cmd1, unquotedparms), parms1, false).getUpdateCount ();

    Integer oppcnt = null;
    final String cmd2 = "select opportunities as oppcnt from sessiontests "
        + " where _fk_Session = ${session} and _efk_AdminSUbject = ${testkey}";
    SqlParametersMaps parms2 = parms1;
    SingleDataResultSet rs2 = executeStatement (connection, cmd2, parms2, false).getResultSets ().next ();
    DbResultRecord rcd2 = (rs2.getCount () > 0 ? rs2.getRecords ().next () : null);
    if (rcd2 != null)
      oppcnt = rcd2.<Integer> get ("oppcnt");

    final String cmd3 = "insert IGNORE into ${oppsTbl} (oppkey, oppnum) "
        + " select _key, opportunity from testopportunity O1 "
        + "  where _fk_Session = ${session} and _efk_AdminSUbject = ${testkey} and Opportunity < ${oppcnt} "
        + "  and not exists "
        + "    (select * from testopportunity O2 where  O2._fk_Session = ${session} "
        + "      and O2._efk_AdminSubject = ${testkey} "
        + "      and O2._Key <> O1._Key and O2._efk_Testee = O1._efk_Testee and O2.opportunity = ${oppcnt} "
        + "      and datecompleted is not null)";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testKey).
        put ("oppcnt", oppcnt);
    insertedCnt = executeStatement (connection, fixDataBaseNames (cmd3, unquotedparms), parms3, false).getUpdateCount ();

    final String cmd4 = "delete from testeeresponse where _fk_TestOpportunity in (select oppkey from ${oppsTbl})";
    int deletedCnt = executeStatement (connection, fixDataBaseNames (cmd4, unquotedparms), null, false).getUpdateCount ();

    final String cmd5 = "delete from testopportunity where _key in (select oppkey from ${oppsTbl})";
    deletedCnt = executeStatement (connection, fixDataBaseNames (cmd5, unquotedparms), null, false).getUpdateCount ();

    connection.dropTemporaryTable (oppsTbl);
  }

  public int SIM_InsertTesteeAttribute_SP (SQLConnection connection, UUID testopp, String identifier,
      String attval, String context) throws ReturnStatusException {

    int cnt = 0;
    final String cmd1 = "select _fk_testopportunity from testeeattribute "
        + " where _fk_TestOpportunity = ${testopp} and context = ${context} and TDS_ID = ${identifier} limit 1";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("testopp", testopp).put ("identifier", identifier).
        put ("context", context).put ("attval", attval);
    if (exists (executeStatement (connection, cmd1, parms1, false))) {

      final String cmd2 = "update testeeattribute set attributeValue = ${attval}, _date = now(3) "
          + " where _fk_TestOpportunity = ${testopp} and context = ${context} and TDS_ID = ${identifier}";
      SqlParametersMaps parms2 = parms1;
      cnt = executeStatement (connection, cmd2, parms2, false).getUpdateCount ();

    } else {
      final String cmd3 = "insert into testeeattribute (_fk_TestOpportunity, TDS_ID, attributeValue, context, _date) "
          + " select ${testopp}, ${identifier}, ${attval}, ${context}, now(3)";
      SqlParametersMaps parms3 = parms1;
      cnt = executeStatement (connection, cmd3, parms3, false).getUpdateCount ();
    }
    return cnt;
  }

  public SingleDataResultSet GetOpportunityStatus_SP (SQLConnection connection, UUID oppkey) throws ReturnStatusException {

    final String cmd1 = "select _efk_Testee, _efk_TestID, Opportunity, testeeName, status, clientname, "
        + " _fk_Session, environment, maxItems, numItems, numResponses "
        + " dateJoined, dateStarted, dateCompleted "
        + "   from testopportunity  where _key = ${oppkey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, true).getResultSets ().next ();
    return rs1;
  }

  public void AA_UpdateAbilityEstimates_SP (SQLConnection connection, UUID oppkey, Integer itemPosition,
      String strand, Float theta, Float info, Float lambda) throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);

    final String cmd1 = "update testoppabilityestimate set estimate = ${theta}, info = ${info},lambda = ${lambda} "
        + " where _fk_TestOpportunity = ${oppkey} and itemPos = ${itemPosition} and strand like ${strand}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("theta", theta).put ("info", info).put ("lambda", lambda)
        .put ("oppkey", oppkey).put ("itemposition", itemPosition).put ("strand", strand);

    int updatedCnt = executeStatement (connection, cmd1, parms1, false).getUpdateCount ();

    final String cmd2 = "select _fk_testOpportunity from testoppabilityestimate "
        + " where _fk_TestOpportunity = ${oppkey} and itempos = ${itemPosition} and strand like ${strand}"
        + "  limit 1";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("oppkey", oppkey).put ("itemposition", itemPosition).put ("strand", strand);

    if (exists (executeStatement (connection, cmd2, parms2, false)) == false) {
      final String cmd3 = "insert into testoppabilityestimate (_fk_testOpportunity, strand, itemPos, estimate, info, lambda) "
          + " select ${oppkey}, ${strand}, ${itemPosition}, ${theta}, ${info}, ${lambda}";
      SqlParametersMaps parms3 = parms1;
      updatedCnt = executeStatement (connection, cmd3, parms3, false).getUpdateCount ();
    }
    _commonDll._LogDBLatency_SP (connection, "AA_UpdateAbilityEstimates", starttime, null, true, null, oppkey, null, null, null);
  }

  public boolean simGetSimAbort (SQLConnection connection, UUID session) throws ReturnStatusException {

    boolean abort = false;
    final String cmd1 = "select sim_abort from session where _key = ${session}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session);
    SingleDataResultSet rs = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();
    DbResultRecord rec = (rs.getCount () > 0 ? rs.getRecords ().next () : null);
    if (rec != null) {
      abort = rec.<Boolean> get ("sim_abort");
    }
    return abort;
  }

  public String sim_getItemType (SQLConnection connection, String sItemKey) throws ReturnStatusException {

    String itemType = null;

    final String cmd1 = "SELECT ItemType FROM ${ItembankDB}.tblitem WHERE _Key = ${sItemKey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("sItemKey", sItemKey);
    SingleDataResultSet rs1 = executeStatement (connection, fixDataBaseNames (cmd1), parms1, false).getResultSets ().next ();
    DbResultRecord rec = (rs1.getCount () > 0 ? rs1.getRecords ().next () : null);
    if (rec != null)
      itemType = rec.<String> get ("itemtype");
    return itemType;
  }

  @Override
  public SingleDataResultSet SIM_GetItemSelectionParameters (SQLConnection connection, UUID sessionKey, String testKey) throws ReturnStatusException {
    final String cmd = "select _fk_session, _fk_adminsubject, bpelementtype, "
        + " (case bpelementtype when ${testentitytype} then ${emptystring} else bpelementid end) as bpelementid, "
        + " name, value, label "
        + " from sim_itemselectionparameter "
        + " where _fk_session = ${sessionkey} and _fk_adminsubject = ${testkey}";
    SqlParametersMaps parms = (new SqlParametersMaps ())
        .put ("sessionkey", sessionKey).put ("testkey", testKey)
        .put ("testentitytype", "Test").put ("emptystring", "");
    SingleDataResultSet rs = executeStatement (connection, cmd, parms, false).getResultSets ().next ();
    return rs;
  }
  
  @Override
  public void SIM_AlterItemSelectionParameter (SQLConnection connection, UUID sessionKey, String testKey, String bpElementID, String paramName, String paramValue)
      throws ReturnStatusException {
    String cmd = "update sim_itemselectionparameter "
        + " set value=${value} "
        + " where _fk_session = ${sessionkey} and _fk_adminsubject = ${testkey} and name=${name} ";
    if (bpElementID.isEmpty ()) {
      cmd += " and bpelementtype=${testentitytype} ";
    } else {
      cmd += " and bpelementid=${bpelementid} ";
    }
    SqlParametersMaps parms = (new SqlParametersMaps ())
        .put ("sessionkey", sessionKey).put ("testkey", testKey).put ("bpelementid", bpElementID)
        .put ("name", paramName).put ("value", paramValue).put ("testentitytype", "Test");
    executeStatement (connection, cmd, parms, false);
  }

  @Override
  public void SIM_DeleteAllItemSelectionParameterDefaultRecords (SQLConnection connection)
      throws ReturnStatusException {
    final String cmd = "delete from sim_defaultitemselectionparameter";
    executeStatement (connection, cmd, null, false);
  }

  @Override
  public void SIM_AddItemSelectionParameterDefaultRecord (SQLConnection connection, String algorithmType, String entityType, String name, String value, String label)
      throws ReturnStatusException {
    final String cmd = "insert into sim_defaultitemselectionparameter (algorithmtype, entitytype, name, value, label) "
        + " values (${algorithmtype}, ${entitytype}, ${name}, ${value}, ${label}) ";
    SqlParametersMaps parms = (new SqlParametersMaps ())
        .put ("algorithmtype", algorithmType).put ("entitytype", entityType)
        .put ("name", name).put ("value", value).put ("label", label);
    executeStatement (connection, cmd, parms, false);
  }
    
  @Override
  public MultiDataResultSet SIM_GetSessionForPublish (SQLConnection connection, UUID sessionKey)
      throws ReturnStatusException {
    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();

    SqlParametersMaps parms = (new SqlParametersMaps ())
        .put ("sessionkey", sessionKey)
        .put ("strand", "Strand").put ("contentlevel", "ContentLevel").put ("affinitygroup", "AffinityGroup");

    // Session information
    final String cmd1 = " select S._efk_proctor, S.proctorid,  S.proctorname, S.sessionid, S.status, "
        + " S.name, S.description, S.datecreated, S.clientname, S.sim_language "
        + " from session S "
        + " where S._key=${sessionkey} ";

    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms, false).getResultSets ().next ();
    resultsets.add (rs1);

    // Session test information
    final String cmd2 = " select S._efk_adminsubject, S._efk_testid, s.iterations, S.opportunities, "
        + " S.meanproficiency, S.sdproficiency, S.strandcorrelation, S.handscoreitemtypes "
        + " from sessiontests S "
        + " where S._fk_session=${sessionkey} ";

    SingleDataResultSet rs2 = executeStatement (connection, cmd2, parms, false).getResultSets ().next ();
    resultsets.add (rs2);

    // Segment information
    final String cmd3 = " select S._efk_adminsubject, S._efk_segment, S.startability, S.startinfo, "
        + " S.minitems, S.maxitems, S.ftstartpos, S.ftendpos, S.ftminitems, S.ftmaxitems, "
        + " S.formselection, S.blueprintweight, S.cset1size, S.cset2random, S.cset2initialrandom, "
        + " S.loadconfig, S.updateconfig, S.itemweight, S.abilityoffset, S.rcabilityweight, S.abilityweight, "
        + " S.precisiontargetnotmetweight, S.precisiontargetmetweight, S.precisiontarget, S.adaptivecut, "
        + " S.toocloseses, S.terminationmincount, S.terminationoverallinfo, S.terminationrcinfo, "
        + " S.terminationtooclose, S.terminationflagsand, S.segmentposition, S.segmentid, S.selectionalgorithm, "
        + " S.cset1order, A._efk_blueprint, ${ItembankDB}.testbankkey(S._efk_adminsubject) as bankkey, S.segmentid "
        + " from sim_segment S, ${ItembankDB}.tblsetofadminsubjects A"
        + " where S._fk_session = ${sessionkey} and S._efk_segment = A._key ";
    SingleDataResultSet rs3 = executeStatement (connection, fixDataBaseNames (cmd3), parms, false).getResultSets ().next ();
    resultsets.add (rs3);

    // Strand or ContentLevel information
    final String cmd4 = "select S._efk_segment, S.contentlevel, S.minitems, S.maxitems, S.adaptivecut, "
        + " S.startability, S.startinfo, S.scalar, S.isstrictmax, S.bpweight, S.abilityweight, "
        + " S.precisiontargetnotmetweight, S.precisiontargetmetweight, S.precisiontarget, S1.name, "
        + " CASE S1.treeLevel when 1 then 'Strand' else 'ContentLevel'end as objectType "
        + " from sim_segmentcontentlevel S, ${ItembankDB}.tblstrand S1 "
        + " where S._fk_Session = ${sessionkey} and S.contentlevel=S1._Key ";
    SingleDataResultSet rs4 = executeStatement (connection, fixDataBaseNames (cmd4), parms, false).getResultSets ().next ();
    resultsets.add (rs4);

    // Affinity Group
    final String cmd5 = "select S._efk_segment, S.contentlevel, S.minitems, S.maxitems, "
        + " S.adaptivecut, S.startability, S.startinfo, S.scalar, S.isstrictmax, S.bpweight, "
        + " S.abilityweight, S.precisiontargetnotmetweight, S.precisiontargetmetweight, S.precisiontarget, "
        + " S.contentlevel, ${affinitygroup} as objectType "
        + " from sim_segmentcontentlevel S, ${ItembankDB}.affinitygroup A"
        + " where S._fk_Session = ${sessionkey} and S.contentlevel = A.groupid and S._efk_segment = A._fk_adminsubject";
    SingleDataResultSet rs5 = executeStatement (connection, fixDataBaseNames (cmd5), parms, false).getResultSets ().next ();
    resultsets.add (rs5);

    // Item Groups
    final String cmd6 = "select S._efk_segment, S.groupid, S.maxitems, T._efk_itskey "
        + " from sim_itemgroup S, ${ItembankDB}.tbladminstimulus A, ${ItembankDB}.tblStimulus T "
        + " where S._fk_session = ${sessionkey} and  S._efk_segment= A._fk_adminsubject and "
        + " S.groupid = A.groupid and A._fk_stimulus = T._key ";
    SingleDataResultSet rs6 = executeStatement (connection, fixDataBaseNames (cmd6), parms, false).getResultSets ().next ();
    resultsets.add (rs6);

    // Items
    final String cmd7 = "select S._efk_segment, S._efk_item, S.isactive, S.isrequired, "
        + " S.isfieldtest, S.strand, S.groupid, I._efk_item "
        + " from sim_segmentitem S, ${ItembankDB}.tblSetofAdminItems A, ${ItembankDB}.tblItem I "
        + " where S._fk_session = ${sessionkey} and S._efk_segment = A._fk_adminsubject and "
        + " S._efk_item = A._fk_item and A._fk_item = I._key";
    SingleDataResultSet rs7 = executeStatement (connection, fixDataBaseNames (cmd7), parms, false).getResultSets ().next ();
    resultsets.add (rs7);

    return new MultiDataResultSet (resultsets);
  }
  
  @Override
  public SingleDataResultSet SIM_GetSessionTestPackage (SQLConnection connection, UUID sessionKey, String testKey)
      throws ReturnStatusException {
    final String cmd = "select T._key, T.testpackage as testpackage "
        + " from ${ItembankDB}.tbltestpackage T, ${ItembankDB}.tbladminsubjecttestpackage A, sim_sessiontestpackage S "
        + " where A._fk_adminsubject = ${testkey} and A._fk_testpackage = T._key and T._key = S._fk_testpackage limit 1";
    SqlParametersMaps parms = (new SqlParametersMaps ())
        .put ("sessionkey", sessionKey).put ("testkey", testKey);
    SingleDataResultSet rs = executeStatement (connection,  fixDataBaseNames (cmd), parms, false).getResultSets ().next ();
    return rs;    
  }
  
  @Override
  public void SIM_LoaderMain (SQLConnection connection, String testKey, String xmlTestPackage) throws ReturnStatusException {
    SqlParametersMaps params = new SqlParametersMaps ();
    params.put ("xmlTestPackage", xmlTestPackage);
    params.put ("testKey", testKey);
    try {
      final String cmd = "call ${ItembankDB}.loader_main(${xmlTestPackage})";
      executeStatement (connection, fixDataBaseNames(cmd), params, false);
    } catch (ReturnStatusException ex) {
      throw ex;
    }    
    // 02/25/2015 arp - On success, add hooks to enable simulation test package update
    final String cmd1 = "call ${ItembankDB}.load_testpackagerecord(${xmlTestPackage}, ${testKey})";
    executeStatement (connection, fixDataBaseNames(cmd1), params, false);
  }  

  private String getClientnameFromSession (SQLConnection connection, UUID session) throws ReturnStatusException {
    String client = null;
    final String cmd1 = "select clientname as client from session where _Key = ${session}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();
    DbResultRecord record1 = (rs1.getCount () > 0 ? rs1.getRecords ().next () : null);
    if (record1 != null) {
      client = record1.<String> get ("client");
    }
    return client;
  }

  private Boolean testOpportunityExists (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException {

    final String cmd1 = "select _fk_session from testopportunity where _fk_Session = ${session} and _efk_AdminSubject = ${testkey} limit 1";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("session", session).put ("testkey", testkey);

    Boolean exists = exists (executeStatement (connection, cmd1, parms1, false));
    return exists;
  }

  private void SIM_CreateItemSelectionParameters (SQLConnection connection, UUID sessionKey)
      throws ReturnStatusException {
    DataBaseTable tempitemselectionparamsTbl = getDataBaseTable ("tempitemselectionparams").addColumn ("testname", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250).
        addColumn ("bpelementtype", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("bpelementid", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200).
        addColumn ("name", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).addColumn ("value", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200).addColumn ("label", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200);
    connection.createTemporaryTable (tempitemselectionparamsTbl);
    SqlParametersMaps parms = (new SqlParametersMaps ())
        .put ("sessionkey", sessionKey)
        .put ("testentity", "Test");

    Map<String, String> dbparams = new HashMap<String, String> ();
    dbparams.put ("tempdbname", tempitemselectionparamsTbl.getTableName ());

    try {
      boolean preexistingAutoCommitMode = connection.getAutoCommit ();
      connection.setAutoCommit (false);
      final String cmd1 = " insert into ${tempdbname} (testname, bpelementtype, bpelementid, name, value, label) "
          + " select S._efk_adminsubject, P.entitytype, S.segmentid, P.name, P.value, P.label "
          + " from sim_defaultitemselectionparameter P, sim_segment S  "
          + " where P.algorithmtype = S.selectionalgorithm and P.entitytype=${testentity} and S._fk_session=${sessionkey}";
      executeStatement (connection, fixDataBaseNames (cmd1, dbparams), parms, false);

      final String cmd2 = " update ${tempdbname} T inner join sim_itemselectionparameter P "
          + " on P._fk_adminsubject = T.testname AND P.bpelementid = T.bpelementid and P.name = T.name "
          + " set T.value=P.value "
          + " where P._fk_session=${sessionkey} ";
      executeStatement (connection, fixDataBaseNames (cmd2, dbparams), parms, false);

      final String cmd3 = " delete from sim_itemselectionparameter where _fk_session=${sessionkey}";
      executeStatement (connection, fixDataBaseNames (cmd3, dbparams), parms, false);

      final String cmd4 = " insert into sim_itemselectionparameter(_fk_session, _fk_adminsubject, bpelementtype, bpelementid, name, value, label) "
          + " select ${sessionkey}, testname, bpelementType, bpelementid, name, value, label "
          + " from ${tempdbname} ";
      executeStatement (connection, fixDataBaseNames (cmd4, dbparams), parms, false);

      connection.commit ();
      connection.setAutoCommit (preexistingAutoCommitMode);
    } catch (Exception e) {
      try {
        connection.rollback ();
      } catch (SQLException se) {
        _logger.error ("Failed rollback transaction", se);
      }
      _logger.error (String.format ("SIM_CreateItemSelectionParameters failed: %s", e.getMessage ()));
      throw new ReturnStatusException (e);
    }
  }  
}
