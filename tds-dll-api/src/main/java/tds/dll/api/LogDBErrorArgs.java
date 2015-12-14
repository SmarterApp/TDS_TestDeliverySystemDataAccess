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
 * Class for passing arguments to the _LogDBLatencyArgs_SP method in the
 * CommonDLL.
 * 
 * @author temp_tprindle
 * 
 */
public class LogDBErrorArgs
{
  SQLConnection connection  = null;
  public String        procName    = null;
  public String        msg         = null;
  public Long          testee      = null;
  public String        test        = null;
  public Integer       opportunity = null;
  public UUID          testOppKey  = null;
  public String        clientName  = null;
  public UUID          sessionKey  = null;

  public LogDBErrorArgs (SQLConnection connection) {
    this.connection = connection;
  }

  public SQLConnection getConnection () {
    return connection;
  }

  public String getProcName () {
    return procName;
  }

  public void setProcName (String procName) {
    this.procName = procName;
  }

  public String getMsg () {
    return msg;
  }

  public void setMsg (String msg) {
    this.msg = msg;
  }

  public Long getTestee () {
    return testee;
  }

  public void setTestee (Long testee) {
    this.testee = testee;
  }

  public String getTest () {
    return test;
  }

  public void setTest (String test) {
    this.test = test;
  }

  public Integer getOpportunity () {
    return opportunity;
  }

  public void setOpportunity (Integer opportunity) {
    this.opportunity = opportunity;
  }

  public UUID getTestOppKey () {
    return testOppKey;
  }

  public void setTestOppKey (UUID testOppKey) {
    this.testOppKey = testOppKey;
  }

  public String getClientName () {
    return clientName;
  }

  public void setClientName (String clientName) {
    this.clientName = clientName;
  }

  public UUID getSessionKey () {
    return sessionKey;
  }

  public void setSessionKey (UUID sessionKey) {
    this.sessionKey = sessionKey;
  }

  public void setConnection (SQLConnection connection) {
    this.connection = connection;
  }

}
