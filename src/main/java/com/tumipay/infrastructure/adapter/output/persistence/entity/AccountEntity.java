package com.tumipay.infrastructure.adapter.output.persistence.entity;

import com.tumipay.infrastructure.adapter.output.persistence.constant.SchemaConstants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Entity
@Table(name = SchemaConstants.Tables.ACCOUNTS)
@Getter
@Setter
public class AccountEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;

    @ManyToOne
    @JoinColumn(name = SchemaConstants.Columns.CUSTOMER_ID)
    private CustomerEntity customer;

    @Column(name = SchemaConstants.Columns.ACCOUNT_TYPE)
    private String accountType;

    private String currency;

}
