DELIMITER $$

drop procedure if exists `_gettesteetestmodes` $$

create procedure `_gettesteetestmodes`(
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/10/2015		Sai V. 			--
*/
	v_clientname varchar(100)
  , v_testid varchar(200)
  , v_testee bigint
  , v_sessiontype int
  , v_modelist text -- = null -- this parameter for debugging purposes only
  , v_debug bit -- = 0 -- this parameter for debugging purposes only
)
sql security invoker
proc: begin 

    declare  v_tideid varchar(200);
	declare v_modefield varchar(50);
	declare v_requiremode, v_requiremodewindow bit;

	drop temporary table if exists tmp_tblmodes;
    create temporary table tmp_tblmodes(
		rtsval varchar(200)
	  , wid varchar(100)
	  , asgnmode varchar(50)
	)engine = memory;

	drop temporary table if exists tmp_tblrtsvals;
    create temporary table tmp_tblrtsvals(record varchar(200));

	drop temporary table if exists tmp_tblgetcurrenttestwindows;
	create temporary table tmp_tblgetcurrenttestwindows(
		windowid		varchar(50)	
	  , windowmax		int
	  , startdate		datetime(3)
	  , enddate			datetime(3)
	  , `mode`			varchar(50)
	  , modemax			int
	  , testkey			varchar(250)
	)engine = memory;
   
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


    if (v_testee < 0) then  -- guest testees have no rts data. if allowed into the system this far, then provide them all modes
		insert into tblout_gettesteetestmodes
		select * from tmp_tblgetcurrenttestwindows;
        leave proc;
    end if;
    
	-- note: it is an error for v_require = 1 and v_modefield = null, however, it is also highly unlikely since both fields have default values in tdsconfigs
    select tide_id, requirertsmodewindow, rtsmodefield, requirertsmode
	into v_tideid, v_requiremodewindow, v_modefield, v_requiremode
    from configs.client_testproperties 
	where clientname = v_clientname and testid = v_testid;

    if (v_modelist is not null) then 
	begin -- this block sets up debugging capabilities by simulating conditions we expect to find
        if (locate('&', v_modelist) > 0) then
            set v_requiremodewindow = 1;
        else 
		begin 
            set v_requiremode = 1;
            set v_requiremodewindow = 0;
        end;
		end if;
    end;
    elseif ((v_requiremodewindow = 1 or v_requiremode = 1) and v_modefield is not null) then   -- this is live production condition
        call _getrtsattribute(v_clientname, v_testee, v_modefield, v_modelist /*output*/);
	end if;

	-- note: we cannot rely on all ';'-delimited values in the test modes field to be window-discriminated. only those for this test
	-- and we can only tell which test we are looking at by the intersection between the modes and the tests
	-- so we have to split this string 2 ways: with the window and without the window
    if (v_modelist is not null) then
	begin
		/* Call _buildtable stored procedure 
		-- To capture and use result set from _buildtable, a temporary table is created to store the resultset */
		drop temporary table if exists tblout_buildtable;
		create temporary table tblout_buildtable(idx int, record varchar(255));
			  
		call _buildtable(v_modelist, ';');

		insert into tmp_tblrtsvals (record)
		select record 
		from tblout_buildtable;
		/* # */

		-- each component is of the form <tideid>:<mode> or <tideid>&<windowid>:<mode>
		-- first, split the string for the tide_id (which is required)
        insert into tmp_tblmodes (rtsval, asgnmode)
        select substring_index(record, ':', 1), substring_index(record, ':', -1)
        from tmp_tblrtsvals 
		where record like concat(v_tideid, ':%') or record like concat(v_tideid, '&%:%');

		-- now, parse the windowid out, if applicable
        update tmp_tblmodes 
		set wid = substring_index(rtsval, '&', -1) 
		where locate('&', rtsval) > 0;
    end;
	end if;

    if (v_requiremodewindow = 1 ) then 
	begin
		insert into tblout_gettesteetestmodes
        select distinct windowid, windowmax, startdate, enddate,  mode, modemax, testkey
        from tmp_tblmodes, tmp_tblgetcurrenttestwindows
        where wid = windowid and `mode` = asgnmode;

		if (v_debug = 1) then 
			select 'require mode window'; 
			select * from tblout_gettesteetestmodes;
		end if;

        leave proc;
    end;
    elseif (v_requiremode = 1 ) then 
	begin
		-- window is not assigned, just the mode, so just join the modes
		insert into tblout_gettesteetestmodes
        select distinct windowid, windowmax, startdate, enddate,  `mode`, modemax, testkey
        from tmp_tblmodes, tmp_tblgetcurrenttestwindows
        where `mode` = asgnmode;

		if (v_debug = 1) then 
			select 'require mode'; 
			select * from tblout_gettesteetestmodes;
		end if;

        leave proc;
    end;
	end if;

    -- else there is no specific mode assignment to the testee: return all active windows all modes
	insert into tblout_gettesteetestmodes
    select windowid, windowmax, startdate, enddate, `mode`, modemax, testkey
    from tmp_tblgetcurrenttestwindows;

	if (v_debug = 1) then 
		select  'mode not required'; 
		select * from tblout_gettesteetestmodes;
	end if;
    
end $$

DELIMITER ;
