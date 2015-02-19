DELIMITER $$

drop procedure if exists `_gettesteeattributes` $$

create procedure `_gettesteeattributes`(
/*
DESCRIPTION: 

VERSION 	DATE 			AUTHOR 			COMMENTS
001			2/11/2015		Sai V. 			Code Migration
*/
	v_clientname varchar(100)
  , v_testee 	bigint
)
sql security invoker
proc: begin

    declare v_attname varchar(50);
    declare v_rtsname varchar(100);
    declare v_attval text;
    declare v_err varchar(200);

    drop temporary table if exists tmp_tblattributes;
	create temporary table tmp_tblattributes(
		attname varchar(50)
	  , rtsname varchar(100)
	  , attval text
	  , done bit
	);

    insert into tmp_tblattributes (attname,  rtsname)
    select tds_id, rtsname
    from configs.client_testeeattribute 
	where clientname = v_clientname and `type` = 'attribute';

    while (exists (select * from tmp_tblattributes where done is null)) do
	begin
        select attname, rtsname 
		into v_attname, v_rtsname
		from tmp_tblattributes 
		where done is null limit 1;  
        
        set v_attval = null;

        -- v_testee < 0 is an anonymous student so no info exists in rts for this testee
        if (v_testee > 0) then  
			call _getrtsattribute(v_clientname, v_testee, v_rtsname, v_attval /*output*/, 'student', 0);
        else 
			set v_attval = 'guest ' + v_attname;
		end if;

        if (v_attval is not null) then
            update tmp_tblattributes 
			set attval = v_attval
			  , done = 1 
			where attname = v_attname;
        else
            set v_err = concat('unknown attribute type: ', (case when v_attname is null then '<null attribute>' else v_attname end));

            update tmp_tblattributes 
			set done = 1 
			where attname = v_attname;

            call _logdberror('_gettesteeattributes', v_err, v_testee, null, null, null, v_clientname, null);
        end if;
	end;
	end while;

	insert into tblout_gettesteeattributes
	select attname as tds_id, attval 
	from tmp_tblattributes;

end $$

DELIMITER ;
