DELIMITER $$

drop procedure if exists `load_conversiontabledesc` $$

create procedure `load_conversiontabledesc` (
/*
Description: Entry point for loading itembank tables from the testpackage file in xml format.

VERSION 	DATE 			AUTHOR 			COMMENTS
001			6/30/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
)
begin

	insert into conversiontabledesc(_key, tablename, _fk_client)
	select distinct conversiontableid
		 , conversiontablename
		 , tp.publisher
	  from loader_conversiontable ct
	  join loader_testpackage tp on tp.packagekey = ct._fk_package	
	 where ct._fk_package = v_testpackagekey
	   and not exists (select 1
						 from conversiontabledesc ctd
						where ctd._key = ct.conversiontableid);


end $$

DELIMITER ;