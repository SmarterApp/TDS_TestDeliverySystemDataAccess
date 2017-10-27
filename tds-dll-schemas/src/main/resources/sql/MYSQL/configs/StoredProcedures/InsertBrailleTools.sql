-- This stored procedure inserts tool data that should be enabled for every Braille assessment
DROP PROCEDURE IF EXISTS `InsertBrailleTools`;
CREATE PROCEDURE `InsertBrailleTools`(v_clientname VARCHAR(100), v_assessmentId VARCHAR(120))
BEGIN
	-- Braille Tools
	INSERT IGNORE INTO configs.client_testtooltype (
		clientname,
		toolname,
		allowchange,
		tideselectable,
		rtsfieldname,
		isrequired,
		tideselectablebysubject,
		isselectable,
		isvisible,
		studentcontrol,
		tooldescription,
		sortorder,
		dateentered,
		origin,
		source,
		contexttype,
		context,
		dependsontooltype,
		disableonguestsession,
		isfunctional,
		testmode
	) VALUES (
		v_clientname, 'Braille Type', b'1', b'1', 'TDSAcc-BrailleType', b'0', b'1', b'1', b'1', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, NULL, b'1', b'1', 'ALL'
	), (
		v_clientname, 'Emboss', b'1', b'1', 'TDSAcc-Emboss', b'0', b'1', b'1', b'1', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, 'Braille Type', b'0', b'1', 'ALL'
	), (
		v_clientname, 'Emboss Request Type', b'1', b'1', 'TDSAcc-EmbossRequestType', b'0', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, 'Braille Type', b'0', b'1', 'ALL'
	);

	INSERT IGNORE INTO configs.client_testtool (
		clientname,
		type,
		code,
		value,
		isdefault,
		allowcombine,
		valuedescription,
 		context,
		sortorder,
		origin,
		source,
		contexttype,
		testmode,
		equivalentclientcode
	) VALUES (
		v_clientname, 'Language', 'ENU-Braille', 'Braille', b'0', b'0', 'Braille English language test',
		v_assessmentId, 0, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), 	(
		v_clientname, 'Braille Type', 'TDS_BT0', 'Not Applicable', b'1', b'0', 'Not Applicable',
		v_assessmentId, -1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Braille Type', 'TDS_BT_ECN', 'EBAE - Contracted - Nemeth Math', b'0', b'0', 'EBAE - Contracted - Nemeth Math',
		v_assessmentId, 4, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname,'Braille Type','TDS_BT_ECT','EBAE - Contracted - UEB Math',0,0,'EBAE - Contracted - UEB Math',
		v_assessmentId, 6,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
	), (
		v_clientname,'Braille Type','TDS_BT_EXN','EBAE - Uncontracted - Nemeth Math',0,0,'EBAE - Uncontracted - Nemeth Math',
		v_assessmentId, 3,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
	), (
		v_clientname,'Braille Type','TDS_BT_EXT','EBAE - Uncontracted - UEB Math',0,0,'EBAE - Uncontracted - UEB Math',
		v_assessmentId,5,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
	),  (
		v_clientname,'Braille Type','TDS_BT_UCN','UEB - Contracted - Nemeth Math',0,0,'UEB - Contracted - Nemeth Math',
		v_assessmentId,10,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
	), (
		v_clientname,'Braille Type','TDS_BT_UCT','UEB - Contracted - UEB Math',0,0,'UEB - Contracted - UEB Math',
		v_assessmentId,9,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
	), (
		v_clientname,'Braille Type','TDS_BT_UXN','UEB - Uncontracted - Nemeth Math',0,0,'UEB - Uncontracted - Nemeth Math',
		v_assessmentId,7,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
	), (
		v_clientname,'Braille Type','TDS_BT_UXT','UEB - Uncontracted - UEB Math',0,0,'UEB - Uncontracted - UEB Math',
		v_assessmentId,8,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
	), (
		v_clientname, 'Emboss', 'TDS_Emboss0', 'None', b'1', b'0', 'Embossing is disabled',
		v_assessmentId, -1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Emboss', 'TDS_Emboss_Stim&TDS_Emboss_Item', 'Stimuli&Items', b'0', b'0', 'Emboss Items',
		v_assessmentId, 0, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Emboss Request Type', 'TDS_ERT0', 'Not Applicable', b'0', b'0', 'Not Applicable',
		v_assessmentId, -1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Emboss Request Type', 'TDS_ERT_OR', 'On-Request', b'0', b'0', 'On-Request',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Emboss Request Type', 'TDS_ERT_OR&TDS_ERT_Auto', 'Auto-Request', b'1', b'0', 'Auto-Request',
		v_assessmentId, 2, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	);

	DELETE FROM __accommodationcache;
END