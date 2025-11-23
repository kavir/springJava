package com.authh.springJwt.common.exception;

public class IncompletePaymentException extends RuntimeException {
    public IncompletePaymentException(String message) {
        super(message);
    }
}
