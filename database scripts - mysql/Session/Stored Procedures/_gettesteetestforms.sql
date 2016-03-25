DELIMITER $$

drop procedure if exists `_gettesteetestforms` $$

create procedure `_gettesteetestforms`(
/*
Description: Get Test Forms data

DEPENDENCIES: -- _selecttestform_predetermined (sp in SESSION db)

VERSION 	DATE 			AUTHOR 			COMMENTS
001			01/14/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_clientname varchar(100)
  , v_testid varchar(200)
  , v_testee bigint
  , v_sessiontype int
  , v_formlist text 
  , v_debug bit /* this parameter for debugging purposes only */ 
)
proc: begin  

    declare v_tideid varchar(200);
	declare v_requireformwindow, v_requireform, v_ifexists bit;
	declare v_formfield varchar(50);
    
	drop temporary table if exists tblforms;
	create temporary table tblforms ( wid varchar(100), form varchar(50));

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

	drop temporary table if exists tblout_gettesteetestforms;
    create temporary table tblout_gettesteetestforms (
		  testkey 	varchar(250)
		, windowid 	varchar(100)
		, windowmax int
		, `mode` 	varchar(50)
		, modemax 	int
		, numopps 	int
		, startdate datetime(3)
		, enddate 	datetime(3)
		, formkey 	varchar(50)
		, cnt 		int default 0
	);

    select gettestformwindows(v_clientname, v_testid, v_sessiontype);

    if (v_testee < 0) then  -- if 'guest' testees are allowed into the system, then they qualify for all forms by default since there is no rts data for them
		insert into tblout_gettesteetestforms (windowid, windowmax, startdate, enddate, formkey, `mode`, modemax, testkey)
        select windowid, windowmax, startdate, enddate, formkey, `mode`, modemax, testkey
        from tblout_gettestformwindows;
		
		select windowid, windowmax, startdate, enddate, formkey, `mode`, modemax, testkey from tblout_gettesteetestforms;
		
       leave proc;
    end if;
    
	-- note: it is an error for v_require = 1 and v_formfield = null
    select tide_id, requirertsformwindow, rtsformfield, requirertsform, requirertsformifexists
	into v_tideid, v_requireformwindow, v_formfield, v_requireform, v_ifexists
    from tdsconfigs_client_testproperties t, tdsconfigs_client_testmode f
    where t.clientname = v_clientname and t.testid = v_testid and f.clientname = v_clientname and f.testid = v_testid
        and (sessiontype = -1 or sessiontype = v_sessiontype);


    if (v_formlist is not null ) then -- this block sets up debugging capabilities by simulating conditions we expect to find in the rts   
        if (locate(':', v_formlist) > 0) then
            set v_requireformwindow = 1;
        else begin 
            set v_requireform = 1;
            set v_requireformwindow = 0;
        end;
		end if;
	end if;

    if (v_debug = 1) then 
		select v_tideid tideid, v_formfield formfield, v_requireformwindow reqwin, v_requireform reqform, v_ifexists ifexists, v_formlist forms;
	end if;

	-- note: we cannot rely on all ';'-delimited values in the test forms field to be window-discriminated. only those for this test
	-- and we can only tell which test we are looking at by the intersection between the forms and the tests
	-- so we have to split this string 2 ways: with the window and without the window
    if (v_formlist is not null and v_tideid is not null) then
		drop temporary table if exists tblout_buildtable;
		create temporary table tblout_buildtable (idx int, record varchar(255));

		call _buildtable(v_formlist, ';');

		-- first, split the string for all values that have the window
        insert into tblforms (wid, form)
        select left(record, locate(':', record) - 1), right(record, length(record) - locate(':', record))
        from tblout_buildtable 
		where locate(':', record) > 0;

        insert into tblforms (form)
        select record
        from tblout_buildtable 
		where locate(':', record) = 0;
        
        if (v_debug = 1) then select * from tblforms; end if;
    end if;


    -- rts has forms assigned to specific windows in this first case
    if (v_requireformwindow = 1 ) then 
		insert into tblout_gettesteetestforms (windowid, windowmax, startdate, enddate, formkey, `mode`, modemax, testkey)
        select distinct windowid, windowmax, startdate, enddate, formkey, `mode`, modemax, testkey
        from tblforms, tblout_gettestformwindows
        where wid = windowid 
			and form = formkey;
	
		select windowid, windowmax, startdate, enddate, formkey, `mode`, modemax, testkey from tblout_gettesteetestforms;

		if (v_debug = 1) then select 'exit 1'; end if;
        
		leave proc;
    end if;

    if (v_requireform = 1) then
		-- window is not required, just the form, so just join the forms
		insert into tblout_gettesteetestforms (windowid, windowmax, startdate, enddate, formkey, `mode`, modemax, testkey)
        select distinct windowid, windowmax, startdate, enddate, formkey, `mode`, modemax, testkey
        from tblforms, tblout_gettestformwindows
        where form = formkey;  

		select windowid, windowmax, startdate, enddate, formkey, `mode`, modemax, testkey from tblout_gettesteetestforms;
		
		if (v_debug = 1) then select 'exit 2'; end if;
		
		leave proc;
	end if;
    
	if (v_ifexists = 1 and exists (select * from tblforms, tblout_gettestformwindows where form = formkey)) then
		--  window is not required, just the form, so just join the forms
		insert into tblout_gettesteetestforms (windowid, windowmax, startdate, enddate, formkey, `mode`, modemax, testkey)
		select distinct windowid, windowmax, startdate, enddate, formkey, `mode`, modemax, testkey
		from tblforms, tblout_gettestformwindows
		where form = formkey;  

		select windowid, windowmax, startdate, enddate, formkey, `mode`, modemax, testkey from tblout_gettesteetestforms;
		
		if (v_debug = 1) then select 'exit 3'; end if;
            
		leave proc;
	end if;

    if (v_debug = 1) then select 'exit last'; end if;

    -- else there is no specific form assignment to the testee: return all active windows all forms
	insert into tblout_gettesteetestforms (windowid, windowmax, startdate, enddate, formkey, `mode`, modemax, testkey)
    select windowid, windowmax, startdate, enddate, formkey, `mode`, modemax, testkey
    from tblout_gettestformwindows;

	select windowid, windowmax, startdate, enddate, formkey, `mode`, modemax, testkey from tblout_gettesteetestforms;    

end$$

DELIMITER ;



