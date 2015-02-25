DELIMITER $$

drop function if exists `p_formataccommodations` $$

create function `p_formataccommodations`(
/*
DESCRIPTION:

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/10/2015		Sai V. 			
*/
	v_oppkey varbinary(16)
)
returns text
sql security invoker
begin

	declare v_result text;    
    declare v_count int;
    declare v_avalue varchar(250);

	drop temporary table if exists tmp_tblformataccoms;
	create temporary table tmp_tblformataccoms(avalue varchar(250));
    
    insert into tmp_tblformataccoms (avalue)
    select coalesce(acctype, ': ', accvalue) 
	from testeeaccommodations
    where _fk_testopportunity = v_oppkey and segment = 0
    order by acctype desc;
   
    set v_count = (select count(*) from tmp_tblformataccoms);

    while (v_count > 0) do 
	begin
        set v_avalue = (select avalue from tmp_tblformataccoms limit 1);
        if (v_result is null) then 
			set v_result = v_avalue; 
        else 
			set v_result = concat(v_result, ' | ', v_avalue);
		end if;

        delete from tmp_tblformataccoms where avalue = v_avalue;

        set v_count = v_count - 1;
    end;
	end while;

	-- clean-up
	drop temporary table tmp_tblformataccoms;

    return v_result;
    
end $$

DELIMITER ;
