CREATE TABLE bulk_products (
  shipments VARCHAR(255)
)

CREATE TABLE shipments (
  id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  quantity BIGINT,
  arrival DATE,
  best_before DATE,
  bulk_product_id BIGINT,
  FOREIGN KEY (bulk_product_id) REFERENCES bulk_products (id)
)

CREATE TABLE items (
  id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  product_number BIGINT
  identified_product_id BIGINT,
  FOREIGN KEY (identified_product_id) REFERENCES identified_products (id)
)

CREATE TABLE identified_products (
  items VARCHAR(255)
)

CREATE TABLE orders (

)

CREATE TABLE products (
  id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  name VARCHAR(255)
)
