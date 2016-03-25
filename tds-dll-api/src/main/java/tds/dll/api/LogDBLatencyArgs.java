/*******************************************************************************
 * Educational Online Test Delivery System 
 * Copyright (c) 2014 American Institutes for Research
 *   
 * Distributed under the AIR Open Source License, Version 1.0 
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.api;

import java.util.Date;
import java.util.UUID;

import AIR.Common.DB.SQLConnection;
/**
 * Class for passing arguments to the _LogDBLatencyArgs_SP method in the
 * CommonDLL.
 * 
 * @author temp_tprindle
 * 
 */
public class LogDBLatencyArgs
{
  SQLConnection connection = null;
  public String        procName   = null;
  public Date          startTime  = null;
  public Long          userKey    = null;
  public boolean       checkAudit = true;
  public Integer       N          = null;
  public UUID          testOppKey = null;
  public UUID          sessionKey = null;
  public String        clientName = null;
  public String        comment    = null;

  public LogDBLatencyArgs (SQLConnection connection) {
    this.connection = connection;
  }

  public SQLConnection getConnection () {
    return connection;
  }

  public void setConnection (SQLConnection connection) {
    this.connection = connection;
  }

  public String getProcName () {
    return procName;
  }

  public void setProcName (String procName) {
    this.procName = procName;
  }

  public Date getStartTime () {
    return startTime;
  }

  public void setStartTime (Date startTime) {
    this.startTime = startTime;
  }

  public Long getUserKey () {
    return userKey;
  }

  public void setUserKey (Long userKey) {
    this.userKey = userKey;
  }

  public boolean isCheckAudit () {
    return checkAudit;
  }

  public void setCheckAudit (boolean checkAudit) {
    this.checkAudit = checkAudit;
  }

  public Integer getN () {
    return N;
  }

  public void setN (Integer n) {
    N = n;
  }

  public UUID getTestOppKey () {
    return testOppKey;
  }

  public void setTestOppKey (UUID testOppKey) {
    this.testOppKey = testOppKey;
  }

  public UUID getSessionKey () {
    return sessionKey;
  }

  public void setSessionKey (UUID sessionKey) {
    this.sessionKey = sessionKey;
  }

  public String getClientName () {
    return clientName;
  }

  public void setClientName (String clientName) {
    this.clientName = clientName;
  }

  public String getComment () {
    return comment;
  }

  public void setComment (String comment) {
    this.comment = comment;
  }

}
