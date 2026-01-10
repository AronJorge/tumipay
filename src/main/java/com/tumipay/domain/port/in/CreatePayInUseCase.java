package com.tumipay.domain.port.in;

import com.tumipay.domain.model.Money;
import com.tumipay.domain.model.PayIn;

public interface CreatePayInUseCase {
    record CreatePayInCommand(
            Long customerId,
            Long accountId,
            Long paymentMethodId,
            Money amount) {
    }

    PayIn execute(CreatePayInCommand command);
}
