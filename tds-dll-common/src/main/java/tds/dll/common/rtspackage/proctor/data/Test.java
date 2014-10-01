/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.common.rtspackage.proctor.data;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType (XmlAccessType.FIELD)
@XmlType (name = "", propOrder = {
    "subjectCode",
    "testId",
    "testName",
    "type",
    "category",
    "maxOpps",
    "windowOpps",
    "delayRule",
    "windowStart"
})
public class Test implements Serializable
{

  private static final long      serialVersionUID = 1L;

  @XmlElement (name = "SubjectCode", required = true)
  protected String               subjectCode;
  @XmlElement (name = "TestId", required = true)
  protected String               testId;
  @XmlElement (name = "TestName", required = true)
  protected String               testName;
  @XmlElement (name = "Type")
  protected String               type;
  @XmlElement (name = "Category")
  protected String               category;
  @XmlElement (name = "MaxOpps")
  protected int                  maxOpps;
  @XmlElement (name = "WindowOpps")
  protected int                  windowOpps;
  @XmlElement (name = "DelayRule")
  protected int                  delayRule;
  @XmlElement (name = "WindowStart", required = true)
  protected XMLGregorianCalendar windowStart;

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
   * Gets the value of the testId property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getTestId () {
    return testId;
  }

  /**
   * Sets the value of the testId property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setTestId (String value) {
    this.testId = value;
  }

  /**
   * Gets the value of the testName property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getTestName () {
    return testName;
  }

  /**
   * Sets the value of the testName property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setTestName (String value) {
    this.testName = value;
  }
  
  
  /**
   * @return the type
   */
  public String getType () {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType (String type) {
    this.type = type;
  }

  /**
   * Gets the value of the category property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getCategory () {
    return category;
  }

  /**
   * Sets the value of the category property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setCategory (String value) {
    this.category = value;
  }

  /**
   * Gets the value of the maxOpps property.
   * 
   */
  public int getMaxOpps () {
    return maxOpps;
  }

  /**
   * Sets the value of the maxOpps property.
   * 
   */
  public void setMaxOpps (int value) {
    this.maxOpps = value;
  }

  /**
   * Gets the value of the windowOpps property.
   * 
   */
  public int getWindowOpps () {
    return windowOpps;
  }

  /**
   * Sets the value of the windowOpps property.
   * 
   */
  public void setWindowOpps (int value) {
    this.windowOpps = value;
  }

  /**
   * Gets the value of the delayRule property.
   * 
   */
  public int getDelayRule () {
    return delayRule;
  }

  /**
   * Sets the value of the delayRule property.
   * 
   */
  public void setDelayRule (int value) {
    this.delayRule = value;
  }

  /**
   * Gets the value of the windowStart property.
   * 
   * @return possible object is {@link XMLGregorianCalendar }
   * 
   */
  public XMLGregorianCalendar getWindowStart () {
    return windowStart;
  }

  /**
   * Sets the value of the windowStart property.
   * 
   * @param value
   *          allowed object is {@link XMLGregorianCalendar }
   * 
   */
  public void setWindowStart (XMLGregorianCalendar value) {
    this.windowStart = value;
  }

}
