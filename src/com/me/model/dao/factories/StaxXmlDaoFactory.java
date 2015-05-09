package com.me.model.dao.factories;


import com.me.model.dao.tentative.*;

public class StaxXmlDaoFactory extends DaoFactory {
    @Override
    public CustomerDao getCustomerDao() {
        return new StaxXmlCustomerDao();
    }

    @Override
    public AddressDao getAddressDao() {
        return new StaxXmlAddressDao();
    }

    @Override
    public EmailDao getEmailDao() {
        return new StaxXmlEmailDao();
    }

    @Override
    public PhoneDao getPhoneDao() {
        return new StaxXmlPhoneDao();
    }
}
