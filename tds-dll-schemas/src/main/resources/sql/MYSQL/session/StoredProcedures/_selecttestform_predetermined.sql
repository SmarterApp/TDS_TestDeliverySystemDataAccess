DELIMITER $$

drop procedure if exists `_selecttestform_predetermined` $$

create procedure `_selecttestform_predetermined`(
/*
Description: select a test form for this new opportunity if one exists
			-- this form selection algorithm reads the rts for a predetermined test form

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/06/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_oppkey varbinary(16)
  ,	v_testkey varchar(250)
  , v_lang varchar(50)
  , out v_formkey varchar(50) 	/*output*/
  , out v_formid varchar(150) 	/*output*/
  , out v_itemcnt int 			/*output*/
  , v_sessiontype int
  , v_formlist text -- = null
  , v_debug bit -- = 0
)
sql security invoker
proc: begin

    declare v_environment varchar(100);
    declare v_clientname varchar(100);
    declare v_session varbinary(16);	
    declare v_testee bigint;
	declare v_testid varchar(150);
	declare v_opportunity int;
    declare v_date datetime(3);

	declare v_procname varchar(100);
	declare v_starttime datetime(3);
	set v_starttime = now(3);
	set v_procname = '_selecttestform_predetermined';

	set v_formkey = null;
	set v_formid = null;
    
	drop temporary table if exists tmp_tblassigned;
    create temporary table tmp_tblassigned(
		testkey 	varchar(250)
	  , window 		varchar(100)
	  , windowmax 	int
	  , `mode` 		varchar(50)
	  , modemax 	int
	  , numopps 	int
	  , startdate 	datetime(3)
	  , enddate 	datetime(3)
	  , frmkey 		varchar(50)
	  , cnt 		int default 0
	);

    set v_date = now(3);

    select clientname, _efk_testee, _efk_testid, _fk_session
	into v_clientname, v_testee, v_testid, v_session
    from testopportunity 
	where _key = v_oppkey;
	-- lock in share mode;

    set v_environment = (select environment from externs where clientname = v_clientname);

	if (v_debug = 1) then select '_gettesteetestforms', v_clientname, v_testid, v_testee, v_sessiontype,  v_formlist; end if;

	/* Call _gettesteetestforms stored procedure 
	-- To capture and use result set from _gettesteetestforms, a temporary table is created to store the resultset */	
	call _gettesteetestforms(v_clientname, v_testid, v_testee, v_sessiontype,  v_formlist, 0, 0);
    
    insert into tmp_tblassigned (window, windowmax, startdate, enddate, frmkey, mode, modemax, testkey)
	select * from tblout_gettesteetestforms;


    if (v_debug = 1) then select 'tmp_tblassigned', a.* from tmp_tblassigned a; end if;


    update tmp_tblassigned 
	set cnt = (select count(*) 
				 from testopportunity o, testopportunitysegment s
				where clientname = v_clientname and _efk_testee = v_testee and s._fk_testopportunity = o._key and formkey = frmkey);
			  -- lock in share mode);
    
	drop temporary table if exists tmp_tblforms;
	create temporary table tmp_tblforms(_formkey varchar(50), id varchar(150), itemcnt int, formcnt int);

    insert into tmp_tblforms (_formkey, id, formcnt, itemcnt)	
    select f._key
		 , f.formid
		 , 0
		 , (select count(*) 
			  from itembank.testformitem i where i._fk_adminsubject = v_testkey and i._fk_testform = f._key) 
    from itembank.testform f
    where f.`language` = v_lang and f._fk_adminsubject = v_testkey;

    update tmp_tblforms 
	set formcnt = (select count(*) 
					 from testopportunity, testopportunitysegment
					where clientname = v_clientname and _fk_testopportunity = _key and _efk_segment = v_testkey and formkey = _formkey
						and (v_environment <> 'simulation' or _fk_session = v_session));

    if (v_debug = 1) then select * from tmp_tblforms; end if;
    	
	select id, itemcnt, _formkey
	into v_formid, v_itemcnt, v_formkey
    from tmp_tblforms f, tmp_tblassigned a
    where f._formkey = a.frmkey
    order by a.cnt, formcnt, rand()
	limit 1;
    
	-- clean-up
	drop temporary table tblout_gettesteetestforms;
	drop temporary table tmp_tblforms;
	drop temporary table tmp_tblassigned;

	call _logdblatency(v_procname, v_starttime, null, null, null, v_oppkey, null, null, null);

end $$

DELIMITER ;
