-- -------------------------------
-- routine tds_fgetentitykey
-- -------------------------------
delimiter $$

drop function if exists tds_fgetentitykey $$

create function tds_fgetentitykey (
/*
VERSION 	DATE 			AUTHOR 			COMMENTS
001			11/4/2014		Sai V. 			Converted code from SQL Server to MySQL
002			11/5/2014		Sai V.			Added input parameter v_entitytype and limiting the result set to 1
*/
	v_externalID varchar(150)
  , v_entitytype varchar(100))
returns bigint
begin

	declare v_entitykey bigint;
	
	select v._fk_entity into v_entitykey
    from tblattributevalue v
		 join tblentityattribute a on v._fk_entityattribute = a._key
		 join tblentitytype t on a._fk_entitytype = t._key
    where a.fieldname = 'ExternalID' 
		and t.entitytype = v_entitytype
		and (v.enddate is null or v.enddate > now())
		and v.attrvalue = v_externalid
	limit 1;


	return v_entitykey;

end $$

delimiter ;