-- ----------------------------
-- routine bridge_eligibletests
-- ----------------------------
delimiter $$

drop function if exists bridge_eligibletests $$

create function bridge_eligibletests (
/*
VERSION 	DATE 			AUTHOR 			COMMENTS
001			11/4/2014		Sai V. 			Converted code from SQL Server to MySQL
*/
	v_clientname varchar(100)
  , v_entitykey bigint)
returns bit
begin
    
	declare v_date datetime;
    declare v_challengekey bigint;
	
	set v_date = now();
    select _key into v_challengekey from tblentityattribute where fieldname = 'CHALLENGEUP';

	-- checking to see if the table already exists.
	-- if it already exists deleting the table content instead of dropping the table, b'coz 
	-- mysql does not allow explicit or implicit commits within a function.
    create temporary table if not exists tbltests (
		tid varchar(100) primary key not null,  `subject` varchar(100), challengeup varchar(100), attsenabled int, attsdisabled int, needatts int);
	delete from tbltests;
                            
	
    insert into tbltests (tid, `subject`, attsenabled, needatts)
    select p.testid,  p.subjectname, 0, count(distinct rtsname)    
    from tdsconfigs_client_testproperties p, tdsconfigs_client_testeligibility e
    where p.clientname = v_clientname and e.clientname = v_clientname 
		and e.testid = p.testid and e.eligibilitytype = 'ATTRIBUTE' and e.enables = 1
    group by p.testid,  p.subjectname;


    delete 
	from tbltests 
	where exists (select * 
				from tdscore_dev_configs2012_sandbox.client_testeligibility e, tblattributevalue v, tblentityattribute a
				where e.clientname = v_clientname and a.fieldname = e.rtsname and e.disables = 1 and tid = e.testid
					and v.enddate > v_date and a._fk_entitytype = e._efk_entitytype and e.eligibilitytype = 'ATTRIBUTE'
					and v._fk_entity = v_entitykey and v._fk_entityattribute = a._key 
					and v.attrvalue like case e.matchtype when 0 then e.rtsvalue when -1 then e.rtsvalue + '%' else '%' + e.rtsvalue end);


    update tbltests 
	set attsenabled = (select count(distinct _fk_entityattribute) 
						from tdscore_dev_configs2012_sandbox.client_testeligibility e, tblattributevalue v, tblentityattribute a
						where e.clientname = v_clientname and a.fieldname = e.rtsname and e.enables = 1 and tid = e.testid and e.eligibilitytype = 'ATTRIBUTE'
							and v.enddate > v_date and a._fk_entitytype = e._efk_entitytype
							and v._fk_entity = v_entitykey and v._fk_entityattribute = a._key 
							and v.attrvalue like case e.matchtype when 0 then e.rtsvalue when -1 then e.rtsvalue + '%' else '%' + e.rtsvalue end);


    delete from tbltests where (attsenabled < needatts);

    if (exists (select * from tblattributevalue where _fk_entity = v_entitykey and _fk_entityattribute = v_challengekey and length(attrvalue) > 0 and enddate > v_date)) then
        update tbltests, tdscore_dev_configs2012_sandbox.client_testproperties p, tblattributevalue v 
		set tid = p.testid
        where p.clientname = v_clientname and v._fk_entity = v_entitykey and v._fk_entityattribute = v_challengekey
            and p.testid = v.attrvalue and p.subjectname = subject and v.enddate > v_date;
    end if;

    insert into tblout_bridge_eligibletests (testid)
    select tid as testid from tbltests;

    return 1;

end $$

delimiter ;