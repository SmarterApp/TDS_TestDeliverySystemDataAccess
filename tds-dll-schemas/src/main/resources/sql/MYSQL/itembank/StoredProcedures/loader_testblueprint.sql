DELIMITER $$

drop procedure if exists `loader_testblueprint` $$

create procedure `loader_testblueprint` (
/*
Description: This procedure extracts data from <testblueprint> 
		  -- Test or segment top-level blueprint specification.
		  -- Test-level blueprint is needed to score or report a test. 

Sample XML: <testblueprint>
				<bpelement elementtype="" minopitems="" maxopitems="" minftitems="" maxftitems="" opitemcount="" ftitemcount="">
					<identifier uniqueid="" name="" version=""/>
				</bpelement>
			:
			:
			</testblueprint>

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/18/2014		Sai V. 			--
*/
	v_testpackagekey varchar(350)
  ,	v_xml  			 longtext
  , v_path 			 varchar(300)
)
begin
    
	declare v_loop 	  int;
	declare v_counter int default 1;

	-- example: v_path = 'testspecification/complete/testblueprint/bpelement';
	set v_loop =  extractvalue(v_xml, concat('count(', v_path, ')') );

	while v_counter <= v_loop do
		insert into loader_testblueprint (_fk_package, elementtype, bpelementid, bpelementname, minopitems, maxopitems, minftitems, maxftitems, opitemcount, ftitemcount, parentid, version)
		select v_testpackagekey
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::elementtype'))
             , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/identifier/attribute::uniqueid'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/identifier/attribute::name'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::minopitems'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::maxopitems'))
			 , case when extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::minftitems')) = '' then 0 else extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::minftitems')) end
			 , case when extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::maxftitems')) = '' then 0 else extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::maxftitems')) end
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::opitemcount'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::ftitemcount'))
			 , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/attribute::parentid'))
             , extractvalue(v_xml, concat(v_path, '[', v_counter, ']/identifier/attribute::version'))
		;
		
		-- increment counter
		set v_counter = v_counter + 1;

   end while;


end $$

DELIMITER ;