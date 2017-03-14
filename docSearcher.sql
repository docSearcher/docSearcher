/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.7.10 : Database - docsearcher
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`docsearcher` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `docsearcher`;

/*Table structure for table `dividefileinfo` */

DROP TABLE IF EXISTS `dividefileinfo`;

CREATE TABLE `dividefileinfo` (
  `divFile_id` int(50) NOT NULL AUTO_INCREMENT,
  `file_id` int(50) DEFAULT NULL,
  `pptFileName` varchar(100) DEFAULT NULL,
  `pptPageNumber` int(11) DEFAULT NULL,
  `fileName` varchar(100) DEFAULT NULL,
  `fileUrl` varchar(100) DEFAULT NULL,
  `createBy` varchar(50) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`divFile_id`),
  KEY `FK_Reference_4` (`file_id`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`file_id`) REFERENCES `uploadfileinfo` (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `dividefileinfo` */

/*Table structure for table `dividepresspicinfo` */

DROP TABLE IF EXISTS `dividepresspicinfo`;

CREATE TABLE `dividepresspicinfo` (
  `pressPic_id` int(50) NOT NULL AUTO_INCREMENT,
  `file_id` int(50) DEFAULT NULL,
  `filename` varchar(100) DEFAULT NULL,
  `fileUrl` varchar(200) DEFAULT NULL,
  `createBy` varchar(50) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`pressPic_id`),
  KEY `FK_Reference_3` (`file_id`),
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`file_id`) REFERENCES `uploadfileinfo` (`file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=utf8;

/*Data for the table `dividepresspicinfo` */


/*Table structure for table `picinfo` */

DROP TABLE IF EXISTS `picinfo`;

CREATE TABLE `picinfo` (
  `picInfo_id` int(50) NOT NULL,
  `file_id` int(50) DEFAULT NULL,
  `fileName` varchar(100) DEFAULT NULL,
  `fileUrl` varchar(200) DEFAULT NULL,
  `fileContent` varchar(5000) DEFAULT NULL,
  `filePageNumber` int(11) DEFAULT NULL,
  `createBy` varchar(50) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`picInfo_id`),
  KEY `FKDD8B41381B4D995F` (`file_id`),
  CONSTRAINT `FKDD8B41381B4D995F` FOREIGN KEY (`file_id`) REFERENCES `uploadfileinfo` (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `picinfo` */

i
/*Table structure for table `singlepagepptitem` */

DROP TABLE IF EXISTS `singlepagepptitem`;

CREATE TABLE `singlepagepptitem` (
  `item_id` int(50) NOT NULL AUTO_INCREMENT,
  `picInfo_id` int(50) DEFAULT NULL,
  `divfile_id` int(50) DEFAULT NULL,
  `pressPic_id` int(50) DEFAULT NULL,
  `pptPageNumber` int(11) DEFAULT NULL,
  `pptFileName` varchar(100) DEFAULT NULL,
  `itemContent` varchar(5000) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `createBy` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`item_id`),
  KEY `FK_Reference_7` (`divfile_id`),
  KEY `FK_Reference_8` (`pressPic_id`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`divfile_id`) REFERENCES `dividefileinfo` (`divFile_id`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`pressPic_id`) REFERENCES `dividepresspicinfo` (`pressPic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `singlepagepptitem` */

/*Table structure for table `uploadfileinfo` */

DROP TABLE IF EXISTS `uploadfileinfo`;

CREATE TABLE `uploadfileinfo` (
  `file_id` int(50) NOT NULL AUTO_INCREMENT,
  `user_id` int(50) DEFAULT NULL,
  `fileName` varchar(100) DEFAULT NULL,
  `fileSize` mediumtext,
  `fileFormats` varchar(20) DEFAULT NULL,
  `fileUrl` varchar(200) DEFAULT NULL,
  `fileUploadtime` datetime DEFAULT NULL,
  PRIMARY KEY (`file_id`),
  KEY `FK_Reference_11` (`user_id`),
  CONSTRAINT `FK_Reference_11` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `uploadfileinfo` */


/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(50) NOT NULL AUTO_INCREMENT,
  `userName` varchar(50) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `genter` int(10) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `picUrl` varchar(100) DEFAULT NULL,
  `activeId` varchar(64) DEFAULT NULL,
  `password` varchar(50) NOT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `token` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`user_id`,`userName`,`email`,`genter`,`mobile`,`picUrl`,`activeId`,`password`,`createTime`,`updateTime`,`token`) values (1,'userName','1234@qq.com',1,'13879396651',NULL,'1','25d55ad283aa400af464c76d713c07ad','2016-12-28 21:48:15','2016-12-28 22:59:57','fb222e59ac033d212025f02ccd3e3c31');

/*Table structure for table `user_pptitem` */

DROP TABLE IF EXISTS `user_pptitem`;

CREATE TABLE `user_pptitem` (
  `user_id` int(50) DEFAULT NULL,
  `item_id` int(50) DEFAULT NULL,
  KEY `FK_Reference_10` (`item_id`),
  KEY `FK_Reference_9` (`user_id`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`item_id`) REFERENCES `singlepagepptitem` (`item_id`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_pptitem` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
