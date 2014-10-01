-- --------------------------
-- routine tds_fmapethnicity
-- --------------------------
delimiter $$

drop function if exists tds_fmapethnicity $$

create function tds_fmapethnicity (
/*
VERSION 	DATE 			AUTHOR 			COMMENTS
001			11/4/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_entitykey bigint
  , v_clientname varchar(100))
returns bigint
begin

	declare v_value varchar(255);
    declare v_race varchar(10);
    declare v_eth varchar(10);
	declare v_sped varchar(10);
    declare v_disable varchar(10);
    declare v_issped bit;
    declare v_isdisable bit;
    declare v_type int;

    set v_race = tds_fgetentityattribute(v_entitykey, 'race');
    set v_eth = tds_fgetentityattribute (v_entitykey, 'ethnicity');  

    if ((v_race is null or length(v_race) = 0) and (v_eth is not null)) then set v_race = v_eth; end if;

    if (v_clientname like 'hawaii%') then -- hawaii or hawaii_pt, same rts
        /*
        1. filipino (d) - no ell or sped
        2. japanese (g) - no ell or sped
        3. white (l) - no ell or sped
        4. hawaiian/part hawaiian (e,f) - no ell or sped
        5. other - no ell or sped
        6. all ell excluding sped
        7. all sped
        */
        set v_sped = '';
        set v_disable = '';
        set v_type = 1;        
        
        set v_disable = tds_fgetentityattribute(v_entitykey, 'pd');
        set v_isdisable = case when isnumeric(v_disable) = 1 and cast(v_disable as unsigned) > 0 then 1 else 0 end;

        while (v_sped <> 'j' and v_type <= 5) do
            set v_sped = tds_fgetentityattribute(v_entitykey, 'type' + cast(v_type as char(1)));
            set v_type = v_type + 1;
        end while;

        set v_issped = case v_sped when 'j' then 1 else 0 end;
 
        if (v_issped = 1) then return 7; end if;    -- all special ed regardless of race or disability
        if (v_isdisable = 1) then return 6; end if; -- all disability that are not special ed
        -- all racial categories with some collapsing
        if (v_race not in ('d', 'e', 'f', 'g', 'l')) then return 5; end if;

        set v_eth = case v_race
						when 'd' then 1
						when 'e' then 4 -- collapse hawaiian and part hawaiian
						when 'f' then 4
						when 'g' then 2
						when 'l' then 3
					end;

        return v_eth;

    end if;

	return coalesce(v_eth, v_race);

end $$

delimiter ;