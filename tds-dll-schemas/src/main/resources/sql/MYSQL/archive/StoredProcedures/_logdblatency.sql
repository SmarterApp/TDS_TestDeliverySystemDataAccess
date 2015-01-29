DELIMITER $$

drop procedure if exists _logdblatency $$

create procedure _logdblatency (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/26/2015		Sai V. 			--
*/
	v_procname varchar(200)
  , v_starttime datetime(3)
)
begin

	declare v_difftime datetime(3);
	declare v_duration int;
	
	set v_duration = TIMESTAMPDIFF(microsecond, v_starttime, now(3))/1000; -- convert into milli seconds
	
	if (v_duration < 0) then 
		set v_duration = 0; 
	end if;

	set v_difftime = now(3) - v_starttime;
        
	insert into _dblatency (duration, starttime, difftime, procname, clientname) 
		 values (v_duration, v_starttime, v_difftime, v_procname, 'system');

end $$

DELIMITER ;