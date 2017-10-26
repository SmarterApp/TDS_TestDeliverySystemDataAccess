-- ---------------------------------------------------------------------------------------------------------------------
-- Description:  Insert database records to create an Illustration Glossary for an assessment.  Records are inserted
-- into the following tables:
--
--  * configs.client_testtooltype
--  * configs.client_testtool
--  * configs.tds_coremessageobject
--  * configs.tds_coremessageuser
--
--  NOTE:  The Illustration Glossary does not have any entries in the configs.client_tooldependencies table.
--
-- Records will be deleted from the following tables, which is required to ensure the updated messages are displayed on
-- the client:
--
--  * configs.__appmessages
--  * configs.__appmessagecontexts
--
-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! IMPORTANT !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
--
-- This script must be updated an run for EACH assessment that needs an Illustration Glossary accommodation
--
-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
--
-- Usage: Update the placeholder values in the script below then execute the script:
--
-- PLACEHOLDERS
-- ------------
-- * [SBAC for production assessments, SBAC_PT for practice assessments]
-- * [the value of the testid column from configs.tblsetofadminsubjects for the assessment]
-- ---------------------------------------------------------------------------------------------------------------------
USE configs;

START TRANSACTION;
INSERT INTO client_testtooltype(
    clientname,
    toolname,
    allowchange,
    tideselectable,
    rtsfieldname,
    isrequired,
    tideselectablebysubject,
    isselectable,
    isvisible,
    studentcontrol,
    tooldescription,
    sortorder,
    dateentered,
    origin,
    source,
    contexttype,
    context,
    dependsontooltype,
    disableonguestsession,
    isfunctional,
    testmode,
    isentrycontrol)
VALUES (
    '[SBAC for production assessments, SBAC_PT for practice assessments]', -- clientname
    'Illustration Glossary', -- toolname
    1, -- allowchange
    1, -- tideselectable
    'TDSAcc-ILG', -- rtsfieldname
    0, -- isrequired
    0, -- tideselectablebysubject
    1, -- isselectable
    1, -- isvisible
    0, -- studentcontrol
    NULL, -- tooldescription
    0, -- sortorder
    NOW(), -- dateentered
    'configs_insert_ig_accommodation.sql', -- origin
    'configs_insert_ig_accommodation.sql', -- source
    'TEST', -- contexttype
    '[the value of the testid column from configs.tblsetofadminsubjects for the assessment]', -- context
    NULL, -- dependsontooltype
    0, -- disable on guest session
    1, -- isfunctional
    'ALL', -- testmode
    0); -- isentrycontrol
COMMIT;

START TRANSACTION;
INSERT INTO client_testtool(
    clientname,
    type,
    code,
    value,
    isdefault,
    allowcombine,
    valuedescription,
    context,
    sortorder,
    origin,
    source,
    contexttype,
    testmode,
    equivalentclientcode)
VALUES (
    '[SBAC for production assessments, SBAC_PT for practice assessments]', -- clientname
    'Illustration Glossary', -- type
    'TDS_ILG0', -- code
    'Do not show Illustration Glossary', -- value
    1, -- isdefault
    0, -- allowcombine
    'Do not show Illustration Glossary', -- valuedescription
    '[the value of the testid column from configs.tblsetofadminsubjects]', -- context
    -1, -- sortorder
    'configs_insert_ig_accommodation.sql', -- origin
    'configs_insert_ig_accommodation.sql', -- source
    'TEST', -- contexttype
    'ALL', -- testmode
    NULL), -- equivalentclientcode
(
    '[SBAC for production assessments, SBAC_PT for practice assessments]', -- clientname
    'Illustration Glossary', -- type
    'TDS_ILG1', -- code
    'Show Illustration Glossary', -- value
    0, -- isdefault
    0, -- allowcombine
    'Show Illustration Glossary', -- valuedescription
    '[the value of the testid column from configs.tblsetofadminsubjects]', -- context
    1, -- sortorder
    'configs_insert_ig_accommodation.sql', -- origin
    'configs_insert_ig_accommodation.sql', -- source
    'TEST', -- contexttype
    'ALL', -- testmode
    NULL); -- equivalentclientcode
COMMIT;

START TRANSACTION;
INSERT INTO tds_coremessageobject(
    context,
    contexttype,
    messageid,
    ownerapp,
    appkey,
    message,
    fromclient
)
SELECT
    'testshell.aspx', -- context
    'ClientSide', -- contexttype
    IFNULL(MAX(messageid), 0) + 1, -- messageid
    'Student', -- ownerapp
    'TDS.WordList.illustration', -- appkey
    'Illustration', -- message
    'AIR' -- fromclient
FROM
    tds_coremessageobject;
COMMIT;

START TRANSACTION;
INSERT INTO tds_coremessageuser(
    _fk_coremessageobject,
    systemid)
SELECT
    MAX(_key),
    'Student'
FROM
    tds_coremessageobject;
COMMIT;

-- needed in order to reset/recalc all the messages
START TRANSACTION;
DELETE FROM __appmessages;
DELETE FROM __appmessagecontexts;
COMMIT;