DELIMITER $$

drop function if exists `_allowproctorlesssessions` $$

create function `_allowproctorlesssessions`(
/*
DESCRIPTION: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/28/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_clientname varchar(100)
)
returns bit
sql security invoker
begin

	declare v_allow bit;	
	
    select ison into v_allow 
	from configs.client_systemflags 
	where clientname = v_clientname 
		and auditobject ='proctorless';

	return (case v_allow when 1 then 1 else 0 end);

end $$

DELIMITER ;