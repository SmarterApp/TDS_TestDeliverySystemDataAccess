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
-- Dumping data for table `client_externs`
--

LOCK TABLES `client_externs` WRITE;
/*!40000 ALTER TABLE `client_externs` DISABLE KEYS */;
INSERT INTO `client_externs` VALUES ('�T��K��%���4Q)','MultiClient_RTS_2013','MultiClient_RTS_2013','itembank','','','RTS','RTS','','session','','','SBAC','','SBAC',NULL,'Development','\0',0,NULL,100000,1,NULL,NULL);
INSERT INTO `client_externs` VALUES ('d���hFMB�à��a�H','MultiClient_RTS_2013','MultiClient_RTS_2013','itembank','','','RTS','RTS','','session','','','SBAC_PT','','SBAC',NULL,'Development','',0,NULL,100000,1,NULL,NULL);
/*!40000 ALTER TABLE `client_externs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `client_forbiddenappslist`
--

LOCK TABLES `client_forbiddenappslist` WRITE;
/*!40000 ALTER TABLE `client_forbiddenappslist` DISABLE KEYS */;
INSERT INTO `client_forbiddenappslist` VALUES ('IOS','FaceTime','FaceTime','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('IOS','FaceTime','FaceTime','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('IOS','Skype','Skype','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('IOS','Skype','Skype','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','acroread','Adobe Acrobat Reader','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','acroread','Adobe Acrobat Reader','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','chrome','Chrome','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','chrome','Chrome','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','compiz/compiz-fusion','Compiz','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','compiz/compiz-fusion','Compiz','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','dropbox','DropBox File Sharing','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','dropbox','DropBox File Sharing','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','evolution','Evolution Email Client','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','evolution','Evolution Email Client','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','firefox','Firefox Web Browser','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','firefox','Firefox Web Browser','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','gcalctool','Gnome Calculator Utility','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','gcalctool','Gnome Calculator Utility','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','gnome-screenshot','Gnome-Screenshot','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','gnome-screenshot','Gnome-Screenshot','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','gtk-recordMyDes','recordMyDesktop','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','gtk-recordMyDes','recordMyDesktop','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','iexplore.exe','Internet Explorer','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','iexplore.exe','Internet Explorer','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','konqueror','Konqueror Web Browser','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','konqueror','Konqueror Web Browser','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','ksnapshot','Ksnapshot','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','ksnapshot','Ksnapshot','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','oosplash','LibreOffice','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','oosplash','LibreOffice','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','opera','Opera','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','opera','Opera','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','pidgen','Pidgen IM Client','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','pidgen','Pidgen IM Client','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','seamonkey-bin','SeaMonkey','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','seamonkey-bin','SeaMonkey','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','shutter','Shutter','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','shutter','Shutter','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','skype','Skype','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','skype','Skype','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','soffice','OpenOffice','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','soffice','OpenOffice','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','soffice.bin','Open Office','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','soffice.bin','Open Office','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','spadmin.bin','LibreOffice','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','spadmin.bin','LibreOffice','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','TeamViewer.exe','TeamViewer','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','TeamViewer.exe','TeamViewer','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','thunderbird-bin','Thunderbird Email Client','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','thunderbird-bin','Thunderbird Email Client','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','wink','Wink','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Linux','wink','Wink','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Activity Monitor','Apple Activity Monitor','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Activity Monitor','Apple Activity Monitor','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Address Book','Address Book - Contacts Manager','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Address Book','Address Book - Contacts Manager','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Adium','Apple Instant Messaging Client','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Adium','Apple Instant Messaging Client','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Adobe Reader','Adobe Acrobat Reader','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Adobe Reader','Adobe Acrobat Reader','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','AIM','AOL Instant Messenger','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','AIM','AOL Instant Messenger','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','AirPlay','Apple AirPlay','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','aLunch','aLunch - Launcher','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','aLunch','aLunch - Launcher','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','AppleVNCServer','Apple VNC Server','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','AppleVNCServer','Apple VNC Server','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','AppleWorks','AppleWorks','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','AppleWorks','AppleWorks','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Butler','Butler - Launcher','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Butler','Butler - Launcher','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Calculator','Apple Calculator','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Calculator','Apple Calculator','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Calculator DashboardClient','Calculator DashboardClient','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Calculator DashboardClient','Calculator DashboardClient','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Calendar DashboardClient','Calendar DashboardClient','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Calendar DashboardClient','Calendar DashboardClient','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Camtasia','Camtasia','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Camtasia','Camtasia','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Capture Me','CaptureMe','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Capture Me','CaptureMe','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','CaptureIt!','Captureit','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','CaptureIt!','Captureit','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Copernicus','Copernicus','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Copernicus','Copernicus','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','DashboardClient','Apple Dashboard and widgets','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','DashboardClient','Apple Dashboard and widgets','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Dictionary','Dictionary','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Dictionary','Dictionary','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Dictionary DashboardClient','Dictionary DashboardClient','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Dictionary DashboardClient','Dictionary DashboardClient','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','DragThing','DragThing - Launcher','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','DragThing','DragThing - Launcher','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Dropbox','DropBox File Sharing','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Dropbox','DropBox File Sharing','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Evernote','Evernote - File sharing and blogging','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Evernote','Evernote - File sharing and blogging','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','FaceTime','Facetime','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','FaceTime','Facetime','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Firefox','Firefox Web Browser','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Firefox','Firefox Web Browser','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','FirstClass','FirstClass','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','FirstClass','FirstClass','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Google Chrome','Google Chrome Web Browser','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Google Chrome','Google Chrome Web Browser','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Google DashboardClient','Google DashboardClient','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Google DashboardClient','Google DashboardClient','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Google Notifier','Gmail Desktop Alerts','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Google Notifier','Gmail Desktop Alerts','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Google Quick Search Box','Google Quick Search Box - Launcher','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Google Quick Search Box','Google Quick Search Box - Launcher','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Grab','Grab Screenshot Utility','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Grab','Grab Screenshot Utility','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Grapher','Grapher','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Grapher','Grapher','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','HimmelBar','HimmelBar - Launcher','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','HimmelBar','HimmelBar - Launcher','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','iCal','iCal - Calendar and Task Scheduling Utility','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','iCal','iCal - Calendar and Task Scheduling Utility','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','iChat','iChat - Internet Chat Utility','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','iChat','iChat - Internet Chat Utility','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Image Capture','Image Capture','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Image Capture','Image Capture','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','iMovie','iMovie - Movie Tool','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','iMovie','iMovie - Movie Tool','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','InstantShot!','Screen Capture Utility','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','InstantShot!','Screen Capture Utility','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Internet Explorer','Internet Explorer','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Internet Explorer','Internet Explorer','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','iPhoto','iPhoto - Photo Management Tool','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','iPhoto','iPhoto - Photo Management Tool','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','iShowU','iShowU','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','iShowU','iShowU','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','iSync','iSync - Desktop and Portable Device Sync Manager','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','iSync','iSync - Desktop and Portable Device Sync Manager','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','iTunes','iTunes Music Catalogue','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','iTunes','iTunes Music Catalogue','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','iTunes DashboardClient','iTunes DashboardClient','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','iTunes DashboardClient','iTunes DashboardClient','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Jing','Jing - Screenshot and Recording application','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Jing','Jing - Screenshot and Recording application','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Keynote','Mac Keynote - Spreadsheets','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Keynote','Mac Keynote - Spreadsheets','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','LaunchBar','LaunchBar - Launcher','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','LaunchBar','LaunchBar - Launcher','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','LittleSnap','LittleSnapper','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','LittleSnap','LittleSnapper','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Mail','Apple Email Client','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Mail','Apple Email Client','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Microsoft Excel','Microsoft Excel','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Microsoft Excel','Microsoft Excel','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Microsoft Outlook','Outlook','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Microsoft Outlook','Outlook','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Microsoft PowerPoint','Mac','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Microsoft PowerPoint','Mac','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Microsoft Word','Microsoft Word','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Microsoft Word','Microsoft Word','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Mozilla','Mozilla Web Browser','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Mozilla','Mozilla Web Browser','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Namely','Namely - Launcher','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Namely','Namely - Launcher','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Navigator','Netscape','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Navigator','Netscape','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','NeoOffice','NeoOffice Office Application','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','NeoOffice','NeoOffice Office Application','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Numbers','Mac Numbers - Spreadsheets','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Numbers','Mac Numbers - Spreadsheets','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','OpenOffice.org','Sun OpenOffice.org','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','OpenOffice.org','Sun OpenOffice.org','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','OpenOffice.org ','Open Office','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','OpenOffice.org ','Open Office','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Opera','Opera Web Browser','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Opera','Opera Web Browser','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','OSxVNC-server','OSX VNC Server','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','OSxVNC-server','OSX VNC Server','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Overflow','Overflow - Launcher','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Overflow','Overflow - Launcher','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Pages','Mac Pages - Word processing','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Pages','Mac Pages - Word processing','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Picasa','Google Picasa','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Picasa','Google Picasa','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Pidgin','Pidgin','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Pidgin','Pidgin','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Podcast Capture','Podcast Capture','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Podcast Capture','Podcast Capture','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Quicksilver','Quicksilver - Launcher','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Quicksilver','Quicksilver - Launcher','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','QuickTime Player','Apple Quicktime Player','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','RemoteDesktop DashboardClient','RemoteDesktop DashboardClient','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','RemoteDesktop DashboardClient','RemoteDesktop DashboardClient','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Safari','Safari Web Browser','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Safari','Safari Web Browser','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Screen Mimic','Screen Mimic','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Screen Mimic','Screen Mimic','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Screenflick','Screenflick','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Screenflick','Screenflick','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','ScreenFlow','ScreenFlow','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','ScreenFlow','ScreenFlow','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Screenium','Screenium','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Screenium','Screenium','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','ScreenRecord','ScreenRecord','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','ScreenRecord','ScreenRecord','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','SeaMonkey','Seamonkey Web Browser ','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','SeaMonkey','Seamonkey Web Browser ','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Sherlock','Sherlock - File and Web Search Tool','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Sherlock','Sherlock - File and Web Search Tool','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Skitch','Skitch','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Skitch','Skitch','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Skype','Skype Communication Client','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Skype','Skype Communication Client','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Snagit','Snagit','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Snagit','Snagit','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Snapz Pro X','Snapz Pro X','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Snapz Pro X','Snapz Pro X','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Stickies DashboardClient','Stickies DashboardClient','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Stickies DashboardClient','Stickies DashboardClient','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','System Preferences','Apple System Preferences','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','System Preferences','Apple System Preferences','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Team Speak 3','teamspeak','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Team Speak 3','teamspeak','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','TeamViewer','TeamViewer','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','TeamViewer','TeamViewer','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','TextEdit','Apple Text Editor','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','TextEdit','Apple Text Editor','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Thunderbird','Thunderbird Email Client','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Thunderbird','Thunderbird Email Client','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Tile Game DashboardClient','Tile Game DashboardClient','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Tile Game DashboardClient','Tile Game DashboardClient','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Translation DashboardClient','Translation DashboardClient','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Translation DashboardClient','Translation DashboardClient','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Trillian','Trillian Instant Messaging Client ','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Trillian','Trillian Instant Messaging Client ','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','TweetDeck','Social Networking Utility','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','TweetDeck','Social Networking Utility','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','twhirl','Social Networking Utility','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','twhirl','Social Networking Utility','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Unit Converter DashboardClient','Unit Converter DashboardClient','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Unit Converter DashboardClient','Unit Converter DashboardClient','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Valet','Valet - Launcher','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Valet','Valet - Launcher','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Ventrillo','ventrillo','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Ventrillo','ventrillo','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Vine Server','Apple Vine Server','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Vine Server','Apple Vine Server','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Weather DashboardClient','Weather DashboardClient','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Weather DashboardClient','Weather DashboardClient','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Widgets DashboardClient','Widgets DashboardClient ','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Widgets DashboardClient','Widgets DashboardClient ','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Windows Messenger','Microsoft Instant Messenger Client ','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Windows Messenger','Microsoft Instant Messenger Client ','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','World Clock DashboardClient','World Clock DashboardClient','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','World Clock DashboardClient','World Clock DashboardClient','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Yahoo! Messenger','Yahoo Instant Messenger ','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('OSX','Yahoo! Messenger','Yahoo Instant Messenger ','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','acrord32.exe','Adobe Acrobat Reader','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','acrord32.exe','Adobe Acrobat Reader','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','aim.exe','AOL Instant Messenger','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','aim.exe','AOL Instant Messenger','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','calc.exe','Windows Calculator','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','calc.exe','Windows Calculator','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','CamtasiaStudio.exe','Camtasia','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','CamtasiaStudio.exe','Camtasia','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','capture.exe','AnalogX Capture','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','capture.exe','AnalogX Capture','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','CaptureAssistant.exe','Capture Assistant','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','CaptureAssistant.exe','Capture Assistant','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','Captus.exe','Softario Captus','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','Captus.exe','Softario Captus','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','chrome.exe','Google Chrome Web browser','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','chrome.exe','Google Chrome Web browser','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','digsby.exe','Digsby Instant Messaging Client','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','digsby.exe','Digsby Instant Messaging Client','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','Dropbox.exe','DropBox File Sharing','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','Dropbox.exe','DropBox File Sharing','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','EXCEL.EXE','Microsoft Excel','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','EXCEL.EXE','Microsoft Excel','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','fcc32.exe','FirstClass','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','fcc32.exe','FirstClass','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','firefox.exe','Firefox Web Browser','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','firefox.exe','Firefox Web Browser','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','fscapture.exe','Faststone Screen Capture','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','fscapture.exe','Faststone Screen Capture','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','googletalk.exe','Google Talk Instant Messaging Client','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','googletalk.exe','Google Talk Instant Messaging Client','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','Greenshot.exe','Greenshot','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','Greenshot.exe','Greenshot','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','Groupwise.exe','Novell Groupwise E-mail Client','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','Groupwise.exe','Novell Groupwise E-mail Client','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','Grpwise.exe','Novell Groupwise E-mail Client','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','Grpwise.exe','Novell Groupwise E-mail Client','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','HprSnap7.exe','HyperSnap','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','HprSnap7.exe','HyperSnap','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','icq.exe','ICQ Instant Messaging Client','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','icq.exe','ICQ Instant Messaging Client','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','iexplore.exe','Internet Explorer Web Browser','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','iexplore.exe','Internet Explorer Web Browser','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','i_view32.exe','IrfanView','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','i_view32.exe','IrfanView','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','LaunchPad.exe','U3 Flash Drive','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','LaunchPad.exe','U3 Flash Drive','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','lightscreen.exe','Lightscreen','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','lightscreen.exe','Lightscreen','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','Lightshot.exe','Lightshot','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','Lightshot.exe','Lightshot','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','msconfig.exe','Microsoft Windows Configuration Utility','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','msconfig.exe','Microsoft Windows Configuration Utility','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','msimn.exe','Microsoft Outlook Express','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','msimn.exe','Microsoft Outlook Express','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','msmsgs.exe','Windows Live Messenger','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','msmsgs.exe','Windows Live Messenger','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','msnmsgr.exe','MSN Messenger','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','msnmsgr.exe','MSN Messenger','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','mspaint.exe','Microsoft Paint','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','mspaint.exe','Microsoft Paint','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','mstsc.exe','Microsoft Remote Desktop','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','mstsc.exe','Microsoft Remote Desktop','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','MWSnap.exe','MWSnap','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','MWSnap.exe','MWSnap','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','navigator.exe','Netscape','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','navigator.exe','Netscape','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','nlnotes.exe','Lotus Notes','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','nlnotes.exe','Lotus Notes','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','notepad.exe','Windows Notepad','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','notepad.exe','Windows Notepad','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','notes2w.exe','Lotus Notes','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','notes2w.exe','Lotus Notes','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','onenote.exe','Microsoft OneNote','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','onenote.exe','Microsoft OneNote','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','opera.exe','Opera Web Browser','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','opera.exe','Opera Web Browser','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','OUTLOOK.EXE','Outlook Mail Client','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','OUTLOOK.EXE','Outlook Mail Client','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','picpick.exe','PicPick Screenshot App','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','picpick.exe','PicPick Screenshot App','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','pidgin.exe','Pidgin','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','pidgin.exe','Pidgin','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','powerpnt.exe','Microsoft Powerpoint','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','powerpnt.exe','Microsoft Powerpoint','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','Printkey2000.exe','PrintKey 2000','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','Printkey2000.exe','PrintKey 2000','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','procexp.exe','Process Explorer','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','procexp.exe','Process Explorer','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','psr.exe','Windows Problem Steps Recorder','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','psr.exe','Windows Problem Steps Recorder','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','quicktimeplayer.exe','Apple Quicktime Player','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','quicktimeplayer.exe','Apple Quicktime Player','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','Safari.exe','Safari Web Browser','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','Safari.exe','Safari Web Browser','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','screenshot.exe','Screenshot Application','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','screenshot.exe','Screenshot Application','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','scrybe.exe','Scrybe (Synaptics)','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','scrybe.exe','Scrybe (Synaptics)','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','seamonkey.exe','Seamonkey Web Browser','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','seamonkey.exe','Seamonkey Web Browser','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','skype.exe','Skype','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','skype.exe','Skype','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','Snagit32.exe','Snagit','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','Snagit32.exe','Snagit','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','SnippingTool.exe','Snipping Tool','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','SnippingTool.exe','Snipping Tool','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','soffice.exe','OpenOffice.org - Sun OpenOffice','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','soffice.exe','OpenOffice.org - Sun OpenOffice','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','SoundRecorder.exe','Sound Recorder','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','SoundRecorder.exe','Sound Recorder','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','StikyNot.exe','Sticky Notes','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','StikyNot.exe','Sticky Notes','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','taskmgr.exe','Windows Task Manager','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','taskmgr.exe','Windows Task Manager','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','TeamViewer.exe','TeamViewer','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','TeamViewer.exe','TeamViewer','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','thunderbird.exe','Thunderbird','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','thunderbird.exe','Thunderbird','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','trillian.exe','Trillian Instant Messaging Client','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','trillian.exe','Trillian Instant Messaging Client','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','ts3_client_win64.exe','teamspeak','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','ts3_client_win64.exe','teamspeak','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','Ventrillo.exe','ventrillo','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','Ventrillo.exe','ventrillo','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','VirtualBox.exe','Virtual Box','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','VirtualBox.exe','Virtual Box','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','VVCap.exe','VVCap','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','VVCap.exe','VVCap','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','WindowClipping.exe','Window Clippings','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','WindowClipping.exe','Window Clippings','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','wink.exe','Wink','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','wink.exe','Wink','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','WinMail.exe','Windows Mail Client','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','WinMail.exe','Windows Mail Client','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','winword.exe','Microsoft Word','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','winword.exe','Microsoft Word','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','wmplayer.exe','Windows Media Player','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','wmplayer.exe','Windows Media Player','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','wordpad.exe','Windows Wordpad','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','wordpad.exe','Windows Wordpad','SBAC_PT');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','yahoomessenger.exe','Yahoo Instant Messenger','SBAC');
INSERT INTO `client_forbiddenappslist` VALUES ('Windows','yahoomessenger.exe','Yahoo Instant Messenger','SBAC_PT');
/*!40000 ALTER TABLE `client_forbiddenappslist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `client_messagetranslation`
--

LOCK TABLES `client_messagetranslation` WRITE;
/*!40000 ALTER TABLE `client_messagetranslation` DISABLE KEYS */;
INSERT INTO `client_messagetranslation` VALUES (3636,'AIR','Pantalla diagnóstica','ESN','--ANY--','--ANY--','\0	�KӲGV���z�o�','2014-03-22 20:36:39.597');
INSERT INTO `client_messagetranslation` VALUES (3725,'AIR','?A?ole ho?okomo ?ia ka polokalamu no ke ?oki leo','HAW','--ANY--','--ANY--','\0��q�@/�,��F��]','2013-12-18 14:09:07.660');
INSERT INTO `client_messagetranslation` VALUES (3230,'SBAC','<h2>Smarter Balanced Assessments Secure Test Material</h2>  <br />  <p><strong>This printout contains secure test content and must be immediately and securely recycled on-site by authorized school staff.</strong></p><p>Do not photocopy, enlarge, digitize, or  retain printouts of test content. Such actions constitute a severe test security violation and must be reported to Smarter Balanced.</p>  <p>In accordance with the Family Educational Rights and Privacy Act (FERPA), disclosure of personally identifiable information protected by privacy laws is prohibited. Please securely destroy all printed test content immediately after the end of this testing session.</p>','ENU','--ANY--','--ANY--','\0\"vU�D���ƿ�ξS','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3632,'AIR','Ho?onohonoho kani','HAW','--ANY--','--ANY--','\0$�#.HCD�UZ��:0','2013-12-18 14:09:07.523');
INSERT INTO `client_messagetranslation` VALUES (3200,'SBAC','Navigate to Another Smarter Balanced Assessment System','ENU','--ANY--','--ANY--','\0<a�0(G���օ}Dx�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3443,'AIR','Makemake a nei ?oe e waiho i keia ho?ike no ka helu ?ai ?ia ?ana?','HAW','--ANY--','--ANY--','\0H�F׊��I<i','2013-12-18 14:09:07.253');
INSERT INTO `client_messagetranslation` VALUES (3155,'AIR','No cumples con los requisitos para comenzar esta prueba en esta ventana.','ESN','--ANY--','--ANY--','\0��<�uF�F1\"��D�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3071,'AIR','Su prueba se ha interrumpido. Para continuar la prueba, consulte con su Administrador de Pruebas.','ESN','--ANY--','--ANY--','\0����O�b�X�\'��','2014-03-22 20:33:38.147');
INSERT INTO `client_messagetranslation` VALUES (3121,'AIR','A?ole pono ka ?apono ?ia ?ana no ka hana ?ana i keia ho?ike nei.Ua kapae ?ia paha ka noi e ka haumana.','HAW','--ANY--','--ANY--','\0�zC45FN�Q��AP�','2013-12-18 14:09:06.940');
INSERT INTO `client_messagetranslation` VALUES (2417,'AIR','Nombre de la prueba','ESN','--ANY--','--ANY--','���#H��:�\r��\"','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2537,'AIR','E Malama','HAW','--ANY--','--ANY--','���gI��\n��|x#�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2451,'AIR','<span>Intentar nuevamente</span>','ESN','--ANY--','--ANY--','@�e��M�/�s�B�S','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2959,'AIR','Mai ho?ike?ike i ka helu ?ai i ka haumana','HAW','--ANY--','--ANY--','�*��M���J �','2013-12-18 14:09:06.780');
INSERT INTO `client_messagetranslation` VALUES (2356,'AIR','../tools/periodic/2010/hi_11_science.html','HAW','--ANY--','--ANY--','*���A��uq!q','2013-12-18 14:09:06.630');
INSERT INTO `client_messagetranslation` VALUES (3036,'AIR','Se produjo un problema con el sistema.  Proporcione este número al AP.','ESN','--ANY--','--ANY--','1�å�D����(��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3025,'AIR','Se produjo un problema con el sistema.  Proporcione este número al AP.','ESN','--ANY--','--ANY--','Z��C���`-\r�*','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2620,'AIR','¿Está seguro de que desea cambiar el tema que seleccionó anteriormente? <Sí> <No>','ESN','--ANY--','--ANY--','hn�A8�\0;6SI�V','2014-03-22 20:33:38.127');
INSERT INTO `client_messagetranslation` VALUES (2404,'AIR','Escuela:','ESN','--ANY--','--ANY--','�Bn,%D���������','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3406,'AIR','O  su nombre o su ID de estudiante no se ingresó correctamente. Inténtelo nuevamente o consulte con el administrador de la prueba.','ESN','--ANY--','--ANY--','��tQAŐ@�Ǉo','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3479,'AIR','Na ?apono ','HAW','--ANY--','--ANY--','��Z�(G �ަO�`o1','2013-12-18 14:09:07.310');
INSERT INTO `client_messagetranslation` VALUES (2683,'AIR','No ','ESN','--ANY--','--ANY--','�]�(eC�1��\r�S�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2514,'AIR','Aia he pilikia me ka ‘ōnaehana. E ‘olu‘olu, e hā‘awi ‘oe i kēia helu i kāu kumu.','HAW','--ANY--','--ANY--','�l�(�Lo��:�ȱ�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2943,'AIR','?A?ohe','HAW','--ANY--','--ANY--','��r�E��Ĺ[��U>','2013-12-18 14:09:06.760');
INSERT INTO `client_messagetranslation` VALUES (2776,'AIR','POLOLEI','HAW','--ANY--','--ANY--','�-�Np�UUTҵ�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2644,'AIR','E ho‘olohe ma ka ‘ōlelo Paniolo','HAW','--ANY--','--ANY--','Q*4�H-��fg�v�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2568,'AIR','E kahiauli i ka Mahele i koho \'ia','HAW','--ANY--','--ANY--','a�\Z�EL<�Ty_�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3152,'AIR','A\'ole hiki iā \'oe ke hana i kēia hō\'ike ma muli o ka palapala mākua hō\'ole hō\'ike.','HAW','--ANY--','--ANY--','l%�D�I��}`Q��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2778,'AIR','Instrucciones','ESN','--ANY--','--ANY--','|��!%A_��Y�P5','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2544,'AIR','E lele \'oe','HAW','--ANY--','--ANY--','�U��VD؊��Ńh�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3617,'AIR','?Olu?olu e kali a pau ka holo ?ana o ka ho?okukohukohu ma mua o ka ho?omau ?ana.','HAW','--ANY--','--ANY--','��FO���#��Ɨ�','2013-12-18 14:09:07.500');
INSERT INTO `client_messagetranslation` VALUES (2364,'AIR','E \'olu\'olu \'oe, e kikokiko i ka helu mahele ho\'ike a ke kumu ho\'ike i ha\'awi aku ai ia \'oe.','HAW','--ANY--','--ANY--','�6<��Gʥ*V��_','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2668,'AIR','Ua pau ka ho‘ike a makaukau no ka waiho ‘ana.','HAW','--ANY--','--ANY--','�PG�A<�C�9ڬR\"','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3318,'AIR','Ho?ole ke kahua FERPA i ka ho?oku?u ?ia na ho?ike pilikino o na mea ho?ohana.Ho?ole ?ia ke ka?ana like ?ia ?ana o na ho?ike pilikino Excel o na haumana ma ke kamepiula, ka leka uila a i ?ole e malama ?ia ma kekahi kamepiula i hiki ai i kekahi ke ho?ohana ','HAW','--ANY--','--ANY--','�$k��Dᕧ�-��0','2013-12-18 14:09:07.133');
INSERT INTO `client_messagetranslation` VALUES (3420,'AIR','O keia ka ho?ike pololei?','HAW','--ANY--','--ANY--','��n;�H[�k�:�q�|','2013-12-18 14:09:07.213');
INSERT INTO `client_messagetranslation` VALUES (3606,'SBAC','Technology-Enhanced','ENU','--ANY--','--ANY--','ֲ7�2O]�$����y','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2679,'AIR','Texto a voz','ESN','--ANY--','--ANY--','�-I�\"��_�+^','2014-03-22 20:33:38.137');
INSERT INTO `client_messagetranslation` VALUES (2649,'AIR','Pilikia ka TTS. E ha‘i i kāu kumu.','HAW','--ANY--','--ANY--','4�Dm��l�)b','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2967,'AIR','../tools/formulas/2010/mn_7_math.html','HAW','--ANY--','--ANY--','��4G�m7]�o�','2013-12-18 14:09:06.793');
INSERT INTO `client_messagetranslation` VALUES (2743,'AIR','HI 10 Makemakika','HAW','--ANY--','--ANY--','?\n�C�[@d�?a�','2013-12-18 14:09:06.653');
INSERT INTO `client_messagetranslation` VALUES (2417,'AIR','Ka Inoa o Ka Ho\'ike:','HAW','--ANY--','--ANY--','*K��O>�4��_ ʿ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2976,'AIR','Acceso denegado','ESN','--ANY--','--ANY--','-�8�O�����\'+��','2014-03-22 20:34:47.200');
INSERT INTO `client_messagetranslation` VALUES (2915,'AIR','Pono e ho\'oia\'i\'o i ka mea ho\'ohana ma mua o ka pane \'ana i kana noi. E ‘olu‘olu, e kainoa hou.','HAW','--ANY--','--ANY--','0$��eB��#��Qn�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2892,'AIR','ID de sesión:','ESN','--ANY--','--ANY--','[-?�K��;-�\\v��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2626,'AIR','Seleccione el objeto que desee eliminar.','ESN','--ANY--','--ANY--','`$�WF��H��[R','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2873,'AIR','Cerrar Comentario','ESN','--ANY--','--ANY--','`7��)J��K�E','2014-03-22 20:34:28.063');
INSERT INTO `client_messagetranslation` VALUES (3667,'AIR','Compruebe de texto a voz','ESN','--ANY--','--ANY--','n�_P�J:�[wy��Ln','2014-03-22 20:36:39.837');
INSERT INTO `client_messagetranslation` VALUES (2456,'AIR','Ke kali nei i ka \'apono \'ana o ke kumu ho\'ike...','HAW','--ANY--','--ANY--','�u0�}K��U�ߑ޻�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3472,'AIR','Helu ID o ka wa ho?ohana','HAW','--ANY--','--ANY--','����Oc��^��D�','2013-12-18 14:09:07.300');
INSERT INTO `client_messagetranslation` VALUES (2901,'AIR','Error al cargar la calculadora. Entregue este código a su administrador de pruebas.','ESN','--ANY--','--ANY--','�$���@k����mb�','2014-03-22 20:33:38.140');
INSERT INTO `client_messagetranslation` VALUES (2534,'AIR','E Pa\'i','HAW','--ANY--','--ANY--','ԯ�\"D�gҥ°^�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3143,'AIR','Lamentablemente, no tiene permiso para acceder a este sistema.  Proporcione este número al administrador de la prueba.','ESN','--ANY--','--ANY--',',(�O��\rO��^�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2836,'AIR','Someter un comentario para este ítem.','ESN','--ANY--','--ANY--','K����E����B�n�','2014-03-22 20:34:06.410');
INSERT INTO `client_messagetranslation` VALUES (2590,'AIR','Pregunta oral (en inglés)','ESN','--ANY--','--ANY--','�kpB�\'9�O�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3621,'AIR','No se puede regresar a esta parte de la prueba. ¿Está seguro que desea continuar?','ESN','--ANY--','--ANY--','Š���DٿE�_���','2014-03-22 20:36:39.530');
INSERT INTO `client_messagetranslation` VALUES (3455,'SBAC','<span style=\"display:none\"><a href=\"https://sbac.tds.airast.org/dataentry\">Go to Data Entry Interface instead.</a></span>','ENU','--ANY--','--ANY--','��\'cG�����Vp��','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2888,'AIR','ua maka \'ia','HAW','--ANY--','--ANY--','\r���N���\n�y�D','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2442,'AIR','Comenzar/detener grabación','ESN','--ANY--','--ANY--','�Z�O��QS��P','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2771,'AIR','POLOLEI ʻOLE','HAW','--ANY--','--ANY--','*h��D|�8���_','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2374,'AIR','Su solicitud de impresión se ha enviado al administrador de la prueba.','ESN','--ANY--','--ANY--','��/Fg��8��^��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3634,'AIR','Seleccione un paquete de voz','ESN','--ANY--','--ANY--','�%�MJ���iJ�3�','2014-03-22 20:36:39.580');
INSERT INTO `client_messagetranslation` VALUES (2869,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','��]�O�\"�\r��_�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2811,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--',' ��ƇK�x�#rX�t','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2690,'AIR','Ka Mikini Helu','HAW','--ANY--','--ANY--','f��HyJ���Jc̈́p','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2497,'SBAC_PT','<div class=\"blurb\"><a target=\"_self\" href=\"https://capt.cloud1.tds.airast.org/student\">Click here if you are a California student</a></p></div>','ENU','--ANY--','--ANY--','�d��?A|���O�{�','2014-03-31 21:59:52.000');
INSERT INTO `client_messagetranslation` VALUES (2807,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','��7�iD�iODm�|Q','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2546,'AIR','Espere...','ESN','--ANY--','--ANY--','�4B�aCy�r����W?','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3164,'AIR','Pono ka ID wa ho?ohana.','HAW','--ANY--','--ANY--','�_Ŧ�F��N��#���','2013-12-18 14:09:06.970');
INSERT INTO `client_messagetranslation` VALUES (2468,'AIR','E kaomi i \'ane\'i no ka ho\'i \'ana i ka papakaumaka kainoa','HAW','--ANY--','--ANY--','�`kE�L:���Rȉ%','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2918,'AIR','Seleccione el texto que desea escuchar y haga clic en el botón verde para reproducirlo.','ESN','--ANY--','--ANY--','��>�O��������','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3156,'AIR','?A?ohe pukaaniani ho?ike i hemo no keia haumana i keia manawa.','HAW','--ANY--','--ANY--','ꯇ�C����G�\"�i','2013-12-18 14:09:06.960');
INSERT INTO `client_messagetranslation` VALUES (2361,'AIR','Cerrar','ESN','--ANY--','--ANY--','6	�ADE��9�-2��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2940,'AIR','?A?ole hiki','HAW','--ANY--','--ANY--','^��dD����y6a','2013-12-18 14:09:06.753');
INSERT INTO `client_messagetranslation` VALUES (2921,'AIR','<span class=\"noTTS\">ʻAʻohe Kākau-i-ka-ʻŌlelo Wahe</span>','HAW','--ANY--','--ANY--','~ٖ��A��0ë4/:','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2432,'AIR','Se necesita Flash {0} o una versión superior para realizar esta prueba. Consulte a su administrador de pruebas.','ESN','--ANY--','--ANY--','�a��F�e�p.\'~','2014-03-22 20:33:38.103');
INSERT INTO `client_messagetranslation` VALUES (2680,'AIR','Sin texto a voz','ESN','--ANY--','--ANY--','�Y�G�Av����oyg','2014-03-22 20:33:38.137');
INSERT INTO `client_messagetranslation` VALUES (2847,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','��[J:@O�\Z�lp)��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2911,'AIR','‘A‘ole leo maika‘i no ka ‘ōlelo Haole i kēia kamepiula.','HAW','--ANY--','--ANY--','	hb��Dݩj�v���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3047,'AIR','Hiki ?ole ke komo ka mea ho?ohana ma muli o kona kulala','HAW','--ANY--','--ANY--','	9n�~L��G��z��','2013-12-18 14:09:06.870');
INSERT INTO `client_messagetranslation` VALUES (3035,'AIR','Aia he pilikia me ka ‘ōnaehana. E ‘olu’olu e hā’awi i kēia helu iā Kumu','HAW','--ANY--','--ANY--','	N�Q�OF�tĶ*VM','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3297,'AIR','Hewa ka mahina palena pau (Ho?ohana i ka ?ano ho?ala MM)','HAW','--ANY--','--ANY--','	�e�PA���Q�Ų�y','2013-12-18 14:09:07.110');
INSERT INTO `client_messagetranslation` VALUES (3467,'AIR','Pono ka ?olelo huna','HAW','--ANY--','--ANY--','	��xtD��~��E�','2013-12-18 14:09:07.290');
INSERT INTO `client_messagetranslation` VALUES (2771,'AIR','FALSO','ESN','--ANY--','--ANY--','	���a�MM��ˍ��.','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2750,'AIR','POLOLEI','HAW','--ANY--','--ANY--','	�QPewI�1��u��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2829,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','	���=KI�6�����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3111,'AIR','No hay oportunidad de prueba coincide con el ID de informes.','ESN','--ANY--','--ANY--','	�7T�Ag�nUE9�=','2014-03-22 20:35:48.653');
INSERT INTO `client_messagetranslation` VALUES (2657,'SBAC','Conectar línea','ESN','--ANY--','--ANY--','\n:���Ns� �j�;\0�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3237,'AIR','Nalowale ana na kuhikuhi i ho?onohonoho ?ia no na haumana e kali nei.Makemake a nei ?oe e ho?omau aku?','HAW','--ANY--','--ANY--','\n=g��Jr��r,�·�','2013-12-18 14:09:07.027');
INSERT INTO `client_messagetranslation` VALUES (2685,'AIR','Espere…','ESN','--ANY--','--ANY--','\nAΰj�I���G\n\\zϬ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3023,'AIR','<p>?A?ole ?oe i ho?okomo i pane no kekahi a i ?ole ka nui o na ?ikemu ho?ike no na kula ma keia ?ao?ao nei. </p>     <p>Ina pololei keia, e kaomi i ka pihi [?Ae] no ka ne?e ?ana i kekahi ?ao?ao a?e. </p>     <p>Ina ?a?ole ?oe makemake, e kaomi i ka pihi [Ho?ole] no ka noho ?ana ma keia ?ao?ao nei.</p>','HAW','--ANY--','--ANY--','\nB᭭�K��hc�E�','2013-12-18 14:09:06.853');
INSERT INTO `client_messagetranslation` VALUES (2907,'AIR','E Malama','HAW','--ANY--','--ANY--','\nW�\\�@��>f�Ӌ�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3651,'AIR','Diagnósticos de la red:','ESN','--ANY--','--ANY--','\n_E��Bۄ*w	h�8�','2014-03-22 20:36:39.703');
INSERT INTO `client_messagetranslation` VALUES (3137,'AIR','Se produjo un problema con el sistema.  Proporcione este número al administrador de la prueba.','ESN','--ANY--','--ANY--','\n���Dˆho�(o��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2644,'AIR','Haga clic para escuchar en español','ESN','--ANY--','--ANY--','\nÅQrA������o�','2014-03-22 20:33:38.130');
INSERT INTO `client_messagetranslation` VALUES (2393,'AIR','Sesión de invitado','ESN','--ANY--','--ANY--','\n�P�bF\r�λ�Qn�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3484,'SBAC','Field Test','ENU','--ANY--','--ANY--','\n��8�I��F>{\"2','2014-03-11 12:35:15.643');
INSERT INTO `client_messagetranslation` VALUES (2604,'AIR','Texto oral (en inglés)','ESN','--ANY--','--ANY--','\n��M�\0@���@�Q���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2442,'AIR','Hoʻomaka/Kū I Ke ʻOki Leo','HAW','--ANY--','--ANY--','��vNJ�q|�bTk�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2439,'AIR','<span>ʻAe</span>','HAW','--ANY--','--ANY--','	��2A��e}D��!','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3541,'AIR','2.Na mea i huli ?ia','HAW','--ANY--','--ANY--','��hN�����v��','2013-12-18 14:09:07.400');
INSERT INTO `client_messagetranslation` VALUES (3555,'AIR','Kuhikuhi Ho?onohonoho','HAW','--ANY--','--ANY--','k�.(G���\r��YQ�','2013-12-18 14:09:07.420');
INSERT INTO `client_messagetranslation` VALUES (3034,'AIR','Aia he pilikia me ka ‘ōnaehana. E ‘olu’olu e hā’awi i kēia helu iā Kumu','HAW','--ANY--','--ANY--','�#�%�Ga�|���(2','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2539,'AIR','Volver','ESN','--ANY--','--ANY--','���q@�τ>d��o','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2714,'AIR','Gráfico','ESN','--ANY--','--ANY--','��0G��.JU��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2372,'AIR','Makemake anei \'oe e ho\'omaha ma keia ho\'ike? Ina oe e ho\'omaha no 20 minuke, \'a\'ole hiki ia\'oe ke ho\'ololi i na pane o na ninau i pane \'ia. E \'olu\'olu \'oe, e ninau i ke Kumu Ho\'ike ma mua o kou ho\'omaha \'ana ma kau ho‘ike.','HAW','--ANY--','--ANY--','�)HA��\Z0�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3005,'AIR','El navegador seguro equivocado está instalado en este equipo. Informe a su administrador de pruebas. Por ahora, tiene que utilizar otro ordenador.','ESN','--ANY--','--ANY--','�j���J΂G�%��+','2014-03-22 20:35:11.283');
INSERT INTO `client_messagetranslation` VALUES (3253,'AIR','Kaila hua palapala','HAW','--ANY--','--ANY--','&5�@I��v��\n�Hl','2013-12-18 14:09:07.043');
INSERT INTO `client_messagetranslation` VALUES (2445,'AIR','<ol> <li>Presione el botón Micrófono para iniciar la grabación. </li> <li>Diga su nombre por el micrófono. </li> <li>Cuando haya terminado, pulse el botón Stop. </li> <li>Luego presione el botón de reproducción para escuchar la grabacion.</li><li>Si escucha su voz, haga clic en  [Sí]. De lo contrario, haga clic en [Problema].</li></ol>','ESN','--ANY--','--ANY--','.���Jհ7!�2�','2014-03-22 20:33:38.107');
INSERT INTO `client_messagetranslation` VALUES (3309,'SBAC','<h3>2. Select the Time (PT) that the Alert will Expire</h3>','ENU','--ANY--','--ANY--','5g;�E��� u�g�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3404,'AIR','Ho?opau ?ia keia la','HAW','--ANY--','--ANY--','u�#?3L���a���}u','2013-12-18 14:09:07.190');
INSERT INTO `client_messagetranslation` VALUES (3682,'AIR','Pio na ha?awina ukali','HAW','--ANY--','--ANY--','\r1Ck�H���I���','2013-12-18 14:09:07.600');
INSERT INTO `client_messagetranslation` VALUES (2838,'AIR','Cerrar Comentario','ESN','--ANY--','--ANY--','\rNE��*B\r�s<~���\n','2014-03-22 20:34:06.420');
INSERT INTO `client_messagetranslation` VALUES (3043,'AIR','?A?ole hiki ke laka ?ia keia polokalamu nei','HAW','--ANY--','--ANY--','\rZ-̼zN�����,�l9','2013-12-18 14:09:06.863');
INSERT INTO `client_messagetranslation` VALUES (2864,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','\rn�#�)Fo�P\'��O�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2647,'AIR','Omitir la verificación de audio en español.','ESN','--ANY--','--ANY--','\r�,�\"KJ`�\Z��E2G','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3010,'SBAC','The web browser you are using was recently released and the American Institutes for Research has not finished testing its compatibility with this system. You may take tests using this browser if you choose to do so; however, there is a possibility that some features may not function correctly.','ENU','--ANY--','--ANY--','\r����O?�e��l�O','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3262,'AIR','Pa?i i ke kauoha ?ia ?ana','HAW','--ANY--','--ANY--','\r��,TO�GΔ+�I','2013-12-18 14:09:07.057');
INSERT INTO `client_messagetranslation` VALUES (3080,'AIR','No se encuentra la base de datos RTS','ESN','--ANY--','--ANY--','\r��2O����vv~�','2014-03-22 20:35:32.333');
INSERT INTO `client_messagetranslation` VALUES (3628,'AIR','Oportunidad {0} de {1}','ESN','--ANY--','--ANY--','\r�ޣ?�G��\r\0�31','2014-03-22 20:36:39.547');
INSERT INTO `client_messagetranslation` VALUES (2714,'AIR','E hana i pakuhi','HAW','--ANY--','--ANY--','De�A�O��B��o*�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3636,'AIR','Pukaaniani ho?ike hoa?o','HAW','--ANY--','--ANY--','e��fE��9�!�.','2013-12-18 14:09:07.530');
INSERT INTO `client_messagetranslation` VALUES (2365,'SBAC','Your first name and State-SSID do not match current records. Please try again or ask your Test Administrator for help.','ENU','--ANY--','--ANY--','�$DH�L.��.�J~A�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3660,'AIR','Cargando …','ESN','--ANY--','--ANY--','����FOQ��Q��2','2014-03-22 20:36:39.777');
INSERT INTO `client_messagetranslation` VALUES (2366,'AIR','A\'ole hiki ia \'oe ke \'e\'e me keia polokalamu kele punaewele. E ho\'ohana i ka Polokalamu Kele Punaewele Pa\'a Hou no ka hana \'ana i ka ho\'ike.','HAW','--ANY--','--ANY--','���1@�����%��Q','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2767,'AIR','Básico','ESN','--ANY--','--ANY--','����Jۯ�M�h�]�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3604,'AIR','Constructor de palabras','ESN','--ANY--','--ANY--','����CI���M!jݏ','2014-03-22 20:36:39.417');
INSERT INTO `client_messagetranslation` VALUES (3084,'AIR','No se encuentra la base de datos RTS','ESN','--ANY--','--ANY--','���lEIl�\\=*�>z','2014-03-22 20:35:32.337');
INSERT INTO `client_messagetranslation` VALUES (2467,'AIR','He mana\'o mai kau Kumu Ho\'ike:','HAW','--ANY--','--ANY--','%7N�dA��,���@','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2419,'AIR','Acción','ESN','--ANY--','--ANY--','[T^.�D\Z��{q��8;','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3415,'AIR','Ka inoa mua o ka haumana:','HAW','--ANY--','--ANY--','nņRON�\\�S�0eK','2013-12-18 14:09:07.203');
INSERT INTO `client_messagetranslation` VALUES (2403,'AIR','Cumpleaños:','ESN','--ANY--','--ANY--','}��e�LY���eذOv','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2474,'AIR','Cerrar','ESN','--ANY--','--ANY--','��BB।��ö/\n','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2900,'AIR','Makemake anei \'oe e ho\'ololi i ka mana\'o i koho mua \'ia?','HAW','--ANY--','--ANY--','����IAÍa��:�U','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2580,'AIR','Aia he pilikia me kāu noi pa‘i. E ‘olu‘olu, e hana ‘oe a i ‘ole e nīnau i kāu kumu.','HAW','--ANY--','--ANY--','�\'9:D\Z����9','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2360,'AIR','Ka mikini helu','HAW','--ANY--','--ANY--','�v7��C\Z����9�6','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2612,'AIR','Pregunta','ESN','--ANY--','--ANY--','��v+�O[��� qj�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3305,'AIR','Hiki ?ole ke ho?ouna ?ia kau mau la palena pau a me ka manawa palena pau ma mua o ka ho?opa?a ?ia ?ana o na la a me na manawa.','HAW','--ANY--','--ANY--','�nh�AF���=�D','2013-12-18 14:09:07.120');
INSERT INTO `client_messagetranslation` VALUES (2525,'AIR','E \'olu\'olu, e kali. Ke ho\'opololei \'ia nei kau ho\'ike. ','HAW','--ANY--','--ANY--','\'ļ+AD8�������J','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3543,'AIR','Ka pae','HAW','--ANY--','--ANY--','���{PA�Y9����','2013-12-18 14:09:07.403');
INSERT INTO `client_messagetranslation` VALUES (3287,'SBAC_PT','TA Training Site','ENU','--ANY--','--ANY--','�駔@N%��5��','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2726,'AIR','Ula Maku\'e Halakea','HAW','--ANY--','--ANY--','��40A��h����$�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3333,'AIR','<h2>Na ka?ao</h2><p><strong>Ka lo?ihi</strong> a ke kahua e lawe ai no ke kukulu ?ana i kekahi noi.</p><p>Ka lo?ihi a ka polokalamu <strong>punaewele e lawe ai</strong> no ke kukulu ?ana i ka noi ?ana no kekahi pu?ulu ?ikemu mai na koho ho?oma?a alokima (algorithm) (komo pu na ho?opa?alua/ho?okala pa?alua a pau)</p><p>Ka lo?ihi a ka polokalamu <strong>punaewele e lawe ai no ka noi</strong> i noi mua ?ia no na koho pu?ulu ?ikemu no ka nana hou ?ana (me na SSL)</p><p>Ka lo?ihi a ka polokalamu <strong>kikowaena punaewele e lawe ai no ka ho?opau ?ana</strong> i na noi o na koho ?ikemu. (?A?ole komo pu na ho?opa?alua/Ho?okala pa?alua o SSL)</p><p>Ka lo?ihi a ka polokalamu <strong> kikowaena punaewele e lawe ai no</strong> ka noi i noi mua ?ia no na koho pu?ulu ?ikemu no ka nana hou ?ana (me ka loa?a ?ole o na SSL)</p><p>Na manawa ma ka <strong>li?ili?i loa, ka nui loa</strong> a me ka manawa (i na hapa kaukani kekona) like no ke kukulu ?ana i kekahi noi </p><p><strong>dbmin/dbmax/dbmean</strong> Ka manawa a ke kahua e lawe ai no ka manawa holo?oko?a e lilo ai i ke kukulu ?ana i ka ?ikepili ho?ike ho?ohaku </p>','HAW','--ANY--','--ANY--','�\n/ڸO?����','2013-12-18 14:09:07.140');
INSERT INTO `client_messagetranslation` VALUES (2763,'AIR','ʻAʻohe','HAW','--ANY--','--ANY--','�A�Bn�:A6��T�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2889,'AIR','E koho i na mea pono\'i','HAW','--ANY--','--ANY--','���hB�;�2�\\:�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2942,'AIR','Estímulo','ESN','--ANY--','--ANY--','���Ỏ��*z','2014-03-22 20:34:47.190');
INSERT INTO `client_messagetranslation` VALUES (3215,'AIR','Pono ?oe e ho?okomo i ka inoa/helu SSID pololei.','HAW','--ANY--','--ANY--','P�B�6Iǵ�@W\0~��','2013-12-18 14:09:07.010');
INSERT INTO `client_messagetranslation` VALUES (2578,'AIR','Atención','ESN','--ANY--','--ANY--','Qv��I��`U��P�M','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3702,'AIR','{silence}Koho C.{silence}','HAW','--ANY--','--ANY--','Y��lO���$\0��\'�','2013-12-18 14:09:07.623');
INSERT INTO `client_messagetranslation` VALUES (2578,'AIR','E Nana','HAW','--ANY--','--ANY--','l�簰B<�#]_�|\\R','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2349,'AIR','../tools/formulas/2010/or_3_5_math_esn.html','ESN','--ANY--','--ANY--','��`�F��EA��-','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2933,'AIR','Tipo de solicitud de relieve','ESN','--ANY--','--ANY--','�9��M\n���4��','2014-03-22 20:34:28.137');
INSERT INTO `client_messagetranslation` VALUES (2684,'AIR','Sí','ESN','--ANY--','--ANY--','O�H>E���4��{��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2524,'AIR','<span>E Ho\'ouna Hō\'ike No Ka Helu \'Ai</span>','HAW','--ANY--','--ANY--','m\"�΂H��ϐ�����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3158,'AIR','Hiki ?ole ke ho?opau ?ia keia koho palapala ho?ike','HAW','--ANY--','--ANY--','�c�L��u����','2013-12-18 14:09:06.963');
INSERT INTO `client_messagetranslation` VALUES (3646,'AIR','Preferencias de navegador:','ESN','--ANY--','--ANY--','�E�H��PUn��u�','2014-03-22 20:36:39.663');
INSERT INTO `client_messagetranslation` VALUES (3569,'AIR','Huli kuikawa','HAW','--ANY--','--ANY--','�7iVEd�3G�(�1V','2013-12-18 14:09:07.443');
INSERT INTO `client_messagetranslation` VALUES (3707,'SBAC','{silence} B.{silence}','ENU','--ANY--','--ANY--','ׄ�F�.S�TH�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2664,'AIR','Interfaz del Estudiante','ESN','--ANY--','--ANY--','�,&F�����y','2014-03-22 20:33:38.133');
INSERT INTO `client_messagetranslation` VALUES (2897,'AIR','Contraer todos los ejercisios.','ESN','--ANY--','--ANY--','��K��<�Q�g�','2014-03-22 20:33:38.140');
INSERT INTO `client_messagetranslation` VALUES (2378,'AIR','No se pudo enviar información por la red.  Haga clic en [Sí] para intentarlo nuevamente o en [No] para salir de la prueba.','ESN','--ANY--','--ANY--','\"��VD��x?q��g','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3029,'AIR','Aia he pilikia me ka ‘ōnaehana. E ‘olu’olu e hā’awi i kēia helu iā Kumu','HAW','--ANY--','--ANY--',',�mu�JĨϑ+��E�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3276,'AIR','Pono e koho ?ia kekahi koho.','HAW','--ANY--','--ANY--','���L0Fʜ�dPNg','2013-12-18 14:09:07.080');
INSERT INTO `client_messagetranslation` VALUES (3633,'AIR','<p>E ?ike ana ka mea ho?ohana i kekahi memo ma?ane?i ina ?a?ole i ho?opau ?ia kekahi o na ?ikemu i pane ?ole ?ia.Ina pono, hiki ia lakou ke kaomi [?Ae] i keia pukaaniani nei no ka ho?omau ?ana, aka, maika?i no ka ho?omaopopo ?ana ia?oe iho, ua hoa?o lakou e ho?ololi i kekahi o keia mau ?ikemu.</p>','HAW','--ANY--','--ANY--','�A��Mf�9\r~zw�-','2013-12-18 14:09:07.527');
INSERT INTO `client_messagetranslation` VALUES (2629,'AIR','E ho\'one\'e i ka mea i kahi hou a laila e kaomi i kahi au e makemake ai.','HAW','--ANY--','--ANY--','P�FC#�T��d�\":','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2722,'AIR','Verde','ESN','--ANY--','--ANY--','^�{�NA�|3۱y','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3161,'SBAC','Your State-SSID is not entered correctly. Please try again or ask your TA.','ENU','--ANY--','--ANY--','p��E}�<�F�\r�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3647,'AIR','Dirección IP:','ESN','--ANY--','--ANY--','|L{J`@(�f\\Ћb�q','2014-03-22 20:36:39.670');
INSERT INTO `client_messagetranslation` VALUES (3410,'AIR','Makemake a nei ?oe e ho?oku ?ia ka ho?okomo ?ana i na ikepili no keia ho?ike nei?','HAW','--ANY--','--ANY--','�*\'�\rFo�����4��','2013-12-18 14:09:07.197');
INSERT INTO `client_messagetranslation` VALUES (3414,'AIR','Ho?ike pilikino haumana','HAW','--ANY--','--ANY--','��2E0�]{�a�~V','2013-12-18 14:09:07.203');
INSERT INTO `client_messagetranslation` VALUES (2545,'AIR','<span>Aceptar</span>','ESN','--ANY--','--ANY--','�	w~�H�����0�i�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3040,'AIR','Ua pilikia ka hoʻomaka hou ʻana o kēia hōʻike. E ʻoluʻolu ʻoe, e hana hou.','HAW','--ANY--','--ANY--','����	M�x�*�\\�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3059,'AIR','La sesión no existe.','ESN','--ANY--','--ANY--','�N�G)C|��.���\\Z','2014-03-22 20:35:32.327');
INSERT INTO `client_messagetranslation` VALUES (2902,'AIR','Pilikia ka mikini helu. E ha\'awi i keia helu i kau Kumu Ho\'ike.','HAW','--ANY--','--ANY--','ٔ�&�F(�Skh','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3456,'AIR','Ke ha’alele ia?oe nei a pani i ka polokalamu punaewele puni honua.','HAW','--ANY--','--ANY--','\0��BЀ�:�6|X','2013-12-18 14:09:07.273');
INSERT INTO `client_messagetranslation` VALUES (2890,'AIR','No, no escuché','ESN','--ANY--','--ANY--','X5ЊC�﫲-�Uo','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2805,'AIR','Imprimir item','ESN','--ANY--','--ANY--','��1��IѦG(�(�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2504,'AIR','E kali \'oe \'oiai e ho\'oponopono ana kau Kumu Ho\'ike i na mea e pono ai nou.','HAW','--ANY--','--ANY--','�W�LO`�Au��c�J','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2982,'AIR','{silence}Opción F.{silence}','ESN','--ANY--','--ANY--','h-�tA�/����v9','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2570,'AIR','Makemake anei \'oe e ho\'omaha ma keia ho\'ike?','HAW','--ANY--','--ANY--','EޮL�E���͓N��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2551,'AIR','Tutoriales','ESN','--ANY--','--ANY--','Xr���Ne�iv�ai','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3565,'AIR','Ho?i hou','HAW','--ANY--','--ANY--','p�:	�H���@p:!','2013-12-18 14:09:07.437');
INSERT INTO `client_messagetranslation` VALUES (2561,'AIR','Verificando prueba, espere...','ESN','--ANY--','--ANY--','��ބNz�Z}\Z/ ��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2546,'AIR','E \'olu\'olu, e kali…','HAW','--ANY--','--ANY--','��ϖb@P��˽�\Z','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3448,'AIR','Cerrar sesión','ESN','--ANY--','--ANY--','�Ik�O˃rI\0���G','2013-09-24 09:34:32.983');
INSERT INTO `client_messagetranslation` VALUES (3645,'AIR','Versión menor:','ESN','--ANY--','--ANY--','��3\nN���X��\Z�','2014-03-22 20:36:39.653');
INSERT INTO `client_messagetranslation` VALUES (3101,'AIR','Esta prueba está bloqueada administrativamente. Por favor, consulte con su administrador de pruebas.','ESN','--ANY--','--ANY--','\"��ON��6.01X','2014-03-22 20:35:48.647');
INSERT INTO `client_messagetranslation` VALUES (2903,'AIR','E Hoʻopuka Waha I Ke Kikokikona','HAW','--ANY--','--ANY--','[� ��@����WTq�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2519,'AIR','Ka Inoa o Ka Ho\'ike:','HAW','--ANY--','--ANY--','���\0O����#�^�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2725,'SBAC','Black on Rose','ENU','--ANY--','--ANY--','��}�CD\Z��\Z�۔.','2013-09-06 13:55:36.180');
INSERT INTO `client_messagetranslation` VALUES (3025,'AIR','Aia he pilikia me ka ‘ōnaehana. E ‘olu’olu e hā’awi i kēia helu iā Kumu','HAW','--ANY--','--ANY--','�/:��I�܌�Z2��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2453,'AIR','<span>Haʻalele</span>','HAW','--ANY--','--ANY--','�\'���Ci�)E\0�NW�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3058,'AIR','ʻAʻole hiki ke ʻimi a loaʻa ka waihona ʻike RTS','HAW','--ANY--','--ANY--','�q��F������J�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2803,'AIR','Cerrar Comentario','ESN','--ANY--','--ANY--','D~N�uI,�oz����','2014-03-22 20:33:38.087');
INSERT INTO `client_messagetranslation` VALUES (2947,'AIR','Verdana','ESN','--ANY--','--ANY--','FYA��O�����L$t','2014-03-22 20:34:47.193');
INSERT INTO `client_messagetranslation` VALUES (3416,'AIR','Sesión de invitado','ESN','--ANY--','--ANY--','R�_YI��e��͛u','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3619,'AIR','Esta acción borrará todos los datos para este artículo. ¿Está seguro que desea continuar? <br/>Haga clic en [Sí] para borrar los datos para este artículo. Haga clic en [No] para cancelar esta acción.','ESN','--ANY--','--ANY--','r��7#E�yx���Z_','2014-03-22 20:36:39.513');
INSERT INTO `client_messagetranslation` VALUES (2931,'AIR','Tipo de Braille','ESN','--ANY--','--ANY--','�\\#ظF-��LŇD\0','2014-03-22 20:34:28.117');
INSERT INTO `client_messagetranslation` VALUES (3601,'AIR','Na noi-ki?i','HAW','--ANY--','--ANY--','=�{	B��E�Nx��','2013-12-18 14:09:07.477');
INSERT INTO `client_messagetranslation` VALUES (2368,'AIR','E maka\'ala. Mai hana i na pihi o ka polokalamu kele punaewele: ke pihi ho\'i, ke pihi  huli a i \'ole ke pihi ho\'opiha hou. E ho\'ohana na\'e  \'oe i na pihi ho\'okele ma ka lalo o kela me keia \'ao\'ao.','HAW','--ANY--','--ANY--','m�0�H����u�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3734,'AIR','La prueba se ha finalizado y está lista para entregarse.','ESN','--ANY--','--ANY--','�Q��F���bqF��','2013-09-18 16:31:43.717');
INSERT INTO `client_messagetranslation` VALUES (2596,'AIR','Opción oral C (en inglés)','ESN','--ANY--','--ANY--','����B%���^��L','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2808,'AIR','Cerrar Comentario','ESN','--ANY--','--ANY--','�$�CKBJ�!Y�����','2014-03-22 20:33:38.087');
INSERT INTO `client_messagetranslation` VALUES (3234,'AIR','Ua pilikia ke kukulu ?ia ?ana o kau noi.?Olu?olu e hoa?o hou.','HAW','--ANY--','--ANY--','\Z�mH�Cf����aj�f','2013-12-18 14:09:07.023');
INSERT INTO `client_messagetranslation` VALUES (2430,'AIR','Mensaje del administrador de pruebas:','ESN','--ANY--','--ANY--','\Zp\\T�TIկ��Jf�<�','2014-03-22 20:33:38.103');
INSERT INTO `client_messagetranslation` VALUES (2814,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','\Z�\"`Nr�<��*��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2883,'AIR','E pani i ka mana?o','HAW','--ANY--','--ANY--','\Z�¥�VJ���/<��b','2013-12-18 14:09:06.737');
INSERT INTO `client_messagetranslation` VALUES (2936,'SBAC','Audio Settings','ENU','--ANY--','--ANY--','\Z�UT,@��\0�j�LZ�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2351,'AIR','../tools/formulas/2010/hi_10_math.html','HAW','--ANY--','--ANY--','\Z�&�KY���ɋ~7Q','2013-12-18 14:09:06.617');
INSERT INTO `client_messagetranslation` VALUES (2530,'AIR','Alejar','ESN','--ANY--','--ANY--','\Z�܅n8N��^;+)�V;','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2394,'AIR','ID de sesión:','ESN','--ANY--','--ANY--','\0F�#LMa�օ�B��_','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3331,'AIR','<p>?Apono ?ia ka loiloi ?ia ?ana o na palapala ho?ike like ?ole. </p>','HAW','--ANY--','--ANY--','P��C���)���','2013-12-18 14:09:07.137');
INSERT INTO `client_messagetranslation` VALUES (2539,'AIR','E Ho\'i','HAW','--ANY--','--ANY--','6~n!3Jǽ��׸T','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2375,'AIR','‘A‘ole i ho‘ouka ‘ia ka nīnau . E kaomi i ka [‘Ae] no ka hana hou ‘ana a i ‘ole [‘A‘ole] no ka lele ‘ana i kāu hō‘ike.','HAW','--ANY--','--ANY--','ClE_�EK�׆D��ڬ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2601,'AIR','E ʻōlelo i ke koho F','HAW','--ANY--','--ANY--','de\\J����T�F','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2665,'SBAC','Smarter Balanced Assessment Consortium','ENU','--ANY--','--ANY--','y��NKG�|!^}ɧ','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2635,'SBAC','Do you hear the voice?','ENU','--ANY--','--ANY--','���G���$�$��','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3709,'AIR','{silence}OpciÃ³n D.{silence}','HAW','--ANY--','--ANY--','�?���E����*8�\Z','2013-12-18 14:09:07.637');
INSERT INTO `client_messagetranslation` VALUES (2656,'SBAC','Agregar punto','ESN','--ANY--','--ANY--','�N�G�n�@����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3350,'AIR','Pono ?oe e ho?okomo i ka inoa/helu SSID pololei.','HAW','--ANY--','--ANY--','ZOl$D��8����','2013-12-18 14:09:07.153');
INSERT INTO `client_messagetranslation` VALUES (2428,'AIR','Espere','ESN','--ANY--','--ANY--','!2F��L��QG.9��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3062,'AIR','La versión respaldada o bien no existe o fue restaurada con anterioridad.','ESN','--ANY--','--ANY--','F�$p�Kt����<��S','2014-03-22 20:35:32.330');
INSERT INTO `client_messagetranslation` VALUES (2426,'AIR','<span>ʻAʻole</span>','HAW','--ANY--','--ANY--','P�KfH	����t#�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2627,'SBAC','Seleccione un punto o borde para agregar valor.','ESN','--ANY--','--ANY--','\\���C�.rĔ�!','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3441,'AIR','Score Entry Interface','ESN','--ANY--','--ANY--','j�їBB���!��','2013-09-18 16:27:30.147');
INSERT INTO `client_messagetranslation` VALUES (2747,'AIR','HI Arial','HAW','--ANY--','--ANY--','p-�fLE,�ۡW̷�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2583,'AIR','Problema','ESN','--ANY--','--ANY--','�.;�D�5�rw�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2689,'AIR','Braille','ESN','--ANY--','--ANY--','���٤F2����t|��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3210,'AIR','<p>Ho?okomo i ka ID haumana a ka/na haumana (SSID) a laila e kaomi i ka pihi [?Apono i ka SSID] no ka huli ?ana i na palapala ana ho?ike.Ho?ike ?ia ka ho?ike pilikino o na haumana ma ka palapala ho?ike e like me ka inoa, ka la hanau, ka pae, a me ke kula/moku?aina o na haumana.Ho?ole ?ia ka huli hapa ?ia ?ana o na SSID.</p><br/><p>Mana?o:Ho?ohana ?ia keia hana huli haumana i hiki ia?oe ke ho?oia i na ho?ike pilikino o kekahi haumana no ko lakou ?e?e wale ?ana no.?A?ole keia e ho?ike nei ina ua makaukau keia haumana no kekahi ho?ike.Olu?olu e kipa aku i ke kahua TIDE no ka ho?oia ?ana ina makaukau kekahi haumana no ka lawe ?ana i ka ho?ike.</p>','HAW','--ANY--','--ANY--','��g�bMc����','2013-12-18 14:09:07.003');
INSERT INTO `client_messagetranslation` VALUES (2511,'AIR','Llego al fin del examen. Se pueden repasar las respuestas. Cuando termine de repasar sus respuestas, haga clic en [Finalizar prueba]. No puede cambiar sus respuestas después de entregar su prueba.','ESN','--ANY--','--ANY--','�Bt8�C����á��','2014-03-22 20:33:38.120');
INSERT INTO `client_messagetranslation` VALUES (3660,'AIR','Ke ho?ouka ?ia nei…','HAW','--ANY--','--ANY--','R3HC\'�Fh=��','2013-12-18 14:09:07.567');
INSERT INTO `client_messagetranslation` VALUES (3120,'AIR','Ke hana ?ia nei keia ho?ike i keia manawa.','HAW','--ANY--','--ANY--','R\"G�`A\r�r�Hjw`%','2013-12-18 14:09:06.937');
INSERT INTO `client_messagetranslation` VALUES (3642,'AIR','Tipo:','ESN','--ANY--','--ANY--',']zʇ�@l�\0jSþ	','2014-03-22 20:36:39.637');
INSERT INTO `client_messagetranslation` VALUES (2868,'AIR','E pani i ka mana?o','HAW','--ANY--','--ANY--','��]��AT�5����','2013-12-18 14:09:06.727');
INSERT INTO `client_messagetranslation` VALUES (2763,'AIR','Ninguno','ESN','--ANY--','--ANY--','���3�CЇ�pZ��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2667,'AIR','A\'ole i loa\'a keia hi\'ohi\'ona ma ka ho\'ike \'olelo Hawai\'i.','HAW','--ANY--','--ANY--','�C�RFB����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3624,'AIR','?A?ohe ho?ike i makaukau i keia manawa.','HAW','--ANY--','--ANY--','�D�ѕHR���RI�F','2013-12-18 14:09:07.510');
INSERT INTO `client_messagetranslation` VALUES (2725,'AIR','Magenta','ESN','--ANY--','--ANY--','�^ѷF\\��%tQ!�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3294,'AIR','Hewa ka manawa halihali –hola (Ho?ohana i ke ?ano ho?ala HH; 01-12)','HAW','--ANY--','--ANY--','��om@ࡏ�2y(Z}','2013-12-18 14:09:07.103');
INSERT INTO `client_messagetranslation` VALUES (3159,'AIR','?A?ole i malama ?ia keia pane e keia kahua nei.?Olu?olu e koho hou i kekahi haina.Ina he polokalamu kokua keia ikemu ho?ike no na haumana, pono paha ?oe e kaomi hou i ka pihi [Malama].','HAW','--ANY--','--ANY--','��b\0A�����\n�^t','2013-12-18 14:09:06.967');
INSERT INTO `client_messagetranslation` VALUES (2992,'AIR','Esperando la aprobación del segmento.','ESN','--ANY--','--ANY--','O��a;Ku�\Zhn���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2911,'AIR','No se encontró un paquete de voz apropiado para el idioma inglés en este equipo.','ESN','--ANY--','--ANY--','z�#�&F��CQS�܄','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3603,'AIR','Simulación','ESN','--ANY--','--ANY--','�`vkA��0q�Kt�','2014-03-22 20:36:39.410');
INSERT INTO `client_messagetranslation` VALUES (2751,'AIR','FALSO','ESN','--ANY--','--ANY--','�6}�K(��wZ݌-','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3653,'AIR','Por favor seleccione una prueba','ESN','--ANY--','--ANY--','�o�LF˹=EQ	s��','2014-03-22 20:36:39.720');
INSERT INTO `client_messagetranslation` VALUES (3346,'AIR','<p>Ho?ohana i ka papakuhikuhi huli lalo no ke koho ?ana i ka moku?aina, ke kula, a me na pae kula ma kau mau huli ?ana.Pono ka inoa mua a i ?ole inoa hope.</p>','HAW','--ANY--','--ANY--','��\'9TI���e��d','2013-12-18 14:09:07.150');
INSERT INTO `client_messagetranslation` VALUES (3614,'AIR','Usted no puede pausar la prueba hasta que se contesten todas las preguntas en esta página.','ESN','--ANY--','--ANY--','���B���آ�L��','2014-03-22 20:36:39.473');
INSERT INTO `client_messagetranslation` VALUES (2603,'AIR','Diga el pasaje','ESN','--ANY--','--ANY--','��.I�I9�9�Bl}I3','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2851,'AIR','Waiho i mana?o no keia ?ikamu pono no na ho?ike.','HAW','--ANY--','--ANY--','�^t�I\0�B���G','2013-12-18 14:09:06.713');
INSERT INTO `client_messagetranslation` VALUES (3042,'AIR','La sesión no existe.','ESN','--ANY--','--ANY--','-�Nܵ�s�I�jE','2014-03-22 20:35:11.297');
INSERT INTO `client_messagetranslation` VALUES (3655,'AIR','Ingrese el número total de estudiantes que le gustaría ensayar a la vez:','ESN','--ANY--','--ANY--','%|N��J\"��P���q�','2014-03-22 20:36:39.737');
INSERT INTO `client_messagetranslation` VALUES (2693,'AIR','ʻAno Kinona','HAW','--ANY--','--ANY--',';-�1UB���{Jz��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3054,'AIR','El usuario no está autorizado para acceder a los informes','ESN','--ANY--','--ANY--','k�\0��Ić5��5�a�','2014-03-22 20:35:32.323');
INSERT INTO `client_messagetranslation` VALUES (2452,'AIR','Se necesita Java 1.4 o una versión superior para realizar esta prueba.','ESN','--ANY--','--ANY--','s*n�L끚�F.�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3032,'AIR','Su rol asignado no permite ver los informes.','ESN','--ANY--','--ANY--','����B֨Z^����','2014-03-22 20:35:11.297');
INSERT INTO `client_messagetranslation` VALUES (3090,'AIR','La sesión de prueba comienza el {0}. Para obtener más ayuda, consulte al administrador de la prueba.','ESN','--ANY--','--ANY--','����Aņ&QQrk7�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2593,'AIR','E ʻōlelo i ke koho B','HAW','--ANY--','--ANY--','���l�Bo������','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2368,'AIR','Utilice sólo los botones de navegación en la parte superior de la pantalla.','ESN','--ANY--','--ANY--','ě�Gڨ��z��4','2014-03-22 20:33:38.093');
INSERT INTO `client_messagetranslation` VALUES (2756,'AIR','\'A\'OLE POLOLEI','HAW','--ANY--','--ANY--','�fkI�L��o��@','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3724,'AIR','Notas','ESN','--ANY--','--ANY--','�\\GiIV������{�','2013-08-08 09:27:24.107');
INSERT INTO `client_messagetranslation` VALUES (2739,'AIR','OR CIM Mamakika','HAW','--ANY--','--ANY--','�y{pO��v߷wI�','2013-12-18 14:09:06.650');
INSERT INTO `client_messagetranslation` VALUES (3388,'AIR','<h3>?Anu?u 2:E koho i kekahi la a i ?ole ka palapala ho?ike ho?omaopopo a me ka palapala ho?ike mua aku</h3><p>(Mana?o:Ina koho ?oe & nana ma ka la ho?ike palapala ho?ike, a puka ana ka ?alemanaka.Hiki ia?oe ke kaomi i ka < a i ?ole > ?ihe ma ka ?alemanaka no ka nana ?ana i kela mahina aku nei a pela aku a i ?ole na mahina ma mua a?e o kela.Kaomi i ka la no ke kukulu ?ana i palapala ho?ike no kela la.)</p>','HAW','--ANY--','--ANY--','�z4CU�������','2013-12-18 14:09:07.173');
INSERT INTO `client_messagetranslation` VALUES (2444,'AIR','Reproducir/Pausa','ESN','--ANY--','--ANY--',' !IoI�@B�JAS�\nR','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2618,'AIR','Se produjo un problema al conectarse con Internet. Ponga la prueba en pausa e inténtelo nuevamente.','ESN','--ANY--','--ANY--',' \'�yb�C�����lv�\n','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2558,'AIR','E Pani','HAW','--ANY--','--ANY--',' )��\"O=�%C� �','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3198,'SBAC','<p><strong>Starting the Session:</strong></p><ul><li>Select the assessment(s) that need to be included in the session, then click the [Start Session] button.</li>              <li>Provide the Session ID to your students so that they can log in.</li>            </ul>            <p><strong>Approving Students:</strong></p>            <ul>              <li>Click the [Approvals (#)] button. The Approvals and Student Test Settings window will appear.</li>              <li>Click [See/Edit Details] for a student to view his or her accommodations and test settings and make any necessary changes (e.g., color background choice).</li>              <li>Click the [Approve] button for each student to allow entry into the session. </li>              <li>Click [Approve All Students] if all their settings are accurate and they can all enter the session. </li>              <li>To deny a student, click [Deny] and enter a brief reason (e.g., student selected the wrong assessment). </li>            </ul>       <p><strong>Student Lookup:</strong></p>            <p>You can use this feature to look up student information if a student is having difficulty logging in. </p>            <ul>              <li>Click the [Student Lookup] button in the top row.</li>              <li>Use the [Quick Search] tab to find a student using his or her State-SSID number. Partial State-SSIDs are not allowed.</li>              <li>Use the [Advanced Search] tab to find a student by district, school, and first or last name. Partial names are allowed. (e.g., entering \"Fi\" into the last name field will start a search for all students in the school whose last name begins with Fi). </li>            </ul>            <p><strong>Monitoring Student Progress:</strong></p>            <p>Students that appear in the table below have entered this session. The page automatically refreshes every minute. When the page refreshes, the students’ statuses in the table below will update. The current status plus (#/#) will indicate how many test items the student has answered out of the total number of test items in the assessment. </p>            <p><strong>Stopping the Test Session</strong></p>            <ul>              <li>When all students have completed testing or it is time for the session to end, click [Stop Session]. </li>              <li>This will end the session, pause all your students\' assessments and then log them out of the online testing system. </li></ul>','ENU','--ANY--','--ANY--',' 5`�M|��	B��S','2014-03-11 12:35:15.653');
INSERT INTO `client_messagetranslation` VALUES (2638,'SBAC','Select the green button to test your Text-to-Speech settings. <br /> You should hear the following phrase: \"This is some sample text to test your settings.\" <br /> Click [Yes, I heard the voice] if it worked. If it did not work, click [No, I did not hear ','ENU','--ANY--','--ANY--',' KZ$��J��y��NW','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3022,'AIR','Ma muli o ke ?ano o ka ho?oili ?ana o kau kahua kamepiula, ua hiki ia?oe ke ha?awi aku i keia ho?ike i ka nui haumana au i makemake ai ma keia wahi nei.','HAW','--ANY--','--ANY--',' Mٍ5A&��A]��G�','2013-12-18 14:09:06.850');
INSERT INTO `client_messagetranslation` VALUES (2480,'AIR','Pregunta de audio','ESN','--ANY--','--ANY--',' _1�N6�:���,�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3614,'AIR','Mai ho?oku i keia ho?ike a pau na ninau a pau i ka pane ?ia.','HAW','--ANY--','--ANY--',' �u|_GM���U����','2013-12-18 14:09:07.493');
INSERT INTO `client_messagetranslation` VALUES (3714,'AIR','Papa wehewehe ?olelo','HAW','--ANY--','--ANY--',' ��Q�HBx�R�V���','2013-12-18 14:09:07.643');
INSERT INTO `client_messagetranslation` VALUES (3518,'AIR','Na pilina','HAW','--ANY--','--ANY--',' �A_�HK������T�','2013-12-18 14:09:07.363');
INSERT INTO `client_messagetranslation` VALUES (3619,'AIR','E holoi ?ia ana na ?ikepili a pau no keia ?ikemu ina hana ?ia keia.Makemake a nei ?oe e ho?omau aku?<br/><br/> ?Olu?olu e kaomi i ka pihi [?Ae] no ka holoi ?ana i na ?ikepili a pau.Kaomi i ka pihi [Ho?ole] no ke kapae ?ana i keia hana.','HAW','--ANY--','--ANY--',' ²�kI�毥�Ƃ�','2013-12-18 14:09:07.503');
INSERT INTO `client_messagetranslation` VALUES (2517,'AIR','Nombre del estudiante:','ESN','--ANY--','--ANY--',' ��D�DۢJ�Đ��+','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2708,'AIR','Español','ESN','--ANY--','--ANY--',' �CmF�L5�G&Z��^�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2565,'AIR','Comentario','ESN','--ANY--','--ANY--','!	\0�5�E׼�#�V�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3033,'AIR','Se produjo un problema con el sistema.  Proporcione este número al AP.','ESN','--ANY--','--ANY--','!�\0v�J��壠�p;','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3403,'AIR','Ho?omaka i ka ?oki ?ana i ka pa?ani hou','HAW','--ANY--','--ANY--','!VLV�:A�����t��','2013-12-18 14:09:07.187');
INSERT INTO `client_messagetranslation` VALUES (2939,'AIR','Nemeth','ESN','--ANY--','--ANY--','!`�eH���CT�\\��','2014-03-22 20:34:28.197');
INSERT INTO `client_messagetranslation` VALUES (3446,'AIR','Makemake a nei ?oe e waiho ?ia keia ho?ike nei?','HAW','--ANY--','--ANY--','!p��z�Oʰ�ܛ�t��','2013-12-18 14:09:07.257');
INSERT INTO `client_messagetranslation` VALUES (3007,'AIR','Volver','ESN','--ANY--','--ANY--','!�\nK��G��w4�x�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2377,'AIR','La respuesta no se ha guardado. Haga clic en [Sí] para intentarlo nuevamente o en [No] para salir de la prueba sin guardar su respuesta.','ESN','--ANY--','--ANY--','!���\0�I_��Ԁ��@','2014-03-22 20:33:38.097');
INSERT INTO `client_messagetranslation` VALUES (2962,'AIR','A203','HAW','--ANY--','--ANY--','!�\\�I�B詪y�O','2013-12-18 14:09:06.783');
INSERT INTO `client_messagetranslation` VALUES (3144,'AIR','No hay más oportunidades para realizar esta prueba.','ESN','--ANY--','--ANY--','!�|�CG̳t�}<B','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2605,'AIR','E ʻōlelo i ke kiʻi.','HAW','--ANY--','--ANY--','!�h���G��]T�?�L','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3154,'AIR','No hay coincidencias con su ID de estudiante. Inténtalo nuevamente o consulte con su administrador de pruebas.','ESN','--ANY--','--ANY--','!���9�F��G=M�n�','2014-03-22 20:33:38.153');
INSERT INTO `client_messagetranslation` VALUES (3044,'AIR','No se pudo insertar una nueva sesión en la base de datos','ESN','--ANY--','--ANY--','\"���AF΅�%)���','2014-03-22 20:35:11.300');
INSERT INTO `client_messagetranslation` VALUES (2722,'AIR','Oma\'oma\'o ','HAW','--ANY--','--ANY--','\"(���,G���ZFK^,�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3136,'AIR','Aia he pilikia me ka ‘ōnaehana. E ‘olu’olu, e hana hou.','HAW','--ANY--','--ANY--','\",_~�3I͢\r�����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2381,'AIR','Debe escriba algo antes de guardar su respuesta.','ESN','--ANY--','--ANY--','\",����M+�&���ã','2014-03-22 20:33:38.100');
INSERT INTO `client_messagetranslation` VALUES (3478,'AIR','Ho?ano hou i keia manawa','HAW','--ANY--','--ANY--','\"X\rq��M��Z\'Z','2013-12-18 14:09:07.310');
INSERT INTO `client_messagetranslation` VALUES (3138,'AIR','A\'ole hiki iā \'oe ke hana i ka hō\'ike ma kēia mahele hō\'ike. E noi i kāu Kumu Hō\'ike i kōkua.','HAW','--ANY--','--ANY--','\"`Z_�\rK���<g�|','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2626,'AIR','E koho \'oe i kekahi mea no ka holoi \'ana.','HAW','--ANY--','--ANY--','\"��W#�Eˬ�4&����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3730,'AIR','?A?ole hiki ia?oe ke lawe i keia ho?ike me keia polokalamu punaewele.','HAW','--ANY--','--ANY--','\"Ͻ9L\nH�9��\r�~','2013-12-18 14:09:07.670');
INSERT INTO `client_messagetranslation` VALUES (2985,'AIR','{silence}Option C.{silence}','ESN','--ANY--','--ANY--','\"���!E����U��d','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2848,'AIR','Cerrar Comentario','ESN','--ANY--','--ANY--','#o�Q�D��I��\0� /','2014-03-22 20:34:06.460');
INSERT INTO `client_messagetranslation` VALUES (2510,'AIR','Felicitaciones, ha finalizado la prueba.','ESN','--ANY--','--ANY--','#rf{��@>���{��v�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3162,'AIR','Debe completar la prueba en una sesión en su propia escuela.','ESN','--ANY--','--ANY--','#x	9\ZC��b�M\'��','2014-03-22 20:39:22.703');
INSERT INTO `client_messagetranslation` VALUES (2683,'AIR','ʻAʻole','HAW','--ANY--','--ANY--','#����AԹ��׋�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3323,'SBAC','Smarter Balanced Assessments','ENU','--ANY--','--ANY--','#�;�t�G׀�b	)cV','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2582,'AIR','No ka ho\'ouna \'ana i ka \'ike e kokua, e \'olu\'olu, e wehewehe i ka pilikia a kaomi i [\'Ae].','HAW','--ANY--','--ANY--','#�2�&I���c��\'!','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3145,'SBAC','You have no more opportunities for this test.','ENU','--ANY--','--ANY--','#��BA��?O4�~','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2625,'AIR','Seleccione 2 puntos para conectar con una flecha doble.','ESN','--ANY--','--ANY--','$���As�\"?x`j\'','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2822,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','$\Z�(��J��]�w���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3671,'AIR','../tools/formulas/2010/air_6_8_math.html','HAW','--ANY--','--ANY--','$*6���F^�X�l?(�\"','2013-12-18 14:09:07.587');
INSERT INTO `client_messagetranslation` VALUES (2532,'AIR','Tabla Periódica','ESN','--ANY--','--ANY--','$e��:�Ky�_��g�A�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2688,'AIR','ma loko o','HAW','--ANY--','--ANY--','$����8H-���k�Zߢ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2512,'AIR','Ho\'ike keia hae, ua loa\'a na ninau i maka \'ia. E \'olu\'olu, e nana hou i kela mau ninau ma mua o ka waiho \'ana i kau ho\'ike.','HAW','--ANY--','--ANY--','%l�.iC��W�-��m','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2455,'AIR','<span>Haʻalele</span>','HAW','--ANY--','--ANY--','% ZD!�J����p�hb','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3431,'AIR','Prueba realizada el:','ESN','--ANY--','--ANY--','%.��\"�B�����)J','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3447,'AIR','Cerrar sesión','ESN','--ANY--','--ANY--','%��3E�����\nDM','2013-09-24 09:34:25.987');
INSERT INTO `client_messagetranslation` VALUES (2654,'SBAC','Text-to-Speech is not available on the browser and/or platform that you are using. Please make sure you are using a supported secure browser.','ENU','--ANY--','--ANY--','%��Z!O@p��8����b','2014-03-13 11:12:18.140');
INSERT INTO `client_messagetranslation` VALUES (3035,'AIR','Se produjo un problema con el sistema.  Proporcione este número al AP.','ESN','--ANY--','--ANY--','%�~�}Lܧ_�P,���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3125,'AIR','La prueba no está soportado por la sesión','ESN','--ANY--','--ANY--','%��*V�N|�O����t','2014-03-22 20:35:48.660');
INSERT INTO `client_messagetranslation` VALUES (3088,'AIR','La sesión está cerrada. Consulte al administrador de la prueba.','ESN','--ANY--','--ANY--','%�SGl�J[��.5���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2993,'AIR','Seleccionar [Sí] para volver a intentarlo o [No] para cerrar la sesión.','ESN','--ANY--','--ANY--','%�f��E�#JΧ�.','2014-03-22 20:33:38.143');
INSERT INTO `client_messagetranslation` VALUES (3447,'AIR','Ha?alele','HAW','--ANY--','--ANY--','%λy��FӼ�@��8�','2013-12-18 14:09:07.260');
INSERT INTO `client_messagetranslation` VALUES (3160,'AIR','No se le permite ingresar sin un Administrador de Prueba.','ESN','--ANY--','--ANY--','%�j�\rM ��ٳ�cK','2014-03-22 20:37:20.657');
INSERT INTO `client_messagetranslation` VALUES (2664,'AIR','Ka Haumana<span class=\"no ka ho\'oma\'ama\'a \'ana\">Ka Ho\'ike</span>Ho\'oma\'ama\'a','HAW','--ANY--','--ANY--','%�ת�G4������','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3675,'AIR','Falta prerrequisito','ESN','--ANY--','--ANY--','%��8o+N��zi���9','2014-03-22 20:36:39.860');
INSERT INTO `client_messagetranslation` VALUES (3453,'AIR','Kainoa','HAW','--ANY--','--ANY--','&~�\Z@B3��i6����','2013-12-18 14:09:07.270');
INSERT INTO `client_messagetranslation` VALUES (2843,'AIR','E pani i ka mana?o','HAW','--ANY--','--ANY--','&VnO;!C?�I3Ps�i','2013-12-18 14:09:06.710');
INSERT INTO `client_messagetranslation` VALUES (3067,'AIR','Ua kahamaha ‘ia kāu hō’ike. E ‘olu’olu, e ‘ōlelo pū me kāu Kumu Hō\'ike no ka ho’omau ‘ana i  kāu hō’ike.','HAW','--ANY--','--ANY--','&W$���H��vqk���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2617,'AIR','Problema','ESN','--ANY--','--ANY--','&u�B\'�I�����X�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3052,'AIR','No se encuentra la base de datos RTS','ESN','--ANY--','--ANY--','&�����J���rM�z��','2014-03-22 20:35:32.320');
INSERT INTO `client_messagetranslation` VALUES (2926,'SBAC','Cerrar sesión','ESN','--ANY--','--ANY--','&��M�O	��B����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3072,'AIR','El artículo no existe en esta oportunidad de prueba','ESN','--ANY--','--ANY--','&��\"}�N9�g`�u�*','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2898,'AIR','E Ho\'omaka i ke Kakau \'Ana ma ke Kumumana\'o I Koho \'ia','HAW','--ANY--','--ANY--','\'!��b@ ���;�g','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2957,'AIR','Wahi Hua?olelo','HAW','--ANY--','--ANY--','\'1E�	Jց��P��>','2013-12-18 14:09:06.777');
INSERT INTO `client_messagetranslation` VALUES (3156,'AIR','No hay una ventana de pruebas activa para este estudiante en este momento.','ESN','--ANY--','--ANY--','\'=�FU�D�@\r���J','2014-03-22 20:37:20.653');
INSERT INTO `client_messagetranslation` VALUES (3167,'AIR','?Apono ?ole ?ia ?oe e ?e?e me ka loa?a ?ole o kekahi kahu ha?awi ho?ike.','HAW','--ANY--','--ANY--','\'o4LD��w�I���','2013-12-18 14:09:06.977');
INSERT INTO `client_messagetranslation` VALUES (2968,'AIR','../tools/formulas/2010/mn_8_math.html','HAW','--ANY--','--ANY--','\'����G�H<��]��','2013-12-18 14:09:06.793');
INSERT INTO `client_messagetranslation` VALUES (3105,'AIR','No se encuentra disponible la oportunidad de prueba para visualizarla','ESN','--ANY--','--ANY--','\'����MJ�M�K9P��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3514,'AIR','Opp #','HAW','--ANY--','--ANY--','\'�~� �H���o��ݥ','2013-12-18 14:09:07.357');
INSERT INTO `client_messagetranslation` VALUES (2379,'AIR','Aia nō ‘oe? E kaomi i ka ‘O ia no ka ho‘omau ‘ana a i ‘ole e lele ‘ia ‘oe i loko o {1} kekona.','HAW','--ANY--','--ANY--','\'�EC�E.��Hb�2�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3166,'AIR','?A?ole loa?a ka pahu ?e?e ID haumana no ke kainoa ?ana.','HAW','--ANY--','--ANY--','\'�Siv@d�\'f-�j�h','2013-12-18 14:09:06.973');
INSERT INTO `client_messagetranslation` VALUES (2455,'SBAC','<span>Cerrar sesión</span>','ESN','--ANY--','--ANY--','\'�Wv��I!�@����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3148,'AIR','No hay coincidencias con su ID de estudiante. Inténtalo nuevamente o consulte con su administrador de pruebas.','ESN','--ANY--','--ANY--','\'��PE�Ǟ���','2014-03-22 20:33:38.153');
INSERT INTO `client_messagetranslation` VALUES (2927,'AIR','No hay más oportunidades para realizar esta prueba.','ESN','--ANY--','--ANY--','(��+1M϶sBG2}~�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2980,'AIR','{silence}Opción D.{silence}','ESN','--ANY--','--ANY--','(\r�]�2G����]&h','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2944,'AIR','Auto-solicitud','ESN','--ANY--','--ANY--','(<Q)�@����}8�Ls','2014-03-22 20:34:47.190');
INSERT INTO `client_messagetranslation` VALUES (3061,'AIR','La sesión no existe.','ESN','--ANY--','--ANY--','(\'e���LG� �䱝','2014-03-22 20:35:32.327');
INSERT INTO `client_messagetranslation` VALUES (2969,'AIR','../tools/formulas/2010/mn_11_math.html','HAW','--ANY--','--ANY--','(]���@_��⩙�Nd','2013-12-18 14:09:06.797');
INSERT INTO `client_messagetranslation` VALUES (2503,'AIR','O ka ho\'ike ma luna a\'e ka ho\'ike au e makemake ai e hana? Ina \'oia no, e kaomi i (\'Ae, E ho\'omaka i ka\'u ho\'ike), ina \'a\'ole, e kaomi i [\'A\'ole].</p>','HAW','--ANY--','--ANY--','(_u��@U��r�B��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2946,'AIR','No aplicable','ESN','--ANY--','--ANY--','(�ƈ��G���P{)J�','2014-03-22 20:34:47.193');
INSERT INTO `client_messagetranslation` VALUES (3436,'AIR','Makemake a nei ?oe e ho?oku ?ia ka ho?okomo i ka na ?ikepili no keia ho?ike nei?','HAW','--ANY--','--ANY--','(�s1�O��jl���','2013-12-18 14:09:07.240');
INSERT INTO `client_messagetranslation` VALUES (3150,'AIR','No hay coincidencias con su ID de estudiante. Inténtalo nuevamente o consulte con su administrador de pruebas.','ESN','--ANY--','--ANY--','(���ڭL����9�V','2014-03-22 20:33:38.153');
INSERT INTO `client_messagetranslation` VALUES (2543,'AIR','ʻAʻole','HAW','--ANY--','--ANY--','(��u8I9���s,�H@','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3303,'AIR','?A?ole hiki ke ?oi aku na ?olelo i 300 hua olelo.?Olu?olu e ho?oponopono.','HAW','--ANY--','--ANY--','(���h�HH��OtdT�','2013-12-18 14:09:07.117');
INSERT INTO `client_messagetranslation` VALUES (3264,'AIR','Kaha waena huapalapala','HAW','--ANY--','--ANY--','(�WY�^Cx�<�p�P��','2013-12-18 14:09:07.060');
INSERT INTO `client_messagetranslation` VALUES (2350,'AIR','../tools/formulas/2010/or_CIM_math.html','HAW','--ANY--','--ANY--','(ˑ[��G\\��\\t?�','2013-12-18 14:09:06.610');
INSERT INTO `client_messagetranslation` VALUES (3646,'AIR','Na makemake polokalamu punaewele puni honua:','HAW','--ANY--','--ANY--','(� 5�K������j','2013-12-18 14:09:07.543');
INSERT INTO `client_messagetranslation` VALUES (3427,'AIR','You have finished entering data for this test. Click on [Enter More Scores] to continue data entry or log out.','ESN','--ANY--','--ANY--','(�r�EB��		n3�&','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2801,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--',')+5aĸD��\r�%�km�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3142,'AIR','No puede realizar la prueba en esta sesión. Solicite ayuda al administrador del sistema.','ESN','--ANY--','--ANY--',')F�7�O&�&T��\\�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3419,'AIR','O keia ka ho?ike pololei?','HAW','--ANY--','--ANY--',')H��-M7��\0q�-9p','2013-12-18 14:09:07.210');
INSERT INTO `client_messagetranslation` VALUES (2623,'AIR','E koho i 2 kiko no ka ho\'ohui \'ana a i \'ole e kaomi a alako no ka hana \'ana a ka ho\'ohui \'ana i na kiko.','HAW','--ANY--','--ANY--',')��J$L*��6h{q��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3144,'AIR','‘A’ohe manawa kūpono i koe no ka hana ‘ana i kēia hō’ike nei.','HAW','--ANY--','--ANY--',')�՗P�E葈ur�qQ�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2370,'AIR','A\'ole hiki ia \'oe ke \'e\'e a hiki i ka manawa ou e ha\'alele ai i keia mau polokolamu:','HAW','--ANY--','--ANY--',')�TQ�.L1��>���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3605,'AIR','?Olelo kulohelohe','HAW','--ANY--','--ANY--',')�3�	�E+�?M���P','2013-12-18 14:09:07.480');
INSERT INTO `client_messagetranslation` VALUES (3410,'AIR','¿Está seguro de que desea pausar la prueba? Consulte al administrador de la prueba antes de hacerlo.','ESN','--ANY--','--ANY--',')��JدN����0��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3031,'AIR','No podemos iniciar su sesión. Por favor verifique su información y vuelva a intentarlo.','ESN','--ANY--','--ANY--','*�꼿Cë�y��P�','2014-03-22 20:35:11.297');
INSERT INTO `client_messagetranslation` VALUES (2418,'AIR','Na Manawa Kupono*','HAW','--ANY--','--ANY--','*$��Fc���U}���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3028,'AIR','Aia he pilikia me ka ‘ōnaehana. E ‘olu’olu e hā’awi i kēia helu iā Kumu','HAW','--ANY--','--ANY--','*;!��|Fm�an�*�z�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3119,'AIR','?A?ohe manawa kupono no keia ho?ike nei.','HAW','--ANY--','--ANY--','*V��M��-Dx�7','2013-12-18 14:09:06.937');
INSERT INTO `client_messagetranslation` VALUES (2850,'AIR','Imprimir item','ESN','--ANY--','--ANY--','*Y��\\G⹑*԰/�Q','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2396,'AIR','Ua ho‘ouka ‘ia ka polokalamu kele punaewele pa‘a hewa ma keia lolouila. E ‘olu‘olu, e ha‘i i kau kumu ho\'ike. No keia manawa, e ho\'ohana \'oe i kekahi lolouila ‘e a‘e.','HAW','--ANY--','--ANY--','*`�F��G��_�����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3163,'AIR','La sesión no está disponible para la prueba.','ESN','--ANY--','--ANY--','*x�[��B\'���!�]:6','2014-03-22 20:39:22.703');
INSERT INTO `client_messagetranslation` VALUES (2920,'AIR','E Ho\'oki','HAW','--ANY--','--ANY--','*�É�LM��N�B�_�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3451,'AIR','Inoa ho?ohana','HAW','--ANY--','--ANY--','*���BƔ�gI�ߔ�','2013-12-18 14:09:07.267');
INSERT INTO `client_messagetranslation` VALUES (3350,'SBAC','You must enter a valid State-SSID.','ENU','--ANY--','--ANY--','*���2Eu�]��Z�/','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3618,'AIR','Ua loa?a mua ka wa ho?ohana no keia mea ho?ohana nei.','HAW','--ANY--','--ANY--','*����D.�����','2013-12-18 14:09:07.500');
INSERT INTO `client_messagetranslation` VALUES (2401,'AIR','Ka Papa O Ka Haumana:','HAW','--ANY--','--ANY--','+\r�ƽ@૸�bU�o','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3437,'AIR','Makemake a nei ?oe e ho?oku ?ia ka ho?okomo i ka na ?ikepili no keia ho?ike nei?','HAW','--ANY--','--ANY--','+�B)N��#t��o','2013-12-18 14:09:07.240');
INSERT INTO `client_messagetranslation` VALUES (3265,'AIR','Na mana?o haumana','HAW','--ANY--','--ANY--','+c��L�NN�*Zs���','2013-12-18 14:09:07.063');
INSERT INTO `client_messagetranslation` VALUES (3433,'AIR','<span>Nana hou i na ?ikepili i ho?okomo ?ia</span>','HAW','--ANY--','--ANY--','+g�Sw@ޞ���u���','2013-12-18 14:09:07.233');
INSERT INTO `client_messagetranslation` VALUES (2489,'AIR','Resaltar selección','ESN','--ANY--','--ANY--','+sV\0=B;�=W\Z�{,','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3254,'AIR','Na ha?ilula','HAW','--ANY--','--ANY--','+t�jB�N��3�H=��','2013-12-18 14:09:07.043');
INSERT INTO `client_messagetranslation` VALUES (2537,'AIR','Guardar','ESN','--ANY--','--ANY--','+���22Iۤ��\n��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2557,'AIR','Ka Pakuhi Kumumea','HAW','--ANY--','--ANY--',',!-k�@ �+���:�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2906,'AIR','Intentar nuevamente','ESN','--ANY--','--ANY--',',Pg�TI`�1Rj-B','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3116,'AIR','El ID de informes no es válido.','ESN','--ANY--','--ANY--',',��q�L�������2�','2014-03-22 20:35:48.657');
INSERT INTO `client_messagetranslation` VALUES (2522,'SBAC','<span>Cerrar sesión</span>','ESN','--ANY--','--ANY--',',�1�oDB��4�Hb�E','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3098,'SBAC','Your login attempt was not successful. Please verify that you are using the correct username (email address) and password.','ENU','--ANY--','--ANY--','-	%ݴ�OY��ٱ<os','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2425,'AIR','Nombre de la prueba:','ESN','--ANY--','--ANY--','-e$��I���W[','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2606,'AIR','Ilustración oral (en inglés)','ESN','--ANY--','--ANY--','-�u��JÙ�WtO�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3000,'AIR','Comenzar a hablar','ESN','--ANY--','--ANY--','-E�DeRG�����t�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3015,'SBAC','<p>Por favor repasa su respuestas antes de continuar el examen. No podrá regresar as estas preguntas más tarde. </p><p> Haga clic en un número de pregunta para revisarlo a la izquierda. </p>','ESN','--ANY--','--ANY--','-J~��Eq��vɢ9��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2618,'AIR','Aia he pilikia me ka ho‘ohui ‘ana i ka Pūnaewele. E ‘olu‘olu, e alia iki ‘oe i kāu hō‘ike a e hana hou.','HAW','--ANY--','--ANY--','-jAS�lH=��\nJ	�v','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3149,'AIR','El grado que usó no era correcto.  Inténtelo nuevamente.','ESN','--ANY--','--ANY--','-m��`Bh��)��rl','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2795,'AIR','Ho?opane?e i na mana?o a pau','HAW','--ANY--','--ANY--','-r���Dk�n�Џ��','2013-12-18 14:09:06.670');
INSERT INTO `client_messagetranslation` VALUES (2616,'AIR','Reproducir grabación','ESN','--ANY--','--ANY--','-~ԎD\\��j?�6F','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3611,'AIR','Ho?omau i ka wala?au ?ana','HAW','--ANY--','--ANY--','-�X�[fC���Q:�Ǧ�','2013-12-18 14:09:07.490');
INSERT INTO `client_messagetranslation` VALUES (2899,'AIR','E ho\'ololi i ke kumumana\'o ','HAW','--ANY--','--ANY--','-���{O7��0iK�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3654,'AIR','Ingrese el número total de estudiantes que le gustaría ensayar a la vez:','ESN','--ANY--','--ANY--','-�s��ULɨ唸�q��','2014-03-22 20:36:39.730');
INSERT INTO `client_messagetranslation` VALUES (3108,'AIR','Ke ho?ohana ?ia nei ka ho?ike ka makemake manawa kupono.','HAW','--ANY--','--ANY--','.�Uw;Hp�YŴ�Z��','2013-12-18 14:09:06.923');
INSERT INTO `client_messagetranslation` VALUES (3085,'AIR','No podemos iniciar su sesión. Por favor verifique su información y vuelva a intentarlo.','ESN','--ANY--','--ANY--','.�⡩G���lF)��','2014-03-22 20:35:32.337');
INSERT INTO `client_messagetranslation` VALUES (3524,'AIR','Kaomi i ka pihi [?Oia] no ka ?apono ?ana a pani aku i keia pahu nei.I na makemake ?oe e ?ike i na memo, a kaomi i ka pihi <img src=\"shared/images/alert.gif\" alt=\"Ki?ona ?olea\" height=\"14\" width=\"15\"> ma ka ?ao?ao o ka pihi ha?alele.','HAW','--ANY--','--ANY--','.���1EA�-�DAd�','2013-12-18 14:09:07.373');
INSERT INTO `client_messagetranslation` VALUES (3285,'SBAC','State-SSID','ENU','--ANY--','--ANY--','.<�>Oԅ�3NN�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3622,'AIR','Pau ka ho?okomo ?ana i na ?ikepili','HAW','--ANY--','--ANY--','.uK\\�CMx�B3j�_�E','2013-12-18 14:09:07.510');
INSERT INTO `client_messagetranslation` VALUES (2975,'AIR','<span style=\"display:none\"><a href=\"https://air.tds.airast.org/test_scoreentry/repLogin.aspx?CSS=AIRDataEntry\">en vez ir al sitio de puntuación.</a></span>','ESN','--ANY--','--ANY--','.���A��`ķ���q','2014-03-22 20:34:47.200');
INSERT INTO `client_messagetranslation` VALUES (3537,'AIR','Inoa hope','HAW','--ANY--','--ANY--','.��ıI�W\"�A���','2013-12-18 14:09:07.393');
INSERT INTO `client_messagetranslation` VALUES (3308,'AIR','Ua ho?ano hou piha ?ia na memo i waiho ?ia','HAW','--ANY--','--ANY--','.�t���D���m�e�','2013-12-18 14:09:07.127');
INSERT INTO `client_messagetranslation` VALUES (2403,'AIR','Ka La Hanau:','HAW','--ANY--','--ANY--','.��*\\(B���)�\'�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3515,'AIR','Na kuhikuhi ho?ike','HAW','--ANY--','--ANY--','.�7f�A/����\noF�','2013-12-18 14:09:07.360');
INSERT INTO `client_messagetranslation` VALUES (3546,'AIR','Pani','HAW','--ANY--','--ANY--','.�6Q�NL��\\M�^�+','2013-12-18 14:09:07.410');
INSERT INTO `client_messagetranslation` VALUES (3615,'AIR','Por favor espere a que la simulación se termine antes de guardar su respuesta.','ESN','--ANY--','--ANY--','.���ON�J��((','2014-03-22 20:36:39.480');
INSERT INTO `client_messagetranslation` VALUES (3160,'AIR','?Apono ?ole ?ia ?oe e ?e?e me ka loa?a ?ole o kekahi kahu ha?awi ho?ike.','HAW','--ANY--','--ANY--','/(�b6nN{������','2013-12-18 14:09:06.967');
INSERT INTO `client_messagetranslation` VALUES (2672,'AIR','<p>Algunas preguntas avanzadas basadas en java  requieren que los estudiantes realicen un dibujo o completen otro tipo de tarea sin plazo definido. <a href=\"https://airpt.tds.airast.org/grid/default.aspx\">Haga clic aquí para obtener ejemplos de estas preguntas sin plazo definido</a>.</p>  <p>El sitio web de pruebas de estudiantes y el explorador seguro disponen de medidas de seguridad para evitar que se activen aplicaciones prohibidas durante una prueba. <a href=\"ProcessListTest3.html\">Haga clic aquí para probar la Demostración de aplicaciones prohibidas</a>.</p>','ESN','--ANY--','--ANY--','/8|��pO$��ٛ(','2014-03-22 20:33:38.137');
INSERT INTO `client_messagetranslation` VALUES (2994,'AIR','Se detectó un problema al procesar la solicitud. Se cerrará tu sesión.','ESN','--ANY--','--ANY--','/KD�t�Bm�/�ǃ�;','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3127,'AIR','Ke hana ?ia nei keia ho?ike i keia manawa.','HAW','--ANY--','--ANY--','/��B\0 L��@������','2013-12-18 14:09:06.950');
INSERT INTO `client_messagetranslation` VALUES (3255,'AIR','Alaka?i no ka nana hou ?ana','HAW','--ANY--','--ANY--','/��c��H���t�@��','2013-12-18 14:09:07.047');
INSERT INTO `client_messagetranslation` VALUES (3014,'AIR','Ua hiki maila ?oe i ka palena pau o keia ?apana nei:','HAW','--ANY--','--ANY--','/�Ԑ78O��)�˜u�','2013-12-18 14:09:06.840');
INSERT INTO `client_messagetranslation` VALUES (2823,'AIR','E pani i ka mana?o','HAW','--ANY--','--ANY--','/�d:�Mc�n�/��','2013-12-18 14:09:06.697');
INSERT INTO `client_messagetranslation` VALUES (3141,'AIR','Se produjo un problema con el sistema.  Proporcione este número al administrador de la prueba.','ESN','--ANY--','--ANY--','0���qB��;D�0�.','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3288,'AIR','Na helu kokua','HAW','--ANY--','--ANY--','0-��t;Jj���VK�','2013-12-18 14:09:07.093');
INSERT INTO `client_messagetranslation` VALUES (2575,'AIR','Guarde el trabajo antes de enviar una solicitud de impresión.','ESN','--ANY--','--ANY--','00r��;C\0�r7�V','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2861,'AIR','Waiho i ka mana?o e pili ana i keia ?ikemu pono.','HAW','--ANY--','--ANY--','0F9���E���;�I���','2013-12-18 14:09:06.720');
INSERT INTO `client_messagetranslation` VALUES (2925,'AIR','<span style=\"font-weight:bold; \">ʻAʻole hoʻopololei ʻia nā pepa inā pōkole:</span>ka pepa no laila he mea ʻole ke kaha, inā ʻaʻohe kākau, inā kākau ʻia ma ka namu haole, inā kākau ʻia ma ke ʻano he poema a i ʻole hanakeaka, inā kope ʻia mai ke kūmole mai, inā kākau ʻole ʻia me kekahi kumuhana i hāʻawi ʻia a i ʻole loaʻa ka ʻōlelo ʻino a i ʻole ke kiʻi ʻino i ʻāpono ʻole ʻia e ke kaila kākau o ke kula.','HAW','--ANY--','--ANY--','0�97�<B��	u˝��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3114,'AIR','Este sistema no está disponible para el inicio se sesión como invitado','ESN','--ANY--','--ANY--','0�z/�O`���d���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3063,'AIR','Ua kahamaha ‘ia kāu hō’ike. E ‘olu’olu, e ‘ōlelo pū me kāu Kumu Hō\'ike no ka ho’omau ‘ana i  kāu hō’ike.','HAW','--ANY--','--ANY--','0���C�IU�g7','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2655,'AIR','E holoi','HAW','--ANY--','--ANY--','0��4�H(�m�NGye','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3106,'AIR','?A?ohe manawa kupono no keia kumu ho?ike no na haumana.','HAW','--ANY--','--ANY--','0���cA[�I�ݴZr�','2013-12-18 14:09:06.920');
INSERT INTO `client_messagetranslation` VALUES (3686,'AIR','?Apono ?ole ?ia na ?e?e maopopo ?ole','HAW','--ANY--','--ANY--','0��dU�M����~��˕','2013-12-18 14:09:07.603');
INSERT INTO `client_messagetranslation` VALUES (2744,'AIR','HI 11 ?Epekema','HAW','--ANY--','--ANY--','0����G�\"�N��','2013-12-18 14:09:06.657');
INSERT INTO `client_messagetranslation` VALUES (2652,'AIR','Continuar','ESN','--ANY--','--ANY--','1ۣ�QO������P','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3107,'AIR','Ua loa?a mua ka makemake manawa kupono.','HAW','--ANY--','--ANY--','1oG>������','2013-12-18 14:09:06.920');
INSERT INTO `client_messagetranslation` VALUES (2587,'AIR','E ʻŌlelo I Ka Nīnau A Me Ke Koho (Ma Ka Namu Haole)','HAW','--ANY--','--ANY--','1����A£��x�c\Z5','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3104,'AIR','Oportunidad de prueba no válida','ESN','--ANY--','--ANY--','1��CcJ+���bo��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3004,'AIR','Ua hewa i ka palapala ho?ike au i koho ai.','HAW','--ANY--','--ANY--','1���M3��\'�,8m','2013-12-18 14:09:06.830');
INSERT INTO `client_messagetranslation` VALUES (2768,'AIR','Hiki Ke Hoʻonui','HAW','--ANY--','--ANY--','1�*�I�mǱ����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2661,'AIR','Realizar diagnóstico','ESN','--ANY--','--ANY--','1�h�B�E���Q�I�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3232,'AIR','<p>Ho?ike?ike keia ?ao?ao i na ala pakahi a na haumana i makemake ai e pa?i.Kaomi i ka pihi [?Apono] in ?apono ?oe i ka pa?i ?ia ?ana i ka noi, a i ?ole hiki ia?oe ke ho?ole in makemake[Ho?ole].</p><p>Mana?o:E puka mai ana aka puka ?aniani nana ?ao?ao e pa?i ?ia ana ina kaomi ?oe i ka pihi [?Apono], aia no i ka polokalamu punaewele au e ho?ohana nei.Kaomi i ka pihi [Pa?i] mai kela ?ao?ao no ka ho?ouna ?ana i ka noi i makemake ?ia i kau wahi pa?i.</p>','HAW','--ANY--','--ANY--','1Ɯ�m�Mʿz^9�r�u','2013-12-18 14:09:07.023');
INSERT INTO `client_messagetranslation` VALUES (2340,'AIR','Los puntajes de las pruebas de muestra no se presentan.','ESN','--ANY--','--ANY--','1�\n��C�����g�+','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2560,'AIR','E Pani','HAW','--ANY--','--ANY--','1ݘK�v@���B_����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2362,'AIR','Ingrese su nombre.','ESN','--ANY--','--ANY--','1��\0^\'Hߎ\r����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2896,'AIR','Expandir  todos los avisos','ESN','--ANY--','--ANY--','1�h��Fw��L��:,`','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2948,'AIR','N','HAW','--ANY--','--ANY--','1�jx��B��ƪ�z���','2013-12-18 14:09:06.770');
INSERT INTO `client_messagetranslation` VALUES (2446,'AIR','<span>Sí</span>','ESN','--ANY--','--ANY--','2=�zG�F����1�d','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3100,'AIR','?A?ole i ho?okomo ?ia keia ho?ike ma keia wa ho?ohana.','HAW','--ANY--','--ANY--','2B��,KU�ԕ\rS','2013-12-18 14:09:06.913');
INSERT INTO `client_messagetranslation` VALUES (2721,'AIR','Gris claro','ESN','--ANY--','--ANY--','2i��DXO��dn0��Q','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2754,'AIR','Ejercicios de NL','ESN','--ANY--','--ANY--','2���,/F����	��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2796,'AIR','Empezar a escribir sobre el ejercicio seleccionado','ESN','--ANY--','--ANY--','2�ةKO�L\"Z\0\'�\'','2014-03-22 20:33:38.083');
INSERT INTO `client_messagetranslation` VALUES (2358,'SBAC_PT','../Projects/SBAC/Help/help.html','ENU','--ANY--','--ANY--','2�]��=I���=�[���','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3517,'AIR','Ho?oku i ka ho?ike','HAW','--ANY--','--ANY--','2�8	�KA��镄��`','2013-12-18 14:09:07.360');
INSERT INTO `client_messagetranslation` VALUES (3061,'AIR','?A?ohe wa ho?ohana e like me keia','HAW','--ANY--','--ANY--','3)��}�G̽�AVS,�+','2013-12-18 14:09:06.887');
INSERT INTO `client_messagetranslation` VALUES (3161,'AIR','No hay coincidencias con su ID de estudiante. Inténtalo nuevamente o consulte con su administrador de pruebas.','ESN','--ANY--','--ANY--','3+�(Z�NL�z���q�','2014-03-22 20:33:38.157');
INSERT INTO `client_messagetranslation` VALUES (3029,'AIR','Se produjo un problema con el sistema.  Proporcione este número al AP.','ESN','--ANY--','--ANY--','3q/�~�BY�;0�y��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3412,'AIR','Pono ?oe a ho?okomo i na ?ikepili a pau no na ninau a pau ma keia ?ao?ao ma mua o ka ho?opau ?ia ?ana o keia wa ho?ohana.','HAW','--ANY--','--ANY--','3�;�L�B��Ҧ��L','2013-12-18 14:09:07.200');
INSERT INTO `client_messagetranslation` VALUES (2812,'AIR','Someter un comentario para este ítem.','ESN','--ANY--','--ANY--','3�}��5@X�7P,���','2014-03-22 20:33:38.090');
INSERT INTO `client_messagetranslation` VALUES (3222,'AIR','Pono ?oe e ho?okomo i ho?okahi a i ?ole kekahi o keia mau mea nei:Pae, inoa mua, a i ?ole inoa hope.','HAW','--ANY--','--ANY--','4DM˾�EڌǬd�[0','2013-12-18 14:09:07.017');
INSERT INTO `client_messagetranslation` VALUES (2397,'AIR','Ua ho‘ouka ‘ia ka polokalamu kele punaewele pa‘a hewa ma keia lolouila. E ‘olu‘olu, e ha‘i i kau kumu ho\'ike. No keia manawa, e ho\'ohana \'oe i kekahi lolouila ‘e a‘e.','HAW','--ANY--','--ANY--','4GhTtOW�-�\Z��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2930,'AIR','Volver a Inicio de Sesión del Estudiante','ESN','--ANY--','--ANY--','4T�\0�N���Ë��Tk','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2605,'AIR','Diga la imagen','ESN','--ANY--','--ANY--','4����cH;���gVħ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3086,'AIR','Hiki ?ole ke loa?a na ?ikepili ho?ike ma ke kahua RTS','HAW','--ANY--','--ANY--','4���YSL����?U<X','2013-12-18 14:09:06.903');
INSERT INTO `client_messagetranslation` VALUES (2422,'AIR','Haga clic aquí para cancelar y volver al inicio.','ESN','--ANY--','--ANY--','4�5RJNS�٭�m�','2014-03-22 20:33:38.100');
INSERT INTO `client_messagetranslation` VALUES (2444,'AIR','Hoʻokani/Hoʻomaha','HAW','--ANY--','--ANY--','4�z�jD�����&','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3705,'AIR','{silence}Koho F.{silence}','HAW','--ANY--','--ANY--','4�%�O�C��/�{��6�','2013-12-18 14:09:07.630');
INSERT INTO `client_messagetranslation` VALUES (3115,'AIR','El administrador de pruebas no aprobó el inicio/reinicio de la prueba.','ESN','--ANY--','--ANY--','4s�Ms�[&=��I','2014-03-22 20:33:38.150');
INSERT INTO `client_messagetranslation` VALUES (2518,'SBAC','State-SSID:','ENU','--ANY--','--ANY--','5����F��ê�\Z��','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2595,'AIR','E ʻōlelo i ke koho C','HAW','--ANY--','--ANY--','5k\ZutF?�e�$r��x','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3032,'AIR','Ho?ole ?ia ?oe mai ka nana ?ana i na ana ho?ike ma muli o kou kulana.','HAW','--ANY--','--ANY--','5m׷�nI�@����5','2013-12-18 14:09:06.860');
INSERT INTO `client_messagetranslation` VALUES (3020,'AIR','Dada la carga actual en el sistema, es poco probable que se pruebe con éxito el número requerido de estudiantes en este sitio. Algunos estudiantes pueden sufrir interrupciones o demoras. Por favor, intente un número cercano a {0}.','ESN','--ANY--','--ANY--','5�wkYECn����s�','2014-03-22 20:35:11.290');
INSERT INTO `client_messagetranslation` VALUES (2576,'AIR','E Nana','HAW','--ANY--','--ANY--','5����OƓ�\\EW�5','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3212,'AIR','<p>Kaomi aku ma ka ?ohenana no ka nana ?ana i na ana ho?ike e pili ana i kela haumana.</p>','HAW','--ANY--','--ANY--','5��*D�ZZ!��>�','2013-12-18 14:09:07.007');
INSERT INTO `client_messagetranslation` VALUES (2772,'AIR','POLOLEI','HAW','--ANY--','--ANY--','5�(i�N���n���Y','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3220,'AIR','Koho i ke kula','HAW','--ANY--','--ANY--','5�2ȅoC���`5�E','2013-12-18 14:09:07.013');
INSERT INTO `client_messagetranslation` VALUES (3215,'SBAC','You must enter a valid State-SSID.','ENU','--ANY--','--ANY--','5�C�aEp��d�a-C�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3031,'AIR','Ho?ole ?ia ka ?e?e ?ana. ?Olu?olu e nana hou i kau waihona ho?ike pilikino a laila e hoa?o hou.','HAW','--ANY--','--ANY--','5��-�FʲTJ��6�','2013-12-18 14:09:06.857');
INSERT INTO `client_messagetranslation` VALUES (3129,'AIR','Este estudiante ya no permanece en esta sesión.','ESN','--ANY--','--ANY--','5�e(�J��^�j�Vu�','2014-03-22 20:37:20.650');
INSERT INTO `client_messagetranslation` VALUES (3438,'AIR','Comenzar prueba','ESN','--ANY--','--ANY--','5��ҭM⻮���o��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2357,'AIR','../tools/periodic/2010/htmlSimple.html','HAW','--ANY--','--ANY--','5�~.II�R��p>�','2013-12-18 14:09:06.633');
INSERT INTO `client_messagetranslation` VALUES (3571,'AIR','Ho?okomo i ka inoa hope','HAW','--ANY--','--ANY--','5�\"#b�C��Bߧ�>&2','2013-12-18 14:09:07.447');
INSERT INTO `client_messagetranslation` VALUES (2700,'AIR','Loa\'a Ka Palapala Ho\'ole Ho\'ike Makua  ','HAW','--ANY--','--ANY--','5�d�H���������','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2471,'AIR','no ke komo \'ana i ka Puke Alaka\'i Kokua ma ka manawa a pau ma ka ho\'ike                                                                                                                                                                                        ','HAW','--ANY--','--ANY--','6&d@�2K�������','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2547,'AIR','Ke pa\'i \'ia nei kau ho\'ike ho\'oma\'ama\'a ...','HAW','--ANY--','--ANY--','6n4Y�M\\���`���/','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3130,'AIR','Ua pa?a keia wa ho?ohana.','HAW','--ANY--','--ANY--','6{�`҇Aڃ�l�!�K=','2013-12-18 14:09:06.953');
INSERT INTO `client_messagetranslation` VALUES (2398,'AIR','Ka Polokalamu Kele Punaewele','HAW','--ANY--','--ANY--','6|p�I1H���C�J��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3261,'AIR','Papa kuhikuhi kumumea','HAW','--ANY--','--ANY--','6�#C3�O��k	�#�k','2013-12-18 14:09:07.057');
INSERT INTO `client_messagetranslation` VALUES (3552,'AIR','Hana','HAW','--ANY--','--ANY--','6ӯ���A�Ȋ>jg��','2013-12-18 14:09:07.420');
INSERT INTO `client_messagetranslation` VALUES (2554,'AIR','No','ESN','--ANY--','--ANY--','7!E��F	�a��M�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3685,'AIR','?Apono ?ole ?ia ?oe e ?e?e me ka loa?a ?ole o kekahi kahu ha?awi ho?ike.','HAW','--ANY--','--ANY--','7?�e��@����Q�_9','2013-12-18 14:09:07.603');
INSERT INTO `client_messagetranslation` VALUES (3471,'AIR','Kainoa ?ia ma lalo o:','HAW','--ANY--','--ANY--','7I��1�F҆�#0|f$','2013-12-18 14:09:07.297');
INSERT INTO `client_messagetranslation` VALUES (2632,'AIR','Haga clic para soltar el objeto donde desee colocarlo.','ESN','--ANY--','--ANY--','7[iő2E����ʃ�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2916,'AIR','<span class=\"hasTTS\">E koho i ke kākau ou e makemake ai e hoʻoloha, a laila, e kaomi i ke pihi ʻōmaʻomaʻo i kani ʻo ia.</span>','HAW','--ANY--','--ANY--','7q���I���:��p��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2645,'AIR','E ho‘olohe ma ka ‘ōlelo Paniolo.','HAW','--ANY--','--ANY--','7sɄ��F��������','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2687,'AIR','Ke ho\'oia \'ia nei ka ho\'ike.','HAW','--ANY--','--ANY--','7{g�yF��va\\M��9','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2611,'AIR','Hoʻokani','HAW','--ANY--','--ANY--','7�D��O@\"��̗_�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3702,'SBAC','{silence} C.{silence}','ENU','--ANY--','--ANY--','7�]B��@�\r]՚B�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2440,'AIR','<span>ʻAʻole</span>','HAW','--ANY--','--ANY--','7ߋ٠EFO�1�|(Vx�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3421,'AIR','?Ae, ho?omaka i ka ho?okomo ?ana i na ?ikepili','HAW','--ANY--','--ANY--','7�ž��B�)&_а]A','2013-12-18 14:09:07.217');
INSERT INTO `client_messagetranslation` VALUES (2901,'AIR','Pilikia ka mikini helu. E ha\'awi i keia helu i kau Kumu Ho\'ike.','HAW','--ANY--','--ANY--','8\rAZRLŅG���5��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3169,'SBAC','<span>Smarter Balanced Assessment Consortium</span> ','ENU','--ANY--','--ANY--','8VJaC(�=�)��','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3482,'AIR','Ha?awina','HAW','--ANY--','--ANY--','80־8�CС�⟨-�P','2013-12-18 14:09:07.317');
INSERT INTO `client_messagetranslation` VALUES (2408,'AIR','Seleccionar grado','ESN','--ANY--','--ANY--','82�V�D���ħM[g�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2555,'AIR','Fórmulas','ESN','--ANY--','--ANY--','8^���I:�	���5�p','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2410,'AIR','Kau Mau Ho\'ike','HAW','--ANY--','--ANY--','8p5_�JL������','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3412,'AIR','Debe responder todas las preguntas de esta página antes de terminar la prueba.','ESN','--ANY--','--ANY--','8�Bt��DP���X�-','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2397,'AIR','Se instaló el navegador seguro incorrecto en esta computadora. Consulte con el administrador de la prueba. Por ahora, debe usar otra computadora.','ESN','--ANY--','--ANY--','8�7\'��C��ʿ�k�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2827,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','8���;[I)�4��۽^�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3623,'AIR','O keia ka ho?ike au i makemake ai e ho?okomo ?ia na ?ikepili?Ina pela, e kaomi i ka pihi [?Ae, a laila e ho?okomo i na ?ikepili].Ina ?a?ole, e kaomi ia [Ho?ole].','HAW','--ANY--','--ANY--','8�ㄎQNB���a�r�\\','2013-12-18 14:09:07.510');
INSERT INTO `client_messagetranslation` VALUES (2711,'AIR','POLOLEI','HAW','--ANY--','--ANY--','8��	gIԥX��@���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2798,'AIR','Na ?ikemu pono a?o no na ho?ike','HAW','--ANY--','--ANY--','9.���F,��p;i�C{','2013-12-18 14:09:06.673');
INSERT INTO `client_messagetranslation` VALUES (3425,'AIR','  <a href=\"https://dept.tds.airast.org/student/\" class=\"goPractice\">En cambio, visite el sitio Prueba de muestra.</a>','ESN','--ANY--','--ANY--','987DnNʥtz�X=OU','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3516,'AIR','KulanaHaumana','HAW','--ANY--','--ANY--','9��ޑ�L�nB��G-','2013-12-18 14:09:07.360');
INSERT INTO `client_messagetranslation` VALUES (3182,'AIR','Pono ?oe e koho i ho?okahi ho?ike ma mu aka ho?omaka ?ana o kekahi wa ho?ohana ho?ike.','HAW','--ANY--','--ANY--','9�<��A��4�2�+�','2013-12-18 14:09:06.980');
INSERT INTO `client_messagetranslation` VALUES (3023,'AIR','<p>Usted no ha registrado una respuesta a uno o más elementos en esta página. </ p>Sí esto es correcto, haga clic en [Sí] para pasar a la página siguiente. </ p> Si no es correcto, haga clic en [No] para permanecer en esta página. </ p>','ESN','--ANY--','--ANY--',':�xB:Nf�|�.���','2014-03-22 20:35:11.293');
INSERT INTO `client_messagetranslation` VALUES (3676,'AIR','Preguntas:','ESN','--ANY--','--ANY--',':�F�Km��tB%s','2013-08-08 09:24:18.110');
INSERT INTO `client_messagetranslation` VALUES (2705,'AIR','Manaʻo O Ka Haumāna','HAW','--ANY--','--ANY--',':���|N��̊�>}','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2717,'AIR','Negro sobre blanco','ESN','--ANY--','--ANY--',':(Hxq�KL��\n��χ�','2014-03-22 20:33:38.137');
INSERT INTO `client_messagetranslation` VALUES (2792,'AIR','Cerrar Comentario','ESN','--ANY--','--ANY--',':_uh��NĿ�C$d�>\0','2014-03-22 20:33:38.080');
INSERT INTO `client_messagetranslation` VALUES (2463,'AIR','\'A\'ole','HAW','--ANY--','--ANY--',':u�3n�H�/��/�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3612,'AIR','No aplicable','ESN','--ANY--','--ANY--',':�T#͐L滯�*ò','2014-03-22 20:36:39.457');
INSERT INTO `client_messagetranslation` VALUES (3425,'AIR',' ','HAW','--ANY--','--ANY--',':���\"\'I���G','2013-12-18 14:09:07.223');
INSERT INTO `client_messagetranslation` VALUES (3390,'AIR','Hewa ka inoa mua a me/a i ?ole ka inoa hope','HAW','--ANY--','--ANY--',':�&�#J}�&q�B���','2013-12-18 14:09:07.173');
INSERT INTO `client_messagetranslation` VALUES (3310,'AIR','<h3>3.E kukulu i ?olea</h3>','HAW','--ANY--','--ANY--',':җQ��G��Ua��v#�','2013-12-18 14:09:07.127');
INSERT INTO `client_messagetranslation` VALUES (2900,'AIR','¿Está seguro que desea cambiar el ejercisio previamente seleccionado?','ESN','--ANY--','--ANY--',':�̴SD��V���k','2014-03-22 20:33:38.140');
INSERT INTO `client_messagetranslation` VALUES (2493,'AIR','Deshacer tachado','ESN','--ANY--','--ANY--',';\0[��$H����S���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2388,'AIR','La grabación se escucha demasiado baja. Haga clic en [Intentar nuevamente] para hacer una nueva grabación o [Guardar] para guardarla y pasar a la pregunta siguiente.','ESN','--ANY--','--ANY--',';�T!nH���M%z$\0','2014-03-22 20:33:38.100');
INSERT INTO `client_messagetranslation` VALUES (2929,'AIR','La oportunidad actual está activa','ESN','--ANY--','--ANY--',';�0�:O��(�9�?P�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3051,'AIR','Hiki ?ole ke loa?a na panako ?ikemu ho?ike no na haumana','HAW','--ANY--','--ANY--',';�s��I��8d���D','2013-12-18 14:09:06.873');
INSERT INTO `client_messagetranslation` VALUES (2813,'AIR','E pani i ka mana?o','HAW','--ANY--','--ANY--',';D�OH�IE�����ʁ�','2013-12-18 14:09:06.690');
INSERT INTO `client_messagetranslation` VALUES (2584,'AIR','E ʻōlelo i ke koho','HAW','--ANY--','--ANY--',';L&���Cs��OP�fg�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3674,'AIR','E ho?omau maoli ?oe i ka lawe ?ana i ka ho?ike ma ke ?ano like o ka wa ho?ohana i ho?omaka ?ia ai.','HAW','--ANY--','--ANY--',';{E��Ow�UB�Ͼ)','2013-12-18 14:09:07.590');
INSERT INTO `client_messagetranslation` VALUES (2666,'SBAC','<!--<span>Smarter Balanced Assessment Consortium</span><br />      Address 1<br />     Address 2<br />-->','ENU','--ANY--','--ANY--',';�N��tD����5�g5\"','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2884,'AIR','Tutorial','ESN','--ANY--','--ANY--',';���cNK��hQ��~�','2014-03-22 20:33:38.140');
INSERT INTO `client_messagetranslation` VALUES (3601,'SBAC','Technology-Enhanced','ENU','--ANY--','--ANY--',';��I�@b��Z[�h�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3606,'AIR','Entrada de resultados','ESN','--ANY--','--ANY--',';�9�k�@Q�E\0��f','2014-03-22 20:36:39.433');
INSERT INTO `client_messagetranslation` VALUES (2866,'AIR','Someter un comentario para este ítem.','ESN','--ANY--','--ANY--',';�g�D��8N�!?SS','2014-03-22 20:34:28.040');
INSERT INTO `client_messagetranslation` VALUES (2908,'AIR','E ʻŌlelo I Ke Kākau (Ma Ka Namu Haole)','HAW','--ANY--','--ANY--','<g���O��C�v���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2654,'AIR','‘A‘ohe TTS. E ‘olu‘olu, e nānā inā ‘oe hana i ka polokalamu kele pūnaewele kūpono.','HAW','--ANY--','--ANY--','<�o�&N���[�\"�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3651,'AIR','Ho?ike hoa?o kikowaena punaewele:','HAW','--ANY--','--ANY--','<0C���@�Փ��s}�','2013-12-18 14:09:07.553');
INSERT INTO `client_messagetranslation` VALUES (2674,'AIR','A\'ole i loa\'a keia hi\'ohi\'ona ma ka ho\'ike \'olelo Hawai\'i.','HAW','--ANY--','--ANY--','<o��1�F��M�%��h','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3205,'AIR','Makemake a nei ?oe e ho?okomo aku i ka/na ho?ike i koho ?ia ma keia wa ho?ohana nei?','HAW','--ANY--','--ANY--','<�[��Aj�	���K�','2013-12-18 14:09:06.997');
INSERT INTO `client_messagetranslation` VALUES (3250,'AIR','Mikini helu','HAW','--ANY--','--ANY--','<��$�}@̟�*\\��','2013-12-18 14:09:07.037');
INSERT INTO `client_messagetranslation` VALUES (2466,'AIR','El administrador de pruebas ha rechazado su solicitud.','ESN','--ANY--','--ANY--','<�Ey�TBP�ᐛ���','2014-03-22 20:33:38.110');
INSERT INTO `client_messagetranslation` VALUES (2671,'AIR','E Pa\'i i ka Mo\'olelo','HAW','--ANY--','--ANY--','=P)�ԹC�{��,�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2707,'AIR','Inglés','ESN','--ANY--','--ANY--','=]*���Gƴv\0.�b\n�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3118,'AIR','ʻAʻole hiki i ka hoakipa ke kāinoa','HAW','--ANY--','--ANY--','=w�<�Gй�N�x��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3291,'AIR','Hewa ka la halihali (Ho?ohana i ke ?ano ho?ala DD)','HAW','--ANY--','--ANY--','=��FqQIc��6M��^�','2013-12-18 14:09:07.097');
INSERT INTO `client_messagetranslation` VALUES (3574,'AIR','Wahi komo no ka','HAW','--ANY--','--ANY--','=�$T�:B&���bV�','2013-12-18 14:09:07.450');
INSERT INTO `client_messagetranslation` VALUES (2371,'AIR','Se ha iniciado otro programa, y se cerrará la sesión. Consulte con el administrador de la prueba.','ESN','--ANY--','--ANY--','=��dBJ�����P�Z�','2014-03-22 20:33:38.093');
INSERT INTO `client_messagetranslation` VALUES (3046,'AIR','No podemos iniciar su sesión. Por favor verifique su información y vuelva a intentarlo.','ESN','--ANY--','--ANY--','=���M�R�jy��','2014-03-22 20:35:32.317');
INSERT INTO `client_messagetranslation` VALUES (2598,'AIR','E ʻŌlelo I Ke Koho D (Ma Ka Namu Haole)','HAW','--ANY--','--ANY--','=�\n���O%��Ɋ㓉','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2464,'AIR','<span>\'Ae, E Ho\'omaka I Ka\'u Hō\'ike </span>','HAW','--ANY--','--ANY--','>-7T\\L3����\'�\n','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3599,'AIR','Ho?okomo hou i kekahi ?ikepili no keia haumana','HAW','--ANY--','--ANY--','>9e2�M�N�%#��o','2013-12-18 14:09:07.467');
INSERT INTO `client_messagetranslation` VALUES (3440,'AIR','Realizar diagnóstico','ESN','--ANY--','--ANY--','>�E��qNX������-','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3221,'AIR','Koho i ke kula','HAW','--ANY--','--ANY--','>��b��Lh��`��i�d','2013-12-18 14:09:07.017');
INSERT INTO `client_messagetranslation` VALUES (3001,'AIR','Na palapala ho?ike:','HAW','--ANY--','--ANY--','>��N��O�����R2X','2013-12-18 14:09:06.827');
INSERT INTO `client_messagetranslation` VALUES (2943,'AIR','Ninguno','ESN','--ANY--','--ANY--','>ϊ��EG��~����d','2014-03-22 20:34:47.190');
INSERT INTO `client_messagetranslation` VALUES (3053,'AIR','No podemos iniciar su sesión. Por favor verifique su información y vuelva a intentarlo.','ESN','--ANY--','--ANY--','>�o1�B�4限���','2014-03-22 20:35:32.323');
INSERT INTO `client_messagetranslation` VALUES (3401,'AIR','Ho?omaka i ka pa?ani hou ?ia ?ana Io ka ?ikemu','HAW','--ANY--','--ANY--','>�?_+UL7�LxWN�n�','2013-12-18 14:09:07.183');
INSERT INTO `client_messagetranslation` VALUES (3714,'AIR','Glosario','ESN','--ANY--','--ANY--','?�H�y@T��oHd�b','2014-03-22 20:36:39.980');
INSERT INTO `client_messagetranslation` VALUES (3283,'SBAC','Audio Settings','ENU','--ANY--','--ANY--','?jMY�_L���_*<N�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2891,'AIR','Cerrar','ESN','--ANY--','--ANY--','?�k�TI$��n�n|�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2928,'AIR','No puede realizar esta prueba hasta {0}.','ESN','--ANY--','--ANY--','?ƟɸJC�vQ���n','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3050,'AIR','ʻAʻole hiki ke ʻimi a loaʻa ka waihona ʻike RTS','HAW','--ANY--','--ANY--','?��/��B���RS�q�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2346,'AIR','../tools/formulas/2010/hi_5_math.html','HAW','--ANY--','--ANY--','@{�%>CX�#�{\'D��','2013-12-18 14:09:06.600');
INSERT INTO `client_messagetranslation` VALUES (2973,'AIR','Cerrar Sesión','ESN','--ANY--','--ANY--','@#h��K����ʂ��N','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3076,'AIR','Formato de identificación no válido','ESN','--ANY--','--ANY--','@\\&�B���&�F','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2791,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','@���tFl��\rڼ;(','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3281,'AIR','Ho?ike punaewele puni honua ?ole','HAW','--ANY--','--ANY--','@���~H&�x�Սf�','2013-12-18 14:09:07.087');
INSERT INTO `client_messagetranslation` VALUES (2594,'AIR','E ʻŌlelo I Ke Koho B (Ma Ka Namu Haole)','HAW','--ANY--','--ANY--','@����@����Ln�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2964,'AIR','A205','HAW','--ANY--','--ANY--','@�D��aL`�m0]V�P','2013-12-18 14:09:06.787');
INSERT INTO `client_messagetranslation` VALUES (2740,'AIR','12kiko','HAW','--ANY--','--ANY--','@�y�~N@���N2k�','2013-12-18 14:09:06.650');
INSERT INTO `client_messagetranslation` VALUES (2581,'AIR','Haga clic aquí para enviar información útil.','ESN','--ANY--','--ANY--','@��NA�v=r���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3123,'AIR','La sesión esta cerrada.','ESN','--ANY--','--ANY--','AH���@(�v��52\n','2014-03-22 20:35:48.660');
INSERT INTO `client_messagetranslation` VALUES (2755,'AIR','Ninguno','ESN','--ANY--','--ANY--','Am:�)�D݈�&HT���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3528,'AIR','?Olu?olu e kali','HAW','--ANY--','--ANY--','A�ΕʳH�$��=��','2013-12-18 14:09:07.380');
INSERT INTO `client_messagetranslation` VALUES (2837,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','A�r�*�A9���@�9','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2748,'AIR','HI Times','HAW','--ANY--','--ANY--','A��*fFQ�ٹ��_��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2443,'AIR','Hoʻomaka I Kinohi','HAW','--ANY--','--ANY--','B\0{�H썉�W��b�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2998,'SBAC','State-SSID','ENU','--ANY--','--ANY--','B$e�\"�Cˏ��Ħ�.C','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3010,'AIR','El navegador que está utilizando fue lanzado recientemente, y AIR no ha terminado de probar su compatibilidad con este sistema. Puede seguir utilizando este navegador si decide hacerlo, sin embargo, existe la posibilidad de que algunas características no funcionen correctamente.','ESN','--ANY--','--ANY--','B8�@��M��H-n@�\n�','2014-03-22 20:35:11.283');
INSERT INTO `client_messagetranslation` VALUES (3485,'AIR','Inoa haumana','HAW','--ANY--','--ANY--','B}5��yN\'�\",���','2013-12-18 14:09:07.320');
INSERT INTO `client_messagetranslation` VALUES (2361,'AIR','E pani','HAW','--ANY--','--ANY--','B�4[��C��~+�c�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3135,'AIR','Se produjo un problema con el sistema.  Inténtelo nuevamente.','ESN','--ANY--','--ANY--','B��`�D��{��Gb=\Z','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2600,'AIR','E ʻŌlelo I Ke Koho E (Ma Ka Namu Haole)','HAW','--ANY--','--ANY--','B��^H�Il�kƮ\\�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2674,'AIR','Leer el texto seleccionado','ESN','--ANY--','--ANY--','B����M`��D���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3081,'AIR','Hiki ?ole ke loa?a na ?ikepili ho?ike ma ke kahua RTS','HAW','--ANY--','--ANY--','Ci�3�D���Z5��','2013-12-18 14:09:06.897');
INSERT INTO `client_messagetranslation` VALUES (2434,'AIR','Ua huli maika\'i \'o Java','HAW','--ANY--','--ANY--','CG�p�D ����(�	','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2445,'AIR','<ol><li>E kaomi i ka pihi poepoe a e ʻōlelo mai i kou inoa i loko ka ipuleo. </li><li>Ke pau, e kaomi hou i ke pihi. </li><li>A laila, e kaomi i ke pihi hoʻokahi.</li><li> Inā lohe ʻoe i kou inoa, e kaomi i ka [ʻAe], inā ʻaʻole, e hana hou ʻoe a i ʻole e kaomi i ke pihi [Pilikia].</li></ol>','HAW','--ANY--','--ANY--','C\\=���E-��rP٘e?','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3629,'AIR','manawa kupono {0}','HAW','--ANY--','--ANY--','C��,:G�ޭ��B!W','2013-12-18 14:09:07.520');
INSERT INTO `client_messagetranslation` VALUES (2452,'AIR','Pono ka Java 1.4 a ʻoi no ka hana ʻana i kēia hōʻike','HAW','--ANY--','--ANY--','C����4C�����ov�[','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3607,'AIR','?Olelo makemake nui','HAW','--ANY--','--ANY--','C�!��M����Z]�','2013-12-18 14:09:07.483');
INSERT INTO `client_messagetranslation` VALUES (3293,'AIR','Hewa ka makahiki halihali (Ho?ohana i ke ?ano ho?ala YYYY)','HAW','--ANY--','--ANY--','C��� �L���J���5t','2013-12-18 14:09:07.100');
INSERT INTO `client_messagetranslation` VALUES (2857,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','D\Z}�R�N�uy�9g�e','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3041,'AIR','Ua pilikia ka hoʻomaka hou ʻana o kēia hōʻike. E ʻoluʻolu ʻoe, e hana hou.','HAW','--ANY--','--ANY--','DB��E`�ӎ�k�(y','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2689,'AIR','Heluhelu Lima','HAW','--ANY--','--ANY--','D3��!�D�(x�h]k','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3666,'AIR','Compruebe ELPA','ESN','--ANY--','--ANY--','DN[M��I����.�f','2014-03-22 20:36:39.827');
INSERT INTO `client_messagetranslation` VALUES (2477,'AIR','Ka Puke Alaka\'i Kokua','HAW','--ANY--','--ANY--','D[^lD��U�\\@\r��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2738,'AIR','OR 3-5 Makemakika','HAW','--ANY--','--ANY--','D[v<eB���6���','2013-12-18 14:09:06.647');
INSERT INTO `client_messagetranslation` VALUES (2781,'AIR','ʻIkamu','HAW','--ANY--','--ANY--','DfV9�H*�)wF�c�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3026,'AIR','Se produjo un problema con el sistema.  Proporcione este número al AP.','ESN','--ANY--','--ANY--','D���p�Ci����h5;','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2630,'SBAC','Libere el botón del mouse para soltarlo donde desee colocarlo.','ESN','--ANY--','--ANY--','D�;��Iօ�\n:&�2','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2686,'AIR','Inicializando…','ESN','--ANY--','--ANY--','D��=�GB\"��/���A','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2580,'AIR','Se produjo un problema con la solicitud de impresión.  Inténtelo nuevamente o consulte con el administrador de pruebas.','ESN','--ANY--','--ANY--','D�=���K�\Z���O��','2014-03-22 20:33:38.127');
INSERT INTO `client_messagetranslation` VALUES (2862,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','D��h�HHk�;�G�[','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3009,'AIR','Enviar','ESN','--ANY--','--ANY--','D�j�/-K����j���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3199,'AIR','Pono ?oe e kaomi i ka pihi [Ho?onoho] a i ?ole ka pihi [Ho?onohonoho & ?apono] no ?a ?apono ?ana i keia mau ho?onohonoho ho?ike no ka wa ho?ohana.Ho?ohana i ka pihi [Ho?onoho] no ka ?apono ?ana i ho?onohonoho a e ho?i hou i ka pukaaniani ?Apono no ka ?apono ?ana i keia haumana nei.','HAW','--ANY--','--ANY--','D�o�{H�i>,���','2013-12-18 14:09:06.987');
INSERT INTO `client_messagetranslation` VALUES (3082,'AIR','?A?ohe kuleana o ka mea ho?ohana no ke komo ?ana','HAW','--ANY--','--ANY--','D��`�L\\�.:��B��','2013-12-18 14:09:06.900');
INSERT INTO `client_messagetranslation` VALUES (2998,'AIR','SSID','ESN','--ANY--','--ANY--','E�U��K��/KM#��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2710,'AIR','FALSO','ESN','--ANY--','--ANY--','E}J3�I��<�;u��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3409,'AIR','Ha llegado al final de la prueba.  Haga clic en [Sí] para pasar a la página siguiente.  Haga clic en [No] para continuar con la prueba.','ESN','--ANY--','--ANY--','EbwT�p@2������c','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2799,'AIR','Imprimir item','ESN','--ANY--','--ANY--','E�G�E��q��?�#','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2392,'SBAC','State-SSID:<p style=\"font-size:0.6em;\">(ex: ST-9999999123)</p>','ENU','--ANY--','--ANY--','E����L �]٘��w','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2613,'AIR','Opción','ESN','--ANY--','--ANY--','F,��^A\n����#�u�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3142,'AIR','ʻAʻole hiki iā ʻoe ke hana i ka hōʻike ma kēia wā hana hō’ike. E nīnau iā Kumu no ke kōkua.','HAW','--ANY--','--ANY--','F0���JM��m���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3724,'AIR','Memo','HAW','--ANY--','--ANY--','F9�f\0DX�N}���','2013-08-13 12:50:12.157');
INSERT INTO `client_messagetranslation` VALUES (3030,'AIR','Hiki ?ole ke loa?a na ?ikepili ho?ike ma ke kahua RTS','HAW','--ANY--','--ANY--','FF�T�\\C�������','2013-12-18 14:09:06.857');
INSERT INTO `client_messagetranslation` VALUES (2584,'AIR','Diga la selección','ESN','--ANY--','--ANY--','FK��0eK���.�+z','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3269,'AIR','Ua pani ?ia keia wa ho?ohana nei.','HAW','--ANY--','--ANY--','Fe�7��E��ǩ>�\\��','2013-12-18 14:09:07.070');
INSERT INTO `client_messagetranslation` VALUES (3563,'AIR','?A?ole','HAW','--ANY--','--ANY--','F����[F��n��W��','2013-12-18 14:09:07.433');
INSERT INTO `client_messagetranslation` VALUES (3680,'AIR','Falló la asignación de satélite','ESN','--ANY--','--ANY--','F�݅~�D˼��˞���','2014-03-22 20:36:39.867');
INSERT INTO `client_messagetranslation` VALUES (3146,'AIR','No puede realizar esta prueba hasta {0}.','ESN','--ANY--','--ANY--','F�H��J��T���5¯','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2949,'AIR','Sí','ESN','--ANY--','--ANY--','F��ǁ�Bq�F�	��','2014-03-22 20:34:47.193');
INSERT INTO `client_messagetranslation` VALUES (2826,'AIR','Waiho i mana?o no keia ?ikamu pono no na ho?ike.','HAW','--ANY--','--ANY--','Gx�BM�����','2013-12-18 14:09:06.697');
INSERT INTO `client_messagetranslation` VALUES (3005,'AIR','Loa?a elua mau polokalamu i ho?okomo ?ia ma keia kamepuila nei i hiki ?ole ke ho?ohana ?ia.?Olu?olu e ho?ike aku i kekahi haku ha?awi ho?ike.No keia manawa, pono paha kou ho?ohana ?ana i kekahi kamepiula ?ea?e.','HAW','--ANY--','--ANY--','G%f y�Hk�yp:e�c6','2013-12-18 14:09:06.830');
INSERT INTO `client_messagetranslation` VALUES (2952,'AIR','Nivel 2','ESN','--ANY--','--ANY--','GF���K��0�Eٰ�n','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2556,'AIR','Cerrar','ESN','--ANY--','--ANY--','Gz\0���Ln��yi��N','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2903,'AIR','Leer oralmente las instrucciones por escrito','ESN','--ANY--','--ANY--','G�O?a�@ߖ�V�Xs','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2359,'AIR','Ka mikini helu','HAW','--ANY--','--ANY--','G�Ш�E���TeD��C','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2893,'AIR','E hoʻouna mai i manaʻo no kēia ʻikamu','HAW','--ANY--','--ANY--','G�T�O������','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2692,'AIR','Tamaño de fuente','ESN','--ANY--','--ANY--','G���E}�&��Ч','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3103,'AIR','Su escuela no está administrando esta prueba en línea.','ESN','--ANY--','--ANY--','H,�J�N_�\\e��G�','2014-03-22 20:35:48.647');
INSERT INTO `client_messagetranslation` VALUES (2438,'AIR','Si escucha el sonido, haga clic en [Sí], de lo contrario, haga clic en [No].','ESN','--ANY--','--ANY--','HU-���L5�G�RML','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3625,'AIR','<span>?Ae, ho?omaka i ka ho?okomo ?ana i na ?ikepili</span>','HAW','--ANY--','--ANY--','H�9�V�B����\\O','2013-12-18 14:09:07.513');
INSERT INTO `client_messagetranslation` VALUES (2842,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','H�	�VuG��IǪ�Q��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2675,'AIR','Imprimir','ESN','--ANY--','--ANY--','I��IB���̾�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3550,'AIR','Ka la & Ka manawa o ka noi','HAW','--ANY--','--ANY--','IE��)J\\�A��#_�','2013-12-18 14:09:07.417');
INSERT INTO `client_messagetranslation` VALUES (3436,'AIR','Are you sure you want to pause?','ESN','--ANY--','--ANY--','IZɩ)H飿�ҫ���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2955,'AIR','Menú desplegable','ESN','--ANY--','--ANY--','Im�x/�Fٔ�ع��/Q','2014-03-22 20:34:47.197');
INSERT INTO `client_messagetranslation` VALUES (2523,'AIR','<span>Revisar respuestas</span>','ESN','--ANY--','--ANY--','I}�@x\\G�}yG ��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3509,'AIR','Na noi','HAW','--ANY--','--ANY--','I�*l��C����>��d�','2013-12-18 14:09:07.353');
INSERT INTO `client_messagetranslation` VALUES (2782,'AIR','Ejercicio y pasaje','ESN','--ANY--','--ANY--','I��#�\"K=��r�L=b�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3454,'AIR','Na hana ','HAW','--ANY--','--ANY--','I����Fκ�%#�u�','2013-12-18 14:09:07.270');
INSERT INTO `client_messagetranslation` VALUES (2938,'AIR','Braille contratado','ESN','--ANY--','--ANY--','I�!3	�L��\r�*��\'','2014-03-22 20:34:28.187');
INSERT INTO `client_messagetranslation` VALUES (3539,'AIR','E ho?okomo i kekahi inoa hope','HAW','--ANY--','--ANY--','I�ѤI��_\0���5','2013-12-18 14:09:07.397');
INSERT INTO `client_messagetranslation` VALUES (2512,'AIR','Usted ha marcado preguntas. Revisar estas preguntas antes de terminar su prueba.','ESN','--ANY--','--ANY--','J��e�KŎؽ�d6+O','2014-03-22 20:33:38.123');
INSERT INTO `client_messagetranslation` VALUES (3244,'SBAC','PT','ENU','--ANY--','--ANY--','J|C\n9FǪ�>Q�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2821,'AIR','Waiho i mana?o no keia ?ikamu pono no na ho?ike.','HAW','--ANY--','--ANY--','J1��\"}H���:m�','2013-12-18 14:09:06.693');
INSERT INTO `client_messagetranslation` VALUES (3110,'AIR','?A?ole hiki ke haku kekahi ID ku kahi.','HAW','--ANY--','--ANY--','J{^NlNϽ��l�)@e','2013-12-18 14:09:06.927');
INSERT INTO `client_messagetranslation` VALUES (3640,'AIR','Plataforma:','ESN','--ANY--','--ANY--','J���H��w:e��6','2014-03-22 20:36:39.620');
INSERT INTO `client_messagetranslation` VALUES (2871,'AIR','Someter un comentario para este ítem.','ESN','--ANY--','--ANY--','K��*~@=�SܰT��0','2014-03-22 20:34:28.053');
INSERT INTO `client_messagetranslation` VALUES (2535,'AIR','E Kahiauli','HAW','--ANY--','--ANY--','K:�E���9�8�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3483,'AIR','Koho pau loa','HAW','--ANY--','--ANY--','K[y�kmNR��1�sB\Z�','2013-12-18 14:09:07.317');
INSERT INTO `client_messagetranslation` VALUES (3147,'AIR','Aia he pilikia me ka ‘ōnaehana.','HAW','--ANY--','--ANY--','Ke\r,ZDA=�8z�6�S','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2858,'AIR','E pani i ka mana?o','HAW','--ANY--','--ANY--','K�_as\rCʮJͳwHg�','2013-12-18 14:09:06.720');
INSERT INTO `client_messagetranslation` VALUES (2609,'AIR','Hale Hoʻokani','HAW','--ANY--','--ANY--','K����K���gh��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3444,'AIR','Kuhikuhi, na kokua kumu ho?olalelale a me na ?ikemu','HAW','--ANY--','--ANY--','K�3�!rD:���SeH9S','2013-12-18 14:09:07.253');
INSERT INTO `client_messagetranslation` VALUES (3247,'AIR','Ua pau ka pono o ke kuki a i ?ole ua nalowale ke kuki no keia kahua nei.?Olu?olu e ha?alele a laila e ?e?e hou no ke kukulu ?ana i wa ho?ohana hou.','HAW','--ANY--','--ANY--','K���D���Q�z�','2013-12-18 14:09:07.033');
INSERT INTO `client_messagetranslation` VALUES (3024,'AIR','Loa?a na wahi i hemo ma kau kamepiula.Pono e wehe ?ia keia hana nei ma mua o ka?e?e ?ana. Noi i kekahi kahu ha?awi ho?ike no ke kokua.','HAW','--ANY--','--ANY--','K�\\6s\ZK���fF�$','2013-12-18 14:09:06.853');
INSERT INTO `client_messagetranslation` VALUES (3267,'AIR','Pono e ?apono ?ia na ?apana','HAW','--ANY--','--ANY--','L���F���,g�','2013-12-18 14:09:07.067');
INSERT INTO `client_messagetranslation` VALUES (3443,'AIR','¿Está seguro de que desea finalizar la prueba?','ESN','--ANY--','--ANY--','L����H�����FS,�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2380,'AIR','Debe seleccionar un tema antes de pasar a la página siguiente.','ESN','--ANY--','--ANY--','L�=��B�V�>�:','2014-03-22 20:33:38.097');
INSERT INTO `client_messagetranslation` VALUES (2394,'AIR','Ka Helu Mahele Ho\'ike:','HAW','--ANY--','--ANY--','Lѱ̇�Eۀ)T��7<','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2588,'AIR','Mai ʻōlelo','HAW','--ANY--','--ANY--','M\0��p�GЙu���F�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2566,'AIR','A\'ole i loa\'a keia hi\'ohi\'ona ma ka ho\'ike \'olelo Hawai\'i.','HAW','--ANY--','--ANY--','M5\rK�IY�k�%�\'��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2638,'AIR','Ua lohe anei ‘oe i ke kani? E kaomi [‘Ae] ‘a i ‘ole [‘A‘ole]','HAW','--ANY--','--ANY--','M�m�0KѴ���)�!','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2875,'AIR','Imprimir item','ESN','--ANY--','--ANY--','M��\0Ay�z�1CCh','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2516,'SBAC','Entregó el examen satisfactoriamente.','ESN','--ANY--','--ANY--','M�[e%Mz���ѐmi','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3731,'SBAC','There is no match for the State-SSID you entered.','ENU','--ANY--','--ANY--','M0��~	J��e�-/U�','2013-09-05 14:08:45.230');
INSERT INTO `client_messagetranslation` VALUES (2615,'AIR','Mai hoʻokani i ka lipine.','HAW','--ANY--','--ANY--','M:�:x�Cϊ����&NH','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2937,'AIR','?A?ohe ?aelike','HAW','--ANY--','--ANY--','MKp���N��, n���','2013-12-18 14:09:06.750');
INSERT INTO `client_messagetranslation` VALUES (2538,'AIR','Pausa','ESN','--ANY--','--ANY--','Mp�Q\\@@���*F���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3523,'AIR','Mea nui!','HAW','--ANY--','--ANY--','M����O�\"�6�4,�','2013-12-18 14:09:07.370');
INSERT INTO `client_messagetranslation` VALUES (3703,'SBAC','{silence} D.{silence}','ENU','--ANY--','--ANY--','M�0�\\�Fd���@��!','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2776,'AIR','VERDADERO','ESN','--ANY--','--ANY--','M���B���4�Q~�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3635,'AIR','Pa?a leo e ku nei:','HAW','--ANY--','--ANY--','M�b�\rWN��]z�*�','2013-12-18 14:09:07.530');
INSERT INTO `client_messagetranslation` VALUES (3658,'AIR','Mo?olelo ana holo kikowaena punaewele:','HAW','--ANY--','--ANY--','M���eM2���R*�g3','2013-12-18 14:09:07.563');
INSERT INTO `client_messagetranslation` VALUES (2913,'AIR','Ke ho\'ouka \'ia nei ka \'ike o ka \'ao\'ao.','HAW','--ANY--','--ANY--','M��-dwJ\Z��J�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2462,'AIR','Ka Nui O ka Pa\'i \'Ana','HAW','--ANY--','--ANY--','M����M{� 	�F)��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2646,'AIR','Ua lohe anei ‘oe i ke kani ma ka ‘ōlelo Paniolo? E kaomi [‘Ae] ‘a i ‘ole [‘A‘ole]','HAW','--ANY--','--ANY--','Mс}��E����j^�S�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2848,'AIR','E pani i ka mana?o','HAW','--ANY--','--ANY--','M��x�HN��U�*�!�','2013-12-18 14:09:06.713');
INSERT INTO `client_messagetranslation` VALUES (2941,'AIR','?Ikemu','HAW','--ANY--','--ANY--','Nyr+\'@幅��M�J','2013-12-18 14:09:06.757');
INSERT INTO `client_messagetranslation` VALUES (3258,'AIR','Ka ?olelo','HAW','--ANY--','--ANY--','N�4��D%��!�Ss','2013-12-18 14:09:07.050');
INSERT INTO `client_messagetranslation` VALUES (3039,'AIR','La sesión no existe.','ESN','--ANY--','--ANY--','NET�;B���s�XBB�','2014-03-22 20:35:11.297');
INSERT INTO `client_messagetranslation` VALUES (3538,'AIR','E ho?okomo i kekahi inoa mua','HAW','--ANY--','--ANY--','Ni���jG��a���?�','2013-12-18 14:09:07.397');
INSERT INTO `client_messagetranslation` VALUES (2453,'SBAC','<span>Cerrar sesión</span>','ESN','--ANY--','--ANY--','Nl�Z��J��y~�SB&','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3148,'SBAC','Your State-SSID is not entered correctly. Please try again or ask your TA.','ENU','--ANY--','--ANY--','N��N�D�>r&�4t�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3732,'AIR','../Projects/Delaware/Help/help_esn.html','ESN','--ANY--','--ANY--','N�_�,�M�n�W��','2013-09-18 13:21:37.113');
INSERT INTO `client_messagetranslation` VALUES (3018,'AIR','No se puede completar el análisis. Por favor, contactar el puesto de informaciones.','ESN','--ANY--','--ANY--','N�U�HҠrȱ����','2014-03-22 20:35:11.290');
INSERT INTO `client_messagetranslation` VALUES (2610,'AIR','Detener','ESN','--ANY--','--ANY--','N���\r(K���I|�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3114,'AIR','ʻAʻole hiki i ka hoakipa ke kāinoa','HAW','--ANY--','--ANY--','O	fźNɈ�Шza�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2637,'SBAC','<span style=\"display:none;\" lang=\"ENU\">This is some sample text to test your settings.</span>','ENU','--ANY--','--ANY--','O�f4�Cx��||�g�O','2014-03-25 16:53:54.550');
INSERT INTO `client_messagetranslation` VALUES (2688,'AIR','fuera de','ESN','--ANY--','--ANY--','O!���)@6�I�����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2755,'AIR','ʻAʻohe','HAW','--ANY--','--ANY--','O?��O��ֆJLx0�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2436,'AIR','Verificación de sonido: ¿Escucha la voz?','ESN','--ANY--','--ANY--','OA\Z�C�f��a�:�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2454,'AIR','Se necesita Flash 10 o una versión superior para realizar esta prueba.','ESN','--ANY--','--ANY--','OJ��+B��Iho5z�','2014-03-22 20:33:38.107');
INSERT INTO `client_messagetranslation` VALUES (3287,'SBAC','TA Interface','ENU','--ANY--','--ANY--','OK��\n�H`�-���\n|�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2436,'AIR','No Ka Leo Kani: Ua lohe ʻoe i kou leo?','HAW','--ANY--','--ANY--','OfаL�A\0��\r���\"�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2843,'AIR','Cerrar Comentario','ESN','--ANY--','--ANY--','O�]�kZKA�HG\\#�','2014-03-22 20:34:06.440');
INSERT INTO `client_messagetranslation` VALUES (3639,'AIR','Agente de Usuario:','ESN','--ANY--','--ANY--','O�z��Gｫ���C','2014-03-22 20:36:39.613');
INSERT INTO `client_messagetranslation` VALUES (2491,'AIR','Ir al editor de texto','ESN','--ANY--','--ANY--','OЮPa�B���r��\r�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2609,'AIR','Recurso de audio','ESN','--ANY--','--ANY--','P���O��FcN���V','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3228,'AIR','Koho i ke kula','HAW','--ANY--','--ANY--','PN���A��\n�a�_�','2013-12-18 14:09:07.020');
INSERT INTO `client_messagetranslation` VALUES (3632,'AIR','Configuración de sonido','ESN','--ANY--','--ANY--','P3�L��G9�x�;�bw�','2014-03-22 20:36:39.570');
INSERT INTO `client_messagetranslation` VALUES (2865,'AIR','Imprimir item','ESN','--ANY--','--ANY--','P4��H��E�P\"��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3051,'AIR','No se encuentra el banco de ítems','ESN','--ANY--','--ANY--','PB\r��Hܽ�\\3/Y4o','2014-03-22 20:35:32.320');
INSERT INTO `client_messagetranslation` VALUES (3098,'AIR','Usted no tiene permiso para acceder a este sistema. Por favor, compruebe si ha completado el Curso de Certificación TA en línea, o si tiene asignado el rol correcto para la administración de pruebas. Por favor comuníquese con el coordinador de pruebas de su escuela para obtener ayuda.','ESN','--ANY--','--ANY--','PU�e�K��R+D!>\0�','2014-03-22 20:35:48.643');
INSERT INTO `client_messagetranslation` VALUES (3605,'AIR','Lenguaje natural','ESN','--ANY--','--ANY--','Pnf���B��6\n=�','2014-03-22 20:36:39.427');
INSERT INTO `client_messagetranslation` VALUES (3049,'AIR','Hiki ?ole ke loa?a na ?ikepili ho?ike ma ke kahua RTS','HAW','--ANY--','--ANY--','Pr\"�K�J6��x/̺�','2013-12-18 14:09:06.870');
INSERT INTO `client_messagetranslation` VALUES (2986,'AIR','{silence}Option D.{silence}','ESN','--ANY--','--ANY--','P{[V�dOȠaY�篋','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3630,'AIR','Proporción','ESN','--ANY--','--ANY--','P�\\�җNi�������','2014-03-22 20:36:39.557');
INSERT INTO `client_messagetranslation` VALUES (3290,'AIR','<h3>Mana?o</h3><p>E ho?oku wale ?ia ana na ho?ike ina loa?a kekahi pilikia e like me ka pio ?ana o ka uila, pilikia ka punaewele puni honua, pilikia ka polokalamu punaewele puni honu, pio ke kamepiula, a pela aku.</p>','HAW','--ANY--','--ANY--','P��ckBs�F1�K��','2013-12-18 14:09:07.097');
INSERT INTO `client_messagetranslation` VALUES (3091,'AIR','La sesión de prueba se expiró el {0}. Para obtener más ayuda, consulte al administrador de pruebas.','ESN','--ANY--','--ANY--','P��j�<C��Kc!\nj8','2014-03-22 20:33:38.147');
INSERT INTO `client_messagetranslation` VALUES (2761,'SBAC','Passages and Items','ENU','--ANY--','--ANY--','Pϫ�WmJJ� \0L*�)','2013-09-06 14:30:49.693');
INSERT INTO `client_messagetranslation` VALUES (3435,'AIR','La prueba está comenzando, espere…','ESN','--ANY--','--ANY--','Q)�`SO����NC','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2669,'AIR','Aviso','ESN','--ANY--','--ANY--','Q\'��D��Y�O�`;','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2342,'AIR','../tools/formulas/2010/de_g2_hundreds_esn.html','ESN','--ANY--','--ANY--','Q2��YIN�y�V1S','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3597,'AIR','Ho?ohana i na pihi ?uai no ka ho?onui ?ana i ka leo a i ?ole ke ki?eki?e o ka leo.Hiki ?ole ke ho?ololi ?ia na kuhikuhi i ka ho?omaka ?ana o kau ho?ike.','HAW','--ANY--','--ANY--','QCF��K���U����H','2013-12-18 14:09:07.460');
INSERT INTO `client_messagetranslation` VALUES (2485,'AIR','Hoʻokani I Ka Leo','HAW','--ANY--','--ANY--','QV0v��Ns��앿X��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3604,'SBAC','Technology-Enhanced','ENU','--ANY--','--ANY--','Qd?���Nͮ�%�`k�m','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2558,'AIR','Cerrar','ESN','--ANY--','--ANY--','Q���ˀH\'�E� .�f*','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2979,'AIR','{silence}Koho C.{silence}','HAW','--ANY--','--ANY--','Q���iD����-�c\'','2013-12-18 14:09:06.807');
INSERT INTO `client_messagetranslation` VALUES (2508,'AIR','Usted ha terminado la prueba. Puede cerrar la sesión.','ESN','--ANY--','--ANY--','Q��0@�Ed�Ǣ��%B�','2014-03-22 20:33:38.120');
INSERT INTO `client_messagetranslation` VALUES (2500,'SBAC','<a class=\"goPractice\" href=\"https://sbacpt.tds.airast.org/student\">Click here to go to the Practice and Training Test Site</a>','ENU','--ANY--','--ANY--','Q���F�L��� :jgr�','2014-03-11 12:35:15.633');
INSERT INTO `client_messagetranslation` VALUES (2342,'AIR','../tools/formulas/2010/de_g2_hundreds.html','HAW','--ANY--','--ANY--','Q����C���\"ukSN','2013-12-18 14:09:06.590');
INSERT INTO `client_messagetranslation` VALUES (2922,'AIR','<span class=\"noTTS\">La función texto a voz no está disponible.</span>','ESN','--ANY--','--ANY--','Q���Z�B!���R','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2421,'AIR','Esperando aprobación del AP…','ESN','--ANY--','--ANY--','R�SEBg���EHo{Q','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2777,'SBAC','Instructions, Stimuli and Items','ENU','--ANY--','--ANY--','R$4�NÏdTI(�#5','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3610,'AIR','Ho?oku i ka wala?au ?ana','HAW','--ANY--','--ANY--','R(���aG��S��1դ','2013-12-18 14:09:07.490');
INSERT INTO `client_messagetranslation` VALUES (3288,'SBAC','Accommodations','ENU','--ANY--','--ANY--','R<\0�$�B,�t�X����','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3661,'AIR','(versión 10 o superior requerida)','ESN','--ANY--','--ANY--','RGz,�L/�1iA��.�','2014-03-22 20:36:39.787');
INSERT INTO `client_messagetranslation` VALUES (2806,'AIR','Waiho i mana?o no keia ?ikamu.','HAW','--ANY--','--ANY--','Rh���)E��TMP��','2013-12-18 14:09:06.683');
INSERT INTO `client_messagetranslation` VALUES (3115,'AIR','ʻAʻole ʻāpono ʻia ka hoʻomaka/hoʻomaka hou ʻana o ka hōʻike e ka luna hōʻike.','HAW','--ANY--','--ANY--','Rr��]K���\n\'����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2668,'SBAC','La prueba se ha finalizado y está lista para entregarse.','ESN','--ANY--','--ANY--','R�X4kL!��OZ�̚�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2623,'SBAC','Seleccione 2 puntos para conectar o presione y arrastre para crear y conectar puntos.','ESN','--ANY--','--ANY--','R޷(^4G�����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3123,'AIR','Ua pani ?ia keia wa ho?ohana nei.','HAW','--ANY--','--ANY--','S#����L���둙�','2013-12-18 14:09:06.943');
INSERT INTO `client_messagetranslation` VALUES (3218,'AIR','Koho i ke kula','HAW','--ANY--','--ANY--','S*18�zI��Q�u�¿','2013-12-18 14:09:07.013');
INSERT INTO `client_messagetranslation` VALUES (3477,'AIR','Pa?i','HAW','--ANY--','--ANY--','SS%�cH߿�&/�҈','2013-12-18 14:09:07.307');
INSERT INTO `client_messagetranslation` VALUES (3694,'AIR','Glosario español','ESN','--ANY--','--ANY--','Sg/�E͈�ƭ.�ڪ','2014-03-22 20:36:39.953');
INSERT INTO `client_messagetranslation` VALUES (3021,'AIR','Dada la carga actual en su sistema, usted debe ser capaz de probar el número solicitado de estudiantes en este sitio. Usted está cerca de su capacidad para esta localidad.','ESN','--ANY--','--ANY--','SkZq��Hƙ,��N�ց','2014-03-22 20:35:11.290');
INSERT INTO `client_messagetranslation` VALUES (2589,'AIR','Diga la pregunta','ESN','--ANY--','--ANY--','S�z���LK���hs','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3692,'AIR','Bloc de notas','ESN','--ANY--','--ANY--','S�P�UO���C�*y�	','2014-03-22 20:36:39.947');
INSERT INTO `client_messagetranslation` VALUES (3045,'AIR','Hiki ?ole ke loa?a na ?ikepili ho?ike ma ke kahua RTS','HAW','--ANY--','--ANY--','S�^\"`�A��3���H\\�','2013-12-18 14:09:06.867');
INSERT INTO `client_messagetranslation` VALUES (2476,'AIR','Interfaz del estudiante','ESN','--ANY--','--ANY--','S��Z�Mm��q����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2509,'AIR','Actualmente no hay resultados para esta prueba. ','ESN','--ANY--','--ANY--','T��RI��A�d�B','2014-03-22 20:33:38.120');
INSERT INTO `client_messagetranslation` VALUES (3495,'SBAC','Submit State-SSID','ENU','--ANY--','--ANY--','T4�jF��}O fm��','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3074,'AIR','Kahiko kēia pane ma mua o ka pane i loaʻa i kēia manawa','HAW','--ANY--','--ANY--','T���M/�_$R���6','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2616,'AIR','E hoʻokani i ka lipine.','HAW','--ANY--','--ANY--','T�OT	Jx��cF�M�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3611,'AIR','Continúe hablando','ESN','--ANY--','--ANY--','T>��m�I��B#<��G','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2427,'AIR','<span>Sí, comenzar la prueba</span>','ESN','--ANY--','--ANY--','T?_��A��Uc��m','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3269,'AIR','La sesión esta cerrada.','ESN','--ANY--','--ANY--','T���Gߝ��9��','2014-03-22 20:39:31.230');
INSERT INTO `client_messagetranslation` VALUES (2746,'AIR','OR 6-8 Makemakika','HAW','--ANY--','--ANY--','T��/�DH�%;a�','2013-12-18 14:09:06.660');
INSERT INTO `client_messagetranslation` VALUES (2406,'AIR','<span>No</span>','ESN','--ANY--','--ANY--','T�5��M-��Ĝ0��s','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2595,'AIR','Diga la opción C','ESN','--ANY--','--ANY--','T�	�&>F#��H\nl��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3719,'AIR','Kaomi i ka pihi (?)','HAW','--ANY--','--ANY--','T�t�C@A�p-Q�S{','2013-12-18 14:09:07.650');
INSERT INTO `client_messagetranslation` VALUES (2698,'AIR','Idioma','ESN','--ANY--','--ANY--','T��1:�ON����{��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3603,'SBAC','Technology-Enhanced','ENU','--ANY--','--ANY--','UU؎�Eu�kR�b�.�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2641,'AIR','Omitir la verificación de audio en inglés.','ESN','--ANY--','--ANY--','U�s�A�C����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2874,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','UJeCO$@ �9\"{�G��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3540,'AIR','Huli','HAW','--ANY--','--ANY--','U]�B$�Il��4��҂Y','2013-12-18 14:09:07.400');
INSERT INTO `client_messagetranslation` VALUES (3715,'AIR','Ua pio ke kokua ?alaka?i komo.?Olu?olu e ho?ike i kekahi kahu ha?awi ho?ike kapu.(Ma mua o ka ho?a ?ana i ke kokua ?alaka?I, e nana i ka leo ma luna o kau ipad ina ua ho?onui ?ia i hiki ia?oe ke lohe i ka leo.)','HAW','--ANY--','--ANY--','U�����E���6�\r�j.','2013-12-18 14:09:07.647');
INSERT INTO `client_messagetranslation` VALUES (2485,'AIR','Reproducir grabación','ESN','--ANY--','--ANY--','U��gbB}��|ho\"L','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3575,'AIR','Volumen','ESN','--ANY--','--ANY--','VE=�-�M;�p)+��^','2014-03-22 20:39:31.233');
INSERT INTO `client_messagetranslation` VALUES (2441,'AIR','Verificación de sonido: Grabe su voz.','ESN','--ANY--','--ANY--','V��A6�K��A�\n�U{','2014-03-22 20:33:38.107');
INSERT INTO `client_messagetranslation` VALUES (2407,'AIR','<span>ʻAe</span>','HAW','--ANY--','--ANY--','V�\Z���H��h�\n[\n�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2423,'AIR','¿Es ésta su prueba?','ESN','--ANY--','--ANY--','V�,�Y�J����J;6�&','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2915,'AIR','La solicitud de contenido requiere la autenticación del usuario. Vuelva a conectarse e intente nuevamente.','ESN','--ANY--','--ANY--','V헶�TA�s��%�J','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2660,'AIR','Reanudar prueba','ESN','--ANY--','--ANY--','V�a]3I�3�嬹��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3087,'AIR','ʻAʻole hiki ke loaʻa wā hana hōʻike, e ʻoluʻolu, e ʻōlelo me kāu luna hōʻike.','HAW','--ANY--','--ANY--','W��nD��P�s1�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2574,'AIR','Atención','ESN','--ANY--','--ANY--','W\nT�x5LS���݋bf�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3206,'AIR','Makemake ?io a nei ?oe e ?apono aku i na haumana a pau e ho?ike ?ia nei ma keia pukaaniani nei?<br/>Mana?o:Hiki ?ole ke ho?ololi ?ia na ho?onohonoho ho?ike ma hope o ka ?apono ?ia ?ana o ka haumana.','HAW','--ANY--','--ANY--','W�}w	O��,b�>�d','2013-12-18 14:09:06.997');
INSERT INTO `client_messagetranslation` VALUES (2472,'SBAC','<span>Cancelar</span>','ESN','--ANY--','--ANY--','W�o�YB�ޯ2��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2716,'AIR','Estándar','ESN','--ANY--','--ANY--','W+!���LW�����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2395,'AIR','<span>E Kainoa</span>','HAW','--ANY--','--ANY--','WS�D��I}�gH�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2629,'AIR','Mueva el objeto a una nueva ubicación y haga clic donde desee colocarlo.','ESN','--ANY--','--ANY--','WX`�|�@?��X�A��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3530,'AIR','Na pilina no ka: ','HAW','--ANY--','--ANY--','W`(��L��w��F��','2013-12-18 14:09:07.383');
INSERT INTO `client_messagetranslation` VALUES (2518,'AIR','Ka Helu Haumana:','HAW','--ANY--','--ANY--','Wq\0ڪE\r���,���\r','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2853,'AIR','Cerrar Comentario','ESN','--ANY--','--ANY--','Wv3�Ja���x�E0','2014-03-22 20:34:06.480');
INSERT INTO `client_messagetranslation` VALUES (2542,'AIR','Sí','ESN','--ANY--','--ANY--','W�f®�@F��1�0��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2724,'AIR','Color invertido','ESN','--ANY--','--ANY--','W�ciC��M�_|yA','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3021,'AIR','Ma muli o ke ?ano o ka ho?oili ?ana o kau kahua kamepiula, hiki ia?oe ke ha?awi aku i keia ho?ike i ka nui haumana au i makemake ai ma keia wahi nei.Ua ho?ea maila ?oe i ka palena o ka nui o na haumana i ?ae ?ia ma keia wahi nei.','HAW','--ANY--','--ANY--','W����G7�����F�','2013-12-18 14:09:06.850');
INSERT INTO `client_messagetranslation` VALUES (2492,'AIR','Aia he pilikia ma ka ho‘ohui ‘ana i ka Pūnaewele. E ‘olu‘olu, e alia iki ‘oe i kāu hō‘ike a e hana hou.','HAW','--ANY--','--ANY--','X>p�ELz�oI�ڗC','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2716,'AIR','Ana Loi','HAW','--ANY--','--ANY--','X0m\nC�I��7��E�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2797,'AIR','E kuapo i ka mana?o','HAW','--ANY--','--ANY--','X[�=s�N%������','2013-12-18 14:09:06.673');
INSERT INTO `client_messagetranslation` VALUES (2607,'AIR','Koho Hoʻokani','HAW','--ANY--','--ANY--','X�/(G�L��ӂ�:�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3419,'AIR','¿Es ésta su prueba?','ESN','--ANY--','--ANY--','X��`G����u;�P','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2468,'AIR','Haga clic aquí para volver a la pantalla de inicio de sesión. ','ESN','--ANY--','--ANY--','Xǹk�Ou��?�U�P','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3676,'AIR','Na ninau:','HAW','--ANY--','--ANY--','X�#3aC!����*&�','2013-12-18 14:09:07.593');
INSERT INTO `client_messagetranslation` VALUES (3445,'AIR','Espere. Recuperando los resultados de la prueba.','ESN','--ANY--','--ANY--','Y3�7�cK-�n&{ꚅ�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3644,'AIR','Mana ko?iko?i:','HAW','--ANY--','--ANY--','YI!��\ZGȩ�����[','2013-12-18 14:09:07.540');
INSERT INTO `client_messagetranslation` VALUES (2507,'AIR','Desplácese hacia abajo para obtener información adicional.','ESN','--ANY--','--ANY--','Y[���Cɾ3�{L�!\"','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3535,'AIR','Na pae a pau','HAW','--ANY--','--ANY--','Y�7b�HV�]��Ŗ�','2013-12-18 14:09:07.390');
INSERT INTO `client_messagetranslation` VALUES (2370,'AIR','No puede iniciar sesión hasta no salir de los siguientes programas:','ESN','--ANY--','--ANY--','Y�؂aH;��%\0F','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2783,'AIR','Stimulus','HAW','--ANY--','--ANY--','Z�ل\\Hz��x��SNm','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2486,'AIR','Diga el texto seleccionado','ESN','--ANY--','--ANY--','Z\n3�5kA����B�y','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2765,'AIR','Oi aku ka nui','HAW','--ANY--','--ANY--','Z�,Cs�ԟ�@;� ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3649,'AIR','Su versión de navegador:','ESN','--ANY--','--ANY--','ZWC5�kNĕ�TJ��','2014-03-22 20:36:39.687');
INSERT INTO `client_messagetranslation` VALUES (2706,'AIR','Nā \'ōkuhi no ka hana \'ana ','HAW','--ANY--','--ANY--','Z[��j�C���tT^C��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3057,'AIR','No se puede encontrar la base de datos RTS','ESN','--ANY--','--ANY--','Z~w��\ZJu�5��	�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2583,'AIR','Pilikia','HAW','--ANY--','--ANY--','Z�)��L�����إz�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2632,'SBAC','Haga clic para soltar el objeto donde desee colocarlo.','ESN','--ANY--','--ANY--','Z��r�I���\'�׽k','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2416,'AIR','\'Oi aku ka nui','HAW','--ANY--','--ANY--','Z�[{�O������7','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2545,'AIR','<span>ʻO ia</span>','HAW','--ANY--','--ANY--','[풛GGс��վu��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2511,'AIR','Hiki ia \'oe ke ho\'i a nana hou i kau mau pane, a i \'ole e kaomi i [Waiho I Ka Ho\'ike} ina pau ka ho\'ike. \'A\'ole hiki ia \'oe ke ho\'ololi i kau mau pane ma hope o ka waiho \'ana i kau ho\'ike.','HAW','--ANY--','--ANY--','[(ʧ�fKԱ3�\ZF{ӌ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3428,'AIR','?Aohe helu ?ai no keia ho?ike nei.','HAW','--ANY--','--ANY--','[6��F�Jж�\0����','2013-12-18 14:09:07.227');
INSERT INTO `client_messagetranslation` VALUES (2499,'SBAC_PT','<p>To log in to the Practice Test, simply select [Sign In], then navigate through the login screens. </p><!-- <p>For Students:</p> <ol><li>Uncheck the Guest User and Guest Session checkboxes.</li><li>Enter your first name into the First Name box.</li><li>Enter your two-digit state code and your SSID into the State-SSID box</li><li>Enter the Session ID that your TA gave you. </li><li>Click [Sign In].</li> </ol></p> -->','ENU','--ANY--','--ANY--','[;[ML%J���g\nќ�\Z','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3420,'AIR','¿Es ésta su prueba?','ESN','--ANY--','--ANY--','[@�14[I����FC�[','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2658,'SBAC','Agregar flecha','ESN','--ANY--','--ANY--','[D<�\"�M����t!\n��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2838,'AIR','E pani i ka mana?o','HAW','--ANY--','--ANY--','[I���OԞ%����U�','2013-12-18 14:09:06.707');
INSERT INTO `client_messagetranslation` VALUES (3608,'SBAC','Technology-Enhanced','ENU','--ANY--','--ANY--','[t���BN�kA7r��','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2470,'AIR','E kaomi i [?]','HAW','--ANY--','--ANY--','[v�m�N�� oL�z�','2013-08-08 16:27:15.117');
INSERT INTO `client_messagetranslation` VALUES (2663,'AIR','Na \'okuhi no ka hana \'ana ','HAW','--ANY--','--ANY--','[�r�\"IB�1����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2366,'AIR','No puede iniciar sesión con este navegador. Use el navegador seguro para realizar esta prueba.','ESN','--ANY--','--ANY--','[�X\"+>KݷDa\0b�>','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2455,'AIR','<span>Cerrar sesión</span>','ESN','--ANY--','--ANY--','[�T��^@΀)���d5','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3429,'AIR','Ua pau kau ho?okomo ?ana i na ?ikepili.','HAW','--ANY--','--ANY--','[�{Å=H����!���-','2013-12-18 14:09:07.230');
INSERT INTO `client_messagetranslation` VALUES (2427,'AIR','<span>ʻAe, E Hoʻomaka I Kaʻu Hōʻike</span>','HAW','--ANY--','--ANY--','\\?d�G���V�,��x','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2499,'AIR','<p>No ke komo ʻana i kou waihona haumāna (ma ka inoa/helu haumāna) :</p><ul><li> E holoi i ka māka o loko o ka pahu no ka \"Haumāna Kipa\"</li><li> E hoʻokomo ʻoe i kou Inoa Mua a me kou Helu Haumāna i loko o nā puka o luna. </li></ul><p> No ke kāinoa ʻana ma ke ʻano he haumāna (Haumāna Kipa) :</p><ul><li> E māka i ka pahu no ka \"Haumāna Kipa\" (E ʻike ʻia ʻo Haumāna Kipa ma nā wahi ʻelua) </li><li> E kaomi i ke [Kāinoa] no ke omo ʻana i ka Hōʻike Hoʻomaʻamaʻa ma ke ʻano he haumāna kipa. </li></ul><p><em> Wā Hana Haumāna Kipa? </em><br />Ma loko o ka Wā Hana Haumāna Kipa, ʻaʻole pono ka ʻāpono ʻana mai o ka luna hōʻike a hiki iā ʻoe ke hana i kekahi Hōʻike Hoʻomaʻamaʻa me ka hoʻohana ʻana i kou mau hiʻohiʻona. No ka hana ʻana i ka Hōʻike Hoʻomaʻamaʻa ma kekahi wā hana me ka luna hōʻike, e holoi i ka māka o ka pahu no ka \"Wā Hana Haumāna Kipa\" a e hoʻokomo i ka Helu Wā Hana i nā puka ma mua o ke kaomi ʻana i ka [Kāinoa]. </p>','HAW','--ANY--','--ANY--','\\5��N�A����4|y','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2941,'AIR','Item','ESN','--ANY--','--ANY--','\\r�>\'�C�h M�3�','2014-03-22 20:34:47.190');
INSERT INTO `client_messagetranslation` VALUES (2897,'AIR','Opihia na Kumumana\'o A Pau','HAW','--ANY--','--ANY--','\\��L��O}��^�s��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3033,'AIR','Aia he pilikia me ka ‘ōnaehana. E ‘olu’olu e hā’awi i kēia helu iā Kumu','HAW','--ANY--','--ANY--','\\�����Aj���Y�O��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3077,'AIR','No se puede encontrar la base de datos RTS','ESN','--ANY--','--ANY--','\\˲�v�ED���E��2�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2852,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--',']\0�e�	L��s�C^=N)','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3149,'AIR','A\'ole i hana \'ia ka papa āu i kikokiko ai. E \'olu\'olu \'oe, \'e ho\'ā\'o hou. ','HAW','--ANY--','--ANY--',']�Y��AT��Ͻ��s5','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2411,'AIR','Na Mea Pono\'i Hana Ho\'ike (He Koho):','HAW','--ANY--','--ANY--',']�K�D#��Lvȅ�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3070,'AIR','No se encuentra la base de datos del banco de ítems','ESN','--ANY--','--ANY--',']4���G��x���(�','2014-03-22 20:35:32.330');
INSERT INTO `client_messagetranslation` VALUES (3496,'AIR','Na loa?a mai ka huli ?ana','HAW','--ANY--','--ANY--',']FW�]<Mݽi���b�','2013-12-18 14:09:07.337');
INSERT INTO `client_messagetranslation` VALUES (2920,'AIR','Cancelar','ESN','--ANY--','--ANY--',']Je�R�I��%���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2533,'AIR','Imprimir','ESN','--ANY--','--ANY--',']^�\r��H���Q�1���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2353,'AIR','../tools/formulas/2010/hi_8_math.html','HAW','--ANY--','--ANY--',']�����Ai����\n*','2013-12-18 14:09:06.627');
INSERT INTO `client_messagetranslation` VALUES (2768,'AIR','Expandido','ESN','--ANY--','--ANY--',']�-9�D���2姫��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2602,'AIR','Opción oral F (en inglés)','ESN','--ANY--','--ANY--',']��Nb�E׍����7�\"','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2853,'AIR','E pani i ka mana?o','HAW','--ANY--','--ANY--',']����G���`�r��','2013-12-18 14:09:06.717');
INSERT INTO `client_messagetranslation` VALUES (2782,'AIR','Stimuli & ʻIkamu','HAW','--ANY--','--ANY--','^/}{r�K0�A�q�\r\r','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2885,'AIR','Si, he oido la voz','ESN','--ANY--','--ANY--','^Sn�qDu�~f��9�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2653,'AIR','No hay audio en este equipo.  Consulta al asistente técnico.','ESN','--ANY--','--ANY--','^p����H\r�r���M��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2628,'AIR','E koho i kahi o ka lepili.','HAW','--ANY--','--ANY--','^�a�O���RGv�k�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2987,'AIR','{silence}Option E.{silence}','ESN','--ANY--','--ANY--','^��k�I���y}(�Rh','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3274,'AIR','?Olu?olu e ho?oia ina ua ?apono ?oe i keia mau haumana {0} e ho?omaka a i ?ole e ho?omau i keia ho?ike nei.','HAW','--ANY--','--ANY--','^�v��M)���F\Z�![','2013-12-18 14:09:07.077');
INSERT INTO `client_messagetranslation` VALUES (3073,'AIR','ʻAʻole launa nā helu huna o ka ʻikamu','HAW','--ANY--','--ANY--','^�s[��J�Lo��-','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2773,'AIR','FALSO','ESN','--ANY--','--ANY--','^����GK���p�?�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2630,'AIR','Libere el botón del mouse para soltarlo donde desee colocarlo.','ESN','--ANY--','--ANY--','_$ԫF8Fj���~�DF�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3621,'SBAC','No puede regresar a esta parte del examen. ¿Está seguro de que quiere continuar?','ESN','--ANY--','--ANY--','_��I��H��$��ӎ�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2612,'AIR','Nīnau','HAW','--ANY--','--ANY--','_�3��F٘���H��l','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2844,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','_�f�CL�%o��h?','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3434,'AIR','Actualmente se está asignando un puntaje a su prueba.  Espere.','ESN','--ANY--','--ANY--','_�]k�ZI�T�`�δ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2783,'AIR','Estímulo','ESN','--ANY--','--ANY--','`��9�G^��b�l�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3663,'AIR','(versión 1.4 o superior requerida)','ESN','--ANY--','--ANY--','`�OZ�K[��L|�״�','2014-03-22 20:36:39.803');
INSERT INTO `client_messagetranslation` VALUES (2926,'AIR','Cerrar sesión','ESN','--ANY--','--ANY--','`;��s�CP����a|�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3245,'SBAC','PT','ENU','--ANY--','--ANY--','`�4��CV�I4���:','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3150,'SBAC','Your State-SSID is not entered correctly. Please try again or ask your TA.','ENU','--ANY--','--ANY--','`�i�|�L:�C��5�1�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2490,'AIR','E ho\'omaka hou i ka mea hana kahiauli','HAW','--ANY--','--ANY--','`�?�AJӜ����]1M','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2440,'AIR','<span>No</span>','ESN','--ANY--','--ANY--','`��5Q�@������4�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2588,'AIR','Deje de hablar','ESN','--ANY--','--ANY--','a�ج�N�ǳ;&�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3270,'AIR','?A?ohe ou kuleana i keia wa ho?ohana nei. ','HAW','--ANY--','--ANY--','a2ĠE��!�(��','2013-12-18 14:09:07.070');
INSERT INTO `client_messagetranslation` VALUES (2419,'AIR','Ka Hana','HAW','--ANY--','--ANY--','a�~��K֙�~��ʴ�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2480,'AIR','Mea Hoʻokani Nīnau','HAW','--ANY--','--ANY--','a(Lݞ|AJ�p���K�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2795,'AIR','Contraer todos los ejercisios','ESN','--ANY--','--ANY--','aR���G��(�bA�)','2014-03-22 20:33:38.080');
INSERT INTO `client_messagetranslation` VALUES (2384,'AIR','Ua pane \'oe i na ninau a pau. Ke pau ka ho\'oia \'ana i kau mau pane, e kaomi i [Pau Ka Ho\'ike].','HAW','--ANY--','--ANY--','am��vNɇ5+�c�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2603,'AIR','E ʻōlelo i ka hopunaʻōlelo','HAW','--ANY--','--ANY--','ao�y\"J���_<��Ɇ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2536,'AIR','E ho\'omaka hou i ka mea hana kahiauli','HAW','--ANY--','--ANY--','a�O�1K`�Ng�-ܙ�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3690,'AIR','Hewa a i ?ole ua pau ka wa ho?ohana o keia ID nei','HAW','--ANY--','--ANY--','a��r��O��gk=r�Y�','2013-12-18 14:09:07.610');
INSERT INTO `client_messagetranslation` VALUES (2895,'AIR','Hacer clic para cerrar','ESN','--ANY--','--ANY--','a�\\��uD��;>+��%','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2634,'AIR','Verificar el audio en inglés.','ESN','--ANY--','--ANY--','a��ոeM��zc��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2577,'AIR','Cargando, espere...','ESN','--ANY--','--ANY--','a��6�Am�I�Ϫ_)5','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2551,'AIR','Na \'okuhi no ka hana \'ana ','HAW','--ANY--','--ANY--','a�\ZߛO�A}���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3494,'AIR','Ha?alele','HAW','--ANY--','--ANY--','a�O�rD�����ͬ��','2013-12-18 14:09:07.333');
INSERT INTO `client_messagetranslation` VALUES (2999,'SBAC','State-SSID','ENU','--ANY--','--ANY--','bp�ґB|��k\\�I�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2560,'AIR','Cerrar','ESN','--ANY--','--ANY--','bQ���wB��\'�mŻ̑','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3519,'AIR','Ho?opa?a& ?Apono','HAW','--ANY--','--ANY--','b~��ǯBN��8�����','2013-12-18 14:09:07.367');
INSERT INTO `client_messagetranslation` VALUES (2619,'AIR','Ke Kakau \'Ana','HAW','--ANY--','--ANY--','c\r��<B��(��g��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3398,'AIR','Kainoa','HAW','--ANY--','--ANY--','c÷��L�4a\\F�j','2013-12-18 14:09:07.180');
INSERT INTO `client_messagetranslation` VALUES (2991,'AIR','Cargando el contenido de la página.','ESN','--ANY--','--ANY--','c&���\0M۝Q=v�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3278,'AIR','Na pa?i no na makapo','HAW','--ANY--','--ANY--','c*qy��@q���j2��~','2013-12-18 14:09:07.083');
INSERT INTO `client_messagetranslation` VALUES (3161,'AIR','ʻAʻole i loa’a kēia Helu Haumāna. E ʻoluʻolu, e hana hou ʻoe.','HAW','--ANY--','--ANY--','c+]��K\Z�C��CC','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2856,'AIR','Waiho i mana?o no keia ?ikamu pono no na ho?ike.','HAW','--ANY--','--ANY--','cF~x�cG���3\'�y��','2013-12-18 14:09:06.717');
INSERT INTO `client_messagetranslation` VALUES (2974,'AIR','Cerrar Sesión','ESN','--ANY--','--ANY--','c�}�n�OJ�\Zc��Ȃ�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3271,'AIR','Ua ho?one?e ?ia keia wa ho?ohana no keia ho?ike i kekahi kamepiula/polokalamu punaewele ?ea?e.E ho?omau ?ia ana keia ho?ike nei me ka imihana ?ole ?ia.','HAW','--ANY--','--ANY--','d��,�Oϟ}��S�','2013-12-18 14:09:07.073');
INSERT INTO `client_messagetranslation` VALUES (2841,'AIR','Someter un comentario para este ítem.','ESN','--ANY--','--ANY--','d!#\Z��O1�B�����','2014-03-22 20:34:06.430');
INSERT INTO `client_messagetranslation` VALUES (3662,'AIR','Versión JRE:','ESN','--ANY--','--ANY--','d,^]5G٥al3g�','2014-03-22 20:36:39.793');
INSERT INTO `client_messagetranslation` VALUES (2886,'AIR','E Ho\'oholo i Ke Kikokikona','HAW','--ANY--','--ANY--','d`���HC�G����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3153,'AIR','Este usuario tiene una sesión de prueba activa. Ingresar el id. de sesión para continuar administrando la sesión desde este equipo o navegador.','ESN','--ANY--','--ANY--','d�[2�#I;�T�߇49�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2778,'AIR','ʻŌkuhi','HAW','--ANY--','--ANY--','d��vi�HK��et�#','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2471,'AIR',' para acceder a la Guía de ayuda en cualquier momento durante la prueba.','ESN','--ANY--','--ANY--','e�`I�KN��taQ0�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3079,'AIR','No se encuentra la base de datos RTS','ESN','--ANY--','--ANY--','e15��fJJ�]�gN��','2014-03-22 20:35:32.330');
INSERT INTO `client_messagetranslation` VALUES (2350,'AIR','../tools/formulas/2010/or_CIM_math_esn.html','ESN','--ANY--','--ANY--','e�c��JҤ�a��!]�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3466,'AIR','Pono ka inoa ho?ohana','HAW','--ANY--','--ANY--','e�F�K�W+D�6�','2013-12-18 14:09:07.290');
INSERT INTO `client_messagetranslation` VALUES (2354,'AIR','../tools/formulas/2010/or_6_8_math.html','HAW','--ANY--','--ANY--','e��kpNA�b�D����','2013-12-18 14:09:06.627');
INSERT INTO `client_messagetranslation` VALUES (2434,'AIR','Java se detectó correctamente.','ESN','--ANY--','--ANY--','f�o�N����ӆ^�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2754,'AIR','\'A\'ole i loa\'a keia hi\'ohi\'ona ma ka ho\'ike \'olelo Hawai\'i.','HAW','--ANY--','--ANY--','f&}�`�I���>]wJ�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3008,'AIR','Enviar','ESN','--ANY--','--ANY--','f3U-[�J]��[X$��,','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3733,'AIR','Ha llegado al final de la prueba.  Haga clic en [Sí] para pasar a la página siguiente.  Haga clic en [No] para continuar con la prueba.','ESN','--ANY--','--ANY--','fU{g�zI\Z��U#\\}��','2013-09-18 15:47:20.480');
INSERT INTO `client_messagetranslation` VALUES (3063,'AIR','Su prueba se ha interrumpido. Para continuar la prueba, consulte con su Administrador de Pruebas.','ESN','--ANY--','--ANY--','f`��B����l���','2014-03-22 20:33:38.143');
INSERT INTO `client_messagetranslation` VALUES (2494,'AIR','Tachado','ESN','--ANY--','--ANY--','f����K����vO�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3092,'AIR','La sesión de prueba no se encuentra en un estado válido. Consulte al administrador de la prueba.','ESN','--ANY--','--ANY--','f���C,�����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3521,'AIR','Ho?ole','HAW','--ANY--','--ANY--','f�iWf�Nn��	��M%','2013-12-18 14:09:07.370');
INSERT INTO `client_messagetranslation` VALUES (2349,'AIR','../tools/formulas/2010/or_3_5_math.html','HAW','--ANY--','--ANY--','f�fm�GO9�c]:E���','2013-12-18 14:09:06.607');
INSERT INTO `client_messagetranslation` VALUES (3422,'AIR','Ha?alele a pani i ka polokalamu punaewele puni honua','HAW','--ANY--','--ANY--','f���!K�����̫�','2013-12-18 14:09:07.217');
INSERT INTO `client_messagetranslation` VALUES (3204,'AIR','Na haumana ma kau ho?ike wa ho?ohana','HAW','--ANY--','--ANY--','f�\"2�K۰�h���g','2013-12-18 14:09:06.993');
INSERT INTO `client_messagetranslation` VALUES (2946,'AIR','?A?ole hiki','HAW','--ANY--','--ANY--','g#R<�J����6=!','2013-12-18 14:09:06.767');
INSERT INTO `client_messagetranslation` VALUES (2634,'AIR','E hō‘ike i ke kani Pelekane.','HAW','--ANY--','--ANY--','gBE}�M\r�+���r3','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2548,'AIR','E kaomi i ke pihi [Pa\'i Mo\'olelo] no ka pa\'i \'ana i ka mo\'olelo','HAW','--ANY--','--ANY--','g�4��KИϧC�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3687,'AIR','?A?ohe URL no ka ukali','HAW','--ANY--','--ANY--','g����O\"����o�R','2013-12-18 14:09:07.607');
INSERT INTO `client_messagetranslation` VALUES (3720,'AIR','Data Entry Interface','ESN','--ANY--','--ANY--','g��@�CY�\'\"x̻3','2013-09-18 16:28:46.463');
INSERT INTO `client_messagetranslation` VALUES (2430,'AIR','Eia he mana\'o mai kau Kumu Ho\'ike mai:','HAW','--ANY--','--ANY--','g�g�?G)�E�n>�T�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3074,'AIR','Esta respuesta es anterior a la respuesta guardada actualmente','ESN','--ANY--','--ANY--','h�޾�E߀\Zw� >','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3665,'AIR','Volver a inicio','ESN','--ANY--','--ANY--','hO� N�����b8','2014-03-22 20:36:39.820');
INSERT INTO `client_messagetranslation` VALUES (2905,'AIR','Instrucciones orales (en inglés)','ESN','--ANY--','--ANY--','h%��7J0�	Ǻ̿�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2535,'AIR','Resaltar','ESN','--ANY--','--ANY--','h,�w�BI�%���@�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2393,'AIR','Ka Wa Hana Haumana Kipa','HAW','--ANY--','--ANY--','hD8i��D��r�IsĄ$','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2833,'AIR','Cerrar Comentario','ESN','--ANY--','--ANY--','h��@O�C�jE���t','2014-03-22 20:34:06.400');
INSERT INTO `client_messagetranslation` VALUES (2416,'AIR','El más grande','ESN','--ANY--','--ANY--','h����FL�\ZssJb','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2981,'AIR','{silence}Opción E.{silence}','ESN','--ANY--','--ANY--','h��3�k@Y����� ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3121,'AIR','La aprobación de la oportunidad de prueba ya no está pendiente. El estudiante pudo haber cancelado la petición.','ESN','--ANY--','--ANY--','h�.VNGx���*/H}','2014-03-22 20:35:48.660');
INSERT INTO `client_messagetranslation` VALUES (3093,'AIR','?A?ohe wa ho?ohana e like me keia: {OPENBRACE}0{CLOSEBRACE}','HAW','--ANY--','--ANY--','h�N�GL�*�5��D','2013-12-18 14:09:06.907');
INSERT INTO `client_messagetranslation` VALUES (3644,'AIR','Versión mayor:','ESN','--ANY--','--ANY--','h���0�Kؙu^�}p�','2014-03-22 20:36:39.647');
INSERT INTO `client_messagetranslation` VALUES (3068,'AIR','Ke hoʻāʻo nei e kākau hou i kēia ʻikamu','HAW','--ANY--','--ANY--','h��Y5D�m�	3��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3426,'AIR','Seleccione la prueba que desea realizar.','ESN','--ANY--','--ANY--','i7�Tp�AA�u�\"�g','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3066,'AIR','ʻAʻole i hoʻokomo ʻia nā ʻikamu hōʻike hou ma ka waihona ʻike.','HAW','--ANY--','--ANY--','iv�}e>GH�q����m','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2504,'AIR','<p>Espere mientras el administrador de pruebs revisa la configuración para su prueba. Esto puede tardar algunos minutos...</p>','ESN','--ANY--','--ANY--','i����J�������G�','2014-03-22 20:33:38.120');
INSERT INTO `client_messagetranslation` VALUES (2479,'AIR','Mea Hoʻokani Koho','HAW','--ANY--','--ANY--','iㆄ��N��I�	��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3214,'AIR','Koho i ke kula','HAW','--ANY--','--ANY--','i�h�r�C���}�3F�','2013-12-18 14:09:07.007');
INSERT INTO `client_messagetranslation` VALUES (2818,'AIR','E pani i ka mana?o','HAW','--ANY--','--ANY--','i��@�N𨍪;#I�q','2013-12-18 14:09:06.693');
INSERT INTO `client_messagetranslation` VALUES (3465,'AIR','?E?e','HAW','--ANY--','--ANY--','j<�TN��a\n-�y','2013-12-18 14:09:07.287');
INSERT INTO `client_messagetranslation` VALUES (2702,'AIR','Imprimir según solicitud','ESN','--ANY--','--ANY--','j/��EE��w X���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2429,'AIR','Ua ho´‘ole ke Kumu Ho\'ike i kau noi.','HAW','--ANY--','--ANY--','j5\"��%C�KړӤV�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3132,'AIR','La prueba no está soportado por la sesión.','ESN','--ANY--','--ANY--','j�!_�NU��T�zk�','2014-03-22 20:37:20.650');
INSERT INTO `client_messagetranslation` VALUES (2402,'SBAC','Grade:','ENU','--ANY--','--ANY--','j�T�=IN�~r����&','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2752,'AIR','VERDADERO','ESN','--ANY--','--ANY--','j��	٦N:�\\��Q��_','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3506,'AIR','?Apono ?ia ma ka la:','HAW','--ANY--','--ANY--','j��R�Dx����+�?','2013-12-18 14:09:07.350');
INSERT INTO `client_messagetranslation` VALUES (3019,'AIR','Ma muli o ke ?ano o ka ho?oili ?ana o kau kahua kamepiula, hiki ?ole ia?oe ke ha?awi aku i keia ho?ike i ka nui haumana au i makemake ai ma keia wahi nei.?Olu?olu e ho?emi mai i na helu haumana a kokoke i ka {0}.','HAW','--ANY--','--ANY--','j��Z��I���4�Z�','2013-12-18 14:09:06.847');
INSERT INTO `client_messagetranslation` VALUES (3557,'AIR','Nana/ho?opololei i na ?ikepili','HAW','--ANY--','--ANY--','k��=�B���eN���','2013-12-18 14:09:07.423');
INSERT INTO `client_messagetranslation` VALUES (2400,'AIR','Nombre:','ESN','--ANY--','--ANY--','kQ9��OY�[�z���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3055,'AIR','Ua hewa ke kulana pili no ka nana ?ana i na ana ho?ike','HAW','--ANY--','--ANY--','kE���B㏡�m��','2013-12-18 14:09:06.880');
INSERT INTO `client_messagetranslation` VALUES (3423,'AIR',' ','HAW','--ANY--','--ANY--','kO�d�I�ks\ne','2013-12-18 14:09:07.220');
INSERT INTO `client_messagetranslation` VALUES (3433,'AIR','<span>Revisar respuestas</span>','ESN','--ANY--','--ANY--','kSI�!�H�%�)\"�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2893,'AIR','Agregar un comentario a este item','ESN','--ANY--','--ANY--','ka�9��Eر=�����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2597,'AIR','E ʻōlelo i ke koho D','HAW','--ANY--','--ANY--','kq��ݵDv�_B����~','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2364,'AIR','Ingrese la ID de sesión que el administrador le asignó.','ESN','--ANY--','--ANY--','ku�F��L�(g����F','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3282,'AIR','Ho?opau i ka helu ?ai','HAW','--ANY--','--ANY--','k�kXŠB���0��l','2013-12-18 14:09:07.090');
INSERT INTO `client_messagetranslation` VALUES (3575,'AIR','Nui o ka leo','HAW','--ANY--','--ANY--','k�0�pB@���Y�>��','2013-12-18 14:09:07.453');
INSERT INTO `client_messagetranslation` VALUES (3665,'AIR','Ho?i hou i ke kainoa','HAW','--ANY--','--ANY--','k�N�,0D�o���','2013-12-18 14:09:07.577');
INSERT INTO `client_messagetranslation` VALUES (2696,'AIR','E Kahiauli','HAW','--ANY--','--ANY--','lj��7I����z����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2949,'AIR','Y','HAW','--ANY--','--ANY--','l<�\\K��������','2013-12-18 14:09:06.770');
INSERT INTO `client_messagetranslation` VALUES (3448,'SBAC','Cerrar sesión','ESN','--ANY--','--ANY--','l&���AD���bU�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3500,'AIR','?Apono ?ia na noi','HAW','--ANY--','--ANY--','l.R̒�G0��E}����','2013-12-18 14:09:07.340');
INSERT INTO `client_messagetranslation` VALUES (3065,'AIR','Ke hoʻāʻo nei e hoʻokomo ʻikamu ma kekahi wahi aku','HAW','--ANY--','--ANY--','lG�c�KO4� j�g�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3272,'AIR','Kuhikuhi a?o no na ?apana ','HAW','--ANY--','--ANY--','le��A0�M�vEM�9','2013-12-18 14:09:07.073');
INSERT INTO `client_messagetranslation` VALUES (2700,'AIR','Padres exentos','ESN','--ANY--','--ANY--','ln�x`�Js��P�\'�\n�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3686,'AIR','No se permite el ingreso anónimo','ESN','--ANY--','--ANY--','l���KINg���ʵc-','2014-03-22 20:36:39.903');
INSERT INTO `client_messagetranslation` VALUES (2658,'AIR','Agregar flecha','ESN','--ANY--','--ANY--','l��(DL��,�`����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3019,'AIR','Dada la carga actual en el sistema, no puede probar con éxito el número requerido de estudiantes en este sitio. Por favor, intente un número cercano a {0}.','ESN','--ANY--','--ANY--','l�[p<@��W� y��','2014-03-22 20:35:11.290');
INSERT INTO `client_messagetranslation` VALUES (2721,'AIR','ʻĀhinahina','HAW','--ANY--','--ANY--','l��k��F=��o\0!��a','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2562,'AIR','E ho\'omaka ana ka ho\'ike, e \'olu\'olu, e kali...','HAW','--ANY--','--ANY--','l��]_�@��l�����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2391,'AIR','Ka Inoa Mua:','HAW','--ANY--','--ANY--','m6��2�AץK�A�7�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3656,'AIR','Ho?ouka i na loa?a:','HAW','--ANY--','--ANY--','mC ���KN�zHC�a�]','2013-12-18 14:09:07.560');
INSERT INTO `client_messagetranslation` VALUES (3043,'AIR','Falló el bloqueo de la aplicación','ESN','--ANY--','--ANY--','me�FoA��Cg����','2014-03-22 20:35:11.300');
INSERT INTO `client_messagetranslation` VALUES (2624,'AIR','Seleccione 2 puntos para conectar con una flecha.','ESN','--ANY--','--ANY--','m�P�.9A��@љ��(�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3134,'AIR','Aia he pilikia me ka ‘ōnaehana. E ‘olu’olu, e hana hou.','HAW','--ANY--','--ANY--','m�\\�R-G(�j��q��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2561,'AIR','E \'olu\'olu, e kali. Ke ho\'oponopono \'ia nei kau ho\'ike, ','HAW','--ANY--','--ANY--','m���N��ዼ�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3710,'AIR','{silence}OpciÃ³n E.{silence}','HAW','--ANY--','--ANY--','m�� Eu���ߟ��','2013-12-18 14:09:07.637');
INSERT INTO `client_messagetranslation` VALUES (3141,'AIR','Aia he pilikia me ka ‘ōnaehana. E ‘olu’olu e hā’awi i kēia helu iā Kumu','HAW','--ANY--','--ANY--','m�^�!�H����\\B4�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2500,'AIR','<a href=\"https://hsapt.tds.airast.org/student/\" class=\"goPractice\">E hele i kahi o ka Hoʻomaʻamaʻa Hōʻike.</a>','HAW','--ANY--','--ANY--','mڦ���@����zq�4','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2945,'AIR','Ina noi ?ia','HAW','--ANY--','--ANY--','m�`O�-c,ǥ�','2013-12-18 14:09:06.763');
INSERT INTO `client_messagetranslation` VALUES (2492,'AIR','Se produjo un problema al conectarse con Internet. Ponga la prueba en pausa e inténtelo nuevamente.','ESN','--ANY--','--ANY--','n�&�oH���P�b�:�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3689,'AIR','No hay dirección URL para el satélite','ESN','--ANY--','--ANY--','nd�#G��jnfy','2014-03-22 20:36:39.920');
INSERT INTO `client_messagetranslation` VALUES (2405,'AIR','Ka Helu Haumana:','HAW','--ANY--','--ANY--','n\"y��E����u','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2774,'AIR','POLOLEI','HAW','--ANY--','--ANY--','n@\0ը�A��3�U�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2383,'AIR','E \'olu\'olu e pane i na ninau a pau ma keia \'ao\'ao ma mua o ka hana \'ana i ka \'ao\'ao \'e a\'e. Pono paha \'oe e lolelole iho no ka \'ike \'ana i na mea a pau.','HAW','--ANY--','--ANY--','nvtQAL�����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3016,'AIR','Preguntas:','ESN','--ANY--','--ANY--','n��lQM��QmJ��9','2014-03-22 20:35:11.287');
INSERT INTO `client_messagetranslation` VALUES (3109,'AIR','?A?ole hiki ke ho?ili ?ia na ?ikepili ko?iko?i i makemake ?ia.','HAW','--ANY--','--ANY--','n�E[K�H	��1�Z�','2013-12-18 14:09:06.923');
INSERT INTO `client_messagetranslation` VALUES (2465,'AIR','E \'olu\'olu e kali.','HAW','--ANY--','--ANY--','n���C��Q�����}','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2762,'AIR','Estímulos','ESN','--ANY--','--ANY--','n�!A�F���&*��Ѡ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3148,'AIR','A\'ole i loa\'a kekahi Helu Haumana e like me kēia. E \'olu\'olu, \'e ho\'ā\'o hou.','HAW','--ANY--','--ANY--','o!&R��F�������','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3059,'AIR','?A?ohe wa ho?ohana e like me keia','HAW','--ANY--','--ANY--','o)Qn��JO�m�e�Y!�','2013-12-18 14:09:06.883');
INSERT INTO `client_messagetranslation` VALUES (3015,'AIR','<p>?Olu?olu e nana hou i kau mau ha?ina ma mua o ka ho?omau ?ana i ka ho?ike.Hiki ?ole ana ia?oe ke ho?i hou mai i keia mau ninau nei ma hope iho. </p><p>Kaomi aku ma ka ?ao?ao hema o ka helu o kekahi ninau au e makemake nei e nana hou ?ia.</p>','HAW','--ANY--','--ANY--','oeiv@/���\'<��l','2013-12-18 14:09:06.840');
INSERT INTO `client_messagetranslation` VALUES (2517,'AIR','Ka Inoa Haumana:','HAW','--ANY--','--ANY--','o��9u�B���N�{]{','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2886,'AIR','Permitir la selección de texto','ESN','--ANY--','--ANY--','o���-�A���֥ᵤ^','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3429,'AIR','Felicitaciones, ha finalizado la prueba.','ESN','--ANY--','--ANY--','oځ�Q�E{��t�t','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2536,'AIR','Restablecer resaltado','ESN','--ANY--','--ANY--','o��ӴgB�U\Zy��{','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2676,'AIR','Imprimir','ESN','--ANY--','--ANY--','p&ܼHE;�}�w���_','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3038,'AIR','No hay preguntas en esta prueba.','ESN','--ANY--','--ANY--','pZM@Cֳ���\"�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2428,'AIR','E \'olu\'olu, e kali','HAW','--ANY--','--ANY--','pc	�,Ol�O�L5�|','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2499,'AIR','<p>Para iniciar sesión con una cuenta de estudiante (con Nombre / SSID):</p><ul><li>desactive la selección de la casilla de verificación “Usuario invitado” (ambos campos aparecerán vacíos)</li><li>Ingrese su nombre y SSID en esos campos.</li></ul><p>Para iniciar sesión como invitado (usuario anónimo):</p><ul><li>Marque la casilla de verificación \"Usuario invitado\" (ambos campos mostrarán automáticamente Invitado)</li><li>Haga clic en [Iniciar sesión] para iniciar sesión en la prueba de muestra como usuario invitado.</li></ul><p><em>¿Sesión de invitado?</em><br />En una sesión de invitado, no necesita la aprobación del administrador de la prueba, por lo que puede realizar la prueba con su propia configuración. Para realizar la prueba de muestra en una sesión con el administrador de la prueba, desactive la selección de la casilla de verificación “Sesión de invitado” e ingrese el id. de sesión en el campo antes de hacer clic en [Iniciar sesión].</p>','ESN','--ANY--','--ANY--','p�]�@�F(�\\3�8\n\Zx','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2374,'AIR','Ua ho‘ouna ‘ia kau noi e pa‘i i ke kumu ho\'ike.','HAW','--ANY--','--ANY--','p���kE��@�Xӛ#S','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3690,'AIR','ID de sesión incorrecta o vencida','ESN','--ANY--','--ANY--','p��̖�O*��R����','2014-03-22 20:36:39.930');
INSERT INTO `client_messagetranslation` VALUES (2475,'AIR','Ayuda','ESN','--ANY--','--ANY--','q�S!XFg�N���t0�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3497,'AIR','Na ?olea','HAW','--ANY--','--ANY--','q\r�W�DC�����@9','2013-12-18 14:09:07.340');
INSERT INTO `client_messagetranslation` VALUES (2958,'AIR','Ho?ike?ike i na helu ?ai i ka haumana','HAW','--ANY--','--ANY--','q5�^NE��NI��qE�','2013-12-18 14:09:06.777');
INSERT INTO `client_messagetranslation` VALUES (2516,'AIR','O Kau Helu\'ai keia','HAW','--ANY--','--ANY--','q8C+������4','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2384,'SBAC','Ha respondido todas las preguntas.  Cuando termine de revisar las preguntas, presione [Terminar prueba].','ESN','--ANY--','--ANY--','qf�2g�A����\'','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2420,'AIR','<span>Volver al inicio de sesión</span>','ESN','--ANY--','--ANY--','q�j���B@�^��~ǙQ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2878,'AIR','Cerrar Comentario','ESN','--ANY--','--ANY--','q��xcM܎fM�8�','2014-03-22 20:34:28.083');
INSERT INTO `client_messagetranslation` VALUES (3687,'AIR','No hay dirección URL para el satélite','ESN','--ANY--','--ANY--','q�ҕ=�FH��B3�)	','2014-03-22 20:36:39.913');
INSERT INTO `client_messagetranslation` VALUES (3284,'SBAC','You are now leaving the Test Administrator Interface. Upon clicking [Exit], your test session and all your students\' in-progress tests will be paused. Click [Return] to remain on the Test Administrator Interface and continue the test session.','ENU','--ANY--','--ANY--','r-h0��I�-�^�9t','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3473,'AIR','Ho?omaka i ka wa ho?ohana','HAW','--ANY--','--ANY--','rVU��@}�:�?CK','2013-12-18 14:09:07.300');
INSERT INTO `client_messagetranslation` VALUES (2849,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','r�ІDDٌ�~M�Iyg','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3470,'AIR','Ha?alele','HAW','--ANY--','--ANY--','r��+�B��s\r�j��','2013-12-18 14:09:07.297');
INSERT INTO `client_messagetranslation` VALUES (3027,'AIR','Aia he pilikia me ka ‘ōnaehana. E ‘olu’olu e hā’awi i kēia helu iā Kumu','HAW','--ANY--','--ANY--','r�Ɲ5!Jٱ �xDl\\s','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2340,'SBAC_PT','<!--Scores are not shown for this test. -->','ENU','--ANY--','--ANY--','r�ip�L	��/���','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2944,'AIR','Noi-Hanawale','HAW','--ANY--','--ANY--','s6L��D4���Y�','2013-12-18 14:09:06.760');
INSERT INTO `client_messagetranslation` VALUES (2930,'AIR','E ho?i hou i ka ?e?e haumana','HAW','--ANY--','--ANY--','s\"^;��C������A','2013-12-18 14:09:06.737');
INSERT INTO `client_messagetranslation` VALUES (2955,'AIR','Holo-lalo','HAW','--ANY--','--ANY--','sPO�[�@��\n��t�*�','2013-12-18 14:09:06.770');
INSERT INTO `client_messagetranslation` VALUES (3298,'AIR','Hewa ka makahiki palena pau (Ho?ohana i ka ?ano ho?ala YYYY)','HAW','--ANY--','--ANY--','ss3�PbE��Dy��L\rW','2013-12-18 14:09:07.110');
INSERT INTO `client_messagetranslation` VALUES (3301,'AIR','?Olu?olu e ho?okomo i ka po?omana?o no ka memo','HAW','--ANY--','--ANY--','s�8�(BI���֮�B��','2013-12-18 14:09:07.113');
INSERT INTO `client_messagetranslation` VALUES (3490,'AIR','?Apono i na haumana a pau','HAW','--ANY--','--ANY--','s����K鍾�o8��_','2013-12-18 14:09:07.327');
INSERT INTO `client_messagetranslation` VALUES (3018,'AIR','Hiki ?ole ke ho?opau ?ia na wehewehe?ano.?Olu?olu e ho?omaopopo i ke kokua.','HAW','--ANY--','--ANY--','s�m��G1����Cf��','2013-12-18 14:09:06.843');
INSERT INTO `client_messagetranslation` VALUES (3512,'AIR','Huna/Ho?ike i na kolamu','HAW','--ANY--','--ANY--','sθ�E���q��','2013-12-18 14:09:07.357');
INSERT INTO `client_messagetranslation` VALUES (2839,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','t����MP��&��NA','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3106,'AIR','No existe la oportunidad original.','ESN','--ANY--','--ANY--','t��eN��e�/','2014-03-22 20:35:48.650');
INSERT INTO `client_messagetranslation` VALUES (3270,'AIR','Usted no es dueño de esta sesión.','ESN','--ANY--','--ANY--','t���K���>\"4)','2014-03-22 20:39:31.230');
INSERT INTO `client_messagetranslation` VALUES (2513,'AIR','<p>Algunas preguntas avanzadas basadas en java  requieren que los estudiantes realicen un dibujo o completen otro tipo de tarea sin plazo definido. <a href=\"https://airpt.tds.airast.org/grid/default.aspx\">Haga clic aquí para obtener ejemplos de estas preguntas sin plazo definido</a>.</p>    ','ESN','--ANY--','--ANY--','t\'��K�B ���X[nM','2014-03-22 20:33:38.123');
INSERT INTO `client_messagetranslation` VALUES (2975,'AIR','<span style=\"display:none\"><a href=\"https://air.tds.airast.org/test_scoreentry/repLogin.aspx?CSS=AIRDataEntry\">Hele ma ke kahua ho?okomo pane/helu ?ai.</a></span>','HAW','--ANY--','--ANY--','tR(�_�E�����o��@','2013-12-18 14:09:06.800');
INSERT INTO `client_messagetranslation` VALUES (2924,'AIR','Aia he pilikia ma waena o ke kamepiula a me ke kikowaena pūnaewele. E ‘olu‘olu, e hana hou.','HAW','--ANY--','--ANY--','t`��P9I&�\r�L�pS�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2386,'AIR','Makemake anei \'oe e malama i kau pane ma mua o ka ha\'alele \'ana i keia \'ao\'ao? <E malama i ka\'u pane> <E holoi i ka\'u pane>','HAW','--ANY--','--ANY--','t�Ǖow@g��`�3l4�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2769,'AIR','HKML','HAW','--ANY--','--ANY--','t��I�@8�r�(�R#','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2956,'AIR','Bloc de notas','ESN','--ANY--','--ANY--','t���;�@����!?d�','2014-03-22 20:34:47.197');
INSERT INTO `client_messagetranslation` VALUES (3440,'AIR','Ho?oholo i ka ho?ike hoa?o','HAW','--ANY--','--ANY--','t�BA��C5�2��C��','2013-12-18 14:09:07.247');
INSERT INTO `client_messagetranslation` VALUES (2651,'AIR','E Hana Hou','HAW','--ANY--','--ANY--','t���zJAK�VL�{D�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2725,'AIR','Ula Maku\'e ','HAW','--ANY--','--ANY--','u	x[�B���b�7�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3606,'AIR','Ho?okomo helu ?ai','HAW','--ANY--','--ANY--','u5���H@֕�^�����','2013-12-18 14:09:07.483');
INSERT INTO `client_messagetranslation` VALUES (2604,'AIR','E ʻŌlelo I Ka Paukū (Ma Ka Namu Haole)','HAW','--ANY--','--ANY--','ua���&I}�O���U�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2966,'AIR','../tools/formulas/2010/mn_6_math.html','HAW','--ANY--','--ANY--','u����qAଛtGr�','2013-12-18 14:09:06.790');
INSERT INTO `client_messagetranslation` VALUES (2619,'AIR','Escritura','ESN','--ANY--','--ANY--','u����J���@��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3525,'AIR','?Oia','HAW','--ANY--','--ANY--','v��t)L\0�de��G�l','2013-12-18 14:09:07.373');
INSERT INTO `client_messagetranslation` VALUES (2950,'AIR','A\'ole i ho\'ohana \'ia ka mea hana pa\'amau ho\'onui','HAW','--ANY--','--ANY--','v5�\\�dG)��;A}t5','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3630,'AIR','Ke ana','HAW','--ANY--','--ANY--','v>\"\0$:G\'��x�.M<w','2013-12-18 14:09:07.520');
INSERT INTO `client_messagetranslation` VALUES (3060,'AIR','Las fechas de inicio/fin de la sesión previenen la apertura en este momento','ESN','--ANY--','--ANY--','vV~��]@}�l�An�','2014-03-22 20:35:32.327');
INSERT INTO `client_messagetranslation` VALUES (2412,'AIR','Idioma:','ESN','--ANY--','--ANY--','vX��6D��_��p�\Z�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2920,'SBAC','Cancelar','ESN','--ANY--','--ANY--','vf�lo6G׋\0WsK���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2956,'AIR','Palemana?o','HAW','--ANY--','--ANY--','vwF-�PK����R�r','2013-12-18 14:09:06.773');
INSERT INTO `client_messagetranslation` VALUES (3003,'AIR','Seleccione una prueba de la lista desplegable.','ESN','--ANY--','--ANY--','v���h�H&� c��s�','2014-03-22 20:35:11.280');
INSERT INTO `client_messagetranslation` VALUES (2415,'AIR','Más grande','ESN','--ANY--','--ANY--','v�h%JOA���K���j','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2701,'AIR','Tabla Periódica','ESN','--ANY--','--ANY--','v��k\nDB�M�T�\r�Q','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2379,'AIR','¿Aún se encuentra allí? Haga clic en Aceptar para continuar o se cerrará la sesión en {1} segundos.','ESN','--ANY--','--ANY--','v߻�1�K��*�Q�xG�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2758,'AIR','\'A\'OLE POLOLEI','HAW','--ANY--','--ANY--','w5�s�C���&w(��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2415,'AIR','Nui A\'e','HAW','--ANY--','--ANY--','w��+�@���l���_','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3475,'AIR','Na ho?ike','HAW','--ANY--','--ANY--','wpj[G��w���آ','2013-12-18 14:09:07.303');
INSERT INTO `client_messagetranslation` VALUES (2597,'AIR','Diga la opción D','ESN','--ANY--','--ANY--','w\Znh�B�������f�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3145,'AIR','Ha usado todas las oportunidades de realizar esta prueba.','ESN','--ANY--','--ANY--','wQ���4K�ﺗ�f','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3734,'AIR','<span>?Olu?olu e nana hou i na kumuhana ma mua o ka ha?awi ?ana i na ho?ike no ka helu ?ana i na helu ?ai.</span>','HAW','--ANY--','--ANY--','wSKvQI��b�e-_','2013-12-18 14:09:07.677');
INSERT INTO `client_messagetranslation` VALUES (2520,'AIR','Prueba realizada el:','ESN','--ANY--','--ANY--','w�w�r�G-�~lDzLr�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3484,'SBAC_PT','Training Test','ENU','--ANY--','--ANY--','w�U�ǥD�bJI��','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2765,'AIR','El mayor','ESN','--ANY--','--ANY--','w�o�4oF��s�Q?�&�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2858,'AIR','Cerrar Comentario','ESN','--ANY--','--ANY--','w�3gEE���|�.��','2014-03-22 20:34:06.500');
INSERT INTO `client_messagetranslation` VALUES (3723,'AIR','Inoa ho?ohana','HAW','--ANY--','--ANY--','w����dM�-2K��I�','2013-12-18 14:09:07.657');
INSERT INTO `client_messagetranslation` VALUES (3648,'AIR','Su sistema operativo:','ESN','--ANY--','--ANY--','w���pFܞ��lN[4�','2014-03-22 20:36:39.680');
INSERT INTO `client_messagetranslation` VALUES (2528,'AIR','Na Ninau I Pau/I Maka \'ia:','HAW','--ANY--','--ANY--','x�M�kO��w��BS','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3111,'AIR','?A?ohe manawa kupono e kohu nei i ka ID ana ho?ike e makemake ?ia nei e huli.','HAW','--ANY--','--ANY--','x/i�jD������z�,','2013-12-18 14:09:06.927');
INSERT INTO `client_messagetranslation` VALUES (3347,'AIR','<p>Kaomi aku ma ka ?ohenana no ka nana ?ana i na ana ho?ike e pili ana i kela haumana.</p>','HAW','--ANY--','--ANY--','x��0M\n��!�qj��','2013-12-18 14:09:07.150');
INSERT INTO `client_messagetranslation` VALUES (2622,'SBAC','Seleccione las ubicaciones de los puntos.','ESN','--ANY--','--ANY--','xQ��yCr���Q�D�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3280,'AIR','Na pa?i no na makapo','HAW','--ANY--','--ANY--','x?Kr�9Bݱl��q','2013-12-18 14:09:07.087');
INSERT INTO `client_messagetranslation` VALUES (2718,'AIR','Azul','ESN','--ANY--','--ANY--','xqGQ�NɣĻ#���k','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2581,'AIR','E kaomi i \'ane\'i no ka ho\'ouna \'ana i ka \'ike kokua.','HAW','--ANY--','--ANY--','x�5~@�Dd�B\"~����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3166,'SBAC','The State-SSID login field is not available.','ENU','--ANY--','--ANY--','y:�\Z�K��sXko}!','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3422,'AIR','Cerrar','ESN','--ANY--','--ANY--','yS�[��EK�$jͭg��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2712,'AIR','ʻAʻohe','HAW','--ANY--','--ANY--','yz�߲C��J�B@V?','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2432,'AIR','Pono ka Flash {0} a \'oi aku no ka hana \'ana i keia ho\'ike','HAW','--ANY--','--ANY--','y�F�c@*��ݠ|��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3324,'AIR','Ua ho?a ?ia ka polokalamu palulu pukaaniani lelewale no keia polokalamu punaewele puni honua.Ina makemake ?oe e komo aku ma keia kahua nei, pono ?oe e ho?opio aku i ka polokalamu palulu.Ho?opio aku i ka polokalamu palulu pukaaniani lele wale a laila e kaomi i ka pihi ho?ano hou.','HAW','--ANY--','--ANY--','y�U��Ep�������','2013-12-18 14:09:07.133');
INSERT INTO `client_messagetranslation` VALUES (3131,'AIR','La sesión no pertenece a este administrador.','ESN','--ANY--','--ANY--','y��?*�BR��h!�h�','2014-03-22 20:37:20.650');
INSERT INTO `client_messagetranslation` VALUES (3682,'AIR','Falló la asignación de satélite','ESN','--ANY--','--ANY--','z\r��@x����|LK','2014-03-22 20:36:39.883');
INSERT INTO `client_messagetranslation` VALUES (2905,'AIR','E ʻŌlelo I Ka ʻŌkuhi (Ma Ka Namu Haole)','HAW','--ANY--','--ANY--','zA�!b8KN�{�:��\'�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3119,'AIR','La oportunidad de prueba no existe.','ESN','--ANY--','--ANY--','z\\�1��H|�=��T��w','2014-03-22 20:35:48.657');
INSERT INTO `client_messagetranslation` VALUES (3601,'AIR','Respuesta gráfica','ESN','--ANY--','--ANY--','zi���bHS�p`,�b��','2014-03-22 20:36:39.403');
INSERT INTO `client_messagetranslation` VALUES (2596,'AIR','E ʻŌlelo I Ke Koho C (Ma Ka Namu Haole)','HAW','--ANY--','--ANY--','z����NU�o�ea8','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3075,'AIR','ʻAʻole pololei ka helu hōʻike','HAW','--ANY--','--ANY--','z�\\��F��o�P���/','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3070,'AIR','Hiki ?ole ke loa?a na panako ?ikemu ho?ike no na haumana','HAW','--ANY--','--ANY--','z�m�NM��_�@���','2013-12-18 14:09:06.890');
INSERT INTO `client_messagetranslation` VALUES (2996,'AIR','página','ESN','--ANY--','--ANY--','z�.��L/�\'+��zz�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2450,'SBAC','<span>Cerrar sesión</span>','ESN','--ANY--','--ANY--','zԑ�T�O��/�w�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3548,'AIR','Inoa','HAW','--ANY--','--ANY--','z�Vņ�E����/�>�','2013-12-18 14:09:07.410');
INSERT INTO `client_messagetranslation` VALUES (3087,'AIR','No se pudo encontrar la sesión. Consulte al administrador de la prueba.','ESN','--ANY--','--ANY--','z�����Eغ%�\"�l�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2730,'AIR','Ka Melemele ma luna o ka Uliuli','HAW','--ANY--','--ANY--','{-~sF����=�n<','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3608,'AIR','Kaulike','HAW','--ANY--','--ANY--','{����G1�L�1�.�','2013-12-18 14:09:07.487');
INSERT INTO `client_messagetranslation` VALUES (2723,'AIR','Oma\'oma\'o Halakea','HAW','--ANY--','--ANY--','{r�5r�C|������l','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2549,'AIR','E kaomi i \'ane\'i no ka pa\'i \'ana i ka mo\'olelo.','HAW','--ANY--','--ANY--','{w���M��39�]�q','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2921,'AIR','<span class=\"noTTS\">La función texto a voz no está disponible.</span>','ESN','--ANY--','--ANY--','{�f}�oD!��4Ak�+','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3641,'AIR','Polokalamu punaewele puni honua:','HAW','--ANY--','--ANY--','{���;�DN�/���/S�','2013-12-18 14:09:07.537');
INSERT INTO `client_messagetranslation` VALUES (2636,'AIR','E ho‘olohe','HAW','--ANY--','--ANY--','{�m�~Bc��A�N:\'','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2933,'AIR','Paniki (emboss) i ke ?ano o ka noi','HAW','--ANY--','--ANY--','{�=(b\'C��>�,d�','2013-12-18 14:09:06.743');
INSERT INTO `client_messagetranslation` VALUES (2682,'AIR','Hiki No','HAW','--ANY--','--ANY--','{�ʡQ�L߸h�RK�Q�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3505,'AIR','Ninau helu','HAW','--ANY--','--ANY--','{�#XD�@�f�!�ƕe','2013-12-18 14:09:07.350');
INSERT INTO `client_messagetranslation` VALUES (2355,'AIR','../tools/periodic/2010/hi_7_science.html','HAW','--ANY--','--ANY--','|!e*vC��=�o��','2013-12-18 14:09:06.630');
INSERT INTO `client_messagetranslation` VALUES (3124,'AIR','Usted no es dueño de esta sesión.','ESN','--ANY--','--ANY--','|#IY�_N��B(�C','2014-03-22 20:35:48.660');
INSERT INTO `client_messagetranslation` VALUES (3432,'AIR','<span>Cerrar sesión</span>','ESN','--ANY--','--ANY--','|<���{@�����Ԉ�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2438,'AIR','Inā lohe ʻoe i ke kani, e kaomi i ka [ʻAe], inā kaomi i ka [ʻAʻole].','HAW','--ANY--','--ANY--','|{��I���fʗ>(t','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2794,'AIR','Expandir todos los ejercicios','ESN','--ANY--','--ANY--','|�/5U�J��p\"��&E','2014-03-22 20:33:38.080');
INSERT INTO `client_messagetranslation` VALUES (3520,'AIR','Ho?opa?a','HAW','--ANY--','--ANY--','}\'Y`��AۮUz�K�','2013-12-18 14:09:07.367');
INSERT INTO `client_messagetranslation` VALUES (2554,'AIR','ʻAʻole','HAW','--ANY--','--ANY--','}:DZ�Dͮ�ag��{','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2557,'AIR','Tabla Periódica','ESN','--ANY--','--ANY--','}��\'�@��	�Oޝ�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3616,'AIR','?A?ohe hana ma keia pukaaniani ho?ike.','HAW','--ANY--','--ANY--','}�~c�6B��p~��','2013-12-18 14:09:07.497');
INSERT INTO `client_messagetranslation` VALUES (3704,'AIR','{silence}Koho E.{silence}','HAW','--ANY--','--ANY--','}ʘ�WOV�k.���k�','2013-12-18 14:09:07.627');
INSERT INTO `client_messagetranslation` VALUES (3411,'AIR','Ha respondido todas las preguntas.  Cuando termine de revisar las preguntas, presione [Terminar prueba].','ESN','--ANY--','--ANY--','}Ҵ�pA��⿉�~','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2484,'AIR','Comenzar grabación','ESN','--ANY--','--ANY--','}��#�B�u�=��Ϻ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2437,'AIR','E kaomi i ke kiʻi kani no ka lohe ʻana i ke kani.','HAW','--ANY--','--ANY--','}�=))�N��Sw��=\r','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3095,'AIR','Hiki ?ole ke loa?a na ?ikepili ho?ike ma ke kahua RTS','HAW','--ANY--','--ANY--','~A��\ZD2�BU���k�','2013-12-18 14:09:06.910');
INSERT INTO `client_messagetranslation` VALUES (2431,'AIR','Haga clic aquí para volver a la pantalla de inicio de sesión. ','ESN','--ANY--','--ANY--','~$��<�JQ�`�E�3��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3319,'SBAC','<span>Smarter Balanced Assessment Consortium</span>  ','ENU','--ANY--','--ANY--','~7��MEH[��Υ���T','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2786,'AIR','Someter un comentario para este ítem.','ESN','--ANY--','--ANY--','~R%�8dJ���:��8��','2014-03-22 20:33:38.077');
INSERT INTO `client_messagetranslation` VALUES (3639,'AIR','Kahu mea ho?ohana:','HAW','--ANY--','--ANY--','~U�_�OȦz��O�;','2013-12-18 14:09:07.533');
INSERT INTO `client_messagetranslation` VALUES (3661,'AIR','(pono ka mana 10 a i ?ole ki?eki?e hou a?e)','HAW','--ANY--','--ANY--','~v�#�sL!�v��	a�','2013-12-18 14:09:07.570');
INSERT INTO `client_messagetranslation` VALUES (2770,'AIR','Ninguno','ESN','--ANY--','--ANY--','~�����J1���+Y��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2462,'AIR','Tamaño de impresión:','ESN','--ANY--','--ANY--','~�X�awO)���q�#�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3705,'SBAC','{silence} F.{silence}','ENU','--ANY--','--ANY--','~����	G\n�$qդ�u�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3567,'AIR','Komo','HAW','--ANY--','--ANY--','\rM�(D���K3w��','2013-12-18 14:09:07.440');
INSERT INTO `client_messagetranslation` VALUES (3334,'AIR','<p>Ke ho?ike ?ia nei na ?ikepili no ke kahua a me ke kikoawena o ka polokalamu punaewele (?a?ao o ka mea ho?ohana) me ka lohi o ke kukulu ?ana ma kahi o na hapa kaukani kekona.</p>','HAW','--ANY--','--ANY--',')���J}�>��\0B','2013-12-18 14:09:07.143');
INSERT INTO `client_messagetranslation` VALUES (3042,'AIR','Loa?a ?ole keia wa ho?ohana.','HAW','--ANY--','--ANY--','Y:���D�Y�b\\A�','2013-12-18 14:09:06.863');
INSERT INTO `client_messagetranslation` VALUES (3704,'SBAC','{silence} E.{silence}','ENU','--ANY--','--ANY--','���%U@ژ��/�5�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3044,'AIR','Ha?ule ke komo ?ana o kekahi wa ho?ohana hou i loko o na ?ikepili ho?ike','HAW','--ANY--','--ANY--','��p�N0����L�l�','2013-12-18 14:09:06.867');
INSERT INTO `client_messagetranslation` VALUES (2420,'AIR','<span>E Ho\'i I \"E Kāinoa \'Oe\"</span>','HAW','--ANY--','--ANY--','�tY�L��C��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2797,'AIR','Cambiar ejercicio','ESN','--ANY--','--ANY--','΂q�iK���l�	��','2014-03-22 20:33:38.083');
INSERT INTO `client_messagetranslation` VALUES (2550,'AIR','E kaomi i \'ane\'i no ka ho\'omau \'ana i kau ho\'ike.','HAW','--ANY--','--ANY--','��B��K��j��O�gi','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3145,'AIR','Ua ho\'ohana \'oe i kāu mau manawa kūpono a pau no ka hana \'ana i kēia hō\'ike.','HAW','--ANY--','--ANY--','���O4��d!8�ѹ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2486,'AIR','E ʻōlelo mai i ka ʻōlelo i koho ʻia','HAW','--ANY--','--ANY--','��E��I�@�i�i\r�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2729,'AIR','Amarillo claro','ESN','--ANY--','--ANY--','�#Q�B[���麟�|','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2527,'AIR','<span>Ayuda</span>','ESN','--ANY--','--ANY--','�\rJ���F����b��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3631,'AIR','Ho?ohana i na kahua pa?amau','HAW','--ANY--','--ANY--','�:���Jٹy�\ZR��','2013-12-18 14:09:07.523');
INSERT INTO `client_messagetranslation` VALUES (2646,'SBAC','Click [Yes] if you heard the voice. If you did not hear the voice, click [No].','ENU','--ANY--','--ANY--','�Z�\'J�Ms�B�!n�_','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2631,'AIR','E ho\'oku\'u i ke pihi \'iole no ka waiho \'ana i ka mea i kahi au e makemake ai.','HAW','--ANY--','--ANY--','������D(��\nSG&V=','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3570,'AIR','Ho?okomo i ka inoa mua','HAW','--ANY--','--ANY--','��\'�7CM�=3�?���','2013-12-18 14:09:07.447');
INSERT INTO `client_messagetranslation` VALUES (2881,'AIR','Someter un comentario para este ítem.','ESN','--ANY--','--ANY--','��9��L�,�	�nc�','2014-03-22 20:34:28.093');
INSERT INTO `client_messagetranslation` VALUES (3717,'AIR','lector de línea','ESN','--ANY--','--ANY--','���J�vL�������','2013-08-08 09:26:31.873');
INSERT INTO `client_messagetranslation` VALUES (3105,'AIR','ʻAʻole hiki ke nānā ʻia ka hōʻike','HAW','--ANY--','--ANY--','�76$~C���:�r�ى','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2564,'AIR','Marcar para revisión','ESN','--ANY--','--ANY--','��S��HB����N�^\"','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3162,'AIR','Pono ?oe e lawe i ka ho?ike ma kou kula pono?i no ia wa ho?ohana.','HAW','--ANY--','--ANY--','�����LY�F�o���','2013-12-18 14:09:06.970');
INSERT INTO `client_messagetranslation` VALUES (2789,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','�<��~�C���!�Z\\�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2635,'AIR','¿Escuchaste el audio en inglés? Haz clic en [Sí] o [No].','ESN','--ANY--','--ANY--','�L��T�F舏�� C','2014-03-22 20:33:38.127');
INSERT INTO `client_messagetranslation` VALUES (3527,'AIR','Ho?a','HAW','--ANY--','--ANY--','�f-�&/E����fie>','2013-12-18 14:09:07.377');
INSERT INTO `client_messagetranslation` VALUES (2877,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','��XC{M��C��Hc','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3306,'AIR','Hiki ?ole ke ho?ouna ?ia kau mau la palena pau a me ka manawa palena pau ma mua o ka ho?opa?a ?ia ?ana o na la a me na manawa.','HAW','--ANY--','--ANY--','���o�	D�Bti�&','2013-12-18 14:09:07.123');
INSERT INTO `client_messagetranslation` VALUES (2389,'AIR','E \'olu\'olu, e Kainoa','HAW','--ANY--','--ANY--','��sSKOh�9�\"�C�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3355,'AIR','Koho i ke kula','HAW','--ANY--','--ANY--','��Ɛ�0A9�	�\r߱��','2013-12-18 14:09:07.157');
INSERT INTO `client_messagetranslation` VALUES (3431,'AIR','Pau ka ho?okomo ?ia ?ana o na ?ikepili ma ka la:','HAW','--ANY--','--ANY--','���)	Bt���r�','2013-12-18 14:09:07.230');
INSERT INTO `client_messagetranslation` VALUES (3251,'AIR','Na koho kala','HAW','--ANY--','--ANY--','�ב\\��N��Z�©�;Y','2013-12-18 14:09:07.040');
INSERT INTO `client_messagetranslation` VALUES (3103,'AIR','Ha?awi ?ole ?ia nei keia ho?ike nei ma kau kula mao ka punaewele puni honua.','HAW','--ANY--','--ANY--','���n��G���t�e��','2013-12-18 14:09:06.917');
INSERT INTO `client_messagetranslation` VALUES (3416,'AIR','Na palapala ho?apono kumu','HAW','--ANY--','--ANY--','��\'	+A��^��s�,','2013-12-18 14:09:07.207');
INSERT INTO `client_messagetranslation` VALUES (2574,'AIR','E Nana','HAW','--ANY--','--ANY--','����Oʰ?S��^��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3311,'SBAC','<h3>1. Select the Time (PT) to Deliver the Alert</h3>','ENU','--ANY--','--ANY--','�����Ko�|?�H!','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2989,'AIR','Aia he pilikia me ka ‘ōnaehana a e hoʻokuʻu ʻia ʻoe.','HAW','--ANY--','--ANY--','������@]��s�V�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3455,'AIR','<span style=\"display:none\"><a href=\"https://air.tds.airast.org/test_student/default.aspx\">?Ne?e aku i ke kahua haumana</a></span>','HAW','--ANY--','--ANY--','��<�K�NK�W�{����','2013-12-18 14:09:07.273');
INSERT INTO `client_messagetranslation` VALUES (2567,'AIR','Deje de hablar','ESN','--ANY--','--ANY--','����UyGŴ����U��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2391,'AIR','Nombre:','ESN','--ANY--','--ANY--','��n;H$/��r��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3216,'SBAC','No students match the State-SSID you entered.','ENU','--ANY--','--ANY--','�	�%>qMM��W��	.}','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3162,'SBAC','Before you can test, your school or district information must be updated.','ENU','--ANY--','--ANY--','�F\Z)/@��9fM5r�X','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2898,'AIR','Comenzar a escribir en el aviso seleccionado','ESN','--ANY--','--ANY--','�>!\n̂M�\0M�f�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3143,'AIR','E kala mai! \'A\'ole hiki iā \'oe ke komo i kēia \'ōnaehana. E \'olu\'olu, e hā\'awi i kēia helu i kāu kumu hō\'ike.','HAW','--ANY--','--ANY--','�H�F�B ��s���j','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2524,'AIR','<span>Finalizar prueba</span>','ESN','--ANY--','--ANY--','�I	(�BO��4��K}','2014-03-22 20:33:38.123');
INSERT INTO `client_messagetranslation` VALUES (2670,'AIR','Makaukau anei \'oe e waiho i kau ho\'ike?','HAW','--ANY--','--ANY--','�_���M�F���j٦','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2369,'AIR','No se pudo iniciar sesión. Inténtelo nuevamente o consulte con el administrador de la prueba.','ESN','--ANY--','--ANY--','�m9��A��t`EHӄ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3002,'AIR','Apono a pani','HAW','--ANY--','--ANY--','��0k�F���N��)!\r','2013-12-18 14:09:06.827');
INSERT INTO `client_messagetranslation` VALUES (3343,'AIR','Ua ho?a ?ia ka polokalamu palulu pukaaniani lele wale ma keia polokalamu punaewele nei.Ina makemake ?oe e komo aku ma keia kahua nei, pono ?oe e ho?opio aku i ka polokalamu palulu.?Olu?olu ho?opio aku i ka polokalamu palulu pukaaniani lele wale a laila e ','HAW','--ANY--','--ANY--','�� w1YO������X�V','2013-12-18 14:09:07.147');
INSERT INTO `client_messagetranslation` VALUES (2348,'AIR','../tools/formulas/2010/hi_7_math.html','HAW','--ANY--','--ANY--','��u��sH漝?&pU�R','2013-12-18 14:09:06.603');
INSERT INTO `client_messagetranslation` VALUES (3062,'AIR','?A?ole loa?a ke kope mana a i ?ole ua ho?ano hou ?ia paha','HAW','--ANY--','--ANY--','����V�@I�fxnh��','2013-12-18 14:09:06.887');
INSERT INTO `client_messagetranslation` VALUES (2458,'AIR','¿Es ésta su prueba?','ESN','--ANY--','--ANY--','�R��VOk�d�܋�D�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2977,'AIR','{silence}Opción A.{silence}','ESN','--ANY--','--ANY--','� �=�Km�pi��$��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2405,'AIR','SSID:','ESN','--ANY--','--ANY--','�+���iA��\'J���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2450,'AIR','<span>Cerrar sesión</span>','ESN','--ANY--','--ANY--','�Y�ȕ�BK�l\Zy\"���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3208,'AIR','He meahana ho?i ke kahua hui TA no na kahu ha?awi ho?ike no na haumana no ka ha?awi ?ana i na ho?ike kapu.','HAW','--ANY--','--ANY--','���j\rHJ�������','2013-12-18 14:09:07.000');
INSERT INTO `client_messagetranslation` VALUES (2706,'AIR','Tutorial','ESN','--ANY--','--ANY--','��X�;G����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3681,'AIR','Pio na ha?awina ukali','HAW','--ANY--','--ANY--','���[�^Bۃ���v�?}','2013-12-18 14:09:07.597');
INSERT INTO `client_messagetranslation` VALUES (2383,'AIR','Debe contestar todas las preguntas antes de seguir a la próxima página. (Es posible que deba desplazarse hacia abajo para ver todas las preguntas)','ESN','--ANY--','--ANY--','���\"�F��xy�5�[','2014-03-22 20:33:38.100');
INSERT INTO `client_messagetranslation` VALUES (2347,'AIR','../tools/formulas/2010/hi_6_math.html','HAW','--ANY--','--ANY--','�t�L�O2���&��-�','2013-12-18 14:09:06.603');
INSERT INTO `client_messagetranslation` VALUES (2627,'AIR','E koho i ke kiko a i \'ole ke ka\'e no ka ho\'onui \'ana i ka waiwai.','HAW','--ANY--','--ANY--','�7���IA�>���-','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2800,'AIR','Someter un comentario para este ítem.','ESN','--ANY--','--ANY--','�OH!�)Aՙ�����z','2014-03-22 20:33:38.083');
INSERT INTO `client_messagetranslation` VALUES (3409,'AIR','Ua ho?ea maila ?oe i ka palena pau o ka ho?ike.Kaomi i ka pihi [?Ae] no ka ho?omau ?ana i kekahi ?ao?ao a?e.Kaomi i ka pihi [Ho?ole] no ka ho?omau ?ana i keia ho?ike nei.','HAW','--ANY--','--ANY--','�_�y�qAÆ\0\0�8','2013-12-18 14:09:07.197');
INSERT INTO `client_messagetranslation` VALUES (2459,'AIR','Ka Helu Mahele Ho\'ike:','HAW','--ANY--','--ANY--','����Ih��J�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2902,'AIR','Error al cargar la calculadora. Entregue este código a su administrador de pruebas.','ESN','--ANY--','--ANY--','�\r���@�^���H�','2014-03-22 20:33:38.143');
INSERT INTO `client_messagetranslation` VALUES (3017,'AIR','(?Olu?olu e heluhelu:Ho?ili pu ?ia na ho?opa?alua a me na ho?okala pa?alua i ka manawa e ho?oili ?ia ai na ?ikepili ho?ike i ho?ulu?ulu ?ia.Loli ka ho?ulu?ulu ?ia ?ana o na ?ikepili i ka loli ?ana o na kahua kamepiula a hiki ke paio mai kekahi holo ?ana a i kekahi.)','HAW','--ANY--','--ANY--','��S�[A��L�\Z�\"�','2013-12-18 14:09:06.843');
INSERT INTO `client_messagetranslation` VALUES (2617,'AIR','Pilikia','HAW','--ANY--','--ANY--','�#^q/F��^�����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3602,'AIR','Makemake a nei ?oe e ha?alele?','HAW','--ANY--','--ANY--','�#����F�k���Z~�','2013-12-18 14:09:07.477');
INSERT INTO `client_messagetranslation` VALUES (3600,'AIR','La aprobación de la oportunidad de prueba ya no está pendiente. El estudiante pudo haber cancelado la petición.','ESN','--ANY--','--ANY--','�H1ɛ�L0�h�,����','2014-03-22 20:36:39.400');
INSERT INTO `client_messagetranslation` VALUES (3492,'AIR','Pau','HAW','--ANY--','--ANY--','�R\".�KO��D����','2013-12-18 14:09:07.330');
INSERT INTO `client_messagetranslation` VALUES (2988,'AIR','{silence}Koho F.{silence}','HAW','--ANY--','--ANY--','�nhƹRJi��T~ɜ��','2013-12-18 14:09:06.820');
INSERT INTO `client_messagetranslation` VALUES (2701,'AIR','Ka Pakuhi Kumumea','HAW','--ANY--','--ANY--','�}=��C%�S׊A2�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2846,'AIR','Someter un comentario para este ítem.','ESN','--ANY--','--ANY--','������ML�\0���%I','2014-03-22 20:34:06.450');
INSERT INTO `client_messagetranslation` VALUES (2694,'AIR','Ka Ha\'ilula','HAW','--ANY--','--ANY--','����xG���G�O','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3022,'AIR','Dada la carga actual en su sistema, usted debe ser capaz de probar el número solicitado de estudiantes en este sitio.','ESN','--ANY--','--ANY--','����]B��0s#�͋','2014-03-22 20:35:11.290');
INSERT INTO `client_messagetranslation` VALUES (3157,'AIR','Falló la inicialización del segmento','ESN','--ANY--','--ANY--','���~)l@贤4�$��','2014-03-22 20:37:20.653');
INSERT INTO `client_messagetranslation` VALUES (2400,'AIR','Ka Inoa:','HAW','--ANY--','--ANY--','�>���Lk�%�[�\Z�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2741,'AIR','14kiko','HAW','--ANY--','--ANY--','�z*�HԜW�D#�#\r','2013-12-18 14:09:06.653');
INSERT INTO `client_messagetranslation` VALUES (2608,'AIR','Nīnau Hoʻokani','HAW','--ANY--','--ANY--','��%�!kJʙ��l&��E','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2927,'AIR','‘A’ohe manawa kūpono i koe no ka hana ‘ana i kēia hō’ike nei.','HAW','--ANY--','--ANY--','��1��P@�p��C','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2775,'AIR','A\'OLE POLOLEI','HAW','--ANY--','--ANY--','����J!�㟆����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2912,'AIR','Ke ki\'i \'ia nei ka \'ao\'ao a\'e.','HAW','--ANY--','--ANY--','���]�tG%��J��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3712,'AIR','?A?ole i ho?ala maika?i ?ia ke kani.Makemake a nei ?oe e hoa?o hou?','HAW','--ANY--','--ANY--','���B�mI��fO:�n�H','2013-12-18 14:09:07.640');
INSERT INTO `client_messagetranslation` VALUES (3717,'AIR','Kahiāuli','HAW','--ANY--','--ANY--','�\n�q��J���o*��s','2013-08-13 12:45:03.753');
INSERT INTO `client_messagetranslation` VALUES (2601,'AIR','Diga la opción F','ESN','--ANY--','--ANY--','�U��OJ�}6.�1|)','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2405,'SBAC','State-SSID:','ENU','--ANY--','--ANY--','�j�r�BI��Z8�O�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3165,'AIR','Ho?ole ?ia ?oe mai ka lawe ?ana i keia ho?ike nei ma muli o ka ho?ole ?ia ?ana e na makua.','HAW','--ANY--','--ANY--','���tJؖ��Q����','2013-12-18 14:09:06.973');
INSERT INTO `client_messagetranslation` VALUES (2461,'AIR','Ka \'Olelo:','HAW','--ANY--','--ANY--','�8\0�A]���}�)\n�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2418,'AIR','Oportunidades*','ESN','--ANY--','--ANY--','�U�V qGu���ʢ��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2490,'AIR','Restablecer resaltado','ESN','--ANY--','--ANY--','�_7	 �@�[�[�:9','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2931,'AIR','Na pa?i no na makapo (Braille)','HAW','--ANY--','--ANY--','�b��@��S�OU#�','2013-12-18 14:09:06.740');
INSERT INTO `client_messagetranslation` VALUES (3094,'AIR','No Existe el ítem: {0}','ESN','--ANY--','--ANY--','�q���H������Ԟ','2014-03-22 20:35:48.640');
INSERT INTO `client_messagetranslation` VALUES (3708,'SBAC','{silence} C.{silence}','ENU','--ANY--','--ANY--','��i�6A5���c�#H','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2655,'SBAC','Borrar','ESN','--ANY--','--ANY--','����/�Bw��\"�B�u','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2940,'AIR','No aplicable','ESN','--ANY--','--ANY--','���>Bs�`1��\\.�','2014-03-22 20:34:47.187');
INSERT INTO `client_messagetranslation` VALUES (2372,'SBAC','¿Está seguro que quiere hacer una pausa en la prueba? Si se hace una pausa durante el examen, las respuestas no serán visibles. Avisa al administrador/a antes de pausar el examen.','ESN','--ANY--','--ANY--','���\n�F>�B`�ޱ�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2575,'AIR','E \'olu\'olu, e malama i kau hana ma mua o ka pa\'i \'ana.','HAW','--ANY--','--ANY--','�ɷg�Kä`|{�4J','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2631,'SBAC','Suelta el ratón para dejar el objeto en la posición  preferida.','ESN','--ANY--','--ANY--','��+H�L�\\�8��ܜ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2748,'AIR','Serif (Times)','ESN','--ANY--','--ANY--','��}��O���W*�~�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2448,'AIR','Hō‘oia ke kani. Pilikia ke kani.','HAW','--ANY--','--ANY--','�:�,WeM`����B�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3663,'AIR','(pono ke ?ano 1.4 a i ?ole ki?eki?e hou a?e)','HAW','--ANY--','--ANY--','���هJW�e$c��','2013-12-18 14:09:07.573');
INSERT INTO `client_messagetranslation` VALUES (2456,'AIR','Esperando aprobación del AP…','ESN','--ANY--','--ANY--','��ǳKAu�$�+p��g','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2803,'AIR','E pani i ka mana?o','HAW','--ANY--','--ANY--','��*���Ec��D�\Z]B','2013-12-18 14:09:06.683');
INSERT INTO `client_messagetranslation` VALUES (3417,'AIR','O keia a nei ka haumana?','HAW','--ANY--','--ANY--','��t�MS����O��','2013-12-18 14:09:07.210');
INSERT INTO `client_messagetranslation` VALUES (3613,'AIR','Mahalo no ka ho?opau ?ana i kau ho?ike!Mahope o ka loa?a ?ana o kekahi pane, kaomi i ka pihi [Na hopena o ka ho?ike] no ka ha?alele ?ana.','HAW','--ANY--','--ANY--','�M��Gރ�� �\\�','2013-12-18 14:09:07.493');
INSERT INTO `client_messagetranslation` VALUES (3139,'AIR','E kala mai! \'A\'ole hiki iā \'oe ke komo i kēia \'ōnaehana. E \'olu\'olu, e hā\'awi i kēia helu i kāu kumu hō\'ike.','HAW','--ANY--','--ANY--','�9�`��K%��	ח','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3673,'AIR','Na hopena o ka ho?ike','HAW','--ANY--','--ANY--','�b0��hD	�Ͳ?���','2013-12-18 14:09:07.590');
INSERT INTO `client_messagetranslation` VALUES (2910,'AIR','‘A‘ole leo maika‘i no ka ‘ōlelo Kepania i kēia kamepiula.','HAW','--ANY--','--ANY--','���6d\nAV�3�\0T��*','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2354,'AIR','../tools/formulas/2010/or_6_8_math_esn.html','ESN','--ANY--','--ANY--','�ށ�:1AI���myn��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2387,'AIR','Presione [Detener] en el reproductor de sonido antes de salir de esta página.','ESN','--ANY--','--ANY--','��SY��A(���y��j','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2887,'AIR','No permitir la selección de texto','ESN','--ANY--','--ANY--','��^٦J�*���\Z�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3196,'AIR','Kaomi i ka pihi [Na ho?ike] no ka nana ?ana a i ?ole no ka nana a i ?ole ho?okomo hou i kekahi ho?ike ?ea?e me keia wa ho?ohana.','HAW','--ANY--','--ANY--','��].�GM��R|E�I�','2013-12-18 14:09:06.983');
INSERT INTO `client_messagetranslation` VALUES (2918,'AIR','A\'ole i loa\'a keia hi\'ohi\'ona ma ka ho\'ike \'olelo Hawai\'i.','HAW','--ANY--','--ANY--','�KpؓrFW��y~�	��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2628,'AIR','Seleccione la ubicación de la etiqueta.','ESN','--ANY--','--ANY--','�O���@�F���.�}','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3469,'AIR','Na ?olea','HAW','--ANY--','--ANY--','�wg��fK�*Q�����','2013-12-18 14:09:07.293');
INSERT INTO `client_messagetranslation` VALUES (2698,'AIR','Ka \'Olelo','HAW','--ANY--','--ANY--','���se�O����w�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2759,'AIR','POLOLEI','HAW','--ANY--','--ANY--','��J*\n�JF������r�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2362,'AIR','E kikokiko i kou inoa mua.','HAW','--ANY--','--ANY--','��l:oKL����%��Z�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3666,'AIR','Nana hou i ka ELPA','HAW','--ANY--','--ANY--','��{��N���*t=\n','2013-12-18 14:09:07.577');
INSERT INTO `client_messagetranslation` VALUES (3112,'AIR','Hiki wale no ke holoi ?ia na manawa kupono mai ka ho?onoho nui a li?ili?i.Ua nui hou a?e na manawa kupono ma mua o {0}','HAW','--ANY--','--ANY--','�ם5�NJC��.���Ƃ','2013-12-18 14:09:06.930');
INSERT INTO `client_messagetranslation` VALUES (2798,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','�=����G��\r3��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2724,'AIR','E ho\'ohuli i ka waiho\'olu\'u','HAW','--ANY--','--ANY--','�?��J�ҁSɵ\r�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2978,'AIR','{silence}Opción B.{silence}','ESN','--ANY--','--ANY--','��sq��B�\n{r��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3076,'AIR','ʻAʻole pololei ka helu o ke kino kākau','HAW','--ANY--','--ANY--','���(v�Dׯ�T�`Ι','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2995,'AIR','Na kuhikuhi no ka pa?ani ?ana','HAW','--ANY--','--ANY--','�\r(U�D���Cv��}','2013-12-18 14:09:06.820');
INSERT INTO `client_messagetranslation` VALUES (2904,'AIR','ʻOki Leo','HAW','--ANY--','--ANY--','�]`��C�����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2715,'AIR','Científico','ESN','--ANY--','--ANY--','�o2\0�gG������','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2861,'AIR','Someter un comentario para este ítem.','ESN','--ANY--','--ANY--','��λJL��a�����','2014-03-22 20:34:06.510');
INSERT INTO `client_messagetranslation` VALUES (3699,'AIR','Ua loa?a kekahi pilikia me ke.?Olu?olu e ?e?e hou no ka ho?omau ?ana i ka ho?okomo ?ana i na ?ikepili.','HAW','--ANY--','--ANY--','���x�YK���2����','2013-12-18 14:09:07.620');
INSERT INTO `client_messagetranslation` VALUES (2734,'AIR','HI 4 Makemakika','HAW','--ANY--','--ANY--','���F��IS���/?�','2013-12-18 14:09:06.640');
INSERT INTO `client_messagetranslation` VALUES (2719,'AIR','Celeste','ESN','--ANY--','--ANY--','��vuPB������Pa�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3140,'AIR','Ua ho?oku ?ia keia ho?ike nei.','HAW','--ANY--','--ANY--','���H��Gv�W����4','2013-12-18 14:09:06.960');
INSERT INTO `client_messagetranslation` VALUES (2948,'AIR','No','ESN','--ANY--','--ANY--','�<��lL����:','2014-03-22 20:34:47.193');
INSERT INTO `client_messagetranslation` VALUES (2667,'AIR','El audio se está reproduciendo o está pausado. Detén el audio antes de navegar hacia otra página.','ESN','--ANY--','--ANY--','�6���I���q_H{yp','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2375,'AIR','La pregunta no se cargó. Haga clic en [Sí] para intentarlo nuevamente o en [No] para salir de la prueba.','ESN','--ANY--','--ANY--','�h݀�AI�M[�p���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3489,'AIR','?Apono i ka noi','HAW','--ANY--','--ANY--','��級C���gZnj�','2013-12-18 14:09:07.327');
INSERT INTO `client_messagetranslation` VALUES (2372,'AIR','¿Está seguro de que desea detener la prueba? Si se detiene la prueba por más de {0} minutos, usted quizás no pueda hacer cambios a las preguntas que usted ya ha contestado. Consulte a su administrador de prueba antes de pausar la prueba.','ESN','--ANY--','--ANY--','��A?-\Z@r��㗺p<','2014-03-22 20:33:38.093');
INSERT INTO `client_messagetranslation` VALUES (2563,'AIR','Cargando, espere...','ESN','--ANY--','--ANY--','���]��A_�YJ�1�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2592,'AIR','Opción oral A (en inglés)','ESN','--ANY--','--ANY--','�(�d�C֔�̱E���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3413,'AIR','Ho?okomo i na ho?ike pilikino o ka haumana','HAW','--ANY--','--ANY--','�]�(8C����ߦ.m�','2013-12-18 14:09:07.200');
INSERT INTO `client_messagetranslation` VALUES (2515,'AIR','E ho\'i i ka papakaumaka kainoa.','HAW','--ANY--','--ANY--','�b�S��@ث��C��Li','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3122,'AIR','Hiki ia keia haumana ke ho?omaka. Ua ?apono ?ia.','HAW','--ANY--','--ANY--','�r٧��CG���\'9�pR','2013-12-18 14:09:06.940');
INSERT INTO `client_messagetranslation` VALUES (2817,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','�xV�IYD\\�;�rP��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2369,'AIR','A\'ole i hana \'ia ka \'e\'e \'ana. E \'olu\'olu \'oe, \'e ho\'a\'o hou a i\'ole e ha\'i i kau kumu ho\'ike.','HAW','--ANY--','--ANY--','�}z���A�6\\�G~�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2487,'AIR','Diga {title}','ESN','--ANY--','--ANY--','��F0eO2�o?<WpR','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2639,'AIR','Sí, lo escuché.','ESN','--ANY--','--ANY--','�� B�Eb��߱�0|1','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2733,'AIR','DE 9-10 Makemakika','HAW','--ANY--','--ANY--','��4;SpJ�KK��a��','2013-12-18 14:09:06.640');
INSERT INTO `client_messagetranslation` VALUES (2386,'AIR','¿Desea guardar la respuesta antes de salir de esta página?  <Guardar respuesta> <Eliminar respuesta>','ESN','--ANY--','--ANY--','����7�Ca��\'l	7�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3130,'AIR','La sesión esta cerrada.','ESN','--ANY--','--ANY--','��`�qF�լ�,','2014-03-22 20:37:20.650');
INSERT INTO `client_messagetranslation` VALUES (2488,'AIR','Deje de hablar','ESN','--ANY--','--ANY--','���y�@����Z.','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3140,'AIR','La oportunidad de de prueba ha sido detenida','ESN','--ANY--','--ANY--','�\Zf��,E���W5�ްG','2014-03-22 20:37:20.653');
INSERT INTO `client_messagetranslation` VALUES (3441,'AIR','Kahua ho?okomo ?ikepili','HAW','--ANY--','--ANY--','���űK.�K������','2013-12-18 14:09:07.250');
INSERT INTO `client_messagetranslation` VALUES (2531,'AIR','Fórmula','ESN','--ANY--','--ANY--','�6h��D؛�ˡTo�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3134,'AIR','Se produjo un problema con el sistema.  Inténtelo nuevamente.','ESN','--ANY--','--ANY--','�9��tNE(�����}�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3050,'AIR','No se puede encontrar la base de datos RTS','ESN','--ANY--','--ANY--','�>���Nv�Q��Ye�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2720,'AIR','Gris','ESN','--ANY--','--ANY--','�NNs��@��g�Wi��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2446,'AIR','<span>ʻAe</span>','HAW','--ANY--','--ANY--','�`�@H5Ir�}PD�E�\r','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3364,'AIR','<h3>Na mana?o ho?ole</h3><p>Na huina nui o ka la - ?a?ole paha e ho?ohui ?ia na hola i ho?opili pu ?ia.Ke ho?ike nei keia mau helu i ka nui o na mea ho?ohana ku kahi o ka la, a e ho?ike ?ole ?ia ho?i na mea ho?ohana ku kahi i hana ai i loko o ho?okahi a i \'ole elua mau hola a lakou i lawe piha aku ai i na ho?ike.</p>','HAW','--ANY--','--ANY--','��/�:Ms��E��N','2013-12-18 14:09:07.167');
INSERT INTO `client_messagetranslation` VALUES (2358,'SBAC','../Projects/SBAC/Help/help.html','ENU','--ANY--','--ANY--','�����N�~,��e','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3462,'AIR','Inoa ho?ohana:','HAW','--ANY--','--ANY--','��F\\�XC��	����','2013-12-18 14:09:07.280');
INSERT INTO `client_messagetranslation` VALUES (3109,'AIR','No es posible realizar la transacción de actualización para mover los datos requeridos.','ESN','--ANY--','--ANY--','�ɘW{Fͦ�s�R','2014-03-22 20:35:48.650');
INSERT INTO `client_messagetranslation` VALUES (3692,'SBAC','cuaderno','ESN','--ANY--','--ANY--','��X4>XA���>q�g��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3657,'AIR','Ho?oili i na loa?a:','HAW','--ANY--','--ANY--','���\'�M4��\"Y�C��','2013-12-18 14:09:07.563');
INSERT INTO `client_messagetranslation` VALUES (3027,'AIR','Se produjo un problema con el sistema.  Proporcione este número al AP.','ESN','--ANY--','--ANY--','�񆑃DL����Hƪ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2859,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','��xZ�G>����f�T','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2699,'AIR','Marcar para la revisión','ESN','--ANY--','--ANY--','�(����D����~D�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3716,'AIR','Advertencia: No se puede ajustar el volumen de su iPad durante la prueba. Si necesita ajustar el volumen, por favor apague Acceso Guiado. Ajuste el volumen con los botones de control de volumen en el iPad y, entonces, activar Acceso Guiado. Si usted necesita ayuda, por favor, consulte a su administrador.','ESN','--ANY--','--ANY--','�S޿,�E���3Z�J@\r','2014-03-22 20:36:39.993');
INSERT INTO `client_messagetranslation` VALUES (2881,'AIR','Waiho i mana?o no keia ?ikemu pono no na ho?ike.','HAW','--ANY--','--ANY--','�|8b�F��U�b�i�h','2013-12-18 14:09:06.733');
INSERT INTO `client_messagetranslation` VALUES (2702,'AIR','E Pa\'i Ke Noi Mai','HAW','--ANY--','--ANY--','��P�O��PZ`��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3351,'SBAC','No students match the State-SSID you entered.','ENU','--ANY--','--ANY--','�؅��CX��DN�~�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2756,'AIR','FALSO','ESN','--ANY--','--ANY--','��0F��N��,z�W�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3562,'AIR','?Ae','HAW','--ANY--','--ANY--','�V��L��|v���j','2013-12-18 14:09:07.433');
INSERT INTO `client_messagetranslation` VALUES (2615,'AIR','Detener reproducción de grabación','ESN','--ANY--','--ANY--','�W!��G怔�<�2z�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2856,'AIR','Someter un comentario para este ítem.','ESN','--ANY--','--ANY--','�m�W+M��Q�����(','2014-03-22 20:34:06.490');
INSERT INTO `client_messagetranslation` VALUES (2819,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','�1��0{H��0}�5\'�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3039,'AIR','Loa?a ?ole keia wa ho?ohana.','HAW','--ANY--','--ANY--','�Skr}�L�d��k\n','2013-12-18 14:09:06.860');
INSERT INTO `client_messagetranslation` VALUES (3095,'AIR','No se encuentra la base de datos RTS','ESN','--ANY--','--ANY--','�|0�=�G���p�X?','2014-03-22 20:35:48.640');
INSERT INTO `client_messagetranslation` VALUES (3259,'AIR','Maka no ka nana hou ?ana.','HAW','--ANY--','--ANY--','���q�A�>���Fƺ','2013-12-18 14:09:07.053');
INSERT INTO `client_messagetranslation` VALUES (3138,'AIR','No puede realizar la prueba en esta sesión. Solicite ayuda al administrador del sistema.','ESN','--ANY--','--ANY--','��\03�\0@���Zox','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2365,'AIR','O  su nombre o su ID de estudiante no se ingresó correctamente. Inténtelo nuevamente o consulte con el administrador de la prueba.','ESN','--ANY--','--ANY--','��UQM���/뀹�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2510,'SBAC','Llegó al fin del examen.','ESN','--ANY--','--ANY--','���сBu�*v��ğ�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3082,'AIR','Usuario no tiene permiso','ESN','--ANY--','--ANY--','��i�j�AT�/�h_�','2014-03-22 20:35:32.333');
INSERT INTO `client_messagetranslation` VALUES (2489,'AIR','E kahiauli i ka Mahele i koho \'ia','HAW','--ANY--','--ANY--','��c.�E@\\��,��|�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2341,'AIR','De acuerdo con la Ley de derechos educativos y de privacidad de la familia (FERPA), está legalmente prohibida la divulgación de información de identificación personal.','ESN','--ANY--','--ANY--','�`��D��Ճ�@��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2815,'AIR','Imprimir item','ESN','--ANY--','--ANY--','�KGr7F0�k���@>`','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3712,'AIR','El sonido no se ha inicializado correctamente. ¿Quieres intentarlo de nuevo?','ESN','--ANY--','--ANY--','�]���DL�5���+F','2014-03-22 20:36:39.960');
INSERT INTO `client_messagetranslation` VALUES (2925,'AIR','<span style=\"font-weight:bold; \">Es posible que el examen no reciba calificación si:</span> es tan breve que una calificación sería irrelevante, está en blanco, está escrito en otro idioma que no sea inglés, está escrito en forma de poesía o drama, es un plagio de otras fuentes, no corresponde a uno de los temas dados, o contiene blasfemias o violencia gráfica que exceden los estándares habituales de la comunidad respecto a la escritura escolar.','ESN','--ANY--','--ANY--','�dlg�UFÓ�AF�g','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2376,'AIR','‘A‘ole i ho‘ouka ‘ia ka ‘ao‘ao. E ‘olu‘olu, e lele ‘oe a i ‘ole e nīnau ‘oe iā Kumu.','HAW','--ANY--','--ANY--','�~���uJ��E[,\Z�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2742,'AIR','ʻAʻohe','HAW','--ANY--','--ANY--','���V�E��.&�^�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3091,'AIR','Pau ka wā hana hōʻike ma {0}. No ke kōkua, e ʻōlelo me kāu luna hōʻike.','HAW','--ANY--','--ANY--','�����I²�v���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3014,'SBAC','Llegó  al fin de la sección.','ESN','--ANY--','--ANY--','��I!�WOr�#Im�p�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2552,'AIR','Cerrar tutorial','ESN','--ANY--','--ANY--','���3QOc�3z�醜\'','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2413,'AIR','Ka Nui O ka Pa\'i \'Ana','HAW','--ANY--','--ANY--','��QW�.N�%�U!2�+','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3064,'AIR','Intente sobrescribir el artículo existente','ESN','--ANY--','--ANY--','�	�b\Z�G=���V�`�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2344,'AIR','../tools/formulas/2010/de_9_10_eoc.html','HAW','--ANY--','--ANY--','��N�Kj�N\ro[��','2013-12-18 14:09:06.600');
INSERT INTO `client_messagetranslation` VALUES (2510,'AIR','Ho\'omaika\'i \'ia \'oe, ua hiki mai \'oe i ka hopena o ka ho\'ike!','HAW','--ANY--','--ANY--','�.qIr�A3�����%q�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3424,'AIR','<p>Na kuhikuhi ?e?e:</p><ul><li>Ho?okomo i ka inoa mua o ka haumana.</li><li>Ho?okomo i ka helu SSID o ka haumana.</li><li>Kaomi i ka pihi [?E?e].</li></ul>','HAW','--ANY--','--ANY--','�;B��@Ɏל\'����','2013-12-18 14:09:07.220');
INSERT INTO `client_messagetranslation` VALUES (3685,'AIR','No se le permite ingresar a la sesión sin un administrador de prueba.','ESN','--ANY--','--ANY--','�L%`Z�@	��}��a�','2014-03-22 20:36:39.897');
INSERT INTO `client_messagetranslation` VALUES (2516,'SBAC','Test Successfully Submitted','ENU','--ANY--','--ANY--','�X�>�hO��2&�}=�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2399,'AIR','¿Es éste usted?','ESN','--ANY--','--ANY--','�a���K��Zx�P���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2749,'AIR','\'A\'OLE POLOLEI','HAW','--ANY--','--ANY--','�q0#!�Bl�UZ��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2828,'AIR','Cerrar Comentario','ESN','--ANY--','--ANY--','���	RBi�kKf�R�#','2014-03-22 20:34:06.377');
INSERT INTO `client_messagetranslation` VALUES (2732,'AIR','DE 6-8 Makemakika','HAW','--ANY--','--ANY--','���pm(Mz�4�A)�0m','2013-12-18 14:09:06.637');
INSERT INTO `client_messagetranslation` VALUES (2855,'AIR','Imprimir item','ESN','--ANY--','--ANY--','�Pvq}M��l���q	Z','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2569,'AIR','E ho\'omaka hou i ka mea hana kahiauli','HAW','--ANY--','--ANY--','�p�ƿA�3.3<��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2522,'AIR','<span>Cerrar sesión</span>','ESN','--ANY--','--ANY--','��ٰ&L|�I�\nI2O','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2501,'AIR','E \'olu\'olu, e heluhelu hou \'oe i ka \'ike i luna a\'e. Inā pololei ka \'ike a pau, e kāomi i [\'Ae], inā \'a\'ole e kāomi i [\'A\'ole].','HAW','--ANY--','--ANY--','�Щ���Dq���ƒV�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2360,'AIR','Calculadora','ESN','--ANY--','--ANY--','���T�B���k��H','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2390,'AIR','Usuario invitado','ESN','--ANY--','--ANY--','�ᤙ��E�������','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2642,'AIR','Haʻalele.','HAW','--ANY--','--ANY--','��prN��[���[�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2569,'AIR','Restablecer resaltado','ESN','--ANY--','--ANY--','��ʨAV��@T���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3351,'AIR','?A?ohe haumana i kohu me ka SSID au i ho?okomo aku ai.','HAW','--ANY--','--ANY--','�����C��\"�b.�4�','2013-12-18 14:09:07.157');
INSERT INTO `client_messagetranslation` VALUES (3668,'AIR','Hinahina ?ano ikaika ma luna o ka hinahina lahilahi','HAW','--ANY--','--ANY--','�X���Kf���[�#=','2013-12-18 14:09:07.580');
INSERT INTO `client_messagetranslation` VALUES (2954,'AIR','Nivel 4','ESN','--ANY--','--ANY--','�\'OIG��9�|G`��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2389,'AIR','Inicie sesión.','ESN','--ANY--','--ANY--','�j5C(C�����G�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3710,'SBAC','{silence} E.{silence}','ENU','--ANY--','--ANY--','�����I���˜w','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2429,'AIR','El administrador de pruebas ha rechazado su solicitud.','ESN','--ANY--','--ANY--','���Ə�L��t:���','2014-03-22 20:33:38.100');
INSERT INTO `client_messagetranslation` VALUES (2830,'AIR','Imprimir item','ESN','--ANY--','--ANY--','������Ma����c31�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2506,'AIR','<p class=\"selectTest\"> </p>','ESN','--ANY--','--ANY--','��:X�)A���&#���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2953,'AIR','Nivel 3','ESN','--ANY--','--ANY--','���puKй�L�ë','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2653,'AIR','‘A‘ohe kani o kēia kamepiula.','HAW','--ANY--','--ANY--','�\n��z�Or��K��c�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3526,'AIR','?Olu?olu e ho?okomo i ka helu ID wa ho?ohana: ','HAW','--ANY--','--ANY--','�T�tL�,�����','2013-12-18 14:09:07.377');
INSERT INTO `client_messagetranslation` VALUES (2907,'AIR','Conservar','ESN','--ANY--','--ANY--','�WL�� CҮ	3Ї\"x0','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2713,'AIR','Mea Maʻa Mau','HAW','--ANY--','--ANY--','�g\0_�Fᖖ\0��I��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3102,'AIR','Esta prueba no está disponible en este momento.','ESN','--ANY--','--ANY--','�t���E��_�H�U','2014-03-22 20:35:48.647');
INSERT INTO `client_messagetranslation` VALUES (2409,'AIR','<-- Debe seleccionar un grado.','ESN','--ANY--','--ANY--','����E{����Fc3�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3726,'AIR','<p> La grabadora requiere un plug-in cuando se utiliza el navegador Firefox. </ p> Usted puede utilizar el navegador seguro en lugar de Firefox para acceder al comprobar de sonido y el comprobador de la grabadora. </ p>','ESN','--ANY--','--ANY--','��zÌ�C���!���','2014-03-22 20:36:40.010');
INSERT INTO `client_messagetranslation` VALUES (2424,'AIR','Ka Helu Mahele Ho\'ike:','HAW','--ANY--','--ANY--','���dGy�c����#','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3418,'AIR','Na ho?ike no keia haumana','HAW','--ANY--','--ANY--','���\\|L\"��R6��E','2013-12-18 14:09:07.210');
INSERT INTO `client_messagetranslation` VALUES (3522,'AIR','Kapae','HAW','--ANY--','--ANY--','���k!�NM��� F_�','2013-12-18 14:09:07.370');
INSERT INTO `client_messagetranslation` VALUES (2899,'AIR','Cambiar aviso','ESN','--ANY--','--ANY--','��ظ�A��j�ߋ/�F','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3172,'SBAC','You are now leaving the Online Testing System. By clicking [Exit], your test session and all in-progress tests will be paused. You may click [Return] to remain logged into the Online Testing System and continue testing without any disruption.','ENU','--ANY--','--ANY--','�T��G��*��)`�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2590,'AIR','E ʻŌlelo I Ka Nīnau (Ma Ka Namu Haole)','HAW','--ANY--','--ANY--','�r��}�G���S��w','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3307,'AIR','Ua ho?ouna piha ?ia na memo i waiho ?ia','HAW','--ANY--','--ANY--','�tRd�vL��`֪C�~�','2013-12-18 14:09:07.123');
INSERT INTO `client_messagetranslation` VALUES (2600,'AIR','Opción oral E (en inglés)','ESN','--ANY--','--ANY--','����C|�l���9@','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2366,'SBAC','Ask your Test Administrator to help you log into this site using the secure browser.','ENU','--ANY--','--ANY--','��H��FB���V���','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2371,'AIR','Ua ho\'omaka \'ia he polokolamu \'e a\'e, a e lele ana \'oe. Ho\'ike \'ia ka polokolamu i lalo nei. E \'olu\'olu \'oe, e ha\'i i kau kumu ho\'ike.','HAW','--ANY--','--ANY--','�N;��B���/��!�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3099,'AIR','La sesión no está disponible. Consulta con su administrador de pruebas.','ESN','--ANY--','--ANY--','�l�\'!uJ9���Ӓ�*','2014-03-22 20:33:38.150');
INSERT INTO `client_messagetranslation` VALUES (3726,'AIR','<p>Pono e loa?a ka polokalamu no ka ?oki leo ?ana ina ke ho?ohana nei ?oe i ka polokalamu punaewele Firefox. </p>  <p>Hiki ia?oe ke ho?ohana i kekahi polokalamu punaewele kapu ma kahi o Firefox no ke komo ?ana i ke ho?oia kani a me ke ho?oia o ka mikini lola.</p>','HAW','--ANY--','--ANY--','��M��J�ӀM��n','2013-12-18 14:09:07.660');
INSERT INTO `client_messagetranslation` VALUES (3442,'AIR','<span>?Olu?olu e nana hou i na koho a pau ma mua o ka waiho ?ana i na ho?ike no ka helu ?ai ?ana.</span>','HAW','--ANY--','--ANY--','�(���B�Ly�C|�','2013-12-18 14:09:07.250');
INSERT INTO `client_messagetranslation` VALUES (2540,'AIR','E holo mua','HAW','--ANY--','--ANY--','�2y؜�Fy�3��#I��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3664,'AIR','Ejecutar diagnósticos de red ','ESN','--ANY--','--ANY--','�L�I:�nг�	','2014-03-22 20:36:39.810');
INSERT INTO `client_messagetranslation` VALUES (2919,'AIR','Enviar','ESN','--ANY--','--ANY--','��ӏ��D±L�����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2772,'AIR','VERDADERO','ESN','--ANY--','--ANY--','��\\\r�I�g�POE>d','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3598,'AIR','Makemake a nei ?oe e ho?okomo hou i kekahi ?ikepili no ka haumana{0},{1} me ka SSID {2}?','HAW','--ANY--','--ANY--','��gl_zC�p�����','2013-12-18 14:09:07.463');
INSERT INTO `client_messagetranslation` VALUES (2663,'AIR','Tutorial','ESN','--ANY--','--ANY--','�؝�i�Cٻ�z�i�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3612,'AIR','A?ole kupono','HAW','--ANY--','--ANY--','��]k�JH��u=�/��','2013-12-18 14:09:07.490');
INSERT INTO `client_messagetranslation` VALUES (2363,'AIR','Ingrese la ID de estudiante.','ESN','--ANY--','--ANY--','�5C��9�QV�!','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2810,'AIR','Imprimir item','ESN','--ANY--','--ANY--','�#�6�I��3�c','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3312,'AIR','<p>Ho?ouna i memo i ke kahu ha?awi ho?ike i keia mana a i ?ole e ho?ouna aku i kekahi manawa ?ea?e.</p>','HAW','--ANY--','--ANY--','�9�\rx�@m�� �X�','2013-12-18 14:09:07.130');
INSERT INTO `client_messagetranslation` VALUES (2632,'AIR','E kaomi no ka waiho \'ana i ka mea i kahi au e makemake ai.','HAW','--ANY--','--ANY--','�A��҄G����3���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3166,'AIR','El campo de ingreso de la ID del estudiante no está disponible.','ESN','--ANY--','--ANY--','�E`�}\'Fؼ�#x�','2014-03-22 20:39:22.707');
INSERT INTO `client_messagetranslation` VALUES (2422,'AIR','E kaomi i ane\'i e ho\'oki.','HAW','--ANY--','--ANY--','�R�\nP�DE����z&�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3564,'AIR','Ha?alele','HAW','--ANY--','--ANY--','�\\YetB耟V#J}�','2013-12-18 14:09:07.437');
INSERT INTO `client_messagetranslation` VALUES (3628,'AIR','manawa kupono {0} o ka {1}','HAW','--ANY--','--ANY--','���B�ZL����8_��a','2013-12-18 14:09:07.520');
INSERT INTO `client_messagetranslation` VALUES (2790,'AIR','Imprimir item','ESN','--ANY--','--ANY--','���s�I��u��͡�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2735,'AIR','HI 5 Makemakika','HAW','--ANY--','--ANY--','���f��AA�:V?3�/�','2013-12-18 14:09:06.643');
INSERT INTO `client_messagetranslation` VALUES (3203,'AIR','Na ?apono a me na ho?onohonoho ho?ike','HAW','--ANY--','--ANY--','��)1�hC��l�@���','2013-12-18 14:09:06.993');
INSERT INTO `client_messagetranslation` VALUES (2728,'AIR','Amarillo','ESN','--ANY--','--ANY--','�Ж��$L�����غ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2466,'AIR','Ua ho‘ole ke Kumu Ho\'ike i kau noi.','HAW','--ANY--','--ANY--','��d���O[���{��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2999,'AIR','SSID','ESN','--ANY--','--ANY--','��n}�<E��c�j�/%','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3080,'AIR','Hiki ?ole ke loa?a na ?ikepili ho?ike ma ke kahua RTS','HAW','--ANY--','--ANY--','�&�Ɲ7M�G��S�','2013-12-18 14:09:06.897');
INSERT INTO `client_messagetranslation` VALUES (3607,'AIR','Texto ampliable','ESN','--ANY--','--ANY--','�(<�QLR��� v��','2014-03-22 20:36:39.440');
INSERT INTO `client_messagetranslation` VALUES (2779,'AIR','ʻŌkuhi & ʻIkamu','HAW','--ANY--','--ANY--','�+~T6Mɼb�}��}','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2774,'AIR','VERDADERO','ESN','--ANY--','--ANY--','�6M��Na��>l�S��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3645,'AIR','Mana ?u?uku:','HAW','--ANY--','--ANY--','�G��1�I��zp��','2013-12-18 14:09:07.543');
INSERT INTO `client_messagetranslation` VALUES (3229,'AIR','<p>No ka pa?i ?ana i na koho i koho ?ia, kaomi i ka pihi [Pa?i] ma lalo iho nei.E pa?i ?ia ana na ?ao?ao e ho?ike ?ia nei:1) loa?a ka inoa o na haumana a me na kulekele no na pilina kapu ma ka ?ao?ao mua a me ka ?ao?ao hope, 2) a me ka ?ao?ao me na koho i koho ?ia e pili ana i ke ana ho?ike.A kekahi mea ?ea?e, e pani ?ia ana keia pukaaniani nei ina kaomi ?ia ka pihi.</p>  <br />  <p>Maika?i no ke haha?i ?ia na lula a me na kulekele e pili ana i keia hana nei.</p>','HAW','--ANY--','--ANY--','�^��K\Z��g���t','2013-12-18 14:09:07.020');
INSERT INTO `client_messagetranslation` VALUES (3673,'AIR','Resultados de la prueba','ESN','--ANY--','--ANY--','�qRz8(I<�������','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3434,'AIR','?Olu?olu e kali, ke ho?omakaukau ?ia nei ka ho?ike.','HAW','--ANY--','--ANY--','��*=��E(�$�}\reF','2013-12-18 14:09:07.237');
INSERT INTO `client_messagetranslation` VALUES (2782,'SBAC','Passages and Items','ENU','--ANY--','--ANY--','��ׇ�]D6���e=x8','2013-09-06 14:31:15.707');
INSERT INTO `client_messagetranslation` VALUES (2910,'AIR','No se encontró un paquete de voz apropiado para el idioma español en este equipo.','ESN','--ANY--','--ANY--','��~a?EK�ņ�٫�	','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3279,'AIR','Paniki','HAW','--ANY--','--ANY--','��ht�YA7�d(\\y�^�','2013-12-18 14:09:07.083');
INSERT INTO `client_messagetranslation` VALUES (2834,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','���b]�NH��f��\\�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2454,'AIR','Pono ka Flash 9 a ʻoi no ka hana ʻana i kēia hōʻike','HAW','--ANY--','--ANY--','�8���C�U�����{','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3413,'AIR','Inicie sesión.','ESN','--ANY--','--ANY--','�_�\n;�FC�$di`$��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2457,'AIR','Haga clic aquí para cancelar y regresar al inicio.','ESN','--ANY--','--ANY--','�c�/CBH�J�!IH�G','2014-03-22 20:33:38.110');
INSERT INTO `client_messagetranslation` VALUES (2441,'AIR','No Ka Leo Kani: E ʻoki a e hoʻokani i kou inoa.','HAW','--ANY--','--ANY--','�kY[�}H@���LL}#','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2564,'AIR','E maka no ka nana hou \'ana','HAW','--ANY--','--ANY--','���wcH!�o@E���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3011,'AIR','E Koho','HAW','--ANY--','--ANY--','�� fm@A�s跺%��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2340,'AIR','‘A‘ole e hō‘ike ‘ia ana nā helu ‘ai ma nā Hō’ike Ho’oma’ama’a.','HAW','--ANY--','--ANY--','����eOb��II���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3701,'SBAC','{silence} B.{silence}','ENU','--ANY--','--ANY--','��[F\"aJ\'��R�q�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3627,'AIR','Kula','HAW','--ANY--','--ANY--','��C��\rC��J��Z','2013-12-18 14:09:07.517');
INSERT INTO `client_messagetranslation` VALUES (3069,'AIR','Intente insertar el artículo después de la última posición','ESN','--ANY--','--ANY--','���\Z$�NF��A�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2443,'AIR','Comenzar al principio','ESN','--ANY--','--ANY--','��:\"J4��8�P>�t','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2650,'SBAC','Cerrar sesión','ESN','--ANY--','--ANY--','�N��x�@l���lI%�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3112,'AIR','Las oportunidades sólo pueden eliminarse de forma secuencial en orden descendente. Existe una oportunidad superior a {0}','ESN','--ANY--','--ANY--','�yh��I�4��q�h','2014-03-22 20:35:48.653');
INSERT INTO `client_messagetranslation` VALUES (2481,'AIR','Recurso de audio','ESN','--ANY--','--ANY--','�����N��+���]C�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2652,'AIR','E ho‘omau','HAW','--ANY--','--ANY--','��V���F���Ɋ�V','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2883,'AIR','Cerrar Comentario','ESN','--ANY--','--ANY--','��XJI�Mї�\'�J	','2014-03-22 20:34:28.107');
INSERT INTO `client_messagetranslation` VALUES (2470,'AIR','Haga clic [?]','ESN','--ANY--','--ANY--','��Bw��B��Y?����+','2014-03-22 20:33:38.110');
INSERT INTO `client_messagetranslation` VALUES (3624,'AIR','No hay pruebas disponibles en este momento.','ESN','--ANY--','--ANY--','����3GOs��(7�IQ','2014-03-22 20:36:39.540');
INSERT INTO `client_messagetranslation` VALUES (2509,'SBAC','Scores are not displayed for this test.','ENU','--ANY--','--ANY--','�Gx�JJܽ�v�#B��','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2764,'AIR','Más grande','ESN','--ANY--','--ANY--','�T(�r�Cľ�{�R��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2651,'AIR','Intentar de nuevo','ESN','--ANY--','--ANY--','�\\��\rG���37H�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3089,'AIR','ʻAʻohe wā hana hōʻike. E ʻoluʻolu, e ʻōlelo me kāu luna hōʻike.','HAW','--ANY--','--ANY--','�g�\0�M���=>�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2665,'AIR','Sistema de Pruebas de AIR','ESN','--ANY--','--ANY--','�r8���Aӕ��V�M�','2014-03-22 20:33:38.133');
INSERT INTO `client_messagetranslation` VALUES (2465,'AIR','Espere','ESN','--ANY--','--ANY--','����l�A��]�%/��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2977,'AIR','{silence}Koho A.{silence}','HAW','--ANY--','--ANY--','��48GA�����Ò�','2013-12-18 14:09:06.800');
INSERT INTO `client_messagetranslation` VALUES (3566,'AIR','?E?e hou i loko?','HAW','--ANY--','--ANY--','������M���иĉH�','2013-12-18 14:09:07.440');
INSERT INTO `client_messagetranslation` VALUES (2959,'AIR','No mostrarle la puntuación al estudiante','ESN','--ANY--','--ANY--','����6IL���G�;�8','2014-03-22 20:34:47.200');
INSERT INTO `client_messagetranslation` VALUES (2544,'SBAC','Cerrar sesión','ESN','--ANY--','--ANY--','��\"��VG鎹��vK�Q','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2828,'AIR','E pani i ka mana?o','HAW','--ANY--','--ANY--','� ��^�K��m��>d�','2013-12-18 14:09:06.700');
INSERT INTO `client_messagetranslation` VALUES (2519,'AIR','Nombre de la prueba:','ESN','--ANY--','--ANY--','�%�3��Jt�o�+����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2358,'AIR','../Projects/Delaware/Help/help_esn.html','ESN','--ANY--','--ANY--','�G��;K�����C\n','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3715,'AIR','Acceso Guiado no está activado. Por favor notifique a su administrador. (Antes de activar Acceso Guiado, compruebe el volumen de su iPad para asegurarse de que se puede escuchar el audio.)','ESN','--ANY--','--ANY--','�L����@E���*�x�','2014-03-22 20:36:39.987');
INSERT INTO `client_messagetranslation` VALUES (3004,'AIR','Ha seleccionado la prueba incorrecta.','ESN','--ANY--','--ANY--','�U��qE��\"��ӳ','2014-03-22 20:35:11.280');
INSERT INTO `client_messagetranslation` VALUES (2747,'AIR','San-Serif (Arial)','ESN','--ANY--','--ANY--','�����K�L]o	s~*','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3335,'AIR','Makemake a nei ?oe e ho?opau ?ia keia memo nei?','HAW','--ANY--','--ANY--','��|���J����*��V5','2013-12-18 14:09:07.143');
INSERT INTO `client_messagetranslation` VALUES (2624,'SBAC','Seleccione 2 puntos para conectar con una flecha.','ESN','--ANY--','--ANY--','��S�NطG��ƹ��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2570,'AIR','¿Está seguro de que desea poner la prueba en pausa?','ESN','--ANY--','--ANY--','��Kj��M���唱��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3464,'AIR','Ho?ano hou','HAW','--ANY--','--ANY--','�.�X��A���\"�B`ӱ','2013-12-18 14:09:07.283');
INSERT INTO `client_messagetranslation` VALUES (2513,'AIR',' <p>No kekahi mau nīnau hōʻike java, pono ka haumāna e kahakiʻi a i ʻole e hoʻokō i kekahi hana ʻē aʻe. <a href=\"https://hsapt.tds.airast.org/grid/DemoTraining.aspx\">E kaomi i ʻaneʻi no nā laʻana o kēia ʻano nīnau.</a>.</p><p> Loaʻa ke kiaʻi ʻenehana no ka hōʻike haumāna punaewele a me ka hoʻokuene ʻikepili HSA i mea e hōʻole ai i ke komo ʻana mai o nā polokalamu hewa ma ka wā o ka hōʻike. <a </a>.</p> href=\"ProcessListTest3.html\">E kaomi i ʻaneʻi no ka hoʻāʻo ʻana i ka Hōʻikeʻike Polokalamu Hewa.','HAW','--ANY--','--ANY--','�/�-{MG��1`Z7�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3197,'AIR','<span class=\"short\">?A?ohe haumana e kali nei i ka ?apono ?ia.</span>','HAW','--ANY--','--ANY--','�8�\\��C&��F,W��','2013-12-18 14:09:06.987');
INSERT INTO `client_messagetranslation` VALUES (2563,'AIR','E \'olu\'olu, e kali, ke ho\'ouka \'ia nei...','HAW','--ANY--','--ANY--','�@;V)KɃ�}BR$o','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3071,'AIR','Ua kahamaha ‘ia kāu hō’ike. E ‘olu’olu, e ‘ōlelo pū me kāu Kumu Hō\'ike no ka ho’omau ‘ana i  kāu hō’ike.','HAW','--ANY--','--ANY--','�H�kYN\0��\\��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2457,'AIR','E kaomi i \'ane\'i e ho\'oki.','HAW','--ANY--','--ANY--','�X3�C�AB���J�f�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2380,'AIR','Pono \'oe e koho i kekahi mana\'o e pane ma mua o ka holo mua \'ana i ka \'ao\'ao \'e a\'e.','HAW','--ANY--','--ANY--','�e�p|�IM��r_��]�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2544,'AIR','Cerrar sesión','ESN','--ANY--','--ANY--','�h��?�Iu�\0��\n���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2409,'AIR','<-- Pono ʻoe e koho i kekahi papa','HAW','--ANY--','--ANY--','�x���/Of�<*X�Ĵ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2957,'AIR','Área de texto','ESN','--ANY--','--ANY--','�x�`0H����ʄ#�','2014-03-22 20:34:47.197');
INSERT INTO `client_messagetranslation` VALUES (3003,'AIR','Koho i ka ho?ike mai ka papa kuhikuhi holo-lalo mai.','HAW','--ANY--','--ANY--','��&8@ؓ3 ̍�H','2013-12-18 14:09:06.830');
INSERT INTO `client_messagetranslation` VALUES (3626,'AIR','SIRVE','HAW','--ANY--','--ANY--','��o	2Hԝ&T%GN','2013-12-18 14:09:07.513');
INSERT INTO `client_messagetranslation` VALUES (3093,'AIR','No existe la sesión: {0}','ESN','--ANY--','--ANY--','�����O���h�i�','2014-03-22 20:35:48.640');
INSERT INTO `client_messagetranslation` VALUES (2894,'AIR','Hacer clic para expandir','ESN','--ANY--','--ANY--','��VۧB�����s�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2792,'AIR','E pani i ka mana?o','HAW','--ANY--','--ANY--','���5�@��B���','2013-12-18 14:09:06.663');
INSERT INTO `client_messagetranslation` VALUES (3108,'AIR','La oportunidad esta en uso.','ESN','--ANY--','--ANY--','�3��Mo�y:<NL��','2014-03-22 20:35:48.650');
INSERT INTO `client_messagetranslation` VALUES (2521,'AIR','Éste es su puntaje:','ESN','--ANY--','--ANY--','�Hv7�nIʧua��Iv�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2650,'AIR','Cerrar sesión','ESN','--ANY--','--ANY--','�T9��C^��v?!�g','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2526,'AIR','Haga clic aquí para enviar información útil.','ESN','--ANY--','--ANY--','�T�5ceB�3�+�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3561,'AIR','Koho','HAW','--ANY--','--ANY--','�X��4�B-�l��y\r�','2013-12-18 14:09:07.430');
INSERT INTO `client_messagetranslation` VALUES (2831,'AIR','Someter un comentario para este ítem.','ESN','--ANY--','--ANY--','�Zqڵ�Gs�>a#�F_=','2014-03-22 20:34:06.387');
INSERT INTO `client_messagetranslation` VALUES (3617,'AIR','Por favor espere a que la simulación se termine antes de continuar.','ESN','--ANY--','--ANY--','�h\nv{}OY�~(L�R�','2014-03-22 20:36:39.500');
INSERT INTO `client_messagetranslation` VALUES (2503,'AIR','¿La prueba que aparece arriba es la prueba que desea realizar? Si es así, haga clic en [Sí, comenzar la prueba], de lo contrario, haga clic en [No].</p> ','ESN','--ANY--','--ANY--','�}�}�HC�l�8����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2463,'AIR','<span>No</span>','ESN','--ANY--','--ANY--','�9(�\"C���&�+��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3386,'AIR','<h3>?Anu?u 1:E koho i ka palapala ho?ike kahua no ke kukulu ?ana</h3>','HAW','--ANY--','--ANY--','�C���NK��j��uƃ','2013-12-18 14:09:07.170');
INSERT INTO `client_messagetranslation` VALUES (2963,'AIR','A204','HAW','--ANY--','--ANY--','�F^}S[HՋҭ���:�','2013-12-18 14:09:06.787');
INSERT INTO `client_messagetranslation` VALUES (3463,'AIR','?Olelo huna:','HAW','--ANY--','--ANY--','�S��,�Do�������','2013-12-18 14:09:07.283');
INSERT INTO `client_messagetranslation` VALUES (3713,'AIR','Ocurrió un error al iniciar la sesión. Por favor, inténtelo de nuevo o consulte con su Administrador de Pruebas.','ESN','--ANY--','--ANY--','���Yq�J����n�h�','2014-03-22 20:36:39.970');
INSERT INTO `client_messagetranslation` VALUES (3066,'AIR','Error al insertar el registro de la base de datos para los nuevos artículos de la prueba','ESN','--ANY--','--ANY--','��H���@���b�2','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3461,'AIR','?Olu?olu e ?e?e','HAW','--ANY--','--ANY--','����h�K&�&�z�T��','2013-12-18 14:09:07.280');
INSERT INTO `client_messagetranslation` VALUES (3507,'AIR','Kainoa TDS','HAW','--ANY--','--ANY--','��m�_~GH�,*7��','2013-12-18 14:09:07.350');
INSERT INTO `client_messagetranslation` VALUES (3002,'AIR','Someter y cerrar','ESN','--ANY--','--ANY--','���X�@���E���','2014-03-22 20:34:47.200');
INSERT INTO `client_messagetranslation` VALUES (3615,'AIR','?Olu?olu e kali a pau ka holo ?ana o ka ho?oku kohukohu ma mua o ka malama ?ana i na pane.','HAW','--ANY--','--ANY--','�ڏ�2dMQ��}|��5','2013-12-18 14:09:07.497');
INSERT INTO `client_messagetranslation` VALUES (2498,'AIR','<p>Bienvenido a AIR en línea!</p><ol type=\"1\"><li>Introducir su nombre, SSID e ID de la sesión en los campos anteriores. El administrador de la prueba te entregará el ID de la sesión.</li><li>Haz clic en Iniciar sesión [Sign In] para continuar.</li></ol>','ESN','--ANY--','--ANY--','�\"�`��@Րְ+܁��','2014-03-22 20:33:38.113');
INSERT INTO `client_messagetranslation` VALUES (2502,'AIR','<p>Espere mientras el administrador de pruebs revisa la configuración para su prueba. Esto puede tardar algunos minutos...</p>','ESN','--ANY--','--ANY--','�=����Mڊ��<�\"e','2014-03-22 20:33:38.117');
INSERT INTO `client_messagetranslation` VALUES (3037,'AIR','Aia he pilikia me ka ‘ōnaehana. E ‘olu’olu e hā’awi i kēia helu iā Kumu','HAW','--ANY--','--ANY--','�D�=�L0��X�d�*','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2876,'AIR','Someter un comentario para este ítem.','ESN','--ANY--','--ANY--','�s���A�����\r','2014-03-22 20:34:28.073');
INSERT INTO `client_messagetranslation` VALUES (3491,'AIR','Ho?ano hou','HAW','--ANY--','--ANY--','���oEL�;��U�','2013-12-18 14:09:07.330');
INSERT INTO `client_messagetranslation` VALUES (2474,'AIR','E Pani I ka Polokalamu Kele Punaewele Pa\'a','HAW','--ANY--','--ANY--','����K��\n��Z�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2433,'AIR','Se necesita Java {0} o una versión superior para realizar esta prueba. Consulte a su administrador de pruebas.','ESN','--ANY--','--ANY--','���0�aF?�u8��I\"','2014-03-22 20:33:38.103');
INSERT INTO `client_messagetranslation` VALUES (2773,'AIR','\'A\'OLE POLOLEI','HAW','--ANY--','--ANY--','�#ށ�Ei�8_&�Xr�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2344,'AIR','../tools/formulas/2010/de_9_10_eoc_esn.html','ESN','--ANY--','--ANY--','�,�a��CμBJ�:V�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2727,'AIR','Blanco sobre azul marino','ESN','--ANY--','--ANY--','�kI��Fr�T>�E��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3101,'AIR','Palulu ?ia keia ho?ike e na kahu ha?awi ho?ike.?Olu?olu e noi i ke kokua o kau TA.','HAW','--ANY--','--ANY--','�lUUw�E	�߂�zl>','2013-12-18 14:09:06.913');
INSERT INTO `client_messagetranslation` VALUES (3549,'AIR','He mau noi hou','HAW','--ANY--','--ANY--','�lz��ME�<���m','2013-12-18 14:09:07.413');
INSERT INTO `client_messagetranslation` VALUES (2431,'AIR','E kaomi i \'ane\'i no ka ho\'i \'ana i ka papakaumaka kainoa.','HAW','--ANY--','--ANY--','�ndg�KEôi��*�b�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3427,'AIR','Kaomi i ka pihi [Ho?okomo hou i kekahi ?ikepili] no ka ho?omau ?aia i ka ho?okomo ?ana i na ?ikepili no kekahi haumana ?ea?e.Kaomi i ka pihi [Ho?okomo hou i kekahi ?ikepili no ka ho?omau] ?aia i ka ho?okomo ?ana i na ?ikepili no kekahi ho?ike ?ea?e no ka ','HAW','--ANY--','--ANY--','�sf]�J�m\'\0��X','2013-12-18 14:09:07.227');
INSERT INTO `client_messagetranslation` VALUES (2345,'AIR','../tools/formulas/2010/hi_4_math_hce.html','HAW','--ANY--','--ANY--','���G�I-����ڮ0�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2614,'AIR','Mai ʻoki leo.','HAW','--ANY--','--ANY--','����!�H��۩�ɷx�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3606,'SBAC','Tecnología aumentada','ESN','--ANY--','--ANY--','��7��M ��7���u','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2433,'AIR','Pono ka Java {0} a \'oi aku no ka hana \'ana i keia ho\'ike','HAW','--ANY--','--ANY--','�� �B��/B�?/','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3430,'AIR','You may now go back and review your entries, or click [Submit Test For Scoring] if you are finished with this test. You cannot change entries after you submit this test.','ESN','--ANY--','--ANY--','����A�^�R\Z0�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2983,'AIR','{silence}Koho A.{silence}','HAW','--ANY--','--ANY--','��/oBB0�L����&','2013-12-18 14:09:06.810');
INSERT INTO `client_messagetranslation` VALUES (3096,'AIR','No podemos iniciar su sesión. Por favor verifique su información en TIDE y vuelva a intentarlo.','ESN','--ANY--','--ANY--','�!5��SH�p�r%;�','2014-03-22 20:35:48.643');
INSERT INTO `client_messagetranslation` VALUES (3532,'AIR','Pani','HAW','--ANY--','--ANY--','�%q�VrL(��.�Ւ4k','2013-12-18 14:09:07.387');
INSERT INTO `client_messagetranslation` VALUES (3691,'AIR','Sesión desconocida','ESN','--ANY--','--ANY--','�:��oKN��5t�mUB','2014-03-22 20:36:39.937');
INSERT INTO `client_messagetranslation` VALUES (2989,'AIR','Se produjo un problema con el sistema se cerrará la sesión.','ESN','--ANY--','--ANY--','�<Hc�I]��2�@��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3545,'AIR','Ka noi','HAW','--ANY--','--ANY--','��/���C�o�M���','2013-12-18 14:09:07.407');
INSERT INTO `client_messagetranslation` VALUES (3718,'AIR','Kaomi i ka pihi (?)','HAW','--ANY--','--ANY--','��djĖJ���($�','2013-12-18 14:09:07.650');
INSERT INTO `client_messagetranslation` VALUES (3689,'AIR','?A?ohe URL no ka ukali','HAW','--ANY--','--ANY--','�0R*4A\'��]fz�','2013-12-18 14:09:07.607');
INSERT INTO `client_messagetranslation` VALUES (2684,'AIR','ʻAe','HAW','--ANY--','--ANY--','�^Oo�@j�Xe��k#�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2548,'AIR','Haga clic en el botón [Imprimir pasaje] para imprimir el pasaje.','ESN','--ANY--','--ANY--','�����I[�P�T�|��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2643,'AIR','Ua lohe anei ‘oe i ke kani ma ka ‘ōlelo Paniolo? E kaomi [‘Ae] ‘a i ‘ole [‘A‘ole]','HAW','--ANY--','--ANY--','�GZ�Fl�I���Ղ`','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3152,'AIR','No cumples con los requisitos para realizar esta prueba debido a una exención parental.','ESN','--ANY--','--ANY--','�c3~CM5�Uί���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2681,'AIR','Desmarcar el artículo de revisión','ESN','--ANY--','--ANY--','�t%��CԲ�m\\:�\rj','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2826,'AIR','Someter un comentario para este ítem.','ESN','--ANY--','--ANY--','�t�F.YD��zX0��`','2014-03-22 20:34:06.367');
INSERT INTO `client_messagetranslation` VALUES (2610,'AIR','Kū','HAW','--ANY--','--ANY--','�~g�e�Iw���7&�C�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3449,'AIR','Ho?okomo i na palapala ho?apono TA','HAW','--ANY--','--ANY--','����\"O���7g�,\\','2013-12-18 14:09:07.263');
INSERT INTO `client_messagetranslation` VALUES (2775,'AIR','FALSO','ESN','--ANY--','--ANY--','��^ȖuH����T�r�d','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2579,'AIR','Problema','ESN','--ANY--','--ANY--','��S�Fմx`V�F','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3637,'AIR','Esta página le permite comprobar el ancho de banda <strong>actual</ strong> de su red. Seleccione una prueba de la lista desplegable e ingrese el número máximo de alumnos que puedan participar a la vez, entonces haga clic en [Ejecutar diagnósticos de red].','ESN','--ANY--','--ANY--','��0QMA���c*tߊ','2014-03-22 20:36:39.603');
INSERT INTO `client_messagetranslation` VALUES (3544,'AIR','3.Na pilina no ka haumana','HAW','--ANY--','--ANY--','�>n��L]�GW)���','2013-12-18 14:09:07.407');
INSERT INTO `client_messagetranslation` VALUES (2515,'AIR','Volver a la pantalla de inicio de sesión.','ESN','--ANY--','--ANY--','�Y�@k�G���P-\r8�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2530,'AIR','E Ho\'olaula','HAW','--ANY--','--ANY--','�]��V�@Ē@���c�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3011,'AIR','Seleccionar','ESN','--ANY--','--ANY--','�cU�>�I{�X���Gp','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3201,'AIR','Koho i ka/na ho?ike i makemake ?ia ma keia wa ho?ohana. ','HAW','--ANY--','--ANY--','�z�\n�E��c��R� o','2013-12-18 14:09:06.990');
INSERT INTO `client_messagetranslation` VALUES (2381,'AIR','E \'olu\'olu, e kikokiko i kekahi mau \'olelo ma mua o ka malama \'ana.','HAW','--ANY--','--ANY--','�{!ʯAB��|���J','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3725,'AIR','Plug-in de grabadaora no instalado','ESN','--ANY--','--ANY--','���i��JT��x���g\r','2014-03-22 20:36:40.003');
INSERT INTO `client_messagetranslation` VALUES (2801,'AIR','Maka i ka ?ikemu pono no ka nana hou ?ana','HAW','--ANY--','--ANY--','��N��E�%M���','2013-12-18 14:09:06.680');
INSERT INTO `client_messagetranslation` VALUES (3428,'AIR','Actualmente no hay resultados para esta prueba.  Consulte con el administrador de la prueba.','ESN','--ANY--','--ANY--','����D��H\'tL��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3647,'AIR','Helu wahi IP:','HAW','--ANY--','--ANY--','��\"��@F�ɧ�|w�','2013-12-18 14:09:07.547');
INSERT INTO `client_messagetranslation` VALUES (2709,'AIR','Ka \'Olelo Hawai\'i','HAW','--ANY--','--ANY--','��r�\n@����B9G�U','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3079,'AIR','Hiki ?ole ke loa?a na ?ikepili ho?ike ma ke kahua RTS','HAW','--ANY--','--ANY--','���\Z�Hd�\\^�5���','2013-12-18 14:09:06.893');
INSERT INTO `client_messagetranslation` VALUES (3439,'AIR','Ho?omau i ka ho?okomo ?ana i na ?ike pili','HAW','--ANY--','--ANY--','�Y�&֮Dx�iXR�W\Z-','2013-12-18 14:09:07.247');
INSERT INTO `client_messagetranslation` VALUES (3675,'AIR','Nalowale na pono','HAW','--ANY--','--ANY--','�e�/� Jݟ\'�u�Y','2013-12-18 14:09:07.593');
INSERT INTO `client_messagetranslation` VALUES (2352,'AIR','../tools/formulas/2010/hi_11_science.html','HAW','--ANY--','--ANY--','���)`M��1g�^�\'','2013-12-18 14:09:06.623');
INSERT INTO `client_messagetranslation` VALUES (2572,'AIR','Na \'okuhi no ka hana \'ana ','HAW','--ANY--','--ANY--','���hC�����\"���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2780,'AIR','ʻIkamu &','HAW','--ANY--','--ANY--','��pqA)LX�]�Ju(]','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2971,'AIR','Seleccionar una opción de la lista para revisar.','ESN','--ANY--','--ANY--','��|�.�@$�&k�lH�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2437,'AIR','Haga clic en el icono de sonido para escuchar el sonido.','ESN','--ANY--','--ANY--','��&���N��w�����Z','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2914,'AIR','Espere. Recuperando los resultados de la prueba.','ESN','--ANY--','--ANY--','��>0�D`�Y\'�~HP','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2479,'AIR','Opción de audio ','ESN','--ANY--','--ANY--','��=��K礣�YRZթ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3406,'AIR','Ua hewa paha ka inoa mua a i ?ole ka helu SSID i ho?okomo ?ia.?Olu?olu e nana hou a laila e hoa?o hou.','HAW','--ANY--','--ANY--','�\rX��B �J�}�\r��','2013-12-18 14:09:07.190');
INSERT INTO `client_messagetranslation` VALUES (2766,'AIR','Maʻa Mau','HAW','--ANY--','--ANY--','�~���TG�����O�r','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3113,'AIR','Solo se permite el restablecimiento de oportunidades incompletas en este momento.','ESN','--ANY--','--ANY--','����7�B���De�Gh','2014-03-22 20:35:48.653');
INSERT INTO `client_messagetranslation` VALUES (3300,'AIR','Hewa ka minuke palena pau –hola (Ho?ohana i ke ?ano ho?ala MM 00-59)','HAW','--ANY--','--ANY--','��/�!D����}Z�|','2013-12-18 14:09:07.113');
INSERT INTO `client_messagetranslation` VALUES (3721,'AIR','Hana','HAW','--ANY--','--ANY--','�	���tLa��ŗ���','2013-12-18 14:09:07.653');
INSERT INTO `client_messagetranslation` VALUES (2777,'AIR','Instrucciones, Textos e Items','ESN','--ANY--','--ANY--','��OܜA��TI�H��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3164,'AIR','Se requiere una ID de sesión.','ESN','--ANY--','--ANY--','�0b	^�N<�T�B�\\5','2014-03-22 20:39:22.703');
INSERT INTO `client_messagetranslation` VALUES (3558,'AIR','?Apono','HAW','--ANY--','--ANY--','�w���wH�����O1�','2013-12-18 14:09:07.427');
INSERT INTO `client_messagetranslation` VALUES (2796,'AIR','E ho?omaka i ke kakau ma na mana?o i koho ?ia','HAW','--ANY--','--ANY--','�+~\\�J͏��9�','2013-12-18 14:09:06.670');
INSERT INTO `client_messagetranslation` VALUES (2938,'AIR','Loa?a ka ?aelike','HAW','--ANY--','--ANY--','��,�y�@��g5�X�c\n','2013-12-18 14:09:06.750');
INSERT INTO `client_messagetranslation` VALUES (3086,'AIR','No se encuentra la base de datos RTS','ESN','--ANY--','--ANY--','��9��I�m��3��*','2014-03-22 20:35:32.337');
INSERT INTO `client_messagetranslation` VALUES (3173,'SBAC','You are now leaving the Online Testing System. By clicking [Exit], your test session and all in-progress tests will be paused. You may click [Return] to remain logged into the Online Testing System and continue testing without any disruption.','ENU','--ANY--','--ANY--','��T��Ek�q����','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2585,'AIR','Sección oral (en inglés)','ESN','--ANY--','--ANY--','�ң�ׯKT��,�kd','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3442,'AIR','La prueba se ha finalizado y está lista para entregarse.','ESN','--ANY--','--ANY--','��3�I���ǿ��l�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2736,'AIR','HI 6 Makemakika','HAW','--ANY--','--ANY--','���3��EĠe�*_.T	','2013-12-18 14:09:06.643');
INSERT INTO `client_messagetranslation` VALUES (2664,'SBAC_PT','Student Training Test','ENU','--ANY--','--ANY--','�UZtF�{>o~�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2672,'SBAC','<p>Some advanced test questions require a student to draw a picture, or complete another type of open-ended task.  <a href=\"https://sbacpt.tds.airast.org/grid/default.aspx\">Click here for examples of these open-ended questions</a>.</p>    <p>The Online Testing System and the secure browser have security measures that prevent forbidden applications from being active during a test opportunity. <a href=\"ProcessListTest3.html\">Click here to try the Forbidden Applications Demonstration</a>.</p>','ENU','--ANY--','--ANY--','�d��T�I���i���V}','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3358,'AIR','Ua pilikia ke kukulu ?ia ?ana o kau noi.?Olu?olu a hoa?o hou.','HAW','--ANY--','--ANY--','�i]D�lC=����z�xW','2013-12-18 14:09:07.160');
INSERT INTO `client_messagetranslation` VALUES (2630,'AIR','E ho\'oku\'u i ke pihi \'iole no ka waiho \'ana i ka mea i kahi au e makemake ai.','HAW','--ANY--','--ANY--','��T��8@Ĺ$��]���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2507,'AIR','E \'olu\'olu e lolelole iho no ka \'ike \'e a\'e.','HAW','--ANY--','--ANY--','���;��N-�Kp�Zl{u','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2425,'AIR','Ka Inoa o Ka Ho\'ike:','HAW','--ANY--','--ANY--','��d�c�L����%ף','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2979,'AIR','{silence}Opción C.{silence}','ESN','--ANY--','--ANY--','��ڛdlC*�\n��ُ� ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2824,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','��3��@E�?I�Aka','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3731,'AIR','?A?ohe mea kohu no ka helu SSID haumana au i ho?okomo ai.','HAW','--ANY--','--ANY--','��m\\~A~��p�����','2013-12-18 14:09:07.670');
INSERT INTO `client_messagetranslation` VALUES (3703,'AIR','{silence}Koho D.{silence}','HAW','--ANY--','--ANY--','�,�-=I릅�}���','2013-12-18 14:09:07.627');
INSERT INTO `client_messagetranslation` VALUES (3424,'AIR','<p>Para iniciar sesión con una cuenta de estudiante (con Nombre / SSID):</p><ul><li>desactive la selección de la casilla de verificación “Usuario invitado” (ambos campos aparecerán vacíos)</li><li>Ingrese su nombre y SSID en esos campos.</li></ul><p>Para iniciar sesión como invitado (usuario anónimo):</p><ul><li>Marque la casilla de verificación \"Usuario invitado\" (ambos campos mostrarán automáticamente Invitado)</li><li>Haga clic en [Iniciar sesión] para iniciar sesión en la prueba de muestra como usuario invitado.</li></ul><p><em>¿Sesión de invitado?</em><br />En una sesión de invitado, no necesita la aprobación del administrador de la prueba, por lo que puede realizar la prueba con su propia configuración. Para realizar la prueba de muestra en una sesión con el administrador de la prueba, desactive la selección de la casilla de verificación “Sesión de invitado” e ingrese el id. de sesión en el campo antes de hacer clic en [Iniciar sesión].</p>','ESN','--ANY--','--ANY--','�#2ʺHi�\r�	+݁','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3001,'AIR','Pruebas:','ESN','--ANY--','--ANY--','�hT�Q&I������4#�','2014-03-22 20:34:47.200');
INSERT INTO `client_messagetranslation` VALUES (2720,'AIR','ʻĀhinahina Ikaika','HAW','--ANY--','--ANY--','�w�}��K�n�B�@��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2669,'AIR','E Maka\'ala','HAW','--ANY--','--ANY--','�~���{F_�>��y}��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3487,'AIR','Na kuhikuhi<span class=\"notButton\"> – E komi e ho?omahola / ?opi</span>','HAW','--ANY--','--ANY--','���lMM3��D\ny�F','2013-12-18 14:09:07.323');
INSERT INTO `client_messagetranslation` VALUES (2556,'AIR','E Pani','HAW','--ANY--','--ANY--','���a;JIʬR�����0','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2343,'AIR','../tools/formulas/2010/de_6_8_esn.html','ESN','--ANY--','--ANY--','��pA�AE�:�\r���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2939,'AIR','Nemeth','HAW','--ANY--','--ANY--','��ؿ�E���G$`6`%','2013-12-18 14:09:06.753');
INSERT INTO `client_messagetranslation` VALUES (2758,'AIR','FALSO','ESN','--ANY--','--ANY--','�Ԉ�(uHN�W�0�j','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2951,'AIR','Nivel 1','ESN','--ANY--','--ANY--','�!�N-M[�Ť��o','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3317,'AIR','Ua pilikia kekahi mea.Ua ho?omaopopo ?ia ke kahu ha?awi ho?ike o keia kahua nei.','HAW','--ANY--','--ANY--','�#BT�AԼ�ɡ��hR','2013-12-18 14:09:07.130');
INSERT INTO `client_messagetranslation` VALUES (2402,'AIR','Ka Papa O Ka Haumana','HAW','--ANY--','--ANY--','�3;O��@��\0<��|','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3150,'AIR','A\'ole i loa\'a kekahi Helu Haumana e like me kēia. E \'olu\'olu, \'e ho\'ā\'o hou.','HAW','--ANY--','--ANY--','�4�;ȧFh�)��;��#','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2960,'AIR','Leer tal cual','ESN','--ANY--','--ANY--','�U,��CՂ\0���B','2014-03-22 20:34:47.200');
INSERT INTO `client_messagetranslation` VALUES (2497,'AIR','<div class=\"blurb\"><p>Bienvenido al sitio de la prueba de muestra. Puede usar este sitio para realizar pruebas de muestra en línea y familiarizarse con el DCAS. Algunas de las preguntas requerirán que seleccione una respuesta; otras pueden requerir que escriba una respuesta breve, haga un dibujo o complete otro tipo de tarea sin plazo definido.</p></div><div class=\"blurb\"><p>Tutorial interactivo: <a class=\"gridlink\" href=\"https://dept.tds.airast.org/grid/default.aspx\">Haga clic aquí para aprender cómo responder preguntas con respuestas gráficas.</a></p></div><div class=\"blurb\"><p><strong> Debe usar Mozilla Firefox como su navegador de Internet para acceder a la prueba de muestra.</strong> Internet Explorer y Safari no funcionarán en este sitio. <a target=\"_self\" href=\"http://www.mozilla.com/en-US/firefox/all-older.html\">Haga clic aquí para descargar Firefox para Windows, Mac OS X y Linux.</a></p></div>','ESN','--ANY--','--ANY--','��g*�K쿼�$�ov','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2498,'AIR','>Aloha mai! ʻO kēia ka Hōʻike HSA Pūnaewele! E hoʻokomo i kou inoa mua, kou helu haumāna, a me ka helu wā hana i nā puka o luna. E hāʻawi ka luna hōʻike iā ʻoe i ka helu wā hana. [Kāinoa] no ka hoʻomau ʻana.','HAW','--ANY--','--ANY--','��>��&H$���;�.ŷ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2547,'AIR','La prueba de muestra se está imprimiendo…','ESN','--ANY--','--ANY--','���_?�DӜ���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2387,'AIR','A\'ole i loa\'a keia hi\'ohi\'ona ma ka ho\'ike \'olelo Hawai\'i.','HAW','--ANY--','--ANY--','������NR���19��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2392,'AIR','ID de estudiante:','ESN','--ANY--','--ANY--','���_�C#��\\%�%��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2934,'AIR','Ho?ike punaewele puni honua ?ole','HAW','--ANY--','--ANY--','���<O��ڞ�;0[�','2013-12-18 14:09:06.743');
INSERT INTO `client_messagetranslation` VALUES (3075,'AIR','Identificación de prueba no válida','ESN','--ANY--','--ANY--','�%�4=@��=o���x','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2767,'AIR','Mea Maʻa Mau','HAW','--ANY--','--ANY--','�8����E��N���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2727,'AIR','Ke Ke\'oke\'o ma luna o ka Uliuli','HAW','--ANY--','--ANY--','�U��^�K��½��w�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3602,'AIR','¿Está seguro de que desea cerrar la sesión?','ESN','--ANY--','--ANY--','�l@�v�NR�0#���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2460,'AIR','Nombre de la prueba:','ESN','--ANY--','--ANY--','�s!���AB�\0=T�%�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2793,'AIR','E waiho i mana?o no keia ?ikamu.','HAW','--ANY--','--ANY--','���D���I�C��8','2013-12-18 14:09:06.667');
INSERT INTO `client_messagetranslation` VALUES (3210,'SBAC','<p>Enter the student’s full State-SSID and click [Submit State-SSID] to search for that student’s record. Searches by partial State-SSID are not permitted.</p><br/>  <!--<p>Note: This Student Lookup feature allows you to verify student information for login purposes only. It does not indicate whether a student is eligible to test. To verify student eligibility for a specific online assessment, please check TIDE.</p> -->','ENU','--ANY--','--ANY--','���d�cG�ɱ̅O߮','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2591,'AIR','E ʻōlelo i ke koho A','HAW','--ANY--','--ANY--','���~*�J[���=���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3126,'AIR','?A?ole loa?a ka manawa kupono no keia ho?ike nei.','HAW','--ANY--','--ANY--','��2�.Mc��r�ύ�','2013-12-18 14:09:06.950');
INSERT INTO `client_messagetranslation` VALUES (3576,'AIR','Ki?eki?e o ke kani','HAW','--ANY--','--ANY--','�����A��h\'�Ik�','2013-12-18 14:09:07.460');
INSERT INTO `client_messagetranslation` VALUES (3202,'AIR','Kaomi i ka pihi [Na ?apono] no ka nana hou ?ana i na ho?onohonoho ho?ike no na haumana e kali nei a ?apono ?ia.','HAW','--ANY--','--ANY--','��)�2J�}��X~�','2013-12-18 14:09:06.990');
INSERT INTO `client_messagetranslation` VALUES (3559,'AIR','Mea nui!','HAW','--ANY--','--ANY--','���P	K����s���','2013-12-18 14:09:07.430');
INSERT INTO `client_messagetranslation` VALUES (3503,'AIR','Ho?ike','HAW','--ANY--','--ANY--','�\n���MN�.d�sY��','2013-12-18 14:09:07.347');
INSERT INTO `client_messagetranslation` VALUES (2481,'AIR','Mea Hoʻokani Hale','HAW','--ANY--','--ANY--','�T�%�:H����w��0�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3088,'AIR','Pau kēia wā hana hōʻike, e ʻoluʻolu, e ʻōlelo me kāu luna hōʻike.','HAW','--ANY--','--ANY--','�Y���QJ3���GR�s','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2506,'AIR','<p class=\"selectTest\"> </p>','HAW','--ANY--','--ANY--','�y�➊A���f��a�=','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2894,'AIR','E kaomi no ka ho\'onui \'ana','HAW','--ANY--','--ANY--','��n��=Ck�}��Z4','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3137,'AIR','Aia he pilikia me ka ‘ōnaehana. E ‘olu’olu e hā’awi i kēia helu iā Kumu','HAW','--ANY--','--ANY--','���Y�I��^�̊�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2410,'AIR','Pruebas','ESN','--ANY--','--ANY--','��o3�Cz�����M','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3415,'AIR','Nombre:','ESN','--ANY--','--ANY--','���1N���0gQ�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2932,'AIR','Paniki (Emboss)','HAW','--ANY--','--ANY--','�<9O��f\"L�','2013-12-18 14:09:06.740');
INSERT INTO `client_messagetranslation` VALUES (2640,'AIR','ʻAʻole au i lohe i ka leo','HAW','--ANY--','--ANY--','�i����D*���OɁ�2','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2623,'AIR','Seleccione 2 puntos para conectar o presione y arrastre para crear y conectar puntos.','ESN','--ANY--','--ANY--','��A�i^N��I���k�<','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2559,'AIR','Guía de ayuda','ESN','--ANY--','--ANY--','���B�FɆ#��e\'T','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3083,'AIR','Hiki ?ole ke komo ka mea ho?ohana ma muli o kona kulala','HAW','--ANY--','--ANY--','�7���D���ʋ�i�','2013-12-18 14:09:06.900');
INSERT INTO `client_messagetranslation` VALUES (2913,'AIR','Cargando el contenido de la página.','ESN','--ANY--','--ANY--','�n��$Bğ�#ZK�Z','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2407,'AIR','<span>Sí</span>','ESN','--ANY--','--ANY--','����O�ήhv��/','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3146,'AIR','A\'ole hiki iā \'oe ke hana i kēia hō\'ike a hiki i ka lā  {0}.','HAW','--ANY--','--ANY--','�,6��	EY�^F��]�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2715,'AIR','Ka Mea \'Epekema','HAW','--ANY--','--ANY--','�D*��A5�)$a�?�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3659,'AIR','Versión de Flash:','ESN','--ANY--','--ANY--','�Q>^�GG��e6ˡ','2014-03-22 20:36:39.770');
INSERT INTO `client_messagetranslation` VALUES (2757,'AIR','POLOLEI','HAW','--ANY--','--ANY--','�i����J��Y1���\n','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2473,'AIR','<span>E Ho\'omaka I Ka Hō\'ike I Kēia Manawa</span>','HAW','--ANY--','--ANY--','�p����L�1�h��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3486,'AIR','Ho?ike','HAW','--ANY--','--ANY--','��c��kJR������','2013-12-18 14:09:07.320');
INSERT INTO `client_messagetranslation` VALUES (2781,'AIR','Artículos','ESN','--ANY--','--ANY--','��ƣ�I9���B1�@','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3708,'AIR','{silence}OpciÃ³n C.{silence}','HAW','--ANY--','--ANY--','�����GΙ�W�9','2013-12-18 14:09:07.633');
INSERT INTO `client_messagetranslation` VALUES (3239,'AIR','Na kuhikuhi no ka ho?ike: ','HAW','--ANY--','--ANY--','���\"[POp��d.\'���','2013-12-18 14:09:07.027');
INSERT INTO `client_messagetranslation` VALUES (3576,'AIR','Tono','ESN','--ANY--','--ANY--','��7�\Z�J���_|���','2014-03-22 20:39:31.233');
INSERT INTO `client_messagetranslation` VALUES (3260,'AIR','Ho?ole ?ia e ka makua','HAW','--ANY--','--ANY--','���-F\\�䔜bM��','2013-12-18 14:09:07.053');
INSERT INTO `client_messagetranslation` VALUES (3395,'AIR','?Olu?olu e ho?okomo i kau inoa ho?ohana a me ka ?olelo huna no ka ?e?e ?ana ma ke kahua SIRVE.Ina makemake ?oe e komo ma keia kahua nei, pono e loa?a ia?oe ke kuleana no ke komo ?ana.','HAW','--ANY--','--ANY--','�a��_L�t��5��','2013-12-18 14:09:07.180');
INSERT INTO `client_messagetranslation` VALUES (2934,'AIR','Probador desconectado','ESN','--ANY--','--ANY--','�}��SIՊ����[S�','2014-03-22 20:34:28.147');
INSERT INTO `client_messagetranslation` VALUES (3089,'AIR','La sesión no está disponible. Consulta con su administrador de pruebas.','ESN','--ANY--','--ANY--','�9�-=MJɷ��u���','2014-03-22 20:33:38.147');
INSERT INTO `client_messagetranslation` VALUES (2358,'SBAC','../Projects/SBAC/Help/help_esn.html','ESN','--ANY--','--ANY--','�a���J�ze�b�4','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2777,'AIR','ʻŌkuhi Stimuli a me nā ʻIkamu','HAW','--ANY--','--ANY--','�x���H��~[ء��k','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2799,'AIR','E pa?i i ka ?ikemu','HAW','--ANY--','--ANY--','���P�H��%��({�','2013-12-18 14:09:06.677');
INSERT INTO `client_messagetranslation` VALUES (2606,'AIR','E ʻŌlelo I Nā Kiʻi (Ma Ka Namu Haole)','HAW','--ANY--','--ANY--','��ҩ�\nJ\0�r���{]c','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2487,'AIR','E ʻōlelo mai','HAW','--ANY--','--ANY--','��q~e�GA����O�JZ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2402,'AIR','Grado suscripto:','ESN','--ANY--','--ANY--','���qA�K��~I��Վ	','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3643,'AIR','Mana:','HAW','--ANY--','--ANY--','��5��E���V���','2013-12-18 14:09:07.540');
INSERT INTO `client_messagetranslation` VALUES (2972,'AIR','¿Estás seguro de que quieres enviar la prueba?','ESN','--ANY--','--ANY--','�ڞ�x@����F�cga','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2645,'SBAC','<span style=\"display:none; lang=\"ENU\">Spanish text to be spoken</span>','ENU','--ANY--','--ANY--','�\")RVC���z3�@�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3336,'SBAC','PT','ENU','--ANY--','--ANY--','�KiR$EF����Ԓ�,','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2523,'AIR','<span>E Nānā Hou I Ka\'u Hō\'ike</span>','HAW','--ANY--','--ANY--','�f6r\r�O}�\0�mί�]','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3126,'AIR','La oportunidad de prueba no existe.','ESN','--ANY--','--ANY--','��D���Af�ɫ9m��','2014-03-22 20:37:20.647');
INSERT INTO `client_messagetranslation` VALUES (3249,'AIR','No na makapo (Braille)','HAW','--ANY--','--ANY--','��L��3B��4�Ձ�','2013-12-18 14:09:07.037');
INSERT INTO `client_messagetranslation` VALUES (2526,'AIR','E kaomi i \'ane\'i no ka ho\'ouna \'ana i ka \'ike kokua.','HAW','--ANY--','--ANY--','�����N�����=�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2678,'AIR','E kaomi i \'ane\'i no ka pa\'i \'ana i ka mo\'olelo.','HAW','--ANY--','--ANY--','�	\ZڌDAO��8��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2649,'AIR','Haga clic en [Reintentar]. Asegúrese de que el sonido de su ordenador no esté en silencio y tratar de ajustar el volumen y el tono. Si usted ha hecho esto, y aún así no escucha el audio, por favor consulte a su administrador de pruebas. No cerrar la sesión a menos que su administrador le indica que lo haga.','ESN','--ANY--','--ANY--','��,z#H1�a���Z��','2014-03-22 20:33:38.130');
INSERT INTO `client_messagetranslation` VALUES (3672,'AIR','../tools/formulas/2010/air_3_5_math.html','HAW','--ANY--','--ANY--','���K|�:P�)�','2013-12-18 14:09:07.587');
INSERT INTO `client_messagetranslation` VALUES (3720,'AIR','Wanana ho?okomo ?ikepili','HAW','--ANY--','--ANY--','�]UZO�h���ʸ^','2013-12-18 14:09:07.653');
INSERT INTO `client_messagetranslation` VALUES (2614,'AIR','Detener grabación','ESN','--ANY--','--ANY--','�u�^aCܪ�k��d�v','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3394,'AIR','Ho?okomo i na palapala ho?apono TA','HAW','--ANY--','--ANY--','�z�+PH���0�(�p','2013-12-18 14:09:07.177');
INSERT INTO `client_messagetranslation` VALUES (2586,'AIR','Diga las preguntas y las opciones','ESN','--ANY--','--ANY--','��q��Kz�������','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3012,'AIR','../tools/formulas/2010/de_eoc2.html','HAW','--ANY--','--ANY--','��)��Fl��R�ً0','2013-12-18 14:09:06.833');
INSERT INTO `client_messagetranslation` VALUES (3252,'AIR','Ka nui o na hua palapala komo','HAW','--ANY--','--ANY--','�Œ?��D[��#D\r���','2013-12-18 14:09:07.040');
INSERT INTO `client_messagetranslation` VALUES (2497,'SBAC','<div class=\"blurb\"><a target=\"_self\" href=\"https://login1.cloud1.tds.airast.org/default.aspx\">Click here if you are a California student</a></p></div>','ENU','--ANY--','--ANY--','�ݕ㜔Di����v}�,','2014-03-20 23:02:10.000');
INSERT INTO `client_messagetranslation` VALUES (2965,'AIR','../tools/formulas/2010/mn_5_math.html','HAW','--ANY--','--ANY--','��P�J���C6e6�','2013-12-18 14:09:06.790');
INSERT INTO `client_messagetranslation` VALUES (3385,'AIR','<p>Hiki ?ia?oe ke ho?ohana i keia ?ao?ao no ka loa?a ?ana o na ho?ike kahua like ?ole.Loa?a keia mau mea:</p><ul><li>Na palapala ho?ike helu mea ho?ohana. Hiki ke nana i ka nui o na TA a me no ka nui o na haumana ma ka wa ho?ohana ma na halo pakahi a me na la pakahi.</li><li>Na palapala ho?ike no na lohi ma ka ?ao?ao o ke kahua ha?awi ho?ike a me ka ?ao?ao o ka mea ho?ohana. Nana na ?ikepili lohi no na kahua ha?awi ho?ike a me na mea ho?ohana (?a?ao o ka mea ho?ohana).</li><li>Palapala Ho?ike Ki?i kukulu – Nana i na ha?awina i makemake ?ia e pili ana i na kahu ha?awi ho?ike pakahi.</li><li>Na ho?ike ho?omau ?ano?e: E nana he aha ke ?ano o na polokalamu punaewele puni honua a me na polokalamu kahua i ho?ohana ?ia i ka manawa i ka inoa ?ano ?e ?ia ai.</li></ul>','HAW','--ANY--','--ANY--','��(VEE�[˷�ц','2013-12-18 14:09:07.170');
INSERT INTO `client_messagetranslation` VALUES (2863,'AIR','E pani i ka mana?o','HAW','--ANY--','--ANY--','�+�O&F���H��ȴ','2013-12-18 14:09:06.723');
INSERT INTO `client_messagetranslation` VALUES (2752,'AIR','POLOLEI','HAW','--ANY--','--ANY--','�B\\�xB��\'Q%�Z�.','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3536,'AIR','Inoa mua','HAW','--ANY--','--ANY--','�W���I��p^�oQ�%','2013-12-18 14:09:07.393');
INSERT INTO `client_messagetranslation` VALUES (2382,'AIR','¿Está seguro de que desea pausar la prueba? Consulte al administrador de la prueba antes de hacerlo.','ESN','--ANY--','--ANY--','�]Odv4E�w��e��:','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2608,'AIR','Pregunta de audio','ESN','--ANY--','--ANY--','�x�CQ(J���G�q�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2760,'AIR','Artículo','ESN','--ANY--','--ANY--','����N߭Tbz���N','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2960,'AIR','Heluhelu ?ano','HAW','--ANY--','--ANY--','��Uy�iK����M��','2013-12-18 14:09:06.780');
INSERT INTO `client_messagetranslation` VALUES (2728,'AIR','Ka Melemele','HAW','--ANY--','--ANY--','��wT\nR@h�]J���\\','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2641,'AIR','E lele i ke kani ma ka ‘ōlelo Pelekane. ','HAW','--ANY--','--ANY--','��?���O����b-','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3135,'AIR','Aia he pilikia me ka ‘ōnaehana. E ‘olu’olu, e hana hou.','HAW','--ANY--','--ANY--','����}@NŶ�\"L�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2945,'AIR','Por demanda','ESN','--ANY--','--ANY--','��n�]YE�����_J','2014-03-22 20:34:47.190');
INSERT INTO `client_messagetranslation` VALUES (2661,'AIR','E ho\'oholo i ka polokalamu ho\'oponopono','HAW','--ANY--','--ANY--','����I����Lhj�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2750,'AIR','VERDADERO','ESN','--ANY--','--ANY--','�/(!yI�J����)','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3706,'SBAC','{silence} A.{silence}','ENU','--ANY--','--ANY--','��׻�FF�`��6Dm','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2589,'AIR','E ʻōlelo i ka nīnau','HAW','--ANY--','--ANY--','�Q�CF�����`�#','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2929,'AIR','Aia he pilikia me ka ‘ōnaehana.','HAW','--ANY--','--ANY--','���M�C��,��@#�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3284,'SBAC_PT','You are now leaving the TA Interface Training Site. By clicking [Exit], your test session and all in-progress tests will be paused. You may click [Return] to remain on the TA Interface Training Site to continue without any test disruption.','ENU','--ANY--','--ANY--','��Z�=�AO��%km�a�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3556,'AIR','Kuhikuhi ma?a mau','HAW','--ANY--','--ANY--','��s�]Jr���\\�C�','2013-12-18 14:09:07.423');
INSERT INTO `client_messagetranslation` VALUES (3547,'AIR','Noi no ka pa?i ?ana no ka haumana:','HAW','--ANY--','--ANY--','��]�tCN횰�l�\\','2013-12-18 14:09:07.410');
INSERT INTO `client_messagetranslation` VALUES (2377,'AIR','‘A‘ole i mālama ‘ia kāu pane. E kaomi i ka [‘Ae] no ka hana hou ‘ana a i ‘ole [‘A‘ole] no ka lele ‘ana i kāu hō‘ike.','HAW','--ANY--','--ANY--','�ѵ���G-��9?}gK6','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2659,'AIR','E ho\'omaka i ka ho\'ike','HAW','--ANY--','--ANY--','���y�6H�����:2M','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2655,'AIR','Borrar','ESN','--ANY--','--ANY--','�U��)E4��Q_��8','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3078,'AIR','No se encuentra la base de datos RTS','ESN','--ANY--','--ANY--','�l��Y�Bݏ�9�l','2014-03-22 20:35:32.330');
INSERT INTO `client_messagetranslation` VALUES (2692,'AIR','Ka Nui o ke Kinona','HAW','--ANY--','--ANY--','��A���F���>Z��o�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2568,'AIR','Resaltar selección','ESN','--ANY--','--ANY--','��zPf�E��T\"j���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2923,'AIR','La función texto a voz no está disponible.','ESN','--ANY--','--ANY--','���RH��b]M_ہX','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3474,'AIR','Ho?opau i ka wa ho?ohana','HAW','--ANY--','--ANY--','���7�AAͲ�WE�-Y','2013-12-18 14:09:07.303');
INSERT INTO `client_messagetranslation` VALUES (2887,'AIR','E Ho\'opio I Ka Mea Hana Kikokikona ','HAW','--ANY--','--ANY--','��n\r�GĚ`��R,q','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2806,'AIR','Someter un comentario para este ítem.','ESN','--ANY--','--ANY--','�ʂJ�C��Hzz��R','2014-03-22 20:33:38.087');
INSERT INTO `client_messagetranslation` VALUES (2718,'AIR','Uliuli','HAW','--ANY--','--ANY--','����I@���T_�=','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2491,'AIR','E Hele I Ka Mea Hana Ho\'oponopono','HAW','--ANY--','--ANY--','�7H(�D>��\Z��!�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3154,'SBAC','Your State-SSID is not entered correctly. Please try again or ask your TA.','ENU','--ANY--','--ANY--','�>����A����ˡm','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3068,'AIR','Intente sobrescribir el artículo existente','ESN','--ANY--','--ANY--','�u�V�M���8���W�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3077,'AIR','ʻAʻole hiki ke ʻimi a loaʻa ka waihona ʻike RTS','HAW','--ANY--','--ANY--','��&��YI���h���	v','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2385,'AIR','Debe responder todas las preguntas de esta página antes de terminar la prueba.','ESN','--ANY--','--ANY--','����a�E�h�h!&�O','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2717,'AIR','ʻAʻohe','HAW','--ANY--','--ANY--','����eOD����M��_','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3488,'AIR','Aia na haumana ma ka wa ho?ohana','HAW','--ANY--','--ANY--','�i�%�IƗ�#0ͥ�]','2013-12-18 14:09:07.323');
INSERT INTO `client_messagetranslation` VALUES (3151,'AIR','A\'ole i hana \'ia ka papa āu i kikokiko ai. E \'olu\'olu \'oe, \'e ho\'ā\'o hou. ','HAW','--ANY--','--ANY--','�2�|D׽�����ē','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2592,'AIR','E ʻŌlelo I Ke Koho A (Ma Ka Namu Haole)','HAW','--ANY--','--ANY--','�+\0?�ZB�����W�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2510,'SBAC','You have reached the end of the test.','ENU','--ANY--','--ANY--','��Y��K�ҫY�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2710,'AIR','POLOLEI ʻOLE','HAW','--ANY--','--ANY--','����VTIK��p+��y�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3501,'AIR','Pau','HAW','--ANY--','--ANY--','��ȶ��G���R8BC�','2013-12-18 14:09:07.343');
INSERT INTO `client_messagetranslation` VALUES (2636,'AIR','Escuchar','ESN','--ANY--','--ANY--','���3<mG���Y���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3414,'AIR','Usuario invitado','ESN','--ANY--','--ANY--','����-F��\"���K�(','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3168,'AIR','Loa?a na ?olea hou a me na ?olea i pau ?ole aku ka wa ho?ohana.','HAW','--ANY--','--ANY--','��Nw��CJ�2YU���','2013-12-18 14:09:06.977');
INSERT INTO `client_messagetranslation` VALUES (2917,'AIR','<span class=\"hasTTS\">Seleccione el texto que desea escuchar y haga clic en el botón verde para reproducirlo.</span>','ESN','--ANY--','--ANY--','����1B:��o���|','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3603,'AIR','Ho?okukohukohu','HAW','--ANY--','--ANY--','��u�K����D�!`�','2013-12-18 14:09:07.480');
INSERT INTO `client_messagetranslation` VALUES (3275,'AIR','Mana?o:Hiki ?ole ke ho?ololi ?ia na ho?onohonoho ho?ike ma hope o ka ?apono ?ia ?ana o ka haumana.','HAW','--ANY--','--ANY--','��z)Cқ��}s0�','2013-12-18 14:09:07.080');
INSERT INTO `client_messagetranslation` VALUES (2845,'AIR','Imprimir item','ESN','--ANY--','--ANY--','�:\0���F��B\'	5��:','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3299,'AIR','Hewa ka manawa palena pau –hola (Ho?ohana i ke ?ano ho?ala HH; 01-12)','HAW','--ANY--','--ANY--','�X�I-�È��|Y','2013-12-18 14:09:07.110');
INSERT INTO `client_messagetranslation` VALUES (2708,'AIR','Paniolo','HAW','--ANY--','--ANY--','�}�g-J$��z���9�','2013-12-18 14:09:06.633');
INSERT INTO `client_messagetranslation` VALUES (3020,'AIR','Ma muli o ke ?ano o ka ho?oili ?ana o kau kahua kamepiula, ua hiki ?ole ia?oe ke ha?awi aku i keia ho?ike i ka nui haumana au i makemake ai ma keia wahi nei.E loa?a kekahi pilikia me kekahi o na haumana i ka wa a lakou e lawe nei i keia ho?ike nei.?Olu?olu e ho?emi mai i na helu haumana a kokoke i ka {0}.','HAW','--ANY--','--ANY--','���I(x@Т�\'\n)�g\r','2013-12-18 14:09:06.847');
INSERT INTO `client_messagetranslation` VALUES (2935,'AIR','Suprimir puntuación','ESN','--ANY--','--ANY--','��1���I}��Lu�\'�','2014-03-22 20:34:28.157');
INSERT INTO `client_messagetranslation` VALUES (3055,'AIR','El usuario tiene una relación de rol incorrecta para ver los informes','ESN','--ANY--','--ANY--','�����OÏd�[�I�1','2014-03-22 20:35:32.323');
INSERT INTO `client_messagetranslation` VALUES (3652,'AIR','Seleccione una prueba','ESN','--ANY--','--ANY--','�/��xJ5�Z�1��u','2014-03-22 20:36:39.710');
INSERT INTO `client_messagetranslation` VALUES (2625,'AIR','E koho i 2 kiko no ka ho\'ohui \'ana me ka nahau palua.','HAW','--ANY--','--ANY--','�S���H²����Z�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3159,'AIR','El sistema no pudo guardar su respuesta. Por favor seleccione su respuesta de nuevo. Si es una pregunta de tecnología ampliada, es posible que tenga que hacer clic en el botón [Guardar] de nuevo.','ESN','--ANY--','--ANY--','�]jK��E+�-��','2014-03-22 20:37:20.657');
INSERT INTO `client_messagetranslation` VALUES (3701,'AIR','{silence}Koho B.{silence}','HAW','--ANY--','--ANY--','�~x�RG���`\\A���','2013-12-18 14:09:07.623');
INSERT INTO `client_messagetranslation` VALUES (3423,'AIR','<div class=\"blurb\"><p>Bienvenido al sitio de la prueba de muestra. Puede usar este sitio para realizar pruebas de muestra en línea y familiarizarse con el DCAS. Algunas de las preguntas requerirán que seleccione una respuesta; otras pueden requerir que escriba una respuesta breve, haga un dibujo o complete otro tipo de tarea sin plazo definido.</p></div><div class=\"blurb\"><p>Tutorial interactivo: <a class=\"gridlink\" href=\"https://dept.tds.airast.org/grid/default.aspx\">Haga clic aquí para aprender cómo responder preguntas con respuestas gráficas.</a></p></div><div class=\"blurb\"><p><strong> Debe usar Mozilla Firefox como su navegador de Internet para acceder a la prueba de muestra.</strong> Internet Explorer y Safari no funcionarán en este sitio. <a target=\"_self\" href=\"http://www.mozilla.com/en-US/firefox/all-older.html\">Haga clic aquí para descargar Firefox para Windows, Mac OS X y Linux.</a></p></div>','ESN','--ANY--','--ANY--','��U�Hی��x�l��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2572,'AIR','Tutorial','ESN','--ANY--','--ANY--','����C��wҼgGj','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2841,'AIR','Waiho i mana?o no keia ?ikamu pono no na ho?ike.','HAW','--ANY--','--ANY--','��=O���f9;`','2013-12-18 14:09:06.707');
INSERT INTO `client_messagetranslation` VALUES (3171,'SBAC','<h2>Smarter Balanced Assessments</h2>','ENU','--ANY--','--ANY--','�.:�_LA��g����','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3618,'AIR','Ya existe una sesión activa para este usuario.','ESN','--ANY--','--ANY--','�M��<�B(��t�\r�K','2014-03-22 20:36:39.507');
INSERT INTO `client_messagetranslation` VALUES (3363,'AIR','Koho i ke kula','HAW','--ANY--','--ANY--','���6�VOv�`B��<J\"','2013-12-18 14:09:07.167');
INSERT INTO `client_messagetranslation` VALUES (3430,'AIR','I keia manawa, hiki ia?oe ke ho?i hou a nana hou i kau i hana ai a i ?ole e kaomi i ka pihi [Ho?oku?u i ka ho?ike no ka helu ?ai].?A?ole hiki ia?oe ke ho?ololi i na koho ma hope o ka ho?oku?u ?ana i ka ho?ike.','HAW','--ANY--','--ANY--','�����|A��9�%xV','2013-12-18 14:09:07.230');
INSERT INTO `client_messagetranslation` VALUES (2997,'AIR','SSID','ESN','--ANY--','--ANY--','��9�TJݣf�@2��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3405,'SBAC','../Projects/SBAC/Help/help_dei.html','ENU','--ANY--','--ANY--','�&�FLjL��+�|Yu�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3597,'AIR','Utilice los controles deslizantes para ajustar el tono y el volumen. Usted no será capaz de cambiar esta configuración una vez que comience la prueba.','ESN','--ANY--','--ANY--','�8tYnGZ�&@�(E�','2014-03-22 20:36:39.393');
INSERT INTO `client_messagetranslation` VALUES (2525,'SBAC','Please wait while your test is being submitted.','ENU','--ANY--','--ANY--','��T� @��a!\Z�\"��','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2648,'AIR','Aia he pilikia me ke kani.','HAW','--ANY--','--ANY--','���{&DC֕�0t���u','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2730,'AIR','Amarillo sobre azul','ESN','--ANY--','--ANY--','���:Fn�����p ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3573,'AIR','?Ikemu no ka','HAW','--ANY--','--ANY--','��n��Og�~\Z}zg/','2013-12-18 14:09:07.450');
INSERT INTO `client_messagetranslation` VALUES (3057,'AIR','Hiki ?ole ke loa?a na ?ikepili ho?ike ma ke kahua RTS','HAW','--ANY--','--ANY--','��?�,�B8��%���','2013-12-18 14:09:06.880');
INSERT INTO `client_messagetranslation` VALUES (2573,'AIR','Problema','ESN','--ANY--','--ANY--','��X�SBӤ�e[�o]','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2888,'AIR','marcado','ESN','--ANY--','--ANY--','��c(1I���q�:�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2555,'AIR','Na Ha\'ilula','HAW','--ANY--','--ANY--','�\'���L����X>','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2527,'AIR','<span>Kōkua</span>','HAW','--ANY--','--ANY--','�(�V@M����=���H','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2579,'AIR','Pilikia','HAW','--ANY--','--ANY--','�GK�>Aذ�Ɛ�Ƶ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2892,'AIR','Ka Helu Mahele Ho\'ike:','HAW','--ANY--','--ANY--','�O)	�oC��Tc�<u�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2990,'AIR','Esperando la siguiente página.','ESN','--ANY--','--ANY--','�r�V��LU��{{�}*�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3356,'AIR','Koho i ka pae','HAW','--ANY--','--ANY--','���٢J<�.�$6','2013-12-18 14:09:07.160');
INSERT INTO `client_messagetranslation` VALUES (2594,'AIR','Opción oral B (en inglés)','ESN','--ANY--','--ANY--','������L����t��0','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2928,'AIR','A\'ole hiki ia \'oe ke hana i keia ho\'ike a hiki i ka la  {0}.','HAW','--ANY--','--ANY--','�����I�@�Cmn#','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2995,'AIR','Reproducir instrucciones','ESN','--ANY--','--ANY--','�	��G�G7;ܖ�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2757,'AIR','VERDADERO','ESN','--ANY--','--ANY--','��sLۖx�Zu�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3448,'AIR','Ha?alele','HAW','--ANY--','--ANY--','�S\Zy�{A����&Qt','2013-12-18 14:09:07.260');
INSERT INTO `client_messagetranslation` VALUES (2543,'AIR','No','ESN','--ANY--','--ANY--','�Uk,-bBN�l��В��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2761,'AIR','Ka Mo\'olelo a me Nā \'Ikamu','HAW','--ANY--','--ANY--','�mڔU�OZ�T�����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3296,'AIR','Hewa ka la palena pau (Ho?ohana i ka ?ano ho?ala DD; 10-31)','HAW','--ANY--','--ANY--','��ih��J�i�xI�','2013-12-18 14:09:07.107');
INSERT INTO `client_messagetranslation` VALUES (2573,'AIR','Pilikia','HAW','--ANY--','--ANY--','���D$�F�\0a���S','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2860,'AIR','Imprimir item','ESN','--ANY--','--ANY--','�˶1��Ah�f}*��.','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2823,'AIR','Cerrar Comentario','ESN','--ANY--','--ANY--','�Й�hbM��й�v�i�','2014-03-22 20:34:06.357');
INSERT INTO `client_messagetranslation` VALUES (3104,'AIR','ʻAʻole hiki ke hana ʻia','HAW','--ANY--','--ANY--','�f��PAj��,	�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2613,'AIR','Koho','HAW','--ANY--','--ANY--','��9L�K������','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3295,'AIR','Hewa ka manawa halihali –minuke (Ho?ohana i ke ?ano ho?ala MM; 00-59)','HAW','--ANY--','--ANY--','�03�EV��;y�g','2013-12-18 14:09:07.103');
INSERT INTO `client_messagetranslation` VALUES (2528,'AIR','Preguntas pasadas/marcadas:','ESN','--ANY--','--ANY--','�6:e`�G�����8�-','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2640,'AIR','No, no escuché la voz','ESN','--ANY--','--ANY--','�\\��/F@��]��F�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2695,'AIR','Ka Puke Alaka\'i no ka Ho\'oponopono \'Ana','HAW','--ANY--','--ANY--','�d�!�Fe����0��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2435,'AIR','Espere mientras se detecta si Java está instalado.','ESN','--ANY--','--ANY--','_*PI������\0\n','2014-03-22 20:33:38.107');
INSERT INTO `client_messagetranslation` VALUES (2923,'AIR','ʻAʻohe Kākau-i-ka-ʻŌlelo Wahe','HAW','--ANY--','--ANY--','���x6I\'�*�P���X','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3090,'AIR','Hoʻomaka ka wā hana hōʻike ma {0}. No ke kōkua, e ʻōlelo me kāu luna hōʻike.','HAW','--ANY--','--ANY--','����Wu@��*��,','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2582,'AIR','Para enviar información útil, describa el problema y haga clic en [Sí].','ESN','--ANY--','--ANY--','�\0���-B�ACK��&T','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3649,'AIR','Ka mana o kau polokalamu punaeweele puni honua:','HAW','--ANY--','--ANY--','�\n�>R�M%��2���','2013-12-18 14:09:07.550');
INSERT INTO `client_messagetranslation` VALUES (2388,'AIR','A\'ole i loa\'a keia hi\'ohi\'ona ma ka ho\'ike \'olelo Hawai\'i.','HAW','--ANY--','--ANY--','�?��ԧJӄ^~�n�D','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2836,'AIR','Waiho i na mana?o no keia ?ikemu pono.','HAW','--ANY--','--ANY--','�G��hDo�b=	M��t','2013-12-18 14:09:06.703');
INSERT INTO `client_messagetranslation` VALUES (2769,'AIR','HTML','ESN','--ANY--','--ANY--','��8��5I����&�n�r','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3722,'AIR','?Olelo huna','HAW','--ANY--','--ANY--','�֞�!~B����̷�','2013-12-18 14:09:07.657');
INSERT INTO `client_messagetranslation` VALUES (2458,'AIR','O Kau Ho\'ike anei Keia?','HAW','--ANY--','--ANY--','���2@Jq�\'_���O','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2424,'AIR','ID de sesión:','ESN','--ANY--','--ANY--','��g��E1�IBQUHRe','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3037,'AIR','Se produjo un problema con el sistema.  Proporcione este número al AP.','ESN','--ANY--','--ANY--','�q�S�BԮ�?L1�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3700,'SBAC','{silence} A.{silence}','ENU','--ANY--','--ANY--','�t��P�L>����N!��','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3273,'AIR','E ?Apono i keia haumana i hiki iaia ke ne?e i kekahi ?apana ?ea?e?','HAW','--ANY--','--ANY--','ıX�5Fh�ȅ#%��\r','2013-12-18 14:09:07.077');
INSERT INTO `client_messagetranslation` VALUES (2478,'AIR','E Pani','HAW','--ANY--','--ANY--','��s��A\'���!8=','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2821,'AIR','Someter un comentario para este ítem.','ESN','--ANY--','--ANY--','��}\\P�O���e�C','2014-03-22 20:34:06.353');
INSERT INTO `client_messagetranslation` VALUES (3113,'AIR','O na ho?ike i ho?opau ?ole ?ia na ho?ike wale no i ?ae ?ia no ka manawa kupono i ?apono ?ia i keia manawa','HAW','--ANY--','--ANY--','��^qgIM+�=����.','2013-12-18 14:09:06.930');
INSERT INTO `client_messagetranslation` VALUES (3691,'AIR','Maopopo ?ole ?ia keia wa ho?ohana','HAW','--ANY--','--ANY--','��^�M����#���','2013-12-18 14:09:07.610');
INSERT INTO `client_messagetranslation` VALUES (3635,'AIR','Paquete de voz actual','ESN','--ANY--','--ANY--','��h��K��P\0��\0�','2014-03-22 20:36:39.587');
INSERT INTO `client_messagetranslation` VALUES (2889,'AIR','Elegir adaptaciones:','ESN','--ANY--','--ANY--','�Jnj��JO��5���!','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3100,'AIR','Esta prueba no fue incluida en esta sesión.','ESN','--ANY--','--ANY--','�S@S�ULm�0R^B+Ty','2014-03-22 20:33:38.150');
INSERT INTO `client_messagetranslation` VALUES (3551,'AIR','Kumu no ka ho?ole ?ia ?ana:','HAW','--ANY--','--ANY--','�[\',��D��~ �٠�','2013-12-18 14:09:07.417');
INSERT INTO `client_messagetranslation` VALUES (3154,'AIR','ʻAʻole i loa’a kēia Helu Haumāna. E ʻoluʻolu, e hana hou ʻoe.','HAW','--ANY--','--ANY--','�a[zi|L7�䬻ؤ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3435,'AIR','?Olu?olu e kali, ke ho?omaka nei ka wa ho?ohana.','HAW','--ANY--','--ANY--','����rA_�!e뺉��','2013-12-18 14:09:07.240');
INSERT INTO `client_messagetranslation` VALUES (2873,'AIR','E pani i ka mana?o','HAW','--ANY--','--ANY--','���n@�&�k&)�D','2013-12-18 14:09:06.730');
INSERT INTO `client_messagetranslation` VALUES (3048,'AIR','?A?ohe kuleana o ka mea ho?ohana no ke komo ?ana','HAW','--ANY--','--ANY--','�퍃21@\\�����','2013-12-18 14:09:06.870');
INSERT INTO `client_messagetranslation` VALUES (2917,'AIR','<span class=\"hasTTS\">E koho i ke kākau ou e makemake ai e hoʻoloha, a laila, e kaomi i ke pihi ʻōmaʻomaʻo i kani ʻo ia.</span>','HAW','--ANY--','--ANY--','��]SPIN�AЃ�e','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2493,'AIR','E holoi i Kahawaena','HAW','--ANY--','--ANY--','���P6L���-,ܭ��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2695,'AIR','Pautas para la revisión ','ESN','--ANY--','--ANY--','����CMї�h��ks','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3407,'AIR','?A?ole hiki ia?oe ke ?e?e me keia polokalamu punaewele puni honua.?Olu?olu e ho?ohana i kekahi polokalamu punaewele i kako?o.','HAW','--ANY--','--ANY--','�1Md�&D��jš��J�','2013-12-18 14:09:07.190');
INSERT INTO `client_messagetranslation` VALUES (2447,'AIR','<span>Pilikia (ʻAʻole)</span>','HAW','--ANY--','--ANY--','�B��	H�����E��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3120,'AIR','La oportunidad de prueba está en curso.','ESN','--ANY--','--ANY--','�L�G��Ex������','2014-03-22 20:35:48.657');
INSERT INTO `client_messagetranslation` VALUES (2532,'AIR','Ka Pakuhi Kumumea','HAW','--ANY--','--ANY--','�Z�j�<@[�zɩl7\"]','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2711,'AIR','VERDADERO','ESN','--ANY--','--ANY--','�p?/˻Mp������L�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2947,'AIR','Verdana','HAW','--ANY--','--ANY--','�rt)(@���*��@_','2013-12-18 14:09:06.767');
INSERT INTO `client_messagetranslation` VALUES (3533,'AIR','Huli:','HAW','--ANY--','--ANY--','�s���`JX�_ܰ��3','2013-12-18 14:09:07.387');
INSERT INTO `client_messagetranslation` VALUES (3457,'AIR','https://tds.airws.org/tdscore_test_student','HAW','--ANY--','--ANY--','Ɨ��CN�����','2013-12-18 14:09:07.277');
INSERT INTO `client_messagetranslation` VALUES (3709,'SBAC','{silence} D.{silence}','ENU','--ANY--','--ANY--','Ʃ�a��Bl�_�Dy','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2890,'AIR','ʻAʻole, ʻaʻole au i lohe.','HAW','--ANY--','--ANY--','ƽ{nK�C������','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3060,'AIR','Ho?ole ?ia ke wehe ?ia ?ana o keia ho?ike nei ma muli o na la ho?omaka a me na la palena pau','HAW','--ANY--','--ANY--','�I���DʏA�\0q&>�','2013-12-18 14:09:06.883');
INSERT INTO `client_messagetranslation` VALUES (3447,'SBAC','Cerrar sesión','ESN','--ANY--','--ANY--','�-��%Gz�>�R��-�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3553,'AIR','Nana i na pilina','HAW','--ANY--','--ANY--','�W�\\�N��k��N','2013-12-18 14:09:07.420');
INSERT INTO `client_messagetranslation` VALUES (3529,'AIR','Pani','HAW','--ANY--','--ANY--','��*b4�Jҗx{Ոx��','2013-12-18 14:09:07.380');
INSERT INTO `client_messagetranslation` VALUES (2404,'AIR','Ke Kula:','HAW','--ANY--','--ANY--','����n=A���Wo��i','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2896,'AIR','E ho\'olaula I Na Kumumana\'o','HAW','--ANY--','--ANY--','�SB��I໵���	��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2690,'AIR','Calculadora','ESN','--ANY--','--ANY--','�S̓��E�����V�Z�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2599,'AIR','E ʻōlelo i ke koho E','HAW','--ANY--','--ANY--','�~`�J\nG衻zT�~�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3067,'AIR','Su prueba se ha interrumpido. Para continuar la prueba, consulte con su Administrador de Pruebas.','ESN','--ANY--','--ANY--','ȯb���C�,1ZAd�j','2014-03-22 20:33:38.147');
INSERT INTO `client_messagetranslation` VALUES (2766,'AIR','Normal','ESN','--ANY--','--ANY--','�Ҙ�<�H���f��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2759,'AIR','VERDADERO','ESN','--ANY--','--ANY--','��T�>E�DA���5','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3292,'AIR','Hewa ka mahina halihali (Ho?ohana i ke ?ano ho?ala MM)','HAW','--ANY--','--ANY--','���%i;E��0vP [','2013-12-18 14:09:07.100');
INSERT INTO `client_messagetranslation` VALUES (3073,'AIR','No coinciden los códigos de seguridad de los artículos','ESN','--ANY--','--ANY--','�p赇IQ��/���J�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2793,'AIR','Someter un comentario para este ítem.','ESN','--ANY--','--ANY--','�k��N��7�*��','2014-03-22 20:33:38.080');
INSERT INTO `client_messagetranslation` VALUES (3056,'AIR','No se encuentra la base de datos RTS','ESN','--ANY--','--ANY--','�)\'A���.���Q','2014-03-22 20:35:32.327');
INSERT INTO `client_messagetranslation` VALUES (3072,'AIR','ʻAʻole loaʻa kēia ʻikamu ma kēia hōʻike','HAW','--ANY--','--ANY--','�8*�yO�#&&$��&','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2511,'SBAC','Llego al fin del examen. Se puede repasar las respuestas. Cuando termine repasando las respuestas, cliquea [Entrega el examen para evaluación]. no puede cambiar sus respuestas después de que usted se someta su examen.','ESN','--ANY--','--ANY--','�Q��z�A���`��aE\0','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3613,'AIR','Felicidades por terminar su prueba! Después de revisar sus respuestas, haga clic en el botón de finalizar la prueba para cerrar la sesión.','ESN','--ANY--','--ANY--','�Y@��J��Y���Y�','2014-03-22 20:36:39.467');
INSERT INTO `client_messagetranslation` VALUES (2713,'AIR','Básico','ESN','--ANY--','--ANY--','�uT���L�t�L���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2358,'AIR','../Projects/Hawaii/Help/help_hi.html','HAW','--ANY--','--ANY--','�x�]�Oܙmd�|$�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2958,'AIR','Mostrarle la puntuación al estudiante','ESN','--ANY--','--ANY--','ɒ5.��Lͫ�g��w~','2014-03-22 20:34:47.197');
INSERT INTO `client_messagetranslation` VALUES (3030,'AIR','No se encuentra la base de datos RTS','ESN','--ANY--','--ANY--','���uGW�j�M3�','2014-03-22 20:35:11.293');
INSERT INTO `client_messagetranslation` VALUES (2794,'AIR','E ho?omohala i na mana?o a pau','HAW','--ANY--','--ANY--','��Y��KI}�v�\"��E�','2013-12-18 14:09:06.667');
INSERT INTO `client_messagetranslation` VALUES (3128,'AIR','Hiki i keia haumana ke ho?omaka. Ua ?apono ?ia.','HAW','--ANY--','--ANY--','��8��N���]��@O�','2013-12-18 14:09:06.950');
INSERT INTO `client_messagetranslation` VALUES (2495,'AIR','Makemake anei \'oe e ho\'ololi i ka mana\'o i koho mua \'ia? <\'Ae> <\'A\'ole>','HAW','--ANY--','--ANY--','ʕ�MxBß5$�,L��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3729,'AIR','?Olu?olu e nana i na ua ho?okomo pololei ?oe i na ?ike au i ho?okomo ai.Ina makemake ?oe i kokua, noi i kekahi kahu ha?awi ho?ike.','HAW','--ANY--','--ANY--','ʲ_\'\0�J�VW��&�','2013-12-18 14:09:07.667');
INSERT INTO `client_messagetranslation` VALUES (2343,'AIR','../tools/formulas/2010/de_6_8.html','HAW','--ANY--','--ANY--','���p8I�V��j*L�','2013-12-18 14:09:06.597');
INSERT INTO `client_messagetranslation` VALUES (2413,'AIR','Tamaño de impresión:','ESN','--ANY--','--ANY--','���K��HR�{�\"|','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2753,'AIR','Na \'Ikamu Pakuhi','HAW','--ANY--','--ANY--','�8� aIи?\0�Y','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2840,'AIR','Imprimir item','ESN','--ANY--','--ANY--','�H�4��H�M^�@�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3700,'AIR','{silence}Koho A.{silence}','HAW','--ANY--','--ANY--','�^T/�F���\r�#ؒ�','2013-12-18 14:09:07.620');
INSERT INTO `client_messagetranslation` VALUES (2761,'AIR','Pasajes y ejercicios','ESN','--ANY--','--ANY--','�s4�<�N}�Q��� ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2831,'AIR','Waiho i na mana?o no keia ?ikemu pono no na ho?ike.','HAW','--ANY--','--ANY--','�z&�E�I+��~`�o�','2013-12-18 14:09:06.700');
INSERT INTO `client_messagetranslation` VALUES (3684,'AIR','No hay dirección URL para el satélite','ESN','--ANY--','--ANY--','��X�O��8�5{�H�','2014-03-22 20:36:39.890');
INSERT INTO `client_messagetranslation` VALUES (2785,'AIR','Imprimir item','ESN','--ANY--','--ANY--','˱�L��C��DsGs\'�9','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2678,'AIR','Haga clic aquí para imprimir el pasaje.','ESN','--ANY--','--ANY--','˻�탆B�[���\Z','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2448,'AIR','Verificación de sonido: Problema de audio','ESN','--ANY--','--ANY--','���V-uBǇ[\n=��7�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2423,'AIR','\'O Kau Ho\'ike anei Keia?','HAW','--ANY--','--ANY--','���&:@̦\'�|t,Y1','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2522,'AIR','<span>E lele \'oe</span>','HAW','--ANY--','--ANY--','��)��%C���U���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2932,'AIR','Grabar en relieve','ESN','--ANY--','--ANY--','���4�Dt�&��a','2014-03-22 20:34:28.127');
INSERT INTO `client_messagetranslation` VALUES (2398,'AIR','Navegador:','ESN','--ANY--','--ANY--','���Y�LV�x!9J�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2571,'AIR','Makemake anei \'oe e ho\'omaha ma keia ho\'ike? Ina oe e ho\'omaha no 20 minuke, \'a\'ole hiki ia\'oe ke ho\'ololi i na pane o na ninau i pane \'ia. E \'olu\'olu \'oe, e ninau i ke Kumu Ho\'ike ma mua o kou ho\'omaha \'ana ma kau ho‘ike.','HAW','--ANY--','--ANY--','���Y�F���2�L','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3097,'AIR','Usted no tiene un rol asignado para acceder .No se puede iniciar una sesión sin un rol válido.','ESN','--ANY--','--ANY--','�Ah�zLM)���.��','2014-03-22 20:35:48.643');
INSERT INTO `client_messagetranslation` VALUES (3325,'SBAC','Navigate to Another Smarter Balanced Assessments System','ENU','--ANY--','--ANY--','�D�SSD��l���','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2395,'AIR','<span>Iniciar sesión</span>','ESN','--ANY--','--ANY--','�p�Ō,M�������','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2984,'AIR','{silence}Option B.{silence}','ESN','--ANY--','--ANY--','̿�\0�HIl�|1t�]��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2978,'AIR','{silence}Koho B.{silence}','HAW','--ANY--','--ANY--','������O��i����','2013-12-18 14:09:06.803');
INSERT INTO `client_messagetranslation` VALUES (3151,'AIR','El grado que selecciono no esta correcto. Inténtelo nuevamente.','ESN','--ANY--','--ANY--','��b�+B��p�jW��','2014-03-22 20:33:38.153');
INSERT INTO `client_messagetranslation` VALUES (2680,'AIR','\'A\'ole i hana \'ia i kēia manawa','HAW','--ANY--','--ANY--','�$&/��N}����5;�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2622,'AIR','Seleccione las ubicaciones de los puntos.','ESN','--ANY--','--ANY--','�r�IŦA.�E���?','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2627,'AIR','Seleccione un punto o borde para agregar valor.','ESN','--ANY--','--ANY--','͏���I�����S�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2699,'AIR','E maka no ka nana hou \'ana','HAW','--ANY--','--ANY--','͚�\Z�lBg��\n���Mp','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3271,'AIR','Esta sesión  ha sido trasladada a otro ordenador/navegador. Todas las pruebas activas continuarán sin interrupción.','ESN','--ANY--','--ANY--','ͤ�8�Dލ�Y-P#�j','2014-03-22 20:39:31.230');
INSERT INTO `client_messagetranslation` VALUES (2514,'AIR','Se produjo un problema con el sistema.  Proporcione este número al administrador de pruebas.','ESN','--ANY--','--ANY--','�ɩ�@�@l�����)�','2014-03-22 20:33:38.123');
INSERT INTO `client_messagetranslation` VALUES (2863,'AIR','Cerrar Comentario','ESN','--ANY--','--ANY--','���NA)���Vz��v','2014-03-22 20:34:06.520');
INSERT INTO `client_messagetranslation` VALUES (2878,'AIR','E pani i ka mana?o','HAW','--ANY--','--ANY--','��K!��Jʎ{����','2013-12-18 14:09:06.733');
INSERT INTO `client_messagetranslation` VALUES (3654,'AIR','Ho?okomo i ka nui o na haumana au e makemake nei e komo i ka ho?ike i keia manawa:','HAW','--ANY--','--ANY--','��2��K̚<��Ȁ>�','2013-12-18 14:09:07.557');
INSERT INTO `client_messagetranslation` VALUES (2731,'AIR','DE 2 Makemakika','HAW','--ANY--','--ANY--','��_��D�$�lvڋ(','2013-12-18 14:09:06.637');
INSERT INTO `client_messagetranslation` VALUES (2587,'AIR','Preguntas y opciones orales (en inglés)','ESN','--ANY--','--ANY--','�6�BMD\"����W�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3681,'AIR','Falló la asignación de satélite','ESN','--ANY--','--ANY--','�<|��eA���C(E{�','2014-03-22 20:36:39.877');
INSERT INTO `client_messagetranslation` VALUES (3432,'AIR','Ho?okomo hou i kekahi mau ?ikepili ?ea?e','HAW','--ANY--','--ANY--','�e�NޢDp�	�B���','2013-12-18 14:09:07.233');
INSERT INTO `client_messagetranslation` VALUES (2880,'AIR','Imprimir item','ESN','--ANY--','--ANY--','�kW�]D_���|<�؂','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3036,'AIR','Aia he pilikia me ka ‘ōnaehana. E ‘olu’olu e hā’awi i kēia helu iā Kumu','HAW','--ANY--','--ANY--','΃d� �@ىh��g��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3534,'AIR','1.Ho?okomo i na helu hana','HAW','--ANY--','--ANY--','ή�a�D��i���&','2013-12-18 14:09:07.390');
INSERT INTO `client_messagetranslation` VALUES (3495,'AIR','E waiho i ka helu SSID','HAW','--ANY--','--ANY--','ξY:7D��B��$�','2013-12-18 14:09:07.333');
INSERT INTO `client_messagetranslation` VALUES (3283,'AIR','Na lula ?oihana TTX','HAW','--ANY--','--ANY--','�m�PJу��<�$','2013-12-18 14:09:07.090');
INSERT INTO `client_messagetranslation` VALUES (3684,'AIR','?A?ohe URL no ka ukali','HAW','--ANY--','--ANY--','�)튲H\'����9f^','2013-12-18 14:09:07.600');
INSERT INTO `client_messagetranslation` VALUES (3002,'SBAC','Guardar y Cerrar','ESN','--ANY--','--ANY--','�U��xI��T�IfO�6','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3695,'AIR','Ua pau ka ho?ike wa ho?ohana a keia wa ho?ohana.?Olu?olu e noi i ke kokua a kau kahu ha?awi ho?ike.','HAW','--ANY--','--ANY--','�#�y�NH�d;��<�','2013-12-18 14:09:07.617');
INSERT INTO `client_messagetranslation` VALUES (3732,'SBAC','../Projects/SBAC/Help/help_dei.html','ENU','--ANY--','--ANY--','�=�S+�AΉ�v���','2013-09-18 13:24:24.793');
INSERT INTO `client_messagetranslation` VALUES (3438,'AIR','Ho?omaka i ka ho?okomo ?ana i na ?ikepili','HAW','--ANY--','--ANY--','�a�{��A)�(�_�h','2013-12-18 14:09:07.243');
INSERT INTO `client_messagetranslation` VALUES (3716,'AIR','Po?ino:?A?ole hiki ia?oe ke ho?onui i ka leo o kau iPad i ka wa e lawe ?ia nei ka ho?ike.Ina makemake ?oe e ho?onui i ka leo, ?olu?olu e ho?opio i ke kokua ?alaka?i.Ho?onui a i ?ole e ho?emi i ka leo ma ka ho?ohana ?ana i na pihi leo ma kau iPad, a laila ','HAW','--ANY--','--ANY--','�y<��|M�J�U�lt<','2013-12-18 14:09:07.647');
INSERT INTO `client_messagetranslation` VALUES (2682,'AIR','Aceptar','ESN','--ANY--','--ANY--','ϡ��VK᤟���Jx�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2987,'AIR','{silence}Koho E.{silence}','HAW','--ANY--','--ANY--','�k�}�A�����6��','2013-12-18 14:09:06.820');
INSERT INTO `client_messagetranslation` VALUES (2729,'AIR','Ka Melemele Halakea','HAW','--ANY--','--ANY--','�rxKv�����Օ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3659,'AIR','Mana Flash:','HAW','--ANY--','--ANY--','���Lo/G\r�r�1ؓ','2013-12-18 14:09:07.567');
INSERT INTO `client_messagetranslation` VALUES (2508,'AIR','E ho‘omaika‘i! Ua pau ka hō‘ike iā ‘oe. E \'olu\'olu, e ha\'alele i kēia manawa.','HAW','--ANY--','--ANY--','��\Z���E��.m�#k!','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3107,'AIR','Ya existe la oportunidad.','ESN','--ANY--','--ANY--','��n�.I=��3 �}Tf','2014-03-22 20:35:48.650');
INSERT INTO `client_messagetranslation` VALUES (2942,'AIR','Na loli','HAW','--ANY--','--ANY--','�T�7�I���(�P���','2013-12-18 14:09:06.757');
INSERT INTO `client_messagetranslation` VALUES (3426,'AIR','Koho i kekahi ho?ike:','HAW','--ANY--','--ANY--','с����IW��K�҄�\n','2013-12-18 14:09:07.223');
INSERT INTO `client_messagetranslation` VALUES (3041,'AIR','Se produjo un problema al reiniciar la prueba.  Inténtelo nuevamente.','ESN','--ANY--','--ANY--','х*��M_��4q��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2552,'AIR','E Pani I Na \'Okuhi Hana \'Ikamu','HAW','--ANY--','--ANY--','ќ��9�EM�IC��AF','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2909,'AIR','Instrucciones orales (en inglés)','ESN','--ANY--','--ANY--','ѯ9��Iz�/��Oo#','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3476,'AIR','Huli i ka haumana','HAW','--ANY--','--ANY--','Ѹ�E��#@�we{�','2013-12-18 14:09:07.307');
INSERT INTO `client_messagetranslation` VALUES (3028,'AIR','Se produjo un problema con el sistema.  Proporcione este número al AP.','ESN','--ANY--','--ANY--','��]��HG���J��;3','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2341,'AIR','Ma o ka \'olelo o ka Family Educational Rights and Privacy Act (FERPA), ho\'ole \'ia ka ho\'ike \'ana i ka \'ike pilikino e ke kanawai.','HAW','--ANY--','--ANY--','����i�J6��3�j�P','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2426,'AIR','<span>No</span>','ESN','--ANY--','--ANY--','��]Y�Ak��fD�\Z�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2401,'SBAC','Grade:','ENU','--ANY--','--ANY--','��E{lI#�aI��UO','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2816,'AIR','Waiho i mana?o no keia ?ikamu pono no na ho?ike.','HAW','--ANY--','--ANY--','�L%�m�FP�/�z��','2013-12-18 14:09:06.690');
INSERT INTO `client_messagetranslation` VALUES (3040,'AIR','Se produjo un problema al reiniciar la prueba.  Inténtelo nuevamente.','ESN','--ANY--','--ANY--','�M�x��O����44�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3542,'AIR','Mau mea ?ea?e','HAW','--ANY--','--ANY--','�]�.]gJB�#��m�','2013-12-18 14:09:07.403');
INSERT INTO `client_messagetranslation` VALUES (2553,'AIR','ʻAe','HAW','--ANY--','--ANY--','�b�2`@ü*F�G��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2745,'AIR','HI 8 Makemakika','HAW','--ANY--','--ANY--','�s\Z�3lGs�I�(Rp��','2013-12-18 14:09:06.657');
INSERT INTO `client_messagetranslation` VALUES (2450,'AIR','<span>Haʻalele</span>','HAW','--ANY--','--ANY--','�|���HCy�˭D�L�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2620,'AIR','Makemake anei \'oe e ho\'ololi i ka mana\'o i koho mua \'ia? <\'Ae> <\'A\'ole>','HAW','--ANY--','--ANY--','ҧf9�H�g�	��;G','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3094,'AIR','?A?ohe ?ikemu e like me kela: {OPENBRACE}0{CLOSEBRACE}','HAW','--ANY--','--ANY--','��U��A��wi��vP','2013-12-18 14:09:06.907');
INSERT INTO `client_messagetranslation` VALUES (3118,'AIR','No se permite el inicio de sesión como invitado','ESN','--ANY--','--ANY--','���*Cw�m�H�UK','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2390,'AIR','Ka Mea Ho\'ohana','HAW','--ANY--','--ANY--','����Cq�ր�j�\n�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2401,'AIR','Nivel de grado del estudiante:','ESN','--ANY--','--ANY--','�7U�SGp���5�zZn','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2414,'AIR','Maʻa Mau','HAW','--ANY--','--ANY--','�C��P�K�7p�[s)O','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2435,'AIR','E \'olu\'olu, e kali no ka \'ike ana ina ua ho\'ouka \'ia ka polokalamu Java.','HAW','--ANY--','--ANY--','�S�h�6Kj�Æ$WN��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2553,'AIR','Sí','ESN','--ANY--','--ANY--','�h\\3�\"A�F�h��W','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3345,'SBAC','<p>Type in a full, valid State-SSID and click [Look Up Student] to access an individual student\'s personal information, including enrolled grade level, birth date and associated school and district. Note: Partial searches are not allowed.</p><br/><p>Note: This Student Lookup feature allows you to verify student information for login purposes only. It does not indicate whether a student is eligible to test. To verify student eligibility for a specific online assessment, please check TIDE.</p>','ENU','--ANY--','--ANY--','�v���I����)�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3084,'AIR','Hiki ?ole ke loa?a na ?ikepili ho?ike ma ke kahua RTS','HAW','--ANY--','--ANY--','��za.E���\'��&	�','2013-12-18 14:09:06.903');
INSERT INTO `client_messagetranslation` VALUES (2885,'AIR','ʻAe, ua lohe nō au i ka leo','HAW','--ANY--','--ANY--','���\n� Aџ��<��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3263,'AIR','Ka nui o ka pa?i ?ana','HAW','--ANY--','--ANY--','�,�%��LP��X��8��','2013-12-18 14:09:07.060');
INSERT INTO `client_messagetranslation` VALUES (2567,'AIR','A\'ole i loa\'a kēia hi\'ohi\'ona ma ka hō\'ike \'ōlelo Hawai\'i.','HAW','--ANY--','--ANY--','�nz}�E�T!58�Q�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3713,'AIR','Hewa ke kainoa ?ana. ?Olu?olu e hoa?o hou a i ?ole e noi i ke kokua o kekahi kahu ha?awi ho?ike.','HAW','--ANY--','--ANY--','�Ƈ$��D0���|�-�=','2013-12-18 14:09:07.640');
INSERT INTO `client_messagetranslation` VALUES (2912,'AIR','Buscando la próxima página.','ESN','--ANY--','--ANY--','���oRL�=�c\Z��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3117,'AIR','No hay oportunidad de prueba coincide con el ID de informes.','ESN','--ANY--','--ANY--','�\0~%�qC��bcy��.V','2014-03-22 20:35:48.657');
INSERT INTO `client_messagetranslation` VALUES (3669,'AIR','?Ho?ole ?ia ka noi no ke komo ?ana i kekahi ?apana.E ho?oha?alele ?ia ana ?oe i keia manawa.','HAW','--ANY--','--ANY--','�9�XD��8�ePF�H','2013-12-18 14:09:07.583');
INSERT INTO `client_messagetranslation` VALUES (3007,'AIR','E Ho\'i','HAW','--ANY--','--ANY--','�2qI՟������','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3568,'AIR','Huli koke','HAW','--ANY--','--ANY--','��#��H̒d�9�','2013-12-18 14:09:07.443');
INSERT INTO `client_messagetranslation` VALUES (2525,'AIR','Actualmente se está asignando un puntaje a su prueba.  Espere.','ESN','--ANY--','--ANY--','�3���D �|��\Zb�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2499,'SBAC',' <p><strong>For Students:</strong></p><p></p> <ol><li>Uncheck the Guest User and Guest Session checkboxes.</li><li>Enter your first name into the First Name box.</li><li>Enter your two-digit state code and your SSID into the State-SSID box.</li><li>Enter the Session ID that your TA gave you. </li><li>Click [Sign In].</li> </ol></p> <p><strong>Guest Users:</strong></p><p> To log in to the Practice and Training Tests, simply select [Sign In], then navigate through the login screens. </p>','ENU','--ANY--','--ANY--','�H���OF�g<�	��','2014-03-11 12:35:15.663');
INSERT INTO `client_messagetranslation` VALUES (2997,'SBAC','State-SSID','ENU','--ANY--','--ANY--','�i�=��FE�@Rh��','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2460,'AIR','Ka Inoa o Ka Ho\'ike:','HAW','--ANY--','--ANY--','Վ�/�LM�H/� �Z�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2672,'AIR','<p>No kekahi mau nīnau hōʻike, pono ka haumāna e kahakiʻi i kekahi kiʻi, a i ʻole, e hoʻopau i kekahi  nīnau me kekahi ʻano hana. <a href=\"https://hsapt.tds.airast.org/grid/default.aspx?showLangSelect=true\" target=\"_self\">E kaomi i ʻaneʻi no nā laʻana o kēia ʻano nīnau.</a>.</p>    ','HAW','--ANY--','--ANY--','�ϫ�9VI�*!�nD�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3242,'AIR','Ua ho?a ?ia ka polokalamu palulu pukaaniani lelewale no keia polokalamu punaewele puni honua.Ina makemake ?oe e komo aku ma keia kahua nei, pono ?oe e ho?opio aku i ka polokalamu palulu.Ho?opio aku i ka polokalamu palulu pukaaniani lele wale a laila e kaomi i ka pihi ho?ano hou.','HAW','--ANY--','--ANY--','����L��r“�<`','2013-12-18 14:09:07.030');
INSERT INTO `client_messagetranslation` VALUES (2497,'AIR','<div class=\"blurb\"><p>Aloha mai kākou! Eia kahi o ka Ho‘oma‘ama‘a Hō‘ike. Hiki iā ‘oe ke ho‘ohana i kēia wahi no ka ho‘oma‘ama‘a ‘ana i nā hō‘ike HSA pūnaewele. No kekahi mau nīnau, e pono ana ‘oe e koho i kekahip pane; no nā nīnau ‘ē a‘e, pono paha ‘oe e kikokiko i pane pōkole, kahaki‘i i ki‘i a i ‘ole e ho‘okō ana ‘oe i kekahi hana ‘ē a‘e.<a href=\"http://www.alohahsa.org/answerKeys.html\" target=\"_self\" class=\"linkbox\">E kaomi i ‘ane‘i no ke Kī Pane no ka Hō‘ike Ho‘oma‘ama‘a.</a></p></div>\r\n<div class=\"blurb\"><p><a href=\"https://hsapt.tds.airast.org/grid/default.aspx?showLangSelect=true\" target=\"_self\" target=\"_self\" class=\"gridlink\">E kaomi i ‘ane‘i no ke a‘o ‘ana mai i ka pane ‘ana i nā nīnau ‘enehana ki‘i.</a></p></div>\r\n\r\n<div class=\"blurb\"><p>Pono ‘oe e ho‘ohana i ka Mozilla Firefox no ka ho‘okuene ‘ikepili no ke komo ‘ana ma ka Hō‘ike Ho‘oma‘ama‘a. ‘A‘ole hana ka Explorer a me ka Safari no kēia wahi. <a href=\"http://www.mozilla.com/en-US/firefox/all-older.html\" target=\"_self\" class=\"linkbox\"> E kaomi i ‘ane‘i no ka ho‘oili ‘ana i ka Firefox no ka Windows, ka Mac OS X a me ka Linux.</a><br />\r\nNo ka ho‘ohana ‘ana i ke Kuapo-Kākau-Me-Ka-Leo, pono ‘oe e ho‘ohana i ka ho‘okuena ‘ike no ka ho‘omaka ‘ana i ka hō‘ike ho‘oma‘ama‘a.</p></p></div>','HAW','--ANY--','--ANY--','���#�C����1.��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3640,'AIR','Paepae:','HAW','--ANY--','--ANY--','��d�K2��\r�;���','2013-12-18 14:09:07.533');
INSERT INTO `client_messagetranslation` VALUES (3117,'AIR','Kohu ?ole ka ID no ka ho?ike e ?imi ?ia nei.','HAW','--ANY--','--ANY--','��M�Kֻ���ZJ','2013-12-18 14:09:06.933');
INSERT INTO `client_messagetranslation` VALUES (3058,'AIR','No se puede encontrar la base de datos RTS','ESN','--ANY--','--ANY--','� χ&�A۫�_����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2496,'AIR','¿Está seguro de que desea cambiar el tema que seleccionó anteriormente? <Sí> <No>','ESN','--ANY--','--ANY--','�J���G��P�2�7','2014-03-22 20:33:38.113');
INSERT INTO `client_messagetranslation` VALUES (2571,'AIR','¿Está seguro de que desea detener la prueba? Si se detiene la prueba por más de {0} minutos, usted quizás no pueda hacer cambios a las preguntas que usted ya ha contestado. Consulte a su administrador de prueba antes de pausar la prueba.','ESN','--ANY--','--ANY--','�Km�� F��\0�9\rf�','2014-03-22 20:33:38.127');
INSERT INTO `client_messagetranslation` VALUES (2909,'AIR','E ʻŌlelo I Ka ʻŌkuhi (Ma Ka Namu Haole)','HAW','--ANY--','--ANY--','�U�\'IA��8��a','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2483,'AIR','Detener reproducción de grabación','ESN','--ANY--','--ANY--','֘��c\"D6�\n��>)Y�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3531,'AIR','Maika?i ka hana ?ana o keia polokalamu ma na pukaaniani ?ike laula.?Olu?olu e ho?ohuli i kau hame?a a ku pololei i luna.','HAW','--ANY--','--ANY--','�!�n�F�ͩl�J��','2013-12-18 14:09:07.383');
INSERT INTO `client_messagetranslation` VALUES (3408,'AIR','Makemake a nei ?oe e ho?oku ?ia keia ho?ike nei?No ka ho?omau ?ana i ka ho?okomo ?ana i na ?ikepili no keia haumana nei, pono ?oe e ?e?e hou.','HAW','--ANY--','--ANY--','�-V��E3�<�5xw�9','2013-12-18 14:09:07.193');
INSERT INTO `client_messagetranslation` VALUES (2980,'AIR','{silence}Koho D.{silence}','HAW','--ANY--','--ANY--','�F}�o�K�摔���','2013-12-18 14:09:06.807');
INSERT INTO `client_messagetranslation` VALUES (2657,'AIR','E Kaha Laina','HAW','--ANY--','--ANY--','�w�B0H����5�K�4','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2449,'AIR','E ha‘i ‘oe iā Kumu aia he pilikia me ke kani. Pono ka ho‘okani ‘ana a me ke ‘oki leo ‘ana no kēia hō‘ike.','HAW','--ANY--','--ANY--','ח����CҠ�_���r','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2981,'AIR','{silence}Koho E.{silence}','HAW','--ANY--','--ANY--','׮padAG���-1','2013-12-18 14:09:06.810');
INSERT INTO `client_messagetranslation` VALUES (2685,'AIR','E \'olu\'olu, e kali…','HAW','--ANY--','--ANY--','װnۖ�OM�v�#;�V�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2876,'AIR','Waiho i mana?o no keia ?ikemu pono no na ho?ike.','HAW','--ANY--','--ANY--','��dY�H;�K0fе�','2013-12-18 14:09:06.730');
INSERT INTO `client_messagetranslation` VALUES (2412,'AIR','Ka \'Olelo:','HAW','--ANY--','--ANY--','�L!�{�Kh�H�\\�A)','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2367,'AIR','No puede ingresar con este navegador. Por favor utilice la última versión del navegador seguro o un explorador web compatible.','ESN','--ANY--','--ANY--','�PJ\\[nN��)g��4o�','2014-03-22 20:33:38.093');
INSERT INTO `client_messagetranslation` VALUES (2935,'AIR','E huna i ka helu ?ai','HAW','--ANY--','--ANY--','�_B �\ZK��jpLԤ��','2013-12-18 14:09:06.747');
INSERT INTO `client_messagetranslation` VALUES (3064,'AIR','Ke hoʻāʻo nei e kākau hou i kēia ʻikamu','HAW','--ANY--','--ANY--','�b�S��H��%>��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3417,'AIR','¿Es éste usted?','ESN','--ANY--','--ANY--','؀_��E����#;�2','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3480,'AIR','Wae ma ke ?ano:','HAW','--ANY--','--ANY--','؎�Z�$H��)NL� x�','2013-12-18 14:09:07.313');
INSERT INTO `client_messagetranslation` VALUES (3650,'AIR','Navegador seguro:','ESN','--ANY--','--ANY--','خ�$��Ku�Pl~��U','2014-03-22 20:36:39.697');
INSERT INTO `client_messagetranslation` VALUES (3733,'AIR','Ua ho?ea maila ?oe i ka palena pau o ka ho?ike.Kaomi i ka pihi [?Ae] no ka ne?e ?ana aku i kekahi ?ao?ao a?e.Kaomi i ka pihi [Ho?ole] no ka ho?omau ?ana i keia ho?ike nei.','HAW','--ANY--','--ANY--','�����MМWuu�U�','2013-12-18 14:09:07.673');
INSERT INTO `client_messagetranslation` VALUES (3610,'AIR','Haga una pausa','ESN','--ANY--','--ANY--','��\n�wB��6ʟ�)�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2367,'AIR','A\'ole hiki ia \'oe ke \'e\'e me keia polokalamu kele punaewele. E \'olu\'olu, e ho\'ohana i ka Polokalamu Kele Punaewele Pa\'a Hou a i \'ole i ka polokalamu Mozilla Firefox no ka hana \'ana i ka ho\'ike ho\'oma\'ama\'a.','HAW','--ANY--','--ANY--','��>+M�����518','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2780,'AIR','ítems y estímulos','ESN','--ANY--','--ANY--','��`6`�Ce���*8��','2014-03-22 20:33:38.140');
INSERT INTO `client_messagetranslation` VALUES (3674,'AIR','Debe continuar la prueba en el mismo tipo de sesión en cual usted comenzo.','ESN','--ANY--','--ANY--','���RʿK��3K�jY�','2014-03-22 20:36:39.850');
INSERT INTO `client_messagetranslation` VALUES (3163,'AIR','?A?ole i loa?a ka wa ho?ohana no ka lawe ?ana i ka ho?ike.','HAW','--ANY--','--ANY--','�+C��C)����w�','2013-12-18 14:09:06.970');
INSERT INTO `client_messagetranslation` VALUES (2983,'AIR','{silence}Option A.{silence}','ESN','--ANY--','--ANY--','��D��M���͐�alm','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2365,'AIR','A\'ole i ho\'okomo pololei \'ia kou inoa mua a i \'ole kau helu haumana. E \'olu\'olu \'oe, e ho\'a\'o hou a i \'ole e noi i kau kumu ho\'ike.','HAW','--ANY--','--ANY--','�5�\Z�*E��I�Mg��4','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2779,'AIR','Instrucciones y ejercicios','ESN','--ANY--','--ANY--','�Jv\Z�]C�n�\rdX��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2986,'AIR','{silence}Koho D.{silence}','HAW','--ANY--','--ANY--','�sNc�A�@Q���Q�','2013-12-18 14:09:06.817');
INSERT INTO `client_messagetranslation` VALUES (3167,'AIR','No se le permite ingresar sin un Administrador de Prueba.','ESN','--ANY--','--ANY--','و�Xf�D�����}','2014-03-22 20:39:22.707');
INSERT INTO `client_messagetranslation` VALUES (2635,'AIR','Ua lohe anei ‘oe i ke kani? E kaomi [‘Ae] ‘a i ‘ole [‘A‘ole]','HAW','--ANY--','--ANY--','ٽ\rB��@L�f�����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3670,'AIR','../tools/formulas/2010/air_HS_math.html','HAW','--ANY--','--ANY--','پ�quCl�ϰ⸲U','2013-12-18 14:09:07.583');
INSERT INTO `client_messagetranslation` VALUES (3634,'AIR','E koho i kekahi pa?a leo','HAW','--ANY--','--ANY--','��MI�B@>�*Ե�-dr','2013-12-18 14:09:07.527');
INSERT INTO `client_messagetranslation` VALUES (3445,'AIR','?Olu?olu e kali.Ke ?imi nei i na helu ?ai panina.','HAW','--ANY--','--ANY--','��S�dAF;��U<��h','2013-12-18 14:09:07.257');
INSERT INTO `client_messagetranslation` VALUES (2697,'AIR','ʻIkamu Kikokiko Mea Hōʻole','HAW','--ANY--','--ANY--','���TB��K�A�ƚ]','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2621,'AIR','Tiempo de espera inactivo','ESN','--ANY--','--ANY--','�D�ǘ�L�[;$m�t�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2703,'AIR','Ka Nui O ka Pa\'i \'Ana','HAW','--ANY--','--ANY--','�I\\��\'H���ɸ���=','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2469,'AIR','Instrucciones y ayuda para la prueba','ESN','--ANY--','--ANY--','�ec#RF��)��^��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3209,'AIR','Ua ho?a ?ia ka polokalamu palulu pukaaniani lele wale ma keia polokalamu punaewele nei.Ina makemake ?oe e komo aku ma keia kahua nei, pono ?oe e ho?opio aku i ka polokalamu palulu.Ho?opio aku i ka polokalamu palulu pukaaniani lele wale a laila e kaomi i ka pihi ho?ano hou.','HAW','--ANY--','--ANY--','�z���B0�~,���','2013-12-18 14:09:07.003');
INSERT INTO `client_messagetranslation` VALUES (3607,'SBAC','Technology-Enhanced','ENU','--ANY--','--ANY--','ڜ!�K^No�S��C���','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2453,'AIR','<span>Cerrar sesión</span>','ESN','--ANY--','--ANY--','ڣ�0��Ht��%��x','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2982,'AIR','{silence}Koho F.{silence}','HAW','--ANY--','--ANY--','��T�eeM����X�Ч4','2013-12-18 14:09:06.810');
INSERT INTO `client_messagetranslation` VALUES (3320,'SBAC','<h2>Smarter Balanced Assessments</h2>','ENU','--ANY--','--ANY--','����@ĽZT�~��\n','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2704,'AIR','E Kahawaena','HAW','--ANY--','--ANY--','��NpCO��i��J�y','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2488,'AIR','Mai ʻŌlelo','HAW','--ANY--','--ANY--','��|��I�@˥�4�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2383,'SBAC','Contesta todas las preguntas antes de seguir a la próxima página.','ESN','--ANY--','--ANY--','��@�{@ݿ�ޠ{��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3139,'AIR','Lamentablemente, no tiene permiso para acceder a este sistema.  Consulte con su administrador de pruebas.','ESN','--ANY--','--ANY--','�\'{D�C���1��n�','2014-03-22 20:33:38.150');
INSERT INTO `client_messagetranslation` VALUES (2562,'AIR','La prueba está comenzando, espere…','ESN','--ANY--','--ANY--','����=�B��C^��\0�M','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3450,'AIR','?Olu?olu e ho?okomo i ka inoa ho?ohana a me ka ?olelo huna no ka ho?ohana ?ana i keia ho?ike helu ?ai a i ?ole ho?okomo i na pane ?ikepili.Pono e loa?a ia?oe ke kuleana no ka ho?ohana ?ana i keia.','HAW','--ANY--','--ANY--','��U2��Hߡ�1�\"�','2013-12-18 14:09:07.263');
INSERT INTO `client_messagetranslation` VALUES (3468,'AIR','Pilikia ka ho?oia ?ana i ka mea ho?ohana.','HAW','--ANY--','--ANY--','��OW�0D;�>�1����','2013-12-18 14:09:07.290');
INSERT INTO `client_messagetranslation` VALUES (2459,'AIR','ID de sesión:','ESN','--ANY--','--ANY--','��)-�E�.\'�CV��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3498,'AIR','Pani','HAW','--ANY--','--ANY--','����T@N��;j���','2013-12-18 14:09:07.340');
INSERT INTO `client_messagetranslation` VALUES (2749,'AIR','FALSO','ESN','--ANY--','--ANY--','�.\\-]NMܰȗf�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2691,'AIR','Opciones de colores','ESN','--ANY--','--ANY--','�V�ģ}K���d�o\\�t','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3711,'SBAC','{silence} F.{silence}','ENU','--ANY--','--ANY--','�\\٣E7�wP���','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3129,'AIR','Ua ha?alele ka haumana i keia wa ho?ohana nei.','HAW','--ANY--','--ANY--','ܙcd�KF�$	��5�','2013-12-18 14:09:06.953');
INSERT INTO `client_messagetranslation` VALUES (3621,'AIR','?A?ole hiki ia?oe ke ho?i hou mai i keia hapa o ka ho?ike.Makemake a nei ?oe e ho?omau aku?','HAW','--ANY--','--ANY--','ܫ��C�D���w��A�','2013-12-18 14:09:07.507');
INSERT INTO `client_messagetranslation` VALUES (3727,'AIR','Ha?i kuli ?olelo Haole','HAW','--ANY--','--ANY--','ܶ\r�\\ES�����6j�','2013-12-18 14:09:07.660');
INSERT INTO `client_messagetranslation` VALUES (3102,'AIR','?A?ole i makaukau keia ho?ike i keia manawa.','HAW','--ANY--','--ANY--','���ʀI��.D]LK','2013-12-18 14:09:06.917');
INSERT INTO `client_messagetranslation` VALUES (2662,'AIR','E pa\'i i ka \'ikamu','HAW','--ANY--','--ANY--','���V;N����o��6�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3662,'AIR','Mana JRE:','HAW','--ANY--','--ANY--','�ʤ@�A����W��>','2013-12-18 14:09:07.570');
INSERT INTO `client_messagetranslation` VALUES (3243,'AIR','Hewa ka inoa mua a me/a i ?ole ka inoa hope','HAW','--ANY--','--ANY--','�6�\Z�F��D.N�>!','2013-12-18 14:09:07.030');
INSERT INTO `client_messagetranslation` VALUES (2726,'AIR','Magenta claro','ESN','--ANY--','--ANY--','�\'�^#�O$���C���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3322,'SBAC','<p>You are about to log out of <b>ALL</b> Smarter Balanced Assessments systems. If you are logged into the Test Administrator Interface or the Test Administrator Training Site, your test sessions and all in-progress tests <b>will be paused.</b></p>  <p> You may click [Cancel] to return to the previous screen or click [Log Out] to exit <b><u>all</u></b> Smarter Balanced Asssessment systems.</p>','ENU','--ANY--','--ANY--','�(̻��G^��\'��G�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2916,'AIR','<span class=\"hasTTS\">Seleccione el texto que desea escuchar y haga clic en el botón verde para reproducirlo.</span>','ESN','--ANY--','--ANY--','�*H~J��1�Yւ� ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3668,'AIR','Gris mediano sobre gris claro','ESN','--ANY--','--ANY--','�/�]��FU�70�.��9','2014-03-22 20:36:39.843');
INSERT INTO `client_messagetranslation` VALUES (2607,'AIR','Opción de audio ','ESN','--ANY--','--ANY--','�F����MQ�٤	��R','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3133,'AIR','Aia he pilikia me ka ‘ōnaehana. E ‘olu’olu, e hana hou.','HAW','--ANY--','--ANY--','�e(��zFh�}�-]���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2385,'AIR','Pono \'oe e pane i na ninau a pau ma keia \'ao\'ao ma mua o ka ho\'opau \'ana i ka ho\'ike.','HAW','--ANY--','--ANY--','�i#pA�#�<~�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2472,'AIR','<span>Volver al ingreso</span>','ESN','--ANY--','--ANY--','ݗ~u��CĤ�ٴd\n�t','2014-03-22 20:33:38.113');
INSERT INTO `client_messagetranslation` VALUES (2871,'AIR','Waiho i mana?o no keia ?ikamu pono no na ho?ike.','HAW','--ANY--','--ANY--','�rǌN��.A�S','2013-12-18 14:09:06.727');
INSERT INTO `client_messagetranslation` VALUES (2813,'AIR','Cerrar Comentario','ESN','--ANY--','--ANY--','�#�`�O˅��x��3�','2014-03-22 20:33:38.090');
INSERT INTO `client_messagetranslation` VALUES (2497,'AIR','<!-- <div class=\"blurb\"><a class=\"gridlink\" target=\"_blank\" href=\"https://airpt.tds.airast.org/grid/default.aspx\">Click here to learn how to answer technology-enhanced questions.</a> </p> </div> <div class=\"blurb\"><p>Welcome to the Training Test site. You can use this site to take sample tests online to become familiar with the online testing environment. Some of the questions will require you to select one answer; others may require you to type a short answer, draw a picture, or complete another type of open-ended task.</p></div><div class=\"blurb\"><p>Interactive tutorial: <a class=\"gridlink\" target=\"_self\" href=\"https://dept.tds.airast.org/grid/default.aspx\">Click here to learn how to answer technology-enhanced questions.</a></p></div><div class=\"blurb\"><p><strong> you must use Mozilla Firefox or the Secure Browser as your Internet browser to access the Training Test.</strong> Internet Explorer and Safari will not work with this site. <a class=\"linkbox\" target=\"_self\" href=\"http://www.mozilla.com/en-US/firefox/all-older.html\">Click here to download Firefox for Windows, Mac OS X, and Linux.</a></p></div> -->','ENU','--ANY--','--ANY--','�7X��DNj��[r���','2013-10-07 13:13:29.133');
INSERT INTO `client_messagetranslation` VALUES (3605,'SBAC','Technology-Enhanced','ENU','--ANY--','--ANY--','�J��սBҁ-����','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2936,'AIR','Reglas de Negocio TTX','ESN','--ANY--','--ANY--','�nOםaB�����v�@','2014-03-22 20:34:28.167');
INSERT INTO `client_messagetranslation` VALUES (2704,'AIR','Tachar','ESN','--ANY--','--ANY--','ކ�&X�C@�����t','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3439,'AIR','Reanudar prueba','ESN','--ANY--','--ANY--','ޣ�^��C���;�5�Ů','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3653,'AIR','Olu?olu e koho i ka ho?ike','HAW','--ANY--','--ANY--','޻@X�|D��AǕY�!','2013-12-18 14:09:07.557');
INSERT INTO `client_messagetranslation` VALUES (2676,'AIR','E Pa\'i','HAW','--ANY--','--ANY--','����nFJ��5 x�2','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3655,'AIR','Ho?okomo i ka nui o na haumana au e makemake nei e komo i ka ho?ike i keia manawa:','HAW','--ANY--','--ANY--','��ӊ��M��kzV���','2013-12-18 14:09:07.560');
INSERT INTO `client_messagetranslation` VALUES (2518,'AIR','ID de estudiante:','ESN','--ANY--','--ANY--','���C��uR෬�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3009,'AIR','?Apono','HAW','--ANY--','--ANY--','�Z`�q_A��A���X6','2013-12-18 14:09:06.833');
INSERT INTO `client_messagetranslation` VALUES (2585,'AIR','E ʻŌlelo I Ke Kākau (Ma Ka Namu Haole)','HAW','--ANY--','--ANY--','�s��\0ZHJ�X>V�|Bn','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3728,'AIR','Mana?o laulike','HAW','--ANY--','--ANY--','�t)ˉ^@ɹJw���|','2013-12-18 14:09:07.667');
INSERT INTO `client_messagetranslation` VALUES (2970,'AIR','../tools/formulas/2010/mn_GRAD_math.html','HAW','--ANY--','--ANY--','���]�sI?���{U��D','2013-12-18 14:09:06.797');
INSERT INTO `client_messagetranslation` VALUES (2483,'AIR','Kū I Ka Hoʻokani ʻAna I Ka Leo','HAW','--ANY--','--ANY--','��?:�EN���j�b��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2648,'AIR','Problema de audio','ESN','--ANY--','--ANY--','�!da��N������Z','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2500,'AIR','  <a href=\"#\" class=\"goPractice\">Visite el sitio de entrenamiento.</a>','ESN','--ANY--','--ANY--','�<d�T�LT�k|w���','2014-03-22 20:33:38.117');
INSERT INTO `client_messagetranslation` VALUES (2833,'AIR','E pani i ka mana?o','HAW','--ANY--','--ANY--','�I���I��	����ȑ','2013-12-18 14:09:06.703');
INSERT INTO `client_messagetranslation` VALUES (2650,'AIR','E lele','HAW','--ANY--','--ANY--','�]^�/J���\\)�%�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2719,'AIR','Uliuli Halakea','HAW','--ANY--','--ANY--','�aж��J�ֲ�brr','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2707,'AIR','Ka \'Olelo Pelekania','HAW','--ANY--','--ANY--','�v�kM<�����)','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3608,'AIR','Ecuación','ESN','--ANY--','--ANY--','��oCF>����&�L�','2014-03-22 20:36:39.450');
INSERT INTO `client_messagetranslation` VALUES (2541,'SBAC','Terminar prueba','ESN','--ANY--','--ANY--','��D��~H����\Z��L','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2378,'AIR','‘A‘ole hiki ke ho‘ouna ‘ia ka ‘ikepili ma ka pūnaewele. E kaomi i ka [‘Ae] no ka hana hou ‘ana a i ‘ole e kaomi i ka [‘A‘ole] no ka lele ‘ana i kāu hō‘ike.','HAW','--ANY--','--ANY--','���u�M^��=�~xw\\','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2611,'AIR','Reproducir','ESN','--ANY--','--ANY--','��w���F��Һ=@I5�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2677,'AIR','Ke pa\'i \'ia nei kau ho\'ike ho\'oma\'ama\'a …','HAW','--ANY--','--ANY--','���#�Bƞumd�0�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3268,'AIR','Pono e ?apono ?ia ka ?apana no ka ha?alele ?ana','HAW','--ANY--','--ANY--','��+�>M��f�SR','2013-12-18 14:09:07.067');
INSERT INTO `client_messagetranslation` VALUES (2501,'AIR','Revise la siguiente información. Si la información es correcta, haga clic en [Sí], de lo contrario, haga clic en [No].','ESN','--ANY--','--ANY--','���㏟BZ���+�c*','2014-03-22 20:33:38.117');
INSERT INTO `client_messagetranslation` VALUES (3327,'AIR','Na kula a pau','HAW','--ANY--','--ANY--','���)dM\"�T@�B�?','2013-12-18 14:09:07.137');
INSERT INTO `client_messagetranslation` VALUES (3158,'AIR','No se puede completar la selección de la prueba','ESN','--ANY--','--ANY--','� �[��A\r��p��>�','2014-03-22 20:37:20.653');
INSERT INTO `client_messagetranslation` VALUES (3078,'AIR','Hiki ?ole ke loa?a na ?ikepili ho?ike ma ke kahua RTS','HAW','--ANY--','--ANY--','�2\\��L��K��9$�\r','2013-12-18 14:09:06.890');
INSERT INTO `client_messagetranslation` VALUES (2534,'AIR','Imprimir','ESN','--ANY--','--ANY--','�<C�d@%��\r}V!','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2447,'AIR','<span>Problema (No)</span>','ESN','--ANY--','--ANY--','��$ս�G�*P\'`�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3049,'AIR','No se encuentra la base de datos RTS','ESN','--ANY--','--ANY--','�ډ2G�G$�a%�l<�','2014-03-22 20:35:32.320');
INSERT INTO `client_messagetranslation` VALUES (2550,'AIR','Haga clic aquí para continuar con la prueba.','ESN','--ANY--','--ANY--','��*�)�NƸ��[�a�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2509,'SBAC_PT','No score is provided for this test. You may now log out.','ENU','--ANY--','--ANY--','�\0%���MN�[9���o','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2675,'AIR','E Pa\'i i ka Mo\'olelo','HAW','--ANY--','--ANY--','�r�B�Mq���WSQϽ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2472,'AIR','<span>E Kāpae</span>','HAW','--ANY--','--ANY--','�2�+5�Bd����[}','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2599,'AIR','Diga la opción E','ESN','--ANY--','--ANY--','���A�A���!ێG��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2469,'AIR','Na \'Okuhi No Ka Ho\'ike A Me Ke Kokua','HAW','--ANY--','--ANY--','��|U��D���H����f','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2846,'AIR','Waiho i mana?o no keia ?ikamu pono no na ho?ike.','HAW','--ANY--','--ANY--','����neK�=�{d`<\"','2013-12-18 14:09:06.710');
INSERT INTO `client_messagetranslation` VALUES (3157,'AIR','Hiki ?ole ke ho?ala ?ia keia ?apana nei','HAW','--ANY--','--ANY--','�mK���LI�S}�(=G�','2013-12-18 14:09:06.963');
INSERT INTO `client_messagetranslation` VALUES (2787,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','�u��@������|��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3207,'AIR','<p>No na TA wale no keia kahua nei, a ?a?ole hiki ke ho?ohana ?ia me na hana ho?ike.Hiki ke ho?ohana ?ia no keia mau mea nei:1) I wahi ho?oma?ama?a no na kahu ha?awi ho?ike punaewele puni honua ma na kahua. 2) No ka ha?awi ?ana i na ho?ike. </p>','HAW','--ANY--','--ANY--','㘫�H�KǍa}���v','2013-12-18 14:09:07.000');
INSERT INTO `client_messagetranslation` VALUES (3620,'AIR','Mai ha?alele i keia ?apana a pau na ninau a pau i ka pane ?ia.','HAW','--ANY--','--ANY--','��~n-D=��I�ڙ�','2013-12-18 14:09:07.503');
INSERT INTO `client_messagetranslation` VALUES (2482,'AIR','Kū I Ke ʻOki Leo ʻAna','HAW','--ANY--','--ANY--','���7O�O��CJ��`�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2712,'AIR','Ninguno','ESN','--ANY--','--ANY--','��{��N���CP�ļ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2529,'AIR','E ho\'okokoke','HAW','--ANY--','--ANY--','��G�I��ڭ�_b�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2697,'AIR','Exclusión de tipos de items','ESN','--ANY--','--ANY--','��y���IO� ��s��6','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3706,'AIR','{silence}OpciÃ³n A.{silence}','HAW','--ANY--','--ANY--','�:5͛HHT�Ex���','2013-12-18 14:09:07.630');
INSERT INTO `client_messagetranslation` VALUES (3694,'AIR','Papa wehewehe ?olelo Paniolo','HAW','--ANY--','--ANY--','�t\Z1ڳ@��CS_l','2013-12-18 14:09:07.617');
INSERT INTO `client_messagetranslation` VALUES (2976,'AIR','Ho?ole ?ia ka ?e?e ?ana','HAW','--ANY--','--ANY--','�u���GQ��W/?��','2013-12-18 14:09:06.800');
INSERT INTO `client_messagetranslation` VALUES (2521,'AIR','O keia kau helu\'ai:','HAW','--ANY--','--ANY--','��1@H�Py�jz\r�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3256,'AIR','Kahiauli','HAW','--ANY--','--ANY--','�K��FP�w,7<�','2013-12-18 14:09:07.047');
INSERT INTO `client_messagetranslation` VALUES (3405,'AIR','../Projects/Delaware/Help/help_esn.html','ESN','--ANY--','--ANY--','��E�KFh����I�M�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2586,'AIR','E ʻōlelo i ka nīnau a me ke koho','HAW','--ANY--','--ANY--','��\n�M;�^�d�<{','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3641,'AIR','Navegador:','ESN','--ANY--','--ANY--','��#$|�Mw�w\r�U��','2014-03-22 20:36:39.630');
INSERT INTO `client_messagetranslation` VALUES (2694,'AIR','Fórmula','ESN','--ANY--','--ANY--','��˗�8@3�#܌��Y�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3332,'AIR','<h3>?Anu?u 1:Koho i ka palapala ho?ike no ke kukulu ?ana</h3>','HAW','--ANY--','--ANY--','�M�#Hï���]<�','2013-12-18 14:09:07.140');
INSERT INTO `client_messagetranslation` VALUES (3056,'AIR','Hiki ?ole ke loa?a na ?ikepili ho?ike ma ke kahua RTS','HAW','--ANY--','--ANY--','�[�c!�F �Z�TZ��','2013-12-18 14:09:06.880');
INSERT INTO `client_messagetranslation` VALUES (3421,'AIR','<span>Sí, comenzar la prueba</span>','ESN','--ANY--','--ANY--','�v� r�LO��L���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2742,'AIR','Ninguno','ESN','--ANY--','--ANY--','���B������j�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3127,'AIR','La oportunidad de prueba está en curso.','ESN','--ANY--','--ANY--','���;�K϶X5�*Os','2014-03-22 20:37:20.647');
INSERT INTO `client_messagetranslation` VALUES (2529,'AIR','Acercar','ESN','--ANY--','--ANY--','廆T�3O���Ѷ �','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2408,'AIR','E Koho I Ka Papa','HAW','--ANY--','--ANY--','���S1G�U�����k','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2577,'AIR','E \'olu\'olu, e kali, ke ho\'ouka \'ia nei...','HAW','--ANY--','--ANY--','��Z2�DHp�#��c��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2693,'AIR','Tipo de fuente','ESN','--ANY--','--ANY--','�����E؈Gk|]r��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2866,'AIR','Waiho i mana?o no keia ?ikamu pono no na ho?ike.','HAW','--ANY--','--ANY--','�d�M����?,t/�','2013-12-18 14:09:06.723');
INSERT INTO `client_messagetranslation` VALUES (2804,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','�)\\=~EM�����ت��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3052,'AIR','Hiki ?ole ke loa?a na ?ikepili ho?ike ma ke kahua RTS','HAW','--ANY--','--ANY--','�5i��~OJ���M�[�','2013-12-18 14:09:06.873');
INSERT INTO `client_messagetranslation` VALUES (2818,'AIR','Cerrar Comentario','ESN','--ANY--','--ANY--','�6u�&�N�������}','2014-03-22 20:33:38.090');
INSERT INTO `client_messagetranslation` VALUES (3014,'AIR','Usted ha llegado al final de este segmento:','ESN','--ANY--','--ANY--','�QN���F��u;�!3','2014-03-22 20:35:11.283');
INSERT INTO `client_messagetranslation` VALUES (3648,'AIR','Kau waihona ?onaehana:','HAW','--ANY--','--ANY--','��\rQM׽<\'S[E]j','2013-12-18 14:09:07.547');
INSERT INTO `client_messagetranslation` VALUES (3124,'AIR','?A?ohe ou kuleana i keia wa ho?ohana nei.','HAW','--ANY--','--ANY--','榧G_dK��;��%Bʢ','2013-12-18 14:09:06.943');
INSERT INTO `client_messagetranslation` VALUES (2988,'AIR','{silence}Option F.{silence}','ESN','--ANY--','--ANY--','�\"Z4�`Cƌ񒮦�m','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2914,'AIR','E \'olu\'olu, e kali.  Ke ki\'i \'ia nei ka helu \'ai ho\'ike.','HAW','--ANY--','--ANY--','�1�@I+�Q�i\"�NI','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3266,'AIR','Na ?ikemu ho?ike no na haumana na kokua a?o','HAW','--ANY--','--ANY--','�@[�gHC��\ZL','2013-12-18 14:09:07.063');
INSERT INTO `client_messagetranslation` VALUES (2662,'AIR','Imprimir item','ESN','--ANY--','--ANY--','�X;��E�s�1a�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2656,'AIR','Agregar punto','ESN','--ANY--','--ANY--','�G�}qC��C���3','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3652,'AIR','Koho i ka ho?ike:','HAW','--ANY--','--ANY--','�il��K8��\';�T�','2013-12-18 14:09:07.553');
INSERT INTO `client_messagetranslation` VALUES (2626,'SBAC','Seleccione el objeto que desee eliminar.','ESN','--ANY--','--ANY--','��|��Eu��{��\0ʩ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2681,'AIR','E Holoi I ka \'Ikamu I Kaha \'Ia no Ka Nana Hou','HAW','--ANY--','--ANY--','�\0$qs<M\0��_����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3407,'AIR','No puede iniciar sesión con este navegador. Use el navegador seguro para realizar esta prueba.','ESN','--ANY--','--ANY--','�!0ZvB��YA\"}�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2631,'AIR','Libere el botón del mouse para soltarlo donde desee colocarlo.','ESN','--ANY--','--ANY--','�\"����E���;s]	\0G','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3460,'AIR','Kokua','HAW','--ANY--','--ANY--','�\'8W�~@��7��D�','2013-12-18 14:09:07.277');
INSERT INTO `client_messagetranslation` VALUES (2753,'AIR','Ejercicios de la grilla','ESN','--ANY--','--ANY--','�H��OB�|�D�ӎb','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2677,'AIR','Se está imprimiendo la prueba de práctica…','ESN','--ANY--','--ANY--','�Q��M�����X�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2870,'AIR','Imprimir item','ESN','--ANY--','--ANY--','�R���N���<ǌ���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2641,'SBAC','Skip this audio check','ENU','--ANY--','--ANY--','�v���L+��_����','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3680,'AIR','Pio na ha?awina ukali','HAW','--ANY--','--ANY--','�]M�xJ-�:�&��','2013-12-18 14:09:07.597');
INSERT INTO `client_messagetranslation` VALUES (3132,'AIR','?A?ole kako?o ?ia keia ho?ike ma keia wa ho?ohana.','HAW','--ANY--','--ANY--','����L�8�$�','2013-12-18 14:09:06.957');
INSERT INTO `client_messagetranslation` VALUES (2984,'AIR','{silence}Koho B.{silence}','HAW','--ANY--','--ANY--','��xVO>�|��N�!','2013-12-18 14:09:06.813');
INSERT INTO `client_messagetranslation` VALUES (2359,'AIR','Calculadora','ESN','--ANY--','--ANY--','��Kک�����i','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2598,'AIR','Opción oral D (en inglés)','ESN','--ANY--','--ANY--','���Fۓƺt�a','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3637,'SBAC','<p>This page allows you to check the <strong>current</strong> bandwidth of your network. The following operating systems and web browsers are supported: </p>  <ul>  <li>Windows XP, Vista, 7, 8.0, 8.1: Mozilla Firefox</li>  <li>Mac OS 10.4.4&#8211;10.9: Firefox</li>  <li>Linux Fedora Core 6 or 16; Ubuntu 9&#8211;11.10: Firefox</li>    <li>Apple tablets (iPad) running iOS 6.0-7.0: Safari</li>  <li>Android tablets running 4.0.4-4.2: Google Chrome</li> <li>Chromeos 31+: Google Chrome</li> </ul>       <p>To determine your bandwidth, select a test from the drop-down list and enter the maximum number of students likely to test at one time, then click [Run Network Diagnostics Tests].  </p>     <p>The [<strong>Text-to-Speech Check</strong>] is for schools who will be administering the Field test and requires the use of the secure browser. The secure browser is available from <a target=\"_blank\" href=\"http://sbac.portal.airast.org\">http://sbac.portal.airast.org</a>.  </p>','ENU','--ANY--','--ANY--','����fCi��	\'��','2014-03-11 12:35:15.630');
INSERT INTO `client_messagetranslation` VALUES (2654,'AIR','Texto a voz no está disponible en el navegador y / o la plataforma que está utilizando. Por favor, asegúrese de que está utilizando un navegador seguro apoyado, o un inicio de sesión seguro de Chromebooks.','ESN','--ANY--','--ANY--','���GEz�X�4��]','2014-03-22 20:33:38.133');
INSERT INTO `client_messagetranslation` VALUES (3349,'AIR','Koho i ke kula','HAW','--ANY--','--ANY--','���\"�E��i�a���7','2013-12-18 14:09:07.153');
INSERT INTO `client_messagetranslation` VALUES (2895,'AIR','E kaomi no ka pani \'ana','HAW','--ANY--','--ANY--','��u�l�Hh��DS���z','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3560,'AIR','Ho?oku','HAW','--ANY--','--ANY--','����Hg�es5�\\yZ','2013-12-18 14:09:07.430');
INSERT INTO `client_messagetranslation` VALUES (2622,'AIR','E koho \'oe i kahi no na kiko.','HAW','--ANY--','--ANY--','�=V:@s�݆8/��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3015,'AIR','<p>Por favor, revise sus respuestas antes de continuar la prueba. Usted no será capaz de volver a estas preguntas después.<p>Haga clic en el número de la pregunta a la izquierda para revisarla.<p>','ESN','--ANY--','--ANY--','�(5��}G�D69���?','2014-03-22 20:35:11.287');
INSERT INTO `client_messagetranslation` VALUES (3656,'AIR','Resultados de descarga:','ESN','--ANY--','--ANY--','�7`ŭ�K5��Ξ@','2014-03-22 20:36:39.743');
INSERT INTO `client_messagetranslation` VALUES (3122,'AIR','La aprobación de este estudiante ya no está pendiente.','ESN','--ANY--','--ANY--','�oG�;5K�?���A��','2014-03-22 20:35:48.660');
INSERT INTO `client_messagetranslation` VALUES (3257,'AIR','Na ?ano pale ?ikemu','HAW','--ANY--','--ANY--','��8~�Ex��e/�T�','2013-12-18 14:09:07.050');
INSERT INTO `client_messagetranslation` VALUES (3402,'AIR','Ho?opau i ka ?oki ?ana o ka pa?ani hou','HAW','--ANY--','--ANY--','�c��tG�[Ƿ�?�	','2013-12-18 14:09:07.187');
INSERT INTO `client_messagetranslation` VALUES (2559,'AIR','Ka Puke Alaka\'i Kokua','HAW','--ANY--','--ANY--','��\'�@w�CR�X\0��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2498,'SBAC','<p>Welcome to the Smarter Balanced Assessments Student Testing Site!</p>  <ol type=\"1\"><li>Please enter your first name, State-SSID and the Session ID in the fields above. Your Test Administrator will give you the Session ID.</li><li>Click [Sign In] to continue.</li></ol><hr />','ENU','--ANY--','--ANY--','��/C�DȈ��X;X','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2679,'AIR','E Koho I Hopuna\'ōlelo A E Ho\'olohe I Ka Leo','HAW','--ANY--','--ANY--','����6�Bׄ\'a֧:��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2670,'AIR','¿Está seguro de que desea finalizar la prueba?','ESN','--ANY--','--ANY--','�ÛLF��Ί���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3013,'AIR','DE EOC2 Math','HAW','--ANY--','--ANY--','�˨H=��h��^͇','2013-12-18 14:09:06.837');
INSERT INTO `client_messagetranslation` VALUES (2658,'AIR','E Kaha Nahau','HAW','--ANY--','--ANY--','��v3�I��û1��9','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3657,'AIR','Resultados de cargue:','ESN','--ANY--','--ANY--','��ܩ*@ܮք����','2014-03-22 20:36:39.753');
INSERT INTO `client_messagetranslation` VALUES (3481,'AIR','Pae','HAW','--ANY--','--ANY--','�NZ�DA~�ī�-}�O','2013-12-18 14:09:07.313');
INSERT INTO `client_messagetranslation` VALUES (3304,'AIR','E ho?ouna ?ia ana keia ?olea memo i na kahu ha?awi ho?ike a pau me na wa ho?ohana e ?a nei.Makemake a nei ?oe e ho?omau aku?','HAW','--ANY--','--ANY--','�.8�#C��WU�G�p�','2013-12-18 14:09:07.120');
INSERT INTO `client_messagetranslation` VALUES (2549,'AIR','Haga clic aquí para imprimir el pasaje.','ESN','--ANY--','--ANY--','�2��@�Y�/�MH�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3008,'AIR','E Waiho','HAW','--ANY--','--ANY--','�H�dD��k�i��x','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2919,'AIR','E Waiho','HAW','--ANY--','--ANY--','�N�\n�XK\0���JX��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3231,'SBAC','<h2>Smarter Balanced Assessments Secure Test Material</h2>  <br />  <p><strong>This printout contains secure test content and must be immediately and securely recycled on-site by authorized school staff.</strong></p><p>Do not photocopy, enlarge, digitize, or retain printouts of test content. Such actions constitute a severe test security violation and must be reported to Smarter Balanced.</p><p>In accordance with the Family Educational Rights and Privacy Act (FERPA), disclosure of personally identifiable information protected by privacy laws is prohibited. Please securely destroy all printed test content immediately after the end of this testing session.</p>','ENU','--ANY--','--ANY--','�ZG��\'I딼�g���','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2642,'AIR','Diagnósticos del texto para decir en español','ESN','--ANY--','--ANY--','ꄅ��F���Є\0�h','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3600,'AIR','?A?ole pono ka ?apono ?ia ?ana no ka hana ?ana i keia ho?ike nei.Ua kapae paha kekahi haumana i kana noi.','HAW','--ANY--','--ANY--','ꔫ�\Z�J̟$�߱��','2013-12-18 14:09:07.467');
INSERT INTO `client_messagetranslation` VALUES (2802,'AIR','Cambiar <br/>ejercicio','ESN','--ANY--','--ANY--','�̯RrF0�_D0k��C','2014-03-22 20:33:38.087');
INSERT INTO `client_messagetranslation` VALUES (2723,'AIR','Verde claro','ESN','--ANY--','--ANY--','괎�9�@����04��	','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3650,'AIR','Polokalamu punaewele puni honua kapu:','HAW','--ANY--','--ANY--','���jC�����Z�(','2013-12-18 14:09:07.550');
INSERT INTO `client_messagetranslation` VALUES (2985,'AIR','{silence}Koho C.{silence}','HAW','--ANY--','--ANY--','��r�KI;�x�k�e�','2013-12-18 14:09:06.817');
INSERT INTO `client_messagetranslation` VALUES (2421,'AIR','Ke kali nei i ka \'apono \'ana o ke kumu ho\'ike...','HAW','--ANY--','--ANY--','�p*T�A�2��a��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2464,'AIR','<span>Sí, comenzar la prueba</span>','ESN','--ANY--','--ANY--','�O���H��V���lQ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3038,'AIR','ʻAʻohe nīnau ma kēia hōʻike.','HAW','--ANY--','--ANY--','�\Z=��Hx���M�X�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3069,'AIR','Ke hoʻāʻo nei e hoʻokomo ʻikamu ma ʻō aku o kahi hope loa','HAW','--ANY--','--ANY--','���~I���Aߢ�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2593,'AIR','Diga la opción B','ESN','--ANY--','--ANY--','�Ӟ�?�G���_\ZU��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2705,'AIR','Comentarios del estudiante','ESN','--ANY--','--ANY--','��#��F�%Pq�_� ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3418,'AIR','Pruebas','ESN','--ANY--','--ANY--','����Lɣ?#�B�Z','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2825,'AIR','Imprimir item','ESN','--ANY--','--ANY--','����L�+�v��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2686,'AIR','Ke ho\'omaka \'ia nei','HAW','--ANY--','--ANY--','�v�<d�K_��:�&\Z\0�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2656,'AIR','Kiko','HAW','--ANY--','--ANY--','��Y`VKP���\rchZ�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3643,'AIR','Versión:','ESN','--ANY--','--ANY--','��#�qE\r�\Z�N�	�','2014-03-22 20:36:39.643');
INSERT INTO `client_messagetranslation` VALUES (2643,'AIR','¿Escuchaste el audio en español? Haz clic en [Sí] o [No].','ESN','--ANY--','--ANY--','�haF��d���\'��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3133,'AIR','Se produjo un problema con el sistema.  Inténtelo nuevamente.','ESN','--ANY--','--ANY--','�m��G!�\0������','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2399,'AIR','O \'Oe Anei Keia?','HAW','--ANY--','--ANY--','���>VJ�pg��ń','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2358,'AIR','../Projects/AIR/Help/help.html','ENU','--ANY--','--ANY--','�f��Gl�zlb[h�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (3024,'AIR','Su equipo tiene \"Espacios\" habilitados. Esta función debe ser desactivada antes de iniciar la sesión Por favor, consulte con su administrador de pruebas para obtener ayuda.','ESN','--ANY--','--ANY--','�NJ�@G���=p<�E','2014-03-22 20:35:11.293');
INSERT INTO `client_messagetranslation` VALUES (2956,'SBAC','cuaderno','ESN','--ANY--','--ANY--','�e��V@I̞D1�V�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3002,'SBAC','Save and Close','ENU','--ANY--','--ANY--','�k\\�o�Cu������f�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2645,'AIR','Texto en español a ser leido en voz alta','ESN','--ANY--','--ANY--','�x�/�O*�f���T','2014-03-22 20:33:38.130');
INSERT INTO `client_messagetranslation` VALUES (3616,'AIR','No hay oportunidades disponibles en esta ventana de pruebas.','ESN','--ANY--','--ANY--','�z6�1J��c�R��','2014-03-22 20:36:39.490');
INSERT INTO `client_messagetranslation` VALUES (3128,'AIR','La aprobación de este estudiante ya no está pendiente.','ESN','--ANY--','--ANY--','��wJ;�%��G�q','2014-03-22 20:37:20.647');
INSERT INTO `client_messagetranslation` VALUES (2406,'AIR','<span>ʻAʻole</span>','HAW','--ANY--','--ANY--','�Y�WDn��9Pg�3o','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2786,'AIR','E waiho i mana?o no keia ?ikemu pono ho?ike.','HAW','--ANY--','--ANY--','�G\"��F��)Q0�\\�','2013-12-18 14:09:06.660');
INSERT INTO `client_messagetranslation` VALUES (2449,'AIR','Avísele al administrador de la prueba que tiene un problema de audio. Debe reproducir y grabar para esta prueba.','ESN','--ANY--','--ANY--','�j��c\rHn����UD','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3504,'AIR','Ka noi','HAW','--ANY--','--ANY--','ͥlNӝ���6Ҁ,','2013-12-18 14:09:07.347');
INSERT INTO `client_messagetranslation` VALUES (3216,'AIR','?A?ohe haumana i kohu me ka SSID au i ho?okomo aku ai.','HAW','--ANY--','--ANY--','�\0����L��V_죢d�','2013-12-18 14:09:07.010');
INSERT INTO `client_messagetranslation` VALUES (3054,'AIR','Ho?ole ?ia ka loiloi ?ana o ka mea ho?ohana i na ana ho?ike','HAW','--ANY--','--ANY--','�����LΛf���.�','2013-12-18 14:09:06.877');
INSERT INTO `client_messagetranslation` VALUES (2494,'AIR','E kahawaena','HAW','--ANY--','--ANY--','�:}̿E�a�j.�T','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2851,'AIR','Someter un comentario para este ítem.','ESN','--ANY--','--ANY--','�_�T�J{����Z','2014-03-22 20:34:06.470');
INSERT INTO `client_messagetranslation` VALUES (3006,'AIR','Tabla Periódica','ESN','--ANY--','--ANY--','�sX��J�]����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2668,'AIR','La prueba se ha finalizado y está lista para entregarse.','ESN','--ANY--','--ANY--','���8 H[��Y�q�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2358,'SBAC_PT','../Projects/SBAC/Help/help_esn.html','ESN','--ANY--','--ANY--','� ���O(�h���$]\n','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2547,'SBAC','Your test is printing …','ENU','--ANY--','--ANY--','�r�?UIﴭ\r?\Zu�','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2473,'AIR','<span>Comenzar la prueba ahora</span>','ESN','--ANY--','--ANY--','�\Z~��FHt��vg�u�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2904,'AIR','Comenzar la grabación','ESN','--ANY--','--ANY--','�>���rA٦`ɘ��u!','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2439,'AIR','<span>Sí</span>','ESN','--ANY--','--ANY--','�S�I�F���*\0>�)','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2936,'AIR','Na lula ?oihana TTX','HAW','--ANY--','--ANY--','�w{2=�O�lZK���7','2013-12-18 14:09:06.747');
INSERT INTO `client_messagetranslation` VALUES (2812,'AIR','Waiho i mana?o no keia ?ikamu pono no na ho?ike.','HAW','--ANY--','--ANY--','��	ΤVD3�F���c��','2013-12-18 14:09:06.687');
INSERT INTO `client_messagetranslation` VALUES (2576,'AIR','Atención','ESN','--ANY--','--ANY--','������L2������T�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2950,'AIR','No se aplicó ningún zoom predeterminado','ESN','--ANY--','--ANY--','���K4L���!G�ф','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3285,'AIR','SSID','HAW','--ANY--','--ANY--','����I��h���Z)','2013-12-18 14:09:07.093');
INSERT INTO `client_messagetranslation` VALUES (2646,'AIR','¿Escuchaste la voz? Haz clic en [Sí] o [No].','ESN','--ANY--','--ANY--','����H(�V+���','2014-03-22 20:33:38.130');
INSERT INTO `client_messagetranslation` VALUES (2624,'AIR','E koho i 2 kiko no ka ho\'ohui \'ana me ka nahau.','HAW','--ANY--','--ANY--','���wB���#w��qc','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3083,'AIR','Usuario no tiene un rol asignado para acceder','ESN','--ANY--','--ANY--','�;��M�\0��#�@','2014-03-22 20:35:32.337');
INSERT INTO `client_messagetranslation` VALUES (2396,'AIR','Se instaló el navegador seguro incorrecto en esta computadora. Consulte con el administrador de la prueba. Por ahora, debe usar otra computadora.','ESN','--ANY--','--ANY--','�U��g|F͒� ��B�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2751,'AIR','\'A\'OLE POLOLEI','HAW','--ANY--','--ANY--','�u�Xj�FW�@���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3667,'AIR','Nana hou i ka TTS','HAW','--ANY--','--ANY--','��ΉLӟ�R�޳2','2013-12-18 14:09:07.580');
INSERT INTO `client_messagetranslation` VALUES (2411,'AIR','Configuración de la prueba (opcional):','ESN','--ANY--','--ANY--','��^IID��\\k&�S','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2882,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','���g$�Hߕ��9��i','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3183,'AIR','E ho?oku ?ia an aka wa ho?ohana ho?ike ina hana ?ia keia a e ho?oku pauloa ?ia an aka ho?ike a na haumana a pau ma keia wa ho?ohana.E ho?oha?alele ?ia pu ana keia haumana nei.Makemake anei ?oe e hana i keia?','HAW','--ANY--','--ANY--','�\"@��H@����F','2013-12-18 14:09:06.983');
INSERT INTO `client_messagetranslation` VALUES (2659,'AIR','Comenzar prueba','ESN','--ANY--','--ANY--','�/�J^��%-g�J','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2908,'AIR','Pautas orales de escritura (en inglés)','ESN','--ANY--','--ANY--','�3�z�Bڵ5���Q�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2495,'AIR','¿Está seguro de que desea cambiar el tema que seleccionó anteriormente? <Sí> <No>','ESN','--ANY--','--ANY--','�9�H��BM��Cٕ��C','2014-03-22 20:33:38.113');
INSERT INTO `client_messagetranslation` VALUES (2891,'AIR','Pane','HAW','--ANY--','--ANY--','�C�;��Hĉ���r}�w','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2647,'AIR','E lele i ke kani ma ka ‘ōlelo Paniolo.','HAW','--ANY--','--ANY--','�N�H@.�p!�α�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2520,'AIR','Ua pau ka ho\'ike ma ka la:','HAW','--ANY--','--ANY--','��lu�K��N�d*��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2625,'SBAC','Seleccione 2 puntos para conectar con una flecha doble.','ESN','--ANY--','--ANY--','�n��JC��������','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2657,'AIR','Conectar línea','ESN','--ANY--','--ANY--','����Bͬ#6̄~','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3125,'AIR','?A?ole kako?o ?ia keia ho?ike ma keia wa ho?ohana.','HAW','--ANY--','--ANY--','��</�A%�3j��}+�','2013-12-18 14:09:06.947');
INSERT INTO `client_messagetranslation` VALUES (2638,'AIR','Seleccione el botón verde para probar la configuración de texto a voz. <br /> Usted debe escuchar la siguiente frase: \"Este texto está leído en voz alta.\" <br /> Haga clic en [Sí, escuché la voz] si funciona. Si no funciona, haga clic en [No, no escuché la voz].','ESN','--ANY--','--ANY--','���Q�Lw��#_1UMB','2014-03-22 20:33:38.130');
INSERT INTO `client_messagetranslation` VALUES (3642,'AIR','?Ano:','HAW','--ANY--','--ANY--','���1��FⷢPb*S','2013-12-18 14:09:07.537');
INSERT INTO `client_messagetranslation` VALUES (2809,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','�����Kd�ܷ_�,��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3016,'AIR','Na ninau:','HAW','--ANY--','--ANY--','�e����Oe�Ԇ �ZF�','2013-12-18 14:09:06.840');
INSERT INTO `client_messagetranslation` VALUES (2737,'AIR','HI 7 Makemakika','HAW','--ANY--','--ANY--','��|KM��?�cw��','2013-12-18 14:09:06.647');
INSERT INTO `client_messagetranslation` VALUES (3045,'AIR','No se encuentra la base de datos RTS','ESN','--ANY--','--ANY--','�FVy�O)�Ex���Rf','2014-03-22 20:35:11.300');
INSERT INTO `client_messagetranslation` VALUES (2808,'AIR','E pani i ka mana?o','HAW','--ANY--','--ANY--','�4|+K�D救�����','2013-12-18 14:09:06.687');
INSERT INTO `client_messagetranslation` VALUES (3669,'AIR','Su solicitud de  continuar con el siguiente segmento ha sido rechazada. Ahora se cerrará la sesión.','ESN','--ANY--','--ANY--','�?/�O\0����\0 05','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3302,'AIR','?Olu?olu e waiho i mana?o iloko o ka pahu memo','HAW','--ANY--','--ANY--','�?���O��7��pi�','2013-12-18 14:09:07.117');
INSERT INTO `client_messagetranslation` VALUES (2476,'AIR','Hoʻohui Haumāna ','HAW','--ANY--','--ANY--','�I��|�M\\�	S����','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3493,'AIR','Ho?i hou','HAW','--ANY--','--ANY--','�N��^K���ZCM�','2013-12-18 14:09:07.330');
INSERT INTO `client_messagetranslation` VALUES (2961,'AIR','A202','HAW','--ANY--','--ANY--','����\nG���� �F','2013-12-18 14:09:06.780');
INSERT INTO `client_messagetranslation` VALUES (3707,'AIR','{silence}OpciÃ³n B.{silence}','HAW','--ANY--','--ANY--','�\"3�Ov����o:�M','2013-12-18 14:09:07.633');
INSERT INTO `client_messagetranslation` VALUES (2854,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','��.�TG|�%j��;�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2531,'AIR','Ka Ha\'ilula','HAW','--ANY--','--ANY--','����k�H���?�8h','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2509,'AIR','‘A‘ohe helu ‘ai no kāu hō‘ike i kēia manawa. E ‘olu‘olu, e noi i kāu kumu.','HAW','--ANY--','--ANY--','��g�6KO�1ɰBf��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2872,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','��O�HV�6��	?Ό','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2922,'AIR','<span class=\"noTTS\">ʻAʻohe Kākau-i-ka-ʻŌlelo Wahe</span>','HAW','--ANY--','--ANY--','�)��$MCی̋ј���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2820,'AIR','Imprimir item','ESN','--ANY--','--ANY--','�^���J��[3G?\n�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2478,'AIR','Cerrar','ESN','--ANY--','--ANY--','�m0Ǜ3B��<��7��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3437,'AIR','Are you sure you want to pause?','ESN','--ANY--','--ANY--','�v<|�wA�����]�B','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3116,'AIR','Hewa ka ID ana ho?ike.','HAW','--ANY--','--ANY--','�H�!�C���M�����','2013-12-18 14:09:06.933');
INSERT INTO `client_messagetranslation` VALUES (3047,'AIR','Usuario no tiene un rol asignado para acceder','ESN','--ANY--','--ANY--','��}���CӪ3|n��S','2014-03-22 20:35:32.317');
INSERT INTO `client_messagetranslation` VALUES (2477,'AIR','Guía de ayuda','ESN','--ANY--','--ANY--','��s�^\'KҞ�+\".�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2505,'AIR','Seleccione la prueba que desea realizar.','ESN','--ANY--','--ANY--','��G�~@�r�G]�\"�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3692,'AIR','Pale waiho mana?o','HAW','--ANY--','--ANY--','�#�x��@��l�M��a�','2013-12-18 14:09:07.613');
INSERT INTO `client_messagetranslation` VALUES (3017,'AIR','(Nota: Las estimaciones de rendimiento incluyen la sobrecarga de cifrado/descifrado para la transferencia de datos. Las estimaciones de rendimiento cambian a medida que las condiciones de la red cambian y pueden variar de una ejecución a otra.)','ESN','--ANY--','--ANY--','�)���D��-��e�','2014-03-22 20:35:11.287');
INSERT INTO `client_messagetranslation` VALUES (2906,'AIR','E hana hou','HAW','--ANY--','--ANY--','�H�EM��>iv','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2384,'AIR','Ha respondido todas las preguntas.  Cuando termine de revisar las preguntas, presione [Terminar prueba].','ESN','--ANY--','--ANY--','�R���K��r�!\"u','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2475,'AIR','Ke Kokua','HAW','--ANY--','--ANY--','�j���J͉�Lٝi�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2376,'AIR','La página no se cargó.  Consulte con el administrador de la prueba.','ESN','--ANY--','--ANY--','�wМPmD��~���O��','2014-03-22 20:33:38.097');
INSERT INTO `client_messagetranslation` VALUES (2602,'AIR','E ʻŌlelo I Ke Koho F (Ma Ka Namu Haole)','HAW','--ANY--','--ANY--','�x�@y�D߶ݛd��e�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2879,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','�~E�,0HС����5f�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2538,'AIR','E Ho\'omaha','HAW','--ANY--','--ANY--','����FJ��QB��X�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2784,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','��	ݢ�I�{9�(��|','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2392,'AIR','Ka Helu Haumana:','HAW','--ANY--','--ANY--','�����F��d�?���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3110,'AIR','No se puede crear un identificador de informes único.','ESN','--ANY--','--ANY--','�̕\r��K2����$�	�','2014-03-22 20:35:48.650');
INSERT INTO `client_messagetranslation` VALUES (2770,'AIR','ʻAʻohe','HAW','--ANY--','--ANY--','��m��GOη�k�*J','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3181,'AIR','Ke ha?alele nei ?oe i na kahua ho?ike no na hauma pau.E ho?oku ?ia an aka ho?ike a me ka wa ho?ohana a e ho?oku ?ia ana na ho?ike a pau.E kaomi i ka pihi [Ho?opau] no ka ho?omau ?ana ma keia kahua nei a i ?ole e kaomi i ka pihi [Ha?alele] no ka ha?alele ?ana i na kahua a pau.','HAW','--ANY--','--ANY--','��L�WJu�C�X��','2013-12-18 14:09:06.980');
INSERT INTO `client_messagetranslation` VALUES (2835,'AIR','Imprimir item','ESN','--ANY--','--ANY--','�#���MI�;��\"\n֝','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2363,'AIR','E kikokiko i kau Helu Haumana.','HAW','--ANY--','--ANY--','� �rCJ�0�7|6��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3000,'AIR','E ho?omaka i ka wala?au ?ana','HAW','--ANY--','--ANY--','�\"��X�H��M�\09�c2','2013-12-18 14:09:06.823');
INSERT INTO `client_messagetranslation` VALUES (3631,'AIR','Utilizar los valores predeterminados del sistema','ESN','--ANY--','--ANY--','�>R��;K��1','2014-03-22 20:36:39.563');
INSERT INTO `client_messagetranslation` VALUES (3136,'AIR','Se produjo un problema con el sistema.  Inténtelo nuevamente.','ESN','--ANY--','--ANY--','�I�f�D2�λ\r��B','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3338,'AIR','<p>?A?ole i kau palena ?ia keia mau memo.</p>','HAW','--ANY--','--ANY--','��®Y�FY��u`l ��','2013-12-18 14:09:07.147');
INSERT INTO `client_messagetranslation` VALUES (3170,'SBAC','Smarter Balanced Assessments','ENU','--ANY--','--ANY--','����5B��:�Т�V@','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2703,'AIR','Tamaño de impresión','ESN','--ANY--','--ANY--','��?n;M����ڵ��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2800,'AIR','Waiho i ka mana?o no keia pono.','HAW','--ANY--','--ANY--','���dpK6��kUqr�','2013-12-18 14:09:06.677');
INSERT INTO `client_messagetranslation` VALUES (2373,'AIR','Ha llegado al final de la prueba.  Haga clic en [Sí] para pasar a la página siguiente.  Haga clic en [No] para continuar con la prueba.','ESN','--ANY--','--ANY--','�B\\��JX��a���p�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2484,'AIR','Hoʻomaka I Ke ʻOki Leo','HAW','--ANY--','--ANY--','����IGJ\"��.�d��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3048,'AIR','El usuario no tiene permiso','ESN','--ANY--','--ANY--','����\0O����b','2014-03-22 20:35:32.320');
INSERT INTO `client_messagetranslation` VALUES (3034,'AIR','Se produjo un problema con el sistema.  Proporcione este número al AP.','ESN','--ANY--','--ANY--','��qh��MҴA��\Z�0','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3344,'SBAC','This application provides access to TDS reports.','ENU','--ANY--','--ANY--','��N)�Cl�}���+�Q','2013-08-01 14:17:09.583');
INSERT INTO `client_messagetranslation` VALUES (2591,'AIR','Diga la opción A','ESN','--ANY--','--ANY--','�5\'��Mǥi��ǅ6V','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2696,'AIR','Resaltar','ESN','--ANY--','--ANY--','�<�}�NAe�[QC2b�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2461,'AIR','Idioma:','ESN','--ANY--','--ANY--','�J3\n֢K�`\'del2','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2516,'AIR','Resultados','ESN','--ANY--','--ANY--','����;NO�x�~��[','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3131,'AIR','?A?ohe kuleana o keia kahu ha?awi ho?ike kapu ma keia wa ho?ohana nei.','HAW','--ANY--','--ANY--','��.QY�L��\'q@��','2013-12-18 14:09:06.957');
INSERT INTO `client_messagetranslation` VALUES (2867,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','�����1L~��LDR��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2540,'AIR','Siguiente','ESN','--ANY--','--ANY--','�\n׏l�K���Ȩ�g��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2687,'AIR','Validando prueba','ESN','--ANY--','--ANY--','���ZG�r��U','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2671,'AIR','Imprimir el artículo','ESN','--ANY--','--ANY--','��}\rUN3�{0;8hW3','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2924,'AIR','Se produjo un error de comunicación con el servidor. Inténtelo de nuevo.','ESN','--ANY--','--ANY--','�[\"�\ZD���*\'�)=','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2660,'AIR','E ho\'omau i ka ho\'ike','HAW','--ANY--','--ANY--','�\\���L�s&���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3081,'AIR','No se encuentra la base de datos RTS','ESN','--ANY--','--ANY--','��P�icG\Z�q�&h(!�','2014-03-22 20:35:32.333');
INSERT INTO `client_messagetranslation` VALUES (2414,'AIR','Normal','ESN','--ANY--','--ANY--','���c��FG�8}���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3658,'AIR','Resumen del ancho de banda:','ESN','--ANY--','--ANY--','��AmeL\Z�w\r��9\r�','2014-03-22 20:36:39.760');
INSERT INTO `client_messagetranslation` VALUES (2760,'AIR','Ka \'Ikamu','HAW','--ANY--','--ANY--','����N4����6�F�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2382,'AIR','Makemake anei \'oe e ho\'omaha ma ka ho\'ike? E \'olu\'olu, e \'olelo i kau Kumu ma mua o ka  ho\'omaha \'ana ma kau ho\'ike.','HAW','--ANY--','--ANY--','����H��J�<���M','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2566,'AIR','Diga el texto seleccionado','ESN','--ANY--','--ANY--','�(	Nw�B8��J�C��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2621,'AIR',' ','HAW','--ANY--','--ANY--','�3A$)KY���`8_�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2373,'AIR','Ua hiki mai \'oe i ka hopena o ka ho\'ike. E kaomi i [\'Ae] no ka ho\'omau \'ana i ka \'ao\'ao a\'e. E kaomi i [\'A\'ole] no ka ho\'omau \'ana i ka hana \'ana ma kau ho\'ike.','HAW','--ANY--','--ANY--','�_aXH�I�\"ib�7','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2541,'AIR','Pau Ka Ho\'ike','HAW','--ANY--','--ANY--','�kĶ��GE�\\�=���K','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2762,'AIR','Ka Mo\'olelo','HAW','--ANY--','--ANY--','�Ɨ��wAa�9�|DB','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2832,'AIR','Marcar Item para Revisar','ESN','--ANY--','--ANY--','�ϙ$Ȍ��Y��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3411,'AIR','Ua ho?okomo ?oe i na ?ikepili no na ?ikemu ho?ike a pau.Ina pau kau nana hou ?ana, kaomi i ka pihi [Pau ka nana ?Ikepili ?ana].','HAW','--ANY--','--ANY--','��\'�Q�K+�Z�y�K+','2013-12-18 14:09:07.200');
INSERT INTO `client_messagetranslation` VALUES (2496,'AIR','Makemake anei \'oe e ho\'ololi i ka mana\'o i koho mua \'ia? <\'Ae> <\'A\'ole>','HAW','--ANY--','--ANY--','��$jv�G:��A|�}�[','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3604,'AIR','Mea kukulu ?olelo','HAW','--ANY--','--ANY--','��O=��C�ȿ�G�R�','2013-12-18 14:09:07.480');
INSERT INTO `client_messagetranslation` VALUES (2639,'AIR','‘Ae, ua lohe au i ke kani.','HAW','--ANY--','--ANY--','� -�Y\ZM����OC�t','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3711,'AIR','{silence}OpciÃ³n F.{silence}','HAW','--ANY--','--ANY--','�\"���Kc��H9\"E�n','2013-12-18 14:09:07.640');
INSERT INTO `client_messagetranslation` VALUES (3693,'AIR','../tools/formulas/2010/hi_Alg_EOC.html','HAW','--ANY--','--ANY--','�;BE�G�۾*��\"�','2013-12-18 14:09:07.613');
INSERT INTO `client_messagetranslation` VALUES (3452,'AIR','?Olelo huna','HAW','--ANY--','--ANY--','�>�t�lM\Z���\0�wi�','2013-12-18 14:09:07.267');
INSERT INTO `client_messagetranslation` VALUES (2467,'AIR','Mensaje del administrador de pruebas:','ESN','--ANY--','--ANY--','�S$���E��Uo璀\\','2014-03-22 20:33:38.110');
INSERT INTO `client_messagetranslation` VALUES (3147,'AIR','La oportunidad actual está activa','ESN','--ANY--','--ANY--','�bt��Hğ��`q�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3165,'AIR','Usted no tiene derecho a tomar esta prueba debido a una excepción parental.','ESN','--ANY--','--ANY--','�u�MW�E��y��A%�','2014-03-22 20:39:22.703');
INSERT INTO `client_messagetranslation` VALUES (3664,'AIR','Ho?oholo i na ho?ike hoa?o Kikowaena Punaewele','HAW','--ANY--','--ANY--','���Qq�O;�X�<FQ','2013-12-18 14:09:07.573');
INSERT INTO `client_messagetranslation` VALUES (2505,'AIR','E \'olu\'olu, e koho i kekahi ho\'ike e hana.','HAW','--ANY--','--ANY--','��J*��A͈i6��Ü�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3026,'AIR','Aia he pilikia me ka ‘ōnaehana. E ‘olu’olu e hā’awi i kēia helu iā Kumu','HAW','--ANY--','--ANY--','��^=��FҎ��[GV�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2788,'AIR','Cerrar Comentario','ESN','--ANY--','--ANY--','��^�.OL��O��c�','2014-03-22 20:33:38.077');
INSERT INTO `client_messagetranslation` VALUES (2937,'AIR','Braille sin contracciones','ESN','--ANY--','--ANY--','��Y9�D��P�%w�_','2014-03-22 20:34:28.177');
INSERT INTO `client_messagetranslation` VALUES (2764,'AIR','Nui A\'e','HAW','--ANY--','--ANY--','�$M���K���&&��','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2868,'AIR','Cerrar Comentario','ESN','--ANY--','--ANY--','�N@�//E���:��j��','2014-03-22 20:34:28.043');
INSERT INTO `client_messagetranslation` VALUES (2816,'AIR','Someter un comentario para este ítem.','ESN','--ANY--','--ANY--','�U�c*L��_Oi�A','2014-03-22 20:33:38.090');
INSERT INTO `client_messagetranslation` VALUES (2533,'AIR','E Pa\'i i ka Mo\'olelo','HAW','--ANY--','--ANY--','�c8]z�I9�I^:�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2926,'AIR','E lele \'oe','HAW','--ANY--','--ANY--','�����J��D��w}V','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2565,'AIR','ʻŌlelo','HAW','--ANY--','--ANY--','���$F��%HsN�\r�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2541,'AIR','Terminar prueba','ESN','--ANY--','--ANY--','��|FyGLb�ɻp��o2','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2542,'AIR','ʻAe','HAW','--ANY--','--ANY--','��$_�@����^,�M','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2691,'AIR','Na Koho Waiho\'olu\'u','HAW','--ANY--','--ANY--','���5�H�.�b\"�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3092,'AIR','ʻOkoʻa ka wā hana hōʻike, ʻokoʻa ka mokuʻāina. E ʻoluʻolu, e ʻōlelo me kāu luna hōʻike.','HAW','--ANY--','--ANY--','��t4(�GβQ��z','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3248,'AIR','Hua?olelo-i-ka-wala?au','HAW','--ANY--','--ANY--','��5�&D7�:�z�w��','2013-12-18 14:09:07.033');
INSERT INTO `client_messagetranslation` VALUES (2802,'AIR','Kuapo i ka<br />mana?o','HAW','--ANY--','--ANY--','��;�E�붹��uV','2013-12-18 14:09:06.680');
INSERT INTO `client_messagetranslation` VALUES (2629,'SBAC','Mueva el objeto a una nueva ubicación y haga clic donde desee colocarlo.','ESN','--ANY--','--ANY--','�l��XOr�u���?�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2788,'AIR','E pani i ka mana?o','HAW','--ANY--','--ANY--','�%KD�rH�}E��','2013-12-18 14:09:06.663');
INSERT INTO `client_messagetranslation` VALUES (2482,'AIR','Detener grabación','ESN','--ANY--','--ANY--','�H\Z�DF�����4�C','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2637,'AIR','This is what is being spoken','HAW','--ANY--','--ANY--','��kH&@I�j��~���','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2502,'AIR','<p> E kali malie ʻoe ʻoiai ke hoʻonohonoho hou nei ka hōʻike luna i nā mea e pono ai. He mau mīnuke ana paha kēia...</p>','HAW','--ANY--','--ANY--','��W(\n0K]�qX`7#�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3097,'AIR','?A?ohe ou kulana mea ho?ohana.Hiki ?ole ia?oe ke ?e?e ma muli o ka nele o kekahi kulana mea ho?ohana.','HAW','--ANY--','--ANY--','���b^TFؓO��i�v','2013-12-18 14:09:06.910');
INSERT INTO `client_messagetranslation` VALUES (3400,'AIR','Ho?oku i ka pa?ani ?ia ?ana Io ka ?ikemu','HAW','--ANY--','--ANY--','�����A�������','2013-12-18 14:09:07.183');
INSERT INTO `client_messagetranslation` VALUES (3393,'AIR','Kahu nana TDS','HAW','--ANY--','--ANY--','�%\'|�N��,\'{��','2013-12-18 14:09:07.177');
INSERT INTO `client_messagetranslation` VALUES (2637,'AIR','This is what is being spoken','ESN','--ANY--','--ANY--','�<sF�DK���(΁�&','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3620,'AIR','Usted no puede dejar este segmento hasta que todas las preguntas requeridas han sido contestadas.','ESN','--ANY--','--ANY--','�>Ok4�Cu���Ш$��','2014-03-22 20:36:39.523');
INSERT INTO `client_messagetranslation` VALUES (2628,'SBAC','Seleccione la ubicación de la etiqueta.','ESN','--ANY--','--ANY--','�a��IU��T>��+ ','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (2451,'AIR','<span>E Hana Hou</span>','HAW','--ANY--','--ANY--','��k�|�IW�[��*ES�','2013-08-01 11:02:31.780');
INSERT INTO `client_messagetranslation` VALUES (3065,'AIR','Intente insertar el artículo después de la siguiente posición disponible','ESN','--ANY--','--ANY--','���P �H���%ߕ�o�','2013-08-01 11:02:31.780');
/*!40000 ALTER TABLE `client_messagetranslation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `client_systemflags`
--

LOCK TABLES `client_systemflags` WRITE;
/*!40000 ALTER TABLE `client_systemflags` DISABLE KEYS */;
INSERT INTO `client_systemflags` VALUES ('accommodations',1,'keeps an audit trail of various changes to accommodations settings','SBAC','\0','2011-06-01 11:27:47.980',NULL);
INSERT INTO `client_systemflags` VALUES ('AnonymousTestee',1,'Permits anonymous login by testees (for practice test)','SBAC','\0','2011-05-24 15:16:32.757',NULL);
INSERT INTO `client_systemflags` VALUES ('CertifyProctor',0,'TRAINEDPROCTOR','SBAC','\0','2010-06-22 11:55:43.967',NULL);
INSERT INTO `client_systemflags` VALUES ('items',0,'Toggles audit trail on item selection','SBAC','\0','2010-06-22 11:55:43.967',NULL);
INSERT INTO `client_systemflags` VALUES ('latencies',1,'Toggles audit trail on system latencies','SBAC','\0','2011-05-24 15:16:32.757',NULL);
INSERT INTO `client_systemflags` VALUES ('MatchTesteeProctorSchool',1,'INSTITUTION','SBAC','\0','2010-08-16 10:21:08.193',NULL);
INSERT INTO `client_systemflags` VALUES ('opportunities',1,'Toggles audit trail on opportunities','SBAC','\0','2011-05-24 15:16:32.757',NULL);
INSERT INTO `client_systemflags` VALUES ('oppreport',1,'Allows/disallows xml reporting of opportunities','SBAC','\0','2011-05-24 15:49:06.323',NULL);
INSERT INTO `client_systemflags` VALUES ('ProctorActivity',1,'Monitor proctor activity','SBAC','\0','2010-09-22 11:51:03.070',NULL);
INSERT INTO `client_systemflags` VALUES ('proctorless',1,'Permits login to proctorless session','SBAC','\0','2010-09-22 11:51:03.070',NULL);
INSERT INTO `client_systemflags` VALUES ('ProctorTraining',0,'Allows proctor app to differentiate between operational system and training system','SBAC','\0','2010-09-22 11:51:03.073',NULL);
INSERT INTO `client_systemflags` VALUES ('responses',1,'Toggles audit trail on testee responses','SBAC','\0','2010-09-22 11:51:03.073',NULL);
INSERT INTO `client_systemflags` VALUES ('RestoreAccommodations',1,'Restore RTS Accommodation values on test resume','SBAC','\0','2010-09-22 11:51:03.077',NULL);
INSERT INTO `client_systemflags` VALUES ('scores',1,'Toggles audit trail on opportunity scores','SBAC','\0','2010-09-22 11:51:03.077',NULL);
INSERT INTO `client_systemflags` VALUES ('sessions',1,'Toggles audit trail on sessions','SBAC','\0','2010-09-22 11:51:03.077',NULL);
INSERT INTO `client_systemflags` VALUES ('SuppressScores',0,'Keeps proctors from seeing test scores','SBAC','\0','2010-08-17 08:46:58.597',NULL);
INSERT INTO `client_systemflags` VALUES ('accommodations',1,'keeps an audit trail of various changes to accommodations settings','SBAC_PT','','2011-06-01 11:27:47.980',NULL);
INSERT INTO `client_systemflags` VALUES ('AnonymousTestee',1,'Permits anonymous login by testees (for practice test)','SBAC_PT','','2011-05-24 15:16:32.757',NULL);
INSERT INTO `client_systemflags` VALUES ('CertifyProctor',0,'TRAINEDPROCTOR','SBAC_PT','','2010-06-22 11:55:43.967',NULL);
INSERT INTO `client_systemflags` VALUES ('items',0,'Toggles audit trail on item selection','SBAC_PT','','2010-06-22 11:55:43.967',NULL);
INSERT INTO `client_systemflags` VALUES ('latencies',1,'Toggles audit trail on system latencies','SBAC_PT','','2011-05-24 15:16:32.757',NULL);
INSERT INTO `client_systemflags` VALUES ('MatchTesteeProctorSchool',0,'INSTITUTION','SBAC_PT','','2010-08-16 10:21:08.193',NULL);
INSERT INTO `client_systemflags` VALUES ('opportunities',1,'Toggles audit trail on opportunities','SBAC_PT','','2011-05-24 15:16:32.757',NULL);
INSERT INTO `client_systemflags` VALUES ('oppreport',1,'Allows/disallows xml reporting of opportunities','SBAC_PT','','2011-05-24 15:49:06.323',NULL);
INSERT INTO `client_systemflags` VALUES ('ProctorActivity',1,'Monitor proctor activity','SBAC_PT','','2010-09-22 11:51:03.070',NULL);
INSERT INTO `client_systemflags` VALUES ('proctorless',1,'Permits login to proctorless session','SBAC_PT','','2010-09-22 11:51:03.070',NULL);
INSERT INTO `client_systemflags` VALUES ('ProctorTraining',1,'Allows proctor app to differentiate between operational system and training system','SBAC_PT','','2010-09-22 11:51:03.073',NULL);
INSERT INTO `client_systemflags` VALUES ('responses',1,'Toggles audit trail on testee responses','SBAC_PT','','2010-09-22 11:51:03.073',NULL);
INSERT INTO `client_systemflags` VALUES ('RestoreAccommodations',1,'Restore RTS Accommodation values on test resume','SBAC_PT','','2010-09-22 11:51:03.077',NULL);
INSERT INTO `client_systemflags` VALUES ('scores',1,'Toggles audit trail on opportunity scores','SBAC_PT','','2010-09-22 11:51:03.077',NULL);
INSERT INTO `client_systemflags` VALUES ('sessions',1,'Toggles audit trail on sessions','SBAC_PT','','2010-09-22 11:51:03.077',NULL);
INSERT INTO `client_systemflags` VALUES ('SuppressScores',0,'Keeps proctors from seeing test scores','SBAC_PT','','2010-08-17 08:46:58.597',NULL);
/*!40000 ALTER TABLE `client_systemflags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `client_timelimits`
--

LOCK TABLES `client_timelimits` WRITE;
/*!40000 ALTER TABLE `client_timelimits` DISABLE KEYS */;
INSERT INTO `client_timelimits` VALUES ('\n�l6-�I��*�L+�',NULL,1,10,-1,10,'SBAC_PT','',30,20,20,'2012-12-21 00:02:53.000','2012-12-21 00:02:53.000',NULL,8,15,2);
INSERT INTO `client_timelimits` VALUES ('l��m@B�}.+n�7�',NULL,1,10,-1,15,'SBAC','\0',20,20,20,'2010-07-07 15:39:31.433',NULL,NULL,8,120,2);
/*!40000 ALTER TABLE `client_timelimits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `client_tooldependencies`
--

LOCK TABLES `client_tooldependencies` WRITE;
/*!40000 ALTER TABLE `client_tooldependencies` DISABLE KEYS */;
/*!40000 ALTER TABLE `client_tooldependencies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `client_voicepack`
--

LOCK TABLES `client_voicepack` WRITE;
/*!40000 ALTER TABLE `client_voicepack` DISABLE KEYS */;
INSERT INTO `client_voicepack` VALUES ('Android','android',2,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Android','android',2,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Android','eng',1,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Android','eng',1,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Android','spa',1,'ESN','SBAC');
INSERT INTO `client_voicepack` VALUES ('Android','spa',1,'ESN','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Chrome','Chrome OS Spanish',1,'ESN','SBAC');
INSERT INTO `client_voicepack` VALUES ('Chrome','Chrome OS Spanish',1,'ESN','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Chrome','Chrome OS US English',1,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Chrome','Chrome OS US English',1,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Chrome','Chrome OS US English Voice',2,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Chrome','Chrome OS US English Voice',2,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Chrome','Chrome OS US Spanish Voice',2,'ESN','SBAC');
INSERT INTO `client_voicepack` VALUES ('Chrome','Chrome OS US Spanish Voice',2,'ESN','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Chrome','native',3,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Chrome','native',3,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Chrome','US English Female TTS (by Google)',1,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Chrome','US English Female TTS (by Google)',1,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Chrome','US Spanish Female TTS (by Google)',1,'ESN','SBAC');
INSERT INTO `client_voicepack` VALUES ('Chrome','US Spanish Female TTS (by Google)',1,'ESN','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('IOS','en-US',1,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('IOS','en-US',1,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('IOS','eng',2,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('IOS','eng',2,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('IOS','es-ES',1,'ESN','SBAC');
INSERT INTO `client_voicepack` VALUES ('IOS','es-ES',1,'ESN','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('IOS','ios-default',3,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('IOS','ios-default',3,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('IOS','spa',2,'ESN','SBAC');
INSERT INTO `client_voicepack` VALUES ('IOS','spa',2,'ESN','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Linux','Cepstral_Marta',1,'ESN','SBAC');
INSERT INTO `client_voicepack` VALUES ('Linux','Cepstral_Marta',1,'ESN','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Linux','Cepstral_Miguel',2,'ESN','SBAC');
INSERT INTO `client_voicepack` VALUES ('Linux','Cepstral_Miguel',2,'ESN','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Linux','cmu_us_awb_arctic_hts',5,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Linux','cmu_us_awb_arctic_hts',5,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Linux','cmu_us_bdl_arctic_hts',8,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Linux','cmu_us_bdl_arctic_hts',8,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Linux','cmu_us_jmk_arctic_hts',7,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Linux','cmu_us_jmk_arctic_hts',7,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Linux','cmu_us_slt_arctic_hts',6,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Linux','cmu_us_slt_arctic_hts',6,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Linux','el_diphone',3,'ESN','SBAC');
INSERT INTO `client_voicepack` VALUES ('Linux','el_diphone',3,'ESN','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Linux','kal_diphone',3,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Linux','kal_diphone',3,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Linux','ked_diphone',4,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Linux','ked_diphone',4,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Linux','native',5,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Linux','native',5,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','Agnes',9,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','Agnes',9,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','Alex',11,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','Alex',11,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','Bruce',16,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','Bruce',16,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','Callie',2,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','Callie',2,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','David',2,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','David',2,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','Diego',1,'ESN','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','Diego',1,'ESN','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','Fred',20,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','Fred',20,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','Heather Infovox iVox HQ',1,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','Heather Infovox iVox HQ',1,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','Javier',1,'ESN','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','Javier',1,'ESN','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','Jill',1,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','Jill',1,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','Junior',23,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','Junior',23,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','Kathy',24,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','Kathy',24,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','Marta',1,'ESN','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','Marta',1,'ESN','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','Monica',1,'ESN','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','Monica',1,'ESN','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','native',5,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','native',5,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','Paulina',1,'ESN','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','Paulina',1,'ESN','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','Princess',26,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','Princess',26,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','Ralph',27,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','Ralph',27,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','Rosa Infovox iVox HQ',1,'ESN','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','Rosa Infovox iVox HQ',1,'ESN','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','Samantha',1,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','Samantha',1,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','Tom',1,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','Tom',1,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','Vicki',29,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','Vicki',29,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('OSX','Victoria',30,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('OSX','Victoria',30,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Windows','Cepstral_David',1,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Windows','Cepstral_David',1,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Windows','Cepstral_Marta',2,'ESN','SBAC');
INSERT INTO `client_voicepack` VALUES ('Windows','Cepstral_Marta',2,'ESN','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Windows','Cepstral_Miguel',3,'ESN','SBAC');
INSERT INTO `client_voicepack` VALUES ('Windows','Cepstral_Miguel',3,'ESN','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Windows','Julie',1,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Windows','Julie',1,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Windows','Kate',1,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Windows','Kate',1,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Windows','MICHAEL',2,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Windows','MICHAEL',2,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Windows','MICHELLE',2,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Windows','MICHELLE',2,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Windows','MS-Anna',3,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Windows','MS-Anna',3,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Windows','MSAnna',3,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Windows','MSAnna',3,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Windows','MSMary',3,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Windows','MSMary',3,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Windows','MSMike',3,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Windows','MSMike',3,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Windows','MSSam',3,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Windows','MSSam',3,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Windows','MS_EN-GB_HAZEL',4,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Windows','MS_EN-GB_HAZEL',4,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Windows','MS_EN-US_DAVID',3,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Windows','MS_EN-US_DAVID',3,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Windows','MS_EN-US_ZIRA',3,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Windows','MS_EN-US_ZIRA',3,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Windows','native',5,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Windows','native',5,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Windows','Paul',1,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Windows','Paul',1,'ENU','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Windows','Violeta',1,'ESN','SBAC');
INSERT INTO `client_voicepack` VALUES ('Windows','Violeta',1,'ESN','SBAC_PT');
INSERT INTO `client_voicepack` VALUES ('Windows','VW Julie',1,'ENU','SBAC');
INSERT INTO `client_voicepack` VALUES ('Windows','VW Julie',1,'ENU','SBAC_PT');
/*!40000 ALTER TABLE `client_voicepack` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `client_testtool`
--

LOCK TABLES `client_testtool` WRITE;
/*!40000 ALTER TABLE `client_testtool` DISABLE KEYS */;
INSERT INTO `client_testtool` VALUES ('SBAC','Color Choices','TDS_CC0','Black on White','','\0','Color Choices feature is disabled','StudentGlobal',-1,'TDSCore_Staging_Configs_2013','TDSCore_Staging_Configs_2013','FAMILY','ALL',NULL);
INSERT INTO `client_testtool` VALUES ('SBAC','Color Choices','TDS_CCInvert','Reverse Contrast','\0','\0','White text with a Black background','StudentGlobal',15,'TDSCore_Staging_Configs_2013','TDSCore_Staging_Configs_2013','FAMILY','ALL',NULL);
INSERT INTO `client_testtool` VALUES ('SBAC','Color Choices','TDS_CCMagenta','Black on Rose','\0','\0','Magenta','StudentGlobal',8,'TDSCore_Staging_Configs_2013','TDSCore_Staging_Configs_2013','FAMILY','ALL',NULL);
INSERT INTO `client_testtool` VALUES ('SBAC','Color Choices','TDS_CCMedGrayLtGray','Medium Gray on Light Gray','\0','\0','Medium Gray on Light Gray','StudentGlobal',17,'TDSCore_Staging_Configs_2013','TDSCore_Staging_Configs_2013','FAMILY','ALL',NULL);
INSERT INTO `client_testtool` VALUES ('SBAC','Color Choices','TDS_CCYellowB','Yellow on Blue','\0','\0','Yellow text with a Blue background','StudentGlobal',14,'TDSCore_Staging_Configs_2013','TDSCore_Staging_Configs_2013','FAMILY','ALL',NULL);
INSERT INTO `client_testtool` VALUES ('SBAC_PT','Color Choices','TDS_CC0','Black on White','','\0','Color Choices feature is disabled','StudentGlobal',-1,'TDSCore_Staging_Configs_2013','TDSCore_Staging_Configs_2013','FAMILY','ALL',NULL);
INSERT INTO `client_testtool` VALUES ('SBAC_PT','Color Choices','TDS_CCInvert','Reverse Contrast','\0','\0','White text with a Black background','StudentGlobal',15,'TDSCore_Staging_Configs_2013','TDSCore_Staging_Configs_2013','FAMILY','ALL',NULL);
INSERT INTO `client_testtool` VALUES ('SBAC_PT','Color Choices','TDS_CCMagenta','Black on Rose','\0','\0','Magenta','StudentGlobal',8,'TDSCore_Staging_Configs_2013','TDSCore_Staging_Configs_2013','FAMILY','ALL',NULL);
INSERT INTO `client_testtool` VALUES ('SBAC_PT','Color Choices','TDS_CCMedGrayLtGray','Medium Gray on Light Gray','\0','\0','Medium Gray on Light Gray','StudentGlobal',17,'TDSCore_Staging_Configs_2013','TDSCore_Staging_Configs_2013','FAMILY','ALL',NULL);
INSERT INTO `client_testtool` VALUES ('SBAC_PT','Color Choices','TDS_CCYellowB','Yellow on Blue','\0','\0','Yellow text with a Blue background','StudentGlobal',14,'TDSCore_Staging_Configs_2013','TDSCore_Staging_Configs_2013','FAMILY','ALL',NULL);
INSERT INTO `client_testtool` VALUES ('SBAC_PT','Language','ENU','English','','\0','English language test','StudentGlobal',0,'jf','jf','FAMILY','ALL',NULL);
INSERT INTO `client_testtool` VALUES ('SBAC_PT','Print Size','TDS_PS_L0','No default zoom applied','','\0','There is no default zoom','StudentGlobal',-1,'TDSCore_Staging_Configs_2012','TDSCore_Staging_Configs_2012','FAMILY','ALL',NULL);
INSERT INTO `client_testtool` VALUES ('SBAC_PT','Print Size','TDS_PS_L1','Level 1','\0','\0','Default level of zoom is set to 1','StudentGlobal',1,'TDSCore_Staging_Configs_2012','TDSCore_Staging_Configs_2012','FAMILY','ALL',NULL);
INSERT INTO `client_testtool` VALUES ('SBAC_PT','Print Size','TDS_PS_L2','Level 2','\0','\0','Default level of zoom is set to 2','StudentGlobal',2,'TDSCore_Staging_Configs_2012','TDSCore_Staging_Configs_2012','FAMILY','ALL',NULL);
INSERT INTO `client_testtool` VALUES ('SBAC_PT','Print Size','TDS_PS_L3','Level 3','\0','\0','Default level of zoom is set to 3','StudentGlobal',3,'TDSCore_Staging_Configs_2012','TDSCore_Staging_Configs_2012','FAMILY','ALL',NULL);
INSERT INTO `client_testtool` VALUES ('SBAC_PT','Print Size','TDS_PS_L4','Level 4','\0','\0','Default level of zoom is set to 4','StudentGlobal',4,'TDSCore_Staging_Configs_2012','TDSCore_Staging_Configs_2012','FAMILY','ALL',NULL);
/*!40000 ALTER TABLE `client_testtool` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `client_testtooltype`
--

LOCK TABLES `client_testtooltype` WRITE;
/*!40000 ALTER TABLE `client_testtooltype` DISABLE KEYS */;
INSERT INTO `client_testtooltype` VALUES ('SBAC','Color Choices','','\0','TDSAcc-ColorChoices','\0','\0','','','',NULL,0,'2013-12-13 13:53:31.687','TDSCore_Staging_Configs_2013','TDSCore_Staging_Configs_2013','FAMILY','StudentGlobal',NULL,'\0','','ALL');
INSERT INTO `client_testtooltype` VALUES ('SBAC_PT','Color Choices','\0','','TDSAcc-ColorChoices','\0','\0','','','',NULL,0,'2014-01-16 11:13:34.067','TDSCore_Staging_Configs_2013','TDSCore_Staging_Configs_2013','FAMILY','StudentGlobal','Language','\0','','ALL');
INSERT INTO `client_testtooltype` VALUES ('SBAC_PT','Language','','\0','TDSAcc-Language','','\0','','','',NULL,-1,'2012-12-27 09:33:33.513','jf','jf','FAMILY','StudentGlobal',NULL,'\0','','ALL');
INSERT INTO `client_testtooltype` VALUES ('SBAC_PT','Print Size','','\0','TDSAcc-PrintSize','','\0','','','\0',NULL,0,'2013-05-13 14:05:26.573','TDSCore_Staging_Configs_2012','TDSCore_Staging_Configs_2012','FAMILY','StudentGlobal','Language','\0','','ALL');
/*!40000 ALTER TABLE `client_testtooltype` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-09-04 11:39:09
