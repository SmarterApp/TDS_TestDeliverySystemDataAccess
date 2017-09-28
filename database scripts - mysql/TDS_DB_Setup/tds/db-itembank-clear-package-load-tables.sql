-- ----------------------------------------------------------------------------------------------------------------------
-- Description:  Delete records from the load tables used by the itembank.loader_main stored procedure (the procedure
-- used to load administration packages into the TDS databases).  This script should be executed before attempting to
-- run loader_main() to insert an administration package.
--
-- Usage: Execute against the MySQL database server that hosts the itembank and configs databases.
-- ----------------------------------------------------------------------------------------------------------------------
USE itembank;
DELETE from loader_setofitemstrands;

DELETE from loader_itemscoredimension;
DELETE from loader_itemscoredimensionproperties;
DELETE from loader_segment;
DELETE from loader_segmentblueprint;
DELETE from loader_segmentform;
DELETE from loader_segmentitemselectionproperties;
DELETE from loader_segmentpool;
DELETE from loader_segmentpoolgroupitem;
DELETE from loader_segmentpoolpassageref;
DELETE from loader_testblueprint;
DELETE from loader_testformgroupitems;
DELETE from loader_testformitemgroup;
DELETE from loader_testformpartition;
DELETE from loader_testformproperties;
DELETE from loader_testforms;
DELETE from loader_testitem;
DELETE from loader_testitemrefs;
DELETE from loader_testitempoolproperties;
DELETE from loader_testpackageproperties;
DELETE from loader_testpassages;
DELETE from loader_testpoolproperties;

DELETE from loader_testpackage;
