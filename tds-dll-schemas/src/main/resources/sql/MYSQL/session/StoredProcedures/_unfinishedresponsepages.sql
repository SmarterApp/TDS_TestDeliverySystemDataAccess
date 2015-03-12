DELIMITER $$

drop procedure if exists `_unfinishedresponsepages` $$

create procedure `_unfinishedresponsepages`(
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/06/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_oppkey varbinary(16)
  , v_newrestart int
  , v_doupdate bit -- = 0
)
sql security invoker
proc: begin

	declare v_procname varchar(100);
	declare v_starttime datetime(3);
	set v_starttime = now(3);
	set v_procname = '_unfinishedresponsepages';

	drop temporary table if exists tmp_tblunfinished;
    create temporary table tmp_tblunfinished(
		`page` int
	  , grouprequired int
	  , numitems int
	  , validcount int
	  , requireditems int
	  , requiredresponses int
	  , isvisible bit
	); 

	insert into tmp_tblunfinished (isvisible, `page`, grouprequired, numitems, validcount, requireditems, requiredresponses)
	select 0, `page`, groupitemsrequired, count(*), sum(cast(isvalid as unsigned))
		 , sum(cast(isrequired as unsigned))
		 , sum(case when isrequired = 1 and isvalid = 1 then 1 else 0 end)
    from testeeresponse
	where _fk_testopportunity = v_oppkey and dategenerated is not null 
    group by `page`, groupitemsrequired
	order by null;
    
    update tmp_tblunfinished 
	set grouprequired = numitems where grouprequired = -1;

    update tmp_tblunfinished 
	set isvisible = 1
    where requiredresponses < requireditems or validcount < grouprequired ;

    if (v_doupdate = 1) then
        update testeeresponse set opportunityrestart = v_newrestart
        where _fk_testopportunity = v_oppkey and `page` in (select page from tmp_tblunfinished where isvisible = 1);
        
		leave proc;
    else 
		select * from tmp_tblunfinished;
	end if;

	-- clean-up
	drop temporary table tmp_tblunfinished;

	call _logdblatency(v_procname, v_starttime, null, null, null, v_oppkey, null, null, null);

end $$

DELIMITER ;