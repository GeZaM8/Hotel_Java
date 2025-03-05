-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               11.4.2-MariaDB-log - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for hotel_java
DROP DATABASE IF EXISTS `hotel_java`;
CREATE DATABASE IF NOT EXISTS `hotel_java` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `hotel_java`;

-- Dumping structure for table hotel_java.booking
DROP TABLE IF EXISTS `booking`;
CREATE TABLE IF NOT EXISTS `booking` (
  `BNum` int(11) NOT NULL AUTO_INCREMENT,
  `Room` int(11) NOT NULL,
  `Customer` int(11) NOT NULL,
  `BDate` datetime NOT NULL,
  `Duration` int(11) NOT NULL,
  `Cost` int(11) NOT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`BNum`),
  KEY `Room` (`Room`),
  KEY `Customer` (`Customer`),
  CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`Room`) REFERENCES `rooms` (`RNum`),
  CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`Customer`) REFERENCES `customers` (`CustNum`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table hotel_java.customers
DROP TABLE IF EXISTS `customers`;
CREATE TABLE IF NOT EXISTS `customers` (
  `CustNum` int(11) NOT NULL AUTO_INCREMENT,
  `CustName` varchar(50) NOT NULL,
  `CustPhone` varchar(15) NOT NULL,
  `CustGen` varchar(10) NOT NULL,
  `CustAdd` varchar(50) DEFAULT NULL,
  `CustDob` date NOT NULL,
  `UserID` int(11) DEFAULT NULL,
  PRIMARY KEY (`CustNum`),
  KEY `FK_UserID` (`UserID`),
  CONSTRAINT `FK_UserID` FOREIGN KEY (`UserID`) REFERENCES `users` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table hotel_java.pembayaran
DROP TABLE IF EXISTS `pembayaran`;
CREATE TABLE IF NOT EXISTS `pembayaran` (
  `id_pembayaran` int(11) NOT NULL AUTO_INCREMENT,
  `id_booking` int(11) NOT NULL,
  `id_room` int(11) NOT NULL,
  `id_customer` int(11) NOT NULL,
  `status` varchar(50) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `tanggal_booking` date DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `metode_pembayaran` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_pembayaran`),
  KEY `FK_pembayaran_booking` (`id_booking`),
  KEY `FK_pembayaran_rooms` (`id_room`),
  KEY `FK_pembayaran_customers` (`id_customer`),
  CONSTRAINT `FK_pembayaran_booking` FOREIGN KEY (`id_booking`) REFERENCES `booking` (`BNum`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_pembayaran_customers` FOREIGN KEY (`id_customer`) REFERENCES `customers` (`CustNum`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_pembayaran_rooms` FOREIGN KEY (`id_room`) REFERENCES `rooms` (`RNum`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table hotel_java.rooms
DROP TABLE IF EXISTS `rooms`;
CREATE TABLE IF NOT EXISTS `rooms` (
  `RNum` int(11) NOT NULL AUTO_INCREMENT,
  `RName` varchar(50) NOT NULL,
  `RType` varchar(30) NOT NULL,
  `RStatus` varchar(20) NOT NULL,
  `Price` int(11) NOT NULL,
  PRIMARY KEY (`RNum`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table hotel_java.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `UserID` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(50) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Role` varchar(20) NOT NULL,
  `Email` varchar(100) NOT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `Email` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data exporting was unselected.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
