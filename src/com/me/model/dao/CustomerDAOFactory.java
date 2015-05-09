package com.me.model.dao;


import com.me.model.dao.tentative.IReadWriteHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class CustomerDAOFactory {

    public ICustomerDAO getCustomerDao(String type, String fileName, IReadWriteHandler handler)
            throws IOException, SAXException, ParserConfigurationException {
        if (type.equals("xml")) {
            InputStream inputStream = new FileInputStream(fileName);
            OutputStream outputStream = new FileOutputStream(fileName);

            return new CustomerDaoXmlDom(inputStream, outputStream, handler);
        }

        return null;
    }


}
