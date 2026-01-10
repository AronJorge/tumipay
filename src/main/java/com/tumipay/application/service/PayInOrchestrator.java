package com.tumipay.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tumipay.domain.exception.PayInDomainException;
import com.tumipay.domain.model.PayIn;
import com.tumipay.domain.port.in.CreatePayInUseCase;
import com.tumipay.domain.port.out.AccountRepositoryPort;
import com.tumipay.domain.port.out.CustomerRepositoryPort;
import com.tumipay.domain.port.out.PayInRepositoryPort;
import com.tumipay.domain.port.out.PaymentGatewayPort;
import com.tumipay.domain.port.out.PaymentMethodRepositoryPort;
import com.tumipay.infrastructure.adapter.input.rest.constant.MessageConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayInOrchestrator implements CreatePayInUseCase, com.tumipay.domain.port.in.GetPayInUseCase {

    private final PayInRepositoryPort payInRepositoryPort;
    private final PaymentGatewayPort paymentGatewayPort;
    private final CustomerRepositoryPort customerRepositoryPort;
    private final AccountRepositoryPort accountRepositoryPort;
    private final PaymentMethodRepositoryPort paymentMethodRepositoryPort;
    private final org.springframework.context.MessageSource messageSource;

    @Override
    public PayIn execute(com.tumipay.domain.model.PayInId id) {
        return payInRepositoryPort.findById(id)
                .orElseThrow(() -> new PayInDomainException(messageSource.getMessage(
                        com.tumipay.infrastructure.adapter.input.rest.constant.MessageConstants.Errors.PAYIN_NOT_FOUND,
                        new Object[] { id.value() },
                        org.springframework.context.i18n.LocaleContextHolder.getLocale())));
    }

    @Override
    public PayIn execute(CreatePayInCommand command) {
        validateResourcesExistence(command);

        PayIn payIn = PayIn.create(
                command.customerId(),
                command.accountId(),
                command.paymentMethodId(),
                command.amount());

        payIn.validate();
        payIn = saveState(payIn);

        boolean success = false;
        try {
            success = paymentGatewayPort.processPayment(payIn);
        } catch (Exception e) {
            log.error(MessageConstants.Logs.ERROR_GATEWAY_CONNECTION, payIn.getId().value(), e);
        }

        if (success) {
            payIn.process();
        } else {
            String errorMessage = messageSource.getMessage(
                    MessageConstants.Errors.GATEWAY_REJECTED,
                    null,
                    org.springframework.context.i18n.LocaleContextHolder.getLocale());
            payIn.fail(errorMessage);
        }

        PayIn savedPayIn = saveState(payIn);

        return savedPayIn;
    }

    @Transactional
    protected PayIn saveState(PayIn payIn) {
        return payInRepositoryPort.save(payIn);
    }

    private void validateResourcesExistence(CreatePayInCommand command) {
        java.util.Locale locale = org.springframework.context.i18n.LocaleContextHolder.getLocale();

        if (!customerRepositoryPort.existsById(command.customerId())) {
            String msg = messageSource.getMessage(MessageConstants.Errors.CUSTOMER_NOT_FOUND,
                    new Object[] { command.customerId() }, locale);
            throw new PayInDomainException(msg);
        }
        if (!accountRepositoryPort.existsById(command.accountId())) {
            String msg = messageSource.getMessage(MessageConstants.Errors.ACCOUNT_NOT_FOUND,
                    new Object[] { command.accountId() },
                    locale);
            throw new PayInDomainException(msg);
        }
        if (!paymentMethodRepositoryPort.existsById(command.paymentMethodId())) {
            String msg = messageSource.getMessage(MessageConstants.Errors.PAYMENT_METHOD_NOT_FOUND,
                    new Object[] { command.paymentMethodId() }, locale);
            throw new PayInDomainException(msg);
        }
    }
}