DELIMITER $$

drop procedure if exists `loader_testpassages` $$

create procedure `loader_testpassages` (
/*
Description: This procedure extracts data from <poolproperty> 

Sample XML: <passage filename="">
				<identifier uniqueid="" name="" version=""/>
			</passage>
			:
			:

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

	-- example: v_path = 'testspecification/complete/itempool/passage';
	set v_loop =  extractvalue(v_xml, concat('count(', v_path, ')') );

	while v_counter <= v_loop do
		insert into loader_testpassages (_fk_package, filename, passageid, passagename, version)
		select v_testpackagekey
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::filename'))
             , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/identifier/attribute::uniqueid'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/identifier/attribute::name'))
             , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/identifier/attribute::version'))
		;
		
		-- increment counter
		set v_counter = v_counter + 1;
   end while;


end $$

DELIMITER ;