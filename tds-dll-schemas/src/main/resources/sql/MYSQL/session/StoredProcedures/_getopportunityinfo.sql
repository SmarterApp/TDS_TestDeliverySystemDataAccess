DELIMITER $$

drop procedure if exists `_getopportunityinfo` $$

create procedure `_getopportunityinfo`(
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			03/06/2015		Sai V. 			
*/
	v_clientname varchar(100)
  , v_sessionid	varbinary(16)
  ,	v_testee bigint
  , v_testlist text -- = null
)
sql security invoker
proc: begin

	-- multiple tests are seperated by ;
	call _buildtable(v_testlist, ';');

	drop temporary table if exists tmp_tbloppcounts;
	create temporary table tmp_tbloppcounts (
	    _efk_testid		varchar(200)
	  , oppcount		int
	) engine = memory;

	insert into tmp_tbloppcounts
	select _efk_testid, count(*) 
	from testopportunity o
		join tblout_buildtable t on t.record = o._efk_testid  
	where _efk_testee = v_testee and clientname = v_clientname and datedeleted is null
	group by _efk_testid
	order by null;
		
	select o._efk_testee
		 , o._key as _efk_testopportunity
		 , o._efk_testid 
		 , o._efk_adminsubject 
		 , o.opportunity
         , o.`status` as oppstatus
		 , o.windowid 
		 , o.datestarted
         , o.`subject`
		 , o.datecompleted 
		 , o.dateexpired 
		 , o.datechanged
		 , (select `status` from statuscodes s where `usage` = 'opportunity' and stage = 'closed' and s.`status` = o.`status`) as laststatus_closedstage
		 , (select `status` from statuscodes s where `usage` = 'opportunity' and stage = 'inactive' and s.`status` = o.`status`) as laststatus_inactivestage
		 , s.sessiontype as lastsessiontype
		 , (select sessiontype from `session` where _key = v_sessionid) as thissessiontype
		 , s._key 	  as lastsession 
		 , s.`status` as lastsessionstatus
		 , s.dateend  as lastsessionend
		 , coalesce((select oppdelay from timelimits tl where clientname = v_clientname and tl._efk_testid = o._efk_testid)
				  , (select oppdelay from timelimits tl where clientname = v_clientname and tl._efk_testid is null)) as delaydays
    from testopportunity o
		join tmp_tbloppcounts c on c._efk_testid = o._efk_testid and opportunity = oppcount
		join `session` s on s._key = o._fk_session 
	where o.clientname = v_clientname 
		and o._efk_testee = v_testee and o.datedeleted is null;


	-- clean-up
-- 	drop temporary table tblout_buildtable;
-- 	drop temporary table tmp_tbloppcounts;

end $$

DELIMITER ;