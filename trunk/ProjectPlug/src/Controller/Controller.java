/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.CustomerDao;
import Model.Customer;
import View.CreateCustomerViewPanel;
import View.DeleteCustomerViewPanel;
import View.MainInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class Controller {

    CreateCustomerViewPanel createCustomerViewPanel;
    MainInterface mainInterface;
    DeleteCustomerViewPanel deleteCustomerViewPanel;

    public Controller(MainInterface mainInterface) {
        this.mainInterface = mainInterface;
        this.createCustomerViewPanel = this.mainInterface.getCreateCustomerPanel();
        this.deleteCustomerViewPanel= this.mainInterface.getDeleteCustomerViewPanel1();
        this.createCustomerViewPanel.addButtonCreateCustomerListener(new CreateCustomerListener());
        this.deleteCustomerViewPanel.addButtonSearchListner(new SearchCustomerListener());
    }

    class CreateCustomerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String name;
            String phone;
            String email;
            String password;
            String retypePassword;

            try {
                name = createCustomerViewPanel.getCustomerName();
                phone = createCustomerViewPanel.getPhone();
                email = createCustomerViewPanel.getEmail();
                password = createCustomerViewPanel.getPassword();
                retypePassword = createCustomerViewPanel.getRetypePassword();

                if (password.equals(retypePassword)) {
                    CustomerDao customerDao = new CustomerDao();
                    Customer customer = new Customer(3, name, phone, 0, email, password);
                    customerDao.addCustomer(customer);

                } else {
                    createCustomerViewPanel.displayErrorMessage("Passwords don't match");
                }

            } catch (Exception e) {
                createCustomerViewPanel.displayErrorMessage("Try again.");
            }
        }
    }
    
    class SearchCustomerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String name;
            String phone;
            String email;
            deleteCustomerViewPanel.displayErrorMessage("button Pressed");
            String col[] = {"ID", "Name", "Phone", "Email", "Balance" };
            DefaultTableModel tableModel = new DefaultTableModel(col, 0);
            deleteCustomerViewPanel.addTableModel(tableModel);
            try {
                name = deleteCustomerViewPanel.getCustomerName();
                phone = deleteCustomerViewPanel.getPhone();
                email = deleteCustomerViewPanel.getEmail();
                deleteCustomerViewPanel.displayErrorMessage("button Pressed");
                CustomerDao customerDao= new CustomerDao();
                ArrayList<Customer> customers= customerDao.findCustomers(name, email, phone);
                for (Customer c: customers){
                    Object[] objs = {c.getId(),c.getName(),c.getPhone(),c.getEmail(),c.getBalance()};
                    tableModel.addRow(objs);
                }
                
               

                }

             catch (Exception e) {
                deleteCustomerViewPanel.displayErrorMessage("Try again.");
            }
        }
    }
}
