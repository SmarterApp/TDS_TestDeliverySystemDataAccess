DELIMITER $$

drop procedure if exists `load_affinitygroups` $$

create procedure `load_affinitygroups` (
/*
Description: Load strands from loader_testblueprint

VERSION 	DATE 			AUTHOR 			COMMENTS
001			4/3/2014		Sai V. 			Original Code.
*/
	v_testpackagekey varchar(350)
)
begin

	declare v_issegmented bit;
	declare v_testkey varchar(250);

	drop temporary table if exists tmp_affinitygroup;
	create temporary table tmp_affinitygroup (
	  	_fk_adminsubject varchar(250)
	  , groupid 		 varchar(100)
	  , minitems 		 int
	  , maxitems 		 int
	  , bpweight 		 float
	  , isstrictmax		 bit
	  , precisiontarget	 float
	  , abilityweight	 float
	  , loadconfig 		 bigint
	  , updateconfig 	 bigint
	  , startability 		float
	  , startinfo 			float 
	  , precisiontargetmetweight	float
	  , precisiontargetnotmetweight float
	  , _fk_package 	 varchar(350)
	)engine = memory;

	drop temporary table if exists tmp_affinitygroupitem;
	create temporary table tmp_affinitygroupitem (
		_fk_adminsubject varchar(250)
	  , groupid 		 varchar(100)
	  , _fk_item 		 varchar(100)
	)engine = memory;

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

	-- load "test" affinity groups for non-segmented tests
	insert into tmp_affinitygroup (_fk_adminsubject, groupid, minitems, maxitems, loadconfig, updateconfig, _fk_package)
	select test.bpelementid
	     , ag.bpelementid
		 , case when ag.minopitems is null or ag.minopitems < 0 then 0 else ag.minopitems end
		 , case when ag.maxopitems is null or ag.maxopitems < 0 then 0 else ag.maxopitems end
		 , ag.version
		 , ag.version
		 , ag._fk_package
	  from loader_testblueprint test 	
	  join loader_testblueprint ag on ag._fk_package = test._fk_package 
	 where test.elementtype = 'test'
	   and ag.elementtype = 'affinitygroup'
	   and test._fk_package = v_testpackagekey;

	set v_testkey = (select bpelementid from loader_testblueprint where elementtype = 'test' and _fk_package = v_testpackagekey);

	insert into tmp_affinitygroupitem (_fk_adminsubject, groupid, _fk_item)
	select v_testkey, ref, testitemid
	  from loader_testitemrefs 
	 where refcategory = 'affinitygroup'
	   and _fk_package = v_testpackagekey;


	if v_issegmented = 1 then
		-- second, load only available affinitygroup records for each of the segment(s) 
		insert into tmp_affinitygroup (_fk_adminsubject, groupid, minitems, maxitems, loadconfig, updateconfig, _fk_package)
		select segp.segmentid
			 , bp.bpelementid
			 , case when segp.minopitems is null or segp.minopitems < 0 then 0 else segp.minopitems end
			 , case when segp.maxopitems is null or segp.maxopitems < 0 then 0 else segp.maxopitems end
			 , version
			 , version
			 , bp._fk_package
		  from loader_testblueprint bp 	
		  join loader_segmentblueprint segp on segp.segmentbpelementid = bp.bpelementid		
										   and segp._fk_package = bp._fk_package
		 where elementtype = 'affinitygroup'
		   and bp._fk_package = v_testpackagekey;

		insert into tmp_affinitygroupitem (_fk_adminsubject, groupid, _fk_item)
		select ref1.ref, ref2.ref, ref2.testitemid
		  from loader_testitemrefs ref1
		  join loader_testitemrefs ref2 on ref2._fk_package = ref1._fk_package and ref1.testitemid = ref2.testitemid
		 where ref1.refcategory = 'segment'
		   and ref2.refcategory = 'affinitygroup'
		   and ref1._fk_package = v_testpackagekey;
	end if;

	drop temporary table if exists tmp_tblaffinityproperties;
	create temporary table tmp_tblaffinityproperties as (
		select tmp._fk_adminsubject 
			 , tmp.groupid
			 , max(if(sisp.propname = 'isstrictmax', (case sisp.propvalue when 'true' then 1 else 0 end), null)) as isstrictmax
			 , max(if(sisp.propname = 'bpweight', sisp.propvalue, null)) as bpweight
			 , max(if(sisp.propname = 'startability', sisp.propvalue, null)) as startability
			 , max(if(sisp.propname = 'startinfo', sisp.propvalue, null)) as startinfo
			 , max(if(sisp.propname = 'abilityweight', sisp.propvalue, null)) as abilityweight
			 , max(if(sisp.propname = 'precisiontarget', sisp.propvalue, null)) as precisiontarget
			 , max(if(sisp.propname = 'precisiontargetmetweight', sisp.propvalue, null)) as precisiontargetmetweight
			 , max(if(sisp.propname = 'precisiontargetnotmetweight', sisp.propvalue, null)) as precisiontargetnotmetweight
		  from tmp_affinitygroup tmp
		  join loader_segmentitemselectionproperties sisp on sisp.segmentid = tmp._fk_adminsubject 
														 and sisp.bpelementid = tmp.groupid
														 and sisp._fk_package = tmp._fk_package	
		 where sisp._fk_package = v_testpackagekey
		 group by tmp.groupid
	);

	update tmp_affinitygroup tmp
	  join tmp_tblaffinityproperties p on p._fk_adminsubject = tmp._fk_adminsubject
									  and p.groupid = tmp.groupid
	   set tmp.bpweight    		= p.bpweight
		 , tmp.isstrictmax 		= p.isstrictmax
		 , tmp.startability     = p.startability
		 , tmp.startinfo	    = p.startinfo
		 , tmp.abilityweight   = p.abilityweight
		 , tmp.precisiontarget  = p.precisiontarget
		 , tmp.precisiontargetmetweight    = p.precisiontargetmetweight
		 , tmp.precisiontargetnotmetweight = p.precisiontargetnotmetweight;


	-- set default values
	update tmp_affinitygroup tmp
	   set tmp.bpweight     = coalesce(tmp.bpweight, 1)
		 , tmp.isstrictmax	= coalesce(tmp.isstrictmax, 0);


	-- delete and re-load all affinitygroup records for the given adminsubjectkey
	delete ag
	  from affinitygroup ag
	  join tmp_affinitygroup tmp on tmp._fk_adminsubject = ag._fk_adminsubject;

	delete ag
	  from affinitygroupitem ag
	  join tmp_affinitygroupitem tmp on tmp._fk_adminsubject = ag._fk_adminsubject;

	-- load affinity groups & affinity group items	
	insert into affinitygroup (_fk_adminsubject, groupid, minitems, maxitems, weight, isstrictmax, loadconfig, updateconfig, abilityweight, precisiontarget, startability, startinfo, precisiontargetmetweight, precisiontargetnotmetweight)
	select _fk_adminsubject, groupid, minitems, maxitems, bpweight, isstrictmax, loadconfig, updateconfig, abilityweight, precisiontarget, startability, startinfo, precisiontargetmetweight, precisiontargetnotmetweight
	  from tmp_affinitygroup;

	insert into affinitygroupitem (_fk_adminsubject, groupid, _fk_item)
	select _fk_adminsubject, groupid, _fk_item
	  from tmp_affinitygroupitem;

	
	-- clean-up
	drop temporary table tmp_tblaffinityproperties;
	drop temporary table tmp_affinitygroup;

end $$

DELIMITER ;