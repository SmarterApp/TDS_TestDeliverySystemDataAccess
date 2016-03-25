DELIMITER $$

drop procedure if exists `loader_testformproperties` $$

create procedure `loader_testformproperties` (
/*
Description: This procedure extracts data from <testform> 

Sample XML: <testform>	
				<property name="" value="" label=""/>
				:
				<poolproperty property="" value="" label="" itemcount=""/>
				:
			</testform>

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/18/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
  ,	v_xml  		 longtext
  , v_path 		 varchar(300)
  , v_testformid varchar(200)
  , v_ispool 	 bit
)
begin
    
	declare v_loop 	  int;
	declare v_counter int default 1;

	-- example: v_path = 'testspecification/complete/testform[$i]/property' or 'testspecification/complete/testform[$i]/poolproperty';
	set v_loop =  extractvalue(v_xml, concat('count(', v_path, ')') );

	while v_counter <= v_loop do
		insert into loader_testformproperties (_fk_package, testformid, ispool, propname, proplabel, propvalue, itemcount)
		select v_testpackagekey
			 , v_testformid
             , v_ispool
			 -- when it is not a poolproperty, attribute 'name' is present instead of 'property'
			 , case when v_ispool = 0 
					then extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::name'))
					else extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::property'))
				end
             , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::label'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::value'))
			-- when it is not a poolproperty, attribute 'itemcount' does not exist 
			 , case when v_ispool = 0 
					then null	
					else extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::itemcount'))
				end
		;

		-- increment counter
		set v_counter = v_counter + 1;

	end while;


end $$

DELIMITER ;