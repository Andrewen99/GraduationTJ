DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM res_dishes;
DELETE FROM restaurants;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
('User', 'user@yandex.ru', '{noop}password'),
('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id) VALUES
('ROLE_USER', 100000),
('ROLE_ADMIN', 100001);

INSERT INTO restaurants (name) VALUES
('Tokio City Restaurant'),
('Yakitoria Restaurant'),
('Restaurant With Food');

INSERT INTO res_dishes (name, price, date, res_id) VALUES
('steak', 9,'2019-08-29', 100004),
('chicken', 11,'2019-08-29', 100004),
('california roll', 5,'2019-08-15', 100004);

INSERT INTO votes (date, user_id, res_id) VALUES
('2019-08-29', 100000, 100004),
('2019-08-29', 100001, 100004);












