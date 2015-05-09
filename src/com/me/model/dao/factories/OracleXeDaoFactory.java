package com.me.model.dao.factories;

import com.me.model.dao.tentative.*;


public class OracleXeDaoFactory extends DaoFactory {
    @Override
    public CustomerDao getCustomerDao() {
        return new OracleXeCustomerDao();
    }

    @Override
    public AddressDao getAddressDao() {
        return new OracleXeAddressDao();
    }

    @Override
    public EmailDao getEmailDao() {
        return new OracleXeEmailDao();
    }

    @Override
    public PhoneDao getPhoneDao() {
        return new OracleXePhoneDao();
    }
}
