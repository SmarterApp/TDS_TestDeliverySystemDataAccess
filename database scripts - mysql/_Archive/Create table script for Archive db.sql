create schema if not exists tdscore_proto_archive_ default charset = UTF8;

use tdscore_proto_archive_;

/*
drop table if exists `_dblatencyreports`;
drop table if exists `_dblatencyarchive`;
drop table if exists `_dblatency`;
drop table if exists `testopportunityscores_audit`;
drop table if exists `systemerrorsarchive`;
drop table if exists `systemerrors`;
drop table if exists `systemclient`;
drop table if exists `sessionaudit`;
drop table if exists `serverlatencyarchive`;
drop table if exists `serverlatency`;
drop table if exists `opportunityclient`;
drop table if exists `opportunityaudit`;
drop table if exists `auditaccommodations`;
*/

create table `auditaccommodations`  ( 
	`_fk_testopportunity`	varbinary(16) not null,
	`acccode`            	varchar(50) not null,
	`acctype`            	varchar(50) not null,
	`datealtered`        	datetime(3) not null, -- constraint `df__auditacco__datea__2e11baa1`  default (getdate()),
	`accvalue`           	varchar(250) null,
	`hostname`           	varchar(50) null, -- constraint `df__auditacco__hostn__2f05deda`  default (host_name()),
	`dbname`             	varchar(50) not null -- constraint `df__auditacco__dbnam__4ab81af0`  default (db_name()) 
	)default charset = UTF8;

create table `opportunityaudit`  ( 
	`_fk_testopportunity`	varbinary(16) not null,
	`dateaccessed`       	datetime(3) not null, -- constraint `df__opportuni__datea__4aadf94f`  default (getdate()),
	`accesstype`         	varchar(50) not null,
	`_fk_session`        	varbinary(16) null,
	`isabnormal`         	bit null default 0,
	`hostname`           	nchar(50) null, -- constraint `df__opportuni__hostn__4c9641c1`  default (host_name()),
	`_fk_browser`        	varbinary(16) null,
	`comment`            	text null,
	`actor`              	varchar(100) null,
	`dbname`             	varchar(50) not null, -- constraint `df__opportuni__dbnam__4f7cd00d`  default (db_name()),
	`satellite`          	varchar(200) null 
	)default charset = UTF8;

create table `opportunityclient`  ( 
	`_fk_testopportunity`	varbinary(16) not null,
	`_fk_browser`        	varbinary(16) not null,
	`restart`            	int not null,
	`serverip`           	varchar(16) null,
	`clientip`           	varchar(16) null,
	`proxyip`            	varchar(16) null,
	`useragent`          	varchar(255) null,
	`screenrez`          	varchar(16) null,
	`issecure`           	bit null,
	`_date`              	datetime(3) null, -- constraint `df__opportuni___date__4f72ae6c`  default (getdate()),
	`macaddress`         	varchar(255) null,
	`localip`            	varchar(16) null,
	`texttospeech`       	varchar(100) null,
	`dbname`             	varchar(50) not null -- constraint `df__opportuni__dbnam__52593cb8`  default (db_name()) 
	)default charset = UTF8;

create table `serverlatency`  ( 
	`_fk_testopportunity`	varbinary(16) not null,
	`_fk_session`        	varbinary(16) null,
	`_fk_browser`        	varbinary(16) null,
	`operation`          	varchar(64) not null,
	`itempage`           	int null,
	`_efk_itsitem`       	bigint null,
	`dblatency`          	int null,
	`serverlatency`      	int null,
	`_date`              	datetime(3) null, -- constraint `df__serverlat___date__5ccca98a`  default (getdate()),
	`hostname`           	nchar(64) null, -- constraint `df__serverlat__hostn__5dc0cdc3`  default (host_name()),
	`itemtype`           	varchar(25) null,
	`pagelist`           	varchar(200) null,
	`itemlist`           	varchar(1000) null,
	`clientname`         	varchar(100) null,
	`dbname`             	varchar(50) not null -- constraint `df__serverlat__dbnam__5fb337d6`  default (db_name()) 
	)default charset = UTF8;

create table `serverlatencyarchive`  ( 
	`client`             	varchar(100) null,
	`_fk_session`        	varbinary(16) null,
	`_fk_browser`        	varbinary(16) null,
	`operation`          	varchar(64) not null,
	`itempage`           	int null,
	`_efk_itsitem`       	bigint null,
	`dblatency`          	int null,
	`serverlatency`      	int null,
	`_date`              	datetime(3) null,
	`hostname`           	nchar(64) null,
	`itemtype`           	varchar(25) null,
	`pagelist`           	varchar(200) null,
	`itemlist`           	varchar(1000) null,
	`environment`        	varchar(50) null,
	`source`             	varchar(50) null, -- constraint `df__serverlat__sourc__276edeb3`  default (db_name()),
	`_fk_testopportunity`	varbinary(16) null,
	`datearchived`       	datetime(3) not null, -- constraint `df__serverlat__dater__37a5467c`  default (getdate()),
	`dbname`             	varchar(50) not null -- constraint `df__serverlat__dbnam__60a75c0f`  default (db_name()) 
	)default charset = UTF8;

create table `sessionaudit`  ( 
	`_fk_session` 	varbinary(16) not null,
	`dateaccessed`	datetime(3) not null,
	`accesstype`  	varchar(50) not null,
	`hostname`    	nchar(50) null, -- constraint `df_sessionaudit_hostname`  default (host_name()),
	`browserkey`  	varbinary(16) not null,
	`dbname`      	varchar(50) not null  -- constraint `df__sessionau__dbnam__5be2a6f2`  default (db_name()) 
	)default charset = UTF8;

create table `systemclient`  ( 
	`clientname`  	varchar(100) not null,
	`application` 	varchar(50) not null,
	`userid`      	varchar(50) not null,
	`clientip`    	varchar(16) null,
	`proxyip`     	varchar(16) null,
	`useragent`   	varchar(255) null,
	`daterecorded`	datetime(3) not null, -- constraint `df_systemclient_daterecorded`  default (getdate()),
	`dbname`      	varchar(50) not null  -- constraint `df__systemcli__dbnam__5aee82b9`  default (db_name()) 
	)default charset = UTF8;

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
	`_fk_session`         	varbinary(16) null,
	`dbname`              	varchar(50) not null -- constraint `df__systemerr__dbnam__619b8048`  default (db_name()) 
	)default charset = UTF8;

create table `systemerrorsarchive`  ( 
	`client`              	varchar(50) null,
	`application`         	varchar(50) null,
	`procname`            	varchar(150) not null,
	`_efk_testee`         	bigint null,
	`_efk_testid`         	varchar(150) null,
	`opportunity`         	int null,
	`errormessage`        	text not null,
	`daterecorded`        	datetime(3) not null,
	`serverid`            	varchar(50) null,
	`ipaddress`           	varchar(50) null,
	`applicationcontextid`	varbinary(16) null,
	`stacktrace`          	text null,
	`environment`         	varchar(50) null,
	`source`              	varchar(50) null, -- constraint `df__systemerr__sourc__29572725`  default (db_name()),
	`datearchived`        	datetime(3) not null, -- constraint `df__systemerr__datea__38996ab5`  default (getdate()),
	`dbname`              	varchar(50) not null, -- constraint `df__systemerr__dbnam__628fa481`  default (db_name()),
	`_fk_testopportunity` 	varbinary(16) null,
	`_fk_session`         	varbinary(16) null 
	)default charset = UTF8;

create table `testopportunityscores_audit`  ( 
	`_fk_testopportunity`	varbinary(16) not null,
	`measurelabel`       	varchar(100) not null,
	`value`              	varchar(100) null,
	`standarderror`      	float null,
	`measureof`          	varchar(150) not null,
	`isofficial`         	bit null,
	`_date`              	datetime(3) null, -- constraint `df__testoppor___date__141cde74`  default (getdate()),
	`subject`            	varchar(100) null,
	`dbname`             	varchar(50) not null -- constraint `df__testoppor__dbnam__5812160e`  default (db_name()) 
	)default charset = UTF8;

create table `_dblatency`  ( 
	`starttime`          	datetime(3) null,
	`difftime`           	datetime(3) null,
	`duration`           	bigint null,
	`procname`           	varchar(80) null,
	`userkey`            	bigint null,
	`host`               	varchar(50) null, -- constraint `df___dblatency__host__475c8b58`  default (host_name()),
	`n`                  	int null,
	`_fk_testopportunity`	varbinary(16) null,
	`clientname`         	varchar(100) null,
	`_fk_session`        	varbinary(16) null,
	`dbname`             	varchar(50) not null, -- constraint `df___dblatenc__dbnam__5cd6cb2b`  default (db_name()),
	`comment`            	varchar(500) null 
	)default charset = UTF8;

create table `_dblatencyarchive`  ( 
	`client`             	varchar(50) null,
	`starttime`          	datetime(3) null,
	`difftime`           	datetime(3) null,
	`duration`           	bigint null,
	`procname`           	varchar(80) null,
	`userkey`            	bigint null,
	`host`               	varchar(50) null,
	`n`                  	int null,
	`environment`        	varchar(50) null,
	`source`             	varchar(50) null, -- constraint `df___dblatenc__sourc__24927208`  default (db_name()),
	`datearchived`       	datetime(3) not null, -- constraint `df___dblatenc__dater__36b12243`  default (getdate()),
	`dbname`             	varchar(50) not null, -- constraint `df___dblatenc__dbnam__5dcaef64`  default (db_name()),
	`_fk_session`        	varbinary(16) null,
	`_fk_testopportunity`	varbinary(16) null 
	)default charset = UTF8;

create table `_dblatencyreports`  ( 
	`client`      	varchar(50) null,
	`procname`    	varchar(100) null,
	`n`           	int null,
	`minduration` 	int null,
	`maxduration` 	int null,
	`meanduration`	int null,
	`starttime`   	datetime(3) null,
	`endtime`     	datetime(3) null,
	`environment` 	varchar(50) null,
	`source`      	varchar(50) null, -- constraint `df___dblatenc__sourc__25869641`  default (db_name()),
	`daterecorded`	datetime(3) not null, -- constraint `df___dblatenc__dater__35bcfe0a`  default (getdate()),
	`dbname`      	varchar(50) not null -- constraint `df___dblatenc__dbnam__5ebf139d`  default (db_name()) 
	)default charset = UTF8;


create index `ix_auditaccommodations`
	on `auditaccommodations`(`dbname`, `_fk_testopportunity`);

create index `ix_opportunityaudit`
	on `opportunityaudit`(`dbname`, `_fk_testopportunity`);

create index `ix_opportunityclient`
	on `opportunityclient`(`dbname`, `_fk_testopportunity`);

create index `ix_serverlatency1`
	on `serverlatency`(`_fk_testopportunity`, `_date`);

create index `ix_serverlatencyarchive`
	on `serverlatencyarchive`(`operation`, `client`);

create index `ix_sessionaudit`
	on `sessionaudit`(`dbname`, `_fk_session`);

create index `ix_systemclient`
	on `systemclient`(`dbname`, `clientname`, `application`);

create index `ix_errors_db`
	on `systemerrors`(`dbname`, `daterecorded`);

create index `ix_sessionerrors`
	on `systemerrors`(`_fk_session`);

create index `ix_syserrsoppkey`
	on `systemerrors`(`_fk_testopportunity`);

create index `ix_systemerrors_2`
	on `systemerrors`(`application`, `procname`);

create index `ix_errorsarchiveclient`
	on `systemerrorsarchive`(`client`, `application`);

create index `ix_systemerrorsarchive`
	on `systemerrorsarchive`(`procname`, `daterecorded` desc);

create index `ix_oppscoresaudit`
	on `testopportunityscores_audit`(`dbname`, `_fk_testopportunity`);

create index `ix_dblatency`
	on `_dblatency`(`procname`);

create index `ix_dblatency_db`
	on `_dblatency`(`dbname`, `starttime`);

create index `ix_dblatencyarchive`
	on `_dblatencyarchive`(`procname`, `client`);

create index `ix_dblatencyarchivesource`
	on `_dblatencyarchive`(`dbname`);

create index `ix_dblatencyarchivedate`
	on `_dblatencyarchive`(`starttime`);

create index `ix_dblatencyreports`
	on `_dblatencyreports`(`procname`, `client`);

