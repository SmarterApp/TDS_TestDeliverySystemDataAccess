# Add the responseDurationInSecs column to testee_response table
ALTER TABLE session.testeeresponse ADD responsedurationinsecs FLOAT DEFAULT 0;
