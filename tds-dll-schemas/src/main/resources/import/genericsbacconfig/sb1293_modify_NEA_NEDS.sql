UPDATE configs.client_testtool SET code='NEDS_NoiseBuf' WHERE type='Non-Embedded Designated Supports' AND code='NEA_NoiseBuf';

UPDATE configs.client_tooldependencies SET thenvalue='NEDS_NoiseBuf' WHERE thentype='Non-Embedded Designated Supports' AND thenvalue='NEA_NoiseBuf';

DELETE FROM  configs.client_testtool WHERE type='Non-Embedded Accommodations' AND code='NEA_NoiseBuf';

DELETE FROM  configs.client_tooldependencies WHERE thentype='Non-Embedded Accommodations' AND thenvalue='NEA_NoiseBuf';

DELETE FROM  configs.client_testtool WHERE code='NEDS_RA_Stimuli';

DELETE FROM  configs.client_tooldependencies WHERE thenvalue='NEDS_RA_Stimuli';
