package com.me.model.dao;


import com.me.model.dao.tentative.IReadWriteHandler;
import com.me.model.dto.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

public class CustomerDaoXmlDom implements ICustomerDAO {

    private Document document;
    private IReadWriteHandler readWriteHandler;

    private InputStream inputStream;
    private OutputStream outputStream;

    public CustomerDaoXmlDom(InputStream inputStream, OutputStream outputStream, IReadWriteHandler readWriteHandler) throws ParserConfigurationException, SAXException, IOException {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.readWriteHandler = readWriteHandler;

        document = readWriteHandler.readStreamToDocument(inputStream);
    }


//    public DomXmlCustomerDao(String filename, IReadWriteHandler xmlReadWriteHandler) {
//        this.filename = filename;
//        this.readWriteHandler = xmlReadWriteHandler;
//
//        document = xmlReadWriteHandler.readStreamToDocument();
//    }
//    public DomXmlCustomerDao(String filename) {
//        this.filename = filename;
//    }

    @Override
    public void addCustomer(Customer c) throws Exception {
//        doc = dom.getDocumentElement();

    }

    @Override
    public void modifyCustomer(Name oldCustomerName, Customer c) throws Exception {

    }

    @Override
    public void removeCustomer(Customer c) throws Exception {

    }

    @Override
    public Customer findCustomerByName(Name name) throws Exception {
        return null;
    }

    @Override
    public Iterator<Customer> findAllCustomers() throws Exception {
        return null;
    }



//    private boolean nodeMatchesContactDetail(Node node, ContactDetail detail) {
//        NodeList children = node.getChildNodes();
//        if (detail instanceof Address) {
//            if (children.getLength() != 5) return false;
//
//        }
//        if (detail instanceof Phone || detail instanceof Email) {
//            if (children.getLength() != 2) return false;
//        }
//    }

    Document readStreamToDocument() throws ParserConfigurationException, SAXException, IOException {
        return readWriteHandler.readStreamToDocument(inputStream);
    }

    void writeDocumentToStream(Document document) throws TransformerException {
        readWriteHandler.writeDocumentToStream(document, outputStream);
    }
}
