DELIMITER $$

drop function if exists `getcurrenttestwindows` $$

create function `getcurrenttestwindows`(
/*
DESCRIPTION: Gets open test windows
			 -- ** Objects referecing this function needs to declare temporary table (tblout_getcurrenttestwindows) **

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/14/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_clientname varchar(100)
  , v_testid varchar(200)
  , v_sessiontype int)
returns bit
begin

	insert into tblout_getcurrenttestwindows	
    select distinct w.numopps as windowmax
		 , w.windowid
		 , case when w.startdate is null then now(3) else date_add(w.startdate, interval shiftwindowstart day) end as startdate
		 , case when w.enddate is null then now(3) else date_add(w.enddate, interval shiftwindowend day) end as enddate
		 , m.mode
		 , m.testkey
		 , m.maxopps as modemax
		 , w.sessiontype as windowsession
		 , m.sessiontype as modesession
    from tdsconfigs_client_testwindow w, tdsconfigs_client_testmode m, _externs e
    where w.clientname = v_clientname 
		and w.testid = v_testid 
        and e.clientname = v_clientname 
        and now(3) between case when w.startdate is null then now(3) else date_add(w.startdate, interval shiftwindowstart day) end
                     and case when w.enddate is null then now(3) else date_add(w.enddate, interval shiftwindowend day) end
        and m.clientname = v_clientname and m.testid = v_testid
        and (m.sessiontype = -1 or m.sessiontype = v_sessiontype)
        and (w.sessiontype = -1 or w.sessiontype = v_sessiontype);


	return 1;
	
end$$

DELIMITER ;