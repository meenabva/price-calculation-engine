-- Sample seed data for pricing-engine
-- Inserts for pricing_driver, product, and pricing_config

-- Pricing drivers
INSERT INTO pricing_driver (id, name) VALUES (1, 'BASE');
INSERT INTO pricing_driver (id, name) VALUES (2, 'SEASONAL');
INSERT INTO pricing_driver (id, name) VALUES (3, 'PROMO');

-- Products
INSERT INTO product (id, name, type, base_price) VALUES (1, 'Widget A', 'STANDARD', 19.99);
INSERT INTO product (id, name, type, base_price) VALUES (2, 'Gadget B', 'PREMIUM', 49.95);
INSERT INTO product (id, name, type, base_price) VALUES (3, 'T-Shirt', 'APPAREL', 15.00);

-- Pricing configs (maps product_type -> pricing_driver + weightage)
-- Note: column names follow Hibernate's default physical naming (productType -> product_type)
INSERT INTO pricing_config (id, product_type, pricing_driver_id, weightage) VALUES (1, 'STANDARD', 1, 1.00);
INSERT INTO pricing_config (id, product_type, pricing_driver_id, weightage) VALUES (2, 'PREMIUM', 2, 1.25);
INSERT INTO pricing_config (id, product_type, pricing_driver_id, weightage) VALUES (3, 'APPAREL', 3, 0.90);

