DELIMITER $$

drop procedure if exists t_getopportunityaccommodations $$

create procedure t_getopportunityaccommodations (
/*
Description:

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/29/2015		Sai V. 			Converted code from T-SQL to MySQL
*/
	v_oppkey  varbinary(16)
  ,	v_session varbinary(16)
  , v_browser varbinary(16)
)
proc: begin

	declare v_errmsg 	varchar(100);
	declare v_starttime datetime(3);

	set v_starttime = now(3);

	-- it is best to call with non-null session and browser to ensure that the student will receive the correct accommodations
    if (v_session is not null and v_browser is not null) then
	begin        
        call _validatetesteeaccessproc(v_oppkey, v_session, v_browser, 1, v_errmsg /*output*/);
        if (v_errmsg is not null) then
            -- select 'failed' as [status], v_errmsg as reason, '_validateitemsaccess' as [context], null as [argstring], null as [delimiter];
            call _returnerror (null, 't_getopportunityaccommodations',v_errmsg, null, v_oppkey, '_validateitemsaccess', 'failed');  

            leave proc;
        end if;
    end;
	end if;
    
    select a.segment, a.acctype, a.acccode, a.isapproved, recordusage
    from testeeaccommodations a
    where a._fk_testopportunity = v_oppkey ;
 
	call _logdblatency('t_getopportunityaccommodations', v_starttime, null, 1, null, v_oppkey, v_session, null, null);
   
end $$

DELIMITER ;