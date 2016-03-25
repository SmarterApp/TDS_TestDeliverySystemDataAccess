DELIMITER $$

drop function if exists `isnumeric` $$

create function `isnumeric`(
/*
DESCRIPTION: Function to determine whether the input parameter value is numeric or not

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/2/2014		Sai V. 			Original Code
*/
	v_exp varchar(255))
returns bit
begin

	declare v_match varchar(255);
	declare v_out bit;

	set v_match = '^(([0-9+-.$]{1})|([+-]?[$]?[0-9]*(([.]{1}[0-9]*)|([.]?[0-9]+))))$';
	set v_out = (case when v_exp regexp v_match then 1 else 0 end);

	return v_out;

end$$

DELIMITER ;