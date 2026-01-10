package com.tumipay.domain.model;

import com.tumipay.infrastructure.adapter.input.rest.constant.MessageConstants;

import java.math.BigDecimal;
import java.util.Currency;

public record Money(BigDecimal amount, String currency) {
    public Money {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(MessageConstants.Validation.MONEY_AMOUNT_NEGATIVE);
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException(MessageConstants.Validation.MONEY_CURRENCY_REQUIRED);
        }
        try {
            Currency.getInstance(currency);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(MessageConstants.Validation.MONEY_CURRENCY_INVALID);
        }
    }

    public static Money of(BigDecimal amount, String currency) {
        return new Money(amount, currency);
    }
}
