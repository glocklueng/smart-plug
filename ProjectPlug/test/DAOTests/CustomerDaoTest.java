/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOTests;

import DAO.CustomerDao;
import Model.Customer;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;


/**
 *
 * @author Dan
 */


public class CustomerDaoTest {
    
    CustomerDao customerDao;
    Customer customerToBeAdded;
    Customer customerToBeFound;
    Customer customerToBeDeleted;
    public CustomerDaoTest() {
        
    }
    
  
    
    @Before
    public void setUp() //it's called before the tests start
    {
        customerDao= new CustomerDao();
        customerToBeAdded = new Customer(999, "LALA", "123213213",1000,"IKnowHowToDoT2h1is@yes.com","Finally" );
        customerToBeFound = new Customer(992, "LALA", "123213213",1000,"IKnowHowToDoTh23is@yes.com","Finally" );
        customerToBeDeleted =new Customer(922, "LALA", "123213213",1000,"IKn1owHowToDoT2his@yes.com","Finally" );
        
        
    }
    
    @After
    public void tearDown() // it's called after the tests end
    {
        customerDao.deleteCustomer(customerToBeAdded.getId());
        customerDao.deleteCustomer(customerToBeFound.getId());
    }

    /**
     * Test of get Customer by id method, of class CsustomerDao.
     */
    @Test
    public void test1addCustomer(){
        
        int value = customerDao.addCustomer(customerToBeAdded);
        assertEquals(value, 1);
    }
    
    @Test
    public void test2findCustomer()
    {
        customerDao.addCustomer(customerToBeFound);
        Customer customerFound = customerDao.findCustomer(customerToBeFound.getId());
        assertEquals(customerFound.getId(), customerToBeFound.getId());
    }
  
    @Test 
    public void test3deleteCustomer()
    {   customerDao.addCustomer(customerToBeDeleted);
        int value = customerDao.deleteCustomer(customerToBeDeleted.getId());
        assertEquals(value,1);
    }
}
