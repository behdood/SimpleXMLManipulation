package com.me.model.bl;

import com.me.model.dao.factories.DaoFactory;
import com.me.model.dao.tentative.CustomerDao;
import com.me.model.dto.Customer;
import com.me.model.dto.Name;

import java.util.Iterator;

public class CustomerBL implements CustomerIX {

    private CustomerDao customerDao;

    public CustomerBL(CustomerDao customerDAO) {
        this.customerDao = customerDAO;
    }

    @Override
    public boolean insertCustomer(Customer customer) {
//        CustomerDao customerDao = factory.getCustomerDao();
        try {
            customerDao.addCustomer(customer);
        } catch (Exception e) {
            e.printStackTrace();
            //todo
            return false;
        }
        return true;
    }

    @Override
    public boolean updateCustomer(Name oldCustomerName, Customer updatedCustomer) {
//        CustomerDao customerDao = factory.getCustomerDao();
        try {
            customerDao.modifyCustomer(oldCustomerName, updatedCustomer);
        } catch (Exception e) {
            e.printStackTrace();
            //todo
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteCustomer(Customer customer) {
//        CustomerDao customerDao = factory.getCustomerDao();
        try {
            customerDao.removeCustomer(customer);
        } catch (Exception e) {
            e.printStackTrace();
            //todo
            return false;
        }
        return true;
    }

    @Override
    public Customer searchCustomerByName(Name name) {
//        CustomerDao customerDao = factory.getCustomerDao();
        try {
            return customerDao.findCustomerByName(name);
        } catch (Exception e) {
            e.printStackTrace();
            //todo
            return null;
        }
    }

    @Override
    public Iterator<Customer> findAllCustomers() {
//        CustomerDao customerDao = factory.getCustomerDao();
        try {
            return customerDao.findAllCustomers();
        } catch (Exception e) {
            e.printStackTrace();
            //todo
            return null;
        }
    }
}
