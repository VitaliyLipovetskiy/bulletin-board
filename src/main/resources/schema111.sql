DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS USERS;
-- DROP TABLE IF EXISTS meals;
-- DROP TABLE IF EXISTS restaurants;
-- DROP TABLE IF EXISTS voting;

CREATE TABLE users (
    id               IDENTITY PRIMARY KEY,
--     name             VARCHAR                           NOT NULL,
    email            VARCHAR                           NOT NULL,
    password         VARCHAR                           NOT NULL,
    registered       TIMESTAMP           DEFAULT now() NOT NULL,
    enabled          BOOL                DEFAULT TRUE  NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles (
    user_id INTEGER NOT NULL,
    role    VARCHAR NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- CREATE TABLE restaurants (
--     id                  IDENTITY PRIMARY KEY,
--     name                VARCHAR   NOT NULL,
--     user_id             INTEGER   NOT NULL,
--     FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
-- );
--
-- CREATE TABLE meals (
--     id                  IDENTITY PRIMARY KEY,
--     name                VARCHAR   NOT NULL,
--     restaurant_id       INTEGER   NOT NULL,
--     date                DATE DEFAULT now() NOT NULL,
--     FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
-- );
-- -- CREATE UNIQUE INDEX meals_unique_user_datetime_idx ON meals (user_id, date_time);
--
-- CREATE TABLE voting (
--     date                DATE        NOT NULL,
--     user_id             INTEGER     NOT NULL,
--     restaurant_id       INTEGER     NOT NULL,
--     CONSTRAINT user_date UNIQUE (date, user_id)
-- --     FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
-- );