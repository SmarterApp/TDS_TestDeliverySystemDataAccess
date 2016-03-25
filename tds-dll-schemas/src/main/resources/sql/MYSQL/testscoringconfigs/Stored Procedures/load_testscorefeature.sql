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
		 , replace(case when testkey = cr.bpelementid then 'Overall' 
						when tbp.bpelementid is not null then tbp.bpelementname
						else cr.bpelementid 
					end, '&amp;', '&')
		 , cr.rulelabel
		 , cr.rulename
		 , cr.computationorder
	  from loader_computationrule cr
	  join loader_testpackage tp on tp.packagekey = cr._fk_package
	  left join loader_testblueprint tbp on tbp._fk_package = cr._fk_package
										and tbp.bpelementid = cr.bpelementid
	 where tp.packagekey = v_testpackagekey
	   and not exists (select 1
						 from testscorefeature tsf 
						where tsf._key = cr.ruleid);


end $$

DELIMITER ;
  