DELIMITER $$

drop procedure if exists `loader_segmentitemselectionproperties` $$

create procedure `loader_segmentitemselectionproperties` (
/*
Description: This procedure extracts data from 

Sample XML: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/18/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
  ,	v_xml  			 longtext
  , v_path 			 varchar(300)
  , v_segmentid 	 varchar(250)
  , v_itemselectorid varchar(200)
)
begin

	declare v_loop 	  	  int;
	declare v_counter 	  int default 1;
	declare v_bpelementid varchar(250);

	-- example: v_path = 'testspecification/complete/adminsegment[$i]/segmentblueprint/itemselectionparameter[$i]'    
	-- we need to extract this value only once
	set v_bpelementid = extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::bpelementid'));

	-- now get count of properties inside each <itemselectionparameter>
	set v_path = concat(v_path, '/property');
	set v_loop = extractvalue(v_xml, concat('count(', v_path, ')') );

	while v_counter <= v_loop do
		insert into loader_segmentitemselectionproperties(_fk_package, segmentid, itemselectorid, bpelementid, propname, propvalue, proplabel)
		select v_testpackagekey
			 , v_segmentid
			 , v_itemselectorid
			 , v_bpelementid
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::name'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::value'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::label'))
		;

		-- increment counter
		set v_counter = v_counter + 1;
	end while;

end $$

DELIMITER ;