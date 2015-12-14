DELIMITER $$

drop function if exists `canscoreopportunity` $$

create function `canscoreopportunity`(
/*
DESCRIPTION:

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/10/2015		Sai V. 			Code Migration
*/
	v_oppkey varbinary(16)
)
returns varchar(200)
sql security invoker
begin

	declare v_ok bit;
    declare v_archived datetime(3);
    declare v_scored datetime(3);
    declare v_scorable bit;
    declare v_scorebytds bit;
	declare v_clientname varchar(100);
    declare v_test varchar(200);

    set v_scorable = 0 ;
    set v_ok = 0;

    select _efk_testid, clientname 
	into v_test, v_clientname
	from testopportunity where _key = v_oppkey;

    set v_scorebytds = scorebytds(v_clientname, v_test);

    if (exists (select * from testopportunitysegment where _fk_testopportunity = v_oppkey and issatisfied = 0)) then
        return 'blueprint not satisfied';
	end if;
    
    -- check all the conditions for reporting an opportunity:
    -- 1. it is complete
    -- 2. all items that can be scored have been scored
    -- 3. if scorable, the test has been scored
    -- there is a fourth possible condition, which is the opportunity has already been reported. but do not check this

    select 1, items_archived, datescored 
	into v_ok, v_archived, v_scored
	from testopportunity
    where _key = v_oppkey 
        and datecompleted is not null;

    if (v_ok = 0 or v_ok is null) then 
		return 'test has not completed';
	end if;

    if (v_archived is null) then
	begin
        if (exists (select * from testeeresponse where _fk_testopportunity = v_oppkey and scorestatus in ('formachinescoring','waitingformachinescore'))) then
            return 'items remain to be scored';
        end if;

        if (not exists (select * from testeeresponse where _fk_testopportunity = v_oppkey --_efk_testee = v_testee and _efk_testid = v_test and opportunity = v_opp and score = -1 and isfieldtest = 0)) then
            set v_scorable = 1;
		end if;
    end;
    else 
	begin      -- this condition should never happen: a test cannot be archived if it has not been reported
        if (exists (select * from testeeresponsearchive where _fk_testopportunity = v_oppkey and scorestatus in ('formachinescoring','waitingformachinescore'))) then 
            return 'items remain to be scored';
        end if;
   
        if (not exists (select * from testeeresponsearchive where _fk_testopportunity = v_oppkey and score = -1 and isfieldtest = 0)) then
            set v_scorable = 1;
		end if;
    end;
	end if;

    if (v_scorable = 0 and v_scorebytds = 1) then
		return 'unofficial score only'; -- there are items that cannot be scored automatically
	end if;

    if (v_scorebytds = 0) then
		return 'complete: do not score';
	end if;

    return null;
        
end $$

DELIMITER ;
