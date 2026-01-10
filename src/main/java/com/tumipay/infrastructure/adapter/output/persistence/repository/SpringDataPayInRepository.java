package com.tumipay.infrastructure.adapter.output.persistence.repository;

import com.tumipay.infrastructure.adapter.output.persistence.entity.PayInJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataPayInRepository extends JpaRepository<PayInJpaEntity, UUID> {
}
