DELIMITER $$

drop function if exists `auditscores` $$

create function `auditscores`(
/*
DESCRIPTION:

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/2/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_clientname varchar(100))
returns int
begin

	declare v_flag bit;	
	select ison into v_flag 
    from tdsconfigs_client_systemflags f
		 join externs e on e.ispracticetest = f.ispracticetest
    where e.clientname = v_clientname 
		and f.clientname = v_clientname 
		and auditobject = 'scores';

	return case when v_flag is null or v_flag = 0 then 0 else 1 end; 

end$$

DELIMITER ;