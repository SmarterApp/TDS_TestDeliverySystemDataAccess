DELIMITER $$

drop procedure if exists `_setgraceperiods` $$

create procedure `_setgraceperiods`(
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/06/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_oppkey varbinary(16)
  , v_newrestart int
  , v_debug bit -- = 0
)
sql security invoker
proc: begin

    declare v_sessiontype, v_delay int;
	declare v_testid, v_clientname varchar(100);
    declare v_fromtime, v_now datetime(3);

	declare v_procname varchar(100);
	declare v_starttime datetime(3);
	set v_starttime = now(3);
	set v_procname = '_setgraceperiods';

    select sessiontype, o._efk_testid, o.clientname
	into v_sessiontype, v_testid, v_clientname
    from testopportunity o, `session` s
    where o._key = v_oppkey and s._key = o._fk_session; 

	-- data entry applicate (sessiontype = 1) has all items visible
    if (v_sessiontype = 1) then
        update testeeresponse 
		set opportunityrestart = v_newrestart
        where _fk_testopportunity = v_oppkey;

        leave proc;
    end if;

    drop temporary table if exists tmp_tblsegments; 
	create temporary table tmp_tblsegments(
		segpos 	 int
	  , fromtime datetime(3)
	  , delay 	 int
	) engine = memory;

    set v_now = now(3);

    set v_delay = (select opprestart from timelimits
					where _efk_testid = v_testid and clientname = v_clientname);

	if (v_delay is null) then 
		set v_delay = (select opprestart from timelimits  
						where _efk_testid is null and clientname = v_clientname);
	end if;

	if (v_delay is null) then set v_delay = 1; end if;

    insert into tmp_tblsegments(segpos, delay, fromtime)
    select p.segmentposition, graceperiodminutes, _lastsegmentactivity(v_oppkey, p.segmentposition)
    from configs.client_segmentproperties p, testopportunitysegment s
    where _fk_testopportunity = v_oppkey and s.segmentid = p.segmentid 
		and p.clientname = v_clientname and graceperiodminutes is not null;

    -- use the test-level grace period for any segment that does not have its own defined
    set v_fromtime = _testopplastactivity(v_oppkey);

    if (v_debug = 0) then 
	begin
        -- first try to update restart for segment-specific items
        update testeeresponse, tmp_tblsegments
		set opportunityrestart = v_newrestart
        where _fk_testopportunity = v_oppkey 
			and opportunityrestart = v_newrestart - 1 
            and dategenerated is not null
            and segment = segpos and timestampdiff(minute, fromtime, v_now) < delay;

        -- now get all those that don't have specific value for segments
        update testeeresponse 
		set opportunityrestart = v_newrestart
        where _fk_testopportunity = v_oppkey and opportunityrestart = v_newrestart - 1
            and timestampdiff(minute, v_fromtime, v_now) < v_delay and dategenerated is not null
            and segment not in (select segpos from tmp_tblsegments);
    end;
    else 
	begin      -- don't do update in debug mode. just return all affected items
        select * from tmp_tblsegments;

        select segment, position, dategenerated, datesubmitted
        from testeeresponse, tmp_tblsegments
        where _fk_testopportunity = v_oppkey and opportunityrestart = v_newrestart - 1 
            and dategenerated is not null
            and segment = segpos and timestampdiff(minute, fromtime, v_now) < delay;
    end;
	end if;

	-- clean-up
	drop temporary table tmp_tblsegments;

	call _logdblatency(v_procname, v_starttime, null, null, null, v_oppkey, null, null, null);

end $$

DELIMITER ;
