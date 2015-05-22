package com.me.model.exceptions;


public class CustomerAlreadyExistException extends BaseException {

    public CustomerAlreadyExistException() {
        super("Customer node already exists.");
    }

    public CustomerAlreadyExistException(String message) {
        super(message);
    }
}
