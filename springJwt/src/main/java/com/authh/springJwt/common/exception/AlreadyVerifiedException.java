package com.authh.springJwt.common.exception;

import org.hibernate.service.spi.ServiceException;

public class AlreadyVerifiedException extends ServiceException {
    public AlreadyVerifiedException(String msg) {
        super(msg);
    }
}
