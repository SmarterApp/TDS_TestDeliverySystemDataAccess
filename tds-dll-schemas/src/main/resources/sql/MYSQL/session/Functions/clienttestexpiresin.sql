DELIMITER $$

drop function if exists `clienttestexpiresin` $$

create function `clienttestexpiresin`(
/*
DESCRIPTION: function needed to embed test expiration limits in a case statement in other procedures

VERSION 	DATE 			AUTHOR 			COMMENTS
001			07/23/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_testid varchar(255)
  ,	v_clientname varchar(100))
returns int
begin

	-- declare the return variable here
	declare v_days int;

	select oppexpire into v_days 
	  from timelimits where _efk_testid = v_testid and clientname = v_clientname;

	if (v_days is null) then
		select oppexpire into v_days 
		  from timelimits where _efk_testid is null and clientname = v_clientname;
	end if;

	-- return the result of the function
	return v_days;

end$$

DELIMITER ;