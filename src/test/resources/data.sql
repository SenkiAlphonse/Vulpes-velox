INSERT INTO products
  (name, quantity, dtype) VALUES
  ('NameTaken', 3,'BulkProduct'),
  ('NameTaken2', 3,'BulkProduct'),
  ('NameTaken3', 4, 'IdentifiedProduct'),
  ('NameTaken4', 4, 'IdentifiedProduct');

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
  (11111111, (SELECT id FROM products WHERE name = 'NameTaken3'));

INSERT INTO shipments
  (quantity, arrival, best_before, bulk_product_id) VALUES
  (5, '2019-03-10', '2019-04-20', (SELECT id FROM products WHERE name = 'NameTaken'));

INSERT INTO users
  (email, name, is_admin, image_url, created, last_login, login_type) VALUES
  ('email', 'user', true, 'img', '2019-03-03', '2019-03-18', 'loginType');