package com.tumipay.domain.exception;

public class PayInDomainException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final transient Object[] args;

    public PayInDomainException(String message) {
        super(message);
        this.args = null;
    }

    public PayInDomainException(String message, Object[] args) {
        super(message);
        this.args = args;
    }

    public PayInDomainException(String message, Throwable cause) {
        super(message, cause);
        this.args = null;
    }

    public Object[] getArgs() {
        return args;
    }
}
