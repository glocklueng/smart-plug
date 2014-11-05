/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projectplug;
import DAO.*;
import Model.*;

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
          
          TransactionsDAO transactionsDao = new TransactionsDAO();
          transactionsDao.deleteTransactions(1);//Delete Transactions by TransactionID(pk)
          
          Transactions transactionKunde1 = new Transactions(1,1,10,"2014-11-03 19:32:16","Hundige","Tryllestav");
          transactionsDao.addTransactions(transactionKunde1);
          
          
          Transactions Lookup = transactionsDao.findTransactions(1);
          System.out.println(Lookup.getCustomerID());
          
          
          
          
    System.out.println(prices.getLocation()+ ":" + prices.getPrice_day()+":"+ prices.getPrice_night());
    System.out.println(checkPrices.getLocation() + ":" + checkPrices.getPrice_day() +":"+ checkPrices.getPrice_night());
    }
    
}

