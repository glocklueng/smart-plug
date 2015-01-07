package Controller;

import DAO.CustomerDao;
import DAO.PricesDao;
import DAO.TransactionDao;
import Model.Customer;
import Model.Prices;
import Model.Transaction;
import View.UserAccountViewPanel;
import View.AdminViewPanel;
import View.CreateCustomerViewPanel;
import View.CreatePricesViewPanel;
import View.EditCustomerViewPanel;
import View.SeeTransactionsViewPanel;
import View.MainInterface;
import View.EditPricesViewPanel;
import View.LoginViewPanel;
import View.UserViewPanel;
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
    EditPricesViewPanel editPricesViewPanel;
    SeeTransactionsViewPanel seeTransactionsViewPanel;
    CreatePricesViewPanel createPricesViewPanel;
    LoginViewPanel loginViewPanel;
    UserViewPanel userViewPanel;
    AdminViewPanel adminViewPanel;
    UserAccountViewPanel accountViewPanel;
    String LoginAs;

    int row;
    int column;
    DefaultTableModel tableModel;

    public Controller(MainInterface mainInterface) {
        this.mainInterface = mainInterface;
        this.createCustomerViewPanel = this.mainInterface.getCreateCustomerPanel();
        this.editCustomerViewPanel = this.mainInterface.getEditCustomerViewPanel();
        this.seeTransactionsViewPanel = this.mainInterface.getSeeTransactionsViewPanel();
        this.createPricesViewPanel = this.mainInterface.getCreatePricesViewPanel();
        this.editPricesViewPanel = this.mainInterface.getEditPricesViewPanel();
        this.loginViewPanel = this.mainInterface.getLoginViewPanel();
        this.userViewPanel = this.mainInterface.getUserViewPanel();
        this.accountViewPanel = this.mainInterface.getAccountViewPanel();

        this.createCustomerViewPanel.addButtonCreateCustomerListener(new CreateCustomerListener());
        this.editPricesViewPanel.addButtonEditPricesListener(new EditPricesListener());
        this.editCustomerViewPanel.addButtonDeleteListener(new DeleteCustomerListener());
        this.editCustomerViewPanel.addMouseClicked(new TableCustomerListener());
        this.editCustomerViewPanel.addButtonSearchCustomerListener(new SearchCustomerListener());
        this.editCustomerViewPanel.addButtonEditListener(new EditCustomerListener());
        this.seeTransactionsViewPanel.addButtonUpdateListener(new UpdateTransactionsListener());
        this.createPricesViewPanel.addButtonCreatePricesListener(new CreatePricesListener());
        this.editPricesViewPanel.addButtonDeletePricesListener(new DeletePricesListener());
        this.editPricesViewPanel.addButtonSearchPricesListener(new SearchPricesListener());
        this.editPricesViewPanel.addMouseClicked(new TableCustomerListener());
        this.loginViewPanel.addButtonLoginListener(new LoginListener());
        this.userViewPanel.addButtonLogOffListener(new LogOffListener());
        this.userViewPanel.addButtonInsertMoneyListener(new InsertMoneyListener());
        this.userViewPanel.addButtonSeePricesListener(new SeePricesListener());
        this.userViewPanel.addButtonSeeUserTransactionsListener(new SeeUserTransactionsListener());
        this.userViewPanel.addButtonSeeUserAccountListener(new FindSingleCustomerListener());
        this.accountViewPanel.addButtonSaveListener(new EditSingleCustomerListener());
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
                Prices prices = new Prices(location, priceDay, priceNight);
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

    class EditSingleCustomerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int id = 0;
            String name;
            String email;
            String phone;
            String password;
            String retypePassword;

            try {
                name = accountViewPanel.getTextFieldAccountName();
                email = accountViewPanel.getTextFieldAccountEmail();
                phone = accountViewPanel.getTextFieldAccountPhone();
                password = accountViewPanel.getTextFieldPassword();
                retypePassword = accountViewPanel.getTextFieldRetypePassword();

                CustomerDao customerDao = new CustomerDao();
                ArrayList<Customer> customers = customerDao.findCustomers("", LoginAs, "");
                for (Customer c : customers) {
                    id = c.getId();
                }
                customerDao.updateCustomerName(name, id);
                customerDao.updateCustomerEmail(email, id);
                customerDao.updateCustomerPhone(phone, id);
                if (!"".equals(password)) {
                    if (password.equals(retypePassword)) {
                        customerDao.updateCustomerPassword(password, id);
                    } else {
                        accountViewPanel.displayErrorMessage("Passwords doesn't match.");
                    }
                }
                accountViewPanel.displayErrorMessage("Account has been updated.");
            } catch (Exception e) {
                accountViewPanel.displayErrorMessage("Try again.");
            }
        }
    }

// needs to be changed ****************************************
    class EditPricesListener implements ActionListener {

        //Reads what customer that are marked.
        @Override
        public void actionPerformed(ActionEvent arg0) {
            Object Location = tableModel.getValueAt(row, 0);

            if (column == 0) {
                editPricesViewPanel.displayErrorMessage("You shouldn't change the location!");
            } else if (column == 1) {
                String update = editPricesViewPanel.getPricesUpdate();
                double intUpdate = Integer.parseInt(update);

                PricesDao pricesDao = new PricesDao();
                pricesDao.updatePricesDay(intUpdate, Location.toString());
                editPricesViewPanel.displayErrorMessage("PricesDay changed");
            } else if (column == 2) {
                String update = editPricesViewPanel.getPricesUpdate();
                double intUpdate = Integer.parseInt(update);

                PricesDao pricesDao = new PricesDao();
                pricesDao.updatePricesNight(intUpdate, Location.toString());
                editCustomerViewPanel.displayErrorMessage("PriceNight Changed");

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

            String col[] = {"ID", "Name", "Phone", "Email", "Balance", "Password"};
            tableModel = new DefaultTableModel(col, 0);
            editCustomerViewPanel.addTableModel(tableModel);
            try {
                name = editCustomerViewPanel.getCustomerName();
                phone = editCustomerViewPanel.getPhone();
                email = editCustomerViewPanel.getEmail();
                CustomerDao customerDao = new CustomerDao();
                ArrayList<Customer> customers = customerDao.findCustomers(name, email, phone);
                for (Customer c : customers) {
                    Object[] objs = {c.getId(), c.getName(), c.getPhone(), c.getEmail(), c.getBalance(), c.getPassword()};
                    tableModel.addRow(objs);
                }
            } catch (Exception e) {
                editCustomerViewPanel.displayErrorMessage("Try again.");
            }
        }
    }

    class FindSingleCustomerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String email;

            try {
                email = LoginAs;
                CustomerDao customerDao = new CustomerDao();
                ArrayList<Customer> customers = customerDao.findCustomers("", email, "");
                for (Customer c : customers) {
                    accountViewPanel.setTextFieldAccountName(c.getName());
                    accountViewPanel.setTextFieldAccountEmail(c.getEmail());
                    accountViewPanel.setTextFieldAccountPhone(c.getPhone());
                    accountViewPanel.setTextFieldAccountBalance(Double.toString(c.getBalance()));
                }
                mainInterface.setContentPane(accountViewPanel);//Skift til kommende panel
                mainInterface.invalidate();
                mainInterface.validate();
            } catch (Exception e) {
                accountViewPanel.displayErrorMessage("Try again.");
            }
        }
    }

    class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String username;
            String password;
            String realPassword;
            try {
                username = loginViewPanel.getUsername();
                password = loginViewPanel.getPassword().toUpperCase();

                CustomerDao customerDao = new CustomerDao();
                realPassword = customerDao.findPassword(username).toUpperCase();
                if (realPassword.equals(password)) {
                    loginViewPanel.displayErrorMessage("You are logged in.");
                    LoginAs = username;
                    if (username.toUpperCase().equals("ADMIN@ADMIN.COM")) {
                        mainInterface.setContentPane(adminViewPanel);
                        mainInterface.invalidate();
                        mainInterface.validate();
                    } else {
                        mainInterface.setContentPane(userViewPanel);
                        mainInterface.invalidate();
                        mainInterface.validate();
                    }
                } else {
                    loginViewPanel.displayErrorMessage("Wrong username or password.");
                }
            } catch (Exception e) {
                loginViewPanel.displayErrorMessage("Try again");
            }
        }

    }

    class LogOffListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            mainInterface.setContentPane(loginViewPanel);
            mainInterface.invalidate();
            mainInterface.validate();
        }
    }

    class InsertMoneyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            mainInterface.setContentPane(loginViewPanel);//Skift til kommende panel
            mainInterface.invalidate();
            mainInterface.validate();
        }
    }

    class SeePricesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            mainInterface.setContentPane(loginViewPanel);//Skift til kommende panel
            mainInterface.invalidate();
            mainInterface.validate();
        }
    }

    class SeeUserTransactionsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            mainInterface.setContentPane(loginViewPanel);//Skift til kommende panel
            mainInterface.invalidate();
            mainInterface.validate();
        }
    }

    class SeeUserAccountListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            mainInterface.setContentPane(accountViewPanel);//Skift til kommende panel
            mainInterface.invalidate();
            mainInterface.validate();
        }
    }

    class DeletePricesListener implements ActionListener {

        //Reads what customer that are marked.
        @Override
        public void actionPerformed(ActionEvent arg0) {
            Object Location = tableModel.getValueAt(row, 0);
            String location = Location.toString();
            //Deletes the marked customer.
            PricesDao pricesDao = new PricesDao();
            pricesDao.deletePrices(location);
        }
    }

    class SearchPricesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            String location;
            double price_Day;
            double price_Night;

            String col[] = {"Location", "PriceDay", "PriceNight"};
            tableModel = new DefaultTableModel(col, 0);
            editPricesViewPanel.addTableModel(tableModel);
            try {
                location = editPricesViewPanel.getPricesLocation();
                // converts first String to double
                PricesDao pricesDao = new PricesDao();
                ArrayList<Prices> prices = pricesDao.findPrices(location);
                for (Prices c : prices) {
                    Object[] objs = {c.getLocation(), c.getPrice_day(), c.getPrice_night(),};
                    tableModel.addRow(objs);
                }
            } catch (Exception e) {
                e.printStackTrace();
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
