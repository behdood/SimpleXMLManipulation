package com.me.model.exceptions;


public class CustomerDoesNotExistException extends BaseException {
    public CustomerDoesNotExistException() {
        super("Customer Node does not exist");
    }

    public CustomerDoesNotExistException(String message) {
        super(message);
    }
}
