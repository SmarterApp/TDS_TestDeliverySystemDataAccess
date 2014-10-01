DELIMITER $$

drop procedure if exists load_adminitems $$

create procedure load_adminitems (
/*
Description: Load test admin items

VERSION 	DATE 			AUTHOR 			COMMENTS
001			4/3/2014		Sai V. 			Original Code.
*/
	v_testpackagekey varchar(350)
)
begin
	
	drop temporary table if exists tmp_adminitems;
	create temporary table tmp_adminitems (
		_fk_item varchar(150)
	  , _fk_adminsubject varchar(250)
	  , groupid varchar(100)
	  , itemposition int(11)
	  , isfieldtest bit(1)
	  , isactive bit(1)
	  , blockid varchar(10)
	  , isrequired bit(1)
	  , _fk_testadmin varchar(150)
	  , _fk_strand varchar(150)
	  , groupkey varchar(100)
	  , strandname varchar(150)
	  , irt_a float
	  , irt_b varchar(150)
	  , irt_c float
	  , irt_model varchar(100)
	  , clstring text
	  , version bigint
	);

	drop temporary table if exists tmp_irt;
	create temporary table tmp_irt (
		testitemid	varchar(150)
	  , measuremodel varchar(100)
	  , irt_a float
	  , irt_b varchar(150)
	  , irt_c float
	);


	-- first gather the linked item and adminsubject information	
	insert into tmp_adminitems (_fk_item, _fk_adminsubject, _fk_testadmin, _fk_strand, strandname, version)
	select _fk_item
		 , _fk_adminsubject
		 , tp.testadmin
		 , _fk_strand
		 , tir.ref
		 , s.version
	  from loader_setofitemstrands s
	  join loader_testitemrefs tir on tir.testitemid = s._fk_item 
								  and tir._fk_package = s._fk_package
	  join loader_testpackage tp on tp.packagekey = s._fk_package
	 where tir.refcategory = 'strand' 
	   and s._fk_package = v_testpackagekey;

	-- now gather IRT information from itemscoredimension
	insert into tmp_irt
	select testitemid
		 , measuremodel
		 , max(case when measurementparam = 'a' then measurementvalue else null end) 
		 , avg(case when measurementparam like 'b%' then measurementvalue else null end)
		 , max(case when measurementparam = 'c' then measurementvalue else null end)
	  from loader_itemscoredimension
	 where _fk_package = v_testpackagekey
	group by testitemid, measuremodel;


	-- update tmp_adminitems with irt data
	update tmp_adminitems ai
	  join tmp_irt irt on irt.testitemid = ai._fk_item
	   set ai.irt_model = irt.measuremodel
		 , ai.irt_a = coalesce(irt.irt_a, 1)
		 , ai.irt_b = coalesce(irt.irt_b, -9999)
		 , ai.irt_c = coalesce(irt.irt_c, 0)
	;
  
	-- gather and update remaining data columns
	update tmp_adminitems ai
	  join loader_testformgroupitems l on l.itemid = ai._fk_item
	   set ai.groupid 		= substring_index(formitemgroupid, ':', -1)
		 , ai.itemposition 	= l.groupposition
		 , ai.isfieldtest 	= l.isfieldtest
		 , ai.isactive 		= l.isactive
		 , ai.blockid 		= l.blockid
		 , ai.isrequired    = l.responserequired
		 , ai.groupkey		= concat(substring_index(formitemgroupid, ':', -1), '_', l.blockid)
	 where _fk_package = v_testpackagekey;


	update tmp_adminitems ai
	  join loader_segmentpoolgroupitem sp on sp.groupitemid  = ai._fk_item
	   set ai.groupid 		= sp.itemgroupid 
		 , ai.itemposition 	= sp.groupposition
		 , ai.isfieldtest 	= sp.isfieldtest
		 , ai.isactive 		= sp.isactive
		 , ai.blockid 		= sp.blockid
		 , ai.isrequired    = sp.responserequired
		 , ai.groupkey		= concat(sp.groupitemid, '_', sp.blockid)
	 where _fk_package = v_testpackagekey;


	-- Make string of all content levels, strands, and affinity group membership for each item for fast loading by adaptive algorithm
	update tmp_adminitems ai
	  join loader_segment s on s.segmentid = ai._fk_adminsubject 
						   and itemselection like 'adaptive%'
	   set clstring = makeclstring(ai._fk_adminsubject, ai._fk_item)
	 where _fk_package = v_testpackagekey;
	
	-- select * from tmp_adminitems	

	-- insert items that do not already exists
	insert into tblsetofadminitems (_fk_item, _fk_adminsubject, loadconfig, _fk_strand, _fk_testadmin)
	select _fk_item, _fk_adminsubject, version, _fk_strand, _fk_testadmin
      from tmp_adminitems tmp 
	 where not exists (select 1 
						 from tblsetofadminitems i 
						where i._fk_adminsubject = tmp._fk_adminsubject 
						  and i._fk_item = tmp._fk_item
					);

	-- update item values/property fields
	update tblsetofadminitems i
	  join tmp_adminitems tmp on tmp._fk_adminsubject = i._fk_adminsubject 
						     and tmp._fk_item = i._fk_item
	   set i.groupid 	= tmp.groupid 
		 , i.itemposition = tmp.itemposition
		 , i.isfieldtest  = coalesce(tmp.isfieldtest, 0)
		 , i.isactive 	  = coalesce(tmp.isactive, 1)
		 , i.blockid 	  = tmp.blockid
		 , i.isrequired	  = coalesce(tmp.isrequired, 1)
		 , i.groupkey	  = tmp.groupkey 
		 , i.strandname   = tmp.strandname
		 , i.irt_a 		  = tmp.irt_a
		 , i.irt_b 		  = tmp.irt_b
		 , i.irt_c 		  = tmp.irt_c
		 , i.irt_model	  = tmp.irt_model
		 , i.clstring 	  = tmp.clstring
		 , i.updateconfig = tmp.version;


	-- clean-up
	drop temporary table tmp_adminitems;
	drop temporary table tmp_irt;

end $$

DELIMITER ;

