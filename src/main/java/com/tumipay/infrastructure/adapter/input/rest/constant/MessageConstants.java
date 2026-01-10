package com.tumipay.infrastructure.adapter.input.rest.constant;

public final class MessageConstants {

    private MessageConstants() {
    }

    public static final class Validation {
        private Validation() {
        }

        public static final String PAYIN_CUSTOMER_ID_REQUIRED = "{payin.customer.id.required}";
        public static final String PAYIN_CUSTOMER_ID_POSITIVE = "{payin.customer.id.positive}";

        public static final String PAYIN_ACCOUNT_ID_REQUIRED = "{payin.account.id.required}";
        public static final String PAYIN_ACCOUNT_ID_POSITIVE = "{payin.account.id.positive}";

        public static final String PAYIN_PAYMENT_METHOD_ID_REQUIRED = "{payin.payment_method.id.required}";
        public static final String PAYIN_PAYMENT_METHOD_ID_POSITIVE = "{payin.payment_method.id.positive}";

        public static final String PAYIN_AMOUNT_REQUIRED = "{payin.amount.required}";
        public static final String PAYIN_AMOUNT_MIN = "{payin.amount.min}";

        public static final String PAYIN_CURRENCY_REQUIRED = "{payin.currency.required}";
        public static final String PAYIN_CURRENCY_PATTERN = "{payin.currency.pattern}";

        public static final String MONEY_AMOUNT_NEGATIVE = "money.amount.negative";
        public static final String MONEY_CURRENCY_REQUIRED = "money.currency.required";
        public static final String MONEY_CURRENCY_INVALID = "money.currency.invalid";
    }

    public static final class Errors {
        private Errors() {
        }

        public static final String CUSTOMER_NOT_FOUND = "payin.error.customer.not_found";
        public static final String ACCOUNT_NOT_FOUND = "payin.error.account.not_found";
        public static final String PAYMENT_METHOD_NOT_FOUND = "payin.error.payment_method.not_found";
        public static final String GATEWAY_REJECTED = "payin.error.gateway.rejected";
        public static final String STATE_TRANSITION_INVALID = "payin.error.state.transition.invalid";
        public static final String AMOUNT_INVALID = "payin.error.amount.invalid";
        public static final String PAYIN_NOT_FOUND = "payin.error.not_found";
        public static final String SUCCESS_CREATED = "payin.success.created";

        public static final String GLOBAL_VALIDATION = "global.error.validation";
        public static final String GLOBAL_UNPROCESSABLE = "global.error.unprocessable";
        public static final String GLOBAL_MESSAGE_VALIDATION_FAILED = "global.message.validation_failed";
        public static final String GLOBAL_TITLE_UNPROCESSABLE = "global.error.unprocessable";
        public static final String GLOBAL_TITLE_VALIDATION = "global.error.validation";
        public static final String GLOBAL_ID_NULL = "global.error.id.null";
        public static final String GLOBAL_ID_INVALID_FORMAT = "global.error.id.invalid_format";
    }

    public static final class Defaults {
        private Defaults() {
        }

        public static final String TITLE_UNPROCESSABLE = "Unprocessable Entity";
        public static final String TITLE_VALIDATION = "Validation Error";
        public static final String MESSAGE_VALIDATION_FAILED = "Validation failed for one or more fields";
        public static final String UNEXPECTED_ERROR = "An unexpected error occurred";
    }

    public static final class Logs {
        private Logs() {
        }

        public static final String ERROR_GATEWAY_CONNECTION = "[GATEWAY_FAILURE] Error de comunicación con pasarela. PayIn ID: {}";
    }
}