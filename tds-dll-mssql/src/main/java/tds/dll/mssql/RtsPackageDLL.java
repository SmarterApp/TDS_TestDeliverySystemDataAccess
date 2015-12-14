/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.mssql;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
import tds.dll.common.rtspackage.student.data.StudentPackage;
import AIR.Common.DB.AbstractDLL;
import AIR.Common.DB.DataBaseTable;
import AIR.Common.DB.DbComparator;
import AIR.Common.DB.SQLConnection;
import AIR.Common.DB.SQL_TYPE_To_JAVA_TYPE;
import AIR.Common.DB.SqlParametersMaps;
import AIR.Common.DB.results.DbResultRecord;
import AIR.Common.DB.results.SingleDataResultSet;
import AIR.Common.Helpers.CaseInsensitiveMap;
import AIR.Common.Helpers._Ref;
import AIR.Common.Sql.AbstractDateUtilDll;
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

  /*
   * (non-Javadoc)
   * 
   * @see
   * tds.dll.api.IRtsDLL#_ValidateInstitutionMatch_SP(AIR.Common.DB.SQLConnection
   * , java.lang.String, java.lang.Long, java.lang.Long,
   * AIR.Common.Helpers._Ref)
   */
  @Override
  public void _ValidateInstitutionMatch_SP (SQLConnection connection, String clientName, Long testeeKey, Long proctorKey, _Ref<String> instKey) throws ReturnStatusException {

    byte[] packageObject = getStudentPackageByKeyAndClientName (connection, testeeKey, clientName);

    if (packageObject != null) {

      IRtsPackageReader studentReader = null;
      studentReader = new StudentPackageReader ();
      try {
        if (studentReader.read (packageObject)) {
          String fieldValue = studentReader.getFieldValue ("institutions");
        }
      } catch (RtsPackageReaderException e) {
        // TODO Auto-generated catch block
        e.printStackTrace ();
      }
      studentReader = null;
    }

    IRtsPackageReader proctorReader = null;
    Long institutionKey = null;
    /**
     * TODO while (proctorRecords.hasNext ()) { DbResultRecord record =
     * proctorRecords.next (); proctorReader = new ProctorPackageReader (); try
     * { if (proctorReader.read (record.<byte[]> get ("Package"))) { String
     * fieldValue = proctorReader.getFieldValue ("institutions"); //TODO get
     * institutions IDs institutionKey = Long.valueOf ("0"); } } catch
     * (NumberFormatException | RtsPackageReaderException e) { // TODO
     * Auto-generated catch block e.printStackTrace(); }
     * 
     * proctorReader = null; break; }
     * 
     * if (institutionKey != null) { instKey.set (institutionKey); }
     */
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
        // TODO Auto-generated catch block
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
    String type = StringUtils.capitalize (entityType.toLowerCase ());
    EntityType.valueOf (type.toUpperCase ());
    final String SQL_QUERY = "select top 1 " + type + "Key from R_" + type + "KeyID where " + type + "ID = ${externalId} and ClientName = ${clientName}";
    SqlParametersMaps parms = (new SqlParametersMaps ()).put ("externalId", externalId).put ("clientName", clientname);
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
        }
        rtsRecord = packageReader.getRtsRecord ("ENRLINST-STUDENT");
        if (rtsRecord != null) {
            CaseInsensitiveMap<Object> result = new CaseInsensitiveMap<Object> ();
            result.put ("relationType", "School");
            result.put ("entityKey", rtsRecord.get ("entityKey"));
            result.put ("TDS_ID",  "SchoolID"); 
            result.put ("attval", rtsRecord.get ("entityId"));
            resultList.add (result);
          }
        }
      } catch (RtsPackageReaderException e) {
        // TODO Auto-generated catch block
        _logger.error (e.getMessage (), e);
      }
      packageReader = null;

      singleDataResultSet = new SingleDataResultSet ();
      singleDataResultSet.addRecords (resultList);
    }
    return singleDataResultSet;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * tds.dll.api.IRtsDLL#_GetRTSRelationship_SP(AIR.Common.DB.SQLConnection,
   * java.lang.String, java.lang.Long, java.lang.String,
   * AIR.Common.Helpers._Ref, AIR.Common.Helpers._Ref, AIR.Common.Helpers._Ref)
   */
  @Override
  public void _GetRTSRelationship_SP (SQLConnection connection, String clientname, Long testee, String relationship, _Ref<Long> entityKey, _Ref<String> entityId, _Ref<String> entityName)
      throws ReturnStatusException {
    byte[] packageObject = getStudentPackageByKeyAndClientName (connection, testee, clientname);
    RtsRecord rtsRecord = null;
    IRtsPackageReader packageReader = null;
    if (packageObject != null) {
      packageReader = new StudentPackageReader ();
      try {
        if (packageReader.read (packageObject)) {
          rtsRecord = packageReader.getRtsRecord (relationship);
        }
      } catch (RtsPackageReaderException e) {
        // TODO Auto-generated catch block
        e.printStackTrace ();
      }
      packageReader = null;
    }
    if (rtsRecord != null) {
      // entityKey.set (rtsRecord.get ("entityId")); Entity Key not used
      entityId.set (rtsRecord.get ("entityId"));
      entityName.set (rtsRecord.get ("entityName"));
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.dll.api.IRtsDLL#R_GetDistricts_SP(AIR.Common.DB.SQLConnection,
   * java.lang.String)
   */
  @Override
  public SingleDataResultSet R_GetDistricts_SP (SQLConnection connection, String clientname) throws ReturnStatusException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * tds.dll.api.IRtsDLL#R_GetDistrictSchools_SP(AIR.Common.DB.SQLConnection,
   * java.lang.String, long)
   */
  @Override
  public SingleDataResultSet R_GetDistrictSchools_SP (SQLConnection connection, String clientname, String districtKey) throws ReturnStatusException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.dll.api.IRtsDLL#R_GetSchoolGrades_SP(AIR.Common.DB.SQLConnection,
   * long)
   */
  @Override
  public SingleDataResultSet R_GetSchoolGrades_SP (SQLConnection connection, String clientname, String schoolKey) throws ReturnStatusException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.dll.api.IRtsDLL#getSchoolStudents(AIR.Common.DB.SQLConnection,
   * java.lang.String, long, java.lang.String, java.lang.String,
   * java.lang.String)
   */
  @Override
  public SingleDataResultSet getSchoolStudents (SQLConnection connection, String clientname, String schoolKey, String grade, String firstName, String lastName) throws ReturnStatusException {
    return null;
  }

  @Override
  public SingleDataResultSet GetRTSUser_SP (SQLConnection connection, String clientName, String userId) throws ReturnStatusException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.dll.api.IRtsDLL#P_GetAllTests_SP(AIR.Common.DB.SQLConnection,
   * java.lang.String, int, java.lang.Long)
   */
  @Override
  public SingleDataResultSet P_GetAllTests_SP (SQLConnection connection, String clientname, int sessionType, Long proctorKey) throws ReturnStatusException {
    SingleDataResultSet singleDataResultSet = null;
    byte[] packageObject = getProctorPackageByKeyAndClientName (connection, proctorKey, clientname);
    if (packageObject != null) {

      IRtsPackageReader packageReader = new ProctorPackageReader ();
      try {
        if (packageReader.read (packageObject)) {
          List<CaseInsensitiveMap<Object>> resultList = new ArrayList<CaseInsensitiveMap<Object>> ();
          RtsTable testList = packageReader.getRtsTable ("Tests");
          for (RtsRecord tests : testList.getRecords ()) {
            CaseInsensitiveMap<Object> result = new CaseInsensitiveMap<Object> ();
            for (RtsField testField : tests.getFields ()) {
              if (testField.getKey ().equals ("SortOrder")) {
                result.put (testField.getKey (), Integer.valueOf (testField.getValue ()));
              } else if (testField.getKey ().equals ("TestKey")) {
                result.put (testField.getKey (), "(" + clientname + ")" + testField.getValue ());
              } else {
                result.put (testField.getKey (), testField.getValue ());
              }
            }
            resultList.add (result);
          }
          singleDataResultSet = new SingleDataResultSet ();
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

  /*
   * (non-Javadoc)
   * 
   * @see tds.dll.api.IRtsDLL#GetRTSUserRoles_SP(AIR.Common.DB.SQLConnection,
   * java.lang.String, java.lang.Long)
   */
  @Override
  public SingleDataResultSet GetRTSUserRoles_SP (SQLConnection connection, String clientName, Long userKey) throws ReturnStatusException {
    // TODO Auto-generated method stub
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.dll.api.IRtsDLL#GetRTSUserRoles_SP(AIR.Common.DB.SQLConnection,
   * java.lang.String, java.lang.Long, java.lang.Integer)
   */
  @Override
  public SingleDataResultSet GetRTSUserRoles_SP (SQLConnection connection, String clientName, Long userKey, Integer sessionType) throws ReturnStatusException {
    // TODO Auto-generated method stub
    return null;
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
  public void _ValidateAsProctor_SP (SQLConnection connection, String clientName, Long userKey, _Ref<Long> userEntityRef, _Ref<Long> roleEntityRef, _Ref<String> roleRef, Integer sessionType)
      throws ReturnStatusException {

    userEntityRef.set (null);
    roleEntityRef.set (null);
    roleRef.set (null);
    Integer certify = null;
    String certificate = null;
    Long entityKey = null;
    _Ref<String> certifiedRef = new _Ref<> ();
    String rts = _commonDll.getExternsColumnByClientName (connection, clientName, "proctorDB");

    String SQL_QUERY1 = "select IsOn as certify, Description as certificate from ${ConfigDB}.Client_SystemFlags where ClientName = ${clientName} and AuditObject = ${CertifyProctor};";
    String finalQuery = fixDataBaseNames (SQL_QUERY1);
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("clientName", clientName).put ("CertifyProctor", "CertifyProctor");
    SingleDataResultSet result = executeStatement (connection, finalQuery, parms1, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      certify = record.<Integer> get ("certify");
      certificate = record.<String> get ("certificate");
    }
    if (rts == null) {
      _logger.error (String.format ("Unable to locate RTS database for clienName: %s", clientName));
    }
    DataBaseTable rolesTable = getDataBaseTable ("roles").addColumn ("role", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 28).addColumn ("roleEntity", SQL_TYPE_To_JAVA_TYPE.BIGINT)
        .addColumn ("userEntity", SQL_TYPE_To_JAVA_TYPE.BIGINT);
    connection.createTemporaryTable (rolesTable);
    Map<String, String> unquotedParms = new HashMap<String, String> ();
    unquotedParms.put ("rolesTableName", rolesTable.getTableName ());

    final String SQL_INSERT = "insert into ${rolesTableName} (role, roleEntity, userEntity) select role, RoleEntity, UserEntity from  ${rts}.dbo.TDS_UserRoles(${userkey});";
    SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("userkey", userKey);
    Map<String, String> unquotedParms1 = new HashMap<> ();
    unquotedParms1.put ("rts", rts);
    unquotedParms1.put ("rolesTableName", rolesTable.getTableName ());
    int insertedCnt = executeStatement (connection, fixDataBaseNames (SQL_INSERT, unquotedParms1), parms3, false).getUpdateCount ();

    final String SQL_QUERY3 = "select top 1 R.role as role, R.userEntity as userEntity, R.roleEntity as roleEntity from ${ConfigDB}.Client_RTSRoles S, ${rolesTableName} R " +
        " where S.RTS_Role = R.role and S.TDS_Role = ${Proctor} and S.ClientName = ${clientname} and sessionType = ${sessionType};";
    finalQuery = fixDataBaseNames (SQL_QUERY3);
    SqlParametersMaps parms4 = new SqlParametersMaps ().put ("clientName", clientName).put ("Proctor", "Proctor").put ("sessionType", sessionType);
    result = executeStatement (connection, fixDataBaseNames (finalQuery, unquotedParms), parms4, false).getResultSets ().next ();
    record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      roleRef.set (record.<String> get ("role"));
      userEntityRef.set (record.<Long> get ("userEntity"));
      roleEntityRef.set (record.<Long> get ("roleEntity"));
    }
    if (roleRef.get () == null) {
      return;
    }
    if (DbComparator.isEqual (certify, 0) || certificate == null) {
      return;
    }
    if (userEntityRef.get () == null) {
      return;
    }
    _GetRTSAttribute_SP (connection, clientName, userEntityRef.get (), certificate, certifiedRef);
    if (certifiedRef == null || certifiedRef.get ().startsWith ("Y") == false) {
      userEntityRef.set (null);
      roleEntityRef.set (null);
      roleRef.set (null);
    }
    connection.dropTemporaryTable (rolesTable);
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
  public SingleDataResultSet P_ValidateProctor_SP (SQLConnection connection, String clientName, UUID browserKey, String proctorId, String password, Integer oppSessions, Boolean ignorepw,
      Integer sessionType) throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);
    // Boolean auditProc = null;
    // Integer duration = null;
    SingleDataResultSet result = null;

    // auditProc = _commonDll.AuditProc_FN (connection, "P_ValidateProctor");
    try {
      String rts = null;
      _Ref<Long> userKey = new _Ref<> ();
      _Ref<String> fullname = new _Ref<> ();
      _Ref<String> rtsPassword = new _Ref<> ();
      rts = _commonDll.getExternsColumnByClientName (connection, clientName, "proctorDB");
      if (rts == null) {
        String msg = String.format ("Unable to locate RTS for proctor %s", proctorId);
        _commonDll._RecordSystemError_SP (connection, "P_ValidateProctor", msg);
        return _commonDll._ReturnError_SP (connection, clientName, "P_ValidateProctor", "Unable to locate RTS database");
      }
      final String SQL_QUERY1 = "select _Key as userKey, fullname as fullname, password as rtspassword from ${rts}.dbo.tbluser where username = ${proctorID}" +
          " and (${ignorepw} = 1 or password = ${password}) and active = 1 and hasAcknowledged = 1;";
      SqlParametersMaps parms1 = new SqlParametersMaps ().put ("proctorID", proctorId).put ("ignorepw", ignorepw).put ("password", password);
      Map<String, String> unquotedMap = new HashMap<String, String> ();
      unquotedMap.put ("rts", rts);
      result = executeStatement (connection, fixDataBaseNames (SQL_QUERY1, unquotedMap), parms1, false).getResultSets ().next ();
      DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
      if (record != null) {
        userKey.set (record.<Long> get ("userKey"));
        fullname.set (record.<String> get ("fullname"));
        rtsPassword.set (record.<String> get ("rtspassword"));
      }
      if (userKey.get () == null) {
        _commonDll._LogDBLatency_SP (connection, "P_ValidateProctor", starttime, null, true, null, null, null, clientName, null);
        return _commonDll._ReturnError_SP (connection, clientName, "P_ValidateProctor", "Unable to log you in. Please check your account in UMS to be sure it is fully activated.");
      }
      _Ref<Long> userEntityRef = new _Ref<> ();
      _Ref<Long> roleEntityRef = new _Ref<> ();
      _Ref<String> roleRef = new _Ref<> ();

      _ValidateAsProctor_SP (connection, clientName, userKey.get (), userEntityRef, roleEntityRef, roleRef, sessionType);

      if (userEntityRef.get () == null) {
        _commonDll._LogDBLatency_SP (connection, "P_ValidateProctor", starttime, null, true, null, null, null, clientName, null);
        return _commonDll._ReturnError_SP (connection, clientName, "P_ValidateProctor", "User does not have permission");
      }
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
      rcrd.put ("entityKey", userEntityRef.get ());
      rcrd.put ("Fullname", fullname.get ());
      rcrd.put ("rtspassword", rtsPassword.get ());
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
   * Find record for particular Entity by entity's key and ClientName.
   * 
   * @param connection
   * @param entityType
   * @param key
   * @param clientName
   * @return SingleDataResultSet
   * @throws ReturnStatusException
   */
  private SingleDataResultSet findAllByKeyAndClientName (SQLConnection connection, EntityType entityType, Long key, String clientName) throws ReturnStatusException {
    SingleDataResultSet result = null;
    final String SQL_SELECT = "select * from R_" + entityType.getValue () + "Package where " + entityType.getValue () + "Key = ${key} and ClientName = ${clientName}";
    SqlParametersMaps params = new SqlParametersMaps ();
    params.put ("key", key);
    params.put ("clientName", clientName);
    result = executeStatement (connection, SQL_SELECT, params, false).getResultSets ().next ();
    return result;
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
  private DbResultRecord  findByKeyAndClientName (SQLConnection connection, EntityType entityType, Long key, String clientName) throws ReturnStatusException {
    final String SQL_SELECT = "select * from " + getTableName (entityType) + " where " + entityType.getValue () + "Key = ${key} and ClientName = ${clientName} and IsCurrent = 1 limit 1";
    SingleDataResultSet result = null;
    SqlParametersMaps params = new SqlParametersMaps ();
    params.put ("key", key);
    params.put ("clientName", clientName);
    result = executeStatement (connection, SQL_SELECT, params, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    return record;
  }

  private String getTableName (EntityType entityType) {
    return "r_" + entityType.getValue ().toLowerCase () + "package";
  }

  
  /**
   * Gets record for particular Entity by entity's key and ClientName.
   * 
   * @param connection
   * @param entityType
   * @param key
   * @param clientName
   * @return package
   * @throws ReturnStatusException
   */
  private byte[] getPackageByKeyAndClientName (SQLConnection connection, EntityType entityType, Long key, String clientName) throws ReturnStatusException {
    final String SQL_SELECT = "select top 1 * from R_" + entityType.getValue () + "Package where " + entityType.getValue () + "Key = ? and ClientName = ?";

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
   * Find records from Student given a StudentKey and ClientName.
   * 
   * @param connection
   * @param key
   * @param clientName
   * @return package
   * @throws ReturnStatusException
   */
  private byte[] getStudentPackageByKeyAndClientName (SQLConnection connection, Long key, String clientName) throws ReturnStatusException {
    return getPackageByKeyAndClientName (connection, EntityType.STUDENT, key, clientName);
  }

  /**
   * Find records from Student given a StudentKey and ClientName.
   * 
   * @param connection
   * @param key
   * @param clientName
   * @return package
   * @throws ReturnStatusException
   */
  private SingleDataResultSet findAllStudentByKeyAndClientName (SQLConnection connection, Long key, String clientName) throws ReturnStatusException {
    return findAllByKeyAndClientName (connection, EntityType.STUDENT, key, clientName);
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
   * Find records from Proctor given a ProctorKey and ClientName.
   * 
   * @param connection
   * @param key
   * @param clientName
   * @return SingleDataResultSet
   * @throws ReturnStatusException
   */
  private SingleDataResultSet findAllProctorByKeyAndClientName (SQLConnection connection, Long key, String clientName) throws ReturnStatusException {
    return findAllByKeyAndClientName (connection, EntityType.PROCTOR, key, clientName);
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
  private int createStudent (SQLConnection connection, Long key, String clientName, String xmlPackage) throws ReturnStatusException {
    IRtsPackageWriter<StudentPackage> writer = new StudentPackageWriter ();
    try {
      writer.writeObject (xmlPackage);
      return create (connection, EntityType.STUDENT, key, clientName, writer.getInputStream (), writer.getObject ().getVersion ());
    } catch (RtsPackageWriterException e) {
      // TODO Auto-generated catch block
      _logger.error (e.getMessage (), e);
    }
    return 0;
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
  private int createProctor (SQLConnection connection, Long key, String clientName, String xmlPackage) throws ReturnStatusException {
    IRtsPackageWriter<ProctorPackage> writer = new ProctorPackageWriter ();
    try {
      writer.writeObject (xmlPackage);
      return create (connection, EntityType.PROCTOR, key, clientName, writer.getInputStream (), writer.getObject ().getVersion ());
    } catch (RtsPackageWriterException e) {
      // TODO Auto-generated catch block
      _logger.error (e.getMessage (), e);
    }
    return 0;
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
  private int create (SQLConnection connection, EntityType entityType, Long key, String clientName, InputStream packageStream, String version) throws ReturnStatusException {
    final String SQL_INSERT = "insert into R_" + entityType.getValue () + "Package (" + entityType.getValue () + "Key, ClientName, Package, Version) values (?, ?, ?, ?) ";
    try (PreparedStatement preparedStatement = connection.prepareStatement (SQL_INSERT)) {
      preparedStatement.setLong (1, key);
      preparedStatement.setString (2, clientName);
      preparedStatement.setBinaryStream (3, packageStream);
      preparedStatement.setString (4, version);
      preparedStatement.execute ();
      return 1;
    } catch (SQLException exp) {
      // TODO
      _logger.error (exp.getMessage (), exp);
    }
    return 0;// TODO
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
  private int createAndUpdateIsCurrent (SQLConnection connection, EntityType entityType, Long key, String clientName, String xmlPackage) throws ReturnStatusException {
    int insertCount = 0;
    boolean isCurrent = false;
    try {
      boolean preExistingAutoCommitMode = connection.getAutoCommit ();
      connection.setAutoCommit (false);
      updateIsCurrent (connection, entityType, key, clientName, isCurrent);
      if (entityType == EntityType.STUDENT) {
        insertCount = createStudent (connection, key, clientName, xmlPackage);
      } else {
        insertCount = createProctor (connection, key, clientName, xmlPackage);
      }
      connection.commit ();
      connection.setAutoCommit (preExistingAutoCommitMode);
    } catch (ReturnStatusException e) {
      try {
        connection.rollback ();
      } catch (SQLException se) {
        _logger.error (String.format ("Problem rolling back transaction: %s", se.getMessage ()));
      }
      throw e;
    } catch (Exception e) {
      try {
        connection.rollback ();
      } catch (SQLException se) {
        _logger.error (String.format ("Problem rolling back transaction: %s", se.getMessage ()));
      }
      return 0;
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
    return createAndUpdateIsCurrent (connection, EntityType.STUDENT, key, clientName, xmlPackage);
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
    return createAndUpdateIsCurrent (connection, EntityType.PROCTOR, key, clientName, xmlPackage);
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
    final String SQL_UPDATE = "update R_" + entityType.getValue () + "Package set isCurrent = ${isCurrent} where " + entityType.getValue ()
        + "Key = ${key} and ClientName = ${clientName} and isCurrent <> ${isCurrent}";
    SqlParametersMaps params = new SqlParametersMaps ();
    params.put ("key", key);
    params.put ("clientName", clientName);
    params.put ("isCurrent", isCurrent);
    int updateCount = executeStatement (connection, SQL_UPDATE, params, false).getUpdateCount ();
    return updateCount;
  }

  @Override
  public Long getOrCreateStudentKey (SQLConnection connection, String SSID, String clientCode) throws ReturnStatusException {
    throw new NotImplementedException ("This behavior is only implemented for MySQL RTSPackageDLL");
  }

  @Override
  public Map<String, String> getTesteeAttributes (SQLConnection connection, String clientName, long testeeKey) {
    Map<String, String> map = new HashMap<> ();
    map.put ("LastName", "Sojka");
    map.put ("FirstName", "Bud");
    map.put ("MiddleName", "Johan");
    map.put ("SSID", "82811007");
    map.put ("DOB", "2013-08-31");
    map.put ("Gender", "Female");
    map.put ("HispanicOrLatinoEthnicity", "No");
    map.put ("AmericanIndianOrAlaskaNative", "No");
    map.put ("Asian", "Yes");
    map.put ("BlackOrAfricanAmerican", "No");
    map.put ("White", "No");
    map.put ("NativeHawaiianOrOtherPacificIslander", "No");
    map.put ("DemographicRaceTwoOrMoreRaces", "No");
    map.put ("IDEAIndicator", "No");
    map.put ("LEPStatus", "No");
    map.put ("Section504Status", "No");
    map.put ("EconomicDisadvantageStatus", "No");
    return map;
  }

  @Override
  public Map<String, String> getTesteeRelationships (SQLConnection connection, String clientName, long testeeKey) {
    Map<String, String> map = new HashMap<> ();
    map.put ("DistrictID", "71715");
    map.put ("DistrictName", "LA County Schools");
    map.put ("SchoolID", "8716411");
    map.put ("SchoolName", "Wren Reedbuck High School");
    return map;
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
  

  // TODO Implement when mssql integration required
  public int createUser (SQLConnection connection, String userId, String email, String fullName) throws ReturnStatusException {
    return 0;
  }

  // TODO Implement when mssql integration required
  public boolean userAlreadyExists (SQLConnection connection, String userId, String email) throws ReturnStatusException {
    return false;
  }

  //Implementation not required  
  @Override
  public String getTrTestId (SQLConnection connection, String testeeId, String testKey) {
    return null;
  }

  @Override
  public SingleDataResultSet getTesteeAttributesAsSet (SQLConnection connection, String clientname, long testee) {
    // TODO Auto-generated method stub
    return null;
  }
  
//Implementation not required  
  @Override
  public void _InsertStudentPackageDetails (SQLConnection connection, Long key, String clientName, String xmlPackage) throws ReturnStatusException {
    
  }
}
