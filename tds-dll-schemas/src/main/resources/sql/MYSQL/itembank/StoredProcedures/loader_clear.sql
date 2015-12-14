DELIMITER $$

drop procedure if exists `loader_clear` $$

create procedure `loader_clear` (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/23/2014		Sai V. 			Original Code.
*/
	v_packagekey varchar(350)
)
begin

	delete from `loader_measurementparameter`;
    
	delete from `loader_setofitemstrands` where _fk_package = v_packagekey;

	delete from `loader_itemscoredimension` where _fk_package = v_packagekey;
	delete from `loader_itemscoredimensionproperties` where _fk_package = v_packagekey;
	delete from `loader_segment` where _fk_package = v_packagekey;
 	delete from `loader_segmentblueprint` where _fk_package = v_packagekey;
	delete from `loader_segmentform` where _fk_package = v_packagekey;
	delete from `loader_segmentitemselectionproperties` where _fk_package = v_packagekey; 
	delete from `loader_segmentpool` where _fk_package = v_packagekey;
	delete from `loader_segmentpoolgroupitem` where _fk_package = v_packagekey;
	delete from `loader_segmentpoolpassageref` where _fk_package = v_packagekey;
	delete from `loader_testblueprint` where _fk_package = v_packagekey; 
	delete from `loader_testformgroupitems` where _fk_package = v_packagekey;
	delete from `loader_testformitemgroup` where _fk_package = v_packagekey;
	delete from `loader_testformpartition` where _fk_package = v_packagekey;
	delete from `loader_testformproperties` where _fk_package = v_packagekey;
	delete from `loader_testforms` where _fk_package = v_packagekey;
	delete from `loader_testitem` where _fk_package = v_packagekey;
	delete from `loader_testitemrefs` where _fk_package = v_packagekey;
	delete from `loader_testitempoolproperties` where _fk_package = v_packagekey;
	delete from `loader_testpackageproperties` where _fk_package = v_packagekey;
	delete from `loader_testpassages` where _fk_package = v_packagekey;
	delete from `loader_testpoolproperties` where _fk_package = v_packagekey;

	delete from `loader_testpackage` where packagekey = v_packagekey;

end $$

DELIMITER ;