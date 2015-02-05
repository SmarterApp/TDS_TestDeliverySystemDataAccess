DELIMITER $$

drop procedure if exists p_opensession $$

create procedure p_opensession (
/*
Description: opens an existing session setting its status and end date as needed. 
			-- also pauses all open test opportunities

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/29/2015		Sai V. 			Converted code from T-SQL to MySQL
*/
	v_sessionkey varbinary(16)
  , v_numhours int -- = 12
  , v_suppressreport bit -- = 0
  , v_browserkey varbinary(16) -- = null
)
proc: begin
	
    declare v_clientname varchar(100);
	declare v_msg varchar(100);
	declare v_enddate, v_now, v_sesend, v_sesstart, v_createdate datetime(3);

	set v_now = now(3);
	set v_enddate = date_add(v_now, interval v_numhours hour);

    if (v_browserkey is null) then 
		set v_browserkey = unhex(REPLACE(UUID(), '-', ''));     -- this should only happen when 'virtual proctor session' is created for practice tests
	end if;

	select clientname, datecreated, dateend, datebegin 
	into v_clientname, v_createdate, v_sesend, v_sesstart
    from `session`
	where _key = v_sessionkey;
	
	if (v_createdate is null) then 
	begin
		set v_msg = concat('no such session: ', v_sessionkey);		
		call _recordsystemerror('p_opensession', v_msg, null, null, null, null, null, null, null, null, null);
		-- select 'failed' as [status], 'no such session' as reason, 'p_opensession' as [context], null as [argstring], null as [delimiter];	
		call _returnerror(v_clientname, 'p_opensession','no such session', null, null, null, null);
		leave proc;
	end;
	end if;

	-- make sure begin/end dates are reliable. change end date if null or if earlier than new computed enddate.
	if (v_sesend is not null and v_enddate < v_sesend) then 
		set v_enddate = v_sesend;	-- update uses v_enddate, not v_sesend
	end if;

	-- change start date if null or later than now
	if (v_sesstart is null or v_sesstart > v_now) then 
		set v_sesstart = v_now;
	end if;

	update `session`
	set `status` = 'open'
	  , datechanged = v_now
	  , datevisited = v_now
	  , dateend = v_enddate
	  , datebegin = v_sesstart
	  , _fk_browser = v_browserkey
    where _key = v_sessionkey;

	if (auditsessions(v_clientname) = 1) then
        insert into archive.sessionaudit(_fk_session, dateaccessed, accesstype, hostname, browserkey) 
			 values (v_sessionkey, v_now, 'open', @@hostname, v_browserkey);
	end if;

	if (v_suppressreport = 0) then 
		select 'open' as `status`,  cast(null as char) as reason;
	end if;

    call _logdblatency('p_opensession', v_now, null, 1, null, null, v_sessionkey, v_clientname, null);

end $$

DELIMITER ;