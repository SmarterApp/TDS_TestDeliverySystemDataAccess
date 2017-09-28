-- ----------------------------------------------------------------------------------------------------------------------
-- Description:  Correct a case-sensitivity bug in the itembank.loader_main stored procedure that incorrectly names a
-- directory in the filepath.  The stored procedure names the filepath wiht a lower-case "i", but the program expects
-- the filepath to have an upper-case "I".  This SQL is executed during the db-schema-setup.sh script.
--
-- Usage: Execute against the itembank database after the schema has been created and sample test data has been loaded.
-- ----------------------------------------------------------------------------------------------------------------------
USE itembank;

START TRANSACTION;
UPDATE itembank.tblitem
SET filepath = CONCAT("I", SUBSTRING(filepath, 2));
COMMIT;