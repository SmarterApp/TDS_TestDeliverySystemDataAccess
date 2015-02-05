DELIMITER $$

drop function if exists `_allowanonymoustestee` $$

create function `_allowanonymoustestee`(
/*
DESCRIPTION: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/28/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_clientname varchar(100)
)
returns bit
begin

	declare v_allow bit;
	
    if (exists (select * from _externs where clientname = v_clientname and environment = 'simulation')) then
        return 1;
	end if;

    select ison into v_allow
    from configs.client_systemflags f, externs e
    where e.clientname = v_clientname and f.clientname = v_clientname 
        and auditobject='anonymoustestee';

	return (case v_allow when 1 then 1 else 0 end);

end $$

DELIMITER ;
