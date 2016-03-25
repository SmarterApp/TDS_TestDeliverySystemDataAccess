DELETE FROM session.r_studentpackage WHERE iscurrent=0;

ALTER IGNORE TABLE session.r_studentpackage
	DROP INDEX ix_r_studentpackage_skey_client_currt,
	ADD UNIQUE INDEX ix_r_studentpackage_studentkey_clientname (studentkey, clientname);

DELETE FROM session.r_proctorpackage WHERE iscurrent=0;

ALTER IGNORE TABLE session.r_proctorpackage
	DROP INDEX ix_r_proctorpackage_proctorkey,
	ADD UNIQUE INDEX ix_r_proctorpackage_proctorkey_clientname (proctorkey, clientname);
