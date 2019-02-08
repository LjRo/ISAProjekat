
INSERT INTO locations (address_name,city,country,latitude,longitude)
VALUES ('Nikola Tesle 21', 'Beograd', 'Serbia', 44.49153 ,  20.17314);

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number,points)
values (0,'normal@gmail.com','pera','peric','$2a$10$KzK60SLUUU8UKH/rtkZsYuRjc.9D1G.HJgmDnRzJDVl6y3V4/X64u','adr1','city1',true,'123-456-1234',0);

INSERT INTO airlines (name, address_id, description, has_food, has_extra_luggage, has_other_services, food_price, luggage_price)
VALUES ('AirSerbia',1, 'Aeroport Nikole Tesle Belgrade', true, true, false, 50, 100);

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