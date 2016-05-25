DELIMITER $$

drop procedure if exists `a_extendingoppgraceperiod` $$

create procedure `a_extendingoppgraceperiod`(
/*
Description: -- used to adminstratively extend grace period
			-- partially finished but a victim of the Pause Rule.   overrides the time before pause     testee response:opportunityrestart    

VERSION 	DATE 			AUTHOR 			COMMENTS
001			04/01/2015		Sai V. 			Code ported from SQL Server to MySQL
*/
    v_oppkey 	varbinary(16)
  , v_requestor varchar(50)
  , v_selectedsitting int -- = 0
  , v_doupdate bit	-- =0
  , v_reason nvarchar(255)
)
sql security invoker 
proc: begin

    declare v_testee bigint;
    declare v_test, v_clientname varchar(150);
    declare v_testkey varchar(250);
    declare v_opp int;
    declare v_maxopp int;
	declare v_archived datetime(3);   
	declare v_restart int; 
    declare v_deleted datetime(3);
    declare v_msg, v_procname varchar(200);

	set v_procname = 'a_extendingoppgraceperiod';
    
    select _efk_adminsubject, _efk_testee, _efk_testid, opportunity, items_archived, restart, datedeleted, clientname
	into v_testkey, v_testee, v_test, v_opp, v_archived, v_restart, v_deleted, v_clientname
    from testopportunity 
	where _key = v_oppkey;

    set v_maxopp = (select max(opportunity) from testopportunity
					 where _efk_testee = v_testee and _efk_adminsubject = v_testkey and datedeleted is null and clientname = v_clientname);

    set v_msg = case when v_maxopp > v_opp
					 then 'this is not the latest test opportunity for this testee/test'
					 when v_testee is null
					 then 'no such test opportunity'
					 when v_archived is not null
					 then 'this action can not be performed because the test already completed.'
					 when v_deleted is not null
					 then 'this action can not be performed because the test was deleted'
					 when v_selectedsitting < 0 or v_selectedsitting > v_restart
					 then 'invalid sitting selection'
				end;

    if (v_msg is not null) then
        call _returnerror(v_clientname, v_procname, v_msg, null, v_oppkey, null, 'failed');    
        leave proc;
    end if;

	-- begin update
	if(v_doupdate = 1) then 		
		start transaction;
			update testeeresponse 
			set opportunityrestart = v_restart + 1 
			where _fk_testopportunity = v_oppkey and opportunityrestart >= v_selectedsitting;
		
			-- record opp audit ...
			insert into archive.opportunityaudit (_fk_testopportunity, dateaccessed, _fk_session, accesstype, hostname, _fk_browser, actor, `comment`)
			select _key, now(3), _fk_session, 'extending grace period', @@hostname, _fk_browser, v_requestor, concat('opportunity changed by admin proc: ', v_procname, '. ', 'extending items grace period from any sittings >= ', cast(v_selectedsitting as char(25)), ' to ', cast(v_restart + 1 as char(25)), '. Reason: ', v_reason)
			from testopportunity 
			where _key = v_oppkey;

		commit;
	end if;
	
/*	select testeeid, testeename, _efk_testee as testeekey, _efk_testid as testid, opportunity, restart, reportingid 
	from testopportunity
	where _efk_testee = v_testee and _efk_adminsubject = v_testkey and datedeleted is null
	order by opportunity;

	select  _efk_itsitem, _efk_itsbank, opportunityrestart, `page`, position, `format`, datesubmitted, datefirstresponse 
	from testeeresponse
	where _fk_testopportunity = v_oppkey; */


	select 'success' as status, cast(null as char) as reason;
		
end $$

DELIMITER ;
