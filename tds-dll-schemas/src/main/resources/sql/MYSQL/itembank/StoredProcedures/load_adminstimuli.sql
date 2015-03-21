DELIMITER $$

drop procedure if exists load_adminstimuli $$

create procedure load_adminstimuli (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			4/3/2014		Sai V. 			Original Code.
*/
	v_testpackagekey varchar(350)
)
proc: begin

	-- if passages do not exists we need not load admin stimuli data 
	if not exists (select * from loader_testpassages where _fk_package = v_testpackagekey) then
		leave proc;
	end if;	

	drop temporary table if exists tmp_tblstimulus;
	create temporary table tmp_tblstimulus (
		_fk_stimulus 	 varchar(100)
	  , _fk_adminsubject varchar(250)
	  , numitemsrequired int
	  , maxitems 		 int
-- 	  , bpweight 		 float 
	  , version 		 bigint
	  , groupid 		 varchar(50)
	)engine = memory;

	-- ?? have to write logic load passages when fixedform test
	insert into tmp_tblstimulus (_fk_stimulus, _fk_adminsubject, numitemsrequired, maxitems, version, groupid)
	select distinct passageid
		 , segmentid
		 , case when maxresponses = 'ALL' then -1 else maxresponses end		as numitemsrequired
		 , case when maxitems = 'ALL' then -1 else maxitems end				as maxitems
		 , version
		 , substring_index(tfig.formitemgroupid, ':', -1)  					as groupid
	  from loader_testformitemgroup tfig 
	  join loader_segmentform sf on sf.formpartitionid = tfig.formpartitionid
								and sf._fk_package = tfig._fk_package
	 where passageid is not null
	   and passageid <> ''
	   and tfig._fk_package = v_testpackagekey;


	insert into tmp_tblstimulus (_fk_stimulus, _fk_adminsubject, numitemsrequired, maxitems, version, groupid) 
	select spref.passageid
		 , sp.segmentid
		 , case when sp.maxresponses = 'ALL' then -1 else sp.maxresponses end
		 , case when sp.maxitems = 'ALL' then -1 else sp.maxitems end
		 , sp.version
		 , sp.itemgroupid 
	  from loader_segmentpool sp
	  join loader_segmentpoolpassageref spref on spref.segmentid = sp.segmentid
											 and spref.itemgroupid = sp.itemgroupid
											 and spref._fk_package = sp._fk_package	
	 where spref.passageid is not null 
	   and spref.passageid <> ''
	   and sp._fk_package = v_testpackagekey;


	insert into tbladminstimulus (_fk_stimulus, _fk_adminsubject, numitemsrequired, maxitems, loadconfig, groupid) 
	select distinct *
	  from tmp_tblstimulus tmp
	where not exists (select 1
						from tbladminstimulus st
					   where st._fk_stimulus = tmp._fk_stimulus
						 and st._fk_adminsubject = tmp._fk_adminsubject);

	update tbladminstimulus st
	  join tmp_tblstimulus tmp on st._fk_stimulus = tmp._fk_stimulus
						      and st._fk_adminsubject = tmp._fk_adminsubject
	   set st.numitemsrequired = tmp.numitemsrequired
		 , st.maxitems = tmp.maxitems
		 , st.updateconfig = tmp.version;


	-- clean-up
	drop temporary table tmp_tblstimulus;

end $$

DELIMITER ;