DELIMITER $$

drop procedure if exists `_initializetestsegments` $$

create procedure `_initializetestsegments`(
/*
Description: -- create a record for each segment of a virtual test, or a single segment for a non-virtual test
			-- segments are used to convey data about a test even if it is not segmented, such as formid and field test item selections
			-- for each segment as appropriate (or the unsegmented test), generate field test items, select a test form
			-- if more than one form is passed into v_formkeylist they must be separated by semicolons. this case is generally only applicable for segmented tests and will not be used in the foreseeable future

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/06/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_oppkey varbinary(16)
  , out v_error varchar(200)
  , v_formkeylist text -- = null
  , v_debug bit -- = 0
)
sql security invoker
proc: begin

    declare v_testkey varchar(250);
    declare v_testid varchar(100);
    declare v_segmentid varchar(100);
    declare v_parentkey varchar(250);
    declare v_clientname varchar(100);
    declare v_query text;
    declare v_ftcnt int;
    declare v_issegmented bit;
    declare v_algorithm varchar(50);
    declare v_pos int;
    declare v_formkey varchar(50);
    declare v_formid varchar(200);
    declare v_formlength int;
    declare v_opitems int;
    declare v_ftitems int;
    declare v_language varchar(20);
    declare v_itemstring text;       		-- for pre-computing the operational item pool and storing with segment record
    declare v_newlen, v_poolcount int;
    declare v_formcohort varchar(20);       -- for enforcing form consistency across segments
    declare v_issatisfied bit; 
    declare v_session varbinary(16);
    declare v_sessionpoolkey varbinary(16);
    declare v_issimulation bit;
    declare v_segcnt int;
    declare v_segpos int;
	declare v_msg varchar(400);
	declare v_procname varchar(100);

	declare raise_error condition for sqlstate '45000'; -- 45000 means "unhandled user-defined exception"	

	declare exit handler for sqlexception
    begin		
		set v_msg = 'mysql exit handler: sqlexception';
		call _logdberror('_initializetestsegments', v_msg, null, null, null, v_oppkey, v_clientname, null);

		if (v_debug = 1) then 
			set v_error = v_msg; 
		else 
			set v_error = 'segment initialization failed';
		end if;
	end; 	

	
    if (exists (select * from testopportunitysegment where _fk_testopportunity = v_oppkey and v_debug = 0)) then
        if v_debug = 1 then select 'segments already exist'; end if;
        leave proc;      -- this is a duplicate attempt to initialize a test. don't do it.
    end if;

	set v_procname = '_initializetestsegments';
    set v_issimulation = issimulation(v_oppkey);
    
    -- create a temporary table to build segments in. when done, insert them en masse into testopportunitysegment table with guard against duplication
	drop temporary table if exists tmp_tblsegments;
    create temporary table tmp_tblsegments(
		_fk_testopportunity	varbinary(16) not null,
		_efk_segment       	varchar(250) not null,
		segmentposition    	int not null,
		formkey            	varchar(50) null,
		formid             	varchar(200) null,
		`algorithm`         varchar(50) null,
		opitemcnt          	int null,
		ftitemcnt          	int null,
		ftitems            	text null,
		ispermeable        	int not null default -1,
		restorepermon      	varchar(50) null,
		segmentid          	varchar(100) null,
		entryapproved      	datetime(3) null,
		exitapproved       	datetime(3) null,
		formcohort         	varchar(20) null,
		issatisfied        	bit not null default 0,
		initialability     	float null,
		currentability     	float null,
		_date              	datetime(3) not null,
		dateexited         	datetime(3) null,
		itempool           	text null,
		poolcount          	int null
	);
    
    set v_error = null;
    set v_language = getopportunitylanguage(v_oppkey);
    
    select _fk_session, clientname, _efk_testid, _efk_adminsubject, issegmented, `algorithm`
	into v_session, v_clientname, v_testid, v_testkey, v_issegmented, v_algorithm
    from testopportunity 
	where _key = v_oppkey;

    set v_parentkey = v_testkey;

    if (v_debug = 1) then
        select v_testkey as testkey, v_language as lang, v_issegmented as segmented, v_algorithm as `algorithm`;
	end if;

    if (v_issimulation = 1) then 
	begin -- non-segmented test has a record in sim_segment as segment 1
        insert into tmp_tblsegments (_fk_testopportunity, _efk_segment, segmentid, segmentposition, `algorithm`, opitemcnt) 
        select v_oppkey, _efk_segment, segmentid, segmentposition, selectionalgorithm, maxitems 
        from sim_segment ss
        where _fk_session = v_session and _efk_adminsubject = v_testkey; 

        set v_sessionpoolkey = v_session;     -- pass this to _computesegmentpool
    end;	
    else 
	begin
        if (v_issegmented = 1) then
            insert into tmp_tblsegments (_fk_testopportunity, _efk_segment, segmentid, segmentposition, `algorithm`, opitemcnt)
            select v_oppkey, _key , testid , testposition , selectionalgorithm, maxitems
            from itembank.tblsetofadminsubjects ss 
			where virtualtest = v_testkey;            
        else  -- not segmented, so make the test its own segment
            insert into tmp_tblsegments (_fk_testopportunity, _efk_segment, segmentid, segmentposition, `algorithm`, opitemcnt)
            select v_oppkey, v_testkey, testid, 1, selectionalgorithm, maxitems
            from itembank.tblsetofadminsubjects ss 
			where _key = v_testkey;            
        end if;
	end;
	end if;

	if (v_debug = 1) then
        select 'tmp_tblsegments', s.* from tmp_tblsegments s;
	end if;

    select max(segmentposition), min(segmentposition) 
	into v_segcnt, v_segpos
	from tmp_tblsegments;

    -- while segments are supposed to number 1 to n, this loop is set up to traverse them any kind of way from lowest position to highest    
    -- intiialize form selection and field test item selection on each segment
    whilelabel: while (v_segpos <= v_segcnt) do 
	begin
        set v_ftcnt 	  = 0;
        set v_formkey 	  = null;
        set v_formid 	  = null;
        set v_formlength  = null;
        set v_itemstring  = '';
        set v_issatisfied = 0;       -- some segments may be empty

		-- 2/2012: get the number of operational items on this segment
        if (exists (select * from tmp_tblsegments where segmentposition = v_segpos)) then
            select _efk_segment, segmentposition, `algorithm`, segmentid, opitemcnt
			into v_testkey, v_pos, v_algorithm, v_segmentid, v_opitems
            from tmp_tblsegments 
            where _fk_testopportunity = v_oppkey and segmentposition = v_segpos limit 1;
        else
            set v_segpos = v_segpos + 1;
            iterate whilelabel;
        end if;

        if (v_algorithm = 'fixedform') then
		begin            
            -- if this test segment is fixed form ...
			if (v_debug = 1) then select '_selecttestform_driver', v_oppkey, v_testkey, v_language, v_formkeylist, v_formcohort; end if;
            call _selecttestform_driver(v_oppkey, v_testkey, v_language, v_formkey /*output*/, v_formid /*output*/, v_formlength /*output*/, v_formkeylist, v_formcohort, 0);
			if (v_debug = 1) then select '_selecttestform_driver', v_formkey, v_formid, v_formlength; end if;

            if (v_formkey is null) then
                set v_error = 'unable to complete test form selection';
                delete from tmp_tblsegments where _fk_testopportunity = v_oppkey;
                
				leave proc;
            end if;

            set v_poolcount = v_formlength;

            if (v_formcohort is null) then
                set v_formcohort = (select cohort from itembank.testform
									 where _fk_adminsubject = v_testkey and _key = v_formkey);			
			else 
			begin
				if (v_debug = 1) then select '_computesegmentpool', v_oppkey, v_testkey, v_sessionpoolkey; end if;
				call _computesegmentpool(v_oppkey, v_testkey, v_newlen /*output*/, v_poolcount /*output*/, v_itemstring /*output*/, 0, v_sessionpoolkey);
				if (v_debug = 1) then select '_computesegmentpool', v_newlen, v_poolcount, v_itemstring; end if;									


				if (ft_iseligible(v_oppkey, v_testkey, v_parentkey, v_language) = 1 and v_newlen = v_opitems) then
					if (v_debug = 1) then select '_ft_selectitemgroups', v_oppkey, v_testkey, v_pos, v_segmentid, v_language; end if;
					call _ft_selectitemgroups(v_oppkey, v_testkey, v_pos, v_segmentid, v_language, v_ftcnt /*output*/, v_debug, 0);             
					if (v_debug = 1) then select '_ft_selectitemgroups', v_ftcnt; end if;  
				else 
					set v_ftcnt = 0;
				end if;

				if (v_ftcnt + v_newlen = 0) then
					set v_issatisfied = 1;
				end if;
			end;
			end if;
        end;
		end if;
                
        update tmp_tblsegments 
		set itempool = v_itemstring
		  , poolcount = v_poolcount
		  , opitemcnt = case when v_algorithm = 'fixedform' then v_formlength else v_newlen end
		  , formcohort = v_formcohort
		  , formkey = v_formkey
		  , formid = v_formid
		  , ftitemcnt = v_ftcnt
		  , issatisfied = v_issatisfied
        where _fk_testopportunity = v_oppkey and _efk_segment = v_testkey and segmentposition = v_pos;        

        set v_segpos = v_segpos + 1;
        
    end;
	end while;
    
    if (v_debug = 1) then
        select _fk_testopportunity, _efk_segment, segmentposition, formkey, formid, `algorithm`, opitemcnt, ftitemcnt, ftitems, ispermeable, restorepermon, segmentid, entryapproved, exitapproved, formcohort, issatisfied, initialability, currentability, _date, dateexited, itempool, poolcount 
        from tmp_tblsegments;
	end if;

    if (not exists (select * from tmp_tblsegments where _fk_testopportunity = v_oppkey and opitemcnt + ftitemcnt > 0)) then
		signal raise_error
		   set message_text = 'no items in pool';
    end if;

    -- insert the initialized segments into the database unless someone else got there first
    if (v_debug = 0) then
        insert ignore into testopportunitysegment(_fk_testopportunity, _efk_segment, segmentposition, formkey, formid, `algorithm`, opitemcnt, ftitemcnt, ftitems, ispermeable, restorepermon, segmentid, entryapproved, exitapproved, formcohort, issatisfied, initialability, currentability, _date, dateexited, itempool, poolcount) 
        select _fk_testopportunity, _efk_segment, segmentposition, formkey, formid, `algorithm`, opitemcnt, ftitemcnt, ftitems, ispermeable, restorepermon, segmentid, entryapproved, exitapproved, formcohort, issatisfied, initialability, currentability, _date, dateexited, itempool, poolcount 
        from tmp_tblsegments;
	end if;

	-- clean-up
	drop temporary table tmp_tblsegments;


end $$

DELIMITER ;