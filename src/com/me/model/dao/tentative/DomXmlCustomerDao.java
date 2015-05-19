package com.me.model.dao.tentative;


import com.me.model.dao.temp.DomXmlCustomerDaoHelper;
import com.me.model.dao.temp.IReadWriteHandler;
import com.me.model.dao.temp.ReadWriteHandler;
import com.me.model.dto.Customer;
import com.me.model.dto.Name;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Iterator;

public class DomXmlCustomerDao implements CustomerDao {

    private String filename;

    private InputStream inputStream;
    private OutputStream outputStream;
    IReadWriteHandler handler;
    DomXmlCustomerDaoHelper helper;


//    public DomXmlCustomerDao(InputStream inputStream, OutputStream outputStream) throws ParserConfigurationException {
//        this.inputStream = inputStream;
//        this.outputStream = outputStream;
//        this.handler = new ReadWriteHandler();
//        this.helper = new DomXmlCustomerDaoHelper();
//    }

    public DomXmlCustomerDao(String filename) throws FileNotFoundException {
        this.filename = filename;
        initializeStreams();
    }

    public DomXmlCustomerDao() throws FileNotFoundException {
        filename = "resources/Customers.xml";
        initializeStreams();
    }

//    public DomXmlCustomerDao(String filename, IReadWriteHandler xmlReadWriteHandler)
//            /*throws IOException, ParserConfigurationException, SAXException */{
//        this.filename = filename;
//        this.readWriteHandler = xmlReadWriteHandler;

//        inputStream = new FileInputStream(filename);
//        outputStream = new FileOutputStream(filename);
//        document = xmlReadWriteHandler.readStreamToDocument(inputStream);
//    }
//    public OLD_DomXmlCustomerDao(String filename) {
//        this.filename = filename;
//    }

    @Override
    public void addCustomer(Customer c) throws Exception {

    }

    @Override
    public void modifyCustomer(Name oldCustomerName, Customer c) throws Exception {

    }

    @Override
    public void removeCustomer(Customer c) throws Exception {

    }

    @Override
    public Customer findCustomerByName(Name name) throws Exception {
        Document doc = handler.readStreamToDocument(inputStream);
        Element root_element = doc.getDocumentElement();

        boolean found = false;
        Node customer;

        customer = getFirstChildElement(root_element);
        while (!found) {
            // first child of each customer should be the Name tag
            Node customer_name = getFirstChildElement(customer);
            if (customer_name.getNodeName().equals("Name"))
                found = true;
            else
                customer = getNextSiblingElement(customer);
            if (customer == null)
                break;
        }

        return helper.parseCustomerFromNode(customer);
    }

    @Override
    public Iterator<Customer> findAllCustomers() throws Exception {
        return null;
    }

    private Node getFirstChildElement(Node node) {
        Node child = node.getFirstChild();
        while (!(child instanceof Element))
            child = child.getNextSibling();
        return child;
    }

    private Node getNextSiblingElement(Node node) {
        Node next_sibling = node.getNextSibling();
        while (!(next_sibling instanceof Element))
            next_sibling = next_sibling.getNextSibling();
        return next_sibling;
    }

    private void initializeStreams() throws FileNotFoundException {
        inputStream = new FileInputStream(filename);
        outputStream = new FileOutputStream(filename);
    }

    private void resetInputStream() throws IOException {
        inputStream.reset();
    }
}
