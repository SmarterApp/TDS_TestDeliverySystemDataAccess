DELIMITER $$

drop procedure if exists `a_reopenopportunity` $$

create procedure `a_reopenopportunity`(
/*
Description: -- this procedure administratively reopens an opportunity so that a student can resume testing
			-- that has been closed for any one of the following reasons:
			-- completed, reported, expired, invalidated (note that a deleted opportunity does not qualify)

VERSION 	DATE 			AUTHOR 			COMMENTS
001			04/01/2015		Sai V. 			Code ported from SQL Server to MySQL
*/
    v_oppkey 	varbinary(16)
  , v_requestor varchar(50)
  , v_reason 	nvarchar(255)
)
sql security invoker 
proc: begin
    
    -- it does not propagate the result downstream to any other entity such as reporting, qa, scoring, etc.
    declare v_msg varchar(255);
    declare v_testee bigint;
    declare v_test varchar(250);
    declare v_opp int;
    declare v_maxopp int;
	declare v_archived datetime(3); 
	declare v_procname, v_clientname varchar(100);

	set v_procname = 'a_reopenopportunity';

    select _efk_testee, _efk_adminsubject, opportunity, items_archived, clientname
	  into v_testee, v_test, v_opp, v_archived, v_clientname
      from testopportunity 
	 where _key = v_oppkey and datedeleted is null;

    set v_maxopp = (select max(opportunity) from testopportunity
					 where _efk_testee = v_testee and _efk_adminsubject = v_test and clientname = v_clientname and datedeleted is null);

    -- todo: check that this is the latest opportunity of this testee/test and that no other opportunities are open. if not, refuse to reopen.
    if (v_maxopp > v_opp) then
		call _returnerror(v_clientname, v_procname, '{0} is not the latest test opportunity for this testee/test', null, v_oppkey, null, 'failed');
        leave proc;
    end if;

    if (v_testee is null) then
		call _returnerror(v_clientname, v_procname, 'no such test opportunity', null, v_oppkey, null, 'failed');
        leave proc;
    end if;

	start transaction;

		set v_msg = 'reopened by ' + v_requestor;
		
		update testopportunity 
		   set prevstatus = 'reopened'
			 , `status` = 'paused'
			 , datesubmitted = null
			 , datereported = null
			 , dateforcecompleted = null
			 , dateexpiredreported = null
			 , dateinvalidated = null
			 , dateexpired = null
			 , datecompleted = null
			 , datescored = null
			 , datechanged = now(3)
			 , datepaused = now(3)
			 , items_archived = null
			 , `comment` = case when `comment` is null then v_msg else concat(`comment`, '; ', v_msg) end
		 where _key = v_oppkey;

		if (v_archived is not null) then   -- move responses back from archive table
			insert into testeeresponse(
				_fk_testopportunity, _efk_itsitem, _efk_itsbank, _fk_session, opportunityrestart, `page`, position, answer, scorepoint, `format`, 
				isfieldtest, dategenerated, datesubmitted, datefirstresponse, response, mark, score, hostname, numupdates, datesystemaltered, 
				isinactive, dateinactivated, _fk_adminevent, groupid, isselected, isrequired, responsesequence, responselength, _fk_browser, isvalid, scorelatency, groupitemsrequired
				, _efk_itemkey, _fk_responsesession, segment, contentlevel, scorestatus, scoringdate, scoreddate) 
			select 
				_fk_testopportunity, _efk_itsitem, _efk_itsbank, _fk_session, opportunityrestart, `page`, position, answer, scorepoint, `format`, 
				isfieldtest, dategenerated, datesubmitted, datefirstresponse, response, mark, score, hostname, numupdates,  datesystemaltered, 
				isinactive, dateinactivated, _fk_adminevent, groupid, isselected, isrequired, responsesequence, responselength, _fk_browser , isvalid, scorelatency, groupitemsrequired
				, _efk_itemkey, _fk_responsesession, segment, contentlevel, scorestatus, scoringdate, scoreddate
			from testeeresponsearchive
			where _fk_testopportunity = v_oppkey;

			-- delete archive responses
			delete from testeeresponsearchive where _fk_testopportunity = v_oppkey;
		end if;

		if (auditopportunities(v_clientname) <> 0) then
			insert into archive.opportunityaudit (_fk_testopportunity, dateaccessed, accesstype, hostname, `comment`)
			select v_oppkey, now(3), 'reopened', @@hostname, concat('opportunity changed by admin proc: ', v_procname, '. Reason: ', v_reason);
			insert into archive.opportunityaudit (_fk_testopportunity, dateaccessed, accesstype, hostname, `comment`)
			select v_oppkey, now(3), 'paused', @@hostname, concat('opportunity changed by admin proc: ', v_procname, '. Reason: ', v_reason);
		end if;

		select 'success' as status, null as newid, null as reason;

	commit;

end $$

DELIMITER ;