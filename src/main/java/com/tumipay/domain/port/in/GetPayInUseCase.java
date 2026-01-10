package com.tumipay.domain.port.in;

import com.tumipay.domain.model.PayIn;
import com.tumipay.domain.model.PayInId;

public interface GetPayInUseCase {
    PayIn execute(PayInId id);
}
