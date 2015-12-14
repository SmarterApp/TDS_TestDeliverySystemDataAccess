DELIMITER $$

drop procedure if exists `loader_itemscoredimension` $$

create procedure `loader_itemscoredimension` (
/*
Description: This procedure extracts data from <itemscoredimension> 

Sample XML: <testitem>
				<itemscoredimension measuremodel="" scorepoints="" weight="">
					<itemscoreparameter measurementparameter="" value=""/>
					:
				</itemscoredimension>
			</testitem>

Usage: call loader_itemscoredimension( 'test'
										,	'<testitem filename="item-143-7007.xml" itemtype="SER">
												<identifier uniqueid="143-7007" name="000" version="4491"/>
												<bpref>(Oregon)Oregon-ELPA Speaking-6-8-winter-2013-2014</bpref>
												<bpref>ELPA-ELPA Speaking-Speaking</bpref>
												<bpref>ELPA-ELPA Speaking-Speaking|SER</bpref>
												<poolproperty property="Language" value="ENU" label="English"/>
												<poolproperty property="TDSPoolFilter" value="Speaking" label="TDSPoolFilter = Speaking"/>
												<itemscoredimension measurementmodel="IRTPCL" dimension="Grammar" scorepoints="2" weight="1.000000000000000e+000">
													<itemscoreparameter measurementparameter="b0" value="-9.000000000000000e+000"/>
													<itemscoreparameter measurementparameter="b1" value="-9.000000000000000e+000"/>
													<property name="recoderule" value="ELPA_Grammar_Recode"/>
												</itemscoredimension>
												<itemscoredimension measurementmodel="IRTPCL" dimension="Illocution" scorepoints="2" weight="1.000000000000000e+000">
													<itemscoreparameter measurementparameter="b0" value="-9.000000000000000e+000"/>
													<itemscoreparameter measurementparameter="b1" value="-9.000000000000000e+000"/>
													<property name="recoderule" value="ELPA_Illocution_Recode"/>
												</itemscoredimension>
											</testitem>'
										, 'testitem/itemscoredimension'
										, '143-7007'
		);


VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/18/2014		Sai V. 			--

*/
	v_testpackagekey varchar(350)
  ,	v_xml  		 longtext
  , v_path 		 varchar(300)
  , v_testitemid varchar(150)
)
begin

	declare v_isdloop int;
	declare v_isploop int;	
	declare v_isdcounter int default 1;
	declare v_ispcounter int default 1;

	declare v_dimension     varchar(130);
	declare v_measuremodel  varchar(100);
	declare v_scorepoints 	int;
	declare v_weight 		float;

	-- set v_path = 'testspecification/complete/itempool/testitem/itemscoredimension';
	set v_isdloop 		   = extractvalue(v_xml, concat('count(', v_path, ')') );


	while v_isdcounter <= v_isdloop do
	begin

		set v_measuremodel = extractvalue(v_xml, concat(v_path, '[', v_isdcounter, ']/attribute::measurementmodel'));
		set v_dimension    = extractvalue(v_xml, concat(v_path, '[', v_isdcounter, ']/attribute::dimension'));
		set v_scorepoints  = extractvalue(v_xml, concat(v_path, '[', v_isdcounter, ']/attribute::scorepoints'));
		set v_weight 	   = extractvalue(v_xml, concat(v_path, '[', v_isdcounter, ']/attribute::weight'));

		set v_isploop 	   = extractvalue(v_xml, concat('count(', concat(v_path, '[', v_isdcounter, ']/itemscoreparameter'), ')') );
		set v_ispcounter   = 1;
	
		if v_isploop = 0 then
		begin
			-- need to load score dimension data even when measurement data does not exist
			insert into loader_itemscoredimension (_fk_package, testitemid, measuremodel, dimensionname, scorepoints, weight)
			select v_testpackagekey
				 , v_testitemid
				 , v_measuremodel
				 , v_dimension
				 , v_scorepoints
				 , v_weight
			;
		end;
		else 
		begin
			while v_ispcounter <= v_isploop do
				insert into loader_itemscoredimension (_fk_package, testitemid, measuremodel, dimensionname, scorepoints, weight, measurementparam, measurementvalue)
				select v_testpackagekey
					 , v_testitemid
					 , v_measuremodel
					 , v_dimension
					 , v_scorepoints
					 , v_weight
					 , extractvalue(v_xml, concat(v_path, '[', v_isdcounter, ']', '/itemscoreparameter', '[', v_ispcounter, ']/attribute::measurementparameter'))
					 , extractvalue(v_xml, concat(v_path, '[', v_isdcounter, ']', '/itemscoreparameter', '[', v_ispcounter, ']/attribute::value'))
				;

				-- increment counter
				set v_ispcounter = v_ispcounter + 1;
		   end while;

			-- can properties exists even when itemscoreparameter does not exist ??????
			-- can more than 1 property exists ??? at the moment assumption is that only one property exists....so instead of calling another procedure
			-- writing the logic here......create and move logic to proc loader_itemscoredimensionproperties if needed
			-- call loader_itemscoredimensionproperties (v_testitemid, v_measuremodel, v_dimension);

			insert into loader_itemscoredimensionproperties
			select v_testpackagekey
				 , v_testitemid
				 , v_dimension 
				 , extractvalue(v_xml, concat(v_path, '[', v_isdcounter, ']', '/property/attribute::name'))
				 , extractvalue(v_xml, concat(v_path, '[', v_isdcounter, ']', '/property/attribute::value'));

		end;
		end if;
	
		set v_isdcounter = v_isdcounter + 1;
	end;
	end while;





end $$

DELIMITER ;