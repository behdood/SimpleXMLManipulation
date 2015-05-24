package com.me.model.bl;

import com.me.model.dao.CustomerDao;
import com.me.model.dao.factories.DaoFactory;
import com.me.model.dto.Customer;
import com.me.model.dto.Name;
import com.me.model.exceptions.invalid_customer.CustomerAlreadyExistException;
import com.me.model.exceptions.invalid_customer.CustomerDoesNotExistException;
import com.me.model.exceptions.invalid_customer.InvalidCustomerObjectException;
import com.me.model.exceptions.invalid_customer.NullCustomerNameException;
import com.me.model.exceptions.io.XmlDocumentIOException;
import com.me.model.exceptions.io.XmlDocumentReadException;
import com.me.model.exceptions.storage.CorruptStorageException;

import java.util.Iterator;

public class CustomerBL implements CustomerIX {

    private DaoFactory daoFactory;


    public CustomerBL(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public boolean insertCustomer(Customer customer) {
//        CustomerDao customerDao = factory.getCustomerDao();
        try {
            getCustomerDao().addCustomer(customer);
            return true;
        } catch (NullCustomerNameException | XmlDocumentIOException
                | CustomerAlreadyExistException | InvalidCustomerObjectException e) {
//            e1.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateCustomer(Name oldCustomerName, Customer updatedCustomer) {
        try {
            getCustomerDao().modifyCustomer(oldCustomerName, updatedCustomer);
            return true;
        } catch (NullCustomerNameException | CustomerDoesNotExistException | CorruptStorageException
                | XmlDocumentIOException | InvalidCustomerObjectException e) {
//            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCustomer(Customer customer) {
        try {
            getCustomerDao().removeCustomer(customer);
            return true;
        } catch (CustomerDoesNotExistException | XmlDocumentIOException | CorruptStorageException e) {
//            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Customer searchCustomerByName(Name name) {
        try {
            return getCustomerDao().findCustomerByName(name);
        } catch (XmlDocumentReadException | CorruptStorageException e) {
//            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterator<Customer> findAllCustomers() {
        try {
            return getCustomerDao().findAllCustomers();
        } catch (XmlDocumentReadException | CorruptStorageException e) {
//            e.printStackTrace();
        }
        return null;
    }

    private CustomerDao getCustomerDao() {
        return daoFactory.getCustomerDao();
    }
}
