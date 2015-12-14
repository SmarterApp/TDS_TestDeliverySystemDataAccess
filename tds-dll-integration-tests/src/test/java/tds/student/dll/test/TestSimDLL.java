/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.student.dll.test;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opentestsystem.shared.test.LifecycleManagingTestRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import TDS.Shared.Exceptions.ReturnStatusException;

import AIR.Common.DB.AbstractConnectionManager;
import AIR.Common.DB.AbstractDLL;
import AIR.Common.DB.DataBaseTable;
import AIR.Common.DB.RollbackConnectionManager;
import AIR.Common.DB.SQLConnection;
import AIR.Common.DB.SqlParametersMaps;
import AIR.Common.DB.results.DbResultRecord;
import AIR.Common.DB.results.MultiDataResultSet;
import AIR.Common.DB.results.SingleDataResultSet;
import AIR.Common.Sql.AbstractDateUtilDll;

import tds.dll.api.ISimDLL;
import tds.dll.api.IStudentDLL;

@RunWith (LifecycleManagingTestRunner.class)
@ContextConfiguration (locations = "/test-context-sim.xml")
@ActiveProfiles ({ "SIMSBAC" })
public class TestSimDLL
{
  private static Logger             _logger                                 = LoggerFactory.getLogger (TestSimDLL.class);

  @Autowired
  private ISimDLL                   _dll;
  @Autowired
  private IStudentDLL               _studentDll;
  @Autowired
  private AbstractDateUtilDll       _dateUtil     = null;

  @Autowired
  private AbstractConnectionManager _connectionManager                      = null;

  MyHelper                          _myHelper                               = new MyHelper ();

  protected final String            SESSIONAFTERSIM                         = "5F8CEA73-F7A0-407A-B796-55557AF219DF";
  protected final String            SESSIONWTESTS                           = "FBD15B7C-0C88-4B46-8520-A019422F103E";
  protected final String            SESSIONNOTESTS                          = "79801BB2-506B-496F-9C2B-5959BB84E4C7";
  protected final String            TESTKEYWSTIMULUSMULTISEG                = "(SBAC)Test5-G10M-ON-PISA-Fall-2013-2014";
  protected final String            SEGMENTKEY_FOR_TESTKEYWSTIMULUSMULTISEG = "(SBAC)CAT-M10-ONCal-S1-A4-MATH-11-Fall-2013-2014";
  protected final String            GROUPID_FOR_TESTKEYWSTIMULUSMULTISEG    = "G-200-1052";

  protected final String            TESTKEYNOSTIMULUSMULTISEG               = "(SBAC)Test1-G11M-ON-ON-Fall-2013-2014";

//  @Test
//  public void test1 () throws Exception {
//    int insertedCnt = 0;
//    SingleDataResultSet result= null;
//    DbResultRecord record = null;
//    Long maxKey1 = null;
//    Long maxKey2 = null;
//    SQLConnection connection1 = null;
//    SQLConnection connection2 = null;
//    try {
//      connection1 = _connectionManager.getConnection ();
//      connection2 = _connectionManager.getConnection (); 
//      
//      final String SQL_QUERY5 = "insert into _anonymoustestee (dateCreated, clientname) values ('2014-08-01 10:42:53.067', 'SBAC_PT')";
//      insertedCnt = _myHelper.executeStatement (connection1, SQL_QUERY5, null, false).getUpdateCount ();
//
//      final String SQL_QUERY6 = "insert into _anonymoustestee (dateCreated, clientname) values ('2014-08-01 10:42:55.099', 'SBAC_PT')";   
//      insertedCnt = _myHelper.executeStatement (connection2, SQL_QUERY6, null, false).getUpdateCount ();
//
//      final String SQL_QUERY7 = "select cast(LAST_INSERT_ID() as SIGNED) as maxkey";
//
//      result = _myHelper.executeStatement (connection2, SQL_QUERY7, null, false).getResultSets ().next ();
//      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
//      if (record != null) {
//        maxKey2 = record.<Long> get ("maxKey");
//      }
//      result = _myHelper.executeStatement (connection1, SQL_QUERY7, null, false).getResultSets ().next ();
//      record = (result.getCount () > 0 ? result.getRecords ().next () : null);
//      if (record != null) {
//        maxKey1 = record.<Long> get ("maxKey");
//      }
//      
//      connection1.close ();
//      connection2.close ();
//    } catch (SQLException se) {
//      _logger.debug (se.getMessage ());
//      throw se;
//    } catch (ReturnStatusException re) {
//      connection1.close ();
//      connection2.close ();
//      _logger.debug (re.getMessage ());
//      throw re;
//    }
//  }

  // To find testkey and whether they are segmented
  // select S.TestID, S.isSegmented as segmented , S._key
  // from simsbacitembank.tblsetofadminsubjects S,
  // simsbacconfigs.client_testproperties P
  // where P.clientname = 'SBAC' and P.testID = S.TestID

  @Test
  public void test_SIM_GetUserClients_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      SingleDataResultSet rs = _dll.SIM_GetUserClients_SP (connection, "efurman");

    }
  }

  @Test
  public void test_SIM_ValidateUser_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      SingleDataResultSet rs = _dll.SIM_ValidateUser_SP (connection, "efurman");

    }
  }

  @Test
  public void test_SIM_CreateSession_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      SingleDataResultSet rs = _dll.SIM_CreateSession_SP (connection, "SBAC", "efurman", "abc");

    }
  }

  @Test
  public void test_SIM_GetUserSessions_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      SingleDataResultSet rs = _dll.SIM_GetUserSessions_SP (connection, "efurman", "SBAC");

    }
  }

  @Test
  public void test_SIM_AddSessionTest2_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONNOTESTS);
      String testKey = TESTKEYWSTIMULUSMULTISEG;

      SingleDataResultSet rs = _dll.SIM_AddSessionTest2_SP (connection, session, testKey);
    }
  }

  @Test
  public void test_SIM_CopySession2_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONWTESTS);
      SingleDataResultSet rs3 = _dll.SIM_CopySession2_SP (connection, session, "abccopy");
    }
  }

  @Test
  public void test_SIM_GetSessionTests2_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONWTESTS);
      SingleDataResultSet rs = _dll.SIM_GetSessionTests2_SP (connection, session);
    }
  }

  @Test
  public void test_SIM_SetSessionAbort_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONWTESTS);
      Boolean abort = true;
      _dll.SIM_SetSessionAbort_SP (connection, session, abort);
    }
  }

  @Test
  public void test_SIM_SetSessionDescription_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      UUID session = UUID.fromString (SESSIONWTESTS);
      String description = "testdescription";
      _dll.SIM_SetSessionDescription_SP (connection, session, description);
    }
  }

  @Test
  public void test_SIM_AlterSegmentIemgroup_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONWTESTS);
      String testKey = TESTKEYWSTIMULUSMULTISEG;
      String segmentKey = SEGMENTKEY_FOR_TESTKEYWSTIMULUSMULTISEG;
      String groupId = GROUPID_FOR_TESTKEYWSTIMULUSMULTISEG;
      Integer maxItems = 5; // random

      SingleDataResultSet rs = _dll.SIM_AlterSegmentItemgroup_SP (connection, session, testKey, segmentKey, groupId, maxItems);
    }
  }

  @Test
  public void test_SIM_AlterSegmentStrand2_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONWTESTS);
      String testKey = TESTKEYWSTIMULUSMULTISEG;
      String segmentKey = SEGMENTKEY_FOR_TESTKEYWSTIMULUSMULTISEG;
      String strand = "SBAC-1";
      // random rest
      Integer maxItems = 5;
      Integer minItems = 2;
      Float bpweight = (float) 0.5;
      Boolean isStrictMax = false;
      Float startAbility = (float) 0.8;
      Float startInfo = (float) 1.0;

      SingleDataResultSet rs = _dll.SIM_AlterSegmentStrand2_SP (connection, session, testKey, segmentKey, strand,
          minItems, maxItems, bpweight, isStrictMax, startAbility, startInfo);
    }
  }

  @Test
  public void test_SIM_AlterSegmentContentLevel_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONWTESTS);
      String testKey = TESTKEYWSTIMULUSMULTISEG;
      String segmentKey = SEGMENTKEY_FOR_TESTKEYWSTIMULUSMULTISEG;
      String contentLevel = "SBAC-1|P|TS01";
      // random rest
      Integer maxItems = 5;
      Integer minItems = 2;
      Float bpweight = (float) 0.5;
      Boolean isStrictMax = false;

      SingleDataResultSet rs = _dll.SIM_AlterSegmentContentLevel_SP (connection, session, testKey, segmentKey, contentLevel,
          minItems, maxItems, bpweight, isStrictMax);
    }
  }

  @Test
  public void test_SIM_GetTestBlueprint2_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONWTESTS);
      String testKey = TESTKEYWSTIMULUSMULTISEG;
      MultiDataResultSet sets = _dll.SIM_GetTestBlueprint2_SP (connection, session, testKey);
      Integer numResSets = sets.getCount ();
      for (int i = 0; i < numResSets; i++) {
        SingleDataResultSet rs = sets.get (i);
        if (i == 0)
          dumpBP1 (rs);
        else if (i == 1)
          dumpBP2 (rs);
        else if (i == 2)
          dumpBP3 (rs);
      }
    }
  }

  private void dumpBP1 (SingleDataResultSet rs) throws Exception {

  }

  private void dumpBP2 (SingleDataResultSet rs) throws Exception {

  }

  private void dumpBP3 (SingleDataResultSet rs) throws Exception {

  }

  @Test
  public void test_SIM_GetTestItems_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONWTESTS);
      String testKey = TESTKEYWSTIMULUSMULTISEG;
      MultiDataResultSet sets = _dll.SIM_GetTestItems_SP (connection, session, testKey);
      Integer numResSets = sets.getCount ();
      for (int i = 0; i < numResSets; i++) {
        SingleDataResultSet rs = sets.get (i);
        if (i == 0)
          dumpItems1 (rs);
        else if (i == 1)
          dumpItems2 (rs);
      }
    }
  }

  private void dumpItems1 (SingleDataResultSet rs) throws Exception {

  }

  private void dumpItems2 (SingleDataResultSet rs) throws Exception {

  }

  @Test
  public void test_SIM_ReportBPSatisfaction_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONWTESTS);
      String testKey = TESTKEYWSTIMULUSMULTISEG;
      Boolean reportBlueprint = true;

      MultiDataResultSet sets = _dll.SIM_ReportBPSatisfaction_SP (connection, session, testKey, reportBlueprint);
      Integer numResSets = sets.getCount ();
      for (int i = 0; i < numResSets; i++) {
        SingleDataResultSet rs = sets.get (i);
        if (reportBlueprint) {
          // expect 3 result sets, first and second specific for
          // reportBlueprint=true case only
          if (i == 0)
            dumpBPSat1 (rs);
          else if (i == 1)
            dumpBPSat2 (rs);
          else if (i == 2)
            dumpBPSat3 (rs);
        } else {
          if (i == 0)
            dumpBPSat3 (rs);
          // expect just one result set, same as the third one for
          // reportBlueprint=true case
        }
      }
    }
  }

  private void dumpBPSat1 (SingleDataResultSet rs) throws Exception {

  }

  private void dumpBPSat2 (SingleDataResultSet rs) throws Exception {

  }

  private void dumpBPSat3 (SingleDataResultSet rs) throws Exception {

  }

  @Test
  public void test_SIM_AlterSegmentItem_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONWTESTS);
      String testKey = TESTKEYWSTIMULUSMULTISEG;
      String segmentKey = SEGMENTKEY_FOR_TESTKEYWSTIMULUSMULTISEG;
      String itemKey = "200-11415";
      // rest are fields that are set up
      Boolean isActive = false;
      Boolean isRequired = false;

      SingleDataResultSet rs = _dll.SIM_AlterSegmentItem_SP (connection, session, testKey,
          segmentKey, itemKey, isActive, isRequired);
    }
  }

  @Test
  public void test_SIM_ValidateBluePrints_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONWTESTS);
      MultiDataResultSet sets = _dll.SIM_ValidateBlueprints_SP (connection, session);
      // first set will contain one rec with 'status','fatals','warnings'
      // if status is not 'success', there should be
      // second set that contain set of recs with 'severity', 'object', 'error'
    }
  }

  @Test
  public void test_SIM_DeleteTest_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONWTESTS);
      String testKey = TESTKEYWSTIMULUSMULTISEG;
      SingleDataResultSet rs = _dll.SIM_DeleteTest_SP (connection, session, testKey);
    }
  }

  @Test
  public void test_SIM_AlterSessionTest2_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONWTESTS);
      String testKey = TESTKEYWSTIMULUSMULTISEG;
      // rest of parms are being set
      Integer iterations = 3;
      Integer opportunities = 4;
      Float meanProficiency = (float) 0;
      Float sdProficiency = (float) 1;
      Float strandCorrelation = (float) 1;

      SingleDataResultSet rs = _dll.SIM_AlterSessionTest2_SP (connection, session, testKey, iterations,
          opportunities, meanProficiency, sdProficiency, strandCorrelation);
    }
  }

  @Test
  public void test_SIM_DeleteSession_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONWTESTS);
      SingleDataResultSet rs = _dll.SIM_DeleteSession_SP (connection, session);
    }
  }

  @Test
  public void test_SIM_AlterSegment2_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONWTESTS);
      String testkey = TESTKEYWSTIMULUSMULTISEG;
      String segmentkey = SEGMENTKEY_FOR_TESTKEYWSTIMULUSMULTISEG;

      // rest are the values being set
      Float startAbility = (float) 0;
      Float startInfo = (float) 0.2;
      Integer minItems = 31;
      Integer maxItems = 31;
      Float bpweight = (float) 50;
      Integer cset1size = 200;
      Integer cset2InitialRandom = 286;
      Integer cset2Random = 200;
      Float itemWeight = (float) 1;
      Float abilityOffset = (float) 0;

      SingleDataResultSet rs = _dll.SIM_AlterSegment2_SP (connection, session, testkey,
          segmentkey, startAbility, startInfo, minItems, maxItems, bpweight, cset1size,
          cset2InitialRandom, cset2Random, itemWeight, abilityOffset);
    }
  }

  @Test
  public void test_SIM_ReportOPItemDistribution_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      // TODO session, testkey will be real value
      // need session for which simulation was ran
      UUID session = null;
      String testkey = null;

      Float distributionInterval = (float) 0.20;

      SingleDataResultSet rs = _dll.SIM_ReportOPItemDistribution_SP (connection, session,
          testkey, distributionInterval);
      Iterator<DbResultRecord> itr = rs.getRecords ();
      while (itr.hasNext ()) {
        DbResultRecord rcd = itr.next ();
        String f1 = rcd.<String> get ("% Tests");
        String f2 = rcd.<String> get ("# Tests");
        Float itemPct = rcd.<Float> get ("% Item Pool");
        Integer itemCnt = rcd.<Integer> get ("# Unique Items");
        Integer poolSize = rcd.<Integer> get ("poolsize");
        System.out.println (String.format ("%s, %s, %f, %d, %d", f1, f2, itemPct, itemCnt, poolSize));
      }
    }
  }

  @Test
  public void test_SIM_ReportOPItemDistribution_SP2 () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      // non-existant session
      UUID session = UUID.fromString ("C4967141-EEE0-4F3C-9FA3-B7CD4C0BC4B1");// (SESSIONWTESTS);
      String testkey = TESTKEYWSTIMULUSMULTISEG;
      Float distributionInterval = (float) 0.20;

      SingleDataResultSet rs = _dll.SIM_ReportOPItemDistribution_SP (connection, session,
          testkey, distributionInterval);
      Iterator<DbResultRecord> itr = rs.getRecords ();
      while (itr.hasNext ()) {
        DbResultRecord rcd = itr.next ();
        String f1 = rcd.<String> get ("% Tests");
        String f2 = rcd.<String> get ("# Tests");
        Float itemPct = rcd.<Float> get ("% Item Pool");
        Integer itemCnt = rcd.<Integer> get ("# Unique Items");
        Integer poolSize = rcd.<Integer> get ("poolsize");
        System.out.println (String.format ("%s, %s, %f, %d, %d", f1, f2, itemPct, itemCnt, poolSize));
      }
    }
  }

  @Test
  public void test_SIM_ItemDistribution_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONWTESTS);
      String testkey = TESTKEYWSTIMULUSMULTISEG;
      MultiDataResultSet rs = _dll.SIM_ItemDistribution_SP (connection, session, testkey);
    }
  }

  @Test
  public void test_SIM_ItemDistribution_SP2 () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONAFTERSIM);
      String testkey = TESTKEYWSTIMULUSMULTISEG;
      MultiDataResultSet rs = _dll.SIM_ItemDistribution_SP (connection, session, testkey);
    }
  }

  @Test
  public void test_SIM_ChangeSegmentNonReportingCategoryAsReportingCategory_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONWTESTS);
      String testkey = TESTKEYWSTIMULUSMULTISEG;
      String segmentkey = SEGMENTKEY_FOR_TESTKEYWSTIMULUSMULTISEG;
      String contentLevel = "SBAC-1|P|TS01";
      SingleDataResultSet rs = _dll.SIM_ChangeSegmentNonReportingCategoryAsReportingCategory_SP (connection, session,
          testkey, segmentkey, contentLevel);
    }
  }

  @Test
  public void test_SIM_ChangeSegmentReportingCategoryAsNonReportingCategory_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONWTESTS);
      String testkey = TESTKEYWSTIMULUSMULTISEG;
      String segmentKey = SEGMENTKEY_FOR_TESTKEYWSTIMULUSMULTISEG;
      String strand = "SBAC-1";
      SingleDataResultSet rs = _dll.SIM_ChangeSegmentReportingCategoryAsNonReportingCategory_SP (connection, session,
          testkey, segmentKey, strand);
      // expect 'failed' status returned
    }
  }

  @Test
  public void test_SIM_ChangeSegmentReportingCategoryAsNonReportingCategory_SP2 () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONWTESTS);
      String testkey = TESTKEYWSTIMULUSMULTISEG;
      String segmentKey = SEGMENTKEY_FOR_TESTKEYWSTIMULUSMULTISEG;
      String strand = "SBAC-1|P|TS01";
      SingleDataResultSet rs = _dll.SIM_ChangeSegmentReportingCategoryAsNonReportingCategory_SP (connection, session,
          testkey, segmentKey, strand);
      // expect 'success' status returned
    }
  }

  @Test
  public void test_SIM_GetErrors_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONAFTERSIM);
      SingleDataResultSet rs = _dll.SIM_GetErrors_SP (connection, session);
    }
  }

  @Test
  public void test_SIM_ReportOpportunities_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONAFTERSIM);
      String testkey = TESTKEYWSTIMULUSMULTISEG;

      MultiDataResultSet rs = _dll.SIM_ReportOpportunities_SP (connection, session, testkey);
    }
  }

  @Test
  public void test__PatchContentCounts_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONAFTERSIM);
      String testKey = TESTKEYWSTIMULUSMULTISEG;

      _dll.__PatchContentCounts_SP (connection, session, testKey);
    }
  }

  @Test
  public void test__PatchSegmentCounts_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONAFTERSIM);
      String testKey = TESTKEYWSTIMULUSMULTISEG;

      _dll.__PatchSegmentCounts_SP (connection, session, testKey);
    }
  }

  // Tested while testing __PatchontentCounts
  // @Test
  // public void test_RecordSegmentSatisfaction () throws Exception {
  // try (SQLConnection connection = _connectionManager.getConnection ()) {
  // // this is called in loop from __PathSegmentCounts,
  // // get oppkey value from there
  // UUID oppKey = null;
  // _dll._RecordSegmentSatisfaction_SP (connection, oppKey);
  // }
  // }

  @Test
  public void test_SIM_ClearSessionOpportunityData_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONAFTERSIM);

      SingleDataResultSet rs = _dll.SIM_ClearSessionOpportunityData_SP (connection, session);
    }
  }

  @Test
  public void test_SIM_ReportItems_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONAFTERSIM);
      String testkey = TESTKEYWSTIMULUSMULTISEG;

      MultiDataResultSet rs = _dll.SIM_ReportItems_SP (connection, session, testkey);
    }
  }

  @Test
  public void test_SIM_ReportRecycled_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONAFTERSIM);
      String testkey = TESTKEYWSTIMULUSMULTISEG;

      SingleDataResultSet rs = _dll.SIM_ReportRecycled_SP (connection, session, testkey);
    }
  }

  @Test
  public void test_SIM_ReportSegmentSatisfaction_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONAFTERSIM);
      String testkey = TESTKEYWSTIMULUSMULTISEG;

      SingleDataResultSet rs = _dll.SIM_ReportSegmentSatisfaction_SP (connection, session, testkey);
    }
  }

  @Test
  public void test_SIM_ReportBPSummary_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONAFTERSIM);
      String testkey = TESTKEYWSTIMULUSMULTISEG;
      // includes SIM_ReportBPSatisfaction, SIM_ReportSegmentSatisfaction
      // SIM_ReportOPItemDistribution and SIM_ReportRecycled
      MultiDataResultSet sets = _dll.SIM_ReportBPSummary_SP (connection, session, testkey);
    }
  }

  @Test
  public void test_SIM_ComputeCoverage05_FN () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONAFTERSIM);
      String testkey = TESTKEYWSTIMULUSMULTISEG;
      Float cov = _dll._SIM_ComputeCoverage05_FN (connection, session, testkey);
    }
  }

  @Test
  public void test_SIM_ComputeBias_FN () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONAFTERSIM);
      String testkey = TESTKEYWSTIMULUSMULTISEG;

      DataBaseTable tbl = _dll._SIM_ComputeBias_FN (connection, session, testkey);
      final String cmd = "select *  from ${tbl}";
      Map<String, String> unquotedParms = new HashMap<String, String> ();
      unquotedParms.put ("tbl", tbl.getTableName ());
      SingleDataResultSet rs = _myHelper.executeStatement (connection,
          _myHelper.fixDataBaseNames (cmd, unquotedParms), null, false).getResultSets ().next ();
    }
  }

  @Test
  public void test_SIM_Init2Firstb_Correlation_FN () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONAFTERSIM);
      String testkey = TESTKEYWSTIMULUSMULTISEG;

      DataBaseTable tbl = _dll._SIM_Init2Firstb_Correlation_FN (connection, session, testkey);
      final String cmd = "select *  from ${tbl}";
      Map<String, String> unquotedParms = new HashMap<String, String> ();
      unquotedParms.put ("tbl", tbl.getTableName ());
      SingleDataResultSet rs = _myHelper.executeStatement (connection,
          _myHelper.fixDataBaseNames (cmd, unquotedParms), null, false).getResultSets ().next ();
    }
  }

  @Test
  public void test_SIM_ReportSummaryStats_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONAFTERSIM);
      String testkey = TESTKEYWSTIMULUSMULTISEG;

      MultiDataResultSet sets = _dll.SIM_ReportSummaryStats_SP (connection, session, testkey);
    }
  }

  @Test
  public void test_SIM_ReportScores_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONAFTERSIM);
      String testkey = TESTKEYWSTIMULUSMULTISEG;

      MultiDataResultSet sets = _dll.SIM_ReportScores_SP (connection, session, testkey);
    }
  }

  @Test
  public void test_SIM_ReportFormDistributions_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONAFTERSIM);
      String testkey = TESTKEYWSTIMULUSMULTISEG;

      MultiDataResultSet sets = _dll.SIM_ReportFormDistributions_SP (connection, session, testkey);
    }
  }

  @Test
  public void test_SIM_FieldtestDistribution_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONAFTERSIM);
      String testkey = TESTKEYWSTIMULUSMULTISEG;

      MultiDataResultSet sets = _dll.SIM_FieldtestDistribution_SP (connection, session, testkey);
    }
  }

  @Test
  public void test_SIM_GetTestControls2_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID session = UUID.fromString (SESSIONAFTERSIM);
      String testkey = TESTKEYWSTIMULUSMULTISEG;

      MultiDataResultSet sets = _dll.SIM_GetTestControls2_SP (connection, session, testkey);

    }
  }

  @Test
  public void test_MakeItemscoreString_XML_FN () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {

      UUID oppkey = UUID.fromString ("A65F7E07-4A36-43DF-9044-0711C082E07D");
      String str = _studentDll.MakeItemscoreString_XML_FN (connection, oppkey);
      _logger.info (String.format ("%s", str));
    }
  }

  @Test
  public void testSIM_OpenSession_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      UUID session = UUID.fromString ("FBD15B7C-0C88-4B46-8520-A019422F103E");

      SingleDataResultSet rs = _dll.SIM_OpenSession_SP (connection, session);
    }
  }

  @Test
  public void testSIM_CleanupSessionTest_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      UUID session = UUID.fromString ("5F8CEA73-F7A0-407A-B796-55557AF219DF");
      String testkey = "(SBAC)Test5-G10M-ON-PISA-Fall-2013-2014";

      _dll.SIM_CleanupSessionTest (connection, session, testkey);
    }
  }

  @Test
  public void testSIM_InsertTesteeAttribute_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      String itentifier = "OVERALL";
      UUID oppkey = UUID.fromString ("4DA399D7-0B77-485B-BB04-320FC4266867");
      String context = "TRUE THETA";
      String attval = "0.77";
      int cnt = _dll.SIM_InsertTesteeAttribute_SP (connection, oppkey, itentifier, attval, context);
    }
  }

  @Test
  public void testSIM_InsertTesteeAttribute_SP1 () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      String itentifier = "OVERALL";
      UUID oppkey = UUID.fromString ("4DA399D7-0B77-485B-BB04-320FC4266867");
      String context = "TRUE THETAtest";
      String attval = "0.77";
      _dll.SIM_InsertTesteeAttribute_SP (connection, oppkey, itentifier, attval, context);
    }
  }

  @Test
  public void testAA_UpdateAbilityEstimates_SP () throws Exception {
    try (SQLConnection connection = _connectionManager.getConnection ()) {
      UUID oppkey = UUID.fromString ("4DA399D7-0B77-485B-BB04-320FC4266867");
      int itempos = 0;
      String strand = "SBAC-1";
      Float theta = 1.065f;
      Float info = 0.25f;
      Float lambda = 0.006f;
      _dll.AA_UpdateAbilityEstimates_SP (connection, oppkey, itempos, strand, theta, info, lambda);
    }
  }

  @Test
  public void test () throws Exception {
    String a = "C:\\tmp\\a1\\a2\\a3\\";
    String b = a.replace ('\\', java.io.File.separatorChar);
    String c = "/tmp/file1/file2/aa";
    String d = c.replace ('/', java.io.File.separatorChar);
    String path = "/tmp/Bank-200\\Items\\Item-200-24095\\item-200-24095.xml";
    path = replaceSeparatorChar (path);
    System.out.println (String.format ("sparator: %s", java.io.File.separatorChar));
    System.out.println (String.format ("a: %s, b: %s, c: %s, d: %s", a, b, c, d));
    System.out.println (String.format ("path: %s", path));
    System.out.println ("done");
  }

  private String replaceSeparatorChar (String str) {
    return str.replace ('/', java.io.File.separatorChar).replace ('\\', java.io.File.separatorChar);
  }

  class MyHelper extends AbstractDLL
  {

  }
}
