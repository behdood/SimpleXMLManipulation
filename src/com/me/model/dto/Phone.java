package com.me.model.dto;


import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return Objects.equals(type, phone.type) &&
                Objects.equals(value, phone.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}
