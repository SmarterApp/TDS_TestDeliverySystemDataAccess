DELIMITER $$

drop procedure if exists `_recordbpsatisfaction` $$

create procedure `_recordbpsatisfaction`(
/*
DESCRIPTION: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/11/2015		Sai V. 			Code Migration to MySQL
*/
   	v_oppkey 	varbinary(16)
)
sql security invoker
proc: begin

    declare v_testkey varchar(200);
	declare v_testid varchar(150);
	declare v_session varbinary(16);
        
    select _efk_adminsubject, _efk_testid, _fk_session
	into v_testkey, v_testid, v_session
    from testopportunity where _key = v_oppkey;

	drop temporary table if exists tmp_tblitems;
    create temporary table tmp_tblitems(
		_key varchar(100)
	  , segment varchar(250)
	  , contentlevel varchar(200)
	);
    
	insert into tmp_tblitems (_key, segment, contentlevel) 
    select _efk_itemkey, _efk_segment, c.contentlevel
    from testeeresponse r, testopportunitysegment s, itembank.aa_itemcl c
    where r._fk_testopportunity = v_oppkey and s._fk_testopportunity = v_oppkey 
        and s.segmentposition = r.segment and c._fk_adminsubject = s._efk_segment
        and c._fk_item = r._efk_itemkey and r.isfieldtest = 0;
    
	-- record strand and contentlevel counts for blueprint analysis 
    if (exists (select * from testopportunitycontentcounts where _fk_testopportunity = v_oppkey)) then
        delete from testopportunitycontentcounts 
		where _fk_testopportunity = v_oppkey;
	end if;

    insert into testopportunitycontentcounts (_fk_testopportunity, _efk_testid, _efk_adminsubject, contentlevel, itemcount)
    select v_oppkey, v_testid, v_testkey, contentlevel, count(*)
    from tmp_tblitems i
    group by contentlevel;

    if (exists (select * from testopportunitysegmentcounts where _fk_testopportunity = v_oppkey)) then
        delete from testopportunitysegmentcounts 
		where _fk_testopportunity = v_oppkey;
	end if;

    insert into testopportunitysegmentcounts (_fk_testopportunity, _efk_testid, _efk_adminsubject, _efk_segment, contentlevel, itemcount)
    select v_oppkey, v_testid, v_testkey, segment, contentlevel, count(*)
    from tmp_tblitems i
    group by segment, contentlevel;

    -- clean-up
	drop temporary table tmp_tblitems;

end $$

DELIMITER $$