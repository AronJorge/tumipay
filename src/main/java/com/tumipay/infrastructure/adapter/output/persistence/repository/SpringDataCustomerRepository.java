package com.tumipay.infrastructure.adapter.output.persistence.repository;

import com.tumipay.infrastructure.adapter.output.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataCustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
