package com.me.model.dao.Utils;


import com.me.model.dto.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class FakeXmlString {
    private String xmlString;
    private Customer customer;
    private final String INDENT_LEVEL1 = "  ";
    private final String INDENT_LEVEL2 = "    ";
    private final String INDENT_LEVEL3 = "      ";
    private final String LINE_SEPARATOR = "\n";

    private final String NAMESPACE = "http://www.arcusys.fi/customer-example";



    public FakeXmlString(Customer customer) {
        this.customer = customer;
        this.xmlString = buildCustomerXmlString(customer);
    }

    public String getXmlString() {
        return xmlString;
    }

    public Node getNodeVersionOfXmlString()
            throws ParserConfigurationException, IOException, SAXException {

        return DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new ByteArrayInputStream(xmlString.getBytes()))
                .getDocumentElement();
    }

    public static String readFakeFile() {
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
        return INDENT_LEVEL1 + "<Customer"
//                +  "xmlns=\"" + NAMESPACE + "\""
                + ">" + LINE_SEPARATOR
                + buildNameXmlString(c.getName())
                + buildContactDetailXmlString(c.getContactDetails())
                + buildNotesXmlString(c.getNotes())
                + INDENT_LEVEL1 + "</Customer>" + LINE_SEPARATOR;
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

    private String buildNameXmlString(Name name) {
        return INDENT_LEVEL2 + "<Name>" + name.toString() + "</Name>" + LINE_SEPARATOR;
    }

    private String buildAddressXmlString(Address address) {
        return INDENT_LEVEL2 + "<Address>" + LINE_SEPARATOR
                + INDENT_LEVEL3 + "<Type>" + address.getType() + "</Type>" + LINE_SEPARATOR
                + INDENT_LEVEL3 + "<Street>" + address.getStreet1() + "</Street>" + LINE_SEPARATOR
                + INDENT_LEVEL3 + "<Street>" + address.getStreet2() + "</Street>" + LINE_SEPARATOR
                + INDENT_LEVEL3 + "<PostalCode>" + address.getPostalCode() + "</PostalCode>" + LINE_SEPARATOR
                + INDENT_LEVEL3 + "<Town>" + address.getTown() + "</Town>" + LINE_SEPARATOR
                + INDENT_LEVEL2 + "</Address>" + LINE_SEPARATOR;
    }

    private String buildEmailDetailXmlString(Email email) {
        return INDENT_LEVEL2 + "<Email>" + LINE_SEPARATOR
                + INDENT_LEVEL3 + "<Type>" + email.getType() + "</Type>" + LINE_SEPARATOR
                + INDENT_LEVEL3 + "<Value>" + email.getValue() + "</Value>" + LINE_SEPARATOR
                + INDENT_LEVEL2 + "</Email>" + LINE_SEPARATOR;
    }

    private String buildPhoneDetailXmlString(Phone phone) {
        return INDENT_LEVEL2 + "<Phone>" + LINE_SEPARATOR
                + INDENT_LEVEL3 + "<Type>" + phone.getType() + "</Type>" + LINE_SEPARATOR
                + INDENT_LEVEL3 + "<Value>" + phone.getValue() + "</Value>" + LINE_SEPARATOR
                + INDENT_LEVEL2 + "</Phone>" + LINE_SEPARATOR;
    }

    private String buildNotesXmlString(String notes) {
        return INDENT_LEVEL2 + "<Notes>" + notes + "</Notes>" + LINE_SEPARATOR;
    }

}
