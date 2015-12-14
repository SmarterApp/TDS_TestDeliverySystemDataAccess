DELIMITER $$

drop procedure if exists `loader_segment` $$

create procedure `loader_segment` (
/*
Description: This procedure extracts data from <adminsegment> 

Sample XML: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/18/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
  ,	v_xml  	longtext
  , v_path 	varchar(300)
)
begin
	declare v_counter				int default 1;
	declare v_segmentid 			varchar(250);
	declare v_itemselectorid 		varchar(200);
	declare v_itemselectortype		varchar(50);
	declare v_segmentblueprintpath 	varchar(300);
	declare v_segmentblueprintcount int;
	
	-- example: v_path = 'testspecification/complete/adminsegment[$i]'    
	set v_segmentid 	   = extractvalue(v_xml, concat(v_path, '/attribute::segmentid'));
	set v_itemselectorid   = extractvalue(v_xml, concat(v_path, '/itemselector/identifier/attribute::uniqueid'));
	set v_itemselectortype = extractvalue(v_xml, concat(v_path, '/itemselector/attribute::type'));

	insert into loader_segment(_fk_package, segmentid, position, itemselection, itemselectortype, itemselectorid, itemselectorname, itemselectorlabel, version)
	select v_testpackagekey
		 , v_segmentid
		 , extractvalue(v_xml, concat(v_path, '/attribute::position'))
		 , extractvalue(v_xml, concat(v_path, '/attribute::itemselection'))
		 , v_itemselectortype
		 , v_itemselectorid
		 , extractvalue(v_xml, concat(v_path, '/itemselector/identifier/attribute::name'))
		 , extractvalue(v_xml, concat(v_path, '/itemselector/identifier/attribute::label'))
		 , extractvalue(v_xml, concat(v_path, '/itemselector/identifier/attribute::version'))
	;

	-- extract data from <segmentblueprint>
	set v_segmentblueprintpath = concat(v_path, '/segmentblueprint/segmentbpelement');
	call loader_segmentblueprint (v_testpackagekey, v_xml, v_segmentblueprintpath, v_segmentid);

	-- extract data from <itemselectionparameter>
	-- there is can be mulitple <itemselectionparameter> and within that there can be mutliple properties(<property>)
	-- making call to procedure loader_segmentitemselectionproperties to extract <property> attribtues within
	set v_segmentblueprintpath  = concat(v_path, '/itemselector/itemselectionparameter');
	set v_segmentblueprintcount = extractvalue(v_xml, concat('count(', v_segmentblueprintpath, ')'));

	while v_counter <= v_segmentblueprintcount do
		call loader_segmentitemselectionproperties(v_testpackagekey, v_xml, concat(v_segmentblueprintpath, '[', v_counter, ']'), v_segmentid, v_itemselectorid);

		-- increment counter
		set v_counter = v_counter + 1;
	end while;


	if (v_itemselectortype = 'Adaptive') then
		call loader_segmentpool(v_testpackagekey, v_xml, concat(v_path, '/segmentpool/itemgroup'), v_segmentid);
	end if;

	if (v_itemselectortype = 'fixedform') then
		call loader_segmentform(v_testpackagekey, v_xml, concat(v_path, '/segmentform'), v_segmentid);
	end if;


end $$

DELIMITER ;