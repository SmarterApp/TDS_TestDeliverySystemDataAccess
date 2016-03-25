-- ----------------------
-- routine _fgetparentkey
-- ----------------------
delimiter $$

drop function if exists _fgetparentkey  $$

create function _fgetparentkey (
/*
-- Description:	function to return the parent key of a parent-child relationship

VERSION 	DATE 			AUTHOR 			COMMENTS
001			11/4/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_childkey 	bigint
  , v_rel 		varchar(100))
returns bigint
begin

	-- declare the return variable here
	declare v_parentkey bigint;

	select r._fk_entity_a into v_parentkey
	from tblrelationship r, tblrelationshiptype t, tblentity e, tblentitytype y
	where t._key = r._fk_relationshiptype
		and r._fk_entity_b = v_childkey 
		and (r.enddate is null or r.enddate > now())
		and r._fk_entity_a = e._key and e._fk_entitytype = y._key
		and t.relationshiptype = v_rel;

	-- return the result of the function
	return v_parentkey;

end $$

delimiter ;