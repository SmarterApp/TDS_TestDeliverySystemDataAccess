DELIMITER $$

drop procedure if exists `a_alteropportunityexpiration` $$

create procedure `a_alteropportunityexpiration`(
/*
Description: -- alter an opportunity expiration date by positive (to extend) or negative (to contract) days from the original start date

VERSION 	DATE 			AUTHOR 			COMMENTS
001			04/01/2015		Sai V. 			Code ported from SQL Server to MySQL
*/
    v_oppkey 	varbinary(16)
  , v_requestor varchar(50)
  , v_dayincrement int 
  , v_reason nvarchar(255)
)
sql security invoker 
proc: begin

    declare v_startdate datetime(3);
    declare v_expirefrom datetime(3);
    declare v_expiredate datetime(3);
    declare v_expiredays int;
    declare v_today datetime(3);
    declare v_test varchar(150);
    declare v_testkey varchar(250);
	declare v_testee bigint;
	declare v_opp int;
    declare v_maxopp int;
    declare v_deleted datetime(3);
    declare v_msg varchar(200);
	declare v_status, v_clientname, v_procname varchar(100);

    set v_today = now(3);
	set v_procname = 'a_alteropportunityexpiration';

    select _efk_adminsubject, _efk_testee, _efk_testid, opportunity, datestarted, `status`, datedeleted, clientname
	into v_testkey, v_testee, v_test, v_opp, v_startdate, v_status, v_deleted, v_clientname
    from testopportunity 
	where _key = v_oppkey;

    set v_maxopp = (select max(opportunity) from testopportunity
					 where _efk_testee = v_testee and _efk_adminsubject = v_testkey and datedeleted is null);

    set v_expirefrom = date_add(v_startdate, interval v_dayincrement day);
    set v_expiredate = date_add(v_expirefrom, interval clienttestexpiresin(v_test, v_clientname) day);
    set v_expiredays = datediff(v_expiredate, v_today);


    set v_msg = case when v_status in ('completed', 'submitted', 'scored', 'reported', 'invalidated')
					 then concat('opportunity status (', v_status, ') prevents expiration date alterations')   
					 when v_deleted is not null
					 then 'opportunity has been deleted'
					 when v_startdate is null
					 then 'expiration date cannot be altered for opportunities that have not yet started.'
					 when v_maxopp > v_opp
					 then 'this is not the latest test opportunity for this testee/test'
					 when v_status = 'expired' and v_expiredays < 0
					 then 'days would not alter opportunity status (expired)'
				end;

    if (v_msg is not null) then
		call _returnerror(v_clientname, v_procname, v_msg, null, v_oppkey, null, 'failed');
		leave proc;
    end if;

    if (v_expiredays >= 0 and v_status = 'expired') then 
		set v_status = 'paused';
    elseif (v_expiredays < 0) then 
		set v_status = 'expired';
	end if;
    -- else leave the status as is (may be a test in progress)

    if (auditopportunities(v_clientname) <> 0) then
		insert into archive.opportunityaudit (_fk_testopportunity, dateaccessed, _fk_session, accesstype, hostname, _fk_browser, actor, `comment`)
		select _key, now(3), _fk_session, v_status, @@hostname, _fk_browser, v_requestor, concat('opportunity changed by admin proc: ', v_procname, '. Reason: ', v_reason)
		from testopportunity 
		where _key = v_oppkey; 
	end if;
    
    update testopportunity 
	set `status` = v_status
	  , expirefrom = v_expirefrom
	  , dateexpired = case v_status when 'expired' then v_today else null end
    where _key = v_oppkey;    
    
    select 'success' as status, v_status as `opportunity status`, v_startdate as datestarted, v_expirefrom as expiresfrom, v_expiredate as expiredate;

end $$

DELIMITER ;
