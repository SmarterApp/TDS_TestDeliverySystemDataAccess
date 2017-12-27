use configs;

UPDATE tds_coremessageobject
SET message = 'You have responded to all questions. You may review and change any answer. When you are ready to finish the test, click the [End Test] button.'
WHERE ownerapp = 'Student'
AND appkey = 'TestCompleted';

/** Below Delete is to delete cache table and its context, once user login it will populate the cache table from tds_coremessageobject,tds_coremessageuser,client_messagetranslation table **/
DELETE FROM __appmessages;
DELETE FROM __appmessagecontexts;
