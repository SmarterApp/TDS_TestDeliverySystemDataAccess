DELIMITER $$

drop function if exists `isxmlon` $$

create function `isxmlon`(
/*
DESCRIPTION: is xml reporting turned on?

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/10/2015		Sai V. 			Code Migration
*/
	v_oppkey varbinary(16)
)
returns int
sql security invoker
begin

	declare v_flag bit;	
    declare v_clientname varchar(100);
	declare v_env varchar(100);
	declare v_guid varbinary(16);

    select clientname, environment 
	into v_clientname, v_env
	from testopportunity where _key = v_oppkey;

	set v_flag = (select coalesce(ison, 0)
					from configs.client_systemflags f, externs e
				   where e.clientname = v_clientname and f.clientname = v_clientname
					 and e.ispracticetest = f.ispracticetest and auditobject='oppreport');
    
--     set v_guid = (select qabrokerguid 
-- 					from externs where clientname = v_clientname and environment = v_env);

	return (case when v_flag = 0 /*or v_guid is null*/ then 0 else 1 end); 

end $$

DELIMITER ;
