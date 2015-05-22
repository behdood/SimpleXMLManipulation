package com.me.model.dto;


import java.util.Objects;

public class Address extends ContactDetail {
    private String type;
    private String street1;
    private String street2;
    private String postalCode;
    private String town;

    public Address(String type, String street1, String street2, String postalCode, String town) {
        this.contactType = CONTACT_TYPE.ADDRESS;
        this.type = type;
        this.street1 = street1;
        this.street2 = street2;
        this.postalCode = postalCode;
        this.town = town;
    }

    public String getType() {
        return type;
    }

    public String getStreet1() {
        return street1;
    }

    public String getStreet2() {
        return street2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getTown() {
        return town;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(type, address.type) &&
                Objects.equals(street1, address.street1) &&
                Objects.equals(street2, address.street2) &&
                Objects.equals(postalCode, address.postalCode) &&
                Objects.equals(town, address.town);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, street1, street2, postalCode, town);
    }
}

