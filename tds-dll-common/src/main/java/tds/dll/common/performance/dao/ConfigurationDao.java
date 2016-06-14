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
package tds.dll.common.performance.dao;

import tds.dll.common.performance.domain.ClientSystemFlag;
import tds.dll.common.performance.domain.Externs;

import java.util.List;

public interface ConfigurationDao {
    List<ClientSystemFlag> getSystemFlags(String clientName);

    void updateClientTestPropertyMaxOpportunities(String clientName, String testId, Integer maxOpportunities);
    // these proctor methods are needed because the queries for checking the system values in Proctor doesn't join on the externs view
    //  and it does on the student side
    List<ClientSystemFlag> getProctorSystemFlags(String clientName);
    Externs getExterns(String clientName);
}
