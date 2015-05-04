package com.me.model.bl;


import com.me.model.dto.Customer;
import com.me.model.dto.Name;

import java.util.Iterator;

public interface CustomerIX {

    boolean insertCustomer(Customer customer);

    boolean updateCustomer(Customer customer);

    boolean deleteCustomer(Customer customer);

    // note: name should be unique!
    Customer searchCustomerByName(Name name);

    Iterator<Customer> findAllCustomers();


    //todo : searchCustomer by other criteria
}
