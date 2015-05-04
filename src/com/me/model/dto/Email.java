package com.me.model.dto;

public class Email extends ContactDetail{
    private String type;
    private String value;

    public Email(String type, String value) {
        this.contactType = CONTACT_TYPE.EMAIL;
        this.type = type;
        this.value = value;
    }
}
