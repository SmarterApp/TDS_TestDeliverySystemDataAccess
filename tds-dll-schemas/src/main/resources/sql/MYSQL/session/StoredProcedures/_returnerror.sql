DELIMITER $$

drop procedure if exists _returnerror $$

create procedure _returnerror (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/26/2015		Sai V. 			--
*/
	v_client varchar(100)
  , v_procname varchar(200)
  , v_appkey varchar(200)
  , v_argstring varchar(1000) -- = null
  , v_oppkey varbinary(16) -- = null
  , v_context varchar(200) -- = null
  , v_status varchar(50) -- = 'failed'
)
begin

    declare v_procname varchar(200);
    declare v_language varchar(50);
	declare v_subject varchar(100);
	declare v_grade varchar(50);
	declare v_testee bigint;
    declare v_errmsg text;


	-- initialize default values
	if v_status is null then set v_status = 'failed'; end if; 
    if (v_context is null) then set v_context = v_procname; end if;  -- using procedure name is the context, unless otherwise the context is explicitly provided

    if (v_oppkey is not null) then 
	begin
        select acccode, `subject`, _efk_testee, clientname
		into v_language, v_subject, v_testee, v_client
        from testopportunity o, testeeaccommodations a
        where o._key = v_oppkey 
			and a._fk_testopportunity = v_oppkey 
			and a.acctype = 'language';
        
		-- if (v_testee > 0) then
        --    call _getrtsattribute v_client, v_testee, 'enrlgrdcode', v_grade output;
    end;
	end if;

    if (v_language is null) then set v_language = 'enu'; end if;

    call _formatmessage (v_client, v_language, v_context, v_appkey, v_errmsg /*output*/, v_argstring, ',', v_subject, v_grade);

    select v_status as status, v_errmsg as reason, v_context as context, v_appkey as appkey;

end $$

DELIMITER ;