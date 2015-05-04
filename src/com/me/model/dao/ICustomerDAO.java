package com.me.model.dao;


import com.me.model.dto.Customer;

import java.util.Iterator;

public interface ICustomerDAO {

    Iterator<Customer> findAllCustomers();

    Customer findCustomer(String name);

    void insertCustomer(Customer c);

    void modifyCustomer(Customer c);

    void deleteCustomer(Customer c);
}
