DELIMITER $$

drop function if exists `_restorertsaccommodations` $$

create function `_restorertsaccommodations`(
/*
DESCRIPTION:

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/10/2015		Sai V. 			code migration
*/
	v_clientname varchar(100)
)
returns bit
sql security invoker
begin

    declare v_result bit;

    set v_result = (select ison from configs.client_systemflags f, externs e
					 where e.clientname = v_clientname and e.ispracticetest = f.ispracticetest 
					   and f.clientname = e.clientname and f.auditobject = 'restoreaccommodations');

    if (v_result is null) then 
		set v_result = 0;
	end if;

    return v_result;

end $$

DELIMITER ;
