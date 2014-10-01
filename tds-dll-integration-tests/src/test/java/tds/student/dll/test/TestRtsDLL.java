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
import java.util.Iterator;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opentestsystem.shared.test.LifecycleManagingTestRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ContextConfiguration;

import tds.dll.api.IRtsDLL;
import AIR.Common.DB.AbstractConnectionManager;
import AIR.Common.DB.SQLConnection;
import AIR.Common.DB.results.DbResultRecord;
import AIR.Common.DB.results.SingleDataResultSet;
import AIR.Common.Helpers._Ref;
import TDS.Shared.Exceptions.ReturnStatusException;

@RunWith (LifecycleManagingTestRunner.class)
@ContextConfiguration (locations = "/test-context.xml")
public class TestRtsDLL // extends AbstractTest
{
  private static Logger             _logger            = LoggerFactory.getLogger (TestCommonDLL.class);
  @Autowired
  // @Resource(name="rtsPackageDLL")
  private IRtsDLL                   _dll               = null;

  @Autowired
  private AbstractConnectionManager _connectionManager = null;

  @Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  // success case #1
  public void test_P_GetAllTests_SP () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    int sessionType = 0;
    Long proctorKey = 0L;// not used yet
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.P_GetAllTests_SP (connection, clientName, sessionType, proctorKey);
      assertTrue (result.getCount () == 37);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("===================P_GetAllTests Records=======================");
        _logger.info (String.format ("TestID: %s", record.<String> get ("TestID")));
        _logger.info (String.format ("GradeText: %s ", record.<String> get ("GradeText")));
        _logger.info (String.format ("Subject: %s ", record.<String> get ("Subject")));
        _logger.info (String.format ("DisplayName: %s ", record.<String> get ("DisplayName")));
        _logger.info (String.format ("SortOrder: %d ", record.<Integer> get ("SortOrder")));
        _logger.info (String.format ("AccommodationFamily: %s ", record.<String> get ("AccommodationFamily")));
        _logger.info (String.format ("IsSelectable: %s ", record.<Boolean> get ("IsSelectable")));
        _logger.info (String.format ("IsSegmented: %s ", record.<Boolean> get ("IsSegmented")));
        _logger.info (String.format ("TestKey: %s ", record.<String> get ("TestKey")));
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  // @Test
  // success case #2
  public void test_P_GetAllTests_SP1 () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    int sessionType = 1;
    Long proctorKey = 0L;// not used yet
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.P_GetAllTests_SP (connection, clientName, sessionType, proctorKey);
      assertTrue (result.getCount () == 1);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("===================P_GetAllTests Record=======================");
        _logger.info (String.format ("TestID: %s", record.<String> get ("TestID")));
        _logger.info (String.format ("GradeText: %s ", record.<String> get ("GradeText")));
        _logger.info (String.format ("Subject: %s ", record.<String> get ("Subject")));
        _logger.info (String.format ("DisplayName: %s ", record.<String> get ("DisplayName")));
        _logger.info (String.format ("SortOrder: %d ", record.<Integer> get ("SortOrder")));
        _logger.info (String.format ("AccommodationFamily: %s ", record.<String> get ("AccommodationFamily")));
        _logger.info (String.format ("IsSelectable: %s ", record.<Boolean> get ("IsSelectable")));
        _logger.info (String.format ("IsSegmented: %s ", record.<Boolean> get ("IsSegmented")));
        _logger.info (String.format ("TestKey: %s ", record.<String> get ("TestKey")));
        assertTrue ("OAKS-Writing-HS".equalsIgnoreCase (record.<String> get ("TestID")));
        assertTrue ("High School".equalsIgnoreCase (record.<String> get ("GradeText")));
        assertTrue ("Writing".equalsIgnoreCase (record.<String> get ("Subject")));
        assertTrue ("High School Writing".equalsIgnoreCase (record.<String> get ("DisplayName")));
        assertTrue (record.<Integer> get ("SortOrder") == 41);
        assertTrue ("WR".equalsIgnoreCase (record.<String> get ("AccommodationFamily")));
        assertTrue (record.<Boolean> get ("IsSelectable") == true);
        assertTrue (record.<Boolean> get ("IsSegmented") == false);
        assertTrue ("(Oregon)OAKS-Writing-HS-Fall-2012-2013".equalsIgnoreCase (record.<String> get ("TestKey")));
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  // @Test
  // sucess case
  public void test_R_GetSchoolGrades_SP () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    String schoolRTSKey = "4";
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.R_GetSchoolGrades_SP (connection, clientName, schoolRTSKey);
      assertTrue (result.getCount () == 7);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info (String.format ("Grade: %s", record.<String> get ("grade")));
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  // @Test
  // failure case
  public void test_R_GetSchoolGrades_SP1 () throws SQLException, ReturnStatusException {
    String clientName = "AIR";
    String schoolRTSKey = "4";
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.R_GetSchoolGrades_SP (connection, clientName, schoolRTSKey);
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
      assertTrue (("Unable to locate RTS database[10123]").equalsIgnoreCase (reason));
      assertTrue ("R_GetSchoolGrades".equalsIgnoreCase (context));
      assertTrue ("Unable to locate RTS database".equalsIgnoreCase (appkey));
    }
  }

  // @Test
  // success case #1
  public void test_GetSchoolStudents_SP () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    String school = "22654";
    String grade = null;
    String firstName = "Michael";
    String lastName = "Nelson";
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.getSchoolStudents (connection, clientName, school, grade, firstName, lastName);
      assertTrue (result.getCount () == 1);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("===================GetSchoolStudents Result=======================");
        _logger.info (String.format ("rtskey: %d", record.<Long> get ("rtskey")));
        _logger.info (String.format ("SSID: %s ", record.<String> get ("SSID")));
        _logger.info (String.format ("LastName: %s ", record.<String> get ("LastName")));
        _logger.info (String.format ("FirstName: %s", record.<String> get ("FirstName")));
        _logger.info (String.format ("Grade: %s ", record.<String> get ("Grade")));

        assertTrue (record.<Long> get ("rtskey") == 116880);
        assertTrue ("10284583".equalsIgnoreCase (record.<String> get ("SSID")));
        assertTrue ("Nelson".equalsIgnoreCase (record.<String> get ("LastName")));
        assertTrue ("Michael".equalsIgnoreCase (record.<String> get ("FirstName")));
        assertTrue ("11".equalsIgnoreCase (record.<String> get ("Grade")));
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  // @Test
  // success case #2
  public void test_GetSchoolStudents_SP1 () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    String school = "4736";
    String grade = "8";
    String firstName = "Kyle";
    String lastName = "Tardie";
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.getSchoolStudents (connection, clientName, school, grade, firstName, lastName);
      assertTrue (result.getCount () == 1);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("===================GetSchoolStudents Result=======================");
        _logger.info (String.format ("rtskey: %d", record.<Long> get ("rtskey")));
        _logger.info (String.format ("SSID: %s ", record.<String> get ("SSID")));
        _logger.info (String.format ("LastName: %s ", record.<String> get ("LastName")));
        _logger.info (String.format ("FirstName: %s", record.<String> get ("FirstName")));
        _logger.info (String.format ("Grade: %s ", record.<String> get ("Grade")));

        assertTrue (record.<Long> get ("rtskey") == 219090);
        assertTrue ("11791276".equalsIgnoreCase (record.<String> get ("SSID")));
        assertTrue ("Tardie".equalsIgnoreCase (record.<String> get ("LastName")));
        assertTrue ("Kyle".equalsIgnoreCase (record.<String> get ("FirstName")));
        assertTrue ("08".equalsIgnoreCase (record.<String> get ("Grade")));
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  // @Test
  // failure case
  public void test_GetSchoolStudents_SP2 () throws SQLException, ReturnStatusException {
    String clientName = null;
    String school = "22654";
    String grade = null;
    String firstName = "Michael";
    String lastName = "Nelson";
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.getSchoolStudents (connection, clientName, school, grade, firstName, lastName);
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
      assertTrue (("Unable to locate RTS database[10114]").equalsIgnoreCase (reason));
      assertTrue ("R_SchoolParticipation".equalsIgnoreCase (context));
      assertTrue ("Unable to locate RTS database".equalsIgnoreCase (appkey));
    }
  }

  // @Test
  // success case
  public void test_R_GetDistricts_SP () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.R_GetDistricts_SP (connection, clientName);
      assertTrue (result.getCount () == 291);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("===================R_GetDistricts Result=======================");
        _logger.info (String.format ("DistrictName: %s ", record.<String> get ("DistrictName")));
        _logger.info (String.format ("DistrictID: %s ", record.<String> get ("DistrictID")));
        _logger.info (String.format ("RTSKey: %d", record.<Long> get ("RTSKey")));
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  // @Test
  // failure case
  public void test_R_GetDistricts_SP1 () throws SQLException, ReturnStatusException {
    String clientName = null;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.R_GetDistricts_SP (connection, clientName);
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
      assertTrue (("Unable to locate RTS database[10122]").equalsIgnoreCase (reason));
      assertTrue ("R_GetDistricts".equalsIgnoreCase (context));
      assertTrue ("Unable to locate RTS database".equalsIgnoreCase (appkey));
    }
  }

  // @Test
  public void test_R_GetDistrictSchools_SP () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    String distRTSKey = "370330";
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.R_GetDistrictSchools_SP (connection, clientName, distRTSKey);
      assertTrue (result.getCount () == 3);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("===================R_GetDistrictSchools Result=======================");
        _logger.info (String.format ("SchoolName: %s ", record.<String> get ("SchoolName")));
        _logger.info (String.format ("SchoolID: %s ", record.<String> get ("SchoolID")));
        _logger.info (String.format ("RTSKey: %d", record.<Long> get ("RTSKey")));
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  // @Test
  // failure case
  public void test_R_GetDistrictSchools_SP1 () throws SQLException, ReturnStatusException {
    String clientName = null;
    String distRTSKey = "370330";
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.R_GetDistrictSchools_SP (connection, clientName, distRTSKey);
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
      assertTrue (("Unable to locate RTS database[10123]").equalsIgnoreCase (reason));
      assertTrue ("R_GetSchoolGrades".equalsIgnoreCase (context));
      assertTrue ("Unable to locate RTS database".equalsIgnoreCase (appkey));
    }
  }

  @Test
  // success case
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void test_GetRTSUser_SP () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    String userId = "brian.wolf@malesd.k12.or.us";
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.GetRTSUser_SP (connection, clientName, userId);
      assertTrue (result.getCount () == 1);
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertTrue (record != null);
        _logger.info ("===================GetRTSUser Result=======================");
        _logger.info (String.format ("userid: %s ", record.<String> get ("userid")));
        _logger.info (String.format ("UserKey: %d ", record.<Long> get ("UserKey")));
        _logger.info (String.format ("fullname: %s ", record.<String> get ("fullname")));
        _logger.info (String.format ("password: %s", record.<String> get ("password")));
        _logger.info (String.format ("isActive: %s ", record.<Boolean> get ("isActive")));
        _logger.info (String.format ("hasAcknowledged: %s ", record.<Boolean> get ("hasAcknowledged")));

        assertTrue ("brian.wolf@malesd.k12.or.us".equalsIgnoreCase (record.<String> get ("userid")));
        assertTrue (record.<Long> get ("UserKey") == 4);
        assertTrue ("Brian Wolf".equalsIgnoreCase (record.<String> get ("fullname")));
        assertTrue ("IKHsT5UI017Yfafg7Tm2ApuOZMPxGrZbA7CC4em5SSAwg566a2mi".equalsIgnoreCase (record.<String> get ("password")));
        assertTrue (record.<Boolean> get ("isActive") == false);
        assertTrue (record.<Boolean> get ("hasAcknowledged") == true);
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }

  // @Test
  // failure case
  public void test_GetRTSUser_SP1 () throws SQLException, ReturnStatusException {
    String clientName = "XYZ";
    String userId = "brian.wolf@malesd.k12.or.us";
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.GetRTSUser_SP (connection, clientName, userId);
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
      assertTrue (("Unable to locate RTS database[-----]").equalsIgnoreCase (reason));
      assertTrue ("GetRTSUser".equalsIgnoreCase (context));
      assertTrue ("Unable to locate RTS database".equalsIgnoreCase (appkey));
    }
  }

  // declare @instkey bigint;
  // exec _ValidateInstitutionMatch 'Oregon',553163, 20424, @instkey output;
  // select @instkey
  // @Test
  public void test_ValidateInstitutionMatch_SP () throws SQLException, ReturnStatusException {
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      String clientname = "Oregon";
      Long testeeKey = 553163L;
      Long proctorKey = 20424L;
      _Ref<String> instKey = new _Ref<String> ();

      _dll._ValidateInstitutionMatch_SP (connection, clientname, testeeKey, proctorKey, instKey);
      assertTrue (instKey.get () != null);
      assertTrue (instKey.get () == "370331");
      _logger.info ("InstitutionKey: " + instKey.get ());

    }
  }

  // declare @attValue varchar(200);
  // exec _GetRTSAttribute 'Oregon', 421021, '--ETHNICITY--', @attvalue output;
  // select @attvalue;
  // @Test
  // TODO if using RtsPackageDLL rather than RtsDLL expect null value, it is not
  // supported yet
  public void test_GetRTSAttribute_SP () throws SQLException, ReturnStatusException {
    String clientname = "Oregon";
    Long testee = 421021L;
    String attname = "--ETHNICITY--";
    _Ref<String> attValue = new _Ref<String> ();

    try (SQLConnection connection = _connectionManager.getConnection ()) {
      _dll._GetRTSAttribute_SP (connection, clientname, testee, attname, attValue);
      assertTrue (attValue.get () != null);
      assertTrue (attValue.get ().equalsIgnoreCase ("6"));
      _logger.info (String.format ("Attvalue: %s", attValue.get ()));

    }
  }

  // declare @attValue varchar(200);
  // exec _GetRTSAttribute 'Oregon', 421021, 'LglFNm', @attvalue output;
  // select @attvalue;
  // @Test
  public void test_GetRTSAttribute_SP2 () throws SQLException, ReturnStatusException {
    String clientname = "Oregon";
    Long testee = 421021L;
    String attname = "LglFNm";
    _Ref<String> attValue = new _Ref<String> ();

    try (SQLConnection connection = _connectionManager.getConnection ()) {
      _dll._GetRTSAttribute_SP (connection, clientname, testee, attname, attValue);
      assertTrue (attValue.get () != null);
      assertTrue (attValue.get ().equalsIgnoreCase ("Terrance"));
      _logger.info (String.format ("Attvalue: %s", attValue.get ()));

    }
  }

  @Test
  public void test_GetTesteeRelationships_SP () throws SQLException, ReturnStatusException {
    String clientname = "Oregon";
    Long testee = 421021L; // 552981L;
    String expectedOutput[][] =
    { { "District", "0", "DistrictID", "2063" },
        { "District", "0", "DistrictName", "Adel SD 21" },
        { "School", "0", "SchoolID", "20632063" },
        { "School", "0", "SchoolName", "Adel SD 21" } };

    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet rs = _dll._GetTesteeRelationships_SP (connection, clientname, testee);
      Iterator<DbResultRecord> records = rs.getRecords ();
      int i = 0;
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        String relationtype = record.<String> get ("relationtype");
        Long entityKey = record.<Long> get ("entitykey");
        String tdsId = record.<String> get ("tds_id");
        String attval = record.<String> get ("attval");
        _logger.info (String.format ("RelationType: %s, EntityKey: %d, tds_id:%s, attval: %s", relationtype, entityKey, tdsId, attval));
        // System.out.println (String.format
        // ("RelationType: %s, EntityKey: %d, tds_id:%s, attval: %s",
        // relationtype, entityKey,tdsId, attval) );
        assertTrue (relationtype != null);
        assertTrue (relationtype.equals (expectedOutput[i][0]));
        // TODO: RtsPackage does not support entityKey
        // assert (entityKey != null);
        assertTrue (tdsId != null);
        assertTrue (tdsId.equals (expectedOutput[i][2]));
        assertTrue (attval != null);
        assertTrue (attval.equals (expectedOutput[i][3]));

        i++;
      }
    }
  }

  // declare @entityKey bigint;
  // declare @entityId varchar(300);
  // declare @entityName varchar(300);
  // exec _GetRTSRelationship 'Oregon', 421021, 'ENRLINST-STUDENT',
  // @entityKey output, @entityId output, @entityName output;
  // select @entityKey, @entityId, @entityName;
  // @Test
  public void test_GetRTSRelationship_SP () throws SQLException, ReturnStatusException {

    String clientname = "Oregon";
    Long testee = 421021L;
    String relationship = "ENRLINST-STUDENT";
    _Ref<Long> entityKey = new _Ref<Long> ();
    _Ref<String> entityId = new _Ref<String> ();
    _Ref<String> entityName = new _Ref<String> ();

    try (SQLConnection connection = _connectionManager.getConnection ()) {
      _dll._GetRTSRelationship_SP (connection, clientname, testee, relationship, entityKey, entityId, entityName);
      // TODO: RtsPackage does not supportentityKey
      // assert (entityKey.get () != null);
      assertTrue (entityId.get () != null);
      assertTrue (entityName.get () != null);
      // assert (entityKey.get () == 4);
      assertTrue (entityId.get ().equalsIgnoreCase ("20632063"));
      assertTrue (entityName.get ().equalsIgnoreCase ("Adel SD 21"));
      _logger.info (String.format ("entityKey: %d", entityKey.get ()));
      _logger.info (String.format ("entityId: %s", entityId.get ()));
      _logger.info (String.format ("entityName: %s", entityName.get ()));
    }
  }

  // @Test
  public void test_GetRTSEntity_SP () throws SQLException, ReturnStatusException {
    String externalId = "999999932";
    String entityType = "STUDENT";
    String clientname = "Oregon";
    _Ref<Long> entityKeyRef = new _Ref<> ();
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      _dll._GetRTSEntity_SP (connection, clientname, externalId, entityType, entityKeyRef);
      _logger.info (String.format ("EntityKey: %s", entityKeyRef.get ()));
      assertTrue (entityKeyRef.get () == 538874);
    }
  }

  // @Test
  // success case #1
  public void test_ValidateAsProctor_SP () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    Long userKey = 35840L;
    _Ref<Long> userEntityRef = new _Ref<> ();
    _Ref<Long> roleEntityRef = new _Ref<> ();
    _Ref<String> roleRef = new _Ref<> ();
    int sessionType = 0;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      _dll._ValidateAsProctor_SP (connection, clientName, userKey, userEntityRef, roleEntityRef, roleRef, sessionType);
      assertTrue (userEntityRef.get () == 957492);
      assertTrue (roleEntityRef.get () == 370331);
      assertTrue ("TA".equalsIgnoreCase (roleRef.get ()));
      _logger.info ("userEntityRef: " + userEntityRef.get ());
      _logger.info ("roleEntityRef: " + roleEntityRef.get ());
      _logger.info ("roleRef: " + roleRef.get ());
    }
  }

  // @Test
  // success case #2 (if certify == 1)
  public void test_ValidateAsProctor_SP1 () throws SQLException, ReturnStatusException {
    String clientName = "Minnesota";
    Long userKey = 20628L;
    _Ref<Long> userEntityRef = new _Ref<> ();
    _Ref<Long> roleEntityRef = new _Ref<> ();
    _Ref<String> roleRef = new _Ref<> ();
    int sessionType = 0;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      _dll._ValidateAsProctor_SP (connection, clientName, userKey, userEntityRef, roleEntityRef, roleRef, sessionType);
      _logger.info ("userEntityRef: " + userEntityRef.get ());
      _logger.info ("roleEntityRef: " + roleEntityRef.get ());
      _logger.info ("roleRef: " + roleRef.get ());

      assertTrue (userEntityRef.get () == 677817);
      assertTrue (roleEntityRef.get () == 4250);
      assertTrue ("TM".equalsIgnoreCase (roleRef.get ()));
    }
  }

  // @Test
  // success case #3
  public void test_ValidateAsProctor_SP3 () throws SQLException, ReturnStatusException {
    String clientName = "Hawaii";
    Long userKey = 20628L;
    _Ref<Long> userEntityRef = new _Ref<> ();
    _Ref<Long> roleEntityRef = new _Ref<> ();
    _Ref<String> roleRef = new _Ref<> ();
    int sessionType = 0;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      _dll._ValidateAsProctor_SP (connection, clientName, userKey, userEntityRef, roleEntityRef, roleRef, sessionType);
      _logger.info ("userEntityRef: " + userEntityRef.get ());
      _logger.info ("roleEntityRef: " + roleEntityRef.get ());
      _logger.info ("roleRef: " + roleRef.get ());

      assertTrue (userEntityRef.get () == null);
      assertTrue (roleEntityRef.get () == null);
      assertTrue (roleRef.get () == null);
    }
  }

  @Test
  // success case #1
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void test_P_ValidateProctor_SP () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    String proctorId = "test1@air.org";// "TA1@air.org";
    UUID browserKey = UUID.fromString ("28EE3122-3C0C-408E-814E-7D5DF63FFBD7");
    String password = null;
    int oppSessions = 0;
    Boolean ignorepw = true;
    int sessionType = 0;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.P_ValidateProctor_SP (connection, clientName, browserKey, proctorId, password, oppSessions, ignorepw, sessionType);
      DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
      assertTrue (record != null);
      if (record != null) {
        _logger.info (String.format ("Status: %s", record.<String> get ("status")));
        _logger.info (String.format ("userKey: %d", record.<Long> get ("userKey")));
        _logger.info (String.format ("entityKey: %s", record.<Long> get ("entityKey")));
        _logger.info (String.format ("fullname: %s", record.<String> get ("fullname")));
        _logger.info (String.format ("rtspassword: %s", record.<String> get ("rtspassword")));
        _logger.info (String.format ("role: %s", record.<String> get ("role")));
        _logger.info (String.format ("Reason: %s", record.<String> get ("reason")));

        assertTrue ("success".equalsIgnoreCase (record.<String> get ("status")));
        assertTrue (record.<Long> get ("userKey") == 35756);
        assertTrue (record.<Long> get ("entityKey") == 942981);
        assertTrue ("dfd dfdf".equalsIgnoreCase (record.<String> get ("fullname")));
        assertTrue ("IKHsT5UI017Yfafg7Tm2ApuOZMPxGrZbA7CC4em5SSAwg566a2mi".equalsIgnoreCase (record.<String> get ("rtspassword")));
        // assert ("TA".equalsIgnoreCase (record.<String> get ("role")));
        assertTrue (record.<String> get ("role") == null);
        assertTrue (record.<String> get ("reason") == null);
      }
    }
  }

  @Test
  // success case #2 for the oppsessions = 1
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void test_P_ValidateProctor_SP1 () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    String proctorId = "TA1@air.org";
    UUID browserKey = UUID.fromString ("28EE3122-3C0C-408E-814E-7D5DF63FFBD7");
    String password = null;
    int oppSessions = 1;
    Boolean ignorepw = true;
    int sessionType = 0;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.P_ValidateProctor_SP (connection, clientName, browserKey, proctorId, password, oppSessions, ignorepw, sessionType);
      DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
      assertTrue (record != null);
      if (record != null) {
        _logger.info (String.format ("Status: %s", record.<String> get ("status")));
        _logger.info (String.format ("userKey: %d", record.<Long> get ("userKey")));
        _logger.info (String.format ("entityKey: %s", record.<Long> get ("entityKey")));
        _logger.info (String.format ("fullname: %s", record.<String> get ("fullname")));
        _logger.info (String.format ("rtspassword: %s", record.<String> get ("rtspassword")));
        _logger.info (String.format ("role: %s", record.<String> get ("role")));
        _logger.info (String.format ("Reason: %s", record.<String> get ("reason")));

        assertTrue ("success".equalsIgnoreCase (record.<String> get ("status")));
        assertTrue (record.<Long> get ("userKey") == 35840);
        assertTrue (record.<Long> get ("entityKey") == 957492);
        assertTrue ("TA Test".equalsIgnoreCase (record.<String> get ("fullname")));
        assertTrue ("IKHsT5UI017Yfafg7Tm2ApuOZMPxGrZbA7CC4em5SSAwg566a2mi".equalsIgnoreCase (record.<String> get ("rtspassword")));
        assertTrue ("TA".equalsIgnoreCase (record.<String> get ("role")));
        assertTrue (record.<String> get ("reason") == null);
      }
    }
  }

  @Test
  // failure case
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void test_P_ValidateProctor_SP2 () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    String proctorId = "TA1@air.org";
    UUID browserKey = UUID.fromString ("28EE3122-3C0C-408E-814E-7D5DF63FFBD7");
    String password = "9cu+IIEpgw1U0UjHhYwcUrK7FJ4BJ6Ef/pDMauaObb8FmcAiCIE=";
    int oppSessions = 0;
    Boolean ignorepw = false;
    int sessionType = 0;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _dll.P_ValidateProctor_SP (connection, clientName, browserKey, proctorId, password, oppSessions, ignorepw, sessionType);
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
      assertTrue (("Unable to log you in. Please check your account in TIDE to be sure it is fully activated. [10161]").equalsIgnoreCase (reason));
      assertTrue ("P_ValidateProctor".equalsIgnoreCase (context));
      assertTrue ("Unable to log you in. Please check your account in UMS to be sure it is fully activated.".equalsIgnoreCase (appkey));
    }
  }

}
