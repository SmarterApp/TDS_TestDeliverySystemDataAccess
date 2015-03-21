DELIMITER $$

drop procedure if exists `load_itemproperties` $$

create procedure `load_itemproperties` (
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

	create temporary table tmp_itemprops (
		_fk_item 		 varchar(150)
	  , propname 		 varchar(50)
	  , propvalue 		 varchar(128)
	  , propdescription  varchar(150)
	  , _fk_adminsubject varchar(250)
	)engine = memory;

	-- load items related to segments   
	insert into tmp_segments
	select distinct testitemid
		 , ref
	  from loader_testitemrefs
	 where reftype = 'bp'
	   and refcategory = 'segment'
	   and _fk_package = v_testpackagekey;

	insert into tmp_itemprops
	select tip.testitemid
		 , tip.propname
		 , tip.propvalue
		 , tip.proplabel
		 , seg.segments
	  from loader_testitempoolproperties tip
	  join tmp_segments seg on seg.testitemid = tip.testitemid
 	 where _fk_package = v_testpackagekey;

	-- all item types should have tdspoolfilter properties
	-- tdspoolfilter properties can exist as an attribute or as property. Here, we are retreiving the property from attribute
	insert into tmp_itemprops
	select ti.testitemid
		 , '--ITEMTYPE--'
		 , ti.itemtype
		 , ti.itemtype
		 , seg.segments
	  from loader_testitem ti
	  join tmp_segments seg on seg.testitemid = ti.testitemid
	 where _fk_package = v_testpackagekey;


	insert into tblitemprops
	select distinct tmp.*
		 , 1 as isactive
	from tmp_itemprops tmp
	where not exists ( select 1
						 from tblitemprops ip
						where ip._fk_Item = tmp._fk_Item 
						  and ip.propname = tmp.propname 
						  and ip.propvalue = tmp.propvalue
						  and ip._fk_adminsubject = tmp._fk_adminsubject
					);

	-- activate/inactivate any item properties that have been removed/re-added for this item and this test
	update tblitemprops ip
	  left join tmp_itemprops tmp on ip._fk_Item = tmp._fk_Item
								 and ip.propname = tmp.propname 
								 and ip.propvalue = tmp.propvalue
	   set isactive = case when tmp._fk_Item is null then 0 else 1 end
	 where ip._fk_adminsubject = tmp._fk_adminsubject;


	-- by default, all item types have tdspoolfilter properties. ensure that this is so
-- ?? do we need this ?? => reason: implemented "select ti.testitemid, '--ITEMTYPE--'" logic to substitute this....test if that works....
-- 	insert into tblitemprops (_fk_item, propname, propvalue, propdescription, _fk_adminsubject)
-- 	select _fk_item, '--ITEMTYPE--', propvalue, propvalue, _fk_adminsubject
-- 	  from tmp_itemprops tmp
--      where not exists (select * 
-- 						 from tblitemprops ip
-- 						where ip._fk_item = tmp._fk_item and ip.propname = '--ITEMTYPE--' and ip.propvalue = tmp.propvalue  and ip._fk_adminsubject = tmp._fk_adminsubject);	


	-- clean-up
	drop temporary table tmp_segments;
	drop temporary table tmp_itemprops;

end $$

DELIMITER ;