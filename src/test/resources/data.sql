INSERT INTO products
  (name, quantity, dtype) VALUES
  ('NameTaken', 3,'BulkProduct'),
  ('NameTaken2', 4, 'IdentifiedProduct');

INSERT INTO orders
  (name, date) VALUES
  ('NameTaken', '2019-03-10'),
  ('NameTaken2', '2019-03-20');

INSERT INTO ordered_products
  (product_name, quantity, order_id) VALUES
  ('NameTaken', 2, (SELECT id FROM orders WHERE name = 'NameTaken')),
  ('NameTaken2', 7, (SELECT id FROM orders WHERE name = 'NameTaken'));

INSERT INTO items
  (product_number, identified_product_id)VALUES
  (11111111, (SELECT id FROM products WHERE name = 'NameTaken2'));

INSERT INTO shipments (id, quantity, arrival, best_before, bulk_product_id)
VALUES(1, 10, '2019-03-12', '2020-01-01', 1);

INSERT INTO users (id, email, name, is_admin, image_url, created, last_login, login_type)
VALUES(1, 'user@test.hu', 'testUser', 0, 'noURL', '1000-01-01 00:00:00', '1000-01-01 00:00:00', NULL),
      (2, 'admin@test.hu', 'testAdmin', 1, 'noURL', '1000-01-01 00:00:00', '1000-01-01 00:00:00', NULL);