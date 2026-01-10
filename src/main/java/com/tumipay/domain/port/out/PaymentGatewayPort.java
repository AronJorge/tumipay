package com.tumipay.domain.port.out;

import com.tumipay.domain.model.PayIn;

public interface PaymentGatewayPort {
    boolean processPayment(PayIn payIn);
}
