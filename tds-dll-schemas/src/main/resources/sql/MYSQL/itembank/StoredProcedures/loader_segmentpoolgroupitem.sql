DELIMITER $$

drop procedure if exists `loader_segmentpoolgroupitem` $$

create procedure `loader_segmentpoolgroupitem` (
/*
Description: This procedure extracts data from 

Sample XML: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/18/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
  ,	v_xml  		  longtext
  , v_path 		  varchar(300)
  , v_segmentid   varchar(250)
  , v_itemgroupid varchar(100)
)
begin

	declare v_loop 	  int;
	declare v_counter int default 1;

	-- example: v_path = 'testspecification/complete/adminsegment[$i]/segmentpool/itemgroup[$j]/groupitem'    
	set v_loop = extractvalue(v_xml, concat('count(', v_path, ')') );

	while v_counter <= v_loop do		
		insert into loader_segmentpoolgroupitem(_fk_package, segmentid, itemgroupid, groupitemid, groupposition, adminrequired, responserequired, isfieldtest, isactive, blockid)
		select v_testpackagekey
			 , v_segmentid
			 , v_itemgroupid
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::itemid'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::groupposition'))
			 , case extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::adminrequired'))
					when 'true' then 1
					when 'false' then 0
			   end 
			 , case extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::responserequired'))
					when 'true' then 1
					when 'false' then 0
			   end 
			 , case extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::isfieldtest'))
					when 'true' then 1
					when 'false' then 0
			   end 
			 , case extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::isactive'))
					when 'true' then 1
					when 'false' then 0
			   end 
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::blockid'))
		;

		-- increment counter
		set v_counter = v_counter + 1;

 	end while;


end $$

DELIMITER ;