DELIMITER $$

drop procedure if exists load_adminforms $$

create procedure load_adminforms (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			4/3/2014		Sai V. 			Original Code.
*/
	v_testpackagekey varchar(350)
)
begin

	-- testformid is a combination of Testkey : Testform.cohort - Testform.language
	-- tfp.formpartitionid is a combination of itsbankkey - itskey
	drop temporary table if exists tmp_testform;
	create temporary table tmp_testform (
		select sf.segmentid				as adminsubject		-- substring_index(tf.testformid, ':', 1)  
			 , coalesce(substring_index(substring_index(tfp.testformid, ':', -1), '-', 1), 'Default') 	as cohort
			 , prop.propvalue			as lang				-- substring_index(substring_index(tf.testformid, ':', -1), '-', -1) 
			 , tfp.formpartitionid
			 , tfp.formpartitionname
			 , coalesce(substring_index(tfp.formpartitionid, '-', -1), pkg._efk_itembank)	as itsbankkey 
			 , coalesce(substring_index(tfp.formpartitionid, '-', -1), 0)					as itskey 			 
			 , tfp.version
		  from loader_testpackage pkg 
		  join loader_testformpartition tfp on tfp._fk_package = pkg.packagekey
		  join loader_segmentform sf on sf.formpartitionid = tfp.formpartitionid
									and sf._fk_package = tfp._fk_package
		  left join loader_testformproperties prop on prop.testformid = tfp.testformid
												  and prop._fk_package = tfp._fk_package
		 where prop.propname = 'language'
		   and prop.ispool = 0
		   and tfp._fk_package = v_testpackagekey
	);


	-- if form-id's do not exists in the current test package being loaded
	-- delete such forms as they have become obsolete
	delete tf
	  from testform tf
	  join tmp_testform tmp on tmp.adminsubject = tf._fk_adminsubject
	 where not exists (select * from loader_testformpartition tfp where tfp._fk_package = v_testpackagekey and tfp.formpartitionid = tf._key);


	-- update existing form information
	update testform tf
	  join tmp_testform tmp on tf._fk_adminsubject = tmp.adminsubject 
						   and tf._key = tmp.formpartitionid
	   set tf.cohort = tmp.cohort 
		 , tf.`language` = tmp.lang
		 , tf.formid = tmp.formpartitionname
		 , tf.updateconfig = tmp.version; 


	-- insert any missing forms	
	insert into testform (_fk_adminsubject, cohort, `language`, _key, formid, _efk_itsbank, _efk_itskey, loadconfig)
	select *
	  from tmp_testform tmp
	 where not exists (select 1 
						 from testform tf
						where tf._fk_adminsubject = tmp.adminsubject 
						  and tf._key = tmp.formpartitionid);
		
	-- the item language loader does not have test key to go on, and may load item languages falsely for a fixed form test 
	-- ??  this is t-code code copy....do we need to do this??
	--     delete ip
	-- 	  from tblitemprops ip
	--      where propname = 'language' 
	-- 	   and exists (select 1 from loader_forms where testkey = _fk_adminsubject)
	-- 	   and not exists (select 1 from loader_forms where testkey = _fk_adminsubject and language = propvalue);

	-- clean-up
	drop temporary table tmp_testform;

end $$

DELIMITER ;