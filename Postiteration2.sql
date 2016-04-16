CREATE DATABASE  IF NOT EXISTS `mavs` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mavs`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: mavs
-- ------------------------------------------------------
-- Server version	5.7.11-log

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
-- Table structure for table `advising_schedule`
--

DROP TABLE IF EXISTS `advising_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advising_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `date` date NOT NULL,
  `start` time NOT NULL,
  `end` time NOT NULL,
  `studentId` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `userId` (`userId`,`date`,`start`),
  CONSTRAINT `advising_schedule_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user_advisor` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6857 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advising_schedule`
--

LOCK TABLES `advising_schedule` WRITE;
/*!40000 ALTER TABLE `advising_schedule` DISABLE KEYS */;
INSERT INTO `advising_schedule` VALUES (6635,96,'2016-04-12','12:00:00','12:05:00',NULL),(6636,96,'2016-04-12','12:05:00','12:10:00',NULL),(6637,96,'2016-04-12','12:10:00','12:15:00',NULL),(6638,96,'2016-04-12','12:15:00','12:20:00',NULL),(6639,96,'2016-04-12','12:20:00','12:25:00',NULL),(6640,96,'2016-04-12','12:25:00','12:30:00',NULL),(6641,96,'2016-04-12','12:30:00','12:35:00',NULL),(6642,96,'2016-04-12','12:35:00','12:40:00',NULL),(6643,96,'2016-04-12','12:40:00','12:45:00',NULL),(6644,96,'2016-04-12','12:45:00','12:50:00',NULL),(6645,96,'2016-04-12','12:50:00','12:55:00',NULL),(6646,96,'2016-04-12','12:55:00','13:00:00',NULL),(6647,96,'2016-04-12','13:00:00','13:05:00',NULL),(6648,96,'2016-04-12','13:05:00','13:10:00',NULL),(6649,96,'2016-04-12','13:10:00','13:15:00',NULL),(6650,96,'2016-04-12','13:15:00','13:20:00',NULL),(6651,96,'2016-04-12','13:20:00','13:25:00',NULL),(6652,96,'2016-04-12','13:25:00','13:30:00',NULL),(6653,96,'2016-04-12','13:30:00','13:35:00',NULL),(6654,96,'2016-04-12','13:35:00','13:40:00',NULL),(6655,96,'2016-04-12','13:40:00','13:45:00',NULL),(6656,96,'2016-04-12','13:45:00','13:50:00',NULL),(6657,96,'2016-04-12','13:50:00','13:55:00',NULL),(6658,96,'2016-04-12','13:55:00','14:00:00',NULL),(6659,96,'2016-04-12','14:00:00','14:05:00',NULL),(6660,96,'2016-04-12','14:05:00','14:10:00',NULL),(6661,96,'2016-04-12','14:10:00','14:15:00',NULL),(6662,96,'2016-04-12','14:15:00','14:20:00',NULL),(6663,96,'2016-04-12','14:20:00','14:25:00',NULL),(6664,96,'2016-04-12','14:25:00','14:30:00',NULL),(6665,96,'2016-04-12','14:30:00','14:35:00',NULL),(6666,96,'2016-04-12','14:35:00','14:40:00',NULL),(6667,96,'2016-04-12','14:40:00','14:45:00',NULL),(6668,96,'2016-04-12','14:45:00','14:50:00',NULL),(6669,96,'2016-04-12','14:50:00','14:55:00',NULL),(6670,96,'2016-04-12','14:55:00','15:00:00',NULL),(6671,96,'2016-04-19','12:00:00','12:05:00',NULL),(6672,96,'2016-04-19','12:05:00','12:10:00',NULL),(6673,96,'2016-04-19','12:10:00','12:15:00',NULL),(6674,96,'2016-04-19','12:15:00','12:20:00',NULL),(6675,96,'2016-04-19','12:20:00','12:25:00',NULL),(6676,96,'2016-04-19','12:25:00','12:30:00',NULL),(6677,96,'2016-04-19','12:30:00','12:35:00',NULL),(6678,96,'2016-04-19','12:35:00','12:40:00',NULL),(6679,96,'2016-04-19','12:40:00','12:45:00',NULL),(6680,96,'2016-04-19','12:45:00','12:50:00',NULL),(6681,96,'2016-04-19','12:50:00','12:55:00',NULL),(6682,96,'2016-04-19','12:55:00','13:00:00',NULL),(6683,96,'2016-04-19','13:00:00','13:05:00',NULL),(6684,96,'2016-04-19','13:05:00','13:10:00',NULL),(6685,96,'2016-04-19','13:10:00','13:15:00',NULL),(6686,96,'2016-04-19','13:15:00','13:20:00',NULL),(6687,96,'2016-04-19','13:20:00','13:25:00',NULL),(6688,96,'2016-04-19','13:25:00','13:30:00',NULL),(6689,96,'2016-04-19','13:30:00','13:35:00',NULL),(6690,96,'2016-04-19','13:35:00','13:40:00',NULL),(6691,96,'2016-04-19','13:40:00','13:45:00',NULL),(6692,96,'2016-04-19','13:45:00','13:50:00',NULL),(6693,96,'2016-04-19','13:50:00','13:55:00',NULL),(6694,96,'2016-04-19','13:55:00','14:00:00',NULL),(6695,96,'2016-04-19','14:00:00','14:05:00',NULL),(6696,96,'2016-04-19','14:05:00','14:10:00',NULL),(6697,96,'2016-04-19','14:10:00','14:15:00',NULL),(6698,96,'2016-04-19','14:15:00','14:20:00',NULL),(6699,96,'2016-04-19','14:20:00','14:25:00',NULL),(6700,96,'2016-04-19','14:25:00','14:30:00',NULL),(6701,96,'2016-04-19','14:30:00','14:35:00',NULL),(6702,96,'2016-04-19','14:35:00','14:40:00',NULL),(6703,96,'2016-04-19','14:40:00','14:45:00',NULL),(6704,96,'2016-04-19','14:45:00','14:50:00',NULL),(6705,96,'2016-04-19','14:50:00','14:55:00',NULL),(6706,96,'2016-04-19','14:55:00','15:00:00',NULL),(6707,96,'2016-04-26','12:00:00','12:05:00',NULL),(6708,96,'2016-04-26','12:05:00','12:10:00',NULL),(6709,96,'2016-04-26','12:10:00','12:15:00',NULL),(6710,96,'2016-04-26','12:15:00','12:20:00',NULL),(6711,96,'2016-04-26','12:20:00','12:25:00',NULL),(6712,96,'2016-04-26','12:25:00','12:30:00',NULL),(6713,96,'2016-04-26','12:30:00','12:35:00',NULL),(6714,96,'2016-04-26','12:35:00','12:40:00',NULL),(6715,96,'2016-04-26','12:40:00','12:45:00',NULL),(6716,96,'2016-04-26','12:45:00','12:50:00',NULL),(6717,96,'2016-04-26','12:50:00','12:55:00',NULL),(6718,96,'2016-04-26','12:55:00','13:00:00',NULL),(6719,96,'2016-04-26','13:00:00','13:05:00',NULL),(6720,96,'2016-04-26','13:05:00','13:10:00',NULL),(6721,96,'2016-04-26','13:10:00','13:15:00',NULL),(6722,96,'2016-04-26','13:15:00','13:20:00',NULL),(6723,96,'2016-04-26','13:20:00','13:25:00',NULL),(6724,96,'2016-04-26','13:25:00','13:30:00',NULL),(6725,96,'2016-04-26','13:30:00','13:35:00',NULL),(6726,96,'2016-04-26','13:35:00','13:40:00',NULL),(6727,96,'2016-04-26','13:40:00','13:45:00',NULL),(6728,96,'2016-04-26','13:45:00','13:50:00',NULL),(6729,96,'2016-04-26','13:50:00','13:55:00',NULL),(6730,96,'2016-04-26','13:55:00','14:00:00',NULL),(6731,96,'2016-04-26','14:00:00','14:05:00',NULL),(6732,96,'2016-04-26','14:05:00','14:10:00',NULL),(6733,96,'2016-04-26','14:10:00','14:15:00',NULL),(6734,96,'2016-04-26','14:15:00','14:20:00',NULL),(6735,96,'2016-04-26','14:20:00','14:25:00',NULL),(6736,96,'2016-04-26','14:25:00','14:30:00',NULL),(6737,96,'2016-04-26','14:30:00','14:35:00',NULL),(6738,96,'2016-04-26','14:35:00','14:40:00',NULL),(6739,96,'2016-04-26','14:40:00','14:45:00',NULL),(6740,96,'2016-04-26','14:45:00','14:50:00',NULL),(6741,96,'2016-04-26','14:50:00','14:55:00',NULL),(6742,96,'2016-04-26','14:55:00','15:00:00',NULL),(6743,96,'2016-04-14','15:30:00','15:35:00',NULL),(6744,96,'2016-04-14','15:35:00','15:40:00',NULL),(6745,96,'2016-04-14','15:40:00','15:45:00',NULL),(6746,96,'2016-04-14','15:45:00','15:50:00',NULL),(6747,96,'2016-04-14','15:50:00','15:55:00',NULL),(6748,96,'2016-04-14','15:55:00','16:00:00',NULL),(6749,96,'2016-04-14','16:00:00','16:05:00',NULL),(6750,96,'2016-04-14','16:05:00','16:10:00',NULL),(6751,96,'2016-04-14','16:10:00','16:15:00',NULL),(6752,96,'2016-04-14','16:15:00','16:20:00',NULL),(6753,96,'2016-04-14','16:20:00','16:25:00',NULL),(6754,96,'2016-04-14','16:25:00','16:30:00',NULL),(6755,96,'2016-04-21','15:30:00','15:35:00',NULL),(6756,96,'2016-04-21','15:35:00','15:40:00',NULL),(6757,96,'2016-04-21','15:40:00','15:45:00',NULL),(6758,96,'2016-04-21','15:45:00','15:50:00',NULL),(6759,96,'2016-04-21','15:50:00','15:55:00','J9KYTPYQjxXrgUclN2dxug=='),(6760,96,'2016-04-21','15:55:00','16:00:00','J9KYTPYQjxXrgUclN2dxug=='),(6761,96,'2016-04-21','16:00:00','16:05:00',NULL),(6762,96,'2016-04-21','16:05:00','16:10:00',NULL),(6763,96,'2016-04-21','16:10:00','16:15:00',NULL),(6764,96,'2016-04-21','16:15:00','16:20:00',NULL),(6765,96,'2016-04-21','16:20:00','16:25:00',NULL),(6766,96,'2016-04-21','16:25:00','16:30:00',NULL),(6767,104,'2016-04-15','12:30:00','12:35:00',NULL),(6768,104,'2016-04-15','12:35:00','12:40:00',NULL),(6769,104,'2016-04-15','12:40:00','12:45:00',NULL),(6770,104,'2016-04-15','12:45:00','12:50:00',NULL),(6771,104,'2016-04-15','12:50:00','12:55:00',NULL),(6772,104,'2016-04-15','12:55:00','13:00:00',NULL),(6773,104,'2016-04-15','13:00:00','13:05:00',NULL),(6774,104,'2016-04-15','13:05:00','13:10:00',NULL),(6775,104,'2016-04-15','13:10:00','13:15:00',NULL),(6776,104,'2016-04-15','13:15:00','13:20:00',NULL),(6777,104,'2016-04-15','13:20:00','13:25:00',NULL),(6778,104,'2016-04-15','13:25:00','13:30:00',NULL),(6779,104,'2016-04-15','13:30:00','13:35:00',NULL),(6780,104,'2016-04-15','13:35:00','13:40:00',NULL),(6781,104,'2016-04-15','13:40:00','13:45:00',NULL),(6782,104,'2016-04-15','13:45:00','13:50:00',NULL),(6783,104,'2016-04-15','13:50:00','13:55:00',NULL),(6784,104,'2016-04-15','13:55:00','14:00:00',NULL),(6785,104,'2016-04-22','12:30:00','12:35:00',NULL),(6786,104,'2016-04-22','12:35:00','12:40:00',NULL),(6787,104,'2016-04-22','12:40:00','12:45:00',NULL),(6788,104,'2016-04-22','12:45:00','12:50:00',NULL),(6789,104,'2016-04-22','12:50:00','12:55:00',NULL),(6790,104,'2016-04-22','12:55:00','13:00:00',NULL),(6791,104,'2016-04-22','13:00:00','13:05:00',NULL),(6792,104,'2016-04-22','13:05:00','13:10:00',NULL),(6793,104,'2016-04-22','13:10:00','13:15:00',NULL),(6794,104,'2016-04-22','13:15:00','13:20:00',NULL),(6795,104,'2016-04-22','13:20:00','13:25:00',NULL),(6796,104,'2016-04-22','13:25:00','13:30:00',NULL),(6797,104,'2016-04-22','13:30:00','13:35:00',NULL),(6798,104,'2016-04-22','13:35:00','13:40:00',NULL),(6799,104,'2016-04-22','13:40:00','13:45:00',NULL),(6800,104,'2016-04-22','13:45:00','13:50:00',NULL),(6801,104,'2016-04-22','13:50:00','13:55:00',NULL),(6802,104,'2016-04-22','13:55:00','14:00:00',NULL),(6803,104,'2016-04-29','12:30:00','12:35:00',NULL),(6804,104,'2016-04-29','12:35:00','12:40:00',NULL),(6805,104,'2016-04-29','12:40:00','12:45:00',NULL),(6806,104,'2016-04-29','12:45:00','12:50:00',NULL),(6807,104,'2016-04-29','12:50:00','12:55:00',NULL),(6808,104,'2016-04-29','12:55:00','13:00:00',NULL),(6809,104,'2016-04-29','13:00:00','13:05:00',NULL),(6810,104,'2016-04-29','13:05:00','13:10:00',NULL),(6811,104,'2016-04-29','13:10:00','13:15:00',NULL),(6812,104,'2016-04-29','13:15:00','13:20:00',NULL),(6813,104,'2016-04-29','13:20:00','13:25:00',NULL),(6814,104,'2016-04-29','13:25:00','13:30:00',NULL),(6815,104,'2016-04-29','13:30:00','13:35:00',NULL),(6816,104,'2016-04-29','13:35:00','13:40:00',NULL),(6817,104,'2016-04-29','13:40:00','13:45:00',NULL),(6818,104,'2016-04-29','13:45:00','13:50:00',NULL),(6819,104,'2016-04-29','13:50:00','13:55:00',NULL),(6820,104,'2016-04-29','13:55:00','14:00:00',NULL),(6821,104,'2016-05-06','12:30:00','12:35:00',NULL),(6822,104,'2016-05-06','12:35:00','12:40:00',NULL),(6823,104,'2016-05-06','12:40:00','12:45:00',NULL),(6824,104,'2016-05-06','12:45:00','12:50:00',NULL),(6825,104,'2016-05-06','12:50:00','12:55:00',NULL),(6826,104,'2016-05-06','12:55:00','13:00:00',NULL),(6827,104,'2016-05-06','13:00:00','13:05:00',NULL),(6828,104,'2016-05-06','13:05:00','13:10:00',NULL),(6829,104,'2016-05-06','13:10:00','13:15:00',NULL),(6830,104,'2016-05-06','13:15:00','13:20:00',NULL),(6831,104,'2016-05-06','13:20:00','13:25:00',NULL),(6832,104,'2016-05-06','13:25:00','13:30:00',NULL),(6833,104,'2016-05-06','13:30:00','13:35:00',NULL),(6834,104,'2016-05-06','13:35:00','13:40:00',NULL),(6835,104,'2016-05-06','13:40:00','13:45:00',NULL),(6836,104,'2016-05-06','13:45:00','13:50:00',NULL),(6837,104,'2016-05-06','13:50:00','13:55:00',NULL),(6838,104,'2016-05-06','13:55:00','14:00:00',NULL),(6839,104,'2016-05-13','12:30:00','12:35:00',NULL),(6840,104,'2016-05-13','12:35:00','12:40:00',NULL),(6841,104,'2016-05-13','12:40:00','12:45:00',NULL),(6842,104,'2016-05-13','12:45:00','12:50:00',NULL),(6843,104,'2016-05-13','12:50:00','12:55:00',NULL),(6844,104,'2016-05-13','12:55:00','13:00:00',NULL),(6845,104,'2016-05-13','13:00:00','13:05:00',NULL),(6846,104,'2016-05-13','13:05:00','13:10:00',NULL),(6847,104,'2016-05-13','13:10:00','13:15:00',NULL),(6848,104,'2016-05-13','13:15:00','13:20:00',NULL),(6849,104,'2016-05-13','13:20:00','13:25:00',NULL),(6850,104,'2016-05-13','13:25:00','13:30:00',NULL),(6851,104,'2016-05-13','13:30:00','13:35:00',NULL),(6852,104,'2016-05-13','13:35:00','13:40:00',NULL),(6853,104,'2016-05-13','13:40:00','13:45:00',NULL),(6854,104,'2016-05-13','13:45:00','13:50:00',NULL),(6855,104,'2016-05-13','13:50:00','13:55:00',NULL),(6856,104,'2016-05-13','13:55:00','14:00:00',NULL);
/*!40000 ALTER TABLE `advising_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointment_types`
--

DROP TABLE IF EXISTS `appointment_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appointment_types` (
  `userId` int(11) NOT NULL,
  `type` varchar(45) NOT NULL,
  `duration` int(11) NOT NULL,
  `colortype` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`userId`,`type`),
  UNIQUE KEY `userId` (`userId`,`type`),
  CONSTRAINT `appointment_types_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user_advisor` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment_types`
--

LOCK TABLES `appointment_types` WRITE;
/*!40000 ALTER TABLE `appointment_types` DISABLE KEYS */;
INSERT INTO `appointment_types` VALUES (96,'Add class',5,'green'),(96,'swap class',10,'brown');
/*!40000 ALTER TABLE `appointment_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointments`
--

DROP TABLE IF EXISTS `appointments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appointments` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `advisor_userId` int(11) NOT NULL,
  `student_userId` int(11) DEFAULT NULL,
  `date` date NOT NULL,
  `start` time NOT NULL,
  `end` time NOT NULL,
  `type` varchar(45) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `studentId` varchar(256) NOT NULL,
  `student_email` varchar(50) DEFAULT NULL,
  `student_cell` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `advisor_userId` (`advisor_userId`,`type`),
  CONSTRAINT `appointments_ibfk_1` FOREIGN KEY (`advisor_userId`) REFERENCES `user_advisor` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `appointments_ibfk_2` FOREIGN KEY (`advisor_userId`, `type`) REFERENCES `appointment_types` (`userId`, `type`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6760 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointments`
--

LOCK TABLES `appointments` WRITE;
/*!40000 ALTER TABLE `appointments` DISABLE KEYS */;
INSERT INTO `appointments` VALUES (6759,96,101,'2016-04-21','15:50:00','16:00:00','swap class','description','J9KYTPYQjxXrgUclN2dxug==','maithili.deshpande@mavs.uta.edu','6824563698');
/*!40000 ALTER TABLE `appointments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `degree_type`
--

DROP TABLE IF EXISTS `degree_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `degree_type` (
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`name`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `degree_type`
--

LOCK TABLES `degree_type` WRITE;
/*!40000 ALTER TABLE `degree_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `degree_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `degree_type_user`
--

DROP TABLE IF EXISTS `degree_type_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `degree_type_user` (
  `name` varchar(45) NOT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`name`,`userId`),
  UNIQUE KEY `userId` (`userId`,`name`),
  CONSTRAINT `degree_type_user_ibfk_1` FOREIGN KEY (`name`) REFERENCES `degree_type` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `degree_type_user_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `degree_type_user`
--

LOCK TABLES `degree_type_user` WRITE;
/*!40000 ALTER TABLE `degree_type_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `degree_type_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`name`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES ('ARCH'),('CSE'),('MAE'),('MATH');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department_user`
--

DROP TABLE IF EXISTS `department_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department_user` (
  `name` varchar(45) NOT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`name`,`userId`),
  UNIQUE KEY `userId` (`userId`,`name`),
  CONSTRAINT `department_user_ibfk_1` FOREIGN KEY (`name`) REFERENCES `department` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `department_user_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department_user`
--

LOCK TABLES `department_user` WRITE;
/*!40000 ALTER TABLE `department_user` DISABLE KEYS */;
INSERT INTO `department_user` VALUES ('CSE',77),('CSE',96),('CSE',100),('CSE',101),('CSE',104),('MAE',100),('MAE',101);
/*!40000 ALTER TABLE `department_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `major`
--

DROP TABLE IF EXISTS `major`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `major` (
  `name` varchar(45) NOT NULL,
  `dep_name` varchar(45) NOT NULL,
  PRIMARY KEY (`name`),
  UNIQUE KEY `name` (`name`),
  KEY `dep_name` (`dep_name`),
  CONSTRAINT `major_ibfk_1` FOREIGN KEY (`dep_name`) REFERENCES `department` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `major`
--

LOCK TABLES `major` WRITE;
/*!40000 ALTER TABLE `major` DISABLE KEYS */;
INSERT INTO `major` VALUES ('Architecture','ARCH'),('Interior Design','ARCH'),('Landscape Architecture','ARCH'),('Computer Engineering','CSE'),('Computer Science','CSE'),('Software Engineering','CSE'),('Aerospace Engineering','MAE'),('Mechanical Engineering','MAE'),('Mathematics','MATH');
/*!40000 ALTER TABLE `major` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `major_user`
--

DROP TABLE IF EXISTS `major_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `major_user` (
  `name` varchar(45) NOT NULL,
  `userId` int(11) NOT NULL,
  PRIMARY KEY (`name`,`userId`),
  UNIQUE KEY `userId` (`userId`,`name`),
  CONSTRAINT `major_user_ibfk_1` FOREIGN KEY (`name`) REFERENCES `major` (`name`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `major_user_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `major_user`
--

LOCK TABLES `major_user` WRITE;
/*!40000 ALTER TABLE `major_user` DISABLE KEYS */;
INSERT INTO `major_user` VALUES ('Computer Engineering',100),('Computer Engineering',101),('Computer Science',96);
/*!40000 ALTER TABLE `major_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `minor_user`
--

DROP TABLE IF EXISTS `minor_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `minor_user` (
  `name` varchar(45) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `minor_user`
--

LOCK TABLES `minor_user` WRITE;
/*!40000 ALTER TABLE `minor_user` DISABLE KEYS */;
INSERT INTO `minor_user` VALUES ('Architecture',95),('Architecture',96),('Architecture',97),('Architecture',98),('Architecture',99),('Architecture',100),('Architecture',101),('Computer Engineering',81),('Aerospace Engineering',83),('Mechanical Engineering',88),('Mechanical Engineering',99),('Mechanical Engineering',100),('Computer Science',101),('Software Engineering',102);
/*!40000 ALTER TABLE `minor_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(64) NOT NULL,
  `role` varchar(32) DEFAULT NULL,
  `validated` tinyint(4) NOT NULL DEFAULT '0',
  `notification` varchar(10) NOT NULL DEFAULT 'false',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (77,'admin@mavs.uta.edu','qwerty','admin',1,'1'),(96,'sahana.ravikumar@mavs.uta.edu','$2a$10$ja2JFdO37PmjQrjWo5yreuShTJ3tozU./5znZFhyu92oDO84IJv7S','advisor',1,'false'),(100,'tirumalapra.kodihallishivakumar@mavs.uta.edu','$2a$10$XI/JEAYnMEryXt9hXL6DYeIBRVdI52Zwabb5FhK4rxO/VI5qllLxy','student',1,'true'),(101,'maithili.deshpande@mavs.uta.edu','$2a$10$Rsnkvv2GwLMDCNFirFIi3uezIXykrg5AIxwbLPSpbw.LukZA24ZSO','student',1,'false'),(104,'rudreshnarayan.ajgaonkar@mavs.uta.edu','$2a$10$Nr5e85oG4bhYImXNzYKh9.fsWrQaLvbfdAkg3VOVmwo1hi.ashttS','advisor',1,'false');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_advisor`
--

DROP TABLE IF EXISTS `user_advisor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_advisor` (
  `userId` int(11) NOT NULL,
  `pName` varchar(32) NOT NULL,
  `notification` varchar(45) NOT NULL,
  `name_low` varchar(2) NOT NULL,
  `name_high` varchar(2) NOT NULL,
  `degree_types` int(11) NOT NULL,
  `lead_status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userId` (`userId`),
  CONSTRAINT `user_advisor_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_advisor`
--

LOCK TABLES `user_advisor` WRITE;
/*!40000 ALTER TABLE `user_advisor` DISABLE KEYS */;
INSERT INTO `user_advisor` VALUES (96,'Sahana','day','A','O',3,0),(104,'Rudresh','day','A','Z',7,0);
/*!40000 ALTER TABLE `user_advisor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_guest`
--

DROP TABLE IF EXISTS `user_guest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_guest` (
  `userId` int(11) NOT NULL,
  `degree_type` int(11) NOT NULL,
  `phone_num` varchar(45) NOT NULL,
  `last_name_initial` varchar(2) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userId` (`userId`),
  CONSTRAINT `user_guest_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_guest`
--

LOCK TABLES `user_guest` WRITE;
/*!40000 ALTER TABLE `user_guest` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_guest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_prospectivestudent`
--

DROP TABLE IF EXISTS `user_prospectivestudent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_prospectivestudent` (
  `userId` int(11) NOT NULL,
  `student_Id` varchar(256) NOT NULL,
  `degree_type` int(11) NOT NULL,
  `phone_num` varchar(45) NOT NULL,
  `last_name_initial` varchar(2) NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userId` (`userId`),
  CONSTRAINT `user_prospectivestudent_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_prospectivestudent`
--

LOCK TABLES `user_prospectivestudent` WRITE;
/*!40000 ALTER TABLE `user_prospectivestudent` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_prospectivestudent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_student`
--

DROP TABLE IF EXISTS `user_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_student` (
  `userId` int(11) NOT NULL,
  `student_Id` varchar(256) NOT NULL,
  `degree_type` int(11) NOT NULL,
  `phone_num` varchar(45) NOT NULL,
  `last_name_initial` varchar(2) NOT NULL,
  `last_name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `userId` (`userId`),
  CONSTRAINT `user_student_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_student`
--

LOCK TABLES `user_student` WRITE;
/*!40000 ALTER TABLE `user_student` DISABLE KEYS */;
INSERT INTO `user_student` VALUES (100,'t8+8Ho223gp51iQgVoSm0g==',1,'682-241-9762','P','Prasad'),(101,'oN1324ExkoX/xbO7m2Ud3Q==',1,'682-963-7410','D','Maithili');
/*!40000 ALTER TABLE `user_student` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-15 20:33:24
