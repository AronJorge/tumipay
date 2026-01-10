package com.tumipay.infrastructure.adapter.input.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tumipay.domain.model.PayIn;
import com.tumipay.domain.port.in.CreatePayInUseCase;
import com.tumipay.infrastructure.adapter.input.rest.dto.PayInRequest;
import com.tumipay.infrastructure.adapter.input.rest.dto.PayInResponse;
import com.tumipay.infrastructure.adapter.input.rest.mapper.PayInRestMapper;
import com.tumipay.infrastructure.adapter.input.rest.constant.RestConstants;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(RestConstants.ApiPaths.PAYINS_V1)
@RequiredArgsConstructor
public class PayInRestController {

    private final CreatePayInUseCase createPayInUseCase;
    private final com.tumipay.domain.port.in.GetPayInUseCase getPayInUseCase;
    private final PayInRestMapper mapper;

    @PostMapping
    public ResponseEntity<PayInResponse> createPayIn(@Valid @RequestBody PayInRequest request) {
        var command = mapper.toCommand(request);
        PayIn result = createPayInUseCase.execute(command);
        return new ResponseEntity<>(mapper.toResponse(result), HttpStatus.CREATED);
    }

    @org.springframework.web.bind.annotation.GetMapping("/{id}")
    public ResponseEntity<PayInResponse> getPayIn(@org.springframework.web.bind.annotation.PathVariable String id) {
        PayIn result = getPayInUseCase.execute(com.tumipay.domain.model.PayInId.of(id));
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
