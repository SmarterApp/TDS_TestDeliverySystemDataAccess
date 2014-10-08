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
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opentestsystem.shared.test.LifecycleManagingTestRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import tds.dll.api.ICommonDLL;
import tds.dll.api.IReportingDLL;
import AIR.Common.DB.AbstractConnectionManager;
import AIR.Common.DB.DataBaseTable;
import AIR.Common.DB.SQLConnection;
import AIR.Common.DB.SqlParametersMaps;
import AIR.Common.DB.results.DbResultRecord;
import AIR.Common.DB.results.SingleDataResultSet;
import AIR.Common.Sql.AbstractDateUtilDll;
import TDS.Shared.Exceptions.ReturnStatusException;

/**
 * @author akulakov
 * 
 */
//@RunWith (SpringJUnit4ClassRunner.class)
//@ContextConfiguration (locations = "/test-context.xml")
//@TestExecutionListeners (DependencyInjectionTestExecutionListener.class)
//@IfProfileValue(name="TestProfile", value="ToBeFixed")
@RunWith (LifecycleManagingTestRunner.class)
@ContextConfiguration (locations = "/test-context.xml")
public class TestReportingDLL
{
  @Autowired
  private AbstractConnectionManager _connectionManager = null;

  @Autowired
  private AbstractDateUtilDll       dateUtil           = null;

  private static final Logger       _logger            = LoggerFactory.getLogger (TestReportingDLL.class);

  @Autowired
  private IReportingDLL             _irepDLL           = null;

  @Autowired
  private ICommonDLL                _commonDLL         = null;
  
  @Autowired
  private MyISDLLHelper             _myDllHelper       = null;

  private Set<UUID>                 oppkeys            = new HashSet<UUID> ();

  // Oppkeys in this file:
  // 9CC6B36B-6A38-436D-9EDB-00010D25F2A7 //+2
  // F4E3EA6C-301D-448F-854C-2899A897B2B2 //+8
  // C5FAD533-8F95-4ECF-AB79-02A8D2AB3C40 //+4
  // 29A51278-BC85-4887-88CF-7457CF2C5421 //+5
  // 73C7442E-9940-42DA-A73D-04E15E5C91B2 //+6
  // OpportunityItems:
  // 13D734F8-A604-47AF-BF0C-55D08E7839FA //+9
  // 07A7BC32-4548-4A15-8D50-89CBD43BAAFE //+1
  // 1447A2F5-517E-4915-B981-033EF02DAFB6 //+10
  // 0262DDDA-48F6-4A59-AEFD-60763F22F06E //+7
  // 682bb264-f699-4e7a-aa2f-2b4a3913f7f6 //+3
//  UUID.fromString ("9CC6B36B-6A38-436D-9EDB-00010D25F2A7"),
//  UUID.fromString ("682bb264-f699-4e7a-aa2f-2b4a3913f7f6"),
//  UUID.fromString ("C5FAD533-8F95-4ECF-AB79-02A8D2AB3C40"),
//  UUID.fromString ("29A51278-BC85-4887-88CF-7457CF2C5421"),
//  UUID.fromString ("73C7442E-9940-42DA-A73D-04E15E5C91B2"),
//  UUID.fromString ("0262DDDA-48F6-4A59-AEFD-60763F22F06E"),
//  UUID.fromString ("F4E3EA6C-301D-448F-854C-2899A897B2B2"),
//  UUID.fromString ("13D734F8-A604-47AF-BF0C-55D08E7839FA"),
//  UUID.fromString ("1447A2F5-517E-4915-B981-033EF02DAFB6")
  
  /**
       try {
      String userdir = System.getProperty ("user.dir");
      file = userdir + "\\testlogs\\XML-Reports\\XML-Report";
      xmlFile = file + oppkey.toString ();
      xmlFile = xmlFile + ".xml";

      File logFile = new File (xmlFile);
      if (!logFile.getParentFile ().exists ()) {
        _logger.info ("Creating directory: " + logFile.getParentFile ());

        boolean result = logFile.getParentFile ().mkdirs ();
        if (result) {
          _logger.info ("DIR: " + logFile.getParentFile () + "  created");
        }
      }
      try (BufferedWriter writer = new BufferedWriter (new FileWriter (logFile))) {
        writer.write (currentRes);
      }

   */
// 0x301ECA7789F840D48D640098BB54659F
  
  @Test
  public final void test_XML_GetOppXML_New () throws Exception {
	  SQLConnection connection = null;
	  List<UUID> oppkeys = new ArrayList<UUID> (Arrays.asList (//
//				 UUID.fromString ("5905251c-c834-4b87-b658-bd59ce44c45b")
				 UUID.fromString ("eecabfaa-850c-420e-b26a-874dbc859cf1")
	 ));
	   BufferedWriter writer = null;
	    int n = 0;
	    long time = 0L;
	    long allTime = 0L; //C:\temp\XML-Reports
	    String file = "C:\\temp\\XML-Reports\\XML-ReportTestTest";
	    //String file = "C:\\java_workspace\\TdsDLLDev\\database scripts - mysql\\testlogs\\XML-Reports-Test\\XML-ReportTestTest";
	    String xmlFile = null;
	    try
	    {
	      connection = _connectionManager.getConnection ();
	      for (UUID oppkey : oppkeys)
	      {
	        xmlFile = file + n;
	        xmlFile = xmlFile + ".xml";
	        File logFile = new File (xmlFile);
	        if (!logFile.getParentFile ().exists ()) {
	          _logger.info ("Creating directory: " + logFile.getParentFile ());

	          boolean result = logFile.getParentFile ().mkdirs ();
	          if (result) {
	            _logger.info ("DIR: " + logFile.getParentFile () + "  created");
	          }
	        }

	        long beginTime = System.currentTimeMillis ();
	        long endTime;

	        String currentRes = _irepDLL.XML_GetOppXML_SP (connection, oppkey, true);

	        endTime = System.currentTimeMillis ();
	        time = endTime - beginTime;
	        System.out.println ("time = " + (time) + " ms");
	        allTime += time;

	        System.out.println (logFile.getCanonicalPath ());
	        writer = new BufferedWriter (new FileWriter (logFile));
	        writer.write (currentRes);
	        writer.close ();
	        n++;
	      }
	      System.out.println ("time = " + (allTime) + " ms; " + n + " XML reports. Average time per XML report: " + (allTime / n) + " ms.");
	    } catch (Exception e) {
	      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
	      throw e;
	    } finally {
	      try {
	        // Close the writer regardless of what happens...
	        writer.close ();
	      } catch (Exception e) {
	      }
	    }

  }

  //@Test
  public final void test_XML_GetOppXML_Results () throws Exception {
    SQLConnection connection = null;
    List<UUID> oppkeys = new ArrayList<UUID> (Arrays.asList (//
        UUID.fromString ("07A7BC32-4548-4A15-8D50-89CBD43BAAFE"),
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
    BufferedWriter writer = null;
    int n = 0;
    long time = 0L;
    long allTime = 0L;
    String file = "C:\\java_workspace\\TdsDLLDev\\database scripts - mysql\\testlogs\\XML-Reports\\XML-ReportTest";
    String xmlFile = null;
    try
    {
      connection = _connectionManager.getConnection ();
      for (UUID oppkey : oppkeys)
      {
        xmlFile = file + n;
        xmlFile = xmlFile + ".xml";
        long beginTime = System.currentTimeMillis ();
        long endTime;

        String currentRes = _irepDLL.XML_GetOppXML_SP (connection, oppkey, true);

        endTime = System.currentTimeMillis ();
        time = endTime - beginTime;
        System.out.println ("time = " + (time) + " ms");
        allTime += time;

        File logFile = new File (xmlFile);
        System.out.println (logFile.getCanonicalPath ());
        writer = new BufferedWriter (new FileWriter (logFile));
        writer.write (currentRes);
        writer.close ();
        n++;
      }
      System.out.println ("time = " + (allTime) + " ms; " + n + " XML reports. Average time per XML report: " + (allTime / n) + " ms.");
    } catch (Exception e) {
      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
      throw e;
    } finally {
      try {
        // Close the writer regardless of what happens...
        writer.close ();
      } catch (Exception e) {
      }
    }

  }

  //@Test
  public final void test_XML_GetOppXML_Performance () throws Exception {

    SQLConnection connection = null;
    SingleDataResultSet result;
    DbResultRecord record;
    SqlParametersMaps parameters = new SqlParametersMaps ();
    try {
      connection = _connectionManager.getConnection ();
      String query = "select _key from testopportunity limit 10";
      result = _myDllHelper.executeStatement (connection, query, parameters, false).getResultSets ().next ();
      Iterator<DbResultRecord> resItr = result.getRecords ();
      while (resItr.hasNext ())
      {
        record = resItr.next ();
        if (record != null) {
          UUID oppkey = record.<UUID> get ("_key");
          oppkeys.add (oppkey);
        }
      }
    } catch (Exception e)
    {
      System.out.println ("Exception: " + e.getMessage () + "; " + e.toString ());
    }

    try
    {
      long time = 0L;
      int n = 0;
      for (UUID oppkey : oppkeys)
      {
        System.out.println ("Oppkey = " + oppkey);
        time += test_XML_GetOppXML (connection, oppkey, n);
        n++;
      }
      System.out.println ("time = " + (time) + " ms; " + n + " XML reports. Average time per XML report: " + (time / n) + " ms.");

    } catch (Exception e) {
      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
      throw e;
    }
  }

  // not test
  private final long test_XML_GetOppXML (SQLConnection connection, UUID oppkey, int n) throws Exception {

    BufferedWriter writer = null;
    long time;
    try
    {
      String file = "C:\\java_workspace\\TdsDLLDev\\database scripts - mysql\\testlogs\\XML-Test";
      file = file + n;
      file = file + ".xml";
      long beginTime = System.currentTimeMillis ();
      long endTime;

      String currentRes = _irepDLL.XML_GetOppXML_SP (connection, oppkey, false);

      endTime = System.currentTimeMillis ();
      time = endTime - beginTime;
      System.out.println ("time = " + (time) + " ms");

      File logFile = new File (file);
      // System.out.println (logFile.getCanonicalPath ());
      writer = new BufferedWriter (new FileWriter (logFile));
      writer.write (currentRes);

    } catch (Exception e) {
      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
      throw e;
    } finally {
      try {
        // Close the writer regardless of what happens...
        writer.close ();
      } catch (Exception e) {
      }
    }
    return time;
  }

  //@Test // Test is good 2014-01-23
  public final void test_XML_GetOppXML_SP () throws Exception {
    SQLConnection connection;
    try
    {
      connection = _connectionManager.getConnection ();

      UUID oppkey = UUID.fromString ("07A7BC32-4548-4A15-8D50-89CBD43BAAFE");
      String actualRes = _irepDLL.XML_GetOppXML_SP (connection, oppkey, true);
      System.out.println ("Result: " + actualRes);

      String expected = "<tdsreport><test name=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" subject=\"RE\" testID=\"HSA-Reading-10\" airbank=\"74\" handscoreproject=\"\" contract=\"Hawaii_PT\" mode=\"online\" grade=\"10\" /><testee airkey=\"-2112\" /><opportunity server=\"ip-10-182-167-138\" database=\"tdscore_dev_session2012_sandbox\" key=\"07a7bc32-4548-4a15-8d50-89cbd43baafe\" oppid=\"3\" startdate=\"2012-12-18 16:30:57.107\" status=\"paused\" opportunity=\"1\" statusdate=\"2012-12-18 16:52:00.117\" datecompleted=\"\" dateForceCompleted=\"\" pausecount=\"0\" itemcount=\"14\" ftcount=\"0\" abnormalStarts=\"0\" GracePeriodRestarts=\"0\" TAID=\"demostate5@air.org\" TAName=\"Demo State\" SessionID=\"Stat-2\" windowID=\"ANNUAL\" windowOpportunity=\"1\" ><segment ID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" position=\"1\" formkey=\"74-448\" formID=\"Training::G10R::FA12\" algorithm=\"fixedform\" /><accommodation type=\"Accommodation Codes\" value=\"None\" code=\"AC0\" segment=\"0\" /><accommodation type=\"Language\" value=\"English\" code=\"ENU\" segment=\"0\" /><accommodation type=\"Block Pausing\" value=\"Block pausing with unanswered items\" code=\"TDS_BP1\" segment=\"0\" /><accommodation type=\"Color Choices\" value=\"None\" code=\"TDS_CC0\" segment=\"0\" /><accommodation type=\"Expandable Passages\" value=\"Expandable Passages On\" code=\"TDS_ExpandablePassages1\" segment=\"0\" /><accommodation type=\"Font Type\" value=\"San-Serif (Arial)\" code=\"TDS_FT_San-Serif\" segment=\"0\" /><accommodation type=\"Font Size\" value=\"12pt\" code=\"TDS_F_S12\" segment=\"0\" /><accommodation type=\"Highlight\" value=\"True\" code=\"TDS_Highlight1\" segment=\"0\" /><accommodation type=\"Mark for Review\" value=\"True\" code=\"TDS_MfR1\" segment=\"0\" /><accommodation type=\"Print Size\" value=\"No default zoom applied\" code=\"TDS_PS_L0\" segment=\"0\" /><accommodation type=\"Strikethrough\" value=\"True\" code=\"TDS_ST1\" segment=\"0\" /><accommodation type=\"Tutorial\" value=\"True\" code=\"TDS_T1\" segment=\"0\" /><accommodation type=\"TTS Audio Adjustments\" value=\"Allow TTS Volume Adjustments&amp;Allow TTS Pitch Adjustments\" code=\"TDS_TTSAA_Volume&amp;TDS_TTSAA_Pitch\" segment=\"0\" /><accommodation type=\"TTS Pausing\" value=\"TTS Pausing On\" code=\"TDS_TTSPause1\" segment=\"0\" /><accommodation type=\"TTS\" value=\"Instructions\" code=\"TDS_TTS_Inst\" segment=\"0\" /><accommodation type=\"TTX Business Rules\" value=\"A203\" code=\"TDS_TTX_A203\" segment=\"0\" /><item position=\"1\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5177\" operational=\"1\" IsSelected=\"true\" Format=\"MC\" scorepoints=\"1\" score=\"0\" scorestatus=\"SCORED\" admindate=\"2012-12-18 16:30:58.28\" responsedate=\"2012-12-18 16:31:04.123\" answerkey=\"D\" numberVisits=\"1\" response=\"B\" mimetype=\"text/plain\" clientID=\"5177\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"1\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"2\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5179\" operational=\"1\" IsSelected=\"true\" Format=\"MC\" scorepoints=\"1\" score=\"1\" scorestatus=\"SCORED\" admindate=\"2012-12-18 16:30:58.28\" responsedate=\"2012-12-18 16:31:12.277\" answerkey=\"B\" numberVisits=\"1\" response=\"B\" mimetype=\"text/plain\" clientID=\"5179\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"1\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"3\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5242\" operational=\"1\" IsSelected=\"true\" Format=\"MC\" scorepoints=\"1\" score=\"1\" scorestatus=\"SCORED\" admindate=\"2012-12-18 16:30:58.28\" responsedate=\"2012-12-18 16:31:13.143\" answerkey=\"C\" numberVisits=\"1\" response=\"C\" mimetype=\"text/plain\" clientID=\"5242\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"1\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"4\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5245\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:30:58.28\" responsedate=\"\" answerkey=\"D\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5245\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"1\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"6\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5078\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"A\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5078\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"7\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5079\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"B\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5079\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"8\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5080\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"B\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5080\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"9\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5081\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"A\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5081\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"10\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5082\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"D\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5082\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"11\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5083\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"D\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5083\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"12\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5085\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"C\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5085\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"13\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5087\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"C\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5087\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"14\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"15560\" operational=\"1\" IsSelected=\"false\" Format=\"SR\" scorepoints=\"2\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"SR\" numberVisits=\"0\" mimetype=\"text/plain\" clientID=\"15560\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" ><response><![CDATA[]]></response><scorerationale><![CDATA[]]></scorerationale></item><item position=\"5\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"15602\" operational=\"1\" IsSelected=\"false\" Format=\"GI\" scorepoints=\"2\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:30:58.28\" responsedate=\"\" answerkey=\"GI\" numberVisits=\"0\" mimetype=\"text/xml\" clientID=\"15602\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"1\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" ><response><![CDATA[]]></response><scorerationale><![CDATA[]]></scorerationale></item></opportunity></tdsreport>";

      assertTrue (actualRes.equals (expected));

      oppkey = UUID.fromString ("9CC6B36B-6A38-436D-9EDB-00010D25F2A7");
      actualRes = _irepDLL.XML_GetOppXML_SP (connection, oppkey, true);
      System.out.println ("Result: " + actualRes);

      expected = "<tdsreport><testee airkey=\"-1048\" /><opportunity server=\"\" database=\"tdscore_test_session\" key=\"9cc6b36b-6a38-436d-9edb-00010d25f2a7\" oppid=\"3000687\" startdate=\"2012-09-26 19:12:47.663\" status=\"paused\" opportunity=\"1\" statusdate=\"2014-01-08 12:44:11.598\" datecompleted=\"\" dateForceCompleted=\"\" pausecount=\"0\" itemcount=\"3\" ftcount=\"0\" abnormalStarts=\"0\" GracePeriodRestarts=\"0\" TAID=\"demostate5@air.org\" TAName=\"Demo State\" SessionID=\"Stat-2\" windowID=\"ANNUAL\" windowOpportunity=\"1\" ><segment ID=\"(Minnesota_PT)MCA III Sampler-Science-5-Winter-2012-2013\" position=\"1\" formkey=\"159-49\" formID=\"MCA III SCI G5 Full Sampler\" algorithm=\"fixedform\" /><accommodation type=\"Accommodation Codes\" value=\"None\" code=\"AC0\" segment=\"0\" /><accommodation type=\"Language\" value=\"English\" code=\"ENU\" segment=\"0\" /><accommodation type=\"Block Pausing\" value=\"Block pausing with unanswered items\" code=\"TDS_BP1\" segment=\"0\" /><accommodation type=\"Calculator\" value=\"Standard\" code=\"TDS_CalcStd\" segment=\"0\" /><accommodation type=\"Color Choices\" value=\"None\" code=\"TDS_CC0\" segment=\"0\" /><accommodation type=\"Expandable Passages\" value=\"Expandable Passages On\" code=\"TDS_ExpandablePassages1\" segment=\"0\" /><accommodation type=\"Font Type\" value=\"Verdana\" code=\"TDS_FT_Verdana\" segment=\"0\" /><accommodation type=\"Font Size\" value=\"12pt\" code=\"TDS_F_S12\" segment=\"0\" /><accommodation type=\"Highlight\" value=\"True\" code=\"TDS_Highlight1\" segment=\"0\" /><accommodation type=\"Item Score Report\" value=\"Summarize Scores And Allow Viewing Responses\" code=\"TDS_ISR_SumViewResp\" segment=\"0\" /><accommodation type=\"Mark for Review\" value=\"True\" code=\"TDS_MfR1\" segment=\"0\" /><accommodation type=\"Print Size\" value=\"No default zoom applied\" code=\"TDS_PS_L0\" segment=\"0\" /><accommodation type=\"Strikethrough\" value=\"True\" code=\"TDS_ST1\" segment=\"0\" /><accommodation type=\"TTS Audio Adjustments\" value=\"Allow TTS Volume and Pitch Adjustments\" code=\"TDS_TTSAA_Volume&amp;TDS_TTSAA_Pitch\" segment=\"0\" /><accommodation type=\"TTS Pausing\" value=\"TTS Pausing On\" code=\"TDS_TTSPause1\" segment=\"0\" /><accommodation type=\"TTS\" value=\"Instructions and Items\" code=\"TDS_TTS_Inst&amp;TDS_TTS_Stim&amp;TDS_TTS_Item\" segment=\"0\" /><accommodation type=\"TTX Business Rules\" value=\"Standard Text-to-Speech\" code=\"TDS_TTX_M101\" segment=\"0\" /><score measureof=\"159-457\" measurelabel=\"0\" value=\"OP\" standarderror=\"0.012345\" /><score measureof=\"159-458\" measurelabel=\"-1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-459\" measurelabel=\"-1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-460\" measurelabel=\"-1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-461\" measurelabel=\"-1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-462\" measurelabel=\"1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-463\" measurelabel=\"0\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-464\" measurelabel=\"1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-465\" measurelabel=\"-1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-466\" measurelabel=\"0\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-467\" measurelabel=\"0\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-468\" measurelabel=\"1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-469\" measurelabel=\"0\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-470\" measurelabel=\"-1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-475\" measurelabel=\"0\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-515\" measurelabel=\"\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-518\" measurelabel=\"0\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-519\" measurelabel=\"-1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-520\" measurelabel=\"1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-521\" measurelabel=\"\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-522\" measurelabel=\"\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-523\" measurelabel=\"\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-524\" measurelabel=\"\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-526\" measurelabel=\"1\" value=\"OP\" standarderror=\"\" /></opportunity><comment context=\"my context\" itemposition=\"1\" date=\"2013-10-25 08:28:17.658\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-10-28 10:08:33.022\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-10-30 16:55:40.732\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-10-31 15:58:58.932\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-10-31 16:05:02.068\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-11-04 14:23:47.685\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-11-11 14:02:45.293\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-11-12 20:33:05.977\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-11-25 13:43:47.188\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-11-25 15:02:43.137\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-03 17:11:11.228\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-10 17:52:10.153\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-13 18:32:29.527\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-13 21:15:58.407\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-13 22:33:54.654\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-27 17:06:06.626\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-29 21:51:45.058\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-30 00:22:09.509\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-30 17:00:43.621\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-30 17:11:24.541\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2014-01-03 10:40:09.364\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2014-01-03 12:10:12.096\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2014-01-05 22:13:09.979\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2014-01-06 00:45:36.544\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2014-01-06 14:28:48.841\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2014-01-08 00:41:52.753\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2014-01-08 12:44:09.866\" ><![CDATA[my comments]]></comment></tdsreport>";
      assertTrue (actualRes.equals (expected));

    } catch (Exception e) {
      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
      throw e;
    }
  }

  //@Test // Test is good 2014-01-22, 2014-03-12
  public final void test_XML_GetOppXML_SP_2 () throws Exception {
    SQLConnection connection;
    try
    {
      connection = _connectionManager.getConnection ();

      UUID oppkey = UUID.fromString ("682bb264-f699-4e7a-aa2f-2b4a3913f7f6");
      String actualRes = _irepDLL.XML_GetOppXML_SP (connection, oppkey, true);
      System.out.println ("Result: " + actualRes);

      //String expected = "<tdsreport><testee airkey=\"-161\" /><opportunity server=\"jdbc:mysql://db-dev.opentestsystem.org:3306/tdscore_test_session?useServerPrepStmts=false&amp;rewriteBatchedStatements=true\" database=\"tdscore_test_session\" itemcount=\"0\" ftcount=\"0\" windowOpportunity=\"0\" ><accommodation type=\"Accommodation Codes\" value=\"None\" code=\"AC0\" segment=\"0\" /><accommodation type=\"Language\" value=\"English\" code=\"ENU\" segment=\"0\" /><accommodation type=\"Item Types Exclusion\" value=\"GI\" code=\"GI\" segment=\"0\" /><accommodation type=\"Color Choices\" value=\"None\" code=\"TDS_CC0\" segment=\"0\" /><accommodation type=\"Font Type\" value=\"San-Serif (Arial)\" code=\"TDS_FT_San-Serif\" segment=\"0\" /><accommodation type=\"Font Size\" value=\"14pt\" code=\"TDS_F_S14\" segment=\"0\" /><accommodation type=\"Highlight\" value=\"True\" code=\"TDS_Highlight1\" segment=\"0\" /><accommodation type=\"Mark for Review\" value=\"True\" code=\"TDS_MfR1\" segment=\"0\" /><accommodation type=\"Print on Request\" value=\"None\" code=\"TDS_PoD0\" segment=\"0\" /><accommodation type=\"Print Size\" value=\"No default zoom applied\" code=\"TDS_PS_L0\" segment=\"0\" /><accommodation type=\"Student Comments\" value=\"None\" code=\"TDS_SC0\" segment=\"0\" /><accommodation type=\"Strikethrough\" value=\"True\" code=\"TDS_ST1\" segment=\"0\" /><accommodation type=\"Tutorial\" value=\"True\" code=\"TDS_T1\" segment=\"0\" /><accommodation type=\"TTS\" value=\"None\" code=\"TDS_TTS0\" segment=\"0\" /><accommodation type=\"TTS Pausing\" value=\"TTS Pausing Off\" code=\"TDS_TTSPause0\" segment=\"0\" /><accommodation type=\"TTX Business Rules\" value=\"A203\" code=\"TDS_TTX_A203\" segment=\"0\" /></opportunity></tdsreport>";
      // after chenges with server name
      String expected = "<tdsreport><testee airkey=\"-161\" /><opportunity server=\"\" database=\"tdscore_dev_session2012_sandbox\" itemcount=\"0\" ftcount=\"0\" windowOpportunity=\"0\" ><accommodation type=\"Accommodation Codes\" value=\"None\" code=\"AC0\" segment=\"0\" /><accommodation type=\"Language\" value=\"English\" code=\"ENU\" segment=\"0\" /><accommodation type=\"Item Types Exclusion\" value=\"GI\" code=\"GI\" segment=\"0\" /><accommodation type=\"Color Choices\" value=\"None\" code=\"TDS_CC0\" segment=\"0\" /><accommodation type=\"Font Type\" value=\"San-Serif (Arial)\" code=\"TDS_FT_San-Serif\" segment=\"0\" /><accommodation type=\"Font Size\" value=\"14pt\" code=\"TDS_F_S14\" segment=\"0\" /><accommodation type=\"Highlight\" value=\"True\" code=\"TDS_Highlight1\" segment=\"0\" /><accommodation type=\"Mark for Review\" value=\"True\" code=\"TDS_MfR1\" segment=\"0\" /><accommodation type=\"Print on Request\" value=\"None\" code=\"TDS_PoD0\" segment=\"0\" /><accommodation type=\"Print Size\" value=\"No default zoom applied\" code=\"TDS_PS_L0\" segment=\"0\" /><accommodation type=\"Student Comments\" value=\"None\" code=\"TDS_SC0\" segment=\"0\" /><accommodation type=\"Strikethrough\" value=\"True\" code=\"TDS_ST1\" segment=\"0\" /><accommodation type=\"Tutorial\" value=\"True\" code=\"TDS_T1\" segment=\"0\" /><accommodation type=\"TTS\" value=\"None\" code=\"TDS_TTS0\" segment=\"0\" /><accommodation type=\"TTS Pausing\" value=\"TTS Pausing Off\" code=\"TDS_TTSPause0\" segment=\"0\" /><accommodation type=\"TTX Business Rules\" value=\"A203\" code=\"TDS_TTX_A203\" segment=\"0\" /></opportunity></tdsreport>";
      assertTrue (actualRes.equals (expected));

    } catch (Exception e) {
      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
      throw e;
    }
  }

  //@Test // Test is good 2014-01-22, 2014-03-12
  public final void test__XML_GetOpportunity_SP_1 () throws Exception {

    UUID oppkey = UUID.fromString ("07A7BC32-4548-4A15-8D50-89CBD43BAAFE");

    // declare @result nvarchar(max);
    // exec _XML_GetOpportunity '07A7BC32-4548-4A15-8D50-89CBD43BAAFE', @result
    // output;
    // select @result;

    String sample = "<opportunity server=\"ip-10-182-167-138\" database=\"tdscore_dev_session2012_sandbox\" key=\"07a7bc32-4548-4a15-8d50-89cbd43baafe\" oppid=\"3\" startdate=\"2012-12-18 16:30:57.107\" status=\"paused\" opportunity=\"1\" statusdate=\"2012-12-18 16:52:00.117\" datecompleted=\"\" dateForceCompleted=\"\" pausecount=\"0\" itemcount=\"14\" ftcount=\"0\" abnormalStarts=\"0\" GracePeriodRestarts=\"0\" TAID=\"demostate5@air.org\" TAName=\"Demo State\" SessionID=\"Stat-2\" windowID=\"ANNUAL\" windowOpportunity=\"1\" ><segment ID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" position=\"1\" formkey=\"74-448\" formID=\"Training::G10R::FA12\" algorithm=\"fixedform\" /><accommodation type=\"Accommodation Codes\" value=\"None\" code=\"AC0\" segment=\"0\" /><accommodation type=\"Language\" value=\"English\" code=\"ENU\" segment=\"0\" /><accommodation type=\"Block Pausing\" value=\"Block pausing with unanswered items\" code=\"TDS_BP1\" segment=\"0\" /><accommodation type=\"Color Choices\" value=\"None\" code=\"TDS_CC0\" segment=\"0\" /><accommodation type=\"Expandable Passages\" value=\"Expandable Passages On\" code=\"TDS_ExpandablePassages1\" segment=\"0\" /><accommodation type=\"Font Type\" value=\"San-Serif (Arial)\" code=\"TDS_FT_San-Serif\" segment=\"0\" /><accommodation type=\"Font Size\" value=\"12pt\" code=\"TDS_F_S12\" segment=\"0\" /><accommodation type=\"Highlight\" value=\"True\" code=\"TDS_Highlight1\" segment=\"0\" /><accommodation type=\"Mark for Review\" value=\"True\" code=\"TDS_MfR1\" segment=\"0\" /><accommodation type=\"Print Size\" value=\"No default zoom applied\" code=\"TDS_PS_L0\" segment=\"0\" /><accommodation type=\"Strikethrough\" value=\"True\" code=\"TDS_ST1\" segment=\"0\" /><accommodation type=\"Tutorial\" value=\"True\" code=\"TDS_T1\" segment=\"0\" /><accommodation type=\"TTS Audio Adjustments\" value=\"Allow TTS Volume Adjustments&amp;Allow TTS Pitch Adjustments\" code=\"TDS_TTSAA_Volume&amp;TDS_TTSAA_Pitch\" segment=\"0\" /><accommodation type=\"TTS Pausing\" value=\"TTS Pausing On\" code=\"TDS_TTSPause1\" segment=\"0\" /><accommodation type=\"TTS\" value=\"Instructions\" code=\"TDS_TTS_Inst\" segment=\"0\" /><accommodation type=\"TTX Business Rules\" value=\"A203\" code=\"TDS_TTX_A203\" segment=\"0\" /><item position=\"1\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5177\" operational=\"1\" IsSelected=\"true\" Format=\"MC\" scorepoints=\"1\" score=\"0\" scorestatus=\"SCORED\" admindate=\"2012-12-18 16:30:58.28\" responsedate=\"2012-12-18 16:31:04.123\" answerkey=\"D\" numberVisits=\"1\" response=\"B\" mimetype=\"text/plain\" clientID=\"5177\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"1\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"2\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5179\" operational=\"1\" IsSelected=\"true\" Format=\"MC\" scorepoints=\"1\" score=\"1\" scorestatus=\"SCORED\" admindate=\"2012-12-18 16:30:58.28\" responsedate=\"2012-12-18 16:31:12.277\" answerkey=\"B\" numberVisits=\"1\" response=\"B\" mimetype=\"text/plain\" clientID=\"5179\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"1\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"3\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5242\" operational=\"1\" IsSelected=\"true\" Format=\"MC\" scorepoints=\"1\" score=\"1\" scorestatus=\"SCORED\" admindate=\"2012-12-18 16:30:58.28\" responsedate=\"2012-12-18 16:31:13.143\" answerkey=\"C\" numberVisits=\"1\" response=\"C\" mimetype=\"text/plain\" clientID=\"5242\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"1\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"4\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5245\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:30:58.28\" responsedate=\"\" answerkey=\"D\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5245\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"1\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"6\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5078\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"A\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5078\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"7\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5079\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"B\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5079\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"8\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5080\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"B\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5080\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"9\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5081\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"A\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5081\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"10\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5082\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"D\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5082\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"11\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5083\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"D\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5083\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"12\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5085\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"C\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5085\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"13\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5087\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"C\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5087\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"14\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"15560\" operational=\"1\" IsSelected=\"false\" Format=\"SR\" scorepoints=\"2\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"SR\" numberVisits=\"0\" mimetype=\"text/plain\" clientID=\"15560\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" ><response><![CDATA[]]></response><scorerationale><![CDATA[]]></scorerationale></item><item position=\"5\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"15602\" operational=\"1\" IsSelected=\"false\" Format=\"GI\" scorepoints=\"2\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:30:58.28\" responsedate=\"\" answerkey=\"GI\" numberVisits=\"0\" mimetype=\"text/xml\" clientID=\"15602\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"1\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" ><response><![CDATA[]]></response><scorerationale><![CDATA[]]></scorerationale></item></opportunity>";

    SQLConnection connection;
    try
    {
      connection = _connectionManager.getConnection ();
      String currentRes = _irepDLL._XML_GetOpportunity_SP (connection, oppkey, true);
      System.out.println ("Result: " + currentRes);
      assertTrue (currentRes.equals (sample));
    } catch (Exception e) {
      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
      throw e;
    }
  }

  //@Test // Test is good 2014-01-22, 2014-03-12
  public final void test__XML_GetOpportunity_SP_2 () throws Exception {

    UUID oppkey = UUID.fromString ("9CC6B36B-6A38-436D-9EDB-00010D25F2A7");

    // declare @result nvarchar(max);
    // exec _XML_GetOpportunity '9CC6B36B-6A38-436D-9EDB-00010D25F2A7', @result
    // output;
    // select @result;

    String sample = "<opportunity server=\"ip-10-182-167-138\" database=\"tdscore_dev_session2012_sandbox\" key=\"9cc6b36b-6a38-436d-9edb-00010d25f2a7\" oppid=\"3000687\" startdate=\"2012-09-26 19:12:47.663\" status=\"paused\" opportunity=\"1\" statusdate=\"2013-12-26 14:36:41.025\" datecompleted=\"\" dateForceCompleted=\"\" pausecount=\"0\" itemcount=\"3\" ftcount=\"0\" abnormalStarts=\"0\" GracePeriodRestarts=\"0\" TAID=\"demostate5@air.org\" TAName=\"Demo State\" SessionID=\"Stat-2\" windowID=\"ANNUAL\" windowOpportunity=\"1\" ><segment ID=\"(Minnesota_PT)MCA III Sampler-Science-5-Winter-2012-2013\" position=\"1\" formkey=\"159-49\" formID=\"MCA III SCI G5 Full Sampler\" algorithm=\"fixedform\" /><accommodation type=\"Accommodation Codes\" value=\"None\" code=\"AC0\" segment=\"0\" /><accommodation type=\"Language\" value=\"English\" code=\"ENU\" segment=\"0\" /><accommodation type=\"Block Pausing\" value=\"Block pausing with unanswered items\" code=\"TDS_BP1\" segment=\"0\" /><accommodation type=\"Calculator\" value=\"Standard\" code=\"TDS_CalcStd\" segment=\"0\" /><accommodation type=\"Color Choices\" value=\"None\" code=\"TDS_CC0\" segment=\"0\" /><accommodation type=\"Expandable Passages\" value=\"Expandable Passages On\" code=\"TDS_ExpandablePassages1\" segment=\"0\" /><accommodation type=\"Font Type\" value=\"Verdana\" code=\"TDS_FT_Verdana\" segment=\"0\" /><accommodation type=\"Font Size\" value=\"12pt\" code=\"TDS_F_S12\" segment=\"0\" /><accommodation type=\"Highlight\" value=\"True\" code=\"TDS_Highlight1\" segment=\"0\" /><accommodation type=\"Item Score Report\" value=\"Summarize Scores And Allow Viewing Responses\" code=\"TDS_ISR_SumViewResp\" segment=\"0\" /><accommodation type=\"Mark for Review\" value=\"True\" code=\"TDS_MfR1\" segment=\"0\" /><accommodation type=\"Print Size\" value=\"No default zoom applied\" code=\"TDS_PS_L0\" segment=\"0\" /><accommodation type=\"Strikethrough\" value=\"True\" code=\"TDS_ST1\" segment=\"0\" /><accommodation type=\"TTS Audio Adjustments\" value=\"Allow TTS Volume and Pitch Adjustments\" code=\"TDS_TTSAA_Volume&amp;TDS_TTSAA_Pitch\" segment=\"0\" /><accommodation type=\"TTS Pausing\" value=\"TTS Pausing On\" code=\"TDS_TTSPause1\" segment=\"0\" /><accommodation type=\"TTS\" value=\"Instructions and Items\" code=\"TDS_TTS_Inst&amp;TDS_TTS_Stim&amp;TDS_TTS_Item\" segment=\"0\" /><accommodation type=\"TTX Business Rules\" value=\"Standard Text-to-Speech\" code=\"TDS_TTX_M101\" segment=\"0\" /><score measureof=\"159-457\" measurelabel=\"0\" value=\"OP\" standarderror=\"0.012345\" /><score measureof=\"159-458\" measurelabel=\"-1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-459\" measurelabel=\"-1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-460\" measurelabel=\"-1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-461\" measurelabel=\"-1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-462\" measurelabel=\"1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-463\" measurelabel=\"0\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-464\" measurelabel=\"1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-465\" measurelabel=\"-1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-466\" measurelabel=\"0\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-467\" measurelabel=\"0\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-468\" measurelabel=\"1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-469\" measurelabel=\"0\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-470\" measurelabel=\"-1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-475\" measurelabel=\"0\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-515\" measurelabel=\"\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-518\" measurelabel=\"0\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-519\" measurelabel=\"-1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-520\" measurelabel=\"1\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-521\" measurelabel=\"\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-522\" measurelabel=\"\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-523\" measurelabel=\"\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-524\" measurelabel=\"\" value=\"OP\" standarderror=\"\" /><score measureof=\"159-526\" measurelabel=\"1\" value=\"OP\" standarderror=\"\" /></opportunity>";

    SQLConnection connection;
    try
    {
      connection = _connectionManager.getConnection ();
      String currentRes = _irepDLL._XML_GetOpportunity_SP (connection, oppkey, true);
      System.out.println ("Result: " + currentRes);
      assertTrue (currentRes.equals (sample));
    } catch (Exception e) {
      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
      throw e;
    }
  }

  //@Test // Test is good 2014-01-22
  public final void test__XML_GetOppComments_SP () {

    UUID oppkey = UUID.fromString ("9CC6B36B-6A38-436D-9EDB-00010D25F2A7");
    // UUID oppkey = UUID.fromString ("07A7BC32-4548-4A15-8D50-89CBD43BAAFE");
    // // result is empty

    // UUID oppkey = UUID.fromString ("");

    // final test query is
    // select context as "@context", itemposition as "@itemposition", _date as
    // "@date", comment from testeecomment where _fk_TestOpportunity =
    // 0x9cc6b36b6a38436d9edb00010d25f2a7 and comment is not null order by
    // itemPosition, _date

    String sample = "<comment context=\"my context\" itemposition=\"1\" date=\"2013-10-25 08:28:17.658\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-10-28 10:08:33.022\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-10-30 16:55:40.732\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-10-31 15:58:58.932\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-10-31 16:05:02.068\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-11-04 14:23:47.685\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-11-11 14:02:45.293\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-11-12 20:33:05.977\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-11-25 13:43:47.188\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-11-25 15:02:43.137\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-03 17:11:11.228\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-10 17:52:10.153\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-13 18:32:29.527\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-13 21:15:58.407\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-13 22:33:54.654\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-27 17:06:06.626\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-29 21:51:45.058\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-30 00:22:09.509\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-30 17:00:43.621\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-30 17:11:24.541\" ><![CDATA[my comments]]></comment>";
    //		+ "<comment context=\"my context\" itemposition=\"1\" date=\"2014-01-03 10:40:09.364\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2014-01-03 12:10:12.096\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2014-01-05 22:13:09.979\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2014-01-06 00:45:36.544\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2014-01-06 14:28:48.841\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2014-01-08 00:41:52.753\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2014-01-08 12:44:09.866\" ><![CDATA[my comments]]></comment>";
    //				<comment context=\"my context\" itemposition=\"1\" date=\"2013-10-25 08:28:17.658\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-10-28 10:08:33.022\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-10-30 16:55:40.732\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-10-31 15:58:58.932\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-10-31 16:05:02.068\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-11-04 14:23:47.685\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-11-11 14:02:45.293\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-11-12 20:33:05.977\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-11-25 13:43:47.188\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-11-25 15:02:43.137\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-03 17:11:11.228\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-10 17:52:10.153\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-13 18:32:29.527\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-13 21:15:58.407\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-13 22:33:54.654\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-23 15:27:56.903\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-23 15:43:36.604\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-23 15:51:52.188\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-26 12:41:22.221\" ><![CDATA[my comments]]></comment><comment context=\"my context\" itemposition=\"1\" date=\"2013-12-26 14:25:37.721\" ><![CDATA[my comments]]></comment>

    SQLConnection connection;
    try
    {
      connection = _connectionManager.getConnection ();
      String currentRes = _irepDLL._XML_GetOppComments_SP (connection, oppkey, true);
      System.out.println ("Result: " + currentRes);
      assertTrue (currentRes.equals (sample));
    } catch (Exception e) {
      System.out.println ("Exception: " + e.getMessage () + "; " + e.toString ());
      _logger.error (e.getMessage ());
    }
  }

  //@Test // Test is good 2014-01-22, 2014-03-12
  public final void test__XML_GetOpportunityItems_SP () throws Exception {

    UUID oppkey = UUID.fromString ("07A7BC32-4548-4A15-8D50-89CBD43BAAFE");

    // final test query from MS SQL is
    // declare @result nvarchar(max);
    // exec _XML_GetOpportunityItems '07A7BC32-4548-4A15-8D50-89CBD43BAAFE',
    // @result output;
    // select @result;

    String sample = "<item position=\"1\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5177\" operational=\"1\" IsSelected=\"true\" Format=\"MC\" scorepoints=\"1\" score=\"0\" scorestatus=\"SCORED\" admindate=\"2012-12-18 16:30:58.28\" responsedate=\"2012-12-18 16:31:04.123\" answerkey=\"D\" numberVisits=\"1\" response=\"B\" mimetype=\"text/plain\" clientID=\"5177\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"1\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"2\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5179\" operational=\"1\" IsSelected=\"true\" Format=\"MC\" scorepoints=\"1\" score=\"1\" scorestatus=\"SCORED\" admindate=\"2012-12-18 16:30:58.28\" responsedate=\"2012-12-18 16:31:12.277\" answerkey=\"B\" numberVisits=\"1\" response=\"B\" mimetype=\"text/plain\" clientID=\"5179\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"1\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"3\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5242\" operational=\"1\" IsSelected=\"true\" Format=\"MC\" scorepoints=\"1\" score=\"1\" scorestatus=\"SCORED\" admindate=\"2012-12-18 16:30:58.28\" responsedate=\"2012-12-18 16:31:13.143\" answerkey=\"C\" numberVisits=\"1\" response=\"C\" mimetype=\"text/plain\" clientID=\"5242\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"1\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"4\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5245\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:30:58.28\" responsedate=\"\" answerkey=\"D\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5245\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"1\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"6\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5078\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"A\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5078\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"7\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5079\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"B\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5079\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"8\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5080\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"B\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5080\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"9\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5081\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"A\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5081\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"10\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5082\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"D\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5082\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"11\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5083\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"D\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5083\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"12\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5085\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"C\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5085\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"13\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"5087\" operational=\"1\" IsSelected=\"false\" Format=\"MC\" scorepoints=\"1\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"C\" numberVisits=\"0\" response=\"\" mimetype=\"text/plain\" clientID=\"5087\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" /><item position=\"14\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"15560\" operational=\"1\" IsSelected=\"false\" Format=\"SR\" scorepoints=\"2\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:31:13.18\" responsedate=\"\" answerkey=\"SR\" numberVisits=\"0\" mimetype=\"text/plain\" clientID=\"15560\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"2\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" ><response><![CDATA[]]></response><scorerationale><![CDATA[]]></scorerationale></item><item position=\"5\" segmentID=\"(Hawaii_PT)HSA-Reading-10-Fall-2012-2013\" bankkey=\"74\" airkey=\"15602\" operational=\"1\" IsSelected=\"false\" Format=\"GI\" scorepoints=\"2\" score=\"-1\" scorestatus=\"\" admindate=\"2012-12-18 16:30:58.28\" responsedate=\"\" answerkey=\"GI\" numberVisits=\"0\" mimetype=\"text/xml\" clientID=\"15602\" strand=\"Reading-10-Undesignated\" contentLevel=\"Reading-10-Undesignated\" pagenumber=\"1\" pagevisits=\"\" pagetime=\"\" dropped=\"0\" ><response><![CDATA[]]></response><scorerationale><![CDATA[]]></scorerationale></item>";

    SQLConnection connection = null;
    boolean preexistingAutoCommitMode = true;
    try
    {
      connection = _connectionManager.getConnection ();
      preexistingAutoCommitMode = connection.getAutoCommit ();
      connection.setAutoCommit (false);

      String currentRes = _irepDLL._XML_GetOpportunityItems_SP (connection, oppkey, true);
      System.out.println ("Result: " + currentRes);
      assertTrue (currentRes.equals (sample));
    } catch (Exception e) {
      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
      throw e;
    } finally {
      try {
        connection.rollback ();
      } catch (Exception e) {
        _logger.error (String.format ("Failed rollback: %s", e.getMessage ()));
        throw e;
      }
      connection.setAutoCommit (preexistingAutoCommitMode);
    }
  }

  //@Test // Test is good 2014-01-22, 2014-03-12
  public final void test_ItemkeyStrandName_F () throws Exception {

    String testkey = "(Delaware)DCAS-Reading-6-Fall-2012-2013";
    String itemkey = "148-100012";
    // final test query is
    // select
    // [TDSCore_Dev_Itembank2012_Sandbox].[dbo].[ItemkeyStrandName]('(Delaware)DCAS-Reading-6-Fall-2012-2013',
    // '148-100012');
    String sample = "ELA-2";
    SQLConnection connection;
    try
    {
      connection = _connectionManager.getConnection ();
      String currentRes = _irepDLL.ItemkeyStrandName_F (connection, testkey, itemkey, true);
      System.out.println ("Result: " + currentRes);
      assertTrue (currentRes.equals (sample));
    } catch (Exception e) {
      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
      throw e;
    }
  }

  //@Test // Test is good 2014-01-22, 2014-03-12
  public final void test_XML_GetAccomodations_F () throws Exception {

    UUID oppkey = UUID.fromString ("C5FAD533-8F95-4ECF-AB79-02A8D2AB3C40");
    // final test query is
    // select
    // TDSCore_Dev_Session2012_Sandbox.dbo.XML_GetAccomodations('C5FAD533-8F95-4ECF-AB79-02A8D2AB3C40');
    // Below is sample from MS SQL Server. Our result differs from this sample
    // an order of records
    // String sample =
    // "<accommodation type=\"Accommodation Codes\" value=\"None\" code=\"AC0\" segment=\"0\" /><accommodation type=\"Language\" value=\"English\" code=\"ENU\" segment=\"0\" /><accommodation type=\"Color Choices\" value=\"None\" code=\"TDS_CC0\" segment=\"0\" /><accommodation type=\"Expandable Passages\" value=\"Expandable Passages On\" code=\"TDS_ExpandablePassages1\" segment=\"0\" /><accommodation type=\"Font Size\" value=\"14pt\" code=\"TDS_F_S14\" segment=\"0\" /><accommodation type=\"Font Type\" value=\"Verdana\" code=\"TDS_FT_Verdana\" segment=\"0\" /><accommodation type=\"Highlight\" value=\"True\" code=\"TDS_Highlight1\" segment=\"0\" /><accommodation type=\"Mark for Review\" value=\"True\" code=\"TDS_MfR1\" segment=\"0\" /><accommodation type=\"Print Size\" value=\"No default zoom applied\" code=\"TDS_PS_L0\" segment=\"0\" /><accommodation type=\"Strikethrough\" value=\"True\" code=\"TDS_ST1\" segment=\"0\" /><accommodation type=\"TTS\" value=\"Instructions and Items\" code=\"TDS_TTS_Inst&amp;TDS_TTS_Stim&amp;TDS_TTS_Item\" segment=\"0\" /><accommodation type=\"TTS Audio Adjustments\" value=\"Allow TTS Volume and Pitch Adjustments\" code=\"TDS_TTSAA_Volume&amp;TDS_TTSAA_Pitch\" segment=\"0\" /><accommodation type=\"TTS Pausing\" value=\"TTS Pausing On\" code=\"TDS_TTSPause1\" segment=\"0\" /><accommodation type=\"TTX Business Rules\" value=\"Standard Text-to-Speech\" code=\"TDS_TTX_M101\" segment=\"0\" /><accommodation type=\"Calculator\" value=\"Standard\" code=\"TDS_CalcStd\" segment=\"1\" /><accommodation type=\"Calculator\" value=\"Standard\" code=\"TDS_CalcStd\" segment=\"3\" />";
    String sample = "<accommodation type=\"Accommodation Codes\" value=\"None\" code=\"AC0\" segment=\"0\" /><accommodation type=\"Language\" value=\"English\" code=\"ENU\" segment=\"0\" /><accommodation type=\"Color Choices\" value=\"None\" code=\"TDS_CC0\" segment=\"0\" /><accommodation type=\"Expandable Passages\" value=\"Expandable Passages On\" code=\"TDS_ExpandablePassages1\" segment=\"0\" /><accommodation type=\"Font Type\" value=\"Verdana\" code=\"TDS_FT_Verdana\" segment=\"0\" /><accommodation type=\"Font Size\" value=\"14pt\" code=\"TDS_F_S14\" segment=\"0\" /><accommodation type=\"Highlight\" value=\"True\" code=\"TDS_Highlight1\" segment=\"0\" /><accommodation type=\"Mark for Review\" value=\"True\" code=\"TDS_MfR1\" segment=\"0\" /><accommodation type=\"Print Size\" value=\"No default zoom applied\" code=\"TDS_PS_L0\" segment=\"0\" /><accommodation type=\"Strikethrough\" value=\"True\" code=\"TDS_ST1\" segment=\"0\" /><accommodation type=\"TTS Audio Adjustments\" value=\"Allow TTS Volume and Pitch Adjustments\" code=\"TDS_TTSAA_Volume&amp;TDS_TTSAA_Pitch\" segment=\"0\" /><accommodation type=\"TTS Pausing\" value=\"TTS Pausing On\" code=\"TDS_TTSPause1\" segment=\"0\" /><accommodation type=\"TTS\" value=\"Instructions and Items\" code=\"TDS_TTS_Inst&amp;TDS_TTS_Stim&amp;TDS_TTS_Item\" segment=\"0\" /><accommodation type=\"TTX Business Rules\" value=\"Standard Text-to-Speech\" code=\"TDS_TTX_M101\" segment=\"0\" /><accommodation type=\"Calculator\" value=\"Standard\" code=\"TDS_CalcStd\" segment=\"1\" /><accommodation type=\"Calculator\" value=\"Standard\" code=\"TDS_CalcStd\" segment=\"3\" />";
    SQLConnection connection;
    try
    {
      connection = _connectionManager.getConnection ();
      String currentRes = _irepDLL.XML_GetAccomodations_F (connection, oppkey, true);
      System.out.println ("Result: " + currentRes);
      assertTrue (currentRes.equals (sample));
    } catch (Exception e) {
      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
      throw e;
    }

  }

  //@Test // Test is good 2014-01-22, 2014-03-12
  public final void test_XML_GetScores_F () throws Exception {

    UUID oppkey = UUID.fromString ("C5FAD533-8F95-4ECF-AB79-02A8D2AB3C40");
    // final test query is:
    // select
    // TDSCore_Dev_Session2012_Sandbox.dbo.XML_GetScores('C5FAD533-8F95-4ECF-AB79-02A8D2AB3C40');
    String sample = "<score measureof=\"Overall\" measurelabel=\"RawScore\" value=\"9/26\" standarderror=\"\" />";

    SQLConnection connection;
    try
    {
      connection = _connectionManager.getConnection ();
      String currentRes = _irepDLL.XML_GetScores_F (connection, oppkey, true);
      System.out.println ("Result: " + currentRes);
      assertTrue (currentRes.equals (sample));
    } catch (Exception e) {
      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
      throw e;
    }
  }

  //@Test // Test is good 2014-01-22, 2014-03-12
  public final void test_XML_GetSegments_F () throws Exception {

    UUID oppkey = UUID.fromString ("C5FAD533-8F95-4ECF-AB79-02A8D2AB3C40");
    // final test query is:
    // select
    // TDSCore_Dev_Session2012_Sandbox.dbo.XML_GetSegments('C5FAD533-8F95-4ECF-AB79-02A8D2AB3C40');
    String sample = "<segment ID=\"(Minnesota_PT)MCA III M G3 S1-Mathematics-3-Winter-2012-2013\" position=\"1\" formkey=\"159-11\" formID=\"Spring2012::G3Math::S1\" algorithm=\"fixedform\" /><segment ID=\"(Minnesota_PT)MCA III M G3 S2-Mathematics-3-Winter-2012-2013\" position=\"2\" formkey=\"159-12\" formID=\"Spring2012::G3Math::S2\" algorithm=\"fixedform\" /><segment ID=\"(Minnesota_PT)MCA III M G3 S3-Mathematics-3-Winter-2012-2013\" position=\"3\" formkey=\"159-13\" formID=\"Spring2012::G3Math::S3\" algorithm=\"fixedform\" />";

    SQLConnection connection;
    try
    {
      connection = _connectionManager.getConnection ();
      String currentRes = _irepDLL.XML_GetSegments_F (connection, oppkey, true);
      System.out.println ("Result: " + currentRes);
      assertTrue (currentRes.equals (sample));
    } catch (Exception e) {
      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
      throw e;
    }
  }

  //@Test // Test is good 2014-01-22, 2014-03-12
  public final void test_XML_GetTest_F () throws Exception {

    String testkey = "(Delaware)DCAS-Alt1-PAPER-Mathematics-10-Fall-2012-2013";
    String mode = "online";
    // final test query is:
    // select
    // TDSCore_Dev_Session2012_Sandbox.dbo.XML_GetTest('(Delaware)DCAS-Alt1-PAPER-Mathematics-10-Fall-2012-2013',
    // 'online');
    String sample = "<test name=\"(Delaware)DCAS-Alt1-PAPER-Mathematics-10-Fall-2012-2013\" subject=\"MA\" testID=\"DCAS-Alt1-PAPER-Mathematics-9-10\" airbank=\"153\" handscoreproject=\"\" contract=\"Delaware\" mode=\"online\" grade=\"9-10\" />";

    SQLConnection connection;
    try
    {
      connection = _connectionManager.getConnection ();
      String currentRes = _irepDLL.XML_GetTest_F (connection, testkey, mode, true);
      System.out.println ("Result: " + currentRes);
      assertTrue (currentRes.equals (sample));
    } catch (Exception e) {
      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
      throw e;
    }
  }

  //@Test // Test is good 2014-01-22, 2014-03-12
  public final void test_XML_GetTestee_F () throws Exception {

    UUID oppkey = UUID.fromString ("29A51278-BC85-4887-88CF-7457CF2C5421");
    // final test query is:
    // select
    // TDSCore_Dev_Session2012_Sandbox.dbo.[XML_GetTestee]('29A51278-BC85-4887-88CF-7457CF2C5421',
    // null);
    String sample = "<testee airkey=\"2634\" ><testeeattribute context=\"INITIAL\" name=\"DOB\" value=\"03192000\" contextDate=\"2012-07-19 12:03:36.1\" /><testeeattribute context=\"INITIAL\" name=\"Ethnicity\" value=\"0\" contextDate=\"2012-07-19 12:03:36.1\" /><testeeattribute context=\"INITIAL\" name=\"FirstName\" value=\"JULIA\" contextDate=\"2012-07-19 12:03:36.1\" /><testeeattribute context=\"INITIAL\" name=\"Gender\" value=\"M\" contextDate=\"2012-07-19 12:03:36.1\" /><testeeattribute context=\"INITIAL\" name=\"Grade\" value=\"08\" contextDate=\"2012-07-19 12:03:36.1\" /><testeeattribute context=\"INITIAL\" name=\"LastName\" value=\"Williams\" contextDate=\"2012-07-19 12:03:36.1\" /><testeeattribute context=\"INITIAL\" name=\"SSID\" value=\"9999999007\" contextDate=\"2012-07-19 12:03:36.1\" /><testeerelationship context=\"INITIAL\" name=\"DistrictID\" entitykey=\"2341\" value=\"195\" contextDate=\"2012-07-19 12:03:36.117\" /><testeerelationship context=\"INITIAL\" name=\"DistrictName\" entitykey=\"2341\" value=\"DCAS Demo District\" contextDate=\"2012-07-19 12:03:36.117\" /><testeerelationship context=\"INITIAL\" name=\"SchoolID\" entitykey=\"2379\" value=\"1001\" contextDate=\"2012-07-19 12:03:36.117\" /><testeerelationship context=\"INITIAL\" name=\"SchoolName\" entitykey=\"2379\" value=\"DCAS Demo School A\" contextDate=\"2012-07-19 12:03:36.117\" /></testee>";

    SQLConnection connection;
    try
    {
      connection = _connectionManager.getConnection ();
      String currentRes = _irepDLL.XML_GetTestee_F (connection, oppkey, true);
      System.out.println ("Result: " + currentRes);
      assertTrue (currentRes.equals (sample));
    } catch (Exception e) {
      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
      throw e;
    }
  }

  //@Test // Test is good 2014-01-22, 2014-03-12
  public final void test_XML_GetToolUsage_F () throws Exception {

    UUID oppkey = UUID.fromString ("73C7442E-9940-42DA-A73D-04E15E5C91B2");
    // final test query is:
    // select
    // TDSCore_Dev_Session2012_Sandbox.dbo.[XML_GetToolUsage]('73C7442E-9940-42DA-A73D-04E15E5C91B2');
    String sample = "<toolusage type=\"TTS\" code=\"TDS_TTS_Item\"><toolpage page=\"1\" groupID=\"I-157-956\" count=\"1\" /></toolusage>";

    SQLConnection connection;
    try
    {
      connection = _connectionManager.getConnection ();
      String currentRes = _irepDLL.XML_GetToolUsage_F (connection, oppkey, true);
      System.out.println ("Result: " + currentRes);
      assertTrue (currentRes.equals (sample));
    } catch (Exception e) {
      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
      throw e;
    }
  }

  //@Test // Test is good 2014-01-22, 2014-03-12
  public final void test_TestBankKey_F () throws Exception {

    String testkey = "(Delaware)DCAS-Alt1-PAPER-Mathematics-10-Fall-2012-2013";
    // final test query is:
    // select
    // TDSCore_Dev_Itembank2012_Sandbox.dbo.TestBankKey('(Delaware)DCAS-Alt1-PAPER-Mathematics-10-Fall-2012-2013');
    long sample = 153;

    SQLConnection connection;
    try
    {
      connection = _connectionManager.getConnection ();
      Long currentRes = _irepDLL.TestBankKey_F (connection, testkey, true);
      System.out.println ("Result: " + currentRes);
      assertTrue (currentRes == sample);
    } catch (Exception e) {
      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
      throw e;
    }
  }

  //@Test // Test is good 2014-01-22, 2014-03-12
  public final void test_TestGradeSpan_F () throws Exception {

    String testkey = "(Delaware)DCAS-Alt1-PAPER-Mathematics-10-Fall-2012-2013";
    // final test query is:
    // select
    // TDSCore_Dev_Itembank2012_Sandbox.dbo.TestGradeSpan('(Delaware)DCAS-Alt1-PAPER-Mathematics-10-Fall-2012-2013');
    String sample = "9-10";

    SQLConnection connection;
    try
    {
      connection = _connectionManager.getConnection ();
      String currentRes = _irepDLL.TestGradeSpan_F (connection, testkey, true);
      System.out.println ("Result: " + currentRes);
      assertTrue (currentRes.equals (sample));
    } catch (Exception e) {
      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
      throw e;
    }
  }

  //@Test // Test is good 2014-01-22, 2014-03-12
  public final void test_TestKeyClient_F () throws Exception {

    String testkey = "(Delaware)DCAS-Alt1-PAPER-Mathematics-10-Fall-2012-2013";
    // final test query is:
    // select
    // TDSCore_Dev_Session2012_Sandbox.dbo.[XML_GetToolUsage]('73C7442E-9940-42DA-A73D-04E15E5C91B2');
    String sample = "Delaware";

    SQLConnection connection;
    try
    {
      connection = _connectionManager.getConnection ();
      String currentRes = _commonDLL.TestKeyClient_FN (connection, testkey);
      System.out.println ("Result: " + currentRes);
      assertTrue (currentRes.equals (sample));
    } catch (Exception e) {
      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
      throw e;
    }
  }

  //@Test // Test is good 2014-01-22, 2014-03-12
  public final void test_createTMPPagesTable () throws Exception {
    // UUID oppkey = UUID.fromString ("0262DDDA-48F6-4A59-AEFD-60763F22F06E");
    UUID oppkey = UUID.fromString ("07A7BC32-4548-4A15-8D50-89CBD43BAAFE");

    SQLConnection connection;
    try
    {
      connection = _connectionManager.getConnection ();
      DataBaseTable table = _irepDLL.createTMPPagesTable (connection, oppkey, true);
      System.out.println ("Result: " + table.toString ());
      String query = "select * from ${table}";

      Map<String, String> unquotedparms = new HashMap<String, String> ();
      unquotedparms.put ("table", table.getTableName ());
      query = _myDllHelper.fixDataBaseNames (query, unquotedparms);
      SqlParametersMaps parameters = new SqlParametersMaps ();

      SingleDataResultSet result;
      DbResultRecord record;

      result = _myDllHelper.executeStatement (connection, query, parameters, true).getResultSets ().next ();
      Iterator<DbResultRecord> resItr = result.getRecords ();
      while (resItr.hasNext ())
      {
        record = resItr.next ();
        dumpRecord (record);
      }
      connection.dropTemporaryTable (table);
    } catch (Exception e) {
      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
      throw e;
    }
  }

  //@Test // Test is good 2014-01-22, 2014-03-12
  public final void test_createTMPItemsTable () throws Exception {
    try
    {
      UUID oppkey = UUID.fromString ("0262DDDA-48F6-4A59-AEFD-60763F22F06E");
      Date date = null;

      SQLConnection connection;

      connection = _connectionManager.getConnection ();
      DataBaseTable table = _irepDLL.createTMPItemsTable (connection, oppkey, date, true);
      System.out.println ("Result: " + table.toString ());
      String query = "select * from ${table}";

      Map<String, String> unquotedparms = new HashMap<String, String> ();
      unquotedparms.put ("table", table.getTableName ());
      query = _myDllHelper.fixDataBaseNames (query, unquotedparms);
      SqlParametersMaps parameters = new SqlParametersMaps ();

      SingleDataResultSet result;
      DbResultRecord record;

      result = _myDllHelper.executeStatement (connection, query, parameters, true).getResultSets ().next ();
      Iterator<DbResultRecord> resItr = result.getRecords ();
      while (resItr.hasNext ())
      {
        record = resItr.next ();
        dumpRecord (record);
      }
      connection.dropTemporaryTable (table);
    } catch (Exception e) {
      _logger.error ("Exception: " + e.getMessage () + "; " + e.toString ());
      throw e;
    }
  }

  // it is not Test
  public final void test_findOppKeyForPagesAndItemsTables () throws SQLException, ReturnStatusException {
    // select distinct _fk_TestOpportunity
    // from ClientLatency with(nolock)
    // where page is not null;

    SQLConnection connection;
    connection = _connectionManager.getConnection ();

    String query = "select distinct _fk_TestOpportunity as oppkey "
        + " from clientlatency where page is not null";
    SqlParametersMaps parameters = new SqlParametersMaps ();
    UUID oppkey = null;
    Set<UUID> oppkeys = new HashSet<UUID> ();
    DbResultRecord record;

    SingleDataResultSet result = _myDllHelper.executeStatement (connection, query, parameters, true).getResultSets ().next ();
    Iterator<DbResultRecord> resItr = result.getRecords ();
    while (resItr.hasNext ())
    {
      record = resItr.next ();
      oppkey = record.<UUID> get ("oppkey");
      oppkeys.add (oppkey);
    }
    System.out.println ("Size of all oppkeys = " + oppkeys.size ());

    List<UUID> listOppKeys = new ArrayList<UUID> ();
    for (UUID oppky : oppkeys)
    {
      Date date = null;
      long count = 0L;

      query = "select items_archived as dateArchived "
          + " from testopportunity where _Key = ${oppkey}";
      parameters.put ("oppkey", oppky);
      result = _myDllHelper.executeStatement (connection, query, parameters, false).getResultSets ().next ();
      record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        date = record.<Date> get ("dateArchived");
      }

      DataBaseTable table = _irepDLL.createTMPItemsTable (connection, oppky, date, true);
      System.out.println ("Result: " + table.toString ());
      query = "select count(*) as count from ${table}";

      Map<String, String> unquotedparms = new HashMap<String, String> ();
      unquotedparms.put ("table", table.getTableName ());
      query = _myDllHelper.fixDataBaseNames (query, unquotedparms);

      result = _myDllHelper.executeStatement (connection, query, parameters, true).getResultSets ().next ();
      record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        count = record.<Long> get ("count");
      }
      if (count > 0)
      {
        listOppKeys.add (oppky);
      }
      connection.dropTemporaryTable (table);
    }
    System.out.println ("Size = " + listOppKeys.size ());

    for (UUID oppky : listOppKeys)
    {
      System.out.println ("Oppkey = " + oppky);
    }
  }

  private void dumpRecord (DbResultRecord record) throws ReturnStatusException {
    System.out.println ();
    String columnName = null;
    Iterator<String> itNames = record.getColumnNames ();
    while (itNames.hasNext ())
    {
      columnName = itNames.next ();
      Object value = record.get (record.getColumnToIndex (columnName).get ());
      _logger.info (String.format ("%s: %s", columnName, value.toString ()));
    }
    System.out.println ();
  }

  @SuppressWarnings ("unused")
  private void dumpAndAssertRecord (DbResultRecord record, Map<String, Object> columnsValueMap) throws ReturnStatusException {
    System.out.println ();
    String columnName = null;
    Iterator<String> itNames = record.getColumnNames ();
    while (itNames.hasNext ())
    {
      columnName = itNames.next ();
      Object expectedValue = columnsValueMap.get (columnName);
      Object actualValue = record.get (record.getColumnToIndex (columnName).get ());
      assertEquals (expectedValue, actualValue);
      _logger.info (String.format ("%s: %s", columnName, actualValue.toString ()));
    }
    System.out.println ();
  }

  // usefull program
  public void test_test_SystemProperty ()
  {
    String home = System.getProperty ("user.home");
    String userdir = System.getProperty ("user.dir");
    System.out.println ("home: " + home);
    System.out.println ();
    System.out.println ("userdir: " + userdir);
    System.out.println ();
    for (Map.Entry<?, ?> e : System.getProperties ().entrySet ()) {
      System.out.println (String.format ("%s = %s", e.getKey (), e.getValue ()));
    }
  }

}
