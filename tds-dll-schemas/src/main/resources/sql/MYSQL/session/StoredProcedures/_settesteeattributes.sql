DELIMITER $$

drop procedure if exists `_settesteeattributes` $$

create procedure `_settesteeattributes`(
/*
DESCRIPTION: -- this procedure runs through all the testee attributes and relationships that are registered in tdsconfigs 
			-- and stores the data in testeeattribute and testeerelationship

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/28/2015		Sai V. 			Code Migration
*/
	v_clientname varchar(100)
  ,	v_oppkey 	varbinary(16)
  , v_testee 	bigint
  , v_context 	varchar(200)
)
sql security invoker
proc: begin

    declare v_starttime datetime(3);
    declare v_sofar varchar(30);
    declare v_msg text;
    declare tmp_tblattsexist, tmp_tblrelsexist bit;

	declare exit handler for sqlexception
	begin
		rollback;
		set v_msg = concat('mysql exit handler: error at ', v_sofar);
		call _logdberror('_settesteeattributes', v_msg, v_testee, null, null, v_oppkey, v_clientname, null);
	end;

    set v_starttime = now(3);

    if (v_testee < 0) then leave proc; end if;    -- anonymous testees do not have rts-based attributes

	drop temporary table if exists tmp_tblrels;    
	create temporary table tmp_tblrels (
		reltype varchar(50)
	  , tdsid varchar(100)
	  , entitykey bigint
	  , attval varchar(500)
	);


    if (exists (select 1 from testeeattribute where _fk_testopportunity = v_oppkey and `context` = v_context)) then
        set tmp_tblattsexist = 1;
    else 
		set tmp_tblattsexist = 0;
	end if;

    if (exists (select 1 from testeerelationship where _fk_testopportunity = v_oppkey and `context` = v_context)) then
        set tmp_tblrelsexist = 1;
    else 
		set tmp_tblrelsexist = 0;
	end if;

    drop temporary table if exists tmp_tbltesteeattributes;
	create temporary table tmp_tbltesteeattributes(
		tdsid varchar(50)
	  , attval varchar(500)
	);

    drop temporary table if exists tblout_gettesteerelationships;
	create temporary table tblout_gettesteerelationships(
		relationtype varchar(50)
	  , entitykey bigint
	  , tdsid varchar(100)
	  , attval varchar(500)
	);

	start transaction;
		-- call _gettesteeattributes(v_clientname, v_testee);
		insert into tmp_tbltesteeattributes
		select d.attrname, d.attrvalue
		from r_studentpackage sp, r_studentpackagedetails d
		where sp._key = d._fk_studentpackagekey
			and sp.studentkey = 105 -- v_testee 
			and sp.clientname = 'sbac' -- v_clientname 
			and sp.iscurrent = 1 and d.istesteeattribute = 1;
		
		set v_sofar = 'first. ';

		call _gettesteerelationships(v_clientname, v_testee);

		insert into tmp_tblrels (reltype, entitykey, tdsid, attval)
		select * from tblout_gettesteerelationships;

		set v_sofar = 'second. ';
		
		if (tmp_tblattsexist = 1) then
			delete from testeeattribute 
			where _fk_testopportunity = v_oppkey and `context` = v_context;
		end if;

		set v_sofar = 'third. ';

		insert into testeeattribute (_fk_testopportunity, `context`, tds_id, attributevalue, _date)
		select v_oppkey, v_context, tdsid, attval, now(3)
		from tmp_tbltesteeattributes;

		set v_sofar = 'fourth. ';

		if (tmp_tblrelsexist = 1) then
			delete from testeerelationship 
			where _fk_testopportunity = v_oppkey and `context` = v_context;
		end if;

		set v_sofar = 'fifth. ';

		insert into testeerelationship (_fk_testopportunity, `context`, relationship, tds_id, entitykey, attributevalue, _date)
		select v_oppkey, v_context, reltype, tdsid, entitykey, attval, now(3)
		from tmp_tblrels;

		set v_sofar = 'sixth. ';
	commit;

    call _logdblatency('_settesteeattributes', v_starttime, v_testee, null, null, v_oppkey, null, v_clientname, null);

end $$

DELIMITER ;