-- =============================================
-- add column with column name 'unittestflag' to all tables
-- in tdscore_test_itembank. DB  on MySQL
-- =============================================

alter table tdscore_test_itembank._dblatency
        add unittestflag int null;

alter table tdscore_test_itembank._sys_formtestitems
        add unittestflag int null;

alter table tdscore_test_itembank._testupdate
        add unittestflag int null;

alter table tdscore_test_itembank.aa_itemcl
        add unittestflag int null;

alter table tdscore_test_itembank.affinitygroup
        add unittestflag int null;

alter table tdscore_test_itembank.affinitygroupitem
        add unittestflag int null;

alter table tdscore_test_itembank.alloweditemprops
        add unittestflag int null;

alter table tdscore_test_itembank.configsloaded
        add unittestflag int null;

alter table tdscore_test_itembank.importitemcohorts
        add unittestflag int null;

alter table tdscore_test_itembank.importtestcohorts
        add unittestflag int null;

alter table tdscore_test_itembank.itemmeasurementparameter
        add unittestflag int null;

alter table tdscore_test_itembank.itemscoredimension
        add unittestflag int null;

alter table tdscore_test_itembank.loader_accommodations
        add unittestflag int null;

alter table tdscore_test_itembank.loader_affinitygroups
        add unittestflag int null;

alter table tdscore_test_itembank.loader_affinityitems
        add unittestflag int null;

alter table tdscore_test_itembank.loader_contentlevels
        add unittestflag int null;

alter table tdscore_test_itembank.loader_errors
        add unittestflag int null;

alter table tdscore_test_itembank.loader_formitems
        add unittestflag int null;

alter table tdscore_test_itembank.loader_forms
        add unittestflag int null;

alter table tdscore_test_itembank.loader_itembank
        add unittestflag int null;

alter table tdscore_test_itembank.loader_itemclancestors
        add unittestflag int null;

alter table tdscore_test_itembank.loader_itemproperties
        add unittestflag int null;

alter table tdscore_test_itembank.loader_items
        add unittestflag int null;

alter table tdscore_test_itembank.loader_itemscoredimension
        add unittestflag int null;

alter table tdscore_test_itembank.loader_logicaltests
        add unittestflag int null;

alter table tdscore_test_itembank.loader_measurementparameter
        add unittestflag int null;

alter table tdscore_test_itembank.loader_proficiencylevels
        add unittestflag int null;

alter table tdscore_test_itembank.loader_rulesrelaxvalidation
        add unittestflag int null;

alter table tdscore_test_itembank.loader_stimuli
        add unittestflag int null;

alter table tdscore_test_itembank.loader_stimulusproperties
        add unittestflag int null;

alter table tdscore_test_itembank.loader_strands
        add unittestflag int null;

alter table tdscore_test_itembank.loader_tests
        add unittestflag int null;

alter table tdscore_test_itembank.measurementmodel
        add unittestflag int null;

alter table tdscore_test_itembank.measurementparameter
        add unittestflag int null;

alter table tdscore_test_itembank.performancelevels
        add unittestflag int null;

alter table tdscore_test_itembank.projects
        add unittestflag int null;

alter table tdscore_test_itembank.setoftestgrades
        add unittestflag int null;

alter table tdscore_test_itembank.tbladminstimulus
        add unittestflag int null;

alter table tdscore_test_itembank.tbladminstrand
        add unittestflag int null;

alter table tdscore_test_itembank.tblalternatetest
        add unittestflag int null;

alter table tdscore_test_itembank.tblclient
        add unittestflag int null;

alter table tdscore_test_itembank.tblitem
        add unittestflag int null;

alter table tdscore_test_itembank.tblitembank
        add unittestflag int null;

alter table tdscore_test_itembank.tblitemprops
        add unittestflag int null;

alter table tdscore_test_itembank.tblsetofadminitems
        add unittestflag int null;

alter table tdscore_test_itembank.tblsetofadminsubjects
        add unittestflag int null;

alter table tdscore_test_itembank.tblsetofitemstimuli
        add unittestflag int null;

alter table tdscore_test_itembank.tblsetofitemstrands
        add unittestflag int null;

alter table tdscore_test_itembank.tblstimulus
        add unittestflag int null;

alter table tdscore_test_itembank.tblstrand
        add unittestflag int null;

alter table tdscore_test_itembank.tblsubject
        add unittestflag int null;

alter table tdscore_test_itembank.tbltestadmin
        add unittestflag int null;

alter table tdscore_test_itembank.testcohort
        add unittestflag int null;

alter table tdscore_test_itembank.testform
        add unittestflag int null;

alter table tdscore_test_itembank.testformitem
        add unittestflag int null;

