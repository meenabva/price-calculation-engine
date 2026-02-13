-- Sample seed data for pricing-engine
-- Inserts for pricing_driver, product, and pricing_config

-- Pricing drivers
INSERT INTO pricing_driver (id, name) VALUES (1, 'DISCOUNT');
INSERT INTO pricing_driver (id, name) VALUES (2, 'TAX');
INSERT INTO pricing_driver (id, name) VALUES (3, 'COMMISSION');
INSERT INTO pricing_driver (id, name) VALUES (4, 'LOGISTICS');
INSERT INTO pricing_driver (id, name) VALUES (5, 'SURCHARGE');

-- Products
INSERT INTO product (id, name, type, base_price) VALUES (1, 'Widget A', 'STANDARD', 19.99);
INSERT INTO product (id, name, type, base_price) VALUES (2, 'Gadget B', 'PREMIUM', 49.95);
INSERT INTO product (id, name, type, base_price) VALUES (3, 'T-Shirt', 'APPAREL', 15.00);

-- Pricing configs (maps product_type -> pricing_driver + weightage)
-- Note: column names follow Hibernate's default physical naming (productType -> product_type)
INSERT INTO pricing_config (id, product_type, pricing_driver_id, weightage) VALUES (1, 'STANDARD', 2, 3.00);
INSERT INTO pricing_config (id, product_type, pricing_driver_id, weightage) VALUES (2, 'PREMIUM', 2, 10.00);
INSERT INTO pricing_config (id, product_type, pricing_driver_id, weightage) VALUES (3, 'APPAREL', 3, 0.90);
INSERT INTO pricing_config (id, product_type, pricing_driver_id, weightage) VALUES (4, 'PREMIUM', 3, 7.90);
INSERT INTO pricing_config (id, product_type, pricing_driver_id, weightage) VALUES (5, 'APPAREL', 1, 5);
INSERT INTO pricing_config (id, product_type, pricing_driver_id, weightage) VALUES (6, 'PREMIUM', 4, 5);
