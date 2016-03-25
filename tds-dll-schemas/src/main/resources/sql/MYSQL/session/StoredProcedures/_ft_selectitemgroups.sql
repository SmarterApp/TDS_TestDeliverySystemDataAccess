DELIMITER $$

drop procedure if exists `_ft_selectitemgroups` $$

create procedure `_ft_selectitemgroups`(
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/06/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_testoppkey varbinary(16)
  , v_testkey varchar(250)
  , v_segment int
  , v_segmentid varchar(100)
  , v_language varchar(120)
  , out v_ftcount int
  , v_debug int -- = 0
  , v_noinsert int -- = 0
)
sql security invoker
proc: begin

	declare v_now datetime(3);

	-- variables for holding selected itemgroup info
    declare v_tier int;
    declare v_cnt int;
    declare v_numitems int;	
    declare v_grpkey varchar(60);
    declare v_grp varchar(50);
    declare v_block varchar(10);
    declare v_parenttest varchar(250);
    
    declare v_testee bigint;
    declare v_clientname varchar(100);
    declare v_subject varchar(100);
    declare v_session varbinary(16);
    declare v_issim bit;

	-- variables for holding test specs
	declare v_startpos int;	-- first position on test for field test item
	declare v_endpos int;	-- last position on test for field test item
	declare v_numintervals int;	-- number of equally-spaced position intervals on test for field test items
    declare v_minitems int;   -- min num items required for this field test
    declare v_maxitems int;   -- max num items required for this field test
    
	declare v_intervalsize int;	-- the target size of intervals 
    declare v_thisintsize int;  -- the interval size is computed for each time through the loop
                                -- thisintsize = 1 for item groups of 1 item, = n - 1 for itemgroups of > 1 item
                                -- the -1 is to allow room for the group to flow over its selected start position without encroaching on the next interval

	-- working variables 
    declare v_nextpos int;		 -- start position of the currently selected item group
	declare v_intervalindex int; -- this starts at startpos + 1 and is incremented by v_intervalsize * num_items_in_group each time through the loop
    declare v_itemcohort int;

	declare exit handler for sqlexception
	begin
		rollback;
		call _logdberror('_ft_selectitemgroups', 'mysql exit handler: sqlexception', v_testee, v_testkey, null, v_testoppkey, null, null);
		set v_ftcount = 0;
	end;

    set v_issim = issimulation(v_testoppkey);
	set v_now = now(3); 
   
    select _efk_testee, clientname, `subject`, _efk_adminsubject, _fk_session
	into v_testee, v_clientname, v_subject, v_parenttest, v_session
    from testopportunity 
	where _key = v_testoppkey; 

    if (v_issim = 0) then
        select ftminitems, ftmaxitems, ftstartpos, ftendpos
		into v_minitems, v_maxitems, v_startpos, v_endpos
        from itembank.tblsetofadminsubjects
        where _key = v_testkey;
    else 
		select ftminitems, ftmaxitems, ftstartpos, ftendpos
		into v_minitems, v_maxitems, v_startpos, v_endpos
        from sim_segment 
        where _fk_session = v_session and _efk_segment = v_testkey;
	end if;

	-- does this test opportunity already have items that have been selected/administered?
	set v_ftcount = (select sum(numitems) from ft_opportunityitem
					  where _fk_testopportunity = v_testoppkey and _efk_fieldtest = v_testkey and coalesce(deleted, 0) = 0);

    if (v_ftcount is null) then 
		set v_ftcount = 0;	-- how many total field test items administered so far
    elseif (v_ftcount >= v_minitems) then
        if (v_debug > 0) then select 'aborted selection due to existing items'; end if;
        leave proc;
    end if;

	-- compute number of position slots
	set v_numintervals = v_maxitems;	-- there is one interval per item. item groups with n items > 1 get n - 1 intervals in which to randomly place their block of items
	set v_intervalsize = (v_endpos - v_startpos )/v_numintervals;	
	set v_intervalindex = v_startpos;	-- working interval position index. not really an interval index, but rather the position at the start of the next interval

	
	-- table tmp_tblitems, after calling _ft_prioritizeitemgroups, has items prioritized by tier then admins.
	-- tier 1 is the highest priority, with admins being the priority within the tier.
	-- every field test item group will have one and only one entry in tmp_tblitems with its tier/admins priority set
	-- tiers are:
	-- 1. all items not ever administered in this district
	-- 2. all items administered in the district but not in this school
	-- 3. all items administered in this school but not to this demographic stratum (ethnicity x gender)
	-- 4. all items that have been administered in this school to this demographic stratum
	-- admins is the number of times the itemgroup has been administered within the tier

	drop temporary table if exists tmp_tblitems;
    create temporary table tmp_tblitems(grpkey varchar(60), grp varchar(50), `block` varchar(10),  numitems int, tier int, admins int, 
            position int, intervalstart int, intervalsize int, numintervals int, selected bit, used bit);

	/* */
	drop temporary table if exists tblout_ft_prioritize_2012;
	create temporary table tblout_ft_prioritize_2012(grpkey varchar(60), grp varchar(50), `block` varchar(10),  numitems int, tier int, admins int);
	call _ft_prioritize_2012(v_testoppkey, v_testkey, 0);
	/* */

    insert into tmp_tblitems(grpkey, grp, `block`, numitems, tier, admins)
	select *
	from tblout_ft_prioritize_2012;

    create index tix_ftitems on tmp_tblitems(grpkey);

    -- assert: tmp_tblitems has one entry for every fieldtest itemgroup on v_testid
    -- update must be done prior to deleting used items because we use the groupid (not key) to delete them
    -- and prioritize does not set the group id
    update tmp_tblitems set  selected = 0, used = 0;
    
    if (v_debug = 1) then        
        select 'original selected items', v_language as `language`, i.* from tmp_tblitems i order by tier, admins;
    end if;

    -- remove all itemgroups administered to this student
    delete 
	from tmp_tblitems 
    where grp in        -- do not use grpkey because we want to exclude all blocks within a group
				(select groupid from ft_opportunityitem f, testopportunity o
				  where o._efk_testee = v_testee and o.clientname = v_clientname and o.`subject` = v_subject and f._fk_testopportunity = o._key);
	
    if (v_debug >= 1) then
        if (exists (select * from tmp_tblitems)) then 
			select 'filtered candidate items', i.* from tmp_tblitems i order by tier, admins;
        else 
			select 'filtered candidate items', 0 as numitems;
		end if;
    end if;

    if (v_debug <> 0) then
		select 'control variables', v_startpos as startpos, v_endpos as endpos, v_minitems as minitems, v_maxitems as maxitems,
			 v_numintervals as numintervals, v_intervalsize as intervalsize, v_ftcount as ftcount;        
    end if;

    if (not exists (select * from tmp_tblitems)) then 
		leave proc;
	end if;

    --  added 4/2010 lea: test cohorts create partitions of items which can be proportionally selected from for a single test
	--  assumptions: sum of cohort itemratios = 1
	--  every item is assigned a (single) cohort index
	--  every cohort index is assigned an itemratio
	-- start: declare cohorts variables
	drop temporary table if exists tmp_tblcohorts;
    create temporary table tmp_tblcohorts(cohortindex int, ratio float, available int, targetcount float, itemcount int, groupcount int) engine = memory;

    -- cohort is the index of the cohort on the test, ratio is the percent of items to administer in the cohort on each test
    -- targetcount is the number of items to administer from the cohort on each test (ratio * maxitems)
    -- itemcount is the running count of items assigned to this test for the cohort
    -- groupcount is a working value used in determining whether to select a given itemgroup during a single iteration of the selection loop below
    insert into tmp_tblcohorts (cohortindex, ratio, targetcount, itemcount)
    select cohort, itemratio, itemratio * v_maxitems, 0
    from itembank.testcohort 
	where _fk_adminsubject = v_testkey;

    if (not exists (select * from tmp_tblcohorts)) then
        insert into tmp_tblcohorts (cohortindex, ratio, targetcount, itemcount)
        values (1, 1, v_maxitems, 0);
    end if;

    if (v_debug <> 0) then 
		select 'cohorts', c.* from tmp_tblcohorts c;
	end if;

    -- -----================ -------
    -- the following was previously computed above in the select to create a buffer before the end position
    -- for tests of varying sized itemgroups, such as reading
    -- however, elpa tests (for oregon) involved a single fieldtest item administered within a very narrow range 
    -- on a very short test. so this buffer had to be adjusted to be conditional upon more info
    if (v_endpos - v_startpos > v_maxitems + 10) then 
		set v_endpos = v_endpos - 2;    
	end if;
    -- ------------------------------------

    whilelabel: while (v_ftcount < v_minitems and exists (select * from tmp_tblitems where used = 0)) do 
	begin
        -- items are prioritized by tier and number of times administeres within the tier
        update tmp_tblcohorts set groupcount = 0;     -- reinitialize the working variable in the cohorts table

        set v_tier = (select max(tier) from tmp_tblitems where used = 0);
        set v_cnt  = (select min(admins) from tmp_tblitems where tier = v_tier and used = 0);

        select grpkey, grp, `block`, numitems 
		into v_grpkey, v_grp, v_block, v_numitems
		from tmp_tblitems
        where tier = v_tier and admins = v_cnt and used = 0
		limit 1;
       
        if v_debug = 3 then select 'loop vars', v_tier tier, v_cnt cnt, v_grpkey grpkey, v_numitems numitems; end if;

        -- remove this item group from further consideration
        update tmp_tblitems set used = 1 where grpkey = v_grpkey;

        -- record the cohort counts in this item group's items. note that a single item group may have items from different cohorts.
        update tmp_tblcohorts 
		set groupcount = (select count(*) from itembank.tblsetofadminitems i, itembank.tblitemprops p
						   where i._fk_adminsubject = v_testkey and p._fk_adminsubject = v_testkey and i.groupkey = v_grpkey
							 and p.propname = 'language' and p.propvalue = v_language and p._fk_item = i._fk_item
							 and p._fk_adminsubject = v_testkey and i.testcohort = cohortindex);


		-- chose the least restrictive
        if (not exists (select * from tmp_tblcohorts where groupcount > 0 and itemcount < targetcount)) then  -- there is no cohort that is below target that this group can help satisfy
            if v_debug > 0 then select 'continue 2'; end if;
            iterate whilelabel;
        end if;

        if (v_numitems > 0 and v_ftcount + v_numitems <= v_maxitems) then
		begin
            if (v_intervalsize < 1) then
                set v_thisintsize = 1;
			elseif (v_numitems = 1) then
				set v_thisintsize = v_intervalsize;
            else 
				set v_thisintsize = v_intervalsize * (v_numitems - 1);
			end if;

            -- randomize the start position of the item group within the slot range determined
			set v_nextpos =  (cast(round(rand() * 1000, 0) as unsigned) % v_thisintsize) + v_intervalindex;
         
			if (v_debug = 3) then
				select 'loop ', v_numitems as numitems, v_nextpos as thispos, v_intervalindex as intstart, v_thisintsize as intsize; 
            end if;

		-- since numitems is subject to change after insertion due to item inactivation, we store v_numitems as numintervals
        -- to facilitate item replacement in case of inactivation of items not yet administered
        -- ft_opportunityitem is used to select and report on item distributions, while ft_itemadmin is used
        -- to administer the items to test opportunities when their positions come up
		-- indicate this item group is selected
            update tmp_tblitems 
			set selected = 1
			  , position = v_nextpos
			  , intervalstart = v_intervalindex
			  , intervalsize = v_intervalsize
			  , numintervals = v_numitems
            where grpkey = v_grpkey;

		-- indicate that all blocks related to this group are 'used'
            update tmp_tblitems set used = 1 where grp = v_grp;

		-- record impact of this itemgroup selection on algorithm variables
            set v_ftcount = v_ftcount + v_numitems;        -- number of items selected so far
            set v_intervalindex = v_intervalindex + v_numitems * case v_intervalsize when 0 then 1 else v_intervalsize end;   
		-- add to the cohort itemcount (never alter the targetcount)
            update tmp_tblcohorts set itemcount = itemcount + groupcount; -- groupcount is the number of items in this group in the respective cohort
        end;       -- if v_ftcount 
		end if;
	end;
    end	while;	-- while v_ftcount < v_min

    if (v_debug = 0) then 
		delete from tmp_tblitems where selected = 0; 
	end if;

	-- now do bulk inserts
    if (v_noinsert = 1) then 
		select * from tmp_tblitems where selected = 1;
    else 
	begin
	start transaction;
        insert into ft_opportunityitem(_fk_testopportunity, _fk_session, _efk_fieldtest, _efk_parenttest, segment, segmentid, `language`, position, groupkey, groupid, blockid, numitems,  intervalstart, intervalsize, numintervals, dateassigned, deleted)
        select v_testoppkey, v_session, v_testkey, v_parenttest, v_segment, v_segmentid, v_language, position, grpkey, grp, `block`, numitems, intervalstart, intervalsize, numintervals, now(3), 0
        from tmp_tblitems 
        where selected = 1 
        and not exists (select * from ft_opportunityitem where _fk_testopportunity = v_testoppkey and _efk_fieldtest = v_testkey);
        
        set v_ftcount = (select sum(numitems) from ft_opportunityitem 
						  where _fk_testopportunity = v_testoppkey and _efk_fieldtest = v_testkey and coalesce(deleted, 0) = 0);

        if (v_ftcount is null) then set v_ftcount = 0; end if;
	commit;
    end;
	end if;

    if (v_issim = 1) then
	begin
        insert into ft_groupsamples(_efk_adminsubject, groupid, blockid, groupkey, samplesize, _efk_parenttest, clientname, _fk_session)
        select v_testkey, grp, `block`, grpkey, 0, v_parenttest, v_clientname, v_session
        from tmp_tblitems 
		where selected = 1
			and not exists (select * from ft_groupsamples 
							 where _efk_adminsubject = v_testkey and groupkey = grpkey and _efk_parenttest = v_parenttest and _fk_session = v_session);

        update ft_groupsamples, tmp_tblitems
		set samplesize = samplesize + 1
        where selected = 1 and _efk_adminsubject = v_testkey and groupkey = grpkey and _fk_session = v_session;
    end;
	end if;

    if (v_debug > 0) then
        select 'final selection', i.* from tmp_tblitems i order by selected desc, position;
        select 'final cohorts', c.* from tmp_tblcohorts c;
    end if;
	
	-- clean-up
	drop temporary table tmp_tblitems;
	drop temporary table tmp_tblcohorts;
	drop temporary table tblout_ft_prioritize_2012;

	call _logdblatency('_ft_selectitemgroups', v_now, v_testee, 1, null, v_testoppkey, null, v_clientname, null);

end $$

DELIMITER ;