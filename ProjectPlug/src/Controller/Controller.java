package Controller;

import DAO.*;
import Model.*;
import View.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class Controller {

    //The viewpanels defined.
    CreateCustomerViewPanel createCustomerViewPanel;
    MainInterface mainInterface;
    EditCustomerViewPanel editCustomerViewPanel;
    EditPricesViewPanel editPricesViewPanel;
    AdminTransactionsViewPanel adminTransactionsViewPanel;
    CreatePricesViewPanel createPricesViewPanel;
    LoginViewPanel loginViewPanel;
    UserViewPanel userViewPanel;
    AdminViewPanel adminViewPanel;
    UserAccountViewPanel userAccountViewPanel;
    UserTransactionsViewPanel userTransactionsViewPanel;
    UserPricesViewPanel userPricesViewPanel;
    InsertMoneyViewPanel insertMoneyViewPanel;

    int row;
    int column;
    DefaultTableModel tableModel;
    String LoginAs;

    public Controller(MainInterface mainInterface) {
        //The different view panels in the mainfriame.
        this.mainInterface = mainInterface;
        this.createCustomerViewPanel = this.mainInterface.getCreateCustomerPanel();
        this.editCustomerViewPanel = this.mainInterface.getEditCustomerViewPanel();
        this.adminTransactionsViewPanel = this.mainInterface.getSeeTransactionsViewPanel();
        this.createPricesViewPanel = this.mainInterface.getCreatePricesViewPanel();
        this.editPricesViewPanel = this.mainInterface.getEditPricesViewPanel();
        this.loginViewPanel = this.mainInterface.getLoginViewPanel();
        this.userViewPanel = this.mainInterface.getUserViewPanel();
        this.userAccountViewPanel = this.mainInterface.getAccountViewPanel();
        this.userTransactionsViewPanel = this.mainInterface.getUserTransactionsViewPanel();
        this.userPricesViewPanel = this.mainInterface.getUserPricesViewPanel();
        this.insertMoneyViewPanel = this.mainInterface.getInsertMoneyViewPanel();
        this.adminViewPanel = this.mainInterface.getAdminViewPanel();

        //Buttons in the different view panels.
        this.loginViewPanel.addButtonLoginListener(new LoginListener());
        this.createPricesViewPanel.addButtonCreatePricesListener(new CreatePricesListener());
        this.createPricesViewPanel.addButtonBackListener(new BackToAdminViewPanelListener());
        this.createCustomerViewPanel.addButtonCreateCustomerListener(new CreateCustomerListener());
        this.createCustomerViewPanel.addButtonBackListener(new BackToAdminViewPanelListener());
        this.editPricesViewPanel.addButtonEditPricesListener(new EditPricesListener());
        this.editPricesViewPanel.addButtonDeletePricesListener(new DeletePricesListener());
        this.editPricesViewPanel.addButtonSearchPricesListener(new AdminSearchPricesListener());
        this.editPricesViewPanel.addMouseClicked(new TableCustomerListener());
        this.editPricesViewPanel.addButtonBackListener(new BackToAdminViewPanelListener());
        this.editCustomerViewPanel.addButtonDeleteListener(new DeleteCustomerListener());
        this.editCustomerViewPanel.addMouseClicked(new TableCustomerListener());
        this.editCustomerViewPanel.addButtonSearchCustomerListener(new SearchCustomerListener());
        this.editCustomerViewPanel.addButtonEditListener(new EditCustomerListener());
        this.editCustomerViewPanel.addButtonBackListener(new BackToAdminViewPanelListener());
        this.adminTransactionsViewPanel.addButtonBackListener(new BackToAdminViewPanelListener());
        this.userViewPanel.addButtonUserTransactionsListener(new UserTransactionsListener());
        this.userViewPanel.addButtonPricesListener(new UserSearchPricesListener());
        this.userViewPanel.addButtonInsertMoneyListener(new SeeInsertMoneyListener());
        this.userViewPanel.addButtonUserAccountListener(new FindSingleCustomerListener());
        this.userViewPanel.addButtonLogOffListener(new LogOffListener());
        this.userAccountViewPanel.addButtonSaveListener(new EditSingleCustomerListener());
        this.userAccountViewPanel.addButtonBackListener(new BackToUserViewPanelListener());
        this.userTransactionsViewPanel.addButtonBackListener(new BackToUserViewPanelListener());
        this.userPricesViewPanel.addButtonSearchListener(new UserSearchPricesListener());
        this.userPricesViewPanel.addButtonBackListener(new BackToUserViewPanelListener());
        this.insertMoneyViewPanel.addButtonInsertMoneyListener(new InsertMoneyListener());
        this.insertMoneyViewPanel.addButtonBackListener(new BackToUserViewPanelListener());
        this.adminViewPanel.addButtonLogOffListener(new LogOffListener());
        this.adminViewPanel.addButtonCreateCustomerListener(new SeeCreateCustomerListener());
        this.adminViewPanel.addButtonEditCustomerListener(new SearchCustomerListener());
        this.adminViewPanel.addButtonSeeTransactionsListener(new AdminTransactionsListener());
        this.adminViewPanel.addButtonCreatePriceListener(new SeeCreatePricesListener());
        this.adminViewPanel.addButtonEditPricesListener(new AdminSearchPricesListener());
        this.adminViewPanel.addButtonSeeTransactionsListener(new AdminTransactionsListener());
    }

    class LoginListener implements ActionListener {

        //When clicking it checks if the password is right, and if you are an admin
        //or a user and then logs you in.
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
                    if (username.equals("Admin")) {
                        mainInterface.setContentPane(adminViewPanel);
                    } else {
                        mainInterface.setContentPane(userViewPanel);
                    }
                    LoginAs = username;
                    loginViewPanel.setTextFieldPassword(null);
                    loginViewPanel.setTextFieldUsername(null);
                } else {
                    loginViewPanel.displayErrorMessage("Wrong username or password.");
                }
            } catch (Exception e) {
                loginViewPanel.displayErrorMessage("Try again");
            }
        }

    }                

    class CreateCustomerListener implements ActionListener {

        // when click the creat button, it creates the new customer.
        @Override
        public void actionPerformed(ActionEvent arg0) {
            String ID;
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

                    createCustomerViewPanel.setTextFieldID(null);
                    createCustomerViewPanel.setTextFieldName(null);
                    createCustomerViewPanel.setTextFieldEmail(null);
                    createCustomerViewPanel.setTextFieldPhone(null);
                    createCustomerViewPanel.setTextFieldPassword(null);
                    createCustomerViewPanel.setTextFieldRetypePassword(null);
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

    class CreatePricesListener implements ActionListener {

        //When clicking the Create button, it creates a customer from the written data.
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

                createPricesViewPanel.setTextFieldLocation(null);
                createPricesViewPanel.setTextFieldPriceDay(null);
                createPricesViewPanel.setTextFieldPriceNight(null);
            } catch (Exception e) {
                //Writes if there are any other exceptions.
                createPricesViewPanel.displayErrorMessage("Try again.");
            }
        }
    }         

    class DeleteCustomerListener implements ActionListener {

        //When clicking the Delete button, it deletes the marked customer.
        @Override
        public void actionPerformed(ActionEvent arg0) {
            Object ID = tableModel.getValueAt(row, 0);
            String id = ID.toString();
            CustomerDao customerDao = new CustomerDao();
            customerDao.deleteCustomer(id);
            editCustomerViewPanel.displayErrorMessage("Customer was deleted.");

            editCustomerViewPanel.getButtonSearch().doClick();
        }
    }       

    class EditCustomerListener implements ActionListener {

        //When clicking the Edit button, it edits the marked customer with the written
        //data on the marked area. 
        @Override
        public void actionPerformed(ActionEvent arg0) {
            Object ID = tableModel.getValueAt(row, 0);

            if (column == 0) {
                editCustomerViewPanel.displayErrorMessage("You cannot change the ID!");
            } else if (column == 1) {
                String update = editCustomerViewPanel.getUpdate();

                CustomerDao customerDao = new CustomerDao();
                customerDao.updateCustomerName(update, (String) ID);
                editCustomerViewPanel.displayErrorMessage("Name changed");
            } else if (column == 2) {
                String update = editCustomerViewPanel.getUpdate();

                CustomerDao customerDao = new CustomerDao();
                customerDao.updateCustomerPhone(update, (String) ID);
                editCustomerViewPanel.displayErrorMessage("Phone number changed");
            } else if (column == 3) {
                String update = editCustomerViewPanel.getUpdate();

                CustomerDao customerDao = new CustomerDao();
                customerDao.updateCustomerEmail(update, (String) ID);
                editCustomerViewPanel.displayErrorMessage("Email changed");
            } else if (column == 4) {
                String update = editCustomerViewPanel.getUpdate();
                double intUpdate = Integer.parseInt(update);

                CustomerDao customerDao = new CustomerDao();
                customerDao.updateCustomerBalance(intUpdate, (String) ID);
                editCustomerViewPanel.displayErrorMessage("Balance changed");
            } else if (column == 5) {
                String update = editCustomerViewPanel.getUpdate();

                CustomerDao customerDao = new CustomerDao();
                customerDao.updateCustomerPassword(update, (String) ID);
                editCustomerViewPanel.displayErrorMessage("Password changed");
            } else {
                editCustomerViewPanel.displayErrorMessage("Try again!");
            }
            editCustomerViewPanel.setTextFieldUpdate(null);
            editCustomerViewPanel.getButtonSearch().doClick();
        }
    }         

    class EditSingleCustomerListener implements ActionListener {

        //When clicking the Edit button, it edits the customer with the written data.
        @Override
        public void actionPerformed(ActionEvent arg0) {
            String id = null;
            String name;
            String email;
            String phone;
            String password;
            String retypePassword;

            try {
                name = userAccountViewPanel.getTextFieldAccountName();
                email = userAccountViewPanel.getTextFieldAccountEmail();
                phone = userAccountViewPanel.getTextFieldAccountPhone();
                password = userAccountViewPanel.getTextFieldPassword();
                retypePassword = userAccountViewPanel.getTextFieldRetypePassword();

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
                        userAccountViewPanel.displayErrorMessage("Passwords doesn't match.");
                    }
                }
                userAccountViewPanel.displayErrorMessage("Account has been updated.");

                userAccountViewPanel.setTextFieldPassword(null);
                userAccountViewPanel.setTextFieldRetypePassword(null);
            } catch (Exception e) {
                userAccountViewPanel.displayErrorMessage("Try again.");
            }
        }
    }   

    class EditPricesListener implements ActionListener {

        //When clicking the Edit button, it edits the marked price with the written
        //data on the marked area.
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
                editPricesViewPanel.displayErrorMessage("PriceNight Changed");

            }
            editPricesViewPanel.setTextFieldUpdate(null);
        }

    }           

    class SearchCustomerListener implements ActionListener {

        //When clicking the search button, it will search for all customers, with 
        //the deffined name, email and/or phone.
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
                if (customers.isEmpty()){
                    editCustomerViewPanel.displayErrorMessage("There was no match.");
                }
            } catch (Exception e) {
                editCustomerViewPanel.displayErrorMessage("Try again.");
            }
            mainInterface.setContentPane(editCustomerViewPanel);
        }
    }       

    class UserTransactionsListener implements ActionListener {

        //Searches through all the transactions for the user.
        @Override
        public void actionPerformed(ActionEvent arg0) {
            String customerID = null;

            String col[] = {"Transaction ID", "Amount", "Timedate", "Location", "Device", "Time Spent"};
            tableModel = new DefaultTableModel(col, 0);
            userTransactionsViewPanel.addTableModel(tableModel);
            try {
                CustomerDao customerDao = new CustomerDao();
                ArrayList<Customer> customers = customerDao.findCustomers("", LoginAs, "");
                for (Customer c : customers) {
                    customerID = c.getId();
                }
                TransactionDao transactionDao = new TransactionDao();
                ArrayList<Transaction> transactions = transactionDao.findTransactionsByCustomerID(customerID);
                for (Transaction t : transactions) {
                    Object[] objs = {t.getTransactionID(), t.getAmount(), t.getTimeDate(), t.getLocation(), t.getDevice(), t.getTimeSpent()};
                    tableModel.addRow(objs);
                }
            } catch (Exception e) {
                userTransactionsViewPanel.displayErrorMessage("Try again.");
            }
            mainInterface.setContentPane(userTransactionsViewPanel);
        }
    }     

    class UserSearchPricesListener implements ActionListener {

        //Searches through all the prices, where the location is the deffined.
        @Override
        public void actionPerformed(ActionEvent arg0) {
            String location;

            String col[] = {"Location", "Prices day", "Prices night"};
            tableModel = new DefaultTableModel(col, 0);
            userPricesViewPanel.addTableModel(tableModel);
            try {
                location = userPricesViewPanel.getTextFieldLocation();
                PricesDao pricesDao = new PricesDao();
                ArrayList<Prices> prices = pricesDao.findPrices(location);
                for (Prices p : prices) {
                    Object[] objs = {p.getLocation(), p.getPrice_day(), p.getPrice_night()};
                    tableModel.addRow(objs);
                }
            } catch (Exception e) {
                e.printStackTrace();
                userPricesViewPanel.displayErrorMessage("Try again.");
            }
            mainInterface.setContentPane(userPricesViewPanel);
        }
    }     

    class FindSingleCustomerListener implements ActionListener {

        //Searches for the customers information that are logged in.
        @Override
        public void actionPerformed(ActionEvent arg0) {
            String email;

            try {
                email = LoginAs;
                CustomerDao customerDao = new CustomerDao();
                ArrayList<Customer> customers = customerDao.findCustomers("", email, "");
                for (Customer c : customers) {
                    userAccountViewPanel.setTextFieldAccountName(c.getName());
                    userAccountViewPanel.setTextFieldAccountEmail(c.getEmail());
                    userAccountViewPanel.setTextFieldAccountPhone(c.getPhone());
                    userAccountViewPanel.setTextFieldAccountBalance(Double.toString(c.getBalance()));
                }
            } catch (Exception e) {
                userAccountViewPanel.displayErrorMessage("Try again.");
            }
            mainInterface.setContentPane(userAccountViewPanel);//Skift til kommende panel
        }
    }   

    class InsertMoneyListener implements ActionListener {

        //Inserts money to the user that are logged in.
        @Override
        public void actionPerformed(ActionEvent arg0) {
            Double money;
            Double balance = 0.0;
            Double newBalance;
            String ID = null;

            try {
                money = insertMoneyViewPanel.getTextFieldInsertMoney();
                CustomerDao customerDao = new CustomerDao();
                ArrayList<Customer> customers = customerDao.findCustomers("", LoginAs, "");
                for (Customer c : customers) {
                    ID = c.getId();
                    balance = c.getBalance();
                }
                newBalance = balance + money;
                customerDao.updateCustomerBalance(newBalance, ID);
                insertMoneyViewPanel.displayErrorMessage("You're balance was now updated with " + money + " kr.");

                insertMoneyViewPanel.setTextFieldInsertMoney(null);
            } catch (Exception e) {
                insertMoneyViewPanel.displayErrorMessage("Try again!");
            }
        }
    }          

    class DeletePricesListener implements ActionListener {

        //Reads what customer that are marked, and deletes it.
        @Override
        public void actionPerformed(ActionEvent arg0) {
            Object Location = tableModel.getValueAt(row, 0);
            String location = Location.toString();
            //Deletes the marked customer.
            PricesDao pricesDao = new PricesDao();
            pricesDao.deletePrices(location);
        }
    }         

    class AdminSearchPricesListener implements ActionListener {

        //Searches through all the prices, from the deffined location.
        @Override
        public void actionPerformed(ActionEvent arg0) {
            String location;

            String col[] = {"Location", "PriceDay", "PriceNight"};
            tableModel = new DefaultTableModel(col, 0);
            editPricesViewPanel.addTableModel(tableModel);
            try {
                location = editPricesViewPanel.getPricesLocation();
                PricesDao pricesDao = new PricesDao();
                ArrayList<Prices> prices = pricesDao.findPrices(location);
                for (Prices p : prices) {
                    Object[] objs = {p.getLocation(), p.getPrice_day(), p.getPrice_night(),};
                    tableModel.addRow(objs);
                }
            } catch (Exception e) {
                e.printStackTrace();
                editPricesViewPanel.displayErrorMessage("Try again.");
            }
            mainInterface.setContentPane(editPricesViewPanel);
        }
    }    

    class AdminTransactionsListener implements ActionListener {

        //Search through all the transactions.
        @Override
        public void actionPerformed(ActionEvent arg0) {

            String col[] = {"Transaction id", "Customer id", "Amount", "Timedate", "Location", "Device", "Time spent"};
            tableModel = new DefaultTableModel(col, 0);
            adminTransactionsViewPanel.addTableModel(tableModel);
            try {
                TransactionDao transactionDao = new TransactionDao();
                ArrayList<Transaction> transactions = transactionDao.findTransactions();
                for (Transaction c : transactions) {
                    Object[] objs = {c.getTransactionID(), c.getCustomerID(), c.getAmount(), c.getTimeDate(), c.getLocation(), c.getDevice(), c.getTimeSpent()};
                    tableModel.addRow(objs);
                }
            } catch (Exception e) {
                adminTransactionsViewPanel.displayErrorMessage("Try again.");
            }
            mainInterface.setContentPane(adminTransactionsViewPanel);
        }
    }    

    class LogOffListener implements ActionListener {

        //Logs of and goes to the logiViewPanel.
        @Override
        public void actionPerformed(ActionEvent arg0) {
            mainInterface.setContentPane(loginViewPanel);
            LoginAs = null;
        }
    }

    class BackToUserViewPanelListener implements ActionListener {

        //Jumps back to the UserViewPanel.
        @Override
        public void actionPerformed(ActionEvent arg0) {
            mainInterface.setContentPane(userViewPanel);
        }
    }

    class BackToAdminViewPanelListener implements ActionListener {

        //Jumps back to the AdminViewPanel.
        @Override
        public void actionPerformed(ActionEvent arg0) {
            mainInterface.setContentPane(adminViewPanel);
        }
    }

    class SeeInsertMoneyListener implements ActionListener {

        //Jump to the InsertMoneyViewPanel.
        @Override
        public void actionPerformed(ActionEvent arg0) {
            mainInterface.setContentPane(insertMoneyViewPanel);
        }
    }

    class SeeCreateCustomerListener implements ActionListener {

        //Jumps to the CreateCustomerViewPanel.
        @Override
        public void actionPerformed(ActionEvent arg0) {
            mainInterface.setContentPane(createCustomerViewPanel);
        }
    }

    class SeeCreatePricesListener implements ActionListener {

        //Jumps to the CreatePricesViewPanel.
        @Override
        public void actionPerformed(ActionEvent arg0) {
            mainInterface.setContentPane(createPricesViewPanel);
        }
    }

    class TableCustomerListener implements MouseListener {

        //Reads what row and column there are marked.
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