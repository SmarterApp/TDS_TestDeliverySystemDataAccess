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

import AIR.Common.DB.AbstractDLL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import tds.dll.common.performance.caching.CacheType;
import tds.dll.common.performance.dao.ItemBankDao;
import tds.dll.common.performance.dao.mappers.SetOfAdminSubjectMapper;
import tds.dll.common.performance.domain.SetOfAdminSubject;
import tds.dll.common.performance.utils.LegacyDbNameUtility;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Data Access Object for interacting with the {@code itembank} database.
 * <p>
 *     This class could potentially be used for all interaction with the {@code itembank} database.  Normally the DAO
 *     pattern tends to define one DAO object per table.  In this case, it might make more sense to just have one
 *     general DAO that allows us to talk to the {@code itembank} database and get whatever we need.
 * </p>
 */
public class ItemBankDaoImpl extends AbstractDLL implements ItemBankDao {
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    protected static final Logger logger = LoggerFactory.getLogger(ConfigurationDaoImpl.class);

    @Autowired
    protected LegacyDbNameUtility dbNameUtility;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    @Cacheable(CacheType.MediumTerm)
    public SetOfAdminSubject get(String adminSubject) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("adminSubject", adminSubject);

        final String SQL =
                "SELECT\n" +
                        "_key AS `key`,\n" +
                        "_fk_testadmin AS clientName,\n" +
                        "maxitems AS maxItems,\n" +
                        "startability AS startAbility,\n" +
                        "testid AS testId,\n" +
                        "issegmented AS isSegmented,\n" +
                        "selectionalgorithm AS selectionAlgorithm\n" +
                        "FROM\n" +
                        "${itembankdb}.tblsetofadminsubjects\n" +
                        "WHERE\n" +
                        "_key = :adminSubject";

        try{
            return namedParameterJdbcTemplate.queryForObject(
                    dbNameUtility.setDatabaseNames(SQL),
                    parameters,
                    new SetOfAdminSubjectMapper());
        } catch(EmptyResultDataAccessException e) {
            logger.warn(String.format("%s did not return results for adminSubject = %s", SQL, adminSubject));
            return null;
        }
    }

    // TODO: consider combining this join on tblsubject with the main call above to get the setOfAdminSubjects.  Would need to be a LEFT JOIN to be safe, but we could add TestSubject to the domain object and remove 1 extra DB call
    @Override
    @Cacheable(CacheType.MediumTerm)
    public String getTestSubject(String testKey) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put ("testKey", testKey);

        // TODO: add integration test
        final String SQL = "select S.Name from  ${itembankdb}.tblsubject S, ${itembankdb}.tblsetofadminsubjects A "
                + " where A._key = :testKey and S._Key = A._fk_Subject";

        try {
            return namedParameterJdbcTemplate.queryForObject(dbNameUtility.setDatabaseNames(SQL), parameters, String.class);
        } catch(EmptyResultDataAccessException e) {
            logger.warn(String.format("%s did not return results for testKey = %s", SQL, testKey));
            return null;
        }
    }

    @Override
    @Cacheable(CacheType.MediumTerm)
    public String getTestFormCohort(String testKey, String formKey) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("testKey", testKey);
        parameters.put("formKey", formKey);

        final String SQL = "select cohort as formCohort from ${itembankdb}.testform where _fk_AdminSubject = :testKey and _key = :formKey";

        try {
            return namedParameterJdbcTemplate.queryForObject(dbNameUtility.setDatabaseNames(SQL), parameters, String.class);
        } catch(EmptyResultDataAccessException e) {
            logger.warn(String.format("%s did not return results for testKey = %s and formKy = %s", SQL, testKey, formKey));
            return null;
        }
    }
}
