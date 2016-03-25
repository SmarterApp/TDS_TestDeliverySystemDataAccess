DELIMITER $$

drop procedure if exists `_initializeopportunity` $$

create procedure `_initializeopportunity`(
/*
Description: all code for initializing a new test opportunity is here, including invoking field test item generation if called for.
			-- the final test length is determined in this procedure as a result of whether or not field test items are generated
			-- this procedure assumes the testee has been cleared to start a test.

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/06/2015		Sai V. 			Code Migration
*/
	v_oppkey varbinary(16)
  , out v_testlength int
  , out v_reason text
  , v_formkeylist text
)
sql security invoker
proc: begin
    
	declare v_today 		datetime(3);
    declare v_ability 		float;
    declare v_testkey 		varchar(200);
	declare v_procname 		varchar(100);

	set v_today = now(3);
    set v_reason = null;
	set v_procname = '_initializeopportunity';

    call _initializetestsegments(v_oppkey, v_reason /*output*/, v_formkeylist, 0);

    if (v_reason is not null) then
        call _logdberror(v_procname, v_reason, null, null, null, v_oppkey, null, null);
        leave proc;
    end if;

	call _getinitialability(v_oppkey, 0, v_ability /*output*/);

    insert into testoppabilityestimate (_fk_testopportunity, strand, estimate,  itempos)
		 values (v_oppkey, 'overall', v_ability, 0);

    insert into testoppabilityestimate (_fk_testopportunity, strand, estimate, itempos)
    select v_oppkey, _fk_strand, v_ability, 0
    from itembank.tbladminstrand s, testopportunity o
    where o._key = v_oppkey and o._efk_adminsubject = s._fk_adminsubject and s.startability is not null;

    set v_testlength = (select sum(opitemcnt) + sum(ftitemcnt)
						  from testopportunitysegment where _fk_testopportunity = v_oppkey);

	-- now create the set of records where all item responses will go
	call _createresponseset(v_oppkey, v_testlength, 0);	

	-- update the test opportunity with information generated here
	update testopportunity 
	set prevstatus = `status`
	  , `status` = 'started'
	  , datestarted = v_today
	  , expirefrom = v_today
	  , stage = 'inprogress'
	  , datechanged = v_today
	  , maxitems = v_testlength
	  , waitingforsegment = null
	where _key = v_oppkey; 
   
    call _logdblatency(v_procname, v_today, null, 1, null, v_oppkey, null, null, null);

end $$

DELIMITER ;
