DELIMITER $$

drop procedure if exists `_canopenexistingopportunity` $$

create procedure `_canopenexistingopportunity`(
/*
Description: determine if testee can open an existing opportunity and, if so, which one

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/10/2015		Sai V. 			
*/
	v_client varchar(100)
  , v_testee bigint
  , v_testid varchar(255)
  , v_sessionid varbinary(16)
  , v_maxopportunities int
  , out v_number int
  , out v_msg varchar(100)
  , v_debug bit	-- = 0
)
sql security invoker
proc: begin

	declare v_lastopp int;
	declare v_ocnt int;
	declare v_laststatus varchar(50);
	declare v_lastsession varbinary(16);
	declare v_lastsessionstatus varchar(50);
	declare v_lastsessionend datetime(3);
    declare v_lastsessiontype int;
    declare v_thissessiontype int;
	declare v_datechanged datetime(3);
	declare v_datestarted datetime(3);
	declare v_today datetime(3);
	
	set v_today = now(3);
	set v_number = 0;	-- default is fail
	set v_msg = null;

	-- first check to see if there are any existing opportunities to open
	set v_ocnt = (select count(*) from testopportunity  
				   where _efk_testee = v_testee and _efk_testid = v_testid and clientname = v_client and datedeleted is null);

	if (v_ocnt = 0) then -- it is expected that the caller will check for opening a first opportunity separately
        if (v_debug = 1) then select 'open existing exit 1'; end if;
		leave proc;
	end if;
		
    --  pick up info on the most recent opportunity
	select opportunity, `status`, _fk_session, datechanged, datestarted
	into v_lastopp, v_laststatus, v_lastsession, v_datechanged, v_datestarted
	from testopportunity
    where _efk_testee = v_testee and _efk_testid = v_testid and opportunity = v_ocnt and clientname = v_client and datedeleted is null;

	-- last opportunity terminated normally
	if (v_laststatus in (select `status` from statuscodes where `usage` = 'opportunity' and stage = 'closed')) then
        if (v_debug = 1) then select 'open existing exit 2'; end if;
		leave proc;	
	end if;

    set v_lastsessiontype = (select sessiontype from `session` where _key = v_lastsession);
    set v_thissessiontype = (select sessiontype from `session` where _key = v_sessionid);

    if (v_lastsessiontype <> v_thissessiontype) then
  		call _formatmessage(v_client, 'enu', '_canopentestopportunity', 'you must continue the test in the same type of session it was started in.', v_msg /*output*/, null, ',', null, null);
        leave proc;
    end if;

	-- final security check
	-- if opportunity is not in use, then we can open it
	if (v_laststatus in (select `status` from statuscodes where `usage` = 'opportunity' and stage = 'inactive')) then
		set v_number = v_lastopp;
        if (v_debug = 1) then select 'open existing exit 3'; end if;
		leave proc;
	end if;
	
    if (v_sessionid is null) then
        if (v_debug = 1) then select 'open existing exit 6'; end if;
        leave proc;
    end if;

	select `status`, dateend into v_lastsessionstatus, v_lastsessionend
    from `session`
    where _key = v_lastsession;

	if ((timestampdiff(day, v_datechanged, v_today) >= 1) or (v_lastsession = v_sessionid) or (v_lastsessionstatus = 'closed' or v_today > v_lastsessionend)) then
		set v_number = v_lastopp;
		if (v_debug = 1) then select 'open existing exit 4'; end if;
		leave proc;
	end if;

	if (v_lastsession = v_sessionid) then
		set v_number = v_lastopp;
		if (v_debug = 1) then select 'open existing exit 5'; end if;
		leave proc;
	end if;

	set v_number = 0;
	call _formatmessage(v_client, 'enu', '_canopentestopportunity', 'current opportunity is active', v_msg  /*output*/, null, ',', null, null);
	 
end $$

DELIMITER ;