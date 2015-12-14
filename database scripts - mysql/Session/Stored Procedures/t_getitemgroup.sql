DELIMITER $$

drop procedure if exists `t_getitemgroup` $$

create procedure `t_getitemgroup`(
/*
Description:

VERSION 	DATE 			AUTHOR 			COMMENTS
001			01/09/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_oppkey varbinary(16)
  , v_pagenumber int
  , v_groupid varchar(50)
  , v_datecreated varchar(50)
  , v_sessionid varbinary(16)
  , v_browserid varbinary(16)
  , v_validateaccess bit)
proc: begin  

	declare v_starttime datetime(3);
	declare v_errormsg varchar(200);
    declare v_testkey varchar(250);
    declare v_testid varchar(200);
    declare v_clientname varchar(100);
    declare v_printtypes text;
    declare v_printtest bit;
    declare v_restart int;

	-- initilize variables
	if v_validateaccess is null then set v_validateaccess = 1; end if;
	set v_starttime = now(3);
    
	
    if (v_validateaccess = 1 and v_sessionid is not null and v_browserid is not null) then        
        call _validatetesteeaccessproc (v_oppkey, v_sessionid, v_browserid, 0, v_errormsg);

        if (v_errormsg is not null) then
            select 'failed' as `status`, v_errormsg as reason, '_validatetesteeaccess' as context, null as argstring, null as `delimiter`; 
            -- exec _returnerror null, @@procid,v_errormsg, null, v_oppkey, v_context = '_validatetesteeaccess', v_status = 'failed'; 
            leave proc;
        end if;
    end if;


    select clientname, _efk_testid, _efk_adminsubject, restart
	into v_clientname, v_testid, v_testkey, v_restart
    from testopportunity 
	where _key = v_oppkey;

    select concat('|', printitemtypes, '|'), isprintable
	into v_printtypes, v_printtest
    from tdsconfigs_client_testproperties 
	where clientname = v_clientname and testid = v_testid;

    select _efk_itsbank as itembank
		 , _efk_itsitem as item
		 , position
		 , `page`
		 , score
		 , mark
		 , response
		 , r.isfieldtest
		 , isselected
		 , r.isrequired
		 , `format`
         , (case when isinactive = 1 then 0
				 when opportunityrestart = v_restart then 1
				 else 0
			end) as isvisible
		 , 0 as readonly
		 , r.groupid
		 , cast(dategenerated as char) as datecreated
		 , responsesequence
		 , responselength
		 , isvalid
		 , groupitemsrequired
		 , r.segment
		 , r.segmentid
		 -- item file path, stimulus file path, and compute whether item is printable
		 , tdscore_dev_itembank2012_sandbox.clientitemfile(v_clientname, _efk_itemkey) as itemfile
		 , tdscore_dev_itembank2012_sandbox.clientitemstimuluspath(v_clientname, s._efk_segment, _efk_itemkey) as stimulusfile
		 , case when v_printtest = 1 or locate(concat('|', r.format, '|'), v_printtypes) > 0 or i.isprintable = 1 then 1 else 0 end as isprintable
		 , (case isinactive         -- this value is needed to prevent from administering an inactivated item that is still (or again) in the item pool
				when 1 then -1 
				else opportunityrestart
			end) as opportunityrestart			
	from testeeresponse r, testopportunitysegment s, itembank_tblsetofadminitems i
	where r._fk_testopportunity  = v_oppkey 
		and s._fk_testopportunity = v_oppkey
        and r.segment = s.segmentposition
        and `page` = v_pagenumber 
        and i._fk_adminsubject = s._efk_segment and i._fk_item = r._efk_itemkey
        and (v_groupid is null or r.groupid = v_groupid) and (v_datecreated is null or v_datecreated = dategenerated)
    order by position;

    -- exec _logdblatency v_v_procid, v_starttime, null, 1, v_testoppkey = v_oppkey;

end$$

DELIMITER ;



