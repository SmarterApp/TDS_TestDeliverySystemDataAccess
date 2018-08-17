/***********************************************************************************************************************
  File: V7__configs_add_xhr_default_message_and_translation.sql

  Desc: Create a new message that will be used in tds_xhr.js to provide a translated/configurable generic
  error message.

***********************************************************************************************************************/
START TRANSACTION;

SELECT 
	@newMessageId := MAX(messageid) + 1,
    @newKey := MAX(_key) + 1
FROM
	configs.tds_coremessageobject;

INSERT INTO 
	configs.tds_coremessageobject(`context`, contexttype, messageid, ownerapp, appkey, `message`, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser)
VALUES(
	'Global', -- context
	'ClientSide', -- contexttype
    @newMessageId, -- messageid
    'Student', -- ownerapp
    'Messages.Label.XHRDefaultError', -- appkey
    'There was a problem with your request.', -- message
    NULL, -- paralables
    @newKey, -- _key
    NULL, -- fromclient
    NOW(), -- datelatered
    NULL, -- nodes
    NULL -- ismessageshowtouser
);

INSERT INTO 
	configs.client_messagetranslation(_fk_coremessageobject, `client`, `message`, `language`, grade, `subject`, _key, datealtered)
VALUES(
	@newKey, -- _fk_coremessageobject
    'SBAC', -- `client`
    'There was a problem with your request.', -- `message`
    'ENU', -- `language`,
    '--ANY--', -- grade
    '--ANY--', -- `subject`
    UNHEX(REPLACE(UUID(),'-','')), -- _key, 
    NOW() -- datealtered
);

INSERT INTO 
	configs.client_messagetranslation(_fk_coremessageobject, `client`, `message`, `language`, grade, `subject`, _key, datealtered)
VALUES(
	@newKey, -- _fk_coremessageobject
    'SBAC', -- `client`
    'Hubo un problema con su solicitud.', -- `message`
    'ESN', -- `language`,
    '--ANY--', -- grade
    '--ANY--', -- `subject`
    UNHEX(REPLACE(UUID(),'-','')), -- _key, 
    NOW() -- datealtered
);

INSERT INTO
	configs.tds_coremessageuser(_fk_coremessageobject, systemid)
VALUES
	(@newKey, 'Student'),
	(@newKey, 'ResponseEntry'),
	(@newKey, 'ScoreEntry'),
	(@newKey, 'SIRVE');
    
DELETE FROM configs.__appmessages;
DELETE FROM configs.__appmessagecontexts;

COMMIT;