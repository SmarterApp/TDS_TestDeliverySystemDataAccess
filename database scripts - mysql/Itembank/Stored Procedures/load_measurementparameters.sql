DELIMITER $$

drop procedure if exists load_measurementparameters $$

create procedure load_measurementparameters (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			4/3/2014		Sai V. 			--
*/
	v_overwrite bit
)
begin

    declare v_nmodels int;
    declare v_nparms  int;
    declare v_models  int;
    declare v_parms   int;

	if v_overwrite is null then set v_overwrite = 0; end if;

	-- the following represents an alteration of the definition of irtpcl. 
	-- the slope parameter has been removed and is assumed to be 1.0
	-- this approach was taken because of data that had already been generated under this assumption in contradiction to the way the model had been defined.

	update measurementparameter set parmname = 'b0', parmdescription = 'Difficulty cut 0 (b0)'
	 where _fk_measurementmodel = 2 and parmnum = 0;

	-- also with the 3pl model
	update measurementparameter set parmname = 'a', parmdescription = 'Slope (a)' 
	 where _fk_measurementmodel = 1 and parmnum = 0;
	update measurementparameter set parmname = 'b', parmdescription = 'Difficulty (b)' 
	 where _fk_measurementmodel = 1 and parmnum = 1;

	-- NOTE That ItemScoreDimension depends upon MeasurementModel and ItemMeasurementParameter depends upon MeasurementParameter
	-- They are not linked by foreign key cascade constraints, but a missing reference can cause needed item scoring data to fail to reach the scoring engine
    delete from loader_measurementparameter;    -- make sure this is up to date
	
   
    insert into loader_measurementparameter(modelnum, parmnum, parmname, parmdescription, modelname)
      values(1.0, 0.0, 'a', 'Slope (a)', 'IRT3pln');

    insert into loader_measurementparameter(modelnum, parmnum, parmname, parmdescription, modelname)
      values(1.0, 1.0, 'b', 'Difficulty (b)', 'IRT3pln');
   
    insert into loader_measurementparameter(modelnum, parmnum, parmname, parmdescription, modelname)
      values(1.0, 2.0, 'c', 'Guessing (c)', 'IRT3pln');
   
	-- assume slope = 1.0 for IRTPCL, per Paul Van Wamelen
    insert into loader_measurementparameter(modelnum, parmnum, parmname, parmdescription, modelname)
      values(2.0, 0.0, 'b0', 'Difficulty cut 0 (b0)', 'IRTPCL');
   
    insert into loader_measurementparameter(modelnum, parmnum, parmname, parmdescription, modelname)
      values(2.0, 1.0, 'b1', 'Difficulty cut 1 (b1)', 'IRTPCL');
   
    insert into loader_measurementparameter(modelnum, parmnum, parmname, parmdescription, modelname)
      values(2.0, 2.0, 'b2', 'Difficulty cut 2 (b2)', 'IRTPCL');
   
    insert into loader_measurementparameter(modelnum, parmnum, parmname, parmdescription, modelname)
      values(2.0, 3.0, 'b3', 'Difficulty cut 3 (b3)', 'IRTPCL');
   
    insert into loader_measurementparameter(modelnum, parmnum, parmname, parmdescription, modelname)
      values(2.0, 4.0, 'b4', 'Difficulty cut 4 (b4)', 'IRTPCL');
   
    insert into loader_measurementparameter(modelnum, parmnum, parmname, parmdescription, modelname)
      values(2.0, 5.0, 'b5', 'Difficulty cut 5 (b5)', 'IRTPCL');

    insert into loader_measurementparameter(modelnum, parmnum, parmname, parmdescription, modelname)
      values (3, null, null, 'Raw (not IRT) scoring model', 'raw');

    
   
    insert into loader_measurementparameter(modelnum, parmnum, parmname, parmdescription, modelname)
      values(4, 0.0, 'a', 'Slope (a)', 'IRT3PL');

    insert into loader_measurementparameter(modelnum, parmnum, parmname, parmdescription, modelname)
      values(4, 1.0, 'b', 'Difficulty (b)', 'IRT3PL');
   
    insert into loader_measurementparameter(modelnum, parmnum, parmname, parmdescription, modelname)
      values(4, 2.0, 'c', 'Guessing (c)', 'IRT3PL');



    insert into loader_measurementparameter(modelnum, parmnum, parmname, parmdescription, modelname)
      values(5, 0.0, 'a', 'Slope (a)', 'IRTGPC');

    insert into loader_measurementparameter(modelnum, parmnum, parmname, parmdescription, modelname)
      values(5, 1.0, 'b0', 'Difficulty cut 0 (b0)', 'IRTGPC');
   
    insert into loader_measurementparameter(modelnum, parmnum, parmname, parmdescription, modelname)
      values(5, 2.0, 'b1', 'Difficulty cut 1 (b1)', 'IRTGPC');

    insert into loader_measurementparameter(modelnum, parmnum, parmname, parmdescription, modelname)
      values(5, 3.0, 'b2', 'Difficulty cut 2 (b2)', 'IRTGPC');
   
    insert into loader_measurementparameter(modelnum, parmnum, parmname, parmdescription, modelname)
      values(5, 4.0, 'b3', 'Difficulty cut 3 (b3)', 'IRTGPC');
   
    insert into loader_measurementparameter(modelnum, parmnum, parmname, parmdescription, modelname)
      values(5, 5.0, 'b4', 'Difficulty cut 4 (b4)', 'IRTGPC');
   
    insert into loader_measurementparameter(modelnum, parmnum, parmname, parmdescription, modelname)
      values(5, 6.0, 'b5', 'Difficulty cut 5 (b5)', 'IRTGPC');


    set v_nmodels = (select count(distinct modelnum) from loader_measurementparameter);
    set v_nparms  = (select count(*) from loader_measurementparameter);
    set v_models  = (select count(*) from measurementmodel);
    set v_parms   = (select count(*) from measurementparameter);
        
    if (v_overwrite = 1 or v_nmodels <> v_models or v_nparms <> v_parms) then 
	begin   
        delete from measurementparameter;
        delete from measurementmodel;

        insert into measurementmodel(modelnumber, modelname) 
        select distinct modelnum, modelname 
          from loader_measurementparameter;

        insert into measurementparameter(_fk_measurementmodel, parmnum, parmname, parmdescription) 
        select modelnum, parmnum, parmname, parmdescription
          from loader_measurementparameter 
		 where parmnum is not null;
    end;
	end if;
   
end $$

DELIMITER ;