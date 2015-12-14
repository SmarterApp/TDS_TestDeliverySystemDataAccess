SET @@global.event_scheduler = ON;
create event if not exists closeabondonedsessions 
ON SCHEDULE  every 1 minute STARTS CURRENT_TIMESTAMP
do call session.__closeabandonedsessions();