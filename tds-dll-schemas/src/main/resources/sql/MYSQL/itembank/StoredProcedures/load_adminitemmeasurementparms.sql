DELIMITER $$

drop procedure if exists load_adminitemmeasurementparms $$

create procedure load_adminitemmeasurementparms (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			4/3/2014		Sai V. 			Original Code.
*/
	v_testpackagekey varchar(350)
)
begin

	drop temporary table if exists tmp_itemdimension;
	create temporary table tmp_itemdimension (
		  _key 					varbinary(16)
		, _fk_adminsubject 		varchar(250) 
		, _fk_item 				varchar(150)
		, _fk_measurementmodel  int 
		, dimension				varchar(255)
		, recoderule			varchar(255)
		, scorepoints 			int
		, weight 				float
	);

	insert into tmp_itemdimension (_fk_adminsubject, _fk_item, _fk_measurementmodel, scorepoints, weight, dimension, recoderule)
	select distinct ais._fk_adminsubject
		 , ais._fk_item
		 , measuremodelkey
		 , isd.scorepoints
		 , isd.weight
		 , case when isd.dimensionname is null then '' else isd.dimensionname end
		 , case when prop.propvalue is null then '' else prop.propvalue end 
	  from loader_setofitemstrands ais
	  join loader_itemscoredimension isd on isd._fk_package = ais._fk_package 
										and isd.testitemid = ais._fk_item
	  left join loader_itemscoredimensionproperties prop on prop._fk_package = isd._fk_package
														and prop.testitemid = isd.testitemid 
														and prop.dimensionname = isd.dimensionname
														and propname = 'recoderule'
     where isd._fk_package = v_testpackagekey;

	-- create unique identifier for rows present in the temporary tables
	update tmp_itemdimension
	set _key = unhex(REPLACE(UUID(), '-', '')); -- uuid_short() 
	
	-- select * from tmp_itemdimension

	-- prior to loading data into itemscoredimension, clear out rows that match test and item
	-- cascading foreign key to itemmeasurementparameter ensures that they will also be deleted
	delete isd
	  from itemscoredimension  isd
	 where exists (select * 
				     from tmp_itemdimension id
					where id._fk_item = isd._fk_item and id._fk_adminsubject = isd._fk_adminsubject);

	-- write data to the disk
    insert into itemscoredimension (_key, _fk_item, _fk_adminsubject, dimension, scorepoints, weight, _fk_measurementmodel, recoderule)
    select _key
		 , _fk_item
		 , _fk_adminsubject
		 , dimension
		 , scorepoints
		 , weight
		 , coalesce(_fk_measurementmodel, 0)
		 , recoderule
    from tmp_itemdimension;

	insert into itemmeasurementparameter
	select distinct _key
		 , measurementparamnum
		 , measurementvalue
	  from loader_setofitemstrands ais
	  join loader_itemscoredimension isd on isd._fk_package = ais._fk_package 
										and isd.testitemid = ais._fk_item
	  join tmp_itemdimension id on id._fk_item = isd.testitemid 
							   and id._fk_adminsubject = ais._fk_adminsubject
							   and id.dimension = isd.dimensionname
	 where isd._fk_package = v_testpackagekey
	   and (measurementparamnum is not null or measurementvalue is not null);


    update tblsetofadminitems 
	   set bvector = coalesce(itembvector(_fk_adminsubject, _fk_item), irt_b)
     where _fk_adminsubject in (select distinct _fk_adminsubject from tmp_itemdimension);

-- ?? do we need to update tblsetofadminitems columns irt_b, irt_a, irt_c here....being done on sql server in this proc ???
--     update tblsetofadminitems 
-- 	   set irt_b = coalesce((select avg(parmvalue)
-- 							 from tmp_itemdimension l, itemmeasurementparameter p, itemscoredimension d, measurementparameter m
-- 							where d._fk_adminsubject = l._fk_adminsubject and d._fk_item = l._fk_item
-- 								and d._fk_adminsubject = tblsetofadminitems._fk_adminsubject and d._fk_item = tblsetofadminitems._fk_item
-- 								and d._fk_measurementmodel = m._fk_measurementmodel and m.parmname like 'b%' 
-- 								and p._fk_itemscoredimension = d._key and p._fk_measurementparameter = m.parmnum), irt_b)
--      where isfieldtest = 0;
-- 
--     update tblsetofadminitems 
-- 	   set irt_a = coalesce((select parmvalue
-- 							from tmp_itemdimension l, itemscoredimension d, itemmeasurementparameter p, measurementparameter m
-- 							where d._fk_adminsubject = l._fk_adminsubject and d._fk_item = l._fk_item
-- 								and d._fk_adminsubject = tblsetofadminitems._fk_adminsubject
-- 								and tblsetofadminitems._fk_item = d._fk_item and p._fk_itemscoredimension = d._key
-- 								and d._fk_measurementmodel = m._fk_measurementmodel and m.parmname = 'a' and p._fk_measurementparameter = m.parmnum), 1.0)
-- 	 where isfieldtest = 0;
-- 
--     update tblsetofadminitems 
-- 	   set irt_c = coalesce((select parmvalue
-- 							from tmp_itemdimension l, itemscoredimension d, itemmeasurementparameter p, measurementparameter m
-- 							where d._fk_adminsubject = l._fk_adminsubject and d._fk_item = l._fk_item
-- 								and d._fk_adminsubject = tblsetofadminitems._fk_adminsubject
-- 								and tblsetofadminitems._fk_item = d._fk_item and p._fk_itemscoredimension = d._key
-- 								and d._fk_measurementmodel = m._fk_measurementmodel and m.parmname = 'c' and p._fk_measurementparameter = m.parmnum), 0)
-- 	 where isfieldtest = 0;
--
--     update tblsetofadminitems set irt_model = coalesce(m.modelname, 'irt3pl')
--     from loader_itemscoredimension l, itemscoredimension d, measurementmodel m
--     where d._fk_adminsubject = l._testkey and d._fk_item = l._itemkey
--         and isfieldtest = 0
--         and d._fk_adminsubject = tblsetofadminitems._fk_adminsubject and tblsetofadminitems._fk_item = d._fk_item 
--         and d._fk_measurementmodel = m.modelnumber;

	-- clean-up
	drop temporary table tmp_itemdimension;

end $$

DELIMITER ;