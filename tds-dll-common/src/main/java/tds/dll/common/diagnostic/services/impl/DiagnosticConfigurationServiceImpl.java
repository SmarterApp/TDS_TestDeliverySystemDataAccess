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

package tds.dll.common.diagnostic.services.impl;

import org.opentestsystem.shared.progman.client.domain.ClientPropertyConfiguration;
import org.opentestsystem.shared.progman.init.ProgManRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tds.dll.common.diagnostic.domain.Configuration;
import tds.dll.common.diagnostic.domain.ProgmanConfiguration;
import tds.dll.common.diagnostic.domain.Rating;
import tds.dll.common.diagnostic.domain.Setting;
import tds.dll.common.diagnostic.services.DiagnosticConfigurationService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Service
public class DiagnosticConfigurationServiceImpl implements DiagnosticConfigurationService {


    @Value("${progman.locator}")
    private String progmanLocatorSetting;

    @Value("${progman.baseUri}")
    private String progmanBaseUri;

    @Value("${spring.profiles.active}")
    private String springProfilesActive;


    @Autowired
    ProgManRetriever progManRetriever;


    public Configuration getConfiguration(List<String> propertyWhitelist) {

        List<Setting> settings = new ArrayList<>();
        settings.add(new Setting("progman.locator", progmanLocatorSetting ));
        settings.add(new Setting("progman.baseUri", progmanBaseUri ));
        settings.add(new Setting("spring.profiles.active", springProfilesActive ));
        Configuration configuration = new Configuration(Rating.IDEAL, settings);


        List<ClientPropertyConfiguration> clientPropertyConfigurations = progManRetriever.loadPropertyConfiguration();

        List<ProgmanConfiguration> progmanConfigurations = new ArrayList<>();

        for ( ClientPropertyConfiguration clientPropertyConfiguration : clientPropertyConfigurations) {

            if ( clientPropertyConfiguration.getPropertyKeys().size() > 0 ) {

                ProgmanConfiguration progmanConfiguration = new ProgmanConfiguration(clientPropertyConfiguration.getEnvName(),
                        clientPropertyConfiguration.getId(), clientPropertyConfiguration.getName());

                List<Setting> properties = new ArrayList<>();

                for (String key : clientPropertyConfiguration.getPropertyKeys() ) {
                    Setting property = new Setting(key,
                            propertyWhitelist.contains(key) ? clientPropertyConfiguration.getPrintSafePropertyValue(key) : "<redacted>"
                    );
                    properties.add(property);
                }

                Collections.sort(properties, new Comparator<Setting>() {
                    @Override
                    public int compare(Setting s1, Setting s2) {
                        return s1.getName().toLowerCase().compareTo(s2.getName().toLowerCase());
                    }
                });

                progmanConfiguration.setProperties(properties);
                progmanConfigurations.add(progmanConfiguration);

            }

        }


        configuration.setProgmanConfigurations(progmanConfigurations);

        return configuration;

    }


}
