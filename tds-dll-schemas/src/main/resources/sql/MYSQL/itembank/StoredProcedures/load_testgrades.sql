DELIMITER $$

drop procedure if exists `load_testgrades` $$

create procedure `load_testgrades` (
/*
Description: Load subjects from loader_testpackageproperties

VERSION 	DATE 			AUTHOR 			COMMENTS
001			4/14/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
)
begin

	declare v_testid varchar(150);

	create temporary table tmp_gradelist (
		gradename  varchar(100)
	  , gradelabel varchar(200)
	)engine = memory;

	create temporary table tmp_testkeys (
		testkey varchar(250)
	  , testid  varchar(255)
	)engine = memory;
		
	-- load grades for the corresponding testpackage into temp table
	insert into tmp_gradelist
	select propvalue, proplabel
	  from loader_testpackageproperties
	 where propname = 'grade'
	   and _fk_package = v_testpackagekey;

	insert into tmp_testkeys
	select distinct bpelementid
		 , bpelementname
	  from loader_testblueprint
	 where elementtype in ('test', 'segment')
	  and _fk_package = v_testpackagekey;

	set v_testid = (select bpelementname from loader_testblueprint
					 where elementtype = 'test'
					   and _fk_package = v_testpackagekey);
	
	-- delete existing set of grades for that test
    delete g
	  from setoftestgrades g
	  join tmp_testkeys t on t.testkey = g._fk_adminsubject;


	insert into setoftestgrades
	select v_testid
		 , gl.gradename
		 , 0
		 , tk.testkey
		 , null
		 , unhex(REPLACE(UUID(), '-', ''))
	  from tmp_testkeys tk
	  join tmp_gradelist gl on 1 = 1;


	-- clear temp tables
	drop temporary table tmp_gradelist;
	drop temporary table tmp_testkeys;

end $$

DELIMITER ;