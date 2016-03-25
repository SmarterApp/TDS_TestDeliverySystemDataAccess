DELIMITER $$

drop procedure if exists `a_restoretestopportunity` $$

create procedure `a_restoretestopportunity`(
/*
Description: -- restore an opportunity from reset condition

VERSION 	DATE 			AUTHOR 			COMMENTS
001			04/01/2015		Sai V. 			Code ported from SQL Server to MySQL
*/
    v_oppkey 	varbinary(16)
  , v_requestor varchar(50)
  , v_reason 	nvarchar(255)
)
sql security invoker 
proc: begin

    declare v_errmsg text;
    declare v_testee bigint; 
	declare v_test 	 varchar(250); 
	declare v_opp 	 int; 
	declare v_clientname, v_status, v_poststatus, v_procname varchar(100);

	set v_procname = 'a_restoretestopportunity';

    select _efk_testee, _efk_testid, opportunity, clientname, `status`
	into v_testee, v_test, v_opp, v_clientname, v_status
    from testopportunity where _key = v_oppkey;

    if (exists (select * from testopportunity where _key = v_oppkey and datedeleted is null)) then
		call _returnerror(v_clientname, v_procname, 'the opportunity is not deleted', null, v_oppkey, null, 'failed');
        leave proc;
    end if;

    if (exists (select * from testopportunity where clientname = v_clientname and _efk_testee = v_testee and _efk_testid = v_test and opportunity = v_opp and datedeleted is null)) then
         call _returnerror(v_clientname, v_procname, 'another record exists in this slot. use _movetestopp to replace the existing opportunity', null, v_oppkey, null, 'failed');
        leave proc;
    end if;
    
    if (exists (select * from testopportunity where clientname = v_clientname and _efk_testee = v_testee and _efk_testid = v_test and opportunity > v_opp and datedeleted is null)) then
         call _returnerror(v_clientname, v_procname, 'a higher-numbered opportunity exists. use _movetestopp to replace the existing opportunity', null, v_oppkey, null, 'failed');
        leave proc;
    end if;
	-- check the failure conditions within this transaction, preventing it if someone else got there first
	if not exists (select * from testopportunity 
					where clientname = v_clientname and _efk_testee = v_testee and _efk_testid = v_test and opportunity >= v_opp and datedeleted is null) then
		update testopportunity 
		   set datedeleted = null
			 , daterestored = now(3)
		 where _key = v_oppkey;
	end if;


    if (not exists (select * from testopportunity where _key = v_oppkey and datedeleted is null)) then
         call _returnerror(v_clientname, v_procname, 'the same or higher-numbered opportunity exists. use _movetestopp to replace the existing opportunity.', null, v_oppkey, null, 'failed');
        leave proc;
    end if;

	if (isxmlon(v_oppkey) = 1 and v_status in ('completed', 'scored', 'invalidated', 'expired')) then
		-- check if there are any previous enteries in the QA Queue
		-- if yes, delete them because they no longer reflect the current state of the opportunity
		delete from qareportqueue 
		 where _fk_testopportunity = v_oppkey;

		set v_poststatus = null;
		set v_poststatus = (case when v_status in ('completed', 'scored') then 'submitted' else null end);

		insert into qareportqueue (_fk_testopportunity, changestatus, datentered, processstate) 
			 values (v_oppkey, v_poststatus, now(3), 'restore');
	end if;


    insert into archive.opportunityaudit (_fk_testopportunity, dateaccessed, _fk_session, accesstype, hostname, _fk_browser, actor, `comment`)
    select _key, now(3), _fk_session, 'restored', @@hostname, _fk_browser, v_requestor, concat('opportunity changed by admin proc: ', v_procname, '. Reason: ', v_reason)
    from testopportunity 
	where _key = v_oppkey;
        
    select 'success' as status, null as reason;

end $$

DELIMITER ;

