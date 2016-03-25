-- Elena: Eventually check if sbac and sbac_pt recs from JF are present in our data

-- java code does not use them; Testpackage does not expect anything either
--insert into configs2.tds_clientaccommodationtype select * from configs.tds_clientaccommodationtype where clientname='sbac' ;
--insert into configs2.tds_clientaccommodationvalue select * from configs.tds_clientaccommodationvalue where clientname='sbac';
-- insert into configs2.tds_clientaccommodationtype select * from configs.tds_clientaccommodationtype where clientname='sbac_pt' ;
-- insert into configs2.tds_clientaccommodationvalue select * from configs.tds_clientaccommodationvalue where clientname='sbac_pt';
--insert into configs2.tds_configtype select * from configs.tds_configtype ;
-- insert into configs2.tds_fieldtestpriority select * from configs.tds_fieldtestpriority ;
-- insert into configs2.tds_role select * from configs.tds_role;
-- insert into configs2.tds_systemflags select * from configs.tds_systemflags ;
-- insert into configs2.tds_testproperties select * from configs.tds_testproperties ;
--insert into configs2.client_accommodationfamily select * from configs.client_accommodationfamily where clientname = 'sbac';
--insert into configs2.client_accommodationfamily select * from configs.client_accommodationfamily where clientname = 'sbac_pt';

-- not used by java
--insert into configs2.client_fieldtestpriority select * from configs.client_fieldtestpriority where clientname = 'sbac';
--insert into configs2.client_fieldtestpriority select * from configs.client_fieldtestpriority where clientname = 'sbac_pt';
-- insert into configs2.client_rtsroles select * from configs.client_rtsroles where clientname = 'sbac';
-- insert into configs2.client_rtsroles select * from configs.client_rtsroles where clientname = 'sbac_pt';

-- next two are not used by java code, but TP will populate them
--insert into configs2.client_grade select * from configs.client_grade where clientname = 'sbac';
--insert into configs2.client_language select * from configs.client_language where clientname = 'sbac';
--insert into configs2.client_grade select * from configs.client_grade where clientname = 'sbac_pt';
--insert into configs2.client_language select * from configs.client_language where clientname = 'sbac_pt';

--TP populates it
--insert into configs2.client select * from configs.client where name = 'sbac';
--insert into configs2.client_testeeattribute select * from configs.client_testeeattribute where clientname = 'sbac';
--insert into configs2.client_segmentproperties select * from configs.client_segmentproperties where clientname = 'sbac';
--insert into configs2.client_subject select * from configs.client_subject where clientname = 'sbac';
--insert into configs2.client_test_itemconstraint select * from configs.client_test_itemconstraint where clientname = 'sbac';
--insert into configs2.client_test_itemtypes select * from configs.client_test_itemtypes where clientname = 'sbac';
--insert into configs2.client_testeerelationshipattribute select * from configs.client_testeerelationshipattribute where clientname = 'sbac';
--insert into configs2.client_testeligibility select * from configs.client_testeligibility where clientname = 'sbac';
--insert into configs2.client_testformproperties select * from configs.client_testformproperties where clientname = 'sbac';
--insert into configs2.client_testgrades select * from configs.client_testgrades where clientname = 'sbac';
--insert into configs2.client_testmode select * from configs.client_testmode where clientname = 'sbac';
--insert into configs2.client_timewindow select * from configs.client_timewindow where clientname = 'sbac';
--insert into configs2.client_testwindow select * from configs.client_testwindow where clientname = 'sbac';

-- AFTER dependencies---
-----------------------------------------------
insert into configs2.client_tooldependencies select * from configs.client_tooldependencies where clientname = 'sbac' and context != '*';
insert into configs2.client_tooldependencies select * from configs.client_tooldependencies where clientname = 'sbac_pt' and context != '*';

-- EF: looks like candidates be populated by testpackage, but it is not now and
-- JF data does not have it; 
-- insert into configs2.client_toolusage select * from configs.client_toolusage where clientname = 'sbac';
-- insert into configs2.client_toolusage select * from configs.client_toolusage where clientname = 'sbac_pt';

-- ??? should we?
insert into configs2.client_testtooltype select * from configs.client_testtooltype  where clientname = 'sbac_pt' and contexttype = 'test' and context = '*' and toolname != 'language' ;
insert into configs2.client_testtooltype select * from configs.client_testtooltype  where clientname = 'sbac' and contexttype = 'test' and context = '*' and toolname != 'language' ;
insert into configs2.client_testtool select * from configs.client_testtool  where clientname = 'sbac_pt' and contexttype = 'test' and context = '*' and type != 'language' ;
insert into configs2.client_testtool select * from configs.client_testtool  where clientname = 'sbac' and contexttype = 'test' and context = '*' and type != 'language' ;

-- EF: discuss
insert into configs2.client_testtooltype 
  select * from 
   configs.client_testtooltype src
  where  src.clientname = 'SBAC_PT'
  and not exists (select 1
					from configs2.client_testtooltype dest 
					where dest.ClientName = src.ClientName
					and dest.toolname = src.toolname
					and dest.Context = src.Context
					and dest.ContextType = src.ContextType );

insert into configs2.client_testtool
  select * from 
   configs.client_testtool src
  where  src.clientname = 'SBAC_PT'
  and Code like '%&%' and context != '*'
  and not exists (select 1
					from configs2.client_testtool dest 
					where dest.ClientName = src.ClientName
					and dest.type = src.type
					and dest.Code = src.code
					and dest.Context = src.Context
					and dest.ContextType = src.ContextType );
					-------------------------------
					
changes i made in sai's data
update itembank.tblsetofadminsubjects set StartAbility = 0;	

-- this should only be done for items in fixed format tests!
update itembank.tblsetofadminitems set irt_b = -9999;	
update itembank.tblsetofadminitems set bvector = -9999;			










