DELIMITER $$

drop procedure if exists `loader_computationruleparametervalue` $$

create procedure `loader_computationruleparametervalue` (
/*
Description: This procedure extracts data from <scoring> 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			6/18/2014		Sai V. 			--

*/
	v_testpackagekey varchar(350)
  ,	v_xml  		 	 longtext
  , v_path 		 	 varchar(300)
  , v_ruleid    	 varchar(250)
  , v_parameterid 	 varchar(250)
)
begin

	declare v_loop 	  int;
	declare v_counter int default 1;

	-- example: v_path = 'testspecification/complete/scoring/scoringrules/computationrule[i]/computationruleparameter[i]/computationruleparametervalue';
	set v_loop =  extractvalue(v_xml, concat('count(', v_path, ')') );


	while v_counter <= v_loop do
	begin

		-- need to load score dimension data even when measurement data does not exist
		insert into loader_computationruleparametervalue(_fk_package, ruleid, parameterid, `index`, `value`)
		select v_testpackagekey
			 , v_ruleid
			 , v_parameterid
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::index'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::value'))
		;

		-- increment counter
		set v_counter = v_counter + 1;

	end;
	end while;

end $$

DELIMITER ;