drop view if exists itembank_testformitem;

create view itembank_testformitem 
/*
Description: to access tables in itembank database mimicking the the concept of synonmys by creating views.

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/2/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
as 
	select * 
	from tdscore_dev_itembank2012_sandbox.testformitem;