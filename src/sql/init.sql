CREATE DATABASE hkstock CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE hkstock.stock (
	symbol INT NOT NULL COMMENT 'javatype=int;',
	chineseName varchar(100) NOT NULL COMMENT 'javatype=String;',
	isHSI bit(0) NOT NULL DEFAULT b'0' COMMENT 'javatype=boolean;',
	PRIMARY KEY (symbol)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci ;

CREATE TABLE hkstock.record (
  rowId INT NOT NULL AUTO_INCREMENT COMMENT 'javatype=int;',
	symbol INT NOT NULL COMMENT 'javatype=int;',
	recordDt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'javatype=String;',
	value varchar(100) NOT NULL COMMENT 'javatype=String;',
	PRIMARY KEY (rowId)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci ;

CREATE TABLE hkstock.daily_record (
  rowId INT NOT NULL AUTO_INCREMENT COMMENT 'javatype=int;',
	symbol INT NOT NULL COMMENT 'javatype=int;',
	recordDt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'javatype=String;',
	closeValue varchar(100) NOT NULL COMMENT 'javatype=String;',
	lastValue varchar(100) NOT NULL COMMENT 'javatype=String',
	dayHighValue varchar(100) NOT NULL COMMENT 'javatype=String',
	dayLowValue varchar(100) NOT NULL COMMENT 'javatype=String',
	PRIMARY KEY (rowId)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_general_ci ;