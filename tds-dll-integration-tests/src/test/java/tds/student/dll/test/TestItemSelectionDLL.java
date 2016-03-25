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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opentestsystem.shared.test.LifecycleManagingTestRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ContextConfiguration;

import tds.dll.api.IItemSelectionDLL;
import tds.dll.api.IStudentDLL;
import AIR.Common.DB.AbstractConnectionManager;
import AIR.Common.DB.AbstractDLL;
import AIR.Common.DB.DataBaseTable;
import AIR.Common.DB.DbComparator;
import AIR.Common.DB.SQLConnection;
import AIR.Common.DB.SqlParametersMaps;
import AIR.Common.DB.results.DbResultRecord;
import AIR.Common.DB.results.MultiDataResultSet;
import AIR.Common.DB.results.SingleDataResultSet;
import AIR.Common.Helpers._Ref;
import AIR.Common.Sql.AbstractDateUtilDll;
import TDS.Shared.Exceptions.ReturnStatusException;

/**
 * @author akulakov
 * 
 */
@RunWith (LifecycleManagingTestRunner.class)
@ContextConfiguration (locations = "/test-context.xml")
public class TestItemSelectionDLL
{
  // @Autowired
  // ICommonDLL iCommonDll = null;
  //
  @Autowired
  private AbstractConnectionManager _connectionManager = null;

  @Autowired
  private AbstractDateUtilDll       dateUtil           = null;

  private static final Logger       _logger            = LoggerFactory.getLogger (TestItemSelectionDLL.class);

  @Autowired
  private IItemSelectionDLL         _itemSelDLL         = null;
  // to compare test in TestStudentDLL for test_GetInitialAbility_FN()
  @Autowired
  private IStudentDLL         		_studentDLL         = null;

  @Autowired
  private MyISDLLHelper             _myDllHelper       = null;
  
  private SQLConnection             _connection                = null;
  private boolean                   _preexistingAutoCommitMode = true;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp () throws Exception {

    try {
      _connection = _connectionManager.getConnection ();
      _preexistingAutoCommitMode = _connection.getAutoCommit ();
      _connection.setAutoCommit (false);
    } catch (SQLException exp) {
      System.out.println ("SQLException occured in this test: " + exp);
      _logger.error (exp.getMessage ());
      throw exp;
    } catch (Exception e) {
      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
      throw e;
    }
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown () throws Exception {
    try {
      _connection.rollback ();
      _connection.setAutoCommit (_preexistingAutoCommitMode);
    }  catch (SQLException exp) {
      System.out.println ("SQLException occured in this test: " + exp);
      _logger.error (exp.getMessage ());
      throw exp;
    }catch (Exception e) {
      _logger.error (String.format ("Failed rollback: %s", e.getMessage ()));
      throw e;
    } finally {
      _connection.setAutoCommit (_preexistingAutoCommitMode);
    }
  }


  /**
   * @throws Exception
   */
  @Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void test_IsSimulation_FN () throws Exception {

    System.out.println ();
    _logger.info ("Test for IsSimulation_FN(connection, oppkey):");
    System.out.println ();

    UUID oppkey = UUID.fromString ("FE731D91-D591-43BB-85CC-B979785FF00D");

    try
    {

      boolean isSim = _itemSelDLL.IsSimulation_FN (_connection, oppkey);
      System.out.print ("Returned value isSimulation is " + isSim + " in this test");
      assertFalse ("isSimulation must be false in this test. Is isSimulation true?", isSim);
    } catch (Exception e)
    {
      System.out.println ("Exception in this test: " + e);
      _logger.error (e.getMessage ());
      throw e;
    } catch (AssertionError error)
    {
      System.out.println ("AssertionError in this test: " + error);
      _logger.error (error.getMessage ());
      throw error;
    }

  }

  @Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void test__AA_IsSegmentSatisfied_FN () throws Exception {

    System.out.println ();
    _logger.info ("Test for_AA_IsSegmentSatisfied_FN(connection, oppkey, segment):");
    System.out.println ();

    UUID oppkey = UUID.fromString ("AAA3217A-2A82-405C-8ED9-20F1AAF29ECE");
    try
    {
      boolean isSegmSatisfied = true;
      isSegmSatisfied = _itemSelDLL._AA_IsSegmentSatisfied_FN (_connection, oppkey, 1);

      System.out.print ("Returned value isSegmSatisfied is " + isSegmSatisfied + " in this test");
      assertFalse ("isSegmSatisfied must be false in this test. isSegmSatisfied is ", isSegmSatisfied);
    } catch (Exception e)
    {
      System.out.println ("Exception in this test: " + e);
      _logger.error (e.getMessage ());
      throw e;
    } catch (AssertionError error)
    {
      System.out.println ("AssertionError in this test: " + error);
      _logger.error (error.getMessage ());
      throw error;
    }
  }

  @Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void test_AA_GetNextItemCandidates_SP () throws Exception {

    System.out.println ();
    System.out.println ();
    _logger.info ("Test for AA_GetNextItemCandidates_SP(connection, oppkey): ");
    System.out.println ("Test for AA_GetNextItemCandidates_SP(connection, oppkey): ");

    UUID oppkey = (UUID.fromString ("0328EBCF-444A-4920-BA95-4BFBFCF8AE80"));

    try
    {
      SingleDataResultSet result;
      // before test
      DbResultRecord record;
      Integer segment = null; // = segment position
      List<Integer> segmnts = new ArrayList<Integer> (); // segment positions
                                                         // for oppkey which
                                                         // have
                                                         // isSatisfied = false
      // I will restore these values after test
      final String sqlQueryBefore = "select SegmentPosition "
          + " from testopportunitysegment "
          + " where _fk_TestOpportunity = ${oppkey} and IsSatisfied = 0 "
          + " order by SegmentPosition ";
      SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey);
      result = (_myDllHelper).executeStatement (_connection, sqlQueryBefore, parameters, false).getResultSets ().next ();
      Iterator<DbResultRecord> records = result.getCount () > 0 ? result.getRecords () : null;
      if (records != null)
      {
        while (records.hasNext ())
        {
          record = records.next ();
          if (record != null) {
            segment = record.<Integer> get ("SegmentPosition");
            segmnts.add (segment);
          }
        }
      }

      SingleDataResultSet sdrSet = _itemSelDLL.AA_GetNextItemCandidates_SP (_connection, oppkey);

      Iterator<DbResultRecord> recItr = sdrSet.getRecords ();
      int resultRecordNumber = 0;

      // There is only one record in this test and only one segmentPosision = 1
      while (recItr.hasNext ())
      {
        record = recItr.next ();
        dumpRecord (record);

        resultRecordNumber++;

         assertEquals (UUID.fromString ("0328ebcf-444a-4920-ba95-4bfbfcf8ae80"),record.<UUID> get ("oppkey"));
         assertEquals (UUID.fromString ("38893e8d-a5d2-4bf8-906e-3c2cbfbacc30"),record.<UUID> get ("session"));
         assertEquals ("(Minnesota_PT)MCA III Sampler-Science-9-Winter-2012-2013",record.<String> get ("segmentKey"));
         assertEquals ("MCA-Science-HS",record.<String> get ("segmentID"));
         assertEquals ("fixedform",record.<String> get ("algorithm"));
         assertEquals ("G-159-62",record.<String> get ("groupID"));
         assertEquals ("A",record.<String> get ("blockID"));
        assertTrue(record.<Integer> get ("segment") == 1);
        assertFalse (record.<Boolean> get ("isSim"));

      }

      printRecordsNumber (resultRecordNumber);
      assertTrue(resultRecordNumber == 1);

      int updateCnt = 0;
      for (Integer sgmnt : segmnts)
      {
        final String sqlQueryAfter = "update testopportunitysegment set IsSatisfied = 0 where _fk_TestOpportunity = ${oppkey} and SegmentPosition = ${SegmentPosition}";
        parameters.put ("SegmentPosition", sgmnt);
        updateCnt += (_myDllHelper).executeStatement (_connection, sqlQueryAfter, parameters, false).getUpdateCount ();
      }

      System.out.println ("Updated segment positions = " + updateCnt);
    } catch (Exception e)
    {
      System.out.println ("Exception in this test: " + e);
      _logger.error (e.getMessage ());
      throw e;
    } catch (AssertionError error)
    {
      System.out.println ("AssertionError in this test: " + error);
      _logger.error (error.getMessage ());
      throw error;
    }
  }

  @Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void test__AA_NextFixedformGroup_SP () throws Exception {

    System.out.println ();
    System.out.println ();
    _logger.info ("Test for _AA_NextFixedformGroup_SP(connection, oppkey, segmentKey, language, groupIDValue, blockIDValue): ");
    System.out.println ();

    UUID oppkey = (UUID.fromString ("0328EBCF-444A-4920-BA95-4BFBFCF8AE80"));
    String segmentKey = "(Minnesota_PT)MCA III Sampler-Science-9-Winter-2012-2013";
    String language = "ENU";

    try
    {
      _Ref<String> groupIDValue = new _Ref<String> ();
      _Ref<String> blockIDValue = new _Ref<String> ();

      _itemSelDLL._AA_NextFixedformGroup_SP (_connection, oppkey, segmentKey, language, groupIDValue, blockIDValue);

      System.out.println ();
      _logger.info (String.format ("groupID: %s", groupIDValue.get ()));
      _logger.info (String.format ("blockID: %s", blockIDValue.get ()));
      System.out.println ();
       assertEquals ("G-159-62",groupIDValue.get ());
       assertEquals ("A",blockIDValue.get ());

    } catch (Exception e)
    {
      System.out.println ("Exception in this test: " + e);
      _logger.error (e.getMessage ());
      throw e;
    } catch (AssertionError error)
    {
      System.out.println ("AssertionError in this test: " + error);
      _logger.error (error.getMessage ());
      throw error;
    }
  }

  @Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void test_AA_GetItemgroup_SP () throws Exception {

    System.out.println ();
    System.out.println ();
    _logger.info ("Test for AA_GetItemgroup_SP(connection, oppkey, segmentKey, groupID,  blockID, false, false): ");
    System.out.println ();

    UUID oppkey = (UUID.fromString ("13D734F8-A604-47AF-BF0C-55D08E7839FA"));
    String segmentKey = "(Minnesota)GRAD-FX-Reading-10-Fall-2011-2012";
    String groupID = "G-157-67";
    String blockID = "A";

    try
    {
      MultiDataResultSet multiDataResultSet = _itemSelDLL.AA_GetItemgroup_SP (_connection, oppkey, segmentKey, groupID, blockID, false, false);

      Iterator<SingleDataResultSet> setItr = multiDataResultSet.getResultSets ();

      SingleDataResultSet groupTable = null;
      SingleDataResultSet itemTable = null;

      // if multiDataResultSet has 2 SinleDataResultSets: first -> groups;
      // second -> items
      // if has only one: it is items
      if (setItr.hasNext ())
      {
        if (multiDataResultSet.getUpdateCount () > 1)
        {
          groupTable = setItr.next ();
          itemTable = setItr.next ();
        }
        else
        {
          itemTable = setItr.next ();
        }
      }
      Integer numRequired = null;
      Integer maxItems = null;
      DbResultRecord record;
      String nGroupID = null;

      Iterator<DbResultRecord> recItr = groupTable.getRecords ();
      record = groupTable.getCount () > 0 ? groupTable.getRecords ().next () : null;
      if (record != null) {
        nGroupID = record.<String> get ("groupID");
        numRequired = record.<Integer> get ("numRequired");
        maxItems = record.<Integer> get ("maxItems");
      }
      try {

        System.out.println ();
        _logger.info (String.format ("nGroupID: %s", nGroupID));
        _logger.info (String.format ("itemsRequired: %s", numRequired));
        _logger.info (String.format ("maxReqItems: %s", maxItems));
        System.out.println ();
        assertTrue(DbComparator.isEqual (groupID, nGroupID));
         assertEquals ("G-159-67", nGroupID);
        assertTrue(numRequired == -1);
        assertTrue(maxItems == 0);

      } catch (Exception e)
      {
        _logger.info ("Cannot create ItemGroup instance in the test of the AA_GetItemgroup_SP ()" + e.getLocalizedMessage ());
      }
      //
      recItr = itemTable.getRecords ();
      String itemID = null;
      String strand = null;
      String IRT_Model = null;
      String bVector = null;
      int formPosition = 0;
      Float irtA = null;
      Float irtC = null;
      Double IRT_b = null;
      Boolean isFieldTest = null;
      Boolean isRequired = null;
      //
      int items = 0;
      while (recItr.hasNext ())
      {
        record = recItr.next ();
        if (record != null) {
          itemID = record.<String> get ("itemID");
          nGroupID = record.<String> get ("groupID");
          strand = record.<String> get ("strand");
          bVector = record.<String> get ("bVector");
          formPosition = record.<Integer> get ("formPosition");
          IRT_b = Double.parseDouble (record.<String> get ("IRT_b"));
          irtA = record.<Float> get ("IRT_a");
          irtC = record.<Float> get ("IRT_c");
          IRT_Model = record.<String> get ("IRT_Model");
          isFieldTest = record.<Boolean> get ("isFieldTest");
          isRequired = record.<Boolean> get ("isRequired");
          //
          System.out.println ();
          _logger.info (String.format ("groupID: %s", nGroupID));
          _logger.info (String.format ("itemID: %s", itemID));
          _logger.info (String.format ("strand: %s", strand));
          _logger.info (String.format ("IRT Model: %s", IRT_Model));
          _logger.info (String.format ("bVector: %s", bVector));
          _logger.info (String.format ("formPosition: %d", formPosition));
          _logger.info (String.format ("IRT_b: %s", IRT_b));
          _logger.info (String.format ("IRT_a: %f", irtA));
          _logger.info (String.format ("IRT_c: %f", irtC));
          _logger.info (String.format ("isFieldTest: %b", isFieldTest));
          _logger.info (String.format ("isRequired: %b", isRequired));
          System.out.println ();
          items++;
        }

      }

      String res = "There " + ((items == 1) ? " was " : " were ") + items + " item" + ((items == 1) ? " " : "s") + "!";
      _logger.info (res);
      System.out.println ();

      // assertions for last item:
       assertEquals ("G-157-67",nGroupID);
       assertEquals ("157-9268",itemID);
       assertEquals ("Minnesota-ELA-I",strand);
       assertEquals ("-1.32936",bVector);
      assertTrue(formPosition == 8);
      assertEquals (-1.32936, IRT_b, 2.0e-6);
      assertEquals (0.559690, irtA, 2.0e-6);
      assertEquals (0.205390, irtC, 2.0e-6);
      assertFalse (isFieldTest);
      assertFalse (isRequired);

    } catch (Exception e)
    {
      System.out.println ("Exception in test_AA_GetItemgroup_SP test: " + e);
      _logger.error (e.getMessage ());
      throw e;
    } catch (AssertionError error)
    {
      System.out.println ("AssertionError in test_AA_GetItemgroup_SP test: " + error);
      _logger.error (error.getMessage ());
      throw error;
    }
  }

  // Check in MS SQL DBs: There is not not empty data
  @Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void test__AA_GetCustomItemgroup_FN () throws Exception {
    System.out.println ();
    System.out.println ();
    _logger.info ("Test for _AA_GetCustomItemgroup_FN(connection, clientname, oppkey, segmentKey, testID, false, groupID,  blockID): ");
    System.out.println ();

//    oppkey: 51bcbeea-e7c8-4886-ab6d-49990908e5a4
//    algorithm: fieldtest
//    segmentKey: (Delaware)DCAS-Reading-10-Fall-2012-2013
//    segmentID: DCAS-Reading-10
//    segmentPosition: 1
//    groupID: G-148-376
//    blockID: B
//    session: fa1dca3a-20ac-4568-90c9-cf2eebce5cac
//    isSimulation: false

    String clientname = "Delaware";
    UUID oppkey = (UUID.fromString ("51bcbeea-e7c8-4886-ab6d-49990908e5a4"));
    String segmentKey = "(Delaware)DCAS-Reading-10-Fall-2012-2013";
    String testID = "DCAS-Reading-10";
    String groupID = "G-148-376";
    String blockID = "B";
    
    /* Output from MS SQL:
itemkey	_fk_Item	groupKey	groupID	blockID	strand	bVector	IRT_model	IRT_a	IRT_b	IRT_c	itemPosition	isRequired	isFieldTest	isActive
148-14993	148-14993	G-148-376_B	G-148-376	B	Delaware-ELA-4	-9999	IRT3PL	1	-9999	0	6	0	1	1
148-14995	148-14995	G-148-376_B	G-148-376	B	Delaware-ELA-2	-9999	IRT3PL	1	-9999	0	7	0	1	1
148-15434	148-15434	G-148-376_B	G-148-376	B	Delaware-ELA-2	-9999	IRT3PL	1	-9999	0	8	0	1	1
148-15622	148-15622	G-148-376_B	G-148-376	B	Delaware-ELA-2	-9999	IRT3PL	1	-9999	0	9	0	1	1
     */
    
//    String clientname = "Delaware";
//    UUID oppkey = (UUID.fromString ("128A57D2-9AB6-471D-9B15-C9A90B16A708"));
//    String segmentKey = "(Delaware)DCAS-Alt1-PAPER-Mathematics-10-Fall-2012-2013";
//    String testID = "DCAS-Reading-10";
//    String groupID = "G-148-376";
//    String blockID = "B";
    
//    String clientname = "Minnesota";
//    UUID oppkey = (UUID.fromString ("13D734F8-A604-47AF-BF0C-55D08E7839FA"));
//    String segmentKey = "(Minnesota)GRAD-FX-Reading-10-Fall-2011-2012";
//    String testID = "GRAD-Reading-10";
//    String groupID = "G-157-67";
//    String blockID = "A";

    try
    {
      DataBaseTable table = _itemSelDLL._AA_GetCustomItemgroup_FN (_connection, clientname, oppkey, segmentKey, testID, true, groupID, blockID);

      final String SQL_QUERY = "select * from ${tblName}";
      Map<String, String> tableName = new HashMap<String, String> ();
      tableName.put ("tblName", table.getTableName ());
      String finalQuery = _myDllHelper.fixDataBaseNames (SQL_QUERY, tableName);
      SqlParametersMaps parameters = new SqlParametersMaps ();
      SingleDataResultSet result = _myDllHelper.executeStatement (_connection, finalQuery, parameters, false).getResultSets ().next ();
      Iterator<DbResultRecord> records = result.getRecords ();
      boolean isFirst = true;
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        dumpRecord (record);
        if (isFirst)
        {
        	assertEquals ("G-148-376", record.<String> get ("groupid"));
        	assertEquals ("B", record.<String> get ("blockid"));
        	assertEquals ("G-148-376_B", record.<String> get ("groupkey"));
        	assertEquals ("IRT3PL",record.<String> get ("irt_model"));
        	assertEquals ("Delaware-ELA-4",record.<String> get ("strand"));
        	assertEquals ("148-14993",record.<String> get ("_fk_item"));
        	assertEquals ("148-14993",record.<String> get ("itemkey"));
        	assertEquals ("-9999",record.<String> get ("bvector"));
          isFirst = false;
        }
     }
      _logger.info ("Total number of records = " + result.getCount ());
      System.out.println ("Total number of records = " + result.getCount ()); // debug
      assertTrue(result.getCount () == 4);
    } catch (Exception e)
    {
      System.out.println ("Exception in test__AA_GetCustomItemgroup_FN test: " + e);
      _logger.error (e.getMessage ());
      throw e;
    } catch (AssertionError error)
    {
      System.out.println ("AssertionError in test__AA_GetCustomItemgroup_FN test: " + error);
      _logger.error (error.getMessage ());
      throw error;
    }
  }

  @Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void test__AA_SIM_GetCustomItemgroup_FN () throws Exception {
    System.out.println ();
    _logger.info ("Test for _AA_SIM_GetCustomItemgroup_FN(connection, clientname, oppkey, segmentKey, testID, false, groupID,  blockID): ");
    System.out.println ();

    String clientname = "Delaware";
    UUID oppkey = (UUID.fromString ("51bcbeea-e7c8-4886-ab6d-49990908e5a4"));
    UUID session = (UUID.fromString ("fa1dca3a-20ac-4568-90c9-cf2eebce5cac"));
    String segmentKey = "(Delaware)DCAS-Reading-10-Fall-2012-2013";
    String testID = "DCAS-Reading-10";
    String groupID = "G-148-376";
    String blockID = "B";
    try
    {
      DataBaseTable table = _itemSelDLL._AA_SIM_GetCustomItemgroup_FN (_connection, clientname, 
          oppkey, segmentKey, testID, true, session, groupID, blockID);

      final String SQL_QUERY = "select * from ${tblName}";
      Map<String, String> tableName = new HashMap<String, String> ();
      tableName.put ("tblName", table.getTableName ());
      String finalQuery = _myDllHelper.fixDataBaseNames (SQL_QUERY, tableName);
      SqlParametersMaps parameters = new SqlParametersMaps ();
      SingleDataResultSet result = _myDllHelper.executeStatement (_connection, finalQuery, parameters, false).getResultSets ().next ();
      Iterator<DbResultRecord> records = result.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        dumpRecord (record);
      }
      _logger.info ("Total number of records = " + result.getCount ());
      System.out.println ("Total number of records = " + result.getCount ()); // debug
      assertTrue(result.getCount () == 24);
    } catch (Exception e)
    {
      System.out.println ("Exception in test__AA_SIM_GetCustomItemgroup_FN test: " + e); // debug
      _logger.error (e.getMessage ());
      throw e;
    } catch (AssertionError error)
    {
      System.out.println ("AssertionError in test__AA_GetCustomItemgroup_FN test: " + error); // debug
      _logger.error (error.getMessage ());
      throw error;
    }
  }

	@Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
	public void test_AA_GetSegment_SP() throws Exception {
		System.out.println();
		_logger.info("Test for_AA_GetSegment_SP(connection, segmentKey):");
		System.out.println("Test for_AA_GetSegment_SP(connection, segmentKey):");

		String segmentKey = "(Delaware)DCAS-Reading-10-Fall-2012-2013";
		try {
			MultiDataResultSet result = _itemSelDLL.AA_GetSegment_SP(_connection, segmentKey);
			Iterator<SingleDataResultSet> resultSetIterator = result.getResultSets();
			int resultSetNumber = 0;
			int resultRecordsNumber = 0;
			while (resultSetIterator.hasNext()) {
				SingleDataResultSet resultSet = resultSetIterator.next();

				Iterator<DbResultRecord> records = resultSet.getRecords();

				while (records.hasNext()) {
//					DbResultRecord record = records.next();
//					dumpRecord(record);
					resultRecordsNumber++;
				}
				resultSetNumber++;
			}
			System.out.println("Tables number is " + resultSetNumber);// debug
			System.out.println("Records number is " + resultRecordsNumber);// debug
		} catch (Exception e) {
			System.out.println("Exception in test_AA_GetSegment_SP test: " + e);
			_logger.error(e.getMessage());
			throw e;
		} catch (AssertionError error) {
			System.out.println("AssertionError in test_AA_GetSegment_SP test: " + error);
			_logger.error(error.getMessage());
			throw error;
		}
	}

	@Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
	public void test_AA_SIM_GetSegment_SP() throws Exception {
		System.out.println();
		_logger.info("Test for _AA_SIM_GetSegment_SP(connection, segmentKey):");
		System.out.println("Test for _AA_SIM_GetSegment_SP(connection, segmentKey):");


	    //UUID oppkey = (UUID.fromString ("51bcbeea-e7c8-4886-ab6d-49990908e5a4"));
	    UUID session = (UUID.fromString ("fa1dca3a-20ac-4568-90c9-cf2eebce5cac"));
	    String segmentKey = "(Delaware)DCAS-Reading-10-Fall-2012-2013";

		try {
			MultiDataResultSet result = _itemSelDLL.AA_SIM_GetSegment_SP(_connection, session, segmentKey);
			Iterator<SingleDataResultSet> resultSetIterator = result.getResultSets();
			int resultSetNumber = 0;
			int resultRecordsNumber = 0;
			while (resultSetIterator.hasNext()) {
				SingleDataResultSet resultSet = resultSetIterator.next();

				Iterator<DbResultRecord> records = resultSet.getRecords();

				while (records.hasNext()) {
					DbResultRecord record = records.next();
					dumpRecord(record);
					resultRecordsNumber++;
				}
				resultSetNumber++;
			}
			System.out.println("Tables number is " + resultSetNumber);// debug
			System.out.println("Records number is " + resultRecordsNumber);// debug
		} catch (Exception e) {
			System.out.println("Exception in test_AA_GetSegment_SP test: " + e);
			_logger.error(e.getMessage());
			throw e;
		} catch (AssertionError error) {
			System.out.println("AssertionError in test_AA_GetSegment_SP test: " + error);
			_logger.error(error.getMessage());
			throw error;
		}
	}

  @Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void test__AA_IsSegmentSatisfied_FN4AA () throws Exception {

    System.out.println ();
    _logger.info ("Test for_AA_IsSegmentSatisfied_FN(connection, oppkey, segment):");
    System.out.println ();

    UUID oppkey = UUID.fromString ("73C7442E-9940-42DA-A73D-04E15E5C91B2");
    try
    {
      boolean isSegmSatisfied = _itemSelDLL._AA_IsSegmentSatisfied_FN (_connection, oppkey, 1);
      System.out.print ("Returned value isSegmSatisfied is " + isSegmSatisfied + " in test__AA_IsSegmentSatisfied_FN4AA test");
      assertFalse ("isSegmSatisfied must be false in test__AA_IsSegmentSatisfied_FN4AA test. isSegmSatisfied is ", isSegmSatisfied);
    } catch (Exception e)
    {
      System.out.println ("Exception in test__AA_IsSegmentSatisfied_FN4AA test: " + e);
      _logger.error (e.getMessage ());
      throw e;
    } catch (AssertionError error)
    {
      System.out.println ("AssertionError in test__AA_IsSegmentSatisfied_FN4AA test: " + error);
      _logger.error (error.getMessage ());
      throw error;
    }
  }

  @Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void test_AA_GetNextItemCandidates_SP4FT () throws Exception {

    String stOppkey = "51BCBEEA-E7C8-4886-AB6D-49990908E5A4";
    // String stOppkey = "8F9972ED-C7C0-4910-A517-0E16D8D50FDE";
    UUID oppkey = (UUID.fromString (stOppkey));
    System.out.println ();
    System.out.println ();
    _logger.info ("Test for AA_GetNextItemCandidates_SP(connection, oppkey) for FieldTest Algorithm: ");
    System.out.println ();
    _logger.info (String.format ("Oppkey = %s", stOppkey));
    System.out.println ();

    SingleDataResultSet result;
    //
    // before test
    DbResultRecord record;
    Integer segment = null; // = segment position
    Map<Integer, Boolean> segmnts = new HashMap<Integer, Boolean> (); // segment
                                                                      // positions
                                                                      // ->
                                                                      // isSatisfied
    SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey);
    // for oppkey which
    // have
    // isSatisfied = false
    try
    {
      boolean isSatisfied = false;
      // I will restore these values after test
      final String sqlQueryBefore = "select SegmentPosition, isSatisfied "
          + " from testopportunitysegment "
          + " where _fk_TestOpportunity = ${oppkey} and dbo._aa_issegmentsatisfied(_fk_TestOpportunity, SegmentPosition) = 0 "
          + " order by SegmentPosition ";

      result = _myDllHelper.executeStatement (_connection, sqlQueryBefore, parameters, false).getResultSets ().next ();
      Iterator<DbResultRecord> records = result.getCount () > 0 ? result.getRecords () : null;
      if (records != null)
      {
        while (records.hasNext ())
        {
          record = records.next ();
          if (record != null) {
            segment = record.<Integer> get ("SegmentPosition");
            isSatisfied = record.<Boolean> get ("isSatisfied");
            segmnts.put (segment, isSatisfied);
          }
        }
      }
    } catch (Exception e)
    {
      System.out.println ("Exception in test_AA_GetNextItemCandidates_SP4FT () test: ");
      System.out.println ();
      _logger.error (e.getMessage ());
      throw e;
    } catch (AssertionError error)
    {
      System.out.println ("AssertionError in test_AA_GetNextItemCandidates_SP4FT () test: ");
      System.out.println ();
      _logger.error (error.getMessage ());
      throw error;
    }

    try
    {
      SingleDataResultSet sdrSet = _itemSelDLL.AA_GetNextItemCandidates_SP (_connection, oppkey);

      Iterator<DbResultRecord> recItr = sdrSet.getRecords ();
      int resultRecordNumber = 0;

      // There is only one record in test_AA_GetNextItemCandidates_SP4FT test
      // and only one segmentPosision = 1
      while (recItr.hasNext ())
      {
        record = recItr.next ();
        dumpRecord (record);

        resultRecordNumber++;

         assertEquals ("51bcbeea-e7c8-4886-ab6d-49990908e5a4",record.<String> get ("oppkey"));
         assertEquals ("fa1dca3a-20ac-4568-90c9-cf2eebce5cac",record.<String> get ("session"));
         assertEquals ("(Delaware)DCAS-Reading-10-Fall-2012-2013",record.<String> get ("segmentKey"));
         assertEquals ("DCAS-Reading-10",record.<String> get ("segmentID"));
         assertEquals ("fieldtest",record.<String> get ("algorithm"));
         assertEquals ("B",record.<String> get ("blockid"));
         assertEquals ("G-148-376",record.<String> get ("groupid"));
        assertTrue(record.<Integer> get ("segment") == 1);
        assertFalse (record.<Boolean> get ("isSim"));
      }

      // printRecordsNumber (resultRecordNumber);
      assertTrue(resultRecordNumber == 1);

    } catch (Exception e)
    {
      System.out.println ("Exception in test_AA_GetNextItemCandidates_SP4FT () test: ");
      System.out.println ();
      _logger.error (e.getMessage ());
      throw e;
    } catch (AssertionError error)
    {
      System.out.println ("AssertionError in test_AA_GetNextItemCandidates_SP4FT () test: ");
      System.out.println ();
      _logger.error (error.getMessage ());
      throw error;
    } finally
    {
      int updateCnt = 0;
      for (Integer sgmnt : segmnts.keySet ())
      {
        final String sqlQueryAfter = "update testopportunitysegment set IsSatisfied = ${isSatisfied} where _fk_TestOpportunity = ${oppkey} and SegmentPosition = ${SegmentPosition}";
        parameters.put ("SegmentPosition", sgmnt).put ("isSatisfied", segmnts.get (sgmnt));
        updateCnt += _myDllHelper.executeStatement (_connection, sqlQueryAfter, parameters, false).getUpdateCount ();
      }
      System.out.println ("Updated segment positions = " + updateCnt);
    }
  }

  @Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void test_AA_GetNextItemCandidates_SP4AA () throws Exception {

    String stOppkey = "73C7442E-9940-42DA-A73D-04E15E5C91B2";
    UUID oppkey = (UUID.fromString (stOppkey));
    System.out.println ();
    System.out.println ();
    _logger.info ("Test for AA_GetNextItemCandidates_SP(connection, oppkey) for Adaptive Algorithm: ");
    System.out.println ();
    _logger.info (String.format ("Oppkey = %s", stOppkey));
    System.out.println ();

    try
    {
      SingleDataResultSet result;
      //
      // before test
      DbResultRecord record;
      Integer segment = null; // = segment position
      Map<Integer, Boolean> segmnts = new HashMap<Integer, Boolean> (); // segment
                                                                        // positions
                                                                        // ->
                                                                        // isSatisfied
      // for oppkey which
      // have
      // isSatisfied = false
      boolean isSatisfied = false;
      // I will restore these values after test
      final String sqlQueryBefore = "select SegmentPosition, isSatisfied "
          + " from testopportunitysegment "
          + " where _fk_TestOpportunity = ${oppkey} and dbo._AA_ISSEGMENTSATISFIED(_fk_TestOpportunity, SegmentPosition) = 0 "
          + " order by SegmentPosition ";
      SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey);
      result = _myDllHelper.executeStatement (_connection, sqlQueryBefore, parameters, false).getResultSets ().next ();
      Iterator<DbResultRecord> records = result.getCount () > 0 ? result.getRecords () : null;
      if (records != null)
      {
        while (records.hasNext ())
        {
          record = records.next ();
          if (record != null) {
            segment = record.<Integer> get ("SegmentPosition");
            isSatisfied = record.<Boolean> get ("isSatisfied");
            segmnts.put (segment, isSatisfied);
          }
        }
      }

      SingleDataResultSet sdrSet = _itemSelDLL.AA_GetNextItemCandidates_SP (_connection, oppkey);

      Iterator<DbResultRecord> recItr = sdrSet.getRecords ();
      int resultRecordNumber = 0;

      // There is only one record in test_AA_GetNextItemCandidates_SP4AA test
      // and only one segmentPosision = 1
      while (recItr.hasNext ())
      {
        record = recItr.next ();
        dumpRecord (record);

        resultRecordNumber++;

         assertEquals ("73C7442E-9940-42DA-A73D-04E15E5C91B2",record.<String> get ("oppkey"));
         assertEquals ("38893e8d-a5d2-4bf8-906e-3c2cbfbacc30",record.<String> get ("session"));
         assertEquals ("(Minnesota)MCA III MG3O-S1-Mathematics-3-Fall-2012-2013",record.<String> get ("segmentKey"));
         assertEquals ("MCA III MG3O-S1-Mathematics-3",record.<String> get ("segmentID"));
         assertEquals ("adaptive",record.<String> get ("algorithm"));
        assertTrue(record.<Integer> get ("segment") == 1);
        assertFalse (record.<Boolean> get ("isSim"));
      }

      // printRecordsNumber (resultRecordNumber);
      assertTrue(resultRecordNumber == 1);

      int updateCnt = 0;
      for (Integer sgmnt : segmnts.keySet ())
      {
        final String sqlQueryAfter = "update testopportunitysegment set IsSatisfied = ${isSatisfied} where _fk_TestOpportunity = ${oppkey} and SegmentPosition = ${SegmentPosition}";
        parameters.put ("SegmentPosition", sgmnt).put ("isSatisfied", segmnts.get (sgmnt));
        updateCnt += _myDllHelper.executeStatement (_connection, sqlQueryAfter, parameters, false).getUpdateCount ();
      }

      System.out.println ("Updated segment positions = " + updateCnt);
    } catch (Exception e)
    {
      System.out.println ("Exception in test_AA_GetNextItemCandidates_SP4AA () test: ");
      System.out.println ();
      _logger.error (e.getMessage ());
      throw e;
    } catch (AssertionError error)
    {
      System.out.println ("AssertionError in test_AA_GetNextItemCandidates_SP4AA () test: ");
      System.out.println ();
      _logger.error (error.getMessage ());
      throw error;
    }
  }
  
	@Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
	public void test_FN_GetInitialAbility_FN() throws Exception {
		System.out.println();
		_logger.info("Test for FN_GetInitialAbility_FN(connection, oppkey):  ");
		System.out.println("Test for FN_GetInitialAbility_FN(connection, oppkey):  ");// debug

		try {
			String stOppkey = "128A57D2-9AB6-471D-9B15-C9A90B16A708";
			UUID oppkey = (UUID.fromString(stOppkey));

			Float initialAbility = _itemSelDLL.FN_GetInitialAbility_FN(_connection, oppkey);
			System.out.println("initialAbility = " + initialAbility);
			assertEquals (-6.151711, initialAbility, 2.0e-6);

		} catch (Exception e) {
			System.out.println("Exception in test_FN_GetInitialAbility_FN () test: ");
			System.out.println(e.getMessage()); // debug
			_logger.error(e.getMessage());
			throw e;
		}
	}

	@Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
	public void test__AA_GetInitialAbility_FN() throws Exception {
		System.out.println();
		_logger.info("Test for _AA_GetInitialAbility_FN(connection, oppkey):  ");
		System.out.println("Test for _AA_GetInitialAbility_FN(connection, oppkey):  ");// debug

		try {
			String stOppkey = "128A57D2-9AB6-471D-9B15-C9A90B16A708";
			UUID oppkey = (UUID.fromString(stOppkey));

			Float initialAbility = _itemSelDLL._AA_GetInitialAbility_FN(_connection, oppkey);
			System.out.println("initialAbility = " + initialAbility);
			assertEquals (-9999.0, initialAbility, 2.0e-6);

		} catch (Exception e) {
			System.out.println("Exception in test__AA_GetInitialAbility_FN () test: ");
			System.out.println(e.getMessage()); // debug
			_logger.error(e.getMessage());
			throw e;
		}
	}

	@Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
	public void test10() throws Exception // uses test2__AA_GetInitialAbility_FN(UUID oppkey)
	{
	    List<UUID> oppkeys = new ArrayList<UUID> (Arrays.asList (//
	        UUID.fromString ("C3895FF7-F5D6-4AEE-94F2-B30C40411536"),
	        UUID.fromString ("02E15D9F-6D1D-4445-AB28-0208C9626A5B"),
	        UUID.fromString ("02E15D9F-6D1D-4445-AB28-0208C9626A5B"),
	        UUID.fromString ("DD1BE99F-AD1A-400C-9176-ED88F60C562A"),
	        UUID.fromString ("50CB1092-3C90-4756-93D4-E7540766F1F6"),
	        UUID.fromString ("46CFBCCA-6F92-486F-B574-E76804EB6BA5"),
	        UUID.fromString ("8BDDB320-D655-4CD6-9B08-EA8E43500D7F"),
	        UUID.fromString ("2746DFAF-1BEB-4239-8C88-E5A5318C3280"),
	        UUID.fromString ("E7C8251D-06EA-40CF-8D40-0905519A9BC1"),
	        UUID.fromString ("2071C3A4-5C2B-4F72-8B06-B4975AF42503")
	        ));
	    try
	    {
	      for (UUID oppkey : oppkeys)
	      {
	    	  test2__AA_GetInitialAbility_FN(oppkey); 
	      }
		} catch (Exception e) {
			System.out.println("Exception in test__AA_GetInitialAbility_FN () test: ");
			System.out.println(e.getMessage()); // debug
			_logger.error(e.getMessage());
			throw e;
		}

	}
	
	private void test2__AA_GetInitialAbility_FN(UUID oppkey) throws Exception {
		System.out.println();
		_logger.info("Test for _AA_GetInitialAbility_FN(connection, oppkey):  ");
		System.out.println("Test for _AA_GetInitialAbility_FN(connection, oppkey):  ");// debug

		try {
			Float initialAbility = _itemSelDLL._AA_GetInitialAbility_FN(_connection, oppkey);
			System.out.println("initialAbility = " + initialAbility);

		} catch (Exception e) {
			System.out.println("Exception in test__AA_GetInitialAbility_FN () test: ");
			System.out.println(e.getMessage()); // debug
			_logger.error(e.getMessage());
			throw e;
		}
	}

	@Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
	public void test_GetInitialAbility_FN() throws Exception {

    String testkey = "(Delaware)DCAS-Alt1-PAPER-Mathematics-10-Fall-2012-2013";
	System.out.println("Test for GetInitialAbility_FN(connection, testkey):  ");// debug
    _logger.info ("Test for GetInitialAbility_FN(connection, testkey):  ");
    
    try
    {
      Float initialAbility = _studentDLL.GetInitialAbility_FN (_connection, testkey);
      System.out.println("initialAbility = " + initialAbility);
      assertEquals (-6.151711, initialAbility, 2.0e-6);
     
    } catch(Exception e)
    {
      System.out.println ("Exception in test_GetInitialAbility_FN () test: ");
      System.out.println(e.getMessage()); // debug
      _logger.error (e.getMessage ());
      throw e;
    }
  
  }

  /**
   * @throws Exception
   *           AF_GetItempool_FN(SQLConnection, UUID, String, String, Boolean,
   *           String, String)
   * 
   *           call from AF_GetItempool_FN(...)
   * 
   */
  @Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void test_AF_GetItempool_FN () throws Exception {

    String stOppkey = "51BCBEEA-E7C8-4886-AB6D-49990908E5A4";
    String segmentKey = "(Delaware)DCAS-Reading-10-Fall-2012-2013";
    String segmentID = "DCAS-Reading-10";
    Boolean fieldTest = true;
    String groupID = "G-148-376";
    String blockID = "B";

    UUID oppkey = (UUID.fromString (stOppkey));

    System.out.println ();
    _logger.info ("Test for AF_GetItempool_FN(connection, oppkey, segmentKey, segmentID, fieldTest, groupID, blockID) for FieldTest Algorithm: ");
    System.out.println ();
    _logger.info (String.format ("oppkey = %s ", stOppkey));
    _logger.info (String.format ("segmentKey = %s ", segmentKey));
    _logger.info (String.format ("segmentID = %s ", segmentID));
    _logger.info (String.format ("fieldTest = %b ", fieldTest));
    _logger.info (String.format ("groupID = %s ", groupID));
    _logger.info (String.format ("blockID = %s ", blockID));
    System.out.println ();

    DbResultRecord record;
    try
    {
    	DataBaseTable tbl = _itemSelDLL.AF_GetItempool_FN (_connection, oppkey, 
    		  segmentKey, segmentID, fieldTest, groupID, blockID);
    	
    	final String SQL_QUERY = "select * from ${tblName}";
        Map<String, String> par = new HashMap<String, String> ();
        par.put ("tblName", tbl.getTableName ());

        SingleDataResultSet result = _myDllHelper.executeStatement (_connection, _myDllHelper.fixDataBaseNames (SQL_QUERY, par), null, false).getResultSets ().next ();
        Iterator<DbResultRecord> records = result.getRecords ();
        int resultRecordNumber = 0;
        boolean isFirst = true;

        assertTrue(result.getCount () == 4); 
        while (records.hasNext ()) {
          record = records.next ();
          dumpRecord (record);
          if (isFirst)
          {
             assertEquals ("G-148-376",record.<String> get ("groupid"));
             assertEquals ("B",record.<String> get ("blockid"));
             assertEquals ("G-148-376_B",record.<String> get ("groupkey"));
             assertEquals ("IRT3PL",record.<String> get ("irt_model"));
             assertEquals ("Delaware-ELA-4",record.<String> get ("strand"));
             assertEquals ("148-14993",record.<String> get ("_fk_item"));
             assertEquals ("148-14993",record.<String> get ("itemkey"));
             assertEquals ("-9999",record.<String> get ("bvector"));
            assertTrue(record.<Integer> get ("propsin") == 1);
            isFirst = false;
          }
          resultRecordNumber++;
        }
        System.out.println ("Records number is " + result.getCount () + "; " +  resultRecordNumber);
        assertTrue(resultRecordNumber == 4);

    } catch (Exception e)
    {
      System.out.println ("Exception in test_AF_GetItempool_FN () test: ");
      System.out.println (e.getMessage ());
      _logger.error (e.getMessage ());
      throw e;
    } catch (AssertionError error)
    {
      System.out.println ("AssertionError in test_AF_GetItempool_FN () test: ");
      System.out.println (error.getMessage ());
      _logger.error (error.getMessage ());
      throw error;
    }
  }

  // _AA_GetCustomItemgroup_FN
  //==============================================================
  private void dumpRecord (DbResultRecord record) throws ReturnStatusException {
    System.out.println ();
    String columnName = null;
    Iterator<String> itNames = record.getColumnNames ();
    while (itNames.hasNext ())
    {
      columnName = itNames.next ();
      Object value = record.get (record.getColumnToIndex (columnName).get ());
      System.out.println(String.format ("%s: %s", columnName, value.toString ())); // debug
      _logger.info (String.format ("%s: %s", columnName, value.toString ()));
    }
    System.out.println ();
  }

  private void printRecordsNumber (int recs)
  {
    String res = "There " + ((recs == 1) ? " was " : " were ") + recs + " record" + ((recs == 1) ? " " : "s") + "!";
    System.out.println (res);
    System.out.println ();
  }

  @Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void test_easyTest1 () throws Exception, IOException
  {
    String segmentKey = "131-512";
    byte[] bbs = TOBytesArray.toBytesArrayObjectStreams ("009326538283444B93A2B71800E8AFD6");
    UUID oppkey = UUID.nameUUIDFromBytes (bbs);

    Integer segmentPosition = null;
    String formKey = null;

    try
    {
      SingleDataResultSet result;

      final String SQL_QUERY = "select formKey, segmentPosition "
          + "from testopportunitysegment where _fk_TestOpportunity = ${oppkey} and _efk_Segment = ${segmentKey}";

      SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey).put ("segmentKey", segmentKey);
      result = _myDllHelper.executeStatement (_connection, _myDllHelper.fixDataBaseNames (SQL_QUERY), parameters, false).getResultSets ().next ();
      DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        segmentPosition = record.<Integer> get ("segmentPosition");
        formKey = record.<String> get ("formKey");
      }
      System.out.println ("segmentPosition = " + segmentPosition + "; formKey = " + formKey);
    } catch (Exception e) {
      _logger.error (e.getMessage ());
    }
  }

  @Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void test_easyTest2 () throws Exception, IOException
  {
    byte[] bbs = TOBytesArray.toBytesArrayObjectStreams ("009326538283444B93A2B71800E8AFD6");
    UUID oppkey = UUID.nameUUIDFromBytes (bbs);
    Integer segmentPosition = 1;

    Integer firstPosition = null;
    Integer lastPosition = null;

    final String SQL_QUERY2 = "select min(position) as firstPosition, max(position) as lastPosition "
        + " from testeeresponse where _fk_TestOpportunity = ${oppkey} and segment = ${segmentPosition}";

    try
    {
      SingleDataResultSet result;

      SqlParametersMaps parameters2 = new SqlParametersMaps ().put ("oppkey", oppkey).put ("segmentPosition", segmentPosition);
      result = _myDllHelper.executeStatement (_connection, SQL_QUERY2, parameters2, false).getResultSets ().next ();
      DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        firstPosition = record.<Integer> get ("firstPosition");
        lastPosition = record.<Integer> get ("lastPosition");
        _logger.info ("first position = " + firstPosition + "; last position = " + lastPosition);
      }
    } catch (Exception e) {
      _logger.error (e.getMessage ());
    }
  }

  @Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void test_easyTest3 () throws Exception, IOException
  {
    try
    {
      final String SQL_QUERY = "select concat(AccType, ': ', AccValue) as avalue from testeeaccommodations where _fk_TestOpportunity = 0x9cc6b36b6a38436d9edb00010d25f2a7 and segment = 0 order by AccType asc";

      SqlParametersMaps parameters = null;
      @SuppressWarnings ("unused")
      SingleDataResultSet result = _myDllHelper.executeStatement (_connection, _myDllHelper.fixDataBaseNames (SQL_QUERY), parameters, false).getResultSets ().next ();

    } catch (Exception e) {
      _logger.error (e.getMessage ());
    }
  }

  @Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void test_easyTest4 () throws Exception, IOException
  {
    try
    {
      final String SQL_QUERY = "(select distinct M.clientname, M.TestKey, ContextType, M.testID as Context, TestMode, IfType, IfValue, ThenType, ThenValue, IsDefault   from tdscore_test_configs.client_tooldependencies TD, tdscore_test_configs.client_testmode M   where M.testkey = '(Oregon)Oregon-Student Help-NA-Winter-2011-2012' and TD.ContextType = 'TEST' and TD.Context = M.TestID and TD.Clientname = M.clientname ) union all  (select distinct M.clientname, M.TestKey, ContextType, M.TestID as Context, TestMode, IfType, IfValue, ThenType, ThenValue, IsDefault from tdscore_test_configs.client_tooldependencies TD, tdscore_test_configs.client_testmode M   where M.Testkey = '(Oregon)Oregon-Student Help-NA-Winter-2011-2012' and TD.clientname = M.clientname and ContextType = 'TEST' and Context = '*' and (TD.TestMode = 'ALL' or TD.TestMode = M.mode) and not exists (select * from tdscore_test_configs.client_tooldependencies TD2 where TD2.ContextType = 'TEST' and TD2.Context = M.TestID and TD.Clientname = M.clientname and TD.IfType = TD2.IfType and TD.IfValue = TD2.IfValue and TD.ThenType = TD2.ThenType and TD.ThenValue = TD2.ThenValue))";

      SqlParametersMaps parameters = null;
      @SuppressWarnings ("unused")
      SingleDataResultSet result = _myDllHelper.executeStatement (_connection, _myDllHelper.fixDataBaseNames (SQL_QUERY), parameters, false).getResultSets ().next ();

    } catch (Exception e) {
      _logger.error (e.getMessage ());
    }
  }

  @Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void test_easyTest5 () throws Exception, IOException
  {
    try
    {
      String SQL_QUERY = "select _fk_Session, _fk_browser, status from testopportunity where _Key = 0x9d7aa6e160e34f3399efdd0330b75f06";

      SqlParametersMaps parameters = null;
      @SuppressWarnings ("unused")
      SingleDataResultSet result = _myDllHelper.executeStatement (_connection, _myDllHelper.fixDataBaseNames (SQL_QUERY), parameters, false).getResultSets ().next ();

      SQL_QUERY = "Select clientname,  DateCreated as createdate, DateEnd as sesend, DateBegin as sesstart from session where _Key = 0xdb5aacdad7244f4998b1c89e5267ab32";
      result = _myDllHelper.executeStatement (_connection, _myDllHelper.fixDataBaseNames (SQL_QUERY), parameters, false).getResultSets ().next ();

    } catch (Exception e) {
      _logger.error (e.getMessage ());
    }
  }

  @Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void test_easyTest6 () throws Exception, IOException
  {
    try
    {
      final String SQL_QUERY = "update deps6d8f9f1az4974z4720zbf18z7f05699ee73f set del = 1 where not exists (select * from testeeaccommodations B, tdscore_test_configs.client_tooldependencies D  where _fk_TestOpportunity = 0xfcec4c40055642d0bf427a64c491ad56 and D.ContextType = 'Test' and D.Context = 'DCAS-EOC-ALGEBRAII-Mathematics-12' and D.clientname = 'Delaware' and D.ThenType = atype and D.ThenValue = acode and B.AccType = D.IfType and B.AccCode = D.IfValue)";

      SqlParametersMaps parameters = null;
      @SuppressWarnings ("unused")
      SingleDataResultSet result = _myDllHelper.executeStatement (_connection, _myDllHelper.fixDataBaseNames (SQL_QUERY), parameters, false).getResultSets ().next ();

    } catch (Exception e) {
      _logger.error (e.getMessage ());
    }
  }

  @Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void Test_Test7() throws Exception
  {
    _logger.info ("Test Test7 -- Boolean value!");
    String segmentKey = "(Delaware)DCAS-EOC-ALG2Seg2-Mathematics-12-Spring-2012-2013";
    // "(Delaware)DCAS-Alt1-PAPER-Mathematics-10-Fall-2012-2013"

    try
    {
    final String SQL_QUERY1 = " (select _fk_Strand as contentLevel, minItems, maxItems, isStrictMax "
        + ", bpweight, adaptiveCut, StartAbility, StartInfo, Scalar "
        + ", case when adaptiveCut is not null then 'true' else 'false' end as isStrand "
        + " from  ${ItemBankDB}.tbladminstrand S   "
        + " where S._fk_AdminSubject = ${segmentKey} ) "
        + " union all "
        + " (select GroupID, minitems, maxitems, isStrictmax, weight, null, null, null, null, 'false' "
        + " from ${ItemBankDB}.affinitygroup G   where G._fk_AdminSubject = ${segmentKey}) "
        + " order by isStrand desc, contentLevel  ";

    SingleDataResultSet result;

    SqlParametersMaps parameters = new SqlParametersMaps ().put ("segmentKey", segmentKey);
    String query = _myDllHelper.fixDataBaseNames (SQL_QUERY1);
    result = _myDllHelper.executeStatement (_connection, query, parameters, true).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      Boolean isStrand =  record. <Boolean>get("isStrand");
      _logger.info ("isStrand position = " + isStrand );
    }
    
    } catch (Exception e) {
      _logger.error (e.getMessage ());
    }
  }
  
  @Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void Test_Test8() throws Exception
  {
    _logger.info ("Test Test8 -- DOUBLE Type!");

    try
    {
    final String SQL_QUERY1 = "select _Key as segmentkey, coalesce(refreshMinutes, 33) as refreshMinutes  ,"
    		+ " case when VirtualTest is null then '(Delaware)DCAS-Reading-10-Fall-2012-2013' else VirtualTest end as ParentTest  ,"
    		+ " case when TestPosition is null then 1 else Testposition end as segmentPosition  ,"
    		+ " (TestID) as SegmentID, blueprintWeight as bpWeight  , itemWeight, abilityOffset  , cset1size, cset1Order  ,"
    		+ " cset2random as randomizer, cset2InitialRandom as initialRandom  ,"
    		+ " case when MinItems is null then 0 else MinItems end as minOpItems  ,"
    		+ " case when MaxItems is null then 0 else MaxItems end as maxOpItems  ,"
    		+ " coalesce(startAbility, 0) as startAbility  ,"
    		+ " coalesce(startInfo, 0) as startInfo  ,"
    		+ " coalesce(slope, 1) as slope  ,"
    		+ " coalesce(intercept, 0) as intercept  ,"
    		+ " FTStartPos, FTEndPos, FTMinItems, FTMaxItems  , selectionAlgorithm  ,"
    		+ " 'bp1'as adaptiveVersion  from  tdscore_dev_itembank2012_sandbox.tblsetofadminsubjects  "
    		+ "  where _Key ='(Delaware)DCAS-Reading-10-Fall-2012-2013'  ";

    SingleDataResultSet result;

    SqlParametersMaps parameters = new SqlParametersMaps ();
    String query = _myDllHelper.fixDataBaseNames (SQL_QUERY1);
    result = _myDllHelper.executeStatement (_connection, query, parameters, true).getResultSets ().next ();
    
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    
    if (record != null) {
    	dumpRecord(record);
//      Boolean isStrand =  record. <Boolean>get("isStrand");
//      _logger.info ("isStrand position = " + isStrand );
    }
    
    } catch (Exception e) {
    	System.out.println("Exception in test8 test: " + e);
      _logger.error (e.getMessage ());
    }
  }
  
  @Test
  @IfProfileValue(name="TestProfile", value="ToBeFixed")
  public void Test_Test9() throws Exception
  {
    _logger.info ("Test what type return coalesce(field, 1) when field type is float");

    try
    {
    final String SQL_QUERY1 = "select coalesce(adaptivecut, 1) "
    		+ "from itembank_tbladminstrand where adaptivecut is null "
    		+ "and _fk_adminsubject = '(Delaware)DCAS-EOC-ALG2Seg1-Mathematics-12-Spring-2012-2013'";

    SingleDataResultSet result;

    SqlParametersMaps parameters = new SqlParametersMaps ();
    String query = _myDllHelper.fixDataBaseNames (SQL_QUERY1);
    result = _myDllHelper.executeStatement (_connection, query, parameters, true).getResultSets ().next ();
    
	int resultRecordsNumber = 0;
	Iterator<DbResultRecord> records = result.getRecords();

	while (records.hasNext()) {
		//DbResultRecord record = 
				records.next();
    	//dumpRecord(record);
    	resultRecordsNumber++;
    }
	System.out.println("Records number is " + resultRecordsNumber);// debug
    
    } catch (Exception e) {
    	System.out.println("Exception in test8 test: " + e);
      _logger.error (e.getMessage ());
    }
  }
}

@Component
class MyISDLLHelper extends AbstractDLL
{

}

@Component
class TOBytesArray
{

  public static byte[] toBytesArrayObjectStreams (String input) throws IOException, ClassNotFoundException
  {
    ByteArrayOutputStream baos = new ByteArrayOutputStream ();
    ObjectOutputStream oos = new ObjectOutputStream (baos);
    oos.writeObject (input);
    oos.close ();
    baos.close ();
    return baos.toByteArray ();
  }
}
