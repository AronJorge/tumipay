package com.tumipay.domain.model;

import java.util.UUID;

import com.tumipay.infrastructure.adapter.input.rest.constant.MessageConstants;

public record PayInId(UUID value) {
    public PayInId {
        if (value == null) {
            throw new IllegalArgumentException(MessageConstants.Errors.GLOBAL_ID_NULL);
        }
    }

    public static PayInId random() {
        return new PayInId(UUID.randomUUID());
    }

    public static PayInId of(String value) {
        if (value == null) {
            throw new IllegalArgumentException(MessageConstants.Errors.GLOBAL_ID_NULL);
        }
        try {
            return new PayInId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(MessageConstants.Errors.GLOBAL_ID_INVALID_FORMAT);
        }
    }
}
