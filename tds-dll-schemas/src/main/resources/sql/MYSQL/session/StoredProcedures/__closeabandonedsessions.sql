DELIMITER $$

drop procedure if exists `__closeabandonedsessions` $$

create procedure `__closeabandonedsessions`(
/*
Description: -- procedure periodically called from event scheduler  to close abondoned sesssions

VERSION 	DATE 			AUTHOR 			COMMENTS
001			06/09/2015		Elena 			Code ported from SQL Server to MySQL
*/
)
sql security invoker 
proc: begin
	
    declare v_now datetime(3);
    declare v_key varbinary(16);
    declare v_proctor bigint;
    declare v_browser varbinary(16);
	
	set v_now = now(3);
	
	select   _key,  _efk_proctor,  _fk_browser 
	into v_key, v_proctor, v_browser
    from session S, timelimits T
    where status <> 'closed' and dateend > v_now 
        and S.clientname = T.clientname and tacheckintime is not null and tacheckintime > 0
        and date_add(datevisited, interval tacheckintime minute) < v_now
   --     and dateadd(minute, TACheckInTime, DateVisited) < @now
		and S._efk_proctor is not null -- this is null for guest sessions
		and S._fk_browser is not null
  --  order by dateadd(minute, TACheckInTime, DateVisited) limit 1;
    order by date_add(datevisited, interval tacheckintime minute) limit 1;
    
    while (v_key is not null) do 
    begin
        call p_pausesession( v_key, v_proctor, v_browser, 'administratively closed', 0);

        set v_key = null;
        
        select   _key,  _efk_proctor,  _fk_browser 
	    into v_key, v_proctor, v_browser
        from session S, timelimits T
        where status <> 'closed' and dateend > v_now 
            and S.clientname = T.clientname and tacheckintime is not null and tacheckintime > 0
            and date_add(datevisited, interval tacheckintime minute) < v_now
		    and S._efk_proctor is not null -- this is null for guest sessions
		    and S._fk_browser is not null
       order by date_add(datevisited, interval tacheckintime minute) limit 1;
       
	end;    
    end while;
	
    call _logdblatency('__closeabandonedsessions', v_now, null,null, null, null, null, null, null);

end $$

DELIMITER ;
