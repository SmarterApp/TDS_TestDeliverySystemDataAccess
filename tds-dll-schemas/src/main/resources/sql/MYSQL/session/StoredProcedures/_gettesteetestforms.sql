DELIMITER $$

drop procedure if exists `_gettesteetestforms` $$

create procedure `_gettesteetestforms`(
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/06/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_clientname varchar(100)
  , v_testid varchar(200)
  , v_testee bigint
  , v_sessiontype int
  , v_formlist text -- = null
  , v_debug bit -- = 0
)
sql security invoker
proc: begin

    declare v_tideid varchar(200);
	declare v_requireformwindow, v_requireform, v_ifexists bit;
	declare v_formfield varchar(50);

	drop temporary table if exists tmp_tblforms;
    create temporary table tmp_tblforms(
		wid varchar(100)
	  , form varchar(50)
	);
	
	drop temporary table if exists tmp_tblrtsvals;
    create temporary table tmp_tblrtsvals(
		record varchar(200)
	);

	drop temporary table if exists tmp_tblgettestformwindows;
	create temporary table tmp_tblgettestformwindows(
		windowid		varchar(50)	
	  , windowmax		int
	  , modemax			int
	  , startdate		datetime(3)
	  , enddate			datetime(3)
	  , formstart 		datetime(3)
	  , formend			datetime(3)
      , formkey			varchar(50)
	  , formid			varchar(200)
	  , `language`		varchar(25)
	  , `mode`			varchar(50)
	  , testkey			varchar(250)
	  , windowsession	int
	  , modesession		int
	);

	insert into tmp_tblgettestformwindows
	-- dbo.gettestformwindows(v_clientname, v_testid, v_sessiontype)
	-- match test windows with form start/end dates for unsegmented tests
    select  windowid, w.numopps as windowmax, m.maxopps as modemax
            , case when w.startdate is null then now(3) else date_add(w.startdate, interval shiftwindowstart day) end as startdate
            , case when w.enddate is null then now(3) else date_add(w.enddate, interval shiftwindowend day) end as enddate
            , case when f.startdate is null then now(3) else date_add(f.startdate, interval shiftformstart day) end as formstart 
            , case when f.enddate is null then now(3) else date_add(f.enddate, interval shiftformend day) end as formend
            , _efk_testform as formkey, formid, f.language
            , m.mode, m.testkey, w.sessiontype as windowsession, m.sessiontype as modesession
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
        and (w.sessiontype = -1 or w.sessiontype = v_sessiontype)
	-- match parent test windows with segment form start/end dates for segmented tests
    union
    select windowid, w.numopps, m.maxopps
		 , case when w.startdate is null then now(3) else date_add(w.startdate, interval shiftwindowstart day) end as startdate
		 , case when w.enddate is null then now(3) else date_add(w.enddate, interval shiftwindowend day) end  as enddate
		 , case when f.startdate is null then now(3) else date_add(f.startdate, interval shiftformstart day) end as formstart 
		 , case when f.enddate is null then now(3) else date_add(f.enddate, interval shiftformend day) end as formend
		 , _efk_testform as formkey, formid, f.`language`, m.`mode`, m.testkey as testkey, w.sessiontype , m.sessiontype 
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
        and (w.sessiontype = -1 or w.sessiontype = v_sessiontype);


    if (v_testee < 0) then  -- if 'guest' testees are allowed into the system, then they qualify for all forms by default since there is no rts data for them
		insert into tblout_gettesteetestforms
        select windowid, windowmax, startdate, enddate, formkey, `mode`, modemax, testkey
        from tmp_tblgettestformwindows;
        
		leave proc;
    end if;
    
	-- note: it is an error for v_require = 1 and v_formfield = null
    select tide_id, requirertsformwindow, rtsformfield, requirertsform, requirertsformifexists
	into v_tideid, v_requireformwindow, v_formfield, v_requireform, v_ifexists
    from configs.client_testproperties t, configs.client_testmode f
    where t.clientname = v_clientname and t.testid = v_testid and f.clientname = v_clientname and f.testid = v_testid
        and (sessiontype = -1 or sessiontype = v_sessiontype);

    if (v_formlist is not null) then 
	begin -- this block sets up debugging capabilities by simulating conditions we expect to find in the rts   
        if (locate(':', v_formlist) > 0) then
            set v_requireformwindow = 1;
        else 
            set v_requireform = 1;
            set v_requireformwindow = 0;
        end if;
    end;
    elseif (v_tideid is not null and v_formfield is not null) then   -- this is live production condition
		call _getrtsattribute(v_clientname, v_testee, v_formfield, v_formlist /*output*/, 'student', 0);
	end if;

    if (v_debug = 1) then 
		select v_tideid tideid, v_formfield formfield, v_requireformwindow reqwin, v_requireform reqform, v_ifexists ifexists, v_formlist forms;
	end if;

	-- note: we cannot rely on all ';'-delimited values in the test forms field to be window-discriminated. only those for this test
	-- and we can only tell which test we are looking at by the intersection between the forms and the tests
	-- so we have to split this string 2 ways: with the window and without the window
    if (v_formlist is not null and v_tideid is not null) then 
	begin
		/* Call _buildtable stored procedure 
		-- To capture and use result set from _buildtable, a temporary table is created to store the resultset */
		drop temporary table if exists tblout_buildtable;
		create temporary table tblout_buildtable(idx int, record varchar(255));
			  
		call _buildtable(v_formlist, ';');

		insert into tmp_tblrtsvals (record)
		select record 
		from tblout_buildtable;
		/* # */

		-- first, split the string for all values that have the window
        insert into tmp_tblforms (wid, form)
        select substring_index(record, ':', 1), substring_index(record, ':', -1)
        from tmp_tblrtsvals 
		where locate(':', record) > 0;

        insert into tmp_tblforms (form)
        select record
        from tmp_tblrtsvals 
		where locate(':', record) = 0;
        
        if (v_debug = 1) then select * from tmp_tblforms; end if;
    end;
	end if;

    -- forms are assigned to specific windows in this first case
    if (v_requireformwindow = 1 ) then
	begin
		insert into tblout_gettesteetestforms
        select distinct windowid, windowmax, startdate, enddate, formkey, mode, modemax, testkey
        from tmp_tblforms, tmp_tblgettestformwindows
        where wid = windowid 
			and form = formkey;

        if (v_debug = 1) then 
			select 'exit 1'; 
			select * from tblout_gettesteetestforms;
		end if;

        leave proc;
	end;
    elseif (v_requireform = 1) then
	begin
		-- window is not required, just the form, so just join the forms
		insert into tblout_gettesteetestforms
        select distinct windowid, windowmax, startdate, enddate, formkey, mode, modemax, testkey
        from tmp_tblforms
			join tmp_tblgettestformwindows on form = formkey;  

		if (v_debug = 1) then 
			select 'exit 2'; 
			select * from tblout_gettesteetestforms;
		end if;

		leave proc;
	end;
    elseif (v_ifexists = 1 and exists (select * from tmp_tblforms, tmp_tblgettestformwindows where form = formkey)) then
	begin
		-- window is not required, just the form, so just join the forms
		insert into tblout_gettesteetestforms
		select distinct windowid, windowmax, startdate, enddate, formkey, mode, modemax, testkey
		from tmp_tblforms
			join tmp_tblgettestformwindows on form = formkey;  
		
		if (v_debug = 1) then 
			select 'exit 3'; 
			select * from tblout_gettesteetestforms;
		end if;

		leave proc;
	end;
	end if;

    -- else there is no specific form assignment to the testee: return all active windows all forms
	insert into tblout_gettesteetestforms
    select windowid, windowmax, startdate, enddate, formkey, mode, modemax, testkey
    from tmp_tblgettestformwindows;

    if (v_debug = 1) then 
		select 'exit last'; 
		select * from tblout_gettesteetestforms;
	end if;

    
end $$

DELIMITER ;