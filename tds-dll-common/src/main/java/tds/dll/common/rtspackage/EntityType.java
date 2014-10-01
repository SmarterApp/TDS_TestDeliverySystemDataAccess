/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.common.rtspackage;

/**
 * 
 * The two entity type used in RTS Package
 * 
 * @author jmambo
 *
 */
public enum EntityType {

  STUDENT("Student"), PROCTOR ("Proctor");

   private final String _value;
   
   private EntityType(String value) {
     _value = value;
   }
   
   /**
    * Gets the Pascal case representation of EntityType
    * 
    * @return value
    */
   public String getValue() {
     return _value;
   }
  
   
}
