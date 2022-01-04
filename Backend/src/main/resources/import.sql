insert into country (code, name) values ('SRB', 'Srbija');
insert into city (postal_code, name, country_id) values ('21000', 'Novi Sad', 1);
insert into address (address, city_id, longitude, latitude) values ('Micurinova 17', 1, 0.0, 0.0)
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