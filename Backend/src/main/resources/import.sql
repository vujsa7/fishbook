insert into country (code, name) values ('AZ', 'Arizona');
insert into country (code, name) values ('CA', 'California');
insert into country (code, name) values ('DE', 'Delaware');
insert into country (code, name) values ('FL', 'Florida');
insert into country (code, name) values ('HI', 'Hawaii');
insert into country (code, name) values ('NJ', 'New Jersey');
insert into country (code, name) values ('NY', 'New York');

insert into city (postal_code, name, country_id) values ('85018', 'Phoenix', 1);
insert into city (postal_code, name, country_id) values ('85142', 'Queen Creek', 1);
insert into city (postal_code, name, country_id) values ('85210', 'Mesa', 1);
insert into city (postal_code, name, country_id) values ('85251', 'Scottsdale', 1);
insert into city (postal_code, name, country_id) values ('90210', 'Beverly Hills', 2);
insert into city (postal_code, name, country_id) values ('90057', 'Los Angeles', 2);
insert into city (postal_code, name, country_id) values ('90230', 'Culver City', 2);
insert into city (postal_code, name, country_id) values ('90277', 'Redondo Beach', 2);
insert into city (postal_code, name, country_id) values ('90401', 'Santa Monica', 2);
insert into city (postal_code, name, country_id) values ('90802', 'Long Beach', 2);
insert into city (postal_code, name, country_id) values ('19711', 'Newark', 3);
insert into city (postal_code, name, country_id) values ('19720', 'New Castle', 3);
insert into city (postal_code, name, country_id) values ('19801', 'Wilmington', 3);
insert into city (postal_code, name, country_id) values ('32118', 'Daytona Beach', 4);
insert into city (postal_code, name, country_id) values ('32207', 'Jacksonville', 4);
insert into city (postal_code, name, country_id) values ('32301', 'Tallahassee', 4);
insert into city (postal_code, name, country_id) values ('32303', 'Panama City', 4);
insert into city (postal_code, name, country_id) values ('96815', 'Honolulu', 5);
insert into city (postal_code, name, country_id) values ('96701', 'Aiea', 5);
insert into city (postal_code, name, country_id) values ('96720', 'Hilo', 5);
insert into city (postal_code, name, country_id) values ('96734', 'Kailua', 5);
insert into city (postal_code, name, country_id) values ('96761', 'Lahaina', 5);
insert into city (postal_code, name, country_id) values ('07001', 'Avenel', 6);
insert into city (postal_code, name, country_id) values ('07002', 'Bayonne', 6);
insert into city (postal_code, name, country_id) values ('07003', 'Bloomfield', 6);
insert into city (postal_code, name, country_id) values ('10005', 'New York', 7);
insert into city (postal_code, name, country_id) values ('10502', 'Ardsley', 7);
insert into city (postal_code, name, country_id) values ('10506', 'Bedford', 7);

insert into address (address, city_id, longitude, latitude) values ('Valley St ', 6, 34.06471951699135, -118.27002914663697)

insert into role (name) values ('ROLE_CLIENT')
insert into role (name) values ('ROLE_BOAT_OWNER')
insert into role (name) values ('ROLE_INSTRUCTOR')
insert into role (name) values ('ROLE_HOUSE_OWNER')
insert into role (name) values ('ROLE_ADMIN')
insert into users (id, first_name, last_name, email, password, phone_number, address_id, role_id, is_enabled, is_deleted) values (nextval('users_id_seq'), 'Nikola', 'Nikolic', 'niki@gmail.com', '$2a$10$5lObVsz.nPpAxIUNInrYE.0qiwKMc1yFBYekTOqJ2sBhnYvmxOEv.', '12345', 1, 5, true, false);

insert into loyalty_config (loyalty_type, buyer_min_points, seller_min_points, discount, extra_revenue) values (0, 100, 200, 0.5, 0.5)
insert into loyalty_config (loyalty_type, buyer_min_points, seller_min_points, discount, extra_revenue) values (1, 200, 200, 0.5, 0.5)
insert into loyalty_config (loyalty_type, buyer_min_points, seller_min_points, discount, extra_revenue) values (2, 300, 200, 0.5, 0.5)
insert into loyalty_config (loyalty_type, buyer_min_points, seller_min_points, discount, extra_revenue) values (3, 400, 200, 0.5, 0.5)

insert into global_config (system_fee, buyer_points_per_reservation, seller_points_per_reservation) values (1000.0, 20, 20)