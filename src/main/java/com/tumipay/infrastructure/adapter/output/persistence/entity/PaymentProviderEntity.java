package com.tumipay.infrastructure.adapter.output.persistence.entity;

import com.tumipay.infrastructure.adapter.output.persistence.constant.SchemaConstants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Entity
@Table(name = SchemaConstants.Tables.PAYMENT_PROVIDERS)
@Getter
@Setter
public class PaymentProviderEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = SchemaConstants.Columns.NAME)
    private String name;

    @Column(name = SchemaConstants.Columns.API_ENDPOINT)
    private String apiEndpoint;

    @Column(name = SchemaConstants.Columns.IS_ACTIVE)
    private Boolean isActive;
}
