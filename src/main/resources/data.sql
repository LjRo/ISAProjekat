/* Locations */

INSERT INTO locations (address_name,city,country,latitude,longitude) /*1*/
VALUES ("Buto Musilini 2, Venecia", "Venecia", "Italy", 12.144123 , 15.1414 );

INSERT INTO locations (address_name,city,country,latitude,longitude) /*2*/
VALUES ("Albara Musafa 2, Milano", "Milano", "Italy", 42.144123 , 11.155 );


INSERT INTO locations (address_name,city,country,latitude,longitude) /*3*/
VALUES ("Maksima Gorkog 35", "Milano", "Italiy", 45.4642 , 9.1900 );

INSERT INTO locations (address_name,city,country,latitude,longitude) /*4*/
VALUES ("Boulevard Tsara Lazara 1", "Novi Sad", "Serbia", 45.247637 , 19.850474);

INSERT INTO locations (address_name,city,country,latitude,longitude) /*5*/
VALUES ("Nikola Tesle 21", "Beograd", "Serbia", 44.49153 ,  20.17314);

INSERT INTO locations (address_name,city,country,latitude,longitude) /*6*/
VALUES ("Kurta Schorka 36", "Sarajevo", "BiH", 43.49303  , 18.20074);


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

/* rent a car*/


INSERT INTO rentacar (name, address_id, description,fast_discount)
VALUES ('RentLjubomir',2, 'Profesional cars with state of the art quaility',20);

INSERT INTO rentacar (name,address_id, description,fast_discount)
VALUES ('RentAleksandar', 1, 'Cheap cars that will take you where you need to be.',20);

/* rent office*/

INSERT INTO rent_office (name,location_id,rentacar_id)
VALUES ("Prva filijala",1,1);

INSERT INTO rent_office (name,location_id,rentacar_id)
VALUES ("Druga filijala",2,1);

INSERT INTO rent_office (name,location_id,rentacar_id)
VALUES ("QQ filijala",3,2);

INSERT INTO rent_office (name,location_id,rentacar_id)
VALUES ("WW filijala",4,2);

/*
INSERT INTO rentacar_rent_offices (rentacar_id,rent_offices_id)
VALUES (1,1);

INSERT INTO rentacar_rent_offices (rentacar_id,rent_offices_id)
VALUES (1,2);*/

/*  cars.sql  */


INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ("Ficko","Mercedes","Benz","13AC12",4,4,4,100,false,1,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ("Brzko","Mercedes","Benz","13QQ55",4,4,4,250,true,1,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ("Spora","Mercedes","C","44WE32",4,4,4,100,false,1,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ("FickoQ","Mercedes","Benz","13AC12",4,4,4,100,false,1,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ("BrzkoQ","Mercedes","Benz","13QQ55",4,4,4,250,true,1,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ("SporaQ","Mercedes","C","44WE32",4,4,4,100,false,1,1);


INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ("FickoQW","Mercedes","Benz","13AC12",4,4,4,100,false,1,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ("BrzkoQW","Mercedes","Benz","13QQ55",4,4,4,250,true,1,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ("SporaQW","Mercedes","C","44WE32",4,4,4,100,false,1,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ("FickoQWE","Mercedes","Benz","13AC12",4,4,4,100,false,1,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ("BrzkoQWE","Mercedes","Benz","13QQ55",4,4,4,250,true,1,1);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ("SporaQWE","Mercedes","C","44WE32",4,4,4,100,false,1,1);


INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ("EW","Mercedes","C","44WE32",4,4,4,100,false,2,2);

INSERT into cars (name, mark, model, registration_number, number_of_bags, max_passengers, number_of_doors, daily_price, is_fast_reserved, type_id, rentacar_id )
values ("WE","Mercedes","C","44WE32",4,4,4,100,false,2,1);

/* users.sql */
/* password = 123*/

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number,points)
values (1,"admin@gmail.com","admin","adminovic","$2a$10$CItG2LPX2aHLCzm9LMud8e05QoLTM1rxEd/WdfZJKHAbE0miu23/e","adr2","city2",b'1',"123-456-1234",0);

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number,points)
values (0,"normal@gmail.com","pera","peric","$2a$10$KzK60SLUUU8UKH/rtkZsYuRjc.9D1G.HJgmDnRzJDVl6y3V4/X64u","adr1","city1",b'1',"123-456-1234",0);

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number,points)
values (0,"normala@gmail.com","normal","normalic","$2a$10$Zz/H94PBqMWVWQlRgCw6GORvl8pIxR8yll1UX/SIy6U7JVO0LF2OW","adr0","city0",b'1',"123-456-1234",0);

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number,administrated_hotel_id)
values (3,"hotel@gmail.com","hotel","hotelic","$2a$10$Zz/H94PBqMWVWQlRgCw6GORvl8pIxR8yll1UX/SIy6U7JVO0LF2OW","adr3","city3",b'1',"123-456-1234",1);

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number,administrated_airline_id)
values (2,"airline@gmail.com","airline","airlinovic","$2a$10$Zz/H94PBqMWVWQlRgCw6GORvl8pIxR8yll1UX/SIy6U7JVO0LF2OW","adr4","city4",b'1',"123-456-1234",1);

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number, administrated_rent_id)
values (4,"rent@gmail.com","rent","rentovic","$2a$10$Zz/H94PBqMWVWQlRgCw6GORvl8pIxR8yll1UX/SIy6U7JVO0LF2OW","adr5","city5",b'1',"123-456-1234", 1);

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number,points)
values (0,"normalb@gmail.com","Mika","Mikic","$2a$10$Zz/H94PBqMWVWQlRgCw6GORvl8pIxR8yll1UX/SIy6U7JVO0LF2OW","adr0","city0",b'1',"123-456-1234",0);

INSERT into Users (type, username, first_name, last_name, password, address, city, enabled, phone_number,points)
values (0,"normalc@gmail.com","Neko","Nekic","$2a$10$Zz/H94PBqMWVWQlRgCw6GORvl8pIxR8yll1UX/SIy6U7JVO0LF2OW","adr0","city0",b'1',"123-456-1234",0);


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

/*companies.sql*/

INSERT INTO airlines (name, address_id, description, has_food, has_extra_luggage, has_other_services, food_price, luggage_price)
VALUES ('AirSerbia',5, 'Aeroport Nikole Tesle Belgrade', true, true, false, 50, 100);

INSERT INTO airlines (name, address_id, description, has_food, has_extra_luggage, has_other_services, food_price, luggage_price)
VALUES ('AirMontenegro', 6, 'Aeroport Saint Sarevos', false, false, true, null, null);




INSERT INTO ratings(user_rating,user_id,type,hotel_id)
VALUES (3,1,3,1);




INSERT INTO hotels (name, address_id, description,fast_discount)
VALUES ('Aleksandar', 3, 'Hotel with 5 stars in Milano with great locations',15);

INSERT INTO hotels (name, address_id, description,fast_discount)
VALUES ('Putnik', 4 , 'Cheap rooms ,Hotel with 3 stars near the center',15);


INSERT INTO room_type (name)
VALUES ("Single Bed");

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


INSERT INTO room_type (name)
VALUES ("Single Beds");

INSERT INTO room_type (name)
VALUES ("Couple DoubleBed + 2 Single beds");

INSERT INTO room_type (name)
VALUES ("Couple DoubleBed + Single bed");

INSERT INTO room_type (name)
VALUES ("2 Couple DoubleBeds");

INSERT INTO room_type (name)
VALUES ("2 Couple DoubleBeds + Single bed");

INSERT INTO room_type (name)
VALUES ("Deluxe room");

INSERT INTO rooms (name,number_of_beds, number_of_people, number_of_rooms,room_number,floor,room_type_id,hotel_id)
VALUES ("Swan of love" ,1, 2 , 1 ,107, 1, 7 , 2);

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


INSERT INTO reservation_hotel (people,arrival_date, departure_date,nights_staying,reservation_date,hotel_id,room_id,user_id,airline_reservation_id)
VALUES (3, '2019-01-01', '2019-01-15' ,14, '2019-01-01', 1 , 3 ,2, null );

INSERT INTO reservation_hotel (people,arrival_date, departure_date,nights_staying,reservation_date,hotel_id,room_id,user_id,airline_reservation_id)
VALUES (5, '2019-01-10', '2019-01-15' ,14, '2019-01-01', 1 ,4,3, null );


INSERT INTO floor_plan (configuration, floor_number, hotel_id)
VALUES ('<div id="droppable" style="z-index: 1; height: 359.011px; width: 766.011px; display: inline-block;" class="floorplan ui-droppable ui-resizable"><div id="draggable6" style="z-index: 10; width: 135.011px; height: 58.0114px; left: 10.9943px; top: 11.9943px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>101</p></div><div id="draggable7" style="z-index: 10; width: 124.011px; height: 57.0114px; left: 144.96px; top: 11.9631px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>102</p></div><div id="draggable8" style="z-index: 10; height: 58.0171px; width: 118.025px; left: 268.969px; top: 11.9602px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>103</p></div><div id="draggable9" style="z-index: 10; width: 115.02px; height: 58.0455px; left: 386.969px; top: 11.946px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>104</p></div><div id="draggable10" style="z-index: 10; width: 61.0284px; height: 100.017px; left: 11.946px; top: 68.9375px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>116</p></div><div id="draggable11" style="z-index: 10; left: 10.9034px; top: 168.915px; height: 95.0284px; width: 61.0341px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>115</p></div><div id="draggable13" style="z-index: 10; left: 561.972px; top: 11.9773px; height: 57.0171px; width: 175.02px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>105</p></div><div id="draggable14" style="z-index: 10; left: 674.966px; top: 68.9517px; height: 101.023px; width: 65.0114px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>106</p></div><div id="draggable15" style="z-index: 10; left: 674.881px; top: 169.872px; width: 65.0142px; height: 95.0114px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>107</p></div><div id="draggable16" style="z-index: 10; height: 85.037px; width: 65.0284px; left: 674.923px; top: 261.898px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>108</p></div><div id="draggable17" style="z-index: 10; left: 10.9034px; top: 262.915px; height: 83.0341px; width: 62.0341px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>114</p></div><div id="draggable18" style="z-index: 10; left: 101.974px; top: 90.9659px; width: 197.02px; height: 78.0114px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>117</p></div><div id="draggable19" style="z-index: 10; left: 100.989px; top: 168.977px; width: 66.0114px; height: 96.0114px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>Staff</p></div><div id="draggable20" style="z-index: 10; left: 231.983px; top: 167.977px; height: 95.0114px; width: 66.0114px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>125</p></div><div id="draggable21" style="z-index: 10; left: 165.955px; top: 167.952px; height: 57.0284px; width: 65.0284px;" class="room ui-draggable ui-draggable-handle ui-resizable"><img style="z-index:15; width:100%;height: 100%;" src="assets/img/stairs.png"></div><div id="draggable22" style="z-index: 10; left: 423.849px; top: 295.852px; width: 118.02px; height: 50.0284px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>110</p></div><div id="draggable23" style="z-index: 10; left: 539.923px; top: 295.926px; width: 136.014px; height: 50.0284px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>109</p></div><div id="draggable24" style="z-index: 10; width: 121.02px; height: 50.0313px; left: 302.935px; top: 295.926px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>111</p></div><div id="draggable25" style="z-index: 10; height: 50.0228px; width: 118.022px; left: 185.952px; top: 295.96px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>112</p></div><div id="draggable26" style="z-index: 10; left: 72.9063px; top: 296.94px; width: 113.028px; height: 49.0199px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>113</p></div><div id="draggable27" style="z-index: 10; left: 325.932px; top: 89.9375px; width: 143.014px; height: 80.0228px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>118</p></div><div id="draggable28" style="z-index: 10; width: 145.042px; left: 324.935px; top: 169.949px; height: 95.0256px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>124</p></div><div id="draggable29" style="z-index: 10; left: 467.963px; top: 89.9801px; height: 80.0228px; width: 85.0142px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>119</p></div><div id="draggable30" style="z-index: 10; left: 552.954px; top: 88.9659px; height: 81.0114px; width: 82.0228px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>120</p></div><div id="draggable31" style="z-index: 10; left: 467.986px; top: 167.977px; width: 84.0114px; height: 97.0114px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>123</p></div><div id="draggable32" style="z-index: 10; left: 551.977px; top: 169.972px; height: 94.0114px; width: 83.0142px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>121</p></div><div id="draggable33" style="z-index: 10; left: 501.977px; top: 10.9773px; height: 58.0199px; width: 60.0114px;" class="room ui-draggable ui-draggable-handle ui-resizable"><img style="z-index:15; width:100%;height: 100%;" src="assets/img/stairs.png"></div></div>',1,1);

INSERT INTO floor_plan (configuration, floor_number , hotel_id)
VALUES('<div id="save" style="width: 100%;"><div id="droppable" style="z-index: 1; height: 359.011px; width: 766.011px; display: inline-block;" class="floorplan ui-droppable ui-resizable"><div id="draggable6" style="z-index: 10; width: 135.011px; height: 58.0114px; left: 10.9943px; top: 11.9943px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>201</p></div><div id="draggable7" style="z-index: 10; width: 124.011px; height: 57.0114px; left: 144.96px; top: 11.9631px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>202</p></div><div id="draggable8" style="z-index: 10; height: 58.0171px; width: 118.025px; left: 268.969px; top: 11.9602px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>203</p></div><div id="draggable9" style="z-index: 10; width: 115.02px; height: 58.0455px; left: 386.969px; top: 11.946px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>204</p></div><div id="draggable10" style="z-index: 10; width: 61.0284px; height: 100.017px; left: 11.946px; top: 68.9375px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>216</p></div><div id="draggable11" style="z-index: 10; left: 10.9034px; top: 168.915px; height: 95.0284px; width: 61.0341px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>215</p></div><div id="draggable13" style="z-index: 10; left: 561.972px; top: 11.9773px; height: 57.0171px; width: 175.02px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>205</p></div><div id="draggable14" style="z-index: 10; left: 674.966px; top: 68.9517px; height: 101.023px; width: 65.0114px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>206</p></div><div id="draggable15" style="z-index: 10; left: 674.881px; top: 169.872px; width: 65.0142px; height: 95.0114px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>207</p></div><div id="draggable16" style="z-index: 10; height: 85.037px; width: 65.0284px; left: 674.923px; top: 261.898px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>208</p></div><div id="draggable17" style="z-index: 10; left: 10.9034px; top: 262.915px; height: 83.0341px; width: 62.0341px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>214</p></div><div id="draggable18" style="z-index: 10; left: 101.974px; top: 90.9659px; width: 197.02px; height: 78.0114px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>217</p></div><div id="draggable19" style="z-index: 10; left: 100.989px; top: 168.977px; width: 66.0114px; height: 96.0114px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>Staff</p></div><div id="draggable20" style="z-index: 10; left: 231.983px; top: 167.977px; height: 95.0114px; width: 66.0114px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>224</p></div><div id="draggable21" style="z-index: 10; left: 165.955px; top: 167.952px; height: 57.0284px; width: 65.0284px;" class="room ui-draggable ui-draggable-handle ui-resizable"><img style="z-index:15; width:100%;height: 100%;" src="assets/img/stairs.png"></div><div id="draggable22" style="z-index: 10; left: 423.849px; top: 295.852px; width: 118.02px; height: 50.0284px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>210</p></div><div id="draggable23" style="z-index: 10; left: 539.923px; top: 295.926px; width: 136.014px; height: 50.0284px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>209</p></div><div id="draggable24" style="z-index: 10; width: 121.02px; height: 50.0313px; left: 302.935px; top: 295.926px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>211</p></div><div id="draggable25" style="z-index: 10; height: 50.0228px; width: 118.022px; left: 185.952px; top: 295.96px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>212</p></div><div id="draggable26" style="z-index: 10; left: 72.9063px; top: 296.94px; width: 113.028px; height: 49.0199px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>213</p></div><div id="draggable27" style="z-index: 10; left: 325.932px; top: 89.9375px; width: 143.014px; height: 80.0228px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>218</p></div><div id="draggable28" style="z-index: 10; width: 145.042px; left: 324.935px; top: 169.949px; height: 95.0256px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>223</p></div><div id="draggable29" style="z-index: 10; left: 467.963px; top: 89.9801px; height: 80.0228px; width: 85.0142px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>219</p></div><div id="draggable30" style="z-index: 10; left: 552.954px; top: 88.9659px; height: 81.0114px; width: 82.0228px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>220</p></div><div id="draggable31" style="z-index: 10; left: 467.986px; top: 167.977px; width: 84.0114px; height: 97.0114px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>222</p></div><div id="draggable32" style="z-index: 10; left: 551.977px; top: 169.972px; height: 94.0114px; width: 83.0142px;" class="room ui-draggable ui-draggable-handle ui-resizable"><p>221</p></div><div id="draggable33" style="z-index: 10; left: 501.977px; top: 10.9773px; height: 58.0199px; width: 60.0114px;" class="room ui-draggable ui-draggable-handle ui-resizable"><img style="z-index:15; width:100%;height: 100%;" src="assets/img/stairs.png"></div></div></div>',2,1);

/*destinations*/
insert into airlines_destinations (airline_id, destinations_id) values (1,1);
insert into airlines_destinations (airline_id, destinations_id) values (1,2);

insert into users_friends(user_id,friends_id) values (2,3);
insert into users_friends(user_id,friends_id) values (3,2);
insert into users_friends(user_id,friends_id) values (2,7);
insert into users_friends(user_id,friends_id) values (7,2);
insert into users_friends(user_id,friends_id) values (2,8);
insert into users_friends(user_id,friends_id) values (8,2);

insert into flights (c_columns, c_rows, distance, duration, land_time, number_of_stops, price, segments, start_time, airline_id, finish_id, start_id)
values (2,2,100,2,'2019-2-20 23:59:59',0,100,2,'2019-2-15 23:59:59',1,2,1);

insert into seats (c_column, c_row, price, quick, taken, reservation_id)
values (0,0,100,true,false,null);
insert into seats (c_column, c_row, price, quick, taken, reservation_id)
values (1,0,100,true,false,null);
insert into seats (c_column, c_row, price, quick, taken, reservation_id)
values (2,0,100,false,true,null);
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
values (2,2,100,2,'2019-1-2 23:59:59',0,50,2,'2019-1-1 23:59:59',1,2,1);

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

insert into reservations(user_name,last_name,user_id,flight_id,passport,points_used, total_cost, confirmed, finished, seat_id) values(null,null,2,1,"RS201515123",0,100,true,true,3);
insert into users_reservations (user_id, reservations_id) values (2,1);

/*insert into airlines_admins (airline_id, admins_id) values (1,5); Commented because of An illegal reflective access operation has occurred*/

/* Rent Reservations*/

INSERT INTO rent_reservation (user_id,start_date, end_date, end_location_id, start_location_id, number_of_people, airline_reservation_id, rented_car_id, price, fast_reservation) VALUES (2,'2019-01-01','2019-01-10', 1,3,3,null,1, 100,false);
INSERT INTO rent_reservation (user_id,start_date, end_date, end_location_id, start_location_id, number_of_people, airline_reservation_id, rented_car_id, price, fast_reservation) VALUES (2,'2019-01-15','2019-01-23', 1,3,3,null,1, 100,false);
INSERT INTO rent_reservation (user_id,start_date, end_date, end_location_id, start_location_id, number_of_people, airline_reservation_id, rented_car_id, price, fast_reservation) VALUES (2,'2019-01-27','2019-02-23', 1,3,3,null,1, 100,false);
INSERT INTO rent_reservation (user_id,start_date, end_date, end_location_id, start_location_id, number_of_people, airline_reservation_id, rented_car_id, price, fast_reservation) VALUES (2,'2019-01-01','2019-01-02', 1,3,3,null,14, 100, false);
INSERT INTO rent_reservation (user_id,start_date, end_date, end_location_id, start_location_id, number_of_people, airline_reservation_id, rented_car_id, price, fast_reservation) VALUES (2,'2019-01-05','2019-01-08', 1,3,3,null,14, 100, false);

INSERT INTO rent_reservation (user_id,start_date, end_date, end_location_id, start_location_id, number_of_people, airline_reservation_id, rented_car_id, price, fast_reservation) VALUES (null,'2019-01-01','2019-01-02', 1,3,3,null,2, 200, true);
INSERT INTO rent_reservation (user_id,start_date, end_date, end_location_id, start_location_id, number_of_people, airline_reservation_id, rented_car_id, price, fast_reservation) VALUES (null,'2019-01-05','2019-01-08', 1,3,3,null,2, 500, true);

INSERT INTO rent_reservation (user_id,start_date, end_date, end_location_id, start_location_id, number_of_people, airline_reservation_id, rented_car_id, price, fast_reservation) VALUES (null,'2019-02-05','2019-02-12', 1,3,3,null,2, 1200, true);
INSERT INTO rent_reservation (user_id,start_date, end_date, end_location_id, start_location_id, number_of_people, airline_reservation_id, rented_car_id, price, fast_reservation) VALUES (null,'2019-02-20','2019-02-25', 1,3,3,null,2, 950, true);