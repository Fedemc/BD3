-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.1.71-community


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema escuela
--

CREATE DATABASE IF NOT EXISTS escuela;
USE escuela;

--
-- Definition of table `alumnos`
--

DROP TABLE IF EXISTS `alumnos`;
CREATE TABLE `alumnos` (
  `cedula` int(11) NOT NULL,
  `cedulaMaestra` int(11) NOT NULL,
  KEY `cedula` (`cedula`),
  KEY `cedulaMaestra` (`cedulaMaestra`),
  CONSTRAINT `alumnos_ibfk_1` FOREIGN KEY (`cedula`) REFERENCES `personas` (`cedula`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `alumnos`
--

/*!40000 ALTER TABLE `alumnos` DISABLE KEYS */;
INSERT INTO `alumnos` (`cedula`,`cedulaMaestra`) VALUES 
 (11111,22222),
 (12412,22222),
 (15445,22222),
 (21212,22222),
 (90999,22222),
 (90999,88888),
 (121242,88888),
 (423112,88888);
/*!40000 ALTER TABLE `alumnos` ENABLE KEYS */;


--
-- Definition of table `maestras`
--

DROP TABLE IF EXISTS `maestras`;
CREATE TABLE `maestras` (
  `cedula` int(11) NOT NULL,
  `grupo` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`cedula`),
  CONSTRAINT `maestras_ibfk_1` FOREIGN KEY (`cedula`) REFERENCES `personas` (`cedula`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `maestras`
--

/*!40000 ALTER TABLE `maestras` DISABLE KEYS */;
INSERT INTO `maestras` (`cedula`,`grupo`) VALUES 
 (22222,'Game Design'),
 (88888,'Game Production');
/*!40000 ALTER TABLE `maestras` ENABLE KEYS */;


--
-- Definition of table `personas`
--

DROP TABLE IF EXISTS `personas`;
CREATE TABLE `personas` (
  `cedula` int(11) NOT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `apellido` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`cedula`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `personas`
--

/*!40000 ALTER TABLE `personas` DISABLE KEYS */;
INSERT INTO `personas` (`cedula`,`nombre`,`apellido`) VALUES 
 (11111,'Carlos','Montal'),
 (12412,'Luis','Pereira'),
 (15445,'Pepe','Rodriguez'),
 (21212,'Prueba','Java'),
 (22222,'Shigeru','Miyamoto'),
 (88888,'Hidetaka','Miyazaki'),
 (90999,'Jorge','Fernandez'),
 (121242,'Sekiro','Okami'),
 (423112,'Vladimir','Ivanof');
/*!40000 ALTER TABLE `personas` ENABLE KEYS */;


--
-- Definition of procedure `BorrarMaestra`
--

DROP PROCEDURE IF EXISTS `BorrarMaestra`;

DELIMITER $$

/*!50003 SET @TEMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `BorrarMaestra`(ced INT)
BEGIN
DECLARE cant INT DEFAULT 0;
SET cant = (SELECT COUNT(cedula) FROM Maestras m WHERE m.cedula = ced);
IF cant = 1 THEN
SELECT a.cedula FROM Alumnos a WHERE a.cedulaMaestra = ced;
SELECT grupo FROM Maestras m WHERE m.cedula = ced;
SELECT nombre, apellido FROM Personas p WHERE p.cedula = ced;
DELETE FROM Alumnos WHERE cedulaMaestra = ced;
DELETE FROM Maestras WHERE cedula = ced;
DELETE FROM Personas WHERE cedula = ced;
END IF;
END $$
/*!50003 SET SESSION SQL_MODE=@TEMP_SQL_MODE */  $$

DELIMITER ;



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
