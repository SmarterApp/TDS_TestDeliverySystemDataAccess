-- -------------------------------
-- routine tds_fgetentityattribute
-- -------------------------------
delimiter $$

drop function if exists tds_fgetentityattribute $$

create function tds_fgetentityattribute (
/*
VERSION 	DATE 			AUTHOR 			COMMENTS
001			11/4/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_entitykey bigint
  , v_attname varchar(50))
returns text
begin

	declare v_value varchar(255);
	declare v_result text;
    declare v_attkey bigint;
    declare v_entitytype bigint;
    
	select _fk_entitytype into v_entitytype
	from tblentity 
	where _key = v_entitykey;

	-- checking to see if the table already exists.
	-- if it already exists deleting the table content instead of dropping the table, b'coz 
	-- mysql does not allow explicit or implicit commits within a function.
    create temporary table if not exists tblvals (val varchar(255));
	delete from tblvals;

    select _key into v_attkey
	from tblentityattribute 
    where _fk_entitytype = v_entitytype 
		and fieldname = v_attname;
    
    if (v_attkey is null) then return null; end if;

    insert into tblvals (val)
	select v.attrvalue
	from tblattributevalue v
	where v._fk_entity = v_entitykey and v._fk_entityattribute = v_attkey
		and (v.enddate is null or v.enddate > now());

    while (exists (select * from tblvals)) do
        select val into v_value from tblvals limit 1;
        delete from tblvals where val = v_value;

        if (v_result is null) then
            set v_result = v_value;
        else 
			set v_result = v_result + ';' + v_value;
		end if;
    end while;

	return v_result;

end $$

delimiter ;