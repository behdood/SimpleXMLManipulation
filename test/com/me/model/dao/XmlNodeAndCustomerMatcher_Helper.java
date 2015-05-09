package com.me.model.dao;


import com.me.model.dto.*;
import org.hamcrest.Matchers;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class XmlNodeAndCustomerMatcher_Helper {

    String base_query;

    public XmlNodeAndCustomerMatcher_Helper() {

    }

    public void assertDocumentContainsCustomer(Node root_node, Customer customer) {
        assertThat(root_node, Matchers.hasXPath("/Customers[count(Customer) > 0]"));
//        boolean is_found = false;
//        NodeList children = root_node.getChildNodes();
//        for (int i = 0; i < children.getLength(); i++) {
//            Node child = children.item(i);
//            if (!(child instanceof Element))
//                continue;
//            try {
//                assertNodeMatchesCustomer(children.item(i), customer);
//                is_found = true;
//            } catch (Exception ignored) { }
//        }
//
//        if (!is_found)
//            fail("Customer not found in xml");
        assertNodeMatchesCustomer(root_node, customer);
    }

    public void assertDocumentNotContainCustomer(Node root_node, Customer customer) {
        String query = "/Customers[count(Customer[Name = \"" + customer.getName().toString() + "\"]) = 0]";
        assertThat(root_node, Matchers.hasXPath(query));
    }


    private void assertNodeMatchesCustomer(Node node, Customer customer) {
        base_query = "/Customers/Customer";
        assertNodeMatchesCustomerName(node, customer.getName());
        assertNodeMatchesContactDetails(node, customer.getContactDetails());
        assertNodeMatchesNotes(node, customer.getNotes());
    }

    private void assertNodeMatchesCustomerName(Node node, Name name) {
        assertThat(node, Matchers.hasXPath(base_query + "[Name = \"" + name.toString() + "\"]"));
    }

    private void assertNodeMatchesContactDetails(Node node, List<ContactDetail> contactDetails) {
        for (ContactDetail detail : contactDetails) {
            if (detail instanceof Address)
                assertNodeMatchesAddress(node, (Address) detail);
            else if (detail instanceof Email)
                assertNodeMatchesEmail(node, (Email) detail);
            else if (detail instanceof Phone)
                assertNodeMatchesPhone(node, (Phone) detail);
            else
                fail("Unknown contact detail : " + detail.getContactType());
        }
    }

    private void assertNodeMatchesAddress(Node node, Address address) {
        String query = base_query + "/Address[" +
                "Type = \"" + address.getType() + "\" and " +
                "Street[1] = \"" + address.getStreet1() + "\" and " +
                "Street[2] = \"" + address.getStreet2() + "\" and " +
                "PostalCode = \"" + address.getPostalCode() + "\" and " +
                "Town = \"" + address.getTown() + "\"]";
        assertThat(node, Matchers.hasXPath(query));
    }

    private void assertNodeMatchesEmail(Node node, Email email) {
        String query = base_query + "/Email[" +
                "Type = \"" + email.getType() + "\" and " +
                "Value = \"" + email.getValue() + "\"]";
        assertThat(node, Matchers.hasXPath(query));
    }

    private void assertNodeMatchesPhone(Node node, Phone phone) {
        String query = base_query + "/Phone[Type = \"" + phone.getType() + "\" and Value = \"" + phone.getValue() + "\"]";
        assertThat(node, Matchers.hasXPath(query));
    }

    private void assertNodeMatchesNotes(Node node, String notes) {
        assertThat(node, Matchers.hasXPath(base_query + "/Notes", equalToIgnoringWhiteSpace(notes)));
    }
}
