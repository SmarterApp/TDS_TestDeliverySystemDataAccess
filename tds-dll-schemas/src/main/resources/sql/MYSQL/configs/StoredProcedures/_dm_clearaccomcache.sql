DELIMITER $$

drop procedure if exists `_dm_clearaccomcache` $$

create procedure `_dm_clearaccomcache`(
/*
Description: a daily maintaince process that registers all active tests in the __accommodationcache

VERSION 	DATE 			AUTHOR 			COMMENTS
001			10/21/2014		Sai V. 			Ported code from SQL Server to MySQL
*/
)
sql security invoker
begin

    declare v_client varchar(100);    

	drop temporary table if exists tmp_tblclients;
    create temporary table tmp_tblclients (
		cname varchar(100)
	) engine = memory;


    delete from __accommodationcache;

    insert into tmp_tblclients (cname)
    select distinct clientname from session._externs;


    while (exists (select * from tmp_tblclients)) do
	begin

        select cname into v_client from tmp_tblclients limit 1;
        delete from tmp_tblclients where cname = v_client;

		insert into __accommodationcache (clientname, testkey, _date)
		select '--none--' -- v_client
			 , m.testkey, now(3) -- , m.sessiontype, w.sessiontype
         from client_testwindow w, client_testmode m, client_testproperties p
 	        , session._externs e, itembank.tblsetofadminsubjects bank    -- make sure the itembank we are pointing to coincides with tdsconfigs' data
		where 
			p.clientname = v_client and e.clientname = v_client
			and w.clientname = v_client and w.testid = p.testid 
			and now(3) between case when w.startdate is null then now(3) else date_add(w.startdate, interval shiftwindowstart day) end
							  and case when w.enddate is null then now(3) else date_add(w.enddate, interval shiftwindowend day) end
			and m.clientname = v_client and m.testid = p.testid        
			and isselectable = 1
			and bank._key = m.testkey;
			-- and (m.sessiontype = -1 or m.sessiontype = v_sessiontype) and (w.sessiontype = -1 or w.sessiontype = v_sessiontype)

			/*
			insert into __accommodationcache (clientname, testkey)
			select v_client, testkey 
			from dbo.activetests(v_client, 0) where isselectable = 1;

			insert into __accommodationcache (clientname, testkey)
			select v_client, testkey 
			from dbo.activetests(v_client, 1) where isselectable = 1;
			*/
    end;
	end while;

	
	-- clean-up
	drop temporary table if exists tmp_tblclients;
	
end$$

DELIMITER ;

