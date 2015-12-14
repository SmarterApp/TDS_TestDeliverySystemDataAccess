DELIMITER $$

drop function if exists `issessionopen` $$

create function `issessionopen`(
/*
DESCRIPTION: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/10/2015		Sai V. 			
*/
	v_sessionkey varbinary(16))
returns int
sql security invoker
begin

	-- declare the return variable here
	declare v_sessionid varchar(128);
	declare v_proctorkey bigint;
    declare v_clientname varchar(100);
	
	select clientname, _efk_proctor, sessionid 
	into v_clientname, v_proctorkey, v_sessionid
    from `session`
	where _key = v_sessionkey 
		and `status` in (select `status` from statuscodes where `usage` = 'session' and stage = 'open')
		and date_sub(datebegin, interval 10 minute) <= now(3) and dateend >= now(3);

	if (v_sessionid is null) then return 0; end if;
	if (v_proctorkey is null and _allowproctorlesssessions(v_clientname) = 0) then return 0; end if;

	return 1;

end $$

DELIMITER ;