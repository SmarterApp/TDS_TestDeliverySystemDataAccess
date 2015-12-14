UPDATE `configs`.`client_messagetranslation` SET `message`='State SSID:<p style=\\\"font-size:0.6em;\\\">(ex: 9999999123)</p>' 
WHERE `_key`=0x45DFC70CB5F74C20955DD998C9F20C77;

UPDATE `configs`.`client_messagetranslation` SET `message`=' <p><strong>For Students:</strong></p><p></p> <ol><li>Uncheck the Guest User and Guest Session checkboxes.</li><li>Enter your SSID into the State SSID box.</li><li>Enter your first name into the First Name box.</li><li>Enter the Session ID that your TA gave you. </li><li>Click [Sign In].</li> </ol></p> <p><strong>Guest Users:</strong></p><p> To log in to the Practice and Training Tests, simply select [Sign In], then navigate through the login screens. </p>' 
WHERE `_key`=0xD548B5BAA0174F4681673C08B909B2A1;


-- update cache tables
call _dm_clearaccomcache();
call __slugtestaccoms(null);