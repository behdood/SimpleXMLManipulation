package com.me.model.bl;

import com.me.model.dao.ICustomerDAO;
import com.me.model.dto.Customer;
import com.me.model.dto.Name;

import java.util.Iterator;

public class CustomerBL implements CustomerIX {

    ICustomerDAO customerDAO;

    public CustomerBL(ICustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public boolean insertCustomer(Customer customer) {
        return false;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public boolean deleteCustomer(Customer customer) {
        return false;
    }

    @Override
    public Customer searchCustomerByName(Name name) {
        return null;
    }

    @Override
    public Iterator<Customer> findAllCustomers() {
        return null;
    }
}
