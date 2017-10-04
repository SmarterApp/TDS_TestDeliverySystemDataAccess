-- ---------------------------------------------------------------------------------------------------------------------
-- Description:  Insert records into the session._externs table for the SBAC and SBAC_PT clients.  Without these records,
-- the session.timelimits view will not return any records.  If the session.timelimits view does not return any records,
-- a user cannot log into the Proctor application.
--
-- Usage: Execute against the session database after the schema has been created.
-- ---------------------------------------------------------------------------------------------------------------------
USE session;

START TRANSACTION;
INSERT IGNORE session._externs(clientname, environment, shiftwindowstart, shiftwindowend, shiftformstart, shiftformend, shiftftstart, shiftftend)
VALUES('SBAC', 'Development', 0, 0, 0, 0, 0, 0);
INSERT IGNORE session._externs(clientname, environment, shiftwindowstart, shiftwindowend, shiftformstart, shiftformend, shiftftstart, shiftftend)
VALUES('SBAC_PT', 'Development', 0, 0, 0, 0, 0, 0);
COMMIT;
