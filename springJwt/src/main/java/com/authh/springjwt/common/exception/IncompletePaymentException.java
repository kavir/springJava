package com.authh.springjwt.common.exception;

public class IncompletePaymentException extends RuntimeException {
    public IncompletePaymentException(String message) {
        super(message);
    }
}
