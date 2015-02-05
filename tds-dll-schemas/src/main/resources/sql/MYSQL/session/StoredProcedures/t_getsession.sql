DELIMITER $$

drop procedure if exists t_getsession $$

create procedure t_getsession (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/29/2015		Sai V. 			Converted code from T-SQL to MySQL
*/
	v_clientname varchar(100)
  , v_sessionid  char(128)
)
proc: begin

	-- session variables
	declare v_sessionkey	varbinary(16);
	declare v_proctorkey	bigint;
	declare v_proctorid		varchar(128);
	declare v_proctorname	varchar(128);
	declare v_status		varchar(32);
	declare v_name			varchar(255);
	declare v_datecreated	datetime(3);
	declare v_datebegin		datetime(3);
	declare v_dateend		datetime(3);
    declare v_msgstatus     varchar(50);
    declare v_msgkey        varchar(200);
    declare v_msgarg        varchar(200);

	-- other variables
	declare v_today datetime(3);
	set v_today = now(3);

    if (v_sessionid = 'guest session') then 
	begin
        if (_allowproctorlesssessions (v_clientname) = 1) then
            call _setupproctorlesssession (v_clientname, v_sessionkey, v_sessionid);
        else 
		begin
            call _returnerror(v_clientname, 't_getsession', 'you are not allowed to log in without a test administrator', null, v_oppkey, '_validateitemsaccess', 'failed');
            leave proc;
		end;
        end if;
    end;
	end if;

	-- get session
    select _key
		 , _efk_proctor
		 , proctorid
		 , proctorname
		 , `status`
		 , `name`
		 , datecreated
		 , datebegin
		 , dateend
	into v_sessionkey, v_proctorkey, v_proctorid, v_proctorname, v_status, v_name, v_datecreated, v_datebegin, v_dateend
    from `session`
    where sessionid = v_sessionid
		and clientname = v_clientname;

	-- session found?
	if (v_sessionkey is null) then
	begin
		set v_msgstatus = 'denied';
		set v_msgkey = 'could not find session, please check with your test administrator.';	
	end;
	-- does the session's state permit joining?
	elseif (v_status = 'closed') then
	begin
		set v_msgstatus = 'closed';
		set v_msgkey = 'the testing session is closed, please check with your test administrator.' ;
	end;
	elseif (v_proctorkey is null and _allowproctorlesssessions(v_clientname) = 0) then 
	begin
		set v_msgstatus = 'denied';
		set v_msgkey = 'the session is not available, please check with your test administrator.';
	end;
	elseif (v_status <> 'open' or v_datebegin is null or v_dateend is null) then
	begin
		set v_msgstatus = 'denied';
		set v_msgkey = 'the testing session is not in a valid state, please check with your test administrator.';
	end;
	elseif (v_today < v_datebegin) then
	begin
		set v_msgstatus = 'denied';
		set v_msgkey = 'the testing session starts on {0}. for further assistance, please check with your test administrator.';
		set v_msgarg = date_format(v_datebegin, '%m/%d/%Y');
	end;
	elseif (v_today > v_dateend) then
	begin
		set v_msgstatus = 'denied';
		set v_msgkey =  'the testing session expired on {0}. for further assistance, please check with your test administrator.';
		set v_msgarg = date_format(v_dateend, '%m/%d/%Y');
	end; 
	end if;

    if (v_msgstatus is not null) then
	begin
        call _returnerror(v_clientname, 't_getsession', v_msgkey, v_msgarg, null, null, v_msgstatus);
        leave proc;
    end;
	end if;

	-- session ok to use
	select 'open' as `status`, '' as reason;

	-- return session info
	select v_sessionkey 	as sessionkey
		 , v_proctorkey 	as proctorkey
		 , v_proctorid 		as proctorid
		 , v_proctorname 	as proctorname
		 , v_name 			as `name`
		 , v_datecreated 	as datecreated
		 , v_datebegin 		as datebegin
		 , v_dateend 		as dateend;

	call _logdblatency('t_getsession', v_today, null, 1, null, null, v_sessionkey, v_clientname, null);

end $$

DELIMITER ;