DELIMITER $$

drop procedure if exists `_validatetesteeaccessproc` $$

create procedure `_validatetesteeaccessproc`(
/*
Description:

VERSION 	DATE 			AUTHOR 			COMMENTS
001			01/10/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_testoppkey varbinary(16)
  , v_session varbinary(16)
  , v_browserid varbinary(16)
  , v_checksession bit
  , out v_message varchar(200))
proc: begin

	declare v_reslen int;
	declare v_sessionkey varbinary(16);
	declare v_browserkey varbinary(16);
    declare v_proctor bigint;
    declare v_sessionbrowser varbinary(16);

	declare v_sessionstatus varchar(255);
	declare v_datebegin datetime(3);
	declare v_dateend datetime(3);
    declare v_datevisited datetime(3);
    declare v_clientname varchar(100);
    declare v_checkin int;

	declare v_now datetime(3);
	-- integrity check: make sure that testee opportunity attributes match those in table testopportunity
	-- unlike getitems and insertitems, there is no provision for suppressing the test status.

	-- allow anything in development environment

    if (exists (select * from _externs e, testopportunity o 
                where o._key = v_testoppkey and e.clientname = o.clientname and e.environment in ('Development', 'SIMULATION'))) then
		set v_checksession = 0;
	end if;

	set v_now = now(3);

	select _fk_session, _fk_browser into v_sessionkey, v_browserkey
	from testopportunity  
	where _key = v_testoppkey;
		
    if (v_browserkey <> v_browserid) then
		set v_message = 'Access violation: System access denied';				            		
        leave proc;
	end if;

	if (v_sessionkey is null or v_session is null or v_sessionkey <> v_session) then
		set v_message = 'The session keys do not match; please consult your test administrator';
        leave proc;
	end if;

    if (v_checksession = 1) then
        select `status`, datebegin, dateend, datevisited, clientname, _efk_proctor, _fk_browser
		into v_sessionstatus, v_datebegin, v_dateend, v_datevisited, v_clientname, v_proctor, v_sessionbrowser
        from `session`
        where _key = v_session;
	end if;

	-- -5 minutes is a fudge factor, not a hard-coded time limit
    if (v_sessionstatus <> 'open' or v_now < date_add(v_datebegin, interval -5 minute) or v_now > v_dateend) then
		set v_message = 'The session is not available for testing, please check with your test administrator.';			            
        leave proc;
	end if;

	if (v_proctor is null) then -- then this is a proctorless session so proctor 'check in' is impossible
		leave proc;
	end if;

    -- if the ta is awol, then close the session and deny testee access
    select tacheckintime into v_checkin 
	from timelimits 
	where clientname = v_clientname and _efk_testid is null;

	if (v_checkin is not null and v_checkin > 0 and date_add(v_datevisited, interval v_checkin minute) < v_now) then
		insert into sessionaudit (_fk_session, accesstype, browserkey, dateaccessed)
        select v_session, 'TACheckin TIMEOUT', v_sessionbrowser, now(3);

        call p_pausesession(v_session, v_proctor, v_sessionbrowser);

		set v_message = 'The session is not available for testing, please check with your test administrator.';
        leave proc;
	end if;

end$$

DELIMITER ;

