package com.me.model.dao;


import com.me.model.dao.Utils.XmlDocumentRWUtils;
import com.me.model.dto.*;
import com.me.model.exceptions.CustomerDoesNotExistException;
import com.me.model.exceptions.CustomerAlreadyExistException;
import com.me.model.exceptions.InconsistentNodeException;
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


//    DomXmlCustomerDao(String filename) throws FileNotFoundException {
//        this.rwUtils = new FileXmlDocumentRWUtils();
//        this.filename = filename;
////        initializeStreams();
//        daoHelper = new DomXmlCustomerDaoHelper();
//    }

    public DomXmlCustomerDao(XmlDocumentRWUtils rwUtils) throws FileNotFoundException {
        this.rwUtils = rwUtils;
        this.daoHelper = new DomXmlCustomerDaoHelper();
    }





    @Override
    public void addCustomer(Customer c) throws InconsistentNodeException, ParserConfigurationException, SAXException, IOException, TransformerException {
        Document doc = loadDocument();

        doc.getDocumentElement().appendChild(daoHelper.createNodeFromCustomer(doc, c));

        saveDocument(doc);
    }

    @Override
    public void modifyCustomer(Name oldCustomerName, Customer c)
            throws ParserConfigurationException, SAXException, IOException, InconsistentNodeException, CustomerDoesNotExistException, CustomerAlreadyExistException, TransformerException {
        Document doc = loadDocument();

//        Node customer_node = findCustomerNodeByChildTag(doc, "Name", oldCustomerName.toString());
        Node customer_node = findCustomerNodeByName(doc, oldCustomerName);
        doc.getDocumentElement().replaceChild(customer_node, daoHelper.createNodeFromCustomer(doc, c));

        //todo: write back to file
        saveDocument(doc);
    }

    @Override
    public void removeCustomer(Customer c) throws Exception {
        Document doc = loadDocument();

        Name name = c.getName();
        Node customer_node = findCustomerNodeByName(doc, name);

        if (customer_node == null)
            throw new CustomerDoesNotExistException("Customer with name " + name.toString() + " does not exist.");

        doc.getDocumentElement().removeChild(customer_node);

        // todo: write back to Document
//        root_element.getOwnerDocument();
//customer_node.getParentNode().re
        saveDocument(doc);

    }

    @Override
    public Customer findCustomerByName(Name name)
            throws CustomerDoesNotExistException, InconsistentNodeException, ParserConfigurationException, SAXException, IOException, CustomerAlreadyExistException {
        Document doc = loadDocument();
        Node customer_node = findCustomerNodeByName(doc, name);

//        Element root_element = getRootElement();
//
//        Document doc = rwUtils.readDocument(new FileInputStream(filename));
//        NodeList all_names = doc.getElementsByTagName("Name");
//
//        for (int i = 0; i < all_names.getLength(); i++) {
//            Node n = all_names.item(i);
//            System.out.println(n.getTextContent());
//        }
//
//        Node p = all_names.item(2).getParentNode();
//        System.out.println(p.getNodeName());
////        doc.removeChild(p);
//
//        doc.getDocumentElement().removeChild(p);
//
//        System.out.println();
//        NodeList all2 = doc.getElementsByTagName("Name");
//        for (int i = 0; i < all2.getLength(); i++) {
//            Node n = all2.item(i);
//            System.out.println(n.getTextContent());
//        }
//
//        Node customer_node = findCustomerNodeByName(root_element, name);
//
//        if (customer_node == null)
//            throw new CustomerDoesNotExistException("Customer with name " + name.toString() + " does not exist.");
        return daoHelper.parseCustomerFromNode(customer_node);
        // no need to save anything
    }

    @Override
    public Iterator<Customer> findAllCustomers() throws Exception {

        Document doc = loadDocument();


        List<Customer> customer_list = new ArrayList<>();

        NodeList nodeList = doc.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node n = nodeList.item(i);
            if (n instanceof Element)
                customer_list.add(daoHelper.parseCustomerFromNode(n));
        }

        // no need to save anything
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

//    private Node getFirstChildElement(Node node) {
//        Node child = node.getFirstChild();
//        while (child != null) {
//            if (child instanceof Element)
//                break;
//            child = child.getNextSibling();
//        }
//        return child;
//    }
//
//    private Node getNextSiblingElement(Node node) {
//        Node next_sibling = node.getNextSibling();
//
//        while (next_sibling != null) {
//            if (next_sibling instanceof Element)
//                break;
//            next_sibling = next_sibling.getNextSibling();
//        }
//        return next_sibling;
//    }

//    private void initializeStreams() throws FileNotFoundException {
////        inputStream = new FileInputStream(filename);
////        outputStream = new FileOutputStream(filename);
//    }

//    private void resetInputStream() throws IOException {
////        inputStream.reset();
//    }

//    private Element getRootElement() throws IOException, SAXException, ParserConfigurationException {
//        return rwUtils.readDocument(new FileInputStream(filename)).getDocumentElement();
//    }


    private Document loadDocument() throws IOException, SAXException, ParserConfigurationException {
        return rwUtils.loadDocument();
    }

    private void saveDocument(Document doc) throws TransformerException, FileNotFoundException {
        rwUtils.saveDocument(doc);
    }


//    private Document getDocument() throws IOException, SAXException, ParserConfigurationException {
//        return rwUtils.readDocument(new FileInputStream(filename));
//    }

//    private Node findCustomerNodeByName(Element root_element, Name customer_name)
//            throws InconsistentNodeException {
//        Node customer_node = getFirstChildElement(root_element);
//        while (customer_node != null) {
//            Node name_node = getFirstChildElement(customer_node);
//
//            // sanity check!
//            if (!name_node.getNodeName().equals("Name"))
//                throw new InconsistentNodeException("first child of each customer node should be the Name tag");
//
//            if (name_node.getTextContent().equals(customer_name.toString()))
//                return customer_node;
//
//            customer_node = getNextSiblingElement(customer_node);
//
//        }
//        return null;
//    }

    private Node findCustomerNodeByName(Document document, Name customer_name)
            throws InconsistentNodeException, CustomerDoesNotExistException, CustomerAlreadyExistException {
        Set<Node> nodeSet = findCustomerNodeByChildTag(document, "Name", customer_name.toString());

        if (nodeSet.isEmpty())
            throw new CustomerDoesNotExistException();


        // there should be only one node with any given name
        if (nodeSet.size() > 1)
            throw new CustomerAlreadyExistException();

        return nodeSet.iterator().next();
    }

    private Set<Node> findCustomerNodeByChildTag(Document document, String detail_tag, String value) {
        NodeList tag_nodes = document.getElementsByTagName(detail_tag);

        Set<Node> node_set = new HashSet<>();
        for (int i = 0; i < tag_nodes.getLength(); i++) {
            Node n = tag_nodes.item(i);

            if (n.getTextContent().equals(value))
                node_set.add(n.getParentNode());
//                return n.getParentNode();
        }

        return node_set;
//        return null;
    }


    private static class DomXmlCustomerDaoHelper {

        public Customer parseCustomerFromNode(Node customer_node) throws InconsistentNodeException {
            if (customer_node == null)
                return null;

            if (!customer_node.getNodeName().equals("Customer"))
                throw new InconsistentNodeException("Should be a Customer node. Instead, it is : " + customer_node.getNodeName());

//        Node first_child = customer_node.getFirstChild();
//        if (!first_child.getNodeName().equals("Name"))
//            throw new InconsistentNodeException("Should be a Name element. Instead, it is : " + first_child.getNodeName());


//        String customer_name = first_child.getNodeValue();
            Customer customer = new Customer("tmp", "", '\0');

            NodeList children = customer_node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if (!(child instanceof Element))
                    continue;

                String child_name = child.getNodeName();
                switch (child_name) {
                    case "Name":
                        customer.setName(new Name(child.getTextContent(), "", '\0'));
                        break;
                    case "Address":
                        customer.addAddress((Address) parseContactDetailFromNode(child));
                        break;
                    case "Phone":
                        customer.addPhone((Phone) parseContactDetailFromNode(child));
                        break;
                    case "Email":
                        customer.addEmail((Email) parseContactDetailFromNode(child));
                        break;
                    case "Notes":
                        customer.setNotes(child.getNodeValue());
                        break;
                    default:
                        throw new InconsistentNodeException("Unknown child of Customer element : " + child_name);
                }
            }
            return customer;
        }

        public Node createNodeFromCustomer(Document document, Customer customer) throws InconsistentNodeException {
            Node customer_node = document.createElement("Customer");
//            Node customer_node = document.createElementNS("Customer" ,document.getNamespaceURI());

            customer_node.appendChild(document.createElement("Name")).setTextContent(customer.getName().toString());
            customer_node = appendContactDetails(document, customer_node, customer.getContactDetails());
            customer_node.appendChild(document.createElement("Notes")).setTextContent(customer.getNotes());

            return customer_node;
        }


        private Node appendContactDetails(Document doc, Node customer_node, List<ContactDetail> contactDetails)
                throws InconsistentNodeException {
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
                }
                else if (detail.getContactType().equals(ContactDetail.CONTACT_TYPE.PHONE)) {
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
                    throw new InconsistentNodeException("unknown contact detail!");
            }

            return customer_node;
        }

        private ContactDetail parseContactDetailFromNode(Node node) throws InconsistentNodeException {
            NodeList children = node.getChildNodes();

            String s = node.getNodeName();
            int children_count;
            switch (s) {
                case "Address":
                    children_count = 5;
                    break;
                case "Email":
                case "Phone":
                    children_count = 2;
                    break;
                default:
                    throw new InconsistentNodeException("Unknown child of Customer element : " + s);
            }

            Iterator<String> vals = traverseNodeList(children, children_count);

            switch (s) {
                case "Address":
                    return new Address(vals.next(), vals.next(), vals.next(), vals.next(), vals.next());
                case "Email":
                    return new Email(vals.next(), vals.next());
                default:
// s.equals("Phone"))
                    return new Phone(vals.next(), vals.next());
            }
        }

        private Iterator<String> traverseNodeList(NodeList nodeList, int childrenCount) throws InconsistentNodeException {
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
                throw new InconsistentNodeException("Element has " + nodeList.getLength() + " children instead of " + childrenCount);

            return children_values.iterator();
        }
    }

}
