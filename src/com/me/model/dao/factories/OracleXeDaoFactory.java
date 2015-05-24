package com.me.model.dao.factories;


import com.me.model.dao.CustomerDao;

public class OracleXeDaoFactory extends DaoFactory {
    @Override
    public CustomerDao getCustomerDao() {
        throw new RuntimeException("not implemented yet!");
    }
}
