DELIMITER $$

drop procedure if exists `load_testgrades` $$

create procedure `load_testgrades` (
/*
Description: Entry point for loading itembank tables from the testpackage file in xml format.

VERSION 	DATE 			AUTHOR 			COMMENTS
001			6/30/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
)
begin

	-- declare variables
	insert into testgrades(clientname, testid, reportinggrade) 
	select tp.publisher, tp.testname, tpp.propvalue
	  from loader_testpackage tp
	  join loader_testpackageproperties	tpp on tpp._fk_package = tp.packagekey
	 where tpp.propname = 'grade'
	   and tp.packagekey = v_testpackagekey
	   and not exists (select 1
						 from testgrades tg
						where tg.clientname = tp.publisher
						  and tg.testid = tp.testname	
					); 


end $$

DELIMITER ;