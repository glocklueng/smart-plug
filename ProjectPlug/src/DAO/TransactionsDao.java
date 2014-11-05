/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author Morten
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Transactions;

public class TransactionsDao {
    
     public Transactions findTransactions(int transactionID) {
        /*
         Sql query to be executed in order to obtain a result set
         */
        String TransactionsQuery = "select * from transaction where location =?";
        //
        // 
        Transactions transactions = null;
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
                transactions = createTransactionsObject(resultSet);// create a transaction object by using the result set
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
     
   
 
      public int addTransactions(Transactions transactions) {
        String insertQuery = "insert into TRANSACTIONS  values (?,?,?,?,?,?)";
        Connection con = null;
        int rowCount = -1;
        try {
            con = DerbyDAOFactory.createConnection();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setInt(1, transactions.getTransactionID());
            preparedStatement.setInt(2, transactions.getCustomerID());
            preparedStatement.setDouble(3, transactions.getAmount());
            preparedStatement.setString(4, transactions.getTimeDate());
            preparedStatement.setString(5, transactions.getLocation());
            preparedStatement.setString(6, transactions.getDevice());
            
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
      
          public int deleteTransactions(int transactionID) {
        String insertQuery = "delete from transactions where location =?";
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


private Transactions createTransactionsObject(ResultSet resultSet) throws SQLException /*
     This method creates a Tranactions Object from the resultset obtained by 
     executing the SQL query
     */ {
        int transactionID = resultSet.getInt("transactionsID");
        int customerID = resultSet.getInt("customerID");
        double amount = resultSet.getDouble("amount");
        String timeDate = resultSet.getString("timeDate");
        String location = resultSet.getString("location");
        String device = resultSet.getString("device");
        return new Transactions(transactionID, customerID, amount, timeDate, location, device);
    }
    
}
