package com.me.model.bl;

import com.me.model.dao.ICustomerDAO;
import com.me.model.dto.Customer;
import com.me.model.dto.Name;

import java.util.Iterator;

public class CustomerBL implements CustomerIX {

    private ICustomerDAO customerDao;

    public CustomerBL(ICustomerDAO customerDAO) {
        this.customerDao = customerDAO;
    }

    @Override
    public boolean insertCustomer(Customer customer) {
        try {
            customerDao.addCustomer(customer);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateCustomer(Name oldCustomerName, Customer updatedCustomer) {
        try {
            customerDao.modifyCustomer(oldCustomerName, updatedCustomer);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteCustomer(Customer customer) {
        try {
            customerDao.removeCustomer(customer);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Customer searchCustomerByName(Name name) {
        try {
            return customerDao.findCustomerByName(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Iterator<Customer> findAllCustomers() {
        try {
            return customerDao.findAllCustomers();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
