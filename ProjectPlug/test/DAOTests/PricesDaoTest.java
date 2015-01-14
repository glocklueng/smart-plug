
package DAOTests;


import DAO.PricesDao;
import Model.Prices;
import java.util.ArrayList;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


/**
 *
 * @author Nicklas
 */
public class PricesDaoTest {
    PricesDao pricesDao;
    Prices pricesToBeAdded;
    Prices pricesToBeFound;
    Prices pricesToBeDeleted;
    Prices pricesToBeUpdated;
    
 public PricesDaoTest() {  
    }
    
    @Before
    public void setUp() {
       pricesDao = new PricesDao();
       pricesToBeAdded = new Prices ("Copenhagen", 3.5, 6.9);
       pricesToBeFound = new Prices ("Ballerup", 3.0, 6.5);
       pricesToBeDeleted = new Prices ("Rødovre", 3.5, 999.9);
       pricesToBeUpdated = new Prices ("Taastrup", 3.5, 999.9);
    }
    
    @After
    public void tearDown() {
       pricesDao.deletePrices(pricesToBeAdded.getLocation());
       pricesDao.deletePrices(pricesToBeFound.getLocation());
       pricesDao.deletePrices(pricesToBeUpdated.getLocation());
    }

    @Test
    public void testAddPrices(){
        int value = pricesDao.addPrices(pricesToBeAdded);
        
        assertEquals(1, value);
    }
   
    
    @Test
    public void testFindPrices() {
        pricesDao.addPrices(pricesToBeFound);
        
        ArrayList<Prices> priceFound = pricesDao.findPrices(pricesToBeFound.getLocation());
        assertEquals(pricesToBeFound.getLocation(), priceFound.get(0).getLocation());
    }
  
    @Test 
    public void testDeletePrices() {  
        pricesDao.addPrices(pricesToBeDeleted);
        
        int value = pricesDao.deletePrices(pricesToBeDeleted.getLocation());
        assertEquals(1,value);
    }
    
    @Test 
    public void testUpdatePrices() {   
        pricesDao.addPrices(pricesToBeUpdated);
        
        Prices updatePrice= new Prices(pricesToBeUpdated.getLocation(), 150, 250);
        pricesDao.updatePricesDay(updatePrice.getPrice_day(), updatePrice.getLocation());
        pricesDao.updatePricesNight(updatePrice.getPrice_night(), updatePrice.getLocation());
        assertEquals(updatePrice.getPrice_day(), pricesDao.findPrices(updatePrice.getLocation()).get(0).getPrice_day(), 0.0);
        assertEquals(updatePrice.getPrice_night(), pricesDao.findPrices(updatePrice.getLocation()).get(0).getPrice_night(), 0.0);
    }
}
