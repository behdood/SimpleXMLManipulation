package com.me.model.bl;


import com.me.model.dao.CustomerDao;
import com.me.model.FakeCustomerBuilder;
import com.me.model.dto.Customer;
import com.me.model.dto.Name;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FakeCustomerDao implements CustomerDao {

    List<Customer> customers;

    public FakeCustomerDao() {
        customers = new ArrayList<>();
    }

    public void initialize(int how_many) {
        for (int i = 1; i <= how_many; i++) {
            customers.add(FakeCustomerBuilder.makeFakeCustomer("fn" + i + " m. " + "ln" + i));
        }
    }

    public void cleanup() {
        customers.clear();
    }



    @Override
    public void addCustomer(Customer c)  {
        customers.add(c);
    }

    @Override
    public void modifyCustomer(Name oldCustomerName, Customer c)  {
        for (Customer customer_in_list : customers) {
            if (customer_in_list.getName().equals(oldCustomerName))
                customers.set(customers.indexOf(customer_in_list), c);
        }
    }

    @Override
    public void removeCustomer(Customer c) {
        customers.remove(c);
    }

    @Override
    public Customer findCustomerByName(Name name) {
        for (Customer customer : customers) {
            if (customer.getName().equals(name))
                return customer;
        }
        return null;
    }

    @Override
    public Iterator<Customer> findAllCustomers() {
        return customers.iterator();
    }

}
