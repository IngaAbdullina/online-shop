CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR unique NOT NULL,
    first_name VARCHAR,
    last_name VARCHAR,
    password VARCHAR,
    username VARCHAR unique NOT NULL,
    address_id BIGINT,
    role VARCHAR DEFAULT 'ROLE_USER'
);

INSERT INTO users (email, password, username, role)
VALUES ('admin@admin.com', '$2a$12$0gPEsF.welo8h4R/s8fkxuPYLTpHeps1S3gRn4OhlQ234SeZ.IoDa', 'admin', 'ROLE_ADMIN');

INSERT INTO users (email, first_name, last_name, password, username, address_id, role)
VALUES ('inga@inga.com', 'Inga', 'Abdullina','$2a$12$LtbYtEzOBdnsiD/E9Wtj2OVXhoddRh41yKDe2yfwhlNZGseqU8IlW', 'inga', 1, 'ROLE_USER');

INSERT INTO users (email, password, username, address_id, role)
VALUES ('test@test.com', '$2a$12$LtbYtEzOBdnsiD/E9Wtj2OfGw3e6wQ07vBruMctLLj5no9sJf74sy', 'test', 2, 'ROLE_USER');

CREATE TABLE IF NOT EXISTS address (
    id BIGSERIAL PRIMARY KEY,
    country VARCHAR,
    city VARCHAR,
    street_address VARCHAR,
    zip_code VARCHAR
);

INSERT INTO address (country, city, street_address, zip_code)
VALUES ('Россия', 'Санкт-Петербург', 'Съежинская ул.', '123'),
       ('Беларусь', 'Гродно', 'Ленина пр.', '344');

CREATE TABLE IF NOT EXISTS article (
    id BIGSERIAL PRIMARY KEY,
    picture VARCHAR UNIQUE,
    price FLOAT8,
    stock INT,
    title VARCHAR
);

CREATE TABLE IF NOT EXISTS brand (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR,
    article_id BIGINT
);

CREATE TABLE IF NOT EXISTS category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR,article_id BIGINT
);

CREATE TABLE IF NOT EXISTS size (
    id BIGSERIAL PRIMARY KEY,
    value VARCHAR,
    article_id BIGINT
);

CREATE TABLE IF NOT EXISTS cart_item (
    id BIGSERIAL PRIMARY KEY,
    quantity INT NOT NULL,
    size VARCHAR,
    article_id BIGINT,
    order_id BIGINT,
    user_id BIGINT
);

CREATE TABLE IF NOT EXISTS payment (
    id BIGSERIAL PRIMARY KEY,
    card_name VARCHAR,
    card_number VARCHAR,
    cvc INT NOT NULL,
    expiry_month INT NOT NULL,
    expiry_year INT NOT NULL,
    holder_name VARCHAR,
    type VARCHAR,
    order_id BIGINT
);

CREATE TABLE IF NOT EXISTS shipping (
    id BIGSERIAL PRIMARY KEY,
    receiver VARCHAR,
    shipping_date TIMESTAMP,
    address_id BIGINT,
    order_id BIGINT
);

CREATE TABLE IF NOT EXISTS user_order (
    id BIGSERIAL PRIMARY KEY,
    order_date TIMESTAMP,
    order_status VARCHAR,
    order_total DECIMAL(19,2),
    shipping_date TIMESTAMP,
    payment_id BIGINT,
    shipping_id BIGINT,
    user_id BIGINT
);