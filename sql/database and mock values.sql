-- Oscar's rental database structure for module GPSE
-- 07/03/2012
-- Author: Stefano Chiodino

-- --------------------------------------------------------
--
-- Table structure for table `branch`
--

CREATE TABLE IF NOT EXISTS `branch` (
  `branchId` int(11) NOT NULL auto_increment,
  `location` varchar(100) NOT NULL,
  `country` enum('UK', 'Italy') NOT NULL default 'UK',
  PRIMARY KEY (`branchId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

-- --------------------------------------------------------
--
-- Table structure for table `carClass`
--

CREATE TABLE IF NOT EXISTS `carClass` (
  `className` varchar(20) NOT NULL,
  `displayName` varchar(20) NOT NULL,
  `description` TEXT NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`className`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

-- --------------------------------------------------------
--
-- Table structure for table `car`
--

CREATE TABLE IF NOT EXISTS `car` (
  `plate` varchar(20) NOT NULL,
  `brand` varchar(10) NOT NULL,
  `model` varchar(20) NOT NULL,
  `year` int(4) NOT NULL,
  `mileage` int(7) NOT NULL,
  `lastServiceMileage` int(7) NOT NULL DEFAULT 0,
  `lastServiceDate` date NOT NULL,
  `className` varchar(20) NOT NULL,
  `color` varchar(20) NOT NULL,
  `branch` int(11) NOT NULL,
  PRIMARY KEY (`plate`),
  FOREIGN KEY (`className`) REFERENCES `carClass`(`className`),
  FOREIGN KEY (`branch`) REFERENCES `branch`(`branchId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------
--
-- Table structure for table `person`
--

CREATE TABLE IF NOT EXISTS `person` (
  `personId` int(11) NOT NULL auto_increment,
  `name` varchar(40) NOT NULL,
  `surname` varchar(40) NOT NULL,
  `dateOfBirth` date NOT NULL,
  `email` varchar(40) NOT NULL,
  `address` text NOT NULL,
  `phone` varchar(30) NOT NULL,
  PRIMARY KEY (`personId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

-- --------------------------------------------------------
--
-- Table structure for table `customer`
--

CREATE TABLE IF NOT EXISTS `customer` (
  `personId` int(11) NOT NULL,
  `username` varchar(20) NOT NULL,      
  `password` char(64) NOT NULL,
  PRIMARY KEY (`personId`),
  FOREIGN KEY (`personId`) REFERENCES `person`(`personId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

-- --------------------------------------------------------
--
-- Table structure for table `staff`
--

CREATE TABLE IF NOT EXISTS `staff` (
  `personId` int(11) NOT NULL,
  `username` varchar(20) NOT NULL,      
  `password` char(64) NOT NULL,
  `attributes` set('admin', 'chauffeur') NOT NULL DEFAULT '',
  PRIMARY KEY (`personId`),
  FOREIGN KEY (`personId`) REFERENCES `person`(`personId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

-- --------------------------------------------------------
--
-- Table structure for table `rental`
--

CREATE TABLE IF NOT EXISTS `rental` (
  `rentalId` int(11) NOT NULL auto_increment,
  `startDatetime` datetime NOT NULL,
  `endDatetime` datetime NOT NULL,
  `carPlate` varchar(20) NOT NULL,
  `customerId` int(11) NOT NULL,
  `amountPaid` decimal(10,2) NOT NULL,
  `isBooking` tinyint(1) NOT NULL DEFAULT 0,
  `depositAmount` decimal(10,2) NOT NULL,
  PRIMARY KEY (`rentalId`),
  FOREIGN KEY (`carPlate`) REFERENCES `car`(`plate`),
  FOREIGN KEY (`customerId`) REFERENCES `person`(`personId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

-- --------------------------------------------------------
--
-- Table structure for table `carBranchReturn`
--
CREATE TABLE IF NOT EXISTS `carBranchReturn` (
  `rentalId`  int(11) NOT NULL,
  `branchId` int(11) NOT NULL,
  PRIMARY KEY (`rentalId`, `branchId`),
  FOREIGN KEY  (`rentalId`) REFERENCES `rental`(`rentalId`),
  FOREIGN KEY  (`branchId`) REFERENCES `branch`(`branchId`)  
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

-- --------------------------------------------------------
--
-- Table structure for table `rentalDamage`
--

CREATE TABLE IF NOT EXISTS `rentalDamage` (
  `rentalDamageId` int(11) NOT NULL auto_increment,
  `rentalId` int(11) NOT NULL,
  `cost` decimal(10,0) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`rentalDamageId`),
  FOREIGN KEY (`rentalId`) REFERENCES `rental`(`rentalId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;



-- #@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#
-- #@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#
-- #@#@#@#@#@  Mock values for testing purpose   #@#@#@#@#@#
-- #@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#
-- #@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#@#
INSERT INTO `branch` (`branchId`, `location`, `country`) VALUES
	(NULL, 'Leeds, Beeston road 33, LS116AD', 'UK'),
	(NULL, 'Bradford, Centenary road 14, BD13AD', 'UK'),
	(NULL, 'Cagliari, Via Roma 33, 09133', 'Italy');
INSERT INTO `carClass` (`className`, `displayName`, `description`, `price`) VALUES
	('A_IT', 'A', 'Classe di macchine superiori come la BMW serie 5', 24.00),
	('A_UK', 'A', 'Superior class of car such as BMW 5 serie', 20.00),
	('A_UK_promo', 'A (promotion)', 'superior class dajhasdghsfh', 15.00),
	('B_UK', 'B', 'Class of good car such as mercedes C class.', 17.00);
INSERT INTO `schiodin`.`car` (`plate`, `brand`, `model`, `year`, `mileage`, `lastServiceMileage`, `lastServiceDate`, `className`, `color`, `branch`) VALUES
	('asd54fassd', 'Ford', 'Focus 2.0 TD', '2004', '10020', '10000', '2012-03-02', 'A_UK', 'Black', '1'),
	('dasfd356ds', 'Fiat', '500 Abarth', '2008', '50321', '40000', '2012-02-18', 'A_IT', 'Bianca', '3'),
	('dsa65asefg', 'Ford', 'Model T', '1934', '999999', '900000', '2011-03-11', 'A_UK_promo', 'Green', '2'),
	('123zs6sr', 'Mercedes', 'C 2.2', '1995', '203344', '200000', '2012-03-07', 'B_UK', 'White', '2');
INSERT INTO `schiodin`.`person` (`personId`, `name`, `surname`, `dateOfBirth`, `email`, `address`, `phone`) VALUES
	(1, 'Stefano', 'Chiodino', '2002-03-16', 'LouderTh@gmail.com', 'Greenhouse, Beeston road, Leeds LS116AD', '075462962149'),
	(2, 'Sujan', 'Dhakal', '2002-03-15', 'sujandhakal@boh.fda', 'here there and thereafter, maybe', '+447549746378'),
	(3, 'Fake', 'One', '2012-03-16', 'fasgsdf@asfg.it', 'WRAUHEDTHSZDFGBSZ', '64282467346'),
	(4, 'Fake', 'Two', '2012-03-16', 'satgasdf@asdf.it', 'eqwhaeegasfg', '2356452467'),
	(5, 'Fake', 'Three', '2001-03-16', 'satdsafgsdf@asdf.it', 'eqwhaeegasfg', '2356452467');
INSERT INTO `staff` (`personId`, `username`, `password`, `attributes`) VALUES
	(1, 'Draga', 'd230e2e591caccbb104f3da6a094f503ec0e4745b232f24e259e092b91ce261f', 'admin'),
	(2, 'Sujan', 'd230e2e591caccbb104f3da6a094f503ec0e4745b232f24e259e092b91ce261f', 'chauffeur');
INSERT INTO `schiodin`.`customer` (`personId`, `username`, `password`) VALUES
	('3', 'dsgjsfngdsdfgh', 'd230e2e591caccbb104f3da6a094f503ec0e4745b232f24e259e092b91ce261f'),
	('4', 'sdghsghsf', 'd230e2e591caccbb104f3da6a094f503ec0e4745b232f24e259e092b91ce261f');
INSERT INTO `schiodin`.`rental` (`rentalId`, `startDatetime`, `endDatetime`, `carPlate`, `customerId`, `amountPaid`, `isBooking`, `depositAmount`) VALUES
	(NULL, '2012-03-15 00:00:00', '2012-03-29 12:00:00', '123zs6sr', '1', '100', '1', '200'),
	(NULL, '2012-03-14 20:00:00', '2012-03-23 12:00:00', 'dasfd356ds', '2', '100', '0', '150');
INSERT INTO `schiodin`.`rentalDamage` (`rentalDamageId`, `rentalId`, `cost`, `description`)
	VALUES (NULL, '1', '15', 'Broken mirror');