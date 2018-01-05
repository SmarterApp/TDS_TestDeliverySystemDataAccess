/***********************************************************************************************************************
  File: V4__spanish_message_translations.sql

  Desc: Adds spanish message translations for some messages that were missing.

***********************************************************************************************************************/

USE configs;

-- "Loading the page content."
UPDATE client_messagetranslation
SET message = 'Subiendo el contenido de la página.'
WHERE _fk_coremessageobject IN (2913, 2991) AND language = 'ESN';

UPDATE tds_coremessageobject
SET appkey = 'UI.LoadingContent'
WHERE _key = 2991;

-- "Thesaurus"
INSERT INTO client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
VALUES (3728, 'AIR', 'Diccionario de sinónimos', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());

-- "Dictionary"
INSERT INTO client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
VALUES (3975, 'AIR', 'Diccionario', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW()),
(3976, 'AIR', 'Diccionario ', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());

-- "American Sign Language"
INSERT INTO client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
VALUES (3727, 'AIR', 'Lenguaje de señas americanas ', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW()),
(3806, 'AIR', 'Lenguaje de señas americanas  ', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());

-- "You cannot take this test with this browser."
INSERT INTO client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
VALUES (3730, 'AIR', 'No puedes tomar esta prueba con este navegador.', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());

-- "Please enter the password to exit fullscreen mode:"
INSERT INTO client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
VALUES (3753, 'AIR', 'Por favor escribe tu contraseña para salir del formato de pantalla completa:', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());

-- "Off"
UPDATE client_messagetranslation
SET message = 'Apagar'
WHERE _fk_coremessageobject IN (2771, 2773) AND language = 'ESN';

-- "On"
INSERT INTO client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
VALUES (3815, 'AIR', 'Encender', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());

-- "Part A"
INSERT INTO client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
VALUES (3809, 'AIR', 'Parte A', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());

-- "Part B"
INSERT INTO client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
VALUES (3810, 'AIR', 'Parte B', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());

-- "Part C"
INSERT INTO client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
VALUES (3811, 'AIR', 'Parte C', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());

-- "Speak Option A"
UPDATE client_messagetranslation
SET message = 'Di opción A'
WHERE _fk_coremessageobject IN (2591) AND language = 'ESN';

-- "Speak Option B"
UPDATE client_messagetranslation
SET message = 'Di opción B'
WHERE _fk_coremessageobject IN (2593) AND language = 'ESN';

-- "Speak Option C"
UPDATE client_messagetranslation
SET message = 'Di opción C'
WHERE _fk_coremessageobject IN (2595) AND language = 'ESN';

-- "Speak Option D"
UPDATE client_messagetranslation
SET message = 'Di opción D'
WHERE _fk_coremessageobject IN (2597) AND language = 'ESN';

-- "Speak Option E"
UPDATE client_messagetranslation
SET message = 'Di opción E'
WHERE _fk_coremessageobject IN (2599) AND language = 'ESN';

-- "Speak Option F"
UPDATE client_messagetranslation
SET message = 'Di opción F'
WHERE _fk_coremessageobject IN (2601) AND language = 'ESN';

-- "Voice Guidance"
INSERT INTO client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
VALUES (3813, 'AIR', 'Orientación/Instrucciones/Guía audible', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());

-- "This cell only accepts alpha characters A through Z."
INSERT INTO client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
VALUES (3822, 'AIR', 'Esta celda solo acepta caracteres alfabéticos de la A a la Z.', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());

-- "This cell only accepts valid numerical data."
INSERT INTO client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
VALUES (3823, 'AIR', 'Esta celda solo acepta datos numéricos válidos.', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());

-- "This cell only accepts alphanumerical data."
INSERT INTO client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
VALUES (3824, 'AIR', 'Esta celda solo acepta datos alfanuméricos.', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());

-- "This cell accepts any type of input."
INSERT INTO client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
VALUES (3825, 'AIR', 'Esta celda acepta cualquier tipo de entrada.', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());

-- "Scoring Guide"
INSERT INTO client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
VALUES (3908, 'AIR', 'Guía para la calificación', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW()),
(3909, 'AIR', 'Guía para la calificación', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());

-- "Enter a number between 1 and 100 to define the number of rows."
INSERT INTO client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
VALUES (3962, 'AIR', 'Escribe un número entre 1 y 100 para definir el número de hileras.', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());

-- "Escribe un número entre 1 y 10 para definir el número de columnas."
INSERT INTO client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
VALUES (3963, 'AIR', 'Escribe un número entre 1 y 10 para definir el número de columnas.', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());

-- "System Settings"
INSERT INTO client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
VALUES (3965, 'AIR', 'Configuración del sistema', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());

-- "Search Results"
INSERT INTO client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
VALUES (3966, 'AIR', 'Resultados de la búsqueda', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());

-- "The password you entered is incorrect. To open the navigation bar, enter the correct password."
INSERT INTO client_messagetranslation (_fk_coremessageobject, client, message, language, grade, subject, _key, datealtered)
VALUES (3967, 'AIR', 'La contraseña que escribiste es incorrecta. Para abrir la barra de navegación, escribe la contraseña correcta.  ', 'ESN', '--ANY--', '--ANY--', UNHEX(REPLACE(UUID(),'-','')), NOW());
