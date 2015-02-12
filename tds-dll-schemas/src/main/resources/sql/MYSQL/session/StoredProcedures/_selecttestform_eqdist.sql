DELIMITER $$

drop procedure if exists `_selecttestform_eqdist` $$

create procedure `_selecttestform_eqdist`(
/*
Description: select a test form for this new opportunity if one exists
			-- this form selection algorithm uses equal distribution to select a test form

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/06/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_oppkey 	varbinary(16)
  ,	v_testkey 	varchar(250)
  , v_lang 		varchar(50)
  , out v_formkey 	 varchar(50) 	/*output*/
  , out v_formid 	 varchar(150) 	/*output*/
  , out v_formlength int 			/*output*/
  , v_formcohort 	 varchar(20) -- = null
  , v_debug 		 bit -- = 0
)
sql security invoker
proc: begin

    declare v_clientname varchar(100);
    declare v_environment varchar(100);
    declare v_testee bigint;
    declare v_parenttest varchar(250);
    declare v_session varbinary(16);
    declare v_date datetime;
    declare v_formcount int;
    declare v_query text; 

    set v_date = now(3);
	set v_formkey = null;
	set v_formid = null;

    select clientname, _efk_adminsubject, _efk_testee , _fk_session
	into v_clientname, v_parenttest, v_testee, v_session
    from testopportunity where _key = v_oppkey;
	
    set v_environment = (select environment from externs where clientname = v_clientname);

	drop temporary table if exists tmp_tblforms;
    create temporary table tmp_tblforms(
		_formkey varchar(50)
	  , id varchar(150)
	  , itemcnt int
	  , usercnt int
	  , formcnt int
	);

	-- usercnt is how many times v_testee has been administered this form.
	-- formcnt is how many times anyone has been adminstered this form.
    insert into tmp_tblforms (_formkey, id, itemcnt)	
    select f._key
		 , f.formid
		 , (select count(*) 
			  from itembank.testformitem i where i._fk_adminsubject = v_testkey and i._fk_testform = f._key) 
    from itembank.testform f, tdsconfigs.client_testformproperties p
    where f.`language` = v_lang and f._fk_adminsubject = v_testkey and f._key = p._efk_testform and p.clientname = v_clientname
        and (v_formcohort is null or cohort = v_formcohort)
		and ((p.startdate is null or v_date > p.startdate) and (p.enddate is null or v_date < p.enddate));
    
    set v_formcount = (select count(*) from tmp_tblforms);

    if (v_formcount = 0 and v_debug = 1) then select * from tmp_tblforms; end if;
    if (v_formcount = 0) then leave proc; end if;
    if (v_formcount = 1) then
        select _formkey, id, itemcnt
		into v_formkey, v_formid, v_formlength
        from tmp_tblforms
		limit 1;

        leave proc;
    end if;
	
	update tmp_tblforms 
	set usercnt = (select count(*) 
					 from testopportunity, testopportunitysegment
					where clientname = v_clientname and _efk_testee = v_testee and _fk_testopportunity = _key and _efk_segment = v_testkey and formkey = _formkey
						and (v_environment <> 'simulation' or _fk_session = v_session))
	  , formcnt = (select count(*) 
					 from testopportunity, testopportunitysegment
					where clientname = v_clientname and _fk_testopportunity = _key and _efk_segment = v_testkey and formkey = _formkey
						and (v_environment <> 'simulation' or _fk_session = v_session));

    if (v_debug = 1) then 
		select * from tmp_tblforms order by usercnt, formcnt, uuid();
	end if;

	select _formkey, id, itemcnt
	into v_formkey, v_formid, v_formlength
	from tmp_tblforms
	order by usercnt, formcnt, uuid()
	limit 1;

end $$

DELIMITER ;