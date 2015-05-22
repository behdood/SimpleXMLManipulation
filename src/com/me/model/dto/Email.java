package com.me.model.dto;

import java.util.Objects;

public class Email extends ContactDetail{
    private String type;
    private String value;

    public Email(String type, String value) {
        this.contactType = CONTACT_TYPE.EMAIL;
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(type, email.type) &&
                Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}
