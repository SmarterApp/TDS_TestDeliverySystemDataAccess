DELIMITER $$

drop procedure if exists `loader_testformpartition` $$

create procedure `loader_testformpartition` (
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
)
begin
    
	declare v_loop 	  int;
	declare v_counter int default 1;
	declare v_formpartitionid varchar(200);

	-- example: v_path = 'testspecification/complete/testform[$i]/formpartition'
	set v_loop =  extractvalue(v_xml, concat('count(', v_path, ')') );

	while v_counter <= v_loop do
		set v_formpartitionid = extractvalue(v_xml, concat(v_path, '[', v_counter, ']/identifier/attribute::uniqueid'));

		insert into loader_testformpartition (_fk_package, testformid, formpartitionid, formpartitionname, version)
		select v_testpackagekey
			 , v_testformid
			 , v_formpartitionid
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/identifier/attribute::name'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/identifier/attribute::version'))
		;

-- 		<itemgroup formposition="1" maxitems="ALL" maxresponses="0">
--           <identifier uniqueid="150-173:I-150-15869" name="150-173:I-150-15869" version="2747"/>
--           <groupitem itemid="150-15869" formposition="1" groupposition="1" adminrequired="true" responserequired="true" isactive="true" isfieldtest="false" blockid="A"/>
--         </itemgroup>
		-- call stored proc to extract <itemgroup>
		call loader_testformitemgroup(v_testpackagekey, v_xml, concat(v_path, '[', v_counter, ']/itemgroup'), v_testformid, v_formpartitionid);

		-- increment counter
		set v_counter = v_counter + 1;

	end while;


end $$

DELIMITER ;