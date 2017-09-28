/***********************************************************************************************************************
  File: exam_create_schema.sql

  Desc: create the exam database schema

***********************************************************************************************************************/
-- Server version	5.6.27-log

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

CREATE SCHEMA IF NOT EXISTS exam
  CHARACTER SET utf8
  COLLATE utf8_unicode_ci;

USE exam;

--
-- Table structure for table `exam_status_codes`
--

DROP TABLE IF EXISTS `exam_status_codes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exam_status_codes` (
  `status` varchar(50) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `stage` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`status`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO exam_status_codes VALUES ('suspended','an existing exam is waiting for proctor approval to restart','inuse');
INSERT INTO exam_status_codes VALUES ('scored','completed exam has been scored','closed');
INSERT INTO exam_status_codes VALUES ('reported','exam scores have been reported to reporting system','closed');
INSERT INTO exam_status_codes VALUES ('approved','exam has been approved for start/restart by proctor','inuse');
INSERT INTO exam_status_codes VALUES ('paused','exam has been put on hold by the user','inactive');
INSERT INTO exam_status_codes VALUES ('pending','a new exam is waiting for proctor approval','inuse');
INSERT INTO exam_status_codes VALUES ('expired','incomplete exam has expired','closed');
INSERT INTO exam_status_codes VALUES ('segmentExit','request to leave a segment','inuse');
INSERT INTO exam_status_codes VALUES ('denied','exam start was denied by the proctor','inactive');
INSERT INTO exam_status_codes VALUES ('segmentEntry','request to enter a segment','inuse');
INSERT INTO exam_status_codes VALUES ('review','student is reviewing completed exam items','inuse');
INSERT INTO exam_status_codes VALUES ('completed','exam has been completed and submitted for scoring by student','closed');
INSERT INTO exam_status_codes VALUES ('submitted','exam scores have been submitted to QA','closed');
INSERT INTO exam_status_codes VALUES ('rescored','the exam was rescored','closed');
INSERT INTO exam_status_codes VALUES ('invalidated','exam results have been invalidated','closed');
INSERT INTO exam_status_codes VALUES ('started','exam is in use','inuse');

--
-- Table structure for table `exam`
--

DROP TABLE IF EXISTS `exam`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exam` (
  `id` char(36) NOT NULL,
  `client_name` varchar(100) NOT NULL,
  `environment` varchar(50) NOT NULL,
  `subject` varchar(20) NOT NULL,
  `login_ssid` varchar(128) DEFAULT NULL,
  `student_id` bigint(20) NOT NULL,
  `student_key` varchar(128) DEFAULT NULL,
  `student_name` varchar(128) DEFAULT NULL,
  `assessment_id` varchar(255) NOT NULL,
  `assessment_key` varchar(250) NOT NULL,
  `assessment_window_id` varchar(50) DEFAULT NULL,
  `assessment_algorithm` varchar(50) NOT NULL,
  `segmented` bit(1) NOT NULL DEFAULT b'0',
  `joined_at` datetime(3) DEFAULT NULL,
  `created_at` timestamp(3) NOT NULL,
  `msb` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `ix_exam_student_id_assessment_id_client_name` (`student_id`,`assessment_id`,`client_name`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `exam_accommodation`
--

DROP TABLE IF EXISTS `exam_accommodation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exam_accommodation` (
  `id` char(36) NOT NULL,
  `exam_id` char(36) NOT NULL,
  `segment_key` varchar(255) DEFAULT NULL,
  `type` varchar(50) NOT NULL,
  `code` varchar(250) NOT NULL,
  `description` varchar(250) NOT NULL,
  `created_at` timestamp(3) NOT NULL,
  `allow_change` bit(1) NOT NULL DEFAULT b'0',
  `value` varchar(256) NOT NULL,
  `segment_position` int(11) NOT NULL,
  `visible` bit(1) NOT NULL DEFAULT b'1',
  `student_controlled` bit(1) NOT NULL DEFAULT b'1',
  `disabled_on_guest_session` bit(1) NOT NULL DEFAULT b'0',
  `default_accommodation` bit(1) NOT NULL,
  `allow_combine` bit(1) NOT NULL,
  `depends_on` varchar(50) DEFAULT NULL,
  `sort_order` int(11) NOT NULL,
  `functional` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`id`),
  KEY `ix_exam_accommodations_exam_id_segment_id_type` (`exam_id`,`segment_key`,`type`),
  CONSTRAINT `fk_exam_accommodation_exam_id` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `exam_accommodation_event`
--

DROP TABLE IF EXISTS `exam_accommodation_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exam_accommodation_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `exam_accommodation_id` char(36) NOT NULL,
  `exam_id` char(36) NOT NULL,
  `denied_at` timestamp(3) NULL DEFAULT NULL,
  `deleted_at` timestamp(3) NULL DEFAULT NULL,
  `created_at` timestamp(3) NOT NULL,
  `selectable` bit(1) NOT NULL DEFAULT b'0',
  `total_type_count` tinyint(4) NOT NULL DEFAULT '0',
  `custom` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `exam_accommodation_event_ibfk_1` (`exam_accommodation_id`),
  KEY `fk_exam_accommodation_event_exam_id` (`exam_id`),
  CONSTRAINT `exam_accommodation_event_ibfk_1` FOREIGN KEY (`exam_accommodation_id`) REFERENCES `exam_accommodation` (`id`),
  CONSTRAINT `fk_exam_accommodation_event_exam_id` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `exam_event`
--

DROP TABLE IF EXISTS `exam_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exam_event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exam_id` char(36) NOT NULL,
  `attempts` int(11) NOT NULL DEFAULT '0',
  `status` varchar(50) NOT NULL DEFAULT 'pending',
  `status_changed_at` datetime(3) NOT NULL,
  `status_change_reason` varchar(255) DEFAULT NULL,
  `changed_at` datetime(3) DEFAULT NULL,
  `deleted_at` datetime(3) DEFAULT NULL,
  `completed_at` datetime(3) DEFAULT NULL,
  `scored_at` datetime(3) DEFAULT NULL,
  `started_at` datetime(3) DEFAULT NULL,
  `created_at` timestamp(3) NOT NULL,
  `abnormal_starts` int(11) NOT NULL DEFAULT '0',
  `waiting_for_segment_approval_position` int(11) DEFAULT '-1',
  `current_segment_position` int(11) DEFAULT NULL,
  `browser_id` varbinary(16) DEFAULT NULL,
  `custom_accommodations` bit(1) NOT NULL DEFAULT b'0',
  `expires_at` datetime(3) DEFAULT NULL,
  `max_items` int(11) NOT NULL DEFAULT '0',
  `language_code` varchar(50) DEFAULT NULL,
  `restarts_and_resumptions` int(11) NOT NULL DEFAULT '0',
  `resumptions` int(11) NOT NULL DEFAULT '0',
  `session_id` char(36) NOT NULL,
  `browser_user_agent` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uix_exam_event_id_exam_id_deleted_at_scored_at` (`id`,`exam_id`,`deleted_at`),
  KEY `ix_exam_event_exam_id_session_id` (`session_id`,`exam_id`,`id`),
  KEY `fk_exam_event_exam_id` (`exam_id`),
  CONSTRAINT `fk_exam_event_exam_id` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `exam_item`
--

DROP TABLE IF EXISTS `exam_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exam_item` (
  `id` char(36) NOT NULL,
  `item_key` varchar(25) NOT NULL,
  `assessment_item_bank_key` bigint(19) NOT NULL,
  `assessment_item_key` bigint(19) NOT NULL,
  `item_type` varchar(50) NOT NULL,
  `exam_page_id` char(36) NOT NULL,
  `position` int(11) NOT NULL,
  `is_fieldtest` bit(1) NOT NULL DEFAULT b'0',
  `is_required` bit(1) NOT NULL DEFAULT b'0',
  `item_file_path` varchar(500) NOT NULL,
  `stimulus_file_path` varchar(500) DEFAULT NULL,
  `created_at` timestamp(3) NOT NULL,
  `group_id` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_created_at` (`created_at`),
  KEY `fk_exam_item_page_id_exam_page_id` (`exam_page_id`),
  CONSTRAINT `fk_exam_item_page_id_exam_page_id` FOREIGN KEY (`exam_page_id`) REFERENCES `exam_page` (`id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `exam_item_response`
--

DROP TABLE IF EXISTS `exam_item_response`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exam_item_response` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `exam_item_id` char(36) NOT NULL,
  `exam_id` char(36) NOT NULL,
  `response` text NOT NULL,
  `sequence` int(11) NOT NULL,
  `is_valid` bit(1) NOT NULL DEFAULT b'0',
  `is_selected` bit(1) NOT NULL DEFAULT b'0',
  `score` int(11) DEFAULT NULL,
  `scoring_status` varchar(50) DEFAULT NULL,
  `scoring_rationale` text,
  `scoring_dimensions` varchar(4096) DEFAULT NULL,
  `created_at` timestamp(3) NOT NULL,
  `scored_at` timestamp(3) NULL DEFAULT NULL,
  `score_sent_at` datetime(3) DEFAULT NULL,
  `score_mark` char(36) DEFAULT NULL,
  `score_latency` bigint(20) NOT NULL DEFAULT '0',
  `is_marked_for_review` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `ix_created_at` (`created_at`),
  KEY `ix_exam_item_response_exam_item_id_created_at` (`exam_item_id`,`created_at`),
  KEY `ix_exam_item_response_exam_id` (`exam_id`),
  KEY `ix_exam_item_response_exam_id_exam_item_id` (`exam_id`,`exam_item_id`),
  CONSTRAINT `fk_exam_item_response_exam_id` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`),
  CONSTRAINT `fk_exam_item_response_exam_item_id` FOREIGN KEY (`exam_item_id`) REFERENCES `exam_item` (`id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `exam_page`
--

DROP TABLE IF EXISTS `exam_page`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exam_page` (
  `id` char(36) NOT NULL,
  `page_position` int(11) NOT NULL,
  `segment_key` varchar(250) NOT NULL,
  `item_group_key` varchar(25) NOT NULL,
  `exam_id` char(36) NOT NULL,
  `created_at` timestamp(3) NOT NULL,
  `group_items_required` int(11) NOT NULL DEFAULT '-1',
  PRIMARY KEY (`id`),
  KEY `fk_exam_page_exam_segment_segment_key` (`segment_key`),
  KEY `ix_exam_page_exam_id_page_position` (`exam_id`,`page_position`),
  CONSTRAINT `fk_exam_page_exam_id` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`),
  CONSTRAINT `fk_exam_page_exam_segment_segment_key` FOREIGN KEY (`segment_key`) REFERENCES `exam_segment` (`segment_key`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `exam_page_event`
--

DROP TABLE IF EXISTS `exam_page_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exam_page_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `exam_page_id` char(36) NOT NULL,
  `exam_id` char(36) NOT NULL,
  `deleted_at` datetime(3) DEFAULT NULL,
  `started_at` datetime(3) DEFAULT NULL,
  `created_at` timestamp(3) NOT NULL,
  `page_duration` bigint(20) NOT NULL DEFAULT '0',
  `visible` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ix_exam_page_event_id_exam_id` (`id`,`exam_id`),
  UNIQUE KEY `uix_exam_page_event_exam_id_exam_page_id_deleted_at` (`exam_id`,`exam_page_id`,`deleted_at`),
  KEY `fk_exam_page_id` (`exam_page_id`),
  KEY `ix_exam_page_event_exam_id_exam_page_id` (`exam_id`,`exam_page_id`),
  CONSTRAINT `fk_exam_page_event_exam_id` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`),
  CONSTRAINT `fk_exam_page_id` FOREIGN KEY (`exam_page_id`) REFERENCES `exam_page` (`id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `exam_print_request`
--

DROP TABLE IF EXISTS `exam_print_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exam_print_request` (
  `id` char(36) NOT NULL,
  `exam_id` char(36) NOT NULL,
  `session_id` char(36) NOT NULL,
  `type` varchar(50) NOT NULL,
  `value` varchar(250) NOT NULL,
  `item_position` int(11) NOT NULL,
  `page_position` int(11) NOT NULL,
  `parameters` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_at` timestamp(3) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_exam_print_request_exam_id_session_id` (`exam_id`,`session_id`),
  CONSTRAINT `fk_exam_request_exam_id_exam_id` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `exam_print_request_event`
--

DROP TABLE IF EXISTS `exam_print_request_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exam_print_request_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `exam_print_request_id` char(36) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `reason_denied` varchar(250) DEFAULT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'SUBMITTED',
  PRIMARY KEY (`id`),
  KEY `ix_exam_print_request_event_print_request_id_status` (`exam_print_request_id`,`status`),
  CONSTRAINT `fk_exam_request_event_exam_request_id` FOREIGN KEY (`exam_print_request_id`) REFERENCES `exam_print_request` (`id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `exam_scores`
--

DROP TABLE IF EXISTS `exam_scores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exam_scores` (
  `exam_id` char(36) NOT NULL,
  `measure_label` varchar(100) NOT NULL,
  `value` double DEFAULT NULL,
  `measure_of` varchar(150) NOT NULL,
  `use_for_ability` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`exam_id`,`measure_of`,`measure_label`),
  CONSTRAINT `fk_exam_scores_examid_exam` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `exam_segment`
--

DROP TABLE IF EXISTS `exam_segment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exam_segment` (
  `exam_id` char(36) NOT NULL,
  `segment_key` varchar(250) DEFAULT NULL,
  `segment_id` varchar(100) DEFAULT NULL,
  `segment_position` int(11) NOT NULL,
  `form_key` varchar(50) DEFAULT NULL,
  `form_id` varchar(200) DEFAULT NULL,
  `algorithm` varchar(50) DEFAULT NULL,
  `exam_item_count` int(11) DEFAULT NULL,
  `field_test_item_count` int(11) DEFAULT NULL,
  `field_test_items` text,
  `form_cohort` varchar(20) DEFAULT NULL,
  `pool_count` int(11) DEFAULT NULL,
  `created_at` timestamp(3) NOT NULL,
  PRIMARY KEY (`exam_id`,`segment_position`),
  KEY `ix_segment_form_key` (`segment_key`,`form_key`),
  KEY `ix_created_at` (`created_at`),
  CONSTRAINT `fk_exam_segment_examid_exam` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `exam_segment_event`
--

DROP TABLE IF EXISTS `exam_segment_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exam_segment_event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `exam_id` char(36) NOT NULL,
  `segment_position` int(11) NOT NULL,
  `satisfied` bit(1) NOT NULL DEFAULT b'0',
  `permeable` bit(1) NOT NULL DEFAULT b'0',
  `restore_permeable_condition` varchar(50) DEFAULT NULL,
  `exited_at` datetime(3) DEFAULT NULL,
  `item_pool` text,
  `created_at` timestamp(3) NOT NULL,
  `off_grade_items` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_exam_segment_event_exam_segment_id_segment_position` (`exam_id`,`segment_position`),
  CONSTRAINT `fk_exam_segment_event_exam_segment_id_segment_position` FOREIGN KEY (`exam_id`, `segment_position`) REFERENCES `exam_segment` (`exam_id`, `segment_position`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `examinee_attribute`
--

DROP TABLE IF EXISTS `examinee_attribute`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `examinee_attribute` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `exam_id` char(36) NOT NULL,
  `context` varchar(10) NOT NULL,
  `attribute_name` varchar(50) NOT NULL,
  `attribute_value` varchar(400) DEFAULT NULL,
  `created_at` timestamp(3) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_exam_attribute_exam_id_exam_id` (`exam_id`),
  CONSTRAINT `fk_exam_attribute_exam_id_exam_id` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `examinee_note`
--

DROP TABLE IF EXISTS `examinee_note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `examinee_note` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `exam_id` char(36) NOT NULL,
  `context` varchar(11) NOT NULL,
  `item_position` int(11) NOT NULL DEFAULT '0',
  `note` text NOT NULL,
  `created_at` timestamp(3) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_examinee_note_exam_id_context` (`exam_id`,`context`),
  CONSTRAINT `fk_examinee_notes_exam_id` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `examinee_relationship`
--

DROP TABLE IF EXISTS `examinee_relationship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `examinee_relationship` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `exam_id` char(36) NOT NULL,
  `attribute_name` varchar(50) NOT NULL,
  `attribute_value` varchar(500) NOT NULL,
  `attribute_relationship` varchar(100) NOT NULL,
  `context` varchar(10) NOT NULL,
  `created_at` timestamp(3) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_exam_relationship_exam_id_exam_id` (`exam_id`),
  CONSTRAINT `fk_exam_relationship_exam_id_exam_id` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `field_test_item_group`
--

DROP TABLE IF EXISTS `field_test_item_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `field_test_item_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `exam_id` char(36) NOT NULL,
  `position` int(11) NOT NULL,
  `item_count` int(11) DEFAULT NULL,
  `segment_id` varchar(100) NOT NULL,
  `segment_key` varchar(200) NOT NULL,
  `group_id` varchar(50) NOT NULL,
  `group_key` varchar(60) NOT NULL,
  `block_id` varchar(10) NOT NULL,
  `session_id` varbinary(16) NOT NULL,
  `language_code` varchar(50) NOT NULL,
  `created_at` timestamp(3) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_created_at` (`created_at`),
  KEY `ix_ftitem_cluster` (`exam_id`,`group_id`),
  KEY `ix_ftexamitemgroup_pk` (`segment_key`,`language_code`,`group_key`),
  KEY `ix_field_test_item_group_exam_id_segment_key_position` (`exam_id`,`segment_key`,`position`),
  CONSTRAINT `fk_field_test_item_group_exam_id_exam` FOREIGN KEY (`exam_id`) REFERENCES `exam` (`id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `field_test_item_group_event`
--

DROP TABLE IF EXISTS `field_test_item_group_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `field_test_item_group_event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `field_test_item_group_id` bigint(20) NOT NULL,
  `deleted_at` datetime(3) DEFAULT NULL,
  `position_administered` int(11) DEFAULT NULL,
  `administered_at` datetime(3) DEFAULT NULL,
  `created_at` timestamp(3) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `field_test_item_group_id` (`field_test_item_group_id`),
  CONSTRAINT `field_test_item_group_event_ibfk_1` FOREIGN KEY (`field_test_item_group_id`) REFERENCES `field_test_item_group` (`id`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `history` (
  `id` varbinary(16) NOT NULL,
  `client_name` varchar(100) NOT NULL,
  `student_id` bigint(20) NOT NULL,
  `subject` varchar(100) NOT NULL,
  `initial_ability` float DEFAULT NULL,
  `attempts` int(11) DEFAULT NULL,
  `segment_id` varchar(200) DEFAULT NULL,
  `changed_at` datetime(3) DEFAULT NULL,
  `segment_key` varchar(250) DEFAULT NULL,
  `exam_id` varbinary(16) DEFAULT NULL,
  `tested_grade` varchar(50) DEFAULT NULL,
  `login_ssid` varchar(50) DEFAULT NULL,
  `item_group_string` text,
  `initial_ability_delim` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_historyexamid` (`exam_id`),
  KEY `ix_historyloginssid` (`login_ssid`),
  KEY `ix_studenthistory` (`student_id`,`client_name`,`subject`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `schema_version`
--

DROP TABLE IF EXISTS `schema_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schema_version` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `schema_version_s_idx` (`success`)
) ENGINE=InnoDB;
/*!40101 SET character_set_client = @saved_cs_client */;

INSERT INTO schema_version(installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success)
VALUES(1, 1496077806, 'exam field test item group tuning', 'SQL', 'V1__exam_create_schema.sql', -1515252824, 'V1__exam_create_schema', UTC_TIMESTAMP(), 201, 1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-07 11:31:14
