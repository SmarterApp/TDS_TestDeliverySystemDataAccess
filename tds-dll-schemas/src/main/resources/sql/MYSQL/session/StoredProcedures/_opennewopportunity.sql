DELIMITER $$

drop procedure if exists `_opennewopportunity` $$

create procedure `_opennewopportunity`(
/*
Description: open a new test opportunity for testing. main purpose of this function is to apply business rules.

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/10/2015		Sai V. 			Code Migration
*/
	v_clientname varchar(100)
  , v_testee bigint
  , v_testkey varchar(255)
  , v_opportunity int
  , v_sessionkey varbinary(16)
  , v_browserkey varbinary(16)
  , v_testeeid varchar(50)
  , v_testeename varchar(100)
  , v_status varchar(50) -- = 'pending'
  , v_guestaccommodations text
  , out v_testoppkey varbinary(16)
)
sql security invoker
proc: begin

	declare v_today, v_startdatewin datetime(3);
    declare v_subject varchar(100);      -- subject added 7/28/2009 to facilitate blocking of tests by subject
    declare v_auditproc bit;
	declare v_newid bigint;
    declare v_testid varchar(200);
    declare v_environment varchar(50);
    declare v_sessiontype int;
    declare v_mode varchar(50);
    declare v_segmented bit;
    declare v_algorithm varchar(50);
    declare v_windowid varchar(100);
	declare v_procname varchar(100);
	declare v_version int;
	declare v_context varchar(100);

	set v_today = now(3);
	set v_procname = '_opennewopportunity';
    set v_auditproc = auditproc(v_procname);
	set v_environment = (select environment from externs where clientname = v_clientname);
    set v_sessiontype = (select sessiontype from `session` where _key = v_sessionkey);

    set v_testoppkey = unhex(REPLACE(UUID(), '-', ''));
 
    select itembank.testsubject(v_testkey), testid, issegmented, selectionalgorithm 
	into v_subject, v_testid, v_segmented, v_algorithm
    from itembank.tblsetofadminsubjects 
	where _key = v_testkey;

	drop temporary table if exists tmp_tblwindows;
    create temporary table tmp_tblwindows(
		wid varchar(100)
	  , maxopps int
	  , numopps int default 0
	  , startdate datetime(3)
	  , enddate datetime(3)
	  , formkey varchar(50)
	  , modeopps int default 0
	  , testmode varchar(100)
	  , modemax int
	  , testkey varchar(250)
	);
    
	drop temporary table if exists tblout_gettesteetestwindows;
	create temporary table tblout_gettesteetestwindows(
		windowid		varchar(50)	
	  , windowmax		int
	  , startdate		datetime(3)
	  , enddate			datetime(3)
	  , formkey			varchar(200)
	  , `mode`			varchar(50)
	  , modemax			int
	  , testkey			varchar(250)
	) engine = memory;

    call _gettesteetestwindows(v_clientname, v_testid, v_testee, v_sessiontype, null, null, 0);

	insert into tmp_tblwindows(wid, maxopps, startdate, enddate, formkey, testmode, modemax, testkey)
	select * from tblout_gettesteetestwindows;

	set v_startdatewin = (select min(startdate) from tmp_tblwindows);

    select wid, testmode into v_windowid, v_mode
    from tmp_tblwindows
    where testkey = v_testkey and startdate = v_startdatewin
	limit 1;

    if (v_windowid is null) then
        call _returnerror(v_clientname, v_procname, 'there is no active testing window for this student at this time', null, null, null, null);
        leave proc;
    end if;

	call _createclientreportingid(v_clientname, v_testoppkey, v_newid /*output*/);

    if (v_newid is null) then
		call _logdberror(v_procname, 'unable to create a unique reporting id', v_testee, v_testkey, v_opportunity, null, null, null);
		call _returnerror(v_clientname, v_procname, 'unable to create a unique reporting id', null, null, null, null);
		leave proc;
	end if;

    -- version is irrespective of deleted status
    
    set v_version = (select max(_version) from testopportunity 
					  where _efk_testee = v_testee and _efk_testid = v_testid and opportunity = v_opportunity and clientname = v_clientname);

    if (v_version is null) then 
		set v_version = 1;
    else 
		set v_version = v_version + 1;
	end if;

	insert into testopportunity(_key, _version, clientname, _efk_testee, _efk_testid, opportunity, `status`, `subject`, testeeid, testeename,
										_fk_browser, datechanged, reportingid, windowid, `mode`, issegmented, `algorithm`,_efk_adminsubject, environment, sessid, proctorname, waitingforsegment)
	select v_testoppkey, v_version, v_clientname, v_testee, v_testid, v_opportunity
		 , 'paused', v_subject, v_testeeid, v_testeename
		 , v_browserkey, v_today, v_newid, v_windowid, v_mode, v_segmented, v_algorithm, v_testkey, v_environment, sessionid, proctorname, 1
    from `session` 
	where _key = v_sessionkey;
    
    set v_context = 'initial';

    call _settesteeattributes(v_clientname, v_testoppkey, v_testee, v_context);

    if (exists (select * from archive.systemerrors where procname = '_settesteeattributes' and _fk_testopportunity = v_testoppkey)) then
        call _settesteeattributes(v_clientname, v_testoppkey, v_testee, v_context);      -- we have had deadlock problems with this, try again?
	end if;

	-- insert the given accommodations or defaults
    call _initopportunityaccommodations(v_testoppkey, v_guestaccommodations);

    update testopportunity 
	set `status` = v_status
    where _key = v_testoppkey; 

	if (v_auditproc <> 0) then
		insert into archive.opportunityaudit(_fk_testopportunity, dateaccessed, _fk_session, accesstype, hostname, _fk_browser)
			 values (v_testoppkey, now(3), v_sessionkey, v_status, @@hostname, v_browserkey);
	end if;

    call _logdblatency(v_procname, v_today, v_testee, 1, null, v_testoppkey, null, v_clientname, null);

end $$

DELIMITER ;
