package tds.dll.common.diagnostic.controller;


import org.opentestsystem.shared.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tds.dll.common.diagnostic.domain.Level;
import tds.dll.common.diagnostic.domain.Rating;
import tds.dll.common.diagnostic.domain.Status;
import tds.dll.common.diagnostic.domain.Summary;
import tds.dll.common.diagnostic.services.DiagnosticConfigurationService;
import tds.dll.common.diagnostic.services.DiagnosticDatabaseService;
import tds.dll.common.diagnostic.services.DiagnosticDependencyService;
import tds.dll.common.diagnostic.services.DiagnosticSystemService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractStatusController {
    @Autowired
    DiagnosticSystemService diagnosticSystemService;

    @Autowired
    DiagnosticConfigurationService diagnosticConfigurationService;

    @Autowired
    DiagnosticDatabaseService diagnosticDatabaseService;

    @Autowired
    DiagnosticDependencyService diagnosticDependencyService;

    @Value("${diagnostic.enabled:true}")
    private Boolean isEnabled;

    protected String unitName;
    protected List<String> configPropertyWhiteList;

    protected AbstractStatusController(String unitName) {
        this(unitName, new ArrayList<String>());
    }

    protected AbstractStatusController(String unitName, List<String> configPropertyWhiteList) {
        this.unitName = unitName;
        this.configPropertyWhiteList = configPropertyWhiteList;
    }


    @RequestMapping(value = "status", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Status getStatus(@RequestParam(value = "level", required = false, defaultValue = "0") Integer level,
                            @RequestParam(value = "single", required = false, defaultValue = "0") Boolean single) {

        if (!isEnabled) {
            throw new ResourceNotFoundException("Diagnostic API disabled");
        }

        return processLevel(level, single);
    }

    @RequestMapping(value = "status/summary", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Summary getSummary(@RequestParam(value = "level", required = false, defaultValue = "0") Integer level,
                              @RequestParam(value = "single", required = false, defaultValue = "0") Boolean single) {

        if (!isEnabled) {
            throw new ResourceNotFoundException("Diagnostic API disabled");
        }

        return new Summary(processLevel(level, single));
    }

    private Status processLevel(Integer level, Boolean single) {
        switch (level) {
            case 1:
                return levelLocalSystem();
            case 2:
                return levelConfig(single);
            case 3:
                return levelReadData(single);
            case 4:
                return levelWriteData(single);
            case 5:
                return levelDependencies(single);
            case 0:
            default:
                return new Status(unitName, Level.LEVEL_0, Rating.IDEAL, new Date());
        }
    }

    // Local System Level 1
    private Status levelLocalSystem() {

        Status status = new Status(unitName, Level.LEVEL_1, new Date());
        status.setLocalSystem(diagnosticSystemService.getSystem());
        return status;
    }

    // Config Level 2
    private Status levelConfig(Boolean single) {

        Status status = new Status(unitName, Level.LEVEL_2, new Date());
        if ( !single ) {
            status.setLocalSystem(diagnosticSystemService.getSystem());
        }
        status.setConfiguration(diagnosticConfigurationService.getConfiguration(configPropertyWhiteList));
        return status;
    }

    // Read Level 3
    private Status levelReadData(Boolean single) {

        Status status = new Status(unitName, Level.LEVEL_3, new Date());
        if ( !single ) {
            status.setLocalSystem(diagnosticSystemService.getSystem());
            status.setConfiguration(diagnosticConfigurationService.getConfiguration(configPropertyWhiteList));
        }
        status.setDatabase(diagnosticDatabaseService.readLevelTest());
        return status;
    }

    // Write Level 4
    private Status levelWriteData(Boolean single) {

        Status status = new Status(unitName, Level.LEVEL_4, new Date());
        if ( !single ) {
            status.setLocalSystem(diagnosticSystemService.getSystem());
            status.setConfiguration(diagnosticConfigurationService.getConfiguration(configPropertyWhiteList));
        }
        status.setDatabase(diagnosticDatabaseService.writeLevelTest());
        return status;
    }

    // Dependencies Level 5
    private Status levelDependencies(Boolean single) {

        Status status = new Status(unitName, Level.LEVEL_5, new Date());
        if ( !single ) {
            status.setLocalSystem(diagnosticSystemService.getSystem());
            status.setConfiguration(diagnosticConfigurationService.getConfiguration(configPropertyWhiteList));
            status.setDatabase(diagnosticDatabaseService.writeLevelTest());
        }
        status.setProviders(diagnosticDependencyService.getProviders());
        return status;
    }
}
