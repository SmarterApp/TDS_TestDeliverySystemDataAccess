/*******************************************************************************
 * Educational Online Test Delivery System
 * Copyright (c) 2016 American Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0
 * See accompanying file AIR-License-1_0.txt or at 
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.api;

import java.util.UUID;

import AIR.Common.DB.SQLConnection;
import AIR.Common.DB.results.SingleDataResultSet;
import TDS.Shared.Exceptions.ReturnStatusException;

public interface ITDSAdminDLL {

	/**
	 * @param connection
	 * @param v_extSsId
	 * @param v_sessionId
	 * @return
	 * @throws ReturnStatusException
	 */
	public SingleDataResultSet getOpportunities(SQLConnection connection, String v_extSsId, String v_sessionId,
			String v_procedure) throws ReturnStatusException;

	/**
	 * @param connection
	 * @param v_oppKey
	 * @param v_requestor
	 * @param v_reason
	 * @return
	 * @throws ReturnStatusException
	 */
	public SingleDataResultSet A_resettestopportunity_SP(SQLConnection connection, UUID v_oppKey, String v_requestor,
			String v_reason) throws ReturnStatusException;

	/**
	 * @param connection
	 * @param v_oppKey
	 * @param v_requestor
	 * @param v_reason
	 * @return
	 * @throws ReturnStatusException
	 */
	public SingleDataResultSet A_invalidatetestopportunity_SP(SQLConnection connection, UUID v_oppKey,
			String v_requestor, String v_reason) throws ReturnStatusException;

	/**
	 * @param connection
	 * @param v_oppKey
	 * @param v_requestor
	 * @param v_reason
	 * @return
	 * @throws ReturnStatusException
	 */
	public SingleDataResultSet A_restoretestopportunity_SP(SQLConnection connection, UUID v_oppKey, String v_requestor,
			String v_reason) throws ReturnStatusException;

	/**
	 * @param connection
	 * @param v_oppKey
	 * @param v_requestor
	 * @param v_reason
	 * @return
	 * @throws ReturnStatusException
	 */
	public SingleDataResultSet A_reopenopportunity_SP(SQLConnection connection, UUID v_oppKey, String v_requestor,
			String v_reason) throws ReturnStatusException;

	/**
	 * @param connection
	 * @param v_oppKey
	 * @param v_requestor
	 * @param v_selectedsitting
	 * @param v_doupdate
	 * @param v_reason
	 * @return
	 * @throws ReturnStatusException
	 */
	public SingleDataResultSet A_extendingoppgraceperiod_SP(SQLConnection connection, UUID v_oppKey, String v_requestor,
			int v_selectedsitting, boolean v_doupdate, String v_reason) throws ReturnStatusException;

	/**
	 * @param connection
	 * @param v_oppKey
	 * @param v_requestor
	 * @param v_dayincrement
	 * @param v_reason
	 * @return
	 * @throws ReturnStatusException
	 */
	public SingleDataResultSet A_alteropportunityexpiration_SP(SQLConnection connection, UUID v_oppKey,
			String v_requestor, int v_dayincrement, String v_reason) throws ReturnStatusException;

	/**
	 * @param connection
	 * @param v_oppKey
	 * @param v_requestor
	 * @param v_ispermeable
	 * @param v_restoreon
	 * @param v_reason
	 * @return
	 * @throws ReturnStatusException
	 */
	public SingleDataResultSet A_setopportunitysegmentperm_SP(SQLConnection connection, UUID v_oppKey,
			String v_requestor, String v_segmentid, int v_segmentposition, String v_restoreon, int v_ispermeable,
			String v_reason) throws ReturnStatusException;
}
