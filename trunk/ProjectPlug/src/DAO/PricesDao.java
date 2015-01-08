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
import java.util.ArrayList;

/**
 *
 * @author Ibrahim
 */
public class PricesDao {

    public ArrayList<Prices> findPrices(String location) {

        String pricesQuery = "select * from PRICES where location like '%" +location+ "%'";

        Connection con = null;
        ArrayList prices = new ArrayList<Prices>();
        int rowCount = -1;
        try {
            con = DerbyDAOFactory.createConnection(); // connection is created
            /*
             The query is saved inside the prepared statment where the needed variable(location)
             is added to the statement. The location changes the question mark inside the query
             */
            PreparedStatement preparedStatement = con.prepareStatement(pricesQuery);
            System.err.println(preparedStatement.toString());

       

            /*
             By executing query on the prepared statement you obtain a result set
             */
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                prices.add(createPricesObject(resultSet)); // create a prices object by using the result set
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

    public int updatePricesDay(double pricesDay, String location) {
        String insertQuery = "update PRICES "
                + "Set price_day= ?  where location=?";
        Connection con = null;
        int rowCount = -1;
        try {
            con = DerbyDAOFactory.createConnection();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setDouble(1, pricesDay);
            preparedStatement.setString(2, location);

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

    public int updatePricesNight(double pricesNight, String location) {
        String insertQuery = "update PRICES "
                + "Set price_night= ?  where location=?";
        Connection con = null;
        int rowCount = -1;
        try {
            con = DerbyDAOFactory.createConnection();
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setDouble(1, pricesNight);
            preparedStatement.setString(2, location);

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
            preparedStatement.setString(1, location);

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
// need to make update prices
