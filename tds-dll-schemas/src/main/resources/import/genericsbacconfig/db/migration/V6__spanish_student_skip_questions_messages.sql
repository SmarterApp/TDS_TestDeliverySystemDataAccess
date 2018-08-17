USE configs;

SET @client = 'SBAC_PT';

SET @cmokey = (
    SELECT _key from tds_coremessageobject
    WHERE ownerapp = 'Student'
    AND appkey = 'TestCompleted'
);
DELETE FROM client_messagetranslation
WHERE _fk_coremessageobject in (@cmokey)
AND language = 'ESN';
INSERT INTO client_messagetranslation (
    _fk_coremessageobject, client,
    message,
    language, grade, subject, _key, datealtered)
VALUES (
    @cmokey, @client,
    'Has contestado todas las preguntas. Puedes revisar y cambiar cualquier respuesta. Cuando estés listo para terminar la prueba, haz clic en el botón [Finalizar la prueba].',
    'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());


SET @cmokey = (
    SELECT _key from tds_coremessageobject
    WHERE ownerapp = 'Student'
    AND appkey = 'NextTestFinished'
);
DELETE FROM client_messagetranslation
WHERE _fk_coremessageobject in (@cmokey)
AND language = 'ESN';
INSERT INTO client_messagetranslation (
    _fk_coremessageobject, client,
    message,
    language, grade, subject, _key, datealtered)
VALUES (
    @cmokey, @client,
    'Has llegado a la última pregunta de esta prueba. Cuando hayas terminado de revisar tus respuestas, haz clic en el botón [Finalizar la prueba].',
    'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());


SET @cmokey = (
    SELECT _key from tds_coremessageobject
    WHERE ownerapp = 'Student'
    AND appkey = 'TestItemScores'
);
DELETE FROM client_messagetranslation
WHERE _fk_coremessageobject in (@cmokey)
AND language = 'ESN';
INSERT INTO client_messagetranslation (
    _fk_coremessageobject, client,
    message,
    language, grade, subject, _key, datealtered)
VALUES (
    @cmokey, @client,
    'Te estás preparando para enviar tu prueba. Puedes revisar tus respuestas seleccionando el número de pregunta abajo.',
    'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());


SET @cmokey = (
    SELECT _key from tds_coremessageobject
    WHERE ownerapp = 'Student'
    AND appkey = 'StaticContent.Label.ReviewSubmit'
);
DELETE FROM client_messagetranslation
WHERE _fk_coremessageobject in (@cmokey)
AND language = 'ESN';
INSERT INTO client_messagetranslation (
    _fk_coremessageobject, client,
    message,
    language, grade, subject, _key, datealtered)
VALUES (
    @cmokey, @client,
    'Te estás preparando para enviar tu prueba. Puedes revisar tus respuestas seleccionando el número de pregunta abajo.',
    'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());


SET @cmokey = (
    SELECT _key from tds_coremessageobject
    WHERE ownerapp = 'Student'
    AND appkey = 'StaticContent.Label.Congratulations'
);
DELETE FROM client_messagetranslation
WHERE _fk_coremessageobject in (@cmokey)
AND language = 'ESN';
INSERT INTO client_messagetranslation (
    _fk_coremessageobject, client,
    message,
    language, grade, subject, _key, datealtered)
VALUES (
    @cmokey, @client,
    'Te estás preparando para enviar tu prueba.',
    'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());


SET @cmokey = (
    SELECT _key from tds_coremessageobject
    WHERE ownerapp = 'Student'
    AND appkey = 'Sections.TopHeader.TestReview'
);
DELETE FROM client_messagetranslation
WHERE _fk_coremessageobject in (@cmokey)
AND language = 'ESN';
INSERT INTO client_messagetranslation (
    _fk_coremessageobject, client,
    message,
    language, grade, subject, _key, datealtered)
VALUES (
    @cmokey, @client,
    'Te estás preparando para enviar tu prueba.',
    'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());


SET @cmokey = (
    SELECT _key from tds_coremessageobject
    WHERE ownerapp = 'Student'
    AND appkey = 'Sections.TopInstructions.TestReview'
);
DELETE FROM client_messagetranslation
WHERE _fk_coremessageobject in (@cmokey)
AND language = 'ESN';
INSERT INTO client_messagetranslation (
    _fk_coremessageobject, client,
    message,
    language, grade, subject, _key, datealtered)
VALUES (
    @cmokey, @client,
    'Puedes revisar tus respuestas seleccionando el número de pregunta abajo.',
    'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());


SET @cmokey = (
    SELECT _key from tds_coremessageobject
    WHERE ownerapp = 'Student'
    AND appkey = 'QuestionsAreMarkedForReview'
);
DELETE FROM client_messagetranslation
WHERE _fk_coremessageobject in (@cmokey)
AND language = 'ESN';
INSERT INTO client_messagetranslation (
    _fk_coremessageobject, client,
    message,
    language, grade, subject, _key, datealtered)
VALUES (
    @cmokey, @client,
    'pregunta(s) están marcadas para revisión.',
    'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());


SET @cmokey = (
    SELECT _key from tds_coremessageobject
    WHERE ownerapp = 'Student'
    AND appkey = 'QuestionsAreUnanswered'
);
DELETE FROM client_messagetranslation
WHERE _fk_coremessageobject in (@cmokey)
AND language = 'ESN';
INSERT INTO client_messagetranslation (
    _fk_coremessageobject, client,
    message,
    language, grade, subject, _key, datealtered)
VALUES (
    @cmokey, @client,
    'pregunta(s) están sin contestar.',
    'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());


/** Below Delete is to delete cache table and its context, once user login it will populate the cache table from tds_coremessageobject,tds_coremessageuser,client_messagetranslation table **/
DELETE FROM __appmessages;
DELETE FROM __appmessagecontexts;

