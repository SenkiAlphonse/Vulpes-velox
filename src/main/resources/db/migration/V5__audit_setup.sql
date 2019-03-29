CREATE TABLE revinfo
(
  rev BIGSERIAL PRIMARY KEY NOT NULL,
  revtstmp BIGINT
);

CREATE TABLE users_AUD
(
  id BIGSERIAL NOT NULL,
  rev BIGINT NOT NULL,
  revtype SMALLINT,
  name VARCHAR(255),
  email VARCHAR(255),
  image_url           VARCHAR(255),
  is_admin BOOL,
  created             VARCHAR(255),
  last_login          VARCHAR(255),
  login_type          VARCHAR(255),
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES revinfo (rev)
);

CREATE TABLE products_AUD
(
  id BIGSERIAL NOT NULL,
  rev BIGINT NOT NULL,
  revtype SMALLINT,
  name VARCHAR(255),
  quantity BIGINT,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES revinfo (rev)
);

CREATE TABLE shipments_AUD
(
  id BIGSERIAL NOT NULL,
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
  id BIGSERIAL NOT NULL,
  rev BIGINT NOT NULL,
  revtype SMALLINT,
  product_number      VARCHAR(255),
  identified_product_id     BIGINT,
  PRIMARY KEY (id, rev),
  FOREIGN KEY (rev) REFERENCES revinfo (rev)
);
