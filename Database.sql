-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema handel
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema handel
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `handel` DEFAULT CHARACTER SET swe7 ;
USE `handel` ;

-- -----------------------------------------------------
-- Table `handel`.`plats`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `handel`.`plats` (
  `PlatsId` INT NOT NULL AUTO_INCREMENT,
  `PlatsNamn` CHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`PlatsId`))
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `handel`.`affar`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `handel`.`affar` (
  `AffarId` INT NOT NULL AUTO_INCREMENT,
  `AffarNamn` CHAR(255) NULL DEFAULT NULL,
  `AffarPlats` INT NOT NULL,
  PRIMARY KEY (`AffarId`),
  INDEX `AffarPlats` (`AffarPlats` ASC) VISIBLE,
  CONSTRAINT `affar_ibfk_1`
    FOREIGN KEY (`AffarPlats`)
    REFERENCES `handel`.`plats` (`PlatsId`))
ENGINE = InnoDB
AUTO_INCREMENT = 13
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `handel`.`datum`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `handel`.`datum` (
  `DatumId` INT NOT NULL AUTO_INCREMENT,
  `Datum` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`DatumId`),
  UNIQUE INDEX `Datum` (`Datum` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `handel`.`handel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `handel`.`handel` (
  `HandelId` INT NOT NULL AUTO_INCREMENT,
  `HandelAffarId` INT NULL DEFAULT NULL,
  `HandelDatumId` INT NULL DEFAULT NULL,
  `Kostnad` INT NULL DEFAULT NULL,
  `Tid` TIME NULL DEFAULT NULL,
  PRIMARY KEY (`HandelId`),
  INDEX `HandelAffarId` (`HandelAffarId` ASC) VISIBLE,
  INDEX `HandelDatumId` (`HandelDatumId` ASC) VISIBLE,
  CONSTRAINT `handel_ibfk_1`
    FOREIGN KEY (`HandelAffarId`)
    REFERENCES `handel`.`affar` (`AffarId`),
  CONSTRAINT `handel_ibfk_2`
    FOREIGN KEY (`HandelDatumId`)
    REFERENCES `handel`.`datum` (`DatumId`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = swe7;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
