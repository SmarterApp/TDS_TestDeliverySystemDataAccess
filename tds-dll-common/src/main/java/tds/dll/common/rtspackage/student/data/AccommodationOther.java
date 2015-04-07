/*************************************************************************
 * Educational Online Test Delivery System
 * Copyright (c) 2015 American Institutes for Research
 *
 * Distributed under the AIR Open Source License, Version 1.0
 * See accompanying file AIR-License-1_0.txt or at 
 * https://bitbucket.org/sbacoss/eotds/wiki/AIR_Open_Source_License
 *************************************************************************/

package tds.dll.common.rtspackage.student.data;

import org.apache.commons.lang3.StringUtils;

import AIR.Common.Utilities.UrlEncoderDecoderUtils;

/**
 * @author jmambo
 *
 */
public class AccommodationOther
{
  

  /**
   * Since Other accommodation value is not defined in the test tools tables, use this prefix to determine its value from accommodations string 
   * Prefix must not be the same as existing test tools codes
   * must not contain accommodation string delimiters : | ;
   */
  public static final String VALUE_PREFIX = "TDS_OTHER#";
  
  /**
   * Code must no be the same as existing test tools code
   */
  public static final String CODE = "TDS_OTHER";
  
  
  /**
   * Code must no be the same as existing test tools names
   */
  public static final String NAME = "Other";
  
  /**
   * Set the value in a way that it can be easily be distinguished from defined accommodation codes 
   * @param ac
   * @return other value
   */
  public static String getValue(Accommodation ac) {
    return getValue(ac.getOther()) ;
  }
  
  
  /**
   * Set the value in a way that it can be easily be distinguished from defined accommodation codes 
   * @param ac
   * @return
   */
  public static String getValue(String otherValue) {
    if (StringUtils.isBlank (otherValue)) {
       return null;
    } else {
      //encode so that delimiters : | ; are encoded
       return VALUE_PREFIX + UrlEncoderDecoderUtils.encode(otherValue);
    }
  }
  
  
}
