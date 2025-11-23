package com.authh.springJwt.common.exception;

import org.hibernate.service.spi.ServiceException;

public class InvalidFileTypeException extends ServiceException {
    public InvalidFileTypeException(String message) {
        super(message);
    }
}
