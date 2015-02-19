DELIMITER $$

drop procedure if exists `_gettesteerelationships` $$

create procedure `_gettesteerelationships`(
/*
DESCRIPTION: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/28/2015		Sai V. 			Code Migration
*/
	v_clientname varchar(100)
  , v_testee 	bigint
)
sql security invoker
proc: begin

    declare v_parentkeys text;
    declare v_reltype varchar(50);
	declare v_relation varchar(100);
	declare v_parentkey bigint;

	if (v_testee < 0) then -- for guest students, we don't have to retreive any of the relationship data, as there isn't any
		leave proc;
	end if;

	drop temporary table if exists tmp_tblrelations;
    create temporary table tmp_tblrelations (reltype varchar(50), rtsname varchar(100)) engine = memory;

	drop temporary table if exists tmp_tblattributes;
    create temporary table tmp_tblattributes (relationtype varchar(50), entitykey bigint, attname varchar(50), rtsname varchar(100), attval varchar(300)) engine = memory;

	insert into tmp_tblrelations (reltype, rtsname)
    select tds_id, rtsname
    from configs.client_testeeattribute 
	where clientname = v_clientname and `type` = 'relationship';

    while (exists (select * from tmp_tblrelations)) do
	begin
        select reltype, rtsname into v_reltype, v_relation
		from tmp_tblrelations limit 1;

        delete from tmp_tblrelations 
		where reltype = v_reltype;

		call _getrtsattribute(v_clientname, v_testee, v_relation, v_parentkeys /*output*/, 'student', 0);

		insert into tmp_tblattributes(relationtype, entitykey, attname, rtsname, attval)
		select v_reltype
			 , 0
			 , tds_id
			 , rtsname
			 , case when tds_id like '%id' 	 then substring_index(v_parentkeys, ':', 1) 
					when tds_id like '%name' then substring_index(v_parentkeys, ':', -1)
					else null
			   end
        from configs.client_testeerelationshipattribute
		where clientname = v_clientname and relationshiptype = v_reltype;
        
	end;
	end while;

	insert into tblout_gettesteerelationships
	select relationtype, entitykey, attname as tds_id,  attval 
	from tmp_tblattributes;

end $$

DELIMITER ;