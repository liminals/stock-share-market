-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: liminal_final
-- ------------------------------------------------------
-- Server version	5.7.14-log

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
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `event` (
  `id` int(3) NOT NULL,
  `name` varchar(15) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `sector` varchar(15) DEFAULT NULL,
  `stockId` int(2) DEFAULT NULL,
  `value` int(1) DEFAULT NULL,
  `duration` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `game` (
  `id` int(3) NOT NULL,
  `currentTurn` int(3) NOT NULL,
  `status` varchar(15) NOT NULL,
  `createdBy` varchar(30) NOT NULL,
  `eventStream` varchar(500) NOT NULL,
  `marketTrend` varchar(800) NOT NULL,
  `sectorTrends` varchar(3000) NOT NULL,
  `marketValue` varchar(500) NOT NULL,
  `sectorValues` varchar(2000) NOT NULL,
  `turns` int(3) DEFAULT NULL,
  `stocks` varchar(2000) DEFAULT NULL,
  `event` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game`
--

LOCK TABLES `game` WRITE;
/*!40000 ALTER TABLE `game` DISABLE KEYS */;
INSERT INTO `game` VALUES (1,3,'STARTED','Riyaz','{\"11\":true,\"1\":false,\"2\":false,\"3\":false,\"4\":false,\"5\":false,\"6\":false,\"7\":false,\"8\":false,\"9\":false,\"10\":false}','{\"1\":\"doesn\'t change\",\"2\":\"decrease\",\"3\":\"doesn\'t change\",\"4\":\"increase\",\"5\":\"increase\"}','{\"technology\":{\"1\":\"doesn\'t change\",\"2\":\"increase\",\"3\":\"increase\",\"4\":\"decrease\",\"5\":\"doesn\'t change\"},\"utilities\":{\"1\":\"doesn\'t change\",\"2\":\"doesn\'t change\",\"3\":\"increase\",\"4\":\"decrease\",\"5\":\"doesn\'t change\"},\"finance\":{\"1\":\"doesn\'t change\",\"2\":\"doesn\'t change\",\"3\":\"doesn\'t change\",\"4\":\"doesn\'t change\",\"5\":\"doesn\'t change\"},\"healthcare\":{\"1\":\"decrease\",\"2\":\"doesn\'t change\",\"3\":\"doesn\'t change\",\"4\":\"decrease\",\"5\":\"decrease\"}}','{\"1\":0,\"2\":-1,\"3\":-1,\"4\":0,\"5\":1}','{\"technology\":{\"1\":3,\"2\":3,\"3\":3,\"4\":2,\"5\":2},\"utilities\":{\"1\":-1,\"2\":-1,\"3\":0,\"4\":-1,\"5\":-1},\"finance\":{\"1\":1,\"2\":1,\"3\":1,\"4\":1,\"5\":1},\"healthcare\":{\"1\":2,\"2\":2,\"3\":2,\"4\":1,\"5\":0}}',5,'[{\"id\":1,\"sector\":\"finance\",\"name\":\"Morgan Stanley[MS]\",\"current_price\":13.5432},{\"id\":2,\"sector\":\"finance\",\"name\":\"BankOfAmerica Copr[BAC]\",\"current_price\":12.8752},{\"id\":3,\"sector\":\"finance\",\"name\":\"JPMorgan[JPM]\",\"current_price\":11.243724},{\"id\":4,\"sector\":\"utilities\",\"name\":\"Eversource Energy[ES]\",\"current_price\":10.4221},{\"id\":5,\"sector\":\"utilities\",\"name\":\"NiSource Inc[NI]\",\"current_price\":7.5321},{\"id\":6,\"sector\":\"utilities\",\"name\":\"Vectren Corp[VVC]\",\"current_price\":12.4421},{\"id\":7,\"sector\":\"healthcare\",\"name\":\"Pfizer Inc[PFE]\",\"current_price\":12.249079},{\"id\":8,\"sector\":\"healthcare\",\"name\":\"CVS Health Corp[CVS]\",\"current_price\":2.455241},{\"id\":9,\"sector\":\"healthcare\",\"name\":\"Anthem Inc[ANTM]\",\"current_price\":3.5010853},{\"id\":10,\"sector\":\"technology\",\"name\":\"Apple Inc[AAPLE]\",\"current_price\":31.649384},{\"id\":11,\"sector\":\"technology\",\"name\":\"Facebook Inc[FB]\",\"current_price\":23.109653},{\"id\":12,\"sector\":\"technology\",\"name\":\"Microsoft Corp[MSFT]\",\"current_price\":22.14579}]',NULL),(2,6,'ENDED','Riyaz','{\"11\":true,\"1\":false,\"2\":false,\"3\":false,\"4\":false,\"5\":false,\"6\":false,\"7\":false,\"8\":false,\"9\":false,\"10\":false}','{\"1\":\"doesn\'t change\",\"2\":\"decrease\",\"3\":\"increase\",\"4\":\"doesn\'t change\",\"5\":\"decrease\"}','{\"technology\":{\"1\":\"doesn\'t change\",\"2\":\"doesn\'t change\",\"3\":\"doesn\'t change\",\"4\":\"increase\",\"5\":\"decrease\"},\"utilities\":{\"1\":\"doesn\'t change\",\"2\":\"doesn\'t change\",\"3\":\"increase\",\"4\":\"doesn\'t change\",\"5\":\"increase\"},\"finance\":{\"1\":\"decrease\",\"2\":\"increase\",\"3\":\"doesn\'t change\",\"4\":\"doesn\'t change\",\"5\":\"doesn\'t change\"},\"healthcare\":{\"1\":\"doesn\'t change\",\"2\":\"doesn\'t change\",\"3\":\"doesn\'t change\",\"4\":\"doesn\'t change\",\"5\":\"decrease\"}}','{\"1\":2,\"2\":1,\"3\":2,\"4\":2,\"5\":1}','{\"technology\":{\"1\":2,\"2\":2,\"3\":2,\"4\":3,\"5\":2},\"utilities\":{\"1\":-3,\"2\":-3,\"3\":-2,\"4\":-2,\"5\":-1},\"finance\":{\"1\":2,\"2\":3,\"3\":3,\"4\":3,\"5\":3},\"healthcare\":{\"1\":-1,\"2\":-1,\"3\":-1,\"4\":-1,\"5\":-2}}',5,'[{\"id\":1,\"sector\":\"finance\",\"name\":\"Morgan Stanley[MS]\",\"current_price\":16.455872},{\"id\":2,\"sector\":\"finance\",\"name\":\"BankOfAmerica Copr[BAC]\",\"current_price\":14.62911},{\"id\":3,\"sector\":\"finance\",\"name\":\"JPMorgan[JPM]\",\"current_price\":13.397767},{\"id\":4,\"sector\":\"utilities\",\"name\":\"Eversource Energy[ES]\",\"current_price\":10.630542},{\"id\":5,\"sector\":\"utilities\",\"name\":\"NiSource Inc[NI]\",\"current_price\":7.6834955},{\"id\":6,\"sector\":\"utilities\",\"name\":\"Vectren Corp[VVC]\",\"current_price\":12.690942},{\"id\":7,\"sector\":\"healthcare\",\"name\":\"Pfizer Inc[PFE]\",\"current_price\":12.371569},{\"id\":8,\"sector\":\"healthcare\",\"name\":\"CVS Health Corp[CVS]\",\"current_price\":2.384192},{\"id\":9,\"sector\":\"healthcare\",\"name\":\"Anthem Inc[ANTM]\",\"current_price\":3.4321},{\"id\":10,\"sector\":\"technology\",\"name\":\"Apple Inc[AAPLE]\",\"current_price\":34.900345},{\"id\":11,\"sector\":\"technology\",\"name\":\"Facebook Inc[FB]\",\"current_price\":25.240547},{\"id\":12,\"sector\":\"technology\",\"name\":\"Microsoft Corp[MSFT]\",\"current_price\":24.406876}]',NULL),(3,6,'STARTED','Riyaz','{\"11\":true,\"1\":false,\"2\":false,\"3\":false,\"4\":false,\"5\":false,\"6\":false,\"7\":false,\"8\":false,\"9\":false,\"10\":false}','{\"1\":\"doesn\'t change\",\"2\":\"doesn\'t change\",\"3\":\"decrease\",\"4\":\"decrease\",\"5\":\"doesn\'t change\"}','{\"technology\":{\"1\":\"doesn\'t change\",\"2\":\"doesn\'t change\",\"3\":\"doesn\'t change\",\"4\":\"doesn\'t change\",\"5\":\"doesn\'t change\"},\"utilities\":{\"1\":\"decrease\",\"2\":\"decrease\",\"3\":\"decrease\",\"4\":\"doesn\'t change\",\"5\":\"decrease\"},\"finance\":{\"1\":\"increase\",\"2\":\"doesn\'t change\",\"3\":\"decrease\",\"4\":\"doesn\'t change\",\"5\":\"doesn\'t change\"},\"healthcare\":{\"1\":\"doesn\'t change\",\"2\":\"decrease\",\"3\":\"decrease\",\"4\":\"doesn\'t change\",\"5\":\"doesn\'t change\"}}','{\"1\":3,\"2\":3,\"3\":2,\"4\":1,\"5\":1}','{\"technology\":{\"1\":-2,\"2\":-2,\"3\":-2,\"4\":-2,\"5\":-2},\"utilities\":{\"1\":0,\"2\":-1,\"3\":-2,\"4\":-2,\"5\":-3},\"finance\":{\"1\":-3,\"2\":-3,\"3\":-3,\"4\":-3,\"5\":-3},\"healthcare\":{\"1\":-1,\"2\":-2,\"3\":-3,\"4\":-3,\"5\":-3}}',5,'[{\"id\":1,\"sector\":\"finance\",\"name\":\"Morgan Stanley[MS]\",\"current_price\":13.814063},{\"id\":2,\"sector\":\"finance\",\"name\":\"BankOfAmerica Copr[BAC]\",\"current_price\":13.003952},{\"id\":3,\"sector\":\"finance\",\"name\":\"JPMorgan[JPM]\",\"current_price\":11.243724},{\"id\":4,\"sector\":\"utilities\",\"name\":\"Eversource Energy[ES]\",\"current_price\":10.736847},{\"id\":5,\"sector\":\"utilities\",\"name\":\"NiSource Inc[NI]\",\"current_price\":7.6074214},{\"id\":6,\"sector\":\"utilities\",\"name\":\"Vectren Corp[VVC]\",\"current_price\":12.817851},{\"id\":7,\"sector\":\"healthcare\",\"name\":\"Pfizer Inc[PFE]\",\"current_price\":12.008901},{\"id\":8,\"sector\":\"healthcare\",\"name\":\"CVS Health Corp[CVS]\",\"current_price\":2.3143},{\"id\":9,\"sector\":\"healthcare\",\"name\":\"Anthem Inc[ANTM]\",\"current_price\":3.4321},{\"id\":10,\"sector\":\"technology\",\"name\":\"Apple Inc[AAPLE]\",\"current_price\":31.345064},{\"id\":11,\"sector\":\"technology\",\"name\":\"Facebook Inc[FB]\",\"current_price\":23.107388},{\"id\":12,\"sector\":\"technology\",\"name\":\"Microsoft Corp[MSFT]\",\"current_price\":20.8765}]',NULL),(4,6,'ENDED','riyaz','{\"11\":true,\"1\":false,\"2\":false,\"3\":false,\"4\":false,\"5\":false,\"6\":false,\"7\":false,\"8\":false,\"9\":false,\"10\":false}','{\"1\":\"doesn\'t change\",\"2\":\"decrease\",\"3\":\"increase\",\"4\":\"doesn\'t change\",\"5\":\"doesn\'t change\"}','{\"technology\":{\"1\":\"doesn\'t change\",\"2\":\"doesn\'t change\",\"3\":\"decrease\",\"4\":\"increase\",\"5\":\"increase\"},\"utilities\":{\"1\":\"increase\",\"2\":\"doesn\'t change\",\"3\":\"decrease\",\"4\":\"decrease\",\"5\":\"increase\"},\"finance\":{\"1\":\"doesn\'t change\",\"2\":\"doesn\'t change\",\"3\":\"decrease\",\"4\":\"doesn\'t change\",\"5\":\"doesn\'t change\"},\"healthcare\":{\"1\":\"doesn\'t change\",\"2\":\"doesn\'t change\",\"3\":\"decrease\",\"4\":\"doesn\'t change\",\"5\":\"doesn\'t change\"}}','{\"1\":-3,\"2\":-3,\"3\":-2,\"4\":-2,\"5\":-2}','{\"technology\":{\"1\":2,\"2\":2,\"3\":1,\"4\":2,\"5\":3},\"utilities\":{\"1\":-2,\"2\":-2,\"3\":-3,\"4\":-3,\"5\":-2},\"finance\":{\"1\":0,\"2\":0,\"3\":-1,\"4\":-1,\"5\":-1},\"healthcare\":{\"1\":-3,\"2\":-3,\"3\":-3,\"4\":-3,\"5\":-3}}',5,'[{\"id\":1,\"sector\":\"finance\",\"name\":\"Morgan Stanley[MS]\",\"current_price\":13.5432},{\"id\":2,\"sector\":\"finance\",\"name\":\"BankOfAmerica Copr[BAC]\",\"current_price\":12.8752},{\"id\":3,\"sector\":\"finance\",\"name\":\"JPMorgan[JPM]\",\"current_price\":11.1324},{\"id\":4,\"sector\":\"utilities\",\"name\":\"Eversource Energy[ES]\",\"current_price\":10.4221},{\"id\":5,\"sector\":\"utilities\",\"name\":\"NiSource Inc[NI]\",\"current_price\":7.5321},{\"id\":6,\"sector\":\"utilities\",\"name\":\"Vectren Corp[VVC]\",\"current_price\":12.4421},{\"id\":7,\"sector\":\"healthcare\",\"name\":\"Pfizer Inc[PFE]\",\"current_price\":11.89},{\"id\":8,\"sector\":\"healthcare\",\"name\":\"CVS Health Corp[CVS]\",\"current_price\":2.3143},{\"id\":9,\"sector\":\"healthcare\",\"name\":\"Anthem Inc[ANTM]\",\"current_price\":3.4321},{\"id\":10,\"sector\":\"technology\",\"name\":\"Apple Inc[AAPLE]\",\"current_price\":31.040743},{\"id\":11,\"sector\":\"technology\",\"name\":\"Facebook Inc[FB]\",\"current_price\":23.33846},{\"id\":12,\"sector\":\"technology\",\"name\":\"Microsoft Corp[MSFT]\",\"current_price\":21.717823}]',NULL),(5,6,'ENDED','dons','{\"11\":true,\"1\":false,\"2\":false,\"3\":false,\"4\":false,\"5\":false,\"6\":false,\"7\":false,\"8\":false,\"9\":false,\"10\":false}','{\"1\":\"increase\",\"2\":\"doesn\'t change\",\"3\":\"doesn\'t change\",\"4\":\"decrease\",\"5\":\"doesn\'t change\"}','{\"technology\":{\"1\":\"doesn\'t change\",\"2\":\"increase\",\"3\":\"doesn\'t change\",\"4\":\"decrease\",\"5\":\"doesn\'t change\"},\"utilities\":{\"1\":\"doesn\'t change\",\"2\":\"increase\",\"3\":\"decrease\",\"4\":\"doesn\'t change\",\"5\":\"increase\"},\"finance\":{\"1\":\"doesn\'t change\",\"2\":\"doesn\'t change\",\"3\":\"doesn\'t change\",\"4\":\"decrease\",\"5\":\"doesn\'t change\"},\"healthcare\":{\"1\":\"doesn\'t change\",\"2\":\"decrease\",\"3\":\"doesn\'t change\",\"4\":\"doesn\'t change\",\"5\":\"decrease\"}}','{\"1\":-1,\"2\":-1,\"3\":-1,\"4\":-2,\"5\":-2}','{\"technology\":{\"1\":0,\"2\":1,\"3\":1,\"4\":0,\"5\":0},\"utilities\":{\"1\":2,\"2\":3,\"3\":2,\"4\":2,\"5\":3},\"finance\":{\"1\":3,\"2\":3,\"3\":3,\"4\":2,\"5\":2},\"healthcare\":{\"1\":-3,\"2\":-3,\"3\":-3,\"4\":-3,\"5\":-3}}',5,'[{\"id\":1,\"sector\":\"finance\",\"name\":\"Morgan Stanley[MS]\",\"current_price\":14.08899},{\"id\":2,\"sector\":\"finance\",\"name\":\"BankOfAmerica Copr[BAC]\",\"current_price\":14.067753},{\"id\":3,\"sector\":\"finance\",\"name\":\"JPMorgan[JPM]\",\"current_price\":11.696846},{\"id\":4,\"sector\":\"utilities\",\"name\":\"Eversource Energy[ES]\",\"current_price\":11.166322},{\"id\":5,\"sector\":\"utilities\",\"name\":\"NiSource Inc[NI]\",\"current_price\":8.071488},{\"id\":6,\"sector\":\"utilities\",\"name\":\"Vectren Corp[VVC]\",\"current_price\":12.817851},{\"id\":7,\"sector\":\"healthcare\",\"name\":\"Pfizer Inc[PFE]\",\"current_price\":11.89},{\"id\":8,\"sector\":\"healthcare\",\"name\":\"CVS Health Corp[CVS]\",\"current_price\":2.3143},{\"id\":9,\"sector\":\"healthcare\",\"name\":\"Anthem Inc[ANTM]\",\"current_price\":3.4321},{\"id\":10,\"sector\":\"technology\",\"name\":\"Apple Inc[AAPLE]\",\"current_price\":31.35115},{\"id\":11,\"sector\":\"technology\",\"name\":\"Facebook Inc[FB]\",\"current_price\":22.6543},{\"id\":12,\"sector\":\"technology\",\"name\":\"Microsoft Corp[MSFT]\",\"current_price\":21.50697}]',NULL),(6,3,'ENDED','dons','{\"11\":true,\"1\":false,\"2\":false,\"3\":false,\"4\":false,\"5\":false,\"6\":false,\"7\":false,\"8\":false,\"9\":false,\"10\":false}','{\"1\":\"decrease\",\"2\":\"doesn\'t change\",\"3\":\"increase\"}','{\"technology\":{\"1\":\"doesn\'t change\",\"2\":\"decrease\",\"3\":\"doesn\'t change\"},\"utilities\":{\"1\":\"doesn\'t change\",\"2\":\"increase\",\"3\":\"doesn\'t change\"},\"finance\":{\"1\":\"increase\",\"2\":\"doesn\'t change\",\"3\":\"doesn\'t change\"},\"healthcare\":{\"1\":\"doesn\'t change\",\"2\":\"doesn\'t change\",\"3\":\"increase\"}}','{\"1\":2,\"2\":2,\"3\":3}','{\"technology\":{\"1\":1,\"2\":0,\"3\":0},\"utilities\":{\"1\":3,\"2\":3,\"3\":3},\"finance\":{\"1\":3,\"2\":3,\"3\":3},\"healthcare\":{\"1\":-3,\"2\":-3,\"3\":-2}}',3,'[{\"id\":1,\"sector\":\"finance\",\"name\":\"Morgan Stanley[MS]\",\"current_price\":15.0654545},{\"id\":2,\"sector\":\"finance\",\"name\":\"BankOfAmerica Copr[BAC]\",\"current_price\":14.327523},{\"id\":3,\"sector\":\"finance\",\"name\":\"JPMorgan[JPM]\",\"current_price\":11.925026},{\"id\":4,\"sector\":\"utilities\",\"name\":\"Eversource Energy[ES]\",\"current_price\":11.820745},{\"id\":5,\"sector\":\"utilities\",\"name\":\"NiSource Inc[NI]\",\"current_price\":8.378708},{\"id\":6,\"sector\":\"utilities\",\"name\":\"Vectren Corp[VVC]\",\"current_price\":13.978699},{\"id\":7,\"sector\":\"healthcare\",\"name\":\"Pfizer Inc[PFE]\",\"current_price\":12.008901},{\"id\":8,\"sector\":\"healthcare\",\"name\":\"CVS Health Corp[CVS]\",\"current_price\":2.3605862},{\"id\":9,\"sector\":\"healthcare\",\"name\":\"Anthem Inc[ANTM]\",\"current_price\":3.4321},{\"id\":10,\"sector\":\"technology\",\"name\":\"Apple Inc[AAPLE]\",\"current_price\":31.661558},{\"id\":11,\"sector\":\"technology\",\"name\":\"Facebook Inc[FB]\",\"current_price\":24.267286},{\"id\":12,\"sector\":\"technology\",\"name\":\"Microsoft Corp[MSFT]\",\"current_price\":22.14579}]',NULL);
/*!40000 ALTER TABLE `game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stock` (
  `id` int(11) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `sector` varchar(15) DEFAULT NULL,
  `current_price` float(8,4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock`
--

LOCK TABLES `stock` WRITE;
/*!40000 ALTER TABLE `stock` DISABLE KEYS */;
INSERT INTO `stock` VALUES (1,'Morgan Stanley[MS]','finance',13.5432),(2,'BankOfAmerica Copr[BAC]','finance',12.8752),(3,'JPMorgan[JPM]','finance',11.1324),(4,'Eversource Energy[ES]','utilities',10.4221),(5,'NiSource Inc[NI]','utilities',7.5321),(6,'Vectren Corp[VVC]','utilities',12.4421),(7,'Pfizer Inc[PFE]','healthcare',11.8900),(8,'CVS Health Corp[CVS]','healthcare',2.3143),(9,'Anthem Inc[ANTM]','healthcare',3.4321),(10,'Apple Inc[AAPLE]','technology',30.4321),(11,'Facebook Inc[FB]','technology',22.6543),(12,'Microsoft Corp[MSFT]','technology',20.8765);
/*!40000 ALTER TABLE `stock` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-06-22 23:28:27
