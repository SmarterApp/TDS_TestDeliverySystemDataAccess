DELIMITER $$

drop procedure if exists `_removeunanswered` $$

create procedure `_removeunanswered`(
/*
Description:

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/06/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_oppkey varbinary(16)
  , v_debug bit -- = 0
)
sql security invoker
proc: begin

    -- get the first item page (and groupid) that was viewed but never answered
    -- place the itemgroups into the 'recycle' pool by updating testeeitemhistory as follows:
        -- all itemgroups visited but not completely answered: set deleted = 1
        -- all itemgroups not visited, set deleted = 1, dategenerated = null
    -- clear the testopp of all items on all pages >= this page
    declare v_restart, v_firstpage, v_lastpage int;
    declare v_starttime datetime(3);

	set v_starttime = now(3);
    set v_restart = (select restart from testopportunity where _key = v_oppkey);

	drop temporary table if exists tmp_tblitems;
    create temporary table tmp_tblitems(`page` int, pos int, groupid varchar(50), required int, viewed bit, response int, grouprequired int);

    insert into tmp_tblitems (`page`, pos, groupid, required, viewed, response, grouprequired)
    select `page`, position, groupid, isrequired
		 , case when visitcount > 0 then 1 else 0 end, case when datesubmitted is not null then 1 else 0 end
		 , groupitemsrequired
    from testeeresponse r, testopportunitysegment s
    where r._fk_testopportunity = v_oppkey and s._fk_testopportunity = v_oppkey and s.`algorithm` like 'adaptive%' and dategenerated is not null
		and r.segment = s.segmentposition 
		and exists (select * from testeeresponse r1
					 where r1._fk_testopportunity = v_oppkey and datelastvisited is not null and r.groupid = r1.groupid and r1.isfieldtest = 0)
		and exists (select * from testeeresponse r2
                     where r2._fk_testopportunity = v_oppkey and datesubmitted is null and r.groupid = r2.groupid and r2.isfieldtest = 0);


    if (not exists (select * from tmp_tblitems)) then
		if (v_debug = 1) then select 'empty tmp_tblitems'; end if;
		leave proc; 
	end if;
    
    update tmp_tblitems 
	set required = 1 
	where grouprequired = -1;

	drop temporary table if exists tmp_tblgroups;
    create temporary table tmp_tblgroups(`page` int, gid varchar(50), itemsrequired int, itemcount int, maxrequired int, answered int);

    insert into tmp_tblgroups (`page`, gid, itemsrequired , itemcount , maxrequired , answered )
    select `page`, groupid, sum(required), count(*), max(grouprequired), sum(response)
    from tmp_tblitems
    group by `page`, groupid;

    update tmp_tblgroups 
	set maxrequired = itemcount
    where maxrequired > itemcount or maxrequired = -1; 

    update tmp_tblitems, tmp_tblgroups
	set required = 1
    where groupid = gid and maxrequired = itemcount;

    /*  cases:
    1. if there exists an item where response < required then the group is a candidate for v_firstpage
        (covers all cases of 'required' items as well as groups which require all items but the items themselves are optional)
    2. if there exists a group where maxrequired > answered the group is a candidate for v_firstpage
        (covers cases where there are optional items in a group that requires fewer than all items to be answered)
    */
	drop temporary table if exists tmp_tblgroups;
    create temporary table tmp_tblpages(`page` int);

    insert into tmp_tblpages (`page`)
    select distinct `page` 
	from tmp_tblitems where required > 0 and response = 0
    union 
    select `page` 
	from tmp_tblgroups where answered < itemsrequired;

    select min(`page`), max(`page`) into v_firstpage, v_lastpage
	from tmp_tblpages;
    
    if (v_debug = 1) then
        select v_firstpage as firstpage, v_lastpage as lastpage;
        select `page` from tmp_tblpages order by `page`;
        select * from tmp_tblgroups order by `page`;
        select * from tmp_tblitems order by pos;
        
		leave proc;
    end if;

    -- treat any items generated after the v_lastpage as if they were never selected
    update testeeitemhistory h, testeeresponse r
	set deleted = 1
	  , dategenerated = null
    where h._fk_testopportunity = v_oppkey and r._fk_testopportunity = v_oppkey 
		and r.`page` > v_lastpage and h.groupid = r.groupid;
        
    -- treat any items found above as administered but recyclable
    update testeeitemhistory h, testeeresponse r 
	set deleted = 1
    where h._fk_testopportunity = v_oppkey and r._fk_testopportunity = v_oppkey 
        and r.`page` between v_firstpage and v_lastpage 
		and r.groupid = h.groupid;
 

    update testeeresponse 
	set _efk_itsitem = null, _efk_itsbank = null, _fk_session = null
	  , opportunityrestart = 0
	  , `page` = null, answer = null, scorepoint = null, `format` = null, isfieldtest = 0
	  , dategenerated = null, datesubmitted = null, datefirstresponse = null
	  , response = null
	  , mark = 0
	  , score = -1
	  , hostname = null
	  , numupdates = 0
	  , datesystemaltered = null
	  , isinactive = 0
	  , dateinactivated = null
	  , _fk_adminevent = null
	  , groupid = null
	  , isselected = 0
	  , isrequired = 0, responsesequence = 0, responselength = 0 
	  , _fk_browser = null, isvalid = 0, scorelatency = 0, groupitemsrequired = -1
	  , scorestatus = null, scoringdate = null, scoreddate = null, scoremark = null, scorerationale = null, scoreattempts = 0
	  , _efk_itemkey = null, _fk_responsesession = null, segment = 0, contentlevel = null, segmentid = null
	  , groupb = null, itemb = null, datelastvisited = null, visitcount = 0
	where _fk_testopportunity = v_oppkey and `page` >= v_firstpage;

	-- update the segment completion status if necessary
    update testopportunitysegment 
	set issatisfied = _aa_issegmentsatisfied(_fk_testopportunity, segmentposition)
    where _fk_testopportunity = v_oppkey;

	-- record this alteration in 'facts' to testopportunity table.
	-- note that, since none of the affected items had responses, it is not necessary to update the numresponses field
	-- or to remove records from testeeresponseaudit
	update testopportunity 
	set numitems = (select count(*) from testeeresponse where _fk_testopportunity = v_oppkey and dategenerated is not null)
	  , numresponses = (select count(*) from testeeresponse where _fk_testopportunity = v_oppkey and datesubmitted is not null)
	where _key = v_oppkey;
       
    insert into archive.opportunityaudit (_fk_testopportunity, dateaccessed, accesstype, `comment`, hostname)
    select v_oppkey, now(3), 'removeunanswered', concat('removed items >= page ', cast(v_firstpage as char(3))), @@hostname;

	-- clean-up
	drop temporary table tmp_tblitems;
	drop temporary table tmp_tblgroups;
	drop temporary table tmp_tblpages;

	call _logdblatency('_removeunanswered', v_starttime, null, null, null, v_oppkey, null, null, null);

end $$

DELIMITER ;
