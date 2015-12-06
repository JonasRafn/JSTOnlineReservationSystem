insert into airline_api (group_name, members, url) values ('angular_airline1', 'Test Airline for Sprint 1', 'http://angularairline-plaul.rhcloud.com/');
insert into airline_api (group_name, members, url) values ('angular_airline2', 'Test Airline for Sprint 1', 'http://angularairline-plaul.rhcloud.com/');
insert into airline_api (group_name, members, url) values ('angular_airline3', 'Test Airline for Sprint 1', 'http://angularairline-plaul.rhcloud.com/');

insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('CPH', 'Copenhagen', 'Copenhagen Airport', 'Denmark','Europe/Copenhagen');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('STN', 'London', 'London Stansted Airport', 'England', 'Europe/London');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('SXF', 'Berlin', 'Flughafen Berlin-Schönefeld', 'Germany', 'Europe/Berlin');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('CDG', 'Paris', 'Aéroport Paris-Charles de Gaulle', 'France' ,'Europe/Paris');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('BCN', 'Barcelona', 'Barcelona Airport','Spain', 'Europe/Madrid');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('LAX', 'Los Angeles', 'Los Angeles International','USA', 'America/Los_Angeles');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('SFO', 'San Francisco', 'San Francisco International','USA', 'America/Los_Angeles');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('JFK', 'New York', 'John F Kennedy International','USA', 'America/New_York');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('HND', 'Tokyo', 'Tokyo International','Japan', 'Asia/Tokyo');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('DME', 'Moscow', 'Domodedovo International Airport','Russia', 'Europe/Moscow');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('PEK', 'Beijing', 'Beijing Capital International Airport','China', 'Asia/Beijing');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('AMS', 'Amsterdam', 'Amsterdam Schiphol Airport','Netherland', 'Europe/Amsterdam');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('IST', 'Istanbul', 'Istanbul Atatürk Airport','Turkey', 'Europe/Istanbul');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('GRU', 'São Paulo', 'São Paulo-Guarulhos International Airport','Brazil', 'America/Sao_Paulo');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('YYZ', 'Toronto', 'Toronto Pearson International Airport','Canada', 'America/Toronto');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('DXB', 'Dubai', 'Dubai International Airport','United Arab Emirates', 'Asia/Dubai');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('ICN', 'Seoul', 'Seoul Incheon International Airport','South Korea', 'Asia/Seoul');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('FCO', 'Rome', 'Leonardo da Vinci-Fiumicino Airport','Italy', 'Europe/Rome');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('MEL', 'Melbourne', 'Tullamarine International Airport','Australia', 'Australia/Melbourne');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('JNB', 'Johannesburg', 'O. R. Tambo International Airport','South Africa', 'Africa/Johannesburg');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('CAI', 'Cairo', 'Cairo International','Egypt', 'Africa/Cairo');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('MEX', 'Mexico City', 'Benito Juárez International Airport','Mexico', 'Mexico/BajaNorte');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('BBU', 'Bucharest', 'Băneasa International Airport','Romania', 'Europe/Bucharest');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('HEM', 'Helsinki', 'Helsinki Malmi Airport','Findland', 'Europe/Helsinki');
insert into airport (IATA_CODE, city, airport_name, country, time_zone) values ('SIN', 'Singapore', 'Singapore Changi International Airport','Singapore', 'Asia/Singapore');

insert into userrole (ROLENAME) values ('User');
insert into userrole (ROLENAME) values ('Admin');

insert into systemuser (USERNAME, PASSWORD) values ('admin', '1000:ac2f87c3602ee1588535e0e196e508bfd563dc544a6dadcd:0824eebf1a4de2cf3f435cdcea90ee408c97bf8c66bbb2f2');
insert into systemuser (USERNAME, PASSWORD) values ('user', '1000:2f05b308ffea5460622f1d8eb5b8036d5f231caddbfb52e0:e6d0fcd188b6c3e26f989e020f34a1fd14c14690ff2ac17f');
insert into systemuser (USERNAME, PASSWORD) values ('user_admin', '1000:39e4a415622f639246c52492920ccd76750b633029413d93:01a34ed109d037c662b11fa86ea2de486d4af8343b677755');

insert into systemuser_userrole (userName, roleName) values ('admin', 'Admin');
insert into systemuser_userrole (userName, roleName) values ('user_admin', 'Admin');
insert into systemuser_userrole (userName, roleName) values ('user_admin', 'User');
insert into systemuser_userrole (userName, roleName) values ('user', 'User');
