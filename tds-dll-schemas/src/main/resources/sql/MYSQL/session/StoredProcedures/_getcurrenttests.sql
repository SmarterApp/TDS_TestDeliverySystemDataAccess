DELIMITER $$

drop procedure if exists `_getcurrenttests` $$

create procedure `_getcurrenttests`(
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			03/06/2015		Sai V. 			
*/
	v_sessionkey varbinary(16)
)
sql security invoker
proc: begin

	declare v_starttime datetime; 
    declare v_sessiontype int;
    declare v_clientname varchar(100);

	set v_starttime = now(3);    

    select sessiontype, clientname into v_sessiontype, v_clientname
	from session where _key = v_sessionkey;

-- 	drop temporary table if exists tmp_tbltests;
-- 	create temporary table tmp_tbltests (
-- 		testkey 		varchar(250)
-- 	  , testid 			varchar(150)
-- 	  , grade 			varchar(20)
-- 	  , subject 		varchar(100)
-- 	  , enroll 			varchar(100)
-- 	  , maxopps 		int
-- 	  , rtsequiv 		bit
-- 	  , mode 			varchar(50)
-- 	  , session 		varbinary(16)
-- 	  , windowmax 		int
-- 	  ,	windowid		varchar(50)	
-- 	  , startdate		datetime(3)
-- 	  , enddate			datetime(3)
-- 	  , modemax			int
-- 	  , windowsession	int
-- 	  , modesession		int
-- 	);

	-- insert into tmp_tbltests
	select distinct t.testkey
		 , t.testid
		 , t.grade
		 , t.subject
		 , t.label as displayname
		 , bigtoint(coalesce(t.sortorder, 0)) as sortorder
		 , enrolledsubject as enroll
		 , maxopportunities as maxopps
		 , 0 as rtsequiv 
		 , t.mode
		 , st._fk_session as session  
		 , currtest.windowmax
		 , currtest.windowid
		 , currtest.startdate
		 , currtest.enddate
		 , currtest.modemax
		 , currtest.windowsession
		 , currtest.modesession	 
	-- getcurrentTests
	from ( select distinct p.testid, p.subjectname as subject, tool.code as language, p.maxopportunities
				 , p.label, g.grade, g.requireenrollment, g.enrolledsubject, isselectable, p.sortorder
				 , w.numopps as windowmax, w.windowid, w.startdate, w.enddate
				 , m.mode, m.testkey, m.maxopps as modemax , w.sessiontype as windowsession, m.sessiontype as modesession 
			from configs.client_testwindow w, configs.client_testmode m, configs.client_testproperties p
			   , configs.client_testgrades g, configs.client_testtool tool, _externs e, itembank.tblsetofadminsubjects bank 
			where p.clientname = v_clientname and g.clientname = v_clientname 
			  and g.testid = p.testid
			  and e.clientname = v_clientname and w.clientname = v_clientname and w.testid = p.testid 
			  and now(3) between (case when w.startdate is null then now(3) else date_add(w.startdate, interval shiftwindowstart day) end) 
							 and (case when w.enddate is null then now(3) else date_add(w.enddate, interval shiftwindowend day) end) 
			  and m.clientname = v_clientname and m.testid = p.testid and tool.clientname = v_clientname 
			  and tool.contexttype = 'test' and tool.context = p.testid and tool.type = 'language' 
			  and (m.sessiontype = -1 or m.sessiontype = v_sessiontype) 
			  and (w.sessiontype = -1 or w.sessiontype = v_sessiontype) 
			  and isselectable = 1 and bank._key = m.testkey
		) t 
		-- getcurrentTestWindows
		join (  select distinct w.numopps as windowmax, w.windowid 
					 , case when w.startdate is null then now(3) else date_add(w.startdate, interval shiftwindowstart day) end as startdate 
					 , case when w.enddate is null then now(3) else date_add(w.enddate, interval shiftwindowend day) end as enddate 
					 , m.mode, m.testkey, m.maxopps as modemax
					 , w.sessiontype as windowsession, m.sessiontype as modesession 
				from configs.client_testwindow w, configs.client_testmode m, _externs e 
				where w.clientname = v_clientname and w.testid = m.testid and e.clientname = v_clientname
					and now(3) between case when w.startdate is null then now(3) else date_add(w.startdate, interval shiftwindowstart day) end 
								   and case when w.enddate is null then now(3) else date_add(w.enddate,  interval shiftwindowend day) end 
					and m.clientname = v_clientname -- and m.testid = v_testid 
					and (m.sessiontype = -1 or m.sessiontype = v_sessiontype) 
					and (w.sessiontype = -1 or w.sessiontype = v_sessiontype)
			) currtest on currtest.testkey = t.testkey
		left join sessiontests st on st._efk_testid = t.testid and st._fk_session = v_sessionkey;

	-- select * from tmp_tbltests;

end $$

DELIMITER ;
