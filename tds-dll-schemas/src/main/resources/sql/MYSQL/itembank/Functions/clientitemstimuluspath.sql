DELIMITER $$

drop function if exists `clientitemstimuluspath` $$

create function `clientitemstimuluspath`(
/*
DESCRIPTION: Get the absolute filepath to the specific stimulus

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/10/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_clientname varchar(100)
  , v_testkey varchar(250)
  , v_itemkey varchar(50)
) returns varchar(150)
sql security invoker
begin

	-- declare the return variable here
	declare v_path varchar(150);

	select concat(c.homepath, b.homepath, b.stimulipath, s.filepath, s.filename)
	into v_path
	from tblitembank b, tblclient c, tblstimulus s, tblsetofitemstimuli i
	where i._fk_adminsubject = v_testkey 
		and i._fk_item = v_itemkey 
        and s._key = _fk_stimulus 
		and c.`name` = v_clientname
        and b._efk_itembank = s._efk_itembank 
		and b._fk_client = c._key;
        
	return v_path;
	
end$$

DELIMITER ;