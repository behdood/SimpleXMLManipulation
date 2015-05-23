package com.me.model.exceptions.invalid_customer;

public class NullCustomerNameException extends CustomerException {

    public NullCustomerNameException() {
        super("Customer name should not be empty string.");
    }

    public NullCustomerNameException(String message) {
        super(message);
    }
}
