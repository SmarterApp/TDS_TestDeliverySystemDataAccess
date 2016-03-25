-- Information in this file contains an example of configuring non-embedded designated support
-- tools for UAAG support.
-- Please see nonembedaccomms.PNG and nonembeddesignatedsupports.PNG in this
-- directory to help visualize changes provided by this script.
 
-- STEP 1
-- to update label values from 'Read Aloud Stimuli' to 'Read Aloud CAT Reading Passages'
--  for all existing tests 
update configs.client_testtool set value = 'Read Aloud CAT Reading Passages'
where type = 'Non-Embedded Designated Supports' 
and code = 'NEDS_RA_STIMULI';


-- STEP 2. To add support for new values for Non-embedded Designated Supports tool
-- you need to work test-by-test.
-- To find out what is testid of given test per test label from 'Your Tests' selection screen
-- (substitute 'xxx' for the test label value)
select testid from configs.client_testproperties where label = 'xxxx';

-- To find out which tests already have Non-Embedded Designated Supports tool:
select context as testid, contexttype from configs.client_testtooltype 
where toolname = 'Non-Embedded Designated Supports';

-- Option1. Test in question already has non-embedded designated supports tool configured.
-- To make non-embedded designated supports tool functional for given test:
-- (context field must be set to testid value).
update configs.client_testtooltype set isfunctional = b'1', isvisible = b'1', isselectable = b'1' 
where context = 'SBAC-ICA-FIXED-G11M' and toolname= 'Non-Embedded Designated Supports';

-- To make non-embedded accommodations tool functional for given test:
-- (context field must be set to testid value).
update configs.client_testtooltype set isfunctional = b'1', isvisible = b'1', isselectable = b'1' 
where context = 'SBAC-ICA-FIXED-G11M' and toolname= 'Non-Embedded Accommodations';

-- Option2. Test in question does not have non-embedded designated supports tool yet.
-- Need to add such tool. Note that context column value must be set to testid value
insert into client_testtooltype 
(clientname, toolname, allowchange, tideselectable, rtsfieldname, isrequired,
tideselectablebysubject, isselectable, isvisible, studentcontrol, tooldescription, 
sortorder, dateentered, origin, source, contexttype, context, dependsontooltype, 
disableonguestsession, isfunctional, testmode, isentrycontrol)
values 
('SBAC','Non-Embedded Designated Supports',1,1,'TDSAcc-DesigSup',0,
1,1,1,0,null,
0,now(),'none','none','TEST','SBAC-ICA-FIXED-G11M',null,0,1,'ALL',0);


-- to add NEDS_RA_Items_ESN and/or NEDS_RA_Stimuli_ESN to a test follow example below;
-- use correct value of your testid for 'context' column.
-- Value of sortorder column control order in which added tools are displayed.
-- You can adjust it based on what other NEDS options this test already have configured. 
insert into client_testtool 
(clientname, type, code, value, isdefault, allowcombine, valuedescription,
context, sortorder, origin, source, contexttype, testmode, equivalentclientcode) values 
('SBAC','Non-Embedded Designated Supports','NEDS_RA_Items_ESN','Read Aloud Items Spanish',
0,1,'Read Aloud Items Spanish','SBAC-ICA-FIXED-G11M',17,'none','none',
'TEST','ALL',null);
insert into client_testtool 
(clientname, type, code, value, isdefault, allowcombine, valuedescription,
context, sortorder, origin, source, contexttype, testmode, equivalentclientcode) values 
('SBAC','Non-Embedded Designated Supports','NEDS_RA_Stimuli_ESN','Read Aloud PT Stimuli-Spanish',
0,1,'Read Aloud Stimuli Spanish','SBAC-ICA-FIXED-G11M',19,'none','none',
'TEST','ALL',null);

-- rebuild cached tools-related tables
call configs._dm_clearaccomcache;
call configs.__slugtestaccoms(null);