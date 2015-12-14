update configs.client_messagetranslation  T, configs.tds_coremessageobject O
 set T.message = '<span style="display:none; lang="es-mx">Spanish text to be spoken</span>'
where T._fk_coremessageobject = O._key and O.appkey='TTSCheck.Label.HearMessageSpanish' 
and T.language = 'ENU' and client <> 'AIR';
update configs.client_messagetranslation  T, configs.tds_coremessageobject O
 set T.message = '<span style="display:none; lang="en-us">This is some sample text to test your settings.</span>'
where T._fk_coremessageobject = O._key and O.appkey='TTSCheck.Label.HearMessageEnglish' 
and T.language = 'ENU' and client <> 'AIR';

update configs.__appmessages set message = '<span style="display:none; lang="es-mx">Spanish text to be spoken</span>'
where appkey='TTSCheck.Label.HearMessageSpanish';
update configs.__appmessages set message = '<span style="display:none; lang="en-us">This is some sample text to test your settings.</span>' 
where appkey='TTSCheck.Label.HearMessageEnglish';

