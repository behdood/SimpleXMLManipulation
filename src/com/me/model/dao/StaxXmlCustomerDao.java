package com.me.model.dao;


import com.me.model.dao.CustomerDao;
import com.me.model.dto.Customer;
import com.me.model.dto.Name;

import java.util.Iterator;

public class StaxXmlCustomerDao implements CustomerDao {
    @Override
    public void addCustomer(Customer c) {
//        //SAX
//        InputStream xmlInput = new FileInputStream(XML_TEST_FILE);
//        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
//        SAXParser saxParser = saxParserFactory.newSAXParser();
//        SaxHandler saxHandler = new SaxHandler();
//        saxParser.parse(xmlInput, saxHandler);
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void modifyCustomer(Name oldCustomerName, Customer c)  {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public void removeCustomer(Customer c)  {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public Customer findCustomerByName(Name name) {
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public Iterator<Customer> findAllCustomers() {
        throw new RuntimeException("Not implemented yet!");
    }
}
