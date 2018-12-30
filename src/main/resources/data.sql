
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

INSERT into Users (type, username, email, first_name, last_name, password, address, city, enabled, phone_number)
values (0,"normal","normal@gmail.com","pera","peric","$2a$10$UwDhAk3jFirZEec1o3pEMeVnUPws.nJG2N3AxjmmyTYVMjOsmB3ku","adr1","city1",b'1',"123-456-1234");

INSERT into Users (type, username, email, first_name, last_name, password, address, city, enabled, phone_number)
values (0,"normalA","normala@gmail.com","normal","normalic","$2a$10$3eTRX.1YlWwnP/j7oeMRpeFP.DA9UhTuUoxO.jMqLXHpIITohxPjW","adr0","city0",b'1',"123-456-1234");

INSERT into Users (type, username, email, first_name, last_name, password, address, city, enabled, phone_number)
values (1,"admin","admin@gmail.com","zika","zikic","$2a$10$6GTuwgfxgVEqQJt1lWsPu.4yiN8ktBAP7JABIcnbehHE7bybpIDKG","adr2","city2",b'1',"123-456-1234");
