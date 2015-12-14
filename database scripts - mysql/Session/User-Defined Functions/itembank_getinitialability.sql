DELIMITER $$

drop function if exists `itembank_getinitialability` $$

create function `itembank_getinitialability`(
/*
DESCRIPTION: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/24/2014		Sai V. 			

*/
	v_testkey varchar(250))
returns float
begin

	declare v_result float;
	
    set v_result = (select tdscore_dev_itembank2012_sandbox.getinitialability(v_testkey));

    return v_result;
	
end$$

DELIMITER ;