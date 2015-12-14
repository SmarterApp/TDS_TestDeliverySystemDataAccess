UPDATE configs.client_testtooltype SET isvisible=b'1', isfunctional=b'0' WHERE toolname='Other';

call configs._dm_clearaccomcache;
call configs.__slugtestaccoms(null);
