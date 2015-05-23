package com.me.model.exceptions.invalid_customer;


public class DuplicateCustomerNameException extends CustomerException {
    public DuplicateCustomerNameException() {
        super("Customer names should be unique.");
    }

    public DuplicateCustomerNameException(String message) {
        super(message);
    }
}
