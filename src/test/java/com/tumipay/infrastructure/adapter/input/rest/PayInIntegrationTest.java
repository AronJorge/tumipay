package com.tumipay.infrastructure.adapter.input.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.tumipay.domain.model.Money;
import com.tumipay.domain.model.PayIn;
import com.tumipay.domain.model.PayInId;
import com.tumipay.domain.model.PayInStatus;
import com.tumipay.domain.port.out.PayInRepositoryPort;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PayInIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PayInRepositoryPort payInRepositoryPort;

    @Test
    void should_return_payin_when_exists() throws Exception {
        PayInId id = PayInId.random();
        PayIn payIn = PayIn.builder()
                .id(id)
                .customerId(1L)
                .accountId(1L)
                .paymentMethodId(1L)
                .status(PayInStatus.PROCESSED)
                .amount(new Money(new BigDecimal("50000"), "COP"))
                .referenceCode("ref-integration-test")
                .build();

        payInRepositoryPort.save(payIn);

        mockMvc.perform(get("/v1/payins/" + id.value().toString())
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PROCESSED"))
                .andExpect(jsonPath("$.amount.value").value(50000))
                .andExpect(jsonPath("$.reference_code").value("ref-integration-test"));
    }
}
