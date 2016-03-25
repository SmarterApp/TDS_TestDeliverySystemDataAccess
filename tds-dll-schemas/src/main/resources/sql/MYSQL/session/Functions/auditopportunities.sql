DELIMITER $$

drop function if exists `auditopportunities` $$

create function `auditopportunities`(
/*
DESCRIPTION: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/28/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_clientname varchar(100)
)
returns bit
begin

	declare v_flag bit;	

    if (exists (select * from _externs where clientname = v_clientname and environment = 'simulation')) then
        return 0;
	end if;

	select ison into v_flag
    from configs.client_systemflags f, externs e 
    where e.clientname=v_clientname and f.clientname = v_clientname 
        and e.ispracticetest = f.ispracticetest and auditobject='opportunities';

	return (case when v_flag is null or v_flag = 0 then 0 else 1 end); 


end $$

DELIMITER ;
