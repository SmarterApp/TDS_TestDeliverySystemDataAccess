drop view if exists itembank_tblsetofadminsubjects;

create view itembank_tblsetofadminsubjects 
/*
Description: to access tables in itembank database mimicking the the concept of synonmys by creating views.

VERSION 	DATE 			AUTHOR 			COMMENTS
001			11/27/2013		Sai V. 			Converted code from SQL Server to MySQL
*/
as 
	select * 
	from tdscore_dev_itembank2012_sandbox.tblsetofadminsubjects;