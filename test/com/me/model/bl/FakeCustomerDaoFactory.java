package com.me.model.bl;


import com.me.model.dao.CustomerDao;
import com.me.model.dao.factories.DaoFactory;

public class FakeCustomerDaoFactory extends DaoFactory {
    static FakeCustomerDao fakeCustomerDao = null;

    @Override
    public CustomerDao getCustomerDao() {
        if (fakeCustomerDao == null)
            fakeCustomerDao = new FakeCustomerDao();

        return fakeCustomerDao;
    }
}
