DELIMITER $$

drop procedure if exists load_adminsubjects $$

create procedure load_adminsubjects (
/*
Description: load test admins loader_* tables

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/23/2014		Sai V. 			Original Code.
*/
	v_testpackagekey varchar(350)
)
begin
    
	declare v_issegmented bit;

	-- create temp table to host data during data gathering and processing
	drop temporary table if exists tmp_tblsetofadminsubjects;
	create temporary table tmp_tblsetofadminsubjects (
		_key varchar(250)
	  , _fk_testadmin varchar(150)
	  , _fk_subject varchar(150)
	  , testid varchar(255)
	  , startability float
	  , startinfo float 
	  , minitems int 
	  , maxitems int
	  , slope int
	  , intercept int
	  , ftstartpos int
	  , ftendpos int
	  , ftminitems int
	  , ftmaxitems int
	  , selectionalgorithm varchar(50)
	  , blueprintweight float
	  , cset1size int
	  , cset2random int
	  , cset2initialrandom int
	  , virtualtest varchar(200)
	  , testposition int
	  , issegmented bit
	  , itemweight float
	  , abilityoffset float
	  , cset1order varchar(50)
	  , version bigint
	  , contract varchar(100)
	  , testtype varchar(60)
	);


	-- first, figure out if the test is segmented or non-segmented
	-- if the bpelementid for test and segment are the same, then the test is non-segmented
	set v_issegmented = (select case when cnt = 0 then 1 else 0 end
						 from (
							select count(*) cnt
							  from loader_testblueprint tbp1
							  join loader_testblueprint tbp2 on tbp1.bpelementid = tbp2.bpelementid
															and tbp1._fk_package = tbp2._fk_package
							 where tbp1.elementtype = 'test'
							   and tbp2.elementtype = 'segment'
							   and tbp2._fk_package = v_testpackagekey
							) t
						);
	
	-- load test data for both segmented and non-segmented (excluding the test segments)
	-- elementtype = test
	insert into tmp_tblsetofadminsubjects (_key, _fk_testadmin, _fk_subject, testid, minitems, maxitems, ftminitems, ftmaxitems, selectionalgorithm, issegmented, version, contract, testtype)
	select bpelementid
		 , testadmin
	     , subjectkey
		 , bpelementname
		 , minopitems
		 , maxopitems
		 , minftitems
		 , maxftitems
		 , 'virtual'
		 , v_issegmented
		 , version
		 , tp.publisher
		 , (select propvalue from loader_testpackageproperties tpp where tpp.propname = 'type' and tpp._fk_package = tp.packagekey)
	  from loader_testblueprint bp
	  join loader_testpackage tp on tp.packagekey = bp._fk_package
	 where elementtype = 'test'
	   and tp.packagekey = v_testpackagekey;

	-- need to load all test segments only when the test is segmented
	if v_issegmented = 1 then
		insert into tmp_tblsetofadminsubjects (_key, _fk_testadmin, _fk_subject, testid, minitems, maxitems, ftminitems, ftmaxitems, testposition, selectionalgorithm, virtualtest, issegmented, version, contract)
		select bpelementid
			 , testadmin
			 , subjectkey
			 , bpelementname
			 , minopitems
			 , maxopitems
			 , minftitems
			 , maxftitems
			 , position
			 , itemselection 
			 , (select bpelementid from loader_testblueprint where elementtype = 'test' and _fk_package = tp.packagekey) as virtualtest
			 , 0
			 , tbp.version
			 , tp.publisher
		  from loader_testblueprint tbp
		  join loader_segment s on s._fk_package = tbp._fk_package
							   and s.segmentid = tbp.bpelementid
		  join loader_testpackage tp on tp.packagekey = tbp._fk_package
		 where elementtype = 'segment'
	       and tp.packagekey = v_testpackagekey;

	end if;

	-- extract test/segment properties from loader_segmentitemselectionproperties
	drop temporary table if exists tmp_testproperties;
	create temporary table tmp_testproperties as (
		select sisp.bpelementid
			 , max(if(sisp.propname = 'ftstartpos', sisp.propvalue, null)) as ftstartpos
			 , max(if(sisp.propname = 'ftendpos', sisp.propvalue, null)) as ftendpos
			 , max(if(sisp.propname = 'bpweight', sisp.propvalue, null)) as bpweight
			 , max(if(sisp.propname = 'startability', sisp.propvalue, null)) as startability
			 , max(if(sisp.propname = 'startinfo', sisp.propvalue, null)) as startinfo
			 , max(if(sisp.propname = 'cset1size', sisp.propvalue, null)) as cset1size
			 , max(if(sisp.propname = 'cset1order', sisp.propvalue, null)) as cset1order
			 , max(if(sisp.propname = 'cset2random', sisp.propvalue, null)) as cset2random
			 , max(if(sisp.propname = 'cset2initialrandom', sisp.propvalue, null)) as cset2initialrandom
			 , max(if(sisp.propname = 'abilityoffset', sisp.propvalue, null)) as abilityoffset
			 , max(if(sisp.propname = 'itemweight', sisp.propvalue, null)) as itemweight
		  from tmp_tblsetofadminsubjects tmp
		  join loader_segmentitemselectionproperties sisp on sisp.segmentid = tmp._key and tmp._key = sisp.bpelementid
		 where sisp._fk_package = v_testpackagekey
		group by sisp.bpelementid
	);

	update tmp_tblsetofadminsubjects tmp
	  join tmp_testproperties tp on tp.bpelementid = tmp._key
	   set tmp.ftstartpos 	   = tp.ftstartpos 
		 , tmp.ftendpos 	   = tp.ftendpos
		 , tmp.blueprintweight = tp.bpweight
		 , tmp.startability    = tp.startability
		 , tmp.startinfo	   = tp.startinfo
		 , tmp.cset1size	   = tp.cset1size
		 , tmp.cset1order	   = tp.cset1order
		 , tmp.cset2random	   = tp.cset2random
		 , tmp.cset2initialrandom = tp.cset2initialrandom
		 , tmp.abilityoffset   = tp.abilityoffset
		 , tmp.itemweight	   = tp.itemweight
	;


	update tmp_tblsetofadminsubjects tmp
	  join loader_segment s on s.segmentid = tmp._key
	   set tmp.selectionalgorithm = s.itemselection 
	 where s._fk_package = v_testpackagekey;

	-- Set default values for columns where data is not present
	-- this needs to be done especially for fixed form tests, where this data is not mandatory to be part of testpackage
	update tmp_tblsetofadminsubjects
	   set startability = coalesce(startability, 0) 
		 , startinfo = coalesce(startinfo, 1)
		 , slope = coalesce(slope, 1)
		 , intercept = coalesce(intercept, 1)
	;

	-- update test info, assuming the test/segment already exists
	update tblsetofadminsubjects sas
	  join tmp_tblsetofadminsubjects tmp on tmp._key = sas._key
	   set sas._fk_testadmin = tmp._fk_testadmin
		 , sas._fk_subject = tmp._fk_subject
		 , sas.testid = tmp.testid
		 , sas.startability = tmp.startability
		 , sas.startinfo = tmp.startinfo
		 , sas.minitems = tmp.minitems
		 , sas.maxitems = tmp.maxitems
		 , sas.slope = tmp.slope
		 , sas.intercept = tmp.intercept
		 , sas.ftstartpos = tmp.ftstartpos
		 , sas.ftendpos = tmp.ftendpos
		 , sas.ftminitems = tmp.ftminitems
		 , sas.ftmaxitems = tmp.ftmaxitems
		 , sas.selectionalgorithm = tmp.selectionalgorithm
		 , sas.blueprintweight = tmp.blueprintweight
		 , sas.cset1size = tmp.cset1size
		 , sas.cset2random = tmp.cset2random
		 , sas.cset2initialrandom = tmp.cset2initialrandom
		 , sas.virtualtest = tmp.virtualtest
		 , sas.testposition = tmp.testposition
		 , sas.issegmented = tmp.issegmented
		 , sas.itemweight = tmp.itemweight
		 , sas.abilityoffset = tmp.abilityoffset
		 , sas.cset1order = tmp.cset1order
		 , sas.updateconfig = tmp.version 
		 , sas.contract = tmp.contract
		 , sas.testtype = tmp.testtype
	;


	-- check if test or segment already exists
	insert into tblsetofadminsubjects (_key, _fk_testadmin, _fk_subject, testid, startability, startinfo, minitems, maxitems, slope, intercept, ftstartpos, ftendpos, ftminitems, ftmaxitems, selectionalgorithm, blueprintweight, cset1size, cset2random, cset2initialrandom, virtualtest, testposition, issegmented, itemweight, abilityoffset, cset1order, loadconfig, contract, testtype)
	select _key, _fk_testadmin, _fk_subject, testid, startability, startinfo, minitems, maxitems, slope, intercept, ftstartpos, ftendpos, ftminitems, ftmaxitems, selectionalgorithm, blueprintweight, cset1size, cset2random, cset2initialrandom, virtualtest, testposition, issegmented, itemweight, abilityoffset, cset1order, version, contract, testtype 
	  from tmp_tblsetofadminsubjects tmp
	 where not exists ( select 1 
						  from tblsetofadminsubjects sas
						 where sas._key = tmp._key);


	-- ?? this is being done in ms sql version....so doing it here....check later if we need this ??
	insert into _testupdate (_fk_adminsubject, configid, _date)
    select _key, version, now(3)
      from tmp_tblsetofadminsubjects;


 	call load_testgrades(v_testpackagekey);


    insert into testcohort (_fk_adminsubject, cohort, itemratio)
    select _key, 1, 1.0
      from tmp_tblsetofadminsubjects 
     where selectionalgorithm like 'adaptive%' 
	   and not exists ( select * 
						  from testcohort 
						 where _fk_adminsubject = _key);


	-- clean-up
	drop temporary table tmp_tblsetofadminsubjects;
	drop temporary table tmp_testproperties;

end $$

DELIMITER ;