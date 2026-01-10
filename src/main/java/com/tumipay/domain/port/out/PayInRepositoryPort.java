package com.tumipay.domain.port.out;

import com.tumipay.domain.model.PayIn;
import com.tumipay.domain.model.PayInId;
import java.util.Optional;

public interface PayInRepositoryPort {
    PayIn save(PayIn payIn);

    Optional<PayIn> findById(PayInId id);
}
