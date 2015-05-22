package com.me.model.dao;


import com.me.model.dto.*;
import org.hamcrest.Matchers;
import org.xmlmatchers.namespace.SimpleNamespaceContext;

import javax.xml.namespace.NamespaceContext;
import javax.xml.transform.Source;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.xmlmatchers.xpath.HasXPath.hasXPath;

//import org.xmlmatchers.XmlMatchers;

public class XmlNodeAndCustomerMatcher_Helper {

    String base_query;

    public XmlNodeAndCustomerMatcher_Helper() {
    }

//    public void assertDocumentContainsCustomer(Node root_node, Customer customer) {
//        assertThat(root_node, Matchers.hasXPath("/Customers[count(Customer) > 0]"));
////        boolean is_found = false;
////        NodeList children = root_node.getChildNodes();
////        for (int i = 0; i < children.getLength(); i++) {
////            Node child = children.item(i);
////            if (!(child instanceof Element))
////                continue;
////            try {
////                assertNodeMatchesCustomer(children.item(i), customer);
////                is_found = true;
////            } catch (Exception ignored) { }
////        }
////
////        if (!is_found)
////            fail("Customer not found in xml");
//        assertNodeMatchesCustomer(root_node, customer);
//    }

    public void assertDocumentContainsManyCustomers(Source source, int how_many) {
        assertThat(source, hasXPath("/Customers[count(Customer) = " + how_many + " 0]"));
    }

    public void assertDocumentContainsCustomer(Source source, Customer customer) {
        NamespaceContext ns = new SimpleNamespaceContext().withBinding("my_ns", "http://www.arcusys.fi/customer-example");

//        assertThat(source, hasXPath("/Customers"));
        assertThat(source, hasXPath("/Customers", ns));
        assertThat(source, hasXPath("/Customers[count(Customer) > 0]"));

        assertSourceMatchesCustomer(source, customer);
    }

//    public void assertDocumentNotContainCustomer(Node root_node, Customer customer) {
//        String query = "/Customers[count(Customer[Name = \"" + customer.getName().toString() + "\"]) = 0]";
//        assertThat(root_node, Matchers.hasXPath(query));
//    }

    public void assertDocumentNotContainCustomer(Source source, Customer customer) {
        String query = "/Customers[count(Customer[Name = \"" + customer.getName().toString() + "\"]) = 0]";
        assertThat(source, hasXPath(query));
    }


//    private void assertNodeMatchesCustomer(Node node, Customer customer) {
//        base_query = "/Customers/Customer";
//        assertNodeMatchesCustomerName(node, customer.getName());
//        assertNodeMatchesContactDetails(node, customer.getContactDetails());
//        assertNodeMatchesNotes(node, customer.getNotes());
//    }

    private void assertSourceMatchesCustomer(Source source, Customer customer) {
        base_query = "/Customers/Customer";
        assertSourceMatchesCustomerName(source, customer.getName());
        assertSourceMatchesContactDetails(source, customer.getContactDetails());
        assertSourceMatchesNotes(source, customer.getNotes());
    }

//    private void assertNodeMatchesCustomerName(Node node, Name name) {
//        assertThat(node, Matchers.hasXPath(base_query + "[Name = \"" + name.toString() + "\"]"));
//    }

    private void assertSourceMatchesCustomerName(Source source, Name name) {
        assertThat(source, hasXPath(base_query + "[Name = \"" + name.toString() + "\"]"));
    }

//    private void assertNodeMatchesContactDetails(Node node, List<ContactDetail> contactDetails) {
//        for (ContactDetail detail : contactDetails) {
//            if (detail instanceof Address)
//                assertNodeMatchesAddress(node, (Address) detail);
//            else if (detail instanceof Email)
//                assertNodeMatchesEmail(node, (Email) detail);
//            else if (detail instanceof Phone)
//                assertNodeMatchesPhone(node, (Phone) detail);
//            else
//                fail("Unknown contact detail : " + detail.getContactType());
//        }
//    }

    private void assertSourceMatchesContactDetails(Source source, List<ContactDetail> contactDetails) {
        for (ContactDetail detail : contactDetails) {
            if (detail instanceof Address)
                assertSourceMatchesAddress(source, (Address) detail);
            else if (detail instanceof Email)
                assertSourceMatchesEmail(source, (Email) detail);
            else if (detail instanceof Phone)
                assertSourceMatchesPhone(source, (Phone) detail);
            else
                fail("Unknown contact detail : " + detail.getContactType());
        }
    }

//    private void assertNodeMatchesAddress(Node node, Address address) {
//        String query = base_query + "/Address[" +
//                "Type = \"" + address.getType() + "\" and " +
//                "Street[1] = \"" + address.getStreet1() + "\" and " +
//                "Street[2] = \"" + address.getStreet2() + "\" and " +
//                "PostalCode = \"" + address.getPostalCode() + "\" and " +
//                "Town = \"" + address.getTown() + "\"]";
//        assertThat(node, Matchers.hasXPath(query));
//    }

    private void assertSourceMatchesAddress(Source source, Address address) {
        String query = base_query + "/Address[" +
                "Type = \"" + address.getType() + "\" and " +
                "Street[1] = \"" + address.getStreet1() + "\" and " +
                "Street[2] = \"" + address.getStreet2() + "\" and " +
                "PostalCode = \"" + address.getPostalCode() + "\" and " +
                "Town = \"" + address.getTown() + "\"]";
        assertThat(source, hasXPath(query));
    }

//    private void assertNodeMatchesEmail(Node node, Email email) {
//        String query = base_query + "/Email[" +
//                "Type = \"" + email.getType() + "\" and " +
//                "Value = \"" + email.getValue() + "\"]";
//        assertThat(node, Matchers.hasXPath(query));
//    }

    private void assertSourceMatchesEmail(Source source, Email email) {
        String query = base_query + "/Email[" +
                "Type = \"" + email.getType() + "\" and " +
                "Value = \"" + email.getValue() + "\"]";
        assertThat(source, hasXPath(query));
    }

//    private void assertNodeMatchesPhone(Node node, Phone phone) {
//        String query = base_query + "/Phone[Type = \"" + phone.getType() + "\" and Value = \"" + phone.getValue() + "\"]";
//        assertThat(node, Matchers.hasXPath(query));
//    }

    private void assertSourceMatchesPhone(Source source, Phone phone) {
        String query = base_query + "/Phone[Type = \"" + phone.getType() + "\" and Value = \"" + phone.getValue() + "\"]";
        assertThat(source, hasXPath(query));
    }

//    private void assertNodeMatchesNotes(Node node, String notes) {
//        assertThat(node, Matchers.hasXPath(base_query + "/Notes", equalToIgnoringWhiteSpace(notes)));
//    }

    private void assertSourceMatchesNotes(Source source, String notes) {
        assertThat(source, hasXPath(base_query + "/Notes", equalToIgnoringWhiteSpace(notes)));
    }
}
