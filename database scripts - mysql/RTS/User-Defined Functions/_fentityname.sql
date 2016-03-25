-- ---------------------
-- routine _fentityname
-- ---------------------
delimiter $$

drop function if exists _fentityname $$

create function _fentityname (
/*
VERSION 	DATE 			AUTHOR 			COMMENTS
001			11/4/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_entitykey bigint)
returns varchar(200)
begin

    declare v_entitytype bigint;
    declare v_result varchar(200);
    declare v_etypename varchar(100);
    declare v_etypekey bigint;
	declare v_fname varchar(100);
	declare v_lname varchar(100);


	select _fk_entitytype into v_entitytype 
	from tblentity 
	where _key = v_entitykey;


    if (v_entitytype not in (3, 6)) then
        select attrvalue into v_result
        from tblentityattribute a, tblattributevalue v
        where _fk_entitytype = v_entitytype and fieldname like '%Name' 
            and _fk_entity = v_entitykey and a._key = v._fk_entityattribute
            and (enddate is null or enddate > now());
        
		return v_result;
    end if;

	
    if (v_entitytype = 3) then
        select attrvalue into v_fname
        from tblentityattribute a, tblattributevalue v
        where _fk_entitytype = v_entitytype and fieldname = 'FirstNM'
            and _fk_entity = v_entitykey and a._key = v._fk_entityattribute
            and (enddate is null or enddate > now());

        select attrvalue into v_lname
        from tblentityattribute a, tblattributevalue v
        where _fk_entitytype = v_entitytype and fieldname = 'LastNM'
            and _fk_entity = v_entitykey and a._key = v._fk_entityattribute
            and (enddate is null or enddate > now());
    end if;


    if (v_entitytype = 6) then
		select attrvalue into v_fname
        from tblentityattribute a, tblattributevalue v
        where _fk_entitytype = v_entitytype and fieldname = 'lglfNM'
            and _fk_entity = v_entitykey and a._key = v._fk_entityattribute
            and (enddate is null or enddate > now());
        
		select attrvalue into v_lname
        from tblentityattribute a, tblattributevalue v
        where _fk_entitytype = v_entitytype and fieldname = 'lgllNM'
            and _fk_entity = v_entitykey and a._key = v._fk_entityattribute
            and (enddate is null or enddate > now());
    end if;


    if (v_fname is not null and v_lname is not null) then
        return v_lname + ', ' + v_fname;
	end if;

    if (v_fname is null) then return v_lname; end if;
    if (v_lname is null) then return v_fname; end if;

    return '';

end $$

delimiter ;