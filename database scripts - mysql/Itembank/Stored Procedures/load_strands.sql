DELIMITER $$

drop procedure if exists `load_strands` $$

create procedure `load_strands` (
/*
Description: Load strands from loader_testblueprint

VERSION 	DATE 			AUTHOR 			COMMENTS
001			4/3/2014		Sai V. 			Original Code.
*/
	v_testpackagekey varchar(350)
)
begin
	
	-- strands do not have a parentid and are always at level 1 of the content hierarchy
	-- content has parentid and parent is equivalent to strandid 
	-- 		treelevel value for content is determined by the number of seperators '|'    
	insert into tblstrand (_fk_subject, `name`, _fk_parent, _key, _fk_client, treelevel, loadconfig) 
	select distinct subjectkey
		 , bpelementname
		 , case when parentid is null or parentid = '' 
				then null 
				else parentid 
		   end
		 , bpelementid
		 , clientkey
		 , treelevel
		 , version
	  from loader_testblueprint tbp
	  join loader_testpackage tp on tp.packagekey = tbp._fk_package	
	 where elementtype in ('strand', 'contentlevel')
	   and bpelementid not in (select _key from tblstrand)
	   and tbp._fk_package = v_testpackagekey;

end $$

DELIMITER ;