package com.me.model.dao.temp;


import com.me.model.dao.DomXmlCustomerDao;
import com.me.model.dao.Utils.FileXmlDocumentRWUtils;
import com.me.model.dao.CustomerDao;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class CustomerDAOFactory {

    public CustomerDao getCustomerDao(String type, String fileName)
            throws IOException, SAXException, ParserConfigurationException {
        if (type.equals("xml")) {
//            InputStream inputStream = new FileInputStream(fileName);
//            OutputStream outputStream = new FileOutputStream(fileName);
//            return new DomXmlCustomerDao(inputStream, outputStream);
            return new DomXmlCustomerDao(new FileXmlDocumentRWUtils("resources/Customer.xml"));
        }

        return null;
    }


}
