package View;

public class MainInterface extends javax.swing.JFrame {

    public MainInterface() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        loginViewPanel1 = new View.LoginViewPanel();
        adminViewPanel2 = new View.AdminViewPanel();
        createCustomerViewPanel1 = new View.CreateCustomerViewPanel();
        editCustomerViewPanel1 = new View.EditCustomerViewPanel();
        createPricesViewPanel1 = new View.CreatePricesViewPanel();
        editPricesViewPanel1 = new View.EditPricesViewPanel();
        seeTransactionsViewPanel2 = new View.AdminTransactionsViewPanel();
        userViewPanel1 = new View.UserViewPanel();
        accountViewPanel1 = new View.UserAccountViewPanel();
        userTransactionsViewPanel1 = new View.UserTransactionsViewPanel();
        userPricesViewPanel1 = new View.UserPricesViewPanel();
        insertMoneyViewPanel1 = new View.InsertMoneyViewPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.addTab("Login", loginViewPanel1);
        jTabbedPane1.addTab("Admin view panel", adminViewPanel2);
        jTabbedPane1.addTab("Create customer", createCustomerViewPanel1);
        jTabbedPane1.addTab("Edit customer", editCustomerViewPanel1);
        jTabbedPane1.addTab("Create Prices", createPricesViewPanel1);
        jTabbedPane1.addTab("Edit Prices", editPricesViewPanel1);
        jTabbedPane1.addTab("See transactions", seeTransactionsViewPanel2);
        jTabbedPane1.addTab("User view panel", userViewPanel1);
        jTabbedPane1.addTab("User account view panel", accountViewPanel1);
        jTabbedPane1.addTab("User Transations", userTransactionsViewPanel1);
        jTabbedPane1.addTab("User Prices", userPricesViewPanel1);
        jTabbedPane1.addTab("Insert money", insertMoneyViewPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 719, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainInterface().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private View.UserAccountViewPanel accountViewPanel1;
    private View.AdminViewPanel adminViewPanel2;
    private View.CreateCustomerViewPanel createCustomerViewPanel1;
    private View.CreatePricesViewPanel createPricesViewPanel1;
    private View.EditCustomerViewPanel editCustomerViewPanel1;
    private View.EditPricesViewPanel editPricesViewPanel1;
    private View.InsertMoneyViewPanel insertMoneyViewPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private View.LoginViewPanel loginViewPanel1;
    private View.AdminTransactionsViewPanel seeTransactionsViewPanel2;
    private View.UserPricesViewPanel userPricesViewPanel1;
    private View.UserTransactionsViewPanel userTransactionsViewPanel1;
    private View.UserViewPanel userViewPanel1;
    // End of variables declaration//GEN-END:variables
   
    //Getters for all the view panels.
    public AdminTransactionsViewPanel getSeeTransactionsViewPanel() {
        return seeTransactionsViewPanel2;
    }

    public View.CreateCustomerViewPanel getCreateCustomerPanel() {
        return createCustomerViewPanel1;
    }

    public View.EditCustomerViewPanel getEditCustomerViewPanel() {
        return editCustomerViewPanel1;
    }
    
    public View.CreatePricesViewPanel getCreatePricesViewPanel(){
        return createPricesViewPanel1;
    }

    public View.EditPricesViewPanel getEditPricesViewPanel() {
        return editPricesViewPanel1;       
    }
    
    public View.LoginViewPanel getLoginViewPanel() {
        return loginViewPanel1; 
    }
    
    public View.UserViewPanel getUserViewPanel() {
        return userViewPanel1; 
    }
    
    public View.AdminViewPanel getAdminViewPanel() {
        return adminViewPanel2; 
    }
    
    public View.UserAccountViewPanel getAccountViewPanel() {
        return accountViewPanel1;
    }
    
    public View.UserTransactionsViewPanel getUserTransactionsViewPanel() {
        return userTransactionsViewPanel1;
    }
    
    public View.UserPricesViewPanel getUserPricesViewPanel() {
        return userPricesViewPanel1;
    }
    
    public View.InsertMoneyViewPanel getInsertMoneyViewPanel() {
        return insertMoneyViewPanel1;
    }
}
