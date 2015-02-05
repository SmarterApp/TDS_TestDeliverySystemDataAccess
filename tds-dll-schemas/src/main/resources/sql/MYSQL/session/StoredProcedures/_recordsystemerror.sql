DELIMITER $$

drop procedure if exists _recordsystemerror $$

create procedure _recordsystemerror (
/*
Description: establish an audit trail of internal errors. currently on setopportunitstatus makes use of this.

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/29/2015		Sai V. 			Converted code from T-SQL to MySQL
*/
	v_proc varchar(50)
  , v_msg text
  , v_testee bigint -- = null
  , v_test varchar(150) -- = null
  , v_opportunity int -- = null
  , v_application varchar(150) -- = null
  , v_clientip varchar(50) -- = null
  , v_applicationcontextid varbinary(16) -- = null
  , v_stacktrace text -- = null
  , v_testoppkey varbinary(16) -- = null
  , v_clientname varchar(100) -- = null
)
proc: begin

--     if (v_application is null) then 
-- 		set v_application = app_name();
-- 	end if;
    
    if (v_clientname is null and v_testoppkey is not null) then
        set v_clientname = (select clientname from testopportunity where _key = v_testoppkey);
	end if;

	insert into archive.systemerrors (procname, errormessage, _efk_testee, _efk_testid, opportunity, application, ipaddress, applicationcontextid, stacktrace, _fk_testopportunity, clientname, daterecorded)
		 values (v_proc, v_msg, v_testee, v_test, v_opportunity, v_application, v_clientip, v_applicationcontextid, v_stacktrace, v_testoppkey, v_clientname, now(3));

end $$

DELIMITER ;