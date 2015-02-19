DELIMITER $$

drop function if exists `_maketestlabel` $$

create function `_maketestlabel` (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			4/1/2014		Sai V. 			Original Code.
*/
	v_testkey varchar(200)
) returns varchar(100)
sql security invoker
begin

    declare v_subject varchar(100);
    set v_subject = (select `name`
					   from tblsetofadminsubjects a
					   join tblsubject s on a._fk_subject = s._key
					  where a._key = v_testkey);

    return concat(_maketestgradelabel(v_testkey), ' ', v_subject);

end $$

DELIMITER ;