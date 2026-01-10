package com.tumipay.infrastructure.adapter.input.rest.mapper;

import com.tumipay.domain.model.Money;
import com.tumipay.domain.model.PayIn;
import com.tumipay.domain.port.in.CreatePayInUseCase.CreatePayInCommand;
import com.tumipay.infrastructure.adapter.input.rest.dto.PayInRequest;
import com.tumipay.infrastructure.adapter.input.rest.dto.PayInResponse;
import org.springframework.stereotype.Component;

@Component
public class PayInRestMapper {

    public CreatePayInCommand toCommand(PayInRequest request) {
        return new CreatePayInCommand(
                request.customerId(),
                request.accountId(),
                request.paymentMethodId(),
                Money.of(request.amount(), request.currency()));
    }

    public PayInResponse toResponse(PayIn domain) {
        return new PayInResponse(
                domain.getId().value(),
                domain.getCustomerId(),
                domain.getAccountId(),
                domain.getPaymentMethodId(),
                domain.getReferenceCode(),
                new PayInResponse.MoneyResponse(domain.getAmount().amount(), domain.getAmount().currency()),
                domain.getStatus().name(),
                domain.getCreatedAt(),
                domain.getUpdatedAt());
    }
}
