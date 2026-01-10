package com.tumipay.infrastructure.adapter.output.persistence;

import com.tumipay.domain.port.out.CustomerRepositoryPort;
import com.tumipay.infrastructure.adapter.output.persistence.repository.SpringDataCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaCustomerRepositoryAdapter implements CustomerRepositoryPort {

    private final SpringDataCustomerRepository repository;

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }
        return repository.existsById(id);
    }
}
