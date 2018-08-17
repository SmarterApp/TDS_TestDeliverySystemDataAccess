-- MySQL dump 10.13  Distrib 5.6.15, for Win64 (x86_64)
--
-- Host: localhost    Database: configs2
-- ------------------------------------------------------
-- Server version	5.6.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `statuscodes`
--

LOCK TABLES `statuscodes` WRITE;
/*!40000 ALTER TABLE `statuscodes` DISABLE KEYS */;
INSERT INTO `statuscodes` VALUES ('opportunity','suspended','an existing opportunity is waiting for proctor approval to restart','inuse','N\ZøzWI9Ñ ªµ~«$ë');
INSERT INTO `statuscodes` VALUES ('opportunity','scored','completed test has been scored','closed','∑/∑VNH´ô∫Q–„.');
INSERT INTO `statuscodes` VALUES ('opportunity','reported','test scores have been reported to reporting system','closed','%ít3•zI≤ôVÌ&ÛÑêπ');
INSERT INTO `statuscodes` VALUES ('session ','closed','the session is closed for business','closed','43å˛åAÁ≥∞«≥|?');
INSERT INTO `statuscodes` VALUES ('session','open','the session is open for business','open','=ßÀê!LˇÑŒ¯ﬂÜ/ˇ');
INSERT INTO `statuscodes` VALUES ('opportunity','approved','opportunity has been approved for start/restart by proctor','inuse','jVöè®PJ√£n4·ñ\rn ');
INSERT INTO `statuscodes` VALUES ('opportunity','paused','opportunity has been put on hold by the user','inactive','uR@YNH5ã7∆K6ÒsT');
INSERT INTO `statuscodes` VALUES ('opportunity','pending','a new opportunity is waiting for proctor approval','inuse','ì.sãrÉKjÄ˝.Iü[™®');
INSERT INTO `statuscodes` VALUES ('opportunity','expired','incomplete test has expired','closed','ó{¥≤G\'J#ö√°Øû3î');
INSERT INTO `statuscodes` VALUES ('opportunity','segmentExit','request to leave a segment','inuse','•˛Ì^¬C[Ωògz¥£…');
INSERT INTO `statuscodes` VALUES ('opportunity','denied','opportunity start was denied by the proctor','inactive','¶†\0∞›L6ú	0tVÙßk');
INSERT INTO `statuscodes` VALUES ('opportunity','segmentEntry','request to enter a segment','inuse','∞k^%1¡Cy∂∆€‹√#');
INSERT INTO `statuscodes` VALUES ('opportunity','review','testee is reviewing completed test items','inuse','µJº=£N,ñk3∆°™à');
INSERT INTO `statuscodes` VALUES ('opportunity','completed','test has been completed and submitted for scoring by testee','closed','∑Y\rÕ£GÔÖe¬hæ’“4');
INSERT INTO `statuscodes` VALUES ('opportunity','submitted','test scores have been submitted to QA','closed','ÃLRg≥H±ü;Ö¶\\dBç');
INSERT INTO `statuscodes` VALUES ('opportunity','rescored','the opportunity was rescored','closed','ŒózˆEHqúﬁøgvı');
INSERT INTO `statuscodes` VALUES ('opportunity','invalidated','test results have been invalidated','closed','ﬂg‚BÇˇ¬®G¸%');
INSERT INTO `statuscodes` VALUES ('opportunity','started','opportunity is in use','inuse','¯óO%#@\'§¥Ùô:Ò”˙');
/*!40000 ALTER TABLE `statuscodes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `system_applicationsettings`
--

LOCK TABLES `system_applicationsettings` WRITE;
/*!40000 ALTER TABLE `system_applicationsettings` DISABLE KEYS */;
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','Student','tds.itemRenderer.enableMathML','boolean','true','’•úèK	àﬂw◊∆†<™','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ResponseEntry','http.cache.prefetch','string','Enabled','\r˙q“”ÉIcà‹çk˚Få*','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ScoreEntry','tds.itemRenderer.enableMathML','boolean','true','⁄q˝J&¢! ë™âŒ;','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','Student','ItemScoring.Timer.Enabled','boolean','true','/âs˚ôF@ÄµmD Á','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ScoreEntry','http.cache.id','string','1','8tiVˇøMò™e∆!≈','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','Student','tds.itemRenderer.supportHttpRanges','boolean','true','`X&XCØºµïU‰|#','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ScoreEntry','ItemScoring.Enabled','boolean','true','`\\º„õH‚ì∞≤èúÖ†ô','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','Student','ItemScoring.Enabled','boolean','true','b/Z›øuK>Æ£≤Ã+7','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ScoreEntry','itemScoring.timer.intervalSecs','integer','300','cVøçRNì£dÊÖk@(','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ResponseEntry','itemScoring.timer.intervalSecs','integer','300','fƒÚHßﬁOÃä÷êF(Ù‡','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ResponseEntry','tds.itemRenderer.urlResolverVersion','integer','2','s¿ÏAØ€IQΩ<“”z∆πî','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ScoreEntry','ItemScoring.ServerUrl','string','https://tds2.airws.org/Test_ItemScoringEngine_2013/ItemScoring.axd','s˙∑&à L‹ù™?Öß','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ResponseEntry','ItemScoring.Timer.Enabled','boolean','true','ymπÄ`ëFFÉÌKè∑÷Ì','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ResponseEntry','tds.itemRenderer.enableMathML','boolean','true','zñ˘vN+öçã¥ÙÜI','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ScoreEntry','http.cache.prefetch','string','Enabled','ÄıP∑™FÖhº*Ú∞ôO','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ScoreEntry','http.manifest.mode','string','Disabled','Å¬PW\rA!ªÛ„¬ö°xp','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ScoreEntry','tds.itemRenderer.urlResolverVersion','integer','2','Å5!Z\ZÈOá∑†?0¯q','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','Student','http.cache.id','string','1','Ç;¿rΩwL¸å∏óó¥«⁄Ï','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ResponseEntry','http.cache.id','string','1','úx –ÓEΩ‰MZ%ÿˆ','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','Student','tds.itemRenderer.urlResolverVersion','integer','2','ú¢µ[ë@}ç¶<ùÕ≈\\r','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ResponseEntry','ItemScoring.ServerUrl','string','https://tds2.airws.org/Test_ItemScoringEngine_2013/ItemScoring.axd','†-Ä_·uIæÅÂo.ıx','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','Student','http.cache.prefetch','string','Enabled','ß∆\rŒ´J´ñﬂ÷â F','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ScoreEntry','tds.itemRenderer.supportHttpRanges','boolean','true','≠D.‹	BÄ>á∫oÂ','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','Student','ItemScoring.ServerUrl','string','https://tds2.airws.org/Test_ItemScoringEngine_2013/ItemScoring.axd','∂»ã⁄\rfDÖŸ7øU<','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ResponseEntry','ItemScoring.Enabled','boolean','true','∏KË[(FÈç˛_x_¶è','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ResponseEntry','tds.itemRenderer.supportHttpRanges','boolean','true','ª¬@≥!ôO-©î`4\"','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ScoreEntry','ItemScoring.Timer.Enabled','boolean','true','⁄^QîπºB¡∑GÚùf…•:','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ResponseEntry','http.manifest.mode','string','Disabled','Ê∑ÜäEM ïï‹B#f0','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','Student','itemScoring.timer.intervalSecs','integer','300','bÊÉ/uFé~\'T<t','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','Student','http.manifest.mode','string','Disabled','˙\0må¸*A+£Îòñw¿Ê[','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC_PT','Development','Student','tds.itemRenderer.urlResolverVersion','integer','2','-q¯ç_ÿC]£ÌW·ÃØLÁ','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC_PT','Development','Student','ItemScoring.Enabled','boolean','true','LàègiOCØTAz∑ä\'˜','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC_PT','Development','Student','http.cache.prefetch','string','Enabled','Zf˝\\¬IÛøüïÁ◊ÅA','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC_PT','Development','Student','ItemScoring.ServerUrl','string','https://tds2.airws.org/Test_ItemScoringEngine_2013/ItemScoring.axd','uÛ‘B›íKòVÉuw◊','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC_PT','Development','Student','tds.itemRenderer.enableMathML','boolean','true','u\ní»PO≥H∞ÿ9°√J','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC_PT','Development','Student','itemScoring.timer.intervalSecs','integer','300','u ä‹\0Lé†¢˝a◊J','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC_PT','Development','Student','http.manifest.mode','string','Disabled','ï[¶~é\'OÌõoca7Àn©','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC_PT','Development','Student','tds.itemRenderer.supportHttpRanges','boolean','true','úÏGDÒåÄGL+™g}','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC_PT','Development','Student','http.cache.id','string','1','ØDà“eCªËÙEµ¨ÿ`','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC_PT','Development','Student','ItemScoring.Timer.Enabled','boolean','true','«<˘B	iD\"°(i∫;Ï†','*','*');
/*!40000 ALTER TABLE `system_applicationsettings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `system_browserwhitelist`
--

LOCK TABLES `system_browserwhitelist` WRITE;
/*!40000 ALTER TABLE `system_browserwhitelist` DISABLE KEYS */;
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','IE','Windows','*',10,'Allow',1,99999,'f⁄N¥)KŸé´Çd’£m',10,0,'','*','GLOBAL','VœbãCK≤bLfåÑ„”');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Safari','OSX','*',99999,'Warn',1,99999,'*∏Ñ\\ M®œ<∆îæ',6.1,0,NULL,'*','GLOBAL','íHÓw±@Ê∂å§#èG¯a');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Chrome','Windows','*',28,'Allow',1,99999,'≥–πgRoOfá±ÕπA<€º',18,0,NULL,'*','GLOBAL','xBÇµé@+ªıò%ór');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Firefox','OSX','*',25,'Allow',1,99999,'c’ÓúÖöJNø(π˚Zü†d',3.6,0,'','*','GLOBAL','`Bãs\rCjû\rÂeI∆S');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','SIRVE','Development','*','*','*',99999,'Warn',0,99999,'ØÜ»c¿DHÒóà«£#B˙(',0,0,NULL,'*','GLOBAL','«eìÇFJ¢≥J∫');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Safari','Windows','*',99999,'Warn',1,99999,'¸aW\'KÒúÓ3N*¬î‹',6.1,0,NULL,'*','GLOBAL','æÑË§NgÇ7µ·=‹˝');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Chrome','OSX','*',28,'Allow',1,99999,'æ»ΩM·9Eo∫ô∏ät+Í',18,0,NULL,'*','GLOBAL','	£©\'L@2¥F!ô5â}');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Chrome','Android','*',99999,'Warn',1,4.2,'z‚q\\hNïâDº±z`ˆ',28.1,4,NULL,'*','GLOBAL','	‚_›e7CqôAÏôäUÓb');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Chrome','Linux','*',28,'Allow',1,99999,'“ÇV∂QE‹é„ÆˇÚ…z',18,0,NULL,'*','GLOBAL','\rùœ9=IÀÑèY¬ü#àú');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','Chrome','Windows','*',99999,'Allow',1,6.3,'E…”Ù+ÊNÑ≤û˚Ô¡«W€',18,5,NULL,'PRINTING','GLOBAL','ëõGçÑÿ©ö‡g ');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','Chrome','Windows','*',99999,'Allow',1,6.3,'¥˛í/†F*å¨öÇ;ˆóœ',18,5,NULL,'PRINTING','GLOBAL','ﬁ™ﬁ¶\Z@˝≠/í[WÈæ');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','Chrome','Linux','*',99999,'Allow',1,99999,'qñ|≥ƒßJ‡™c`ƒy»ÑW',18,0,NULL,'PRINTING','GLOBAL','ò§o›F∏°ß◊øä∏5');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','IE','Windows','*',99999,'Allow',1,6.3,'ïD∑Ë≠D	í\Z°Í©ﬁ',10,5,NULL,'PRINTING','GLOBAL','*gÙ%“LæÆ√Øä£ß');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','SIRVE','Development','IE','Windows','*',10,'Allow',1,99999,'ø‡ÏÎﬁBBZ∂äﬁiô∏œ',10,0,'','*','GLOBAL','~£x®ÿKwÉBW,ì*');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','Safari','OSX','*',99999,'Allow',1,99999,'˝¿˛õÆ2HöÄH©d§F™',4.13,0,NULL,'PRINTING','GLOBAL','æ=Ï≠EHéã RTÌÏ');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','*','*','*',99999,'Warn',0,99999,'∫Í©îÒAˇèÓN^áËÕ¡',0,0,NULL,'*','GLOBAL','%∑t#uZBa¶f£Ÿ\r‹û');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Firefox','Windows','*',25,'Allow',1,99999,'iXsûf¬@>¶ÛÅ	aµïy',3.6,0,'','*','GLOBAL','%€ß˚WGHßÆB‰æñ®˝');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','SIRVE','Development','IE','Windows','*',99999,'Warn',1,99999,'ø‡ÏÎﬁBBZ∂äﬁiô∏œ',10.1,0,'','*','GLOBAL','-‡”G÷E„û˝E“ôy•ò');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Chrome','Android','*',28,'Allow',1,4.2,'z‚q\\hNïâDº±z`ˆ',18,4,NULL,'*','GLOBAL','.J–PƒMæJ˙G');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','Chrome','Android','*',99999,'Allow',1,4.2,'∂-¨»VA‰©F?ÊVúíœ',18,4,NULL,'PRINTING','GLOBAL','2êQ¸#D÷Ö+¿5Õ/Í/');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','Chrome','Chrome','*',99999,'Allow',1,99999,'≠…‰n€Kté0≤ﬂí´åz',23,0,NULL,'PRINTING','GLOBAL','4UÂcR∫IÏßˆ‚gd¯K');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Chrome','Linux','*',99999,'Warn',1,99999,'g√¯≠9™BÌ®Áπˇè?Xü',28.1,0,NULL,'*','GLOBAL',';<àbwîN¶æ(‰ü∆E8»');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Safari','Windows','*',6,'Allow',1,99999,'¸aW\'KÒúÓ3N*¬î‹',4,0,NULL,'*','GLOBAL','AuyÜıHóá\0a¥»¢}');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','IE','Windows','*',99999,'Allow',1,6.3,'\"¢±eÓ¡J~î˚∞*•◊Ÿ',10,5,NULL,'PRINTING','GLOBAL','Iø;±-åA’êIî|¬Ê');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','Firefox','Windows','*',99999,'Allow',1,6.3,'·üÆ∞HnçT™p]÷u',3.6,5,NULL,'PRINTING','GLOBAL','NKÖ\\®HüâÃP54?ª');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','*','*','*',99999,'DENY',0,99999,'∫Í©îÒAˇèÓN^áËÕ¡',0,0,NULL,'*','GLOBAL','Ne»oº@Gå∞ï›úH∫');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Firefox','Linux','*',25,'Allow',1,99999,'±bÅ˝ûAºû%ÕÓçΩüÇ',3.6,0,'','*','GLOBAL','OwÉ\"ªeJµíê…∑[ıá¿');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Safari','OSX','*',6,'Allow',1,99999,'Ñ¡Á~prK÷ê≥u!ÄQÃ',4,0,NULL,'*','GLOBAL','Rˆi◊\ZvHE•ÎÉ«∫*9v');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Safari','IOS','*',99999,'Warn',1,999999,'Ór(møúB˙´pb°P0j',6.1,0,NULL,'*','GLOBAL','W±Gfä˜Bìà·¡P}ÏÚ');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Safari','OSX','*',6,'Allow',1,99999,'*∏Ñ\\ M®œ<∆îæ',4,0,NULL,'*','GLOBAL','`ıPˇß^A˛ù«iì');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','Safari','IOS','*',99999,'Allow',1,7.9,'mX|\nÓ¯D√ê∞uØ?¯lí',6,6,NULL,'PRINTING','GLOBAL','b?WÊ◊O“É‚*ŒqŒi');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Student','Development','*','*','*',99999,'Warn',0,99999,'h\"u†∏ΩFqå\r¿0w´',0,0,NULL,'*','GLOBAL','caAåJ©Mõã¥~&ﬂ\"±ÿ');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','IE','Windows','*',99999,'Warn',1,99999,'f	‰BvÑQ%¬ﬂOî',10.1,0,'','*','GLOBAL','d†2ÔËèI\\å≈≤ÍúTıˇ');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Chrome','Android','*',28,'Allow',1,4.2,'≥ÃOnÂGOçBÿ‡+?Ÿœ',18,4,NULL,'*','GLOBAL','n•VyP:Dø≠©Ñ˝»m');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Firefox','Linux','*',99999,'Warn',1,99999,'Ö\0w”◊‚L¥Øy^¶‡’xv',25.1,0,NULL,'*','GLOBAL','o¶’\\\']DÊÆ∑≤6Ë4');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Safari','IOS','*',7.9,'Allow',1,7.9,'@®Gd?JqòÛS˘®Â‰',6,6,NULL,'*','GLOBAL','p∏Îl¢)Hôõe8YÒ‹Ñ');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Firefox','Linux','*',99999,'Warn',1,99999,'±bÅ˝ûAºû%ÕÓçΩüÇ',25.1,0,NULL,'*','GLOBAL','q¬|±ÃCéìm7ã$XF');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Student','Development','*','*','*',99999,'Warn',0,99999,'¢ìäîÃMﬂóî˚Û*[oº',0,0,NULL,'*','GLOBAL','u4Ñ˙ÎN£πB\r	\r`#}');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Chrome','Windows','*',99999,'Warn',1,99999,'≥–πgRoOfá±ÕπA<€º',28.1,0,NULL,'*','GLOBAL','w)ßOtlA€Å+ºU=ŸLŸ');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Chrome','OSX','*',28,'Allow',1,99999,'…^+ï?D∂é\0ç–∑ˆóy',18,0,NULL,'*','GLOBAL','w£≠£ÏôN4è]∑ü\0:˛;');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','*','*','*',99999,'Deny',0,99999,'⁄\nMîuACò±bø{¿†',0,0,NULL,'PRINTING','GLOBAL','xÙíπ–ÏLú•SÏÎ∏=°');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','Chrome','OSX','*',99999,'Allow',1,99999,'PmÇtwNJ´º%&õZ@Z',18,0,NULL,'PRINTING','GLOBAL','yﬁ∫©aBNOÄtà\0\Zë˝l');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','IE','Windows','*',99999,'Warn',1,99999,'f⁄N¥)KŸé´Çd’£m',10.1,0,'','*','GLOBAL','zìK—[ÙJ®Ò’4˜û');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Safari','Linux','*',6,'Allow',1,99999,'1^‘jÿ#J7ôõöoÃ@àÈ',4,0,NULL,'*','GLOBAL','{…=⁄+gIôN8≤’uŒ');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Firefox','Linux','*',25,'Allow',1,99999,'Ö\0w”◊‚L¥Øy^¶‡’xv',3.6,0,'','*','GLOBAL','|ŒU¬€Nı∫‹9˜·Tõ');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Firefox','OSX','*',25,'Allow',1,99999,'|ôÇ£ìB∞;“∏œ∆',3.6,0,'','*','GLOBAL','|Ô@≥Ü˛@º≠kF8`o');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Chrome','Android','*',99999,'Warn',1,4.2,'≥ÃOnÂGOçBÿ‡+?Ÿœ',28.1,4,NULL,'*','GLOBAL','~!≤LπE≤n†çû À∂');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','Chrome','Chrome','*',99999,'Allow',1,99999,'vÕﬂ\\SM.öS˛éÁî•j',23,0,NULL,'PRINTING','GLOBAL','~å6C∆Bw≠FÚñ-˝ü');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Student','Development','*','*','*',99999,'Allow',0,99999,'¢ìäîÃMﬂóî˚Û*[oº',0,0,NULL,'*','TEST','~ﬁ´ú\rAD)åÕ≥*<I[Z');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Safari','Linux','*',99999,'Warn',1,99999,'1^‘jÿ#J7ôõöoÃ@àÈ',6.1,0,NULL,'*','GLOBAL','Ö1 =ΩΩOìï˜. ∞°ï');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Chrome','Linux','*',28,'Allow',1,99999,'g√¯≠9™BÌ®Áπˇè?Xü',18,0,NULL,'*','GLOBAL','àpª9¶†HyÉÂü©“kO');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Chrome','OSX','*',99999,'Warn',1,99999,'…^+ï?D∂é\0ç–∑ˆóy',28.1,0,NULL,'*','GLOBAL','âøVÍœèJGò3–í6mµ9');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Safari','IOS','*',99999,'Warn',1,999999,'@®Gd?JqòÛS˘®Â‰',6.1,0,NULL,'*','GLOBAL','ånﬁÅ„€A„ûÜ§Å…SQ¶');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Android','Android','*',999999,'Allow',1,4.2,'’À°ÁãA\rÆ9∞·Í3T&',4,4,NULL,'*','GLOBAL','ê8NEûã$ûˇ÷(Ú');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Safari','Windows','*',6,'Allow',1,99999,'B∏¡ÂˆHŒüﬁÚ–RØ|ã',4,0,NULL,'*','GLOBAL','êH]2-∑@≤ª†ı≈¢Íiq');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','Firefox','Linux','*',99999,'Allow',1,99999,'eˇ∂EdO@ÉU_——#$·',3.6,0,NULL,'PRINTING','GLOBAL','í}çëèM2àÖy≠Êπ');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Safari','Linux','*',99999,'Warn',1,99999,'9Ø8ç0H<¥’à˝·ÿ«',6.1,0,NULL,'*','GLOBAL','ñ-öÖ_K∞®¶™≤ã');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Safari','IOS','*',7.9,'Allow',1,7.9,'Ór(møúB˙´pb°P0j',6,6,NULL,'*','GLOBAL','òÏ3ÃLHfÅ§G‹ÉÔ+’');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Firefox','OSX','*',99999,'Warn',1,99999,'|ôÇ£ìB∞;“∏œ∆',25.1,0,NULL,'*','GLOBAL','ôπ”e’≠K≥°y}›ãQá');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Student','Development','AIRSecureBrowser','Chrome','*',99999,'Allow',1,99999,' \rsüˇkNºº\"¡ÆD7jj',2.3,31,NULL,'*','GLOBAL','õ˜ÇéÓO2Å„ªß$‰?†');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','Safari','OSX','*',99999,'Allow',1,99999,'ÈW @Äân?Ô˙z∆',4.13,0,NULL,'PRINTING','GLOBAL','ßê¿âöBL∑E…HU˜');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','*','*','*',99999,'Deny',0,99999,'ˆœÆ#Æ*F˚≠üıÀ∞í',0,0,NULL,'PRINTING','GLOBAL','´)Î\rmRDÌÑ¬ﬂ€˝SU');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Student','Development','IE','Windows','*',99999,'Warn',1,99999,'·\"≠í∞ÔIƒºØ[;ì˛◊w',10.1,0,'','*','GLOBAL','∞˚j¸»øHsûÉÕ«JÅá@');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','Firefox','Linux','*',99999,'Allow',1,99999,'∞3SºhHˇÑNO–™Y@',3.6,0,NULL,'PRINTING','GLOBAL','≥	ù‚Iôöè\"@À');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','Safari','IOS','*',99999,'Allow',1,7.9,'Î”n…\'¡F7©™c€ãB7Ù',6,6,NULL,'PRINTING','GLOBAL','¥ë€?RBìº∞˛s=ÿØ');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Student','Development','AIRSecureBrowser','Chrome','*',10000000,'Deny',1,30,'˝˜ˇjEŒôÇ,∆√ÑŒö',2,0,NULL,'*','GLOBAL','∑T±O\\I=§ÏÄ$¥¨m');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Student','Development','AIRSecureBrowser','Chrome','*',10000000,'Allow',1,99999,'˝˜ˇjEŒôÇ,∆√ÑŒö',1.1,31,NULL,'*','GLOBAL','Ω€qjI·OÄıÎBÇ°I');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','IE','Windows','*',10,'Allow',1,99999,'f	‰BvÑQ%¬ﬂOî',10,0,'','*','GLOBAL','Ã“¯v∆EßØYn€ô§¶.');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','Chrome','Linux','*',99999,'Allow',1,99999,'L¢7Òn†Jq°y∫ló:x',18,0,NULL,'PRINTING','GLOBAL','œmÏÈxLºÄ4qkÓyªb');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Chrome','Windows','*',28,'Allow',1,99999,'‡¯„%‹J)¶Ãø-]F?8',18,0,NULL,'*','GLOBAL','÷Â‰èMöI∞ù§Û—ÔŒ,6');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','Chrome','OSX','*',99999,'Allow',1,99999,'t…ú~ÏO˚éeoAÕÀ˛⁄',18,0,NULL,'PRINTING','GLOBAL','Ÿ8V7\ZÂLY¶∫NùI');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Chrome','Windows','*',99999,'Warn',1,99999,'‡¯„%‹J)¶Ãø-]F?8',28.1,0,NULL,'*','GLOBAL','ﬁsçY…8O ëº#LŸ˙îÏ');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Safari','Linux','*',6,'Allow',1,99999,'9Ø8ç0H<¥’à˝·ÿ«',4,0,NULL,'*','GLOBAL','ﬂ‹”¸#äLiè¡%≠ˆ<˜\n');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Student','Development','IE','Windows','*',10,'Allow',1,99999,'·\"≠í∞ÔIƒºØ[;ì˛◊w',10,0,'','*','GLOBAL','·5k:ë@C¡ï‡’çcä;');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Safari','Windows','*',99999,'Warn',1,99999,'B∏¡ÂˆHŒüﬁÚ–RØ|ã',6.1,0,NULL,'*','GLOBAL','Î≥X·ˇ±Bâw{Q-0');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Firefox','Windows','*',99999,'Warn',1,99999,'iXsûf¬@>¶ÛÅ	aµïy',25.1,0,NULL,'*','GLOBAL','Î≥±ˇﬂæOôç<.!˚\Z9¬');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Safari','OSX','*',99999,'Warn',1,99999,'Ñ¡Á~prK÷ê≥u!ÄQÃ',6.1,0,NULL,'*','GLOBAL','Ïro˝∫D·Ük¶òısË8');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Chrome','Linux','*',99999,'Warn',1,99999,'“ÇV∂QE‹é„ÆˇÚ…z',28.1,0,NULL,'*','GLOBAL','Ì©(c:ñLOö∞2â«`Œ');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Firefox','OSX','*',99999,'Warn',1,99999,'c’ÓúÖöJNø(π˚Zü†d',25.1,0,NULL,'*','GLOBAL','Ó(5W¿‹K+â°W›#>’)');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','Firefox','OSX','*',99999,'Allow',1,99999,'¬÷x«D C\"ï)û•ΩX‡8',3.6,0,NULL,'PRINTING','GLOBAL','Ó•RÕ&EÁçΩ⁄œﬂõ˙');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','Firefox','Windows','*',99999,'Allow',1,6.3,'Âî´w\0Jo©:jÑÉJT',3.6,5,NULL,'PRINTING','GLOBAL','Ô©É∑HüJï≤p âSƒƒà');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Android','Android','*',999999,'Allow',1,4.2,'xäuR|Eê4}IM9€ﬂ',4,4,NULL,'*','GLOBAL','ıŒ⁄LA¬@∂®ÂzÏdû∑ç');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','*','*','*',99999,'Warn',0,99999,'u	XÎŸN7§«!èπ9',0,0,NULL,'*','GLOBAL','¯$ (˜MHë¸I|œ~€');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Chrome','OSX','*',99999,'Warn',1,99999,'æ»ΩM·9Eo∫ô∏ät+Í',28.1,0,NULL,'*','GLOBAL','¯ZÔgã,IM°LÀ≥ü˜');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Firefox','Windows','*',99999,'Warn',1,99999,'¥¸$BD^®`0	Æ≈“R',25.1,0,NULL,'*','GLOBAL','¸}9û≠EFï.*˛J,');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Firefox','Windows','*',25,'Allow',1,99999,'¥¸$BD^®`0	Æ≈“R',3.6,0,'','*','GLOBAL','¸Ér¡EΩKTñµ´àù\Z');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','Firefox','OSX','*',99999,'Allow',1,99999,'ùƒáEúD	§bÛh¡`1®',3.6,0,NULL,'PRINTING','GLOBAL','˝˙˝O€∫Àë	1∂è');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','Chrome','Android','*',99999,'Allow',1,4.2,'÷`÷Ä»îKfà&\rõmñß†',18,4,NULL,'PRINTING','GLOBAL','ˇ_£v,\"N\0Öàªx◊µÒ');
/*!40000 ALTER TABLE `system_browserwhitelist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `system_networkdiagnostics`
--

LOCK TABLES `system_networkdiagnostics` WRITE;
/*!40000 ALTER TABLE `system_networkdiagnostics` DISABLE KEYS */;
INSERT INTO `system_networkdiagnostics` VALUES ('SBAC_PT','Student','English Language Arts',50,100,60,'`å®òH]ño{¶ea');
INSERT INTO `system_networkdiagnostics` VALUES ('SBAC','Student','English Language Arts',50,100,60,'ànm2DçK¶ñNø5}H');
INSERT INTO `system_networkdiagnostics` VALUES ('SBAC_PT','Student','Mathematics',50,100,60,'‚Ü^%nÜOØö£8àª,\nœ');
INSERT INTO `system_networkdiagnostics` VALUES ('SBAC','Student','Mathematics',50,100,60,'Ï∫dYÔFJë¬œB©TZa');
/*!40000 ALTER TABLE `system_networkdiagnostics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `client_itemscoringconfig`
--

LOCK TABLES `client_itemscoringconfig` WRITE;
/*!40000 ALTER TABLE `client_itemscoringconfig` DISABLE KEYS */;
INSERT INTO `client_itemscoringconfig`
VALUES ('SBAC','*','*','*','HTQ','',1,unhex(replace(uuid(), '-', '')),'http://localhost:8080/item-scoring-service/Scoring/ItemScoring','Development')
      ,('SBAC','*','*','*','*','',0,unhex(replace(uuid(), '-', '')),'http://localhost:8080/itemscoring/Scoring/ItemScoring','Development')
      ,('*','*','*','*','QTI','',1,unhex(replace(uuid(), '-', '')),'http://localhost:8080/itemscoring/Scoring/ItemScoring','Development')
      ,('SBAC','*','*','*','EQ','',1,unhex(replace(uuid(), '-', '')),'http://localhost:8080/itemscoring/Scoring/ItemScoring','Development')
      ,('SBAC','*','*','*','GI','',1,unhex(replace(uuid(), '-', '')),'http://localhost:8080/itemscoring/Scoring/ItemScoring','Development')
      ,('SBAC_PT','*','*','*','HTQ','',1,unhex(replace(uuid(), '-', '')),'http://localhost:8080/item-scoring-service/Scoring/ItemScoring','Development')
      ,('SBAC_PT','*','*','*','*','',0,unhex(replace(uuid(), '-', '')),'http://localhost:8080/itemscoring/Scoring/ItemScoring','Development')
      ,('SBAC_PT','*','*','*','EQ','',1,unhex(replace(uuid(), '-', '')),'http://localhost:8080/itemscoring/Scoring/ItemScoring','Development')
      ,('SBAC_PT','*','*','*','GI','',1,unhex(replace(uuid(), '-', '')),'http://localhost:8080/itemscoring/Scoring/ItemScoring','Development');
/*!40000 ALTER TABLE `client_itemscoringconfig` ENABLE KEYS */;
UNLOCK TABLES;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-09-04 11:39:28
