DELIMITER $$

drop procedure if exists `_gettesteetestwindows` $$

create procedure `_gettesteetestwindows`(
/*
Description: -- this procedure returns all test windows for which the testee is qualified.
			-- there are, essentially, 3 cases
			-- 1. the window is assigned directly in rts and is not associated with any specific test form  (middle precedence)
			-- 2. the window is associated with a test form and is assigned for the student in the rts    (highest precedence)
			-- 3. the window is not assigned directly to the student in the rts                             (lowest precedence)

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/10/2015		Sai V. 			--
*/
	v_clientname varchar(100)
  , v_testid varchar(200)
  , v_testee bigint
  , v_sessiontype int -- = 0
  , v_windowlist text -- = null -- this parameter for debugging purposes only
  , v_formlist text -- = null -- this parameter for debugging purposes only
  , v_debug bit -- = 0 -- this parameter for debugging purposes only
  , v_returnresult bit -- = 0
)
sql security invoker
proc: begin

    declare  v_windowfield varchar(50);
	declare v_requirewindow bit;
	declare v_tideid varchar(200); 
    declare v_starttime datetime(3);
	declare v_isformtest bit;
	declare v_gettestformwindows_cnt int;

    set v_starttime = now(3);
    set v_isformtest = 0;

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
/*
	drop temporary table if exists tmp_tblgetcurrenttestwindows;
	create temporary table tmp_tblgetcurrenttestwindows(
		windowid		varchar(50)	
	  , windowmax		int
	  , startdate		datetime(3)
	  , enddate			datetime(3)
	  , `mode`			varchar(50)
	  , modemax			int
	  , testkey			varchar(250)
	) engine = memory;

	-- getcurrenttestwindows(v_clientname, v_testid, v_sessiontype);
	insert into tmp_tblgetcurrenttestwindows
	select distinct w.windowid
		 , w.numopps as windowmax
		 , case when w.startdate is null then now(3) else date_add(w.startdate, interval shiftwindowstart day) end as startdate
		 , case when w.enddate is null then now(3) else date_add(w.enddate, interval shiftwindowend day) end as enddate
		 , m.`mode`
		 , m.maxopps as modemax
		 , m.testkey
	from configs.client_testwindow w, configs.client_testmode m, _externs e
	where w.clientname = v_clientname and w.testid = v_testid 
		and e.clientname = v_clientname 
		and now(3) between case when w.startdate is null then now(3) else date_add(w.startdate, interval shiftwindowstart day) end
					   and case when w.enddate is null then now(3) else date_add(w.enddate, interval shiftwindowend day) end
		and m.clientname = v_clientname and m.testid = v_testid
		and (m.sessiontype = -1 or m.sessiontype = v_sessiontype)
		and (w.sessiontype = -1 or w.sessiontype = v_sessiontype);
*/   

    if (v_testee < 0) then -- for guest testees there is no registration data to be found
		-- getcurrenttestwindows(v_clientname, v_testid, v_sessiontype);
		insert into tblout_gettesteetestwindows
        select distinct windowid, windowmax , startdate, enddate, null as formkey, `mode`, modemax, testkey
		from tmp_tblgetcurrenttestwindows_global;

        leave proc;
    end if;

    -- note: it is an error for v_require = 1 and v_formfield = null
    select rtswindowfield, requirertswindow, tide_id
	into v_windowfield, v_requirewindow, v_tideid
    from configs.client_testproperties 
	where clientname = v_clientname and testid = v_testid;

    set v_isformtest = (select 1 from dual
						 where exists (select * from configs.client_testformproperties
										where clientname = v_clientname and testid = v_testid));

    if (v_windowlist is not null) then    -- use for debugging
        set v_requirewindow = 1;
    elseif (v_windowfield is null) then  -- sanity check
        set v_requirewindow = 0;
	end if;

	drop temporary table if exists tmp_tblformwindows;
	create temporary table tmp_tblformwindows(wid varchar(100), form varchar(50)) engine = memory; 

	-- independent window selection is used in several different places for form and adaptive tests. set it up here    
    if (v_requirewindow = 1) then
		if (v_debug = 1) then select 'require window'; end if;         

        if (v_windowlist is null) then
            call _getrtsattribute(v_clientname, v_testee, v_windowfield, v_windowlist /*output*/, 'student', 0);
		end if;

		/* Call _buildtable stored procedure 
		-- To capture and use result set from _buildtable, a temporary table is created to store the resultset */
		call _buildtable(v_windowlist, ';');

		insert into tmp_tblformwindows (wid)
		select substring_index(record, ':', -1) 
		from tblout_buildtable
		where record like concat(v_tideid, ':%');	
	end if;

	set v_gettestformwindows_cnt = (select 
										(select count(*)
										from configs.client_testwindow w, configs.client_testformproperties f, configs.client_testmode m, itembank.tblsetofadminsubjects bank, _externs e
										where f.clientname = v_clientname and f.testid = v_testid 
											and m.testkey = f.testkey and m.testkey = bank._key
											and m.clientname = v_clientname and m.testid = v_testid and (m.sessiontype = -1 or m.sessiontype = v_sessiontype)
											and e.clientname = v_clientname
											and now(3) between case when f.startdate is null then now(3) else date_add(f.startdate, interval shiftformstart day) end 
														   and case when f.enddate is null then now(3) else date_add(f.enddate, interval shiftformend day) end 
											and now(3) between case when w.startdate is null then now(3) else date_add(w.startdate, interval shiftwindowstart day) end 
														   and case when w.enddate is null then now(3) else date_add(w.enddate, interval shiftwindowend day) end 
											and w.clientname = v_clientname and w.testid = v_testid 
											and (w.sessiontype = -1 or w.sessiontype = v_sessiontype))
										+
										(select count(*)
										from configs.client_testwindow w, configs.client_testformproperties f, configs.client_segmentproperties s
										   , configs.client_testmode m, itembank.tblsetofadminsubjects bank, _externs e
										where s.clientname = v_clientname and f.clientname = v_clientname and f.testkey = bank._key and s.parenttest = v_testid
											and m.clientname = v_clientname and m.testid = v_testid and (m.sessiontype = -1 or m.sessiontype = v_sessiontype)
											and s.modekey = m.testkey and s.segmentid = bank.testid
											and e.clientname = v_clientname
											and now(3) between case when f.startdate is null then now(3) else date_add(f.startdate, interval shiftformstart day) end 
														   and case when f.enddate is null then now(3) else date_add(f.enddate, interval shiftformend day) end 
											and now(3) between case when w.startdate is null then now(3) else date_add(w.startdate, interval shiftwindowstart day)end 
														   and case when w.enddate is null then now(3) else date_add(w.enddate, interval shiftwindowend day) end 
											and w.clientname = v_clientname and w.testid = s.parenttest 
											and (w.sessiontype = -1 or w.sessiontype = v_sessiontype)));



    if (v_gettestformwindows_cnt > 0) then
		if (v_debug = 1) then select 'cnt > 0'; end if;
		/* Call _gettesteetestforms stored procedure 
		-- To capture and use result set from _gettesteetestforms, a temporary table is created to store the resultset */		
		call _gettesteetestforms(v_clientname, v_testid, v_testee, v_sessiontype, v_formlist, 0, 0);

		insert into tblout_gettesteetestwindows
		select distinct window, windowmax , startdate, enddate, null as formkey, `mode`, modemax, testkey
		from tblout_gettesteetestforms;	

        call _logdblatency('_gettesteetestwindows', v_starttime, v_testee, null, null, null, null, v_clientname, 'exit at condition: v_gettestformwindows_cnt > 0');
        leave proc;
    end if;

	-- not a fixed form test. determine if the window has been assigned to the student
	-- test windows are recorded by tide_id (in lieu of testid), which is not necessarily unique.
    if (v_requirewindow = 1) then
	begin                
        if (v_windowlist is null) then
            call _getrtsattribute(v_clientname, v_testee, v_windowfield, v_windowlist /*output*/, 'student', 0);
		end if;

		insert into tblout_gettesteetestwindows
        select distinct windowid, windowmax , startdate, enddate, null as formkey, `mode`, modemax, testkey
        from tmp_tblformwindows, tmp_tblgetcurrenttestwindows_global
        where wid = windowid;
		
		if (v_debug = 1) then select 'window required'; select * from tblout_gettesteetestwindows; end if;
        call _logdblatency('_gettesteetestwindows', v_starttime, v_testee, null, null, null, null, v_clientname, null);
        leave proc;
    end;
	end if;

    -- not a fixed form test and no special window conditions specific to the testee. return all windows currently active on this test
    insert into tblout_gettesteetestwindows
    select distinct windowid, windowmax , startdate, enddate, null as formkey, `mode`, modemax, testkey
    from tmp_tblgetcurrenttestwindows_global;
	    
	-- clean-up
	drop temporary table tmp_tblformwindows;
-- 	drop temporary table tmp_tblgetcurrenttestwindows;

    call _logdblatency('_gettesteetestwindows', v_starttime, v_testee, null, null, null, null, v_clientname, null);

end $$

DELIMITER ;
