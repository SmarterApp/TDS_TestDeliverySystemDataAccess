DELIMITER $$

drop procedure if exists `_onstatus_completed` $$

create procedure `_onstatus_completed`(
/*
Description: perform whatever actions are required when a test opportunity status changes to 'completed'

VERSION 	DATE 			AUTHOR 			COMMENTS
001			01/10/2014		Sai V. 			Code Migration
*/
	v_oppkey varbinary(16)
)
sql security invoker
proc: begin

	declare v_starttime datetime(3);
	declare v_testee bigint;
    declare v_testkey varchar(200);
	declare v_testid varchar(150);
    declare v_clientname varchar(100);
	declare v_audit bit;

	set v_starttime = now(3);
    set v_audit = auditproc('_onstatus_completed');

	-- add the following info to test opportunity.
    select _efk_testee, _efk_adminsubject, clientname, _efk_testid
	into v_testee, v_testkey, v_clientname, v_testid
    from testopportunity  
	where _key = v_oppkey;
	
    update testopportunity 
	set itemgroupstring = makeitemgroupstring(v_oppkey) 
	where _key = v_oppkey;

	-- restore segment permeabilities, even though the test is over (in case it reopens?)
    update testopportunitysegment 
	set ispermeable = -1
    where _fk_testopportunity = v_oppkey;

    call _settesteeattributes(v_clientname, v_oppkey, v_testee, 'final');
    call _recordbpsatisfaction(v_oppkey);

	if (v_testee > 0) then 
		if (isxmlon(v_oppkey) = 1 and canscoreopportunity(v_oppkey) = 'complete: do not score') then
			call submitqareport(v_oppkey, 'submitted', '_onstatus_completed');
		end if;
	end if;

    if (exists (select 1 from ft_opportunityitem where _fk_testopportunity = v_oppkey limit 1)) then  
	begin
        drop temporary table if exists tmp_tblgroups;
		create temporary table tmp_tblgroups(gid varchar(50), bid varchar(20), seg int, pos int) engine = memory;

        insert into tmp_tblgroups (gid, bid, seg, pos)
        select r.groupid, i.blockid, r.segment, min(r.position)
        from testeeresponse r, ft_opportunityitem i
        where r._fk_testopportunity = v_oppkey and i._fk_testopportunity = v_oppkey 
            and r.segment = i.segment and r.groupid = i.groupid and r.isfieldtest = 1
        group by r.segment, r.groupid, i.blockid;

		-- set the true position each item group was administered in
        update ft_opportunityitem, tmp_tblgroups
		set positionadministered = pos
		  , dateadministered = now(3)
        where _fk_testopportunity = v_oppkey 
			and segment = seg and groupid = gid;
	end;
    end if;

	-- clean-up
	drop temporary table if exists tmp_tblgroups;

    call _logdblatency('_onstatus_completed', v_starttime, v_testee, 1, 1, v_oppkey, null, null, null);

end $$

DELIMITER ;