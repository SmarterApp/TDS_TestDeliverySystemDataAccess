/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.api;

import java.util.Date;
import java.util.UUID;

import TDS.Shared.Exceptions.ReturnStatusException;
import AIR.Common.DB.DataBaseTable;
import AIR.Common.DB.SQLConnection;
import AIR.Common.DB.results.MultiDataResultSet;
import AIR.Common.DB.results.SingleDataResultSet;
import AIR.Common.Helpers._Ref;

/**
 * @author temp_ukommineni
 * 
 */
public interface IProctorDLL
{
  /**
   * @param connection
   * @param clientName
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   */
  public MultiDataResultSet IB_GetSegments_SP (SQLConnection connection, String clientName, Integer sessionType) throws ReturnStatusException;
  
  /**
   * @param connection
   * @param clientName
   * @param context
   * @return
   * @throws ReturnStatusException
   */
  public MultiDataResultSet IB_GlobalAccommodations_SP (SQLConnection connection, String clientName, String context) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @param application
   * @param userId
   * @param clientIp
   * @param proxyIp
   * @param userAgent
   * @throws ReturnStatusException
   */
  public void _RecordSystemClient_SP (SQLConnection connection, String clientName, String application, String userId, String clientIP, String proxyIP, String userAgent) throws ReturnStatusException;
  
  
  /**
   * This function returns 'bit' in the studio. so, in our java side, we need to
   * return 'boolean' but in this case its not possible to return 'boolean'
   * value because column 'IsOn' from TDSCONFIGS_Client_SystemFlags table is
   * Integer type and its not possible to convert 'int' to 'boolean' on our java
   * side. So, declared return type as 'int'.
   * 
   * @param connection
   * @param clientName
   * @return
   * @throws ReturnStatusException
   */
  public int _SuppressScores_FN (SQLConnection connection, String clientName) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @param testeeId
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet GetTesteeAttributes_SP (SQLConnection connection, String clientName, String testeeId) throws ReturnStatusException;

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @param opportunityKey
   * @param segment
   * @param segmentAccoms
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_ApproveAccommodations_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, UUID opportunityKey, Integer segment, String segmentAccoms) throws ReturnStatusException;
  
  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @param opportunityKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_ApproveOpportunity_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, UUID opportunityKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @param browserKey
   * @param sessionName
   * @param proctorKey
   * @param procId
   * @param procName
   * @param dateBegin
   * @param dateEnd
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_CreateSession_SP (SQLConnection connection, String clientName, UUID browserKey, String sessionName, Long proctorKey, String procId, String procName,
      Date dateBegin, Date dateEnd, Integer sessionType) throws ReturnStatusException;

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetActiveCount_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey) throws ReturnStatusException;

  
  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetCurrentSessionTestees_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @param externalId
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetRTSTestee_SP (SQLConnection connection, String clientName, String externalId) throws ReturnStatusException;

  public SingleDataResultSet P_GetCurrentSessions_SP (SQLConnection connection, String clientName, Long proctorKey) throws ReturnStatusException;

  /**
   * Need to address dateadd() when migrating to MySql
   * 
   * @param connection
   * @param clientName
   * @param proctorKey
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetCurrentSessions_SP (SQLConnection connection, String clientName, Long proctorKey, int sessionType) throws ReturnStatusException;

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetSessionTests_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @return
   * @throws ReturnStatusException
   */
  public MultiDataResultSet P_GetTestsForApproval_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetCurrentAlertMessages_SP (SQLConnection connection, String clientName) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @param proctorKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetUnAcknowledgedAlertMessages_SP (SQLConnection connection, String clientName, Long proctorKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @param proctorKey
   * @param sessionID
   * @param browserKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_HandOffSession_SP (SQLConnection connection, String clientName, Long proctorKey, String sessionID, UUID browserKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @param testKey
   * @param testId
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_InsertSessionTest_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, String testKey, String testId) throws ReturnStatusException;

  public SingleDataResultSet P_PauseAllSessions_SP (SQLConnection connection, String clientName, Long proctorKey, UUID browserKey, int sessionType) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @param proctorKey
   * @param browserKey
   * @param exemptCurrent
   * @param reportClosed
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_PauseAllSessions_SP (SQLConnection connection, String clientName, Long proctorKey, UUID browserKey, int exemptCurrent, int reportClosed, int sessionType)
      throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @param proctorKey
   * @param browserKey
   * @param sessionType
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_LogOutProctor_SP (SQLConnection connection, String clientName, Long proctorKey, UUID browserKey, int sessionType) throws ReturnStatusException;

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @param opportunityKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_PauseOpportunity_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, UUID opportunityKey) throws ReturnStatusException;

  public SingleDataResultSet P_ResumeAllSessions_SP (SQLConnection connection, String clientName, Long proctorKey, UUID browserKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @param proctorKey
   * @param browserKey
   * @param suppressOutput
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_ResumeAllSessions_SP (SQLConnection connection, String clientName, Long proctorKey, UUID browserKey, Integer suppressOutput, int sessionType)
      throws ReturnStatusException;


  public SingleDataResultSet P_DenyApproval_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, UUID opportunityKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @param opportunityKey
   * @param reason
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_DenyApproval_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, UUID opportunityKey, String reason) throws ReturnStatusException;

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetApprovedTesteeRequests_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @param opportunityKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetCurrentTesteeRequests_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, UUID opportunityKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param bankkey
   * @param itemkey
   * @return
   * @throws ReturnStatusException
   */
  public String ITEMBANK_StimulusFile_FN (SQLConnection connection, Long bankkey, Long stimKey) throws ReturnStatusException;

  public SingleDataResultSet P_GetTesteeRequestValues_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, UUID requestKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @param requestKey
   * @param markFulfilled
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetTesteeRequestValues_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, UUID requestKey, Boolean markFulfilled)
      throws ReturnStatusException;

  public SingleDataResultSet P_DenyTesteeRequest_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, UUID requestKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @param requestKey
   * @param reason
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_DenyTesteeRequest_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, UUID requestKey, String reason) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetConfigs_SP (SQLConnection connection, String clientName) throws ReturnStatusException;

  /**
   * @param connection
   * @param systemID
   * @param client
   * @param language
   * @param contextList
   * @param delimiter
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet TDSCONFIGS_TDS_GetMessages_SP (SQLConnection connection, String systemID, String client, String language, String contextList, Character delimiter)
      throws ReturnStatusException;

  public SingleDataResultSet AppMessagesByContext_SP (SQLConnection connection, String systemID, String client, String language, String contextList) throws ReturnStatusException;

  /**
   * @param connection
   * @param systemID
   * @param client
   * @param language
   * @param contextList
   * @param delimiter
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet AppMessagesByContext_SP (SQLConnection connection, String systemID, String client, String language, String contextList, Character delimiter)
      throws ReturnStatusException;

  
  public String All_FormatMessage_SP (SQLConnection connection, String client, String language, String application, String contextType, String context, String appkey, _Ref<String> msg)
      throws ReturnStatusException;

  /**
   * @param connection
   * @param client
   * @param language
   * @param application
   * @param contextType
   * @param context
   * @param appkey
   * @param msg
   * @param argstring
   * @param delim
   * @param subject
   * @param grade
   * @return
   * @throws ReturnStatusException
   */
  public String All_FormatMessage_SP (SQLConnection connection, String client, String language, String application, String contextType, String context, String appkey, _Ref<String> msg,
      String argstring, Character delim, String subject, String grade) throws ReturnStatusException;

  /**
   * @param connection
   * @param sessionKey
   * @param proctorKey
   * @param browserKey
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_SetSessionDateVisited_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey) throws ReturnStatusException;

  public DataBaseTable TDS_GetMessages_SP (SQLConnection connection, String systemID, String client, String language, String contextList, Character delimiter)
      throws ReturnStatusException;
}
