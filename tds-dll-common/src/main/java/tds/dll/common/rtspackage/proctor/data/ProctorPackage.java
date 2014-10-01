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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * ProctorPackage XML Mapper. Works with Version 1
 * 
 * @author jmambo
 * 
 */
@XmlAccessorType (XmlAccessType.FIELD)
@XmlType (name = "", propOrder = {
    "proctor"
})
@XmlRootElement (name = "ProctorPackage")
public class ProctorPackage implements Serializable
{

  private static final long        serialVersionUID = 1L;

  @XmlElement (name = "Proctor", required = true)
  protected Proctor proctor;
  @XmlAttribute (name = "version", required = true)
  protected String                 version;

  /**
   * Gets the value of the proctor property.
   * 
   * @return possible object is {@link ProctorPackage.Proctor }
   * 
   */
  public Proctor getProctor () {
    return proctor;
  }

  /**
   * Sets the value of the proctor property.
   * 
   * @param value
   *          allowed object is {@link ProctorPackage.Proctor }
   * 
   */
  public void setProctor (Proctor value) {
    this.proctor = value;
  }

  /**
   * Gets the value of the version property.
   * 
   * @return possible object is {@link String }
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
