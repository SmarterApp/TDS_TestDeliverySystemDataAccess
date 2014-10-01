-- ---------------------------------
-- routine tds_accommodationsstring
-- ---------------------------------
delimiter $$

drop function if exists tds_accommodationsstring $$

create function tds_accommodationsstring(
/*
VERSION 	DATE 			AUTHOR 			COMMENTS
001			11/4/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_testee bigint)
returns varchar(8000) deterministic
begin
	
	declare v_str varchar(8000);

    declare v_key int;
    declare v_codestr varchar(8000);

	-- checking to see if the table already exists.
	-- if it already exists deleting the table content instead of dropping the table, b'coz 
	-- mysql does not allow explicit or implicit commits within a function.
	create temporary table if not exists allcodes (idx int auto_increment primary key, codestr varchar(8000));
	delete from allcodes;

    insert into allcodes (codestr)
    select coalesce(v.tdsacc,v.attrvalue)
    from tblentityattribute a, tblattributevalue v
	where a.fieldname like 'tdsacc-%' and v._fk_entity = v_testee  and v._fk_entityattribute = a._key 
        and v.attrvalue is not null and length(v.attrvalue) > 0
        and (v.enddate is null or v.enddate > now());

    select a.idx, a.codestr 
	  into v_key, v_codestr 
	from allcodes a limit 1;

	delete from allcodes where idx = v_key;

	set v_str = v_codestr;
    
	while (exists (select * from allcodes)) do
		select a.idx, a.codestr 
		into v_key, v_codestr 
		from allcodes a limit 1;

        delete from allcodes where idx = v_key;
        
		set v_str = v_str + ';' + v_codestr;
    end while;

    return v_str;

end $$

delimiter ;