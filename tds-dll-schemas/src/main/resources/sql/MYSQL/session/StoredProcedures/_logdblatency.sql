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
  , v_userkey bigint -- = null
  , v_checkaudit bit -- = 1
  , v_n int -- = null, 
  , v_testoppkey varbinary(16) -- = null
  , v_sessionkey varbinary(16) -- = null
  , v_clientname varchar(100) -- = null
  , v_comment varchar(500) -- = null
)
proc: begin

    declare v_now datetime(3);
	declare v_difftime datetime(3);
    declare v_secs int;
    declare v_duration int;
	declare v_loginterval int;
	declare v_latencycutofftime int;

	set v_loginterval = 60;			
	set v_latencycutofftime = 30000;	-- only log those procedure that have latency greater than this number

    set v_now = now(3);
    set v_secs = extract(second from v_now);
	set v_duration = TIMESTAMPDIFF(microsecond, v_starttime, v_now)/1000; -- convert into milli seconds

    -- if (v_secs > 0 and v_checkaudit = 1) then leave proc; end if;
	if (v_duration < 0) then set v_duration = 0; end if;

	/*to avoid additional overhead during load testing - adding code to sample out latency logging*/
	-- if (v_duration > v_latencycutofftime or (v_secs % coalesce(v_loginterval, v_secs)) <> 0) then leave proc; end if;
	if (v_duration < 6000) then leave proc; end if; -- do not log if the latency is less than 3 secs

    if (v_checkaudit = 0 or 1 = 1) then 
	begin

--         if (v_clientname is null and v_testoppkey is not null)  then
--             set v_clientname = (select clientname from testopportunity where _key = v_testoppkey);
--         elseif (v_clientname is null and v_sessionkey is not null) then
--             set v_clientname = (select clientname from `session` where _key = v_sessionkey);
-- 		end if;
-- 
--         set v_difftime = v_now - v_starttime;
        
        insert into archive._dblatency (userkey, duration, starttime, difftime, procname, n, _fk_testopportunity, _fk_session, clientname, comment) 
			 values (v_userkey, v_duration, v_starttime, v_difftime, v_procname, v_n, v_testoppkey, v_sessionkey, v_clientname, v_comment);

	end;
	end if;

end $$

DELIMITER ;