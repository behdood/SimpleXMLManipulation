package com.me.model.bl;


import com.me.model.dao.FakeCustomerDao;
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

    @Before
    public void setUp() {

        fakeCustomerDao = new FakeCustomerDao();
//        customerBL = new CustomerBL(fakeCustomerDao);
        customerBL = new CustomerBL(fakeCustomerDao);
//        fakeCustomerDao = (FakeCustomerDao) fakeFactory.getCustomerDao();
    }

    @After
    public void tearDown() {
//        fakeCustomerDao.customers.clear();
    }

    @Test
    public void testInsertNewCustomer() {
        Customer c1 = makeFakeCustomer("fn");
        Customer c2 = makeFakeCustomer("f n m. l n");

        boolean res1 = customerBL.insertCustomer(c1);
        boolean res2 = customerBL.insertCustomer(c2);

        assertTrue(res1);
        assertTrue(res2);
        assertEquals(c1, fakeCustomerDao.customers.get(0));
        assertEquals(c2, fakeCustomerDao.customers.get(1));
    }

    @Test
    public void testUpdateCustomer_modifyDetails() {
        Customer c = makeFakeCustomer("fn m. ln");
//        Customer c_modified = c;
        Customer c_modified = makeFakeCustomer("fn m. ln");
        c_modified.addEmail("personal", "personal@fakedomain.com")
                .setNotes("this is the new note");
        fakeCustomerDao.customers.add(c);

        boolean res = customerBL.updateCustomer(c.getName(), c_modified);

        assertTrue(res);
        assertEquals(c_modified, fakeCustomerDao.customers.get(0));
        assertFalse(fakeCustomerDao.customers.contains(c));     // unmodified customer should not exist anymore
    }

    @Test
    public void testUpdateCustomer_modifyName() {
        Customer c = makeFakeCustomer("fn m. ln");
        Customer c_modified = makeFakeCustomer("fnew m. lnew");
        fakeCustomerDao.customers.add(c);

        boolean res = customerBL.updateCustomer(c.getName(), c_modified);

        assertTrue(res);
        assertEquals(c_modified, fakeCustomerDao.customers.get(0));
        assertFalse(fakeCustomerDao.customers.contains(c));     // unmodified customer should not exist anymore
}

    @Test
    public void testDeleteCustomer() {
        Customer c = makeFakeCustomer("fn m. ln");

        boolean res = customerBL.deleteCustomer(c);

        assertTrue(res);
        assertFalse(fakeCustomerDao.customers.contains(c));
    }

    @Test
    public void testFindCustomerByName() {
        Customer c = makeFakeCustomer("fn m. ln");
        fakeCustomerDao.customers.add(c);

        Customer result = customerBL.searchCustomerByName(c.getName());

        assertEquals(c, result);
    }

    @Test
    public void testFindAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(makeFakeCustomer("fn1 m. ln1"));
        customerList.add(makeFakeCustomer("fn2 m. ln2"));
        customerList.add(makeFakeCustomer("fn3 m. ln3"));
        fakeCustomerDao.customers.addAll(customerList);

        Iterator<Customer> returnedCustomers = customerBL.findAllCustomers();

        assertIteratorMatchesListInOrder(customerList, returnedCustomers);
    }


    private void assertIteratorMatchesListInOrder(List values, Iterator i) {
        for (Object value : values) {
            Object item = i.next();
            assertEquals(value, item);
        }
        assertFalse(i.hasNext());
    }

//    private Customer makeFakeCustomer(String firstName, String lastName, char middleInitial) {
//        return new Customer(firstName, lastName, middleInitial)
//                .addAddress("home", "street1", "street2", "12345", "fakeville")
//                .addPhone("work", "+358555555555")
//                .addEmail("work", "fake@fakedomain.com")
//                .setNotes(" this is a note ");
//    }

    private Customer makeFakeCustomer(String name) {
        return new Customer(name)
                .addAddress("home", "street1", "street2", "12345", "fakeville")
                .addPhone("work", "+358555555555")
                .addEmail("work", "fake@fakedomain.com")
                .setNotes(" this is a note ");
    }
}
