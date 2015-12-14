drop table if exists loader_errors;
create table loader_errors (
	testkey 			varchar(250) not null
  , testpackageversion 	varchar(20)
  , severity 			varchar(100) not null
  ,	errormsg 			text not null
);

drop table if exists loader_setofitemstrands;
create table loader_setofitemstrands (
	_fk_package	  	 varchar(350)
  , _fk_item 		 varchar(150)
  , _fk_strand 		 varchar(150)
  , _fk_adminsubject varchar(250)
  , version 		 bigint
);

drop table if exists loader_measurementparameter;
create table loader_measurementparameter(
	modelnum 		float not null
  ,	parmnum 		float null
  ,	parmname 		nvarchar(255) null
  ,	parmdescription nvarchar(255) null
  ,	modelname 		nvarchar(255) not null
);

drop table if exists loader_testpackage;
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
);

drop table if exists loader_testpackageproperties;
create table loader_testpackageproperties (
	_fk_package	  varchar(350)
  , propname  	  varchar(200)
  , propvalue 	  varchar(200)
  , proplabel 	  varchar(200)
);

drop table if exists loader_testblueprint;
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
);

drop table if exists loader_testpassages;
create table loader_testpassages (
	_fk_package	varchar(350)
  ,	filename	varchar(50)
  , filepath	varchar(50)	
  , passageid	varchar(150)
  ,	passagename	varchar(100)
  , passagevalue varchar(200)
  ,	version		int
);

drop table if exists loader_testitem;
create table loader_testitem (
	_fk_package		varchar(350)
  ,	filename		varchar(200)
  , filepath		varchar(200)	
  , itemtype		varchar(50) 
  , testitemid		varchar(150)	
  , testitemname	varchar(80)
  , version			int	
);

drop table if exists loader_testitemrefs;
create table loader_testitemrefs (
	_fk_package	varchar(350)
  ,	testitemid	varchar(150)
  , reftype 	varchar(50)	
  , ref			varchar(150)
  , refcategory varchar(250)
  , treelevel	int
);

drop table if exists loader_testitempoolproperties;
create table loader_testitempoolproperties (
	_fk_package	varchar(350)
  ,	testitemid	varchar(150)
  , propname	varchar(50)
  , propvalue	varchar(128)
  , proplabel	varchar(150)
);

drop table if exists loader_itemscoredimension;
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
);

drop table if exists loader_itemscoredimensionproperties;
create table loader_itemscoredimensionproperties (
	_fk_package		varchar(350)
  ,	testitemid		varchar(150)
  , dimensionname   varchar(200)
  , propname		varchar(200)
  , propvalue		varchar(200)
);

drop table if exists loader_testforms;
create table loader_testforms (
	_fk_package		varchar(350)
  ,	testformid		varchar(200)
  , testformname	varchar(200)
  , testformlength	int
  , version			int
);

drop table if exists loader_testformproperties;
create table loader_testformproperties (
	_fk_package	varchar(350)
  ,	testformid	varchar(200)
  , isPool		bit
  , propname	varchar(200)
  , proplabel	varchar(200)
  , propvalue	varchar(200)
  , itemcount	int
);

drop table if exists loader_testformpartition;
create table loader_testformpartition (
	_fk_package		varchar(350)
  ,	testformid		varchar(200)
  , formpartitionid	varchar(200)
  , formpartitionname varchar(250)
  , version 		int
);

drop table if exists loader_testformitemgroup;
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
);

drop table if exists loader_testformgroupitems;
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
);

drop table if exists loader_computationrule;
create table loader_computationrule (
	_fk_package 		varchar(350)
  , bpelementid   		varchar(250)
  , computationorder 	int
  , ruleid				varchar(250)
  , rulename 			varchar(200)
  , rulelabel			varchar(200)
  , version				float
);

drop table if exists loader_computationruleparameter;
create table loader_computationruleparameter (
	_fk_package 		varchar(350)
  , ruleid   			varchar(250)
  , parametertype		varchar(16)
  , position			int
  , parametername		varchar(128)
  , version				float
  , parameterid			varchar(250)
  , propname			varchar(200)
  , propvalue			varchar(200)
);

drop table if exists loader_computationruleparametervalue;
create table loader_computationruleparametervalue (
	_fk_package 		varchar(350)
  , ruleid	   			varchar(250)
  , parameterid			varchar(250)
  , `index`				varchar(256)
  , `value`				varchar(256)
);


drop table if exists loader_conversiontable;
create table loader_conversiontable (
	_fk_package 		varchar(350)
  , conversiontableid  	varchar(250)
  , conversiontablename varchar(150)
  , version				float
  , measureid			varchar(150)
  , purpose				varchar(150)
);

drop table if exists loader_conversiontuple;
create table loader_conversiontuple (
	_fk_package 		varchar(350)
  , conversiontableid  	varchar(250)
  , invalue 			varchar(150)
  , outvalue			varchar(150)
);

drop table if exists loader_performancelevels;
create table loader_performancelevels (
	_fk_package 	varchar(350)
  ,	bpelementid 	varchar(250)
  , plevel 			int 
  , scaledlo 		float
  , scaledhi 		float
);
