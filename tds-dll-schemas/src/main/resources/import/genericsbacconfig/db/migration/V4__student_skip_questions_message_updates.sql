use configs;

UPDATE tds_coremessageobject
SET message = 'You have responded to all questions. You may review and change any answer. When you are ready to finish the test, click the [End Test] button.'
WHERE ownerapp = 'Student'
AND appkey = 'TestCompleted';

UPDATE tds_coremessageobject
SET message = 'You have reached the last question of this test.  When you have finished checking your answers, click the [End Test] button.'
WHERE ownerapp = 'Student'
AND appkey = 'NextTestFinished';

UPDATE tds_coremessageobject
SET message = 'You are preparing to submit your test. You can review your answers by selecting the question number below.'
WHERE ownerapp = 'Student'
AND appkey = 'TestItemScores';

UPDATE tds_coremessageobject
SET message = 'You are preparing to submit your test. You can review your answers by selecting the question number below.'
WHERE ownerapp = 'Student'
AND appkey = 'StaticContent.Label.ReviewSubmit';


UPDATE tds_coremessageobject
SET message = 'You are preparing to submit your test.'
WHERE ownerapp = 'Student'
AND appkey = 'StaticContent.Label.Congratulations';

UPDATE client_messagetranslation
SET message = 'You are preparing to submit your test.'
WHERE language = 'ENU'
AND _fk_coremessageobject in (
    SELECT _key from tds_coremessageobject
    WHERE ownerapp = 'Student'
    AND appkey = 'StaticContent.Label.Congratulations'
);


UPDATE tds_coremessageobject
SET message = 'You are preparing to submit your test.'
WHERE ownerapp = 'Student'
AND appkey = 'Sections.TopHeader.TestReview';

UPDATE client_messagetranslation
SET message = 'You are preparing to submit your test.'
WHERE language = 'ENU'
AND _fk_coremessageobject in (
    SELECT _key from tds_coremessageobject
    WHERE ownerapp = 'Student'
    AND appkey = 'Sections.TopHeader.TestReview'
);


UPDATE tds_coremessageobject
SET message = 'You can review your answers by selecting the question number below.'
WHERE ownerapp = 'Student'
AND appkey = 'Sections.TopInstructions.TestReview';

UPDATE client_messagetranslation
SET message = 'You can review your answers by selecting the question number below.'
WHERE language = 'ENU'
AND _fk_coremessageobject in (
    SELECT _key from tds_coremessageobject
    WHERE ownerapp = 'Student'
    AND appkey = 'Sections.TopInstructions.TestReview'
);


UPDATE tds_coremessageobject
SET message = 'questions are marked for review.'
WHERE ownerapp = 'Student'
AND appkey = 'QuestionsAreMarkedForReview';


DELETE FROM tds_coremessageobject
WHERE ownerapp = 'Student'
AND appkey = 'QuestionsAreUnanswered';

INSERT INTO tds_coremessageobject (
    context,
    contexttype,
    messageid,
    ownerapp,
    appkey,
    message)
VALUES (
    'TestReview.aspx',
    'ServerSide',
    12236,
    'Student',
    'QuestionsAreUnanswered',
    'questions are unanswered.'); 


/** Below Delete is to delete cache table and its context, once user login it will populate the cache table from tds_coremessageobject,tds_coremessageuser,client_messagetranslation table **/
DELETE FROM __appmessages;
DELETE FROM __appmessagecontexts;
