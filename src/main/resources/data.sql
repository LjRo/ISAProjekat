/* Locations */

INSERT INTO locations (address_name,city,country,latitude,longitude,active) /*1*/
VALUES ('Buto Musilini 2, Venecia', 'Venecia', 'Italy', 12.144123 , 15.1414 ,true);

INSERT INTO locations (address_name,city,country,latitude,longitude, active) /*2*/
VALUES ('Albara Musafa 2, Milano', 'Milano', 'Italy', 42.144123 , 11.155 , true);


INSERT INTO locations (address_name,city,country,latitude,longitude) /*3*/
VALUES ('Maksima Gorkog 35', 'Milano', 'Italiy', 45.4642 , 9.1900 );

INSERT INTO locations (address_name,city,country,latitude,longitude) /*4*/
VALUES ('Boulevard Tsara Lazara 1', 'Novi Sad', 'Serbia', 45.247637 , 19.850474);

INSERT INTO locations (address_name,city,country,latitude,longitude) /*5*/
VALUES ('Nikola Tesle 21', 'Beograd', 'Serbia', 44.49153 ,  20.17314);

INSERT INTO locations (address_name,city,country,latitude,longitude) /*6*/
VALUES ('Kurta Schorka 36', 'Sarajevo', 'BiH', 43.49303  , 18.20074);


/*  car_types.sql*/

INSERT into car_type (name)
values ('Hatchback');

INSERT into car_type (name)
values ('Sedan');

INSERT into car_type (name)
values ('MPV');

INSERT into car_type (name)
values ('SUV');

INSERT into car_type (name)
values ('Crossover');

INSERT into car_type (name)
values ('Coupe');

INSERT into car_type (name)
values ('Convertible');

/* rent a car*/


INSERT INTO rentacar (name, address_id, description,fast_discount)
VALUES ('RentLjubomir',2, 'Profesional cars with state of the art quaility',20);

INSERT INTO rentacar (name,address_id, description,fast_discount)
VALUES ('RentAleksandar', 1, 'Cheap cars that will take you where you need to be.',20);

/* rent office*/

INSERT INTO rent_office (name,location_id,rentacar_id)
VALUES ('Prva filijala',1,1);

INSERT INTO rent_office (name,location_id,rentacar_id)
VALUES ('Druga filijala',2,1);

INSERT INTO rent_office (name,location_id,rentacar_id)
VALUES ('QQ filijala',3,2);

INSERT INTO rent_office (name,location_id,rentacar_id)
VALUES ('WW filijala',4,2);


INSERT INTO airlines (name, address_id, description, has_food, has_extra_luggage, has_other_services, food_price, luggage_price)
VALUES ('AirSerbia',5, 'Aeroport Nikole Tesle Belgrade', true, true, false, 50, 100);

INSERT INTO airlines (name, address_id, description, has_food, has_extra_luggage, has_other_services, food_price, luggage_price)
VALUES ('AirMontenegro', 6, 'Aeroport Saint Sarevos', false, false, true, null, null);


INSERT INTO hotels (name, address_id, description,fast_discount)
VALUES ('Aleksandar', 3, 'Hotel with 5 stars in Milano with great locations',15);

INSERT INTO hotels (name, address_id, description,fast_discount)
VALUES ('Putnik', 4 , 'Cheap rooms ,Hotel with 3 stars near the center',15);

/*
INSERT INTO rentacar_rent_offices (rentacar_id,rent_offices_id)
VALUES (1,1);

INSERT INTO rentacar_rent_offices (rentacar_id,rent_offices_id)
VALUES (1,2);*/

/*  cars.sql  */


INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ('Ficko','Mercedes','Benz','13AC42',4,4,4,100,false,1,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ('Brzko','Mercedes','Benz','13QQ65',4,4,4,250,true,1,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ('Spora','Mercedes','C','44WE72',4,4,4,100,false,1,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ('FickoQ','Mercedes','Benz','13AC12',4,4,4,100,false,1,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ('BrzkoQ','Mercedes','Benz','13QQ55',4,4,4,250,true,1,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ('SporaQ','Mercedes','C','44WE32',4,4,4,100,false,1,1);


INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ('FickoQW','Mercedes','Benz','13AC72',4,4,4,100,false,1,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ('BrzkoQW','Mercedes','Benz','13QQ85',4,4,4,250,true,1,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ('SporaQW','Mercedes','C','44WE92',4,4,4,100,false,1,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ('FickoQWE','Mercedes','Benz','11AC12',4,4,4,100,false,1,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ('BrzkoQWE','Mercedes','Benz','11QQ55',4,4,4,250,true,1,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ('SporaQWE','Mercedes','C','41WE32',4,4,4,100,false,1,1);


INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ('EW','Mercedes','C','45WE32',4,4,4,100,false,2,2);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ('WE','Mercedes','C','46WE32',4,4,4,100,false,2,1);

/* users.sql */
/* password = 123*/

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number,points)
values (1,'admin@gmail.com','admin','adminovic','$2a$10$CItG2LPX2aHLCzm9LMud8e05QoLTM1rxEd/WdfZJKHAbE0miu23/e','adr2','city2',true,'123-456-1234',0);

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number,points)
values (0,'normal@gmail.com','pera','peric','$2a$10$KzK60SLUUU8UKH/rtkZsYuRjc.9D1G.HJgmDnRzJDVl6y3V4/X64u','adr1','city1',true,'123-456-1234',0);

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number,points)
values (0,'normala@gmail.com','normal','normalic','$2a$10$Zz/H94PBqMWVWQlRgCw6GORvl8pIxR8yll1UX/SIy6U7JVO0LF2OW','adr0','city0',true,'123-456-1234',0);

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number,administrated_hotel_id,password_changed)
values (3,'hotel@gmail.com','hotel','hotelic','$2a$10$Zz/H94PBqMWVWQlRgCw6GORvl8pIxR8yll1UX/SIy6U7JVO0LF2OW','adr3','city3',true,'123-456-1234',1,true);

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number,administrated_airline_id,password_changed)
values (2,'airline@gmail.com','airline','airlinovic','$2a$10$Zz/H94PBqMWVWQlRgCw6GORvl8pIxR8yll1UX/SIy6U7JVO0LF2OW','adr4','city4',true,'123-456-1234',1,true);

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number, administrated_rent_id,password_changed)
values (4,'rent@gmail.com','rent','rentovic','$2a$10$Zz/H94PBqMWVWQlRgCw6GORvl8pIxR8yll1UX/SIy6U7JVO0LF2OW','adr5','city5',true,'123-456-1234', 1,true);

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number,points)
values (0,'normalb@gmail.com','Mika','Mikic','$2a$10$Zz/H94PBqMWVWQlRgCw6GORvl8pIxR8yll1UX/SIy6U7JVO0LF2OW','adr0','city0',true,'123-456-1234',0);

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number,points)
values (0,'normalc@gmail.com','Neko','Nekic','$2a$10$Zz/H94PBqMWVWQlRgCw6GORvl8pIxR8yll1UX/SIy6U7JVO0LF2OW','adr0','city0',true,'123-456-1234',0);


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
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (7, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (8, 1);


INSERT INTO room_type (name)
VALUES ('Single Beds');

INSERT INTO room_type (name)
VALUES ('Couple DoubleBed + 2 Single beds');

INSERT INTO room_type (name)
VALUES ('Couple DoubleBed + Single bed');

INSERT INTO room_type (name)
VALUES ('2 Couple DoubleBeds');

INSERT INTO room_type (name)
VALUES ('2 Couple DoubleBeds + Single bed');

INSERT INTO room_type (name)
VALUES ('Deluxe room');

INSERT INTO room_type (name)
VALUES ('Single Bed');

INSERT INTO hotels_room_types(hotel_id,room_types_id)
VALUES (2,7);

INSERT INTO hotels_room_types(hotel_id,room_types_id)
VALUES (1,1);
INSERT INTO hotels_room_types(hotel_id,room_types_id)
VALUES (1,2);
INSERT INTO hotels_room_types(hotel_id,room_types_id)
VALUES (1,3);
INSERT INTO hotels_room_types(hotel_id,room_types_id)
VALUES (1,4);
INSERT INTO hotels_room_types(hotel_id,room_types_id)
VALUES (1,5);
INSERT INTO hotels_room_types(hotel_id,room_types_id)
VALUES (1,6);

INSERT INTO hotel_services(id,name,price,hotel_id)
VALUES (1,'WiFi',20,1);
INSERT INTO hotel_services(id,name,price,hotel_id)
VALUES (2,'Pool',100,1);
INSERT INTO hotel_services(id,name,price,hotel_id)
VALUES (3,'Parking',150,1);
INSERT INTO hotel_services(id,name,price,hotel_id)
VALUES (4,'Restaurant',200,1);
INSERT INTO hotel_services(id,name,price,hotel_id)
VALUES (5,'Wellness',250,1);
INSERT INTO hotel_services(id,name,price,hotel_id)
VALUES (6,'Spa center',400,1);
INSERT INTO hotel_services(id,name,price,hotel_id)
VALUES (7,'Transport to Airport',100,1);

INSERT INTO hotels_hotel_services(hotel_id,hotel_services_id)
VALUES (1,1);
INSERT INTO hotels_hotel_services(hotel_id,hotel_services_id)
VALUES (1,2);
INSERT INTO hotels_hotel_services(hotel_id,hotel_services_id)
VALUES (1,3);
INSERT INTO hotels_hotel_services(hotel_id,hotel_services_id)
VALUES (1,4);
INSERT INTO hotels_hotel_services(hotel_id,hotel_services_id)
VALUES (1,5);
INSERT INTO hotels_hotel_services(hotel_id,hotel_services_id)
VALUES (1,6);
INSERT INTO hotels_hotel_services(hotel_id,hotel_services_id)
VALUES (1,7);

INSERT INTO rooms (name,number_of_beds, number_of_people, number_of_rooms,room_number,floor,room_type_id,hotel_id)
VALUES ('Swan of love' ,1, 2 , 1 ,107, 1, 7 , 2);

INSERT INTO rooms (number_of_beds, number_of_people, number_of_rooms,room_number,floor,room_type_id,hotel_id)
VALUES (4, 6 , 2 ,118, 1, 3 , 1);
INSERT INTO rooms (number_of_beds, number_of_people, number_of_rooms,room_number,floor,room_type_id,hotel_id)
VALUES (1, 2 , 1 ,100, 1, 1 , 1);
INSERT INTO rooms (number_of_beds, number_of_people, number_of_rooms,room_number,floor,room_type_id,hotel_id)
VALUES (2, 3 , 1 ,109, 1, 2 , 1);
INSERT INTO rooms (number_of_beds, number_of_people, number_of_rooms,room_number,floor,room_type_id,hotel_id)
VALUES (2, 4 , 2 ,112, 1, 4 , 1);
INSERT INTO rooms (number_of_beds, number_of_people, number_of_rooms,room_number,floor,room_type_id,hotel_id)
VALUES (4, 6 , 2 ,104, 1, 5 , 1);

INSERT INTO hotel_price_list (price,starts,hotel_id,room_type_id)
VALUES (100,'2019-1-2 23:59:59',1,1);

INSERT INTO hotel_price_list (price,starts,hotel_id,room_type_id)
VALUES (200,'2019-1-2 23:59:59',1,2);

INSERT INTO hotel_price_list (price,starts,hotel_id,room_type_id)
VALUES (350,'2019-1-2 23:59:59',1,3);

INSERT INTO hotel_price_list (price,starts,hotel_id,room_type_id)
VALUES (650,'2019-1-2 23:59:59',1,4);

INSERT INTO hotel_price_list (price,starts,hotel_id,room_type_id)
VALUES (800,'2019-1-2 23:59:59',1,5);

INSERT INTO hotel_price_list (price,starts,hotel_id,room_type_id)
VALUES (1200,'2019-1-2 23:59:59',1,6);

INSERT INTO hotel_price_list (price,starts,hotel_id,room_type_id)
VALUES (400,'2019-1-2 23:59:59',2,7);

INSERT INTO hotels_hotel_price_list(hotel_id,hotel_price_list_id)
VALUES (1,1);
INSERT INTO hotels_hotel_price_list(hotel_id,hotel_price_list_id)
VALUES (1,2);
INSERT INTO hotels_hotel_price_list(hotel_id,hotel_price_list_id)
VALUES (1,3);
INSERT INTO hotels_hotel_price_list(hotel_id,hotel_price_list_id)
VALUES (1,4);
INSERT INTO hotels_hotel_price_list(hotel_id,hotel_price_list_id)
VALUES (1,5);
INSERT INTO hotels_hotel_price_list(hotel_id,hotel_price_list_id)
VALUES (1,6);
INSERT INTO hotels_hotel_price_list(hotel_id,hotel_price_list_id)
VALUES (2,7);


INSERT INTO reservation_hotel (people,arrival_date, departure_date,nights_staying,reservation_date,hotel_id,room_id,user_id,user_order_id)
VALUES (3, '2019-01-01', '2019-01-15' ,14, '2019-01-01', 1 , 3 ,2, null );

INSERT INTO reservation_hotel (people,arrival_date, departure_date,nights_staying,reservation_date,hotel_id,room_id,user_id,user_order_id)
VALUES (5, '2019-01-10', '2019-01-15' ,14, '2019-01-01', 1 ,4,3, null );

INSERT INTO reservation_hotel (people,arrival_date, departure_date,nights_staying,reservation_date,hotel_id,room_id,user_id,user_order_id,fast)
VALUES (4, '2019-03-03', '2019-03-15' ,12, null, 1 , 3 ,null , null,true );

INSERT INTO reservation_hotel (people,arrival_date, departure_date,nights_staying,reservation_date,hotel_id,room_id,user_id,user_order_id,fast)
VALUES (3, '2019-05-10', '2019-05-15' ,5, null, 1 ,4,null , null,true );

/*destinations*/
insert into airlines_destinations (airline_id, destinations_id) values (1,1);
insert into airlines_destinations (airline_id, destinations_id) values (1,2);


insert into flights (c_columns, c_rows, distance, duration, land_time, number_of_stops, price, segments, start_time, airline_id, finish_id, start_id)
values (2,2,100,2,'2019-2-20 23:59:59',0,100,2,'2019-2-15 23:59:59',1,2,1);

insert into seats (c_column, c_row, price, quick, taken, reservation_id)
values (0,0,100,true,false, null);
insert into seats (c_column, c_row, price, quick, taken, reservation_id)
values (1,0,100,true,false, null);
insert into seats (c_column, c_row, price, quick, taken, reservation_id)
values (2,0,100,false,false, null);
insert into seats (c_column, c_row, price, quick, taken, reservation_id)
values (3,0,100,false,false,null);
insert into seats (c_column, c_row, price, quick, taken, reservation_id)
values (0,1,100,false,false,null);
insert into seats (c_column, c_row, price, quick, taken, reservation_id)
values (1,1,100,false,false,null);
insert into seats (c_column, c_row, price, quick, taken, reservation_id)
values (2,1,100,false,false,null);
insert into seats (c_column, c_row, price, quick, taken, reservation_id)
values (3,1,100,false,false,null);

insert into flights_seats(flight_id,seats_id) values(1,1);
insert into flights_seats(flight_id,seats_id) values(1,2);
insert into flights_seats(flight_id,seats_id) values(1,3);
insert into flights_seats(flight_id,seats_id) values(1,4);
insert into flights_seats(flight_id,seats_id) values(1,5);
insert into flights_seats(flight_id,seats_id) values(1,6);
insert into flights_seats(flight_id,seats_id) values(1,7);
insert into flights_seats(flight_id,seats_id) values(1,8);

insert into flights (c_columns, c_rows, distance, duration, land_time, number_of_stops, price, segments, start_time, airline_id, finish_id, start_id)
values (2,2,100,2,'2018-1-2 23:59:59',0,50,2,'2018-1-1 23:59:59',1,2,1);

insert into seats (c_column, c_row, price, quick, taken, reservation_id)
values (0,0,50,false,false,null);
insert into seats (c_column, c_row, price, quick, taken, reservation_id)
values (1,0,50,false,false,null);
insert into seats (c_column, c_row, price, quick, taken, reservation_id)
values (2,0,50,false,false,null);
insert into seats (c_column, c_row, price, quick, taken, reservation_id)
values (3,0,50,false,false,null);
insert into seats (c_column, c_row, price, quick, taken, reservation_id)
values (0,1,50,false,false,null);
insert into seats (c_column, c_row, price, quick, taken, reservation_id)
values (1,1,50,false,false,null);
insert into seats (c_column, c_row, price, quick, taken, reservation_id)
values (2,1,50,true,false,null);
insert into seats (c_column, c_row, price, quick, taken, reservation_id)
values (3,1,50,true,false,null);

insert into flights_seats(flight_id,seats_id) values(2,9);
insert into flights_seats(flight_id,seats_id) values(2,10);
insert into flights_seats(flight_id,seats_id) values(2,11);
insert into flights_seats(flight_id,seats_id) values(2,12);
insert into flights_seats(flight_id,seats_id) values(2,13);
insert into flights_seats(flight_id,seats_id) values(2,14);
insert into flights_seats(flight_id,seats_id) values(2,15);
insert into flights_seats(flight_id,seats_id) values(2,16);


/*insert into airlines_admins (airline_id, admins_id) values (1,5); Commented because of An illegal reflective access operation has occurred*/

/* Rent Reservations*/
