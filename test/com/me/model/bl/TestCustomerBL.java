package com.me.model.bl;


import com.me.model.dao.ICustomerDAO;
import com.me.model.dto.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class TestCustomerBL {

    FakeCustomerDAO fakeCustomerDAO;
    CustomerIX customerBL;

    @Before
    public void setUp() {
        fakeCustomerDAO = new FakeCustomerDAO();
        customerBL = new CustomerBL(fakeCustomerDAO);
    }

    @After
    public void tearDown() {
        fakeCustomerDAO.customers.clear();
    }

    @Test
    public void testInsertNewCustomer() {
        Customer c1 = makeFakeCustomer("fn", "", '\0');
        Customer c2 = makeFakeCustomer("f n", "l n", 'm');

        boolean res1 = customerBL.insertCustomer(c1);
        boolean res2 = customerBL.insertCustomer(c2);

        assertTrue(res1);
        assertTrue(res2);
        assertEquals(c1, fakeCustomerDAO.customers.get(0));
        assertEquals(c2, fakeCustomerDAO.customers.get(1));
    }

    @Test
    public void testUpdateCustomer_modifyDetails() {
        Customer c = makeFakeCustomer("fn", "ln", 'm');
//        Customer c_modified = c;
        Customer c_modified = makeFakeCustomer("fn", "ln", 'm');
        c_modified.addEmail("personal", "personal@fakedomain.com")
                .setNotes("this is the new note");
        fakeCustomerDAO.customers.add(c);

        boolean res = customerBL.updateCustomer(c.getName(), c_modified);

        assertTrue(res);
        assertEquals(c_modified, fakeCustomerDAO.customers.get(0));
        assertFalse(fakeCustomerDAO.customers.contains(c));     // unmodified customer should not exist anymore
    }

    @Test
    public void testUpdateCustomer_modifyName() {
        Customer c = makeFakeCustomer("fn", "ln", 'm');
        Customer c_modified = makeFakeCustomer("fnew", "lnew", 'm');
        fakeCustomerDAO.customers.add(c);

        boolean res = customerBL.updateCustomer(c.getName(), c_modified);

        assertTrue(res);
        assertEquals(c_modified, fakeCustomerDAO.customers.get(0));
        assertFalse(fakeCustomerDAO.customers.contains(c));     // unmodified customer should not exist anymore
}

    @Test
    public void testDeleteCustomer() {
        Customer c = makeFakeCustomer("fn", "ln", 'm');

        boolean res = customerBL.deleteCustomer(c);

        assertTrue(res);
        assertFalse(fakeCustomerDAO.customers.contains(c));
    }

    @Test
    public void testFindCustomerByName() {
        Customer c = makeFakeCustomer("fn", "ln", 'm');
        fakeCustomerDAO.customers.add(c);

        Customer result = customerBL.searchCustomerByName(new Name("fn", "ln", 'm'));

        assertEquals(c, result);
    }

    @Test
    public void testFindAll() {
        List<Customer> customerList = new ArrayList<Customer>();
        customerList.add(makeFakeCustomer("fn1", "ln1", 'm'));
        customerList.add(makeFakeCustomer("fn2", "ln2", 'm'));
        customerList.add(makeFakeCustomer("fn3", "ln3", 'm'));
        fakeCustomerDAO.customers.addAll(customerList);

        Iterator<Customer> returnedCustomers = customerBL.findAllCustomers();

        assertEquals(customerList.iterator(), returnedCustomers);
    }


    private Customer makeFakeCustomer() {
        return makeFakeCustomer("firstname", "lastname", 'm');
    }

    private Customer makeFakeCustomer(String firstName, String lastName, char middleInitial) {
        return new Customer(firstName, lastName, middleInitial)
                .addAddress("home", "street1", "street2", "12345", "fakeville")
                .addPhone("work", "+358555555555")
                .addEmail("work", "fake@fakedomain.com")
                .setNotes(" this is a note ");
    }

    class FakeCustomerDAO implements ICustomerDAO {

        public List<Customer> customers;

        public FakeCustomerDAO() {
            customers = new ArrayList<Customer>();
        }

        public FakeCustomerDAO(Customer c) {
            customers = new ArrayList<Customer>();
            customers.add(c);
        }

        @Override
        public void addCustomer(Customer c) {
            customers.add(c);
        }

        @Override
        public void modifyCustomer(Customer c) {
            for (Customer customer : customers) {
                if (customer.getName().equals(c.getName()))
                    customers.set(customers.indexOf(customer), c);
            }
        }

        @Override
        public void removeCustomer(Customer c) {
            customers.remove(c);
        }

        @Override
        public Customer findCustomerByName(Name name) {
            for (Customer customer : customers) {
                if (customer.getName().equals(name))
                    return customer;
            }
            return null;
        }

        @Override
        public Iterator<Customer> findAllCustomers() {
            return customers.iterator();
        }


    }

}
