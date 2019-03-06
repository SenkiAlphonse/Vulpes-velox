CREATE TABLE bulk_product_shipments (
  id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  quantity BIGINT,
  arrival DATE,
  best_before DATE,
  bulk_product_id BIGINT,
  FOREIGN KEY (bulk_product_id) REFERENCES bulk_products (id)
)
