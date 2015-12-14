DELIMITER $$

drop procedure if exists t_istestcomplete $$

create procedure t_istestcomplete (
/*
Description: -- have all required items for each segment been selected
			-- note: this does not imply that the test is complete (all required items answered) 
			-- or even that every selected item has been administered (i.e. prefetch)
			-- what it does mean is that the testeeresponse table has every item that will ever be administered to the student on this test.

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/29/2015		Sai V. 			Converted code from T-SQL to MySQL
*/
	v_oppkey varbinary(16)
)
begin

    declare v_starttime datetime(3);
	declare v_incomplete int;
	declare v_iscomplete bit;

	set v_starttime = now(3);
   
	set v_incomplete = (select count(*) from testopportunitysegment where _fk_testopportunity = v_oppkey and issatisfied = 0);
	set v_iscomplete = (case when v_incomplete > 0 then 0 else 1 end);

	select v_iscomplete as iscomplete, v_incomplete as segmentsincomplete;

	call _logdblatency('t_istestcomplete', v_starttime, null, 1, null, v_oppkey, null, null, null);

end $$

DELIMITER ;