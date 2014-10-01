DELIMITER $$

drop procedure if exists load_adminstrands $$

create procedure load_adminstrands (
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
	drop temporary table if exists tmp_tbladminstrand;
	create temporary table tmp_tbladminstrand (
		_key 				varchar(255)
	  , _fk_adminsubject 	varchar(250)
	  , _fk_strand 			varchar(150)
	  , minitems 			int 
	  , maxitems 			int
	  , bpweight 			float
	  , adaptivecut 		float
	  , startability 		float
	  , startinfo 			float 
	  , scalar 				float
	  , loadmin				int
	  , loadmax				int	
	  , version 			bigint
	  , elementtype 		varchar(100)
	  , _fk_package 		varchar(350)
	);

	drop temporary table if exists tmp_tblallstrands;
	create temporary table tmp_tblallstrands (
		strand 				varchar(150)
	  , minopitems			int
	  , maxopitems			int
	);

	-- first, gather all the strand (top level), contentlevel (child) nodes
	-- in DRC's version of test package both strand and contentlevel standards maybe labeled under 'strand'
	insert into tmp_tblallstrands
	select bpelementid, minopitems, maxopitems
 	  from loader_testblueprint 	
	 where elementtype in ('strand', 'contentlevel')
	   and _fk_package = v_testpackagekey;


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

	-- load test data for both segmented and non-segment tests
	-- elementtype = test
	insert into tmp_tbladminstrand (_key, _fk_adminsubject, _fk_strand, minitems, maxitems, version, loadmin, loadmax, elementtype, _fk_package)
	select distinct concat(bp.bpelementid, '-', s.strand)
		 , bp.bpelementid
		 , s.strand
		 , case when v_issegmented = 1 then bp.minopitems else s.minopitems end
		 , case when v_issegmented = 1 then bp.maxopitems else s.maxopitems end
		 , version
		 , case when v_issegmented = 1 then null else s.minopitems end
		 , case when v_issegmented = 1 then null else s.maxopitems end
		 , elementtype
		 , bp._fk_package
	  from loader_testblueprint bp 	
	  join tmp_tblallstrands s on 1 = 1	
	 where elementtype = 'test'
	   and bp._fk_package = v_testpackagekey;

	-- for segment tests, load individual segments as well
	-- elementtype = segment
	if v_issegmented = 1 then
		insert into tmp_tbladminstrand (_key, _fk_adminsubject, _fk_strand, minitems, maxitems, version, loadmin, loadmax, elementtype, _fk_package)
		select distinct concat(bp.bpelementid, '-', s.strand)
			 , bp.bpelementid
			 , s.strand
			 , bp.minopitems
			 , bp.maxopitems
			 , version
			 , bp.minopitems
			 , bp.maxopitems
			 , elementtype
			 , bp._fk_package
		  from loader_testblueprint bp 	
		  join tmp_tblallstrands s on 1 = 1	
		 where elementtype = 'segment'
		   and bp._fk_package = v_testpackagekey;
	end if;

	drop temporary table if exists tmp_tblstrandproperties;	
	create temporary table tmp_tblstrandproperties as (
		select tmp._key
			 , max(if(sisp.propname = 'bpweight', sisp.propvalue, null)) as bpweight
			 , max(if(sisp.propname = 'adaptivecut', sisp.propvalue, null)) as adaptivecut
			 , max(if(sisp.propname = 'startability', sisp.propvalue, null)) as startability
			 , max(if(sisp.propname = 'startinfo', sisp.propvalue, null)) as startinfo
			 , max(if(sisp.propname = 'scalar', sisp.propvalue, null)) as scalar
		  from tmp_tbladminstrand tmp
		  join loader_segmentitemselectionproperties sisp on sisp.segmentid = tmp._fk_adminsubject 
														 and sisp.bpelementid = tmp._fk_strand
														 and sisp._fk_package = tmp._fk_package		
		 where tmp.elementtype = 'strand'
		   and sisp._fk_package = v_testpackagekey
		group by tmp._key
	);

	drop temporary table if exists tmp_tblcontentproperties;
	create temporary table tmp_tblcontentproperties as (
		select tmp._key
			 , max(if(sisp.propname = 'bpweight', sisp.propvalue, null)) as bpweight
		  from tmp_tbladminstrand tmp
		  join loader_segmentitemselectionproperties sisp on sisp.segmentid = tmp._fk_adminsubject 
														 and sisp.bpelementid = tmp._fk_strand
														 and sisp._fk_package = tmp._fk_package	
		 where tmp.elementtype = 'contentlevel'
		   and sisp._fk_package = v_testpackagekey
		group by tmp._key
	);

/*select * from tmp_tblcontentproperties;
  select * from tmp_tblstrandproperties;
  select * from tmp_tbladminstrand; */

	update tmp_tbladminstrand tmp
	  join tmp_tblstrandproperties p on p._key = tmp._key
	   set tmp.bpweight 	= p.bpweight
		 , tmp.adaptivecut 	= p.adaptivecut	
		 , tmp.startability = p.startability
		 , tmp.startinfo	= p.startinfo
		 , tmp.scalar   	= p.scalar;

	update tmp_tbladminstrand tmp
	  join tmp_tblcontentproperties p on p._key = tmp._key
	   set tmp.bpweight = p.bpweight;


	-- set default values
	update tmp_tbladminstrand tmp
	   set tmp.adaptivecut 	= coalesce(tmp.adaptivecut, 0)
		 , tmp.startability = coalesce(tmp.startability, 0)
		 , tmp.startinfo	= coalesce(tmp.startinfo, 1)
		 , tmp.scalar   	= coalesce(tmp.scalar, 1);


	-- update test info, assuming the test/segment already exists
	update tbladminstrand sas
	  join tmp_tbladminstrand tmp on tmp._key = sas._key
	   set sas._fk_adminsubject = tmp._fk_adminsubject
		 , sas._fk_strand 	= tmp._fk_strand
		 , sas.minitems 	= tmp.minitems
		 , sas.maxitems 	= tmp.maxitems
		 , sas.bpweight 	= tmp.bpweight
		 , sas.adaptivecut 	= tmp.adaptivecut
		 , sas.startability = tmp.startability
		 , sas.startinfo 	= tmp.startinfo
		 , sas.scalar 		= tmp.scalar 
		 , sas.updateconfig = tmp.version
		 , sas.loadmin		= tmp.loadmin
		 , sas.loadmax		= tmp.loadmax
	;


	-- check if test or segment already exists
	insert into tbladminstrand (_key, _fk_adminsubject, _fk_strand, minitems, maxitems, bpweight, adaptivecut, startability, startinfo, scalar, loadconfig, loadmin, loadmax)
	select _key, _fk_adminsubject, _fk_strand, minitems, maxitems, bpweight, adaptivecut, startability, startinfo, scalar, version, loadmin, loadmax
	  from tmp_tbladminstrand tmp
	 where not exists ( select 1 
						  from tbladminstrand sas
						 where sas._key = tmp._key);


	-- clean-up
	drop temporary table tmp_tbladminstrand;
	drop temporary table tmp_tblstrandproperties;
	drop temporary table tmp_tblcontentproperties;
	drop temporary table tmp_tblallstrands;


end $$

DELIMITER ;