package com.me.model.dto;


public class Phone extends ContactDetail {
    private String type;
    private String value;

    public Phone(String type, String value) {
        this.contactType = CONTACT_TYPE.PHONE;
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
