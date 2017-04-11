/***********************************************************************************************************************
  File: V1__bugfix_update_spanish_text.sql

  Desc: Replaces a mistranslated spanish word "Someter" (meaning to subjugate) with the correct word for "save".

***********************************************************************************************************************/

use configs;

UPDATE client_messagetranslation
SET message = REPLACE(message, 'Someter', 'Guardar')
WHERE message LIKE '%Someter%';