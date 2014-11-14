DELIMITER $$

drop procedure if exists `load_testscorefeature` $$

create procedure `load_testscorefeature` (
/*
Description: Entry point for loading itembank tables from the testpackage file in xml format.

VERSION 	DATE 			AUTHOR 			COMMENTS
001			6/30/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
)
begin

	insert into testscorefeature(_key, clientname, testid, measureof, measurelabel, computationrule, computationorder)
	select distinct cr.ruleid
		 , tp.publisher
		 , tp.testname
		 , case when testkey = cr.bpelementid then 'Overall' else cr.bpelementid end
		 , cr.rulelabel
		 , cr.rulename
		 , cr.computationorder
	  from loader_computationrule cr
-- 	  join loader_computationruleparameter crp on crp.bpelementid = cr.bpelementid
-- 											  and crp._fk_package = cr._fk_package
	  join loader_testpackage tp on tp.packagekey = cr._fk_package
	 where tp.packagekey = v_testpackagekey
	   and not exists (select 1
						 from testscorefeature tsf 
						where tsf._key = cr.ruleid);


end $$

DELIMITER ;
