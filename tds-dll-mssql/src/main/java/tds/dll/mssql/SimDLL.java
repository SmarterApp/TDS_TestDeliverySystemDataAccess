/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/

package tds.dll.mssql;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import TDS.Shared.Exceptions.ReturnStatusException;
import tds.dll.api.ICommonDLL;
import tds.dll.api.ISimDLL;
import AIR.Common.DB.AbstractDLL;
import AIR.Common.DB.DataBaseTable;
import AIR.Common.DB.SQLConnection;
import AIR.Common.DB.SQL_TYPE_To_JAVA_TYPE;
import AIR.Common.DB.SqlParametersMaps;
import AIR.Common.DB.results.DbResultRecord;
import AIR.Common.DB.results.MultiDataResultSet;
import AIR.Common.DB.results.SingleDataResultSet;
import AIR.Common.Sql.AbstractDateUtilDll;

public class SimDLL extends AbstractDLL implements ISimDLL {

	private static Logger       _logger    = LoggerFactory.getLogger (SimDLL.class);
	  
	@Autowired
	private ICommonDLL          _commonDll = null;
	  
	@Autowired
	private AbstractDateUtilDll _dateUtil  = null;

	@Override
	public SingleDataResultSet SIM_GetUserClients_SP(SQLConnection connection,
			String userId) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_ValidateUser_SP(SQLConnection connection,
			String userId) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_ValidateUser_SP(SQLConnection connection,
			String userId, String password) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_CreateSession_SP(SQLConnection connection,
			String clientname, String userId, String sessionName)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_CreateSession_SP(SQLConnection connection,
			String clientname, String userId, String sessionName,
			String language) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_CreateSession_SP(SQLConnection connection,
			String clientname, String userId, String sessionName,
			String language, String sessionDescription, Integer sessiontype)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_GetUserSessions_SP(SQLConnection connection,
			String userId) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_GetUserSessions_SP(SQLConnection connection,
			String userId, String clientname) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_CopySession2_SP(SQLConnection connection,
			UUID fromSession, String sessionName) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_CopySession2_SP(SQLConnection connection,
			UUID fromSession, String sessionName, String sessionDescription,
			String toUser) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_GetSessionTests2_SP(
			SQLConnection connection, UUID sessionKey)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_GetSessionTests_SP(SQLConnection connection,
			UUID sessionKey) throws ReturnStatusException {
		final String cmd1 = "select S._efk_AdminSubject as testkey, S._efk_TestID as testID, iterations, opportunities, meanProficiency, "
				+ " sdProficiency, strandCorrelation,  "
				+ " (select count(*) from testopportunity O "
				+ "         where O._fk_Session = ${sessionKey} and O._efk_AdminSubject = S._efk_AdminSubject and dateCompleted is not null) as simulations"
				+ " from sessiontests S where s._fk_Session = ${sessionKey}";
		SqlParametersMaps parms1 = (new SqlParametersMaps()).put("sessionKey",
				sessionKey);
		SingleDataResultSet rs1 = executeStatement(connection, cmd1, parms1,
				false).getResultSets().next();
		return rs1;
	}

	@Override
	public void SIM_SetSessionAbort_SP(SQLConnection connection, UUID session,
			Boolean abort) throws ReturnStatusException {
		// TODO Auto-generated method stub

	}

	@Override
	public void SIM_SetSessionDescription_SP(SQLConnection connection,
			UUID session, String description) throws ReturnStatusException {
		// TODO Auto-generated method stub

	}

	@Override
	public SingleDataResultSet SIM_AddSessionTest2_SP(SQLConnection connection,
			UUID session, String testkey) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_AddSessionTest2_SP(SQLConnection connection,
			UUID session, String testkey, Integer iterations,
			Integer opportunities, Float meanProficiency, Float sdProficiency,
			Float strandCorrelation, String handScoreItemTypes)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_AlterSegmentItemgroup_SP(
			SQLConnection connection, UUID session, String testkey,
			String segmentKey, String groupId, Integer maxItems)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_AlterSegmentStrand2_SP(
			SQLConnection connection, UUID session, String testkey,
			String segmentKey, String strand, Integer minItems,
			Integer maxItems, Float bpweight, Boolean isStrictMax,
			Float startAbility, Float startInfo) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_AlterSegmentStrand2_SP(
			SQLConnection connection, UUID session, String testkey,
			String segmentKey, String strand, Integer minItems,
			Integer maxItems, Float bpweight, Boolean isStrictMax,
			Float startAbility, Float startInfo, Float adaptiveCut,
			Float scalar, Float abilityWeight,
			Float precisionTargetNotMetWeight, Float precisionTargetMetWeight,
			Float precisionTarget) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_AlterSegmentContentLevel_SP(
			SQLConnection connection, UUID session, String testkey,
			String segmentKey, String contentLevel, Integer minItems,
			Integer maxItems, Float bpweight, Boolean isStrictMax)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiDataResultSet SIM_GetTestBlueprint2_SP(
			SQLConnection connection, UUID session, String testkey)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiDataResultSet SIM_GetTestItems_SP(SQLConnection connection,
			UUID session, String testkey) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiDataResultSet SIM_ReportBPSatisfaction_SP(
			SQLConnection connection, UUID session, String testkey)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiDataResultSet SIM_ReportBPSatisfaction_SP(
			SQLConnection connection, UUID session, String testkey,
			Boolean reportBlueprint) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiDataResultSet SIM_ValidateBlueprints_SP(
			SQLConnection connection, UUID session)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_ClearSessionOpportunityData_SP(
			SQLConnection connection, UUID session)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_ClearSessionOpportunityData_SP(
			SQLConnection connection, UUID session, String testkey)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_DeleteTest_SP(SQLConnection connection,
			UUID session, String testkey) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_AlterSessionTest2_SP(
			SQLConnection connection, UUID session, String testkey,
			Integer iterations, Integer opportunities, Float meanProficiency,
			Float sdProficiency, Float strandCorrelation)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_AlterSessionTest2_SP(
			SQLConnection connection, UUID session, String testkey,
			Integer iterations, Integer opportunities, Float meanProficiency,
			Float sdProficiency, Float strandCorrelation,
			String handScoreItemTypes) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_DeleteSession_SP(SQLConnection connection,
			UUID session) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_AlterSegment2_SP(SQLConnection connection,
			UUID session, String testkey, String segmentKey,
			Float startAbility, Float startInfo, Integer minItems,
			Integer maxItems, Float bpweight, Integer cset1size,
			Integer cset2InitialRandom, Integer cset2Random, Float itemWeight,
			Float abilityOffset) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_AlterSegment2_SP(SQLConnection connection,
			UUID session, String testkey, String segmentKey,
			Float startAbility, Float startInfo, Integer minItems,
			Integer maxItems, Float bpweight, Integer cset1size,
			Integer cset2InitialRandom, Integer cset2Random, Float itemWeight,
			Float abilityOffset, String selectionAlgorithm, String cset1Order,
			Integer ftmin, Integer ftmax, Integer ftstart, Integer ftend,
			Float rcAbilityWeight, Float abilityWeight,
			Float precisionTargetMetWeight, Float precisionTargetNotMetWeight,
			Float precisionTarget, Float adaptiveCut, Float tooCloseSEs,
			Boolean terminationOverallInfo, Boolean terminationRCInfo,
			Boolean terminationMinCount, Boolean terminationTooClose,
			Boolean terminationFlagsAnd) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_ReportOPItemDistribution_SP(
			SQLConnection connection, UUID session, String testkey)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_ReportOPItemDistribution_SP(
			SQLConnection connection, UUID session, String testkey,
			Float distributionInterval) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiDataResultSet SIM_ItemDistribution_SP(SQLConnection connection,
			UUID session, String testkey) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiDataResultSet SIM_ReportItems_SP(SQLConnection connection,
			UUID session, String testkey) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiDataResultSet SIM_ReportItems_SP(SQLConnection connection,
			UUID session, String testkey, Boolean debug)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_ReportRecycled_SP(SQLConnection connection,
			UUID session, String testkey) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void _RecordSegmentSatisfaction_SP(SQLConnection connection,
			UUID oppkey) throws ReturnStatusException {
		// TODO Auto-generated method stub

	}

	@Override
	public void __PatchContentCounts_SP(SQLConnection connection,
			UUID sessionKey, String testKey) throws ReturnStatusException {
		// TODO Auto-generated method stub

	}

	@Override
	public void __PatchSegmentCounts_SP(SQLConnection connection,
			UUID sessionKey, String testKey) throws ReturnStatusException {
		// TODO Auto-generated method stub

	}

	@Override
	public SingleDataResultSet SIM_AlterSegmentItem_SP(
			SQLConnection connection, UUID session, String testkey,
			String segmentKey, String itemKey, Boolean isActive,
			Boolean isRequired) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_ReportSegmentSatisfaction_SP(
			SQLConnection connection, UUID session, String testkey)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiDataResultSet SIM_ReportBPSummary_SP(SQLConnection connection,
			UUID session, String testkey) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Float _SIM_ComputeCoverage05_FN(SQLConnection connection,
			UUID session, String testkey) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataBaseTable _SIM_ComputeBias_FN(SQLConnection connection,
			UUID session, String testkey) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataBaseTable _SIM_Init2Firstb_Correlation_FN(
			SQLConnection connection, UUID session, String testkey)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiDataResultSet SIM_ReportSummaryStats_SP(
			SQLConnection connection, UUID session, String testkey)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiDataResultSet SIM_ReportScores_SP(SQLConnection connection,
			UUID session, String testkey) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiDataResultSet SIM_ReportFormDistributions_SP(
			SQLConnection connection, UUID session, String testkey)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiDataResultSet SIM_FieldtestDistribution_SP(
			SQLConnection connection, UUID session, String testkey)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_ChangeSegmentNonReportingCategoryAsReportingCategory_SP(
			SQLConnection connection, UUID session, String testkey,
			String segmentKey, String contentLevel)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_ChangeSegmentReportingCategoryAsNonReportingCategory_SP(
			SQLConnection connection, UUID session, String testkey,
			String segmentKey, String strand) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDataResultSet SIM_GetErrors_SP(SQLConnection connection,
			UUID session) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiDataResultSet SIM_ReportOpportunities_SP(
			SQLConnection connection, UUID session, String testKey)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer sim_setSimulationRunProps(SQLConnection connection,
			UUID session, String status) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer sim_setSimulationErrorProps(SQLConnection connection,
			UUID session, String status) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String sim_GetSimStatus(SQLConnection connection, UUID session)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiDataResultSet SIM_GetTestControls2_SP(SQLConnection connection,
			UUID session, String testkey) throws ReturnStatusException {
		List<SingleDataResultSet> resultsets = new ArrayList<SingleDataResultSet>();

		final String cmd1 = "select coalesce(N.sim_language, 'ENU') as language, "
				+ " S.iterations, S.opportunities, S.meanProficiency, S.sdProficiency, "
				+ " S.strandCorrelation, S.handScoreItemTypes, "
				+ " M.dbname as itembank, sim_threads as threads, sim_thinkTime as thinkTime, "
				+ " (select count(*) from testopportunity O "
				+ "    where O._fk_Session = ${sessionKey} and O._efk_AdminSubject = S._efk_AdminSubject "
				+ "      and dateCompleted is not null) "
				+ "  as simulations "
				+ "from session N, sessiontests S, _synonyms M "
				+ " where N._Key = ${sessionKey} and s._fk_Session = ${sessionKey} and _efk_AdminSubject = ${testkey} "
				+ "  and M.prefix = 'ITEMBANK_'";
		SqlParametersMaps parms1 = (new SqlParametersMaps()).put("sessionkey",
				session).put("testkey", testkey);
		SingleDataResultSet rs1 = executeStatement(connection, cmd1, parms1,
				false).getResultSets().next();

		resultsets.add(rs1);

		final String cmd2 = "select distinct contentLevel as strand "
				+ "from sim_segmentcontentlevel L, sim_segment S "
				+ "  where S._fk_Session = ${sessionKey} and L._fk_Session = ${sessionKey} "
				+ "  and S._efk_AdminSubject = ${testkey} and S._efk_Segment = L._efk_Segment "
				+ "  and L.StartAbility is not null";
		SqlParametersMaps parms2 = parms1;
		SingleDataResultSet rs2 = executeStatement(connection, cmd2, parms2,
				false).getResultSets().next();

		resultsets.add(rs2);

		return new MultiDataResultSet(resultsets);	
	}

	@Override
	public SingleDataResultSet sim_getItemTypes(SQLConnection connection,
			String adminSuject) throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void SIM_LogError_SP(SQLConnection connection, UUID session,
			String clientname, String procname, String oppkey, String testkey,
			String msg) throws ReturnStatusException {
		// TODO Auto-generated method stub

	}

	@Override
	public void SIM_LogSessionError_SP(SQLConnection connection, UUID session,
			String clientname, String procname, String testkey, String msg)
			throws ReturnStatusException {
		// TODO Auto-generated method stub

	}

	@Override
	public SingleDataResultSet SIM_OpenSession_SP(SQLConnection connection,
			UUID sessionkey) throws ReturnStatusException {
		Date startDate = _dateUtil.getDateWRetStatus(connection);
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.MONTH, 1);
		Date endDate = cal.getTime();
		Timestamp startTime = new Timestamp(startDate.getTime());
		Timestamp endTime = new Timestamp(endDate.getTime());

		final String cmd1 = "   update session set status = 'open', dateend = ${endDate}, "
				+ " datevisited = ${startDate}, datechanged = ${startDate} "
				+ "   where _key = ${sessionKey} and environment = 'SIMULATION'";
		SqlParametersMaps parms1 = (new SqlParametersMaps())
				.put("sessionkey", sessionkey).put("startDate", startTime)
				.put("endDate", endTime);
		int updatedCnt = executeStatement(connection, cmd1, parms1, false).getUpdateCount();

		final String cmd2 = "  select dbname as itembank, clientname, sessionID, _efk_Proctor as proctorKey, "
				+ " _fk_Browser as browserKey, sim_language as language, sim_proctorDelay as proctorDelay "
				+ " from session, _synonyms "
				+ " where _key = ${sessionKey} and environment = 'SIMULATION' and prefix = 'ITEMBANK_'";
		SqlParametersMaps parms2 = parms1;
		SingleDataResultSet rs = executeStatement(connection, cmd2, parms2,
				false).getResultSets().next();
		return rs;
	}

	@Override
	public void SIM_EndSimulation(SQLConnection connection, UUID session,
			String status) throws ReturnStatusException {
		// TODO Auto-generated method stub

	}

	@Override
	public void SIM_CleanupSessionTest(SQLConnection connection, UUID session,
			String testKey) throws ReturnStatusException {
		DataBaseTable oppsTbl = getDataBaseTable("opps").addColumn("oppkey",
				SQL_TYPE_To_JAVA_TYPE.UNIQUEIDENTIFIER).addColumn("oppnum",
				SQL_TYPE_To_JAVA_TYPE.INT);
		connection.createTemporaryTable(oppsTbl);

		final String SQL_INDEX = "create index opp_idx on ${oppsTbl} (oppkey)";
		Map<String, String> unquotedparms = new HashMap<String, String>();
		unquotedparms.put("oppsTbl", oppsTbl.getTableName());
		executeStatement(connection,
				fixDataBaseNames(SQL_INDEX, unquotedparms), null, false);

		final String cmd1 = "insert into ${oppsTbl} (oppkey, oppnum) "
				+ " select _Key, opportunity from testopportunity "
				+ " where _fk_Session = ${session} and _efk_AdminSUbject = ${testkey} and datecompleted is null";
		SqlParametersMaps parms1 = (new SqlParametersMaps()).put("session",
				session).put("testkey", testKey);
		int insertedCnt = executeStatement(connection,
				fixDataBaseNames(cmd1, unquotedparms), parms1, false)
				.getUpdateCount();

		Integer oppcnt = null;
		final String cmd2 = "select opportunities as oppcnt from sessiontests "
				+ " where _fk_Session = ${session} and _efk_AdminSUbject = ${testkey}";
		SqlParametersMaps parms2 = parms1;
		SingleDataResultSet rs2 = executeStatement(connection, cmd2, parms2,
				false).getResultSets().next();
		DbResultRecord rcd2 = (rs2.getCount() > 0 ? rs2.getRecords().next()
				: null);
		if (rcd2 != null)
			oppcnt = rcd2.<Integer> get("oppcnt");

		final String cmd3 = "insert into ${oppsTbl} (oppkey, oppnum) "
				+ " select _key, opportunity from testopportunity O1 "
				+ "  where _fk_Session = ${session} and _efk_AdminSUbject = ${testkey} and Opportunity < ${oppcnt} "
				+ "  and not exists "
				+ "    (select * from testopportunity O2 where  O2._fk_Session = ${session} "
				+ "      and O2._efk_AdminSubject = ${testkey} "
				+ "      and O2._Key <> O1._Key and O2._efk_Testee = O1._efk_Testee and O2.opportunity = ${oppcnt} "
				+ "      and datecompleted is not null)";
		SqlParametersMaps parms3 = (new SqlParametersMaps())
				.put("session", session).put("testkey", testKey)
				.put("oppcnt", oppcnt);
		insertedCnt = executeStatement(connection,
				fixDataBaseNames(cmd3, unquotedparms), parms3, false)
				.getUpdateCount();

		final String cmd4 = "delete from testeeresponse where _fk_TestOpportunity in (select oppkey from ${oppsTbl})";
		int deletedCnt = executeStatement(connection,
				fixDataBaseNames(cmd4, unquotedparms), null, false)
				.getUpdateCount();

		final String cmd5 = "delete from testopportunity where _key in (select oppkey from ${oppsTbl})";
		deletedCnt = executeStatement(connection,
				fixDataBaseNames(cmd5, unquotedparms), null, false)
				.getUpdateCount();

		connection.dropTemporaryTable(oppsTbl);
	}

	@Override
	public SingleDataResultSet GetOpportunityStatus_SP(
			SQLConnection connection, UUID oppkey) throws ReturnStatusException {
		final String cmd1 = "select _efk_Testee, _efk_TestID, Opportunity, testeeName, status, clientname, "
				+ " _fk_Session, environment, maxItems, numItems, numResponses "
				+ " dateJoined, dateStarted, dateCompleted "
				+ "   from testopportunity  where _key = ${oppkey}";
		SqlParametersMaps parms1 = (new SqlParametersMaps()).put("oppkey",
				oppkey);
		SingleDataResultSet rs1 = executeStatement(connection, cmd1, parms1,
				true).getResultSets().next();
		return rs1;
	}

	@Override
	public int SIM_InsertTesteeAttribute_SP(SQLConnection connection,
			UUID testopp, String identifier, String attval, String context)
			throws ReturnStatusException {
		int cnt = 0;
				
		final String cmd1 = "select top 1 _fk_testopportunity from testeeattribute "
				+ " where _fk_TestOpportunity = ${testopp} and context = ${context} and TDS_ID = ${identifier}";
		SqlParametersMaps parms1 = (new SqlParametersMaps())
				.put("testopp", testopp).put("identifier", identifier)
				.put("context", context).put("attval", attval);
		if (exists(executeStatement(connection, cmd1, parms1, false))) {

			final String cmd2 = "update testeeattribute set attributeValue = ${attval}, _date = getdate()"
					+ " where _fk_TestOpportunity = ${testopp} and context = ${context} and TDS_ID = ${identifier}";
			SqlParametersMaps parms2 = parms1;
			cnt = executeStatement(connection, cmd2, parms2, false)
					.getUpdateCount();

		} else {
			final String cmd3 = "insert into testeeattribute (_fk_TestOpportunity, TDS_ID, attributeValue, context, _date) "
					+ " select ${testopp}, ${identifier}, ${attval}, ${context}, getdate()";
			SqlParametersMaps parms3 = parms1;
			cnt = executeStatement(connection, cmd3, parms3, false)
					.getUpdateCount();
		}
		return cnt;
	}

	@Override
	public void AA_UpdateAbilityEstimates_SP(SQLConnection connection,
			UUID oppkey, Integer itemPosition, String strand, Float theta,
			Float info, Float lambda) throws ReturnStatusException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean simGetSimAbort(SQLConnection connection, UUID session)
			throws ReturnStatusException {
		boolean abort = false;
		final String cmd1 = "select sim_abort from session where _key = ${session}";
		SqlParametersMaps parms1 = (new SqlParametersMaps()).put("session", session);
		SingleDataResultSet rs = executeStatement(connection, cmd1, parms1, false).getResultSets().next();
		DbResultRecord rec = (rs.getCount() > 0 ? rs.getRecords().next() : null);
		if (rec != null) {
			abort = rec.<Boolean> get("sim_abort");
		}
		return abort;
	}

	@Override
	public String sim_getItemType(SQLConnection connection, String sItemKey)
			throws ReturnStatusException {
		// TODO Auto-generated method stub
		return null;
	}
}
