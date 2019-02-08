
INSERT INTO locations (address_name,city,country,latitude,longitude, active)
VALUES ('Nikola Tesle 21', 'Beograd', 'Serbia', 44.49153 ,  20.17314, true);

INSERT INTO locations (address_name,city,country,latitude,longitude) /*1*/
VALUES ('Maksima Gorkog 35', 'Milano', 'Italiy', 45.4642 , 9.1900 );

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number,points)
values (0,'normal@gmail.com','pera','peric','$2a$10$KzK60SLUUU8UKH/rtkZsYuRjc.9D1G.HJgmDnRzJDVl6y3V4/X64u','adr1','city1',true,'123-456-1234',0);


INSERT INTO AUTHORITY (name) VALUES ('ROLE_USER');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMIN');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMIN_AIRLINE');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMIN_HOTEL');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMIN_RENT');

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 1);



INSERT INTO airlines (name, address_id, description, has_food, has_extra_luggage, has_other_services, food_price, luggage_price)
VALUES ('AirSerbia',1, 'Aeroport Nikole Tesle Belgrade', true, true, false, 50, 100);

insert into airlines_destinations (airline_id, destinations_id) values (1,1);

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number,administrated_airline_id,password_changed)
values (2,'airline@gmail.com','airline','airlinovic','$2a$10$Zz/H94PBqMWVWQlRgCw6GORvl8pIxR8yll1UX/SIy6U7JVO0LF2OW','adr4','city4',true,'123-456-1234',1,true);

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 3);

insert into flights (c_columns, c_rows, distance, duration, land_time, number_of_stops, price, segments, start_time, airline_id, finish_id, start_id)
values (2,2,100,2,'2019-2-20 23:59:59',0,100,2,'2019-2-15 23:59:59',1,1,1);

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


INSERT INTO hotels (name, address_id, description,fast_discount)
VALUES ('Aleksandar', 2, 'Hotel with 5 stars in Milano with great locations',15);

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number,administrated_hotel_id,password_changed)
values (3,'hotel@gmail.com','hotel','hotelic','$2a$10$Zz/H94PBqMWVWQlRgCw6GORvl8pIxR8yll1UX/SIy6U7JVO0LF2OW','adr3','city3',true,'123-456-1234',1,true);

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (3, 4);

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number,points)
values (1,'admin@gmail.com','admin','adminovic','$2a$10$CItG2LPX2aHLCzm9LMud8e05QoLTM1rxEd/WdfZJKHAbE0miu23/e','adr2','city2',true,'123-456-1234',0);


INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (4, 2);

INSERT INTO room_type (name)
VALUES ('Single Beds');

INSERT INTO hotels_room_types(hotel_id,room_types_id)
VALUES (1,1);

INSERT INTO rooms (id,number_of_beds, number_of_people, number_of_rooms,room_number,floor,room_type_id,hotel_id)
VALUES (1,1, 2 , 2 ,118, 1, 1 , 1);

INSERT INTO rooms (number_of_beds, number_of_people, number_of_rooms,room_number,floor,room_type_id,hotel_id)
VALUES (1, 2 , 2 ,118, 1, 1 , 1);

INSERT INTO rooms (number_of_beds, number_of_people, number_of_rooms,room_number,floor,room_type_id,hotel_id)
VALUES (1, 2 , 2 ,208, 1, 1 , 1);

INSERT INTO hotel_services(name,price)
VALUES ('WiFi',20);

INSERT INTO hotels_hotel_services(hotel_id,hotel_services_id)
VALUES (1,1);

INSERT INTO reservation_hotel (people,arrival_date, departure_date,nights_staying,reservation_date,room_id,user_id,user_order_id)
VALUES (3, '2019-01-01', '2019-01-15' ,14, '2019-01-01' , 1 ,1, null );

INSERT INTO reservation_hotel (people,arrival_date, departure_date,nights_staying,reservation_date,room_id,user_id,user_order_id,fast,price)
VALUES (4, '2018-02-03', '2018-02-15' ,12, null , 1 ,null , null,true ,1400);

INSERT INTO rentacar (name,address_id, description,fast_discount)
VALUES ('RentAleksandar', 1, 'Cheap cars that will take you where you need to be.',20);

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number, administrated_rent_id,password_changed)
values (4,'rent@gmail.com','rent','rentovic','$2a$10$Zz/H94PBqMWVWQlRgCw6GORvl8pIxR8yll1UX/SIy6U7JVO0LF2OW','adr5','city5',true,'123-456-1234', 1,true);


