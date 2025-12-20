package com.authh.springjwt.common.exception;

import org.hibernate.service.spi.ServiceException;

public class FailedToMoveFileException extends ServiceException {
    public FailedToMoveFileException(String msg) {
        super(msg);
    }
}
