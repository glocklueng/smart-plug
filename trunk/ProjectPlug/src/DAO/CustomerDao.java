/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Customer;
import Model.Prices;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ibrahim
 */
public class CustomerDao {
    
    public Customer findCustomerById(int id) {
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
    
    public ArrayList<Customer> findCustomers(String name, String email, String phone) {
        String insertQuery = "select * from CUSTOMER where name like '%" +name+  "%'" 
                           + " and email like '%" + email + "%'"
                           + " and phone like '%"+phone+"%'";
        Connection con = null;
        ArrayList customers = new ArrayList<Customer>();
        int rowCount = -1;
        try {
            con = DerbyDAOFactory.createConnection();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);

//            preparedStatement.setString(1, name);
//            preparedStatement.setString(2, email);
//            preparedStatement.setString(3, phone);
            System.err.println(preparedStatement.toString());
           
            /*
             By executing query on the prepared statement you obtain a result set
             */
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                customers.add(createCustomerObject(resultSet));// create a customer object by using the result set
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
        return customers;
    }
    
    public String findPassword(String email) {
        String insertQuery = "select * from CUSTOMER where" 
                           + " email = '" + email + "'";
        Connection con = null;
        int rowCount = -1;
        Customer customer;
        try {
            con = DerbyDAOFactory.createConnection();
            try (PreparedStatement preparedStatement = con.prepareStatement(insertQuery)) {
                System.err.println(preparedStatement.toString());
                
                ResultSet resultSet = preparedStatement.executeQuery();
                
                if (resultSet.next()) {
                    customer = createCustomerObject(resultSet);// create a customer object by using the result set
                    return customer.getPassword();
                }
            }
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
        return null;
        
    }
   
    public int addCustomer(Customer customer) throws SQLException {
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
            throw e;
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
            preparedStatement.setInt(1, id);

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

        public int updateCustomerName(String name, int ID) {
        String insertQuery = "update CUSTOMER "
                + "Set name=? where id=?";
        Connection con = null;
        int rowCount = -1;
        try {
            con = DerbyDAOFactory.createConnection();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, ID);
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
    
    public int updateCustomerPhone(String phone, int ID) {
        String insertQuery = "update CUSTOMER "
                + "Set phone=? where id=?";
        Connection con = null;
        int rowCount = -1;
        try {
            con = DerbyDAOFactory.createConnection();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);

            preparedStatement.setString(1, phone);
            preparedStatement.setInt(2, ID);
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
    
    public int updateCustomerEmail(String email, int ID) {
        String insertQuery = "update CUSTOMER "
                + "Set email=? where id=?";
        Connection con = null;
        int rowCount = -1;
        try {
            con = DerbyDAOFactory.createConnection();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);

            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, ID);
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
    
    public int updateCustomerBalance(double balance, int ID) {
        String insertQuery = "update CUSTOMER "
                + "Set balance=? where id=?";
        Connection con = null;
        int rowCount = -1;
        try {
            con = DerbyDAOFactory.createConnection();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);

            preparedStatement.setDouble(1, balance);
            preparedStatement.setInt(2, ID);
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
