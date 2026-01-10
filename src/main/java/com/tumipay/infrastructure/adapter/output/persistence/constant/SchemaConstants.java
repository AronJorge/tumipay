package com.tumipay.infrastructure.adapter.output.persistence.constant;

public final class SchemaConstants {

    private SchemaConstants() {
    }

    public static final class Tables {
        private Tables() {
        }

        public static final String ACCOUNTS = "accounts";
        public static final String CUSTOMERS = "customers";
        public static final String PAYINS = "payins";
        public static final String PAYMENT_METHODS = "payment_methods";
        public static final String PAYMENT_PROVIDERS = "payment_providers";
    }

    public static final class Columns {
        private Columns() {
        }

        public static final String CUSTOMER_ID = "customer_id";
        public static final String ACCOUNT_ID = "account_id";
        public static final String PAYMENT_METHOD_ID = "payment_method_id";
        public static final String PAYMENT_PROVIDER_ID = "payment_provider_id";

        public static final String ACCOUNT_TYPE = "account_type";
        public static final String FULL_NAME = "full_name";
        public static final String DOCUMENT_NUMBER = "document_number";
        public static final String DOCUMENT_TYPE = "document_type";

        public static final String REFERENCE_CODE = "reference_code";
        public static final String CREATED_AT = "created_at";
        public static final String UPDATED_AT = "updated_at";
        public static final String STATUS = "status";
        public static final String AMOUNT = "amount";
        public static final String CURRENCY = "currency";

        public static final String API_ENDPOINT = "api_endpoint";
        public static final String IS_ACTIVE = "is_active";
        public static final String NAME = "name";
        public static final String TYPE = "type";
    }
}