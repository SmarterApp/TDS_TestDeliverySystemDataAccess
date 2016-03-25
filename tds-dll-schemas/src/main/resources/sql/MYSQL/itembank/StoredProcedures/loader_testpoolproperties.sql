DELIMITER $$

drop procedure if exists `loader_testpoolproperties` $$

create procedure `loader_testpoolproperties` (
/*
Description: This procedure extracts data from <poolproperty> 

Sample XML: <testblueprint>....</testblueprint>
			<poolproperty property="" value="" label="" itemcount=""/>
			:
			:
			<itempool>....</itempool>

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/18/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
  ,	v_xml  			 longtext
  , v_path 			 varchar(300)
)
begin
    
	declare v_loop 	  int;
	declare v_counter int default 1;

	-- example: v_path = 'testspecification/complete/poolproperty';
	set v_loop =  extractvalue(v_xml, concat('count(', v_path, ')') );

	while v_counter <= v_loop do
		insert into loader_testpoolproperties (_fk_package, propname, propvalue, proplabel, itemcount)
		select v_testpackagekey
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::property'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::value'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::label'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::itemcount'))
		;
		
		-- increment counter
		set v_counter = v_counter + 1;
   end while;


end $$

DELIMITER ;