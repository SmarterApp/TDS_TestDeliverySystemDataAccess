-- ----------------------------------------------------------------------------------------------------------------------
-- Description:  Update the casing of the 'Language' accommodation for consistency
--
-- Usage: Execute against the MySQL database server that hosts the itembank and configs databases.
-- ----------------------------------------------------------------------------------------------------------------------
USE configs;

START TRANSACTION;
UPDATE configs.client_testtool SET type = 'Language' WHERE BINARY type = 'language';
COMMIT;