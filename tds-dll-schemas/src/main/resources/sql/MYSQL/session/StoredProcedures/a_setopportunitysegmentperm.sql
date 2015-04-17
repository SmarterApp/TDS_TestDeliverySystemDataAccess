DELIMITER $$

drop procedure if exists `a_setopportunitysegmentperm` $$

create procedure `a_setopportunitysegmentperm`(
/*
Description: -- alter the default behavior of the test segment by setting its permeability flag
			-- restoreon is one of 
			-- 'segment' : restore the default when the testee exits the segment (so he/she is allowed one visit into the segment. behaves most like the natural manner)
			--      the default will be restored on pause whether or not the testee entered it.
			-- 'paused' : restore the default when the testee pauses the test. if testee does not visit the segment, it doesn't matter.
			-- 'completed' : the default is never restored

VERSION 	DATE 			AUTHOR 			COMMENTS
001			04/01/2015		Sai V. 			Code ported from SQL Server to MySQL
*/
    v_oppkey 	varbinary(16)
  , v_requestor varchar(50)
  , v_segmentid varchar(200)
  , v_segmentposition int
  , v_restoreon varchar(50) -- = 'segment'
  , v_ispermeable int -- = 1
  , v_reason nvarchar(255)
)
sql security invoker 
proc: begin

    declare v_restart int;
    declare v_status varchar(50);
    declare v_testid varchar(200);
    declare v_clientname varchar(100);
	declare v_procname varchar(100);

	set v_procname = 'a_setopportunitysegmentperm';

    select clientname, _efk_testid, `status`, restart
	into v_clientname, v_testid, v_status, v_restart
    from testopportunity where _key = v_oppkey;
    
    if (not exists (select * from testopportunitysegment where _fk_testopportunity = v_oppkey and segmentid = v_segmentid and segmentposition = v_segmentposition)) then
        call _returnerror(v_clientname, v_procname, 'no such test segment', null, v_oppkey, null, 'failed');
        leave proc;
    end if;

    if (v_restoreon not in ('segment', 'paused', 'completed')) then
        call _returnerror(v_clientname, v_procname, 'unknown restore parameter. valid values are "segment", "paused", "completed"', null, v_oppkey, null, 'failed');
        leave proc;
    end if;

	start transaction;
        -- check for the segment being impermeable. if not, then changing the local permeability is incorrect?
        if (exists (select * from configs.client_segmentproperties where clientname = v_clientname and parenttest = v_testid and segmentid = v_segmentid and ispermeable = 0)) then
            update testopportunitysegment 
			set ispermeable = v_ispermeable, restorepermon = v_restoreon
            where _fk_testopportunity = v_oppkey and segmentposition = v_segmentposition;
        end if;
        
        -- need to set visibility of all items in the segment for next resume
        update testeeresponse 
		set opportunityrestart = v_restart + 1 
        where _fk_testopportunity = v_oppkey and segment = v_segmentposition;

        -- record opp audit ...
        insert into archive.opportunityaudit (_fk_testopportunity, dateaccessed, accesstype, actor, comment)
        select v_oppkey, now(3), 'reopen segment', v_requestor, concat('altering behaviour of test segment by admin proc: ', v_procname, '. segment id:', v_segmentid, '. Reason: ', v_reason);
	commit;

    select 'success' as status, null as reason;

end $$

DELIMITER ;
