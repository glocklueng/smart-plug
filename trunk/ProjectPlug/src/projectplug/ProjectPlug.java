/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projectplug;
import DAO.*;
import Model.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ibrahim
 */
public class ProjectPlug {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
          PricesDao pricesDao = new PricesDao();
          pricesDao.deletePrices("Tokyo");
          pricesDao.deletePrices("Copenhagen");
          
          Prices pricesTokyo= new Prices("Tokyo", 70,170);
          pricesDao.addPrices(pricesTokyo);
          Prices pricesCopenhagen = new Prices ("Copenhagen", 10,100);
          pricesDao.addPrices(pricesCopenhagen);
          Prices prices= pricesDao.findPrices("Copenhagen");
          Prices checkPrices = pricesDao.findPrices("Tokyo");
       
//          CustomerDao customerdao = new CustomerDao();
//          
//          Customer Ibrahim = customerdao.findCustomer(123);
//          System.out.println(Ibrahim.getBalance());
          
            TransactionDao transactionsDao = new TransactionDao();             transactionsDao.deleteTransaction(1234567);
            Transaction anyName = new Transaction(1234567,123,1234,"2010-10-10 10:10:10", "Budapest", "Iphone", 180);
            transactionsDao.addTransaction(anyName);
            
            
            Transaction check= transactionsDao.findTransactions(1234567);
            System.out.println("this is the transaction" + check.getLocation() + check.getTransactionID());
            transactionsDao.deleteTransaction(1234567);
          
          CustomerDao customerDao =new CustomerDao();
          Customer customerFromDatabase = customerDao.findCustomer(123);
          System.out.println(customerFromDatabase.getName());
          
          
          Customer newCustomerInDatabase= new Customer(145, "LALA", "123213213",1000,"IKnowHowToDoThis@yes.com","Finally" ); 
        try {
            customerDao.addCustomer(newCustomerInDatabase);
        } catch (SQLException ex) {
            Logger.getLogger(ProjectPlug.class.getName()).log(Level.SEVERE, null, ex);
        }
          customerFromDatabase = customerDao.findCustomer(145);
          System.out.println(customerFromDatabase.getName());
          
          System.out.println(customerDao.deleteCustomer(145));
          
    System.out.println(prices.getLocation()+ ": " + prices.getPrice_day()+": "+ prices.getPrice_night());
    System.out.println(checkPrices.getLocation() + ":" + checkPrices.getPrice_day() +":"+ checkPrices.getPrice_night());
    }
    
}

