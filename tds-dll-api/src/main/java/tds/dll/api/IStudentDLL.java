/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.api;

import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import AIR.Common.DB.DataBaseTable;
import AIR.Common.DB.SQLConnection;
import AIR.Common.DB.results.MultiDataResultSet;
import AIR.Common.DB.results.SingleDataResultSet;
import AIR.Common.Helpers._Ref;
import TDS.Shared.Exceptions.ReturnStatusException;

public interface IStudentDLL
{

  /**
   * @param connection
   * @param clientName
   * @param bankKey
   * @param itemKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet IB_GetItemPath_SP (SQLConnection connection, String clientName, long bankKey, long itemKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @param bankKey
   * @param stimulusKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet IB_GetStimulusPath_SP (SQLConnection connection, String clientName, long bankKey, long stimulusKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @param stimulusKey
   * @return
   * @throws ReturnStatusException
   */
  public String ClientStimulusFile_FN (SQLConnection connection, String clientName, String stimulusKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @param appName
   * @return
   * @throws ReturnStatusException
   */

  public SingleDataResultSet T_GetNetworkDiagnostics_SP (SQLConnection connection, String clientName, String appName) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientname
   * @param OS_ID
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_GetVoicePacks_SP (SQLConnection connection, String clientname, String OS_ID) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientname
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_GetVoicePacks_SP (SQLConnection connection, String clientname) throws ReturnStatusException;

  /**
   * @param connection
   * @param sessionKey
   * @param oppKey
   * @param itempage
   * @param itemposition
   * @param requestType
   * @param requestValue
   * @param requestParameters
   * @param requestDescription
   * @throws ReturnStatusException
   */
  public void T_SubmitRequest_SP (SQLConnection connection, UUID sessionKey, UUID oppKey, Integer itempage, Integer itemposition, String requestType, String requestValue,
      String requestParameters, String requestDescription) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientname
   * @return
   * @throws ReturnStatusException
   */
  public MultiDataResultSet GetForbiddenApps_SP (SQLConnection connection, String clientname) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   */

  public DataBaseTable ListClientTests_FN (SQLConnection connection, String clientName, Integer sessionType) throws ReturnStatusException;

  public Boolean ValidateCompleteness_FN (SQLConnection connection, String testKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   * @throws SQLException
   */
  public SingleDataResultSet IB_ListTests_SP (SQLConnection connection, String clientName, int sessionType) throws ReturnStatusException;

  
  /**
   * Udaya: In the original stored procedure, please note that there is a
   * parameter called "@environment" in the T_GetApplicationSettings.. This
   * parameter has been Deprecated and replaced with value in _externs table. I
   * confirmed with sai on this and i'm not passing this parameter in our java
   * side
   * 
   * @param connection
   * @param clientName
   * @param appName
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_GetApplicationSettings_SP (SQLConnection connection, String clientName, String appName) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @param context
   * @return
   * @throws ReturnStatusException
   */
  public MultiDataResultSet IB_GlobalAccommodations_SP (SQLConnection connection, String clientName, String context) throws ReturnStatusException;

  public int CompletenessValid_FN (SQLConnection connection, String testkey, String scorestring, Character rowdelim, Character coldelim) throws ReturnStatusException;

  public Integer T_ValidateCompleteness_SP (SQLConnection connection, UUID oppKey, String scoresString) throws ReturnStatusException;

  public Integer T_ValidateCompleteness_SP (SQLConnection connection, UUID oppKey, String scoresString, Character rowdelim, Character coldelim) throws ReturnStatusException;

  /**
   * @param SQLConnection
   *          the database connection
   * @param oppkey
   *          the test opportunity UUID
   * @param session
   *          the session UUID
   * @param browserID
   *          the browser UUID
   * 
   * @return SingleDataResultSet containing the segments for the specified Test
   *         Opportunity.
   * 
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_GetOpportunityComment_SP (SQLConnection connection, UUID oppkey, String context) throws ReturnStatusException;

  public SingleDataResultSet T_GetOpportunityComment_SP (SQLConnection connection, UUID oppkey, String context, Integer itemposition) throws ReturnStatusException;

  public SingleDataResultSet T_GetOpportunityItems_SP (SQLConnection connection, UUID oppKey) throws ReturnStatusException;

  public MultiDataResultSet IB_GetTestProperties_SP (SQLConnection connection, String testKey) throws ReturnStatusException;

  public void _ValidateTesteeAccessProc_SP (SQLConnection connection, UUID testoppkey, UUID session, UUID browserId, Boolean checkSession, _Ref<String> message) throws ReturnStatusException;

  public Integer AuditLatencies_FN (SQLConnection connection, String clientname) throws ReturnStatusException;

  public int T_RecordServerLatency_SP (SQLConnection connection, String operation, UUID oppkey, UUID session,
      UUID browser, Integer serverLatency) throws ReturnStatusException;

  public int T_RecordServerLatency_SP (SQLConnection connection, String operation, UUID oppkey, UUID session,
      UUID browser, Integer serverLatency, Integer dblatency, String pageList, String itemList) throws ReturnStatusException;

  public void T_RecordClientLatency_SP (SQLConnection connection, UUID oppkey, UUID session, UUID browser, int itempage, int numitems, int visitCount,
      Date createDate, Date loadDate, int loadTime, int requestTime, int visitTime, Integer loadAttempts, Date visitDate, String toolsUsed) throws ReturnStatusException;

  public void T_RecordClientLatency_XML_SP (SQLConnection connection, UUID oppkey, UUID session, UUID browser, String latencies) throws ReturnStatusException;

  public SingleDataResultSet IB_GetTestGrades_SP (SQLConnection connection, String clientname, String testkey) throws ReturnStatusException;

  public SingleDataResultSet IB_GetTestGrades_SP (SQLConnection connection, String clientname, String testkey, Integer sessionType) throws ReturnStatusException;

  public SingleDataResultSet T_ValidateAccess_SP (SQLConnection connection, UUID oppkey, UUID session, UUID browserId) throws ReturnStatusException;

  public void T_RecordToolsUsed_SP (SQLConnection connection, UUID oppkey, String toolsused) throws ReturnStatusException;

  public void T_RecordComment_SP (SQLConnection connection, UUID sessionKey, Long testee, String comment) throws ReturnStatusException;

  public void T_RecordComment_SP (SQLConnection connection, UUID sessionKey, Long testee, String comment, String context, UUID oppkey) throws ReturnStatusException;

  public void T_RecordComment_SP (SQLConnection connection, UUID sessionKey, Long testee, String comment, String context, UUID oppkey, Integer itemposition) throws ReturnStatusException;

  /**
   * This method returns null in case of success
   * 
   * @param connection
   * @param oppkey
   * @param itemId
   * @param position
   * @param dateCreated
   * @param session
   * @param browser
   * @return SingleDataResultSet
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_RemoveResponse_SP (SQLConnection connection, UUID oppkey, String itemId, Integer position, String dateCreated, UUID session, UUID browser) throws ReturnStatusException;

  /**
   * Original stored proc has 'mark'parameter as integer, however lark column in
   * testeeresponse table has 'bit'type, we changed 'mark' parameter type to
   * Boolean to clarify its valid values.
   * 
   * @param connection
   * @param oppkey
   * @param session
   * @param browserId
   * @param position
   * @param mark
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_SetItemMark_SP (SQLConnection connection, UUID oppkey, UUID session, UUID browserId, Integer position, Boolean mark) throws ReturnStatusException;

  /**
   * This method will always return non-null SingleDataResultSet because we call
   * SetOpportunityStatus with suppressReport parameter set to false.
   * 
   * @param connection
   * @param oppkey
   * @param status
   * @param sessionKey
   * @param browserKey
   * @param comment
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_SetOpportunityStatus_SP (SQLConnection connection, UUID oppkey, String status, UUID sessionKey, UUID browserKey, String comment) throws ReturnStatusException;

  /**
   * @param connection
   * @param oppKey
   * @param sessionKey
   * @param browserKey
   * @param segment
   * @param entry
   * @param exit
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_WaitForSegment_SP (SQLConnection connection, UUID oppKey, UUID sessionKey, UUID browserKey, Integer segment, Boolean entry, Boolean exit) throws ReturnStatusException;

  /**
   * @param connection
   * @param oppKey
   * @param session
   * @param browserId
   * @param itemId
   * @param page
   * @param position
   * @param dateCreated
   * @param responseSequence
   * @param score
   * @param response
   * @param isSelected
   * @param isValid
   * @param scoreLatency
   * @param scoreStatus
   * @param scoreRationale
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_UpdateScoredResponse_SP (SQLConnection connection, UUID oppKey, UUID session, UUID browserId, String itemId, Integer page, Integer position, String dateCreated,
      Integer responseSequence, Integer score, String response, Boolean isSelected, Boolean isValid, Integer scoreLatency, String scoreStatus, String scoreRationale) throws ReturnStatusException;

  /**
   * please note that i'm renaming the variables which are in ' '(single quotes)
   * as named parameters (${ }) because in future when we move to MySql
   * database, we are not sure if the syntax will be same as in SQL Server.
   * 
   * @param connection
   * @param clientName
   * @return
   * @throws ReturnStatusException
   */
  public Integer AuditResponses_FN (SQLConnection connection, String clientName) throws ReturnStatusException;

  /**
   * @param connection
   * @param testKey
   * @return
   * @throws ReturnStatusException
   */
  public Float GetInitialAbility_FN (SQLConnection connection, String testKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param oppKey
   * @return
   * @throws ReturnStatusException
   */
  public void _GetInitialAbility_SP (SQLConnection connection, UUID oppKey, _Ref<Float> ability) throws ReturnStatusException;

  public void _CreateResponseSet_SP (SQLConnection connection, UUID testoppKey, Integer maxitems, Integer reset) throws ReturnStatusException;

  /**
   * @param connection
   * @param oppKey
   * @param segmentKey
   * @return
   * @throws ReturnStatusException
   */
  public String _AA_ItempoolString_FN (SQLConnection connection, UUID oppKey, String segmentKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @param oppKey
   * @param segmentKey
   * @param testId
   * @param fieldTest
   * @return
   * @throws ReturnStatusException
   */
  public DataBaseTable _AA_TestoppItempool_FN (SQLConnection connection, String clientName, UUID oppKey, String segmentKey, String testId, Boolean fieldTest)
      throws ReturnStatusException;

  /**
   * @param connection
   * @param oppKey
   * @param segmentKey
   * @param testlen
   * @param poolcount
   * @param poolString
   * @param debug
   * @param sessionKey
   * @throws ReturnStatusException
   */
  public MultiDataResultSet _ComputeSegmentPool_SP (SQLConnection connection, UUID oppKey, String segmentKey, _Ref<Integer> testlen, _Ref<Integer> poolcount, _Ref<String> poolString, UUID sessionKey)
      throws ReturnStatusException;

  public MultiDataResultSet _ComputeSegmentPool_SP (SQLConnection connection, UUID oppKey, String segmentKey, _Ref<Integer> testlen, _Ref<Integer> poolcount, _Ref<String> poolString, Boolean debug,
      UUID sessionKey)
      throws ReturnStatusException;

  /**
   * @param connection
   * @param oppKey
   * @return
   * @throws ReturnStatusException
   */
  public Boolean IsSimulation_FN (SQLConnection connection, UUID oppKey) throws ReturnStatusException;

  /**
   * Need to address the Create Index, when migrating to MY_SQL
   * 
   * @param connection
   * @param testOppKey
   * @param testKey
   * @param segment
   * @param segmentId
   * @param language
   * @param ftCount
   * @param debug
   * @param noinsert
   * @throws ReturnStatusException
   */
  public MultiDataResultSet _FT_SelectItemgroups_SP (SQLConnection connection, UUID testOppKey, String testKey, Integer segment, String segmentId, String language, _Ref<Integer> ftCount)
      throws ReturnStatusException;

  public MultiDataResultSet _FT_SelectItemgroups_SP (SQLConnection connection, UUID testOppKey, String testKey, Integer segment, String segmentId, String language, _Ref<Integer> ftCount,
      Integer debug, Integer noinsert) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @param oppKey
   * @param segmentKey
   * @param testId
   * @param fieldTest
   * @param session
   * @return
   * @throws ReturnStatusException
   */
  public DataBaseTable _AA_SIM_TestoppItempool_FN (SQLConnection connection, String clientName, UUID oppKey, String segmentKey, String testId, Boolean fieldTest, UUID session)
      throws ReturnStatusException;

  /**
   * Need to address the Create Index, when migrating to MY_SQL
   * 
   * @param connection
   * @param oppKey
   * @param testKey
   * @param debug
   * @throws ReturnStatusException
   */

  public SingleDataResultSet _FT_Prioritize_2012_SP (SQLConnection connection, UUID oppKey, String testKey) throws ReturnStatusException;

  public SingleDataResultSet _FT_Prioritize_2012_SP (SQLConnection connection, UUID oppKey, String testKey, Integer debug) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientname
   * @param testId
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   */

  public DataBaseTable GetTestFormWindows_FN (SQLConnection connection, String clientname, String testId, Integer sessionType) throws ReturnStatusException;

  public SingleDataResultSet _GetTesteeTestForms_SP (SQLConnection connection, String clientname, String testId, Long testee, Integer sessionType) throws ReturnStatusException;

  public SingleDataResultSet _GetTesteeTestForms_SP (SQLConnection connection, String clientname, String testId, Long testee, Integer sessionType,
      String formList) throws ReturnStatusException;

  public void _SelectTestForm_Predetermined_SP (SQLConnection connection, UUID oppkey, String testkey, String lang,
      _Ref<String> formkeyRef, _Ref<String> formIdRef, _Ref<Integer> itemcnt, Integer sessionType) throws ReturnStatusException;

  public void _SelectTestForm_Predetermined_SP (SQLConnection connection, UUID oppkey, String testkey, String lang,
      _Ref<String> formkeyRef, _Ref<String> formIdRef, _Ref<Integer> itemcntRef, Integer sessionType, String formList) throws ReturnStatusException;

  public void _SelectTestForm_EqDist_SP (SQLConnection connection, UUID oppkey, String testkey, String lang,
      _Ref<String> formkeyRef, _Ref<String> formIdRef, _Ref<Integer> formlengthRef) throws ReturnStatusException;

  /**
   * when migrating to MySql newid() MsSQL function needs to be substituted by
   * RAND()
   * 
   * @param connection
   * @param oppkey
   * @param testkey
   * @param lang
   * @param formkeyRef
   * @param formIdRef
   * @param formlengthRef
   * @param formCohort
   * @throws ReturnStatusException
   */
  public void _SelectTestForm_EqDist_SP (SQLConnection connection, UUID oppkey, String testkey, String lang,
      _Ref<String> formkeyRef, _Ref<String> formIdRef, _Ref<Integer> formlengthRef, String formCohort) throws ReturnStatusException;

  public void _SelectTestForm_Driver_SP (SQLConnection connection, UUID oppkey, String testkey, String lang,
      _Ref<String> formkeyRef, _Ref<String> formIdRef, _Ref<Integer> formlengthRef) throws ReturnStatusException;

  public void _SelectTestForm_Driver_SP (SQLConnection connection, UUID oppkey, String testkey, String lang,
      _Ref<String> formkeyRef, _Ref<String> formIdRef, _Ref<Integer> formlengthRef, String formList, String formCohort) throws ReturnStatusException;

  /**
   * @param connection
   * @param oppKey
   * @param testKey
   * @param parentKey
   * @param language
   * @return
   * @throws ReturnStatusException
   */
  public Integer FT_IsEligible_FN (SQLConnection connection, UUID oppKey, String testKey, String parentKey, String language) throws ReturnStatusException;

  /**
   * @param connection
   * @param oppKey
   * @return
   * @throws ReturnStatusException
   */
  public String GetOpportunityLanguage_FN (SQLConnection connection, UUID oppKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param oppKey
   * @param error
   * @param formKeyList
   * @param debug
   * @throws ReturnStatusException
   */
  public MultiDataResultSet _InitializeTestSegments_SP (SQLConnection connection, UUID oppKey, _Ref<String> error, String formKeyList) throws ReturnStatusException;

  public MultiDataResultSet _InitializeTestSegments_SP (SQLConnection connection, UUID oppKey, _Ref<String> error, String formKeyList, Boolean debug) throws ReturnStatusException;

  /**
   * @param connection
   * @param oppKey
   * @param testLength
   * @param reason
   * @param formKeyList
   * @throws ReturnStatusException
   */
  public void _InitializeOpportunity_SP (SQLConnection connection, UUID oppKey, _Ref<Integer> testLength, _Ref<String> reason, String formKeyList) throws ReturnStatusException;

  /**
   * @param connection
   * @param oppKey
   * @param segment
   * @return
   * @throws ReturnStatusException
   */
  public Boolean _AA_IsSegmentSatisfied_FN (SQLConnection connection, UUID oppKey, Integer segment) throws ReturnStatusException;

  /**
   * @param connection
   * @param oppKey
   * @param debug
   * @throws ReturnStatusException
   */
  public MultiDataResultSet _RemoveUnanswered_SP (SQLConnection connection, UUID oppKey) throws ReturnStatusException;

  public MultiDataResultSet _RemoveUnanswered_SP (SQLConnection connection, UUID oppKey, Boolean debug) throws ReturnStatusException;

  /**
   * @param connection
   * @param oppKey
   * @return
   * @throws ReturnStatusException
   */
  public Date _TestOppLastActivity_FN (SQLConnection connection, UUID oppKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param oppKey
   * @param newRestart
   * @param doUpdate
   * @throws ReturnStatusException
   */
  public SingleDataResultSet _UnfinishedResponsePages_SP (SQLConnection connection, UUID oppKey, Integer newRestart, Boolean doUpdate) throws ReturnStatusException;

  /**
   * @param connection
   * @param oppKey
   * @param restart
   * @return
   * @throws ReturnStatusException
   */
  public Integer ResumeItemPosition_FN (SQLConnection connection, UUID oppKey, Integer restart) throws ReturnStatusException;

  /**
   * @param connection
   * @param oppKey
   * @param sessionKey
   * @param browserId
   * @param formKeyList
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_StartTestOpportunity_SP (SQLConnection connection, UUID oppKey, UUID sessionKey, UUID browserId, String formKeyList) throws ReturnStatusException;

  public boolean _AllowAnonymousTestee_FN (SQLConnection connection, String clientname) throws ReturnStatusException;

  public void _CanOpenExistingOpportunity_SP (SQLConnection connection, String client, Long testee, String testID, UUID sessionID, Integer maxOpportunities,
      _Ref<Integer> numberRef, _Ref<String> msgRef) throws ReturnStatusException;

  public void _CanOpenNewOpportunity_SP (SQLConnection connection, String client, Long testee, String testID, Integer maxOpportunities, Integer delayDays,
      _Ref<Integer> numberRef, _Ref<String> msgRef, UUID session) throws ReturnStatusException;

  public SingleDataResultSet _GetTesteeTestModes_SP (SQLConnection connection, String clientname, String testID, Long testee, Integer sessionType) throws ReturnStatusException;

  public SingleDataResultSet _GetTesteeTestModes_SP (SQLConnection connection, String clientname, String testID, Long testee, Integer sessionType,
      String modeList) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientname
   * @param testID
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   */

  public DataBaseTable GetCurrentTestWindows_FN (SQLConnection connection, String clientname, String testID, Integer sessionType) throws ReturnStatusException;

  public SingleDataResultSet _GetTesteeTestWindows_SP (SQLConnection connection, String clientname, String testID, Long testee, Integer sessionType) throws ReturnStatusException;

  public SingleDataResultSet _GetTesteeTestWindows_SP (SQLConnection connection, String clientname, String testID, Long testee, Integer sessionType,
      String windowList, String formList) throws ReturnStatusException;

  public void _IsOpportunityBlocked_SP (SQLConnection connection, String clientname, Long testee, String testID, Integer maxopps, _Ref<String> reasonBlockedRef) throws ReturnStatusException;

  public void _IsOpportunityBlocked_SP (SQLConnection connection, String clientname, Long testee, String testID, Integer maxopps, _Ref<String> reasonBlockedRef, Integer sessionType)
      throws ReturnStatusException;

  public void _CanOpenTestOpportunity_SP (SQLConnection connection, String clientname, Long testee, String testkey, UUID sessionId,
      Integer maxOpportunities, _Ref<Boolean> newRef, _Ref<Integer> numberRef, _Ref<String> reasonRef) throws ReturnStatusException;

  public SingleDataResultSet _InitOpportunityAccommodations_SP (SQLConnection connection, UUID oppkey, String accoms) throws ReturnStatusException;

  /**
   * Open an existing test opportunity for testing. Main purpose of this
   * function is to apply business rules. -- Assumptions: -- the given
   * opportunity exists for opening (and has not expired), as verified by
   * _CanOpenTestOpportunity -- the session is open for business, as verified by
   * _CanOpenTestOpportunity
   * 
   * @param connection
   * @param clientname
   * @param testee
   * @param testkey
   * @param opportunity
   * @param sessionId
   * @param browserId
   * @param newstatusRef
   * @param accommodations
   * @param restoreRTS
   * @param testoppkeyRef
   * @throws ReturnStatusException
   */
  public void _OpenExistingOpportunity_SP (SQLConnection connection, String clientname, Long testee, String testkey, Integer opportunity,
      UUID sessionId, UUID browserId,
      _Ref<String> newstatusRef, String accommodations, Boolean restoreRTS, _Ref<UUID> testoppkeyRef) throws ReturnStatusException;

  public void _CreateClientReportingID_SP (SQLConnection connection, String clientname, UUID oppkey, _Ref<Long> newIdRef) throws ReturnStatusException;

  public String TestSubject_FN (SQLConnection connection, String testkey) throws ReturnStatusException;

  public void _GetTestParms_SP (SQLConnection connection, String clientname, String testkey,
      _Ref<String> subjectRef, _Ref<String> testIdRef, _Ref<Boolean> segmentedRef, _Ref<String> algorithmRef) throws ReturnStatusException;

  /**
   * Open a new test opportunity for testing. Main purpose of this function is
   * to apply business rules. -- Assumptions: -- the given opportunity does not
   * exist and is within testing limits, as verified by _CanOpenTestOpportunity
   * -- the session is open for business, as verified by _CanOpenTestOpportunity
   * 
   * @param connection
   * @param clientname
   * @param testee
   * @param testkey
   * @param opportunity
   * @param sessionKey
   * @param browserKey
   * @param testeeID
   * @param testeeName
   * @param status
   * @param guestAccommodations
   * @param testoppkeyRef
   * @throws ReturnStatusException
   */
  public SingleDataResultSet _OpenNewOpportunity_SP (SQLConnection connection, String clientname, Long testee, String testkey, Integer opportunity,
      UUID sessionKey, UUID browserKey, String testeeID, String testeeName, String status, String guestAccommodations, _Ref<UUID> testoppkeyRef)
      throws ReturnStatusException;

  public boolean _AllowProctorlessSessions_FN (SQLConnection connection, String clientname) throws ReturnStatusException;

  public int IsSessionOpen_FN (SQLConnection connection, UUID sessionKey) throws ReturnStatusException;

  public boolean _RestoreRTSAccommodations_FN (SQLConnection connection, String clientname) throws ReturnStatusException;

  public SingleDataResultSet T_OpenTestOpportunity_SP (SQLConnection connection, Long testee, String testkey, UUID sessionKey, UUID browserKey) throws ReturnStatusException;

  public SingleDataResultSet T_OpenTestOpportunity_SP (SQLConnection connection, Long testee, String testkey, UUID sessionKey, UUID browserKey,
      String guestAccommodations) throws ReturnStatusException;

  /**
   * Discussed with sai about the guestID and guestName as they are not used
   * anywhere in the stored procedure. Commenting the unused code here String
   * guestId = ""; String guestName = ""; if(StringUtils.lastIndexOf
   * (clientName, "_PT") == 3){ guestId = "GUEST"; guestName = "GUEST"; }
   * 
   * @param connection
   * @param clientName
   * @return
   * @throws ReturnStatusException
   */

  public SingleDataResultSet T_TesteeAttributeMetadata_SP (SQLConnection connection, String clientName) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @return
   * @throws ReturnStatusException
   */

  public Integer _ActiveOpps_FN (SQLConnection connection, String clientName) throws ReturnStatusException;

  public SingleDataResultSet P_OpenSession_SP (SQLConnection connection, UUID sessionKey) throws ReturnStatusException;

  public SingleDataResultSet P_OpenSession_SP (SQLConnection connection, UUID sessionKey, Integer numhours, Boolean suppressReport, UUID browserKey)
      throws ReturnStatusException;

  /**
   * 
   * @param connection
   * @param clientname
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   */
  public DataBaseTable GetActiveTests_FN (SQLConnection connection, String clientname, Integer sessionType) throws ReturnStatusException;

  public void _SetupProctorlessSession_SP (SQLConnection connection, String clientname, _Ref<UUID> sessionKeyRef, _Ref<String> sessionIdRef) throws ReturnStatusException;

  public void _T_ValidateTesteeLogin_SP (SQLConnection connection, String clientname, String testeeId, String sessionId,
      _Ref<String> reasonRef, _Ref<Long> testeeKeyRef) throws ReturnStatusException;

  public MultiDataResultSet T_Login_SP (SQLConnection connection, String clientname, Map<String, String> keyValues) throws ReturnStatusException;

  public MultiDataResultSet T_Login_SP (SQLConnection connection, String clientname, Map<String, String> keyValues, String sessionId) throws ReturnStatusException;

  /**
   * @param connection
   * @param oppKey
   * @param page
   * @param segment
   * @param segmentId
   * @param groupId
   * @param msg
   * @return
   * @throws ReturnStatusException
   */
  public void _ValidateItemInsert_SP (SQLConnection connection, UUID oppKey, Integer page, Integer segment, String segmentId, String groupId, _Ref<String> msg) throws ReturnStatusException;

  /**
   * @param connection
   * @param oppKey
   * @param sessionKey
   * @param browserId
   * @param segment
   * @param segmentId
   * @param page
   * @param groupId
   * @param itemKeys
   * @param delimiter
   * @param groupItemsRequired
   * @param groupB
   * @param debug
   * @param noinsert
   * @return
   * @throws ReturnStatusException
   */
  public MultiDataResultSet T_InsertItems_SP (SQLConnection connection, UUID oppKey, UUID sessionKey, UUID browserId, Integer segment, String segmentId, Integer page, String groupId,
      String itemKeys, Character delimiter, Integer groupItemsRequired, Float groupB, Integer debug, Boolean noinsert) throws ReturnStatusException;

  /**
   * @param connection
   * @param testKey
   * @param groupId
   * @param language
   * @param formkey
   * @return
   * @throws ReturnStatusException
   */
  public DataBaseTable ITEMBANK_TestItemGroupData_FN (SQLConnection connection, String testKey, String groupId, String language, String formkey) throws ReturnStatusException;

  public String SIM_MakeItemscoreString_FN (SQLConnection connection, UUID oppkey) throws ReturnStatusException;

  /**
   * -- This function creates an item string suitable for submission to either
   * the adaptive algorithm or to the scoring algorithm -- These strings are
   * stored with all completed, invalidated, and reset test opportunities in the
   * 'itemstring' field. -- Since the text of the response is not part of this
   * computation, there is no concern over the use of special characters in --
   * the delimiter set. -- Special version of MakeItemscoreString that
   * interpolates dimension scores on items ONLY for the purposes of test
   * simulation
   * 
   * @param connection
   * @param oppkey
   * @param rowdelim
   * @param coldelim
   * @return
   * @throws ReturnStatusException
   */
  // TODO * This method need to be addressed when migrating to MySQL:
  // row_number()
  // over(order by...) funcionality
  public String SIM_MakeItemscoreString_FN (SQLConnection connection, UUID oppkey, Character rowdelim, Character coldelim) throws ReturnStatusException;

  public String MakeItemscoreString_FN (SQLConnection connection, UUID oppkey) throws ReturnStatusException;

  /**
   * -- This function creates an item string suitable for submission to either
   * the adaptive algorithm or to the scoring algorithm -- These strings are
   * stored with all completed, invalidated, and reset test opportunities in the
   * 'itemstring' field. -- Since the text of the response is not part of this
   * computation, there is no concern over the use of special characters in --
   * the delimiter set. -- Note that items are separated from each other by the
   * CHAR(13), and fields within an item are separated by the @delim character.
   * -- Returns '' score when the item is not selected. This mod is to conform
   * to DE ALT overloading of meaning to item score
   * 
   * @param connection
   * @param oppkey
   * @param rowdelim
   * @param coldelim
   * @return
   * @throws ReturnStatusException
   */
  public String MakeItemscoreString_FN (SQLConnection connection, UUID oppkey, Character rowdelim, Character coldelim) throws ReturnStatusException;

  public SingleDataResultSet T_GetTestforScoring_SP (SQLConnection connection, UUID oppkey) throws ReturnStatusException;

  public SingleDataResultSet T_GetTestforScoring_SP (SQLConnection connection, UUID oppkey, Character rowdelim, Character coldelim) throws ReturnStatusException;

  public SingleDataResultSet T_GetTestforCompleteness_SP (SQLConnection connection, UUID oppkey) throws ReturnStatusException;

  public SingleDataResultSet T_GetTestforCompleteness_SP (SQLConnection connection, UUID oppkey, Character rowdelim, Character coldelim) throws ReturnStatusException;

  public SingleDataResultSet T_GetResponseRationales_SP (SQLConnection connection, UUID oppkey, UUID sessionKey, UUID browserKey) throws ReturnStatusException;

  public SingleDataResultSet T_GetPTSetup_SP (SQLConnection connection, String clientname) throws ReturnStatusException;

  /**
   * -- This procedure mainly used for setting scores for constructed response
   * items by asynchronous item scorer -- -- Updated 10/2009 to required the
   * itemkey and the response sequence to prevent delayed scores for obsolete
   * item responses
   * 
   * -- 3/2011: Updated for _fk_testOpportunity and clientname -- 3/2012: Moves
   * reliance on testeeresponse index to testeeresponsescore table
   * 
   * @param SQLConnection
   *          the database connection
   * @param oppkey
   *          the test opportunity UUID
   * @param itemKey
   *          the item key UUID
   * @param position
   * @param sequence
   * @param score
   * @param scorestatus
   * @param scoreRationale
   * @param scoremark
   * 
   * @throws ReturnStatusException
   * @throws SQLException
   */
  public SingleDataResultSet S_UpdateItemScore_SP (SQLConnection connection, UUID oppkey, Long itemKey, Integer position, Integer sequence, Integer score, String scorestatus,
	      String scoreRationale,
	      UUID scoremark)
	      throws ReturnStatusException;

  public SingleDataResultSet S_UpdateItemScore_SP (SQLConnection connection, UUID oppkey, Long itemKey, Integer position, Integer sequence, Integer score, String scorestatus,
	      String scoreRationale, UUID scoremark, String scoreDimensions)
	      throws ReturnStatusException;

  /**
   * Integrity check: Make sure that testee opportunity attributes match those
   * in table testopportunity Unlike GetItems and InsertItems, there is no
   * provision for suppressing the test status.
   * 
   * @note Related to ValidateProctorSession_FN and _ValidateTesteeAccessProc_SP
   * 
   * @param SQLConnection
   *          the database connection
   * @param testopp
   *          the test opportunity UUID
   * @param session
   *          the session UUID
   * @param browserID
   *          the browser UUID
   * 
   * @return String containing status message if error or null if ok.
   * 
   * @throws ReturnStatusException
   */
  public String _ValidateItemsAccess_FN (SQLConnection connection, UUID testopp, UUID session, UUID browserID) throws ReturnStatusException;

  /**
   * -- returns a recordset of the opportunity's segments suitable for passing
   * to the C# item selection algorithm -- the structure of the recordset
   * parallels that of the C# class : -- string segmentID, int position, string
   * formKey, string ftItems
   * 
   * returns segmentID, SegmentPosition as position, formKey, formID, algorithm,
   * ftItems, isPermeable, restorePermOn Most of these values are passed to the
   * C# item selection algorithm via the TestSegment struct, one for each record
   * returned by the procedure.
   * 
   * 2 values must be operationalized by the student app: isPermeable and
   * restorePermOn. See the discussion of these above. These parameters need to
   * be available to the client-side code.
   * 
   * It is not expected that the remainder of these values change during a
   * sitting. The student app should not hold these parameters client-side.
   * ftItems is the actual string of item keys and positions that have been
   * pre-allocated to the test. In the case of independent field tests, this
   * constitutes every item on the entire test. Not only is this a burden on
   * network transmissions, but it is also a security risk.
   * 
   * -- restorePermOn is one of -- 'segment' : restore the default when the
   * testee exits the segment (so he/she is allowed one visit into the segment.
   * Behaves most like the natural manner) -- The default will be restored on
   * pause whether or not the testee entered it. -- 'paused' : restore the
   * default when the testee pauses the test. If testee does not visit the
   * segment, it doesn't matter. -- 'completed' : the default is never restored
   * 
   * @note Related to T_RemoveResponse_SP and T_GetOpportunityComment_SP
   * 
   * @param SQLConnection
   *          the database connection
   * @param oppkey
   *          the test opportunity UUID
   * @param session
   *          the session UUID
   * @param browserID
   *          the browser UUID
   * 
   * @return SingleDataResultSet containing the segments for the specified Test
   *         Opportunity.
   * 
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_GetOpportunitySegments_SP (SQLConnection connection, UUID oppkey, UUID session, UUID browser) throws ReturnStatusException;

  /**
   * @param SQLConnection
   *          the database connection
   * @param oppkey
   *          the test opportunity UUID
   * @param session
   *          the session UUID
   * @param browserID
   *          the browser UUID
   * 
   * @return SingleDataResultSet containing the segments for the specified Test
   *         Opportunity.
   * 
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_GetOpportunityItemsWithValidation_SP (SQLConnection connection, UUID oppkey, UUID session, UUID browserID) throws ReturnStatusException;

  /**
   * -- Description: Determine whether the user can open each of several test
   * opportunities lists in tests -- This procedure DOES NOT validate that a
   * test is appropriate for the testee. Only that he is allowed to open -- an
   * opp under the rules of expiration dates and max tries.
   * 
   * @param connection
   * @param testee
   * @param sessionKey
   * @param grade
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_GetEligibleTests_SP (SQLConnection connection, Long testee, UUID sessionKey, String grade) throws ReturnStatusException;

  public SingleDataResultSet S_InsertTestScores_SP (SQLConnection connection, UUID oppkey, String scoreString, Character rowdelim, Character coldelim)
      throws ReturnStatusException;

  public SingleDataResultSet S_GetScoreItems_SP (SQLConnection connection, String clientname, Integer pendingMinutes, Integer minAttempts, Integer maxAttempts, Integer sessionType)
      throws ReturnStatusException;

  public SingleDataResultSet T_IsTestComplete_SP (SQLConnection connection, UUID oppkey) throws ReturnStatusException;

  /**
   * @param connection
   * @param oppKey
   * @param which
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet _TestReportScores_SP (SQLConnection connection, UUID oppKey, String which) throws ReturnStatusException;

  /**
   * @param connection
   * @param oppKey
   * @return
   * @throws ReturnStatusException
   */
  public MultiDataResultSet T_GetDisplayScores_SP (SQLConnection connection, UUID oppKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param segment
   * @param oppKey
   * @param sessionKey
   * @param browserKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_ExitSegment_SP (SQLConnection connection, Integer segment, UUID oppKey, UUID sessionKey, UUID browserKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param segment
   * @param oppKey
   * @param sessionKey
   * @param browserKey
   * @param segmentAccoms
   * @throws ReturnStatusException
   */
  public SingleDataResultSet T_ApproveAccommodations_SP (SQLConnection connection, Integer segment, UUID oppKey, UUID sessionKey, UUID browserKey, String segmentAccoms) throws ReturnStatusException;

  public boolean AuditScores_FN (SQLConnection connection, String clientname) throws ReturnStatusException;

  public void _InsertTestoppScores_SP (SQLConnection connection, UUID testoppkey, String scoreString, _Ref<String> msgRef) throws ReturnStatusException;

  public void _InsertTestoppScores_SP (SQLConnection connection, UUID testoppkey, String scoreString, Character coldelim, Character rowdelim,
      _Ref<String> msgRef) throws ReturnStatusException;

  /**
   * Description of GetCurrentTests(connection, clientname, sessiontype)
   * 
   * @param connection
   *          - connection object
   * @param clientname
   *          - clientname(String)
   * @param sessiontype
   *          - sessiontype(int)
   * @return result - SingleDataResultSet
   */
  public DataBaseTable GetCurrentTests_FN (SQLConnection connection, String clientname, int sessionType) throws ReturnStatusException;

  /**
   * Description of Isgradeequiv(connection, grade1, grade2) Checks if a strings
   * - Grade1 & Grade2 are numerics
   * 
   * @param connection
   *          - connection object
   * @param grade1
   *          - grade1(String)
   * @param grade2
   *          - grade2(String)
   * @return result - Boolean
   */
  public boolean __IsGradeEquiv_FN (String grade1, String grade2);

  /**
   * Description of __Eligibletests(connection, clientname, testee, sessiontype,
   * grade, debug)
   * 
   * -- This is a 3-stage algorithm which determines the set of tests for which
   * the testee is eligible. -- Stage 1: Challenge Up: ChallengeUp is a
   * pipe-delimited list of Test Identifiers which serve as absolute permission
   * to take a test. No other qualifier is checked. -- Stage 2: Determine the
   * tests for which the student meets grade and enrollment requirements and
   * which are not superseded by any challenge-up test -- Stage 3: Apply any ad
   * hoc RTS attribute requirements (enablers/disablers) which may be given in
   * TDS_Configs.
   * 
   * -- Exceptions for anonymous testees on practice test: -- 1. @testee value <
   * 0 -- 2. @grade must be non-null and match the grade field of one or more
   * tests -- 3. Challengeup is irrelevant since the testee selects his/her own
   * grade level from the application interface -- 4. Any test enrollment
   * requirement will not be applied to anonymous testees. They will be allowed
   * to take these tests as long as the @grade they selected matches
   * 
   * @param connection
   *          - connection object
   * @param clientname
   *          - clientname(String)
   * @param testee
   *          - testee(bigint)
   * @param sessiontype
   *          - sessiontype(int = 0)
   * @param grade
   *          - grade(String)
   * @param debug
   *          - debug(boolean)
   * @return result - Boolean
   */

  public SingleDataResultSet __EligibleTests_SP (SQLConnection connection, String clientname, long testee, int sessiontype, String grade, boolean debug) throws ReturnStatusException;

  /**
   * Description of ClientItemStimulusPath(connection, clientname, testkey,
   * itemkey)
   * 
   * @param connection
   *          - connection object
   * @param clientname
   *          - clientname(String)
   * @param testkey
   *          - testkey(string)
   * @return itemkey - itemkey(string)
   */
  public String ClientItemStimulusPath_FN (SQLConnection connection, String clientName, String testkey, String itemkey) throws ReturnStatusException;

  /**
   * Description of T_GetOpportunityAccommodations_SP (SQLConnection connection,
   * UUID oppkey, UUID session, UUID browserId) This Stored procedure gets the
   * following inputs - connection, oppkey, session and browser ID and returns a
   * Single dataresult set which contains - Segment, AccType, accCode,
   * isApproved and recordUsage
   * 
   * @param connection
   *          - connection object
   * @param oppkey
   *          - oppkey(UUID)
   * @param session
   *          - session(UUID)
   * @return result - MultiDataResultset
   */
  public SingleDataResultSet T_GetOpportunityAccommodations_SP (SQLConnection connection, UUID oppkey, UUID session, UUID browserId)
      throws ReturnStatusException;

  /**
   * Description of T_GetItemGroup_SP (SQLConnection connection, UUID oppkey,
   * int pageNumber, String groupID, String dateCreated, UUID session, UUID
   * browserId, boolean validateAccess )
   * 
   * @param connection
   *          - connection object
   * @param oppkey
   *          - oppkey(UUID)
   * @param pagenumber
   *          - pagenumber(int)
   * @param groupID
   *          - groupID(String)
   * @param datecreated
   *          - datecreated(String)
   * @param session
   *          - session(UUID)
   * @param browserid
   *          - browserid(UUID)
   * @param validateaccess
   *          - validateaccess(UUID)
   * @return result - MultiDataResultset
   */
  public SingleDataResultSet T_GetItemGroup_SP (SQLConnection connection, UUID oppkey, int pageNumber, String groupID, String dateCreated, UUID session, UUID browserId, boolean validateAccess)
      throws ReturnStatusException;

  public SingleDataResultSet P_GetTestForms_SP (SQLConnection connection, String clientname, String testId, Integer sessionType) throws ReturnStatusException;

  public MultiDataResultSet T_GetSession_SP (SQLConnection connection, String clientname, String sessionId) throws ReturnStatusException;

  public SingleDataResultSet T_GetItemScoringConfigs_SP (SQLConnection connection, String clientName) throws ReturnStatusException;

  public SingleDataResultSet T_GetItemScoringConfigs_SP (SQLConnection connection, String clientName, String siteId, String serverName) throws ReturnStatusException;

  public SingleDataResultSet T_UpdateScoredResponse2_SP (SQLConnection connection, UUID oppKey, UUID session, 
      UUID browserId, String itemId,
      Integer page, Integer position, String dateCreated,
      Integer responseSequence, Integer score ) throws ReturnStatusException;
  public SingleDataResultSet T_UpdateScoredResponse2_SP (SQLConnection connection, UUID oppKey, UUID session, 
      UUID browserId, String itemId,
      Integer page, Integer position, String dateCreated,
      Integer responseSequence, Integer score, String response, Boolean isSelected, 
      Boolean isValid, Integer scoreLatency, String scoreStatus, String scoreRationale, String scoreDimensions)
          throws ReturnStatusException;
  
  public SingleDataResultSet T_GetTestforScoring2_SP (SQLConnection connection, UUID oppkey) throws ReturnStatusException;
  public SingleDataResultSet T_GetTestforScoring2_SP (SQLConnection connection, UUID oppkey, Character rowdelim) throws ReturnStatusException;
  public SingleDataResultSet T_GetTestforScoring2_SP (SQLConnection connection, UUID oppkey, Character rowdelim, Character coldelim) throws ReturnStatusException;
  
  public String MakeItemscoreString_XML_FN (SQLConnection connection, UUID oppkey) throws ReturnStatusException;
  
  public SingleDataResultSet T_LoginRequirements (SQLConnection connection, String clientname) throws ReturnStatusException;

  /**
   * Get student opportunity number from testopporunity.opportunity field
   * 
   * @param connection
   * @param oppKey
   * @return opportunity number
   * @throws ReturnStatusException
   */
  int getOpportunityNumber (SQLConnection connection, UUID oppKey) throws ReturnStatusException;
  
  public void handleTISReply(SQLConnection connection, UUID oppkey, Boolean success, String errorMessage) throws ReturnStatusException;
}
