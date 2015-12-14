-- ----------------------
-- routine _fparentkeys
-- ----------------------
delimiter $$

drop function if exists _fparentkeys  $$

create function _fparentkeys (
/*
VERSION 	DATE 			AUTHOR 			COMMENTS
001			11/5/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_child bigint)
returns bit
begin
	
	-- mysql does not support table valued return from a function
	-- As an alternative, creating a temporary table to store the output data, that temp table will be referred where ever nedeed
	create temporary table if not exists tblout_fparentkeys (entitykey bigint, relationshiptype varchar(200), entitytypekey int, entitytype varchar(200));
	delete from tblout_fparentkeys;


	insert into tblout_fparentkeys
	select r._fk_entity_a as entitykey, t.relationshiptype, y._key as entitytypekey, y.entitytype	-- this name eliminates naming conflicts with rts tables
	from tblrelationship r, tblrelationshiptype t, tblentity e, tblentitytype y
	where 
		t._key = r._fk_relationshiptype
		and r._fk_entity_b = v_child and (r.enddate is null or r.enddate > now())
        and r._fk_entity_a = e._key and e._fk_entitytype = y._key;

	return 1;

end $$

delimiter ;