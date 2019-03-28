CREATE TABLE revinfo
(
  rev BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
  revtstmp BIGINT
);

CREATE TABLE user_AUD
(
  id BIGINT AUTO_INCREMENT NOT NULL,
  rev BIGINT NOT NULL,
  revtype SMALLINT,
  name VARCHAR(255),
  email VARCHAR(255),
  is_admin BOOL,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES revinfo (rev)
);

CREATE TABLE products_AUD
(
  id BIGINT AUTO_INCREMENT NOT NULL,
  rev BIGINT NOT NULL,
  revtype SMALLINT,
  name VARCHAR(255),
  quantity BIGINT,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES revinfo (rev)
);

CREATE TABLE shipments_AUD
(
  id BIGINT AUTO_INCREMENT NOT NULL,
  rev BIGINT NOT NULL,
  revtype SMALLINT,
  quantity            BIGINT,
  arrival             VARCHAR(255),
  best_before         VARCHAR(255),
  bulk_product_id     BIGINT,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES revinfo (rev)
);

CREATE TABLE items_AUD
(
  id BIGINT AUTO_INCREMENT NOT NULL,
  rev BIGINT NOT NULL,
  revtype SMALLINT,
  product_number      VARCHAR(255),
  identified_product_id     BIGINT,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES revinfo (rev)
);

