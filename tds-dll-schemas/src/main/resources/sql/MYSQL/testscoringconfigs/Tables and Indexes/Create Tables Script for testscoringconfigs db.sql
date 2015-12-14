-- create schema if not exists testscoringconfigs default charset = utf8;

use testscoringconfigs;


create table computationrules(
	rulename varchar(255) not null,
	description text null,
 constraint pk_computationrule primary key clustered (
	rulename asc)
) default charset = utf8;

create table measuretypes(
	measureof varchar(150) not null,
	measurement varchar(150) not null,
 constraint pk_measuretypes primary key clustered (
	measureof asc,
	measurement asc)
) default charset = utf8;

create table computationlocations(
	location varchar(50) not null,
 constraint pk_location primary key clustered (
	location asc)
) default charset = utf8;

create table test(
	clientname varchar(100) not null,
	testid varchar(150) not null,
	_efk_subject varchar(100) null,
	_efk_standardspublication varchar(100) null,
	reportinginstrument varchar(25) null,
	dorrbinstrument varchar(50) null,
	tistoscore bit not null default 0,
 constraint pk_test primary key clustered (
	clientname asc,
	testid asc)
) default charset = utf8;

create table client(
	name varchar(100) not null,
 constraint pk_client primary key clustered (
	name asc)
) default charset = utf8;

create table computationruleparameters(
	_key varchar(36) not null,
	computationrule varchar(256) not null,
	parametername varchar(128) not null,
	parameterposition int not null,
	indextype varchar(16) null,
	type varchar(16) not null,
 constraint pk_computationruleparameters primary key clustered (
	_key asc)
) default charset = utf8;

-- drop table testscorefeature
create table testscorefeature(
	_key varchar(36) not null,
	clientname varchar(100) not null,
	testid varchar(150) not null,
	measureof varchar(150) not null,
	measurelabel varchar(150) not null,
	isscaled bit not null,
	computationrule varchar(255) not null,
	computationorder int not null,
	reportingtransform varchar(32) null,
	reportinginstrument varchar(50) null,
	reportinggrade varchar(16) null,
	reportingsubject varchar(5) null,
	reportingscale varchar(255) null,
	reportingmeasuretype varchar(25) null,
 constraint pk_testscorefeature primary key clustered (
	_key asc)
) default charset = utf8;

-- drop table feature_computationlocation;
create table feature_computationlocation(
	_fk_testscorefeature varchar(36) not null,
	location varchar(50) not null,
 constraint pk_featurelocation primary key clustered (
	_fk_testscorefeature asc,
	location asc)
) default charset = utf8;

-- drop table computationruleparametervalue;
create table computationruleparametervalue(
	_fk_testscorefeature varchar(36) not null,
	_fk_parameter varchar(36) not null,
	`index` varchar(256) not null,
	value varchar(256) not null,
 constraint pk_computationruleparametervalue primary key clustered (
	_fk_testscorefeature asc,
	_fk_parameter asc,
	`index`(200) asc)
) default charset = utf8;

create table testgrades(
	clientname varchar(100) not null,
	testid varchar(150) not null,
	reportinggrade varchar(25) not null,
 constraint pk_testgrades primary key clustered (
	clientname asc,
	testid asc,
	reportinggrade asc)
) default charset = utf8; 

create table client_computationrules(
	clientname varchar(100) not null,
	rulename varchar(255) not null,
 constraint pk_clientrules primary key clustered (
	clientname asc,
	rulename asc)
) default charset = utf8;

create table conversiontabledesc(
	_key varchar(36) not null,
	tablename varchar(1000) not null,
	_fk_client varchar(100) not null,
 constraint pk_conversiontabledesc primary key clustered (
	_key asc)
) default charset = utf8;

create table conversiontables(
	tablename varchar(128) not null,
	invalue varchar(150) not null,
	isinvaluestring bit,
	outvalue varchar(150) null,
	isoutvaluestring bit,
	clientname varchar(100) not null
) default charset = utf8;


/****** object:  foreignkey fk_clientrules_client    script date: 06/06/2014 12:09:29 ******/
alter table client_computationrules add constraint fk_clientrules_client foreign key(clientname)
references client (name)
on delete cascade;

/****** object:  foreignkey fk_ruleparm_rule    script date: 06/06/2014 12:09:29 ******/
alter table computationruleparametervalue add constraint fk_ruleparm_rule foreign key(_fk_parameter)
references computationruleparameters (_key)
on delete cascade;

/****** object:  foreignkey fk_ruleparm_scorefeatuer    script date: 06/06/2014 12:09:29 ******/
alter table computationruleparametervalue add constraint fk_ruleparm_scorefeatuer foreign key(_fk_testscorefeature)
references testscorefeature (_key)
on delete cascade;

/****** object:  foreignkey fk_feature_location    script date: 06/06/2014 12:09:29 ******/
alter table feature_computationlocation add  constraint fk_feature_location foreign key(location)
references computationlocations (location)
on delete cascade;

/****** object:  foreignkey fk_location_feature    script date: 06/06/2014 12:09:29 ******/
alter table feature_computationlocation add  constraint fk_location_feature foreign key(_fk_testscorefeature)
references testscorefeature (_key)
on delete cascade;

/****** object:  foreignkey fk_grades_test    script date: 06/06/2014 12:09:29 ******/
alter table testgrades add  constraint fk_grades_test foreign key(clientname, testid)
references test (clientname, testid)
on update cascade
on delete cascade;

/****** object:  foreignkey fk_client    script date: 06/06/2014 12:09:29 ******/
alter table testscorefeature add  constraint fk_client foreign key(clientname)
references client (name)
on delete cascade;

/****** object:  foreignkey fk_scorefeature_rule    script date: 06/06/2014 12:09:29 ******/
alter table testscorefeature add  constraint fk_scorefeature_rule foreign key(computationrule)
references computationrules (rulename);

/****** object:  foreignkey fk_scorefeature_test    script date: 06/06/2014 12:09:29 ******/
alter table testscorefeature add  constraint fk_scorefeature_test foreign key(clientname, testid)
references test (clientname, testid)
on update cascade;

/****** object:  foreignkey fk_scorefeature_test    script date: 06/06/2014 12:09:29 ******/
alter table conversiontabledesc add  constraint conversiontabledesc_client foreign key(_fk_client)
references client (name);


