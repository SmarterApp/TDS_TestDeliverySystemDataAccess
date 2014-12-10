DELIMITER $$

drop procedure if exists `loader_main` $$

create procedure `loader_main` (
/*
Description: Entry point for loading itembank tables from the testpackage file in xml format.

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/23/2014		Sai V. 			--
*/
	v_xml  longtext
)
begin

	-- declare variables
	declare v_testpackagekey varchar(350);

/*** STEP 1: extract data from xml and load it into loader_* tables ***/
	call loader_extractxml(v_xml, v_testpackagekey /*output*/);
	select v_testpackagekey;


/*** STEP 2: Prep the loader_* tables with missing/computed column data ***/	
	-- look into this, see if this needs to be called: Load_MeasurementParameters
	call load_measurementparameters(NULL);

	update loader_testpackage
	   set clientkey = (select _key from tblclient where `name` = publisher)
	 where packagekey = v_testpackagekey;

	update loader_testpackage tp
	  join tblitembank ib on ib._fk_client = tp.clientkey
	   set tp._efk_itembank = ib._efk_itembank
	 where packagekey = v_testpackagekey;

	update loader_testpackage 
	   set testadmin  = publisher
	 where packagekey = v_testpackagekey;

	update loader_testpackage tp
	  join loader_testpackageproperties	tpp on tpp._fk_package = tp.packagekey and tpp.propname = 'subject'
	   set tp.subjectkey  = concat(tp.publisher, '-', tpp.propvalue)
		 , tp.subjectname = tpp.propvalue
	 where packagekey = v_testpackagekey;

	update loader_testblueprint
	   set treelevel = case when elementtype in ('strand', 'contentlevel') 
							then length(bpelementid) - length(replace(bpelementid, '|', '')) + 1
							else -1 
						end
	 where _fk_package = v_testpackagekey;

	update loader_testpassages
	   set filepath = concat(substring_index(filename, '.', 1), '/')
	 where _fk_package = v_testpackagekey;

	update loader_testitem
	   set filepath = concat(substring_index(filename, '.', 1), '/')
	 where _fk_package = v_testpackagekey;

	update loader_testitemrefs tir
	  join loader_testblueprint tbp on tbp._fk_package = tir._fk_package and tbp.bpelementid = tir.ref and tbp.elementtype <> 'test'
	   set tir.refcategory = tbp.elementtype
		 , tir.treelevel = tbp.treelevel
	 where tir._fk_package = v_testpackagekey;	

	update loader_itemscoredimension dim
	  join measurementmodel m on m.modelname = dim.measuremodel
	   set dim.measuremodelkey = m.modelnumber
	 where _fk_package = v_testpackagekey;

	update loader_itemscoredimension dim
	  join measurementparameter mp on mp._fk_measurementmodel = dim.measuremodelkey and mp.parmname = dim.measurementparam
	   set dim.measurementparamnum = mp.parmnum
	 where _fk_package = v_testpackagekey;


/*** STEP 3: Validate data in loader_* tables ***/	
-- 	call loader_validate(v_testpackagekey);


/*** STEP 4: Process and load data into actual itembank tables ***/	

				/* ?? do we need this logic
					select @clientkey = _key, @oldpath = homepath from tblclient 
					where [name] = (select ClientName from Loader_Itembank);

					if (@filepath is null and @oldpath is null) begin
						select 'Missing filepath for itembank content';
						return 0;
					end

					if (@filepath is null) set @filepath = @oldpath;
					if (charindex('\', @filepath) > 0) set @pathdelim = '\';
					else set @pathdelim = '/';
					
					set @lastchar = substring(@filepath, len(@filepath), 1);
					if (@lastchar <> @pathdelim)
						set @filepath = @filepath + @pathdelim;
				*/

	call load_subject(v_testpackagekey);
	call load_strands(v_testpackagekey);
	
	call load_stimuli(v_testpackagekey);
	call load_items(v_testpackagekey);

	call load_linkitemstostrands(v_testpackagekey);
	call load_linkitemstostimuli(v_testpackagekey);
	call load_itemproperties(v_testpackagekey);

	-- start loading test administration
	call load_testadmin(v_testpackagekey);
	call load_adminsubjects(v_testpackagekey);
	call load_adminstrands(v_testpackagekey);
	call load_adminitems(v_testpackagekey);

	call load_adminitemmeasurementparms(v_testpackagekey);
	call load_adminStimuli(v_testpackagekey);
	call load_adminforms(v_testpackagekey);
	call load_adminformitems(v_testpackagekey);
	call load_affinitygroups(v_testpackagekey);

	
	/*** STEP 5: Populate client_* tables in configs db ***/
 	call updatetdsconfigs(v_testpackagekey);


	/*** STEP 6: Clear loader_* tables ***/
	call loader_clear(v_testpackagekey);


end $$

DELIMITER ;