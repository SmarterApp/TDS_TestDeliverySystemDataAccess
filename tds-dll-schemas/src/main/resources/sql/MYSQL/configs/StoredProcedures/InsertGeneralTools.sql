-- This stored procedure inserts tool data that should be enabled for every loaded assessment

DROP PROCEDURE IF EXISTS `InsertGeneralTools`;
CREATE PROCEDURE `InsertGeneralTools`(v_clientname VARCHAR(100), v_assessmentId VARCHAR(120), v_soundCheck BIT(1))
BEGIN
	-- Clear the current tools
	DELETE FROM configs.client_testtooltype
	WHERE context = v_assessmentId AND clientname = v_clientname;
	DELETE FROM configs.client_testtool
	WHERE context = v_assessmentId AND clientname = v_clientname;
	DELETE FROM configs.client_tooldependencies
	WHERE context = v_assessmentId AND clientname = v_clientname;

	-- General tools
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
		v_clientname, 'Language', b'0', b'1', 'TDSAcc-Language', b'1', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, NULL, b'0', b'1', 'ALL'
	), (
		v_clientname, 'Print Size', b'1', b'1', 'TDSAcc-PrintSize', b'1', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, NULL, b'0', b'1', 'ALL'
	), (
		v_clientname, 'American Sign Language', b'1', b'1', 'TDSAcc-ASL', b'0', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, 'Language', b'0', b'1', 'ALL'
	), (
		v_clientname, 'Color Choices', b'1', b'1', 'TDSAcc-ColorChoices', b'0', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, 'Language', b'0', b'1', 'ALL'
	), (
		v_clientname, 'Highlight', b'1', b'0', 'TDSAcc-Highlight', b'0', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, 'Language', b'0', b'1', 'ALL'
	), (
		v_clientname, 'Mark for Review', b'1', b'0', 'TDSAcc-MarkForReview', b'0', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, 'Language', b'0', b'1', 'ALL'
	), (
		v_clientname, 'Masking', b'1', b'1', 'TDSAcc-Masking', b'0', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, 'Language', b'0', b'1', 'ALL'
	), (
		v_clientname, 'Strikethrough', b'1', b'0', 'TDSAcc-Strikethrough', b'0', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, 'Language', b'0', b'1', 'ALL'
	), (
		v_clientname, 'Expandable Passages', b'1', b'0', 'TDSAcc-ExpandablePassages', b'0', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, 'Language', b'0', b'1', 'ALL'
	), (
		v_clientname, 'Streamlined Mode', b'1', b'0', 'TDSAcc-EAM', b'0', b'1', b'1', b'1', b'0', NULL, 999, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, 'Language', b'0', b'1', 'ALL'
	), (
		v_clientname, 'Word List', b'1', b'1', 'TDSAcc-WordList', b'0', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, 'Language', b'0', b'1', 'ALL'
	), (
		v_clientname, 'Student Comments', b'1', b'0', 'TDSAcc-StudentComments', b'0', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, NULL, b'0', b'1', 'ALL'
	), (
		v_clientname, 'Global Notes', b'1', b'0', 'TDSAcc-GlobalNotes', b'0', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, NULL, b'0', b'1', 'ALL'
	), (
		v_clientname, 'Closed Captioning', b'1', b'1', 'TDSACC-NFCLOSEDCAP', b'0', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, 'Language', b'0', b'1', 'ALL'
	), (
		v_clientname, 'Print on Request', b'1', b'1', 'TDSAcc-PrintOnRequest', b'0', b'1', b'1', b'1', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, 'Language', b'1', b'1', 'ALL'
	), (
		v_clientname, 'Tutorial', b'1', b'0', 'TDSAcc-Tutorial', b'0', b'0', b'0', b'0', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, 'Language', b'0', b'1', 'ALL'
	), (
		v_clientname, 'Audio Playback Controls', b'1', b'0', 'TDSAcc-AudioPlaybackControls', b'0', b'0', b'0', b'0', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, NULL, b'0', b'1', 'ALL'
	), (
		v_clientname, 'Font Type', b'1', b'0', 'TDSAcc-FontType', b'0', b'0', b'0', b'0', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, NULL, b'0', b'1', 'ALL'
	), (
		v_clientname, 'Hardware Checks', b'1', b'0', 'TDSAcc-HWCheck', b'0', b'0', b'0', b'0', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, NULL, b'0', b'1', 'ALL'
	), (
		v_clientname, 'Item Font Size', b'1', b'0', 'TDSAcc-ItemFontSize', b'0', b'0', b'0', b'0', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, NULL, b'0', b'1', 'ALL'
	), (
		v_clientname, 'Item Tools Menu', b'1', b'0', 'TDSAcc-ITM', b'0', b'0', b'0', b'0', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, NULL, b'0', b'1', 'ALL'
	), (
		v_clientname, 'Mute System Volume', b'1', b'0', 'TDSAcc-Mute', b'0', b'0', b'0', b'0', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, 'Language', b'0', b'1', 'ALL'
	), (
		v_clientname, 'Non-Embedded Accommodations', b'1', b'1', 'TDSAcc-NonEmbedAcc', b'0', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, NULL, b'0', b'0', 'ALL'
	), (
		v_clientname, 'Non-Embedded Designated Supports', b'1', b'1', 'TDSAcc-DesigSup', b'0', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, NULL, b'0', b'0', 'ALL'
	), (
		v_clientname, 'Passage Font Size', b'1', b'0', 'TDSAcc-FontSize', b'0', b'0', b'0', b'0', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, NULL, b'0', b'1', 'ALL'
	), (
		v_clientname, 'Review Screen Layout', b'1', b'0', 'TDSAcc-RvScrn', b'0', b'0', b'0', b'0', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, NULL, b'0', b'1', 'ALL'
	), (
		v_clientname, 'System Volume Control', b'1', b'0', 'TDSAcc-SVC', b'0', b'0', b'0', b'0', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, NULL, b'0', b'1', 'ALL'
	), (
		v_clientname, 'Test Progress Indicator', b'1', b'0', 'TDSAcc-TPI', b'0', b'0', b'0', b'0', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, NULL, b'0', b'1', 'ALL'
	), (
		v_clientname, 'Test Shell', b'1', b'1', 'TDSAcc-TestShell', b'0', b'0', b'0', b'0', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, NULL, b'0', b'1', 'ALL'
	), (
		v_clientname, 'TTS', b'1', b'1', 'TDSAcc-TTS', b'0', b'0', b'1', b'1', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, 'Language', b'0', b'1', 'ALL'
	), (
		v_clientname, 'TTS Audio Adjustments', b'1', b'0', 'TDSAcc-TTSAdjust', b'0', b'0', b'0', b'0', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, 'Language', b'0', b'1', 'ALL'
	), (
		v_clientname, 'TTS Pausing', b'1', b'0', 'TDSAcc-TTSPausing', b'0', b'0', b'0', b'0', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, 'Language', b'0', b'1', 'ALL'
	), (
		v_clientname, 'TTX Business Rules', b'1', b'0', 'TDSAcc-TTXBusinessRules', b'0', b'0', b'0', b'0', b'0', NULL, 0, NOW(),
		'FairwayToolScript', 'FairwayToolScript', 'TEST', v_assessmentId, 'Language', b'0', b'1', 'ALL'
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
		v_clientname, 'American Sign Language', 'TDS_ASL0', 'Do not show ASL videos', b'1', b'0', 'Do not show ASL videos',
		v_assessmentId, -1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'American Sign Language', 'TDS_ASL1', 'Show ASL videos', b'0', b'0', 'Show ASL videos',
		v_assessmentId, 2, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), 	(
		v_clientname, 'Color Choices', 'TDS_CC0', 'Black on White', b'1', b'0', 'Black text with a White Background',
		v_assessmentId, -1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Color Choices', 'TDS_CCInvert', 'Reverse Contrast', b'0', b'0', 'White text with a Black background',
		v_assessmentId, 99, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Color Choices', 'TDS_CCMagenta', 'Black on Rose', b'0', b'0', 'Black on Rose',
		v_assessmentId, 8, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Color Choices', 'TDS_CCMedGrayLtGray', 'Medium Gray on Light Gray', b'0', b'0', 'Medium Gray on Light Gray',
		v_assessmentId, 17, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Color Choices', 'TDS_CCYellowB', 'Yellow on Blue', b'0', b'0', 'Yellow text with a Blue background',
		v_assessmentId, 14, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	),(
		v_clientname, 'Highlight', 'TDS_Highlight0', 'False', b'0', b'0', 'Highlight feature is disabled',
		v_assessmentId, -1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Highlight', 'TDS_Highlight1', 'True', b'1', b'0', 'Highlight  feature is enabled',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Language', 'ENU', 'English', b'1', b'0', 'English language test',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Mark for Review', 'TDS_MfR0', 'False', b'0', b'0', 'Mark for Review feature is disabled',
		v_assessmentId, -1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Mark for Review', 'TDS_MfR1', 'True', b'1', b'0', 'Mark for Review  feature is enabled',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	),	(
		v_clientname, 'Masking', 'TDS_Masking0', 'Masking Not Available', b'1', b'0', 'Masking is off',
		v_assessmentId, -1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Masking', 'TDS_Masking1', 'Masking Available', b'0', b'0', 'Masking is on',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Accommodations', 'NEA0', 'None', b'1', b'0', 'None',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Accommodations', 'NEA_Abacus', 'Abacus', b'0', b'1', 'Abacus Available',
		v_assessmentId, 2, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Accommodations', 'NEA_AR', 'Alternate Response Options', b'0', b'1', 'Alternate Response Options Available',
		v_assessmentId, 3, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Accommodations', 'NEA_Calc', 'Calculator', b'0', b'1', 'Calculator Available',
		v_assessmentId, 4, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Accommodations', 'NEA_MT', 'Multiplication Table', b'0', b'1', 'Multiplication Table',
		v_assessmentId, 5, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Accommodations', 'NEA_NoiseBuf', 'Noise Buffers', b'0', b'1', 'Noise Buffers Available',
		v_assessmentId, 9, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Accommodations', 'NEA_STT', 'Speech-to-Text', b'0', b'1', 'Speech-to-Text Available',
		v_assessmentId, 8, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Designated Supports', 'NEDS0', 'None', b'1', b'0', 'None',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Designated Supports', 'NEDS_CC', 'Color Contrast', b'0', b'1', 'Color Contrast Available',
		v_assessmentId, 3, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Designated Supports', 'NEDS_CO', 'Color Overlay', b'0', b'1', 'Color Overlay Available',
		v_assessmentId, 4, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Designated Supports', 'NEDS_Mag', 'Magnification', b'0', b'1', 'Magnification Available',
		v_assessmentId, 15, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Designated Supports', 'NEDS_RA_Items', 'Read Aloud Items', b'0', b'1', 'Read Aloud Items',
		v_assessmentId, 16, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Designated Supports', 'NEDS_RA_Stimuli', 'Read Aloud Stimuli', b'0', b'1', 'Read Aloud Stimuli',
		v_assessmentId, 17, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Designated Supports', 'NEDS_SC_Items', 'Scribe Items (Non-Writing)', b'0', b'1', 'Scribe Items',
		v_assessmentId, 18, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Designated Supports', 'NEDS_SS', 'Separate Setting', b'0', b'1', 'Separate Setting Available',
		v_assessmentId, 19, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Designated Supports', 'NEDS_TArabic', 'Glossary - Arabic', b'0', b'1', 'Glossary - Arabic',
		v_assessmentId, 6, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Designated Supports', 'NEDS_TCantonese', 'Glossary - Cantonese', b'0', b'1', 'Glossary - Cantonese',
		v_assessmentId, 7, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Designated Supports', 'NEDS_TFilipino', 'Glossary - Filipino', b'0', b'1', 'Glossary - Filipino',
		v_assessmentId, 8, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Designated Supports', 'NEDS_TKorean', 'Glossary - Korean', b'0', b'1', 'Glossary - Korean',
		v_assessmentId, 9, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Designated Supports', 'NEDS_TMandarin', 'Glossary - Mandarin', b'0', b'1', 'Glossary - Mandarin',
		v_assessmentId, 10, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Designated Supports', 'NEDS_TPunjabi', 'Glossary - Punjabi', b'0', b'1', 'Glossary - Punjabi',
		v_assessmentId, 11, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Designated Supports', 'NEDS_TransDirs', 'Translated Test Directions', b'0', b'1', 'Translated Test Directions Available',
		v_assessmentId, 20, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Designated Supports', 'NEDS_TRussian', 'Glossary - Russian', b'0', b'1', 'Glossary - Russian',
		v_assessmentId, 12, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Designated Supports', 'NEDS_TSpanish', 'Glossary - Spanish', b'0', b'1', 'Glossary - Spanish',
		v_assessmentId, 5, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Designated Supports', 'NEDS_TUkrainian', 'Glossary - Ukrainian', b'0', b'1', 'Glossary - Ukrainian',
		v_assessmentId, 13, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Non-Embedded Designated Supports', 'NEDS_TVietnamese', 'Glossary - Vietnamese', b'0', b'1', 'Glossary - Vietnamese',
		v_assessmentId, 14, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Strikethrough', 'TDS_ST0', 'False', b'0', b'0', 'Strikethrough feature is disabled',
		v_assessmentId, -1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Strikethrough', 'TDS_ST1', 'True', b'1', b'0', 'Strikethrough  feature is enabled',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Expandable Passages', 'TDS_ExpandablePassages0', 'Expandable Passages Off', b'0', b'0', 'No expanding passages',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Expandable Passages', 'TDS_ExpandablePassages1', 'Expandable Passages On', b'1', b'0', 'Allow student to expand or collapse passages',
		v_assessmentId, 2, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Print Size', 'TDS_PS_L0', 'No default zoom applied', b'1', b'0', 'There is no default zoom',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	),  (
		v_clientname, 'Print Size', 'TDS_PS_L1', 'Level 1', b'0', b'0', 'There is no default zoom',
		v_assessmentId, 2, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	),  (
		v_clientname, 'Print Size', 'TDS_PS_L2', 'Level 2', b'0', b'0', 'There is no default zoom',
		v_assessmentId, 3, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	),  (
		v_clientname, 'Print Size', 'TDS_PS_L3', 'Level 3', b'0', b'0', 'There is no default zoom',
		v_assessmentId, 4, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	),  (
		v_clientname, 'Print Size', 'TDS_PS_L4', 'Level 4', b'0', b'0', 'There is no default zoom',
		v_assessmentId, 5, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Review Screen Layout', 'TDS_RSL_ListView', 'List', b'1', b'0', 'List',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Streamlined Mode', 'TDS_SLM0', 'Off', b'1', b'0', 'gen pop and accessibility in one shell',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Streamlined Mode', 'TDS_SLM1', 'On', b'0', b'0', 'gen pop and accessibility in one shell',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Student Comments', 'TDS_SC0', 'Off', b'0', b'0', 'Digital Notepad is turned off',
		v_assessmentId, 99, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Student Comments', 'TDS_SCNotepad', 'On', b'1', b'0', 'Digital Notepad is turned on',
		v_assessmentId, 2, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'System Volume Control', 'TDS_SVC1', 'Show widget', b'1', b'0', 'allow student to change the volume',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Test Progress Indicator', 'TDS_TPI_ResponsesFix', 'Show indicator as a fraction and adjust to test length', b'1', b'0', 'Show indicator as a fraction and adjust to test length',
		v_assessmentId, 2, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Test Shell', 'TDS_TS_Modern', 'Modern skin', b'1', b'0', 'gen pop and accessibility in one shell',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'TTS', 'TDS_TTS0', 'None', b'1', b'0', 'TTS feature is disabled',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'TTS', 'TDS_TTS_Item', 'Items', b'0', b'0', 'Text-to-speech is enabled on items',
		v_assessmentId, 2, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
    v_clientname, 'TTS', 'TDS_TTS_Stim', 'Stimuli', b'0', b'0', 'Text-to-speech is enabled on stimuli',
    v_assessmentId, 3, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
  ), (
    v_clientname, 'TTS', 'TDS_TTS_Stim&TDS_TTS_Item', 'Stimuli&Items', b'0', b'0', 'Text-to-speech is enabled on items',
    v_assessmentId, 4, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
  ), (
    v_clientname, 'TTS Audio Adjustments', 'TDS_TTSAA0', 'TTS Audio Adjustments Off', b'0', b'0', 'TTS Audio Adjustments Off',
    v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
  ), (
    v_clientname, 'TTS Audio Adjustments', 'TDS_TTSAA_Volume&TDS_TTSAA_Pitch&TDS_TTSAA_Rate&TDS_TTSAA_SelectVP', 'All Options', b'1', b'0', 'All Options',
    v_assessmentId, 0, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'TTS Pausing', 'TDS_TTSPause0', 'TTS Pausing Off', b'0', b'0', 'TTS Pausing Off',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'TTS Pausing', 'TDS_TTSPause1', 'TTS Pausing On', b'1', b'0', 'TTS Pausing On',
		v_assessmentId, 2, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'TTX Business Rules', 'TDS_TTX_A203', 'A203', b'0', b'0', 'A203 - For TTS',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'TTX Business Rules', 'TDS_TTX_A206', 'A206', b'1', b'0', 'A206 - For Braille',
		v_assessmentId, 2, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Tutorial', 'TDS_T0', 'False', b'0', b'0', 'Tutorial feature is disabled',
		v_assessmentId, -1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Tutorial', 'TDS_T1', 'True', b'1', b'0', 'Tutorial  feature is enabled',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Word List', 'TDS_WL0', 'No Glossary', b'0', b'0', 'No Word List Available',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
	 	v_clientname, 'Word List', 'TDS_WL_ArabicGloss', 'Arabic Glossary', b'0', b'0', 'Arabic Glossary is Available',
	 	v_assessmentId, 3, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
 	), (
 		v_clientname, 'Word List', 'TDS_WL_ArabicGloss&TDS_WL_Glossary', 'Arabic & English Glossary', b'0', b'0', 'Arabic Glossary is Available',
 		v_assessmentId, 13, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Word List', 'TDS_WL_CantoneseGloss', 'Cantonese Glossary', b'0', b'0', 'Cantonese Glossary is Available',
		v_assessmentId, 4, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Word List', 'TDS_WL_CantoneseGloss&TDS_WL_Glossary', 'Cantonese & English Glossary', b'0', b'0', 'Cantonese Glossary is Available',
		v_assessmentId, 14, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Word List', 'TDS_WL_ESNGlossary', 'Spanish Glossary', b'0', b'0', 'Spanish Glossary is Available',
		v_assessmentId, 10, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Word List', 'TDS_WL_ESNGlossary&TDS_WL_Glossary', 'Spanish & English Glossary', b'0', b'0', 'Spanish Glossary is Available',
		v_assessmentId, 20, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Word List', 'TDS_WL_Glossary', 'English Glossary', b'1', b'0', 'Glossary is Available',
		v_assessmentId, 2, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Word List', 'TDS_WL_KoreanGloss', 'Korean Glossary', b'0', b'0', 'Korean Glossary is Available',
		v_assessmentId, 6, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Word List', 'TDS_WL_KoreanGloss&TDS_WL_Glossary', 'Korean & English Glossary', b'0', b'0', 'Korean Glossary is Available',
		v_assessmentId, 16, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Word List', 'TDS_WL_MandarinGloss', 'Mandarin Glossary', b'0', b'0', 'Mandarin Glossary is Available',
		v_assessmentId, 7, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Word List', 'TDS_WL_MandarinGloss&TDS_WL_Glossary', 'Mandarin & English Glossary', b'0', b'0', 'Mandarin Glossary is Available',
		v_assessmentId, 17, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Word List', 'TDS_WL_PunjabiGloss', 'Punjabi Glossary', b'0', b'0', 'Punjabi Glossary is Available',
		v_assessmentId, 8, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Word List', 'TDS_WL_PunjabiGloss&TDS_WL_Glossary', 'Punjabi & English Glossary', b'0', b'0', 'Punjabi Glossary is Available',
		v_assessmentId, 18, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Word List', 'TDS_WL_RussianGloss', 'Russian Glossary', b'0', b'0', 'Russian Glossary is Available',
		v_assessmentId, 9, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Word List', 'TDS_WL_RussianGloss&TDS_WL_Glossary', 'Russian & English Glossary', b'0', b'0', 'Russian Glossary is Available',
		v_assessmentId, 19, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Word List', 'TDS_WL_TagalGloss', 'Filipino Glossary', b'0', b'0', 'Tagal Glossary is Available',
		v_assessmentId, 5, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Word List', 'TDS_WL_TagalGloss&TDS_WL_Glossary', 'Filipino & English Glossary', b'0', b'0', 'Tagal Glossary is Available',
		v_assessmentId, 15, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Word List', 'TDS_WL_UkrainianGloss', 'Ukrainian Glossary', b'0', b'0', 'Ukrainian Glossary is Available',
		v_assessmentId, 11, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Word List', 'TDS_WL_UkrainianGloss&TDS_WL_Glossary', 'Ukrainian & English Glossary', b'0', b'0', 'Ukrainian Glossary is Available',
		v_assessmentId, 21, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Word List', 'TDS_WL_VietnameseGloss', 'Vietnamese Glossary ', b'0', b'0', 'Vietnamese Glossary is Available',
		v_assessmentId, 12, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Word List', 'TDS_WL_VietnameseGloss&TDS_WL_Glossary', 'Vietnamese & English Glossary', b'0', b'0', 'Vietnamese Glossary is Available',
		v_assessmentId, 22, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Passage Font Size', 'TDS_F_S14', '14pt', b'1', b'0', 'Font Size is set to 14pt',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Mute System Volume', 'TDS_Mute0', 'Not Muted', b'1', b'0', 'System is NOT muted',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Mute System Volume', 'TDS_Mute1', 'Muted', b'0', b'0', 'System IS muted',
		v_assessmentId, 2, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Item Font Size', 'TDS_IF_S14', '14pt Items', b'1', b'0', 'Item Font Size is set to 14pt',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Item Tools Menu', 'TDS_ITM1', 'On', b'1', b'0', 'gen pop and accessibility in one shell',
		v_assessmentId, 0, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Font Type', 'TDS_FT_Verdana', 'Verdana', b'1', b'0', 'Font Type is set to Verdana',
		v_assessmentId, 2, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), 	(
		v_clientname, 'Audio Playback Controls', 'TDS_APC_PSP', 'Play Stop and Pause', b'1', b'0', 'Play Stop Pause and rewind. Two buttons.',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Print on Request', 'TDS_PoD0', 'None', b'1', b'0', 'Print on Request feature is disabled',
		v_assessmentId, -1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Print on Request', 'TDS_PoD_Item', 'Items', b'0', b'0', 'Print on Request enabled for items',
		v_assessmentId, 2, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Print on Request', 'TDS_PoD_Stim', 'Stimuli', b'0', b'0', 'Print on Request enabled for stimuli',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Print on Request', 'TDS_PoD_Stim&TDS_PoD_Item', 'Stimuli&Items', b'0', b'0', 'Print on Request enabled for items',
		v_assessmentId, 0, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Global Notes', 'TDS_GN0', 'Off', b'0', b'0', 'Global Notes Off',
		v_assessmentId, -1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Global Notes', 'TDS_GN1', 'On', b'1', b'0', 'Global Notes On',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Closed Captioning', 'TDS_ClosedCap0', 'Closed Captioning Not Available', b'1', b'0', 'Closed Captioning is off',
		v_assessmentId, -1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	), (
		v_clientname, 'Closed Captioning', 'TDS_ClosedCap1', 'Closed Captioning Available', b'0', b'0', 'Closed Captioning is on',
		v_assessmentId, 1, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
	);

  IF (v_soundCheck = 1) THEN
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
      v_clientname, 'Hardware Checks', 'TDS_HWPlayback', 'Check Playback Capabilities', b'1', b'0', 'Check Playback Capabilities',
      v_assessmentId, 3, 'FairwayToolScript', 'FairwayToolScript', 'TEST', 'ALL', NULL
    );
  END IF;

	INSERT IGNORE INTO configs.client_tooldependencies (
		context,
		contexttype,
		iftype,
		ifvalue,
		isdefault,
		thentype,
		thenvalue,
 		clientname,
		_key,
		testmode
	) VALUES 	(
		v_assessmentId, 'TEST', 'Language', 'ENU-Braille', b'0', 'Mark for Review', 'TDS_MfR0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU-Braille', b'1', 'Print on Request', 'TDS_PoD0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'0', 'Strikethrough', 'TDS_ST0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU-Braille', b'0', 'Emboss Request Type', 'TDS_ERT_OR', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Word List', 'TDS_WL_UkrainianGloss', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'1', 'Color Choices', 'TDS_CC0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'1', 'Streamlined Mode', 'TDS_SLM0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU-Braille', b'1', 'Color Choices', 'TDS_CC0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU-Braille', b'1', 'Highlight', 'TDS_Highlight0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Word List', 'TDS_WL_PunjabiGloss', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'1', 'TTX Business Rules', 'TDS_TTX_A203', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Word List', 'TDS_WL_VietnameseGloss&TDS_WL_Glossary', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Word List', 'TDS_WL_TagalGloss', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU-Braille', b'1', 'Expandable Passages', 'TDS_ExpandablePassages0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'0', 'Print on Request', 'TDS_PoD_Stim', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Word List', 'TDS_WL_CantoneseGloss&TDS_WL_Glossary', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'1', 'Masking', 'TDS_Masking0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Word List', 'TDS_WL_ESNGlossary', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'0', 'Streamlined Mode', 'TDS_SLM1', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Word List', 'TDS_WL0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'1', 'Expandable Passages', 'TDS_ExpandablePassages1', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'1', 'Tutorial', 'TDS_T1', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'1', 'Word List', 'TDS_WL_Glossary', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'1', 'Highlight', 'TDS_Highlight1', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Word List', 'TDS_WL_ArabicGloss', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Word List', 'TDS_WL_MandarinGloss', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'1', 'TTS Pausing', 'TDS_TTSPause1', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Color Choices', 'TDS_CCYellowB', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'1', 'American Sign Language', 'TDS_ASL0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'1', 'TTS', 'TDS_TTS0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'0', 'Mark for Review', 'TDS_MfR0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'1', 'Masking', 'TDS_Masking0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'0', 'Color Choices', 'TDS_CCMagenta', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Highlight', 'TDS_Highlight0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Color Choices', 'TDS_CCMagenta', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'0', 'Color Choices', 'TDS_CCMedGrayLtGray', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Word List', 'TDS_WL_MandarinGloss&TDS_WL_Glossary', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'1', 'Streamlined Mode', 'TDS_SLM0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU-Braille', b'1', 'Strikethrough', 'TDS_ST0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Masking', 'TDS_Masking1', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Expandable Passages', 'TDS_ExpandablePassages0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Word List', 'TDS_WL_UkrainianGloss&TDS_WL_Glossary', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Word List', 'TDS_WL_RussianGloss&TDS_WL_Glossary', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Color Choices', 'TDS_CCInvert', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Word List', 'TDS_WL_TagalGloss&TDS_WL_Glossary', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'1', 'Expandable Passages', 'TDS_ExpandablePassages1', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'All'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'1', 'TTS', 'TDS_TTS0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'American Sign Language', 'TDS_ASL1', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Word List', 'TDS_WL_PunjabiGloss&TDS_WL_Glossary', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'1', 'TTS Audio Adjustments', 'TDS_TTSAA_Volume&TDS_TTSAA_Pitch&TDS_TTSAA_Rate&TDS_TTSAA_SelectVP', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'TTS', 'TDS_TTS_Stim', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Word List', 'TDS_WL_VietnameseGloss', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Color Choices', 'TDS_CCMedGrayLtGray', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'0', 'Word List', 'TDS_WL_Glossary', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Word List', 'TDS_WL_ESNGlossary&TDS_WL_Glossary', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'0', 'Expandable Passages', 'TDS_ExpandablePassages0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'0', 'Print on Request', 'TDS_PoD_Stim&TDS_PoD_Item', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU-Braille', b'1', 'American Sign Language', 'TDS_ASL0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'0', 'American Sign Language', 'TDS_ASL1', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'0', 'Highlight', 'TDS_Highlight0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Streamlined Mode', 'TDS_SLM1', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'0', 'Color Choices', 'TDS_CCInvert', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU-Braille', b'1', 'Emboss', 'TDS_Emboss_Stim&TDS_Emboss_Item', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'1', 'TTS Audio Adjustments', 'TDS_TTSAA_Volume&TDS_TTSAA_Pitch&TDS_TTSAA_Rate&TDS_TTSAA_SelectVP', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'1', 'American Sign Language', 'TDS_ASL0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'1', 'Highlight', 'TDS_Highlight1', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'1', 'Strikethrough', 'TDS_ST1', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'TTS', 'TDS_TTS_Stim&TDS_TTS_Item', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'TTS', 'TDS_TTS_Item', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'1', 'Print on Request', 'TDS_PoD0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'1', 'Color Choices', 'TDS_CC0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU-Braille', b'1', 'Streamlined Mode', 'TDS_SLM1', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU-Braille', b'1', 'TTX Business Rules', 'TDS_TTX_A206', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Mark for Review', 'TDS_MfR0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'1', 'Mark for Review', 'TDS_MfR1', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU-Braille', b'1', 'Tutorial', 'TDS_T0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'1', 'Print on Request', 'TDS_PoD0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'1', 'Word List', 'TDS_WL0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'1', 'TTS Pausing', 'TDS_TTSPause1', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Print on Request', 'TDS_PoD_Stim&TDS_PoD_Item', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Word List', 'TDS_WL_KoreanGloss', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Word List', 'TDS_WL_CantoneseGloss', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Print on Request', 'TDS_PoD_Item', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'0', 'Masking', 'TDS_Masking1', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU-Braille', b'1', 'Masking', 'TDS_Masking0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Word List', 'TDS_WL_KoreanGloss&TDS_WL_Glossary', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU-Braille', b'1', 'TTS Pausing', 'TDS_TTSPause0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'0', 'Print on Request', 'TDS_PoD_Item', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'1', 'Mark for Review', 'TDS_MfR1', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'1', 'TTX Business Rules', 'TDS_TTX_A203', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'0', 'Color Choices', 'TDS_CCYellowB', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Strikethrough', 'TDS_ST0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Print on Request', 'TDS_PoD_Stim', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU-Braille', b'1', 'TTS Audio Adjustments', 'TDS_TTSAA0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'1', 'Strikethrough', 'TDS_ST1', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Word List', 'TDS_WL_ArabicGloss&TDS_WL_Glossary', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ESN', b'1', 'Tutorial', 'TDS_T1', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Word List', 'TDS_WL_RussianGloss', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL' ),
  (
		v_assessmentId, 'TEST', 'Language', 'ENU', b'1', 'Closed Captioning', 'TDS_ClosedCap1', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU', b'0', 'Closed Captioning', 'TDS_ClosedCap0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
		v_assessmentId, 'TEST', 'Language', 'ENU-Braille', b'1', 'Closed Captioning', 'TDS_ClosedCap0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
	(
	  v_assessmentId, 'TEST', 'Language', 'ESN', b'1', 'Closed Captioning', 'TDS_ClosedCap0', v_clientname,  UNHEX(REPLACE(UUID(),'-','')), 'ALL'
	), (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 1, 'Color Choices', 'TDS_CC0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 0, 'Print on Request', 'TDS_PoD_Stim', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 1, 'TTS Pausing', 'TDS_TTSPause1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 1, 'Emboss', 'TDS_Emboss0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 0, 'Color Choices', 'TDS_CCInvert', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 1, 'Streamlined Mode', 'TDS_SLM0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 1, 'TTS Audio Adjustments', 'TDS_TTSAA_Volume&TDS_TTSAA_Pitch&TDS_TTSAA_Rate&TDS_TTSAA_SelectVP', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 0, 'Expandable Passages', 'TDS_ExpandablePassages0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 1, 'TTX Business Rules', 'TDS_TTX_A203', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 1, 'Print on Request', 'TDS_PoD0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 1, 'Highlight', 'TDS_Highlight1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 0, 'Print on Request', 'TDS_PoD_Stim&TDS_PoD_Item', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECN', 1, 'American Sign Language', 'TDS_ASL0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECN', 1, 'Color Choices', 'TDS_CC0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECN', 1, 'Masking', 'TDS_Masking0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECN', 1, 'Print on Request', 'TDS_PoD0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECN', 1, 'Mark for Review', 'TDS_MfR0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECN', 1, 'Mute System Volume', 'TDS_Mute0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECN', 1, 'TTS Pausing', 'TDS_TTSPause0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECN', 1, 'TTX Business Rules', 'TDS_TTX_A206', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECN', 1, 'Streamlined Mode', 'TDS_SLM1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECN', 1, 'Expandable Passages', 'TDS_ExpandablePassages0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECN', 1, 'Highlight', 'TDS_Highlight0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECN', 1, 'Tutorial', 'TDS_T0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECN', 1, 'Strikethrough', 'TDS_ST0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECN', 1, 'TTS Audio Adjustments', 'TDS_TTSAA0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXN', 1, 'American Sign Language', 'TDS_ASL0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXN', 1, 'Color Choices', 'TDS_CC0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXN', 1, 'Masking', 'TDS_Masking0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXN', 1, 'Print on Request', 'TDS_PoD0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXN', 1, 'Mark for Review', 'TDS_MfR0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXN', 1, 'Mute System Volume', 'TDS_Mute0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXN', 1, 'TTS Pausing', 'TDS_TTSPause0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXN', 1, 'TTX Business Rules', 'TDS_TTX_A206', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXN', 1, 'Streamlined Mode', 'TDS_SLM1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXN', 1, 'Expandable Passages', 'TDS_ExpandablePassages0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXN', 1, 'Highlight', 'TDS_Highlight0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXN', 1, 'Tutorial', 'TDS_T0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXN', 1, 'Strikethrough', 'TDS_ST0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXN', 1, 'TTS Audio Adjustments', 'TDS_TTSAA0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXT', 1, 'American Sign Language', 'TDS_ASL0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXT', 1, 'Color Choices', 'TDS_CC0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXT', 1, 'Masking', 'TDS_Masking0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXT', 1, 'Print on Request', 'TDS_PoD0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXT', 1, 'Mark for Review', 'TDS_MfR0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXT', 1, 'Mute System Volume', 'TDS_Mute0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXT', 1, 'TTS Pausing', 'TDS_TTSPause0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXT', 1, 'TTX Business Rules', 'TDS_TTX_A206', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXT', 1, 'Streamlined Mode', 'TDS_SLM1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXT', 1, 'Expandable Passages', 'TDS_ExpandablePassages0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXT', 1, 'Highlight', 'TDS_Highlight0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXT', 1, 'Tutorial', 'TDS_T0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXT', 1, 'Strikethrough', 'TDS_ST0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXT', 1, 'TTS Audio Adjustments', 'TDS_TTSAA0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECT', 1, 'American Sign Language', 'TDS_ASL0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECT', 1, 'Color Choices', 'TDS_CC0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECT', 1, 'Masking', 'TDS_Masking0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECT', 1, 'Print on Request', 'TDS_PoD0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECT', 1, 'Mark for Review', 'TDS_MfR0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECT', 1, 'Mute System Volume', 'TDS_Mute0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECT', 1, 'TTS Pausing', 'TDS_TTSPause0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECT', 1, 'TTX Business Rules', 'TDS_TTX_A206', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECT', 1, 'Streamlined Mode', 'TDS_SLM1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECT', 1, 'Expandable Passages', 'TDS_ExpandablePassages0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECT', 1, 'Highlight', 'TDS_Highlight0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECT', 1, 'Tutorial', 'TDS_T0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECT', 1, 'Strikethrough', 'TDS_ST0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECT', 1, 'TTS Audio Adjustments', 'TDS_TTSAA0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXN', 1, 'American Sign Language', 'TDS_ASL0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXN', 1, 'Color Choices', 'TDS_CC0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXN', 1, 'Masking', 'TDS_Masking0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXN', 1, 'Print on Request', 'TDS_PoD0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXN', 1, 'Mark for Review', 'TDS_MfR0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXN', 1, 'Mute System Volume', 'TDS_Mute0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXN', 1, 'TTS Pausing', 'TDS_TTSPause0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXN', 1, 'TTX Business Rules', 'TDS_TTX_A206', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXN', 1, 'Streamlined Mode', 'TDS_SLM1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXN', 1, 'Expandable Passages', 'TDS_ExpandablePassages0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXN', 1, 'Highlight', 'TDS_Highlight0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXN', 1, 'Tutorial', 'TDS_T0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXN', 1, 'Strikethrough', 'TDS_ST0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXN', 1, 'TTS Audio Adjustments', 'TDS_TTSAA0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCN', 1, 'American Sign Language', 'TDS_ASL0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCN', 1, 'Color Choices', 'TDS_CC0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCN', 1, 'Masking', 'TDS_Masking0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCN', 1, 'Print on Request', 'TDS_PoD0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCN', 1, 'Mark for Review', 'TDS_MfR0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCN', 1, 'Mute System Volume', 'TDS_Mute0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCN', 1, 'TTS Pausing', 'TDS_TTSPause0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCN', 1, 'TTX Business Rules', 'TDS_TTX_A206', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCN', 1, 'Streamlined Mode', 'TDS_SLM1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCN', 1, 'Expandable Passages', 'TDS_ExpandablePassages0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCN', 1, 'Highlight', 'TDS_Highlight0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCN', 1, 'Tutorial', 'TDS_T0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCN', 1, 'Strikethrough', 'TDS_ST0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCN', 1, 'TTS Audio Adjustments', 'TDS_TTSAA0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXT', 1, 'American Sign Language', 'TDS_ASL0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXT', 1, 'Color Choices', 'TDS_CC0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXT', 1, 'Masking', 'TDS_Masking0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXT', 1, 'Print on Request', 'TDS_PoD0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXT', 1, 'Mark for Review', 'TDS_MfR0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXT', 1, 'Mute System Volume', 'TDS_Mute0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXT', 1, 'TTS Pausing', 'TDS_TTSPause0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXT', 1, 'TTX Business Rules', 'TDS_TTX_A206', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXT', 1, 'Streamlined Mode', 'TDS_SLM1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXT', 1, 'Expandable Passages', 'TDS_ExpandablePassages0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXT', 1, 'Highlight', 'TDS_Highlight0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXT', 1, 'Tutorial', 'TDS_T0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXT', 1, 'Strikethrough', 'TDS_ST0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXT', 1, 'TTS Audio Adjustments', 'TDS_TTSAA0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCT', 1, 'American Sign Language', 'TDS_ASL0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCT', 1, 'Color Choices', 'TDS_CC0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCT', 1, 'Masking', 'TDS_Masking0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCT', 1, 'Print on Request', 'TDS_PoD0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCT', 1, 'Mark for Review', 'TDS_MfR0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCT', 1, 'Mute System Volume', 'TDS_Mute0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCT', 1, 'TTS Pausing', 'TDS_TTSPause0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCT', 1, 'TTX Business Rules', 'TDS_TTX_A206', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCT', 1, 'Streamlined Mode', 'TDS_SLM1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCT', 1, 'Expandable Passages', 'TDS_ExpandablePassages0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCT', 1, 'Highlight', 'TDS_Highlight0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCT', 1, 'Tutorial', 'TDS_T0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCT', 1, 'Strikethrough', 'TDS_ST0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCT', 1, 'TTS Audio Adjustments', 'TDS_TTSAA0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 1, 'Tutorial', 'TDS_T1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 1, 'Mark for Review', 'TDS_MfR1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 1, 'Strikethrough', 'TDS_ST1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 0, 'Color Choices', 'TDS_CCMedGrayLtGray', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 1, 'Expandable Passages', 'TDS_ExpandablePassages1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 0, 'Color Choices', 'TDS_CCMagenta', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 0, 'Color Choices', 'TDS_CCYellowB', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 0, 'Print on Request', 'TDS_PoD_Item', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 0, 'Masking', 'TDS_Masking1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECN', 1, 'Emboss', 'TDS_Emboss_Stim&TDS_Emboss_Item', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXN', 1, 'Emboss', 'TDS_Emboss_Stim&TDS_Emboss_Item', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXT', 1, 'Emboss', 'TDS_Emboss_Stim&TDS_Emboss_Item', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXN', 1, 'Emboss', 'TDS_Emboss_Stim&TDS_Emboss_Item', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCT', 1, 'Emboss', 'TDS_Emboss_Stim&TDS_Emboss_Item', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 1, 'American Sign Language', 'TDS_ASL0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 1, 'Emboss Request Type', 'TDS_ERT0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECN', 0, 'Emboss Request Type', 'TDS_ERT_OR&TDS_ERT_Auto', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXN', 0, 'Emboss Request Type', 'TDS_ERT_OR&TDS_ERT_Auto', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXT', 0, 'Emboss Request Type', 'TDS_ERT_OR&TDS_ERT_Auto', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXN', 0, 'Emboss Request Type', 'TDS_ERT_OR&TDS_ERT_Auto', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCT', 0, 'Emboss Request Type', 'TDS_ERT_OR&TDS_ERT_Auto', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECT', 0, 'Emboss Request Type', 'TDS_ERT_OR&TDS_ERT_Auto', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCN', 0, 'Emboss Request Type', 'TDS_ERT_OR&TDS_ERT_Auto', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXT', 0, 'Emboss Request Type', 'TDS_ERT_OR&TDS_ERT_Auto', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECN', 1, 'Emboss Request Type', 'TDS_ERT_OR', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXN', 1, 'Emboss Request Type', 'TDS_ERT_OR', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXT', 1, 'Emboss Request Type', 'TDS_ERT_OR', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXN', 1, 'Emboss Request Type', 'TDS_ERT_OR', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCT', 1, 'Emboss Request Type', 'TDS_ERT_OR', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECT', 1, 'Emboss Request Type', 'TDS_ERT_OR', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCN', 1, 'Emboss Request Type', 'TDS_ERT_OR', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXT', 1, 'Emboss Request Type', 'TDS_ERT_OR', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 1, 'Masking', 'TDS_Masking0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 0, 'Highlight', 'TDS_Highlight0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 0, 'American Sign Language', 'TDS_ASL1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 0, 'Strikethrough', 'TDS_ST0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 0, 'Mark for Review', 'TDS_MfR0', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT0', 1, 'Mute System Volume', 'TDS_Mute1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECT', 1, 'Emboss', 'TDS_Emboss_Stim&TDS_Emboss_Item', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCN', 1, 'Emboss', 'TDS_Emboss_Stim&TDS_Emboss_Item', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXT', 1, 'Emboss', 'TDS_Emboss_Stim&TDS_Emboss_Item', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECN', 0, 'Mute System Volume', 'TDS_Mute2', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXN', 0, 'Mute System Volume', 'TDS_Mute2', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXT', 0, 'Mute System Volume', 'TDS_Mute2', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECT', 0, 'Mute System Volume', 'TDS_Mute2', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXN', 0, 'Mute System Volume', 'TDS_Mute2', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCN', 0, 'Mute System Volume', 'TDS_Mute2', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXT', 0, 'Mute System Volume', 'TDS_Mute2', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCT', 0, 'Mute System Volume', 'TDS_Mute2', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECN', 0, 'Mute System Volume', 'TDS_Mute3', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXN', 0, 'Mute System Volume', 'TDS_Mute3', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXT', 0, 'Mute System Volume', 'TDS_Mute3', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECT', 0, 'Mute System Volume', 'TDS_Mute3', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXN', 0, 'Mute System Volume', 'TDS_Mute3', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCN', 0, 'Mute System Volume', 'TDS_Mute3', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXT', 0, 'Mute System Volume', 'TDS_Mute3', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCT', 0, 'Mute System Volume', 'TDS_Mute3', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECN', 0, 'Mute System Volume', 'TDS_Mute1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXN', 0, 'Mute System Volume', 'TDS_Mute1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_EXT', 0, 'Mute System Volume', 'TDS_Mute1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_ECT', 0, 'Mute System Volume', 'TDS_Mute1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXN', 0, 'Mute System Volume', 'TDS_Mute1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCN', 0, 'Mute System Volume', 'TDS_Mute1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UXT', 0, 'Mute System Volume', 'TDS_Mute1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL'),
  (v_assessmentId, 'TEST', 'Braille Type', 'TDS_BT_UCT', 0, 'Mute System Volume', 'TDS_Mute1', v_clientname, UNHEX(REPLACE(UUID(),'-','')), 'ALL');
	DELETE FROM __accommodationcache;
END