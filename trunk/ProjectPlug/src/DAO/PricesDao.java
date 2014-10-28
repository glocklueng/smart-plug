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
import Model.Prices;

/**
 *
 * @author Ibrahim
 */
public class PricesDao {

    public Prices findPrices(String location) {
        /*
         Sql query to be executed in order to obtain a result set
         */
        String pricesQuery = "select * from prices where location =?";
        //
        // 
        Prices prices = null;
        Connection con = null;

        try {
            con = DerbyDAOFactory.createConnection(); // connection is created
            /*
             The query is saved inside the prepared statment where the needed variable(location)
             is added to the statement. The location changes the question mark inside the query
             */
            PreparedStatement preparedStatement = con.prepareStatement(pricesQuery);
            preparedStatement.setString(1, location);

            /*
             By executing query on the prepared statement you obtain a result set
             */
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                prices = createPricesObject(resultSet);// create a prices object by using the result set
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
        return prices;
    }

    public int addPrices(Prices prices) {
        String insertQuery = "insert into PRICES  values (?,?,?)";
        Connection con = null;
        int rowCount = -1;
        try {
            con = DerbyDAOFactory.createConnection();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setString(1, prices.getLocation());
            preparedStatement.setDouble(2, prices.getPrice_day());
            preparedStatement.setDouble(3, prices.getPrice_night());

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

    public int deletePrices(String location) {
        String insertQuery = "delete from prices where location =?";
        Connection con = null;
        int rowCount = -1;
        try {
            con = DerbyDAOFactory.createConnection();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setString(1,location);

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

    private Prices createPricesObject(ResultSet resultSet) throws SQLException /*
     This method creates a Prices Object from the resultset obtained by 
     executing the SQL query
     */ {
        String location = resultSet.getString("location");
        double price_day = resultSet.getDouble("price_day");
        double price_night = resultSet.getDouble("price_night");
        return new Prices(location, price_day, price_night);
    }
}
