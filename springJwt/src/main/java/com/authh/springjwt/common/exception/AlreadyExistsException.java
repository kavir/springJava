package com.authh.springjwt.common.exception;

import org.hibernate.service.spi.ServiceException;

public class AlreadyExistsException extends ServiceException {
    public AlreadyExistsException(String msg) {
        super(msg);
    }
}
