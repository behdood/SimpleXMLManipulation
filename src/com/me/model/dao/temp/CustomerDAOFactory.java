package com.me.model.dao.temp;


import com.me.model.dao.tentative.OLD_DomXmlCustomerDao;
import com.me.model.dao.tentative.CustomerDao;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class CustomerDAOFactory {

    public CustomerDao getCustomerDao(String type, String fileName, IReadWriteHandler handler)
            throws IOException, SAXException, ParserConfigurationException {
        if (type.equals("xml")) {
//            InputStream inputStream = new FileInputStream(fileName);
//            OutputStream outputStream = new FileOutputStream(fileName);
//            return new OLD_DomXmlCustomerDao(inputStream, outputStream, handler);
            return new OLD_DomXmlCustomerDao(fileName, handler);
        }

        return null;
    }


}
