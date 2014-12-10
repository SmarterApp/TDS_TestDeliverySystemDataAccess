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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
import TDS.Shared.Exceptions.ReturnStatusException;
import tds.dll.api.ICommonDLL;
import tds.dll.api.IItemSelectionDLL;
import tds.dll.api.IStudentDLL;

/**
 * @author akulakov
 * 
 */
public class ItemSelectionDLL extends AbstractDLL implements IItemSelectionDLL
{
  private static Logger       _logger     = LoggerFactory.getLogger (ItemSelectionDLL.class);

  @Autowired
  ICommonDLL                  _commonDll   = null;

  @Autowired
  IStudentDLL                 _studentDll   = null;

  @Autowired
  private AbstractDateUtilDll _dateUtil    = null;

  final static String         SATISFIED   = "SATISFIED";
  final static String         FIXEDFORM   = "fixedform";
  final static String         ADAPTIVE    = "adaptive";
  final static String         FIELDTEST   = "fieldtest";
  final static String         EMPTY       = "";
  final static String         LIKESTRING1 = "I-";
  final static String         LIKESTRING2 = "\'(Delaware)%Read%\'";
  final static String         LANGUAGE    = "\'Language\'";

  /**
   * Comments from C#: public void GetItemCandidates(string oppkey, ref string
   * algorithm, ref string segmentKey, ref string segmentID, ref int
   * segmentPosition, ref string groupID, ref string blockID, ref string
   * itempool, ref string session, ref bool isSimulation)
   */

  /*
   * (non-Javadoc)
   * 
   * @see
   * tds.dll.api.IItemSelectionDLL#AA_GetNextItemCandidates_SP(java.sql.Connection
   * , java.util.UUID)
   */
  // This stored procedure uses update query
  // From TDSCore_Dev_Session2012_Sandbox
  public SingleDataResultSet AA_GetNextItemCandidates_SP (SQLConnection connection, UUID oppkey
      ) throws ReturnStatusException
  {
    Date starttime = _dateUtil.getDateWRetStatus (connection);

    String algorithm = null;
    Integer segment = null; // = segment position
    String segmentKey = null;
    String segmentID = null;

    Integer ftcnt = 0;
    UUID session = null;
    boolean isSim;

    SingleDataResultSet result;
    DbResultRecord record;

    String language = null;
    isSim = IsSimulation_FN (connection, oppkey);

    // session will be result query1
    final String SQL_QUERY1 = "select _fk_Session as session from testopportunity where _Key = ${oppkey}";
    SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey);
    result = executeStatement (connection, SQL_QUERY1, parameters, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      session = record.<UUID> get ("session");
    }
    // now language = null; //language will be result query2
    final String SQL_QUERY2 = "select AccCode from testeeaccommodations where _fk_TestOpportunity = ${oppkey} and AccType = ${Language}";
    SqlParametersMaps parameters2 = new SqlParametersMaps ().put ("oppkey", oppkey).put ("Language", "Language");
    result = executeStatement (connection, SQL_QUERY2, parameters2, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      language = record.<String> get ("AccCode");
    }

    while (true) // while not found
    {
      final String SQL_QUERY3 = "select  SegmentPosition, _efk_Segment, segmentID, algorithm, ftItemCnt "
          + " from testopportunitysegment "
          + " where _fk_TestOpportunity = ${oppkey} and IsSatisfied = ${isSatisfied} "
          + " order by SegmentPosition limit 1";
      SqlParametersMaps parameters3 = new SqlParametersMaps ().put ("oppkey", oppkey).put ("isSatisfied", false);
      result = executeStatement (connection, SQL_QUERY3, parameters3, false).getResultSets ().next ();
      record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        segment = record.<Integer> get ("SegmentPosition");
        segmentKey = record.<String> get ("_efk_Segment");
        segmentID = record.<String> get ("segmentID");
        algorithm = record.<String> get ("algorithm");
        ftcnt = record.<Integer> get ("ftItemCnt");
      }

      if (segment == null) // loop terminating conditions, No segment lacks
                           // fulfillment
      {
        List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
        CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
        rcd.put ("segment", null);
        rcd.put ("algorithm", SATISFIED);
        rcd.put ("ability", null);
        resultList.add (rcd);
        result = new SingleDataResultSet ();
        result.addColumn ("segment", SQL_TYPE_To_JAVA_TYPE.INT);
        result.addColumn ("algorithm", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
        result.addColumn ("ability", SQL_TYPE_To_JAVA_TYPE.FLOAT);
        result.addRecords (resultList);
        return result;
      }
      // found the segment to work on
      // go to next
      else if (!_AA_IsSegmentSatisfied_FN (connection, oppkey, segment)) {
        // TODO: check, what value returns this function, after
        // isSegmentSatisfied (oppkey, segment) will be implemented
        break;
      }
      else {// segment is satisfied, mark and continue loop, NOTE: T_InsertItems
            // modified to do this update, so this should never be necessary

        final String SQL_QUERY4 = "update testopportunitysegment set IsSatisfied = 1 where _fk_TestOpportunity = ${oppkey} and SegmentPosition = ${SegmentPosition}";
        SqlParametersMaps parameters4 = new SqlParametersMaps ().put ("oppkey", oppkey).put ("SegmentPosition", segment);
        int updateCnt = executeStatement (connection, SQL_QUERY4, parameters4, false).getUpdateCount ();

        _logger.debug ("Update count after Insert query in AA_GetNextItemCandidates_SP is " + updateCnt);
      }
    }

    _Ref<String> groupIDValue = new _Ref<String> ();
    _Ref<String> blockIDValue = new _Ref<String> ();

    if (DbComparator.isEqual (algorithm, FIXEDFORM))
    {
      _AA_NextFixedformGroup_SP (connection, oppkey, segmentKey, language, groupIDValue, blockIDValue);
    }
    if (DbComparator.containsIgnoreCase (algorithm, ADAPTIVE) && DbComparator.greaterThan (ftcnt, 0))
    // the groupkey includes the blockID. We need this to ensure that we
    // get the items from the desired block
    {
      _AA_NextFieldtestGroup_SP (connection, oppkey, segment, segmentKey, segmentID, language, groupIDValue, blockIDValue, true);

      if (groupIDValue.get () != null)
      {
        algorithm = FIELDTEST;
      }
    }
    if (DbComparator.containsIgnoreCase (algorithm, ADAPTIVE))
    // -- The C# adaptive algorithm will control when to access items, 
    // blueprint, etc. from AA_MakeCset1, AA_GetResponseSet,
    // AA_UpdateAbilityEstimates, AA_GetGroupContentLevels
    {
      groupIDValue.set (EMPTY);
      blockIDValue.set (EMPTY);
    }

    /**
     * Comments from DB: -- 9/2012: Added isSimulation and sessionKey to
     * returned record, and itempool string for adaptive tests select @oppkey as
     * oppkey, @segment as segmentPosition, @segmentKey as segmentkey, @segmentID
     * as segmentID, @algorithm as algorithm , @groupID as groupID, @blockID as
     * blockID, @isSim as isSimulation, @session as sessionKey;
     */
    List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
    CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
    rcd.put ("oppkey", oppkey);
    rcd.put ("segment", segment);
    rcd.put ("segmentKey", segmentKey);
    rcd.put ("segmentID", segmentID);
    rcd.put ("algorithm", algorithm);
    rcd.put ("groupID", groupIDValue.get ());
    rcd.put ("blockID", blockIDValue.get ());
    rcd.put ("isSim", isSim);
    rcd.put ("session", session);
    resultList.add (rcd);
    result = new SingleDataResultSet ();
    result.addColumn ("oppkey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER);
    result.addColumn ("segment", SQL_TYPE_To_JAVA_TYPE.INT);
    result.addColumn ("segmentKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    result.addColumn ("segmentID", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    result.addColumn ("algorithm", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    result.addColumn ("groupID", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    result.addColumn ("blockID", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    result.addColumn ("isSim", SQL_TYPE_To_JAVA_TYPE.BIT);
    result.addColumn ("session", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER);
    result.addRecords (resultList);

    _commonDll._LogDBLatency_SP (connection, "AA_GetNextItemCandidates", starttime, null, true, null, oppkey);

    return result;
  }

  // From TDSCore_Dev_Session2012_Sandbox
  public Boolean IsSimulation_FN (SQLConnection connection, UUID oppkey) throws ReturnStatusException
  {
    boolean res = false;
    final String SQL_QUERY = "select 1 from _externs E, testopportunity O, sim_segment S "
        + "where O._Key = ${oppkey} and E.clientname = O.clientname and E.environment = 'SIMULATION' "
        + "and S._fk_Session = O._fk_Session and S._efk_AdminSubject = O._efk_AdminSubject";
    SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY, parameters, false).getResultSets ().next ();
    if (result.getCount () > 0) {
      res = true;
    }
    return res;
  }

  // From TDSCore_Dev_Session2012_Sandbox
  public Boolean _AA_IsSegmentSatisfied_FN (SQLConnection connection, UUID oppkey, Integer segment) throws ReturnStatusException
  {
    boolean res = false;

    // Determine if all required items have been selected for a segment of a
    // test opp (just selected, not necessarily administered or responded to)
    Integer ftcnt = null;
    Integer opcnt = null;
    String segmentKey = null;
    Integer opneed = null;
    Integer ftneed = null;
    Integer oppitems = null;
    String algorithm = null;

    final String SQL_QUERY = "select opItemCnt, ftITemCnt, algorithm, _efk_Segment "
        + "from testopportunitysegment where _fk_TestOpportunity = ${oppkey} and SegmentPosition = ${segment}";

    SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey).put ("segment", segment);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY, parameters, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      opneed = record.<Integer> get ("opItemCnt");
      ftneed = record.<Integer> get ("ftITemCnt");
      algorithm = record.<String> get ("algorithm");
      segmentKey = record.<String> get ("_efk_Segment");
    }
    // value of the isFieldTest = false(0)
    final String SQL_QUERY2 = "select count(*) as count from testeeresponse where _fk_TestOpportunity = ${oppkey} and segment = ${segment} and isFieldTest = ${isFieldTest}";
    SqlParametersMaps parameters2 = new SqlParametersMaps ().put ("oppkey", oppkey).put ("segment", segment).put ("isFieldTest", 0);
    result = executeStatement (connection, SQL_QUERY2, parameters2, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      opcnt = record.<Long> get ("count").intValue ();
    }
    // value of the isFieldTest = true(1)
    final String SQL_QUERY3 = "select count(*) as count from testeeresponse where _fk_TestOpportunity = ${oppkey} and segment = ${segment} and isFieldTest = ${isFieldTest}";
    SqlParametersMaps parameters3 = new SqlParametersMaps ().put ("oppkey", oppkey).put ("segment", segment).put ("isFieldTest", 1);
    result = executeStatement (connection, SQL_QUERY3, parameters3, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      ftcnt = record.<Long> get ("count").intValue ();
    }

    if (DbComparator.containsIgnoreCase (algorithm, ADAPTIVE) && DbComparator.greaterOrEqual (opcnt, opneed))
    {
      final String SQL_QUERY4 = "select count(*) as count from ft_opportunityitem where _fk_TestOpportunity = ${oppkey} and _efk_FieldTest = ${segmentKey} and coalesce(deleted, 0) = 0 and dateAdministered is null";
      SqlParametersMaps parameters4 = new SqlParametersMaps ().put ("oppkey", oppkey).put ("segmentKey", segmentKey);
      result = executeStatement (connection, SQL_QUERY4, parameters4, false).getResultSets ().next ();
      record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        oppitems = record.<Long> get ("count").intValue ();
      }
      if (DbComparator.lessOrEqual (ftneed, ftcnt) || DbComparator.isEqual (oppitems,  0))
      {
        res = true;
      }
    }
    else if (DbComparator.isEqual (algorithm, FIXEDFORM) && DbComparator.isEqual (opneed, opcnt + ftcnt)) {
      res = true;
    }

    return res;
  }

  // From TDSCore_Dev_Session2012_Sandbox
  public void _AA_NextFixedformGroup_SP (SQLConnection connection, UUID oppkey,
      String segmentKey,
      String language,
      _Ref<String> groupIDValue,
      _Ref<String> blockIDValue) throws ReturnStatusException
  {
    Date starttime = _dateUtil.getDateWRetStatus (connection);

    int relativePosition;
    Integer firstPosition = null;
    Integer lastPosition = null;
    String formKey = null;

    Integer segmentPosition = null;

    final String SQL_QUERY = "select formKey, segmentPosition "
        + "from testopportunitysegment where _fk_TestOpportunity = ${oppkey} and _efk_Segment = ${segmentKey}";

    SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey).put ("segmentKey", segmentKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY, parameters, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      segmentPosition = record.<Integer> get ("segmentPosition");
      formKey = record.<String> get ("formKey");
    }

    final String SQL_QUERY2 = "select min(position) as firstPosition, max(position) as lastPosition "
        + " from testeeresponse where _fk_TestOpportunity = ${oppkey} and segment = ${segmentPosition}";

    SqlParametersMaps parameters2 = new SqlParametersMaps ().put ("oppkey", oppkey).put ("segmentPosition", segmentPosition);
    result = executeStatement (connection, SQL_QUERY2, parameters2, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      firstPosition = record.<Integer> get ("firstPosition");
      lastPosition = record.<Integer> get ("lastPosition");
    }

    if (firstPosition == null)
    {
      firstPosition = new Integer (0);
      lastPosition = new Integer (-1);
    }
    relativePosition = lastPosition + 1 - firstPosition + 1;

    final String SQL_QUERY3 = "select groupID, blockID "
        + " from ${ItemBankDB}.testformitem F, ${ItemBankDB}.tblsetofadminitems I "
        + " where F._fk_adminsubject = ${segmentKey} and F._fk_TestForm = ${formKey} "
        + " and F.FormPosition = ${relativePosition} and I._fk_AdminSubject = ${segmentKey} and I._fk_Item = F._fk_Item";

    SqlParametersMaps parameters3 = new SqlParametersMaps ().put ("segmentKey", segmentKey).put ("formKey", formKey).put ("relativePosition", relativePosition);
    String query = fixDataBaseNames (SQL_QUERY3);
    result = executeStatement (connection, query, parameters3, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      groupIDValue.set (record.<String> get ("groupID"));
      blockIDValue.set (record.<String> get ("blockID"));
    }

    _commonDll._LogDBLatency_SP (connection, "_AA_NextFixedformGroup", starttime, null, true, null, oppkey);
  }

  // From TDSCore_Dev_Session2012_Sandbox
  // This function uses update query
  /* (non-Javadoc)
   * @see tds.dll.api.IItemSelectionDLL#_AA_NextFieldtestGroup_SP(AIR.Common.DB.SQLConnection, java.util.UUID, int, java.lang.String, java.lang.String, java.lang.String, AIR.Common.Helpers._Ref, AIR.Common.Helpers._Ref, boolean)
   */
  @Override
  public void _AA_NextFieldtestGroup_SP (SQLConnection connection, UUID oppkey,
      int segmentPosition,
      String segmentKey,
      String segmentID,
      String language,
      _Ref<String> groupID,
      _Ref<String> blockID,
      boolean debug) throws ReturnStatusException
  {
    Date starttime = _dateUtil.getDateWRetStatus (connection);

    int relativePosition;
    Integer firstPosition = null;
    Integer lastPosition = null;
    int itemcnt;

    SqlParametersMaps parameters;
    SingleDataResultSet result;
    DbResultRecord record;

    String SQL_QUERY = "select min(position) as firstPosition, max(position) as lastPosition "
        + "from testeeresponse where _fk_TestOpportunity = ${oppkey} and segment = ${segmentPosition}";

    parameters = new SqlParametersMaps ().put ("oppkey", oppkey).put ("segmentPosition", segmentPosition);
    result = executeStatement (connection, SQL_QUERY, parameters, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      firstPosition = record.<Integer> get ("firstPosition");
      lastPosition = record.<Integer> get ("lastPosition");
    }

    if (firstPosition == null)
    {
      firstPosition = new Integer (0);
      lastPosition = new Integer (-1);
    }
    relativePosition = (lastPosition + 1 - firstPosition) + 1;

    if (debug)
    {
      _logger.debug("First position = " + firstPosition + "; last position = " + lastPosition + "; relative position = " + relativePosition);
    }

    SQL_QUERY = "select groupID, blockID "
        + " from ft_opportunityitem   where _fk_TestOpportunity = ${oppkey} "
        + "and _efk_FieldTest = ${segmentKey} and language = ${language} and positionAdministered is null "
        + "and position <= ${relativePosition} "
        + "and coalesce(deleted, 0) = 0 order by position limit 1";

    parameters = new SqlParametersMaps ().put ("oppkey", oppkey).put ("segmentKey", segmentKey).put ("language", language).put ("relativePosition", relativePosition);
    result = executeStatement (connection, SQL_QUERY, parameters, true).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      String grID = record.<String> get ("groupID");
      String blID = record.<String> get ("blockID");
      groupID.set (grID);
      blockID.set (blID);
    }

    if (groupID.get () == null)
    {
      if(debug)
      {
      _logger.debug  ("Error ocurred in \"_AA_NextFieldtestGroup_SP\" method: No item in position");
      }
      return;
    }

    /**
     * Comments from DB: -- If groupID is not null, determine whether it
     * contains any items with properties which match testopp accommodations --
     * If there are no items meeting the accommodation requirements, then delete
     * the itemgroup from this opportunity
     */
    DataBaseTable tbl = AF_GetItempool_FN (connection, oppkey, segmentKey, segmentID, true, groupID.get (), blockID.get ());
    
   	SQL_QUERY = "select * from ${tblName}";
    Map<String, String> par = new HashMap<String, String> ();
    par.put ("tblName", tbl.getTableName ());

    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY, par), null, false).getResultSets ().next ();
    itemcnt = result.getCount();
    
    if (DbComparator.isEqual (itemcnt, 0))
    {
      if (debug) // -- then there are no items in this group that meet the
                 // student's accommodation requirement
      {
        _logger.debug ("Positioned items don't qualify, deleting");
      }
      SQL_QUERY = "update ft_opportunityitem set deleted = 1 "
          + " where _fk_TestOpportunity = ${oppkey} and _efk_FieldTest = ${segmentKey} and groupID = ${groupID} ";

      parameters = new SqlParametersMaps ().put ("oppkey", oppkey).put ("segmentKey", segmentKey).put ("groupID", groupID.get ());
      executeStatement (connection, SQL_QUERY, parameters, false);

      groupID.set (null);
      blockID.set (null);

    }

    _commonDll._LogDBLatency_SP (connection, "_AA_NextFieldtestGroup", starttime, null, true, null, oppkey);
  }

	public DataBaseTable AF_GetItempool_FN(SQLConnection connection,
			UUID oppkey, String segmentKey, String segmentID,
			Boolean fieldTest, String groupID, String blockID)
			throws ReturnStatusException {
	    DataBaseTable pool = getDataBaseTable ("tmpTable")
	            .addColumn ("itemkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50)
	            .addColumn ("propsin", SQL_TYPE_To_JAVA_TYPE.INT)
	            .addColumn ("groupKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100)
	            .addColumn ("groupID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50)
	            .addColumn ("blockID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50)
	            .addColumn ("_fk_Item", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50)
	            .addColumn ("isActive", SQL_TYPE_To_JAVA_TYPE.BIT)
	            .addColumn ("itemPosition", SQL_TYPE_To_JAVA_TYPE.INT)
	            .addColumn ("IRT_Model", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50)
	            .addColumn ("IRT_a", SQL_TYPE_To_JAVA_TYPE.FLOAT)
	            .addColumn ("IRT_b", SQL_TYPE_To_JAVA_TYPE.FLOAT) 
	            .addColumn ("IRT_c", SQL_TYPE_To_JAVA_TYPE.FLOAT)
	            .addColumn ("strand", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200)
	            .addColumn ("bVector", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200) 
	            .addColumn ("isRequired", SQL_TYPE_To_JAVA_TYPE.BIT)
	            .addColumn ("isFieldTest", SQL_TYPE_To_JAVA_TYPE.BIT);

	        connection.createTemporaryTable (pool);


		long propsin = 0;
		String clientname = null;
		UUID session = null;
		SqlParametersMaps parameters;
		SingleDataResultSet result;
		DbResultRecord record;

		String SQL_QUERY = "select clientname, _fk_Session as session "
				+ " from testopportunity where _Key = ${oppkey}";

		parameters = new SqlParametersMaps().put("oppkey", oppkey);
		result = executeStatement(connection, SQL_QUERY, parameters, false)
				.getResultSets().next();
		record = result.getCount() > 0 ? result.getRecords().next() : null;
		if (record != null) {
			clientname = record.<String> get("clientname");
			session = record.<UUID> get("session");
		}

		SQL_QUERY = "select count(distinct propname) as propsin "
				+ " from testeeaccommodations A, ${ConfigDB}.client_test_itemconstraint C "
				+ " where A._fk_TestOpportunity = ${oppkey} and A.AccType = C.ToolTYpe and A.AccCode = C.ToolValue"
				+ " and C.Clientname = ${clientname} and C.testID = ${segmentID}";

		parameters.put("clientname", clientname);
		parameters.put("segmentID", segmentID);
		
		SQL_QUERY = fixDataBaseNames(SQL_QUERY);
		result = executeStatement(connection, SQL_QUERY, parameters, false)
				.getResultSets().next();
		record = result.getCount() > 0 ? result.getRecords().next() : null;
		if (record != null) {
			propsin = record.<Long> get("propsin");
		}

		String SQL_INSERT = "INSERT into ${tblName} (itemkey, _fk_Item, groupKey, "
				+ " groupID, blockID, strand, bVector, "
				+ " IRT_Model, IRT_a, IRT_b, IRT_c, "
				+ " itemPosition, isRequired, isFieldTest, isActive) "
		        + " select  _fk_ITem, _fk_Item, I.groupKey, "
		        + " I.groupID, I.blockID, I.strandname, I.bVector, "
		        + " I.IRT_model, coalesce(I.IRT_a, 1), I.IRT_b, coalesce(I.IRT_c, 0), "
		        + " I.itemPosition, I.isrequired, I.isFieldTest, I.isActive "
				+ " from ${ItemBankDB}.tblsetofadminitems I "
				+ " where _fk_AdminSUbject = ${segmentKey} and (${groupID} is null or groupID = ${groupID}) "
				+ " and (${blockID} is null or blockID = ${blockID})";
		
	    String query = fixDataBaseNames (SQL_INSERT);
	    Map<String, String> unquotedparms = new HashMap<String, String> ();
	    unquotedparms.put ("tblName", pool.getTableName ());
	    parameters.put ("segmentKey", segmentKey);
	    parameters.put ("groupID", groupID);
	    parameters.put ("blockID", blockID);
	    int insertedCnt = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, true).getUpdateCount ();

	    _logger.info ("Inserted rows after Insert query in temporary table in AF_GetItempool_FN are " + insertedCnt);

	    String SQL_QUERY2 = "delete from ${tblName} where exists ( select * "
	    		+ " from ${ConfigDB}.client_test_itemconstraint C2, "
	            + " testeeaccommodations A2, ${ItemBankDB}.tblitemprops P2 "
	            + " where A2._fk_TestOpportunity = ${oppkey} "
	            + " and C2.Clientname = ${clientname} and C2.testID = ${segmentID} and C2.item_in = 0 "
	            + " and A2.AccType = C2.ToolType and A2.AccCode = C2.ToolValue "
	            + " and P2._fk_AdminSubject = ${segmentKey} "
	            + " and P2._fk_Item  = itemKey "
	            + " and P2.Propname = C2.propname and P2.Propvalue = C2.Propvalue) ";

	    query = fixDataBaseNames (SQL_QUERY2);
	    int deletedCnt = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, true).getUpdateCount ();

	    _logger.info ("deleted rows after delete query in temporary table in AF_GetItempool_FN are " + deletedCnt);

	    String SQL_QUERY3 = "update  ${tblName} set propsin = (select count(distinct C.propname) "
	            + " from ${ConfigDB}.client_test_itemconstraint C, "
	            + " testeeaccommodations A, ${ItemBankDB}.tblitemprops P "
	            + " where A._fk_TestOpportunity = ${oppkey} "
	            + " and C.Clientname = ${clientname} and C.testID = ${segmentID} and C.item_in = 1 " 
	            + " and A.AccType = C.ToolType and A.AccCode = C.ToolValue "
	            + " and P._fk_Item  = itemKey "
	            + " and P._fk_AdminSubject = ${segmentKey} "
	            + " and P.Propname = C.propname and P.Propvalue = C.Propvalue )";
	    
	    query = fixDataBaseNames (SQL_QUERY3);
	    int updatedCnt = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, true).getUpdateCount ();

	    _logger.info ("updated rows after update query in temporary table in AF_GetItempool_FN are " + updatedCnt);

		if (IsSimulation_FN(connection, oppkey))
		{
		    String SQL_QUERY4 = "update ${tblName} set isRequired = S.isRequired, isActive = S.isActive"
		            + " from sim_segmentitem S where S._fk_Session = ${session} "
		            + " and S._efk_Segment = ${segmentKey} "
		            + " and S._efk_Item = itemkey";
		    parameters.put ("session", session);
		    updatedCnt = executeStatement (connection, fixDataBaseNames (SQL_QUERY4, unquotedparms), parameters, true).getUpdateCount ();

		    _logger.info ("updated rows after updated query in temporary table in AF_GetItempool_FN are " + updatedCnt + " isSimulation = true");
		}
		
	    String SQL_QUERY5 = "delete from ${tblName} where propsin < ${propsin} or isActive = 0 ";
	    
	    parameters.put ("propsin", propsin);
	    deletedCnt = executeStatement (connection, fixDataBaseNames (SQL_QUERY5, unquotedparms), parameters, true).getUpdateCount ();

	    _logger.info ("deleted rows after delete query in temporary table in AF_GetItempool_FN are " + deletedCnt);

	    if(fieldTest != null)
	    {
		    String SQL_QUERY6 = "delete from ${tblName} where isFieldTest <> ${fieldTest} ";
		    
		    parameters.put ("fieldTest", fieldTest);
		    deletedCnt = executeStatement (connection, fixDataBaseNames (SQL_QUERY6, unquotedparms), parameters, true).getUpdateCount ();

		    _logger.info ("deleted rows after delete query in temporary table in AF_GetItempool_FN are " + deletedCnt);	    	
	    }
		return pool;
	}
  // Optimal variant?
  public SingleDataResultSet AF_GetItempool_FN_2 (SQLConnection connection, UUID oppkey,
	      String segmentKey,
	      String segmentID,
	      Boolean fieldTest,
	      String groupID,
	      String blockID) throws ReturnStatusException
	  {
	    SingleDataResultSet pool = null;

	    long propsin = 0;
	    String clientname = null;
	    UUID session = null;
	    // Comment from BD:
	    // -- declare @pool table (itemkey varchar(50) primary key not null, propsin
	    // int);
	    SqlParametersMaps parameters;
	    SingleDataResultSet result;
	    DbResultRecord record;

	    String SQL_QUERY = "select clientname, _fk_Session as session "
	        + " from testopportunity where _Key = ${oppkey}";

	    parameters = new SqlParametersMaps ().put ("oppkey", oppkey);
	    result = executeStatement (connection, SQL_QUERY, parameters, false).getResultSets ().next ();
	    record = result.getCount () > 0 ? result.getRecords ().next () : null;
	    if (record != null) {
	      clientname = record.<String> get ("clientname");
	      session = record.<UUID> get ("session");
	    }

	    // Coment from BD: -- How many 'include' item properties are there for this
	    // test opp?
	    SQL_QUERY = "select count(distinct propname) as propsin "
	        + " from testeeaccommodations A, ${ConfigDB}.client_test_itemconstraint C "
	        + " where A._fk_TestOpportunity = ${oppkey} and A.AccType = C.ToolTYpe and A.AccCode = C.ToolValue"
	        + " and C.Clientname = ${clientname} and C.testID = ${segmentID}";

	    parameters = new SqlParametersMaps ().put ("oppkey", oppkey).put ("clientname", clientname).put ("segmentID", segmentID);
	    SQL_QUERY = fixDataBaseNames (SQL_QUERY);
	    result = executeStatement (connection, SQL_QUERY, parameters, false).getResultSets ().next ();
	    record = result.getCount () > 0 ? result.getRecords ().next () : null;
	    if (record != null) {
	      propsin = record.<Long> get ("propsin");
	    }

	    // Coment from BD:-- get all the items with their 'includes' property count
	    //
	    // ==========================!!!!!!!!!!!!!!!!!======================================================================================================
	    // new table pool will be have following columns and types:
	    // itemkey, propsin, _fk_Item, groupKey, groupID, blockID, strand, bVector,
	    // IRT_model, IRT_a, IRT_b, IRT_c, itemPosition, isRequired, isFieldTest,
	    // isActive
	    // String, int, String, String, String, String, String, String, String,
	    // float, String, float, int, boolean, boolean, boolean

	    SQL_QUERY = "select _fk_ITem as itemkey, _fk_Item, I.groupKey, I.groupID, "
	        + " I.blockID, I.strandname as strand, I.bVector, I.IRT_model, "
	        + " coalesce(I.IRT_a, 1) as IRT_a, I.IRT_b, coalesce(I.IRT_c, 0) as IRT_c,"
	        + " I.itemPosition, I.isRequired, I.isFieldTest, I.isActive "
	        + " from ${ItemBankDB}.tblsetofadminitems I "
	        + " where _fk_AdminSUbject = ${segmentKey} and (${groupID} is null or groupID = ${groupID}) "
	        + " and (${blockID} is null or blockID = ${blockID})";

	    parameters = new SqlParametersMaps ().put ("segmentKey", segmentKey).put ("groupID", groupID).put ("blockID", blockID);
	    SQL_QUERY = fixDataBaseNames (SQL_QUERY);
	    pool = executeStatement (connection, SQL_QUERY, parameters, false).getResultSets ().next ();
	    // delete from SingleDataResultSet pool
	    deleteFromItemPool (connection, pool, oppkey, clientname, segmentID, segmentKey);
	    // update SingleDataResultSet pool
	    updateItemPool (connection, pool, oppkey, clientname, segmentID, segmentKey);

	    // Comment from BD:-- Join with item table to get active items with required
	    // number of properties and fieldtest criteria
	    if (IsSimulation_FN (connection, oppkey))// Comment from BD: -- then this is
	                                             // a simulation, use simulation's
	                                             // isrequired and isactive flags
	    {// update SingleDataResultSet pool
	      updateItemPoolIfIsSimulation (connection, pool, session, segmentKey);
	    }
	    // delete from SingleDataResultSet pool
	    deleteFromItemPool2 (pool, propsin, segmentKey, fieldTest);

	    return pool;
	  }

  // doesn't use delete query: delete from SingleDataResultSet pool
  protected void deleteFromItemPool (SQLConnection connection,
      SingleDataResultSet pool,
      UUID oppkey,
      String clientname,
      String segmentID,
      String segmentKey) throws ReturnStatusException
  {
    String itemKey = null;
    SingleDataResultSet res = null;
    SqlParametersMaps parameters;
    DbResultRecord record;
    // itemKey from pool!
    String SQL_QUERY = "select distinct _fk_Item as itemKey from ${ConfigDB}.client_test_itemconstraint C2, "
        + " testeeaccommodations A2, ${ItemBankDB}.tblitemprops P2 "
        + " where A2._fk_TestOpportunity = ${oppkey} "
        + " and C2.Clientname = ${clientname} and C2.testID = ${segmentID} and C2.item_in = 0 "
        + " and A2.AccType = C2.ToolType and A2.AccCode = C2.ToolValue "
        + " and P2._fk_AdminSubject = ${segmentKey} "
        // + " and P2._fk_Item  = itemKey "
        + " and P2.Propname = C2.propname and P2.Propvalue = C2.Propvalue ";

    parameters = new SqlParametersMaps ().put ("oppkey", oppkey).put ("segmentKey", segmentKey);
    parameters.put ("clientname", clientname).put ("segmentID", segmentID);
    SQL_QUERY = fixDataBaseNames (SQL_QUERY);
    res = executeStatement (connection, SQL_QUERY, parameters, false).getResultSets ().next ();

    Iterator<DbResultRecord> resItr = res.getRecords ();
    while (resItr.hasNext ())
    {
      record = resItr.next ();
      itemKey = record.<String> get ("itemKey");
      Iterator<DbResultRecord> poolItr = pool.getRecords ();
      while (poolItr.hasNext ())
      {
        record = poolItr.next ();
        if (itemKey.equals (record.<String> get ("itemKey")))
        {
          poolItr.remove ();
        }
      }
    }
  }

  // pool is updated SingleDataResultSet
  protected void updateItemPool (SQLConnection connection,
      SingleDataResultSet pool,
      UUID oppkey,
      String clientname,
      String segmentID,
      String segmentKey) throws ReturnStatusException
  {
    long propsin = 0;
    SingleDataResultSet res = null;
    SqlParametersMaps parameters;
    DbResultRecord record;

    String SQL_QUERY = "select count(distinct C2.propname) as propsin "
        + " from ${ConfigDB}.client_test_itemconstraint C2, "
        + " testeeaccommodations A2, ${ItemBankDB}.tblitemprops P2 "
        + " where A2._fk_TestOpportunity = ${oppkey} "
        + " and C2.Clientname = ${clientname} and C2.testID = ${segmentID} and C2.item_in = 1 " // difference
                                                                                                // from
                                                                                                // previous
                                                                                                // method
                                                                                                // QUERY
        + " and A2.AccType = C2.ToolType and A2.AccCode = C2.ToolValue "
        + " and P2._fk_AdminSubject = ${segmentKey} "
        // + " and P2._fk_Item  = itemKey "
        + " and P2.Propname = C2.propname and P2.Propvalue = C2.Propvalue ";

    parameters = new SqlParametersMaps ().put ("oppkey", oppkey).put ("segmentKey", segmentKey);
    parameters.put ("clientname", clientname).put ("segmentID", segmentID);
    SQL_QUERY = fixDataBaseNames (SQL_QUERY);
    res = executeStatement (connection, SQL_QUERY, parameters, false).getResultSets ().next ();

    record = res.getCount () > 0 ? res.getRecords ().next () : null;
    if (record != null) {
      propsin = record.<Long> get ("propsin");
    }

    pool.addColumn ("propsin", SQL_TYPE_To_JAVA_TYPE.BIGINT);
    Iterator<DbResultRecord> poolItr = pool.getRecords ();
    while (poolItr.hasNext ())
    {
      record = poolItr.next ();
      record.addColumnValue ("propsin", propsin);
    }
  }

  // pool is updated SingleDataResultSet
  protected void updateItemPoolIfIsSimulation (SQLConnection connection, SingleDataResultSet pool, UUID session, String segmentKey) throws ReturnStatusException
  {// -- then this is a simulation, use simulation's isrequired and isactive
   // flags

    SingleDataResultSet res = null;
    SqlParametersMaps parameters;
    DbResultRecord poolRecord;
    DbResultRecord tmpRecord;
    // itemKey from pool!
    String SQL_QUERY = "select isRequired, isActive, _efk_Item  as itemKey"
        + " from sim_segmentitem where _fk_Session = ${session} and _efk_Segment = ${segmentKey} ";

    parameters = new SqlParametersMaps ().put ("session", session).put ("segmentKey", segmentKey);
    res = executeStatement (connection, SQL_QUERY, parameters, false).getResultSets ().next ();
    Iterator<DbResultRecord> poolItr = pool.getRecords ();
    while (poolItr.hasNext ())
    {
      poolRecord = poolItr.next ();
      Iterator<DbResultRecord> tmpItr = res.getRecords ();
      while (tmpItr.hasNext ())
      {
        tmpRecord = tmpItr.next ();
        if (poolRecord.<String> get ("itemKey") == tmpRecord.<String> get ("itemKey"))
        {
          poolRecord.addColumnValue ("isRequired", tmpRecord.<Boolean> get ("isRequired"));
          poolRecord.addColumnValue ("isActive", tmpRecord.<Boolean> get ("isActive"));
        }
      }
    }
  }

  // pool is updated SingleDataResultSet: we delete records
  protected void deleteFromItemPool2 (SingleDataResultSet pool, long propsin, String segmentKey, Boolean fieldTest)
  {
    DbResultRecord record;

    /**
     * 
     * delete from @pool where propsin < @propsin or isActive = 0; if
     * (@fieldTest is not null) delete from @pool where isFieldTest <>
     * 
     * @fieldTest;
     */
    Iterator<DbResultRecord> poolItr = pool.getRecords ();
    while (poolItr.hasNext ())
    {
      record = poolItr.next ();
      if (propsin < record.<Long> get ("propsin")
          || !record.<Boolean> get ("isActive")
          || (fieldTest != null && (fieldTest ^ record.<Boolean> get ("isFieldTest")))) // TODO:
                                                                                        // (AK)
                                                                                        // check
                                                                                        // it
      {
        poolItr.remove ();
      }
    }
  }

  //
  public MultiDataResultSet AA_GetItemgroup_SP (SQLConnection connection, UUID oppkey,
      String segmentKey,
      String groupID,
      String blockID,
      Boolean isFieldTest,
      Boolean gebug
      ) throws ReturnStatusException
  {
    List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet> ();
    Date starttime = _dateUtil.getDateWRetStatus (connection);

    String clientName = null;
    String formKey = null;
    String algorithm = null;
    String testID = null;
    //String segmentID = null;
    UUID session = null;
    Boolean isSim = IsSimulation_FN (connection, oppkey);

    SingleDataResultSet result;
    DbResultRecord record;

    final String SQL_QUERY1 = "select clientname, _fk_Session as session, _efk_TestID as testID from testopportunity where _Key = ${oppkey}";

    SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey);
    result = executeStatement (connection, SQL_QUERY1, parameters, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      clientName = record.<String> get ("clientname");
      session = record.<UUID> get ("session");
      testID = record.<String> get ("testID");
    }

    // -- will need segmentID to find item filtering accommodations in
    // TDSCONFIGS
    final String SQL_QUERY2 = "select formKey, algorithm, segmentID"
        + " from testopportunitysegment where _fk_TestOpportunity = ${oppkey} "
        + " and _efk_Segment = ${segmentKey}";

    parameters.put ("segmentKey", segmentKey).put ("groupID", groupID).put ("blockID", blockID);
    result = executeStatement (connection, SQL_QUERY2, parameters, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      formKey = record.<String> get ("formKey");
      algorithm = record.<String> get ("algorithm");
      //segmentID = record.<String> get ("segmentID");
    }
    SingleDataResultSet res1 = null;
    String likeRecord = LIKESTRING1;
    // -- send the next itemgroup together with items
    if (groupID != null && groupID.startsWith (likeRecord))
    { // -- let the item's required flag determine numRequired

      List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
      CaseInsensitiveMap<Object> rcrd = new CaseInsensitiveMap<Object> ();
      rcrd.put ("groupID", groupID);
      rcrd.put ("numRequired", 0);
      rcrd.put ("maxItems", -1);

      resultList.add (rcrd);
     
      res1 = new SingleDataResultSet ();
      res1.addColumn ("groupID", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      res1.addColumn ("numRequired", SQL_TYPE_To_JAVA_TYPE.INT);
      res1.addColumn ("maxItems", SQL_TYPE_To_JAVA_TYPE.INT);
            
      res1.addRecords (resultList);

   }
    else
    {
      Integer simMax = null;
      if (isSim) // -- then the 'proctor' may have altered the group's max items for the simulation
      {
        final String SQL_QUERY4 = "select S.maxItems as simMax from sim_itemgroup S "
            + " where S._fk_Session = ${session} and S._efk_Segment = ${segmentKey} "
            + " and S.groupID = ${groupID}";
        parameters.put ("session", session);
        result = executeStatement (connection, SQL_QUERY4, parameters, false).getResultSets ().next ();
        record = result.getCount () > 0 ? result.getRecords ().next () : null;
        if (record != null) {
          simMax = record.<Integer> get ("simMax");
        }
      }
      final String SQL_QUERY5 = "select  groupID, numItemsRequired as numRequired, "
          + " coalesce(${simMax}, maxItems) as maxItems "
          + " from ${ItemBankDB}.tbladminstimulus "
          + " where _fk_AdminSubject = ${segmentKey} and groupID = ${groupID}";
      parameters.put ("simMax", simMax);
      res1 = executeStatement (connection, fixDataBaseNames (SQL_QUERY5), parameters, false).getResultSets ().next ();
    }
    // this dataSet contains groupID, numRequired, maxItems
    resultsets.add (res1);

    SingleDataResultSet res2;
    if (DbComparator.isEqual (algorithm, FIXEDFORM))
    {
      // -- NOTE: Items will be positioned by form position by T_InsertItems
      // -- Item filtering even by language is not applicable to fixed form
      // tests.
      final String SQL_QUERY6 = "select I._fk_Item as itemID, groupID, coalesce(${blockID}, 'NA') as blockID"
          + " , itemPosition, strandName as strand, isRequired, coalesce(I.IRT_a, 1) as IRT_a, I.IRT_b"
          + " , coalesce(I.IRT_c, 0) as IRT_c, upper(I.IRT_Model) as IRT_Model "
          + " , bVector, isFieldTest, formPosition "
          + " from ${ItemBankDB}.tblsetofadminitems I, ${ItemBankDB}.testformitem M "
          + " where M._fk_TestForm = ${formkey} and I.groupID = ${groupID} and I._fk_Item = M._fk_Item "
          + " and I._fk_AdminSubject = ${segmentKey} order by itemPosition";
      parameters.put ("formkey", formKey);
      res2 = executeStatement (connection, fixDataBaseNames (SQL_QUERY6), parameters, false).getResultSets ().next ();
      resultsets.add (res2);
    }
    else
    {
      String SQL_QUERY6 = "select _fk_Item as itemID, groupID, coalesce(${blockID}, 'NA') as blockID"
          + ", itemPosition,  strand, isRequired "
          + ", IRT_b, IRT_a, IRT_c, upper(IRT_Model) as IRT_Model, bVector, isFieldTest, itemPosition as formPosition "
          + " from  ${tmpTableName} "
          + " order by itemPosition";
      parameters.put ("blockID", blockID);
      Map<String, String> unquotedParms1 = new HashMap<String, String> ();

      DataBaseTable tmpTable = null;
      if (!isSim) // is not Simulation
      {
        tmpTable = _AA_GetCustomItemgroup_FN (connection, clientName, oppkey, segmentKey, testID,
            isFieldTest, groupID, blockID);
        unquotedParms1.put ("tmpTableName", tmpTable.getTableName ());
        res2 = executeStatement (connection, fixDataBaseNames (SQL_QUERY6, unquotedParms1), parameters, false).getResultSets ().next ();
        resultsets.add (res2);

      }
      else // Simulation
      {
        tmpTable = _AA_SIM_GetCustomItemgroup_FN (connection, clientName, oppkey, segmentKey, testID,
            isFieldTest, session, groupID, blockID);
        unquotedParms1.put ("tmpTableName", tmpTable.getTableName ());
        res2 = executeStatement (connection, fixDataBaseNames (SQL_QUERY6, unquotedParms1), parameters, false).getResultSets ().next ();
        resultsets.add (res2);
      }
      try
      {
        connection.dropTemporaryTable (tmpTable);
      } catch (Exception e)
      {
        String error = "Error occured when tried to drop temporary table in AA_GetItemgroup_SP method : ";
        _logger.error (error + e.getMessage ());
        // throw new ReturnStatusException (error + e.getMessage ());
      }

    }
    _commonDll._LogDBLatency_SP (connection, "AA_GetItemgroup", starttime, null, true, null, oppkey);

    return new MultiDataResultSet (resultsets);
  }

  /**
   * Comments from DB: -- Customizes the itemgroup for the testee's
   * accommodations -- Get only the items that meet the testee's accommodations
   * -- Every item must match all required constraint 'item_in' properties and
   * not match any item_out properties. -- Constraints are actuated by the
   * assignment to this test opp as a TesteeAccommodation. -- There are only 2
   * types of constraints: Language, and TDSPoolFilter. The most common
   * TDSPoolFilter constraints are by itemtype. -- Note that the group itself
   * has already passed muster via _AA_GetCustomItempool
   */
  public DataBaseTable _AA_GetCustomItemgroup_FN (SQLConnection connection,
      String clientname,
      UUID oppkey,
      String segmentKey,
      String testID,
      Boolean fieldTest,
      String groupID,
      String blockID) throws ReturnStatusException
  {
    DataBaseTable resDBTable = getDataBaseTable ("tmpTable")
        .addColumn ("itemkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150)
        .addColumn ("_fk_Item", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150)
        .addColumn ("groupKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100)
        .addColumn ("groupID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100)
        .addColumn ("blockID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 10)
        .addColumn ("strand", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150)
        .addColumn ("bVector", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200) // TODO: (AK)
                                                                   // will be
                                                                   // changed
                                                                   // with
                                                                   // AAlgorithm
                                                                   // changes in
                                                                   // DB
        .addColumn ("IRT_model", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100)

        .addColumn ("IRT_a", SQL_TYPE_To_JAVA_TYPE.FLOAT)
        .addColumn ("IRT_b", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150) // TODO: (AK)
                                                                 // will be
                                                                 // changed with
                                                                 // AAlgorithm
                                                                 // changes in
                                                                 // DB
        .addColumn ("IRT_c", SQL_TYPE_To_JAVA_TYPE.FLOAT)
        .addColumn ("itemPosition", SQL_TYPE_To_JAVA_TYPE.INT)

        .addColumn ("isRequired", SQL_TYPE_To_JAVA_TYPE.BIT)
        .addColumn ("isFieldTest", SQL_TYPE_To_JAVA_TYPE.BIT)
        .addColumn ("isActive", SQL_TYPE_To_JAVA_TYPE.BIT);

    connection.createTemporaryTable (resDBTable);

    final String SQL_INSERT = "INSERT into ${tblName} (itemkey, _fk_Item, groupKey, groupID, blockID"
        + ", strand, bVector, IRT_model, IRT_a"
        + ", IRT_b, IRT_c, itemPosition"
        + ", isRequired, isFieldTest, isActive) "
        + " select I._fk_ITem as itemkey"
        + ", I._fk_Item"
        + ", I.groupKey"
        + ", I.groupID"
        + ", I.blockID"
        + ", I.strandname as strand"
        + ", I.bVector"
        + ", I.IRT_model "
        + ", coalesce(I.IRT_a, 1) as IRT_a"
        + ", I.IRT_b"
        + ", coalesce(I.IRT_c, 0) as IRT_c"
        + ", I.itemPosition"
        + ", I.isRequired"
        + ", I.isFieldTest"
        + ", I.isActive "
        + " from ${ItemBankDB}.tblsetofadminitems I  "
        + ", ${ConfigDB}.client_test_itemconstraint C1  "
        + ", testeeaccommodations A1  "
        + ", ${ItemBankDB}.tblitemprops P1   "
        + " where I._fk_AdminSUbject = ${segmentKey} "
        + " and I.isActive = 1"
        + " and (${fieldTest} is null or I.isFieldTest = ${fieldTest}) "
        + " and C1.Clientname = ${clientname} "
        + " and C1.testID = ${testID} "
        + " and C1.item_in = 1 "
        + " and A1._fk_TestOpportunity = ${oppkey} "
        + " and A1.AccType = C1.ToolType "
        + " and A1.AccCode = C1.ToolValue "
        + " and P1._fk_AdminSubject = ${segmentKey} "
        + " and P1._fk_Item  = I._fk_Item "
        + " and P1.Propname = C1.propname "
        + " and P1.Propvalue = C1.Propvalue "
        + " and P1.isactive = 1 "
        + " and I.groupID = ${groupID} "
        + " and (${blockID} is null or I.blockID = ${blockID}) "
        + " and not exists " // -- check for 'item_out' constraints
        + " (select * from ${ConfigDB}.client_test_itemconstraint C2  "
        + ", testeeaccommodations A2  "
        + ", ${ItemBankDB}.tblitemprops P2   "
        + " where A2._fk_TestOpportunity = ${oppkey} "
        + " and C2.Clientname = ${clientname} "
        + " and C2.testID = ${testID} "
        + " and C2.item_in = 0 "
        + " and A2.AccType = C2.ToolType "
        + " and A2.AccCode = C2.ToolValue "
        + " and A2.AccType = C2.ToolType "
        + " and A2.AccCode = C2.ToolValue "
        + " and P2._fk_AdminSubject = ${segmentKey} "
        + " and P2._fk_Item  = I._fk_Item "
        + " and P2.Propname = C2.propname "
        + " and P2.Propvalue = C2.Propvalue "
        + " and P2.isactive = 1"
        + ") "
        // -- each item must have the same number of records as the number of
        // 'item_in' constraints
        + " GROUP BY I._fk_ITem "
        + ", I._fk_Item"
        + ", I.groupKey"
        + ", I.groupID"
        + ", I.blockID"
        + ", I.strandname "
        + ", I.bVector"
        + ", I.IRT_model "
        + ", IRT_a, I.IRT_b"
        + ", IRT_c"
        + ", I.itemPosition"
        + ", I.isRequired"
        + ", I.isFieldTest"
        + ", I.isActive "
        + " HAVING count(*) = "
        + "  (select count(*) from ${ConfigDB}.client_test_itemconstraint C1  "
        + ", testeeaccommodations A1   "
        + " where  C1.Clientname = ${clientname} "
        + " and C1.testID = ${testID} "
        + " and C1.item_in = 1 "
        + " and A1._fk_TestOpportunity = ${oppkey} "
        + " and A1.AccType = C1.ToolType "
        + " and A1.AccCode = C1.ToolValue) ";

    String query = fixDataBaseNames (SQL_INSERT);
    Map<String, String> unquotedparms = new HashMap<String, String> ();
    unquotedparms.put ("tblName", resDBTable.getTableName ());
    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("segmentKey", segmentKey);
    parameters.put ("fieldTest", fieldTest);
    parameters.put ("clientname", clientname);
    parameters.put ("testID", testID);
    parameters.put ("oppkey", oppkey);
    parameters.put ("groupID", groupID);
    parameters.put ("blockID", blockID);
    int insertedCnt = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, true).getUpdateCount ();

    _logger.info ("Insert count after Insert query in temporary table in _AA_GetCustomItemgroup_FN is " + insertedCnt);

    return resDBTable;
  }

  /**
   * Comments from DB: -- Get custom item group as altered by simulation session
   * -- Get all the items not administered on this test opp that meet the
   * testee's accommodations -- Every item must match all required constraint
   * 'item_in' properties and not match any item_out properties. -- Constraints
   * are actuated by the assignment to this test opp as a TesteeAccommodation.
   * -- There are only 2 types of constraints: Language, and TDSPoolFilter. The
   * most common TDSPoolFilter constraints are by itemtype.
   */
  public DataBaseTable _AA_SIM_GetCustomItemgroup_FN (SQLConnection connection,
      String clientname,
      UUID oppkey,
      String segmentKey,
      String segmentID,
      Boolean fieldTest,
      UUID session,
      String groupID,
      String blockID) throws ReturnStatusException
  {
    DataBaseTable resDBTable = getDataBaseTable ("tmpTable")
        .addColumn ("itemkey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150)
        .addColumn ("_fk_Item", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150)
        .addColumn ("groupKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100)
        .addColumn ("groupID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100)
        .addColumn ("blockID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 10)
        .addColumn ("strand", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150)
        .addColumn ("bVector", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200) // TODO: (AK)
                                                                   // will be
                                                                   // changed
                                                                   // with
                                                                   // AAlgorithm
                                                                   // changes in
                                                                   // DB
        .addColumn ("IRT_model", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100)

        .addColumn ("IRT_a", SQL_TYPE_To_JAVA_TYPE.FLOAT)
        .addColumn ("IRT_b", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 150) // TODO: (AK)
                                                                 // will be
                                                                 // changed with
                                                                 // AAlgorithm
                                                                 // changes in
                                                                 // DB
        .addColumn ("IRT_c", SQL_TYPE_To_JAVA_TYPE.FLOAT)
        .addColumn ("itemPosition", SQL_TYPE_To_JAVA_TYPE.INT)

        .addColumn ("isRequired", SQL_TYPE_To_JAVA_TYPE.BIT)
        .addColumn ("isFieldTest", SQL_TYPE_To_JAVA_TYPE.BIT)
        .addColumn ("isActive", SQL_TYPE_To_JAVA_TYPE.BIT);

    connection.createTemporaryTable (resDBTable);

    final String SQL_INSERT = "INSERT into ${tblName} (itemkey, _fk_Item, groupKey, groupID, blockID"
        + ", strand, bVector, IRT_model, IRT_a"
        + ", IRT_b, IRT_c, itemPosition"
        + ", isRequired, isFieldTest, isActive) "
        + " select I._fk_ITem as itemkey"
        + ", I._fk_Item"
        + ", I.groupKey"
        + ", I.groupID"
        + ", I.blockID"
        + ", I.strandname as strand"
        + ", I.bVector"
        + ", I.IRT_model "
        + ", coalesce(I.IRT_a, 1) as IRT_a"
        + ", I.IRT_b"
        + ", coalesce(I.IRT_c, 0) as IRT_c"
        + ", I.itemPosition"
        + ", SI.isRequired" // from SI table
        + ", I.isFieldTest"
        + ", SI.isActive " // from SI table
        + " from ${ItemBankDB}.tblsetofadminitems I  "
        + ", ${ConfigDB}.client_test_itemconstraint C1  "
        + ", testeeaccommodations A1  "
        + ", ${ItemBankDB}.tblitemprops P1   "
        + ", sim_segmentitem SI  " // Added new table
        + " where I._fk_AdminSUbject = ${segmentKey} "
        + " and SI.isActive = 1" // from SI table
        + " and (${fieldTest} is null or SI.isFieldTest = ${fieldTest}) " // from
                                                                          // SI
                                                                          // table
        + " and C1.Clientname = ${clientname} "
        + " and C1.testID = ${segmentID} "
        // added 3 new conditions:
        + " and SI._fk_Session = ${session} "
        + " and SI._efk_Segment = ${segmentKey} "
        + " and SI._efk_Item = I._fk_Item "
        //
        + " and C1.item_in = 1 "
        + " and A1._fk_TestOpportunity = ${oppkey} "
        + " and A1.AccType = C1.ToolType "
        + " and A1.AccCode = C1.ToolValue "
        + " and P1._fk_AdminSubject = ${segmentKey} "
        + " and P1._fk_Item  = I._fk_Item "
        + " and P1.Propname = C1.propname "
        + " and P1.Propvalue = C1.Propvalue "
        // + " and P1.isactive = 1 " // removed from simulation
        + " and I.groupID = ${groupID} "
        + " and (${blockID} is null or I.blockID = ${blockID}) "
        + " and not exists " // -- check for 'item_out' constraints
        + " (select * from ${ConfigDB}.client_test_itemconstraint C2  "
        + ", testeeaccommodations A2  "
        + ", ${ItemBankDB}.tblitemprops P2   "
        + " where A2._fk_TestOpportunity = ${oppkey} "
        + " and C2.Clientname = ${clientname} "
        + " and C2.testID = ${segmentID} "
        + " and C2.item_in = 0 "
        + " and A2.AccType = C2.ToolType "
        + " and A2.AccCode = C2.ToolValue "
        + " and A2.AccType = C2.ToolType "
        + " and A2.AccCode = C2.ToolValue "
        + " and P2._fk_AdminSubject = ${segmentKey} "
        + " and P2._fk_Item  = I._fk_Item "
        + " and P2.Propname = C2.propname "
        + " and P2.Propvalue = C2.Propvalue"
        // + " and P2.isactive = 1" // removed from simulation
        + ") "
        // -- each item must have the same number of records as the number of
        // 'item_in' constraints
        + " GROUP BY I._fk_ITem "
        + ", I._fk_Item, I.groupKey"
        + ", I.groupID, I.blockID"
        + ", I.strandname "
        + ", I.bVector"
        + ", I.IRT_model "
        + ",  IRT_a"
        + ", I.IRT_b"
        + ",  IRT_c"
        + ", I.itemPosition"
        + ", SI.isRequired" // from SI table
        + ", I.isFieldTest"
        + ", SI.isActive " // from SI table
        + " HAVING count(*) = "
        + "  (select count(*) from ${ConfigDB}.client_test_itemconstraint C1  "
        + ", testeeaccommodations A1   "
        + " where  C1.Clientname = ${clientname} "
        + " and C1.testID = ${segmentID} "
        + " and C1.item_in = 1 "
        + " and A1._fk_TestOpportunity = ${oppkey} "
        + " and A1.AccType = C1.ToolType "
        + " and A1.AccCode = C1.ToolValue) ";

    String query = fixDataBaseNames (SQL_INSERT);
    Map<String, String> unquotedparms = new HashMap<String, String> ();
    unquotedparms.put ("tblName", resDBTable.getTableName ());
    SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("segmentKey", segmentKey);
    parameters.put ("fieldTest", fieldTest);
    parameters.put ("clientname", clientname);
    parameters.put ("segmentID", segmentID);
    parameters.put ("oppkey", oppkey);
    parameters.put ("session", session);
    parameters.put ("groupID", groupID);
    parameters.put ("blockID", blockID);
    int insertedCnt = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, true).getUpdateCount ();

    _logger.debug ("Insert count after Insert query in _AA_SIM_GetCustomItemgroup_FN is " + insertedCnt);

    return resDBTable;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * tds.dll.api.IItemSelectionDLL#AA_GetSegment_SP(AIR.Common.DB.SQLConnection,
   * java.lang.String)
   */
  /**
   * Comments from DB: -- 9/2012: Designed to work almost seamlessly with
   * blueprint returned by AA_MakeCset1 -- Allows app-server-located computation
   * of cset1 using cached segment blueprint -- Only needed for adaptive test
   * segments. Fixed form segment selection is done by AA_GetNextItemCandidates
   * using _AA_NextFixedFormGroup
   * 
   * -- TODO: Make a SIMULATOR VERSION that discriminates by session key
   * 
   * @throws ReturnStatusException
   */
  public MultiDataResultSet AA_GetSegment_SP (SQLConnection connection, String segmentKey) throws ReturnStatusException {
    Date startTime = _dateUtil.getDateWRetStatus (connection);

    String client = _commonDll.TestKeyClient_FN (connection, segmentKey);
    String parentTest = null;

    List<SingleDataResultSet> resultsSets = new ArrayList<SingleDataResultSet> ();
    SingleDataResultSet res;
    DbResultRecord record;

    final String SQL_QUERY = "select VirtualTest "
        + " from ${ItemBankDB}.tblsetofadminsubjects where _Key = ${segmentKey} ";

    SqlParametersMaps parameters = new SqlParametersMaps ().put ("segmentKey", segmentKey);
    String query = fixDataBaseNames (SQL_QUERY);
    res = executeStatement (connection, query, parameters, false).getResultSets ().next ();
    record = res.getCount () > 0 ? res.getRecords ().next () : null;
    if (record != null) {
      parentTest = record.<String> get ("VirtualTest");
    }
    // -- First the segment-level specs
    final String SQL_QUERY0 = " select _Key as segmentkey, coalesce(refreshMinutes, 33) as refreshMinutes "
        + " , case when VirtualTest is null then ${segmentKey} else VirtualTest end as ParentTest "
        + " , case when TestPosition is null then 1 else Testposition end as segmentPosition "
        + " , (TestID) as SegmentID, blueprintWeight as bpWeight "
        + " , itemWeight, abilityOffset "
        + " , cset1size, cset1Order "
        + " , cset2random as randomizer, cset2InitialRandom as initialRandom "
        + " , case when MinItems is null then 0 else MinItems end as minOpItems "
        + " , case when MaxItems is null then 0 else MaxItems end as maxOpItems "
        //
		+ " , coalesce(startAbility, 0) as startAbility  "
		+ " , coalesce(startInfo, 0) as startInfo  "
		+ " , coalesce(slope, 1) as slope  "
		+ " , coalesce(intercept, 0) as intercept  "
        // -- Field test specs
        + " , FTStartPos, FTEndPos, FTMinItems, FTMaxItems "
        // -- Item selection algorithm
        + " , selectionAlgorithm "
        + " , 'bp1'as adaptiveVersion "
        + " from  ${ItemBankDB}.tblsetofadminsubjects   "
        + " where _Key =${segmentKey}";

    query = fixDataBaseNames (SQL_QUERY0);
    SingleDataResultSet res0 = executeStatement (connection, query, parameters, true).getResultSets ().next ();
    resultsSets.add (res0);

    // -- Next the content-level specs (strands, content-levels
    // and affinity groups together)

    final String SQL_QUERY1 = " (select _fk_Strand as contentLevel, minItems, maxItems, isStrictMax "
        + ", bpweight, adaptiveCut, StartAbility, StartInfo, Scalar "
        + ", case when adaptiveCut is not null then true else false end as isStrand "
        + " from  ${ItemBankDB}.tbladminstrand S   "
        + " where S._fk_AdminSubject = ${segmentKey} ) "
        + " union all "
        + " (select GroupID, minitems, maxitems, isStrictmax, weight, null, null, null, null, false "
        + " from ${ItemBankDB}.affinitygroup G   where G._fk_AdminSubject = ${segmentKey}) "
        + " order by isStrand desc, contentLevel ";

    query = fixDataBaseNames (SQL_QUERY1);
    SingleDataResultSet res1 = executeStatement (connection, query, parameters, true).getResultSets ().next ();
    resultsSets.add (res1);
    // -- Next the itemgroups
    final String SQL_QUERY2 = " select groupID as itemGroup"
        + ", numItemsRequired as itemsRequired, maxItems, bpweight "
        + " from ${ItemBankDB}.tbladminstimulus   "
        + " where _fk_AdminSubject =${segmentKey} ";

    query = fixDataBaseNames (SQL_QUERY2);
    SingleDataResultSet res2 = executeStatement (connection, query, parameters, true).getResultSets ().next ();
    resultsSets.add (res2);

    // -- Last the items
    final String SQL_QUERY3 = " select groupID as GID, _fk_Item as itemkey"
        + ", ItemPosition as position, isRequired, strandName as strand, isActive, isFieldTest "
        + ", upper(IRT_Model) as irtModel, IRT_b as irtB, IRT_a as irtA, IRT_c as irtC"
        + ", bVector, clString"
        + " from ${ItemBankDB}.tblsetofadminitems   "
        + " where _fk_AdminSubject = ${segmentKey}";

    query = fixDataBaseNames (SQL_QUERY3);
    SingleDataResultSet res3 = executeStatement (connection, query, parameters, true).getResultSets ().next ();
    resultsSets.add (res3);

    // -- Need all items from the virtual test not on this segment
    if (parentTest != null)
    {
      final String SQL_QUERY4 = "  select distinct groupID as GID, _fk_Item as itemkey, ItemPosition as position"
          + ", isRequired, strandName as strand, isActive, isFieldTest"
          + ", upper(IRT_Model) as irtModel, IRT_b as irtB, IRT_a as irtA, IRT_c as irtC, bVector "
          + " from  ${ItemBankDB}.tblsetofadminsubjects S  ,  ${ItemBankDB}.tblsetofadminitems I1  "
          + "  where S.VirtualTest = ${parentTest} and S._Key <> ${segmentKey} and _fk_AdminSubject = S._Key "
          + " and not exists (select * from  ${ItemBankDB}.tblsetofadminitems I2 "
          + " where I2._fk_AdminSubject = ${segmentKey} and I1._fk_Item = I2._fk_Item)";

      parameters.put ("parentTest", parentTest);
      query = fixDataBaseNames (SQL_QUERY4);
      SingleDataResultSet res4 = executeStatement (connection, query, parameters, true).getResultSets ().next ();
      resultsSets.add (res4);
    }

    _commonDll._LogDBLatency_SP (connection, "AA_GetSegment",
        startTime, null, true, null, null, null, client, segmentKey);

    return new MultiDataResultSet (resultsSets);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * tds.dll.api.IItemSelectionDLL#AA_SIM_GetSegment(AIR.Common.DB.SQLConnection
   * , java.util.UUID, java.lang.String)
   */
  /**
   * Comments from DB: -- 9/2012: Designed to work almost seamlessly with
   * blueprint returned by AA_MakeCset1 -- Allows app-server-located computation
   * of cset1 using cached segment blueprint -- Only needed for adaptive test
   * segments. Fixed form segment selection is done by AA_GetNextItemCandidates
   * using _AA_NextFixedFormGroup
   * 
   * -- TODO: Make a SIMULATOR VERSION that discriminates by session key
   * 
   * @throws ReturnStatusException
   */
  public MultiDataResultSet AA_SIM_GetSegment_SP (SQLConnection connection, UUID sessionKey, String segmentKey) throws ReturnStatusException {

    Date startTime = _dateUtil.getDateWRetStatus (connection);

    String client = _commonDll.TestKeyClient_FN (connection, segmentKey);
    String parentTest = null;

    List<SingleDataResultSet> resultsSets = new ArrayList<SingleDataResultSet> ();
    SingleDataResultSet res;
    DbResultRecord record;

    final String SQL_QUERY = "select VirtualTest "
        + " from ${ItemBankDB}.tblsetofadminsubjects where _Key = ${segmentKey} ";

    SqlParametersMaps parameters = new SqlParametersMaps ().put ("segmentKey", segmentKey);
    String query = fixDataBaseNames (SQL_QUERY);
    res = executeStatement (connection, query, parameters, false).getResultSets ().next ();
    record = res.getCount () > 0 ? res.getRecords ().next () : null;
    if (record != null) {
      parentTest = record.<String> get ("VirtualTest");
    }
    // -- First the segment-level specs

    final String SQL_QUERY0 = " select _efk_Segment as segmentkey, 1000000 as refreshMinutes "
        + " , _efk_AdminSubject as ParentTest "
        + " , S.segmentPosition,  S.SegmentID, S.blueprintWeight as bpWeight "
        + " , S.itemWeight, S.abilityOffset, S.cset1size, S.cset1Order "
        + " , S.cset2random as randomizer, S.cset2InitialRandom as initialRandom "
        + " , S.MinItems as minOpItems, S.MaxItems as maxOpItems "
        + " , S.startAbility, S.startInfo "
		+ " , coalesce(A.Slope, 1) as slope "
		+ " , coalesce(A.Intercept, 0) as intercept "
        // -- Field test specs
        + " , S.FTStartPos, S.FTEndPos, S.FTMinItems, S.FTMaxItems "
        + " , S.selectionAlgorithm "
        // -- Item selection algorithm
        // -- 3/2013: Special new adaptive algorithm internal selection
        // parameter just for Delaware Reading!!!! (f@#k)
        + " , 'bp1' as adaptiveVersion "
        + " from sim_segment S  , ${ItemBankDB}.tblsetofadminsubjects A   "
        + " where  S._fk_Session = ${sessionKey} and S._efk_Segment = ${segmentkey} and A._Key =${segmentkey}";

    parameters.put ("sessionKey", sessionKey);
    query = fixDataBaseNames (SQL_QUERY0);
    SingleDataResultSet res0 = executeStatement (connection, query, parameters, true).getResultSets ().next ();
    resultsSets.add (res0);

    // -- Next the content-level specs (strands, content-levels and affinity
    // groups together)
    final String SQL_QUERY1 = " select contentLevel, minItems, maxItems, isStrictMax "
        + ", bpweight, adaptiveCut, StartAbility, StartInfo, Scalar "
        + ", case when adaptiveCut is not null then true else false end as isStrand "
        + " from sim_segmentcontentlevel S   "
        + " where S._fk_Session = ${sessionKey} and S._efk_Segment = ${segmentKey} "
        + " order by isStrand desc, contentLevel ";

    SingleDataResultSet res1 = executeStatement (connection, SQL_QUERY1, parameters, true).getResultSets ().next ();
    resultsSets.add (res1);

    // -- Next the itemgroups (note that itemsRequired refers to how many a
    // student must answer which is irrelevant to the simulator)
    // -- and that bpweight is unused and therefore unavailable
    final String SQL_QUERY2 = " select groupID as itemGroup, -1 as itemsRequired, maxItems, 1 as bpweight "
        + " from sim_itemgroup   "
        + " where _fk_Session = ${sessionKey} and _efk_Segment = ${segmentKey} ";

    SingleDataResultSet res2 = executeStatement (connection, SQL_QUERY2, parameters, true).getResultSets ().next ();
    resultsSets.add (res2);

    // -- Last the items
    final String SQL_QUERY3 = " select I.groupID as GID, I._fk_Item as itemkey"
        + ", I.ItemPosition as position, S.isRequired, I.strandName as strand, I.isFieldTest "
        + ", S.isActive, upper(I.IRT_Model) as irtModel, I.IRT_b as irtB, I.IRT_a as irtA"
        + ", I.IRT_c as irtC, I.bVector, I.clString "
        + " from sim_segmentitem S  ,  ${ItemBankDB}.tblsetofadminitems I   "
        + " where S._fk_Session = ${sessionKey} and S._efk_Segment = ${segmentKey} "
        + " and I._fk_AdminSubject = ${segmentKey} and S.isFieldTest = 0 and S._efk_Item = I._fk_Item";

    query = fixDataBaseNames (SQL_QUERY3);
    SingleDataResultSet res3 = executeStatement (connection, query, parameters, true).getResultSets ().next ();
    resultsSets.add (res3);

    // -- Need all items from the virtual test not on this segment
    if (parentTest != null)
    {
      final String SQL_QUERY4 = "  select distinct groupID as GID, _fk_Item as itemkey, ItemPosition as position"
          + ", isRequired, strandName as strand, isActive, isFieldTest"
          + ", upper(IRT_Model) as irtModel, IRT_b as irtB, IRT_a as irtA, IRT_c as irtC, bVector "
          + " from  ${ItemBankDB}.tblsetofadminsubjects S  ,  ${ItemBankDB}.tblsetofadminitems I1  "
          + "  where S.VirtualTest = ${parentTest} and S._Key <> ${segmentKey} and _fk_AdminSubject = S._Key "
          + " and not exists (select * from  ${ItemBankDB}.tblsetofadminitems I2 "
          + " where I2._fk_AdminSubject = ${segmentKey} and I1._fk_Item = I2._fk_Item)";

      parameters.put ("parentTest", parentTest);
      query = fixDataBaseNames (SQL_QUERY4);
      SingleDataResultSet res4 = executeStatement (connection, query, parameters, true).getResultSets ().next ();
      resultsSets.add (res4);
    }

    _commonDll._LogDBLatency_SP (connection, "AA_SIM_GetSegment",
        startTime, null, true, null, null, sessionKey, client, segmentKey);

    return new MultiDataResultSet (resultsSets);
  }

//  /*
//   * (non-Javadoc)
//   * 
//   * @see tds.dll.api.IItemSelectionDLL#TestkeyClient_FN(java.lang.String)
//   */
//  public String TestkeyClient_FN (SQLConnection connection, String testkey) throws ReturnStatusException {
//
//    String clientName = null;
//    SingleDataResultSet result;
//    DbResultRecord record;
//
//    final String SQL_QUERY = "select C.Name as clientName "
//        + " from ${ItemBankDB}.tblsetofadminsubjects S  , ${ItemBankDB}.tblclient C  "
//        + ", ${ItemBankDB}.tbltestadmin A   "
//        + " where  A._fk_Client = C._Key and S._Key = ${testkey} and S._fk_TestAdmin = A._key ";
//    SqlParametersMaps parameters = new SqlParametersMaps ().put ("testkey", testkey);
//    String query = fixDataBaseNames (SQL_QUERY);
//    result = executeStatement (connection, query, parameters, true).getResultSets ().next ();
//    record = result.getCount () > 0 ? result.getRecords ().next () : null;
//    if (record != null) {
//      clientName = record.<String> get ("clientName");
//    }
//    return clientName;
//  }
//
  /*
   * (non-Javadoc)
   * 
   * @see tds.dll.api.IItemSelectionDLL#AA_GetDataHistory_SP(java.util.UUID,
   * java.lang.String)
   */
  // -- Collect all test-level historical data needed to perform adaptive item
  // selection
  // -- Has the side-effect of updating existing data where needed (i.e.
  // TestopportunitySegment.itempool and TestOpportunity.itemgroupString,
  // initialAbility)
  public MultiDataResultSet AA_GetDataHistory_SP (SQLConnection connection, UUID oppkey, String segmentKey) throws ReturnStatusException {
    Date startTime = _dateUtil.getDateWRetStatus (connection);

    String clientname = null;
    String itempool = null;
    String algorithm = null;
    Long testee = null;
    String subject = null;
    String testID = null;
    Float startability = null;

    List<SingleDataResultSet> resultsSets = new ArrayList<SingleDataResultSet> ();
    SingleDataResultSet res;
    DbResultRecord record;

    final String SQL_QUERY = "select clientname, _efk_Testee, subject "
        + ", _efk_TestID, initialAbility "
        + " from testopportunity where _Key = ${oppkey} ";

    SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey);

    res = executeStatement (connection, SQL_QUERY, parameters, false).getResultSets ().next ();
    record = res.getCount () > 0 ? res.getRecords ().next () : null;
    if (record != null) {
      clientname = subject = record.<String> get ("clientname");
      testee = record.<Long> get ("_efk_Testee");
      subject = record.<String> get ("subject");
      testID = record.<String> get ("_efk_TestID");
      startability = record.<Float> get ("initialAbility");
    }

    if (startability == null)
    {
      startability = FN_GetInitialAbility_FN (connection, oppkey);

      final String SQL_QUERY1 = " update testopportunity set initialAbility = ${startAbility} "
          + " where _Key = ${oppkey} ";
      parameters.put ("startAbility", startability);
      executeStatement (connection, SQL_QUERY1, parameters, false);
    }

    final String SQL_QUERY2 = " select itempool, algorithm "
        + " from testopportunitysegment   "
        + " where _fk_TestOpportunity = ${oppkey} and _efk_Segment = ${segmentKey} ";

    parameters.put ("segmentKey", segmentKey);
    res = executeStatement (connection, SQL_QUERY2, parameters, true).getResultSets ().next ();
    record = res.getCount () > 0 ? res.getRecords ().next () : null;
    if (record != null) {
      itempool = record.<String> get ("itempool");
      algorithm = record.<String> get ("algorithm");
    }
    if (itempool == null && algorithm != null && DbComparator.containsIgnoreCase (algorithm, ADAPTIVE))
    {
      itempool = _AA_ItemPoolString_FN (connection, oppkey, segmentKey);
      //
      final String SQL_QUERY3 = " update testopportunitysegment set itempool = ${itempool} "
          + " where _Key = ${oppkey} ";
      parameters.put ("itempool", itempool);
      executeStatement (connection, SQL_QUERY3, parameters, false);
    }
    // -- First the segment-level specs
    final String SQL_QUERY4 = " select * from testopportunity "
        + " where _Key <> ${oppkey} and clientname = ${clientname} "
        + " and _efk_Testee = ${testee} and _efk_TestID = ${testID} "
        + " and itemgroupString is null ";

    parameters.put ("testee", testee).put ("testID", testID).put ("clientname", clientname);
    res = executeStatement (connection, SQL_QUERY4, parameters, false).getResultSets ().next ();
    if (res.getCount () > 0)
    {
      String itemgroupString = _commonDll.MakeItemGroupString_FN (connection, oppkey);
      final String SQL_QUERY5 = " update testopportunity set itemgroupString = ${itemgroupString} "
          + " where _Key <> ${oppkey} and clientname = ${clientname} "
          + " and _efk_Testee = ${testee} and _efk_TestID = ${testID} "
          + " and itemgroupString is null ";
      parameters.put ("itemgroupString", itemgroupString);
      executeStatement (connection, SQL_QUERY5, parameters, false);
    }

    List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
    CaseInsensitiveMap<Object> rcrd = new CaseInsensitiveMap<Object> ();
    rcrd.put ("initialAbility", startability);
    rcrd.put ("itempool", itempool);
    resultList.add (rcrd);
    SingleDataResultSet rs = new SingleDataResultSet ();
    rs.addColumn ("initialAbility", SQL_TYPE_To_JAVA_TYPE.FLOAT);
    rs.addColumn ("itempool", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    rs.addRecords (resultList);
    resultsSets.add (rs);

    final String SQL_QUERY6 = " (select _Key as oppkey, dateStarted, itemgroupString "
        + " from testopportunity   "
        + " where _Key <> ${oppkey} and clientname =  ${clientname} "
        + " and _efk_Testee = ${testee} and _efk_TestID = ${testID}) "
        + " union all "
        + " (select _fk_TestOpportunity as oppkey, dateChanged, itemgroupString "
        + " from testeehistory   "
        + " where clientname =  ${clientname} "
        + " and _efk_Testee = ${testee} and subject = ${subject}  "
        + " and length(coalesce(itemgroupString, '')) > 0 ) "
        + " order by dateStarted ";

    parameters.put ("subject", subject);
    SingleDataResultSet res1 = executeStatement (connection, SQL_QUERY6, parameters, true).getResultSets ().next ();
    resultsSets.add (res1);

    // -- Adaptive algorithm must not select item groups that have been selected
    // for field test on this test
    final String SQL_QUERY7 = " select groupID as FTGroupID "
        + " from ft_opportunityitem where _fk_TestOpportunity = ${oppkey} and coalesce(deleted, 0) = 0 ";

    SingleDataResultSet res2 = executeStatement (connection, SQL_QUERY7, parameters, false).getResultSets ().next ();
    resultsSets.add (res2);

    // -- Collect the entire set of item responses on all segments
    final String SQL_QUERY8 = " select R.segment as segmentPosition, R.segmentID, R.page"
        + ", R.position, R.groupID, R._efk_Itemkey as itemID, R.score, R.isFieldTest "
        + " from testeeresponse R   where R._fk_TestOpportunity = ${oppkey} "
        + " and _efk_ItemKey is not null ";

    SingleDataResultSet res3 = executeStatement (connection, SQL_QUERY8, parameters, true).getResultSets ().next ();
    resultsSets.add (res3);

    _commonDll._LogDBLatency_SP (connection, "AA_GetDataHistory_SP", startTime, null, true, null, oppkey);

    return new MultiDataResultSet (resultsSets);
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.dll.api.IItemSelectionDLL#FN_GetInitialAbility_FN(AIR.Common.DB.
   * SQLConnection, java.util.UUID)
   * 
   * Comments from DB: -- T_GetInitialAbility is used by the testee app to
   * attempt to get the initial ability estimates for adaptive test -- this gets
   * the most recently scored opportunity in this test, or another test of the
   * same subject -- ASSUME: Previous year's scores are in an opportunity
   * numbered 0 and are in a testID different from input parameter. -- 3/2011:
   * Updated to utilize TestOpportunity._Key and to get clientname from
   * TestOpportunity -- 9/2012: Convert the procedure to a function
   */
  // Old variant
  public Float FN_GetInitialAbility_FN_2 (SQLConnection connection, UUID oppkey) throws ReturnStatusException {

	    final String INITIALABILITY = "INITIALABILITY";

	    Float ability = null;
	    String abilityString = null;
	    String testID = null;
	    Date maxdate = null;
	    String clientname = null;
	    String test = null;
	    Long testee = null;
	    String subject = null;
	    Boolean bySubject = null;
	    Float slope = null;
	    Float intercept = null;

	    SingleDataResultSet res;
	    DbResultRecord record;
	    // attributeValue is varchar in this table
	    final String SQL_QUERY = "select attributeValue as ability "
	        + " from testeeattribute where _fk_TestOpportunity = ${oppkey} and TDS_ID = ${INITIALABILITY} ";

	    SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey).put ("INITIALABILITY", INITIALABILITY);

	    res = executeStatement (connection, SQL_QUERY, parameters, false).getResultSets ().next ();
	    record = res.getCount () > 0 ? res.getRecords ().next () : null;
	    if (record != null) {
	      abilityString = record.<String> get ("ability");
	      try {
	        ability = Float.parseFloat (abilityString);
	      } catch (Exception e)
	      {
	        _logger.error ("Cannot parse " + abilityString + " from String to Float. Set ability = 0.0");
	       
	      }
	    }
	    if (ability != null)
	    {
	      return ability;
	    }

	    final String SQL_QUERY1 = "select  _efk_Testee, subject, clientname "
	        + ", _efk_AdminSubject,  _efk_TestID "
	        + " from testopportunity where _Key = ${oppkey} ";

	    res = executeStatement (connection, SQL_QUERY1, parameters, false).getResultSets ().next ();
	    record = res.getCount () > 0 ? res.getRecords ().next () : null;
	    if (record != null) {
	      clientname = record.<String> get ("clientname");
	      testee = record.<Long> get ("_efk_Testee");
	      subject = record.<String> get ("subject");
	      testID = record.<String> get ("_efk_TestID");
	      test = record.<String> get ("_efk_AdminSubject");
	    }

	    final String SQL_QUERY2 = "select initialAbilityBySubject, abilitySlope, abilityIntercept "
	        + " from ${ConfigDB}.client_testproperties where clientname = ${clientname} and TestID = ${testID}";

	    parameters.put ("clientname", clientname).put ("testID", testID);
	    String query = fixDataBaseNames (SQL_QUERY2);
	    res = executeStatement (connection, query, parameters, false).getResultSets ().next ();
	    record = res.getCount () > 0 ? res.getRecords ().next () : null;
	    if (record != null) {
	      bySubject = record.<Boolean> get ("initialAbilityBySubject");
	      slope = record.<Float> get ("abilitySlope");
	      intercept = record.<Float> get ("abilityIntercept");
	    }
	    if (bySubject == null)
	    {
	      bySubject = false;
	    }
	    // -- this table holds all possible alternatives. No field is null.
	    // declare @tbl table (oppkey uniqueidentifier, test varchar(200),
	    // opportunity int, scored datetime, ability float);
	    // (AK): I will not create table. For performance I will work with
	    // SingleDataResultSet better

	    final String SQL_QUERY3 = "select OTHEROPP._Key as oppkey, OTHEROPP._efk_TestID as test"
	        + ",  OTHEROPP.Opportunity as opportunity, OTHEROPP.dateScored as scored, SCORE.value as ability"
	        + " from testopportunity OTHEROPP, testopportunityscores SCORE "
	        + " where clientname = ${clientname} "
	        // -- find all other opportunity candidates, same testee, same
	        // subject, scored and not deleted
	        + " and OTHEROPP._efk_Testee = ${testee} "
	        + " and OTHEROPP.subject = ${subject} and OTHEROPP.dateDeleted is null "
	        + " and OTHEROPP.dateScored is not null and OTHEROPP._Key <> ${oppkey} "
	        // -- that have a usable ability score
	        + " and OTHEROPP._Key = SCORE._fk_TestOpportunity "
	        + " and SCORE.UseForAbility = 1 and SCORE.value is not null";

	    SingleDataResultSet tbl = null;
	    parameters.put ("testee", testee).put ("subject", subject);
	    tbl = executeStatement (connection, SQL_QUERY3, parameters, false).getResultSets ().next ();

	    // -- first, try to find a previous opportunity on this exact same test.
	    // -- look for the latest scored
	    Iterator<DbResultRecord> recItr = tbl.getRecords ();
	    // for performance
	    Date maxDateEqTest = new Date (0);
	    Date maxDateNotEqTest = new Date (0);
	    while (recItr.hasNext ())
	    {
	      record = recItr.next ();
	      if (record != null)
	      {
	        if (test.equals (record.<String> get ("test")))
	        {
	          maxDateEqTest = new Date (Math.max (maxDateEqTest.getTime (), record.<Date> get ("scored").getTime ()));
	        } else
	        {
	          maxDateNotEqTest = new Date (Math.max (maxDateNotEqTest.getTime (), record.<Date> get ("scored").getTime ()));
	        }

	      }
	    }
	    // -- found one of same test. Return the ability value, print 'Same test';
	    maxdate = maxDateEqTest;
	    if (maxdate.getTime () > 0)
	    {
	      recItr = tbl.getRecords ();
	      while (recItr.hasNext ())
	      {
	        record = recItr.next ();
	        if (record != null && test.equals (record.<String> get ("test")) && record.<Date> get ("scored").getTime () == maxdate.getTime ())
	        {
	          ability = record.<Float> get ("ability");
	          if (ability != null)
	            return ability; // break
	        }
	      }
	    } else if (bySubject)
	    {
	      maxdate = maxDateNotEqTest;
	      if (maxdate.getTime () > 0)
	      {
	        recItr = tbl.getRecords ();
	        while (recItr.hasNext ())
	        {
	          record = recItr.next ();
	          if (record != null && !test.equals (record.<String> get ("test")) && record.<Date> get ("scored") == maxdate)
	          {// print 'Not the same test'
	            ability = record.<Float> get ("ability");
	            if (ability != null)
	              return ability; // break;
	          }
	        }
	      }
	    }
	    // -- failing that, try to get an initial ability from the previous year
	    if (bySubject)
	    {
	      // -- on their first test this year, they are likely at their lowest
	      // performance level
	      final String SQL_QUERY5 = "select max(initialAbility) as ability "
	          + " from testeehistory "
	          + " where clientname = ${clientname} and _efk_Testee = ${testee} "
	          + " and Subject = ${subject} and initialAbility is not null ";

	      res = executeStatement (connection, SQL_QUERY5, parameters, false).getResultSets ().next ();
	      record = res.getCount () > 0 ? res.getRecords ().next () : null;
	      if (record != null) {
	        ability = record.<Float> get ("ability");
	      }
	      if (ability != null)
	      { // -- print 'History';
	        ability = ability * slope + intercept;
	        return ability;
	      }
	    }

	    // -- else get the default for this test from the itembank
	    // -- print 'Itembank';
	    return _studentDll.GetInitialAbility_FN (connection, test);
	  }
  
  // New variant with temporary table
  public Float FN_GetInitialAbility_FN (SQLConnection connection, UUID oppkey) 
		  throws ReturnStatusException {

	    final String INITIALABILITY = "INITIALABILITY";

	    Float ability = null;
	    String abilityString = null;
	    String testID = null;
	    Date maxdate = null;
	    String clientname = null;
	    String test = null;
	    Long testee = null;
	    String subject = null;
	    Boolean bySubject = null;
	    Float slope = null;
	    Float intercept = null;

	    SingleDataResultSet res;
	    DbResultRecord record;
	    // attributeValue is varchar in this table
	    final String SQL_QUERY = "select attributeValue as ability "
	        + " from testeeattribute where _fk_TestOpportunity = ${oppkey} and TDS_ID = ${INITIALABILITY} ";

	    SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey).put ("INITIALABILITY", INITIALABILITY);

	    res = executeStatement (connection, SQL_QUERY, parameters, false).getResultSets ().next ();
	    record = res.getCount () > 0 ? res.getRecords ().next () : null;
	    if (record != null) {
	      abilityString = record.<String> get ("ability");
	      try {
	        ability = Float.parseFloat (abilityString);
	      } catch (Exception e)
	      {
	    	  // TODO: check is it necessary?
	        _logger.error ("Cannot parse " + abilityString + " from String to Float.");
	      }
	    }
	    if (ability != null)
	    {
	      return ability;
	    }

	    final String SQL_QUERY1 = "select  _efk_Testee, subject, clientname "
	        + ", _efk_AdminSubject,  _efk_TestID "
	        + " from testopportunity where _Key = ${oppkey} ";

	    res = executeStatement (connection, SQL_QUERY1, parameters, false).getResultSets ().next ();
	    record = res.getCount () > 0 ? res.getRecords ().next () : null;
	    if (record != null) {
	      clientname = record.<String> get ("clientname");
	      testee = record.<Long> get ("_efk_Testee");
	      subject = record.<String> get ("subject");
	      testID = record.<String> get ("_efk_TestID");
	      test = record.<String> get ("_efk_AdminSubject");
	    }

	    final String SQL_QUERY2 = "select initialAbilityBySubject, abilitySlope, abilityIntercept "
	        + " from ${ConfigDB}.client_testproperties where clientname = ${clientname} and TestID = ${testID}";

	    parameters.put ("clientname", clientname).put ("testID", testID);
	    String query = fixDataBaseNames (SQL_QUERY2);
	    res = executeStatement (connection, query, parameters, false).getResultSets ().next ();
	    record = res.getCount () > 0 ? res.getRecords ().next () : null;
	    if (record != null) {
	      bySubject = record.<Boolean> get ("initialAbilityBySubject");
	      slope = record.<Float> get ("abilitySlope");
	      intercept = record.<Float> get ("abilityIntercept");
	    }
	    if (bySubject == null)
	    {
	      bySubject = false;
	    }
	    // -- this table holds all possible alternatives. No field is null.
	    // declare @tbl table (oppkey uniqueidentifier, test varchar(200),
	    // opportunity int, scored datetime, ability float);
	    DataBaseTable pool = getDataBaseTable ("tmpTable")
	            .addColumn ("oppkey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER)
	            .addColumn ("test", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200)
	            .addColumn ("opportunity", SQL_TYPE_To_JAVA_TYPE.INT)
	            .addColumn ("scored", SQL_TYPE_To_JAVA_TYPE.DATETIME)
	            .addColumn ("ability", SQL_TYPE_To_JAVA_TYPE.FLOAT);

	        connection.createTemporaryTable (pool);

	    final String SQL_QUERY3 = "insert into ${tblName} (oppkey, test, opportunity, scored, ability)"
	    	+ " select OTHEROPP._Key as oppkey, OTHEROPP._efk_TestID as test"
	        + ",  OTHEROPP.Opportunity as opportunity, OTHEROPP.dateScored as scored, SCORE.value as ability"
	        + " from testopportunity OTHEROPP, testopportunityscores SCORE "
	        + " where clientname = ${clientname} "
	        // -- find all other opportunity candidates, same testee, same
	        // subject, scored and not deleted
	        + " and OTHEROPP._efk_Testee = ${testee} "
	        + " and OTHEROPP.subject = ${subject} and OTHEROPP.dateDeleted is null "
	        + " and OTHEROPP.dateScored is not null and OTHEROPP._Key <> ${oppkey} "
	        // -- that have a usable ability score
	        + " and OTHEROPP._Key = SCORE._fk_TestOpportunity "
	        + " and SCORE.UseForAbility = 1 and SCORE.value is not null";

	    Map<String, String> unquotedparms = new HashMap<String, String> ();
	    unquotedparms.put ("tblName", pool.getTableName ());
	    parameters.put ("testee", testee).put ("subject", subject);	    
	    int insertedCnt = executeStatement (connection, fixDataBaseNames (SQL_QUERY3, unquotedparms), parameters, false).getUpdateCount ();

	    _logger.info ("Inserted rows after Insert query in temporary table in AF_GetItempool_FN are " + insertedCnt);
	    // -- first, try to find a previous opportunity on this exact same test.
	    // -- look for the latest scored
	    query = "select max(scored) as maxdate from ${tblName} where test = ${test}";
	    
	    parameters.put ("test", test);
	    res = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, false).getResultSets ().next ();
	    record = res.getCount () > 0 ? res.getRecords ().next () : null;
		if (record != null) {
			maxdate = record.<Date> get("maxdate");
		}
	    if(maxdate != null)
	    {
	     // found one of same test. Return the ability value, print 'Same test';
	    	query = "select ability from ${tblName} "
	    			+ " where test = ${test} and scored = ${maxdate} limit 1";

		    parameters.put ("maxdate", maxdate);
		    res = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, false).getResultSets ().next ();
		    record = res.getCount () > 0 ? res.getRecords ().next () : null;
			if (record != null) {
				ability = record.<Float> get("ability");
			}
			return ability;
	    }
	    else if(bySubject) //-- failing that, try for same subject/different test
	    {
		    query = "select max(scored) as maxdate from ${tblName} where test <> ${test}";
		    
		    res = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, false).getResultSets ().next ();
		    record = res.getCount () > 0 ? res.getRecords ().next () : null;
			if (record != null) {
				maxdate = record.<Date> get("maxdate");
			}
		    if(maxdate != null)
		    {
		     // print 'Different test';
		    	query = "select ability from ${tblName} "
		    			+ " where test <> ${test} and scored = ${maxdate} limit 1";

			    parameters.put ("maxdate", maxdate);
			    res = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, false).getResultSets ().next ();
			    record = res.getCount () > 0 ? res.getRecords ().next () : null;
				if (record != null) {
					ability = record.<Float> get("ability");
				}
				return ability;
		    }
	    	
	    }
	    // -- failing that, try to get an initial ability from the previous year
	    if (bySubject)
	    {
	      // -- on their first test this year, they are likely at their lowest
	      // performance level
	      final String SQL_QUERY5 = "select max(initialAbility) as ability "
	          + " from testeehistory "
	          + " where clientname = ${clientname} and _efk_Testee = ${testee} "
	          + " and Subject = ${subject} and initialAbility is not null ";

	      res = executeStatement (connection, SQL_QUERY5, parameters, false).getResultSets ().next ();
	      record = res.getCount () > 0 ? res.getRecords ().next () : null;
	      if (record != null) {
	        ability = record.<Float> get ("ability");
	      }
	      if (ability != null)
	      { // -- print 'History';
	        ability = ability * slope + intercept;
	        return ability;
	      }
	    }

	    // -- else get the default for this test from the itembank
	    // -- print 'Itembank';
	    return _studentDll.GetInitialAbility_FN (connection, test);
	  }

  // this function from ITEMBANK table
  // there is this function in StudentDLL
//  public Float GetInitialAbility_FN (SQLConnection connection, String testkey) throws ReturnStatusException {
//
//    SingleDataResultSet res;
//    DbResultRecord record;
//    Float ability = null;
//
//    final String SQL_QUERY = "select StartAbility as ability "
//        + " from ${ItemBankDB}.tblsetofadminsubjects where _key = ${testkey} ";
//
//    SqlParametersMaps parameters = new SqlParametersMaps ().put ("testkey", testkey);
//    String query = fixDataBaseNames (SQL_QUERY);
//    res = executeStatement (connection, query, parameters, false).getResultSets ().next ();
//    record = res.getCount () > 0 ? res.getRecords ().next () : null;
//    if (record != null) {
//      ability = record.<Float> get ("ability");
//    }
//
//    return ability;
//  }

  /**
   * -- At the start of a new test opportunity, establish the custom itempool to
   * use for a segment's non-fieldtest items -- It is the responsibility of the
   * item selection processes to determine specific applicability of each item
   * (isActive, previously administered, etc.) -- Since an itempool cannot be
   * altered (by subtraction) this procedure only needs to be run at the start
   * of a new test opp -- However, it can be run to add to an itempool while the
   * test is in progress, though this is almost never required. -- The custom
   * itempool is not required for fixed form tests.
   * 
   * @param connection
   * @param oppkey
   * @param segmentKey
   * @return
   * @throws ReturnStatusException
   */
  public String _AA_ItemPoolString_FN (SQLConnection connection, UUID oppkey, String segmentKey) throws ReturnStatusException {

    String itemstring = null;
    String testID = null;
    String clientname = null;
    String itemkey = null;

    SingleDataResultSet res;
    DbResultRecord record;

    final String SQL_QUERY = " select _efk_TestID as testID, clientname, _fk_Session as session"
        + "from testopportunity where _key = @oppkey ";

    SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey);

    res = executeStatement (connection, SQL_QUERY, parameters, false).getResultSets ().next ();
    record = res.getCount () > 0 ? res.getRecords ().next () : null;
    if (record != null) {
      testID = record.<String> get ("testID");
      clientname = record.<String> get ("clientname");
    }

    final String SQL_QUERY1 = " select AccCode as language from testeeaccommodations "
        + " where _fk_TestOPportunity = ${oppkey} and AccType = ${language} ";

    parameters.put ("language", LANGUAGE);

    res = executeStatement (connection, SQL_QUERY1, parameters, false).getResultSets ().next ();
    record = res.getCount () > 0 ? res.getRecords ().next () : null;
    if (record != null) {
    }

    final String SQL_QUERY2 = "  select distinct I._fk_ITem as itemkey"
        + " from ${ItemBankDB}.tblsetofadminitems I  "
        + ", ${ConfigDB}.client_test_itemconstraint C1  ,"
        + " testeeaccommodations A1  , ${ItemBankDB}.tblitemprops P1  "
        + " where I._fk_AdminSUbject = ${segmentKey) "
        + " and C1.Clientname = ${clientname} and C1.testID =${testID} and C1.item_in = 1 "
        + " and A1._fk_TestOpportunity = ${oppkey} and A1.AccType = C1.ToolType "
        + " and A1.AccCode = C1.ToolValue "
        + " and P1._fk_AdminSubject =${segmentKey) and P1._fk_Item  = I._fk_Item "
        + " and P1.Propname = C1.propname and P1.Propvalue = C1.Propvalue and P1.isActive = 1 "
        + " and not exist "
        + "  (select * from ${ConfigDB}.client_test_itemconstraint C2  "
        + ", testeeaccommodations A2  ,  ${ItemBankDB}.tblitemprops P2   "
        + " where A2._fk_TestOpportunity = ${oppkey} "
        + " and C2.Clientname = ${clientname} and C2.testID = ${testID} and C2.item_in = 0 "
        + " and A2.AccType = C2.ToolType and A2.AccCode = C2.ToolValue "
        + " and P2._fk_AdminSubject = ${segmentKey) and P2._fk_Item  = I._fk_Item "
        + " and P2.Propname = C2.propname and P2.Propvalue = C2.Propvalue and P2.isActive = 1 "
        + " )"
        // -- there should be the same number of records per item as there are
        // item_in constraints, check in the 'having' clause
        + " group by I._fk_ITem "
        + " (select count(*) from ${ConfigDB}.client_test_itemconstraint C1  "
        + ", testeeaccommodations A1  "
        + " where C1.Clientname =  ${clientname} and C1.testID = ${testID} and C1.item_in = 1 "
        + " and A1._fk_TestOpportunity = ${oppkey} and A1.AccType = C1.ToolType "
        + " and A1.AccCode = C1.ToolValue "
        + "  )";

    parameters.put ("segmentKey", segmentKey).put ("clientname", clientname).put ("testID", testID);
    String query = fixDataBaseNames (SQL_QUERY2);
    SingleDataResultSet pool = executeStatement (connection, query, parameters, true).getResultSets ().next ();

    Iterator<DbResultRecord> recItr = pool.getRecords ();
    // in pool all itemkeys are distinct
    // TODO: (AK) test it
    while (recItr.hasNext ())
    {
      record = recItr.next ();
      if (record != null)
      {
        itemkey = record.<String> get ("itemkey");
        if (itemkey != null)
        {
          if (itemstring == null)
          {
            itemstring = itemkey;
          } else {
            itemstring = itemstring + "," + itemkey;
          }
        }
      }
    }
    return itemstring;
  }
  
  //======================Adaptive2 Algorithm==================================
  
  public boolean AA_SetSegmentSatisfied_SP (SQLConnection connection, UUID oppkey, 
		  Integer segmentPosition, String termReason) throws ReturnStatusException {

		Date startTime = _dateUtil.getDateWRetStatus(connection);
		String SQL_QUERY = "update TestOpportunitySegment set IsSatisfied = 1, SatisfiedReason = ${termReason} "
				+ " where _fk_TestOpportunity = ${oppkey} and SegmentPosition = ${segmentPosition}";

		SqlParametersMaps parameters = new SqlParametersMaps()
				.put("oppkey", oppkey).put("termReason", termReason)
				.put("segmentPosition", segmentPosition);

		int updatedCnt = executeStatement(connection, SQL_QUERY, parameters, true).getUpdateCount();

		_logger.info("updated rows after update query in TestOpportunitySegment table in AA_SetSegmentSatisfied_SP are "
				+ updatedCnt);

		_commonDll._LogDBLatency_SP(connection, "AA_SetSegmentSatisfied_SP",
				startTime, null, true, null, oppkey);
		return updatedCnt > 0;
  }
  
   public MultiDataResultSet AA_GetSegment2_SP (SQLConnection connection, String segmentKey) throws ReturnStatusException {
	    Date startTime = _dateUtil.getDateWRetStatus (connection);

	    String selectionAlgorithm = null;
	    String client = _commonDll.TestKeyClient_FN (connection, segmentKey);
	    String parentTest = null;

	    List<SingleDataResultSet> resultsSets = new ArrayList<SingleDataResultSet> ();
	    SingleDataResultSet res;
	    DbResultRecord record;

	    final String SQL_QUERY = "select VirtualTest, selectionAlgorithm "
	        + " from ${ItemBankDB}.tblsetofadminsubjects where _Key = ${segmentKey} ";

	    SqlParametersMaps parameters = new SqlParametersMaps ().put ("segmentKey", segmentKey);
	    String query = fixDataBaseNames (SQL_QUERY);
	    res = executeStatement (connection, query, parameters, false).getResultSets ().next ();
	    record = res.getCount () > 0 ? res.getRecords ().next () : null;
	    if (record != null) {
	      parentTest = record.<String> get ("VirtualTest");
	      selectionAlgorithm = record.<String> get ("selectionAlgorithm");
	    }
	    // -- First the segment-level specs
	    final String SQL_QUERY0 = " select _Key as segmentkey, coalesce(refreshMinutes, 33) as refreshMinutes "
	        + " , case when VirtualTest is null then ${segmentKey} else VirtualTest end as ParentTest "
	        + " , case when TestPosition is null then 1 else Testposition end as segmentPosition "
	        + " , (TestID) as SegmentID, blueprintWeight as bpWeight "
	        + " , itemWeight, abilityOffset "
	        + " , cset1size, cset1Order "
	        + " , cset2random as randomizer, cset2InitialRandom as initialRandom "
	        + " , case when MinItems is null then 0 else MinItems end as minOpItems "
	        + " , case when MaxItems is null then 0 else MaxItems end as maxOpItems "
	        //
			+ " , coalesce(startAbility, 0) as startAbility  "
			+ " , coalesce(startInfo, 0) as startInfo  "
			+ " , coalesce(slope, 1) as slope  "
			+ " , coalesce(intercept, 0) as intercept  "
	        // -- Field test specs
	        + " , FTStartPos, FTEndPos, FTMinItems, FTMaxItems "
	        // -- Item selection algorithm
	        + " , selectionAlgorithm "
	        + " , bpmetricfunction as adaptiveVersion "
	        //-- 11/6/2013: new fields for new AA
	        + " , abilityWeight  "
	        + " , rcAbilityWeight  "
	        + " , precisionTarget  "
	        + " , precisionTargetMetWeight  "
	        + " , precisionTargetNotMetWeight  "
	        + " , adaptiveCut  "
	        + " , tooCloseSEs  "
	        + " , terminationOverallInfo  "
	        + " , terminationRCInfo  "
	        + " , terminationMinCount  "
	        + " , terminationTooClose  "
	        + " , terminationFlagsAnd  "
	        + " from  ${ItemBankDB}.tblsetofadminsubjects   "
	        + " where _Key =${segmentKey}";

	    query = fixDataBaseNames (SQL_QUERY0);
	    SingleDataResultSet res0 = executeStatement (connection, query, parameters, true).getResultSets ().next ();
	    resultsSets.add (res0);

	    // -- Next the content-level specs (strands, content-levels
	    // and affinity groups together)

	    final String SQL_QUERY1 = " (select _fk_Strand as contentLevel, minItems, maxItems, isStrictMax "
	        + ", bpweight, adaptiveCut, StartAbility, StartInfo, Scalar "
	        + ", case when StartAbility is not null then 1 else 0 end as isReportingCategory, abilityWeight "
	        + ", precisionTarget, precisionTargetMetWeight, precisionTargetNotMetWeight"
	        + ", case when SS._fk_Parent is null then 0 else 1 end as elementType "
	        + " from  ${ItemBankDB}.tbladminstrand S   "
	        + " inner join ${ItemBankDB}.tblstrand SS " 
	        + " on SS._Key = S._fk_Strand "
	        + " where S._fk_AdminSubject = ${segmentKey} ) "
	        + " union all "
	        + " (select GroupID, minitems, maxitems, isStrictmax, weight, null, StartAbility, StartInfo, null, "
	        + "  case when StartAbility is not null then 1 else 0 end as isReportingCategory"
	        + ", abilityWeight"
	        + ", precisionTarget, precisionTargetMetWeight, precisionTargetNotMetWeight"
	        + ", 2 as elementType"
	        + " from ${ItemBankDB}.affinitygroup G   where G._fk_AdminSubject = ${segmentKey}) "
	        + " order by isReportingCategory desc, contentLevel ";

	    query = fixDataBaseNames (SQL_QUERY1);
	    SingleDataResultSet res1 = executeStatement (connection, query, parameters, true).getResultSets ().next ();
	    resultsSets.add (res1);
	    // -- Next the itemgroups
	    final String SQL_QUERY2 = " select groupID as itemGroup"
	        + ", numItemsRequired as itemsRequired, maxItems, bpweight "
	        + " from ${ItemBankDB}.tbladminstimulus   "
	        + " where _fk_AdminSubject =${segmentKey} ";

	    query = fixDataBaseNames (SQL_QUERY2);
	    SingleDataResultSet res2 = executeStatement (connection, query, parameters, true).getResultSets ().next ();
	    resultsSets.add (res2);

	    // -- Last the items
	    final String SQL_QUERY3 = " select groupID as GID, _fk_Item as itemkey"
	        + ", ItemPosition as position, isRequired, strandName as strand, isActive, isFieldTest "
	       // + ", upper(IRT_Model) as irtModel, IRT_b as irtB, IRT_a as irtA, IRT_c as irtC, bVector"
	       // Related to main document I need to comment this field and added to populate 
	       // + ", upper(IRT_Model) as irtModel, IRT2_b as irtB, IRT2_a as irtA, IRT2_c as irtC, bVector"
	       //!!! These fields (IRT2_b, ...) had VARCHAR (String) type !!!
	        + ", upper(IRT_Model) as irtModel, IRT_b as irtB, IRT_a as irtA, IRT_c as irtC, bVector"
	        + ", clString"
	        + " from ${ItemBankDB}.tblsetofadminitems   "
	        + " where _fk_AdminSubject = ${segmentKey}";

	    query = fixDataBaseNames (SQL_QUERY3);
	    SingleDataResultSet res3 = executeStatement (connection, query, parameters, true).getResultSets ().next ();
	    resultsSets.add (res3);

	    // -- Need all items from the virtual test not on this segment
		if (parentTest != null) {
			final String SQL_QUERY4 = "  select distinct coalesce(S.TestPosition, 1) as segmentPosition"
					+ ", groupID as GID, _fk_Item as itemkey, ItemPosition as  position , isRequired"
					+ ", strandName as strand, isActive, isFieldTest"
					+ ", case ${selectionAlgorithm} when 'adaptive2' then clString else null end as clString "
					+ " from  ${ItemBankDB}.tblsetofadminsubjects S  ,  ${ItemBankDB}.tblsetofadminitems I1  "
					+ " where S.VirtualTest = ${parentTest} and S._Key <> ${segmentKey} and _fk_AdminSubject = S._Key "
					+ " and not exists (select * from  ${ItemBankDB}.tblsetofadminitems I2 "
					+ " where I2._fk_AdminSubject = ${segmentKey} and I1._fk_Item = I2._fk_Item)";

			parameters.put("parentTest", parentTest).put("selectionAlgorithm", selectionAlgorithm);
			query = fixDataBaseNames(SQL_QUERY4);
			SingleDataResultSet res4 = executeStatement(connection, query,
					parameters, true).getResultSets().next();
			resultsSets.add(res4);
		}
		// -- Need all dims and corresponding IRT params for the new AA - all segments, including the current

		final String SQL_QUERY5 = "  select coalesce(S.TestPosition, 1) as segmentPosition, AI._fk_Item as itemkey,"
				+ " DIM.Dimension, upper(DIM.ModelName) as irtModel, DIM.parmnum, DIM.parmname, DIM.parmvalue "
				+ " from  ${ItemBankDB}.tblsetofadminitems AI  "
				+ " inner join  ${ItemBankDB}.tblsetofadminsubjects S  "
				+ " on S._Key = AI._fk_AdminSubject "
				+ " inner join  "
				+ "( select D._fk_AdminSubject, D._fk_Item, D.Dimension,m.ModelName, p.parmnum, p.parmname, ip.parmvalue"
				+ " from ${ItemBankDB}.itemscoredimension as D  "
				+ " inner join ${ItemBankDB}.measurementmodel m "
				+ " on m.ModelNumber = D._fk_MeasurementModel "
				+ " inner join ${ItemBankDB}.measurementparameter p"
				+ " on p._fk_measurementModel = m.ModelNumber"
				+ " inner join ${ItemBankDB}.itemmeasurementparameter ip"
				+ " on ip._fk_ItemScoreDimension = D._Key"
				+ " and ip._fk_MeasurementParameter = p.parmnum) as DIM "
				+ " on DIM._fk_AdminSubject = AI._fk_AdminSubject"
				+ " and DIM._fk_Item = AI._fk_Item"
				+ " where S._Key = ${segmentKey} or S.VirtualTest = ${parentTest}";
		
		parameters.put("parentTest", parentTest);
		query = fixDataBaseNames(SQL_QUERY5);
		SingleDataResultSet res5 = executeStatement(connection, query,
				parameters, true).getResultSets().next();
		resultsSets.add(res5);
		
	    _commonDll._LogDBLatency_SP (connection, "AA_GetSegment2",
	        startTime, null, true, null, null, null, client, segmentKey);

	    return new MultiDataResultSet (resultsSets);
	  }
   // -- Collect all test-level historical data needed to perform adaptive item
   // selection
   // -- Has the side-effect of updating existing data where needed (i.e.
   // TestopportunitySegment.itempool and TestOpportunity.itemgroupString,
   // initialAbility)
   public MultiDataResultSet AA_GetDataHistory2_SP (SQLConnection connection, UUID oppkey, String segmentKey) throws ReturnStatusException {
     Date startTime = _dateUtil.getDateWRetStatus (connection);

     String clientname = null;
     String itempool = null;
     String algorithm = null;
     Long testee = null;
     String subject = null;
     String testID = null;
     Float startability = null;

     List<SingleDataResultSet> resultsSets = new ArrayList<SingleDataResultSet> ();
     SingleDataResultSet res;
     DbResultRecord record;

     final String SQL_QUERY = "select clientname, _efk_Testee, subject "
         + ", _efk_TestID, initialAbility "
         + " from testopportunity where _Key = ${oppkey} ";

     SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey);

     res = executeStatement (connection, SQL_QUERY, parameters, false).getResultSets ().next ();
     record = res.getCount () > 0 ? res.getRecords ().next () : null;
     if (record != null) {
       clientname = subject = record.<String> get ("clientname");
       testee = record.<Long> get ("_efk_Testee");
       subject = record.<String> get ("subject");
       testID = record.<String> get ("_efk_TestID");
       startability = record.<Float> get ("initialAbility");
     }

     if (startability == null)
     {
       startability =_AA_GetInitialAbility_FN (connection, oppkey);

       final String SQL_QUERY1 = " update testopportunity set initialAbility = ${startAbility} "
           + " where _Key = ${oppkey} ";
       parameters.put ("startAbility", startability);
       executeStatement (connection, SQL_QUERY1, parameters, false);
     }

     final String SQL_QUERY2 = " select itempool, algorithm "
         + " from testopportunitysegment   "
         + " where _fk_TestOpportunity = ${oppkey} and _efk_Segment = ${segmentKey} ";

     parameters.put ("segmentKey", segmentKey);
     res = executeStatement (connection, SQL_QUERY2, parameters, true).getResultSets ().next ();
     record = res.getCount () > 0 ? res.getRecords ().next () : null;
     if (record != null) {
       itempool = record.<String> get ("itempool");
       algorithm = record.<String> get ("algorithm");
     }
     if (itempool == null && algorithm != null && DbComparator.containsIgnoreCase (algorithm, ADAPTIVE))
     {
       itempool = _AA_ItemPoolString_FN (connection, oppkey, segmentKey);
       //
       final String SQL_QUERY3 = " update testopportunitysegment set itempool = ${itempool} "
           + " where _Key = ${oppkey} ";
       parameters.put ("itempool", itempool);
       executeStatement (connection, SQL_QUERY3, parameters, false);
     }
     // -- First the segment-level specs
     final String SQL_QUERY4 = " select * from testopportunity "
         + " where _Key <> ${oppkey} and clientname = ${clientname} "
         + " and _efk_Testee = ${testee} and _efk_TestID = ${testID} "
         + " and itemgroupString is null ";

     parameters.put ("testee", testee).put ("testID", testID).put ("clientname", clientname);
     res = executeStatement (connection, SQL_QUERY4, parameters, false).getResultSets ().next ();
     if (res.getCount () > 0)
     {
       String itemgroupString = _commonDll.MakeItemGroupString_FN (connection, oppkey);
       final String SQL_QUERY5 = " update testopportunity set itemgroupString = ${itemgroupString} "
           + " where _Key <> ${oppkey} and clientname = ${clientname} "
           + " and _efk_Testee = ${testee} and _efk_TestID = ${testID} "
           + " and itemgroupString is null ";
       parameters.put ("itemgroupString", itemgroupString);
       executeStatement (connection, SQL_QUERY5, parameters, false);
     }

     List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
     CaseInsensitiveMap<Object> rcrd = new CaseInsensitiveMap<Object> ();
     rcrd.put ("initialAbility", startability);
     rcrd.put ("itempool", itempool);
     resultList.add (rcrd);
     SingleDataResultSet rs = new SingleDataResultSet ();
     rs.addColumn ("initialAbility", SQL_TYPE_To_JAVA_TYPE.FLOAT);
     rs.addColumn ("itempool", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
     rs.addRecords (resultList);
     resultsSets.add (rs);

     final String SQL_QUERY6 = " (select _Key as oppkey, dateStarted, itemgroupString "
         + " from testopportunity   "
         + " where _Key <> ${oppkey} and clientname =  ${clientname} "
         + " and _efk_Testee = ${testee} and _efk_TestID = ${testID}) "
         + " union all "
         + " (select _fk_TestOpportunity as oppkey, dateChanged, itemgroupString "
         + " from testeehistory   "
         + " where clientname =  ${clientname} "
         + " and _efk_Testee = ${testee} and subject = ${subject}  "
         + " and length(coalesce(itemgroupString, '')) > 0 ) "
         + " order by dateStarted ";

     parameters.put ("subject", subject);
     SingleDataResultSet res1 = executeStatement (connection, SQL_QUERY6, parameters, true).getResultSets ().next ();
     resultsSets.add (res1);

     // -- Adaptive algorithm must not select item groups that have been selected
     // for field test on this test
     final String SQL_QUERY7 = " select groupID as FTGroupID "
         + " from ft_opportunityitem where _fk_TestOpportunity = ${oppkey} and coalesce(deleted, 0) = 0 ";

     SingleDataResultSet res2 = executeStatement (connection, SQL_QUERY7, parameters, false).getResultSets ().next ();
     resultsSets.add (res2);

     // -- Collect the entire set of item responses on all segments
     final String SQL_QUERY8 = " select R.segment as segmentPosition, R.segmentID, R.page"
         + ", R.position, R.groupID, R._efk_Itemkey as itemID, R.score, R.isFieldTest, R.scoreDimensions "
         + " from testeeresponse R   where R._fk_TestOpportunity = ${oppkey} "
         + " and _efk_ItemKey is not null ";

     SingleDataResultSet res3 = executeStatement (connection, SQL_QUERY8, parameters, true).getResultSets ().next ();
     resultsSets.add (res3);

     _commonDll._LogDBLatency_SP (connection, "AA_GetDataHistory2_SP", startTime, null, true, null, oppkey);

     return new MultiDataResultSet (resultsSets);
   } 
   
   /**
-- =============================================
-- GetInitialAbility is used by the testee app to attempt to get the initial ability estimates for adaptive test
-- this gets the most recently scored opportunity in this test, or another test of the same subject
-- ASSUME: Previous year's scores are in an opportunity numbered 0 and are in a testID different from input parameter.

-- 3/2011: Updated to utilize TestOpportunity._Key and to get clientname from TestOpportunity
-- 9/2012: Convert the procedure to a function
-- 11/2013: added ability to specify a testID from which to pull initial ability, current or previous years; overrides bySubject.
--				Also renamed form FN_GetInitialAbility for use with new adaptive algorithm.
-- =============================================
    */
   public Float _AA_GetInitialAbility_FN (SQLConnection connection, UUID oppkey) 
			  throws ReturnStatusException {

	    final String INITIALABILITY = "INITIALABILITY";

	    Float ability = null;
	    String abilityString = null;

	    Date maxdate = null;
	    String clientname = null;
	    String test = null;
	    Long testee = null;
	    String subject = null;
	    Boolean bySubject = null;
	    Float slope = null;
	    Float intercept = null;
	    String initialAbilityTestId = null;

	    SingleDataResultSet res;
	    DbResultRecord record;
	    // attributeValue is varchar in this table
	    final String SQL_QUERY = "select attributeValue as ability "
	        + " from testeeattribute where _fk_TestOpportunity = ${oppkey} and TDS_ID = ${INITIALABILITY} ";

	    SqlParametersMaps parameters = new SqlParametersMaps ().put ("oppkey", oppkey).put ("INITIALABILITY", INITIALABILITY);

	    res = executeStatement (connection, SQL_QUERY, parameters, false).getResultSets ().next ();
	    record = res.getCount () > 0 ? res.getRecords ().next () : null;
	    if (record != null) {
	      abilityString = record.<String> get ("ability");
	      try {
	        ability = Float.parseFloat (abilityString);
	      } catch (Exception e)
	      {
	        _logger.error ("Cannot parse " + abilityString + " from String to Float. Set ability = 0.0");		        
	      }
	    }
	    if (ability != null)
	    {
	      return ability;
	    }

	    final String SQL_QUERY1 = "select  _efk_Testee, subject, clientname "
	        + ", _efk_AdminSubject,  _efk_TestID "
	        + " from testopportunity where _Key = ${oppkey} ";

	    res = executeStatement (connection, SQL_QUERY1, parameters, false).getResultSets ().next ();
	    record = res.getCount () > 0 ? res.getRecords ().next () : null;
	    if (record != null) {
	      clientname = record.<String> get ("clientname");
	      testee = record.<Long> get ("_efk_Testee");
	      subject = record.<String> get ("subject");
	      test = record.<String> get ("_efk_TestID");
	    }

	    final String SQL_QUERY2 = "select initialAbilityBySubject, abilitySlope, abilityIntercept, initialAbilityTestId "
	        + " from ${ConfigDB}.client_testproperties where clientname = ${clientname} and TestID = ${test}";

	    parameters.put ("clientname", clientname).put ("test", test);
	    String query = fixDataBaseNames (SQL_QUERY2);
	    res = executeStatement (connection, query, parameters, false).getResultSets ().next ();
	    record = res.getCount () > 0 ? res.getRecords ().next () : null;
	    if (record != null) {
	      bySubject = record.<Boolean> get ("initialAbilityBySubject");
	      slope = record.<Float> get ("abilitySlope");
	      intercept = record.<Float> get ("abilityIntercept");
	      initialAbilityTestId = record.<String> get ("initialAbilityTestId");
	    }
	    if (bySubject == null)
	    {
	      bySubject = false;
	    }
	    //-- if a testID was provided, that will override bySubject; these approaches are mutually exclusive
	    if (initialAbilityTestId == null)
	    {
	      bySubject = false;
	    }

	    // -- this table holds all possible alternatives. No field is null.
	    // declare @tbl table (oppkey uniqueidentifier, test varchar(200), opportunity int, scored datetime, ability float);
	    DataBaseTable pool = getDataBaseTable ("tmpTable")
	            .addColumn ("oppkey", SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER)
	            .addColumn ("test", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 200)
	            .addColumn ("opportunity", SQL_TYPE_To_JAVA_TYPE.INT)
	            .addColumn ("scored", SQL_TYPE_To_JAVA_TYPE.DATETIME)
	            .addColumn ("ability", SQL_TYPE_To_JAVA_TYPE.FLOAT);

	        connection.createTemporaryTable (pool);

	    final String SQL_QUERY3 = "insert into ${tblName} (oppkey, test, opportunity, scored, ability)"
	    	+ " select OTHEROPP._Key as oppkey, OTHEROPP._efk_TestID as test"
	        + ",  OTHEROPP.Opportunity as opportunity, OTHEROPP.dateScored as scored, SCORE.value as ability"
	        + " from testopportunity OTHEROPP, testopportunityscores SCORE "
	        + " where clientname = ${clientname} "
	        // -- find all other opportunity candidates, same testee, same subject, scored and not deleted
	        // --	AM: changed to same subject or configured testId, in case the configured testID is not
	        // --	of the same subject as the current test opp (which I doubt will be the case, but...)
	        + " and OTHEROPP._efk_Testee = ${testee} "
	        + " and (OTHEROPP.subject = ${subject} or OTHEROPP._efk_TestID = ${initialAbilityTestId})"
	        + " and OTHEROPP.dateDeleted is null "
	        + " and OTHEROPP.dateScored is not null and OTHEROPP._Key <> ${oppkey} "
	        // -- that have a usable ability score
	        + " and OTHEROPP._Key = SCORE._fk_TestOpportunity "
	        + " and SCORE.UseForAbility = 1 and SCORE.value is not null";

	    Map<String, String> unquotedparms = new HashMap<String, String> ();
	    unquotedparms.put ("tblName", pool.getTableName ());
	    parameters.put ("testee", testee).put ("subject", subject).put ("initialAbilityTestId", initialAbilityTestId);	    
	    int insertedCnt = executeStatement (connection, fixDataBaseNames (SQL_QUERY3, unquotedparms), parameters, false).getUpdateCount ();

	    _logger.info ("Inserted rows after Insert query in temporary table in AF_GetItempool_FN are " + insertedCnt);
	    // -- first, try to find a previous opportunity on this exact same test.
	    // -- look for the latest scored
	    query = "select max(scored) as maxdate from ${tblName} where test = ${test}";
	    
	    parameters.put ("test", test);
	    res = executeStatement (connection, fixDataBaseNames (query, unquotedparms), parameters, false).getResultSets ().next ();
	    record = res.getCount () > 0 ? res.getRecords ().next () : null;
		if (record != null) {
			maxdate = record.<Date> get("maxdate");
		}
		if (maxdate != null) {
			// found one of same test. Return the ability value, print 'Same
			// test';
			query = "select ability from ${tblName} "
					+ " where test = ${test} and scored = ${maxdate} limit 1";

			parameters.put("maxdate", maxdate);
			res = executeStatement(connection,
					fixDataBaseNames(query, unquotedparms), parameters, false)
					.getResultSets().next();
			record = res.getCount() > 0 ? res.getRecords().next() : null;
			if (record != null) {
				ability = record.<Float> get("ability");
			}
			return ability;
		}
		// -- DIFF TEST, SAME YEAR...
		// -- failing that, if a testID was configured, look in the current year
		// for an opp
		if (initialAbilityTestId != null) {
			query = "select max(scored) as maxdate from ${tblName} where test = ${initialAbilityTestId}";

			res = executeStatement(connection,
					fixDataBaseNames(query, unquotedparms), parameters, false)
					.getResultSets().next();
			record = res.getCount() > 0 ? res.getRecords().next() : null;
			if (record != null) {
				maxdate = record.<Date> get("maxdate");
			}
			if (maxdate != null) {
				query = "select ability from ${tblName} "
						+ " where test = ${initialAbilityTestId} and scored = ${maxdate} limit 1";

				parameters.put("maxdate", maxdate);
				res = executeStatement(connection,fixDataBaseNames(query, unquotedparms), parameters,
						false).getResultSets().next();
				record = res.getCount() > 0 ? res.getRecords().next() : null;
				if (record != null) {
					ability = record.<Float> get("ability");
				}

				// -- found one of configured testID. Return the ability value
				// after applying linear transformation
				// -- Note that transformation is not applied if searching by
				// subject in the same year (below)
				// -- print 'Configured testID, same year';
				ability = ability * slope + intercept;
				return ability;
			}
		}
		// -- failing that, try for same subject/different test (mutually
		// exclusive with configured testID)
		else if (bySubject) 
		{// -- failing that, try for same subject/different test
			query = "select max(scored) as maxdate from ${tblName} where test <> ${test}";

			res = executeStatement(connection,
					fixDataBaseNames(query, unquotedparms), parameters, false)
					.getResultSets().next();
			record = res.getCount() > 0 ? res.getRecords().next() : null;
			if (record != null) {
				maxdate = record.<Date> get("maxdate");
			}
			if (maxdate != null) {
				// print 'By subject, different test';
				query = "select ability from ${tblName} "
						+ " where test <> ${test} and scored = ${maxdate} limit 1";

				parameters.put("maxdate", maxdate);
				res = executeStatement(connection,
						fixDataBaseNames(query, unquotedparms), parameters,
						false).getResultSets().next();
				record = res.getCount() > 0 ? res.getRecords().next() : null;
				if (record != null) {
					ability = record.<Float> get("ability");
				}
				return ability;
			}
		}
		//
		// -- CHECK HISTORY...
		// -- SAME TEST...
		if (!bySubject) {
			// -- only if not checking by-subject; by-subject includes this
			// test, and I didn't want to alter existing logic
			// -- taking same approach as below for by-subject: max score

			final String SQL_QUERY5 = "select max(initialAbility) as ability "
					+ " from testeehistory "
					+ " where clientname = ${clientname} and _efk_Testee = ${testee} "
					+ " and TestID = ${test} and initialAbility is not null ";

			res = executeStatement(connection, SQL_QUERY5, parameters, false)
					.getResultSets().next();
			record = res.getCount() > 0 ? res.getRecords().next() : null;
			if (record != null) {
				ability = record.<Float> get("ability");
			}
			if (ability != null) { // -- print 'Same test, history';
				return ability;
			}
		}
	    //-- DIFF TEST...
	    //-- failing that, if another testID was configured, look in a prior year for an opp
		if (initialAbilityTestId != null) {
			final String SQL_QUERY6 = "select max(initialAbility) as ability "
					+ " from testeehistory "
					+ " where clientname = ${clientname} and _efk_Testee = ${testee} "
					+ " and TestID = ${initialAbilityTestId} and initialAbility is not null ";

			res = executeStatement(connection, SQL_QUERY6, parameters, false)
					.getResultSets().next();
			record = res.getCount() > 0 ? res.getRecords().next() : null;
			if (record != null) {
				ability = record.<Float> get("ability");
			}
			if (ability != null) {
				// -- print 'Configured testID, history';
				ability = ability * slope + intercept;
				return ability;
			}

		}
		//	-- failing that, try to get an initial ability from the previous year  
		// (mutually exclusive with configured testID)
		else if (bySubject) {// -- on their first test this year, they are
								// likely at their lowest performance level
			final String SQL_QUERY7 = "select max(initialAbility) as ability "
					+ " from testeehistory "
					+ " where clientname = ${clientname} and _efk_Testee = ${testee} "
					+ " and Subject = ${subject} and initialAbility is not null ";

			res = executeStatement(connection, SQL_QUERY7, parameters, false)
					.getResultSets().next();
			record = res.getCount() > 0 ? res.getRecords().next() : null;
			if (record != null) {
				ability = record.<Float> get("ability");
			}
			if (ability != null) {
				// -- print 'By subject, history';
				ability = ability * slope + intercept;
				return ability;
			}
		}
		// -- no longer returning the default value from the itembank, because the blueprint will already
		// -- be loaded with default start ability values for all segments (and reporting categories for that matter).
		// -- Returning a token value to flag to the caller that there is no start value for this student.
		// -- print 'DEFAULT';		
		// -- else get the default for this test from the itembank
		// -- print 'Itembank';
		return (float) -9999;
	}

   @Override
	public MultiDataResultSet AA_GetDataHistory_LA2_SP(
			SQLConnection connection, UUID oppkey, String segmentKey)
			throws ReturnStatusException {
		Date startTime = _dateUtil.getDateWRetStatus(connection);

		String clientname = null;
		String itempool = null;
		String algorithm = null;
		Long testee = null;
		String subject = null;
		String testID = null;
		Float startability = null;

		List<SingleDataResultSet> resultsSets = new ArrayList<SingleDataResultSet>();
		SingleDataResultSet res;
		DbResultRecord record;

		final String SQL_QUERY = "select clientname, _efk_Testee, subject "
				+ ", _efk_TestID, initialAbility "
				+ " from testopportunity where _Key = ${oppkey} ";

		SqlParametersMaps parameters = new SqlParametersMaps().put("oppkey",
				oppkey);

		res = executeStatement(connection, SQL_QUERY, parameters, false)
				.getResultSets().next();
		record = res.getCount() > 0 ? res.getRecords().next() : null;
		if (record != null) {
			clientname = subject = record.<String> get("clientname");
			testee = record.<Long> get("_efk_Testee");
			subject = record.<String> get("subject");
			testID = record.<String> get("_efk_TestID");
			startability = record.<Float> get("initialAbility");
		}

		final String SQL_QUERY2 = " select itempool, algorithm "
				+ " from testopportunitysegment   "
				+ " where _fk_TestOpportunity = ${oppkey} and _efk_Segment = ${segmentKey} ";

		parameters.put("segmentKey", segmentKey);
		res = executeStatement(connection, SQL_QUERY2, parameters, true)
				.getResultSets().next();
		record = res.getCount() > 0 ? res.getRecords().next() : null;
		if (record != null) {
			itempool = record.<String> get("itempool");
			algorithm = record.<String> get("algorithm");
		}
		if (itempool == null && algorithm != null
				&& DbComparator.containsIgnoreCase(algorithm, ADAPTIVE)) {
			itempool = _AA_ItemPoolString_FN(connection, oppkey, segmentKey);
			//
			final String SQL_QUERY3 = " update testopportunitysegment set itempool = ${itempool} "
					+ " where _Key = ${oppkey} ";
			parameters.put("itempool", itempool);
			executeStatement(connection, SQL_QUERY3, parameters, false);
		}

		List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>>();
		CaseInsensitiveMap<Object> rcrd = new CaseInsensitiveMap<Object>();
		rcrd.put("initialAbility", startability);
		rcrd.put("itempool", itempool);
		resultList.add(rcrd);
		SingleDataResultSet rs = new SingleDataResultSet();
		rs.addColumn("initialAbility", SQL_TYPE_To_JAVA_TYPE.FLOAT);
		rs.addColumn("itempool", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
		rs.addRecords(resultList);
		resultsSets.add(rs);

   // -- BIG CHANGE: Counting on TesteeHistory for info about other tests taken this student
		final String SQL_QUERY6 = " select _fk_TestOpportunity as oppkey, dateChanged as dateStarted, itemgroupString "
				+ " from testeehistory   "
				+ " where clientname =  ${clientname} "
				+ " and _efk_Testee = ${testee} and subject = ${subject}  "
				+ " and _fk_TestOpportunity <> ${oppkey}"
				+ " and length(coalesce(itemgroupString, '')) > 0 "
				+ " order by dateStarted ";

		parameters.put("subject", subject).put("clientname", clientname)
				.put("testee", testee);
		SingleDataResultSet res1 = executeStatement(connection, SQL_QUERY6,
				parameters, true).getResultSets().next();
		resultsSets.add(res1);
   
   //-- placeholder for backward compatibility from the days when field test items were preselected at test creation time
   //-- AA should be able to deal with itemgroups it can't locate

   // following 8 lines is equals to " select 'NA' as FTGroupID"
		List<CaseInsensitiveMap<Object>> resultList2 = new ArrayList<CaseInsensitiveMap<Object>>();
		CaseInsensitiveMap<Object> rcrd2 = new CaseInsensitiveMap<Object>();
		rcrd2.put("FTGroupID", "NA");

		resultList2.add(rcrd2);
		SingleDataResultSet res2 = new SingleDataResultSet();
		res2.addColumn("FTGroupID", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
		res2.addRecords(resultList2);
		resultsSets.add(res2);
   
   //-- Collect the entire set of item responses on all segments
   //-- 1/2014: LEA: Added R.scoreDimensions for adaptive2
		final String SQL_QUERY8 = " select G.segment as segmentPosition, G.segmentID, G.page, I.position,"
				+ " G.groupID, I._efk_Itemkey as itemID, R.score, I.isFieldTest, R.scoreDimensions "
				+ " from testoppitem I   "
				+ " join testoppitemgroup G on G._fk_TestOpportunity = ${oppkey} and I._fk_OppItemGroup = G._Key"
				+ " join testoppitemresponse R on R._fk_TestOpportunity = ${oppkey} and R._Key = I._fk_Response"
				+ " where R._fk_TestOpportunity = ${oppkey} and _efk_ItemKey is not null ";

		SingleDataResultSet res3 = executeStatement(connection, SQL_QUERY8,
				parameters, true).getResultSets().next();
		resultsSets.add(res3);

		_commonDll._LogDBLatency_SP(connection, "AA_GetDataHistory_LA2_SP",
				startTime, null, true, null, oppkey);

		return new MultiDataResultSet(resultsSets);
	}

   @Override
	public MultiDataResultSet AA_GetDataHistory2_LA2_SP(
			SQLConnection connection, UUID oppkey, String segmentKey)
			throws ReturnStatusException {
		Date startTime = _dateUtil.getDateWRetStatus(connection);
		//    -- GEO NOTE: Startability is computed on the SHARD when the test opp record is first generated. Do not attempt to compute here (on satellite)
		String clientname = null;
		String itempool = null;
		String algorithm = null;
		Long testee = null;
		String subject = null;
		String testID = null;
		Float startability = null;

		List<SingleDataResultSet> resultsSets = new ArrayList<SingleDataResultSet>();
		SingleDataResultSet res;
		DbResultRecord record;

		final String SQL_QUERY = "select clientname, _efk_Testee, subject "
				+ ", _efk_TestID, initialAbility "
				+ " from testopportunity where _Key = ${oppkey} ";

		SqlParametersMaps parameters = new SqlParametersMaps().put("oppkey",
				oppkey);

		res = executeStatement(connection, SQL_QUERY, parameters, false)
				.getResultSets().next();
		record = res.getCount() > 0 ? res.getRecords().next() : null;
		if (record != null) {
			clientname = subject = record.<String> get("clientname");
			testee = record.<Long> get("_efk_Testee");
			subject = record.<String> get("subject");
			testID = record.<String> get("_efk_TestID");
			startability = record.<Float> get("initialAbility");
		}

		final String SQL_QUERY2 = " select itempool, algorithm "
				+ " from testopportunitysegment   "
				+ " where _fk_TestOpportunity = ${oppkey} and _efk_Segment = ${segmentKey} ";

		parameters.put("segmentKey", segmentKey);
		res = executeStatement(connection, SQL_QUERY2, parameters, true)
				.getResultSets().next();
		record = res.getCount() > 0 ? res.getRecords().next() : null;
		if (record != null) {
			itempool = record.<String> get("itempool");
			algorithm = record.<String> get("algorithm");
		}
		if (itempool == null && algorithm != null
				&& DbComparator.containsIgnoreCase(algorithm, ADAPTIVE)) {
			itempool = _AA_ItemPoolString_FN(connection, oppkey, segmentKey);
			//
			final String SQL_QUERY3 = " update testopportunitysegment set itempool = ${itempool} "
					+ " where _Key = ${oppkey} ";
			parameters.put("itempool", itempool);
			executeStatement(connection, SQL_QUERY3, parameters, false);
		}

		List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>>();
		CaseInsensitiveMap<Object> rcrd = new CaseInsensitiveMap<Object>();
		rcrd.put("initialAbility", startability);
		rcrd.put("itempool", itempool);
		resultList.add(rcrd);
		SingleDataResultSet rs = new SingleDataResultSet();
		rs.addColumn("initialAbility", SQL_TYPE_To_JAVA_TYPE.FLOAT);
		rs.addColumn("itempool", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
		rs.addRecords(resultList);
		resultsSets.add(rs);

   // -- BIG CHANGE: Counting on TesteeHistory for info about other tests taken this student
		final String SQL_QUERY6 = " select _fk_TestOpportunity as oppkey, dateChanged as dateStarted, itemgroupString "
				+ " from testeehistory   "
				+ " where clientname =  ${clientname} "
				+ " and _efk_Testee = ${testee} and subject = ${subject}  "
				+ " and _fk_TestOpportunity <> ${oppkey}"
				+ " and length(coalesce(itemgroupString, '')) > 0 "
				+ " order by dateStarted ";

		parameters.put("subject", subject).put("clientname", clientname)
				.put("testee", testee);
		SingleDataResultSet res1 = executeStatement(connection, SQL_QUERY6,
				parameters, true).getResultSets().next();
		resultsSets.add(res1);
   
   //-- Collect the entire set of item responses on all segments
		final String SQL_QUERY8 = " select G.segment as segmentPosition, G.segmentID, G.page, I.position,"
				+ " G.groupID, I._efk_Itemkey as itemID, R.score, I.isFieldTest, R.scoreDimensions "
				+ " from testoppitem I   "
				+ " join testoppitemgroup G on G._fk_TestOpportunity = ${oppkey} and I._fk_OppItemGroup = G._Key"
				+ " join testoppitemresponse R on R._fk_TestOpportunity = ${oppkey} and R._Key = I._fk_Response"
				+ " where R._fk_TestOpportunity = ${oppkey} and _efk_ItemKey is not null ";

		SingleDataResultSet res3 = executeStatement(connection, SQL_QUERY8,
				parameters, true).getResultSets().next();
		resultsSets.add(res3);

		_commonDll._LogDBLatency_SP(connection, "AA_GetDataHistory_LA2_SP",
				startTime, null, true, null, oppkey);

		return new MultiDataResultSet(resultsSets);
	}

}
