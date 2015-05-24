package com.me.model;

import com.me.model.dto.Customer;

public class FakeCustomerBuilder {
    public static Customer makeFakeCustomer(String name) {
        return new Customer(name)
                .addAddress("home", "street name 1", "street name 2", "12345", "fakeville")
                .addPhone("work", "+358555555555")
                .addEmail("work", "fake@fakedomain.com")
                .setNotes(" this is a note ");
    }

    public static Customer makeFakeCustomerConan() {
        return new Customer("Conan C. Customer") /*Customer("Conan", "Customer", 'C')*/
                .addAddress("VISITING_ADDRESS", "Customer Street 8 B 9", "(P.O. Box 190)", "12346", "Customerville")
                .addPhone("WORK_PHONE", "+358 555 555 555")
                .addEmail("WORK_EMAIL", "conan.c.customer@example.com")
                .addPhone("MOBILE_PHONE", "+358 50 999 999 999")
                .setNotes("                                            Conan inputStream a customer.\n\t\t");
    }

}
