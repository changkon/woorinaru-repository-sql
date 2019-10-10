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