create table `aa_itemcl`  ( 
	`_fk_adminsubject`	varchar(250) not null,
	`_fk_item`        	varchar(25) not null,
	`contentlevel`    	varchar(100) not null,
	constraint `pk_itemcl` primary key clustered(`_fk_adminsubject`,`_fk_item`,`contentlevel`)
) default charset = UTF8;

create table `affinitygroup`  ( 
	`_fk_adminsubject` 	varchar(250) not null,
	`groupid`          	varchar(100) not null,
	`description`      	varchar(1000) null,
	`minitems`         	int not null,
	`maxitems`         	int not null,
	`isstrictmax`      	bit not null default 0,
	`weight`           	float not null default 1.0,
	`selectioncriteria`	varchar(2000) null,
	`loadconfig`       	bigint null,
	`updateconfig`     	bigint null,
	`startability`      float DEFAULT NULL,
    `startinfo`         float DEFAULT NULL,
    `abilityweight`     float DEFAULT NULL,
    `precisiontarget`   float DEFAULT NULL,
    `precisiontargetmetweight`    float DEFAULT NULL,
    `precisiontargetnotmetweight` float DEFAULT NULL,
	constraint `pk_affinitygroup` primary key clustered(`_fk_adminsubject`,`groupid`)
) default charset = UTF8;

create table `affinitygroupitem`  ( 
	`_fk_adminsubject`	varchar(250) not null,
	`groupid`         	varchar(100) not null,
	`_fk_item`        	varchar(100) not null,
	constraint `pk_affinityitem` primary key clustered(`_fk_adminsubject`,`groupid`,`_fk_item`)
) default charset = UTF8;

create table `alloweditemprops`  ( 
	`propertyname`	varchar(50) not null,
	constraint `pk_allowedprops` primary key clustered(`propertyname`)
) default charset = UTF8;

create table `configsloaded`  ( 
	`configid`          	int not null,
	`bankkey`           	bigint not null,
	`clientname`        	varchar(200) not null,
	`contract`          	varchar(200) not null,
	`_date`             	datetime(3) not null, -- default (getdate(,
	`_error`            	text null,
	`tdsconfigs_updated`	datetime(3) null,
	`_key`              	varbinary(16) not null, -- constraint `df__configsloa___key__302f0d3d`  default (newid(,
	constraint `pk_configsloaded` primary key clustered(`_key`)
) default charset = UTF8;

create table `importitemcohorts`  ( 
	`bankkey`      	varchar(150) null,
	`blueprint key`	varchar(150) null,
	`testname`     	varchar(150) null,
	`itemid`       	varchar(150) null,
	`cohort number`	varchar(150) null 
) default charset = UTF8;

create table `importtestcohorts`  ( 
	`blueprint key`	varchar(150) null,
	`testname`     	varchar(150) null,
	`cohort number`	varchar(150) null,
	`cohort ratio` 	varchar(150) null 
) default charset = UTF8;

create table `itemmeasurementparameter`  ( 
	`_fk_itemscoredimension`  	varbinary(16) not null,
	`_fk_measurementparameter`	int not null,
	`parmvalue`               	float null,
	constraint `pk_itemmeasureparm` primary key clustered(`_fk_itemscoredimension`,`_fk_measurementparameter`)
) default charset = UTF8;

create table `itemscoredimension`  ( 
	`dimension`           	varchar(255) not null default '',
	`recoderule`          	varchar(255) null,
	`responsebankscale`   	varchar(150) null,
	`scorepoints`         	int not null,
	`surrogateitemid`     	varchar(150) null,
	`weight`              	float not null,
	`_key`                	varbinary(16) not null,
	`_efk_surrogateitskey`	bigint null,
	`_fk_adminsubject`    	varchar(250) not null,
	`_fk_item`            	varchar(150) not null,
	`_fk_measurementmodel`	int not null,
	constraint `pk_itemscoredim` primary key clustered(`_key`)
) default charset = UTF8;

create table `tblitemselectionparm`  ( 
	`_fk_adminsubject` varchar(250) not null,
	`bpelementid`      varchar(200) default null,
	`name`             varchar(100) not null,
	`value`            varchar(200) not null,
	`label`            varchar(200) default null,
	`_key`             varbinary(16) not null,
 	constraint `pk_tblitemselectionparm` primary key clustered(`_key`) 
) default charset = UTF8;

/*
create table `loader_accommodations`  ( 
	`type`            	varchar(255) not null,
	`code`            	varchar(255) not null,
	`value`           	varchar(255) not null,
	`isdefault`       	bit not null,
	`multivalued`     	bit not null,
	`allowchange`     	bit not null,
	`valuedescription`	varchar(255) null,
	`owner`           	varchar(50) not null,
	constraint `pk_loaderaccommodations` primary key clustered(`code`,`owner`)
) default charset = UTF8;

create table `loader_affinitygroups`  ( 
	`testname`   	varchar(100) not null,
	`groupid`    	varchar(100) not null,
	`_testkey`   	varchar(150) null,
	`description`	varchar(1000) null,
	`minitems`   	int not null,
	`maxitems`   	int not null,
	`isstrictmax`	bit not null default 0,
	`weight`     	float not null default 1.0,
	`isfieldtest`	bit not null default 0,
	constraint `pk_loaderaffinitygroups` primary key clustered(`testname`,`groupid`)
) default charset = UTF8;

create table `loader_affinityitems`  ( 
	`testname`	varchar(100) not null,
	`groupid` 	varchar(100) not null,
	`_testkey`	varchar(150) null,
	`itemid`  	varchar(25) not null,
	`_itemkey`	varchar(100) null,
	constraint `pk_loaderaffinityitems` primary key clustered(`testname`,`groupid`,`itemid`)
) default charset = UTF8;

create table `loader_contentlevels`  ( 
	`adminclkey`    	varchar(255) null,
	`blueprint`     	int null,
	`clkey`         	varchar(255) null,
	`contentlevelid`	varchar(255) not null,
	`description`   	text null,
	`grade`         	varchar(255) null,
	`level`         	int null,
	`maxnum`        	int null,
	`minnum`        	int null,
	`parentcl`      	varchar(255) null,
	`poolcount`     	int null,
	`strandid`      	varchar(255) null,
	`subject`       	varchar(255) null,
	`subjectkey`    	varchar(255) null,
	`testname`      	varchar(255) not null,
	`testkey`       	varchar(255) null,
	`isstrictmax`   	bit null default 0,
	`bpweight`      	float null default 1,
	constraint `pk_loadercl` primary key clustered(`testname`,`contentlevelid`)
) default charset = UTF8;

create table `loader_errors`  ( 
	`configid`	bigint not null,
	`severity`	varchar(100) not null,
	`test`    	varchar(250) null,
	`error`   	text not null 
	) default charset = UTF8;

create table `loader_formitems`  ( 
	`testname`    	varchar(255) not null,
	`formkey`     	bigint not null,
	`formid`      	varchar(255) null,
	`itemid`      	bigint not null,
	`sequence`    	int null,
	`_fk_testform`	varchar(100) null,
	`_itemkey`    	varchar(200) null,
	`_testkey`    	varchar(200) null,
	`isactive`    	bit not null default 1,
	constraint `pk_loaderformitems` primary key clustered(`testname`,`formkey`,`itemid`)
) default charset = UTF8;

create table `loader_forms`  ( 
	`formid`    	varchar(255) null,
	`formkey`   	bigint not null,
	`language`  	varchar(255) null,
	`testname`  	varchar(255) not null,
	`enddate`   	datetime(3) null,
	`startdate` 	datetime(3) null,
	`_key`      	varchar(255) null,
	`testkey`   	varchar(255) null,
	`iteration` 	int null,
	`originalid`	varchar(255) null,
	`cohort`    	varchar(20) not null default 'default',
	constraint `pk_loaderforms` primary key clustered(`testname`,`formkey`)
) default charset = UTF8;

create table `loader_itemclancestors`  ( 
	`contentlevel`	varchar(255) not null,
	`ancestor`    	varchar(255) not null,
	constraint `pk_clancestor` primary key clustered(`contentlevel`,`ancestor`)
) default charset = UTF8;

create table `loader_itemproperties`  ( 
	`code`       	varchar(255) not null,
	`description`	varchar(255) null,
	`itemid`     	varchar(255) not null,
	`property`   	varchar(255) not null default 'language',
	`_itemkey`   	varchar(200) null,
	`_testkey`   	varchar(250) null,
	constraint `pk_loaderitemprops` primary key clustered(`itemid`,`property`,`code`)
) default charset = UTF8;

create table `loader_itemscoredimension`  ( 
	`dimension`        	varchar(255) null default '',
	`itskey`           	varchar(255) not null,
	`measurementmodel` 	varchar(255) null,
	`recoderule`       	varchar(255) null,
	`responsebankscale`	varchar(255) null,
	`scorepoints`      	int null,
	`surrogateitemid`  	varchar(255) null,
	`surrogateitemkey` 	varchar(255) null,
	`testname`         	varchar(255) not null,
	`weight`           	float null,
	`_key`             	varbinary(16) not null, -- constraint `df__loader_ite___key__14f1071c`  default (newid()),
	`p0`               	float null,
	`p1`               	float null,
	`p2`               	float null,
	`p3`               	float null,
	`p4`               	float null,
	`p5`               	float null,
	`p6`               	float null,
	`p7`               	float null,
	`p8`               	float null,
	`p9`               	float null,
	`_itemkey`         	varchar(100) null,
	`_testkey`         	varchar(200) null,
	`p10`              	float null,
	constraint `pk_loaderitemdim` primary key clustered(`_key`)
) default charset = UTF8;

create table `loader_itembank`  ( 
	`bankkey`   	bigint not null,
	`clientname`	varchar(255) null,
	`contract`  	varchar(255) null,
	`schoolyear`	varchar(255) null,
	`season`    	varchar(255) null,
	`configid`  	int null,
	constraint `pk_loaderitembank` primary key clustered(`bankkey`)
) default charset = UTF8;

create table `loader_items`  ( 
	`answerkey`       	varchar(255) null,
	`block`           	varchar(255) null,
	`clkey`           	varchar(255) null,
	`clientid`        	varchar(255) null,
	`contentlevel`    	varchar(255) null,
	`difficulty`      	float null,
	`grade`           	varchar(255) null,
	`itskey`          	varchar(255) not null,
	`isactive`        	bit null,
	`isfieldtest`     	bit null,
	`itemtype`        	varchar(255) null,
	`scorepoints`     	int null,
	`setsequence`     	int null,
	`stimulusid`      	varchar(255) null,
	`strandid`        	varchar(255) null,
	`strandkey`       	varchar(255) null,
	`subject`         	varchar(255) null,
	`testname`        	varchar(255) not null,
	`_itemkey`        	varchar(200) null,
	`_testkey`        	varchar(200) null,
	`isrequired`      	bit null,
	`isprintable`     	bit null,
	`responsemimetype`	varchar(255) null,
	constraint `pk_loaderitems` primary key clustered(`testname`,`itskey`)
) default charset = UTF8;

create table `loader_logicaltests`  ( 
	`logicalid`	varchar(150) null,
	`testkey`  	varchar(250) not null,
	`its_id`   	varchar(150) not null 
) default charset = UTF8;

create table `loader_measurementparameter`  ( 
	`modelnum`       	float not null,
	`parmnum`        	float null,
	`parmname`       	varchar(255) null,
	`parmdescription`	varchar(255) null,
	`modelname`      	varchar(255) not null 
) default charset = UTF8;

create table `loader_proficiencylevels`  ( 
	`testid`  	varchar(255) not null,
	`plevel`  	int not null,
	`label`   	varchar(255) null,
	`low`     	float null,
	`high`    	float null,
	`scaledlo`	float null,
	`scaledhi`	float null,
	`_testkey`	varchar(255) null,
	constraint `pk_loaderproflevels` primary key clustered(`testid`,`plevel`)
) default charset = UTF8;

create table `loader_rulesrelaxvalidation`  ( 
	`_fk_ruleid`	varchar(255) not null,
	`startdate` 	varchar(255) null,
	`enddate`   	varchar(255) null 
) default charset = UTF8;

create table `loader_stimuli`  ( 
	`clientid`    	varchar(255) null,
	`stimulusid`  	varchar(255) not null,
	`numresponses`	varchar(255) null,
	`_stimuluskey`	varchar(100) null,
	`maxitems`    	varchar(25) null,
	`testname`    	varchar(255) not null,
	`_testkey`    	varchar(200) null,
	constraint `pk_loaderstimuli` primary key clustered(`testname`,`stimulusid`)
) default charset = UTF8;

create table `loader_stimulusproperties`  ( 
	`code`       	varchar(255) not null,
	`description`	varchar(255) null,
	`property`   	varchar(255) not null default 'language',
	`stimulusid` 	varchar(255) not null,
	constraint `pk_loaderstimprops` primary key clustered(`stimulusid`,`property`,`code`)
) default charset = UTF8;

create table `loader_strands`  ( 
	`adminstrandkey`  	varchar(255) null,
	`cutability`      	float null,
	`description`     	text null,
	`grade`           	varchar(255) null,
	`lambdamultiplier`	float null,
	`mlo`             	varchar(255) null,
	`maxitems`        	int null,
	`minitems`        	int null,
	`startability`    	float null,
	`startinformation`	float null,
	`strandid`        	varchar(255) not null,
	`strandkey`       	varchar(255) null,
	`subject`         	varchar(255) not null,
	`subjectkey`      	varchar(255) null,
	`testname`        	varchar(255) not null,
	`testkey`         	varchar(255) null,
	`isstrictmax`     	bit null default 0,
	`bpweight`        	float null default 1,
	constraint `pk_loaderstrands` primary key clustered(`testname`,`subject`,`strandid`)
) default charset = UTF8;

create table `loader_tests`  ( 
	`ftendpos`             	varchar(255) null,
	`ftmaxitems`           	varchar(255) null,
	`ftminitems`           	varchar(255) null,
	`ftstartpos`           	varchar(255) null,
	`grade`                	varchar(255) null,
	`intercept`            	varchar(255) null,
	`lambdamultiplier`     	varchar(255) null,
	`maxitems`             	varchar(255) null,
	`minitems`             	varchar(255) null,
	`setofgrades`          	varchar(255) null,
	`slope`                	varchar(255) null,
	`startability`         	varchar(255) null,
	`startinformation`     	varchar(255) null,
	`subject`              	varchar(255) null,
	`subjectkey`           	varchar(255) null,
	`testname`             	varchar(255) not null,
	`selectionalgorithm`   	varchar(255) null,
	`testkey`              	varchar(255) null,
	`cset1size`            	int null,
	`cset2random`          	int null,
	`cset2initialrandom`   	int null,
	`blueprintweight`      	float null,
	`virtualtest`          	varchar(200) null,
	`segmentposition`      	int null,
	`computesegmentability`	bit null,
	`virtualtestkey`       	varchar(200) null,
	`itemweight`           	float null,
	`abilityoffset`        	float null,
	`cset1order`           	varchar(50) null default 'ability',
	constraint `pk_loadertests` primary key clustered(`testname`)
) default charset = UTF8;
*/
create table `measurementmodel`  ( 
	`modelnumber`	int not null,
	`modelname`  	varchar(50) not null,
	constraint `pk_measurementmodel` primary key clustered(`modelnumber`)
) default charset = UTF8;

create table `measurementparameter`  ( 
	`_fk_measurementmodel`	int not null,
	`parmnum`             	int not null,
	`parmname`            	varchar(50) not null,
	`parmdescription`     	varchar(50) null,
	constraint `pk_measurementparameter` primary key clustered(`_fk_measurementmodel`,`parmnum`)
) default charset = UTF8;

create table `performancelevels`  ( 
	`_fk_content`	varchar(250) not null,
	`plevel`     	int not null,
	`thetalo`    	float not null,
	`thetahi`    	float not null,
	`scaledlo`   	float null,
	`scaledhi`   	float null,
	constraint `pk_performancelevels` primary key clustered(`_fk_content`,`plevel`)
) default charset = UTF8;

create table `projects`  ( 
	`_key`       	int not null,
	`_fk_client` 	bigint null,
	`description`	varchar(256) not null,
	constraint `pk_projects` primary key clustered(`_key`)
) default charset = UTF8;

create table `setoftestgrades`  ( 
	`testid`           	varchar(100) not null,
	`grade`            	varchar(25) not null,
	`requireenrollment`	bit not null default 0,
	`_fk_adminsubject` 	varchar(250) not null,
	`enrolledsubject`  	varchar(100) null,
	`_key`             	varbinary(16) not null, -- default (newid()),
	constraint `pk_setoftestgrades` primary key clustered(`_key`)
) default charset = UTF8;

create table `testcohort`  ( 
	`_fk_adminsubject`	varchar(200) not null,
	`cohort`          	int not null,
	`itemratio`       	float not null default 1,
	constraint `pk_testcohort` primary key clustered(`_fk_adminsubject`,`cohort`)
) default charset = UTF8;

create table `testform`  ( 
	`_fk_adminsubject`	varchar(250) not null,
	`_efk_itsbank`    	bigint not null,
	`_efk_itskey`     	bigint not null,
	`formid`          	varchar(150) null,
	`language`        	varchar(150) null,
	`_key`            	varchar(100) not null,
	`itsid`           	varchar(150) null,
	`iteration`       	int not null default 0,
	`loadconfig`      	bigint null,
	`updateconfig`    	bigint null,
	`cohort`          	varchar(20) not null default 'default',
	constraint `pk_testform` primary key clustered(`_key`)
) default charset = UTF8;

create table `testformitem`  ( 
	`_fk_item`        	varchar(150) not null,
	`_efk_itsformkey` 	bigint not null,
	`formposition`    	int not null,
	`_fk_adminsubject`	varchar(250) not null,
	`_fk_testform`    	varchar(100) null,
	`isactive`        	bit not null default 1,
	constraint `pk_testformitem` primary key clustered(`_fk_item`,`formposition`,`_fk_adminsubject`, `_fk_testform`)
) default charset = UTF8;

create table `_synonyms`  ( 
	`dbname`	varchar(200) not null,
	`_date` 	datetime(3) not null,
	`prefix`	varchar(200) not null,
	constraint `pk_synonyms` primary key clustered(`prefix`)
) default charset = UTF8;

create table `_sys_formtestitems`  ( 
	`testid` 	varchar(100) not null,
	`bankkey`	bigint not null,
	`itemkey`	bigint not null,
	constraint `pk_sysformitems` primary key clustered(`testid`,`bankkey`,`itemkey`)
) default charset = UTF8;

create table `_testupdate`  ( 
	`_fk_adminsubject`	varchar(250) not null,
	`configid`        	bigint not null,
	`_date`           	datetime(3) not null -- default (getdate()) 
	) default charset = UTF8;

create table `_dblatency`  ( 
	`duration` 	int not null,
	`endtime`  	datetime(3) not null,
	`procname` 	varchar(50) not null,
	`starttime`	datetime(3) not null 
	) default charset = UTF8;

create table `tbladminstimulus`  ( 
	`_fk_stimulus`    	varchar(100) not null,
	`_fk_adminsubject`	varchar(250) not null,
	`numitemsrequired`	int not null default -1,
	`maxitems`        	int not null default -1,
	`bpweight`        	float not null default 1.0,
	`loadconfig`      	bigint null,
	`updateconfig`    	bigint null,
	`groupid`         	varchar(50) null,
	constraint `pk_adminstimulus` primary key clustered(`_fk_adminsubject`,`_fk_stimulus`)
) default charset = UTF8;

create table `tbladminstrand`  ( 
	`_fk_adminsubject`	varchar(250) not null,
	`_fk_strand`      	varchar(150) not null,
	`minitems`        	int null,
	`maxitems`        	int null,
	`adaptivecut`     	float null,
	`startability`    	float null,
	`startinfo`       	float null,
	`scalar`          	float null,
	`_fk_proflevels`  	bigint null,
	`loaderid`        	varchar(150) null,
	`synthmin`        	int null,
	`synthmax`        	int null,
	`inheritmin`      	int null,
	`inheritmax`      	int null,
	`inheritratio`    	float null default -1,
	`numitems`        	int null,
	`loadmin`         	int null,
	`loadmax`         	int null,
	`_key`            	varchar(255) not null,
	`isstrictmax`     	bit not null default 0,
	`bpweight`        	float not null default 1,
	`loadconfig`      	bigint null,
	`updateconfig`    	bigint null,
	`precisiontarget`   float DEFAULT NULL,
    `precisiontargetmetweight`    float DEFAULT NULL,
    `precisiontargetnotmetweight` float DEFAULT NULL,
    `abilityweight`     float DEFAULT NULL,
	constraint `pk_adminstrand` primary key clustered(`_key`)
) default charset = UTF8;

create table `tblalternatetest`  ( 
	`_key`            	bigint not null AUTO_INCREMENT,
	`_fk_adminsubject`	bigint not null,
	`formlang`        	char(128) not null,
	`filepath`        	char(255) not null,
	constraint `pk_tblalternatetest` primary key clustered(`_key`)
) default charset = UTF8;

create table `tblclient`  ( 
	`_key`       	bigint not null AUTO_INCREMENT,
	`name`       	varchar(255) not null,
	`description`	varchar(255) null,
	`homepath`   	varchar(250) null,
	constraint `pk_tblclient` primary key clustered(`_key`)
) default charset = UTF8;

create table `tblitem`  ( 
	`_efk_itembank`  	bigint not null,
	`_efk_item`      	bigint not null auto_increment,
	`itemtype`       	varchar(50) null,
	`answer`         	varchar(50) null,
	`scorepoint`     	int null,
	`filepath`       	varchar(50) null,
	`filename`       	varchar(50) null,
	`version`        	varchar(150) null,
	`datelastupdated`	datetime(3) null,
	`itemid`         	varchar(80) null,
	`_key`           	varchar(150) not null,
	`contentsize`    	int null,
	`loadconfig`     	bigint null,
	`updateconfig`   	bigint null,
	primary key (`_key`),
	key `ix_efk_item` (`_efk_item`)
) default charset = UTF8;

create table `tblitembank`  ( 
	`_fk_client`   	bigint not null,
	`homepath`     	varchar(255) null,
	`itempath`     	varchar(50) null,
	`stimulipath`  	varchar(50) null,
	`name`         	varchar(255) null,
	`_efk_itembank`	bigint not null,
	`_key`         	bigint not null,
	`contract`     	varchar(255) null,
	constraint `pk_tblitembank_1` primary key clustered(`_key`)
) default charset = UTF8;

create table `tblitemprops`  ( 
	`_fk_item`        	varchar(150) not null,
	`propname`        	varchar(50) not null,
	`propvalue`       	varchar(128) not null,
	`propdescription` 	varchar(150) null,
	`_fk_adminsubject`	varchar(250) not null,
	`isactive`        	bit not null default 1,
	constraint `pk_itemprops` primary key clustered(`_fk_adminsubject`,`_fk_item`,`propname`,`propvalue`)
) default charset = UTF8;

create table `tblsetofadminitems`  ( 
	`_fk_item`        	varchar(150) not null,
	`_fk_adminsubject`	varchar(250) not null,
	`groupid`         	varchar(100) null,
	`itemposition`    	int null,
	`isfieldtest`     	bit null,
	`isactive`        	bit null,
	`irt_b`           	varchar(150) null,
	`blockid`         	varchar(10) null,
	`ftstartdate`     	datetime(3) null,
	`ftenddate`       	datetime(3) null,
	`isrequired`      	bit not null default 0,
	`isprintable`     	bit null default 0,
	`_fk_testadmin`   	varchar(150) null,
	`responsemimetype`	varchar(255) not null default 'text/plain',
	`testcohort`      	int not null default 1,
	`_fk_strand`      	varchar(150) null,
	`loadconfig`      	bigint null,
	`updateconfig`    	bigint null,
	`groupkey`        	varchar(100) null,
	`strandname`      	varchar(150) null,
	`irt_a`           	float null,
	`irt_c`           	float null,
	`irt_model`       	varchar(100) null,
	`bvector`         	varchar(200) null,
	`notforscoring`   	bit not null default 0,
	`clstring`        	text null,
	`irt2_a`        	varchar(400) null,
	`irt2_b`        	varchar(800) null,
	`irt2_c`        	varchar(400) null,
	`irt2_model`      	varchar(800) null,
	`ftweight`          float not null default 1.0,
	constraint `pk_adminitems` primary key clustered(`_fk_adminsubject`,`_fk_item`)
) default charset = UTF8;

create table `tblsetofadminsubjects`  ( 
	`_key`                   	varchar(250) not null,
	`_fk_testadmin`          	varchar(150) not null,
	`_fk_subject`            	varchar(150) not null,
	`testid`                 	varchar(255) null,
	`startability`           	float null,
	`startinfo`              	float null,
	`minitems`               	int null,
	`maxitems`               	int null,
	`slope`                  	float null,
	`intercept`              	float null,
	`ftstartpos`             	int null,
	`ftendpos`               	int null,
	`ftminitems`             	int null,
	`ftmaxitems`             	int null,
	`sampletarget`           	float null,
	`selectionalgorithm`     	varchar(50) null,
	`formselection`          	varchar(100) null,
	`blueprintweight`        	float not null default 5.0,
	`abilityweight`          	float not null default 1.0,
	`cset1size`              	int not null default 20,
	`cset2random`            	int not null default 1,
	`cset2initialrandom`     	int not null default 5,
	`virtualtest`            	varchar(200) null,
	`testposition`           	int null,
	`issegmented`            	bit not null default 0,
	`computeabilityestimates`	bit not null default 1,
	`loadconfig`             	bigint null,
	`updateconfig`           	bigint null,
	`itemweight`             	float null default 5.0,
	`abilityoffset`          	float null default 0.0,
	`contract`               	varchar(100) null,
	`its_id`                 	varchar(200) null,
	`cset1order`             	varchar(50) not null default 'ability',
	`refreshminutes`         	int null,
	`rcabilityweight`           float NOT NULL DEFAULT '1',
    `precisiontarget`           float DEFAULT NULL,
    `precisiontargetmetweight`  float NOT NULL DEFAULT '1',
    `precisiontargetnotmetweight` float NOT NULL DEFAULT '1',
    `adaptivecut`               float DEFAULT NULL,
    `toocloseses`               float DEFAULT NULL,
    `terminationoverallinfo`    bit not null default 0,
    `terminationrcinfo`         bit not null default 0,
    `terminationmincount`       bit not null default 0,
    `terminationtooclose`       bit not null default 0,
    `terminationflagsand`       bit not null default 0,
    `bpmetricfunction`          varchar(25) not null default 'bp2',
    `_efk_blueprint`            bigint default NULL,
    `initialabilitytestID`      varchar(100) default NULL,
    `testtype`                  varchar(30) default NULL,
	constraint `pk_tblsetofadminsubjects` primary key clustered(`_key`)
) default charset = UTF8;

create table `tblsetofitemstimuli`  ( 
	`_fk_item`        	varchar(150) not null,
	`_fk_stimulus`    	varchar(150) not null,
	`_fk_adminsubject`	varchar(250) not null,
	`loadconfig`      	bigint null,
	constraint `pk_itemstimuli` primary key clustered(`_fk_item`,`_fk_stimulus`,`_fk_adminsubject`)
) default charset = UTF8;

create table `tblsetofitemstrands`  ( 
	`_fk_item`        	varchar(150) not null,
	`_fk_strand`      	varchar(150) not null,
	`_fk_adminsubject`	varchar(250) not null,
	`loadconfig`      	bigint null,
	constraint `pk_itemstrands` primary key clustered(`_fk_item`,`_fk_strand`,`_fk_adminsubject`)
) default charset = UTF8;

create table `tblstimulus`  ( 
	`_efk_itembank`  	bigint not null,
	`_efk_itskey`    	bigint not null auto_increment,
	`clientid`       	varchar(100) null,
	`filepath`       	varchar(50) null,
	`filename`       	varchar(50) null,
	`version`        	varchar(150) null,
	`datelastupdated`	datetime(3) null,
	`_key`           	varchar(150) not null,
	`contentsize`    	int null,
	`loadconfig`     	bigint null,
	`updateconfig`   	bigint null,
	constraint `pk_tblstimulus_1` primary key clustered(`_key`),
	key `ix_tblstimulus_efk_itskey` (`_efk_itskey`)
) default charset = UTF8;

create table `tblstrand`  ( 
	`_fk_subject`    	varchar(150) not null,
	`name`           	varchar(150) not null,
	`scorereportname`	varchar(55) null,
	`_fk_parent`     	varchar(150) null,
	`_key`           	varchar(150) not null,
	`description`    	varchar(512) null,
	`_fk_client`     	bigint null,
	`treelevel`      	int null,
	`loadconfig`     	bigint null,
	`updateconfig`   	bigint null,
	constraint `pk_tblstrand_1` primary key clustered(`_key`)
) default charset = UTF8;

create table `tblsubject`  ( 
	`name`        	varchar(100) not null,
	`grade`       	varchar(64) null,
	`_key`        	varchar(150) not null,
	`_fk_client`  	bigint null,
	`loadconfig`  	bigint null,
	`updateconfig`	bigint null,
	constraint `pk_tblsubject_1` primary key clustered(`_key`)
) default charset = UTF8;

create table `tbltestadmin`  ( 
	`schoolyear`  	varchar(25) not null,
	`season`      	varchar(10) not null,
	`description` 	varchar(255) null,
	`_key`        	varchar(150) not null,
	`_fk_client`  	bigint null,
	`loadconfig`  	bigint null,
	`updateconfig`	bigint null,
	constraint `pk_tbltestadmin_1` primary key clustered(`_key`)
) default charset = UTF8;

create table `tblitemgroup` (
   `_fk_adminsubject`     varchar(250) not null,
   `groupid`              varchar(50) not null,
   `numitemsrequired`     int not null default -1,
   `maxitems`             int not null default -1,
   `bpweight`             float not null default 1.0,
   `ftweight`             float null,
   `loadconfig`           bigint null,
   `updateconfig`         bigint null,
   `ftitemcount`          int null,
   `opitemcount`          int null,
   `ftweightsum`          float null
) default charset = UTF8;



/********************Loader Tables to support processing of TestPackage*************************/

create table loader_errors (
	testkey 			varchar(250) not null
  , testpackageversion 	varchar(20)
  , severity 			varchar(100) not null
  ,	errormsg 			text not null
) default charset = UTF8;

create table loader_setofitemstrands (
	_fk_package	  	 varchar(350)
  , _fk_item 		 varchar(150)
  , _fk_strand 		 varchar(150)
  , _fk_adminsubject varchar(250)
  , version 		 bigint
) default charset = UTF8;

create table loader_measurementparameter(
	modelnum 		float not null
  ,	parmnum 		float null
  ,	parmname 		nvarchar(255) null
  ,	parmdescription nvarchar(255) null
  ,	modelname 		nvarchar(255) not null
) default charset = UTF8;

create table loader_testpackage (
	packagekey		varchar(350)
  ,	purpose			varchar(100)
  , publisher		varchar(255)
  , publishdate		date
  , packageversion	varchar(20)
  , testkey			varchar(250)
  , testname		varchar(200)
  , testlabel		varchar(200)
  , testversion		int
  , `year` 		 	varchar(50)
  ,	season	 		varchar(50)
  , clientkey   	int
  , subjectkey		varchar(100)
  , subjectname		varchar(100)
  , testadmin 		varchar(250)
  , _efk_itembank   bigint
) default charset = UTF8;

create table loader_testpackageproperties (
	_fk_package	  varchar(350)
  , propname  	  varchar(200)
  , propvalue 	  varchar(200)
  , proplabel 	  varchar(200)
) default charset = UTF8;

create table loader_testblueprint (
	_fk_package	  	varchar(350)
  ,	elementtype 	varchar(100)
  , bpelementid		varchar(250)	
  , bpelementname  	varchar(255)
  , bpelmentlabel 	varchar(200) 
  , minopitems		int
  , maxopitems		int
  , minftitems		int
  , maxftitems		int
  , opitemcount		int
  , ftitemcount		int
  , parentid		varchar(150)
  , version 		int
  , treelevel		int
) default charset = UTF8;

create table loader_testpoolproperties (
	_fk_package	varchar(350)
  ,	propname	varchar(50)	
  , propvalue	varchar(128)
  , proplabel	varchar(150)
  , itemcount	int
) default charset = UTF8;

create table loader_testpassages (
	_fk_package	varchar(350)
  ,	filename	varchar(50)
  , filepath	varchar(50)	
  , passageid	varchar(150)
  ,	passagename	varchar(100)
  , passagevalue varchar(200)
  ,	version		int
) default charset = UTF8;

create table loader_testitem (
	_fk_package		varchar(350)
  ,	filename		varchar(200)
  , filepath		varchar(200)	
  , itemtype		varchar(50) 
  , testitemid		varchar(150)	
  , testitemname	varchar(80)
  , version			int	
) default charset = UTF8;

create table loader_testitemrefs (
	_fk_package	varchar(350)
  ,	testitemid	varchar(150)
  , reftype 	varchar(50)	
  , ref			varchar(150)
  , refcategory varchar(250)
  , treelevel	int
) default charset = UTF8;

create table loader_testitempoolproperties (
	_fk_package	varchar(350)
  ,	testitemid	varchar(150)
  , propname	varchar(50)
  , propvalue	varchar(128)
  , proplabel	varchar(150)
) default charset = UTF8;

create table loader_itemscoredimension (
	_fk_package		varchar(350)
  ,	testitemid		varchar(150)
  , measuremodel 	varchar(100)
  , measuremodelkey int
  , dimensionname   varchar(200)
  , scorepoints 	int
  , weight 			float
  , measurementparam varchar(50)  
  , measurementparamnum int	
  , measurementvalue float
) default charset = UTF8;

create table loader_itemscoredimensionproperties (
	_fk_package		varchar(350)
  ,	testitemid		varchar(150)
  , dimensionname   varchar(200)
  , propname		varchar(200)
  , propvalue		varchar(200)
) default charset = UTF8;

create table loader_testforms (
	_fk_package		varchar(350)
  ,	testformid		varchar(200)
  , testformname	varchar(200)
  , testformlength	int
  , version			int
) default charset = UTF8;

create table loader_testformproperties (
	_fk_package	varchar(350)
  ,	testformid	varchar(200)
  , isPool		bit
  , propname	varchar(200)
  , proplabel	varchar(200)
  , propvalue	varchar(200)
  , itemcount	int
) default charset = UTF8;


create table loader_testformpartition (
	_fk_package		varchar(350)
  ,	testformid		varchar(200)
  , formpartitionid	varchar(200)
  , formpartitionname varchar(250)
  , version 		int
) default charset = UTF8;

create table loader_testformitemgroup (
	_fk_package			 varchar(350)
  ,	testformid			 varchar(200) 
  , formpartitionid		 varchar(200)
  , formitemgroupid 	 varchar(200)
  , formitemgroupname	 varchar(200)
  , formitemgrouplabel	 varchar(200)
  , version 			 int
  , formposition		 int
  , maxitems			 varchar(30)
  , maxresponses		 varchar(30)
  , passageid			 varchar(100)
) default charset = UTF8;

create table loader_testformgroupitems (
	_fk_package		varchar(350)
  ,	testformid		varchar(200) 
  , formitemgroupid varchar(200)
  , itemid			varchar(150)
  , formposition 	int
  , groupposition 	int
  , adminrequired	bit
  , responserequired bit
  , isactive		bit
  , isfieldtest		bit
  , blockid			varchar(10)
) default charset = UTF8;

create table loader_segment (
	_fk_package			varchar(350)
  ,	segmentid 			varchar(250)
  , position			int
  , itemselection 		varchar(100)
  , itemselectortype    varchar(100)
  , itemselectorid  	varchar(200)
  , itemselectorname  	varchar(200)
  , itemselectorlabel 	varchar(200)
  , version 			float	
) default charset = UTF8;

create table loader_segmentblueprint (
	_fk_package			varchar(350)
  ,	segmentid 			varchar(250)
  ,	segmentbpelementid	varchar(150)
  , minopitems			int
  , maxopitems			int
) default charset = UTF8;

create table loader_segmentitemselectionproperties (
	_fk_package		varchar(350)
  ,	segmentid 		varchar(250)
  , itemselectorid  varchar(500)
  ,	bpelementid		varchar(250)
  , propname		varchar(200)
  , propvalue		varchar(100)
  , proplabel		varchar(500)
) default charset = UTF8;

create table loader_segmentpool (
	_fk_package		varchar(350)
  ,	segmentid 		varchar(250)
  , itemgroupid		varchar(100)
  , itemgroupname	varchar(100)
  , maxitems		varchar(30)
  , maxresponses	varchar(30)
  , version			int 
) default charset = UTF8;

create table loader_segmentform (
	_fk_package		varchar(350)
  ,	segmentid 		varchar(250)
  , formpartitionid varchar(100)
) default charset = UTF8;

create table loader_segmentpoolpassageref (
	_fk_package		varchar(350)
  ,	segmentid 		varchar(250)
  , itemgroupid		varchar(100)
  , passageid		varchar(100)

) default charset = UTF8;

create table loader_segmentpoolgroupitem (
	_fk_package		varchar(350)
  ,	segmentid 		varchar(250)
  , itemgroupid		varchar(100)
  , groupitemid		varchar(150)
  , groupposition	int
  , adminrequired	bit
  , responserequired bit
  , isfieldtest		bit
  , isactive		bit
  , blockid			varchar(10)
) default charset = UTF8;