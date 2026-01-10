package com.tumipay.infrastructure.adapter.output.persistence.repository;

import com.tumipay.infrastructure.adapter.output.persistence.entity.PaymentMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataPaymentMethodRepository extends JpaRepository<PaymentMethodEntity, Long> {
}
