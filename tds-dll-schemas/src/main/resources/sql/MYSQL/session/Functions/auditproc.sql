DELIMITER $$

drop function if exists `auditproc` $$

create function `auditproc`(
/*
DESCRIPTION: tells whether a stored procedure's performance timing should be audited

VERSION 	DATE 			AUTHOR 			COMMENTS
001			02/02/2015		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_procname varchar(50)
)
returns bit
begin

    -- with the following we can turn blocks of proc auditing off
    declare v_auditinternal bit;
    declare v_auditreporting bit;
    declare v_auditqc bit;
    declare v_auditpipefile bit;
    declare v_auditproctor bit;
    declare v_auditfieldtest bit;
    declare v_audittestee bit;
    declare v_checkindividual bit;   -- if 0, then only discriminate on sp type (i.e. internal, testee, proctor, reporting)

    declare v_auditon bit;   -- allows turning all proc auditing on or off with this flag
    set v_auditon = 1;

    if (v_auditon = 0) then return 0; end if;

    set v_auditfieldtest = 1;
    set v_auditinternal = 1;     
    set v_audittestee = 1;
    set v_auditproctor = 1;
    set v_auditreporting = 1;
    set v_auditqc = 0;
    set v_auditpipefile = 1;     -- but i don't think there is any code in those procs for this
    set v_checkindividual = 0;   -- if 0, then don't discriminate individually, only in blocks

    if (exists (select * from _externs where environment = 'simulation')) then 
        set v_auditinternal = 0;
    end if;

   
    if (v_audittestee = 0 and (v_procname like 't_%' or v_procname like 's_%')) then return 0; end if;
    if (v_auditproctor = 0 and v_procname like 'p_%' ) then return 0; end if;
    if (v_auditfieldtest = 0 and v_procname like 'ft_%' ) then return 0; end if;  
    if (v_auditreporting = 0  and v_procname like 'r_%'  ) then return 0; end if;
    if (v_auditqc = 0 and v_procname like 'qc_%') then return 0; end if;
    if (v_auditpipefile = 0 and v_procname like 'ode_%') then return 0; end if;
    if (v_auditinternal = 0 and left(v_procname, 1) = '_') then return 0; end if;

    -- special auditing of proctor logouts. exclude all those calls to getxxxtestees for now.
	--   if (v_auditproctor = 1 and v_procname like 'p_get%' ) return 0;

    -- if we are not checking for individual auditing and have not yet intercepted this object, then audit this object
    return 1; 

    
end $$

DELIMITER ;