package com.me.model.dao;


import com.me.model.dao.Utils.FakeXmlString;
import com.me.model.dao.Utils.FileXmlDocumentRWUtils;
import com.me.model.dto.*;

import com.me.model.exceptions.invalid_customer.CustomerDoesNotExistException;
import com.me.model.exceptions.invalid_customer.CustomerAlreadyExistException;
import com.me.model.exceptions.invalid_customer.NullCustomerNameException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.xml.sax.SAXException;


import javax.xml.parsers.*;
import javax.xml.transform.Source;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;
import static org.xmlmatchers.transform.XmlConverters.the;
import static org.xmlmatchers.transform.XmlConverters.xml;
import static org.xmlmatchers.xpath.HasXPath.hasXPath;


public class DomXmlCustomerDaoTest {

    private final String XML_TEST_FILE = "resources/temp_test.xml";
    private final int nInitialCustomers = 3;
    private final File TEST_FILE = new File(XML_TEST_FILE);
    private final String CHAR_SET = "UTF-8";
    private CustomerDao customerDAO;


    @Before
    public void setUp() throws IOException, SAXException, ParserConfigurationException {

        // at the beginning there are customers in file with names: "fn{i} m. ln{i}"
        createTestFileWithCustomers();

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
//        new File(XML_TEST_FILE).delete();
    }


    @Test
    public void addCustomer() throws Exception {
        Customer c0 = makeFakeCustomerConan();
        Customer c1 = makeFakeCustomer("fn");
        Customer c2 = makeFakeCustomer("f n m. l n");
//        Customer c1 = makeFakeCustomer("fn", "", '\0');
//        Customer c2 = makeFakeCustomer("f n", "l n", 'm');


        customerDAO.addCustomer(c0);
        customerDAO.addCustomer(c1);
        customerDAO.addCustomer(c2);


        String xml_string = readFileIntoString();

//        String xml1 = "<mountains><mountain>K2</mountain></mountains>";
//        String xml2 = "<mountains><mountain>\n\tK2\n\t </mountain></mountains>";
//        assertThat(the(xml1), isEquivalentTo(the(xml2)));
        Source xx = the(xml_string);

//        assertThat(xml(xml_string), hasXPath("/Customers"));
//        assertThat(xml(xml_string), hasXPath("/Customers/Customer"));
        MatcherHelper.assertDocumentContainsCorrectNumberOfCustomers(xml(xml_string), nInitialCustomers + 3);
        MatcherHelper.assertDocumentContainsCustomer(xml(xml_string), c0);
        MatcherHelper.assertDocumentContainsCustomer(xml(xml_string), c1);
        MatcherHelper.assertDocumentContainsCustomer(xml(xml_string), c2);
    }

    @Test(expected = NullCustomerNameException.class)
    public void addCustomer_nullNameThrowsException() throws Exception {
        Customer c_null = makeFakeCustomer("");


        customerDAO.addCustomer(c_null);
    }

    @Test(expected = CustomerAlreadyExistException.class)
    public void addCustomer_duplicateThrowsException() throws Exception {
        Customer c = makeFakeCustomer("duplicate");
        Customer c_duplicate = makeFakeCustomer("duplicate");


        customerDAO.addCustomer(c);
        customerDAO.addCustomer(c_duplicate);
    }

    @Test
    public void modifyCustomer_details() throws Exception {
        Customer c_existing = makeFakeCustomer("fn2 m. ln2");
        Customer c_modified = makeFakeCustomer("fn2 m. ln2")
                .setNotes("new notes").addEmail("NEW_EMAIL", "new@new_domain.com");

        customerDAO.modifyCustomer(c_existing.getName(), c_modified);

        String xml_string = readFileIntoString();
        MatcherHelper.assertDocumentContainsCorrectNumberOfCustomers(xml(xml_string), nInitialCustomers);
        MatcherHelper.assertDocumentContainsCustomer(xml(xml_string), c_modified);
        MatcherHelper.assertDocumentNotContainCustomer(xml(xml_string), c_existing);
    }

    @Test
    public void modifyCustomer_name() throws Exception {
        Customer c_existing = makeFakeCustomer("fn2 m. ln2");
        Customer c_modified = makeFakeCustomer("fnew m. lnew");


        customerDAO.modifyCustomer(c_existing.getName(), c_modified);


        String xml_string = readFileIntoString();
        MatcherHelper.assertDocumentContainsCorrectNumberOfCustomers(xml(xml_string), nInitialCustomers);
        MatcherHelper.assertDocumentContainsCustomer(xml(xml_string), c_modified);
        MatcherHelper.assertDocumentNotContainCustomerName(xml(xml_string), c_existing);
    }

    @Test(expected = NullCustomerNameException.class)
    public void modifyCustomer_name_nullNameThrowsException() throws Exception {
        Customer c_existing = makeFakeCustomer("fn2 m, ln2");
        Customer c_null = makeFakeCustomer("");

        customerDAO.modifyCustomer(c_existing.getName(), c_null);
    }

    @Test(expected = CustomerDoesNotExistException.class)
    public void modifyCustomer_nonExistentThrowsException() throws Exception {
        Customer c_nonexistent = makeFakeCustomer("non existent");

        customerDAO.modifyCustomer(c_nonexistent.getName(), c_nonexistent);
    }

    @Test
    public void removeCustomer() throws Exception {
        Customer c_existing = makeFakeCustomer("fn2 m. ln2");

        customerDAO.removeCustomer(c_existing);

        String xml_string = readFileIntoString();
        MatcherHelper.assertDocumentContainsCorrectNumberOfCustomers(xml(xml_string), nInitialCustomers - 1);
        MatcherHelper.assertDocumentNotContainCustomer(xml(xml_string), c_existing);
        MatcherHelper.assertDocumentNotContainCustomerName(xml(xml_string), c_existing);
    }

    @Test(expected = CustomerDoesNotExistException.class)
    public void removeCustomer_nonExistentThrowsException() throws Exception {
        Customer c_nonexistent = makeFakeCustomer("non existent");

        customerDAO.removeCustomer(c_nonexistent);
    }

    @Test
    public void findCustomerByName() throws Exception {
        Customer c_existing = makeFakeCustomer("fn2 m. ln2");

        Customer res = customerDAO.findCustomerByName(c_existing.getName());

        assertEquals(res, c_existing);
    }

    @Test
    public void findCustomerByName_nonExistentReturnsNull() throws Exception {
        Name name_nonexistent = new Name("non existent");

        Customer res = customerDAO.findCustomerByName(name_nonexistent);

        assertNull(res);
    }

    @Test
    public void findAllCustomers() throws Exception {
        // all three should exist in the document at the beginning
        Customer c1 = makeFakeCustomer("fn1 m. ln1");
        Customer c2 = makeFakeCustomer("fn2 m. ln2");
        Customer c3 = makeFakeCustomer("fn3 m. ln3");

        Iterator<Customer> res = customerDAO.findAllCustomers();

        assertEquals(res.next(), c1);
        assertEquals(res.next(), c2);
        assertEquals(res.next(), c3);
        assertFalse(res.hasNext());
    }

    @Test
    public void findAllCustomers_emptyDocumentReturnsNull() throws Exception {
        // all three should exist in the document at the beginning
        Customer c1 = makeFakeCustomer("fn1 m. ln1");
        Customer c2 = makeFakeCustomer("fn2 m. ln2");
        Customer c3 = makeFakeCustomer("fn3 m. ln3");

        customerDAO.removeCustomer(c1);
        customerDAO.removeCustomer(c2);
        customerDAO.removeCustomer(c3);

        Iterator<Customer> res = customerDAO.findAllCustomers();

        assertNull(res);
    }


    private void createTestFileWithCustomers() throws IOException {
//        if (!TEST_FILE.exists())
//            TEST_FILE.createNewFile();

        String exampleXml = createFakeStringWithCustomers();
        FileOutputStream fos = new FileOutputStream(TEST_FILE);
        fos.write(exampleXml.getBytes());
        fos.close();
    }

    private String createFakeStringWithCustomers() {
        String s = "<Customers>" + "\n";
        for (int i = 1; i <= nInitialCustomers; i++) {
            s += (new FakeXmlString(makeFakeCustomer("fn" + i + " m. " + "ln" + i)).getXmlString());
        }
        s += "</Customers>";
        return s;
    }


    private String readFileIntoString() throws FileNotFoundException {
        return new Scanner(TEST_FILE, CHAR_SET).useDelimiter("\\A").next();
    }


    private Customer makeFakeCustomer(String name) {
        return new Customer(name)
                .addAddress("home", "street name 1", "street name 2", "12345", "fakeville")
                .addPhone("work", "+358555555555")
                .addEmail("work", "fake@fakedomain.com")
                .setNotes(" this is a note ");
    }

    private Customer makeFakeCustomerConan() {
        return new Customer("Conan C. Customer") /*Customer("Conan", "Customer", 'C')*/
                .addAddress("VISITING_ADDRESS", "Customer Street 8 B 9", "(P.O. Box 190)", "12346", "Customerville")
                .addPhone("WORK_PHONE", "+358 555 555 555")
                .addEmail("WORK_EMAIL", "conan.c.customer@example.com")
                .addPhone("MOBILE_PHONE", "+358 50 999 999 999")
                .setNotes("                                            Conan inputStream a customer.\n\t\t");
    }


    private static class MatcherHelper {

        public static void assertDocumentContainsCorrectNumberOfCustomers(Source source, int how_many) {
            assertThat(source, hasXPath("/Customers[count(Customer) = " + how_many + "]"));
        }

        public static void assertDocumentContainsCustomer(Source source, Customer customer) {
            String sub_query_name_only = buildCustomerSpecificSubQuery(customer, true);
            String sub_query = buildCustomerSpecificSubQuery(customer, false);
            assertThat(source, hasXPath("/Customers[count(Customer[" + sub_query_name_only + "]) = 1]"));
            assertThat(source, hasXPath("/Customers/Customer[" + sub_query + "]"));
        }

        public static void assertDocumentNotContainCustomerName(Source source, Customer customer) {
            String sub_query_name_only = buildCustomerSpecificSubQuery(customer, true);
            assertThat(source, not(hasXPath("/Customers/Customer[" + sub_query_name_only + "]")));
        }

        public static void assertDocumentNotContainCustomer(Source source, Customer customer) {
            String sub_query = buildCustomerSpecificSubQuery(customer, false);
            assertThat(source, not(hasXPath("/Customers/Customer[" + sub_query + "]")));
        }


        private static String buildCustomerSpecificSubQuery(Customer customer, boolean name_only) {


            String sub_query = "Name = \"" + customer.getName() + "\"";
            if (name_only)
                return sub_query;

            for (ContactDetail detail : customer.getContactDetails()) {
                if (detail instanceof Address)
                    sub_query += buildAddressSubQuery((Address) detail);
                else if (detail instanceof Email)
                    sub_query += buildEmailSubQuery((Email) detail);
                else if (detail instanceof Phone)
                    sub_query += buildPhoneSubQuery((Phone) detail);
            }

            if (customer.getNotes() != null && !customer.getNotes().equals(""))
                sub_query += " and Notes = \"" + customer.getNotes() + "\"";

            return sub_query;
        }

        private static String buildAddressSubQuery(Address address) {
            return " and Address[Type = \"" + address.getType()
                    + "\" and Street = \"" + address.getStreet1()
                    + "\" and Street = \"" + address.getStreet2()
                    + "\" and PostalCode = \"" + address.getPostalCode()
                    + "\" and Town = \"" + address.getTown() + "\"]";
        }

        private static String buildEmailSubQuery(Email email) {
            return " and Email[Type = \"" + email.getType() + "\" and Value = \"" + email.getValue() + "\"]";
        }

        private static String buildPhoneSubQuery(Phone phone) {
            return " and Phone[Type = \"" + phone.getType() + "\" and Value = \"" + phone.getValue() + "\"]";
        }

    }

}
