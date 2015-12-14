-- -------------------------------
-- routine tds_fparentinstitution
-- -------------------------------
delimiter $$

drop procedure if exists tds_fparentinstitution  $$

create procedure tds_fparentinstitution (
/*
VERSION 	DATE 			AUTHOR 			COMMENTS
001			11/4/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_childkey bigint
  , v_reltype varchar(50))
begin

	select v0._fk_entity as rtskey, v0.attrvalue as externalid, v1.attrvalue as entityname
	from tblattributevalue v0, tblentityattribute a0,
		 tblattributevalue v1, tblentityattribute a1
	where a0.fieldname = 'ExternalID' and v0._fk_entityattribute = a0._key
		and v0._fk_entity = _fgetparentkey(v_childkey, v_reltype)
		and (v0.enddate is null or v0.enddate > now())
		and v1._fk_entity = v0._fk_entity and a1.fieldname = 'EntityName' 
		and v1._fk_entityattribute = a1._key and (v1.enddate is null or v1.enddate > now());

end $$

delimiter ;