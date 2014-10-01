drop view if exists externs;

create view externs 
/*
VERSION 	DATE 			AUTHOR 			COMMENTS
001			11/1/2013		Sai V. 			Converted code from SQL Server to MySQL
*/
as 
select distinct x.clientname
            , testeedb
            , proctordb
            , 'session'/*'${SessionDBName}'*/ as sessiondb
            -- , db_name() as sessiondb
            , testeetype
            , proctortype
            , clientstylepath
            , x.environment
            , ispracticetest
            , (select url
                        from configs/*${ConfigsDBName}*/.geo_clientapplication a
                        where a.clientname = x.clientname 
                                    and a.environment = x.environment 
                                    and a.servicetype = 'CHECKIN' and a.appname = 'STUDENT') as testeecheckin
            , (select url
                        from configs/*${ConfigsDBName}*/.geo_clientapplication a
                        where  a.clientname = x.clientname 
                                    and a.environment = x.environment 
                                    and a.servicetype = 'CHECKIN' and a.appname = 'PROCTOR') as proctorcheckin
            , timezoneoffset
            , publishurl
            , initialreportingid
            , initialsessionid
            , testdb
            , qabrokerguid
from _externs x
join configs/*${ConfigsDBName}*/.client_externs e
on e.clientname = x.clientname and e.environment = x.environment;

