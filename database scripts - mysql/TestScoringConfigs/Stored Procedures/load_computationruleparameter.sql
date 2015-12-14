DELIMITER $$

drop procedure if exists `load_computationruleparameter` $$

create procedure `load_computationruleparameter` (
/*
Description: Entry point for loading itembank tables from the testpackage file in xml format.

VERSION 	DATE 			AUTHOR 			COMMENTS
001			6/30/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
)
begin

	insert into computationruleparameters(_key, computationrule, parametername, parameterposition, indextype, `type`)
	select distinct l.parameterid
		 , cr.rulename
		 , l.parametername
		 , l.position
		 , case when l.propvalue = 'indextype' then l.propvalue else '' end
		 , l.parametertype
	  from loader_computationruleparameter l
	  join loader_computationrule cr on cr.ruleid = l.ruleid
									and cr._fk_package = l._fk_package
	 where l._fk_package = v_testpackagekey
	   and not exists (select 1
						 from computationruleparameters crp 
						where crp._key = l.parameterid);


end $$

DELIMITER ;