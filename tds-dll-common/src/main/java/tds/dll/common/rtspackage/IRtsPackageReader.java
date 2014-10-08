/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.common.rtspackage;

import java.util.List;

import tds.dll.common.rtspackage.common.exception.RtsPackageReaderException;
import tds.dll.common.rtspackage.common.table.RtsRecord;
import tds.dll.common.rtspackage.common.table.RtsTable;

/**
 * Reads serialized package and return its field values
 * 
 * @author jmambo
 *
 */
public interface IRtsPackageReader
{

  /**
   * Reads serialized package
   * 
   * @param serialized package
   * @return true if successful
   */
  public boolean read (byte[] pkg) throws RtsPackageReaderException;
  
  /**
   * Get the value of a field
   * 
   * @param fieldName
   * @return
   */
  public String getFieldValue(String fieldName); 
  
  /**
   * Get values of a field
   * 
   * @param fieldName
   * @return
   */
  public RtsRecord getRtsRecord(String fieldName); 
  
  /**
   * Gets RtsTable 
   * 
   * @param fieldName
   * @return list of field values
   */
  public RtsTable getRtsTable (String fieldName);


  /**
   * Get Package object
   * @return Package object
   */
  <T> T getPackage (Class<T> clazz);
  
}
