DELIMITER $$

drop function if exists `gettestformwindows` $$

create function `gettestformwindows`(
/*
DESCRIPTION: Create function in SESSION db
			 -- ** Objects referecing this function needs to declare temporary table (tblout_gettestformwindows) **

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/23/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_clientname varchar(100)
  , v_testid varchar(200)
  , v_sessiontype int)
returns bit
begin

	insert into tblout_gettestformwindows	
	-- match test windows with form start/end dates for unsegmented tests
    select windowid, w.numopps as windowmax, m.maxopps as modemax
		 , case when w.startdate is null then now(3) else date_add(w.startdate, interval shiftwindowstart day) end  as startdate
		 , case when w.enddate is null then now(3) else date_add(w.enddate, interval shiftwindowend day) end  as enddate
         , case when f.startdate is null then now(3) else date_add(f.startdate, interval shiftformstart day) end as formstart 
         , case when f.enddate is null then now(3) else date_add(f.enddate, interval shiftformend day) end as formend
         , _efk_testform as formkey, formid, f.`language`
         , m.`mode`, m.testkey, w.sessiontype as windowsession, m.sessiontype as modesession
    from tdsconfigs_client_testwindow w, tdsconfigs_client_testformproperties f, tdsconfigs_client_testmode m, itembank_tblsetofadminsubjects bank, _externs e
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
		 , case when w.startdate is null then now(3) else date_add(w.startdate, interval shiftwindowstart day) end  as startdate
		 , case when w.enddate is null then now(3) else date_add(w.enddate, interval shiftwindowend day) end  as enddate
         , case when f.startdate is null then now(3) else date_add(f.startdate, interval shiftformstart day) end as formstart 
         , case when f.enddate is null then now(3) else date_add(f.enddate, interval shiftformend day) end as formend
		 , _efk_testform as formkey, formid, f.`language`
		 , m.`mode`, m.testkey as testkey, w.sessiontype , m.sessiontype 
    from tdsconfigs_client_testwindow w, tdsconfigs_client_testformproperties f, tdsconfigs_client_segmentproperties s
        , tdsconfigs_client_testmode m, itembank_tblsetofadminsubjects bank, _externs e
    where s.clientname = v_clientname and f.clientname = v_clientname and f.testkey = bank._key and s.parenttest = v_testid
        and m.clientname = v_clientname and m.testid = v_testid and (m.sessiontype = -1 or m.sessiontype = v_sessiontype)
        and s.modekey = m.testkey and s.segmentid = bank.testid
        and e.clientname = v_clientname
        and now(3) between case when f.startdate is null then now(3) else date_add(f.startdate, interval shiftformstart day) end 
                         and case when f.enddate is null then now(3) else date_add(f.enddate, interval shiftformend day) end 
        and now(3) between case when w.startdate is null then now(3) else date_add(w.startdate, interval shiftwindowstart day) end 
                         and case when w.enddate is null then now(3) else date_add(w.enddate, interval shiftwindowend day) end
        and w.clientname = v_clientname and w.testid = s.parenttest 
        and (w.sessiontype = -1 or w.sessiontype = v_sessiontype);


	return 1;
	
end$$

DELIMITER ;