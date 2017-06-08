# --- !Ups

CREATE TABLE asset (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(2000),
  last_access_by VARCHAR(255),
  last_access_date DATE,
  CONSTRAINT pk_asset_id PRIMARY KEY (id)
);

CREATE TABLE asset_log (
  id BIGINT NOT NULL AUTO_INCREMENT,
  asset_id BIGINT NOT NULL,
  username VARCHAR(255),
  access_date DATE,
  CONSTRAINT pk_asset_log_id PRIMARY KEY (id),
  CONSTRAINT fk_asset_id FOREIGN KEY (asset_id) REFERENCES asset(id)
);


# --- !Downs

DROP TABLE IF EXISTS asset_log;

DROP TABLE IF EXISTS asset;
