DELIMITER $$

drop procedure if exists `_initopportunityaccommodations` $$

create procedure `_initopportunityaccommodations`(
/*
DESCRIPTION:

VERSION 	DATE 			AUTHOR 			COMMENTS
001			2/10/2015		Sai V. 			
*/
  	v_oppkey 	varbinary(16)
  , v_accoms	text -- = null
)
sql security invoker
proc: begin

    declare v_starttime datetime(3);
    declare v_clientname nvarchar(100); 
	declare  v_test varchar(200);
	declare v_testee bigint;
	declare v_testid varchar(200);
    declare v_error varchar(300);
	declare v_err text;

	declare exit handler for sqlexception
	begin		
		set v_err = 'mysql exit handler: sqlexception';
		call _logdberror('_initopportunityaccommodations', v_err, null, null, null, v_oppkey, null, null);
	end;

    set v_starttime = now(3);	

    select _efk_testid, _efk_adminsubject, _efk_testee, clientname 
	into v_testid, v_test, v_testee, v_clientname
	from testopportunity
    where _key = v_oppkey;

	drop temporary table if exists tblout_testlanguages;
	create temporary table tblout_testlanguages (
		`code`	varchar(150)
	  , label	varchar(150)
	);

	call itembank.testlanguages(v_test);

	start transaction;
		-- first, set default for every type of accommodation 
		-- valuecount is used to know whether there is a choice involved by proctor when tests restart
		-- this is particularly necessary for segment entry
		insert into testeeaccommodations (_fk_testopportunity, segment, acctype, acccode, accvalue, allowchange, testeecontrol, isselectable, isapproved, valuecount, recordusage) 
		select v_oppkey, segment, acctype, acccode, accvalue, allowchange, studentcontrol, isselectable, case valcount when 1 then 1 else 0 end, valcount
			 , coalesce((select 1 from configs.client_toolusage 
						  where clientname = v_clientname and testid = v_testid and tooltype = acctype and (recordusage = 1 or reportusage = 1)), 0)
		from -- testkeyaccommodations (v_test) a
			( -- first, get all tds tools directly related to this client's tests, 0 represents the global test scope, while segment positions are local segment score
				select distinct 0 as segment,ttype.disableonguestsession, ttype.sortorder as tooltypesortorder, tt.sortorder as toolvaluesortorder, ttype.testmode as typemode
					, tt.testmode as toolmode, `type` as acctype, `value` as accvalue, `code` as acccode, isdefault, allowcombine
					, isfunctional, allowchange, isselectable, isvisible, studentcontrol
					, (select count(*) from configs.client_testtool tool 
						where tool.contexttype = 'test' and tool.`context` = md.testid and tool.clientname = md.clientname and tool.`type` = tt.`type`) as valcount
					, dependsontooltype
					from configs.client_testtooltype ttype, configs.client_testtool tt, configs.client_testmode md
					where md.testkey = v_test and
						ttype.contexttype = 'test' and ttype.`context` = md.testid and ttype.clientname = md.clientname
						and tt.contexttype = 'test' and tt.`context` = md.testid and tt.clientname = md.clientname and tt.type = ttype.toolname
						and (tt.type <> 'language' or tt.`code` in (select `code` from tblout_testlanguages))
						and (ttype.testmode = 'all' or ttype.testmode = md.`mode`) and (tt.testmode = 'all' or tt.testmode = md.mode)
			-- get all the segment-specific accommodations
				union all
				select distinct segmentposition ,ttype.disableonguestsession, ttype.sortorder , tt.sortorder, ttype.testmode , tt.testmode 
					, `type`, `value`, `code`, isdefault, allowcombine, isfunctional, allowchange
					, isselectable, isvisible, studentcontrol
					, (select count(*) from configs.client_testtool tool 
						where tool.contexttype = 'test' and tool.`context` = md.testid and tool.clientname = md.clientname and tool.`type` = tt.`type`) as valcount
					, null  -- dependsontooltype
					from configs.client_testtooltype ttype, configs.client_testtool tt, configs.client_segmentproperties seg, configs.client_testmode md
					where parenttest = md.testid and md.testkey = v_test and seg.modekey = v_test
						and ttype.contexttype = 'segment' and ttype.`context` = segmentid and ttype.clientname = md.clientname
						and tt.contexttype = 'segment' and tt.`context` = segmentid and tt.clientname = md.clientname and tt.`type` = ttype.toolname
						and (ttype.testmode = 'all' or ttype.testmode = md.`mode`) and (tt.testmode = 'all' or tt.testmode = md.`mode`)
			-- now get all test tools that have 'wild card' (i.e. '*') assignments (segments and languages never have wildcard assignments)
				union all  
					select distinct 0,ttype.disableonguestsession,  ttype.sortorder , tt.sortorder, ttype.testmode , tt.testmode 
						, `type` , `value`, `code`, isdefault, allowcombine
						, isfunctional, allowchange, isselectable, isvisible, studentcontrol
						, (select count(*) from configs.client_testtool tool 
							where tool.contexttype = 'test' and tool.`context` = '*' and tool.clientname = md.clientname and tool.`type` = tt.`type`) as valcount
						, dependsontooltype
					from configs.client_testtooltype ttype, configs.client_testtool tt, configs.client_testmode md
					where md.testkey = v_test and ttype.contexttype = 'test' and ttype.`context` = '*' and ttype.clientname = md.clientname
						and tt.contexttype = 'test' and tt.`context` = '*' and tt.clientname = md.clientname and tt.`type` = ttype.toolname 
						and (ttype.testmode = 'all' or ttype.testmode = md.`mode`) and (tt.testmode = 'all' or tt.testmode = md.`mode`)
						and not exists (select * from configs.client_testtooltype tool 
										 where tool.contexttype = 'test' and tool.`context` = md.testid and tool.toolname = ttype.toolname and tool.clientname = md.clientname)
			) a
		where isdefault = 1 and dependsontooltype is null
		   and not exists (select * from testeeaccommodations acc where acc._fk_testopportunity = v_oppkey and acc.acccode = a.acccode);

	commit;

    if (v_testee > 0) then 
	begin
        if (v_accoms is null) then
		begin
            call _getrtsattribute(v_clientname, v_testee, '--accommodations--', v_accoms /*output*/, 'student', 0);
            if (v_accoms is null or length(v_accoms) < 1) then
                update testopportunity_readonly 
				set accommodationstring = p_formataccommodations (v_oppkey)
                where _fk_testopportunity = v_oppkey;

                call _logdblatency('_initopportunityaccommodations', v_starttime, v_testee, null, null, v_oppkey, null, v_clientname, null);

                leave proc;
            end if;
        end;
		end if;

		-- now override the defaults with those specific to this testee
        call _updateopportunityaccommodations(v_oppkey, 0, v_accoms, 0, 0, 0, v_error /*output*/, 0);


        if (v_error is not null) then
		begin -- we are having trouble with deadlocks on _update so try one more time
            set v_error = concat('accommodations update failed. making second attempt.', v_error);
            call _logdberror('_initopportunityaccommodations', v_error, null, null, null, v_oppkey, null, null);
            set v_error = null;

            call _updateopportunityaccommodations(v_oppkey, 0, v_accoms, 0, 0, 0,  v_error /*output*/, 0);

            if (v_error is not null) then
                call _logdberror('_initopportunityaccommodations', v_error, null, null, null, v_oppkey, null, null);
                call _returnerror(v_clientname, '_initopportunityaccommodations', 'accommodations update failed', null, v_oppkey, null, null);
                leave proc;
            end if;
        end;
		end if;
    end;
	end if;
    
    call _logdblatency('_initopportunityaccommodations', v_starttime, v_testee, null, null, v_oppkey, null, v_clientname, null);

end $$

DELIMITER ;