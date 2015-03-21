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
    
	declare v_loop 	   		int;
	declare v_counter  		int default 1;
	declare v_strindex 		int default 2;
	declare v_testitemid 	varchar(150);
	declare v_testitemxml 	longtext;

	-- example: v_path = 'testspecification/complete/itempool/testitem';
	set v_loop =  extractvalue(v_xml, concat('count(', v_path, ')') );
	set v_path = 'testitem';

	while v_counter <= v_loop do

		if v_counter = 1 then -- first item
			set v_testitemxml = substring_index(substring_index(v_xml, '<testitem', v_strindex), '<itempool>', -1);
		elseif v_counter = v_loop then -- last item
			set v_testitemxml = substring_index(substring_index(substring_index(v_xml, '<testitem', v_strindex), '</testitem>', -2), '</itempool>', 1);
		else -- all inbetween items
			set v_testitemxml = substring_index(substring_index(v_xml, '<testitem', v_strindex), '</testitem>', -2);
		end if;

		-- perform a one time extarct of this value from the xml file
		set v_testitemid = (extractvalue(v_testitemxml, concat(v_path, '/identifier/attribute::uniqueid')));

		insert into loader_testitem (_fk_package, filename, itemtype, testitemid, testitemname, version)
		select v_testpackagekey
			 , extractvalue(v_testitemxml, concat(v_path, '/attribute::filename'))
             , extractvalue(v_testitemxml, concat(v_path, '/attribute::itemtype'))
			 , v_testitemid
             , extractvalue(v_testitemxml, concat(v_path, '/identifier/attribute::name'))
			 , extractvalue(v_testitemxml, concat(v_path, '/identifier/attribute::version'))
		;

		call loader_testitemrefs (v_testpackagekey, v_testitemxml, 'bp', concat(v_path, '/bpref'), v_testitemid);
		call loader_testitemrefs (v_testpackagekey, v_testitemxml, 'passage', concat(v_path, '/passageref'), v_testitemid);
		call loader_testitempoolproperties (v_testpackagekey, v_testitemxml, concat(v_path, '/poolproperty'), v_testitemid);
		call loader_itemscoredimension (v_testpackagekey, v_testitemxml, concat(v_path, '/itemscoredimension'), v_testitemid);

		-- increment counter
		set v_counter = v_counter + 1;
		set v_strindex = v_strindex + 1;

	end while;

end $$

DELIMITER ;