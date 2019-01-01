
/*  car_types.sql*/

INSERT into car_type (name)
values ("Hatchback");

INSERT into car_type (name)
values ("Sedan");

INSERT into car_type (name)
values ("MPV");

INSERT into car_type (name)
values ("SUV");

INSERT into car_type (name)
values ("Crossover");

INSERT into car_type (name)
values ("Coupe");

INSERT into car_type (name)
values ("Convertible");

/*  cars.sql  */


INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id )
values ("Ficko","Mercedes","Benz","13AC12",4,4,4,100,false,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id )
values ("Brzko","Mercedes","Benz","13QQ55",4,4,4,250,true,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id )
values ("Spora","Mercedes","C","44WE32",4,4,4,100,false,1);


/* users.sql */
/* password = 123*/

INSERT into Users (type, username,first_name, last_name, password, address, city, enabled, phone_number)
values (1,"admin@gmail.com","admin","adminovic","$2a$10$CItG2LPX2aHLCzm9LMud8e05QoLTM1rxEd/WdfZJKHAbE0miu23/e","adr2","city2",b'1',"123-456-1234");

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number)
values (0,"normal@gmail.com","pera","peric","$2a$10$KzK60SLUUU8UKH/rtkZsYuRjc.9D1G.HJgmDnRzJDVl6y3V4/X64u","adr1","city1",b'1',"123-456-1234");

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number)
values (0,"normala@gmail.com","normal","normalic","$2a$10$Zz/H94PBqMWVWQlRgCw6GORvl8pIxR8yll1UX/SIy6U7JVO0LF2OW","adr0","city0",b'1',"123-456-1234");

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number)
values (0,"hotel@gmail.com","hotel","hotelic","$2a$10$Zz/H94PBqMWVWQlRgCw6GORvl8pIxR8yll1UX/SIy6U7JVO0LF2OW","adr3","city3",b'1',"123-456-1234");

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number)
values (0,"airline@gmail.com","airline","airlinovic","$2a$10$Zz/H94PBqMWVWQlRgCw6GORvl8pIxR8yll1UX/SIy6U7JVO0LF2OW","adr4","city4",b'1',"123-456-1234");

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number)
values (0,"rent@gmail.com","rent","rentovic","$2a$10$Zz/H94PBqMWVWQlRgCw6GORvl8pIxR8yll1UX/SIy6U7JVO0LF2OW","adr5","city5",b'1',"123-456-1234");


/* authorities */
/*0 - Normal, 1 - Admin, 2 - Airline Admin, 3 - Hotel Admin, 4 - RentACar Admin */
/*1 ROLE_USER, 2 ROLE_ADMIN, 3 ROLE_ADMIN_AIRLINE, 4 ROLE_ADMIN_HOTEL, 5 ROLE_ADMIN_RENT     */

INSERT INTO AUTHORITY (name) VALUES ('ROLE_USER');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMIN');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMIN_AIRLINE');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMIN_HOTEL');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMIN_RENT');

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 2);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (3, 1);

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (4, 4);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (5, 3);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (6, 5);

/*companies.sql*/

INSERT INTO airlines (name, address, description)
VALUES ('AirSerbia', 'Nikola Tesle 21 , Beograd', 'Aeroport Nikole Tesle Belgrade');

INSERT INTO airlines (name, address, description)
VALUES ('AirMontenegro', 'Sarajevos 8, Sarajevo', 'Aeroport Saint Sarevos');

INSERT INTO hotels (name, address, description)
VALUES ('Aleksandar', 'Bulevar Oslobodjenja, Novi Sad', 'Hotel with 5 stars in Novi Sad with great locations');

INSERT INTO hotels (name, address, description)
VALUES ('Putnik', 'Trg Trifuna Miltuića, Novi Sad', 'Cheap rooms ,Hotel with 3 stars near the center');


INSERT INTO ratings(user_rating,user_id,type,hotel_id)
VALUES (3,1,3,1);


INSERT INTO locations (address_name,city,country,latitude,longitude)
VALUES ("Buto Musilini 2, Venecia", "Venecia", "Italy", 12.144123 , 15.1414 );

INSERT INTO locations (address_name,city,country,latitude,longitude)
VALUES ("Albara Musafa 2, Milano", "Milano", "Italy", 42.144123 , 11.155 );


INSERT INTO rentacar (name, address_id, description,fast_discount)
VALUES ('RentLjubomir',2, 'Profesional cars with state of the art quaility',20);

INSERT INTO rentacar (name,address_id, description,fast_discount)
VALUES ('RentAleksandar', 1, 'Cheap cars that will take you where you need to be.',20);

