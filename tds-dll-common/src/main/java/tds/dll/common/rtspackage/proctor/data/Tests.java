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
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType (XmlAccessType.FIELD)
@XmlType (name = "", propOrder = {
    "test"
})
public class Tests implements Serializable
{

  private static final long serialVersionUID = 1L;

  @XmlElement (name = "Test", required = true)
  protected List<Test>      test;

  /**
   * Gets the value of the test property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot.
   * Therefore any modification you make to the returned list will be present
   * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
   * for the test property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getTest ().add (newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list
   * {@link ProctorPackage.Proctor.Tests.Test }
   * 
   * 
   */
  public List<Test> getTest () {
    if (test == null) {
      test = new ArrayList<Test> ();
    }
    return this.test;
  }

  public void setTest (List<Test> test) {
    this.test = test;
  }

}
