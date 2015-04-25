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
  public static final String VALUE_PREFIX = "TDS_Other#";
  
  /**
   * Code must no be the same as existing test tools code
   */
  public static final String CODE = "TDS_Other";
  
  
  /**
   * Code must no be the same as existing test tools names
   */
  public static final String NAME = "Other";
  
  /**
   * Set the value in a way that it can be easily be distinguished from defined accommodation codes 
   * @param ac
   * @return other value
   */
  public static String getFormattedValue(Accommodation ac) {
    return getFormattedValue(ac.getOther()) ;
  }
  
  
  /**
   * Set the value in a way that it can be easily be distinguished from defined accommodation codes 
   * @param ac
   * @return formatted value
   */
  public static String getFormattedValue(String otherValue) {
    if (StringUtils.isBlank (otherValue)) {
       return null;
    } else {
      //As per SB-145 Other can only contain alphanumeric characters
      //but currently it is not fixed hence remove accommodations string delimiters
      //it is still fine to leave it this way even if SB-145 is fixed
       return VALUE_PREFIX + otherValue.replaceAll("(\\||\\:|;)", " ");
    }
  }

  /**
   * Get Actual value from formatted value
   * 
   * @param formattedValue
   * @return actual value
   */
  public static String getActualValue(String formattedValue) {
     return formattedValue.substring (VALUE_PREFIX.length());
  }  
  
  
}
