/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.api;

import java.util.Map;

import AIR.Common.DB.SQLConnection;

/**
 * @author jmambo
 *
 */
public interface IRtsReportingDLL
{

  /**
   * Gets a map of testee attributes
   * 
   * @param connection
   * @param clientName
   * @param studentKey
   * @return Map of testee attributes
   */
   Map<String, String> getTesteeAttributes(SQLConnection connection, String clientName, long testeeKey);
   
   /**
    * Gets a map of testee relationships
    * 
    * @param connection
    * @param clientName
    * @param studenKey
    * @return Map of testee relationships
    */
   Map<String, String> getTesteeRelationships(SQLConnection connection, String clientName, long testeeKey);
   
}
