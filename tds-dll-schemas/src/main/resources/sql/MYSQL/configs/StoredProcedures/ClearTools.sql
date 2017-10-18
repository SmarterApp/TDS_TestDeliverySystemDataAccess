-- This stored procedure inserts tool data that should be enabled for every Braille assessment
DROP PROCEDURE IF EXISTS `ClearTools`;
CREATE PROCEDURE `ClearTools`(v_clientname VARCHAR(100), v_assessmentId VARCHAR(120))
BEGIN
	-- Clear the current tools (aside from the Language/ENU tool, if it exists
	DELETE FROM configs.client_testtooltype
	WHERE context = v_assessmentId AND clientname = v_clientname AND toolname != 'Language' AND toolname != 'Print Size';
	DELETE FROM configs.client_testtool
	WHERE context = v_assessmentId AND clientname = v_clientname AND type != 'Print Size' AND code != 'ENU';
	DELETE FROM configs.client_tooldependencies
	WHERE context = v_assessmentId AND clientname = v_clientname AND thentype != 'Language';
	DELETE FROM __accommodationcache;
END