CREATE TABLE identified_products (
  id                  BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  name                VARCHAR(255)
);

CREATE TABLE bulk_products (
  id                  BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  name                VARCHAR(255)
);

CREATE TABLE products (
  id                  BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  name                VARCHAR(255),
  quantity            BIGINT,
  dtype               VARCHAR(255)
);

CREATE TABLE items (
  id                  BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  product_number      VARCHAR(255),
  identified_product_id     BIGINT,
  FOREIGN KEY (identified_product_id) REFERENCES identified_products(id)
);

CREATE TABLE shipments (
  id                  BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  quantity            BIGINT,
  arrival             VARCHAR(255),
  best_before         VARCHAR(255),
  bulk_product_id     BIGINT,
  FOREIGN KEY (bulk_product_id) REFERENCES bulk_products(id)
);

CREATE TABLE orders (
  id                  BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  name                VARCHAR(255),
  date                VARCHAR(255)
);

CREATE TABLE product_for_order (
  id                  BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  product_name            VARCHAR(255),
  quantity                BIGINT,
  order_id            BIGINT,
  FOREIGN KEY (order_id) REFERENCES orders(id)
);
