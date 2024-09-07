DELETE FROM user_role;
DELETE FROM users;
DELETE FROM vote;
DELETE FROM dish;
DELETE FROM restaurant;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001),
       ('USER', 100002);

INSERT INTO restaurant (name)
VALUES ('TanGen'),
       ('Myata'),
       ('Suluguni i vino');

INSERT INTO dish (created, name, price, restaurant_id)
VALUES ('2024-08-30 10:00:00', 'Баклажаны в соусе', 500, 100003),
       ('2024-08-30 10:00:00', 'Свинина Танцуй', 1000, 100003),
       ('2024-08-29 10:00:00', 'Осьминоги', 500, 100003),
       ('2024-08-30 10:00:00', 'Салат Цезарь', 100, 100004),
       ('2024-08-30 10:00:00', 'Суп грибной', 500, 100004),
       ('2024-08-29 10:00:00', 'Пюре с котлетой', 1000, 100004),
       ('2024-08-30 10:00:00', 'Сулугуни', 510, 100005),
       ('2024-08-30 10:00:00', 'Хачапури', 510, 100005),
       ('2024-08-29 10:00:00', 'Хинкали', 1500, 100005);

INSERT INTO vote (created, user_id, restaurant_id)
VALUES ('2024-08-29 10:00:00', 100000, 100003),
       ('2024-08-30 10:00:00', 100000, 100004),
       ('2024-08-29 10:00:00', 100002, 100005),
       ('2024-08-30 10:00:00', 100002, 100004);