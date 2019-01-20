/*
H:\_java\_opleiding 2018\_eduvision\case\.dev\.dev_projects\grot\src\main\resources\db.sql
*/

DROP SCHEMA IF EXISTS `db_example` ;
CREATE SCHEMA `db_example`;

DROP TABLE IF EXISTS `db_example`.`user_role`;
DROP TABLE IF EXISTS `db_example`.`user`;
DROP TABLE IF EXISTS `db_example`.`role`;

CREATE TABLE `db_example`.`user` (
	`user_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`user_name` char(7) NOT NULL UNIQUE,
	`first_name` VARCHAR(20) NOT NULL,
	`last_name` VARCHAR(20) NOT NULL,
	`prefix` VARCHAR(10) NULL,
	`sex` char(1) NOT NULL,
	`email` varchar(30) DEFAULT NULL,
	`password` VARCHAR(45) NOT NULL ,
	`enabled` TINYINT NOT NULL DEFAULT 1 ,
	`date_birth` DATETIME NOT NULL,
	`date_registered` DATETIME NOT NULL,
	`date_modified` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`user_id`,`user_name`) USING BTREE,
	UNIQUE KEY `uni_user_name` (`user_name`),
	UNIQUE KEY `uni_user_id` (`user_id`) USING BTREE);

CREATE TABLE `db_example`.`role` (
	`role_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`role_name` VARCHAR(20) NOT NULL UNIQUE,
	`role_desc` VARCHAR(255) NOT NULL,
	`date_modified` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`role_id`,`role_name`) USING BTREE,
	UNIQUE KEY `uni_group_name` (`role_name`),
	UNIQUE KEY `uni_group_id` (`role_id`) USING BTREE);

CREATE TABLE `db_example`.`user_role` (
	`user_id` INT(10) UNSIGNED NOT NULL,
	`role_id` INT(10) UNSIGNED NOT NULL,
	`date_modified` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`user_id`,`role_id`),
	CONSTRAINT `fk_group_id` FOREIGN KEY (`role_id`)
		REFERENCES `db_example`.`role` (`role_id`) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`)
		REFERENCES `db_example`.`user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE);

INSERT INTO `db_example`.`user`(`user_id`,`user_name`,`first_name`,`last_name`,`prefix`,`sex`,`email`,`password`,`enabled`,`date_birth`,`date_registered`) VALUES
	('1','front00','Thierry','Fronte','de la','m','thierry@domain.org','123456',false,'1118-06-17','2018-06-17'),
	('2','arc0j00','Jeanne','Arc','d\'','f','jeanne@domain.org','123456',false,'1218-06-17','2018-06-17'),
	('3','zoetg00','Gerard','Zoethout','','m','gerard@domain.org','123456',true,'1965-03-18','2018-06-17');

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

