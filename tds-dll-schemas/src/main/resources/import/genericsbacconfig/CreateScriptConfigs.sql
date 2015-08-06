-- run 
select 'use configs;';
-- tools related part
select 'delete from client_tooldependencies;';
select 'delete from client_testtool';
select 'delete from client_testtooltype;';

SELECT 'insert into client_testtooltype values '
+ '(' + coalesce('''' + REPLACE([clientname], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([ToolName], '''', '''''') + '''', 'null')
+ ',' + COALESCE(CAST([AllowChange] AS VARCHAR(50)), 'NULL')
+ ',' + COALESCE(CAST([TIDESelectable] AS VARCHAR(50)), 'NULL')
+ ',' + coalesce('''' + REPLACE([RTSFieldName], '''', '''''') + '''', 'null')
+ ',' + COALESCE(CAST([IsRequired] AS VARCHAR(50)), 'NULL')
+ ',' + COALESCE(CAST([TIDESelectableBySubject] AS VARCHAR(50)), 'NULL')
+ ',' + COALESCE(CAST([IsSelectable] AS VARCHAR(50)), 'NULL')
+ ',' + COALESCE(CAST([IsVisible] AS VARCHAR(50)), 'NULL')
+ ',' + COALESCE(CAST([StudentControl] AS VARCHAR(50)), 'NULL')
+ ',' + coalesce('''' + REPLACE([ToolDescription], '''', '''''') + '''', 'null')
+ ',' + COALESCE(CAST([SortOrder] AS VARCHAR(50)), 'NULL')
+ ',' + coalesce('''' + convert(varchar(30), [dateEntered], 121) + '''', 'null')
+ ',' + coalesce('''' + REPLACE([origin], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([source], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([ContextType], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([Context], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([DependsOnToolType], '''', '''''') + '''', 'null')
+ ',' + COALESCE(CAST([DisableOnGuestSession] AS VARCHAR(50)), 'NULL')
+ ',' + COALESCE(CAST([IsFunctional] AS VARCHAR(50)), 'NULL')
+ ',' + coalesce('''' + REPLACE([TestMode], '''', '''''') + '''', 'null')
+ ',' + '0'
+ ');' 
FROM [TDSGEO_Test_Configs].[dbo].[client_testtooltype] where clientname = 'sbac_pt';


SELECT 'insert into client_testtool values '
+ '(' + coalesce('''' + REPLACE([clientname], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([Type], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([Code], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([Value], '''', '''''') + '''', 'null')
+ ',' + COALESCE(CAST([IsDefault] AS VARCHAR(50)), 'NULL')
+ ',' + COALESCE(CAST([AllowCombine] AS VARCHAR(50)), 'NULL')
+ ',' + coalesce('''' + REPLACE([ValueDescription], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([Context], '''', '''''') + '''', 'null')
+ ',' + COALESCE(CAST([SortOrder] AS VARCHAR(50)), 'NULL')
+ ',' + coalesce('''' + REPLACE([origin], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([source], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([ContextType], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([TestMode], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([EquivalentClientCode], '''', '''''') + '''', 'null')
+ ');'
FROM [tdsgeo_test_configs].[dbo].[client_testtool] where ClientName = 'sbac_pt';

SELECT 'insert into client_tooldependencies values '
+ '(' + coalesce('''' + REPLACE([Context], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([ContextType], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([IfType], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([IfValue], '''', '''''') + '''', 'null')
+ ',' + COALESCE(CAST([IsDefault] AS VARCHAR(50)), 'NULL')
+ ',' + coalesce('''' + REPLACE([ThenType], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([ThenValue], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([clientname], '''', '''''') + '''', 'null')
+ ',' + COALESCE( 'UNHEX(REPLACE(' + '''' + CAST([_Key] AS VARCHAR(50)) + '''' + ',' + '''-''' + ',' + '''''' + '))', '''''')
+ ',' + coalesce('''' + REPLACE([TestMode], '''', '''''') + '''', 'null')
+ ');'
FROM [tdsgeo_test_configs].[dbo].[client_tooldependencies] where clientname = 'sbac_pt';

-- messaging related part 
select 'delete from client_messagetranslation;';
select 'delete from tds_coremessageuser;';
select 'delete from tds_coremessageobject;';

 SELECT 'insert into tds_coremessageobject values '
+ '(' + coalesce('''' + REPLACE([Context], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([ContextType], '''', '''''') + '''', 'null')
+ ',' + COALESCE(CAST([MessageID] AS VARCHAR(50)), 'NULL')
+ ',' + coalesce('''' + REPLACE([OwnerApp], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([AppKey], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([Message], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([ParaLabels], '''', '''''') + '''', 'null')
+ ',' + COALESCE(CAST([_Key] AS VARCHAR(50)), 'NULL')
+ ',' + coalesce('''' + REPLACE([fromClient], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + convert(varchar(30), [dateAltered], 121) + '''', 'null')
+ ',' + coalesce('''' + REPLACE([Nodes], '''', '''''') + '''', 'null')
+ ',' + COALESCE(CAST([isMessageShowToUser] AS VARCHAR(50)), 'NULL')
+ ');'
FROM [tdsgeo_test_configs].[dbo].[tds_coremessageobject];


SELECT 'insert into tds_coremessageuser values '
+ '(' + COALESCE(CAST([_fk_CoreMessageObject] AS VARCHAR(50)), 'NULL')
+ ',' + coalesce('''' + REPLACE([SystemID], '''', '''''') + '''', 'null')
+ ');'
FROM [tdsgeo_test_configs].[dbo].[tds_coremessageuser]
where _fk_CoreMessageObject in (select _key from [tdsgeo_test_configs].[dbo].[tds_coremessageobject]); 

SELECT 'insert into client_messagetranslation values '
+ '(' + COALESCE(CAST([_fk_CoreMessageObject] AS VARCHAR(50)), 'NULL')
+ ',' + coalesce('''' + REPLACE([client], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([Message], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([Language], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([Grade], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([Subject], '''', '''''') + '''', 'null')
+ ',' + COALESCE( 'UNHEX(REPLACE(' + '''' + CAST([_key] AS VARCHAR(50)) + '''' + ',' + '''-''' + ',' + '''''' + '))', '''''')
+ ',' + coalesce('''' + convert(varchar(30), [dateAltered], 121) + '''', 'null')
+ ');'
FROM [tdsgeo_test_configs].[dbo].[client_messagetranslation]
where (client = 'sbac_pt' or Client = 'sbac' or Client= 'AIR') 
and _fk_CoreMessageObject in (select _key from [tdsgeo_test_configs].[dbo].[tds_coremessageobject]);


---
select 'delete from system_applicationsettings;';
select 'delete from tds_applicationsettings';
select 'delete from client_systemflags;'; 

SELECT 'insert into tds_applicationsettings values '
+ '(' + COALESCE( 'UNHEX(REPLACE(' + '''' + CAST([_key] AS VARCHAR(50)) + '''' + ',' + '''-''' + ',' + '''''' + '))', '''''')
+ ',' + coalesce('''' + REPLACE([Environment], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([AppName], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([Property], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([Type], '''', '''''') + '''', 'null')
+ ',' + COALESCE(CAST([IsOperational] AS VARCHAR(50)), 'NULL')
+ ',' + 'NULL'
+ ');'
FROM [tdsgeo_test_configs].[dbo].[tds_applicationsettings];

SELECT 'insert into system_applicationsettings values '
+ '(' + coalesce('''' + REPLACE([clientname], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([Environment], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([AppName], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([Property], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([Type], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([Value], '''', '''''') + '''', 'null')
+ ',' + COALESCE( 'UNHEX(REPLACE(' + '''' + CAST([_fk_TDS_ApplicationSettings] AS VARCHAR(50)) + '''' + ',' + '''-''' + ',' + '''''' + '))', '''''')
+ ',' + '''*'''  +  ','  +  '''*'''
+ ');'
FROM [tdsgeo_test_configs].[dbo].[system_applicationsettings]
where clientname = 'sbac_pt'
and _fk_tds_applicationsettings in (select _key from [tdsgeo_test_configs].[dbo].[tds_applicationsettings]);


SELECT 'insert into client_systemflags values '
+ '(' + coalesce('''' + REPLACE([AuditObject], '''', '''''') + '''', 'null')
+ ',' + COALESCE(CAST([IsOn] AS VARCHAR(50)), 'NULL')
+ ',' + coalesce('''' + REPLACE([Description], '''', '''''') + '''', 'null')
+ ',' + coalesce('''' + REPLACE([ClientName], '''', '''''') + '''', 'null')
+ ',' + COALESCE(CAST([IsPracticeTest] AS VARCHAR(50)), 'NULL')
+ ',' + coalesce('''' + convert(varchar(30), [DateChanged], 121) + '''', 'null')
+ ',' + coalesce('''' + convert(varchar(30), [DatePublished], 121) + '''', 'null')
+ ');'
FROM [tdsgeo_test_configs].[dbo].[client_systemflags]
where clientname = 'sbac_pt';

