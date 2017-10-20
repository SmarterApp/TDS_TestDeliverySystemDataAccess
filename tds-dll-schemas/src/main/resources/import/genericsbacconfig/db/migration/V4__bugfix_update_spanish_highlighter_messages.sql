/***********************************************************************************************************************
  File: V4__bugfix_update_spanish_highlighter_messages.sql

  Desc: Adds a translation for the "Remove Highlighter" menu option

***********************************************************************************************************************/

INSERT INTO configs.tds_coremessageobject (
	context,
	contexttype,
	messageid,
	ownerapp,
	appkey,
	message,
	paralabels,
	_key,
	fromclient,
	datealtered
) VALUES (
	'tds_content.js',
	'ClientSide',
	(
		SELECT MAX(messageid) + 1
		FROM (SELECT * FROM configs.tds_coremessageobject) as cmo
	),
	'Student',
	'TDSContentJS.Label.RemoveHighlight',
	'Remove Highlight',
	NULL,
	4046,
	NULL,
	NOW()
);

INSERT INTO configs.client_messagetranslation (
	_fk_coremessageobject,
	client,
	message,
	language,
	grade,
	subject,
	_key,
	datealtered
) VALUES (
	4046,
	'AIR',
	'Borrar resaltado',
	'ESN',
	'--ANY--',
	'--ANY--',
	UNHEX(REPLACE(UUID(),'-','')),
	NOW()
);

INSERT INTO configs.tds_coremessageuser (
	_fk_coremessageobject,
	systemid
) VALUES (
	4046,
	'Student'
);

DELETE FROM __appmessages;
DELETE FROM __appmessagecontexts;