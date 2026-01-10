package com.tumipay.infrastructure.adapter.output.persistence;

import com.tumipay.domain.model.PayIn;
import com.tumipay.domain.model.PayInId;
import com.tumipay.domain.port.out.PayInRepositoryPort;
import com.tumipay.infrastructure.adapter.output.persistence.entity.PayInJpaEntity;
import com.tumipay.infrastructure.adapter.output.persistence.mapper.PayInJpaMapper;
import com.tumipay.infrastructure.adapter.output.persistence.repository.SpringDataPayInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PayInPersistenceAdapter implements PayInRepositoryPort {

    private final SpringDataPayInRepository repository;
    private final PayInJpaMapper mapper;

    @Override
    public PayIn save(PayIn payIn) {
        PayInJpaEntity entity = mapper.toJpaEntity(payIn);
        PayInJpaEntity savedEntity = repository.save(Objects.requireNonNull(entity));
        return mapper.toDomainEntity(savedEntity);
    }

    @Override
    public Optional<PayIn> findById(PayInId id) {
        return repository.findById(Objects.requireNonNull(id.value()))
                .map(mapper::toDomainEntity);
    }
}
