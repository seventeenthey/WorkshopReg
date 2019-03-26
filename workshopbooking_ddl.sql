SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS PERSON;
DROP TABLE IF EXISTS ROLES;
DROP TABLE IF EXISTS DEPARTMENTS;
DROP TABLE IF EXISTS CATALOGUE;
DROP TABLE IF EXISTS REGISTRATIONS;
DROP TABLE IF EXISTS WAITLIST;
DROP TABLE IF EXISTS REVIEWS;
DROP TABLE IF EXISTS LOCATIONS;

CREATE TABLE PERSON (
	net_id VARCHAR(10) PRIMARY KEY,
	empl_id INTEGER,
	common_name VARCHAR(50),
	email VARCHAR(50),
    role_id INTEGER,
    department_id INTEGER
);

CREATE TABLE ROLES (
	role_id INTEGER PRIMARY KEY,
	role_name VARCHAR(20)
);

CREATE TABLE DEPARTMENTS (
	department_id INTEGER PRIMARY KEY,
    department_name VARCHAR(20)
);

CREATE TABLE CATALOGUE (
    workshop_id INTEGER AUTO_INCREMENT PRIMARY KEY,
    workshop_host_id VARCHAR(10),
    department_id INTEGER,
    title VARCHAR(100),
    details VARCHAR(500),
    location VARCHAR(200),
	max_participants INTEGER,
	current_participants INTEGER,
    start_time DATETIME,
	end_time DATETIME
);

CREATE TABLE REGISTRATIONS (
	workshop_id INTEGER,
    net_id VARCHAR(10),
    PRIMARY KEY(workshop_id, net_id)
);

CREATE TABLE WAITLIST (
	workshop_id INTEGER,
	net_id VARCHAR(10),
    PRIMARY KEY(workshop_id, net_id)
);

CREATE TABLE REVIEWS (
	workshop_id INTEGER,
	net_id VARCHAR(10),
	review VARCHAR(1000),
    PRIMARY KEY(workshop_id, net_id)
);

CREATE TABLE LOCATIONS (
	location_name VARCHAR(100) PRIMARY KEY
);

-- Constraints for table PERSON
ALTER TABLE PERSON ADD CONSTRAINT FOREIGN KEY (role_id) REFERENCES ROLES (role_id);
ALTER TABLE PERSON ADD CONSTRAINT FOREIGN KEY (department_id) REFERENCES DEPARTMENTS (department_id);

-- Constraints for table ROLES
ALTER TABLE ROLES ALTER role_id SET DEFAULT 0;

-- Constraints for table DEPARTMENTS
-- none

-- Constraints for table CATALOGUE
ALTER TABLE CATALOGUE ADD CONSTRAINT FOREIGN KEY (workshop_host_id) REFERENCES PERSON (net_id);
ALTER TABLE CATALOGUE ADD CONSTRAINT FOREIGN KEY (department_id) REFERENCES DEPARTMENTS (department_id);

-- Constraints for table REGISTRATIONS
ALTER TABLE REGISTRATIONS ADD CONSTRAINT FOREIGN KEY (workshop_id) REFERENCES CATALOGUE (workshop_id);
ALTER TABLE REGISTRATIONS ADD CONSTRAINT FOREIGN KEY (net_id) REFERENCES PERSON (net_id);

-- Constraints for table WAITLIST
ALTER TABLE WAITLIST ADD CONSTRAINT FOREIGN KEY (workshop_id) REFERENCES CATALOGUE (workshop_id);
ALTER TABLE WAITLIST ADD CONSTRAINT FOREIGN KEY (net_id) REFERENCES PERSON (net_id);

-- Constraints for table REVIEWS
ALTER TABLE REVIEWS ADD CONSTRAINT FOREIGN KEY (workshop_id) REFERENCES CATALOGUE (workshop_id);
ALTER TABLE REVIEWS ADD CONSTRAINT FOREIGN KEY (net_id) REFERENCES PERSON (net_id);

-- ADD ROLES
INSERT INTO ROLES VALUES (0, "Guest");
INSERT INTO ROLES VALUES (1, "Authenticated User");
INSERT INTO ROLES VALUES (2, "Workshop Host");
INSERT INTO ROLES VALUES (3, "Workshop Creator");
INSERT INTO ROLES VALUES (4, "Department Head");
INSERT INTO ROLES VALUES (5, "Administrator");

-- ADD DEPARTMENTS
INSERT INTO DEPARTMENTS VALUES (1, "Medical");
INSERT INTO DEPARTMENTS VALUES (2, "Engineering");
INSERT INTO DEPARTMENTS VALUES (3, "Arts & Science");

-- ADD LOCATIONS
INSERT INTO LOCATIONS VALUES ("Dunning Hall");
INSERT INTO LOCATIONS VALUES ("Walter Light Hall");
INSERT INTO LOCATIONS VALUES ("Goodes Hall");

-- ADD FAKE DATA
INSERT INTO PERSON VALUES ("15dny", 12345678, "Daniel K.", "5dny@queensu.ca", 1, 0);
INSERT INTO PERSON VALUES ("emmah", 57338531, "Emma H.", "emma_h@queensu.ca", 3, 2);
INSERT INTO CATALOGUE VALUES (NULL, 2, 2, "How to Stay Awake in Class", "Learn to stay focused!", "Walter Light Hall", 100, 5, '2019-02-05 10:00:00', '2019-02-05 11:30:00');
INSERT INTO CATALOGUE VALUES (NULL, 2, 2, "How to Study Effectively", "Learn how to study effectively!", "Dunning Hall", 100, 0, '2019-04-01 14:00:00', '2019-04-01 16:00:00');
INSERT INTO REVIEWS VALUES (1, "15dny", "Loved it!");
INSERT INTO REGISTRATIONS VALUES (2, "15dny");
