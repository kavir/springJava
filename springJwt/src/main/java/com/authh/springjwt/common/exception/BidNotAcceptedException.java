package com.authh.springjwt.common.exception;

public class BidNotAcceptedException extends RuntimeException {
    public BidNotAcceptedException(String message) {
        super(message);
    }
}
