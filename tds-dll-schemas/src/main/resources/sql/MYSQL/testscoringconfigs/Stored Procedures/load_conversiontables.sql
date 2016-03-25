DELIMITER $$

drop procedure if exists `load_conversiontables` $$

create procedure `load_conversiontables` (
/*
Description: Entry point for loading itembank tables from the testpackage file in xml format.

VERSION 	DATE 			AUTHOR 			COMMENTS
001			6/30/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
)
begin

	insert into conversiontables(tablename, invalue, isinvaluestring, outvalue, isoutvaluestring, clientname)
	select distinct ct.conversiontablename
		 , tup.invalue
		 , case when isnumeric(tup.invalue) = 1 then 0 else 1 end
		 , tup.outvalue
		 , case when isnumeric(tup.outvalue) = 1 then 0 else 1 end
		 , tp.publisher
	  from loader_conversiontable ct
	  join loader_conversiontuple tup on tup._fk_package = ct._fk_package	
									 and tup.conversiontableid = ct.conversiontableid
	  join loader_testpackage tp on tp.packagekey = ct._fk_package	
	 where ct._fk_package = v_testpackagekey
	   and not exists (select 1
						 from conversiontables t
						where t.tablename = ct.conversiontablename);


end $$

DELIMITER ;