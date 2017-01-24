update configs.tds_coremessageobject set message = replace(message, '/Projects/AIR/', '/Projects/SBAC/') where message like '%/Projects/%';
update configs.client_messagetranslation set message = replace(message, '/Projects/Delaware/', '/Projects/SBAC/') where message like '%/Projects/Delaware/%';
update configs.client_messagetranslation set message = replace(message, '/Projects/Hawaii/', '/Projects/SBAC/') where message like '%/Projects/Hawaii/%';
update configs.client_messagetranslation set message = replace(message, '/Projects/AIR/', '/Projects/SBAC/') where message like '%/Projects/AIR/%';