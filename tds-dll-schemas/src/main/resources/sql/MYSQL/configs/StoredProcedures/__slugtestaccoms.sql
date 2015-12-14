DELIMITER $$

drop procedure if exists `__slugtestaccoms` $$

create procedure `__slugtestaccoms`(
/*
Description: a background process that precomputes accommodations for all tests that have been registered in the __accommodationcache and lack a recordset in __cachedaccommodations

VERSION 	DATE 			AUTHOR 			COMMENTS
001			10/21/2014		Sai V. 			Ported code from SQL Server to MySQL
*/
	v_contextkey bigint
)
sql security invoker
proc: begin

    declare v_client varchar(100);
	declare v_testkey varchar(250);  
    declare v_segmented bit; 
	declare v_algorithm varchar(50);

    if (v_contextkey is null) then
	begin
        select _key into v_contextkey
		  from __accommodationcache 
		 where dategenerated is null 
		 order by _key
		 limit 1;
        
		if (v_contextkey is null) then leave proc; end if;
    end;
	end if;

	drop temporary table if exists tmp_tblresult;
	create temporary table tmp_tblresult (
		`code` varchar(100)
	  , label  varchar(200)
	) engine = Memory;		
    

    while (v_contextkey is not null) do
	begin

        select clientname, testkey into v_client, v_testkey
		  from __accommodationcache where _key = v_contextkey;
        
		-- get test languages
		select issegmented, selectionalgorithm into v_segmented, v_algorithm
		  from itembank.tblsetofadminsubjects
		 where _key = v_testkey;

		if (v_segmented = 0) then 
		begin
			if (v_algorithm = 'fixedform') then 
			begin
				insert into tmp_tblresult (`code`, label)
				select distinct  propvalue, propdescription
				  from itembank.tblitemprops p, itembank.testform f
				 where p._fk_adminsubject = v_testkey and propname = 'language' and f._fk_adminsubject = v_testkey and f.`language` = p.propvalue
				   and p.isactive = 1;
			end;
			else 
			begin
				insert into tmp_tblresult (`code`, label)
				select distinct  propvalue, propdescription
				  from itembank.tblitemprops p
				 where p._fk_adminsubject = v_testkey and propname = 'language' and isactive = 1;
			end;
			end if;
		end;
		else 
		begin
			insert into tmp_tblresult (`code`, label)
			select distinct propvalue, propdescription
			  from itembank.tblsetofadminitems a, itembank.tblitemprops p, itembank.tblsetofadminsubjects s
			 where s.virtualtest = v_testkey and a._fk_adminsubject = s._key and a._fk_adminsubject = p._fk_adminsubject 
			   and a._fk_item = p._fk_item and propname = 'language' and p.isactive = 1;
		end;
		end if;
	

		insert into __cachedaccommodations(_fk_accommodationcache, segment, disableonguestsession, tooltypesortorder, toolvaluesortorder, typemode, toolmode, acctype, accvalue, acccode, isdefault, allowcombine, isfunctional, allowchange, isselectable, isvisible, studentcontrol, valcount, dependsontooltype) 
		select v_contextkey, segment, disableonguestsession, tooltypesortorder, toolvaluesortorder, typemode, toolmode, acctype, accvalue, acccode, isdefault, allowcombine, isfunctional, allowchange, isselectable, isvisible, studentcontrol, valcount, dependsontooltype
		  from (
			-- first, get all tds tools directly related to this client's tests, 0 represents the global test scope, while segment positions are local segment score
			select distinct 0 as segment,ttype.disableonguestsession, ttype.sortorder as tooltypesortorder, tt.sortorder as toolvaluesortorder, ttype.testmode
				 as typemode, tt.testmode as toolmode, type as acctype, value as accvalue, code as acccode, isdefault, allowcombine
				, isfunctional, allowchange, isselectable, isvisible, studentcontrol,
				(select count(*) from client_testtool tool 
					where tool.contexttype = 'test' and tool.context = md.testid  and tool.clientname = md.clientname and tool.type = tt.type) as valcount
				, dependsontooltype
				from client_testtooltype ttype, client_testtool tt, client_testmode md
				where md.testkey = v_testkey
					and ttype.contexttype = 'test' and ttype.context = md.testid and ttype.clientname = md.clientname
					and tt.contexttype = 'test' and tt.context = md.testid and tt.clientname = md.clientname and tt.type = ttype.toolname
					and (tt.type <> 'language' or tt.code in (select `code` from tmp_tblresult))
					and (ttype.testmode = 'all' or ttype.testmode = md.mode) and (tt.testmode = 'all' or tt.testmode = md.mode)
			-- get all the segment-specific accommodations
			union all
			select distinct segmentposition ,ttype.disableonguestsession, ttype.sortorder , tt.sortorder, ttype.testmode , tt.testmode 
				, type , value , code , isdefault, allowcombine, isfunctional, allowchange
				, isselectable, isvisible, studentcontrol,
				(select count(*) from client_testtool tool 
					where tool.contexttype = 'test' and tool.context = md.testid and tool.clientname = md.clientname and tool.type = tt.type) as valcount
				, null  -- dependsontooltype
				from client_testtooltype ttype, client_testtool tt, client_segmentproperties seg, client_testmode md
				where parenttest = md.testid and md.testkey = v_testkey and seg.modekey = v_testkey
					and ttype.contexttype = 'segment' and ttype.context = segmentid and ttype.clientname = md.clientname
					and tt.contexttype = 'segment' and tt.context = segmentid and tt.clientname = md.clientname and tt.type = ttype.toolname
					and (ttype.testmode = 'all' or ttype.testmode = md.mode) and (tt.testmode = 'all' or tt.testmode = md.mode)
			-- now get all test tools that have 'wild card' (i.e. '*') assignments (segments and languages never have wildcard assignments)
			union all  
				select distinct 0,ttype.disableonguestsession,  ttype.sortorder , tt.sortorder, ttype.testmode , tt.testmode 
				, type , value , code , isdefault, allowcombine
				, isfunctional, allowchange, isselectable, isvisible, studentcontrol,
				(select count(*) from client_testtool tool 
					where tool.contexttype = 'test' and tool.context = '*' and tool.clientname = md.clientname and tool.type = tt.type) as valcount
				, dependsontooltype
				from  client_testtooltype ttype, client_testtool tt, client_testmode md
				where md.testkey = v_testkey and ttype.contexttype = 'test' and ttype.context = '*' and ttype.clientname = md.clientname
					and tt.contexttype = 'test' and tt.context = '*' and tt.clientname = md.clientname and tt.type = ttype.toolname 
					and (ttype.testmode = 'all' or ttype.testmode = md.mode) and (tt.testmode = 'all' or tt.testmode = md.mode)
					and not exists
							(select * from client_testtooltype tool 
							where tool.contexttype = 'test' and tool.context = md.testid and tool.toolname = ttype.toolname and tool.clientname = md.clientname)    
		) t
		where not exists (select * from __cachedaccommodations where _fk_accommodationcache = v_contextkey);



		insert into __cachedaccomdepends(_fk_accommodationcache, contexttype, `context`, testmode, iftype, ifvalue, thentype, thenvalue, isdefault) 
		select v_contextkey, contexttype, `context`, testmode, iftype, ifvalue, thentype, thenvalue, isdefault
		  from (
			-- tool dependencies
			select distinct m.clientname, m.testkey, contexttype, m.testid as `context`, testmode, iftype, ifvalue, thentype, thenvalue, isdefault
			  from client_tooldependencies td, client_testmode m
			 where m.testkey = v_testkey and td.contexttype = 'test' and td.context = m.testid and td.clientname = m.clientname 
			union all
			select distinct m.clientname, m.testkey, contexttype, m.testid as context, testmode, iftype, ifvalue, thentype, thenvalue, isdefault
			  from client_tooldependencies td, client_testmode m
			 where m.testkey = v_testkey and td.clientname = m.clientname and contexttype = 'test' and context='*'
			   and (td.testmode = 'all' or td.testmode = m.mode)
			   and not exists (select * from client_tooldependencies td2
								where td2.contexttype = 'test' and td2.context = m.testid and td2.clientname = m.clientname
								  and td.iftype = td2.iftype and td.ifvalue = td2.ifvalue and td.thentype = td2.thentype and td.thenvalue = td2.thenvalue)
		) t
		where not exists (select * from __cachedaccomdepends where _fk_accommodationcache = v_contextkey);


        update __accommodationcache 
		   set dategenerated = now(3) where _key = v_contextkey;

		-- re-set data for next iteration	
		set v_contextkey = null;
		set v_segmented = null; 
		set v_algorithm = null;
		
		delete from tmp_tblresult;

        select _key into v_contextkey 
		  from __accommodationcache where dategenerated is null order by _key limit 1;

    end;  
	end while;


	drop temporary table if exists tmp_tblresult;

end$$

DELIMITER ;


