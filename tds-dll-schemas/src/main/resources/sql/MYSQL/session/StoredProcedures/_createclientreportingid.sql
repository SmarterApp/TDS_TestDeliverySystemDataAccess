DELIMITER $$

drop procedure if exists `_createclientreportingid` $$

create procedure `_createclientreportingid`(
/*
Description: create unique reporting id for test opportunity 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/10/2015		Sai V. 			--
*/
	v_clientname varchar(100)
  , v_oppkey varbinary(16)
  , out v_newid bigint
)
sql security invoker
proc: begin

	declare v_applock int;
    declare v_resourcename varchar(100);
	declare v_consume int;

	declare exit handler for sqlexception
	begin
		set v_newid = null;

		if (v_applock >= 0) then 
			set v_consume = (select release_lock(v_resourcename));
		end if;

		rollback;
		call _logdberror('_createclientreportingid', 'mysql exit handler', null, null, null, v_oppkey, v_clientname, null);
	end;

    set v_resourcename = concat('createtestid', v_clientname);   -- this makes lock specific to a client
    set v_applock = -1;      -- indicates no applock obtained

	set v_newid = null;

	-- ensure that the name is truly unique by making its generation and insertion an atomic action
	start transaction;
		set v_applock = get_lock(v_resourcename, 0);
		if (v_applock < 0) then
			call _logdberror('_createclientreportingid', 'failed to get applock', null, null, null, v_oppkey, v_clientname, null);
			rollback;
			leave proc;
		end if;

		set v_newid = (select max(reportingid) + 1 
						from client_reportingid where clientname = v_clientname);

        if (v_newid is null) then     -- then get the starting value from externs
            set v_newid = (select initialreportingid from externs where clientname = v_clientname);
		end if;

        insert into client_reportingid(clientname, reportingid, _fk_testopportunity)
			 values (v_clientname, v_newid, v_oppkey);

		set v_consume = (select release_lock(v_resourcename));   -- note: commit/rollback will implicitly release the lock
        set v_applock = -1;
	commit;

end $$

DELIMITER ;
