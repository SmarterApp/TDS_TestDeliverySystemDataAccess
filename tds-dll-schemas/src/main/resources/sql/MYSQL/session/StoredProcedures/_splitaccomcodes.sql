DELIMITER $$

drop procedure if exists `_splitaccomcodes` $$

create procedure `_splitaccomcodes`(
/*
DESCRIPTION: -- splits a 3-way delimited string of accommodation codes that may be discriminated by accommodation family
			-- this result may then be joined with the full set of accommodations available for the given test using dbo.clienttestaccommodations
			-- note: this function does not filter accommodations for the test beyond those appropriate to the family

VERSION 	DATE 			AUTHOR 			COMMENTS
001			2/10/2015		Sai V. 			
*/
  	v_clientname varchar(100)
  , v_testkey 	 varchar(250)
  , v_accoms 	 text
)
sql security invoker
proc: begin

    declare v_family varchar(50);
    declare v_testid varchar(200);
    declare v_cset1 text;
    declare v_row text;
    declare v_rowidx int;
    declare v_code text;
    declare v_codeidx int;
    declare v_codedelim, v_delim, v_familydelim char(1);
    set v_codedelim = '|';
    set v_delim = ';';
    set v_familydelim = ':';

	drop temporary table if exists tmp_tblresult;
	create temporary table tmp_tblresult(
		atype varchar(50)
	  , acode varchar(100)
	  , avalue varchar(250)
	  , allow bit
	  , control bit
	  , isdefault bit
	)engine=memory;
        
    select k.testid, accommodationfamily
	into v_testid, v_family
    from configs.client_testmode k, configs.client_testproperties p
    where p.clientname = v_clientname and k.clientname = v_clientname 
        and k.testkey = v_testkey and k.testid = p.testid;

	-- split the string into rows as delimited by the family row delimiter v_delim
	drop temporary table if exists tmp_tblsplitrow;
    create temporary table tmp_tblsplitrow(idx int, record text)engine=memory;

	/* Call _buildtable stored procedure 
	-- To capture and use result set from _buildtable, a temporary table is created to store the resultset */
	drop temporary table if exists tblout_buildtable;
	create temporary table tblout_buildtable(idx int, record varchar(255))engine=memory;
		  
	call _buildtable(v_accoms, v_delim);

	insert into tmp_tblsplitrow(idx, record)
	select idx, record 
	from tblout_buildtable;
	/* # */

	-- remove all family-specific rows that do not match this test's accommodation family
    delete from tmp_tblsplitrow 
	where locate(':', record, 1) > 0 and locate(concat(v_family, v_familydelim), record, 1) = 0;

	-- remove the (non-redundant) family delimiter to 'normalize' possibly heterogeneous row types (family vs no-family)
    update tmp_tblsplitrow 
	set record = substring(record, length(record) - (length(v_family) + 1), length(record)) 
	where locate(concat(v_family, v_familydelim), record, 1) > 0;


	-- join the rows together into a single string of individual tool codes delimited by the code delimiter v_codedelim
    while (exists (select * from tmp_tblsplitrow)) do
	begin
        select idx, record 
		into v_rowidx, v_row
		from tmp_tblsplitrow limit 1;

        delete from tmp_tblsplitrow where idx = v_rowidx;

        if (v_cset1 is null) then 
			set v_cset1 = v_row; 
        else 
			set v_cset1 = v_cset1 + v_codedelim + v_row;
		end if;
    end;
	end while;

	/* Call _buildtable stored procedure 
	-- To capture and use result set from _buildtable, a temporary table is created to store the resultset */
	drop temporary table if exists tblout_buildtable;
	create temporary table tblout_buildtable(idx int, record varchar(255))engine=memory;
		  
	call _buildtable(v_cset1, v_codedelim);

	insert into tblout_splitaccomcodes(`code`)
	select substring(record, 1, 50) 
	from tblout_buildtable;
	/* # */

	-- clean-up
	drop temporary table tmp_tblresult;
	drop temporary table tmp_tblsplitrow;
	drop temporary table tblout_buildtable;

end $$

DELIMITER ;
