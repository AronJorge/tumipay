package com.tumipay.infrastructure.adapter.output.persistence.entity;

import com.tumipay.infrastructure.adapter.output.persistence.constant.SchemaConstants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Entity
@Table(name = SchemaConstants.Tables.PAYMENT_METHODS)
@Getter
@Setter
public class PaymentMethodEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = SchemaConstants.Columns.NAME)
    private String name;

    @Column(name = SchemaConstants.Columns.TYPE)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = SchemaConstants.Columns.PAYMENT_PROVIDER_ID)
    private PaymentProviderEntity paymentProvider;
}
