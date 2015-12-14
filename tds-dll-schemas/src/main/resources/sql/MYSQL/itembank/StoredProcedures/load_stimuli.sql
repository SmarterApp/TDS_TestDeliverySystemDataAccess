DELIMITER $$

drop procedure if exists `load_stimuli` $$

create procedure `load_stimuli` (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			4/3/2014		Sai V. 			Original Code.
*/
	v_testpackagekey varchar(350)
)
begin

	/*
	** _efk_itembank & clientid:- passageid is a comnibation of itembank value and clientid seperated by '-'. 
	   For e.g: if the passageid = 148-100240 then, itembankkey = 148 and client = 100240

	** _efk_itskey:- itskey value is considered to be same as clientid	

	** FilePath:- strip out the .xml extension from the filename value and append '\'
	*/

	--  load the stimuli conditionally first
	insert into tblstimulus (_efk_itembank, _efk_itskey, clientid, filepath, filename, datelastupdated, _key, loadconfig)
	select coalesce(substring_index(passageid, '-', 1), pkg._efk_itembank)
		 , coalesce(substring_index(passageid, '-', -1), 0)
		 , null
		 , filepath
		 , filename
		 , now(3)
		 , passageid
		 , version
	  from loader_testpassages p
	  join loader_testpackage pkg on pkg.packagekey = p._fk_package 
	 where not exists (select 1
						 from tblstimulus 
						where passageid = _key)
	   and _fk_package = v_testpackagekey;


	-- do we need to update any of the data??

end $$

DELIMITER ;
