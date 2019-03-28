INSERT INTO products
  (name, quantity, dtype) VALUES
  ('Orange', 50, 'BulkProduct'),
  ('Laptop DX-50', 3, 'IdentifiedProduct');

INSERT INTO items
  (product_number, identified_product_id) VALUES
  (32432423, (SELECT id FROM products WHERE name = 'Laptop DX-50')),
  (12345664, (SELECT id FROM products WHERE name = 'Laptop DX-50')),
  (43534534, (SELECT id FROM products WHERE name = 'Laptop DX-50'));

INSERT INTO shipments
  (quantity, arrival, best_before, bulk_product_id) VALUES
  (50, 2019-03-15, 2019-03-30, (SELECT id FROM products WHERE name = 'Orange')),
  (30, 2019-03-25, 2019-04-10, (SELECT id FROM products WHERE name = 'Orange'));
