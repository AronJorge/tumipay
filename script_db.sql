DROP TABLE IF EXISTS payins CASCADE;
DROP TABLE IF EXISTS accounts CASCADE;
DROP TABLE IF EXISTS payment_methods CASCADE;
DROP TABLE IF EXISTS payment_providers CASCADE;
DROP TABLE IF EXISTS customers CASCADE;

CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    document_number VARCHAR(20) UNIQUE NOT NULL,
    document_type VARCHAR(20),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payment_providers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    api_endpoint VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE payment_methods (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL, 
    type VARCHAR(20) NOT NULL, 
    payment_provider_id BIGINT NOT NULL,
    CONSTRAINT fk_payment_method_provider FOREIGN KEY (payment_provider_id) REFERENCES payment_providers(id)
);

CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(50) UNIQUE NOT NULL,
    currency VARCHAR(3) NOT NULL,
    account_type VARCHAR(20),
    customer_id BIGINT NOT NULL,
    CONSTRAINT fk_account_customer FOREIGN KEY (customer_id) REFERENCES customers(id)
);

CREATE TABLE payins (
    id UUID PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    payment_method_id BIGINT NOT NULL,
    reference_code VARCHAR(255) UNIQUE NOT NULL,
    amount NUMERIC(19, 2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    status VARCHAR(20) NOT NULL, 
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    CONSTRAINT fk_payin_customer FOREIGN KEY (customer_id) REFERENCES customers(id),
    CONSTRAINT fk_payin_account FOREIGN KEY (account_id) REFERENCES accounts(id),
    CONSTRAINT fk_payin_method FOREIGN KEY (payment_method_id) REFERENCES payment_methods(id)
);

CREATE INDEX idx_payins_customer ON payins(customer_id);
CREATE INDEX idx_payins_reference ON payins(reference_code);
CREATE INDEX idx_customers_document ON customers(document_number);

