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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * StudentPackage XML Mapper. Works with Version 1
 * 
 * @author jmambo
 * 
 */
@XmlAccessorType (XmlAccessType.FIELD)
@XmlType (name = "", propOrder = {
    "student"
})
@XmlRootElement (name = "StudentPackage")
public class StudentPackage implements Serializable
{

  private static final long serialVersionUID = 1L;

  @XmlElement (name = "Student", required = true)
  protected Student         student;
  @XmlAttribute (name = "version", required = true)
  protected String          version;

  /**
   * Gets the value of the student property.
   *
   * @return possible object is {@link StudentPackage.Student }
   *
   */
  public Student getStudent () {
    return student;
  }

  /**
   * Sets the value of the student property.
   *
   * @param value
   *          allowed object is {@link StudentPackage.Student }
   *
   */
  public void setStudent (Student value) {
    this.student = value;
  }

  /**
   * Gets the value of the version property.
   *
   */
  public String getVersion () {
    if (version == null) {
      return "1.0";
    } else {
      return version;
    }
  }

  /**
   * Sets the value of the version property.
   *
   */
  public void setVersion (String value) {
    this.version = value;
  }
}
