DELIMITER $$

drop procedure if exists `a_resettestopportunity` $$

create procedure `a_resettestopportunity`(
/*
Description: 'delete' the opportunity by setting the datedeleted timestamp

VERSION 	DATE 			AUTHOR 			COMMENTS
001			04/01/2015		Sai V. 			Code ported from SQL Server to MySQL
*/
    v_oppkey 		 varbinary(16)
  , v_requestor 	 varchar(100)
-- , v_allowcompleted bit -- = 1
  , v_reason 		 nvarchar(255)
)
sql security invoker 
proc: begin

    declare v_testee bigint;
    declare v_test varchar(150);
    declare v_opportunity int;
    declare v_clientname varchar(100);
    declare v_completed datetime(3);
    declare v_reported datetime(3);
	declare v_dateexpiredreported datetime(3);
	declare v_dateinvalidated datetime(3);
    declare v_errmsg varchar(200) ;
    declare v_audit int;
    declare v_oldstatus varchar(50);	
	declare v_maxopp int;
	declare v_arg varchar(4);    
	declare v_statuschange varchar(50);

	declare v_procname varchar(100);

	set v_procname = 'a_resettestopportunity';
	-- if v_allowcompleted is null then set v_allowcompleted = 1; end if;
    
    select _efk_testee, _efk_testid, opportunity, `status`, datecompleted, datereported, dateexpiredreported, dateinvalidated, clientname
	  into v_testee, v_test, v_opportunity, v_oldstatus, v_completed, v_reported, v_dateexpiredreported, v_dateinvalidated, v_clientname
      from testopportunity 
	 where _key = v_oppkey 
	   and datedeleted is null;

    if (v_testee is null) then
		call _returnerror(v_clientname, v_procname, 'no test opportunity matches the opportunity key', null, v_oppkey, null, 'failed');         
		leave proc;
    end if;
    
    select max(opportunity) into v_maxopp
	  from testopportunity
     where _efk_testee = v_testee and _efk_testid = v_test and datedeleted is null and clientname = v_clientname;

    -- perform validation checks
    if (v_maxopp <> v_opportunity) then        
        set v_arg = cast(v_opportunity as char(4)); 
        call _returnerror(v_clientname, v_procname, 'opportunities can only be deleted sequentially in descending order. there is a higher numbered opportunity than {0}', v_arg, v_oppkey, null, 'failed'); 
		leave proc;
    end if;

    if (/*v_allowcompleted = 0 and*/ v_completed is not null) then
        call _returnerror(v_clientname, v_procname, 'only incomplete test opportunity reset is supported at this time', null, v_oppkey, null, 'failed');
		leave proc;
    end if;


	start transaction;     
		if (v_reported is not null or v_dateexpiredreported is not null or v_dateinvalidated is not null) then    -- only if the test was reported  
            update testopportunity  
               set `status` = 'reset'
                 , datechanged = now(3)
             where _key = v_oppkey;			

			-- change the status to the last valid 'terminal' status (e.g. paused, completed, scored, reported, etc)
			set v_statuschange = lastterminalstatus(v_oppkey);
	
			if (isxmlon(v_oppkey) = 1) then
				-- check if there are any previous enteries in the QA Queue
				-- if yes, delete them because they no longer reflect the current state of the opportunity
				delete from qareportqueue 
				 where _fk_testopportunity = v_oppkey;

				insert into qareportqueue (_fk_testopportunity, changestatus, dateentered, processstate) 
					 values (v_oppkey, v_statuschange, now(3), 'reset');

            -- the status of the opportunity will be changed by the QA system.
			/*
				update testopportunity 
				set `status` = v_statuschange 
				where _key = v_oppkey;
			*/
			end if;
			select 'success' as `status`, null as reason;
		end if;
   
		update testopportunity set datedeleted = now(3) where _key = v_oppkey;

	commit;

    insert into archive.opportunityaudit (_fk_testopportunity, dateaccessed, _fk_session, accesstype, hostname, _fk_browser, actor, `comment`)
    select _key, now(3), _fk_session, v_statuschange, @@hostname, _fk_browser, v_requestor, concat('opportunity changed by admin proc: ', v_procname, '. Reason: ', v_reason)
    from testopportunity 
	where _key = v_oppkey;

end $$

DELIMITER ;