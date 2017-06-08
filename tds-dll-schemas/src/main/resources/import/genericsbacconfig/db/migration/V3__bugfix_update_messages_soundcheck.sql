/***********************************************************************************************************************
  File: V3__bugfix_update_messages_soundcheck.sql

  Desc: Fixes casing on a spanish translation and updates sound check instructional text to match requirements

***********************************************************************************************************************/

USE configs;

UPDATE client_messagetranslation
SET message = '<span>Enviar Prueba</span>'
WHERE _fk_coremessageobject = 2524 AND language = 'ESN';

UPDATE tds_coremessageobject
SET message = 'To play the sample sound, press the speaker button.'
WHERE messageid = 10968

UPDATE tds_coremessageobject
SET message = 'You must be able to play audio for this test. Please tell your Test Administrator that your device has an audio problem.'
WHERE messageid = 10980