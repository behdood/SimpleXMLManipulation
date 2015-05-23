package com.me.model.dao;


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

public interface CustomerDao {

    void addCustomer(Customer c)
            throws NullCustomerNameException, CustomerAlreadyExistException,
            XmlDocumentIOException, InvalidCustomerObjectException;

    void modifyCustomer(Name oldCustomerName, Customer c)
            throws NullCustomerNameException, CustomerDoesNotExistException,
            CorruptStorageException, XmlDocumentIOException, InvalidCustomerObjectException;

    void removeCustomer(Customer c)
            throws CustomerDoesNotExistException, CorruptStorageException, XmlDocumentIOException;

    // names of the customers are unique
    Customer findCustomerByName(Name name)
            throws XmlDocumentReadException, CorruptStorageException;
    //todo: when implementing findBy_otherCriterion, remember to override the equal() of the corresponding class

    Iterator<Customer> findAllCustomers()
            throws XmlDocumentReadException, CorruptStorageException;

}
