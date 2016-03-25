DELIMITER $$

drop procedure if exists `load_subject` $$

create procedure `load_subject` (
/*
Description: Load subjects from loader_testpackageproperties

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/23/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
)
begin

	insert into tblsubject (`name`, grade, _key, _fk_client, loadconfig)
	select tp.subjectname
		 , '' as grade
		 , subjectkey
		 , clientkey
		 , tp.testversion
	  from loader_testpackage tp
	  -- join loader_testpackageproperties	tpp on tp.packagekey = tpp._fk_package and tpp.propname = 'subject'
	 where not exists ( select 1
						  from tblsubject s
						 where s._key = tp.subjectkey )
	   and packagekey = v_testpackagekey;


/*
	create temporary table tmp_gradelist (
		gradename  varchar(100)
	  , gradelabel varchar(200)
	);
	
	create temporary table tmp_subject (
		`name` 		varchar(100)
	  , grade 		varchar(64)
      , _key  		varchar(150)
	  , _fk_client 	bigint
	  , testversion bigint
	);

	-- load grades for the corresponding testpackage into temp table
	insert into tmp_gradelist
	select propvalue, proplabel
	  from loader_testpackageproperties
	 where propname = 'grade';

	-- if no grades are listed, insert one dummy value for processing purposes
	if (select count(*) from tmp_gradelist) < 1 then
		insert into tmp_gradelist
		select '', '';
	end if;

	insert into tmp_subject
	select `subject`
		 , gradename
		 , makesubjectkey(publisher, `subject`, gradename) as subjectkey
		 , clientkey
		 , testversion
	  from loader_testpackage, tmp_gradelist;

	-- new inserts
	insert into tblsubject(`name`, grade, _Key, _fk_Client, loadConfig) 
	select *
	  from tmp_subject	
	 where _key not in (select _key from tblsubject);

	-- update existing data
	update tblsubject sub, tmp_subject tmp
	   set sub.`name` = tmp.`name`
		 , sub.grade  = tmp.grade 
		 , updateConfig = testversion
	 where sub._key = tmp._key;
	
	-- clean-up
	drop temporary table tmp_gradelist;
	drop temporary table tmp_subject;
*/
end $$

DELIMITER ;