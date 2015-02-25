DELIMITER $$

drop procedure if exists `load_testpackagerecord` $$

create procedure `load_testpackagerecord` (
/*
 * aphilip - 02/24/2015 - This is needed for simulation 
 */
	v_xml  longtext,
	v_testkey varchar(350)
)
begin
	declare v_simtestpackagekey varbinary(16)	;
	set v_simtestpackagekey = unhex(REPLACE(UUID(), '-', ''));
	
	insert into tbltestpackage (_key, testpackage) 
	values (v_simtestpackagekey, v_xml) ;
	
	insert into tbladminsubjecttestpackage (_fk_adminsubject, _fk_testpackage, dateloaded) 
	values (v_testkey, v_simtestpackagekey, NOW()) ;
	
end $$

DELIMITER ;
