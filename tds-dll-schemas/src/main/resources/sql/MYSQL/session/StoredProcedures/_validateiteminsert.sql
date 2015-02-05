DELIMITER $$

drop procedure if exists _validateiteminsert $$

create procedure _validateiteminsert (
/*
Description: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			1/29/2015		Sai V. 			Converted code from T-SQL to MySQL
*/
	v_oppkey 	varbinary(16)
  ,	v_page 		int
  , v_segment 	int
  , v_segmentid varchar(100)
  , v_groupid 	varchar(100)
  , out v_msg 	varchar(200)
)								
proc: begin

    declare v_lastpage, v_lastsegment, v_lastposition int;
	declare v_segmentid2 	varchar(100);
	declare v_algorithm 	varchar(50);

    set v_lastposition = (select max(position)
							from testeeresponse where _fk_testopportunity = v_oppkey and _efk_itsitem is not null);

    select segment, `page`
	into v_lastsegment, v_lastpage
    from testeeresponse 
	where _fk_testopportunity = v_oppkey and position = v_lastposition;

    if (v_page <= v_lastpage) then 
	begin
        set v_msg = concat('attempt to overwrite existing page: ', ltrim(cast(v_page as char(20))));
        leave proc;
    end;
	end if;

    if (v_page > v_lastpage + 1) then 
	begin
        set v_msg = concat('attempt to write non-contiguous page: ', ltrim(cast(v_page as char(20))));
        leave proc;
    end;
	end if;
   
	set v_segmentid2 = '';
	set v_algorithm = '';
	
	select segmentid, `algorithm`
	into v_segmentid2, v_algorithm
	from testopportunitysegment 
	where _fk_testopportunity = v_oppkey and segmentposition = v_segment;
	
	if (v_segmentid <> v_segmentid2) then 
	begin
        set v_msg = 'segment id does not match segment position';
        leave proc;
    end;
	end if;

    if ((v_segment < v_lastsegment or v_segment > v_lastsegment + 1) and v_algorithm <> 'altadaptive') then 
	begin
        set v_msg = concat('attempt to write non-contiguous segment: ', ltrim(cast(v_segment as char(20)))) ;
        leave proc;
    end;
	end if;

    if (exists (select * from testeeresponse where _fk_testopportunity = v_oppkey and groupid = v_groupid)) then 
	begin
		set v_msg = concat('attempt to duplicate existing item group: ', v_groupid) ;
		leave proc;
    end;
	end if;

end $$

DELIMITER ;
