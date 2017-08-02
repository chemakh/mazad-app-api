-- --------------------------------------------------------
-- Hôte :                        127.0.0.1
-- Version du serveur:           5.7.17-log - MySQL Community Server (GPL)
-- SE du serveur:                Win64
-- HeidiSQL Version:             9.4.0.5174
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;

-- Export de la structure de la table mazad. address
CREATE TABLE IF NOT EXISTS `address` (
  `id`              BIGINT(20) NOT NULL AUTO_INCREMENT,
  `city`            VARCHAR(255)        DEFAULT NULL,
  `country`         VARCHAR(255)        DEFAULT NULL,
  `lat`             DOUBLE     NOT NULL,
  `lon`             DOUBLE     NOT NULL,
  `postal_code`     VARCHAR(255)        DEFAULT NULL,
  `reference`       VARCHAR(255)        DEFAULT NULL,
  `region`          VARCHAR(255)        DEFAULT NULL,
  `street_address1` VARCHAR(255)        DEFAULT NULL,
  `street_address2` VARCHAR(255)        DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gd5xjv8srxbnnqtuv9wjvrjva` (`reference`),
  UNIQUE KEY `index_address_reference` (`reference`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 27
  DEFAULT CHARSET = utf8;

-- Les données exportées n'étaient pas sélectionnées.
-- Export de la structure de la table mazad. article
CREATE TABLE IF NOT EXISTS `article` (
  `id`                BIGINT(20) NOT NULL AUTO_INCREMENT,
  `creation_date`     DATETIME            DEFAULT NULL,
  `current_price`     DECIMAL(19, 2)      DEFAULT NULL,
  `description`       VARCHAR(255)        DEFAULT NULL,
  `label`             VARCHAR(255)        DEFAULT NULL,
  `reference`         VARCHAR(255)        DEFAULT NULL,
  `sold`              BIT(1)     NOT NULL,
  `avatar_id`         BIGINT(20)          DEFAULT NULL,
  `deleted`           BIT(1)     NOT NULL,
  `deletion_date`     DATETIME            DEFAULT NULL,
  `category_id`       BIGINT(20)          DEFAULT NULL,
  `user_id`           BIGINT(20) NOT NULL,
  `bid_amount`        DECIMAL(19, 2)      DEFAULT NULL,
  `buy_it_now_price`  DECIMAL(19, 2)      DEFAULT NULL,
  `starting_price`    DECIMAL(19, 2)      DEFAULT NULL,
  `valid`             BIT(1)     NOT NULL,
  `validity_duration` INT(11)             DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rti6xu203k7hfohpu89214w6c` (`reference`),
  UNIQUE KEY `index_article_reference` (`reference`),
  KEY `FK4x9vnu2o62gw3yqrb692wtg9m` (`avatar_id`),
  KEY `FKy5kkohbk00g0w88fi05k2hio` (`user_id`),
  KEY `FKy5kkohbk00g0w88fi05k2hcw` (`category_id`),
  CONSTRAINT `FK4x9vnu2o62gw3yqrb692wtg9m` FOREIGN KEY (`avatar_id`) REFERENCES `photo` (`id`)
    ON DELETE SET NULL,
  CONSTRAINT `FKy5kkohbk00g0w88fi05k2hcw` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `FKy5kkohbk00g0w88fi05k2hio` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 16
  DEFAULT CHARSET = utf8;

-- Les données exportées n'étaient pas sélectionnées.
-- Export de la structure de la table mazad. authority
CREATE TABLE IF NOT EXISTS `authority` (
  `id`   BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50)         DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8;

-- Les données exportées n'étaient pas sélectionnées.
-- Export de la structure de la table mazad. bid
CREATE TABLE IF NOT EXISTS `bid` (
  `id`            BIGINT(20) NOT NULL AUTO_INCREMENT,
  `bid_amount`    DECIMAL(19, 2)      DEFAULT NULL,
  `reference`     VARCHAR(255)        DEFAULT NULL,
  `article_id`    BIGINT(20)          DEFAULT NULL,
  `user_id`       BIGINT(20) NOT NULL,
  `creation_date` DATETIME            DEFAULT NULL,
  `actual_bid`    DECIMAL(19, 2)      DEFAULT NULL,
  `old_bid`       DECIMAL(19, 2)      DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pskinv949oxyndma0di6xvh40` (`reference`),
  UNIQUE KEY `index_bid_reference` (`reference`),
  KEY `FK2lbh9otawlroajeyo15q0dd81` (`article_id`),
  KEY `FK4abkntgv9nvsfi86p7kfl63au` (`user_id`),
  CONSTRAINT `FK2lbh9otawlroajeyo15q0dd81` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `FK4abkntgv9nvsfi86p7kfl63au` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8;

-- Les données exportées n'étaient pas sélectionnées.
-- Export de la structure de la table mazad. category
CREATE TABLE IF NOT EXISTS `category` (
  `id`        BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name`      VARCHAR(50)         DEFAULT NULL,
  `reference` VARCHAR(255)        DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9pex31vexfd5ian5g0ymemdqu` (`reference`),
  UNIQUE KEY `index_category_reference` (`reference`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8;


-- Les données exportées n'étaient pas sélectionnées.
-- Export de la structure de la table mazad. photo
CREATE TABLE IF NOT EXISTS `photo` (
  `id`         BIGINT(20) NOT NULL AUTO_INCREMENT,
  `label`      VARCHAR(255)        DEFAULT NULL,
  `name`       VARCHAR(255)        DEFAULT NULL,
  `reference`  VARCHAR(255)        DEFAULT NULL,
  `article_id` BIGINT(20)          DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_mtuo907255q2sc866tm1kt5ao` (`reference`),
  UNIQUE KEY `index_photo_reference` (`reference`),
  KEY `FKrm4wnmb1hcwhxvrmf2x6gf9ox` (`article_id`),
  CONSTRAINT `FKrm4wnmb1hcwhxvrmf2x6gf9ox` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 76
  DEFAULT CHARSET = utf8;

-- Les données exportées n'étaient pas sélectionnées.
-- Export de la structure de la table mazad. sale
CREATE TABLE IF NOT EXISTS `sale` (
  `id`         BIGINT(20) NOT NULL AUTO_INCREMENT,
  `bid`        TINYBLOB,
  `reference`  VARCHAR(255)        DEFAULT NULL,
  `sold_by`    TINYBLOB,
  `sold_date`  DATETIME            DEFAULT NULL,
  `sold_price` DECIMAL(19, 2)      DEFAULT NULL,
  `article_id` BIGINT(20)          DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_g36meeytxqihj8xyvwlalm0jw` (`reference`),
  UNIQUE KEY `index_sale_reference` (`reference`),
  KEY `FKmcu3pp05qnalysdc7a202ls8o` (`article_id`),
  CONSTRAINT `FKmcu3pp05qnalysdc7a202ls8o` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Les données exportées n'étaient pas sélectionnées.
-- Export de la structure de la table mazad. user
CREATE TABLE IF NOT EXISTS `user` (
  `id`                 BIGINT(20) NOT NULL AUTO_INCREMENT,
  `activated`          BIT(1)     NOT NULL,
  `deleted`            BIT(1)     NOT NULL DEFAULT b'0',
  `deletion_date`      DATETIME            DEFAULT NULL,
  `email`              VARCHAR(255)        DEFAULT NULL,
  `email_key`          VARCHAR(20)         DEFAULT NULL,
  `firstname`          VARCHAR(255)        DEFAULT NULL,
  `is_mail_verified`   TINYINT(1)          DEFAULT '0',
  `lang_key`           VARCHAR(5)          DEFAULT NULL,
  `lastname`           VARCHAR(255)        DEFAULT NULL,
  `password`           VARCHAR(60)         DEFAULT NULL,
  `reference`          VARCHAR(255)        DEFAULT NULL,
  `reset_password_key` VARCHAR(20)         DEFAULT NULL,
  `sex`                VARCHAR(255)        DEFAULT NULL,
  `address_id`         BIGINT(20)          DEFAULT NULL,
  `avatar_id`          BIGINT(20)          DEFAULT NULL,
  `mobile_number`      VARCHAR(255)        DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_olers6ibwn426rshksb9rh6m4` (`reference`),
  UNIQUE KEY `index_user_reference` (`reference`),
  KEY `FKddefmvbrws3hvl5t0hnnsv8ox` (`address_id`),
  KEY `FKprb1s4tehy8cqjvuler419omm` (`avatar_id`),
  CONSTRAINT `FKddefmvbrws3hvl5t0hnnsv8ox` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `FKprb1s4tehy8cqjvuler419omm` FOREIGN KEY (`avatar_id`) REFERENCES `photo` (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 19
  DEFAULT CHARSET = utf8;

-- Les données exportées n'étaient pas sélectionnées.
-- Export de la structure de la table mazad. user_authority
CREATE TABLE IF NOT EXISTS `user_authority` (
  `user_id`      BIGINT(20) NOT NULL,
  `authority_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`user_id`, `authority_id`),
  KEY `FKgvxjs381k6f48d5d2yi11uh89` (`authority_id`),
  CONSTRAINT `FKgvxjs381k6f48d5d2yi11uh89` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`),
  CONSTRAINT `FKpqlsjpkybgos9w2svcri7j8xy` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- Les données exportées n'étaient pas sélectionnées.
/*!40101 SET SQL_MODE = IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS = IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
