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

insert into address (address, city_id, longitude, latitude) values ('Pleasant Harbor Marina', 1, 33.866275879927, -112.3172382609774)
insert into address (address, city_id, longitude, latitude) values ('Fort McDowell', 2, 33.57656632098998, -111.53590502406543)
insert into address (address, city_id, longitude, latitude) values ('Santa Monica Pier', 8, 34.00912078128916, -118.4983513137427)
insert into address (address, city_id, longitude, latitude) values ('405 Davis St', 15, 30.311987258497282, -81.39677457226082)
insert into address (address, city_id, longitude, latitude) values ('Anastacia Cir', 17, 30.30770982214547, -86.0029552269471)
insert into address (address, city_id, longitude, latitude) values ('Old Stuart Rd', 22, 20.92030682542084, -156.68111631702078)
insert into address (address, city_id, longitude, latitude) values ('Hilo Yacht Club', 20, 19.73707400557101, -155.03370000211694)
insert into address (address, city_id, longitude, latitude) values ('Isles Hilo Bay', 20, 19.725484956369332, -155.07117921320418)
insert into address (address, city_id, longitude, latitude) values ('McGrew Point', 19, 21.375305209851334, -157.94056272729537)

insert into role (name) values ('ROLE_CLIENT')
insert into role (name) values ('ROLE_BOAT_OWNER')
insert into role (name) values ('ROLE_INSTRUCTOR')
insert into role (name) values ('ROLE_HOUSE_OWNER')
insert into role (name) values ('ROLE_ADMIN')
insert into users (id, first_name, last_name, email, password, phone_number, address_id, role_id, profile_image, is_enabled, is_deleted) values (nextval('users_id_seq'), 'Clementine', 'Charles', 'admin@gmail.com', '$2a$10$t5B4Vu20.u//zjDP2IU4kOR49tqzbUo9WRVQ50rV1Og3FxBsioG2C', '+1 (359) 824-2578', 1, 5, '213dsa214edw132.jpg', true, false);
insert into users (id, first_name, last_name, email, password, phone_number, address_id, role_id, profile_image, is_enabled, is_deleted) values (nextval('users_id_seq'), 'Aphrodite', 'Sellers', 'boat-seller@gmail.com', '$2a$10$qonYFJh8vYljXXcoih76ke2fG8PMx1r18rdqpodqfgwMJR6njA3Xq', '+1 (655) 143-1354', 2, 2, 'masiofu218h421ij.jpg', true, false);
insert into users (id, first_name, last_name, email, password, phone_number, address_id, role_id, profile_image, is_enabled, is_deleted) values (nextval('users_id_seq'), 'Ursa', 'Foster', 'house-seller@gmail.com', '$2a$10$CsCg4yGBinBwzr2KnOMftuBvERAwk86nnhMmOyhyb97rZpetWCQDK', '+1 (344) 589-9773', 3, 4, 'a1312klfsad87124j.jpg', true, false);
insert into users (id, first_name, last_name, email, password, phone_number, address_id, role_id, profile_image, is_enabled, is_deleted) values (nextval('users_id_seq'), 'Farrah', 'Wilkins', 'instructor-seller@gmail.com', '$2a$10$Rg7YmVRX6KoLBqtNRsonOO2bMcM4I/FxR80avrUuwWiB6Q7tbnthG', '+1 (109) 862-3819', 4, 3, '44124a3148iZJm4.jpg', true, false);
insert into users (id, first_name, last_name, email, password, phone_number, address_id, role_id, profile_image, is_enabled, is_deleted) values (nextval('users_id_seq'), 'Alexander', 'Sparks', 'client@gmail.com', '$2a$10$l45eAw.KTerds3fxaaIKMuGssB5dTZ9GXb7hTiSShgN18faN/F1C.', '+1 (327) 206-3487', 5, 1, '4WOWSXxIU8iZJm4.jpg', true, false);


insert into loyalty_config (loyalty_type, buyer_min_points, seller_min_points, discount, extra_revenue) values (0, 100, 200, 0.5, 0.5)
insert into loyalty_config (loyalty_type, buyer_min_points, seller_min_points, discount, extra_revenue) values (1, 200, 200, 0.5, 0.5)
insert into loyalty_config (loyalty_type, buyer_min_points, seller_min_points, discount, extra_revenue) values (2, 300, 200, 0.5, 0.5)
insert into loyalty_config (loyalty_type, buyer_min_points, seller_min_points, discount, extra_revenue) values (3, 400, 200, 0.5, 0.5)

insert into global_config (system_fee, buyer_points_per_reservation, seller_points_per_reservation) values (1000.0, 20, 20)

insert into rule (description, type) values ('No smoking', 'boat')
insert into rule (description, type) values ('No trash in water', 'boat')
insert into rule (description, type) values ('Be cautious of other boaters', 'boat')
insert into rule (description, type) values ('Avoid restricted areas', 'boat')
insert into rule (description, type) values ('Always have a lookout', 'boat')
insert into rule (description, type) values ('Don’t go into shallow waters', 'boat')
insert into rule (description, type) values ('Don’t go too fast', 'boat')

insert into rule (description, type) values ('Rods must comply with sporting ethics and customs', 'fishingLesson')
insert into rule (description, type) values ('Rod tip must be a minimum of 40 inches in length', 'fishingLesson')
insert into rule (description, type) values ('All catch must be returned to water', 'fishingLesson')
insert into rule (description, type) values ('Be respectful to other fishermen', 'fishingLesson')
insert into rule (description, type) values ('No trash in water', 'fishingLesson')
insert into rule (description, type) values ('Must use natural bait', 'fishingLesson')
insert into rule (description, type) values ('No shouting', 'fishingLesson')

insert into rule (description, type) values ('No smoking allowed', 'house')
insert into rule (description, type) values ('No parties or events allowed', 'house')
insert into rule (description, type) values ('No pets allowed', 'house')
insert into rule (description, type) values ('No unregistered guests allowed', 'house')
insert into rule (description, type) values ('Please don’t eat or drink in the bedrooms', 'house')
insert into rule (description, type) values ('Please respect the noise curfew', 'house')
insert into rule (description, type) values ('Please turn off the AC when you go out', 'house')
insert into rule (description, type) values ('Please respect check-in and check-out times', 'house')

insert into equipment (type, name) values ('navigation', 'GPS System')
insert into equipment (type, name) values ('navigation', 'VHF')
insert into equipment (type, name) values ('navigation', 'Marine Radar')
insert into equipment (type, name) values ('navigation', 'Fishfinder')
insert into equipment (type, name) values ('fishing', 'Fishing Rod')
insert into equipment (type, name) values ('fishing', 'Fishing net')
insert into equipment (type, name) values ('fishing', 'Lures')
insert into equipment (type, name) values ('fishing', 'Gloves')
insert into equipment (type, name) values ('fishing', 'Fridge')
insert into equipment (type, name) values ('fishing', 'Knives')

insert into entity (id, name, description, address_id, price_per_day, cancellation_fee, is_deleted, owner_id) values (nextval('entity_id_seq'), 'Lake Pleasant', 'Located in the northwest Valley about 45 minutes from downtown Phoenix, Lake Pleasant is always bustling with a variety of water sports and outdoor enthusiasts. The 10,000-acre lake is enjoyed by windsurfers, boaters, sailors, water and jet skiers, and fishermen. Lake Pleasant Regional Park also offers camping and trails for mountain biking and hiking.', 1, 72.0, 10, false, 4)
insert into fishing_lesson (id, instructor_biography, max_number_of_people) values (1, 'Farrah Wilkins is a fishing instructor with eight years of experience working alongside the best fisherman''s in the world. Farrah is an outgoing person, looking to make people smile and that''s why he embarked on a Fishbook journey in 2016. Every day he goes on adventures and create smiles on people''s faces.', 5)
insert into entity_image (name, priority, entity_id) values ('1.jpg', 1, 1)
insert into entity_image (name, priority, entity_id) values ('1-1.jpg', 2, 1)

insert into entity (id, name, description, address_id, price_per_day, cancellation_fee, is_deleted, owner_id) values (nextval('entity_id_seq'), 'Saguaro Lake', 'A hidden gem only 40 minutes from Phoenix. These views are absolutely amazing. In China there is a saying ''where there are mountains, where there is water''. Sunset here is extra vibrant because the water further reflects the sun''s rays onto the mountainside and cliffs.', 2, 55.0, 5, false, 4)
insert into fishing_lesson (id, instructor_biography, max_number_of_people) values (2, 'Farrah Wilkins is a fishing instructor with eight years of experience working alongside the best fisherman''s in the world. Farrah is an outgoing person, looking to make people smile and that''s why he embarked on a Fishbook journey in 2016. Every day he goes on adventures and create smiles on people''s faces.', 3)
insert into entity_image (name, priority, entity_id) values ('2.jpg', 1, 2)
insert into entity_image (name, priority, entity_id) values ('2-1.jpg', 2, 2)

insert into entity (id, name, description, address_id, price_per_day, cancellation_fee, is_deleted, owner_id) values (nextval('entity_id_seq'), 'Santa Monica Pier Surfing', 'Don''t wait another day to start planning your trip to southern California and Santa Monica. Not only can you get your fill of surfing, there’s also plenty of other things to do and places to see. After hitting up these Santa Monica surf spots, why not stop by Big Dean’s Ocean Front Cafe for a cold drink and some lunch or dinner. Big Dean’s blog is also full of information on things to do and places to visit in Santa Monica.', 3, 68.0, 15, false, 4)
insert into fishing_lesson (id, instructor_biography, max_number_of_people) values (3, 'Farrah Wilkins is a fishing instructor with eight years of experience working alongside the best fisherman''s in the world. Farrah is an outgoing person, looking to make people smile and that''s why he embarked on a Fishbook journey in 2016. Every day he goes on adventures and create smiles on people''s faces.', 9)
insert into entity_image (name, priority, entity_id) values ('3.jpg', 1, 3)
insert into entity_image (name, priority, entity_id) values ('3-1.jpg', 2, 3)
insert into entity_image (name, priority, entity_id) values ('3-2.jpg', 3, 3)

insert into entity (id, name, description, address_id, price_per_day, cancellation_fee, is_deleted, owner_id) values (nextval('entity_id_seq'), 'Atlantic''s Bliss', 'Situated in Jacksonville in Florida, 100 meters from the beach, the Nature''s Bliss house features a sun terrace and views of the ocean. There is a cuisine with outside seating and free WiFi is provided throughout the property.', 4, 200.0, 15, false, 3)
insert into house (id, max_number_of_people) values (4, 4)
insert into room (num_of_beds, house_id) values (2, 4)
insert into room (num_of_beds, house_id) values (2, 4)
insert into entity_image (name, priority, entity_id) values ('4.jpg', 1, 4)

insert into entity (id, name, description, address_id, price_per_day, cancellation_fee, is_deleted, owner_id) values (nextval('entity_id_seq'), 'Panama''s Creek House', 'Located in Panama City, 250 meters from the beach, the Panama''s Creek House features amazing views of the ocean and has many casinos around. If you need serious rest, and break from the normal world, you should definitely visit.', 5, 180.0, 15, false, 3)
insert into house (id, max_number_of_people) values (5, 3)
insert into room (num_of_beds, house_id) values (2, 5)
insert into room (num_of_beds, house_id) values (1, 5)
insert into entity_image (name, priority, entity_id) values ('5.jpg', 1, 5)

insert into entity (id, name, description, address_id, price_per_day, cancellation_fee, is_deleted, owner_id) values (nextval('entity_id_seq'), 'Hawaii Paradise', 'Somewhere in Hawaii, these is a magical house Hawaii Paradise that features amazing scenery and jungle vibes. Ocean is nearby if you want to take a swim and there are free surf boards in the house.', 6, 150.0, 10, false, 4)
insert into house (id, max_number_of_people) values (6, 3)
insert into room (num_of_beds, house_id) values (2, 6)
insert into room (num_of_beds, house_id) values (2, 6)
insert into entity_image (name, priority, entity_id) values ('6.jpg', 1, 6)

insert into entity (id, name, description, address_id, price_per_day, cancellation_fee, is_deleted, owner_id) values (nextval('entity_id_seq'), 'Godzilla 3.1', 'Great fast boat with barbecue grill and three fridges. Good for long sailing across the sea.', 7, 120.0, 3, false, 2)
insert into boats (id, boat_type, max_number_of_people, energy_consumption, fuel_consumption, boat_length, load_capacity, max_distance, max_speed, motors, power) values (7, 'SMALL_YACHT', 4, 10, 32, 11.0, 2000, 30, 210, 3, 1200)
insert into entity_image (name, priority, entity_id) values ('7.jpg', 1, 7)

insert into entity (id, name, description, address_id, price_per_day, cancellation_fee, is_deleted, owner_id) values (nextval('entity_id_seq'), 'Pirates Dream', 'Big yacht for long cruises. Has amazing grill and many fridges. Great for parties. Once you rent this big boy, you''ll never want to rent anything else.', 8, 220.0, 15, false, 2)
insert into boats (id, boat_type, max_number_of_people, energy_consumption, fuel_consumption, boat_length, load_capacity, max_distance, max_speed, motors, power) values (8, 'BIG_YACHT', 20, 30, 54, 21.0, 11500, 200, 220, 8, 3900)
insert into entity_image (name, priority, entity_id) values ('8.jpg', 1, 8)
insert into entity_image (name, priority, entity_id) values ('8-1.jpg', 2, 8)
insert into entity_image (name, priority, entity_id) values ('8-2.jpg', 3, 8)

insert into entity (id, name, description, address_id, price_per_day, cancellation_fee, is_deleted, owner_id) values (nextval('entity_id_seq'),'Diamond', 'Like the name suggests, this boat is like a diamond in the sea. If you are a couple this is the perfect boat for you.', 9, 110.0, 10, false, 2)
insert into boats (id, boat_type, max_number_of_people, energy_consumption, fuel_consumption, boat_length, load_capacity, max_distance, max_speed, motors, power) values (9, 'CABIN_CRUISER', 2, 8, 20, 6.0, 1500, 60, 120, 2, 1000)
insert into entity_image (name, priority, entity_id) values ('9.jpg', 1, 9)
