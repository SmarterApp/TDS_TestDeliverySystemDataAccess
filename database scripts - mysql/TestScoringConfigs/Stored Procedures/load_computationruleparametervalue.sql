DELIMITER $$

drop procedure if exists `load_computationruleparametervalue` $$

create procedure `load_computationruleparametervalue` (
/*
Description: Entry point for loading itembank tables from the testpackage file in xml format.

VERSION 	DATE 			AUTHOR 			COMMENTS
001			6/30/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
)
begin

	insert into computationruleparametervalue(_fk_testscorefeature, _fk_parameter, `index`, `value`)
	select l.ruleid
		 , l.parameterid
		 , l.`index`
		 , l.`value`
	  from loader_computationruleparametervalue l
	 where l._fk_package = v_testpackagekey
	   and not exists (select 1
						 from computationruleparametervalue crpv 
						where crpv._fk_testscorefeature = l.ruleid
						  and crpv._fk_parameter = l.parameterid
						  and crpv.`index` = l.`index`);


end $$

DELIMITER ;