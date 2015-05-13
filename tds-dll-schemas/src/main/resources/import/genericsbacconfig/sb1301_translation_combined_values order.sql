UPDATE configs.client_testtool SET code = CONCAT(TRIM(SUBSTRING_INDEX(code, '&', -1)), '&TDS_WL_Glossary'), 
  value = CONCAT(TRIM(SUBSTRING_INDEX(SUBSTRING_INDEX(value, '&', -1), 'Glossary', 1)), ' & English Glossary')
  WHERE TYPE = 'Word List' AND code LIKE 'TDS_WL_Glossary&%';

UPDATE configs.client_tooldependencies SET thenvalue = CONCAT(TRIM(SUBSTRING_INDEX(thenvalue, '&', -1)), '&TDS_WL_Glossary')
  WHERE thentype = 'Word List' AND thenvalue LIKE 'TDS_WL_Glossary&%';
  
call configs._dm_clearaccomcache;
call configs.__slugtestaccoms(null);
