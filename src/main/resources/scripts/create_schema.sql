-- MySQL dump 10.13  Distrib 8.0.17, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: lab_01
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `el_appliance`
--

DROP TABLE IF EXISTS `el_appliance`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `el_appliance`
(
    `id`                     int(11) NOT NULL AUTO_INCREMENT,
    `weight`                 double      DEFAULT NULL,
    `color`                  varchar(30) DEFAULT NULL,
    `name`                   varchar(30) DEFAULT NULL,
    `brand`                  varchar(30) DEFAULT NULL,
    `power_consumption`      int(11)     DEFAULT NULL,
    `is_for_continuous_work` tinyint(1)  DEFAULT NULL,
    `is_connect_to_socket`   tinyint(1)  DEFAULT NULL,
    `is_turn_on`             tinyint(1)  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 9
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `el_appliance`
--

LOCK TABLES `el_appliance` WRITE;
/*!40000 ALTER TABLE `el_appliance`
    DISABLE KEYS */;
INSERT INTO `el_appliance` (`id`, `weight`, `color`, `name`, `brand`, `power_consumption`, `is_for_continuous_work`,
                            `is_connect_to_socket`, `is_turn_on`)
VALUES (1, 0.45, 'Red', 'Hairdryer', 'Rowenta', 220, 0, 0, 0),
       (2, 3.5, 'Black', 'Vacuum cleaner', 'Philips', 1000, 0, 0, 0),
       (3, 15, 'White', 'Washing machine', 'Samsung', 1700, 1, 0, 0),
       (4, 65, 'White', 'Fridge', 'Samsung', 250, 1, 0, 0),
       (5, 0.5, 'Blue', 'Lamp', 'MAXUS', 60, 1, 0, 0),
       (6, 2, 'Black', 'Laptop', 'Asus', 100, 1, 0, 0),
       (7, 7, 'Black', 'TV', 'Samsung', 700, 1, 0, 0),
       (8, 0.4, 'White', 'Iron', 'Philips', 1000, 0, 0, 0);
/*!40000 ALTER TABLE `el_appliance`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2019-10-22  1:05:20
