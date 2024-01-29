CREATE DATABASE eventuate;

USE eventuate;


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cdc_monitoring
-- ----------------------------
DROP TABLE IF EXISTS `cdc_monitoring`;
CREATE TABLE `cdc_monitoring` (
  `reader_id` varchar(255) NOT NULL,
  `last_time` bigint DEFAULT NULL,
  PRIMARY KEY (`reader_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for entities
-- ----------------------------
DROP TABLE IF EXISTS `entities`;
CREATE TABLE `entities` (
  `entity_type` varchar(255) NOT NULL,
  `entity_id` varchar(255) NOT NULL,
  `entity_version` longtext NOT NULL,
  PRIMARY KEY (`entity_type`,`entity_id`),
  KEY `entities_idx` (`entity_type`,`entity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for events
-- ----------------------------
DROP TABLE IF EXISTS `events`;
CREATE TABLE `events` (
  `event_id` varchar(255) NOT NULL,
  `event_type` longtext,
  `event_data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `entity_type` varchar(255) NOT NULL,
  `entity_id` varchar(255) NOT NULL,
  `triggering_event` longtext,
  `metadata` longtext,
  `published` tinyint DEFAULT '0',
  PRIMARY KEY (`event_id`),
  KEY `events_idx` (`entity_type`,`entity_id`,`event_id`),
  KEY `events_published_idx` (`published`,`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` varchar(255) NOT NULL,
  `destination` longtext NOT NULL,
  `headers` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `payload` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `published` smallint DEFAULT '0',
  `message_partition` smallint DEFAULT NULL,
  `creation_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `message_published_idx` (`published`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for offset_store
-- ----------------------------
DROP TABLE IF EXISTS `offset_store`;
CREATE TABLE `offset_store` (
  `client_name` varchar(255) NOT NULL,
  `serialized_offset` longtext,
  PRIMARY KEY (`client_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for received_messages
-- ----------------------------
DROP TABLE IF EXISTS `received_messages`;
CREATE TABLE `received_messages` (
  `consumer_id` varchar(255) NOT NULL,
  `message_id` varchar(255) NOT NULL,
  `creation_time` bigint DEFAULT NULL,
  `published` smallint DEFAULT '0',
  PRIMARY KEY (`consumer_id`,`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for saga_instance
-- ----------------------------
DROP TABLE IF EXISTS `saga_instance`;
CREATE TABLE `saga_instance` (
  `saga_type` varchar(255) NOT NULL,
  `saga_id` varchar(100) NOT NULL,
  `state_name` varchar(100) NOT NULL,
  `last_request_id` varchar(100) DEFAULT NULL,
  `end_state` int DEFAULT NULL,
  `compensating` int DEFAULT NULL,
  `failed` int DEFAULT NULL,
  `saga_data_type` varchar(1000) NOT NULL,
  `saga_data_json` varchar(1000) NOT NULL,
  PRIMARY KEY (`saga_type`,`saga_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for saga_instance_participants
-- ----------------------------
DROP TABLE IF EXISTS `saga_instance_participants`;
CREATE TABLE `saga_instance_participants` (
  `saga_type` varchar(255) NOT NULL,
  `saga_id` varchar(100) NOT NULL,
  `destination` varchar(100) NOT NULL,
  `resource` varchar(100) NOT NULL,
  PRIMARY KEY (`saga_type`,`saga_id`,`destination`,`resource`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for saga_lock_table
-- ----------------------------
DROP TABLE IF EXISTS `saga_lock_table`;
CREATE TABLE `saga_lock_table` (
  `target` varchar(100) NOT NULL,
  `saga_type` varchar(255) NOT NULL,
  `saga_Id` varchar(100) NOT NULL,
  PRIMARY KEY (`target`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for saga_stash_table
-- ----------------------------
DROP TABLE IF EXISTS `saga_stash_table`;
CREATE TABLE `saga_stash_table` (
  `message_id` varchar(100) NOT NULL,
  `target` varchar(100) NOT NULL,
  `saga_type` varchar(255) NOT NULL,
  `saga_id` varchar(100) NOT NULL,
  `message_headers` varchar(1000) NOT NULL,
  `message_payload` varchar(1000) NOT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for snapshots
-- ----------------------------
DROP TABLE IF EXISTS `snapshots`;
CREATE TABLE `snapshots` (
  `entity_type` varchar(255) NOT NULL,
  `entity_id` varchar(255) NOT NULL,
  `entity_version` varchar(255) NOT NULL,
  `snapshot_type` longtext NOT NULL,
  `snapshot_json` longtext NOT NULL,
  `triggering_events` longtext,
  PRIMARY KEY (`entity_type`,`entity_id`,`entity_version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
