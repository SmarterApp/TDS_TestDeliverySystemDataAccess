DELIMITER $$

drop procedure if exists `setopportunitystatus` $$

create procedure `setopportunitystatus`(
/*
Description: set the test opportunity status in order to track all changes to the opportunity

VERSION 	DATE 			AUTHOR 			COMMENTS
001			01/10/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_oppkey varbinary(16)
  , v_status varchar(50)
  , v_suppressreport bit -- = 0
  , v_requestor varchar(100) -- = null
  , v_comment varchar(200) -- = null
)
sql security invoker
proc: begin

	declare v_now datetime(3);
	declare v_oldstatus varchar(50);
	declare v_datestarted datetime(3);
	declare v_msg varchar(250);
    declare v_clientname varchar(100);
    declare v_arg varchar(100);
	declare v_dbmsg text;
	declare v_procname varchar(100);
	declare v_itemcount int;

	set v_now = now(3);
	set v_procname = 'setopportunitystatus';

	select clientname, `status`, datestarted 
	into v_clientname, v_oldstatus, v_datestarted
	from testopportunity
	where _key  = v_oppkey;

    if (v_oldstatus is null) then 
		set v_oldstatus = 'undefined'; 
	end if;

	set v_msg = _canchangeoppstatus(v_oldstatus, v_status);

	if (v_msg is not null) then
	begin   
        set v_dbmsg = concat('bad status transition from ', v_oldstatus, ' to ', v_status);

		call _logdberror(v_procname, v_dbmsg, null, null, null, v_oppkey, v_clientname, null);         
		if (v_suppressreport = 0) then
			-- select 'failed' as `status`, v_msg as reason, '_canchangeoppstatus' as [context], v_oldstatus + '|' + v_status as [argstring], '|' as [delimiter]; 
			set v_arg = concat(v_oldstatus, ',', v_status);
			call _returnerror(v_clientname, v_procname, v_msg, v_arg, v_oppkey, '_canchangeoppstatus', 'failed');
			leave proc;
		end if;	
	end;
	end if;

	--  place all the special pre-processing calls here
	if (v_status = 'pending'and v_datestarted is not null) then 
	begin		
		-- don't allow return to pending status if there has been any test activity
		-- check for items administered. if any administered then set status to suspended. if not then leave as pending
		set v_itemcount = (select count(*) from testeeresponse 
							where _fk_testopportunity = v_oppkey);
		
		if (v_itemcount > 0) then set v_status = 'suspended'; end if;
	end;
	end if;

	-- ===============================================
	update testopportunity 
	set prevstatus 		= `status`
	  , datechanged 	= v_now
	  , datescored      = case v_status when 'scored' then v_now else datescored end
	  , dateapproved    = case v_status when 'approved' then v_now else dateapproved end
	  , datecompleted   = case v_status when 'completed' then v_now else datecompleted end
	  , dateexpired     = case v_status when 'expired' then v_now else dateexpired end
	  , datesubmitted   = case v_status when 'submitted' then v_now else datesubmitted end
	  , datereported    = case v_status  when 'reported' then v_now else datereported end
	  , daterescored    = case v_status when 'rescored' then v_now else daterescored end
	  , datepaused      = case -- alter datepaused only when the opportunity passed through some actual testing activity as indicated by the current status
							when v_status = 'paused' and status in ('started', 'review') then v_now else datepaused end
	  , dateinvalidated = case v_status when 'invalidated' then v_now else dateinvalidated end
	  , invalidatedby   = case v_status when 'invalidated' then v_requestor else invalidatedby end
	  , xmlhost         = case v_status when 'submitted' then @@hostname else xmlhost end
	  , waitingforsegment = case -- proctor updates segment entry/exit request
								when v_status in ('approved', 'denied') and status in ('segmententry', 'segmentexit') then null 
								else waitingforsegment end
	  , `comment`		= case when v_comment is not null then v_comment else comment end
	  , `status` 		= v_status
	where _key = v_oppkey;

	-- place all the special post-processing calls here
	if (v_status = 'completed') then call _onstatus_completed(v_oppkey); end if;
    if (v_status = 'scored') then call _onstatus_scored(v_oppkey); end if;
    if (v_status = 'paused') then call _onstatus_paused(v_oppkey, v_oldstatus); end if;

	-- ====================

    insert into archive.opportunityaudit (_fk_testopportunity, dateaccessed, _fk_session, accesstype, hostname, _fk_browser, actor, `comment`)
    select v_oppkey, now(3), _fk_session, v_status, @@hostname, _fk_browser, v_requestor, v_comment
    from testopportunity 
	where _key = v_oppkey;

	if (v_suppressreport = 0) then 
		select v_status as `status`, cast(null as char) reason, cast(null as char) as `context`, cast(null as char) as `argstring`, '|' as `delimiter`;
	end if;

	call _logdblatency(v_procname, v_now, null, null, null, v_oppkey, null, v_clientname, null);
    	
end $$

DELIMITER ;