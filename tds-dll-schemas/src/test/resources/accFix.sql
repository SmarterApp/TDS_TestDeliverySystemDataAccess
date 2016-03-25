-- 12217 is max messageid for ownerapp=Student
-- 12046 is max messageid for ownerapp=Proctor
-- I will using value greated than both of them to keep happy
-- unique index on (`ownerapp`, `messageid`, `contexttype`, `context`)
--  If adding more recs to this script, start from messageis = 13000

use configs;
delete from `configs`.`tds_coremessageobject` where messageid > 12217 and
(ownerapp = 'Student' or ownerapp = 'Proctor');
delete from `configs`.`client_messagetranslation`
where client='SBAC_PT' and `_fk_coremessageobject` in (2691, 3262, 2702, 3248, 2679);  
 
insert into `configs`.`tds_coremessageobject` (`context`, `contexttype`, `messageid`,
  `ownerapp`, `appkey`, `message`, `datealtered` ) values
  ('AccValue', 'ServerSide', 12218, 'Student', 'TDS_ASL1', 'on',now(3));
insert into `configs`.`tds_coremessageobject` (`context`, `contexttype`, `messageid`,
  `ownerapp`, `appkey`, `message`, `datealtered` ) values
  ('AccValue', 'ServerSide', 12219, 'Proctor', 'TDS_ASL1', 'on',now(3)); 
   
 -- Need to add ColorContrast for Proctor, both acctype and accvalues
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccType','ServerSide',12220,'Proctor','ColorContrast','Color Contrast',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12221,'Proctor','TDS_CC0','Black on White',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12222,'Proctor','TDS_CCInvert','Reverse Contrast',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12223,'Proctor','TDS_CCMagenta','Magenta',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12224,'Proctor','TDS_CCYellowB','Yellow on Blue',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12225,'Proctor','TDS_CCMedGrayLtGray','Medium Gray on Light Gray',now(3));
 
 -- ClosedCaptioning; new
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccType','ServerSide',12226,'Proctor','ClosedCaptioning','Closed Captioning',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccType','ServerSide',12227,'Student','ClosedCaptioning','Closed Captioning',now(3));
 
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12228,'Proctor','TDS_ClosedCap0','off',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12229,'Proctor','TDS_ClosedCap1','on',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12230,'Student','TDS_ClosedCap0','off',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12231,'Student','TDS_ClosedCap1','on',now(3));
 
 -- Language: Student has 'ENU' and 'ESN'; Proctor has only 'ENU'
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12232,'Student','ENU-Braille','English Braille',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12233,'Proctor','ENU-Braille','English Braille',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12234,'Proctor','ESN','Spanish',now(3));
 
 -- Masking. new
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccType','ServerSide',12235,'Proctor','Masking','Masking',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccType','ServerSide',12236,'Student','Masking','Masking',now(3));
 
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12237,'Proctor','TDS_Masking0','off',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12238,'Proctor','TDS_Masking1','on',now(3));
 
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12239,'Student','TDS_Masking0','off',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12240,'Student','TDS_Masking1','on',now(3));
 
 -- PermissiveMode. new
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccType','ServerSide',12241,'Proctor','PermissiveMode','Permissive Mode',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccType','ServerSide',12242,'Student','PermissiveMode','Permissive Mode',now(3));
 
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12243,'Proctor','TDS_PM0','off',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12244,'Proctor','TDS_PM1','on',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12245,'Student','TDS_PM0','off',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12246,'Student','TDS_PM1','on',now(3));
 
  
  -- StreamlinedInterface. new
  INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccType','ServerSide',12247,'Proctor','StreamlinedInterface','StreamLined Interface',now(3));
  INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccType','ServerSide',12248,'Student','StreamlinedInterface','StreamLined Interface',now(3));
 
 -- TODO check message text!
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12249,'Proctor','TDS_TS_Modern','Modern Shell',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12250,'Proctor','TDS_TS_Accessibility','Accessibility Shell',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12251,'Student','TDS_TS_Modern','Modern Shell',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12252,'Student','TDS_TS_Accessibility','Accessibility Shell',now(3));

   
 -- Translation.new
  INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccType','ServerSide',12253,'Proctor','Translation','Translation',now(3));
  INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccType','ServerSide',12254,'Student','Translation','Translation',now(3));
 
 -- TODO check message text used!
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12255,'Proctor','TDS_WL_Glossary','English Glossary',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12256,'Proctor','TDS_WL_ArabicGloss','Arabic Glossary',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12257,'Proctor','TDS_WL_ContoneseGloss','Cantonese Glossary',now(3));
INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12258,'Proctor','TDS_WL_ESNGlossary','Spanish Glossary',now(3));
INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12259,'Proctor','TDS_WL_KoreanGloss','Corean Glossary',now(3));
INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12260,'Proctor','TDS_WL_MandarinGloss','Mandarin Glossary',now(3));
INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12261,'Proctor','TDS_WL_PunjabiGloss','Punjabi Glossary',now(3));
INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12262,'Proctor','TDS_WL_RussianGloss','Russian Glossary',now(3)); 
INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12263,'Proctor','TDS_WL_TagalGloss','Tagal Glossary',now(3)); 
INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12264,'Proctor','TDS_WL_UkranianGloss','Ukranian Glossary',now(3));   
INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12265,'Proctor','TDS_WL_VietnameseGloss','Vietnamese Glossary',now(3));    
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12266,'Proctor','TDS_WL_Glossary&TDS_WL_ArabicGloss','Engllish&Arabic Glossary',now(3));  
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12267,'Proctor','TDS_WL_Glossary&TDS_WL_CantoneseGloss','English&Cantonese Glossary',now(3)); 
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12268,'Proctor','TDS_WL_Glossary&TDS_WL_ESNGloss','English&Spanish Glossary',now(3)); 
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12269,'Proctor','TDS_WL_Glossary&TDS_WL_KoreanGloss','English&Korean Glossary',now(3)); 
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12270,'Proctor','TDS_WL_Glossary&TDS_WL_MandarinGloss','English&Mandarin Glossary',now(3)); 
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12271,'Proctor','TDS_WL_Glossary&TDS_WL_PunjabiGloss','English&Punjabi Glossary',now(3)); 
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12272,'Proctor','TDS_WL_Glossary&TDS_WL_RussianGloss','English&Russian Glossary',now(3)); 
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12273,'Proctor','TDS_WL_Glossary&TDS_WL_TagalGloss','English&Tagal Glossary',now(3)); 
-- INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12274,'Proctor','TDS_WL_Glossary&TDS_WL_UkranianGloss','English&Ukranian Glossary',now(3)); 
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12275,'Proctor','TDS_WL_Glossary&TDS_WL_VietnameseGloss','English&Vietnamese Glossary',now(3)); 
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12276,'Proctor','TDS_WL0','No Glossary',now(3)); 
     
INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12277,'Student','TDS_WL_Glossary','English Glossary',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12278,'Student','TDS_WL_ArabicGloss','Arabic Glossary',now(3));
 INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12279,'Student','TDS_WL_ContoneseGloss','Cantonese Glossary',now(3));
INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12280,'Student','TDS_WL_ESNGlossary','Spanish Glossary',now(3));
INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12281,'Student','TDS_WL_KoreanGloss','Corean Glossary',now(3));
INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12282,'Student','TDS_WL_MandarinGloss','Mandarin Glossary',now(3));
INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12283,'Student','TDS_WL_PunjabiGloss','Punjabi Glossary',now(3));
INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12284,'Student','TDS_WL_RussianGloss','Russian Glossary',now(3)); 
INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12285,'Student','TDS_WL_TagalGloss','Tagal Glossary',now(3)); 
INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
 `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
 ('AccValue','ServerSide',12286,'Student','TDS_WL_UkranianGloss','Ukranian Glossary',now(3));   
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12287,'Student','TDS_WL_VietnameseGloss','Vietnamese Glossary',now(3));    
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12288,'Student','TDS_WL_Glossary&TDS_WL_ArabicGloss','Engllish&Arabic Glossary',now(3));  
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12289,'Student','TDS_WL_Glossary&TDS_WL_CantoneseGloss','English&Cantonese Glossary',now(3)); 
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12290,'Student','TDS_WL_Glossary&TDS_WL_ESNGloss','English&Spanish Glossary',now(3)); 
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12291,'Student','TDS_WL_Glossary&TDS_WL_KoreanGloss','English&Korean Glossary',now(3)); 
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12292,'Student','TDS_WL_Glossary&TDS_WL_MandarinGloss','English&Mandarin Glossary',now(3)); 
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12293,'Student','TDS_WL_Glossary&TDS_WL_PunjabiGloss','English&Punjabi Glossary',now(3)); 
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12294,'Student','TDS_WL_Glossary&TDS_WL_RussianGloss','English&Russian Glossary',now(3)); 
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12295,'Student','TDS_WL_Glossary&TDS_WL_TagalGloss','English&Tagal Glossary',now(3)); 
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12296,'Student','TDS_WL_Glossary&TDS_WL_UkranianGloss','English&Ukranian Glossary',now(3)); 
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12297,'Student','TDS_WL_Glossary&TDS_WL_VietnameseGloss','English&Vietnamese Glossary',now(3)); 
--INSERT INTO `tds_coremessageobject` (`context`,`contexttype`,`messageid`,
-- `ownerapp`,`appkey`,`message`,`datealtered`) VALUES 
-- ('AccValue','ServerSide',12298,'Student','TDS_WL0','No Glossary',now(3)); 
     
--  NonEmbeddedDesignatedSupport :ignored now now

-- NonEmbeddedAccommodations: ignored for now

-- Other : ignored for now

 -- ColorContrast; existing ColorCoice for Student
 -- TODO what about Spanish?
 insert into `configs`.`client_messagetranslation` (`_fk_coremessageobject`,`client`, `message`,
   `language`, `_key`, `datealtered`)  values
   (2691, 'SBAC_PT', 'ColorContrast', 'ENU', unhex(REPLACE(UUID(), '-', '')), now(3));
   
   -- PrintOnDemand. Exists as PrintOneRequest for both Proctor and Student
 insert into `configs`.`client_messagetranslation` (`_fk_coremessageobject`,`client`, `message`,
   `language`, `_key`, `datealtered`)  values
   (3262, 'SBAC_PT', 'PrintOnDemand ', 'ENU', unhex(REPLACE(UUID(), '-', '')), now(3));
 insert into `configs`.`client_messagetranslation` (`_fk_coremessageobject`,`client`, `message`,
   `language`, `_key`, `datealtered`)  values
   (2702, 'SBAC_PT', 'PrintOnDemand ', 'ENU', unhex(REPLACE(UUID(), '-', '')), now(3));
   
 -- TexttoSpeech; exists as TTS for both Student and Proctor
 insert into `configs`.`client_messagetranslation` (`_fk_coremessageobject`,`client`, `message`,
   `language`, `_key`, `datealtered`)  values
   (3248, 'SBAC_PT', 'TexttoSpeech', 'ENU', unhex(REPLACE(UUID(), '-', '')), now(3));
 insert into `configs`.`client_messagetranslation` (`_fk_coremessageobject`,`client`, `message`,
   `language`, `_key`, `datealtered`)  values
   (2679, 'SBAC_PT', 'TexttoSpeech', 'ENU', unhex(REPLACE(UUID(), '-', '')), now(3));
   
   

 

  
  