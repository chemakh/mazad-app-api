-- --------------------------------------------------------
-- Hôte :                        127.0.0.1
-- Version du serveur:           5.7.17-log - MySQL Community Server (GPL)
-- SE du serveur:                Win64
-- HeidiSQL Version:             9.4.0.5174
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Export de la structure de la table mazad. article
CREATE TABLE IF NOT EXISTS `article` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` tinyblob,
  `creation_date` datetime DEFAULT NULL,
  `current_price` decimal(19,2) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `final_price` decimal(19,2) DEFAULT NULL,
  `initial_price` decimal(19,2) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `sold` bit(1) NOT NULL,
  `avatar_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rti6xu203k7hfohpu89214w6c` (`reference`),
  UNIQUE KEY `index_article_reference` (`reference`),
  KEY `FK4x9vnu2o62gw3yqrb692wtg9m` (`avatar_id`),
  CONSTRAINT `FK4x9vnu2o62gw3yqrb692wtg9m` FOREIGN KEY (`avatar_id`) REFERENCES `photo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Les données exportées n'étaient pas sélectionnées.
-- Export de la structure de la table mazad. authority
CREATE TABLE IF NOT EXISTS `authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Les données exportées n'étaient pas sélectionnées.
-- Export de la structure de la table mazad. bid
CREATE TABLE IF NOT EXISTS `bid` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bid_amount` decimal(19,2) DEFAULT NULL,
  `final_price` decimal(19,2) DEFAULT NULL,
  `initial_price` decimal(19,2) DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `article_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pskinv949oxyndma0di6xvh40` (`reference`),
  UNIQUE KEY `index_bid_reference` (`reference`),
  KEY `FK2lbh9otawlroajeyo15q0dd81` (`article_id`),
  CONSTRAINT `FK2lbh9otawlroajeyo15q0dd81` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Les données exportées n'étaient pas sélectionnées.
-- Export de la structure de la table mazad. photo
CREATE TABLE IF NOT EXISTS `photo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `label` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `article_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_mtuo907255q2sc866tm1kt5ao` (`reference`),
  UNIQUE KEY `index_photo_reference` (`reference`),
  KEY `FKrm4wnmb1hcwhxvrmf2x6gf9ox` (`article_id`),
  CONSTRAINT `FKrm4wnmb1hcwhxvrmf2x6gf9ox` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Les données exportées n'étaient pas sélectionnées.
-- Export de la structure de la table mazad. sale
CREATE TABLE IF NOT EXISTS `sale` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bid` tinyblob,
  `reference` varchar(255) DEFAULT NULL,
  `sold_by` tinyblob,
  `sold_date` datetime DEFAULT NULL,
  `sold_price` decimal(19,2) DEFAULT NULL,
  `article_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_g36meeytxqihj8xyvwlalm0jw` (`reference`),
  UNIQUE KEY `index_sale_reference` (`reference`),
  KEY `FKmcu3pp05qnalysdc7a202ls8o` (`article_id`),
  CONSTRAINT `FKmcu3pp05qnalysdc7a202ls8o` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Les données exportées n'étaient pas sélectionnées.
-- Export de la structure de la table mazad. user
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activated` bit(1) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `email_key` varchar(20) DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `is_mail_verified` tinyint(1) DEFAULT '0',
  `lang_key` varchar(5) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `password` varchar(60) DEFAULT NULL,
  `reference` varchar(255) DEFAULT NULL,
  `reset_password_key` varchar(20) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `avatar_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_olers6ibwn426rshksb9rh6m4` (`reference`),
  UNIQUE KEY `index_user_reference` (`reference`),
  KEY `FKprb1s4tehy8cqjvuler419omm` (`avatar_id`),
  CONSTRAINT `FKprb1s4tehy8cqjvuler419omm` FOREIGN KEY (`avatar_id`) REFERENCES `photo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Les données exportées n'étaient pas sélectionnées.
-- Export de la structure de la table mazad. user_authority
CREATE TABLE IF NOT EXISTS `user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_id`),
  KEY `FKgvxjs381k6f48d5d2yi11uh89` (`authority_id`),
  CONSTRAINT `FKgvxjs381k6f48d5d2yi11uh89` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`),
  CONSTRAINT `FKpqlsjpkybgos9w2svcri7j8xy` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DELETE FROM `authority`;
  INSERT INTO `authority` (`id`,`name`) VALUES
	(1,'ROLE_ADMIN'),
	(2,'ROLE_USER');
	/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
