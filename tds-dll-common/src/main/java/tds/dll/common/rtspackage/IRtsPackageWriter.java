/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.common.rtspackage;

import java.io.InputStream;

import tds.dll.common.rtspackage.common.exception.RtsPackageWriterException;

/**
 * Writes package as serialized object
 * 
 * @author jmambo
 *
 */
public interface IRtsPackageWriter<T>
{

  /**
   * Serialize the package
   * 
   * @param package
   * @throws RtsPackageWriterException
   */
  public void writeObject(String pkg) throws RtsPackageWriterException;
  
  /**
   * Gets non serialized package
   * 
   * @return
   */
  public T getObject();

  /**
   * Gets object as input stream
   * 
   * @return
   */
  public InputStream getInputStream () throws RtsPackageWriterException;;
  
}
