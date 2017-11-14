-- This stored procedure inserts calculator tool data
DROP PROCEDURE IF EXISTS `InsertCalculatorTool`;
DELIMITER $$
CREATE PROCEDURE `InsertCalculatorTool`(v_clientname VARCHAR(100), v_segmentId VARCHAR(120), v_grade INT(11), v_issegment BIT)
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
		v_clientname, 'Calculator', b'1', b'0', 'TDSAcc-Calculator', b'0', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'SEGMENT', v_segmentId, 'Language', b'0', b'1', 'ALL'
	);

	IF (v_grade < 6) THEN
	  IF (v_issegment = 1) THEN
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
        v_clientname, 'Calculator', 'TDS_Calc0', 'None', 0, 0, 'Calculator feature is disabled',
        v_segmentId,-1,'FairwayToolScript','FairwayToolScript','SEGMENT','ALL', NULL
      ), (
        v_clientname, 'Calculator', 'TDS_CalcBasic', 'Basic', 1, 0, 'Basic Calculator is enabled',
        v_segmentId,1,'FairwayToolScript','FairwayToolScript','SEGMENT','ALL', NULL
      );
    ELSE
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
        v_clientname, 'Calculator', 'TDS_Calc0', 'None', 0, 0, 'Calculator feature is disabled',
        v_segmentId,-1,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
      ), (
        v_clientname, 'Calculator', 'TDS_CalcBasic', 'Basic', 1, 0, 'Basic Calculator is enabled',
        v_segmentId,1,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
      );
    END IF;
	ELSEIF (v_grade < 9) AND (v_grade >= 6) THEN
	  IF (v_issegment = 1) THEN
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
        v_clientname, 'Calculator', 'TDS_Calc0', 'None', 0, 0, 'Calculator feature is disabled',
        v_segmentId,-1,'FairwayToolScript','FairwayToolScript','SEGMENT','ALL', NULL
      ), (
        v_clientname, 'Calculator', 'TDS_CalcSciInv', 'ScientificInv', 1, 0, 'Scientific Calculator is enabled',
        v_segmentId,4,'FairwayToolScript','FairwayToolScript','SEGMENT','ALL', NULL
      );
    ELSE
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
        v_clientname, 'Calculator', 'TDS_Calc0', 'None', 0, 0, 'Calculator feature is disabled',
        v_segmentId,-1,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
      ), (
        v_clientname, 'Calculator', 'TDS_CalcSciInv', 'ScientificInv', 1, 0, 'Scientific Calculator is enabled',
        v_segmentId,4,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
      );
    END IF;
	ELSEIF (v_grade >= 9) THEN
	  IF (v_issegment = 1) THEN
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
        v_clientname, 'Calculator', 'TDS_Calc0', 'None', 0, 0, 'Calculator feature is disabled',
        v_segmentId,-1,'FairwayToolScript','FairwayToolScript','SEGMENT','ALL', NULL
      ), (
        v_clientname, 'Calculator', 'TDS_CalcSci&TDS_CalcGraphing&TDS_CalcRegress', 'Scientific&Graphing&Regressions', 1, 0, 'Regressions Calculator is enabled',
        v_segmentId,0,'FairwayToolScript','FairwayToolScript','SEGMENT','ALL', NULL
      );
    ELSE
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
        v_clientname, 'Calculator', 'TDS_Calc0', 'None', 0, 0, 'Calculator feature is disabled',
        v_segmentId,-1,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
      ), (
        v_clientname, 'Calculator', 'TDS_CalcSci&TDS_CalcGraphing&TDS_CalcRegress', 'Scientific&Graphing&Regressions', 1, 0, 'Regressions Calculator is enabled',
        v_segmentId,0,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
      );
    END IF;
	END IF;

	DELETE FROM __accommodationcache;
END$$
DELIMITER ;