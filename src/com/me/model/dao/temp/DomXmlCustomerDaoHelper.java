package com.me.model.dao.temp;


import com.me.model.exceptions.InconsistentNodeException;
import com.me.model.dto.*;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DomXmlCustomerDaoHelper {

    public Customer parseCustomerFromNode(Node node) throws InconsistentNodeException {
        if (!node.getNodeName().equals("Customer"))
            throw new InconsistentNodeException("Should be a Customer element. Instead, it is : " + node.getNodeName());

        Node first_child = node.getFirstChild();
        if (!first_child.getNodeName().equals("Name"))
            throw new InconsistentNodeException("Should be a Name element. Instead, it is : " + first_child.getNodeName());

        String customer_name = first_child.getNodeValue();
        Customer customer = new Customer(customer_name, "", '\0');

        NodeList children = node.getChildNodes();
        for (int i = 1; i < children.getLength(); i++) {
            Node child = children.item(i);
            String child_name = child.getNodeName();

            if (child_name.equals("Address"))
                customer.addAddress((Address) parseContactDetailFromNode(child));
            else if (child_name.equals("Phone"))
                customer.addPhone((Phone) parseContactDetailFromNode(child));
            else if (child_name.equals("Email"))
                customer.addEmail((Email) parseContactDetailFromNode(child));
            else if (child_name.equals("Notes"))
                customer.setNotes(child.getNodeValue());
            else
                throw new InconsistentNodeException("Unknown child of Customer element : " + child_name);
        }
        return customer;
    }

    private ContactDetail parseContactDetailFromNode(Node node) throws InconsistentNodeException {
        NodeList children = node.getChildNodes();

        String s = node.getNodeName();
        int children_count;
        if (s.equals("Address")) {
            children_count = 5;
        } else if (s.equals("Email") || s.equals("Phone")) {
            children_count = 2;
        } else
            throw new InconsistentNodeException("Unknown child of Customer element : " + s);

        Iterator<String> vals = traverseNodeList(children, children_count);

        if (s.equals("Address"))
            return new Address(vals.next(), vals.next(), vals.next(), vals.next(), vals.next());
        else if (s.equals("Email"))
            return new Email(vals.next(), vals.next());
        else // s.equals("Phone"))
            return new Phone(vals.next(), vals.next());
    }

    private Iterator<String> traverseNodeList(NodeList nodeList, int childrenCount) throws InconsistentNodeException {
        if (nodeList.getLength() != childrenCount)
            throw new InconsistentNodeException(
                    "Element has " + nodeList.getLength() + " children instead of " + childrenCount);

        List<String> children_values = new ArrayList<String>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            children_values.add(nodeList.item(i).getNodeValue());
        }
        return children_values.iterator();
    }

}
