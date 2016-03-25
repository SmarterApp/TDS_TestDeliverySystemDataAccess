/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.common.rtspackage.student.data;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType (XmlAccessType.FIELD)
@XmlType (name = "", propOrder = {
    "clientId",
    "clientName",
    "groupOfStatesIdentifier",
    "stateAbbreviation",
    "stateName",
    "groupOfDistrictsIdentifier",
    "groupOfDistrictsName",
    "responsibleDistrictIdentifier",
    "organizationName",
    "groupOfInstitutionsIdentifier",
    "groupOfInstitutionsName",
    "responsibleInstitutionIdentifier",
    "nameOfInstitution",
    "lastOrSurname",
    "firstName",
    "middleName",
    "birthdate",
    "studentIdentifier",
    "alternateSSID",
    "gradeLevelWhenAssessed",
    "sex",
    "hispanicOrLatinoEthnicity",
    "americanIndianOrAlaskaNative",
    "asian",
    "blackOrAfricanAmerican",
    "white",
    "nativeHawaiianOrOtherPacificIslander",
    "demographicRaceTwoOrMoreRaces",
    "ideaIndicator",
    "lepStatus",
    "section504Status",
    "economicDisadvantageStatus",
    "languageCode",
    "englishLanguageProficiencyLevel",
    "migrantStatus",
    "firstEntryDateIntoUSSchool",
    "limitedEnglishProficiencyEntryDate",
    "lepExitDate",
    "titleIIILanguageInstructionProgramType",
    "primaryDisabilityType",
    "shareIdentityData",
    "tests",
    "accommodations",
    "groups"
})
public class Student implements Serializable
{

  private static final long      serialVersionUID = 1L;

  @XmlElement (name = "ClientId", required = true)
  protected String               clientId;
  @XmlElement (name = "ClientName", required = true)
  protected String               clientName;
  @XmlElement (name = "GroupOfStatesIdentifier")
  protected String               groupOfStatesIdentifier;
  @XmlElement (name = "StateAbbreviation", required = true)
  protected String               stateAbbreviation;
  @XmlElement (name = "StateName", required = true)
  protected String               stateName;
  @XmlElement (name = "GroupOfDistrictsIdentifier", required = true)
  protected String               groupOfDistrictsIdentifier;
  @XmlElement (name = "GroupOfDistrictsName", required = true)
  protected String               groupOfDistrictsName;
  @XmlElement (name = "ResponsibleDistrictIdentifier", required = true)
  protected String               responsibleDistrictIdentifier;
  @XmlElement (name = "OrganizationName", required = true)
  protected String               organizationName;
  @XmlElement (name = "GroupOfInstitutionsIdentifier", required = true)
  protected String               groupOfInstitutionsIdentifier;
  @XmlElement (name = "GroupOfInstitutionsName", required = true)
  protected String               groupOfInstitutionsName;
  @XmlElement (name = "ResponsibleInstitutionIdentifier", required = true)
  protected String               responsibleInstitutionIdentifier;
  @XmlElement (name = "NameOfInstitution", required = true)
  protected String               nameOfInstitution;
  @XmlElement (name = "LastOrSurname", required = true)
  protected String               lastOrSurname;
  @XmlElement (name = "FirstName", required = true)
  protected String               firstName;
  @XmlElement (name = "MiddleName")
  protected String               middleName;
  @XmlElement (name = "Birthdate", required = true)
  protected String               birthdate;
  @XmlElement (name = "StudentIdentifier", required = true)
  protected String               studentIdentifier;
  @XmlElement (name = "AlternateSSID", required = true)
  protected String               alternateSSID;
  @XmlElement (name = "GradeLevelWhenAssessed", required = true)
  protected String               gradeLevelWhenAssessed;
  @XmlElement (name = "Sex", required = true)
  protected String               sex;
  @XmlElement (name = "HispanicOrLatinoEthnicity", required = true)
  protected String               hispanicOrLatinoEthnicity;
  @XmlElement (name = "AmericanIndianOrAlaskaNative", required = true)
  protected String               americanIndianOrAlaskaNative;
  @XmlElement (name = "Asian", required = true)
  protected String               asian;
  @XmlElement (name = "BlackOrAfricanAmerican", required = true)
  protected String               blackOrAfricanAmerican;
  @XmlElement (name = "White", required = true)
  protected String               white;
  @XmlElement (name = "NativeHawaiianOrOtherPacificIslander", required = true)
  protected String               nativeHawaiianOrOtherPacificIslander;
  @XmlElement (name = "DemographicRaceTwoOrMoreRaces", required = true)
  protected String               demographicRaceTwoOrMoreRaces;
  @XmlElement (name = "IDEAIndicator", required = true)
  protected String               ideaIndicator;
  @XmlElement (name = "LEPStatus", required = true)
  protected String               lepStatus;
  @XmlElement (name = "Section504Status", required = true)
  protected String               section504Status;
  @XmlElement (name = "EconomicDisadvantageStatus", required = true)
  protected String               economicDisadvantageStatus;
  @XmlElement (name = "LanguageCode")
  protected String               languageCode;
  @XmlElement (name = "EnglishLanguageProficiencyLevel")
  protected String               englishLanguageProficiencyLevel;
  @XmlElement (name = "MigrantStatus")
  protected String               migrantStatus;
  @XmlElement (name = "FirstEntryDateIntoUSSchool")
  protected XMLGregorianCalendar firstEntryDateIntoUSSchool;
  @XmlElement (name = "LimitedEnglishProficiencyEntryDate")
  protected XMLGregorianCalendar limitedEnglishProficiencyEntryDate;
  @XmlElement (name = "LEPExitDate")
  protected XMLGregorianCalendar lepExitDate;
  @XmlElement (name = "TitleIIILanguageInstructionProgramType")
  protected String               titleIIILanguageInstructionProgramType;
  @XmlElement (name = "PrimaryDisabilityType")
  protected String               primaryDisabilityType;
  @XmlElement (name = "ShareIdentityData", required = true)
  protected String               shareIdentityData;
  @XmlElement (name = "Tests", required = true)
  protected Tests                tests;
  @XmlElement (name = "Accommodations", required = true)
  protected Accommodations       accommodations;
  @XmlElement (name = "Groups", required = true)
  protected Groups               groups;

  
  
  /**
   * @return the clientId
   */
  public String getClientId () {
    return clientId;
  }

  /**
   * @param clientId the clientId to set
   */
  public void setClientId (String clientId) {
    this.clientId = clientId;
  }

  /**
   * @return the clientName
   */
  public String getClientName () {
    return clientName;
  }

  /**
   * @param clientName the clientName to set
   */
  public void setClientName (String clientName) {
    this.clientName = clientName;
  }

  /**
   * @return the groupOfStatesIdentifier
   */
  public String getGroupOfStatesIdentifier () {
    return groupOfStatesIdentifier;
  }

  /**
   * @param groupOfStatesIdentifier the groupOfStatesIdentifier to set
   */
  public void setGroupOfStatesIdentifier (String groupOfStatesIdentifier) {
    this.groupOfStatesIdentifier = groupOfStatesIdentifier;
  }

  /**
   * @return the ideaIndicator
   */
  public String getIdeaIndicator () {
    return ideaIndicator;
  }

  /**
   * @param ideaIndicator the ideaIndicator to set
   */
  public void setIdeaIndicator (String ideaIndicator) {
    this.ideaIndicator = ideaIndicator;
  }

  /**
   * @return the lepStatus
   */
  public String getLepStatus () {
    return lepStatus;
  }

  /**
   * @param lepStatus the lepStatus to set
   */
  public void setLepStatus (String lepStatus) {
    this.lepStatus = lepStatus;
  }

  /**
   * @return the lepExitDate
   */
  public XMLGregorianCalendar getLepExitDate () {
    return lepExitDate;
  }

  /**
   * @param lepExitDate the lepExitDate to set
   */
  public void setLepExitDate (XMLGregorianCalendar lepExitDate) {
    this.lepExitDate = lepExitDate;
  }

  /**
   * Gets the value of the stateAbbreviation property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getStateAbbreviation () {
    return stateAbbreviation;
  }

  /**
   * Sets the value of the stateAbbreviation property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setStateAbbreviation (String value) {
    this.stateAbbreviation = value;
  }

  /**
   * Gets the value of the stateName property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getStateName () {
    return stateName;
  }

  /**
   * Sets the value of the stateName property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setStateName (String value) {
    this.stateName = value;
  }

  /**
   * Gets the value of the groupOfDistrictsIdentifier property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getGroupOfDistrictsIdentifier () {
    return groupOfDistrictsIdentifier;
  }

  /**
   * Sets the value of the groupOfDistrictsIdentifier property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setGroupOfDistrictsIdentifier (String value) {
    this.groupOfDistrictsIdentifier = value;
  }

  /**
   * Gets the value of the groupOfDistrictsName property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getGroupOfDistrictsName () {
    return groupOfDistrictsName;
  }

  /**
   * Sets the value of the groupOfDistrictsName property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setGroupOfDistrictsName (String value) {
    this.groupOfDistrictsName = value;
  }

  /**
   * Gets the value of the responsibleDistrictIdentifier property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getResponsibleDistrictIdentifier () {
    return responsibleDistrictIdentifier;
  }

  /**
   * Sets the value of the responsibleDistrictIdentifier property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setResponsibleDistrictIdentifier (String value) {
    this.responsibleDistrictIdentifier = value;
  }

  /**
   * Gets the value of the organizationName property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getOrganizationName () {
    return organizationName;
  }

  /**
   * Sets the value of the organizationName property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setOrganizationName (String value) {
    this.organizationName = value;
  }

  /**
   * Gets the value of the groupOfInstitutionsIdentifier property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getGroupOfInstitutionsIdentifier () {
    return groupOfInstitutionsIdentifier;
  }

  /**
   * Sets the value of the groupOfInstitutionsIdentifier property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setGroupOfInstitutionsIdentifier (String value) {
    this.groupOfInstitutionsIdentifier = value;
  }

  /**
   * Gets the value of the groupOfInstitutionsName property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getGroupOfInstitutionsName () {
    return groupOfInstitutionsName;
  }

  /**
   * Sets the value of the groupOfInstitutionsName property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setGroupOfInstitutionsName (String value) {
    this.groupOfInstitutionsName = value;
  }

  /**
   * Gets the value of the responsibleInstitutionIdentifier property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getResponsibleInstitutionIdentifier () {
    return responsibleInstitutionIdentifier;
  }

  /**
   * Sets the value of the responsibleInstitutionIdentifier property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setResponsibleInstitutionIdentifier (String value) {
    this.responsibleInstitutionIdentifier = value;
  }

  /**
   * Gets the value of the nameOfInstitution property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getNameOfInstitution () {
    return nameOfInstitution;
  }

  /**
   * Sets the value of the nameOfInstitution property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setNameOfInstitution (String value) {
    this.nameOfInstitution = value;
  }

  /**
   * Gets the value of the lastOrSurname property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getLastOrSurname () {
    return lastOrSurname;
  }

  /**
   * Sets the value of the lastOrSurname property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setLastOrSurname (String value) {
    this.lastOrSurname = value;
  }

  /**
   * Gets the value of the firstName property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getFirstName () {
    return firstName;
  }

  /**
   * Sets the value of the firstName property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setFirstName (String value) {
    this.firstName = value;
  }

  /**
   * Gets the value of the middleName property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getMiddleName () {
    return middleName;
  }

  /**
   * Sets the value of the middleName property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setMiddleName (String value) {
    this.middleName = value;
  }

  /**
   * Gets the value of the birthdate property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getBirthdate () {
    return birthdate;
  }

  /**
   * Sets the value of the birthdate property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setBirthdate (String value) {
    this.birthdate = value;
  }

  /**
   * Gets the value of the studentIdentifier property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getStudentIdentifier () {
    return studentIdentifier;
  }

  /**
   * Sets the value of the studentIdentifier property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setStudentIdentifier (String value) {
    this.studentIdentifier = value;
  }

  /**
   * Gets the value of the alternateSSID property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getAlternateSSID () {
    return alternateSSID;
  }

  /**
   * Sets the value of the alternateSSID property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setAlternateSSID (String value) {
    this.alternateSSID = value;
  }

  /**
   * Gets the value of the gradeLevelWhenAssessed property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getGradeLevelWhenAssessed () {
    return gradeLevelWhenAssessed;
  }

  /**
   * Sets the value of the gradeLevelWhenAssessed property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setGradeLevelWhenAssessed (String value) {
    this.gradeLevelWhenAssessed = value;
  }

  /**
   * Gets the value of the sex property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getSex () {
    return sex;
  }

  /**
   * Sets the value of the sex property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setSex (String value) {
    this.sex = value;
  }

  /**
   * Gets the value of the hispanicOrLatinoEthnicity property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getHispanicOrLatinoEthnicity () {
    return hispanicOrLatinoEthnicity;
  }

  /**
   * Sets the value of the hispanicOrLatinoEthnicity property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setHispanicOrLatinoEthnicity (String value) {
    this.hispanicOrLatinoEthnicity = value;
  }

  /**
   * Gets the value of the americanIndianOrAlaskaNative property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getAmericanIndianOrAlaskaNative () {
    return americanIndianOrAlaskaNative;
  }

  /**
   * Sets the value of the americanIndianOrAlaskaNative property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setAmericanIndianOrAlaskaNative (String value) {
    this.americanIndianOrAlaskaNative = value;
  }

  /**
   * Gets the value of the asian property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getAsian () {
    return asian;
  }

  /**
   * Sets the value of the asian property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setAsian (String value) {
    this.asian = value;
  }

  /**
   * Gets the value of the blackOrAfricanAmerican property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getBlackOrAfricanAmerican () {
    return blackOrAfricanAmerican;
  }

  /**
   * Sets the value of the blackOrAfricanAmerican property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setBlackOrAfricanAmerican (String value) {
    this.blackOrAfricanAmerican = value;
  }

  /**
   * Gets the value of the white property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getWhite () {
    return white;
  }

  /**
   * Sets the value of the white property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setWhite (String value) {
    this.white = value;
  }

  /**
   * Gets the value of the nativeHawaiianOrOtherPacificIslander property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getNativeHawaiianOrOtherPacificIslander () {
    return nativeHawaiianOrOtherPacificIslander;
  }

  /**
   * Sets the value of the nativeHawaiianOrOtherPacificIslander property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setNativeHawaiianOrOtherPacificIslander (String value) {
    this.nativeHawaiianOrOtherPacificIslander = value;
  }

  /**
   * Gets the value of the demographicRaceTwoOrMoreRaces property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getDemographicRaceTwoOrMoreRaces () {
    return demographicRaceTwoOrMoreRaces;
  }

  /**
   * Sets the value of the demographicRaceTwoOrMoreRaces property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setDemographicRaceTwoOrMoreRaces (String value) {
    this.demographicRaceTwoOrMoreRaces = value;
  }

  /**
   * Gets the value of the ideaIndicator property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getIDEAIndicator () {
    return ideaIndicator;
  }

  /**
   * Sets the value of the ideaIndicator property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setIDEAIndicator (String value) {
    this.ideaIndicator = value;
  }

  /**
   * Gets the value of the lepStatus property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getLEPStatus () {
    return lepStatus;
  }

  /**
   * Sets the value of the lepStatus property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setLEPStatus (String value) {
    this.lepStatus = value;
  }

  /**
   * Gets the value of the section504Status property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getSection504Status () {
    return section504Status;
  }

  /**
   * Sets the value of the section504Status property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setSection504Status (String value) {
    this.section504Status = value;
  }

  /**
   * Gets the value of the economicDisadvantageStatus property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getEconomicDisadvantageStatus () {
    return economicDisadvantageStatus;
  }

  /**
   * Sets the value of the economicDisadvantageStatus property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setEconomicDisadvantageStatus (String value) {
    this.economicDisadvantageStatus = value;
  }

  /**
   * Gets the value of the languageCode property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getLanguageCode () {
    return languageCode;
  }

  /**
   * Sets the value of the languageCode property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setLanguageCode (String value) {
    this.languageCode = value;
  }

  /**
   * Gets the value of the englishLanguageProficiencyLevel property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getEnglishLanguageProficiencyLevel () {
    return englishLanguageProficiencyLevel;
  }

  /**
   * Sets the value of the englishLanguageProficiencyLevel property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setEnglishLanguageProficiencyLevel (String value) {
    this.englishLanguageProficiencyLevel = value;
  }

  /**
   * Gets the value of the migrantStatus property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getMigrantStatus () {
    return migrantStatus;
  }

  /**
   * Sets the value of the migrantStatus property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setMigrantStatus (String value) {
    this.migrantStatus = value;
  }

  /**
   * Gets the value of the firstEntryDateIntoUSSchool property.
   *
   * @return possible object is {@link XMLGregorianCalendar }
   *
   */
  public XMLGregorianCalendar getFirstEntryDateIntoUSSchool () {
    return firstEntryDateIntoUSSchool;
  }

  /**
   * Sets the value of the firstEntryDateIntoUSSchool property.
   *
   * @param value
   *          allowed object is {@link XMLGregorianCalendar }
   *
   */
  public void setFirstEntryDateIntoUSSchool (XMLGregorianCalendar value) {
    this.firstEntryDateIntoUSSchool = value;
  }

  /**
   * Gets the value of the limitedEnglishProficiencyEntryDate property.
   *
   * @return possible object is {@link XMLGregorianCalendar }
   *
   */
  public XMLGregorianCalendar getLimitedEnglishProficiencyEntryDate () {
    return limitedEnglishProficiencyEntryDate;
  }

  /**
   * Sets the value of the limitedEnglishProficiencyEntryDate property.
   *
   * @param value
   *          allowed object is {@link XMLGregorianCalendar }
   *
   */
  public void setLimitedEnglishProficiencyEntryDate (XMLGregorianCalendar value) {
    this.limitedEnglishProficiencyEntryDate = value;
  }

  /**
   * Gets the value of the lepExitDate property.
   *
   * @return possible object is {@link XMLGregorianCalendar }
   *
   */
  public XMLGregorianCalendar getLEPExitDate () {
    return lepExitDate;
  }

  /**
   * Sets the value of the lepExitDate property.
   *
   * @param value
   *          allowed object is {@link XMLGregorianCalendar }
   *
   */
  public void setLEPExitDate (XMLGregorianCalendar value) {
    this.lepExitDate = value;
  }

  /**
   * Gets the value of the titleIIILanguageInstructionProgramType property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getTitleIIILanguageInstructionProgramType () {
    return titleIIILanguageInstructionProgramType;
  }

  /**
   * Sets the value of the titleIIILanguageInstructionProgramType property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setTitleIIILanguageInstructionProgramType (String value) {
    this.titleIIILanguageInstructionProgramType = value;
  }

  /**
   * Gets the value of the primaryDisabilityType property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getPrimaryDisabilityType () {
    return primaryDisabilityType;
  }

  /**
   * Sets the value of the primaryDisabilityType property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setPrimaryDisabilityType (String value) {
    this.primaryDisabilityType = value;
  }

  /**
   * Gets the value of the shareIdentityData property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getShareIdentityData () {
    return shareIdentityData;
  }

  /**
   * Sets the value of the shareIdentityData property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setShareIdentityData (String value) {
    this.shareIdentityData = value;
  }

  /**
   * Gets the value of the tests property.
   *
   * @return possible object is {@link Tests }
   *
   */
  public Tests getTests () {
    return tests;
  }

  /**
   * Sets the value of the tests property.
   *
   * @param value
   *          allowed object is {@link Tests }
   *
   */
  public void setTests (Tests value) {
    this.tests = value;
  }

  /**
   * Gets the value of the accommodations property.
   *
   * @return possible object is {@link Accommodations }
   *
   */
  public Accommodations getAccommodations () {
    return accommodations;
  }

  /**
   * Sets the value of the accommodations property.
   *
   * @param value
   *          allowed object is {@link Accommodations }
   *
   */
  public void setAccommodations (Accommodations value) {
    this.accommodations = value;
  }

  /**
   * Gets the value of the groups property.
   *
   * @return possible object is {@link Groups }
   *
   */
  public Groups getGroups () {
    return groups;
  }

  /**
   * Sets the value of the groups property.
   *
   * @param value
   *          allowed object is {@link Groups }
   *
   */
  public void setGroups (Groups value) {
    this.groups = value;
  }

}
