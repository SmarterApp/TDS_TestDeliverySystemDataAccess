DELIMITER $$

drop procedure if exists `load_test` $$

create procedure `load_test` (
/*
Description: Entry point for loading itembank tables from the testpackage file in xml format.

VERSION 	DATE 			AUTHOR 			COMMENTS
001			6/30/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
)
begin

	-- declare variables
	insert into test(clientname, testid, _efk_subject) 
	select tp.publisher, tp.testname, tpp.propvalue
	  from loader_testpackage tp
	  join loader_testpackageproperties	tpp on tpp._fk_package = tp.packagekey
	 where tpp.propname = 'subject'
	   and tp.packagekey = v_testpackagekey
	   and not exists (select 1
						 from test t
						where t.clientname = tp.publisher
						  and t.testid = tp.testname	
					); 


end $$

DELIMITER ;