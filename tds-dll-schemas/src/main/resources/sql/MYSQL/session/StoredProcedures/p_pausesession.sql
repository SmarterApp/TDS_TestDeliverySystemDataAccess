DELIMITER $$

drop procedure if exists `p_pausesession` $$

create procedure `p_pausesession`(
/*
Description:

VERSION 	DATE 			AUTHOR 			COMMENTS
001			01/10/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_sessionkey varbinary(16)
  , v_proctorkey bigint
  , v_browserkey varbinary(16)
  , v_reason varchar(50) -- = 'closed'
  , v_report bit -- = 1
)
proc: begin

	declare v_clientname varchar(100);
    declare v_today datetime(3);	
	declare v_accessdenied varchar(200);
	declare v_procname varchar(100);
	declare v_audit int;
	declare v_host nchar (128);
	declare v_now datetime(3);
	declare v_msg varchar(100);
	declare v_oppkey varbinary(16);

	set v_today = now(3); 
	set v_procname = 'p_pausesession';
    
	set v_clientname = (select clientname from `session` where _key = v_sessionkey);
    set v_accessdenied = validateproctorsession(v_proctorkey, v_sessionkey, v_browserkey);

    if (v_accessdenied is not null) then 
	begin        
        call _logdberror(v_procname, v_accessdenied, v_proctorkey, null, null, null, v_clientname, v_sessionkey);
		call _logdblatency (v_procname, v_today, null, null, null, null, v_sessionkey, v_clientname, null);
        call _returnerror(v_clientname, v_procname, v_accessdenied, null, null, 'validateproctorsession', null);
        leave proc;
    end;
	end if;

	set v_audit = auditsessions(v_clientname);
	set v_host = @@hostname;
	set v_now = now(3);

	if (not(exists(select * from `session` where _key = v_sessionkey))) then 
	begin		
		set v_msg = concat('no such session: ', cast(v_sessionkey as char(50)));

		call _recordsystemerror(v_procname, v_msg, null, null, null, null, null, null, null, null, null);
		call _returnerror (v_clientname, v_procname,'session does not exist', null, null, null, null);
		leave proc;
	end;
	end if;

	-- end the session when pause
	update `session`
	set `status` = 'closed'
	  , datechanged = v_now
	  , dateend = v_now 
	where _key = v_sessionkey;

	if (v_audit <> 0) then
		insert into archive.sessionaudit (_fk_session, dateaccessed, accesstype, hostname, browserkey) 
			values (v_sessionkey, v_now, v_reason, v_host, v_browserkey);
	end if;

	-- account for any dangling test opportunities
	if (auditopportunities(v_clientname) <> 0) then
		insert into archive.opportunityaudit (_fk_testopportunity, dateaccessed, accesstype, _fk_session, hostname, _fk_browser)
		select _key, v_now, 'paused by session', v_sessionkey, v_host, _fk_browser
		from testopportunity 
		where _fk_session = v_sessionkey	
			and `status` in (select `status` from statuscodes where `usage` = 'opportunity' and stage = 'inuse');
	end if;

	drop temporary table if exists tmp_tblopps;
    create temporary table tmp_tblopps(
		oppkey varbinary(16) primary key
	);
    
    insert into tmp_tblopps (oppkey)
    select _key 
	from testopportunity 
	where _fk_session = v_sessionkey 
		and `status` in (select `status` from statuscodes where `usage` = 'opportunity' and stage = 'inuse');

    while (exists (select * from tmp_tblopps)) do
	begin
        select oppkey into  v_oppkey from tmp_tblopps limit 1;
        delete from tmp_tblopps where oppkey = v_oppkey;
        call setopportunitystatus(v_oppkey, 'paused', 1, v_sessionkey, null);
    end;
	end while;

    if (v_report = 1) then 
		select 'closed' as `status`, cast(null as char) as reason;
	end if;

	call _logdblatency (v_procname, v_today, null, null, null, null, v_sessionkey, v_clientname, null);

end$$

DELIMITER ;

