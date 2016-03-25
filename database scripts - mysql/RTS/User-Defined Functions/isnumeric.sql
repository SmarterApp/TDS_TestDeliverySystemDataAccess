-- ------------------
-- routine isnumeric
-- ------------------
delimiter $$

drop function if exists isnumeric $$

create function isnumeric (
/*
VERSION 	DATE 			AUTHOR 			COMMENTS
001			11/4/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_in varchar(1024))
returns tinyint
return v_in regexp '^(-|\\+){0,1}([0-9]+\\.[0-9]*|[0-9]*\\.[0-9]+|[0-9]+)$';

$$

delimiter ;