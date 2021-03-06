/* Add Column */
drop procedure if exists configs.addColumnUnlessExists;

delimiter $$

create procedure configs.addColumnUnlessExists()
begin
	IF NOT EXISTS (select * from information_schema.columns where TABLE_SCHEMA = 'configs' and table_name = 'client_testtooltype' and column_name = 'IsEntryControl') then
		ALTER TABLE configs.client_testtooltype ADD COLUMN IsEntryControl BIT NOT NULL DEFAULT b'0';
	END IF;
end $$

delimiter ;

call configs.addColumnUnlessExists();

drop procedure configs.addColumnUnlessExists;
/***/


INSERT IGNORE INTO configs.client_testtooltype (clientname, toolname, allowchange, tideselectable, rtsfieldname, isrequired, tideselectablebysubject, isselectable, isvisible, studentcontrol, tooldescription, sortorder, dateentered, origin, source, contexttype, context, dependsontooltype, disableonguestsession, isfunctional, testmode, isentrycontrol) VALUES ('SBAC', 'Other', b'0', b'0', 'TDSAcc-Other', b'0', b'0', b'0', b'0', b'0', NULL, 0, '2015-04-22 00:00:00.000', 'OSS', 'OSS', 'TEST', '*', NULL, b'1', b'1', 'ALL', b'1');

INSERT IGNORE INTO configs.client_testtool (clientname, type, code, value, isdefault, allowcombine, valuedescription, context, sortorder, origin, source, contexttype, testmode, equivalentclientcode) VALUES ('SBAC', 'Other', 'TDS_Other', 'None', b'1', b'0', 'Other', '*', 0, 'OSS', 'OSS', 'TEST', 'ALL', NULL);

INSERT IGNORE INTO configs.client_testtooltype (clientname, toolname, allowchange, tideselectable, rtsfieldname, isrequired, tideselectablebysubject, isselectable, isvisible, studentcontrol, tooldescription, sortorder, dateentered, origin, source, contexttype, context, dependsontooltype, disableonguestsession, isfunctional, testmode, isentrycontrol) VALUES ('SBAC_PT', 'Other', b'0', b'0', 'TDSAcc-Other', b'0', b'0', b'0', b'0', b'0', NULL, 0, '2015-04-22 00:00:00.000', 'OSS', 'OSS', 'TEST', '*', NULL, b'1', b'1', 'ALL', b'1');

INSERT IGNORE INTO configs.client_testtool (clientname, type, code, value, isdefault, allowcombine, valuedescription, context, sortorder, origin, source, contexttype, testmode, equivalentclientcode) VALUES ('SBAC_PT', 'Other', 'TDS_Other', 'None', b'1', b'0', 'Other', '*', 0, 'OSS', 'OSS', 'TEST', 'ALL', NULL);

call configs._dm_clearaccomcache;

call configs.__slugtestaccoms(null);