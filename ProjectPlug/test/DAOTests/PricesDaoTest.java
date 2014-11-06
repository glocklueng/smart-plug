/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAOTests;


import DAO.PricesDao;
import Model.Prices;
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
public class PricesDaoTest {
    PricesDao pricesDao;
    Prices pricesToBeAdded;
    Prices pricesToBeFound;
    Prices pricesToBeDeleted;
 public PricesDaoTest() {
        
    }
    
    @Before
    public void setUp() {
       pricesDao = new PricesDao();
       pricesToBeAdded = new Prices ("Brøndby", 3.5, 6.9);
       pricesToBeFound = new Prices ("FCK", 3.0, 6.5);
       pricesToBeDeleted = new Prices ("Pakistan", 3.5, 999.9);
    }
    
    @After
    public void tearDown() {
       pricesDao.deletePrices(pricesToBeAdded.getLocation());
       pricesDao.deletePrices(pricesToBeFound.getLocation());
    }

    /**
     * Test of get Customer by id method, of class CsustomerDao.
     */
    @Test
    public void test1addPrices(){
        int value = pricesDao.addPrices(pricesToBeAdded);
        assertEquals(1,value);
    }
   
    
    @Test
    public void test2findPrices()
    {
        pricesDao.addPrices(pricesToBeFound);
        Prices priceFound = pricesDao.findPrices(pricesToBeFound.getLocation());
        assertEquals(pricesToBeFound.getLocation(), priceFound.getLocation());
    }
  
    @Test 
    public void test3deletePrices()
    {  
        pricesDao.addPrices(pricesToBeDeleted);
        int value = pricesDao.deletePrices(pricesToBeDeleted.getLocation());
        assertEquals(1,value);
    }
}
