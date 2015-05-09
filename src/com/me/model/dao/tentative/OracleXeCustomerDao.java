package com.me.model.dao.tentative;

import com.me.model.dto.Customer;
import com.me.model.dto.Name;

import java.util.Iterator;


public class OracleXeCustomerDao implements CustomerDao {
    @Override
    public void addCustomer(Customer c) throws Exception {
        throw new RuntimeException("Not Implemented yet");
    }

    @Override
    public void modifyCustomer(Name oldCustomerName, Customer c) throws Exception {
        throw new RuntimeException("Not Implemented yet");
    }

    @Override
    public void removeCustomer(Customer c) throws Exception {
        throw new RuntimeException("Not Implemented yet");
    }

    @Override
    public Customer findCustomerByName(Name name) throws Exception {
        throw new RuntimeException("Not Implemented yet");
    }

    @Override
    public Iterator<Customer> findAllCustomers() throws Exception {
        throw new RuntimeException("Not Implemented yet");
    }
}
