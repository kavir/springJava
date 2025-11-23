package com.authh.springJwt.common.exception;

public class BidNotAcceptedException extends RuntimeException {
    public BidNotAcceptedException(String message) {
        super(message);
    }
}
