
-- This stored procedure inserts tool data that should be enabled for every loaded assessment
DROP PROCEDURE IF EXISTS `InsertGeneralTools`;
DELIMITER $$
CREATE PROCEDURE `InsertToolDependencies`(v_clientname VARCHAR(100), v_assessmentId VARCHAR(120))
BEGIN
	-- Clear the current tool dependencies
	DELETE FROM configs.client_tooldependencies
	WHERE context = v_assessmentId AND clientname = v_clientname;

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
END$$
DELIMITER ;