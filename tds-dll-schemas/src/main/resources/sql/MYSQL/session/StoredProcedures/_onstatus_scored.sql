DELIMITER $$

drop procedure if exists `_onstatus_scored` $$

create procedure `_onstatus_scored`(
/*
DESCRIPTION: perform whatever actions are required when a test opportunity status changes to 'scored'

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/12/2015		Sai V. 			Code Migration
*/
	v_oppkey varbinary(16)
)
sql security invoker
proc: begin

    if (isxmlon(v_oppkey) = 1) then
        call submitqareport(v_oppkey, 'submitted', '_onstatus_scored');
	end if;

end $$

DELIMITER ;