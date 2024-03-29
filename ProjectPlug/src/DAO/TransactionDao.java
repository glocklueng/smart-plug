package DAO;

/**
 *
 * @author Morten
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Transaction;
import java.util.ArrayList;

public class TransactionDao {

    //Finds all transactions made.
    public ArrayList<Transaction> findTransactions() {
        String insertQuery = "select * from TRANSACTIONS ORDER BY transaction_id ASC ";
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

            while (resultSet.next()) {
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

    //Finds all transactions from the customer ID.
    public ArrayList<Transaction> findTransactionsByCustomerID(String customerID) {
        String insertQuery = "select * from TRANSACTIONS where customer_ID=?";
        Connection con = null;
        ArrayList transactions = new ArrayList<Transaction>();
        int rowCount = -1;
        try {
            con = DerbyDAOFactory.createConnection();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setString(1, customerID);

            System.err.println(preparedStatement.toString());

            /*
             By executing query on the prepared statement you obtain a result set
             */
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
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

    //Finds all transactions from the transaction ID.
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

    //Adds a new transaction
    public int addTransaction(Transaction transaction) {
        String insertQuery = "insert into TRANSACTIONS  values (?,?,?,?,?,?,?)";
        Connection con = null;
        int rowCount = -1;
        try {
            con = DerbyDAOFactory.createConnection();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setInt(1, transaction.getTransactionID());
            preparedStatement.setDouble(2, transaction.getAmount());
            preparedStatement.setString(3, transaction.getTimeDate());
            preparedStatement.setString(4, transaction.getLocation());
            preparedStatement.setString(5, transaction.getDevice());
            preparedStatement.setDouble(6, transaction.getTimeSpent());
            preparedStatement.setString(7, transaction.getCustomerID());

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

    //Deletes a transaction from the transaction ID.
    public int deleteTransaction(int transactionID) {
        String insertQuery = "delete from transactions where transaction_id =?";
        Connection con = null;
        int rowCount = -1;
        try {
            con = DerbyDAOFactory.createConnection();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setInt(1, transactionID);

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

    //This method creates a Tranactions Object from the resultset obtained by 
    //executing the SQL query
    private Transaction createTransactionsObject(ResultSet resultSet) throws SQLException {

        int transactionID = resultSet.getInt("transaction_ID");
        String customerID = resultSet.getString("customer_ID");
        double amount = resultSet.getDouble("amount");
        String timeDate = resultSet.getString("timeDate");
        String location = resultSet.getString("location");
        String device = resultSet.getString("device");
        double timeSpent = resultSet.getDouble("time_Spent");
        return new Transaction(transactionID, customerID, amount, timeDate, location, device, timeSpent);
    }

}
