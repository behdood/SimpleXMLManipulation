package com.me.model.bl;


import com.me.model.FakeCustomerBuilder;
import com.me.model.dao.factories.DaoFactory;
import com.me.model.dto.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class CustomerBLTest {

    FakeCustomerDao fakeCustomerDao;
    CustomerIX customerBL;

    private final int nInitialCustomer = 4;

    @Before
    public void setUp() {

        DaoFactory daoFactory = new FakeCustomerDaoFactory();
        customerBL = new CustomerBL(daoFactory);

        fakeCustomerDao = (FakeCustomerDao) daoFactory.getCustomerDao();
        fakeCustomerDao.initialize(nInitialCustomer);
    }

    @After
    public void tearDown() {
        fakeCustomerDao.cleanup();
    }

    @Test
    public void insertNewCustomer() {
        Customer c0 = makeFakeCustomerConan();
        Customer c1 = makeFakeCustomer("fn");
        Customer c2 = makeFakeCustomer("f n m. l n");

        boolean res0 = customerBL.insertCustomer(c0);
        boolean res1 = customerBL.insertCustomer(c1);
        boolean res2 = customerBL.insertCustomer(c2);

        assertTrue(res0);
        assertTrue(res1);
        assertTrue(res2);
        assertEquals(nInitialCustomer + 3, fakeCustomerDao.customers.size());
        assertEquals(c0, fakeCustomerDao.customers.get(nInitialCustomer));
        assertEquals(c1, fakeCustomerDao.customers.get(nInitialCustomer + 1));
        assertEquals(c2, fakeCustomerDao.customers.get(nInitialCustomer + 2));
    }

    @Test
    public void updateCustomer_modifyDetails() {
        Customer c_existing = makeFakeCustomer("fn2 m. ln2");
        Customer c_modified = makeFakeCustomer("fn2 m. ln2")
                .setNotes("new notes").addEmail("NEW_EMAIL", "new@new_domain.com");
        int existing_idx = fakeCustomerDao.customers.indexOf(c_existing);

        boolean res = customerBL.updateCustomer(c_existing.getName(), c_modified);

        assertTrue(res);
        assertEquals(nInitialCustomer, fakeCustomerDao.customers.size());
        assertEquals(c_modified, fakeCustomerDao.customers.get(existing_idx));
        assertFalse(fakeCustomerDao.customers.contains(c_existing));
    }

    @Test
    public void testUpdateCustomer_modifyName() {
        Customer c_existing = makeFakeCustomer("fn2 m. ln2");
        Customer c_modified = makeFakeCustomer("fnew m. lnew");
        int existing_idx = fakeCustomerDao.customers.indexOf(c_existing);

        boolean res = customerBL.updateCustomer(c_existing.getName(), c_modified);

        assertTrue(res);
        assertEquals(nInitialCustomer, fakeCustomerDao.customers.size());
        assertEquals(c_modified, fakeCustomerDao.customers.get(existing_idx));
        assertFalse(fakeCustomerDao.customers.contains(c_existing));
}

    @Test
    public void testDeleteCustomer() {
        Customer c_existing = makeFakeCustomer("fn2 m. ln2");

        boolean res = customerBL.deleteCustomer(c_existing);

        assertTrue(res);
        assertEquals(nInitialCustomer - 1 , fakeCustomerDao.customers.size());
        assertFalse(fakeCustomerDao.customers.contains(c_existing));
    }

    @Test
    public void testFindCustomerByName() {
        Customer c = makeFakeCustomer("fn2 m. ln2");

        Customer result = customerBL.searchCustomerByName(c.getName());

        assertEquals(c, result);
    }

    @Test
    public void testFindAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        for (int i = 1; i <= nInitialCustomer; i++) {
            customerList.add(makeFakeCustomer("fn" + i + " m. ln" + i));
        }

        Iterator<Customer> returnedCustomers = customerBL.findAllCustomers();

        assertIteratorMatchesListInOrder(returnedCustomers, customerList);
    }


    private void assertIteratorMatchesListInOrder(Iterator<Customer> iter, List<Customer> customers) {
        for (Customer customer : customers) {
            assertEquals(customer, iter.next());
        }
        assertFalse(iter.hasNext());
    }

    private Customer makeFakeCustomer(String name) {
        return FakeCustomerBuilder.makeFakeCustomer(name);
    }

    private Customer makeFakeCustomerConan() {
        return FakeCustomerBuilder.makeFakeCustomerConan();
    }
}
