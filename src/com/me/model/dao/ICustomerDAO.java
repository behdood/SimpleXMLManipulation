package com.me.model.dao;


import com.me.model.dto.Customer;
import com.me.model.dto.Name;

import java.util.Iterator;

public interface ICustomerDAO {

    void addCustomer(Customer c);

    void modifyCustomer(Customer c);

    void removeCustomer(Customer c);

    // names of the customers are unique
    Customer findCustomerByName(Name name);

    Iterator<Customer> findAllCustomers();

}
