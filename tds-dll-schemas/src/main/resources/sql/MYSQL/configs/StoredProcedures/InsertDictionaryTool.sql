-- This stored procedure inserts calculator tool data
DROP PROCEDURE IF EXISTS `InsertDictionaryTool`;
DELIMITER $$
CREATE PROCEDURE `InsertDictionaryTool`(v_clientname VARCHAR(100), v_segmentId VARCHAR(120), v_grade INT(11), v_issegment BIT)
BEGIN

	IF (v_issegment = 1) THEN
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
      v_clientname, 'Dictionary', b'1', b'0', 'TDSAcc-Dictionary', b'0', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
      'FairwayToolScript', 'FairwayToolScript', 'SEGMENT', v_segmentId, NULL, b'0', b'1', 'ALL'
    ), (
      v_clientname, 'Thesaurus', b'1', b'0', 'TDSAcc-Thesaurus', b'0', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
      'FairwayToolScript', 'FairwayToolScript', 'SEGMENT', v_segmentId, NULL, b'0', b'1', 'ALL'
    );
  ELSE
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
      v_clientname, 'Dictionary', b'1', b'0', 'TDSAcc-Dictionary', b'0', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
      'FairwayToolScript', 'FairwayToolScript', 'TEST', v_segmentId, NULL, b'0', b'1', 'ALL'
    ), (
      v_clientname, 'Thesaurus', b'1', b'0', 'TDSAcc-Thesaurus', b'0', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
      'FairwayToolScript', 'FairwayToolScript', 'TEST', v_segmentId, NULL, b'0', b'1', 'ALL'
    );
  END IF;

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
        v_clientname, 'Dictionary', 'TDS_Dict0', 'None', 0, 0, 'dictionary turned off',
        v_segmentId,-1,'FairwayToolScript','FairwayToolScript','SEGMENT','ALL', NULL
      ), (
        v_clientname, 'Dictionary', 'TDS_Dict_SD2', 'MerriamWebsters Elementary Dictionary', 1, 0, 'MerriamWebsters Elementary Dictionary with Audio for Grades 3-5',
        v_segmentId,2,'FairwayToolScript','FairwayToolScript','SEGMENT','ALL', NULL
      ), (
        v_clientname, 'Thesaurus', 'TDS_TH0', 'Off', 0, 0, 'Thesaurus turned off',
        v_segmentId,-1,'FairwayToolScript','FairwayToolScript','SEGMENT','ALL', NULL
      ), (
        v_clientname, 'Thesaurus', 'TDS_TH_TA', 'MerriamWebsters Collegiate Thesaurus', 1, 0, 'MW Thesaurus On',
        v_segmentId,2,'FairwayToolScript','FairwayToolScript','SEGMENT','ALL', NULL
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
        v_clientname, 'Dictionary', 'TDS_Dict0', 'None', 0, 0, 'dictionary turned off',
        v_segmentId,-1,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
      ), (
        v_clientname, 'Dictionary', 'TDS_Dict_SD2', 'MerriamWebsters Elementary Dictionary', 1, 0, 'MerriamWebsters Elementary Dictionary with Audio for Grades 3-5',
        v_segmentId,2,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
      ), (
        v_clientname, 'Thesaurus', 'TDS_TH0', 'Off', 0, 0, 'Thesaurus turned off',
        v_segmentId,-1,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
      ), (
        v_clientname, 'Thesaurus', 'TDS_TH_TA', 'MerriamWebsters Collegiate Thesaurus', 1, 0, 'MW Thesaurus On',
        v_segmentId,2,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
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
        v_clientname, 'Dictionary', 'TDS_Dict0', 'None', 0, 0, 'dictionary turned off',
        v_segmentId,-1,'FairwayToolScript','FairwayToolScript','SEGMENT','ALL', NULL
      ), (
        v_clientname, 'Dictionary', 'TDS_Dict_SD3', 'MerriamWebsters Intermediate Dictionary', 1, 0, 'MerriamWebsters Intermediate Dictionary with Audio for Grades 6-8',
        v_segmentId,3,'FairwayToolScript','FairwayToolScript','SEGMENT','ALL', NULL
      ), (
        v_clientname, 'Thesaurus', 'TDS_TH0', 'Off', 0, 0, 'Thesaurus turned off',
        v_segmentId,-1,'FairwayToolScript','FairwayToolScript','SEGMENT','ALL', NULL
      ), (
        v_clientname, 'Thesaurus', 'TDS_TH_TA', 'MerriamWebsters Collegiate Thesaurus', 1, 0, 'MW Thesaurus On',
        v_segmentId,2,'FairwayToolScript','FairwayToolScript','SEGMENT','ALL', NULL
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
        v_clientname, 'Dictionary', 'TDS_Dict0', 'None', 0, 0, 'dictionary turned off',
        v_segmentId,-1,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
      ), (
        v_clientname, 'Dictionary', 'TDS_Dict_SD3', 'MerriamWebsters Intermediate Dictionary', 1, 0, 'MerriamWebsters Intermediate Dictionary with Audio for Grades 6-8',
        v_segmentId,3,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
      ), (
        v_clientname, 'Thesaurus', 'TDS_TH0', 'Off', 0, 0, 'Thesaurus turned off',
        v_segmentId,-1,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
      ), (
        v_clientname, 'Thesaurus', 'TDS_TH_TA', 'MerriamWebsters Collegiate Thesaurus', 1, 0, 'MW Thesaurus On',
        v_segmentId,2,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
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
        v_clientname, 'Dictionary', 'TDS_Dict0', 'None', 0, 0, 'dictionary turned off',
        v_segmentId,-1,'FairwayToolScript','FairwayToolScript','SEGMENT','ALL', NULL
      ), (
        v_clientname, 'Dictionary', 'TDS_Dict_SD4', 'MerriamWebsters School Dictionary', 1, 0, 'MerriamWebsters School Dictionary with Audio for Grades 9-11',
        v_segmentId,3,'FairwayToolScript','FairwayToolScript','SEGMENT','ALL', NULL
      ), (
        v_clientname, 'Thesaurus', 'TDS_TH0', 'Off', 0, 0, 'Thesaurus turned off',
        v_segmentId,-1,'FairwayToolScript','FairwayToolScript','SEGMENT','ALL', NULL
      ), (
        v_clientname, 'Thesaurus', 'TDS_TH_TA', 'MerriamWebsters Collegiate Thesaurus', 1, 0, 'MW Thesaurus On',
        v_segmentId,2,'FairwayToolScript','FairwayToolScript','SEGMENT','ALL', NULL
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
        v_clientname, 'Dictionary', 'TDS_Dict0', 'None', 0, 0, 'dictionary turned off',
        v_segmentId,-1,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
      ), (
        v_clientname, 'Dictionary', 'TDS_Dict_SD4', 'MerriamWebsters School Dictionary', 1, 0, 'MerriamWebsters School Dictionary with Audio for Grades 9-11',
        v_segmentId,3,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
      ), (
        v_clientname, 'Thesaurus', 'TDS_TH0', 'Off', 0, 0, 'Thesaurus turned off',
        v_segmentId,-1,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
      ), (
        v_clientname, 'Thesaurus', 'TDS_TH_TA', 'MerriamWebsters Collegiate Thesaurus', 1, 0, 'MW Thesaurus On',
        v_segmentId,2,'FairwayToolScript','FairwayToolScript','TEST','ALL', NULL
      );
    END IF;
	END IF;

	DELETE FROM __accommodationcache;
END$$
DELIMITER ;
