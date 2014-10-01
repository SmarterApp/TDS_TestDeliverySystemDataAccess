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

@XmlAccessorType (XmlAccessType.FIELD)
@XmlType (name = "", propOrder = {
    "tests"
})
public class Proctor implements Serializable
{

  private static final long serialVersionUID = 1L;

  @XmlElement (name = "Tests", required = true)
  protected Tests           tests;

  /**
   * Gets the value of the tests property.
   * 
   * @return possible object is {@link ProctorPackage.Proctor.Tests }
   * 
   */
  public Tests getTests () {
    return tests;
  }

  /**
   * Sets the value of the tests property.
   * 
   * @param value
   *          allowed object is {@link ProctorPackage.Proctor.Tests }
   * 
   */
  public void setTests (Tests value) {
    this.tests = value;
  }
  
}
