DELIMITER $$

drop function if exists `testsubject` $$

create function `testsubject`(
/*
DESCRIPTION:

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/10/2015		Sai V. 			
*/
	v_testkey varchar(250)
)
returns varchar(100)
sql security invoker
begin

	declare v_result varchar(100);

    set v_result = (select s.`name`
					  from tblsubject s, tblsetofadminsubjects a
					 where a._key = v_testkey and s._key = a._fk_subject);

    return v_result;

end $$

DELIMITER ;