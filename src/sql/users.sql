use isaprojekat;


/*0 - Normal, 1 - Admin, 2 - Airline Admin, 3 - Hotel Admin, 4 - RentACar Admin */


INSERT into Users (type, username, email, first_name, last_name, password, address, city, enabled, phone_number)
values (0,"normal","normal@gmail.com","pera","peric","$10$M7ooixCj3tCNjIvHecXVAOnS6Myx0gFMmi0nSf6s2lwp/8ylNsdy.","adr1","city1",b'1',"123-456-1234");

INSERT into Users (type, username, email, first_name, last_name, password, address, city, enabled, phone_number)
values (1,"admin","admin@gmail.com","zika","zikic","$10$M7ooixCj3tCNjIvHecXVAOnS6Myx0gFMmi0nSf6s2lwp/8ylNsdy.","adr1","city1",b'1',"123-456-1234");

INSERT into Users (type, username, email, first_name, last_name, password, address, city, enabled, phone_number)
values (2,"airline","airline@gmail.com","airline","airlineovic","$10$M7ooixCj3tCNjIvHecXVAOnS6Myx0gFMmi0nSf6s2lwp/8ylNsdy.","adr1","city1",b'1',"123-456-1234");

INSERT into Users (type, username, email, first_name, last_name, password, address, city, enabled, phone_number)
values (3,"hotel","hotel@gmail.com","hotel","hotelic","$10$M7ooixCj3tCNjIvHecXVAOnS6Myx0gFMmi0nSf6s2lwp/8ylNsdy.","adr1","city1",b'1',"123-456-1234");

INSERT into Users (type, username, email, first_name, last_name, password, address, city, enabled, phone_number)
values (4,"rent","rent@gmail.com","rent","retnic","$10$M7ooixCj3tCNjIvHecXVAOnS6Myx0gFMmi0nSf6s2lwp/8ylNsdy.","adr1","city1",b'1',"123-456-1234");
