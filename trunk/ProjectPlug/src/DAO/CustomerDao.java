/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Model.Customer;
import Model.Prices;

/**
 *
 * @author Ibrahim
 */
public class CustomerDao {

    public Customer findCustomer(int id) {
        /*
         Sql query to be executed in order to obtain a result set
         */
        String customerQuery = "select * from customer where id =?";
        //
        // 
        Customer customer = null;
        Connection con = null;

        try {
            con = DerbyDAOFactory.createConnection(); // connection is created
            /*
             The query is saved inside the prepared statment where the needed variable(location)
             is added to the statement. The location changes the question mark inside the query
             */
            PreparedStatement preparedStatement = con.prepareStatement(customerQuery);
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement.toString());

            /*
             By executing query on the prepared statement you obtain a result set
             */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                customer = createCustomerObject(resultSet);// create a customer object by using the result set
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
        return customer;
    }

    public int addCustomer(Customer customer) {
        String insertQuery = "insert into CUSTOMER  values (?,?,?,?,?,?)";
        Connection con = null;
        int rowCount = -1;
        try {
            con = DerbyDAOFactory.createConnection();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, customer.getPassword());
            preparedStatement.setString(3, customer.getName());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setString(5, customer.getEmail());
            preparedStatement.setDouble(6, customer.getBalance());

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
 public int deleteCustomer(int id) {
        String insertQuery = "delete from customer where id =?";
        Connection con = null;
        int rowCount = -1;
        try {
            con = DerbyDAOFactory.createConnection();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setInt(1,id);

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
    private Customer createCustomerObject(ResultSet resultSet) throws SQLException /*
     This method creates a Customer Object from the resultset obtained by 
     executing the SQL query
     */ {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String phone = resultSet.getString("phone");
        double balance = resultSet.getDouble("balance");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");

        return new Customer(id, name, phone, balance, email, password);
    }
}
