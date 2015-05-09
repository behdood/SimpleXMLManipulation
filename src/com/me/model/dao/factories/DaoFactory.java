package com.me.model.dao.factories;


import com.me.model.dao.tentative.AddressDao;
import com.me.model.dao.tentative.CustomerDao;
import com.me.model.dao.tentative.EmailDao;
import com.me.model.dao.tentative.PhoneDao;

public abstract class DaoFactory {

    public static final int DOM_XML = 1;
    public static final int STAX_XML = 2;
    public static final int ORACLE_XE = 3;



    public abstract CustomerDao getCustomerDao();

    public abstract AddressDao getAddressDao();

    public abstract EmailDao getEmailDao();

    public abstract PhoneDao getPhoneDao();


    public static DaoFactory getDaoFactory(int whichFactory) {
        switch (whichFactory) {
            case DOM_XML:
                return new DomXmlDaoFactory();
            case STAX_XML:
                return new StaxXmlDaoFactory();
            default:
                return null;
        }
    }

}
