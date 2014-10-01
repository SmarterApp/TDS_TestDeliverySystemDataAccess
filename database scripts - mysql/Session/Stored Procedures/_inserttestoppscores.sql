DELIMITER $$

drop procedure if exists `_inserttestoppscores` $$

create procedure `_inserttestoppscores`(
/*
Description: Procedure is called to insert test opportunity scores

VERSION 	DATE 			AUTHOR 			COMMENTS
001			01/02/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_testoppkey varbinary(16)
  , v_scorestring varchar(250)
  , v_coldelim char(1)
  , v_rowdelim char(1)
  , out v_msg text)
begin  

	-- NOTE: delimited string containing 4-tuples: (strand, measure name, score/value, standard error)   

    declare v_test varchar(200);
    declare v_starttime datetime;
    declare v_isofficial bit;
    declare v_rowstr text;
    declare v_rowindex bigint;
    declare v_subject varchar(100);
    declare v_clientname varchar(100);

	-- initialize variables
	if v_coldelim is null then set v_coldelim = ','; end if;
	if v_rowdelim is null then set v_rowdelim = ';'; end if;

	set v_starttime = now();

	drop temporary table if exists tblscores;
	drop temporary table if exists tblrows;
	drop temporary table if exists tblcols;

    create temporary table tblscores (strand text, measure text, val varchar(100), se float, ability bigint default 0);
    create temporary table tblrows (idx bigint, rowstr text);
    create temporary table tblcols (idx bigint, colstr text);


-- begin try

    select clientname, _efk_testid, `subject`
	  into v_clientname, v_test, v_subject
      from testopportunity 
	 where _key  = v_testoppkey;

    -- check the string for rowdelim termination. if not, make it so.

    if (exists (select * from testeeresponse 
				where _fk_testopportunity = v_testoppkey and _efk_itsitem is not null and score = -1 and isfieldtest = 0)) then
        set v_isofficial = 0;
    else 
		set v_isofficial = 1;
	end if;

	/* Call _buildtable stored procedure 
	-- To capture and use result set from _buildtable, a temporary table is created to store the resultset */
	drop temporary table if exists tblout_buildtable;
	create temporary table tblout_buildtable(idx int, record varchar(255));
		  
	call _buildtable(v_scorestring, v_rowdelim);

    insert into tblrows (idx, rowstr)
    select record 
	from tblout_buildtable;
	/* # */

    while (exists (select * from tblrows)) 
	do
        select idx, rowstr into v_rowindex, v_rowstr
		from tblrows limit 1;

        delete from tblrows where idx = v_rowindex;
		delete from tblcols;

		/* Call _buildtable stored procedure 
		-- To capture and use result set from _buildtable, a temporary table is created to store the resultset */
		drop temporary table if exists tblout_buildtable;
		create temporary table tblout_buildtable(idx int, record varchar(255));
			  
		call _buildtable(v_rowstr, v_coldelim);

		insert into tblcols (idx, colstr)
		select record 
		from tblout_buildtable;
		/* # */

        insert into tblscores (strand, measure, val, se)
        select (select colstr from tblcols where idx = 1)
			 , (select colstr from tblcols where idx = 2)
			 , (select colstr from tblcols where idx = 3 )
			 , (select colstr from tblcols where idx = 4 and isnumeric(colstr) = 1);
    end while;

    if (exists (select * from testopportunityscores where _fk_testopportunity = v_testoppkey)) then
        if (auditscores(v_clientname) = 1) then
            insert into testopportunityscores_audit (_fk_testopportunity, `subject`, measureof, measurelabel, `value`, standarderror)
            select v_testoppkey, `subject`, measureof, measurelabel, `value`, standarderror
            from testopportunityscores 
			where _fk_testopportunity = v_testoppkey;           
        end if;
        delete from testopportunityscores where _fk_testopportunity = v_testoppkey;
    end if;

    update tblscores s, tdsconfigs_client_testscorefeatures f 
	set ability = useforability 
    where f.clientname = v_clientname and (f.testid = v_test or f.testid = '*') and f.measureof = strand and f.measurelabel = measure;

    insert into testopportunityscores (_fk_testopportunity, `subject`, useforability, measureof, measurelabel, `value`, standarderror)
    select v_testoppkey, v_subject, ability, strand, measure, val, se 
	from tblscores;

    
-- end try
-- begin catch
    
    -- set v_msg = error_message();
    -- exec _logdberror v_v_procid, v_msg, v_testopp = v_testoppkey;
    
-- end catch


end$$

DELIMITER ;



