/*
create schema oregon_rts_2012;

drop table if exists `tbluserroles`;
drop table if exists `tbluser`;
drop table if exists `tblrole`;
drop table if exists `tblrelationshiptype`;
drop table if exists `tblrelationship`;
drop table if exists `tbllistentry`;
drop table if exists `tbllist`;
drop table if exists `tblentitytype`;
drop table if exists `tblentityattribute`;
drop table if exists `tblentity`;
drop table if exists `tblattributevalue`;
drop table if exists `eligibletest`;
*/

create table `eligibletest`  ( 
	`_fk_entity`	bigint not null,
	`testid`    	varchar(100) not null,
	`startdate` 	datetime(3) not null,
	`enddate`   	datetime(3) not null,
	`_key`      	bigint not null auto_increment,
	`clientname`	varchar(100) null,
	constraint `pk_eligibletest` primary key clustered(`_key`)
)default charset = UTF8;

create table `tblattributevalue`  ( 
	`_key`               	bigint not null auto_increment,
	`_fk_entity`         	bigint not null,
	`_fk_entityattribute`	bigint not null,
	`startdate`          	datetime(3) not null,
	`attrvalue`          	varchar(700) null,
	`enddate`            	datetime(3) null,
	`_fk_user`           	bigint null,
	`tdsacc`             	text null,
	constraint `pk_tblattributevalue` primary key nonclustered(`_key`)
)default charset = UTF8;

create table `tblentity`  ( 
	`_key`          	bigint not null auto_increment,
	`_fk_entitytype`	bigint not null,
	constraint `pk_tblentity` primary key clustered(`_key`)
)default charset = UTF8;

create table `tblentityattribute`  ( 
	`_key`                    	bigint not null,
	`_fk_entitytype`          	bigint not null,
	`fieldname`               	varchar(35) not null,
	`label`                   	varchar(300) not null,
	`datatype`                	varchar(10) not null,
	`length`                  	smallint null,
	`decimalprecision`        	smallint null,
	`minvalue`                	varchar(50) null,
	`maxvalue`                	varchar(50) null,
	`formatstring`            	varchar(255) null,
	`formaterrormsg`          	varchar(255) null,
	`_fk_list`                	bigint null,
	`isrequired`              	bit not null,
	`issearchable`            	bit not null,
	`isviewable`              	bit not null,
	`iseditable`              	bit not null,
	`detaillevel`             	smallint not null,
	`sortorder`               	smallint not null,
	`defaultvalue`            	varchar(50) null,
	`hasmultipleactiverecords`	bit null,
	`iscasesensitive`         	bit null,
	constraint `pk_tblentityattribute` primary key clustered(`_key`)
)default charset = UTF8;

create table `tblentitytype`  ( 
	`_key`       	bigint not null auto_increment,
	`entitytype` 	varchar(50) not null,
	`label`      	varchar(50) not null,
	`description`	varchar(255) not null,
	constraint `pk_tblentitytype` primary key clustered(`_key`)
)default charset = UTF8;

create table `tbllist`  ( 
	`_key`       	bigint not null,
	`listname`   	varchar(50) not null,
	`description`	varchar(255) not null,
	constraint `pk_tbllist` primary key clustered(`_key`)
)default charset = UTF8;

create table `tbllistentry`  ( 
	`_key`            	bigint not null auto_increment,
	`_fk_list`        	bigint not null,
	`codedvalue`      	varchar(50) not null,
	`shortdescription`	varchar(255) not null,
	`longdescription` 	varchar(255) not null,
	`_fk_childlist`   	bigint null,
	`iscombinable`    	bit not null,
	constraint `pk_tbllistentry` primary key clustered(`_key`)
)default charset = UTF8;

create table `tblrelationship`  ( 
	`_key`                	bigint not null auto_increment,
	`_fk_entity_a`        	bigint not null,
	`_fk_entity_b`        	bigint not null,
	`startdate`           	datetime(3) not null,
	`_fk_relationshiptype`	bigint not null,
	`enddate`             	datetime(3) null,
	`_fk_user`            	bigint null,
	constraint `pk_tblrelationship` primary key nonclustered(`_key`)
)default charset = UTF8;

create table `tblrelationshiptype`  ( 
	`_key`            	bigint not null auto_increment,
	`relationshiptype`	varchar(25) not null,
	`_fk_entitytype_a`	bigint not null,
	`_fk_entitytype_b`	bigint not null,
	`aistob`          	varchar(25) not null,
	`bistoa`          	varchar(25) not null,
	`cardinality`     	varchar(12) not null,
	`priority`        	int not null,
	`sortorder`       	int not null,
	`isbtoarequired`  	bit not null,
	`issearchable`    	bit null,
	constraint `pk_tblrelationshiptype` primary key clustered(`_key`)
)default charset = UTF8;

create table `tblrole`  ( 
	`_key`          	bigint not null,
	`role`          	varchar(25) not null,
	`description`   	varchar(50) not null,
	`_fk_entitytype`	bigint null,
	constraint `pk_tblrole` primary key clustered(`_key`)
)default charset = UTF8;

create table `tbluser`  ( 
	`_key`           	bigint not null auto_increment,
	`username`       	varchar(50) not null,
	`fullname`       	varchar(75) not null,
	`password`       	varchar(800) null,
	`hasacknowledged`	bit not null,
	`active`         	bit not null,
	`_fk_entity`     	bigint null,
	constraint `pk_tbluser` primary key clustered(`_key`)
)default charset = UTF8;

create table `tbluserroles`  ( 
	`_fk_user`  	bigint not null,
	`_fk_role`  	bigint not null,
	`sortorder` 	smallint not null,
	`_fk_entity`	bigint not null,
	constraint `pk_tbluserroles` primary key clustered(`_fk_user`,`_fk_role`,`_fk_entity`)
)default charset = UTF8;

create index `ix_eligibletests`
	on `eligibletest`(`clientname`, `_fk_entity`, `startdate`);

create index `idx02_tblattributevalue`
	on `tblattributevalue`(`_fk_entityattribute`, `attrvalue`);

create index `ix_entityattribute_clus`
	on `tblattributevalue`(`_fk_entity`, `_fk_entityattribute`, `startdate`);

create index `ix_fieldname_tblentityattribute`
	on `tblentityattribute`(`fieldname`, `_fk_entitytype`);

create index `ix_entitytype_tblentitytype`
	on `tblentitytype`(`entitytype`);

create index `ix_listentry_list`
	on `tbllistentry`(`_fk_list`);

create index `idx01_tblrelationship`
	on `tblrelationship`(`_fk_entity_b`, `_fk_relationshiptype`);

create index `idx02_tblrelationship`
	on `tblrelationship`(`_fk_entity_b`, `_fk_relationshiptype`, `startdate`);

create index `ix_reltype_tblrelationshiptype`
	on `tblrelationshiptype`(`relationshiptype`);

create index `ix_role_tblrole`
	on `tblrole`(`role`);

create index `ix_username_tbluser`
	on `tbluser`(`username`);
