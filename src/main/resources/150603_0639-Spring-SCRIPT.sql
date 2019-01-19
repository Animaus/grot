DROP SCHEMA IF EXISTS `nofas` ;
CREATE SCHEMA `nofas`;

DROP TABLE IF EXISTS `nofas`.`address`;
DROP TABLE IF EXISTS `nofas`.`user_group`;
DROP TABLE IF EXISTS `nofas`.`user`;
DROP TABLE IF EXISTS `nofas`.`group`;
DROP TABLE IF EXISTS `nofas`.`country`;

CREATE TABLE `nofas`.`user` (
	`user_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`first_name` VARCHAR(20) NOT NULL,
	`last_name` VARCHAR(20) NOT NULL,
	`prefix` VARCHAR(10) NULL,
	`sex` char(1) NOT NULL,
	`date_birth` DATE NOT NULL,
	`mobile_number` char(10) DEFAULT NULL,
	`phone_number` char(10) DEFAULT NULL,
	`user_name` char(7) NOT NULL UNIQUE,
	`alias` varchar(20) DEFAULT NULL,
	`member_code` varchar(11) NOT NULL UNIQUE,
	`mail_address` varchar(30) DEFAULT NULL,
	`password` VARCHAR(45) NOT NULL ,
	`enabled` TINYINT NOT NULL DEFAULT 1 ,
	PRIMARY KEY (`user_id`,`user_name`) USING BTREE,
	UNIQUE KEY `uni_user_name` (`user_name`),
	UNIQUE KEY `uni_user_id` (`user_id`) USING BTREE);

CREATE TABLE `nofas`.`address` (
	`user_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`street_name` VARCHAR(255) NOT NULL,
	`house_number` VARCHAR(255) NOT NULL,
	`address_code` CHAR(7) NOT NULL,
	`city` VARCHAR(128) NOT NULL,
	`country` CHAR(2) NOT NULL,
	KEY `fk_address_id` (`user_id`),
	CONSTRAINT `fk_address_id` FOREIGN KEY (`user_id`)
		REFERENCES `nofas`.`user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE `nofas`.`group` (
	`group_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`group_name` VARCHAR(20) NOT NULL UNIQUE,
	`group_desc` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`group_id`,`group_name`) USING BTREE,
	UNIQUE KEY `uni_group_name` (`group_name`),
	UNIQUE KEY `uni_group_id` (`group_id`) USING BTREE);

CREATE TABLE `nofas`.`user_group` (
	`user_id` INT(10) UNSIGNED NOT NULL,
	`group_id` INT(10) UNSIGNED NOT NULL,
	PRIMARY KEY (`user_id`,`group_id`),
	CONSTRAINT `fk_group_id` FOREIGN KEY (`group_id`)
		REFERENCES `nofas`.`group` (`group_id`) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`)
		REFERENCES `nofas`.`user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE `nofas`.`album` (
	`user_id` INT(10) UNSIGNED NOT NULL,
	`album_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`album_folder` char(8) NOT NULL,
	`title` VARCHAR(255) NOT NULL,
	`description` VARCHAR(255) NOT NULL,
	`published` TINYINT NOT NULL DEFAULT 1 ,
	PRIMARY KEY (`album_id`) USING BTREE,
	KEY `fk_album_id` (`user_id`),
	CONSTRAINT `fk_album_id` FOREIGN KEY (`user_id`)
		REFERENCES `nofas`.`user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE `nofas`.`photo` (
	`album_id` INT(10) UNSIGNED NOT NULL,
	`name` char(100) NOT NULL,
	`iso_value` char(10),
	`exposure` char(10),
	`date_exposure` DATETIME,
	`date_upload` DATETIME,
	`artist` char(50),
	`model` char(50),
	`width` char(5),
	`height` char(5),
	`aperture` char(6),
	`orientation` INT(1),
	`latitude` char(20),
	`longitude` char(20),
	`focus` char(6),
	`category_id` INT(10),
	`description` varchar(255),
	`exif` char(12),
	`published` TINYINT NOT NULL DEFAULT 1 ,
	PRIMARY KEY (`name`),
	CONSTRAINT `fk_photo_id` FOREIGN KEY (`album_id`)
		REFERENCES `nofas`.`album` (`album_id`) ON DELETE CASCADE ON UPDATE CASCADE);

CREATE TABLE `nofas`.`country` (
	`country_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`country_code` varchar(2) NOT NULL UNIQUE,
	`country_name` VARCHAR(50) NOT NULL UNIQUE,
	PRIMARY KEY (`country_id`) USING BTREE,
	UNIQUE KEY `uni_country_id` (`country_id`) USING BTREE);

CREATE TABLE `nofas`.`category` (
	`user_id` INT(10) UNSIGNED NOT NULL,
	`category_id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`category_text` VARCHAR(50) NOT NULL,
	PRIMARY KEY (`category_id`) USING BTREE,
	KEY `fk_category_id` (`user_id`),
	CONSTRAINT `fk_category_id` FOREIGN KEY (`user_id`)
		REFERENCES `nofas`.`user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE);

INSERT INTO `nofas`.`user`(`user_id`,`first_name`,`last_name`,`prefix`,`sex`,`date_birth`,`mobile_number`,`phone_number`,`user_name`,`alias`,`member_code`,`mail_address`,`password`,`enabled`) VALUES
	('1','Thierry','Fronte','de la','m','1013-12-2','0612341013','2412341013','front00','Rebel','10131202001','front00@domain.org','123456', true),
	('2','Jeanne','Arc','d\'','f','1413-12-2','0612341413','2412341413','arc0j00','Witch','14131202001','arc0j00@domain.org','123456', true),
	('3','Gerard','Zoethout','','m','1965-3-18','0612345678','0248765432','zoetg00','Augur','19650318001','zoetg00@domain.org','123456', true);

INSERT INTO `nofas`.`address`(`user_id`,`street_name`,`house_number`,`address_code`,`city`,`country`) VALUES
	('1','Forest','123','1013 TF','Amiens','FR'),
	('2','Convent','321 b','1413 JA','Orleans','FR'),
	('3','Koningsmantelhof','22','6533 SJ','Nijmegen','NL');

INSERT INTO `nofas`.`group`(`group_id`,`group_name`,`group_desc`) VALUES
	('1','admin','Administrators'),
	('2','user','Regular users'),
	('3','employee','Employee'),
	('4','student','Student'),
	('5','ROLE_USER','Gebruiker'),
	('6','ROLE_ADMIN','Beheerder');

INSERT INTO `nofas`.`user_group`(`user_id`,`group_id`) VALUES
	('1','6'),
	('1','5'),
	('2','5'),
	('3','6'),
	('3','5');

INSERT INTO `nofas`.`album`(`user_id`,`album_folder`,`title`,`description`,`published`) VALUES
	('1','140821', 'De belegering van Teylingen', 'Middeleeuws festival (Teylingen, ZH)', true),
	('1','140827', 'Gebroeders van Limburg (2)', 'Middeleeuws festival (Nijmegen, GLD)', true),
	('2','140829', 'Gebroeders van Limburg (3)', 'Middeleeuws festival (Nijmegen, GLD)', true),
	('2','140830', 'Gebroeders van Limburg (4)', 'Middeleeuws festival (Nijmegen, GLD)', true),
	('3','140831', 'Gebroeders van Limburg (5)', 'Middeleeuws festival (Nijmegen, GLD)', true),
	('3','141011', 'Gebroeders van Limburg (6)', 'Middeleeuws festival (Nijmegen, GLD)', true);

INSERT INTO `nofas`.`category`(`user_id`,`category_id`,`category_text`) VALUES
	('1','1','Natuur'),
	('1','2','Kunst'),
	('1','3','Geschiedenis'),
	('2','4','Politiek'),
	('2','5','Nieuws'),
	('2','6','Cultuur'),
	('3','7','Wetenschap'),
	('3','8','Esthetiek');

INSERT INTO `nofas`.`photo`(`album_id`,`name`,`iso_value`,`exposure`,`date_exposure`,`date_upload`,`artist`,`model`,`width`,`height`,`aperture`,`orientation`,`latitude`,`longitude`,`focus`,`category_id`,`description`,`exif`,`published`) VALUES
	('1','011_img_7360_edt.jpg','','','2015-03-09','2015-04-21 14:27:41','','','','','','0','','','','3','','000000000000', true),
	('1','IMG_0703.JPG','1000','1/41','2014-09-29 12:38:10','2015-04-21 14:27:41','Gerard Zoethout','Canon EOS 600D','5184','3456','4,6','1','','','29','3','','111111100111', true),
	('1','IMG_1147.JPG','100','1/255','2014-11-24 15:59:52','2015-04-21 14:27:41','Gerard Zoethout','Canon EOS 600D','5184','3456','9,9','1','','','55','3','','111111100111', true),
	('1','IMG_1155.JPG','100','1/98','2014-11-24 16:04:54','2015-04-21 14:27:41','Gerard Zoethout','Canon EOS 600D','5184','3456','6,4','1','','','18','3','','111111100111', true),
	('1','IMG_7380.JPG','500','1/63','2014-08-21 18:39:20','2015-04-21 14:27:41','Gerard Zoethout','Canon EOS 600D','5184','3456','5','8','','','39','3','','111111100111', true),
	('1','IMG_8742.JPG','1600','1/200','2014-09-28 16:23:55','2015-04-21 14:27:41','','Canon EOS 400D DIGITAL','3888','2592','9','1','','','24','3','','111111100111', true),
	('1','withIptcExifGps.jpg','','1/724','2002-07-13 15:58:28','2015-04-21 14:27:41','Ian Britton','FinePixS1Pro','2400','1600','16','1','54.989666666666665','-1.9141666666666666','','3','','111101011111', true);

INSERT INTO `nofas`.`country`(`country_id`,`country_code`,`country_name`) VALUES
	('1','AF','Afghanistan'),
	('2','AX','�land Eilanden'),
	('3','AL','Albani�'),
	('4','DZ','Algerije'),
	('5','AS','Amerikaans Samoa'),
	('6','AD','Andorra'),
	('7','AO','Angola'),
	('8','AI','Anguilla'),
	('9','AQ','Antarctica'),
	('10','AG','Antigua en Barbuda'),
	('11','AR','Argentini�'),
	('12','AM','Armeni�'),
	('13','AW','Aruba'),
	('14','AU','Australi�'),
	('15','AT','Oostenrijk'),
	('16','AZ','Azerbaijan'),
	('17','BS','Bahamas'),
	('18','BH','Bahrein'),
	('19','BD','Bangladesh'),
	('20','BB','Barbados'),
	('21','BY','Belarus'),
	('22','BE','Belgi�'),
	('23','BZ','Belize'),
	('24','BJ','Benin'),
	('25','BM','Bermuda'),
	('26','BT','Bhutan'),
	('27','BO','Bolivi�'),
	('28','BQ','Bonaire, Sint Eustatius en Saba'),
	('29','BA','Bosni�-Herzegovina'),
	('30','BW','Botswana'),
	('31','BV','Bouvet Eiland'),
	('32','BR','Brazili�'),
	('33','IO','Brits-Indisch Oceaangebied'),
	('34','BN','Brunei Darussalam'),
	('35','BG','Bulgarije'),
	('36','BF','Burkina Faso'),
	('37','BI','Burundi'),
	('38','KH','Cambodja'),
	('39','CM','Cameroen'),
	('40','CA','Canada'),
	('41','CV','Kaap Verdi�'),
	('42','KY','Kaaiman Eilanden'),
	('43','CF','Centraal Afrikaanse Republiek'),
	('44','TD','Tsjaad'),
	('45','CL','Chili'),
	('46','CN','China'),
	('47','CX','Kerstmis Eiland'),
	('48','CC','Cocos (Keeling Eilanden)'),
	('49','CO','Colombia'),
	('50','KM','Comoros'),
	('51','CG','Congo'),
	('52','CK','Cook Eilanden'),
	('53','CR','Costa Rica'),
	('54','CI','Cote d\'Ivoire (Ivory Coast)'),
	('55','HR','Croati� (Hrvatska)'),
	('56','CU','Cuba'),
	('57','CW','Cura�ao'),
	('58','CY','Cyprus'),
	('59','CZ','Tjechische Republiek'),
	('60','DK','Denemarken'),
	('61','DJ','Djibouti'),
	('62','DM','Dominica'),
	('63','DO','Dominicaanse Republiek'),
	('64','TP','Oost Timor'),
	('65','EC','Ecuador'),
	('66','EG','Egypte'),
	('67','SV','El Salvador'),
	('68','GQ','Equatoriaal Guinea'),
	('69','ER','Eritrea'),
	('70','EE','Estland'),
	('71','ET','Ethiopi�'),
	('72','FK','Falkland Eilanden (Malvinas)'),
	('73','FO','Faroe Eilanden'),
	('74','FJ','Fiji'),
	('75','FI','Finland'),
	('76','FR','Frankrijk'),
	('77','GF','Frans Guyana'),
	('78','PF','Frans Polynesi�'),
	('79','TF','Frans Zuidelijk gebied'),
	('80','GA','Gabon'),
	('81','GM','Gambia'),
	('82','GE','Georgi�'),
	('83','DE','Duitsland'),
	('84','GH','Ghana'),
	('85','GI','Gibraltar'),
	('86','GR','Griekenland'),
	('87','GL','Groenland'),
	('88','GD','Grenada'),
	('89','GP','Guadeloupe'),
	('90','GU','Guam'),
	('91','GT','Guatemala'),
	('92','GG','Guernsey'),
	('93','GN','Guinea'),
	('94','GW','Guinea-Bissau'),
	('95','GY','Guyana'),
	('96','HT','Ha�ti'),
	('97','HM','Heard en McDonald Eilanden'),
	('98','HN','Honduras'),
	('99','HK','Hong Kong'),
	('100','HU','Hungary'),
	('101','IS','Iceland'),
	('102','IN','India'),
	('103','ID','Indonesi�'),
	('104','IR','Iran'),
	('105','IQ','Irak'),
	('106','IE','Ierland'),
	('107','IM','Eiland van Man'),
	('108','IL','Isra�l'),
	('109','IT','Itali�'),
	('110','JM','Jamaica'),
	('111','JP','Japan'),
	('112','JE','Jersey'),
	('113','JO','Jordani�'),
	('114','KZ','Kazachstan'),
	('115','KE','Kenia'),
	('116','KI','Kiribati'),
	('117','KP','Korea (Noord) (Volksrepubliek)'),
	('118','KR','Korea (Zuid) (Republiek)'),
	('119','KW','Kuweit'),
	('120','KG','Kirgizistan (Kirgizische Republiek)'),
	('121','LA','Laos'),
	('122','LV','Letland'),
	('123','LB','Libanon'),
	('124','LS','Lesotho'),
	('125','LR','Liberia'),
	('126','LY','Libya'),
	('127','LI','Liechtenstein'),
	('128','LT','Litouwen'),
	('129','LU','Luxemburg'),
	('130','MO','Macao'),
	('131','MK','Macedoni�'),
	('132','MG','Madagascar'),
	('133','MW','Malawi'),
	('134','MY','Maleisi�'),
	('135','MV','Malediven'),
	('136','ML','Mali'),
	('137','MT','Malta'),
	('138','MH','Marshall Eilanden'),
	('139','MQ','Martinique'),
	('140','MR','Mauritani�'),
	('141','MU','Mauritius'),
	('142','YT','Mayotte'),
	('143','MX','Mexico'),
	('144','FM','Micronesi�'),
	('145','MD','Moldovi�'),
	('146','MC','Monaco'),
	('147','MN','Mongoli�'),
	('148','ME','Montenegro'),
	('149','MS','Montserrat'),
	('150','MA','Marokko'),
	('151','MZ','Mozambique'),
	('152','MM','Myanmar'),
	('153','NA','Namibi�'),
	('154','NR','Nauru'),
	('155','NP','Nepal'),
	('156','NL','Nederland'),
	('157','NC','Nieuw Caledoni�'),
	('158','NZ','Nieuw Zeeland'),
	('159','NI','Nicaragua'),
	('160','NE','Niger'),
	('161','NG','Nigeria'),
	('162','NU','Niue'),
	('163','NF','Norfolk Eiland'),
	('164','MP','Noordelijke Marianen Eilanden'),
	('165','NO','Norwegen'),
	('166','OM','Oman'),
	('167','PK','Pakistan'),
	('168','PW','Palau'),
	('169','PS','Palestine'),
	('170','PA','Panama'),
	('171','PG','Papua Nieuw Guinea'),
	('172','PY','Paraguay'),
	('173','PE','Peru'),
	('174','PH','Filippijnen'),
	('175','PN','Pitcairn'),
	('176','PL','Polen'),
	('177','PT','Portugal'),
	('178','PR','Puerto Rico'),
	('179','QA','Qatar'),
	('180','RE','Reunion'),
	('181','RO','Roemeni�'),
	('182','RU','Russische Federatie'),
	('183','RW','Rwanda'),
	('184','SH','Sint Helena, Ascension en Tristan'),
	('185','KN','Sint Kitts en Nevis'),
	('186','LC','Sint Lucia'),
	('187','PM','Sint Pierre en Miquelon'),
	('188','VC','Sint Vincent en The Grenadines'),
	('189','WS','Samoa'),
	('190','SM','San Marino'),
	('191','ST','Sao Tome en Principe'),
	('192','SA','Saudi Arabi�'),
	('193','SN','Senegal'),
	('194','RS','Servi�'),
	('195','SC','Seychelles'),
	('196','SL','Sierra Leone'),
	('197','SG','Singapore'),
	('198','SX','Sint Maarten (Nederlands deel)'),
	('199','SK','Slovakije (Slovaakse Republiek)'),
	('200','SI','Sloveni�'),
	('201','SB','Solomon Eilanden'),
	('202','SO','Somali�'),
	('203','ZA','Zuid-Afrika'),
	('204','GS','Zuid Georgi� en Sandwich Eilanden'),
	('205','SU','Sovjet Unie (voormalig)'),
	('206','ES','Spanje'),
	('207','LK','Sri Lanka'),
	('208','SD','Soedan'),
	('209','SR','Suriname'),
	('210','SJ','Svalbard en Jan Mayen Eilanden'),
	('211','SZ','Swaziland'),
	('212','SE','Zweden'),
	('213','CH','Zwitserland'),
	('214','SY','Syri�'),
	('215','TW','Taiwan'),
	('216','TJ','Tajikistan'),
	('217','TZ','Tanzania'),
	('218','TH','Thailand'),
	('219','TL','Timor-Leste'),
	('220','TG','Togo'),
	('221','TK','Tokelau'),
	('222','TO','Tonga'),
	('223','TT','Trinidad en Tobago'),
	('224','TN','Tunesi�'),
	('225','TR','Turkey'),
	('226','TM','Turkmenistan'),
	('227','TC','Turks en Caicos Eilanden'),
	('228','TV','Tuvalu'),
	('229','UG','Oeganda'),
	('230','UA','Oekra�ne'),
	('231','AE','Verenigde Arabische Emiraten'),
	('232','GB','Verenigd Koninkrijk (Groot-Britanni�)'),
	('233','UM','Verenigde Staten Kleine Buiteneilanden'),
	('234','US','Verenigde Staten'),
	('235','UY','Uruguay'),
	('236','UZ','Uzbekistan'),
	('237','VU','Vanuatu'),
	('238','VA','Vaticaanstad'),
	('239','VE','Venezuela'),
	('240','VN','Viet Nam'),
	('241','VG','Maagden Eilanden (Brits)'),
	('242','VI','Maagden Eilanden (Amerikaans)'),
	('243','WF','Wallis en Futuna Eilanden'),
	('244','EH','Westelijke Sahara'),
	('245','YE','Yemen'),
	('246','ZM','Zambia'),
	('247','ZW','Zimbabwe');