DELIMITER $$

drop function if exists `clientitemfile` $$

create function `clientitemfile`(
/*
DESCRIPTION: Get the absolute filepath to the items

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/10/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_clientname varchar(100)
  , v_itemkey varchar(50))
returns varchar(150)
begin

	-- declare the return variable here
	declare v_path varchar(150);

	select concat(c.homepath, b.homepath, b.itempath, i.filepath, i.filename)
	into v_path
	from tblitembank b, tblclient c, tblitem i
	where b._efk_itembank = i._efk_itembank 
		and c.`name` = v_clientname 
		and b._fk_client = c._key 
		and i._key = v_itemkey;

	return v_path;
	
end$$

DELIMITER ;