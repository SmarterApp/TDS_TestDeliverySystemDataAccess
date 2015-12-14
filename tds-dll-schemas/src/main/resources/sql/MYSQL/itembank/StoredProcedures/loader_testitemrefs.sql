DELIMITER $$

drop procedure if exists `loader_testitemrefs` $$

create procedure `loader_testitemrefs` (
/*
Description: This procedure extracts data from <bpref> 

Sample XML: <bpref> </bpref>
			:
			<passageref> </passageref>
			:

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/18/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
  ,	v_xml  		 longtext
  , v_reftype    varchar(100)
  , v_path 		 varchar(300)
  , v_testitemid varchar(150)
)
begin

	declare v_loop 	  int;
	declare v_counter int default 1;

	-- example: v_path = 'testspecification/complete/itempool/testitem/bpref';
	set v_loop = extractvalue(v_xml, concat('count(', v_path, ')'));

	while v_counter <= v_loop do
		-- select extractvalue(v_xml, '//bpref[$v_counter]')
		insert into loader_testitemrefs (_fk_package, testitemid, reftype, ref)
		select v_testpackagekey
			 , v_testitemid
			 , v_reftype
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']'));

		-- increment counter
		set v_counter = v_counter + 1;

	end while;

end $$

DELIMITER ;