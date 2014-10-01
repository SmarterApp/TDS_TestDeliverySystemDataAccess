/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.api;

import java.io.IOException;
import AIR.Common.DB.SQLConnection;


/**
 * @author akulakov
 *
 */
public interface IUnitTestFlag
{
  /** 
  * @param connection
  */
 public void statisticsUTFs (SQLConnection connection)  throws Exception, IOException;
 /**
  * 
  * @param connection
  */
 public void createUnitTestFlag (SQLConnection connection)  throws Exception, IOException;
 /**
  * 
  * @param connection
  */
 public void dropUnitTestFlag (SQLConnection connection)  throws Exception, IOException;
 /**
  * 
  * @param connection
  */
 public void propagateUnittestflag (SQLConnection connection);

}
