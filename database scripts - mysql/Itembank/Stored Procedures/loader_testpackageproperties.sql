DELIMITER $$

drop procedure if exists `loader_testpackageproperties` $$

create procedure `loader_testpackageproperties` (
/*
Description: A general-purpose element used especially to create extensibility of objects in the test package 

Sample XML: <property name="subject" value="Reading" label="Reading"/>
			<property name="grade" value="5" label="grade 5"/>

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/18/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
  ,	v_xml  	  		 longtext
  , v_path 	  		 varchar(300)
)
begin

	declare v_loop		int;
	declare v_counter	int default 1;

	-- example: v_path = 'testspecification/property'    
	set v_loop = extractvalue(v_xml, concat('count(', v_path, ')'));

	while v_counter <= v_loop do
		insert into loader_testpackageproperties (_fk_package, propname, propvalue, proplabel)
		select v_testpackagekey
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::name'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::value'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::label'))
		;

		-- increment counter
		set v_counter = v_counter + 1;
	end while;

end $$

DELIMITER ;