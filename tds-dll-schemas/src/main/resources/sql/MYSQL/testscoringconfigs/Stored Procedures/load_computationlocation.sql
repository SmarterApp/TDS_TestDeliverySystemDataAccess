DELIMITER $$

drop procedure if exists `load_computationlocation` $$

create procedure `load_computationlocation` (
/*
Description:

VERSION 	DATE 			AUTHOR 			COMMENTS
001			11/10/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
)
begin

	insert into feature_computationlocation (_fk_testscorefeature, location)
	select distinct cr.ruleid
		 , 'TIS' -- This column is used to configure which type of computations should be done for which system. For OSS, this value is static
	  from loader_computationrule cr
	  join loader_testpackage tp on tp.packagekey = cr._fk_package
	 where tp.packagekey = v_testpackagekey
	   and not exists (select 1
						 from feature_computationlocation cl 
						where cl._fk_testscorefeature = cr.ruleid)
	   and exists (select 1
				     from testscorefeature tsf 
					where tsf._key = cr.ruleid);


end $$

DELIMITER ;