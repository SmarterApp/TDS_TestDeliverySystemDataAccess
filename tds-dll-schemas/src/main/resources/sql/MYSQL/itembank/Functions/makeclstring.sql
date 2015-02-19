DELIMITER $$

drop function if exists `makeclstring` $$

create function `makeclstring` (
/*
Description: 

Example: select makeclstring('(Oregon)Oregon-ELPA-6-8-winter-2013-2014)', '143-201')

VERSION 	DATE 			AUTHOR 			COMMENTS
001			4/3/2014		Sai V. 			--
*/
	v_testkey varchar(250)
  , v_itemkey varchar(50)
) returns text
sql security invoker
begin

	-- make a string from the set of content level ids for quick retrieval by adaptive algorithm (this will be stored with the item, see load_adminitems)
    declare v_str text;
	declare v_cl varchar(100);
	
	create temporary table tmp_cltbl (
		cl varchar(100)
	);

    insert into tmp_cltbl (cl)
    select contentlevel 
	  from aa_itemcl 
	 where _fk_adminsubject = v_testkey 
	   and _fk_item = v_itemkey 
	order by contentlevel;

    while (exists (select * from tmp_cltbl)) do
	begin
        set v_cl = (select cl from tmp_cltbl limit 1);

        if (v_str is null) then 
			set v_str = v_cl;
        else 
			set v_str = concat(v_str, ';', v_cl);
		end if;

        delete from tmp_cltbl where cl = v_cl;
    end;
	end while;

	-- clean-up
	drop temporary table tmp_cltbl;

	-- return
    return v_str;

end $$

DELIMITER ;

