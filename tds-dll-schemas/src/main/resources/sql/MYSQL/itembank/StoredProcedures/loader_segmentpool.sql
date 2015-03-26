DELIMITER $$

drop procedure if exists `loader_segmentpool` $$

create procedure `loader_segmentpool` (
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
)
begin

	declare v_loop 	  	    int;
	declare v_counter 	    int default 1;
	declare v_itemgroupid   varchar(100);
	declare v_groupitempath varchar(300);

	-- example: v_path = 'testspecification/complete/adminsegment[$i]/segmentpool/itemgroup'    
	set v_loop = extractvalue(v_xml, concat('count(', v_path, ')') );

	while v_counter <= v_loop do
		set v_itemgroupid = extractvalue(v_xml, concat(v_path, '[', v_counter, ']/identifier/attribute::uniqueid'));
		
		insert into loader_segmentpool(_fk_package, segmentid, itemgroupid, itemgroupname, maxitems, maxresponses, version)
		select v_testpackagekey
			 , v_segmentid
			 , v_itemgroupid
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/identifier/attribute::name'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::maxitems'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::maxresponses'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/identifier/attribute::version'))
		;
		
		-- extract <passageref> data here
		-- assumption is there will be only one <passageref> per <itemgroup>
		insert into loader_segmentpoolpassageref (_fk_package, segmentid, itemgroupid, passageid)
		select v_testpackagekey
			 , v_segmentid
			 , v_itemgroupid
             , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/passageref'))
		;

		-- extract data from <groupitem>
		-- there is can be mulitple <groupitem> within each <itemgroup>
		-- making call to procedure loader_segmentpoolgroupitem which will loop thru <groupitem>
		set v_groupitempath = concat(v_path, '[', v_counter, ']/groupitem');
		call loader_segmentpoolgroupitem (v_testpackagekey, v_xml, v_groupitempath, v_segmentid, v_itemgroupid);
		
		-- increment counter
		set v_counter = v_counter + 1;

	end while;


end $$

DELIMITER ;