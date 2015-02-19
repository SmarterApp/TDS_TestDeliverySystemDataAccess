DELIMITER $$

drop procedure if exists t_insertitems $$

create procedure t_insertitems (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/29/2015		Sai V. 			Converted code from T-SQL to MySQL
*/
	v_oppkey 	varbinary(16)
  ,	v_session 	varbinary(16)
  , v_browserid varbinary(16)
  , v_segment 	int
  , v_segmentid varchar(100)
  , v_page 		int
  , v_groupid 	varchar(50)
  , v_itemkeys 	text
  , v_delimiter char -- = ','
  , v_groupitemsrequired int -- = -1
  , v_groupb 	float -- = null    -- this is needed for simulation reports
  , v_debug 	int -- = 0
  , v_noinsert 	bit -- = 0
)								
proc: begin

 	declare v_hostname 		nchar(50);
	declare v_error 		varchar(128);
	declare v_procname		varchar(30) default 't_insertitems';
	declare v_testee 		bigint;
	declare v_today 		datetime(3);
	declare v_status 		varchar(50);
    declare v_clientname 	varchar(100);
	declare v_environment 	varchar(100);
    declare v_language 		varchar(20);
    declare v_item 			varchar(50);
    declare v_testkey 		varchar(250);
    declare v_msg 			varchar(1000);
    declare v_segmentkey 	varchar(250);
    declare v_formkey 		varchar(50);
    declare v_algorithm 	varchar(100);
    declare v_argstring 	varchar(1000);
-- 	declare v_trace 		varchar(200);
	declare v_errmsg 		varchar(200);
    declare v_lastposition 	int;      -- last position of existing items
	declare v_relativepositionflag bit default 0;
    declare v_count, v_opprestart, v_lastpos, v_minpos, v_maxpos, v_insertcnt, v_lastsegment, v_lastpage, v_formstart, v_itemcnt, v_minftpos int;

	declare v_starttime datetime(3);
	
	declare exit handler for sqlexception
	begin
		rollback;

		set v_errmsg = 'mysql exit handler: item insertion failed:';
		call _logdberror(v_procname, v_errmsg, null, null, null, v_oppkey, v_clientname, v_session);
		call _returnerror(v_clientname, v_procname, 'mysql exit handler: database record insertion failed for new test items', null, v_oppkey, null, null);
	end;

	set v_today = now(3);
	set v_starttime = now(3);
	set v_hostname  = @@hostname;

	-- check if input parameters that MUST contain values are populated, if not set default values here
	if (v_delimiter is null) then set v_delimiter = ','; end if;
	if (v_groupitemsrequired is null) then set v_groupitemsrequired = -1; end if;

	call _validatetesteeaccessproc(v_oppkey, v_session, v_browserid, 0, v_error /*output*/);

	if (v_error is not null) then
		-- select 'denied' as [status], v_error as reason, '_validatetesteeaccess' as [context], null as [argstring], null as [delimiter];
        call _returnerror(null, v_procname, v_error, null, v_oppkey, '_validatetesteeaccesss', 'denied');
		leave proc;
	end if;
    
	drop temporary table if exists tmp_tblitems;
    create temporary table tmp_tblitems(
		p 		int
	  , itemkey varchar(50)
	) engine = memory;

    if (v_itemkeys is not null) then
	begin
		/* Call _buildtable stored procedure 
		-- To capture and use result set from _buildtable, a temporary table is created to store the resultset */
		drop temporary table if exists tblout_buildtable;
		create temporary table tblout_buildtable(idx int, record varchar(255)) engine = memory;
			  
		call _buildtable(v_itemkeys, v_delimiter);

        insert into tmp_tblitems (p, itemkey)
        select idx, record
		from tblout_buildtable;
		/* # */
	end;
	end if;

	select clientname, _efk_adminsubject, restart, `status`, environment, _efk_testee
	into v_clientname, v_testkey, v_opprestart, v_status, v_environment, v_testee
    from testopportunity 
	where _key = v_oppkey;

--     if (v_environment <> 'production') then
--         set v_trace = concat('tracing ', v_groupid, ':', v_itemkeys);
--     end if;

	if (v_status not in ('started')) then
	begin
		-- select 'denied' as [status], 'your test opportunity has been interrupted. please check with your test administrator to resume your test.' as reason, 't_insertitems_2009' as [context], null as [argstring], null as [delimiter];
		if v_debug = 1 then select 1; end if;
        call _returnerror(v_clientname, v_procname,'your test opportunity has been interrupted. please check with your test administrator to resume your test.',null, v_oppkey, 't_insertitems_2009', 'denied');
		leave proc;
	end;
	end if;

    select _efk_segment, formkey, `algorithm`
	into v_segmentkey, v_formkey, v_algorithm
    from testopportunitysegment 
	where _fk_testopportunity = v_oppkey and segmentposition = v_segment;

    if (v_segmentkey is null) then 
	begin
        set v_argstring = ltrim(cast(v_segment as char(10)));
        set v_msg = concat('unknown test segment ', v_argstring);
		
		if v_debug = 1 then select 2; end if;
        call _logdberror(v_procname, v_msg, null, null, null, v_oppkey, v_clientname, v_session);
        call _returnerror(v_clientname, v_procname, 'unknown test segment', null, v_oppkey, null, null);
        leave proc;
    end;
	end if;

    call _validateiteminsert(v_oppkey, v_page, v_segment, v_segmentid, v_groupid, v_msg /*output*/);

    if (v_msg is not null) then 
	begin
		if v_debug = 1 then select 3; end if;
        call _logdberror(v_procname, v_msg, null, null, null, v_oppkey, v_clientname, v_session);
        call _returnerror(v_clientname, v_procname, 'database record insertion failed for new test items', null, v_oppkey, null, null);
        leave proc;
    end;
	end if;

    set v_language = (select acccode 
						from testeeaccommodations where _fk_testopportunity = v_oppkey and acctype = 'language');

    set v_lastposition = (select max(position)
							from testeeresponse where _fk_testopportunity = v_oppkey and _efk_itsitem is not null);

    select segment, `page`
	into v_lastsegment, v_lastpage
    from testeeresponse 
	where _fk_testopportunity = v_oppkey and position = v_lastposition;

	insert into archive._dblatency (duration, starttime, procname, _fk_testopportunity, _fk_session, clientname, `comment`) 
		 values (TIMESTAMPDIFF(microsecond, v_starttime, now(3))/1000, v_today, v_procname, v_oppkey, v_session, v_clientname, 'phase 1');
	set v_starttime = now(3);

	-- get item data from the itembank, filtering by the items that were chosen by the selection algorithm (some may have been excluded)
	drop temporary table if exists tmp_tblinserts;
    create temporary table tmp_tblinserts(
		bankitemkey varchar(50)
	  , relativeposition int
	  , formposition int
	  , position int
	  , answer varchar(50)
	  , b float
	  , bankkey bigint
	  , _efk_itsitem bigint
	  , scorepoint int
	  , contentlevel varchar(200)
	  , `format` varchar(50)
	  , isfieldtest bit
	  , isrequired bit
	) engine = memory;

	insert into tmp_tblinserts(bankitemkey, relativeposition, bankkey, _efk_itsitem, b, scorepoint, `format`, isfieldtest, isrequired, contentlevel, formposition, answer) 
    select a._fk_item 		as bankitemkey
		 , itemposition
		 , _efk_itembank 	as bankkey
		 , _efk_item 		as itemkey
		 , irt_b
		 , scorepoint
		 , itemtype
		 , isfieldtest
		 , isrequired
		 , _fk_strand 		as contentlevel
		 , (select formposition from itembank.testformitem f where f._fk_item = a._fk_item and _fk_testform = v_formkey and f._fk_adminsubject = v_segmentkey) as formposition
		 , answer 			as answerkey
    -- from dbo.itembank_testitemgroupdata(v_segmentkey, v_groupid, v_language, v_formkey) 
    from itembank.tblsetofadminitems a, itembank.tblitem i, itembank.tblitemprops p
    where a._fk_adminsubject = v_segmentkey and a.groupid = v_groupid and a._fk_item = i._key 
        and p._fk_adminsubject = v_segmentkey and p._fk_item = a._fk_item and p.propname = 'language' and p.propvalue = v_language
    order by itemposition;
                    
	if (v_itemkeys is not null) then
        delete 
		from tmp_tblinserts 
		where bankitemkey not in (select itemkey from tmp_tblitems);
	end if;

    if (v_algorithm = 'fixedform' and exists (select * from tmp_tblinserts where formposition is null)) then
	begin
        set v_msg = concat('item(s) not on form: ', v_groupid, '; items: ', v_itemkeys);

		if v_debug = 1 then select 4; end if;        
		call _logdberror(v_procname, v_msg, null, null, null, v_oppkey, v_clientname, v_session);
		call _returnerror(v_clientname, v_procname, 'database record insertion failed for new test items', null, v_oppkey, null, null);
        leave proc;
    end;
	end if;

    if (not exists (select * from tmp_tblinserts)) then 
	begin
        set v_msg = concat('item group does not exist: ', coalesce(v_groupid, ''), '; items: ', coalesce(v_itemkeys, ''));

		if v_debug = 1 then select 5; end if;
		call _logdberror(v_procname, v_msg, null, null, null, v_oppkey, v_clientname, v_session);
		call _returnerror(v_clientname, v_procname, 'database record insertion failed for new test items', null, v_oppkey, null, null);
        leave proc;
    end;
	end if;

    
	-- now position the items according to where we are in this test opportunity, using their relative position in the itembank
	-- reposition the items remaining in the group according to the form position if this is a fixed form segment
    if (v_algorithm = 'fixedform') then 
	begin    
        -- note: for fixed form, position items according to their formposition, not their group position
        set v_formstart = (select min(formposition) from tmp_tblinserts);

        update tmp_tblinserts 
		set relativeposition = formposition - v_formstart;
    end;
	end if;

	if exists (select * from tmp_tblinserts where relativeposition is null) then
		set v_relativepositionflag = 1;
	end if;
	
    if (exists (select * from tmp_tblinserts group by relativeposition having count(*) > 1) or v_relativepositionflag = 1) then
	begin
        set v_msg = concat('ambiguous item positions in item group ', v_groupid);
	
		if v_debug = 1 then select 6; end if;	
		call _logdberror(v_procname, v_msg, null, null, null, v_oppkey, v_clientname, v_session);
		call _returnerror(v_clientname, v_procname, 'database record insertion failed for new test items', null, v_oppkey, null, null);
        leave proc;
    end;
	end if;
    
    set v_lastpos = v_lastposition;

    if (v_lastpos is null) then
		set v_lastpos = 0;
	end if;

    while (exists (select * from tmp_tblinserts where position is null)) do
	begin
        set v_minpos = (select min(relativeposition) from tmp_tblinserts where position is null);
        
		update tmp_tblinserts 
		set position = v_lastpos + 1 
		where relativeposition = v_minpos;
        
		set v_lastpos = v_lastpos + 1;
    end;
	end while;
    
    set v_count = (select count(*) from tmp_tblinserts);

    if (v_debug = 1) then
		select * from tmp_tblinserts; 
	end if;
	
    if (exists (select * from testeeresponse, tmp_tblinserts where _fk_testopportunity = v_oppkey and _efk_itemkey = bankitemkey)) then
	begin
        set v_msg = concat('attempt to duplicate existing item: ', v_itemkeys);

		if v_debug = 1 then select 7; end if;
		call _logdberror(v_procname, v_msg, null, null, null, v_oppkey, v_clientname, v_session);
		call _returnerror(v_clientname, v_procname, 'database record insertion failed for new test items', null, v_oppkey, null, null);
        leave proc;
    end;
	end if;

    if (v_noinsert = 1) then leave proc; end if;

	insert into archive._dblatency (duration, starttime, procname, _fk_testopportunity, _fk_session, clientname, `comment`) 
		 values (TIMESTAMPDIFF(microsecond, v_starttime, now(3))/1000, v_today, v_procname, v_oppkey, v_session, v_clientname, 'phase 2: before transaction');
	set v_starttime = now(3);

	start transaction;

		-- prepare to either insert, update, or combination of the two depending on whether we have the records preloaded into testeeresponse table
		insert into testeeresponse (_fk_testopportunity, position)
		select v_oppkey, r.position
		from tmp_tblinserts r
		where not exists (select * from testeeresponse where _fk_testopportunity = v_oppkey and position = r.position);
    
		-- make sure the page has not been used, and that the items have not already been administered
		if (not exists (select * from testeeresponse t, tmp_tblinserts r 
						  where t._fk_testopportunity = v_oppkey and (t.`page` = v_page or (t._efk_itsbank = r.bankkey and t._efk_itsitem = r._efk_itsitem)))) 
		then
			update testeeresponse t, tmp_tblinserts r 
			set t.isrequired 		= r.isrequired
			  , t._efk_itsitem 		= r._efk_itsitem
			  , _efk_itsbank 		= r.bankkey
			  , response 			= null
			  ,	opportunityrestart 	= v_opprestart
			  , t.`page` 			= v_page
			  ,	t.answer 			= r.answer
			  , t.scorepoint 		= r.scorepoint
			  , dategenerated 		= v_today
			  , _fk_session 		= v_session
			  , t.`format` 			= r.`format`
			  , t.isfieldtest 		= r.isfieldtest
			  , hostname 			= v_hostname
			  , groupid 			= v_groupid
			  , groupitemsrequired 	= v_groupitemsrequired
			  , _efk_itemkey 		= r.bankitemkey
			  , segment 			= v_segment
			  , segmentid 			= v_segmentid
			  , groupb 				= v_groupb
			  , itemb 				= b
			where -- first, hit the primary key
				_fk_testopportunity = v_oppkey and t.position = r.position 
				-- next guard against errors
				and t._efk_itsitem is null; -- make sure this position has not been filled
		end if;
	   
		-- check for successful insertion of all and only the items in the group given here
		set v_itemcnt = (select count(*) from testeeresponse
						  where _fk_testopportunity = v_oppkey and groupid = v_groupid and dategenerated = v_today);

		if (v_itemcnt <> v_count) then 
		begin
			rollback;
			set v_errmsg = concat('item insertion failed for group ', v_groupid);

		if v_debug = 1 then select 8; end if;
			call _logdberror(v_procname, v_errmsg, null, null, null, v_oppkey, v_clientname, v_session);
			call _returnerror(v_clientname, v_procname, 'database record insertion failed for new test items', null, v_oppkey, null, null);
			leave proc;
		end;
		end if;

		-- some special processing if these are field test items
		if (exists (select * from tmp_tblinserts where isfieldtest = 1)) then 
		begin
			set v_minftpos = (select min(position) from tmp_tblinserts);

			update ft_opportunityitem 
			set dateadministered = now(3)
			  , positionadministered = v_minftpos
			where _fk_testopportunity = v_oppkey and segment = v_segment and groupid = v_groupid;
		end;
		end if;

		if (_aa_issegmentsatisfied (v_oppkey, v_segment) = 1) then
			update testopportunitysegment 
			set issatisfied = 1
			where _fk_testopportunity = v_oppkey and segmentposition = v_segment;
		end if;

	commit;

	insert into archive._dblatency (duration, starttime, procname, _fk_testopportunity, _fk_session, clientname, `comment`) 
		 values (TIMESTAMPDIFF(microsecond, v_starttime, now(3))/1000, v_today, v_procname, v_oppkey, v_session, v_clientname, 'phase 3: after transaction commit');

    set v_itemcnt = (select count(*)
					   from testeeresponse where _fk_testopportunity = v_oppkey and dategenerated is not null);

    update testopportunity 
	set insegment = v_segment
	  , numitems  = v_itemcnt  
	where _key = v_oppkey;

	select 'inserted' as `status`, v_count as `number`, date_format(v_today, '%Y-%m-%d %H:%i:%S.%f') as datecreated, cast(null as char) as reason;

    select bankitemkey 	as bankitemkey
		 , bankkey
		 , _efk_itsitem as itemkey
		 , v_page 		as `page`
		 , position
		 , `format`
    from tmp_tblinserts
    order by position;

	insert into archive._dblatency (duration, starttime, procname, _fk_testopportunity, _fk_session, clientname, `comment`) 
		 values (TIMESTAMPDIFF(microsecond, v_today, now(3))/1000, v_today, v_procname, v_oppkey, v_session, v_clientname, 'final log');
	
--     call _logdblatency(v_procname, v_today, null, 1, v_page, v_oppkey, v_session, v_clientname, null);

	-- clean-up
	drop temporary table tmp_tblitems;
	drop temporary table tmp_tblinserts;
	drop temporary table if exists tblout_buildtable;


end $$

DELIMITER ;
