package com.me.model.dao.temp;


import com.me.model.dto.Customer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class SaxHandler extends DefaultHandler {
    public List<Customer> customers = new ArrayList<Customer>();

    public void startElement(String uri, String localName,
                             String qName, Attributes attributes) throws SAXException {

        int a = 1;
//            this.elementStack.push(qName);
//
//            if("driver".equals(qName)){
//                Driver driver = new Driver();
//                this.objectStack.push(driver);
//                this.drivers.add(driver);
//            } else if("vehicle".equals(qName)){
//                this.objectStack.push(new Vehicle());
//            }
    }

}