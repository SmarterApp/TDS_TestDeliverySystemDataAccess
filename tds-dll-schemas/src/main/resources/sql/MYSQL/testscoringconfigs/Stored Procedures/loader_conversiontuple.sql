DELIMITER $$

drop procedure if exists `loader_conversiontuple` $$

create procedure `loader_conversiontuple` (
/*
Description: This procedure extracts data from <scoring> 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			6/18/2014		Sai V. 			--

*/
	v_testpackagekey varchar(350)
  ,	v_xml  		 	 longtext
  , v_path 		 	 varchar(300)
  , v_conversiontableid varchar(250)
)
begin

	declare v_loop 	  int;
	declare v_counter int default 1;

	-- example: v_path = 'testspecification/complete/scoring/scoringrules/conversiontable[i]/conversiontuple';
	set v_loop =  extractvalue(v_xml, concat('count(', v_path, ')') );

	while v_counter <= v_loop do
	begin

		-- need to load score dimension data even when measurement data does not exist
		insert into loader_conversiontuple(_fk_package, conversiontableid, invalue, outvalue)
		select v_testpackagekey
			 , v_conversiontableid
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::invalue'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::outvalue'))
		;

		-- increment counter
		set v_counter = v_counter + 1;

	end;
	end while;

end $$

DELIMITER ;