package com.me.model;


import com.me.model.bl.CustomerBLTest;
import com.me.model.dao.DomXmlCustomerDaoTest;
import com.me.model.dao.Utils.FileXmlDocumentRWUtilsTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        FileXmlDocumentRWUtilsTest.class,
        DomXmlCustomerDaoTest.class,
        CustomerBLTest.class
})


public class ModelTestSuite {
    // the class remains empty,
    // used only as a holder for the above annotations
}
