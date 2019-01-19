/*
H:\_java\_opleiding 2018\_eduvision\case\.dev\.dev_projects\grot\src\main\resources\db.sql
*/

DROP SCHEMA IF EXISTS `db_example` ;

CREATE SCHEMA `db_example`;

/*
	`email` varchar(30) DEFAULT NULL,
*/

CREATE TABLE `db_example`.`user` (
	`user_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`user_name` char(7) NOT NULL UNIQUE,
	`first_name` VARCHAR(20) NOT NULL,
	`last_name` VARCHAR(20) NOT NULL,
	`prefix` VARCHAR(10) NULL,
	`sex` char(1) NOT NULL,
	`password` VARCHAR(45) NOT NULL ,
	`enabled` TINYINT NOT NULL DEFAULT 1 ,
	`date_birth` DATETIME NOT NULL,
	`date_registered` DATETIME NOT NULL,
	`date_modified` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`user_id`,`user_name`) USING BTREE,
	UNIQUE KEY `uni_user_name` (`user_name`) USING BTREE,
	UNIQUE KEY `uni_user_id` (`user_id`) USING BTREE);

CREATE TABLE `db_example`.`role` (
	`role_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`role_name` VARCHAR(20) NOT NULL UNIQUE,
	`role_desc` VARCHAR(255) NOT NULL,
	`date_modified` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`role_id`,`role_name`) USING BTREE,
	UNIQUE KEY `uni_role_name` (`role_name`) USING BTREE,
	UNIQUE KEY `uni_role_id` (`role_id`) USING BTREE);

CREATE TABLE `db_example`.`user_role` (
	`user_id` INT(10) UNSIGNED NOT NULL,
	`role_id` INT(10) UNSIGNED NOT NULL,
	`date_modified` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`user_id`,`role_id`),
	CONSTRAINT `fk_userrole_role_id` FOREIGN KEY (`role_id`)
		REFERENCES `db_example`.`role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT `fk_userrole_user_id` FOREIGN KEY (`user_id`)
		REFERENCES `db_example`.`user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE);

/*
INSERT INTO `db_example`.`user`(`user_id`,`user_name`,`first_name`,`last_name`,`prefix`,`sex`,`email`,`password`,`enabled`,`date_birth`,`date_registered`) VALUES
	('1','front00','Thierry','Fronte','de la','m','thierry@domain.org','123456',false,'1118-06-17','2018-06-17'),
	('2','arc0j00','Jeanne','Arc','d\'','f','jeanne@domain.org','123456',false,'1218-06-17','2018-06-17'),
	('3','zoetg00','Gerard','Zoethout','','m','gerard@domain.org','123456',true,'1965-03-18','2018-06-17');
*/
INSERT INTO `db_example`.`user`(`user_id`,`user_name`,`first_name`,`last_name`,`prefix`,`sex`,`password`,`enabled`,`date_birth`,`date_registered`) VALUES
	('1','front00','Thierry','Fronte','de la','m','123456',false,'1118-06-17','2018-06-17'),
	('2','arc0j00','Jeanne','Arc','d\'','f','123456',false,'1218-06-17','2018-06-17'),
	('3','zoetg00','Gerard','Zoethout','','m','123456',true,'1965-03-18','2018-06-17');

INSERT INTO `db_example`.`role`(`role_id`,`role_name`,`role_desc`) VALUES
	('1','admin','Administrators'),
	('2','user','Regular users'),
	('3','employee','Employee'),
	('4','student','Student'),
	('5','ROLE_USER','Gebruiker'),
	('6','ROLE_ADMIN','Beheerder');

INSERT INTO `db_example`.`user_role`(`user_id`,`role_id`) VALUES
	('1','6'),
	('1','5'),
	('2','5'),
	('3','6'),
	('3','5');

/*
CREATE TABLE `db_example`.`address` (
	`address_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`user_id` INT(10) UNSIGNED DEFAULT NULL,
	`street_name` VARCHAR(255) NOT NULL,
	`street_number` VARCHAR(255) NOT NULL,
	`zip` CHAR(7) NOT NULL,
	`city` VARCHAR(128) NOT NULL,
	`country` CHAR(2) NOT NULL,
	`phone1` char(10) DEFAULT NULL,
	`phone2` char(10) DEFAULT NULL,
	`email1` varchar(30) DEFAULT NULL,
	`email2` varchar(30) DEFAULT NULL,
	`date_modified` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`address_id`, `user_id`),
	CONSTRAINT `fk_address_user_id` FOREIGN KEY (`user_id`)
		REFERENCES `db_example`.`user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE);

INSERT INTO `db_example`.`address`(`address_id`,`user_id`,`street_name`,`street_number`,`zip`,`city`,`country`,`phone1`,`phone2`,`email1`,`email2`) VALUES
	('1','1','Forest','123','1013 TF','Amiens','FR','0612345678','','thierry@domain.org',''),
	('2','2','Convent','321 b','1413 JA','Orleans','FR','0687654321','','jeanne@domain.org',''),
	('3','3','Koningsmantelhof','22','6533 SJ','Nijmegen','NL','0621854671','','gerard@domain.org','');

	KEY `fk_address_user_id` (`user_id`),
	`user_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,

	PRIMARY KEY (`user_id`, `user_name`),
*/

CREATE TABLE `db_example`.`address` (
	`user_id` INT(10) UNSIGNED UNIQUE,
	`user_name` char(7) NOT NULL UNIQUE,
	`street_name` VARCHAR(255) NOT NULL,
	`street_number` VARCHAR(255) NOT NULL,
	`zip` CHAR(7) NOT NULL,
	`city` VARCHAR(128) NOT NULL,
	`country` CHAR(2) NOT NULL,
	`phone1` char(10) DEFAULT NULL,
	`phone2` char(10) DEFAULT NULL,
	`email1` varchar(30) DEFAULT NULL,
	`email2` varchar(30) DEFAULT NULL,
	`date_modified` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`user_id`, `user_name`),
	UNIQUE KEY `uni_user_id` (`user_id`) USING BTREE,
	UNIQUE KEY `uni_user_name` (`user_name`) USING BTREE,
	CONSTRAINT `fk_address_user_id` FOREIGN KEY (`user_id`)
		REFERENCES `db_example`.`user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE);

INSERT INTO `db_example`.`address`(`user_id`,`user_name`,`street_name`,`street_number`,`zip`,`city`,`country`,`phone1`,`phone2`,`email1`,`email2`) VALUES
	('1','front00','Forest','123','1013 TF','Amiens','FR','0612345678','','thierry@domain.org',''),
	('2','arc0j00','Convent','321 b','1413 JA','Orleans','FR','0687654321','','jeanne@domain.org',''),
	('3','zoetg00','Koningsmantelhof','22','6533 SJ','Nijmegen','NL','0621854671','','gerard@domain.org','');
