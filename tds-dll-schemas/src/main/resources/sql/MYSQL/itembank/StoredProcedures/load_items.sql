DELIMITER $$

drop procedure if exists `load_items` $$

create procedure `load_items` (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			4/3/2014		Sai V. 			Original Code.
*/
	v_testpackagekey varchar(350)
)
begin

	/*
	** _efk_itembank & _efk_Item:- testitemid is a comnibation of itembank value and item seperated by '-'. 
	   For e.g: if the testitemid = 148-100240 then, itembankkey = 148 and _efk_Item = 100240	

	** FilePath:- strip out the .xml extension from the filename value and append '\'
	*/

	create temporary table tmp_scorepoints (
		testitemid  varchar(150)
	  , scorepoints int
	)engine = memory;

	--  load the nonexistent items barbones conditionally first, then update from Loader_Items
	insert into tblitem (_efk_itembank, _efk_Item, itemtype, filepath, filename, datelastupdated, itemid, _key, loadconfig)
	select coalesce(substring_index(testitemid, '-', 1), pkg._efk_itembank)
		 , coalesce(substring_index(testitemid, '-', -1), 0)
		 , itemtype
		 , filepath
		 , filename
		 , now(3)
		 , testitemname
		 , testitemid
		 , version
	  from loader_testitem ti
	  join loader_testpackage pkg on pkg.packagekey = ti._fk_package 
	 where not exists (select * 
						 from tblitem 
					    where _Key = testitemid)
	   and _fk_package = v_testpackagekey;

	insert into tmp_scorepoints
	select distinct testitemid, scorepoints 
	  from loader_itemscoredimension
	 where _fk_package = v_testpackagekey;

	update tblitem i
	  join tmp_scorepoints sp on sp.testitemid = i._key
	  join loader_testitem ti on ti.testitemid = i._key
	   set i.scorepoint = sp.scorepoints
		 , i.updateconfig = ti.version;

	-- do we need to update any of the data??

	-- clean-up
	drop temporary table tmp_scorepoints;

end $$

DELIMITER ;

