package com.me.model.dao.factories;


import com.me.model.dao.CustomerDao;
import com.me.model.dao.DomXmlCustomerDao;
import com.me.model.dao.Utils.XmlDocumentRWUtils;

public class DomXmlDaoFactory extends DaoFactory {
    XmlDocumentRWUtils utils;

    public DomXmlDaoFactory(XmlDocumentRWUtils utils) {
        this.utils = utils;
    }


    @Override
    public CustomerDao getCustomerDao() {
        return new DomXmlCustomerDao(utils);
    }
}
