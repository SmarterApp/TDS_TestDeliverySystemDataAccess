/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.mysql;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.opentestsystem.shared.trapi.ITrClient;
import org.opentestsystem.shared.trapi.data.District;
import org.opentestsystem.shared.trapi.data.Institution;
import org.opentestsystem.shared.trapi.data.SchoolStudent;
import org.opentestsystem.shared.trapi.data.User;
import org.opentestsystem.shared.trapi.data.User.RoleAssociation;
import org.opentestsystem.shared.trapi.exception.TrApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import tds.dll.api.ICommonDLL;
import tds.dll.api.IProctorDLL;
import tds.dll.api.IRtsDLL;
import tds.dll.api.IRtsReportingDLL;
import tds.dll.api.TestType;
import tds.dll.common.rtspackage.EntityType;
import tds.dll.common.rtspackage.IRtsPackageReader;
import tds.dll.common.rtspackage.IRtsPackageWriter;
import tds.dll.common.rtspackage.common.exception.RtsPackageReaderException;
import tds.dll.common.rtspackage.common.exception.RtsPackageWriterException;
import tds.dll.common.rtspackage.common.table.RtsField;
import tds.dll.common.rtspackage.common.table.RtsRecord;
import tds.dll.common.rtspackage.common.table.RtsTable;
import tds.dll.common.rtspackage.proctor.ProctorPackageReader;
import tds.dll.common.rtspackage.proctor.ProctorPackageWriter;
import tds.dll.common.rtspackage.proctor.data.ProctorPackage;
import tds.dll.common.rtspackage.student.StudentPackageReader;
import tds.dll.common.rtspackage.student.StudentPackageWriter;
import tds.dll.common.rtspackage.student.data.Student;
import tds.dll.common.rtspackage.student.data.StudentPackage;
import AIR.Common.DB.AbstractDLL;
import AIR.Common.DB.DbComparator;
import AIR.Common.DB.SQLConnection;
import AIR.Common.DB.SQL_TYPE_To_JAVA_TYPE;
import AIR.Common.DB.SqlParametersMaps;
import AIR.Common.DB.results.DbResultRecord;
import AIR.Common.DB.results.SingleDataResultSet;
import AIR.Common.Helpers.CaseInsensitiveMap;
import AIR.Common.Helpers._Ref;
import AIR.Common.Sql.AbstractDateUtilDll;
import AIR.Common.Utilities.UrlEncoderDecoderUtils;
import AIR.Common.time.DateTime;
import TDS.Shared.Data.ColumnResultSet;
import TDS.Shared.Exceptions.ReturnStatusException;

/**
 * Performs parallel operations to RtsDLL on R_StudentPackage and
 * R_ProctorPackage.
 * 
 * @author jmambo
 * 
 */
public class RtsPackageDLL extends AbstractDLL implements IRtsDLL, IRtsReportingDLL
{

  private static final Logger _logger     = LoggerFactory.getLogger (RtsPackageDLL.class);

  @Autowired
  private ICommonDLL          _commonDll  = null;

  @Autowired
  private AbstractDateUtilDll _dateUtil   = null;

  @Autowired
  private IProctorDLL         _proctorDll = null;

  @Autowired
  private ITrClient           _trClient   = null;

  @Value ("${StateCode}")
  private String              _stateCode;
  
  @Value ("${ClientName}")
  private String              _clientName;

  @PostConstruct
  private void init () {
    _stateCode = _stateCode.toUpperCase ();
  }


  @Override
  public void _ValidateInstitutionMatch_SP (SQLConnection connection, String clientName, Long testeeKey, Long proctorKey, _Ref<String> instKey) throws ReturnStatusException {
    byte[] packageObject = getStudentPackageByKeyAndClientName (connection, testeeKey, clientName);
    instKey.set (null);
    String studentSchoolId = null;
    if (packageObject != null) {

      IRtsPackageReader studentReader = null;
      studentReader = new StudentPackageReader ();
      try {
        if (studentReader.read (packageObject)) {
          StudentPackage studentPackage = studentReader.getPackage (StudentPackage.class);
          Student student = studentPackage.getStudent ();
          studentSchoolId = student.getResponsibleInstitutionIdentifier ();
          if (studentSchoolId == null) {
            return;
          }

          String userEmail = getUserEmail (connection, proctorKey);
          if (userEmail == null) {
            return;
          }
          
          // get proctor's institution ID
          String apiUrl = "users?email=" + userEmail;
          User[] users = null;
          try {
            users = _trClient.getForObject (apiUrl, User[].class);
          } catch (TrApiException e) {
            if (e.isErrorExempted ()) {
               instKey.set (studentSchoolId);
               return;
           }      
           _logger.error (e.getMessage (), e);
         }
          
          if (users != null && users.length > 0) {
            RtsRecord rtsRecord = studentReader.getRtsRecord ("INST-HIERARCHY");
            for (RtsField rtsField : rtsRecord.getFields ()) {
              if (isInstitutionMatch (rtsField, users[0])) {
                instKey.set (studentSchoolId);
                break;
              }
            }
          }

        }
      } catch (RtsPackageReaderException e) {
        _logger.error (e.getMessage (), e);
      }
    }
  }

  private boolean isInstitutionMatch (RtsField rtsField, User user) {
    if (StringUtils.isBlank (rtsField.getValue ())) {
      return false;
    }
    for (RoleAssociation roleAssociation : user.getRoleAssociations ()) {
      if (rtsField.getKey ().equals (roleAssociation.getLevel ())) {
        return (rtsField.getValue ().equals (roleAssociation.getAssociatedEntityId ())) ? true : false;
      }
    }
    return false;
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.dll.api.IRtsDLL#_GetRTSAttribute_SP(AIR.Common.DB.SQLConnection,
   * java.lang.String, java.lang.Long, java.lang.String,
   * AIR.Common.Helpers._Ref, java.lang.Boolean)
   */
  @Override
  public void _GetRTSAttribute_SP (SQLConnection connection, String clientname, Long testee, String attname, _Ref<String> attValue, Boolean debug) throws ReturnStatusException {
    byte[] pkg = getStudentPackageByKeyAndClientName (connection, testee, clientname);
    String attribute = null;
    if (pkg != null) {
      IRtsPackageReader packageReader = new StudentPackageReader ();
      try {
        if (packageReader.read (pkg)) {
          attribute = packageReader.getFieldValue (attname);
          if (attribute != null) {
            attValue.set (attribute);
          }
        }
      } catch (RtsPackageReaderException e) {
        _logger.error (e.getMessage (), e);
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.dll.api.IRtsDLL#_GetRTSAttribute_SP(AIR.Common.DB.SQLConnection,
   * java.lang.String, java.lang.Long, java.lang.String,
   * AIR.Common.Helpers._Ref)
   */
  @Override
  public void _GetRTSAttribute_SP (SQLConnection connection, String clientname, Long testee, String attname, _Ref<String> attValue) throws ReturnStatusException {
    _GetRTSAttribute_SP (connection, clientname, testee, attname, attValue, false);
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.dll.api.IRtsDLL#_GetRTSEntity_SP(AIR.Common.DB.SQLConnection,
   * java.lang.String, java.lang.String, java.lang.String,
   * AIR.Common.Helpers._Ref)
   */
  @Override
  public void _GetRTSEntity_SP (SQLConnection connection, String clientname, String externalId, String entityType, _Ref<Long> entityKeyRef) throws ReturnStatusException {
    entityKeyRef.set (null);
    String entityTypeLower = entityType.toLowerCase ();
    String type = StringUtils.capitalize (entityTypeLower);
    EntityType.valueOf (type.toUpperCase ());
    final String SQL_QUERY = "select " + type + "Key from r_" + entityTypeLower + "keyid where " + type + "ID = ${externalId} and StateCode = ${stateCode} and ClientName = ${clientName} limit 1";
    SqlParametersMaps parms = (new SqlParametersMaps ()).put ("externalId", externalId).put ("stateCode", _stateCode).put ("clientName", clientname);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY, parms, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      entityKeyRef.set (record.<Long> get (type + "Key"));
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * tds.dll.api.IRtsDLL#_GetTesteeRelationships_SP(AIR.Common.DB.SQLConnection,
   * java.lang.String, java.lang.Long)
   */
  @Override
  public SingleDataResultSet _GetTesteeRelationships_SP (SQLConnection connection, String clientName, Long testee) throws ReturnStatusException {
    byte[] packageObject = getStudentPackageByKeyAndClientName (connection, testee, clientName);
    IRtsPackageReader packageReader = null;
    SingleDataResultSet singleDataResultSet = null;
    if (packageObject != null) {
      List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
      packageReader = new StudentPackageReader ();
      try {
        if (packageReader.read (packageObject)) {
          RtsRecord rtsRecord = packageReader.getRtsRecord ("ENRDIST-STUDENT");
          if (rtsRecord != null) {
              CaseInsensitiveMap<Object> result = new CaseInsensitiveMap<Object> ();
              result.put ("relationType", "District");
              result.put ("entityKey", rtsRecord.get ("entityKey"));
              result.put ("TDS_ID", "DistrictID");
              result.put ("attval", rtsRecord.get ("entityId"));
              resultList.add (result);
              
              result = new CaseInsensitiveMap<Object> ();
              result.put ("relationType", "District");
              result.put ("entityKey", rtsRecord.get ("entityKey"));
              result.put ("TDS_ID", "DistrictName");
              result.put ("attval", rtsRecord.get ("entityName"));
              resultList.add (result);
          }
          rtsRecord = packageReader.getRtsRecord ("ENRLINST-STUDENT");
          if (rtsRecord != null) {
              CaseInsensitiveMap<Object> result = new CaseInsensitiveMap<Object> ();
              result.put ("relationType", "School");
              result.put ("entityKey", rtsRecord.get ("entityKey"));
              result.put ("TDS_ID",  "SchoolID"); 
              result.put ("attval", rtsRecord.get ("entityId"));
              resultList.add (result);
              
              result = new CaseInsensitiveMap<Object> ();
              result.put ("relationType", "School");
              result.put ("entityKey", rtsRecord.get ("entityKey"));
              result.put ("TDS_ID",  "SchoolName"); 
              result.put ("attval", rtsRecord.get ("entityName"));
              resultList.add (result);
            }
          rtsRecord = packageReader.getRtsRecord ("STATE-STUDENT");
          if (rtsRecord != null) {
            CaseInsensitiveMap<Object> result = new CaseInsensitiveMap<Object> ();
            result.put ("relationType", "State");
            result.put ("entityKey", rtsRecord.get ("entityKey"));
            result.put ("TDS_ID",  "StateAbbreviation"); 
            result.put ("attval", rtsRecord.get ("entityId"));
            resultList.add (result);   
            
            result = new CaseInsensitiveMap<Object> ();
            result.put ("relationType", "State");
            result.put ("entityKey", rtsRecord.get ("entityKey"));
            result.put ("TDS_ID",  "StateName"); 
            result.put ("attval", rtsRecord.get ("entityName"));
            resultList.add (result);  
          }
        }
      } catch (RtsPackageReaderException e) {
        _logger.error (e.getMessage (), e);
      }
      packageReader = null;

      singleDataResultSet = new SingleDataResultSet ();

      singleDataResultSet.addColumn ("relationType", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      singleDataResultSet.addColumn ("entityKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      singleDataResultSet.addColumn ("TDS_ID", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      singleDataResultSet.addColumn ("attval", SQL_TYPE_To_JAVA_TYPE.VARCHAR);

      singleDataResultSet.addRecords (resultList);
    }
    return singleDataResultSet;
  }


  @Override
  public void _GetRTSRelationship_SP (SQLConnection connection, String clientname, Long testeeKey, String relationship, _Ref<Long> entityKey, _Ref<String> entityId, _Ref<String> entityName)
      throws ReturnStatusException {
    byte[] packageObject = getStudentPackageByKeyAndClientName (connection, testeeKey, clientname);
    RtsRecord rtsRecord = null;
    IRtsPackageReader packageReader = null;
    if (packageObject != null) {
      packageReader = new StudentPackageReader ();
      try {
        if (packageReader.read (packageObject)) {
          rtsRecord = packageReader.getRtsRecord (relationship);
        }
      } catch (RtsPackageReaderException e) {
        _logger.error (e.getMessage (), e);
      }
      packageReader = null;
    }
    if (rtsRecord != null) {
      entityKey.set (testeeKey);  
      entityId.set (rtsRecord.get ("entityId"));
      entityName.set (rtsRecord.get ("entityName"));
    }
  }

  @Override
  public SingleDataResultSet R_GetDistricts_SP (SQLConnection connection, String clientname) throws ReturnStatusException {

    SingleDataResultSet singleDataResultSet = null;
    String apiUrl = String.format ("districts?stateAbbreviation=%s", _stateCode);

    District[] districts = null;;
    try {
      districts = _trClient.getForObject (apiUrl, District[].class);
    } catch (TrApiException e) {
      if (e.isErrorExempted ()) {
        return null;
     }      
     _logger.error (e.getMessage (), e);
    }

    if (districts != null) {
      List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
      for (District district : districts) {
        CaseInsensitiveMap<Object> result = new CaseInsensitiveMap<Object> ();
        result.put ("RTSKey", district.getEntityId ());
        result.put ("DistrictName", district.getEntityName ());
        result.put ("DistrictID", district.getEntityId ());
        resultList.add (result);
      }
      singleDataResultSet = new SingleDataResultSet ();
      singleDataResultSet.addColumn ("RTSKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      singleDataResultSet.addColumn ("DistrictName", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      singleDataResultSet.addColumn ("DistrictID", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      singleDataResultSet.addRecords (resultList);
    }

    return singleDataResultSet;
  }

  @Override
  public SingleDataResultSet R_GetDistrictSchools_SP (SQLConnection connection, String clientname, String districtKey) throws ReturnStatusException {
    SingleDataResultSet singleDataResultSet = null;
    String apiUrl = String.format ("institutions?stateAbbreviation=%s&parentEntityId=%s&parentEntityType=DISTRICT", _stateCode, districtKey);

    Institution[] schools = null;
    try {
      schools = _trClient.getForObject (apiUrl, Institution[].class);
    } catch (TrApiException e) {
      if (e.isErrorExempted ()) {
         return null;
      }      
      _logger.error (e.getMessage (), e);
    }
    
    if (schools != null) {
      List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
      for (Institution school : schools) {
        CaseInsensitiveMap<Object> result = new CaseInsensitiveMap<Object> ();
        result.put ("RTSKey", school.getEntityId ());
        result.put ("SchoolName", school.getEntityName ());
        result.put ("SchoolID", school.getEntityId ());
        resultList.add (result);
      }
      singleDataResultSet = new SingleDataResultSet ();
      singleDataResultSet.addColumn ("RTSKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      singleDataResultSet.addColumn ("SchoolName", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      singleDataResultSet.addColumn ("SchoolID", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      singleDataResultSet.addRecords (resultList);
    }
    return singleDataResultSet;
  }

  @Override
  public SingleDataResultSet R_GetSchoolGrades_SP (SQLConnection connection, String clientname, String schoolKey) throws ReturnStatusException {
    SingleDataResultSet singleDataResultSet = null;

    String apiUrl = String.format ("institution/%s/state/%s/grades", schoolKey, _stateCode);

    String[] grades = null;
    try {
      grades = _trClient.getForObject (apiUrl, String[].class);
    } catch (TrApiException e) {
      if (e.isErrorExempted ()) {
        return null;
       }      
      _logger.error (e.getMessage (), e);
    }
    
    if (grades != null) {
      List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
      for (String grade : grades) {
        CaseInsensitiveMap<Object> result = new CaseInsensitiveMap<Object> ();
        result.put ("grade", grade);
        resultList.add (result);
      }

      singleDataResultSet = new SingleDataResultSet ();
      singleDataResultSet.addColumn ("grade", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      singleDataResultSet.addRecords (resultList);
    }

    return singleDataResultSet;
  }
 
  /**
   * 
   * @param testeeId
   * @param proctorKey
   * @return
   */
  public IRtsPackageReader getRtsPackageReader (SQLConnection connection, String testeeId, long proctorKey)  throws ReturnStatusException {
    String studentPackageStr  = null;
    try {
       //TODO: externalSsid does not work for student endpoint
        String apiUrl = String.format ("studentpackage?stateabbreviation=%s&externalId=%s", _stateCode, testeeId);
        studentPackageStr =  _trClient.getPackage (apiUrl);
     } catch (TrApiException e) {
      if (e.isErrorExempted ()) {
        return null;
       }    
       _logger.error (e.getMessage (), e);
       return null;
    }
    IRtsPackageReader reader = new StudentPackageReader();
    try {
      if (reader.read (studentPackageStr)) {
        StudentPackage studentPackage = reader.getPackage (StudentPackage.class);
        Student student = studentPackage.getStudent ();
        String studentSchoolId = student.getResponsibleInstitutionIdentifier ();
        if (studentSchoolId == null) {
          return null;
        }

        String userEmail = getUserEmail (connection, proctorKey);
        if (userEmail == null) {
          return null;
        }
        
        // get proctor's institution ID
        String apiUrl = "users?email=" + userEmail;
        User[] users = null;
        try {
          users = _trClient.getForObject (apiUrl, User[].class);
        } catch (TrApiException e) {
           _logger.error (e.getMessage (), e);
           return null;
       }
        
        if (users != null && users.length > 0) {
          RtsRecord rtsRecord = reader.getRtsRecord ("INST-HIERARCHY");
          for (RtsField rtsField : rtsRecord.getFields ()) {
            if (isInstitutionMatch (rtsField, users[0])) {
                return reader;
            }
          }
        }
        
        
      
      }
    } catch (RtsPackageReaderException e) {
      _logger.error (e.getMessage (), e);
    }
    return null;
  }
  
  @Override
  public SingleDataResultSet getSchoolStudents (SQLConnection connection, String clientname, String schoolKey, String grade, String firstName, String lastName) throws ReturnStatusException {
  
    SingleDataResultSet singleDataResultSet = null;

    StringBuilder sb = new StringBuilder ( String.format ("students?stateAbbreviation=%s&institutionIdentifier=%s", _stateCode, schoolKey));
    if (!StringUtils.isBlank (grade) && !grade.equalsIgnoreCase ("all") ) {
      sb.append ("&gradeLevelWhenAssessed=").append (grade);
    }
    if (!StringUtils.isBlank (firstName)) {
      sb.append ("&firstName=").append (UrlEncoderDecoderUtils.encode(firstName));
    }
    if (!StringUtils.isBlank (lastName)) {
      sb.append ("&lastName").append (UrlEncoderDecoderUtils.encode(lastName));
    }

    String apiUrl =  sb.toString ();

    SchoolStudent[] schoolStudents = null;;
    try {
      schoolStudents = _trClient.getForObject (apiUrl, SchoolStudent[].class);
    } catch (TrApiException e) {
      if (e.isErrorExempted ()) {
        return null;
      }  
      _logger.error (e.getMessage (), e);
    }

    if (schoolStudents != null && schoolStudents.length > 0) {
      List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
      for (SchoolStudent student : schoolStudents) {
        CaseInsensitiveMap<Object> result = new CaseInsensitiveMap<Object> ();
        result.put ("rtsKey", student.getEntityId ());
        result.put ("SSID", student.getEntityId ());
        result.put ("FirstName", student.getFirstName ());
        result.put ("LastName", student.getLastName ());
        result.put ("Grade", student.getGradeLevelWhenAssessed ());
        resultList.add (result);
      }
      singleDataResultSet = new SingleDataResultSet ();
      singleDataResultSet.addColumn ("rtsKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      singleDataResultSet.addColumn ("SSID", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      singleDataResultSet.addColumn ("FirstName", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      singleDataResultSet.addColumn ("LastName", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      singleDataResultSet.addColumn ("Grade", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      singleDataResultSet.addRecords (resultList);
    }
    return singleDataResultSet;
  }

  @Override
  public SingleDataResultSet GetRTSUser_SP (SQLConnection connection, String clientName, String userId) throws ReturnStatusException {
    final String SQL_QUERY = "select * from tbluser where userId = ${userId}";
    SqlParametersMaps parms = (new SqlParametersMaps ()).put ("userId", userId);
    return executeStatement (connection, SQL_QUERY, parms, false).getResultSets ().next ();
  }

  @Override
  public SingleDataResultSet P_GetAllTests_SP (SQLConnection connection, String clientname, int sessionType, Long proctorKey) throws ReturnStatusException {
    Map<String, Object> testKeyMap = new HashMap<> ();
    DbResultRecord proctorRecord = findProctorByKeyAndClientName (connection, proctorKey, clientname);
    String testTypes = null;
    if (proctorRecord != null) {
      IRtsPackageReader packageReader = new ProctorPackageReader ();
      try {
          byte[] packageObject =  getProctorPackageByKeyAndClientName (connection, proctorKey, clientname);
          if (packageObject!= null) {
            if (packageReader.read (packageObject)) {
              RtsTable testList = packageReader.getRtsTable ("Tests");
              testTypes = proctorRecord.<String> get ("testType");  
              for (RtsRecord packageRecord : testList.getRecords ()) {
                testKeyMap.put (packageRecord.get ("TestKey").trim (), null);
              }
            }
          }

      } catch (NumberFormatException | RtsPackageReaderException e) {
        _logger.error (e.getMessage (), e);
      }
      packageReader = null;
    }
    SingleDataResultSet resultSet = null;

    if(testKeyMap.size () > 0) {
      String testTypesStr = listToQuotedString (testTypes); 
      final String SQL_QUERY = "select distinct P.TestID, P.GradeText, P.SubjectName as Subject, P.label as DisplayName, "
          + " P.SortOrder, P.AccommodationFamily,P.IsSelectable, M.IsSegmented, M.TestKey"
          + " from ${ConfigDB}.client_testproperties P, ${ConfigDB}.client_testmode M, ${ItemBankDB}.tblsetofadminsubjects S "
          + " where P.clientname = ${clientname}  and M.clientname = ${clientname} and M.testID = P.testID and M.testkey = S._Key"
          + " and S.testtype in (${testTypesStr})"
          + " order by sortorder";
      Map<String, String> unquotedparms = new HashMap<String, String> ();
      unquotedparms.put ("testTypesStr", testTypesStr);
      String query = fixDataBaseNames (SQL_QUERY, unquotedparms);
       
      SqlParametersMaps parms = new SqlParametersMaps ().put ("clientname", clientname);
      resultSet = executeStatement (connection, fixDataBaseNames (query), parms, false).getResultSets ().next ();
      Iterator<DbResultRecord> records = resultSet.getRecords ();
      while (records.hasNext ()) {
        DbResultRecord record = records.next ();
        String key = record.<String> get ("testkey");
        if (testKeyMap.containsKey (key) == false)
          records.remove ();
      }
    } else {
      _logger.warn ("Proctor Package has no tests");
    }
    return resultSet;
  }
  
  private String listToQuotedString (String theLine) {

    String retLine = "";
    String splits[] = StringUtils.split (theLine, ",");
    if (splits.length == 0)
      return retLine;

    for (String split : splits) {
      if (retLine.isEmpty ())
        retLine = String.format ("'%s'", split);
      else
        retLine += String.format (", '%s'", split);
    }
    return retLine;
  }

  @Override
  public SingleDataResultSet GetRTSUserRoles_SP (SQLConnection connection, String clientName, Long userKey) throws ReturnStatusException {
    return GetRTSUserRoles_SP (connection, clientName, userKey,  null) ;
  }
  
  @Override
  public SingleDataResultSet GetRTSUserRoles_SP (SQLConnection connection, String clientName, Long userKey,  Integer sessionType) throws ReturnStatusException {

    SingleDataResultSet resultSet = null;
     User[] users = null;
     if (userKey != null) {
       String userEmail = getUserEmail (connection, userKey);
       if (userEmail != null) {
         String apiUrl = "users?email=" +  userEmail; 
          try {
            users = _trClient.getForObject (apiUrl, User[].class);
          } catch (TrApiException e) {
            if (e.isErrorExempted ()) {
              return null;
            }  
            _logger.error (e.getMessage (), e);
          }
       }
     }

     if (users == null || users.length == 0) {
       List<CaseInsensitiveMap<Object>> resultlist = new ArrayList<CaseInsensitiveMap<Object>> ();
       CaseInsensitiveMap<Object> rcrd = new CaseInsensitiveMap<Object> ();
       rcrd.put ("role", null);
       rcrd.put ("status", "failed");
       rcrd.put ("reason", "Incorrect ID or password");
       rcrd.put ("context", "R_LoginReportUser");
       rcrd.put ("argstring", null);
       rcrd.put ("delimiter", null);
       resultlist.add (rcrd);

       resultSet = new SingleDataResultSet ();
       resultSet.addColumn ("role", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
       resultSet.addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
       resultSet.addColumn ("reason", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
       resultSet.addColumn ("context", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
       resultSet.addColumn ("argstring", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
       resultSet.addColumn ("delimiter", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
       resultSet.addRecords (resultlist);
       return resultSet;
     }
     List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();

     for (RoleAssociation roleAssociation : users[0].getRoleAssociations ()) {
         CaseInsensitiveMap<Object> result = new CaseInsensitiveMap<Object> ();
         result.put ("rtsKey", userKey);
         result.put ("TDS_Role", roleAssociation.getRole ());
         result.put ("InstitutionKey", roleAssociation.getAssociatedEntityId ());
         result.put ("InstitutionName", roleAssociation.getAssociatedEntityName ());
         
         result.put ("InstitutionID", roleAssociation.getAssociatedEntityId ());
         result.put ("InstitutionType", roleAssociation.getLevel ());
         resultList.add (result);
     }

     resultSet = new SingleDataResultSet ();
     resultSet.addColumn ("rtsKey", SQL_TYPE_To_JAVA_TYPE.BIGINT);
     resultSet.addColumn ("TDS_Role", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
     resultSet.addColumn ("InstitutionKey", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
     resultSet.addColumn ("InstitutionName", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
     resultSet.addColumn ("InstitutionID", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
     resultSet.addColumn ("InstitutionType", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
     resultSet.addRecords (resultList);
  
     return resultSet;
   
  }
  

  @Override
  public SingleDataResultSet T_GetRTSAccommodations_SP (SQLConnection connection, String clientname, Long testee) throws ReturnStatusException {
    SingleDataResultSet singleDataResultSet = null;
    byte[] packageObject = getStudentPackageByKeyAndClientName (connection, testee, clientname);
    if (packageObject != null) {

      IRtsPackageReader packageReader = new StudentPackageReader ();
      try {
        if (packageReader.read (packageObject)) {
          List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
          RtsTable accList = packageReader.getRtsTable ("--ACCOMMODATIONS--");
          singleDataResultSet = new SingleDataResultSet ();
          for (RtsRecord accs : accList.getRecords ()) {
            CaseInsensitiveMap<Object> result = new CaseInsensitiveMap<Object> ();
            for (RtsField accFields : accs.getFields ()) {
              result.put (accFields.getKey (), accFields.getValue ());
            }
            resultList.add (result);
          }
          singleDataResultSet.addColumn ("Subject", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
          singleDataResultSet.addColumn ("AccCode", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
          singleDataResultSet.addRecords (resultList);
        }
      } catch (NumberFormatException | RtsPackageReaderException e) {
        // TODO Auto-generated catch block
        _logger.error (e.getMessage (), e);
      }
      packageReader = null;

    }
    return singleDataResultSet;
  }

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
  // TODO this is a hacked version. It does nothing
  public void _ValidateAsProctor_SP (SQLConnection connection, String clientName, Long userKey, _Ref<Long> userEntityRef, _Ref<Long> roleEntityRef, _Ref<String> roleRef, Integer sessionType)
      throws ReturnStatusException {

    userEntityRef.set (null);
    roleEntityRef.set (null);
    roleRef.set (null);
    return;
    // Integer certify = null;
    // String certificate = null;
    // Long entityKey = null;
    // _Ref<String> certifiedRef = new _Ref<> ();
    // String rts = _commonDll.getExternsColumnByClientName (connection,
    // clientName, "proctorDB");

    // String SQL_QUERY1 =
    // "select IsOn as certify, Description as certificate from ${ConfigDB}.client_systemflags where ClientName = ${clientName} and AuditObject = ${CertifyProctor};";
    // String finalQuery = fixDataBaseNames (SQL_QUERY1);
    // SqlParametersMaps parms1 = new SqlParametersMaps ().put ("clientName",
    // clientName).put ("CertifyProctor", "CertifyProctor");
    // SingleDataResultSet result = executeStatement (connection, finalQuery,
    // parms1, false).getResultSets ().next ();
    // DbResultRecord record = result.getCount () > 0 ? result.getRecords
    // ().next () : null;
    // if (record != null) {
    // certify = record.<Integer> get ("certify");
    // certificate = record.<String> get ("certificate");
    // }
    // if (rts == null) {
    // _logger.error (String.format
    // ("Unable to locate RTS database for clienName: %s", clientName));
    // }
    // DataBaseTable rolesTable = getDataBaseTable ("roles").addColumn ("role",
    // SQL_TYPE_To_JAVA_TYPE.VARCHAR, 28).addColumn ("roleEntity",
    // SQL_TYPE_To_JAVA_TYPE.BIGINT)
    // .addColumn ("userEntity", SQL_TYPE_To_JAVA_TYPE.BIGINT);
    // connection.createTemporaryTable (rolesTable);
    // Map<String, String> unquotedParms = new HashMap<String, String> ();
    // unquotedParms.put ("rolesTableName", rolesTable.getTableName ());

    // final String SQL_INSERT =
    // "insert into ${rolesTableName} (role, roleEntity, userEntity) select role, RoleEntity, UserEntity from  ${rts}.tds_userroles(${userkey});";
    // SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("userkey",
    // userKey);
    // Map<String, String> unquotedParms1 = new HashMap<> ();
    // unquotedParms1.put ("rts", rts);
    // unquotedParms1.put ("rolesTableName", rolesTable.getTableName ());
    // int insertedCnt = executeStatement (connection, fixDataBaseNames
    // (SQL_INSERT, unquotedParms1), parms3, false).getUpdateCount ();

    // final String SQL_QUERY3 =
    // "select R.role as role, R.userEntity as userEntity, R.roleEntity as roleEntity from ${ConfigDB}.client_rtsroles S, ${rolesTableName} R "
    // +
    // " where S.RTS_Role = R.role and S.TDS_Role = ${Proctor} and S.ClientName = ${clientname} and sessionType = ${sessionType} limit 1;";
    // finalQuery = fixDataBaseNames (SQL_QUERY3);
    // SqlParametersMaps parms4 = new SqlParametersMaps ().put ("clientName",
    // clientName).put ("Proctor", "Proctor").put ("sessionType", sessionType);
    // result = executeStatement (connection, fixDataBaseNames (finalQuery,
    // unquotedParms), parms4, false).getResultSets ().next ();
    // record = result.getCount () > 0 ? result.getRecords ().next () : null;
    // if (record != null) {
    // roleRef.set (record.<String> get ("role"));
    // userEntityRef.set (record.<Long> get ("userEntity"));
    // roleEntityRef.set (record.<Long> get ("roleEntity"));
    // }
    // if (roleRef.get () == null) {
    // return;
    // }
    // if (DbComparator.isEqual (certify, 0) || certificate == null) {
    // return;
    // }
    // if (userEntityRef.get () == null) {
    // return;
    // }
    // _GetRTSAttribute_SP (connection, clientName, userEntityRef.get (),
    // certificate, certifiedRef);
    // if (certifiedRef == null || certifiedRef.get ().startsWith ("Y") ==
    // false) {
    // userEntityRef.set (null);
    // roleEntityRef.set (null);
    // roleRef.set (null);
    // }
    // connection.dropTemporaryTable (rolesTable);
  }

  public SingleDataResultSet P_ValidateProctor_SP (SQLConnection connection, String clientName, UUID browserKey, String proctorId) throws ReturnStatusException {
    return P_ValidateProctor_SP (connection, clientName, browserKey, proctorId, null, 0, true, 0);
  }

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
  // TODO This is a hacked version
  public SingleDataResultSet P_ValidateProctor_SP (SQLConnection connection, String clientName, UUID browserKey, String proctorId, String password, Integer oppSessions, Boolean ignorepw,
      Integer sessionType) throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);
    // Boolean auditProc = null;
    // Integer duration = null;
    SingleDataResultSet result = null;

    // auditProc = _commonDll.AuditProc_FN (connection, "P_ValidateProctor");
    try {
      _Ref<Long> userKey = new _Ref<> ();
      _Ref<String> fullname = new _Ref<> ();
      _Ref<String> rtsPassword = new _Ref<> ();

      _Ref<Long> userEntityRef = new _Ref<> ();
      _Ref<Long> roleEntityRef = new _Ref<> ();
      _Ref<String> roleRef = new _Ref<> ();
      Long entityKey = null;
     
      // TODO should we also check clientname here?
      final String SQL_QUERY1 = "select userkey, fullname from tbluser where userid = ${proctorID} ";
      // final String SQL_QUERY1 =
      // "select _Key as userKey, fullname as fullname, password as rtspassword from ${rts}.tbluser where username = ${proctorID}"
      // +
      // " and (${ignorepw} = 1 or password = ${password}) and active = 1 and hasAcknowledged = 1;";
      SqlParametersMaps parms1 = new SqlParametersMaps ().put ("proctorID", proctorId);
 
      result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
      DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        userKey.set (record.<Long> get ("userkey"));
        fullname.set (record.<String> get ("fullname"));
        rtsPassword.set (null);
        //entityKey = record.<Long> get ("entityKey");
      }
      if (userKey.get () == null) {
        _commonDll._LogDBLatency_SP (connection, "P_ValidateProctor", starttime, null, true, null, null, null, clientName, null);
        return _commonDll._ReturnError_SP (connection, clientName, "P_ValidateProctor", "Unable to log you in. Please check your account in UMS to be sure it is fully activated.");
      }

      // EF ignore modified _ValidateAsProctor as it does nothing, just not to
      // break compilation
      _ValidateAsProctor_SP (connection, clientName, userKey.get (), userEntityRef, roleEntityRef, roleRef, sessionType);
      // set userEntityKey to value we got from new session db tbluser table,
      // rather than _ValidateAsProctor
      /*
       * userEntityRef.set (entityKey);
       * 
       * if (userEntityRef.get () == null) { _commonDll._LogDBLatency_SP
       * (connection, "P_ValidateProctor", starttime, null, true, null, null,
       * null, clientName, null); return _commonDll._ReturnError_SP (connection,
       * clientName, "P_ValidateProctor", "User does not have permission"); }
       */
      if (DbComparator.notEqual (oppSessions, 0)) {
        // pause all sessions whose date-end is before now
        _proctorDll.P_PauseAllSessions_SP (connection, clientName, userKey.get (), browserKey, 1, 0, sessionType);
        // -- we need to ensure consistent state in case of abend by proctor
        // application.
        // -- '1' = Exempt sessions whose dateend is current to prevent
        // dislodging test opportunities in event of proctor machine failure
        // -- '0' = suppress reporting by procedure

        // -- 'open' all date-relevant sessions this proctor, suppress output
        _proctorDll.P_ResumeAllSessions_SP (connection, clientName, userKey.get (), browserKey, 1, sessionType);
      }
      List<CaseInsensitiveMap<Object>> resultlist = new ArrayList<CaseInsensitiveMap<Object>> ();
      CaseInsensitiveMap<Object> rcrd = new CaseInsensitiveMap<Object> ();
      rcrd.put ("status", "success");
      rcrd.put ("userKey", userKey.get ());
      rcrd.put ("entityKey", 0L);
      rcrd.put ("Fullname", fullname.get ());
      rcrd.put ("rtspassword", null);
      rcrd.put ("role", roleRef.get ());
      rcrd.put ("reason", null);
      resultlist.add (rcrd);

      result = new SingleDataResultSet ();
      result.addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("userKey", SQL_TYPE_To_JAVA_TYPE.BIGINT);
      result.addColumn ("entityKey", SQL_TYPE_To_JAVA_TYPE.BIGINT);
      result.addColumn ("Fullname", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("rtspassword", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("role", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("reason", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addRecords (resultlist);

    } catch (Exception e) {
      String msg2 = String.format ("%sfor proctor ID%s ", e.getMessage (), proctorId);
      _commonDll._LogDBError_SP (connection, "P_ValidateProctor", msg2, null, null, null, null, clientName, null);
    }
    _commonDll._LogDBLatency_SP (connection, "P_ValidateProctor", starttime, null, true, null, null, null, clientName, null);

    return result;
  }


  /**
   * Creates a new student record.
   * 
   * @param connection
   * @param entityType
   * @param key
   * @param clientName
   * @param xmlPackage
   * @return number of records created
   * @throws ReturnStatusException
   */
  public int createStudent (SQLConnection connection, Long key, String clientName, String xmlPackage, List<TestType> testTypes) throws ReturnStatusException {
    IRtsPackageWriter<StudentPackage> writer = new StudentPackageWriter ();
    try {
      writer.writeObject (xmlPackage);
      return create (connection, EntityType.STUDENT, key, clientName, writer.getInputStream (), writer.getObject ().getVersion (), testTypes);
    } catch (RtsPackageWriterException e) {
      _logger.error (e.getMessage (), e);
      throw new ReturnStatusException ("could not create student " + e.getMessage ());
    }
  }

  /**
   * Creates a new proctor record.
   * 
   * @param connection
   * @param entityType
   * @param key
   * @param clientName
   * @param xmlPackage
   * @return number of records created
   * @throws ReturnStatusException
   * @throws RtsPackageWriterException
   */
  public int createProctor (SQLConnection connection, Long key, String clientName, String xmlPackage, List<TestType> testTypes) throws ReturnStatusException {
    IRtsPackageWriter<ProctorPackage> writer = new ProctorPackageWriter ();
    try {
      writer.writeObject (xmlPackage);
      return create (connection, EntityType.PROCTOR, key, clientName, writer.getInputStream (), writer.getObject ().getVersion (), testTypes);
    } catch (RtsPackageWriterException e) {
      _logger.error (e.getMessage (), e);
      throw new ReturnStatusException ("could not create proctor " + e.getMessage ());
    }
  }

  /**
   * Creates a new record.
   * 
   * @param connection
   * @param entityType
   * @param key
   * @param clientName
   * @param xmlPackage
   * @return number of records created
   * @throws ReturnStatusException
   */
  private int create (SQLConnection connection, EntityType entityType, Long key, String clientName, InputStream packageStream, String version, List<TestType> testTypes) throws ReturnStatusException {
    String SQL_INSERT = null;
    if (entityType == EntityType.PROCTOR) {
          SQL_INSERT = "insert into r_proctorpackage (ProctorKey, ClientName, Package, Version, DateCreated, TestType) values (?, ?, ?, ?, now(), ?)";
      } else {
          SQL_INSERT = "insert into r_studentpackage (StudentKey, ClientName, Package, Version, DateCreated) values (?, ?, ?, ?, now())";
      }
    try (PreparedStatement preparedStatement = connection.prepareStatement (SQL_INSERT)) {
      preparedStatement.setLong (1, key);
      preparedStatement.setString (2, clientName);
      preparedStatement.setBinaryStream (3, packageStream);
      preparedStatement.setString (4, version);
      if (entityType == EntityType.PROCTOR) {
        preparedStatement.setString (5, StringUtils.join(testTypes,","));
      }
      preparedStatement.execute ();
      return 1;
    } catch (SQLException e) {
       _logger.error (e.getMessage ()+"; SQLState: "+e.getSQLState (), e);
       throw new ReturnStatusException ("could not create " + entityType.getValue () +  ": "  + e.getMessage ());
    }
  }

  /**
   * Updates IsCurrent for all an Entity records to false and then creates a new
   * record.
   * 
   * @param connection
   * @param entityType
   * @param key
   * @param clientName
   * @param xmlPackage
   * @return number of records created
   * @throws ReturnStatusException
   */
  private int createAndUpdateIsCurrent (SQLConnection connection, EntityType entityType, Long key, String clientName, String xmlPackage, List<TestType> testType) throws ReturnStatusException {
    int insertCount = 0;
    boolean isCurrent = false;
    try {
      updateIsCurrent (connection, entityType, key, clientName, isCurrent);
    } catch (ReturnStatusException e) {
      _logger.error (e.getMessage (), e);
      throw new ReturnStatusException ("Could not update current package record " + e.getMessage ());
    }
    try {
      if (entityType == EntityType.STUDENT) {
        insertCount = createStudent (connection, key, clientName, xmlPackage, null);
      } else {
        insertCount = createProctor (connection, key, clientName, xmlPackage, testType);
      }
    } catch (ReturnStatusException e) {
      _logger.error (e.getMessage (), e);
      throw new ReturnStatusException ("Could not insert package " + e.getMessage ());
    }
    return insertCount;
  }

  /**
   * Updates IsCurrent for all Student records to false and then creates a new
   * Student record.
   * 
   * @param connection
   * @param key
   * @param clientName
   * @param xmlPackage
   * @return number of records created
   * @throws ReturnStatusException
   */
  public int createAndUpdateStudentIsCurrent (SQLConnection connection, Long key, String clientName, String xmlPackage) throws ReturnStatusException {

    Date today = _dateUtil.getDateWRetStatus (connection);
    int ret = createAndUpdateIsCurrent (connection, EntityType.STUDENT, key, clientName, xmlPackage, null);
    _commonDll._LogDBLatency_SP (connection, "createAndUpdateStudentIsCurrent", today, key, true, null, null, null, clientName, null);

    return ret;
  }

  /**
   * Updates IsCurrent for all Proctor records to false and then creates a new
   * Proctor record.
   * 
   * @param connection
   * @param key
   * @param clientName
   * @param xmlPackage
   * @param isCurrent
   * @return number of records created
   * @throws ReturnStatusException
   */
  public int createAndUpdateProctorIsCurrent (SQLConnection connection, Long key, String clientName, String xmlPackage, List<TestType> testType) throws ReturnStatusException {
    return createAndUpdateIsCurrent (connection, EntityType.PROCTOR, key, clientName, xmlPackage, testType);
  }

  /**
   * Updates all records of an Entity's IsCurrent field if the value is
   * different from the provided value
   * 
   * @param connection
   * @param entityType
   * @param key
   * @param clientName
   * @param isCurrent
   * @return number of records updated.
   * @throws ReturnStatusException
   */
  private int updateIsCurrent (SQLConnection connection, EntityType entityType, Long key, String clientName, boolean isCurrent) throws ReturnStatusException {
    final String SQL_UPDATE = "update " + getTableName (entityType) + " set isCurrent = ${isCurrent} where " + entityType.getValue ()
        + "Key = ${key} and ClientName = ${clientName} and isCurrent <> ${isCurrent}";
    SqlParametersMaps params = new SqlParametersMaps ();
    params.put ("key", key);
    params.put ("clientName", clientName);
    params.put ("isCurrent", isCurrent);
    int updateCount = executeStatement (connection, SQL_UPDATE, params, false).getUpdateCount ();
    return updateCount;
  }
  

  @Override
  public Long getOrCreateStudentKey (SQLConnection connection, String SSID, String clientName) throws ReturnStatusException {

    Date today = _dateUtil.getDateWRetStatus (connection);
    // START: If we already have a key corresponding to this ID and client,
    // return it.
    final String SQL_QUERY = "SELECT studentkey FROM r_studentkeyid WHERE studentid=${SSID} AND statecode=${stateCode} AND clientname=${clientname}";
    final SqlParametersMaps parms = new SqlParametersMaps ()
        .put ("SSID", SSID).put ("stateCode", _stateCode).put ("clientname", clientName);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY, parms, false).getResultSets ().next ();

    if (result.getRecords ().hasNext ()) {
      DbResultRecord record = result.getRecords ().next ();
      _commonDll._LogDBLatency_SP (connection, "getOrCreateStudentKey", today, 0L, true, null, null, null, clientName, null);

      return record.get ("studentkey");
    }
    // END: If we already have a key corresponding to this ID and client, return
    // it.
    parms.put("clientName", clientName);
    // START: If we have never seen this ID and client before, create a new ID
    final String SQL_INSERT = "INSERT INTO r_studentkeyid( studentid, statecode, clientname ) VALUES( ${SSID}, ${stateCode}, ${clientName});";
    executeStatement (connection, SQL_INSERT, parms, false);
    // END: If we have never seen this ID and client before, create a new ID

    // START: return the newly created value
    result = executeStatement (connection, SQL_QUERY, parms, false).getResultSets ().next ();
    _commonDll._LogDBLatency_SP (connection, "getOrCreateStudentKey", today, 0L, true, null, null, null, clientName, null);

    return result.getRecords ().next ().get ("studentkey");
    // END: return the newly created value

  }

  @Override
  public Map<String, String> getTesteeAttributes (SQLConnection connection, String clientName, long testeeKey) {
    Map<String, String> attributes = null;
    try {
      byte[] packageObject = getStudentPackageByKeyAndClientName (connection, testeeKey, clientName);
      if (packageObject != null) {
        IRtsPackageReader studentReader = new StudentPackageReader ();
        if (studentReader.read (packageObject)) {
          StudentPackage studentPackage = studentReader.getPackage (StudentPackage.class);
          Student student = studentPackage.getStudent ();
          attributes = new HashMap<> ();

          attributes.put ("LastName", studentReader.getFieldValue ("LglLNm"));
          attributes.put ("FirstName", studentReader.getFieldValue ("LglFNm"));
          attributes.put ("MiddleName", student.getMiddleName ());
          attributes.put ("SSID", student.getStudentIdentifier ());
          attributes.put ("DOB", studentReader.getFieldValue ("BirthDtTxt"));
          attributes.put ("Gender", studentReader.getFieldValue ("Gndr"));
          attributes.put ("HispanicOrLatinoEthnicity", student.getHispanicOrLatinoEthnicity ());
          attributes.put ("AmericanIndianOrAlaskaNative", student.getAmericanIndianOrAlaskaNative ());
          attributes.put ("Asian", student.getAsian ());
          attributes.put ("BlackOrAfricanAmerican", student.getBlackOrAfricanAmerican ());
          attributes.put ("White", student.getWhite ());
          attributes.put ("NativeHawaiianOrOtherPacificIslander", student.getNativeHawaiianOrOtherPacificIslander ());
          attributes.put ("DemographicRaceTwoOrMoreRaces", student.getDemographicRaceTwoOrMoreRaces ());
          attributes.put ("IDEAIndicator", student.getIDEAIndicator ());
          attributes.put ("LEPStatus", student.getLEPStatus ());
          attributes.put ("Section504Status", student.getSection504Status ());

          attributes.put ("EconomicDisadvantageStatus", student.getEconomicDisadvantageStatus ());
          // SB-512
          attributes.put ("GradeLevelWhenAssessed", student.getGradeLevelWhenAssessed ());
          if (student.getLEPExitDate () == null) { 
            attributes.put ("LEPExitDate", "");
          } else {
            attributes.put ("LEPExitDate", DateTime.getFormattedDate (student.getLEPExitDate (), "yyyy-MM-dd"));
          }
        }
      }
    } catch (RtsPackageReaderException | ReturnStatusException e) {
      _logger.error (e.getMessage (), e);
    }
    return attributes;
  }

  @Override
  public SingleDataResultSet getTesteeAttributesAsSet (SQLConnection connection, String clientName, long testeeKey) {
    
    SingleDataResultSet result1 = new SingleDataResultSet ();
    Map<String, String> attributes = null;
    
    List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
    try {
      byte[] packageObject = getStudentPackageByKeyAndClientName (connection, testeeKey, clientName);
      if (packageObject != null) {
        IRtsPackageReader studentReader = new StudentPackageReader ();
        if (studentReader.read (packageObject)) {
          StudentPackage studentPackage = studentReader.getPackage (StudentPackage.class);
          Student student = studentPackage.getStudent ();
                 
          CaseInsensitiveMap<Object> rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "LastName");         
          rcd.put ("attval", studentReader.getFieldValue ("LglLNm"));         
          resultList.add (rcd);
          
          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "FirstName");         
          rcd.put ("attval", studentReader.getFieldValue ("LglFNm"));         
          resultList.add (rcd);
  
          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "MiddleName");         
          rcd.put ("attval", student.getMiddleName ());          
          resultList.add (rcd);
  
          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "SSID");         
          rcd.put ("attval", student.getStudentIdentifier ());          
          resultList.add (rcd);
          
          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "DOB");         
          rcd.put ("attval", studentReader.getFieldValue ("BirthDtTxt"));          
          resultList.add (rcd);
                    
          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "Gender");         
          rcd.put ("attval", studentReader.getFieldValue ("Gndr"));          
          resultList.add (rcd);
          
          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "HispanicOrLatinoEthnicity");         
          rcd.put ("attval", student.getHispanicOrLatinoEthnicity ());          
          resultList.add (rcd);
          
          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "AmericanIndianOrAlaskaNative");         
          rcd.put ("attval", student.getAmericanIndianOrAlaskaNative ());          
          resultList.add (rcd);
          
          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "Asian");         
          rcd.put ("attval", student.getAsian ());          
          resultList.add (rcd);
          
          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "BlackOrAfricanAmerican");         
          rcd.put ("attval", student.getBlackOrAfricanAmerican ());          
          resultList.add (rcd);
          
          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "White");         
          rcd.put ("attval", student.getWhite ());          
          resultList.add (rcd);
          
          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "NativeHawaiianOrOtherPacificIslander");         
          rcd.put ("attval", student.getNativeHawaiianOrOtherPacificIslander ());          
          resultList.add (rcd);
          
          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "DemographicRaceTwoOrMoreRaces");         
          rcd.put ("attval", student.getDemographicRaceTwoOrMoreRaces ());          
          resultList.add (rcd);
          
          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "IDEAIndicator");         
          rcd.put ("attval", student.getIDEAIndicator());          
          resultList.add (rcd);
          
          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "LEPStatus");         
          rcd.put ("attval", student.getLEPStatus());          
          resultList.add (rcd);
          
          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "Section504Status");         
          rcd.put ("attval", student.getSection504Status());          
          resultList.add (rcd);
  
          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "EconomicDisadvantageStatus");         
          rcd.put ("attval", student.getEconomicDisadvantageStatus());          
          resultList.add (rcd);
          
          // SB-512
          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "GradeLevelWhenAssessed");         
          rcd.put ("attval", student.getGradeLevelWhenAssessed());          
          resultList.add (rcd);
          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "LEPExitDate");             
          if (student.getLEPExitDate () == null) {         
            rcd.put ("attval", "");                              
          } else {      
            rcd.put ("attval", DateTime.getFormattedDate (student.getLEPExitDate (), "yyyy-MM-dd"));          
          }
          resultList.add (rcd); 
          
          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "AlternateSSID");         
          rcd.put ("attval", student.getAlternateSSID ());          
          resultList.add (rcd);

          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "MigrantStatus");   
          if (student.getMigrantStatus () == null) {        
            rcd.put ("attval", "");         
          } else {
            rcd.put ("attval", student.getMigrantStatus ());                 
          }
          resultList.add (rcd);
          
          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "LanguageCode");   
          if (student.getLanguageCode () == null) {        
            rcd.put ("attval", "");         
          } else {
            rcd.put ("attval", student.getLanguageCode ());                 
          }
          resultList.add (rcd);

          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "EnglishLanguageProficiencLevel");   
          if (student.getEnglishLanguageProficiencyLevel () == null) {        
            rcd.put ("attval", "");         
          } else {
            rcd.put ("attval", student.getEnglishLanguageProficiencyLevel ());                 
          }
          resultList.add (rcd);

          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "FirstEntryDateIntoUSSchool");   
          if (student.getFirstEntryDateIntoUSSchool () == null) {        
            rcd.put ("attval", "");         
          } else { 
            rcd.put ("attval", DateTime.getFormattedDate (student.getFirstEntryDateIntoUSSchool (), "yyyy-MM-dd"));                 
          }
          resultList.add (rcd);          

          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "LimitedEnglishProficiencyEntryDate");   
          if (student.getLimitedEnglishProficiencyEntryDate () == null) {        
            rcd.put ("attval", "");         
          } else {
            rcd.put ("attval", DateTime.getFormattedDate (student.getLimitedEnglishProficiencyEntryDate (), "yyyy-MM-dd"));                 
          }
          resultList.add (rcd);

          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "TitleIIILanguageInstructionProgramType");   
          if (student.getTitleIIILanguageInstructionProgramType () == null) {        
            rcd.put ("attval", "");         
          } else {
            rcd.put ("attval", student.getTitleIIILanguageInstructionProgramType ());                 
          }
          resultList.add (rcd);
          
          rcd = new CaseInsensitiveMap<Object> ();
          rcd.put ("tds_id", "PrimaryDisabilityType");   
          if (student.getPrimaryDisabilityType () == null) {        
            rcd.put ("attval", "");         
          } else {
            rcd.put ("attval", student.getPrimaryDisabilityType ());                 
          }
          resultList.add (rcd);           
        }
      }
      result1.addColumn ("tds_id", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result1.addColumn ("attval", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result1.addRecords (resultList);
      
    } catch (RtsPackageReaderException | ReturnStatusException e) {
      _logger.error (e.getMessage (), e);
    } 
    return result1;
  }

  @Override
  public Map<String, String> getTesteeRelationships (SQLConnection connection, String clientName, long testeeKey) {
    Map<String, String> attributes = null;
    try {
      byte[] packageObject = getStudentPackageByKeyAndClientName (connection, testeeKey, clientName);
      if (packageObject != null) {
        IRtsPackageReader studentReader = new StudentPackageReader ();
        if (studentReader.read (packageObject)) {
          StudentPackage studentPackage = studentReader.getPackage (StudentPackage.class);
          Student student = studentPackage.getStudent ();
          attributes = new HashMap<> ();
          attributes.put ("StateName", student.getStateName ());
          attributes.put ("StateAbbreviation", student.getStateAbbreviation ());
          attributes.put ("DistrictID", student.getResponsibleDistrictIdentifier ());
          attributes.put ("DistrictName", student.getOrganizationName ());
          attributes.put ("SchoolID", student.getResponsibleInstitutionIdentifier ());
          attributes.put ("SchoolName", student.getNameOfInstitution ());
        }
      }
    } catch (RtsPackageReaderException | ReturnStatusException e) {
      _logger.error (e.getMessage (), e);
    }

    return attributes;
  }

  public String getTrTestId (SQLConnection connection, String testeeId, String testKey) {
    try {
      _Ref<Long> testeeKey = new _Ref<> ();
      _GetRTSEntity_SP(connection, _clientName, testeeId, "STUDENT",  testeeKey);
      if (testeeKey.get () != null) {
        byte[] packageObject = getStudentPackageByKeyAndClientName (connection, testeeKey.get (), _clientName);
        if (packageObject != null) {
          IRtsPackageReader studentReader = new StudentPackageReader ();
          if (studentReader.read (packageObject)) {
            RtsTable testList = studentReader.getRtsTable ("Tests");
            for (RtsRecord packageRecord : testList.getRecords ()) {
              if (packageRecord.get ("TestKey").equals(testKey)) {
                return packageRecord.get ("TestID");
              }
            }
          }
        }
      } else {
        _logger.error ("RtsPackageDLL.getTrTestId: TesteeKey not found for " + testeeId);
      }
     } catch (RtsPackageReaderException | ReturnStatusException e) {
       _logger.error (e.getMessage (), e);
     }
     return null;  
   
  }
  
  public int createUser (SQLConnection connection, String userId, String email, String fullName) throws ReturnStatusException {
    final String SQL_QUERY = "insert into tbluser (userid,email,fullname,clientname) values(${userId},${email},${fullName},${clientName});";

    SqlParametersMaps parameters = new SqlParametersMaps ();
    parameters.put ("userId", userId);
    parameters.put ("email", email);
    parameters.put ("fullName", fullName);
    parameters.put ("clientName", _clientName);
    
    int insertedCnt = executeStatement (connection, SQL_QUERY, parameters, false).getUpdateCount ();
    return insertedCnt;
  }

  public boolean userAlreadyExists (SQLConnection connection, String userId, String email) throws ReturnStatusException {
    final String SQL_QUERY1 = "select userkey, email, fullname from tbluser where userid = ${proctorID}";
    // final String SQL_QUERY1 =
    // "select _Key as userKey, fullname as fullname, password as rtspassword from ${rts}.tbluser where username = ${proctorID}"
    // +
    // " and (${ignorepw} = 1 or password = ${password}) and active = 1 and hasAcknowledged = 1;";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("proctorID", userId);

    /*
     * String rts = _commonDll.getExternsColumnByClientName (connection,
     * clientName, "proctorDB"); if (rts == null) { String msg = String.format
     * ("Unable to locate RTS for proctor %s", proctorId);
     * _commonDll._RecordSystemError_SP (connection, "P_ValidateProctor", msg);
     * return _commonDll._ReturnError_SP (connection, clientName,
     * "P_ValidateProctor", "Unable to locate RTS database"); } Map<String,
     * String> unquotedMap = new HashMap<String, String> (); unquotedMap.put
     * ("rts", rts);
     * 
     * SingleDataResultSet result = executeStatement (connection,
     * fixDataBaseNames (SQL_QUERY1, unquotedMap), parms1, false).getResultSets
     * ().next ();
     */
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;

    if (record != null) {
      String existingEmail = record.<String> get ("email");
      if (!email.equals (existingEmail)) {
        updateUserEmail(connection, userId, email);
      }
      return true;
    }
    return false;
  }
  
  /**
   * 
   * @param connection
   * @param userId
   * @param email
   * @return
   * @throws ReturnStatusException
   */
  private int updateUserEmail (SQLConnection connection, String userId, String email) throws ReturnStatusException {
    final String SQL_UPDATE = "update tbluser set email = ${email} where userId = ${userId}";
    SqlParametersMaps params = new SqlParametersMaps ();
    params.put ("email", email);
    params.put ("userId", userId);
    int updateCount = executeStatement (connection, SQL_UPDATE, params, false).getUpdateCount ();
    return updateCount;
  }
  
  /**
   * 
   * @param connection
   * @param userKey
   * @throws ReturnStatusException
   */
  private String getUserEmail (SQLConnection connection, long userKey) throws ReturnStatusException {
    final String SQL_QUERY = "select email from tbluser where userkey =  ${userKey}";
    SqlParametersMaps parms = (new SqlParametersMaps ()).put ("userKey", userKey);
    SingleDataResultSet result = executeStatement (connection, SQL_QUERY, parms, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    return (record != null) ? record.<String> get ("email") : null;
  }

 
  private String getTableName (EntityType entityType) {
    return "r_" + entityType.getValue ().toLowerCase () + "package";
  }

  /**
   * Find record for particular Entity by entity's key and ClientName.
   * 
   * @param connection
   * @param entityType
   * @param key
   * @param clientName
   * @return package
   * @throws ReturnStatusException
   */
  private DbResultRecord findByKeyAndClientName (SQLConnection connection, EntityType entityType, Long key, String clientName) throws ReturnStatusException {
    String testType =  (entityType == EntityType.PROCTOR) ? ", TestType" : "";
       
    final String SQL_SELECT = "select iscurrent, version " + testType + " from " + getTableName (entityType) + " where " + entityType.getValue () + "Key = ${key} and ClientName = ${clientName} and IsCurrent = 1 limit 1";
    SingleDataResultSet result = null;
    SqlParametersMaps params = new SqlParametersMaps ();
    params.put ("key", key);
    params.put ("clientName", clientName);
    result = executeStatement (connection, SQL_SELECT, params, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    return record;
  }

  /**
   *  Gets package for particular Entity by entity's key and ClientName.
   *  
   * @param connection
   * @param entityType
   * @param key
   * @param clientName
   * @return
   * @throws ReturnStatusException
   */
  private byte[] getPackageByKeyAndClientName (SQLConnection connection, EntityType entityType, Long key, String clientName) throws ReturnStatusException {
    final String SQL_SELECT = "select * from " + getTableName (entityType) + " where " + entityType.getValue () + "Key = ? and ClientName = ? and IsCurrent = 1 limit 1";

    try (PreparedStatement preparedStatement = connection.prepareStatement (SQL_SELECT)) {
      preparedStatement.setLong (1, key);
      preparedStatement.setString (2, clientName);
      boolean rtn = preparedStatement.execute ();
      if (rtn) {
        ColumnResultSet crs = ColumnResultSet.getColumnResultSet (preparedStatement.getResultSet ());
        if (crs != null && crs.next () == true) {
          return (byte[]) crs.getObject ("Package");
        }
      }

    } catch (SQLException exp) {
      // TODO
      _logger.error (exp.getMessage (), exp);
    }
    return null;
  }

  /**
   * Gets student package given a StudentKey and ClientName.
   * 
   * @param connection
   * @param key
   * @param clientName
   * @return
   * @throws ReturnStatusException
   */
  private byte[] getStudentPackageByKeyAndClientName (SQLConnection connection, Long key, String clientName) throws ReturnStatusException {
    return getPackageByKeyAndClientName (connection, EntityType.STUDENT, key, clientName);
  }
  

  /**
   * Find records from Proctor given a ProctorKey and ClientName.
   * 
   * @param connection
   * @param key
   * @param clientName
   * @return package
   * @throws ReturnStatusException
   */
  private DbResultRecord findProctorByKeyAndClientName (SQLConnection connection, Long key, String clientName) throws ReturnStatusException {
    return findByKeyAndClientName (connection, EntityType.PROCTOR, key, clientName);
  }

  /**
   * Find proctor package given a ProctorKey and ClientName.
   * 
   * @param connection
   * @param key
   * @param clientName
   * @return
   * @throws ReturnStatusException
   */
  private byte[] getProctorPackageByKeyAndClientName (SQLConnection connection, Long key, String clientName) throws ReturnStatusException {
    return getPackageByKeyAndClientName (connection, EntityType.PROCTOR, key, clientName);
  }

}
