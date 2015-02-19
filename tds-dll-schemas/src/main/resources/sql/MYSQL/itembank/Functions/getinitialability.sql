DELIMITER $$

drop function if exists `getinitialability` $$

create function `getinitialability`(
/*
DESCRIPTION: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/24/2014		Sai V. 			

*/
	v_testkey varchar(250))
returns float
sql security invoker
begin

	declare v_result float;
	
    select startability into v_result
    from tblsetofadminsubjects 
	where _key = v_testkey;

    return v_result;
	
end$$

DELIMITER ;