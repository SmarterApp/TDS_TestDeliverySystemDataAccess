DELIMITER $$

drop procedure if exists t_getitemgroup $$

create procedure t_getitemgroup (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/29/2015		Elena Furman 	Converted code from T-SQL to MySQL
*/
  	v_oppkey 		varbinary(16)
  , v_pagenumber 	int
  , v_groupid 		varchar(50) -- = null
  , v_datecreated 	varchar(50) -- = null
  , v_sessionid 	varbinary(16) -- = null
  , v_browserid 	varbinary(16) -- = null
  , v_validateaccess bit -- = 1
)
proc: begin
 
	declare v_starttime datetime(3);
    declare v_error varchar(128);    
    declare v_procname varchar(200);
    declare v_testkey varchar(250);
    declare v_testid varchar(200);
    declare v_clientname varchar(100);
    declare v_printtypes text;
    declare v_printtest bit;
    declare v_restart int;
     
     set v_starttime = now(3);
     set v_procname = 't_getitemgroup';
           
     if (v_validateaccess = 1 and v_sessionid is not null and v_browserid is not null) then 
     begin
		call _validatetesteeaccessproc (v_oppkey, v_sessionid, v_browserid, 0, v_error);

		if (v_error is not null) then 
		begin
           call _returnerror (null, v_procname, v_error, null, v_oppkey, '_validatetesteeaccess', 'failed');
           leave proc;
       end;
       end if;           
    end;
    end if;

    select clientname, _efk_testid, _efk_adminsubject, restart
    into v_clientname, v_testid, v_testkey, v_restart    
    from testopportunity 
	where _key = v_oppkey;
    
    select concat('|', printitemtypes, '|') , isprintable  
    into v_printtypes, v_printtest
    from  configs.client_testproperties 
	where clientname = v_clientname and testid = v_testid;
  
    select s._efk_segment as testkey
		 , r._efk_itemkey as itemkey
		 , _efk_itsbank   as itembank
         , _efk_itsitem   as item 
         , position
		 , `page`
		 , score
		 , mark
		 , response
		 , r.isfieldtest
		 , isselected
		 , r.isrequired
		 , `format`
		 , (case when isinactive = '1' then 'flase' 
				 when opportunityrestart = v_restart then 'true' 
				 else 'false' end) 
		   as isvisible 
		 , 'false'  as readonly
         , r.groupid
         , date_format(dategenerated, '%y-%m-%d %h:%i:%s.%f') as datecreated
         , responsesequence
		 , responselength
		 , isvalid
         , groupitemsrequired
		 , r.segment
		 , r.segmentid
		 , itembank.clientitemfile(v_clientname, _efk_itemkey) as itemfile
         , itembank.clientitemstimuluspath(v_clientname, s._efk_segment, _efk_itemkey) as stimulusfile
         , case when v_printtest = 1 or locate(concat('|', r.`format`, '|'), v_printtypes) > 0 or i.isprintable = 1
				then 1 
				else 0 
		   end as isprintable 
		 , (case isinactive when '1' then -1 else opportunityrestart end) as opportunityrestart 
	from testeeresponse r, testopportunitysegment s, itembank.tblsetofadminitems i 
	where r._fk_testopportunity = v_oppkey and s._fk_testopportunity = v_oppkey
		and r.segment = s.segmentposition and `page` = v_pagenumber
        and i._fk_adminsubject = s._efk_segment 
        and i._fk_item = r._efk_itemkey 
        and (v_groupid is null or r.groupid = v_groupid) 
        and (v_datecreated is null or dategenerated = v_datecreated) 
	order  by position; 
        
	call _logdblatency (v_procname, v_starttime, null, 1, null, v_oppkey, v_sessionid, v_clientname, null);

end $$
 
DELIMITER ;
