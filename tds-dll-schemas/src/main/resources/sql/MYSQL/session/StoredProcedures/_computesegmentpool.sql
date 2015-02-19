DELIMITER $$

drop procedure if exists `_computesegmentpool` $$

create procedure `_computesegmentpool`(
/*
Description: for adaptive segments only, adjust the test length according to this opportunity's custom item pool (operational items only)
			by reducing the overall test length by the amount of items unavailable to satisfy strands or the overall test length
			returns the adjusted test length and the itempool string

VERSION 	DATE 			AUTHOR 			COMMENTS
001			11/25/2013		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_oppkey varbinary(16)
  , v_segmentkey varchar(250)
  , out v_testlen int
  , out v_poolcount int
  , out v_poolstring text
  , v_debug bit
  , v_sessionkey varbinary(16)
)
sql security invoker
begin  

    declare v_shortfall int;       -- sum of items on strands that the pool falls below the min
    declare v_strandcnt, v_newlen int;

	drop temporary table if exists tmp_tblpool;
	drop temporary table if exists tmp_tblblueprint;

    create temporary table tmp_tblpool(itemkey varchar(50) primary key not null, isft bit, isactive bit, strand varchar(200));
    create temporary table tmp_tblblueprint(strand varchar(200), minitems int, maxitems int, poolcnt int);

	-- initilize input variables when no values are passed
	if v_debug is null then set v_debug = 0; end if;
	
    set v_poolstring = _aa_itempoolstring(v_oppkey, v_segmentkey);

	/* Call _buildtable stored procedure 
	-- To capture and use result set from _buildtable, a temporary table is created to store the resultset */
	drop temporary table if exists tblout_buildtable;
	create temporary table tblout_buildtable(idx int, record varchar(255));
		  
	call _buildtable(v_poolstring, ',');

    insert into tmp_tblpool (itemkey)
    select record 
	from tblout_buildtable;
	/* # */

	if (v_sessionkey is null) then
	begin
        update tmp_tblpool p, itembank.tblsetofadminitems i
		set p.isft = i.isfieldtest
		  , p.isactive = i.isactive
		  , p.strand = i.strandname
		where i._fk_adminsubject = v_segmentkey and i._fk_item = p.itemkey;

        insert into tmp_tblblueprint (strand, minitems, maxitems, poolcnt)
        select _fk_strand, minitems, maxitems, (select count(*) from tmp_tblpool where strand = _fk_strand and isft = 0 and isactive = 1 )
        from itembank.tbladminstrand
        where _fk_adminsubject = v_segmentkey and adaptivecut is not null;

        select minitems into v_testlen 
		from itembank.tblsetofadminsubjects 
		where _key = v_segmentkey;
    end;
    else 
	begin
        update tmp_tblpool p, sim_segmentitem i  
		set p.isft = i.isfieldtest
		  , p.isactive = i.isactive
		  , p.strand = i.strand
        where _fk_session = v_sessionkey and _efk_segment = v_segmentkey and _efk_item = itemkey;

        insert into tmp_tblblueprint (strand, minitems, maxitems, poolcnt)
        select contentlevel, minitems, maxitems, (select count(*) from tmp_tblpool where strand = contentlevel and isft = 0 and isactive = 1  )
        from sim_segmentcontentlevel
        where _fk_session = v_sessionkey and _efk_segment = v_segmentkey and adaptivecut is not null;

        select minitems into v_testlen 
		from sim_segment 
		where _fk_session = v_sessionkey and _efk_segment = v_segmentkey;
    end;
	end if;

    select sum(minitems - poolcnt) into v_shortfall
    from tmp_tblblueprint 
	where poolcnt < minitems;

    if (v_shortfall is null) then set v_shortfall = 0; end if;

    set v_strandcnt = (select sum(poolcnt) from tmp_tblblueprint);
    set v_newlen = (case when v_testlen - v_shortfall < v_strandcnt then v_testlen - v_shortfall else v_strandcnt end);
    set v_poolcount = v_strandcnt;
    set v_testlen = v_newlen;

    if (v_debug = 1) then
        select v_testlen as testlen, v_shortfall as shortfall, v_strandcnt as strandcnt, v_newlen as newlen, v_poolstring as poolstring;
        select * from tmp_tblblueprint;
        select * from tmp_tblpool;
    end if;

	-- clean-up
	drop temporary table tmp_tblpool;
	drop temporary table tmp_tblblueprint;

end $$

DELIMITER ;



