-- MySQL dump 10.13  Distrib 5.7.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: smmp_dev
-- ------------------------------------------------------
-- Server version	5.7.23-log

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
-- Current Database: `smmp_dev`
--

/*!40000 DROP DATABASE IF EXISTS `smmp_dev`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `smmp_dev` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `smmp_dev`;

--
-- Table structure for table `exchange_datasinfo`
--

DROP TABLE IF EXISTS `exchange_datasinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exchange_datasinfo` (
  `id` varchar(32) NOT NULL,
  `table_name` varchar(64) DEFAULT NULL,
  `filed_name` varchar(128) DEFAULT NULL,
  `datasource_id` varchar(32) DEFAULT NULL,
  `movegroup_id` varchar(32) DEFAULT NULL,
  `type` varchar(128) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `flag` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exchange_datasinfo`
--

LOCK TABLES `exchange_datasinfo` WRITE;
/*!40000 ALTER TABLE `exchange_datasinfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `exchange_datasinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exchange_datasinfo_copy1`
--

DROP TABLE IF EXISTS `exchange_datasinfo_copy1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exchange_datasinfo_copy1` (
  `id` varchar(32) NOT NULL,
  `table_name` varchar(64) DEFAULT NULL,
  `filed_name` varchar(128) DEFAULT NULL,
  `datasource_id` varchar(32) DEFAULT NULL,
  `movegroup_id` varchar(32) DEFAULT NULL,
  `type` varchar(128) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `flag` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exchange_datasinfo_copy1`
--

LOCK TABLES `exchange_datasinfo_copy1` WRITE;
/*!40000 ALTER TABLE `exchange_datasinfo_copy1` DISABLE KEYS */;
/*!40000 ALTER TABLE `exchange_datasinfo_copy1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exchange_datasource`
--

DROP TABLE IF EXISTS `exchange_datasource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exchange_datasource` (
  `id` varchar(64) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `driver` varchar(128) DEFAULT NULL,
  `url` varchar(512) DEFAULT NULL,
  `username` varchar(128) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `type` varchar(64) DEFAULT NULL,
  `isTarget` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `flag` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exchange_datasource`
--

LOCK TABLES `exchange_datasource` WRITE;
/*!40000 ALTER TABLE `exchange_datasource` DISABLE KEYS */;
/*!40000 ALTER TABLE `exchange_datasource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exchange_datasource_copy1`
--

DROP TABLE IF EXISTS `exchange_datasource_copy1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exchange_datasource_copy1` (
  `id` varchar(64) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `driver` varchar(128) DEFAULT NULL,
  `url` varchar(512) DEFAULT NULL,
  `username` varchar(128) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `type` varchar(64) DEFAULT NULL,
  `isTarget` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `flag` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exchange_datasource_copy1`
--

LOCK TABLES `exchange_datasource_copy1` WRITE;
/*!40000 ALTER TABLE `exchange_datasource_copy1` DISABLE KEYS */;
/*!40000 ALTER TABLE `exchange_datasource_copy1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exchange_logs`
--

DROP TABLE IF EXISTS `exchange_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exchange_logs` (
  `id` varchar(32) NOT NULL,
  `type` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remote_addr` varchar(16) DEFAULT NULL,
  `source_id` varchar(64) DEFAULT NULL,
  `params` text,
  `exc_content` text,
  `remark` varchar(512) DEFAULT NULL,
  `flag` varchar(64) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exchange_logs`
--

LOCK TABLES `exchange_logs` WRITE;
/*!40000 ALTER TABLE `exchange_logs` DISABLE KEYS */;
/*!40000 ALTER TABLE `exchange_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exchange_logs_copy1`
--

DROP TABLE IF EXISTS `exchange_logs_copy1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exchange_logs_copy1` (
  `id` varchar(32) NOT NULL,
  `type` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remote_addr` varchar(16) DEFAULT NULL,
  `source_id` varchar(64) DEFAULT NULL,
  `params` text,
  `exc_content` text,
  `remark` varchar(512) DEFAULT NULL,
  `flag` varchar(64) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exchange_logs_copy1`
--

LOCK TABLES `exchange_logs_copy1` WRITE;
/*!40000 ALTER TABLE `exchange_logs_copy1` DISABLE KEYS */;
/*!40000 ALTER TABLE `exchange_logs_copy1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exchange_movegroup`
--

DROP TABLE IF EXISTS `exchange_movegroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exchange_movegroup` (
  `id` varchar(64) NOT NULL COMMENT 'id',
  `name` varchar(64) DEFAULT NULL COMMENT '分组名称',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `flag` varchar(64) DEFAULT NULL COMMENT '标记',
  `state` varchar(64) DEFAULT NULL COMMENT '状态',
  `operate_date` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exchange_movegroup`
--

LOCK TABLES `exchange_movegroup` WRITE;
/*!40000 ALTER TABLE `exchange_movegroup` DISABLE KEYS */;
/*!40000 ALTER TABLE `exchange_movegroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exchange_movegroup_copy1`
--

DROP TABLE IF EXISTS `exchange_movegroup_copy1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exchange_movegroup_copy1` (
  `id` varchar(64) NOT NULL COMMENT 'id',
  `name` varchar(64) DEFAULT NULL COMMENT '分组名称',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `flag` varchar(64) DEFAULT NULL COMMENT '标记',
  `state` varchar(64) DEFAULT NULL COMMENT '状态',
  `operate_date` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exchange_movegroup_copy1`
--

LOCK TABLES `exchange_movegroup_copy1` WRITE;
/*!40000 ALTER TABLE `exchange_movegroup_copy1` DISABLE KEYS */;
/*!40000 ALTER TABLE `exchange_movegroup_copy1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exchange_transfernexus`
--

DROP TABLE IF EXISTS `exchange_transfernexus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exchange_transfernexus` (
  `id` varchar(32) NOT NULL,
  `movegroup_id` varchar(32) DEFAULT NULL,
  `source_ds_id` varchar(32) DEFAULT NULL,
  `source_tableName` varchar(128) DEFAULT NULL,
  `source_filedName` varchar(128) DEFAULT NULL,
  `target_ds_id` varchar(32) DEFAULT NULL,
  `target_tableName` varchar(128) DEFAULT NULL,
  `target_filedName` varchar(128) DEFAULT NULL,
  `regulation` varchar(128) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `flag` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exchange_transfernexus`
--

LOCK TABLES `exchange_transfernexus` WRITE;
/*!40000 ALTER TABLE `exchange_transfernexus` DISABLE KEYS */;
/*!40000 ALTER TABLE `exchange_transfernexus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exchange_transfernexus_copy1`
--

DROP TABLE IF EXISTS `exchange_transfernexus_copy1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exchange_transfernexus_copy1` (
  `id` varchar(32) NOT NULL,
  `movegroup_id` varchar(32) DEFAULT NULL,
  `source_ds_id` varchar(32) DEFAULT NULL,
  `source_tableName` varchar(128) DEFAULT NULL,
  `source_filedName` varchar(128) DEFAULT NULL,
  `target_ds_id` varchar(32) DEFAULT NULL,
  `target_tableName` varchar(128) DEFAULT NULL,
  `target_filedName` varchar(128) DEFAULT NULL,
  `regulation` varchar(128) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `flag` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exchange_transfernexus_copy1`
--

LOCK TABLES `exchange_transfernexus_copy1` WRITE;
/*!40000 ALTER TABLE `exchange_transfernexus_copy1` DISABLE KEYS */;
/*!40000 ALTER TABLE `exchange_transfernexus_copy1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sm_address`
--

DROP TABLE IF EXISTS `sm_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sm_address` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `type` varchar(64) NOT NULL,
  `country` varchar(32) DEFAULT NULL,
  `province` varchar(32) DEFAULT NULL,
  `city` varchar(32) DEFAULT NULL,
  `district` varchar(32) DEFAULT NULL,
  `detail` varchar(128) DEFAULT NULL,
  `zipcode` varchar(32) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `flag` varchar(64) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sm_address`
--

LOCK TABLES `sm_address` WRITE;
/*!40000 ALTER TABLE `sm_address` DISABLE KEYS */;
/*!40000 ALTER TABLE `sm_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sm_association`
--

DROP TABLE IF EXISTS `sm_association`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sm_association` (
  `id` varchar(64) NOT NULL,
  `parent_id` varchar(64) NOT NULL,
  `parent_ids` varchar(2048) DEFAULT NULL,
  `name` varchar(256) NOT NULL,
  `logo` varchar(256) DEFAULT NULL,
  `type` varchar(64) DEFAULT NULL,
  `build_date` datetime DEFAULT NULL,
  `summary` text,
  `master` varchar(64) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `phone` varchar(64) DEFAULT NULL,
  `address` varchar(512) DEFAULT NULL,
  `sum` int(11) DEFAULT NULL,
  `wcaccount` varchar(64) DEFAULT NULL,
  `wcigroup` varchar(64) DEFAULT NULL,
  `constitution` text,
  `province` varchar(64) DEFAULT NULL,
  `city` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `flag` varchar(64) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sm_association`
--

LOCK TABLES `sm_association` WRITE;
/*!40000 ALTER TABLE `sm_association` DISABLE KEYS */;
/*!40000 ALTER TABLE `sm_association` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sm_card`
--

DROP TABLE IF EXISTS `sm_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sm_card` (
  `id` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `type` varchar(64) DEFAULT NULL,
  `start_level` int(11) DEFAULT NULL,
  `end_level` int(11) DEFAULT NULL,
  `front_font` varchar(64) DEFAULT NULL,
  `front_font_color` varchar(64) DEFAULT NULL,
  `front_font_style` varchar(64) DEFAULT NULL,
  `back_font` varchar(64) DEFAULT NULL,
  `back_font_color` varchar(64) DEFAULT NULL,
  `back_font_style` varchar(64) DEFAULT NULL,
  `card_style_line` varchar(64) DEFAULT NULL,
  `card_style_radius` varchar(64) DEFAULT NULL,
  `card_style_opacity` varchar(64) DEFAULT NULL,
  `front_background` varchar(256) DEFAULT NULL,
  `back_background` varchar(256) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `flag` varchar(64) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sm_card`
--

LOCK TABLES `sm_card` WRITE;
/*!40000 ALTER TABLE `sm_card` DISABLE KEYS */;
/*!40000 ALTER TABLE `sm_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sm_contact`
--

DROP TABLE IF EXISTS `sm_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sm_contact` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `type` varchar(64) DEFAULT NULL,
  `contact` varchar(128) DEFAULT NULL,
  `is_default` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT '0',
  `flag` varchar(64) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sm_contact`
--

LOCK TABLES `sm_contact` WRITE;
/*!40000 ALTER TABLE `sm_contact` DISABLE KEYS */;
/*!40000 ALTER TABLE `sm_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sm_education`
--

DROP TABLE IF EXISTS `sm_education`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sm_education` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `type` varchar(64) DEFAULT NULL,
  `school` varchar(32) DEFAULT NULL,
  `college` varchar(32) DEFAULT NULL,
  `series` varchar(32) DEFAULT NULL,
  `temp_series` varchar(64) DEFAULT NULL,
  `specialty` varchar(32) DEFAULT NULL,
  `temp_specialty` varchar(64) DEFAULT NULL,
  `classes` varchar(64) DEFAULT NULL,
  `academy` varchar(64) DEFAULT NULL,
  `edu_model` varchar(64) DEFAULT NULL,
  `edu_record` varchar(64) DEFAULT NULL,
  `degree_type` varchar(32) DEFAULT NULL,
  `degree` varchar(64) DEFAULT NULL,
  `edu_type` varchar(64) DEFAULT NULL,
  `student_no` varchar(32) DEFAULT NULL,
  `schoollen` varchar(64) DEFAULT NULL,
  `startdate` datetime DEFAULT NULL,
  `enddate` datetime DEFAULT NULL,
  `is_default` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(64) DEFAULT NULL,
  `flag` varchar(64) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sm_education`
--

LOCK TABLES `sm_education` WRITE;
/*!40000 ALTER TABLE `sm_education` DISABLE KEYS */;
/*!40000 ALTER TABLE `sm_education` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sm_experience`
--

DROP TABLE IF EXISTS `sm_experience`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sm_experience` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `name` varchar(64) NOT NULL,
  `position` varchar(64) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `content` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(64) DEFAULT NULL,
  `flag` varchar(64) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sm_experience`
--

LOCK TABLES `sm_experience` WRITE;
/*!40000 ALTER TABLE `sm_experience` DISABLE KEYS */;
/*!40000 ALTER TABLE `sm_experience` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sm_family`
--

DROP TABLE IF EXISTS `sm_family`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sm_family` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `relation` varchar(64) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `sex` varchar(64) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `profession` varchar(64) DEFAULT NULL,
  `phone` varchar(16) DEFAULT NULL,
  `workplace` varchar(64) DEFAULT NULL,
  `isschool` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT '0',
  `flag` varchar(64) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sm_family`
--

LOCK TABLES `sm_family` WRITE;
/*!40000 ALTER TABLE `sm_family` DISABLE KEYS */;
/*!40000 ALTER TABLE `sm_family` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sm_famous`
--

DROP TABLE IF EXISTS `sm_famous`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sm_famous` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `sort` int(11) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `photo` varchar(32) DEFAULT NULL,
  `information` text,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT '0',
  `flag` varchar(64) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sm_famous`
--

LOCK TABLES `sm_famous` WRITE;
/*!40000 ALTER TABLE `sm_famous` DISABLE KEYS */;
/*!40000 ALTER TABLE `sm_famous` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sm_history_data`
--

DROP TABLE IF EXISTS `sm_history_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sm_history_data` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `other_school` varchar(64) DEFAULT NULL,
  `other_specialty` varchar(128) DEFAULT NULL,
  `other_degree` varchar(64) DEFAULT NULL,
  `join_date` datetime DEFAULT NULL,
  `degree_date` datetime DEFAULT NULL,
  `trust_unit` varchar(128) DEFAULT NULL,
  `before_school_unit` varchar(128) DEFAULT NULL,
  `native_adress` varchar(128) DEFAULT NULL,
  `person_love` varchar(512) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT '0',
  `flag` varchar(64) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sm_history_data`
--

LOCK TABLES `sm_history_data` WRITE;
/*!40000 ALTER TABLE `sm_history_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `sm_history_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sm_honor`
--

DROP TABLE IF EXISTS `sm_honor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sm_honor` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `type` varchar(64) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `industry` varchar(32) DEFAULT NULL,
  `infoval` text,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(64) DEFAULT NULL,
  `flag` varchar(64) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sm_honor`
--

LOCK TABLES `sm_honor` WRITE;
/*!40000 ALTER TABLE `sm_honor` DISABLE KEYS */;
/*!40000 ALTER TABLE `sm_honor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sm_invite_code`
--

DROP TABLE IF EXISTS `sm_invite_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sm_invite_code` (
  `id` varchar(64) NOT NULL,
  `sm_temp_id` varchar(64) DEFAULT NULL,
  `sys_user_id` varchar(64) DEFAULT NULL,
  `username` varchar(64) DEFAULT NULL,
  `invitecode` varchar(64) NOT NULL,
  `status` varchar(64) DEFAULT NULL,
  `send_user_id` varchar(64) DEFAULT NULL,
  `send_date` varchar(64) DEFAULT NULL,
  `active_date` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `flag` varchar(64) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sm_invite_code`
--

LOCK TABLES `sm_invite_code` WRITE;
/*!40000 ALTER TABLE `sm_invite_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `sm_invite_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sm_other`
--

DROP TABLE IF EXISTS `sm_other`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sm_other` (
  `id` varchar(32) NOT NULL,
  `name` varchar(64) DEFAULT NULL,
  `type` varchar(64) DEFAULT NULL,
  `value` varchar(256) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `flag` varchar(64) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sm_other`
--

LOCK TABLES `sm_other` WRITE;
/*!40000 ALTER TABLE `sm_other` DISABLE KEYS */;
/*!40000 ALTER TABLE `sm_other` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sm_other_info`
--

DROP TABLE IF EXISTS `sm_other_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sm_other_info` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `other_id` varchar(32) DEFAULT NULL,
  `value` varchar(256) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `flag` varchar(64) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sm_other_info`
--

LOCK TABLES `sm_other_info` WRITE;
/*!40000 ALTER TABLE `sm_other_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `sm_other_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sm_politics`
--

DROP TABLE IF EXISTS `sm_politics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sm_politics` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `position` varchar(64) DEFAULT NULL,
  `infoval` varchar(512) DEFAULT NULL,
  `startdate` datetime DEFAULT NULL,
  `enddate` datetime DEFAULT NULL,
  `type` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT '0',
  `flag` varchar(64) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sm_politics`
--

LOCK TABLES `sm_politics` WRITE;
/*!40000 ALTER TABLE `sm_politics` DISABLE KEYS */;
/*!40000 ALTER TABLE `sm_politics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sm_profession`
--

DROP TABLE IF EXISTS `sm_profession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sm_profession` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `country` varchar(32) DEFAULT NULL,
  `province` varchar(32) DEFAULT NULL,
  `city` varchar(32) DEFAULT NULL,
  `district` varchar(32) DEFAULT NULL,
  `detail` varchar(256) DEFAULT NULL,
  `workplace` varchar(256) DEFAULT NULL,
  `nature` varchar(64) DEFAULT NULL,
  `industry` varchar(32) DEFAULT NULL,
  `department` varchar(64) DEFAULT NULL,
  `position` varchar(64) DEFAULT NULL,
  `telephone` varchar(64) DEFAULT NULL,
  `fax` varchar(64) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `status` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `flag` varchar(64) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sm_profession`
--

LOCK TABLES `sm_profession` WRITE;
/*!40000 ALTER TABLE `sm_profession` DISABLE KEYS */;
/*!40000 ALTER TABLE `sm_profession` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sm_schoolmate`
--

DROP TABLE IF EXISTS `sm_schoolmate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sm_schoolmate` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `name` varchar(64) NOT NULL,
  `eg_name` varchar(128) DEFAULT NULL,
  `pinyin` varchar(64) DEFAULT NULL,
  `sex` varchar(64) DEFAULT NULL,
  `true_photo` varchar(64) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `card_type` varchar(64) NOT NULL,
  `card_num` varchar(32) DEFAULT NULL,
  `nation` varchar(64) DEFAULT NULL,
  `type` varchar(64) NOT NULL,
  `card_id` varchar(64) DEFAULT NULL,
  `card_status` varchar(64) DEFAULT NULL,
  `complete` int(11) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `point` int(11) DEFAULT NULL,
  `don_fee` decimal(10,0) DEFAULT NULL,
  `is_show` varchar(64) DEFAULT NULL,
  `marks` varchar(1024) DEFAULT NULL,
  `mark_ids` varchar(1024) DEFAULT NULL,
  `openid` varchar(64) DEFAULT NULL,
  `pos_city` varchar(64) DEFAULT NULL,
  `pos_long` varchar(64) DEFAULT NULL,
  `pos_lat` varchar(64) DEFAULT NULL,
  `status` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `flag` varchar(64) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sm_schoolmate`
--

LOCK TABLES `sm_schoolmate` WRITE;
/*!40000 ALTER TABLE `sm_schoolmate` DISABLE KEYS */;
/*!40000 ALTER TABLE `sm_schoolmate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sm_social`
--

DROP TABLE IF EXISTS `sm_social`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sm_social` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `position` varchar(64) DEFAULT NULL,
  `infoval` varchar(512) DEFAULT NULL,
  `startdate` datetime DEFAULT NULL,
  `enddate` datetime DEFAULT NULL,
  `status` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT '0',
  `flag` varchar(64) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sm_social`
--

LOCK TABLES `sm_social` WRITE;
/*!40000 ALTER TABLE `sm_social` DISABLE KEYS */;
/*!40000 ALTER TABLE `sm_social` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_user` (
  `id` varchar(64) NOT NULL,
  `username` varchar(64) NOT NULL,
  `password` varchar(128) NOT NULL,
  `safecode` varchar(64) DEFAULT NULL,
  `virtual_name` varchar(64) NOT NULL,
  `idcard` varchar(20) DEFAULT NULL,
  `virtual_photo` varchar(1024) DEFAULT NULL,
  `mobile` varchar(16) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `user_type` varchar(64) NOT NULL,
  `user_source` varchar(64) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `flag` varchar(64) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-25 15:54:09
