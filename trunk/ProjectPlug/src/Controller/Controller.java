/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.CustomerDao;
import Model.Customer;
import View.CreateCustomerViewPanel;
import View.MainInterface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {

    CreateCustomerViewPanel createCustomerViewPanel;
    MainInterface mainInterface;

    public Controller(MainInterface mainInterface) {
        this.mainInterface = mainInterface;
        this.createCustomerViewPanel = this.mainInterface.getCreateCustomerPanel();
        this.createCustomerViewPanel.addButtonCreateCustomerListener(new CreateCustomerListener());
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
}
