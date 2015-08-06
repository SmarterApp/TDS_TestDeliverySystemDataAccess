-- these are messagging recs that still need fixing
use configs;
update configs.client_messagetranslation  T, configs.tds_coremessageobject O
 set T.message = '<span style="display:none; lang="es-mx">Spanish text to be spoken</span>'
where T._fk_coremessageobject = O._key and O.appkey='TTSCheck.Label.HearMessageSpanish' 
and T.language = 'ENU' and client <> 'AIR';
update configs.client_messagetranslation  T, configs.tds_coremessageobject O
 set T.message = '<span style="display:none; lang="en-us">This is some sample text to test your settings.</span>'
where T._fk_coremessageobject = O._key and O.appkey='TTSCheck.Label.HearMessageEnglish' 
and T.language = 'ENU' and client <> 'AIR';

-- we want Streamline to be called 'Streamlined Interface'
update configs.tds_coremessageobject set message = 'Streamlined Interface' 
where Appkey = 'Streamlined Mode' and ownerapp = 'student';

-- delete from configs.__appmessages;
delete from configs.__appmessagecontexts;

-- these are tools related recs that still need the hack;
update configs.client_testtooltype set isvisible = b'0', isselectable = b'0' 
where toolname = 'Print on Request' and clientname = 'sbac_pt';
update configs.client_testtooltype set isvisible = b'0', isselectable = b'0' 
where toolname = 'Braille Type' and clientname = 'sbac_pt';

call configs._dm_clearaccomcache;
call configs.__slugtestaccoms(null);


-- fixing segment labels to have segment position in them
update configs.client_segmentproperties
set label = replace(label, 'segment', concat('Segment ', segmentposition))
where label like '%segment';

-- setting entryapproval, exitapproval and so on per segment
update client_segmentproperties set IsPermeable = b'0', entryApproval = b'0', exitApproval = b'0', itemReview = b'1'
where segmentposition = 1 and clientname = 'sbac_pt';
update client_segmentproperties set IsPermeable = b'1', entryApproval = b'0', exitApproval = b'0', itemReview = b'0'
where segmentposition > 1 and clientname = 'sbac_pt';

-- for test 'SBAC Math HS-MATH-10' language for two testform was not correct
update itembank.testform set language = 'ENU-Braille' where _key = '187-634';
update itembank.testform set language = 'ESN' where _key = '187-635';
update client_testformproperties set language = 'ENU-Braille' where _efk_testform = '187-634';
update client_testformproperties set language = 'ESN' where _efk_testform = '187-635';


-- let's  give performance tests 'Performance Tast' labels
update client_testproperties set label  = replace(label, 'Test', 'Task') where 
label like '%performance%';
-- now, we have couple of misspelled 'performace' tests
update client_testproperties set label = replace(label, 'Performace Test', 'Performance Task') where
label like '%performace%';

-- some test has plural test in the lable.
update configs.client_testproperties set label = replace(label, 'Tests', 'Test') where label like '%Tests';

-- reset number of test opportunities to 99
update configs.client_testproperties set maxopportunities = 99 
where clientname = 'sbac_pt' and testid not like '%Training-Student Help%';

update configs.client_testproperties set sortorder = 1 where testid like 'SBAC MATH %';
update configs.client_testproperties set sortorder = 3 where testid like 'SBAC-Mathematics%';
update configs.client_testproperties set sortorder = 5 where testid like 'SBAC-Perf-Math%';
update configs.client_testproperties set sortorder = 2 where testid like 'SBAC ELA %';
update configs.client_testproperties set sortorder = 4 where testid like 'SBAC-ELA-%';
update configs.client_testproperties set sortorder = 6 where testid like 'SBAC-Perf-ELA%';
update configs.client_testproperties set sortorder = 29 where testid = 'SBAC-Student Help-11';

-- copy of Sai's script to disable expired forms
-- Script start
create temporary table tmptbl (formpartitionkeys varchar(100));

insert into tmptbl values ('187-573');
insert into tmptbl values ('187-574');
insert into tmptbl values ('187-561');
insert into tmptbl values ('187-562');
insert into tmptbl values ('187-563');
insert into tmptbl values ('187-564');
insert into tmptbl values ('187-565');
insert into tmptbl values ('187-566');
insert into tmptbl values ('187-567');
insert into tmptbl values ('187-568');
insert into tmptbl values ('187-569');
insert into tmptbl values ('187-570');
insert into tmptbl values ('187-571');
insert into tmptbl values ('187-572');
insert into tmptbl values ('187-507');
insert into tmptbl values ('187-508');
insert into tmptbl values ('187-509');
insert into tmptbl values ('187-510');
insert into tmptbl values ('187-511');
insert into tmptbl values ('187-512');
insert into tmptbl values ('187-513');
insert into tmptbl values ('187-514');
insert into tmptbl values ('187-515');
insert into tmptbl values ('187-625');
insert into tmptbl values ('187-624');
insert into tmptbl values ('187-626');
insert into tmptbl values ('187-607');
insert into tmptbl values ('187-606');
insert into tmptbl values ('187-608');
insert into tmptbl values ('187-610');
insert into tmptbl values ('187-609');
insert into tmptbl values ('187-611');
insert into tmptbl values ('187-613');
insert into tmptbl values ('187-612');
insert into tmptbl values ('187-614');
insert into tmptbl values ('187-616');
insert into tmptbl values ('187-615');
insert into tmptbl values ('187-617');
insert into tmptbl values ('187-619');
insert into tmptbl values ('187-618');
insert into tmptbl values ('187-620');
insert into tmptbl values ('187-622');
insert into tmptbl values ('187-621');
insert into tmptbl values ('187-623');
insert into tmptbl values ('187-599');
insert into tmptbl values ('187-600');
insert into tmptbl values ('187-575');
insert into tmptbl values ('187-576');
insert into tmptbl values ('187-579');
insert into tmptbl values ('187-580');
insert into tmptbl values ('187-583');
insert into tmptbl values ('187-584');
insert into tmptbl values ('187-587');
insert into tmptbl values ('187-588');
insert into tmptbl values ('187-591');
insert into tmptbl values ('187-592');
insert into tmptbl values ('187-595');
insert into tmptbl values ('187-596');
insert into tmptbl values ('187-601');
insert into tmptbl values ('187-602');
insert into tmptbl values ('187-577');
insert into tmptbl values ('187-578');
insert into tmptbl values ('187-581');
insert into tmptbl values ('187-582');
insert into tmptbl values ('187-585');
insert into tmptbl values ('187-586');
insert into tmptbl values ('187-589');
insert into tmptbl values ('187-590');
insert into tmptbl values ('187-593');
insert into tmptbl values ('187-594');
insert into tmptbl values ('187-597');
insert into tmptbl values ('187-598');
insert into tmptbl values ('187-534');
insert into tmptbl values ('187-535');
insert into tmptbl values ('187-536');
insert into tmptbl values ('187-516');
insert into tmptbl values ('187-517');
insert into tmptbl values ('187-518');
insert into tmptbl values ('187-522');
insert into tmptbl values ('187-523');
insert into tmptbl values ('187-524');
insert into tmptbl values ('187-528');
insert into tmptbl values ('187-529');
insert into tmptbl values ('187-530');
insert into tmptbl values ('187-537');
insert into tmptbl values ('187-538');
insert into tmptbl values ('187-539');
insert into tmptbl values ('187-519');
insert into tmptbl values ('187-520');
insert into tmptbl values ('187-521');
insert into tmptbl values ('187-525');
insert into tmptbl values ('187-526');
insert into tmptbl values ('187-527');
insert into tmptbl values ('187-531');
insert into tmptbl values ('187-532');
insert into tmptbl values ('187-533');
insert into tmptbl values ('187-603');


/*
select *
from client_testformproperties tfp
where clientname = 'SBAC_PT'
	  and not exists (select 1 from tmptbl t
					  where t.formpartitionkeys = _efk_testform);
*/

update client_testformproperties tfp
set startdate = '2001-01-01'
  , enddate = '2001-01-01'
where clientname = 'SBAC_PT'
	  and not exists (select 1 from tmptbl t
					  where t.formpartitionkeys = tfp._efk_testform);
drop temporary table tmptbl;
-- Script end


