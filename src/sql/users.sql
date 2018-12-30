use isaprojekat;


/*0 - Normal, 1 - Admin, 2 - Airline Admin, 3 - Hotel Admin, 4 - RentACar Admin */
/* password = 123*/

INSERT into Users (type, username, email, first_name, last_name, password, address, city, enabled, phone_number)
values (0,"normal","normal@gmail.com","pera","peric","$2a$10$UwDhAk3jFirZEec1o3pEMeVnUPws.nJG2N3AxjmmyTYVMjOsmB3ku","adr1","city1",b'1',"123-456-1234");

INSERT into Users (type, username, email, first_name, last_name, password, address, city, enabled, phone_number)
values (0,"normalA","normala@gmail.com","normal","normalic","$2a$10$3eTRX.1YlWwnP/j7oeMRpeFP.DA9UhTuUoxO.jMqLXHpIITohxPjW","adr0","city0",b'1',"123-456-1234");


INSERT into Users (type, username, email, first_name, last_name, password, address, city, enabled, phone_number)
values (1,"admin","admin@gmail.com","zika","zikic","$2a$10$6GTuwgfxgVEqQJt1lWsPu.4yiN8ktBAP7JABIcnbehHE7bybpIDKG","adr2","city2",b'1',"123-456-1234");

/*INSERT into Users (type, username, email, first_name, last_name, password, address, city, enabled, phone_number)
values (2,"airline","airline@gmail.com","airline","airlineovic","$10$M7ooixCj3tCNjIvHecXVAOnS6Myx0gFMmi0nSf6s2lwp/8ylNsdy.","adr1","city1",b'1',"123-456-1234");

INSERT into Users (type, username, email, first_name, last_name, password, address, city, enabled, phone_number)
values (3,"hotel","hotel@gmail.com","hotel","hotelic","$10$M7ooixCj3tCNjIvHecXVAOnS6Myx0gFMmi0nSf6s2lwp/8ylNsdy.","adr1","city1",b'1',"123-456-1234");

INSERT into Users (type, username, email, first_name, last_name, password, address, city, enabled, phone_number)
values (4,"rent","rent@gmail.com","rent","retnic","$10$M7ooixCj3tCNjIvHecXVAOnS6Myx0gFMmi0nSf6s2lwp/8ylNsdy.","adr1","city1",b'1',"123-456-1234");
*/