DELIMITER $$

drop function if exists `iscomplete` $$

create function `iscomplete`(
/*
DESCRIPTION:

VERSION 	DATE 			AUTHOR 			COMMENTS
001			07/23/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_oppkey varbinary(16))
returns bit
begin

    declare v_numitems 	  int;
	declare v_numrequired int;

	drop temporary table if exists tmp_tblgroups;
    create temporary table tmp_tblgroups (
		gid 		varchar(50)
	  , numrequired int
	  , numvalid 	int
	  , waiting 	int
	  , cnt 		int
	  , itemsrequired int
	);

    select count(*) into v_numitems
	  from testeeresponse 
     where _fk_testopportunity = v_oppkey and dategenerated is not null;
    
    select maxitems into v_numrequired from testopportunity where _key = v_oppkey;

	-- first, have the required number of items been administered?
	-- if not, don't go on
    if (v_numitems < v_numrequired) then return 0; end if;

	-- else has each group received enough valid responses?
    insert into tmp_tblgroups (gid, numrequired, numvalid, waiting, cnt, itemsrequired)
    select groupid, groupitemsrequired, sum(cast(isvalid as unsigned)), 0, count(*), sum(cast(isrequired as unsigned))
      from testeeresponse 
	 where _fk_testopportunity = v_oppkey and groupid is not null
    group by groupid, groupitemsrequired;

	-- adjust group numrequired when it is -1 (meaning all items required)
    update tmp_tblgroups set numrequired = cnt 
	 where numrequired = -1;

	-- individual item requirements trump numrequired when they are greater
    update tmp_tblgroups set numrequired = itemsrequired 
	 where numrequired < itemsrequired;


    update tmp_tblgroups 
	   set waiting = (select count(*) from testeeresponse 
					   where _fk_testopportunity = v_oppkey and groupid = gid and scorestatus in ('formachinescoring','waitingformachinescore'));


    if (exists (select * from tmp_tblgroups where numrequired > numvalid or waiting > 0)) then return 0; end if;

    return 1;

end$$

DELIMITER ;