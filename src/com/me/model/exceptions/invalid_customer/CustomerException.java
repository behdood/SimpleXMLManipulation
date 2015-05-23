package com.me.model.exceptions.invalid_customer;


import com.me.model.exceptions.BaseException;

public class CustomerException extends BaseException {
    public CustomerException() {
    }

    public CustomerException(String message) {
        super(message);
    }
}
