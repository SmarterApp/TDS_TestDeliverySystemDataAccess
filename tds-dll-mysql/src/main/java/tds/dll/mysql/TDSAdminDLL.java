/*******************************************************************************
 * Educational Online Test Delivery System
 * Copyright (c) 2016 American Institutes for Research
 * 
 * Distributed under the AIR Open Source License, Version 1.0
 * See accompanying file AIR-License-1_0.txt or at 
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 ******************************************************************************/
package tds.dll.mysql;

import java.io.Serializable;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import AIR.Common.DB.AbstractDLL;
import AIR.Common.DB.SQLConnection;
import AIR.Common.DB.SqlParametersMaps;
import AIR.Common.DB.results.SingleDataResultSet;
import TDS.Shared.Exceptions.ReturnStatusException;
import tds.dll.api.ITDSAdminDLL;

public class TDSAdminDLL extends AbstractDLL implements ITDSAdminDLL, Serializable {

	private static final Logger _logger = LoggerFactory.getLogger(TDSAdminDLL.class);

	@Override
	public SingleDataResultSet getOpportunities(SQLConnection connection, String v_extSsId, String v_sessionId,
			String v_procedure) throws ReturnStatusException {
		String sql = getQuerybyProcedure(v_procedure);
		SqlParametersMaps parameters = new SqlParametersMaps();
		if (!StringUtils.isEmpty(v_extSsId)) {
			sql = sql + " topp.testeeid=${testeeid}";
			parameters = parameters.put("testeeid", v_extSsId);
			if (!StringUtils.isEmpty(v_sessionId)) {
				sql = sql + " and topp.sessid=${sessionid}";
				parameters = parameters.put("sessionid", v_sessionId);
			}
		} else {
			sql = sql + "topp.sessid=${sessionid}";
			parameters = parameters.put("sessionid", v_sessionId);
		}
		SingleDataResultSet result = executeStatement(connection, sql, parameters, false).getResultSets().next();
		return result;
	}

	private String getQuerybyProcedure(String v_procedure) throws ReturnStatusException {
		String sql = "SELECT * FROM testopportunity topp where ";

		switch (v_procedure) {
		case "changeperm":
			sql = "SELECT * FROM testopportunity topp,testopportunitysegment tos where _key = _fk_testopportunity and ";
			sql += " exists (select * from configs.client_segmentproperties cprop where "
					+ "cprop.clientname = topp.clientname and cprop.parenttest = topp._efk_testid "
					+ "and cprop.segmentid = tos.segmentid and cprop.ispermeable = 0) and ";
			break;
		case "reset":
			sql += "topp.datedeleted is null and "
					+ "(topp.datereported is not null or topp.dateexpiredreported is not null or topp.dateinvalidated is not null) and ";
			break;
		case "restore":
			sql += "topp.datedeleted is not null and ";
			break;
		case "alter":
			sql += "topp.datedeleted is null and topp.datestarted is not null and topp.status not in "
					+ "(\'completed\', \'submitted\', \'scored\', \'reported\', \'invalidated\') and "
					+ " topp.opportunity >= (select max(t.opportunity) from testopportunity t"
					+ " where t._efk_testee = topp._efk_testee and t._efk_adminsubject = topp._efk_adminsubject and t.datedeleted is null) and ";
			break;
		case "reopen":
			sql += "topp.datedeleted is null and "
					+ "topp.opportunity >= (select max(t.opportunity) from testopportunity t"
					+ " where t._efk_testee = topp._efk_testee and t._efk_adminsubject = topp._efk_adminsubject and t.datedeleted is null) and ";
			break;
		case "extend":
			sql += "topp.datedeleted is null and topp.items_archived is null and "
					+ "topp.opportunity >= (select max(t.opportunity) from testopportunity t"
					+ " where t._efk_testee = topp._efk_testee and t._efk_adminsubject = topp._efk_adminsubject and t.datedeleted is null) and ";
			break;
		case "invalidate":
			sql += "topp.datedeleted is null and ";
			break;
		default:
			throw new ReturnStatusException(v_procedure + " is not a valid procedure name");
		}
		return sql;
	}

	@Override
	public SingleDataResultSet A_resettestopportunity_SP(SQLConnection connection, UUID v_oppKey, String v_requestor,
			String v_reason) throws ReturnStatusException {

		final String SQL_QUERY = "call a_resettestopportunity(${v_oppKey}, ${v_requestor}, ${v_reason})";
		SqlParametersMaps params = new SqlParametersMaps();

		params.put("v_oppKey", v_oppKey);
		params.put("v_requestor", v_requestor);
		params.put("v_reason", v_reason);

		SingleDataResultSet resultSet = executeStatement(connection, SQL_QUERY, params, false).getResultSets().next();
		return resultSet;
	}

	@Override
	public SingleDataResultSet A_invalidatetestopportunity_SP(SQLConnection connection, UUID v_oppKey,
			String v_requestor, String v_reason) throws ReturnStatusException {

		final String SQL_QUERY = "call a_invalidatetestopportunity(${v_oppKey}, ${v_requestor}, ${v_reason})";
		SqlParametersMaps params = new SqlParametersMaps();

		params.put("v_oppKey", v_oppKey);
		params.put("v_requestor", v_requestor);
		params.put("v_reason", v_reason);

		SingleDataResultSet resultSet = executeStatement(connection, SQL_QUERY, params, false).getResultSets().next();
		return resultSet;

	}

	@Override
	public SingleDataResultSet A_restoretestopportunity_SP(SQLConnection connection, UUID v_oppKey, String v_requestor,
			String v_reason) throws ReturnStatusException {

		final String SQL_QUERY = "call a_restoretestopportunity(${v_oppKey}, ${v_requestor}, ${v_reason})";
		SqlParametersMaps params = new SqlParametersMaps();

		params.put("v_oppKey", v_oppKey);
		params.put("v_requestor", v_requestor);
		params.put("v_reason", v_reason);

		SingleDataResultSet resultSet = executeStatement(connection, SQL_QUERY, params, false).getResultSets().next();
		return resultSet;
	}

	@Override
	public SingleDataResultSet A_reopenopportunity_SP(SQLConnection connection, UUID v_oppKey, String v_requestor,
			String v_reason) throws ReturnStatusException {

		final String SQL_QUERY = "call a_reopenopportunity(${v_oppKey}, ${v_requestor}, ${v_reason})";
		SqlParametersMaps params = new SqlParametersMaps();

		params.put("v_oppKey", v_oppKey);
		params.put("v_requestor", v_requestor);
		params.put("v_reason", v_reason);

		SingleDataResultSet resultSet = executeStatement(connection, SQL_QUERY, params, false).getResultSets().next();
		return resultSet;
	}

	@Override
	public SingleDataResultSet A_extendingoppgraceperiod_SP(SQLConnection connection, UUID v_oppKey, String v_requestor,
			int v_selectedsitting, boolean v_doupdate, String v_reason) throws ReturnStatusException {

		final String SQL_QUERY = "call a_extendingoppgraceperiod(${v_oppKey}, ${v_requestor}, ${v_selectedsitting}, ${v_doupdate}, ${v_reason})";
		SqlParametersMaps params = new SqlParametersMaps();

		params.put("v_oppKey", v_oppKey);
		params.put("v_requestor", v_requestor);
		params.put("v_selectedsitting", v_selectedsitting);
		params.put("v_doupdate", v_doupdate);
		params.put("v_reason", v_reason);

		SingleDataResultSet resultSet = executeStatement(connection, SQL_QUERY, params, false).getResultSets().next();
		return resultSet;
	}

	@Override
	public SingleDataResultSet A_alteropportunityexpiration_SP(SQLConnection connection, UUID v_oppKey,
			String v_requestor, int v_dayincrement, String v_reason) throws ReturnStatusException {

		final String SQL_QUERY = "call a_alteropportunityexpiration(${v_oppKey}, ${v_requestor}, ${v_dayincrement}, ${v_reason})";
		SqlParametersMaps params = new SqlParametersMaps();

		params.put("v_oppKey", v_oppKey);
		params.put("v_requestor", v_requestor);
		params.put("v_dayincrement", v_dayincrement);
		params.put("v_reason", v_reason);

		SingleDataResultSet resultSet = executeStatement(connection, SQL_QUERY, params, false).getResultSets().next();
		return resultSet;
	}

	@Override
	public SingleDataResultSet A_setopportunitysegmentperm_SP(SQLConnection connection, UUID v_oppKey,
			String v_requestor, String v_segmentid, int v_segmentposition, String v_restoreon, int v_ispermeable,
			String v_reason) throws ReturnStatusException {

		final String SQL_QUERY = "call a_setopportunitysegmentperm(${v_oppKey}, ${v_requestor}, ${v_segmentid}, ${v_segmentposition},${v_restoreon},${v_ispermeable}, ${v_reason})";
		SqlParametersMaps params = new SqlParametersMaps();

		params.put("v_oppKey", v_oppKey);
		params.put("v_requestor", v_requestor);
		params.put("v_segmentid", v_segmentid);
		params.put("v_segmentposition", v_segmentposition);
		params.put("v_restoreon", v_restoreon);
		params.put("v_ispermeable", v_ispermeable);
		params.put("v_reason", v_reason);

		SingleDataResultSet resultSet = executeStatement(connection, SQL_QUERY, params, false).getResultSets().next();
		return resultSet;
	}

}
