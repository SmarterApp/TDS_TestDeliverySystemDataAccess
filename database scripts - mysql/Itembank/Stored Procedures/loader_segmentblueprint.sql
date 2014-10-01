DELIMITER $$

drop procedure if exists `loader_segmentblueprint` $$

create procedure `loader_segmentblueprint` (
/*
Description: This procedure extracts data from 

Sample XML: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/18/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
  ,	v_xml  		longtext
  , v_path 		varchar(300)
  , v_segmentid varchar(250)
)
begin

	declare v_loop 	  int;
	declare v_counter int default 1;

	-- example: v_path = 'testspecification/complete/adminsegment[$i]/segmentblueprint/segmentbpelement'    
	set v_loop =  extractvalue(v_xml, concat('count(', v_path, ')') );

	while v_counter <= v_loop do
		insert into loader_segmentblueprint(_fk_package, segmentid, segmentbpelementid, minopitems, maxopitems)
		select v_testpackagekey
			 , v_segmentid
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::bpelementid'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::minopitems'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::maxopitems'))
		;

		-- increment counter
		set v_counter = v_counter + 1;
	end while;


end $$

DELIMITER ;