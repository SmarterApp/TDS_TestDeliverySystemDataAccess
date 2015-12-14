/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.common.rtspackage.common.table;

/**
 * @author jmambo
 *
 */
public class RtsField
{
  private final String _key;

  private final String _value;

  public RtsField(String key, String value) {
    _key = key;
    _value = value;
  }

  public String getKey() {
    return _key;
  }

  public String getValue() {
    return _value;
  }
  
}
