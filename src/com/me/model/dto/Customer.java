package com.me.model.dto;


import java.util.List;

public class Customer {
    private Name costumerName;
    private List<ContactDetail> contactDetails;



    public Customer(Name costumerName, List<ContactDetail> contactDetails) {
        this.costumerName = costumerName;
        this.contactDetails = contactDetails;
    }

}
