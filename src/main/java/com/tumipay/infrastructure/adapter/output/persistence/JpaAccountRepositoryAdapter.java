package com.tumipay.infrastructure.adapter.output.persistence;

import com.tumipay.domain.port.out.AccountRepositoryPort;
import com.tumipay.infrastructure.adapter.output.persistence.repository.SpringDataAccountRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JpaAccountRepositoryAdapter implements AccountRepositoryPort {

    private final SpringDataAccountRepository repository;

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            return false;
        }
        return repository.existsById(id);
    }
}
