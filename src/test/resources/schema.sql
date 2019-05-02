DROP TABLE IF EXISTS ordered_products;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS shipments;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS revinfo;
DROP TABLE IF EXISTS users_AUD;
DROP TABLE IF EXISTS products_AUD;
DROP TABLE IF EXISTS shipments_AUD;
DROP TABLE IF EXISTS items_AUD;

CREATE TABLE products (
  id       BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  name     VARCHAR(255),
  quantity BIGINT,
  price    BIGINT,
  value    BIGINT,
  unit     VARCHAR(255),
  dtype    VARCHAR(255)
);

CREATE TABLE items (
  id                    BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  product_number        VARCHAR(255),
  price                 BIGINT,
  identified_product_id BIGINT,
  FOREIGN KEY (identified_product_id) REFERENCES products (id)
);

CREATE TABLE shipments (
  id              BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  quantity        BIGINT,
  arrival         VARCHAR(255),
  best_before     VARCHAR(255),
  price           BIGINT,
  bulk_product_id BIGINT,
  FOREIGN KEY (bulk_product_id) REFERENCES products (id)
);

CREATE TABLE orders (
  id                  BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  name                VARCHAR(255),
  date                VARCHAR(255)
);

CREATE TABLE ordered_products (
  id                  BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  product_name        VARCHAR(255),
  quantity            BIGINT,
  order_id            BIGINT,
  FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE TABLE users (
  id                  BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  email               VARCHAR(255),
  name                VARCHAR(255),
  is_admin            BOOL,
  image_url           VARCHAR(255),
  created             DATETIME,
  last_login          DATETIME,
  login_type          VARCHAR(255)
);

CREATE TABLE revinfo
(
  rev BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  revtstmp BIGINT
);

CREATE TABLE users_AUD (
  id BIGINT AUTO_INCREMENT NOT NULL,
  rev BIGINT NOT NULL,
  revtype SMALLINT,
  created VARCHAR(255),
  email VARCHAR(255),
  image_url VARCHAR(255),
  is_admin BOOL,
  last_login VARCHAR(255),
  login_type VARCHAR(255),
  name VARCHAR(255),
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES revinfo (rev)
);

CREATE TABLE products_AUD (
  id BIGINT AUTO_INCREMENT NOT NULL,
  rev BIGINT NOT NULL,
  revtype SMALLINT,
  name VARCHAR(255),
  quantity BIGINT,
  price    BIGINT,
  value    BIGINT,
  unit     VARCHAR(255),
  dtype VARCHAR(255),
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES revinfo (rev)
);

CREATE TABLE shipments_AUD (
  id BIGINT AUTO_INCREMENT NOT NULL,
  rev BIGINT NOT NULL,
  revtype SMALLINT,
  quantity            BIGINT,
  price    BIGINT,
  arrival             VARCHAR(255),
  best_before         VARCHAR(255),
  bulk_product_id     BIGINT,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES revinfo (rev)
);

CREATE TABLE items_AUD (
  id BIGINT AUTO_INCREMENT NOT NULL,
  rev BIGINT NOT NULL,
  revtype SMALLINT,
  product_number      VARCHAR(255),
  price    BIGINT,
  identified_product_id     BIGINT,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES revinfo (rev)
);
