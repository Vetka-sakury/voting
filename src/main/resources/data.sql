DROP TABLE vote IF EXISTS;
DROP TABLE dish IF EXISTS;
DROP TABLE restaurant IF EXISTS;
DROP TABLE user_role IF EXISTS;
DROP TABLE users IF EXISTS;
DROP SEQUENCE global_seq IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 100000;

CREATE TABLE users
(
    id         INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name       VARCHAR(255)            NOT NULL,
    email      VARCHAR(255)            NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    enabled    BOOLEAN   DEFAULT TRUE  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON USERS (email);

CREATE TABLE user_role
(
    user_id INTEGER      NOT NULL,
    role    VARCHAR(255) NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE restaurant
(
    id      INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name    VARCHAR(255)            NOT NULL,
    created TIMESTAMP DEFAULT now() NOT NULL
);

CREATE TABLE dish
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    created       TIMESTAMP DEFAULT now() NOT NULL,
    name          VARCHAR(255)            NOT NULL,
    price         INT                     NOT NULL,
    restaurant_id INTEGER                 NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANT (id) ON DELETE CASCADE
);
CREATE INDEX restaurant_id_created_idx
    ON DISH (restaurant_id, created);

CREATE TABLE vote
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    created       TIMESTAMP DEFAULT now() NOT NULL,
    user_id       INTEGER                 NOT NULL,
    restaurant_id INTEGER                 NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES RESTAURANT (id) ON DELETE CASCADE
);
CREATE INDEX restaurant_vote_id_created_idx
    ON VOTE (restaurant_id, created);

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('User', 'user@yandex.ru', '{noop}password');

INSERT INTO user_role (role, user_id)
VALUES ('ADMIN', 100000),
       ('USER', 100001);

INSERT INTO restaurant (name)
VALUES ('TanGen'),
       ('Myata');

INSERT INTO dish (created, name, price, restaurant_id)
VALUES ('2024-08-30 10:00:00', 'Баклажаны в соусе', 500, 100002),
       ('2024-08-30 10:00:00', 'Свинина Танцуй', 1000, 100002),
       ('2024-08-30 10:00:00', 'Салат Цезарь', 100, 100003),
       ('2024-08-29 10:00:00', 'Пюре с котлетой', 1000, 100003);

INSERT INTO vote (created, user_id, restaurant_id)
VALUES ('2024-08-29 10:00:00', 100000, 100003),
       ('2024-08-30 10:00:00', 100000, 100002),
       ('2024-08-29 10:00:00', 100001, 100003),
       ('2024-08-30 10:00:00', 100001, 100002);