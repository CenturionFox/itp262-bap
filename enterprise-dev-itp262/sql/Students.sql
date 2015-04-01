/* Drop tables */

DROP TABLE Students 		CASCADE;
DROP TABLE StudentAddress	CASCADE;
DROP TABLE StudentClasses	CASCADE;
DROP TABLE Rooms 			CASCADE;
DROP TABLE UnavailableRooms CASCADE;
DROP TABLE Classes 			CASCADE;
DROP TABLE ScheduledClasses	CASCADE;
DROP TABLE Admin			CASCADE;

/* Begin Student data section */

/* Basic Student Information Table */
CREATE TABLE Students
(
	studentID	VARCHAR(5)	PRIMARY KEY,
	fName		VARCHAR(20)	NOT NULL,	/* First name */
	mInitial	CHAR(1),				/* Middle Initial */
	lName		VARCHAR(20)	NOT NULL,	/* Last name */
	phoneNumber	CHAR(10) 	NOT NULL,	
	
	password	CHAR(64)	NOT NULL
); 

/* Student Address Information Table */
CREATE TABLE StudentAddress
(
	studentID	VARCHAR(5)	PRIMARY KEY,
	streetAddress1	VARCHAR(40)	NOT NULL,
	streetAddress2	VARCHAR(20),
	city		VARCHAR(20)	NOT NULL,
	state		CHAR(2)		NOT NULL,
	zip		INT		NOT NULL,
	FOREIGN KEY (studentID) REFERENCES Students(studentID)
);

/* End Student Data Section */

/* Begin class data section */

/* Table that stores classes the student has signed up for */
CREATE TABLE StudentClasses
(
	studentID	VARCHAR(5),
	classID		VARCHAR(7),
	startDate	DATE,
	PRIMARY KEY (studentID, classID),
	FOREIGN KEY (studentID) REFERENCES Students(studentID)
);

/* All rooms and room data */
CREATE TABLE Rooms
(
	roomNumber	VARCHAR(7)	PRIMARY KEY,	/* Room # (Library in that case) */
	type		VARCHAR(5),					/* Type of room ie(class, lab, instr'uction lab, libra'ry) */
	seats		INT,						/* # of seats */
	projection	BOOLEAN,					/* Does it have a projector? */
	smartBoard	BOOLEAN,					/* Have a SmartBoard? */
	programming	BOOLEAN,					/* Programming software? */
	gaming		BOOLEAN,					/* Gaming software? */
	multimedia	BOOLEAN,					/* Multimedia software? */
	cad		BOOLEAN							/* CAD software? */
);

/* Table contains a list of rooms that are unavailable during the specified quarter */
CREATE TABLE UnavailableRooms
(
	roomNumber	VARCHAR(7),
	startDate	DATE,										/* reference to ScheduledClasses.startDate */
	PRIMARY KEY (roomNumber, startDate),
	FOREIGN KEY (roomNumber) REFERENCES Rooms(roomNumber)
);

/* Available Classes */
CREATE TABLE Classes(
	classID		CHAR(6)		PRIMARY KEY,
	type		VARCHAR(20),				/* Type of class ie (Programming, CAD, etc) */
	name		VARCHAR(40),				/* Title of the class */
	hoursClass	INT,						/* Hours required in a classroom */
	hoursInstruct	INT,					/* Hours required in an instructional lab */
	hoursLab	INT,						/* Hours required in a computer lab */
	hoursLibrary	INT						/* Hours required in the library */
);

CREATE TABLE classroomListing
{
	roomNumber	VARCHAR(7),
	classID		CHAR(6),
	classStart	TIMESTAMP,
	classEnd	TIMESTAMP,
	FOREIGN KEY (roomNumber) REFERENCES Rooms(roomNumber),
	FOREIGN KEY (classID) REFERENCES Classes(classID)
};

/* Class / available date association table. Date determines when this class may be scheduled. */
CREATE TABLE ScheduledClasses(
	classID		CHAR(6),
	startDate	DATE,
	PRIMARY KEY (classID, startDate),
	FOREIGN KEY (classID) REFERENCES Classes(classID)
);

ALTER TABLE UnavailableRooms ADD FOREIGN KEY(startDate) REFERENCES ScheduledClasses(startDate);

/* End Class Data Section */

/* Admin user table */
CREATE TABLE Admin(
	adminID		VARCHAR(10)	PRIMARY KEY,
	name		VARCHAR(20)	NOT NULL,
	password	VARCHAR(30)	NOT NULL
);

/* DUMMY INFORMATION FOLLOWS: Delete For Production Build */

/* Inserting the room information into the dB */
INSERT INTO Rooms VALUES ('106A', 'lab', 25, false, false, true, false, true, true);
INSERT INTO Rooms VALUES ('106B', 'lab', 25, false, false, true, false, true, true);
INSERT INTO Rooms VALUES ('106C', 'lab', 25, false, false, true, false, true, true);
INSERT INTO Rooms VALUES ('501', 'class', 30, true, true, false, false, false, false);
INSERT INTO Rooms VALUES ('502', 'class', 30, true, true, false, false, false, false);
INSERT INTO Rooms VALUES ('604', 'instr', 35, true, true, true, false, false, true);
INSERT INTO Rooms VALUES ('605', 'instr', 35, true, true, true, true, false, true);
INSERT INTO Rooms VALUES ('611', 'instr', 20, true, true, false, false, true, true);
INSERT INTO Rooms VALUES ('Library', 'libra', 20, false, false, false, false, false, false);

/* Inserting the class information into the dB */
INSERT INTO Classes VALUES ('SSD551', 'Programming', 'Data Structures and Algorithms 1', 0, 5, 3, 0);
INSERT INTO Classes VALUES ('SSD750', 'Programming', 'Database Systems', 0, 4, 6, 0);
INSERT INTO Classes VALUES ('ITP201', 'Programming', 'Project Management', 2, 0, 2, 0);
INSERT INTO Classes VALUES ('ITP300', 'Programming', 'Introduction to Game Design', 0, 0, 6, 0);

INSERT INTO Classes VALUES ('MMA113', 'Multimedia', 'Introduction to Multimedia', 0, 3, 2, 0);
INSERT INTO Classes VALUES ('MMA131', 'Multimedia', '3-Dimensional Modeling and Rendering', 0, 5, 3, 0);
INSERT INTO Classes VALUES ('MMA144', 'Multimedia', 'Multimedia Delivery For the Web', 0, 2, 2, 0);
INSERT INTO Classes VALUES ('MMA280', 'Multimedia', 'Multimedia Portfolio Development', 0, 2, 3, 3);

INSERT INTO Classes VALUES ('CAD120', 'CAD', 'Introduction to Mechanical Drafting', 0, 3, 2, 0);
INSERT INTO Classes VALUES ('CAD125', 'CAD', 'Building Materials', 0, 3, 2, 0);
INSERT INTO Classes VALUES ('CAD131', 'CAD', 'Residential Drafting', 0, 3, 2, 0);
INSERT INTO Classes VALUES ('CAD145', 'CAD', '3-Dimensional CAD', 0, 3, 2, 0);

INSERT INTO Classes VALUES ('GES100', 'General Education', 'Psychology', 2, 0, 2, 0);
INSERT INTO Classes VALUES ('GEH120', 'General Education', 'Art History', 2, 0, 2, 0);
INSERT INTO Classes VALUES ('GEE150', 'General Education', 'English Composition 2', 2, 0, 2, 0);
INSERT INTO Classes VALUES ('GEE211', 'General Education', 'Effective Speech', 3, 0, 2, 0);

INSERT INTO ScheduledClasses VALUES ('SSD551', '04/15/2015');
INSERT INTO ScheduledClasses VALUES ('SSD750', '04/15/2015');
INSERT INTO ScheduledClasses VALUES ('ITP201', '04/15/2015');
INSERT INTO ScheduledClasses VALUES ('ITP300', '04/15/2015');

INSERT INTO ScheduledClasses VALUES ('MMA113', '04/15/2015');
INSERT INTO ScheduledClasses VALUES ('MMA131', '04/15/2015');
INSERT INTO ScheduledClasses VALUES ('MMA144', '04/15/2015');
INSERT INTO ScheduledClasses VALUES ('MMA280', '04/15/2015');

INSERT INTO ScheduledClasses VALUES ('CAD120', '04/15/2015');
INSERT INTO ScheduledClasses VALUES ('CAD125', '04/15/2015');
INSERT INTO ScheduledClasses VALUES ('CAD131', '04/15/2015');
INSERT INTO ScheduledClasses VALUES ('CAD145', '04/15/2015');

INSERT INTO ScheduledClasses VALUES ('GES100', '04/15/2015');
INSERT INTO ScheduledClasses VALUES ('GEH120', '04/15/2015');
INSERT INTO ScheduledClasses VALUES ('GEE150', '04/15/2015');
INSERT INTO ScheduledClasses VALUES ('GEE211', '04/15/2015');

INSERT INTO UnavailableRooms VALUES ('106A', '04/15/2015');

INSERT INTO Admin VALUES ('', 'Guest', '');
INSERT INTO Admin VALUES ('CLayton', 'Mr. Layton', 'trains');
INSERT INTO Admin VALUES ('BMaskrey', 'Mr. Maskrey', 'pathing');