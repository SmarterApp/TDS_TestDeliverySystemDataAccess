-- ----------------------------------------------------------------------------------------------------------------------
-- Description:  Delete assessment records for ALL assessments from the itembank and configs database
--
-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! IMPORTANT !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
-- TAKE A BACKUP OF YOUR ITEMBANK AND CONFIGS DATABASES PRIOR TO RUNNING THIS SCRIPT.
--
-- This script will remove ALL records for ALL assessments that were loaded in the TDS system.  To make sure your
-- environment is in sync, you should also remove ALL records from the assessment collection in ART and the
-- testSpecification in TestSpecBank.  With a clean slate, the assessments can be reloaded into the system.
--
-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
--
-- Usage: Execute against the MySQL database server that hosts the itembank and configs databases.
-- ----------------------------------------------------------------------------------------------------------------------

START TRANSACTION;
DELETE FROM itembank.affinitygroupitem;
DELETE FROM itembank.affinitygroup;
DELETE FROM itembank.testformitem;
DELETE FROM itembank.testform;
DELETE FROM itembank.tbladminstimulus;
DELETE FROM itembank.itemmeasurementparameter;
DELETE FROM itembank.tblsetofadminitems;
DELETE FROM itembank.tbladminstrand;
DELETE FROM itembank.tblitemselectionparm;
DELETE FROM itembank.testcohort;
DELETE FROM itembank.tblsetofadminsubjects;
DELETE FROM itembank.tbltestadmin;
DELETE FROM itembank.tblitemprops;
DELETE FROM itembank.tblitem;
DELETE FROM itembank.tblstimulus;
DELETE FROM itembank.tblstrand;
DELETE FROM itembank.tblsubject;
COMMIT;

START TRANSACTION;
DELETE FROM configs.client_testproperties;
DELETE FROM configs.client_testmode;
DELETE FROM configs.client_segmentproperties;
DELETE FROM configs.client_testformproperties;
DELETE FROM configs.client_testwindow;
DELETE FROM configs.client_testgrades;
DELETE FROM configs.client_testeligibility;
DELETE FROM configs.client_test_itemtypes;
DELETE FROM configs.client_test_itemconstraint;
DELETE FROM configs.client_testtooltype;
DELETE FROM configs.client_testtool;
COMMIT;