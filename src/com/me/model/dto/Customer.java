package com.me.model.dto;


import java.util.ArrayList;
import java.util.List;

public class Customer {
    private Name customerName;
    private List<ContactDetail> contactDetails;
    private String notes;


    public Customer(Name costumerName, List<ContactDetail> contactDetails, String notes) {
        this.customerName = costumerName;
        this.contactDetails = contactDetails;
        this.notes = notes;
    }

    public Customer(String firstName, String lastName, char middleInitial) {
        this.customerName = new Name(firstName, lastName, middleInitial);
        contactDetails = new ArrayList<ContactDetail>();
    }


    public Name getName() {
        return customerName;
    }

    public List<ContactDetail> getContactDetails() {
        return contactDetails;
    }

    public String getNotes() {
        return notes;
    }


    public Customer addAddress(Address address) {
        contactDetails.add(address);
        return this;
    }

    public Customer addAddress(String addressType, String street1, String street2, String postalCode, String town) {
        return addAddress(new Address(addressType, street1, street2, postalCode, town));
    }

    public Customer addEmail(Email email) {
        contactDetails.add(email);
        return this;
    }

    public Customer addEmail(String emailType, String value) {
        return addEmail(new Email(emailType, value));
    }

    public Customer addPhone(Phone phone) {
        contactDetails.add(phone);
        return this;
    }

    public Customer addPhone(String phoneType, String value) {
        return addPhone(new Phone(phoneType, value));
    }


    public Customer setName(Name newCustomerName) {
        this.customerName = newCustomerName;
        return this;
    }

    public Customer setNotes(String newNotes) {
        this.notes = newNotes;
        return this;
    }

}
