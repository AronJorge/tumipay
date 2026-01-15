package com.tumipay.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.tumipay.domain.exception.PayInDomainException;
import com.tumipay.domain.model.Money;
import com.tumipay.domain.model.PayIn;
import com.tumipay.domain.port.in.CreatePayInUseCase;
import com.tumipay.domain.port.out.AccountRepositoryPort;
import com.tumipay.domain.port.out.CustomerRepositoryPort;
import com.tumipay.domain.port.out.PayInRepositoryPort;
import com.tumipay.domain.port.out.PaymentGatewayPort;
import com.tumipay.domain.port.out.PaymentMethodRepositoryPort;
import com.tumipay.infrastructure.adapter.input.rest.constant.MessageConstants;
import com.tumipay.domain.port.in.GetPayInUseCase;
import com.tumipay.domain.model.PayInId;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayInOrchestrator implements CreatePayInUseCase, GetPayInUseCase {

    private final PayInRepositoryPort payInRepositoryPort;
    private final PaymentGatewayPort paymentGatewayPort;
    private final CustomerRepositoryPort customerRepositoryPort;
    private final AccountRepositoryPort accountRepositoryPort;
    private final PaymentMethodRepositoryPort paymentMethodRepositoryPort;
    private final MessageSource messageSource;

    @Override
    public PayIn execute(PayInId id) {
        return payInRepositoryPort.findById(id)
                .orElseThrow(() -> new PayInDomainException(messageSource.getMessage(
                        MessageConstants.Errors.PAYIN_NOT_FOUND,
                        new Object[] { id.value() },
                        LocaleContextHolder.getLocale())));
    }

    @Override
    public PayIn execute(CreatePayInCommand command) {
        validateResourcesExistence(command);

        PayIn payIn = PayIn.create(
                command.customerId(),
                command.accountId(),
                command.paymentMethodId(),
                command.amount());
                
        Money amount = payIn.getAmount();
        System.out.println(amount);
        payIn = saveState(payIn);

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
                    LocaleContextHolder.getLocale());
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
        Locale locale = LocaleContextHolder.getLocale();

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