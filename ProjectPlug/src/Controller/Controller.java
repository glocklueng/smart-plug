/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.CustomerDao;
import DAO.TransactionDao;
import Model.Customer;
import Model.Transaction;
import View.CreateCustomerViewPanel;
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
    int position;
    DefaultTableModel tableModel;

    public Controller(MainInterface mainInterface) {
        this.mainInterface = mainInterface;
        this.createCustomerViewPanel = this.mainInterface.getCreateCustomerPanel();
        this.editCustomerViewPanel = this.mainInterface.getEditCustomerViewPanel();
        this.seeTransactionsViewPanel = this.mainInterface.getSeeTransactionsViewPanel();
        this.createCustomerViewPanel.addButtonCreateCustomerListener(new CreateCustomerListener());
        this.editCustomerViewPanel.addButtonSearchListner(new SearchCustomerListener());
        this.editCustomerViewPanel.addButtonDeleteListner(new DeleteCustomerListener());
        this.seeTransactionsViewPanel.addButtonUpdateListner(new UpdateTransactionsListener());
        this.editCustomerViewPanel.addMouseClicked(new TableCustomerListener() {});

    }

    class DeleteCustomerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            Object ID = tableModel.getValueAt(position, 0);
            String id = ID.toString();
            CustomerDao customerDao = new CustomerDao();
            customerDao.deleteCustomer(Integer.parseInt(id));
        }
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
            position = editCustomerViewPanel.getTableCustomers().rowAtPoint(e.getPoint());
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
