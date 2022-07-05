CREATE TABLE IF NOT EXISTS product
(
    id                    SERIAL PRIMARY KEY,
    brand                 VARCHAR(255)      NOT NULL,
    category              VARCHAR(255)      NOT NULL,
    name                  VARCHAR(255)      NOT NULL,
    flavour               VARCHAR(255)      NULL,
    calories_per_100_gram NUMERIC(5, 1)     NOT NULL,
    weight_gram           NUMERIC(5, 1)     NOT NULL
);

CREATE TABLE IF NOT EXISTS app_role
(
    id                    SERIAL PRIMARY KEY,
    name                  VARCHAR(50)       NOT NULL UNIQUE
);

INSERT INTO app_role (name)
VALUES
       ('USER'),
       ('ADMIN'),
       ('MANAGER');


CREATE TABLE IF NOT EXISTS app_user
(
    id                    SERIAL PRIMARY KEY,
    login                 VARCHAR(100)      NOT NULL UNIQUE,
    password              VARCHAR(255)      NOT NULL,
    role_id               BIGINT            NOT NULL REFERENCES app_role (id)
);
