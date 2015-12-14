-- -------------------------------
-- routine tds_fgetparentkeys
-- -------------------------------
delimiter $$

drop procedure if exists tds_fgetparentkeys  $$

create procedure tds_fgetparentkeys (
/*
VERSION 	DATE 			AUTHOR 			COMMENTS
001			11/4/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_childkey bigint
  , v_relationship varchar(100))
begin

	select r._fk_entity_a as parentkey
	from tblrelationship r, tblrelationshiptype t
	where t.relationshiptype = v_relationship 
		and t._key = r._fk_relationshiptype
		and r._fk_entity_b = v_childkey 
		and (r.enddate is null or r.enddate > now());

end $$

delimiter ;