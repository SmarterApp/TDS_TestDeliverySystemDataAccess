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
    "testId",
    "testName",
    "testForm"
})
public class Test implements Serializable
{

  private static final long serialVersionUID = 1L;

  @XmlElement (name = "SubjectCode", required = true)
  protected String          subjectCode;
  @XmlElement (name = "TestId", required = true)
  protected String          testId;
  @XmlElement (name = "TestName", required = true)
  protected String          testName;
  @XmlElement (name = "TestForm")
  protected String          testForm;

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
   * @return the testId
   */
  public String getTestId () {
    return testId;
  }

  /**
   * @param testId the testId to set
   */
  public void setTestId (String testId) {
    this.testId = testId;
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
   * Gets the value of the testForm property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getTestForm () {
    return testForm;
  }

  /**
   * Sets the value of the testForm property.
   *
   * @param value
   *          allowed object is {@link String }
   *
   */
  public void setTestForm (String value) {
    this.testForm = value;
  }
}
