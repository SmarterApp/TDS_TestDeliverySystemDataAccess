/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.common.rtspackage.common.exception;

/**
 * 
 * @author jmambo
 * 
 */
public class RtsPackageReaderException extends Exception
{

  private static final long serialVersionUID = 1L;

  public RtsPackageReaderException () {
  }

  public RtsPackageReaderException (String message) {
    super (message);
  }

  public RtsPackageReaderException (Throwable cause) {
    super (cause);
  }

  public RtsPackageReaderException (String message, Throwable cause) {
    super (message, cause);
  }

}
