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

	create temporary table tmp_adminsubjects (
		elementtype			varchar(100)
	  ,	adminsubjectkey		varchar(250)
	);

	create temporary table tmp_affinitygroup (
	  	_fk_adminsubject varchar(250)
	  , groupid 		 varchar(100)
	  , minitems 		 int
	  , maxitems 		 int
	  , loadconfig 		 bigint
	  , updateconfig 	 bigint
	);
	
	-- gather all adminsubjects (test + segment)
	insert into tmp_adminsubjects
	select elementtype, bpelementid
	  from loader_testblueprint
	 where elementtype = 'segment'
	   and _fk_package = v_testpackagekey;
	
	-- first, insert one record for each test and affinitygroup combination
-- 	insert into tmp_affinitygroup
-- 	select adminsubjectkey
-- 		 , bpelementid
-- 		 , case when minopitems is null or minopitems < 0 then 0 else minopitems end
-- 		 , case when maxopitems is null or maxopitems < 0 then 0 else maxopitems end
-- 		 , version
-- 		 , version
-- 	  from loader_testblueprint tbp
-- 	  join tmp_adminsubjects asub on 1 = 1
-- 	 where tbp.elementtype = 'affinitygroup'
-- 	   and asub.elementtype = 'test'
-- 	   and _fk_package = v_testpackagekey;	

	-- second, load only available affinitygroup records for each of the segment(s) 
	insert into tmp_affinitygroup
	select adminsubjectkey
		 , bpelementid
		 , case when tbp.minopitems is null or tbp.minopitems < 0 then 0 else tbp.minopitems end
		 , case when tbp.maxopitems is null or tbp.maxopitems < 0 then 0 else tbp.maxopitems end
		 , version
		 , version
	  from loader_testblueprint tbp
	  join tmp_adminsubjects asub on 1 = 1
	  left join loader_segmentblueprint sbp on sbp.segmentid = asub.adminsubjectkey 
									  and sbp.segmentbpelementid = tbp.bpelementid
									  and sbp._fk_package = tbp._fk_package	
	 where tbp.elementtype = 'affinitygroup'
	   and asub.elementtype = 'segment'
	   and tbp._fk_package = v_testpackagekey;


	-- delete and re-load all affinitygroup records for the given adminsubjectkey
	delete
	  from affinitygroup
	 where groupid in (select distinct _fk_adminsubject from tmp_affinitygroup);  


	insert into affinitygroup (_fk_adminsubject, groupid, minitems, maxitems, loadconfig, updateconfig)
	select *
	  from tmp_affinitygroup;


	-- clean-up
	drop temporary table if exists tmp_adminsubjects;
	drop temporary table if exists tmp_affinitygroup;

end $$

DELIMITER ;





