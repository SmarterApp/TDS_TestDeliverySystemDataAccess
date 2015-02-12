DELIMITER $$

drop function if exists `_isvalidstatustransition` $$

create function `_isvalidstatustransition`(
/*
DESCRIPTION: this function defines all legal transitions outside of a test reset event. resetting a test is done outside of the purview of normal state transition activity

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/28/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_oldstatus varchar(50)
  , v_newstatus varchar(50)
)
returns bit
begin

	declare v_okay bit;

    set v_okay = (
		case v_oldstatus
			when 'pending' then case when v_newstatus in ('initializing','pending','denied','approved','paused','expired','invalidated', 'forcecompleted') then 1 else 0 end
			when 'suspended' then case when v_newstatus in ('suspended','denied','approved','paused','expired','invalidated', 'forcecompleted') then 1 else 0 end
			when 'started' then case when v_newstatus in ('started','paused','review','completed','expired','invalidated', 'segmententry', 'segmentexit', 'forcecompleted') then 1 else 0 end
			when 'approved' then case when v_newstatus in ('approved','pending','started','paused','expired','invalidated', 'forcecompleted') then 1 else 0 end
			when 'review' then case when v_newstatus in ('review','completed','paused','expired','invalidated', 'forcecompleted', 'segmententry') then 1 else 0 end
			when 'paused' then case when v_newstatus in ('paused','pending','suspended','expired','invalidated', 'forcecompleted') then 1 else 0 end
			when 'denied' then case when v_newstatus in ('denied','pending','suspended','paused','expired','invalidated', 'forcecompleted') then 1 else 0 end
			when 'completed' then case when v_newstatus in ('completed','scored','submitted','invalidated') then 1 else 0 end
			when 'scored' then case when v_newstatus in ('rescored','submitted','invalidated') then 1 else 0 end
			when 'submitted' then case when v_newstatus in ('rescored','reported','invalidated') then 1 else 0 end
			when 'reported' then case when v_newstatus in ('rescored','invalidated') then 1 else 0 end
			when 'expired' then case when v_newstatus in ('rescored','invalidated') then 1 else 0 end
			when 'invalidated' then case when v_newstatus in ('rescored', 'invalidated') then 1 else 0 end
			when 'rescored' then case when v_newstatus in ('scored') then 1 else 0 end       -- see a_acceptrescore where the status transitions immediately to scored
			when 'segmententry' then case when v_newstatus in ('approved', 'denied', 'expired', 'invalidated', 'forcecompleted') then 1 else 0 end
			when 'segmentexit' then case when v_newstatus in ('approved', 'denied', 'expired', 'invalidated', 'forcecompleted') then 1 else 0 end
			when 'forcecompleted' then case when v_newstatus in ('submitted', 'scored') then 1 else 0 end
			when 'initializing' then case when v_newstatus in ('initializing','pending','denied','approved','paused','expired','invalidated', 'forcecompleted') then 1 else 0 end
			else 0
		end);

    return v_okay;

end $$

DELIMITER ;