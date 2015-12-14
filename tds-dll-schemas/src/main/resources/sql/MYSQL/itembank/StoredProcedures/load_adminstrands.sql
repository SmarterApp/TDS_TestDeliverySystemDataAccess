DELIMITER $$

drop procedure if exists load_adminstrands $$

create procedure load_adminstrands (
/*
Description: load test admins loader_* tables

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/23/2014		Sai V. 			Original Code.
*/
	v_testpackagekey varchar(350)
)
begin
    
	declare v_issegmented bit;

	-- create temp table to host data during data gathering and processing
	drop temporary table if exists tmp_tbladminstrand;
	create temporary table tmp_tbladminstrand (
		_key 				varchar(255)
	  , _fk_adminsubject 	varchar(250)
	  , _fk_strand 			varchar(150)
	  , minitems 			int 
	  , maxitems 			int
	  , bpweight 			float
	  , adaptivecut 		float
	  , startability 		float
	  , startinfo 			float 
	  , scalar 				float
      , loadmin             int
      , loadmax             int    
	  , isstrictmax			bit
	  , precisiontarget				float
	  , precisiontargetmetweight	float
	  , precisiontargetnotmetweight float
	  , abilityweight				float
	  , version 			bigint
      , elementtype         varchar(100)
	  , _fk_package 		varchar(350)
	)engine = memory;

    -- first, figure out if the test is segmented or non-segmented
    -- if the bpelementid for test and segment are the same, then the test is non-segmented
    set v_issegmented = (select case when cnt = 0 then 1 else 0 end
                         from (
                            select count(*) cnt
                              from loader_testblueprint tbp1
                              join loader_testblueprint tbp2 on tbp1.bpelementid = tbp2.bpelementid
                                                            and tbp1._fk_package = tbp2._fk_package
                             where tbp1.elementtype = 'test'
                               and tbp2.elementtype = 'segment'
                               and tbp2._fk_package = v_testpackagekey
                            ) t
                        );

	-- load "test" data for non-segmented tests
	-- the minopitem and maxopitem counts for strands/contentlevel for the test are available in the <testblueprint>
	insert into tmp_tbladminstrand (_key, _fk_adminsubject, _fk_strand, minitems, maxitems, version, elementtype, _fk_package)
	select distinct concat(test.bpelementid, '-', strand.bpelementid)
		 , test.bpelementid
	     , strand.bpelementid
		 , case when strand.minopitems is null or strand.minopitems < 0 then 0 else strand.minopitems end
		 , case when strand.maxopitems is null or strand.maxopitems < 0 then 0 else strand.maxopitems end
		 , strand.version
		 , strand.elementtype
		 , strand._fk_package
	  from loader_testblueprint test 	
	  join loader_testblueprint strand on strand._fk_package = test._fk_package 
	 where test.elementtype = 'test'
	   and strand.elementtype in ('strand', 'contentlevel')
	   and test._fk_package = v_testpackagekey;

	if v_issegmented = 1 then
		-- for segmented tests, load individual segments data
		-- the minopitem and maxopitem counts for strands/contentlevel for the segment are available in the <segmentblueprint>
		insert into tmp_tbladminstrand (_key, _fk_adminsubject, _fk_strand, minitems, maxitems, loadmin, loadmax, version, elementtype, _fk_package)
		select distinct concat(segp.segmentid, '-', bp.bpelementid)
			 , segp.segmentid
			 , bp.bpelementid
			 , case when segp.minopitems is null or segp.minopitems < 0 then 0 else segp.minopitems end
			 , case when segp.maxopitems is null or segp.maxopitems < 0 then 0 else segp.maxopitems end
			 , case when segp.minopitems is null or segp.minopitems < 0 then 0 else segp.minopitems end
			 , case when segp.maxopitems is null or segp.maxopitems < 0 then 0 else segp.maxopitems end
			 , version
			 , elementtype
			 , bp._fk_package
		  from loader_testblueprint bp 	
		  join loader_segmentblueprint segp on segp.segmentbpelementid = bp.bpelementid		
										   and segp._fk_package = bp._fk_package
		 where elementtype in ('strand', 'contentlevel')
		   and bp._fk_package = v_testpackagekey;
	end if;

	
	drop temporary table if exists tmp_tblstrandproperties;	
	create temporary table tmp_tblstrandproperties as (
		select tmp._key
			 , max(if(sisp.propname = 'isstrictmax', (case sisp.propvalue when 'true' then 1 else 0 end), null)) as isstrictmax
			 , max(if(sisp.propname = 'bpweight', sisp.propvalue, null)) as bpweight
			 , max(if(sisp.propname = 'adaptivecut', sisp.propvalue, null)) as adaptivecut
			 , max(if(sisp.propname = 'startability', sisp.propvalue, null)) as startability
			 , max(if(sisp.propname = 'startinfo', sisp.propvalue, null)) as startinfo
			 , max(if(sisp.propname = 'scalar', sisp.propvalue, null)) as scalar
			-- added on 03.20.2015
			 , max(if(sisp.propname = 'precisiontargetmetweight', sisp.propvalue, null)) as precisiontargetmetweight
			 , max(if(sisp.propname = 'precisiontargetnotmetweight', sisp.propvalue, null)) as precisiontargetnotmetweight
			 , max(if(sisp.propname = 'precisiontarget', sisp.propvalue, null)) as precisiontarget
			 , max(if(sisp.propname = 'abilityweight', sisp.propvalue, null)) as abilityweight
		  from tmp_tbladminstrand tmp
		  join loader_segmentitemselectionproperties sisp on sisp.segmentid = tmp._fk_adminsubject 
														 and sisp.bpelementid = tmp._fk_strand
														 and sisp._fk_package = tmp._fk_package		
		 where tmp.elementtype = 'strand'
		   and sisp._fk_package = v_testpackagekey
		 group by tmp._key
	);

	drop temporary table if exists tmp_tblcontentproperties;
	create temporary table tmp_tblcontentproperties as (
		select tmp._key
			 , max(if(sisp.propname = 'isstrictmax', (case sisp.propvalue when 'true' then 1 else 0 end), null)) as isstrictmax
			 , max(if(sisp.propname = 'bpweight', sisp.propvalue, null)) as bpweight
		  from tmp_tbladminstrand tmp
		  join loader_segmentitemselectionproperties sisp on sisp.segmentid = tmp._fk_adminsubject 
														 and sisp.bpelementid = tmp._fk_strand
														 and sisp._fk_package = tmp._fk_package	
		 where tmp.elementtype = 'contentlevel'
		   and sisp._fk_package = v_testpackagekey
		group by tmp._key
	);

/*
  select * from tmp_tblcontentproperties;
  select * from tmp_tblstrandproperties;
  select * from tmp_tbladminstrand;  */

	-- update strand level records
	update tmp_tbladminstrand tmp
	  join tmp_tblstrandproperties p on p._key = tmp._key
	   set tmp.bpweight 	 = p.bpweight
		 , tmp.adaptivecut 	 = p.adaptivecut	
		 , tmp.startability  = p.startability
		 , tmp.startinfo	 = p.startinfo
		 , tmp.scalar   	 = p.scalar
		 , tmp.isstrictmax	 = p.isstrictmax
		 , tmp.abilityweight   = p.abilityweight
		 , tmp.precisiontarget = p.precisiontarget
		 , tmp.precisiontargetmetweight    = p.precisiontargetmetweight
		 , tmp.precisiontargetnotmetweight = p.precisiontargetnotmetweight;

	-- update content level records
	update tmp_tbladminstrand tmp
	  join tmp_tblcontentproperties p on p._key = tmp._key
	   set tmp.bpweight    = p.bpweight
		 , tmp.isstrictmax = p.isstrictmax;

	-- set default values for both strand and content level
	update tmp_tbladminstrand tmp
	   set tmp.bpweight     = coalesce(tmp.bpweight, 1)
		 , tmp.isstrictmax	= coalesce(tmp.isstrictmax, 0);

	-- update test info, assuming the test/segment already exists
	update tbladminstrand sas
	  join tmp_tbladminstrand tmp on sas._fk_adminsubject = tmp._fk_adminsubject and sas._fk_strand = tmp._fk_strand
	   set sas._fk_adminsubject = tmp._fk_adminsubject
		 , sas._fk_strand 	= tmp._fk_strand
		 , sas.minitems 	= tmp.minitems
		 , sas.maxitems 	= tmp.maxitems
		 , sas.bpweight 	= tmp.bpweight
		 , sas.adaptivecut 	= tmp.adaptivecut
		 , sas.startability = tmp.startability
		 , sas.startinfo 	= tmp.startinfo
		 , sas.scalar 		= tmp.scalar
		 , sas.isstrictmax  = tmp.isstrictmax
		 , sas.updateconfig = tmp.version
		 , sas.loadmin		= tmp.loadmin
		 , sas.loadmax		= tmp.loadmax
		 , sas.precisiontarget 				= tmp.precisiontarget
		 , sas.precisiontargetmetweight 	= tmp.precisiontargetmetweight
		 , sas.precisiontargetnotmetweight  = tmp.precisiontargetnotmetweight
		 , sas.abilityweight 				= tmp.abilityweight
	;

	-- check if test or segment already exists
	insert into tbladminstrand (_key, _fk_adminsubject, _fk_strand, minitems, maxitems, isstrictmax, bpweight, adaptivecut, startability, startinfo, scalar, loadconfig, loadmin, loadmax, precisiontarget, precisiontargetmetweight, precisiontargetnotmetweight, abilityweight)
	select _key, _fk_adminsubject, _fk_strand, minitems, maxitems, isstrictmax, bpweight, adaptivecut, startability, startinfo, scalar, version, loadmin, loadmax, precisiontarget, precisiontargetmetweight, precisiontargetnotmetweight, abilityweight
	  from tmp_tbladminstrand tmp
	 where not exists ( select 1 
						  from tbladminstrand sas
						 where sas._fk_adminsubject = tmp._fk_adminsubject
						   and sas._fk_strand = tmp._fk_strand);


	-- clean-up
	drop temporary table tmp_tbladminstrand;
	drop temporary table tmp_tblstrandproperties;
	drop temporary table tmp_tblcontentproperties;

end $$

DELIMITER ;