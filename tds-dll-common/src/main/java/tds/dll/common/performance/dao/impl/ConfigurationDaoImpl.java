/*******************************************************************************
 * Educational Online Test Delivery System
 * Copyright (c) 2016 Regents of the University of California
 *
 * Distributed under the AIR Open Source License, Version 1.0
 * See accompanying file AIR-License-1_0.txt or at
 * http://www.smarterapp.org/documents/American_Institutes_for_Research_Open_Source_Software_License.pdf
 *
 * SmarterApp Open Source Assessment Software Project: http://smarterapp.org
 * Developed by Fairway Technologies, Inc. (http://fairwaytech.com)
 * for the Smarter Balanced Assessment Consortium (http://smarterbalanced.org)
 ******************************************************************************/
package tds.dll.common.performance.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import tds.dll.common.performance.caching.CacheType;
import tds.dll.common.performance.dao.ConfigurationDao;
import tds.dll.common.performance.domain.ClientSystemFlag;
import tds.dll.common.performance.domain.Externs;
import tds.dll.common.performance.utils.LegacyDbNameUtility;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object for interacting with the {@code configs} database.
 * <p>
 *     This class could potentially be used for all interaction with the {@code configs} database.  Normally the DAO
 *     pattern tends to define one DAO object per table.  In this case, it might make more sense to just have one
 *     general DAO that allows us to talk to the {@code configs} database and get whatever we need.
 * </p>
 */
@Repository
public class ConfigurationDaoImpl implements ConfigurationDao {
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    protected static final Logger logger = LoggerFactory.getLogger(ConfigurationDaoImpl.class);

    @Autowired
    protected LegacyDbNameUtility dbNameUtility;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * Get all the {@code ClientSystemFlag} records from the {@code configs.client_systemflags} database for the
     * specified client name.
     * <p>
     *     The {@code JOIN} to the {@code session.externs} view came from looking at the SQL contained in the
     *     {@code CommonDLL.selectIsOnByAuditObject} method.
     * </p>
     *
     * @param clientName The client name for which the {@code ClientSystemFlag} records should be fetched.
     * @return A collection of {@code ClientSystemFlag} records for the specified client name.
     */
    @Override
    @Cacheable(CacheType.LongTerm)
    public List<ClientSystemFlag> getSystemFlags(String clientName) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("clientName", clientName);

        final String SQL =
                "SELECT\n" +
                    "s.auditobject AS auditObject,\n" +
                    "s.clientname AS clientName,\n" +
                    "s.ispracticetest AS isPracticeTest,\n" +
                    "s.ison AS isOn,\n" +
                    "s.description AS description,\n" +
                    "s.datechanged AS dateChanged,\n" +
                    "s.datepublished AS datePublished\n" +
                "FROM\n" +
                    "${configdb}.client_systemflags s\n" +
                "JOIN\n" +
                    "${sessiondb}.externs e\n" +
                    "ON (e.clientname = s.clientname\n" +
                    "AND e.ispracticetest = s.ispracticetest)\n" +
                "WHERE\n" +
                    "e.clientname = :clientName";

        try {
            return namedParameterJdbcTemplate.query(
                    dbNameUtility.setDatabaseNames(SQL),
                    parameters,
                    new BeanPropertyRowMapper<>(ClientSystemFlag.class));
        } catch(EmptyResultDataAccessException e) {
            logger.warn(String.format("%s did not return results for clientName = %s", SQL, clientName));
            return null;
        }
    }

    /**
     * Get all the {@code ClientSystemFlag} records from the {@code configs.client_systemflags} database for the
     * specified client name.
     * <p>
     *     This does not join on externs like the student side does.  We don't believe this would in fact cause an issue
     *     but wanted to make the SQL the same in case there is a difference in practice.
     * </p>
     *
     * @param clientName The client name for which the {@code ClientSystemFlag} records should be fetched.
     * @return A collection of {@code ClientSystemFlag} records for the specified client name.
     */
    @Override
    @Cacheable(CacheType.LongTerm)
    public List<ClientSystemFlag> getProctorSystemFlags(String clientName) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("clientName", clientName);

        final String SQL =
                "SELECT\n" +
                    "s.auditobject AS auditObject,\n" +
                    "s.clientname AS clientName,\n" +
                    "s.ispracticetest AS isPracticeTest,\n" +
                    "s.ison AS isOn,\n" +
                    "s.description AS description,\n" +
                    "s.datechanged AS dateChanged,\n" +
                    "s.datepublished AS datePublished\n" +
                "FROM\n" +
                    "${configdb}.client_systemflags s\n" +
                "WHERE\n" +
                    "s.clientname = :clientName";

        try {
            return namedParameterJdbcTemplate.query(
                    dbNameUtility.setDatabaseNames(SQL),
                    parameters,
                    new BeanPropertyRowMapper<>(ClientSystemFlag.class));
        } catch(EmptyResultDataAccessException e) {
            logger.warn(String.format("%s did not return results for clientName = %s", SQL, clientName));
            return null;
        }
    }

    @Override
    public void updateClientTestPropertyMaxOpportunities(String clientName, String testId, Integer maxOpportunities) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("maxOpportunities", maxOpportunities);
        parameters.put("testId", testId);
        parameters.put("clientName", clientName);

        final String SQL =
                "UPDATE ${configdb}.client_testproperties\n" +
                        "SET maxopportunities = :maxOpportunities\n" +
                        "WHERE testid = :testId\n" +
                        "AND clientName = :clientName";

        namedParameterJdbcTemplate.update(dbNameUtility.setDatabaseNames(SQL), parameters);
    }

    @Override
    @Cacheable(CacheType.LongTerm)
    public Externs getExterns(String clientName) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("clientName", clientName);

        final String SQL =
                "SELECT\n" +
                    "clientname AS clientName,\n" +
                    "testeetype AS testeeType,\n" +
                    "proctortype AS proctorType,\n" +
                    "environment,\n" +
                    "ispracticetest AS isPracticeTest,\n" +
                    "sessiondb AS sessionDb,\n" +
                    "testdb AS testDb,\n" +
                    "clientStylePath,\n" +
                    "timeZoneOffset,\n" +
                    "proctorCheckin\n" +
                "FROM\n" +
                    "${sessiondb}.externs\n" +
                "WHERE\n" +
                    "clientname = :clientName";
        try {
            return namedParameterJdbcTemplate.queryForObject(
                    dbNameUtility.setDatabaseNames(SQL),
                    parameters,
                    new BeanPropertyRowMapper<>(Externs.class));
        } catch (EmptyResultDataAccessException e) {
            logger.warn(String.format("%s did not return results for clientName = %s", SQL, clientName));
            return null;
        }
    }
}
