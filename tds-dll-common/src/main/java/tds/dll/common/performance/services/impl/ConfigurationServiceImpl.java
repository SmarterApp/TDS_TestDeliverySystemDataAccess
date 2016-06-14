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
package tds.dll.common.performance.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tds.dll.common.performance.dao.ConfigurationDao;
import tds.dll.common.performance.domain.ClientSystemFlag;
import tds.dll.common.performance.domain.Externs;
import tds.dll.common.performance.services.ConfigurationService;

import java.util.List;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    @Autowired
    ConfigurationDao configurationDao;

    @Override
    public Boolean isFlagOn(String clientName, String auditObject) {
        List<ClientSystemFlag> flags = configurationDao.getSystemFlags(clientName);

        return clientSystemFlagIsOn(flags, auditObject, clientName);
    }

    @Override
    public ClientSystemFlag getSystemFlag(String clientName, String auditObject) {
        List<ClientSystemFlag> flags = configurationDao.getSystemFlags(clientName);

        return matchSystemFlag(flags, auditObject);
    }

    @Override
    public Boolean isProctorFlagOn(String clientName, String auditObject) {
        List<ClientSystemFlag> flags = configurationDao.getProctorSystemFlags(clientName);

        return clientSystemFlagIsOn(flags, auditObject, clientName);
    }

    @Override
    public ClientSystemFlag getProctorSystemFlag(String clientName, String auditObject) {
        List<ClientSystemFlag> flags = configurationDao.getProctorSystemFlags(clientName);

        return matchSystemFlag(flags, auditObject);
    }

    @Override
    public Externs getExterns(String clientName) {
        return configurationDao.getExterns(clientName);
    }

    /**
     * Determine if a {@code ClientSystemFlag} is enabled for the specified audit object (i.e. the name of the flag in
     * question) and client name combination.
     *
     * @param systemFlags The collection of {@code ClientSystemFlag} records to inspect.
     * @param auditObject The name of the audit object to look for.
     * @param clientName The name of the client.
     * @return {@code True} if the specified audit object is set to "On" for the client; otherwise {@code False}.
     */
    private Boolean clientSystemFlagIsOn(List<ClientSystemFlag> systemFlags, String auditObject, String clientName) {
        if (systemFlags == null || systemFlags.size() == 0) {
            return false;
            // TODO:  throw exception instead.
        }

        ClientSystemFlag flagToFind = new ClientSystemFlag(auditObject, clientName);

        return systemFlags.contains(flagToFind)
                ? systemFlags.get(systemFlags.indexOf(flagToFind)).getIsOn()
                : false;
    }

    /**
     * Takes the full list of flags and finds the one that matches the AuditObject property
     * @param flags
     * @param auditObject
     * @return
     */
    private ClientSystemFlag matchSystemFlag(List<ClientSystemFlag> flags, String auditObject) {
        for (ClientSystemFlag flag : flags) {
            if (flag.getAuditObject() == auditObject) {
                return flag;
            }
        }

        return null;
    }
}
