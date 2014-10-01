-- srcipt to be run from Sql server ManagementStudio before executing 
-- these three steps
--1. use tdscore_load_itembank; exec dbo.load_loaderTablesFromDB 4076;
--1a. check that dbo.loader_errors does not have erros for this configid
-- select * from dbo.loader_errors where configid= 4076;
--1b. look at loader_logicalTests.  As I understood it, logicalId should be set to ITS_ID manually
--2.0 loader_main complained about homepath being null. I ended up updating tblclient.homepath column
-- to some value. When we know where on Linux box our test items are going to be located, we need
-- to change this value.
-- update dbo.tblclient set homepath='C:\temp\'; 
--2. exec dbo.loader_main
--3. exec dbo.UpdateTDSConfigs 
--4. Now we need to export data from SQL server into  xm format to be imported into MySql.
-- use shared-db-test module,
-- org.opentestsystem.unittest.db.export.AssessmentExporter.java program.
-- It creates exportedAsessmentConfigsFromMSSQL.xml and
-- exportedAssessmentItembankFromMSSQL.xml in target/classes/export directory for
-- shared-db-test module.
--5. YOu would need to copy these files to location where your application using
-- DBDataDeployer expects them. 
-- For example, for testadmin-user-stories,
-- copy them to src/test/resources/import/configs/ and
-- src/test/resources/import/itembank directories respectevly.
--6. Make sure that corresponding DBDataDeployers are configured to know appropriate file
--  location and name.
-- For example:
--<bean id="configsData" class="org.opentestsystem.unittest.db.DBDataDeployerImpl">
--		<property name="dataSource" ref="configsSchema"/>
--		<property name="dataDefinitionName" value="configs"/>
--	  	<property name="importFileName" value="exportedAsessmentConfigsFromMSSQL.xml"/> 
--	</bean>
-- 
delete  from  tdscore_load_configs.dbo._MessageID;
delete  from  tdscore_load_configs.dbo.Client;
delete  from  tdscore_load_configs.dbo.Client_AccommodationFamily;
delete  from  tdscore_load_configs.dbo.Client_Accommodations;
delete  from  tdscore_load_configs.dbo.Client_AllowIPs;
delete  from  tdscore_load_configs.dbo.Client_Externs;
delete  from  tdscore_load_configs.dbo.Client_FieldtestPriority;
delete  from  tdscore_load_configs.dbo.Client_ForbiddenAppsExcludeSchools;
delete  from  tdscore_load_configs.dbo.Client_ForbiddenAppsList;
delete  from  tdscore_load_configs.dbo.Client_Grade;
delete  from  tdscore_load_configs.dbo.Client_Language;
delete  from  tdscore_load_configs.dbo.Client_MessageTranslation;
delete  from  tdscore_load_configs.dbo.Client_MessageTranslationAudit;
delete  from  tdscore_load_configs.dbo.Client_PilotSchools;
delete  from  tdscore_load_configs.dbo.Client_RTSRoles;
delete  from  tdscore_load_configs.dbo.Client_SegmentProperties;
delete  from  tdscore_load_configs.dbo.Client_Server;
delete  from  tdscore_load_configs.dbo.Client_Subject;
delete  from  tdscore_load_configs.dbo.Client_SystemFlags;
delete  from  tdscore_load_configs.dbo.Client_TDS_RTSAttribute;
delete  from  tdscore_load_configs.dbo.Client_TDS_RTSAttributeValues;
delete  from  tdscore_load_configs.dbo.Client_Test_ItemConstraint;
delete  from  tdscore_load_configs.dbo.Client_Test_Itemtypes;
delete  from  tdscore_load_configs.dbo.Client_TesteeAttribute;
delete  from  tdscore_load_configs.dbo.Client_TesteeRelationshipAttribute;
delete  from  tdscore_load_configs.dbo.Client_TestEligibility;
delete  from  tdscore_load_configs.dbo.Client_TestformProperties;
delete  from  tdscore_load_configs.dbo.Client_TestGrades;
delete  from  tdscore_load_configs.dbo.Client_Testkey;
delete  from  tdscore_load_configs.dbo.Client_TestMode;
delete  from  tdscore_load_configs.dbo.Client_TestPrerequisite;
delete  from  tdscore_load_configs.dbo.Client_TestProperties;
delete  from  tdscore_load_configs.dbo.Client_TestRTSSpecs;
delete  from  tdscore_load_configs.dbo.Client_TestscoreFeatures;
delete  from  tdscore_load_configs.dbo.Client_TestTool;
delete  from  tdscore_load_configs.dbo.Client_TestToolRule;
delete  from  tdscore_load_configs.dbo.Client_TestToolType;
delete  from  tdscore_load_configs.dbo.Client_TestWindow;
delete  from  tdscore_load_configs.dbo.Client_TimeLimits;
delete  from  tdscore_load_configs.dbo.Client_TimeWindow;
delete  from  tdscore_load_configs.dbo.Client_ToolDependencies;
delete  from  tdscore_load_configs.dbo.Client_ToolUsage;
delete  from  tdscore_load_configs.dbo.Client_VoicePack;
delete  from  tdscore_load_configs.dbo.GEO_ClientApplication;
delete  from  tdscore_load_configs.dbo.GEO_Database;
delete  from  tdscore_load_configs.dbo.RTS_Role;
delete  from  tdscore_load_configs.dbo.StatusCodes;
delete  from  tdscore_load_configs.dbo.System_ApplicationSettings;
delete  from  tdscore_load_configs.dbo.System_BrowserWhiteList;
delete  from  tdscore_load_configs.dbo.System_NetworkDiagnostics;
delete  from  tdscore_load_configs.dbo.TDS_Application;
delete  from  tdscore_load_configs.dbo.TDS_ApplicationSettings;
delete  from  tdscore_load_configs.dbo.TDS_BrowserWhiteList;
delete  from  tdscore_load_configs.dbo.TDS_ClientAccommodationType ;
delete  from  tdscore_load_configs.dbo.TDS_ClientAccommodationValue;
delete  from  tdscore_load_configs.dbo.TDS_ConfigType;
delete  from  tdscore_load_configs.dbo.TDS_CoreMessageObject;
delete  from  tdscore_load_configs.dbo.TDS_CoreMessageUser;
delete  from  tdscore_load_configs.dbo.TDS_FieldtestPriority;
delete  from  tdscore_load_configs.dbo.TDS_Role;
delete  from  tdscore_load_configs.dbo.TDS_SystemFlags;
delete  from  tdscore_load_configs.dbo.TDS_TesteeAttribute;
delete  from  tdscore_load_configs.dbo.TDS_TesteeRelationshipAttribute;
delete  from  tdscore_load_configs.dbo.TDS_TestProperties;
delete  from  tdscore_load_configs.dbo.TDS_TestTool;
delete  from  tdscore_load_configs.dbo.TDS_TestToolRule;
delete  from  tdscore_load_configs.dbo.TDS_TestToolType;

delete  from  tdscore_load_itembank.dbo._dblatency;
delete  from  tdscore_load_itembank.dbo._Synonyms;
delete  from  tdscore_load_itembank.dbo._Sys_FormtestItems;
delete  from  tdscore_load_itembank.dbo._TestUpdate;
delete  from  tdscore_load_itembank.dbo.AA_ItemCL;
delete  from  tdscore_load_itembank.dbo.AffinityGroup;
delete  from  tdscore_load_itembank.dbo.AffinityGroupItem;
delete  from  tdscore_load_itembank.dbo.AllowedItemProps;
delete  from  tdscore_load_itembank.dbo.ConfigsLoaded;
delete  from  tdscore_load_itembank.dbo.ImportItemCohorts;
delete  from  tdscore_load_itembank.dbo.ImportTestCohorts;
delete  from  tdscore_load_itembank.dbo.ItemMeasurementParameter;
delete  from  tdscore_load_itembank.dbo.ItemScoreDimension;
delete  from  tdscore_load_itembank.dbo.Loader_Accommodations;
delete  from  tdscore_load_itembank.dbo.Loader_AffinityGroups;
delete  from  tdscore_load_itembank.dbo.Loader_AffinityItems;
delete  from  tdscore_load_itembank.dbo.Loader_ContentLevels;
delete  from  tdscore_load_itembank.dbo.Loader_Errors;
delete  from  tdscore_load_itembank.dbo.Loader_FormItems;
delete  from  tdscore_load_itembank.dbo.Loader_Forms;
delete  from  tdscore_load_itembank.dbo.Loader_Itembank;
delete  from  tdscore_load_itembank.dbo.Loader_ItemCLAncestors;
delete  from  tdscore_load_itembank.dbo.Loader_ItemProperties;
delete  from  tdscore_load_itembank.dbo.Loader_Items;
delete  from  tdscore_load_itembank.dbo.Loader_ItemScoreDimension;
delete  from  tdscore_load_itembank.dbo.Loader_LogicalTests;
delete  from  tdscore_load_itembank.dbo.Loader_MeasurementParameter;
delete  from  tdscore_load_itembank.dbo.Loader_ProficiencyLevels;
delete  from  tdscore_load_itembank.dbo.Loader_RulesRelaxValidation;
delete  from  tdscore_load_itembank.dbo.Loader_Stimuli;
delete  from  tdscore_load_itembank.dbo.Loader_StimulusProperties;
delete  from  tdscore_load_itembank.dbo.Loader_Strands;
delete  from  tdscore_load_itembank.dbo.Loader_Tests;
delete  from  tdscore_load_itembank.dbo.MeasurementModel;
delete  from  tdscore_load_itembank.dbo.MeasurementParameter;
delete  from  tdscore_load_itembank.dbo.PerformanceLevels;
delete  from  tdscore_load_itembank.dbo.Projects;
delete  from  tdscore_load_itembank.dbo.SetofTestGrades;
delete  from  tdscore_load_itembank.dbo.tblAdminStimulus;
delete  from  tdscore_load_itembank.dbo.tblAdminStrand;
delete  from  tdscore_load_itembank.dbo.tblAlternateTest;
delete  from  tdscore_load_itembank.dbo.tblClient;
delete  from  tdscore_load_itembank.dbo.tblItem;
delete  from  tdscore_load_itembank.dbo.tblItemBank;
delete  from  tdscore_load_itembank.dbo.tblItemProps;
delete  from  tdscore_load_itembank.dbo.tblSetofAdminItems;
delete  from  tdscore_load_itembank.dbo.tblSetofAdminSubjects;
delete  from  tdscore_load_itembank.dbo.tblSetofItemStimuli;
delete  from  tdscore_load_itembank.dbo.tblSetofItemStrands;
delete  from  tdscore_load_itembank.dbo.tblStimulus;
delete  from  tdscore_load_itembank.dbo.tblStrand;
delete  from  tdscore_load_itembank.dbo.tblSubject;
delete  from  tdscore_load_itembank.dbo.tblTestAdmin;
delete  from  tdscore_load_itembank.dbo.TestCohort;
delete  from  tdscore_load_itembank.dbo.TestForm;
delete  from  tdscore_load_itembank.dbo.TestFormItem;

-- please do not change order of inserts!
--insert into tdscore_load_configs.dbo._MessageID select * from tdscore_test_configs_2013.dbo._MessageID; 
insert into tdscore_load_configs.dbo.TDS_Application select * from tdscore_test_configs_2013.dbo.TDS_Application ;
insert into tdscore_load_configs.dbo.TDS_ApplicationSettings select * from tdscore_test_configs_2013.dbo.TDS_ApplicationSettings where environment='development';
insert into tdscore_load_configs.dbo.TDS_BrowserWhiteList select * from tdscore_test_configs_2013.dbo.TDS_BrowserWhiteList where environment='development';
insert into tdscore_load_configs.dbo.TDS_ClientAccommodationType select * from tdscore_test_configs_2013.dbo.TDS_ClientAccommodationType where clientname='minnesota_pt' ;
insert into tdscore_load_configs.dbo.TDS_ClientAccommodationValue select * from tdscore_test_configs_2013.dbo.TDS_ClientAccommodationValue where clientname='minnesota_pt';
insert into tdscore_load_configs.dbo.TDS_ConfigType select * from tdscore_test_configs_2013.dbo.TDS_ConfigType ;

SET IDENTITY_INSERT tdscore_load_configs.dbo.TDS_CoreMessageObject ON;
insert into tdscore_load_configs.dbo.TDS_CoreMessageObject(_key, context,contexttype, messageid,ownerapp, appkey,[message],paralabels,fromclient,datealtered, nodes, ismessageshowtouser) 
select _key, context,contexttype, messageid,ownerapp, appkey,[message],paralabels,fromclient,datealtered, nodes, ismessageshowtouser from tdscore_test_configs_2013.dbo.TDS_CoreMessageObject ;
SET IDENTITY_INSERT tdscore_load_configs.dbo.TDS_CoreMessageObject OFF;

insert into tdscore_load_configs.dbo.TDS_CoreMessageUser select * from tdscore_test_configs_2013.dbo.TDS_CoreMessageUser;

insert into tdscore_load_configs.dbo.TDS_TesteeAttribute select * from tdscore_test_configs_2013.dbo.TDS_TesteeAttribute ;
insert into tdscore_load_configs.dbo.TDS_FieldtestPriority select * from tdscore_test_configs_2013.dbo.TDS_FieldtestPriority ;

insert into tdscore_load_configs.dbo.TDS_Role select * from tdscore_test_configs_2013.dbo.TDS_Role;
insert into tdscore_load_configs.dbo.TDS_SystemFlags select * from tdscore_test_configs_2013.dbo.TDS_SystemFlags ;

insert into tdscore_load_configs.dbo.TDS_TesteeRelationshipAttribute select * from tdscore_test_configs_2013.dbo.TDS_TesteeRelationshipAttribute ;
insert into tdscore_load_configs.dbo.TDS_TestProperties select * from tdscore_test_configs_2013.dbo.TDS_TestProperties ;
insert into tdscore_load_configs.dbo.TDS_TestTool select * from tdscore_test_configs_2013.dbo.TDS_TestTool ;
insert into tdscore_load_configs.dbo.TDS_TestToolRule select * from tdscore_test_configs_2013.dbo.TDS_TestToolRule;
insert into tdscore_load_configs.dbo.TDS_TestToolType select * from tdscore_test_configs_2013.dbo.TDS_TestToolType ;

insert into tdscore_load_configs.dbo.Client select * from tdscore_test_configs_2013.dbo.Client where name = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_AccommodationFamily select * from tdscore_test_configs_2013.dbo.Client_AccommodationFamily where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_Accommodations select * from tdscore_test_configs_2013.dbo.Client_Accommodations where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_AllowIPs select * from tdscore_test_configs_2013.dbo.Client_AllowIPs where clientname = 'Minnesota_PT' and environment='development';
insert into tdscore_load_configs.dbo.Client_Externs select * from tdscore_test_configs_2013.dbo.Client_Externs where clientname = 'Minnesota_PT'and environment='development';

insert into tdscore_load_configs.dbo.Client_TesteeAttribute select * from tdscore_test_configs_2013.dbo.Client_TesteeAttribute where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_FieldtestPriority select * from tdscore_test_configs_2013.dbo.Client_FieldtestPriority where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_ForbiddenAppsExcludeSchools select * from tdscore_test_configs_2013.dbo.Client_ForbiddenAppsExcludeSchools where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_ForbiddenAppsList select * from tdscore_test_configs_2013.dbo.Client_ForbiddenAppsList where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_Grade select * from tdscore_test_configs_2013.dbo.Client_Grade where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_Language select * from tdscore_test_configs_2013.dbo.Client_Language where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_MessageTranslation select * from tdscore_test_configs_2013.dbo.Client_MessageTranslation where client = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_MessageTranslation select * from tdscore_test_configs_2013.dbo.Client_MessageTranslation where client = 'Minnesota';
insert into tdscore_load_configs.dbo.Client_MessageTranslation select * from tdscore_test_configs_2013.dbo.Client_MessageTranslation where client = 'AIR';
insert into tdscore_load_configs.dbo.Client_MessageTranslationAudit select * from tdscore_test_configs_2013.dbo.Client_MessageTranslationAudit where client = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_PilotSchools select * from tdscore_test_configs_2013.dbo.Client_PilotSchools where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_RTSRoles select * from tdscore_test_configs_2013.dbo.Client_RTSRoles where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_SegmentProperties select * from tdscore_test_configs_2013.dbo.Client_SegmentProperties where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_Server select * from tdscore_test_configs_2013.dbo.Client_Server where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_Subject select * from tdscore_test_configs_2013.dbo.Client_Subject where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_SystemFlags select * from tdscore_test_configs_2013.dbo.Client_SystemFlags where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_TDS_RTSAttribute select * from tdscore_test_configs_2013.dbo.Client_TDS_RTSAttribute where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_TDS_RTSAttributeValues select * from tdscore_test_configs_2013.dbo.Client_TDS_RTSAttributeValues where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_Test_ItemConstraint select * from tdscore_test_configs_2013.dbo.Client_Test_ItemConstraint where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_Test_Itemtypes select * from tdscore_test_configs_2013.dbo.Client_Test_Itemtypes where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_TesteeRelationshipAttribute select * from tdscore_test_configs_2013.dbo.Client_TesteeRelationshipAttribute where clientname = 'Minnesota_PT';

insert into tdscore_load_configs.dbo.Client_TestEligibility select * from tdscore_test_configs_2013.dbo.Client_TestEligibility where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_TestformProperties select * from tdscore_test_configs_2013.dbo.Client_TestformProperties where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_TestGrades select * from tdscore_test_configs_2013.dbo.Client_TestGrades where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_Testkey select * from tdscore_test_configs_2013.dbo.Client_Testkey where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_TestMode select * from tdscore_test_configs_2013.dbo.Client_TestMode where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_TestPrerequisite select * from tdscore_test_configs_2013.dbo.Client_TestPrerequisite where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_TestProperties select * from tdscore_test_configs_2013.dbo.Client_TestProperties where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_TestRTSSpecs select * from tdscore_test_configs_2013.dbo.Client_TestRTSSpecs where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_TestscoreFeatures select * from tdscore_test_configs_2013.dbo.Client_TestscoreFeatures where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_TestToolRule select * from tdscore_test_configs_2013.dbo.Client_TestToolRule where clientname = 'Minnesota_PT';

insert into tdscore_load_configs.dbo.Client_TimeWindow select * from tdscore_test_configs_2013.dbo.Client_TimeWindow where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_TestWindow select * from tdscore_test_configs_2013.dbo.Client_TestWindow where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_TestToolType select * from tdscore_test_configs_2013.dbo.Client_TestToolType where clientname = 'Minnesota_PT';

insert into tdscore_load_configs.dbo.Client_TestTool select * from tdscore_test_configs_2013.dbo.Client_TestTool where clientname = 'Minnesota_PT';

insert into tdscore_load_configs.dbo.Client_TimeLimits select * from tdscore_test_configs_2013.dbo.Client_TimeLimits where clientname = 'Minnesota_PT' and (environment is null or environment='development');
insert into tdscore_load_configs.dbo.Client_ToolDependencies select * from tdscore_test_configs_2013.dbo.Client_ToolDependencies where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_ToolUsage select * from tdscore_test_configs_2013.dbo.Client_ToolUsage where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_VoicePack select * from tdscore_test_configs_2013.dbo.Client_VoicePack where clientname = 'Minnesota_PT';

insert into tdscore_load_configs.dbo.GEO_Database select * from tdscore_test_configs_2013.dbo.GEO_Database ;
insert into tdscore_load_configs.dbo.GEO_ClientApplication select * from tdscore_test_configs_2013.dbo.GEO_ClientApplication where clientname = 'Minnesota_PT' and environment='development';

insert into tdscore_load_configs.dbo.RTS_Role select * from tdscore_test_configs_2013.dbo.RTS_Role where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.StatusCodes select * from tdscore_test_configs_2013.dbo.StatusCodes ;
insert into tdscore_load_configs.dbo.System_ApplicationSettings select * from tdscore_test_configs_2013.dbo.System_ApplicationSettings where clientname = 'Minnesota_PT'and environment='development';
insert into tdscore_load_configs.dbo.System_BrowserWhiteList select * from tdscore_test_configs_2013.dbo.System_BrowserWhiteList where clientname = 'Minnesota_PT'and environment='development';
insert into tdscore_load_configs.dbo.System_NetworkDiagnostics select * from tdscore_test_configs_2013.dbo.System_NetworkDiagnostics where clientname = 'Minnesota_PT';

XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
Generic config data for Minnesota_PT. Used by Sai as base for work on TestPackage
insert into tdscore_load_configs.dbo.Client_MessageTranslation select * from tdscore_test_configs_2013.dbo.Client_MessageTranslation where client = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_MessageTranslation select * from tdscore_test_configs_2013.dbo.Client_MessageTranslation where client = 'Minnesota';
insert into tdscore_load_configs.dbo.Client_MessageTranslation select * from tdscore_test_configs_2013.dbo.Client_MessageTranslation where client = 'AIR';

insert into tdscore_load_configs.dbo.Client_ForbiddenAppsList select * from tdscore_test_configs_2013.dbo.Client_ForbiddenAppsList where clientname = 'Minnesota_PT';
insert into tdscore_load_configs.dbo.Client_SystemFlags select * from tdscore_test_configs_2013.dbo.Client_SystemFlags where clientname = 'Minnesota_PT';

insert into tdscore_load_configs.dbo.Client_TesteeRelationshipAttribute select * from tdscore_test_configs_2013.dbo.Client_TesteeRelationshipAttribute where clientname = 'Minnesota_PT';

insert into tdscore_load_configs.dbo.Client_TestToolType select * from tdscore_test_configs_2013.dbo.Client_TestToolType 
where clientname = 'Minnesota_PT' and (context = '*' or contexttype = 'FAMILY');


insert into tdscore_load_configs.dbo.Client_TestToolType select * from tdscore_test_configs_2013.dbo.Client_TestToolType 
where clientname = 'Minnesota_PT' and (context != '*' and contexttype != 'FAMILY' and toolname != 'language' and toolname != 'passage font size');

insert into tdscore_load_configs.dbo.Client_TestTool select * from tdscore_test_configs_2013.dbo.Client_TestTool
where clientname = 'Minnesota_PT' and (context = '*' or contexttype = 'FAMILY');

insert into tdscore_load_configs.dbo.Client_TestTool
select * from tdscore_test_configs_2013.dbo.Client_TestTool
where clientname = 'Minnesota_PT' and (context != '*' and contexttype != 'FAMILY' and type != 'language' and type != 'passage font size');

 insert into tdscore_load_configs.dbo.Client_VoicePack select * from tdscore_test_configs_2013.dbo.Client_VoicePack where clientname = 'Minnesota_PT';




 
