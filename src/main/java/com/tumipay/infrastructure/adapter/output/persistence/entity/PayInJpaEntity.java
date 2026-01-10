package com.tumipay.infrastructure.adapter.output.persistence.entity;

import com.tumipay.infrastructure.adapter.output.persistence.constant.SchemaConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = SchemaConstants.Tables.PAYINS)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayInJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @Column(name = SchemaConstants.Columns.CUSTOMER_ID)
    private Long customerId;

    @Column(name = SchemaConstants.Columns.ACCOUNT_ID)
    private Long accountId;

    @Column(name = SchemaConstants.Columns.PAYMENT_METHOD_ID)
    private Long paymentMethodId;

    @Column(name = SchemaConstants.Columns.REFERENCE_CODE)
    private String referenceCode;

    @Column(name = SchemaConstants.Columns.AMOUNT)
    private BigDecimal amount;

    @Column(name = SchemaConstants.Columns.CURRENCY)
    private String currency;

    @Column(name = SchemaConstants.Columns.STATUS)
    private String status;

    @Column(name = SchemaConstants.Columns.CREATED_AT)
    private LocalDateTime createdAt;

    @Column(name = SchemaConstants.Columns.UPDATED_AT)
    private LocalDateTime updatedAt;
}
