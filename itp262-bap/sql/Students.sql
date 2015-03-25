DROP TABLE Students;
DROP TABLE StudentAddress;
DROP TABLE Admin;

CREATE TABLE Students(
	id		VARCHAR(5)	PRIMARY KEY,
	fName		VARCHAR(20)	NOT NULL,
	mInitial	CHAR(1),
	lName		VARCHAR(20)	NOT NULL,
	phoneNumber	INT		NOT NULL,
	
	password	VARCHAR(20)	NOT NULL,
); 

CREATE TABLE StudentAddress(
	id		VARCHAR(5)	PRIMARY KEY,
	streetAddress1	VARCHAR(40)	NOT NULL,
	streetAddress2	VARCHAR(10),
	city		VARCHAR(20)	NOT NULL,
	state		CHAR(2)		NOT NULL,
	zip		INT		NOT NULL
);

CREATE TABLE Admin(
	id		VARCHAR(10)	PRIMARY KEY,
	password	VARCHAR(30)
);

INSERT INTO Admin VALUES ('CLayton', 'trains');
INSERT INTO Admin VALUES ('BMaskrey', 'pathing');