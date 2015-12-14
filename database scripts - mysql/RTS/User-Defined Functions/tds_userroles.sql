-- ----------------------
-- routine tds_userroles
-- ----------------------
delimiter $$

drop function if exists tds_userroles  $$

create function tds_userroles (
/*
VERSION 	DATE 			AUTHOR 			COMMENTS
001			11/5/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_userkey bigint)
returns bit
begin
	
	-- mysql does not support table valued return from a function
	-- As an alternative, creating a temporary table to store the output data, that temp table will be referred where ever nedeed
	create temporary table if not exists tblout_tds_userroles (role varchar(200), description varchar(300), userentity bigint, roleentity bigint);
	delete from tblout_tds_userroles;


	insert into tblout_tds_userroles
    select role, description, s._fk_entity as userentity, u._fk_entity as roleentity
    from tblrole r, tbluserroles u, tbluser s
    where u._fk_user = v_userkey 
		and u._fk_role = r._key 
		and s._key = v_userkey;

	return 1;

end $$

delimiter ;