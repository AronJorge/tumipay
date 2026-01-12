DELETE FROM payins;
DELETE FROM accounts;
DELETE FROM payment_methods;
DELETE FROM payment_providers;
DELETE FROM customers;

ALTER SEQUENCE IF EXISTS "CUSTOMERS_ID_SEQ" RESTART WITH 1;
ALTER SEQUENCE IF EXISTS "PAYMENT_PROVIDERS_ID_SEQ" RESTART WITH 1;
ALTER SEQUENCE IF EXISTS "PAYMENT_METHODS_ID_SEQ" RESTART WITH 1;
ALTER SEQUENCE IF EXISTS "ACCOUNTS_ID_SEQ" RESTART WITH 1;

INSERT INTO customers (full_name, email, document_number, status) VALUES 
('Jorge Gutierrez', 'jorge@tumipay.com', '1098765432', 'ACTIVE'),
('Usuario Prueba', 'test@tumipay.com', '999999999', 'ACTIVE'),
('Maria Lopez', 'maria.lopez@tumipay.com', '555444333', 'ACTIVE'),
('Carlos Perez', 'carlos.perez@tumipay.com', '1122334455', 'ACTIVE');

INSERT INTO payment_providers (name, api_endpoint, is_active) VALUES 
('MockProvider', 'http://localhost:8080/mock', TRUE),
('Stripe', 'https://api.stripe.com/v1', TRUE),
('PayPal', 'https://api.paypal.com/v1', TRUE),
('MercadoPago', 'https://api.mercadopago.com/v1', FALSE);

INSERT INTO payment_methods (name, type, payment_provider_id) VALUES 
('PSE', 'DEBIT', 1),
('Visa Credit', 'CREDIT_CARD', 2),
('Mastercard', 'CREDIT_CARD', 2),
('PayPal Balance', 'WALLET', 3);

INSERT INTO accounts (account_number, currency, customer_id) VALUES 
('111-222-333', 'COP', 1),
('999-888-777', 'USD', 2),
('555-666-777', 'EUR', 3),
('888-444-222', 'COP', 4);
