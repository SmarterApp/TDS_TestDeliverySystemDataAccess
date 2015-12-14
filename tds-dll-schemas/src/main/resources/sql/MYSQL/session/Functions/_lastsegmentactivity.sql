DELIMITER $$

drop function if exists `_lastsegmentactivity` $$

create function `_lastsegmentactivity`(
/*
DESCRIPTION: determine the datetime of the last overt activity on the part of the testee

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/10/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_oppkey varbinary(16)
  , v_segmentposition int
)
returns datetime(3)
sql security invoker
begin

	declare v_fromtime datetime(3);

	drop temporary table if exists tmp_tblfromtimes; 
	create temporary table tmp_tblfromtimes(lasttime datetime(3))engine = memory;	

    insert into tmp_tblfromtimes (lasttime)
	select max(datesubmitted) from testeeresponse
	where _fk_testopportunity = v_oppkey and segment = v_segmentposition and datesubmitted is not null;

	insert into tmp_tblfromtimes (lasttime)
	select max(dategenerated) from testeeresponse
	where _fk_testopportunity = v_oppkey and segment = v_segmentposition and dategenerated is not null;

	set v_fromtime = (select max(lasttime) from tmp_tblfromtimes);

	-- clean-up
	drop temporary table tmp_tblfromtimes;

	return v_fromtime;

end $$

DELIMITER ;