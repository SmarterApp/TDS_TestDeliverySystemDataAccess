DELIMITER $$

drop procedure if exists _openexistingopportunity $$

create procedure _openexistingopportunity (
/*
Description: open an existing test opportunity for testing. main purpose of this function is to apply business rules.

Assumptions: the given opportunity exists for opening (and has not expired), as verified by _canopentestopportunity
			-- the session is open for business, as verified by _canopentestopportunity

VERSION 	DATE 			AUTHOR 			COMMENTS
001			2/10/2015		Sai V.			code migration 		
*/
    v_clientname varchar(100)
  , v_testee bigint
  , v_testkey varchar(250)
  , v_opportunity int
  , v_sessionid varbinary(16)
  , v_browserid varbinary(16)
  , out v_newstatus varchar(50) 
  , v_accommodations text -- = null
  , v_restorerts bit
  , out v_testoppkey varbinary(16)
  , v_debug bit -- = 0
)
sql security invoker 
proc: begin

	declare v_today datetime(3);
	declare v_slotcount int;		-- how many item slots are there?
	declare v_laststatus varchar(50);
	declare v_isabnormal int;
	declare v_audit int;
    declare v_isstarted bit;
	declare v_error varchar(1000);

	set v_today = now(3);

    -- clienttestoppkey will only return non-deleted testopps
    set v_testoppkey = (select _key from testopportunity 
						 where clientname = v_clientname and _efk_testee = v_testee and _efk_adminsubject = v_testkey and opportunity = v_opportunity and datedeleted is null);

    set v_audit = auditopportunities(v_clientname);

	
	-- pick up info on the most recent opportunity
	set v_laststatus = (select `status`
						  from testopportunity where _key = v_testoppkey); 

	-- determine if the test has been started in fact (items administered)
	set v_slotcount = (select count(*) from testeeresponse
						where _fk_testopportunity = v_testoppkey); 
	
	if (v_slotcount > 0) then
		set v_newstatus = 'suspended';	-- indicates an opportunity in which items have been administered
	else 
		set v_newstatus = 'pending';	-- indicates a new opportunity for all intents and purposes		
	end if;

	-- determine if this is an 'abnormal restart' as indicated by an opportunity which appears to be in use
	-- this is for auditing purposes only
	if (v_laststatus in (select `status` from statuscodes where `usage` = 'opportunity' and stage = 'inuse')) then
		set v_isabnormal = 1;
	else 
		set v_isabnormal = 0;
	end if;

	-- open the opportunity
	update testopportunity 
	set _fk_browser = v_browserid
	  , datechanged = v_today
	  , prevstatus = `status`
	  , `status` = v_newstatus
	  , abnormalstarts = abnormalstarts + v_isabnormal
	  , waitingforsegment = case when insegment is null then 1 else insegment end
	  , datestarted = (case when v_slotcount = 0 then null else datestarted end)
	where _key = v_testoppkey;  
    

    -- there should be accommodations already, but database distress errors have been known to fail here
    -- so this should self-repair the data
    -- with segments, we only want to burden the proctor with setting accoms on restart when there is an actual choice required.
    -- it also provides an opportunity to correct a mistake.
    set v_isstarted = case v_newstatus when 'pending' then 0 else 1 end;

    if (not (exists (select * from testeeaccommodations where _fk_testopportunity = v_testoppkey))) then
		if (v_debug > 0) then select '_initopportunityaccommodations', hex(v_testoppkey), v_accommodations; end if;
        call _initopportunityaccommodations(v_testoppkey, v_accommodations);
    elseif (v_accommodations is not null and length(v_accommodations) > 0) then    
		if (v_debug > 0) then select '_updateopportunityaccommodations', hex(v_testoppkey), v_accommodations, v_isstarted, v_restorerts; end if;
        call _updateopportunityaccommodations(v_testoppkey, 0, v_accommodations, v_isstarted, 0, v_restorerts, v_error /*output*/, 0);
		if (v_debug > 0) then select '_updateopportunityaccommodations', v_error; end if;
    end if;

    -- do this in case the valuecount on an accommodation has changed since the test started
    update testeeaccommodations, 
			( -- ClientTestAccommodations (@clientname, @testkey) -- first, get all tds tools directly related to this client's tests, 0 represents the global test scope, while segment positions are local segment score
				select 0 as segment, `type` as acctype, `value` as accvalue, `code` as acccode, allowcombine, isdefault,  allowchange, isselectable, isvisible, studentcontrol
					 , (select count(*) from configs.client_testtool tool 
						 where tool.contexttype = 'test' and tool.`context` = v_testkey  and tool.clientname = v_clientname and tool.type = tt.type) as valcount
					 , dependsontooltype
				from configs.client_testtooltype ttype, configs.client_testtool tt
				where ttype.contexttype = 'test' and ttype.`context` = v_testkey and ttype.clientname = v_clientname
					and tt.contexttype = 'test' and tt.`context` = v_testkey and tt.clientname = v_clientname and tt.type = ttype.toolname
				-- get all the segment-specific accommodations
				union 
				select segmentposition as segment, `type` as acctype, `value` as accvalue, `code` as acccode, allowcombine, isdefault,  allowchange, isselectable, isvisible, studentcontrol
					 , (select count(*) from configs.client_testtool tool 
						 where tool.contexttype = 'test' and tool.`context` = v_testkey and tool.clientname = v_clientname and tool.`type` = tt.`type`) as valcount
					 , null  -- dependsontooltype
				from configs.client_testtooltype ttype, configs.client_testtool tt, configs.client_segmentproperties
				where parenttest = v_testkey
					and ttype.contexttype = 'segment' and ttype.`context` = segmentid and ttype.clientname = v_clientname
					and tt.contexttype = 'segment' and tt.`context` = segmentid and tt.clientname = v_clientname and tt.`type` = ttype.toolname
				-- now get all test tools that have 'wild card' (i.e. '*') assignments (segments never have wildcard assignments)
				union
				select 0, `type`, `value`, `code`, allowcombine, isdefault, allowchange, isselectable, isvisible, studentcontrol
					 , (select count(*) from configs.client_testtool tool 
						 where tool.contexttype = 'test' and tool.`context` = '*' and tool.clientname = v_clientname and tool.`type` = tt.`type`) as valcount
					 , dependsontooltype
				from  configs.client_testtooltype ttype, configs.client_testtool tt
				where ttype.contexttype = 'test' and ttype.`context` = '*' and ttype.clientname = v_clientname
					and tt.contexttype = 'test' and tt.`context` = '*' and tt.clientname = v_clientname and tt.`type` = ttype.toolname 
					and not exists (select * from configs.client_testtooltype tool 
								     where tool.contexttype = 'test' and tool.`context` = v_testkey and tool.toolname = ttype.toolname and tool.clientname = v_clientname)  
				) a
	set valuecount = valcount
    where _fk_testopportunity = v_testoppkey and testeeaccommodations.segment = a.segment and testeeaccommodations.acccode = a.acccode;

    
    update testeeaccommodations 
	set isapproved = 0
    where _fk_testopportunity = v_testoppkey and valuecount > 1 and isselectable = 1 and allowchange = 1;

	-- record the audit trail
    insert into archive.opportunityaudit(_fk_testopportunity, dateaccessed, _fk_session, hostname, _fk_browser, accesstype, isabnormal)
		 values (coalesce(v_testoppkey, ''), now(3), v_sessionid, @@hostname, v_browserid, v_newstatus, v_isabnormal);

    call _logdblatency('_openexistingopportunity', v_today, v_testee, null, null, v_testoppkey, null, null, null);
    
end $$

DELIMITER ;
