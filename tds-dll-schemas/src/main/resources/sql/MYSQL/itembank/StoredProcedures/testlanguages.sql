DELIMITER $$

drop procedure if exists `testlanguages` $$

create procedure `testlanguages`(
/*
DESCRIPTION:

VERSION 	DATE 			AUTHOR 			COMMENTS
001			2/10/2015		Sai V. 			
*/
  	v_test varchar(200)
)
sql security invoker
proc: begin

    declare v_segmented bit;
	declare v_algorithm varchar(50);


    select issegmented, selectionalgorithm 
	into v_segmented, v_algorithm
	from tblsetofadminsubjects
    where _key = v_test;

    if (v_segmented = 0) then 
	begin
        if (v_algorithm = 'fixedform') then
            insert into tblout_testlanguages (`code`, label)
            select distinct propvalue, propdescription
            from tblitemprops p, testform f
            where p._fk_adminsubject = v_test and propname = 'language' and f._fk_adminsubject = v_test and f.`language` = p.propvalue
                and p.isactive = 1;
        else 
            insert into tblout_testlanguages (`code`, label)
            select distinct  propvalue, propdescription
            from  tblitemprops p
            where p._fk_adminsubject = v_test and propname = 'language' and isactive = 1;
        end if;
    end;
    else
        insert into tblout_testlanguages (`code`, label)
        select distinct propvalue, propdescription
        from tblsetofadminitems a, tblitemprops p, tblsetofadminsubjects s
        where s.virtualtest = v_test and a._fk_adminsubject = s._key and a._fk_adminsubject = p._fk_adminsubject 
            and a._fk_item = p._fk_item and propname = 'language' and p.isactive = 1;
	end if;

end $$

DELIMITER ;