package com.me.model.dao;


import com.me.model.dao.CustomerDao;
import com.me.model.dto.Customer;
import com.me.model.dto.Name;

import java.util.Iterator;

public class StaxXmlCustomerDao implements CustomerDao {
    @Override
    public void addCustomer(Customer c) throws Exception {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void modifyCustomer(Name oldCustomerName, Customer c) throws Exception {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void removeCustomer(Customer c) throws Exception {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public Customer findCustomerByName(Name name) throws Exception {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public Iterator<Customer> findAllCustomers() throws Exception {
        throw new RuntimeException("Not implemented yet!");
    }
}
