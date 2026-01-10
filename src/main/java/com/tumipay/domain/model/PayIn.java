package com.tumipay.domain.model;

import com.tumipay.domain.exception.PayInDomainException;
import com.tumipay.infrastructure.adapter.input.rest.constant.MessageConstants;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class PayIn {
    private final PayInId id;
    private final Long customerId;
    private final Long accountId;
    private final Long paymentMethodId;
    private final String referenceCode;
    private final Money amount;
    private PayInStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PayIn create(Long customerId, Long accountId, Long paymentMethodId, Money amount) {
        if (amount == null)
            throw new PayInDomainException(MessageConstants.Validation.PAYIN_AMOUNT_REQUIRED);
        if (customerId == null)
            throw new PayInDomainException(MessageConstants.Validation.PAYIN_CUSTOMER_ID_REQUIRED);
        if (accountId == null)
            throw new PayInDomainException(MessageConstants.Validation.PAYIN_ACCOUNT_ID_REQUIRED);
        if (paymentMethodId == null)
            throw new PayInDomainException(MessageConstants.Validation.PAYIN_PAYMENT_METHOD_ID_REQUIRED);

        return PayIn.builder()
                .id(PayInId.random())
                .customerId(customerId)
                .accountId(accountId)
                .paymentMethodId(paymentMethodId)
                .referenceCode(UUID.randomUUID().toString())
                .amount(amount)
                .status(PayInStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void validate() {
        if (this.status != PayInStatus.CREATED) {
            throw new PayInDomainException(MessageConstants.Errors.STATE_TRANSITION_INVALID,
                    new Object[] { this.status, PayInStatus.VALIDATED });
        }

        this.status = PayInStatus.VALIDATED;
        this.updatedAt = LocalDateTime.now();
    }

    public void process() {
        if (this.status != PayInStatus.VALIDATED) {
            throw new PayInDomainException(MessageConstants.Errors.STATE_TRANSITION_INVALID,
                    new Object[] { this.status, PayInStatus.PROCESSED });
        }

        this.status = PayInStatus.PROCESSED;
        this.updatedAt = LocalDateTime.now();
    }

    public void fail(String reason) {
        if (this.status == PayInStatus.PROCESSED) {
            throw new PayInDomainException(MessageConstants.Errors.STATE_TRANSITION_INVALID,
                    new Object[] { this.status, PayInStatus.FAILED });
        }

        this.status = PayInStatus.FAILED;
        this.updatedAt = LocalDateTime.now();
    }
}