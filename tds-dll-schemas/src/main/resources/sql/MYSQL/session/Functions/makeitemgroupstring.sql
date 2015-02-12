DELIMITER $$

drop function if exists `makeitemgroupstring` $$

create function `makeitemgroupstring`(
/*
DESCRIPTION: Make delimited string of item groups administered so far on the opportunity

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/28/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_oppkey varbinary(16)
)
returns text
begin

    declare v_str text;
    declare v_grp varchar(50);
    declare v_p int;

	drop temporary table if exists tmp_tbl;
	create temporary table tmp_tbl (groupID varchar(50) primary key not null);     

    insert into tmp_tbl (groupid)
    select distinct groupid
    from testeeresponse
    where _fk_testopportunity = v_oppkey and _efk_itsitem is not null;
        
    if (not exists(select * from tmp_tbl)) then
        return '';
    end if;
    
    while (exists (select * from tmp_tbl)) do
	begin        
        select groupid into v_grp from tmp_tbl limit 1;
        delete from tmp_tbl where groupid = v_grp;
        if (v_str is null) then 
			set v_str = v_grp; 
        else 
			set v_str = v_str + ',' + v_grp;
		end if;
    end;
	end while;

    return v_str;

end $$

DELIMITER ;