DELIMITER $$

drop procedure if exists `_gettesteetestmodes` $$

create procedure `_gettesteetestmodes`(
/*
Description:

VERSION 	DATE 			AUTHOR 			COMMENTS
001			01/14/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_clientname varchar(100)
  , v_testid varchar(200)
  , v_testee bigint
  , v_sessiontype int
  , v_modelist text
)
proc: begin  

    declare v_tideid varchar(200);
	declare v_modefield varchar(50);
	declare v_requiremode, v_requiremodewindow bit;
    
	drop temporary table if exists tblmodes;
	create temporary table tblmodes (modes varchar(200), wid varchar(100), asgnmode varchar(50));

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

    if (v_testee < 0) then  -- guest testees have no rts data. if allowed into the system this far, then provide them all modes        
		select windowid, windowmax, startdate, enddate, `mode`, modemax, testkey 
		from tblout_getcurrenttestwindows;

        leave proc;
    end if;
    
	-- note: it is an error for v_require = 1 and v_modefield = null, however, it is also highly unlikely since both fields have default values in tdsconfigs
    select tide_id, requirertsmodewindow, rtsmodefield, requirertsmode
	into v_tideid, v_requiremodewindow, v_modefield, v_requiremode
    from tdsconfigs_client_testproperties 
	where clientname = v_clientname 
		and testid = v_testid;

    if (v_modelist is not null) then -- this block sets up debugging capabilities by simulating conditions we expect to find in the rts   
        if (locate('&', v_modelist) > 0) then
            set v_requiremodewindow = 1;
        else 
		begin 
            set v_requiremode = 1;
            set v_requiremodewindow = 0;
        end;
		end if;
    end if;

	-- NOTE: we cannot rely on all ';'-delimited values in the test modes field to be window-discriminated. only those for this test
	-- and we can only tell which test we are looking at by the intersection between the modes and the tests
	-- so we have to split this string 2 ways: with the window and without the window
    if (v_modelist is not null) then
		drop temporary table if exists tblout_buildtable;
		create temporary table tblout_buildtable (idx int, record varchar(255));

		call _buildtable(v_modelist, ';');

		-- each component is of the form <tideid>:<mode> or <tideid>&<windowid>:<mode>
		-- first, split the string for the tide_id (which is required)
        insert into tblmodes (modes, asgnmode)
        select left(record, locate(':', record) - 1), right(record, length(record) - locate(':', record))
        from tblout_buildtable 
		where record like concat(v_tideid, ':%') 
			or record like concat(v_tideid, '&%:%');

		-- now, parse the windowid out, if applicable
        update tblmodes 
		set wid = right(modes, length(modes) - locate('&', modes)) 
		where locate('&', modes) > 0;

		-- select * from tblmodes;
    end if;


    -- rts has modes assigned to specific windows in this first case
    if (v_requiremodewindow = 1 ) then 
	begin
		-- select 'require mode window';
        select distinct windowid, windowmax, startdate, enddate, `mode`, modemax, testkey
        from tblmodes, tblout_getcurrenttestwindows
        where wid = windowid 
			and `mode` = asgnmode;

        leave proc;
    end;
    else if (v_requiremode = 1 ) then
	begin
		-- select 'require mode';
		-- window is not assigned, just the mode, so just join the modes
        select distinct windowid, windowmax, startdate, enddate, `mode`, modemax, testkey
        from tblmodes, tblout_getcurrenttestwindows
        where `mode` = asgnmode;

        leave proc;
    end;
	end if;
	end if;

	-- select 'mode not required'
    -- else there is no specific mode assignment to the testee: return all active windows all modes
    select windowid, windowmax, startdate, enddate, `mode`, modemax, testkey
    from tblout_getcurrenttestwindows;
    

end$$

DELIMITER ;



