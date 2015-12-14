DELIMITER $$

drop procedure if exists `_selecttestform_driver` $$

create procedure `_selecttestform_driver`(
/*
Description: -- select a test form for this new opportunity if one exists
			-- this procedure changed to a 'driver' which selects which form algorithm, if any, to use

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/06/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_oppkey varbinary(16)
  ,	v_testkey varchar(250)
  , v_language varchar(50)
  , out v_formkey varchar(50) 	/*output*/
  , out v_formid varchar(150) 	/*output*/
  , out v_formlength int 		/*output*/
  , v_formlist text -- = null
  , v_formcohort varchar(20) -- = null
  , v_debug bit -- = 0
)
sql security invoker
proc: begin

    declare v_clientname varchar(100);
	declare v_parenttest varchar(250);
	declare v_parenttestid varchar(100);
	declare v_sessiontype int;
    declare v_fromrts int;
    declare v_ifrts bit;

	declare v_procname varchar(100);
	declare v_starttime datetime(3);
	set v_starttime = now(3);
	set v_procname = '_selecttestform_driver';
		
    select o.clientname, _efk_testid, _efk_adminsubject, sessiontype
	into v_clientname, v_parenttestid, v_parenttest, v_sessiontype
    from testopportunity o, `session` s 
	where o._key = v_oppkey and o._fk_session = s._key;

	-- form predetermination is set at the parent test level, not the segment level. so use the parent test id to find out
    set v_fromrts = 0;

    select (cast(requirertsform as unsigned) + cast(requirertsformwindow as unsigned)), requirertsformifexists
	into v_fromrts, v_ifrts
    from configs.client_testmode 
	where testkey = v_parenttest;

	set v_formkey = null;
	set v_formid = null;
    set v_formlength = null;

	if (v_debug = 1) then
        select v_parenttest parent, v_fromrts fromrts, v_ifrts ifrts;
		select hex(v_oppkey), v_testkey, v_language, v_sessiontype, v_formlist, v_formcohort;
	end if;
    
    if (v_formcohort is null and (v_fromrts > 0 or v_formlist <> null or v_ifrts = 1)) then 
		-- use formlist for debugging or for overriding the distribution algorithm 
        if (v_debug = 1) then select 'predetermined'; end if;
        call _selecttestform_predetermined(v_oppkey, v_testkey, v_language, v_formkey /*output*/, v_formid /*output*/, v_formlength /*output*/, v_sessiontype, v_formlist, 0);
		if (v_debug = 1) then select '_selecttestform_predetermined', v_formkey, v_formid, v_formlength; end if;
    else 
        if (v_debug = 1)  then select 'algorithmic'; end if;
        call _selecttestform_eqdist(v_oppkey, v_testkey, v_language, v_formkey /*output*/, v_formid /*output*/, v_formlength /*output*/, v_formcohort, 0);
		if (v_debug = 1) then select '_selecttestform_eqdist', v_formkey, v_formid, v_formlength; end if;
	end if;

    if (v_ifrts = 1 and v_formkey is null) then
		if (v_debug = 1) then select 'algorithmic'; end if;
		call _selecttestform_eqdist(v_oppkey, v_testkey, v_language, v_formkey /*output*/, v_formid /*output*/, v_formlength /*output*/, v_formcohort, 0);
		if (v_debug = 1) then select '_selecttestform_eqdist', v_formkey, v_formid, v_formlength; end if;
	end if;

    if (v_formkey is null or v_formid is null or v_formlength is null) then
		call _logdberror('_selecttestform_driver', 'unable to select test form', null, null, null, v_oppkey, v_clientname, null);
        leave proc;
    end if;

	call _logdblatency(v_procname, v_starttime, null, null, null, v_oppkey, null, null, null);

end $$

DELIMITER ;