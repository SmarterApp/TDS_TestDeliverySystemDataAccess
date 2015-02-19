DELIMITER $$

drop function if exists `_maketestgradelabel` $$

create function `_maketestgradelabel` (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			4/1/2014		Sai V. 			Original Code.
*/
	v_testkey varchar(200)
) returns varchar(100)
sql security invoker
begin

    declare v_result 		varchar(100);
    declare v_grade 		varchar(25);
    declare v_numgrades 	int;
    declare v_numintgrades  int;
    declare v_intgrade 		int;
	declare v_mingrade 		int;
    declare v_maxgrade 		int;

	drop temporary table if exists tmp_grades;

    create temporary table tmp_grades(
		grade varchar(25)
	  , g int
	);

    insert into tmp_grades (grade, g)
    select grade
		 , case when grade regexp'^([+-]?[0-9]+\.?[0-9]*e?[0-9]+)|(0x[0-9A-F]+)$' is not null then cast(grade as unsigned) else null end
      from setoftestgrades 
	 where _fk_adminsubject = v_testkey;

    set v_numgrades 	= (select count(*) from tmp_grades);
    set v_numintgrades  = (select count(*) from tmp_grades where g is not null);

    if (v_numgrades = 0) then return ''; end if;

    if (v_numgrades = 1) then
        select grade, g into v_grade, v_intgrade
		  from tmp_grades;
        
		if (v_intgrade is null) then 
			return v_grade ;
        else 
			return concat('Grade ', v_grade);
		end if;
    end if;
    
    if (v_numintgrades = v_numgrades) then 
	begin
        select min(g), max(g) into v_mingrade, v_maxgrade
		  from tmp_grades;

        if (v_mingrade = 9 and v_maxgrade = 12 and v_numgrades = 4) then 
			return 'High School'; 
		end if;
        if (v_maxgrade - v_mingrade + 1 = v_numintgrades) then
            return concat('Grades ', cast(v_mingrade as char(10)), ' - ', cast(v_maxgrade as char(10)));
		end if;
        
		set v_result = concat('Grades ', cast(v_mingrade as char(10)));

        delete from tmp_grades where g = v_mingrade;

        while (exists (select * from tmp_grades)) do
		begin
            set v_mingrade = (select min(g) from tmp_grades);
            delete from tmp_grades where g = v_mingrade;
            
			set v_result = concat(v_result, ', ', cast(v_mingrade as char(10)));
        end;
		end while;
	
        return v_result;
	end;
	end if;

	set v_grade  = (select min(grade) from tmp_grades);
	set v_result = v_grade;

	delete from tmp_grades where grade = v_grade;

    while (exists (select * from tmp_grades)) do 
	begin
		set v_grade = (select min(grade) from tmp_grades);
        delete from tmp_grades where grade = v_grade;

        set v_result = concat(v_result, ', ', v_grade);
	end;
	end while;

	return v_result;

end $$

DELIMITER ;