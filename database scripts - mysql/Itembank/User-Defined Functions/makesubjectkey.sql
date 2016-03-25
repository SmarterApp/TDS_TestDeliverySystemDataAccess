DELIMITER $$

drop function if exists `makesubjectkey` $$

create function `makesubjectkey` (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			4/1/2014		Sai V. 			Original Code.
*/
	v_client 	  varchar(100)
  , v_subjectname varchar(100)
  , v_grade 	  varchar(100)
) returns varchar(200)
begin

	declare v_key varchar(200);
    set v_key = concat(v_client, '-', v_subjectname);

    if (v_grade = '' or length(v_grade) < 1) then
		return v_key;
	end if;

    return concat(v_key, '-', v_grade);

end $$

DELIMITER ;