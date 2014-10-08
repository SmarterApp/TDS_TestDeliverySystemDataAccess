/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.mssql;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import tds.dll.api.ICommonDLL;
import tds.dll.api.IProctorDLL;
import tds.dll.api.IRtsDLL;
import tds.dll.api.TestType;
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
import TDS.Shared.Data.ReturnStatus;
import TDS.Shared.Exceptions.ReturnStatusException;


public class RtsDLL extends AbstractDLL implements IRtsDLL
{
  private static Logger              _logger   = LoggerFactory.getLogger (RtsDLL.class);
  
  private ICommonDLL           _commonDll = null;
  private AbstractDateUtilDll _dateUtil = null;
  private IProctorDLL         _proctorDll = null;
  
  @Autowired
  private void setCommonDll( ICommonDLL commonDll ) {
    _commonDll = commonDll;
  }
  
  @Autowired
  private void setDateUtil( AbstractDateUtilDll dateUtil ) {
    _dateUtil = dateUtil;
  }
  
  @Autowired
  private void setProctorDll( IProctorDLL proctorDll ) {
    _proctorDll = proctorDll;
  }
  
  public void _GetRTSAttribute_SP (SQLConnection connection, String clientname, Long testee, String attname, _Ref<String> attValue) throws ReturnStatusException {

    _GetRTSAttribute_SP (connection, clientname, testee, attname, attValue, false);
  }

  public void _GetRTSAttribute_SP (SQLConnection connection, String clientname, Long testee, String attname, _Ref<String> attValue, Boolean debug) throws ReturnStatusException {

    String rts = getTesteeDbByClientName (connection, clientname);
    if (rts == null)
      return; // no logging or throwing exception? that's how SP looks

    String sql_query = null;
    Map<String, String> unquotedMap = new HashMap<String, String> ();
    unquotedMap.put ("rts", rts);

    SqlParametersMaps parameters1 = (new SqlParametersMaps ()).put ("testee", testee);

    SqlParametersMaps parameters2 = (new SqlParametersMaps ()).put ("testee", testee).put ("clientname", clientname);

    SqlParametersMaps parameters3 = (new SqlParametersMaps ()).put ("testee", testee).put ("attname", attname);

    SqlParametersMaps parameters = null;

    if (DbComparator.isEqual (attname, "--ACCOMMODATIONS--")) {
      // if ("--ACCOMMODATIONS--".equals (attname)) {
      final String SQL_QUERY1 = "select ${rts}.dbo.TDS_AccommodationsString(${testee}) as attrValue";
      sql_query = SQL_QUERY1;
      parameters = parameters1;

    } else if (DbComparator.isEqual (attname, "--ENTITYNAME--")) {
      final String SQL_QUERY2 = "select ${rts}.dbo._fEntityName(${testee}) as attrValue";
      sql_query = SQL_QUERY2;
      parameters = parameters1;

    } else if (DbComparator.isEqual (attname, "--ETHNICITY--")) {
      final String SQL_QUERY3 = "select ${rts}.dbo.TDS_fMapEthnicity(${testee}, ${clientname}) as attrValue";
      sql_query = SQL_QUERY3;
      parameters = parameters2;

    } else if (DbComparator.isEqual (attname, "--ELIGIBLETESTS--")) {
      final String SQL_QUERY4 = "select ${rts}.dbo.TDS_EligibleTests(${testee}, ${clientname}) as attrValue";
      sql_query = SQL_QUERY4;
      parameters = parameters2;

    } else {
      final String SQL_QUERY5 = "select ${rts}.dbo.TDS_fGetEntityAttribute(${testee}, ${attname}) as attrValue";
      sql_query = SQL_QUERY5;
      parameters = parameters3;
    }

    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (sql_query, unquotedMap), parameters, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      String value = null;
      // Elena: hate doing this check, but mapEthnicity is the only function
      // that returns bigint rather than String
      if (DbComparator.isEqual (attname, "--ETHNICITY--")) {
        Long val = record.<Long> get ("attrValue");
        value = (val == null ? null : val.toString ());
      }
      else
        value = record.<String> get ("attrValue");
      attValue.set (value);
    }
  }

  public void _GetRTSEntity_SP (SQLConnection connection, String clientname, String externaId, String entityType, _Ref<Long> entityKeyRef) throws ReturnStatusException {

    entityKeyRef.set (null);
    String rts = getTesteeDbByClientName (connection, clientname);
    if (rts == null)
      return;
    final String SQL_QUERY = "select top 1 entityKey from ${rts}.dbo.TDS_fGetEntityKey(${externalId}) where EntityType = ${entityType}";
    SqlParametersMaps parms = (new SqlParametersMaps ()).put ("externalId", externaId).put ("entityType", entityType);
    Map<String, String> unquotedParms = new HashMap<> ();
    unquotedParms.put ("rts", rts);

    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY, unquotedParms), parms, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null)
      entityKeyRef.set (record.<Long> get ("entityKey"));

  }

  public SingleDataResultSet _GetTesteeRelationships_SP (SQLConnection connection, String clientname, Long testee) throws ReturnStatusException {

    DataBaseTable attributesTable = getDataBaseTable ("gtrAttrs").addColumn ("relationType", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).
        addColumn ("entityKey", SQL_TYPE_To_JAVA_TYPE.BIGINT).addColumn ("rtsName", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 100).
        addColumn ("attname", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 50).addColumn ("attval", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 2000).
        addColumn ("done", SQL_TYPE_To_JAVA_TYPE.BIT);
    connection.createTemporaryTable (attributesTable);

    final String SQL_QUERY1 = "select TesteeDB  as rts from externs where clientname = ${clientname}";
    SqlParametersMaps parms1 = (new SqlParametersMaps ()).put ("clientname", clientname);

    SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parms1, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);

    String rts = null;
    if (record != null) {
      rts = record.<String> get ("rts");
    }

    final String SQL_QUERY2 = "select TDS_ID as reltype, RTSName "
        + " from ${ConfigDB}.Client_TesteeAttribute where clientname = ${clientname} and type = 'relationship'";
    SqlParametersMaps parms2 = (new SqlParametersMaps ()).put ("clientname", clientname);

    SingleDataResultSet relationsResult = executeStatement (connection, fixDataBaseNames (SQL_QUERY2), parms2, false).getResultSets ().next ();

    Iterator<DbResultRecord> records = relationsResult.getRecords ();
    while (records.hasNext ()) {
      DbResultRecord relationsRecord = records.next ();
      String reltype = relationsRecord.<String> get ("reltype");
      String relation = relationsRecord.<String> get ("rtsname");

      final String SQL_QUERY3 = "select parentKey from ${rts}.dbo.TDS_fGetParentKeys(${childkey}, ${relationship})";
      SqlParametersMaps parms3 = (new SqlParametersMaps ()).put ("childkey", testee).put ("relationship", relation);
      Map<String, String> unquotedParms3 = new HashMap<String, String> ();
      unquotedParms3.put ("rts", rts);

      SingleDataResultSet relkeysResult = executeStatement (connection, fixDataBaseNames (SQL_QUERY3, unquotedParms3), parms3, false).getResultSets ().next ();
      Iterator<DbResultRecord> records3 = relkeysResult.getRecords ();

      while (records3.hasNext ()) {
        DbResultRecord relkeysRecord = records3.next ();
        Long parentkey = relkeysRecord.<Long> get ("parentKey");

        final String SQL_INSERT4 = "insert into ${attributes} (relationType, entityKey, attname, rtsName) "
            + " select ${reltype}, ${parentKey}, TDS_ID, RTSName from ${ConfigDB}.Client_TesteeRelationshipAttribute "
            + " where clientname = ${clientname} and relationshipType = ${reltype}";
        SqlParametersMaps parms4 = (new SqlParametersMaps ()).put ("clientname", clientname).put ("reltype", reltype).
            put ("parentkey", parentkey);
        Map<String, String> unquotedParms4 = new HashMap<String, String> ();
        unquotedParms4.put ("attributes", attributesTable.getTableName ());

        final String query4 = fixDataBaseNames (SQL_INSERT4);
        int insertedCnt = executeStatement (connection, fixDataBaseNames (query4, unquotedParms4), parms4, false).getUpdateCount ();

        final String SQL_QUERY5 = "select attname, rtsname from  ${attributes} where done is null";
        Map<String, String> unquotedParms5 = unquotedParms4;

        SingleDataResultSet attributesResult = executeStatement (connection, fixDataBaseNames (SQL_QUERY5, unquotedParms5), null, false).getResultSets ().next ();
        Iterator<DbResultRecord> records5 = attributesResult.getRecords ();
        while (records5.hasNext ()) {
          DbResultRecord record5 = records5.next ();
          String attname = record5.<String> get ("attname");
          String rtsname = record5.<String> get ("rtsname");

          String attval = null;
          if (testee > 0) {
            _Ref<String> attvalRef = new _Ref<String> ();
            _GetRTSAttribute_SP (connection, clientname, parentkey, rtsname, attvalRef);
            attval = attvalRef.get ();
          } else {
            attval = String.format ("GUEST %s", attname);
          }
          final String SQL_UPDATE6 = "update ${attributes} set attval = ${attval}, done = 1 where attname = ${attname}";
          SqlParametersMaps parms6 = (new SqlParametersMaps ()).put ("attname", attname).put ("attval", attval);
          Map<String, String> unquotedParms6 = unquotedParms4;
          int updateCnt = executeStatement (connection, fixDataBaseNames (SQL_UPDATE6, unquotedParms6), parms6, false).getUpdateCount ();
        }
      }
    }
    final String SQL_QUERY7 = "select relationType, entityKey, attname as TDS_ID,  attval from ${attributes}";
    Map<String, String> unquotedParms7 = new HashMap<String, String> ();
    unquotedParms7.put ("attributes", attributesTable.getTableName ());

    SingleDataResultSet rs = executeStatement (connection, fixDataBaseNames (SQL_QUERY7, unquotedParms7), null, false).getResultSets ().next ();
    connection.dropTemporaryTable (attributesTable);

    return rs;
  }

  public void _ValidateInstitutionMatch_SP (SQLConnection connection, String clientname, Long testeeKey, Long proctorKey, _Ref<String> instKey) throws ReturnStatusException {
    SingleDataResultSet result = null;

    String rts = getTesteeDbByClientName (connection, clientname);
    if (rts == null) {
      ReturnStatus rs = new ReturnStatus ("failed", String.format ("RTS %1$s not found for client %2$s", "testeeDB", clientname));
      throw new ReturnStatusException (rs);
    }
    final String RTS_QUERY1 = "select top 1  S.entityKey  as instKey from ${databaseRTS}.dbo.TDS_UserRoles(${proctorKey}) R, "
        + " ${ConfigDB}.Client_RTSRoles C, ${databaseRTS}.dbo._fParentKeys (${testeeKey}) S  where  R.RoleEntity = S.EntityKey "
        + " and  C.clientname = ${clientname} and R.role = C.RTS_Role and C.TDS_Role = 'proctor'";

    Map<String, String> databaseNames = new HashMap<String, String> ();
    databaseNames.put ("databaseRTS", rts);

    SqlParametersMaps parameters1 = new SqlParametersMaps ();
    parameters1.put ("proctorKey", proctorKey);
    parameters1.put ("testeeKey", testeeKey);
    parameters1.put ("clientname", clientname);

    final String query1 = fixDataBaseNames (RTS_QUERY1);
    result = executeStatement (connection, fixDataBaseNames (query1, databaseNames), parameters1, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      instKey.set (record.<String> get ("instKey"));
    }
  }

  public void _GetRTSRelationship_SP (SQLConnection connection, String clientname, Long testee,
      String relationship, _Ref<Long> entityKey, _Ref<String> entityId, _Ref<String> entityName) throws ReturnStatusException {

    String rts = getTesteeDbByClientName (connection, clientname);
    // TODO should we throw exception here? no checks for null rts in SP
    if (rts == null) {
      ReturnStatus rs = new ReturnStatus ("failed", String.format ("RTS %1$s not found for client %2$s", "testeeDB", clientname));
      throw new ReturnStatusException (rs);
    }

    final String SQL_QUERY1 = "select RTSKey, ExternalID, EntityName "
        + " from ${databaseRTS}.dbo.TDS_fParentInstitution(${testee}, ${Rel})";
    Map<String, String> databaseNames = new HashMap<String, String> ();
    databaseNames.put ("databaseRTS", rts);

    SqlParametersMaps parameters1 = new SqlParametersMaps ().put ("testee", testee).put ("rel", relationship);

    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY1, databaseNames), parameters1, false).getResultSets ().next ();
    DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
    if (record != null) {
      entityKey.set (record.<Long> get ("RTSKey"));
      entityId.set (record.<String> get ("externalID"));
      entityName.set (record.<String> get ("entityName"));
    }
  }

  public SingleDataResultSet GetRTSUserRoles_SP (SQLConnection connection, String clientName, Long userKey) throws ReturnStatusException {
    return GetRTSUserRoles_SP (connection, clientName, userKey, 0);
  }

  /**
   * @param connection
   * @param clientName
   * @param userKey
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet GetRTSUserRoles_SP (SQLConnection connection, String clientName, Long userKey, Integer sessionType) throws ReturnStatusException {

  
    SingleDataResultSet result = null;

    String rts = getTesteeDbByClientName (connection, clientName);
   
    if (rts == null) {
      _logger.error (String.format ("Unable to locate RTS database for clienName: %s", clientName));
    }
    // select null as [role], 'failed' as [status], 'Incorrect ID or password'
    // as reason, 'R_LoginReportUser' as [context], null as [argstring], null as
    // [delimiter];

    if (userKey == null) {
      List<CaseInsensitiveMap<Object>> resultlist = new ArrayList<CaseInsensitiveMap<Object>> ();
      CaseInsensitiveMap<Object> rcrd = new CaseInsensitiveMap<Object> ();
      rcrd.put ("role", null);
      rcrd.put ("status", "failed");
      rcrd.put ("reason", "Incorrect ID or password");
      rcrd.put ("context", "R_LoginReportUser");
      rcrd.put ("argstring", null);
      rcrd.put ("delimiter", null);
      resultlist.add (rcrd);

      result = new SingleDataResultSet ();
      result.addColumn ("role", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("status", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("reason", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("context", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("argstring", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addColumn ("delimiter", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
      result.addRecords (resultlist);

      return result;
    }

    final String SQL_QUERY = "select ${userkey} as Userkey, TDS_Role, InstitutionKey, InstitutionName, InstitutionID, InstitutionType from ${rts}.dbo.TDS_UserRolesComplete(${userkey}) U,"
        + " ${ConfigDB}.Client_RTSRoles R where R.Clientname = ${clientname} and U.Role = R.RTS_Role and R.sessionType = ${sessionType}";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("userkey", userKey).put ("sessionType", sessionType).put ("clientname", clientName);
    Map<String, String> unquotedParms = new HashMap<> ();
    unquotedParms.put ("rts", rts);
    String finalQuery = fixDataBaseNames (SQL_QUERY);
    result = executeStatement (connection, fixDataBaseNames (finalQuery, unquotedParms), parms, false).getResultSets ().next ();

    return result;
  }
  
    
private String getTesteeDbByClientName (SQLConnection connection, String clientName) throws ReturnStatusException {
  String columnValue = null;
  final String SQL_QUERY1 = "select testeedb from externs where clientname = ${clientname};";

  SqlParametersMaps parameters = (new SqlParametersMaps ()).put ("clientname", clientName);
  parameters.put ("clientname", clientName);

  SingleDataResultSet result = executeStatement (connection, SQL_QUERY1, parameters, false).getResultSets ().next ();
  DbResultRecord record = result.getCount () > 0 ? result.getRecords ().next () : null;
  if (record != null) {
    columnValue = record.<String> get ("testeedb");
  }
  return columnValue;
}

  /*
   * (non-Javadoc)
   * 
   * @see tds.dll.api.IRtsDLL#R_GetDistricts_SP(AIR.Common.DB.SQLConnection,
   * java.lang.String)
   */
  //@Override
  //public SingleDataResultSet R_GetDistricts_SP (SQLConnection connection, String clientname) throws ReturnStatusException {
    // TODO Auto-generated method stub
  //  return null;
  //}

/**
 * @param connection
 * @param clientName
 * @return
 * @throws ReturnStatusException
 */
public SingleDataResultSet R_GetDistricts_SP (SQLConnection connection, String clientName) throws ReturnStatusException {

  Date starttime = _dateUtil.getDateWRetStatus (connection);
  SingleDataResultSet result = null;
  String rts = null;

  String SQL_QUERY = "select TesteeDB as rts from Externs where TesteeType = ${RTS} and clientname = ${clientname};";
  SqlParametersMaps parms1 = new SqlParametersMaps ().put ("RTS", "RTS").put ("clientname", clientName);
  result = executeStatement (connection, SQL_QUERY, parms1, false).getResultSets ().next ();
  DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
  if (record != null) {
    rts = record.<String> get ("rts");
  }
  if (rts == null) {
    _commonDll._LogDBError_SP (connection, "R_GetDistricts", "Unable to locate RTS", null, null, null, null, clientName, null);
    return _commonDll._ReturnError_SP (connection, clientName, "R_GetDistricts", "Unable to locate RTS database");
  }
  final String SQL_QUERY1 = "select * from ${rts}.dbo.TDS_fDistricts2() order by DistrictName";
  Map<String, String> unquotedParms = new HashMap<> ();
  unquotedParms.put ("rts", rts);
  result = executeStatement (connection, fixDataBaseNames (SQL_QUERY1, unquotedParms), null, false).getResultSets ().next ();

  _commonDll._LogDBLatency_SP (connection, "R_GetDistricts", starttime, null, true, null, null, null, clientName, null);
  return result;
}
  /*
   * (non-Javadoc)
   * 
   * @see
   * tds.dll.api.IRtsDLL#R_GetDistrictSchools_SP(AIR.Common.DB.SQLConnection,
   * java.lang.String, long)
   */
  //@Override
  //public SingleDataResultSet R_GetDistrictSchools_SP (SQLConnection connection, String clientname, long districtKey) throws ReturnStatusException {
    // TODO Auto-generated method stub
  //  return null;
  //}

/**
 * @param connection
 * @param clientName
 * @param distRTSKey
 * @return
 * @throws ReturnStatusException
 */
public SingleDataResultSet R_GetDistrictSchools_SP (SQLConnection connection, String clientName, String districtKey) throws ReturnStatusException {
  long distRTSKey = Long.valueOf (districtKey);
  Date starttime = _dateUtil.getDateWRetStatus (connection);
  SingleDataResultSet result = null;

  String rts = _commonDll.getExternsColumnByClientName (connection, clientName, "testeeDB");

  if (rts == null) {
    return _commonDll._ReturnError_SP (connection, clientName, "R_GetDistrictSchools", "Unable to locate RTS database", null, null, "R_GetSchoolGrades");
  }
  final String SQL_QUERY1 = "select * from ${rts}.dbo.TDS_fDistrictSchools (${distRTSKey}) order by SchoolName";
  SqlParametersMaps parms = new SqlParametersMaps ().put ("distRTSKey", distRTSKey);
  Map<String, String> unquotedParms = new HashMap<> ();
  unquotedParms.put ("rts", rts);
  result = executeStatement (connection, fixDataBaseNames (SQL_QUERY1, unquotedParms), parms, false).getResultSets ().next ();

  _commonDll._LogDBLatency_SP (connection, "R_GetDistrictSchools", starttime, null, true, null, null, null, clientName, null);
  return result;
}
  /*
   * (non-Javadoc)
   * 
   * @see tds.dll.api.IRtsDLL#R_GetSchoolGrades_SP(AIR.Common.DB.SQLConnection,
   * long)
   */
  //@Override
  //public SingleDataResultSet R_GetSchoolGrades_SP (SQLConnection connection, String clientName, long schoolKey) throws ReturnStatusException {
    // TODO Auto-generated method stub
  //  return null;
  //}

  /**
   * 
   * @param connection
   * @param clientName
   * @param schoolRTSKey
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet R_GetSchoolGrades_SP (SQLConnection connection, String clientName, String schoolKey) throws ReturnStatusException {
    long schoolRTSKey = Long.valueOf (schoolKey);
    Date starttime = _dateUtil.getDateWRetStatus (connection);
    final int DEFAULT_EXPIRE_DAYS = 7;
    Integer expireDays = DEFAULT_EXPIRE_DAYS;
    SingleDataResultSet result = null;

    String rts = _commonDll.getExternsColumnByClientName (connection, clientName, "testeeDB");

    if (rts == null) {
      _commonDll._LogDBError_SP (connection, "R_GetSchholGrades", "Unable to locate RTS", null, null, null, null, clientName, null);
      return _commonDll._ReturnError_SP (connection, clientName, "R_GetSchoolGrades", "Unable to locate RTS database");
    }
    final String SQL_QUERY1 = "select top 1 SchoolKey from RTSSchoolGrades where SchoolKey = ${schoolRTSKey} and (${expireDays} is null or datediff(day, ${starttime}, dateEntered) > ${expireDays})";
    SqlParametersMaps parms1 = new SqlParametersMaps ().put ("schoolRTSKey", schoolRTSKey).put ("starttime", starttime).put ("expireDays", expireDays);
    if (!exists (executeStatement (connection, SQL_QUERY1, parms1, false))) {
      final String SQL_DELETE = "delete from RTSSchoolGrades where clientname = ${clientname} and SchoolKey = ${schoolRTSKey}; ";
      SqlParametersMaps parms2 = new SqlParametersMaps ().put ("schoolRTSKey", schoolRTSKey).put ("clientname", clientName);
      int deletedCnt = executeStatement (connection, SQL_DELETE, parms2, false).getUpdateCount ();
      // _logger.info (deletedCnt); // for testing

      final String SQL_INSERT1 = "insert into RTSSchoolGrades (clientname, SchoolKey) values (${clientname}, ${schoolRTSKey});";
      SqlParametersMaps parms3 = parms2;
      int insertedCnt = executeStatement (connection, SQL_INSERT1, parms3, false).getUpdateCount ();
      // _logger.info (insertedCnt); // for testing

      final String SQL_INSERT2 = "insert into RTSSchoolGrades (clientname, SchoolKey, grade, enrollment) select ${clientname}, ${key}, grade, enrollment from ${databaseRTS}.dbo.TDS_fSchoolGrades2 (${key})";
      Map<String, String> databaseNames = new HashMap<String, String> ();
      databaseNames.put ("databaseRTS", rts);
      SqlParametersMaps parms4 = new SqlParametersMaps ().put ("key", schoolRTSKey).put ("clientname", clientName);
      insertedCnt = executeStatement (connection, fixDataBaseNames (SQL_INSERT2, databaseNames), parms4, false).getUpdateCount ();
      // _logger.info (insertedCnt); // for testing
    }
    final String SQL_QUERY2 = "select distinct grade from RTSSchoolGrades where clientname = ${clientname} and SchoolKey = ${schoolRTSKey} and grade is not null;";
    SqlParametersMaps parms5 = new SqlParametersMaps ().put ("schoolRTSKey", schoolRTSKey).put ("clientname", clientName);
    result = executeStatement (connection, SQL_QUERY2, parms5, false).getResultSets ().next ();

    _commonDll._LogDBLatency_SP (connection, "R_GetSchholGrades", starttime, null, true, null, null, null, clientName, null);
    return result;
  }
  /*
   * (non-Javadoc)
   * 
   * @see tds.dll.api.IRtsDLL#getSchoolStudents(AIR.Common.DB.SQLConnection,
   * java.lang.String, long, java.lang.String, java.lang.String,
   * java.lang.String)
   */
  //@Override
  //public SingleDataResultSet getSchoolStudents (SQLConnection connection, String clientname, long schoolKey, String grade, String firstName, String lastName) throws ReturnStatusException {
    // TODO Auto-generated method stub
  //  return null;
  //}

  /**
   * @param connection
   * @param clientName
   * @param school
   * @param grade
   * @param firstName
   * @param lastName
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet getSchoolStudents (SQLConnection connection, String clientName, String schoolKey, String grade, String firstName, String lastName) throws ReturnStatusException {
    long school = Long.valueOf (schoolKey);
    Date starttime = _dateUtil.getDateWRetStatus (connection);
    SingleDataResultSet result = null;

    String rts = _commonDll.getExternsColumnByClientName (connection, clientName, "testeeDB");

    if (rts == null) {
      _commonDll._LogDBError_SP (connection, "GetSchoolStudents", "Unable to locate RTS", null, null, null, null, clientName, null);
      return _commonDll._ReturnError_SP (connection, clientName, "GetSchoolStudents", "Unable to locate RTS database", null, null, "R_SchoolParticipation");
    }

    DataBaseTable studentsTable = getDataBaseTable ("students").addColumn ("rtskey", SQL_TYPE_To_JAVA_TYPE.BIGINT).addColumn ("SSID", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 128)
        .addColumn ("LastName", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 128).addColumn ("FirstName", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 128).addColumn ("Grade", SQL_TYPE_To_JAVA_TYPE.VARCHAR, 30);
    connection.createTemporaryTable (studentsTable);
    Map<String, String> unquotedParms = new HashMap<> ();
    unquotedParms.put ("rts", rts);
    unquotedParms.put ("StudentsTableName", studentsTable.getTableName ());

    if (grade != null) {
      final String SQL_INSERT = "insert into ${StudentsTableName} (rtskey, SSID, LastName, FirstName, Grade) select entityKey, ExternalID, LastName, FirstName, Grade" +
          " from ${rts}.dbo.TDS_fSchoolGradeStudentsWithExtra(${school}, ${grade}, null, null, null)";
      SqlParametersMaps parms1 = new SqlParametersMaps ().put ("school", school).put ("grade", grade);
      int insertedCnt = executeStatement (connection, fixDataBaseNames (SQL_INSERT, unquotedParms), parms1, false).getUpdateCount ();
      // _logger.info (insertedCnt);

    } else {
      final String SQL_INSERT1 = "insert into ${StudentsTableName} (rtskey, SSID, LastName, FirstName, Grade) select entityKey, ExternalID, LastName, FirstName, Grade" +
          " from ${rts}.dbo.TDS_fSchoolStudentsWithExtra(${school}, null, null, null)";
      SqlParametersMaps parms2 = new SqlParametersMaps ().put ("school", school);
      int insertedCnt = executeStatement (connection, fixDataBaseNames (SQL_INSERT1, unquotedParms), parms2, false).getUpdateCount ();
      // _logger.info (insertedCnt);
    }

    String likeRec1 = String.format ("%s", firstName);
    String likeRec2 = String.format ("%s", lastName);

    final String SQL_QUERY = "select * from ${StudentsTableName} where firstname like ${likeRec1} and lastname like ${likeRec2} order by Grade, Lastname, firstname;";
    SqlParametersMaps parms3 = new SqlParametersMaps ().put ("likeRec1", likeRec1).put ("likeRec2", likeRec2);
    Map<String, String> unquotedParms1 = new HashMap<> ();
    unquotedParms1.put ("StudentsTableName", studentsTable.getTableName ());
    result = executeStatement (connection, fixDataBaseNames (SQL_QUERY, unquotedParms1), parms3, false).getResultSets ().next ();

    connection.dropTemporaryTable (studentsTable);
    _commonDll._LogDBLatency_SP (connection, "GetSchoolStudents", starttime, school, true, null, null, null, clientName, null);
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see tds.dll.api.IRtsDLL#P_GetAllTests_SP(AIR.Common.DB.SQLConnection,
   * java.lang.String, int, java.lang.Long)
   */
  //@Override
  //public SingleDataResultSet P_GetAllTests_SP (SQLConnection connection, String clientname, int sessionType, Long proctorKey) throws ReturnStatusException {
    // TODO Auto-generated method stub
  //  return null;
  //}
  /**
   * @param connection
   * @param clientName
   * @param sessionType
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet P_GetAllTests_SP (SQLConnection connection, String clientName, int sessionType, Long proctorKey) throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);

    final String SQL_QUERY = "select distinct P.TestID, P.GradeText, P.SubjectName as Subject, P.label as DisplayName, P.SortOrder, P.AccommodationFamily, P.IsSelectable,"
        + " M.IsSegmented, M.TestKey from ${ConfigDB}.Client_TestProperties P, ${ConfigDB}.Client_TestMode M, ${ConfigDB}.Client_TestWindow W, ${ItemBankDB}.tblSetofAdminSubjects S "
        + " where W.clientname = ${clientname} and (W.sessionType = -1 or W.sessionType = ${sessionType}) and ${starttime} between W.startDate and W.endDate"
        + " and P.clientname = ${clientname} and P.testID = W.testID and M.clientname = ${clientname} and M.testID = P.testID and "
        + " (M.sessionType = -1 or M.sessionType = ${sessionType}) and M.testkey = S._Key order by SortOrder;";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("clientname", clientName).put ("starttime", starttime).put ("sessionType", sessionType);
    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (SQL_QUERY), parms, false).getResultSets ().next ();

    _commonDll._LogDBLatency_SP (connection, "P_GetAllTests", starttime, null, true, null, null, null, clientName, null);
    return result;
  }

  /**
   * @param connection
   * @param clientName
   * @param userId
   * @return
   * @throws ReturnStatusException
   */
  public SingleDataResultSet GetRTSUser_SP (SQLConnection connection, String clientName, String userId) throws ReturnStatusException {

    Date starttime = _dateUtil.getDateWRetStatus (connection);
    _Ref<Long> userKey = new _Ref<> ();
    _Ref<String> fullName = new _Ref<> ();
    _Ref<String> password = new _Ref<> ();
    _Ref<Boolean> active = new _Ref<> ();
    _Ref<Boolean> ack = new _Ref<> ();
    String rts = _commonDll.getExternsColumnByClientName (connection, clientName, "proctorDB");

    if (rts == null) {
      String msg = "Unable to locate RTS database";
      _commonDll._RecordSystemError_SP (connection, "GetRTSUser", msg);
      return _commonDll._ReturnError_SP (connection, clientName, "GetRTSUser", msg, null, null, null);
    }
    final String SQL_QUERY = "select _Key as userkey, fullname, password, active, hasAcknowledged as ack from ${RTS}.dbo.tbluser where username =  ${userID}";
    SqlParametersMaps parms = new SqlParametersMaps ().put ("userID", userId);
    Map<String, String> unquotedParms = new HashMap<> ();
    unquotedParms.put ("RTS", rts);
    String finalQuery = fixDataBaseNames (SQL_QUERY);
    SingleDataResultSet result = executeStatement (connection, fixDataBaseNames (finalQuery, unquotedParms), parms, false).getResultSets ().next ();
    DbResultRecord record = (result.getCount () > 0 ? result.getRecords ().next () : null);
    if (record != null) {
      userKey.set (record.<Long> get ("userkey"));
      fullName.set (record.<String> get ("fullname"));
      password.set (record.<String> get ("password"));
      active.set (record.<Boolean> get ("active"));
      ack.set (record.<Boolean> get ("ack"));
    }
    List<CaseInsensitiveMap<Object>> resultlist = new ArrayList<CaseInsensitiveMap<Object>> ();
    CaseInsensitiveMap<Object> rcrd = new CaseInsensitiveMap<Object> ();
    rcrd.put ("userid", userId);
    rcrd.put ("userkey", userKey.get ());
    rcrd.put ("fullname", fullName.get ());
    rcrd.put ("password", password.get ());
    rcrd.put ("isActive", active.get ());
    rcrd.put ("hasAcknowledged", ack.get ());
    resultlist.add (rcrd);

    result = new SingleDataResultSet ();
    result.addColumn ("userid", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    result.addColumn ("userkey", SQL_TYPE_To_JAVA_TYPE.BIGINT);
    result.addColumn ("fullname", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    result.addColumn ("password", SQL_TYPE_To_JAVA_TYPE.VARCHAR);
    result.addColumn ("isActive", SQL_TYPE_To_JAVA_TYPE.BIT);
    result.addColumn ("hasAcknowledged", SQL_TYPE_To_JAVA_TYPE.BIT);
    result.addRecords (resultlist);

    _commonDll._LogDBLatency_SP (connection, "GetRTSUser", starttime, null, true, null, null, null, clientName, null);
    return result;
  }
  
  //TODO EF: temporary returning and empty result set
  public SingleDataResultSet  T_GetRTSAccommodations_SP (SQLConnection connection, String clientname, Long testee) throws ReturnStatusException {
    
    return( new SingleDataResultSet ());
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
    Boolean auditProc = null;
    Integer duration = null;
    SingleDataResultSet result = null;

    auditProc = _commonDll.AuditProc_FN (connection, "P_ValidateProctor");
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
  
  @Override
  public Long getOrCreateStudentKey (SQLConnection connection, String SSID, String clientCode) throws ReturnStatusException {
    throw new NotImplementedException ("This behavior is only implemented for MySQL RTSPackageDLL");
  }
//Implementation not required
  public int createUser(SQLConnection connection,String userId,String fullName) throws ReturnStatusException{
     return 0;
  }
//Implementation not required  
  public boolean userAlreadyExists(SQLConnection connection,String userId) throws ReturnStatusException {
    return false;
  }

//Implementation not required  
  public int createAndUpdateStudentIsCurrent (SQLConnection connection, Long testeeKey, String clientName, String studentPackage) {
    return 0;
  }

 //Implementation not required  
  public int createAndUpdateProctorIsCurrent (SQLConnection connection, Long key, String clientName, String proctorPackage, TestType testType) throws ReturnStatusException {
    return 0;
  }

}
