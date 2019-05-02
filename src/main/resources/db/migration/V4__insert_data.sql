INSERT INTO products
  (name, quantity, price, value, unit, dtype) VALUES
  ('Orange', 80, 100, 8000, 'Piece', 'BulkProduct'),
  ('Laptop DX-50', 3, 200000, 600000, 'Piece', 'IdentifiedProduct');

INSERT INTO items
  (product_number, price, identified_product_id) VALUES
  (32432423, 200000, (SELECT id FROM products WHERE name = 'Laptop DX-50')),
  (12345664, 200000, (SELECT id FROM products WHERE name = 'Laptop DX-50')),
  (43534534, 200000, (SELECT id FROM products WHERE name = 'Laptop DX-50'));

INSERT INTO shipments
  (quantity, price, arrival, best_before, bulk_product_id) VALUES
  (50, 100, '2019-03-15', '2019-03-30', (SELECT id FROM products WHERE name = 'Orange')),
  (30, 100, '2019-03-25', '2019-04-10', (SELECT id FROM products WHERE name = 'Orange'));
