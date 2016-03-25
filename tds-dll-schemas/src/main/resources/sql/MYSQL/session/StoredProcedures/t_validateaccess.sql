DELIMITER $$

drop procedure if exists t_validateaccess $$

create procedure t_validateaccess (
/*
Description: establish an audit trail of internal errors. currently on setopportunitstatus makes use of this.

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/29/2015		Sai V. 			Converted code from T-SQL to MySQL
*/
	v_oppkey varbinary(16)
  ,	v_session varbinary(16)
  , v_browserid varbinary(16)
)								
begin

	declare v_error 	varchar(64);
	declare v_starttime datetime(3);

	set v_starttime = now(3);

	call _validatetesteeaccessproc(v_oppkey, v_session, v_browserid, 1, v_error);

	if (v_error is null) then
		select 'success' as `status`, cast(null as char) as reason, status as oppstatus, `comment`
        from testopportunity 
		where _key = v_oppkey;
	else 
		call _returnerror(null, 't_validateaccess', v_error, null, v_oppkey, '_validatetesteeaccess', null);
	end if;

	call _logdblatency('t_validateaccess', v_starttime, null, 1, null, v_oppkey, v_session, null, null);

end $$

DELIMITER ;