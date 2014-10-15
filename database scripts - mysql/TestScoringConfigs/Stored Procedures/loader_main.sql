DELIMITER $$

drop procedure if exists `loader_main` $$

create procedure `loader_main` (
/*
Description: Entry point for loading itembank tables from the testpackage file in xml format.

VERSION 	DATE 			AUTHOR 			COMMENTS
001			6/23/2014		Sai V. 			--
*/
	v_xml  longtext
)
begin

	-- declare variables
	declare v_testpackagekey varchar(350);
	declare v_clientname varchar(50);
	declare v_testadmin  varchar(250);
	declare v_year 		 varchar(50);
	declare v_season	 varchar(50);
 	declare v_subject    varchar(100);
	declare v_clientkey  bigint;
	declare v_version    int;


	/*** STEP 1: extract data from xml and load it into loader_* tables ***/
	call loader_extractxml(v_xml, v_testpackagekey /*output*/);


	/*** STEP 2: Prep the loader_* tables with missing/computed column data ***/	
	update loader_testpackage tp
	  join loader_testpackageproperties	tpp on tp.packagekey = tpp._fk_package and tpp.propname = 'subject'
	   set tp.subjectkey  = concat(tp.publisher, '-', tpp.propvalue)
		 , tp.subjectname = tpp.propvalue
	 where packagekey = v_testpackagekey;

	-- select * from loader_testpackage;
	-- select * from loader_testpackageproperties

	update loader_testblueprint
	   set treelevel = case when elementtype in ('strand', 'contentlevel') 
							then length(bpelementid) - length(replace(bpelementid, '|', '')) + 1
							else -1 
						end
	 where _fk_package = v_testpackagekey;


	/*** STEP 3: Validate data in loader_* tables ***/	
	-- 	call loader_validate(v_testpackagekey);


	/*** STEP 4: Process and load data into actual tables ***/	
	call load_test(v_testpackagekey);
	call load_testgrades(v_testpackagekey);
	call load_testscorefeature(v_testpackagekey);
	call load_computationruleparameter(v_testpackagekey);
	call load_computationruleparametervalue(v_testpackagekey);

	call load_conversiontabledesc(v_testpackagekey);
	call load_conversiontables(v_testpackagekey);
	
	call load_performancelevels(v_testpackagekey);

	/*** STEP 6: Clear loader_* tables ***/
	call loader_clear(v_testpackagekey);

end $$

DELIMITER ;