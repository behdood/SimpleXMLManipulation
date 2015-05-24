package com.me.model.dao.factories;


import com.me.model.dao.CustomerDao;
import com.me.model.dao.DomXmlCustomerDao;
import com.me.model.dao.OracleXeCustomerDao;
import com.me.model.dao.StaxXmlCustomerDao;
import com.me.model.dao.Utils.FileXmlDocumentRWUtils;

import java.io.FileNotFoundException;

public abstract class DaoFactory {

    public abstract CustomerDao getCustomerDao();

}
