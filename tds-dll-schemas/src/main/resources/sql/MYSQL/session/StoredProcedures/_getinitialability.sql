DELIMITER $$

drop procedure if exists `_getinitialability` $$

create procedure `_getinitialability`(
/*
Description: -- t_getinitialability is used by the testee app to attempt to get the initial ability estimates for adaptive test
			-- this gets the most recently scored opportunity in this test, or another test of the same subject
			-- assume: previous year's scores are in an opportunity numbered 0 and are in a testid different from input parameter.

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/06/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_oppkey 		varbinary(16)
  , v_debug 		bit
  , out v_ability 	float
)
sql security invoker
proc: begin
    
    declare v_testid varchar(150);
    declare v_maxdate datetime(3);
    declare v_clientname varchar(100);
    declare v_test varchar(200);
    declare v_testee bigint;
    declare v_subject varchar(200);
    declare v_bysubject bit;
    declare v_slope, v_intercept float;

	declare v_procname varchar(100);
	declare v_starttime datetime(3);
	set v_starttime = now(3);
	set v_procname = '_getinitialability';
    
    select _efk_testee, `subject`, clientname, _efk_adminsubject, _efk_testid
	into v_testee, v_subject, v_clientname, v_test, v_testid
    from testopportunity 
	where _key = v_oppkey;

    select initialabilitybysubject, abilityslope, abilityintercept
	into v_bysubject, v_slope, v_intercept
    from configs.client_testproperties
    where clientname = v_clientname and testid = v_testid;

    if (v_bysubject is null) then set v_bysubject = 0; end if;

	-- this table holds all possible alternatives. no field is null.
	drop temporary table if exists tmp_tbl;
    create temporary table tmp_tbl(
		oppkey 		varbinary(16)
	  , test 		varchar(200)
	  , opportunity int
	  , scored 		datetime(3)
	  , ability 	float
	);

    insert into tmp_tbl (oppkey, test, opportunity, scored, ability)
    select otheropp._key, otheropp._efk_testid,  otheropp.opportunity, otheropp.datescored, score.value
    from testopportunity otheropp, testopportunityscores score
    where clientname = v_clientname 
        -- find all other opportunity candidates, same testee, same subject, scored and not deleted
        and otheropp._efk_testee = v_testee and otheropp.`subject` = v_subject and otheropp.datedeleted is null  
        and otheropp.datescored is not null and otheropp._key <> v_oppkey
        -- that have a usable ability score
        and otheropp._key = score._fk_testopportunity and score.useforability = 1 and score.`value` is not null;

	-- first, try to find a previous opportunity on this exact same test. 
    set v_maxdate = (select max(scored)   -- look for the latest scored
					   from  tmp_tbl where test = v_test);
    
    if (v_maxdate is not null) then
	begin
		-- found one of same test. return the ability value
        if v_debug = 1 then select 'same test'; end if;
        select ability into v_ability
        from tmp_tbl where test = v_test and scored = v_maxdate limit 1;
        leave proc;
    end;
    elseif v_bysubject = 1 then  -- failing that, try for same subject/different test
	begin        
        select max(scored) into v_maxdate
        from tmp_tbl where test <> v_test;

        if (v_maxdate is not null) then 
		begin
			if v_debug = 1 then select 'different test'; end if;
            select ability into v_ability
            from tmp_tbl where test <> v_test and scored = v_maxdate limit 1;
            leave proc;
        end;
		end if;
    end;
	end if;

	-- failing that, try to get an initial ability from the previous year
    if (v_bysubject = 1) then 
	begin
        select max(initialability) into v_ability       -- on their first test this year, they are likely at their lowest performance level
        from testeehistory 
        where clientname = v_clientname and _efk_testee = v_testee and `subject` = v_subject and initialability is not null;

        if (v_ability is not null) then 
		begin
            if v_debug = 1 then select 'history'; end if;
            set v_ability = v_ability  * v_slope + v_intercept;
            leave proc;
        end;
		end if;
    end;
	end if;
            
	-- else get the default for this test from the itembank
	if v_debug = 1 then select 'itembank'; end if; 
	set v_ability = itembank.getinitialability(v_test);
    
	-- clean-up
	drop temporary table tmp_tbl;

	call _logdblatency(v_procname, v_starttime, null, null, null, v_oppkey, null, v_clientname, null);

end $$

DELIMITER ;
