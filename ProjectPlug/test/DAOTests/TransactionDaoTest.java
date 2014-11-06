/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOTests;

import DAO.TransactionDao;
import Model.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author Morten + Ibrahim
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
        transactionToBeAdded = new Transaction(444, 333, 333, "2010-10-10 10:10:10", "Amsterdam", "blackberry", 40.0);
        transactionToBeFound = new Transaction(445, 333, 333, "2010-10-10 10:10:10", "Amsterdam", "blackberry", 40.0);
        transactionToBeDeleted = new Transaction(446, 333, 333, "2010-10-10 10:10:10", "Amsterdam", "blackberry", 40.0);

    }

    @After
    public void tearDown() {
        transactionDao.deleteTransaction(transactionToBeAdded.getTransactionID());
        transactionDao.deleteTransaction(transactionToBeFound.getTransactionID());
    }

    /**
     * Test of get Customer by id method, of class CsustomerDao.
     */
    @Test
    public void test1addPrices() {
        int value = transactionDao.addTransaction(transactionToBeAdded);
        assertEquals(1, value);
    }

    @Test
    public void test2findPrices() {
        transactionDao.addTransaction(transactionToBeFound);
        Transaction transactionFound = transactionDao.findTransactions(transactionToBeFound.getTransactionID());
        assertEquals(transactionToBeFound.getTransactionID(), transactionFound.getTransactionID());

    }

    @Test
    public void test3deletePrices() {
        transactionDao.addTransaction(transactionToBeDeleted);
        int value = transactionDao.deleteTransaction(transactionToBeDeleted.getTransactionID());
        assertEquals(value, 1);
    }
}
