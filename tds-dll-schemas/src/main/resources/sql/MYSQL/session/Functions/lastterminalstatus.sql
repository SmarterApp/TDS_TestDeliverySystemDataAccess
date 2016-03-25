DELIMITER $$

drop function if exists `lastterminalstatus` $$

create function `lastterminalstatus`(
/*
Description: -- determine what the terminal status of an opportunity should be prior to being reported
			 -- based on the time stamps datescored, datecompleted, dateexpired, dateinvalidated

VERSION 	DATE 			AUTHOR 			COMMENTS
001			04/01/2015		Sai V. 			Code ported from SQL Server to MySQL
*/
	v_oppkey varbinary(16)
)
returns varchar(50)
sql security invoker
begin

	declare v_status varchar(50);
	declare v_maxdate datetime(3);

	drop temporary table if exists tmp_tblstatusdates;
    create temporary table tmp_tblstatusdates(
		`status` varchar(50)
	  , _date datetime
	)engine = memory;

    
    insert into tmp_tblstatusdates (`status`, _date)
    select 'scored', datescored
    from testopportunity where _key = v_oppkey 
    union
    select 'completed', datecompleted
    from testopportunity where _key = v_oppkey
    union
    select 'expired', dateexpired
    from testopportunity where _key = v_oppkey
    union
    select 'invalidated', dateinvalidated
    from testopportunity where _key = v_oppkey;

    delete from tmp_tblstatusdates where _date is null;

    set v_maxdate = (select max(_date) from tmp_tblstatusdates);

    set v_status = (select `status` from tmp_tblstatusdates where _date = v_maxdate);

	-- clean-up
	drop temporary table tmp_tblstatusdates;

	-- return result
    return v_status;  

end $$

DELIMITER ;