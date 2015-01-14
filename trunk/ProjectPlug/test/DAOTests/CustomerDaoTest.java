package DAOTests;

import DAO.CustomerDao;
import Model.Customer;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Nicklas
 */
public class CustomerDaoTest {

    CustomerDao customerDao;
    Customer customerToBeAdded;
    Customer customerToBeFound;
    Customer customerToBeDeleted;
    Customer customerToBeUpdated;

    public CustomerDaoTest() {

    }

    @Before //it's called before the tests start
    public void setUp(){
        customerDao = new CustomerDao();
        customerToBeAdded = new Customer("1", "Added", "Added", 100, "12345678", "Added@added.com");
        customerToBeFound = new Customer("2", "Found", "Found", 100, "12345678", "Found@found.com");
        customerToBeDeleted = new Customer("3", "Deleted", "Deleted", 100, "12345678", "Deleted@deleted.com");
        customerToBeUpdated = new Customer("4", "Updated", "Update", 100, "12345678", "Updated@updated.com");

    }

    @After // it's called after the tests ends.
    public void tearDown() { 
        customerDao.deleteCustomer(customerToBeAdded.getId());
        customerDao.deleteCustomer(customerToBeFound.getId());
        customerDao.deleteCustomer(customerToBeUpdated.getId());
    }

    @Test //Testing if the customer is added to the database.
    public void testAddCustomer() throws SQLException {
        int value = customerDao.addCustomer(customerToBeAdded);
        
        assertEquals(value, 1);
    }

    @Test //Tests if it finds the right customer.
    public void testFindCustomerByID() throws SQLException {
        customerDao.addCustomer(customerToBeFound);
        
        Customer customerFound = customerDao.findCustomerById(customerToBeFound.getId());
        assertEquals(customerToBeFound.getName(), customerFound.getName());
        assertEquals(customerToBeFound.getPhone(), customerFound.getPhone());
        assertEquals(customerToBeFound.getEmail(), customerFound.getEmail());
        assertEquals(customerToBeFound.getPassword(), customerFound.getPassword());
        assertEquals(customerToBeFound.getBalance(), customerFound.getBalance(), 0.0);
    }

    @Test
    public void testDeleteCustomer() throws SQLException {
        customerDao.addCustomer(customerToBeDeleted);
        
        int value = customerDao.deleteCustomer(customerToBeDeleted.getId());
        assertEquals(1, value);
    }

    @Test
    public void testUpdateCustomer() throws SQLException {
        customerDao.addCustomer(customerToBeUpdated);
        
        customerDao.updateCustomerName("NewUpdate", customerToBeUpdated.getId());
        assertEquals("NewUpdate", customerDao.findCustomerById(customerToBeUpdated.getId()).getName());
        
        Customer afterUpdate = customerDao.findCustomerById(customerToBeUpdated.getId());
        assertEquals(customerToBeUpdated.getBalance(), afterUpdate.getBalance(), 0.0);
    }
}
