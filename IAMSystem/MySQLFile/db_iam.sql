-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: db_iam
-- ------------------------------------------------------
-- Server version	5.7.17

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
-- Table structure for table `t_extrainfo`
--

DROP TABLE IF EXISTS `t_extrainfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_extrainfo` (
  `uid` int(11) DEFAULT NULL,
  `attrName` varchar(20) DEFAULT NULL,
  `attrValue` varchar(30) DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_extrainfo`
--

LOCK TABLES `t_extrainfo` WRITE;
/*!40000 ALTER TABLE `t_extrainfo` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_extrainfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_identity`
--

DROP TABLE IF EXISTS `t_identity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_identity` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `displayName` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `FirstName` varchar(30) DEFAULT '',
  `LastName` varchar(30) DEFAULT '',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uid_UNIQUE` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_identity`
--

LOCK TABLES `t_identity` WRITE;
/*!40000 ALTER TABLE `t_identity` DISABLE KEYS */;
INSERT INTO `t_identity` VALUES (1,'jack','31213@163.com','zx','l'),(2,'Amy','test','',''),(3,'test3','test@fddg.com','',''),(12,'testt','test','',''),(13,'testttt','test','',''),(14,'testwfd','test','',''),(15,'test8','test','',''),(16,'rrrr','test','',''),(39,'qqqq','13','21','1'),(40,'tweqewq','eqweqw','qwe','qwe'),(41,'qweewqeqw','eqwqwe','qweqwe','eqwqwewqe'),(42,'eqwewqweew','eqweqw','sda','dsa'),(43,'testtt','','',''),(44,'ttt','','',''),(46,'smalljack','sdaad@gmail.com','a','b');
/*!40000 ALTER TABLE `t_identity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user` (
  `uid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(30) DEFAULT NULL,
  `secret` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `uid_UNIQUE` (`uid`),
  UNIQUE KEY `secret_UNIQUE` (`secret`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user`
--

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;
INSERT INTO `t_user` VALUES (1,'admin','admin',NULL);
/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-05 23:13:54
