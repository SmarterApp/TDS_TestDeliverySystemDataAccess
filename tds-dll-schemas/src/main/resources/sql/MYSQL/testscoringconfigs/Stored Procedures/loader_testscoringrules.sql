DELIMITER $$

drop procedure if exists `loader_testscoringrules` $$

create procedure `loader_testscoringrules` (
/*
Description: This procedure extracts data from <scoring> 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			6/18/2014		Sai V. 			--

*/
	v_testpackagekey varchar(350)
  ,	v_xml  		 	 longtext
  , v_path 		 	 varchar(300)
)
begin

	declare v_loop 	  int;
	declare v_counter int default 1;

	declare v_ruleid 						varchar(250);
	declare v_conversiontableid				varchar(250);
	declare v_computationruleparameterpath  varchar(300);
	declare v_computationrulepath			varchar(300);
	declare v_conversiontablepath			varchar(300);
	declare v_conversiontuplepath			varchar(300);

	-- example: v_path = 'testspecification/complete/scoring/scoringrules/computationrule';
	set v_computationrulepath = concat(v_path, '/computationrule');
	set v_loop =  extractvalue(v_xml, concat('count(', v_computationrulepath, ')') );


	while v_counter <= v_loop do
	begin

		set v_ruleid = extractvalue(v_xml, concat(v_computationrulepath, '[', v_counter, ']/identifier/attribute::uniqueid'));

		-- need to load score dimension data even when measurement data does not exist
		insert into loader_computationrule(_fk_package, bpelementid, computationorder, ruleid, rulename, rulelabel, version)
		select v_testpackagekey
			 , extractvalue(v_xml, concat(v_computationrulepath, '[', v_counter, ']/attribute::bpelementid'))
			 , extractvalue(v_xml, concat(v_computationrulepath, '[', v_counter, ']/attribute::computationorder'))
			 , v_ruleid
			 , extractvalue(v_xml, concat(v_computationrulepath, '[', v_counter, ']/identifier/attribute::name'))
		     , extractvalue(v_xml, concat(v_computationrulepath, '[', v_counter, ']/identifier/attribute::label'))
			 , extractvalue(v_xml, concat(v_computationrulepath, '[', v_counter, ']/identifier/attribute::version'))
		;
		
		set v_computationruleparameterpath = concat(v_computationrulepath, '[', v_counter, ']', '/computationruleparameter');
		call loader_computationruleparameter(v_testpackagekey, v_xml, v_computationruleparameterpath, v_ruleid);

		-- increment counter
		set v_counter = v_counter + 1;

	end;
	end while;


	-- re-set V_loop and v_counter values for re-use
	set v_loop    = 0;
	set v_counter = 1;
	

	set v_conversiontablepath = concat(v_path, '/conversiontable');
	set v_loop =  extractvalue(v_xml, concat('count(', v_conversiontablepath, ')') );

	while v_counter <= v_loop do
	begin

		set v_conversiontableid = extractvalue(v_xml, concat(v_conversiontablepath, '[', v_counter, ']/identifier/attribute::uniqueid'));

		-- need to load score dimension data even when measurement data does not exist
		insert into loader_conversiontable(_fk_package, conversiontableid, conversiontablename, version, measureid, purpose)
		select v_testpackagekey
			 , v_conversiontableid
			 , extractvalue(v_xml, concat(v_conversiontablepath, '[', v_counter, ']/identifier/attribute::name'))
			 , extractvalue(v_xml, concat(v_conversiontablepath, '[', v_counter, ']/identifier/attribute::version'))
			 , extractvalue(v_xml, concat(v_conversiontablepath, '[', v_counter, ']/attribute::measureid'))
			 , extractvalue(v_xml, concat(v_conversiontablepath, '[', v_counter, ']/attribute::purpose'))
		;
		
		set v_conversiontuplepath = concat(v_conversiontablepath, '[', v_counter, ']', '/conversiontuple');
		call loader_conversiontuple(v_testpackagekey, v_xml, v_conversiontuplepath, v_conversiontableid);

		-- increment counter
		set v_counter = v_counter + 1;

	end;
	end while;

end $$

DELIMITER ;