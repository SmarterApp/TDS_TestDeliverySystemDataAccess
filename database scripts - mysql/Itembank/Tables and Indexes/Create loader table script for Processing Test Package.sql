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
--  , subjectkey	  varchar(100)
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

drop table if exists loader_testpoolproperties;
create table loader_testpoolproperties (
	_fk_package	varchar(350)
  ,	propname	varchar(50)	
  , propvalue	varchar(128)
  , proplabel	varchar(150)
  , itemcount	int
);

/*
		<itempool>
			<passage filename="stim-143-35.xml">
				<identifier uniqueid="143-35" name="35" version="4491"/>
			</passage>
			:
			:
		</itempool>
*/
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


/*
-- example xml structure:
			<testitem filename="item-143-201.xml" itemtype="MC">
				<identifier uniqueid="143-201" name="2L-PC-NA6471" version="4491"/>
				<bpref>(Oregon)Oregon-ELPA-6-8-winter-2013-2014</bpref>
				<bpref>ELPA-ELPA-Listening</bpref>
				<bpref>ELPA-ELPA-Listening|MC</bpref>
				<poolproperty property="Language" value="ENU" label="English"/>
				<poolproperty property="TDSPoolFilter" value="Listening" label="TDSPoolFilter = Listening"/>
				<itemscoredimension measuremodel="IRT3pl" scorepoints="1" weight="1.000000000000000e+000">
					<itemscoreparameter measurementparameter="a" value="1.000000000000000e+000"/>
					<itemscoreparameter measurementparameter="b" value="-2.310000000000000e+000"/>
					<itemscoreparameter measurementparameter="c" value="0.000000000000000e+000"/>
				</itemscoredimension>
			</testitem>

can this structure occur?
				<itemscoredimension measuremodel="IRT3pl" scorepoints="1" weight="1.000000000000000e+000">
					<itemscoreparameter measurementparameter="a" value="1.000000000000000e+000">
						<property name = "", value = "", label = ""/> ---------------> ????
					</itemscoreparameter>	
				</itemscoredimension>

*/
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

-- 	<bpref>(Oregon)Oregon-ELPA-6-8-winter-2013-2014</bpref>
-- 	<bpref>ELPA-ELPA-Listening</bpref>
-- 	<bpref>ELPA-ELPA-Listening|MC</bpref>
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


/*
-- example xml structure:
	<testform length="216">
			<identifier uniqueid="(Minnesota)GRAD-Paper-Mathematics-11-Fall-2013-2014:Default-ENU" name="(Minnesota)GRAD-Paper-Mathematics-11-Fall-2013-2014:Default-ENU" version="4170"/>
			<property name="language" value="ENU" label="English"/>
			<poolproperty property="--ITEMTYPE--" value="MC" label="MC" itemcount="216"/>
			<poolproperty property="Language" value="ENU" label="English" itemcount="216"/>
			<formpartition>
				<identifier uniqueid="157-218" name="FormA::GRADM DE::2012-2015" version="4170"/>
				<itemgroup formposition="1" maxitems="ALL" maxresponses="0">
					<identifier uniqueid="157-218:I-157-8914" name="157-218:I-157-8914" version="4248"/>
					<groupitem itemid="157-8914" formposition="1" groupposition="1" adminrequired="false" responserequired="false" isactive="true" isfieldtest="false" blockid="A"/>
				</itemgroup>
				:
			</formpartition>
			:
	</testform>

*/
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

drop table if exists loader_segment;
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
);

drop table if exists loader_segmentblueprint;
create table loader_segmentblueprint (
	_fk_package			varchar(350)
  ,	segmentid 			varchar(250)
  ,	segmentbpelementid	varchar(150)
  , minopitems			int
  , maxopitems			int
);

drop table if exists loader_segmentitemselectionproperties;
create table loader_segmentitemselectionproperties (
	_fk_package		varchar(350)
  ,	segmentid 		varchar(250)
  , itemselectorid  varchar(500)
  ,	bpelementid		varchar(250)
  , propname		varchar(200)
  , propvalue		varchar(100)
  , proplabel		varchar(500)
);

drop table if exists loader_segmentpool;
create table loader_segmentpool (
	_fk_package		varchar(350)
  ,	segmentid 		varchar(250)
  , itemgroupid		varchar(100)
  , itemgroupname	varchar(100)
  , maxitems		varchar(30)
  , maxresponses	varchar(30)
  , version			int 
);

drop table if exists loader_segmentform;
create table loader_segmentform (
	_fk_package		varchar(350)
  ,	segmentid 		varchar(250)
  , formpartitionid varchar(100)
);

drop table if exists loader_segmentpoolpassageref;
create table loader_segmentpoolpassageref (
	_fk_package		varchar(350)
  ,	segmentid 		varchar(250)
  , itemgroupid		varchar(100)
  , passageid		varchar(100)

);

drop table if exists loader_segmentpoolgroupitem;
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
);