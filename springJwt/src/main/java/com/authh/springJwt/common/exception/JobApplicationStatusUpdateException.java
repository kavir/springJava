package com.authh.springJwt.common.exception;

public class JobApplicationStatusUpdateException extends RuntimeException {

    public JobApplicationStatusUpdateException(String message) {
        super(message);
    }

    public JobApplicationStatusUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public JobApplicationStatusUpdateException(Throwable cause) {
        super(cause);
    }
}
