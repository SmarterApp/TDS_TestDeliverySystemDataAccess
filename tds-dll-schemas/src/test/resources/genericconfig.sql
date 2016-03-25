mysqldump -u root -p --no-create-info --skip-extended-insert configs2 tds_application tds_applicationsettings tds_browserwhitelist tds_coremessageobject tds_coremessageuser tds_testeeattribute tds_testeerelationshipattribute  tds_testtool tds_testtooltype > a.sql 

mysqldump -u root -p --no-create-info --skip-extended-insert configs2   client_externs client_forbiddenappslist client_messagetranslation client_systemflags  client_timelimits  client_tooldependencies client_voicepack client_testtool client_testtooltype > b.sql

mysqldump -u root -p --no-create-info --skip-extended-insert configs2    statuscodes system_applicationsettings system_browserwhitelist  system_networkdiagnostics > c.sql

delete  from  configs2._messageid;
delete  from  configs2.client;
delete  from  configs2.client_accommodationfamily;
delete  from  configs2.client_accommodations;
delete  from  configs2.client_allowips;
delete  from  configs2.client_externs;
delete  from  configs2.client_fieldtestpriority;
delete  from  configs2.client_forbiddenappsexcludeschools;
delete  from  configs2.client_forbiddenappslist;
delete  from  configs2.client_grade;
delete  from  configs2.client_language;
delete  from  configs2.client_messagetranslation;
delete  from  configs2.client_messagetranslationaudit;
delete  from  configs2.client_pilotschools;
delete  from  configs2.client_rtsroles;
delete  from  configs2.client_segmentproperties;
delete  from  configs2.client_server;
delete  from  configs2.client_subject;
delete  from  configs2.client_systemflags;
delete  from  configs2.client_tds_rtsattribute;
delete  from  configs2.client_tds_rtsattributevalues;
delete  from  configs2.client_test_itemconstraint;
delete  from  configs2.client_test_itemtypes;
delete  from  configs2.client_testeeattribute;
delete  from  configs2.client_testeerelationshipattribute;
delete  from  configs2.client_testeligibility;
delete  from  configs2.client_testformproperties;
delete  from  configs2.client_testgrades;
delete  from  configs2.client_testkey;
delete  from  configs2.client_testmode;
delete  from  configs2.client_testprerequisite;
delete  from  configs2.client_testproperties;
delete  from  configs2.client_testrtsspecs;
delete  from  configs2.client_testscorefeatures;
delete  from  configs2.client_testtool;
delete  from  configs2.client_testtoolrule;
delete  from  configs2.client_testtooltype;
delete  from  configs2.client_testwindow;
delete  from  configs2.client_timelimits;
delete  from  configs2.client_timewindow;
delete  from  configs2.client_tooldependencies;
delete  from  configs2.client_toolusage;
delete  from  configs2.client_voicepack;
delete  from  configs2.geo_clientapplication;
delete  from  configs2.geo_database;
delete  from  configs2.rts_role;
delete  from  configs2.statuscodes;
delete  from  configs2.system_applicationsettings;
delete  from  configs2.system_browserwhitelist;
delete  from  configs2.system_networkdiagnostics;
delete  from  configs2.tds_application;
delete  from  configs2.tds_applicationsettings;
delete  from  configs2.tds_browserwhitelist;
delete  from  configs2.tds_clientaccommodationtype ;
delete  from  configs2.tds_clientaccommodationvalue;
delete  from  configs2.tds_configtype;
delete  from  configs2.tds_coremessageobject;
delete  from  configs2.tds_coremessageuser;
delete  from  configs2.tds_fieldtestpriority;
delete  from  configs2.tds_role;
delete  from  configs2.tds_systemflags;
delete  from  configs2.tds_testeeattribute;
delete  from  configs2.tds_testeerelationshipattribute;
delete  from  configs2.tds_testproperties;
delete  from  configs2.tds_testtool;
delete  from  configs2.tds_testtoolrule;
delete  from  configs2.tds_testtooltype;

insert into configs2.tds_application select * from configs.tds_application ;
insert into configs2.tds_applicationsettings select * from configs.tds_applicationsettings where environment='development';
insert into configs2.tds_browserwhitelist select * from configs.tds_browserwhitelist where environment='development';
insert into configs2.client_forbiddenappslist select * from configs.client_forbiddenappslist where clientname = 'sbac';
insert into configs2.client_forbiddenappslist select * from configs.client_forbiddenappslist where clientname = 'sbac_pt';

-- Absent in JF data
--insert into configs2.client_forbiddenappsexcludeschools select * from configs.client_forbiddenappsexcludeschools where clientname = 'sbac'; 
--insert into configs2.client_forbiddenappsexcludeschools select * from configs.client_forbiddenappsexcludeschools where clientname = 'sbac_pt'; 


insert into configs2.statuscodes select * from configs.statuscodes ;
insert into configs2.system_applicationsettings select * from configs.system_applicationsettings where clientname = 'sbac'and environment='development';
insert into configs2.system_browserwhitelist select * from configs.system_browserwhitelist where clientname = 'sbac'and environment='development';
insert into configs2.system_networkdiagnostics select * from configs.system_networkdiagnostics where clientname = 'sbac';
insert into configs2.system_applicationsettings select * from configs.system_applicationsettings where clientname = 'sbac_pt'and environment='development';
insert into configs2.system_browserwhitelist select * from configs.system_browserwhitelist where clientname = 'sbac_pt'and environment='development';
insert into configs2.system_networkdiagnostics select * from configs.system_networkdiagnostics where clientname = 'sbac_pt';

insert into configs2.tds_coremessageobject select * from configs.tds_coremessageobject;
insert into configs2.tds_coremessageuser select * from configs.tds_coremessageuser;

--testpackage (TP) expects data here
 insert into configs2.tds_testeeattribute select * from configs.tds_testeeattribute ;
 insert into configs2.tds_testeerelationshipattribute select * from configs.tds_testeerelationshipattribute ;
 insert into configs2.tds_testtool select * from configs.tds_testtool ;
 insert into configs2.tds_testtooltype select * from configs.tds_testtooltype ;

-- definitely need this one!
insert into configs2.client_externs select * from configs.client_externs where clientname = 'sbac_pt'and environment='development';
insert into configs2.client_externs select * from configs.client_externs where clientname = 'sbac'and environment='development';

insert into configs2.client_messagetranslation select * from configs.client_messagetranslation where client = 'sbac';
insert into configs2.client_messagetranslation select * from configs.client_messagetranslation where client = 'sbac_pt';
insert into configs2.client_messagetranslation select * from configs.client_messagetranslation where client = 'air';

insert into configs2.client_systemflags select * from configs.client_systemflags where clientname = 'sbac';
insert into configs2.client_systemflags select * from configs.client_systemflags where clientname = 'sbac_pt';
insert into configs2.client_timelimits select * from configs.client_timelimits where clientname = 'sbac' and (environment is null or environment='development');
insert into configs2.client_timelimits select * from configs.client_timelimits where clientname = 'sbac_pt' and (environment is null or environment='development');
insert into configs2.client_voicepack select * from configs.client_voicepack where clientname = 'sbac';
insert into configs2.client_voicepack select * from configs.client_voicepack where clientname = 'sbac_pt';

-- java code does not use them, JF data for not have it
-- insert into configs2.client_tds_rtsattribute select * from configs.client_tds_rtsattribute where clientname = 'sbac';
-- insert into configs2.client_tds_rtsattributevalues select * from configs.client_tds_rtsattributevalues where clientname = 'sbac';
-- insert into configs2.client_tds_rtsattribute select * from configs.client_tds_rtsattribute where clientname = 'sbac_pt';
-- insert into configs2.client_tds_rtsattributevalues select * from configs.client_tds_rtsattributevalues where clientname = 'sbac_pt';
-- insert into configs2.client_testkey select * from configs.client_testkey where clientname = 'sbac';
-- insert into configs2.client_testkey select * from configs.client_testkey where clientname = 'sbac_pt';
-- insert into configs2.client_testrtsspecs select * from configs.client_testrtsspecs where clientname = 'sbac';
-- insert into configs2.client_testrtsspecs select * from configs.client_testrtsspecs where clientname = 'sbac_pt';
-- insert into configs2.client_testtoolrule select * from configs.client_testtoolrule where clientname = 'sbac';
-- insert into configs2.client_testtoolrule select * from configs.client_testtoolrule where clientname = 'sbac_pt';
-- insert into configs2.rts_role select * from configs.rts_role where clientname = 'sbac';
-- insert into configs2.rts_role select * from configs.rts_role where clientname = 'sbac_pt';
-- insert into configs2.tds_testtoolrule select * from configs.tds_testtoolrule;
-- insert into configs2.client_pilotschools select * from configs.client_pilotschools where clientname = 'sbac';
-- insert into configs2.client_pilotschools select * from configs.client_pilotschools where clientname = 'sbac_pt';
-- insert into configs2.client_server select * from configs.client_server where clientname = 'sbac';
-- insert into configs2.client_server select * from configs.client_server where clientname = 'sbac_pt';
--insert into configs2.client_accommodations select * from configs.client_accommodations where clientname = 'sbac';
--insert into configs2.client_accommodations select * from configs.client_accommodations where clientname = 'sbac_pt';
--insert into configs2.client_allowips select * from configs.client_allowips where clientname = 'sbac' and environment='development';
--insert into configs2.client_allowips select * from configs.client_allowips where clientname = 'sbac_pt' and environment='development';


--  TP does not do it. JF data doe not have it either; java uses it
-- insert into configs2.client_testprerequisite select * from configs.client_testprerequisite where clientname = 'sbac';
-- insert into configs2.client_testprerequisite select * from configs.client_testprerequisite where clientname = 'sbac_pt';

-- EF: JF data does not have it for SBAC or SBAC_PT. Java code uses it in multiple places
-- Did we discuss that Sai will find data for these tables in someother database?
-- insert into configs2.client_testscorefeatures select * from configs.client_testscorefeatures where clientname = 'sbac';
-- insert into configs2.client_testscorefeatures select * from configs.client_testscorefeatures where clientname = 'sbac_pt';

-- EF: what about contexttype = 'SEGMENT', seems that TP should do it
-- EF: let John know that for contexttype = 'TEST' he need to populate everything, excluding type= 'language'
--insert into configs2.client_testtooltype select * from configs.client_testtooltype  where clientname = 'sbac' and contexttype = 'test'  and toolname != 'language' ;
insert into configs2.client_testtooltype select * from configs.client_testtooltype  where clientname = 'sbac' and contexttype = 'family' ;
-- EF: what about contexttype = 'SEGMENT', seems that TP should do it														 
-- insert into configs2.client_testtool select * from configs.client_testtool where clientname = 'sbac' and contexttype = 'test'  and type != 'language' ;
insert into configs2.client_testtool select * from configs.client_testtool  where clientname = 'sbac' and contexttype = 'family' ;

 --insert into configs2.client_testtooltype select * from configs.client_testtooltype  where clientname = 'sbac_pt' and contexttype = 'test'  and toolname != 'language' ;
insert into configs2.client_testtooltype select * from configs.client_testtooltype  where clientname = 'sbac_pt' and contexttype = 'family' ;														 
-- insert into configs2.client_testtool select * from configs.client_testtool where clientname = 'sbac_pt' and contexttype = 'test'  and type != 'language' ;
insert into configs2.client_testtool select * from configs.client_testtool  where clientname = 'sbac_pt' and contexttype = 'family' ;
	
--TODO: Start discussion from here
-- EF: Category of post TestPackage processing for context != '*' ? and keep this statement here? 
--  insert into configs2.client_tooldependencies select * from configs.client_tooldependencies where clientname = 'sbac' and context = '*';
-- insert into configs2.client_tooldependencies select * from configs.client_tooldependencies where clientname = 'sbac_pt' and context = '*';

-- Assume that we do not need them and columns
-- used in _extern view will be nulls
-- insert into configs2.geo_database select * from configs.geo_database ;
--  insert into configs2.geo_clientapplication select * from configs.geo_clientapplication where clientname = 'sbac' and environment='development';
--  insert into configs2.geo_clientapplication select * from configs.geo_clientapplication where clientname = 'sbac_pt' and environment='development';











													 





