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

@XmlAccessorType (XmlAccessType.FIELD)
@XmlType (name = "", propOrder = {
    "subjectCode",
    "americanSignLanguage",
    "closedCaptioning",
    "colorContrast",
    "texttoSpeech",
    "language",
    "masking",
    "permissiveMode",
    "translation",
    "printOnDemand",
    "printSize",
    "streamlinedInterface",
    "nonEmbeddedDesignatedSupports",
    "nonEmbeddedAccommodations",
    "other"
})
public class Accommodation implements Serializable
{

  private static final long serialVersionUID = 1L;

  @XmlElement (name = "SubjectCode", required = true)
  protected String          subjectCode;
  @XmlElement (name = "AmericanSignLanguage")
  protected String          americanSignLanguage;
  @XmlElement (name = "ClosedCaptioning")
  protected String          closedCaptioning;
  @XmlElement (name = "ColorContrast")
  protected String          colorContrast;
  @XmlElement (name = "TexttoSpeech")
  protected String          texttoSpeech;
  @XmlElement (name = "Language")
  protected String          language;
  @XmlElement (name = "Masking")
  protected String          masking;
  @XmlElement (name = "PermissiveMode")
  protected String          permissiveMode;
  @XmlElement (name = "Translation")
  protected String          translation;
  @XmlElement (name = "PrintOnDemand")
  protected String          printOnDemand;
  @XmlElement (name = "PrintSize")
  protected String          printSize;
  @XmlElement (name = "StreamlinedInterface")
  protected String          streamlinedInterface;
  @XmlElement (name = "NonEmbeddedDesignatedSupports")
  protected String          nonEmbeddedDesignatedSupports;
  @XmlElement (name = "NonEmbeddedAccommodations")
  protected String          nonEmbeddedAccommodations;
  @XmlElement (name = "Other")
  protected String          other;

  /**
   * Gets the value of the subjectCode property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getSubjectCode () {
    return subjectCode;
  }

  /**
   * Sets the value of the subjectCode property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setSubjectCode (String value) {
    this.subjectCode = value;
  }

  /**
   * Gets the value of the americanSignLanguage property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getAmericanSignLanguage () {
    return americanSignLanguage;
  }

  /**
   * Sets the value of the americanSignLanguage property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setAmericanSignLanguage (String value) {
    this.americanSignLanguage = value;
  }

  /**
   * Gets the value of the closedCaptioning property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getClosedCaptioning () {
    return closedCaptioning;
  }

  /**
   * Sets the value of the closedCaptioning property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setClosedCaptioning (String value) {
    this.closedCaptioning = value;
  }

  /**
   * Gets the value of the colorContrast property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getColorContrast () {
    return colorContrast;
  }

  /**
   * Sets the value of the colorContrast property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setColorContrast (String value) {
    this.colorContrast = value;
  }

  /**
   * Gets the value of the texttoSpeech property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getTexttoSpeech () {
    return texttoSpeech;
  }

  /**
   * Sets the value of the texttoSpeech property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setTexttoSpeech (String value) {
    this.texttoSpeech = value;
  }

  /**
   * Gets the value of the language property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getLanguage () {
    return language;
  }

  /**
   * Sets the value of the language property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setLanguage (String value) {
    this.language = value;
  }

  /**
   * Gets the value of the masking property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getMasking () {
    return masking;
  }

  /**
   * Sets the value of the masking property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setMasking (String value) {
    this.masking = value;
  }

  /**
   * Gets the value of the permissiveMode property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getPermissiveMode () {
    return permissiveMode;
  }

  /**
   * Sets the value of the permissiveMode property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setPermissiveMode (String value) {
    this.permissiveMode = value;
  }

  /**
   * Gets the value of the translation property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getTranslation () {
    return translation;
  }

  /**
   * Sets the value of the translation property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setTranslation (String value) {
    this.translation = value;
  }

  /**
   * Gets the value of the printOnDemand property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getPrintOnDemand () {
    return printOnDemand;
  }

  /**
   * Sets the value of the printOnDemand property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setPrintOnDemand (String value) {
    this.printOnDemand = value;
  }

  /**
   * Gets the value of the printSize property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getPrintSize () {
    return printSize;
  }

  /**
   * Sets the value of the printSize property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setPrintSize (String value) {
    this.printSize = value;
  }

  /**
   * Gets the value of the streamlinedInterface property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getStreamlinedInterface () {
    return streamlinedInterface;
  }

  /**
   * Sets the value of the streamlinedInterface property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setStreamlinedInterface (String value) {
    this.streamlinedInterface = value;
  }

  /**
   * Gets the value of the nonEmbeddedDesignatedSupports property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getNonEmbeddedDesignatedSupports () {
    return nonEmbeddedDesignatedSupports;
  }

  /**
   * Sets the value of the nonEmbeddedDesignatedSupports property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setNonEmbeddedDesignatedSupports (String value) {
    this.nonEmbeddedDesignatedSupports = value;
  }

  /**
   * Gets the value of the nonEmbeddedAccommodations property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getNonEmbeddedAccommodations () {
    return nonEmbeddedAccommodations;
  }

  /**
   * Sets the value of the nonEmbeddedAccommodations property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setNonEmbeddedAccommodations (String value) {
    this.nonEmbeddedAccommodations = value;
  }

  /**
   * Gets the value of the other property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getOther () {
    return other;
  }

  /**
   * Sets the value of the other property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setOther (String value) {
    this.other = value;
  }

}
