DELIMITER $$

drop procedure if exists `_getrtsattribute` $$

create procedure `_getrtsattribute`(
/*
Description: retreive student data

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/11/2015		Sai V. 			--
*/
	v_clientname varchar(100)
  , v_testee bigint
  , v_attname varchar(100)
  , out v_attvalue text /*output*/
  , v_entitytype varchar(100) -- = 'student'
  , v_debug bit -- = 0
)
sql security invoker
proc: begin

	declare v_procname varchar(100);
	declare v_starttime datetime(3);
	set v_starttime = now(3);
	set v_procname = '_getrtsattribute';

	set	v_attvalue = (select d.attrvalue
						from r_studentpackage sp, r_studentpackagedetails d
						where sp._key = d._fk_studentpackagekey
							and sp.studentkey = v_testee  and sp.clientname = v_clientname and sp.iscurrent = 1
							and d.studentkey = v_testee and d.attrname = v_attname);
					
    if (v_debug = 1) then select 'v_attvalue:', v_attvalue; end if;
    
call _logdblatency(v_procname, v_starttime, null, null, null, null, null, null, null);

end $$

DELIMITER ;
