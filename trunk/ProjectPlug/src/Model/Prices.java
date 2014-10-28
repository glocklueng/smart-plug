/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 *
 * @author Ibrahim
 */
public class Prices {
    private String location;
    private double price_day;
    private double price_night;

    public Prices(String location, double price_day, double price_night)
    {
        this.setPrice_day(price_day);
        this.setPrice_night(price_night);
        this.setLocation(location);
    }
    
    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the price_day
     */
    public double getPrice_day() {
        return price_day;
    }

    /**
     * @param price_day the price_day to set
     */
    public void setPrice_day(double price_day) {
        this.price_day = price_day;
    }

    /**
     * @return the price_night
     */
    public double getPrice_night() {
        return price_night;
    }

    /**
     * @param price_night the price_night to set
     */
    public void setPrice_night(double price_night) {
        this.price_night = price_night;
    }
    
}
