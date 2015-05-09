package com.me.model.exceptions;


public class InconsistentNodeException extends RuntimeException {

    public InconsistentNodeException() {
    }

    public InconsistentNodeException(String message) {
        super(message);
    }

    public InconsistentNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InconsistentNodeException(Throwable cause) {
        super(cause);
    }

    public InconsistentNodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
