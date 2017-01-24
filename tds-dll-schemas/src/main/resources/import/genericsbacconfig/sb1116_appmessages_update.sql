INSERT INTO configs.__appmessages select @fkmsgcontext:=_fk_appmessagecontext, 4002, msgsource, 12338, contexttype, context, 'TDSTTS.Label.SpeakOption', language, grade, subject, paralabels, 'Speak Option' 
FROM configs.__appmessages WHERE appkey = 'TDSTTS.Label.SpeakOptionAPrimary' AND language = 'ENU'
AND NOT EXISTS (SELECT 1 FROM configs.__appmessages where appkey = 'TDSTTS.Label.SpeakOption' AND language = 'ENU' AND _fk_appmessagecontext = @fkmsgcontext);

INSERT INTO configs.tds_coremessageobject (context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser)
VALUES ('tds_tts.js', 'ClientSide', 12238, 'Student', 'TDSTTS.Label.SpeakOption', 'Speak Option', NULL, 4002, 'AIR', '2012-05-03 19:17:36.590', NULL, NULL);

INSERT INTO configs.tds_coremessageuser (_fk_coremessageobject, systemid) VALUES (4002, 'Student');

INSERT INTO configs.__appmessages select @fkmsgcontext:=_fk_appmessagecontext, 4003, msgsource, 12339, contexttype, context, 'Global.Path.Help.Streamlined', language, grade, subject, paralabels, '../Projects/SBAC/Help/help_streamlined.html' 
  FROM  configs.__appmessages WHERE appkey = 'Global.Path.Help' AND language = 'ENU'
  AND NOT EXISTS (SELECT 1 FROM  configs.__appmessages where appkey = 'Global.Path.Help.Streamlined' AND language = 'ENU' AND _fk_appmessagecontext = @fkmsgcontext);

INSERT INTO configs.tds_coremessageobject (context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES ('Global', 'ClientSide', 12339, 'Student', 'Global.Path.Help.Streamlined', '../Projects/SBAC/Help/help_streamlined.html', NULL, 4003, NULL, '2012-05-03 19:17:36.590', NULL, NULL);

INSERT INTO configs.tds_coremessageuser (_fk_coremessageobject, systemid) VALUES (4003, 'Student');

INSERT INTO configs.client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) VALUES (4003, 'SBAC_PT', '../Projects/SBAC/Help/help_streamlined.html', 'ENU', '--ANY--', '--ANY--', 0xF17F128861006E006700650073000000, '2013-08-01 14:17:09.583');

INSERT INTO configs.client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) VALUES (4003, 'SBAC', '../Projects/SBAC/Help/help_streamlined.html', 'ENU', '--ANY--', '--ANY--', 0x20529E39740069006D00650000006500, '2013-08-01 14:17:09.583');
