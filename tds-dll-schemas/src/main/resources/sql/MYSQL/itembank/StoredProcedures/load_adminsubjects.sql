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

	drop temporary table if exists tmp_tblalgprop;
	create temporary table tmp_tblalgprop (
		propname varchar(1000)
	)engine = memory;

	insert into tmp_tblalgprop values ('ftstartpos');
	insert into tmp_tblalgprop values ('ftendpos');
	insert into tmp_tblalgprop values ('bpweight');
	insert into tmp_tblalgprop values ('startability');
	insert into tmp_tblalgprop values ('startinfo');
	insert into tmp_tblalgprop values ('cset1size');
	insert into tmp_tblalgprop values ('cset1order');
	insert into tmp_tblalgprop values ('cset2random');
	insert into tmp_tblalgprop values ('cset2initialrandom');
	insert into tmp_tblalgprop values ('abilityoffset');
	insert into tmp_tblalgprop values ('itemweight');
	insert into tmp_tblalgprop values ('precisiontarget');
	insert into tmp_tblalgprop values ('adaptivecut');
	insert into tmp_tblalgprop values ('toocloseses');
	insert into tmp_tblalgprop values ('slope');
	insert into tmp_tblalgprop values ('intercept');
	insert into tmp_tblalgprop values ('abilityweight');
	insert into tmp_tblalgprop values ('computeabilityestimates');
	insert into tmp_tblalgprop values ('rcabilityweight');
	insert into tmp_tblalgprop values ('precisiontargetmetweight');
	insert into tmp_tblalgprop values ('precisiontargetnotmetweight');
	insert into tmp_tblalgprop values ('terminationoverallinfo');
	insert into tmp_tblalgprop values ('terminationrcinfo');
	insert into tmp_tblalgprop values ('terminationmincount');
	insert into tmp_tblalgprop values ('terminationtooclose');
	insert into tmp_tblalgprop values ('terminationflagsand');


	-- create temp table to host data during data gathering and processing
	drop temporary table if exists tmp_tblsetofadminsubjects;
	create temporary table tmp_tblsetofadminsubjects (
		_key varchar(250)
	  , _fk_testadmin 	varchar(150)
	  , _fk_subject 	varchar(150)
	  , testid 			varchar(255)
	  , startability 	float
	  , startinfo 		float 
	  , minitems 		int 
	  , maxitems 		int
	  , slope 			float
	  , intercept 		float
	  , ftstartpos 		int
	  , ftendpos 		int
	  , ftminitems 		int
	  , ftmaxitems 		int
	  , selectionalgorithm varchar(50)
	  , blueprintweight float
	  , cset1size 		int
	  , cset2random 	int
	  , cset2initialrandom int
	  , virtualtest 	varchar(200)
	  , testposition 	int
	  , issegmented 	bit
	  , itemweight 		float
	  , abilityoffset 	float
	  , cset1order 		varchar(50)
	  , version 		bigint
	  , contract 		varchar(100)
	  , testtype 		varchar(60)
	  , precisiontarget	float
	  , adaptivecut		float
	  , toocloseses		float
	  , abilityweight				float
	  , computeabilityestimates		bit
	  , rcabilityweight				float
	  , precisiontargetmetweight	float
	  , precisiontargetnotmetweight float
	  , terminationoverallinfo		bit
	  , terminationrcinfo			bit
	  , terminationmincount			bit
	  , terminationtooclose			bit
	  , terminationflagsand			bit
	  , bpmetricfunction 			varchar(10)
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
			 , max(if(sisp.propname = 'slope', sisp.propvalue, null)) as slope
			 , max(if(sisp.propname = 'intercept', sisp.propvalue, null)) as intercept
			 , max(if(sisp.propname = 'precisiontarget', sisp.propvalue, null)) as precisiontarget
			 , max(if(sisp.propname = 'adaptivecut', sisp.propvalue, null)) as adaptivecut
			 , max(if(sisp.propname = 'toocloseses', sisp.propvalue, null)) as toocloseses
		-- added on 03.20.2015
			 , max(if(sisp.propname = 'abilityweight', sisp.propvalue, null)) as abilityweight
			 , max(if(sisp.propname = 'computeabilityestimates', (case sisp.propvalue when 'true' then 1 else 0 end), null)) as computeabilityestimates
			 , max(if(sisp.propname = 'rcabilityweight', sisp.propvalue, null)) as rcabilityweight
			 , max(if(sisp.propname = 'precisiontargetmetweight', sisp.propvalue, null)) as precisiontargetmetweight
			 , max(if(sisp.propname = 'precisiontargetnotmetweight', sisp.propvalue, null)) as precisiontargetnotmetweight
			 , max(if(sisp.propname = 'terminationoverallinfo', (case sisp.propvalue when 'true' then 1 else 0 end), null)) as terminationoverallinfo
			 , max(if(sisp.propname = 'terminationrcinfo', (case sisp.propvalue when 'true' then 1 else 0 end), null)) as terminationrcinfo
			 , max(if(sisp.propname = 'terminationmincount', (case sisp.propvalue when 'true' then 1 else 0 end), null)) as terminationmincount
			 , max(if(sisp.propname = 'terminationtooclose', (case sisp.propvalue when 'true' then 1 else 0 end), null)) as terminationtooclose
			 , max(if(sisp.propname = 'terminationflagsand', (case sisp.propvalue when 'true' then 1 else 0 end), null)) as terminationflagsand
		  from tmp_tblsetofadminsubjects tmp
		  join loader_segmentitemselectionproperties sisp on sisp.segmentid = tmp._key and tmp._key = sisp.bpelementid
		 where sisp._fk_package = v_testpackagekey
		group by sisp.bpelementid
	);

	update tmp_tblsetofadminsubjects tmp
	  join tmp_testproperties tp on tp.bpelementid = tmp._key
	   set tmp.ftstartpos 	   				= tp.ftstartpos 
		 , tmp.ftendpos 	   				= tp.ftendpos
		 , tmp.blueprintweight 				= tp.bpweight
		 , tmp.startability    				= tp.startability
		 , tmp.startinfo	  				= tp.startinfo
		 , tmp.cset1size	   				= tp.cset1size
		 , tmp.cset1order	   				= tp.cset1order
		 , tmp.cset2random	   				= tp.cset2random
		 , tmp.cset2initialrandom 			= tp.cset2initialrandom
		 , tmp.abilityoffset   				= tp.abilityoffset
		 , tmp.itemweight	   				= tp.itemweight
		 , tmp.slope						= tp.slope
		 , tmp.intercept					= tp.intercept
		 , tmp.precisiontarget  			= tp.precisiontarget
		 , tmp.adaptivecut					= tp.adaptivecut
		 , tmp.toocloseses					= tp.toocloseses
		 , tmp.abilityweight				= tp.abilityweight
		 , tmp.computeabilityestimates 		= tp.computeabilityestimates
		 , tmp.rcabilityweight 				= tp.rcabilityweight				
		 , tmp.precisiontargetmetweight 	= tp.precisiontargetmetweight
		 , tmp.precisiontargetnotmetweight 	= tp.precisiontargetnotmetweight
		 , tmp.terminationoverallinfo 		= tp.terminationoverallinfo
		 , tmp.terminationrcinfo 			= tp.terminationrcinfo			
		 , tmp.terminationmincount 			= tp.terminationmincount
		 , tmp.terminationtooclose 			= tp.terminationtooclose
		 , tmp.terminationflagsand 			= tp.terminationflagsand			
	;


	update tmp_tblsetofadminsubjects tmp
	  join loader_segment s on s.segmentid = tmp._key
	   set tmp.selectionalgorithm = s.itemselection 
	 where s._fk_package = v_testpackagekey;

	-- if segmented test grab default values from startability, startinfo...etc from segment position 1 test
	if v_issegmented = 1 then
		drop temporary table if exists tmp_testdefaults;
		create temporary table tmp_testdefaults as (
			select startability, startinfo, slope, intercept, toocloseses, rcabilityweight, precisiontarget, adaptivecut
			  from tmp_tblsetofadminsubjects
			 where testposition = 1
		);

		update tmp_tblsetofadminsubjects s, tmp_testdefaults d
		   set s.startability = d.startability
			 , s.startinfo = d.startinfo
			 , s.slope = d.slope
			 , s.intercept = d.intercept
			 , s.toocloseses = d.toocloseses
			 , s.rcabilityweight = d.rcabilityweight
			 , s.precisiontarget = d.precisiontarget
			 , s.adaptivecut = d.adaptivecut
		where s.testposition is null;		
	end if;

	-- Set default values for columns where data is not present
	-- this needs to be done especially for fixed form tests, where this data is not mandatory to be part of testpackage
	update tmp_tblsetofadminsubjects
	   set startability 	  = coalesce(startability, 0) 
		 , startinfo 		  = coalesce(startinfo, 1)
		 , slope	 		  = coalesce(slope, 1)
		 , intercept 		  = coalesce(intercept, 1)
		 , bpmetricfunction   = 'bp1'
		 , blueprintweight    = coalesce(blueprintweight, 5.0)
		 , itemweight	      = coalesce(itemweight, 5.0)
		 , cset1order	      = coalesce(cset1order, 'ABILITY')
		 , abilityoffset   	  = coalesce(abilityoffset, 0.0)
		 , cset2initialrandom = coalesce(cset2initialrandom, 5)
		 , cset1size	   	  = coalesce(cset1size, 20)
		 , cset2random	      = coalesce(cset2random, 1)
		 , abilityweight				= coalesce(abilityweight, 1)
		 , computeabilityestimates 		= coalesce(computeabilityestimates, 1)
		 , rcabilityweight 				= coalesce(rcabilityweight, 1)				
		 , precisiontargetmetweight 	= coalesce(precisiontargetmetweight, 1)	
		 , precisiontargetnotmetweight 	= coalesce(precisiontargetnotmetweight, 1)
		 , terminationoverallinfo 		= coalesce(terminationoverallinfo, 0)		
		 , terminationrcinfo 			= coalesce(terminationrcinfo, 0)			
		 , terminationmincount 			= coalesce(terminationmincount, 0)			
		 , terminationtooclose 			= coalesce(terminationtooclose, 0)		
		 , terminationflagsand 			= coalesce(terminationflagsand, 0)
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
		 , sas.precisiontarget = tmp.precisiontarget
		 , sas.adaptivecut = tmp.adaptivecut
		 , sas.toocloseses = tmp.toocloseses
		 , sas.abilityweight	= tmp.abilityweight
		 , sas.computeabilityestimates = tmp.computeabilityestimates
		 , sas.rcabilityweight = tmp.rcabilityweight				
		 , sas.precisiontargetmetweight = tmp.precisiontargetmetweight
		 , sas.precisiontargetnotmetweight = tmp.precisiontargetnotmetweight
		 , sas.terminationoverallinfo = tmp.terminationoverallinfo		
		 , sas.terminationrcinfo = tmp.terminationrcinfo	
		 , sas.terminationmincount = tmp.terminationmincount
		 , sas.terminationtooclose = tmp.terminationtooclose
		 , sas.terminationflagsand = tmp.terminationflagsand
		 , sas.bpmetricfunction = tmp.bpmetricfunction
	;


	-- check if test or segment already exists
	insert into tblsetofadminsubjects (_key, _fk_testadmin, _fk_subject, testid, startability, startinfo, minitems, maxitems, slope, intercept, ftstartpos, ftendpos, ftminitems, ftmaxitems, selectionalgorithm, blueprintweight, cset1size, cset2random, cset2initialrandom, virtualtest, testposition, issegmented, itemweight, abilityoffset, cset1order, loadconfig, contract, testtype, precisiontarget, adaptivecut, toocloseses, abilityweight, computeabilityestimates, rcabilityweight, precisiontargetmetweight, precisiontargetnotmetweight, terminationoverallinfo, terminationrcinfo, terminationmincount, terminationtooclose, terminationflagsand, bpmetricfunction)
	select _key, _fk_testadmin, _fk_subject, testid, startability, startinfo, minitems, maxitems, slope, intercept, ftstartpos, ftendpos, ftminitems, ftmaxitems, selectionalgorithm, blueprintweight, cset1size, cset2random, cset2initialrandom, virtualtest, testposition, issegmented, itemweight, abilityoffset, cset1order, version, contract, testtype, precisiontarget, adaptivecut, toocloseses, abilityweight, computeabilityestimates, rcabilityweight, precisiontargetmetweight, precisiontargetnotmetweight, terminationoverallinfo, terminationrcinfo, terminationmincount, terminationtooclose, terminationflagsand, bpmetricfunction 
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

	-- clear old data and re-load again
	delete isp
	from tblitemselectionparm isp
	join tmp_tblsetofadminsubjects tmp on tmp._key = isp._fk_adminsubject;


	-- for all properties that do not have a column defined/created in tblsetofadminsubjects tables
	-- insert them into tblitemselectionparm
	insert into tblitemselectionparm
	select tmp._key
		 , tmp.testid
		 , sisp.propname
		 , sisp.propvalue
		 , sisp.proplabel
		 , unhex(REPLACE(UUID(), '-', ''))
	  from tmp_tblsetofadminsubjects tmp
	  join loader_segmentitemselectionproperties sisp on sisp.segmentid = tmp._key and tmp._key = sisp.bpelementid
	 where propname not in (select propname from tmp_tblalgprop)
	   and selectionalgorithm like 'adaptive%';


	-- clean-up
	drop temporary table tmp_tblsetofadminsubjects;
	drop temporary table tmp_testproperties;
	drop temporary table tmp_tblalgprop;

end $$

DELIMITER ;