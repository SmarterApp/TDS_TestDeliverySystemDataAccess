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
INSERT INTO `statuscodes` VALUES ('opportunity','suspended','an existing opportunity is waiting for proctor approval to restart','inuse','N\Z�zWI9� ��~�$�');
INSERT INTO `statuscodes` VALUES ('opportunity','scored','completed test has been scored','closed','�/�VNH���Q��.');
INSERT INTO `statuscodes` VALUES ('opportunity','reported','test scores have been reported to reporting system','closed','%�t3�zI��V�&󄐹');
INSERT INTO `statuscodes` VALUES ('session ','closed','the session is closed for business','closed','43���A糰ǳ|?');
INSERT INTO `statuscodes` VALUES ('session','open','the session is open for business','open','=���!L����߆/�');
INSERT INTO `statuscodes` VALUES ('opportunity','approved','opportunity has been approved for start/restart by proctor','inuse','jV���PJãn4�\rn�');
INSERT INTO `statuscodes` VALUES ('opportunity','paused','opportunity has been put on hold by the user','inactive','uR@YNH5�7�K6�sT');
INSERT INTO `statuscodes` VALUES ('opportunity','pending','a new opportunity is waiting for proctor approval','inuse','�.s�r�Kj��.I�[��');
INSERT INTO `statuscodes` VALUES ('opportunity','expired','incomplete test has expired','closed','�{��G\'J#�á��3�');
INSERT INTO `statuscodes` VALUES ('opportunity','segmentExit','request to leave a segment','inuse','���^�C[��gz���');
INSERT INTO `statuscodes` VALUES ('opportunity','denied','opportunity start was denied by the proctor','inactive','��\0��L6�	0tV��k');
INSERT INTO `statuscodes` VALUES ('opportunity','segmentEntry','request to enter a segment','inuse','�k^%1�Cy�����#');
INSERT INTO `statuscodes` VALUES ('opportunity','review','testee is reviewing completed test items','inuse','�J�=�N,�k3����');
INSERT INTO `statuscodes` VALUES ('opportunity','completed','test has been completed and submitted for scoring by testee','closed','�Y\rͣG�e�h���4');
INSERT INTO `statuscodes` VALUES ('opportunity','submitted','test scores have been submitted to QA','closed','�LRg�H��;��\\dB�');
INSERT INTO `statuscodes` VALUES ('opportunity','rescored','the opportunity was rescored','closed','Ηz�EHq�޿gv�');
INSERT INTO `statuscodes` VALUES ('opportunity','invalidated','test results have been invalidated','closed','�g�B��¨G�%');
INSERT INTO `statuscodes` VALUES ('opportunity','started','opportunity is in use','inuse','��O%#@\'����:���');
/*!40000 ALTER TABLE `statuscodes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `system_applicationsettings`
--

LOCK TABLES `system_applicationsettings` WRITE;
/*!40000 ALTER TABLE `system_applicationsettings` DISABLE KEYS */;
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','Student','tds.itemRenderer.enableMathML','boolean','true','ե��K	��w�Ơ<�','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ResponseEntry','http.cache.prefetch','string','Enabled','\r�q�ӃIc�܍k�F�*','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ScoreEntry','tds.itemRenderer.enableMathML','boolean','true','�q�J&�!ʑ���;','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','Student','ItemScoring.Timer.Enabled','boolean','true','/�s��F@��mD��','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ScoreEntry','http.cache.id','string','1','8tiV��M��e�!�','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','Student','tds.itemRenderer.supportHttpRanges','boolean','true','`X&XC����U�|#','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ScoreEntry','ItemScoring.Enabled','boolean','true','`\\���H⓰������','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','Student','ItemScoring.Enabled','boolean','true','b/ZݿuK>����+7','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ScoreEntry','itemScoring.timer.intervalSecs','integer','300','cV��RN��d�k@(','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ResponseEntry','itemScoring.timer.intervalSecs','integer','300','f��H��O̊��F(��','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ResponseEntry','tds.itemRenderer.urlResolverVersion','integer','2','s��A��IQ�<��zƹ�','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ScoreEntry','ItemScoring.ServerUrl','string','https://tds2.airws.org/Test_ItemScoringEngine_2013/ItemScoring.axd','s��&��Lܝ�?��','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ResponseEntry','ItemScoring.Timer.Enabled','boolean','true','ym��`�FF��K����','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ResponseEntry','tds.itemRenderer.enableMathML','boolean','true','z��vN+�����I','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ScoreEntry','http.cache.prefetch','string','Enabled','��P��F�h�*�O','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ScoreEntry','http.manifest.mode','string','Disabled','��PW\rA!����xp','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ScoreEntry','tds.itemRenderer.urlResolverVersion','integer','2','�5!Z\Z�O���?0�q','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','Student','http.cache.id','string','1','�;�r�wL���������','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ResponseEntry','http.cache.id','string','1','�x ��E��MZ%��','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','Student','tds.itemRenderer.urlResolverVersion','integer','2','���[�@}��<���\\r','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ResponseEntry','ItemScoring.ServerUrl','string','https://tds2.airws.org/Test_ItemScoringEngine_2013/ItemScoring.axd','�-�_�uI���o.�x','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','Student','http.cache.prefetch','string','Enabled','��\rΫJ������F','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ScoreEntry','tds.itemRenderer.supportHttpRanges','boolean','true','�D.�	B�>��o�','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','Student','ItemScoring.ServerUrl','string','https://tds2.airws.org/Test_ItemScoringEngine_2013/ItemScoring.axd','�ȋ�\rfD��7�U�<','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ResponseEntry','ItemScoring.Enabled','boolean','true','�K�[(F��_x_��','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ResponseEntry','tds.itemRenderer.supportHttpRanges','boolean','true','��@�!�O-��`4\"','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ScoreEntry','ItemScoring.Timer.Enabled','boolean','true','�^Q���B��G�fɥ:','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','ResponseEntry','http.manifest.mode','string','Disabled','����EMʕ��B#f0','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','Student','itemScoring.timer.intervalSecs','integer','300','�b�/uF�~\'T<t','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC','Development','Student','http.manifest.mode','string','Disabled','�\0m��*A+�똖w��[','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC_PT','Development','Student','tds.itemRenderer.urlResolverVersion','integer','2','-q��_�C]��W�̯L�','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC_PT','Development','Student','ItemScoring.Enabled','boolean','true','L��giOC�TAz��\'�','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC_PT','Development','Student','http.cache.prefetch','string','Enabled','Zf�\\�I󿟕�ׁA','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC_PT','Development','Student','ItemScoring.ServerUrl','string','https://tds2.airws.org/Test_ItemScoringEngine_2013/ItemScoring.axd','u���BݒK�V�uw�','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC_PT','Development','Student','tds.itemRenderer.enableMathML','boolean','true','u\n��PO�H��9��J','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC_PT','Development','Student','itemScoring.timer.intervalSecs','integer','300','uʊ�\0L����a�J','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC_PT','Development','Student','http.manifest.mode','string','Disabled','�[�~�\'O�oca7�n�','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC_PT','Development','Student','tds.itemRenderer.supportHttpRanges','boolean','true','��GD�GL+�g}','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC_PT','Development','Student','http.cache.id','string','1','�D��eC���E���`','*','*');
INSERT INTO `system_applicationsettings` VALUES ('SBAC_PT','Development','Student','ItemScoring.Timer.Enabled','boolean','true','�<�B	iD\"�(i�;�','*','*');
/*!40000 ALTER TABLE `system_applicationsettings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `system_browserwhitelist`
--

LOCK TABLES `system_browserwhitelist` WRITE;
/*!40000 ALTER TABLE `system_browserwhitelist` DISABLE KEYS */;
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','IE','Windows','*',10,'Allow',1,99999,'f�N�)Kَ��dգm',10,0,'','*','GLOBAL','V�b�CK�bLf����');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Safari','OSX','*',99999,'Warn',1,99999,'*��\\�M��<Ɣ��',6.1,0,NULL,'*','GLOBAL','�H�w�@涌�#�G�a');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Chrome','Windows','*',28,'Allow',1,99999,'�йgRoOf��͹A<ۼ',18,0,NULL,'*','GLOBAL','xB���@+���%�r');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Firefox','OSX','*',25,'Allow',1,99999,'c��JN�(��Z��d',3.6,0,'','*','GLOBAL','`B�s\rCj�\r�eI�S');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','SIRVE','Development','*','*','*',99999,'Warn',0,99999,'���c�DH�ǣ#B�(',0,0,NULL,'*','GLOBAL','�e��FJ��J�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Safari','Windows','*',99999,'Warn',1,99999,'�aW\'K��3N*�',6.1,0,NULL,'*','GLOBAL','���Ng�7��=��');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Chrome','OSX','*',28,'Allow',1,99999,'�ȽM�9Eo����t+�',18,0,NULL,'*','GLOBAL','	��\'L@2�F!�5�}');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Chrome','Android','*',99999,'Warn',1,4.2,'z�q\\�hN��D��z`�',28.1,4,NULL,'*','GLOBAL','	�_�e7Cq�A왊U�b');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Chrome','Linux','*',28,'Allow',1,99999,'҂V�QE܎�����z',18,0,NULL,'*','GLOBAL','\r��9=I˄�Y#��');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','Chrome','Windows','*',99999,'Allow',1,6.3,'E���+�N�������W�',18,5,NULL,'PRINTING','GLOBAL','��G��ة��g�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','Chrome','Windows','*',99999,'Allow',1,6.3,'���/�F*����;���',18,5,NULL,'PRINTING','GLOBAL','ުަ\Z@��/�[W�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','Chrome','Linux','*',99999,'Allow',1,99999,'q�|�ħJ�c`�yȄW',18,0,NULL,'PRINTING','GLOBAL','��o�F���׿��5');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','IE','Windows','*',99999,'Allow',1,6.3,'�D���D	�\Z���',10,5,NULL,'PRINTING','GLOBAL','*g�%�L��ï���');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','SIRVE','Development','IE','Windows','*',10,'Allow',1,99999,'�����BBZ���i���',10,0,'','*','GLOBAL','~�x��Kw�BW,�*');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','Safari','OSX','*',99999,'Allow',1,99999,'�����2H��H�d�F�',4.13,0,NULL,'PRINTING','GLOBAL','�=�EH���RT��');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','*','*','*',99999,'Warn',0,99999,'�꩔�A���N^����',0,0,NULL,'*','GLOBAL','%�t#uZBa�f��\rܞ');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Firefox','Windows','*',25,'Allow',1,99999,'iXs�f�@>��	a��y',3.6,0,'','*','GLOBAL','%ۧ�WGH��B侖��');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','SIRVE','Development','IE','Windows','*',99999,'Warn',1,99999,'�����BBZ���i���',10.1,0,'','*','GLOBAL','-��G�E��Eҙy��');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Chrome','Android','*',28,'Allow',1,4.2,'z�q\\�hN��D��z`�',18,4,NULL,'*','GLOBAL','.J�P�M�J�G');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','Chrome','Android','*',99999,'Allow',1,4.2,'�-��VA�F?�V���',18,4,NULL,'PRINTING','GLOBAL','2�Q�#Dօ+�5�/�/');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','Chrome','Chrome','*',99999,'Allow',1,99999,'���n�Kt�0�ߒ��z',23,0,NULL,'PRINTING','GLOBAL','4U�cR�I���gd�K');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Chrome','Linux','*',99999,'Warn',1,99999,'g���9�B����?X�',28.1,0,NULL,'*','GLOBAL',';<�bw�N��(��E8�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Safari','Windows','*',6,'Allow',1,99999,'�aW\'K��3N*�',4,0,NULL,'*','GLOBAL','Auy��H��\0a�Ȣ}');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','IE','Windows','*',99999,'Allow',1,6.3,'\"��e��J~���*���',10,5,NULL,'PRINTING','GLOBAL','I�;�-�AՐI�|��');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','Firefox','Windows','*',99999,'Allow',1,6.3,'៮�Hn�T�p]�u',3.6,5,NULL,'PRINTING','GLOBAL','NK�\\�H���P54?�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','*','*','*',99999,'DENY',0,99999,'�꩔�A���N^����',0,0,NULL,'*','GLOBAL','Ne�o�@G���ݜH�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Firefox','Linux','*',25,'Allow',1,99999,'�b���A��%���',3.6,0,'','*','GLOBAL','Ow�\"�eJ���ɷ[���');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Safari','OSX','*',6,'Allow',1,99999,'���~prK֐�u!�Q�',4,0,NULL,'*','GLOBAL','R�i�\ZvHE��Ǻ*9v');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Safari','IOS','*',99999,'Warn',1,999999,'�r(m��B��pb�P0j',6.1,0,NULL,'*','GLOBAL','W�Gf��B����P}��');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Safari','OSX','*',6,'Allow',1,99999,'*��\\�M��<Ɣ��',4,0,NULL,'*','GLOBAL','`�P��^A���i�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','Safari','IOS','*',99999,'Allow',1,7.9,'mX|\n��DÐ�u�?�l�',6,6,NULL,'PRINTING','GLOBAL','b?W��O҃�*�q�i');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Student','Development','*','*','*',99999,'Warn',0,99999,'h\"u���Fq�\r�0w�',0,0,NULL,'*','GLOBAL','caA�J�M���~&�\"��');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','IE','Windows','*',99999,'Warn',1,99999,'f	�Bv�Q%��O�',10.1,0,'','*','GLOBAL','d�2��I\\�Ų�T��');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Chrome','Android','*',28,'Allow',1,4.2,'��On�GO�B��+?��',18,4,NULL,'*','GLOBAL','n�VyP:D������m');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Firefox','Linux','*',99999,'Warn',1,99999,'�\0w���L��y^���xv',25.1,0,NULL,'*','GLOBAL','o��\\\']D殷�6�4');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Safari','IOS','*',7.9,'Allow',1,7.9,'@�Gd?Jq��S����',6,6,NULL,'*','GLOBAL','p��l�)H��e8Y�܄');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Firefox','Linux','*',99999,'Warn',1,99999,'�b���A��%���',25.1,0,NULL,'*','GLOBAL','q�|��C��m7�$XF');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Student','Development','*','*','*',99999,'Warn',0,99999,'�����Mߗ���*[o�',0,0,NULL,'*','GLOBAL','u4���N��B\r	\r`#}');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Chrome','Windows','*',99999,'Warn',1,99999,'�йgRoOf��͹A<ۼ',28.1,0,NULL,'*','GLOBAL','w)�OtlAہ+�U=�L�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Chrome','OSX','*',28,'Allow',1,99999,'�^+�?D��\0�з��y',18,0,NULL,'*','GLOBAL','w����N4�]��\0:�;');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','*','*','*',99999,'Deny',0,99999,'�\nM�uAC��b�{��',0,0,NULL,'PRINTING','GLOBAL','x�����L��S��=�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','Chrome','OSX','*',99999,'Allow',1,99999,'Pm�twNJ��%&�Z@Z',18,0,NULL,'PRINTING','GLOBAL','y޺�aBNO�t�\0\Z��l');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','IE','Windows','*',99999,'Warn',1,99999,'f�N�)Kَ��dգm',10.1,0,'','*','GLOBAL','z�K�[�J���4��');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Safari','Linux','*',6,'Allow',1,99999,'1^�j�#J7���o�@��',4,0,NULL,'*','GLOBAL','{�=�+gI�N8��u�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Firefox','Linux','*',25,'Allow',1,99999,'�\0w���L��y^���xv',3.6,0,'','*','GLOBAL','|�U��N���9��T�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Firefox','OSX','*',25,'Allow',1,99999,'|����B�;Ҹ��',3.6,0,'','*','GLOBAL','|�@���@��kF8`o');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Chrome','Android','*',99999,'Warn',1,4.2,'��On�GO�B��+?��',28.1,4,NULL,'*','GLOBAL','~!�L�E�n����˶');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','Chrome','Chrome','*',99999,'Allow',1,99999,'v��\\SM.�S��甥j',23,0,NULL,'PRINTING','GLOBAL','~�6C�Bw�F�-���');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Student','Development','*','*','*',99999,'Allow',0,99999,'�����Mߗ���*[o�',0,0,NULL,'*','TEST','~ޫ�\rAD)�ͳ*<I[Z');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Safari','Linux','*',99999,'Warn',1,99999,'1^�j�#J7���o�@��',6.1,0,NULL,'*','GLOBAL','�1 =��O���. ���');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Chrome','Linux','*',28,'Allow',1,99999,'g���9�B����?X�',18,0,NULL,'*','GLOBAL','�p�9��Hy�埩�kO');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Chrome','OSX','*',99999,'Warn',1,99999,'�^+�?D��\0�з��y',28.1,0,NULL,'*','GLOBAL','��V�ϏJG�3В6m�9');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Safari','IOS','*',99999,'Warn',1,999999,'@�Gd?Jq��S����',6.1,0,NULL,'*','GLOBAL','�nށ��A㞆���SQ�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Android','Android','*',999999,'Allow',1,4.2,'�ˡ��A\r�9���3T&',4,4,NULL,'*','GLOBAL','�8NE��$���(�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Safari','Windows','*',6,'Allow',1,99999,'B����HΟ���R�|�',4,0,NULL,'*','GLOBAL','�H]2-�@����Ţ�iq');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','Firefox','Linux','*',99999,'Allow',1,99999,'e��EdO@�U_��#$�',3.6,0,NULL,'PRINTING','GLOBAL','�}���M2��y��');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Safari','Linux','*',99999,'Warn',1,99999,'9�8�0H<�Ո����',6.1,0,NULL,'*','GLOBAL','�-��_K������');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Safari','IOS','*',7.9,'Allow',1,7.9,'�r(m��B��pb�P0j',6,6,NULL,'*','GLOBAL','��3�LHf��G܃�+�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Firefox','OSX','*',99999,'Warn',1,99999,'|����B�;Ҹ��',25.1,0,NULL,'*','GLOBAL','���eխK��y}݋Q�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Student','Development','AIRSecureBrowser','Chrome','*',99999,'Allow',1,99999,' \rs��kN��\"��D7jj',2.3,31,NULL,'*','GLOBAL','�����O2�㻧$�?�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','Safari','OSX','*',99999,'Allow',1,99999,'�W�@��n?��z�',4.13,0,NULL,'PRINTING','GLOBAL','�����BL�E�HU�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','*','*','*',99999,'Deny',0,99999,'�Ϯ#�*F�������',0,0,NULL,'PRINTING','GLOBAL','�)�\rmRD�����SU');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Student','Development','IE','Windows','*',99999,'Warn',1,99999,'�\"����Iļ�[;���w',10.1,0,'','*','GLOBAL','��j�ȿHs����J��@');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','Firefox','Linux','*',99999,'Allow',1,99999,'�3S�hH��NOЪY@',3.6,0,NULL,'PRINTING','GLOBAL','�	��I���\"@�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','Safari','IOS','*',99999,'Allow',1,7.9,'��n�\'�F7��cۋB7�',6,6,NULL,'PRINTING','GLOBAL','���?RB����s=د');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Student','Development','AIRSecureBrowser','Chrome','*',10000000,'Deny',1,30,'���jEΙ�,�ÄΚ',2,0,NULL,'*','GLOBAL','�T�O\\I=��$��m');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Student','Development','AIRSecureBrowser','Chrome','*',10000000,'Allow',1,99999,'���jEΙ�,�ÄΚ',1.1,31,NULL,'*','GLOBAL','��qjI�O���B���I');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','IE','Windows','*',10,'Allow',1,99999,'f	�Bv�Q%��O�',10,0,'','*','GLOBAL','���v�E��Ynۙ��.');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','Chrome','Linux','*',99999,'Allow',1,99999,'L�7�n�Jq�y�l�:x',18,0,NULL,'PRINTING','GLOBAL','�m��xL��4qk�y�b');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Chrome','Windows','*',28,'Allow',1,99999,'���%�J)�̿-]F?8',18,0,NULL,'*','GLOBAL','���M�I�������,6');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','Chrome','OSX','*',99999,'Allow',1,99999,'tɜ~�O��eoA����',18,0,NULL,'PRINTING','GLOBAL','�8V7\Z�LY��N�I');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Chrome','Windows','*',99999,'Warn',1,99999,'���%�J)�̿-]F?8',28.1,0,NULL,'*','GLOBAL','�s�Y�8Oʑ�#L����');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Safari','Linux','*',6,'Allow',1,99999,'9�8�0H<�Ո����',4,0,NULL,'*','GLOBAL','����#�Li��%��<�\n');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Student','Development','IE','Windows','*',10,'Allow',1,99999,'�\"����Iļ�[;���w',10,0,'','*','GLOBAL','�5k:�@C���Սc�;');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Safari','Windows','*',99999,'Warn',1,99999,'B����HΟ���R�|�',6.1,0,NULL,'*','GLOBAL','�X���B�w�{Q-0');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Firefox','Windows','*',99999,'Warn',1,99999,'iXs�f�@>��	a��y',25.1,0,NULL,'*','GLOBAL','볱�߾O��<.!�\Z9�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Safari','OSX','*',99999,'Warn',1,99999,'���~prK֐�u!�Q�',6.1,0,NULL,'*','GLOBAL','�ro��D�k���s�8');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Chrome','Linux','*',99999,'Warn',1,99999,'҂V�QE܎�����z',28.1,0,NULL,'*','GLOBAL','�(c:�LO��2��`�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Firefox','OSX','*',99999,'Warn',1,99999,'c��JN�(��Z��d',25.1,0,NULL,'*','GLOBAL','�(5W��K+��W�#>�)');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','Firefox','OSX','*',99999,'Allow',1,99999,'��x�D C\"�)���X�8',3.6,0,NULL,'PRINTING','GLOBAL','�R�&E獽��ߛ�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','Firefox','Windows','*',99999,'Allow',1,6.3,'��w\0Jo�:j��JT',3.6,5,NULL,'PRINTING','GLOBAL','暑�H�J��p �S�Ĉ');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Android','Android','*',999999,'Allow',1,4.2,'x�uR|E�4}IM9��',4,4,NULL,'*','GLOBAL','���LA�@���z�d���');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','*','*','*',99999,'Warn',0,99999,'u	X��N7��!��9',0,0,NULL,'*','GLOBAL','�$ (�MH��I|�~�');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ScoreEntry','Development','Chrome','OSX','*',99999,'Warn',1,99999,'�ȽM�9Eo����t+�',28.1,0,NULL,'*','GLOBAL','�Z�g�,IM�L˳��');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Firefox','Windows','*',99999,'Warn',1,99999,'��$BD^�`0	���R',25.1,0,NULL,'*','GLOBAL','�}9��EF�.*�J,');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','ResponseEntry','Development','Firefox','Windows','*',25,'Allow',1,99999,'��$BD^�`0	���R',3.6,0,'','*','GLOBAL','��r�E�KT�����\Z');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC_PT','Proctor','Development','Firefox','OSX','*',99999,'Allow',1,99999,'�ćE�D	�b�h�`1�',3.6,0,NULL,'PRINTING','GLOBAL','���Oۺˑ	�1��');
INSERT INTO `system_browserwhitelist` VALUES ('SBAC','Proctor','Development','Chrome','Android','*',99999,'Allow',1,4.2,'�`րȔKf�&\r�m���',18,4,NULL,'PRINTING','GLOBAL','�_�v,\"N\0���x׵�');
/*!40000 ALTER TABLE `system_browserwhitelist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `system_networkdiagnostics`
--

LOCK TABLES `system_networkdiagnostics` WRITE;
/*!40000 ALTER TABLE `system_networkdiagnostics` DISABLE KEYS */;
INSERT INTO `system_networkdiagnostics` VALUES ('SBAC_PT','Student','English Language Arts',50,100,60,'`���H]�o{�ea');
INSERT INTO `system_networkdiagnostics` VALUES ('SBAC','Student','English Language Arts',50,100,60,'�nm2D�K��N�5}H');
INSERT INTO `system_networkdiagnostics` VALUES ('SBAC_PT','Student','Mathematics',50,100,60,'�^%n�O���8��,\n�');
INSERT INTO `system_networkdiagnostics` VALUES ('SBAC','Student','Mathematics',50,100,60,'�dY�FJ���B�TZa');
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
