package com.me.model.dao.temp;


import com.me.model.exceptions.InconsistentNodeException;
import com.me.model.dto.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DomXmlCustomerDaoHelper {

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
