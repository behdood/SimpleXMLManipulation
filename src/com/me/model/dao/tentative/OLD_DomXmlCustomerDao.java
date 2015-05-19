package com.me.model.dao.tentative;


import com.me.model.dao.temp.IReadWriteHandler;
import com.me.model.dto.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.Iterator;

public class OLD_DomXmlCustomerDao implements CustomerDao {

    private Document document;
    private IReadWriteHandler readWriteHandler;
    private String filename;

    private InputStream inputStream;
    private OutputStream outputStream;

//    public OLD_DomXmlCustomerDao(InputStream inputStream, OutputStream outputStream, IReadWriteHandler readWriteHandler) throws ParserConfigurationException, SAXException, IOException {
//        this.inputStream = inputStream;
//        this.outputStream = outputStream;
//        this.readWriteHandler = readWriteHandler;
//
//        document = readWriteHandler.readStreamToDocument(inputStream);
//    }



    public OLD_DomXmlCustomerDao(String filename, IReadWriteHandler xmlReadWriteHandler)
            throws IOException, ParserConfigurationException, SAXException {
        this.filename = filename;
        this.readWriteHandler = xmlReadWriteHandler;

        inputStream = new FileInputStream(filename);
        outputStream = new FileOutputStream(filename);
        document = xmlReadWriteHandler.readStreamToDocument(inputStream);
    }
//    public OLD_DomXmlCustomerDao(String filename) {
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
