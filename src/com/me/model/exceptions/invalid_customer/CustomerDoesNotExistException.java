package com.me.model.exceptions.invalid_customer;


public class CustomerDoesNotExistException extends CustomerException {
    public CustomerDoesNotExistException() {
        super("Customer Node does not exist");
    }

    public CustomerDoesNotExistException(String message) {
        super(message);
    }
}
