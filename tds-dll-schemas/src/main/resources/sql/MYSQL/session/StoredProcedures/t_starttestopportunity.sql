DELIMITER $$

drop procedure if exists `t_starttestopportunity` $$

create procedure `t_starttestopportunity`(
/*
Description: perform all actions necessary to start or restart a student test opportunity.
		-- returns: on failure: status = 'denied', 'failed', reason = message appropriate for user
		-- 			on success: status = 'start', reason = null

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/06/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_oppkey varbinary(16)
  , v_sessionkey varbinarY(16) -- = null
  , v_browserid varbinary(16) -- = null
  , v_formkeylist text -- = null
  , v_debug bit -- = 0
)
sql security invoker
proc: begin
	
    declare v_interfacetimeout int;      	-- pass this along to the caller as how many minutes of inactivity before the interface should timeout
	declare v_requestinterfacetimeout int;  -- pass this along to the caller as how many minutes of inactivity after print request sent before the interface should timeout
	declare v_datestarted datetime(3);
	declare v_datechanged datetime(3);		-- time of last opportunity activity e.g. 'pause' event
	declare v_rcnt int;						-- restart count
	declare v_now datetime(3);
    declare v_ability float;
    declare v_excludeitemtypes text;
	declare v_fromtime datetime(3);			-- max of v_fromtimes
	declare v_delay int;					-- grace period in minutes on setting restart, limiting testee access to previous items
	declare v_gprestarts int;				-- number of restarts within grace period
	declare v_status varchar(50);
    declare v_sessiontype int;
	declare v_error varchar(200);
    declare v_reason text;
    declare v_testkey varchar(250);
    declare v_testid varchar(200);
    declare v_clientname varchar(100);
	declare v_query text; 
	declare v_operationallength int; 		-- this value only valid for adaptive tests (virtual tests and fixedform this is unreliable)	
	declare v_testlength int;        		-- this value should be reliable
    declare v_removeunanswered bit;
	declare v_procname varchar(100);
 	
	--  we have to take the most recent of last item generated, last item responded, and datechanged to determine if 
	--  testee has met the delay rule that permits picking up without a penalty
	set v_now = now(3);
	set v_procname = 't_starttestopportunity';
        
	select `status`, datestarted, datechanged, `restart`, graceperiodrestarts, maxitems, _efk_adminsubject, clientname, _efk_testid 
	into v_status, v_datestarted, v_datechanged, v_rcnt, v_gprestarts, v_testlength, v_testkey, v_clientname, v_testid
	from testopportunity
    where _key = v_oppkey;

	/*
		1. check that test opportunity is in 'approved' state, that session keys match, (and no other opportunities are in 'started' state?)
		2. if datestarted is not null, set restart as appropriate and apply grace period to unanswered items
		3. else if datestarted is null, then call createresponseset and set datestarted
		4. set testopportunity status to 'started'
	*/
    if (v_sessionkey is not null and v_browserid is not null) then 
	begin
        call _validatetesteeaccessproc(v_oppkey, v_sessionkey, v_browserid, 1, v_error /*output*/);
        if (v_error is not null) then
		begin
			if (v_debug = 1) then select 'condition 1. exit'; end if;
			-- select 'denied' as [status], v_error as reason, '_validatetesteeaccess' as [context], null as [argstring], null as [delimiter];
            call _returnerror(v_clientname, v_procname, v_error, null, v_oppkey, '_validatetesteeaccess', 'denied');
            leave proc;
        end;
		end if;
    end;
	end if;

    -- 2/2012: get sessiontype so as to exclude data-entry from pause rule
    set v_sessiontype = (select sessiontype from `session` where _key = v_sessionkey);
	set v_removeunanswered = (select deleteunanswereditems from configs.client_testproperties where clientname = v_clientname and testid = v_testid);
	set v_operationallength = (select maxitems from itembank.tblsetofadminsubjects where _key = v_testkey);
    
	select opprestart, interfacetimeout, requestinterfacetimeout 
	into v_delay, v_interfacetimeout, v_requestinterfacetimeout
    from timelimits 
	where _efk_testid = v_testid and clientname = v_clientname;

	if (v_delay is null) then 
		if (v_debug = 1) then select 'condition 2'; end if;
		select opprestart, interfacetimeout, requestinterfacetimeout 
		into v_delay, v_interfacetimeout, v_requestinterfacetimeout
		from timelimits  
		where _efk_testid is null and clientname = v_clientname;
	end if;

	if (v_delay is null) then set v_delay = 1; end if;

	if (v_status not in ('approved')) then	-- this cannot be checked by _validatetesteeaccess
		if (v_debug = 1) then select 'condition 3.exit'; end if;
		call _returnerror(v_clientname, v_procname, 'test start/restart not approved by test administrator', null, v_oppkey, 't_starttestopportunity', 'denied');
		leave proc;
	end if;

    call _getinitialability(v_oppkey, 0, v_ability /*output*/);
	
	if (v_datestarted is null) then 
	begin
		-- 1/31/2009: _initializeopportunity now handles all data initialization including item, field test, and opportunity-level
		if (v_debug = 1) then select '_initializeopportunity', hex(v_oppkey), v_formkeylist; end if;
		call _initializeopportunity(v_oppkey, v_testlength /*output*/,  v_reason /*output*/, v_formkeylist);
		if (v_debug = 1) then select '_initializeopportunity', v_testlength,  v_reason; end if;

        if (v_reason is not null) then
			if (v_debug = 1) then select 'condition 4.exit'; end if;
            call _logdberror(v_procname, v_reason, null, null, null, v_oppkey, v_clientname, null);
			call _returnerror(v_clientname, v_procname, v_reason, null, v_oppkey, 't_starttestopportunity', 'failed');
            leave proc;
        end if;

        insert into archive.opportunityaudit(_fk_testopportunity, dateaccessed, _fk_session, hostname, accesstype, _fk_browser)
			 values (v_oppkey, v_now, v_sessionkey, @@hostname, 'started', v_browserid);

		select 'started' as `status`
			 , bigtoint(0) as restart
			 , v_testlength as testlength
             , cast(null as char) as reason
			 , v_interfacetimeout as interfacetimeout
			 , v_delay as opprestart
             , v_ability as initialability
			 , v_excludeitemtypes as excludeitemtypes
             , bigtoint(120) as contentloadtimeout
			 , v_requestinterfacetimeout as requestinterfacetimeout
             , bigtoint(1) as startposition
			 , prefetch
			 , validatecompleteness
			 , scorebytds(v_clientname, v_testid) as scorebytds
             , (select count(*) from testopportunitysegment where _fk_testopportunity = v_oppkey) as segments
        from configs.client_testproperties 
		where clientname = v_clientname and testid = v_testid;
        
        call _logdblatency(v_procname, v_now, null, null, null, v_oppkey, null, v_clientname, null);

		leave proc;
	end;
	end if;

	set v_fromtime = _testopplastactivity(v_oppkey);

	if (timestampdiff(minute, v_fromtime, v_now) < v_delay) then
		set v_gprestarts = v_gprestarts + 1;
	end if;

	-- note: to handle restarts within the grace period (v_delay), we could just leave the response restarts value
	--   as they are, but only if we also leave the opportunity restart as well. we choose not to do this
	--   because we would lose a recording of the restart event if we don't update the testopportunity.restart value for each login.
	update testopportunity 
	set prevstatus = `status`
	  , `status` = 'started'
	  , restart = v_rcnt + 1
	  , daterestarted = v_now
	  , datechanged = v_now
	  , graceperiodrestarts = v_gprestarts 
	  , maxitems = v_testlength
	  , waitingforsegment = null
	where _key = v_oppkey;
	
    insert into archive.opportunityaudit(_fk_testopportunity, dateaccessed, _fk_session, hostname, accesstype, _fk_browser)
		 values (v_oppkey, v_now, v_sessionkey, @@hostname, concat('restart ', cast(v_rcnt + 1 as char(10))), v_browserid);

    -- 2/2012: exempt data-entry session from pause-time rule
    if (v_sessiontype = 1) then
        update testeeresponse 
		set opportunityrestart = v_rcnt + 1
        where _fk_testopportunity = v_oppkey;
	elseif (timestampdiff(minute, v_fromtime, v_now) < v_delay) then	-- then every item from the previous login is available in this login
		update testeeresponse 
		set opportunityrestart = v_rcnt + 1 
		where _fk_testopportunity = v_oppkey
			and opportunityrestart = v_rcnt;
    elseif (v_removeunanswered = 1) then
		call _removeunanswered(v_oppkey, 0);
	end if;
	
    set v_rcnt = v_rcnt + 1;      -- careful! this assignment required to call the procedure below

	call _setgraceperiods(v_oppkey, v_rcnt, 0);

	call _unfinishedresponsepages(v_oppkey, v_rcnt, 1);

	select 'started' as `status`
		 , v_rcnt as restart
		 , v_testlength as testlength
		 , cast(null as char) as reason
		 , v_interfacetimeout as interfacetimeout
		 , v_delay as opprestart
		 , v_ability as initialability
		 , v_excludeitemtypes as excludeitemtypes
		 , bigtoint(120) as contentloadtimeout
		 , v_requestinterfacetimeout as requestinterfacetimeout
		 , prefetch
		 , validatecompleteness
		 , scorebytds(v_clientname, v_testid) as scorebytds
		 , resumeitemposition(v_oppkey, v_rcnt) as startposition
		 , (select count(*) from testopportunitysegment where _fk_testopportunity = v_oppkey) as segments
    from configs.client_testproperties 
	where clientname = v_clientname and testid = v_testid;
            
    call _logdblatency(v_procname, v_now, null, null, null, v_oppkey, null, v_clientname, null);

end $$

DELIMITER ;