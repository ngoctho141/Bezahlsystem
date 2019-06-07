-- MySQL dump 10.13  Distrib 5.7.23, for Linux (x86_64)
--
-- Host: localhost    Database: automaten2_0
-- ------------------------------------------------------
-- Server version	5.7.23-0ubuntu0.16.04.1

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
-- Table structure for table `automat`
--

DROP TABLE IF EXISTS `automat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `automat` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `fid` int(11) DEFAULT NULL,
  `aname` varchar(20) DEFAULT NULL,
  `macadresse` varchar(30) DEFAULT NULL,
  `astandort` varchar(40) DEFAULT NULL,
  `tan` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`aid`),
  KEY `fid` (`fid`),
  CONSTRAINT `automat_ibfk_1` FOREIGN KEY (`fid`) REFERENCES `firma` (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `automat`
--

LOCK TABLES `automat` WRITE;
/*!40000 ALTER TABLE `automat` DISABLE KEYS */;
INSERT INTO `automat` VALUES (2,1,'TEST_Automat','B8:27:EB:29:5C:5D','TEST_ORT','TEST_TAN');
/*!40000 ALTER TABLE `automat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `firma`
--

DROP TABLE IF EXISTS `firma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `firma` (
  `fid` int(11) NOT NULL AUTO_INCREMENT,
  `finame` varchar(20) DEFAULT NULL,
  `standort` varchar(40) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `telefon` varchar(20) DEFAULT NULL,
  `kontodaten` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `firma`
--

LOCK TABLES `firma` WRITE;
/*!40000 ALTER TABLE `firma` DISABLE KEYS */;
INSERT INTO `firma` VALUES (1,'TEST_FIRMA','TEST_FORT','TEST_EMAIL@EMAIL.EMAIL','TEST_NUMMER','TEST_KONTO');
/*!40000 ALTER TABLE `firma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `firmenacc`
--

DROP TABLE IF EXISTS `firmenacc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `firmenacc` (
  `faid` int(11) NOT NULL AUTO_INCREMENT,
  `fid` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `passwort` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`faid`),
  KEY `fid` (`fid`),
  CONSTRAINT `firmenacc_ibfk_1` FOREIGN KEY (`fid`) REFERENCES `firma` (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `firmenacc`
--

LOCK TABLES `firmenacc` WRITE;
/*!40000 ALTER TABLE `firmenacc` DISABLE KEYS */;
/*!40000 ALTER TABLE `firmenacc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kunde`
--

DROP TABLE IF EXISTS `kunde`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kunde` (
  `kid` int(11) NOT NULL AUTO_INCREMENT,
  `kname` varchar(20) DEFAULT NULL,
  `passwort` varchar(50) DEFAULT NULL,
  `telefon` varchar(50) DEFAULT NULL,
  `geburtsdatum` varchar(50) DEFAULT NULL,
  `guthaben` double DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`kid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kunde`
--

LOCK TABLES `kunde` WRITE;
/*!40000 ALTER TABLE `kunde` DISABLE KEYS */;
INSERT INTO `kunde` VALUES (1,'Martin','12345','1234567','1.1.1994',34.75,NULL),(2,'Sven','12345','1234567','1.1.1994',142,NULL),(3,'Florian','12345','1234567','1.1.1994',160.5,NULL);
/*!40000 ALTER TABLE `kunde` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produkt`
--

DROP TABLE IF EXISTS `produkt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `produkt` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produkt`
--

LOCK TABLES `produkt` WRITE;
/*!40000 ALTER TABLE `produkt` DISABLE KEYS */;
INSERT INTO `produkt` VALUES (1,'latte',2),(2,'black',1.25);
/*!40000 ALTER TABLE `produkt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rechnung`
--

DROP TABLE IF EXISTS `rechnung`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rechnung` (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `kid` int(11) DEFAULT NULL,
  `aid` int(11) DEFAULT NULL,
  `produkt` varchar(50) DEFAULT NULL,
  `preis` double DEFAULT NULL,
  PRIMARY KEY (`rid`),
  KEY `kid` (`kid`),
  KEY `aid` (`aid`),
  CONSTRAINT `rechnung_ibfk_1` FOREIGN KEY (`kid`) REFERENCES `kunde` (`kid`),
  CONSTRAINT `rechnung_ibfk_2` FOREIGN KEY (`aid`) REFERENCES `automat` (`aid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rechnung`
--

LOCK TABLES `rechnung` WRITE;
/*!40000 ALTER TABLE `rechnung` DISABLE KEYS */;
/*!40000 ALTER TABLE `rechnung` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-13 19:38:44
