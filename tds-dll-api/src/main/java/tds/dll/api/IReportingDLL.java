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

import AIR.Common.DB.DataBaseTable;
import AIR.Common.DB.SQLConnection;
import TDS.Shared.Exceptions.ReturnStatusException;

/**
 * @author akulakov
 *
 */
public interface IReportingDLL
{
  /**
   * 
   * @param connection
   * @param oppkey
   * @param debug
   * @return
   * @throws ReturnStatusException
   */
  public String XML_GetOppXML_SP (SQLConnection connection, UUID oppkey, boolean debug) throws ReturnStatusException;
  /**
   * 
   * @param connection
   * @param oppkey
   * @param debug
   * @return
   * @throws ReturnStatusException
   */
  public String _XML_GetOpportunity_SP (SQLConnection connection, UUID oppkey, boolean debug) throws ReturnStatusException;
  /**
   * 
   * @param connection
   * @param oppkey
   * @param debug
   * @return
   * @throws ReturnStatusException
   */
  public String _XML_GetOppComments_SP (SQLConnection connection, UUID oppkey, boolean debug) throws ReturnStatusException;
  /**
   * 
   * @param connection
   * @param oppkey
   * @param debug
   * @return
   * @throws ReturnStatusException
   */
  public String _XML_GetOpportunityItems_SP (SQLConnection connection, UUID oppkey, boolean debug) throws ReturnStatusException;
  /**
   * 
   * @param connection
   * @param testkey
   * @param itemkey
   * @param debug
   * @return
   * @throws ReturnStatusException
   */
  public String ItemkeyStrandName_F(SQLConnection connection, String testkey, String itemkey, boolean debug) throws ReturnStatusException;
  /**
   * 
   * @param connection
   * @param oppkey
   * @param debug
   * @return
   * @throws ReturnStatusException
   */
  public String XML_GetAccomodations_F (SQLConnection connection, UUID oppkey, boolean debug) throws ReturnStatusException;
  /**
   * 
   * @param connection
   * @param oppkey
   * @param debug
   * @return
   * @throws ReturnStatusException
   */
  public String XML_GetScores_F (SQLConnection connection, UUID oppkey, boolean debug) throws ReturnStatusException;
  /**
   * 
   * @param connection
   * @param oppkey
   * @param debug
   * @return
   * @throws ReturnStatusException
   */
  public String XML_GetSegments_F (SQLConnection connection, UUID oppkey, boolean debug) throws ReturnStatusException;
  /**
   * 
   * @param connection
   * @param testkey
   * @param mode
   * @param debug
   * @return
   * @throws ReturnStatusException
   */
  public String XML_GetTest_F (SQLConnection connection, String testkey, String mode, boolean debug) throws ReturnStatusException;
  /**
   * 
   * @param connection
   * @param oppkey
   * @param debug
   * @return
   * @throws ReturnStatusException
   */
  public String XML_GetTestee_F (SQLConnection connection, UUID oppkey, boolean debug) throws ReturnStatusException;
  /**
   * 
   * @param connection
   * @param oppkey
   * @param debug
   * @return
   * @throws ReturnStatusException
   */
  public String XML_GetToolUsage_F (SQLConnection connection, UUID oppkey, boolean debug) throws ReturnStatusException;
  /**
   * 
   * @param connection
   * @param testkey
   * @param debug
   * @return
   * @throws ReturnStatusException
   */
  public Long TestBankKey_F (SQLConnection connection, String testkey, boolean debug) throws ReturnStatusException;
  /**
   * 
   * @param connection
   * @param testkey
   * @param debug
   * @return
   * @throws ReturnStatusException
   */
  public String TestGradeSpan_F (SQLConnection connection, String testkey, boolean debug) throws ReturnStatusException;
  /**
   * 
   * @param connection
   * @param testkey
   * @param debug
   * @return
   * @throws ReturnStatusException
   */
  public DataBaseTable createTMPPagesTable (SQLConnection connection, UUID oppkey, boolean debug) throws ReturnStatusException;
  /**
   * 
   * @param connection
   * @param oppkey
   * @param dateArchived
   * @param debug
   * @return
   * @throws ReturnStatusException
   */
  public DataBaseTable createTMPItemsTable (SQLConnection connection,
      UUID oppkey,
      Date dateArchived,
      boolean debug) throws ReturnStatusException;
  /**
   * 
   * @param connection
   * @param client
   * @param testid
   * @return
   * @throws ReturnStatusException
   */
  public String getEffectiveDate(SQLConnection connection, String client, String testid) throws ReturnStatusException;
  
}
