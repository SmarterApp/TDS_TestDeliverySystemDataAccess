DELIMITER $$

drop procedure if exists `_onstatus_paused` $$

create procedure `_onstatus_paused`(
/*
Description:

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/10/2015		Sai V. 			Code Migration
*/
	v_oppkey varbinary(16)
  , v_prevstatus varchar(50)
)
sql security invoker
proc: begin

    if (v_prevstatus not in ('started', 'review')) then 
		leave proc;
	end if;

	-- restore ispermeable flags
    if (exists (select * from testopportunitysegment where _fk_testopportunity = v_oppkey and ispermeable > -1 and restorepermon <> 'completed')) then
        update testopportunitysegment 
		set ispermeable = -1
		  , restorepermon = null
        where _fk_testopportunity = v_oppkey and ispermeable > -1 and restorepermon in ('segment', 'paused');

        insert into archive.opportunityaudit(_fk_testopportunity, dateaccessed, accesstype, _fk_session, hostname, _fk_browser)
        select v_oppkey, now(3), 'restore segment permeability', _fk_session, @@hostname, _fk_browser
        from testopportunity 
		where _key = v_oppkey;
    end if;

end $$

DELIMITER ;
