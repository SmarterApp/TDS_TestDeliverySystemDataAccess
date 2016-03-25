-- -------------------------------
-- routine tds_eligibletests
-- -------------------------------
delimiter $$

drop function if exists tds_eligibletests $$

create function tds_eligibletests (
/*
VERSION 	DATE 			AUTHOR 			COMMENTS
001			11/4/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_entitykey bigint
  , v_clientname varchar(50))
returns text
begin

    declare v_bridge2011 bit;    
	declare v_discard bit;
	declare v_value varchar(255);
	declare v_result text;
	
	set v_bridge2011 = 0;

	set transaction isolation level read uncommitted;

	-- checking to see if the table already exists.
	-- if it already exists deleting the table content instead of dropping the table, b'coz 
	-- mysql does not allow explicit or implicit commits within a function.
    create temporary table if not exists tblvals (val varchar(255));
	delete from tblvals;    
   
    if (v_bridge2011 = 0) then
        insert into tblvals (val)
        select v.testid
        from eligibletest v
        where v.clientname = v_clientname and v._fk_entity = v_entitykey 
            and (v.enddate is null or v.enddate > now());
    else 
	begin
		create temporary table if not exists tblout_bridge_eligibletests (testID varchar(100));
		delete from tblout_bridge_eligibletests;

		select bridge_eligibletests(v_clientname, v_entitykey) into v_discard;

        insert into tblvals (val)
        select testid 
		from tblout_bridge_eligibletests;
	end;
	end if;

    while (exists (select * from tblvals)) do
        select val into v_value from tblvals limit 1;
		delete from tblvals where val = v_value;

        if (v_result is null) then
            set v_result = v_value;
        else 
			set v_result = v_result + ';' + v_value;
		end if;
    end while;

	return v_result;

end $$

delimiter ;