package DAOTests;

import DAO.TransactionDao;
import Model.Transaction;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Nicklas
 */
public class TransactionDaoTest {
    TransactionDao transactionDao;
    Transaction transactionToBeAdded;
    Transaction transactionToBeFound;
    Transaction transactionToBeDeleted;

    public TransactionDaoTest() {
    }

    @Before
    public void setUp() {
        transactionDao = new TransactionDao();
        transactionToBeAdded = new Transaction(1, "1", 333, "2015-01-14 10:10:10", "Ballerup", "blackberry", 40.0);
        transactionToBeFound = new Transaction(2, "1", 333, "2015-01-14 10:10:10", "Ballerup", "blackberry", 40.0);
        transactionToBeDeleted = new Transaction(3, "1", 333, "2015-01-14 10:10:10", "Ballerup", "blackberry", 40.0);

    }

    @After
    public void tearDown() {
        transactionDao.deleteTransaction(transactionToBeAdded.getTransactionID());
        transactionDao.deleteTransaction(transactionToBeFound.getTransactionID());
    }

    @Test
    public void testAddPrices() {
        int value = transactionDao.addTransaction(transactionToBeAdded);
        
        assertEquals(1, value);
    }

    @Test
    public void testFindPrices() {
        transactionDao.addTransaction(transactionToBeFound);
        
        Transaction transactionFound = transactionDao.findTransactions(transactionToBeFound.getTransactionID());
        assertEquals(transactionToBeFound.getTransactionID(), transactionFound.getTransactionID());

    }

    @Test
    public void testDeletePrices() {
        transactionDao.addTransaction(transactionToBeDeleted);
        
        int value = transactionDao.deleteTransaction(transactionToBeDeleted.getTransactionID());
        assertEquals(1, value);
    }
}
