package com.me.model.dao.factories;


import com.me.model.dao.tentative.*;

public class DomXmlDaoFactory extends DaoFactory {
    @Override
    public CustomerDao getCustomerDao() {

//            InputStream inputStream = new FileInputStream(fileName);
//            OutputStream outputStream = new FileOutputStream(fileName);
//            return new DomXmlCustomerDao(inputStream, outputStream, handler);

        //todo:
//            return new DomXmlCustomerDao(fileName, handler);

        return null;
    }

    @Override
    public AddressDao getAddressDao() {
        return new DomXmlAddressDao();
    }

    @Override
    public EmailDao getEmailDao() {
        return new DomXmlEmailDao();
    }

    @Override
    public PhoneDao getPhoneDao() {
        return new DomXmlPhoneDao();
    }
}
