package com.me.model.bl;


import com.me.model.dao.ICustomerDAO;
import com.me.model.dto.Customer;
import com.me.model.dto.Name;

import java.util.Iterator;

public class fakeCustomerDAO implements ICustomerDAO {
    @Override
    public void addCustomer(Customer c) {

    }

    @Override
    public void modifyCustomer(Customer c) {

    }

    @Override
    public void removeCustomer(Customer c) {

    }

    @Override
    public Customer findCustomerByName(Name name) {
        return null;
    }

    @Override
    public Iterator<Customer> findAllCustomers() {
        return null;
    }
}
