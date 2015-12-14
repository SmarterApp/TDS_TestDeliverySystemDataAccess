DELIMITER $$

drop function if exists `resumeitemposition` $$

create function `resumeitemposition`(
/*
VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/06/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_oppkey varbinary(16)
  , v_restart int
) returns int
sql security invoker
begin

	declare v_itemposition int;
    declare v_segment, v_override int;

    set v_segment = (select max(segment) from testeeresponse
					  where _fk_testopportunity = v_oppkey and coalesce(isinactive, 0) = 0 and (datesubmitted is not null or datelastvisited is not null));

    set v_override = (select min(segmentposition) from testopportunitysegment 
					   where _fk_testopportunity = v_oppkey and ispermeable = 1 and restorepermon is not null);

    if (v_segment is not null and v_override is not null and v_override < v_segment) then 
		set v_segment = v_override;
	end if;

    if (v_segment is not null) then  -- first try for unanswered items in the segment
        set v_itemposition = (select min(position) from testeeresponse 
							   where _fk_testopportunity = v_oppkey and segment = v_segment and dategenerated is not null and isvalid = 0 and opportunityrestart = v_restart);
	end if;

	-- next try for a segment that has all answered items but not exited
    if (v_itemposition is null and v_segment is not null) then
        set v_itemposition = (select max(position) from testeeresponse 
							   where _fk_testopportunity = v_oppkey and segment = v_segment and dategenerated is not null and opportunityrestart = v_restart);
	end if;

    if (v_itemposition is null) then
        set v_itemposition = (select min(position) from testeeresponse 
							   where _fk_testopportunity = v_oppkey and opportunityrestart = v_restart and isvalid = 0 and dategenerated is not null);
	end if;

    if (v_itemposition is null) then
        set v_itemposition = (select max(position) from testeeresponse 
							   where _fk_testopportunity = v_oppkey and opportunityrestart = v_restart and datesubmitted is not null);
	end if;

    if (v_itemposition is null) then
        set v_itemposition = (select count(*) + 1 from testeeresponse 
							   where _fk_testopportunity = v_oppkey and dategenerated is not null);
	end if;

    return v_itemposition;

end $$

DELIMITER ;
