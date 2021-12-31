insert into country (code, name) values ('SRB', 'Srbija');
insert into city (postal_code, name, country_id) values ('21000', 'Novi Sad', 1);
insert into address (address, city_id, longitude, latitude) values ('Micurinova 17', 1, 0.0, 0.0)
insert into role (name) values ('ROLE_CLIENT')
insert into role (name) values ('ROLE_BOAT_OWNER')
insert into role (name) values ('ROLE_INSTRUCTOR')
insert into role (name) values ('ROLE_HOUSE_OWNER')
insert into role (name) values ('ROLE_ADMIN')
insert into users (id, first_name, last_name, email, password, phone_number, address_id, role_id, is_enabled, is_deleted) values (nextval('users_id_seq'), 'Nikola', 'Nikolic', 'niki@gmail.com', 'pass', '12345', 1, 5, false, false);