DELIMITER $$

drop procedure if exists t_getopportunityitems $$

create procedure t_getopportunityitems (
/*
Description: this returns all items that have been administered, whether responded to or not, scored or not.

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/29/2015		Sai V. 			Converted code from T-SQL to MySQL
*/
	v_oppkey varbinary(16)
)
proc: begin
	
	declare v_starttime datetime(3);
    declare v_archived datetime(3);
	declare v_maxitems, v_restart int;

	set v_starttime = now(3);
        
    select items_archived, maxitems, restart
	into v_archived, v_maxitems, v_restart
    from testopportunity 
	where _key = v_oppkey;

    if (v_archived is not null) then
        select _efk_itsbank as itembank
			 , _efk_itsitem as item
			 , position
			 , page
			 , segment
			 , segmentid
			 , groupid
			 , responsesequence
			 , isrequired
			 , date_format(dateGenerated, '%Y-%m-%d %H:%i:%S.%f') as datecreated
			 , groupitemsrequired
			 , score
			 , mark			   
			 , (case isinactive when 1 then -1 else opportunityrestart end) as opportunityrestart -- this value is needed to prevent from administering an inactivated item that is still (or again) in the item pool
             , isfieldtest
			 , isselected
			 , isvalid
			 , `format` 
			 , (case when isinactive = 1 then 'false' 
					 when opportunityrestart = v_restart then 'true'
					 else 'false' end) 
			   as isvisible
			 , 'false' as readonly
        from testeeresponsearchive 
        where _fk_testopportunity = v_oppkey
			and _efk_itsitem is not null 
        order by position;
    else 
        select _efk_itsbank as itembank
			 , _efk_itsitem as item
			 , position
			 , page
			 , segment
			 , segmentid
			 , groupid
			 , responsesequence
			 , isrequired
			 , date_format(dateGenerated, '%Y-%m-%d %H:%i:%S.%f') as datecreated
			 , groupitemsrequired
			 , score
			 , mark 
			 , (case isinactive when 1 then -1 else opportunityrestart end) as opportunityrestart -- this value is needed to prevent from administering an inactivated item that is still (or again) in the item pool
			 , isfieldtest
			 , isselected
			 , isvalid
			 , `format` 
			 , (case when isinactive = 1 then 'false' 
					 when opportunityrestart = v_restart then 'true'
					 else 'false' end) 
			   as isvisible
			 , 'false' as readonly
			 , visitcount
			 , datelastvisited  
        from testeeresponse 
        where _fk_testopportunity = v_oppkey
			and _efk_itsitem is not null 
        order by position;
	end if;
	
	call _logdblatency('t_getopportunityitems', v_starttime, null, 1, null, v_oppkey, null, null, null);

end $$

DELIMITER ;
