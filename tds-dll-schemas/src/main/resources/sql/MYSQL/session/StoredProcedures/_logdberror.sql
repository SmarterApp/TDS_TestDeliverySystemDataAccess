DELIMITER $$

drop procedure if exists _logdberror $$

create procedure _logdberror (
/*
Description: establish an audit trail of internal errors. 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/26/2015		Sai V. 			--
*/
	v_procname varchar(200)
  , v_msg text
  , v_testee bigint -- = null
  , v_test varchar(150) -- = null
  , v_opportunity int -- = null
  , v_testopp varbinary(16) -- = null
  , v_clientname varchar(100) -- = null
  , v_session varbinary(16) -- = null
)
begin

    if (v_clientname is null and v_testopp is not null) then
        select clientname into v_clientname 
		from testopportunity 
		where _key = v_testopp;
    elseif (v_clientname is null and v_session is not null) then
        select clientname into v_clientname 
		from `session` 
		where _key = v_session;
	end if;

	insert into archive.systemerrors (procname, errormessage, _efk_testee, _efk_testid, opportunity, application, _fk_testopportunity, _fk_session, clientname, daterecorded)
	values (v_procname, v_msg, v_testee, v_test, v_opportunity, 'database', v_testopp, v_session, v_clientname, now(3));


end $$

DELIMITER ;
