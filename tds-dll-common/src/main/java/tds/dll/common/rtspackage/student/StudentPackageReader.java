/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.common.rtspackage.student;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StringReader;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.opentestsystem.shared.trapi.data.Ethnicity;
import org.opentestsystem.shared.trapi.data.RoleLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tds.dll.common.rtspackage.IRtsPackageReader;
import tds.dll.common.rtspackage.common.exception.RtsPackageReaderException;
import tds.dll.common.rtspackage.common.table.RtsRecord;
import tds.dll.common.rtspackage.common.table.RtsTable;
import tds.dll.common.rtspackage.student.data.Accommodation;
import tds.dll.common.rtspackage.student.data.AccommodationOther;
import tds.dll.common.rtspackage.student.data.Student;
import tds.dll.common.rtspackage.student.data.StudentPackage;
import tds.dll.common.rtspackage.student.data.Test;
import tds.dll.common.rtspackage.student.data.Tests;

/**
 * @author jmambo
 * 
 */
public class StudentPackageReader implements IRtsPackageReader
{

  private static final Logger _logger = LoggerFactory.getLogger (StudentPackageReader.class);
  
  private Student        _student;

  private StudentPackage _studentPackage;
  
  private static JAXBContext  _jaxbContext = getJaxbContext();


  /**
   * Gets a field value
   * 
   * @param fieldName
   * @return field value
   */
  public String getFieldValue (String fieldName) {
    String value = null;
    switch (fieldName) {
    case "--ACCOMMODATIONS--":
      value = getAccommodations ();
      break;
    case "--ENTITYNAME--":
      value = getEntityName ();
      break;
    case "--ETHNICITY--":
      value = getEthnicity ();
      break;
    case "--ELIGIBLETESTS--":
      value = getEligibleTests ();
      break;
    case "BirthDtTxt":
      String birthDate = _student.getBirthdate ();
      if (!StringUtils.isBlank (birthDate)) {
         String[] dateTokens = birthDate.split ("-");
         value = dateTokens[1] + dateTokens[2] + dateTokens[0];
      }
      break;
    case "LglFNm":
      value = _student.getFirstName ();
      break;
    case "LglLNm":
      value = _student.getLastOrSurname ();
      break;
    case "Gndr":
      if (!StringUtils.isBlank (_student.getSex ())) {
         value = _student.getSex ().substring (0, 1);
      }
      break;
    case "EnrlGrdCd":
      value = _student.getGradeLevelWhenAssessed ();
      break;
    case "ExternalID":
      value = _student.getAlternateSSID ();
      break;
    case "LEPfg":
      value = _student.getLEPStatus ();
      break;
    case "BLOCKEDSUBJECT":
      value = null;
      break;
    case "ResponsibleSchoolId":
      value = _student.getResponsibleInstitutionIdentifier ();
      break;
    case "TDS-TestForm":
      value = getTestForms ();
      break;
    default:
      break;
    }
    return value;
  }

  /**
   * Gets RtsRecord
   * 
   * @param fieldName
   * @return RtsRecord
   */
  public RtsRecord getRtsRecord (String fieldName) {
    RtsRecord rtsRecord = null;
    switch (fieldName) {
    case "ENRLINST-STUDENT":
      rtsRecord = getInstitution ();
      break;
    case "ENRDIST-STUDENT":
      rtsRecord = getDistrict ();
      break;
    case "STATE-STUDENT":
      rtsRecord = getState ();
      break;
    case "INST-HIERARCHY":
      rtsRecord = getInstitutionHierarchy ();
      break;
    default:
      break;
    }
    return rtsRecord;
  }

  @Override
  public RtsTable getRtsTable (String fieldName) {
    if (fieldName.equals ("--ACCOMMODATIONS--")) {
      return getAccommodationList ();
    } else if (fieldName.equals ("Tests")) {
      return getTests ();
    } 
    return null;
  }

  @Override
  public <T> T getPackage (Class<T> clazz) {
    return clazz.cast (_studentPackage);
  }
  
  @Override
  public boolean read (byte[] pkg) throws RtsPackageReaderException {
    if (pkg == null) {
      throw new RtsPackageReaderException ("Package object is null");
    }
    try (ObjectInputStream objectIn = new ObjectInputStream (new GZIPInputStream (new ByteArrayInputStream (pkg)))) {
      _studentPackage = (StudentPackage) objectIn.readObject ();
      _student = _studentPackage.getStudent ();
      return true;
    } catch (IOException | ClassNotFoundException e) {
      throw new RtsPackageReaderException (e.getMessage (), e);
    }
  }

 //TODO: This will not be needed once ART has fixed student endpoint to return queries by ssid and externalID
  @Override
  public boolean read (String pkg) throws RtsPackageReaderException {
    if (pkg == null) {
      throw new RtsPackageReaderException ("Package XML is null");
    }
     try {
       Unmarshaller jaxbUnmarshaller = _jaxbContext.createUnmarshaller ();
       _studentPackage = (StudentPackage) jaxbUnmarshaller.unmarshal (new StringReader (pkg));
       _student = _studentPackage.getStudent ();
     } catch (JAXBException e) {
       _logger.debug ("Student Package: {}",  pkg );
       throw new RtsPackageReaderException (e.getMessage (), e);
     }
     return true;
    
  }
  
  private RtsRecord getInstitution () {
    return new RtsRecord (new String[][] {
        {"entityKey", _student.getResponsibleInstitutionIdentifier ()},
        {"entityId", _student.getResponsibleInstitutionIdentifier ()},
        {"entityName", _student.getNameOfInstitution ()}
      });
  }

  private RtsRecord getDistrict () {
    return new RtsRecord (new String[][] {
        {"entityKey", _student.getResponsibleDistrictIdentifier ()},
        {"entityId", _student.getResponsibleDistrictIdentifier ()},
        {"entityName", _student.getOrganizationName ()},
    });
  }
  
  private RtsRecord getState () {
    return new RtsRecord (new String[][] {
        {"entityKey", _student.getStateAbbreviation ()},
        {"entityId", _student.getStateAbbreviation ()},
        {"entityName", _student.getStateName ()},
    });
  }

  private String getAccommodations () {
    List<Accommodation> accomodations = _student.getAccommodations ().getAccommodation ();
    StringBuilder sb = new StringBuilder ();
    for (Accommodation ac : accomodations) {
      String subject = ac.getSubjectCode () + ":";

      appendAccommodation (sb, subject, ac.getAmericanSignLanguage ());
      appendAccommodation (sb, subject, ac.getClosedCaptioning ());
      appendAccommodation (sb, subject, ac.getColorContrast ());
      appendAccommodation (sb, subject, ac.getTexttoSpeech ());
      appendAccommodation (sb, subject, ac.getTranslation ());
      appendAccommodation (sb, subject, ac.getMasking ());
      appendAccommodation (sb, subject, ac.getLanguage ());
      appendAccommodation (sb, subject, ac.getPrintOnDemand ());
      appendAccommodation (sb, subject, ac.getPrintSize ());
      appendAccommodation (sb, subject, ac.getPermissiveMode ());
      appendAccommodation (sb, subject, ac.getStreamlinedInterface ());
      appendAccommodation (sb, subject, ac.getIllustrationGlossary ());
      appendAccommodation (sb, subject, AccommodationOther.getFormattedValue (ac));
      if ( ac.getNonEmbeddedAccommodations () != null ) {
        for (String nonEmbeddedAccommodation : ac.getNonEmbeddedAccommodations () ) {
            appendAccommodation (sb, subject, nonEmbeddedAccommodation);
        }
      }
      if ( ac.getNonEmbeddedDesignatedSupports () != null ) {
        for (String nonEmbeddedDesignatedSupport : ac.getNonEmbeddedDesignatedSupports () ) {
            appendAccommodation (sb, subject, nonEmbeddedDesignatedSupport);
        }
      }
    }
    if (sb.length () > 0) {
      sb.setLength (sb.length () - 1);
      return sb.toString ();
    }
    return null;
  }

  private RtsTable getAccommodationList () {
    List<Accommodation> accomodations = _student.getAccommodations ().getAccommodation ();
    RtsTable accommodationList = new RtsTable ();
    for (Accommodation ac : accomodations) {
      String subjectCode = ac.getSubjectCode ();
      addAccommodation (accommodationList, subjectCode, ac.getAmericanSignLanguage ());
      addAccommodation (accommodationList, subjectCode, ac.getClosedCaptioning ());
      addAccommodation (accommodationList, subjectCode, ac.getColorContrast ());
      addAccommodation (accommodationList, subjectCode, ac.getTexttoSpeech ());
      addAccommodation (accommodationList, subjectCode, ac.getTranslation ());
      addAccommodation (accommodationList, subjectCode, ac.getMasking ());
      addAccommodation (accommodationList, subjectCode, ac.getLanguage ());
      addAccommodation (accommodationList, subjectCode, ac.getPrintOnDemand ());
      addAccommodation (accommodationList, subjectCode, ac.getPrintSize ());
      addAccommodation (accommodationList, subjectCode, ac.getPermissiveMode ());
      addAccommodation (accommodationList, subjectCode, ac.getStreamlinedInterface ());
      addAccommodation (accommodationList, subjectCode, ac.getIllustrationGlossary ());
      addAccommodation (accommodationList, subjectCode, AccommodationOther.getFormattedValue (ac));
      if ( ac.getNonEmbeddedAccommodations () != null ) {
        for (String nonEmbeddedAccommodation : ac.getNonEmbeddedAccommodations () ) {
            addAccommodation (accommodationList, subjectCode, nonEmbeddedAccommodation);
        }
      }
      if ( ac.getNonEmbeddedDesignatedSupports () != null ) {
        for (String nonEmbeddedDesignatedSupport : ac.getNonEmbeddedDesignatedSupports ()) {
            addAccommodation (accommodationList, subjectCode, nonEmbeddedDesignatedSupport);
        }
      }
    }
    return accommodationList;
  }

  /**
   * Adds accommodation to the list
   * 
   * @param accomodationList
   * @param subjectCode
   * @param accomodationCode
   */
  private void addAccommodation (RtsTable accomodationList, String subjectCode, String accomodationCode) {
    if (StringUtils.isNotBlank (accomodationCode)) {
      accomodationList.addRecord (new RtsRecord (new String[][] {
          {"Subject", subjectCode},
          {"AccCode", accomodationCode},
      }));
    }
  }

  /**
   * Appends accommodation to StringBuilder
   * 
   * @param accomodationList
   * @param subjectCode
   * @param accomodationCode
   */
  private void appendAccommodation (StringBuilder stringBuilder, String subjectCode, String accomodationCode) {
    if (StringUtils.isNotBlank (accomodationCode)) {
      stringBuilder.append (subjectCode).append (accomodationCode).append (";");
    }
  }

  private String getEntityName () {
    String middleName = (_student.getMiddleName () == null) ? "" : " " + _student.getMiddleName ();
    _student.getFirstName ();
    return _student.getLastOrSurname () + ", " + _student.getFirstName () + middleName;
  }

  private String getEthnicity () {
    StringBuilder ethnicities = new StringBuilder();
    appendEthnicity(ethnicities, Ethnicity.HispanicOrLatinoEthnicity, _student.getHispanicOrLatinoEthnicity());
    appendEthnicity(ethnicities, Ethnicity.AmericanIndianOrAlaskaNative, _student.getAmericanIndianOrAlaskaNative());
    appendEthnicity(ethnicities, Ethnicity.Asian, _student.getAsian());
    appendEthnicity(ethnicities, Ethnicity.BlackOrAfricanAmerican, _student.getBlackOrAfricanAmerican());
    appendEthnicity(ethnicities, Ethnicity.White, _student.getWhite());
    appendEthnicity(ethnicities, Ethnicity.NativeHawaiianOrOtherPacificIslander, _student.getNativeHawaiianOrOtherPacificIslander());
    appendEthnicity(ethnicities, Ethnicity.DemographicRaceTwoOrMoreRaces, _student.getDemographicRaceTwoOrMoreRaces());
    if (ethnicities.length () > 0) {
      ethnicities.setLength (ethnicities.length () - 2);
    }
    return ethnicities.toString ();
  }
  
  private void appendEthnicity(StringBuilder ethnicities, Ethnicity ethnicity,  String value) {
    if (value != null && value.equalsIgnoreCase ("Yes")) {
      ethnicities.append (ethnicity.name ()) .append(", ");
    }
  }

  private String getEligibleTests () {
    Tests tests = _student.getTests ();
    StringBuilder sb = new StringBuilder ();
    for (Test test : tests.getTest ()) {
      //TODO: jmambo use TestId()
      sb.append (test.getTestName ()).append (';');
    }
    if (sb.length () > 0) {
      sb.setLength (sb.length () - 1);
      return sb.toString ();
    }
    return null;
  }

  private String getTestForms () {
    Tests tests = _student.getTests ();
    StringBuilder sb = new StringBuilder ();
    for (Test test : tests.getTest ()) {
      // test.getTestForm ()
      //TODO: jmambo use TestId()
      sb.append (test.getTestName ()).append (";");
    }
    if (sb.length () > 0) {
      sb.setLength (sb.length () - 1);
      return sb.toString ();
    }
    return null;
  }

  private RtsRecord getInstitutionHierarchy () {
    return new RtsRecord (new String[][] {
        {RoleLevel.INSTITUTION.name (), _student.getResponsibleInstitutionIdentifier ()},
        {RoleLevel.GROUPOFINSTITUTIONS.name (), _student.getGroupOfInstitutionsIdentifier ()},
        {RoleLevel.DISTRICT.name (), _student.getResponsibleDistrictIdentifier ()},
        {RoleLevel.GROUPOFDISTRICTS.name (), _student.getGroupOfDistrictsIdentifier ()},
        {RoleLevel.STATE.name (), _student.getStateAbbreviation ()},
        {RoleLevel.GROUPOFSTATES.name (), _student.getGroupOfStatesIdentifier ()},
        {RoleLevel.CLIENT.name (), _student.getClientId ()}
    });
  }
  
  private RtsTable getTests () {
    Tests tests = _student.getTests ();
    RtsTable rtsTable = new RtsTable();
    for (Test test : tests.getTest ()) {
      rtsTable.addRecord (new RtsRecord (new String[][] {
          {"TestID", test.getTestId ()},  
          {"Subject", test.getSubjectCode ()},
          {"TestKey", test.getTestName ()}
      }));
    }
    return rtsTable;
  }
  
  
  //TODO: This will not be needed once ART has fixed student end point to return queries by ssid and externalID
  private static JAXBContext getJaxbContext() {
    try {
      return JAXBContext.newInstance (StudentPackage.class);
    } catch (JAXBException e) {
      _logger.error (e.getMessage (), e);
    }
    return null;
 }

}
