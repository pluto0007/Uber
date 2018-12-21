/**
 * CREATE Script for init of DB
 */

-- Create 3 OFFLINE drivers

insert into driver (id, date_created, deleted, online_status, password, username) values (1, now(), false, 'OFFLINE',
'driver01pw', 'driver01');

insert into driver (id, date_created, deleted, online_status, password, username) values (2, now(), false, 'OFFLINE',
'driver02pw', 'driver02');

insert into driver (id, date_created, deleted, online_status, password, username) values (3, now(), false, 'OFFLINE',
'driver03pw', 'driver03');


-- Create 3 ONLINE drivers

insert into driver (id, date_created, deleted, online_status, password, username) values (4, now(), false, 'ONLINE',
'driver04pw', 'driver04');

insert into driver (id, date_created, deleted, online_status, password, username) values (5, now(), false, 'ONLINE',
'driver05pw', 'driver05');

insert into driver (id, date_created, deleted, online_status, password, username) values (6, now(), false, 'ONLINE',
'driver06pw', 'driver06');

-- Create 1 OFFLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username)
values
 (7,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'OFFLINE',
'driver07pw', 'driver07');

-- Create 1 ONLINE driver with coordinate(longitude=9.5&latitude=55.954)

insert into driver (id, coordinate, date_coordinate_updated, date_created, deleted, online_status, password, username)
values
 (8,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'ONLINE',
'driver08pw', 'driver08');

insert into car(id, license_plate, seat_count, convertible, rating, engine_type, manufacturer, selected) values(1, 'DL9CAP1871', 4, true, 4,'GAS','audi',false);

insert into car(id, license_plate, seat_count, convertible, rating, engine_type, manufacturer, selected) values(2, 'DL9CAP1872', 6, false, 1,'ELECTRIC','suzuki',false);

insert into car(id, license_plate, seat_count, convertible, rating, engine_type, manufacturer, selected) values(3, 'DL9CAP1873', 6, true, 3,'GAS','audi',false);

insert into car(id, license_plate, seat_count, convertible, rating, engine_type, manufacturer, selected) values(4, 'DL9CAP1874', 4, false, 5,'ELECTRIC','bmw',false);


INSERT INTO role (id, description, name) VALUES (4, 'Admin role', 'ADMIN');
INSERT INTO role (id, description, name) VALUES (5, 'User role', 'USER');

INSERT INTO DRIVER_ROLES (DRIVER_ID, ROLE_ID) VALUES (1, 4);
INSERT INTO DRIVER_ROLES (DRIVER_ID,ROLE_ID) VALUES (2, 5);
INSERT INTO DRIVER_ROLES (DRIVER_ID,ROLE_ID) VALUES (3, 5);
INSERT INTO DRIVER_ROLES (DRIVER_ID,ROLE_ID) VALUES (4, 5);
INSERT INTO DRIVER_ROLES (DRIVER_ID,ROLE_ID) VALUES (5, 5);
INSERT INTO DRIVER_ROLES (DRIVER_ID,ROLE_ID) VALUES (6, 5);
INSERT INTO DRIVER_ROLES (DRIVER_ID,ROLE_ID) VALUES (7, 5);
INSERT INTO DRIVER_ROLES (DRIVER_ID,ROLE_ID) VALUES (8, 5);