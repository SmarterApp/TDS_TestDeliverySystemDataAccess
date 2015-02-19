DELIMITER $$

drop procedure if exists `submitqareport` $$

create procedure `submitqareport`(
/*
Description: generates the xml data and puts it into the pipeline for reporting

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/10/2014		Sai V. 			Code Migration
*/
	v_oppkey varbinary(16)
  , v_statuschange varchar(50) -- = 'submitted'
  , v_procname varchar(100) -- = null
)
sql security invoker
proc: begin

    declare v_status varchar(50);

    set v_status = (select `status` from testopportunity where _key = v_oppkey);
        
    if (isxmlon(v_oppkey) = 0 or v_status in ('submitted', 'reported')) then 
        select 'success' as status, cast(null as char) as reason;
        leave proc;
    end if;

	insert into qareportqueue(_fk_testopportunity, changestatus, dateentered)
		 values (v_oppkey, v_statuschange, now(3));

    call _logdblatency('submitqareport', now(3), null, 0, null, v_oppkey, null, null, null);
	
end $$

DELIMITER ;