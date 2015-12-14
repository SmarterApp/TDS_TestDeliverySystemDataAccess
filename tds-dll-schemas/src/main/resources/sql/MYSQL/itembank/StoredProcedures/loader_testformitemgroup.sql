DELIMITER $$

drop procedure if exists `loader_testformitemgroup` $$

create procedure `loader_testformitemgroup` (
/*
Description: This procedure extracts data from <itemgroup> 

Sample XML: 	<itemgroup formposition="" maxitems="" maxresponses="">
				  <identifier uniqueid="" name="" version=""/>

				</itemgroup>

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/18/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
  ,	v_xml  		 longtext
  , v_path 		 varchar(300)
  , v_testformid varchar(200)
  , v_formpartitionid varchar(200)
)
begin
    
	declare v_loop 	  int;
	declare v_counter int default 1;
	declare v_formitemgroupid varchar(200);

	-- example: v_path = 'testspecification/complete/testform[$i]/formpartition[$j]/itemgroup'
	set v_loop =  extractvalue(v_xml, concat('count(', v_path, ')') );

	while v_counter <= v_loop do
		set v_formitemgroupid = extractvalue(v_xml, concat(v_path, '[', v_counter, ']/identifier/attribute::uniqueid'));
		
		insert into loader_testformitemgroup(_fk_package, testformid, formpartitionid, formitemgroupid, formitemgroupname, version, formposition, maxitems, maxresponses, passageid)
		select v_testpackagekey
			 , v_testformid
			 , v_formpartitionid
			 , v_formitemgroupid
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/identifier/attribute::name'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/identifier/attribute::version'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::formposition'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::maxitems'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::maxresponses'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/passageref'))
		;

		-- extract <groupitem> thru this call
		-- possibility of <groupitem> being more that one, so implementing as a seperate procedure call
		call loader_testformgroupitems(v_testpackagekey, v_xml, concat(v_path, '[', v_counter, ']/groupitem'), v_testformid, v_formitemgroupid);

		-- increment counter
		set v_counter = v_counter + 1;

	end while;
-- 

end $$

DELIMITER ;