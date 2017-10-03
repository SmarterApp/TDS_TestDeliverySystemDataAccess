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
DELETE FROM itembank.affinitygroupitem; -- nothing to load
DELETE FROM itembank.affinitygroup; -- nothing to load
-- DELETE FROM itembank.testformitem; -- worked fine
-- DELETE FROM itembank.testform; -- worked fine
-- DELETE FROM itembank.tbladminstimulus; -- worked fine
-- DELETE FROM itembank.itemmeasurementparameter; -- worked fine
-- DELETE FROM itembank.itemscoredimension; -- worked fine
-- DELETE FROM itembank.tblsetofadminitems; -- worked fin
-- DELETE FROM itembank.tbladminstrand; -- worked fine
DELETE FROM itembank.tblitemselectionparm; -- nothing to load
DELETE FROM itembank.testcohort; -- nothing to load
-- DELETE FROM itembank.tblsetofadminsubjects; -- worked fine
-- DELETE FROM itembank.tbltestadmin; -- do not need to run
-- DELETE FROM itembank.tblitemprops; -- worked fine; used script from ~/Desktop
-- DELETE FROM itembank.tblitem; -- worked fine
-- DELETE FROM itembank.tblstimulus; -- worked fine
-- DELETE FROM itembank.tblstrand; -- did not run; not sure what needs to be in here; might not need to if this is for scoring...?
-- DELETE FROM itembank.tblsubject; -- only loaded SBAC-StudentHelp
COMMIT;

START TRANSACTION;
-- DELETE FROM configs.client_testproperties; -- worked fine
-- DELETE FROM configs.client_testmode; -- worked fine
-- DELETE FROM configs.client_segmentproperties; -- worked fine
-- DELETE FROM configs.client_testformproperties; -- worked fine
-- DELETE FROM configs.client_testwindow; -- worked fine
-- DELETE FROM configs.client_testgrades; -- worked fine
-- DELETE FROM configs.client_testeligibility; -- worked fine
-- DELETE FROM configs.client_test_itemtypes; -- worked fine
-- DELETE FROM configs.client_test_itemconstraint; -- worked fine
-- DELETE FROM configs.client_testtooltype; -- commented out 'PrintSize' (line 20)
-- DELETE FROM configs.client_testtool; -- commented out 'PrintSize' (lines 27 - 32)
-- DELETE FROM configs.client_tooldependencies; -- worked fine
COMMIT;