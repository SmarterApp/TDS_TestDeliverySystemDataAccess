USE configs;

UPDATE tds_coremessageobject set message = 'I heard the sound' where appkey = 'SoundCheck.Label.HeardYes';
UPDATE tds_coremessageobject set message = 'I did not hear the sound' where appkey = 'SoundCheck.Label.HeardNo';
UPDATE tds_coremessageobject set message = 'I heard the voice' where appkey = 'TTSCheck.Label.HeardYes' ;
UPDATE tds_coremessageobject set message = 'I did not hear the voice' where appkey = 'TTSCheck.Label.HeardNo';
UPDATE tds_coremessageobject set message = 'Skip TTS Check' where appkey = 'TTSCheck.Label.SkipEnglish' ;

INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('Default.aspx', 'ServerSide', '12340', 'Student', 'Sections.TopHeader.IsThisYou', 'Is This You?', NULL, '4004', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4004', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4004', 'ScoreEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4004', 'SIRVE');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4004', 'Student');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4004', 'SBAC', '¿Es éste usted?', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4004', 'SBAC', 'Is This You?', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());


INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('Default.aspx', 'ServerSide', '12341', 'Student', 'Sections.TopInstructions.IsThisYou', 'Please review the following information.', NULL, '4005', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4005', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4005', 'ScoreEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4005', 'SIRVE');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4005', 'Student');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4005', 'SBAC', 'Por favor, revise la siguiente información.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4005', 'SBAC', 'Please review the following information.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());



INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('Default.aspx', 'ServerSide', '12342', 'Student', 'Sections.BottomInstructions.IsThisYou', 'Please review the following information.', NULL, '4006', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4006', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4006', 'ScoreEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4006', 'SIRVE');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4006', 'Student');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4006', 'SBAC', 'Si la información es correcta, seleccione <strong> Sí </ strong>. Si no es así, seleccione <strong> No </ strong>.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4006', 'SBAC', 'If the information is correct, choose <strong>Yes</strong>. If not, choose <strong>No</strong>.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());



INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('Default.aspx', 'ServerSide', '12343', 'Student', 'Sections.TopHeader.ChooseAccommodations', 'Choose Settings:', NULL, '4007', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4007', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4007', 'ScoreEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4007', 'SIRVE');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4007', 'Student');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4007', 'SBAC', 'Selecciona la configuración:', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4007', 'SBAC', 'Choose Settings:', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());




INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('Default.aspx', 'ServerSide', '12344', 'Student', 'Sections.BottomInstructions.ChooseAccommodations', 'To test with these settings, choose <strong>Select</strong>. To select a different test, choose <strong>Go Back</strong>.', NULL, '4008', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4008', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4008', 'ScoreEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4008', 'SIRVE');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4008', 'Student');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4008', 'SBAC', 'Para tomar la prueba con esta configuración, selecciona <strong>Seleccionar</strong>. Para seleccionar una prueba diferente, selecciona <strong>Regresar</strong>.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4008', 'SBAC', 'To test with these settings, choose <strong>Select</strong>. To select a different test, choose <strong>Go Back</strong>.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());


INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('Default.aspx', 'ServerSide', '12345', 'Student', 'Sections.TopInstructions.ChooseAccommodations', 'Review the following test settings. You can change the options, if necessary.', NULL, '4009', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4009', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4009', 'ScoreEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4009', 'SIRVE');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4009', 'Student');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4009', 'SBAC', 'Revisa la configuración de la siguiente prueba. Puedes cambiar las opciones de ser necesario.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4009', 'SBAC', 'Review the following test settings. You can change the options, if necessary.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());




INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('Default.aspx', 'ServerSide', '12346', 'Student', 'Sections.TopHeader.ChooseTest', 'Your Tests', NULL, '4010', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4010', 'Student');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4010', 'SBAC', 'Tus pruebas', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4010', 'SBAC', 'Your Tests', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());


INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('Default.aspx', 'ServerSide', '12347', 'Student', 'Sections.TopInstructions.ChooseTest', 'Select the test you need to take.', NULL, '4011', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4011', 'Student');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4011', 'SBAC', 'Selecciona la prueba que necesitas tomar.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4011', 'SBAC', 'Select the test you need to take.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());



INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('Default.aspx', 'ServerSide', '12348', 'Student', 'Sections.BottomInstructions.ChooseTest', 'If you do not see the test you need to take, notify your Test Administrator and select <strong>Back to Login</strong>.', NULL, '4012', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4012', 'Student');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4012', 'SBAC', 'Si no ves la prueba que necesitas tomar, comunícaselo al Administrador de la prueba y selecciona <strong>Regresar a Inicio</strong>.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4012', 'SBAC', 'If you do not see the test you need to take, notify your Test Administrator and select <strong>Back to Login</strong>.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());



INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('Default.aspx', 'ServerSide', '12349', 'Student', 'Sections.TopHeader.TestInstructions', 'Instructions and Help', NULL, '4013', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4013', 'Student');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4013', 'SBAC', 'Instrucciones y ayuda', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4013', 'SBAC', 'Instructions and Help', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());




INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('Default.aspx', 'ServerSide', '12350', 'Student', 'Sections.TopInstructions.TestInstructions', 'You may select the question mark button to access this Help Guide at any time during your test.', NULL, '4014', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4014', 'Student');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4014', 'SBAC', 'Puedes seleccionar el botón del signo de interrogación para tener acceso a esta Guía de ayuda en cualquier momento durante la prueba.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4014', 'SBAC', 'You may select the question mark button to access this Help Guide at any time during your test.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());


INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('Default.aspx', 'ServerSide', '12351', 'Student', 'Sections.BottomInstructions.TestInstructions', 'To begin your test, choose <strong>Begin Test Now</strong>. If your Test Administrator tells you to log out, choose <strong>Return to Login</strong>.', NULL, '4015', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4015', 'Student');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4015', 'SBAC', 'Para comenzar tu prueba, selecciona <strong>Comenzar prueba ahora</strong>. Si el Administrador de la prueba te dice que cierres la sesión, selecciona <strong>Regresar a Inicio</strong>.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4015', 'SBAC', 'To begin your test, choose <strong>Begin Test Now</strong>. If your Test Administrator tells you to log out, choose <strong>Return to Login</strong>.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());



INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('Default.aspx', 'ServerSide', '12354', 'Student', 'Sections.BottomInstructions.IsYourTest', 'If the test name and settings are correct, choose <strong>Yes</strong>. If not, choose <strong>No</strong>.', NULL, '4018', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4018', 'Student');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4018', 'SBAC', 'Si el nombre de la prueba y la configuración son correctos, selecciona <strong>Sí</strong>. Si no, selecciona <strong>No</strong>.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4018', 'SBAC', 'If the test name and settings are correct, choose <strong>Yes</strong>. If not, choose <strong>No</strong>.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());



INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('TestResults.aspx', 'ServerSide', '12355', 'Student', 'Sections.TopHeader.TestResults', 'Your Results', NULL, '4019', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4019', 'Student');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4019', 'SBAC', 'Tus resultados', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4019', 'SBAC', 'Your Results', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());




INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('TestResults.aspx', 'ServerSide', '12356', 'Student', 'Sections.TopInstructions.TestResults', 'Your test was submitted. You may review the test details below.', NULL, '4020', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4020', 'Student');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4020', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4020', 'ScoreEntry');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4020', 'SBAC', 'Has terminado tu revisión. Puedes ver los detalles de la prueba abajo.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4020', 'SBAC', 'Your test was submitted. You may review the test details below.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());



INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('TestResults.aspx', 'ServerSide', '12357', 'Student', 'Sections.BottomInstructions.TestResults', 'To log out of the test, select <strong>Log Out</strong>.', NULL, '4021', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4021', 'Student');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4021', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4021', 'ScoreEntry');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4021', 'SBAC', 'Para cerrar la sesión de la prueba, selecciona <strong>Cerrar sesión</strong>.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4021', 'SBAC', 'To log out of the test, select <strong>Log Out</strong>.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());



INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('TestReview.aspx', 'ServerSide', '12358', 'Student', 'Sections.TopHeader.TestReview', 'Congratulations, you reached the end of the test!', NULL, '4022', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4022', 'Student');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4022', 'SBAC', '¡Felicidades, has llegado al final de la prueba!', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4022', 'SBAC', 'Congratulations, you reached the end of the test!', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());



INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('TestReview.aspx', 'ServerSide', '12359', 'Student', 'Sections.TopInstructions.TestReview', 'If you need to review your answers, select the question number you wish to review. A flag icon appears for any questions that you marked for review.', NULL, '4023', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4023', 'Student');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4023', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4023', 'ScoreEntry');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4023', 'SBAC', 'Si necesitas revisar tus respuestas, selecciona el número de la pregunta que te gustaría revisar. Aparecerá el ícono de una bandera en cada pregunta que hayas marcado para revisión.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4023', 'SBAC', 'If you need to review your answers, select the question number you wish to review. A flag icon appears for any questions that you marked for review.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());


INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('TestReview.aspx', 'ServerSide', '12360', 'Student', 'Sections.BottomInstructions.TestReview', 'When you are done reviewing your answers, select <strong>Submit Test</strong>. You cannot change your answers after you submit the test.', NULL, '4024', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4024', 'Student');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4024', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4024', 'ScoreEntry');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4024', 'SBAC', 'Cuando termines de revisar tus respuestas, selecciona <strong>Enviar prueba</strong>. No puedes cambiar las respuestas después de enviar la prueba.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4024', 'SBAC', 'When you are done reviewing your answers, select <strong>Submit Test</strong>. You cannot change your answers after you submit the test.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());




INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('SoundCheck.aspx', 'ServerSide', '12361', 'Student', 'Sections.TopHeader.checkSound', 'Audio Playback Check', NULL, '4025', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4025', 'Student');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4025', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4025', 'ScoreEntry');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4025', 'SBAC', 'Revisión de reproducción de audio', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4025', 'SBAC', 'Audio Playback Check', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());



INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('SoundCheck.aspx', 'ServerSide', '12362', 'Student', 'Sections.TopInstructions.checkSound', 'Make sure audio playback is working.', NULL, '4026', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4026', 'Student');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4026', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4026', 'ScoreEntry');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4026', 'SBAC', 'Asegúrate que la reproducción del audio funciona.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4026', 'SBAC', 'Make sure audio playback is working.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());



INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('SoundCheck.aspx', 'ServerSide', '12363', 'Student', 'Sections.BottomInstructions.checkSound', 'If you heard the sound, choose <strong>I heard the sound</strong>. If not, choose <strong>I did not hear the sound</strong>.', NULL, '4027', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4027', 'Student');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4027', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4027', 'ScoreEntry');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4027', 'SBAC', 'Si escuchaste el sonido, selecciona <strong>Escuché el sonido</strong>. Si no, selecciona <strong>No escuché el sonido</strong>.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4027', 'SBAC', 'If you heard the sound, choose <strong>I heard the sound</strong>. If not, choose <strong>I did not hear the sound</strong>.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());



INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('SoundCheck.aspx', 'ServerSide', '12364', 'Student', 'Sections.BottomInstructions.CheckError', 'To try the audio playback check again, choose <strong>Try Again</strong>. If your Test Administrator tells you to log out, choose <strong>Log Out</strong>.', NULL, '4028', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4028', 'Student');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4028', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4028', 'ScoreEntry');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4028', 'SBAC', 'Para volver a revisar la reproducción del audio, selecciona <strong>Intentar nuevamente</strong>. Si el Administrador de la prueba te dice que cierres la sesión, selecciona <strong>Cerrar sesión</strong>.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4028', 'SBAC', 'To try the audio playback check again, choose <strong>Try Again</strong>. If your Test Administrator tells you to log out, choose <strong>Log Out</strong>.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());





INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('Approval.aspx', 'ServerSide', '12365', 'Student', 'Sections.TopHeader.TestApproval', 'Waiting for Approval', NULL, '4029', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4029', 'Student');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4029', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4029', 'ScoreEntry');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4029', 'SBAC', 'Esperando aprobación', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4029', 'SBAC', 'Waiting for Approval', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());




INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('Approval.aspx', 'ServerSide', '12366', 'Student', 'Sections.TopInstructions.TestApproval', 'Your Test Administrator needs to review your requested test and your test settings. This may take a few minutes.', NULL, '4030', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4030', 'Student');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4030', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4030', 'ScoreEntry');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4030', 'SBAC', 'El Administrador de la prueba necesita revisar la prueba que solicitaste y la configuración de tu prueba. Esto puede tardar varios minutos.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4030', 'SBAC', 'Your Test Administrator needs to review your requested test and your test settings. This may take a few minutes.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());



INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('Approval.aspx', 'ServerSide', '12367', 'Student', 'Sections.BottomInstructions.TestApproval', 'Please wait for your TA to approve your request. If you need to cancel your request, select <strong>Cancel Request</strong>.', NULL, '4031', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4031', 'Student');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4031', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4031', 'ScoreEntry');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4031', 'SBAC', 'Por favor espera que el Administrador de la prueba apruebe tu solicitud. Si necesitas cancelar tu solicitud, selecciona <strong>Cancelar solicitud</strong>.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4031', 'SBAC', 'Please wait for your TA to approve your request. If you need to cancel your request, select <strong>Cancel Request</strong>.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());



INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('TTSCheck.aspx', 'ServerSide', '12368', 'Student', 'Sections.TopHeader.CheckSoundEnglish', 'Text-to-Speech Sound Check', NULL, '4032', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4032', 'Student');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4032', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4032', 'ScoreEntry');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4032', 'SBAC', 'Revisión de sonido de la función De texto a voz', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4032', 'SBAC', 'Text-to-Speech Sound Check', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());




INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('TTSCheck.aspx', 'ServerSide', '12369', 'Student', 'Sections.TopInstructions.CheckSoundEnglish', 'Make sure text-to-speech is working.', NULL, '4033', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4033', 'Student');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4033', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4033', 'ScoreEntry');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4033', 'SBAC', 'Asegúrate que la función De texto a voz funciona.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4033', 'SBAC', 'Make sure text-to-speech is working.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());



INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('TTSCheck.aspx', 'ServerSide', '12370', 'Student', 'Sections.BottomInstructions.CheckSoundEnglish', 'If you heard the voice clearly, choose <strong>I heard the voice</strong>. If not, choose <strong>I did not hear the voice</strong>. To continue testing without checking text-to-speech, choose <strong>Skip TTS Check</strong>.', NULL, '4034', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4034', 'Student');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4034', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4034', 'ScoreEntry');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4034', 'SBAC', 'Si escuchaste claramente la voz, selecciona <strong>Escuché la voz</strong>. Si no, selecciona <strong>No escuché la voz</strong>. Para continuar con la prueba sin revisar la función De texto a voz, selecciona <strong>Omitir revisión TTS</strong>.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4034', 'SBAC', 'If you heard the voice clearly, choose <strong>I heard the voice</strong>. If not, choose <strong>I did not hear the voice</strong>. To continue testing without checking text-to-speech, choose <strong>Skip TTS Check</strong>.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());





INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('TTSCheck.aspx', 'ServerSide', '12371', 'Student', 'Sections.BottomInstructions.TTSNotAvailable', 'To continue testing without text-to-speech, choose <strong>Continue</strong>. To log out and switch to another browser or device, choose <strong>Log Out</strong>.', NULL, '4035', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4035', 'Student');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4035', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4035', 'ScoreEntry');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4035', 'SBAC', 'Para continuar las pruebas sin texto a voz, elija <strong> Continuar </strong>. Para cerrar la sesión y cambiar a otro navegador o dispositivo, elija <strong> Cerrar sesión </strong>.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4035', 'SBAC', 'To continue testing without text-to-speech, choose <strong>Continue</strong>. To log out and switch to another browser or device, choose <strong>Log Out</strong>.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());




INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('TTSCheck.aspx', 'ServerSide', '12372', 'Student', 'Sections.BottomInstructions.CheckErrorDiv', 'To check your text-to-speech settings again, choose <strong>Try Again</strong>. If your Test Administrator tells you to log out, choose <strong>Log Out</strong>. To continue testing without checking  text-to-speech, choose <strong>Continue</strong>.', NULL, '4036', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4036', 'Student');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4036', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4036', 'ScoreEntry');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4036', 'SBAC', 'Para volver a revisar la configuración de tu función De texto a voz, selecciona <strong>Volver a intentar</strong>. Si el Administrador de la prueba te dice que cierres la sesión, selecciona <strong>Cerrar sesión</strong>. Para continuar con la prueba sin revisar la función De texto a voz, selecciona <strong>Continuar</strong>.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4036', 'SBAC', 'To check your text-to-speech settings again, choose <strong>Try Again</strong>. If your Test Administrator tells you to log out, choose <strong>Log Out</strong>. To continue testing without checking  text-to-speech, choose <strong>Continue</strong>.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());


INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('Default.aspx', 'ServerSide', '12352', 'Student', 'Sections.TopHeader.IsYourTest', 'Is This Your Test?', NULL, '4037', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4037', 'Student');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4037', 'SBAC', '¿Es esta tu prueba?', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4037', 'SBAC', 'Is This Your Test?', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());




INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('Default.aspx', 'ServerSide', '12353', 'Student', 'Sections.TopInstructions.IsYourTest', 'Review the following test settings.', NULL, '4017', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4017', 'Student');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4017', 'SBAC', 'Revisa la configuración de la siguiente prueba.', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4017', 'SBAC', 'Review the following test settings.', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());


INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('Default.aspx', 'ServerSide', '12373', 'Student', 'Sections.BottomHeader.IsYourTest', 'Next Step:', NULL, '4038', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4038', 'Student');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4038', 'SBAC', 'Siguiente paso:', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4038', 'SBAC', 'Next Step:', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());




INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('TestReview.aspx', 'ServerSide', '12374', 'Student', 'Sections.BottomHeader.TestReview', 'Next Step:', NULL, '4039', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4039', 'Student');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4039', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4039', 'ScoreEntry');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4039', 'SBAC', 'Siguiente paso:', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4039', 'SBAC', 'Next Step:', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());



INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('TestReview.aspx', 'ServerSide', '12375', 'Student', 'Sections.BottomHeader.TestResults', 'Next Step:', NULL, '4040', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4040', 'Student');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4040', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4040', 'ScoreEntry');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4040', 'SBAC', 'Siguiente paso:', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4040', 'SBAC', 'Next Step:', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());


INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('TTSCheck.aspx', 'ServerSide', '12376', 'Student', 'Sections.BottomHeader.TTSNotAvailable', 'Next Step:', NULL, '4041', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4041', 'Student');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4041', 'ResponseEntry');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4041', 'ScoreEntry');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4041', 'SBAC', 'Siguiente paso:', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4041', 'SBAC', 'Next Step:', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());


INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('Default.aspx', 'ServerSide', '12377', 'Student', 'Sections.BottomHeader.TestInstructions', 'Next Step:', NULL, '4042', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4042', 'Student');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4042', 'SBAC', 'Siguiente paso:', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4042', 'SBAC', 'Next Step:', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());

INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
values ('2662', 'SBAC', 'Imprimir item', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')), now());

INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
values ('2663', 'SBAC', 'Tutoría', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')), now());

INSERT INTO tds_coremessageobject(context, contexttype, messageid, ownerapp, appkey, message, paralabels, _key, fromclient, datealtered, nodes, ismessageshowtouser) VALUES
('tds_content_events.js', 'ClientSide', '12379', 'Student', 'TDSContentEventsJS.Label.Notepad', 'Notepad', NULL, '4044', NULL, NOW(), NULL, NULL);
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4044', 'Student');
INSERT INTO tds_coremessageuser(_fk_coremessageobject, systemid) values ('4044', 'ResponseEntry');
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4044', 'SBAC', 'bloc de notas', 'ESN', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());
INSERT INTO  client_messagetranslation(_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered) values
('4044', 'SBAC', 'Notepad', 'ENU', '--ANY--', '--ANY--', unhex(REPLACE(UUID(), '-', '')) , NOW());

/** Below Delete is to delete cache table and its context, once user login it will populate the cache table from tds_coremessageobject,tds_coremessageuser,client_messagetranslation table **/
DELETE FROM __appmessages;
DELETE FROM __appmessagecontexts;









