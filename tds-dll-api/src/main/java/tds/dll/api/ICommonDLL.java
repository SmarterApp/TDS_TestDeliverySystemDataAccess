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
import java.util.UUID;

import TDS.Shared.Exceptions.ReturnStatusException;
import AIR.Common.DB.DataBaseTable;
import AIR.Common.DB.SQLConnection;
import AIR.Common.DB.results.MultiDataResultSet;
import AIR.Common.DB.results.SingleDataResultSet;
import AIR.Common.Helpers._Ref;

public interface ICommonDLL
{

  /**
   * @param connection
   * @param testKey
   * @return
   * @throws ReturnStatusException
   */
  public MultiDataResultSet IB_GetTestAccommodations_SP (SQLConnection connection, String testKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param tblName
   * @param testKey
   * @return
   * @throws ReturnStatusException
   */
  public DataBaseTable TestKeyAccommodations_FN (SQLConnection connection, String testKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param testKey
   * @return
   * @throws ReturnStatusException
   */
  public DataBaseTable TestKeyAccommodationDependencies_FN (SQLConnection connection, String testKey) throws ReturnStatusException;

  /**
   * This method differs from SQL function because it returns comma separated,
   * single-quoted list of codes only(but not labels) selected VS SQL function
   * returning a table with code and label as columns It is suitable for the
   * manner in which they method is used and it decreases number of temporary
   * tables created.
   * 
   * @param connection
   * @param testKey
   * @return
   * @throws ReturnStatusException
   */
  public String ITEMBANK_TestLanguages_FN (SQLConnection connection, String testKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientname
   * @param itemKey
   * @return
   * @throws ReturnStatusException
   */
  public String ClientItemFile_FN (SQLConnection connection, String clientName, String itemKey) throws ReturnStatusException;

  /**
   * Below two functions(i.e MakeItemKey_FN, MakeStimulusKey_FN) has same
   * functionality except one i/p parameter in future, we can make these 2
   * functions as a single function to minimize coding.
   * 
   * @param connection
   * @param bankkey
   * @param itemkey
   * @return
   */
  public String MakeItemKey_FN (SQLConnection connection, Long bankKey, Long itemKey);

  /**
   * @param connection
   * @param bankkey
   * @param stimkey
   * @return
   */
  public String MakeStimulusKey_FN (SQLConnection connection, Long bankKey, Long stimulusKey);

  public boolean _IsValidStatusTransition_FN (String oldStatus, String newStatus);

  public String _CanChangeOppStatus_FN (SQLConnection connection, String oldstatus, String newstatus);

  public Boolean ScoreByTDS_FN (SQLConnection connection, String clientName, String testId) throws ReturnStatusException;

  public String CanScoreOpportunity_FN (SQLConnection connection, UUID oppkey) throws ReturnStatusException;

  /**
   * This function uses temp table. However it is only used to build comma
   * separated list of values in this one-column table We will not be using temp
   * table for string manipulation (if possible). Note how we built comma
   * separated list based on records in SingleDataResultSet
   * 
   * @param connection
   * @param oppkey
   * @return String
   * @throws ReturnStatusException
   */
  public String MakeItemGroupString_FN (SQLConnection connection, UUID oppkey) throws ReturnStatusException;

  public int IsXMLOn_Fn (SQLConnection connection, UUID oppKey) throws ReturnStatusException;

  public SingleDataResultSet _GetTesteeAttributes_SP (SQLConnection connection, String clientname, Long testee) throws ReturnStatusException;

  public void _SetTesteeAttributes_SP (SQLConnection connection, String clientname, UUID oppkey, Long testee, String context) throws ReturnStatusException;

  public void _RecordBPSatisfaction_SP (SQLConnection connection, UUID oppkey) throws ReturnStatusException;

  public void _OnStatus_Completed_SP (SQLConnection connection, UUID oppkey) throws ReturnStatusException;

  /**
   * This method may return null if it does not make call to submitqareport
   * 
   * @param connection
   * @param oppKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet _OnStatus_Scored_SP (SQLConnection connection, UUID oppKey) throws ReturnStatusException;

  public void _OnStatus_Paused_SP (SQLConnection connection, UUID oppkey, String prevStatus) throws ReturnStatusException;

  public SingleDataResultSet SetOpportunityStatus_SP (SQLConnection connection, UUID oppkey, String status) throws ReturnStatusException;

  public SingleDataResultSet SetOpportunityStatus_SP (SQLConnection connection, UUID oppkey, String status, Boolean suppressReport) throws ReturnStatusException;

  public SingleDataResultSet SetOpportunityStatus_SP (SQLConnection connection, UUID oppkey, String status, Boolean suppressReport, String requestor) throws ReturnStatusException;

  /**
   * This method will return null if suppressReport parameter is set to true
   * 
   * @param connection
   * @param oppkey
   * @param status
   * @param suppressReport
   * @param requestor
   * @param comment
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet SetOpportunityStatus_SP (SQLConnection connection, UUID oppkey, String status, Boolean suppressReport, String requestor, String comment) throws ReturnStatusException;

  public Integer AuditOpportunities_FN (SQLConnection connection, String clientname) throws ReturnStatusException;

  public Integer AuditSessions_FN (SQLConnection connection, String clientname) throws ReturnStatusException;

  /**
   * This method differs from SQL function because it returns comma separated,
   * single-quoted list of statuses selected VS SQL function returning a table.
   * It is suitable for the manner in which they method is used and it decreases
   * number of temporary tables created.
   * 
   * @param connection
   * @param usage
   * @param stage
   * @return
   * @throws ReturnStatusException
   */
  public String GetStatusCodes_FN (SQLConnection connection, String usage, String stage) throws ReturnStatusException;

  public String ValidateProctorSession_FN (SQLConnection connection, Long proctorkey, UUID sessionkey, UUID browserkey) throws ReturnStatusException;

  public SingleDataResultSet P_PauseSession_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey) throws ReturnStatusException;

  public SingleDataResultSet P_PauseSession_SP (SQLConnection connection, UUID sessionKey, Long proctorKey, UUID browserKey, String reason, Boolean report) throws ReturnStatusException;

  public void _RecordSystemError_SP (SQLConnection connection, String proc, String msg) throws ReturnStatusException;

  public void _RecordSystemError_SP (SQLConnection connection, String proc, String msg, Long testee,
      String test, Integer opportunity, String application, String clientIp, UUID applicationContextID,
      String stackTrace, UUID testoppkey, String clientname) throws ReturnStatusException;

  public SingleDataResultSet _ReturnError_SP (SQLConnection connection, String client, String procname, String appkey) throws ReturnStatusException;

  public SingleDataResultSet _ReturnError_SP (SQLConnection connection, String client, String procname, String appkey, String argstring, UUID oppkey, String context) throws ReturnStatusException;

  /**
   * procname paramter must not be null
   * 
   * @param connection
   * @param client
   * @param procname
   * @param appkey
   * @param argstring
   * @param oppkey
   * @param context
   * @param status
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet _ReturnError_SP (SQLConnection connection, String client, String procname, String appkey, String argstring, UUID oppkey, String context, String status)
      throws ReturnStatusException;

  public void _FormatMessage_SP (SQLConnection connection, String clientname, String language, String context, String appkey, _Ref<String> errmsg, String argstring) throws ReturnStatusException;

  public void _FormatMessage_SP (SQLConnection connection, String clientname, String language, String context, String appkey, _Ref<String> errmsg) throws ReturnStatusException;

  public void _FormatMessage_SP (SQLConnection connection, String clientname, String language, String context, String appkey, _Ref<String> errmsg, String argstring, Character delimiter,
      String subject, String grade) throws ReturnStatusException;

  public String TDS_GetMessagekey_FN (SQLConnection connection, String client, String application, String contextType,
      String context, String appkey, String language, String grade, String subject) throws ReturnStatusException;

  public DataBaseTable _BuildTable_FN (SQLConnection connection, String tblName, String theLine, String delimiter)
      throws ReturnStatusException;

  /**
   * This method is an alternative for _BuildTable_FN for cases where we can
   * avoid creating temporary table on database server side and instead can use
   * String array on java side.
   * 
   * @param theLine
   *          a string to be split by delimiter into array of strings
   * @param delimiter
   * @param columnIdx
   *          ; if set to -1, all rows in the array are returned; column index
   *          is zero based.
   * @return
   */
  public String[] _BuildTableAsArray (String theLine, String delimiter, int columnIdx);

  public void _LogDBError_SP (SQLConnection connection, String procname, String msg, Long testee,
      String test, Integer opportunity, UUID testopp) throws ReturnStatusException;

  public void _LogDBError_SP (SQLConnection connection, String procname, String msg, Long testee,
      String test, Integer opportunity, UUID testopp, String clientname, UUID session) throws ReturnStatusException;

  public void _LogDBLatency_SP (SQLConnection connection, String procname, Date starttime) throws ReturnStatusException;

  public void _LogDBLatency_SP (SQLConnection connection, String procname, Date starttime, Long userkey, boolean checkaudit, Integer N, UUID testoppkey) throws ReturnStatusException;

  /**
   * Caller must pass non-null procname and starttime. If starttime is null,
   * Runtime exception is thrown.
   * 
   * @param connection
   * @param procname
   * @param starttime
   * @param userkey
   * @param checkaudit
   * @param N
   * @param testoppkey
   * @param sessionkey
   * @param clientname
   * @param comment
   * @throws ReturnStatusException
   */
  public void _LogDBLatency_SP (SQLConnection connection, String procname, Date starttime, Long userkey, boolean checkaudit, Integer N, UUID testoppkey, UUID sessionkey, String clientname,
      String comment) throws ReturnStatusException;

  // TODO Oksana will talk to LA about this function; this is just a placeholder
  public boolean AuditProc_FN (SQLConnection connection, String procName) throws ReturnStatusException;

  /**
   * @param ReturnErrorArgs
   *          class containing arguments to the underlying _ReturnError_SP
   *          method.
   * 
   * @returns SingleDataResultSet containing one row specifying the error.
   * 
   * @throws ReturnStatusException
   */
  public SingleDataResultSet _ReturnError_SP (ReturnErrorArgs args)
      throws ReturnStatusException;

  /**
   * @param LogDBLatencyArgs
   *          class containing arguments to the underlying _LogDBLatency_SP
   *          method.
   * 
   * @returns SingleDataResultSet containing one row specifying the error.
   * 
   * @throws ReturnStatusException
   * 
   * @throws SQLException
   */
  public void _LogDBLatency_SP (LogDBLatencyArgs args) throws ReturnStatusException;

  /**
   * @param LogDBErrorArgs
   *          class containing arguments to the underlying _LogDBError_SP
   *          method.
   * 
   * @returns SingleDataResultSet containing one row specifying the error.
   * 
   * @throws ReturnStatusException
   */
  public void _LogDBError_SP (LogDBErrorArgs args) throws ReturnStatusException;

  Date adjustDate (Date theDate, int increment, int incrementUnit) throws ReturnStatusException;

  String getExternsColumnByClientName (SQLConnection connection, String clientName, String columnName) throws ReturnStatusException;

  String getLocalhostName ();

  Date adjustDateMinutes (Date theDate, Integer increment);

  SingleDataResultSet ReturnStatusReason (String status, String reason) throws ReturnStatusException;

  SingleDataResultSet ReturnStatusReason (String status, String reason, String context, UUID oppkey, Integer opportunity) throws ReturnStatusException;

  public MultiDataResultSet _UpdateOpportunityAccommodations_SP (SQLConnection connection, UUID oppKey, int segment, String accoms, int isStarted, Boolean approved, Boolean restoreRTS,
      _Ref<String> error) throws ReturnStatusException;

  public MultiDataResultSet _UpdateOpportunityAccommodations_SP (SQLConnection connection, UUID oppKey, int segment, String accoms, int isStarted, Boolean approved, Boolean restoreRTS,
      _Ref<String> error, int debug) throws ReturnStatusException;
  
  /**
   * @param connection
   * @param tblName
   * @param clientName
   * @param testId
   * @return
   * @throws ReturnStatusException
   */
  public DataBaseTable ClientTestAccommodations_FN (SQLConnection connection, String clientName, String testId) throws ReturnStatusException;

  /**After discussing with sai, please note that i changed from "desc" to "asc" in the below query for the o/p format. 
   * @param connection
   * @param oppKey
   * @return
   * @throws ReturnStatusException
   */
  public String P_FormatAccommodations_FN (SQLConnection connection, UUID oppKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param bankkey
   * @param itemkey
   * @return
   * @throws ReturnStatusException
   */
  public String ITEMBANK_ItemFile_FN (SQLConnection connection, long bankkey, long itemkey) throws ReturnStatusException;

  public DataBaseTable _SplitAccomCodes_FN (SQLConnection connection, String clientname, String testkey, String accoms) throws ReturnStatusException;

  public Integer getAppLock (SQLConnection connection, String resourcename, String lockmode) throws ReturnStatusException;
  
  public void releaseAppLock (SQLConnection connection, String resourcename) throws ReturnStatusException;
  
  /**
   * 
   * @param connection
   * @param testkey
   * @return
   * @throws ReturnStatusException
   */
  public String TestKeyClient_FN(SQLConnection connection, String testkey) throws ReturnStatusException;
  
  public SingleDataResultSet SubmitQAReport_SP (SQLConnection connection, UUID oppkey, String statusChange) throws ReturnStatusException;

  public String _CoreSessName_FN (SQLConnection connection, String clientName, String procName) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @param prefix
   * @param sessionId
   * @throws ReturnStatusException
   */
  public void _CreateClientSessionID_SP (SQLConnection connection, String clientName, String prefix, _Ref<String> sessionId) throws ReturnStatusException;

  /**
   * Udaya: In the original stored procedure, please note that there is a
   * parameter called "@environment" in the T_GetBrowserWhiteList. This
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

  public SingleDataResultSet T_GetBrowserWhiteList_SP (SQLConnection connection, String clientName, String appName) throws ReturnStatusException;

  public SingleDataResultSet T_GetBrowserWhiteList_SP (SQLConnection connection, String clientName, String appName, String context) throws ReturnStatusException;

}
