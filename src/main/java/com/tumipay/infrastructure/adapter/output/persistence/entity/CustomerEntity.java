package com.tumipay.infrastructure.adapter.output.persistence.entity;

import com.tumipay.infrastructure.adapter.output.persistence.constant.SchemaConstants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Entity
@Table(name = SchemaConstants.Tables.CUSTOMERS)
@Getter
@Setter
public class CustomerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = SchemaConstants.Columns.FULL_NAME)
    private String fullName;

    @Column(name = SchemaConstants.Columns.DOCUMENT_NUMBER)
    private String documentNumber;

    @Column(name = SchemaConstants.Columns.DOCUMENT_TYPE)
    private String documentType;

    private String email;

    @Column(name = SchemaConstants.Columns.STATUS)
    private String status;
}
