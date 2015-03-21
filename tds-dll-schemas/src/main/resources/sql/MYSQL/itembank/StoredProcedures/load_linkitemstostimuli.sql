DELIMITER $$

drop procedure if exists `load_linkitemstostimuli` $$

create procedure `load_linkitemstostimuli` (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			4/3/2014		Sai V. 			Original Code.
*/
	v_testpackagekey varchar(350)
)
begin

	create temporary table tmp_segments (
		testitemid	varchar(150)
	  , segments    varchar(250)
	)engine = memory;

	create temporary table tmp_passages (
		testitemid	varchar(150)
	  , passages   	varchar(150)
	)engine = memory;

	-- load items related to segments   
	insert into tmp_segments
	select distinct testitemid
		 , ref
	  from loader_testitemrefs
	 where reftype = 'bp'
	   and refcategory = 'segment'
	   and _fk_package = v_testpackagekey;

	insert into tmp_passages (testitemid, passages)
	select distinct testitemid
		 , ref
	  from loader_testitemrefs
	 where reftype = 'passage'
	   and _fk_package = v_testpackagekey;

	-- first delete all strand links for these items
	delete s
	  from tblsetofitemstimuli s 
	  join tmp_segments seg on seg.testitemid = s._fk_item and seg.segments = s._fk_adminsubject;

	insert into tblsetofitemstimuli
	select p.testitemid
		 , p.passages
		 , seg.segments
		 , version
	  from tmp_passages p
	  join tmp_segments seg on seg.testitemid = p.testitemid
	  join loader_testitem ti on ti.testitemid = seg.testitemid
	 where _fk_package = v_testpackagekey;
	
	-- clean-up
	drop temporary table tmp_segments;
	drop temporary table tmp_passages;

end $$

DELIMITER ;