DELIMITER $$

drop procedure if exists `loader_testitempoolproperties` $$

create procedure `loader_testitempoolproperties` (
/*
Description: This procedure extracts data from <bpref> 

Sample XML: <testitem>
				<poolproperty property="" value="" label=""/>
			:
			:
			</testitem>

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/18/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
  ,	v_xml  		 longtext
  , v_path 		 varchar(300)
  , v_testitemid varchar(150)
)
begin
    
	declare v_loop 	  int;
	declare v_counter int default 1;

	-- set v_path = 'testspecification/complete/itempool/testitem/poolproperty';
	set v_loop =  extractvalue(v_xml, concat('count(', v_path, ')') );


	while v_counter <= v_loop do
		insert into loader_testitempoolproperties (_fk_package, testitemid, propname, propvalue, proplabel)
		select v_testpackagekey
			 , v_testitemid
             , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::property'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::value'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::label'))
		;
		
		-- increment counter
		set v_counter = v_counter + 1;

   end while;

end $$

DELIMITER ;