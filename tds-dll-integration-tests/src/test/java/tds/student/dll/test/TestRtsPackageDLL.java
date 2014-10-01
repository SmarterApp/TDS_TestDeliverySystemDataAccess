/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.student.dll.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.UUID;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opentestsystem.shared.test.LifecycleManagingTestRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ContextConfiguration;

import tds.dll.api.IRtsDLL;
import AIR.Common.DB.AbstractConnectionManager;
import AIR.Common.DB.SQLConnection;
import AIR.Common.DB.results.DbResultRecord;
import AIR.Common.DB.results.SingleDataResultSet;
import AIR.Common.Helpers._Ref;
import AIR.test.framework.AbstractTest;
import TDS.Shared.Exceptions.ReturnStatusException;

@RunWith (LifecycleManagingTestRunner.class)
@ContextConfiguration (locations = "/test-context.xml")
@IfProfileValue(name="TestProfile", value="ToBeFixed")
public class TestRtsPackageDLL extends AbstractTest
{

  private static final Logger       _logger = LoggerFactory.getLogger (TestRtsPackageDLL.class);

  @Resource(name="rtsPackageDLL")
  IRtsDLL                           _rtsDll;

  @Autowired
  private AbstractConnectionManager _connectionManager;

  //@Ignore ("institutions for proctor not currently implemented in RtsPackageDLL")
  @Test
  public void test_ValidateInstitutionMatch_SP () throws Exception {
    String clientname = "Oregon";
    Long testeeKey = -1L;
    Long proctorKey = -1L;
    Long expectedInstKey = 8716411L; //-1L;
    
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      _Ref<String> instKey = new _Ref<String> ();
      _rtsDll._ValidateInstitutionMatch_SP (connection, clientname, testeeKey, proctorKey, instKey);
      assertNotNull ("Institution", instKey.get ());
      assertEquals ("Institution", expectedInstKey, instKey.get ());
      _logger.info ("InstitutionKey: " + instKey.get ());
    }
  }
  
  @Test
  public void testT_GetRTSAccommodations_SP () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    Long testeeKey = -1L;
    int expectedNumberOfAccommodations = 108;

    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _rtsDll.T_GetRTSAccommodations_SP (connection, clientName, testeeKey);
      assertEquals ("Number of Accomodations", expectedNumberOfAccommodations, result.getCount ());
      Iterator<DbResultRecord> records = result.getRecords ();
      int maxAccsToprint = 10;
      int printCount = 0;
      _logger.info ("===================T_GetRTSAccommodations Records=======================");
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertNotNull ("Accomodation", record);
        assertNotNull ("Accomodation", record.<String> get ("Subject"));
        assertNotNull ("Accomodation", record.<String> get ("AccCode"));
        if (printCount < maxAccsToprint) {
          _logger.info (String.format ("Subject: %s ", record.<String> get ("Subject")));
          _logger.info (String.format ("AccCode: %s ", record.<String> get ("AccCode")));
          printCount++;
        }
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }
  
  
  private void test_GetRTSAttribute_SP (String clientName, Long testeeKey, String attName, String expectedAttValue, String testName) throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      _Ref<String> attValue = new _Ref<String> ();
      _rtsDll._GetRTSAttribute_SP (connection, clientName, testeeKey, attName, attValue);
      assertNotNull (testName, attValue.get ());
      assertEquals (testName, expectedAttValue, attValue.get ());
      _logger.info (String.format ("Attvalue: %s", attValue.get ()));

    }
  }

  @Test
  public void test_GetRTSAttribute_SP1 () throws Exception {
    String clientName = "Oregon";
    Long testeeKey = -1L;
    String attName = "--ACCOMMODATIONS--";
    String expectedAttValue = "EL:TDS_ASL1;EL:TDS_ClosedCap0;EL:TDS_CC0;EL:TDS_TTS0;EL:TDS_WL0;EL:TDS_WLA0;EL:ENU;EL:TDS_PoD0;EL:TDS_PS_L0;MA:TDS_ASL1;MA:TDS_ClosedCap0;MA:TDS_CC0;MA:TDS_TTS0;MA:TDS_WL0;MA:TDS_WLA0;MA:ENU;MA:TDS_PoD0;MA:TDS_PS_L0;RE:TDS_ASL1;RE:TDS_ClosedCap0;RE:TDS_CC0;RE:TDS_TTS0;RE:TDS_WL0;RE:TDS_WLA0;RE:ENU;RE:TDS_PoD0;RE:TDS_PS_L0;SC:TDS_ASL1;SC:TDS_ClosedCap0;SC:TDS_CC0;SC:TDS_TTS0;SC:TDS_WL0;SC:TDS_WLA0;SC:ENU;SC:TDS_PoD0;SC:TDS_PS_L0;SS:TDS_ASL1;SS:TDS_ClosedCap0;SS:TDS_CC0;SS:TDS_TTS0;SS:TDS_WL0;SS:TDS_WLA0;SS:ENU;SS:TDS_PoD0;SS:TDS_PS_L0;ELA:TDS_ASL1;ELA:TDS_ClosedCap0;ELA:TDS_CC0;ELA:TDS_TTS0;ELA:TDS_WL0;ELA:TDS_WLA0;ELA:ENU;ELA:TDS_PoD0;ELA:TDS_PS_L0;EL:TDS_ASL1;EL:TDS_ClosedCap0;EL:TDS_CC0;EL:TDS_TTS0;EL:TDS_WL0;EL:TDS_WLA0;EL:ENU;EL:TDS_PoD0;EL:TDS_PS_L0;MA:TDS_ASL1;MA:TDS_ClosedCap0;MA:TDS_CC0;MA:TDS_TTS0;MA:TDS_WL0;MA:TDS_WLA0;MA:ENU;MA:TDS_PoD0;MA:TDS_PS_L0;RE:TDS_ASL1;RE:TDS_ClosedCap0;RE:TDS_CC0;RE:TDS_TTS0;RE:TDS_WL0;RE:TDS_WLA0;RE:ENU;RE:TDS_PoD0;RE:TDS_PS_L0;SC:TDS_ASL1;SC:TDS_ClosedCap0;SC:TDS_CC0;SC:TDS_TTS0;SC:TDS_WL0;SC:TDS_WLA0;SC:ENU;SC:TDS_PoD0;SC:TDS_PS_L0;SS:TDS_ASL1;SS:TDS_ClosedCap0;SS:TDS_CC0;SS:TDS_TTS0;SS:TDS_WL0;SS:TDS_WLA0;SS:ENU;SS:TDS_PoD0;SS:TDS_PS_L0;ELA:TDS_ASL1;ELA:TDS_ClosedCap0;ELA:TDS_CC0;ELA:TDS_TTS0;ELA:TDS_WL0;ELA:TDS_WLA0;ELA:ENU;ELA:TDS_PoD0;ELA:TDS_PS_L0";
  
    test_GetRTSAttribute_SP (clientName, testeeKey, attName, expectedAttValue, "Accommodations");

  }

  

  @Test
  public void test_GetRTSAttribute_SP2 () throws Exception {
    String clientName = "Oregon";
    Long testeeKey = -1L;
    String attName = "--ENTITYNAME--";
    String expectedAttValue = "Sojka, Bud Johan";

    test_GetRTSAttribute_SP (clientName, testeeKey, attName, expectedAttValue, "Entity Name");
  }

  // TODO: Ethnicity not in package
  @Ignore
  @Test
  public void test_GetRTSAttribute_SP3 () throws Exception {
    String clientName = "Oregon";
    Long testeeKey = -1L;
    String attName = "--ETHNICITY--";
    String expectedAttValue = "6";
    
    test_GetRTSAttribute_SP (clientName, testeeKey, attName, expectedAttValue, "Ethnicity");
  }

  @Test
  public void test_GetRTSAttribute_SP4 () throws Exception {
    String clientName = "Oregon";
    Long testeeKey = -1L;
    String attName = "--ELIGIBLETESTS--";
    String expectedAttValue = "DCAS-EOC-AlgebraII;DCAS-EOC-AlgebraII;DCAS-EOC-AlgebraII";
    
    test_GetRTSAttribute_SP (clientName, testeeKey, attName, expectedAttValue, "Eligible Tests");
  }

  @Test
  public void test_GetRTSAttribute_SP5 () throws Exception {
    String clientName = "Oregon";
    Long testeeKey = -1L;
    String attName = "BirthDtTxt";
    String expectedAttValue = "31082013";

    test_GetRTSAttribute_SP (clientName, testeeKey, attName, expectedAttValue, "Birth Date");
  }

  @Test
  public void test_GetRTSAttribute_SP6 () throws Exception {
    String clientName = "Oregon";
    Long testeeKey = -1L;
    String attName = "LglFNm";
    String expectedAttValue = "Bud";

    test_GetRTSAttribute_SP (clientName, testeeKey, attName, expectedAttValue, "First Name");
  }

  @Test
  public void test_GetRTSAttribute_SP7 () throws Exception {
    String clientName = "Oregon";
    Long testeeKey = -1L;
    String attName = "LglLNm";
    String expectedAttValue = "Sojka";

    test_GetRTSAttribute_SP (clientName, testeeKey, attName, expectedAttValue, "Last Name");
  }

  @Test
  public void test_GetRTSAttribute_SP8 () throws Exception {
    String clientName = "Oregon";
    Long testeeKey = -1L;
    String attName = "Gndr";
    String expectedAttValue = "F";

    test_GetRTSAttribute_SP (clientName, testeeKey, attName, expectedAttValue, "Gender");
    
  }

  @Test
  public void test_GetRTSAttribute_SP9 () throws Exception {
    String clientName = "Oregon";
    Long testeeKey = -1L;
    String attName = "EnrlGrdCd";
    String expectedAttValue = "05";

    test_GetRTSAttribute_SP (clientName, testeeKey, attName, expectedAttValue, "Grade Code");
  }

  @Test
  public void test_GetRTSAttribute_SP10 () throws Exception {
    String clientName = "Oregon";
    Long testeeKey = -1L;
    String attName = "ExternalID";
    String expectedAttValue = "82811007";

    test_GetRTSAttribute_SP (clientName, testeeKey, attName, expectedAttValue, "External ID");
  }

  @Test
  public void test_GetRTSAttribute_SP11 () throws Exception {
    String clientName = "Oregon";
    Long testeeKey = -1L;
    String attName = "LEPfg";
    String expectedAttValue = "Yes";

    test_GetRTSAttribute_SP (clientName, testeeKey, attName, expectedAttValue, "LEP Status");
  }

  // TODO blocked subject not in package
  @Ignore
  @Test
  public void test_GetRTSAttribute_SP12 () throws Exception {
    String clientName = "Oregon";
    Long testeeKey = -1L;
    String attName = "BLOCKEDSUBJECT";
    String expectedAttValue = "Writing";

    test_GetRTSAttribute_SP (clientName, testeeKey, attName, expectedAttValue, "Blocked Subject");
  }
  
  @Test
  public void test_GetRTSAttribute_SP13 () throws Exception {
    String clientName = "Oregon";
    Long testeeKey = -1L;
    String attName = "TDS-TestForm";
    String expectedAttValue = "DCAS-EOC-AlgebraII-Spring-2012-2013;DCAS-EOC-AlgebraII-Spring-2012-2013;DCAS-EOC-AlgebraII-Spring-2012-2013";

    test_GetRTSAttribute_SP (clientName, testeeKey, attName, expectedAttValue, "TDS TestForm");
  }

  @Test
  public void test_GetRTSEntity_SP () throws Exception {
    String externalId = "-1";
    String entityType = "STUDENT";
    String clientname = "Oregon";
    Long expectedEntityKey = -1L;
    
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      _Ref<Long> entityKeyRef = new _Ref<> ();
      _rtsDll._GetRTSEntity_SP (connection, clientname, externalId, entityType, entityKeyRef);
      _logger.info (String.format ("EntityKey: %s", entityKeyRef.get ()));
      assertEquals ("Student Key", expectedEntityKey, entityKeyRef.get ());
    }
  }
  
  private void test_GetRTSRelationship_SP (String clientName, Long testeeKey, String relationship, Long expectedEntityKey, String expectedEntityId,  String expectedEntityName, String testName) throws Exception {
  try (SQLConnection connection = _connectionManager.getConnection ()) {
    _Ref<Long> entityKey = new _Ref<Long> ();
    _Ref<String> entityId = new _Ref<String> ();
    _Ref<String> entityName = new _Ref<String> ();

    _rtsDll._GetRTSRelationship_SP (connection, clientName, testeeKey, relationship, entityKey, entityId, entityName);
    assertNotNull (testName + "Entity Key", entityKey.get ());
    assertNotNull (testName + "Entity ID", entityId.get ());
    assertNotNull (testName + "Entity Name", entityName.get ());
    assertEquals (testName + "Entity Key", expectedEntityKey, entityKey.get ());
    assertEquals (testName + "Entity ID", expectedEntityId, entityId.get ());
    assertTrue (testName + "Entity Name", expectedEntityName.equalsIgnoreCase (entityName.get ()));
    _logger.info (String.format ("entityKey: %d", entityKey.get ()));
    _logger.info (String.format ("entityId: %s", entityId.get ()));
    _logger.info (String.format ("entityName: %s", entityName.get ()));
  }
  }

  // TODO entity key does not exist in package
  @Test
  public void test_GetRTSRelationship_SP1 () throws Exception {

    String clientName = "Oregon";
    Long testeeKey = -1L;
    String relationship = "ENRLINST-STUDENT";
    Long expectedEntityKey = 0L;
    String expectedEntityId = "8716411";
    String expectedEntityName = "Wren Reedbuck High School";

    test_GetRTSRelationship_SP (clientName, testeeKey, relationship, expectedEntityKey, expectedEntityId, expectedEntityName, "EnrlInst Student");

  }

  // TODO entity key does not exist in package
  @Test
  public void test_GetRTSRelationship_SP2 () throws Exception {

    String clientName = "Oregon";
    Long testeeKey = -1L;
    String relationship = "ENRDIST-STUDENT";
    Long expectedEntityKey = 0L;
    String expectedEntityId = "71715";
    String expectedEntityName = "LA County Schools";

    test_GetRTSRelationship_SP (clientName, testeeKey, relationship,  expectedEntityKey, expectedEntityId,  expectedEntityName, "EnrlDist Student");
  }

  // TODO entity keys does not exist in package
  @Test
  public void test_GetTesteeRelationships_SP () throws Exception {

    String clientname = "Oregon";
    Long testeeKey = -1L;

    // relationType, entityKey, TDS_ID, attval
    String expectedOutput[][] =
    { { "District", "0", "DistrictID", "71715" },
        { "District", "0", "DistrictName", "LA County Schools" },
        { "School", "0", "SchoolID", "8716411" },
        { "School", "0", "SchoolName", "Wren Reedbuck High School" } };

    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet rs = _rtsDll._GetTesteeRelationships_SP (connection, clientname, testeeKey);

      Iterator<DbResultRecord> records = rs.getRecords ();
      assertEquals ("Testee Relationships number of records", expectedOutput.length, rs.getCount ());
      int i = 0;
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertEquals ("Relation Type", expectedOutput[i][0], record.<String> get ("relationType"));
        assertEquals ("Entity Key", expectedOutput[i][1], record.<String> get ("entityKey"));
        assertEquals ("TDS ID", expectedOutput[i][2], record.<String> get ("TDS_ID"));
        assertEquals ("Attribute Value", expectedOutput[i][3], record.<String> get ("attval"));
        i++;
      }
    }
  }

  @Test
  public void test_P_GetAllTests_SP () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    int sessionType = 0;
    Long proctorKey = -1L;
    int expectedNumberOfTests = 3;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _rtsDll.P_GetAllTests_SP (connection, clientName, sessionType, proctorKey);

      assertEquals ("Proctor number of tests", expectedNumberOfTests, result.getCount ());
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        assertNotNull ("Proctor Tests", record);
        _logger.info ("===================P_GetAllTests Records=======================");
        _logger.info (String.format ("TestID: %s", record.<String> get ("TestID")));
        _logger.info (String.format ("Subject: %s ", record.<String> get ("Subject")));
        _logger.info (String.format ("DisplayName: %s ", record.<String> get ("DisplayName")));
        _logger.info (String.format ("TestKey: %s ", record.<String> get ("TestKey")));
      }
      _logger.info (String.format ("Total no: of records -- %d", result.getCount ()));
    }
  }
  
  @Ignore
  @Test
  // success case #1
  public void test_ValidateAsProctor_SP () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    Long userKey = 35840L;
    _Ref<Long> userEntityRef = new _Ref<> ();
    _Ref<Long> roleEntityRef = new _Ref<> ();
    _Ref<String> roleRef = new _Ref<> ();
    int sessionType = 0;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      _rtsDll._ValidateAsProctor_SP (connection, clientName, userKey, userEntityRef, roleEntityRef, roleRef, sessionType);
      assertTrue(userEntityRef.get () == 957492);
      assertTrue(roleEntityRef.get () == 370331);
      assertTrue("TA".equalsIgnoreCase (roleRef.get ()));
      _logger.info ("userEntityRef: " + userEntityRef.get ());
      _logger.info ("roleEntityRef: " + roleEntityRef.get ());
      _logger.info ("roleRef: " + roleRef.get ());
    }
  }

  @Ignore
  @Test
  // success case #2 (if certify == 1)
  public void test_ValidateAsProctor_SP1 () throws SQLException, ReturnStatusException {
    String clientName = "Minnesota";
    Long userKey = 20628L;
    _Ref<Long> userEntityRef = new _Ref<> ();
    _Ref<Long> roleEntityRef = new _Ref<> ();
    _Ref<String> roleRef = new _Ref<> ();
    int sessionType = 0;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      _rtsDll._ValidateAsProctor_SP (connection, clientName, userKey, userEntityRef, roleEntityRef, roleRef, sessionType);
      _logger.info ("userEntityRef: " + userEntityRef.get ());
      _logger.info ("roleEntityRef: " + roleEntityRef.get ());
      _logger.info ("roleRef: " + roleRef.get ());

      assertTrue(userEntityRef.get () == 677817);
      assertTrue(roleEntityRef.get () == 4250);
      assertTrue("TM".equalsIgnoreCase (roleRef.get ()));
    }
  }

  @Ignore
  @Test
  // success case #3
  public void test_ValidateAsProctor_SP3 () throws SQLException, ReturnStatusException {
    String clientName = "Hawaii";
    Long userKey = 20628L;
    _Ref<Long> userEntityRef = new _Ref<> ();
    _Ref<Long> roleEntityRef = new _Ref<> ();
    _Ref<String> roleRef = new _Ref<> ();
    int sessionType = 0;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      _rtsDll._ValidateAsProctor_SP (connection, clientName, userKey, userEntityRef, roleEntityRef, roleRef, sessionType);
      _logger.info ("userEntityRef: " + userEntityRef.get ());
      _logger.info ("roleEntityRef: " + roleEntityRef.get ());
      _logger.info ("roleRef: " + roleRef.get ());

      assertTrue(userEntityRef.get () == null);
      assertTrue(roleEntityRef.get () == null);
      assertTrue(roleRef.get () == null);
    }
  }

  @Test
  // success case #1
  public void test_P_ValidateProctor_SP () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    String proctorId = "test1@air.org";//"TA1@air.org";
    UUID browserKey = UUID.fromString ("28EE3122-3C0C-408E-814E-7D5DF63FFBD7");
    String password = null;
    int oppSessions = 0;
    Boolean ignorepw = true;
    int sessionType = 0;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _rtsDll.P_ValidateProctor_SP (connection, clientName, browserKey, proctorId, password, oppSessions, ignorepw, sessionType);
      DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
      assertTrue(record != null);
      if (record != null) {
        _logger.info (String.format ("Status: %s", record.<String> get ("status")));
        _logger.info (String.format ("userKey: %d", record.<Long> get ("userKey")));
        _logger.info (String.format ("fullname: %s", record.<String> get ("fullname")));
        _logger.info (String.format ("role: %s", record.<String> get ("role")));
        _logger.info (String.format ("Reason: %s", record.<String> get ("reason")));

        assertTrue("success".equalsIgnoreCase (record.<String> get ("status")));
        assertTrue(record.<Long> get ("userKey") == 35756);
        assertTrue("dfd dfdf".equalsIgnoreCase (record.<String> get ("fullname")));
        //assert ("TA".equalsIgnoreCase (record.<String> get ("role")));
        assertTrue(record.<String> get ("role") == null);
        assertTrue(record.<String> get ("reason") == null);
      }
    }
  }
  
  @Test
  // success case #2 for the oppsessions = 1
  public void test_P_ValidateProctor_SP1 () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    String proctorId = "test1@air.org";//"TA1@air.org";
    UUID browserKey = UUID.fromString ("28EE3122-3C0C-408E-814E-7D5DF63FFBD7");
    String password = null;
    int oppSessions = 1;
    Boolean ignorepw = true;
    int sessionType = 0;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _rtsDll.P_ValidateProctor_SP (connection, clientName, browserKey, proctorId, password, oppSessions, ignorepw, sessionType);
      DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
      assertTrue(record != null);
      if (record != null) {
        _logger.info (String.format ("Status: %s", record.<String> get ("status")));
        _logger.info (String.format ("userKey: %d", record.<Long> get ("userKey")));
        _logger.info (String.format ("fullname: %s", record.<String> get ("fullname")));
        _logger.info (String.format ("role: %s", record.<String> get ("role")));
        _logger.info (String.format ("Reason: %s", record.<String> get ("reason")));

        assertTrue("success".equalsIgnoreCase (record.<String> get ("status")));
        assertTrue(record.<Long> get ("userKey") == 35756);
        assertTrue("dfd dfdf".equalsIgnoreCase (record.<String> get ("fullname")));

        //assert ("TA".equalsIgnoreCase (record.<String> get ("role")));
        assertTrue(record.<String> get ("role") == null);
        assertTrue(record.<String> get ("reason") == null);
      }
    }
  }

  @Test
  // failure case
  public void test_P_ValidateProctor_SP2 () throws SQLException, ReturnStatusException {
    String clientName = "Oregon";
    String proctorId = "test1@air.org";//"TA1@air.org";
    UUID browserKey = UUID.fromString ("28EE3122-3C0C-408E-814E-7D5DF63FFBD7");
    String password = "9cu+IIEpgw1U0UjHhYwcUrK7FJ4BJ6Ef/pDMauaObb8FmcAiCIE=";
    int oppSessions = 0;
    Boolean ignorepw = false;
    int sessionType = 0;
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      SingleDataResultSet result = _rtsDll.P_ValidateProctor_SP (connection, clientName, browserKey, proctorId, password, oppSessions, ignorepw, sessionType);
      DbResultRecord record = result.getRecords ().next ();
      assertTrue(record != null);
      String status = record.<String> get ("status");
      String reason = record.<String> get ("Reason");
      String context = record.<String> get ("context");
      String appkey = record.<String> get ("appkey");
      _logger.info (String.format ("Status: %s", status));
      _logger.info (String.format ("Reason: %s", reason));
      _logger.info (String.format ("context: %s", context));
      _logger.info (String.format ("appkey: %s", appkey));
      assertTrue("failed".equalsIgnoreCase (status));
      assertTrue(("Unable to log you in. Please check your account in TIDE to be sure it is fully activated. [10161]").equalsIgnoreCase (reason));
      assertTrue("P_ValidateProctor".equalsIgnoreCase (context));
      assertTrue("Unable to log you in. Please check your account in UMS to be sure it is fully activated.".equalsIgnoreCase (appkey));
    }
  }
  
  
  @Test
  public void test_R_GetDistricts_SP () throws Exception {
      String clientname = "SBAC_PT";
      try (SQLConnection connection = _connectionManager.getConnection ()) {
        SingleDataResultSet result = _rtsDll.R_GetDistricts_SP (connection, clientname);
        DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
        assertTrue(record != null);
        if (record != null) {
          String districtName = record.<String> get ("DistrictName");
          System.out.println(districtName);
          String districtId = record.<String> get ("DistrictID");
          System.out.println(districtId);
          String rtsKey = record.<String> get ("RTSKey");
          System.out.println(districtId);

        }
      }
    }
    
    
}
