INSERT INTO products (id, name, quantity, dtype)
VALUES(1, 'testProduct1', 10,'BulkProduct'),
      (2, 'testProduct2', 10, 'IdentifiedProduct');

INSERT INTO orders (id, name, date)
VALUES(1, 'testName1', '2018-10-10'),
      (2, 'testName2', '2018-10-10');

INSERT INTO ordered_products (id, product_name, quantity, order_id)
VALUES(1, 'testProduct1', 5, 1),
      (2, 'testProduct2', 5, 2);

INSERT INTO items (id, product_number, identified_product_id)
VALUES(1, 1111000, 2);

INSERT INTO shipments (id, quantity, arrival, best_before, bulk_product_id)
VALUES(1, 10, '2019-03-12', '2020-01-01', 1);

INSERT INTO users (id, email, name, is_admin, image_url, created, last_login, login_type)
VALUES(1, 'user@test.hu', 'testUser', 0, 'noURL', '1000-01-01 00:00:00', '1000-01-01 00:00:00', NULL),
      (2, 'admin@test.hu', 'testAdmin', 1, 'noURL', '1000-01-01 00:00:00', '1000-01-01 00:00:00', NULL);