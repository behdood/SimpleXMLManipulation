package com.me.model.exceptions.invalid_customer;


public class InvalidCustomerObjectException extends CustomerException {
    public InvalidCustomerObjectException() {
        super("Customer object format is not valid!");
    }

    public InvalidCustomerObjectException(String message) {
        super(message);
    }
}
