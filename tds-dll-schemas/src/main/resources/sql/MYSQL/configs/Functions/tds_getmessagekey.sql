DELIMITER $$

drop function if exists `tds_getmessagekey` $$

create function `tds_getmessagekey`(
/*
DESCRIPTION: -- Returns TDS_CoreMessageObject._Key as bigint if there is no translation
			-- Returns Client_MessageTranslation._Key as uniqueidentifier if there IS a translation
			-- Returns null if neither is found

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/26/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_client varchar(100)
  , v_application varchar(100)
  , v_contexttype varchar(50)
  , v_context varchar(500)
  , v_appkey varchar(255)
  , v_language varchar(50)
  , v_grade varchar(50)
  , v_subject varchar(50)
)
returns varchar(64)
begin

    declare v_altmsg varchar(64);
    declare v_msgkey bigint;
    declare v_default varchar(50);
	declare v_inter bit;

    select _fk_coremessageobject
	into v_msgkey
    from tds_coremessageuser, tds_coremessageobject
    where systemid = v_application 
		and `context` = v_context 
		and _fk_coremessageobject = _key 
        and contexttype = v_contexttype 
		and appkey = v_appkey;

    if (v_msgkey is null) then return null; end if;

    if (v_client is null) then set v_client = 'air'; end if;
    if (v_language is null) then set v_language = 'enu'; end if;
    if (v_grade is null) then set v_grade = '--any--'; end if;
    if (v_subject is null) then set v_subject = '--any--'; end if;

	-- some clients don't want internationalized messages. this is an all-or-nothing configuration
    select defaultlanguage, internationalize
	into v_default, v_inter
    from `client`
	where `name` = v_client ;

    if (v_inter = 0) then set v_language = v_default; end if;

    select _key
	into v_altmsg
    from client_messagetranslation 
    where _fk_coremessageobject = v_msgkey 
		and (`language` = v_language or `language` = v_default)
        and (`client` = v_client or `client` = 'air')
		and (grade = v_grade or grade = '--any--') and (`subject` = v_subject or `subject` = '--any--');

    if (v_altmsg is not null) then
		return v_altmsg;
	else
		return v_msgkey;
	end if;

end $$

DELIMITER ;