package com.tumipay.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import com.tumipay.domain.model.PayInId;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.tumipay.domain.model.Money;
import com.tumipay.domain.model.PayIn;
import com.tumipay.domain.model.PayInStatus;
import com.tumipay.domain.port.in.CreatePayInUseCase.CreatePayInCommand;
import com.tumipay.domain.port.out.AccountRepositoryPort;
import com.tumipay.domain.port.out.CustomerRepositoryPort;
import com.tumipay.domain.port.out.PayInRepositoryPort;
import com.tumipay.domain.port.out.PaymentGatewayPort;
import com.tumipay.domain.port.out.PaymentMethodRepositoryPort;
import org.mockito.ArgumentMatchers;

@ExtendWith(MockitoExtension.class)
class PayInOrchestratorTest {

    private static final Long CUSTOMER_ID = 1L;
    private static final Long ACCOUNT_ID = 1L;
    private static final Long PAYMENT_METHOD_ID = 1L;
    private static final BigDecimal AMOUNT = new BigDecimal("10000");
    private static final String CURRENCY = "COP";
    private static final String ERROR_MESSAGE_GATEWAY_REJECTED = "Gateway rejected payment";

    @Mock
    private PayInRepositoryPort repositoryPort;
    @Mock
    private PaymentGatewayPort gatewayPort;
    @Mock
    private CustomerRepositoryPort customerRepositoryPort;
    @Mock
    private AccountRepositoryPort accountRepositoryPort;
    @Mock
    private PaymentMethodRepositoryPort paymentMethodRepositoryPort;
    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private PayInOrchestrator orchestrator;

    private MockedStatic<LocaleContextHolder> localeMock;

    @BeforeEach
    void setUp() {
        localeMock = mockStatic(LocaleContextHolder.class);
        localeMock.when(LocaleContextHolder::getLocale).thenReturn(Locale.US);
    }

    @AfterEach
    void tearDown() {
        if (localeMock != null) {
            localeMock.close();
        }
    }

    @Test
    void should_process_payin_successfully() {
        CreatePayInCommand command = new CreatePayInCommand(CUSTOMER_ID, ACCOUNT_ID, PAYMENT_METHOD_ID,
                new Money(AMOUNT, CURRENCY));
        List<PayInStatus> capturedStatuses = new ArrayList<>();

        when(customerRepositoryPort.existsById(CUSTOMER_ID)).thenReturn(true);
        when(accountRepositoryPort.existsById(ACCOUNT_ID)).thenReturn(true);
        when(paymentMethodRepositoryPort.existsById(PAYMENT_METHOD_ID)).thenReturn(true);
        when(repositoryPort.save(any(PayIn.class))).thenAnswer(invocation -> {
            PayIn argument = invocation.getArgument(0);
            capturedStatuses.add(argument.getStatus());
            return argument;
        });
        when(gatewayPort.processPayment(any(PayIn.class))).thenReturn(true);

        orchestrator.execute(command);

        assertEquals(2, capturedStatuses.size());
        assertEquals(PayInStatus.VALIDATED, capturedStatuses.get(0));
        assertEquals(PayInStatus.PROCESSED, capturedStatuses.get(1));

        verify(repositoryPort, times(2)).save(any(PayIn.class));
        verify(gatewayPort).processPayment(any(PayIn.class));
    }

    @Test
    @SuppressWarnings("null")
    void should_handle_gateway_failure() {
        CreatePayInCommand command = new CreatePayInCommand(CUSTOMER_ID, ACCOUNT_ID, PAYMENT_METHOD_ID,
                new Money(AMOUNT, CURRENCY));
        List<PayInStatus> capturedStatuses = new ArrayList<>();

        when(customerRepositoryPort.existsById(CUSTOMER_ID)).thenReturn(true);
        when(accountRepositoryPort.existsById(ACCOUNT_ID)).thenReturn(true);
        when(paymentMethodRepositoryPort.existsById(PAYMENT_METHOD_ID)).thenReturn(true);
        when(repositoryPort.save(any(PayIn.class))).thenAnswer(invocation -> {
            PayIn argument = invocation.getArgument(0);
            capturedStatuses.add(argument.getStatus());
            return argument;
        });
        when(gatewayPort.processPayment(any(PayIn.class))).thenReturn(false);
        when(messageSource.getMessage(any(), any(), ArgumentMatchers.eq(Locale.US)))
                .thenReturn(ERROR_MESSAGE_GATEWAY_REJECTED);

        orchestrator.execute(command);

        assertEquals(2, capturedStatuses.size());
        assertEquals(PayInStatus.VALIDATED, capturedStatuses.get(0));
        assertEquals(PayInStatus.FAILED, capturedStatuses.get(1));

        verify(repositoryPort, times(2)).save(any(PayIn.class));
        verify(gatewayPort).processPayment(any(PayIn.class));
    }

    @Test
    void should_get_payin_successfully() {
        PayInId id = PayInId.random();
        PayIn expectedPayIn = PayIn.builder()
                .id(id)
                .status(PayInStatus.PROCESSED)
                .amount(new Money(BigDecimal.TEN, CURRENCY))
                .referenceCode(UUID.randomUUID().toString())
                .build();

        when(repositoryPort.findById(id)).thenReturn(Optional.of(expectedPayIn));

        PayIn result = orchestrator.execute(id);

        assertEquals(expectedPayIn, result);
        verify(repositoryPort).findById(id);
    }

    @SuppressWarnings("null")
    @Test
    void should_throw_exception_when_payin_not_found() {
        PayInId id = PayInId.random();
        when(repositoryPort.findById(id)).thenReturn(Optional.empty());
        when(messageSource.getMessage(any(), any(), any())).thenReturn("PayIn not found");

        com.tumipay.domain.exception.PayInDomainException exception = org.junit.jupiter.api.Assertions.assertThrows(
                com.tumipay.domain.exception.PayInDomainException.class,
                () -> orchestrator.execute(id));

        assertEquals("PayIn not found", exception.getMessage());
        verify(repositoryPort).findById(id);
    }
}