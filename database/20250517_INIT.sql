-- MySQL dump 10.13  Distrib 8.0.42, for Linux (x86_64)
--
-- Host: localhost    Database: deenasty_data
-- ------------------------------------------------------
-- Server version	8.0.42-0ubuntu0.24.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity` (
  `id` binary(16) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `activity_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `id` binary(16) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `publication_date` date DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city` (
  `id` binary(16) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `old_name` varchar(255) DEFAULT NULL,
  `country_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrpd7j1p7yxr784adkx4pyepba` (`country_id`),
  CONSTRAINT `FKrpd7j1p7yxr784adkx4pyepba` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `country` (
  `id` binary(16) NOT NULL,
  `area` double DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `old_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country_region`
--

DROP TABLE IF EXISTS `country_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `country_region` (
  `id` binary(16) NOT NULL,
  `end_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `country_id` binary(16) DEFAULT NULL,
  `region_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1r7jdsb0k68rnfbbo562u04x9` (`country_id`),
  KEY `FKrwvo7ynurl2l88f9b76x1d809` (`region_id`),
  CONSTRAINT `FK1r7jdsb0k68rnfbbo562u04x9` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FKrwvo7ynurl2l88f9b76x1d809` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country_region`
--

LOCK TABLES `country_region` WRITE;
/*!40000 ALTER TABLE `country_region` DISABLE KEYS */;
/*!40000 ALTER TABLE `country_region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deenasty`
--

DROP TABLE IF EXISTS `deenasty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deenasty` (
  `id` binary(16) NOT NULL,
  `end_date` date DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deenasty`
--

LOCK TABLES `deenasty` WRITE;
/*!40000 ALTER TABLE `deenasty` DISABLE KEYS */;
/*!40000 ALTER TABLE `deenasty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deenasty_capital`
--

DROP TABLE IF EXISTS `deenasty_capital`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deenasty_capital` (
  `id` binary(16) NOT NULL,
  `end_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `city_id` binary(16) DEFAULT NULL,
  `deenasty_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtbq5v91kq628pa3r383ibd9li` (`city_id`),
  KEY `FK3bgw1iqggvijb9w47323ecuuv` (`deenasty_id`),
  CONSTRAINT `FK3bgw1iqggvijb9w47323ecuuv` FOREIGN KEY (`deenasty_id`) REFERENCES `deenasty` (`id`),
  CONSTRAINT `FKtbq5v91kq628pa3r383ibd9li` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deenasty_capital`
--

LOCK TABLES `deenasty_capital` WRITE;
/*!40000 ALTER TABLE `deenasty_capital` DISABLE KEYS */;
/*!40000 ALTER TABLE `deenasty_capital` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deenasty_region`
--

DROP TABLE IF EXISTS `deenasty_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deenasty_region` (
  `id` binary(16) NOT NULL,
  `end_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `deenasty_id` binary(16) DEFAULT NULL,
  `region_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqao1guocq94yj9y6iwq93npuu` (`deenasty_id`),
  KEY `FKl9xhg0l3wrah573pfmwgkmnlo` (`region_id`),
  CONSTRAINT `FKl9xhg0l3wrah573pfmwgkmnlo` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`),
  CONSTRAINT `FKqao1guocq94yj9y6iwq93npuu` FOREIGN KEY (`deenasty_id`) REFERENCES `deenasty` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deenasty_region`
--

LOCK TABLES `deenasty_region` WRITE;
/*!40000 ALTER TABLE `deenasty_region` DISABLE KEYS */;
/*!40000 ALTER TABLE `deenasty_region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
  `id` binary(16) NOT NULL,
  `date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `deenasty_id` binary(16) DEFAULT NULL,
  `region_id` binary(16) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9brdto6w51hkygq9b1m745rsi` (`deenasty_id`),
  KEY `FKjo3inaua6ckdrr2qeehh9225v` (`region_id`),
  CONSTRAINT `FK9brdto6w51hkygq9b1m745rsi` FOREIGN KEY (`deenasty_id`) REFERENCES `deenasty` (`id`),
  CONSTRAINT `FKjo3inaua6ckdrr2qeehh9225v` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historical_figure`
--

DROP TABLE IF EXISTS `historical_figure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historical_figure` (
  `id` binary(16) NOT NULL,
  `birth_date` date DEFAULT NULL,
  `death_date` date DEFAULT NULL,
  `gender` enum('F','M') DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historical_figure`
--

LOCK TABLES `historical_figure` WRITE;
/*!40000 ALTER TABLE `historical_figure` DISABLE KEYS */;
/*!40000 ALTER TABLE `historical_figure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historical_figure_activity`
--

DROP TABLE IF EXISTS `historical_figure_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historical_figure_activity` (
  `id` binary(16) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `activity_id` binary(16) DEFAULT NULL,
  `deenasty_id` binary(16) DEFAULT NULL,
  `historical_figure_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKo48485cohhedtm75q0v5lu87t` (`activity_id`),
  KEY `FK5yxskc5xa1udcq35ook8ln281` (`deenasty_id`),
  KEY `FKlhprpc72takthltbrlxj01d9s` (`historical_figure_id`),
  CONSTRAINT `FK5yxskc5xa1udcq35ook8ln281` FOREIGN KEY (`deenasty_id`) REFERENCES `deenasty` (`id`),
  CONSTRAINT `FKlhprpc72takthltbrlxj01d9s` FOREIGN KEY (`historical_figure_id`) REFERENCES `historical_figure` (`id`),
  CONSTRAINT `FKo48485cohhedtm75q0v5lu87t` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historical_figure_activity`
--

LOCK TABLES `historical_figure_activity` WRITE;
/*!40000 ALTER TABLE `historical_figure_activity` DISABLE KEYS */;
/*!40000 ALTER TABLE `historical_figure_activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historical_figure_book`
--

DROP TABLE IF EXISTS `historical_figure_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historical_figure_book` (
  `id` binary(16) NOT NULL,
  `book_id` binary(16) DEFAULT NULL,
  `historical_figure_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkhdosccbto3m3vfgqv7atjye4` (`book_id`),
  KEY `FKd2llejw9gkdmic4vdt4rm2y8i` (`historical_figure_id`),
  CONSTRAINT `FKd2llejw9gkdmic4vdt4rm2y8i` FOREIGN KEY (`historical_figure_id`) REFERENCES `historical_figure` (`id`),
  CONSTRAINT `FKkhdosccbto3m3vfgqv7atjye4` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historical_figure_book`
--

LOCK TABLES `historical_figure_book` WRITE;
/*!40000 ALTER TABLE `historical_figure_book` DISABLE KEYS */;
/*!40000 ALTER TABLE `historical_figure_book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historical_figure_monument`
--

DROP TABLE IF EXISTS `historical_figure_monument`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historical_figure_monument` (
  `id` binary(16) NOT NULL,
  `monument_id` binary(16) DEFAULT NULL,
  `historical_figure_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5garbfg1vkkwlbuh02vcis5w6` (`monument_id`),
  KEY `FKho9i420joha8vpxh63sy3lwqh` (`historical_figure_id`),
  CONSTRAINT `FK5garbfg1vkkwlbuh02vcis5w6` FOREIGN KEY (`monument_id`) REFERENCES `monument` (`id`),
  CONSTRAINT `FKho9i420joha8vpxh63sy3lwqh` FOREIGN KEY (`historical_figure_id`) REFERENCES `historical_figure` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historical_figure_monument`
--

LOCK TABLES `historical_figure_monument` WRITE;
/*!40000 ALTER TABLE `historical_figure_monument` DISABLE KEYS */;
/*!40000 ALTER TABLE `historical_figure_monument` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historical_figure_obedience`
--

DROP TABLE IF EXISTS `historical_figure_obedience`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historical_figure_obedience` (
  `id` binary(16) NOT NULL,
  `historical_figure_id` binary(16) DEFAULT NULL,
  `obedience_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK307k4deuh54t94my14napk1cr` (`historical_figure_id`),
  KEY `FKnp230sv9fljl75i5ovlo1062d` (`obedience_id`),
  CONSTRAINT `FK307k4deuh54t94my14napk1cr` FOREIGN KEY (`historical_figure_id`) REFERENCES `historical_figure` (`id`),
  CONSTRAINT `FKnp230sv9fljl75i5ovlo1062d` FOREIGN KEY (`obedience_id`) REFERENCES `obedience` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historical_figure_obedience`
--

LOCK TABLES `historical_figure_obedience` WRITE;
/*!40000 ALTER TABLE `historical_figure_obedience` DISABLE KEYS */;
/*!40000 ALTER TABLE `historical_figure_obedience` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historical_figure_region`
--

DROP TABLE IF EXISTS `historical_figure_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historical_figure_region` (
  `id` binary(16) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `relation_type` varchar(255) DEFAULT NULL,
  `historical_figure_id` binary(16) DEFAULT NULL,
  `region_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1bqdihn2p0ss3g3jjtg97run8` (`historical_figure_id`),
  KEY `FKqddk1uqyfny92ifpdaitye0o2` (`region_id`),
  CONSTRAINT `FK1bqdihn2p0ss3g3jjtg97run8` FOREIGN KEY (`historical_figure_id`) REFERENCES `historical_figure` (`id`),
  CONSTRAINT `FKqddk1uqyfny92ifpdaitye0o2` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historical_figure_region`
--

LOCK TABLES `historical_figure_region` WRITE;
/*!40000 ALTER TABLE `historical_figure_region` DISABLE KEYS */;
/*!40000 ALTER TABLE `historical_figure_region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monument`
--

DROP TABLE IF EXISTS `monument`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `monument` (
  `id` binary(16) NOT NULL,
  `construction_date` date DEFAULT NULL,
  `destruction_date` date DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `city_id` binary(16) DEFAULT NULL,
  `deenasty_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtjepdqj9hub4ej6op7o0vs0tv` (`city_id`),
  KEY `FK87rmbador3rxmm0n5644q6jvb` (`deenasty_id`),
  CONSTRAINT `FK87rmbador3rxmm0n5644q6jvb` FOREIGN KEY (`deenasty_id`) REFERENCES `deenasty` (`id`),
  CONSTRAINT `FKtjepdqj9hub4ej6op7o0vs0tv` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `monument`
--

LOCK TABLES `monument` WRITE;
/*!40000 ALTER TABLE `monument` DISABLE KEYS */;
/*!40000 ALTER TABLE `monument` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `obedience`
--

DROP TABLE IF EXISTS `obedience`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `obedience` (
  `id` binary(16) NOT NULL,
  `creation_date` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `obedience`
--

LOCK TABLES `obedience` WRITE;
/*!40000 ALTER TABLE `obedience` DISABLE KEYS */;
/*!40000 ALTER TABLE `obedience` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `region`
--

DROP TABLE IF EXISTS `region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `region` (
  `id` binary(16) NOT NULL,
  `area` double DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `region`
--

LOCK TABLES `region` WRITE;
/*!40000 ALTER TABLE `region` DISABLE KEYS */;
/*!40000 ALTER TABLE `region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` binary(16) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (_binary '˜\ÚþÏ¹J6Š@Wœ\Ç=–','ROLE_ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` binary(16) NOT NULL,
  `role_id` binary(16) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKrhfovtciq1l558cw6udg0h0d3` (`role_id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKrhfovtciq1l558cw6udg0h0d3` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (_binary '0ü_m\í¤HÕ³ŒB¨É¬\Ü',_binary '˜\ÚþÏ¹J6Š@Wœ\Ç=–');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` binary(16) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (_binary '0ü_m\í¤HÕ³ŒB¨É¬\Ü','$2a$10$kaTL/d1tt5.Zrvz6008A.e8ybO0cavhlswNfiNJiSIKfmlBC3T7O2','admin');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'deenasty_data'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-17 23:19:28
