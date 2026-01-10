package com.tumipay.infrastructure.adapter.input.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PayInResponse(
        UUID id,
        Long customerId,
        Long accountId,
        Long paymentMethodId,
        String referenceCode,
        MoneyResponse amount,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
    public record MoneyResponse(BigDecimal value, String currency) {
    }
}
