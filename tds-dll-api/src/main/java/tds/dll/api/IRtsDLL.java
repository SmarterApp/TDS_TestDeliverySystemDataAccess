/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.api;

import java.util.List;
import java.util.UUID;

import AIR.Common.DB.SQLConnection;
import AIR.Common.DB.results.SingleDataResultSet;
import AIR.Common.Helpers._Ref;
import TDS.Shared.Exceptions.ReturnStatusException;

/**
 * Interface for RTS procedure calls.
 */
public interface IRtsDLL
{

  /**
   * Overloaded method for
   * <code> _GetRTSAttribute_SP(SQLConnection connection, String clientname, Long testee, String attname, _Ref attValue, Boolean debug)</code>
   * with a debug value of false
   * 
   * @see #_GetRTSAttribute_SP(SQLConnection, String, Long, String, _Ref,
   *      Boolean)
   */
  public void _GetRTSAttribute_SP (SQLConnection connection, String clientname, Long testee, String attname, _Ref<String> attValue) throws ReturnStatusException;

  /**
   * Gets RTS attribute.
   * 
   * @param connection
   * @param clientname
   * @param testee
   *          Testee key
   * @param attname
   *          Attribute name
   * @param attValue
   *          Attribute value
   * @param debug
   * @throws ReturnStatusException
   */
  public void _GetRTSAttribute_SP (SQLConnection connection, String clientname, Long testee, String attname, _Ref<String> attValue, Boolean debug) throws ReturnStatusException;

  /**
   * Gets RTS Entity Key.
   * 
   * @param connection
   * @param clientname
   * @param externaId
   *          External ID
   * @param entityType
   * @param entityKeyRef
   * @throws ReturnStatusException
   */
  public void _GetRTSEntity_SP (SQLConnection connection, String clientname, String externaId, String entityType, _Ref<Long> entityKeyRef) throws ReturnStatusException;

  /**
   * Gets Testee Relationships.
   * 
   * @param connection
   * @param clientname
   * @param testee
   *          Testee key
   * @return SingleDataResultSet
   * @throws ReturnStatusException
   */
  public SingleDataResultSet _GetTesteeRelationships_SP (SQLConnection connection, String clientname, Long testee) throws ReturnStatusException;

  /**
   * Validate institution match.
   * 
   * @param connection
   * @param clientname
   * @param testeeKey
   * @param proctorKey
   * @param instKey
   *          Institution key
   * @throws ReturnStatusException
   */
  public void _ValidateInstitutionMatch_SP (SQLConnection connection, String clientname, Long testeeKey, Long proctorKey, _Ref<String> instKey) throws ReturnStatusException;

  /**
   * Gets RTS relationship.
   * 
   * @param connection
   * @param clientname
   * @param testee
   *          Testee key
   * @param relationship
   * @param entityKey
   * @param entityId
   * @param entityName
   * @throws ReturnStatusException
   */
  public void _GetRTSRelationship_SP (SQLConnection connection, String clientname, Long testee,
      String relationship, _Ref<Long> entityKey, _Ref<String> entityId, _Ref<String> entityName) throws ReturnStatusException;

  /**
   * 
   * @param connection
   * @param clientName
   * @param userKey
   * @return SingleDataResultSet
   * @throws ReturnStatusException
   */
  public SingleDataResultSet GetRTSUserRoles_SP (SQLConnection connection, String clientName, Long userKey) throws ReturnStatusException;

  /**
   * 
   * @param connection
   * @param clientName
   * @param userKey
   * @param sessionType
   * @return SingleDataResultSet
   * @throws ReturnStatusException
   */
  public SingleDataResultSet GetRTSUserRoles_SP (SQLConnection connection, String clientName, Long userKey, Integer sessionType) throws ReturnStatusException;

  /**
   * Gets Districts Schools.
   * 
   * @param connection
   * @param clientname
   * @param stateCode
   * @return SingleDataResultSet
   * @throws ReturnStatusException
   */
  public SingleDataResultSet R_GetDistricts_SP (SQLConnection connection, String clientname) throws ReturnStatusException;
  
  /**
   * Gets district schools.
   * 
   * @param connection
   * @param clientname
   * @param districtKey
   * @return SingleDataResultSet
   * @throws ReturnStatusException
   */
  public SingleDataResultSet R_GetDistrictSchools_SP (SQLConnection connection, String clientname, String districtKey) throws ReturnStatusException;

  /**
   * Gets school grades.
   * 
   * @param connection
   * @param schoolKey
   * @return SingleDataResultSet
   * @throws ReturnStatusException
   */
  public SingleDataResultSet R_GetSchoolGrades_SP (SQLConnection connection, String clientName, String schoolKey) throws ReturnStatusException;

  /**
   * Gets school students.
   * 
   * @param connection
   * @param clientname
   * @param schoolKey
   * @param grade
   * @param firstName
   * @param lastName
   * @return SingleDataResultSet
   * @throws ReturnStatusException
   */
  public SingleDataResultSet getSchoolStudents (SQLConnection connection, String clientname, String schoolKey, String grade, String firstName, String lastName) throws ReturnStatusException;

  /**
   * Gets all tests.
   * 
   * @param connection
   * @param clientname
   * @param sessionType
   * @param proctorKey
   * @return SingleDataResultSet
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetAllTests_SP (SQLConnection connection, String clientname, int sessionType, Long proctorKey) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @param userId
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet GetRTSUser_SP (SQLConnection connection, String clientName, String userId) throws ReturnStatusException;

  public SingleDataResultSet T_GetRTSAccommodations_SP (SQLConnection connection, String clientname, Long testee) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @param userKey
   * @param userEntityRef
   * @param roleEntityRef
   * @param roleRef
   * @param sessionType
   * @throws ReturnStatusException
   */
  public void _ValidateAsProctor_SP (SQLConnection connection, String clientName, Long userKey, _Ref<Long> userEntityRef, _Ref<Long> roleEntityRef, _Ref<String> roleRef, Integer sessionType)
      throws ReturnStatusException;

  public SingleDataResultSet P_ValidateProctor_SP (SQLConnection connection, String clientName, UUID browserKey, String proctorId) throws ReturnStatusException;

  /**
   * @param connection
   * @param clientName
   * @param browserKey
   * @param proctorId
   * @param password
   * @param oppSessions
   * @param ignorepw
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_ValidateProctor_SP (SQLConnection connection, String clientName, UUID browserKey, String proctorId, String password, Integer oppSessions, Boolean ignorepw,
      Integer sessionType) throws ReturnStatusException;

  public Long getOrCreateStudentKey (SQLConnection connection, String SSID, String clientCode) throws ReturnStatusException;
  
  /**
   * @param connection
   * @param userId
   * @return
   * @throws ReturnStatusException
   */
  public int createUser(SQLConnection connection,String userId, String email, String fullName) throws ReturnStatusException;
  
  /**
   * 
   * @param connection
   * @param userId
   * @param email
   * @return
   * @throws ReturnStatusException
   */
  public boolean userAlreadyExists(SQLConnection connection,String userId, String email) throws ReturnStatusException;

  /**
   * @param connection
   * @param testeeKey
   * @param clientName
   * @param studentPackage
   */
  public int createAndUpdateStudentIsCurrent (SQLConnection connection, Long testeeKey, String clientName, String studentPackage) throws ReturnStatusException;

  /**
   * @param connection
   * @param key
   * @param clientName
   * @param proctorPackage
   * @param testType list
   */
  public int createAndUpdateProctorIsCurrent (SQLConnection connection, Long key, String clientName, String proctorPackage, List<TestType> testType) throws ReturnStatusException;
  
}
