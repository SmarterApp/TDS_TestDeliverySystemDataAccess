DELIMITER $$

drop procedure if exists `_canopennewopportunity` $$

create procedure `_canopennewopportunity`(
/*
Description: determine if testee can open a new opportunity and, if so, which one
			-- on fail returns v_number = 0 and v_msg = <why failed>
			-- on success returns v_number > 0
			-- does not do session availability checks

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/10/2015		Sai V. 			
*/
	v_clientname varchar(100)
  , v_testee bigint
  , v_testid varchar(255)
  , v_maxopportunities int
  , v_delaydays int 
  , out v_number int
  , out v_msg varchar(100)
  , v_sessionid varbinary(16)
  , v_debug bit	-- = 0
)
sql security invoker
proc: begin

	declare v_lastopp int;
    declare v_lastoppkey varbinary(16);
	declare v_ocnt int;
	declare v_environment varchar(50);
	declare v_laststatus varchar(50);
	declare v_datestarted datetime(3);
	declare v_datecompleted datetime(3);
	declare v_today datetime(3);
	declare v_arg varchar(100);

	set v_today = now(3);
	set v_number = 0;	-- default is fail
	set v_msg = null;

    set v_environment = (select environment from _externs where clientname = v_clientname);  -- if simulation, then special rules apply

	-- first check to see if there are any existing opportunities to open
	set v_ocnt = (select count(*) from testopportunity
				   where _efk_testee = v_testee and _efk_testid = v_testid and clientname = v_clientname and datedeleted is null);
    
	if (v_ocnt = 0 ) then
		if (v_ocnt < v_maxopportunities or v_environment = 'simulation') then 
			set v_number = 1;
		elseif (v_environment <> 'simulation') then
			call _formatmessage(v_clientname, 'enu', '_canopentestopportunity', 'no opportunities are available for this test', v_msg /*output*/, null, ',', null, null);
		end if;
		leave proc;
	end if;
	
    set v_lastopp = (select max(opportunity) from testopportunity 
					  where _efk_testee = v_testee and _efk_testid = v_testid and opportunity = v_ocnt and clientname = v_clientname and datedeleted is null);

    --  pick up info on the most recent opportunity
	select `status`, datestarted, datecompleted, _key
	into v_laststatus, v_datestarted, v_datecompleted, v_lastoppkey
	from testopportunity
    where _efk_testee = v_testee and _efk_testid = v_testid and opportunity = v_lastopp and clientname = v_clientname and datedeleted is null;

	-- last opportunity terminated normally?
	if (v_laststatus in (select `status` from statuscodes where `usage` = 'opportunity' and stage = 'closed')) then
    begin
        if (v_environment = 'simulation') then
            set v_number = v_lastopp + 1;
            leave proc;    -- no further constraints apply to simulation environment
        end if;

		if ((v_lastopp < v_maxopportunities) and (v_datecompleted is null or (timestampdiff(day, v_datecompleted, v_today) >= v_delaydays))) then
			set v_number = v_lastopp + 1;
		elseif (v_lastopp >= v_maxopportunities) then   
			call _formatmessage(v_clientname, 'enu', '_canopentestopportunity', 'all opportunities have been used for this test', v_msg /*output*/, null, ',', null, null);
		else
            set v_arg = date_add(v_datecompleted, interval v_delaydays day);
            call _formatmessage(v_clientname, 'enu', '_canopentestopportunity', 'your next test opportunity is not yet available.', v_msg /*output*/, v_arg, ',', null, null);
        end if;
		leave proc;	
	end;
	end if;

	-- can't open a new opportunity. probably because the existing one must be completed first
	leave proc;

end $$

DELIMITER ;