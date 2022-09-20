INSERT INTO USERS (email, password)
VALUES ('user@yandex.ru', '{noop}password'),
       ('admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);
