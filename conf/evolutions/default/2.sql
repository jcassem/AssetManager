# --- !Ups

INSERT INTO asset(name) VALUES ('Buildings');
INSERT INTO asset(name) VALUES ('Cash on deposit');
INSERT INTO asset(name) VALUES ('Cash on hand');
INSERT INTO asset(name) VALUES ('Certificates of deposit or CDs');
INSERT INTO asset(name) VALUES ('Commercial paper');
INSERT INTO asset(name) VALUES ('Corporate bonds');
INSERT INTO asset(name) VALUES ('Corporate stock');
INSERT INTO asset(name) VALUES ('Debentures held');
INSERT INTO asset(name) VALUES ('Equipment');
INSERT INTO asset(name) VALUES ('Federal agency securities');
INSERT INTO asset(name) VALUES ('Federal treasury notes');
INSERT INTO asset(name) VALUES ('Guaranteed investment accounts');
INSERT INTO asset(name) VALUES ('Inventory');
INSERT INTO asset(name) VALUES ('Land');
INSERT INTO asset(name) VALUES ('Loans to members of insurance trusts systems');
INSERT INTO asset(name) VALUES ('Loans receivables');
INSERT INTO asset(name) VALUES ('Marketable equity securities');
INSERT INTO asset(name) VALUES ('Marketable securities');
INSERT INTO asset(name) VALUES ('Money market funds');
INSERT INTO asset(name) VALUES ('Mortgages (receivable) held directly');
INSERT INTO asset(name) VALUES ('Mutual funds');
INSERT INTO asset(name) VALUES ('Notes receivables');
INSERT INTO asset(name) VALUES ('Repurchase agreements');
INSERT INTO asset(name) VALUES ('"Restricted" cash and investments');
INSERT INTO asset(name) VALUES ('Savings accounts');
INSERT INTO asset(name) VALUES ('Share of funds in governmental investment accounts or pools');
INSERT INTO asset(name) VALUES ('State and local government securities');
INSERT INTO asset(name) VALUES ('Time deposits');
INSERT INTO asset(name) VALUES ('Warrants (to purchase securities)');

INSERT INTO asset_log (asset_id, username, access_date)
  SELECT id, 'admin_user', CURRENT_DATE() - 3
  FROM asset;

INSERT INTO asset_log (asset_id, username, access_date)
  SELECT id, 'admin_user', CURRENT_DATE() - 2
  FROM asset;

INSERT INTO asset_log (asset_id, username, access_date)
  SELECT id, 'admin_user', CURRENT_DATE() - 1
  FROM asset;

UPDATE asset SET last_access_by='admin_user', last_access_date=CURRENT_DATE() - 1;


# --- !Downs

DELETE FROM asset_log;
DELETE FROM asset;
