package com.me.model.dao;


import com.me.model.dto.Customer;
import com.me.model.dto.Name;

import java.util.Iterator;

public interface ICustomerDAO {

    void addCustomer(Customer c) throws Exception;

    void modifyCustomer(Name oldCustomerName, Customer c) throws Exception;

    void removeCustomer(Customer c) throws Exception;

    // names of the customers are unique
    Customer findCustomerByName(Name name) throws Exception;
    //todo: when implementing findBy_otherCriterion, remember to override the equal() of the corresponding class

    Iterator<Customer> findAllCustomers() throws Exception;

}
