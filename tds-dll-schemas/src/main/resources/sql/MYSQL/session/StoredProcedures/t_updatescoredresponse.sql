DELIMITER $$

drop procedure if exists t_updatescoredresponse $$

create procedure t_updatescoredresponse (
/*
Description: -- this is the new version for updating responses to go along with the new client-side student interface
			-- that posts responses immediately and includes the following data integrity and security checks:
			-- datecreated: the date the item was created
			-- itemkey: the key to the item (previously only needed the position)
			-- responsesequence: only a response with a sequence higher than is currently held will be posted.
			-- response times are no longer recorded here. see clientlatency table

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/26/2015		Sai V. 			--
*/
	v_oppkey varbinary(16)
  , v_session varbinary(16)
  , v_browserid varbinary(16)
  , v_itemid varchar(50)
  , v_page int
  , v_position int
  ,  v_datecreated varchar(100)
  , v_responsesequence int
  , v_score int
  , v_response text -- = null
  , v_isselected bit -- = 1
  , v_isvalid bit -- = 0
  , v_scorelatency int -- = 0
  , v_scorestatus varchar(50) -- = null
  , v_scorerationale text -- = null
)
proc: begin
     
	declare v_starttime datetime(3);	
    declare v_now datetime(3);
	declare v_opprestart int;
	declare v_status varchar(50);
    declare v_lastsequence int;
    declare v_itmkey varchar(50);
    declare v_gendate datetime(3);
    declare v_msg varchar(1000);
    declare v_isinactive bit;
    declare v_clientname varchar(100);    
    declare v_scoremark varbinary(16);
    declare v_scoringdate datetime(3);
    declare v_scoreddate datetime(3);
    declare v_thescore int;
	declare v_audit int;
    declare v_environment varchar(100);
    declare v_abortsim bit;
	declare v_error varchar(128);
    declare v_responsecount int;
	declare v_procname varchar(200);
	declare v_reason varchar(10);

	-- initialize default values
	set v_now = now(3);
	set v_procname = 't_updatescoredresponse';

    set v_abortsim = 0;
	set v_starttime = now(3);
	if v_isselected is null then set v_isselected = 1; end if; 
	if v_isvalid is null then set v_isvalid = 0; end if; 
	if v_scorelatency is null then set v_scorelatency = 0; end if; 

	-- integrity check
	call _validatetesteeaccessproc (v_oppkey, v_session, v_browserid, 1, v_error);

	if (v_error is not null) then 
	begin
-- select 1;
		call _returnerror (v_clientname, v_procname, v_error, null, v_oppkey, '_validatetesteeaccess', 'denied');
		leave proc;
	end;
	end if;

    select restart, `status`, clientname
	into v_opprestart, v_status, v_clientname
    from testopportunity
	where _key = v_oppkey;

	if (v_status not in ('started', 'review')) then begin
-- select 2;
		-- select 'denied' as [status], 'your test opportunity has been interrupted. please check with your test administrator to resume your test.' as reason, 
		-- 't_updatescoredresponse' as [context], null as [argstring], null as [delimiter];
		call _returnerror (v_clientname, v_procname,'your test opportunity has been interrupted. please check with your test administrator to resume your test.', null, v_oppkey, 't_updatescoredresponse', 'denied');
		leave proc;
	end;
	end if;

    select environment into v_environment 
	from _externs where clientname = v_clientname;

    if (v_environment = 'simulation') then
-- select 3;
        select v_abortsim = sim_abort from `session` where _key = v_session;
	end if;

    set v_audit = (select auditresponses(v_clientname));

	-- ===============================================================================================
	select responsesequence, _efk_itemkey, dategenerated, score, isinactive, scoremark, scoringdate, scoreddate
	into v_lastsequence, v_itmkey, v_gendate, v_thescore, v_isinactive, v_scoremark, v_scoringdate, v_scoreddate
    from testeeresponse 
	where _fk_testopportunity = v_oppkey and position = v_position;
       
    if (v_itmkey is null or v_itmkey <> v_itemid) then
        set v_msg = concat('the item does not exist at this position in this test opportunity:', ' position=', ltrim(cast(v_position as char(10))), '; item =', v_itemid);
    elseif (v_gendate <> v_datecreated) then -- interesting: apparently sql server allows a +/- 1 millisecond tolerance for this comparison
        set v_msg = concat('item security codes do not match:', ' position=', ltrim(str(v_position)), '; item =', v_itemid, '; date=', v_datecreated);
    elseif (v_lastsequence > v_responsesequence) then -- we are not following strict sequencing, but sequence numbers must be monotonically increaing
        set v_msg = concat('responses out of sequence:', ' position=', ltrim(str(v_position)), '; stored sequence =', ltrim(cast(v_lastsequence as char(10))), '; new sequence=', cast(v_responsesequence as char(20)));
    end if;

    if (v_msg is not null) then 
	begin
-- select 4;
        call _logdberror (v_procname, v_msg, null, null, null, v_oppkey, v_clientname, v_session);
		call _returnerror (v_clientname, v_procname, 'response update failed', null, null, null, null);
        leave proc;
    end;
	end if;
	
	-- since this procedure is used to update ancillary (non-response) data in which the response is not submitted,
	-- or responses without scores,
	-- we need some logic to know what to do about this
    if (v_response is not null and (v_score is null or v_score < 0)) then
	begin
-- select 5;
		-- new response, score to be determined
        set v_thescore = -1;         -- not scored
        set v_scoremark = unhex(REPLACE(UUID(), '-', ''));   -- app will need this token to update the record with the score for this response once it is determined asynchronously
        set v_scoringdate = v_now;    -- start the scoring clock running
        set v_scoreddate = null;     -- no score, so the date scored is undetermined
    end;
    elseif (v_response is not null and v_score >= 0) then 
	begin
-- select 6;
			-- new response together with a valid score
			set v_scoremark = null;      -- any previous scoremark is now obsolete
			set v_thescore = v_score;     -- use the provided score
			set v_scoringdate = v_now;    -- 'instantaneous' time lag
			set v_scoreddate = v_now;
    end;
	end if;

    -- else response must be null indicating a posting of ancillary data. leave everything as we found it in the database
    if (v_response is null) then  -- having removed mark and comment, is response ever null?
        update testeeresponse 
        set isselected = v_isselected
		  , isvalid = v_isvalid
		  ,  _fk_browser = v_browserid
		  , responsesequence = v_responsesequence
		where _fk_testopportunity = v_oppkey 
		and position = v_position;
	else 
	begin
        update testeeresponse 
        set isselected = v_isselected
		  , isvalid = v_isvalid
		  , _fk_responsesession = v_session
		  , _fk_browser = v_browserid
          , scoremark = v_scoremark
          , numupdates =  numupdates + 1
		  , datesubmitted = v_now
          , response = v_response 
          , responsesequence = v_responsesequence
          , responselength =length(v_response)
          , score = v_thescore
          , datefirstresponse = coalesce(datefirstresponse, v_now)
          , scorelatency = scorelatency + v_scorelatency
		  , scorestatus = v_scorestatus    -- new for fall 2010, is generated by the item scoring engine
          , scoringdate = v_scoringdate
          , scorerationale = v_scorerationale
            -- if not scored, then clear the scoreddate (end of scoring), else use v_now for synchronous scoring
          , scoreddate = v_scoreddate
        where _fk_testopportunity = v_oppkey and position = v_position and responsesequence <= v_responsesequence;
        
		if (v_audit <> 0 ) then
            insert into testeeresponseaudit(_fk_testopportunity, position, scoremark, sequence, response, sessionkey, browserkey, isselected, isvalid, score, scorelatency, scoringdate, scoreddate, _efk_item, _date)
            select v_oppkey, v_position, v_scoremark, v_responsesequence, v_response, v_session, v_browserid, v_isselected, v_isvalid, v_thescore, v_scorelatency, v_scoringdate, v_scoreddate, v_itemid, now(3);
		end if;
	end;
    end if;

    select count(*) into v_responsecount 
	from testeeresponse where _fk_testopportunity = v_oppkey and datefirstresponse is not null;

    update testopportunity 
	set numresponses = v_responsecount 
	where _key = v_oppkey;

	set v_reason = null;
-- select 7;
	-- return the responsecount so student app can coordinate with proctor app
    select 'updated' as `status`, 1 as `number`, v_reason as reason, v_scoremark as scoremark, v_responsecount as responsecount, v_abortsim as abortsim;

    call _logdblatency (v_procname, v_starttime, null, 1, v_position, v_oppkey, v_session, v_clientname, null);

end $$

DELIMITER ;