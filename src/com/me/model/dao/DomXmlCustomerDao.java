package com.me.model.dao;


import com.me.model.dao.Utils.XmlDocumentRWUtils;
import com.me.model.dto.*;
import com.me.model.exceptions.io.XmlDocumentIOException;
import com.me.model.exceptions.io.XmlDocumentReadException;
import com.me.model.exceptions.io.XmlDocumentWriteException;
import com.me.model.exceptions.invalid_customer.*;
import com.me.model.exceptions.storage.CorruptStorageException;
import com.me.model.exceptions.storage.MalformedXmlNodeException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.*;

public class DomXmlCustomerDao implements CustomerDao {

    private XmlDocumentRWUtils rwUtils;
    private DomXmlCustomerDaoHelper daoHelper;
    private Document document = null;


//    DomXmlCustomerDao(String filename) throws FileNotFoundException {
//        this.rwUtils = new FileXmlDocumentRWUtils();
//        this.filename = filename;
////        initializeStreams();
//        daoHelper = new DomXmlCustomerDaoHelper();
//    }

    public DomXmlCustomerDao(XmlDocumentRWUtils rwUtils) {
        this.rwUtils = rwUtils;
        this.daoHelper = new DomXmlCustomerDaoHelper();
    }


    @Override
    public void addCustomer(Customer c)
            throws NullCustomerNameException, CustomerAlreadyExistException,
            XmlDocumentIOException, InvalidCustomerObjectException {
        Name name = c.getName();
        if (isNameInvalid(name))
            throw new NullCustomerNameException();

        loadDocument();

        if (!findCustomerNodeByChildTag(document, "Name", name.toString()).isEmpty())
            throw new CustomerAlreadyExistException();

        document.getDocumentElement().appendChild(daoHelper.createNodeFromCustomer(document, c));

        saveDocument();
    }

    @Override
    public void modifyCustomer(Name oldCustomerName, Customer c)
            throws NullCustomerNameException, CustomerDoesNotExistException,
            CorruptStorageException, XmlDocumentIOException, InvalidCustomerObjectException {

        if (isNameInvalid(c.getName()))
            throw new NullCustomerNameException();

        loadDocument();

        Node customer_node = findCustomerNodeByName(document, oldCustomerName);
        if (customer_node == null)
            throw new CustomerDoesNotExistException();

        Node new_node = daoHelper.createNodeFromCustomer(document, c);
//        document.getDocumentElement().replaceChild(customer_node, new_node);
        document.getDocumentElement().removeChild(customer_node);
        document.getDocumentElement().appendChild(new_node);

        saveDocument();
    }

    @Override
    public void removeCustomer(Customer c)
            throws CustomerDoesNotExistException, CorruptStorageException, XmlDocumentIOException {
        loadDocument();

        Name name = c.getName();
        Node customer_node = findCustomerNodeByName(document, name);

        if (customer_node == null)
            throw new CustomerDoesNotExistException("Customer with name " + name.toString() + " does not exist.");

        document.getDocumentElement().removeChild(customer_node);

        saveDocument();
    }

    @Override
    public Customer findCustomerByName(Name name)
            throws XmlDocumentReadException, CorruptStorageException {

        loadDocument();
        Element customer_node = findCustomerNodeByName(document, name);
        if (customer_node == null)
            return null;

        return daoHelper.parseCustomerFromElement(customer_node);
        // no need to save anything
    }

    @Override
    public Iterator<Customer> findAllCustomers()
            throws XmlDocumentReadException, CorruptStorageException {

        loadDocument();

        List<Customer> customer_list = new ArrayList<>();

        NodeList nodeList = document.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node n = nodeList.item(i);
            if (n instanceof Element)
                customer_list.add(daoHelper.parseCustomerFromElement((Element) n));
        }

        // no need to save anything
        if (customer_list.size() == 0)
            return null;
        return customer_list.iterator();

//
//
//        Element root_element = getDocument().getDocumentElement();
//        Node customer_node = getFirstChildElement(root_element);
//
//        while (customer_node != null) {
//                customer_list.add(customer_node);
//                customer_node = getNextSiblingElement(customer_node);
//        }
//        return customer_list.iterator();
    }



    private boolean isNameInvalid(Name name) {
        return (name == null || !name.isValid());
    }


    private void loadDocument() throws XmlDocumentReadException {
        if (document == null)
            try {
                document = rwUtils.loadDocument();
            } catch (IOException e) {
                throw new XmlDocumentReadException("Unable to load the document due to: IOException");
            } catch (SAXException e) {
                throw new XmlDocumentReadException("Unable to load the document due to: SAXException");
            } catch (ParserConfigurationException e) {
                throw new XmlDocumentReadException("Unable to load the document due to: ParserConfigurationException");
            }
    }

    private void saveDocument() throws XmlDocumentWriteException {
        try {
            rwUtils.saveDocument(document);
        } catch (TransformerException e) {
            throw new XmlDocumentWriteException("Unable to save the document due to: TransformerException");
        } catch (FileNotFoundException e) {
            throw new XmlDocumentWriteException("Unable to save the document due to: FileNotFoundException");
        }
    }


    private Element findCustomerNodeByName(Document document, Name customer_name) throws CorruptStorageException {
        Set<Element> nodeSet = findCustomerNodeByChildTag(document, "Name", customer_name.toString());

        if (nodeSet.isEmpty())
            return null;

        // there should be only one node with any given name
        if (nodeSet.size() > 1)
            throw new CorruptStorageException("Multiple customers exist with name " + customer_name.toString());

        return nodeSet.iterator().next();
    }

    private Set<Element> findCustomerNodeByChildTag(Document document, String detail_tag, String value) {
        NodeList tag_nodes = document.getElementsByTagName(detail_tag);

        Set<Element> node_set = new HashSet<>();
        for (int i = 0; i < tag_nodes.getLength(); i++) {
            Node n = tag_nodes.item(i);

            if (n.getTextContent().equals(value))
                node_set.add((Element) n.getParentNode());
        }
        return node_set;
    }


    private static class DomXmlCustomerDaoHelper {

        public Customer parseCustomerFromElement(Element customer_element)
                throws MalformedXmlNodeException {
            if (customer_element == null)
                return null;

            if (!customer_element.getNodeName().equals("Customer"))
                throw new MalformedXmlNodeException("Should be a \"Customer\" node. Instead, it is : "
                        + customer_element.getNodeName());

            NodeList names = customer_element.getElementsByTagName("Name");
            if (names.getLength() != 1)
                throw new MalformedXmlNodeException("Customer node should have exactly one \"Name\" child. " +
                        "instead, it has " + names.getLength() + "\n");
            Customer customer = new Customer(names.item(0).getTextContent());

            NodeList notes = customer_element.getElementsByTagName("Notes");
            if (notes.getLength() > 1)
                throw new MalformedXmlNodeException("Customer node could have at most one \"Notes\" child. " +
                        "instead, it has " + notes.getLength() + "\n");
            if (notes.getLength() > 0)
                customer.setNotes(notes.item(0).getTextContent());

            NodeList children = customer_element.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if (!(child instanceof Element))
                    continue;

                String child_name = child.getNodeName();
                if (child_name.equals("Name") || child_name.equals("Notes"))
                    continue;   // Name and notes two have already been dealt with

                customer.addDetail(parseContactDetailFromElement((Element) child, child_name));
            }
            return customer;
        }

        public Node createNodeFromCustomer(Document document, Customer customer)
                throws InvalidCustomerObjectException {

            Element customer_node = document.createElement("Customer");

            customer_node.appendChild(document.createElement("Name")).setTextContent(customer.getName().toString());
            customer_node = (Element) appendContactDetails(document, customer_node, customer.getContactDetails());
            customer_node.appendChild(document.createElement("Notes")).setTextContent(customer.getNotes());

            return customer_node;
        }


        private Node appendContactDetails(Document doc, Node customer_node, List<ContactDetail> contactDetails)
                throws InvalidCustomerObjectException {
            for (ContactDetail detail : contactDetails) {
                if (detail.getContactType().equals(ContactDetail.CONTACT_TYPE.ADDRESS)) {
                    Address address = (Address) detail;
                    Element address_node = doc.createElement("Address");

                    address_node.appendChild(doc.createElement("Type")).setTextContent(address.getType());
                    address_node.appendChild(doc.createElement("Street")).setTextContent(address.getStreet1());
                    address_node.appendChild(doc.createElement("Street")).setTextContent(address.getStreet2());
                    address_node.appendChild(doc.createElement("PostalCode")).setTextContent(address.getPostalCode());
                    address_node.appendChild(doc.createElement("Town")).setTextContent(address.getTown());

                    customer_node.appendChild(address_node);
                } else if (detail.getContactType().equals(ContactDetail.CONTACT_TYPE.PHONE)) {
                    Phone phone = (Phone) detail;
                    Element phone_node = doc.createElement("Phone");

                    phone_node.appendChild(doc.createElement("Type")).setTextContent(phone.getType());
                    phone_node.appendChild(doc.createElement("Value")).setTextContent(phone.getValue());

                    customer_node.appendChild(phone_node);

                } else if (detail.getContactType().equals(ContactDetail.CONTACT_TYPE.EMAIL)) {
                    Email email = (Email) detail;
                    Element email_node = doc.createElement("Email");

                    email_node.appendChild(doc.createElement("Type")).setTextContent(email.getType());
                    email_node.appendChild(doc.createElement("Value")).setTextContent(email.getValue());

                    customer_node.appendChild(email_node);
                } else
                    throw new InvalidCustomerObjectException("unknown contact detail!");
            }

            return customer_node;
        }

        private ContactDetail parseContactDetailFromElement(Element element, String node_name)
                throws MalformedXmlNodeException {
            NodeList children = element.getChildNodes();

            Iterator<String> vals; // todo: check for validity and order of children elements

            switch (node_name) {
                case "Address":
                    vals = traverseNodeList(children, 5);
                    return new Address(vals.next(), vals.next(), vals.next(), vals.next(), vals.next());
                case "Email":
                    vals = traverseNodeList(children, 2);
                    return new Email(vals.next(), vals.next());
                case "Phone":
                    vals = traverseNodeList(children, 2);
                    return new Phone(vals.next(), vals.next());
                default:
                    throw new MalformedXmlNodeException("Unknown child of Customer element : " + node_name);
            }
        }

        private Iterator<String> traverseNodeList(NodeList nodeList, int childrenCount)
                throws MalformedXmlNodeException {
            int nValidChildren = 0;

            List<String> children_values = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node field = nodeList.item(i);
                if (!(field instanceof Element))
                    continue;

                nValidChildren++;
                children_values.add(field.getTextContent());
            }
            if (nValidChildren != childrenCount)
                throw new MalformedXmlNodeException("Element has " + nodeList.getLength()
                        + " children instead of " + childrenCount);

            return children_values.iterator();
        }
    }

}
