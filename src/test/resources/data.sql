INSERT INTO bulk_products
  (name) VALUES
  ('Peanuts'),
  ('Beans');

INSERT INTO orders (id, name, date)
VALUES(1, 'testName1', '2018-10-10'),
      (2, 'testName2', '2018-10-10');

INSERT INTO products (id, name, quantity, dtype)
VALUES(1, 'testProduct1', 10,'BulkProduct'),
      (2, 'testProduct2', 10, 'IdentifiedProduct');