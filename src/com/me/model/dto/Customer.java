package com.me.model.dto;


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


    public Customer addAddress(String addressType, String street1, String street2, String postalCode, String town) {
        contactDetails.add(new Address(addressType, street1, street2, postalCode, town));
        return this;
    }

    public Customer addEmail(String emailType, String value) {
        contactDetails.add(new Email(emailType, value));
        return this;
    }

    public Customer addPhone(String phoneType, String value) {
        contactDetails.add(new Phone(phoneType, value));
        return this;
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
