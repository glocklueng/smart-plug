/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Morten
 */
public class Transaction {
    
    private int transactionID;
    private int customerID;
    private double amount;
    private String timeDate;
    private String location;
    private String device;
    private double timeSpent;
    
    public Transaction(int transactionID, int customerID, double amount, 
            String timeDate, String location, String device, double timeSpent)
    {
        this.setTransactionID(transactionID);
        this.setCustomerID(customerID);
        this.setAmount(amount);
        this.setTimeDate(timeDate);
        this.setLocation(location);
        this.setDevice(device);
        this.setTimeSpent(timeSpent);
    
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    /**
     * @return the timeSpent
     */
    public double getTimeSpent() {
        return timeSpent;
    }

    /**
     * @param timeSpent the timeSpent to set
     */
    public void setTimeSpent(double timeSpent) {
        this.timeSpent = timeSpent;
    }
    
}
