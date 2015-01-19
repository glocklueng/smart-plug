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
public class Customer {
    private String id;
    private String name;
    private String phone;
    private double balance;
    private String email;
    private String password;
    
   public Customer(String id, String name, String phone, double balance,
           String email, String password)
   {
       this.setId(id);
       this.setName(name);
       this.setBalance(balance);
       this.setEmail(email);
       this.setPassword(password);
       this.setPhone(phone);
   }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public boolean equals(Object anObject){
        if (anObject instanceof Customer)
        {
            Customer theCustomer = (Customer)anObject;
            return theCustomer.getName().equals(name) && theCustomer.getBalance()==(balance) &&
                    theCustomer.getEmail().equals(email) && theCustomer.getPassword().equals(password) && 
                    theCustomer.getId().equals(id) && theCustomer.getPhone().equals(phone);
                    
        }
        return false;
        
    }
  
    
}
