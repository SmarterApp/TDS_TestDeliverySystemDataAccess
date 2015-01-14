/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.api;

import java.util.UUID;

import AIR.Common.DB.DataBaseTable;
import AIR.Common.DB.SQLConnection;
import AIR.Common.DB.results.MultiDataResultSet;
import AIR.Common.DB.results.SingleDataResultSet;
import TDS.Shared.Exceptions.ReturnStatusException;

public interface ISimDLL
{
  public SingleDataResultSet SIM_GetUserClients_SP (SQLConnection connection, String userId) throws ReturnStatusException;

  public SingleDataResultSet SIM_ValidateUser_SP (SQLConnection connection, String userId) throws ReturnStatusException;

  public SingleDataResultSet SIM_ValidateUser_SP (SQLConnection connection, String userId, String password) throws ReturnStatusException;

  public SingleDataResultSet SIM_CreateSession_SP (SQLConnection connection, String clientname, String userId, String sessionName) throws ReturnStatusException;

  public SingleDataResultSet SIM_CreateSession_SP (SQLConnection connection, String clientname, String userId, String sessionName,
      String language) throws ReturnStatusException;

  public SingleDataResultSet SIM_CreateSession_SP (SQLConnection connection, String clientname, String userId, String sessionName,
      String language, String sessionDescription, Integer sessiontype) throws ReturnStatusException;

  public SingleDataResultSet SIM_GetUserSessions_SP (SQLConnection connection, String userId) throws ReturnStatusException;

  public SingleDataResultSet SIM_GetUserSessions_SP (SQLConnection connection, String userId, String clientname) throws ReturnStatusException;

  public SingleDataResultSet SIM_CopySession2_SP (SQLConnection connection, UUID fromSession, String sessionName) throws ReturnStatusException;

  public SingleDataResultSet SIM_CopySession2_SP (SQLConnection connection, UUID fromSession, String sessionName, String sessionDescription, String toUser) throws ReturnStatusException;

  public SingleDataResultSet SIM_GetSessionTests2_SP (SQLConnection connection, UUID sessionKey) throws ReturnStatusException;

  public SingleDataResultSet SIM_GetSessionTests_SP (SQLConnection connection, UUID sessionKey) throws ReturnStatusException;

  public void SIM_SetSessionAbort_SP (SQLConnection connection, UUID session, Boolean abort) throws ReturnStatusException;

  public void SIM_SetSessionDescription_SP (SQLConnection connection, UUID session, String description) throws ReturnStatusException;

  public SingleDataResultSet SIM_AddSessionTest2_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException;

  public SingleDataResultSet SIM_AddSessionTest2_SP (SQLConnection connection, UUID session, String testkey,
      Integer iterations, Integer opportunities, Float meanProficiency,
      Float sdProficiency, Float strandCorrelation, String handScoreItemTypes) throws ReturnStatusException;

  public SingleDataResultSet SIM_AlterSegmentItemgroup_SP (SQLConnection connection, UUID session,
      String testkey, String segmentKey,
      String groupId, Integer maxItems) throws ReturnStatusException;

  public SingleDataResultSet SIM_AlterSegmentStrand2_SP (SQLConnection connection, UUID session,
      String testkey, String segmentKey,
      String strand, Integer minItems, Integer maxItems, Float bpweight,
      Boolean isStrictMax, Float startAbility, Float startInfo) throws ReturnStatusException;

  public SingleDataResultSet SIM_AlterSegmentStrand2_SP (SQLConnection connection, UUID session,
      String testkey, String segmentKey,
      String strand, Integer minItems, Integer maxItems, Float bpweight, Boolean isStrictMax,
      Float startAbility, Float startInfo,
      Float adaptiveCut, Float scalar,
      Float abilityWeight,
      Float precisionTargetNotMetWeight,
      Float precisionTargetMetWeight,
      Float precisionTarget) throws ReturnStatusException;

  public SingleDataResultSet SIM_AlterSegmentContentLevel_SP (SQLConnection connection, UUID session, String testkey, String segmentKey,
      String contentLevel, Integer minItems, Integer maxItems, Float bpweight, Boolean isStrictMax) throws ReturnStatusException;

  public MultiDataResultSet SIM_GetTestBlueprint2_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException;

  public MultiDataResultSet SIM_GetTestItems_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException;

  public MultiDataResultSet SIM_ReportBPSatisfaction_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException;

  public MultiDataResultSet SIM_ReportBPSatisfaction_SP (SQLConnection connection, UUID session, String testkey,
      Boolean reportBlueprint) throws ReturnStatusException;

  public MultiDataResultSet SIM_ValidateBlueprints_SP (SQLConnection connection, UUID session) throws ReturnStatusException;

  public SingleDataResultSet SIM_ClearSessionOpportunityData_SP (SQLConnection connection, UUID session) throws ReturnStatusException;

  public SingleDataResultSet SIM_ClearSessionOpportunityData_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException;

  public SingleDataResultSet SIM_DeleteTest_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException;

  public SingleDataResultSet SIM_AlterSessionTest2_SP (SQLConnection connection, UUID session, String testkey,
      Integer iterations, Integer opportunities,
      Float meanProficiency, Float sdProficiency, Float strandCorrelation) throws ReturnStatusException;

  public SingleDataResultSet SIM_AlterSessionTest2_SP (SQLConnection connection, UUID session, String testkey,
      Integer iterations, Integer opportunities,
      Float meanProficiency, Float sdProficiency, Float strandCorrelation, String handScoreItemTypes) throws ReturnStatusException;

  public SingleDataResultSet SIM_DeleteSession_SP (SQLConnection connection, UUID session) throws ReturnStatusException;

  public SingleDataResultSet SIM_AlterSegment2_SP (SQLConnection connection,
      UUID session, String testkey,
      String segmentKey, Float startAbility, Float startInfo,
      Integer minItems, Integer maxItems, Float bpweight,
      Integer cset1size, Integer cset2InitialRandom,
      Integer cset2Random, Float itemWeight,
      Float abilityOffset) throws ReturnStatusException;

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
      Boolean terminationFlagsAnd) throws ReturnStatusException;

  public SingleDataResultSet SIM_ReportOPItemDistribution_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException;

  public SingleDataResultSet SIM_ReportOPItemDistribution_SP (SQLConnection connection, UUID session, String testkey,
      Float distributionInterval) throws ReturnStatusException;

  public MultiDataResultSet SIM_ItemDistribution_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException;

  // note that default value for debug is true for this method
  public MultiDataResultSet SIM_ReportItems_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException;

  public MultiDataResultSet SIM_ReportItems_SP (SQLConnection connection, UUID session, String testkey, Boolean debug) throws ReturnStatusException;

  public SingleDataResultSet SIM_ReportRecycled_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException;

  public void _RecordSegmentSatisfaction_SP (SQLConnection connection, UUID oppkey) throws ReturnStatusException;

  public void __PatchContentCounts_SP (SQLConnection connection, UUID sessionKey, String testKey) throws ReturnStatusException;

  public void __PatchSegmentCounts_SP (SQLConnection connection, UUID sessionKey, String testKey) throws ReturnStatusException;

  public SingleDataResultSet SIM_AlterSegmentItem_SP (SQLConnection connection, UUID session, String testkey, String segmentKey, String itemKey,
      Boolean isActive, Boolean isRequired) throws ReturnStatusException;

  public SingleDataResultSet SIM_ReportSegmentSatisfaction_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException;

  public MultiDataResultSet SIM_ReportBPSummary_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException;

  public Float _SIM_ComputeCoverage05_FN (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException;

  public DataBaseTable _SIM_ComputeBias_FN (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException;

  public DataBaseTable _SIM_Init2Firstb_Correlation_FN (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException;

  public MultiDataResultSet SIM_ReportSummaryStats_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException;

  public MultiDataResultSet SIM_ReportScores_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException;

  public MultiDataResultSet SIM_ReportFormDistributions_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException;

  public MultiDataResultSet SIM_FieldtestDistribution_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException;

  public SingleDataResultSet SIM_ChangeSegmentNonReportingCategoryAsReportingCategory_SP (SQLConnection connection, UUID session,
      String testkey, String segmentKey, String contentLevel) throws ReturnStatusException;

  public SingleDataResultSet SIM_ChangeSegmentReportingCategoryAsNonReportingCategory_SP (SQLConnection connection, UUID session,
      String testkey, String segmentKey, String strand) throws ReturnStatusException;

  public SingleDataResultSet SIM_GetErrors_SP (SQLConnection connection, UUID session) throws ReturnStatusException;

  public MultiDataResultSet SIM_ReportOpportunities_SP (SQLConnection connection, UUID session, String testKey) throws ReturnStatusException;

  public Integer sim_setSimulationRunProps (SQLConnection connection, UUID session, String status) throws ReturnStatusException;

  public Integer sim_setSimulationErrorProps (SQLConnection connection, UUID session, String status) throws ReturnStatusException;

  public String sim_GetSimStatus (SQLConnection connection, UUID session) throws ReturnStatusException;

  public MultiDataResultSet SIM_GetTestControls2_SP (SQLConnection connection, UUID session, String testkey) throws ReturnStatusException;

  public SingleDataResultSet sim_getItemTypes (SQLConnection connection, String adminSuject) throws ReturnStatusException;

  public void SIM_LogError_SP (SQLConnection connection, UUID session, String clientname,
      String procname, String oppkey, String testkey, String msg) throws ReturnStatusException;

  public void SIM_LogSessionError_SP (SQLConnection connection, UUID session, String clientname,
      String procname, String testkey, String msg) throws ReturnStatusException;

  public SingleDataResultSet SIM_OpenSession_SP (SQLConnection connection, UUID sessionkey) throws ReturnStatusException;

  public void SIM_EndSimulation (SQLConnection connection, UUID session, String status) throws ReturnStatusException;

  public void SIM_CleanupSessionTest (SQLConnection connection, UUID session, String testKey) throws ReturnStatusException;

  public SingleDataResultSet GetOpportunityStatus_SP (SQLConnection connection, UUID oppkey) throws ReturnStatusException;

  public int SIM_InsertTesteeAttribute_SP (SQLConnection connection, UUID testopp, String identifier,
      String attval, String context) throws ReturnStatusException;
  
  public void AA_UpdateAbilityEstimates_SP (SQLConnection connection, UUID oppkey, Integer itemPosition,
      String strand, Float theta, Float info, Float lambda) throws ReturnStatusException;
  
  public boolean simGetSimAbort (SQLConnection connection, UUID session) throws ReturnStatusException;
  
  public String sim_getItemType (SQLConnection connection, String sItemKey)throws ReturnStatusException;  
  
  public SingleDataResultSet SIM_GetItemSelectionParameters (SQLConnection connection, UUID sessionKey, String testKey) throws ReturnStatusException;

  public void SIM_AlterItemSelectionParameter (SQLConnection connection, UUID sessionKey, String testKey, String bpElementID, String paramName, String paramValue) throws ReturnStatusException;

  public void SIM_DeleteAllItemSelectionParameterDefaultRecords (SQLConnection connection) throws ReturnStatusException;

  public void SIM_AddItemSelectionParameterDefaultRecord (SQLConnection connection, String algorithmType, String entityType, String name, String value, String label) throws ReturnStatusException;
}
