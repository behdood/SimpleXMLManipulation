package com.me.model.exceptions.invalid_customer;


public class CustomerAlreadyExistException extends CustomerException {

    public CustomerAlreadyExistException() {
        super("Customer node already exists.");
    }

    public CustomerAlreadyExistException(String message) {
        super(message);
    }
}
