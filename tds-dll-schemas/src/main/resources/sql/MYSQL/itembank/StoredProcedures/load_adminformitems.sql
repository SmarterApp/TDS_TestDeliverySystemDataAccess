DELIMITER $$

drop procedure if exists load_adminformitems $$

create procedure load_adminformitems (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			4/3/2014		Sai V. 			Original Code.
*/
	v_testpackagekey varchar(350)
)
begin

	drop temporary table if exists tmp_formitem;
	create temporary table tmp_formitem (
		select fgi.itemid
			 , coalesce(substring_index(tfp.formpartitionid, '-', -1), 0)		as itsformkey
			 , fgi.formposition
			 , sf.segmentid 		as adminsubject
			 , tfp.formpartitionid
			 , fgi.isactive
		  from loader_testforms tf 
		  join loader_testformpartition tfp on tfp.testformid = tf.testformid
										   and tfp._fk_package = tf._fk_package	 	
		  join loader_testformitemgroup tfig on tfig.testformid = tfp.testformid
											and tfig.formpartitionid = tfp.formpartitionid
										    and tfig._fk_package = tfp._fk_package	 	
		  join loader_testformgroupitems fgi on fgi.testformid = tfig.testformid
									        and fgi.formitemgroupid = tfig.formitemgroupid
											and fgi._fk_package = tfig._fk_package 
		  join loader_segmentform sf on sf.formpartitionid = tfp.formpartitionid
									and sf._fk_package = tfp._fk_package
		 where tf._fk_package = v_testpackagekey
	);

	insert into testformitem(_fk_item, _efk_itsformkey, formposition, _fk_adminsubject, _fk_testform, isactive)
	select distinct *
	  from tmp_formitem tmp
	 where not exists (select 1
						 from testformitem tfi
						where tmp.itemid = tfi._fk_item and tmp.formposition = tfi.formposition and tmp.formpartitionid = tfi._fk_testform and tmp.adminsubject = tfi._fk_adminsubject);


	update testformitem tfi
	  join tmp_formitem tmp on tmp.itemid = tfi._fk_item and tmp.formposition = tfi.formposition and tmp.formpartitionid = tfi._fk_testform and tmp.adminsubject = tfi._fk_adminsubject
	   set tfi.isactive = tmp.isactive;


	-- clean-up
	drop temporary table tmp_formitem;

end $$

DELIMITER ;