DELIMITER $$

drop procedure if exists `load_testadmin` $$

create procedure `load_testadmin` (
/*
Description: Entry point for loading a test administration data from the loader_XXX tables
			-- It is being assumed that tblclient is already populated with the supported clientname's

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/23/2014		Sai V. 			Original Code.
*/
	v_testpackagekey varchar(350)
)
begin
    
	-- declare variables
	declare v_clientname varchar(50);
	declare v_adminkey   varchar(150);
	declare v_purpose 	 varchar(300);
	declare v_version    int;
	declare v_clientkey	 int;


	select clientkey
		 , testadmin
		 , purpose
		 , testversion
	  into v_clientkey, v_adminkey, v_purpose, v_version
	  from loader_testpackage t
	 where packagekey = v_testpackagekey;


	-- check if the package info. already exists
	if (not exists (select * from tbltestadmin
					 where _key = v_adminkey and _fk_client = v_clientkey))
	then
		insert into tbltestadmin (_key, schoolyear, season, _fk_client, description, loadConfig) values 
			(v_adminkey,  '', '', v_clientkey, concat(v_adminkey, ' ', v_purpose) , v_version);
	else
		update tbltestadmin
		   set updateconfig = v_version
		 where _key = v_adminkey;
	end if;

end $$

DELIMITER ;