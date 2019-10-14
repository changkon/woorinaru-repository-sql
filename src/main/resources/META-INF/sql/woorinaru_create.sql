-- Database script to initialise woorinaru schema
-- CREATE DATABASE IF NOT EXISTS `woorinaru`;
-- USE `woorinaru`;

-- Create User Table
CREATE TABLE IF NOT EXISTS `USER` (
	`ID` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `NAME` NVARCHAR(50),
    `NATIONALITY` VARCHAR(40),
    `EMAIL` VARCHAR(40),
    `STAFF_ROLE` VARCHAR(30),
    `SIGNUPDATE` TIMESTAMP,
    `TEAM` VARCHAR(30),
    `USER_TYPE` CHAR(1)
);

-- Create Resource Table
CREATE TABLE IF NOT EXISTS `RESOURCE` (
	`ID` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `RESOURCE` BLOB,
    `DESCRIPTION` NVARCHAR(75)
);

-- Create User Resource Table (Many to Many relationship)
CREATE TABLE IF NOT EXISTS `USER_RESOURCE` (
	`USER_ID` INT,
    `RESOURCE_ID` INT,
    FOREIGN KEY (`USER_ID`) REFERENCES USER(`ID`) ON DELETE CASCADE,
    FOREIGN KEY (`RESOURCE_ID`) REFERENCES RESOURCE(`ID`) ON DELETE CASCADE
);

-- WooriClass table
CREATE TABLE IF NOT EXISTS `WOORICLASS` (
    `ID` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `CLASS_TYPE` CHAR(1)
);

CREATE TABLE IF NOT EXISTS `WOORICLASS_RESOURCE` (
    `WOORICLASS_ID` INT,
    `RESOURCE_ID` INT,
    FOREIGN KEY (`WOORICLASS_ID`) REFERENCES WOORICLASS(`ID`) ON DELETE CASCADE,
    FOREIGN KEY (`RESOURCE_ID`) REFERENCES RESOURCE(`ID`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `WOORICLASS_STAFF` (
    `WOORICLASS_ID` INT,
    `STAFF_ID` INT,
    FOREIGN KEY (`WOORICLASS_ID`) REFERENCES WOORICLASS(`ID`) ON DELETE CASCADE,
    FOREIGN KEY (`STAFF_ID`) REFERENCES USER(`ID`) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `WOORICLASS_STUDENT` (
    `WOORICLASS_ID` INT,
    `STUDENT_ID` INT,
    FOREIGN KEY (`WOORICLASS_ID`) REFERENCES WOORICLASS(`ID`) ON DELETE CASCADE,
    FOREIGN KEY (`STUDENT_ID`) REFERENCES USER(`ID`) ON DELETE CASCADE
);