/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.student.dll.test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ContextConfiguration;

import tds.dll.api.IProctorDLL;
import AIR.Common.DB.AbstractConnectionManager;
import AIR.Common.DB.AbstractDLL;
import AIR.Common.DB.SQLConnection;
import AIR.Common.DB.SqlParametersMaps;
import AIR.Common.DB.results.DbResultRecord;
import AIR.Common.DB.results.MultiDataResultSet;
import AIR.Common.DB.results.SingleDataResultSet;
import AIR.Common.Helpers._Ref;
import AIR.Common.Sql.AbstractDateUtilDll;
import TDS.Shared.Exceptions.ReturnStatusException;

import org.opentestsystem.shared.test.LifecycleManagingTestRunner;

@RunWith (LifecycleManagingTestRunner.class)
@ContextConfiguration (locations = "/test-context.xml")
@IfProfileValue(name="TestProfile", value="ToBeFixed")
public class TestProctorDLL
{
  private static Logger             _logger            = LoggerFactory.getLogger (TestStudentDLL.class);

  @Autowired
  private IProctorDLL               _dll               = null;

  @Autowired
  private AbstractDateUtilDll       _dateUtil          = null;

  @Autowired
  private AbstractConnectionManager _connectionManager = null;

  @Autowired
  private MyProctorDLLHelper        _myDllHelper       = null;

  @Test
  public void test_IB_GetSegments_SP () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    int sessionType = 0;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      MultiDataResultSet resultsets = _dll.IB_GetSegments_SP (connection, clientName, sessionType);
      Iterator<SingleDataResultSet> iterator = resultsets.getResultSets ();
      int resultIdx = 0;
      while (iterator.hasNext ()) {
        SingleDataResultSet set = iterator.next ();
        if (resultIdx == 0) {
          Iterator<DbResultRecord> iteratorRec = set.getRecords ();
          while (iteratorRec.hasNext ()) {
            assertTrue (set.getCount () == 15);
            DbResultRecord record = iteratorRec.next ();
            _logger.info ("===================IB_GetSegments Result=======================");
            _logger.info (String.format ("TestKey: %s", record.<String> get ("TestKey")));
            _logger.info (String.format ("TestID: %s", record.<String> get ("TestID")));
            _logger.info (String.format ("segmentID %s", record.<String> get ("segmentID")));
            _logger.info (String.format ("segmentPosition: %d", record.<Integer> get ("segmentPosition")));
            _logger.info (String.format ("segmentLabel: %s", record.<String> get ("segmentLabel")));
            _logger.info (String.format ("IsPermeable : %s", record.<Integer> get ("IsPermeable")));
            _logger.info (String.format ("entryApproval: %d", record.<Integer> get ("entryApproval")));
            _logger.info (String.format ("exitApproval: %d", record.<Integer> get ("exitApproval")));
            _logger.info (String.format ("itemReview: %s", record.<Boolean> get ("itemReview")));
          }
        }
        if (resultIdx == 1) {
          assertTrue (set.getCount () == 4);
          Iterator<DbResultRecord> iteratorRec = set.getRecords ();
          while (iteratorRec.hasNext ()) {
            DbResultRecord record = iteratorRec.next ();
            _logger.info ("===================IB_GetSegments Result====================");
            _logger.info (String.format ("TestKey: %s", record.<String> get ("TestKey")));
            _logger.info (String.format ("TestID: %s", record.<String> get ("TestID")));
            _logger.info (String.format ("segmentID %s", record.<String> get ("segmentID")));
            _logger.info (String.format ("segmentPosition: %d", record.<Integer> get ("segmentPosition")));
            _logger.info (String.format ("AccType: %s", record.<String> get ("AccType")));
            _logger.info (String.format ("AccValue: %s", record.<String> get ("AccValue")));
            _logger.info (String.format ("AccCode: %s", record.<String> get ("AccCode")));
            _logger.info (String.format ("AllowCombine: %s", record.<Boolean> get ("AllowCombine")));
            _logger.info (String.format ("IsDefault : %s", record.<Boolean> get ("IsDefault")));
            _logger.info (String.format ("AllowChange: %s", record.<Boolean> get ("AllowChange")));
            _logger.info (String.format ("IsSelectable: %s", record.<Boolean> get ("IsSelectable")));
            _logger.info (String.format ("IsVisible: %s", record.<Boolean> get ("IsVisible")));
            _logger.info (String.format ("studentControl: %s", record.<Boolean> get ("StudentControl")));
            _logger.info (String.format ("IsFunctional: %s", record.<Boolean> get ("IsFunctional")));
          }
        }
        resultIdx++;
      }
    }
  }

  @Test
  public void testIB_GlobalAccommodations_SP () throws SQLException, ReturnStatusException {
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      String clientName = "Oregon";
      String context = "StudentGLobal";
      MultiDataResultSet resultsets = _dll.IB_GlobalAccommodations_SP (connection, clientName, context);
      Iterator<SingleDataResultSet> iterator = resultsets.getResultSets ();
      int resultIdx = 0;
      while (iterator.hasNext ()) {
        SingleDataResultSet set = iterator.next ();
        if (resultIdx == 0) {
          assertTrue (set.getCount () == 8);
          Iterator<DbResultRecord> iteratorRec = set.getRecords ();
          assertTrue (iteratorRec != null);
          while (iteratorRec.hasNext ()) {
            DbResultRecord record = iteratorRec.next ();
            _logger.info ("===================testIB_GlobalAccommodations_SP Result=======================");
            _logger.info (String.format ("ContextType: %s", record.<String> get ("contextType")));
            _logger.info (String.format ("Context: %s", record.<String> get ("context")));
            _logger.info (String.format ("AccType: %s", record.<String> get ("accType")));
            _logger.info (String.format ("AccValue: %s", record.<String> get ("accValue")));
            _logger.info (String.format ("AccCode: %s", record.<String> get ("accCode")));
            _logger.info (String.format ("IsDefault: %s", record.<Boolean> get ("isDefault")));
            _logger.info (String.format ("AllowCombine: %s", record.<Boolean> get ("allowCombine")));
            _logger.info (String.format ("AllowChange: %s", record.<Boolean> get ("allowChange")));
            _logger.info (String.format ("IsSelectable: %s", record.<Boolean> get ("isSelectable")));
            _logger.info (String.format ("IsVisible: %s", record.<Boolean> get ("isVisible")));
            _logger.info (String.format ("StudentControl: %s", record.<Boolean> get ("studentControl")));
            _logger.info (String.format ("IsFunctional: %s", record.<Boolean> get ("isFunctional")));
            _logger.info (String.format ("DependsOnToolType: %s", record.<String> get ("dependsOnToolType")));
          }
        }
        if (resultIdx == 1) {
          Iterator<DbResultRecord> iteratorRec = set.getRecords ();
          assertTrue (set.getCount () == 1);
          assertTrue (iteratorRec != null);
          while (iteratorRec.hasNext ()) {
            DbResultRecord record = iteratorRec.next ();
            _logger.info ("===================testIB_GlobalAccommodations_SP Result=======================");
            _logger.info (String.format ("clientName: %s", record.<String> get ("clientName")));
            _logger.info (String.format ("ContextType: %s", record.<String> get ("contextType")));
            _logger.info (String.format ("Context: %s", record.<String> get ("context")));
            _logger.info (String.format ("IfType: %s", record.<String> get ("ifType")));
            _logger.info (String.format ("IfValue: %s", record.<String> get ("ifValue")));
            _logger.info (String.format ("ThenType: %s", record.<String> get ("thenType")));
            _logger.info (String.format ("ThenValue: %s", record.<String> get ("thenValue")));
            _logger.info (String.format ("IsDefault: %s", record.<String> get ("isDefault")));
          }
        }
        resultIdx++;
      }
    }
  }

  @Test
  public void test_RecordSystemClient_SP () throws SQLException, ReturnStatusException {
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      String clientName = "SBAC";
      String userId = "ClientUser1@air.org";
      String application = "Proctor";
      String clientIP = "1.2.3.4";
      String proxyIP = "3.5.8.9";
      String userAgent = "";

      _dll._RecordSystemClient_SP (connection, clientName, application, userId, clientIP, proxyIP, userAgent);
    }
  }

  @Test
  // success case #1
  public void test_SuppressScores_FN () throws SQLException, ReturnStatusException {
    String clientName = "Hawaii";
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      Integer allow = _dll._SuppressScores_FN (connection, clientName);
      _logger.info (String.format ("_SuppressScores_FN Result -- : %s", allow));
      assertTrue (allow == 1);
    }
  }

  @Test
  // success case #2
  public void test_SuppressScores_FN1 () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      Integer allow = _dll._SuppressScores_FN (connection, clientName);
      _logger.info (String.format ("_SuppressScores_FN Result -- : %s", allow));
      assertTrue (allow == 0);
    }
  }

  @Test
  public void test_GetTesteeAttributes_SP () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    String testeeId = "2941406";
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.GetTesteeAttributes_SP (connection, clientName, testeeId);
      assertTrue (result.getCount () == 12);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("=================== GetTesteeAttributes_SP Records=======================");
        _logger.info (String.format ("TDS_ID: %s", record.<String> get ("TDS_ID")));
        _logger.info (String.format ("type: %s ", record.<String> get ("type")));
        _logger.info (String.format ("atLogin: %s ", record.<String> get ("atLogin")));
        _logger.info (String.format ("rtsName: %s ", record.<String> get ("rtsName")));
        _logger.info (String.format ("label: %s ", record.<String> get ("label")));
        _logger.info (String.format ("value: %s ", record.<String> get ("value")));
        _logger.info (String.format ("sortOrder: %d ", record.<Integer> get ("sortOrder")));
        _logger.info (String.format ("entityKey: %d ", record.<Long> get ("entityKey")));
        _logger.info (String.format ("entityID: %s ", record.<String> get ("entityID")));
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  @Test
  // test case for testee.get() == null
  public void test_GetTesteeAttributes_SP1 () throws SQLException, ReturnStatusException {
    String clientName = "AIR";
    String testeeId = "2941406";
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.GetTesteeAttributes_SP (connection, clientName, testeeId);
      assertTrue (result.getCount () == 11);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("=================== GetTesteeAttributes_SP Records=======================");
        _logger.info (String.format ("TDS_ID: %s", record.<String> get ("TDS_ID")));
        _logger.info (String.format ("type: %s ", record.<String> get ("type")));
        _logger.info (String.format ("atLogin: %s ", record.<String> get ("atLogin")));
        _logger.info (String.format ("rtsName: %s ", record.<String> get ("rtsName")));
        _logger.info (String.format ("label: %s ", record.<String> get ("label")));
        _logger.info (String.format ("value: %s ", record.<String> get ("value")));
        _logger.info (String.format ("sortOrder: %d ", record.<Integer> get ("sortOrder")));
        _logger.info (String.format ("entityKey: %d ", record.<Long> get ("entityKey")));
        _logger.info (String.format ("entityID: %s ", record.<String> get ("entityID")));
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  // Run update once before each execution for the below test case to work #
  // update TestOpportunity
  // set status = 'pending'
  // , prevstatus = NULL
  // , datechanged = '2012-05-29'
  // , dateapproved = NULL
  // where _fk_session = '2F40C9A6-9525-4F6C-A93F-507180297D74'
  // and _key = 'B97A6448-5F59-40F1-9CE6-019A26DD549B'

  @Test
  // success case
  public void test_P_ApproveOpportunity_SP () throws SQLException, ReturnStatusException {
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      UUID sessionKey = UUID.fromString ("2F40C9A6-9525-4F6C-A93F-507180297D74");
      Long proctorKey = 1108L;
      UUID browserKey = UUID.fromString ("C90FCF70-55DC-4110-BDA1-BE28C6DE615F");
      UUID opportunityKey = UUID.fromString ("B97A6448-5F59-40F1-9CE6-019A26DD549B");

      final String query = "update testopportunity set status = 'pending', prevstatus = NULL, datechanged = '2012-05-29', dateapproved = NULL where _fk_session = ${sessionkey} and _key = ${oppkey}";
      SqlParametersMaps parms = (new SqlParametersMaps ()).put ("oppkey", opportunityKey).put ("sessionkey", sessionKey);
      _myDllHelper.executeStatement (connection, query, parms, false);

      Date start = new Date ();
      SingleDataResultSet result = _dll.P_ApproveOpportunity_SP (connection, sessionKey, proctorKey, browserKey, opportunityKey);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_ApproveOpportunity latency: %d millisec", diff));
      DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
      assertTrue (record != null);
      if (record != null) {
        _logger.info (String.format ("Status: %s", record.<String> get ("status")));
        _logger.info (String.format ("Reason: %s", record.<String> get ("reason")));
        assertTrue ("approved".equalsIgnoreCase (record.<String> get ("status")));
        assertTrue (record.<String> get ("reason") == null);
      }
    }
  }

  @Test
  // failure case
  // For this particular oppkey, we are getting clientname as "Delaware_PT"
  // instead of oregon(which is our default one for testing). So, its throwing
  // an error.
  public void test_P_ApproveOpportunity_SP2 () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("690B4504-7571-4E04-9E07-28C96B47FA33");
    Long proctorKey = 24365L;
    UUID browserKey = UUID.fromString ("9B7E9550-72E8-4860-A7AC-FEF1FC132982");
    UUID opportunityKey = UUID.fromString ("FE0620E9-3530-4FA8-8A0F-0004AEA29F40");
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.P_ApproveOpportunity_SP (connection, sessionKey, proctorKey, browserKey, opportunityKey);
      DbResultRecord record = result.getRecords ().next ();
      assertTrue (record != null);
      String status = record.<String> get ("status");
      String reason = record.<String> get ("Reason");
      String context = record.<String> get ("context");
      String appkey = record.<String> get ("appkey");
      _logger.info (String.format ("Status: %s", status));
      _logger.info (String.format ("Reason: %s", reason));
      _logger.info (String.format ("context: %s", context));
      _logger.info (String.format ("appkey: %s", appkey));
      assertTrue ("failed".equalsIgnoreCase (status));
      assertTrue (("The test opportunity is not pending approval[10186]").equalsIgnoreCase (reason));
      assertTrue ("P_ApproveOpportunity".equalsIgnoreCase (context));
      assertTrue ("The test opportunity is not pending approval".equalsIgnoreCase (appkey));
    }
  }

  @Test
  // failure case #1
  public void test_P_ApproveOpportunity_SP1 () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("B717F914-6C57-414B-A875-CBC3286A4A8A");
    Long proctorKey = null;
    UUID browserKey = UUID.fromString ("D26FBEEF-EB7F-4780-9BEF-E8FDA251F26E");
    UUID opportunityKey = UUID.fromString ("FD4C3B16-2D78-4234-A97A-F3168237B478");
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.P_ApproveOpportunity_SP (connection, sessionKey, proctorKey, browserKey, opportunityKey);
      DbResultRecord record = result.getRecords ().next ();
      assertTrue (record != null);
      String status = record.<String> get ("status");
      String reason = record.<String> get ("Reason");
      String context = record.<String> get ("context");
      String appkey = record.<String> get ("appkey");
      _logger.info (String.format ("Status: %s", status));
      _logger.info (String.format ("Reason: %s", reason));
      _logger.info (String.format ("context: %s", context));
      _logger.info (String.format ("appkey: %s", appkey));
      assertTrue ("failed".equalsIgnoreCase (status));
      assertTrue (("The session is not owned by this proctor [11602]").equalsIgnoreCase (reason));
      assertTrue ("ValidateProctorSession".equalsIgnoreCase (context));
      assertTrue ("The session is not owned by this proctor".equalsIgnoreCase (appkey));
    }
  }

  // -- Run below statement before running the TEST CASE below.
  // delete
  // from session
  // where clientname = 'Oregon' and _fk_browser =
  // 'D1019F11-7220-42E0-9847-450701BE3CB6'
  @Test
  public void test_P_CreateSession_SP () throws SQLException, ReturnStatusException {
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      String clientName = "Oregon";
      UUID browserKey = UUID.fromString ("D1019F11-7220-42E0-9847-450701BE3CB6");
      String sessionName = "";
      Long proctorKey = 358387L;
      String procId = "dtsa1@air.org";
      String procName = "DTSA One";
      Date dateBegin = null;
      Date dateEnd = null;
      int sessionType = 0;

      final String query = "delete from session where clientname = ${clientname} and _fk_browser = ${browserKey}";
      SqlParametersMaps parms = (new SqlParametersMaps ()).put ("browserKey", browserKey).put ("clientname", clientName);
      _myDllHelper.executeStatement (connection, query, parms, false);

      Date start = new Date ();
      SingleDataResultSet result = _dll.P_CreateSession_SP (connection, clientName, browserKey, sessionName, proctorKey, procId, procName, dateBegin, dateEnd, sessionType);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_CreateSession latency: %d millisec", diff));
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("===================P_CreateSession Result=======================");
        _logger.info (String.format ("sessionKey: %s", record.<String> get ("sessionKey")));
        _logger.info (String.format ("sessionID: %s ", record.<String> get ("sessionID")));
        _logger.info (String.format ("Name: %s ", record.<String> get ("Name")));
        _logger.info (String.format ("sessionStatus: %s ", record.<String> get ("sessionStatus")));
      }
    }
  }

  @Test
  // failure case #1 run this case with out running the delete statement
  public void test_P_CreateSession_SP1 () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    UUID browserKey = UUID.fromString ("D1019F11-7220-42E0-9847-450701BE3CB6");
    String sessionName = "";
    Long proctorKey = 358387L;
    String procId = "dtsa1@air.org";
    String procName = "DTSA One";
    Date dateBegin = null;
    Date dateEnd = null;
    int sessionType = 0;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.P_CreateSession_SP (connection, clientName, browserKey, sessionName, proctorKey, procId, procName, dateBegin, dateEnd, sessionType);
      DbResultRecord record = result.getRecords ().next ();
      assertTrue (record != null);
      String status = record.<String> get ("status");
      String reason = record.<String> get ("Reason");
      String context = record.<String> get ("context");
      String appkey = record.<String> get ("appkey");
      _logger.info (String.format ("Status: %s", status));
      _logger.info (String.format ("Reason: %s", reason));
      _logger.info (String.format ("context: %s", context));
      _logger.info (String.format ("appkey: %s", appkey));
      assertTrue ("failed".equalsIgnoreCase (status));
      assertTrue (("An active session for this user already exists. [11880]").equalsIgnoreCase (reason));
      assertTrue ("P_CreateSession".equalsIgnoreCase (context));
      assertTrue ("There already is an active session for this user.".equalsIgnoreCase (appkey));
    }
  }

  @Test
  public void test_P_CreateSession_SP2 () throws SQLException, ReturnStatusException {
    String clientName = "ABC";
    UUID browserKey = UUID.fromString ("D1019F11-7220-42E0-9847-450701BE3CB6");
    String sessionName = "";
    Long proctorKey = 35837L;
    String procId = "dtsa1@air.org";
    String procName = "DTSA One";
    Date dateBegin = null;
    Date dateEnd = null;
    int sessionType = 0;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.P_CreateSession_SP (connection, clientName, browserKey, sessionName, proctorKey, procId, procName, dateBegin, dateEnd, sessionType);
      DbResultRecord record = result.getRecords ().next ();
      assertTrue (record != null);
      String status = record.<String> get ("status");
      String reason = record.<String> get ("Reason");
      String context = record.<String> get ("context");
      String appkey = record.<String> get ("appkey");
      _logger.info (String.format ("Status: %s", status));
      _logger.info (String.format ("Reason: %s", reason));
      _logger.info (String.format ("context: %s", context));
      _logger.info (String.format ("appkey: %s", appkey));
      assertTrue ("failed".equalsIgnoreCase (status));
      assertTrue (("Unknown client: ABC [-----]").equalsIgnoreCase (reason));
      assertTrue ("P_CreateSession".equalsIgnoreCase (context));
      assertTrue ("Unknown client: ABC".equalsIgnoreCase (appkey));
    }
  }

  // Run the below update stmts each time before running this test
  // update TestOpportunity_ReadOnly
  // set DateChanged = getdate()
  // where _fk_session = 'FEA63E2D-B102-4920-88B0-E6011B8C837E'

  @Test
  public void test_P_GetActiveCount_SP () throws ReturnStatusException, SQLException {
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      UUID sessionKey = UUID.fromString ("FEA63E2D-B102-4920-88B0-E6011B8C837E");
      Long proctorKey = 20424L;
      UUID browserKey = UUID.fromString ("9318C537-EC05-41ED-AB08-39ECE4E8D605");
      Date now = _dateUtil.getDate (connection);

      final String query = "update testopportunity_readonly set DateChanged = ${now} where _fk_session = ${sessionkey}";
      SqlParametersMaps parms = (new SqlParametersMaps ()).put ("sessionkey", sessionKey).put ("now", now);
      _myDllHelper.executeStatement (connection, query, parms, false);

      Date start = new Date ();
      SingleDataResultSet result = _dll.P_GetActiveCount_SP (connection, sessionKey, proctorKey, browserKey);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_GetActiveCount latency: %d millisec", diff));
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("===================P_GetActiveCount Result=======================");
        Integer cnt = record.<Integer> get ("active");
        _logger.info (String.format ("active: %d", record.<Integer> get ("active")));

        assertTrue (cnt == 1138);
      }
    }
  }

  @Test
  // failure case
  public void test_P_GetActiveCount_SP1 () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("FEA63E2D-B142-4920-88B0-E6011B8C837E");
    Long proctorKey = 20424L;
    UUID browserKey = UUID.fromString ("9318C537-EC05-41ED-AB08-39ECE4E8D605");
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      Date start = new Date ();
      SingleDataResultSet result = _dll.P_GetActiveCount_SP (connection, sessionKey, proctorKey, browserKey);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_GetActiveCount latency: %d millisec", diff));

      DbResultRecord record = result.getRecords ().next ();
      assertTrue (record != null);
      String status = record.<String> get ("status");
      String reason = record.<String> get ("Reason");
      String context = record.<String> get ("context");
      String appkey = record.<String> get ("appkey");
      _logger.info (String.format ("Status: %s", status));
      _logger.info (String.format ("Reason: %s", reason));
      _logger.info (String.format ("context: %s", context));
      _logger.info (String.format ("appkey: %s", appkey));
      assertTrue ("failed".equalsIgnoreCase (status));
      assertTrue (("The session is closed. [11601]").equalsIgnoreCase (reason));
      assertTrue ("ValidateProctorSession".equalsIgnoreCase (context));
      assertTrue ("The session is closed.".equalsIgnoreCase (appkey));
    }
  }

  // -- # Run update once daily for the below test case to work #
  // update TestOpportunity_ReadOnly
  // set DateChanged = getdate()
  // where _fk_session = '87388C02-92D4-4FF0-A12B-0866DD0BE0E5'
  @Test
  public void test_P_GetCurrentSessionTestees_SP () throws SQLException, ReturnStatusException {
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      UUID sessionKey = UUID.fromString ("87388C02-92D4-4FF0-A12B-0866DD0BE0E5");
      Long proctorKey = 26496L;
      UUID browserKey = UUID.fromString ("91B088B8-1815-41E3-B16F-857C615626EF");
      Date now = _dateUtil.getDate (connection);

      final String query = "update testopportunity_readonly set DateChanged = ${now} where _fk_session = ${sessionkey}";
      SqlParametersMaps parms = (new SqlParametersMaps ()).put ("sessionkey", sessionKey).put ("now", now);
      _myDllHelper.executeStatement (connection, query, parms, false);

      Date start = new Date ();
      SingleDataResultSet result = _dll.P_GetCurrentSessionTestees_SP (connection, sessionKey, proctorKey, browserKey);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_getCurrentSessionTestees latency: %d millisec", diff));

      assertTrue (result.getCount () == 20);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("===================P_GetCurrentSessionTestees Records=======================");
        _logger.info (String.format ("_efk_AdminSubject: %s", record.<String> get ("_efk_AdminSubject")));
        _logger.info (String.format ("opportunityKey: %s ", record.<String> get ("opportunityKey")));
        _logger.info (String.format ("_efk_Testee: %d ", record.<Long> get ("_efk_Testee")));
        _logger.info (String.format ("_efk_TestID: %s ", record.<String> get ("_efk_TestID")));
        _logger.info (String.format ("opportunity: %d ", record.<Integer> get ("opportunity")));
        _logger.info (String.format ("TesteeName: %s ", record.<String> get ("TesteeName")));
        _logger.info (String.format ("TesteeID: %s ", record.<String> get ("TesteeID")));
        _logger.info (String.format ("status: %s ", record.<String> get ("status")));
        _logger.info (String.format ("DateCompleted: %s ", record.<Date> get ("DateCompleted")));
        _logger.info (String.format ("_fk_Session: %s ", record.<String> get ("_fk_Session")));
        _logger.info (String.format ("SessionID: %s ", record.<String> get ("SessionID")));
        _logger.info (String.format ("sessionName: %s ", record.<String> get ("sessionName")));
        _logger.info (String.format ("ItemCount: %d ", record.<Integer> get ("ItemCount")));
        _logger.info (String.format ("pauseMinutes: %d ", record.<Integer> get ("pauseMinutes")));
        _logger.info (String.format ("ResponseCount: %d ", record.<Integer> get ("ResponseCount")));
        _logger.info (String.format ("RequestCount: %d ", record.<Integer> get ("RequestCount")));
        _logger.info (String.format ("score: %f ", record.<Float> get ("score")));
        _logger.info (String.format ("Accommodations: %s ", record.<String> get ("Accommodations")));
        _logger.info (String.format ("customAccommodations: %s ", record.<Boolean> get ("customAccommodations")));
        _logger.info (String.format ("mode: %s ", record.<String> get ("mode")));

        assertTrue (record.<Integer> get ("opportunity") == 1);
        assertTrue (record.<UUID> get ("_fk_Session").equals (UUID.fromString ("87388C02-92D4-4FF0-A12B-0866DD0BE0E5")));
        assertTrue ("air-30".equalsIgnoreCase (record.<String> get ("SessionID")));
        assertTrue (record.<Integer> get ("pauseMinutes") == null);
        assertTrue (record.<Integer> get ("RequestCount") == 0);
        assertTrue (record.<Float> get ("score") == null);
        assertTrue (record.<Boolean> get ("customAccommodations") == true);
        assertTrue ("online".equalsIgnoreCase (record.<String> get ("mode")));
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  @Test
  public void test_P_GetRTSTestee_SP () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    String externalId = "2941406";
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.P_GetRTSTestee_SP (connection, clientName, externalId);
      assertTrue (result.getCount () == 12);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("=================== P_GetRTSTestee Records=======================");
        _logger.info (String.format ("TDS_ID: %s", record.<String> get ("TDS_ID")));
        _logger.info (String.format ("type: %s ", record.<String> get ("type")));
        _logger.info (String.format ("atLogin: %s ", record.<String> get ("atLogin")));
        _logger.info (String.format ("rtsName: %s ", record.<String> get ("rtsName")));
        _logger.info (String.format ("label: %s ", record.<String> get ("label")));
        _logger.info (String.format ("value: %s ", record.<String> get ("value")));
        _logger.info (String.format ("sortOrder: %d ", record.<Integer> get ("sortOrder")));
        _logger.info (String.format ("entityKey: %d ", record.<Long> get ("entityKey")));
        _logger.info (String.format ("entityID: %s ", record.<String> get ("entityID")));
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  // Run the below update statements for the below test case to work
  // update session
  // set [Status] = 'Open'
  // , datebegin = '2012-05-29'
  // , dateend = '2020-05-29'
  // , datechanged = '2012-05-29'
  // where _key in ('DB5AACDA-D724-4F49-98B1-C89E5267AB32',
  // 'CC0CCD9C-A69D-4C1A-9512-A63F4A840922',
  // 'FEA63E2D-B102-4920-88B0-E6011B8C837E'
  // , 'E483B532-264C-4F27-A16E-005FF85CA7C7' ,
  // 'DF9B19BA-C0EB-4781-9E47-08534CF115A4')
  //
  // update TestOpportunity_ReadOnly
  // set DateChanged = getdate()
  // where _fk_session = 'FEA63E2D-B102-4920-88B0-E6011B8C837E'

  // The procedure will not return any values, if success. The column ‘Status’
  // in sessions table for the following sessionKeys will be set to ‘Closed’
  // after the test case is executed.
  // select * from session where _key in
  // ('16C36F33-AE91-4EC6-A38B-5012BDC1BFD5',
  // 'CC0CCD9C-A69D-4C1A-9512-A63F4A840922')

  @Test
  public void test_P_GetCurrentSessions_SP () throws SQLException, ReturnStatusException {
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      String clientName = "Oregon";// "Minnesota";
      Long proctorKey = 20424L; // 2L
      int sessionType = 0;
      UUID sessionkey1 = UUID.fromString ("1CC384CE-CBA9-44FE-81C1-07DBBE2F4394");// ("DB5AACDA-D724-4F49-98B1-C89E5267AB32");
      UUID sessionkey2 = UUID.fromString ("CC0CCD9C-A69D-4C1A-9512-A63F4A840922");
      UUID sessionkey3 = UUID.fromString ("FEA63E2D-B102-4920-88B0-E6011B8C837E");
      UUID sessionkey4 = UUID.fromString ("E483B532-264C-4F27-A16E-005FF85CA7C7");
      UUID sessionkey5 = UUID.fromString ("DF9B19BA-C0EB-4781-9E47-08534CF115A4");
      Date now = _dateUtil.getDate (connection);

      final String query = "update session set Status = 'Open', datebegin = '2012-05-29', dateend = '2020-05-29',  datechanged = '2012-05-29' where _key in (${sessionkey1}, ${sessionkey2}, ${sessionkey3}, ${sessionkey4}, ${sessionkey5})";
      SqlParametersMaps parms = (new SqlParametersMaps ()).put ("sessionkey1", sessionkey1).put ("sessionkey2", sessionkey2).put ("sessionkey3", sessionkey3).put ("sessionkey4", sessionkey4)
          .put ("sessionkey5", sessionkey5);
      _myDllHelper.executeStatement (connection, query, parms, false);

      final String query2 = "update session set dateVisited = ${now} where _key = ${sessionkey}";
      SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("now", now).put ("sessionkey", sessionkey1);
      _myDllHelper.executeStatement (connection, query2, parms2, false);

      final String query1 = "update testopportunity_readonly set DateChanged = ${now} where _fk_session = ${sessionkey}";
      SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("sessionkey", sessionkey3).put ("now", now);
      _myDllHelper.executeStatement (connection, query1, parms1, false);

      Date start = new Date ();
      SingleDataResultSet result = _dll.P_GetCurrentSessions_SP (connection, clientName, proctorKey, sessionType);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_getCurrentSessions latency: %d millisec", diff));

      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("=================== P_GetCurrentSessions Records=======================");
        _logger.info (String.format ("_Key: %s", record.<String> get ("_Key")));
        _logger.info (String.format ("sessionID: %s ", record.<String> get ("sessionID")));
        _logger.info (String.format ("sessionName: %s ", record.<String> get ("sessionName")));
        _logger.info (String.format ("status: %s ", record.<String> get ("status")));
        _logger.info (String.format ("dateBegin: %s ", record.<Date> get ("dateBegin")));
        _logger.info (String.format ("dateEnd: %s ", record.<Date> get ("dateEnd")));
        _logger.info (String.format ("browserKey: %s ", record.<String> get ("browserKey")));
        _logger.info (String.format ("NeedApproval: %d ", record.<Integer> get ("NeedApproval")));
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  @Test
  public void test_P_GetSessionTests_SP () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("C2E01F55-0FA4-4E43-AAD9-B62233F8A490");
    Long proctorKey = 20628L;
    UUID browserKey = UUID.fromString ("2BB0568E-ED4D-42AD-B493-994AC51EC398");
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.P_GetSessionTests_SP (connection, sessionKey, proctorKey, browserKey);
      assertTrue (result.getCount () == 30);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("=================== P_GetSessionTests Records=======================");
        _logger.info (String.format ("TestID: %s", record.<String> get ("TestID")));
        _logger.info (String.format ("TestKey: %s ", record.<String> get ("TestKey")));
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  @Test
  // failure case #1
  public void test_P_GetSessionTests_SP1 () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("38893E8D-A5D2-4BF8-906E-3C2CBFBACC30");
    Long proctorKey = 24352L;
    UUID browserKey = UUID.fromString ("A3161C78-314F-4337-90D4-2B0FCB50C9DF");
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      Date start = new Date ();
      SingleDataResultSet result = _dll.P_GetSessionTests_SP (connection, sessionKey, proctorKey, browserKey);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_GetSessionTests latency: %d millisec", diff));
      DbResultRecord record = result.getRecords ().next ();
      assertTrue (record != null);
      String status = record.<String> get ("status");
      String reason = record.<String> get ("Reason");
      String context = record.<String> get ("context");
      String appkey = record.<String> get ("appkey");
      _logger.info (String.format ("Status: %s", status));
      _logger.info (String.format ("Reason: %s", reason));
      _logger.info (String.format ("context: %s", context));
      _logger.info (String.format ("appkey: %s", appkey));
      assertTrue ("failed".equalsIgnoreCase (status));
      assertTrue (("This test session has been moved to another computer/browser. All active tests will continue without interruption. [11603]").equalsIgnoreCase (reason));
      assertTrue ("ValidateProctorSession".equalsIgnoreCase (context));
      assertTrue ("Unauthorized session access".equalsIgnoreCase (appkey));
    }
  }

  // Need to run this update statement for the below test case to work
  // update session
  // set [Status] = 'Open'
  // , datebegin = '2012-05-29'
  // , dateend = '2020-05-29'
  // , datecreated = '2012-05-29'
  // , datechanged = '2012-05-29'
  // where _key = 'FEA63E2D-B102-4920-88B0-E6011B8C837E'

  @Test
  // success case
  public void test_P_GetTestsForApproval_SP () throws ReturnStatusException, SQLException {
    UUID sessionKey = UUID.fromString ("FEA63E2D-B102-4920-88B0-E6011B8C837E");
    Long proctorKey = 20424L;
    UUID browserKey = UUID.fromString ("9318C537-EC05-41ED-AB08-39ECE4E8D605");
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      final String query = "update session set Status = 'Open', datebegin = '2012-05-29', dateend = '2020-05-29', datecreated = '2012-05-29', datechanged = '2012-05-29' where _key = ${sessionkey}";
      SqlParametersMaps parms = (new SqlParametersMaps ()).put ("sessionkey", sessionKey);
      _myDllHelper.executeStatement (connection, query, parms, false);

      Date start = new Date ();
      MultiDataResultSet resultsets = _dll.P_GetTestsForApproval_SP (connection, sessionKey, proctorKey, browserKey);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_GetTestsForApproval latency: %d millisec", diff));
      if (resultsets.getUpdateCount () > 0) {
        Iterator<SingleDataResultSet> iterator = resultsets.getResultSets ();
        int resultIdx = 0;
        while (iterator.hasNext ()) {
          SingleDataResultSet set = iterator.next ();
          if (resultIdx == 0) {
            assertTrue (set.getCount () == 1);
            DbResultRecord record = set.getRecords ().next ();
            if (record != null) {
              _logger.info ("===============P_GetTestsForApproval Record=======================");
              _logger.info (String.format ("opportunityKey: %s", record.<String> get ("opportunityKey")));
              _logger.info (String.format ("_efk_testee: %d", record.<Long> get ("_efk_testee")));
              _logger.info (String.format ("_efk_TestID: %s", record.<String> get ("_efk_TestID")));
              _logger.info (String.format ("Opportunity: %d", record.<Integer> get ("Opportunity")));
              _logger.info (String.format ("_efk_AdminSubject: %s", record.<String> get ("_efk_AdminSubject")));
              _logger.info (String.format ("status: %s", record.<String> get ("status")));
              _logger.info (String.format ("testeeID: %s", record.<String> get ("testeeID")));
              _logger.info (String.format ("testeeName: %s", record.<String> get ("testeeName")));
              _logger.info (String.format ("customAccommodations: %s", record.<Boolean> get ("customAccommodations")));
              _logger.info (String.format ("waitingForSegment: %s", record.<Integer> get ("waitingForSegment")));
              _logger.info (String.format ("mode: %s", record.<String> get ("mode")));
              _logger.info (String.format ("LEP: %s", record.<String> get ("LEP")));

              assertTrue (UUID.fromString ("FFF95930-B368-4D4A-B657-30130BA87AC4").equals (record.<String> get ("opportunityKey")));
              assertTrue (record.<Long> get ("_efk_testee") == -2023);
              assertTrue ("DCAS-Training-9-11".equalsIgnoreCase (record.<String> get ("_efk_TestID")));
              assertTrue (record.<Integer> get ("Opportunity") == 1);
              assertTrue ("(Delaware_PT)DCAS-Training-9-11-Fall-2011-2012".equalsIgnoreCase (record.<String> get ("_efk_AdminSubject")));
              assertTrue ("pending".equalsIgnoreCase (record.<String> get ("status")));
              assertTrue ("GUEST".equalsIgnoreCase (record.<String> get ("testeeID")));
              assertTrue ("GUEST".equalsIgnoreCase (record.<String> get ("testeeName")));
              assertTrue (record.<Boolean> get ("customAccommodations") == false);
              assertTrue (record.<Integer> get ("waitingForSegment") == 1);
              assertTrue ("online".equalsIgnoreCase (record.<String> get ("mode")));
              assertTrue (record.<String> get ("LEP") == null);
            }
            _logger.info (String.format ("Total no: of records -- %d", set.getCount ()));
          }
          if (resultIdx == 1) {
            Iterator<DbResultRecord> iteratorRec = set.getRecords ();
            assertTrue (set.getCount () == 21);
            while (iteratorRec.hasNext ()) {
              _logger.info ("===============P_GetTestsForApproval Records=======================");
              DbResultRecord record = iteratorRec.next ();
              _logger.info (String.format ("oppKey: %s", record.<String> get ("oppKey")));
              _logger.info (String.format ("AccType : %s ", record.<String> get ("AccType")));
              _logger.info (String.format ("AccCode: %s", record.<String> get ("AccCode")));
              _logger.info (String.format ("AccValue: %s", record.<String> get ("AccValue")));
              _logger.info (String.format ("segment: %d", record.<Integer> get ("segment")));
              _logger.info (String.format ("isSelectable: %s", record.<Boolean> get ("isSelectable")));
              _logger.info (String.format ("opportunityKey: %s", record.<String> get ("opportunityKey")));
              _logger.info (String.format ("_efk_testee: %d", record.<Long> get ("_efk_testee")));
              _logger.info (String.format ("_efk_TestID: %s", record.<String> get ("_efk_TestID")));
              _logger.info (String.format ("Opportunity: %d", record.<Integer> get ("Opportunity")));
              _logger.info (String.format ("_efk_AdminSubject: %s", record.<String> get ("_efk_AdminSubject")));
              _logger.info (String.format ("status: %s", record.<String> get ("status")));
              _logger.info (String.format ("testeeID: %s", record.<String> get ("testeeID")));
              _logger.info (String.format ("testeeName: %s", record.<String> get ("testeeName")));
              _logger.info (String.format ("customAccommodations: %s", record.<Boolean> get ("customAccommodations")));
              _logger.info (String.format ("waitingForSegment: %s", record.<Integer> get ("waitingForSegment")));
              _logger.info (String.format ("mode: %s", record.<String> get ("mode")));
              _logger.info (String.format ("LEP: %s", record.<String> get ("LEP")));

              assertTrue (UUID.fromString ("FFF95930-B368-4D4A-B657-30130BA87AC4").equals (record.<String> get ("oppKey")));
              assertTrue (record.<Integer> get ("segment") == 0);
              assertTrue (UUID.fromString ("FFF95930-B368-4D4A-B657-30130BA87AC4").equals (record.<String> get ("opportunityKey")));
              assertTrue (record.<Long> get ("_efk_testee") == -2023);
              assertTrue ("DCAS-Training-9-11".equalsIgnoreCase (record.<String> get ("_efk_TestID")));
              assertTrue (record.<Integer> get ("Opportunity") == 1);
              assertTrue ("(Delaware_PT)DCAS-Training-9-11-Fall-2011-2012".equalsIgnoreCase (record.<String> get ("_efk_AdminSubject")));
              assertTrue ("pending".equalsIgnoreCase (record.<String> get ("status")));
              assertTrue ("GUEST".equalsIgnoreCase (record.<String> get ("testeeID")));
              assertTrue ("GUEST".equalsIgnoreCase (record.<String> get ("testeeName")));
              assertTrue (record.<Boolean> get ("customAccommodations") == false);
              assertTrue (record.<Integer> get ("waitingForSegment") == 1);
              assertTrue ("online".equalsIgnoreCase (record.<String> get ("mode")));
              assertTrue (record.<String> get ("LEP") == null);
            }
            _logger.info (String.format ("Total no: of records -- %d", set.getCount ()));
          }
          resultIdx++;
        }
      }
    }
  }

  @Test
  // failure case #1
  public void test_P_GetTestsForApproval_SP1 () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("FEA67E2D-B102-4920-88B0-E6011B8C837E");
    Long proctorKey = 20424L;
    UUID browserKey = UUID.fromString ("9318C537-EC05-41ED-AB08-39ECE4E8D605");
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      Date start = new Date ();
      MultiDataResultSet resultsets = _dll.P_GetTestsForApproval_SP (connection, sessionKey, proctorKey, browserKey);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_GetTestForApproval latency: %d millisec", diff));

      SingleDataResultSet set = resultsets.getResultSets ().next ();
      DbResultRecord record = set.getRecords ().next ();
      assertTrue (record != null);
      String status = record.<String> get ("status");
      String reason = record.<String> get ("Reason");
      String context = record.<String> get ("context");
      String appkey = record.<String> get ("appkey");
      _logger.info (String.format ("Status: %s", status));
      _logger.info (String.format ("Reason: %s", reason));
      _logger.info (String.format ("context: %s", context));
      _logger.info (String.format ("appkey: %s", appkey));
      assertTrue ("failed".equalsIgnoreCase (status));
      assertTrue (("The session is closed. [11601]").equalsIgnoreCase (reason));
      assertTrue ("ValidateProctorSession".equalsIgnoreCase (context));
      assertTrue ("The session is closed.".equalsIgnoreCase (appkey));
    }
  }

  @Test
  public void testP_GetUnAcknowledgedAlertMessages_SP () throws SQLException, ReturnStatusException {
    String clientName = "Hawaii"; // SBAC
    Long proctorKey = 20424L; // 35837
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.P_GetUnAcknowledgedAlertMessages_SP (connection, clientName, proctorKey);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("===================P_GetUnAcknowledgedAlertMessages Result=======================");
        _logger.info (String.format ("_key: %s", record.<String> get ("_key")));
        _logger.info (String.format ("title: %s ", record.<String> get ("title")));
        _logger.info (String.format ("message: %s ", record.<String> get ("message")));
        _logger.info (String.format ("dateCreated %s ", record.<Date> get ("dateCreated")));
        _logger.info (String.format ("createdUser: %s", record.<String> get ("createdUser")));
        _logger.info (String.format ("dateUpdated %s ", record.<Date> get ("dateUpdated")));
        _logger.info (String.format ("updatedUser %s ", record.<String> get ("updatedUser")));
        _logger.info (String.format ("dateStarted %s ", record.<Date> get ("dateStarted")));
        _logger.info (String.format ("dateEnded %s ", record.<Date> get ("dateEnded")));
        _logger.info (String.format ("dateCancelled %s ", record.<Date> get ("dateCancelled")));
        _logger.info (String.format ("cancelledUser %s ", record.<String> get ("cancelledUser")));
      }
    }
  }

  // --For success case; a record will be inserted into SessionAudit table for
  // the given date
  // select *
  // from SessionAudit
  // where _fk_session = 'DF9B19BA-C0EB-4781-9E47-08534CF115A4'
  // and cast(floor(cast(DateAccessed as float)) as smalldatetime) =
  // cast(floor(cast(getdate() as float)) as smalldatetime)
  // and browserkey = '93A42BE2-B8F6-4248-BFA7-12900410D5FC'

  @Test
  // success case
  public void test_P_HandOffSession_SP () throws ReturnStatusException, SQLException {
    String clientName = "Oregon";
    Long proctorKey = 26496L;
    String sessionID = "air-50";
    UUID browserKey = UUID.fromString ("93A42BE2-B8F6-4248-BFA7-12900410D5FC");
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.P_HandOffSession_SP (connection, clientName, proctorKey, sessionID, browserKey);
      DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
      assertTrue (record != null);
      UUID sessionKey = record.<UUID> get ("sessionKey");
      _logger.info (String.format ("sessionKey: %s", sessionKey));
      assertTrue (sessionKey.equals (UUID.fromString ("DF9B19BA-C0EB-4781-9E47-08534CF115A4")));
    }
  }

  @Test
  // failure case
  public void test_P_HandOffSession_SP1 () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    Long proctorKey = 2649L;
    String sessionID = "air-50";
    UUID browserKey = UUID.fromString ("93A42BE2-B8F6-4248-BFA7-12900410D5FC");
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.P_HandOffSession_SP (connection, clientName, proctorKey, sessionID, browserKey);
      DbResultRecord record = result.getRecords ().next ();
      assertTrue (record != null);
      String status = record.<String> get ("status");
      String reason = record.<String> get ("Reason");
      String context = record.<String> get ("context");
      String appkey = record.<String> get ("appkey");
      _logger.info (String.format ("Status: %s", status));
      _logger.info (String.format ("Reason: %s", reason));
      _logger.info (String.format ("context: %s", context));
      _logger.info (String.format ("appkey: %s", appkey));
      assertTrue ("failed".equalsIgnoreCase (status));
      assertTrue (("The session does not exist [-----]").equalsIgnoreCase (reason));
      assertTrue ("P_HandoffSession".equalsIgnoreCase (context));
      assertTrue ("The session does not exist".equalsIgnoreCase (appkey));
    }
  }

  // --Need to run delete statement every time before the running the testcase
  // for it to work
  // delete
  // from SessionTests
  // where _fk_Session = 'C2E01F55-0FA4-4E43-AAD9-B62233F8A490'
  // and _efk_AdminSubject = '(Oregon_PT)ELPA-6-8-Fall-2012-2013'

  @Test
  // success case
  public void test_P_InsertSessionTest_SP () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("C2E01F55-0FA4-4E43-AAD9-B62233F8A490");
    Long proctorKey = 20628L;
    UUID browserKey = UUID.fromString ("2BB0568E-ED4D-42AD-B493-994AC51EC398");
    String testKey = "(Oregon_PT)ELPA-6-8-Fall-2012-2013";
    String testId = "ELPA 6-8";
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      final String query = "delete from sessiontests where _fk_Session = ${sessionkey} and _efk_AdminSubject = ${testkey}";
      SqlParametersMaps parms = (new SqlParametersMaps ()).put ("sessionkey", sessionKey).put ("testkey", testKey);
      _myDllHelper.executeStatement (connection, query, parms, false);
      Date start = new Date ();
      SingleDataResultSet result = _dll.P_InsertSessionTest_SP (connection, sessionKey, proctorKey, browserKey, testKey, testId);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_InsertSessionTest latency: %d millisec", diff));
      DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
      assertTrue (record != null);
      if (record != null) {
        _logger.info (String.format ("Status: %s", record.<String> get ("status")));
        _logger.info (String.format ("Reason: %s", record.<String> get ("reason")));
        assertTrue ("success".equalsIgnoreCase (record.<String> get ("status")));
        assertTrue (record.<String> get ("reason") == null);
      }
    }
  }

  @Test
  // failure case
  public void test_P_InsertSessionTest_SP1 () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("C2E01F55-0FA4-4E43-AAD9-B62233F8A490");
    Long proctorKey = 20628L;
    UUID browserKey = UUID.fromString ("2BB0568E-ED4D-42AD-B493-994AC51EC398");
    String testKey = "(Oregon)Oregon-CoreOnly-ELPA-K-1-Winter-2012-2013";
    String testId = "Oregon-CoreOnly-ELPA-K-1";
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.P_InsertSessionTest_SP (connection, sessionKey, proctorKey, browserKey, testKey, testId);
      DbResultRecord record = result.getRecords ().next ();
      assertTrue (record != null);
      String status = record.<String> get ("status");
      String reason = record.<String> get ("Reason");
      String context = record.<String> get ("context");
      String appkey = record.<String> get ("appkey");
      _logger.info (String.format ("Status: %s", status));
      _logger.info (String.format ("Reason: %s", reason));
      _logger.info (String.format ("context: %s", context));
      _logger.info (String.format ("appkey: %s", appkey));
      assertTrue ("failed".equalsIgnoreCase (status));
      assertTrue (("SessionTestExists [-----]").equalsIgnoreCase (reason));
      assertTrue ("P_InsertSessionTest".equalsIgnoreCase (context));
      assertTrue ("SessionTestExists".equalsIgnoreCase (appkey));
    }
  }

  // --Run these 2 update stmts prior to executing TEST CASE#1
  // update session
  // set status = 'Open'
  // where clientname = 'SBAC'
  // and _efk_Proctor = 5
  // and _fk_browser = '689502E2-BD87-44FD-BA41-4470DC60C87A'
  // and sessiontype = 0
  //
  // update TestOpportunity
  // set [Status] = 'approved'
  // , PrevStatus = 'pending'
  // where _key in ('42A0EF7F-81B6-474C-A59C-E76A7CEBE3FE',
  // '9014AC83-0253-424D-976F-F515A0A371B2')

  @Test
  public void test_P_PauseAllSessions_SP () throws SQLException, ReturnStatusException {
    String clientName = "SBAC";
    Long proctorKey = 5L;
    UUID browserKey = UUID.fromString ("689502E2-BD87-44FD-BA41-4470DC60C87A");
    int exemptCurrent = 1;
    int reportClosed = 1;
    int sessionType = 0;
    UUID oppkey1 = UUID.fromString ("42A0EF7F-81B6-474C-A59C-E76A7CEBE3FE");
    UUID oppkey2 = UUID.fromString ("9014AC83-0253-424D-976F-F515A0A371B2");
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      final String cmd = "update session set status = 'Open' where clientname = ${clientname} and _efk_Proctor = ${proctorKey} "
          + " and _fk_browser = ${browserkey} and sessiontype = ${sessionType}";
      SqlParametersMaps parms = (new SqlParametersMaps ()).put ("browserkey", browserKey).put ("clientname", clientName).
          put ("proctorKey", proctorKey).put ("sessionType", sessionType);
      _myDllHelper.executeStatement (connection, cmd, parms, false);

      final String cmd1 = "update testopportunity set status = 'approved', PrevStatus = 'pending' where _key in (${oppkey1}, ${oppkey2})";
      SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey1", oppkey1).put ("oppkey2", oppkey2);
      _myDllHelper.executeStatement (connection, cmd1, parms1, false);

      Date start = new Date ();
      SingleDataResultSet result = _dll.P_PauseAllSessions_SP (connection, clientName, proctorKey, browserKey, exemptCurrent, reportClosed, sessionType);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_PauseAllSessions latency: %d millisec", diff));
      // We are only asked to report closed session. It means, we will get null
      // statement meaning everything is okay and no sessions to be paused
      // because all were closed
      DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
      assertTrue (record != null);
      if (record != null) {
        _logger.info (String.format ("Status: %s", record.<String> get ("status")));
        _logger.info (String.format ("number: %d", record.<Long> get ("number")));
        _logger.info (String.format ("Reason: %s", record.<String> get ("reason")));

        assertTrue ("closed".equalsIgnoreCase (record.<String> get ("status")));
        assertTrue (record.<Long> get ("number") == 1);
        assertTrue (record.<String> get ("reason") == null);
      }
    }
  }

  // --Need to run these update statement every time before the running the
  // testcase for it to work
  // update session
  // set status = 'Open'
  // where clientname = 'SBAC'
  // and _efk_Proctor = 2
  // and _fk_browser = 'EB9C0E65-E667-4AE5-870D-D201448C9442'
  // and sessiontype = 0
  @Test
  public void test_P_LogOutProctor_SP () throws SQLException, ReturnStatusException {
    String clientName = "SBAC";
    Long proctorKey = 2L;
    UUID browserKey = UUID.fromString ("EB9C0E65-E667-4AE5-870D-D201448C9442");
    int sessionType = 0;
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      final String cmd = "update session set status = 'Open' where clientname = ${clientname} and _efk_Proctor = 2 and _fk_browser = ${browserkey} and sessiontype = 0";
      SqlParametersMaps parms = (new SqlParametersMaps ()).put ("browserkey", browserKey).put ("clientname", clientName);
      _myDllHelper.executeStatement (connection, cmd, parms, false);

      Date start = new Date ();
      SingleDataResultSet result = _dll.P_LogOutProctor_SP (connection, clientName, proctorKey, browserKey, sessionType);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_LogoutProctor latency: %d millisec", diff));
      // We are only asked to report closed session. It means, we will get null
      // statement meaning everything is okay and no sessions to be paused
      // because all were closed
      DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
      assertTrue (record != null);
      if (record != null) {
        _logger.info (String.format ("Status: %s", record.<String> get ("status")));
        _logger.info (String.format ("number: %d", record.<Long> get ("number")));
        _logger.info (String.format ("Reason: %s", record.<String> get ("reason")));
        assertTrue ("closed".equalsIgnoreCase (record.<String> get ("status")));
        assertTrue (record.<Long> get ("number") == 2);
        assertTrue (record.<String> get ("reason") == null);
      }
    }
  }

  // -- # Run update once before each execution for test CASE#2 to work #
  // update TestOpportunity
  // set status = 'started'
  // , prevstatus = 'approved'
  // , datechanged = '2012-05-29'
  // , comment = NULL
  // , datepaused = NULL
  // where _key = '2FE4069F-383A-4945-BFF1-E88BE3BACADF'

  @Test
  // success case
  public void test_P_PauseOpportunity_SP () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("B3922143-B287-4D5B-9364-0F9BAB10398F");
    Long proctorKey = 20628L;
    UUID browserKey = UUID.fromString ("062819DE-DDAF-44D0-8977-EBC4C002BDF0");
    UUID opportunityKey = UUID.fromString ("ABFC402F-07A5-488A-BBFD-FF1AAB7EFAD8");
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      final String cmd = "update testopportunity set status = 'started', prevstatus = 'approved', datechanged = '2012-05-29', comment = NULL, datepaused = NULL where _key = ${oppkey}";
      SqlParametersMaps parms = (new SqlParametersMaps ()).put ("oppkey", opportunityKey);
      _myDllHelper.executeStatement (connection, cmd, parms, false);

      Date start = new Date ();
      SingleDataResultSet result = _dll.P_PauseOpportunity_SP (connection, sessionKey, proctorKey, browserKey, opportunityKey);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_PauseOpportunity latency: %d millisec", diff));
      DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
      assertTrue (record != null);
      if (record != null) {
        _logger.info (String.format ("Status: %s", record.<String> get ("status")));
        _logger.info (String.format ("Reason: %s", record.<String> get ("reason")));
        assertTrue ("paused".equalsIgnoreCase (record.<String> get ("status")));
        assertTrue (record.<String> get ("reason") == null);
      }
    }
  }

  @Test
  // failure case
  public void test_P_PauseOpportunity_SP1 () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("38893E8D-A5D2-4BF8-906E-3C2CBFBACC30");
    Long proctorKey = 24352L;
    UUID browserKey = UUID.fromString ("A3161C78-314F-4337-90D4-2B0FCB50C9DF");
    UUID opportunityKey = UUID.fromString ("9CC6B36B-6A38-436D-9EDB-00010D25F2A7");
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      Date start = new Date ();
      SingleDataResultSet result = _dll.P_PauseOpportunity_SP (connection, sessionKey, proctorKey, browserKey, opportunityKey);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_PauseOpportunity latency: %d millisec", diff));
      DbResultRecord record = result.getRecords ().next ();
      assertTrue (record != null);
      String status = record.<String> get ("status");
      String reason = record.<String> get ("Reason");
      String context = record.<String> get ("context");
      String appkey = record.<String> get ("appkey");
      _logger.info (String.format ("Status: %s", status));
      _logger.info (String.format ("Reason: %s", reason));
      _logger.info (String.format ("context: %s", context));
      _logger.info (String.format ("appkey: %s", appkey));
      assertTrue ("failed".equalsIgnoreCase (status));
      assertTrue (("This test session has been moved to another computer/browser. All active tests will continue without interruption. [11603]").equalsIgnoreCase (reason));
      assertTrue ("ValidateProctorSession".equalsIgnoreCase (context));
      assertTrue ("Unauthorized session access".equalsIgnoreCase (appkey));
    }
  }

  @Test
  public void test_P_ResumeAllSessions_SP () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    Long proctorKey = 20628L;
    UUID browserKey = UUID.fromString ("062819DE-DDAF-44D0-8977-EBC4C002BDF0");
    int suppressOutput = 0;
    int sessionType = 0;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.P_ResumeAllSessions_SP (connection, clientName, proctorKey, browserKey, suppressOutput, sessionType);
      DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
      assertTrue (record != null);
      if (record != null) {
        _logger.info (String.format ("Status: %s", record.<String> get ("status")));
        _logger.info (String.format ("number: %d", record.<Integer> get ("number")));
        _logger.info (String.format ("Reason: %s", record.<String> get ("reason")));
        assertTrue ("open".equalsIgnoreCase (record.<String> get ("status")));
        assertTrue (record.<Integer> get ("number") == 3);
        assertTrue (record.<String> get ("reason") == null);
      }
    }
  }

  // -- # Run update once before each execution for test case to work
  // update TestOpportunity
  // set status = 'pending'
  // , prevstatus = 'approved'
  // , datechanged = '2012-05-29'
  // , comment = NULL
  // where _key = '2FE4069F-383A-4945-BFF1-E88BE3BACADF'

  @Test
  // success case
  public void test_P_DenyApproval_SP () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("9FBB7B46-9D8F-42C8-AF85-4ED2FD2E4777");
    Long proctorKey = 32L;
    UUID browserKey = UUID.fromString ("C7145D62-A4C5-4345-B633-87678FBE2EF7");
    UUID opportunityKey = UUID.fromString ("2FE4069F-383A-4945-BFF1-E88BE3BACADF");
    String reason = "Test Reason - Case#4";
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      final String cmd = "update testopportunity set status = 'pending', prevstatus = 'approved', datechanged = '2012-05-29', comment = NULL, datepaused = NULL where _key = ${oppkey}";
      SqlParametersMaps parms = (new SqlParametersMaps ()).put ("oppkey", opportunityKey);
      _myDllHelper.executeStatement (connection, cmd, parms, false);

      Date start = new Date ();
      SingleDataResultSet result = _dll.P_DenyApproval_SP (connection, sessionKey, proctorKey, browserKey, opportunityKey, reason);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_DenyApproval latency: %d millisec", diff));
      DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
      assertTrue (record != null);
      if (record != null) {
        _logger.info (String.format ("Status: %s", record.<String> get ("status")));
        _logger.info (String.format ("Reason: %s", record.<String> get ("reason")));
        assertTrue ("denied".equalsIgnoreCase (record.<String> get ("status")));
        assertTrue (record.<String> get ("reason") == null);
      }
    }
  }

  @Test
  // failure case #1
  public void test_P_DenyApproval_SP1 () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("38893E8D-A5D2-4BF8-906E-3C2CBFBACC30");
    Long proctorKey = 24352L;
    UUID browserKey = UUID.fromString ("A3161C78-314F-4337-90D4-2B0FCB50C9DF");
    UUID opportunityKey = UUID.fromString ("9CC6B36B-6A38-436D-9EDB-00010D25F2A7");
    String reason1 = "Test Reason - Case#3";
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.P_DenyApproval_SP (connection, sessionKey, proctorKey, browserKey, opportunityKey, reason1);
      DbResultRecord record = result.getRecords ().next ();
      assertTrue (record != null);
      String status = record.<String> get ("status");
      String reason = record.<String> get ("Reason");
      String context = record.<String> get ("context");
      String appkey = record.<String> get ("appkey");
      _logger.info (String.format ("Status: %s", status));
      _logger.info (String.format ("Reason: %s", reason));
      _logger.info (String.format ("context: %s", context));
      _logger.info (String.format ("appkey: %s", appkey));
      assertTrue ("failed".equalsIgnoreCase (status));
      assertTrue (("This session has been moved to another computer/browser. All active item samplers will continue without interruption. [11603]").equalsIgnoreCase (reason));
      assertTrue ("ValidateProctorSession".equalsIgnoreCase (context));
      assertTrue ("Unauthorized session access".equalsIgnoreCase (appkey));
    }
  }

  @Test
  // failure case #2
  public void test_P_DenyApproval_SP2 () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("38893E8D-A5D2-4BF8-906E-3C2CBFBACC30");
    Long proctorKey = 24352L;
    UUID browserKey = UUID.fromString ("EEABB7D2-DADA-4DD8-B82F-5CE44AD871CE");
    UUID opportunityKey = UUID.fromString ("9CC6B36B-6A38-436D-9EDB-00010D25F2A7");
    String reason1 = "Test Reason - Case#2";
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.P_DenyApproval_SP (connection, sessionKey, proctorKey, browserKey, opportunityKey, reason1);
      DbResultRecord record = result.getRecords ().next ();
      assertTrue (record != null);
      String status = record.<String> get ("status");
      String reason = record.<String> get ("Reason");
      String context = record.<String> get ("context");
      String appkey = record.<String> get ("appkey");
      _logger.info (String.format ("Status: %s", status));
      _logger.info (String.format ("Reason: %s", reason));
      _logger.info (String.format ("context: %s", context));
      _logger.info (String.format ("appkey: %s", appkey));
      assertTrue ("failed".equalsIgnoreCase (status));
      assertTrue (("The test opportunity is not pending approval [10193]").equalsIgnoreCase (reason));
      assertTrue ("P_DenyApproval".equalsIgnoreCase (context));
      assertTrue ("The test opportunity is not pending approval".equalsIgnoreCase (appkey));
    }
  }

  @Test
  // failure case #3
  public void test_P_DenyApproval_SP3 () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("38893E8D-A5D2-4BF8-906E-3C2CBFBACC30");
    Long proctorKey = 24352L;
    UUID browserKey = UUID.fromString ("EEABB7D2-DADA-4DD8-B82F-5CE44AD871CE");
    UUID opportunityKey = UUID.fromString ("4308172C-7FCF-42A9-8CE7-005299111EED");
    String reason1 = null;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      Date start = new Date ();
      SingleDataResultSet result = _dll.P_DenyApproval_SP (connection, sessionKey, proctorKey, browserKey, opportunityKey, reason1);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_DenyApproval latency: %d millisec", diff));
      DbResultRecord record = result.getRecords ().next ();
      assertTrue (record != null);
      String status = record.<String> get ("status");
      String reason = record.<String> get ("Reason");
      String context = record.<String> get ("context");
      String appkey = record.<String> get ("appkey");
      _logger.info (String.format ("Status: %s", status));
      _logger.info (String.format ("Reason: %s", reason));
      _logger.info (String.format ("context: %s", context));
      _logger.info (String.format ("appkey: %s", appkey));
      assertTrue ("failed".equalsIgnoreCase (status));
      assertTrue (("The test opportunity is in progress [10192]").equalsIgnoreCase (reason));
      assertTrue ("P_DenyApproval".equalsIgnoreCase (context));
      assertTrue ("The test opportunity is in progress".equalsIgnoreCase (appkey));
    }
  }

  // --run once every day for the test case to work
  // update TestOppRequest
  // set DateSubmitted = getdate()
  // where _fk_Session = '1558D3D3-5C43-4E6A-9E1E-CD4A0A33D0E5'

  @Test
  // success case
  public void test_P_GetApprovedTesteeRequests_SP () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("1558D3D3-5C43-4E6A-9E1E-CD4A0A33D0E5");
    Long proctorKey = 24348L;
    UUID browserKey = UUID.fromString ("55FF687A-10FE-47E8-93A6-DE922B8FB28D");
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      Date now = _dateUtil.getDate (connection);

      final String cmd = "update testopprequest set DateSubmitted = ${now} where _fk_Session = ${sessionkey}";
      SqlParametersMaps parms = (new SqlParametersMaps ()).put ("sessionkey", sessionKey).put ("now", now);
      _myDllHelper.executeStatement (connection, cmd, parms, false);

      Date start = new Date ();
      SingleDataResultSet result = _dll.P_GetApprovedTesteeRequests_SP (connection, sessionKey, proctorKey, browserKey);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_GetApprovedTesteeRequests latency: %d millisec", diff));
      assertTrue (result.getCount () == 1);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("===================P_GetApprovedTesteeRequests Result=======================");
        _logger.info (String.format ("_key: %s", record.<String> get ("_key")));
        _logger.info (String.format ("_fk_TestOpportunity: %s ", record.<String> get ("_fk_TestOpportunity")));
        _logger.info (String.format ("_fk_Session: %s ", record.<String> get ("_fk_Session")));
        _logger.info (String.format ("RequestType: %s ", record.<String> get ("RequestType")));
        _logger.info (String.format ("RequestValue: %s", record.<String> get ("RequestValue")));
        _logger.info (String.format ("DateSubmitted: %s ", record.<Date> get ("DateSubmitted")));
        _logger.info (String.format ("DateFulfilled: %s ", record.<Date> get ("DateFulfilled")));
        _logger.info (String.format ("Denied: %s ", record.<String> get ("Denied")));
        _logger.info (String.format ("ItemPage: %d ", record.<Integer> get ("ItemPage")));
        _logger.info (String.format ("ItemPosition: %d ", record.<Integer> get ("ItemPosition")));
        _logger.info (String.format ("RequestDescription: %s ", record.<String> get ("RequestDescription")));

        assertTrue (record.<UUID> get ("_key").equals (UUID.fromString ("6AFE5824-9909-470E-B1D9-60D2FACC1EA1")));
        assertTrue (record.<UUID> get ("_fk_TestOpportunity").equals (UUID.fromString ("5E1498EA-CFC7-4AE6-9CC6-6EE84455569D")));
        assertTrue (record.<UUID> get ("_fk_Session").equals (UUID.fromString ("1558D3D3-5C43-4E6A-9E1E-CD4A0A33D0E5")));
        assertTrue ("PRINTPASSAGE".equalsIgnoreCase (record.<String> get ("RequestType")));
        assertTrue (record.<String> get ("Denied") == null);
        assertTrue (record.<Integer> get ("ItemPage") == 1);
        assertTrue (record.<Integer> get ("ItemPosition") == 0);
        assertTrue ("Passage for Items 1-7".equalsIgnoreCase (record.<String> get ("RequestDescription")));
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  @Test
  // failure case
  public void test_P_GetApprovedTesteeRequests_SP1 () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("1558D3D3-5C43-7E6A-9E1E-CD4A0A33D0E5");
    Long proctorKey = 24348L;
    UUID browserKey = UUID.fromString ("55FF687A-10FE-47E8-93A6-DE922B8FB28D");
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      Date start = new Date ();
      SingleDataResultSet result = _dll.P_GetApprovedTesteeRequests_SP (connection, sessionKey, proctorKey, browserKey);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_ApprovedTesteeRequests latency: %d millisec", diff));
      DbResultRecord record = result.getRecords ().next ();
      assertTrue (record != null);
      String status = record.<String> get ("status");
      String reason = record.<String> get ("Reason");
      String context = record.<String> get ("context");
      String appkey = record.<String> get ("appkey");
      _logger.info (String.format ("Status: %s", status));
      _logger.info (String.format ("Reason: %s", reason));
      _logger.info (String.format ("context: %s", context));
      _logger.info (String.format ("appkey: %s", appkey));
      assertTrue ("failed".equalsIgnoreCase (status));
      assertTrue (("The session is closed. [11601]").equalsIgnoreCase (reason));
      assertTrue ("ValidateProctorSession".equalsIgnoreCase (context));
      assertTrue ("The session is closed.".equalsIgnoreCase (appkey));
    }
  }

  // --run once every day for the test case to work
  // update TestOppRequest
  // set DateSubmitted = getdate()
  // where _fk_Session = '18597734-367C-4F90-B2C6-92A36615481C'
  // and _fk_TestOpportunity = '17062A57-B857-451D-B817-8BEC587787CE'

  @Test
  // success case
  public void test_P_GetCurrentTesteeRequests_SP () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("18597734-367C-4F90-B2C6-92A36615481C");
    Long proctorKey = 258L;
    UUID browserKey = UUID.fromString ("7A814B25-35A3-49C0-9D9E-BCA95CFA670A");
    UUID opportunityKey = UUID.fromString ("17062A57-B857-451D-B817-8BEC587787CE");
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      Date now = _dateUtil.getDate (connection);

      final String cmd = "update testopprequest set DateSubmitted = ${now}, dateFulfilled = null where _fk_Session = ${sessionkey} and _fk_TestOpportunity = ${oppkey} ";
      SqlParametersMaps parms = (new SqlParametersMaps ()).put ("sessionkey", sessionKey).put ("now", now).put ("oppkey", opportunityKey);
      _myDllHelper.executeStatement (connection, cmd, parms, false).getUpdateCount ();

      Date start = new Date ();
      SingleDataResultSet result = _dll.P_GetCurrentTesteeRequests_SP (connection, sessionKey, proctorKey, browserKey, opportunityKey);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_GetCurrentTesteeRequests latency: %d millisec", diff));
      // assertTrue(result.getCount () == 63);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("===================P_GetCurrentTesteeRequests Result=======================");
        _logger.info (String.format ("_key: %s", record.<String> get ("_key")));
        _logger.info (String.format ("_fk_Session: %s ", record.<String> get ("_fk_Session")));
        _logger.info (String.format ("RequestType: %s ", record.<String> get ("RequestType")));
        _logger.info (String.format ("RequestValue: %s", record.<String> get ("RequestValue")));
        _logger.info (String.format ("DateSubmitted: %s ", record.<Date> get ("DateSubmitted")));
        _logger.info (String.format ("DateFulfilled: %s ", record.<Date> get ("DateFulfilled")));
        _logger.info (String.format ("Denied: %s ", record.<String> get ("Denied")));
        _logger.info (String.format ("ItemPage: %d ", record.<Integer> get ("ItemPage")));
        _logger.info (String.format ("ItemPosition: %d ", record.<Integer> get ("ItemPosition")));
        _logger.info (String.format ("RequestDescription: %s ", record.<String> get ("RequestDescription")));

        assertTrue (record.<UUID> get ("_fk_Session").equals (UUID.fromString ("18597734-367C-4F90-B2C6-92A36615481C")));
        assertTrue ("EMBOSSITEM".equalsIgnoreCase (record.<String> get ("RequestType")));
        assertTrue (record.<Date> get ("DateFulfilled") == null);
        // assertTrue(record.<String> get ("Denied") == null);
        // assertTrue(record.<Integer> get ("ItemPage") == 16);
        // assertTrue(record.<Integer> get ("ItemPosition") == 16);
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  @Test
  // failure case
  public void test_P_GetCurrentTesteeRequests_SP1 () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("18597734-377C-4F90-B2C6-92A36615481C");
    Long proctorKey = 258L;
    UUID browserKey = UUID.fromString ("7A814B25-35A3-49C0-9D9E-BCA95CFA670A");
    UUID opportunityKey = UUID.fromString ("17062A57-B897-451D-B817-8BEC587787CE");
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      Date start = new Date ();
      SingleDataResultSet result = _dll.P_GetCurrentTesteeRequests_SP (connection, sessionKey, proctorKey, browserKey, opportunityKey);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_GetCurrentTesteeRequests latency: %d millisec", diff));
      DbResultRecord record = result.getRecords ().next ();
      assertTrue (record != null);
      String status = record.<String> get ("status");
      String reason = record.<String> get ("Reason");
      String context = record.<String> get ("context");
      String appkey = record.<String> get ("appkey");
      _logger.info (String.format ("Status: %s", status));
      _logger.info (String.format ("Reason: %s", reason));
      _logger.info (String.format ("context: %s", context));
      _logger.info (String.format ("appkey: %s", appkey));
      assertTrue ("failed".equalsIgnoreCase (status));
      assertTrue (("The session is closed. [11601]").equalsIgnoreCase (reason));
      assertTrue ("ValidateProctorSession".equalsIgnoreCase (context));
      assertTrue ("The session is closed.".equalsIgnoreCase (appkey));
    }
  }

  @Test
  public void testStimulusFile_FN () throws ReturnStatusException, SQLException {
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      long bankkey = 153;
      long stimKey = 413;
      String path = _dll.ITEMBANK_StimulusFile_FN (connection, bankkey, stimKey);
      _logger.info (String.format ("StimulusFile path -- : %s", path));
      assertTrue ("D:\\DataFiles\\BB_Files\\tds_airws_org\\TDSCore_2012-2013\\Bank-153\\Stimuli\\Stim-153-413\\stim-153-413.xml".equalsIgnoreCase (path));
    }
  }

  @Test
  public void test_P_GetTesteeRequestValues_SP () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("18597734-367C-4F90-B2C6-92A36615481C");
    Long proctorKey = 258L;
    UUID browserKey = UUID.fromString ("7A814B25-35A3-49C0-9D9E-BCA95CFA670A");
    UUID requestKey = UUID.fromString ("52EEE76D-3A8E-41A9-B186-00224DB90F19");
    Boolean markFulfilled = false;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      Date start = new Date ();
      SingleDataResultSet result = _dll.P_GetTesteeRequestValues_SP (connection, sessionKey, proctorKey, browserKey, requestKey, markFulfilled);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_GetTesteeRequestValues latency: %d millisec", diff));
      assertTrue (result.getCount () == 1);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("===================P_GetTesteeRequestValues Result=======================");
        _logger.info (String.format ("opportunityKey: %s", record.<String> get ("opportunityKey")));
        _logger.info (String.format ("_efk_Testee: %d ", record.<Long> get ("_efk_Testee")));
        _logger.info (String.format ("testeeID %s ", record.<String> get ("testeeID")));
        _logger.info (String.format ("TesteeName: %s", record.<String> get ("TesteeName")));
        _logger.info (String.format ("_efk_TestID %s ", record.<String> get ("_efk_TestID")));
        _logger.info (String.format ("Opportunity %d ", record.<Integer> get ("Opportunity")));
        _logger.info (String.format ("RequestType %s ", record.<String> get ("RequestType")));
        _logger.info (String.format ("RequestValue %s ", record.<String> get ("RequestValue")));
        _logger.info (String.format ("ItemPage %d ", record.<Integer> get ("ItemPage")));
        _logger.info (String.format ("ItemPosition %d ", record.<Integer> get ("ItemPosition")));
        _logger.info (String.format ("AccCode %s ", record.<String> get ("AccCode")));
        _logger.info (String.format ("Language %s ", record.<String> get ("Language")));
        _logger.info (String.format ("RequestParameters %s ", record.<String> get ("RequestParameters")));
        _logger.info (String.format ("RequestDescription %s ", record.<String> get ("RequestDescription")));

        assertTrue (record.<UUID> get ("opportunityKey").equals (UUID.fromString ("17062A57-B857-451D-B817-8BEC587787CE")));
        assertTrue (record.<Long> get ("_efk_Testee") == 553471);
        assertTrue ("9999999492".equalsIgnoreCase (record.<String> get ("testeeID")));
        assertTrue ("LUNA, SONYA".equalsIgnoreCase (record.<String> get ("TesteeName")));
        assertTrue ("OAKS-Math-6".equalsIgnoreCase (record.<String> get ("_efk_TestID")));
        assertTrue (record.<Integer> get ("Opportunity") == 1);
        assertTrue ("EMBOSSITEM".equalsIgnoreCase (record.<String> get ("RequestType")));
        assertTrue (record.<Integer> get ("ItemPage") == 16);
        assertTrue (record.<Integer> get ("ItemPosition") == 16);
        assertTrue ("ENU-Braille".equalsIgnoreCase (record.<String> get ("AccCode")));
        assertTrue ("ENU-Braille".equalsIgnoreCase (record.<String> get ("Language")));
        assertTrue ("FileFormat:BRF".equalsIgnoreCase (record.<String> get ("RequestParameters")));
        assertTrue ("Item 16 (BRF)".equalsIgnoreCase (record.<String> get ("RequestDescription")));
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  @Test
  // failure case #1
  public void test_P_GetTesteeRequestValues_SP1 () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("9773CE47-2EB6-4E14-82A7-6A7E6F43C41B");
    Long proctorKey = 263L;
    UUID browserKey = UUID.fromString ("81114DDF-60DC-43E4-99E5-DAB055CCF5E7");
    UUID requestKey = UUID.fromString ("5FC97B3C-ADC7-4D8A-9A06-C9F859991E2B");
    Boolean markFulfilled = false;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.P_GetTesteeRequestValues_SP (connection, sessionKey, proctorKey, browserKey, requestKey, markFulfilled);
      DbResultRecord record = result.getRecords ().next ();
      assertTrue (record != null);
      String status = record.<String> get ("status");
      String reason = record.<String> get ("Reason");
      String context = record.<String> get ("context");
      String appkey = record.<String> get ("appkey");
      _logger.info (String.format ("Status: %s", status));
      _logger.info (String.format ("Reason: %s", reason));
      _logger.info (String.format ("context: %s", context));
      _logger.info (String.format ("appkey: %s", appkey));
      assertTrue ("failed".equalsIgnoreCase (status));
      assertTrue (("Item stimulus not found [-----]").equalsIgnoreCase (reason));
      assertTrue ("P_GetTesteeRequest".equalsIgnoreCase (context));
      assertTrue ("Item stimulus not found".equalsIgnoreCase (appkey));
    }
  }

  @Test
  // failure case #2
  public void test_P_GetTesteeRequestValues_SP2 () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("3D807FC4-2C46-4789-8F0C-B2D1A42A5573");
    Long proctorKey = 24348L;
    UUID browserKey = UUID.fromString ("69754AE3-2C32-454B-94A8-6A51C1277673");
    UUID requestKey = UUID.fromString ("5972AE8F-F0C4-4FF8-A663-06CD6816B2FD");
    Boolean markFulfilled = false;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      Date start = new Date ();
      SingleDataResultSet result = _dll.P_GetTesteeRequestValues_SP (connection, sessionKey, proctorKey, browserKey, requestKey, markFulfilled);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_GetTesteeRequestValues latency: %d millisec", diff));
      DbResultRecord record = result.getRecords ().next ();
      assertTrue (record != null);
      String status = record.<String> get ("status");
      String reason = record.<String> get ("Reason");
      String context = record.<String> get ("context");
      String appkey = record.<String> get ("appkey");
      _logger.info (String.format ("Status: %s", status));
      _logger.info (String.format ("Reason: %s", reason));
      _logger.info (String.format ("context: %s", context));
      _logger.info (String.format ("appkey: %s", appkey));
      assertTrue ("failed".equalsIgnoreCase (status));
      assertTrue (("The session is closed. [11601]").equalsIgnoreCase (reason));
      assertTrue ("ValidateProctorSession".equalsIgnoreCase (context));
      assertTrue ("The session is closed.".equalsIgnoreCase (appkey));
    }
  }

  // This Sp doesn't return anything. If this is successful, 'DateFulFilled' &
  // 'DateDenied' columns in the table 'TestOppRequest' will be updated to
  // current Date. We can check this by running the below query
  // select * from TestOppRequest where _Key =
  // '41D943CF-CAB3-4B73-8FCB-00F938CA1F1C'
  @Test
  public void test_P_DenyTesteeRequest_SP () throws ReturnStatusException, SQLException {
    UUID sessionKey = UUID.fromString ("45F18366-CB66-469D-89E9-B4F6AEDFEA02");
    Long proctorKey = 32L;
    UUID browserKey = UUID.fromString ("3537FA32-8FFA-4D08-9430-C87F2BB8F512");
    UUID requestKey = UUID.fromString ("41D943CF-CAB3-4B73-8FCB-00F938CA1F1C");
    String reason = "Testing";
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      Date start = new Date ();
      _dll.P_DenyTesteeRequest_SP (connection, sessionKey, proctorKey, browserKey, requestKey, reason);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_DenyTesteeRequest latency: %d millisec", diff));
      _logger.info ("Successful");
    }
  }

  @Test
  // success case
  public void test_P_GetConfigs_SP () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.P_GetConfigs_SP (connection, clientName);
      assertTrue (result.getCount () == 1);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("===================P_GetConfigs Result=======================");
        _logger.info (String.format ("AnonymousLogin: %s ", record.<String> get ("AnonymousLogin")));
        _logger.info (String.format ("ClientName: %s ", record.<String> get ("ClientName")));
        _logger.info (String.format ("Environment: %s", record.<String> get ("Environment")));
        _logger.info (String.format ("ClientStylePath: %s ", record.<String> get ("ClientStylePath")));
        _logger.info (String.format ("TimeZoneOffset: %d ", record.<Integer> get ("TimeZoneOffset")));
        _logger.info (String.format ("refreshValue: %d ", record.<Integer> get ("refreshValue")));
        _logger.info (String.format ("TAInterfaceTimeout: %d ", record.<Integer> get ("TAInterfaceTimeout")));
        _logger.info (String.format ("ProctorTraining: %s ", record.<Integer> get ("ProctorTraining")));
        _logger.info (String.format ("MatchTesteeProctorSchool: %s ", record.<String> get ("MatchTesteeProctorSchool")));
        _logger.info (String.format ("refreshValueMultiplier: %d ", record.<Integer> get ("refreshValueMultiplier")));
        _logger.info (String.format ("checkinURL: %s ", record.<String> get ("checkinURL")));

        assertTrue (record.<String> get ("AnonymousLogin") == null);
        assertTrue ("Oregon".equalsIgnoreCase (record.<String> get ("ClientName")));
        assertTrue ("Development".equalsIgnoreCase (record.<String> get ("Environment")));
        assertTrue ("Oregon".equalsIgnoreCase (record.<String> get ("ClientStylePath")));
        assertTrue (record.<Integer> get ("TimeZoneOffset") == 3);
        assertTrue (record.<Integer> get ("refreshValue") == 30);
        assertTrue (record.<Integer> get ("TAInterfaceTimeout") == 20);
        assertTrue (record.<Integer> get ("ProctorTraining") == 0);
        assertTrue (record.<String> get ("MatchTesteeProctorSchool") == null);
        assertTrue (record.<Integer> get ("refreshValueMultiplier") == 2);
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  @Test
  // success case #1
  public void test_TDS_GetMessages_SP () throws SQLException, ReturnStatusException {
    String systemID = "Student";
    String client = "SBAC";
    String language = "ENU";
    String contextList = "testshell.aspx,Default.aspx,Opportunity.aspx";
    Character delimiter = ',';
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      Date start = new Date ();
      SingleDataResultSet result = _dll.TDSCONFIGS_TDS_GetMessages_SP (connection, systemID, client, language, contextList, delimiter);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("TDS_GetMessages latency: %d millisec", diff));

      assertTrue (result.getCount () == 191);
      Iterator<DbResultRecord> records = result.getRecords ();
      _logger.info ("===================TDS_GetMessages Result=======================");
      _logger.info (String.format ("msgkeys  msgSource  messageID  contextType Context  Appkey  Lng  Grade  Subject msg"));

      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info (String.format ("%d %s %d %s %s %s %s %s %s %s",
            record.<Long> get ("msgkey"), record.<String> get ("msgSource"), record.<Integer> get ("MessageID"),
            record.<String> get ("ContextType"), record.<String> get ("Context"), record.<String> get ("AppKey"),
            record.<String> get ("Language"), record.<String> get ("Grade"), record.<String> get ("Subject"),
            record.<String> get ("Message")));
        // _logger.info
        // ("===================TDS_GetMessages Result=======================");
        // _logger.info (String.format ("msgkey: %d ", record.<Long> get
        // ("msgkey")));
        // _logger.info (String.format ("msgSource: %s ", record.<String> get
        // ("msgSource")));
        // _logger.info (String.format ("MessageID: %d", record.<Integer> get
        // ("MessageID")));
        // _logger.info (String.format ("ContextType: %s ", record.<String> get
        // ("ContextType")));
        // _logger.info (String.format ("Context: %s ", record.<String> get
        // ("Context")));
        // _logger.info (String.format ("AppKey: %s ", record.<String> get
        // ("AppKey")));
        // _logger.info (String.format ("Language: %s ", record.<String> get
        // ("Language")));
        // _logger.info (String.format ("Grade: %s ", record.<String> get
        // ("Grade")));
        // _logger.info (String.format ("Subject: %s ", record.<String> get
        // ("Subject")));
        // _logger.info (String.format ("ParaLabels: %s ", record.<String> get
        // ("ParaLabels")));
        // _logger.info (String.format ("Message: %s ", record.<String> get
        // ("Message")));

        // assertTrue("--ANY--".equalsIgnoreCase (record.<String> get
        // ("Grade")));
        // assertTrue("--ANY--".equalsIgnoreCase (record.<String> get
        // ("Subject")));
        // assertTrue("ENU".equalsIgnoreCase (record.<String> get
        // ("Language")));
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  @Test
  // success case #2
  public void test_TDS_GetMessages_SP1 () throws SQLException, ReturnStatusException {
    String systemID = "ResponseEntry";
    String client = "Minnesota_PT";
    String language = "ESN";
    String contextList = "Alerts.aspx;Approval.aspx;ApprovedRequests.aspx";
    Character delimiter = ';';
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      Date start = new Date ();
      SingleDataResultSet result = _dll.TDSCONFIGS_TDS_GetMessages_SP (connection, systemID, client, language, contextList, delimiter);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("TDS_GetMessages latency: %d millisec", diff));
      assertTrue (result.getCount () == 16);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("===================TDS_GetMessages Result=======================");
        _logger.info (String.format ("msgkey: %d ", record.<Long> get ("msgkey")));
        _logger.info (String.format ("msgSource: %s ", record.<String> get ("msgSource")));
        _logger.info (String.format ("MessageID: %d", record.<Integer> get ("MessageID")));
        _logger.info (String.format ("ContextType: %s ", record.<String> get ("ContextType")));
        _logger.info (String.format ("Context: %s ", record.<String> get ("Context")));
        _logger.info (String.format ("AppKey: %s ", record.<String> get ("AppKey")));
        _logger.info (String.format ("Language: %s ", record.<String> get ("Language")));
        _logger.info (String.format ("Grade: %s ", record.<String> get ("Grade")));
        _logger.info (String.format ("Subject: %s ", record.<String> get ("Subject")));
        _logger.info (String.format ("ParaLabels: %s ", record.<String> get ("ParaLabels")));
        _logger.info (String.format ("Message: %s ", record.<String> get ("Message")));

        assertTrue ("--ANY--".equalsIgnoreCase (record.<String> get ("Grade")));
        assertTrue ("--ANY--".equalsIgnoreCase (record.<String> get ("Subject")));
        assertTrue (record.<String> get ("ParaLabels") == null);
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  @Test
  // success case #3
  public void test_TDS_GetMessages_SP2 () throws SQLException, ReturnStatusException {
    String systemID = "Student";
    String client = "Minnesota_PT";
    String language = "ENU";
    String contextList = "testshell.aspx,Default.aspx,Opportunity.aspx";
    Character delimiter = ',';
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.TDSCONFIGS_TDS_GetMessages_SP (connection, systemID, client, language, contextList, delimiter);
      assertTrue (result.getCount () == 191);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("===================TDS_GetMessages Result=======================");
        _logger.info (String.format ("msgkey: %d ", record.<Long> get ("msgkey")));
        _logger.info (String.format ("msgSource: %s ", record.<String> get ("msgSource")));
        _logger.info (String.format ("MessageID: %d", record.<Integer> get ("MessageID")));
        _logger.info (String.format ("ContextType: %s ", record.<String> get ("ContextType")));
        _logger.info (String.format ("Context: %s ", record.<String> get ("Context")));
        _logger.info (String.format ("AppKey: %s ", record.<String> get ("AppKey")));
        _logger.info (String.format ("Language: %s ", record.<String> get ("Language")));
        _logger.info (String.format ("Grade: %s ", record.<String> get ("Grade")));
        _logger.info (String.format ("Subject: %s ", record.<String> get ("Subject")));
        _logger.info (String.format ("ParaLabels: %s ", record.<String> get ("ParaLabels")));
        _logger.info (String.format ("Message: %s ", record.<String> get ("Message")));

        assertTrue ("--ANY--".equalsIgnoreCase (record.<String> get ("Grade")));
        assertTrue ("--ANY--".equalsIgnoreCase (record.<String> get ("Subject")));
        assertTrue ("ENU".equalsIgnoreCase (record.<String> get ("Language")));
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  @Test
  // success case
  public void test_AppMessagesByContext_SP () throws SQLException, ReturnStatusException {
    String systemID = "Student";
    String client = "SBAC";
    String language = "ENU";
    String contextList = "testshell.aspx,Default.aspx,Opportunity.aspx";
    Character delimiter = ',';
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      Date start = new Date ();
      SingleDataResultSet result = _dll.AppMessagesByContext_SP (connection, systemID, client, language, contextList, delimiter);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("AppMessagesByContext latency: %d millisec", diff));
      assertTrue (result.getCount () == 191);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("===================AppMessagesByContext Result=======================");
        _logger.info (String.format ("msgkey: %d ", record.<Long> get ("msgkey")));
        _logger.info (String.format ("msgSource: %s ", record.<String> get ("msgSource")));
        _logger.info (String.format ("MessageID: %d", record.<Integer> get ("MessageID")));
        _logger.info (String.format ("ContextType: %s ", record.<String> get ("ContextType")));
        _logger.info (String.format ("Context: %s ", record.<String> get ("Context")));
        _logger.info (String.format ("AppKey: %s ", record.<String> get ("AppKey")));
        _logger.info (String.format ("Language: %s ", record.<String> get ("Language")));
        _logger.info (String.format ("Grade: %s ", record.<String> get ("Grade")));
        _logger.info (String.format ("Subject: %s ", record.<String> get ("Subject")));
        _logger.info (String.format ("ParaLabels: %s ", record.<String> get ("ParaLabels")));
        _logger.info (String.format ("Message: %s ", record.<String> get ("Message")));

        assertTrue ("--ANY--".equalsIgnoreCase (record.<String> get ("Grade")));
        assertTrue ("--ANY--".equalsIgnoreCase (record.<String> get ("Subject")));
        assertTrue ("ENU".equalsIgnoreCase (record.<String> get ("Language")));
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  @Test
  public void test_AllFormatMessage_SP () throws SQLException, ReturnStatusException {
    String client = "Hawaii";
    String language = "HAW";
    String application = "ScoreEntry";
    String contextType = "ClientSide";
    String context = "testshell.aspx";
    String appkey = "TDS_F_HI_11Sci";
    _Ref<String> msg = new _Ref<> ();
    String argstring = null;
    Character delim = ',';
    String subject = null;
    String grade = null;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      _dll.All_FormatMessage_SP (connection, client, language, application, contextType, context, appkey, msg, argstring, delim, subject, grade);
      _logger.info (String.format ("msg: %s", msg.get ()));
      assertTrue ("../tools/formulas/2010/hi_11_science.html [10878]".equalsIgnoreCase (msg.get ()));
    }
  }

  // This SP will not return anything. But, we can check the 'DateVisited'
  // column in the session Table by executing the below query. Successful
  // execution updates 'DateVisited' column to current date
  // select * from session where _key = 'E483B532-264C-4F27-A16E-005FF85CA7C7'

  @Test
  // success case
  public void test_P_SetSessionDateVisited_SP () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("E483B532-264C-4F27-A16E-005FF85CA7C7");
    Long proctorKey = 1326L;
    UUID browserKey = UUID.fromString ("32CACB7B-03C9-40C4-A6B6-F719DD2E9A34");
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      Date start = new Date ();
      SingleDataResultSet result = _dll.P_SetSessionDateVisited_SP (connection, sessionKey, proctorKey, browserKey);
      Date end = new Date ();
      long diff = end.getTime () - start.getTime ();
      System.out.println (String.format ("P_SetSessionDateVisited latency: %d millisec", diff));
      assertTrue (result == null);
    }
  }

  @Test
  // failure case #1
  public void test_P_SetSessionDateVisited_SP1 () throws SQLException, ReturnStatusException {
    UUID sessionKey = UUID.fromString ("E4835532-264C-4F27-A16E-005FF85CA7C7");
    Long proctorKey = 1326L;
    UUID browserKey = UUID.fromString ("32CACB7B-03C9-40C4-A6B6-F719DD2E9A34");
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.P_SetSessionDateVisited_SP (connection, sessionKey, proctorKey, browserKey);
      DbResultRecord record = result.getRecords ().next ();
      assertTrue (record != null);
      String status = record.<String> get ("status");
      String reason = record.<String> get ("Reason");
      String context = record.<String> get ("context");
      String appkey = record.<String> get ("appkey");
      _logger.info (String.format ("Status: %s", status));
      _logger.info (String.format ("Reason: %s", reason));
      _logger.info (String.format ("context: %s", context));
      _logger.info (String.format ("appkey: %s", appkey));
      assertTrue ("failed".equalsIgnoreCase (status));
      assertTrue (("The session is closed. [11601]").equalsIgnoreCase (reason));
      assertTrue ("ValidateProctorSession".equalsIgnoreCase (context));
      assertTrue ("The session is closed.".equalsIgnoreCase (appkey));
    }
  }

}

@Component
class MyProctorDLLHelper extends AbstractDLL
{

}
