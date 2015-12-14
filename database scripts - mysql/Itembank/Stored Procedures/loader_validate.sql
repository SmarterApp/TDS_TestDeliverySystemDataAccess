DELIMITER $$

drop procedure if exists `loader_validate` $$

create procedure `loader_validate` (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/23/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
)
begin

	create temporary table tmp_errormsgs (
		packagekey varchar(350)
	  , severity   varchar(20)
	  -- , xmlelement varchar(200)
	  , err  	   text
	);

	create temporary table tmp_items (
		test varchar(100)
	  , obj  varchar(100)
	  , cnt  int
	  , req  int
	  , mn	 float
	  , mx	 float
	);

	insert into tmp_errormsgs
	select v_testpackagekey
		 , 'FATAL ERROR'
		 , 'Test package with purpose = administration can be used to load test data.' 
	  from loader_testpackage
	 where packagekey = v_testpackagekey
	   and purpose <> 'administration';
	
	insert into tmp_errormsgs
	select v_testpackagekey
		 , 'FATAL ERROR'
		 , 'Blank or NULL TestName/TestKey in loader_testpackage' 
	  from loader_testpackage
	 where packagekey = v_testpackagekey
	   and ((testname is null and testname <> '')
			or (testkey is null and testkey <> ''));
	
	insert into tmp_errormsgs
	select v_testpackagekey
		 , 'FATAL ERROR'
		 , 'Tespackage property name/value field cannot be null or blank' 
	  from loader_testpackageproperties
	 where _fk_package = v_testpackagekey
	   and ((propname is NULL and propname <> '')
			or (propvalue is NULL and propvalue <> ''));

	if exists (select count(*) cnt from loader_testpackageproperties
				where _fk_package = v_testpackagekey and propname = 'subject' 
				group by _fk_package) = 0
	then 
		insert into tmp_errormsgs 
		select v_testpackagekey, 'FATAL ERROR', 'Atleast one subject property must be present for a given test';
	end if;

	if exists (select count(*) cnt from loader_testpackageproperties
				where _fk_package = v_testpackagekey and propname = 'grade' 
				group by _fk_package) = 0
	then 
		insert into tmp_errormsgs 
		select v_testpackagekey, 'FATAL ERROR', 'Atleast one gradelevel property must be present for a given test';
	end if;

	
	delete from tmp_items;
	insert into tmp_items (obj)
	select propvalue
	  from loader_testpackageproperties
	 where _fk_package = v_testpackagekey 
	   and propname = 'grade' 
	   and isnumeric(propvalue) = 0;
	
	if exists(select * from tmp_items) then
		insert into tmp_errormsgs
		select v_testpackagekey, 'WARNING', concat(obj, ': ', 'Test has a non-numeric grade')
		  from tmp_items;
	end if;


	/*Check if Testblueprint elementtype has only valid values*/
	if exists (select * from loader_testblueprint
				where _fk_package = v_testpackagekey and elementtype not in ('test', 'segment', 'affinitygroup', 'strand', 'contentlevel') )
	then 
		insert into tmp_errormsgs 
		select v_testpackagekey, 'FATAL ERROR', 'Invalid test blueprint element type';
	end if;


	delete from tmp_items;
	insert into tmp_items (obj)
	select distinct bpelementid
	  from loader_testblueprint
	 where _fk_package = v_testpackagekey and elementtype = 'test';

	if exists (select count(*) from tmp_items) > 1 then
		insert into tmp_errormsgs
		select v_testpackagekey, 'FATAL ERROR', 'Only one test should be present per test package';
	end if;


	delete from tmp_items;
	insert into tmp_items (obj, cnt)
	select bpelementid, count(*)
	  from loader_testblueprint
	 where _fk_package = v_testpackagekey and elementtype = 'segment'
	group by bpelementid;

	if exists (select * from tmp_items where cnt > 1) then
		insert into tmp_errormsgs
		select v_testpackagekey, 'FATAL ERROR', concat('Duplicate segment ID: ', obj)
		  from tmp_items;
	end if;

	delete from tmp_items;
	insert into tmp_items (obj, cnt)
	select bpelementid, count(*)
	  from loader_testblueprint
	 where _fk_package = v_testpackagekey and elementtype = 'strand'
	group by bpelementid;

	if exists (select * from tmp_items where cnt > 1) then
		insert into tmp_errormsgs
		select v_testpackagekey, 'FATAL ERROR', concat('Duplicate strand ID: ', obj)
		  from tmp_items;
	end if;


	delete from tmp_items;
	insert into tmp_items (obj, cnt)
	select bpelementid, count(*)
	  from loader_testblueprint
	 where _fk_package = v_testpackagekey and elementtype = 'contentlevel'
	group by bpelementid;

	if exists (select * from tmp_items where cnt > 1) then
		insert into tmp_errormsgs
		select v_testpackagekey, 'FATAL ERROR', concat('Duplicate content level ID: ', obj)
		  from tmp_items;
	end if;


	delete from tmp_items;
	insert into tmp_items (obj, cnt)
	select bpelementid, count(*)
	  from loader_testblueprint
	 where _fk_package = v_testpackagekey and elementtype = 'affinitygroup'
	group by bpelementid;

	if exists (select * from tmp_items where cnt > 1) then
		insert into tmp_errormsgs
		select v_testpackagekey, 'FATAL ERROR', concat('Duplicate affinity group ID: ', obj)
		  from tmp_items;
	end if;


	/* Check if operational or field items item counts are correct */
	if exists (select * from loader_testblueprint
				where _fk_package = v_testpackagekey and elementtype = 'test' 
				  and (minopitems > maxopitems or minftitems > maxftitems) )
	then 
		insert into tmp_errormsgs 
		select v_testpackagekey, 'FATAL ERROR', 'Test has min items > max items, operational or field test';
	end if;

	if exists (select * from loader_testblueprint
				where _fk_package = v_testpackagekey and elementtype = 'segment' 
				  and (minopitems > maxopitems or minftitems > maxftitems) )
	then 
		insert into tmp_errormsgs 
		select v_testpackagekey, 'FATAL ERROR', 'Segment has min items > max items, operational or field test';
	end if;

	if exists (select * from loader_testblueprint
				where _fk_package = v_testpackagekey and elementtype = 'strand' 
				  and minopitems > maxopitems )
	then 
		insert into tmp_errormsgs 
		select v_testpackagekey, 'FATAL ERROR', 'Strand has min items > max items';
	end if;

	if exists (select * from loader_testblueprint
				where _fk_package = v_testpackagekey and elementtype = 'contentlevel' 
				  and minopitems > maxopitems )
	then 
		insert into tmp_errormsgs 
		select v_testpackagekey, 'FATAL ERROR', 'Content level has min items > max items';
	end if;

	if exists (select * from loader_testblueprint
				where _fk_package = v_testpackagekey and elementtype = 'affinitygroup' 
				  and minopitems > maxopitems )
	then 
		insert into tmp_errormsgs 
		select v_testpackagekey, 'FATAL ERROR', 'Affinity group has min items > max items';
	end if;


	insert into tmp_errormsgs
	select v_testpackagekey, 'FATAL ERROR', concat('Content level is missing parent id: ', parentid)
	  from loader_testblueprint
	 where _fk_package = v_testpackagekey and elementtype = 'contentlevel' 
	   and (parentid is null or parentid = '');

	insert into tmp_errormsgs
	select v_testpackagekey, 'FATAL ERROR', concat('Parent blue print element id does not exist: ', bp1.bpelementid)	
	  from loader_testblueprint bp1
	  left join loader_testblueprint bp2 on bp2.bpelementid = bp1.parentid 
										and bp2._fk_package = bp1._fk_package
	 where bp1._fk_package = v_testpackagekey
	   and bp1.elementtype = 'contentlevel' 
	   and bp2.bpelementid is null;



select * 
from tblsetofadminsubjects
where _fk_adminsubject = 

select * from loader_





select * from loader_testblueprint
where elementtype = 'strand' 









select * from a




	-- check strand requirements against test length on adaptive tests


	if exists (select * from loader_segment where _fk_package = v_testpackagekey and itemselection like 'adaptive%')
		  and (select * from loader_testpackage where packagekey = v_testpackagekey and testname not like '%student help%') then
		insert into tmp_items (test, obj, cnt, req)
		select bpelementid
			 , null
			 , sum(case when elementtype = 'strand' then maxopitems else 0 end)
			 , max(case when elementtype = 'test' then minopitems else 0 end)
		  from loader_testblueprint bp
		 where _fk_package = '(SBAC)CAT-M3-ONON-S1-A1-MATH-3-Fall-2013-2014' -- v_testpackagekey
		group by _fk_package;
	end if;

	if exists (select * from tmp_items where cnt < req) then
		insert into tmp_errormsgs
		select v_testpackagekey
		     , 'Insufficient strand item requirements (sum of maxes) for test length (minitems)';
	end if;
	
	delete from tmp_items;


	if exists (select * from loader_segment where _fk_package = v_testpackagekey and itemselection like 'adaptive%')
		  and (select * from loader_testpackage where packagekey = v_testpackagekey and testname not like '%student help%') then
		insert into tmp_items (test, obj, cnt, req)
		select bpelementid
			 , null
			 , sum(case when elementtype = 'strand' then minopitems else 0 end)
			 , max(case when elementtype = 'test' then maxopitems else 0 end)
		  from loader_testblueprint bp
		 where _fk_package = v_testpackagekey
		group by _fk_package;
	end if;

	if exists (select * from tmp_items where cnt < req) then
		insert into tmp_errormsgs
		select v_testpackagekey
		     , 'Strand item requirements (sum of mins) exceed test length (maxitems)';
	end if;


	-- check form requirements against test length on fixed form tests
	delete from tmp_items;
	insert into tmp_items








	-- check if the client name exists in the db, if not error out. clientnname is in loader_testpackage.publisher
	insert into errormsgs
	select v_testpackagekey
		 , 'Missing client configuration data' 
	  from loader_testpackage
	 where packagekey = v_testpackagekey
	   and clientkey is null;

	insert into errormsgs
	select v_testpackagekey
		 , 'Incorrect year data' 
	  from loader_testpackage
	 where packagekey = v_testpackagekey
	   and `year` is null;

	insert into errormsgs
	select v_testpackagekey
		 , 'Incorrect season data' 
	  from loader_testpackage
	 where packagekey = v_testpackagekey
	   and season is null;	



	-- check if season is one of the following, if not error. fall, winter, spring


end $$

DELIMITER ;