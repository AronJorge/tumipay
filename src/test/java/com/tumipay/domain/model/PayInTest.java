package com.tumipay.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.tumipay.domain.exception.PayInDomainException;
import com.tumipay.infrastructure.adapter.input.rest.constant.MessageConstants;

class PayInTest {

    private static final Long CUSTOMER_ID = 1L;
    private static final Long ACCOUNT_ID = 1L;
    private static final Long PAYMENT_METHOD_ID = 1L;
    private static final Money AMOUNT_COP = new Money(new BigDecimal("50000"), "COP");
    private static final String ERROR_MESSAGE = "Gateway failure";

    @Test
    void should_create_payin_in_created_state() {

        PayIn payIn = PayIn.create(CUSTOMER_ID, ACCOUNT_ID, PAYMENT_METHOD_ID, AMOUNT_COP);

        assertNotNull(payIn.getId());
        assertEquals(PayInStatus.CREATED, payIn.getStatus());
        assertNotNull(payIn.getReferenceCode());
    }

    @Test
    void should_validate_successfully() {

        PayIn payIn = PayIn.create(CUSTOMER_ID, ACCOUNT_ID, PAYMENT_METHOD_ID, AMOUNT_COP);

        payIn.validate();

        assertEquals(PayInStatus.VALIDATED, payIn.getStatus());
    }

    @Test
    void should_throw_exception_when_processing_unvalidated_payin() {

        PayIn payIn = PayIn.create(CUSTOMER_ID, ACCOUNT_ID, PAYMENT_METHOD_ID, AMOUNT_COP);

        PayInDomainException exception = assertThrows(PayInDomainException.class, payIn::process);
        assertTrue(exception.getMessage().contains(MessageConstants.Errors.STATE_TRANSITION_INVALID));
    }

    @Test
    void should_throw_exception_when_creating_with_null_amount() {

        PayInDomainException exception = assertThrows(PayInDomainException.class,
                () -> PayIn.create(CUSTOMER_ID, ACCOUNT_ID, PAYMENT_METHOD_ID, null));
        assertEquals(MessageConstants.Validation.PAYIN_AMOUNT_REQUIRED, exception.getMessage());
    }

    @Test
    void should_fail_payin_successfully() {

        PayIn payIn = PayIn.create(CUSTOMER_ID, ACCOUNT_ID, PAYMENT_METHOD_ID, AMOUNT_COP);
        payIn.validate();

        payIn.fail(ERROR_MESSAGE);

        assertEquals(PayInStatus.FAILED, payIn.getStatus());
    }
}