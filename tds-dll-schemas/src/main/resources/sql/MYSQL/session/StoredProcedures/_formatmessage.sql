DELIMITER $$

drop procedure if exists _formatmessage $$

create procedure _formatmessage (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/26/2015		Sai V. 			--
*/
	v_client varchar(100)
  , v_language varchar(50)
  , v_context varchar(200)
  , v_appkey varchar(255)
  , out v_msg text
  , v_argstring varchar(1000) -- = null
  , v_delim char -- = ','
  , v_subject varchar(100) -- = null
  , v_grade varchar(50) -- = null
)
sql security invoker
proc: begin

    declare v_msgkey varbinary(16);    
    declare v_msgid int;
    declare v_indx int;
	declare v_arg varchar(1000);

    if (v_argstring is not null) then
	begin
		drop temporary table if exists tmp_tblargs;
		create temporary table tmp_tblargs (
			indx int
		  , arg varchar(1000)
		);

		/* Call _buildtable stored procedure 
		-- To capture and use result set from _buildtable, a temporary table is created to store the resultset */			  
		call _buildtable(v_argstring, ',');

        insert into tmp_tblargs (indx, arg)
        select idx, record
        from tblout_buildtable;
	end;
    end if;

    set v_msgkey = configs.tds_getmessagekey (v_client, 'database', 'database', v_context, v_appkey, v_language, v_grade, v_subject);

    if (v_msgkey is null) then -- no official message
		set v_msg = concat(v_appkey, ' [-----]');
        
		insert into _missingmessages(application, contexttype, `context`, appkey, message) 
		select 'database', 'database', v_context, v_appkey, v_msg
		from dual
		where not exists (select * from _missingmessages 
						   where application = 'database' and `context` = v_context and contexttype = 'database' and appkey = v_appkey and message = v_msg);
        leave proc;
	end if;

    if (isnumeric(v_msgkey) = 1) then
        select message, messageid 
		into v_msg, v_msgid
		from configs.tds_coremessageobject 
		where _key = v_msgkey;
    else
        select t.message, messageid
		into v_msg, v_msgid
        from configs.tds_coremessageobject o, configs.client_messagetranslation t
        where t._key = v_msgkey and o._key = t._fk_coremessageobject;
	end if;

	if (v_argstring is not null) then
		while (exists (select * from tmp_tblargs)) do
		begin
			select indx, arg 
			into v_indx, v_arg
			from tmp_tblargs order by indx limit 1;

			set v_msg = replace(v_msg, concat('{', cast((v_indx - 1) as char(3)), '}'), v_arg);
			delete from tmp_tblargs where indx = v_indx;
		end;
		end while;
	end if;

	set v_msg = concat(v_msg, ' [', cast(v_msgid as char(10)), ']');

	-- clean-up
	drop temporary table if exists tmp_tblargs;
	drop temporary table if exists tblout_buildtable;

end $$

DELIMITER ;
