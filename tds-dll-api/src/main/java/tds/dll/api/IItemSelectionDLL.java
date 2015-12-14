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
import AIR.Common.Helpers._Ref;
import TDS.Shared.Exceptions.ReturnStatusException;

/**
 * @author akulakov
 *
 */
public interface IItemSelectionDLL
{
 
  final static String         SATISFIED   = "SATISFIED";
  /**
   * 
   *     final String SQL_QUERY = "select 
   *    ${oppkey} as oppkey, 
   *    ${segment}   as segmentPosition, 
        ${segmentKey} as segmentKey, 
        ${segmentID}  as segmentID, 
        ${algorithm}  as algorithm,
        ${groupID}    as groupID, 
        ${blockID}    as blockID, 
        ${isSim}      as isSimulation, 
        ${session}    as sessionKey";

   */
  public static String OPPKEY = "oppkey";
  public static String SEGMENTPOSITION = "segmentPosition";
  public static String SEGMENTKEY = "segmentKey";
  public static String SEGMENT = "segment";
  public static String SEGMENTID = "segmentID";
  public static String ALGORITHM = "algorithm";
  public static String GROUPID = "groupID";
  public static String BLOCKID = "blockID";
  public static String ISSIMULATION = "issim";
  public static String SESSIONKEY = "sessionKey";
  public static String SESSION = "session";

  /**
   * 
   * @param connection
   * @param oppkey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet AA_GetNextItemCandidates_SP (SQLConnection connection, UUID oppkey) 
      throws ReturnStatusException;
 
  /**
   * 
   * @param connection
   * @param oppkey
   * @param segmentKey
   * @param language
   * @param groupIDValue
   * @param blockIDValue
   * @throws ReturnStatusException
   */
  public void _AA_NextFixedformGroup_SP (SQLConnection connection, UUID oppkey,
      String segmentKey, String language,
      _Ref<String> groupIDValue,
      _Ref<String> blockIDValue) throws ReturnStatusException;

  /**
   * 
   * @param connection
   * @param oppkey
   * @param segmentPosition
   * @param segmentKey
   * @param segmentID
   * @param language
   * @param groupID
   * @param blockID
   * @param debug
   * @throws ReturnStatusException
   */
  public void _AA_NextFieldtestGroup_SP (SQLConnection connection, UUID oppkey,
      int segmentPosition, String segmentKey,String segmentID, String language,
      _Ref<String> groupID,
      _Ref<String> blockID,
      boolean debug) throws ReturnStatusException;
  
  /**
   * 
   * @param connection
   * @param oppkey
   * @param segmentKey
   * @param groupID
   * @param blockID
   * @param isFieldTest
   * @param gebug
   * @return
   * @throws ReturnStatusException
   */
  public MultiDataResultSet AA_GetItemgroup_SP(SQLConnection connection, UUID oppkey,
      String segmentKey, String groupID,  String blockID, Boolean isFieldTest, 
      Boolean gebug) throws ReturnStatusException;
  
  /**
   * 
   * @param connection
   * @param oppkey
   * @return
   * @throws ReturnStatusException
   */
  public Boolean IsSimulation_FN (SQLConnection connection, UUID oppkey) throws ReturnStatusException;
  
  /**
   * 
   * @param connection
   * @param oppkey
   * @param segment
   * @return
   * @throws ReturnStatusException
   */
  public Boolean _AA_IsSegmentSatisfied_FN (SQLConnection connection, UUID oppkey, 
      Integer segment) throws ReturnStatusException;
    
  /**
   * 
   * @param connection
   * @param oppkey
   * @param segmentKey
   * @param segmentID
   * @param groupID
   * @param blockID
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet AF_GetItempool_FN_2 (SQLConnection connection, UUID oppkey,
      String segmentKey, 
      String segmentID,
      Boolean fieldTest,
      String groupID,
      String blockID) throws ReturnStatusException;
 
  /**
   * 
   * @param connection
   * @param oppkey
   * @param segmentKey
   * @param segmentID
   * @param fieldTest
   * @param groupID
   * @param blockID
   * @return
   * @throws ReturnStatusException
   */
  public DataBaseTable AF_GetItempool_FN(SQLConnection connection,
			UUID oppkey, String segmentKey, String segmentID,
			Boolean fieldTest, String groupID, String blockID)
			throws ReturnStatusException ;
  
  /**
   * 
   * @param clientname
   * @param oppkey
   * @param segmentKey
   * @param testID
   * @param fieldTest
   * @param groupID
   * @param blockID
   * @return
   * @throws ReturnStatusException
   */
  public DataBaseTable _AA_GetCustomItemgroup_FN(SQLConnection connection, String clientname, UUID oppkey,
      String segmentKey, 
      String testID, 
      Boolean fieldTest, 
      String groupID, 
      String blockID) throws ReturnStatusException;
  
  /**
   * 
   * @param clientname
   * @param oppkey
   * @param segmentKey
   * @param testID
   * @param fieldTest
   * @param groupID
   * @param blockID
   * @return
   * @throws ReturnStatusException
   */
  public DataBaseTable _AA_SIM_GetCustomItemgroup_FN(SQLConnection connection, String clientname, UUID oppkey,
      String segmentKey, 
      String segmentID, 
      Boolean fieldTest,
      UUID session,
      String groupID, 
      String blockID) throws ReturnStatusException;
  
  /**
   * 
   * @param connection
   * @param segmentKey
   * @return
   * @throws ReturnStatusException 
   */
  public MultiDataResultSet AA_GetSegment_SP(SQLConnection connection, String segmentKey) throws ReturnStatusException;

  /**
   * 
   * @param connection
   * @param sessionKey
   * @param segmentKey
   * @return
   * @throws ReturnStatusException 
   */
  public MultiDataResultSet AA_SIM_GetSegment_SP(SQLConnection connection, UUID sessionKey, String segmentKey) throws ReturnStatusException;
 
  /**
   * 
   * @param oppKey
   * @param segmentKey
   * @return
   * @throws ReturnStatusException
   */
  public MultiDataResultSet AA_GetDataHistory_SP(SQLConnection connection, UUID oppKey, String segmentKey) throws ReturnStatusException;
  
  /**
   * 
   * @param connection
   * @param oppKey
   * @return
   * @throws ReturnStatusException
   */
  public Float FN_GetInitialAbility_FN(SQLConnection connection, UUID oppKey) throws ReturnStatusException;

  /**
   * 
   * @param connection
   * @param oppkey
   * @param segmentKey
   * @return
   * @throws ReturnStatusException
   */
  public String _AA_ItemPoolString_FN(SQLConnection connection, UUID oppkey, String segmentKey) throws ReturnStatusException;
  
  /**
   * 
   * @param connection
   * @param oppkey
   * @param segmentPosition
   * @param termReason
 * @return 
   * @throws ReturnStatusException
   */
  public boolean AA_SetSegmentSatisfied_SP (SQLConnection connection, UUID oppkey, 
		  Integer segmentPosition, String termReason) throws ReturnStatusException;
	/**
	 *   
	 * @param connection
	 * @param segmentKey
	 * @return
	 * @throws ReturnStatusException
	 */
  public MultiDataResultSet AA_GetSegment2_SP (SQLConnection connection, String segmentKey, Boolean controlTriples) throws ReturnStatusException;
  
  /**
   * 
   * @param connection
   * @param sessionKey
   * @param segmentKey
   * @param controlTriples
   * @return
   * @throws ReturnStatusException
   */
  public MultiDataResultSet AA_SIM_GetSegment2_SP(SQLConnection connection, UUID sessionKey, String segmentKey, Boolean controlTriples) throws ReturnStatusException;    
  
  /**
   * 
   * @param connection
   * @param oppkey
   * @param segmentKey
   * @return
   * @throws ReturnStatusException
   */
  public MultiDataResultSet AA_GetDataHistory2_SP (SQLConnection connection, UUID oppkey, String segmentKey) throws ReturnStatusException;

  /**
   * 
   * @param connection
   * @param oppkey
   * @return
   * @throws ReturnStatusException
   */
  public Float _AA_GetInitialAbility_FN (SQLConnection connection, UUID oppkey) 
		  throws ReturnStatusException;     
  /**
   * 
   * @param connection
   * @param oppkey
   * @param segmentKey
   * @return
   * @throws ReturnStatusException
   */
  public MultiDataResultSet AA_GetDataHistory_LA2_SP (SQLConnection connection, UUID oppkey, String segmentKey) throws ReturnStatusException;

  /**
   * 
   * @param connection
   * @param oppkey
   * @param segmentKey
   * @return
   * @throws ReturnStatusException
   */
  public MultiDataResultSet AA_GetDataHistory2_LA2_SP(SQLConnection connection, UUID oppkey, String segmentKey)
			throws ReturnStatusException;
  
  /**
   * 
   * @param connection
   * @param oppkey
   * @param poolfilterProperty
   * @param segmentkey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet AA_AddOffgradeItems_SP(SQLConnection connection, UUID oppkey,
		  String poolfilterProperty, String segmentkey) throws ReturnStatusException;
  
}
