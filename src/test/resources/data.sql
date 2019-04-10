INSERT INTO products
  (name, quantity, price, value, dtype) VALUES
  ('NameTaken', 3, 2, 1, 'BulkProduct'),
  ('NameTaken2', 3, 2, 1, 'BulkProduct'),
  ('NameTaken3', 4, 5, 6, 'IdentifiedProduct'),
  ('NameTaken4', 4, 5, 6, 'IdentifiedProduct');

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
  ('email', 'user', false, 'img', '2019-03-03', '2019-03-18', 'loginType'),
  ('email2', 'user2', true, 'img', '2019-03-03', '2019-03-18', 'loginType'),
  ('email3', 'user3', true, 'img', '2019-03-03', '2019-03-18', 'loginType'),
  ('email4', 'user4', true, 'img', '2019-03-03', '2019-03-18', 'loginType'),
  ('email5', 'user5', true, 'img', '2019-03-03', '2019-03-18', 'loginType'),
  ('email6', 'user6', true, 'img', '2019-03-03', '2019-03-18', 'loginType'),
  ('email7', 'user7', true, 'img', '2019-03-03', '2019-03-18', 'loginType'),
  ('email8', 'user8', true, 'img', '2019-03-03', '2019-03-18', 'loginType'),
  ('email9', 'user9', true, 'img', '2019-03-03', '2019-03-18', 'loginType'),
  ('email10', 'user10', true, 'img', '2019-03-03', '2019-03-18', 'loginType'),
  ('email11', 'user11', true, 'img', '2019-03-03', '2019-03-18', 'loginType'),
  ('email12', 'user12', true, 'img', '2019-03-03', '2019-03-18', 'loginType');
