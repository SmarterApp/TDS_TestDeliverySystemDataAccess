/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.api;

import java.util.UUID;

import AIR.Common.DB.SQLConnection;

/**
 * Class for passing arguments to the _ReturnError_SP method in the CommonDLL.
 * 
 * @author temp_tprindle
 * 
 */
public class ReturnErrorArgs
{
  SQLConnection connection = null;
  public String        client     = null;
  public String        procName   = null;
  public String        appKey     = null;
  public String        argString  = null;
  public UUID          oppKey     = null;
  public String        context    = null;
  public String        status     = "failed";

  public ReturnErrorArgs (SQLConnection connection) {
    this.connection = connection;
  }

  public SQLConnection getConnection () {
    return connection;
  }

  public void setConnection (SQLConnection connection) {
    this.connection = connection;
  }

  public String getClient () {
    return client;
  }

  public void setClient (String client) {
    this.client = client;
  }

  public String getProcName () {
    return procName;
  }

  public void setProcName (String procName) {
    this.procName = procName;
  }

  public String getAppKey () {
    return appKey;
  }

  public void setAppKey (String appKey) {
    this.appKey = appKey;
  }

  public String getArgString () {
    return argString;
  }

  public void setArgString (String argString) {
    this.argString = argString;
  }

  public UUID getOppKey () {
    return oppKey;
  }

  public void setOppKey (UUID oppKey) {
    this.oppKey = oppKey;
  }

  public String getContext () {
    return context;
  }

  public void setContext (String context) {
    this.context = context;
  }

  public String getStatus () {
    return status;
  }

  public void setStatus (String status) {
    this.status = status;
  }

}
