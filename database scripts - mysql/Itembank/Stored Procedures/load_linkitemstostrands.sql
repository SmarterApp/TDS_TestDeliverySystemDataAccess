DELIMITER $$

drop procedure if exists `load_linkitemstostrands` $$

create procedure `load_linkitemstostrands` (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			4/3/2014		Sai V. 			Original Code.
*/
	v_testpackagekey varchar(350)
)
begin

	delete 
	  from loader_setofitemstrands
	 where _fk_package = v_testpackagekey;

	drop temporary table if exists tmp_segments;
	create temporary table tmp_segments (
		testitemid	varchar(150)
	  , segments    varchar(250)
	);

	drop temporary table if exists tmp_strands;
	create temporary table tmp_strands (
		testitemid	varchar(150)
	  , strands   	varchar(150)
	  , lvl			int
	);

	drop temporary table if exists tmp_maxlvlstrands;
	create temporary table tmp_maxlvlstrands (
		testitemid	varchar(150)
	  , max_lvl		int
	);

	-- load items related to segments   
	insert into tmp_segments
	select distinct testitemid
		 , ref
	  from loader_testitemrefs
	 where reftype = 'bp'
	   and refcategory = 'segment'
	   and _fk_package = v_testpackagekey;

	insert into tmp_strands (testitemid, strands, lvl)
	select distinct testitemid
		 , ref
		 , treelevel
	  from loader_testitemrefs 
	 where reftype = 'bp'
	   and refcategory in ('strand', 'contentlevel')
	   and _fk_package = v_testpackagekey; 


	insert into tmp_maxlvlstrands
	select testitemid, max(lvl) max_lvl
	  from tmp_strands
	group by testitemid;

    delete cl
	  from aa_itemcl cl
	 where exists ( select * 
					  from tmp_segments seg 
					 where seg.segments = cl._fk_adminsubject and seg.testitemid = cl._fk_item);

	insert into aa_itemcl
	select seg.segments
		 , s.testitemid
		 , s.strands
	  from tmp_strands s
	  join tmp_segments seg on seg.testitemid = s.testitemid;

	delete s
	  from tmp_strands s
	  join tmp_maxlvlstrands ms on ms.testitemid = s.testitemid
							   and ms.max_lvl <> s.lvl;


	insert into loader_setofitemstrands
	select ti._fk_package
		 , s.testitemid
		 , s.strands
		 , seg.segments
		 , version
	  from tmp_strands s
	  join tmp_segments seg on seg.testitemid = s.testitemid
	  join loader_testitem ti on ti.testitemid = s.testitemid
	 where ti._fk_package = v_testpackagekey;

	
	-- first delete all strand links for these items
	delete s
	  from tblsetofitemstrands s 
	 where exists (select * 
					 from tmp_segments seg 
				    where seg.testitemid = s._fk_item 
					  and seg.segments = s._fk_adminsubject);

	insert into tblsetofitemstrands
	select _fk_item
		 , _fk_strand
		 , _fk_adminsubject
		 , version
	  from loader_setofitemstrands
	 where _fk_package = v_testpackagekey;
	
	-- clean-up
	drop temporary table tmp_segments;
	drop temporary table tmp_strands;
	drop temporary table tmp_maxlvlstrands;

end $$

DELIMITER ;