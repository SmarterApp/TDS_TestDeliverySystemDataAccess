DELIMITER $$

drop procedure if exists `loader_testformgroupitems` $$

create procedure `loader_testformgroupitems` (
/*
Description: This procedure extracts data from <groupitem> 

Sample XML: <groupitem itemid="" formposition="" groupposition="" adminrequired="" responserequired="" isactive="" isfieldtest="" blockid=""/>

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/18/2014		Sai V. 			--
*/
	v_testpackagekey  varchar(350)
  ,	v_xml  		 	  longtext
  , v_path 		 	  varchar(300)
  , v_testformid 	  varchar(200)
  , v_formitemgroupid varchar(200)
)
begin
    
	declare v_loop 	  int;
	declare v_counter int default 1;

	-- example: v_path = 'testspecification/complete/testform[$i]/formpartition[$j]/itemgroup'
	set v_loop =  extractvalue(v_xml, concat('count(', v_path, ')') );

	while v_counter <= v_loop do
		insert into loader_testformgroupitems (_fk_package, testformid, formitemgroupid, itemid, formposition, groupposition, adminrequired, responserequired, isactive, isfieldtest, blockid)
		select v_testpackagekey
			 , v_testformid
			 , v_formitemgroupid
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::itemid'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::formposition'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::groupposition'))
			 , case extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::adminrequired'))
					when 'true' then 1
					when 'false' then 0
			   end 
			 , case extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::responserequired'))
					when 'true' then 1
					when 'false' then 0
			   end 
			 , case extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::isactive'))
					when 'true' then 1
					when 'false' then 0
			   end 
			 , case extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::isfieldtest'))
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