DELIMITER $$

drop procedure if exists `loader_testproperties` $$

create procedure `loader_testproperties` (
	v_xml  longtext)
begin
    
	declare v_loop 	  int;
	declare v_counter int;
	declare v_path     varchar(500);
	declare v_uniqueid varchar(170);

	set v_path = 'testspecification/complete/poolproperty';
	set v_loop =  extractvalue(v_xml, concat('count(', v_path, ')') );

	-- need to fetch id value only once
	set v_uniqueid = (extractvalue(v_xml, 'testspecification/identifier/attribute::uniqueid'));

	-- initialize counter value
	set v_counter = 1;

	while v_counter <= v_loop do
		insert into loader_testproperties (propertyname, propertyvalue, label, itemcount, uniqueid)
		select extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::property'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::value'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::label'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::itemcount'))
			 , v_uniqueid
		;
		
		-- increment counter
		set v_counter = v_counter + 1;
   end while;


end $$

DELIMITER ;