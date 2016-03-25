DELIMITER $$

drop function if exists `_canchangeoppstatus` $$

create function `_canchangeoppstatus`(
/*
DESCRIPTION: preliminary function to determine legal status changes for opportunities

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/28/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_oldstatus varchar(50)
  , v_newstatus varchar(50)
)
returns varchar(100)
begin

    if (_isvalidstatustransition(v_oldstatus, v_newstatus) = 0) then
        return 'cannot change opportunity from {0} to {1}';
    end if;

	return null;
	
end $$

DELIMITER ;
