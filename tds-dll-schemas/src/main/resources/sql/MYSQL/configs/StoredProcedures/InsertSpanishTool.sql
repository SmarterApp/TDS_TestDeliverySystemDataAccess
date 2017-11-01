-- This stored procedure inserts tool data that should be enabled for every Braille assessment
DROP PROCEDURE IF EXISTS `InsertSpanishTool`;
DELIMITER $$
CREATE PROCEDURE `InsertSpanishTool`(v_clientname VARCHAR(100), v_assessmentId VARCHAR(120))
BEGIN
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
		v_clientname, 'Language', 'ESN', 'Spanish', b'0', b'0', 'Spanish language test',
		v_assessmentId, 0, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	);

	DELETE FROM __accommodationcache;
END$$
DELIMITER ;