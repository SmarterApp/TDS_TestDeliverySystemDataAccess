/***********************************************************************************************************************
  File: V8__configs_update_spanish_translations.sql

  Desc: Provide new Spanish translations and corrections to existing Spanish translations.

***********************************************************************************************************************/

SELECT
    @popupMessageKey := _key
FROM
    configs.tds_coremessageobject
WHERE
    appkey = 'TestCompleted'
    AND ownerapp = 'Student';

SELECT
    @messageTextKey := _key
FROM
    configs.tds_coremessageobject
WHERE
    appkey = 'Sections.BottomInstructions.TestReview'
    AND ownerapp = 'Student';

START TRANSACTION;
UPDATE
    configs.tds_coremessageobject
SET
    `context` = 'TestReview.aspx',
    contexttype = 'ServerSide'
WHERE
    appkey = 'TestShellScripts.Label.Questions';

INSERT INTO
    configs.client_messagetranslation(_fk_coremessageobject, `client`, message, `language`, grade, `subject`, _key, datealtered)
SELECT
    _key,
    'SBAC',
    'Questions:',
    'ENU',
    '--ANY--',
    '--ANY--',
    UNHEX(REPLACE(UUID(),'-','')),
    NOW()
FROM
    configs.tds_coremessageobject
WHERE
    appkey = 'TestShellScripts.Label.Questions';

INSERT INTO
    configs.client_messagetranslation(_fk_coremessageobject, `client`, message, `language`, grade, `subject`, _key, datealtered)
SELECT
    _key,
    'SBAC',
    'Preguntas:',
    'ESN',
    '--ANY--',
    '--ANY--',
    UNHEX(REPLACE(UUID(),'-','')),
    NOW()
FROM
    configs.tds_coremessageobject
WHERE
    appkey = 'TestShellScripts.Label.Questions';

UPDATE
    configs.client_messagetranslation
SET
    message = 'Imprimir pregunta'
WHERE
    message = 'Imprimir item';

UPDATE
    configs.client_messagetranslation
SET
    message = 'Has contestado todas las preguntas. Puedes revisar y cambiar cualquier respuesta. Cuando estés listo para terminar la prueba, haz clic en el botón [Terminar prueba].'
WHERE
    _fk_coremessageobject = @popupMessageKey
    AND `client` LIKE 'SBAC%';

UPDATE
    configs.client_messagetranslation
SET
    message = 'Cuando termines de revisar tus respuestas, haz clic en el botón <strong>Finalizar prueba</strong>. No puedes cambiar las respuestas después de enviar la prueba.'
WHERE
    _fk_coremessageobject = @messageTextKey
    AND `client` LIKE 'SBAC%';

DELETE FROM configs.__appmessagecontexts;
DELETE FROM configs.__appmessages;
COMMIT;