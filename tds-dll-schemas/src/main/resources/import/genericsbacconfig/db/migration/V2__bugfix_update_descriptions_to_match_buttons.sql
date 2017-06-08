/***********************************************************************************************************************
  File: V2__bugfix_update_descriptions_to_match_buttons.sql

  Desc: Replaces mismatching descriptions and buttons for instructional text

***********************************************************************************************************************/

use configs;

UPDATE client_messagetranslation
SET message = 'Para comenzar tu prueba, selecciona <strong>Comenzar la Prueba Ahora</strong>. Si el Administrador de la prueba te dice que cierres la sesión, selecciona <strong>Regresar al Inicio</strong>.'
WHERE _fk_coremessageobject = 4015 AND language = 'ESN';

UPDATE client_messagetranslation
SET message = '<span>Comenzar la Prueba Ahora</span>'
WHERE _fk_coremessageobject = 2473 AND language = 'ESN';

UPDATE client_messagetranslation
SET message = '<span>Regresar al Inicio</span>'
WHERE _fk_coremessageobject = 2472 AND language = 'ESN';

UPDATE client_messagetranslation
SET message = 'Cuando termines de revisar tus respuestas, selecciona <strong>Enviar Prueba</strong>. No puedes cambiar las respuestas después de enviar la prueba.'
WHERE _fk_coremessageobject = 4024 AND language = 'ESN';

UPDATE client_messagetranslation
SET message = '<span>Enviar prueba</span>'
WHERE _fk_coremessageobject = 2524 AND language = 'ESN';

UPDATE client_messagetranslation
SET message = 'Si el nombre de la prueba y la configuración son correctos, selecciona <strong>Sí, Comenzar la Prueba</strong>. Si no, selecciona <strong>No</strong>.'
WHERE _fk_coremessageobject = 4018 AND language = 'ESN';

UPDATE client_messagetranslation
SET message = '<span>Sí, Comenzar la Prueba</span>'
WHERE _fk_coremessageobject IN (2427, 3421, 2464) AND language = 'ESN';

UPDATE client_messagetranslation
SET message = 'If the test name and settings are correct, choose <strong>Yes, Start My Test</strong>. If not, choose <strong>No</strong>.'
WHERE _fk_coremessageobject = 4018 AND language = 'ENU';

UPDATE client_messagetranslation
SET message = 'Please wait for your TA to approve your request. If you need to cancel your request, select <strong>Cancel the Request and Return to the Login Page</strong>.'
WHERE _fk_coremessageobject = 4031 AND language = 'ENU';

UPDATE tds_coremessageobject
SET message = 'Cancel the Request and Return to the Login Page.'
WHERE _fk_coremessageobject IN (10953, 10988)
