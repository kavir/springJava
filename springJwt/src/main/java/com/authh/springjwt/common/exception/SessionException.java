package com.authh.springjwt.common.exception;

import javax.naming.AuthenticationException;

public class SessionException extends AuthenticationException {

    public SessionException(String message) {
        super(message);
    }
}
