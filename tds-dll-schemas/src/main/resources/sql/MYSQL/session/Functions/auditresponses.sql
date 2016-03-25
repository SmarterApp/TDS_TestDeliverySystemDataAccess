DELIMITER $$

drop function if exists `auditresponses` $$

create function `auditresponses`(
/*
DESCRIPTION: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/26/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_clientname varchar(100)
)
returns int
begin
    
	declare v_flag bit;	

	select ison into v_flag
    from configs.client_systemflags f, externs e 
    where e.clientname = v_clientname 
		and f.clientname = v_clientname 
        and e.ispracticetest = f.ispracticetest 
		and auditobject = 'responses';

	return case when v_flag is null or v_flag = 0 then 0 else 1 end; 

end $$

DELIMITER ;