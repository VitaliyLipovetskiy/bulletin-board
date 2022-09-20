INSERT INTO USERS (email, password)
VALUES ('user@yandex.ru', '{noop}password'),
       ('admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO ADS (CONTACT, DESCRIPTION, IMAGE, NAME, USER_ID)
VALUES ( 'Contact1', 'Description1', 'Image1', 'name1', 1 ),
       ( 'Contact2', 'Description2', 'Image2', 'name2', 2 ),
       ( 'Contact3', 'Description3', 'Image3', 'name3', 2 );
