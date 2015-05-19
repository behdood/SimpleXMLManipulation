package com.me.model.dao;


import com.me.model.dao.tentative.CustomerDao;
import com.me.model.dto.Customer;
import com.me.model.dto.Name;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FakeCustomerDao implements CustomerDao {

    public List<Customer> customers;

    public FakeCustomerDao() {
        customers = new ArrayList<>();
    }

    public FakeCustomerDao(Customer c) {
        customers = new ArrayList<>();
        customers.add(c);
    }

    @Override
    public void addCustomer(Customer c) throws Exception {
        customers.add(c);
    }

    @Override
    public void modifyCustomer(Name oldCustomerName, Customer c) throws Exception {
        for (Customer customer_in_list : customers) {
            if (customer_in_list.getName().equals(oldCustomerName))
                customers.set(customers.indexOf(customer_in_list), c);
        }
    }

    @Override
    public void removeCustomer(Customer c) throws Exception {
        customers.remove(c);
    }

    @Override
    public Customer findCustomerByName(Name name) throws Exception {
        for (Customer customer : customers) {
            if (customer.getName().equals(name))
                return customer;
        }
        return null;
    }

    @Override
    public Iterator<Customer> findAllCustomers() throws Exception {
        return customers.iterator();
    }


}
