package com.me.model.dto;


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
}
