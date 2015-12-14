DELIMITER $$

drop procedure if exists `a_invalidatetestopportunity` $$

create procedure `a_invalidatetestopportunity`(
/*
Description: updated for opportunity key internally

VERSION 	DATE 			AUTHOR 			COMMENTS
001			04/01/2015		Sai V. 			Code ported from SQL Server to MySQL
*/
    v_oppkey 		 varbinary(16)
  , v_requestor 	 varchar(100)
  , v_reason 		 nvarchar(255)
)
sql security invoker 
proc: begin

    declare v_oppkey1 	 varbinary(16);
    declare v_clientname varchar(100);
	declare v_procname	 varchar(100);	
    declare v_err 		 text;

	set v_procname = 'a_invalidatetestopportunity';
    set v_oppkey1 = (select _key from testopportunity where _key = v_oppkey and datedeleted is null);
        
    if (v_oppkey1 is null) then
		call _returnerror(v_clientname, v_procname, 'no test opportunity matches the opportunity key input value', null, null, null, 'failed');
        leave proc;
    end if;

    call setopportunitystatus(v_oppkey, 'invalidated', 1, v_requestor, null);

	if (isxmlon(v_oppkey) = 1) then
		-- check if there are any previous enteries in the QA Queue
		-- if yes, delete them because they no longer reflect the current state of the opportunity
		delete from qareportqueue 
		 where _fk_testopportunity = v_oppkey;

		insert into qareportqueue (_fk_testopportunity, changestatus, dateentered, processstate) 
			 values (v_oppkey, null, now(3), 'invalidated');
	end if;

    insert into archive.opportunityaudit (_fk_testopportunity, dateaccessed, _fk_session, accesstype, hostname, _fk_browser, actor, `comment`)
    select _key, now(3), _fk_session, 'invalidated', @@hostname, _fk_browser, v_requestor, concat('opportunity changed by admin proc: ', v_procname, '. Reason: ', v_reason)
    from testopportunity 
	where _key = v_oppkey;
    
end $$

DELIMITER ;
