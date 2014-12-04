/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.CustomerDao;
import DAO.PricesDao;
import DAO.TransactionDao;
import Model.Customer;
import Model.Prices;
import Model.Transaction;
import View.CreateCustomerViewPanel;
import View.CreatePricesViewPanel;
import View.EditCustomerViewPanel;
import View.SeeTransactionsViewPanel;
import View.MainInterface;
import com.sun.java.accessibility.util.EventID;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class Controller {

    CreateCustomerViewPanel createCustomerViewPanel;
    MainInterface mainInterface;
    EditCustomerViewPanel editCustomerViewPanel;
    SeeTransactionsViewPanel seeTransactionsViewPanel;
    CreatePricesViewPanel createPricesViewPanel;
    int row;
    int column;
    DefaultTableModel tableModel;

    public Controller(MainInterface mainInterface) {
        this.mainInterface = mainInterface;
        this.createCustomerViewPanel = this.mainInterface.getCreateCustomerPanel();
        this.editCustomerViewPanel = this.mainInterface.getEditCustomerViewPanel();
        this.seeTransactionsViewPanel = this.mainInterface.getSeeTransactionsViewPanel();
        this.createPricesViewPanel = this.mainInterface.getCreatePricesViewPanel();

        this.createCustomerViewPanel.addButtonCreateCustomerListener(new CreateCustomerListener());
        this.editCustomerViewPanel.addButtonSearchListner(new SearchCustomerListener());
        this.editCustomerViewPanel.addButtonDeleteListner(new DeleteCustomerListener());
        this.editCustomerViewPanel.addMouseClicked(new TableCustomerListener());
        this.editCustomerViewPanel.addButtonEditListner(new EditCustomerListener());
        this.seeTransactionsViewPanel.addButtonUpdateListner(new UpdateTransactionsListener());
        this.createPricesViewPanel.addButtonCreatePricesListener(new CreatePricesListener());

    }

     class CreatePricesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            
            String location;
            double priceDay;
            double priceNight;

            try {

                location = createPricesViewPanel.getPriceLocation();
                priceDay = createPricesViewPanel.getPriceDay();
                priceNight = createPricesViewPanel.getPriceNight();
                
                PricesDao pricesDao = new PricesDao();
                    Prices prices = new Prices (location, priceDay, priceNight);
                    pricesDao.addPrices(prices);
                    //Writes when the prices are created.
                    createPricesViewPanel.displayErrorMessage("Prices created");
                
            } catch (Exception e) {
                //Writes if there are any other exceptions.
                createPricesViewPanel.displayErrorMessage("Try again.");
        }
    }
 }

    // When click the delete button, it deletes the marked customer.
    class DeleteCustomerListener implements ActionListener {

        //Reads what customer that are marked.
        @Override
        public void actionPerformed(ActionEvent arg0) {
            Object ID = tableModel.getValueAt(row, 0);
            String id = ID.toString();
            //Deletes the marked customer.
            CustomerDao customerDao = new CustomerDao();
            customerDao.deleteCustomer(Integer.parseInt(id));
        }
    }

    class EditCustomerListener implements ActionListener {

        //Reads what customer that are marked.
        @Override
        public void actionPerformed(ActionEvent arg0) {
            Object ID = tableModel.getValueAt(row, 0);

            if (column == 0) {
                editCustomerViewPanel.displayErrorMessage("You cannot change the ID!");
            } else if (column == 1) {
                String update = editCustomerViewPanel.getUpdate();

                CustomerDao customerDao = new CustomerDao();
                customerDao.updateCustomerName(update, (int) ID);
                editCustomerViewPanel.displayErrorMessage("Name changed");
            } else if (column == 2) {
                String update = editCustomerViewPanel.getUpdate();

                CustomerDao customerDao = new CustomerDao();
                customerDao.updateCustomerPhone(update, (int) ID);
                editCustomerViewPanel.displayErrorMessage("Phone number changed");
            } else if (column == 3) {
                String update = editCustomerViewPanel.getUpdate();

                CustomerDao customerDao = new CustomerDao();
                customerDao.updateCustomerEmail(update, (int) ID);
                editCustomerViewPanel.displayErrorMessage("Email changed");
            } else if (column == 4) {
                String update = editCustomerViewPanel.getUpdate();
                double intUpdate = Integer.parseInt(update);

                CustomerDao customerDao = new CustomerDao();
                customerDao.updateCustomerBalance(intUpdate, (int) ID);
                editCustomerViewPanel.displayErrorMessage("Balance changed");
            }
        }
    }

    // when click the creat button, it creates the new customer.
    class CreateCustomerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int ID;
            String name;
            String phone;
            String email;
            String password;
            String retypePassword;

            try {
                //Gets all the textfields from the viewpanel.
                ID = createCustomerViewPanel.getCosutmerID();
                name = createCustomerViewPanel.getCustomerName();
                phone = createCustomerViewPanel.getPhone();
                email = createCustomerViewPanel.getEmail();
                password = createCustomerViewPanel.getPassword();
                retypePassword = createCustomerViewPanel.getRetypePassword();

                //Sees if the to passwords are the same.
                if (password.equals(retypePassword)) {
                    //creates a new customer from with the data from the textfields.
                    CustomerDao customerDao = new CustomerDao();
                    Customer customer = new Customer(ID, name, phone, 0, email, password);
                    customerDao.addCustomer(customer);
                    //Writes when the customer are created.
                    createCustomerViewPanel.displayErrorMessage("Customer created");
                } else {
                    //Writes if the password does not match.
                    createCustomerViewPanel.displayErrorMessage("Passwords don't match");
                }

            } catch (Exception e) {
                //Writes if there are any other exceptions.
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

            String col[] = {"ID", "Name", "Phone", "Email", "Balance"};
            tableModel = new DefaultTableModel(col, 0);
            editCustomerViewPanel.addTableModel(tableModel);
            try {
                name = editCustomerViewPanel.getCustomerName();
                phone = editCustomerViewPanel.getPhone();
                email = editCustomerViewPanel.getEmail();
                CustomerDao customerDao = new CustomerDao();
                ArrayList<Customer> customers = customerDao.findCustomers(name, email, phone);
                for (Customer c : customers) {
                    Object[] objs = {c.getId(), c.getName(), c.getPhone(), c.getEmail(), c.getBalance()};
                    tableModel.addRow(objs);
                }
            } catch (Exception e) {
                editCustomerViewPanel.displayErrorMessage("Try again.");
            }
        }
    }

    class UpdateTransactionsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            String col[] = {"Transaction id", "Customer id", "Amount", "Timedate", "Location", "Device", "Time spent"};
            tableModel = new DefaultTableModel(col, 0);
            seeTransactionsViewPanel.addTableModel(tableModel);
            try {
                TransactionDao transactionDao = new TransactionDao();
                ArrayList<Transaction> transactions = transactionDao.findTransactions();
                for (Transaction c : transactions) {
                    Object[] objs = {c.getTransactionID(), c.getCustomerID(), c.getAmount(), c.getTimeDate(), c.getLocation(), c.getDevice(), c.getTimeSpent()};
                    tableModel.addRow(objs);
                }
            } catch (Exception e) {
                seeTransactionsViewPanel.displayErrorMessage("Try again.");
            }
        }
    }

    class TableCustomerListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            row = editCustomerViewPanel.getTableCustomers().rowAtPoint(e.getPoint());
            column = editCustomerViewPanel.getTableCustomers().columnAtPoint(e.getPoint());
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }
}
