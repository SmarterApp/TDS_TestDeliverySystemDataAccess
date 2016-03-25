DELIMITER $$

drop procedure if exists `loader_testforms` $$

create procedure `loader_testforms` (
/*
Description: This procedure extracts data from <testform> 

Sample XML: <testform length="">
			  <identifier uniqueid="" name="" version=""/>
			  <property name="" value="" label=""/>
			  <poolproperty property="" value="" label="" itemcount=""/>
			  :
			  <formpartition>
				<identifier uniqueid="" name="" version=""/>
				<itemgroup formposition="" maxitems="" maxresponses="">
				  <identifier uniqueid="" name="" version=""/>
				  <groupitem itemid="" formposition="" groupposition="" adminrequired="" responserequired="" isactive="" isfieldtest="" blockid=""/>
				</itemgroup>
				:
			  </formpartition>
			</testform>

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/18/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
  ,	v_xml  longtext
  , v_path varchar(300)
)
begin
    
	declare v_testformid varchar(200);
	declare v_testformpropertiespath varchar(300);
	declare v_testformpartitionpath	 varchar(300);

	set v_testformid = extractvalue(v_xml, concat(v_path, '/identifier/attribute::uniqueid'));

	insert into loader_testforms (_fk_package, testformid, testformname, testformlength, version)
	select v_testpackagekey
		 , v_testformid
	     , extractvalue(v_xml, concat(v_path, '/identifier/attribute::name'))
		 , extractvalue(v_xml, concat(v_path, '/attribute::length'))
		 , extractvalue(v_xml, concat(v_path, '/identifier/attribute::version'))
	;

	-- ** testform can have <property> or <poolproperty> or both. there can one or more of these properties** --
	-- ** for both these attributes values extracted are written to the same table ** --
	-- first process <property>
	set v_testformpropertiespath  = concat(v_path, '/property');
	call loader_testformproperties (v_testpackagekey, v_xml, v_testformpropertiespath, v_testformid, 0);

	-- now process <poolproperty>
	-- use the same path variable, overwrite earlier value
	set v_testformpropertiespath  = concat(v_path, '/poolproperty');
	call loader_testformproperties (v_testpackagekey, v_xml, v_testformpropertiespath, v_testformid, 1);

	-- extract data from <formpartition>
	set v_testformpartitionpath = concat(v_path, '/formpartition');
	call loader_testformpartition (v_testpackagekey, v_xml, v_testformpartitionpath, v_testformid);

end $$

DELIMITER ;