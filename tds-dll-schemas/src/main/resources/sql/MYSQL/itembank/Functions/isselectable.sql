DELIMITER $$

drop function if exists `isselectable` $$

create function `isselectable` (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			4/1/2014		Sai V. 			Original Code.
*/
	v_testkey varchar(250)
) returns bit
sql security invoker
begin

	declare v_result bit;

    set v_result = coalesce((select 0 
							   from tblsetofadminsubjects
							  where _key = v_testkey 
							    and (virtualtest is not null
									or testid like '%student help%'))
							, 1);

    return v_result;

end $$

DELIMITER ;