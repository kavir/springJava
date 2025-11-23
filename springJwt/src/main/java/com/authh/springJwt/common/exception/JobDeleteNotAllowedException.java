package com.authh.springJwt.common.exception;

public class JobDeleteNotAllowedException extends RuntimeException {

    public JobDeleteNotAllowedException(String message) {
        super(message);
    }
}
