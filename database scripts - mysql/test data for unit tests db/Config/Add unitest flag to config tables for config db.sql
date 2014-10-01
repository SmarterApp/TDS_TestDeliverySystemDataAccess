-- =============================================
-- add column with column name 'unittestflag' to all tables
-- in tdscore_test_configs. DB  on MySQL
-- =============================================

alter table tdscore_test_configs._messageid
        add unittestflag int null;

alter table tdscore_test_configs.client
        add unittestflag int null;

alter table tdscore_test_configs.client_accommodationfamily
        add unittestflag int null;

alter table tdscore_test_configs.client_accommodations
        add unittestflag int null;

alter table tdscore_test_configs.client_allowips
        add unittestflag int null;

alter table tdscore_test_configs.client_externs
        add unittestflag int null;

alter table tdscore_test_configs.client_fieldtestpriority
        add unittestflag int null;

alter table tdscore_test_configs.client_forbiddenappsexcludeschools
        add unittestflag int null;

alter table tdscore_test_configs.client_forbiddenappslist
        add unittestflag int null;

alter table tdscore_test_configs.client_grade
        add unittestflag int null;

alter table tdscore_test_configs.client_language
        add unittestflag int null;

alter table tdscore_test_configs.client_message
        add unittestflag int null;

alter table tdscore_test_configs.client_messagearchive
        add unittestflag int null;

alter table tdscore_test_configs.client_messagetranslation
        add unittestflag int null;

alter table tdscore_test_configs.client_messagetranslationaudit
        add unittestflag int null;

alter table tdscore_test_configs.client_pilotschools
        add unittestflag int null;

alter table tdscore_test_configs.client_rtsroles
        add unittestflag int null;

alter table tdscore_test_configs.client_segmentproperties
        add unittestflag int null;

alter table tdscore_test_configs.client_server
        add unittestflag int null;

alter table tdscore_test_configs.client_subject
        add unittestflag int null;

alter table tdscore_test_configs.client_systemflags
        add unittestflag int null;

alter table tdscore_test_configs.client_tds_rtsattribute
        add unittestflag int null;

alter table tdscore_test_configs.client_tds_rtsattributevalues
        add unittestflag int null;

alter table tdscore_test_configs.client_test_itemconstraint
        add unittestflag int null;

alter table tdscore_test_configs.client_test_itemtypes
        add unittestflag int null;

alter table tdscore_test_configs.client_testeeattribute
        add unittestflag int null;

alter table tdscore_test_configs.client_testeerelationshipattribute
        add unittestflag int null;

alter table tdscore_test_configs.client_testeligibility
        add unittestflag int null;

alter table tdscore_test_configs.client_testformproperties
        add unittestflag int null;

alter table tdscore_test_configs.client_testgrades
        add unittestflag int null;

alter table tdscore_test_configs.client_testkey
        add unittestflag int null;

alter table tdscore_test_configs.client_testmode
        add unittestflag int null;

alter table tdscore_test_configs.client_testprerequisite
        add unittestflag int null;

alter table tdscore_test_configs.client_testproperties
        add unittestflag int null;

alter table tdscore_test_configs.client_testrtsspecs
        add unittestflag int null;

alter table tdscore_test_configs.client_testscorefeatures
        add unittestflag int null;

alter table tdscore_test_configs.client_testtool
        add unittestflag int null;

alter table tdscore_test_configs.client_testtoolrule
        add unittestflag int null;

alter table tdscore_test_configs.client_testtooltype
        add unittestflag int null;

alter table tdscore_test_configs.client_testwindow
        add unittestflag int null;

alter table tdscore_test_configs.client_timelimits
        add unittestflag int null;

alter table tdscore_test_configs.client_timewindow
        add unittestflag int null;

alter table tdscore_test_configs.client_tooldependencies
        add unittestflag int null;

alter table tdscore_test_configs.client_toolusage
        add unittestflag int null;

alter table tdscore_test_configs.client_voicepack
        add unittestflag int null;

alter table tdscore_test_configs.geo_clientapplication
        add unittestflag int null;

alter table tdscore_test_configs.geo_database
        add unittestflag int null;

alter table tdscore_test_configs.rts_role
        add unittestflag int null;

alter table tdscore_test_configs.statuscodes
        add unittestflag int null;

alter table tdscore_test_configs.system_applicationsettings
        add unittestflag int null;

alter table tdscore_test_configs.system_browserwhitelist
        add unittestflag int null;

alter table tdscore_test_configs.system_networkdiagnostics
        add unittestflag int null;

alter table tdscore_test_configs.tds_application
        add unittestflag int null;

alter table tdscore_test_configs.tds_applicationsettings
        add unittestflag int null;

alter table tdscore_test_configs.tds_browserwhitelist
        add unittestflag int null;

alter table tdscore_test_configs.tds_clientaccommodationtype
        add unittestflag int null;

alter table tdscore_test_configs.tds_clientaccommodationvalue
        add unittestflag int null;

alter table tdscore_test_configs.tds_configtype
        add unittestflag int null;

alter table tdscore_test_configs.tds_coremessageobject
        add unittestflag int null;

alter table tdscore_test_configs.tds_coremessageuser
        add unittestflag int null;

alter table tdscore_test_configs.tds_fieldtestpriority
        add unittestflag int null;

alter table tdscore_test_configs.tds_role
        add unittestflag int null;

alter table tdscore_test_configs.tds_systemflags
        add unittestflag int null;

alter table tdscore_test_configs.tds_testeeattribute
        add unittestflag int null;

alter table tdscore_test_configs.tds_testeerelationshipattribute
        add unittestflag int null;

alter table tdscore_test_configs.tds_testproperties
        add unittestflag int null;

alter table tdscore_test_configs.tds_testtool
        add unittestflag int null;

alter table tdscore_test_configs.tds_testtoolrule
        add unittestflag int null;

alter table tdscore_test_configs.tds_testtooltype
        add unittestflag int null;

