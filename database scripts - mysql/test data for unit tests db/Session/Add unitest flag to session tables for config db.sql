-- =============================================
-- add column with column name 'unittestflag' to all tables
-- in tdscore_test_session. DB  on MySQL
-- =============================================

alter table tdscore_test_session._anonymoustestee
        add unittestflag int null;

alter table tdscore_test_session._externs
        add unittestflag int null;

alter table tdscore_test_session._maxtestopps
        add unittestflag int null;

alter table tdscore_test_session._missingmessages
        add unittestflag int null;

alter table tdscore_test_session._sb_errorlog
        add unittestflag int null;

alter table tdscore_test_session._sb_messagehandler
        add unittestflag int null;

alter table tdscore_test_session._sb_messages
        add unittestflag int null;

alter table tdscore_test_session._sb_messagesarchive
        add unittestflag int null;

alter table tdscore_test_session._sitelatency
        add unittestflag int null;

alter table tdscore_test_session._synonyms
        add unittestflag int null;

alter table tdscore_test_session.adminevent
        add unittestflag int null;

alter table tdscore_test_session.admineventitems
        add unittestflag int null;

alter table tdscore_test_session.admineventopportunities
        add unittestflag int null;

alter table tdscore_test_session.alertmessages
        add unittestflag int null;

alter table tdscore_test_session.client_os
        add unittestflag int null;

alter table tdscore_test_session.client_reportingid
        add unittestflag int null;

alter table tdscore_test_session.client_sessionid
        add unittestflag int null;

alter table tdscore_test_session.clientlatency
        add unittestflag int null;

alter table tdscore_test_session.clientlatencyarchive
        add unittestflag int null;

alter table tdscore_test_session.ft_groupsamples
        add unittestflag int null;

alter table tdscore_test_session.ft_opportunityitem
        add unittestflag int null;

alter table tdscore_test_session.geo_clientsystem
        add unittestflag int null;

alter table tdscore_test_session.geo_session
        add unittestflag int null;

alter table tdscore_test_session.itemdistribution
        add unittestflag int null;

alter table tdscore_test_session.loadtest_testee
        add unittestflag int null;

alter table tdscore_test_session.qc_validationexception
        add unittestflag int null;

alter table tdscore_test_session.r_abnormallogins
        add unittestflag int null;

alter table tdscore_test_session.r_blueprintreport
        add unittestflag int null;

alter table tdscore_test_session.r_geolatencyreport
        add unittestflag int null;

alter table tdscore_test_session.r_hourlygeolatencytable
        add unittestflag int null;

alter table tdscore_test_session.r_hourlyusers
        add unittestflag int null;

alter table tdscore_test_session.r_participationcountstable
        add unittestflag int null;

alter table tdscore_test_session.r_proctorkeyid
        add unittestflag int null;

alter table tdscore_test_session.r_proctorpackage
        add unittestflag int null;

alter table tdscore_test_session.r_schoolparticipationreport
        add unittestflag int null;

alter table tdscore_test_session.r_studentkeyid
        add unittestflag int null;

alter table tdscore_test_session.r_studentpackage
        add unittestflag int null;

alter table tdscore_test_session.r_testcounts
        add unittestflag int null;

alter table tdscore_test_session.rtsschoolgrades
        add unittestflag int null;

alter table tdscore_test_session.session
        add unittestflag int null;

alter table tdscore_test_session.sessiontests
        add unittestflag int null;

alter table tdscore_test_session.setofproctoralertmessages
        add unittestflag int null;

alter table tdscore_test_session.sim_itemgroup
        add unittestflag int null;

alter table tdscore_test_session.sim_segment
        add unittestflag int null;

alter table tdscore_test_session.sim_segmentcontentlevel
        add unittestflag int null;

alter table tdscore_test_session.sim_segmentitem
        add unittestflag int null;

alter table tdscore_test_session.sim_user
        add unittestflag int null;

alter table tdscore_test_session.sim_userclient
        add unittestflag int null;

alter table tdscore_test_session.simp_itemgroup
        add unittestflag int null;

alter table tdscore_test_session.simp_segment
        add unittestflag int null;

alter table tdscore_test_session.simp_segmentcontentlevel
        add unittestflag int null;

alter table tdscore_test_session.simp_segmentitem
        add unittestflag int null;

alter table tdscore_test_session.simp_session
        add unittestflag int null;

alter table tdscore_test_session.simp_sessiontests
        add unittestflag int null;

alter table tdscore_test_session.sirve_audit
        add unittestflag int null;

alter table tdscore_test_session.sirve_session
        add unittestflag int null;

alter table tdscore_test_session.sysdiagrams
        add unittestflag int null;

alter table tdscore_test_session.tblclsclientsessionstatus
        add unittestflag int null;

alter table tdscore_test_session.testeeaccommodations
        add unittestflag int null;

alter table tdscore_test_session.testeeattribute
        add unittestflag int null;

alter table tdscore_test_session.testeecomment
        add unittestflag int null;

alter table tdscore_test_session.testeehistory
        add unittestflag int null;

alter table tdscore_test_session.testeeitemhistory
        add unittestflag int null;

alter table tdscore_test_session.testeerelationship
        add unittestflag int null;

alter table tdscore_test_session.testeeresponse
        add unittestflag int null;

alter table tdscore_test_session.testeeresponsearchive
        add unittestflag int null;

alter table tdscore_test_session.testeeresponseaudit
        add unittestflag int null;

alter table tdscore_test_session.testeeresponsescore
        add unittestflag int null;

alter table tdscore_test_session.testoppabilityestimate
        add unittestflag int null;

alter table tdscore_test_session.testopportunity
        add unittestflag int null;

alter table tdscore_test_session.testopportunity_readonly
        add unittestflag int null;

alter table tdscore_test_session.testopportunitycontentcounts
        add unittestflag int null;

alter table tdscore_test_session.testopportunityscores
        add unittestflag int null;

alter table tdscore_test_session.testopportunitysegment
        add unittestflag int null;

alter table tdscore_test_session.testopportunitysegmentcounts
        add unittestflag int null;

alter table tdscore_test_session.testopprequest
        add unittestflag int null;

alter table tdscore_test_session.testopptoolsused
        add unittestflag int null;

