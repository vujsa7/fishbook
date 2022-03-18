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

insert into address (address, city_id, longitude, latitude) values ('20 Cooper Square', 8, 34.06471951699135, -118.27002914663697)
insert into address (address, city_id, longitude, latitude) values ('Long St 33', 2, 34.06471951699135, -118.27002914663697)
insert into address (address, city_id, longitude, latitude) values ('Valley Street 1', 24, 34.06471951699135, -118.27002914663697)
insert into address (address, city_id, longitude, latitude) values ('13th Street 3', 26, 34.06471951699135, -118.27002914663697)
insert into address (address, city_id, longitude, latitude) values ('WallStreet 7', 28, 34.06471951699135, -118.27002914663697)

insert into role (name) values ('ROLE_CLIENT')
insert into role (name) values ('ROLE_BOAT_OWNER')
insert into role (name) values ('ROLE_INSTRUCTOR')
insert into role (name) values ('ROLE_HOUSE_OWNER')
insert into role (name) values ('ROLE_ADMIN')
insert into users (id, first_name, last_name, email, password, phone_number, address_id, role_id, is_enabled, is_deleted) values (nextval('users_id_seq'), 'Clementine', 'Charles', 'admin@gmail.com', '$2a$10$t5B4Vu20.u//zjDP2IU4kOR49tqzbUo9WRVQ50rV1Og3FxBsioG2C', '+1 (359) 824-2578', 1, 5, true, false);
insert into users (id, first_name, last_name, email, password, phone_number, address_id, role_id, is_enabled, is_deleted) values (nextval('users_id_seq'), 'Aphrodite', 'Sellers', 'boat-seller@gmail.com', '$2a$10$qonYFJh8vYljXXcoih76ke2fG8PMx1r18rdqpodqfgwMJR6njA3Xq', '+1 (655) 143-1354', 2, 2, true, false);
insert into users (id, first_name, last_name, email, password, phone_number, address_id, role_id, is_enabled, is_deleted) values (nextval('users_id_seq'), 'Ursa', 'Foster', 'house-seller@gmail.com', '$2a$10$CsCg4yGBinBwzr2KnOMftuBvERAwk86nnhMmOyhyb97rZpetWCQDK', '+1 (344) 589-9773', 3, 4, true, false);
insert into users (id, first_name, last_name, email, password, phone_number, address_id, role_id, is_enabled, is_deleted) values (nextval('users_id_seq'), 'Farrah', 'Wilkins', 'instructor-seller@gmail.com', '$2a$10$Rg7YmVRX6KoLBqtNRsonOO2bMcM4I/FxR80avrUuwWiB6Q7tbnthG', '+1 (109) 862-3819', 4, 3, true, false);
insert into users (id, first_name, last_name, email, password, phone_number, address_id, role_id, is_enabled, is_deleted) values (nextval('users_id_seq'), 'Alexander', 'Sparks', 'client@gmail.com', '$2a$10$l45eAw.KTerds3fxaaIKMuGssB5dTZ9GXb7hTiSShgN18faN/F1C.', '+1 (327) 206-3487', 5, 1, true, false);


insert into loyalty_config (loyalty_type, buyer_min_points, seller_min_points, discount, extra_revenue) values (0, 100, 200, 0.5, 0.5)
insert into loyalty_config (loyalty_type, buyer_min_points, seller_min_points, discount, extra_revenue) values (1, 200, 200, 0.5, 0.5)
insert into loyalty_config (loyalty_type, buyer_min_points, seller_min_points, discount, extra_revenue) values (2, 300, 200, 0.5, 0.5)
insert into loyalty_config (loyalty_type, buyer_min_points, seller_min_points, discount, extra_revenue) values (3, 400, 200, 0.5, 0.5)

insert into global_config (system_fee, buyer_points_per_reservation, seller_points_per_reservation) values (1000.0, 20, 20)