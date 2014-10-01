DELIMITER $$

drop procedure if exists `loader_extractxml` $$

create procedure `loader_extractxml` (
/*
Description: This is the control procedure which is called to initiate the xml extraction process
			-- This procedure makes call to child procedures which are responsible for extracting subsets from the xml

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/18/2014		Sai V. 			--
*/
	v_xml  longtext
  , out v_testpackagekey varchar(350)
)
proc: begin

    declare errorexit condition for sqlstate '45000'; -- 45000 means "unhandled user-defined exception"

	-- declare variables
	declare v_root    varchar(100);
	declare v_path 	  varchar(100);
	declare v_testkey varchar(150);
-- 	declare v_testpackagekey varchar(350);
	
	declare v_testblueprintpath 	 varchar(300);
	declare v_testpackageproppath 	 varchar(300);
	declare v_testpoolpropertiespath varchar(300);
	declare v_testitempoolpath		 varchar(300);
	declare v_testformpath			 varchar(300);
	declare v_testscoringpath		 varchar(300);

	declare v_testformcount int;

	declare v_counter int default 1;

	-- This path has been seperated from the subpaths declared below to ensure flexibility during testing
-- 	set v_root = 'testpackage/';
-- 	set v_path = 'testpackage/complete/';

 	set v_root = 'testspecification/';
 	set v_path = 'testspecification/scoring/';
 
	set v_testkey = (extractvalue(v_xml, concat(v_root, 'identifier/attribute::uniqueid')));

	/* Before starting the loading/extraction process check if there is already another thread/process 
	** running with the same packagekey 
	
	** As of now, packagekey is same as the testkey, 
	** a different column in created to enable creation of a more unique way of identifying test packages (for e.g: by appending version number) in future, if needed.
	*/
	set v_testpackagekey = v_testkey;
 
	if exists (select * from loader_testpackage where packagekey = v_testpackagekey) then
		signal errorexit
			set message_text = 'A record with same packagekey value already exists in the loader_testpackage table.';
		-- leave proc;
	end if;

	
	-- ** extract testpackage configuration data ** --
	insert into loader_testpackage(packagekey, purpose, publisher, publishdate, packageversion, testkey, testname, testlabel, testversion)
	select v_testpackagekey
		 , extractvalue(v_xml, concat(v_root, 'attribute::purpose'))
		 , extractvalue(v_xml, concat(v_root, 'attribute::publisher'))
		 , extractvalue(v_xml, concat(v_root, 'attribute::publishdate'))
		 , extractvalue(v_xml, concat(v_root, 'attribute::version'))
		 , v_testkey
		 , extractvalue(v_xml, concat(v_root, 'identifier/attribute::name'))
		 , extractvalue(v_xml, concat(v_root, 'identifier/attribute::label'))
		 , extractvalue(v_xml, concat(v_root, 'identifier/attribute::version'))
	;

	-- ?? maybe need to add condition to check if the package purpose = "administration".....as we only care about that....see where to add it??
		
	-- ** extract testpackage property data ** --
	set v_testpackageproppath = concat(v_root, 'property');
	call loader_testpackageproperties(v_testpackagekey, v_xml, v_testpackageproppath);

	-- ** extract testblueprint attributes ** --
	set v_testblueprintpath = concat(v_path, 'testblueprint/bpelement');
	call loader_testblueprint(v_testpackagekey, v_xml, v_testblueprintpath);

	-- ** extract testpoolproperties attributes ** --
	set v_testpoolpropertiespath = concat(v_path, 'poolproperty');
	call loader_testpoolproperties(v_testpackagekey, v_xml, v_testpoolpropertiespath);

	-- ** extract testitempool attributes ** --
	set v_testitempoolpath = concat(v_path, 'itempool/');
	call loader_testitempool(v_testpackagekey, v_xml, v_testitempoolpath);

	-- ** extract testform attributes ** --
	-- there can be more than 1 <testform>, so have to extract data iteratively  
	set v_testformpath  = concat(v_path, 'testform');
	set v_testformcount = extractvalue(v_xml, concat('count(', v_testformpath, ')'));

	while v_counter <= v_testformcount do
		call loader_testforms(v_testpackagekey, v_xml, concat(v_testformpath, '[', v_counter, ']'));

		-- increment counter
		set v_counter = v_counter + 1;
	end while;

	-- ** extract scoring attributes ** --
	set v_testscoringpath = concat(v_path, 'scoringrules');
	call loader_testscoringrules(v_testpackagekey, v_xml, v_testscoringpath);


end $$

DELIMITER ;