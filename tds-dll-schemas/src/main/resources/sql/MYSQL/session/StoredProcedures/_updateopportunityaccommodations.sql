DELIMITER $$

drop procedure if exists `_updateopportunityaccommodations` $$

create procedure `_updateopportunityaccommodations`(
/*
DESCRIPTION:

VERSION 	DATE 			AUTHOR 			COMMENTS
001			2/10/2015		Sai V. 			
*/
  	v_oppkey 	varbinary(16)
  , v_segment 	int
  , v_accoms 	text
  , v_isstarted int 
  , v_approved bit -- = 1
  , v_restorerts bit -- = 0
  , out v_error varchar(300)
  , v_debug int -- = 0
)
sql security invoker
proc: begin

	declare v_starttime datetime(3);
	declare v_clientname varchar(100);
	declare v_testkey varchar(200);
	declare v_testid varchar(200);
	declare v_custom bit;

	declare exit handler for sqlexception
	begin
		rollback;
		set v_error = 'mysql exit handler: error setting accommodations';
		call _logdberror('_updateopportunityaccommodations', v_error, null, null, null, v_oppkey, null, null);
		call _logdblatency('_updateopportunityaccommodations', v_starttime, null, 1, null, v_oppkey, null, null, null);
	end;

	set v_starttime = now(3);
	set v_error = null;

    select clientname, _efk_adminsubject, _efk_testid, customaccommodations
	into v_clientname, v_testkey, v_testid, v_custom
    from testopportunity where _key = v_oppkey;

	drop temporary table if exists tblout_splitaccomcodes;
	create temporary table tblout_splitaccomcodes(
		idx 	int auto_increment primary key
	  , `code`  varchar(50)
	);
	
	if (v_debug > 0) then select '_splitaccomcodes', v_clientname, v_testkey, v_accoms; end if;
	call _splitaccomcodes(v_clientname, v_testkey, v_accoms);

    if (v_debug <> 0) then
        select v_segment as segment;
        select * from tblout_splitaccomcodes;
--         select acctype, acccode, accvalue, allowchange, studentcontrol, isdefault, isselectable, valcount
--         from dbo.testkeyaccommodations(v_testkey) c, tblout_splitaccomcodes s
--         where s.`code` = c.acccode and cast(segment as unsigned) = v_segment;
    end if;

	drop temporary table if exists tblout_testlanguages;
	create temporary table tblout_testlanguages (
		`code`	varchar(150)
	  , label	varchar(150)
	);

	call itembank.testlanguages(v_testkey);

	drop temporary table if exists tmp_tblaccoms;
    create temporary table tmp_tblaccoms(atype varchar(50), acode varchar(100), avalue varchar(250), allow bit, control bit, recordusage bit, isdefault bit, isselectable bit, valcount int);

    insert into tmp_tblaccoms (atype, acode, avalue, allow, control, isdefault, isselectable, valcount, recordusage)
	select distinct acctype, acccode, accvalue, allowchange, studentcontrol, isdefault, isselectable, valcount
         , coalesce((select 1 from configs.client_toolusage 
					  where clientname = v_clientname and testid = v_testid and tooltype = acctype and (recordusage = 1 or reportusage = 1)), 0)
    from -- dbo.testkeyaccommodations(v_testkey) c
			( -- first, get all tds tools directly related to this client's tests, 0 represents the global test scope, while segment positions are local segment score
				select distinct 0 as segment,ttype.disableonguestsession, ttype.sortorder as tooltypesortorder, tt.sortorder as toolvaluesortorder, ttype.testmode as typemode
					, tt.testmode as toolmode, `type` as acctype, `value` as accvalue, `code` as acccode, isdefault, allowcombine
					, isfunctional, allowchange, isselectable, isvisible, studentcontrol
					, (select count(*) from configs.client_testtool tool 
						where tool.contexttype = 'test' and tool.`context` = md.testid and tool.clientname = md.clientname and tool.`type` = tt.`type`) as valcount
					, dependsontooltype
					from configs.client_testtooltype ttype, configs.client_testtool tt, configs.client_testmode md
					where md.testkey = v_testkey and
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
					where parenttest = md.testid and md.testkey = v_testkey and seg.modekey = v_testkey
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
					where md.testkey = v_testkey and ttype.contexttype = 'test' and ttype.`context` = '*' and ttype.clientname = md.clientname
						and tt.contexttype = 'test' and tt.`context` = '*' and tt.clientname = md.clientname and tt.`type` = ttype.toolname 
						and (ttype.testmode = 'all' or ttype.testmode = md.`mode`) and (tt.testmode = 'all' or tt.testmode = md.`mode`)
						and not exists (select * from configs.client_testtooltype tool 
										 where tool.contexttype = 'test' and tool.`context` = md.testid and tool.toolname = ttype.toolname and tool.clientname = md.clientname)
			) c
		, tblout_splitaccomcodes s
	where s.`code` = c.acccode and segment = v_segment;

    if (v_debug <> 0) then 
		select * from tmp_tblaccoms;
	end if;

    if (v_isstarted <> 0) then
        delete from tmp_tblaccoms where allow = 0;
    end if;

	-- the following case is to satisfy a condition for updating accommodations.
	-- if the caller sent accommodations to restore, then we only restore those that are not selectable
    if (v_restorerts = 1) then
        delete from tmp_tblaccoms where isselectable = 1;
	end if;

    if (exists (select * from tmp_tblaccoms where isdefault = 0)) then
        set v_custom = 1;
	end if;
    

	start transaction;
		-- note: isapproved added to testeeaccommodations table because of segments: the defaults are created when the test is opened, but may require approval later
		delete from testeeaccommodations 
		where _fk_testopportunity = v_oppkey 
			and acctype in (select distinct atype from tmp_tblaccoms) and segment = v_segment;

		insert into testeeaccommodations (_fk_testopportunity, acctype, acccode, accvalue, _date, allowchange, recordusage, testeecontrol, segment, valuecount, isapproved, isselectable)
		select distinct v_oppkey, atype, acode, avalue, now(3), allow, recordusage, control, v_segment, valcount, case valcount when 1 then 1 else v_approved end, isselectable
		from tmp_tblaccoms;

		if (exists (select * from tmp_tblaccoms where atype = 'language')) then
			update testopportunity, tmp_tblaccoms
			set `language` = avalue
			  , customaccommodations = v_custom 
			where atype = 'language' and _key = v_oppkey; 
		else 
			update testopportunity 
			set customaccommodations = v_custom 
			where _key = v_oppkey;
		end if;
	commit;

   
	drop temporary table if exists tmp_tbldeps;
    create temporary table tmp_tbldeps(atype varchar(50), aval varchar(50), acode varchar(50), del bit);   

    -- collect all accommodations with dependencies
    insert into tmp_tbldeps (atype, aval, acode, del)
    select acctype, accvalue, acccode, 0 
	from testeeaccommodations a
    where _fk_testopportunity= v_oppkey
		and exists (select * from configs.client_tooldependencies d
					 where d.contexttype = 'test' and d.`context` = v_testid and d.clientname = v_clientname
					   and a.acctype = d.thentype and a.acccode = d.thenvalue);

    -- flag those whose dependencies are not met by the existence of an accommodation type and value on which they depend

    update tmp_tbldeps 
	set del = 1
    where not exists (select * from testeeaccommodations b, configs.client_tooldependencies d
					   where _fk_testopportunity = v_oppkey 
						 and d.contexttype = 'test' and d.`context` = v_testid and d.clientname = v_clientname
						 and d.thentype = atype and d.thenvalue = acode
						 and b.acctype = d.iftype and b.acccode = d.ifvalue);

    -- delete the accommodations whose dependencies are not met
    if (exists (select * from tmp_tbldeps where del = 1)) then
        delete from testeeaccommodations
        where _fk_testopportunity = v_oppkey
			and exists (select * from tmp_tbldeps where del = 1 and acctype = atype and acccode = acode);
	end if;

    update testopportunity_readonly 
	set accommodationstring = p_formataccommodations(v_oppkey)
    where _fk_testopportunity = v_oppkey;

	-- clean-up
    drop temporary table tmp_tblaccoms;
	drop temporary table tmp_tbldeps;
	drop temporary table tblout_splitaccomcodes;

    call _logdblatency('_updateopportunityaccommodations', v_starttime, null, 1, null, v_oppkey, null, null, null);
	
end $$

DELIMITER ;
