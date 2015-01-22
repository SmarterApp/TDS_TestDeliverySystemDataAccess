/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.mysql;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import tds.dll.api.ICommonDLL;
import tds.dll.api.IReportingDLL;
import tds.dll.api.IRtsReportingDLL;
import AIR.Common.DB.AbstractDLL;
import AIR.Common.DB.DataBaseTable;
import AIR.Common.DB.SQLConnection;
import AIR.Common.DB.SQL_TYPE_To_JAVA_TYPE;
import AIR.Common.DB.SqlParametersMaps;
import AIR.Common.DB.results.DbResultRecord;
import AIR.Common.DB.results.SingleDataResultSet;
import AIR.Common.Helpers._Ref;
import AIR.Common.Sql.AbstractDateUtilDll;
import TDS.Shared.Exceptions.ReturnStatusException;
import TDS.Shared.Web.client.ITdsRestClient;

/**
 * @author akulakov
 * 
 */
public class ReportingDLL extends AbstractDLL implements IReportingDLL
{
  private static Logger        _logger    = LoggerFactory.getLogger (ReportingDLL.class);

  @Autowired
  protected AbstractDateUtilDll _dateUtil  = null;

  @Autowired
  protected ICommonDLL          _commonDll = null;
 
  @Autowired
  protected IRtsReportingDLL 	_rtsReporting;
    
  @Autowired(required=false)
  private ITdsRestClient 		_restClient;
  
  protected Object ls = System.getProperties().get("line.separator");

  private String at = "@";
  private String slash = "/";
    
  protected final String TDS_REPORT_NODE_NAME 		= "TDSReport";
  
  protected final String TEST_NODE_NAME 			= "Test";
  
  protected final String TESTEE_NODE_NAME 			= "Examinee";
  protected final String TESTEEATTRIBUTE_NODE_NAME 	= TESTEE_NODE_NAME + "Attribute";
  protected final String TESTEERELATIONSHIP_NODE_NAME = TESTEE_NODE_NAME + "Relationship";
  
  protected final String OPPORTUNITY_NODE_NAME 		= "Opportunity";
  protected final String SEGMENT_NODE_NAME 			= "Segment";
  protected final String ACCOMMODATION_NODE_NAME 	= "Accommodation";
  protected final String SCORE_NODE_NAME 			= "Score";
  protected final String ITEM_NODE_NAME 			= "Item";
  protected final String RESPONSE_NODE_NAME 		= "Response";
  
  protected final String SCOREINFO_NODE_NAME 		= "ScoreInfo";
  protected final String SCORERATIONALE_NODE_NAME 	= "ScoreRationale";
  protected final String SCOREINFOMESSAGE_NODE_NAME = "Message";
  
  protected final String COMMENT_NODE_NAME 			= "Comment";
  protected final String TOOLUSAGE_NODE_NAME 		= "ToolUsage";
  
  protected final String INITIAL 					= "INITIAL";
  protected final String FINAL 						= "FINAL";
  
  // Letter from David 2014-10-29:
  // That's no applicable to open source TDS. 
  // HARDCODED VALUE !!!
  protected final int IS_DEMO 						= 1; 
  protected final String EMPTY 						= "";
  
  protected final String SPACE = " ";
  
  private String _tisUrl;
  
  private String _tisStatusCallbackUrl;

  private long _tisWaitTime;
  
  private long _tisMaxWaitTime;
  
  @PostConstruct
  private void init () {
    if (_tisUrl != null && _tisStatusCallbackUrl != null) {
      _tisUrl += "?statusCallback=" + /*UrlEncoderDecoderUtils.encode (*/_tisStatusCallbackUrl;
    }
  }
  
 //
  public String XML_GetOppXML_SP (SQLConnection connection, UUID oppkey, boolean debug) throws ReturnStatusException
  {
    Date starttime = _dateUtil.getDateWRetStatus (connection);

    String res 		= null;
	String testkey = null;
	String mode 	= null;

    StringBuilder strBuilder = new StringBuilder ();

    try {
	  String test 	= null;
	  String testee = null;
      String oppxml = null;
      String comments = null;
      String tools 	= null;
      Long testeeKey = null;
		
	  String dbmsg 	= null;
		
      SingleDataResultSet result;
      DbResultRecord record;

      String query = "select _efk_AdminSubject as testkey, mode, _efk_Testee as testeeKey from testopportunity where _Key = ${oppkey}";
      SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey);
      result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
      record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        testkey = record.<String> get ("testkey");
        mode 	= record.<String> get ("mode");
        testeeKey = record.<Long> get ("testeeKey");
      }

      try{
    	  
    	  test 		= XML_GetTest_F (connection, testkey, mode, debug);
    	  
    	  if(test == null || test.isEmpty())
    	  {
        	  test = "<" + TEST_NODE_NAME + "/>";
        	  dbmsg = String.format ("XML Element %s is either empty or null: test key = %s, test mode = %s ", TEST_NODE_NAME, testkey, mode);
              _commonDll._LogDBError_SP (connection, "XML_GetOppXML", dbmsg, null, null, null, oppkey);
              _logger.error (dbmsg);    		  
    	  }
      } catch(Exception e)
      {
    	  // see reportxml_os.xsd file
    	  //<xs:element name="Test" minOccurs="1" maxOccurs="1">
    	  test = "<" + TEST_NODE_NAME + "/>";
    	  dbmsg = String.format ("Bad  XML Element %s: test key = %s, test mode = %s with error: %s", TEST_NODE_NAME, testkey, mode, e.getMessage());
          _commonDll._LogDBError_SP (connection, "XML_GetOppXML", dbmsg, null, null, null, oppkey);
          _logger.error (dbmsg);
      }
 
      StringBuilder strBlder = new StringBuilder ();
      try{
    	  
    	  strBlder.append("<").append(TESTEE_NODE_NAME).append(" key=\"").append (testeeKey).append ("\" />");
    	  
    	  testee 	= XML_GetTestee_F (connection, oppkey, debug);
    	  if(testee == null || testee.isEmpty())
    	  {
    		  testee = strBlder.toString();
        	  dbmsg = String.format ("XML Element %s is either empty or null: test key = %s, test mode = %s ", TESTEE_NODE_NAME, testkey, mode);
              _commonDll._LogDBError_SP (connection, "XML_GetOppXML", dbmsg, null, null, null, oppkey);
              _logger.error (dbmsg);    		  
    	  }

      } catch(Exception e)
      {
    	  // see reportxml_os.xsd file
    	  //<xs:element name="Examinee" minOccurs="1" maxOccurs="1">
    	  testee = strBlder.toString(); 
       	  dbmsg = String.format ("Bad  XML Element %s: test key = %s, test mode = %s with error: %s", TESTEE_NODE_NAME, testkey, mode, e.getMessage());
          _commonDll._LogDBError_SP (connection, "XML_GetOppXML", dbmsg, null, null, null, oppkey);
          _logger.error (dbmsg);
      }
      
      try{
    	  oppxml 	= _XML_GetOpportunity_SP (connection, oppkey, debug);
       	  if(oppxml == null || oppxml.isEmpty())
    	  {
       		  oppxml = "<" + OPPORTUNITY_NODE_NAME + "/>";
        	  dbmsg = String.format ("XML Element %s is either empty or null: test key = %s, test mode = %s ", OPPORTUNITY_NODE_NAME, testkey, mode);
              _commonDll._LogDBError_SP (connection, "XML_GetOppXML", dbmsg, null, null, null, oppkey);
              _logger.error (dbmsg);    		  
    	  }
      } catch(Exception e)
      {
    	  // see reportxml_os.xsd file
    	  //<xs:element name="Opportunity" minOccurs="1" maxOccurs="1">
    	  oppxml = "<" + OPPORTUNITY_NODE_NAME + "/>";
       	  dbmsg = String.format ("Bad  XML Element %s: test key = %s, test mode = %s with error: %s", OPPORTUNITY_NODE_NAME, testkey, mode, e.getMessage());
          _commonDll._LogDBError_SP (connection, "XML_GetOppXML", dbmsg, null, null, null, oppkey);
          _logger.error (dbmsg);
      }
      
      try{
    	  comments 	= _XML_GetOppComments_SP (connection, oppkey, debug);
      } catch(Exception e)
      {
    	  comments = "<" + COMMENT_NODE_NAME + "/>";
      	  dbmsg = String.format ("Bad  XML Element %s: test key = %s, test mode = %s with error: %s", COMMENT_NODE_NAME, testkey, mode, e.getMessage());
          _commonDll._LogDBError_SP (connection, "XML_GetOppXML", dbmsg, null, null, null, oppkey);
          _logger.error (dbmsg);
      }
      
      try{
    	  tools 	= XML_GetToolUsage_F (connection, oppkey, debug);
      } catch(Exception e)
      {
    	  tools = "<" + TOOLUSAGE_NODE_NAME + "/>";
      	  dbmsg = String.format ("Bad  XML Element %s: test key = %s, test mode = %s with error: %s", TOOLUSAGE_NODE_NAME, testkey, mode, e.getMessage());
          _commonDll._LogDBError_SP (connection, "XML_GetOppXML", dbmsg, null, null, null, oppkey);
          _logger.error (dbmsg);
      }
      
      if(	(test == null 		|| test.isEmpty())
		  &&(testee == null 	|| testee.isEmpty())
		  &&(oppxml == null 	|| oppxml.isEmpty())
		  &&(comments == null 	|| comments.isEmpty())
		  &&(tools == null 		|| tools.isEmpty())
		  ) // Only if all elements are either null or empty res is null
      {
    	  res = null;
          dbmsg = String.format ("Bad  XML Report for test key = %s, test mode = %s with error: %s", testkey, mode, "All elements are null");
          _commonDll._LogDBError_SP (connection, "XML_GetOppXML", dbmsg, null, null, null, oppkey);
          _logger.error (dbmsg);
      } else
      {
    	  strBuilder.append("<").append(TDS_REPORT_NODE_NAME).append(">").append(ls);
    	  strBuilder.append(test);
    	  strBuilder.append(testee).append(ls);
    	  strBuilder.append(oppxml).append(ls);
    	  if(comments != null && !comments.isEmpty())
    	  {
    		  strBuilder.append(comments).append(ls);
    	  }
    	  if(tools != null && !tools.isEmpty())
    	  {
    		  strBuilder.append(tools).append(ls);
    	  }
    	  strBuilder.append("</").append(TDS_REPORT_NODE_NAME).append(">");
      
    	  res = strBuilder.toString();
      }
      if (debug)
      {
        _logger.info ("Result: " + res);
      }
    } catch (Exception e)
    {
      res = null;
      String dbmsg = String.format ("Bad  XML Report for test key = %s, test mode = %s with error: %s", testkey, mode, e.getMessage());
      _commonDll._LogDBError_SP (connection, "XML_GetOppXML", dbmsg, null, null, null, oppkey);
      _logger.error (dbmsg);
      //throw new ReturnStatusException (e);
    }
    _commonDll._LogDBLatency_SP (connection, "XML_GetOppXML", starttime, null, true, null, oppkey);

    return res;
  }
  
  public String XML_GetTest_F (SQLConnection connection, String testkey, String mode, boolean debug) throws ReturnStatusException
  {
    StringBuilder strBuilder = new StringBuilder ();

    try {
      Long bankKey = TestBankKey_F (connection, testkey, debug);
      String gradeSpan = TestGradeSpan_F (connection, testkey, debug);
      String clientName = _commonDll.TestKeyClient_FN (connection, testkey);
      SingleDataResultSet result;
      DbResultRecord record;
      List<String> orderedColumns = new ArrayList<String> (Arrays.asList (
    		  getNodeAttributeName(TEST_NODE_NAME, "name"),				//"Test/@name",
    		  getNodeAttributeName(TEST_NODE_NAME, "subject"),			//"Test/@subject",
    		  getNodeAttributeName(TEST_NODE_NAME, "testId"),			//"Test/@testID",
    		  getNodeAttributeName(TEST_NODE_NAME, "bankKey"),			//"Test/@airbank",
    		  getNodeAttributeName(TEST_NODE_NAME, "contract"),			//"Test/@contract",
    		  getNodeAttributeName(TEST_NODE_NAME, "mode"),				//"Test/@mode",
    		  getNodeAttributeName(TEST_NODE_NAME, "grade"),			//"Test/@grade",
    		  getNodeAttributeName(TEST_NODE_NAME, "handScoreProject"),	//"Test/@handscoreproject",
    		  getNodeAttributeName(TEST_NODE_NAME, "assessmentType"),	//"Test/@assessmentType",
    		  getNodeAttributeName(TEST_NODE_NAME, "academicYear"),		//"Test/@academicYear",
    		  getNodeAttributeName(TEST_NODE_NAME, "assessmentVersion")	//"Test/@assessmentVersion"
          ));

      String academicYear = getAcademicYear(connection, testkey);
      
      String query = "select ${testkey} as \"Test/@name\", "
          + " coalesce(S.SubjectCode, B.Name, '') as \"Test/@subject\", "
          + " T.TestID as \"Test/@testId\",  "
          + " ${bankKey} as \"Test/@bankKey\", "
          + " P.HandScoreProject as \"Test/@handScoreProject\","
          + " T.Contract as \"Test/@contract\",  "
          + " ${mode} as \"Test/@mode\", "
          + " case when B.Grade is null or B.Grade = '' then ${gradeSpan} else B.Grade end as \"Test/@grade\"  "
          + " , \'\' as \"Test/@assessmentType\" "			// Added for new attributes TODO
          + " , ${academicYear} as \"Test/@academicYear\""			// Added for new attributes
          + " , case when TA.Updateconfig is null then TA.loadconfig else TA.Updateconfig  end as \"Test/@assessmentVersion\" "// Added for new attributes
          + " from ${ItemBankDB}.tblsetofadminsubjects T, ${ItemBankDB}.tblsubject B, "
          + " ${ConfigDB}.client_testproperties P,  ${ConfigDB}.client_subject S,"
          + " ${ItemBankDB}.tbltestadmin TA"	// Added for new attributes
          + " where T._fk_Subject = B._key and T._Key = ${testkey} "
          + " and S.ClientName = ${clientName} and S.Subject = B.Name and P.Clientname = S.ClientName "
          + " and P.TestiD = T.TestID "
          + " and T._fk_testadmin = TA._key"; // Added for new attributes

      SqlParametersMaps parameters = new SqlParametersMaps ().put ("testkey", testkey);
      parameters.put ("bankKey", bankKey).put ("gradeSpan", gradeSpan).put ("mode", mode).put ("clientName", clientName);
      parameters.put("academicYear", academicYear);
      result = executeStatement (connection, this.fixDataBaseNames (query), parameters, false).getResultSets ().next ();
      Iterator<DbResultRecord> resItr = result.getRecords ();
      while (resItr.hasNext ())
      {
        record = resItr.next ();
        recordToXML (record, orderedColumns, strBuilder, debug);
      }
    } catch (Exception e)
    {
    	String error = "Error occurs in XML_GetTest_F procedure: " + e.getMessage ();
        _logger.error (error);
        throw new ReturnStatusException (error);
    }
    return strBuilder.toString ();
  }
  
  public String XML_GetTestee_F (SQLConnection connection, UUID oppkey, boolean debug) throws ReturnStatusException
  {

    StringBuilder strBuilder = new StringBuilder ();
    try {
      SingleDataResultSet result;
      DbResultRecord record;

      String clientname = null;
      Long testee = null;
      
      String context = null;
      String query = "select clientname, _efk_Testee as testee from testopportunity where _key = ${oppkey}";

      SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey);
      result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
      record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        clientname = record.<String> get ("clientname");
        testee = record.<Long> get ("testee");
      }
      if(clientname == null || testee == null)
      {
    	  String error = "clientname = " + clientname + "; testee = " + testee + ": <Examinee> section will be empty";
    	  _logger.error(error);
    	  throw new ReturnStatusException (error);
      }

      strBuilder.append ("<").append(TESTEE_NODE_NAME);
      strBuilder.append(" key=\"").append (testee).append ("\" ");
      if(debug)
    	  strBuilder.append(" isDemo=\"").append (IS_DEMO).append ("\" ");
      
      if(testee < 0) // case when Examinee = "GUEST"
      {
    	  strBuilder.append("/>");
    	  return strBuilder.toString();
      }
      
      strBuilder.append (" >").append(ls);
      
      String initialContextDate = formatXSDateTime(getContextDate(connection, oppkey, INITIAL));
      String finalContextDate 	= formatXSDateTime(getContextDate(connection, oppkey, FINAL));

      context = INITIAL;
      strBuilder.append(getTesteeAttributes(_rtsReporting.getTesteeAttributes(connection, clientname, testee), context, initialContextDate));	      

      context = FINAL;	      
      strBuilder.append(getTesteeAttributes(_rtsReporting.getTesteeAttributes(connection, clientname, testee), context, finalContextDate));

      context = INITIAL;
      strBuilder.append(getTesteeRelationships(_rtsReporting.getTesteeRelationships(connection, clientname, testee), context, initialContextDate));

      context = FINAL;	      
      strBuilder.append(getTesteeRelationships(_rtsReporting.getTesteeRelationships(connection, clientname, testee), context, finalContextDate));
      
      strBuilder.append ("</").append(TESTEE_NODE_NAME).append(">");

    } catch (Exception e)
    {
    	String error = "Error occurs in XML_GetTestee_F procedure: " + e.getMessage ();
        _logger.error (error);
        throw new ReturnStatusException (error);
    }

    return strBuilder.toString ();
  }

  //
  public String _XML_GetOpportunity_SP (SQLConnection connection, UUID oppkey, boolean debug) throws ReturnStatusException
  {
    Date starttime = _dateUtil.getDateWRetStatus (connection);

    String res = null; // = oppxml
    // I won't to create temporary table. This table needs only to know order
    // number oppkey in the set order by opportunity
    // I will do that on the java side
    try {
      Long itemcount = null;
      Long ftcount = null;
      _Ref<Long> newReportingIdRef = new _Ref<> (); // this is reportingId !!!
      Date archived = null;
      String testid = null;
      String testkey = null;
      Long testee = null; // bigint
      String client = null;
      int winopp = 0;
      int winoppcnt = 0;
      String window = null;

      SingleDataResultSet result;
      DbResultRecord record;

      String query = "select _efk_testid as testid, _efk_AdminSubject as testkey, _efk_Testee as testee, clientname as client, "
          + "windowID as window, items_archived as archived from testopportunity where _Key = ${oppkey}";
      SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey);
      result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
      record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
    	testid = record.<String> get ("testid");
        testkey = record.<String> get ("testkey");
        testee = record.<Long> get ("testee");
        client = record.<String> get ("client");
        window = record.<String> get ("window");
        archived = record.<Date> get ("archived");
      }

      query = "select _Key from testopportunity  where clientname = ${client} "
          + " and _efk_Testee = ${testee} and _efk_AdminSubject = ${testkey} and datedeleted is null "
          + " and windowID = ${window} order by opportunity";
      parameters.put ("client", client).put ("testee", testee).put ("testkey", testkey).put ("window", window);

      result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
      Iterator<DbResultRecord> resItr = result.getRecords ();
      while (resItr.hasNext ())
      {
        record = resItr.next ();
        UUID key = record.<UUID> get ("_Key");
        winoppcnt++;
        if (debug)
        {
          _logger.info ("# = " + winoppcnt + "; _Key = " + key.toString ());
        }
        if (oppkey.equals (key))
        {// find max winoppcnt with this property
          winopp = winoppcnt;
        }
      }

      if (archived != null)
      {
        // select @ftcount = count(*) from TesteeResponseArchive
        // where _fk_TestOpportunity = @oppkey and _efk_ITSITem is not null and
        // IsFieldTest = 1
        //
        // select @itemcount = count(*) from TesteeResponseArchive
        // where _fk_TestOpportunity = @oppkey and _efk_ITSITem is not null

        query = "select count(*) as ftcnt from testeeresponsearchive "
            + "  where _fk_TestOpportunity = ${oppkey} and _efk_ITSITem is not null and IsFieldTest = 1";

        result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
        record = result.getCount () > 0 ? result.getRecords ().next () : null;
        if (record != null) {
          ftcount = record.<Long> get ("ftcnt");
        }

        query = "select count(*) as itemcount from testeeresponsearchive "
            + "  where _fk_TestOpportunity = ${oppkey} and _efk_ITSITem is not null";

        result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
        record = result.getCount () > 0 ? result.getRecords ().next () : null;
        if (record != null) {
          itemcount = record.<Long> get ("itemcount");
        }

      } else
      {
        // select @ftcount = count(*) from TesteeResponse
        // where _fk_TestOpportunity = @oppkey and _efk_ITSITem is not null and
        // IsFieldTest = 1 --and (isInactive is null or isInactive = 0);
        //
        // select @itemcount = count(*) from TesteeResponse
        // where _fk_TestOpportunity = @oppkey and _efk_ITSITem is not null
        // --and (isInactive is null or isInactive = 0);

        query = "select count(*) as ftcnt from testeeresponse"
            + "  where _fk_TestOpportunity = ${oppkey} and _efk_ITSITem is not null and IsFieldTest = 1";

        result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
        record = result.getCount () > 0 ? result.getRecords ().next () : null;
        if (record != null) {
          ftcount = record.<Long> get ("ftcnt");
        }

        query = "select count(*) as itemcount from testeeresponse"
            + "  where _fk_TestOpportunity = ${oppkey} and _efk_ITSITem is not null";

        result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
        record = result.getCount () > 0 ? result.getRecords ().next () : null;
        if (record != null) {
          itemcount = record.<Long> get ("itemcount");
        }
      }
      
      _commonDll._CreateClientReportingID_SP (connection, client, oppkey, newReportingIdRef);

      //String dbName = getAppSettings ().get ("TDSSessionDBName");// jdbc.url
      String dbName = getTdsSettings().getTDSSessionDBName ();
      
      //EF: we are using @@hostname in place of serverproperty('servername')
      //String url = getAppSettings ().get ("jdbc.url");
      
      // SB-512
      String effectiveDate = getEffectiveDate(connection, client, testid);  
        
      StringBuilder strBuilder = new StringBuilder ();

      List<String> orderedColumns = new ArrayList<String> (Arrays.asList (
          "@server",
          "@database",
          "@clientName",
          "@key",
          "@oppId",
          "@startDate",
          "@status",
          "@opportunity",
          "@statusDate",
          "@dateCompleted",
          "@pauseCount",
          "@itemCount",
          "@ftCount",
          "@abnormalStarts",
          "@gracePeriodRestarts",
          "@taId",
          "@taName",
          "@sessionId",
          "@windowId",
          "@windowOpportunity",
          "@dateForceCompleted",
          //SB-999
          //"@qaLevel", // qaLevel is n/a for OSS.  email from Adam Thu 1/8/2015 3:28 PM
          "@assessmentParticipantSessionPlatformUserAgent",
          // SB-512
          "@effectiveDate"
          ));

      query = "select @@hostname as \"@server\", " // was:
                                               // serverproperty('servername')
          + " ${dbName} as \"@database\", "
          + " O.clientname as \"@clientName\", "
          + " O._Key as\"@key\", "
          + " ${reportingID} as \"@oppId\", "
          + " DateStarted as \"@startDate\", "
          + " O.status as \"@status\", "
          + " opportunity as \"@opportunity\", "
          + " O.DateChanged as \"@statusDate\", "
          + " DateCompleted as \"@dateCompleted\", "
          + " Restart as \"@pauseCount\", "
          + " ${itemcount} as \"@itemCount\", "
          + " ${ftcount} as \"@ftCount\", "
          + " abnormalStarts as \"@abnormalStarts\", "
          + " gracePeriodRestarts as \"@gracePeriodRestarts\", "
          + " ProctorID as \"@taId\", "
          + " S.ProctorName as \"@taName\", "
          + " sessionID as \"@sessionId\", "
          + " windowID as \"@windowId\", "
          + " ${winopp} as \"@windowOpportunity\","
          + " O.dateForceCompleted as \"@dateForceCompleted\", "
          + " '' as \"@qaLevel\", "											// TODO Hardcoded value SB-999
          + " '' as \"@assessmentParticipantSessionPlatformUserAgent\", "	// TODO Hardcoded value
          + " ${effectiveDate} as \"@effectiveDate\" "
          + " from testopportunity O, session S where O._Key = ${oppkey}"
          + " and O._fk_Session = S._Key";

      parameters.put ("itemcount", itemcount).put ("ftcount", ftcount).put("reportingID", newReportingIdRef.get())
          .put ("winopp", winopp).put ("dbName", dbName).put("effectiveDate", effectiveDate);
      result = executeStatement (connection, this.fixDataBaseNames (query), parameters, false).getResultSets ().next ();
      resItr = result.getRecords ();
      String opps = OPPORTUNITY_NODE_NAME;
      String oppRB = "</" + opps + ">";
      while (resItr.hasNext ())
      {
        record = resItr.next ();
        recordToXML (record, orderedColumns, strBuilder, opps, debug);
      }
      res = strBuilder.toString ();
      
      //Alex needs to test case when where clause in the above statement 
      // does not return any rows
      // In any case we need to have xml root like <oportynity .... />
      if(res == null || res.isEmpty ())
      {
        res = "<" + opps + " server=\"" // server value is empty
            + "\" database=\"" + dbName 
            + "\" itemcount=\"" + itemcount + "\" ftcount=\"" + ftcount 
            + "\" windowOpportunity=\"" + winopp + "\" " + "/>";
      }

      String segmentsXML 		= XML_GetSegments_F (connection, oppkey, debug);
      String accomodationsXML 	= XML_GetAccomodations_F (connection, oppkey, debug);
      String scoresXML 			= XML_GetScores_F (connection, oppkey, debug);
      String items 				= _XML_GetOpportunityItems_SP (connection, oppkey, debug);
      // cut </opportunity>
      int indexOpp = res.indexOf (oppRB);
      StringBuilder oppstrBldr = new StringBuilder();
      if (indexOpp < 0)
      {
        indexOpp = res.lastIndexOf ("/>");
        if (indexOpp > 0)
        {
        	oppstrBldr.append(res.substring (0, indexOpp)).append(">").append(ls);
        	res = oppstrBldr.toString();
//          res = res.substring (0, indexOpp);
//          res = res + ">" + ls;
        }
        else
        {
        	throw new ReturnStatusException("Structure of xml: " + res + " is not correct");
        }
      }
      else
      {
    	  oppstrBldr.append(res.substring (0, indexOpp)).append(ls);
//        res = res.substring (0, indexOpp);
      }
      oppstrBldr.append(segmentsXML);
      oppstrBldr.append(accomodationsXML);
      oppstrBldr.append(scoresXML);
      oppstrBldr.append(items);
      oppstrBldr.append(oppRB);
      res = oppstrBldr.toString();
//      res = res + segmentsXML + accomodationsXML + scoresXML + items;
//      res = res + oppRB;
    } catch (Exception e)
    {
    	String error = "Error occurs in _XML_GetOpportunity_SP procedure: " + e.getMessage ();
        _logger.error (error);
        throw new ReturnStatusException (error);
    }
    _commonDll._LogDBLatency_SP (connection, "_XML_GetOpportunity", starttime, null, true, null, oppkey);
    return res;
  }

  //
  public String _XML_GetOppComments_SP (SQLConnection connection, UUID oppkey, boolean debug) throws ReturnStatusException
  {
    Date starttime = _dateUtil.getDateWRetStatus (connection);

    String res = "";

    // set @result = '';
    // declare @indx int, @cmnt nvarchar(max);
    // declare @comments table (indx int primary key not null identity(1,1),
    // cmnt nvarchar(max));
    // insert into @comments (cmnt)
    // select
    // '<comment context="' + coalesce(context, '')
    // + '" itemposition="' + coalesce(cast(itemposition as varchar(4)), '')
    // + '" date="' + convert(nvarchar(30), _date, 126)
    // + '"><![CDATA[' + comment + ']]></comment>'
    // from TesteeComment
    // where _fk_TestOpportunity = @oppkey and comment is not null
    // order by itemPosition, _date;
    //
    // while (exists (select * from @comments)) begin
    // select top 1 @indx = indx, @cmnt = cmnt from @comments;
    // delete from @comments where indx = @indx;
    // set @result = @result + @cmnt;
    // end

    try {
      SingleDataResultSet result;
      DbResultRecord record;

      List<String> orderedColumns = new ArrayList<String> (Arrays.asList (
          "@context",
          "@itemPosition",
          "@date"));

      String query = "select context as \"@context\", "
          + " itemposition as \"@itemPosition\", "
          + " _date  as \"@date\", "
          + " comment "
          + " from testeecomment where _fk_TestOpportunity = ${oppkey} and comment is not null "
          + " order by itemPosition, _date";

      SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey);
      result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
      Iterator<DbResultRecord> resItr = result.getRecords ();
      String resCurr = null;
      String com = COMMENT_NODE_NAME;
      String comRB = "</" + com + ">";
      String cmmnt = null;
      while (resItr.hasNext ())
      {
        StringBuilder strBuilder = new StringBuilder ();
        record = resItr.next ();
        recordToXML (record, orderedColumns, strBuilder, com, debug);
        resCurr = strBuilder.toString ();
        cmmnt = record.<String> get ("comment");
        // cut "/>"
        int indexCom = resCurr.indexOf ("/>");
        resCurr = resCurr.substring (0, indexCom);
        resCurr = resCurr + "><![CDATA[" + cmmnt + "]]>";
        // add </comment>
        res = res + resCurr + comRB;
      }
    } catch (Exception e)
    {
    	String error = "Error occurs in _XML_GetOppComments_SP procedure: " + e.getMessage ();
        _logger.error (error);
        throw new ReturnStatusException (error);
    }
    _commonDll._LogDBLatency_SP (connection, "_XML_GetOppComments", starttime, null, true, null, oppkey);

    return res;
  }

  // returns @items
  public String _XML_GetOpportunityItems_SP (SQLConnection connection, UUID oppkey, boolean debug) throws ReturnStatusException
  {
    Date starttime = _dateUtil.getDateWRetStatus (connection);

    StringBuilder resStrBuilder = new StringBuilder ();
    DataBaseTable pagesTable = null;
    DataBaseTable itemsTable = null;
    Date dateArchived = null;
    String status = null;
    String xml = null;
    String itemkey = null;
    String itemtype = null;
    String response = null;
    String scoreStatus = null;
    String scorePoint = null;
    String scoredimension  = null;
    String maxscore = null;
    Date responseDate = null;
    String respDate = null;
    String rationale = null;
    try {
      SingleDataResultSet result;
      DbResultRecord record;

      String query = "select items_archived as dateArchived, status"
          + " from testopportunity where _Key = ${oppkey}";
      SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey);
      result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
      record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        dateArchived = record.<Date> get ("dateArchived");
        status = record.<String> get ("status");
      }      
      _logger.info("status = " + status);

      pagesTable = createTMPPagesTable (connection, oppkey, debug);
      itemsTable = createTMPItemsTable (connection, oppkey, dateArchived, debug);

      Map<String, String> unquotedparms = new HashMap<String, String> ();
      unquotedparms.put ("pagestblName", pagesTable.getTableName ());
      unquotedparms.put ("itemstblName", itemsTable.getTableName ());

      if (debug)
      {
        // output pages table
        query = "select * from ${pagestblName}";
        result = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, true).getResultSets ().next ();
        Iterator<DbResultRecord> resItr = result.getRecords ();
        while (resItr.hasNext ())
        {
          record = resItr.next ();
          dumpRecord (record);
        }
        // output items table
        query = "select * from ${itemstblName}";
        result = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, true).getResultSets ().next ();
        resItr = result.getRecords ();
        while (resItr.hasNext ())
        {
          record = resItr.next ();
          dumpRecord (record);
        }
      }
      // SB-439: XML Item format does not depend from item format ("MC" or not) 
      StringBuilder strBuilder = new StringBuilder ();
      List<String> orderedColumns = new ArrayList<String> (Arrays.asList (
          "Item/@position",
          "Item/@segmentId",
          "Item/@bankKey",
          "Item/@key",
          "Item/@operational",
          "Item/@isSelected",
          "Item/@format",
         // "Item/@scorePoints",
          "Item/@score",
          "Item/@scoreStatus",
          "Item/@adminDate",
         // "Item/@answerKey",
          "Item/@numberVisits",
          "Item/@mimeType",
          "Item/@clientId",
          "Item/@strand",
          "Item/@contentLevel",
          "Item/@pageNumber",
          "Item/@pageVisits",
          "Item/@pageTime",
          "Item/@dropped"
          ));

      String querySuffix = null;
      String queryPreffix1 = "select R.position as \"Item/@position\", S._efk_Segment as \"Item/@segmentId\", "
            + "R._efk_ITSBank as \"Item/@bankKey\", R._efk_ITSItem as \"Item/@key\", "
            + " cast((1 - R.IsFieldTest) as SIGNED) as \"Item/@operational\", "
            + " IsSelected as \"Item/@isSelected\", "
            + " Format as \"Item/@format\",  "
            + " I.scorepoints as \"Item/@scorePoints\",  "
            + " R.score as \"Item/@score\", "
            + " upper(R.scorestatus) as \"Item/@scoreStatus\", "
            + " R.DateGenerated as \"Item/@adminDate\", "
            + " R.answer as \"Item/@answerKey\",  R.numUpdates as \"Item/@numberVisits\", ";

      String queryPreffix3 =  " I.mimetype as \"Item/@mimeType\", "
            + " I.clientID as \"Item/@clientId\", "
            + " I.strand as \"Item/@strand\","
            + " I.contentLevel as \"Item/@contentLevel\", "
            + " R.page as \"Item/@pageNumber\", "
            + " P.visits as \"Item/@pageVisits\", "
            + " P.dwell as \"Item/@pageTime\",  "
            + " case when R.IsInactive = 1 or notforScoring = 1 then 1 else 0 end as \"Item/@dropped\" ";
        
      String queryPreffix = queryPreffix1 
    		  //+ queryPreffix2 
    		  +  queryPreffix3;

      query = "select * from ${itemstblName}";
      result = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, false).getResultSets ().next ();
      Iterator<DbResultRecord> resItr = result.getRecords ();
 
      while (resItr.hasNext ())
      {

        strBuilder = new StringBuilder ();
        record = resItr.next ();
        itemkey = record.<String> get ("_key");
        parameters.put ("itemkey", itemkey);
        itemtype = record.<String> get ("itemtype");

        queryPreffix = queryPreffix1 + queryPreffix3; 

        querySuffix = " inner join "
            + " testopportunitysegment S  on "
            + " S._fk_TestOpportunity = ${oppkey} "
            + " and R.segment = S.segmentPosition "
            + " left join "
            + " ${pagestblName} P on "
            + " P.pagenum = R.page "
            + " left join"
            + " ${itemstblName} I on "
            + " I._key = ${itemkey} "
            + " where R._fk_TestOpportunity = ${oppkey} "
            + " and R._efk_ItemKey = ${itemkey} "
            + " order by position";
        if (dateArchived == null)
        {
          query = queryPreffix + " from testeeresponse R " + querySuffix;
        }
        else
        {
          query = queryPreffix + " from testeeresponsearchive R " + querySuffix;
        }

        SingleDataResultSet result2 = executeStatement (connection,
            fixDataBaseNames (query, unquotedparms), parameters, false).getResultSets ().next ();
        Iterator<DbResultRecord> resItr2 = result2.getRecords ();
        DbResultRecord record2 = null;
        while (resItr2.hasNext ())
        {
          record2 = resItr2.next ();
          recordToXML (record2, orderedColumns, strBuilder, debug);
        }
        query = "select coalesce(response, '') as response, coalesce(scoreRationale, '') as rationale, datesubmitted as responseDate,  "
        		+ " I.scorepoints as scorePoint,"
        		+ " R.scoredimensions as scoreDimension, "
        		+ " R.scorestatus as scoreStatus ";
// TODO: (AK) We need to add the column scoredimensions to table  testeeresponsearchive !!!       
//        if (dateArchived == null)
//        {
          query = query + " from testeeresponse R ";
//        }
//        else
//        {
//          query = query + " from testeeresponsearchive R ";
//        }

        query = query 
                + " left join"
                + " ${itemstblName} I on "
                + " I._key = ${itemkey} "
        		+ " where _fk_testOpportunity = ${oppkey} and _efk_Itemkey = ${itemkey}";

        result2 = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, false).getResultSets ().next ();
        record2 = result2.getCount () > 0 ? result2.getRecords ().next () : null;
        if (record2 != null) {
          response 		= record2.<String> get ("response");
          rationale 	= record2.<String> get ("rationale");
          responseDate 	= record2.<Date> get ("responseDate");
          respDate 		= (responseDate != null)?  responseDate.toString(): "";
          respDate		= formatXSDateTime(respDate);
          scoreStatus   = record2.<String> get ("scoreStatus");
          Integer spoint	= record2.<Integer> get ("scorePoint");
          scorePoint = spoint.toString();
          scoredimension= record2.<String> get ("scoreDimension");
        }
        else
        {
          response 	= "";
          rationale = "";
          respDate 	= "";
        }
        scoreStatus 	= formatValueToXSD(scoreStatus, "scoreStatus");
        scorePoint 		= formatValueToXSD(scorePoint, "scorePoint");
        scoredimension 	= formatValueToXSD(scoredimension, "scoreDimension");
        //TODO maxscore????
        maxscore 	= formatValueToXSD(maxscore, "maxScore");


        xml = strBuilder.toString ();
        if (xml != null && !xml.isEmpty ())
        {
          int indexEnd = xml.lastIndexOf ("/>");
          if (indexEnd > 0)
          {
        	strBuilder = new StringBuilder ();
            strBuilder.append(xml.substring (0, indexEnd));
            strBuilder.append(">").append(ls);
            strBuilder.append("<").append(RESPONSE_NODE_NAME).append(" date =").append("\"").append(respDate).append("\"").append(">");
	            if(!(itemtype.equalsIgnoreCase("MC")||itemtype.equalsIgnoreCase("MS"))) {
	            	strBuilder.append("<![CDATA[ ");  
	            }            
	            	strBuilder.append(response);            
	            if(!(itemtype.equalsIgnoreCase("MC")||itemtype.equalsIgnoreCase("MS"))) {
	            	strBuilder.append(" ]]> ");
	            }
            strBuilder.append("</").append(RESPONSE_NODE_NAME).append(">").append(ls);
            if(rationale != null && !rationale.isEmpty())
            {
            	strBuilder.append(getScoreInfo(rationale, scorePoint, 
            			scoreStatus, scoredimension, maxscore));
            }
            
            strBuilder.append("</").append(ITEM_NODE_NAME).append(">").append(ls);
          }
        }
        resStrBuilder.append(strBuilder.toString());
        if (debug)
        {
          _logger.info ("xml: " + xml);
        }
      }
	} catch (Exception e) {
		String error = "Error occurs in _XML_GetOpportunityItems_SP procedure: " + e.getMessage();
		_logger.error(error);
		throw new ReturnStatusException(error);
	} finally {
		connection.dropTemporaryTable(pagesTable);
		connection.dropTemporaryTable(itemsTable);
	}
    _commonDll._LogDBLatency_SP (connection, "_XML_GetOpportunityItems", starttime, null, true, null, oppkey);
    return resStrBuilder.toString();
  }
  
  protected String getScoreInfo(String rationale,
		  String scorePointValue,
		  String scoreStatusValue,
		  String scoreDimensionValue,
		  String maxScoreValue)
  {
	StringBuilder strBuilder = new StringBuilder();

	String confLevelValue = "";
	
	strBuilder.append("<").append(SCOREINFO_NODE_NAME);
		strBuilder.append(SPACE).append("scorePoint = \"").append(scorePointValue).append("\" ");
		strBuilder.append(SPACE).append("maxScore = \"").append(maxScoreValue).append("\" ");
		strBuilder.append(SPACE).append("scoreDimension = \"").append(scoreDimensionValue).append("\" ");
		strBuilder.append(SPACE).append("scoreStatus = \"").append(scoreStatusValue).append("\" ");
		strBuilder.append(SPACE).append("confLevel = \"").append(confLevelValue).append("\" ");	
	strBuilder.append(">").append(ls);
	
    	strBuilder.append(SPACE).append("<").append(SCORERATIONALE_NODE_NAME).append(">").append(ls);
    	strBuilder.append(SPACE).append("<").append(SCOREINFOMESSAGE_NODE_NAME).append(">").append(ls);
   	
    		strBuilder.append(SPACE).append(SPACE).append(rationale);
        	
    	strBuilder.append(SPACE).append("</").append(SCOREINFOMESSAGE_NODE_NAME).append(">").append(ls);	
    	strBuilder.append(SPACE).append("</").append(SCORERATIONALE_NODE_NAME).append(">").append(ls);
    
    strBuilder.append("</").append(SCOREINFO_NODE_NAME).append(">").append(ls);
	  
	  
	return  strBuilder.toString(); 
  }

  // @Test
  public String ItemkeyStrandName_F (SQLConnection connection, String testkey, String itemkey, boolean debug) throws ReturnStatusException
  {
    String res = null;
    String contentLevel = null;
    int pos = 0;
    try {
      SingleDataResultSet result;
      DbResultRecord record;

      String query = "select name "
          + " from ${ItemBankDB}.tblsetofitemstrands S, ${ItemBankDB}.tblstrand D "
          + " where _fk_Item = ${itemkey} and S._fk_Strand = D._Key and _fk_AdminSubject = ${testkey}";
      SqlParametersMaps parameters = new SqlParametersMaps ().put ("itemkey", itemkey).put ("testkey", testkey);
      result = executeStatement (connection, fixDataBaseNames (query), parameters, false).getResultSets ().next ();
      record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        contentLevel = record.<String> get ("name");
      }

      pos = contentLevel.indexOf ("|");
      if (pos < 0)
      {
        res = contentLevel;
      }
      else
      {
        res = contentLevel.substring (0, pos);
      }

    } catch (Exception e)
    {
		String error = "Error occurs in ItemkeyStrandName_F procedure: " + e.getMessage();
		_logger.error(error);
		throw new ReturnStatusException(error);
    }
    return res;
  }

  //
  public String XML_GetAccomodations_F (SQLConnection connection, UUID oppkey, boolean debug) throws ReturnStatusException
  {
    StringBuilder strBuilder = new StringBuilder ();
    try {
      SingleDataResultSet result;
      DbResultRecord record;
      List<String> orderedColumns = new ArrayList<String> (Arrays.asList ("Accommodation/@type",
          "Accommodation/@value",
          "Accommodation/@code",
          "Accommodation/@segment"
//AK: (2014-11-13)    There are not these attributes in new reportxml_os.xsd file. See SB-971.      
//          ,
//          "Accommodation/@context",
//          "Accommodation/@contextDate"
          ));

      String query = "select AccType as \"Accommodation/@type\", AccValue as \"Accommodation/@value\", "
          + " AccCode as \"Accommodation/@code\", Segment as \"Accommodation/@segment\", "
          + " \"INITIAL\" as \"Accommodation/@context\", _date as \"Accommodation/@contextDate\" "
          + " from testeeaccommodations where _fk_TestOpportunity = ${oppkey} order by Segment, AccCode";
      SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey);
      result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
      Iterator<DbResultRecord> resItr = result.getRecords ();
      while (resItr.hasNext ())
      {
        record = resItr.next ();
        recordToXML (record, orderedColumns, strBuilder, debug);
      }
    } catch (Exception e)
    {
		String error = "Error occurs in XML_GetAccomodations_F procedure: " + e.getMessage();
		_logger.error(error);
		throw new ReturnStatusException(error);
    }

    return strBuilder.toString ();
  }

  //
  public String XML_GetScores_F (SQLConnection connection, UUID oppkey, boolean debug) throws ReturnStatusException
  {
    StringBuilder strBuilder = new StringBuilder ();
    try {
      SingleDataResultSet result;
      DbResultRecord record;
      List<String> orderedColumns = new ArrayList<String> (Arrays.asList ("Score/@value",
          "Score/@standardError",
          "Score/@measureLabel",
          "Score/@measureOf"));

      String query = "select measureof as \"Score/@measureOf\", measurelabel as \"Score/@measureLabel\", coalesce(value, '') as \"Score/@value\", "
          + " standarderror as \"Score/@standardError\" "
          + " from testopportunityscores where _fk_TestOpportunity = ${oppkey}";
      SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey);
      result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
      Iterator<DbResultRecord> resItr = result.getRecords ();
      while (resItr.hasNext ())
      {
        record = resItr.next ();
        recordToXML (record, orderedColumns, strBuilder, debug);
      }
    } catch (Exception e)
    {
		String error = "Error occurs in XML_GetScores_F procedure: " + e.getMessage();
		_logger.error(error);
		throw new ReturnStatusException(error);
    }

    return strBuilder.toString ();

  }

  //
  public String XML_GetSegments_F (SQLConnection connection, UUID oppkey, boolean debug) throws ReturnStatusException
  {
    // declare @result XML;
    //
    // select @result =
    // (select _efk_Segment as "segment/@ID", SegmentPosition as
    // "segment/@position", formkey as "segment/@formkey",
    // formID as "segment/@formID", algorithm as "segment/@algorithm"
    // from TestOpportunitySegment where _fk_TestOpportunity = @oppkey order by
    // segmentPosition FOR XML PATH(''));
    //
    // return @result;

    StringBuilder strBuilder = new StringBuilder ();
    try {
      SingleDataResultSet result;
      DbResultRecord record;
      List<String> orderedColumns = new ArrayList<String> (Arrays.asList ("Segment/@id",
          "Segment/@position",
          "Segment/@formId",
          "Segment/@formKey",
          "Segment/@algorithm",
          "Segment/@algorithmVersion"));// SB-439: See https://docs.google.com/spreadsheet/ccc?key=0ArK0Ai9lGDb9dHZkcUw2bXFodi0ybmJSY1FtcnlCQVE&usp=sharing

      String query = "select  _efk_Segment as \"Segment/@id\", SegmentPosition as \"Segment/@position\", "
          + " formkey as \"Segment/@formKey\",  formID as \"Segment/@formId\", algorithm as \"Segment/@algorithm\","
          + " 0 as   \"Segment/@algorithmVersion\""
          + " from testopportunitysegment where _fk_TestOpportunity = ${oppkey} order by segmentPosition";
      SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey);
      result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
      Iterator<DbResultRecord> resItr = result.getRecords ();
      while (resItr.hasNext ())
      {
        record = resItr.next ();
        recordToXML (record, orderedColumns, strBuilder, debug);
      }
    } catch (Exception e)
    {
		String error = "Error occurs in XML_GetSegments_F procedure: " + e.getMessage();
		_logger.error(error);
		throw new ReturnStatusException(error);
    }

    return strBuilder.toString ();
  }

  //
  public String XML_GetToolUsage_F (SQLConnection connection, UUID oppkey, boolean debug) throws ReturnStatusException
  {
    // declare @result XML;
    // declare @clientname varchar(100), @testID varchar(100);
    // select @clientname = clientname, @testID = _efk_TestID
    // from TestOpportunity where _Key = @oppkey;
    //
    // select @result =
    // (select O.toolType as "toolusage/@type", O.toolCode as "toolusage/@code",
    // itempage as "toolusage/toolpage/@page", groupID as
    // "toolusage/toolpage/@groupID", count(*) as "toolusage/toolpage/@count"
    // from TestoppToolsUsed O, TDSCONFIGS_Client_ToolUsage U
    // where _fk_TestOpportunity = @oppkey and clientname = @clientname and
    // testID = @testID and O.tooltype = U.tooltype and U.reportUsage = 1
    // group by O.tooltype, O.toolCode, O.itempage, O.groupID
    // order by O.tooltype, O.itempage
    // for XML PATH(''));

    StringBuilder strBuilder = new StringBuilder ();
    try {
      SingleDataResultSet result;
      DbResultRecord record;

      String clientname = null;
      String testID = null;

      String query = "select clientname, _efk_TestID as testID from testopportunity where _Key = ${oppkey}";

      SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey);
      result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
      record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        clientname = record.<String> get ("clientname");
        testID = record.<String> get ("testID");
      }

      List<String> orderedColumns = new ArrayList<String> (Arrays.asList ("Toolusage/@type",
          "Toolusage/@code",
          "Toolusage/Toolpage/@page",
          "Toolusage/Toolpage/@groupId",
          "Toolusage/Toolpage/@count"));

      query = "select O.toolType as \"Toolusage/@type\", O.toolCode as \"Toolusage/@code\", "
          + " itempage as \"Toolusage/Toolpage/@page\", groupID as \"Toolusage/Toolpage/@groupId\","
          + " count(*) as \"Toolusage/Toolpage/@count\" "
          + " from testopptoolsused O, ${ConfigDB}.client_toolusage U  "
          + " where _fk_TestOpportunity = ${oppkey} and clientname = ${clientname} "
          + " and testID = ${testID} and O.tooltype = U.tooltype and U.reportUsage = 1 "
          + " group by O.tooltype, O.toolCode, O.itempage, O.groupID "
          + " order by O.tooltype, O.itempage";

      parameters.put ("clientname", clientname).put ("testID", testID);
      result = executeStatement (connection, fixDataBaseNames (query), parameters, false).getResultSets ().next ();
      Iterator<DbResultRecord> resItr = result.getRecords ();
      while (resItr.hasNext ())
      {
        record = resItr.next ();
        recordToXML (record, orderedColumns, strBuilder, debug);
      }
    } catch (Exception e)
    {
		String error = "Error occurs in XML_GetToolUsage_F procedure: " + e.getMessage();
		_logger.error(error);
		throw new ReturnStatusException(error);
    }
    return strBuilder.toString ();
  }

  public Long TestBankKey_F (SQLConnection connection, String testkey, boolean debug) throws ReturnStatusException
  {
    // declare @bankKey bigint;
    //
    // select top 1 @bankKey = _efk_ItemBank
    // from tblSetofAdminSubjects S, tblSetofAdminItems A, tblItem I
    // where S._Key = @testkey and A._fk_AdminSubject = S._Key and I._Key =
    // A._fk_Item;
    //
    // if (@bankkey is null)
    // select top 1 @bankKey = _efk_ItemBank
    // from tblSetofAdminSubjects S, tblSetofAdminItems A, tblItem I
    // where S.VirtualTest = @testkey and A._fk_AdminSubject = S._Key and I._Key
    // = A._fk_Item;
    //
    // return @bankKey;
    Long bankKey = null;

    try {
      SingleDataResultSet result;
      DbResultRecord record;
      String query = "select _efk_ItemBank as bankKey "
          + " from ${ItemBankDB}.tblsetofadminsubjects S, ${ItemBankDB}.tblsetofadminitems A, ${ItemBankDB}.tblitem I "
          + " where S._Key = ${testkey} and A._fk_AdminSubject = S._Key and I._Key = A._fk_Item limit 1";

      SqlParametersMaps parameters = new SqlParametersMaps ().put ("testkey", testkey);
      result = executeStatement (connection, fixDataBaseNames (query), parameters, false).getResultSets ().next ();
      record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        bankKey = record.<Long> get ("bankKey");
      }
      if (bankKey == null)
      {
        query = "select _efk_ItemBank as bankKey "
            + " from ${ItemBankDB}.tblsetofadminsubjects S, ${ItemBankDB}.tblsetofadminitems A, ${ItemBankDB}.tblitem I "
            + " where S.VirtualTest =  ${testkey} and A._fk_AdminSubject = S._Key and I._Key = A._fk_Item limit 1";

        result = executeStatement (connection, fixDataBaseNames (query), parameters, false).getResultSets ().next ();
        record = result.getCount () > 0 ? result.getRecords ().next () : null;
        if (record != null) {
          bankKey = record.<Long> get ("bankKey");
        }
      }
    } catch (Exception e)
    {
		String error = "Error occurs in TestBankKey_F procedure: " + e.getMessage();
		_logger.error(error);
		throw new ReturnStatusException(error);
    }
    if (bankKey == null)
    {
      bankKey = Long.MIN_VALUE;
    }
    return bankKey;
  }

  public String TestGradeSpan_F (SQLConnection connection, String testkey, boolean debug) throws ReturnStatusException
  {
    String res = null;

    // select grade, case when IsNumeric(grade) = 1 then cast(grade as int) else
    // null end
    // from SetofTestGrades where _fk_AdminSubject = @testkey;

    List<String> grades = new ArrayList<String> ();
    Set<String> grdes = new TreeSet<String> ();
    Set<Integer> diffGrades = new TreeSet<Integer> ();
    String grade = null;
    Integer g = null;
    int numgrades = 0;
    int numintgrades = 0;
    int mingrade = Integer.MAX_VALUE;
    int maxgrade = Integer.MIN_VALUE;

    try {
      SingleDataResultSet result;
      DbResultRecord record;
      String query = "select grade from ${ItemBankDB}.setoftestgrades where _fk_AdminSubject =${testkey} ";

      SqlParametersMaps parameters = new SqlParametersMaps ().put ("testkey", testkey);
      result = executeStatement (connection, fixDataBaseNames (query), parameters, false).getResultSets ().next ();

      Iterator<DbResultRecord> resItr = result.getRecords ();
      while (resItr.hasNext ())
      {
        record = resItr.next ();
        grade = record.<String> get ("grade");
        grades.add (grade);
        try {
          g = Integer.valueOf (grade);
        } catch (NumberFormatException e) {
          // to do nothing
        }
        numgrades++;
        if (g != null)
        {
          numintgrades++;
          mingrade = Math.min (mingrade, g);
          maxgrade = Math.max (maxgrade, g);
          diffGrades.add (g);
        }
      }
      if (numgrades == 0)
      {
        res = "";
      } else if (numgrades == 1)
      {
        res = grades.get (0);
      }
      else if (numgrades == numintgrades)
      {
        if (mingrade == 9 && maxgrade == 12 && numgrades == 4)
          res = "HS";
        else if (maxgrade - mingrade + 1 == numintgrades)
          res = Integer.toString (mingrade) + "-" + Integer.toString (maxgrade);
        else // return all grades in increase order as int
        {
          res = "";
          boolean isFirstTime = true;
          for (Integer gr : diffGrades)
          {
            if (isFirstTime)
            {
              res = res + Integer.toString (gr);
              isFirstTime = false;
            }
            else
            {
              res = res + ", " + Integer.toString (gr);
            }
          }
        }
      } else // return all grades in increase order as String
      {
        res = "";
        boolean isFirstTime = true;
        for (String gr : grdes)
        {
          if (isFirstTime)
          {
            res = res + gr;
            isFirstTime = false;
          }
          else
          {
            res = res + ", " + gr;
          }
        }
      }
    } catch (Exception e)
    {
		String error = "Error occurs in TestGradeSpan_F procedure: " + e.getMessage();
		_logger.error(error);
		throw new ReturnStatusException(error);
    }

    return res;
  }

  
  // ===================Helping functions======================================
  //
  public DataBaseTable createTMPPagesTable (SQLConnection connection,
      UUID oppkey, boolean debug) throws ReturnStatusException
  {
    // create table #latencies (pagenum int, visits int, dwell bigint);
    DataBaseTable latenciesDBTable = getDataBaseTable ("latencies")
        .addColumn ("pagenum", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("visits", SQL_TYPE_To_JAVA_TYPE.BIGINT)
        .addColumn ("dwell", SQL_TYPE_To_JAVA_TYPE.BIGINT);
    connection.createTemporaryTable (latenciesDBTable);

    Map<String, String> unquotedparms = new HashMap<String, String> ();
    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("oppkey", oppkey);

    // insert into #latencies (pagenum, visits, dwell)
    // select page, sum(visitCount), sum(visitTime)
    // from ClientLatency with(nolock)
    // where _fk_TestOpportunity = @oppkey and page is not null
    // group by page;
    String query = "insert into ${latenciestblName} (pagenum, visits, dwell) "
        + " select page, sum(visitCount), sum(visitTime) "
        + " from clientlatency "
        + " where _fk_TestOpportunity = ${oppkey} and page is not null group by page";

    unquotedparms.put ("latenciestblName", latenciesDBTable.getTableName ());
    int insertedCnt = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, true).getUpdateCount ();
    if (debug)
    {
      _logger.info ("Insert count after Insert query in temporary table latencies is " + insertedCnt);
    }
    // insert into #latencies (pagenum, visits, dwell)
    // select page, sum(visitCount), sum(visitTime)
    // from ClientLatencyArchive with(nolock)
    // where _fk_TestOpportunity = @oppkey and page is not null
    // group by page;
    query = "insert into ${latenciestblName} (pagenum, visits, dwell) "
        + " select page, convert (sum(visitCount), SIGNED), convert (sum(visitTime), SIGNED) "
        + " from clientlatencyarchive "
        + " where _fk_TestOpportunity = ${oppkey} and page is not null group by page";

    insertedCnt = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, true).getUpdateCount ();
    if (debug)
    {
      _logger.info ("Insert count after Insert query in temporary table latencies is " + insertedCnt);
    }
    // create table #pages (pagenum int not null primary key, visits int, dwell
    // bigint);
    DataBaseTable pagesDBTable = getDataBaseTable ("pages")
        .addColumn ("pagenum", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("visits", SQL_TYPE_To_JAVA_TYPE.BIGINT)
        .addColumn ("dwell", SQL_TYPE_To_JAVA_TYPE.BIGINT);
    connection.createTemporaryTable (pagesDBTable);

    unquotedparms.put ("pagestblName", pagesDBTable.getTableName ());
    // insert into #pages (pagenum, visits, dwell)
    // select pagenum, sum(visits), sum(dwell)
    // from #latencies
    // group by pagenum;

    query = "insert into ${pagestblName} (pagenum, visits, dwell) "
        + " select pagenum, convert (sum(visits), SIGNED), convert (sum(dwell), SIGNED) "
        + " from ${latenciestblName} "
        + " group by pagenum";

    insertedCnt = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, true).getUpdateCount ();
    if (debug)
    {
      _logger.info ("Insert count after Insert query in temporary table pages is " + insertedCnt);
    }
    // insert into #pages (pagenum, visits, dwell)
    // select distinct page,0,0 from TesteeResponse
    // where _fk_TestOpportunity = @oppkey and page is not null and not exists
    // (select * from #pages where pagenum = page)
    // TODO: I have exception: Can't reopen table:
    // 'pages0ea415b0z52bdz4fedz936dz52c33c37e983'? Why?
    // query = "insert into ${pagestblName} (pagenum, visits, dwell)"
    // + " select distinct page, 0, 0 from testeeresponse"
    // + " where _fk_TestOpportunity = ${oppkey} and page is not null "
    // +
    // " and not exists (select * from ${pagestblName} where pagenum = page) ";
    //
    // insertedCnt = executeStatement (connection, fixDataBaseNames (query,
    // unquotedparms), parameters, true).getUpdateCount ();
    // _logger.info
    // ("Insert count after Insert query in temporary table pages is " +
    // insertedCnt);

    connection.dropTemporaryTable (latenciesDBTable);
    return pagesDBTable;
  }

  public DataBaseTable createTMPItemsTable (SQLConnection connection,
      UUID oppkey,
      Date dateArchived,
      boolean debug) throws ReturnStatusException
  {
    DataBaseTable resDBTable = getDataBaseTable ("items")
        .addColumn ("_key", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100)
        .addColumn ("bankkey", SQL_TYPE_To_JAVA_TYPE.BIGINT)
        .addColumn ("itemkey", SQL_TYPE_To_JAVA_TYPE.BIGINT)
        .addColumn ("pagenumber", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("isFT", SQL_TYPE_To_JAVA_TYPE.BIT)
        .addColumn ("segmentkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250)
        .addColumn ("itemtype", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50)
        .addColumn ("strand", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200)
        .addColumn ("contentLevel", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200)
        .addColumn ("scorepoints", SQL_TYPE_To_JAVA_TYPE.INT)
        .addColumn ("mimetype", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100)
        .addColumn ("clientID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100)
        .addColumn ("notforScoring", SQL_TYPE_To_JAVA_TYPE.BIT)
        .addColumn ("strandSegment", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 250)
        .addColumn ("strandItem", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100);

    connection.createTemporaryTable (resDBTable);

    String SQL_INSERT = null;
    String query = null;
    Map<String, String> unquotedparms = new HashMap<String, String> ();
    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("oppkey", oppkey);
    String queryPreffix = "insert into ${tblName} (_key, bankkey, itemkey,  pagenumber, isFT, segmentkey, "
        + " itemtype, strand, contentLevel, scorepoints, mimetype, clientID, notforScoring, strandSegment, strandItem) "
        + " select _efk_ItemKey, _efk_ITSBank, _efk_ITSItem, page, R.isFieldTest, _efk_Segment, "
        + " format, null, D.name, " // dbo.ITEMBANK_ItemkeyStrandname(S._efk_Segment,
                                    // _fk_Item) (instead of null)
        + " R.scorepoint, responseMimeType, itemID, notForScoring, S._efk_Segment, _fk_Item ";
    String querySuffix = " testopportunitysegment S, ${ItemBankDB}.tblsetofadminitems A, "
        + " ${ItemBankDB}.tblitem I, ${ItemBankDB}.tblstrand D "
        + " where R._fk_TestOpportunity = ${oppkey} and _efk_ITSItem is not null "
        + " and A._fk_Strand = D._Key and S._fk_TestOpportunity = ${oppkey} "
        + " and R.segmentID = S.segmentID and A._fk_AdminSubject = _efk_Segment "
        + " and A._fk_Item = _efk_ItemKey and I._Key = _efk_ItemKey";

    if (dateArchived == null)
    {
      // insert into #items(_key, bankkey, itemkey, pagenumber, isFT,
      // segmentkey, itemtype, strand, contentLevel, scorepoints, mimetype,
      // clientID, notforScoring)
      // select _efk_ItemKey,_efk_ITSBank, _efk_ITSItem, page, R.isFieldTest,
      // _efk_Segment, format, dbo.ITEMBANK_ItemkeyStrandname(S._efk_Segment,
      // _fk_Item), D.name,
      // R.scorepoint, responseMimeType, itemID, notForScoring
      // from TesteeResponse R, TestOpportunitySegment S,
      // ITEMBANK_tblSetofAdminItems A, ITEMBANK_tblItem I, ITEMBANK_tblStrand D
      // where R._fk_TestOpportunity = @oppkey and _efk_ITSItem is not null and
      // A._fk_Strand = D._Key
      // and S._fk_TestOpportunity = @oppkey and R.segmentID = S.segmentID and
      // A._fk_AdminSubject = _efk_Segment and A._fk_Item = _efk_ItemKey and
      // I._Key = _efk_ItemKey
      // ;
      SQL_INSERT = queryPreffix
          + " from testeeresponse R, "
          + querySuffix;
    }
    else
    {
      // insert into #items(_key, bankkey, itemkey, pagenumber, isFT,
      // segmentkey, itemtype, strand, contentLevel, scorepoints, mimetype,
      // clientID, notForScoring)
      // select _efk_ItemKey,_efk_ITSBank, _efk_ITSItem, page, R.isFieldTest,
      // _efk_Segment, format,
      // dbo.ITEMBANK_ItemkeyStrandname(S._efk_Segment,_fk_Item), D.name,
      // R.scorepoint, responseMimeType, itemID, notForScoring
      // from TesteeResponseArchive R, TestOpportunitySegment S,
      // ITEMBANK_tblSetofAdminItems A, ITEMBANK_tblItem I, ITEMBANK_tblStrand D
      // where R._fk_TestOpportunity = @oppkey and _efk_ITSItem is not null and
      // A._fk_Strand = D._key
      // and S._fk_TestOpportunity = @oppkey and R.segmentID = S.segmentID and
      // A._fk_AdminSubject = _efk_Segment and A._fk_Item = _efk_ItemKey and
      // I._Key = _efk_ItemKey
      // ;
      SQL_INSERT = queryPreffix
          + " from testeeresponsearchive R, "
          + querySuffix;
    }
    query = fixDataBaseNames (SQL_INSERT);
    unquotedparms.put ("tblName", resDBTable.getTableName ());

    int insertedCnt = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, true).getUpdateCount ();
    if (debug)
    {
      _logger.info ("Insert count after Insert query in temporary table items is " + insertedCnt);
    }
    DbResultRecord record;
    String key = null;
    String strandSegment = null;
    String strandItem = null;
    String strand = null;
    String updateStrand = null;
    int countUp = 0;
    query = "select _key, strandSegment, strandItem from  ${tblName} ";

    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, false).getResultSets ().next ();

    Iterator<DbResultRecord> resItr = result.getRecords ();
    while (resItr.hasNext ())
    {
      record = resItr.next ();
      key = record.<String> get ("_key");
      strandSegment = record.<String> get ("strandSegment");
      strandItem = record.<String> get ("strandItem");

      strand = ItemkeyStrandName_F (connection, strandSegment, strandItem, true);

      updateStrand = "update ${tblName} set strand = ${strand} where _key = ${key}";
      parameters.put ("strand", strand).put ("key", key);

      countUp = executeStatement (connection, fixDataBaseNames (updateStrand, unquotedparms), parameters, false).getUpdateCount ();
      if (debug)
      {
        _logger.info ("Update count after update query in temporary table items is " + countUp);
      }
    }

    return resDBTable;
  }

  //
  protected void recordToXML (DbResultRecord record, List<String> orderedColumns, StringBuilder strBuilder, boolean debug) throws ReturnStatusException
  {
    boolean isFirstColumn = true;
    String[] prevXMLVertexes = null;
    String[] xmlVertexes = null;
    for (String columnName : orderedColumns)
    {
      xmlVertexes = parseColumnName (columnName);
      Object value = record.get (record.getColumnToIndex (columnName).get ());
      String resValue = "";
      if (value != null)
      {
        resValue = value.toString ();
      }
      String stValue = xmlSpecialSymbolsReplacer (resValue);
      try {
        columnFlatToXML (xmlVertexes, prevXMLVertexes, stValue, isFirstColumn, strBuilder, debug);
      } catch (ReturnStatusException e)
      {
        String error = String.format (e.getMessage (), columnName);
        _logger.error (error);
        throw new ReturnStatusException (error);
      }
      prevXMLVertexes = xmlVertexes;
      if (isFirstColumn)
      {
        isFirstColumn = false;
      }
      if (debug)
      {
        _logger.info (String.format ("%s: %s", columnName, resValue));
      }
    }
    List<String> lastTags = new ArrayList<String> ();
    for (int i = 0; i < xmlVertexes.length - 1; i++)
    {
      lastTags.add (xmlVertexes[i]);
    }
    closeTags (lastTags, strBuilder, debug);
  }

  // First very easy and flat variant
  protected void columnFlatToXML (String[] xmlVertexes, String[] prevXMLVertexes,
      Object value, boolean isFirstColumn,
      StringBuilder strBuilder, boolean debug) throws ReturnStatusException
  {
    int i = indexLastCoincidence (xmlVertexes, prevXMLVertexes);
    List<String> closedTags = new ArrayList<String> ();
    // i = -1 not have coinsides
    if (prevXMLVertexes != null && (i < prevXMLVertexes.length - 1))
    {
      closedTags = new ArrayList<String> ();
      for (int j = i + 1; j < prevXMLVertexes.length - 1; j++)
      {
        closedTags.add (prevXMLVertexes[j]);
      }
      closeTags (closedTags, strBuilder, debug);
    }
    if (xmlVertexes != null && i < xmlVertexes.length - 1)
    {
      List<String> openedTags = new ArrayList<String> ();
      for (int j = i + 1; j < xmlVertexes.length - 1; j++)
      {
        openedTags.add (xmlVertexes[j]);
      }
      if (prevXMLVertexes != null && closedTags.size () == 0 && openedTags.size () > 0)
      {
        strBuilder.append (">");
      }

      openTags (openedTags, strBuilder, debug);
    }
    if ((prevXMLVertexes != null) && (xmlVertexes != null))
    {
      if (i < prevXMLVertexes.length - 1 && i >= xmlVertexes.length - 1)
      {
        throw new ReturnStatusException ("Attribute-centric column %s must not come after a non-attribute-centric sibling in XML hierarchy");
      }
    }
    String resValue = "";
    if (value != null)
    {
      resValue = value.toString ();
    }
    String attrName = xmlVertexes[xmlVertexes.length - 1];
    // write value
    //SB-999
    resValue = formatValueToXSD(resValue, attrName);
    
    strBuilder.append (" ");
    strBuilder.append (attrName);
    strBuilder.append ("=\"");
    strBuilder.append (resValue);
    strBuilder.append ("\"");
  }

  //
  protected int indexLastCoincidence (String[] xmlVertexes, String[] prevXMLVertexes)
  {
    if (prevXMLVertexes == null || xmlVertexes == null)
      return -1;

    int i = 0;
    while (xmlVertexes[i].equals (prevXMLVertexes[i]))
    {
      i++;
    }
    return i - 1;
  }

  //
  protected void closeTags (List<String> closedTags, StringBuilder strBuilder, boolean debug) throws ReturnStatusException
  {
    if (closedTags.size () > 0)
    {
      strBuilder.append (" />").append(ls); // last tag closed
      for (int i = closedTags.size () - 2; i >= 0; i--)
      {
        strBuilder.append ("</");
        strBuilder.append (closedTags.get (i));
        strBuilder.append (">").append(ls);
      }
    }
  }

  //
  protected void openTags (List<String> openedTags, StringBuilder strBuilder, boolean debug) throws ReturnStatusException
  {
    if (openedTags.size () > 0)
    {
      for (int i = 0; i < openedTags.size () - 1; i++)
      {
        strBuilder.append ("<");
        strBuilder.append (openedTags.get (i));
        strBuilder.append (">");
      }
      strBuilder.append ("<");
      strBuilder.append (openedTags.get (openedTags.size () - 1));
    }
  }

  //
  protected String[] parseColumnName (String columnName)
  {
    String[] parts = columnName.split ("/");
    String valueName = parts[parts.length - 1];
    if (valueName.startsWith ("@"))
    {
      parts[parts.length - 1] = valueName.substring (1);
    }
    return parts;
  }

  //
  protected String[] parseColumnName (String columnName, String outerBracket)
  {
    String[] parts = columnName.split ("/");
    String valueName = parts[parts.length - 1];
    if (valueName.startsWith ("@"))
    {
      parts[parts.length - 1] = valueName.substring (1);
    }
    if (parts.length == 1 && outerBracket != null && !outerBracket.isEmpty ())
    {
      String[] partss = new String[2];
      partss[0] = outerBracket;
      partss[1] = parts[0];
      parts = partss;
    }
    return parts;
  }

  //
  protected void recordToXML (DbResultRecord record,
      List<String> orderedColumns,
      StringBuilder strBuilder, String outerBracket,
      boolean debug) throws ReturnStatusException
  {
    boolean isFirstColumn = true;
    String[] prevXMLVertexes = null;
    String[] xmlVertexes = null;
    for (String columnName : orderedColumns)
    {
      if (outerBracket == null || outerBracket.isEmpty ())
      {
        xmlVertexes = parseColumnName (columnName);
      }
      else
      {
        xmlVertexes = parseColumnName (columnName, outerBracket);
      }
      Object value = record.get (record.getColumnToIndex (columnName).get ());
      String resValue = "";
      if (value != null)
      {
        resValue = value.toString ();
      }
      String stValue = xmlSpecialSymbolsReplacer (resValue);
      try {
        columnFlatToXML (xmlVertexes, prevXMLVertexes, stValue, isFirstColumn, strBuilder, debug);
      } catch (ReturnStatusException e)
      {
        String error = String.format (e.getMessage (), columnName);
        _logger.error (error);
        throw new ReturnStatusException (error);
      }
      prevXMLVertexes = xmlVertexes;
      if (isFirstColumn)
      {
        isFirstColumn = false;
      }
      if (debug)
      {
        _logger.info (String.format ("%s: %s", columnName, resValue));
      }
    }
    List<String> lastTags = new ArrayList<String> ();
    for (int i = 0; i < xmlVertexes.length - 1; i++)
    {
      lastTags.add (xmlVertexes[i]);
    }
    closeTags (lastTags, strBuilder, debug);
  }

  // StringBuilder stringBuilder = new StringBuilder ();
  // stringBuilder.append (commentsDelimeter);
  // return stringBuilder.toString ();
  // Exception:
  // "Row tag omission (empty row tag name) cannot be used with attribute-centric FOR XML serialization.";

  // This function has name "escape" in standard
  protected String xmlSpecialSymbolsReplacer (String value)
  {
    if (value != null)
    {
      value = value.replaceAll ("&", "&amp;"); // this is first because every
                                               // new
                                               // set of symbols contains symbol
                                               // "&" (ampersant)!
      value = value.replaceAll ("<", "&lt;");
      value = value.replaceAll (">", "&gt;");
      value = value.replaceAll ("'", "&apos;");
      value = value.replaceAll ("\"", "&quot;");
      // < (replace with &lt;)
      // > (replace with &gt;)
      // & (replace with &amp;)
      // ' (replace with &apos;)
      // " (replace with &quot;)
    }
    return value;
  }
  /**
   * @param record
   */
  // for debugging only 
  public void dumpRecord (DbResultRecord record) throws ReturnStatusException {

    String columnName = null;
    Iterator<String> itNames = record.getColumnNames ();
    String resValue = "";
    while (itNames.hasNext ())
    {
      columnName = itNames.next ();
      Object value = record.get (record.getColumnToIndex (columnName).get ());
      if(value != null)
      {
        resValue = value.toString ();
      }
      _logger.info (String.format ("%s: %s", columnName, resValue));
      //System.out.println(String.format ("%s: %s", columnName, resValue));
    }

  }
  //
  protected String getNodeAttributeName(String nodeName, String attrName)
  {
	  return nodeName + this.slash + this.at + attrName;
  }
  //
  protected String getTesteeAttributes(Map<String, String> testeeAttributes, String context, String contextDate)
  {
	  StringBuilder strBuilder = new StringBuilder ();
	  if(testeeAttributes != null)
	  {
		  // List Testee Attributes from the spreadsheet 
		  List<String> orderedColumns = new ArrayList<String> (Arrays.asList (
				  "Birthdate",
				  "FirstName",
				  "Sex",
				  "GradeLevelWhenAssessed",
				  "LastOrSurname",
				  "StudentIdentifier",
				  "MiddleName",
				  "AlternateSSID",
				  "HispanicOrLatinoEthnicity",
				  "AmericanIndianOrAlaskaNative",
				  "Asian",
				  "BlackOrAfricanAmerican",
				  "White",
				  "NativeHawaiianOrOtherPacificIslander",
				  "DemographicRaceTwoOrMoreRaces",
				  "IDEAIndicator",
				  "LEPStatus",
				  "Section504Status",
				  "EconomicDisadvantageStatus",
				  "MigrantStatus",
				  "ConfirmationCode",
				  "LanguageCode",
				  "EnglishLanguageProficiencLevel",
				  "FirstEntryDateIntoUSSchool",
				  "LimitedEnglishProficiencyEntryDate",
				  "LEPExitDate",
				  //"GradeLevelWhenAssessed",
				  "TitleIIILanguageInstructionProgramType",
				  "PrimaryDisabilityType"
				  ));
		  Map<String, String> new2OldAttrNames = new HashMap<String, String>();
		  new2OldAttrNames.put("Birthdate","DOB");
		  new2OldAttrNames.put("Sex", "Gender");//
		  new2OldAttrNames.put("LastOrSurname","LastName");
		  //new2OldAttrNames.put("StudentIdentifier","SSID");
		  new2OldAttrNames.put("AlternateSSID", "SSID");//
		  
		  Set<String> attrNames = testeeAttributes.keySet();
		  String mapKey = null;
		  String value;
		  for(String name : orderedColumns)
		  {
			  mapKey = findKey(name, attrNames, new2OldAttrNames);
			  value = testeeAttributes.get(mapKey);
		  	  if(value == null)
		  		  continue;
			  	
			  strBuilder.append("<").append(TESTEEATTRIBUTE_NODE_NAME).append(SPACE);
			  strBuilder.append("context = \"").append(context).append("\"").append(SPACE);
			  strBuilder.append("name = \"").append(name).append("\"").append(SPACE);
			  strBuilder.append("value = \"").append(value).append("\"").append(SPACE);
			  strBuilder.append("contextDate = \"").append(contextDate).append("\"").append(SPACE);
			  strBuilder.append("/>").append(ls);				  			  
		  }			  	
	  } else
	  {// TODO GUEST from table!
		  strBuilder.append("<").append(TESTEEATTRIBUTE_NODE_NAME).append(SPACE);
		  strBuilder.append("context = \"").append(context).append("\"").append(SPACE);
		  strBuilder.append("name = \"").append("GUEST").append("\"").append(SPACE);
		  strBuilder.append("value = \"").append("GUEST").append("\"").append(SPACE);
		  strBuilder.append("contextDate = \"").append(contextDate).append("\"").append(SPACE);
		  strBuilder.append("/>").append(ls);
	  }
	  return strBuilder.toString();
  }

  protected String getTesteeRelationships(Map<String, String> testeeRelationships, String context, String contextDate)
  {
	  StringBuilder strBuilder = new StringBuilder ();
	  if(testeeRelationships != null)
	  {
		  // List Testee Relationships from the spreadsheet 
		  List<String> orderedColumns = new ArrayList<String> (Arrays.asList (
		  "ResponsibleDistrictIdentifier",
		  "OrganizationName",
		  "ResponsibleInstitutionIdentifier",
		  "NameOfInstitution",
		  "StateName",
		  "StateAbbreviation"
				  ));
		  Map<String, String> new2OldAttrNames = new HashMap<String, String>();
		  new2OldAttrNames.put("ResponsibleDistrictIdentifier","DistrictID");
		  new2OldAttrNames.put("OrganizationName", "DistrictName");
		  new2OldAttrNames.put("ResponsibleInstitutionIdentifier","SchoolID");
		  new2OldAttrNames.put("NameOfInstitution","SchoolName");
		  
		  Set<String> attrNames = testeeRelationships.keySet();
		  String mapKey = null;
		  //String entityKey = ""; see letter from Adam
		  String value;
		  for(String name : orderedColumns)
		  {
			  mapKey = findKey(name, attrNames, new2OldAttrNames);
			  value = testeeRelationships.get(mapKey);
		  	  if(value == null)
		  		  continue;

			  strBuilder.append("<").append(TESTEERELATIONSHIP_NODE_NAME).append(SPACE);
			  strBuilder.append("context = \"").append(context).append("\"").append(SPACE);
			  strBuilder.append("name = \"").append(name).append("\"").append(SPACE);
			  //strBuilder.append("entityKey = \"").append(entityKey).append("\"").append(SPACE);
			  strBuilder.append("value = \"").append(value).append("\"").append(SPACE);
			  strBuilder.append("contextDate = \"").append(contextDate).append("\"").append(SPACE);
			  strBuilder.append("/>").append(ls);			  			  
		  }
	  }
	  return strBuilder.toString();		  	  		  
  }

  /**
   * 
   * @param attrName -- is name what we looking for
   * @param attrNames
   * @return name of attibute as in Set attrNames
   * can return null!!!
   */
  protected String findAttrNameIgnoreCase(String attrName, Set<String> attrNames)
  {
	  String ret = null;
	  for(String name: attrNames)
	  {
		 if(name.equalsIgnoreCase(attrName))
			 ret = name;
	  }
	  return ret;
  }

  /**
   * 
   * @param attrName
   * @param new2OldAttrNames
   * @return
   */
  protected String findOldAttrNameIgnoreCase(String attrName, Map<String, String> new2OldAttrNames)
  {
	  Set<String> attrNames = new2OldAttrNames.keySet();
	  String ret = null;
	  for(String name: attrNames)
	  {
		 if(name.equalsIgnoreCase(attrName))
			 ret = name;
	  }
	  return new2OldAttrNames.get(ret);
  }
/**
 *  
 * @param name
 * @param attrNames
 * @param new2OldAttrNames
 * @return
 */
  protected String findKey(String name, Set<String> attrNames,
			Map<String, String> new2OldAttrNames) {
		String mapKey = findAttrNameIgnoreCase(name, attrNames);
		if (mapKey == null) {
			mapKey = findOldAttrNameIgnoreCase(name, new2OldAttrNames);
		}
		return mapKey;
	}

  protected String getContextDate(SQLConnection connection, UUID oppkey, String context) throws ReturnStatusException
  {
	  String contextDate = "";
      String query = "select _date as date from session.testeerelationship where context = ${context} and _fk_testopportunity = ${oppkey} limit 1";
      SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey).put("context", context);
      SingleDataResultSet result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
      DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
    	  contextDate = record.<Date> get ("date").toString();
      }
	 return contextDate; 
  }
  
  protected String getContextDateForGUEST(SQLConnection connection, UUID oppkey, String context) throws ReturnStatusException
  {
		String contextDate = "";
		if (context.equalsIgnoreCase(INITIAL)) {
			String query = "select datestarted as date from testopportunity where  _key = ${oppkey}";
			SqlParametersMaps parameters = new SqlParametersMaps().put(	"oppkey", oppkey).put("context", context);
			SingleDataResultSet result = executeStatement(connection, query, parameters, false).getResultSets().next();
			DbResultRecord record = result.getCount() > 0 ? result.getRecords().next() : null;
			if (record != null) {
				contextDate = record.<Date> get("date").toString();
			}
		} else if (context.equalsIgnoreCase(FINAL)) {
			String query = "select datecompleted as date from testopportunity where  _key = ${oppkey}";
			SqlParametersMaps parameters = new SqlParametersMaps().put(	"oppkey", oppkey).put("context", context);
			SingleDataResultSet result = executeStatement(connection, query, parameters, false).getResultSets().next();
			DbResultRecord record = result.getCount() > 0 ? result.getRecords().next() : null;
			if (record != null) {
				contextDate = record.<Date> get("date").toString();
			}
		}
		return contextDate;
  }

  public String getEffectiveDate(SQLConnection connection, String client, String testid) throws ReturnStatusException
  {
	  Date starttime = _dateUtil.getDateWRetStatus (connection);
      String windowStart = null;
      String effectiveDate = "";

      try {

        String SQL_QUERY = "select case when startdate is null then ${now} else (startDate + INTERVAL shiftWindowStart DAY ) end as windowStart "
        		+ " from ${ConfigDB}.client_testwindow W, _externs E  "
                + " where W.clientname = ${client} and W.testid = ${testid} and E.clientname = ${client}"
                + " and ${now} between "
                + " case when W.startdate is null then ${now}  else (W.startDate + INTERVAL shiftWindowStart DAY ) end "
                + " and " 
                + " case when W.endDate is null then ${now}  else ( W.endDate + INTERVAL shiftWindowEnd DAY) end";

        String query = fixDataBaseNames (SQL_QUERY);
        SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("client", client).put ("now", starttime).put("testid", testid);
        SingleDataResultSet result = executeStatement (connection, query, parameters, false).getResultSets ().next ();
        DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
        if (record != null) {
          windowStart = record.<String> get("windowStart"); // 2014-12-07 22:17:13.387
          effectiveDate = windowStart.substring(0, 10); 	// 2014-12-07
        }

      } catch (Exception e)
      {
    	  _logger.error(e.getMessage());
    	  throw new ReturnStatusException(e.getMessage());
      }
      
      _commonDll._LogDBLatency_SP (connection, "getContextDate", starttime, null, true, null, null, null, client, null);

      return effectiveDate;
  
  }
  
public SingleDataResultSet readQaReportQueue (SQLConnection connection) throws ReturnStatusException {
    
    final String cmd1 = "select _key, _fk_testopportunity as testopp, changestatus, dateentered  "
      + " from qareportqueue where  datesent is null limit 1";
    SingleDataResultSet res = executeStatement (connection, cmd1, null, false).getResultSets ().next ();

    return res;
  }
  
  public void deleteQaReportQueue (SQLConnection conn, Long key) throws ReturnStatusException {
    final String cmd1 = "delete from qareportqueue where _key = ${key}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("key", key);
    int deletedCnt = executeStatement (conn, cmd1, parms1, false).getUpdateCount ();
  }
  
  public void QA_SendXML (SQLConnection connection, UUID oppkey, String changeStatus) throws ReturnStatusException {
    Date starttime = _dateUtil.getDateWRetStatus (connection);
    _Ref<String> errRef = new _Ref<> ();
    // some checks that may fill out errRef
    String status = null;

    final String cmd1 = "select status from testopportunity where _key = ${oppkey}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("oppkey", oppkey);
    SingleDataResultSet rs1 = executeStatement (connection, cmd1, parms1, false).getResultSets ().next ();
    DbResultRecord rec1 = (rs1.getCount () > 0 ? rs1.getRecords ().next () : null);
    if (rec1 != null) {
      status = rec1.<String> get ("status");
    }
    if (status == null) {
      errRef.set (String.format ("No such opportunity: %s", oppkey.toString ()));

    } else if (status.equalsIgnoreCase ("submitted") || status.equalsIgnoreCase ("reported")) {
      errRef.set ("Opportunity already submitted");

    } else {

      String xmlReport = XML_GetOppXML_SP (connection, oppkey, false);
      if (xmlReport == null)
        errRef.set (String.format ("No XML result for: %s", oppkey.toString ()));
      else {
        sendQAReportToTis(oppkey, xmlReport, errRef);
      }
      if (errRef.get () == null) {

        final String cmd3 = "insert into ${ArchiveDB}.opportunityaudit "
            + "(_fk_TestOpportunity,  AccessType, actor, comment, hostname,  dateaccessed, dbname) "
            + " values ( ${oppkey},  'SEND XML', 'QA_SendXML', ${xmlreport}, ${localhost},  now(3), ${dbname})";

        SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("oppkey", oppkey).
            put ("localhost", _commonDll.getLocalhostName ()).put ("dbname", getTdsSettings ().getTDSSessionDBName ()).
            put ("xmlreport", xmlReport);
        int insertedCnt = executeStatement (connection, fixDataBaseNames (cmd3), parms3, false).getUpdateCount ();
 
        if (changeStatus != null)
          // TODO Elena: change the last parameter to name of the deamon?
          _commonDll.SetOpportunityStatus_SP (connection, oppkey, changeStatus, true, "TDS_XML_SERVICE");

      }
    }
    if (errRef.get () != null)
      _commonDll._LogDBError_SP (connection, "QA_SendXML", errRef.get (), null, null, null, oppkey);
    _commonDll._LogDBLatency_SP (connection, "QA_SendXML", starttime, null, true, null, oppkey, null, null, null);
  }
  
  private void sendQAReportToTis (UUID oppkey, String xmlReport, _Ref<String> errRef) {
    boolean isSent = false;
    long startSentTime = System.currentTimeMillis ();
    while (!isSent) {
      try {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);  
        HttpEntity<String> entity = new HttpEntity<String>(xmlReport, headers);
        ResponseEntity<String> response = _restClient.exchange (_tisUrl, HttpMethod.POST, entity, String.class);
        isSent = true;
        if (response.getStatusCode () != HttpStatus.OK) {
          errRef.set (response.getStatusCode ().toString () + ": " + response.getBody ());
        }
      } catch (Exception e) {
        _logger.error ("_tisUrl::: "+_tisUrl);
        _logger.error ("xmlReport::: "+xmlReport);
        _logger.error (e.getMessage (), e);
        isSent = false;
        try {
          Thread.sleep (_tisWaitTime * 1000);
        } catch (InterruptedException e1) {
          _logger.error ("sendQAReportToTis sleep exception:  " + e1.getMessage (), e);
          System.exit (1);
        }
        if (_tisMaxWaitTime > 0 && ((System.currentTimeMillis () - startSentTime) / 1000) > _tisMaxWaitTime) {
          _logger.error ("sendQAReportToTis: No valid response received from TIS after " + (System.currentTimeMillis () - startSentTime) / 1000 + " seconds, exiting");
          System.exit (1);
        }
      }
    }
  }
  
  public String formatValueToXSD(String resValue, String attrName)
  {
	    // SB-999
	    // type="xs:dateTime"
	    try{
		    if(attrName.equalsIgnoreCase("statusDate")
		    		|| attrName.equalsIgnoreCase("adminDate")
		    		|| attrName.equalsIgnoreCase("contextDate")
		    		|| attrName.equalsIgnoreCase("date")
		    		)
		    {
		    	resValue = formatXSDateTime(resValue);
		    }
		    // type = "NullableDateTime"
		    else if(attrName.equalsIgnoreCase("dateCompleted")
		    		|| attrName.equalsIgnoreCase("startDate")
		    		|| attrName.equalsIgnoreCase("dateForceCompleted")
		    		)
		    {
		    	resValue = formatNullableDateTime(resValue);
		    }
		    // type = Bit 
		    else if (attrName.equalsIgnoreCase("isSelected")
		    		|| attrName.equalsIgnoreCase("isDemo")
		    		|| attrName.equalsIgnoreCase("operational")
		    		|| attrName.equalsIgnoreCase("dropped")
		    		) 
		    {
		    	resValue = (!(resValue == null || resValue.isEmpty()) 
		    			|| resValue.equalsIgnoreCase("true")
		    			|| resValue.equalsIgnoreCase("1"))? "1": "0";
		    }
		    // type = 'UFloatAllowNegativeOne'
		    else if (attrName.equalsIgnoreCase("scorePoint")
		    		|| attrName.equalsIgnoreCase("maxScore")
		    		|| attrName.equalsIgnoreCase("score")
		    		)
		    {
		    	resValue = ((resValue == null || resValue.isEmpty()) )? "-1": resValue;	    	
		    }
		    // type="xs:unsignedInt" && type="xs:int"
		    else if(attrName.equalsIgnoreCase("pageTime")
		    		|| attrName.equalsIgnoreCase("handScoreProject")
		    		|| attrName.equalsIgnoreCase("academicYear")
		    		|| attrName.equalsIgnoreCase("segment")
		    		|| attrName.equalsIgnoreCase("position")
		    		|| attrName.equalsIgnoreCase("bankKey")
		    		|| attrName.equalsIgnoreCase("key")
		    		|| attrName.equalsIgnoreCase("numberVisits")
		    		|| attrName.equalsIgnoreCase("pageNumber")
		    		|| attrName.equalsIgnoreCase("pageVisits")
		    		|| attrName.equalsIgnoreCase("opportunity")
		    		|| attrName.equalsIgnoreCase("pauseCount")
		    		|| attrName.equalsIgnoreCase("itemCount")
		    		|| attrName.equalsIgnoreCase("ftCount")
		    		|| attrName.equalsIgnoreCase("abnormalStarts")
		    		|| attrName.equalsIgnoreCase("gracePeriodRestarts")
		    		|| attrName.equalsIgnoreCase("page")
		    		|| attrName.equalsIgnoreCase("count")
		    		)
		    {
		    	resValue = ((resValue == null || resValue.isEmpty()) )? "0": resValue;	
		    }
	    }
	    catch(Exception e)
	    {
	    	_logger.error(e.getMessage());
	    }
	    return resValue;
	  
  }
  
  public String formatNullableDateTime(String inDate) throws ReturnStatusException {
	  return (inDate == null || inDate.isEmpty())? EMPTY : formatXSDateTime(inDate);
  }

	public String formatXSDateTime(String inDate) throws ReturnStatusException {
	
		String out = null;
		// String date_s = "2011-01-18 00:00:00.0";
		String inputContextDateFormat = "yyyy-MM-dd HH:mm:ss.SSS";
		// <xs:attribute name="contextDate" use="required" type="xs:dateTime"/>
		// see reportxml_os.xsd
		String contextDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	
		// *** note that it's "yyyy-MM-dd hh:mm:ss" not "yyyy-mm-dd hh:mm:ss"
		SimpleDateFormat dt = new SimpleDateFormat(inputContextDateFormat);
	
		try {
			Date date = dt.parse(inDate);
			// *** same for the format String below
			SimpleDateFormat dt1 = new SimpleDateFormat(contextDateFormat);
			out = dt1.format(date);
	
		} catch (ParseException e) {
			throw new ReturnStatusException(e.getMessage());
		}
		if (out == null || out.isEmpty())
			out = inDate;
	
		return out;
	
	}

  public void setTisUrl (String tisUrl) {
    _tisUrl = tisUrl;
  }

  public void setTisStatusCallbackUrl (String tisStatusCallbackUrl) {
    _tisStatusCallbackUrl = tisStatusCallbackUrl;
  }

  public void setTisWaitTime (long tisWaitTime) {
    _tisWaitTime = tisWaitTime;
  }

  public void setTisMaxWaitTime (long tisMaxWaitTime) {
    _tisMaxWaitTime = tisMaxWaitTime;
  }
 
  public String getAcademicYear (SQLConnection connection, String testkey) throws ReturnStatusException
  {
	      String academicYear = null;
	      SingleDataResultSet result;
	      DbResultRecord record;
	      String query = "select TA.schoolyear as academicYear "
	      		+ " from ${ItemBankDB}.tblsetofadminsubjects T, ${ItemBankDB}.tbltestadmin TA "
	      		+ " where T._Key = ${testkey}"
	      		+ " and  T._fk_testadmin = TA._key";

	      SqlParametersMaps parameters = new SqlParametersMaps ().put ("testkey", testkey);
	      result = executeStatement (connection, this.fixDataBaseNames (query), parameters, false).getResultSets ().next ();
	      record = result.getCount () > 0 ? result.getRecords ().next () : null;
	      if (record != null) {
	        academicYear = record.<String> get ("academicYear");
	      }
	      
	      if(academicYear != null)
	      {
	    	  if(academicYear.length() > 4)
	    	  {
//	    		  email from Mon 12/8/2014 11:19 AM	    		  
//	    		  The way it is defined in our specifications, as well as in CEDS (https://ceds.ed.gov/CEDSElementDetails.aspx?TermId=7243), is as follows:
//	    		  For academic years that span a calendar year this is the four digit year-end. E.g. 2013 for 2012-2013. 1900 <= YYYY <= 9999. xsd:integer
//	    		  See row 10 in TDS Output tab on our spec sheet.

	    		  academicYear = academicYear.substring(academicYear.length() - 4); 
	    	  }
	    	  try{
	    		  Integer ayear = Integer.parseInt(academicYear);
	    		  // to do nothing;
	    	  } catch (Exception e)
	    	  {
	    		  academicYear = "";
	    	  }
	      }
	      else
	      {
	    	  academicYear = "";	      
	      }
	      return academicYear;
  }
}
