CREATE TABLE __accommodationcache ( 
	_key         	bigint auto_increment,
	testkey      	varchar(250) NOT NULL,
	clientname   	varchar(100) NOT NULL,
	_date        	datetime NOT NULL,
	dategenerated	datetime NULL,
	CONSTRAINT pk_accomcache PRIMARY KEY CLUSTERED(_Key)
);

CREATE TABLE __cachedaccommodations ( 
	_fk_accommodationcache	bigint not null,
	segment               	int null,
	disableonguestsession 	bit null,
	tooltypesortorder     	int null,
	toolvaluesortorder    	int null,
	typemode              	varchar(100) null,
	toolmode              	varchar(100) null,
	acctype               	varchar(200) null,
	accvalue              	varchar(200) null,
	acccode               	varchar(200) null,
	isdefault             	bit null,
	allowcombine          	bit null,
	isfunctional          	bit null,
	allowchange           	bit null,
	isselectable          	bit null,
	isvisible             	bit null,
	studentcontrol        	bit null,
	valcount              	int null,
	dependsontooltype     	varchar(100) null 
);

create table __cachedaccomdepends ( 
	_fk_accommodationcache	bigint not null,
	contexttype           	varchar(100) null,
	`context`              	varchar(100) null,
	testmode              	varchar(50) null,
	iftype                	varchar(200) null,
	ifvalue               	varchar(200) null,
	thentype              	varchar(200) null,
	thenvalue             	varchar(200) null,
	isdefault             	bit null 
);

create table __appmessagecontexts (
	clientname varchar(100) not null,
	systemid varchar(100) not null,
	`language` varchar(30) null,
	contextlist text null,
	_key bigint auto_increment not null,
	dategenerated datetime(3) null,
	soundx char(4) null,
	contextindex varchar(50) null,
	delim char(1) null,
	constraint pk_messagecontext primary key nonclustered (_key asc)
);

create table __appmessages (
	_fk_appmessagecontext bigint null,
	msgkey bigint null,
	msgsource varchar(100) null,
	messageid int null,
	contexttype varchar(50) null,
	`context` varchar(100) null,
	appkey varchar(255) null,
	`language` varchar(30) null,
	grade varchar(25) null,
	`subject` varchar(50) null,
	paralabels varchar(255) null,
	message text null
);

create table `client`  ( 
	`name`            	varchar(100) not null,
	`origin`          	varchar(50) null,
	`internationalize`	bit not null default 1,
	`defaultlanguage` 	varchar(50) not null default 'enu',
	constraint `pk_client` primary key clustered(`name`)
);

create table `client_accommodationfamily`  ( 
	`clientname`	varchar(100) not null,
	`family`    	varchar(50) not null,
	`label`     	varchar(200) not null,
	constraint `pk_accomfamily` primary key clustered(`clientname`,`family`)
);

create table `client_accommodations`  ( 
	`clientname`      	varchar(100) not null,
	`type`            	varchar(255) not null,
	`code`            	varchar(255) not null,
	`value`           	varchar(255) not null,
	`isdefault`       	bit not null,
	`allowcombine`    	bit not null,
	`allowchange`     	bit not null,
	`isselectable`    	bit not null,
	`isvisible`       	bit not null default 1,
	`valuedescription`	varchar(500) null,
	`tideselectable`  	bit null,
	`rtsfieldname`    	varchar(100) not null,
	constraint `pk_clientaccommodations` primary key clustered(`clientname`,`code`)
);

create table `client_allowips`  ( 
	`clientname`     	varchar(100) not null,
	`applicationname`	varchar(50) not null,
	`allowip`        	varchar(25) not null,
	`datebegin`      	datetime(3) not null,
	`dateend`        	datetime(3) not null,
	`comments`       	varchar(255) null,
	`userid`         	varchar(50) null,
	`environment`    	varchar(100) not null,
	`message`        	text null,
	`datecreated`    	datetime(3) null, -- constraint `df_client_allowips_datecreated`  default (getdate()),
	constraint `pk_client_allowips` primary key clustered(`clientname`,`applicationname`,`allowip`,`environment`)
);

create table `client_externs`  ( 
	`_key`              	varbinary(16) not null, -- constraint `df_client_externs__key`  default (newid()),
	`testeedb`          	varchar(255) not null,
	`proctordb`         	varchar(255) not null,
	`testdb`            	varchar(255) not null,
	`scorerdb`          	varchar(255) null,
	`archivedb`         	varchar(255) null,
	`testeetype`        	varchar(255) not null default 'rts',
	`proctortype`       	varchar(255) not null default 'rts',
	`scorertype`        	varchar(255) not null default 'rts',
	`sessiondb`         	varchar(255) null,
	`errorsserver`      	varchar(255) null,
	`errorsdb`          	varchar(255) null,
	`clientname`        	varchar(100) not null,
	`bankadminkey`      	varchar(150) null,
	`clientstylepath`   	varchar(500) null,
	`qaxmlpath`         	varchar(500) null,
	`environment`       	varchar(100) not null,
	`ispracticetest`    	bit not null,
	`timezoneoffset`    	int not null default 0,
	`publishurl`        	varchar(255) null,
	`initialreportingid`	bigint not null default 1,
	`initialsessionid`  	bigint not null default 1,
	`qabrokerguid`      	varbinary(16) null,
	`expiresatminutes`  	int null,
	constraint `pk_client_externs` primary key clustered(`_key`)
);

create table `client_fieldtestpriority`  ( 
	`tds_id`    	varchar(50) not null,
	`clientname`	varchar(100) not null,
	`priority`  	int not null,
	`testid`    	varchar(200) not null,
	constraint `pk_clientft` primary key clustered(`clientname`,`testid`,`tds_id`,`priority`)
);

create table `client_forbiddenappsexcludeschools`  ( 
	`districtname`	varchar(150) null,
	`districtid`  	varchar(150) not null,
	`schoolname`  	varchar(150) null,
	`schoolid`    	varchar(150) not null,
	`clientname`  	varchar(100) not null,
	constraint `pk_client_forbiddenappsexcludeschools` primary key clustered(`districtid`,`schoolid`,`clientname`)
);

create table `client_forbiddenappslist`  ( 
	`os_id`             	varchar(25) not null,
	`processname`       	varchar(150) not null,
	`processdescription`	varchar(150) null,
	`clientname`        	varchar(100) not null,
	constraint `pk_client_forbiddenappslist` primary key clustered(`os_id`,`processname`,`clientname`)
);

create table `client_grade`  ( 
	`gradecode` 	varchar(25) not null,
	`grade`     	varchar(64) not null,
	`clientname`	varchar(100) not null,
	`origin`    	varchar(50) null, -- constraint `df__client_gr__origi__5d56b96f`  default (db_name()),
	constraint `pk_client_grade` primary key clustered(`gradecode`,`clientname`)
);

create table `client_language`  ( 
	`clientname`  	varchar(100) not null,
	`language`    	varchar(100) not null,
	`languagecode`	varchar(25) not null,
	`origin`      	varchar(50) null, -- constraint `df__client_la__origi__5f3f01e1`  default (db_name()),
	constraint `pk_client_language` primary key clustered(`clientname`,`languagecode`)
);

create table `client_message`  ( 
	`clientname`    	varchar(100) not null,
	`messageid`     	int not null,
	`translationid` 	int not null default 0,
	`systemid`      	varchar(25) not null,
	`contexttype`   	varchar(25) not null,
	`context`       	varchar(100) not null,
	`defaultmessage`	varchar(255) not null,
	`clientmessage` 	text null,
	`paralabels`    	varchar(255) null,
	`languagecode`  	varchar(25) null default 'enu',
	`gradecode`     	varchar(25) null,
	`subject`       	varchar(100) null,
	`priority`      	int not null default 0,
	`usefilepath`   	bit not null default 0,
	`datechanged`   	datetime(3) not null, -- constraint `df_client_message_datechanged`  default (getdate()),
	`datepublished` 	datetime(3) null,
	constraint `pk_client_message` primary key clustered(`clientname`,`messageid`,`translationid`,`systemid`)
);

create table `client_messagearchive`  ( 
	`clientname`    	varchar(100) not null,
	`messageid`     	int not null,
	`translationid` 	int not null default 0,
	`systemid`      	varchar(25) not null,
	`contexttype`   	varchar(25) not null,
	`context`       	varchar(100) not null,
	`defaultmessage`	varchar(255) not null,
	`clientmessage` 	text null,
	`paralabels`    	varchar(255) null,
	`languagecode`  	varchar(25) null,
	`gradecode`     	varchar(25) null,
	`subject`       	varchar(100) null,
	`priority`      	int not null  default 0,
	`usefilepath`   	bit not null  default 0,
	`datechanged`   	datetime(3) not null, -- constraint `df__client_me__datec__3c89f72a`  default (getdate()),
	`datepublished` 	datetime(3) null,
	`_date`         	datetime(3) not null, -- constraint `df__client_me___date__3d7e1b63`  default (getdate()),
	`username`      	varchar(128) null,
	`action`        	varchar(128) null,
	constraint `pk_client_messagearchive` primary key clustered(`clientname`,`messageid`,`translationid`,`systemid`,`_date`)
);

create table `client_messagetranslation`  ( 
	`_fk_coremessageobject`	bigint not null,
	`client`               	varchar(100) not null,
	`message`              	text not null,
	`language`             	varchar(30) not null,
	`grade`                	varchar(25) not null default '--any--',
	`subject`              	varchar(50) not null default '--any--',
	`_key`                 	varbinary(16) not null, -- constraint `df__client_mes___key__1293bd5e`  default (newid()),
	`datealtered`          	datetime(3) null,
	constraint `pk_clientmsgtranslation` primary key clustered(`_key`)
);

create table `client_messagetranslationaudit`  ( 
	`_fk_coremessageobject`	bigint not null,
	`context`              	varchar(100) not null,
	`contexttype`          	varchar(50) not null,
	`client`               	varchar(100) not null,
	`appkey`               	varchar(255) not null,
	`message`              	text null,
	`messageid`            	int not null,
	`language`             	varchar(30) not null,
	`grade`                	varchar(25) not null,
	`subject`              	varchar(50) not null,
	`datealtered`          	datetime(3) not null,
	`username`             	varchar(128) not null,
	`_date`                	datetime(3) not null,
	`action`               	varchar(128) not null 
);

create table `client_pilotschools`  ( 
	`clientname` 	varchar(100) not null,
	`_efk_testid`	varchar(50) not null,
	`schoolid`   	varchar(50) not null,
	`schoolname` 	varchar(100) null,
	constraint `pk_client_pilotschools` primary key clustered(`clientname`,`_efk_testid`,`schoolid`)
);

create table `client_rtsroles`  ( 
	`tds_role`     	varchar(100) not null,
	`rts_role`     	varchar(100) not null,
	`clientname`   	varchar(100) not null,
	`datechanged`  	datetime(3) null, -- default (getdate()),
	`datepublished`	datetime(3) null,
	`sessiontype`  	int not null default 0,
	constraint `pk_clientrtsroles` primary key clustered(`clientname`,`tds_role`,`rts_role`,`sessiontype`)
);

create table `client_segmentproperties`  ( 
	`ispermeable`    	int not null,
	`clientname`     	varchar(100) not null,
	`entryapproval`  	int not null,
	`exitapproval`   	int not null,
	`itemreview`     	bit not null default 1,
	`segmentid`      	varchar(255) not null,
	`segmentposition`	int not null,
	`parenttest`     	varchar(255) not null,
	`ftstartdate`    	datetime(3) null,
	`ftenddate`      	datetime(3) null,
	`label`          	varchar(255) null,
	`modekey`        	varchar(250) null,
	`restart`           int null,
	`graceperiodminutes` int null,
	constraint `pk_segmentprops` primary key clustered(`clientname`,`parenttest`,`segmentid`)
);

create table `client_server`  ( 
	`_key`           	varbinary(16) not null, -- constraint `df_client_server__key`  default (newid()),
	`clientname`     	varchar(100) not null,
	`applicationname`	varchar(100) not null,
	`serverip`       	varchar(100) not null,
	`serverid`       	varchar(100) not null,
	`servertype`     	varchar(100) not null,
	`virtualdirname` 	varchar(50) null,
	constraint `pk_client_server` primary key clustered(`_key`)
);

create table `client_subject`  ( 
	`subjectcode`	varchar(25) null,
	`subject`    	varchar(100) not null,
	`clientname` 	varchar(100) not null,
	`origin`     	varchar(50) null, -- constraint `df__client_su__origi__61274a53`  default (db_name(,
	constraint `pk_client_subject_1` primary key clustered(`subject`,`clientname`)
);

create table `client_systemflags`  ( 
	`auditobject`   	varchar(255) not null,
	`ison`          	int not null,
	`description`   	varchar(255) null,
	`clientname`    	varchar(100) not null,
	`ispracticetest`	bit not null,
	`datechanged`   	datetime(3) null, -- constraint `df__client_sy__datec__3e1d39e1`  default (getdate()),
	`datepublished` 	datetime(3) null,
	constraint `pk_clientsystemflags` primary key clustered(`clientname`,`auditobject`,`ispracticetest`)
);

create table `client_tds_rtsattribute`  ( 
	`clientname`     	varchar(100) not null,
	`name`           	varchar(50) not null,
	`fieldname`      	varchar(35) not null,
	`datatype`       	varchar(50) null default 'codelist',
	`_efk_entitytype`	bigint not null default 6,
	`type`           	varchar(25) not null default 'attribute',
	`entitytype`     	varchar(100) null,
	constraint `pk_rts_attribute` primary key clustered(`clientname`,`fieldname`,`_efk_entitytype`,`type`)
);

create table `client_tds_rtsattributevalues`  ( 
	`clientname`     	varchar(100) not null,
	`fieldname`      	varchar(35) not null,
	`value`          	varchar(365) not null,
	`allowcombine`   	bit not null default 1,
	`_efk_entitytype`	bigint not null  default 6,
	`type`           	varchar(25) not null default 'attribute',
	constraint `pk_rts_attributevalues` primary key clustered(`clientname` (50),`fieldname`,`value` (100),`_efk_entitytype`,`type`)
	
);

create table `client_testeligibility`  ( 
	`_key`           	varbinary(16) not null, -- default (newid()),
	`clientname`     	varchar(100) not null,
	`testid`         	varchar(150) not null,
	`rtsname`        	varchar(100) not null,
	`enables`        	bit not null,
	`disables`       	bit not null,
	`rtsvalue`       	varchar(400) not null,
	`_efk_entitytype`	bigint not null,
	`eligibilitytype`	varchar(50) null,
	`matchtype`      	int not null default 0,
	constraint `pk_client_testeligibility` primary key clustered(`_key`)
);

create table `client_testgrades`  ( 
	`clientname`       	varchar(100) not null,
	`testid`           	varchar(150) not null,
	`grade`            	varchar(25) not null,
	`requireenrollment`	bit not null default 0,
	`enrolledsubject`  	varchar(100) null,
	constraint `pk_testgrades` primary key clustered(`clientname`,`testid`,`grade`)
);

create table `client_testmode`  ( 
	`clientname`            	varchar(100) not null,
	`testid`                	varchar(200) not null,
	`mode`                  	varchar(50) not null default 'online',
	`algorithm`             	varchar(50) null,
	`formtideselectable`    	bit not null default 0,
	`issegmented`           	bit not null default 0,
	`maxopps`               	int not null default 50,
	`requirertsform`        	bit not null default 0,
	`requirertsformwindow`  	bit not null default 0,
	`requirertsformifexists`	bit not null default 1,
	`sessiontype`           	int not null default -1,
	`testkey`               	varchar(250) null,
	`_key`                  	varbinary(16) not null, -- constraint `df_client_testmode__key`  default (newid()),
	constraint `pk_client_testmode` primary key clustered(`_key`)
);

create table `client_testprerequisite`  ( 
	`clientname`  	varchar(100) not null,
	`isactive`    	bit not null default 1,
	`prereqtestid`	varchar(255) not null,
	`testid`      	varchar(255) not null,
	`_key`        	varbinary(16) not null, -- constraint `df_client_testprerequisite__key`  default (newid()),
	constraint `pk_client_testprerequisite` primary key clustered(`_key`)
);

create table `client_testproperties`  ( 
	`clientname`             	varchar(100) not null,
	`testid`                 	varchar(255) not null,
	`maxopportunities`       	int null,
	`handscoreproject`       	int null,
	`prefetch`               	int not null default 2,
	`datechanged`            	datetime(3) null, -- constraint `df_client_testproperties_datechanged`  default (getdate()),
	`isprintable`            	bit not null default 0,
	`isselectable`           	bit not null default 1,
	`label`                  	varchar(255) null,
	`printitemtypes`         	varchar(1000) null default '',
	`scorebytds`             	bit not null default 1,
	`batchmodereport`        	bit not null default 0,
	`subjectname`            	varchar(100) null,
	`origin`                 	varchar(50) null, -- constraint `df__client_te__origi__51e506c3`  default (db_name()),
	`source`                 	varchar(50) null, -- constraint `df__client_te__sourc__52d92afc`  default (db_name()),
	`maskitemsbysubject`     	bit not null default 1,
	`initialabilitybysubject`	bit not null default 1,
	`startdate`              	datetime(3) null,
	`enddate`                	datetime(3) null,
	`ftstartdate`            	datetime(3) null,
	`ftenddate`              	datetime(3) null,
	`accommodationfamily`    	varchar(50) null,
	`sortorder`              	int null,
	`rtsformfield`           	varchar(50) not null default 'tds-testform',
	`rtswindowfield`         	varchar(50) not null default 'tds-testwindow',
	`windowtideselectable`   	bit not null default 0,
	`requirertswindow`       	bit not null default 0,
	`reportinginstrument`    	varchar(50) null,
	`tide_id`                	varchar(100) null,
	`forcecomplete`          	bit not null default 1,
	`rtsmodefield`           	varchar(50) not null default 'tds-testmode',
	`modetideselectable`     	bit not null default 0,
	`requirertsmode`         	bit not null default 0,
	`requirertsmodewindow`   	bit not null default 0,
	`deleteunanswereditems`  	bit not null default 0,
	`abilityslope`           	float not null default 1,
	`abilityintercept`       	float not null default 0,
	`validatecompleteness`   	bit not null default 0,
	`gradetext`              	varchar(50) null,
	`initialabilitytestid`      varchar(100) DEFAULT NULL,
	`proctoreligibility`        int not null default 0,
	`category`                  varchar(50) null,
    `msb`                       bit not null default 0,
	constraint `pk_client_testproperties` primary key clustered(`clientname`,`testid`)
);

create table `client_testrtsspecs`  ( 
	`_key`        	varbinary(16) not null, -- default (newid()),
	`clientname`  	varchar(100) not null,
	`testid`      	varchar(150) not null,
	`rtsfieldname`	varchar(50) not null,
	`enables`     	bit not null,
	`disables`    	bit not null,
	`attrvalues`  	text not null,
	constraint `pk_client_testrtsspecs` primary key clustered(`_key`)
);

create table `client_testtool`  ( 
	`clientname`          	varchar(100) not null,
	`type`                	varchar(255) not null,
	`code`                	varchar(255) not null,
	`value`               	varchar(255) not null,
	`isdefault`           	bit not null,
	`allowcombine`        	bit not null,
	`valuedescription`    	varchar(255) null,
	`context`             	varchar(255) not null,
	`sortorder`           	int not null default 0,
	`origin`              	varchar(50) null, -- constraint `df__client_te__origi__579de019`  default (db_name()),
	`source`              	varchar(50) null, -- constraint `df__client_te__sourc__58920452`  default (db_name()),
	`contexttype`         	varchar(50) not null,
	`testmode`            	varchar(25) not null default 'all',
	`equivalentclientcode`	varchar(255) null,
	constraint `pk_client_toolvalues` primary key clustered(`clientname`,`context`,`contexttype`,`type`,`code`)
);

create table `client_testtoolrule`  ( 
	`clientname`	varchar(100) not null,
	`testid`    	varchar(200) not null,
	`tooltype`  	varchar(255) not null,
	`toolcode`  	varchar(255) not null,
	`rule`      	varchar(100) not null,
	`rulevalue` 	varchar(255) not null,
	constraint `pk_clienttoolrule` primary key clustered(`clientname`,`testid`,`toolcode`,`rule`)
);

create table `client_testtooltype`  ( 
	`clientname`             	varchar(100) not null,
	`toolname`               	varchar(255) not null,
	`allowchange`            	bit not null default 1,
	`tideselectable`         	bit null,
	`rtsfieldname`           	varchar(100) null,
	`isrequired`             	bit not null default 0,
	`tideselectablebysubject`	bit not null default 0,
	`isselectable`           	bit not null default 1,
	`isvisible`              	bit not null default 1,
	`studentcontrol`         	bit not null default 1,
	`tooldescription`        	varchar(255) null,
	`sortorder`              	int not null default 0,
	`dateentered`            	datetime(3) not null, 
	`origin`                 	varchar(50) null, 
	`source`                 	varchar(50) null, 
	`contexttype`            	varchar(50) not null,
	`context`                	varchar(255) not null,
	`dependsontooltype`      	varchar(50) null,
	`disableonguestsession`  	bit not null default 0,
	`isfunctional`           	bit not null default 1,
	`testmode`               	varchar(25) not null default 'all',
	`isentrycontrol`           	bit not null default 0,
	constraint `pk_clienttesttool` primary key clustered(`clientname`,`context`,`contexttype`,`toolname`)
);

create table `client_testwindow`  ( 
	`clientname` 	varchar(100) not null,
	`testid`     	varchar(200) not null,
	`window`     	int not null default 1,
	`numopps`    	int not null,
	`startdate`  	datetime(3) null,
	`enddate`    	datetime(3) null,
	`origin`     	varchar(100) null,
	`source`     	varchar(100) null,
	`windowid`   	varchar(50) null,
	`_key`       	varbinary(16) not null,
	`sessiontype`	int not null default -1,
	`sortorder`  	int null default 1,
	constraint `pk_testwindow` primary key clustered(`_key`)
);

create table `client_test_itemconstraint`  ( 
	`clientname`	varchar(100) not null,
	`testid`    	varchar(255) not null,
	`propname`  	varchar(100) not null,
	`propvalue` 	varchar(100) not null,
	`tooltype`  	varchar(255) not null,
	`toolvalue` 	varchar(255) not null,
	`item_in`   	bit not null,
	constraint `pk_itemconstraint` primary key clustered(`clientname`,`testid`,`propname`,`propvalue`,`item_in`)
);

create table `client_test_itemtypes`  ( 
	`clientname`	varchar(100) not null,
	`testid`    	varchar(255) not null,
	`itemtype`  	varchar(25) not null,
	`origin`    	varchar(50) null, -- default (db_name()),
	`source`    	varchar(50) null, -- default (db_name()),
	constraint `pk_itemtypes` primary key clustered(`clientname`,`testid`,`itemtype`)
);

create table `client_testeeattribute`  ( 
	`rtsname`      	varchar(50) not null,
	`tds_id`       	varchar(50) not null,
	`clientname`   	varchar(100) not null,
	`reportname`   	varchar(100) null,
	`type`         	varchar(50) not null,
	`label`        	varchar(50) null,
	`atlogin`      	varchar(25) null,
	`sortorder`    	int null,
	`islatencysite`	bit not null default 0,
	`showonproctor` bit null default 1,
	constraint `pk_testeeattribute` primary key clustered(`clientname`,`tds_id`)
);

create table `client_testeerelationshipattribute`  ( 
	`clientname`      	varchar(50) not null,
	`relationshiptype`	varchar(50) not null,
	`rtsname`         	varchar(50) not null,
	`tds_id`          	varchar(50) not null,
	`label`           	varchar(50) null,
	`atlogin`         	varchar(25) null,
	`sortorder`       	int null,
	`reportname`      	varchar(50) null,
	constraint `pk_client_testeerelationshipattribute` primary key clustered(`clientname`,`tds_id`)
);

create table `client_testformproperties`  ( 
	`clientname`    	varchar(100) not null,
	`_efk_testform` 	varchar(50) not null,
	`startdate`     	datetime(3) null,
	`enddate`       	datetime(3) null,
	`language`      	varchar(25) not null,
	`formid`        	varchar(200) null,
	`testid`        	varchar(150) not null,
	`testkey`       	varchar(250) null,
	`clientformid`  	varchar(25) null,
	`accommodations`	text null,
	constraint `pk_testform` primary key clustered(`clientname`,`_efk_testform`)
);

create table `client_testkey`  ( 
	`clientname`       	varchar(100) not null,
	`testid`           	varchar(255) not null,
	`_efk_adminsubject`	varchar(250) not null,
	`isactive`         	bit not null,
	`_date`            	datetime(3) not null, -- constraint `df__client_te___date__236943a5`  default (getdate()),
	`source`           	varchar(100) not null, -- constraint `df__client_te__sourc__245d67de`  default (db_name()),
	`origin`           	varchar(100) not null, -- constraint `df__client_te__origi__25518c17`  default (db_name()),
	constraint `pk_client_testkey` primary key clustered(`clientname`,`testid`,`_efk_adminsubject`)
);

create table `client_testscorefeatures`  ( 
	`clientname`           	varchar(100) not null,
	`testid`               	varchar(255) not null,
	`measureof`            	varchar(250) not null,
	`measurelabel`         	varchar(200) not null,
	`reporttostudent`      	bit not null default 0,
	`reporttoproctor`      	bit not null default 0,
	`reporttoparticipation`	bit not null default 0,
	`useforability`        	bit not null default 0,
	`reportlabel`          	text null,
	`reportorder`          	int null,
	constraint `pk_scorefeatures` primary key clustered(`clientname`,`testid`,`measureof`,`measurelabel`)
);

create table `client_timelimits`  ( 
	`_key`                   	varbinary(16) not null, -- constraint `df_client_timelimits__key`  default (newid()),
	`_efk_testid`            	varchar(255) null,
	`oppexpire`              	int not null,
	`opprestart`             	int not null,
	`oppdelay`               	int not null,
	`interfacetimeout`       	int null,
	`clientname`             	varchar(100) null,
	`ispracticetest`         	bit not null default 0,
	`refreshvalue`           	int null,
	`tainterfacetimeout`     	int null,
	`tacheckintime`          	int null,
	`datechanged`            	datetime(3) null, -- constraint `df__client_ti__datec__3d2915a8`  default (getdate()),
	`datepublished`          	datetime(3) null,
	`environment`            	varchar(100) null,
	`sessionexpire`          	int not null default 8,
	`requestinterfacetimeout`	int not null default 120,
	`refreshvaluemultiplier` 	int not null default 2,
	constraint `pk_client_timelimits` primary key clustered(`_key`)
);

create table `client_timewindow`  ( 
	`clientname` 	varchar(100) not null,
	`windowid`   	varchar(50) not null,
	`startdate`  	datetime(3) null,
	`enddate`    	datetime(3) null,
	`description`	varchar(200) null,
	constraint `pk_timewindow` primary key clustered(`clientname`,`windowid`)
);

create table `client_tooldependencies`  ( 
	`context`    	varchar(250) not null,
	`contexttype`	varchar(50) not null,
	`iftype`     	varchar(50) not null,
	`ifvalue`    	varchar(255) not null,
	`isdefault`  	bit not null default 0,
	`thentype`   	varchar(50) not null,
	`thenvalue`  	varchar(255) not null,
	`clientname` 	varchar(100) not null,
	`_key`       	varbinary(16) not null, -- constraint `df_client_tooldependencies__key`  default (newid()),
	`testmode`   	varchar(25) not null default 'all',
	constraint `pk_client_tooldependencies` primary key clustered(`_key`)
);

create table `client_toolusage`  ( 
	`clientname` 	varchar(100) not null,
	`testid`     	varchar(150) not null,
	`tooltype`   	varchar(100) not null,
	`recordusage`	bit not null,
	`reportusage`	bit not null,
	constraint `pk_toolusage` primary key clustered(`clientname`,`testid`,`tooltype`)
);

create table `client_voicepack`  ( 
	`os_id`        	varchar(25) not null,
	`voicepackname`	varchar(255) not null,
	`priority`     	int not null,
	`languagecode` 	varchar(25) not null,
	`clientname`   	varchar(100) not null,
	constraint `pk_client_voicepack` primary key clustered(`os_id`,`voicepackname`,`clientname`)
);

create table `geo_clientapplication`  ( 
	`clientname`      	varchar(100) not null,
	`environment`     	varchar(100) not null,
	`url`             	varchar(200) null,
	`appname`         	varchar(100) not null,
	`servicetype`     	varchar(50) not null,
	`_fk_geo_database`	varbinary(16) not null,
	`_key`            	varbinary(16) not null, -- constraint `df_geo_clientapplication__key`  default (newid()),
	`isactive`          bit null,
	constraint `pk__geo_clientapplic` primary key clustered(`_key`)
);

create table `geo_database`  ( 
	`servername`	varchar(100) not null,
	`dbname`    	varchar(100) not null,
	`brokerguid`	varbinary(16) null,
	`_key`      	varbinary(16) not null,
	`tds_id`    	varchar(25) null,
	constraint `pk_geodatabase` primary key clustered(`_key`)
);

create table `rts_role`  ( 
	`clientname` 	varchar(100) not null,
	`rolename`   	varchar(100) not null,
	`description`	varchar(100) null,
	constraint `pk_rts_role` primary key clustered(`clientname`,`rolename`)
);

create table `statuscodes`  ( 
	`usage`      	varchar(50) not null,
	`status`     	varchar(50) not null,
	`description`	varchar(255) null,
	`stage`      	varchar(50) null,
	`_key`       	varbinary(16) not null, -- constraint `df_statuscodes__key`  default (newid()),
	constraint `pk_statuscodes` primary key clustered(`_key`)
);

create table `system_applicationsettings`  ( 
	`clientname`                 	varchar(100) not null,
	`environment`                	varchar(100) not null,
	`appname`                    	varchar(100) not null,
	`property`                   	varchar(100) not null,
	`type`                       	varchar(100) not null,
	`value`                      	varchar(100) not null,
	`_fk_tds_applicationsettings`	varbinary(16) not null,
	`servername`	                varchar(100) not null default '*',
	`siteid`                        varchar(100) not null default '*',

	constraint `pk_systemapplicationsetting` primary key clustered(`clientname`, `servername`, `siteid`,`_fk_tds_applicationsettings`)
); -- UTF8;

create table `system_browserwhitelist`  ( 
	`clientname`              	varchar(100) not null,
	`appname`                 	varchar(100) not null,
	`environment`             	varchar(100) not null,
	`browsername`             	varchar(100) not null,
	`osname`                  	varchar(100) null,
	`hw_arch`                 	varchar(100) null,
	`browsermaxversion`       	float null,
	`action`                  	varchar(150) null,
	`priority`                	int null,
	`osmaxversion`            	float not null,
	`_fk_tds_browserwhitelist`	varbinary(16) not null,
	`browserminversion`       	float null,
	`osminversion`            	float not null,
	`messagekey`              	varchar(255) null,
	`context`                   varchar(255) not null default '*',
	`contexttype`	            varchar(50) not null default 'GLOBAL', -- EF: default added temporarely!!!
	`_key`           	        varbinary(16) not null, -- default (newid()),

	constraint `pk_system_browserwhitelist` primary key (`_key`)	

);

create table `system_networkdiagnostics`  ( 
	`clientname`     	varchar(100) not null,
	`appname`        	varchar(100) not null,
	`testlabel`      	varchar(100) not null,
	`mindataratereqd`	int null,
	`aveitemsize`    	int null,
	`responsetime`   	int null,
	`_key`           	varbinary(16) not null, -- default (newid()),
	constraint `pk_system_networkdiagnostics` primary key clustered(`_key`)
);

create table `tds_application`  ( 
	`_key`       	varbinary(16) not null, -- constraint `df_tds_application__key`  default (newid()),
	`name`       	varchar(50) not null,
	`description`	varchar(255) not null,
	constraint `pk_tds_application` primary key clustered(`_key`)
);

create table `tds_applicationsettings`  ( 
	`_key`         	varbinary(16) not null, -- constraint `df__tds_applic___key__573ded66`  default (newid(,
	`environment`  	varchar(100) not null,
	`appname`      	varchar(100) not null,
	`property`     	varchar(100) not null,
	`type`         	varchar(100) not null,
	`isoperational`	bit not null default 1,
	`value`         varchar(100) null,
	constraint `pk_tds_applicationsettings` primary key clustered(`_key`)
);

create table `tds_browserwhitelist`  ( 
	`_key`             	varbinary(16) not null, -- constraint `df__tds_browse___key__5b0e7e4a`  default (newid()),
	`appname`          	varchar(100) not null,
	`environment`      	varchar(100) not null,
	`browsername`      	varchar(100) not null,
	`osname`           	varchar(100) null,
	`hw_arch`          	varchar(100) null,
	`browserminversion`	float null,
	`browsermaxversion`	float null,
	`action`           	varchar(150) null,
	`priority`         	int null,
	`osminversion`     	float null,
	`isoperational`    	bit null,
	`osmaxversion`     	float null,
	`messagekey`       	varchar(255) null,

	constraint `pk_tds_browserwhitelist` primary key clustered(`_key`)
);

create table `tds_clientaccommodationtype`  ( 
	`toolname`               	varchar(255) not null,
	`clientname`             	varchar(100) not null,
	`allowchange`            	bit not null,
	`tideselectable`         	bit null,
	`rtsfieldname`           	varchar(100) null,
	`isrequired`             	bit not null,
	`tideselectablebysubject`	bit not null,
	constraint `pk_clientacctype` primary key clustered(`toolname`,`clientname`)
);

create table `tds_clientaccommodationvalue`  ( 
	`clientname`      	varchar(100) not null,
	`type`            	varchar(255) not null,
	`code`            	varchar(255) not null,
	`value`           	varchar(255) not null,
	`isdefault`       	bit not null,
	`valuedescription`	varchar(255) null,
	`allowcombine`    	bit not null,
	`allowadd`        	bit not null,
	`sortorder`       	int not null,
	constraint `pk_clientaccvalue` primary key clustered(`clientname`,`code`)
);

create table `tds_configtype`  ( 
	`configtype`     	varchar(25) not null,
	`pagename`       	varchar(50) not null,
	`pagedescription`	varchar(100) null,
	`pagepath`       	varchar(100) not null,
	`_key`           	varbinary(16) not null, -- constraint `df_tds_configtype__key`  default (newid()),
	constraint `pk_tds_configtype` primary key clustered(`_key`)
);

create table `tds_coremessageobject`  ( 
	`context`            	varchar(100) not null,
	`contexttype`        	varchar(50) not null,
	`messageid`          	int not null,
	`ownerapp`           	varchar(100) not null,
	`appkey`             	varchar(255) not null,
	`message`            	text null,
	`paralabels`         	varchar(255) null,
	`_key`               	bigint not null auto_increment,
	`fromclient`         	varchar(100) null,
	`datealtered`        	datetime(3) null,
	`nodes`              	text null,
	`ismessageshowtouser`	bit null,
	constraint `pk_coremsgobject` primary key clustered(`_key`)
);

create table `tds_coremessageuser`  ( 
	`_fk_coremessageobject`	bigint not null,
	`systemid`             	varchar(100) not null,
	constraint `pk_msguser` primary key clustered(`_fk_coremessageobject`,`systemid`)
);

create table `tds_fieldtestpriority`  ( 
	`tds_id`  	varchar(50) not null,
	`priority`	int not null,
	constraint `pk_tds_ftattribute` primary key clustered(`tds_id`,`priority`)
);

create table `tds_role`  ( 
	`rolename`   	varchar(100) not null,
	`description`	varchar(100) null,
	`_key`       	varbinary(16) not null, -- constraint `df_tds_role__key`  default (newid()),
	constraint `pk_tds_role` primary key clustered(`_key`)
);

create table `tds_systemflags`  ( 
	`auditobject`   	varchar(255) not null,
	`description`   	varchar(255) null,
	`ison`          	int not null,
	`ispracticetest`	bit not null,
	`_key`          	varbinary(16) not null, -- constraint `df_tds_systemflags__key`  default (newid(,
	constraint `pk_tds_systemflags` primary key clustered(`_key`)
);

create table `tds_testproperties`  ( 
	`fieldname`  	varchar(100) not null,
	`description`	varchar(255) null,
	`isrequired` 	bit not null,
	`_key`       	varbinary(16) not null, -- constraint `df_tds_testproperties__key`  default (newid(,
	constraint `pk_tds_testproperties` primary key clustered(`_key`)
);

create table `tds_testtool`  ( 
	`type`            	varchar(255) not null,
	`code`            	varchar(255) not null,
	`value`           	varchar(255) not null,
	`isdefault`       	bit not null,
	`valuedescription`	varchar(255) null,
	`allowcombine`    	bit not null,
	`allowadd`        	bit not null,
	`sortorder`       	int not null,
	constraint `pk_testtool` primary key clustered(`code`)
);

create table `tds_testtoolrule`  ( 
	`tooltype`	varchar(255) not null,
	`toolcode`	varchar(255) not null,
	`rule`    	varchar(100) not null,
	constraint `pk_toolrule` primary key clustered(`tooltype`,`toolcode`,`rule`)
);

create table `tds_testtooltype`  ( 
	`toolname`               	varchar(255) not null,
	`allowchange`            	bit not null,
	`tideselectable`         	bit null,
	`rtsfieldname`           	varchar(100) null,
	`isrequired`             	bit not null,
	`tideselectablebysubject`	bit not null,
	constraint `pk_tooltype` primary key clustered(`toolname`)
);

create table `tds_testeeattribute`  ( 
	`rtsname`      	varchar(50) not null,
	`tds_id`       	varchar(50) not null,
	`type`         	varchar(50) not null,
	`label`        	varchar(50) null,
	`atlogin`      	varchar(25) null,
	`sortorder`    	int null,
	`reportname`   	varchar(50) null,
	`islatencysite`	bit not null default 0,
	`showonproctor` bit null default 1,
	constraint `pk_tds_testeeattribute` primary key clustered(`tds_id`)
);

create table `tds_testeerelationshipattribute`  ( 
	`relationshiptype`	varchar(50) not null,
	`rtsname`         	varchar(50) not null,
	`tds_id`          	varchar(50) not null,
	`label`           	varchar(50) null,
	`atlogin`         	varchar(25) null,
	`sortorder`       	int null,
	`reportname`      	varchar(50) null,
	constraint `pk_tds_testeerelationshipattribute` primary key clustered(`tds_id`)
);

create table `_messageid`  ( 
	`_key`	int not null auto_increment,
	`date`	datetime(3) null,
	constraint `pk__messageid` primary key clustered(`_key`)
);

-- identity(10000,1)
alter table `_messageid` auto_increment = 10000;

CREATE TABLE client_itemscoringconfig(
	`clientname` varchar(100) NOT NULL,
	`servername` varchar(255) NOT NULL DEFAULT '*',
	`siteid` varchar(255) NOT NULL DEFAULT '*',
	`context` varchar(255) NOT NULL DEFAULT '*',
	`itemtype` varchar(50) NOT NULL DEFAULT '*',
	`item_in` bit NULL,
	`priority` int NULL,
	`_key` varbinary(16) NOT NULL,
	`serverurl` varchar(255) NOT NULL DEFAULT '*',
	`environment` varchar(50) NULL,
 PRIMARY KEY CLUSTERED (`_key` ASC)
);

create table `systemerrors`  ( 
	`application`         	varchar(50) null default 'database',
	`procname`            	varchar(150) not null,
	`_efk_testee`         	bigint null,
	`_efk_testid`         	varchar(150) null,
	`opportunity`         	int null,
	`errormessage`        	text not null,
	`daterecorded`        	datetime(3) not null, -- constraint `df_systemerrors_daterecorded`  default (getdate()),
	`serverid`            	varchar(50) null, -- constraint `df_systemerrors_serverid`  default (host_name()),
	`ipaddress`           	varchar(50) null,
	`applicationcontextid`	varbinary(16) null,
	`stacktrace`          	text null,
	`_fk_testopportunity` 	varbinary(16) null,
	`clientname`          	varchar(100) null,
	`_fk_session`         	varbinary(16) null
	);
