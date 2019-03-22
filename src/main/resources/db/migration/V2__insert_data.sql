INSERT INTO users
       (email, is_god) VALUES
       ('kalendaryoii@gmail.com', false),
       ('the.nagy.kriszta@gmail.com', true),
       ('bali332211bali@gmail.com', true),
       ('nilcsi@gmail.com', true);

INSERT INTO products
       (name, quantity, dtype) VALUES
       ('name', 0, 'IdentifiedProduct'),
       ('name2', 0, 'IdentifiedProduct'),
       ('nameBulk', 0, 'BulkProduct'),
       ('nameBulk2', 0, 'BulkProduct');

INSERT INTO items
       (product_number) VALUES
       (23424),
       (324324);
