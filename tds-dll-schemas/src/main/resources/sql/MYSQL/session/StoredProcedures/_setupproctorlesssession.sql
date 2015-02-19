DELIMITER $$

drop procedure if exists _setupproctorlesssession $$

create procedure _setupproctorlesssession (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/29/2015		Sai V. 			Converted code from T-SQL to MySQL
*/
	v_clientname varchar(100)
  , out v_sessionkey varbinary(16)
  , out v_sessionid varchar(50)
)
proc: begin

	declare v_environ varchar(100);
	declare v_status  varchar(50);
	declare v_enddate datetime(3);
	declare v_applock int;
    declare v_resourcename varchar(200);
	declare v_consume int;

	declare exit handler for sqlexception
	begin
		rollback;
		call _recordsystemerror ('_setupproctorlesssession', 'mysql exit handler: attempt to set up proctorless failed', null, null, null, null, null, null, null, null, null);
	end;

	-- only allow when anonymous testee login permitted
	if (_allowanonymoustestee(v_clientname) = 0) then
		call _recordsystemerror ('_setupproctorlesssession', 'attempt to set up proctorless session forbidden', null, null, null, null, null, null, null, null, null);
		leave proc;
	end if;

	set v_sessionid = 'guest session';
	set v_environ = (select environment from externs where clientname = v_clientname);
	
	drop temporary table if exists tmp_tbltests;
	create temporary table  tmp_tbltests (
		testkey varchar(250)
	  , testid varchar(200)
	) engine = memory;

    insert into tmp_tbltests (testkey, testid) 
	-- from getactivetests(v_clientname, 0) 
    select distinct m.testkey, p.testid
    from configs.client_testwindow w, configs.client_testmode m, configs.client_testproperties p
        , _externs e , itembank.tblsetofadminsubjects bank    -- make sure the itembank we are pointing to coincides with tdsconfigs' data
    where p.clientname = v_clientname and e.clientname = v_clientname
        and w.clientname = v_clientname and w.testid = p.testid and (e.environment = 'simulation' or now(3) between w.startdate and w.enddate)
        and m.clientname = v_clientname and m.testid = p.testid
        and (m.sessiontype = -1 or m.sessiontype = 0 /*v_sessiontype*/) and (w.sessiontype = -1 or w.sessiontype = 0 /*v_sessiontype*/)
        and isselectable = 1
        and bank._key = m.testkey;

 
	select _key, `status`, dateend  
	into v_sessionkey, v_status, v_enddate
    from `session` 
	where clientname = v_clientname and sessionid = v_sessionid;


	if (v_sessionkey is null) then 
	begin
        start transaction;
            set v_resourcename = concat('guestsession ', v_clientname);
			set v_applock = get_lock(v_resourcename, 0);

			if (v_applock < 0) then
			begin
				call _recordsystemerror('_setupproctorlesssession', 'unable to obtain applock', null, null, null, null, null, null, null, null, null);
				rollback;
				
				set v_sessionkey = null;
				set v_sessionid = null;

				leave proc;
			end;
			end if;
			
			set v_sessionkey = unhex(REPLACE(UUID(), '-', ''));

            -- _fk_browser is a required field but is not used for proctorless sessions, so we dummy it with the same guid as session key
			insert into `session` (_key, _fk_browser, sessionid, `name`, clientname, environment, datecreated, serveraddress) 
				 values (v_sessionkey, v_sessionkey, v_sessionid, 'tds session', v_clientname, v_environ, now(3), @@hostname);
			
			set v_consume = (select release_lock(v_resourcename));
		commit;        
	end;
	end if;
    
	insert into sessiontests (_fk_session, _efk_adminsubject, _efk_testid)
	select v_sessionkey, testkey, testid 
	from tmp_tbltests
    where not exists (select * from sessiontests 
					   where _fk_session = v_sessionkey and _efk_adminsubject = testkey);

	call p_opensession(v_sessionkey, 12, 1, null);

	-- clean-up
	drop temporary table tmp_tbltests;

end $$

DELIMITER ;