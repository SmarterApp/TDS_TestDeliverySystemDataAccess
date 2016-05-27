/* Add records for SBAC and SBAC_PT to the session._externs table. This table is referenced by the externs view. */
/* The value for the "environment" field should match the value for the "environment" field that is set in the client_externs table (see gen2.sql) */


USE session;
START TRANSACTION;
INSERT _externs (clientname, environment, shiftwindowstart, shiftwindowend, shiftformstart, shiftformend, shiftftstart, shiftftend)
VALUES('SBAC', 'Development', 0, 0, 0, 0, 0, 0);
INSERT _externs (clientname, environment, shiftwindowstart, shiftwindowend, shiftformstart, shiftformend, shiftftstart, shiftftend)
VALUES('SBAC_PT', 'Development', 0, 0, 0, 0, 0, 0);
COMMIT;
