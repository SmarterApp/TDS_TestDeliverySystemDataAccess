DELIMITER $$

drop procedure if exists `updatetdsconfigs` $$

create procedure `updatetdsconfigs` (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			5/14/2014		Sai V. 			--
*/
	v_testpackagekey  varchar(350)
)
begin

    declare v_client 	varchar(100);
	declare v_testkey	varchar(250);
    declare v_testname 	varchar(250);
    declare v_segmented bit;
	declare v_algorithm varchar(50);

	drop temporary table if exists tmp_tests;
    create temporary table tmp_tests(
		`client` 	varchar(100)
	  , test 		varchar(150)
	  , testkey 	varchar(250)
	  , `subject` 	varchar(100)
	  , issegmented bit
	  , selectionalgorithm varchar(50)
	  , instrument  varchar(100)
	  , done 		bit default 0
	  , msb         bit default 0
	);
    
	drop temporary table if exists tmp_segments;
	create temporary table tmp_segments(
		`client` 	varchar(100)
	  , segment 	varchar(150)
	  , segkey 		varchar(250)
	  , pos 		int
	  , virtualtest varchar(200)
	  , vtestkey 	varchar(250)
	);

--  start transaction;
	
	-- Only do those that are related to the current testpackage
	insert into tmp_tests (`client`, test, testkey, `subject`, issegmented)		-- get virtual tests or non-segmented tests here
	select distinct tp.publisher
		 , s.testid
		 , s._key
		 , tp.subjectname
		 , s.issegmented      
	  from tblsetofadminsubjects s 
	  join loader_testpackage tp on tp.testkey = s._key
	 where s.virtualtest is null
	   and packagekey = v_testpackagekey;

	 update tmp_tests 
	 	set msb = cast_to_bit(
	 			CONVERT(IFNULL((
	 				select tpp.propvalue 
	 				from loader_testpackageproperties tpp 
	 				where tpp.propname = 'msb'), '0'), unsigned));

	insert into tmp_segments (`client`, segment, segkey, pos, virtualtest, vtestkey)       -- get the segments
    select distinct tp.publisher
		 , s.testid
		 , s._key
		 , s.testposition
		 , (select testid from tblsetofadminsubjects where _key = s.virtualtest)
		 , s.virtualtest
	  from tblsetofadminsubjects s 
	  join loader_testpackage tp on tp.testkey = s.virtualtest
     where s.virtualtest is not null
	   and packagekey = v_testpackagekey;


	-- collect all language accommodations
	drop temporary table if exists tmp_langs;
    create temporary table tmp_langs(
		`client` 	varchar(100)
	  , testkey 	varchar(200)
	  , testname 	varchar(200)
	  , lang 		varchar(50)
	  , descrip 	varchar(200)
	);

	drop temporary table if exists tmp_result;
	create temporary table tmp_result(
		`code` 	varchar(100)
	  , label 	varchar(200)
	);

	-- get client info.
    set v_client = (select distinct `client` from tmp_tests);

	-- do not add test segments to language test tool
    while (exists (select * from tmp_tests where done = 0)) do
	begin
        select testkey, test, issegmented, selectionalgorithm
		  into v_testkey, v_testname, v_segmented, v_algorithm
          from tmp_tests 
		 where done = 0 limit 1;

		if (v_segmented = 0) then
		begin
			if (v_algorithm = 'fixedform') then
			begin
				insert into tmp_result (`code`, label)
				select distinct  propvalue, propdescription
				  from tblitemprops p
				  join testform f on f._fk_adminsubject = p._fk_adminsubject and f.`language` = p.propvalue
				 where propname = 'language'
				   and p.isactive = 1
				   and f._fk_adminsubject = v_testkey;
			end;
			else begin
				insert into tmp_result (`code`, label)
				select distinct  propvalue, propdescription
				  from tblitemprops p
				 where propname = 'language' 
				   and isactive = 1
				   and p._fk_adminsubject = v_testkey;
			end;
			end if;
		end;
		else begin
			insert into tmp_result (`code`, label)
			select distinct propvalue, propdescription
			  from tblsetofadminitems a
			  join tblitemprops p on a._fk_item = p._fk_item and a._fk_adminsubject = p._fk_adminsubject 
			  join tblsetofadminsubjects s on a._fk_adminsubject = s._key
			 where propname = 'language' 
			   and p.isactive = 1
			   and s.virtualtest = v_testkey;
		end;
		end if;

		-- write the current set of langs to tmp_langs and truncate the table for next iteration
        insert into tmp_langs (client, testkey, testname, lang, descrip)
        select distinct v_client, v_testkey, v_testname, `code`, label
          from tmp_result;

		-- set iteration status and clear out intermediatary temp table
		delete from tmp_result;
		update tmp_tests set done = 1 where testkey = v_testkey;
	end;
	end while;

    
    insert into tdscore_configs_testpackage.client_testeeattribute (clientname, tds_id, rtsname, `type`, label, reportname, atlogin, sortorder)
    select v_client, tds_id, rtsname, `type`, label, reportname, atlogin, sortorder
      from tdscore_configs_testpackage.tds_testeeattribute
     where not exists (select * from tdscore_configs_testpackage.client_testeeattribute 
						where clientname = v_client);

    insert into tdscore_configs_testpackage.client_testeerelationshipattribute (clientname, tds_id, rtsname, label, reportname, atlogin, sortorder, relationshiptype)
    select v_client, tds_id, rtsname, label, reportname, atlogin, sortorder, relationshiptype
      from tdscore_configs_testpackage.tds_testeerelationshipattribute
     where not exists (select * from tdscore_configs_testpackage.client_testeerelationshipattribute 
						where clientname = v_client);
    
    insert into tdscore_configs_testpackage.`client` (`name`)
    select v_client
	  from dual
     where not exists (select * from tdscore_configs_testpackage.`client` 
						where `name` = v_client);

    insert into tdscore_configs_testpackage.client_timewindow(clientname, windowid, startdate, enddate)
    select v_client, 'ANNUAL', now(), date_add(now(), interval 1 year)
      from dual
     where not exists (select * from tdscore_configs_testpackage.client_timewindow 
						where clientname = v_client and windowid = 'ANNUAL');
    
    insert into tdscore_configs_testpackage.client_fieldtestpriority(clientname, tds_id, priority, testid)
    select v_client, p.tds_id, priority, '*'
      from tdscore_configs_testpackage.tds_fieldtestpriority p
	  join tdscore_configs_testpackage.client_testeeattribute a on a.tds_id = p.tds_id
    where a.clientname = v_client 
      and not exists (select * from tdscore_configs_testpackage.client_fieldtestpriority
					   where clientname = v_client);

    insert into tdscore_configs_testpackage.client_grade(clientname, gradecode, grade)
    select distinct `client`, grade, concat('Grade ', cast(grade as char(4))) gradelabel
      from tmp_tests t
	  join setoftestgrades s on s._fk_adminsubject = t.testkey 
     where t.testkey not like '%student help%'
	   and not exists (select * from tdscore_configs_testpackage.client_grade c 
					    where c.clientname = t.`client` and c.gradecode = s.grade);

    insert into tdscore_configs_testpackage.client_subject(clientname, `subject`, subjectcode)
    select distinct t.`client`
		 , t.`subject`
         , t.`subject`
      from tmp_tests t
     where t.testkey not like '%student help%'
       and not exists (select * from tdscore_configs_testpackage.client_subject c 
					    where c.clientname = t.`client` and c.`subject` = t.`subject`);

    insert into tdscore_configs_testpackage.client_accommodationfamily (clientname, family, label)
    select distinct t.`client`, t.`subject`, t.`subject`
      from tmp_tests t
     where not exists (select * from tdscore_configs_testpackage.client_accommodationfamily f 
						where f.clientname = t.`client` and f.family = t.`subject`);

	-- add any missing tests to client_testproperties
    insert into tdscore_configs_testpackage.client_testproperties 
        (clientname, testid, isselectable, label, subjectname, maxopportunities, scorebytds, accommodationfamily,  reportinginstrument, tide_id, gradetext, msb)
    select distinct `client`
		 , test
		 , isselectable(testkey)
		 , _maketestlabel(testkey) as label
		 , t.`subject`
		 , 3
		 , isselectable(testkey)
		 , (select family from tdscore_configs_testpackage.client_accommodationfamily f where t.`client` = f.clientname and t.`subject` = f.label)
         , instrument
		 , case when instrument is not null then concat(instrument, '-', `subject`) else null end
         , _maketestgradelabel(testkey)
         , msb
      from tmp_tests t
     where not exists (select * from tdscore_configs_testpackage.client_testproperties 
						where clientname = `client` and testid = test);

	-- all tests are online only
    insert into tdscore_configs_testpackage.client_testmode(clientname, testid, testkey, `mode`, sessiontype, _key)
    select client, test, testkey, 'online', 0
		 , unhex(replace(uuid(), '-', ''))
      from tmp_tests t
	 where not exists (select * from tdscore_configs_testpackage.client_testmode 
						where clientname = `client` and testid = test);

    update tdscore_configs_testpackage.client_testmode tm
	  join tmp_tests t on t.testkey = tm.testkey
	   set tm.issegmented = t.issegmented;

    update tdscore_configs_testpackage.client_testmode tm 
	  join tblsetofadminsubjects s on s._key = tm.testkey
	  join tmp_tests t on t.testkey = s._key
	   set tm.`algorithm` = s.selectionalgorithm;

	-- remove any segments that no longer are on the test (and hope for the best)
    delete 
	  from tdscore_configs_testpackage.client_segmentproperties 
	 where exists (select * from tmp_tests 
					where clientname = `client` and parenttest = test)
       and not exists (select * from tmp_segments 
						where `client` = clientname and segment = segmentid and virtualtest = parenttest);

    insert into tdscore_configs_testpackage.client_segmentproperties (clientname, segmentid, segmentposition, parenttest, ispermeable, entryapproval, exitapproval, itemreview, label, modekey)
    select client, segment, pos, virtualtest, 1, 0, 0, 0, concat(_maketestlabel(segkey), ' segment'), vtestkey
      from tmp_segments t
     where not exists (select * 
						 from tdscore_configs_testpackage.client_segmentproperties s 
						where s.clientname = t.`client` and s.segmentid = t.segment  and s.parenttest = t.virtualtest);

    update tdscore_configs_testpackage.client_segmentproperties 
	  join tmp_segments on clientname = `client` and segmentid = segment
	   set segmentposition = pos
		 , modekey = vtestkey;

	-- test form properties: 
	-- allow test forms to be assigned to more than one test.
    insert into tdscore_configs_testpackage.client_testformproperties (clientname, _efk_testform, formid, testid, `language`, startdate, enddate, testkey)
    select `client`, _key, formid, test, `language`, null, null, t.testkey
      from tmp_tests t
	  join testform f on f._fk_adminsubject = t.testkey
     where not exists (select * from tdscore_configs_testpackage.client_testformproperties p 
					    where p.clientname = `client` and p._efk_testform = f._key and p.testkey = t.testkey);

	--  test form properties
    insert into tdscore_configs_testpackage.client_testformproperties (clientname, _efk_testform, formid, testid, `language`, startdate, enddate, testkey)
    select `client`, _key, formid, segment, `language`, null, null, t.segkey
      from tmp_segments t
	  join testform f on f._fk_adminsubject = t.segkey
     where not exists (select * from tdscore_configs_testpackage.client_testformproperties p 
					    where p.clientname = `client` and p._efk_testform = f._key and p.testkey = t.segkey);

    insert into tdscore_configs_testpackage.client_testwindow (clientname, testid, windowid, numopps, startdate, enddate, _key)
    select `client`, test, 'ANNUAL', 3, now()
		 , date_add(now(), interval 1 year)
		 , unhex(replace(uuid(), '-', ''))
      from tmp_tests 
	 where not exists (select * from tdscore_configs_testpackage.client_testwindow 
						where clientname = `client` and testid = test);

    delete 
	  from tdscore_configs_testpackage.client_testgrades
     where exists (select * from tmp_tests 
					where clientname = `client` and testid = test);

    insert into tdscore_configs_testpackage.client_testgrades (clientname, testid, grade)
    select distinct `client`, s.testid, g.grade
      from setoftestgrades g
	  join tmp_tests t on t.testkey = g._fk_adminsubject
	  join tblsetofadminsubjects s on s._key = t.testkey
     where s.virtualtest is null;

	-- seed test eligibility table
    insert into tdscore_configs_testpackage.client_testeligibility (_key, clientname, testid, rtsname, enables, disables, rtsvalue, _efk_entitytype, eligibilitytype, matchtype)
	select unhex(replace(uuid(), '-', '')), t.*
	  from (
			select distinct t.client, t.test
				 , 'EnrlGrdCd' as rtsname
				 , 1 as enables
				 , 0 as disables
				   -- rts grade values < 10 are prepended with a '0'. make this adjustment
				 , case when g.grade regexp'^([+-]?[0-9]+\.?[0-9]*e?[0-9]+)|(0x[0-9A-F]+)$' is not null and length(g.grade) = 1 then concat('0', g.grade) else g.grade end as rtsvalue
				 , 6 as _efk_entitytype
				 , 'ATTRIBUTE' as eligibilitytype
				 , 0 matchtype
			  from tmp_tests t
			  join tdscore_configs_testpackage.client_testgrades g on t.`client` = g.clientname and t.test = g.testid
			 where not exists (select * from tdscore_configs_testpackage.client_testeligibility e 
								where e.clientname = g.clientname and e.testid = g.testid)
		) t;

	-- update the itemtypes (used to configure what itemtypes may be printed by student on a test)
    delete 
	  from tdscore_configs_testpackage.client_test_itemtypes
     where exists (select * from tmp_tests 
					where clientname = `client` and testid = test);

	-- first do all the unsegmented tests excluding student help
    insert into tdscore_configs_testpackage.client_test_itemtypes (clientname, testid, itemtype)
    select distinct `client`, test, itemtype
      from tblsetofadminitems a 
	  join tmp_tests t on t.testkey = a._fk_adminsubject
	  join tblitem i on i._key = a._fk_item
     where test not like '%student help%' 
	   and t.issegmented = 0;

	-- now do all the segmented tests, getting their itemtypes from their segments' items
    insert into tdscore_configs_testpackage.client_test_itemtypes (clientname, testid, itemtype)
    select distinct t.`client`, t.test, itemtype
      from tmp_segments s, tblitem i, tblsetofadminitems a, tmp_tests t
     where t.issegmented = 1 
	   and s.virtualtest = t.test
       and a._fk_adminsubject = s.segkey and a._fk_item = i._key
       and not exists (select * from tdscore_configs_testpackage.client_test_itemtypes x 
						where x.clientname = t.`client` and x.testid = t.test and x.itemtype = i.itemtype);


	-- place all item properties into tdsconfigs for individualized itempool selection
    -- seed the test_itemconstraint table with language property
	drop temporary table if exists tmp_tests2;
	create temporary table tmp_tests2 (
		select * from tmp_tests
	);

	drop temporary table if exists tmp_segments2;
	create temporary table tmp_segments2 (
		select * from tmp_segments
	);
	
    delete tic
	  from tdscore_configs_testpackage.client_test_itemconstraint tic
     where exists (select * from tmp_tests2 t 
					where tic.clientname = t.`client` 
					  and tic.testid = t.test and t.issegmented = 0)
	   and not exists (select * from tblitemprops p, tmp_tests t 
						where p._fk_adminsubject = t.testkey 
						  and t.`client` = clientname and t.test = testid
                          and p.propname = tic.propname and p.propvalue = tic.propvalue);

    delete tic
	  from tdscore_configs_testpackage.client_test_itemconstraint tic
     where exists (select * from tmp_segments2 s 
					where clientname = s.`client` and testid = s.virtualtest )
       and not exists (select * from tblitemprops p, tmp_segments s 
						where p._fk_adminsubject = s.segkey and s.`client` = clientname and s.virtualtest = testid
                          and p.propname = tic.propname
                          and p.propvalue = tic.propvalue);

	-- language is only 'in', itemtype is only 'out', TDSPoolFilter is unknown therefore we set for in or out with an appended code
    insert into tdscore_configs_testpackage.client_test_itemconstraint (clientname, testid, propname, propvalue, tooltype, toolvalue, item_in)
    select distinct t.`client`, t.test
		 , p.propname, propvalue, p.propname
		 , concat(propvalue, case p.propname when 'TDSPoolFilter' then ' in' else '' end)
		 , 1
      from tblitemprops p
	  join tmp_tests t on t.testkey = p._fk_adminsubject
     where t.issegmented = 0 
	   and t.test not like '%student help%'
	   and p.propname in ('language', 'TDSPoolFilter')
       and not exists (select * from tdscore_configs_testpackage.client_test_itemconstraint c 
						where c.clientname = t.`client` and c.testid = t.test 
						  and c.propname = p.propname and c.propvalue = p.propvalue and c.item_in = 1);

    -- put in the item_out constraints
    insert into tdscore_configs_testpackage.client_test_itemconstraint (clientname, testid, propname, propvalue, tooltype, toolvalue, item_in)
    select distinct t.`client`, t.test, p.propname, p.propvalue
         , case p.propname when '--ITEMTYPE--' then 'Item Types Exclusion' else p.propname end 
         , case p.propname when '--ITEMTYPE--' then concat('TDS_ItemTypeExcl_', propvalue) else concat(propvalue, ' OUT') end 
         , 0
      from tblitemprops p
	  join tmp_tests t on t.testkey = p._fk_adminsubject 
     where t.issegmented = 0 
	   and t.test not like '%student help%' 
	   and p.propname in ('TDSPoolFilter', '--ITEMTYPE--')
	   and not exists (select * from tdscore_configs_testpackage.client_test_itemconstraint c 
						where c.clientname = t.`client` and c.testid = t.test 
					      and c.propname = p.propname and c.propvalue = p.propvalue and c.item_in = 0);

 
	-- ********* segments of tests *************
    insert into tdscore_configs_testpackage.client_test_itemconstraint (clientname, testid, propname, propvalue, tooltype, toolvalue, item_in)
    select distinct s.`client`, s.virtualtest, p.propname, propvalue, p.propname
         , concat(propvalue, case p.propname when 'TDSPoolFilter' then ' IN' else '' end), 1
    from tblitemprops p, tmp_segments s
    where s.segkey = p._fk_adminsubject and p.propname in ('Language', 'TDSPoolFilter')
    and not exists (select * from tdscore_configs_testpackage.client_test_itemconstraint c 
					 where c.clientname = s.client and c.testid = s.virtualtest
					   and c.propname = p.propname and c.propvalue = p.propvalue and c.item_in = 1);
    
    insert into tdscore_configs_testpackage.client_test_itemconstraint (clientname, testid, propname, propvalue, item_in, tooltype, toolvalue)
    select distinct s.`client`, s.virtualtest, p.propname, propvalue, 0
		 , case when p.propname = '--ITEMTYPE--' then 'Item Types Exclusion' else p.propname end 
         , case p.propname when '--ITEMTYPE--' then concat('TDS_ItemTypeExcl_', propvalue) else concat(propvalue, ' OUT') end
    from tblitemprops p, tmp_segments s
    where  s.segkey = p._fk_adminsubject and p.propname in ('TDSPoolFilter', '--ITEMTYPE--')
    and not exists 
        (select * from tdscore_configs_testpackage.client_test_itemconstraint c where c.clientname = s.`client` and c.testid = s.virtualtest
            and c.propname = p.propname and c.propvalue = p.propvalue and c.item_in = 0);

	-- link up the non-language itempool constraints with an accommodation whose configuration must be finished manually
    insert into tdscore_configs_testpackage.client_testtooltype (clientname, `context`, contexttype, toolname, allowchange, isselectable, isvisible
            , studentcontrol, isfunctional, rtsfieldname, isrequired, tideselectable, tideselectablebysubject)
    select distinct p.clientname, p.testid, 'TEST', p.tooltype, allowchange, 0, 0
		 , 0, 0, rtsfieldname, 0, tideselectable, tideselectablebysubject
    from tdscore_configs_testpackage.client_test_itemconstraint p
	join tdscore_configs_testpackage.tds_testtooltype l on l.toolname = p.tooltype
	join tmp_tests s on p.testid = s.test and p.clientname = s.`client`
    where p.propname = 'TDSPoolFilter'
	  and not exists (select * from tdscore_configs_testpackage.client_testtooltype c
                       where c.clientname = s.`client` and c.contexttype = 'TEST' and c.`context` = testid and c.toolname = p.tooltype);

    insert into tdscore_configs_testpackage.client_testtool (clientname, `type`, `code`, `value`, isdefault, allowcombine, valuedescription, `context`, contexttype)
    select distinct c.clientname, c.tooltype, c.toolvalue, c.toolvalue, 0, 1, 'Item Pool Filter', c.testid, 'TEST'
      from tmp_tests t
	  join tdscore_configs_testpackage.client_test_itemconstraint c on t.`client` = c.clientname and t.test = c.testid
     where c.propname = 'TDSPoolFilter'    -- handle language and itemtype exclusions separately
       and not exists (select * from tdscore_configs_testpackage.client_testtool tt
					    where tt.clientname = c.clientname and tt.contexttype = 'TEST' and tt.`context` = c.testid and tt.`type` = c.tooltype and tt.`code` = c.toolvalue);

	--  insert the default test (testid = '*') for every required testtooltype for every new client and add all values for every required tool except language  
    insert into tdscore_configs_testpackage.client_testtooltype (clientname, `context`, contexttype, toolname, allowchange, rtsfieldname, isrequired, tideselectable, tideselectablebysubject)
    select v_client, '*' , 'TEST', toolname, allowchange, rtsfieldname, isrequired, tideselectable, tideselectablebysubject
    from tdscore_configs_testpackage.tds_testtooltype t
    where t.isrequired = 1 
	  and t.toolname not in  ('language', 'TDSPoolFilter')
      and not exists (select * from tdscore_configs_testpackage.client_testtooltype c
                       where c.clientname = v_client and c.contexttype = 'TEST' and c.`context` = '*' and c.toolname = t.toolname);

    insert into tdscore_configs_testpackage.client_testtool (clientname, `type`, `code`, `value`, isdefault, allowcombine, valuedescription, `context`, contexttype)
    select v_client, `type`, `code`, `value`, isdefault, allowcombine, valuedescription, '*', 'test'
      from tdscore_configs_testpackage.tds_testtool 
	  join tdscore_configs_testpackage.tds_testtooltype t on `type` = toolname
    where isrequired = 1 
	  and toolname not in  ('language', 'TDSPoolFilter')
      and not exists (select * from tdscore_configs_testpackage.client_testtool c 
                    where c.clientname = v_client and c.contexttype = 'TEST' and c.`context` = '*' and c.`type` = t.toolname);

	drop temporary table if exists tmp_lang2;
	create temporary table tmp_langs2 (
		select * from tmp_langs
	);

    -- remove any languages no longer on this test. do not delete and start over so as not to disturb special flags set by configurers
    delete 
	  from tdscore_configs_testpackage.client_testtool
     where `type` = 'language'
	   and exists (select * from tmp_langs where `client` = clientname and testname = `context` and contexttype = 'TEST')
	   and not exists (select * from tmp_langs2 where `client` = clientname and testname = `context` and contexttype = 'TEST' and `code` = lang);

    insert into tdscore_configs_testpackage.client_testtooltype (clientname, `context`, contexttype, toolname, allowchange, isrequired, rtsfieldname
        , isselectable, isvisible, tideselectable, tideselectablebysubject, studentcontrol)
    select distinct `client`
		 , testname, 'TEST', toolname
		 , allowchange, isrequired, rtsfieldname
         , isselectable(testkey) as isselectable
         , isselectable(testkey) as isvisible
         , isselectable(testkey) as tideselectable     
         , isselectable(testkey) as tideselectablebysubject  
         , case when isselectable(testname) = 1 and l.`client` like '%_PT' then 1 else 0 end as studentcontrol
      from tmp_langs l, tdscore_configs_testpackage.tds_testtooltype t
     where t.toolname = 'language' 
       and not exists (select * from tdscore_configs_testpackage.client_testtooltype c 
						where clientname = l.`client` and contexttype = 'TEST' 
						  and `context` = l.testname and c.toolname = 'language');

    -- add any language accommodation that did not already exist
    insert into tdscore_configs_testpackage.client_testtool (clientname, `context`, contexttype, `type`, `code`, `value`, valuedescription, isdefault, allowcombine)
    select distinct `client`, testname, 'test', 'language' , lang, descrip, concat(descrip, ' language test') valuedescription
         , case lang when 'enu' then 1 else 0 end as isdefault
		 , 0 as allowcombine
      from tmp_langs t
     where not exists (select * from tdscore_configs_testpackage.client_testtool 
                        where clientname = t.client and contexttype = 'test' and context = t.testname and code = lang and type = 'language');


    -- finally, update general language, grade, and subject for tds_configs. this is used to set user message translations specific to these parameters
    insert into tdscore_configs_testpackage.client_language (clientname, language, languagecode)
    select distinct `client`, descrip, lang 
	  from tmp_langs l
     where not exists (select * from tdscore_configs_testpackage.client_language c 
						where c.clientname = l.client and c.languagecode = l.lang);


--     if (@doall = 0) begin
--         update configsloaded set tdscore_configs_testpackage.updated = getdate()
--         from loader_itembank b
--         where configsloaded.configid = b.configid and _error is null;
--     end
--     else begin
--         update configsloaded set tdscore_configs_testpackage.updated = getdate ()
--         where clientname in
--         (select cname from tmp_clients) and _error is null;
--     end
--     commit

	-- clean-up
	drop temporary table tmp_langs;
	drop temporary table tmp_result;
	drop temporary table tmp_tests;
	drop temporary table tmp_segments;

 	drop temporary table tmp_langs2;
	drop temporary table tmp_tests2;
	drop temporary table tmp_segments2;

end $$

DELIMITER ;