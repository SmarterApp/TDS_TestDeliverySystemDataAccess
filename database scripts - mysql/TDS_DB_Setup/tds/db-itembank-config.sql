-- ----------------------------------------------------------------------------------------------------------------------
-- Description:  Insert seed data into the itembank database.  This SQL came directly from the Load Configuration Data
-- section here: https://bitbucket.org/sbacoss/tds_release.  This SQL is executed during the db-schema-setup.sh script.
--
-- Usage: Execute against the itembank database after the schema has been created.
-- ----------------------------------------------------------------------------------------------------------------------
USE itembank;

START TRANSACTION;
INSERT INTO `itembank`.`tblclient` (`name`, `description`,`homepath`)
VALUES ('SBAC', NULL, '/usr/local/tomcat/resources/tds/');

INSERT INTO `itembank`.`tblitembank`(`_fk_client`,`homepath`,`itempath`,`stimulipath`,`name`,`_efk_itembank`,`_key`,`contract`)
VALUES (1, 'bank/', 'items/', 'stimuli/', NULL, 200, 200, NULL);

INSERT INTO `itembank`.`tblclient` (`name`, `description`,`homepath`)
VALUES ('SBAC_PT', NULL, '/usr/local/tomcat/resources/tds/');

INSERT INTO `itembank`.`tblitembank`(`_fk_client`,`homepath`,`itempath`,`stimulipath`,`name`,`_efk_itembank`,`_key`,`contract`)
VALUES (2, 'bank/', 'items/', 'stimuli/', NULL, 187, 187, NULL);
COMMIT;