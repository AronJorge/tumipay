package com.tumipay.infrastructure.adapter.input.rest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.lang.NonNull;

import com.tumipay.domain.exception.PayInDomainException;
import com.tumipay.infrastructure.adapter.input.rest.constant.MessageConstants;
import com.tumipay.infrastructure.adapter.input.rest.constant.RestConstants;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(PayInDomainException.class)
    public ResponseEntity<Map<String, Object>> handleDomainException(PayInDomainException ex,
            HttpServletRequest request) {
        String message = tryTranslate(ex.getMessage(), ex.getArgs(), ex.getMessage());
        String errorTitle = tryTranslate(MessageConstants.Errors.GLOBAL_UNPROCESSABLE, null,
                MessageConstants.Defaults.TITLE_UNPROCESSABLE);

        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, errorTitle, message, request.getRequestURI(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = tryTranslate(error.getDefaultMessage(), null, error.getDefaultMessage());
            errors.put(fieldName, errorMessage);
        });

        String errorTitle = tryTranslate(MessageConstants.Errors.GLOBAL_TITLE_VALIDATION, null,
                MessageConstants.Defaults.TITLE_VALIDATION);
        String message = tryTranslate(MessageConstants.Errors.GLOBAL_MESSAGE_VALIDATION_FAILED, null,
                MessageConstants.Defaults.MESSAGE_VALIDATION_FAILED);

        return buildResponse(HttpStatus.BAD_REQUEST, errorTitle, message, request.getRequestURI(), errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(@NonNull IllegalArgumentException ex,
            HttpServletRequest request) {
        String message = tryTranslate(ex.getMessage(), null, ex.getMessage());
        String errorTitle = tryTranslate(MessageConstants.Errors.GLOBAL_TITLE_VALIDATION, null,
                MessageConstants.Defaults.TITLE_VALIDATION);

        return buildResponse(HttpStatus.BAD_REQUEST, errorTitle, message, request.getRequestURI(), null);
    }

    private String tryTranslate(String key, Object[] args, String defaultMessage) {
        if (key == null || key.isBlank()) {
            return defaultMessage != null ? defaultMessage : MessageConstants.Defaults.UNEXPECTED_ERROR;
        }
        try {
            return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            log.warn("Mensaje no encontrado para la clave: {}", key);
            return defaultMessage;
        }
    }

    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String errorTitle, String message,
            String path, Map<String, String> details) {
        Map<String, Object> body = new HashMap<>();
        body.put(RestConstants.ResponseKeys.TIMESTAMP, LocalDateTime.now());
        body.put(RestConstants.ResponseKeys.STATUS, status.value());
        body.put(RestConstants.ResponseKeys.ERROR, errorTitle);
        body.put(RestConstants.ResponseKeys.MESSAGE, message);
        body.put(RestConstants.ResponseKeys.PATH, path);

        if (details != null) {
            body.put(RestConstants.ResponseKeys.DETAILS, details);
        }

        return new ResponseEntity<>(body, status);
    }
}
