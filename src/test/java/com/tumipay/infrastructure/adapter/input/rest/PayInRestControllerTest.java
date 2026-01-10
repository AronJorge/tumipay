package com.tumipay.infrastructure.adapter.input.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tumipay.domain.model.Money;
import com.tumipay.domain.model.PayIn;
import com.tumipay.domain.model.PayInId;
import com.tumipay.domain.model.PayInStatus;
import com.tumipay.domain.port.in.CreatePayInUseCase;
import com.tumipay.infrastructure.adapter.input.rest.constant.RestConstants;
import com.tumipay.infrastructure.adapter.input.rest.dto.PayInRequest;
import com.tumipay.infrastructure.adapter.input.rest.mapper.PayInRestMapper;
import com.tumipay.infrastructure.config.JacksonConfiguration;

@WebMvcTest(PayInRestController.class)
@Import({ PayInRestMapper.class, JacksonConfiguration.class })
class PayInRestControllerTest {

        private static final String CURRENCY = "COP";
        private static final BigDecimal AMOUNT = new BigDecimal("50000");

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockitoBean
        private CreatePayInUseCase createPayInUseCase;

        @MockitoBean
        private com.tumipay.domain.port.in.GetPayInUseCase getPayInUseCase;

        @Test
        void should_create_payin_with_snake_case_response() throws Exception {
                PayInRequest request = new PayInRequest(1L, 2L, 3L, AMOUNT, CURRENCY);

                PayIn mockPayIn = PayIn.builder()
                                .id(PayInId.random())
                                .status(PayInStatus.CREATED)
                                .amount(new Money(BigDecimal.TEN, CURRENCY))
                                .referenceCode(UUID.randomUUID().toString())
                                .build();

                when(createPayInUseCase.execute(any())).thenReturn(mockPayIn);

                mockMvc.perform(post(RestConstants.ApiPaths.PAYINS_V1)
                                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                                .content(Objects.requireNonNull(objectMapper.writeValueAsString(request))))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$." + RestConstants.ResponseKeys.REFERENCE_CODE).exists())
                                .andExpect(jsonPath("$." + RestConstants.ResponseKeys.STATUS)
                                                .value(PayInStatus.CREATED.name()))
                                .andExpect(jsonPath(
                                                "$." + RestConstants.ResponseKeys.AMOUNT + "."
                                                                + RestConstants.ResponseKeys.VALUE)
                                                .exists());
        }

        @Test
        void should_get_payin_with_snake_case_response() throws Exception {
                PayInId id = PayInId.random();
                PayIn mockPayIn = PayIn.builder()
                                .id(id)
                                .status(PayInStatus.PROCESSED)
                                .amount(new Money(BigDecimal.TEN, CURRENCY))
                                .referenceCode(UUID.randomUUID().toString())
                                .build();

                when(getPayInUseCase.execute(id)).thenReturn(mockPayIn);

                mockMvc.perform(
                                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(
                                                RestConstants.ApiPaths.PAYINS_V1 + "/{id}", id.value().toString())
                                                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$." + RestConstants.ResponseKeys.REFERENCE_CODE).exists())
                                .andExpect(jsonPath("$." + RestConstants.ResponseKeys.STATUS)
                                                .value(PayInStatus.PROCESSED.name()));
        }

        @Test
        void should_return_404_when_payin_not_found() throws Exception {
                PayInId id = PayInId.random();

                when(getPayInUseCase.execute(id))
                                .thenThrow(new com.tumipay.domain.exception.PayInDomainException("PayIn not found"));

                mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get(
                                RestConstants.ApiPaths.PAYINS_V1 + "/{id}", id.value().toString())
                                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON)))
                                .andExpect(status().is4xxClientError());
        }
}