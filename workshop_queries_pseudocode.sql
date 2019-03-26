--some_xyz is placeholder for whatever actual variable will be

--all workshops they can view (ones with completed info)
SELECT * FROM CATALOGUE;


--workshops on specific date
SELECT * FROM CATALOGUE WHERE CATALOGUE.start_time = some_date;


--workshop under specific department
SELECT * FROM CATALOGUE WHERE CATALOGUE.department_id = some_department_id;

--Workshop Host +

--create workshop
INSERT INTO CATALOGUE VALUE ('some_workshop_id', 'some_host_id', 'some_department_id', 'some_title', 'some_details', 'some_location', 'some_start_time', 'some_end_time');

--delete workshop
DELETE FROM CATALOGUE WHERE CATALOGUE.workshop_id = workshop_id;

--modify workshop info
--modify title
UPDATE CATALOGUE
SET title = 'some_new_title'
WHERE workshop_id = some_workshop_id;

--modify details
UPDATE CATALOGUE
SET details = 'some_new_details'
WHERE workshop_id = some_workshop_id;

--modify location
UPDATE CATALOGUE
SET location = 'some_new_location'
WHERE workshop_id = some_workshop_id;

--modify time
UPDATE CATALOGUE
SET start_time = 'some_new_title', end_time = 'some_end_time'
WHERE workshop_id = some_workshop_id;

-- Add other user to workshop
INSERT INTO REGISTRATIONS VALUE('some_workshop_id', 'some_net_id');

-- all users registered in a workshop
SELECT net_id FROM REGISTRATIONS WHERE REGISTRATIONS.workshop_id = some_workshop_id;

-- Department Head +

--Return all workshops in their department
SELECT * FROM CATALOGUE WHERE CATALOGUE.department_id = some_person.department_id;

--assign host to a workshop
UPDATE CATALOGUE
SET workshop_host_id = some_host_net_id
WHERE workshop_id = some_workshop_id;

-- remove a user from a workshop
DELETE FROM REGISTRATIONS WHERE net_id = some_net_id;

-- copy workshop info to another time, maybe a better way to do this?
INSERT INTO CATALOGUE VALUE ('some_workshop_id', 'some_host_id', 'some_department_id', 'some_title', 'some_details', 'some_location', 'some_start_time', 'some_end_time');
UPDATE CATALOGUE
SET start_time = 'some_new_title', end_time = 'some_end_time'
WHERE workshop_id = some_workshop_id;

--admin only

-- modify role
UPDATE PERSON
SET role_id = some_role_id
WHERE net_id = some_net_id;

-- see all workshops
SELECT * FROM CATALOGUE;

--get all workshops, specific department
SELECT * FROM CATALOGUE WHERE CATALOGUE.department_id = some_department_id;

--get all workshops specific time
SELECT * FROM CATALOGUE WHERE CATALOGUE.start_time = some_start_time;

--get all users
SELECT * FROM PERSON;

--list of all hosts
SELECT workshop_host_id FROM CATALOGUE;

-- list of all department heads
SELECT * FROM PERSON WHERE PERSON.role_id = id_for_department_head_here;



