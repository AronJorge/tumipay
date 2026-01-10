package com.tumipay.infrastructure.adapter.input.rest.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

import com.tumipay.infrastructure.adapter.input.rest.constant.MessageConstants;

public record PayInRequest(
                @NotNull(message = MessageConstants.Validation.PAYIN_CUSTOMER_ID_REQUIRED) @Positive(message = MessageConstants.Validation.PAYIN_CUSTOMER_ID_POSITIVE) Long customerId,

                @NotNull(message = MessageConstants.Validation.PAYIN_ACCOUNT_ID_REQUIRED) @Positive(message = MessageConstants.Validation.PAYIN_ACCOUNT_ID_POSITIVE) Long accountId,

                @NotNull(message = MessageConstants.Validation.PAYIN_PAYMENT_METHOD_ID_REQUIRED) @Positive(message = MessageConstants.Validation.PAYIN_PAYMENT_METHOD_ID_POSITIVE) Long paymentMethodId,

                @NotNull(message = MessageConstants.Validation.PAYIN_AMOUNT_REQUIRED) @DecimalMin(value = "0.01", message = MessageConstants.Validation.PAYIN_AMOUNT_MIN) BigDecimal amount,

                @NotNull(message = MessageConstants.Validation.PAYIN_CURRENCY_REQUIRED) @Pattern(regexp = "[A-Z]{3}", message = MessageConstants.Validation.PAYIN_CURRENCY_PATTERN) String currency) {
}
