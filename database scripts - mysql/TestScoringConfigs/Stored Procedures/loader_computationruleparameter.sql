DELIMITER $$

drop procedure if exists `loader_computationruleparameter` $$

create procedure `loader_computationruleparameter` (
/*
Description: This procedure extracts data from <scoring> 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			6/18/2014		Sai V. 			--

*/
	v_testpackagekey varchar(350)
  ,	v_xml  		 	 longtext
  , v_path 		 	 varchar(300)
  , v_ruleid    	 varchar(250)
)
begin

	declare v_loop 	  int;
	declare v_counter int default 1;
	declare v_parameterid varchar(250);
	declare v_computationruleparametervaluepath varchar(300);

	-- example: v_path = 'testspecification/complete/scoring/scoringrules/computationrule[i]/computationruleparameter';
	set v_loop =  extractvalue(v_xml, concat('count(', v_path, ')') );


	while v_counter <= v_loop do
	begin

		set v_parameterid = extractvalue(v_xml, concat(v_path, '[', v_counter, ']/identifier/attribute::uniqueid'));

		-- need to load score dimension data even when measurement data does not exist
		insert into loader_computationruleparameter(_fk_package, ruleid, parametertype, position, parametername, version, parameterid, propname, propvalue)
		select v_testpackagekey
			 , v_ruleid
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::parametertype'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::position'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/identifier/attribute::name'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/identifier/attribute::version'))
			 , v_parameterid
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/property/attribute::name'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/property/attribute::value'))
		;

		set v_computationruleparametervaluepath = concat(v_path, '[', v_counter, ']', '/computationruleparametervalue');
		call loader_computationruleparametervalue(v_testpackagekey, v_xml, v_computationruleparametervaluepath, v_ruleid, v_parameterid);

		-- increment counter
		set v_counter = v_counter + 1;

	end;
	end while;


end $$

DELIMITER ;