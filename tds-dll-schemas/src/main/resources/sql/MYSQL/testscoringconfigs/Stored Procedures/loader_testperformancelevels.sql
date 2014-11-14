DELIMITER $$

drop procedure if exists `loader_testperformancelevels` $$

create procedure `loader_testperformancelevels` (
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

	-- example: v_path = 'testspecification/scoring/performancelevels/performancelevel';
	set v_loop =  extractvalue(v_xml, concat('count(', v_path, ')') );


	while v_counter <= v_loop do
	begin

		-- need to load score dimension data even when measurement data does not exist
		insert into loader_performancelevels(_fk_package, bpelementid, plevel, scaledlo, scaledhi)
		select v_testpackagekey
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::bpelementid'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::plevel'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::scaledlo'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::scaledhi'))
		;
		
		-- increment counter
		set v_counter = v_counter + 1;

	end;
	end while;


end $$

DELIMITER ;