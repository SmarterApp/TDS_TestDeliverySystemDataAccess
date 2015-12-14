DELIMITER $$

drop procedure if exists `_gettesteetestwindows` $$

create procedure `_gettesteetestwindows`(
/*
Description: This procedure is created on the SESSION db
			-- This procedure returns all test windows for which the testee is qualified.
			-- There are, essentially, 3 cases
			-- 1. The window is assigned directly in RTS and is NOT associated with any specific test form (middle precedence)
			-- 2. The window is associated with a test form and is assigned for the student in the RTS     (highest precedence)
			-- 3. The window is NOT assigned directly to the student in the RTS                            (lowest precedence)

VERSION 	DATE 			AUTHOR 			COMMENTS
001			01/23/2014		Sai V. 			Converted code from SQL Server to MySQL

*/
	v_clientname varchar(100)
  , v_testid varchar(200)
  , v_testee bigint
  , v_sessiontype int
  , v_windowlist text
  , v_formlist text 
)
proc: begin  

    declare v_windowfield varchar(50);
	declare v_requirewindow bit;
	declare v_tideid varchar(200); 
    declare v_starttime datetime;
    declare v_isformtest bit;

	drop temporary table if exists tblwindows;
    create temporary table tblwindows (wid varchar(100), form varchar(50));

    set v_starttime = now(3);
    set v_isformtest = 0;
	if v_sessiontype is null then set v_sessiontype = 0; end if;

	drop temporary table if exists tblout_getcurrenttestwindows;
	create temporary table tblout_getcurrenttestwindows( 
		windowmax 		int
	  , windowid 		varchar(50)
	  , startdate 		datetime(3)
	  , enddate 		datetime(3)
	  , `mode` 			varchar(50)
	  , testkey 		varchar(250)
	  , modemax 		int
	  , windowsession 	int
	  , modesession 	int
	);

	select getcurrenttestwindows(v_clientname, v_testid, v_sessiontype);

    if (v_testee < 0) then  -- for guest testees there is no registration data to be found
        select distinct windowid, windowmax , startdate, enddate, null as formkey, `mode`, modemax, testkey
        from tblout_getcurrenttestwindows;

        leave proc;
    end if;

    -- note: it is an error for v_require = 1 and v_formfield = null
    select rtswindowfield, requirertswindow, tide_id
	into v_windowfield, v_requirewindow, v_tideid
    from tdsconfigs_client_testproperties 
	where clientname = v_clientname 
		and testid = v_testid;

	if exists ( select * from tdsconfigs_client_testformproperties
				where clientname = v_clientname and testid = v_testid) 
	then
		set v_isformtest = 1;
	end if;

    -- form+window assignment takes precedence over window assignment. 
    -- this is a bit complicated. window assignment is pegged to one or more forms, and we have to match forms up with their start/end dates.
    -- so in order to qualify for a test window, the student must have a form assignment that is active
    -- we do not check whether the form has been used or not since forms may be administered more than one, depending on the number of opportunities allowed by the window and the test
    if (v_windowlist is not null) then    -- use for debugging
        set v_requirewindow = 1;
	end if;
    if (v_windowfield is null ) then  -- sanity check
        set v_requirewindow = 0;
	end if;

	-- independent window selection is used in several different places for form and adaptive tests. set it up here    
    if (v_requirewindow = 1) then                
		drop temporary table if exists tblout_buildtable;
		create temporary table tblout_buildtable (idx int, record varchar(255));

		call _buildtable(v_windowlist, ';');

        insert into tblwindows (wid)
        select right(record, length(record) - locate(':', record))
        from tblout_buildtable 
		where record like concat(v_tideid, ':%');

    end if;

	-- select * from tblwindows;
	drop temporary table if exists tblout_gettestformwindows;
	create temporary table tblout_gettestformwindows( 
	    windowid 		varchar(50)
	  ,	windowmax 		int
	  , modemax 		int
	  , startdate 		datetime(3)
	  , enddate 		datetime(3)
	  , formstart 		datetime(3)
	  , formend 		datetime(3)
	  , formkey			varchar(50)
	  , formid			varchar(200)
	  , `language`		varchar(25)
	  , `mode` 			varchar(50)
	  , testkey 		varchar(250)
	  , windowsession 	int
	  , modesession 	int
	);

	select gettestformwindows(v_clientname, v_testid, v_sessiontype);

    if (exists (select * from tblout_gettestformwindows)) then
        call _gettesteetestforms (v_clientname, v_testid, v_testee, v_sessiontype, v_formlist);
        -- exec _logdblatency v_v_procid, v_starttime, v_clientname = v_clientname;

        leave proc;
    end if;

	-- not a fixed form test. determine if the window has been assigned to the student
	-- test windows are recorded by tide_id (in lieu of testid), which is not necessarily unique.
    if (v_requirewindow = 1) then                
		-- select 'window required';
        select distinct windowid, windowmax , startdate, enddate, null as formkey, `mode`, modemax, testkey
        from tblwindows, tblout_getcurrenttestwindows
        where wid = windowid;
        -- exec _logdblatency v_v_procid, v_starttime, v_clientname = v_clientname;

        leave proc;
    end if;

    
    -- not a fixed form test and no special window conditions specific to the testee. return all windows currently active on this test
    select distinct windowid, windowmax , startdate, enddate, null as formkey, `mode`, modemax, testkey
    from tblout_getcurrenttestwindows;
    
    -- exec _logdblatency v_v_procid, v_starttime, v_clientname = v_clientname;

end$$

DELIMITER ;