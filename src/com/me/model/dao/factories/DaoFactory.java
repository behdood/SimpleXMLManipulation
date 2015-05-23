package com.me.model.dao.factories;


import com.me.model.dao.CustomerDao;
import com.me.model.dao.DomXmlCustomerDao;
import com.me.model.dao.OracleXeCustomerDao;
import com.me.model.dao.StaxXmlCustomerDao;
import com.me.model.dao.Utils.FileXmlDocumentRWUtils;

import java.io.FileNotFoundException;

public /*abstract*/ class DaoFactory {

    public static final int XML_DOM = 1;
    public static final int XML_STAX = 2;
    public static final int RDB_ORACLE_XE = 3;



//    public abstract CustomerDao getCustomerDao();

    public static CustomerDao getCustomerDao(int type) {
        switch (type) {
            case XML_DOM:
                return new DomXmlCustomerDao(new FileXmlDocumentRWUtils("resources/Customer.xml")); // todo
            case XML_STAX:
                return new StaxXmlCustomerDao();
            case RDB_ORACLE_XE:
                return new OracleXeCustomerDao();
            default:
                return null;
        }
    }

}
