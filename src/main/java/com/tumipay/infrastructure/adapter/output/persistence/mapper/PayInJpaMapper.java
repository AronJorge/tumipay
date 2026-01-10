package com.tumipay.infrastructure.adapter.output.persistence.mapper;

import com.tumipay.domain.model.Money;
import com.tumipay.domain.model.PayIn;
import com.tumipay.domain.model.PayInId;
import com.tumipay.domain.model.PayInStatus;
import com.tumipay.infrastructure.adapter.output.persistence.entity.PayInJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class PayInJpaMapper {

    public PayInJpaEntity toJpaEntity(PayIn domain) {
        if (domain == null)
            return null;
        return PayInJpaEntity.builder()
                .id(domain.getId().value())
                .customerId(domain.getCustomerId())
                .accountId(domain.getAccountId())
                .paymentMethodId(domain.getPaymentMethodId())
                .referenceCode(domain.getReferenceCode())
                .amount(domain.getAmount().amount())
                .currency(domain.getAmount().currency())
                .status(domain.getStatus().name())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    public PayIn toDomainEntity(PayInJpaEntity entity) {
        if (entity == null)
            return null;
        return PayIn.builder()
                .id(new PayInId(entity.getId()))
                .customerId(entity.getCustomerId())
                .accountId(entity.getAccountId())
                .paymentMethodId(entity.getPaymentMethodId())
                .referenceCode(entity.getReferenceCode())
                .amount(Money.of(entity.getAmount(), entity.getCurrency()))
                .status(PayInStatus.valueOf(entity.getStatus()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
