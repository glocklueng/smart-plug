/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author Morten
 */
import Model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Transaction;
import java.util.ArrayList;

public class TransactionDao {
    
    public ArrayList<Transaction> findTransactions() {
        String insertQuery = "select * from TRANSACTIONS";
        Connection con = null;
        ArrayList transactions = new ArrayList<Transaction>();
        int rowCount = -1;
        try {
            con = DerbyDAOFactory.createConnection();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);

            System.err.println(preparedStatement.toString());
           
            /*
             By executing query on the prepared statement you obtain a result set
             */
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                transactions.add(createTransactionsObject(resultSet));// create a customer object by using the result set
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return transactions;
    }
    
     public Transaction findTransactions(int transactionID) {
        /*
         Sql query to be executed in order to obtain a result set
         */
        String TransactionsQuery = "select * from transactions where transaction_id =?";
        //
        // 
        Transaction transaction = null;
        Connection con = null;

        try {
            con = DerbyDAOFactory.createConnection(); // connection is created
            /*
             The query is saved inside the prepared statment where the needed variable(location)
             is added to the statement. The location changes the question mark inside the query
             */
            PreparedStatement preparedStatement = con.prepareStatement(TransactionsQuery);
            preparedStatement.setInt(1, transactionID);

            /*
             By executing query on the prepared statement you obtain a result set
             */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                transaction = createTransactionsObject(resultSet);// create a transaction object by using the result set
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return transaction;
    }
     
   
 
      public int addTransaction(Transaction transaction) {
        String insertQuery = "insert into TRANSACTIONS  values (?,?,?,?,?,?,?)";
        Connection con = null;
        int rowCount = -1;
        try {
            con = DerbyDAOFactory.createConnection();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setInt(1, transaction.getTransactionID());
            preparedStatement.setInt(2, transaction.getCustomerID());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setString(4, transaction.getTimeDate());
            preparedStatement.setString(5, transaction.getLocation());
            preparedStatement.setString(6, transaction.getDevice());
            preparedStatement.setDouble(7, transaction.getTimeSpent());
            
            rowCount = preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return rowCount;
    }
      
        public int deleteTransaction(int transactionID) {
        String insertQuery = "delete from transactions where transaction_id =?";
        Connection con = null;
        int rowCount = -1;
        try {
            con = DerbyDAOFactory.createConnection();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setInt(1,transactionID);

            rowCount = preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return rowCount;
    }


private Transaction createTransactionsObject(ResultSet resultSet) throws SQLException /*
     This method creates a Tranactions Object from the resultset obtained by 
     executing the SQL query
     */ {
        int transactionID = resultSet.getInt("transaction_ID");
        int customerID = resultSet.getInt("customer_ID");
        double amount = resultSet.getDouble("amount");
        String timeDate = resultSet.getString("timeDate");
        String location = resultSet.getString("location");
        String device = resultSet.getString("device");
        double timeSpent = resultSet.getDouble("time_Spent");
        return new Transaction(transactionID, customerID, amount, timeDate, location, device,timeSpent);
    }
    
}
