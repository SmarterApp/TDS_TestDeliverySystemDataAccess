DELIMITER $$

drop procedure if exists `_selecttestform_predetermined` $$

create procedure `_selecttestform_predetermined`(
/*
Description: Select a test form for this new opportunity if one exists
			-- This form selection algorithm reads the RTS for a predetermined test form
			-- IMPORTANT: v_testkey is required because, if the main test is segmented, then the form is on a segment, not the main test. So the caller MUST provide the testkey

VERSION 	DATE 			AUTHOR 			COMMENTS
001			01/23/2014		Sai V. 			Converted code from SQL Server to MySQL

*/
	v_oppkey varbinary(16)
  , v_testkey varchar(250)
  , v_lang varchar(50)
  , out v_formkey varchar(50)
  , out v_formid varchar(150)
  , out v_itemcnt int
  , v_sessiontype int
  , v_formlist text -- lists are for debugging
)
proc: begin

    declare v_environment, v_clientname varchar(100);
    declare v_testee bigint;
	declare v_testid varchar(150);
	declare v_opportunity int;
    declare v_date datetime(3);
    declare v_session varbinary(16);

	set v_formkey = null;
	set v_formid = null;
	set v_date = now(3);	


    select clientname, _efk_testee, _efk_testid, _fk_session
	into v_clientname, v_testee, v_testid, v_session
    from testopportunity 
	where _key = v_oppkey;

    select environment into v_environment
	from externs 
	where clientname = v_clientname;

    
	-- NOTE: this is all the forms assigned to this student whether for this test or not.
	call _gettesteetestforms (v_clientname, v_testid, v_testee, v_sessiontype,  v_formlist, 0);

	-- select * from tblout_gettesteetestforms;
	/*********** tblout_gettesteetestforms: is declared in stored procedure _gettesteetestforms *******************/

    update tblout_gettesteetestforms f
		 , (  select s.formkey, count(*) cnt
			  from testopportunity o, testopportunitysegment s  
			  where clientname = v_clientname and _efk_testee = v_testee and s._fk_testopportunity = o._key
			  group by s.formkey ) t
	set f.cnt = t.cnt
    where t.formkey = f.formkey;

	drop temporary table if exists tblforms;
	create temporary table tblforms (_formkey varchar(50), id varchar(150), itemcnt int, formcnt int);

    insert into tblforms (_formkey, id, formcnt, itemcnt)	
    select f._key
		 , f.formid
		 , 0
		 , (select count(*) 
			from itembank_testformitem i where i._fk_adminsubject = v_testkey and i._fk_testform = f._key) 
    from itembank_testform f
    where f.`language` = v_lang and f._fk_adminsubject = v_testkey ;


    update tblforms f
		 , (  select s.formkey, count(*) cnt
			  from testopportunity o, testopportunitysegment s  
			  where clientname = v_clientname and  _efk_segment = v_testkey and s._fk_testopportunity = o._key
					and _fk_testopportunity = _key and (v_environment <> 'simulation' or _fk_session = v_session)
			  group by s.formkey ) t
	set formcnt = t.cnt 
	where formkey = _formkey;

	-- select * from tblforms
    	
	select id, itemcnt, _formkey
	into v_formid, v_itemcnt, v_formkey
    from tblforms f, tblout_gettesteetestforms a
    where f._formkey = a.formkey
    order by a.cnt, formcnt, rand()
	limit 1;


end$$

DELIMITER ;