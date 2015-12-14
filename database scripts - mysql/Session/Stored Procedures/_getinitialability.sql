DELIMITER $$

drop procedure if exists `_getinitialability` $$

create procedure `_getinitialability`(
/*
Description: Is used by the testee app to attempt to get the initial ability estimates for adaptive test
			-- this gets the most recently scored opportunity in this test, or another test of the same subject
			-- ASSUME: Previous year's scores are in an opportunity numbered 0 and are in a testID different from input parameter.

VERSION 	DATE 			AUTHOR 			COMMENTS
001			01/23/2014		Sai V. 			

*/
	v_oppkey varbinary(16)
  , out v_ability float
)
proc: begin  
    
    declare v_testid varchar(150);
    declare v_maxdate datetime(3);
    declare v_clientname varchar(100);
    declare v_testee bigint;
    declare v_bysubject bit;
    declare v_test, v_subject varchar(200);
    declare v_slope, v_intercept float;
    
    select _efk_testee, `subject`, clientname, _efk_adminsubject, _efk_testid
	into v_testee, v_subject, v_clientname, v_test, v_testid
    from testopportunity 
	where _key = v_oppkey;

    select initialabilitybysubject, abilityslope, abilityintercept
	into v_bysubject, v_slope, v_intercept
    from tdsconfigs_client_testproperties
    where clientname = v_clientname and testid = v_testid;

    if (v_bysubject is null) then 
		set v_bysubject = 0;
	end if;

	-- this table holds all possible alternatives. no field is null.
	drop temporary table if exists tbltemp;
    create temporary table tbltemp (oppkey varbinary(16), test varchar(200), opportunity int, scored datetime(3), ability float);

    insert into tbltemp (oppkey, test, opportunity, scored, ability)
    select otheropp._key, otheropp._efk_testid,  otheropp.opportunity, otheropp.datescored, score.`value`
    from testopportunity otheropp, testopportunityscores score
    where clientname = v_clientname 
        -- find all other opportunity candidates, same testee, same subject, scored and not deleted
        and otheropp._efk_testee = v_testee and otheropp.`subject` = v_subject and otheropp.datedeleted is null  
        and otheropp.datescored is not null and otheropp._key <> v_oppkey
        -- that have a usable ability score
        and otheropp._key = score._fk_testopportunity and score.useforability = 1 and score.`value` is not null;

	-- first, try to find a previous opportunity on this exact same test. 
    select max(scored) into v_maxdate   -- look for the latest scored
    from  tbltemp 
    where test = v_test;
    

    if (v_maxdate is not null) then
	begin
    -- found one of same test. return the ability value
	-- select 'same test';
        select ability into v_ability
        from tbltemp 
		where test = v_test and scored = v_maxdate limit 1;

        leave proc;
    end;
	-- failing that, try for same subject/different test
    elseif (v_bysubject = 1) then        
	begin
        select max(scored) into v_maxdate
        from tbltemp 
        where test <> v_test;

        if (v_maxdate is not null) then
            -- select 'different test';
            select ability into v_ability
            from tbltemp 
			where test <> v_test and scored = v_maxdate limit 1;

            leave proc;
        end if;
    end;
	end if;

	-- failing that, try to get an initial ability from the previous year
    if (v_bysubject = 1) then
        select max(initialability) into v_ability       -- on their first test this year, they are likely at their lowest performance level
        from testeehistory 
        where clientname = v_clientname and `subject` = v_subject 
			and _efk_testee = v_testee and initialability is not null;

        if (v_ability is not null) then
            -- select 'history';
            set v_ability = v_ability * v_slope + v_intercept;
            
			leave proc;
        end if;
    end if;
        
    
	-- else get the default for this test from the itembank
    -- select 'itembank';
    set v_ability = itembank_getinitialability (v_test);
    

end$$

DELIMITER ;