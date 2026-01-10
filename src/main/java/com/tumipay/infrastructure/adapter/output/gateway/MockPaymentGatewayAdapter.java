package com.tumipay.infrastructure.adapter.output.gateway;

import com.tumipay.domain.model.PayIn;
import com.tumipay.domain.port.out.PaymentGatewayPort;
import org.springframework.stereotype.Component;

@Component
public class MockPaymentGatewayAdapter implements PaymentGatewayPort {

    @Override
    public boolean processPayment(PayIn payIn) {
        // Simulate successful payment
        return true;
    }
}
