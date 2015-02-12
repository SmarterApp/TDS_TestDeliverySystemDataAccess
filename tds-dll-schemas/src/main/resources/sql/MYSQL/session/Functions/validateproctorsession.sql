DELIMITER $$

drop function if exists `validateproctorsession` $$

create function `validateproctorsession`(
/*
DESCRIPTION: New function to validate proctor and browser rights to the testing session

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/02/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_proctorkey bigint
  , v_sessionkey varbinary(16)
  , v_browserkey varbinary(16)
)
returns varchar(200)
begin

    if (not exists (select * from `session` where _key = v_sessionkey and `status` = 'open' and now(3) between datebegin and dateend )) then
        return 'the session is closed.';
	end if;

    if (not exists (select * from `session` where _key = v_sessionkey and _efk_proctor = v_proctorkey)) then
        return 'the session is not owned by this proctor';
	end if;

    if (not exists (select * from `session` where _key = v_sessionkey and _fk_browser = v_browserkey)) then
       return 'unauthorized session access';
	end if;

    return null;
    
end $$

DELIMITER ;