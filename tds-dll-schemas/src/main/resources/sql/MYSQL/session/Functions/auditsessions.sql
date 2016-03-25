DELIMITER $$

drop function if exists `auditsessions` $$

create function `auditsessions`(
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

	select ison 
	into v_flag
    from configs.client_systemflags f, externs e 
    where e.clientname = v_clientname and f.clientname = v_clientname 
        and e.ispracticetest = f.ispracticetest and auditobject = 'sessions';

	return (case when v_flag is null or v_flag = 0 then 0 else 1 end); 


end $$

DELIMITER ;
