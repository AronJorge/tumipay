package com.tumipay.infrastructure.adapter.input.rest.constant;

public final class RestConstants {

    private RestConstants() {
    }

    public static final class ApiPaths {
        private ApiPaths() {
        }

        public static final String PAYINS_V1 = "/v1/payins";
    }

    public static final class ResponseKeys {
        private ResponseKeys() {
        }

        public static final String TIMESTAMP = "timestamp";
        public static final String STATUS = "status";
        public static final String ERROR = "error";
        public static final String MESSAGE = "message";
        public static final String PATH = "path";
        public static final String DETAILS = "details";
        public static final String REFERENCE_CODE = "reference_code";
        public static final String AMOUNT = "amount";
        public static final String VALUE = "value";
    }

    public static final class Docs {
        private Docs() {
        }

        public static final String TITLE = "TumiPay API";
        public static final String DESCRIPTION = "API for TumiPay Payment System";
        public static final String VERSION = "v0.0.1";
    }
}