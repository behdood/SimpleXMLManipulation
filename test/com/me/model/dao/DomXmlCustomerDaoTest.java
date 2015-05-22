package com.me.model.dao;


import com.me.model.dao.Utils.FileXmlDocumentRWUtils;
import com.me.model.dto.*;

import com.me.model.exceptions.CustomerDoesNotExistException;
import com.me.model.exceptions.CustomerAlreadyExistException;
import com.me.model.exceptions.InconsistentNodeException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import org.xml.sax.SAXException;


import javax.xml.parsers.*;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;
import static org.xmlmatchers.transform.XmlConverters.the;
import static org.xmlmatchers.transform.XmlConverters.xml;
import static org.xmlmatchers.xpath.HasXPath.hasXPath;


public class DomXmlCustomerDaoTest {

    private final String XML_TEST_FILE = "resources/temp_test.xml";
    private final int nInitialCustomers = 3;

    private CustomerDao customerDAO;
//    private final String CHAR_SET = "UTF-8";

    @Before
    public void setUp() throws IOException, SAXException, ParserConfigurationException {

        // at the beginning there are customers in file with names: "fn{i} m. ln{i}"
        createTestFileWithCustomers(new File(XML_TEST_FILE), nInitialCustomers);

//        NamespaceContext usingNamespaces = new SimpleNamespaceContext().withBinding("my_ns", "http://www.arcusys.fi/customer-example");

        FileXmlDocumentRWUtils rwUtils = new FileXmlDocumentRWUtils(XML_TEST_FILE);
        customerDAO = new DomXmlCustomerDao(rwUtils);

//        //SAX
//        InputStream xmlInput = new FileInputStream(XML_TEST_FILE);
//        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
//        SAXParser saxParser = saxParserFactory.newSAXParser();
//        SaxHandler saxHandler = new SaxHandler();
//        saxParser.parse(xmlInput, saxHandler);
    }

    @After
    public void tearDown() throws IOException {
        new File(XML_TEST_FILE).delete();
    }


    @Test
    public void addCustomer() throws Exception {
        Customer c0 = makeFakeCustomerConan();
        Customer c1 = makeFakeCustomer("fn", "", '\0');
        Customer c2 = makeFakeCustomer("f n", "l n", 'm');


        customerDAO.addCustomer(c0);
        customerDAO.addCustomer(c1);
        customerDAO.addCustomer(c2);


        String xml_string = readFileIntoString();
        assertDocumentContainsManyCustomer(xml(xml_string), nInitialCustomers + 3);
        assertDocumentContainsCustomer(xml(xml_string), c0);
        assertDocumentContainsCustomer(xml(xml_string), c1);
        assertDocumentContainsCustomer(xml(xml_string), c2);
    }

    @Test(expected = CustomerAlreadyExistException.class)
    public void addCustomer_duplicateThrowsException() throws Exception {
        Customer c = makeFakeCustomer("dup", "duplicate", 'm');
        Customer c_duplicate = makeFakeCustomer("dup", "duplicate", 'm');


        customerDAO.addCustomer(c);
        customerDAO.addCustomer(c_duplicate);
    }

    @Test(expected = InconsistentNodeException.class)
    public void addCustomer_nullNameThrowsException() throws Exception {
        Customer c_null = makeFakeCustomer("", "", '\0');


        customerDAO.addCustomer(c_null);
    }

    @Test
    public void modifyCustomer_name() throws Exception {
        Customer c_existing = makeFakeCustomer("fn2", "ln2", 'm');
        Customer c_modified = makeFakeCustomer("fnew", "lnew", 'm');


        customerDAO.modifyCustomer(c_existing.getName(), c_modified);


        String xml_string = readFileIntoString();
        assertDocumentContainsManyCustomer(xml(xml_string), nInitialCustomers);
        assertDocumentContainsCustomer(xml(xml_string), c_modified);
        assertDocumentNotContainCustomer(xml(xml_string), c_existing);
    }

    @Test
    public void modifyCustomer_details() throws Exception { // TODO
        Customer c_modified = makeFakeCustomer("fn2", "ln2", 'm');
        c_modified.setNotes("new notes").addEmail("NEW_EMAIL", "new@new_domain.com");


        customerDAO.modifyCustomer(c_modified.getName(), c_modified);


        String xml_string = readFileIntoString();
        assertDocumentContainsManyCustomer(xml(xml_string), nInitialCustomers);
        assertDocumentContainsCustomer(xml(xml_string), c_modified);
    }

    @Test(expected = CustomerDoesNotExistException.class)
    public void modifyCustomer_NonExistentThrowsException() throws Exception {
        Customer c_nonexistent = makeFakeCustomer("non", "existent", 'm');

        customerDAO.modifyCustomer(c_nonexistent.getName(), c_nonexistent);
    }

    @Test
    public void testRemoveCustomer() throws Exception {
        Customer c_existing = makeFakeCustomer("fn2", "ln2", 'm');


        customerDAO.removeCustomer(c_existing);


        String xml_string = readFileIntoString();
        assertDocumentContainsManyCustomer(xml(xml_string), nInitialCustomers - 1);
        assertDocumentNotContainCustomer(xml(xml_string), c_existing);
    }

    @Test(expected = CustomerDoesNotExistException.class)
    public void removeCustomer_NonExistentThrowsException() throws Exception {
        Customer c_nonexistent = makeFakeCustomer("non", "existent", 'm');

        customerDAO.removeCustomer(c_nonexistent);
    }

    @Test
    public void findCustomerByName() throws Exception {
        Customer c_existing = makeFakeCustomer("fn2", "ln2", 'm');


        Customer res = customerDAO.findCustomerByName(c_existing.getName());


        assertEquals(res, c_existing);
    }

    @Test
    public void findCustomerByName_NonExistentReturnsNull() throws Exception {
        Name name_nonexistent = new Name("non", "existent", 'm');


        Customer res = customerDAO.findCustomerByName(name_nonexistent);


        assertNull(res);
    }


    @Test
    public void findAllCustomers() throws Exception {
        // all three should exist in the document at the beginning
        Customer c1 = makeFakeCustomer("fn1", "ln1", 'm');
        Customer c2 = makeFakeCustomer("fn2", "ln2", 'm');
        Customer c3 = makeFakeCustomer("fn3", "ln3", 'm');


        Iterator<Customer> res = customerDAO.findAllCustomers();


        assertEquals(res.next(), c1);
        assertEquals(res.next(), c2);
        assertEquals(res.next(), c3);
        assertNull(res.hasNext());
    }



    private void createTestFileWithCustomers(File file, int initialCustomers) throws IOException {
        if (!file.exists())
            file.createNewFile();

        String s = "<Customers xmlns=\"http://www.arcusys.fi/customer-example\">";
        for (int i = 0; i < initialCustomers; i++) {
            s += (new FakeXmlString(makeFakeCustomer("fn" + i, "ln" + i, 'm'))).toString();
        }
        s += "</Customers>";

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(s.getBytes());
        fos.close();
    }



//    private void assertDocumentContainsCustomer(Node root_node, Customer customer) {
//        matcher_helper.assertDocumentContainsCustomer(root_node, customer);
//    }

    private void assertDocumentContainsManyCustomer(Source source, int how_many) {
        new XmlNodeAndCustomerMatcher_Helper().assertDocumentContainsManyCustomers(source, how_many);
    }

    private void assertDocumentContainsCustomer(Source source, Customer customer) {
        new XmlNodeAndCustomerMatcher_Helper().assertDocumentContainsCustomer(source, customer);
    }
//    private void assertDocumentContainsCustomer(Node root_element, Customer customer) {
//        new XmlNodeAndCustomerMatcher_Helper().assertDocumentContainsCustomer(root_element, customer);
//    }

//    private void assertDocumentNotContainCustomer(Node root_node, Customer customer) {
//        matcher_helper.assertDocumentNotContainCustomer(root_node, customer);
//    }

    private void assertDocumentNotContainCustomer(Source source, Customer customer) {
        new XmlNodeAndCustomerMatcher_Helper().assertDocumentNotContainCustomer(source, customer);
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
                .setNotes("                                            Conan inputStream a customer.\n\t\t");
    }


    private String readFileIntoString() throws FileNotFoundException {
        return new Scanner(new File(XML_TEST_FILE), "UTF-8").useDelimiter("\\A").next();
    }

    private static Node nodeVersionOf(String exampleXml) throws Exception {
        Element element = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(exampleXml.getBytes()))
                .getDocumentElement();
        return element;
    }

    private StreamSource readFileIntoStreamSource(String filename) {
        return new StreamSource(filename);
    }
//
//    private Element getRootElement() throws IOException, SAXException, ParserConfigurationException {
//        return new FileXmlDocumentRWUtils().readDocument(new FileInputStream(XML_TEST_FILE)).getDocumentElement();
//    }



    private static class FakeXmlString {

        private String fakeString;
        public FakeXmlString(Customer c) {
            fakeString = buildCustomerXmlString(c);
        }

        @Override
        public String toString() {
            return fakeString;
        }

        private String readFakeFile(){
            return /*"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +*/
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
                            "\t\t<Notes>                                            Conan inputStream a customer.\n" +
                            "\t\t</Notes> \n" +
                            "\t</Customer>\n" +
                            "</Customers>\n";
        }

        private String buildCustomerXmlString(Customer c) {
            return "<Customer>"
                    + buildNameXmlString(c.getName())
                    + buildContactDetailXmlString(c.getContactDetails())
                    + buildNotesXmlString(c.getNotes())
                    + "</Customer>";
        }

        private String buildNameXmlString(Name name) {
            return "<Name>" + name.toString() + "</Name>";
        }

        private String buildContactDetailXmlString(List<ContactDetail> contactDetails) {
            String s = "";
            for (ContactDetail detail : contactDetails) {
                if (detail instanceof Address)
                    s += buildAddressXmlString((Address) detail);
                else if (detail instanceof Email)
                    s += buildEmailDetailXmlString((Email) detail);
                else if (detail instanceof Phone)
                    s += buildPhoneDetailXmlString((Phone) detail);
                else
                    throw new RuntimeException("Unknown contact detail : " + detail.getContactType());
            }
            return s;
        }

        private String buildAddressXmlString(Address address) {
            return "<Address>"
                    + "<Type>" + address.getType() + "</Type>"
                    + "<Street>" + address.getStreet1() + "</Street>"
                    + "<Street>" + address.getStreet2() + "</Street>"
                    + "<PostalCode>" + address.getPostalCode() + "</PostalCode>"
                    + "<Town>" + address.getTown() + "</Town>"
                    + "</Address>";
        }

        private String buildEmailDetailXmlString(Email email) {
            return "<Email>"
                    + "<Type>" + email.getType() + "</Type>"
                    + "<Value>" + email.getValue() + "</Value>"
                    + "</Email>";
        }

        private String buildPhoneDetailXmlString(Phone phone) {
            return "<Phone>"
                    + "<Type>" + phone.getType() + "</Type>"
                    + "<Value>" + phone.getValue() + "</Value>"
                    + "</Phone>";
        }

        private String buildNotesXmlString(String notes) {
            return "<Notes>" + notes + "</Notes>";
        }

    }



}
