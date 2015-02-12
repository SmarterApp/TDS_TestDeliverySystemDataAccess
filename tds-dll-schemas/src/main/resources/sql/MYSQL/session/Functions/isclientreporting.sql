DELIMITER $$

drop function if exists `isclientreporting` $$

create function `isclientreporting`(
/*
DESCRIPTION: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			07/23/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_clientname varchar(100))
returns int
sql security invoker
begin

	declare v_brokerid varbinary(16);
	declare v_environment varchar(100);
	declare v_flag bit;

    select qabrokerguid, environment
	  into v_brokerid, v_environment
      from  externs e
     where e.clientname = v_clientname;

	-- set default value
    set v_flag = 0;

	select ison into v_flag
      from configs.client_systemflags f
     where f.clientname = v_clientname 
	   and auditobject = 'oppreport';

 --   if (v_flag = 0 or v_brokerid is null or v_environment = 'simulation') then
    if (v_flag = 0  or v_environment = 'simulation') then
        return 0;
    end if;

    return 1;

end $$

DELIMITER ;