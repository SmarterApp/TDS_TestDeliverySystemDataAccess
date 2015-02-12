DELIMITER $$

drop procedure if exists `_isopportunityblocked` $$

create procedure `_isopportunityblocked`(
/*
Description:

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/10/2015		Sai V. 			
*/
    v_clienname varchar(100)
  , v_testee bigint
  , v_testkey varchar(255)  
  , v_maxoppps int
  , out v_reasonblocked text /*output */
  , v_sessiontype int -- = 0
)
sql security invoker 
proc: begin

    declare v_subject varchar(100);
    declare v_segmented bit;
    declare v_algorithm varchar(50);
    
	set v_reasonblocked = null;
	set v_subject = (select subjectname from configs.client_testproperties
					  where clientname = v_clientname and testid = v_testid);	
    
    if (exists (select * from _externs where clientname = v_clientname and environment = 'simulation')) then
        -- do not impose any restrictions on simulation environments
        leave proc;
    end if;

    -- this is where skipped blockedsubject functionality was located  
  
    drop temporary table if exists tmp_tblwindows;
    create temporary table tmp_tblwindows(
		winsession 	int
	  , modesessn   int
	  , wid         varchar(100)
	  , numopps     int
	  , winmax      int
	  , winopps     int default 0
	  , modeopps    int default 0
	  , startdate   datetime(3)
	  , enddate     datetime(3)
	  , formkey     varchar(50)
	  , testkey     varchar(250)
	  , `mode`      varchar(50)
	  , modemax     int	  
	) engine = memory;
	
	drop temporary table if exists tmp_tblmodes;
    create temporary table tmp_tblmodes(
        winid       varchar(100)
      , numopps     int
 	  , winmax      int
 	  , winopps     int default 0
	  , modeopps    int default 0	
	  , startdate   datetime(3)
	  , enddate     datetime(3)
	  , formkey     varchar(50)
	  , modetestkey varchar(250)
	  , `mode`      varchar(50)
	  , modemax     int		  
    ) engine = memory;

	drop temporary table if exists tblout_gettesteetestmodes;
	create temporary table tblout_gettesteetestmodes(
		windowid		varchar(50)	
	  , windowmax		int
	  , startdate		datetime(3)
	  , enddate			datetime(3)
	  , `mode`			varchar(50)
	  , modemax			int
	  , testkey			varchar(250)
	) engine = memory;

	call _gettesteetestmodes(v_clientname, v_testid, v_testee, v_sessiontype, null, 0);
	
    insert into tmp_tblmodes (winid, winmax, startdate, enddate, `mode`, modemax, modetestkey)
	select * from tblout_gettesteetestmodes;	

    if (not exists (select * from tmp_tblmodes)) then
        set v_reasonblocked = 'na';  -- test is not applicable to this student in this mode
        leave proc;
    end if;

    if (exists (select * from configs.client_testprerequisite 
				 where clientname = v_clientname and testid = v_testid and isactive = 1
					and not exists (select * from testopportunity 
									 where clientname = v_clientname and _efk_testee = v_testee and _efk_testid = prereqtestid and datecompleted is not null and datedeleted is null))) 
	then
        call _formatmessage(v_clientname, 'enu', '_canopentestopportunity', 'missing prerequisite', v_reasonblocked /*output*/, null, ',', null, null);
        leave proc;
    end if;

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

	insert into tmp_tblwindows (wid, winmax, startdate, enddate, formkey, `mode`, modemax, testkey)
	select * from tblout_gettesteetestwindows;

	-- select * from tmp_tblmodes; select * from tmp_tblwindows;

    delete from tmp_tblwindows 
	where not exists (select * from tmp_tblmodes where winid = wid and modetestkey = testkey);

    if (not exists (select * from tmp_tblwindows where wid is not null)) then
        call _formatmessage(v_clientname, 'enu', '_canopentestopportunity', 'there is no active testing window for this student on this test', v_reasonblocked /*output*/, null, ',', null, null);
        leave proc;
    end if;

	drop temporary table if exists tmp_tblsessionvalue;
	create temporary table tmp_tblsessionvalue(
	    windowsession	int
	  , modesession		int
	)engine = memory;
   
    -- getcurrenttestwindows(v_clientname, v_testid, v_sessiontype);
	insert into tmp_tblsessionvalue
	select distinct w.sessiontype as windowsession, m.sessiontype as modesession
	from configs.client_testwindow w, configs.client_testmode m, _externs e
	where w.clientname = v_clientname and w.testid = v_testid 
		and e.clientname = v_clientname 
		and now(3) between case when w.startdate is null then now(3) else date_add(w.startdate, interval shiftwindowstart day) end
					   and case when w.enddate is null then now(3) else date_add(w.enddate, interval shiftwindowend day) end
		and m.clientname = v_clientname and m.testid = v_testid
		and (m.sessiontype = -1 or m.sessiontype = v_sessiontype)
		and (w.sessiontype = -1 or w.sessiontype = v_sessiontype);

    update tmp_tblwindows, tmp_tblsessionvalue
	set winsession = windowsession
	  , modesessn = modesession;
    
    update tmp_tblwindows 
	set winopps = (select count(*) from testopportunity o, `session` s
					where o.clientname = v_clientname and _efk_testee = v_testee and _efk_testid = v_testid
					  and o._fk_session  = s._key and (winsession = -1 or s.sessiontype = winsession)
					  and windowid = wid and datedeleted is null -- if deleted then the opportunity was reset
					  and o.`status` in (select `status` from statuscodes where `usage` = 'opportunity' and stage = 'closed'));


    update tmp_tblwindows 
	set modeopps = (select count(*) from testopportunity o, `session` s
					 where o.clientname = v_clientname and _efk_testee = v_testee and _efk_adminsubject = testkey
					   and o._fk_session  = s._key and (modesessn = -1 or s.sessiontype = modesessn)
					   and datedeleted is null -- if deleted then the opportunity was reset
					   and o.`status` in (select `status` from statuscodes where `usage` = 'opportunity' and stage = 'closed'));

	-- select * from tmp_tblwindows;
    if (not exists (select * from tmp_tblwindows where winopps < winmax and modeopps < modemax)) then        
        call _formatmessage(v_clientname, 'enu', '_canopentestopportunity', 'no opportunities available in this testing window.', v_reasonblocked /*output*/, null, ',', null, null);
        leave proc;
    end if;

	-- check for all opportunities used for the logical test (testid as opposed to testkey)
    update tmp_tblwindows 
	set numopps = (select count(*) from testopportunity
					where clientname = v_clientname and _efk_testee = v_testee and _efk_testid = v_testid
					  and datedeleted is null -- if deleted then the opportunity was reset
					  and `status` in (select `status` from statuscodes where `usage` = 'opportunity' and stage = 'closed'));

    if (not exists (select * from tmp_tblwindows where numopps < v_maxopps)) then
        call _formatmessage(v_clientname, 'enu', '_canopentestopportunity', 'all opportunities have been used for this test', v_reasonblocked /*output*/, null, ',', null, null);
        leave proc;
    end if;

end $$ 

DELIMITER ;