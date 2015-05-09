package com.me.model.dao;


import com.me.model.dao.tentative.IReadWriteHandler;
import com.me.model.dao.tentative.ReadWriteHandler;
import com.me.model.dto.Customer;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.xml.sax.SAXException;


import javax.xml.parsers.*;

import java.io.*;
import java.util.Iterator;

import static org.junit.Assert.*;

public class TestXmlDomCustomerDao {

    ICustomerDAO customerDAO;
//    String xmlFilename = "resources/temp.xml";
//    String namespace;

    InputStream is;
    OutputStream os;
    String xml;
//    Document document;
    Element root_element;

//    NodeList nodeList;
    XmlNodeAndCustomerMatcher_Helper matcher_helper;
    IReadWriteHandler readWriteHandler;

    @BeforeClass
    public static void classSetUp() throws ParserConfigurationException, IOException, SAXException {

    }

    @Before
    public void setUp() throws IOException, SAXException, ParserConfigurationException {
        readWriteHandler = new ReadWriteHandler();
        matcher_helper = new XmlNodeAndCustomerMatcher_Helper();

        xml = readFakeFile();
        initializeInputStream();
        initializeOutputStream();
//        customerDAO = new CustomerDAOFactory().getCustomerDao("xml", xmlFilename, new ReadWriteHandler());
        customerDAO = getFakeStreamsCustomerDao(readWriteHandler);


        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        is.reset();
        Document doc = db.parse(is);
        root_element = doc.getDocumentElement();
//        document = ((CustomerDaoXmlDom)customerDAO).readStreamToDocument();
//        root_element = document.getDocumentElement();


//
//        //SAX
//        InputStream xmlInput = new FileInputStream(xmlFilename);
//        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
//        SAXParser saxParser = saxParserFactory.newSAXParser();
//        SaxHandler saxHandler = new SaxHandler();
//        saxParser.parse(xmlInput, saxHandler);
    }


    @After
    public void tearDown() throws IOException {
//        xmlInputStream.close();
//        xmlOutputStream.close();
    }

    @Test
    public void testAddCustomer() throws Exception {


//        Customer c0 = makeFakeCustomerConan();
        Customer c1 = makeFakeCustomer("fn", "", '\0');
        Customer c2 = makeFakeCustomer("f n", "l n", 'm');

//        customerDAO.addCustomer(c0);
        customerDAO.addCustomer(c1);
        customerDAO.addCustomer(c2);



//        assertDocumentContainsCustomer(root_element, c0);

        assertDocumentContainsCustomer(root_element, c1);
        assertDocumentContainsCustomer(root_element, c2);
    }

    @Test
    public void testModifyCustomer_details() throws Exception {
        Customer c = makeFakeCustomer("fn", "ln", 'm');

        Customer c_modified = c;
        c_modified.setNotes("new notes").addEmail("NEW_EMAIL", "new@new_domain.com");

//        try {
            customerDAO.modifyCustomer(c.getName(), c_modified);
//        } catch (Exception e) {
//            fail("Removing the customer failed!");
//        }

        assertDocumentContainsCustomer(root_element, c_modified);
        assertDocumentNotContainCustomer(root_element, c);
    }

    @Test
    public void testModifyCustomer_name() throws Exception {
        Customer c = makeFakeCustomer("fn", "ln", 'm');

        Customer c_modified = makeFakeCustomer("fnew", "lnew", 'm');
        customerDAO.modifyCustomer(c.getName(), c_modified);


        assertDocumentContainsCustomer(root_element, c_modified);
        assertDocumentNotContainCustomer(root_element, c);
    }

    @Test
    public void testRemoveCustomer() throws Exception {
        Customer c0 = makeFakeCustomerConan();
        Customer c1 = makeFakeCustomer("fn", "", '\0');
        Customer c2 = makeFakeCustomer("f n", "l n", 'm');


        customerDAO.removeCustomer(c0);

        assertDocumentNotContainCustomer(root_element, c0);
    }

    @Test
    public void testFindCustomerByName() throws Exception {
        Customer c1 = makeFakeCustomer("fn1", "ln1", 'm');

        customerDAO.addCustomer(c1);

        Customer res = customerDAO.findCustomerByName(c1.getName());

        assertEquals(res, c1);
    }

    @Test
    public void testFindAllCustomers() throws Exception {
        Customer c1 = makeFakeCustomer("fn1", "ln1", 'm');
        Customer c2 = makeFakeCustomer("fn2", "ln2", 'm');
        Customer c3 = makeFakeCustomer("fn3", "ln3", 'm');

        customerDAO.addCustomer(c1);
        customerDAO.addCustomer(c2);
        customerDAO.addCustomer(c3);


        Iterator<Customer> res = customerDAO.findAllCustomers();

        assertEquals(res.next(), c1);
        assertEquals(res.next(), c2);
        assertEquals(res.next(), c3);
        assertNull(res.hasNext());
    }


    private void assertDocumentContainsCustomer(Node root_node, Customer customer) {
        matcher_helper.assertDocumentContainsCustomer(root_node, customer);
    }

    private void assertDocumentNotContainCustomer(Node root_node, Customer customer) {
        matcher_helper.assertDocumentNotContainCustomer(root_node, customer);
    }


    private Customer makeFakeCustomer(String firstName, String lastName, char middleInitial) {
        return new Customer(firstName, lastName, middleInitial)
                .addAddress("home", "street1", "street2", "12345", "fakeville")
                .addPhone("work", "+358555555555")
                .addEmail("work", "fake@fakedomain.com")
                .setNotes(" this is a note ");
    }

    private Customer makeFakeCustomerConan() {
        return new Customer("Conan", "Customer", 'C')
                .addAddress("VISITING_ADDRESS", "Customer Street 8 B 9", "(P.O. Box 190)", "12346", "Customerville")
                .addPhone("WORK_PHONE", "+358 555 555 555")
                .addEmail("WORK_EMAIL", "conan.c.customer@example.com")
                .addPhone("MOBILE_PHONE", "+358 50 999 999 999")
                .setNotes("                                            Conan is a customer.\n\t\t");
    }


    private String readFakeFile(){
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Customers xmlns=\"http://www.arcusys.fi/customer-example\">\n" +
                "\t<Customer>\n" +
                "\t\t<Name>Conan C. Customer</Name>\n" +
                "\t\t<Address>\n" +
                "\t\t\t<Type>VISITING_ADDRESS</Type>\n" +
                "\t\t\t<Street>Customer Street 8 B 9</Street>\n" +
                "\t\t\t<Street>(P.O. Box 190)</Street>\n" +
                "\t\t\t<PostalCode>12346</PostalCode>\n" +
                "\t\t\t<Town>Customerville</Town>\n" +
                "\t\t</Address>\n" +
                "\t\t<Phone>\n" +
                "\t\t\t<Type>WORK_PHONE</Type>\n" +
                "\t\t\t<Value>+358 555 555 555</Value>\n" +
                "\t\t</Phone>\n" +
                "\t\t<Email>\n" +
                "\t\t\t<Type>WORK_EMAIL</Type>\n" +
                "\t\t\t<Value>conan.c.customer@example.com</Value>\n" +
                "\t\t</Email>\n" +
                "\t\t<Phone>\n" +
                "\t\t\t<Type>MOBILE_PHONE</Type>\n" +
                "\t\t\t<Value>+358 50 999 999 999</Value>\n" +
                "\t\t</Phone>\n" +
                "\t\t<Notes>                                            Conan is a customer.\n" +
                "\t\t</Notes> \n" +
                "\t</Customer>\n" +
                "</Customers>\n";
    }

    private void initializeInputStream() {
        is = new ByteArrayInputStream(xml.getBytes());
    }

    private void initializeOutputStream(){
        StringWriter sw = new StringWriter();
        os = new ByteArrayOutputStream();

    }

    private ICustomerDAO getFakeStreamsCustomerDao(IReadWriteHandler readWriteHandler)
            throws ParserConfigurationException, IOException, SAXException {
        return new CustomerDaoXmlDom(is, os, readWriteHandler);
    }



}
