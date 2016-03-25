DELIMITER $$

drop procedure if exists `_ft_prioritize_2012` $$

create procedure `_ft_prioritize_2012`(
/*
Description: prioritizes field test items to create an equal sampling distribution across population

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/06/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_oppkey  varbinary(16)
  , v_testkey varchar(250)
  , v_debug   bit -- = 0
)
sql security invoker
proc: begin

    declare v_starttime datetime(3);
    declare v_clientname varchar(100);
    declare v_session varbinary(16);
    declare v_testee bigint;    
    declare v_testid varchar(200);
    declare v_issim bit;
    declare v_tier, v_maxtiers int;

	set v_starttime = now(3);
    set v_issim = issimulation(v_oppkey);

    select clientname, _fk_session, _efk_testee, _efk_testid
	into v_clientname, v_session, v_testee, v_testid
    from testopportunity 
	where _key = v_oppkey;
 
	-- use temporary tables to reduce the cardinality and combinatorics of the join query below
	drop temporary table if exists tmp_tblitemgroups;
    create temporary table tmp_tblitemgroups (
		grpkey varchar(60)
	  , groupid varchar(50)
	  , blockid varchar(50)
	  , activeitems int
	  , tier int
	  , admins int
	);   -- the item groups on this test after prioritization

    -- be sure to get all groups, even those inactivated since they may show up in the queries below from ft_opportunityitem
    -- as having been administered and then inactivated.
    -- since we are just short-circuiting by counting number of records in tmp_tblitemgroups, we want the superset
	drop temporary table if exists tmp_tblgroups;
    create temporary table tmp_tblgroups(
		gkey varchar(60)
	  , gid varchar(50)
	  , bid varchar(50)
	  , lang varchar(50)
	  , active int
	);  -- the itemgroups available for this test
   
    
	-- get all itemgroups this test customized for this testee's accommodations
    if (v_issim = 0) then
        insert into tmp_tblgroups (gkey, gid, bid, active)
		select groupkey, groupid, blockid, count(*) as itemcount
		from ( -- _aa_testoppitempool (v_clientname, v_oppkey, v_testkey, v_testid, 1) -- note: this function automatically customizes the itempool for language and other accommodations
				select i._fk_item as itemkey, i.groupkey, i.groupid, i.blockid, i.isactive
				from itembank.tblsetofadminitems i, configs.client_test_itemconstraint c1, 
					testeeaccommodations a1, itembank.tblitemprops p1 
				where i._fk_adminsubject = v_testkey and i.isfieldtest = 1
					and c1.clientname = v_clientname and c1.testid = v_testid and c1.item_in = 1
					and a1._fk_testopportunity = v_oppkey and a1.acctype = c1.tooltype and a1.acccode = c1.toolvalue
					and p1._fk_adminsubject = v_testkey and p1._fk_item  = i._fk_item and p1.propname = c1.propname and p1.propvalue = c1.propvalue
					and not exists    -- exclude any item possessing an 'item_out' constraint
								 (select * from configs.client_test_itemconstraint c2 , testeeaccommodations a2 , itembank.tblitemprops p2 
								  where a2._fk_testopportunity = v_oppkey 
									and c2.clientname = v_clientname and c2.testid = v_testid and c2.item_in = 0
									and a2.acctype = c2.tooltype and a2.acccode = c2.toolvalue
									and p2._fk_adminsubject = v_testkey and p2._fk_item  = i._fk_item and p2.propname = c2.propname and p2.propvalue = c2.propvalue)
				-- there should be the same number of records per item as there are item_in constraints, check in the 'having' clause
				group by i._fk_item , i.groupkey, i.groupid, i.blockid, i.isactive
				having count(*) = (select count(*) from configs.client_test_itemconstraint c1 , testeeaccommodations a1 
									where c1.clientname = v_clientname and c1.testid = v_testid and c1.item_in = 1
									  and a1._fk_testopportunity = v_oppkey and a1.acctype = c1.tooltype and a1.acccode = c1.toolvalue)
			) t
		where isactive = 1
		group by groupkey, groupid, blockid;
    else 
        insert into tmp_tblgroups (gkey, gid, bid, active)
        select groupkey, groupid, blockid, count(*) as itemcount
        from ( -- dbo._aa_sim_testoppitempool (v_clientname, v_oppkey, v_testkey, v_testid, 1, v_session) -- note: this function automatically customizes the itempool for language and other accommodations
			select i._fk_item as itemkey, i.groupkey, i.groupid, i.blockid, i.irt_b, si.isactive    
			from itembank.tblsetofadminitems i , configs.client_test_itemconstraint c1 , 
				testeeaccommodations a1 , itembank.tblitemprops p1 , sim_segmentitem si 
			where i._fk_adminsubject = v_testkey and si._fk_session = v_session and si._efk_segment = v_testkey 
				and si._efk_item = i._fk_item and si.isactive = 1 and (1 is null or si.isfieldtest = 1)
				and c1.clientname = v_clientname and c1.testid = v_testid and c1.item_in = 1
				and a1._fk_testopportunity = v_oppkey and a1.acctype = c1.tooltype and a1.acccode = c1.toolvalue
				and p1._fk_adminsubject = v_testkey and p1._fk_item  = i._fk_item and p1.propname = c1.propname and p1.propvalue = c1.propvalue
			   and not exists (select * from configs.client_test_itemconstraint c2 , testeeaccommodations a2 , itembank.tblitemprops p2 
								where a2._fk_testopportunity = v_oppkey 
								  and c2.clientname = v_clientname and c2.testid = v_testid and c2.item_in = 0
								  and a2.acctype = c2.tooltype and a2.acccode = c2.toolvalue
								  and p2._fk_adminsubject = v_testkey and p2._fk_item  = i._fk_item and p2.propname = c2.propname and p2.propvalue = c2.propvalue)
			group by i._fk_item , i.groupkey, i.groupid, i.blockid, i.irt_b, si.isactive
			having count(*) = (select count(*) from configs.client_test_itemconstraint c1 , testeeaccommodations a1 
								where c1.clientname = v_clientname and c1.testid = v_testid and c1.item_in = 1
								  and a1._fk_testopportunity = v_oppkey and a1.acctype = c1.tooltype and a1.acccode = c1.toolvalue)
			) t
        where isactive = 1
        group by groupkey, groupid, blockid;

	end if;

	create index _ix_ftgroups on tmp_tblgroups(gkey);

    if (v_debug = 1) then
         select * from tmp_tblgroups;
    end if;

    -- tier system reduced to 2 due to performance issues with group by demographics.
    set v_tier = 1;   

    -- important note: can not stop at v_tier1 because we need to get 
    -- all items remaining in the pool. they are not necessarily all represented in ft_opportunityitem
    if (v_issim = 0) then
        insert into tmp_tblitemgroups (grpkey, groupid, blockid, activeitems, tier, admins)
        select  gkey, gid, bid, active, v_tier, count(*) as admins
        from ft_opportunityitem o, tmp_tblgroups g     -- the items on the test
        where o._efk_fieldtest = v_testkey and g.active > 0 and o.groupkey = g.gkey 
            and coalesce(o.deleted, 0) = 0 
        group by gkey, gid, bid, lang, active
		order by null;
    else
        insert into tmp_tblitemgroups (grpkey, groupid, blockid, activeitems, tier, admins)
        select  gkey, gid, bid, active, v_tier, count(*) as admins
        from ft_opportunityitem o, tmp_tblgroups g     -- the items on the test
        where o._efk_fieldtest = v_testkey and g.active > 0 and o.groupkey = g.gkey 
            and coalesce(o.deleted, 0) = 0 and _fk_session = v_session
        group by gkey, gid, bid, lang, active
		order by null;
    end if;

    create index _ix_ftitems on tmp_tblitemgroups(grpkey);
    set v_tier = v_tier + 1;


    insert ignore into tmp_tblitemgroups (grpkey, groupid, blockid,  activeitems, tier, admins)
    select gkey, gid, bid,  active, v_tier, 0
    from tmp_tblgroups;

	insert into tblout_ft_prioritize_2012
    select grpkey, groupid, blockid, activeitems, tier, admins 
	from tmp_tblitemgroups
    order by tier, admins, uuid();

	call _logdblatency ('_ft_prioritize_2012', v_starttime, null, null, null, v_oppkey, null, v_clientname, null);

	-- clean-up
    drop temporary table tmp_tblgroups;
    drop temporary table tmp_tblitemgroups;

end $$

DELIMITER ;
