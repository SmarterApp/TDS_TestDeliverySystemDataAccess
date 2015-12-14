DELIMITER $$

drop procedure if exists `loader_testitempool` $$

create procedure `loader_testitempool` (
/*
Description: This procedure extracts data from <itempool> 

Sample XML: <itempool>
				<passage filename="">
					<identifier uniqueid="" name="" version=""/>
				</passage>
				:
				:
				<testitem filename="" itemtype="">
					<identifier uniqueid="" name="" version=""/>
					<bpref></bpref>
					:
					:
					<passageref></passageref>
					<poolproperty property="" value="" label=""/>
					<itemscoredimension measuremodel="" scorepoints="" weight="">
						<itemscoreparameter measurementparameter="" value=""/>
						<itemscoreparameter measurementparameter="" value=""/>
						<itemscoreparameter measurementparameter="" value=""/>
					</itemscoredimension>
				</testitem>
			:
			:
			</itempool>

VERSION 	DATE 			AUTHOR 			COMMENTS
001			3/18/2013		Sai V. 			Original Code.
*/
	v_testpackagekey varchar(350)
  ,	v_xml  			 longtext
  , v_path 			 varchar(300)
)
begin
    
	declare v_testpassages varchar(300);
	declare v_testitem varchar(300);

	-- example: v_path = 'testspecification/complete/itempool/';
	set v_testpassages = concat(v_path, 'passage');
	call loader_testpassages(v_testpackagekey, v_xml, v_testpassages);

	set v_testitem = concat(v_path, 'testitem');
	call loader_testitem(v_testpackagekey, v_xml, v_testitem);


end $$

DELIMITER ;