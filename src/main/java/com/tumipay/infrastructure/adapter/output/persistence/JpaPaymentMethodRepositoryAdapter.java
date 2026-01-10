package com.tumipay.infrastructure.adapter.output.persistence;

import com.tumipay.domain.port.out.PaymentMethodRepositoryPort;
import com.tumipay.infrastructure.adapter.output.persistence.repository.SpringDataPaymentMethodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaPaymentMethodRepositoryAdapter implements PaymentMethodRepositoryPort {

    private final SpringDataPaymentMethodRepository repository;

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }
        return repository.existsById(id);
    }
}
