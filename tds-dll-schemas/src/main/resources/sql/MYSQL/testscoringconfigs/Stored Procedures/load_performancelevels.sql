DELIMITER $$

drop procedure if exists `load_performancelevels` $$

create procedure `load_performancelevels` (
/*
Description: Entry point for loading itembank tables from the testpackage file in xml format.

VERSION 	DATE 			AUTHOR 			COMMENTS
001			6/30/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
)
begin

	insert into tdscore_itembank_testpackage.performancelevels(_fk_content, plevel, scaledlo, scaledhi)
	select distinct bpelementid
		 , plevel
		 , scaledlo
		 , scaledhi
	  from loader_performancelevels l
	 where l._fk_package = v_testpackagekey
	   and not exists (select 1
						 from tdscore_itembank_testpackage.performancelevels pl
						where pl._fk_content = l.bpelementid);


end $$

DELIMITER ;