create index `ix_loader_itemscoredimension`
	on `loader_itemscoredimension`(`_fk_package`(250));

create index `ix_loader_itemscoredimensionproperties`
	on `loader_itemscoredimensionproperties`(`_fk_package`(250));

create index `ix_loader_segmentblueprint`
	on `loader_segmentblueprint`(`_fk_package`(250));

create index `ix_loader_segmentform`
	on `loader_segmentform`(`_fk_package`(250));

create index `ix_loader_segmentitemselectionproperties`
	on `loader_segmentitemselectionproperties`(`_fk_package`(250));

create index `ix_loader_segmentpool`
	on `loader_segmentpool`(`_fk_package`(250));

create index `ix_loader_segmentpoolgroupitem`
	on `loader_segmentpoolgroupitem`(`_fk_package`(250));

create index `ix_loader_segmentpoolpassageref`
	on `loader_segmentpoolpassageref`(`_fk_package`(250));

create index `ix_loader_setofitemstrands`
	on `loader_setofitemstrands`(`_fk_package`(250));

create index `ix_loader_testblueprint`
	on `loader_testblueprint`(`_fk_package`(250));

create index `ix_loader_testformgroupitems`
	on `loader_testformgroupitems`(`_fk_package`(250));

create index `ix_loader_testformitemgroup`
	on `loader_testformitemgroup`(`_fk_package`(250));

create index `ix_loader_testformpartition`
	on `loader_testformpartition`(`_fk_package`(250));

create index `ix_loader_testformproperties`
	on `loader_testformproperties`(`_fk_package`(250));

create index `ix_loader_testforms`
	on `loader_testforms`(`_fk_package`(250));

create index `ix_loader_testitem`
	on `loader_testitem`(`_fk_package`(250));

create index `ix_loader_testitempoolproperties`
	on `loader_testitempoolproperties`(`_fk_package`(250));

create index `ix_loader_testitemrefs`
	on `loader_testitemrefs`(`_fk_package`(250));

create index `ix_loader_testpackage`
	on `loader_testpackage`(`packagekey`(250));

create index `ix_loader_testpackageproperties`
	on `loader_testpackageproperties`(`_fk_package`(250));

create index `ix_loader_testpassages`
	on `loader_testpassages`(`_fk_package`(250));

create index `ix_loader_testpoolproperties`
	on `loader_testpoolproperties`(`_fk_package`(250));


create index `ix_aaitemkey`
	on `aa_itemcl`(`_fk_item`);

create index `ix_adminitemgroup2`
	on `tblsetofadminitems`(`_fk_adminsubject`, `groupid`, `blockid`);

create index `ix_adminitem_ftitemgroup`
	on `tblsetofadminitems`(`_fk_adminsubject`, `isfieldtest`, `groupkey`);

create index `ix_adminstrand_test`
	on `tbladminstrand`(`_fk_adminsubject`);

create index `ix_adminsubjects_testid`
	on `tblsetofadminsubjects`(`testid`);

create index `ix_adminsubjects_virtualtest`
	on `tblsetofadminsubjects`(`virtualtest`);
/*
create index `ix_contentlevelkey`
	on `loader_contentlevels`(`clkey`, `testkey`);
*/

create unique index `ix_formitemposition`
	on `testformitem`(`_fk_testform`, `formposition`);

create unique index `ix_formitemunique`
	on `testformitem`(`_fk_testform`, `_fk_item`);

create index `ix_formitem_test`
	on `testformitem`(`_fk_adminsubject`);
/*
create index `ix_ldritemdim_key`
	on `loader_itemscoredimension`(`_itemkey`);

create index `ix_ldritemprops_key`
	on `loader_itemproperties`(`_itemkey`);

create index `ix_ldritems_key`
	on `loader_items`(`_itemkey`);

create index `ix_ldrstimulus`
	on `loader_stimuli`(`_stimuluskey`);

create index `ix_ldrtests_key`
	on `loader_tests`(`testkey`);

create index `ix_loadererrors`
	on `loader_errors`(`configid`, `test`);

create index `ix_loaderitemdim`
	on `loader_itemscoredimension`(`testname`, `itskey`);

create index `ix_logicaltests`
	on `loader_logicaltests`(`testkey`);
*/
create index `ix_scoredimensionitem`
	on `itemscoredimension`(`_fk_item`);

create index `ix_scoredim_test`
	on `itemscoredimension`(`_fk_adminsubject`);

create index `ix_testgrades_testid`
	on `setoftestgrades`(`testid`);
/*
create index `ix_ldritems_test`
	on `loader_items`(`_testkey`);
*/
create index `ix_tbladminstrand`
	on `tbladminstrand`(`loaderid`);

create unique index `ix_tblitem`
	on `tblitem`(`_efk_itembank`, `_efk_item`);

create index `ix_tblstimulus`
	on `tblstimulus`(`_efk_itembank`, `_efk_itskey`);

create index `ix_tblstrand`
	on `tblstrand`(`name`);

