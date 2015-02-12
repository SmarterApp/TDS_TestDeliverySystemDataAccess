DELIMITER $$

drop procedure if exists `_onstatus_completed` $$

create procedure `_onstatus_completed`(
/*
Description: perform whatever actions are required when a test opportunity status changes to 'completed'

VERSION 	DATE 			AUTHOR 			COMMENTS
001			01/10/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_oppkey varbinary(16)
)
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
	set itemgroupstring = makeitemgroupstring(v_oppkey) where _key = v_oppkey;

	-- restore segment permeabilities, even though the test is over (in case it reopens?)
    update testopportunitysegment 
	set ispermeable = -1
    where _fk_testopportunity = v_oppkey;

    exec _settesteeattributes v_clientname, v_oppkey, v_testee, 'final';
    exec _recordbpsatisfaction v_oppkey;

    if (dbo.isxmlon(v_oppkey) = 1 and dbo.canscoreopportunity(v_oppkey) = 'complete: do not score')
        exec submitqareport v_oppkey, 'submitted', v_v_procid;

-- note: _efk_adminsubject is the primary key for table ft_fieldtest, hence the direct comparison between (ft_itemgroup.)_fk_fieldtest and _efk_adminsubject from testopportunity
    if (exists (select * from ft_opportunityitem where _fk_testopportunity = v_oppkey)) begin   
        declare v_groups table (gid varchar(50), bid varchar(20), seg int, pos int);
        insert into v_groups (gid, bid, seg, pos)
        select r.groupid, i.blockid, r.segment, min(r.position)
        from testeeresponse r, ft_opportunityitem i
        where r._fk_testopportunity = v_oppkey and i._fk_testopportunity = v_oppkey 
            and r.segment = i.segment and r.groupid = i.groupid and r.isfieldtest = 1
        group by r.segment, r.groupid, i.blockid;

-- set the true position each item group was administered in
        update ft_opportunityitem set positionadministered = pos, dateadministered=getdate()
        from v_groups 
        where _fk_testopportunity = v_oppkey and segment = seg and groupid = gid;

        

    end

    exec _logdblatency v_v_procid, v_starttime, v_testee, 1, v_testoppkey = v_oppkey;

end $$

DELIMITER ;