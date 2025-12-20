package com.authh.springjwt.common.exception;

import org.hibernate.service.spi.ServiceException;

public class NotFoundException extends ServiceException {
    public NotFoundException(String msg) {
        super(msg);
    }
}
