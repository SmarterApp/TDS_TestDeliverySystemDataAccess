DELIMITER $$

drop procedure if exists `loader_testitem` $$

create procedure `loader_testitem` (
/*
Description: This procedure extracts data from <testitem> 

Sample XML: <testitem filename="" itemtype="">
				<identifier uniqueid="" name="" version=""/>

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
	declare v_testitemid varchar(150);

	-- example: v_path = 'testspecification/complete/itempool/testitem';
	set v_loop =  extractvalue(v_xml, concat('count(', v_path, ')') );

	while v_counter <= v_loop do
		-- perform a one time extarct of this value from the xml file
		set v_testitemid = (extractvalue(v_xml, concat(v_path, '[', v_counter, ']/identifier/attribute::uniqueid')));

		insert into loader_testitem (_fk_package, filename, itemtype, testitemid, testitemname, version)
		select v_testpackagekey
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::filename'))
             , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::itemtype'))
			 , v_testitemid
             , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/identifier/attribute::name'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/identifier/attribute::version'))
		;

		call loader_testitemrefs (v_testpackagekey, v_xml, 'bp', concat(v_path, '[', v_counter, ']/bpref'), v_testitemid);
		call loader_testitemrefs (v_testpackagekey, v_xml, 'passage', concat(v_path, '[', v_counter, ']/passageref'), v_testitemid);
		call loader_testitempoolproperties (v_testpackagekey, v_xml, concat(v_path, '[', v_counter, ']/poolproperty'), v_testitemid);
		call loader_itemscoredimension (v_testpackagekey, v_xml, concat(v_path, '[', v_counter, ']/itemscoredimension'), v_testitemid);
	
		-- increment counter
		set v_counter = v_counter + 1;

	end while;


end $$

DELIMITER ;