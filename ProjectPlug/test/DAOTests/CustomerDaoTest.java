
package DAOTests;

import DAO.CustomerDao;
import Model.Customer;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    Customer customerToBeUpdated;
    public CustomerDaoTest() {
        
    }
    
  
    
    @Before
    public void setUp() //it's called before the tests start
    {
        customerDao= new CustomerDao();
        customerToBeAdded = new Customer(999, "LALA", "123213213",1000,"IKnowHowToDoT2h1is@yes.com","Finally" );
        customerToBeFound = new Customer(992, "LALA", "123213213",1000,"IKnowHowToDoTh23is@yes.com","Finally" );
        customerToBeDeleted =new Customer(922, "LALA", "123213213",1000,"IKn1owHowToDoT2his@yes.com","Finally" );
        customerToBeUpdated = new Customer(928, "LALA", "123213213",1000,"IKn1owHowToDoT2his@yes.com","Finally" );
        
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
        
        int value=0;
        try {
            value = customerDao.addCustomer(customerToBeAdded);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(value, 1);
    }
    
    @Test
    public void test2findCustomer()
    {
        try {
            customerDao.addCustomer(customerToBeFound);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Customer customerFound = customerDao.findCustomerById(customerToBeFound.getId());
        assertEquals(customerToBeFound.getId(), customerToBeFound.getId());
    }
  
    @Test 
    public void test3deleteCustomer()
    {   try {
            customerDao.addCustomer(customerToBeDeleted);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        int value = customerDao.deleteCustomer(customerToBeDeleted.getId());
        assertEquals(1,value);
    }
    
    @Test
    public void test4updateCustomer()
    {   try {
            customerDao.addCustomer(customerToBeUpdated);
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDaoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    Customer updateCustomer= new Customer(customerToBeUpdated.getId(),"dsadsa","lala",9.0,"yes","sdada");
    int value = customerDao.updateCustomer(updateCustomer);
    assertEquals(1,value);
    Customer afterUpdate= customerDao.findCustomerById(updateCustomer.getId());
    assertEquals(updateCustomer.getBalance(), afterUpdate.getBalance(),0.0);
    
    }
}
