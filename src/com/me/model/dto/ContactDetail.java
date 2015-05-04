package com.me.model.dto;


public abstract class ContactDetail {

    public enum CONTACT_TYPE {ADDRESS, EMAIL, PHONE}

    protected CONTACT_TYPE contactType;

    public CONTACT_TYPE getContactType() {
        return contactType;
    }

}
