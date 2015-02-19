DELIMITER $$

drop function if exists `itembvector` $$

create function `itembvector` (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			4/1/2014		Sai V. 			Original Code.
*/
	v_testkey varchar(200)
  , v_itemkey varchar(20)
) returns varchar(1000)
sql security invoker
begin

    declare v_result varchar(1000);
    declare v_b float;
    declare v_p int;

    create temporary table tmp_b_s (b float, parmnum int, _key int primary key auto_increment);
    
    insert into tmp_b_s (b, parmnum)
    select parmvalue, parmnum
      from itemscoredimension d 
	  join itemmeasurementparameter p on p._fk_itemscoredimension = d._key
	  join measurementparameter m on m._fk_measurementmodel = d._fk_measurementmodel
								 and m.parmnum = p._fk_measurementparameter
     where d._fk_adminsubject = v_testkey -- '(sbac)cat-m3-onon-s1-a1-math-3-fall-2013-2014' 
	   and d._fk_item = v_itemkey -- '200-1004'
       and m.parmname like 'b%';

    while (exists (select * from tmp_b_s)) do
	begin
        select b, _key into v_b, v_p from tmp_b_s limit 1;
        delete from tmp_b_s where _key = v_p;
        if (v_result is null) then
            set v_result = cast(v_b as char(50));
        else 
            set v_result = concat(v_result, ';',  cast(v_b as char(50)));
		end if;
    end;
	end while;

	drop temporary table tmp_b_s;

    return v_result;

end $$

DELIMITER ;